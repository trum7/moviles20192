package unal.edu.co.directorio.service;

import android.content.Context;

import java.util.List;

import unal.edu.co.directorio.model.Service;
import unal.edu.co.directorio.repository.ServiceRepository;

public class ServiceService
{
    private ServiceRepository serviceRepository;

    public ServiceService( Context context ){
        serviceRepository = new ServiceRepository( context );
    }

    public Service findById( Integer id ){
        return serviceRepository.findById( id );
    }

    public List<Service> findById( List<Integer> ids ){
        return serviceRepository.findByIds( ids );
    }

    public void save( Service service ){
        if( service.getId( ) == null || findById( service.getId( ) ) == null )
            service.setId( serviceRepository.insert(service).intValue( ) );
        else
            serviceRepository.update( service );
    }

    public void delete( Service service ){
        serviceRepository.delete( service );
    }

    public void delete( Integer id ){
        serviceRepository.delete( id );
    }
}