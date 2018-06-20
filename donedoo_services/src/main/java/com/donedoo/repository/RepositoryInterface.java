package com.pluralsight.repository;

import java.util.List;

import com.pluralsight.model.Change;
import com.pluralsight.model.Task;
import com.pluralsight.model.User;
import com.pluralsight.model.ExistsCheck;

public interface RepositoryInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pluralsight.repository.ActivityRepository#findAllActivities()
	 */
	// Repository interface for TaskRepositoryStub
	List<Task> findAllTasks(String groupName);

	ExistsCheck findGroupUserPassword(String groupName, String userName, String password);

	ExistsCheck findGroupUser(String groupName, String userName);

	ExistsCheck findGroup(String groupName);

	Change findLastChange(String groupName);

	Change addChange(Change change);

	Task addTask(Task task);

	ExistsCheck checkTask(Task task);

	User addFirstUser(User user);

	User addNextUser(User user);

	User addGroup(User user);

	Task updateTask(Task task);

	User updateUser(User user);
	
	User updateOtherUser(User user);

	void delete(int id);

	List<User> findAllUsers(String groupName);

	void deleteUser(String userName, String groupname);

	// Task findTask(String activityId);

}