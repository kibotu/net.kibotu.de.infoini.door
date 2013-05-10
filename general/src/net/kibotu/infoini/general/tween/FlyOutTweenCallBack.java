package net.kibotu.infoini.general.tween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.math.Vector3;
import net.kibotu.infoini.general.Constants;
import net.kibotu.infoini.general.IniApp;
import net.kibotu.infoini.general.graphics.scene.RootNode;
import net.kibotu.infoini.general.infoini.Cup;

import static net.kibotu.infoini.general.Constants.TWEEN_OUT_DURATION;
import static net.kibotu.infoini.general.graphics.scene.RootNode.TWEEN_XYZ;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class FlyOutTweenCallBack implements TweenCallback {

    private static final String TAG = FlyOutTweenCallBack.class.getSimpleName();

    public FlyOutTweenCallBack() {
    }

    @Override
    public void onEvent(int type, BaseTween<?> source) {
        if ( !( source.getUserData() instanceof Cup ) ) return;
        Cup cup = ( ( Cup ) source.getUserData() );
        RootNode node = cup.getNode();
        flyOutPos( cup.getNode().getPosition(), Tween.to( node, TWEEN_XYZ, TWEEN_OUT_DURATION )
                .setCallback( new ChangeVisibilityTweenCallBack( !cup.empty ) ).setUserData( node )
                .ease( Constants.TWEEN_FLY_OUT_EQUATION )
                .start( IniApp.tweenManager ) );

    }

    /**
     * direction to fly out
     * <p/>
     * ^
     * |
     * 1
     * ----
     * <- 0  |   |  2 ->
     * |   |
     */
    public Tween flyOutPos(Vector3 pos, Tween tween) {
        int d = ( int ) ( Math.random() * 3 );
        switch ( d ) {
            case 0:
                tween.target( ( float ) ( Math.random() * pos.x * 30 ), ( float ) ( Math.random() * pos.y ) + 30, ( float ) ( Math.random() * -3 ) );
                break;
            case 1:
                tween.target( ( ( float ) ( Math.random() * pos.x ) - 30 ), ( float ) ( Math.random() * 30 ), ( float ) ( Math.random() * -3 ) );
                break;
            case 2:
                tween.target( ( float ) ( Math.random() * pos.x ) + 30, ( float ) ( Math.random() * 30 ), ( float ) ( Math.random() * -3 ) );
                break;
        }
        return tween;
    }
}
