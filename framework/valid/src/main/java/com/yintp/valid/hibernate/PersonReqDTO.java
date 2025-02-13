package com.yintp.valid.hibernate;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

public class PersonReqDTO {

    @NotNull(message = "身份证号不能为空")
    private String idNo;
    @Size(max = 2, message = "姓名大小不能超过2")
    private String idName;
    @Min(value = 0, message = "年龄需要大于0")
    private Integer age;
    @Valid
    private BirthDay birthDay;
    @Pattern(regexp = "^\\d{13}$", message = "手机号为13位")
    private String phone;
    @Email(message = "请输入正确的邮箱")
    private String email;
    @Length(max = 10, message = "签名长度不超过10")
    private String sign;
    @Digits(integer = 10, fraction = 2, message = "工资格式错误")
    private BigDecimal salary;
    @StringArray(value = {"M", "W", "N"}, message = "性别必须是M、W、N之中的一个")
    private String sex;

    public static class BirthDay {

        private Integer year;
        @Range(min = 1, max = 12, message = "月份格式错误")
        private Integer month;
        @Range(min = 1, max = 31, message = "月份格式错误")
        private Integer day;
        @Past(message = "生日必须是过去的日期")
        private Date birthDay;
        @Future(message = "毁灭日必须是未来的日期")
        private Date doomsday;

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public Date getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(Date birthDay) {
            this.birthDay = birthDay;
        }

        public Date getDoomsday() {
            return doomsday;
        }

        public void setDoomsday(Date doomsday) {
            this.doomsday = doomsday;
        }
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BirthDay getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(BirthDay birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
