package notes.service.com.servicenotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.analytics.Tracker;


public class SettingsActivity extends PreferenceActivity {
    /**
     * Determines whether to always show the simplified settings UI, where
     * settings are presented in a single list. When false, settings are shown
     * as a master/detail two-pane view on tablets. When true, a single pane is
     * shown on tablets.
     */
    private static final boolean ALWAYS_SIMPLE_PREFS = false;
    // private static final String TAG = "ServiceUtils";
    // public static Activity activity;
    //private static Tracker mTracker;
    //public static Activity activity;
    private static final String TAG = "SettingsActivity";

    public static AppCompatActivity activity;
    private int weight;
    // analytics tracker
    private static Tracker mTracker;

    private static Preference frequencyPreference;

    private static boolean monitorNotificationModsPreference;
    private static boolean monitorNotificationNewsPreference;
    private static boolean monitorAnonymousStatisticsPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ServiceUtils.setSavedAnimations(this);
        super.onCreate(savedInstanceState);
        ServiceUtils.setSavedTheme(this);
        ServiceUtils.setSavedLanguage(this);
        // ServiceUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);
        Log.i(TAG, "Activity started (onCreate)");
        addPreferencesFromResource(R.xml.fragmented_preferences);
        //ServiceUtils.setSavedLanguage(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_settings);
        toolbar.setTitle(R.string.action_settings);
        toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                ServiceUtils.setSavedAnimations(SettingsActivity.this);
                ServiceUtils.setSavedAnimations(SettingsActivity.this);
                startActivity(i);
            }
        });


        Preference themePreference = findPreference("selected_theme");
        themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //ServiceUtils.changeToTheme(SettingsActivity.this, ServiceUtils.VERDE);
                Intent i = new Intent(SettingsActivity.this, SettingsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ServiceUtils.setSavedAnimations(SettingsActivity.this);
                startActivity(i);
                return true;
            }
        });

        Preference animationsPreference = findPreference("animations");
        animationsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //ServiceUtils.changeToTheme(SettingsActivity.this, ServiceUtils.VERDE);
                Intent i = new Intent(SettingsActivity.this, SettingsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ServiceUtils.setSavedAnimations(SettingsActivity.this);
                startActivity(i);
                return true;
            }
        });
        Preference languagePreference = findPreference("selected_language");
        languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent i = new Intent(SettingsActivity.this, SettingsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ServiceUtils.setSavedAnimations(SettingsActivity.this);
                startActivity(i);
                return true;
            }
        });
        Preference info = findPreference("other_info");
        info.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://heromine.github.io/")));
                return false;
            }
        });
        Preference helpTranslatingPreference = findPreference("help_translating");
        helpTranslatingPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://crowdin.com/project/service-notes")));
                return false;
            }
        });

        Preference license = findPreference("library");
        license.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(SettingsActivity.this, LibrariesActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ServiceUtils.setSavedAnimations(SettingsActivity.this);
                startActivity(i);
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        // No se edito ningúna nota ni creo alguna nota

        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ServiceUtils.setSavedAnimations(SettingsActivity.this);
        startActivity(i);
        finish();
    }
}