package com.kero.security.spring.test;

import java.util.ArrayList;
import java.util.List;

public class TestObjectImpl implements TestObject {

	private String text;
	
	private List<TestObjectImpl> childs = new ArrayList<>();
	
	public TestObjectImpl() {}
	
	public TestObjectImpl(String text) {
		
		this.text = text;
	}
	
	public List<TestObjectImpl> getChilds() {
	
		return this.childs;
	}
	
	public String getText() {
		
		return this.text;
	}
}
