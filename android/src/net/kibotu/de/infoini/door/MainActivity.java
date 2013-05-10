package net.kibotu.de.infoini.door;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.flurry.android.FlurryAgent;
import com.immersion.uhl.Launcher;
import net.kibotu.de.infoini.door.door.InfoIniStatus;
import net.kibotu.infoini.general.Constants;
import net.kibotu.infoini.general.IniApp;
import net.kibotu.infoini.general.utils.ActionResolver;
import net.kibotu.infoini.general.utils.IHapticFeedBack;
import net.kibotu.infoini.general.utils.UpdateHandler;
import net.kibotu.logger.Logger;
import net.kibotu.logger.android.GdxLogger;
import net.kibotu.utils.ActionResolverAndroid;
import net.kibotu.utils.ConnectionDetector;
import net.kibotu.utils.ShakerHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Observable;
import java.util.Observer;

import static net.kibotu.infoini.general.Constants.*;

public class MainActivity extends AndroidApplication implements ShakerHandler.Callback, Observer, IHapticFeedBack {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    public ActionResolver actionResolver;
    private IniApp app;
    private ShakerHandler shaker;
    private Launcher launcher;

    public static Intent getOpenFacebookIntent(@NotNull Context context, @NotNull String id, @NotNull String dns) {
        try {
            context.getPackageManager().getPackageInfo( FB_PACKAGE_NAME, 0 );
            // try fb app
            return new Intent( Intent.ACTION_VIEW, Uri.parse( FB_URI_ID + id ) );
        } catch ( Exception e ) {
            // else call fb in browser
            return new Intent( Intent.ACTION_VIEW, Uri.parse( FB_URI + dns ) );
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        actionResolver = new ActionResolverAndroid( this );
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.r = 8;
        cfg.g = 8;
        cfg.b = 8;
        cfg.a = 8;
        app = new IniApp( actionResolver );
        app.handler.addObserver( this );
        initialize( app, cfg );

        // shake updates
        shaker = new ShakerHandler( this, Constants.THRESHOLD, Constants.GAP, this );

        // rgba 8888
        if ( graphics.getView() instanceof SurfaceView )
            ( ( SurfaceView ) graphics.getView() ).getHolder().setFormat( PixelFormat.RGBA_8888 );

        // haptic
        launcher = new Launcher( this );
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.init( new GdxLogger(), Constants.LOGGING_TAG, Constants.LOGGING_LEVEL );
        FlurryAgent.onStartSession( this, FLURRY_API_KEY );
    }

    @Override
    public void onStop() {
        super.onStop();
        FlurryAgent.onEndSession( this );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ) {

            case R.id.homepage:
                openBrowser( Constants.INFOINI_HOMEPAGE_URL );
                actionResolver.playHaptic( Launcher.FAST_PULSE_33 );
                return true;
            case R.id.facebook:
                startFacebook( Constants.INFOINI_FACEBOOK_ID, Constants.INFOINI_FACEBOOK_DNS );
                actionResolver.showShortToast( "Opening Facebook." );
                actionResolver.playHaptic( Launcher.FAST_PULSE_33 );
                return true;
            case R.id.twitter:
                openBrowser( Constants.INFOINI_TWITTER_URL );
                actionResolver.showShortToast( "Opening Twitter." );
                actionResolver.playHaptic( Launcher.FAST_PULSE_33 );
                return true;
            case R.id.zuendstoff:
                openBrowser( Constants.GOOGLE_ONLINE_PDF_READER_URL + Constants.INFOINI_LATEST_ZUENDSTOFF_URL );
                actionResolver.showShortToast( "Opening Zündstoff." );
                actionResolver.playHaptic( Launcher.FAST_PULSE_33 );
                return true;
//            case R.id.toggle_messages:
//                toggleMessages();
//                return true;
            case R.id.webapp:
                openBrowser( Constants.INFOINI_WEBAPP_URL );
                actionResolver.showShortToast( "Opening WebApp." );
                actionResolver.playHaptic( Launcher.FAST_PULSE_33 );
                return true;
            case R.id.red_phone:
                dial( Constants.INFOINI_PHONE_NUMBER );
                actionResolver.playHaptic( Launcher.ALERT10 );
                return true;
            case R.id.openings:
                openBrowser( Constants.GOOGLE_ONLINE_PDF_READER_URL + Constants.INFOINI_LATEST_OPENING_TIMES_URL );
                actionResolver.showShortToast( "Opening Opening Times." );
                actionResolver.playHaptic( Launcher.FAST_PULSE_33 );
                return true;
            case R.id.mensa:
                openBrowser( Constants.MENSA );
                actionResolver.showShortToast( "Opening Mensa." );
                actionResolver.playHaptic( Launcher.FAST_PULSE_33 );
                return true;
//            case R.id.members:
//                IniApp.getInstance().playMembersDialog();
//                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    private void dial(String phoneNumber) {
        Intent i = new Intent( Intent.ACTION_DIAL );
        String p = "tel:" + phoneNumber;
        i.setData( Uri.parse( p ) );
        startActivity( i );
    }

    /**
     * Starts new Activity with url data.
     *
     * @param url
     */
    private void openBrowser(String url) {
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        startActivity(i);
        Gdx.net.openURI( url );
    }

    /**
     * Convenient method for directly starting the facebook app or browser if app isn't installed.
     *
     * @param id  facebook user id
     * @param dns facebook id name
     */
    private void startFacebook(String id, String dns) {
        startActivity( getOpenFacebookIntent( this, id, dns ) );
    }

    private void togglePlanets() {
//        if (!isOpenGLEnabled()) return;
//        center.isVisible(!center.isVisible());
//        editor.putBoolean("planetsVisible", center.isVisible());
//        editor.commit();
//        toast(this, "Turning earth globe " + (center.isVisible() ? "on." : "off."));
    }

    @Override
    public void shakingStarted() {
        requestUpdate();
    }

    public void requestUpdate() {
//        actionResolver.showShortToast("Fetching update...");
        final MainActivity activity = this;
        synchronized ( activity ) {
            new Thread( new Runnable() {
                @Override
                public void run() {
                    activity.update( ( ConnectionDetector.hasActiveInternetConnection( activity ) ) ? InfoIniStatus.getInfoIniStatus( activity, Constants.INFOINI_API_COMBINED ) : null );
                }
            } ).start();
        }
    }

    public void update(@Nullable InfoIniStatus status) {
        if ( status == null ) app.update( DOOR_OFFLINE, 10, 10 );
        else {
            app.update( status.doorIsOpen ? DOOR_OPEN : DOOR_CLOSED, status.cane1.getCups(), status.cane2.getCups() );
        }
    }

    @Override
    public void shakingStopped() {
    }

    @Override
    public void update(Observable observable, Object o) {
        if ( !( o instanceof String ) ) return;
        switch ( ( String ) o ) {
            case UpdateHandler.REQUEST_UPDATE:
                requestUpdate();
                break;
        }
    }

    @Override
    public void playHaptic(int amount) {
        launcher.play( amount );
    }

    @Override
    public void stopHaptic() {
        launcher.stop();
    }
}
