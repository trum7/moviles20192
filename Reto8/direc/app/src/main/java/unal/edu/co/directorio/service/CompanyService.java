package unal.edu.co.directorio.service;

import android.content.Context;

import java.util.List;

import unal.edu.co.directorio.model.Company;
import unal.edu.co.directorio.repository.CompanyRepository;

public class CompanyService
{
    private CompanyRepository companyRepository;

    public CompanyService( Context context ){
        companyRepository = new CompanyRepository( context );
    }

    public Company findById( Integer id ){
        return companyRepository.findById( id );
    }

    public List<Company> findById( List<Integer> ids ){
        return companyRepository.findByIds( ids );
    }

    public Company findByUsername( String username ){
        return companyRepository.findByUsername( username );
    }

    public List<Company> searchByName( String name, int categoryId ){
        return companyRepository.searchByNameAndCategory( name, categoryId );
    }

    public List<Company> searchByName( String name ){
        return companyRepository.searchByName( name );
    }

    public List<Company> getAll( ){
        return companyRepository.getAll( );
    }

    public void save( Company company ){
        if( company.getId( ) == null || findById( company.getId( ) ) == null ) {
            company.setId( companyRepository.insert( company ).intValue( ) );
        }else
            companyRepository.update( company );
    }

    public void delete( Company company ){
        companyRepository.delete( company );
    }

    public void delete( Integer id ){
        companyRepository.delete( id );
    }
}