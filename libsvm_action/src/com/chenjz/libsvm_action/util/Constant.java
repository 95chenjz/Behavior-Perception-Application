package com.ydcun.libsvm_action.util;import java.util.HashMap;import java.util.Map;public class Constant {	public static Map<String,Double> actMapToCode = new HashMap<String, Double>();	public static Map<Double,String> actMapFromCode = new HashMap<Double,String>();	public static Map<String,Double> wzMapToCode = new HashMap<String, Double>();	public static Map<Double,String> wzMapFromCode = new HashMap<Double,String>();	static {		//ACT ID->Code		actMapToCode.put("Still",1d);		actMapToCode.put("Walking",4d);		actMapToCode.put("Running",5d);				//ACT code->ID		actMapFromCode.put(1d,"Still");		actMapFromCode.put(4d,"Walking");		actMapFromCode.put(5d,"Running");				//POSITION ID->code		wzMapToCode.put("Hand Fixed", 1d);		wzMapToCode.put("Hand Swing", 2d);		//POSITION code->ID			wzMapFromCode.put(1d,"Hand Fixed");		wzMapFromCode.put(2d,"Hand Swing"); 	}		/**ActivityRecognizer class index  Still**/	public static final String  ACT_JJ_ID="Still";	/**ActivityRecognizer class index   Still**/	public static final String  ACT_JJ_NAME="Still";	public static final double  ACT_JJ_CODE=2;		/**ActivityRecognizer class index  Walking**/	public static final String  ACT_ZL_ID="Walking";	/**ActivityRecognizer class index Walking**/	public static final String  ACT_ZL_NAME="Walking"";	public static final double  ACT_ZL_CODE=4;		/**ActivityRecognizer class index  Running**/	public static final String  PB_ID="Running";	/**ActivityRecognizer class index  Running**/	public static final String  ACT_PB_NAME="Running";	public static final double  ACT_PB_CODE=5;	 	/*Funcation*/	/**min**/	public static final String FUN_101_MINIMUM_NAME = "min";	public static final int FUN_101_MINIMUM_CODE = 101;	/**max**/	public static final String FUN_102_MAXIMUM_NAME= "max";	public static final int FUN_102_MAXIMUM_CODE= 102;	/**variance**/	public static final String FUN_103_VARIANCE_NAME = "variance";	public static final int FUN_103_VARIANCE_CODE = 103;	/**mcr**/	public static final String FUN_104_MEANCROSSINGSRATE_NAME = "mcr";	public static final int FUN_104_MEANCROSSINGSRATE_CODE = 104;	/**stddev**/	public static final String FUN_105_STANDARDDEVIATION_NAME = "stddev";	public static final int FUN_105_STANDARDDEVIATION_CODE = 105;	/**mean**/	public static final String FUN_106_MEAN_NAME = "mean";	public static final int FUN_106_MEAN_CODE = 106;	/**mag**/	public static final String FUN_107_SIGNALVECTORMAGNITUDE_NAME = "mag";	public static final int FUN_107_SIGNALVECTORMAGNITUDE_CODE = 107;	/** 1/4 q1**/	public static final String FUN_108_FIRSTQUARTILE_NAME = "q1";	public static final int FUN_108_FIRSTQUARTILE_CODE = 108;	/**median**/	public static final String FUN_109_MEDIAN_NAME = "median";	public static final int FUN_109_MEDIAN_CODE = 109;	/**3/4 q3**/	public static final String FUN_110_THIRDQUARTILE_NAME = "q3";	public static final int FUN_110_THIRDQUARTILE_CODE = 110;	/**zcr**/	public static final String FUN_111_ZEROCROSSINGRATE_NAME = "zcr";	public static final int FUN_111_ZEROCROSSINGRATE_CODE = 111;	/**rms**/	public static final String FUN_112_RMS_NAME = "rms";	public static final int FUN_112_RMS_CODE = 112;	/**sma**/	public static final String FUN_113_SMA_NAME = "sma";	public static final int FUN_113_SMA_CODE = 113;	/**iqr**/	public static final String FUN_114_IQR_NAME = "iqr";	public static final int FUN_114_IQR_CODE = 114;	/**mad**/	public static final String FUN_115_MAD_NAME = "mad";	public static final int FUN_115_MAD_CODE = 115;	/**tenergy**/	public static final String FUN_116_TENERGY_NAME = "tenergy";	public static final int FUN_116_TENERGY_CODE = 116;			/**spp**/	public static final String FUN_201_SPP_NAME = "spp";	public static final int FUN_201_SPP_CODE = 201;	/**energy**/	public static final String FUN_202_ENERGY_NAME = "energy";	public static final int FUN_202_ENERGY_CODE = 202;	/**entropy**/	public static final String FUN_203_ENTROPY_NAME = "entropy";	public static final int FUN_203_ENTROPY_CODE = 203;	/**centroid**/	public static final String FUN_204_CENTROID_NAME = "centroid";	public static final int FUN_204_CENTROID_CODE = 204;	/**fdev**/	public static final String FUN_205_FDEV_NAME = "fdev";	public static final int FUN_205_FDEV_CODE = 205;	/**fmean**/	public static final String FUN_206_FMEAN_NAME = "fmean";	public static final int FUN_206_FMEAN_CODE = 206;	/**skew**/	public static final String FUN_207_SKEW_NAME = "skew";	public static final int FUN_207_SKEW_CODE = 207;	/**kurt**/	public static final String FUN_208_KURT_NAME = "kurt";	public static final int FUN_208_KURT_CODE = 208;}