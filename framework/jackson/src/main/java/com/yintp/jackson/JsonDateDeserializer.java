package com.yintp.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class JsonDateDeserializer extends JsonDeserializer<Date> {

    private static final String[] pattern = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd",
            "yyyyMMdd"};

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Date result = null;
        String dateStr = p.getText();
        if (StringUtils.isNotEmpty(dateStr)) {
            try {
                result = DateUtils.parseDate(dateStr, pattern);
            } catch (ParseException e) {
            }
            if (result == null) {
                long timestamp = p.getValueAsLong();
                if (timestamp > 0) {
                    int length = String.valueOf(timestamp).length();
                    if (length == 10) {
                        result = new Date(timestamp * 1000);
                    }
                    if (length == 13) {
                        result = new Date(timestamp);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Class<Date> handledType() {
        return Date.class;
    }
}
