package cenario;

import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by clarkxp on 10-02-15.
 */
public class SystemUIController {

    public FragmentActivity actionBarActivity;

    public static final int FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES = 0x1;


    public static final int FLAG_FULLSCREEN = 0x2;


    public static final int FLAG_HIDE_NAVIGATION = FLAG_FULLSCREEN | 0x4;

    protected int mFlags;

    private int mShowFlags;

    /**
     * Flags for {@link View#setSystemUiVisibility(int)} to use when hiding the
     * system UI.
     */
    private int mHideFlags;

    /**
     * Flags to test against the first parameter in
     * {@link android.view.View.OnSystemUiVisibilityChangeListener#onSystemUiVisibilityChange(int)}
     * to determine the system UI visibility state.
     */
    private int mTestFlags;

    /**
     * Whether or not the system UI is currently visible. This is cached from
     * {@link android.view.View.OnSystemUiVisibilityChangeListener}.
     */
    private boolean mVisible = true;
    private View mAnchorView;

    private OnInmersiveModeListener onInmersiveModeListener;


    public SystemUIController(FragmentActivity actionBarActivity) {
        this.actionBarActivity = actionBarActivity;
        onInmersiveModeListener = (OnInmersiveModeListener) actionBarActivity;
        mShowFlags = View.SYSTEM_UI_FLAG_VISIBLE;
        mHideFlags = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        mTestFlags = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        if ((mFlags & FLAG_FULLSCREEN) != 0) {
            // If the client requested fullscreen, add flags relevant to hiding
            // the status bar. Note that some of these constants are new as of
            // API 16 (Jelly Bean). It is safe to use them, as they are inlined
            // at compile-time and do nothing on pre-Jelly Bean devices.
            mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if ((mFlags & FLAG_HIDE_NAVIGATION) != 0) {
            // If the client requested hiding navigation, add relevant flags.
            mShowFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            mHideFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            mTestFlags |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
    }



    public boolean isVisible() {
        return mVisible;
    }

    public void toggleHideyBar() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = actionBarActivity.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)


        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        actionBarActivity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
        boolean isImmersiveModeEnabled =
                ((newUiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == newUiOptions);
        if (isImmersiveModeEnabled) {
            // Log.i(TAG, "Turning immersive mode mode off. ");
            mVisible = false;
        } else {
            mVisible = true;
            // Log.i(TAG, "Turning immersive mode mode on.");
        }
        onInmersiveModeListener.inmersiveChanged(isVisible());

    }

    public interface OnInmersiveModeListener{
        public void inmersiveChanged(boolean visible);
    }
}