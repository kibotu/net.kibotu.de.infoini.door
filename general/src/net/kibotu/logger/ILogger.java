package net.kibotu.logger;

/**
 * Logging interface for concrete device specific logger.
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public interface ILogger {

    /**
     * Debug Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    void debug(final String tag, final String message);

    /**
     * Debug Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    void verbose(final String tag, final String message);

    /**
     * Information Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    void information(final String tag, final String message);

    /**
     * Warning Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    void warning(final String tag, final String message);

    /**
     * Error Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    void error(final String tag, final String message);
}
