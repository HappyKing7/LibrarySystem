package com.Service.Servicelmpl;

import com.Bean.SqlCus;
import com.Bean.User.UserLogin;
import com.Mapper.testMapper;
import com.Service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class Testlmpl implements testService {
	@Autowired
	private testMapper tm;

	@Override
	public List<UserLogin> test(SqlCus sql) {
		return tm.test(sql);
	}
}
