package net.kibotu.infoini.general.graphics.scene;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.sun.istack.internal.NotNull;
import net.kibotu.infoini.general.utils.UIDGenerator;

import java.util.ArrayList;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class RootNode implements TweenAccessor<RootNode> {

    public static final String TAG = RootNode.class.getSimpleName();

    public static final int TWEEN_XYZ = UIDGenerator.getNewUID();
    public RootNode parent;
    public ArrayList<RootNode> children;
    private Matrix4 scaleMat = new Matrix4();
    private Matrix4 transMat = new Matrix4();
    private Matrix4 rotMat = new Matrix4();
    private Vector3 position;
    private Quaternion rotation;
    private Vector3 scaling;
    private Matrix4 combined;
    private boolean isDirtyCombined;
    private ShaderProgram activeShader;
    private boolean isVisible;

    public RootNode() {
        this.position = new Vector3();
        this.rotation = new Quaternion();
        this.scaling = new Vector3( 1, 1, 1 );
        this.combined = new Matrix4();
        isDirtyCombined = true;
        isVisible = true;
    }

    public void addChild(@NotNull RootNode node) {
        if ( children == null ) children = new ArrayList<>( 10 );
        children.add( node );
        node.setParent( this );
    }

    public ArrayList<RootNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<RootNode> children) {
        this.children = children;
    }

    public Matrix4 getCombinedTransoformation() {

        if ( !isDirtyCombined() ) return combined;

        // M = S * R * T
        scaleMat.setToScaling( scaling );
        transMat.setTranslation( position );
//        combined.setToTranslationAndScaling(position.x, position.y, position.z, scaling.x, scaling.y, scaling.z);
        rotMat.set( rotation.nor() );
        combined.set( rotMat.mul( scaleMat.mul( transMat ) ) );

        // appending parent
        if ( hasParent() ) {
            Matrix4 comb = combined.mul( parent.getCombinedTransoformation() );
            combined.set( comb );
        }

        isDirtyCombined = false;

        return combined;
    }

    public Vector3 getPosition() {
        setDirtyCombined( true );
        return position;
    }

    public void setPosition(Vector3 position) {
        setDirtyCombined( true );
        this.position = position;
    }

    public Quaternion getRotation() {
        setDirtyCombined( true );
        return rotation;
    }

    public void setRotation(Quaternion rotation) {
        setDirtyCombined( true );
        this.rotation = rotation;
    }

    public Vector3 getScaling() {
        setDirtyCombined( true );
        return scaling;
    }

    public void setScaling(Vector3 scalling) {
        setDirtyCombined( true );
        this.scaling = scalling;
    }

    public boolean isDirtyCombined() {
        return isDirtyCombined;
    }

    public void setDirtyCombined(boolean dirtyCombined) {
        isDirtyCombined = dirtyCombined;
        if ( hasChildren() ) for ( int i = 0; i < children.size(); ++i ) {
            children.get( i ).setDirtyCombined( true );
        }
    }

    private boolean hasParent() {
        return parent != null;
    }

    public RootNode getParent() {
        setDirtyCombined( true );
        return parent;
    }

    public void setParent(@NotNull RootNode parent) {
        setDirtyCombined( true );
        this.parent = parent;
    }

    public void render(ShaderProgram program) {
        if ( !hasChildren() ) return;
        for ( int i = 0; i < children.size(); ++i ) {
            children.get( i ).render( program );
        }
    }

    private boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    public void setRotation(float x, float y, float z, float w) {
        setDirtyCombined( true );
        rotation.setFromAxis( x, y, z, w );
    }

    public void setPosition(float x, float y, float z) {
        setDirtyCombined( true );
        position.set( x, y, z );
    }

    public ShaderProgram getActiveShader() {
        return activeShader;
    }

    public void setActiveShader(ShaderProgram activeShader) {
        this.activeShader = activeShader;
    }

    public void render() {
        render( activeShader );
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public int getValues(RootNode target, int tweenType, float[] returnValues) {
        if ( tweenType == TWEEN_XYZ ) {
            returnValues[0] = position.x;
            returnValues[1] = position.y;
            returnValues[2] = position.z;
            return 3;
        }
        return 0;
    }

    @Override
    public void setValues(RootNode target, int tweenType, float[] newValues) {
        if ( tweenType == TWEEN_XYZ ) {
            setPosition( newValues[0], newValues[1], newValues[2] );
        }
    }
}

