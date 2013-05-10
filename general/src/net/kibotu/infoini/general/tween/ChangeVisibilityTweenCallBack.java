package net.kibotu.infoini.general.tween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import net.kibotu.infoini.general.graphics.scene.RootNode;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class ChangeVisibilityTweenCallBack implements TweenCallback {

    private static final String TAG = ChangeVisibilityTweenCallBack.class.getSimpleName();
    private boolean isVisible;

    public ChangeVisibilityTweenCallBack(boolean visible) {
        isVisible = visible;
    }

    @Override
    public void onEvent(int type, BaseTween<?> source) {
        if ( !( source.getUserData() instanceof RootNode ) ) return;
        RootNode node = ( ( RootNode ) source.getUserData() );
        node.setVisible( isVisible );
    }
}
