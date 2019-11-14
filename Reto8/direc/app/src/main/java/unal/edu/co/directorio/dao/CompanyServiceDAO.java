package unal.edu.co.directorio.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import unal.edu.co.directorio.model.CompanyService;

@Dao
public interface CompanyServiceDAO
{
    @Query( "SELECT * FROM company_service" )
    List<CompanyService> getAll( );

    @Query( "SELECT * FROM company_service WHERE id = (:id)" )
    CompanyService findById( Integer id );

    @Query( "SELECT * FROM company_service WHERE id IN (:ids)" )
    List<CompanyService> findByIds( List<Integer> ids );

    @Query( "SELECT * FROM company_service WHERE company_id = (:companyId)" )
    List<CompanyService> findByCompanyId( Integer companyId );

    @Query( "SELECT * FROM company_service WHERE service_id = (:serviceId)" )
    List<CompanyService> findByServiceId( Integer serviceId );

    @Insert
    void insert( CompanyService companyService );

    @Update
    void update( CompanyService companyService );

    @Delete
    void delete( CompanyService companyService );

    @Delete
    void delete( List<CompanyService> companyService );
}
