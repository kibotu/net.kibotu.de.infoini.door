package net.kibotu.infoini.general.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.math.Vector3;
import net.kibotu.infoini.general.graphics.scene.RootNode;
import net.kibotu.infoini.general.utils.UIDGenerator;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class NodeAcessor implements TweenAccessor<RootNode> {

    public static final int XYZ = UIDGenerator.getNewUID();

    @Override
    public int getValues(RootNode node, int tweenType, float[] floats) {
        if ( tweenType == XYZ ) {
            Vector3 pos = node.getPosition();
            floats[0] = pos.x;
            floats[1] = pos.y;
            floats[2] = pos.z;
            return 3;
        }
        return 0;
    }

    @Override
    public void setValues(RootNode node, int tweenType, float[] floats) {
        if ( tweenType == XYZ ) {
            node.setPosition( floats[0], floats[1], floats[2] );
        }
    }
}
