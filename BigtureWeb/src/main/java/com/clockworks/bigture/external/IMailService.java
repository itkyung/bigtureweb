package com.clockworks.bigture.external;

import java.util.Map;

public interface IMailService {
	void sendEmail(String email,String title,String templateName,Map<String,Object> vars) throws Exception;
	
}
