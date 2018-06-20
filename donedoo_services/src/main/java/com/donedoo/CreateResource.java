package com.pluralsight;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.pluralsight.model.Change;
import com.pluralsight.model.Task;
import com.pluralsight.model.User;
import com.pluralsight.model.ExistsCheck;
import com.pluralsight.repository.RepositoryInterface;
import com.pluralsight.repository.RepositoryStub;

// JSON FILES WHEN POST'ED DONT USE WRAPPER LIKE XML DOES 

@Path("create")
public class CreateResource {
	private RepositoryInterface repository = new RepositoryStub();

	@POST
	@Path("change")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
	public Change createChange(Change change) {

		// If didnt go troug change id will change to -1
		Change change2 = repository.addChange(change);
		if (change2.getId() == -1) {
			System.out.println("id = -1");
		}
		return change2;
	}

	@POST
	@Path("task")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
	public Task createTask(Task task) {
		System.out.println("create called");
		if (repository.checkTask(task).getExists().equals("Not Exist")) {
			repository.addTask(task);
			return task;
		} else
			task.setId(-1);
		return task;
	}

	@POST
	@Path("group")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
	public User createGroupAndUser(User user) {

		System.out.println(
				"create group called by: " + user.getGroupName() + "" + user.getUserName() + "" + user.getPassword());
		// this check will not allow to create group of the same name as one already in
		// database
		if (repository.findGroup(user.getGroupName()).getExists().equals("Not Exist")) {
			repository.addGroup(user); // this method creates new group and calls for another to cereate user aswell
			return user;
		
		} else
			user.setId(-1);
		return user;
	}

	@POST
	@Path("user")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
	public User createNextUser(User user) {

		// checks if user of specific username already exists in the group and if not
		// will create one
		if (repository.findGroupUser(user.getGroupName(), user.getUserName()).getExists().equals("Not Exist")) {
			repository.addNextUser(user);

			return user;
		} else
			user.setId(-1);
		return user;
	}

}
