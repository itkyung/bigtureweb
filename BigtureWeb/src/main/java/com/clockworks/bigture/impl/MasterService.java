package com.clockworks.bigture.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clockworks.bigture.IMasterDAO;
import com.clockworks.bigture.IMasterService;
import com.clockworks.bigture.entity.Country;

@Service
public class MasterService implements IMasterService {
	@Autowired
	private IMasterDAO dao;
	
	
	@Override
	public List<Country> getCountries() {
		return dao.getCountries();
	}

	
}
