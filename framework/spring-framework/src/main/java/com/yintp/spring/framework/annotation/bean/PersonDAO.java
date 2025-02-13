package com.yintp.spring.framework.annotation.bean;

import org.springframework.stereotype.Repository;

@Repository
public class PersonDAO {
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "PersonDAO{" +
                "label='" + label + '\'' +
                '}';
    }
}
