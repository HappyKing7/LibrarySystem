package com.Mapper;

import com.Bean.Borrowed.BorrowedInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BorrowedMapper {
	List<BorrowedInfo> findAllBorrowedInfos(Integer current,Integer size,Integer mode);
	List<BorrowedInfo> findBorrowedInfosByBorrowedID(String borrowedID);
	List<BorrowedInfo> findAllBorrowedInfosByUserID(String userID,Integer current,Integer size,Integer mode);
	void updateBorrowedInfosStatus(String borrowedID,String borrowedStatus);
	void insertBorrowedInfo(BorrowedInfo bi);
	List<BorrowedInfo> findBorrowedInfosByUserIDAndBorrowedIDAndStatus(String userID,String bookID,String bookStatus);
	List<BorrowedInfo> findBookInfosByBorrowedIDOrUserIDOrBookID(String borrowedID,String userID,String bookID,Integer current,Integer size,Integer mode);
}
