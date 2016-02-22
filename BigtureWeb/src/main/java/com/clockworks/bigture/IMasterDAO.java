package com.clockworks.bigture;

import java.util.List;

import com.clockworks.bigture.entity.Country;
import com.clockworks.bigture.entity.TempImage;

public interface IMasterDAO {
	public List<Country> getCountries();
	public Country findByCode(String code);
	public void saveTempImage(TempImage img);
	TempImage loadTempImage(Long id);
}
