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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Contact> list);

    @Update
    void update(List<Contact> list);

    @Delete
    void delete(Contact contact);
}
