package com.example.springboottest.dao;

import com.example.springboottest.entity.ProjectFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectFileDao extends JpaRepository<ProjectFileEntity,String> {

}
