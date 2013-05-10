package net.kibotu.infoini.general.assets;

import net.kibotu.infoini.general.IniApp;
import net.kibotu.logger.Logger;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class CookieHandler implements CookieCallBack {

    private static final String TAG = CookieHandler.class.getSimpleName();

    @Override
    public void onFinishLoading(FortuneCookie cookie) {
        IniApp.actionResolver.showLongToast( cookie.message + " - " + cookie.source );

        Logger.v( TAG, "toast" + cookie.message + " - " + cookie.source );
    }
}
