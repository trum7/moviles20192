package unal.edu.co.directorio.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import unal.edu.co.directorio.database.AppDatabase;
import unal.edu.co.directorio.model.Category;

public class CategoryRepository
{
    private static final String DB_NAME = "busdir";

    private AppDatabase appDatabase;

    public CategoryRepository( Context context ){
        appDatabase = Room.databaseBuilder( context, AppDatabase.class, DB_NAME ).build( );
    }

    public List<Category> getAll( ){
        List<Category> categories = null;
        try{
            categories = (new AsyncTask<Void, Void, List<Category>>( ){
                @Override
                protected List<Category> doInBackground( Void... voids ){
                    return appDatabase.categoryDAO( ).getAll( );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return categories;
    }

    public Category findById( final Integer id ){
        Category category = null;
        try{
            category = (new AsyncTask<Void, Void, Category>( ){
                @Override
                protected Category doInBackground( Void... voids ){
                    return appDatabase.categoryDAO( ).findById( id );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return category;
    }

    public List<Category> findByIds( final List<Integer> ids ){
        List<Category> categories = null;
        try{
            categories = (new AsyncTask<Void, Void, List<Category>>( ){
                @Override
                protected List<Category> doInBackground( Void... voids ){
                    return appDatabase.categoryDAO( ).findByIds( ids );
                }
            }).execute( ).get( );
        }catch( Exception e ){
            e.printStackTrace( );
        }

        return categories;
    }

    public void insert( final Category category ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.categoryDAO( ).insert( category );
                return null;
            }
        }.execute( );
    }

    public void update( final Category category ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.categoryDAO( ).update( category );
                return null;
            }
        }.execute( );
    }

    public void delete( final Integer id ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.categoryDAO( ).delete( appDatabase.categoryDAO( ).findById( id ) );
                return null;
            }
        }.execute( );
    }

    public void delete( final Category category ){
        new AsyncTask<Void, Void, Void>( ){
            @Override
            protected Void doInBackground( Void... voids ){
                appDatabase.categoryDAO( ).delete( category );
                return null;
            }
        }.execute( );
    }
}