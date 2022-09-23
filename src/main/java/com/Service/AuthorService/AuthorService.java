package com.Service.AuthorService;

import com.Bean.Author.AuthorInfo;

import java.util.List;

public interface AuthorService {
	List<AuthorInfo> findAllAuthorInfos(Integer current,Integer size,Integer mode);
	List<AuthorInfo> findAllAuthorInfoByAuthorID(String authorID);
	List<AuthorInfo> findAllAuthorInfoByIDOrName(String key,Integer current, Integer size,Integer mode);
	void insertAuthorInfo(AuthorInfo ai);
	void deleteAuthorInfo(String authorID);

}
