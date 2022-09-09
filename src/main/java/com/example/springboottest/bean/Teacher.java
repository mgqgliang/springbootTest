package com.example.springboottest.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Teacher {
    @ExcelProperty("年龄")
    Integer age;
    @ExcelProperty("姓名")
    String name;
    @ExcelProperty("科室")
    String teach;
}
