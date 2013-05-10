package net.kibotu.infoini.general.infoini;

import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import net.kibotu.infoini.general.assets.Assets;
import net.kibotu.infoini.general.graphics.PhongMaterial;
import net.kibotu.infoini.general.graphics.scene.MeshNode;

import static net.kibotu.infoini.general.Constants.MODEL_COFFEE_CUP;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class Cup {

    public int index;
    public boolean empty;
    private MeshNode node;

    public Cup() {
        node = new MeshNode( PhongMaterial.createDefaultMaterial(), Assets.manager.get( MODEL_COFFEE_CUP, StillModel.class ) );
    }

    public MeshNode getNode() {
        return node;
    }
}
