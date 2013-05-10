package net.kibotu.infoini.general.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.sun.istack.internal.NotNull;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class GLESPerspectiveCamera extends PerspectiveCamera {

    private Color background;
    private ShaderProgram activeShader;

    public GLESPerspectiveCamera(float fieldOfView, float viewportWidth, float viewportHeight) {
        super( fieldOfView, viewportWidth, viewportHeight );
        background = Color.BLACK;
    }

    public void apply() {
        apply( activeShader );
    }

    public void apply(@NotNull ShaderProgram program) {
        program.setUniformMatrix( "u_ProjectionWorldView", combined );
//        program.setUniformMatrix("u_ProjectionView", projection);
//        program.setUniformMatrix("u_WorldView", view);
    }

    public void clearScreen(@NotNull GL20 gl20) {
        gl20.glClearColor( background.r, background.g, background.b, background.a );
        gl20.glClear( GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT );
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(@NotNull Color background) {
        this.background = background;
    }

    public ShaderProgram getActiveShader() {
        return activeShader;
    }

    public void setActiveShader(ShaderProgram activeShader) {
        this.activeShader = activeShader;
    }
}
