package notes.service.com.servicenotes;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Imperato on 19/01/2016.
 */
public class Info extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ServiceUtils.setSavedAnimations(this);
        super.onCreate(savedInstanceState);
        ServiceUtils.setSavedTheme(Info.this);
        ServiceUtils.setSavedLanguage(this);
        setContentView(R.layout.activity_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolbar.setTitle("Info");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void onClick(View v) {
        Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Heromine/Service-Notes"));
        startActivity(i2);
    }
}
