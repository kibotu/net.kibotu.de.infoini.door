package net.kibotu.infoini.general.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.utils.Array;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class StillModelLoader extends SynchronousAssetLoader<StillModel, AssetLoaderParameters<StillModel>> {


    public StillModelLoader(FileHandleResolver resolver) {
        super( resolver );
    }

    @Override
    public StillModel load(AssetManager assetManager, String fileName, AssetLoaderParameters<StillModel> parameter) {
        return ModelLoaderRegistry.loadStillModel( resolve( fileName ) );
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, AssetLoaderParameters<StillModel> parameter) {
        return null;
    }
}
