package com.Service.Servicelmpl.Presslmpl;

import com.Bean.Press.PressInfo;
import com.Mapper.PressMapper;
import com.Service.PressService.PressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PressServicelmpl implements PressService {
	@Autowired
	private PressMapper pm;

	@Override
	public List<PressInfo> findAllPressInfos(Integer current,Integer size,Integer mode) {
		return pm.findAllPressInfos(current, size, mode);
	}

	@Override
	public List<PressInfo> findAllPressInfoByIDOrName(String key, Integer current, Integer size, Integer mode) {
		return pm.findAllPressInfoByIDOrName(key, current, size, mode);
	}


	@Override
	public 	List<PressInfo> findAllPressInfoByID(String pressID) {
		return pm.findAllPressInfoByID(pressID);
	}

	@Override
	public void insertPressInfo(PressInfo pi) {
		pm.insertPressInfo(pi);
	}

	@Override
	public void updatePressInfo(PressInfo pi) {
		pm.updatePressInfo(pi);
	}
}
