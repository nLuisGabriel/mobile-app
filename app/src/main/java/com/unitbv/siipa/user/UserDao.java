package com.unitbv.siipa.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * from User ORDER By username Asc")
    List<User> getUsers();

    @Delete
    void deleteUser(User user);
    @Insert
    void addUser(User user);

    @Query("SELECT COUNT(*) > 0 FROM User WHERE email = :email AND password = :password")
    Boolean isUserValid(String email, String password);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User getUserByUsernameAndPassword(String username, String password);

}
