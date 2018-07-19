package com.wenbin.o2o.service.impl;

import com.wenbin.o2o.dao.LocalAuthDao;
import com.wenbin.o2o.dto.LocalAuthExecution;
import com.wenbin.o2o.entity.LocalAuth;
import com.wenbin.o2o.enums.LocalAuthStateEnum;
import com.wenbin.o2o.exceptions.LocalAuthOperationException;
import com.wenbin.o2o.service.LocalAuthService;
import com.wenbin.o2o.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalAuthByUsernameAndPwd(String userName, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(userName,password);
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword)
            throws LocalAuthOperationException {

        if (userId != null && userName != null && password != null && newPassword != null
                && !password.equals(newPassword)) {
            try {
                // 更新密码，并对新密码进行MD5加密
                int effectedNum = localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password),
                        MD5.getMd5(newPassword), new Date());

                if (effectedNum <= 0) {
                    throw new LocalAuthOperationException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new LocalAuthOperationException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
