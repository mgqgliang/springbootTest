package com.example.springboottest.service;

import com.example.springboottest.bean.Student;
import com.example.springboottest.common.FailResultException;
import com.example.springboottest.entity.ProjectFileEntity;

import java.util.List;

public interface HelloWorldService {

    String helloWorld() throws FailResultException;

    List<Student> getStudents() throws FailResultException;

    Boolean testDelay(String message,Integer code);

    List<ProjectFileEntity> getProjectFileList() throws FailResultException;
}
