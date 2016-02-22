package com.clockworks.bigture.aws;

import java.util.Map;

public interface ISESService {
	void sendEmail(String email,String title,String templateName,Map<String,Object> vars) throws Exception;
}
