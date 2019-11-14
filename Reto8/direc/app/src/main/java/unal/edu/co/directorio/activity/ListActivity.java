package unal.edu.co.directorio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import unal.edu.co.directorio.R;
import unal.edu.co.directorio.controller.ListController;
import unal.edu.co.directorio.model.Company;

public class ListActivity extends AppCompatActivity {

    private ListController searchController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        searchController = new ListController( this );

        final Spinner spinner = findViewById( R.id.categorySpinner );
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_item );
        arrayAdapter.add( "---" );
        String[] categories = searchController.getCategories( );
        for( int i = 0; i < categories.length; i++ ){
            arrayAdapter.add( categories[i] );
        }
        spinner.setAdapter( arrayAdapter );
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener( ){
            @Override
            public void onItemSelected( AdapterView<?> parent, View view, int position, long id ){
                ArrayAdapter<Company> arrayAdapterCompany = new ArrayAdapter<>( ListActivity.this, android.R.layout.simple_list_item_1 );

                if( position == 0 ){
                    arrayAdapterCompany.addAll( new ArrayList<>( searchController.getAllCompanies( ) ) );
                    ((ListView) findViewById( R.id.companyList )).setAdapter( arrayAdapterCompany );
                    return;
                }

                arrayAdapterCompany.addAll( new ArrayList<>( searchController.getCompaniesByCategoryId( position ) ) );
                ((ListView) findViewById( R.id.companyList )).setAdapter( arrayAdapterCompany );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        ArrayAdapter<Company> arrayAdapterCompany = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1 );
        arrayAdapterCompany.addAll( new ArrayList<>( searchController.getAllCompanies( ) ) );
        ((ListView) findViewById( R.id.companyList )).setAdapter( arrayAdapterCompany );

        ((EditText) findViewById( R.id.categorySearch )).addTextChangedListener( new TextWatcher( ){
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ){ }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ){
                int item = (int) spinner.getSelectedItemId( );
                String search = s.subSequence( start, count ).toString( );
                ArrayAdapter<Company> arrayAdapterCompany = new ArrayAdapter<>( ListActivity.this, android.R.layout.simple_list_item_1 );

                if( item == 0 )
                    arrayAdapterCompany.addAll( new ArrayList<>( searchController.searchCompanies( search ) ) );
                else
                    arrayAdapterCompany.addAll( new ArrayList<>( searchController.searchCompanies( search, item ) ) );

                ((ListView) findViewById( R.id.companyList )).setAdapter( arrayAdapterCompany );
            }

            @Override
            public void afterTextChanged( Editable s ){ }
        });
    }
}
