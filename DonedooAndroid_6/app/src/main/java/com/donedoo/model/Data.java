package com.donedoo.model;

import java.util.List;

import com.donedoo.model.Tasks;
import com.donedoo.model.User;

public class Data {


	//-----------------------------creating user
	private static String groupName;
	private static String userName;
	private static String password;
	private static String isAdmin;
	private static User user;
	private static String check="wait";
	private static String notification="Please Enter Login Details";


	//----------------------------------creating task
	private static String task;

	//----------------------------------retrieving data - classes send back an array of strings
	private static String users[];
	private static List<Tasks> tasks;
	private static List<User> allUsers;

	//-----------------------------------updates
	private static int oldChanges; //check if change occured
	private static int newChanges;
	private static String currentPane = "Do";
	private static String changeType;

	public static int getOldChanges() {
		return oldChanges;
	}

	public static void setOldChanges(int oldChanges) {
		Data.oldChanges = oldChanges;
	}

	public static int getNewChanges() {
		return newChanges;
	}

	public static void setNewChanges(int newChanges) {
		Data.newChanges = newChanges;
	}

	public static List<User> getAllUsers() {
		return allUsers;
	}

	public static void setAllUsers(List<User> allUsers) {
		Data.allUsers = allUsers;
	}

	public static String getGroupName() {
		return groupName;
	}

	public static void setGroupName(String groupName) {
		Data.groupName = groupName;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		Data.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Data.password = password;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Data.user = user;
	}

	public static String getCheck() {
		return check;
	}

	public static void setCheck(String check) {
		Data.check = check;
	}

	public static String getTask() {
		return task;
	}

	public static void setTask(String task) {
		Data.task = task;
	}

	public static String[] getUsers() {
		return users;
	}

	public static void setUsers(String[] users) {
		Data.users = users;
	}

	public static List<Tasks> getTasks() {
		return tasks;
	}

	public static void setTasks(List<Tasks> tasks) {
		Data.tasks = tasks;
	}

	public static String getCurrentPane() {
		return currentPane;
	}

	public static void setCurrentPane(String currentPane) {
		Data.currentPane = currentPane;
	}

	public static String getChangeType() {
		return changeType;
	}

	public static void setChangeType(String changeType) {
		Data.changeType = changeType;
	}

    public static String getNotification() {
        return notification;
    }

    public static void setNotification(String notification) {
        Data.notification = notification;
    }

	public static String getIsAdmin() {
		return isAdmin;
	}

	public static void setIsAdmin(String isAdmin) {
		Data.isAdmin = isAdmin;
	}
}
