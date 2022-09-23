package com.Service.Servicelmpl.UserServiceImpl;

import com.Bean.User.UserInfo;
import com.Mapper.UserMapper;
import com.Service.UserService.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServicelmpl implements UserInfoService {
	@Autowired
	private UserMapper um;

	@Override
	public List<UserInfo> findUserInfoByUserID(String userID) {
		return um.findUserInfoByUserID(userID);
	}

	@Override
	public void updayeUserInfo(UserInfo ui) {
		um.updayeUserInfo(ui);
	}

	@Override
	public List<UserInfo> getAllUserInfos(Integer current, Integer size,Integer mode){
		return um.getAllUserInfos(current, size,mode);
	}

	@Override
	public List<UserInfo> findUserInfoByKey(String userID, String userNanme, String userPhone, String userPSPTID, String userEmail
			,Integer current,Integer size,Integer mode) {
		return um.findUserInfoByKey(userID, userNanme, userPhone, userPSPTID, userEmail,current,size,mode);
	}

}
