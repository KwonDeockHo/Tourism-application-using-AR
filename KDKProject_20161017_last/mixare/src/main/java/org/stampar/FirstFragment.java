package org.stampar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.stampar.plugin.PluginType;

import java.util.List;

/**
 * Created by 덕호 on 2016-11-15.
 */

public class FirstFragment extends Activity{
    View myView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ImageView buton[] = new ImageView[16];

    FloatingActionButton fab_plus,fab1,fab2;
    Animation fab_open, fab_close, fab_clock, fab_antiClock;
    boolean isOpen = false;

    private Context ctx;
    private final String usePluginsPrefs = "mixareUsePluginsPrefs";
    private final String usePluginsKey = "usePlugins";


    RelativeLayout r1 = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        ctx = this;
        pref = getSharedPreferences("KDK_DATA", Activity.MODE_PRIVATE);

        fab_plus = (FloatingActionButton)findViewById(R.id.fab_plus);
        fab1 = (FloatingActionButton)findViewById(R.id.fab_button1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab_button2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fab_antiClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);


        r1 = (RelativeLayout)findViewById(R.id.r1);

        buton[0] = new ImageView(this);
        buton[1] = new ImageView(this);
        buton[2] = new ImageView(this);
        buton[3] = new ImageView(this);
        buton[4] = new ImageView(this);

        //buton[0].setBackgroundColor(Color.YELLOW);
        //buton[0].setBackground(getResources().getDrawable(R.drawable.app_bar));


        /*
        buton[4] = (ImageView)findViewById(R.id.dongs_stamp_5);
        buton[5] = (ImageView)findViewById(R.id.dongs_stamp_6);
        buton[6] = (ImageView)findViewById(R.id.dongs_stamp_7);
        buton[7] = (ImageView)findViewById(R.id.dongs_stamp_8);
        buton[8] = (ImageView)findViewById(R.id.dongs_stamp_9);
        buton[9] = (ImageView)findViewById(R.id.dongs_stamp_10);
        buton[10] = (ImageView)findViewById(R.id.dongs_stamp_11);
        buton[11] = (ImageView)findViewById(R.id.dongs_stamp_12);
        */
        //buton[0].setImageResource(R.drawable.stamp_front_228);

      //  Log.v("DD__DDD", String.valueOf(pref.getInt("STAMP_4", 0)));



        buton[0].setImageDrawable(getDrawable(R.drawable.stamp_final_gang));
        buton[0].setRotation(-15.0f);
        RelativeLayout.LayoutParams params;
        params = new RelativeLayout.LayoutParams(700, 700);
        params.leftMargin = 300;
        params.topMargin = 60;
        r1.addView(buton[0], params);

        buton[1].setImageDrawable(getDrawable(R.drawable.stamp_final_228));
        buton[1].setRotation(12.0f);
        params = new RelativeLayout.LayoutParams(650, 650);
        params.leftMargin = 550;
        params.topMargin = 700;
        r1.addView(buton[1], params);

        buton[2].setImageDrawable(getDrawable(R.drawable.stamp_final_kimst));
        //buton[2].setRotation(12.0f);
        params = new RelativeLayout.LayoutParams(720, 720);
        params.leftMargin = 100;
        params.topMargin = 1300;
        r1.addView(buton[2], params);

        buton[3].setImageDrawable(getDrawable(R.drawable.stamp_final_duryu));
        //buton[2].setRotation(12.0f);
        params = new RelativeLayout.LayoutParams(730, 730);
        params.leftMargin = 300;
        params.topMargin = 1800;
        r1.addView(buton[3], params);



        // 강정보의 경우
        if(pref.getInt("STAMP_1", 0) == 1)
        {
        }
        // 228의 경우
        if(pref.getInt("STAMP_2", 0) == 1)
        {
        }
        // 김광석 거리의 경우
        if(pref.getInt("STAMP_3", 0) == 1)
        {
        }
        // 두류공원의 경우
        if(pref.getInt("STAMP_4", 0) == 1)
        {
        }

        /*
        if(pref.getInt("STAMP_5", 0) == 1)
        {
            buton[4].setImageResource(R.drawable.stamp_color_1);
        }
        if(pref.getInt("STAMP_6", 0) == 1)
        {
            buton[5].setImageResource(R.drawable.stamp_color_2);
        }
        if(pref.getInt("STAMP_7", 0) == 1)
        {
            buton[6].setImageResource(R.drawable.stamp_color_3);
        }
        if(pref.getInt("STAMP_8", 0) == 1)
        {
            buton[7].setImageResource(R.drawable.stamp_color_4);
        }
        if(pref.getInt("STAMP_9", 0) == 1)
        {
            buton[8].setImageResource(R.drawable.stamp_color_5);
        }
        if(pref.getInt("STAMP_10", 0) == 1)
        {
            buton[9].setImageResource(R.drawable.stamp_color_6);
        }
        if(pref.getInt("STAMP_11", 0) == 1)
        {
            buton[10].setImageResource(R.drawable.stamp_color_7);
        }
        if(pref.getInt("STAMP_12", 0) == 1)
        {
            buton[11].setImageResource(R.drawable.stamp_color_8);
        }
        */
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
    }
    public void clickMethod_1(View v)
    {
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
    public void clickMethod_2(View v)
    {
        startActivity(new Intent(ctx, map_activity.class));
        finish();
    }
    public void clickMethod_3(View v)
    {
        startActivity(new Intent(ctx, FirstFragment.class));
        finish();
    }
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
