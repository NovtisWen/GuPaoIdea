package vip.wen.spring.Service.impl;


import vip.wen.spring.Service.GPService;
import vip.wen.spring.Service.IDemoService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService {


	public String get(String name) {
		return "My name is " + name;
	}

}
