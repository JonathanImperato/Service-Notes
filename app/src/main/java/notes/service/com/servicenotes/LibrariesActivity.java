package notes.service.com.servicenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.LibsConfiguration;
import com.mikepenz.aboutlibraries.entity.Library;
import com.mikepenz.aboutlibraries.ui.LibsSupportFragment;

/**
 * Created by Jonathan Imperato on 16/12/2015.
 */
public class LibrariesActivity extends AppCompatActivity {

    private static final String TAG = "LibrariesActivity";

    public static AppCompatActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ServiceUtils.setSavedAnimations(this);
        super.onCreate(savedInstanceState);
        ServiceUtils.setSavedLanguage(this);
        ServiceUtils.setSavedTheme(this);
        setContentView(R.layout.activity_libraries);

        // Set up the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_libraries); // Attaching the layout to the toolbar object
        toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_back);
        toolbar.setTitle(R.string.used_libraries_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceUtils.setSavedAnimations(LibrariesActivity.this);
                Intent i = new Intent(LibrariesActivity.this, SettingsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ServiceUtils.setSavedAnimations(LibrariesActivity.this);
                startActivity(i);
            }
        });


        LibsSupportFragment fragment = new LibsBuilder()
                .withLibraries("gitty_reporter", "appintro", "aboutlibraries") // definitions in strings.xml
                .withExcludedLibraries("androideasingfunctions","Jackson","androideasingfunctions")
                .withAutoDetect(true)
                .withLicenseShown(true)
                .withVersionShown(false)
                .withListener(new LibsConfiguration.LibsListener() {
                    @Override
                    public void onIconClicked(View view) {

                    }

                    @Override
                    public boolean onIconLongClicked(View view) {
                        return false;
                    }


                    @Override
                    public boolean onLibraryAuthorClicked(View v, Library library) {
                        return true;
                    }

                    @Override
                    public boolean onLibraryBottomClicked(View v, Library library) {
                        return true;
                    }

                    @Override
                    public boolean onLibraryAuthorLongClicked(View v, Library library) {
                        return true;
                    }

                    @Override
                    public boolean onLibraryBottomLongClicked(View v, Library library) {
                        return true;
                    }


                    @Override
                    public boolean onLibraryContentClicked(View v, Library library) {
                        return false;
                    }

                    @Override
                    public boolean onExtraClicked(View v, Libs.SpecialButton specialButton) {
                        return false;
                    }

                    @Override
                    public boolean onLibraryContentLongClicked(View v, Library library) {
                        return true;
                    }


                })
                .supportFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.about_libraries_container, fragment)
                .commit();

    }
}