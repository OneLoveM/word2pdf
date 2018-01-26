package com.kanq.util;

import com.jacob.activeX.ActiveXComponent;

public class OfficeVeriosn {

	public static void main(String[] args) {
		ActiveXComponent word= null;
		try {
			word= new ActiveXComponent("Word.Application");
			System.out.println("office版本"+word.getBuildVersion());
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
