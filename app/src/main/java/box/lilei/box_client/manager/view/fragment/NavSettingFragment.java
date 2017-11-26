package box.lilei.box_client.manager.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import box.lilei.box_client.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavSettingFragment extends Fragment implements View.OnClickListener{

    private Spinner spinnerStartTime;
    private Spinner spinnerEndTime;
    private View view;
    private Context mContext;


    public NavSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nav_setting, container, false);
        mContext = getContext();
        initView();






        // Inflate the layout for this fragment
        return view;
    }

    private void initView() {
        spinnerStartTime = (Spinner) view.findViewById(R.id.setting_sp_start_time);
        spinnerStartTime.setAdapter(new ArrayAdapter<>(
                mContext,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.setting_time_String_array)));
        spinnerEndTime = (Spinner) view.findViewById(R.id.setting_sp_end_time);
        spinnerEndTime.setAdapter(new ArrayAdapter<>(
                mContext,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.setting_time_String_array)));

    }

    @Override
    public void onClick(View v) {
        
    }
}
