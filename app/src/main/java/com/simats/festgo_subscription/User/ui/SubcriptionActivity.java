package com.simats.festgo_subscription.User.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryPurchasesParams;
import com.simats.festgo_subscription.R;
import com.simats.festgo_subscription.User.LoginActivity;

import java.util.List;

public class SubcriptionActivity extends AppCompatActivity {

    private BillingManager billingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcription);

        billingManager = new BillingManager(this, new BillingManager.SubscriptionStatusListener() {
            @Override
            public void onSubscriptionActive() {
                // ✅ Allow access to main app
                startActivity(new Intent(SubcriptionActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onSubscriptionInactive() {
                // ❌ Block app access
                Toast.makeText(SubcriptionActivity.this, "You need a subscription to continue", Toast.LENGTH_LONG).show();
                // Optionally open purchase flow here
            }
        });

        billingManager.startConnection();
    }
    public class BillingManager implements PurchasesUpdatedListener {

        private static final String TAG = "BillingManager";
        private static final String SUBSCRIPTION_ID = "your_monthly_sub_id"; // Play Console ID

        private final BillingClient billingClient;
        private final Activity activity;
        private SubscriptionStatusListener listener;

        public interface SubscriptionStatusListener {
            void onSubscriptionActive();
            void onSubscriptionInactive();
        }

        public BillingManager(Activity activity, SubscriptionStatusListener listener) {
            this.activity = activity;
            this.listener = listener;

            billingClient = BillingClient.newBuilder(activity)
                    .enablePendingPurchases()
                    .setListener(this)
                    .build();
        }

        public void startConnection() {
            billingClient.startConnection(new BillingClientStateListener() {
                @Override
                public void onBillingSetupFinished(BillingResult billingResult) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        checkActiveSubscription();
                    } else {
                        listener.onSubscriptionInactive();
                    }
                }

                @Override
                public void onBillingServiceDisconnected() {
                    Log.d(TAG, "Billing service disconnected");
                }
            });
        }

        private void checkActiveSubscription() {
            billingClient.queryPurchasesAsync(
                    QueryPurchasesParams.newBuilder()
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build(),
                    (billingResult, purchasesList) -> {
                        boolean active = false;
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            for (Purchase purchase : purchasesList) {
                                if (purchase.getProducts().contains(SUBSCRIPTION_ID)
                                        && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED
                                        && !purchase.isAcknowledged()) {
                                    active = true;
                                }
                            }
                        }
                        if (active) {
                            listener.onSubscriptionActive();
                        } else {
                            listener.onSubscriptionInactive();
                        }
                    });
        }

        @Override
        public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
            // Not needed for initial check, but can handle purchase flow here
        }
    }
}