package com.example.springboottest.common;

import com.example.springboottest.common.commonstatic.CommonCode;
import com.example.springboottest.common.commonstatic.CommonMessage;
import lombok.Data;

import java.util.List;

@Data
public class CommonPageResult<T> extends CommonResult{

    private Integer totalPages = 0;

    private Integer totalRows = 0;

    public static <T> CommonPageResult<T> result(Boolean status, CommonCode code, CommonMessage message,
                             List<T> resultList, Integer totalPages, Integer totalRows) {
        CommonPageResult<T> pageResult = new CommonPageResult<T>();
        pageResult.setStatus(status);
        pageResult.setCode(code.value);
        pageResult.setMessages(message.message);
        pageResult.setResultList(resultList);
        if(totalPages != null && totalPages != 0){
            pageResult.setTotalPages(totalPages);
            pageResult.setTotalRows(totalRows);
        }
        return pageResult;
    }
}
