package net.kibotu.infoini.general.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.sun.istack.internal.NotNull;

/**
 * Basically Loads <code>ShaderPrograms</code>.
 *
 * @author Jan Rabe <a href="mailto:jan.rabe@wooga.net">jan.rabe@wooga.net</a>
 */
public class ShaderLoader {

    /**
     * Static class.
     */
    private ShaderLoader() {
    }

    /**
     * Loads and creates a shader program from assets folder.
     *
     * @param vertex   - Path to vertex shader folder.
     * @param fragment - Path to fragment shader folder.
     * @return ShaderProgram - Returns loaded and compiled shader.
     */
    public static ShaderProgram loadAndCreateShader(@NotNull final String vertex, @NotNull final String fragment) {
        final ShaderProgram meshShader = new ShaderProgram( loadFile( vertex ), loadFile( fragment ) );
        if ( meshShader.isCompiled() == false ) {
            throw new IllegalStateException( meshShader.getLog() );
        }
        return meshShader;
    }

    /**
     * Loads a file from assets folder.
     *
     * @param filepath - Path to the file.
     * @return File - Returns the file as string.
     */
    private static String loadFile(@NotNull final String filepath) {
        String file = null;
        try {
            file = Gdx.files.internal( filepath ).readString();
        } catch ( final Exception e ) {
            e.printStackTrace();
        }
        return file;
    }
}
