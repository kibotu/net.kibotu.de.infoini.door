package net.kibotu.infoini.general;

import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.utils.GdxNativesLoader;
import net.kibotu.logger.ILogger;
import net.kibotu.logger.Logger;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
final public class Constants {

    /**
     * FILE PATHS
     */

    public static final String DATA_PATH = "data/";
    public static final String GRAPHICS_PATH = DATA_PATH + "Graphics/";
    public static final String SHADER_PATH = GRAPHICS_PATH + "Shader/";
    public static final String ATLASES_PATH = GRAPHICS_PATH + "Atlases/";
    public static final String MODELS_PATH = GRAPHICS_PATH + "Models/";

    /**
     * INFOINI RELATED
     */

    public static final String INFOINI_API_COMBINED = "http://infoini.de/api/combined.json";
    public static final String INFOINI_API_MEMBERS = "http://infoini.de/api/members.json";
    public static final String INFOINI_LATEST_ZUENDSTOFF_URL = "http://infoini.de/api/zuendstoff.pdf";
    public static final String INFOINI_LATEST_OPENING_TIMES_URL = "http://infoini.de/api/raumplan.pdf";
    public static final String INFOINI_HOMEPAGE_URL = "http://www.infoini.de";
    public static final String INFOINI_TWITTER_URL = "http://twitter.com/InfoINI";
    public static final String INFOINI_WEBAPP_URL = "http://infoini.de/webapp/";
    public static final String INFOINI_PHONE_NUMBER = "03045042318";
    public static final String MENSA = "http://www.studentenwerk-berlin.de/mensen/speiseplan/beuth/";

    /**
     * Fotune Cookies
     */
    public static final String FORTUNE_COOKIE_CODE = "391e32e72d811d174b9ede0adfa4286a";
    public static final int FORTUNE_COOKIE_AMOUNT = 1;
    public static final String FORTUNE_COOKE_URL = "http://api.fortunekookie.com/fortune_server.php";
    public static final String FORTUNE_COOKIE_URL_FORMAT = String.format( "%s?nbr=%d&code=%s", FORTUNE_COOKE_URL, FORTUNE_COOKIE_AMOUNT, FORTUNE_COOKIE_CODE );

    /**
     * Faceboook
     */

    public static final String FB_URI = "https://www.facebook.com/";
    public static final String FB_PACKAGE_NAME = "com.facebook.katana";
    public static final String FB_URI_ID = "fb://profile/";
    public static final String INFOINI_FACEBOOK_ID = "193965251945";
    public static final String INFOINI_FACEBOOK_DNS = "InfoINI";

    /**
     * Flurry
     */

    public static final String FLURRY_API_KEY = "VWGPJ2QCH8X34PR6SMSS";

    /**
     * GENERAL PURPOSE
     */

    public static final String GOOGLE_ONLINE_PDF_READER_URL = "http://docs.google.com/gview?embedded=true&url=";

    /**
     * SHADER
     */
    public static final String LIBGDX_DEFAULT_SHADER = "LIBGDX_DEFAULT_SHADER";
    public static final String PHONG_SHADER = "PHONG_SHADER";
    public static final String Libgdx_DefaultShader_vsh = SHADER_PATH + "Libgdx_DefaultShader.vsh";
    public static final String Libgdx_DefaultShader_fsh = SHADER_PATH + "Libgdx_DefaultShader.fsh";
    public static final String Phong_vsh = SHADER_PATH + "Phong.vsh";
    public static final String Phong_fsh = SHADER_PATH + "Phong.fsh";

    /**
     * Meshes
     */

    public static final String MODEL_COFFEE_CUP = MODELS_PATH + "coffee.obj";

    /**
     * Textures
     */

    public static final String DOOR_OPEN = ATLASES_PATH + "door_open.png";
    public static final String DOOR_CLOSED = ATLASES_PATH + "door_closed.png";
    public static final String DOOR_OFFLINE = ATLASES_PATH + "door_offline.png";
    public static final String WHITE = ATLASES_PATH + "white.png";

    /**
     * Cane
     */

    public static final int ROWS = 2;
    public static final int COLS = 5;
    public static final float H_SPACE = 4f;
    public static final float V_SPACE = 3.6f;

    /**
     * Door
     */

    public static final int DIMENSION = 21;

    /**
     * SHAKEHANDLER
     */

    public static final double THRESHOLD = 1.25d;
    public static final long GAP = 1000;
    public static final int TIMEOUT = 3000;

    /**
     * SPLINES
     */

    public final static int SPLINE_STEPS = 5;
    public final static float TWEEN_STEPS_DURATION = 2f / SPLINE_STEPS;
    public final static float TWEEN_FINAL_STEP_DURATION = 2f;
    public final static float TWEEN_OUT_DURATION = 1.3f;
    public final static TweenEquation TWEEN_STEPS_EQUATION = TweenEquations.easeInOutSine;
    public final static TweenEquation TWEEN_FINAL_STEP_EQUATION = TweenEquations.easeOutElastic;
    public static final TweenEquation TWEEN_FLY_OUT_EQUATION = TweenEquations.easeOutBounce;

    /**
     * SCREENSHOTS
     */

    public static final String SCREENSHOT_FOLDER = "IniApp";
    public static final boolean SCREENSHOTS_ENABLED = true;

    /**
     * LOGGER
     */

    public static final String LOGGING_TAG = "Ini";
    public static final Logger.Level LOGGING_LEVEL = Logger.Level.NO_LOGGING;

    /**
     * Static class.
     */
    private Constants() {
    }
}


