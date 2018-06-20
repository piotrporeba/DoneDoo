package com.pluralsight.repository;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.pluralsight.model.Change;
import com.pluralsight.model.Task;
import com.pluralsight.model.User;
import com.pluralsight.model.ExistsCheck;

public class RepositoryStub implements RepositoryInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pluralsight.repository.ActivityRepository#findAllActivities()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pluralsight.repository.ActivityRepository#findAllActivities()
	 */

	@Override
	public List<Task> findAllTasks(String groupName) {
		// String groupName="1";
		List<Task> taskList = new ArrayList<Task>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			// query will contain only those that have no time stamp on them

			String query = "select * from tasks WHERE groupname='" + groupName
					+ "' ORDER BY TIMESTAMPDIFF(MINUTE, NOW(), timelimit)";
			// String query1="select task from "+groupName+"_tasks where completion= '1' and
			// doing IS NULL and timeLimit <> '0000-00-00 00:00:00' ORDER BY
			// TIMESTAMPDIFF(MINUTE, NOW(), timeLimit)";
			stmt.executeQuery(query);
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) { // Move the cursor to the next row, return false if no more row
				// arrayTemp[rowCount] = rset.getString("task");
				// ++rowCount;
				Task activitytemp = new Task();
				activitytemp.setGroupName(rset.getString("groupname"));
				activitytemp.setTaskName(rset.getString("taskname"));
				activitytemp.setPostedBy(rset.getString("postedby"));
				activitytemp.setClaimedBy(rset.getString("claimedby"));
				activitytemp.setCompletedBy(rset.getString("completedby"));
				activitytemp.setTimePosted(rset.getString("timeposted"));
				activitytemp.setTimeLimit(rset.getString("timelimit"));
				activitytemp.setId(rset.getInt("id"));
				taskList.add(activitytemp);

			}
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskList;
	}

	////////// Lists All the users from one
	////////// group////////////////////////////////////////////////////

	@Override
	public List<User> findAllUsers(String groupName) {
		List<User> userList = new ArrayList<User>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			// query will contain only those that have no time stamp on them

			String query = "select username, id , admin from users WHERE groupname='" + groupName + "'";
			// String query1="select task from "+groupName+"_tasks where completion= '1' and
			// doing IS NULL and timeLimit <> '0000-00-00 00:00:00' ORDER BY
			// TIMESTAMPDIFF(MINUTE, NOW(), timeLimit)";
			stmt.executeQuery(query);
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) { // Move the cursor to the next row, return false if no more row
				// arrayTemp[rowCount] = rset.getString("task");
				// ++rowCount;
				User user = new User();
				user.setId(rset.getInt("id"));
				user.setUserName(rset.getString("userName"));
				user.setAdmin(rset.getInt("admin"));
				userList.add(user);

			}
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}

	////////// CHECKS IF USER And Password EXISTS IN A GROUP (to
	////////// login)/////////////////////////////

	@Override
	public ExistsCheck findGroupUserPassword(String groupName, String userName, String password) {
		// List<User> userList = new ArrayList<User>();
		ExistsCheck tempUser = new ExistsCheck();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			// query will contain only those that have no time stamp on them
			String query = "select * from users WHERE groupname='" + groupName + "' AND username='" + userName
					+ "' AND password='" + password + "'";
			// String query1="select task from "+groupName+"_tasks where completion= '1' and
			// doing IS NULL and timeLimit <> '0000-00-00 00:00:00' ORDER BY
			// TIMESTAMPDIFF(MINUTE, NOW(), timeLimit)";
			stmt.executeQuery(query);
			ResultSet rset = stmt.executeQuery(query);

			if (rset.next()) {
				tempUser.setExists(rset.getString("admin").toString());
				
			} else {
				tempUser.setExists("Not Exist");
			}
			// userList.add(tempUser);
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempUser;
	}

	/////////////// Checks if group has user of specific name (before creating new
	/////////////// group)//////////////////////////////////////////////////

	@Override
	public ExistsCheck findGroupUser(String groupName, String userName) {
		// List<User> userList = new ArrayList<User>();
		ExistsCheck tempUser = new ExistsCheck();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			// query will contain only those that have no time stamp on them
			String query = "select * from users WHERE groupname='" + groupName + "' AND username='" + userName + "'";
			// String query1="select task from "+groupName+"_tasks where completion= '1' and
			// doing IS NULL and timeLimit <> '0000-00-00 00:00:00' ORDER BY
			// TIMESTAMPDIFF(MINUTE, NOW(), timeLimit)";
			stmt.executeQuery(query);
			ResultSet rset = stmt.executeQuery(query);

			if (rset.next()) {
				tempUser.setExists("exist");
			} else {
				tempUser.setExists("Not Exist");
			}
			// userList.add(tempUser);
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempUser;
	}

	///////////////// Checking if group already exists (before creating
	///////////////// group)/////////////////////////

	@Override
	public ExistsCheck findGroup(String groupName) {
		// List<User> userList = new ArrayList<User>();
		ExistsCheck tempUser = new ExistsCheck();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			// query will contain only those that have no time stamp on them
			String query = "select * from users WHERE groupname='" + groupName + "'";
			// String query1="select task from "+groupName+"_tasks where completion= '1' and
			// doing IS NULL and timeLimit <> '0000-00-00 00:00:00' ORDER BY
			// TIMESTAMPDIFF(MINUTE, NOW(), timeLimit)";
			stmt.executeQuery(query);
			ResultSet rset = stmt.executeQuery(query);

			if (rset.next()) {
				tempUser.setExists("exist");
			} else {
				tempUser.setExists("Not Exist");
			}
			// userList.add(tempUser);
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempUser;
	}

	/////////////// CHECKS WHAT WAS THE LAST CHANGE /////////////////////

	@Override
	public Change findLastChange(String groupName) {
		// List<User> userList = new ArrayList<User>();
		Change tempChange = new Change();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			// query will contain only those that have no time stamp on them
			String query = "select * from changes WHERE groupname='" + groupName + "' ORDER BY id DESC LIMIT 1";
			// String query1="select task from "+groupName+"_tasks where completion= '1' and
			// doing IS NULL and timeLimit <> '0000-00-00 00:00:00' ORDER BY
			// TIMESTAMPDIFF(MINUTE, NOW(), timeLimit)";
			stmt.executeQuery(query);
			ResultSet rset = stmt.executeQuery(query);

			while (rset.next()) { // Move the cursor to the next row, return false if no more row
				// arrayTemp[rowCount] = rset.getString("task");
				// ++rowCount;

				tempChange.setId(rset.getInt("id"));
				tempChange.setChangeType(rset.getString("changetype"));

			}
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempChange;
	}

	///// RECORDS CHANGE DONE TO TASK ( CLAIM, ADD, REMOVE, COMPLETE ) ////////////

	@Override
	public Change addChange(Change change) {

		// Change tempChange=new Change();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			// query will contain only those that have no time stamp on them
			String query = "INSERT INTO changes (groupname , changetype) VALUES ('" + change.getGroupName() + "', '"
					+ change.getChangeType() + "') ";
			// String query1="select task from "+groupName+"_tasks where completion= '1' and
			// doing IS NULL and timeLimit <> '0000-00-00 00:00:00' ORDER BY
			// TIMESTAMPDIFF(MINUTE, NOW(), timeLimit)";
			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
			return change;

		} catch (SQLException ex) {
			change.setId(-1);
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			change.setId(-1);
			e.printStackTrace();
		}
		change.setId(-1);
		return change;

	}

	//// Adds New Task To THE DATABASE ////////////

	@Override
	public Task addTask(Task task) {

		// Change tempChange=new Change();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			String query;

			// IF STATEMENT SENDS DIFFRENT QUERIES DEPENDING ON EXISTANCE OF TIMELIMIT
			if (task.getMinutesLeft() == 0 && task.getHoursLeft() == 0 && task.getDaysLeft() == 0) {
				query = "INSERT INTO tasks (groupname , taskname, postedby) VALUES ('" + task.getGroupName() + "', '"
						+ task.getTaskName() + "', '" + task.getPostedBy() + "') ";
				System.out.println(query);
			} else {
				int minutesSummed = (task.getDaysLeft() * 1440) + (task.getHoursLeft() * 60) + (task.getMinutesLeft());
				System.out.println(minutesSummed);
				query = "INSERT INTO tasks (groupname , taskname, postedby, timelimit) VALUES ('" + task.getGroupName()
						+ "', '" + task.getTaskName() + "', '" + task.getPostedBy() + "', NOW() + INTERVAL "
						+ minutesSummed + " MINUTE) ";
			}
			// String query1="select task from "+groupName+"_tasks where completion= '1' and
			// doing IS NULL and timeLimit <> '0000-00-00 00:00:00' ORDER BY
			// TIMESTAMPDIFF(MINUTE, NOW(), timeLimit)"; NOW() + INTERVAL "+due+" HOUR
			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return task;
	}
	///// Adding new group to the database

	public User addGroup(User user) {
		// TODO Auto-generated method stub

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			String query;

			query = "INSERT INTO users (groupname) VALUES ('" + user.getGroupName() + "')";

			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addFirstUser(user);
		return user;

	}

	// Adding New User To The Group///////////
	// ****** This method is calles by method abve when creating new
	// group*****///////////////

	@Override
	public User addFirstUser(User user) {
		// TODO Auto-generated method stub

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			String query;

			query = "Update users set username='" + user.getUserName() + "', password='" + user.getPassword() + "', admin='" + user.getAdmin() 
					+ "' WHERE groupname = '" + user.getGroupName() + "'";
			System.out.println(query);

			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	///// MEthod to add aditional users to the group//////////////
	@Override
	public User addNextUser(User user) {
		// TODO Auto-generated method stub

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			String query;

			query = "INSERT INTO users (groupname, username, password, admin) VALUES ('" + user.getGroupName() + "','"
					+ user.getUserName() + "', '" + user.getPassword() + "', '" + user.getAdmin() + "')";
			System.out.println(query);

			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	// Adding next users to the group

	/// Updating tasks with new info (claimed, completed, etc)///////////
	@Override
	public Task updateTask(Task task) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			String query;

			if (task.getCompletedBy() != null && task.getClaimedBy() == null) {
				query = "UPDATE tasks set completedby='" + task.getCompletedBy() + "' WHERE groupname='"
						+ task.getGroupName() + "' AND taskname='" + task.getTaskName() + "'";
			}

			else if (task.getCompletedBy() == null && task.getClaimedBy() != null) {
				query = "UPDATE tasks set claimedby='" + task.getClaimedBy() + "' WHERE groupname='"
						+ task.getGroupName() + "' AND taskname='" + task.getTaskName() + "'";

			} else if (task.getCompletedBy() == null && task.getClaimedBy() == null) {
				query = "UPDATE tasks set claimedby=null WHERE groupname='" + task.getGroupName() + "' AND taskname='"
						+ task.getTaskName() + "'";

			}

			else {
				query = "UPDATE tasks set completedby = '" + task.getCompletedBy() + "', claimedby = '"
						+ task.getClaimedBy() + "' WHERE groupname = '" + task.getGroupName() + "' AND taskname = '"
						+ task.getTaskName() + "'";

			}
			// "UPDATE "+groupName+"_tasks set completion= '0' where task='"+task+"'";

			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
			return task;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		task.setId(-1);
		return task;

	}

	//////////////// Updating user ( to change password)

	@Override
	public User updateUser(User user) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			String query;

			query = "UPDATE users set password = '" + user.getPassword() + "' WHERE groupname = '" + user.getGroupName()
					+ "' AND username = '" + user.getUserName() + "'";

			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
			return user;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		user.setId(-1);
		return user;

	}
	
	
	@Override
	public User updateOtherUser(User user) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			String query;
			
			System.out.println("updating: " +user.getUserName()+" " + user.getGroupName());

			query = "UPDATE users set admin = 1 WHERE groupname = '" + user.getGroupName()
					+ "' AND username = '" + user.getUserName() + "'";

			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
			return user;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		user.setId(-1);
		return user;

	}

	// DELETING TASKS///////////////////////////////
	@Override
	public void delete(int id) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			String query;

			query = "DELETE FROM tasks where id='" + id + "'";

			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return null;

	}
	
	
	
	/////////// Deleting user
	@Override
	public void deleteUser(String username, String groupname) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			String query;

			query = "DELETE FROM users where username='" + username + "' AND groupname = '"+groupname+"'";
			System.out.println("Deleting user:" + username+" "+ groupname);

			stmt.executeUpdate(query);

			// tempChange.setChangeType(changeType);
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return null;

	}

	@Override
	public ExistsCheck checkTask(Task task) {
		ExistsCheck tempTask = new ExistsCheck();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Step 1: Allocate a database 'Connection' object
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donedoo?useSSL=false", "test",
					"test");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			Statement stmt = conn.createStatement();
			// query will contain only those that have no time stamp on them
			String query = "select * from tasks WHERE groupname='" + task.getGroupName() + "' AND taskname='"
					+ task.getTaskName() + "'";
			// String query1="select task from "+groupName+"_tasks where completion= '1' and
			// doing IS NULL and timeLimit <> '0000-00-00 00:00:00' ORDER BY
			// TIMESTAMPDIFF(MINUTE, NOW(), timeLimit)";
			stmt.executeQuery(query);
			ResultSet rset = stmt.executeQuery(query);

			if (rset.next()) {
				tempTask.setExists("exist");
			} else {
				tempTask.setExists("Not Exist");
			}
			// userList.add(tempUser);
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempTask;
	}

}
