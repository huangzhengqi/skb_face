package com.fzy.admin.fp.invoice.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class MyMappingJackson2XmlHttpMessageConverter extends MappingJackson2XmlHttpMessageConverter {

    public MyMappingJackson2XmlHttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();

        mediaTypes.add(MediaType.TEXT_HTML);  //加入text/html类型的支持

        setSupportedMediaTypes(mediaTypes);
    }

}
