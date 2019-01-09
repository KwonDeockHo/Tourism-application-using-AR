package org.stampar;

/**
 * Created by GE62 on 2016-11-25.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;


public class tutorial_main extends ActionBarActivity{
    ViewPager viewPager = null;
    Thread thread = null;
    Handler handler = null;
    int p=0;	//페이지번호
    int v=1;	//화면 전환 뱡향
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_active);
        //addContentView(this, R.layout.tutorial_active);

        //viewPager
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        handler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                if(p==0){
                    viewPager.setCurrentItem(1);
                    p++;
                    v=1;
                }if(p==1&&v==0){
                    viewPager.setCurrentItem(1);
                    p--;
                }if(p==1&&v==1){
                    viewPager.setCurrentItem(2);
                    p++;
                }if(p==2){
                    viewPager.setCurrentItem(1);
                    p--;
                    v=0;
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
        // 이를 해제하면 1-2 로 3-2로 가는 2초당의 스레드가 자동 발생합니다.
        //thread.start();
    }

    public void onTutoClick(View v)
    {
        //startActivity(new Intent(ctx, SecondMain.class));
        startActivity(new Intent(this, map_activity.class));
        //startActivity(new Intent(this, FirstFragment.class));
        finish();
    }

}

