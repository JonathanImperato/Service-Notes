package notes.service.com.servicenotes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Imperato on 01/12/2015.
 */
public class Backup extends AppCompatActivity implements View.OnClickListener {
    private static final String SAMPLE_DB_NAME = "notes.db";
    private static final String SAMPLE_TABLE_NAME = "notes_schema-v%s.sql";
    public static final int elimina = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ServiceUtils.setSavedAnimations(this);
        super.onCreate(savedInstanceState);
        ServiceUtils.setSavedLanguage(this);
        ServiceUtils.setSavedTheme(this);
        setContentView(R.layout.activity_backup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.action_settings);
        toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ServiceUtils.setSavedAnimations(Backup.this);
            }
        });
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3:
                showDialog(elimina);
                deleteDB();
                break;
            case R.id.button2:
                exportDB();
                break;
            case R.id.button:
                showDialog(elimina);
                importDB();
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case elimina:
                return new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.backuppopup))
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent i = new Intent(Backup.this, MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        ServiceUtils.setSavedAnimations(Backup.this);
                                        startActivity(i);
                                        ServiceUtils.setSavedAnimations(Backup.this);
                                    }
                                }
                        )
                        .create();
        }
        return super.onCreateDialog(id);
    }

    /**
     * DATABASE SETTINGS
     */
    private void deleteDB() {
        boolean result = this.deleteDatabase(SAMPLE_DB_NAME);
        if (result == true) {
            Snackbar.make(this.findViewById(android.R.id.content), "Database Eliminato con successo, Riavviare l'app", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        }
    }

    private void exportDB() {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "ServiceNotes");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            // Do something on success
        } else {
            // Do something else on failure
        }
        String currentDBPath = "/data/" + "notes.service.com.servicenotes" + "/databases/" + SAMPLE_DB_NAME;
        String backupDBPath = "/ServiceNotes/" + SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Snackbar.make(this.findViewById(android.R.id.content), "Esportato con Successo", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/" + "notes.service.com.servicenotes"
                        + "/databases/" + SAMPLE_DB_NAME;
                String backupDBPath = "/ServiceNotes/" + SAMPLE_DB_NAME;
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Snackbar.make(this.findViewById(android.R.id.content), "Importato con Successo, Riavviare l'app", Snackbar.LENGTH_LONG).setAction("Action", null).show();
               /* Toast.makeText(getBaseContext(), backupDB.toString(),


                        Toast.LENGTH_LONG).show();*/
            }
        } catch (Exception e) {
            Snackbar.make(this.findViewById(android.R.id.content), "File non presente, impossibile importare", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        }
    }
}
