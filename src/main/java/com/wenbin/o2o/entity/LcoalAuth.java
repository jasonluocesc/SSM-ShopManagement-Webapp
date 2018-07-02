package com.wenbin.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class LcoalAuth {
    private long localAuthId;

    private String username;

    private String password;

    private Date createTime;

    private Date lastEditTime;

    private PersonInfo personInfo;
}
