package unal.edu.co.directorio.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import unal.edu.co.directorio.database.AppDatabase;
import unal.edu.co.directorio.model.Company;

public class CompanyRepository
{
    private static final String DB_NAME = "busdir";

    private AppDatabase appDatabase;

    public CompanyRepository( Context context ){
        appDatabase = Room.databaseBuilder( context, AppDatabase.class, DB_NAME ).build( );
    }

    public List<Company> getAll( ){
        List<Company> companies = null;
        try{
            companies = (new AsyncTask<Void, Void, List<Company>>( ){
                @Override
                protected List<Company> doInBackground( Void... voids ){
                    return appDatabase.companyDAO( ).getAll( );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companies;
    }

    public Company findById( final Integer id ){
        Company company = null;
        try{
            company = (new AsyncTask<Void, Void, Company>( ){
                @Override
                protected Company doInBackground( Void... voids ){
                    return appDatabase.companyDAO( ).findById( id );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return company;
    }

    public List<Company> findByIds( final List<Integer> ids ){
        List<Company> companies = null;
        try{
            companies = (new AsyncTask<Void, Void, List<Company>>( ){
                @Override
                protected List<Company> doInBackground( Void... voids ){
                    return appDatabase.companyDAO( ).findByIds( ids );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companies;
    }

    public Company findByUsername( final String username ){
        Company company = null;
        try{
            company = (new AsyncTask<Void, Void, Company>( ){
                @Override
                protected Company doInBackground( Void... voids ){
                    return appDatabase.companyDAO( ).findByUsername( username );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return company;
    }

    public List<Company> searchByName( final String name ){
        List<Company> companies = null;
        try{
            companies = (new AsyncTask<Void, Void, List<Company>>( ){
                @Override
                protected List<Company> doInBackground( Void... voids ){
                    return appDatabase.companyDAO( ).searchByName( name + "%" );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companies;
    }

    public List<Company> searchByNameAndCategory( final String name, final int categoryId ){
        List<Company> companies = null;
        try{
            companies = (new AsyncTask<Void, Void, List<Company>>( ){
                @Override
                protected List<Company> doInBackground( Void... voids ){
                    return appDatabase.companyDAO( ).searchByNameAndCategory( name + "%", categoryId );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companies;
    }

    public Long insert( final Company company ){
        try {
            return new AsyncTask<Void, Void, Long>( ){
                @Override
                protected Long doInBackground( Void... voids ){
                    return appDatabase.companyDAO( ).insert( company );
                }
            }.execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }
        return null;
    }

    public void update( final Company company ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyDAO( ).update( company );
                return null;
            }
        }.execute( );
    }

    public void delete( final Integer id ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyDAO( ).delete( appDatabase.companyDAO( ).findById( id ) );
                return null;
            }
        }.execute( );
    }

    public void delete( final Company company ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyDAO( ).delete( company );
                return null;
            }
        }.execute( );
    }

}
