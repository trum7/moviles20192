package unal.edu.co.directorio.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class Company
{
    @PrimaryKey
    private Integer id;
    private String name;
    @ColumnInfo( name = "web_site" )
    private String webSite;
    private String phone;
    private String email;
    private String username;
    private String password;
    @Ignore
    private List<Service> services;
    @Ignore
    private List<Category> categories;

    public Company( ){ }

    public Company( String name, String webSite, String phone, String email, String username, String password, List<Service> services, List<Category> categories ){
        this.name = name;
        this.webSite = webSite;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.services = services;
        this.categories = categories;
    }

    public Company( Integer id, String name, String webSite, String phone, String email, String username,
                    String password, List<Service> services, List<Category> categories ){
        this.id = id;
        this.name = name;
        this.webSite = webSite;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.services = services;
        this.categories = categories;
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

    public String getWebSite( ){
        return webSite;
    }

    public void setWebSite( String webSite ){
        this.webSite = webSite;
    }

    public String getPhone( ){
        return phone;
    }

    public void setPhone( String phone ){
        this.phone = phone;
    }

    public String getEmail( ){
        return email;
    }

    public void setEmail( String email ){
        this.email = email;
    }

    public String getUsername( ){
        return username;
    }

    public void setUsername( String username ){
        this.username = username;
    }

    public String getPassword( ){
        return password;
    }

    public void setPassword( String password ){
        this.password = password;
    }

    public List<Service> getServices( ){
        return services;
    }

    public void setServices( List<Service> services ){
        this.services = services;
    }

    public List<Category> getCategories( ){
        return categories;
    }

    public void setCategories( List<Category> categories ){
        this.categories = categories;
    }

    @Override
    public String toString( ){
        String string =  name + "\n" + webSite + "\n" + email + "\n" + phone;
        return string;
    }
}