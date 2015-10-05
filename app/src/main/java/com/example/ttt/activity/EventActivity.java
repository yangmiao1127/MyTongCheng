package com.example.ttt.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttt.R;
import com.example.ttt.adapter.EventAdapter;
import com.example.ttt.model.City;
import com.example.ttt.model.Event;
import com.example.ttt.util.HttpCallbackListener;
import com.example.ttt.util.HttpUtil;
import com.example.ttt.util.RefreshableView;
import com.example.ttt.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by 1 on 2015/10/1.
 */
public class EventActivity extends Activity {

    RefreshableView refreshableView;
    private ProgressDialog progressDialog;

    private ListView listView;
    private static final int DDD=1;
    private List<Event> eventList = new ArrayList<Event>();
    private EventAdapter mAdapter;
    private String id;
    private String name;
    private String type;
    private android.os.Handler handler=new android.os.Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DDD:
                    eventList= (List<Event>) msg.obj;
                    mAdapter = new EventAdapter(EventActivity.this,R.layout.event_activity,eventList);
                    mAdapter.notifyDataSetChanged();
                    listView.setAdapter(mAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Event event = eventList.get(position);

                            Toast.makeText(EventActivity.this, event.getTitle(), Toast.LENGTH_SHORT).show();

                            Intent intent1 = new Intent(EventActivity.this, MainActivity.class);
                            intent1.putExtra("eventId", event.getEventId());

                            Log.d("III",event.getEventId());

                            startActivity(intent1);
                        }
                    });

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        listView= (ListView) findViewById(R.id.list_event);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name=intent.getStringExtra("name");
        setTitle(name);

        ActionBar actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener=new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                switch (tab.getPosition()){
                    case 0:
                        setTitle("所有");
                        type="all";
                        break;
                    case 1:
                        setTitle("音乐");
                        type="music";
                        Log.d("kkkkk",type);
                        break;
                    case 2:
                        setTitle("电影");
                        type="film";
                        break;
                    case 3:
                        setTitle("戏剧");
                        type="drama";
                        break;
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };



        ActionBar.Tab tab=actionBar.newTab()
                .setText("所有").setTabListener(tabListener);
        actionBar.addTab(tab);
        ActionBar.Tab tab1=actionBar.newTab()
                .setText("音乐").setTabListener(tabListener);
        actionBar.addTab(tab1);
        ActionBar.Tab tab2=actionBar.newTab()
                .setText("电影").setTabListener(tabListener);
        actionBar.addTab(tab2);
        ActionBar.Tab tab3=actionBar.newTab()
                .setText("戏剧").setTabListener(tabListener);
        actionBar.addTab(tab3);

        Log.d("kkkkk2", type);


        String website = "https://api.douban.com/v2/event/list?loc=" + id + "&day_type=future&type="+type;

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("加载中");
        progressDialog.setCanceledOnTouchOutside(false);

        Log.d("OOOOO",website);

        HttpUtil.sendHttpRequest(website, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                parseJSONWithJSONObject(response);
                progressDialog.dismiss();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.event,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save:
                Intent intent=new Intent(EventActivity.this,SaveActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                Intent intent1=new Intent(EventActivity.this,SettingActivity.class);
                startActivity(intent1);
                break;
        }


        return true;
    }



    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONArray("events");
            List<Event> eventList1=new ArrayList<Event>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);

                String eventId = json.getString("id");
                String title = json.getString("title");
                String startTime = json.getString("begin_time");
                String endTime = json.getString("end_time");


                Event event = new Event();
                event.setEventId(eventId);
                event.setStartTime(startTime);
                event.setEndTime(endTime);
                event.setTitle(title);

                eventList1.add(event);
            }
            Message message=new Message();
            message.what=DDD;
            message.obj=eventList1;
            handler.sendMessage(message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
