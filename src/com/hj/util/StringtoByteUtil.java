package com.hj.util;

import java.util.Date;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * 汉字转成字节、汉字转成拼音，获取首字母
 * @author wqfang
 *
 */
public class StringtoByteUtil {
	/** 
     * <把字符串转换成字节数组然后在封装成字符串> 
     * @param chinese 
     * @return 
     */  
    public static String chineseToString(String chinese)  
    {  
        if (chinese==null || chinese=="")  
        {  
            return "";  
        }  
        else  
        {  
            // 定义StringBuffer  
            StringBuffer sb = new StringBuffer();  
              
            // 把传进来的字符串转换成字节数组  
            byte[] b = chinese.getBytes();  
              
            byte[] temp = null;  
              
            // 遍历字节数组，把字节数组转换成字符串  
            for (int i = 0; i < b.length; i++)  
            {  
                temp = new byte[4];  
                temp[0] = b[i];  
                temp[1] = 0;  
                temp[2] = 0;  
                temp[3] = 0;  
                sb.append(lBytesToInt(temp));  
                if (i < b.length - 1)  
                {  
                    sb.append("@");  
                }  
            }  
              
            return sb.toString();  
        }  
    }  
      
    /** 
     * <把字节数组封装成的字符串转换成原来的字符串> 
     * @param stc 
     * @return 
     */  
    public static String stringToChinese(String stc)  
    {  
        // 如果传递的字符串为空则直接返回空  
        if (stc==null || stc=="")  
        {  
            return "";  
        }  
        else  
        {  
            // 分割字符串  
            String[] s = stc.split("@");  
            if (s.length > 0)  
            {  
                // 循环构造BYTE数组  
                byte[] b = new byte[s.length];  
                for (int i = 0; i < s.length; i++)  
                {  
                    b[i] = (byte)Integer.parseInt(s[i]);  
                }  
                  
                // 根据BYTE数组构造字符串  
                return new String(b);  
            }  
            else  
            {  
                return "";  
            }  
        }  
    }  
    /** 
     * 将低字节数组转换为int 
     * @param b byte[] 
     * @return int 
     */  
    public static int lBytesToInt(byte[] b)  
    {  
        int s = 0;  
        for (int i = 0; i < 3; i++)  
        {  
            if (b[3 - i] >= 0)  
            {  
                s = s + b[3 - i];  
            }  
            else  
            {  
                s = s + 256 + b[3 - i];  
            }  
            s = s * 256;  
        }  
        if (b[0] >= 0)  
        {  
            s = s + b[0];  
        }  
        else  
        {  
            s = s + 256 + b[0];  
        }  
        return s;  
    }  
    
    	/**
    	 * 将汉字转换为全拼  
    	 * @param src
    	 * @return
    	 */
    public static String getPingYin(String src) {  
  
        char[] t1 = null;  
        t1 = src.toCharArray();  
        String[] t2 = new String[t1.length];  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();  
          
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length;  
        try {  
            for (int i = 0; i < t0; i++) {  
                // 判断是否为汉字字符  
                if (java.lang.Character.toString(t1[i]).matches(  
                        "[\\u4E00-\\u9FA5]+")) {  
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);  
                    t4 += t2[0];  
                } else  
                    t4 += java.lang.Character.toString(t1[i]);  
            }  
            // System.out.println(t4);  
            return t4;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
        return t4;  
    }  
    
 /**
  *  返回中文的首字母  
  * @param str
  * @return
  */
    public static String getPinYinHeadChar(String str) {  
  
        String convert = "";  
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);  
            } else {  
                convert += word;  
            }  
        }  
        return convert;  
    }  
  
    /**
     *  将字符串转移为ASCII码  
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {  
        StringBuffer strBuf = new StringBuffer();  
        byte[] bGBK = cnStr.getBytes();  
        for (int i = 0; i < bGBK.length; i++) {  
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));  
        }  
        return strBuf.toString();  
    }  
    
    /**
     *  将汉字转换为大写拼音，输出n位，不足的在后面用0补足。
     * @param cnStr
     * @param n  n小于20
     * @return
     */
    public static String getSixCode(String str,int n) {  
    	if(str == null || str.isEmpty()){
    		return "";
    	}
    	String SixStr = getPinYinHeadChar(str).toUpperCase()+"0000000000000000000000000000";
    	return SixStr.substring(0, n);
    }  
    
    /**
     * 根据时间生成唯一ID，精度为1秒
     * @return
     */
    public static int getDateCode() {  
    	long applyId = new Date().getTime();
    	String str = applyId + "";
    	return Integer.parseInt(str.substring(0, 10));
    }  
    
    
    
    
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
    	long applyId = new Date().getTime();
    	String str = applyId + "";
    	System.out.println(applyId);//1504842132//1504842157649//1504842181221
	}
}
