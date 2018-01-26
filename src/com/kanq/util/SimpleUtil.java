package com.kanq.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import jodd.datetime.JDateTime;
import jodd.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * description : 常用的一些静态方法
 */
public class SimpleUtil {

    //获取真实IP地址
    public static String getIpAddr(HttpServletRequest request, Integer... indexs) {
        Integer index = indexs.length == 0 ? 0 : indexs[0];//若提供的index为空，默认赋予0
        String[] headers = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP"};
        String ip = request.getHeader(headers[index]);
        if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip.trim())) {//递归获取真实IP地址
            if (index < headers.length - 1) {
                ip = getIpAddr(request, ++index);
            } else {
                ip = request.getRemoteAddr();
            }
        }
        return ip;
    }

    public static boolean isChineseName(String name) {
        Pattern pattern = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,5}$");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static Boolean IsNullOrEmpty(String s) {
        Boolean b = false;
        try {
            if (s == null) {
                b = true;
                return b;
            }
            if (s.trim().equals("")) {
                b = true;
            }
        } catch (Exception e) {
            return true;
        }
        return b;
    }


    public static Date sdf(String s) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d = sdf.parse(s);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }

    public static Boolean Contains(String[] s, String s1) {
        Boolean b = false;
        for (int i = 0; i < s.length; i++) {
            if (s[i].trim().equals(s1.trim())) {
                b = true;
                break;
            }
        }
        return b;
    }

//    public static String get32R() {
//        long a = new Date().getTime();
//        String ret = MD5.getMD5ofStr(a + "");
//        return ret;
//    }


    public static String getAbsWebPath(HttpServletRequest request) {
        return getAbsWebPath(request, "/");
    }

    public static String getAbsWebPath(HttpServletRequest request, String folderName) {
        String webPath = request.getServletContext().getRealPath(folderName);
        return webPath;
    }

    public static String getAbsWebPathWithSeparator(HttpServletRequest request) {
        return getAbsWebPath(request, "/") + File.separator;
    }

    public static String getAbsWebPathWithSeparator(HttpServletRequest request, String folderName) {
        return getAbsWebPath(request, folderName) + File.separator;
    }

    static SerializerFeature[] features = new SerializerFeature[]{
            //空的处理
            SerializerFeature.WriteNullStringAsEmpty,//字符串类型字段，若为null,输出为''
            SerializerFeature.WriteNullNumberAsZero,//数值类型字段，若为null,输出为0
            SerializerFeature.WriteNullListAsEmpty,//List字段，若为null,输出为[]
            SerializerFeature.WriteNullBooleanAsFalse,//Boolean字段，若为null,输出为false
            //格式化
            SerializerFeature.WriteDateUseDateFormat,//时间，2015-10-09 12:00:00
            SerializerFeature.QuoteFieldNames,//非引号模式
            SerializerFeature.DisableCircularReferenceDetect//非引用模式
    };

    public static String toJSON(List list) {
        String str = JSON.toJSONString(list, features);
        return str;
    }

    public static String toJSON(Object object) {
        String str = JSON.toJSONString(object, features);
        return str;
    }

    public static String getNow() {
        String now = new JDateTime().toString("YYYY-MM-DD hh:mm:ss");
        return now;
    }

    public static String getNowDate() {
        String now = new JDateTime().toString("YYYY-MM-DD");
        return now;
    }

    public static String getNowTime() {
        String now = new JDateTime().toString("hh:mm:ss");
        return now;
    }

    public static String convertOracleDate(String dateStr) {
        return "to_date('" + dateFormatToString(dateStr) + "','yyyy-mm-dd hh24:mi:ss')";

    }

    public static String convertOracleDate(Date date) {
        return "to_date('" + dateFormatToString(date) + "','yyyy-mm-dd hh24:mi:ss')";
    }

    public static String convertOracleDate() {
        return convertOracleDate(new Date());
    }

    public static String dateFormatToString(Date date) {
        return new JDateTime(date).toString("YYYY-MM-DD hh:mm:ss");
    }

    public static String dateFormatToString(String dateStr) {
        return new JDateTime(dateStr).toString("YYYY-MM-DD hh:mm:ss");
    }

    public static String getXmlStr(String Url, String cs) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(Url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Language", "zh-cn");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(cs);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (Exception ex) {
                throw ex;
            }
        }
        return result;
    }

    public static String getQueryString(HttpServletRequest request) {
        String queryString = "";
        Map<String, String[]> params = request.getParameterMap();
        if (params.size() > 0) {
            for (String key : params.keySet()) {
                String values[] = params.get(key);
                for (String value : values) {
                    queryString += key + "=" + value + "&";
                }
            }
            if (queryString.endsWith("&")) {
                queryString = queryString.substring(0, queryString.length() - 1);
            }
        }
        return queryString;
    }

    public static String encodeConvert(String str) throws Exception {
        return new String(str.getBytes("gb2312"), "iso8859-1");
    }

    public static String decimalFormatToString(float f) {
        return new DecimalFormat("0.0000####").format(f);
    }

    public static int convertInt(String str) {
        if (StringUtil.isBlank(str)) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }

}
