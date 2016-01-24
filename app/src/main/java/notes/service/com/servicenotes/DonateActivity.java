package notes.service.com.servicenotes;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonateActivity extends Activity {
    public static Activity activity;
    public static Toolbar toolbar;
    IInAppBillingService mservice;
    ServiceConnection connection;
    String inappid = "gp1"; //replace this with your in-app product id
    String inappid2 = "gp2";
    String inappid3 = "gp3";
    String inappid5 = "gp5";

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mservice = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mservice = IInAppBillingService.Stub.asInterface(service);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ServiceUtils.setSavedAnimations(this);
        ServiceUtils.setSavedLanguage(this);
        Window window = getWindow();
        ServiceUtils.setSavedTheme(this, window);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        activity = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar); // Setting toolbar with setSupportActionBar()
        toolbar.setTitle(R.string.action_donate2);
        //Get status bar color from the utils activity and set it
        toolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceUtils.setSavedAnimations(DonateActivity.this);
                activity.finish();
            }
        });

        connection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mservice = null;

            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mservice = IInAppBillingService.Stub.asInterface(service);
            }
        };

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);


        Button purchaseBtn = (Button) findViewById(R.id.addBtn);
        purchaseBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList skuList = new ArrayList();
                skuList.add(inappid);
                Bundle querySkus = new Bundle();

                querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
                Bundle skuDetails;
                try {
                    skuDetails = mservice.getSkuDetails(3, getPackageName(),
                            "inapp", querySkus);

                    int response = skuDetails.getInt("RESPONSE_CODE");
                    if (response == 0) {
                        ArrayList<String> responseList = skuDetails
                                .getStringArrayList("DETAILS_LIST");

                        for (String thisResponse : responseList) {
                            JSONObject object = new JSONObject(thisResponse);
                            String sku = object.getString("productId");
                            String price = object.getString("price");
                            if (sku.equals(inappid)) {
                                System.out.println("price " + price);
                                Bundle buyIntentBundle = mservice
                                        .getBuyIntent(3, getPackageName(), sku,
                                                "inapp",
                                                "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                                PendingIntent pendingIntent = buyIntentBundle
                                        .getParcelable("BUY_INTENT");
                                startIntentSenderForResult(
                                        pendingIntent.getIntentSender(), 1001,
                                        new Intent(), Integer.valueOf(0),
                                        Integer.valueOf(0), Integer.valueOf(0));
                            }
                        }
                    }
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SendIntentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        Button purchaseBtn2 = (Button) findViewById(R.id.addBtn2);
        purchaseBtn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList skuList = new ArrayList();
                skuList.add(inappid2);
                Bundle querySkus = new Bundle();
                querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
                Bundle skuDetails;
                try {
                    skuDetails = mservice.getSkuDetails(3, getPackageName(),
                            "inapp", querySkus);

                    int response = skuDetails.getInt("RESPONSE_CODE");
                    if (response == 0) {

                        ArrayList<String> responseList = skuDetails
                                .getStringArrayList("DETAILS_LIST");

                        for (String thisResponse : responseList) {
                            JSONObject object = new JSONObject(thisResponse);
                            String sku = object.getString("productId");
                            String price = object.getString("price");
                            if (sku.equals(inappid2)) {
                                System.out.println("price " + price);
                                Bundle buyIntentBundle = mservice
                                        .getBuyIntent(3, getPackageName(), sku,
                                                "inapp",
                                                "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                                PendingIntent pendingIntent = buyIntentBundle
                                        .getParcelable("BUY_INTENT");
                                startIntentSenderForResult(
                                        pendingIntent.getIntentSender(), 1001,
                                        new Intent(), Integer.valueOf(0),
                                        Integer.valueOf(0), Integer.valueOf(0));
                            }
                        }
                    }
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SendIntentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        Button purchaseBtn3 = (Button) findViewById(R.id.addBtn3);
        purchaseBtn3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList skuList = new ArrayList();
                skuList.add(inappid3);
                Bundle querySkus = new Bundle();
                querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
                Bundle skuDetails;
                try {
                    skuDetails = mservice.getSkuDetails(3, getPackageName(),
                            "inapp", querySkus);

                    int response = skuDetails.getInt("RESPONSE_CODE");
                    if (response == 0) {

                        ArrayList<String> responseList = skuDetails
                                .getStringArrayList("DETAILS_LIST");

                        for (String thisResponse : responseList) {
                            JSONObject object = new JSONObject(thisResponse);
                            String sku = object.getString("productId");
                            String price = object.getString("price");
                            if (sku.equals(inappid3)) {
                                System.out.println("price " + price);
                                Bundle buyIntentBundle = mservice
                                        .getBuyIntent(3, getPackageName(), sku,
                                                "inapp",
                                                "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                                PendingIntent pendingIntent = buyIntentBundle
                                        .getParcelable("BUY_INTENT");
                                startIntentSenderForResult(
                                        pendingIntent.getIntentSender(), 1001,
                                        new Intent(), Integer.valueOf(0),
                                        Integer.valueOf(0), Integer.valueOf(0));
                            }
                        }
                    }
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SendIntentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        Button purchaseBtn5 = (Button) findViewById(R.id.addBtn5);
        purchaseBtn5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList skuList = new ArrayList();
                skuList.add(inappid5);
                Bundle querySkus = new Bundle();
                querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
                Bundle skuDetails;
                try {
                    skuDetails = mservice.getSkuDetails(3, getPackageName(),
                            "inapp", querySkus);

                    int response = skuDetails.getInt("RESPONSE_CODE");
                    if (response == 0) {

                        ArrayList<String> responseList = skuDetails
                                .getStringArrayList("DETAILS_LIST");

                        for (String thisResponse : responseList) {
                            JSONObject object = new JSONObject(thisResponse);
                            String sku = object.getString("productId");
                            String price = object.getString("price");
                            if (sku.equals(inappid5)) {
                                System.out.println("price " + price);
                                Bundle buyIntentBundle = mservice
                                        .getBuyIntent(3, getPackageName(), sku,
                                                "inapp",
                                                "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                                PendingIntent pendingIntent = buyIntentBundle
                                        .getParcelable("BUY_INTENT");
                                startIntentSenderForResult(
                                        pendingIntent.getIntentSender(), 1001,
                                        new Intent(), Integer.valueOf(0),
                                        Integer.valueOf(0), Integer.valueOf(0));

                            }
                        }
                    } else {
                        Toast.makeText(
                                DonateActivity.this,
                                "ASD!",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SendIntentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        Button PayPal = (Button) findViewById(R.id.addBtn6);
        PayPal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=danimperato%40gmail%2ecom&lc=IT&item_name=Service%20Notes&no_note=0&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHostedGuest"));
                startActivity(i2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);

                    Snackbar.make(findViewById(android.R.id.content),
                            "Thank you for your support!",
                            Snackbar.LENGTH_LONG).setAction("Action", null).
                            show();

                } catch (JSONException e) {
                    System.out.println("Failed to parse purchase data.");
                    e.printStackTrace();
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content),
                        "You can not perform this action right now",
                        Snackbar.LENGTH_LONG).setAction("Action", null).
                        show();

               /* Toast.makeText(
                        DonateActivity.this,
                        "You can not perform this action right now",
                        Toast.LENGTH_LONG).show();*/
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mservice != null) {
            unbindService(mServiceConn);
        }
    }
}

