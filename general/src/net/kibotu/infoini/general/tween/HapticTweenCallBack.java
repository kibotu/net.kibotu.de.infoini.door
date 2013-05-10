package net.kibotu.infoini.general.tween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import net.kibotu.infoini.general.IniApp;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class HapticTweenCallBack implements TweenCallback {

    @Override
    public void onEvent(int type, BaseTween<?> source) {
        IniApp.actionResolver.playHaptic( 47 );
    }
}
