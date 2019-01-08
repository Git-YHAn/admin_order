package com.yang.server.dao;

import com.yang.server.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoDao {

    List<UserInfo> getLoginInfo();

    UserInfo getUserInfoByName(String username);

    UserInfo getUserInfoById(Long userId);

    List<UserInfo> getUserInfoDim(@Param("username") String username);

    int addUserInfo(UserInfo userInfo);

    int deletedUserInfo(Long userId);

    int updateUserInfo(UserInfo userInfo);
}
