package com.wenbin.o2o.service;

import com.wenbin.o2o.dto.LocalAuthExecution;
import com.wenbin.o2o.entity.LocalAuth;
import com.wenbin.o2o.exceptions.LocalAuthOperationException;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface LocalAuthService {

    LocalAuth getLocalAuthByUsernameAndPwd(String userName, String password);


    LocalAuth getLocalAuthByUserId(long userId);

    LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;

    LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword) throws LocalAuthOperationException;
}
