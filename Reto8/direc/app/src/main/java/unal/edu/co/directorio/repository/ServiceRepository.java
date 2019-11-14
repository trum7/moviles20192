package unal.edu.co.directorio.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import unal.edu.co.directorio.database.AppDatabase;
import unal.edu.co.directorio.model.Service;

public class ServiceRepository
{
    private static final String DB_NAME = "busdir";

    private AppDatabase appDatabase;

    public ServiceRepository( Context context ){
        appDatabase = Room.databaseBuilder( context, AppDatabase.class, DB_NAME ).build( );
    }

    public List<Service> getAll( ){
        List<Service> services = null;
        try{
            services = (new AsyncTask<Void, Void, List<Service>>( ){
                @Override
                protected List<Service> doInBackground( Void... voids ){
                    return appDatabase.serviceDAO( ).getAll( );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return services;
    }

    public Service findById( final Integer id ){
        Service service = null;
        try{
            service = (new AsyncTask<Void, Void, Service>( ){
                @Override
                protected Service doInBackground( Void... voids ){
                    return appDatabase.serviceDAO( ).findById( id );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return service;
    }

    public List<Service> findByIds( final List<Integer> ids ){
        List<Service> services = null;
        try{
            services = (new AsyncTask<Void, Void, List<Service>>( ){
                @Override
                protected List<Service> doInBackground( Void... voids ){
                    return appDatabase.serviceDAO( ).findByIds( ids );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return services;
    }

    public Long insert( final Service service ){
        try {
            return (new AsyncTask<Void, Void, Long>() {
                @Override
                protected Long doInBackground(Void... voids) {
                    return appDatabase.serviceDAO().insert(service);
                }
            }.execute( )).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }
        return null;
    }

    public void update( final Service service ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.serviceDAO( ).update( service );
                return null;
            }
        }.execute( );
    }

    public void delete( final Integer id ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.serviceDAO( ).delete( appDatabase.serviceDAO( ).findById( id ) );
                return null;
            }
        }.execute( );
    }

    public void delete( final Service service ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.serviceDAO( ).delete( service );
                return null;
            }
        }.execute( );
    }
}
