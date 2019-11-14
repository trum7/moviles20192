package unal.edu.co.directorio.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class Category
{
    @PrimaryKey
    private Integer id;
    private String name;
    @Ignore
    private List<Company> companies;

    public Category( ){ }

    public Category( Integer id, String name ){
        this.id = id;
        this.name = name;
    }

    public Category( Integer id, String name, List<Company> companies ){
        this.id = id;
        this.name = name;
        this.companies = companies;
    }

    public Integer getId( ){
        return id;
    }

    public void setId( Integer id ){
        this.id = id;
    }

    public String getName( ){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public List<Company> getCompanies( ){
        return companies;
    }

    public void setCompanies( List<Company> companies ){
        this.companies = companies;
    }
}
