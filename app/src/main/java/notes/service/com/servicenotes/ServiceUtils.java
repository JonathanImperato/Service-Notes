package notes.service.com.servicenotes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;

public class ServiceUtils {
    private static int cTheme;

    private static final String TAG = "ServiceUtils";

    public final static int TEAL = 0;

    public final static int VIOLA = 1;

    public final static int BLUE = 2;

    public final static int VERDE = 3;

    public final static int MARRONE = 4;

    public static void setSavedTheme(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String theme = sharedPrefs.getString("selected_theme", "0");
        try {
            int themeNumber = Integer.parseInt(theme);
            switch (themeNumber) {
                case 0:
                    context.setTheme(R.style.MyAppTheme);
                    break;
                case 1:
                    context.setTheme(R.style.MyAppTheme2);
                    break;
                case 2:
                    context.setTheme(R.style.MyAppTheme3);
                    break;
                case 3:
                    context.setTheme(R.style.MyAppTheme4);
                    break;
                case 4:
                    context.setTheme(R.style.MyAppTheme5);
                    break;
                default:
                    context.setTheme(R.style.MyAppTheme);
                    break;
            }
        } catch (NumberFormatException e) {
            Log.e(TAG, "NumberFormatExcpetion in setSavedTheme() with " + theme, e);
            context.setTheme(R.style.MyAppTheme);
        }

    }

    /* ######### APP UTILS ######### */
    public static void setSavedLanguage(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String[] language = sharedPrefs.getString("selected_language", "not_changed").split("-r");

        if (!language[0].equals("default") && !language[0].equals("not_changed")) {
            Locale locale;
            if (language.length == 1)
                locale = new Locale(language[0]);
            else
                locale = new Locale(language[0], language[1]);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }

        // this will be removed when languages become more accurate
        // languages that I'm sure are accurate are not affected
        if (language[0].equals("not_changed") && !Locale.getDefault().getCountry().equals("IT")) {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }

    public static void setSavedAnimations(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String animations = sharedPrefs.getString("animations", "0");
        try {
            int animationsNumber = Integer.parseInt(animations);
            switch (animationsNumber) {
                case 0:
                    ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    break;
                case 1:
                    ((Activity) context).overridePendingTransition(0, 1);
                    break;
                default:
                    ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    break;
            }
        } catch (NumberFormatException e) {
            Log.e(TAG, "NumberFormatExcpetion in setSavedAnimations() with " + animations, e);
        }

    }

}