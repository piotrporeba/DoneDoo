package com.pluralsight;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pluralsight.model.Change;
import com.pluralsight.model.Task;
import com.pluralsight.model.User;
import com.pluralsight.model.ExistsCheck;
import com.pluralsight.repository.RepositoryInterface;
import com.pluralsight.repository.RepositoryStub;

// this class handles users from database

@Path("read") // http://localhost:8080/donedoo.services/webapi/users
public class ReadResource {
	private RepositoryInterface repository = new RepositoryStub();

	/// Method that checks if user of specific password exists in database///
	@GET
	@Produces(MediaType.APPLICATION_JSON) // .APPLICATION_XML
	@Path("users/{groupName}/{userName}/{password}") // http://donedoo:8080/excercise.services/webapi/read/users/1/1/1
	public ExistsCheck getUser(@PathParam("groupName") String groupName, @PathParam("userName") String userName,
			@PathParam("password") String password) {

		// System.out.println("conection from android");
		return repository.findGroupUserPassword(groupName, userName, password);
	}

	// checking if group has already user of this name
	@GET
	@Produces(MediaType.APPLICATION_JSON) // .APPLICATION_XML
	@Path("users/{groupName}/{userName}") // http://donedoo:8080/excercise.services/webapi/read/users/1/1
	public ExistsCheck getUser(@PathParam("groupName") String groupName, @PathParam("userName") String userName) {

		return repository.findGroupUser(groupName, userName);
	}

	// checking if group exists
	@GET
	@Produces(MediaType.APPLICATION_JSON) // .APPLICATION_XML
	@Path("users/{groupName}") // http://donedoo:8080/excercise.services/webapi/read/users/1
	public ExistsCheck getUser(@PathParam("groupName") String groupName) {

		return repository.findGroup(groupName);
	}

	/// method that returns list of tasks for specific group//////////
	@GET
	@Produces(MediaType.APPLICATION_JSON) // .APPLICATION_XML
	@Path("tasks/{groupName}") // http://localhost:8080/donedoo.services/webapi/read/tasks/1
	public List<Task> getTasks(@PathParam("groupName") String groupName) {
		// System.out.println("Read tasks called");
		return repository.findAllTasks(groupName);

	}

	/// method that checks for changes in tasks for specific group////
	@GET
	@Produces(MediaType.APPLICATION_JSON) // .APPLICATION_XML
	@Path("changes/{groupName}") // http://localhost:8080/donedoo.services/webapi/read/changes/1
	public Change getChange(@PathParam("groupName") String groupName) {

		return repository.findLastChange(groupName);
	}

	// method that returns list of all users belonging to one group
	@GET
	@Produces(MediaType.APPLICATION_JSON) // .APPLICATION_XML
	@Path("allusers/{groupName}") // http://localhost:8080/donedoo.services/webapi/read/users/1
	public List<User> getAllUsers(@PathParam("groupName") String groupName) {
		// System.out.println("Read tasks called");
		return repository.findAllUsers(groupName);

	}

}
