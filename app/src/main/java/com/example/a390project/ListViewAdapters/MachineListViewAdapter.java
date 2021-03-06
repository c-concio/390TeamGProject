package com.example.a390project.ListViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a390project.MachineActivity;
import com.example.a390project.Model.Machine;

import java.util.List;

import com.example.a390project.Model.Paintbooth;
import com.example.a390project.R;

public class MachineListViewAdapter extends BaseAdapter {

    private String TAG = "MachineListAdapter";
    private Context context; //context
    private List<Machine> items; //data source of the list adapter

    //views
    TextView mMachineName;
    TextView mTemperature;
    TextView mStatus;
    TextView mHumidity;
    ConstraintLayout mRowItem;

    //public constructor
    public MachineListViewAdapter(Context context, List<Machine> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.row_item_machine, parent, false);
        }

        // get current item to be displayed
        final Machine currentItem = (Machine) getItem(position);

        //set views of row item
        mMachineName = convertView.findViewById(R.id.machine_title_text_view_row);
        mTemperature = convertView.findViewById(R.id.temperature_machine_text_view);
        mStatus = convertView.findViewById(R.id.status_text_view);
        mRowItem = convertView.findViewById(R.id.machine_row_item);
        mHumidity = convertView.findViewById(R.id.humidity_machine_text_view);

        mMachineName.setText(currentItem.getMachineTitle());
        mTemperature.setText(Float.toString(currentItem.getTemperature()) + "°F");
        String status = currentItem.isMachineStatus() ? "On":"Off";
        mStatus.setText("Status: " + status);
        if (!currentItem.getMachineTitle().equals("Big_Oven") && !currentItem.getMachineTitle().equals("GFS_Oven")) {
            Paintbooth paintbooth = (Paintbooth) currentItem;
            mHumidity.setText(paintbooth.getHumidity() + "%");
        }
        else {
            mHumidity.setVisibility(View.GONE);
        }

        //change background color if On/Off
        if (!currentItem.isMachineStatus())
            mRowItem.setBackgroundColor(Color.parseColor("#FFE5E5"));
        else
            mRowItem.setBackgroundColor(Color.parseColor("#DBFFDC"));

        //onClick opens Machine Activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineTitle = currentItem.getMachineTitle();
                Boolean machineStatus = currentItem.isMachineStatus();
                float machineTemperature = currentItem.getTemperature();
                if (!currentItem.getMachineTitle().equals("Big_Oven") && !currentItem.getMachineTitle().equals("GFS_Oven")) {
                    Paintbooth paintbooth = (Paintbooth) currentItem;
                    float humidity = paintbooth.getHumidity();
                    startMachineActivity(machineTitle, machineStatus, machineTemperature, humidity);
                }
                else {
                    startMachineActivity(machineTitle, machineStatus, machineTemperature);
                }

            }
        });

        return convertView;
    }
    //Controller will only need to send machineID from MachineListViewAdapter to machineActivity,
    // which will get the entire Machine Object from Firebase
    private void startMachineActivity(String machineTitle, boolean machineStatus, float machineTemperature) {
        Intent intent = new Intent(context, MachineActivity.class);
        intent.putExtra("machine_title", machineTitle);
        intent.putExtra("machine_status", machineStatus);
        intent.putExtra("machine_temperature", machineTemperature);
        context.startActivity(intent);
    }

    private void startMachineActivity(String machineTitle, boolean machineStatus, float machineTemperature, float humidity) {
        Intent intent = new Intent(context, MachineActivity.class);
        intent.putExtra("machine_title", machineTitle);
        intent.putExtra("machine_status", machineStatus);
        intent.putExtra("machine_temperature", machineTemperature);
        intent.putExtra("machine_humidity", humidity);
        context.startActivity(intent);
    }
}
