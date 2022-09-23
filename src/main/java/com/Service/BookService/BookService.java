package com.Service.BookService;

import com.Bean.Book.BookInfos;

import java.util.List;

public interface BookService {
	List<BookInfos> findAllBookInfos(Integer current,Integer size,Integer mode);
	List<BookInfos> findBookInfosByKey (String key,Integer current, Integer size,Integer mode);
	void deleteBookInfo(String bookID);
	List<BookInfos> findBookInfoByKey(String key);
	void insertBookInfo(BookInfos bi);
	void updateBookInfo(BookInfos bi);
	void updateBookNumber(String bookID,Integer borrowedNumber,Integer remainingNumber);
}
