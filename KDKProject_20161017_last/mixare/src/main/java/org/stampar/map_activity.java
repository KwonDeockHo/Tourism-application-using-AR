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
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.stampar.plugin.PluginType;

import java.util.HashMap;
import java.util.List;

public class map_activity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {

    static final LatLng SEOUL = new LatLng(35.84, 128.555);
    static final LatLng TARGET1 = new LatLng(35.861858, 128.606951); //김광석거리
    static final LatLng TARGET2 = new LatLng(35.838796, 128.468352); //디아크
    static final LatLng TARGET3 = new LatLng(35.870086, 128.597754);//2.28공원
    static final LatLng TARGET4 = new LatLng(35.851318, 128.556086); //야외음악당

    private HashMap<Marker, Integer> hash = new HashMap<Marker, Integer>();
    private static final String TAG = "@@@";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final int REQUEST_CODE_LOCATION = 2;
    private CoordinatorLayout coordinatorLayout;


    FloatingActionButton fab_plus,fab1,fab2, fab3;
    Animation fab_open, fab_close, fab_clock, fab_antiClock;
    boolean isOpen = false;

    private Context ctx;
    private final String usePluginsPrefs = "mixareUsePluginsPrefs";
    private final String usePluginsKey = "usePlugins";


    Thread thread = null;
    Handler handler = null;

    int p=0;	//페이지번호
    int v=1;	//화면 전환 뱡향


    public Animation animationFadeIn = null;
    public Animation animationFadeOut = null;

    private TextView map_text = null;

    //private LatLngBounds newarkBounds = new LatLngBounds(new LatLng(35.80, 127.56), new LatLng(35.87, 129.56)); 지도 바운더리 설정
    private GoogleMap googleMap;

    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        googleMap.setMyLocationEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(SEOUL).zoom((float) 11.1).bearing(0).tilt(90).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        final Marker t1 = googleMap.addMarker(new MarkerOptions().position(TARGET1));
        t1.setTitle("김광석거리");
        final Marker t2 = googleMap.addMarker(new MarkerOptions().position(TARGET2));
        t2.setTitle("디아크");
        final Marker t3 = googleMap.addMarker(new MarkerOptions().position(TARGET3));
        t3.setTitle("2.28공원");
        final Marker t4 = googleMap.addMarker(new MarkerOptions().position(TARGET4));
        t4.setTitle("야외음악당");

        hash.put(t1, R.drawable.p_street1);
        hash.put(t2,  R.drawable.p_thearc);
        hash.put(t3,  R.drawable.p_2_28);
        hash.put(t4,  R.drawable.p_duru);

        //김광석 ,디아크 ,2.28 , 야외음악당
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);


        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(final Marker marker) {
                //SpannableStringBuilder builder = new SpannableStringBuilder();
                //builder.append("My message ").append(" ");
                //ImageSpan is= new ImageSpan(map_activity.this, hash.get(marker));
                //builder.setSpan(new ImageSpan(map_activity.this, hash.get(marker) ), builder.length() -10, builder.length(), 0);
                //builder.append(" next message");
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "", Snackbar.LENGTH_LONG).setActionTextColor(Color.parseColor("#FF0000"))
                        .setAction("Go!", new View.OnClickListener() {

                            public void onClick(View view) {
                                Intent intent = new Intent(map_activity.this, // 현재 화면의 제어권자
                                        list_MainActivity.class); // 다음 넘어갈 클래스 지정
                                intent.putExtra("marker_info", marker.getTitle());
                                startActivity(intent);
                            }
                        });

                snackbar.setDuration(40000);
                View snackView = snackbar.getView();
                snackView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                //김광석거리 ,디아크 ,2.28공원 , 야외음악당
                if(marker.getTitle().equals("김광석거리"))
                    snackView.setBackgroundResource(R.drawable.hello4);

                else if(marker.getTitle().equals("디아크"))
                    snackView.setBackgroundResource(R.drawable.hello1);

                else if(marker.getTitle().equals("2.28공원"))
                    snackView.setBackgroundResource(R.drawable.hello3);
                else
                    snackView.setBackgroundResource(R.drawable.hello2);

                snackbar.show();

                return false;
            }
        });

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.marker_extra_info, null);

               ImageView imagen = ((ImageView)v.findViewById(R.id.imagen));

               if(hash.get(marker) != null) {
                   imagen.setImageResource(hash.get(marker));

                   //setContentView(imagen);
               }
                return null;

            }
            public View getInfoWindow(Marker marker) {
                // TODO Auto-generated method stub
                return null;
            }
        });
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_activity);
        ctx = this;

        fab_plus = (FloatingActionButton)findViewById(R.id.fab_plus);
        fab1 = (FloatingActionButton)findViewById(R.id.fab_button1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab_button2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab_button3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fab_antiClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        map_text = (TextView)findViewById(R.id.map_text);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fab_plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(isOpen)
                {
                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    fab3.startAnimation(fab_close);
                    fab_plus.startAnimation(fab_antiClock);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    fab3.setClickable(false);
                    isOpen = false;
                }
                else
                {
                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);

                    fab3.startAnimation(fab_open);
                    fab_plus.startAnimation(fab_clock);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    fab3.setClickable(true);
                    isOpen = true;
                }
            }

        });
        map_text.setText("안녕하세요 STAMPAR입니다.");
        handler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                if(p==0){
                    map_text.setText("안녕하세요 STAMPAR 입니다.");
                    map_text.startAnimation(animationFadeIn);
                    p=1;
                }else if(p==1){
                    map_text.setText("현재 위치를 확인하세요");
                    map_text.startAnimation(animationFadeIn);
                    p=2;
                }else if(p==2){
                    map_text.setText("주변의 스탬프를 확인하세요");
                    map_text.startAnimation(animationFadeIn);
                    p=0;
                }
            }
        };
        thread = new Thread(){
            //run은 jvm이 쓰레드를 채택하면, 해당 쓰레드의 run메서드를 수행한다.
            public void run() {
                super.run();
                while(true){
                    try {
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(1500);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            CheckPermission();
        }
        startLocationUpdates();
    }


    private void CheckPermission() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(map_activity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(map_activity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(map_activity.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_LOCATION);
                return;
            }
            ActivityCompat.requestPermissions(map_activity.this,
                    new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_LOCATION);
            return;
        }
        hasWriteContactsPermission = ContextCompat.checkSelfPermission(map_activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(map_activity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(map_activity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION);
                return;
            }
            ActivityCompat.requestPermissions(map_activity.this,
                    new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_LOCATION);
            return;
        }
    }

    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void onLocationChanged(Location location) {
        LatLng CURRENT_LOCATION = new LatLng(location.getLatitude(), location.getLongitude());
        // googleMap.clear();
        Marker seoul = googleMap.addMarker(new MarkerOptions().position(CURRENT_LOCATION).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        seoul.setTitle("현재위치");
       // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CURRENT_LOCATION, (float) 11.1));

    }

    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (locationAvailability.isLocationAvailable()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            Toast.makeText(this,"Location Unavialable",Toast.LENGTH_LONG).show();
        }
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

