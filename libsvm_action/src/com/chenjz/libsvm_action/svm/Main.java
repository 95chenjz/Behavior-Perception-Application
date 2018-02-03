/**
 * 
 */
package com.ydcun.libsvm_action.svm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ydcun.libsvm_action.libsvm.svm_scale;
import com.ydcun.libsvm_action.libsvm.svm_train;
import com.ydcun.libsvm_action.model.Item;
import com.ydcun.libsvm_action.util.CSVFileUtil;
import com.ydcun.libsvm_action.util.Constant;
import com.ydcun.libsvm_action.util.DateUtil;
import com.ydcun.libsvm_action.util.Features;
import com.ydcun.libsvm_action.util.Util;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;


public class Main {
	private static String dir = Main.class.getResource("../resource").getPath().substring(1);
	/**
	 * training model
	 * @throws Exception
	 */
	public static void trainModel() throws Exception{
		String fileName = "accdata2.csv";
		List<Item> list = readFile(fileName);
		System.out.println(list.size());
		//creat training file
		createTrainFile(list,"train");
		//creat normalize file
		creatScaleFile( new String[]{"-l","0","-u","1","-s",dir+"/range",dir+"/train"}, dir+"/scale");
		//calculate c g by grid.py 
		String[] cgr = cmdGridPy("python "+Util.getSvmPath()+"/libsvm/tools/grid.py"+" "+dir+"/scale",dir+"/grid");
		//model: "-v","5",
		creatModeFile(new String[]{"-s","0","-c",cgr[0],"-t","2","-g",cgr[1],"-e","0.1",dir+"/scale",dir+"/model"});
	}
	/**
	 * prediction
	 * @throws Exception 
	 */
	public static void predict() throws Exception{
		String[] ruleArr = readRule(dir+"/range");
		int sum = ruleArr.length-2;//get features number
		String[] tempArr = null;//lable：tempArr[0] min:tempArr[1] max:tempArr[2]
		//normalize
		tempArr = ruleArr[1].split(" ");
		double lower = Double.parseDouble(tempArr[0]);
		double upper = Double.parseDouble(tempArr[1]);
		
		String trainTest = Util.readFileToString(dir+"/trainTest");
		System.out.println(trainTest);
		String[] trainTestLineArry = trainTest.split(Util.getChangeRow());
		String[] trainTestItemArry = null;
		svm_node[] px =null;
		svm_node p = null;
		String[] tempNode = null;
		StringBuffer sb = new StringBuffer();
		System.out.println("predicted--- true_value-----istrue");
		int cw =0;
		int zq = 0;
		for(int j=0;j<trainTestLineArry.length;j++){
			trainTestItemArry = trainTestLineArry[j].split(" ");
			px = new svm_node[sum];
			for(int i=0;i<9;i++){
				p = new svm_node();
				tempArr = ruleArr[i+2].split(" ");
				tempNode = trainTestItemArry[i+1].split(":");
				p.index = Integer.parseInt(tempNode[0]);
				p.value = Features.zeroOneLibSvm(lower, upper, Double.parseDouble(tempNode[1]), Double.parseDouble(tempArr[1]), Double.parseDouble(tempArr[2]));
				px[i] = p;
			}
			svm_model model = svm.svm_load_model(dir+"/model");
			double code = svm.svm_predict(model, px);
			if(trainTestItemArry[0].equals(code+"")){
				System.out.println(code+"  "+trainTestItemArry[0]+" true");
				sb.append(code+"  "+trainTestItemArry[0]+" true");
				zq++;
			}else{
				System.err.println(code+"  "+trainTestItemArry[0]+" false");
				sb.append(code+"  "+trainTestItemArry[0]+" false");
				cw++;
			}
			sb.append(Util.getChangeRow());
		}
		System.out.println("prediction： correct："+zq+" wrong:"+cw);
		Util.stringToFile(sb.toString(),dir+"/trainTestResult", false);
		
	}
	public static void main(String[] args) throws Exception {
		trainModel();
//		predict();
		System.out.println(Main.class.getResource(""));
		System.out.println(Main.class.getResource("../resource"));
	}

	
	
	
	
	
	/**
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private static List<Item> readFile(String fileName) throws Exception {
		DateUtil.printNameDate(new Date(), "reading" + fileName + "file");
		List<Item> list = new ArrayList<Item>();
		
		CSVFileUtil csv = new CSVFileUtil(dir + "/" + fileName);
		String temp = null;
		ArrayList<String> tempList = null;
		csv.readLine();// header first
		while ((temp = csv.readLine()) != null) {
			tempList = csv.fromCSVLinetoArray(temp);
			Item tempItem = new Item(tempList.get(1), tempList.get(2), Double.valueOf(tempList.get(3)),
					Double.valueOf(tempList.get(4)), Double.valueOf(tempList.get(5)), Double.valueOf(tempList.get(6)),
					Double.valueOf(tempList.get(7)), Double.valueOf(tempList.get(8)), Double.valueOf(tempList.get(9)),
					Double.valueOf(tempList.get(10)), Double.valueOf(tempList.get(11)));
			list.add(tempItem);
		}
		DateUtil.printNameDate(new Date(), "reading" + fileName + "file finished");
		return list;
	}
	private static void createTrainFile(List<Item> list,String trainFileName){
		DateUtil.printNameDate(new Date(), "create "+trainFileName+" file");
		
		StringBuffer sb = new StringBuffer();
		Item tempItem = null;
		for(int i=0;i<list.size();i++){
			tempItem = list.get(i);
			//define training nots
			sb.append(Constant.actMapToCode.get(tempItem.getAct()));
			//mix
			sb.append(" "+Constant.FUN_101_MINIMUM_CODE+":"+tempItem.getT_min());
			//max
			sb.append(" "+Constant.FUN_102_MAXIMUM_CODE+":"+tempItem.getT_max());
			//variance 
			sb.append(" "+Constant.FUN_103_VARIANCE_CODE+":"+tempItem.getT_variance());
			//mcr
			sb.append(" "+Constant.FUN_104_MEANCROSSINGSRATE_CODE+":"+tempItem.getT_mcr());
			//stddev
			sb.append(" "+Constant.FUN_105_STANDARDDEVIATION_CODE+":"+tempItem.getT_sttdev());
			//mean
			sb.append(" "+Constant.FUN_106_MEAN_CODE+":"+tempItem.getT_mean());
			//rms
			sb.append(" "+Constant.FUN_112_RMS_CODE+":"+tempItem.getT_rms());
			//iqr
			sb.append(" "+Constant.FUN_114_IQR_CODE+":"+tempItem.getT_iqr());
			//mad
			sb.append(" "+Constant.FUN_115_MAD_CODE+":"+tempItem.getT_mad());
			sb.append(Util.getChangeRow());
		}
		Util.stringToFile(sb.toString(),dir+"/"+trainFileName,false);
		DateUtil.printNameDate(new Date(), trainFileName+"file creation complete");
	}
	
	/**
	 * normalize train data and create scale file
	 * @param args String[] args = new String[]{"-l","0","-u","1",path+"/train"};
	 * @param scalePath return path
	 */
	private static void creatScaleFile(String[] args,String scalePath) {

		DateUtil.printNameDate(new Date(), "normalizing");
		FileOutputStream fileOutputStream =null;
		PrintStream printStream = null;
		try {
			File file = new File(scalePath);
			file.createNewFile();
			fileOutputStream = new FileOutputStream(file);
		    printStream = new PrintStream(fileOutputStream);
		    // old stream
		    PrintStream oldStream = System.out;
	        System.setOut(printStream);//system.out
			svm_scale.main(args);//normalizing
	        System.setOut(oldStream);//syste.out
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fileOutputStream!=null){
					fileOutputStream.close();
				}
				if(printStream != null){
					printStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DateUtil.printNameDate(new Date(), "normalized");
	}
	/**
	 * train c and g
	 * @param string 
	 */
	private static String[] cmdGridPy(String str,String gridPath){
		DateUtil.printNameDate(new Date(), "calculating c and g ");
		String grid = Util.exeCmd(str);
		System.out.println(str);
		Util.stringToFile(grid, gridPath,false);//save
		String gridEndLine = Util.readLastLine(new File(gridPath),null);
		gridEndLine = gridEndLine.substring(0, gridEndLine.indexOf("\n"));
		String[] cgr= gridEndLine.split(" ");
		DateUtil.printNameDate(new Date(),"c="+cgr[0]+" γ="+cgr[1]+" CV Rate="+cgr[2]+"%");
		return cgr;
	}
	/**
	 * build model
	 * @param agrs
	 */
	private static void creatModeFile(String[] agrs){
		DateUtil.printNameDate(new Date(),"calculating model");
		try {
			svm_train.main(agrs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DateUtil.printNameDate(new Date(),"model calculated");
	}
	/**
	 * read normalized file line by line into strings.
	 */
	private static String[] readRule(String rulePath){
		String ruleStr = Util.readFileToString(rulePath);
		String[] ruleArr = ruleStr.split(Util.getChangeRow());
		return ruleArr;
	}
}
