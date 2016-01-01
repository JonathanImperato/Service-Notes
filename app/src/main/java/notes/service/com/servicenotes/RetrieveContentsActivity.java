package notes.service.com.servicenotes;

/**
 * Created by Imperato on 08/12/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveApi.DriveIdResult;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*downloads a text file's contents, reads it and displays
the contents in a new activity*/
public class RetrieveContentsActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    /*get this id from your google drive on the web*/
    private static final String EXISTING_FILE_ID = "0ByM3Pdq-D1SPX3A1VHNKTjJBOXc";
    private static final int REQUEST_CODE = 102;
    private GoogleApiClient googleApiClient;
    private static final String TAG = "retrieve_contents";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 /*build the api client*/
        buildGoogleApiClient();
    }
 /*connect client to Google Play Services*/
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "In onStart() - connecting...");
        googleApiClient.connect();
    }
    /*close connection to Google Play Services*/
    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null) {
            Log.i(TAG, "In onStop() - disConnecting...");
            googleApiClient.disconnect();
        }
    }
    /*handles onConnectionFailed callbacks*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Log.i(TAG, "In onActivityResult() - connecting...");
            googleApiClient.connect();
        }
    }
    /*handles connection callbacks*/
    @Override
    public void onConnected(Bundle bundle) {
        Drive.DriveApi.fetchDriveId(googleApiClient, EXISTING_FILE_ID)
                .setResultCallback(idCallback);
    }
    /*handles suspended connection callbacks*/
    @Override
    public void onConnectionSuspended(int i) {
        Drive.DriveApi.fetchDriveId(googleApiClient, EXISTING_FILE_ID)
                .setResultCallback(idCallback);
    }
    /*callback on getting the drive id, contained in result*/
    final private ResultCallback<DriveIdResult> idCallback = new
            ResultCallback<DriveIdResult>() {
                @Override
                public void onResult(DriveIdResult result) {DriveFile file = Drive.DriveApi.getFile(googleApiClient, result.getDriveId());
 /*use a pending result to get the file contents */
                    PendingResult<DriveContentsResult> pendingResult = file.open(googleApiClient,
                            DriveFile.MODE_READ_ONLY, null);
 /*the callback receives the contents in the result*/
                    pendingResult.setResultCallback(new ResultCallback<DriveContentsResult>() {
                        public String fileAsString;
                        @Override
                        public void onResult(DriveContentsResult result) {
                            DriveContents fileContents = result.getDriveContents();
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(fileContents.getInputStream()));
                            StringBuilder builder = new StringBuilder();
                            String oneLine;
                            Log.i(TAG, "reading input stream and building string...");
                            try {
                                while ((oneLine = reader.readLine()) != null) {
                                    builder.append(oneLine);
                                }
                                fileAsString = builder.toString();
                            } catch (IOException e) {
                                Log.e(TAG, "IOException while reading from the stream", e);
                            }
                            fileContents.discard(googleApiClient);
                            Intent intent = new Intent(RetrieveContentsActivity.this, DisplayFileActivity.class);
                            intent.putExtra("text", fileAsString);
                            startActivity(intent);
                        }
                    });
                }
            };
    /*callback when there there's an error connecting the client to the service.*/
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed");
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }
        try {Log.i(TAG, "trying to resolve the Connection failed error...");
            result.startResolutionForResult(this, REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }
    /*build the google api client*/
    private void buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }
}