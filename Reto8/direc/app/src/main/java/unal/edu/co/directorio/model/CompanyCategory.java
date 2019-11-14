package unal.edu.co.directorio.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity( tableName = "company_category", indices = { @Index( value = { "company_id", "category_id" },
        unique = true ) } )
public class CompanyCategory
{
    @PrimaryKey
    private Integer id;
    @ColumnInfo( name = "company_id" )
    private Integer companyId;
    @ColumnInfo( name = "category_id" )
    private Integer categoryId;

    public CompanyCategory( ){ }

    public CompanyCategory( Integer companyId, Integer categoryId ){
        this.companyId = companyId;
        this.categoryId = categoryId;
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

    public Integer getCategoryId( ){
        return categoryId;
    }

    public void setCategoryId( Integer categoryId ){
        this.categoryId = categoryId;
    }

}
