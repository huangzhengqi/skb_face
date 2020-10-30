package com.fzy.admin.fp.common.util;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * @Author fyz123
 * @create 2020/7/13 9:30
 * @Description: EscPos工具类
 */
public class EscPosUtil {
    public static final byte ESC = 27;
    public static final byte FS = 28;
    public static final byte GS = 29;
    public static final byte DLE = 16;
    public static final byte EOT = 4;
    public static final byte ENQ = 5;
    public static final byte SP = 32;
    public static final byte HT = 9;
    public static final byte LF = 10;
    public static final byte CR = 13;
    public static final byte FF = 12;
    public static final byte CAN = 24;
    /**
     * CodePage table
     */
    public static class CodePage {
        public static final byte PC437       = 0;
        public static final byte KATAKANA    = 1;
        public static final byte PC850       = 2;
        public static final byte PC860       = 3;
        public static final byte PC863       = 4;
        public static final byte PC865       = 5;
        public static final byte WPC1252     = 16;
        public static final byte PC866       = 17;
        public static final byte PC852       = 18;
        public static final byte PC858       = 19;
    }

    /**
     * BarCode table
     */
    public static class BarCode {
        public static final byte UPC_A       = 0;
        public static final byte UPC_E       = 1;
        public static final byte EAN13       = 2;
        public static final byte EAN8        = 3;
        public static final byte CODE39      = 4;
        public static final byte ITF         = 5;
        public static final byte NW7         = 6;
        //public static final byte CODE93      = 72;
        public static final byte CODE128     = 73;
    }


    /**
     * Print and line feed
     * LF
     * @return bytes for this command
     */
    public static String printLinefeed()
    {
        return "$ESC_10";
    }

    /**
     * Turn underline mode on, set at 1-dot width
     * ESC - n
     * @return bytes for this command
     */
    public static String underline1DotOn()
    {
        return "$ESC_27_45_1";
    }

    /**
     * Turn underline mode on, set at 2-dot width
     * ESC - n
     * @return bytes for this command
     */
    public static String underline2DotOn()
    {
        return "$ESC_27_45_2";
    }

    /**
     * Turn underline mode off
     * ESC - n
     * @return bytes for this command
     */
    public static String underlineOff()
    {
        return "$ESC_27_45_0";
    }


    /**
     * Initialize printer
     * Clears the data in the print buffer and resets the printer modes to the modes that were
     * in effect when the power was turned on.
     * ESC @
     * @return bytes for this command
     */
    public static String initPrinter()
    {
        return "$ESC_27_64";
    }

    /**
     * Turn emphasized mode on
     * ESC E n
     * @return bytes for this command
     */
    public static String emphasizedOn()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0xF;
        return "$ESC_27_69_1";
    }

    /**
     * Turn emphasized mode off
     * ESC E n
     * @return bytes for this command
     */
    public static String emphasizedOff()
    {
        return "$ESC_27_69_0";
    }

    /**
     * double_strike_on
     * ESC G n
     * @return bytes for this command
     */
    public static String doubleStrikeOn()
    {
        return "$ESC_27_71_1";
    }

    /**
     * double_strike_off
     * ESC G n
     * @return bytes for this command
     */
    public static String doubleStrikeOff()
    {
        return "$ESC_27_71_0";
    }

    /**
     * Select Font A
     * ESC M n
     * @return bytes for this command
     */
    public static String selectFontA()
    {
        return "$ESC_27_77_0";
    }

    /**
     * Select Font B
     * ESC M n
     * @return bytes for this command
     */
    public static String selectFontB()
    {
        return "$ESC_27_77_1";
    }

    /**
     * Select Font C ( some printers don't have font C )
     * ESC M n
     * @return bytes for this command
     */
    public static String selectFontC()
    {
        return "$ESC_27_77_2";
    }

    /**
     * double height width mode on Font A
     * ESC ! n
     * @return bytes for this command
     */
//    public static String doubleHeightWidthOn()
//    {
//        return "$ESC_27_33_56";
//    }

    public static List<String> doubleHeightWidthOn()
    {
        return Arrays.asList("$ESC_27_33_56","$ESC_28_33_12");
    }

    /**
     * double height width mode off Font A
     * ESC ! n
     * @return bytes for this command
     */
    public static List<String> doubleHeightWidthOff()
    {
        return Arrays.asList("$ESC_27_33_0","$ESC_28_33_0");
    }

    /**
     * Select double height mode Font A
     * ESC ! n
     * @return bytes for this command
     */
    public static String doubleHeightOn()
    {
        return "$ESC_27_33_16";
    }

    /**
     * disable double height mode, select Font A
     * ESC ! n
     * @return bytes for this command
     */
    public static String doubleHeightOff()
    {
        return "$ESC_27_33_0";
    }

    /**
     * justification_left
     * ESC a n
     * @return bytes for this command
     */
    public static String justificationLeft()
    {
        return "$ESC_27_97_0";
    }

    /**
     * justification_center
     * ESC a n
     * @return bytes for this command
     */
    public static String justificationCenter()
    {
        return "$ESC_27_97_1";
    }

    /**
     * justification_right
     * ESC a n
     * @return bytes for this command
     */
    public static String justificationRight()
    {
        return "$ESC_27_97_2";
    }

    /**
     * Print and feed n lines
     * Prints the data in the print buffer and feeds n lines
     * ESC d n
     * @param n lines
     * @return bytes for this command
     */
    public static String printAndFeedLines(byte n)
    {
        return "$ESC_27_100_" + String.valueOf(n);
    }

    /**
     * Print and reverse feed n lines
     * Prints the data in the print buffer and feeds n lines in the reserve direction
     * ESC e n
     * @param n lines
     * @return bytes for this command
     */
    public static String printAndReverseFeedLines(byte n)
    {
        return "$ESC_27_101_" + String.valueOf(n);
    }

    /**
     * Drawer Kick
     * Drawer kick-out connector pin 2
     * ESC p m t1 t2
     * @return bytes for this command
     */
    public static String drawerKick()
    {
        return "$ESC_27_112_0_60_120";
    }

    /**
     * Select printing color1
     * ESC r n
     * @return bytes for this command
     */
    public static String selectColor1()
    {
        return "$ESC_27_114_0";
    }

    /**
     * Select printing color2
     * ESC r n
     * @return bytes for this command
     */
    public static String selectColor2()
    {
        return "$ESC_27_114_1";
    }

    /**
     * Select character code table
     * ESC t n
     * @param cp example:CodePage.WPC1252
     * @return bytes for this command
     */
    public static String selectCodeTab(byte cp)
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 116;
        result[2] = cp;
        return "$ESC_27_116_" + String.valueOf(cp);
    }

    /**
     * white printing mode on
     * Turn white/black reverse printing mode on
     * GS B n
     * @return bytes for this command
     */
    public static String whitePrintingOn()
    {
        return "$ESC_29_66_1";
    }

    /**
     * white printing mode off
     * Turn white/black reverse printing mode off
     * GS B n
     * @return bytes for this command
     */
    public static String whitePrintingOff()
    {
        return "$ESC_29_66_0";
    }

    /**
     * feed paper and cut
     * Feeds paper to ( cutting position + n x vertical motion unit )
     * and executes a full cut ( cuts the paper completely )
     * @return bytes for this command
     */
    public static String feedPaperCut()
    {
        return "$ESC_29_86_65_0";
    }

    /**
     * feed paper and cut partial
     * Feeds paper to ( cutting position + n x vertical motion unit )
     * and executes a partial cut ( one point left uncut )
     * @return bytes for this command
     */
    public static String feedPaperCutPartial()
    {
        return "$ESC_29_86_66_0";
    }

    /**
     * select bar code height
     * Select the height of the bar code as n dots
     * default dots = 162
     * @param dots ( heigth of the bar code )
     * @return bytes for this command
     */
    public static String barcodeHeight(byte dots)
    {
        return "$ESC_29_104_" + String.valueOf(dots);
    }

    /**
     * select font hri
     * Selects a font for the Human Readable Interpretation (HRI) characters when printing a barcode, using n as follows:
     * @param n
     *           Font
     *           0, 48 Font A
     *           1, 49 Font B
     * @return bytes for this command
     */
    public static String selectFontHri( byte n )
    {
        return "$ESC_29_102_" + String.valueOf(n);
    }

    /**
     * select position_hri
     * Selects the print position of Human Readable Interpretation (HRI) characters when printing a barcode, using n as follows:
     * @param n
     *           Print position
     *           0, 48 Not printed
     *           1, 49 Above the barcode
     *           2, 50 Below the barcode
     *           3, 51 Both above and below the barcode
     * @return bytes for this command
     */
    public static String selectPositionHri( byte n )
    {
        return "$ESC_29_72_" + String.valueOf(n);
    }

    /**
     * print bar code
     * @param barcode_typ( Barcode.CODE39, Barcode.EAN8 ,...)
     * @param barcode2print
     * @return bytes for this command
     */
    public static byte[] printBarcode(byte barcode_typ, String barcode2print )
    {
        byte[] barcodebytes = barcode2print.getBytes();
        byte[] result = new byte[3+barcodebytes.length+1];
        result[0] = GS;
        result[1] = 107;
        result[2] = barcode_typ;
        int idx = 3;

        for ( int i = 0; i < barcodebytes.length; i++ )
        {
            result[idx] = barcodebytes[i];
            idx++;
        }
        result[idx] = 0;

        return result;
    }

    public static String printBarcode39(String barcodeContent) {
        byte[] barcodeBytes = barcodeContent.getBytes();
        StringBuilder result = new StringBuilder("$ESC_29_107_4_");
        for (int i = 0; i < barcodeBytes.length; i++) {
            result.append(barcodeBytes[i]).append("_");
        }
        result.append("0");
        return result.toString();
    }


    /**
     * Set horizontal tab positions
     * @param col ( coulumn )
     * @return bytes for this command
     */
    public String setHTPosition( byte col )
    {
        return "$ESC_27_68_" + String.valueOf(col) + "_0";
    }

    /**
     * Print and line feed
     * LF
     * @return bytes for this command
     */
    public static byte[] print_linefeed()
    {
        byte[] result = new byte[1];
        result[0] = LF;
        return result;
    }

    /**
     * Turn underline mode on, set at 1-dot width
     * ESC - n
     * @return bytes for this command
     */
    public  static byte[] underline_1dot_on()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 45;
        result[2] = 1;
        return result;
    }

    /**
     * Turn underline mode on, set at 2-dot width
     * ESC - n
     * @return bytes for this command
     */
    public  static byte[] underline_2dot_on()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 45;
        result[2] = 2;
        return result;
    }

    /**
     * Turn underline mode off
     * ESC - n
     * @return bytes for this command
     */
    public  static byte[] underline_off()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 45;
        result[2] = 0;
        return result;
    }


    /**
     * Initialize printer
     * Clears the data in the print buffer and resets the printer modes to the modes that were
     * in effect when the power was turned on.
     * ESC @
     * @return bytes for this command
     */
    public  static byte[] init_printer()
    {
        byte[] result = new byte[2];
        result[0] = ESC;
        result[1] = 64;
        return result;
    }

    /**
     * Turn emphasized mode on
     * ESC E n
     * @return bytes for this command
     */
    public  static byte[] emphasized_on()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0xF;
        return result;
    }

    /**
     * Turn emphasized mode off
     * ESC E n
     * @return bytes for this command
     */
    public  static byte[] emphasized_off()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0;
        return result;
    }

    /**
     * double_strike_on
     * ESC G n
     * @return bytes for this command
     */
    public  static byte[] double_strike_on()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 71;
        result[2] = 0xF;
        return result;
    }

    /**
     * double_strike_off
     * ESC G n
     * @return bytes for this command
     */
    public  static byte[] double_strike_off()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 71;
        result[2] = 0xF;
        return result;
    }

    /**
     * Select Font A
     * ESC M n
     * @return bytes for this command
     */
    public  static byte[] select_fontA()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 77;
        result[2] = 0;
        return result;
    }

    /**
     * Select Font B
     * ESC M n
     * @return bytes for this command
     */
    public  static byte[] select_fontB()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 77;
        result[2] = 1;
        return result;
    }

    /**
     * Select Font C ( some printers don't have font C )
     * ESC M n
     * @return bytes for this command
     */
    public  static byte[] select_fontC()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 77;
        result[2] = 2;
        return result;
    }

    /**
     * double height width mode on Font A
     * ESC ! n
     * @return bytes for this command
     */
    public  static byte[] double_height_width_on()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 33;
        result[2] = 56;
        return result;
    }

    /**
     * double height width mode off Font A
     * ESC ! n
     * @return bytes for this command
     */
    public static  byte[] double_height_width_off()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 33;
        result[2] = 0;
        return result;
    }

    /**
     * Select double height mode Font A
     * ESC ! n
     * @return bytes for this command
     */
    public  static byte[] double_height_on()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 33;
        result[2] = 16;
        return result;
    }

    /**
     * disable double height mode, select Font A
     * ESC ! n
     * @return bytes for this command
     */
    public static  byte[] double_height_off()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 33;
        result[2] = 0;
        return result;
    }

    /**
     * justification_left
     * ESC a n
     * @return bytes for this command
     */
    public static  byte[] justification_left()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 97;
        result[2] = 0;
        return result;
    }

    /**
     * justification_center
     * ESC a n
     * @return bytes for this command
     */
    public  static byte[] justification_center()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 97;
        result[2] = 1;
        return result;
    }

    /**
     * justification_right
     * ESC a n
     * @return bytes for this command
     */
    public  static byte[] justification_right()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 97;
        result[2] = 2;
        return result;
    }

    /**
     * Print and feed n lines
     * Prints the data in the print buffer and feeds n lines
     * ESC d n
     * @param n lines
     * @return bytes for this command
     */
    public static byte[] print_and_feed_lines(byte n)
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 100;
        result[2] = n;
        return result;
    }

    /**
     * Print and reverse feed n lines
     * Prints the data in the print buffer and feeds n lines in the reserve direction
     * ESC e n
     * @param n lines
     * @return bytes for this command
     */
    public static byte[] print_and_reverse_feed_lines(byte n)
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 101;
        result[2] = n;
        return result;
    }

    /**
     * Drawer Kick
     * Drawer kick-out connector pin 2
     * ESC p m t1 t2
     * @return bytes for this command
     */
    public static byte[] drawer_kick()
    {
        byte[] result = new byte[5];
        result[0] = ESC;
        result[1] = 112;
        result[2] = 0;
        result[3] = 60;
        result[4] = 120;
        return result;
    }

    /**
     * Select printing color1
     * ESC r n
     * @return bytes for this command
     */
    public static byte[] select_color1()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 114;
        result[2] = 0;
        return result;
    }

    /**
     * Select printing color2
     * ESC r n
     * @return bytes for this command
     */
    public static byte[] select_color2()
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 114;
        result[2] = 1;
        return result;
    }

    /**
     * Select character code table
     * ESC t n
     * @param cp example:CodePage.WPC1252
     * @return bytes for this command
     */
    public static  byte[] select_code_tab(byte cp)
    {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 116;
        result[2] = cp;
        return result;
    }

    /**
     * white printing mode on
     * Turn white/black reverse printing mode on
     * GS B n
     * @return bytes for this command
     */
    public  static byte[] white_printing_on()
    {
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 66;
        result[2] = (byte)128;
        return result;
    }

    /**
     * white printing mode off
     * Turn white/black reverse printing mode off
     * GS B n
     * @return bytes for this command
     */
    public  static byte[] white_printing_off()
    {
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 66;
        result[2] = 0;
        return result;
    }

    /**
     * feed paper and cut
     * Feeds paper to ( cutting position + n x vertical motion unit )
     * and executes a full cut ( cuts the paper completely )
     * @return bytes for this command
     */
    public  static byte[] feedpapercut()
    {
        byte[] result = new byte[4];
        result[0] = GS;
        result[1] = 86;
        result[2] = 65;
        result[3] = 0;
        return result;
    }

    /**
     * feed paper and cut partial
     * Feeds paper to ( cutting position + n x vertical motion unit )
     * and executes a partial cut ( one point left uncut )
     * @return bytes for this command
     */
    public  static byte[] feedpapercut_partial()
    {
        byte[] result = new byte[4];
        result[0] = GS;
        result[1] = 86;
        result[2] = 66;
        result[3] = 0;
        return result;
    }

    /**
     * select bar code height
     * Select the height of the bar code as n dots
     * default dots = 162
     * @param dots ( heigth of the bar code )
     * @return bytes for this command
     */
    public  static byte[] barcode_height(byte dots)
    {
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 104;
        result[2] = dots;
        return result;
    }

    /**
     * select font hri
     * Selects a font for the Human Readable Interpretation (HRI) characters when printing a barcode, using n as follows:
     * @param n
     *           Font
     *           0, 48 Font A
     *           1, 49 Font B
     * @return bytes for this command
     */
    public  static byte[] select_font_hri( byte n )
    {
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 102;
        result[2] = n;
        return result;
    }

    /**
     * select position_hri
     * Selects the print position of Human Readable Interpretation (HRI) characters when printing a barcode, using n as follows:
     * @param n
     *           Print position
     *           0, 48 Not printed
     *           1, 49 Above the barcode
     *           2, 50 Below the barcode
     *           3, 51 Both above and below the barcode
     * @return bytes for this command
     */
    public  static byte[] select_position_hri( byte n )
    {
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 72;
        result[2] = n;
        return result;
    }

    /**
     * print bar code
     * @param barcode_typ( Barcode.CODE39, Barcode.EAN8 ,...)
     * @param barcode2print
     * @return bytes for this command
     */
    public  static byte[] print_bar_code(byte barcode_typ, String barcode2print )
    {
        byte[] barcodebytes = barcode2print.getBytes();
        byte[] result = new byte[3+barcodebytes.length+1];
//        byte[] result = new byte[3+barcodebytes.length];
        result[0] = GS;
        result[1] = 107;
        result[2] = barcode_typ;
        int idx = 3;

        for ( int i = 0; i < barcodebytes.length; i++ )
        {
            result[idx] = barcodebytes[i];
            idx++;
        }
        result[idx] = 0;

        return result;
    }

    /**
     * print bar code
     * @param barcode_typ( Barcode.CODE39, Barcode.EAN8 ,...)
     * @param barcode2print
     * @return bytes for this command
     */
    public  static byte[] print_bar_code128(byte barcode_typ, String barcode2print )
    {
        byte[] barcodebytes = barcode2print.getBytes();
        byte[] result = new byte[4+barcodebytes.length];
//        byte[] result = new byte[3+barcodebytes.length];
        result[0] = GS;
        result[1] = 107;
        result[2] = barcode_typ;
        result[3] = (byte) barcodebytes.length;
        /*result[4] = 123;
        result[5] =66;*/
        int idx = 4;

        for ( int i = 0; i < barcodebytes.length; i++ )
        {
            result[idx] = barcodebytes[i];
            idx++;
        }
        //result[idx] = 0;

        return result;
    }

    public static byte[] set_print_area_width(int leftMargin, int rightMargin )
    {

        byte[] result = new byte[4];
//        byte[] result = new byte[3+barcodebytes.length];
        result[0] = GS;
        result[1] = 87;
        result[2] = (byte) leftMargin;
        result[3] = (byte) rightMargin;
        return result;
    }

    public  static byte[] set_bar_code_width(int n)
    {

        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 119;
        result[2] = (byte) n;
        return result;
    }


    /**
     * Set horizontal tab positions
     * @param col ( coulumn )
     * @return bytes for this command
     */
    public  static byte[] set_HT_position( byte col )
    {
        byte[] result = new byte[4];
        result[0] = ESC;
        result[1] = 68;
        result[2] = col;
        result[3] = 0;
        return result;
    }

    public static byte[] set_chinese_on() {
        byte[] result = new byte[2];
        result[0] = FS;
        result[1] = 38;
        return result;
    }

    public static byte[] set_chinese_off() {
        byte[] result = new byte[2];
        result[0] = FS;
        result[1] = 46;
        return result;
    }

    public static byte[] set_chinese_super_on() {
        byte[] result = new byte[3];
        result[0] = FS;
        result[1] = 33;
        result[2] = 12;
        return result;
    }
    public static byte[] set_chinese_super_off() {
        byte[] result = new byte[3];
        result[0] = FS;
        result[1] = 33;
        result[2] = 0;
        return result;
    }

    public static void main(String[] args) throws IOException {
        String ip = "192.168.1.126";
        int port = 8888;
        String code = "1234567890";
        int skip = 2;

        Socket client=new java.net.Socket();
        OutputStreamWriter outSW = null;
        client.connect(new InetSocketAddress(ip,port),10000); // 创建一个 socket
//        socketWriter = new PrintWriter(client.getOutputStream());// 创建输入输出数据流
        outSW = new OutputStreamWriter(client.getOutputStream(), "GBK");
        BufferedWriter socketWriter = new BufferedWriter(outSW);
//        socketWriter.write(new String(set_chinese_on(), "UTF-8"));
        socketWriter.write(new String(justification_center(), "UTF-8"));
        socketWriter.write("***测试***\n");
        socketWriter.write("快来买吧\n");
        socketWriter.write(new String(justification_left(), "UTF-8"));
        socketWriter.write("订单号：170426543103\n");
        socketWriter.write("哈哈哈\n");
        socketWriter.write("-------------------------------------------\n");
        socketWriter.write(new String(emphasized_on(), "UTF-8"));
        socketWriter.write(new String(double_height_width_on(), "UTF-8"));
        socketWriter.write("367#085_1300\n");
        socketWriter.write(new String(double_height_width_off(), "UTF-8"));
        socketWriter.write(new String(emphasized_off(), "UTF-8"));
        socketWriter.write("测试人：王大帅哥\n");
        socketWriter.write("手机：13052235269\n");
        socketWriter.write(new String(emphasized_on(), "UTF-8"));
        socketWriter.write(new String(set_chinese_super_on(), "UTF-8"));
        socketWriter.write(new String(double_height_width_on(), "UTF-8"));
        socketWriter.write("你说我是不是帅哥\n");
        socketWriter.write(new String(double_height_width_off(), "UTF-8"));
        socketWriter.write(new String(set_chinese_super_off(), "UTF-8"));
        socketWriter.write(new String(emphasized_off(), "UTF-8"));

        socketWriter.write("\n\n");
        socketWriter.write(new String(set_bar_code_width(2), "UTF-8"));
        socketWriter.write(new String(print_bar_code128(BarCode.CODE128,"{Bcb7099132890012345"), "UTF-8"));
        socketWriter.write("\n");
        socketWriter.write("cb7099132890012345");

        for(int i=0;i<skip;i++){
            socketWriter.write("\n");
        }
        socketWriter.write(new String(feedpapercut(), "UTF-8"));
        System.out.println(socketWriter.toString());
//        socketWriter.flush();
//        socketWriter.close();
    }
}
