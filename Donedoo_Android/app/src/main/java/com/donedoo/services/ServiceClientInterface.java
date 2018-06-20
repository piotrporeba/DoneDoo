package com.donedoo.services;

import com.donedoo.model.Change;
import com.donedoo.model.ExistsCheck;
import com.donedoo.model.Tasks;
import com.donedoo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Genet on 3/3/2018.
 */

public interface ServiceClientInterface {

    @GET("read/tasks/{groupName}")
    Call<List<Tasks>> getTasks (@Path("groupName") String groupName);

    @GET("read/changes/{groupName}")
    Call<Change> getChange(@Path("groupName") String groupName);

    @GET("read/users/{groupName}/{userName}/{password}")
    Call<ExistsCheck> getGroupUserPassword(@Path("groupName") String groupName,
                                           @Path("userName") String userName,
                                           @Path("password") String password);

    @GET("read/allusers/{groupName}")
    Call<List<User>> getUsers(@Path ("groupName") String groupName);

    @POST("create/task/")
    Call<Tasks> createTask(@Body Tasks newTask);

    @POST("create/change")
    Call<Change> createChange(@Body Change newChange);

    @POST("create/group")
    Call<User> createGroup(@Body User newGroup);

    @POST("create/user")
    Call<User> createUser(@Body User newUser);

    @PUT("update/task")
    Call<Tasks> updateTask(@Body Tasks updateTask);

    @PUT("update/user")
    Call<User> updateUser(@Body User updateUser);

    @PUT("update/otheruser")
    Call<User> updateOtherUser(@Body User updateOtherUser);

    @DELETE("delete/{id}")
    Call<Void> deleteTask(@Path("id") int id);

    @DELETE("delete/user/{groupName}/{userName}")
    Call<Void> deleteUser(@Path("groupName") String groupName,@Path("userName") String userName);

}
