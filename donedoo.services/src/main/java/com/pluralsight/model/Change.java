package com.pluralsight.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement // all classes outputted in XML need this!!!!
public class Change {

	private int id;
	private String groupName;
	private String changeType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

}