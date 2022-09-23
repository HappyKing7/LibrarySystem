package com.Service.PressService;

import com.Bean.Press.PressInfo;

import java.util.List;

public interface PressService {
	List<PressInfo> findAllPressInfos(Integer current,Integer size,Integer mode);
	List<PressInfo> findAllPressInfoByIDOrName(String key,Integer current, Integer size,Integer mode);
	List<PressInfo> findAllPressInfoByID(String pressID);
	void insertPressInfo(PressInfo pi);
	void deletePressInfo(String pressID);
}
