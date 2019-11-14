package unal.edu.co.directorio.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import unal.edu.co.directorio.model.Category;

@Dao
public interface CategoryDAO
{
    @Query( "SELECT * FROM category" )
    List<Category> getAll( );

    @Query( "SELECT * FROM category WHERE id = (:id)" )
    Category findById( Integer id );

    @Query( "SELECT * FROM category WHERE id IN (:ids)" )
    List<Category> findByIds( List<Integer> ids );

    @Insert
    void insert( Category category );

    @Update
    void update( Category category );

    @Delete
    void delete( Category category );
}
