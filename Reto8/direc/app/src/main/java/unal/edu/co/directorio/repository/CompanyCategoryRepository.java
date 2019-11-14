package unal.edu.co.directorio.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import unal.edu.co.directorio.database.AppDatabase;
import unal.edu.co.directorio.model.CompanyCategory;

public class CompanyCategoryRepository
{
    private static final String DB_NAME = "busdir";

    private AppDatabase appDatabase;

    public CompanyCategoryRepository( Context context ){
        appDatabase = Room.databaseBuilder( context, AppDatabase.class, DB_NAME ).build( );
    }

    public List<CompanyCategory> getAll( ){
        List<CompanyCategory> companyCategories = null;
        try{
            companyCategories = (new AsyncTask<Void, Void, List<CompanyCategory>>( ){
                @Override
                protected List<CompanyCategory> doInBackground( Void... voids ){
                    return appDatabase.companyCategoryDAO( ).getAll( );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyCategories;
    }

    public CompanyCategory findById( final Integer id ){
        CompanyCategory companyCategory = null;
        try{
            companyCategory = (new AsyncTask<Void, Void, CompanyCategory>( ){
                @Override
                protected CompanyCategory doInBackground( Void... voids ){
                    return appDatabase.companyCategoryDAO( ).findById( id );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyCategory;
    }

    public List<CompanyCategory> findByIds( final List<Integer> ids ){
        List<CompanyCategory> companyCategories = null;
        try{
            companyCategories = (new AsyncTask<Void, Void, List<CompanyCategory>>( ){
                @Override
                protected List<CompanyCategory> doInBackground( Void... voids ){
                    return appDatabase.companyCategoryDAO( ).findByIds( ids );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyCategories;
    }

    public List<CompanyCategory> findByCompanyId( final Integer companyId ){
        List<CompanyCategory> companyCategories = null;
        try{
            companyCategories = (new AsyncTask<Void, Void, List<CompanyCategory>>( ){
                @Override
                protected List<CompanyCategory> doInBackground( Void... voids ){
                    return appDatabase.companyCategoryDAO( ).findByCompanyId( companyId );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyCategories;
    }

    public List<CompanyCategory> findByCategoryId( final Integer categoryId ){
        List<CompanyCategory> companyCategories = null;
        try{
            companyCategories = (new AsyncTask<Void, Void, List<CompanyCategory>>( ){
                @Override
                protected List<CompanyCategory> doInBackground( Void... voids ){
                    return appDatabase.companyCategoryDAO( ).findByCategoryId( categoryId );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return companyCategories;
    }

    public Long insert( final CompanyCategory companyCategory ){
        try{
            return new AsyncTask < Void,Void, Long > () {
                @Override
                protected Long doInBackground (Void...voids ){
                    return appDatabase.companyCategoryDAO().insert(companyCategory);
                }
            }.execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }
        return null;
    }

    public void update( final CompanyCategory companyCategory ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyCategoryDAO( ).update( companyCategory );
                return null;
            }
        }.execute( );
    }

    public void delete( final Integer id ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyCategoryDAO( ).delete( findById( id ) );
                return null;
            }
        }.execute( );
    }

    public void delete( final CompanyCategory companyCategory ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyCategoryDAO( ).delete( companyCategory );
                return null;
            }
        }.execute( );
    }

    public  void delete( final List<CompanyCategory> companyCategories ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.companyCategoryDAO( ).delete( companyCategories );
                return null;
            }
        }.execute( );
    }
}