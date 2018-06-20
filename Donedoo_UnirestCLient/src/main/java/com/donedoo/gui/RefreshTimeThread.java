package com.donedoo.gui;

import javafx.application.Platform;
import javafx.concurrent.Task;

import com.donedoo.client.ServicesClient;
import com.donedoo.data.Change;

public class RefreshTimeThread {

	public static void refresh() {

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {

				while (true) {

					Platform.runLater(new Runnable() {

						@Override
						public void run() {

							ServicesClient client = new ServicesClient();

							DoneDo.refreshDo();
							DoneDo.refreshDone();

						}
					});

					Thread.sleep(30000);
					// System.out.println(Data.getTasks().size());
				}
			}
		};

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

}