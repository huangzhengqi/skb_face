package com.fzy.admin.fp.member.coupon.util;

/**
 * @author lb
 * @date 2019/6/4 15:47
 * @Description 生成二维码
 */
/*
public class QRUtil {
    public static String getMsg(String merchantId,String couponId){
        QrConfig config=new QrConfig();
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        config.setHeight(400);
        config.setWidth(400);
        String qrcode= RandomUtil.randomString(13);
        String path="WorkDir/temp/lypay/qr/"+qrcode+".jpg";
        File file=new File(path);
        File fileParent=file.getParentFile();
        if (!fileParent.exists()){
            try{
                fileParent.mkdirs();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        QrCodeUtil.generate("https://www.qq.com/?merchantId="+merchantId+"&couponId="+couponId+"",config, FileUtil.file(path));
        String image="qr/"+qrcode+".jpg";
        return image;
    }
}
*/
