package unal.edu.co.directorio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.directorio.R;
import unal.edu.co.directorio.controller.UpdateController;
import unal.edu.co.directorio.model.Category;
import unal.edu.co.directorio.model.Company;
import unal.edu.co.directorio.model.Service;

public class RefactorActivity extends AppCompatActivity {

    private Company company;

    private UpdateController settingController;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        settingController = new UpdateController( this );

        company = settingController.getCompany( getIntent( ).getStringExtra( "<username>" ) );

        ((EditText) findViewById( R.id.editNameSetting )).setText( company.getName( ) );
        ((EditText) findViewById( R.id.editWebSiteSetting )).setText( company.getWebSite( ) );
        ((EditText) findViewById( R.id.editPhoneSetting )).setText( company.getPhone( ) );
        ((EditText) findViewById( R.id.editEmailSetting )).setText( company.getEmail( ) );
        ((EditText) findViewById( R.id.editUsernameSetting )).setText( company.getUsername( ) );
        ((EditText) findViewById( R.id.editPasswordSetting )).setText( company.getPassword( ) );
        List<Service> services = settingController.getCompanyServices( company );
        for( int i = 0; i < services.size( ); i++ ){
            addService( services.get( i ).getName( ) );
        }
        boolean[] checkBoxes = settingController.getCompanyCategories( company );
        ((CheckBox) findViewById( R.id.consultancySetting )).setChecked( checkBoxes[0] );
        ((CheckBox) findViewById( R.id.softwareDevelopmentSetting )).setChecked( checkBoxes[1] );
        ((CheckBox) findViewById( R.id.customDevelopmentSetting )).setChecked( checkBoxes[2] );
    }

    public void addService( View view ){
        EditText editText = new EditText( this );
        editText.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
        editText.setHint( R.string.service );
        ((TextView) findViewById( R.id.textServicesSetting )).setText( getResources( ).getString( R.string.service ) + "s" );
        LinearLayout linearLayout = findViewById( R.id.serviceLinearLayoutSetting );
        linearLayout.addView( editText, linearLayout.getChildCount( ) - 1 );
    }

    public void addService( String text ){
        EditText editText = new EditText( this );
        editText.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
        editText.setText( text );
        ((TextView) findViewById( R.id.textServicesSetting )).setText( getResources( ).getString( R.string.service ) + "s" );
        LinearLayout linearLayout = findViewById( R.id.serviceLinearLayoutSetting );
        linearLayout.addView( editText, linearLayout.getChildCount( ) - 1 );
    }

    public void update( View view ){

        settingController.delete( company );

        boolean update = true;

        company.setName( ((EditText) findViewById( R.id.editNameSetting )).getText( ).toString( ).trim( ) );
        company.setWebSite( ((EditText) findViewById( R.id.editWebSiteSetting )).getText( ).toString( ).trim( ) );
        company.setPhone( ((EditText) findViewById( R.id.editPhoneSetting )).getText( ).toString( ).trim( ) );
        company.setEmail( ((EditText) findViewById( R.id.editEmailSetting )).getText( ).toString( ).trim( ) );
        company.setUsername( ((EditText) findViewById( R.id.editUsernameSetting )).getText( ).toString( ).trim( ) );
        company.setPassword( ((EditText) findViewById( R.id.editPasswordSetting )).getText( ).toString( ).trim( ) );
        String confirmPassword = ((EditText) findViewById( R.id.editConfirmPasswordSetting )).getText( ).toString( ).trim( );

        if( "".equals( company.getName( ) ) ){
            ((EditText) findViewById( R.id.editNameSetting )).setError( "This field can not be Empty" );
            update = false;
        }
        if( "".equals( company.getWebSite( ) ) ){
            ((EditText) findViewById( R.id.editWebSiteSetting )).setError( "This field can not be Empty" );
            update = false;
        }
        if( "".equals( company.getPhone( ) ) ){
            ((EditText) findViewById( R.id.editPhoneSetting )).setError( "This field can not be Empty" );
            update = false;
        }
        if( "".equals( company.getEmail( ) ) ){
            ((EditText) findViewById( R.id.editEmailSetting )).setError( "This field can not be Empty" );
            update = false;
        }
        if( "".equals( company.getName( ) ) ){
            ((EditText) findViewById( R.id.editUsernameSetting )).setError( "This field can not be Empty" );
            update = false;

        }
        if( !confirmPassword.equals( company.getPassword( ) ) ){
            ((EditText) findViewById( R.id.editConfirmPasswordSetting )).setError( "The password and confirm password can be match" );
            update = false;
        }

        LinearLayout linearLayout = findViewById( R.id.serviceLinearLayoutSetting );
        List<String> stringServices = new ArrayList<>( );

        for( int i = 0; i < linearLayout.getChildCount( ); i++ ){
            if( linearLayout.getChildAt( i ) instanceof EditText ){
                String service = ((EditText) linearLayout.getChildAt( i )).getText( ).toString( ).trim( );
                if( service.equals( "" ) ){
                    ((EditText) linearLayout.getChildAt( i )).setError( "This field can not be Empty" );
                    update = false;
                    break;
                }
                stringServices.add( service );
            }
        }



        List<Service> services = settingController.getServices( stringServices );

        boolean consultancy = ((CheckBox) findViewById( R.id.consultancySetting )).isChecked( );
        boolean softwareDevelopment = ((CheckBox) findViewById( R.id.softwareDevelopmentSetting )).isChecked( );
        boolean customDevelopment = ((CheckBox) findViewById( R.id.customDevelopmentSetting )).isChecked( );
        List<Category> categories = settingController.getCategories( new boolean[] {consultancy, softwareDevelopment, customDevelopment} );

        if( categories.size( ) == 0 ) {
            ((TextView) findViewById( R.id.textCategorySetting )).setError("You need to choose one or more Categories");
            update = false;
        }

        if( update ) {
            company.setServices( services );
            company.setCategories( categories );
            settingController.registerCompany( company );
            finish( );
        }
    }
}
