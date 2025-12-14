package com.example.douyin;

import android.util.Log;

import java.math.BigDecimal;
//工具类，这里主要实现加减点赞数量
public class Utils {
    //加点赞数量：
    //这里我服务端的返回值是都是以万结尾的
    //为了方便展示UI数字的变化
    //点赞直接+0.1万
    public static String addLikes(String oldlikes) {
        if (oldlikes.contains("万")) {

            String oldNLike = oldlikes.substring(0, oldlikes.length() - 1).trim();
            BigDecimal bigDecimal = new BigDecimal(oldNLike);
            BigDecimal add = bigDecimal.add(new BigDecimal("0.1"));
            return add.toString() + "万";

        } else {
            return String.valueOf(Integer.parseInt(oldlikes) + 1);
        }
    }
   //取消点赞直接减0.1万
    public static String jianLikes(String oldlikes) {
        if (oldlikes.contains("万")) {

            String oldNLike = oldlikes.substring(0, oldlikes.length() - 1).trim();
            BigDecimal bigDecimal = new BigDecimal(oldNLike);
            BigDecimal add = bigDecimal.subtract(new BigDecimal("0.1"));
            return add.toString() + "万";

        } else {
            return String.valueOf(Integer.parseInt(oldlikes) - 1);
        }
    }


//    private static int parseCount(String v) {
//        if (v.endsWith("万")) {
//            String num = v.substring(0, v.length() - 1).trim();
//            double d = Double.parseDouble(num);
//            return (int) Math.round(d * 10000);
//        }
//        String digits = v.replaceAll("[^0-9]", "");
//        if (digits.isEmpty()) return 0;
//        return Integer.parseInt(digits);
//    }
//
//    private static String formatCount(int n) {
//        if (n >= 10000) {
//            double d = n / 10000.0;
//            return String.format(java.util.Locale.CHINA, "%.1f万", d);
//        }
//        return String.valueOf(n);
//    }
//
//    public static String addLikes(String oldlikes) {
//        int n = parseCount(oldlikes);
//        return formatCount(n + 1);
//    }
//
//    public static String jianLikes(String oldlikes) {
//        int n = parseCount(oldlikes);
//        n = Math.max(0, n - 1);
//        return formatCount(n);
//    }
}
