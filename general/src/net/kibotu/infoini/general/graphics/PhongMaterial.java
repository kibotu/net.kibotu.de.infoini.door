package net.kibotu.infoini.general.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.sun.istack.internal.NotNull;

/**
 * Author: Jan Rabe
 * E-Mail: cloudgazer3d@gmail.com
 * Date: 14/02/13
 * Time: 14:30
 */
public class PhongMaterial extends Material {

    public static final String PHONG_MATERIAL = "u_PhongMaterial";
    public float shininess;
    public Color Ambient = null;
    public Color Diffuse = null;
    public Color Specular = null;
    public Color Emissive = null;

    private static PhongMaterial defaultMaterial;

    /**
     * Constructs a new material.
     *
     * @param name The material's name.
     */
    public PhongMaterial(String name) {
        super( name );
    }

    public static PhongMaterial createDefaultMaterial() {
        if ( defaultMaterial != null ) return defaultMaterial;
        defaultMaterial = new PhongMaterial( "white" );
        defaultMaterial.Ambient = new Color( 0.4f, 0.4f, 0.4f, 1f );
        defaultMaterial.Diffuse = new Color( 0.5f, 0.5f, 0.5f, 1f );
        defaultMaterial.Specular = new Color( 0.5f, 0.5f, 0.5f, 1f );
        defaultMaterial.Emissive = new Color( 0f, 0f, 0f, 1f );
        defaultMaterial.shininess = 80;
        return defaultMaterial;
    }

    public void apply(@NotNull ShaderProgram program) {
        program.setUniformf( PHONG_MATERIAL + ".ambient", Ambient.r, Ambient.g, Ambient.b, Ambient.a );
        program.setUniformf( PHONG_MATERIAL + ".diffuse", Diffuse.r, Diffuse.g, Diffuse.b, Diffuse.a );
        program.setUniformf( PHONG_MATERIAL + ".specular", Specular.r, Specular.g, Specular.b, Specular.a );
        program.setUniformf( PHONG_MATERIAL + ".emissive", Emissive.r, Emissive.g, Emissive.b, Emissive.a );
        program.setUniformf( PHONG_MATERIAL + ".shininess", shininess );
    }
}
