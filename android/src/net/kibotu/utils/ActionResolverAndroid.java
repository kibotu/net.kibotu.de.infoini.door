package net.kibotu.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;
import com.badlogic.gdx.Gdx;
import net.kibotu.de.infoini.door.MainActivity;
import net.kibotu.infoini.general.utils.ActionResolver;
import net.kibotu.logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class ActionResolverAndroid implements ActionResolver {

    private static final String TAG = ActionResolver.class.getSimpleName();
    Handler uiThread;
    MainActivity appContext;

    public ActionResolverAndroid(MainActivity appContext) {
        uiThread = new Handler();
        this.appContext = appContext;
    }

    /**
     * Saves a bitmap into a folder.
     *
     * @param screenshot
     * @param filePath
     * @param fileName
     */
    public static void saveBitmap(@NotNull Bitmap screenshot, @NotNull String filePath, @NotNull String fileName) {
        OutputStream outStream = null;
        filePath = Environment.getExternalStorageDirectory().toString() + "/" + filePath;
        File dir = new File( filePath );
        dir.mkdirs();
        File output = new File( filePath, fileName );
        try {
            outStream = new FileOutputStream( output );
            screenshot.compress( Bitmap.CompressFormat.PNG, 100, outStream );
            outStream.flush();
            outStream.close();
            Logger.v( TAG, "Saving Screenshot [" + filePath + fileName + "]" );
        } catch ( FileNotFoundException e ) {
            Logger.e( TAG, "" + e.getMessage() );
        } catch ( IOException e ) {
            Logger.e( TAG, "" + e.getMessage() );
        }
    }

    @Override
    public void showShortToast(final CharSequence toastMessage) {
        showToast( toastMessage, Toast.LENGTH_SHORT );
    }

    @Override
    public void showLongToast(final CharSequence toastMessage) {
        showToast( toastMessage, Toast.LENGTH_LONG );
    }

    @Override
    public void showToast(final CharSequence toastMessage, final int toastDuration) {
        uiThread.post( new Runnable() {
            public void run() {
                Toast.makeText( appContext, toastMessage, toastDuration )
                        .show();
            }
        } );
    }

    @Override
    public void showAlertBox(final String alertBoxTitle,
                             final String alertBoxMessage, final String alertBoxButtonText) {
        uiThread.post( new Runnable() {
            public void run() {
                new AlertDialog.Builder( appContext )
                        .setTitle( alertBoxTitle )
                        .setMessage( alertBoxMessage )
                        .setNeutralButton( alertBoxButtonText,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                    }
                                } ).create().show();
            }
        } );
    }

    @Override
    public void openUri(String uri) {
        Uri myUri = Uri.parse( uri );
        Intent intent = new Intent( Intent.ACTION_VIEW, myUri );
        appContext.startActivity( intent );
    }

    @Override
    public void showMyList() {
//        appContext.startActivity(new Intent(this.appContext, MyListActivity.class));
    }

    @Override
    public void exit() {
        appContext.finish();
    }

    @Override
    public void playHaptic(int amount) {
        appContext.playHaptic( amount );
    }

    @Override
    public void stopHaptic() {
        appContext.stopHaptic();
    }

    @Override
    public void captureScreenShot(@NotNull final String path, @NotNull final String fileName) {
        Bitmap screenshot = captureScreenShot();
        saveBitmap( screenshot, path, fileName );
        screenshot.recycle();
    }

    @NotNull
    public Bitmap captureScreenShot() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int screenshotSize = width * height;
        ByteBuffer bb = ByteBuffer.allocateDirect( screenshotSize * 4 );
        bb.order( ByteOrder.nativeOrder() );
        Gdx.graphics.getGL20().glReadPixels( 0, 0, width, height, Gdx.graphics.getGL20().GL_RGBA, Gdx.graphics.getGL20().GL_UNSIGNED_BYTE, bb );
        int pixelsBuffer[] = new int[screenshotSize];
        bb.asIntBuffer().get( pixelsBuffer );
        bb = null;
        Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.RGB_565 );
        bitmap.setPixels( pixelsBuffer, screenshotSize - width, -width, 0, 0, width, height );
        pixelsBuffer = null;

        short sBuffer[] = new short[screenshotSize];
        ShortBuffer sb = ShortBuffer.wrap( sBuffer );
        bitmap.copyPixelsToBuffer( sb );

        //Making created bitmap (from OpenGL points) compatible with Android bitmap
        for ( int i = 0; i < screenshotSize; ++i ) {
            short v = sBuffer[i];
            sBuffer[i] = ( short ) ( ( ( v & 0x1f ) << 11 ) | ( v & 0x7e0 ) | ( ( v & 0xf800 ) >> 11 ) );
        }
        sb.rewind();
        bitmap.copyPixelsFromBuffer( sb );
        return bitmap;
    }
}
