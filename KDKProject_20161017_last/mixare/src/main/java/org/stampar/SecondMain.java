package org.stampar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;

import org.stampar.plugin.PluginType;

import java.util.List;

/**
 * Created by GE62 on 2016-11-15.
 */

public class SecondMain extends Activity {

    private Context ctx;
    private final String usePluginsPrefs = "mixareUsePluginsPrefs";
    private final String usePluginsKey = "usePlugins";

    Button button1;
    Button button2;
    Button button3;

    FloatingActionButton fab_plus,fab1,fab2;
    Animation fab_open, fab_close, fab_clock, fab_antiClock;
    boolean isOpen = false;

    boolean musicIsplay = false;
    private static MediaPlayer mp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dongs_layer);
        mp = MediaPlayer.create(this, R.raw.test);
        ctx = this;

        SharedPreferences pref = getSharedPreferences("KDK_DATA", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        // 다른 액티비비에서의 디비가 동작하는지 확인함 - 확인됨.
        Log.v("TEST", "SECOND " + pref.getString("ID", ""));


        fab_plus = (FloatingActionButton)findViewById(R.id.fab_plus);
        fab1 = (FloatingActionButton)findViewById(R.id.fab_button1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab_button2);
        button2 = (Button)findViewById(R.id.musicPlay);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fab_antiClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fab_plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(isOpen)
                {
                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    fab_plus.startAnimation(fab_antiClock);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    isOpen = false;
                }
                else
                {
                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);
                    fab_plus.startAnimation(fab_clock);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    isOpen = true;
                }
            }

        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(musicIsplay)
                {
                    musicIsplay = false;
                    mp.pause();
                }
                else
                {
                    musicIsplay = true;
                    mp.setLooping(true);
                    mp.start();
                }
            }
        });

        // 첫번 째 버튼 -> 카메라
        fab1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mp.stop();
                if(arePluginsAvailable() && isDecisionRemembered()){
                    showDialog();
                }else{
                    if(getRememberedDecision()){ 		//yes button
                        startActivity(new Intent(ctx, PluginLoaderActivity.class));
                        finish();
                    } else{								//no button
                        startActivity(new Intent(ctx, MixView.class));
                        finish();
                    }
                }
            }
        });

        /*
        button1 = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("MAP", "1 메뉴선택 됨.");
                startActivity(new Intent(ctx, map_activity.class));
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("MAP", "2번 메뉴 선택됨.");
                if(arePluginsAvailable() && isDecisionRemembered()){
                    showDialog();
                }else{
                    if(getRememberedDecision()){ 		//yes button
                        startActivity(new Intent(ctx, PluginLoaderActivity.class));
                        finish();
                    } else{								//no button
                        startActivity(new Intent(ctx, MixView.class));
                        finish();
                    }
                }

            }
        });
        button3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("MAP", "3 메뉴선택 됨.");
                startActivity(new Intent(ctx, InformationView.class));
                finish();
            }
        });
        */


		/*
		if(arePluginsAvailable() && isDecisionRemembered()){
			showDialog();
		}else{
			if(getRememberedDecision()){ 		//yes button
				startActivity(new Intent(ctx, PluginLoaderActivity.class));
				finish();
			} else{								//no button
				startActivity(new Intent(ctx, MixView.class));
				finish();
			}
		}
		*/
    }


    public void onBackPressed()
    {
        mp.stop();
        this.finish();
    }
    /**
     * Shows a dialog
     */
    public void showDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(R.string.launch_plugins);
        dialog.setMessage(R.string.plugin_message);

        final CheckBox checkBox = new CheckBox(ctx);
        checkBox.setText(R.string.remember_this_decision);
        dialog.setView(checkBox);

        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int whichButton) {
                processCheckbox(true, checkBox);
                startActivity(new Intent(ctx, PluginLoaderActivity.class));
                finish();
            }
        });

        dialog.setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int whichButton) {
                processCheckbox(true, checkBox);
                startActivity(new Intent(ctx, MixView.class));
                finish();
            }
        });

        dialog.show();
    }

    private boolean isDecisionRemembered(){
        SharedPreferences sharedPreferences = getSharedPreferences(usePluginsPrefs, MODE_PRIVATE);
        return !sharedPreferences.contains(usePluginsKey);
    }

    private boolean arePluginsAvailable(){
        PluginType[] allPluginTypes = PluginType.values();
        for(PluginType pluginType : allPluginTypes){
            PackageManager packageManager = getPackageManager();
            Intent baseIntent = new Intent(pluginType.getActionName());
            List<ResolveInfo> list = packageManager.queryIntentServices(baseIntent,
                    PackageManager.GET_RESOLVED_FILTER);

            if(list.size() > 0){
                return true;
            }
        }
        return false;
    }

    private void processCheckbox(boolean loadplugin, CheckBox checkBox){
        if(checkBox.isChecked()){
            SharedPreferences sharedPreferences = getSharedPreferences(usePluginsPrefs, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(usePluginsKey, loadplugin);
            editor.commit();
        }
    }

    private boolean getRememberedDecision(){
        SharedPreferences sharedPreferences = getSharedPreferences(usePluginsPrefs, MODE_PRIVATE);
        return sharedPreferences.getBoolean(usePluginsKey, false);
    }

}