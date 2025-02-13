package com.yintp.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class PersonBean {

    @JsonFormat(pattern = "yyyyMMdd")
    private Date birthDay;
    private Date weddingDay;
    @JsonDeserialize(using = JsonDateDeserializerForAnnotation.class)
    private Date memorialDay;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date momDate;

    @JsonProperty("user_name")
    private String name;

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getWeddingDay() {
        return weddingDay;
    }

    public void setWeddingDay(Date weddingDay) {
        this.weddingDay = weddingDay;
    }

    public Date getMemorialDay() {
        return memorialDay;
    }

    public void setMemorialDay(Date memorialDay) {
        this.memorialDay = memorialDay;
    }

    public Date getMomDate() {
        return momDate;
    }

    public void setMomDate(Date momDate) {
        this.momDate = momDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
