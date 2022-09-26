package com.Mapper;

import com.Bean.Press.PressInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PressMapper {
	List<PressInfo> findAllPressInfos(Integer current,Integer size,Integer mode);
	List<PressInfo> findAllPressInfoByIDOrName(String key,Integer current, Integer size,Integer mode);
	List<PressInfo> findAllPressInfoByID(String pressID);
	void insertPressInfo(PressInfo pi);
	void updatePressInfo(PressInfo pi);
}
