package cn.linc;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * 描述:
 * 阿里支付
 *
 * @author xiechenglin
 * @create 2019-02-26 17:22
 */
public class Alipay {

    @Test
    public void testPay() throws Exception{
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String("dfgsgwe1241242312df");
        //付款金额，必填
        String total_amount = new String("0.1");
        //订单名称，必填
        String subject = new String("ali测试");
        //商品描述，可空
        String body = new String("东西不错");

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //输出
        System.out.println(result);
    }

    @Test
    public void testPay1() throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求体
        AlipayTradePayRequest alipayTradePayRequest = new AlipayTradePayRequest();
        BizContent bizContent = new BizContent();
        bizContent.setOut_trade_no(UUID.randomUUID().toString().replace("-",""));
        bizContent.setBody("java代码编写测试");
        bizContent.setSubject("小试牛刀");
        bizContent.setProduct_code("FAST_INSTANT_TRADE_PAY");
        bizContent.setTotal_amount("0.02");
        //将请求体转成json
        String content = JsonSerializeUtil.jsonSerializerNoType(bizContent);
        alipayTradePayRequest.setBizContent(content);
        //请求
        String result = alipayClient.pageExecute(alipayTradePayRequest).getBody();
        //输出
        System.out.println(result);
    }


}
