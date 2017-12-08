package com.zhang.box.manager.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zhang.box.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavExitManagerFragment extends Fragment implements View.OnClickListener{


    @BindView(R.id.exit_manager_btn)
    Button exitManagerBtn;
    Unbinder unbinder;
    Context mContext;

    public NavExitManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_exit_manager, container, false);
        mContext = getContext();
        unbinder = ButterKnife.bind(NavExitManagerFragment.this, view);

        exitManagerBtn.setOnClickListener(this);

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
            case R.id.exit_manager_btn:
                getActivity().setResult(2);
                getActivity().finish();
                break;
        }
    }
}
