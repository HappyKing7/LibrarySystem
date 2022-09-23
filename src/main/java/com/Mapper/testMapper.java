package com.Mapper;

import com.Bean.SqlCus;
import com.Bean.User.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


@Mapper
public interface testMapper {
	List<UserLogin> test(SqlCus sql);
}
