package com.ecom.site_project.service;

import com.ecom.site_project.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> getAllUserDetails();

    void saveUserDetail(UserInfo userInfo);

    UserInfo getUserDetail(int id);

    void deleteUserDetail(int id);
}
