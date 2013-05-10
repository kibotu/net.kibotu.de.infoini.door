package net.kibotu.infoini.general.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.sun.istack.internal.NotNull;
import net.kibotu.infoini.general.Constants;
import net.kibotu.infoini.general.IniApp;
import net.kibotu.infoini.general.assets.Assets;
import net.kibotu.infoini.general.graphics.GLESPerspectiveCamera;
import net.kibotu.infoini.general.graphics.Light;
import net.kibotu.infoini.general.graphics.scene.MembersDialog;
import net.kibotu.infoini.general.graphics.scene.RootNode;
import net.kibotu.infoini.general.infoini.Cane;
import net.kibotu.infoini.general.infoini.Door;
import net.kibotu.infoini.general.utils.UIDGenerator;
import net.kibotu.logger.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class MainScreen implements Screen, GestureDetector.GestureListener {

    public static final String TAG = MainScreen.class.getSimpleName();
    public static final float RAD_TO_DEG = ( float ) ( Math.PI / 180f );
    public static ShaderProgram libgdx;
    public static ShaderProgram phong;
    protected ConcurrentLinkedQueue<String> screenshotRequestedQueue;
    private GLESPerspectiveCamera camera;
    private Light light;
    private SpriteBatch batch;
    private RootNode stage;
    private Cane[] cane;
    private Door door;
    private float startTime = 0;
    private float speed = 50;
    private float velocityX = 0;
    private float velocityY = 0;
    private float scale;
    private boolean flinging = false;
    private MembersDialog membersDialog;

    public MainScreen(@NotNull IniApp app) {
        camera = app.camera;
        light = app.light;
        batch = app.batch;
        door = app.door;
        cane = app.cane;
        membersDialog = app.membersDialog;
        screenshotRequestedQueue = new ConcurrentLinkedQueue<>();
    }

    private void initGL() {
        Gdx.graphics.getGL20().glEnable( GL20.GL_BLEND );
        Gdx.graphics.getGL20().glEnable( GL20.GL_DITHER );
        Gdx.graphics.getGL20().glEnable( GL20.GL_DEPTH_TEST );
        Gdx.graphics.getGL20().glDisable( GL20.GL_CULL_FACE );   // spritebatch
        Gdx.graphics.getGL20().glEnable( GL20.GL_TEXTURE_2D );
        phong.begin();
    }

    @Override
    public void render(float delta) {

        IniApp.tweenManager.update( delta );

        startTime += delta * speed;

        // camera circle movement
        camera.position.z = 35f + ( float ) Math.sin( startTime * RAD_TO_DEG ) * 3f;
        camera.direction.x = ( float ) Math.cos( startTime * RAD_TO_DEG ) * 0.10f;

        phong.begin();

        // camera
        camera.clearScreen( Gdx.graphics.getGL20() );
        camera.update();
        camera.apply( phong );

        // light
//        light.position.set(camera.position);
//        light.direction.set(camera.direction);
        light.apply( phong );

        stage.setRotation( 0, 1, 0, startTime % 360 );

        // render mesh
        stage.render( phong );

        phong.end();

        // sprite batch
        batch.setShader( libgdx );
        batch.setProjectionMatrix( camera.combined );
        door.render( batch );

        batch.begin();
        membersDialog.render();
        batch.end();

        // capture screen on request
        if ( !screenshotRequestedQueue.isEmpty() )
            IniApp.actionResolver.captureScreenShot(Constants.SCREENSHOT_FOLDER, screenshotRequestedQueue.poll() );
    }

    @Override
    public void resize(int width, int height) {
        Logger.v( TAG, "resize" );
        Gdx.graphics.getGL20().glViewport( 0, 0, width, height );
        membersDialog.resize( width, height );
    }

    @Override
    public void show() {
        Logger.v( TAG, "show" );

        phong = Assets.activeShader.get( Constants.PHONG_SHADER );
        libgdx = Assets.activeShader.get( Constants.LIBGDX_DEFAULT_SHADER );
        stage = new RootNode();
        stage.addChild( cane[0].getNode() );
        stage.addChild( cane[1].getNode() );
        membersDialog.create();

        initGL();
        Gdx.input.setInputProcessor( new GestureDetector( this ) );
    }

    @Override
    public void hide() {
        Logger.v( TAG, "hide" );
    }

    @Override
    public void pause() {
        Logger.v( TAG, "pause" );
    }

    @Override
    public void resume() {
        Logger.v( TAG, "create" );
    }

    @Override
    public void dispose() {
        Logger.v( TAG, "dispose" );
        membersDialog.dispose();
        batch.dispose();
        Assets.clear();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Logger.v( TAG, "touchDown gesture" );
        flinging = false;
//        scale = camera.zoom;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Logger.v( TAG, "tap gesture" );
        if(Constants.SCREENSHOTS_ENABLED) screenshotRequestedQueue.add( UIDGenerator.getNewUID() + "_" + Math.random() * Integer.MAX_VALUE + ".png" );
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        Logger.v( TAG, "longPress gesture" );
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        Logger.v( TAG, "fling gesture" );
        flinging = true;
//        this.velocityX = camera.zoom * velocityX * 0.5f;
//        this.velocityY = camera.zoom * velocityY * 0.5f;
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Logger.v( TAG, "pan gesture" );
//        camera.position.add(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
        light.position.add( deltaX, deltaY, 0 );
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        Logger.v( TAG, "zoom gesture" );
        float z = this.scale * ( initialDistance / distance );
//        if (z > WorldConstants.MAX_ZOOM_LEVEL) z = WorldConstants.MAX_ZOOM_LEVEL;
//        if (z < WorldConstants.MIN_ZOOM_LEVEL) z = WorldConstants.MIN_ZOOM_LEVEL;
//        camera.zoom = z;
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        Logger.v( TAG, "pinch gesture" );
        return false;
    }
}