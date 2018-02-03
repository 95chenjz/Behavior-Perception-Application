package com.ydcun.libsvm_action.util;  
  
import java.io.BufferedReader;  
import java.io.FileInputStream;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
  
public class CSVFileUtil {  
    // CSV encode 
    public static final String ENCODE = "UTF-8";  
  
    private FileInputStream fis = null;  
    private InputStreamReader isw = null;  
    private BufferedReader br = null;  
  
     
    public CSVFileUtil(String filename) throws Exception {  
        fis = new FileInputStream(filename);  
        isw = new InputStreamReader(fis, ENCODE);  
        br = new BufferedReader(isw);  
    }  
  
    // ==========public=============================  
    /** 
     * CSV readline。 
     * 
     * @throws Exception 
     */  
    public String readLine() throws Exception {  
  
        StringBuffer readLine = new StringBuffer();  
        boolean bReadNext = true;  
  
        while (bReadNext) {  
            //  
            if (readLine.length() > 0) {  
                readLine.append("\r\n");  
            }  
            // 一行  
            String strReadLine = br.readLine();  
  
            // readLine is Null  
            if (strReadLine == null) {  
                return null;  
            }  
            readLine.append(strReadLine);  
  
       
            if (countChar(readLine.toString(), '"', 0) % 2 == 1) {  
                bReadNext = true;  
            } else {  
                bReadNext = false;  
            }  
        }  
        return readLine.toString();  
    }  
  
    /** 
     * make lines in CSV to strings. set null to short string. 
     */  
    public static String[] fromCSVLine(String source, int size) {  
        ArrayList tmpArray = fromCSVLinetoArray(source);  
        if (size < tmpArray.size()) {  
            size = tmpArray.size();  
        }  
        String[] rtnArray = new String[size];  
        tmpArray.toArray(rtnArray);  
        return rtnArray;  
    }  
  
    /** 
     * make lines in CSV to strings without thershold.
     */  
    public static ArrayList fromCSVLinetoArray(String source) {  
        if (source == null || source.length() == 0) {  
            return new ArrayList();  
        }  
        int currentPosition = 0;  
        int maxPosition = source.length();  
        int nextComma = 0;  
        ArrayList rtnArray = new ArrayList();  
        while (currentPosition < maxPosition) {  
            nextComma = nextComma(source, currentPosition);  
            rtnArray.add(nextToken(source, currentPosition, nextComma));  
            currentPosition = nextComma + 1;  
            if (currentPosition == maxPosition) {  
                rtnArray.add("");  
            }  
        }  
        return rtnArray;  
    }  
  
  
    /** 
     * String to CSV line
     */  
    public static String toCSVLine(String[] strArray) {  
        if (strArray == null) {  
            return "";  
        }  
        StringBuffer cvsLine = new StringBuffer();  
        for (int idx = 0; idx < strArray.length; idx++) {  
            String item = addQuote(strArray[idx]);  
            cvsLine.append(item);  
            if (strArray.length - 1 != idx) {  
                cvsLine.append(',');  
            }  
        }  
        return cvsLine.toString();  
    }  
  
    /** 
     * strArrList to CSV line
     */  
    public static String toCSVLine(ArrayList strArrList) {  
        if (strArrList == null) {  
            return "";  
        }  
        String[] strArray = new String[strArrList.size()];  
        for (int idx = 0; idx < strArrList.size(); idx++) {  
            strArray[idx] = (String) strArrList.get(idx);  
        }  
        return toCSVLine(strArray);  
    }  
  
    // ==========private=============================  
    /** 
     *account the number of characters 
     * 
     * @param str string column
     * @param c charracter
     * @param start  beginning position 
     * @return number
     */  
    private int countChar(String str, char c, int start) {  
        int i = 0;  
        int index = str.indexOf(c, start);  
        return index == -1 ? i : countChar(str, c, index + 1) + 1;  
    }  
  
    /** 
     * Looking for next comma. 
     * 
     * @param source string column
     * @param st  beginning index position 
     * @return index of next comma 
     */  
    private static int nextComma(String source, int st) {  
        int maxPosition = source.length();  
        boolean inquote = false;  
        while (st < maxPosition) {  
            char ch = source.charAt(st);  
            if (!inquote && ch == ',') {  
                break;  
            } else if ('"' == ch) {  
                inquote = !inquote;  
            }  
            st++;  
        }  
        return st;  
    }  
  
    /** 
     * get next string 
     */  
    private static String nextToken(String source, int st, int nextComma) {  
        StringBuffer strb = new StringBuffer();  
        int next = st;  
        while (next < nextComma) {  
            char ch = source.charAt(next++);  
            if (ch == '"') {  
                if ((st + 1 < next && next < nextComma) && (source.charAt(next) == '"')) {  
                    strb.append(ch);  
                    next++;  
                }  
            } else {  
                strb.append(ch);  
            }  
        }  
        return strb.toString();  
    }  
  
    /** 
     * add "" to strings 
     * 
     * @param item  strings 
     * @return strings after added "" 
     */  
    private static String addQuote(String item) {  
        if (item == null || item.length() == 0) {  
            return "\"\"";  
        }  
        StringBuffer sb = new StringBuffer();  
        sb.append('"');  
        for (int idx = 0; idx < item.length(); idx++) {  
            char ch = item.charAt(idx);  
            if ('"' == ch) {  
                sb.append("\"\"");  
            } else {  
                sb.append(ch);  
            }  
        }  
        sb.append('"');  
        return sb.toString();  
    }  
}  