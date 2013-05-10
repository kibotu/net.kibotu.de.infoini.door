package net.kibotu.infoini.general.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.HttpParametersUtils;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.XmlReader;
import net.kibotu.logger.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static net.kibotu.infoini.general.Constants.*;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class FortuneCookie implements Net.HttpResponseListener {

    private static final String TAG = FortuneCookie.class.getSimpleName();
    public String message;
    public String source;
    private CookieCallBack callBack;

    public FortuneCookie() {
        message = "";
        source = "";
    }

    public void loadCookie(CookieCallBack callBack) {

        this.callBack = callBack;

        // params
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "code", FORTUNE_COOKIE_CODE );
        parameters.put( "nbr", String.valueOf( FORTUNE_COOKIE_AMOUNT ) );

        // build url
        final Net.HttpRequest httpGet = new Net.HttpRequest( Net.HttpMethods.GET );
        httpGet.setUrl( FORTUNE_COOKE_URL );
        httpGet.setContent( HttpParametersUtils.convertHttpParameters( parameters ) );

        // fill fileHandle listener
        Gdx.net.sendHttpRequest( httpGet, this );

        Logger.v( TAG, "sending request: " + httpGet.getContent() );
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {

        // check if http request is ok
        if ( httpResponse.getStatus().getStatusCode() != HttpStatus.SC_OK ) return;

        XmlReader reader = new XmlReader();
        XmlReader.Element element = null;
        try {
            element = reader.parse( httpResponse.getResultAsStream() );
        } catch ( IOException e ) {
            Logger.e( TAG, "failed to load cookie " + e.getMessage() );
        }

        if ( element == null ) return;

        StringBuilder builder = new StringBuilder();
        for ( XmlReader.Element s : element.getChild( 0 ).getChildrenByNameRecursively( "front" ) ) {
            builder.append( s.getText() );
        }
        message = builder.toString();
        source = element.getChild( 0 ).getChildByName( "back" ).getText();

        Logger.e( TAG, "handle response" );
        callBack.onFinishLoading( this );
    }

    @Override
    public void failed(Throwable t) {
        Logger.e( TAG, "failed to load " + t.getMessage() );
    }
}
