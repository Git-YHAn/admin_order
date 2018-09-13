package com.yang.server.service;

import com.yang.server.dao.UserInfoDao;
import com.yang.server.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;

    /**
     * 获取所有用户信息
     * @return
     */
    public List<UserInfo> getLoginInfo() {
        List<UserInfo> users = userInfoDao.getLoginInfo();
        return users;
    }

    /**
     * 根据名字查询用户
     * @param username
     * @return
     */
    public UserInfo getUserInfoByName(String username) {
        UserInfo userInfo = userInfoDao.getUserInfoByName(username);
        return userInfo;
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    public UserInfo verifierUser(String username, String password) {
        UserInfo user = userInfoDao.getUserInfoByName(username);
        if(user != null){
            if(user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 根据ID查询用户
     * @param userId
     * @return
     */
    public UserInfo getUserById(Long userId) {
        UserInfo userInfoById = userInfoDao.getUserInfoById(userId);
        return userInfoById;
    }

    /**
     * 模糊查询所有用户
     * @param username
     */
    public List<UserInfo> getUserInfoDim(String username) {
        List<UserInfo> users = userInfoDao.getUserInfoDim(username);
        return users;
    }

    public int addUserInfo(UserInfo userInfo) {
        return userInfoDao.addUserInfo(userInfo);
    }

    public int deletedUserInfo(Long userId) {
        return userInfoDao.deletedUserInfo(userId);
    }

    public int updateUserInfo(UserInfo userInfo) {
        return userInfoDao.updateUserInfo(userInfo);
    }
}
