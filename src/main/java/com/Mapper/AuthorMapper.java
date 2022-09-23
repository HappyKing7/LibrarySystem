package com.Mapper;

import com.Bean.Author.AuthorInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorMapper {
	List<AuthorInfo> findAllAuthorInfos(Integer current,Integer size,Integer mode);
	List<AuthorInfo> findAllAuthorInfoByAuthorID(String authorID);
	List<AuthorInfo> findAllAuthorInfoByIDOrName(String key,Integer current, Integer size,Integer mode);
	void insertAuthorInfo(AuthorInfo ai);
	void deleteAuthorInfo(String authorID);
}
