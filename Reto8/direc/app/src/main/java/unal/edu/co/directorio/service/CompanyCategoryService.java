package unal.edu.co.directorio.service;

import android.content.Context;

import java.util.List;

import unal.edu.co.directorio.model.Category;
import unal.edu.co.directorio.model.Company;
import unal.edu.co.directorio.model.CompanyCategory;
import unal.edu.co.directorio.repository.CompanyCategoryRepository;

public class CompanyCategoryService
{
    private CompanyCategoryRepository companyCategoryRepository;

    public CompanyCategoryService( Context context ){
        companyCategoryRepository = new CompanyCategoryRepository( context );
    }

    public CompanyCategory findById( Integer id ){
        return companyCategoryRepository.findById( id );
    }

    public List<CompanyCategory> findById( List<Integer> ids ){
        return companyCategoryRepository.findByIds( ids );
    }

    public List<CompanyCategory> findByCompany( Company company ){
        return companyCategoryRepository.findByCompanyId( company.getId( ) );
    }

    public List<CompanyCategory> findByCategory( Category category ){
        return companyCategoryRepository.findByCategoryId( category.getId( ) );
    }

    public void save( CompanyCategory companyCategory ){
        companyCategory.setId( companyCategoryRepository.insert( companyCategory ).intValue( ) );
    }

    public void delete( Integer id ){
        delete( findById( id ) );
    }

    public void delete( CompanyCategory companyCategory ){
        companyCategoryRepository.delete( companyCategory );
    }

    public void delete( List<CompanyCategory> companyCategories ){
        for( int i = 0; i < companyCategories.size( ); i++  )
            delete( companyCategories.get( i ) );
    }

}
