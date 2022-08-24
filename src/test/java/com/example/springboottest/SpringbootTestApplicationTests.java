package com.example.springboottest;

import com.example.springboottest.bean.Student;
import com.example.springboottest.common.FailResultException;
import com.example.springboottest.common.delay.CommonDelay;
import com.example.springboottest.common.delay.Delay;
import com.example.springboottest.dao.ProjectFileDao;
import com.example.springboottest.entity.ProjectFileEntity;
import com.example.springboottest.service.HelloWorldService;
import com.example.springboottest.service.HelloWorldServiceImpl;
import com.example.springboottest.po.ProjectFilePo;
import com.example.springboottest.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
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
        try{
            String s = helloWorldService.helloWorld();
           log.info(s);
        } catch (FailResultException e){
           log.error(e.getMessages());
        }
    }

//    @Test
    void testStudent(){
        try{
            List<Student> students = helloWorldService.getStudents();
            for (Student student:students) {
                log.info("姓名：" + student.getName() +
                        ";年龄：" + student.getAge());
            }
        } catch (FailResultException e){
            log.error(e.getMessage());
        }
    }
//    @Test
    void testLog(){
        log.info("日志");
        log.debug("测试");
        log.warn("警告");
        log.error("错误");
    }
//    @Test
    void testJdbc(){
        String sql = " select * from t_project_file where tasksId = ? ";
//        ProjectFileEntity projectFile = projectFileDao.findById("402880d88267f9a90182681cf85c011b").orElse(null);
        List<ProjectFileEntity> projectFiles = jdbcTemplate.query(sql,new ProjectFilePo());
        for (ProjectFileEntity projectFile:projectFiles) {
            log.info(projectFile.getFileName());
        }
    }

//    @Test
    void testHttp(){
        String url = "http://localhost:8866/api/product/getProductDetail?productCode=123";
       try{
           Student student = HttpUtils.getSend(url,null,new Student().getClass());
       } catch (FailResultException e){
           log.error(e.getMessage());
       } catch (Exception e){
           e.printStackTrace();
       }

    }

    @Test
    void testStopWatch() throws InterruptedException {
        String code = "123";
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(code);
        Thread.sleep(1000);
        stopWatch.stop();
        log.info("================执行时间：" + stopWatch.getTotalTimeMillis() + "==============");
    }



}
