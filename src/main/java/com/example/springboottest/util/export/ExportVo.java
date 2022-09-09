package com.example.springboottest.util.export;

import lombok.Data;

import java.util.List;
@Data
public class ExportVo<T> {

    public ExportVo(int sheetNo,String sheetName,Class propertyClass,List<T> tList){
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
        this.propertyClass = propertyClass;
        this.tList = tList;
    }
    public ExportVo(String sheetName,Class propertyClass,List<T> tList){
        this.sheetName = sheetName;
        this.propertyClass = propertyClass;
        this.tList = tList;
    }
    int sheetNo;
    /**
     * 表单名称
     */
    String sheetName;
    /**
     * 导出信息类(T)的class
     */
    Class propertyClass;
    /**
     * 导出信息
     */
    List<T> tList;
}
