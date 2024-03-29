package com.badlogic.drop.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.pay.Information;
import com.badlogic.gdx.pay.Offer;
import com.badlogic.gdx.pay.OfferType;
import com.badlogic.gdx.pay.PurchaseManager;
import com.badlogic.gdx.pay.PurchaseManagerConfig;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.Transaction;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PaymentForm extends Dialog {
    public static final String MY_ENTITLEMENT = "entitlement_sku";
    public static final String MY_CONSUMABLE = "consumable_sku";

    private final MainMenuScreen app;
    private final TextButton restoreButton;
    private final TextButton closeButton;
    private boolean restorePressed;
    private TapButton buyEntitlement;
    private TapButton buyConsumable;

    public PaymentForm(final MainMenuScreen app) {
        super("", app.skin);
        this.app = app;

        closeButton = new TextButton("Close", app.skin);
        button(closeButton);

        restoreButton = new TextButton("Restore", app.skin);
        restoreButton.setDisabled(true);
        restoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                restorePressed = true;
                restoreButton.setDisabled(true);
                app.purchaseManager.purchaseRestore();
            }
        });

        getButtonTable().add(restoreButton);

        fillContent(app);
        initPurchaseManager();
    }

    private void fillContent(MainMenuScreen app) {
        Table contentTable = getContentTable();
        contentTable.pad(10);

        contentTable.add(new Label("My Payment Form", app.skin));
        contentTable.row();

        Table iapTable = new Table();
        iapTable.defaults().fillX().uniform().expandX();
        buyEntitlement = new TapButton(MY_ENTITLEMENT, 179);
        iapTable.add(buyEntitlement);
        buyConsumable = new TapButton(MY_CONSUMABLE, 349);
        iapTable.row();
        iapTable.add(buyConsumable);

        contentTable.add(iapTable);
    }

    private void initPurchaseManager() {
        PurchaseManagerConfig pmc = new PurchaseManagerConfig();
        pmc.addOffer(new Offer().setType(OfferType.ENTITLEMENT).setIdentifier(MY_ENTITLEMENT));
        pmc.addOffer(new Offer().setType(OfferType.CONSUMABLE).setIdentifier(MY_CONSUMABLE));

        app.purchaseManager.install(new MyPurchaseObserver(), pmc, true);
    }

    private void updateGuiWhenPurchaseManInstalled(String errorMessage) {

        buyEntitlement.updateFromManager();
        buyConsumable.updateFromManager();

        if (app.purchaseManager.installed() && errorMessage == null) {
            restoreButton.setDisabled(false);
        } else {
            errorMessage = (errorMessage == null ? "Error instantiating the purchase system" : errorMessage);

        }

    }

    private class TapButton extends TextButton {
        private final String sku;
        private final int usdCents;

        public TapButton(String sku, int usdCents) {
            super(sku, app.skin);
            this.sku = sku;
            this.usdCents = usdCents;

            addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    buyItem();
                }
            });
        }

        private void buyItem() {
            app.purchaseManager.purchase(sku);
        }

        public void setBought(boolean fromRestore) {
            setDisabled(true);
        }

        public void updateFromManager() {
            Information skuInfo = app.purchaseManager.getInformation(sku);

            if (skuInfo == null || skuInfo.equals(Information.UNAVAILABLE)) {
                setDisabled(true);
                setText("Not available");
            } else {
                setText(skuInfo.getLocalName() + " " + skuInfo.getLocalPricing());
            }
        }
    }

    private class MyPurchaseObserver implements PurchaseObserver {

        @Override
        public void handleInstall() {
            Gdx.app.log("IAP", "Installed");

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    updateGuiWhenPurchaseManInstalled(null);
                }
            });
        }

        @Override
        public void handleInstallError(final Throwable e) {
            Gdx.app.error("IAP", "Error when trying to install PurchaseManager", e);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    updateGuiWhenPurchaseManInstalled(e.getMessage());
                }
            });
        }

        @Override
        public void handleRestore(final Transaction[] transactions) {
            if (transactions != null && transactions.length > 0)
                for (Transaction t : transactions) {
                    handlePurchase(t, true);
                }
            else if (restorePressed)
                showErrorOnMainThread("Nothing to restore");
        }

        @Override
        public void handleRestoreError(Throwable e) {
            if (restorePressed)
                showErrorOnMainThread("Error restoring purchases: " + e.getMessage());
        }

        @Override
        public void handlePurchase(final Transaction transaction) {
            handlePurchase(transaction, false);
        }

        protected void handlePurchase(final Transaction transaction, final boolean fromRestore) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    if (transaction.isPurchased()) {
                        if (transaction.getIdentifier().equals(MY_ENTITLEMENT))
                            buyEntitlement.setBought(fromRestore);
                        else if (transaction.getIdentifier().equals(MY_CONSUMABLE))
                            buyConsumable.setBought(fromRestore);

                    }
                }
            });
        }

        @Override
        public void handlePurchaseError(Throwable e) {
            showErrorOnMainThread("Error on buying:\n" + e.getMessage());
        }

        @Override
        public void handlePurchaseCanceled() {

        }

        private void showErrorOnMainThread(final String message) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    // show a dialog here...
                }
            });
        }
    }
}
