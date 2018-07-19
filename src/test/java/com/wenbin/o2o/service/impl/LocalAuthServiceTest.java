package com.wenbin.o2o.service.impl;

import com.wenbin.o2o.BaseTest;
import com.wenbin.o2o.dto.LocalAuthExecution;
import com.wenbin.o2o.entity.LocalAuth;
import com.wenbin.o2o.entity.PersonInfo;
import com.wenbin.o2o.service.LocalAuthService;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Wenbin Luo @ Aalto University
 */

public class LocalAuthServiceTest extends BaseTest {
    @Autowired
    private LocalAuthService localAuthService;

    @Test
    public void testModifyLocalAuth() {

        long userId = 1;
        String username = "testusername";
        String password = "newpassword";
        String newPassword = "testnewpassword";

        localAuthService.modifyLocalAuth(userId, username, password, newPassword);

        LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(username, newPassword);

        System.out.println(localAuth.getPersonInfo().getName());
    }
}