package com.example.ttt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ttt.R;
import com.example.ttt.model.Event;

import java.util.List;

/**
 * Created by 1 on 2015/10/1.
 */
public class EventAdapter extends ArrayAdapter<Event>{

    private int resourceId;

    public EventAdapter(Context context, int textViewResourceId, List<Event> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else{
            view=convertView;
        }

        TextView listTitle= (TextView) view.findViewById(R.id.list_title);
        TextView startTime= (TextView) view.findViewById(R.id.start_time);
        TextView stopTime= (TextView) view.findViewById(R.id.end_time);
        TextView id= (TextView) view.findViewById(R.id.event_id);

        listTitle.setText(event.getTitle());
        startTime.setText(event.getStartTime());
        stopTime.setText(event.getEndTime());
        id.setText(event.getEventId());
        return view;
    }
}