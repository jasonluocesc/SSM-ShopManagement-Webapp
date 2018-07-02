package com.wenbin.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class PersonInfo {
    private Long userId;

    private String name;

    private String profileImg;

    private String email;

    private String gender;

    private Integer enableStatus;
    //1 customer 2 seller 3 admin

    private Integer userType;

    private Date createTime;

    private Date lastEditTime;
}
