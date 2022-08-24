package com.example.springboottest.entity;

import com.example.springboottest.bean.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "t_project_file")
public class ProjectFileEntity extends BasicEntity implements Serializable {

    @Column(name = "project_id")
    private String projectId;
    @Column(name = "version_id")
    private String versionId;
    @Column(name = "tasks_id")
    private String tasksId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private Integer fileType;
    @Column(name = "file_path")
    private String filePath;
//    @Id
//    private String objId;
//
//    public void setObjId(String objId) {
//        this.objId = objId;
//    }
//
//    public String getObjId() {
//        return objId;
//    }
}
