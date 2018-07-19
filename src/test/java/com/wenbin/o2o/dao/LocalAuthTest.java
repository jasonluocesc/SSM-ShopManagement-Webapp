package com.wenbin.o2o.dao;

import com.wenbin.o2o.BaseTest;
import com.wenbin.o2o.entity.LocalAuth;
import com.wenbin.o2o.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author Wenbin Luo @ Aalto University
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;

    private static final String username = "username1";
    private static final String password = "testpassword";

    @Test
    public void testAInsertAuth() throws Exception{
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);
        localAuth.setPersonInfo(personInfo);
        localAuth.setUsername(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testBQueryLocalByUserNameAndPwd() throws Exception{
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username, password);
         assertEquals("test",localAuth.getPersonInfo().getEmail());
    }

    @Test
    public void testCQueryLocalByUserId() throws Exception{
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        assertEquals("测试",localAuth.getPersonInfo().getName());
    }

    @Test
    public void testDUpdateLocalAuth() throws Exception{
        Date lastEditTime = new Date();
        int effectNum = localAuthDao.updateLocalAuth(1L,username, password, "newpassword", lastEditTime);
        assertEquals(1, effectNum);
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        System.out.println(localAuth.getPassword());
    }
}
