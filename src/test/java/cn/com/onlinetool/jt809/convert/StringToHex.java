package cn.com.onlinetool.jt809.convert;


import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StringToHex{

    public String convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }

    public String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    //504F533838383834  POS88884
    public static void main1(String[] args) {

        StringToHex strToHex = new StringToHex();
//        System.out.println("\n-----ASCII码转换为16进制 -----");
//        String str = "POS88884";
//        System.out.println("字符串: " + str);
//        String hex = strToHex.convertStringToHex(str);
//        System.out.println("转换为16进制 : " + hex);

//        String hex="3132333435363738393031000000000000000000";
//        String hex="3132333435364071712E636F6D";
        String hex="5BAC3F40";
        System.out.println("\n***** 16进制转换为ASCII *****");
        System.out.println("Hex : " + hex);
        System.out.println("ASCII : " + strToHex.convertHexToString(hex));
    }


    public static void main(String[] args) {
        String s = "000000005BAC4D50";
        long longVaue = new BigInteger(s, 16).longValue();

        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(longVaue);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        System.out.println(dateFormat.format(c.getTime()));

    }
}