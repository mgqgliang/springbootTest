package com.example.springboottest.util;

import com.example.springboottest.common.commonstatic.CommonKuaidi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;


public class KdniaoSubscribeAPI {

    /**
     * 物流地图跟踪信息
     * @param postageCode 快递单号
     * @param postageCompany 快递公司名称
     * @param tel 收/寄件人电话
     * @param fromCity 发件城市
     * @param toCity 收件城市
     * @return 信息
     * @throws Exception
     */
    public String orderForMapOnlineByJson(String postageCode, String postageCompany, String tel, String fromCity, String toCity) throws Exception{
        String companyName = KdniaoSubscribeAPI.getPostCompany(postageCompany);
        String requestData = "{'LogisticCode':'" + postageCode
                + "','ShipperCode':'" + companyName
                + "','SenderCityName':'" + fromCity
                + "','ReceiverCityName':'" + toCity
                + "','IsReturnCoordinates':1"
                + ",'IsReturnRouteMap':1"
                + ",'CustomerName':";
        if ("SF".equals(postageCompany)) {
            requestData += "'" + tel.substring(tel.length() - 4) + "'}";
        } else {
            requestData += "''}";
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", CommonKuaidi.KDN_EBUSINESSID.value);
        params.put("RequestType", CommonKuaidi.KDN_REQUEST_TYPE.value);
        String dataSign=encrypt(requestData, CommonKuaidi.KDN_APPKEY.value, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result=sendPost(CommonKuaidi.KDN_REQURL.value, params);
        result = result.replace("result", "");

        return result;
    }
    /**
     *
     * @param postageCode
     *            快递单号
     * @param postageCompany
     *            快递公司名称
     * @param tel
     *            收/寄件人手机号码（仅顺丰快递需要,其他快递不用传，传了也没用）
     * @return String 快递信息，无需处理直接返回给前台。
     * @throws Exception
     */
    public String orderTracesSubByJson(String postageCode, String postageCompany, String tel) throws Exception {
        String companyName = getPostCompany(postageCompany);
        String requestData = "{'LogisticCode':'" + postageCode + "','ShipperCode':'" + companyName
                + "','CustomerName':";
        if ("SF".equals(postageCompany)) {
            requestData += "'" + tel.substring(tel.length() - 4) + "'}";
        } else {
            requestData += "''}";
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", CommonKuaidi.KDN_EBUSINESSID.value);
        params.put("RequestType", CommonKuaidi.KDN_REQUEST_TYPE.value);
        String dataSign = encrypt(requestData, CommonKuaidi.KDN_APPKEY.value, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = sendPost(CommonKuaidi.KDN_REQURL.value, params);

        // 根据公司业务处理返回的信息......

        return result;
    }

    /**
     * MD5加密
     *
     * @param str
     *            内容
     * @param charset
     *            编码方式
     * @throws Exception
     */
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     *
     * @param str
     *            内容
     * @param charset
     *            编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     *
     * @param content
     *            内容
     * @param keyValue
     *            Appkey
     * @param charset
     *            编码方式
     * @throws UnsupportedEncodingException
     *             ,Exception
     * @return DataSign签名
     */
    private String encrypt(String content, String keyValue, String charset)
            throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param params
     *            请求的参数集合
     * @return 远程资源的响应结果
     */
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
                System.out.println("param:" + param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    public static String getPostCompany(String companyName) {
        if ("顺丰快递".equals(companyName)) {
            return CommonKuaidi.KDN_SF.value;
        }
        if ("中通快递".equals(companyName)) {
            return CommonKuaidi.KDN_ZTO.value;
        }
        if ("圆通快递".equals(companyName)) {
            return CommonKuaidi.KDN_YTO.value;
        }
        if ("韵达快递".equals(companyName)) {
            return CommonKuaidi.KDN_YD.value;
        }
        if ("邮政快递".equals(companyName)) {
            return CommonKuaidi.KDN_YZPY.value;
        }
        if ("特级送".equals(companyName)) {
            return CommonKuaidi.KDN_TJS.value;
        }
        if ("快捷快递".equals(companyName)) {
            return "";
        }
        if ("天天快递".equals(companyName)) {
            return CommonKuaidi.KDN_HHTT.value;
        }
        if ("国通快递".equals(companyName)) {
            return "";
        }
        if ("安能物流".equals(companyName)) {
            return CommonKuaidi.KDN_ANE.value;
        }
        if ("佳吉物流".equals(companyName)) {
            return CommonKuaidi.KDN_CNEX.value;
        }
        if ("百世汇通".equals(companyName)) {
            return CommonKuaidi.KDN_HTKY.value;
        }
        if ("申通快递".equals(companyName)) {
            return CommonKuaidi.KDN_STO.value;
        }
        return null;
    }
}

