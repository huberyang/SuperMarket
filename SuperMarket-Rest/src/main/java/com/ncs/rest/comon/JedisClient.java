package com.ncs.rest.comon;

public interface JedisClient {
	
	public String set(String host,int port);
	
	public String get(String  key);
	
	

}
