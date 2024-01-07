package net.sourceforge.opencamera;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.HashSet;

/** Must be used as the parent class for all sub-screens.
 */
public class PreferenceSubScreen extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "PreferenceSubScreen";
    private boolean enable_settings_on_back_callback = false;

    // see note for dialogs in MyPreferenceFragment
    protected final HashSet<AlertDialog> dialogs = new HashSet<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if( MyDebug.LOG )
            Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        // need to disable the SettingsOnBackPressedCallback (as don't want MainActivity.settingsClosing() to be called when exiting a sub-screen)
        enable_settings_on_back_callback = ((MainActivity)getActivity()).isSettingsOnBackPressedCallbackEnabled();
        if( MyDebug.LOG )
            Log.d(TAG, "enable_settings_on_back_callback: " + enable_settings_on_back_callback);
        ((MainActivity)getActivity()).enableSettingsOnBackPressedCallback(false);

        if( MyDebug.LOG )
            Log.d(TAG, "onCreate done");
    }

    @Override
    public void onDestroy() {
        if( MyDebug.LOG )
            Log.d(TAG, "onDestroy");
        super.onDestroy();

        // if the screen we're returning to is the top screen, need to reenable the SettingsOnBackPressedCallback
        if( MyDebug.LOG )
            Log.d(TAG, "enable_settings_on_back_callback: " + enable_settings_on_back_callback);
        ((MainActivity)getActivity()).enableSettingsOnBackPressedCallback(enable_settings_on_back_callback);

        MyPreferenceFragment.dismissDialogs(getFragmentManager(), dialogs);
    }

    public void onResume() {
        super.onResume();

        MyPreferenceFragment.setBackground(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    /* See comment for MyPreferenceFragment.onSharedPreferenceChanged().
     */
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if( MyDebug.LOG )
            Log.d(TAG, "onSharedPreferenceChanged: " + key);

        if( key == null ) {
            // On Android 11+, when targetting Android 11+, this method is called with key==null
            // if preferences are cleared. Unclear if this happens here in practice, but return
            // just in case.
            return;
        }

        Preference pref = findPreference(key);
        MyPreferenceFragment.handleOnSharedPreferenceChanged(prefs, key, pref);
    }
}
