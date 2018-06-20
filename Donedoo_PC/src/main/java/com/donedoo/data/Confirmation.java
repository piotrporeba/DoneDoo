package com.donedoo.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "userCheck")
public class Confirmation {

	private String exists;

	public String getExists() {
		return exists;
	}

	@XmlElement
	public void setExists(String exists) {
		this.exists = exists;
	}

}
