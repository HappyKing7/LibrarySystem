package com.Service.UserService;

import com.Bean.User.UserInfo;
import com.Bean.User.UserLogin;

import java.util.List;

public interface UserLoginService {
	List<UserLogin> findUserLoginInfoByUserIDAndPass(UserLogin ul);
	void updayeUserPassword(String userID,String newPassWord);
}
