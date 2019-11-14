package unal.edu.co.directorio.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import unal.edu.co.directorio.model.CompanyCategory;

@Dao
public interface CompanyCategoryDAO
{
    @Query( "SELECT * FROM company_category" )
    List<CompanyCategory> getAll( );

    @Query( "SELECT * FROM company_category WHERE id = (:id)" )
    CompanyCategory findById( Integer id );

    @Query( "SELECT * FROM company_category WHERE id IN (:ids)" )
    List<CompanyCategory> findByIds( List<Integer> ids );

    @Query( "SELECT * FROM company_category WHERE company_id = (:companyId)" )
    List<CompanyCategory> findByCompanyId( Integer companyId );

    @Query( "SELECT * FROM company_category WHERE category_id = (:categoryId)" )
    List<CompanyCategory> findByCategoryId( Integer categoryId );

    @Insert
    Long insert( CompanyCategory companyCategory );

    @Update
    void update( CompanyCategory companyCategory );

    @Delete
    void delete( CompanyCategory companyCategory );

    @Delete
    void delete( List<CompanyCategory> companyCategories );
}