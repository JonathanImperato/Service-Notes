package notes.service.com.servicenotes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

public class Contributors extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ServiceUtils.setSavedAnimations(this);
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        ServiceUtils.setSavedTheme(this, window);
        ServiceUtils.setSavedLanguage(this);
        setContentView(R.layout.activity_contributors);
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolbar.setTitle("Contributors");
        //Get status bar color from the utils activity and set it
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
