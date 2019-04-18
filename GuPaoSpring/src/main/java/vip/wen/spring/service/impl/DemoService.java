package vip.wen.spring.service.impl;


import vip.wen.spring.service.GPService;
import vip.wen.spring.service.IDemoService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService {


	public String get(String name) {
		return "My name is " + name;
	}

}
