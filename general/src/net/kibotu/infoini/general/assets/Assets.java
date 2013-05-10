
package net.kibotu.infoini.general.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import net.kibotu.infoini.general.graphics.ShaderLoader;

import java.util.HashMap;

import static net.kibotu.infoini.general.Constants.*;

public final class Assets {

    public static AssetManager manager;
    public static HashMap<String, ShaderProgram> activeShader;
    private static Assets instance;

    private Assets() {
        activeShader = new HashMap<>( 2 );
        manager = new AssetManager();
        manager.setLoader( StillModel.class, new StillModelLoader( new InternalFileHandleResolver() ) );
    }

    public synchronized static Assets instance() {
        if ( instance == null ) {
            instance = new Assets();
        }
        return instance;
    }

    public static void loadModels() {
        manager.load( MODEL_COFFEE_CUP, StillModel.class );
    }

    /**
     * Loads all required shader, compiles them and sets active shader.
     */
    public static void loadShaderAssets() {
        activeShader.put( LIBGDX_DEFAULT_SHADER, ShaderLoader.loadAndCreateShader( Libgdx_DefaultShader_vsh, Libgdx_DefaultShader_fsh ) );
        activeShader.put( PHONG_SHADER, ShaderLoader.loadAndCreateShader( Phong_vsh, Phong_fsh ) );
    }

    public static void loadSprites() {
        manager.load( DOOR_OPEN, Texture.class );
        manager.load( DOOR_CLOSED, Texture.class );
        manager.load( DOOR_OFFLINE, Texture.class );
        manager.load( WHITE, Texture.class );
    }

    /**
     * Re-Allocates assets.
     */
    public static void create() {
        loadShaderAssets();
        loadModels();
        loadSprites();
        manager.finishLoading();
    }

    /**
     * Clears all assets related buffers.
     */
    public static void clear() {
        unload();
        Assets.manager.clear();
        ShaderProgram.clearAllShaderPrograms( Gdx.app );
        Texture.clearAllTextures( Gdx.app );
    }

    private static void unload() {
        manager.unload( DOOR_OPEN );
        manager.unload( DOOR_CLOSED );
        manager.unload( DOOR_OFFLINE );
        manager.unload( WHITE );
        manager.unload( MODEL_COFFEE_CUP );
    }
}
