package com.donedoo.donedoo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.donedoo.model.Change;
import com.donedoo.model.Data;
import com.donedoo.model.Tasks;
import com.donedoo.model.User;
import com.donedoo.services.ServiceClient;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.graphics.Color.argb;

public class DoActivity extends AppCompatActivity {
    //  final LinearLayout taskLayout = (LinearLayout) findViewById(R.id.task_linear_layout);
    int refresh=1;
    // String currMenu = new String();
    // String tempMenu = new String();

    LinearLayout taskLayout, addTaskLayout;
    Thread thread, thread2;
    LinearLayout.LayoutParams params,params2;
    String time;
    Button button1;
    Vibrator v;
    int doneDoFlag=0;
    int staticDonedooFlag =0;
    int threadTime=1000;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        taskLayout = (LinearLayout) findViewById(R.id.task_linear_layout);
        // Adding some colors to buttons
        ServiceClient.getUsers(Data.getGroupName());

        Button addTaksButton = (Button) findViewById(R.id.button);
        addTaksButton.setBackgroundColor(Color.rgb(102,179,255));
        Button addUserButton = (Button) findViewById(R.id.button3);
        addUserButton.setBackgroundColor(Color.rgb(102,179,255));
        Button updatePassword = (Button) findViewById(R.id.button5);
        updatePassword.setBackgroundColor(Color.rgb(102,179,255));
        Button listUsersButton = (Button) findViewById(R.id.button6);
        listUsersButton.setBackgroundColor(Color.rgb(102,179,255));
        final Button switchDoneDoButton = (Button) findViewById(R.id.button4);
        switchDoneDoButton.setBackgroundColor(argb(200,153,255,102));

        if(Data.getIsAdmin().equals("0")){
            addUserButton.setVisibility(View.GONE);
        }

        switchDoneDoButton.setText("Go To Done");

        switchDoneDoButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                vibrate(35);
                if(staticDonedooFlag==0) {

                    doneDoFlag=1;
                    staticDonedooFlag =1;
                    switchDoneDoButton.setText("Go To Do");
                    switchDoneDoButton.setBackgroundColor(argb(200,255,166,77));
                    refreshDoneScreen();

                    // Andrew code --->>> switchDoneDoButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0000")));
                }else {

                    doneDoFlag=0;
                    staticDonedooFlag =0;
                    switchDoneDoButton.setText("Go To Done");
                    switchDoneDoButton.setBackgroundColor(argb(200,153, 255, 102));
                    refreshDoScreen();

                }

            }
        });




        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        params.setMargins(10, 20, 10, 10);

        params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        params2.setMargins(200, 20, 150, 60);




    }

    public void sendNotification(String notice) {
        //Get an instance of NotificationManager//
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.background)
                        .setContentTitle("Donedoo")
                        .setContentText(notice)
                        .setAutoCancel(true)
                        .setColor(argb(200,255,176,77))
                        .setColorized(true)
                        .setVibrate(new long[]{0, 400, 100, 200, 100, 400})
                        .setLights(Color.RED, 3000, 3000)
                        .setContentIntent(PendingIntent.getActivity(this,0, getIntent(),0));

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }



    public void checkingThread(final int time) {

        final MediaPlayer mp1 = MediaPlayer.create(this, R.raw.quiteimpressed);
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.ringo);
        final MediaPlayer mp3 = MediaPlayer.create(this, R.raw.appointed);
        final MediaPlayer mp4 = MediaPlayer.create(this, R.raw.stairs);


        thread = new Thread() {
            //Handler handler = new Handler();
            @Override
            public void run() {
                try {
                    while (true) {
                        ServiceClient.getChange(Data.getGroupName());
                        refreshScreen();
                        sleep(threadTime);
                        //ServiceClient.existCheck(Data.getGroupName(), Data.getUserName(), Data.getPassword());
                        if(Data.getNewChanges()!=Data.getOldChanges()) {
                            ServiceClient.getTasks(Data.getGroupName());
                            ServiceClient.getUsers(Data.getGroupName());
                            refreshScreen();
                            if (Flags.getLOGINFLAG() == 1) {

                                if (Data.getChangeType().equals("1")) {
                                    sendNotification("Task has been created");
                                }
                                if (Data.getChangeType().equals("2")) {
                                    sendNotification("Task has been completed");
                                }
                                if (Data.getChangeType().equals("3")) {
                                    sendNotification("Task has been claimed");
                                }
                                if (Data.getChangeType().equals("4")) {
                                    sendNotification("Task has been removed");
                                }

                            } else {

                                if (Data.getChangeType().equals("1")) {
                                    refreshScreen();
                                    mp1.start();
                                }
                                if (Data.getChangeType().equals("2")) {
                                    refreshScreen();
                                    mp2.start();
                                }
                                if (Data.getChangeType().equals("3")) {
                                    refreshScreen();
                                    mp3.start();
                                }
                                if (Data.getChangeType().equals("4")) {
                                    refreshScreen();
                                    mp4.start();
                                }

                            }

                            sleep(1000);
                            Data.setOldChanges(Data.getNewChanges());
                        }



                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    public void refreshTimeThread(final int time) {

        thread2 = new Thread() {
            //Handler handler = new Handler();
            @Override
            public void run() {
                try {
                    while (true) {

                        sleep(time);

                        refreshScreen();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread2.start();
    }



    public void refreshDoScreen() {

        taskLayout.post(new Runnable() {
            @Override
            public void run() {
                taskLayout.removeAllViews();

                for (int i = 0; i < Data.getTasks().size(); i++) {
                    final int x=i; //needed to access from nested loop
                    if(Data.getTasks().get(i).getCompletedBy()==null) { // Showing only not completed tasks
                        if (Data.getTasks().get(i).getClaimedBy() == null) { // shows not claimed tasks
                            if(Data.getTasks().get(i).getTimeLimit()!=null) {

                                try {
                                    time = stringTimeConverter(Data.getTasks().get(i).getTimeLimit());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                final Button button1 = new Button(DoActivity.this);
                                final Tasks t1 = Data.getTasks().get(i);
                                final Change c1 = new Change();
                                c1.setGroupName(Data.getGroupName());

                                button1.setText(Data.getTasks().get(i).getTaskName()+ "\n " +time); //zmiana
                                button1.setId(Data.getTasks().get(i).getId());
                                button1.setBackgroundColor(argb(200,255,136,77));

                                button1.setLayoutParams(params);
                                taskLayout.addView(button1);

                                final int j=i;
                                button1.setOnClickListener(new Button.OnClickListener() {
                                    public void onClick(View view) {
                                        vibrate(30);

                                        doneDoFlag=3;
                                        taskLayout.removeAllViews();

                                        // Adding Button To complete Task
                                        Button completeTaskButton = new Button(DoActivity.this);
                                        completeTaskButton.setText("Complete Task");
                                        completeTaskButton.setLayoutParams(params);
                                        completeTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        completeTaskButton.setOnClickListener(new Button.OnClickListener() {
                                            public void onClick(View view) {
                                                vibrate(30);
                                                t1.setCompletedBy(Data.getUserName());
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("2");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;


                                            }
                                        });

                                        // Adding Button to claim Task
                                        Button claimTaskButton = new Button(DoActivity.this);
                                        claimTaskButton.setText("Claim");
                                        claimTaskButton.setLayoutParams(params);
                                        claimTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        claimTaskButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View viev) {
                                                vibrate(30);
                                                t1.setClaimedBy(Data.getUserName());
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("3");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;

                                            }
                                        });

                                        Button removeButton = new Button(DoActivity.this);
                                        removeButton.setText("Remove");
                                        removeButton.setLayoutParams(params);
                                        removeButton.setBackgroundColor(argb(200,255,176,77));
                                        removeButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {
                                                ServiceClient.getUsers(Data.getGroupName());
                                                vibrate(30);
                                                ServiceClient.deleteTask(t1.getId());
                                                c1.setChangeType("4");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;


                                            }
                                        });

                                        Button assignTaskButton = new Button(DoActivity.this);
                                        assignTaskButton.setText("Assign Task");
                                        assignTaskButton.setLayoutParams(params);
                                        assignTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        assignTaskButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {

                                                vibrate(30);
                                                ServiceClient.getUsers(Data.getGroupName());
                                                taskLayout.removeAllViews();
                                                Button button2;
                                                for (int j = 0; j < Data.getAllUsers().size(); j++) {
                                                    final int z = j; //needed to ccess from nested listener
                                                    button2 = new Button(DoActivity.this);
                                                    button2.setBackgroundColor(argb(200,255,224,102));
                                                    button2.setLayoutParams(params);

                                                    button2.setText(Data.getAllUsers().get(j).getUserName());

                                                    button2.setOnClickListener(new Button.OnClickListener() {
                                                        public void onClick(View view) {
                                                            Tasks t2 = Data.getTasks().get(x);
                                                            t2.setClaimedBy(Data.getAllUsers().get(z).getUserName());

                                                            Change c2 = new Change();
                                                            c2.setGroupName(Data.getGroupName());
                                                            c2.setChangeType("3");

                                                            ServiceClient.updateTask(t2);
                                                            ServiceClient.createChange(c2);

                                                            doneDoFlag = staticDonedooFlag;
                                                        }
                                                    });

                                                    taskLayout.addView(button2);
                                                }
                                            }
                                        });


                                        // adding cancel button
                                        Button cancelButton = new Button(DoActivity.this);
                                        cancelButton.setText("Cancel");
                                        cancelButton.setLayoutParams(params);
                                        cancelButton.setBackgroundColor(argb(200,255,176,77));
                                        cancelButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {
                                                vibrate(30);
                                                doneDoFlag= staticDonedooFlag;

                                                refreshScreen();
                                            }
                                        });


                                        TextView taskName = new TextView(DoActivity.this);
                                        taskName.setText(Data.getTasks().get(j).getTaskName());
                                        taskName.setTextSize(25);
                                        taskName.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                                        taskName.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskName);

                                        TextView taskDue = new TextView(DoActivity.this);
                                        taskDue.setText("Due: "+Data.getTasks().get(j).getTimeLimit().substring(0, Data.getTasks().get(j).getTimeLimit().length() - 5));
                                        taskDue.setTextSize(18);
                                        taskDue.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskDue);

                                        TextView taskPostedBy = new TextView(DoActivity.this);
                                        taskPostedBy.setText("Posted by "+Data.getTasks().get(j).getPostedBy());
                                        taskPostedBy.setTextSize(18);
                                        taskPostedBy.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskPostedBy);


                                        taskLayout.addView(completeTaskButton);
                                        taskLayout.addView(claimTaskButton);
                                        taskLayout.addView(removeButton);

                                        if(Data.getIsAdmin().equals("1")) {
                                            taskLayout.addView(assignTaskButton);
                                        }
                                        taskLayout.addView(cancelButton);
                                    }
                                });
                            }// end timelimit
                        }// End if not claimed == null

                    }// end Completedby

                }// end for



                for (int i = 0; i < Data.getTasks().size(); i++) {
                    final int x=i;
                    if(Data.getTasks().get(i).getCompletedBy()==null) { // Showing only not completed tasks
                        if (Data.getTasks().get(i).getClaimedBy() == null) { // shows not claimed tasks
                            if(Data.getTasks().get(i).getTimeLimit()==null) {
                                button1 = new Button(DoActivity.this);
                                final Tasks t1 = Data.getTasks().get(i);
                                final Change c1 = new Change();
                                c1.setGroupName(Data.getGroupName());

                                button1.setText(Data.getTasks().get(i).getTaskName());
                                button1.setId(Data.getTasks().get(i).getId());
                                button1.setBackgroundColor(argb(200,255,136,77));


                                button1.setLayoutParams(params);
                                taskLayout.addView(button1);

                                final int j=i;
                                button1.setOnClickListener(new Button.OnClickListener() {
                                    public void onClick(View view) {
                                        vibrate(30);
                                        doneDoFlag=3;
                                        taskLayout.removeAllViews();

                                        // Adding Button To complete Task
                                        Button completeTaskButton = new Button(DoActivity.this);
                                        completeTaskButton.setText("Complete Task");
                                        completeTaskButton.setLayoutParams(params);
                                        completeTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        completeTaskButton.setOnClickListener(new Button.OnClickListener() {
                                            public void onClick(View view) {
                                                vibrate(30);
                                                t1.setCompletedBy(Data.getUserName());
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("2");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;

                                            }
                                        });

                                        // Adding Button to claim Task
                                        Button claimTaskButton = new Button(DoActivity.this);
                                        claimTaskButton.setText("Claim");
                                        claimTaskButton.setLayoutParams(params);
                                        claimTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        claimTaskButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View viev) {
                                                vibrate(30);
                                                t1.setClaimedBy(Data.getUserName());
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("3");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;

                                            }
                                        });

                                        Button removeButton = new Button(DoActivity.this);
                                        removeButton.setText("Remove");
                                        removeButton.setLayoutParams(params);
                                        removeButton.setBackgroundColor(argb(200,255,176,77));
                                        removeButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {
                                                vibrate(30);
                                                ServiceClient.deleteTask(t1.getId());
                                                c1.setChangeType("4");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;

                                            }
                                        });

                                        Button assignTaskButton = new Button(DoActivity.this);
                                        assignTaskButton.setText("Assign Task");
                                        assignTaskButton.setLayoutParams(params);
                                        assignTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        assignTaskButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {

                                                vibrate(30);
                                                ServiceClient.getUsers(Data.getGroupName());
                                                taskLayout.removeAllViews();
                                                Button button2;
                                                for (int j = 0; j < Data.getAllUsers().size(); j++) {
                                                    final int z = j; //needed to ccess from nested listener
                                                    button2 = new Button(DoActivity.this);
                                                    button2.setBackgroundColor(argb(200,255,224,102));
                                                    button2.setLayoutParams(params);

                                                    button2.setText(Data.getAllUsers().get(j).getUserName());

                                                    button2.setOnClickListener(new Button.OnClickListener() {
                                                        public void onClick(View view) {
                                                            Tasks t2 = Data.getTasks().get(x);
                                                            t2.setClaimedBy(Data.getAllUsers().get(z).getUserName());

                                                            Change c2 = new Change();
                                                            c2.setGroupName(Data.getGroupName());
                                                            c2.setChangeType("3");

                                                            ServiceClient.updateTask(t2);
                                                            ServiceClient.createChange(c2);

                                                            doneDoFlag = staticDonedooFlag;
                                                        }
                                                    });

                                                    taskLayout.addView(button2);
                                                }
                                            }
                                        });
                                        // adding cancel button
                                        Button cancelButton = new Button(DoActivity.this);
                                        cancelButton.setText("Cancel");
                                        cancelButton.setLayoutParams(params);
                                        cancelButton.setBackgroundColor(argb(200,255,176,77));
                                        cancelButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {
                                                vibrate(30);
                                                doneDoFlag= staticDonedooFlag;
                                                refreshScreen();
                                            }
                                        });


                                        TextView taskName = new TextView(DoActivity.this);
                                        taskName.setText(Data.getTasks().get(j).getTaskName());
                                        taskName.setTextSize(25);
                                        taskName.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                                        taskName.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskName);

                                        TextView taskPostedBy = new TextView(DoActivity.this);
                                        taskPostedBy.setText("Posted by: "+Data.getTasks().get(j).getPostedBy());
                                        taskPostedBy.setTextSize(20);
                                        taskPostedBy.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskPostedBy);

                                        taskLayout.addView(completeTaskButton);
                                        taskLayout.addView(claimTaskButton);
                                        taskLayout.addView(removeButton);
                                        if(Data.getIsAdmin().equals("1")) {
                                            taskLayout.addView(assignTaskButton);
                                        }
                                        taskLayout.addView(cancelButton);
                                    }
                                });
                            }// end timelimit
                        }// End if not claimed == null

                    }// end Completedby

                }// end for

                for (int i = 0; i < Data.getTasks().size(); i++) {
                    if(Data.getTasks().get(i).getCompletedBy()==null) { // Showing only not completed tasks
                        if (Data.getTasks().get(i).getClaimedBy() != null) { // shows not claimed tasks
                            if(Data.getTasks().get(i).getTimeLimit()!=null) {

                                try {
                                    time = stringTimeConverter(Data.getTasks().get(i).getTimeLimit());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                button1 = new Button(DoActivity.this);
                                final Tasks t1 = Data.getTasks().get(i);
                                final Change c1 = new Change();
                                c1.setGroupName(Data.getGroupName());

                                if(t1.getClaimedBy().equals(Data.getUserName())){
                                    button1.setBackgroundColor(argb(200,255,224,102));
                                }
                                else{
                                    button1.setBackgroundColor(argb(200,255,176,77));
                                }

                                button1.setText(Data.getTasks().get(i).getTaskName()+"\n" + time);
                                button1.setId(Data.getTasks().get(i).getId());
                                button1.setLayoutParams(params);
                                taskLayout.addView(button1);

                                final int j=i;
                                button1.setOnClickListener(new Button.OnClickListener() {
                                    public void onClick(View view) {
                                        vibrate(30);
                                        doneDoFlag=3;
                                        taskLayout.removeAllViews();

                                        // Adding Button To complete Task
                                        Button completeTaskButton = new Button(DoActivity.this);
                                        completeTaskButton.setText("Complete Task");
                                        completeTaskButton.setLayoutParams(params);
                                        completeTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        completeTaskButton.setOnClickListener(new Button.OnClickListener() {
                                            public void onClick(View view) {
                                                vibrate(30);
                                                t1.setCompletedBy(Data.getUserName());
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("2");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;


                                            }
                                        });

                                        // Adding Button to claim Task
                                        Button claimTaskButton = new Button(DoActivity.this);
                                        claimTaskButton.setText("Claim");
                                        claimTaskButton.setLayoutParams(params);
                                        claimTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        claimTaskButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View viev) {
                                                vibrate(30);
                                                t1.setClaimedBy(Data.getUserName());
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("3");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;

                                            }
                                        });

                                        Button removeButton = new Button(DoActivity.this);
                                        removeButton.setText("Remove");
                                        removeButton.setLayoutParams(params);
                                        removeButton.setBackgroundColor(argb(200,255,176,77));
                                        removeButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {
                                                vibrate(30);
                                                ServiceClient.deleteTask(t1.getId());
                                                c1.setChangeType("4");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;


                                            }
                                        });
                                        // adding cancel button
                                        Button cancelButton = new Button(DoActivity.this);
                                        cancelButton.setText("Cancel");
                                        cancelButton.setLayoutParams(params);
                                        cancelButton.setBackgroundColor(argb(200,255,176,77));
                                        cancelButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {
                                                vibrate(30);
                                                doneDoFlag= staticDonedooFlag;
                                                refreshScreen();

                                            }
                                        });
                                        Button unclaimButton = new Button(DoActivity.this);
                                        unclaimButton.setText("Unclaim");
                                        unclaimButton.setLayoutParams(params);
                                        unclaimButton.setBackgroundColor(argb(200,255,176,77));
                                        unclaimButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View viev) {
                                                vibrate(30);
                                                t1.setClaimedBy(null);
                                                t1.setCompletedBy(null);
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("1");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;

                                            }
                                        });


                                        TextView taskName = new TextView(DoActivity.this);
                                        taskName.setText(Data.getTasks().get(j).getTaskName());
                                        taskName.setTextSize(25);
                                        taskName.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                                        taskName.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskName);

                                        TextView taskDue = new TextView(DoActivity.this);
                                        taskDue.setText("Due: "+Data.getTasks().get(j).getTimeLimit().substring(0, Data.getTasks().get(j).getTimeLimit().length() - 5));
                                        taskDue.setTextSize(18);
                                        taskDue.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskDue);

                                        TextView taskPostedBy = new TextView(DoActivity.this);
                                        taskPostedBy.setText("Posted by: "+Data.getTasks().get(j).getPostedBy());
                                        taskPostedBy.setTextSize(18);
                                        taskPostedBy.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskPostedBy);

                                        TextView taskClaimedBy = new TextView(DoActivity.this);
                                        taskClaimedBy.setText("Claimed by: "+Data.getTasks().get(j).getClaimedBy());
                                        taskClaimedBy.setTextSize(18);
                                        taskClaimedBy.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskClaimedBy);

                                        if(t1.getClaimedBy().equals(Data.getUserName())) {
                                            taskLayout.addView(completeTaskButton);
                                            taskLayout.addView(unclaimButton);
                                            taskLayout.addView(removeButton);
                                        }
                                        taskLayout.addView(cancelButton);
                                    }
                                });
                            }// end timelimit
                        }// End if not claimed == null

                    }// end Completedby

                }// end for

                for (int i = 0; i < Data.getTasks().size(); i++) {
                    if(Data.getTasks().get(i).getCompletedBy()==null) { // Showing only not completed tasks
                        if (Data.getTasks().get(i).getClaimedBy() != null) { // shows not claimed tasks
                            if(Data.getTasks().get(i).getTimeLimit()==null) {
                                button1 = new Button(DoActivity.this);
                                final Tasks t1 = Data.getTasks().get(i);
                                final Change c1 = new Change();
                                c1.setGroupName(Data.getGroupName());

                                if(t1.getClaimedBy().equals(Data.getUserName())){
                                    button1.setBackgroundColor(argb(200,255,224,102));
                                }
                                else{
                                    button1.setBackgroundColor(argb(200,255,176,77));
                                }

                                button1.setText(Data.getTasks().get(i).getTaskName());
                                button1.setId(Data.getTasks().get(i).getId());

                                button1.setLayoutParams(params);
                                taskLayout.addView(button1);

                                final int j=i;
                                button1.setOnClickListener(new Button.OnClickListener() {
                                    public void onClick(View view) {
                                        vibrate(30);
                                        doneDoFlag=3;

                                        taskLayout.removeAllViews();

                                        // Adding Button To complete Task
                                        Button completeTaskButton = new Button(DoActivity.this);
                                        completeTaskButton.setText("Complete Task");
                                        completeTaskButton.setLayoutParams(params);
                                        completeTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        completeTaskButton.setOnClickListener(new Button.OnClickListener() {
                                            public void onClick(View view) {
                                                vibrate(30);
                                                t1.setCompletedBy(Data.getUserName());
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("2");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;


                                            }
                                        });

                                        // Adding Button to claim Task
                                        Button claimTaskButton = new Button(DoActivity.this);
                                        claimTaskButton.setText("Claim");
                                        claimTaskButton.setLayoutParams(params);
                                        claimTaskButton.setBackgroundColor(argb(200,255,176,77));
                                        claimTaskButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View viev) {
                                                vibrate(30);
                                                t1.setClaimedBy(Data.getUserName());
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("3");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;

                                            }
                                        });

                                        Button removeButton = new Button(DoActivity.this);
                                        removeButton.setText("Remove");
                                        removeButton.setLayoutParams(params);
                                        removeButton.setBackgroundColor(argb(200,255,176,77));
                                        removeButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {
                                                vibrate(30);
                                                ServiceClient.deleteTask(t1.getId());
                                                c1.setChangeType("4");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;


                                            }
                                        });
                                        // adding cancel button
                                        Button cancelButton = new Button(DoActivity.this);
                                        cancelButton.setText("Cancel");
                                        cancelButton.setLayoutParams(params);
                                        cancelButton.setBackgroundColor(argb(200,255,176,77));
                                        cancelButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View view) {
                                                vibrate(30);
                                                doneDoFlag= staticDonedooFlag;
                                                refreshScreen();
                                            }
                                        });

                                        Button unclaimButton = new Button(DoActivity.this);
                                        unclaimButton.setText("Unclaim");
                                        unclaimButton.setLayoutParams(params);
                                        unclaimButton.setBackgroundColor(argb(200,255,176,77));
                                        unclaimButton.setOnClickListener(new Button.OnClickListener() {

                                            public void onClick(View viev) {
                                                vibrate(30);
                                                t1.setClaimedBy(null);
                                                t1.setCompletedBy(null);
                                                ServiceClient.updateTask(t1);

                                                c1.setChangeType("1");
                                                ServiceClient.createChange(c1);
                                                doneDoFlag= staticDonedooFlag;

                                            }
                                        });



                                        TextView taskName = new TextView(DoActivity.this);
                                        taskName.setText(Data.getTasks().get(j).getTaskName());
                                        taskName.setTextSize(25);
                                        taskName.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                                        taskName.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskName);

                                        TextView taskPostedBy = new TextView(DoActivity.this);
                                        taskPostedBy.setText("Posted by: "+Data.getTasks().get(j).getPostedBy());
                                        taskPostedBy.setTextSize(18);
                                        taskPostedBy.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskPostedBy);

                                        TextView taskClaimedBy = new TextView(DoActivity.this);
                                        taskClaimedBy.setText("Claimed by: "+Data.getTasks().get(j).getClaimedBy());
                                        taskClaimedBy.setTextSize(18);
                                        taskClaimedBy.setTextColor(argb(200,179, 71, 0));
                                        taskLayout.addView(taskClaimedBy);

                                        if(t1.getClaimedBy().equals(Data.getUserName())) {
                                            taskLayout.addView(completeTaskButton);
                                            taskLayout.addView(unclaimButton);
                                            taskLayout.addView(removeButton);
                                        }
                                        taskLayout.addView(cancelButton);
                                    }
                                });
                            }// end timelimit
                        }// End if not claimed == null

                    }// end Completedby

                }// end for

            }
        });
    }

    public void refreshDoneScreen() {

        taskLayout.post(new Runnable() {
            @Override
            public void run() {
                taskLayout.removeAllViews();

                for (int i = 0; i < Data.getTasks().size(); i++) {
                    if(Data.getTasks().get(i).getCompletedBy()!=null) {
                        button1 = new Button(DoActivity.this);
                        button1.setBackgroundColor(argb(200,153, 255, 102));
                        button1.setLayoutParams(params);

                        final Tasks t1 = Data.getTasks().get(i);;
                        final Change c1 = new Change();
                        c1.setGroupName(Data.getGroupName());

                        button1.setText(Data.getTasks().get(i).getTaskName());
                        button1.setId(Data.getTasks().get(i).getId());
                        taskLayout.addView(button1);

                        final int j=i;
                        button1.setOnClickListener(new Button.OnClickListener() {
                            public void onClick(View view) {
                                vibrate(30);
                                doneDoFlag=3;
                                taskLayout.removeAllViews();

                                // Adding Button To complete Task
                                Button completeTaskButton = new Button(DoActivity.this);
                                completeTaskButton.setText("Complete Task");
                                completeTaskButton.setLayoutParams(params);
                                completeTaskButton.setBackgroundColor(argb(200,255,176,77));
                                completeTaskButton.setOnClickListener(new Button.OnClickListener() {
                                    public void onClick(View view) {
                                        vibrate(30);
                                        t1.setCompletedBy(Data.getUserName());
                                        ServiceClient.updateTask(t1);

                                        c1.setChangeType("2");
                                        ServiceClient.createChange(c1);
                                        doneDoFlag= staticDonedooFlag;
                                        refreshScreen();
                                    }
                                });

                                // Adding Button to claim Task
                                Button claimTaskButton = new Button(DoActivity.this);
                                claimTaskButton.setText("Claim");
                                claimTaskButton.setLayoutParams(params);
                                claimTaskButton.setBackgroundColor(argb(200,255,176,77));
                                claimTaskButton.setOnClickListener(new Button.OnClickListener() {

                                    public void onClick(View viev) {

                                        t1.setTaskName(button1.getText().toString());
                                        ServiceClient.updateTask(t1);

                                        c1.setChangeType("3");
                                        ServiceClient.createChange(c1);
                                        doneDoFlag= staticDonedooFlag;
                                        refreshScreen();

                                    }
                                });

                                Button removeButton = new Button(DoActivity.this);
                                removeButton.setText("Remove");
                                removeButton.setLayoutParams(params);
                                removeButton.setBackgroundColor(argb(200,255,176,77));
                                removeButton.setOnClickListener(new Button.OnClickListener() {

                                    public void onClick(View view) {
                                        vibrate(30);
                                        ServiceClient.deleteTask(t1.getId());
                                        c1.setChangeType("4");
                                        ServiceClient.createChange(c1);
                                        doneDoFlag= staticDonedooFlag;


                                    }
                                });
                                // adding cancel button
                                Button cancelButton = new Button(DoActivity.this);
                                cancelButton.setText("Cancel");
                                cancelButton.setLayoutParams(params);
                                cancelButton.setBackgroundColor(argb(200,255,176,77));
                                cancelButton.setOnClickListener(new Button.OnClickListener() {

                                    public void onClick(View view) {
                                        vibrate(30);
                                        doneDoFlag= staticDonedooFlag;
                                        refreshScreen();
                                    }
                                });


                                TextView taskName = new TextView(DoActivity.this);
                                taskName.setText(Data.getTasks().get(j).getTaskName());
                                taskName.setTextSize(25);
                                taskName.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                                taskName.setTextColor(argb(200,179, 71, 0));
                                taskLayout.addView(taskName);

                                TextView taskPostedBy = new TextView(DoActivity.this);
                                taskPostedBy.setText("Posted by "+Data.getTasks().get(j).getPostedBy());
                                taskPostedBy.setTextSize(18);
                                taskPostedBy.setTextColor(argb(200,179, 71, 0));
                                taskLayout.addView(taskPostedBy);

                                TextView taskCompletedBy = new TextView(DoActivity.this);
                                taskCompletedBy.setText("Completed by "+Data.getTasks().get(j).getCompletedBy());
                                taskCompletedBy.setTextSize(18);
                                taskCompletedBy.setTextColor(argb(200,179, 71, 0));
                                taskLayout.addView(taskCompletedBy);

                                //  taskLayout.addView(completeTaskButton);
                                //  taskLayout.addView(claimTaskButton);
                                taskLayout.addView(removeButton);
                                taskLayout.addView(cancelButton);
                            }
                        });
                    }


                }
            }
        });
    }



    public void addTask(View view){
        vibrate(30);
        doneDoFlag=3;
        taskLayout.removeAllViews();

        final EditText taskName = new EditText(this);
        taskName.setHint(R.string.new_task_name);



        String[] minuteArray=new String[60];
        minuteArray[0]="minutes";
        for(int i=1; i<60; i++){
            minuteArray[i]=new String(""+i);
        }
        final Spinner minuteSpinner = new Spinner(this);
        minuteSpinner.setLayoutParams(params2);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        minuteArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        minuteSpinner.setAdapter(spinnerArrayAdapter);



        String[] hourArray = new String[24];
        hourArray[0]="hours";
        for(int i=1; i<24; i++){
            hourArray[i]=new String(""+i);
        }
        final Spinner hourSpinner = new Spinner(this);
        hourSpinner.setLayoutParams(params2);
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        hourArray);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        hourSpinner.setAdapter(spinnerArrayAdapter2);



        String[] dayArray = new String[365];
        dayArray[0]="days";
        for(int i=1; i<365; i++){
            dayArray[i]=new String(""+i);
        }
        final Spinner daySpinner = new Spinner(this);
        daySpinner.setLayoutParams(params2);
        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        dayArray);
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerArrayAdapter3);




        Button createTask = new Button(this);
        createTask.setText("Create Task");
        createTask.setBackgroundColor(argb(200,255,176,77));
        createTask.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                vibrate(30);
                Tasks t1=new Tasks();
                t1.setGroupName(Data.getGroupName());
                t1.setPostedBy(Data.getUserName());
                t1.setTaskName(taskName.getText().toString());
                t1.setMinutesLeft(minuteSpinner.getSelectedItem().toString());
                t1.setHoursLeft(hourSpinner.getSelectedItem().toString());
                t1.setDaysLeft(daySpinner.getSelectedItem().toString());


                ServiceClient.createTask(t1);
                Change c1= new Change();
                c1.setGroupName(Data.getGroupName());
                c1.setChangeType("1");

                ServiceClient.createChange(c1);


                // Removing keyboard after pressing button
                View view1 = getCurrentFocus();
                if(view1!=null){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(),0);
                }
                doneDoFlag= staticDonedooFlag;
                refreshScreen();
            }
        });

        taskLayout.addView(taskName);
        taskLayout.addView(minuteSpinner);
        taskLayout.addView(hourSpinner);
        taskLayout.addView(daySpinner);
        taskLayout.addView(createTask);

    }

    public void addUser(View view){
        vibrate(30);
        doneDoFlag=3;
        taskLayout.removeAllViews();

        final EditText userName = new EditText(this);
        userName.setHint(R.string.new_user_name);

        final EditText password = new EditText(this);
        password.setHint(R.string.new_user_password);

        final CheckBox adminCheckBox= new CheckBox(this);
        adminCheckBox.setHint("admin role");

        Button createUser = new Button(this);
        createUser.setText("Create User");
        createUser.setBackgroundColor(argb(200,255,176,77));
        createUser.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                vibrate(30);

                User u1= new User();
                u1.setGroupName(Data.getGroupName());
                u1.setUserName(userName.getText().toString());
                u1.setPassword(password.getText().toString());
                if(adminCheckBox.isChecked()){
                    u1.setAdmin(1);
                }
                else u1.setAdmin(0);

                ServiceClient.createUser(u1);

                // Removing keyboard after pressing button
                View view1 = getCurrentFocus();
                if(view1!=null){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(),0);
                }

                doneDoFlag= staticDonedooFlag;
                refreshScreen();
            }

        });


        taskLayout.addView(userName);
        taskLayout.addView(password);
        taskLayout.addView(adminCheckBox);
        taskLayout.addView(createUser);


    }

    public void changePassword(View view){
        vibrate(30);
        User u1 = new User();
        u1.setGroupName(Data.getGroupName());
        u1.setUserName(Data.getUserName());

        doneDoFlag=3;
        taskLayout.removeAllViews();

        final EditText oldPassword = new EditText(this);
        oldPassword.setHint(R.string.old_password);

        final EditText newPassword = new EditText(this);
        newPassword.setHint(R.string.new_password);

        Button updatePassword = new Button(this);
        updatePassword.setText("Change Password");
        updatePassword.setBackgroundColor(argb(200,255,176,77));
        updatePassword.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                vibrate(30);

                User u1= new User();
                u1.setGroupName(Data.getGroupName());
                u1.setUserName(Data.getUserName());
                u1.setPassword(newPassword.getText().toString());

                if(Data.getPassword().equals(oldPassword.getText().toString())) {

                    ServiceClient.updateUser(u1);

                }
                // Removing keyboard after pressing button
                View view1 = getCurrentFocus();
                if(view1!=null){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(),0);



                }
                doneDoFlag= staticDonedooFlag;
                refreshScreen();

            }

        });


        taskLayout.addView(oldPassword);
        taskLayout.addView(newPassword);
        taskLayout.addView(updatePassword);
    }

    public String stringTimeConverter(String timeLimit) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat(

                "yyyy-MM-dd HH:mm:ss.S");

        Date parsedTimeStamp = dateFormat.parse(timeLimit);

        Timestamp limitTime = new Timestamp(parsedTimeStamp.getTime());
        Timestamp currTime = new Timestamp(System.currentTimeMillis());

        long milliseconds1 = limitTime.getTime();
        long milliseconds2 = currTime.getTime();

        BigInteger big1=BigInteger.valueOf(limitTime.getTime());
        BigInteger big2=BigInteger.valueOf(currTime.getTime());

        long diff = (big1.subtract(big2).longValue());
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);



        if (diffDays>0){
            return("time left: "+diffDays+"d & "+diffHours%24+" h & "+diffMinutes%60+ " m");
        }else if(diffHours>0){
            return("time left: "+diffHours%24+" h & "+diffMinutes%60+ " m");
        }else if(diffMinutes>0){
            return("time left: "+diffMinutes%60+ " m");
        }if (diffDays<0){
            return("overdue: "+(diffDays)*(-1)+"d & "+(diffHours%24)*(-1)+" h & "+(diffMinutes%60)*(-1)+ " m");
        }else if(diffHours<0){
            return("overdue: "+(diffHours%24)*(-1)+" h & "+(diffMinutes%60)*(-1)+ " m");
        }else{
            return("overdue: "+(diffMinutes%60)*(-1)+ " m");
        }
    }

    public void vibrate(int time){
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(time,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(time);
        }
    }




    public void listUsers(View view) {
        vibrate(30);
        doneDoFlag=3;

        ServiceClient.getUsers(Data.getGroupName());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                taskLayout.removeAllViews();
                Button button2;
                for (int j = 0; j < Data.getAllUsers().size(); j++) {
                    final int x = j; //needed to ccess from nested listener
                    button2 = new Button(DoActivity.this);
                    button2.setBackgroundColor(argb(200, 255, 224, 102));
                    if(Data.getAllUsers().get(x).getAdmin() == 1){
                        button2.setBackgroundColor(argb(200,153, 255, 102));
                        button2.setText(Data.getAllUsers().get(x).getUserName()+" (Admin)");
                    }
                    else
                        button2.setText(Data.getAllUsers().get(x).getUserName());

                    button2.setLayoutParams(params);



                    button2.setOnClickListener(new Button.OnClickListener() {
                        public void onClick(View view) {
                            taskLayout.removeAllViews();

                            final EditText taskName = new EditText(DoActivity.this);
                            taskName.setHint(R.string.new_task_name);

                            final Button promoteButton = new Button(DoActivity.this);
                            promoteButton.setLayoutParams(params);
                            promoteButton.setText("Promote to Admin");
                            promoteButton.setBackgroundColor(argb(200,255,176,77));
                            promoteButton.setOnClickListener(new Button.OnClickListener() {
                                public void onClick(View v) {
                                    vibrate(30);
                                    User u1 = new User();
                                    u1.setUserName(Data.getAllUsers().get(x).getUserName());
                                    u1.setGroupName(Data.getGroupName());


                                    ServiceClient.updateOtherUser(u1);
                                    ServiceClient.getUsers(Data.getGroupName());
                                    doneDoFlag = staticDonedooFlag;
                                }});

                            final Button deleteButton = new Button(DoActivity.this);
                            deleteButton.setLayoutParams(params);
                            deleteButton.setText("Delete User");
                            deleteButton.setBackgroundColor(argb(200,255,176,77));
                            deleteButton.setOnClickListener(new Button.OnClickListener() {
                                public void onClick(View v) {
                                    vibrate(30);
                                    User u1 = new User();
                                    u1.setUserName(Data.getAllUsers().get(x).getUserName());
                                    u1.setGroupName(Data.getGroupName());


                                    ServiceClient.deleteUser(Data.getGroupName(),Data.getAllUsers().get(x).getUserName());
                                    ServiceClient.getUsers(Data.getGroupName());
                                    doneDoFlag = staticDonedooFlag;
                                }});

                            TextView userName = new TextView(DoActivity.this);
                            userName.setText(Data.getAllUsers().get(x).getUserName());
                            userName.setTextSize(25);
                            userName.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                            userName.setTextColor(argb(200,179, 71, 0));


                            taskLayout.addView(userName);
                            if(Data.getAllUsers().get(x).getAdmin()==0) {
                                taskLayout.addView(promoteButton);
                                taskLayout.addView(deleteButton);
                            }
                        }
                    });

                    taskLayout.addView(button2);
                }

            }
        }, 500);
    }

    public void refreshScreen(){

        if(doneDoFlag==0) {
            refreshDoScreen();
        }
        else if(doneDoFlag==1){
            refreshDoneScreen();
        }

    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (staticDonedooFlag==0)
            refreshDoScreen();
          else
              refreshDoneScreen();

    }

    @Override
    public void onPause(){

        super.onPause();

        threadTime=4000;
        Flags.setLOGINFLAG(1);

        //stopService();
    }

    @Override
    public void onStop(){

        super.onStop();

        threadTime=4000;
        Flags.setLOGINFLAG(1);

    }

    @Override
    public void onResume(){

        refreshScreen();
        super.onResume();
        threadTime=1000;

        if(Flags.getLOGINFLAG()==0) {
            checkingThread(1000);
            refreshTimeThread(30000);
        }

        Flags.setLOGINFLAG(2);
    }

}
