package net.kibotu.infoini.general.infoini;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.math.Vector3;
import net.kibotu.infoini.general.Constants;
import net.kibotu.infoini.general.IniApp;
import net.kibotu.infoini.general.graphics.scene.RootNode;
import net.kibotu.infoini.general.tween.ChangeVisibilityTweenCallBack;
import net.kibotu.logger.Logger;

import java.util.ArrayList;

import static net.kibotu.infoini.general.Constants.*;
import static net.kibotu.infoini.general.graphics.scene.RootNode.TWEEN_XYZ;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class Cane implements TweenCallback {

    public static final String TAG = Cane.class.getSimpleName();
    public boolean isAnimated = false;
    private RootNode cane;
    private ArrayList<Cup> cups;

    public Cane() {
        cane = new RootNode();
        create();
    }

    public RootNode getNode() {
        return cane;
    }

    private void create() {
        cups = new ArrayList<>( 10 );
        for ( int i = 0; i < 10; i++ ) {
            Cup cup = new Cup();
            cup.index = i;
            Tween.registerAccessor( RootNode.class, cup.getNode() );
            cup.getNode().setVisible( false );
            cups.add( cup );
            cane.addChild( cup.getNode() );
        }
    }

    public void setAmountCups(int amountCups) {
        if ( isAnimated ) return;
        isAnimated = true;

        if ( amountCups < 0 ) throw new IllegalArgumentException( "amountCups can't be below 0" + amountCups );
        if ( amountCups > cane.children.size() )
            throw new IllegalArgumentException( "amountCups > cane cups: " + amountCups + " > " + cane.children.size() );

        try {
            Timeline parallel = Timeline.createParallel();

            int i = 0;
            for ( float y = 0; y < ROWS * V_SPACE; y += V_SPACE ) {
                for ( float x = 0; x < COLS * H_SPACE; x += H_SPACE ) {
                    Cup cup = cups.get( i );
                    RootNode node = cup.getNode();
                    node.setVisible( true );
                    node.setPosition( x, y, 0 );

                    // new sequence for every node
                    Timeline sequence = Timeline.createSequence();

                    cup.empty = !( i + 1 <= amountCups );

                    // create random positions
                    for ( int j = 0; j < SPLINE_STEPS; ++j ) {
                        // add random way point
                        sequence.push( Tween.to( node, TWEEN_XYZ, TWEEN_STEPS_DURATION ).target( ( float ) ( Math.random() * x ), ( float ) ( Math.random() * y ), ( float ) ( Math.random() * ( -50 + 2 * j ) ) )
                                .ease( Constants.TWEEN_STEPS_EQUATION ) );
                    }

                    // final position
                    sequence.push( Tween.to( node, TWEEN_XYZ, TWEEN_FINAL_STEP_DURATION ).target( x, y, 0 )
                            .ease( Constants.TWEEN_FINAL_STEP_EQUATION ) );

                    if ( cup.empty )
                        sequence.push( flyOutPos( cup.getNode().getPosition(), Tween.to( node, TWEEN_XYZ, TWEEN_OUT_DURATION )
                                .setCallback( new ChangeVisibilityTweenCallBack( !cup.empty ) ).setUserData( node )
                                .ease( Constants.TWEEN_FLY_OUT_EQUATION )
                                .start( IniApp.tweenManager ) ) );

                    // append sequence to parallel timeline
                    parallel.push( sequence );
                    ++i;
                }
            }

            parallel.setCallback( this );
            parallel.start( IniApp.tweenManager );
        } catch ( Exception e ) {
            Logger.e( TAG, "for some weird reason this method gets called multiple times if shacked too frequently" );
        }
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

    @Override
    public void onEvent(int type, BaseTween<?> source) {
        isAnimated = false;
    }
}

