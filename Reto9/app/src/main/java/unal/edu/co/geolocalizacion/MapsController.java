package unal.edu.co.geolocalizacion;

public class MapsController
{

    final String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    final String LOCATION = "location=";
    final String RADIUS = "radius=";
    final String TYPE = "type=";
    final String KEY = "key=AIzaSyB2uwapiy041iTJy2ZIvUiEjoT-D3UnYqU";
    final String AMPERSAND = "&";

    public MapsController( ){ }

    public String getURL( String location, String radius, String type ){
        return URL + LOCATION + location + AMPERSAND + RADIUS + radius + AMPERSAND + TYPE + type + AMPERSAND + KEY;
    }
}
