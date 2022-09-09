package com.example.springboottest;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.springboottest.bean.Student;
import com.example.springboottest.bean.Teacher;
import com.example.springboottest.common.FailResultException;
import com.example.springboottest.dao.ProjectFileDao;
import com.example.springboottest.entity.ProjectFileEntity;
import com.example.springboottest.service.HelloWorldService;
import com.example.springboottest.po.ProjectFilePo;
import com.example.springboottest.util.export.ExcelImportDataListener;
import com.example.springboottest.util.export.ExcelUtils;
import com.example.springboottest.util.HttpUtils;
import com.example.springboottest.util.export.ExportVo;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Slf4j
class SpringbootTestApplicationTests {
    @Autowired
    HelloWorldService helloWorldService;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ProjectFileDao projectFileDao;


    //    @Test
    void contextLoads() {
        try {
            String s = helloWorldService.helloWorld();
            log.info(s);
        } catch (FailResultException e) {
            log.error(e.getMessages());
        }
    }

    //    @Test
    void testStudent() {
        try {
            List<Student> students = helloWorldService.getStudents();
            for (Student student : students) {
                log.info("姓名：" + student.getName() +
                        ";年龄：" + student.getAge());
            }
        } catch (FailResultException e) {
            log.error(e.getMessage());
        }
    }

    //    @Test
    void testLog() {
        log.info("日志");
        log.debug("测试");
        log.warn("警告");
        log.error("错误");
    }

    //    @Test
    void testJdbc() {
        String sql = " select * from t_project_file where tasksId = ? ";
//        ProjectFileEntity projectFile = projectFileDao.findById("402880d88267f9a90182681cf85c011b").orElse(null);
        List<ProjectFileEntity> projectFiles = jdbcTemplate.query(sql, new ProjectFilePo());
        for (ProjectFileEntity projectFile : projectFiles) {
            log.info(projectFile.getFileName());
        }
    }

    //    @Test
    void testHttp() {
        String url = "http://localhost:8866/api/product/getProductDetail?productCode=123";
        try {
            Student student = HttpUtils.getSend(url, null, new Student().getClass());
        } catch (FailResultException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //    @Test
//    @Ignore
    void testStopWatch() throws InterruptedException {
        String code = "123";
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(code);
        Thread.sleep(1000);
        stopWatch.stop();
        log.info("================执行时间：" + stopWatch.getTotalTimeMillis() + "==============");
    }

    @Ignore
    @Test
    void testExport() throws FailResultException {
        LocalDateTime localDateTime = LocalDateTime.now();
        Student student = new Student();
        student.setAge(15);
        student.setName("张三");
        List<Student> studentList = new ArrayList<Student>();
        studentList.add(student);
        Teacher teacher = new Teacher();
        teacher.setAge(50);
        teacher.setName("李四");
        teacher.setTeach("抠脚");
        List<Teacher> teaherList = new ArrayList<Teacher>();
        teaherList.add(teacher);
        ExportVo studentExportVo = new ExportVo<Student>(1, "学生信息表", Student.class, studentList);
        ExportVo teacherExportVo = new ExportVo<Teacher>(2, "教师信息表", Teacher.class, teaherList);
        ExportVo studentNoNumExportVo = new ExportVo<Student>("无编号学生", Student.class, studentList);
        ExportVo teacherNoNumEExportVo = new ExportVo<Teacher>("无编号教师信息表", Teacher.class, teaherList);
        List<ExportVo> exportList = new ArrayList<ExportVo>();
        exportList.add(studentExportVo);
        exportList.add(teacherExportVo);
        exportList.add(studentNoNumExportVo);
        exportList.add(teacherNoNumEExportVo);
        new ExcelUtils().exportExcel(exportList, "导出信息测试" + localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
    }

    @Test
    void testImportExcel() {
        try{

        String fileName = "D:\\export\\导出信息测试1662628666971.xlsx";
        ExcelImportDataListener<Student> studentOneListener = new ExcelImportDataListener();
        ExcelReader studentOneReader = EasyExcel.read(fileName, studentOneListener).build();

        //第一页表单
        ReadSheet sheetOne = EasyExcel.readSheet(0).head(Student.class).registerReadListener(new ExcelImportDataListener<Student>()).build();
        studentOneReader.read(sheetOne);
        List<Student> studentOneList = studentOneListener.getList();
        studentOneReader.finish();
        System.out.println(studentOneList.size() + "用户名：" + studentOneList.get(0).getName());
        ExcelImportDataListener<Teacher> teacherOneListener = new ExcelImportDataListener();
        ExcelReader teacherOneReader = EasyExcel.read(fileName, teacherOneListener).build();
        //第二页表单
        ReadSheet sheetTwo = EasyExcel.readSheet(1).head(Teacher.class).registerReadListener(new ExcelImportDataListener<Teacher>()).build();
        teacherOneReader.read(sheetTwo);
        List<Teacher> teacherOneList = teacherOneListener.getList();
        teacherOneReader.finish();
        System.out.println(teacherOneList.size() + "用户名：" + teacherOneList.get(0).getName());
        //第三页表单
        ExcelImportDataListener<Student> studentTwoListener = new ExcelImportDataListener();
        ExcelReader studentTwoReader = EasyExcel.read(fileName, studentTwoListener).build();
        ReadSheet sheetThree = EasyExcel.readSheet(2).head(Student.class).registerReadListener(new ExcelImportDataListener<Student>()).build();
        studentTwoReader.read(sheetThree);
        List<Student> studentTwoList = studentTwoListener.getList();
        studentTwoReader.finish();
        System.out.println(studentTwoList.size() + "用户名：" + studentTwoList.get(0).getName());
        //第四页表单
        ExcelImportDataListener<Teacher> teacherTwoListener = new ExcelImportDataListener();
        ExcelReader teacherTwoReader = EasyExcel.read(fileName, teacherTwoListener).build();
        ReadSheet sheetFour = EasyExcel.readSheet(3).head(Teacher.class).registerReadListener(new ExcelImportDataListener<Teacher>()).build();
        teacherTwoReader.read(sheetFour);
        List<Teacher> teacherTwoList = teacherTwoListener.getList();
        teacherTwoReader.finish();
        System.out.println(teacherTwoList.size() + "用户名：" + teacherTwoList.get(0).getName());
        } catch (ExcelDataConvertException e){
            System.out.println("导入数据错误");
        }
    }

}
