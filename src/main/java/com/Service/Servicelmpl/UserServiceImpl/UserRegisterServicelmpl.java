package com.Service.Servicelmpl.UserServiceImpl;

import com.Bean.User.UserLogin;
import com.Bean.User.UserRegisterInfo;
import com.Mapper.UserMapper;
import com.Service.UserService.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegisterServicelmpl implements UserRegisterService {
	@Autowired
	private UserMapper um;

	@Override
	public void insertUserRegisterInfo(UserRegisterInfo uri) {
		um.insertUserRegisterInfo(uri);
	}

	@Override
	public void insertUserInfo(UserRegisterInfo uri) {
		um.insertUserInfo(uri);
	}

	@Override
	public List<UserRegisterInfo> findUserRegisterInfoByUserID(UserRegisterInfo uri) {
		return um.findUserRegisterInfoByUserID(uri);
	}

	@Override
	public List<UserLogin> findUserLoginInfoByUserIDAndPass(UserLogin ul) {
		return um.findUserLoginInfoByUserIDAndPass(ul);
	}
}
