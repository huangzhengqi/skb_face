package com.fzy.admin.fp.wx.util;

/**
 * @Author hzq
 * @Date 2020/9/9 15:24
 * @Version 1.0
 * @description
 */
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

/**
 * 处理CDATA[]
 *
 * @author cl
 * @since 2017年11月24日-上午1:03:39
 */
public class CDataContentHandler extends XMLWriter {

    public CDataContentHandler(Writer writer){
        super(writer);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
//		boolean useCData = XML_CHARS.matcher(new String(ch, start, length)).find();
        boolean useCData = true;
        if (useCData) {
            super.startCDATA();
        }
        super.characters(ch, start, length);
        if (useCData) {
            super.endCDATA();
        }
    }

}
