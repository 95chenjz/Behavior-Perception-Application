package com.ydcun.libsvm_action.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Util {

	/**
	 * return according to the type
	 * 
	 * @return
	 */
	public static String getChangeRow() {
		return System.getProperty("line.separator", "/n");
	}

	/**
	 * str == num? true or false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		if (str == null)
			return false;
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * string == null?
	 * 
	 * @param str
	 *            strings
	 * @param isDelSpace
	 *         	  delete space
	 * @return true or false
	 * 
	 */
	public static boolean isEmptyString(String str, boolean isDelSpace) {
		if (null == str) {
			return true;
		}
		if (isDelSpace) {
			str = str.trim();
		}
		if (str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * string == null
	 * 
	 * @param str
	 *            string
	 * @return true or false
	 * 
	 */
	public static boolean isEmptyString(String str) {
		return isEmptyString(str, false);
	}

	/**
	 * object to list
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] ObjectToByteArr(Object obj) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * list to object
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static Object ByteArrToObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);
			obj = oi.readObject();
			bi.close();
			oi.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * get user home path
	 * 
	 * @return
	 */
	public static String getUserHomePath() {
		return System.getProperties().getProperty("user.home");
	}

	/**
	 * get temp svm path
	 * 
	 * @return
	 */
	public static String getSvmPath() {
		File file = new File(getUserHomePath() + "/svm/test");
	
		if (!file.getParentFile().exists()) {
			
			System.out.println("path dose not exist, creating!");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("creating failed");
			}
		}
		return getUserHomePath() + "/svm";
	}

	/**
	 * 
	 * @param commandStr
	 */
	@SuppressWarnings("finally")
	public static String exeCmd(String commandStr) {
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			Process p = Runtime.getRuntime().exec(commandStr);
			br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
			String line = null;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
	}

	/**
	 * string to system path
	 * 
	 * @param content
	 * @param filePath
	 * @param fileAdd  ture or false
	 */
	public static void stringToFile(String content, String filePath,boolean fileAdd) {
		FileWriter writer = null;
		BufferedWriter bw = null;
		try {
			writer = new FileWriter(filePath, fileAdd);
			bw = new BufferedWriter(writer);
			bw.write(content);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * read file to String
	 **/
	@SuppressWarnings("finally")
	public static String readFileToString(String filePath) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; 
		String str = "";
		try {
			fis = new FileInputStream(filePath);// FileInputStream
			
			isr = new InputStreamReader(fis);// InputStreamReader 
			br = new BufferedReader(isr);
			String line ="";							// InputStreamReader
			while ((line = br.readLine()) != null) {
				str += line + Util.getChangeRow();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null){
					br.close();
				}
				if(isr!=null){
					isr.close();
				}
				if(fis!=null){
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	/**
	 * read the last line
	 * 
	 * @param file
	 * @param charset
	 * @return
	 */
	public static String readLastLine(File file, String charset) {
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			return null;
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
			long len = raf.length();
			if (len == 0L) {
				return "";
			} else {
				long pos = len - 1;
				while (pos > 0) {
					pos--;
					raf.seek(pos);
					if (raf.readByte() == '\n') {
						break;
					}
				}
				if (pos == 0) {
					raf.seek(0);
				}
				byte[] bytes = new byte[(int) (len - pos)];
				raf.read(bytes);
				if (charset == null) {
					return new String(bytes);
				} else {
					return new String(bytes, charset);
				}
			}
		} catch (Exception e) {
			System.out.println("read the last line failed：" + file.getPath());
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e2) {
				}
			}
		}
		return null;
	}
	/**
	 * 01011->00000,
	 * 		  00001,
	 * 		  00010,
	 *        00011,
	 *        01000,
	 *        01001,
	 *        01010,
	 *        01011,
	 * @param str
	 * @return
	 */
	public static List<String> stringToMatrix(String str){
		List<String> result = new ArrayList<String>();
		int length = 0;
		int[] index = new int[str.length()];
		String zeroCount="";
		for(int i=0;i<str.length();i++){//计算长度
			if(str.charAt(i)=='1'){
				index[length] = i;
				length++;
				zeroCount+="0";
			}
		}
		String temp=null;
		StringBuffer sb=null;
		for(int i=0;i<Math.pow(2, length);i++){
			temp = (zeroCount+Integer.toBinaryString(i)).replaceAll("0*(\\d{"+length+"})","$1");
			sb = new StringBuffer(str);
			for(int j=0;j<length;j++){
				sb.deleteCharAt(index[j]);
				sb.insert(index[j], temp.charAt(j));
			}
			result.add(sb.toString());
		}
		return result;
	}
	/**
	 * list
	 * @param source
	 * @param sex
	 * @param age
	 * @param weight
	 * @param height
	 * @return
	 */
	public static List<String> getFileNameHZList(String source,String sex,Integer age,Integer weight,Integer height){
		List<String> listName = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append(source==null?0:1);
		sb.append(sex==null?0:1);
		sb.append(age==null?0:1);
		sb.append(weight==null?0:1);
		sb.append(height==null?0:1);
		List<String> list = Util.stringToMatrix(sb.toString());
		
		StringBuffer sbFileName = new StringBuffer();
		String[] typeStr={source,sex};
		Integer[] typeInt = {age,weight,height};
		Util.typeScale(typeStr, typeInt);//normalize
		for(String str:list){
			sbFileName.setLength(0);
			sbFileName.append(str.charAt(0)=='0'?"source_0":"source_"+typeStr[0]);
			sbFileName.append(str.charAt(1)=='0'?"_sex_0":"_sex_"+typeStr[1]);
			sbFileName.append(str.charAt(2)=='0'?"_age_0":"_age_"+typeInt[0]);
			sbFileName.append(str.charAt(3)=='0'?"_weight_0":"_weight_"+typeInt[1]);
			sbFileName.append(str.charAt(4)=='0'?"_height_0":"_height_"+typeInt[2]);
			listName.add(sbFileName.toString());
		}
		return listName;
	}
	/**
	 * normalize model type（source sex  age weight height）
	 * @param typeStr
	 * @param typeInt
	 */
	public static void typeScale(String[] typeStr,Integer[] typeInt){
		typeStr[0] = typeStr[0]==null?"0":typeStr[0];
		typeStr[1] = typeStr[1]==null?"0":typeStr[1];
		typeInt[0]  = typeInt[0]==null||typeInt[0]<1||typeInt[0]>=100?0:typeInt[0]/5+1;
		typeInt[1] = typeInt[1]==null||typeInt[1]<1||typeInt[1]>=100?0:typeInt[1]/5+1;
		typeInt[2] = typeInt[2]==null||typeInt[2]<50||typeInt[2]>=200?0:typeInt[2]/5+1;
		
	}

	/**
	 * baye[] transform
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(System.getProperties().getProperty("user.home"));
	}
}
