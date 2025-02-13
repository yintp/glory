package com.yintp.spring.framework.annotation.test;

import com.yintp.spring.framework.annotation.aop.MathCalculator;
import com.yintp.spring.framework.annotation.config.AopConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AopConfigTest {

    private AnnotationConfigApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new AnnotationConfigApplicationContext(AopConfig.class);
    }

    @Test
    public void testAop() {
        MathCalculator mathCalculator = (MathCalculator) applicationContext.getBean("mathCalculator");
        mathCalculator.div(1, 1);
//        mathCalculator.div(1, 0);
        applicationContext.close();
    }

    @Test
    public void testResource() throws Exception {
        Resource resource = new ClassPathResource("a/test");
        printContent(resource.getInputStream());
    }

    private void printContent(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line=br.readLine()) != null) {
            System.out.println(line);
        }
        if (is != null) {
            is.close();
        }
        if (br != null) {
            br.close();
        }
    }
}
