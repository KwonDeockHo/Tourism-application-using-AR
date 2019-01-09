package org.stampar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GE62 on 2016-11-15.
 */

public class InformationView extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_information);
        Log.v("INFOR", "create");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Recycler_item> items=new ArrayList<>();
        Recycler_item[] item=new Recycler_item[5];
        item[0]=new Recycler_item(R.drawable.image1,"피츄의 최종진화형, 모티브는 캥거루 쥐,\n안녕하세요");
        item[1]=new Recycler_item(R.drawable.image2,"#2");
        item[2]=new Recycler_item(R.drawable.image3,"#3");
        item[3]=new Recycler_item(R.drawable.image4,"#4");
        item[4]=new Recycler_item(R.drawable.image5,"#5");

        for(int i=0;i<5;i++) items.add(item[i]);



        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(),items,R.layout.active_information));
    }

}
