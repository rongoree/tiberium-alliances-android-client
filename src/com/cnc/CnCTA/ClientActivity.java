package com.cnc.CnCTA;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import com.cnc.CnCTA.fragment.BaseFragment;
import com.cnc.CnCTA.fragment.BasesFragment;
import com.cnc.CnCTA.fragment.ServersFragment;
import com.cnc.CnCTA.helper.ErrorHandler;
import com.cnc.game.Client;
import com.cnc.model.Server;
import com.cnc.model.base.City;

import java.util.ArrayList;


public class ClientActivity extends FragmentActivity implements ErrorHandler.HandleActivity {
    private final ErrorHandler errorHandler = new ErrorHandler(this);
    private Client gameClient;

    private Handler interfaceHandler;

    private SharedPreferences sharedPref;

    private ServersFragment serversFragment;
    private BaseFragment baseFragment;
    private BasesFragment basesFragment;
    private Fragment currentFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);


        interfaceHandler = new Handler();
        sharedPref = getSharedPreferences(LoginActivity.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

        gameClient = new Client();
        gameClient.setHash(sharedPref.getString(LoginActivity.HASH_KEY, ""));

        serversFragment = new ServersFragment(gameClient, errorHandler, (ArrayList<Server>) getIntent().getSerializableExtra("servers"));
        basesFragment = new BasesFragment(gameClient, errorHandler);
        baseFragment = new BaseFragment(gameClient, errorHandler);

        showFragment(serversFragment, false);
    }

    public void showBases() {
        showFragment(basesFragment, true);
        basesFragment.setLoaded(false);
    }

    private void showFragment(Fragment fragment, boolean addToStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, fragment);
        if (addToStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        currentFragment = fragment;
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Handler getHandler() {
        return interfaceHandler;
    }

    public void loadImage(ImageView holder, String resource) {
        int imageResource = this.getResources().getIdentifier("@drawable/" + resource, null, this.getPackageName());
        holder.setImageResource(imageResource);
    }

    public void openCity(City city) {
        baseFragment.setCity(city);
        showFragment(baseFragment, true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (currentFragment.equals(baseFragment)) {
            baseFragment.updateGridMargins();
        }
    }
}