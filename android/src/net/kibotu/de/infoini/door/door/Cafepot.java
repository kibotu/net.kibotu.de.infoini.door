package net.kibotu.de.infoini.door.door;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Jan Rabe
 * Date: 15/11/12
 * Time: 00:12
 */
public class Cafepot {

    public static final String UNAVAILIBLE = "UNAVAILIBLE";
    public static final String AVAILIBLE = "AVAILIBLE";
    protected String status = UNAVAILIBLE;
    protected float level;

    public Cafepot(String status, float level) {
        this.status = status;
        this.level = level;
    }

    public Cafepot(@NotNull JSONObject jsonObject) throws JSONException {
        status = jsonObject.getString( "status" );
        level = ( jsonObject.has( "level" ) ) ? ( float ) jsonObject.getDouble( "level" ) : 0f;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append( "Cafepot" );
        sb.append( "{status='" ).append( status ).append( '\'' );
        sb.append( ", level=" ).append( level );
        sb.append( '}' );
        return sb.toString();
    }

    public int getCups() {
        return ( int ) Math.round( level / 10 );
    }
}
