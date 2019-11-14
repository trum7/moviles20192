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

public class UpdateController
{
    private CategoryService categoryService;
    private CompanyService companyService;
    private CompanyServiceService companyServiceService;
    private CompanyCategoryService companyCategoryService;
    private ServiceService serviceService;

    public UpdateController(Context context ){
        categoryService = new CategoryService( context );
        companyService = new CompanyService( context );
        companyCategoryService = new CompanyCategoryService( context );
        companyServiceService = new CompanyServiceService( context );
        serviceService = new ServiceService( context );
    }

    public Company getCompany( String username ){
        return companyService.findByUsername( username );
    }

    public List<Service> getCompanyServices( Company company ){
        List<unal.edu.co.directorio.model.CompanyService> companyServices = companyServiceService.findByCompany( company );
        List<Service> services = new ArrayList<>( );
        for( int i = 0; i < companyServices.size( ); i++ ){
            services.add( serviceService.findById( companyServices.get( i ).getServiceId( ) ) );
        }
        return services;
    }

    public boolean[] getCompanyCategories( Company company ){
        List<CompanyCategory> companyCategories = companyCategoryService.findByCompany( company );
        boolean[] categories = { false, false, false };
        for( int i = 0; i < companyCategories.size( ); i++ )
            categories[companyCategories.get( i ).getCategoryId( ) - 1] = true;
        return categories;
    }

    public void delete( Company company ){
        companyServiceService.delete( companyServiceService.findByCompany( company ) );
        companyCategoryService.delete( companyCategoryService.findByCompany( company ) );
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

    public List<Category> getCategories(boolean[] categories ){
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

    public void registerCompany( Company company ){
        companyService.save( company );
        for( int i = 0; i < company.getCategories( ).size( ); i++ ){
            companyCategoryService.save( new CompanyCategory( company.getId( ), company.getCategories( ).get( i ).getId( ) ) );
        }
        for( int i = 0; i < company.getServices( ).size( ); i++ ){
            companyServiceService.save( new unal.edu.co.directorio.model.CompanyService( company.getId( ), company.getServices( ).get( i ).getId( ) ) );
        }
    }
}
