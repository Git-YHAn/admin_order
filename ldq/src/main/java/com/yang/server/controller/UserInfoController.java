package com.yang.server.controller;

import com.yang.server.common.BaseController;
import com.yang.server.common.handler.ResultHandler;
import com.yang.server.entity.UserInfo;
import com.yang.server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoController extends BaseController{

    @Autowired
    UserInfoService userInfoService;

    /**
     * 获取当前用户
     * @return
     */
    @GetMapping(value = "/get")
    public ResultHandler<UserInfo> getUserInfoByReq() {
        UserInfo userInfo = getUserInfo();
        return ResultHandler.success(userInfo);
    }

    /**
     * 查询用户
     * @param username
     * @return
     */
    @GetMapping("/search")
    public ResultHandler<List<UserInfo>> getUserInfoDate(@RequestParam(value = "username", required = false) String username) {
        List<UserInfo> userInfos = userInfoService.getUserInfoDim(username);
        return ResultHandler.success(userInfos);
    }

    /**
     * 新增用户
     * @param userInfo
     * @return
     */
    @PostMapping("/add")
    public ResultHandler<String> addUserInfo(@RequestBody UserInfo userInfo) {
        // 判断用户名是否重复
        UserInfo user = userInfoService.getUserInfoByName(userInfo.getUsername());
        if (user == null) {
            userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()));
            int resultNum = userInfoService.addUserInfo(userInfo);
            if (resultNum == 1) {
                return ResultHandler.success("添加成功");
            }
            return ResultHandler.error("添加失败");
        }
        return ResultHandler.error("用户名已存在");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/delete")
    public ResultHandler<String> deletedUserInfo(@RequestParam Long userId) {
        int resultNum =  userInfoService.deletedUserInfo(userId);
        if (resultNum == 1) {
            return ResultHandler.success("删除成功");
        }
        return ResultHandler.error("删除失败");
    }

    @PutMapping("/update")
    public  ResultHandler<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        UserInfo user = userInfoService.getUserById(userInfo.getUserId());
        if (user != null) {
            if(user.getPassword().equals(userInfo.getPassword())) {
                userInfo.setPassword(null);
            } else {
                userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()));
            }
            int resultNum =  userInfoService.updateUserInfo(userInfo);
            if (resultNum == 1) {
                return ResultHandler.success("修改成功");
            }
        }
        return ResultHandler.success("修改失败");
    }
}
