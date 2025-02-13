package com.yintp.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateDeserializerForAnnotation extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Date result = null;
        String dateStr = p.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if (StringUtils.isNotEmpty(dateStr)) {
            try {
                result = sdf.parse(dateStr);
            } catch (ParseException e) {
            }
        }
        return result;
    }

    @Override
    public Class<Date> handledType() {
        return Date.class;
    }
}
