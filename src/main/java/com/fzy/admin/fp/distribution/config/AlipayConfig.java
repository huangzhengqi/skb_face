package com.fzy.admin.fp.distribution.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author yy
 * @Date 2019-11-15 14:39:34
 */
public class AlipayConfig {



    // 商户appid
    public static String APPID = "2019111469153319";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY ="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCCxnHlZk8ykyizwEaXWzUM+82PgrZHP+28en/zoxQvgnOT1HFgRjh/fojGBrbuguhofyqNJn8Sz1wBtXejPuXkHQjgDZkJ9MgPsWOMYrja63Wue95hY0WFoSz6TZc3I2OYSCvdz4dl6MzuL4wTsEZxtaori6rzTbR9dnEC6eiD0d65DcWB3Tj9kMdDO5cygYcor+sML2Qy+xNqy3V31qC2nTRUsdtvOjd8mIc6LW29Z05OqI+Iffmkk2RsOa6G1JBTg7gbhb2+CwFYlqxcJt0NQ56TgbLjOPWOYmhFwji70Mck4vEEhQZtK6w6rb7ZgrIlzy1iGG09fcV/9E7tyIWfAgMBAAECggEAdEW2bIz7a28V6EPTP61zTJjxGmDQaib32/VaoCdlwhwNYOmo6D1m7Ex3lQHRUd0IgChYj+kjYHkHR+mtdnJs+dWq13KfmA+QdQt2BBKeYt6o4jr17MbTwE0ebWnhQb/wRCwe/HcQ3lYPJtMVi0fWSjGIHHFwzCRRjp9pOXr5JYGDM6kL+Y5c/pc57e3lS/9DI7tP7wmLPVMRuJj7MkB4x6UkcvrmIVacOGH31J6ovyGkTJbE50gr5Ut2f0ujfD7YDwG18VER9/S3XppCkzO8Sr0RQ054SzcSDwRMK5/Zvdseub/oPbXhVksqJDXyJg/F0Z5enER8/0IpH2UjLmsboQKBgQDNIy8xc1j2GJUh1ZlbjlZchtr1dpgvC74mAg7ea6M18xG6+jFD3UANLbLdmemCiV6Ue5juKCcHSt5LiNPXsEHzNG4n6FpYvs7+PsvpNC4vziXfsLLKTwLjWEYDh1KgIZrug46g13oxA2o3oqhLjAaqwXfv4IAl2ZiT0YUYmf4ncQKBgQCjMzf0ibKho4HGYfGQXCmWtLH524uAmhx+tsza+ScHCGPXMkUwLcy2gaznMnr/VtZHXbwMQBd05hRz7gHfU6be1rwFcmZGGjYdXho+K3Z5xWz+fOHUDMYU7RYcMPxCLkci2mflCi4WEfx3RazTJ2BXkxF+ZyniYv1TT4AVtUiWDwKBgQCQyTSWAoPXrqITr17+cAgkZkpE1d+z1OXwjaNQ+UfI3Da3cb8z1wVOR+Lm5asapzpKMrJN5akmTIF5bqmWoqDptyfIowahfIs8h7YEcv/mXen9aAnJINs4+yTveKOowg9IKTKR84Qa8/4UJooCSGKsQsEUXbAVBu/Z5Re7tXTdAQKBgQCf2xGaJC6nCYOakGGPp9bM2d3jAduZclsq+1+Ztac2yk8RgRooYRQ+SLLWG6pC5goqCXSjAzx2ss+h5qZK3KN9bxTouO/ZBt+u0HggIW6BRJLIjAagMIruAHVB0/Gj2bSwPQK7ZMdTsvZ61fK9ObqyzUm7aM2sBI5ixmKN4O5inQKBgQC5nAs6ZXyXRVm6L1sv6DWrOXnBaRvhYHI/JkQHMKH6iKih1kUZvBALnZW9Gm2esuMh4cBwoP/HAMaAbpvB9WrYnh+m1m9jcK6Joj2oPKej2vR6rGvrV1w6K2DZr9cd25yaAmKPa0Q6BtElKAjcI0tXXOt8cXi7IQkpUs1SCi2nXA==";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsqsHbCN1NeWSbsN24delBeNrDMR3LctYeY7Z+3C/jrpaHLgWYHfmDESOzyCzbQYYJtP/u0OVyvIw6Hrz/pkNKFeNnz+WBzynzuR6A4TscliZIlP3YKsi8WTZHCJexhmVxmKkqr5l80c0SgoRFOx5/RlL80329kLT7CkiocDVeqEPr0b2kaaVMh9MBuU/HfnubWKnwVqDNwUvhweNn1a7N7UOENsSvYKHXgNFRH9djCaLZCZCdbwYmN9teQXOLeFgOTN2be85shiUWZEfkhRej7IGpT7tlOWkjUIj3pZRDHYUwIXkgnSIsA5PuNSGq+CuBX/+1d/hxKrYb6BLKgbK5QIDAQAB";

   //public static String notify_url = "https://oem.weiliangjian.com/dist/app/pay/notify";
    //public static String return_url = "https://oem.weiliangjian.com/dist/app/pay/alipay/return";

    // 请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
     // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
