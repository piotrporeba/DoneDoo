package com.donedoo.model;


import java.sql.Timestamp;

public class Tasks {

/*	@Override
	public String toString() {
		return "Task [taskName=" + taskName + ", postedBy=" + postedBy + ", claimedBy=" + claimedBy + ", completedBy="
				+ completedBy + ", timePosted=" + timePosted + ", timeLimit=" + timeLimit + "]";
	}*/

	private String taskName;
	private String postedBy;
	private String claimedBy;
	private String completedBy;
	private String timePosted;
	private String minutesLeft;
	private String hoursLeft;
	private String daysLeft;
	private String timeLimit;
	private String groupName;
	private int id;
	
	
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	public String getClaimedBy() {
		return claimedBy;
	}
	public void setClaimedBy(String claimedBy) {
		this.claimedBy = claimedBy;
	}
	public String getCompletedBy() {
		return completedBy;
	}
	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}
	public String getTimePosted() {
		return timePosted;
	}
	public void setTimePosted(String timePosted) {
		this.timePosted = timePosted;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getMinutesLeft() {
		return minutesLeft;
	}

	public void setMinutesLeft(String minutesLeft) {
		this.minutesLeft = minutesLeft;
	}

	public String getHoursLeft() {
		return hoursLeft;
	}

	public void setHoursLeft(String hoursLeft) {
		this.hoursLeft = hoursLeft;
	}

	public String getDaysLeft() {
		return daysLeft;
	}

	public void setDaysLeft(String daysLeft) {
		this.daysLeft = daysLeft;
	}
}