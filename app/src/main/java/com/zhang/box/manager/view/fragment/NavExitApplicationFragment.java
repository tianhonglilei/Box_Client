package com.zhang.box.manager.view.fragment;


import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zhang.box.R;
import com.zhang.box.application.BaseApplication;
import com.zhang.box.service.HeartService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavExitApplicationFragment extends Fragment implements View.OnClickListener{


    @BindView(R.id.exit_app_btn)
    Button exitAppBtn;
    Unbinder unbinder;

    public NavExitApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_exit_application, container, false);
        unbinder = ButterKnife.bind(NavExitApplicationFragment.this, view);

        exitAppBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_app_btn:
                BaseApplication.exitAllActivity();
                break;
        }
    }
}
