package com.fzy.admin.fp.codegen;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Date;

/**
 * 生成二维码链接EXCEL
 */
@Slf4j
public class QrcodeTest {
    public static void main(String [] a)throws Exception{
        //创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet1");
        //设置单元格的值
        String qrs="http://oem.weiliangjian.com/merchant/qrcode/app/get_qrcode?id=";
        int num=800000;
        for(int i=1;i<11000;i++){
            String qrcode="SKT2020";
            int inNum=num+i;
            String m= String.valueOf(inNum);
            HSSFRow row = sheet.createRow(i);
            //输出
            row.createCell(0).setCellValue(qrs+qrcode+m.replace("4","f"));
        }
        System.out.println("生成成功");
        //输出Excel文件
        FileOutputStream output=new FileOutputStream("D:/workbook2.xls");
        wb.write(output);
        output.close();
    }
}
