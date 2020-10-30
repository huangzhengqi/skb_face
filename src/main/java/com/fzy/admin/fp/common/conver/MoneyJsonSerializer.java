package com.fzy.admin.fp.common.conver;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fzy.admin.fp.common.web.ParamUtil;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-07-03 20:03
 * @description
 */
public class MoneyJsonSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (ParamUtil.isBlank(bigDecimal)) {
            jsonGenerator.writeString("0");
        } else {
            jsonGenerator.writeString(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString());
        }
    }
}
