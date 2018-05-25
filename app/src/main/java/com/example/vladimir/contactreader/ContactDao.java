package com.example.vladimir.contactreader;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact getById(long id);


//    @Query("UPDATE orders SET order_amount = :amount, price = :price WHERE order_id =:id")
//    void update(Float amount, Float price, int id);

    @Query("UPDATE contact SET phone = :phone WHERE id =:id")
    void updatePhone(String phone, int id);


    @Query("UPDATE contact SET email = :email WHERE id =:id")
    void updateEmail(String email, int id);


    @Query("UPDATE contact SET name = :name WHERE id =:id")
    void updateName(String name, int id);


    @Query("UPDATE contact SET surname = :surname WHERE id =:id")
    void updateSurname(String surname, int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Contact> list);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
}
