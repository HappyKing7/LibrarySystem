package com.Service.Servicelmpl;

import com.Bean.Borrowed.BorrowedInfo;
import com.Mapper.BorrowedMapper;
import com.Service.BorrowedService.BorrowedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowedServicelmpl implements BorrowedService {
	@Autowired
	private BorrowedMapper bm;

	@Override
	public List<BorrowedInfo> findAllBorrowedInfos(Integer current,Integer size,Integer mode) {
		return bm.findAllBorrowedInfos(current, size, mode);
	}

	@Override
	public List<BorrowedInfo> findBorrowedInfosByBorrowedID(String borrowedID) {
		return bm.findBorrowedInfosByBorrowedID(borrowedID);
	}

	@Override
	public List<BorrowedInfo> findAllBorrowedInfosByUserID(String userID,Integer current,Integer size,Integer mode) {
		return bm.findAllBorrowedInfosByUserID(userID, current, size, mode);
	}

	@Override
	public void updateBorrowedInfosStatus(String borrowedID, String borrowedStatus) {
		bm.updateBorrowedInfosStatus(borrowedID,borrowedStatus);
	}

	@Override
	public void insertBorrowedInfo(BorrowedInfo bi) {
		bm.insertBorrowedInfo(bi);
	}

	@Override
	public List<BorrowedInfo> findBorrowedInfosByUserIDAndBorrowedIDAndStatus(String userID, String bookID, String bookStatus) {
		return bm.findBorrowedInfosByUserIDAndBorrowedIDAndStatus(userID, bookID, bookStatus);
	}

	@Override
	public List<BorrowedInfo> findBookInfosByBorrowedIDOrUserIDOrBookID(String borrowedID, String userID, String bookID, Integer current, Integer size, Integer mode) {
		return bm.findBookInfosByBorrowedIDOrUserIDOrBookID(borrowedID, userID, bookID, current, size, mode);
	}
}
