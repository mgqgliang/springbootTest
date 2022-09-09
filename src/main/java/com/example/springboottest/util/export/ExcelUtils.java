package com.example.springboottest.util.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.springboottest.bean.Student;
import com.example.springboottest.common.FailResultException;
import com.example.springboottest.common.commonstatic.CommonCode;
import com.example.springboottest.common.commonstatic.CommonMessage;
import org.apache.commons.collections.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelUtils<T> {

    public static final String path = "D:\\export\\";

    /**
     * 导出Excel
     *
     * @param exportVos 导出信息
     * @param fileName  导出文件名称
     * @throws FailResultException
     */
    public void exportExcel(List<ExportVo> exportVos, String fileName) throws FailResultException {
        if (StringUtils.isEmpty(fileName)) {
            throw new FailResultException(CommonCode.CODE_60001, CommonMessage.EXPORT_NO_FILENAME);
        }
        if (CollectionUtils.isNotEmpty(exportVos)) {
            //根据表单号排序
            exportVos = exportVos.stream()
                    .sorted(Comparator.comparing(ExportVo::getSheetNo))
                    .collect(Collectors.toList());
            //导出排序后的表单信息
            try (ExcelWriter excelWriter =
                         EasyExcel.write(path + fileName + ".xlsx")
                                 .build()) {
                exportVos.stream().forEach(m -> {
                    WriteSheet writeSheet = EasyExcel.writerSheet(m.getSheetName())
                            .head(m.getPropertyClass())
                            .build();
                    excelWriter.write(m.getTList(), writeSheet);
                });
            } catch (ExcelCommonException e) {
                throw new FailResultException(CommonCode.CODE_60001, CommonMessage.IMOPRT_NO_FILENAME);
            }

        }
    }

    public List<T> importExcel(String fileName, Class clazz, int sheetNo){
        ExcelImportDataListener<T> importDataListener = new ExcelImportDataListener();
        ExcelReader excelReader = EasyExcel.read(fileName, importDataListener).build();
        ReadSheet readSheet = EasyExcel.readSheet(sheetNo)
                .head(clazz)
                .registerReadListener(new ExcelImportDataListener<T>())
                .build();
        excelReader.read(readSheet);
        List<T> tList = importDataListener.getList();
        excelReader.finish();
        return tList;
    }
}
