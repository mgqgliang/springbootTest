package com.example.springboottest.util.export;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

import java.util.ArrayList;
import java.util.List;

public class ExcelImportDataListener<T> implements ReadListener<T> {

    /**
     * 缓存的数据
     */
    private List<T> tList = new ArrayList<>();


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param t    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T t, AnalysisContext context) {
        tList.add(t);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    public List<T> getList(){
        return this.tList;
    }

    public void setList(List<T> tList){
        this.tList = tList;
    }


}
