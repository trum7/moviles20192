package unal.edu.co.directorio.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.directorio.model.Category;
import unal.edu.co.directorio.model.Company;
import unal.edu.co.directorio.model.CompanyCategory;
import unal.edu.co.directorio.service.CategoryService;
import unal.edu.co.directorio.service.CompanyCategoryService;
import unal.edu.co.directorio.service.CompanyService;

public class ListController
{

    private CompanyService companyService;
    private CategoryService categoryService;
    private CompanyCategoryService companyCategoryService;

    public ListController(Context context ){
        companyService = new CompanyService( context );
        categoryService = new CategoryService( context );
        companyCategoryService = new CompanyCategoryService( context );
    }

    public String[] getCategories( ){
        return CategoryService.categories;
    }

    public List<Company> getAllCompanies( ){
        return  companyService.getAll( );
    }

    public List<Company> getCompaniesByCategoryId( Integer id ){
        Category category = categoryService.findById( id );
        List<Company> companies = new ArrayList<>( );
        if( category != null ) {
            List<CompanyCategory> companyCategory = companyCategoryService.findByCategory(category);
            for (int i = 0; i < companyCategory.size(); i++) {
                companies.add(companyService.findById(companyCategory.get(i).getCompanyId()));
            }
        }
        return companies;
    }

    public List<Company> searchCompanies( String name ){
        return companyService.searchByName( name );
    }

    public List<Company> searchCompanies( String name, int item ){
        return companyService.searchByName( name, item );
    }
}
