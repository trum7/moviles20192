package unal.edu.co.directorio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.directorio.R;
import unal.edu.co.directorio.controller.RegisterController;
import unal.edu.co.directorio.model.Category;
import unal.edu.co.directorio.model.Company;
import unal.edu.co.directorio.model.Service;

public class RegisterActivity extends AppCompatActivity {

    private String name;
    private String webSite;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private List<String> services;
    private List<String> categories;

    private RegisterController signUpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUpController = new RegisterController( this );
    }

    public void signUp( View view ){

        boolean create = true;

        name = ((EditText) findViewById( R.id.editName )).getText( ).toString( ).trim( );
        webSite = ((EditText) findViewById( R.id.editWebSite )).getText( ).toString( ).trim( );
        phone = ((EditText) findViewById( R.id.editPhone )).getText( ).toString( ).trim( );
        email = ((EditText) findViewById( R.id.editEmail )).getText( ).toString( ).trim( );
        username = ((EditText) findViewById( R.id.editUsername )).getText( ).toString( ).trim( );
        password = ((EditText) findViewById( R.id.editPassword )).getText( ).toString( ).trim( );
        confirmPassword = ((EditText) findViewById( R.id.editConfirmPassword )).getText( ).toString( ).trim( );

        if( "".equals( name ) ){
            ((EditText) findViewById( R.id.editName )).setError( "This field can not be Empty" );
            create = false;
        }
        if( "".equals( webSite ) ){
            ((EditText) findViewById( R.id.editWebSite )).setError( "This field can not be Empty" );
            create = false;
        }
        if( "".equals( phone ) ){
            ((EditText) findViewById( R.id.editPhone )).setError( "This field can not be Empty" );
            create = false;
        }
        if( "".equals( email ) ){
            ((EditText) findViewById( R.id.editEmail )).setError( "This field can not be Empty" );
            create = false;
        }
        if( "".equals( username ) ){
            ((EditText) findViewById( R.id.editUsername )).setError( "This field can not be Empty" );
            create = false;

        }
        if( !confirmPassword.equals( password ) ){
            ((EditText) findViewById( R.id.editConfirmPassword )).setError( "The password and confirm password can be match" );
            create = false;
        }

        LinearLayout linearLayout = findViewById( R.id.serviceLinearLayout );
        List<String> stringServices = new ArrayList<>( );
        for( int i = 0; i < linearLayout.getChildCount( ); i++ ){
            if( linearLayout.getChildAt( i ) instanceof EditText ){
                String service = ((EditText) linearLayout.getChildAt( i )).getText( ).toString( ).trim( );
                if( service.equals( "" ) ){
                    ((EditText) linearLayout.getChildAt( i )).setError( "This field can not be Empty" );
                    create = false;
                    break;
                }
                stringServices.add( service );
            }
        }
        List<Service> services = signUpController.getServices( stringServices );

        boolean consultancy = ((CheckBox) findViewById( R.id.consultancy )).isChecked( );
        boolean softwareDevelopment = ((CheckBox) findViewById( R.id.softwareDevelopment )).isChecked( );
        boolean customDevelopment = ((CheckBox) findViewById( R.id.customDevelopment )).isChecked( );
        List<Category> categories = signUpController.getCategories( new boolean[] {consultancy, softwareDevelopment, customDevelopment} );

        if( categories.size( ) == 0 ) {
            ((TextView) findViewById(R.id.textCategory)).setError("You need to choose one or more Categories");
            create = false;
        }

        if( create ) {
            Company company = new Company( name, webSite, phone, email, username, password, services, categories );
            signUpController.registerCompany( company );
            finish( );
        }
    }

    public void addService( View view ){
        EditText editText = new EditText( this );
        editText.setLayoutParams( new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT ) );
        editText.setHint( R.string.service );
        ((TextView) findViewById( R.id.textServices )).setText( getResources().getString( R.string.service ) + "s" );
        LinearLayout linearLayout = findViewById( R.id.serviceLinearLayout );
        linearLayout.addView( editText, linearLayout.getChildCount( ) - 1 );
    }
}
