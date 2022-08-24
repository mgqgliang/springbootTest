package com.example.springboottest.po;

import com.example.springboottest.entity.ProjectFileEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectFilePo implements RowMapper<ProjectFileEntity> {
    @Override
    public ProjectFileEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProjectFileEntity projectFileEntity = new ProjectFileEntity();
        projectFileEntity.setObjId(rs.getString("obj_id"));
        projectFileEntity.setProjectId(rs.getString("project_id"));
        projectFileEntity.setVersionId(rs.getString("version_id"));
        projectFileEntity.setTasksId(rs.getString("tasks_id"));
        projectFileEntity.setFileName(rs.getString("file_name"));
        projectFileEntity.setFileType(rs.getInt("file_type"));
        projectFileEntity.setFilePath(rs.getString("file_path"));
        projectFileEntity.setCreateUser(rs.getString("create_user"));
        projectFileEntity.setCreateDate(rs.getTimestamp("create_date"));
        projectFileEntity.setModifyDate(rs.getTimestamp("modify_date"));
        projectFileEntity.setModifyUser(rs.getString("modify_user"));
        return projectFileEntity;
    }
}
