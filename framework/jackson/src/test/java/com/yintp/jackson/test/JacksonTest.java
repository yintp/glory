package com.yintp.jackson.test;

import com.yintp.jackson.JsonDateDeserializer;
import com.yintp.jackson.JsonDateSerializer;
import com.yintp.jackson.PersonBean;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 需求：设置一个全局时间反序列化处理器来处理时间对象，但是对于个性化的处理不想单独写一个反序列化处理器，而是想通过@JsonFormat来实现
 * 问题：当设置全局反序列化来处理时间对象时，此时如果想用@JsonFormat来另外解析时间不起作用，也就是说处理器优先于@JsonFormat和全局的JsonFormat
 */
public class JacksonTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        // 设置全局JSON时间格式转换
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(myDateFormat);
        // 设置全局自定义Date Deserializer、Serializer
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new JsonDateDeserializer());
        module.addSerializer(Date.class, new JsonDateSerializer());
        objectMapper.registerModule(module);
        // 支持jdk8时间类
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testJsonFormatAnnotation() throws IOException {
        PersonBean person = new PersonBean();
        person.setBirthDay(new Date());
        String pJson = objectMapper.writeValueAsString(person);
        System.out.println("person json: " + pJson);
        String json = "{\"birthDay\":\"19921125\"}";
        PersonBean personBean = objectMapper.readValue(json, PersonBean.class);
        System.out.println("personBean birthDay: " + personBean.getBirthDay());
    }

    @Test
    public void testMapperDateFormat() throws IOException {
        PersonBean person = new PersonBean();
        person.setWeddingDay(new Date());
        person.setBirthDay(new Date());
        String pJson = objectMapper.writeValueAsString(person);
        System.out.println("person json: " + pJson);
        String json = "{\"birthDay\":\"19921125\",\"weddingDay\":\"2018-08-24\"}";
        PersonBean personBean = objectMapper.readValue(json, PersonBean.class);
        System.out.println("personBean birthDay: " + personBean.getBirthDay());
    }

    @Test
    public void testDeserialize() throws IOException {
        String json = "{\"birthDay\":\"19921125\",\"weddingDay\":\"2018-08-24 16:55:08\"}";
        PersonBean personBean = objectMapper.readValue(json, PersonBean.class);
        System.out.println("personBean birthDay: " + personBean.getWeddingDay());
    }

    @Test
    public void testJsonDeserializeForAnnotation() throws IOException {
        String json = "{\"birthDay\":\"19921125\",\"memorialDay\":\"2018/08/24 16:38\"}";
        PersonBean personBean = objectMapper.readValue(json, PersonBean.class);
        System.out.println("personBean birthDay: " + personBean.getWeddingDay());
    }

    @Test
    public void testJsonSerialize() throws IOException {
        PersonBean person = new PersonBean();
        person.setMomDate(new Date());
        String pJson = objectMapper.writeValueAsString(person);
        System.out.println("person json: " + pJson);
    }

    @Test
    public void testJsonSerializeForAnnotation() throws IOException {
        PersonBean person = new PersonBean();
        person.setMomDate(new Date());
        String pJson = objectMapper.writeValueAsString(person);
        System.out.println("person json: " + pJson);
    }

    @Test
    public void testJsonPropertyForAnnotation() throws IOException {
        String json = "{\"user_name\":\"张三\"}";
        PersonBean personBean = objectMapper.readValue(json, PersonBean.class);
        System.out.println("personBean name: " + personBean.getName());
    }
}
