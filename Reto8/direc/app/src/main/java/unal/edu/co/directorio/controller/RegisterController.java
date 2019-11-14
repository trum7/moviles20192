package unal.edu.co.directorio.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.directorio.model.Category;
import unal.edu.co.directorio.model.Company;
import unal.edu.co.directorio.model.CompanyCategory;
import unal.edu.co.directorio.model.Service;
import unal.edu.co.directorio.service.CategoryService;
import unal.edu.co.directorio.service.CompanyCategoryService;
import unal.edu.co.directorio.service.CompanyService;
import unal.edu.co.directorio.service.CompanyServiceService;
import unal.edu.co.directorio.service.ServiceService;

public class RegisterController
{
    private CompanyService companyService;
    private ServiceService serviceService;
    private CategoryService categoryService;
    private CompanyCategoryService companyCategoryService;
    private CompanyServiceService companyServiceService;

    public RegisterController(Context context ){
        companyService = new CompanyService( context );
        serviceService = new ServiceService( context );
        categoryService = new CategoryService( context );
        companyCategoryService = new CompanyCategoryService( context );
        companyServiceService = new CompanyServiceService( context );
    }

    public void registerCompany( Company company ){
        companyService.save( company );
        for( int i = 0; i < company.getCategories( ).size( ); i++ ){
            companyCategoryService.save( new CompanyCategory( company.getId( ), company.getCategories( ).get( i ).getId( ) ) );
        }
        for( int i = 0; i < company.getServices( ).size( ); i++ ){
            companyServiceService.save( new unal.edu.co.directorio.model.CompanyService( company.getId( ), company.getServices( ).get( i ).getId( ) ) );
        }
    }

    public List<Service> getServices( List<String> stringServices ){
        List<Service> services = new ArrayList<>( );
        for( int i = 0; i < stringServices.size( ); i++ ){
            Service service = new Service( stringServices.get( i ) );
            serviceService.save( service );
            services.add( service );
        }
        return services;
    }

    public List<Category> getCategories( boolean[] categories ){
        List<Category> categoryList = new ArrayList<>( );
        for( int i = 0; i < categories.length; i++ ){
            if( categories[i] ) {
                if( categoryService.findById( i + 1 ) == null ){
                    categoryService.save( new Category( i + 1, categoryService.categories[i] ) );
                }
                categoryList.add( categoryService.findById( i + 1 ) );
            }
        }
        return categoryList;
    }
}
