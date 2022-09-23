package com.Service.Servicelmpl.UserServiceImpl;

import com.Bean.User.UserLogin;
import com.Mapper.UserMapper;
import com.Service.UserService.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginServicelmpl implements UserLoginService {
	@Autowired
	private UserMapper um;

	@Override
	public List<UserLogin> findUserLoginInfoByUserIDAndPass(UserLogin ul) {
		return um.findUserLoginInfoByUserIDAndPass(ul);
	}

	@Override
	public void updayeUserPassword(String userID, String newPassWord) {
		um.updayeUserPassword(userID, newPassWord);
	}

}
