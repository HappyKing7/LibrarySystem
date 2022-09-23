package com.Service.Servicelmpl.BookTypelmpl;

import com.Bean.BookType.BookTypeInfo;
import com.Mapper.BookTypeMapper;
import com.Service.BookTypeService.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTypeServicelmpl implements BookTypeService {
	@Autowired
	private BookTypeMapper bym;

	@Override
	public List<BookTypeInfo> findAllBookTypeInfos(Integer current, Integer size, Integer mode) {
		return bym.findAllBookTypeInfos(current, size, mode);
	}

	@Override
	public List<BookTypeInfo> findAllBookTypeInfoByBookTypeID(String bookTypeID) {
		return bym.findAllBookTypeInfoByBookTypeID(bookTypeID);
	}

	@Override
	public List<BookTypeInfo> findAllBookTypeInfoByIDOrName(String key, Integer current, Integer size, Integer mode) {
		return bym.findAllBookTypeInfoByIDOrName(key, current, size, mode);
	}

	@Override
	public void insertBookTypeInfo(BookTypeInfo bti) {
		bym.insertBookTypeInfo(bti);
	}

	@Override
	public void deleteBookTypeInfo(String bookTypeID) {
		bym.deleteBookTypeInfo(bookTypeID);
	}
}
