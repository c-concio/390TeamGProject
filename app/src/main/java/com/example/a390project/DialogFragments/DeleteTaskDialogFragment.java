package com.example.a390project.DialogFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a390project.FirebaseHelper;
import com.example.a390project.R;

@SuppressLint("ValidFragment")
public class DeleteTaskDialogFragment extends DialogFragment {

    private TextView mDeleteTaskTitle;
    private Button Yesbutton;
    private Button Nobutton;

    private String projectPO;
    private String taskID;
    private String taskTitle;



    public DeleteTaskDialogFragment(String taskID, String projectPO, String taskTitle) {
        this.taskID = taskID;
        this.projectPO = projectPO;
        this.taskTitle = taskTitle;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_task_dialog_fragment, container, false);
        mDeleteTaskTitle = view.findViewById(R.id.delete_task_title);
        Yesbutton = view.findViewById(R.id.Yes_button);
        Nobutton = view.findViewById(R.id.No_button);

        mDeleteTaskTitle.setText("Delete " + taskTitle);

        Yesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseHelper FBHelper = new FirebaseHelper();
                FBHelper.deleteTask(taskID, projectPO);
                getDialog().dismiss();
            }
        });
        Nobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;

    }
}
