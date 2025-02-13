package com.yintp.spring.framework.annotation.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * SpEL：Spring内置的表达式语言，语法与OGNL等其他表达式语言十分类似。SpEL设计之初就是朝着做一个表达式语言的通用框架，可以独立运行。
 * SpEL对表达式语法解析过程进行了很高的抽象，抽象出解析器、表达式、解析上下文、估值(Evaluate)上下文等对象，非常优雅的表达了解析逻辑。主要的对象如下：
 *  - ExpressionParser：表达式解析器接口，包含了(Expression) parseExpression(String), (Expression) parseExpression(String, ParserContext)两个接口方法
 *  - ParserContext：解析器上下文接口，主要是对解析器Token的抽象类，包含3个方法：getExpressionPrefix,getExpressionSuffix和isTemplate，就是表示表达式从什么符号开始什么符号结束，是否是作为模板（包含字面量和表达式）解析。一般保持默认。
 *  - Expression：表达式的抽象，是经过解析后的字符串表达式的形式表示。通过expressionInstance.getValue方法，可以获取表示式的值。也可以通过调用getValue(EvaluationContext)，从评估（evaluation)上下文中获取表达式对于当前上下文的值
 *  - EvaluationContext：估值上下文接口，只有一个setter方法：setVariable(String, Object)，通过调用该方法，可以为evaluation提供上下文变量
 * spring官方文档地址：https://docs.spring.io/spring/docs/4.3.12.RELEASE/spring-framework-reference/htmlsingle/#expressions-ref-literal
 *
 * #{…} 用于执行SpEl表达式，并将内容赋值给属性
 * ${…} 主要用于加载外部属性文件中的值
 * #{…} 和${…} 可以混合使用，但是必须#{}外面，${}在里面。spring执行${}是时机要早于#{}
 */
public class Banana {

    /**
     * 文字表达式
     */
    @Value("#{ 'Hello World' }")
    private String str;
    @Value("#{ 6.0221415E+23 }")
    private double salary;
    @Value("#{ 0x7FFFFFFF }")
    private int maxValue;
    @Value("#{ true }")
    private boolean trueValue;
    @Value("#{ null }")
    private Object nullValue;
    /**
     * 调用String的concat方法
     */
    @Value("#{ 'Hello World'.concat('!') }")
    private String stringMethod;
    /**
     * 调用JavaBean的属性，如这里实际是调用getBytes()方法
     */
    @Value("#{ T(java.util.Arrays).toString('Hello World'.bytes) }")
    private String javaBeanProperties;
    /**
     * 访问对象公共属性
     */
    @Value("#{ 'Hello World'.bytes.length }")
    private long publicAttr;
    /**
     * 获取People的name值，比较name值是不是hry
     */
    @Value("#{ man.name }")
    private String objName;
    /**
     * 比较name值是不是张三，结果为boolean
     */
    @Value("#{ man.name=='张三' }")
    private boolean objNamecmp;
    @Value("#{ '${man.nickName}' }")
    private String nickName;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isTrueValue() {
        return trueValue;
    }

    public void setTrueValue(boolean trueValue) {
        this.trueValue = trueValue;
    }

    public Object getNullValue() {
        return nullValue;
    }

    public void setNullValue(Object nullValue) {
        this.nullValue = nullValue;
    }

    public String getStringMethod() {
        return stringMethod;
    }

    public void setStringMethod(String stringMethod) {
        this.stringMethod = stringMethod;
    }

    public String getJavaBeanProperties() {
        return javaBeanProperties;
    }

    public void setJavaBeanProperties(String javaBeanProperties) {
        this.javaBeanProperties = javaBeanProperties;
    }

    public long getPublicAttr() {
        return publicAttr;
    }

    public void setPublicAttr(long publicAttr) {
        this.publicAttr = publicAttr;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public boolean isObjNamecmp() {
        return objNamecmp;
    }

    public void setObjNamecmp(boolean objNamecmp) {
        this.objNamecmp = objNamecmp;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Banana{" +
                "str='" + str + '\'' +
                ", salary=" + salary +
                ", maxValue=" + maxValue +
                ", trueValue=" + trueValue +
                ", nullValue=" + nullValue +
                ", stringMethod='" + stringMethod + '\'' +
                ", javaBeanProperties='" + javaBeanProperties + '\'' +
                ", publicAttr=" + publicAttr +
                ", objName='" + objName + '\'' +
                ", objNamecmp=" + objNamecmp +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
