package com.example.douyin;

import java.math.BigDecimal;

public class Utils {
    //因为我们有加万字样，所以我们这里模糊处理；
    // 如果上万，就直接加0.1万用来显示UI数字的变化；
    //如果没上万，就直接+1；
    public static String addLikes(String oldlikes){
        BigDecimal bigDecimal;
        if(oldlikes.contains("万")){
            String substring = oldlikes.substring(0, oldlikes.length() - 2);
            bigDecimal = new BigDecimal(substring);
            StringBuilder sb = new StringBuilder(String.valueOf(bigDecimal.add(new BigDecimal("0.1"))));
            sb.append("万");
            return sb.toString();
        }
        else{
            return String.valueOf(Integer.parseInt(oldlikes)+1);

        }
    }

    public static String jianLikes(String oldlikes){
        BigDecimal bigDecimal;
        if(oldlikes.contains("万")){
            String substring = oldlikes.substring(0, oldlikes.length() - 2);
            bigDecimal = new BigDecimal(substring);
            StringBuilder sb = new StringBuilder(String.valueOf(bigDecimal.subtract(new BigDecimal("0.1"))));
            sb.append("万");
            return sb.toString();

        }
        else{
            return String.valueOf(Integer.parseInt(oldlikes)-1);

        }
    }
}
