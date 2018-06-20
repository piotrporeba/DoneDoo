package com.donedoo.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.donedoo.data.Change;
import com.donedoo.data.Confirmation;
import com.donedoo.data.Tasks;
import com.donedoo.data.User;
import com.donedoo.gui.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;

public class ServicesClient {

	static String url = "http://192.168.0.150:8080/donedoo.services/webapi/";

	public ServicesClient() {

		Unirest.setObjectMapper(new ObjectMapper() {
			private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

			public <T> T readValue(String value, Class<T> valueType) {
				try {
					return jacksonObjectMapper.readValue(value, valueType);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			public String writeValue(Object value) {
				try {
					try {
						return jacksonObjectMapper.writeValueAsString(value);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
				return null;
			}
		});
	}

	////////////////////////////////////////////////////////////////

	public Boolean createUser(User user) {
		HttpResponse<User> response;
		try {
			response = Unirest.post(url + "create/user").header("accept", "application/json")
					.header("Content-Type", "application/json").body(user).asObject(User.class);
			if (response.getBody().getId() == -1) {
				// throw new RuntimeException(response.getStatus() + ": there was an error on
				// the server.");
				return false;
			}

			return true;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	//////////////////////////////////////////////////////////////

	public static boolean createGroup(User user) {

		HttpResponse<User> response;
		try {
			response = Unirest.post(url + "create/group").header("accept", "application/json")
					.header("Content-Type", "application/json").body(user).asObject(User.class);
			if (response.getBody().getId() == -1) {
				// throw new RuntimeException(response.getStatus() + ": there was an error on
				// the server.");
				return false;
			}

			return true;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	////////////////////////////////////////////////////

	public static boolean createChange(Change change) {

		HttpResponse<Change> response;
		try {
			response = Unirest.post(url + "create/change").header("accept", "application/json")
					.header("Content-Type", "application/json").body(change).asObject(Change.class);
			if (response.getBody().getId() == -1) {
				// throw new RuntimeException(response.getStatus() + ": there was an error on
				// the server.");
				return false;
			}

			return true;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	/////////////////////////////////////////////////////////////

	public static boolean createTask(Tasks task) {

		HttpResponse<Tasks> response;
		try {
			response = Unirest.post(url + "create/task").header("accept", "application/json")
					.header("Content-Type", "application/json").body(task).asObject(Tasks.class);
			if (response.getBody().getId() == -1) {
				// throw new RuntimeException(response.getStatus() + ": there was an error on
				// the server.");
				return false;
			}

			return true;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	////////////////////////////////////////////////////////////

	public static boolean updateTask(Tasks task) {

		HttpResponse<Tasks> response;
		try {
			response = Unirest.put(url + "update/task/").header("accept", "application/json")
					.header("Content-Type", "application/json").body(task).asObject(Tasks.class);
			if (response.getStatus() != 200) {
				// throw new RuntimeException(response.getStatus() + ": there was an error on
				// the server.");
				return false;
			}

			return true;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public static boolean updateUser(User user) {

		HttpResponse<User> response;
		try {
			response = Unirest.put(url + "update/user/").header("accept", "application/json")
					.header("Content-Type", "application/json").body(user).asObject(User.class);
			if (response.getBody().getId() == -1) {
				// throw new RuntimeException(response.getStatus() + ": there was an error on
				// the server.");
				return false;
			}

			return true;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	
	public static boolean updateOtherUser(User user) {

		HttpResponse<User> response;
		try {
			response = Unirest.put(url + "update/otheruser/").header("accept", "application/json")
					.header("Content-Type", "application/json").body(user).asObject(User.class);
			if (response.getBody().getId() == -1) {
				// throw new RuntimeException(response.getStatus() + ": there was an error on
				// the server.");
				return false;
			}

			return true;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public List<Tasks> getTasks(String groupName) {
		HttpResponse<Tasks[]> response;
		try {
			response = Unirest.get(url + "read/tasks/" + groupName).asObject(Tasks[].class);
			Tasks[] tasks = response.getBody();
			List<Tasks> taskList = new ArrayList();
			for (int i = 0; i < tasks.length; i++) {

				taskList.add(tasks[i]);

			}
			return taskList;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public List<User> getUsers(String groupName) {
		HttpResponse<User[]> response;
		try {
			response = Unirest.get(url + "read/allusers/" + groupName).asObject(User[].class);
			User[] users = response.getBody();
			List<User> userList = new ArrayList();
			for (int i = 0; i < users.length; i++) {

				userList.add(users[i]);

			}
			return userList;

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public Change getChange(String groupName) {
		HttpResponse<Change> response;
		try {
			response = Unirest.get(url + "read/changes/" + groupName).asObject(Change.class);
			Change change = response.getBody();
			return change;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// check if all three values match db entry
	public static boolean getGroupUserPassword(String groupName, String userName, String password) {
		try {
			HttpResponse<Confirmation> response = Unirest
					.get(url + "read/users/" + groupName + "/" + userName + "/" + password)
					.asObject(Confirmation.class);
			if (response.getBody().getExists().equals("1")) {
				Data.setIsAdmin(1);
				System.out.println("admin = " +Data.getIsAdmin());
				return true;
			}
			else if (response.getBody().getExists().equals("0")) {
				Data.setIsAdmin(0);
				return true;
			}

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static void delete(int id) {

		HttpResponse<String> response;

		try {
			response = Unirest.delete(url + "delete/{id}").routeParam("id", String.valueOf(id)).asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void deleteUser(String groupName, String userName) {

		HttpResponse<String> response;

		try {
			response = Unirest.delete(url + "delete/user/{groupName}/{userName}").
					routeParam("groupName", String.valueOf(groupName)).
					routeParam("userName", String.valueOf(userName)).asString();
					
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}