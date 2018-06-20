package com.donedoo.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "change")
public class Change {

	private String groupName;
	private int id;
	private String changeType;

	public String getGroupName() {
		return groupName;
	}

	@XmlElement
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public String getChangeType() {
		return changeType;
	}

	@XmlElement
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

}
