package com.example.a390project.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a390project.FirebaseHelper;
import com.example.a390project.R;

@SuppressLint("ValidFragment")
public class ProjectGraphFragment extends Fragment {

    private String projectPO;

    public ProjectGraphFragment(String projectPO) {
        this.projectPO = projectPO;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.project_graph_fragment, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // setup the adapters and make the height of the list views dynamic

        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.populateGraphNames(getActivity(),view,projectPO);
        //firebaseHelper.populateGraphs(getActivity(),view, projectPO);
    }
}
