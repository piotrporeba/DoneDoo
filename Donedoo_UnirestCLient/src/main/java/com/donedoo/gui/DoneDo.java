package com.donedoo.gui;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.swing.JOptionPane;

import com.donedoo.client.ServicesClient;
import com.donedoo.data.Change;
import com.donedoo.data.Tasks;
import com.donedoo.data.User;

import javafx.application.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class DoneDo extends Application {

	Scene loginScene, adminScene;
	static Button addUserButton, createUserButton, addTaskButton, adminButton3, goToDone, goToDo, createTaskButton,
			changePasswordButton, cancel, listUsersButton;
	static Button completeTaskButton, claimTaskButton, removeTaskButton, unclaimTaskButton, assignTaskButton, removeUserButton, makeAdminButton; 
	static CheckBox isAdminCheckBox;
	static VBox doList, doneList, adminMenu, switchList, userList;
	static GridPane loginGrid;
	static BorderPane adminPane;
	static ScrollPane scrollDo, scrollDone, scrollUsers;
	static GridPane addUserPane, addTaskPane, taskPane, userPane, changePasswordPane;
	static TextField userNameTF, passwordTF, passwordTF1, taskNameTF, taskDueTimeTF, newPasswordTF;
	static ServicesClient client;

	static Alert alertSomethingWrong;
	static DialogPane dialogPane;

	static Spinner<Integer> minuteSpinner, hourSpinner, daySpinner;

	Dialog<Pair<String, String>> createUserDialog, createTaskDialog, handleTaskDialog;
	static String time;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		client = new ServicesClient();

		////////////////// Login window
		////////////////// //////////////////////////////////////////////////////
		primaryStage.setTitle("DoneDoo");

		loginGrid = new GridPane();
		loginGrid.setAlignment(Pos.CENTER);
		loginGrid.setHgap(10);
		loginGrid.setVgap(10);
		loginGrid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("DoneDoo");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		loginGrid.add(scenetitle, 0, 0, 2, 1);

		Label groupName = new Label("Group Name:");
		loginGrid.add(groupName, 0, 1);

		TextField groupTextField = new TextField();
		groupTextField.setPromptText("Group Name");
		loginGrid.add(groupTextField, 1, 1);

		Label userName = new Label("User Name:");
		loginGrid.add(userName, 0, 2);

		TextField userTextField = new TextField();
		userTextField.setPromptText("User Name");
		loginGrid.add(userTextField, 1, 2);

		Label pw = new Label("Password:");
		loginGrid.add(pw, 0, 3);

		PasswordField passwordTextField = new PasswordField();
		passwordTextField.setPromptText("Password");
		loginGrid.add(passwordTextField, 1, 3);

		Button loginBTN = new Button("Sign in");
		Button createBTN = new Button("Create Group");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		loginGrid.add(hbBtn, 1, 5);

		final Text actiontarget = new Text();
		actiontarget.setFill(Color.FIREBRICK);
		actiontarget.setText("welcome to donedoo");
		
		final Text actiontarget1 = new Text();
		actiontarget1.setFill(Color.FIREBRICK);
		actiontarget1.setText("welcome to donedoo");
		
		loginGrid.add(actiontarget1, 1, 6);
		hbBtn.getChildren().add(loginBTN);
		hbBtn.getChildren().add(createBTN);

		// button for logging in
		loginBTN.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Signing in");

				boolean groupcheck;
				boolean usercheck;
				boolean passcheck;

				// client = new ServicesClient();

				if (client.getGroupUserPassword(groupTextField.getText(), userTextField.getText(),
						passwordTextField.getText())) {
					// setting data to Data class
					Data.setGroupName(groupTextField.getText());
					Data.setUserName(userTextField.getText());
					Data.setPassword(passwordTextField.getText());
					actiontarget.setText(
							"Signined in as: " + userTextField.getText() + "  of group: " + Data.getGroupName());
					// changing sceeneto adminsceene if connection ok and login ok
					if(Data.getIsAdmin()==1) {
					adminMenu.getChildren().addAll( addTaskButton,addUserButton,changePasswordButton, listUsersButton);
					}
					else {
						adminMenu.getChildren().addAll(addTaskButton, changePasswordButton, listUsersButton);
					}

					primaryStage.setScene(adminScene);
					primaryStage.setTitle("DoneDoo");
					primaryStage.show();

					RefreshTasks.refresh(); // start thread
					RefreshTimeThread.refresh();

				} else {
					alertSomethingWrong.showAndWait();
				}
			}
		});

		// button for creating new group
		createBTN.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontarget.setFill(Color.FIREBRICK);
				User user = new User();
				user.setGroupName(groupTextField.getText());
				user.setUserName(userTextField.getText());
				user.setPassword(passwordTextField.getText());
				user.setAdmin(1);

				// client = new ServicesClient();

				if (client.createGroup(user)) {
					Change change = new Change();
					change.setChangeType("1");
					change.setGroupName(groupTextField.getText());
					client.createChange(change);

					actiontarget1.setText("Group created,now You can log in");
					Data.setGroupName(groupTextField.getText());
					Data.setUserName(userTextField.getText());
					Data.setPassword(passwordTextField.getText());
					// }
				} else {
					alertSomethingWrong.showAndWait();
				}

			}
		});

		/////////////////////// CreateUser_Pane/////////////////////////////////////////////////

		createUserButton = new Button("Create");
		createUserButton.setTranslateX(70);
		createUserButton.setOnAction(e -> {

			User user = new User();
			user.setGroupName(Data.getGroupName());
			user.setUserName(userNameTF.getText());
			user.setPassword(passwordTF1.getText());
			if(isAdminCheckBox.isSelected()){
				user.setAdmin(1);
			}
			else
			user.setAdmin(0);
					
			
			// client = new ServicesClient();
			// CreateTask_DB.cre(Data.getGroupName(), Data.getTask(), Data.getIp(),
			// Data.getDbName());
			if (client.createUser(user)) {

				actiontarget.setText("new user created");
				if (Data.getCurrentPane().equals("Do"))
					adminPane.setCenter(scrollDo);
				else
					adminPane.setCenter(scrollDone);
			} else
				alertSomethingWrong.showAndWait();

		});

		cancel = new Button("Cancel");
		cancel.setTranslateX(70);
		cancel.setOnAction(e -> {

			if (Data.getCurrentPane().equals("Do"))
				adminPane.setCenter(scrollDo);
			else
				adminPane.setCenter(scrollDone);

		});

		addUserPane = new GridPane();
		addUserPane.setHgap(10);
		addUserPane.setVgap(10);
		addUserPane.setPadding(new Insets(20, 15, 10, 10));

		userNameTF = new TextField();
		userNameTF.setPromptText("User Name");
		passwordTF1 = new TextField();
		passwordTF1.setPromptText("new user Password");
		
		
		isAdminCheckBox = new CheckBox();
	
		addUserPane.add(new Label("User Name:"), 0, 0);
		addUserPane.add(userNameTF, 1, 0);
		addUserPane.add(new Label("Password:"), 0, 1);
		addUserPane.add(passwordTF1, 1, 1);
		
		addUserPane.add(new Label("Admin Role?"), 0, 2);
		addUserPane.add(isAdminCheckBox, 1, 2);
		
		
		addUserPane.add(createUserButton, 0, 3);
		addUserPane.add(cancel, 1, 3);

		//////////////////////////// Change Password Pane /////////////////////////////

		changePasswordButton = new Button("Confirm");
		changePasswordButton.setTranslateX(70);
		changePasswordButton.setOnAction(e -> {

			User user = new User();
			user.setGroupName(Data.getGroupName());
			user.setUserName(Data.getUserName());
			user.setPassword(newPasswordTF.getText());

			// client = new ServicesClient();
			// CreateTask_DB.cre(Data.getGroupName(), Data.getTask(), Data.getIp(),
			// Data.getDbName());
			if (Data.getPassword().equals(passwordTF.getText())) {

				if (client.updateUser(user)) {
					actiontarget.setText("password Changed");
					Data.setPassword(newPasswordTF.getText());
					if (Data.getCurrentPane().equals("Do"))
						adminPane.setCenter(scrollDo);
					else
						adminPane.setCenter(scrollDone);
				} else {
					alertSomethingWrong.showAndWait();
					actiontarget.setText("password wasn't Changed");
				}
			} else {
				actiontarget.setText("incorrect password entered");
				alertSomethingWrong.showAndWait();

			}

		});

		cancel = new Button("Cancel");
		cancel.setTranslateX(70);
		cancel.setOnAction(e -> {

			if (Data.getCurrentPane().equals("Do"))
				adminPane.setCenter(scrollDo);
			else
				adminPane.setCenter(scrollDone);

		});

		changePasswordPane = new GridPane();
		changePasswordPane.setHgap(10);
		changePasswordPane.setVgap(10);
		changePasswordPane.setPadding(new Insets(20, 15, 10, 10));

		passwordTF = new TextField();
		passwordTF.setPromptText("Old Password");

		newPasswordTF = new TextField();
		newPasswordTF.setPromptText("New Password");

		changePasswordPane.add(new Label("Old Password:"), 0, 0);
		changePasswordPane.add(passwordTF, 1, 0);
		changePasswordPane.add(new Label("New Password:"), 0, 1);
		changePasswordPane.add(newPasswordTF, 1, 1);

		changePasswordPane.add(changePasswordButton, 0, 2);
		changePasswordPane.add(cancel, 1, 2);

		//////////////////////////// CREATE TASK
		//////////////////////////// DIALOG//////////////////////////////////////////

		createTaskButton = new Button("Create");
		createTaskButton.setTranslateX(70);
		createTaskButton.setOnAction(e -> {
			Tasks task = new Tasks();
			task.setGroupName(Data.getGroupName());
			task.setPostedBy(Data.getUserName());
			task.setTaskName(taskNameTF.getText());
			// task.setTimeLimit(taskDueTimeTF.getText());
			task.setMinutesLeft(minuteSpinner.getValue());
			task.setHoursLeft(hourSpinner.getValue());
			task.setDaysLeft(daySpinner.getValue());

			// client = new ServicesClient();

			if (client.createTask(task)) {

				Change change = new Change();
				change.setChangeType("1");
				change.setGroupName(groupTextField.getText());
				client.createChange(change);

				actiontarget.setText("new task created");
				if (Data.getCurrentPane().equals("Do"))
					adminPane.setCenter(scrollDo);
				else
					adminPane.setCenter(scrollDone);
			} else {
				alertSomethingWrong.setContentText(" Task already exists ");
				alertSomethingWrong.showAndWait();
				alertSomethingWrong.setContentText("oops, something went wrong ");

			}

		});

		cancel = new Button("Cancel");
		cancel.setId("burron5");
		cancel.setTranslateX(70);
		cancel.setOnAction(e -> {

			if (Data.getCurrentPane().equals("Do"))
				adminPane.setCenter(scrollDo);
			else
				adminPane.setCenter(scrollDone);

		});

		addTaskPane = new GridPane();
		addTaskPane.setId("addTaskPane");
		addTaskPane.setHgap(10);
		addTaskPane.setVgap(10);
		addTaskPane.setPadding(new Insets(20, 15, 10, 10));

		taskNameTF = new TextField();
		taskNameTF.setPromptText("New task");

		taskDueTimeTF = new TextField();
		taskDueTimeTF.setPromptText("hours to complete");

		/////// adding spinners representing time

		minuteSpinner = new Spinner<Integer>();
		// minuteSpinner.setEditable(true);
		// Value factory.
		SpinnerValueFactory<Integer> minuteValueFactory = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
		minuteSpinner.setValueFactory(minuteValueFactory);

		hourSpinner = new Spinner<Integer>();
		// hourSpinner.setEditable(true);
		// Value factory.
		SpinnerValueFactory<Integer> hourValueFactory = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
		hourSpinner.setValueFactory(hourValueFactory);

		daySpinner = new Spinner<Integer>();
		// daySpinner.setEditable(true);
		// Value factory.
		SpinnerValueFactory<Integer> dayValueFactory = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 364, 0);
		daySpinner.setValueFactory(dayValueFactory);

		addTaskPane.add(new Label("New Task:"), 0, 0);
		addTaskPane.add(taskNameTF, 1, 0);
		// addTaskPane.add(new Label("hours to complete:"),0,1);
		// addTaskPane.add(taskDueTimeTF,1,1);
		addTaskPane.add(new Label("optional Time Limit"), 0, 1);

		addTaskPane.add(new Label("minutes"), 0, 3);
		addTaskPane.add(minuteSpinner, 1, 3);
		addTaskPane.add(new Label("hours"), 0, 4);
		addTaskPane.add(hourSpinner, 1, 4);
		addTaskPane.add(new Label("days"), 0, 5);
		addTaskPane.add(daySpinner, 1, 5);
		addTaskPane.add(createTaskButton, 0, 7);
		addTaskPane.add(cancel, 1, 7);

		// Request focus on the taskname field by default.
		Platform.runLater(() -> taskNameTF.requestFocus());

		// MERGING
		// CODE____________________________________________________________________________________

		// -------------------------------- separate task display options

		completeTaskButton = new Button("Complete");
		completeTaskButton.setId("burron5");
		claimTaskButton = new Button("Claim Task");
		claimTaskButton.setId("burron5");
		removeTaskButton = new Button("Remove");
		removeTaskButton.setId("burron5");
		cancel = new Button("Cancel");
		cancel.setId("burron5");
		unclaimTaskButton = new Button("Unclaim Task");
		unclaimTaskButton.setId("burron5");
		assignTaskButton = new Button("Assign Task to Coleague");
		assignTaskButton.setId("burron5");
		
		removeUserButton= new Button("Remove User");
		removeUserButton.setId("button5");
		makeAdminButton= new Button("Promote to Admin");
		makeAdminButton.setId("button5");
		
		

		taskPane = new GridPane();
		taskPane.setId("taskPane");
		taskPane.setHgap(10);
		taskPane.setVgap(10);
		taskPane.setPadding(new Insets(20, 15, 10, 10));
		
		
		userPane = new GridPane();
		userPane.setId("userPane");
		userPane.setHgap(10);
		userPane.setVgap(10);
		userPane.setPadding(new Insets(20, 15, 10, 10));

		alertSomethingWrong = new Alert(AlertType.NONE, "oops, something went wrong", new ButtonType("OK"));
		// alertSomethingWrong.initStyle(StageStyle.TRANSPARENT);

		// --------------------------------

		// 4 boxes of lists layout---------------------
		doList = new VBox(10);
		doList.setId("l1");
		doList.setPadding(new Insets(0, 0, 50, 0));
		scrollDo = new ScrollPane();
		scrollDo.setContent(doList);
		scrollDo.setId("scroll");
		// scrollDo.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		doneList = new VBox(30);
		doneList.setId("l2");
		doneList.setPadding(new Insets(0, 0, 50, 0));
		scrollDone = new ScrollPane();
		scrollDone.setContent(doneList);
		scrollDone.setId("scroll");
		// scrollDone.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		userList = new VBox(10);
		userList.setId("l3");
		userList.setPadding(new Insets(0, 0, 50, 0));
		scrollUsers = new ScrollPane();
		scrollUsers.setContent(userList);
		scrollUsers.setId("scroll");

		adminMenu = new VBox(20);
		adminMenu.setId("adminMenu");
		switchList = new VBox(30);
		switchList.setId("switchList");
		switchList.setPadding(new Insets(0, 0, 10, 0));

		// ----------------------------------------------

		// adding those boxes to lists pane--------------
		adminPane = new BorderPane();
		adminPane.setCenter(scrollDo);
		adminPane.setRight(adminMenu);
		adminPane.setTop(switchList);
		adminPane.setBottom(actiontarget);

		// ----------------------------------------------

		// creating scenes-------------------------------
		adminScene = new Scene(adminPane, 400, 500);
		adminScene.getStylesheets().add("application.css");
		adminScene.setFill(Color.TRANSPARENT);
		// ----------------------------------------------

		// final display options-------------------------
		// primaryStage.setScene(adminScene);
		// primaryStage.setTitle("tasks");
		// primaryStage.show();
		loginScene = new Scene(loginGrid, 500, 375);
		loginScene.getStylesheets().add("application.css");
		primaryStage.setScene(loginScene);
		primaryStage.show();
		// ----------------------------------------------

		// refreshDo();
		// refreshDone();

		// add buttons to AdminScene > adminMenu

		addUserButton = new Button("Add new User");
		addUserButton.setOnAction(e -> {
			
			adminPane.setCenter(addUserPane);
			
		});

		addTaskButton = new Button("Add new Task");
		addTaskButton.setOnAction(e -> {
			// CreateTask_DB.create(Data.getGroupName(), Data.getTask(), Data.getIp(),
			// Data.getDbName());
			adminPane.setCenter(addTaskPane);
		});

		changePasswordButton = new Button("Change\n Password");
		changePasswordButton.setOnAction(e -> {
			// CreateTask_DB.create(Data.getGroupName(), Data.getTask(), Data.getIp(),
			// Data.getDbName());
			adminPane.setCenter(changePasswordPane);
		});
		
		listUsersButton = new Button("List Users");
		listUsersButton.setOnAction(e -> {
			// CreateTask_DB.create(Data.getGroupName(), Data.getTask(), Data.getIp(),
			// Data.getDbName());
			refreshGrupUsers();
			
			adminPane.setCenter(scrollUsers);
		});
		
		
		
		//adminMenu.getChildren().addAll(addUserButton, adminButton2, changePasswordButton);
			
		
	
		// --------------------------------------------------------------------

		// add buttons to change between lists done / do
		goToDone = new Button("<< Go To Done ");
		goToDone.setId("goToDone");
		goToDone.setOnAction(e -> {

			// adminPane.getChildren().clear();
			adminPane.setRight(adminMenu);
			adminPane.setTop(switchList);
			adminPane.setCenter(scrollDone);
			switchList.getChildren().clear();
			switchList.getChildren().add(goToDo);
			Data.setCurrentPane("Done");
		});
		goToDo = new Button("<< Go To Do     ");
		goToDo.setId("goToDo");
		goToDo.setOnAction(e -> {

			// adminPane.getChildren().clear();
			adminPane.setRight(adminMenu);
			adminPane.setTop(switchList);
			adminPane.setCenter(scrollDo);
			switchList.getChildren().clear();
			switchList.getChildren().add(goToDone);
			Data.setCurrentPane("Do");
		});

		switchList.getChildren().addAll(goToDone);
	}// end of start
	// -----------------------------------------------------------------------

	// populating task lists-------------------------------------------------
	public static void refreshDo() {
		doList.getChildren().clear();
		// client = new ServicesClient();
		Data.setTasks(client.getTasks(Data.getGroupName()));
		// Data.setDoClaimedTasks(ListClaimedTasks_DB.list(Data.getGroupName(),
		// Data.getIp(), Data.getDbName()));
		// Data.setDoClaims(ListClaim_DB.list(Data.getGroupName(), Data.getIp(),
		// Data.getDbName()));
		listDoTasks();
		listDoNoLimitTasks();
		listDoClaimedTasks();
		listDoClaimedNoLimitTasks();
	}

	public static void refreshDone() {
		doneList.getChildren().clear();
		// client = new ServicesClient();
		// Data.setDoneTasks(ListCompleteTasks_DB.list(Data.getGroupName(),
		// Data.getIp(), Data.getDbName()));
		Data.setTasks(client.getTasks(Data.getGroupName()));
		listDoneTasks();

	}

	
	/////////// 2 methods below handle printing list of users to the screen
	public static void refreshGrupUsers() {
		userList.getChildren().clear();
		
		Data.setAllUsers(client.getUsers(Data.getGroupName()));
		listGroupUsers();

	}
	

	public static void listGroupUsers() {
		for (int i = 0; i < Data.getAllUsers().size(); i++) {
			User currUser = Data.getAllUsers().get(i);
			final int j = i;
			Button button1 = new Button(currUser.getUserName());
			button1.setId("button4");
			if(currUser.getAdmin()==1) {
				button1.setText(currUser.getUserName()+" (Admin)");
				button1.setId("button2");
				
			}
		
			
			button1.setOnAction(e -> {
				userPane.getChildren().clear();
				Label nameLabel = new Label(currUser.getUserName());
				nameLabel.setTextFill(Color.web("b34700"));
				nameLabel.setFont(Font.font("Cambria", 24));
				nameLabel.setMaxWidth(Double.MAX_VALUE);
				nameLabel.setAlignment(Pos.CENTER);
				userPane.add(nameLabel, 0, 0);
				adminPane.setCenter(userPane);
				if(currUser.getAdmin()!=1) {
					
				userPane.add(makeAdminButton, 0, 4);
				userPane.add(removeUserButton, 0, 5);
				
				}
				else {
					
					nameLabel.setText(currUser.getUserName()+" (Admin)");
					
				}
				
				adminPane.setCenter(userPane);
				
				
			});
			makeAdminButton.setOnAction(f -> {
				   // client = new ServicesClient()
					currUser.setAdmin(1);
					currUser.setGroupName(Data.getGroupName());
					currUser.setUserName(button1.getText());
					
					if(client.updateOtherUser(currUser)) {
						System.out.println("user changed to admin role");
					}
					
					
			});
			
			removeUserButton.setOnAction(f -> {
				   // client = new ServicesClient();
					client.deleteUser(Data.getGroupName(), currUser.getUserName());
							
					
			});
			
			
			
			
			

			userList.getChildren().add(button1);
		}
	}
	
	
	
	
	
	
	
	
	
	
	///// 2 methods below handle assigning task to user
	
	public static void refreshListAllUsers(Tasks currTask) {
		userList.getChildren().clear();
		// client = new ServicesClient();
		// Data.setDoneTasks(ListCompleteTasks_DB.list(Data.getGroupName(),
		// Data.getIp(), Data.getDbName()));
		Data.setAllUsers(client.getUsers(Data.getGroupName()));
		listAllUsers(currTask);

	}
	
	
	////////////////// Listing all users belonging to the gruoup
	////////////////// //////////////////////////////////////
	public static void listAllUsers(Tasks currTask) {
		for (int i = 0; i < Data.getAllUsers().size(); i++) {
			User currUser = Data.getAllUsers().get(i);
			final int j = i;
			Button button1 = new Button(currUser.getUserName());
			button1.setId("button4");
			button1.setOnAction(e -> {
				// send update to the database
				currTask.setClaimedBy(currUser.getUserName());
				// client = new ServicesClient();
				client.updateTask(currTask);

				Change change = new Change();
				change.setGroupName(Data.getGroupName());
				change.setChangeType("3");
				client.createChange(change);

				adminPane.setCenter(scrollDo);
			});
			userList.getChildren().add(button1);
		}
	}

	// Listing all do tasks----------------------------------------------------
	public static void listDoTasks() {

		for (int i = 0; i < Data.getTasks().size(); i++) {

			Tasks currTask = Data.getTasks().get(i);
			Change change = new Change();
			change.setGroupName(Data.getGroupName());

			if (currTask.getCompletedBy() == null && currTask.getClaimedBy() == null
					&& currTask.getTimeLimit() != null) {

				try {
					time = getTimeLeft(currTask.getTimeLimit());

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				final int j = i;
				Button button1 = new Button(Data.getTasks().get(i).getTaskName() + "\n" + time);

				button1.setId("button1");
				button1.setOnAction(e -> {
					taskPane.getChildren().clear();
					adminPane.setCenter(taskPane);
					Label taskNameLabel = new Label(Data.getTasks().get(j).getTaskName());
					Label timeLimitLabel = new Label("due: " + Data.getTasks().get(j).getTimeLimit().substring(0,
							Data.getTasks().get(j).getTimeLimit().length() - 5));
					Label postedBy = new Label("Posted by: " + Data.getTasks().get(j).getPostedBy());

					taskNameLabel.setTextFill(Color.web("b34700"));
					taskNameLabel.setFont(Font.font("Cambria", 24));
					taskNameLabel.setMaxWidth(Double.MAX_VALUE);
					taskNameLabel.setAlignment(Pos.CENTER);
					timeLimitLabel.setTextFill(Color.web("b34700"));
					timeLimitLabel.setFont(Font.font("Cambria", 14));
					postedBy.setTextFill(Color.web("b34700"));
					postedBy.setFont(Font.font("Cambria", 14));

					taskPane.add(taskNameLabel, 0, 0);
					taskPane.add(timeLimitLabel, 0, 1);
					taskPane.add(postedBy, 0, 2);

					if(Data.getIsAdmin()==1) {
						taskPane.add(completeTaskButton, 0, 3);
						taskPane.add(claimTaskButton, 0, 4);
						taskPane.add(removeTaskButton, 0, 5);
						taskPane.add(assignTaskButton, 0, 6);
						taskPane.add(cancel, 0, 8);
						}
						else {
							taskPane.add(completeTaskButton, 0, 3);
							taskPane.add(claimTaskButton, 0, 4);
							taskPane.add(removeTaskButton, 0, 5);
							taskPane.add(cancel, 0, 7);
						}

					completeTaskButton.setOnAction(f -> {
						// client = new ServicesClient();
						currTask.setCompletedBy(Data.getUserName());
						client.updateTask(currTask);

						change.setChangeType("2");
						client.createChange(change);

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

					claimTaskButton.setOnAction(f -> {
						// client = new ServicesClient();
						currTask.setClaimedBy(Data.getUserName());
						client.updateTask(currTask);

						change.setChangeType("3");
						client.createChange(change);

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

					removeTaskButton.setOnAction(f -> {
						// client = new ServicesClient();
						client.delete(currTask.getId());

						change.setChangeType("4");
						client.createChange(change);

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

					assignTaskButton.setOnAction(f -> {
						refreshListAllUsers(currTask);
						adminPane.setCenter(scrollUsers);

					});

					cancel.setOnAction(f -> {

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

					refreshDo();
					refreshDone();

				});
				doList.getChildren().add(button1);
			}
		}

	}

	public static void listDoNoLimitTasks() {
		for (int i = 0; i < Data.getTasks().size(); i++) {

			Tasks currTask = Data.getTasks().get(i);
			Change change = new Change();
			change.setGroupName(Data.getGroupName());
			final int j = i;

			if (currTask.getCompletedBy() == null && currTask.getClaimedBy() == null
					&& currTask.getTimeLimit() == null) {

				Button button1 = new Button(Data.getTasks().get(i).getTaskName());
				button1.setId("button1");
				button1.setOnAction(e -> {
					taskPane.getChildren().clear();
					adminPane.setCenter(taskPane);
					Label taskNameLabel = new Label(Data.getTasks().get(j).getTaskName());
					Label postedBy = new Label("Posted by: " + Data.getTasks().get(j).getPostedBy());

					taskNameLabel.setTextFill(Color.web("b34700"));
					taskNameLabel.setFont(Font.font("Cambria", 24));
					taskNameLabel.setMaxWidth(Double.MAX_VALUE);
					taskNameLabel.setAlignment(Pos.CENTER);
					postedBy.setTextFill(Color.web("b34700"));
					postedBy.setFont(Font.font("Cambria", 14));

					taskPane.add(taskNameLabel, 0, 0);
					taskPane.add(postedBy, 0, 1);
					
					if(Data.getIsAdmin()==1) {
					taskPane.add(completeTaskButton, 0, 2);
					taskPane.add(claimTaskButton, 0, 3);
					taskPane.add(removeTaskButton, 0, 4);
					taskPane.add(assignTaskButton, 0, 5);
					taskPane.add(cancel, 0, 7);
					}
					else {
						taskPane.add(completeTaskButton, 0, 2);
						taskPane.add(claimTaskButton, 0, 3);
						taskPane.add(removeTaskButton, 0, 4);
						taskPane.add(cancel, 0, 6);
					}

					completeTaskButton.setOnAction(f -> {
						// client = new ServicesClient();
						currTask.setCompletedBy(Data.getUserName());
						client.updateTask(currTask);

						change.setChangeType("2");
						client.createChange(change);

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

					claimTaskButton.setOnAction(f -> {
						// client = new ServicesClient();
						currTask.setClaimedBy(Data.getUserName());
						client.updateTask(currTask);

						change.setChangeType("3");
						client.createChange(change);

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

					removeTaskButton.setOnAction(f -> {
						// client = new ServicesClient();
						client.delete(currTask.getId());

						change.setChangeType("4");
						client.createChange(change);

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

					assignTaskButton.setOnAction(f -> {
						refreshListAllUsers(currTask);
						adminPane.setCenter(scrollUsers);

					});

					cancel.setOnAction(f -> {

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

					refreshDo();
					refreshDone();

				});
				doList.getChildren().add(button1);
			}
		}
	}

	public static void listDoClaimedTasks() {

		for (int i = 0; i < Data.getTasks().size(); i++) {

			Tasks currTask = Data.getTasks().get(i);
			Change change = new Change();
			change.setGroupName(Data.getGroupName());

			final int j = i;

			if (currTask.getClaimedBy() != null && currTask.getCompletedBy() == null
					&& currTask.getTimeLimit() != null) {

				try {
					time = getTimeLeft(currTask.getTimeLimit());

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String claim = Data.getTasks().get(i).getClaimedBy();
				Button button3 = new Button(Data.getTasks().get(i).getTaskName() + "\n" + time);

				if (currTask.getClaimedBy().equals(Data.getUserName())) {
					button3.setId("button4");
				} else {
					button3.setId("button3");
				}
				button3.setOnAction(e -> {
					taskPane.getChildren().clear();
					adminPane.setCenter(taskPane);

					if (claim.equals(Data.getUserName())) {
						Label taskNameLabel = new Label(Data.getTasks().get(j).getTaskName());
						Label timeLimitLabel = new Label("due: " + Data.getTasks().get(j).getTimeLimit().substring(0,
								Data.getTasks().get(j).getTimeLimit().length() - 5));
						Label postedBy = new Label("Posted by: " + Data.getTasks().get(j).getPostedBy());
						Label claimedBy = new Label("Claimed by: " + Data.getTasks().get(j).getClaimedBy());

						taskNameLabel.setTextFill(Color.web("b34700"));
						taskNameLabel.setFont(Font.font("Cambria", 24));
						taskNameLabel.setMaxWidth(Double.MAX_VALUE);
						taskNameLabel.setAlignment(Pos.CENTER);
						timeLimitLabel.setTextFill(Color.web("b34700"));
						timeLimitLabel.setFont(Font.font("Cambria", 14));
						postedBy.setTextFill(Color.web("b34700"));
						postedBy.setFont(Font.font("Cambria", 14));
						claimedBy.setTextFill(Color.web("b34700"));
						claimedBy.setFont(Font.font("Cambria", 14));

						taskPane.add(taskNameLabel, 0, 0);
						taskPane.add(timeLimitLabel, 0, 1);
						taskPane.add(postedBy, 0, 2);
						taskPane.add(claimedBy, 0, 3);

						taskPane.add(completeTaskButton, 0, 4);
						taskPane.add(unclaimTaskButton, 0, 5);
						taskPane.add(removeTaskButton, 0, 6);
						taskPane.add(cancel, 0, 8);

						completeTaskButton.setOnAction(f -> {
							// client = new ServicesClient();
							currTask.setCompletedBy(Data.getUserName());
							client.updateTask(currTask);

							change.setChangeType("2");
							client.createChange(change);

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);
						});

						removeTaskButton.setOnAction(f -> {
							// client = new ServicesClient();
							client.delete(currTask.getId());

							change.setChangeType("4");
							client.createChange(change);

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);
						});

						cancel.setOnAction(f -> {

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);
						});

						unclaimTaskButton.setOnAction(f -> {

							// client = new ServicesClient();
							currTask.setClaimedBy(null);
							client.updateTask(currTask);

							change.setChangeType("1");
							client.createChange(change);

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);

						});

						refreshDo();
						refreshDone();

					} else {
						Label taskNameLabel = new Label(Data.getTasks().get(j).getTaskName());
						Label timeLimitLabel = new Label("due: " + Data.getTasks().get(j).getTimeLimit().substring(0,
								Data.getTasks().get(j).getTimeLimit().length() - 5));
						Label postedBy = new Label("Posted by: " + Data.getTasks().get(j).getPostedBy());
						Label claimedBy = new Label("Claimed by: " + Data.getTasks().get(j).getClaimedBy());

						taskNameLabel.setTextFill(Color.web("b34700"));
						taskNameLabel.setFont(Font.font("Cambria", 24));
						taskNameLabel.setMaxWidth(Double.MAX_VALUE);
						taskNameLabel.setAlignment(Pos.CENTER);
						timeLimitLabel.setTextFill(Color.web("b34700"));
						timeLimitLabel.setFont(Font.font("Cambria", 14));
						postedBy.setTextFill(Color.web("b34700"));
						postedBy.setFont(Font.font("Cambria", 14));
						claimedBy.setTextFill(Color.web("b34700"));
						claimedBy.setFont(Font.font("Cambria", 14));

						taskPane.add(taskNameLabel, 0, 0);
						taskPane.add(timeLimitLabel, 0, 1);
						taskPane.add(postedBy, 0, 2);
						taskPane.add(claimedBy, 0, 3);
						taskPane.add(cancel, 0, 4);
						cancel.setOnAction(f -> {

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);
						});

					}
				});
				doList.getChildren().add(button3);
			}
		}
	}

	public static void listDoClaimedNoLimitTasks() {
		for (int i = 0; i < Data.getTasks().size(); i++) {

			Tasks currTask = Data.getTasks().get(i);
			Change change = new Change();
			change.setGroupName(Data.getGroupName());

			final int j = i;

			if (currTask.getClaimedBy() != null && currTask.getCompletedBy() == null
					&& currTask.getTimeLimit() == null) {

				String claim = Data.getTasks().get(i).getClaimedBy();
				Button button3 = new Button(Data.getTasks().get(i).getTaskName());
				// if(Data.getTasks().get(i).)

				if (currTask.getClaimedBy().equals(Data.getUserName())) {
					button3.setId("button4");
				} else {
					button3.setId("button3");
				}
				button3.setOnAction(e -> {
					taskPane.getChildren().clear();
					adminPane.setCenter(taskPane);
					if (claim.equals(Data.getUserName())) {
						Label taskNameLabel = new Label(Data.getTasks().get(j).getTaskName());
						Label postedBy = new Label("Posted by: " + Data.getTasks().get(j).getPostedBy());
						Label claimedBy = new Label("Claimed by: " + Data.getTasks().get(j).getClaimedBy());

						taskNameLabel.setTextFill(Color.web("b34700"));
						taskNameLabel.setFont(Font.font("Cambria", 24));
						taskNameLabel.setMaxWidth(Double.MAX_VALUE);
						taskNameLabel.setAlignment(Pos.CENTER);
						postedBy.setTextFill(Color.web("b34700"));
						postedBy.setFont(Font.font("Cambria", 14));
						claimedBy.setTextFill(Color.web("b34700"));
						claimedBy.setFont(Font.font("Cambria", 14));

						taskPane.add(taskNameLabel, 0, 0);
						taskPane.add(postedBy, 0, 1);
						taskPane.add(claimedBy, 0, 2);

						taskPane.add(completeTaskButton, 0, 3);
						taskPane.add(unclaimTaskButton, 0, 4);
						taskPane.add(removeTaskButton, 0, 5);
						taskPane.add(cancel, 0, 7);

						completeTaskButton.setOnAction(f -> {
							// client = new ServicesClient();
							currTask.setCompletedBy(Data.getUserName());
							client.updateTask(currTask);

							change.setChangeType("2");
							client.createChange(change);

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);
						});

						removeTaskButton.setOnAction(f -> {
							// client = new ServicesClient();
							client.delete(currTask.getId());

							change.setChangeType("4");
							client.createChange(change);

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);
						});

						cancel.setOnAction(f -> {

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);
						});

						unclaimTaskButton.setOnAction(f -> {

							// client = new ServicesClient();
							currTask.setClaimedBy(null);
							client.updateTask(currTask);

							change.setChangeType("1");
							client.createChange(change);

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);

						});

						refreshDo();
						refreshDone();

					} else {
						Label taskNameLabel = new Label(Data.getTasks().get(j).getTaskName());
						Label postedBy = new Label("Posted by: " + Data.getTasks().get(j).getPostedBy());
						Label claimedBy = new Label("Claimed by: " + Data.getTasks().get(j).getClaimedBy());

						taskNameLabel.setTextFill(Color.web("b34700"));
						taskNameLabel.setFont(Font.font("Cambria", 24));
						taskNameLabel.setMaxWidth(Double.MAX_VALUE);
						taskNameLabel.setAlignment(Pos.CENTER);
						postedBy.setTextFill(Color.web("b34700"));
						postedBy.setFont(Font.font("Cambria", 14));
						claimedBy.setTextFill(Color.web("b34700"));
						claimedBy.setFont(Font.font("Cambria", 14));

						taskPane.add(taskNameLabel, 0, 0);
						taskPane.add(postedBy, 0, 1);
						taskPane.add(claimedBy, 0, 2);
						taskPane.add(cancel, 0, 4);
						cancel.setOnAction(f -> {

							if (Data.getCurrentPane().equals("Do"))
								adminPane.setCenter(scrollDo);
							else
								adminPane.setCenter(scrollDone);
						});

					}
				});
				doList.getChildren().add(button3);
			}
		}
	}

	public static void listDoneTasks() {

		for (int i = 0; i < Data.getTasks().size(); i++) {

			Tasks currTask = Data.getTasks().get(i);
			Change change = new Change();
			change.setGroupName(Data.getGroupName());

			final int j = i;

			if (currTask.getCompletedBy() != null) {
				Button button2 = new Button(Data.getTasks().get(i).getTaskName());
				button2.setId("button2");
				button2.setOnAction(e -> {
					taskPane.getChildren().clear();
					adminPane.setCenter(taskPane);
					Label taskNameLabel = new Label(Data.getTasks().get(j).getTaskName());
					Label postedBy = new Label("Posted by: " + Data.getTasks().get(j).getPostedBy());
					Label completedBy = new Label("Completed by: " + Data.getTasks().get(j).getCompletedBy());

					taskNameLabel.setTextFill(Color.web("b34700"));
					taskNameLabel.setFont(Font.font("Cambria", 24));
					taskNameLabel.setMaxWidth(Double.MAX_VALUE);
					taskNameLabel.setAlignment(Pos.CENTER);
					postedBy.setTextFill(Color.web("b34700"));
					postedBy.setFont(Font.font("Cambria", 14));
					completedBy.setTextFill(Color.web("b34700"));
					completedBy.setFont(Font.font("Cambria", 14));

					taskPane.add(taskNameLabel, 0, 0);
					taskPane.add(postedBy, 0, 1);
					taskPane.add(completedBy, 0, 2);
					taskPane.add(removeTaskButton, 0, 3);
					taskPane.add(cancel, 0, 5);

					removeTaskButton.setOnAction(f -> {
						// client = new ServicesClient();
						client.delete(currTask.getId());
						change.setChangeType("4");
						client.createChange(change);

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

					cancel.setOnAction(f -> {

						if (Data.getCurrentPane().equals("Do"))
							adminPane.setCenter(scrollDo);
						else
							adminPane.setCenter(scrollDone);
					});

				});
				doneList.getChildren().add(button2);
			}
		}
	}// end of taskButtons
		// ----------------------------------------------------------------------------------

	/// Method to print time left on each of the tasks
	public static String getTimeLeft(String timelimit) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date parsedTimeStamp = dateFormat.parse(timelimit);

		Timestamp limitTime = new Timestamp(parsedTimeStamp.getTime());
		Timestamp currTime = new Timestamp(System.currentTimeMillis());

		long milliseconds1 = limitTime.getTime();
		long milliseconds2 = currTime.getTime();

		long diff = milliseconds1 - milliseconds2;
		long diffSeconds = diff / 1000;
		long diffMinutes = diff / (60 * 1000);
		long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60 * 60 * 1000);

		if (diffDays > 0) {
			return ("time left:" + diffDays + "d & " + (diffHours % 24) + "h & " + (diffMinutes % 60) + "m");
		} else if (diffHours > 0) {
			return ("time left: " + diffHours % 24 + "h & " + diffMinutes % 60 + "m");
		} else if (diffMinutes >= 0) {
			return "time left: " + diffMinutes + "m";
		}

		else if (diffDays < 0) {
			return ("Overdue: " + (diffDays) * (-1) + " d & " + (diffHours % 24) * (-1) + "h &"
					+ (diffMinutes % 60) * (-1) + "m");
		} else if (diffHours < 0) {
			return ("Overdue: " + (diffHours % 24) * (-1) + "h & " + (diffMinutes % 60) * (-1) + "m");
		} else if (diffMinutes < 0) {
			return "Overdue: " + (diffMinutes % 60) * (-1) + "m";
		} else
			return null;

	}

}// end of class
