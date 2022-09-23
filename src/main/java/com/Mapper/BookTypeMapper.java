package com.Mapper;

import com.Bean.BookType.BookTypeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookTypeMapper {
	List<BookTypeInfo> findAllBookTypeInfos(Integer current, Integer size, Integer mode);
	List<BookTypeInfo> findAllBookTypeInfoByBookTypeID(String bookTypeID);
	List<BookTypeInfo> findAllBookTypeInfoByIDOrName(String key, Integer current, Integer size, Integer mode);
	void insertBookTypeInfo(BookTypeInfo bti);
	void deleteBookTypeInfo(String bookTypeID);
}
