package net.kibotu.de.infoini.door.door;

import android.content.Context;
import net.kibotu.utils.JsonParser;
import net.kibotu.logger.Logger;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Jan Rabe
 * Date: 15/11/12
 * Time: 00:00
 */
public class InfoIniStatus {

    public static final String TAG = InfoIniStatus.class.getSimpleName();
    public boolean doorIsOpen;
    public Cafepot cane1;
    public Cafepot cane2;

    public InfoIniStatus(boolean doorIsOpen, @NotNull Cafepot cane1, @NotNull Cafepot cane2) {
        this.doorIsOpen = doorIsOpen;
        this.cane1 = cane1;
        this.cane2 = cane2;
    }

    public InfoIniStatus(@NotNull JSONObject jsonObject) throws JSONException {
        doorIsOpen = jsonObject.getString( "status" ).equals( "OPEN" );
        JSONArray cafepots = jsonObject.getJSONArray( "pots" );
        cane1 = new Cafepot( cafepots.getJSONObject( 0 ) );
        cane2 = new Cafepot( cafepots.getJSONObject( 1 ) );
    }

    /**
     * gets infoini status by json url
     *
     * @param url
     * @return infoini status
     */
    public static InfoIniStatus getInfoIniStatus(@NotNull Context context, @NotNull String url) {

        InfoIniStatus infoIniStatus = null;

        // check connections
        try {
            JSONObject json = JsonParser.readJson( url );
            if ( json == null ) return null;
            infoIniStatus = new InfoIniStatus( json );
        } catch ( JSONException e ) {
            Logger.e( TAG, "Wrong version of Infoini status file." );
        }

        return infoIniStatus;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( !( o instanceof InfoIniStatus ) ) return false;

        InfoIniStatus that = ( InfoIniStatus ) o;

        if ( doorIsOpen != that.doorIsOpen ) return false;
        if ( cane1 != null ? !cane1.equals( that.cane1 ) : that.cane1 != null ) return false;
        if ( cane2 != null ? !cane2.equals( that.cane2 ) : that.cane2 != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ( doorIsOpen ? 1 : 0 );
        result = 31 * result + ( cane1 != null ? cane1.hashCode() : 0 );
        result = 31 * result + ( cane2 != null ? cane2.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append( "InfoIniStatus" );
        sb.append( "{doorIsOpen=" ).append( doorIsOpen );
        sb.append( ", cane1=" ).append( cane1 );
        sb.append( ", cane2=" ).append( cane2 );
        sb.append( '}' );
        return sb.toString();
    }
}
