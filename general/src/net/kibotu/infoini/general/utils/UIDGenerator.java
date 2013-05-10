
package net.kibotu.infoini.general.utils;

/**
 * Unique Id Generator.
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class UIDGenerator {

    // static
    private UIDGenerator() {
    }

    public static final int START_UID = 0;
    public static final int INVALID_UID = START_UID - 1;

    @SuppressWarnings("unused")
    private static final String TAG = "StaticUIDGenerator";
    private static int nextUID = 0;

    public static int getNewUID() {
        if ( !isValid( nextUID ) ) {
            throw new IllegalStateException( "UID pool depleted" );
        }
        return nextUID++;
    }

    public static boolean isValid(final int uid) {
        return uid >= START_UID;
    }
}
