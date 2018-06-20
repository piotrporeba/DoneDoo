package com.donedoo.gui;

import javafx.application.Platform;
import javafx.concurrent.Task;

import com.donedoo.client.ServicesClient;
import com.donedoo.data.Change;

public class RefreshTasks {

	static ServicesClient client = new ServicesClient();

	static Change change = client.getChange(Data.getGroupName());
	static Change updateChange;

	public static void refresh() {
		DoneDo.refreshDo();
		DoneDo.refreshDone();

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {

				while (true) {

					Platform.runLater(new Runnable() {

						@Override
						public void run() {

							ServicesClient client = new ServicesClient();

							// checkIfChanged = CheckChange_DB.check(Data.getGroupName(), Data.getIp(),
							// Data.getDbName())[0]; //this method returns an array 0-if change happened,
							// 1-what kind of change
							updateChange = client.getChange(Data.getGroupName());

							if (change.getId() != updateChange.getId()) {

								DoneDo.refreshDo();
								DoneDo.refreshDone();

								change = updateChange;

								// if(updateChange.getChangeType()!=null) {

								if (updateChange.getChangeType().equals("1"))
									new PlayAudio("sounds/quiteimpressed.wav"); // create
								if (updateChange.getChangeType().equals("2"))
									new PlayAudio("sounds/ringo.wav"); // complete
								if (updateChange.getChangeType().equals("3"))
									new PlayAudio("sounds/appointed.wav"); // claim
								if (updateChange.getChangeType().equals("4")) {
								}
								new PlayAudio("sounds/stairs.wav"); // remove

							}
						}
					});

					Thread.sleep(1000);
					// System.out.println(Data.getTasks().size());
				}
			}
		};

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

}
