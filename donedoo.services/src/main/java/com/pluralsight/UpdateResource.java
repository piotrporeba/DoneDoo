package com.pluralsight;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.pluralsight.model.Change;
import com.pluralsight.model.Task;
import com.pluralsight.model.User;
import com.pluralsight.model.ExistsCheck;
import com.pluralsight.repository.RepositoryInterface;
import com.pluralsight.repository.RepositoryStub;

// JSON FILES WHEN POST'ED DONT USE WRAPPER LIKE XML DOES 

@Path("update")
public class UpdateResource {
	private RepositoryInterface repository = new RepositoryStub();

	@PUT
	@Path("task") // http://localhost:8080/excercise.services/webapi/update/task +JSON OBJECT INFO
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
	public Task updateTask(Task task) {

		if (repository.updateTask(task).getId() != -1) {
			return task;
		} else
			task.setId(-1);
		return task;

	}

	@PUT
	@Path("user") // http://localhost:8080/excercise.services/webapi/update/task +JSON OBJECT INFO
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
	public User updateUser(User user) {

		if (repository.updateUser(user).getId() != -1) {
			return user;
		} else
			user.setId(-1);
		return user;

	}
	
	@PUT
	@Path("otheruser") // http://localhost:8080/excercise.services/webapi/update/task +JSON OBJECT INFO
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
	public User updateOtherUser(User user) {
		System.out.println("otheruser wlazlo");

		if (repository.updateOtherUser(user).getId() != -1) {
			System.out.println("priviledge changed");
			return user;
		} else
			user.setId(-1);
		return user;

	}

}
