package com.donedoo.donedoo;

import android.content.Intent;

import com.donedoo.model.Data;
import com.donedoo.services.ServiceClient;

/**
 * Created by Pawel on 07/03/2018.
 */

public class RefreshTasksThread implements Runnable{

    @Override
    public void run() {
        Data.setOldChanges(0);

        while(true){
            ServiceClient.getChange(Data.getGroupName());
            if(Data.getOldChanges()!=Data.getNewChanges()){

                //code to update tasks

            }
            try {
                wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
