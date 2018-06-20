package com.donedoo.services;

import com.donedoo.model.Change;
import com.donedoo.model.Data;
import com.donedoo.model.ExistsCheck;
import com.donedoo.model.Tasks;
import com.donedoo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public class ServiceClient {
    static ServiceClientInterface client;
            static String check="";
            static Tasks task;


        public static void getTasks(String groupName) {
            Call<List<Tasks>> getTasksCall = client.getTasks(groupName);
            getTasksCall.enqueue(new Callback<List<Tasks>>() {

                @Override
                public void onResponse(Call<List<Tasks>> request, Response<List<Tasks>> response) {
                  //  ((TextView) findViewById(R.id.message)).setText(response.body().get(0).getTaskName());
                    Data.setTasks(response.body());
                }

                @Override
                public void onFailure(Call<List<Tasks>> request, Throwable t) {
                  //  ((TextView) findViewById(R.id.message)).setText("Request Failed");
                }
            });
        }


        public static void getChange(String groupName) {
            Call<Change> getChangeCall = client.getChange(groupName);
            getChangeCall.enqueue(new Callback<Change>() {
                @Override
                public void onResponse(Call<Change> request, Response<Change> response) {
                  //  ((TextView) findViewById(R.id.message)).setText(response.body().getChangeType());
                    Data.setNewChanges(response.body().getId());
                    Data.setChangeType(response.body().getChangeType());

                }

                @Override
                public void onFailure(Call<Change> request, Throwable t) {
                  //  ((TextView) findViewById(R.id.message)).setText("Request Failed");
                }
            });
        }

    public static void getUsers(String groupName) {
        Call<List<User>> getTasksCall = client.getUsers(groupName);
        getTasksCall.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> request, Response<List<User>> response) {
                //  ((TextView) findViewById(R.id.message)).setText(response.body().get(0).getTaskName());
                Data.setAllUsers(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> request, Throwable t) {
                //  ((TextView) findViewById(R.id.message)).setText("Request Failed");
            }
        });
    }

        public static void createTask(Tasks t1) {
            client = ServiceBuilder.buildService(ServiceClientInterface.class);
            Call<Tasks> createTaskCall = client.createTask(t1);
            createTaskCall.enqueue(new Callback<Tasks>() {
                @Override
                public void onResponse(Call<Tasks> request, Response<Tasks> response) {
                    if (response.body().getId() != -1) {
                      //  ((TextView) findViewById(R.id.message)).setText(response.body().getTaskName());
                    } else {
                      //  ((TextView) findViewById(R.id.message)).setText("Task wasnt added");
                    }
                }

                @Override
                public void onFailure(Call<Tasks> request, Throwable t) {
                   // ((TextView) findViewById(R.id.message)).setText("Request Failed");
                }
            });
        }

        public static void updateTask(Tasks t1) {
            Call<Tasks> updateTaskCall = client.updateTask(t1);
            updateTaskCall.enqueue(new Callback<Tasks>() {
                @Override
                public void onResponse(Call<Tasks> request, Response<Tasks> response) {
                    if (response.body().getId() != -1) {
                      //  ((TextView) findViewById(R.id.message)).setText(response.body().getTaskName());
                    } else {
                      //  ((TextView) findViewById(R.id.message)).setText("Task wasnt added");
                    }
                }

                @Override
                public void onFailure(Call<Tasks> request, Throwable t) {
                  //  ((TextView) findViewById(R.id.message)).setText("Request Failed");
                }
            });
        }

    public static void updateUser(User u1) {
        Call<User> updateUserCall = client.updateUser(u1);
        updateUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> request, Response<User> response) {
                if (response.body().getId() != -1) {
                    Data.setPassword(response.body().getPassword());
                } else {
                    //  ((TextView) findViewById(R.id.message)).setText("Task wasnt added");
                }
            }

            @Override
            public void onFailure(Call<User> request, Throwable t) {
                //  ((TextView) findViewById(R.id.message)).setText("Request Failed");
            }
        });
    }

    public static void updateOtherUser(User u1) {
        Call<User> updateUserCall = client.updateOtherUser(u1);
        updateUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> request, Response<User> response) {
                if (response.body().getId() != -1) {
                    //
                } else {
                    Data.setNotification("user not updated");
                }
            }

            @Override
            public void onFailure(Call<User> request, Throwable t) {
                //  ((TextView) findViewById(R.id.message)).setText("Request Failed");
            }
        });
    }



        public static void createChange(Change c1) {
            Call<Change> createChangeCall = client.createChange(c1);
            createChangeCall.enqueue(new Callback<Change>() {
                @Override
                public void onResponse(Call<Change> request, Response<Change> response) {
                  //  ((TextView) findViewById(R.id.message)).setText(response.body().getChangeType());
                }

                @Override
                public void onFailure(Call<Change> request, Throwable t) {
                  //  ((TextView) findViewById(R.id.message)).setText("Request Failed");
                }
            });
        }


        public static void existCheck(final String groupName, String userName, String password) {
            client = ServiceBuilder.buildService(ServiceClientInterface.class);
            Call<ExistsCheck> existCheckCall = client.getGroupUserPassword(groupName, userName, password);
            existCheckCall.enqueue(new Callback<ExistsCheck>() {
                @Override
                public void onResponse(Call<ExistsCheck> request, Response<ExistsCheck> response) {

                     check= response.body().getExists().toString();
                     if (check.equals("1") || check.equals("0") ) {
                         Data.setCheck("exist");
                         Data.setIsAdmin(check);
                         getTasks(groupName);
                     }else{
                         Data.setCheck("Wrong login details enter");
                         Data.setNotification("Incorrect login details entered");
                     }
                }

                @Override
                public void onFailure(Call<ExistsCheck> request, Throwable t) {
                    Data.setNotification("failed to connect to database");
                }

            });

        }

        public static void createGroup(User u1) {
            client = ServiceBuilder.buildService(ServiceClientInterface.class);
            Call<User> createGroupCall = client.createGroup(u1);
            createGroupCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> request, Response<User> response) {
                    if (response.body().getId() !=-1 ) {
                     Data.setCheck("exist");
                     Data.setIsAdmin(check);
                     getTasks(Data.getGroupName());
                    }else{
                        Data.setCheck("Group with this name already exists");
                        Data.setNotification("Group with this name already exists");

                    }
                }
                @Override
                public void onFailure(Call<User> request, Throwable t) {
                    Data.setNotification("failed to connect to database");
                }
            });
        }


        public static void createUser(User u1) {
            Call<User> createUserCall = client.createUser(u1);
            createUserCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> request, Response<User> response) {
                    if (response.body().getId() == 0) {
                      //  ((TextView) findViewById(R.id.message)).setText(response.body().getUserName() + " Was created");
                    } else {
                       // ((TextView) findViewById(R.id.message)).setText("User wasn't added ");
                    }
                }

                @Override
                public void onFailure(Call<User> request, Throwable t) {
                   // ((TextView) findViewById(R.id.message)).setText("Request Failed");
                }
            });
        }


        public static void deleteTask(int id) {
            Call<Void> deleteTaskCall = client.deleteTask(id);
            deleteTaskCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> request, Response<Void> response) {

                  //  ((TextView) findViewById(R.id.message)).setText("Removing succesful");
                }

                @Override
                public void onFailure(Call<Void> request, Throwable t) {
                   // ((TextView) findViewById(R.id.message)).setText("Request Failed");
                }
            });
        }

    public static void deleteUser(String groupName, String userName) {
        Call<Void> deleteTaskCall = client.deleteUser(groupName, userName);
        deleteTaskCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> request, Response<Void> response) {

                //  ((TextView) findViewById(R.id.message)).setText("Removing succesful");
            }

            @Override
            public void onFailure(Call<Void> request, Throwable t) {
                // ((TextView) findViewById(R.id.message)).setText("Request Failed");
            }
        });
    }
    }



