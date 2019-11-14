package unal.edu.co.directorio.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import unal.edu.co.directorio.model.Service;

@Dao
public interface ServiceDAO
{
    @Query( "SELECT * FROM service" )
    List<Service> getAll( );

    @Query( "SELECT * FROM service WHERE id = (:id)" )
    Service findById( Integer id );

    @Query( "SELECT * FROM service WHERE id IN (:ids)" )
    List<Service> findByIds( List<Integer> ids );

    @Insert
    long insert( Service service );

    @Update
    void update( Service service );

    @Delete
    void delete( Service service );
}
