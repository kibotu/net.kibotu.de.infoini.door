package net.kibotu.infoini.general.utils;

import org.jetbrains.annotations.NotNull;

/**
 * TODO insert description
 *
 * @author <a href="mailto:jan.rabe@wooga.net">Jan Rabe</a>
 */
public interface ActionResolver extends IHapticFeedBack {

    public void showShortToast(final CharSequence toastMessage);

    public void showLongToast(final CharSequence toastMessage);

    public void showToast(final CharSequence toastMessage, final int toastDuration);

    public void showAlertBox(final String alertBoxTitle, final String alertBoxMessage, final String alertBoxButtonText);

    public void openUri(String uri);

    public void showMyList();

    public void exit();

    public void captureScreenShot(@NotNull final String path, @NotNull final String fileName);
}
