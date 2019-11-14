package unal.edu.co.directorio.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import unal.edu.co.directorio.R;
import unal.edu.co.directorio.controller.DashController;
import unal.edu.co.directorio.model.Company;

public class DashActivity extends AppCompatActivity {

    private DashController enterpriseController;
    private Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        enterpriseController = new DashController( this );
        company = enterpriseController.getCompany( getIntent( ).getStringExtra( "<username>" ) );
    }

    public void viewCompanies( View view ){
        startActivity( new Intent( this, ListActivity.class ) );
    }

    public void companySettings( View view ){
        startActivity( new Intent( this, RefactorActivity.class ).putExtra( "<username>", company.getUsername( ) ) );
    }

    public void deleteCompany( View view ){
        AlertDialog alertDialog = new AlertDialog.Builder( this ).create( );
        alertDialog.setTitle( "Company's Name" );
        alertDialog.setMessage( "To confirm, enter the correct name of company and press confirm" );
        final EditText editText = new EditText( this );
        alertDialog.setView( editText );
        alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "Confirm",
                new DialogInterface.OnClickListener( ){
                    public void onClick( DialogInterface dialog, int which ){
                        if( editText.getText( ).toString( ).equals( company.getName( ) ) ) {
                            enterpriseController.delete( company );
                            Toast.makeText( DashActivity.this, "Complete Delete for Company", Toast.LENGTH_LONG ).show( );
                            finish( );
                            startActivity( new Intent( DashActivity.this, LoginActivity.class ) );
                        }else{
                            Toast.makeText( DashActivity.this, "Error with Company's Name", Toast.LENGTH_LONG ).show( );
                        }
                        dialog.dismiss( );
                    }
                });
        alertDialog.setButton( AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener( ){
                    public void onClick( DialogInterface dialog, int which ){
                        dialog.dismiss( );
                    }
                });

        alertDialog.show( );
        alertDialog.getButton( AlertDialog.BUTTON_NEGATIVE ).setTextColor( getResources( ).getColor( R.color.colorPrimaryDark ) );
    }


}
