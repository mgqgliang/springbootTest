package com.example.springboottest.service;

import com.example.springboottest.bean.Student;
import com.example.springboottest.common.commonstatic.CommonCode;
import com.example.springboottest.common.commonstatic.CommonMessage;
import com.example.springboottest.common.FailResultException;
import com.example.springboottest.dao.ProjectFileDao;
import com.example.springboottest.entity.ProjectFileEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelloWorldServiceImpl implements HelloWorldService{

    ProjectFileDao projectFileDao;

    public HelloWorldServiceImpl(ProjectFileDao projectFileDao){
        this.projectFileDao = projectFileDao;
    }

    @Override
    public String helloWorld(){
        return "Hello World!";
    }

    @Override
    public List<Student> getStudents() throws FailResultException {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setAge(3);
        student1.setName("阿姨");
        Student student2 = new Student();
        student2.setAge(5);
        student2.setName("爸爸");
        Student student3 = new Student();
        student3.setAge(3);
        student3.setName("妈妈");
        students.add(student1);
        students.add(student2);
        students.add(student3);
        try{
            students = students.stream().sorted(Comparator.comparing(Student::getAge).reversed())
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new FailResultException(CommonCode.CODE_50001, CommonMessage.ERROR_MESSAGE );
        }


        return students;
    }

    @Override
    public Boolean testDelay(String message, Integer code) {
        System.out.println(message);
        System.out.println(code);
        return true;
    }

    @Override
    public List<ProjectFileEntity> getProjectFileList() throws FailResultException {
        try{
            List<ProjectFileEntity> projectFileList = projectFileDao.findAll();
            System.out.println(projectFileList.get(0).getFileName());
            return projectFileList;
        } catch (Exception e){
            e.printStackTrace();
            throw new FailResultException(CommonCode.CODE_50001,CommonMessage.ERROR_MESSAGE);
        }
    }

    @Override
    public String sayHi() {
        return "Hi gays";
    }

	@Override
	public String hiGays() {
		return "大家好";
	}

    @Override
    public String sayWhat() {
        return "你让我说啥？";
    }

}
