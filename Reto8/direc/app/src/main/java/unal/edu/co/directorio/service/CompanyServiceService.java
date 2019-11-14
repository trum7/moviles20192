package unal.edu.co.directorio.service;

import android.content.Context;

import java.util.List;

import unal.edu.co.directorio.model.Company;
import unal.edu.co.directorio.model.CompanyService;
import unal.edu.co.directorio.model.Service;
import unal.edu.co.directorio.repository.CompanyServiceRepository;

public class CompanyServiceService
{
    private CompanyServiceRepository companyServiceRepository;

    public CompanyServiceService( Context context ){
        companyServiceRepository = new CompanyServiceRepository( context );
    }

    public CompanyService findById( Integer id ){
        return companyServiceRepository.findById( id );
    }

    public List<CompanyService> findById( List<Integer> ids ){
        return companyServiceRepository.findByIds( ids );
    }

    public List<CompanyService> findByCompany( Company company ){
        return companyServiceRepository.findByCompanyId( company.getId( ) );
    }

    public List<CompanyService> findByService( Service service ){
        return companyServiceRepository.findByServiceId( service.getId( ) );
    }

    public void save( CompanyService companyService ){
        companyServiceRepository.insert( companyService );
    }

    public void delete( Integer id ){
        delete( findById( id ) );
    }

    public void delete( CompanyService companyService ){
        companyServiceRepository.delete( companyService );
    }

    public void delete( List<CompanyService> companyServices ){
        for( int i = 0; i < companyServices.size( ); i++  )
            delete( companyServices.get( i ) );
    }
}
