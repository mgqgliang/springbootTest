package com.example.springboottest.util;

import com.example.springboottest.dao.ProjectFileDao;
import com.example.springboottest.entity.ProjectFileEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SelectUtil {

    public SelectUtil(){}
    ProjectFileDao projectFileDao;
    public SelectUtil(ProjectFileDao projectFileDao){
        this.projectFileDao = projectFileDao;
    }

    public Boolean testUtils(String message){
        System.out.println(message);
        return true;
    }


    public void getProjectFileList(){
       List<ProjectFileEntity> projectFileList = projectFileDao.findAll();
       if(CollectionUtils.isNotEmpty(projectFileList)){
            log.info(projectFileList.get(0).getFileName());
       }
    }
}
