package net.kibotu.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import net.kibotu.infoini.general.IniApp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: Jan Rabe
 * Date: 14/11/12
 * Time: 22:14
 */
final public class ConnectionDetector {

    private final static String TAG = ConnectionDetector.class.getSimpleName();

    private ConnectionDetector() {
    }

    public static boolean hasActiveInternetConnection(Context context) {
        if ( isNetworkAvailable( context ) ) {
            try {
                HttpURLConnection urlc = ( HttpURLConnection ) ( new URL( "http://www.google.com" ).openConnection() );
                urlc.setRequestProperty( "User-Agent", "Test" );
                urlc.setRequestProperty( "Connection", "close" );
                urlc.setConnectTimeout( 5000 );
                urlc.connect();
                return ( urlc.getResponseCode() == 200 );
            } catch ( IOException e ) {
                IniApp.actionResolver.showShortToast( "Error checking internet connection" );
            }
        } else {
            IniApp.actionResolver.showShortToast( "No network available!" );
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = ( ConnectivityManager ) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        if ( connectivity != null ) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if ( info != null )
                for ( int i = 0; i < info.length; i++ )
                    if ( info[i].getState() == NetworkInfo.State.CONNECTED ) {
                        return true;
                    }

        }
        return false;
    }
}