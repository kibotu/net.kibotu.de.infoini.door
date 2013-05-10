package net.kibotu.infoini.general;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.istack.internal.NotNull;
import net.kibotu.infoini.general.assets.Assets;
import net.kibotu.infoini.general.assets.CookieHandler;
import net.kibotu.infoini.general.assets.FortuneCookie;
import net.kibotu.infoini.general.graphics.GLESPerspectiveCamera;
import net.kibotu.infoini.general.graphics.Light;
import net.kibotu.infoini.general.graphics.scene.MembersDialog;
import net.kibotu.infoini.general.infoini.Cane;
import net.kibotu.infoini.general.infoini.Door;
import net.kibotu.infoini.general.screens.MainScreen;
import net.kibotu.infoini.general.tween.NodeAcessor;
import net.kibotu.infoini.general.utils.ActionResolver;
import net.kibotu.infoini.general.utils.UpdateHandler;
import net.kibotu.logger.Logger;

import static net.kibotu.infoini.general.Constants.*;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
final public class IniApp extends Game {

    public static TweenManager tweenManager;
    public static ActionResolver actionResolver;
    public static volatile boolean isUpdating;
    private static IniApp instance;
    public final String TAG = IniApp.class.getSimpleName();
    public GLESPerspectiveCamera camera;
    public SpriteBatch batch;
    public Light light;
    public Cane[] cane;
    public Door door;
    public UpdateHandler handler;
    public FortuneCookie cookie;
    public MembersDialog membersDialog;
    private CookieHandler cookieHandler;

    public IniApp(@NotNull ActionResolver actionResolver) {
        super();
        instance = this;
        handler = new UpdateHandler();
        IniApp.actionResolver = actionResolver;
        cookie = new FortuneCookie();
        cookieHandler = new CookieHandler();
        membersDialog = new MembersDialog();
    }

    public static IniApp getInstance() {
        return instance;
    }

    @Override
    public void create() {
        Logger.v( TAG, "create" );

        // camera
        camera = new GLESPerspectiveCamera( 67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        camera.position.set( 0, 0, 35 );
        camera.direction.set( 0, 0, -1 );
        camera.near = 0.5f;
        camera.far = 1000f;
        camera.setBackground( Color.BLACK );

        // light
        light = new Light( Light.LIGHT_DIRECTIONAL_UNIFORM, 0, 0, -35, 0, 0, 1 );

        // sprite batch
        batch = new SpriteBatch();

        // tween manager
        tweenManager = new TweenManager();

        // register tween accessor
        Tween.registerAccessor( Cane.class, new NodeAcessor() );

        // to initialize the static AssetManager
        Assets.instance();
        Assets.create();

        // canes
        cane = new Cane[2];

        // top cane
        cane[0] = new Cane();
        cane[0].getNode().setPosition( -COLS / 2 * H_SPACE, V_SPACE * ROWS * 2, 0 );

        // lower cane
        cane[1] = new Cane();
        cane[1].getNode().setPosition( -COLS / 2 * H_SPACE, V_SPACE * ROWS, 0 );

        // ini logo
        door = new Door();

        handler.requestUpdate();

        setScreen( new MainScreen( this ) );
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.clear();
        Logger.v( TAG, "dispose" );
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize( width, height );
        Logger.v( TAG, "resize" );
        camera.translate( 0, 0, 0 );
    }

    @Override
    public void pause() {
        super.pause();
        Logger.v( TAG, "pause" );
        IniApp.actionResolver.exit();
    }

    @Override
    public void resume() {
        super.resume();
        Assets.create();
        Logger.v( TAG, "create" );
    }

    public void update(@NotNull String doorState, int amountCane1Cups, int amountCane2Cups) {
        Logger.v( TAG, "update" );

        door.setState( doorState );
        cane[0].setAmountCups( amountCane1Cups );
        cane[1].setAmountCups( amountCane2Cups );
        actionResolver.playHaptic( 95 );
        cookie.loadCookie( cookieHandler );
    }

    public void playMembersDialog() {
        membersDialog.show( true );
    }
}