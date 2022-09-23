package com.Mapper;

import com.Bean.User.UserInfo;
import com.Bean.User.UserLogin;
import com.Bean.User.UserRegisterInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
	List<UserRegisterInfo> findUserRegisterInfoByUserID(UserRegisterInfo uri);
	void insertUserRegisterInfo(UserRegisterInfo uri);
	void insertUserInfo(UserRegisterInfo uri);
	List<UserLogin> findUserLoginInfoByUserIDAndPass(UserLogin ul);

	List<UserInfo> findUserInfoByUserID(String userID);
	void updayeUserInfo(UserInfo ui);
	void updayeUserPassword(String userID,String newPassWord);

	List<UserInfo> getAllUserInfos(Integer current,Integer size,Integer mode);
	List<UserInfo> findUserInfoByKey(String userID,String userNanme,String userPhone,String userPSPTID,
									 String userEmail,Integer current,Integer size,Integer mode);
}
