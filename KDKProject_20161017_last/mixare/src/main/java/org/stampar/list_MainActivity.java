package org.stampar;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;


public class list_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayoutManager lLayout;
    private static List<ItemObject> rowListItem = new ArrayList<ItemObject>();

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("DONGS", "Helll????");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity_main);
        setTitle(null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
        // 오류가 난 부분 정확하게는 addDrawerListener(toggle); 이 부분에서 에러가 난다. oncreate 관련 오류임.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //topToolBar.setLogo(R.mipmap.ic_thearc);
        //topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        Intent intent = getIntent();
        String a = intent.getStringExtra("marker_info");
        boolean b = false;

        for(int i = 0; i < rowListItem.size(); i++) {
            if (a.equals(rowListItem.get(i).getName())) {
                b = true;
                break;
            }

        }

        if(b == false) {

            if (a.equals("디아크"))
                rowListItem.add(new ItemObject("디아크", R.drawable.p_thearc));
            else if (a.equals("2.28공원"))
                rowListItem.add(new ItemObject("2.28공원", R.drawable.p_2_28));
            else if (a.equals("야외음악당"))
                rowListItem.add(new ItemObject("야외음악당", R.drawable.p_duru));
            else if (a.equals("김광석거리"))
                rowListItem.add(new ItemObject("김광석거리", R.drawable.p_street1));
        }





        lLayout = new LinearLayoutManager(list_MainActivity.this);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(list_MainActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    public void clickMethod(View v) {

    }

    public void back_button_dong(View v)
    {
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        if (id == R.id.action_refresh) {
            Toast.makeText(list_MainActivity.this, "Refresh App", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.action_new) {
            Toast.makeText(list_MainActivity.this, "Create Text", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Fragment fragment = null;
        //FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_first_layout) {
            Toast.makeText(list_MainActivity.this, "First_Layout", Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(getApplicationContext(), FirstFragment.class);
            startActivity(new Intent(this, FirstFragment.class));


            // Intent intent = new Intent(list_MainActivity.this, FirstFragment.class);
            // startActivity(intent);
            //fragment = FirstFragment.newInstance();
            //fragmentManager.beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
        } else if (id == R.id.nav_second_layout) {
            Toast.makeText(list_MainActivity.this, "Second_Layout", Toast.LENGTH_LONG).show();
            //fragmentManager.beginTransaction().replace(R.id.content_frame, new SecondFragment()).commit();
        } else if (id == R.id.nav_third_layout) {
            Toast.makeText(list_MainActivity.this, "Third_Layout", Toast.LENGTH_LONG).show();
            //fragmentManager.beginTransaction().replace(R.id.content_frame, new Thir
            // dFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("list_Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
