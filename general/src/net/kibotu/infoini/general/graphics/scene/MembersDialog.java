package net.kibotu.infoini.general.graphics.scene;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class MembersDialog extends InputAdapter implements ApplicationListener {

    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private boolean isVisible;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor( stage );

        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap( 1, 1, Pixmap.Format.RGBA8888 );
        pixmap.setColor( Color.WHITE );
        pixmap.fill();
        skin.add( "white", new Texture( pixmap ) );

        // Store the default libgdx font under the name "default".
        skin.add( "default", new BitmapFont() );

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable( "white", Color.DARK_GRAY );
        textButtonStyle.down = skin.newDrawable( "white", Color.DARK_GRAY );
        textButtonStyle.checked = skin.newDrawable( "white", Color.BLUE );
        textButtonStyle.over = skin.newDrawable( "white", Color.LIGHT_GRAY );
        textButtonStyle.font = skin.getFont( "default" );
        skin.add( "default", textButtonStyle );

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent( true );
        stage.addActor( table );

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton button = new TextButton( "Click me!", skin );
        table.add( button );

        // Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
        // Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
        // ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
        // revert the checked state.
        button.addListener( new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println( "Clicked! Is checked: " + button.isChecked() );
                button.setText( "Good job!" );
            }
        } );

        // Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).
        table.add( new Image( skin.newDrawable( "white", Color.RED ) ) ).size( 64 );
    }

    @Override
    public void render() {
        if ( !isVisible ) return;
        stage.act( Math.min( Gdx.graphics.getDeltaTime(), 1 / 30f ) );
        stage.draw();
//        Table.drawDebug(stage);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport( width, height, false );
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public void show(boolean isVisible) {
        this.isVisible = isVisible;
    }
}