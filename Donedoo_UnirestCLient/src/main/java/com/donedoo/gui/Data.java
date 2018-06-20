package com.donedoo.gui;

import java.util.List;

import com.donedoo.data.Tasks;
import com.donedoo.data.User;

import javafx.scene.Node;

public class Data {
	// ip not used anymore
	private static String ip = "192.168.0.150";
	private static String dbName = "donedoo";

	// -----------------------------creating user
	private static String groupName;
	private static String userName;
	private static String password;
	private static User user;

	// ----------------------------------creating task
	private static String task;

	// ----------------------------------retrieving data - classes send back an
	// array of strings
	private static String users[];
	private static String doneTasks[];
	private static String doTasks[];
	private static String doClaimedTasks[];
	private static String doClaims[];
	private static List<Tasks> tasks;
	private static List<User> allUsers;
	private static int isAdmin;

	private static Tasks assignedTask;

	public static Tasks getAssignedTask() {
		return assignedTask;
	}

	public static void setAssignedTask(Tasks assignedTask) {
		Data.assignedTask = assignedTask;
	}

	// -----------------------------------updates
	private static int changes; // check if change occured
	private static String currentPane = "Do";

	public static List<Tasks> getTasks() {
		return tasks;
	}

	public static void setTasks(List<Tasks> tasks) {
		Data.tasks = tasks;
	}

	public static List<User> getAllUsers() {
		return allUsers;
	}

	public static void setAllUsers(List<User> allUsers) {
		Data.allUsers = allUsers;
	}

	/**
	 * @return the groupName
	 */
	public static String getGroupName() {// marketplace.eclipse.org/marketplace-client-intro?mpc_install=335225
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public static void setGroupName(String groupName1) {
		groupName = groupName1;
	}

	/**
	 * @return the userName
	 */
	public static String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public static void setUserName(String userName1) {
		userName = userName1;
	}

	/**
	 * @return the password
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public static void setPassword(String password1) {
		password = password1;
	}

	/**
	 * @return the task
	 */
	public static String getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public static void setTask(String task1) {
		task = task1;
	}

	/**
	 * @return the users
	 */
	public static String[] getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public static void setUsers(String users1[]) {
		users = users1;
	}

	/**
	 * @return the doneTasks
	 */
	public static String[] getDoneTasks() {
		return doneTasks;
	}

	/**
	 * @param doneTasks
	 *            the doneTasks to set
	 */
	public static void setDoneTasks(String doneTasks1[]) {
		doneTasks = doneTasks1;
	}

	/**
	 * @return the doTasks
	 */
	public static String[] getDoTasks() {
		return doTasks;
	}

	/**
	 * @param doTasks
	 *            the doTasks to set
	 */
	public static void setDoTasks(String doTasks1[]) {
		doTasks = doTasks1;
	}

	/**
	 * @return the doClaimedTasks
	 */
	public static String[] getDoClaimedTasks() {
		return doClaimedTasks;
	}

	/**
	 * @param doClaimedTasks
	 *            the doClaimedTasks to set
	 */
	public static void setDoClaimedTasks(String doClaimedTasks1[]) {
		Data.doClaimedTasks = doClaimedTasks1;
	}

	/**
	 * @return the doClaims
	 */
	public static String[] getDoClaims() {
		return doClaims;
	}

	/**
	 * @param doClaims
	 *            the doClaims to set
	 */
	public static void setDoClaims(String doClaims1[]) {
		Data.doClaims = doClaims1;
	}

	/**
	 * @return the ip
	 */
	public static String getIp() {
		return ip;
	}

	/**
	 * @return the dbName
	 */
	public static String getDbName() {
		return dbName;
	}

	/**
	 * @return the changes
	 */
	public static int getChanges() {
		return changes;
	}

	/**
	 * @param changes
	 *            the changes to set
	 */
	public static void setChanges(int changes1) {
		Data.changes = changes1;
	}

	/**
	 * @return the currentPane
	 */
	public static String getCurrentPane() {
		return currentPane;
	}

	/**
	 * @param currentPane
	 *            the currentPane to set
	 */
	public static void setCurrentPane(String currentPane) {
		Data.currentPane = currentPane;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Data.user = user;
	}

	public static int getIsAdmin() {
		return isAdmin;
	}

	public static void setIsAdmin(int isAdmin) {
		Data.isAdmin = isAdmin;
	}
	
	

}
