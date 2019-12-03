package unal.edu.co.webservice;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{

    private Calendar calendar;
    private EditText editText;
    private ListView listView;
    private final String url = "https://www.datos.gov.co/resource/88a8-vbbx.json";
    private static final String[] mes = { "enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre" };

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        calendar = Calendar.getInstance( );
        editText = findViewById( R.id.date );
        listView = findViewById( R.id.list );

        final OnDateSetListener date = new OnDateSetListener( ){
            @Override
            public void onDateSet( DatePicker view, int year, int month, int dayOfMonth ){
                calendar.set( year, month, dayOfMonth );
                updateLabel( );
            }
        };

        editText.setOnClickListener( new OnClickListener( ){
            @Override
            public void onClick( View v ){
                DatePickerDialog datePickerDialog = new DatePickerDialog( MainActivity.this,
                    date, calendar.get( Calendar.YEAR ), calendar.get( Calendar.MONTH ),
                    calendar.get( Calendar.DAY_OF_MONTH ) );
                datePickerDialog.getDatePicker( ).setMinDate( System.currentTimeMillis( ) );
                datePickerDialog.show( );
            }
        });
    }

    private void updateLabel( ){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat( myFormat, Locale.US );

        editText.setText( sdf.format( calendar.getTime( ) ) );
    }

    public void click( View view ){

        RequestQueue queue = Volley.newRequestQueue( this );
        JsonArrayRequest jsonRequest = new JsonArrayRequest( url, new Listener<JSONArray>( ){
            @Override
            public void onResponse( JSONArray response ){
                List<String> list = new ArrayList<>( );
                for( int i = 0; i < response.length( ); i++ ){
                    try {
                        JSONObject json = response.getJSONObject(i);
                        String[] fecha = json.getString( "fecha" ).split( " " );
                        if( (fecha[fecha.length - 1].toLowerCase( )).equals( mes[calendar.get( Calendar.MONTH )] ) ){
                            StringBuilder stringBuilder = new StringBuilder( );
                            stringBuilder.append( "Nombre: " );
                            stringBuilder.append( json.getString( "nombre_del_evento" ) + "\n" );
                            stringBuilder.append( "Fecha: " );
                            stringBuilder.append( json.getString( "fecha" ) + "\n" );
                            stringBuilder.append( "Municipio: " );
                            stringBuilder.append( json.getString( "municipio" ) + "\n" );
                            list.add( stringBuilder.toString( ) );
                        }
                    }catch( Exception e ){
                        e.printStackTrace( );
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( getApplicationContext( ),
                        android.R.layout.simple_list_item_1 );
                arrayAdapter.addAll( list );
                listView.setAdapter( arrayAdapter );
            }
        }, new ErrorListener( ){
            @Override
            public void onErrorResponse( VolleyError error ){

            }
        } );
        queue.add( jsonRequest );

    }
}
