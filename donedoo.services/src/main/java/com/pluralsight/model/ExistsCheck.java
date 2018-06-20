package com.pluralsight.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement // all classes outputted in XML need this!!!!
public class ExistsCheck {

	private String exists = ".";
	

	public String getExists() {
		return exists;
	}

	public void setExists(String exists) {
		this.exists = exists;
	}



}
