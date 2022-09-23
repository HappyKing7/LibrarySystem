package com.Service.Servicelmpl.Booklmpl;

import com.Bean.Book.BookInfos;
import com.Mapper.BookMapper;
import com.Service.BookService.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServicelmpl implements BookService {
	@Autowired
	private BookMapper bm;


	@Override
	public java.util.List<BookInfos> findAllBookInfos(Integer current, Integer size,Integer mode) {
		return bm.findAllBookInfos(current,size,mode);
	}

	@Override
	public List<BookInfos> findBookInfosByKey (String key,Integer current, Integer size,Integer mode) {
		return bm.findBookInfosByKey(key,current,size,mode);
	}

	@Override
	public void deleteBookInfo(String bookID) {
		bm.deleteBookInfo(bookID);
	}

	@Override
	public List<BookInfos> findBookInfoByKey(String key) {
		return bm.findBookInfoByKey(key);
	}

	@Override
	public void insertBookInfo(BookInfos bi) {
		bm.insertBookInfo(bi);
	}

	@Override
	public void updateBookInfo(BookInfos bi) {
		bm.updateBookInfo(bi);
	}

	@Override
	public void updateBookNumber(String bookID, Integer borrowedNumber, Integer remainingNumber) {
		bm.updateBookNumber(bookID, borrowedNumber, remainingNumber);
	}
}
