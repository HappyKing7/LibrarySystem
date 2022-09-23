package com.Service.UserService;

import com.Bean.User.UserLogin;
import com.Bean.User.UserRegisterInfo;

import java.util.List;

public interface UserRegisterService {
	void insertUserRegisterInfo(UserRegisterInfo uri);
	void insertUserInfo(UserRegisterInfo uri);
	List<UserRegisterInfo> findUserRegisterInfoByUserID(UserRegisterInfo uri);
	List<UserLogin> findUserLoginInfoByUserIDAndPass(UserLogin ul);
}
