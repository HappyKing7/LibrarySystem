package com.Mapper;

import com.Bean.Book.BookInfos;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
	List<BookInfos> findAllBookInfos (Integer current,Integer size,Integer mode);
	List<BookInfos> findBookInfosByKey (String key,Integer current, Integer size,Integer mode);
	void deleteBookInfo(String bookID);

	List<BookInfos> findBookInfoByKey(String key);
	void insertBookInfo(BookInfos bi);

	void updateBookInfo(BookInfos bi);

	void updateBookNumber(String bookID,Integer borrowedNumber,Integer remainingNumber);
}
