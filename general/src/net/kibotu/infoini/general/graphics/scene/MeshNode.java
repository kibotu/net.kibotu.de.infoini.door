package net.kibotu.infoini.general.graphics.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.sun.istack.internal.NotNull;
import net.kibotu.infoini.general.Constants;
import net.kibotu.infoini.general.assets.Assets;
import net.kibotu.infoini.general.graphics.PhongMaterial;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class MeshNode extends RootNode {

    public static final String TAG = MeshNode.class.getSimpleName();
    public static final String MODEL_VIEW = "u_ModelView";
    public static final String NORMAL = "u_NormalMatrix";
    Material mat;
    private PhongMaterial material;
    private StillModel model;

    public MeshNode(@NotNull PhongMaterial material, @NotNull StillModel model) {
        super();
        this.material = material;
        this.model = model;

        Texture texture = Assets.manager.get( Constants.WHITE, Texture.class );
        texture.setFilter( Texture.TextureFilter.Linear, Texture.TextureFilter.Linear );
        mat = new Material( "Material1", new TextureAttribute( texture, 0, "u_texture01" ) );
        model.setMaterial( mat );
    }

    @Override
    public void render(@NotNull ShaderProgram program) {
        super.render( program );
        if ( !isVisible() ) return;
        program.setUniformMatrix( MODEL_VIEW, getCombinedTransoformation() );
//        program.setUniformMatrix(NORMAL, getCombinedTransoformation().toNormalMatrix());
        material.apply( program );
        model.render( program );
    }
}

