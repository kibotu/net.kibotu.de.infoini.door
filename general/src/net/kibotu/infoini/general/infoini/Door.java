package net.kibotu.infoini.general.infoini;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.sun.istack.internal.NotNull;
import net.kibotu.infoini.general.Constants;
import net.kibotu.infoini.general.assets.Assets;

import static net.kibotu.infoini.general.Constants.DIMENSION;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class Door {

    public final Matrix4 modelview;
    private final Texture DOOR_OPEN;
    private final Texture DOOR_CLOSED;
    private final Texture DOOR_OFFLINE;
    private Sprite sprite;

    public Door() {
        modelview = new Matrix4();
        DOOR_OPEN = Assets.manager.get( Constants.DOOR_OPEN, Texture.class );
        DOOR_CLOSED = Assets.manager.get( Constants.DOOR_CLOSED, Texture.class );
        DOOR_OFFLINE = Assets.manager.get( Constants.DOOR_OFFLINE, Texture.class );

        DOOR_OPEN.setWrap( Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge );
        DOOR_CLOSED.setWrap( Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge );
        DOOR_OFFLINE.setWrap( Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge );

        sprite = new Sprite( DOOR_OPEN, 0, 0, DOOR_OPEN.getWidth(), DOOR_OPEN.getHeight() );
        sprite.setSize( DIMENSION, DIMENSION );
        sprite.setPosition( -DIMENSION / 2, -20 );
    }

    public void setState(@NotNull String state) {
        switch ( state ) {
            case Constants.DOOR_OPEN:
                sprite.setTexture( DOOR_OPEN );
                break;
            case Constants.DOOR_CLOSED:
                sprite.setTexture( DOOR_CLOSED );
                break;
            case Constants.DOOR_OFFLINE:
                sprite.setTexture( DOOR_OFFLINE );
                break;
        }
    }

    public void render(@NotNull SpriteBatch batch) {
        batch.setTransformMatrix( modelview );
        batch.begin();
        batch.disableBlending();
        sprite.draw( batch );
        batch.enableBlending();
        batch.flush();
        batch.end();
    }
}
