package notes.service.com.servicenotes;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class DonateActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ServiceUtils.setSavedAnimations(this);
        ServiceUtils.setSavedLanguage(this);
        Window window = getWindow();
        ServiceUtils.setSavedTheme(this, window);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.action_donate2);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqh7t8U87IitQWGwtM30dA253ec+YeskONuZXJnnpJYit/e0XDlxKB95Nb1658H5cRMbxqvwzUfyRIS64LgJWP/EtrqSNjCNuccvRj1psZylvkU9z+MCHm3pxJ7kS9wadVtp7Biw8KBPK7QnEdX2arQpykbdNipJHjQBn7D4ynonC/emoZKv4KIIScOo4j2Tg09EyFzNnXBBbWh9xKx4w9kUbXbA0hHqKA/iryri0fgy+1QX1+S74FHLXqNvJb5Xy5ZKQcmZZW+xve/FybfCWkLhkH0o6Rc191Ff4MgSH47i3oHy0v1yF2icPekc6fSeBt10E88H+yuTlWfz1ZO/efwIDAQAB", this);
        bp.consumePurchase("gp1");
        bp.consumePurchase("gp2");
        bp.consumePurchase("gp3");
        bp.consumePurchase("gp5");
        Button purchaseBtn = (Button) findViewById(R.id.addBtn);
        purchaseBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                bp.purchase(DonateActivity.this, "gp1");
            }
        });
        Button purchaseBtn2 = (Button) findViewById(R.id.addBtn2);
        purchaseBtn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                bp.purchase(DonateActivity.this, "gp2");
            }
        });
        Button purchaseBtn3 = (Button) findViewById(R.id.addBtn3);
        purchaseBtn3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                bp.purchase(DonateActivity.this, "gp3");
            }
        });

        Button purchaseBtn5 = (Button) findViewById(R.id.addBtn5);
        purchaseBtn5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                bp.purchase(DonateActivity.this, "gp5");
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
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }

    @Override
    public void onBillingInitialized() {
        /*
         * Called when BillingProcessor was initialized and it's ready to purchase
         */
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        /*
         * Called when requested PRODUCT ID was successfully purchased
         */

        Snackbar.make(findViewById(android.R.id.content),
                "Transaction done, Thank you!",
                Snackbar.LENGTH_LONG).setAction("Action", null).
                show();
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        /*
         * Called when some error occurred. See Constants class for more details
         */

        Snackbar.make(findViewById(android.R.id.content),
                "Sorry, an error occured",
                Snackbar.LENGTH_LONG).setAction("Action", null).
                show();
    }

    @Override
    public void onPurchaseHistoryRestored() {
        /*
         * Called when purchase history was restored and the list of all owned PRODUCT ID's
         * was loaded from Google Play
         */
    }
}

