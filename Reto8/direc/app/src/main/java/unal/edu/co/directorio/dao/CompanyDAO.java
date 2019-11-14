package unal.edu.co.directorio.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import unal.edu.co.directorio.model.Company;

@Dao
public interface CompanyDAO
{
    @Query( "SELECT * FROM company" )
    List<Company> getAll( );

    @Query( "SELECT * FROM company WHERE id = (:id)" )
    Company findById( Integer id );

    @Query( "SELECT * FROM company WHERE id IN (:ids)" )
    List<Company> findByIds( List<Integer> ids );

    @Query( "SELECT * FROM company WHERE username = (:username)" )
    Company findByUsername( String username );

    @Query( "SELECT * FROM company WHERE name LIKE (:name)" )
    List<Company> searchByName( String name );

    @Query( "SELECT c.* FROM company c JOIN company_category cc ON c.id = cc.company_id WHERE c.name LIKE (:name) AND cc.category_id = (:categoryId)" )
    List<Company> searchByNameAndCategory( String name, int categoryId );

    @Insert
    Long insert( Company company );

    @Update
    void update( Company company );

    @Delete
    void delete( Company company );

}
