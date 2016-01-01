package notes.service.com.servicenotes;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.OpenFileActivityBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import notes.service.com.servicenotes.data.source.sqlite.NotesDatabaseHelper;

/**
 * Created by Imperato on 15/12/2015.
 */
public class NotesBackupActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RESOLVE_CONNECTION_REQUEST_CODE = 1212121;
    private static final int REQUEST_CODE_CREATOR = 4343434;
    private static final int REQUEST_CODE_OPENER = 5656565;
    private GoogleApiClient mGoogleApiClient;
    private NotesDatabaseHelper mDbHelper;
    private Button backupButton;
    private Button restoreButton;
    private View.OnClickListener backupButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            saveNotes();
        }
    };
    private View.OnClickListener restoreButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            restoreNotes();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDbHelper = new NotesDatabaseHelper(this);
        //mDbHelper.openDataBase();

        setContentView(R.layout.activity_display_file);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

        backupButton = (Button) findViewById(R.id.button4);
        backupButton.setOnClickListener(backupButtonListener);
        backupButton.setEnabled(false);

        restoreButton = (Button) findViewById(R.id.button5);
        restoreButton.setOnClickListener(restoreButtonListener);
        restoreButton.setEnabled(false);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getApplicationContext(),"Connected  To Google Drive",Toast.LENGTH_SHORT).show();
        backupButton.setEnabled(true);
        restoreButton.setEnabled(true);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    private void saveNotes() {
        ResultCallback<DriveApi.DriveContentsResult> newFileCallback = new ResultCallback<DriveApi.DriveContentsResult>() {
            @Override
            public void onResult(DriveApi.DriveContentsResult result) {
                MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                        .setMimeType("text/plain").build();
                DriveContents contents = result.getDriveContents();
                try {
                    ParcelFileDescriptor parcelFileDescriptor = contents.getParcelFileDescriptor();
                    FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor
                            .getFileDescriptor());
                    OutputStream os = contents.getOutputStream();
                  //  os.write(mDbHelper.createJsonFromNotes().getBytes("UTF-8"));
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                IntentSender intentSender = Drive.DriveApi
                        .newCreateFileActivityBuilder()
                        .setInitialMetadata(metadataChangeSet)
                        .setInitialDriveContents(contents)
                        .build(mGoogleApiClient);
                try {
                    startIntentSenderForResult(intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                    Log.w("Service Notes", "Unable to send intent", e);
                }
            }
        };
        Drive.DriveApi.newDriveContents(mGoogleApiClient).setResultCallback(newFileCallback);
    }


    private void restoreNotes() {
        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
                .setMimeType(new String[]{"text/plain", "text/html"})
                .build(mGoogleApiClient);
        try {
            startIntentSenderForResult(
                    intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            Log.w("Service Notes", "Unable to send intent", e);
        }

    }


    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(),"Connessione sospesa",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RESOLVE_CONNECTION_REQUEST_CODE);
            } catch (IntentSender.SendIntentException e) {
            }
            // Unable to resolve, message user appropriately
        } else {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case RESOLVE_CONNECTION_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    mGoogleApiClient.connect();
                }
                break;
            case REQUEST_CODE_CREATOR:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getApplicationContext(),"All Notes Backed Up",Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_OPENER:
                if (resultCode == RESULT_OK) {
                    DriveId mCurrentDriveId = data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);

                    DriveFile file = Drive.DriveApi.getFile(mGoogleApiClient, mCurrentDriveId);

                    final PendingResult<DriveResource.MetadataResult> metadataResult = file.getMetadata(mGoogleApiClient);

                    final PendingResult<DriveApi.DriveContentsResult> contentsResult = file.open(mGoogleApiClient,
                            DriveFile.MODE_READ_ONLY | DriveFile.MODE_WRITE_ONLY, null);

                    file.open(mGoogleApiClient, DriveFile.MODE_READ_ONLY, null)
                            .setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
                                @Override
                                public void onResult(DriveApi.DriveContentsResult result) {
                                    if (!result.getStatus().isSuccess()) {
                                        Toast.makeText(getApplicationContext(),"Error While Restoring Notes",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    // DriveContents object contains pointers
                                    // to the actual byte stream
                                    DriveContents contents = result.getDriveContents();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(contents.getInputStream()));
                                    StringBuilder builder = new StringBuilder();
                                    String line;
                                    try {
                                        while ((line = reader.readLine()) != null) {
                                            builder.append(line);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    String contentsAsString = builder.toString();

                                   // mDbHelper.createNotesFromJson(contentsAsString);

                                    Toast.makeText(getApplicationContext(), "Notes Restored", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

}