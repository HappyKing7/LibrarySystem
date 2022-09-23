package com.Service;

import com.Bean.SqlCus;
import com.Bean.User.UserLogin;
import java.util.List;

public interface testService {
	List<UserLogin> test(SqlCus sql);
}
