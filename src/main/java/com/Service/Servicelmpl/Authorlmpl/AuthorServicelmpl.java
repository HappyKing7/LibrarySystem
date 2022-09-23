package com.Service.Servicelmpl.Authorlmpl;

import com.Bean.Author.AuthorInfo;
import com.Mapper.AuthorMapper;
import com.Service.AuthorService.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServicelmpl implements AuthorService {
	@Autowired
	private AuthorMapper am;

	@Override
	public List<AuthorInfo> findAllAuthorInfos(Integer current,Integer size,Integer mode) {
		return am.findAllAuthorInfos(current, size, mode);
	}

	@Override
	public List<AuthorInfo> findAllAuthorInfoByAuthorID(String authorID) {
		return am.findAllAuthorInfoByAuthorID(authorID);
	}

	@Override
	public List<AuthorInfo> findAllAuthorInfoByIDOrName(String key,Integer current, Integer size,Integer mode){
		return am.findAllAuthorInfoByIDOrName(key,current,size,mode);
	}

	@Override
	public void insertAuthorInfo(AuthorInfo ai) {
		am.insertAuthorInfo(ai);
	}

	@Override
	public void deleteAuthorInfo(String authorID) {
		am.deleteAuthorInfo(authorID);
	}
}
