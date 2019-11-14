package unal.edu.co.directorio.service;

import android.content.Context;

import java.util.List;

import unal.edu.co.directorio.model.Category;
import unal.edu.co.directorio.repository.CategoryRepository;

public class CategoryService
{
    public static final String[] categories = { "Consultoria", "Desarrollo de software", "Desarrollo a la medida" };

    private CategoryRepository categoryRepository;

    public CategoryService( Context context ){
        categoryRepository = new CategoryRepository( context );
    }

    public List<Category> getAll( ){
        return categoryRepository.getAll( );
    }

    public Category findById( Integer id ){
        return categoryRepository.findById( id );
    }

    public List<Category> findById( List<Integer> ids ){
        return categoryRepository.findByIds( ids );
    }

    public void save( Category category ){
        if( category.getId( ) == null || findById( category.getId( ) ) == null )
            categoryRepository.insert( category );
        else
            categoryRepository.update( category );
    }

    public void delete( Category category ){
        categoryRepository.delete( category );
    }

    public void delete( Integer id ){
        categoryRepository.delete( id );
    }
}