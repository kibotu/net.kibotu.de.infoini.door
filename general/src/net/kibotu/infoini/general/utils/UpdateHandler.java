package net.kibotu.infoini.general.utils;

import java.util.Observable;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public class UpdateHandler extends Observable {

    public static final String REQUEST_UPDATE = "REQUEST_UPDATE";

    public void requestUpdate() {
        setChanged();
        notifyObservers( REQUEST_UPDATE );
    }
}
