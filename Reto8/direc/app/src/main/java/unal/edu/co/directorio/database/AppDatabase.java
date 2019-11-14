package unal.edu.co.directorio.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import unal.edu.co.directorio.dao.CategoryDAO;
import unal.edu.co.directorio.dao.CompanyCategoryDAO;
import unal.edu.co.directorio.dao.CompanyDAO;
import unal.edu.co.directorio.dao.CompanyServiceDAO;
import unal.edu.co.directorio.dao.ServiceDAO;
import unal.edu.co.directorio.model.Category;
import unal.edu.co.directorio.model.Company;
import unal.edu.co.directorio.model.CompanyCategory;
import unal.edu.co.directorio.model.CompanyService;
import unal.edu.co.directorio.model.Service;

@Database( entities = { Category.class, Company.class, CompanyCategory.class, CompanyService.class, Service.class }, version = 1 )
public abstract class AppDatabase extends RoomDatabase
{
    public abstract CategoryDAO categoryDAO( );
    public abstract CompanyCategoryDAO companyCategoryDAO( );
    public abstract CompanyDAO companyDAO( );
    public abstract CompanyServiceDAO companyServiceDAO( );
    public abstract ServiceDAO serviceDAO( );
}