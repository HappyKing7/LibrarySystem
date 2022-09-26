package com.Service.BookTypeService;

import com.Bean.BookType.BookTypeInfo;

import java.util.List;

public interface BookTypeService {
	List<BookTypeInfo> findAllBookTypeInfos(Integer current, Integer size, Integer mode);
	List<BookTypeInfo> findAllBookTypeInfoByBookTypeID(String bookTypeID);
	List<BookTypeInfo> findAllBookTypeInfoByIDOrName(String key, Integer current, Integer size, Integer mode);
	void insertBookTypeInfo(BookTypeInfo bti);
	void updateBookTypeInfo(BookTypeInfo bti);
}
