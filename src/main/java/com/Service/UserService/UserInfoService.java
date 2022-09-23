package com.Service.UserService;

import com.Bean.User.UserInfo;

import java.util.List;

public interface UserInfoService {
	List<UserInfo> findUserInfoByUserID(String userID);
	void updayeUserInfo(UserInfo ui);
	List<UserInfo> getAllUserInfos(Integer current,Integer size,Integer mode);
	List<UserInfo> findUserInfoByKey(String userID,String userNanme,String userPhone,String userPSPTID,String userEmail,
									 Integer current,Integer size,Integer mode);
}
