package cn.linc;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig3
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092800615716";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCfX63slxqRrqUtdem/WbADlQTHwSx3ULg2Xb19Xx9GgUUsu7MLVuwzsIJZIjOR3axae1MKphMIoeqNaSv5cH2DkwI40c4BrD3MBYcYLry9MQISsUxdIoEi3FbRTdvXZz61UXvQC9/cHTZmb4nnQcQyCUeEDid2+XwkTs0SRQmZHrRJQNsHdXB2uwht6V6v2NMtlU4okUp1SShafNZjFZlSTmGDbQjCxKhzWxPG+tXnRImUs9LNWFQn/c+7yD8FS1eulJXZxH0HmgszB5gs+5JmlO0b0VvsbrmNZovyl/aKEsrczvZxMJifzBz8eqIGAfOoDGvsk7pIPsgqwLkqvIoFAgMBAAECggEAM5BtYWLyQbhT0QWZf0OxYzdPuKGTlXcUYuKpcGraX6FrlDGWmFCjU4YEZnpeXQ9vuEa2c3eQKPbaQK6Kf8YZlfx2d5PKJ1hnFh+juftFLaExnoKmxmtnhLPtkwKx4luROnEGsiVlZH4/H51u7NemdtnxvZQmyKOnuseUKhkUJen6IQTvFeT6q89vvWCG9ZorrEfe3EhRvr/xLWpbDuIaxDLlolxp9XwCv5JIlEGrZQzEJemio2Yy9hsgnNGNEgZeM57/7cHzYkjTZuI5Sxq5bs7G8Ljssz0C+teKyOg9H9WloD6H2MDzwMKpx4EqGUwt5PtcRWFlkXgABx+mXmrNAQKBgQDa9mHf822hSbFUl6onE+wv0W2WwZmL5A+J4wo924opY1hsFpm1RgcKuV7u/yQQuUbzw18+Do/xNdYEjtyxfeYxRkSQDH8DZoP0Uo1Po4JLyKNEZML93cb8ilINic1qwPC0OIiIRffNMLILmBR0iiI5/v+bd9RaHL2i2QWvcyoI9QKBgQC6VPV746Pu2LF6HgG8iIuaQFgRXqvaGvvm11B+0qA7RDnJtHedQXYwNBrbJtobTRbT8Cr+is9DjUt/W0FMSlwd1ew0+Yh94EyLEqJdPRMIly4yc7zVABkzFvI8DLVq5bzAqGDMDwJc0qcA/qi0rIaJyccEHtCIREJ98nPu12IS0QKBgQDC5nKCZGzeFvlYgdN8Czi9652vhk5Ffc/y/XvdSbB2XK6VL5zMBTQ5cH03ITYaCMFGM05wr4eivjftQLji6Fzg2VooGmdoQHUOIfkUvUc6a7IgK8yBKkp3+itFT7bf4jOfR38IRbdoFoM03L7AaUBD/V3KJeSH/XMzJBfybwEojQKBgFSB0bA1hxT9TEjBMTJrjz0PL6H5GOHbWSHRx10tc6RNhhP3+EPm+45TI1b/YO3+4XqPs0AsfmH5xCLnpU7PbsXNTsqb3Y6RWsW8Vs0ItQgxYK9bFGpaGBO2tXgMn37y4M9TzkreB8lHdJyb4rHGiWIWlF9HBna21p0+h9vr7TvxAoGAbU5LwJG8L73G3dmxX+iBoVBIlGZOd1JKoyjdWGeVHGwhgvGgL3yDQeAiTaYGysOquAgZiPMQxgcXOZQ4y79VlnARHEne8QgzlpKkg14x5XE1QMsBFXlOlBXBWFgwHFuUl/yEk/2rvMswIiG32I0wsu3jFIGD1JG+f1bg6ckJh7A=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn1+t7Jcaka6lLXXpv1mwA5UEx8Esd1C4Nl29fV8fRoFFLLuzC1bsM7CCWSIzkd2sWntTCqYTCKHqjWkr+XB9g5MCONHOAaw9zAWHGC68vTECErFMXSKBItxW0U3b12c+tVF70Avf3B02Zm+J50HEMglHhA4ndvl8JE7NEkUJmR60SUDbB3VwdrsIbeler9jTLZVOKJFKdUkoWnzWYxWZUk5hg20IwsSoc1sTxvrV50SJlLPSzVhUJ/3Pu8g/BUtXrpSV2cR9B5oLMweYLPuSZpTtG9Fb7G65jWaL8pf2ihLK3M72cTCYn8wc/HqiBgHzqAxr7JO6SD7IKsC5KryKBQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

