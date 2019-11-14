package unal.edu.co.directorio.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import unal.edu.co.directorio.database.AppDatabase;
import unal.edu.co.directorio.model.CompanyService;

public class CompanyServiceRepository
{
    private static final String DB_NAME = "busdir";

    private AppDatabase appDatabase;

    public CompanyServiceRepository( Context context ){
        appDatabase = Room.databaseBuilder( context, AppDatabase.class, DB_NAME ).build( );
    }

    public List<CompanyService> getAll( ){
        List<CompanyService> companyServices = null;
        try{
            companyServices = (new AsyncTask<Void, Void, List<CompanyService>>( ){
                @Override
                protected List<CompanyService> doInBackground( Void... voids ){
                    return appDatabase.companyServiceDAO( ).getAll( );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyServices;
    }

    public CompanyService findById( final Integer id ){
        CompanyService companyService = null;
        try{
            companyService = (new AsyncTask<Void, Void, CompanyService>( ){
                @Override
                protected CompanyService doInBackground( Void... voids ){
                    return appDatabase.companyServiceDAO( ).findById( id );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyService;
    }

    public List<CompanyService> findByIds(final List<Integer> ids ){
        List<CompanyService> companyServices = null;
        try{
            companyServices = (new AsyncTask<Void, Void, List<CompanyService>>( ){
                @Override
                protected List<CompanyService> doInBackground( Void... voids ){
                    return appDatabase.companyServiceDAO( ).findByIds( ids );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyServices;
    }

    public List<CompanyService> findByCompanyId( final Integer companyId ){
        List<CompanyService> companyServices = null;
        try{
            companyServices = (new AsyncTask<Void, Void, List<CompanyService>>( ){
                @Override
                protected List<CompanyService> doInBackground( Void... voids ){
                    return appDatabase.companyServiceDAO( ).findByCompanyId( companyId );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyServices;
    }

    public List<CompanyService> findByServiceId( final Integer serviceId ){
        List<CompanyService> companyServices = null;
        try{
            companyServices = (new AsyncTask<Void, Void, List<CompanyService>>( ){
                @Override
                protected List<CompanyService> doInBackground( Void... voids ){
                    return appDatabase.companyServiceDAO( ).findByServiceId( serviceId );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyServices;
    }

    public void insert( final CompanyService companyService ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyServiceDAO( ).insert( companyService );
                return null;
            }
        }.execute( );
    }

    public void update( final CompanyService companyService ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyServiceDAO( ).update( companyService );
                return null;
            }
        }.execute( );
    }

    public void delete( final Integer id ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyServiceDAO( ).delete( findById( id ) );
                return null;
            }
        }.execute( );
    }

    public void delete( final CompanyService companyService ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyServiceDAO( ).delete( companyService );
                return null;
            }
        }.execute( );
    }
}