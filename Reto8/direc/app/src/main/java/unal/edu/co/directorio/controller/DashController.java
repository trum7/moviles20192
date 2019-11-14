package unal.edu.co.directorio.controller;

import android.content.Context;

import unal.edu.co.directorio.model.Company;
import unal.edu.co.directorio.service.CompanyCategoryService;
import unal.edu.co.directorio.service.CompanyService;
import unal.edu.co.directorio.service.CompanyServiceService;

public class DashController
{
    private CompanyService companyService;
    private CompanyServiceService companyServiceService;
    private CompanyCategoryService companyCategoryService;

    public DashController(Context context ){
        companyService = new CompanyService( context );
        companyServiceService = new CompanyServiceService( context );
        companyCategoryService = new CompanyCategoryService( context );
    }

    public Company getCompany( String username ){
        return companyService.findByUsername( username );
    }

    public void delete( Company company ){
        companyCategoryService.delete( companyCategoryService.findByCompany( company ) );
        companyServiceService.delete( companyServiceService.findByCompany( company ) );
        companyService.delete( company );
    }
}
