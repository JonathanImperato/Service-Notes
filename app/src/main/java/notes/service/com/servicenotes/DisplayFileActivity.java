package notes.service.com.servicenotes;

/**
 * Created by Imperato on 08/12/2015.
 */
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;


public class DisplayFileActivity extends ActionBarActivity {

    private static final String TAG = "asynctask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_file);

        String text = getIntent().getStringExtra("text");
        Log.i(TAG, "displaying text...");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(text);
    }
}