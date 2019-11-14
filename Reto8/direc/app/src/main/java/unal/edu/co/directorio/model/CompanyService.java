package unal.edu.co.directorio.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity( tableName = "company_service", indices = { @Index( value = { "company_id", "service_id" },
        unique = true ) } )
public class CompanyService
{
    @PrimaryKey
    private Integer id;
    @ColumnInfo( name = "company_id" )
    private Integer companyId;
    @ColumnInfo( name = "service_id" )
    private Integer serviceId;

    public CompanyService( ){ }

    public CompanyService( Integer companyId, Integer serviceId ){
        this.companyId = companyId;
        this.serviceId = serviceId;
    }

    public Integer getId( ){
        return id;
    }

    public void setId( Integer id ){
        this.id = id;
    }

    public Integer getCompanyId( ){
        return companyId;
    }

    public void setCompanyId( Integer companyId ){
        this.companyId = companyId;
    }

    public Integer getServiceId( ){
        return serviceId;
    }

    public void setServiceId( Integer serviceId ){
        this.serviceId = serviceId;
    }
}