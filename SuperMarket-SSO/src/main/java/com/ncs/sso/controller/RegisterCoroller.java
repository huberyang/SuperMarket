package com.ncs.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.ExceptionUtil;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbUser;
import com.ncs.sso.service.RegisterService;

@RequestMapping("/user")
@Controller
public class RegisterCoroller {
	
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkRegistInfo(@PathVariable String param,@PathVariable Integer type,String callback) {
		
		try {
		
		SmResult reuslt = registerService.CheckInfo(param, type);
		
		if(StringUtils.isNotBlank(callback)) {
			//调用此方法为jsonp
			MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(reuslt);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		
		return reuslt;
		}catch(Exception e) {
			
			e.printStackTrace();
			return SmResult.build(500,ExceptionUtil.getStatckTrace(e));
			
		}
	}
	
	@RequestMapping(value="/registe",method=RequestMethod.POST)
	@ResponseBody
	public SmResult registe(@RequestBody TbUser user) {
		
		try {
		SmResult result = registerService.Registe(user);
		return result;
		}catch(Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
	}
	

}
