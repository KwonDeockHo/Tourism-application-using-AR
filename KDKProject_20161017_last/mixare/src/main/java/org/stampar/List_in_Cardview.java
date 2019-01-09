package org.stampar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.stampar.plugin.PluginType;

import java.util.ArrayList;
import java.util.List;


public class List_in_Cardview extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ArrayList<SectionListDataModel> allSampleData;

    Button stemp_button;
    Button camera_button;
    Button map_button;

    FloatingActionButton fab_plus,fab1,fab2;
    Animation fab_open, fab_close, fab_clock, fab_antiClock;
    boolean isOpen = false;

    private Context ctx;
    private final String usePluginsPrefs = "mixareUsePluginsPrefs";
    private final String usePluginsKey = "usePlugins";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_in_cardview_activity);
        setTitle(null);

        ctx = this;

        fab_plus = (FloatingActionButton)findViewById(R.id.fab_plus);
        fab1 = (FloatingActionButton)findViewById(R.id.fab_button1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab_button2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fab_antiClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_app);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //topToolBar.setLogo(R.drawable.cast_album_art_placeholder);
       // topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

         allSampleData = new ArrayList<SectionListDataModel>();

       // android:id="@+id/imageView"

        Intent intent = getIntent();
        String st =intent.getStringExtra("list_info");
        ImageView pi = (ImageView)findViewById(R.id.imageView);
        TextView pheader =(TextView)findViewById(R.id.header_text);
        TextView pcontent =(TextView)findViewById(R.id.blog_content);

        if( st.equals("디아크"))
        {
            pi.setBackgroundResource(R.drawable.p_thearc);
            pheader.setText(st);
            pcontent.setText(R.string.thearc);
        }

        else if(st.equals("2.28공원"))
        {
            pi.setBackgroundResource(R.drawable.p_2_28);
            pheader.setText(st);
            pcontent.setText(R.string.park_2_28);
        }
        else if(st.equals("야외음악당"))
        {
            pi.setBackgroundResource(R.drawable.p_duru);
            pheader.setText(st);
            pcontent.setText(R.string.duru);
        }
        else if(st.equals("김광석거리"))
        {
            pi.setBackgroundResource(R.drawable.p_street1);
            pheader.setText(st);
            pcontent.setText(R.string.street);
        }

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

        createDummyData(st);

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        Store_RecyclerViewAdapter adapter = new Store_RecyclerViewAdapter(this, allSampleData);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.cardview_menu, menu);
        return true;
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

    public void back_button(View v)
    {
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_first_layout) {
            //Toast.makeText(list_MainActivity.this, "First_Layout", Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(getApplicationContext(), FirstFragment.class);
            startActivity(new Intent(this, FirstFragment.class));
        } else if (id == R.id.nav_second_layout) {

        } else if (id == R.id.nav_third_layout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_refresh){
            Toast.makeText(List_in_Cardview.this, "Refresh App", Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_new){
            Toast.makeText(List_in_Cardview.this, "Create Text", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void createDummyData(String st) {

        SectionListDataModel dm = new SectionListDataModel();

        dm.setHeaderTitle("주변음식점 " );
        ArrayList<Store_data> singleItem = new ArrayList<Store_data>();

        // 상점 데이터 집어 넣는곳 item : 부가설명,  R.drawable 사진 경로
        // position에 따라 각게 다른 상점을 들어 가게 만들어야 함

        if(st.equals("디아크"))
        {
            singleItem.add(new Store_data("금호식당 " , R.drawable.arc1,"https://www.google.co.kr/maps/place/%EA%B8%88%ED%98%B8%EC%8B%9D%EB%8B%B9/@35.8425132,128.4629105,18.96z/data=!4m5!3m4!1s0x3565f03e07a58a2b:0x5490b3c5403a9ba8!8m2!3d35.8425479!4d128.463153" ));
            singleItem.add(new Store_data("경산식당 " , R.drawable.arc2,"https://www.google.co.kr/maps/place/%EA%B2%BD%EC%82%B0%EC%8B%9D%EB%8B%B9/@35.8425479,128.4627842,19z/data=!3m1!4b1!4m5!3m4!1s0x3565f03e00ce5b63:0x75ec6c78b5fe30f2!8m2!3d35.8425479!4d128.4633474" ));
            singleItem.add(new Store_data("필레몬스 " , R.drawable.arc3,"https://www.google.co.kr/maps/place/35%C2%B050'37.2%22N+128%C2%B027'58.3%22E/@35.8438282,128.4652232,17.74z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.843673!4d128.466193" ));
            singleItem.add(new Store_data("파스쿠찌 " , R.drawable.arc4,"https://www.google.co.kr/maps/place/35%C2%B050'28.7%22N+128%C2%B027'55.4%22E/@35.8413888,128.4647178,18.22z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.841308!4d128.465394" ));
        }
        else if(st.equals("2.28공원"))
        {
            singleItem.add(new Store_data("중앙떡복이 " , R.drawable.part2_28_1,"https://www.google.co.kr/maps/place/%EC%A4%91%EC%95%99%EB%96%A1%EB%B3%B6%EC%9D%B4/@35.8697405,128.5959703,17.96z/data=!4m12!1m6!3m5!1s0x3565e3c4f39f218f:0xca2f6595ff2a2ebc!2z7KSR7JWZ65ah67O27J20!8m2!3d35.8694916!4d128.5970284!3m4!1s0x3565e3c4f39f218f:0xca2f6595ff2a2ebc!8m2!3d35.8694916!4d128.5970284" ));
            singleItem.add(new Store_data("카레센타 " , R.drawable.part2_28_2 ,"https://www.google.co.kr/maps/place/35%C2%B052'09.7%22N+128%C2%B035'49.0%22E/@35.869359,128.59631,18z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.869359!4d128.596935"));
            singleItem.add(new Store_data("엔젤리너스 " , R.drawable.part2_28_3 ,"https://www.google.co.kr/maps/place/35%C2%B052'08.2%22N+128%C2%B035'49.5%22E/@35.8688904,128.5966998,19z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.868936!4d128.597078"));
            singleItem.add(new Store_data("봉추찜닭 " , R.drawable.part2_28_4 ,"https://www.google.co.kr/maps/place/35%C2%B052'09.1%22N+128%C2%B035'47.7%22E/@35.8692103,128.5960779,18.96z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.869187!4d128.596593"));
        }
        else if(st.equals("야외음악당"))
        {
            singleItem.add(new Store_data("궁" , R.drawable.ex1,"https://www.google.co.kr/maps/place/35%C2%B051'07.2%22N+128%C2%B033'17.2%22E/@35.852005,128.5525883,17z/data=!3m1!4b1!4m5!3m4!1s0x0:0x0!8m2!3d35.852005!4d128.554777" ));
            singleItem.add(new Store_data("한식정" , R.drawable.ex2,"https://www.google.co.kr/maps/place/35%C2%B051'07.2%22N+128%C2%B033'17.2%22E/@35.8524398,128.5527278,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.852005!4d128.554777" ));
            singleItem.add(new Store_data("김파사" , R.drawable.ex3,"https://www.google.co.kr/maps/place/35%C2%B051'07.2%22N+128%C2%B033'17.2%22E/@35.8524398,128.5527278,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.852005!4d128.554777" ));
            singleItem.add(new Store_data("가이세키" , R.drawable.ex4,"https://www.google.co.kr/maps/place/35%C2%B051'07.2%22N+128%C2%B033'17.2%22E/@35.8524398,128.5527278,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.852005!4d128.554777" ));
        }
        else if(st.equals("김광석거리"))
        {
            singleItem.add(new Store_data("또바기치킨 " , R.drawable.street1,"https://www.google.co.kr/maps/place/35%C2%B051'38.8%22N+128%C2%B036'24.1%22E/@35.8607965,128.6043762,16.92z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.860781!4d128.606688" ));
            singleItem.add(new Store_data("닭한끼 " , R.drawable.street2,"https://www.google.co.kr/maps/place/35%C2%B051'40.2%22N+128%C2%B036'24.4%22E/@35.8612206,128.6041055,16.7z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.861168!4d128.606774" ));
            singleItem.add(new Store_data("오짱 " , R.drawable.street3,"https://www.google.co.kr/maps/place/35%C2%B051'38.0%22N+128%C2%B036'25.1%22E/@35.8605653,128.6047329,16.96z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.860559!4d128.606975" ));
            singleItem.add(new Store_data("커피명가 " , R.drawable.street4,"https://www.google.co.kr/maps/place/35%C2%B051'36.7%22N+128%C2%B036'23.6%22E/@35.8603823,128.6020547,15.96z/data=!4m5!3m4!1s0x0:0x0!8m2!3d35.860189!4d128.606549" ));
        }

        dm.setAllItemsInSection(singleItem);

        allSampleData.add(dm);


    }

}
