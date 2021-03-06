package com.example.a390project.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.a390project.DummyDatabase;
import com.example.a390project.FirebaseHelper;
import com.example.a390project.ListViewAdapters.MachineListViewAdapter;
import com.example.a390project.Model.Machine;
import com.example.a390project.R;

public class MachineFragment extends Fragment {

    //views

    //variables
    private List<Machine> machines;
    private FirebaseHelper firebaseHelper;

    public static final String TAG = "MachineFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.machine_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Populate the ArrayList machines
        //In further sprints, we would create a controller to fetch machines from firebase database
        //machines = new DummyDatabase().generateDummyMachines();

        //After populating the machines, populate the mMachineListView
        firebaseHelper = new FirebaseHelper();
        firebaseHelper.populateMachine(view, getActivity());
    }

}
