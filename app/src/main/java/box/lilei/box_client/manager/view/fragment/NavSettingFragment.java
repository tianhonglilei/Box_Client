package box.lilei.box_client.manager.view.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxParams;
import box.lilei.box_client.loading.ZLoadingDialog;
import box.lilei.box_client.loading.Z_TYPE;
import box.lilei.box_client.manager.presenter.NavSettingPresenter;
import box.lilei.box_client.manager.presenter.impl.NavSettingPresenterImpl;
import box.lilei.box_client.manager.view.NavSettingFragmentView;
import box.lilei.box_client.util.SharedPreferencesUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavSettingFragment extends Fragment implements View.OnClickListener ,NavSettingFragmentView{

    @BindView(R.id.nav_setting_btn_ok)
    Button navSettingBtnOk;
    @BindView(R.id.setting_txt_now_time)
    TextView settingTxtNowTime;
    Unbinder unbinder;
    private Spinner spinnerStartTime;
    private Spinner spinnerEndTime;
    private View view;
    private Context mContext;
    private ZLoadingDialog dialog;

    private NavSettingPresenter navSettingPresenter;


    public NavSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nav_setting, container, false);
        unbinder = ButterKnife.bind(NavSettingFragment.this, view);
        mContext = getContext();
        initView();

        navSettingPresenter = new NavSettingPresenterImpl(mContext,this);


        return view;
    }

    private void initView() {
        spinnerStartTime = (Spinner) view.findViewById(R.id.setting_sp_start_time);
        spinnerStartTime.setAdapter(new ArrayAdapter<>(
                mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setting_time_String_array)));
        spinnerEndTime = (Spinner) view.findViewById(R.id.setting_sp_end_time);
        spinnerEndTime.setAdapter(new ArrayAdapter<>(
                mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setting_time_String_array)));

        navSettingBtnOk.setOnClickListener(this);
        String lightTime = SharedPreferencesUtil.getString(mContext, BoxParams.LIGHT_TIME);
        if (lightTime.length()==8) {
            String start = lightTime.substring(0, 2) + ":" + lightTime.substring(2, 4);
            String end = lightTime.substring(4, 6) + ":" + lightTime.substring(6);
            settingTxtNowTime.setText(start + "-" + end);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_setting_btn_ok:
                navSettingPresenter.setLightTime(spinnerStartTime.getSelectedItem().toString(),spinnerEndTime.getSelectedItem().toString());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showDialog(String text) {
        dialog = new ZLoadingDialog(mContext);
        dialog.setLoadingBuilder(Z_TYPE.TEXT)
                .setLoadingColor(Color.parseColor("#ff5307"))
                .setHintText(text)
                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.parseColor("#525252"))  // 设置字体颜色
                .setCanceledOnTouchOutside(false)
                .show();
    }

    @Override
    public void hiddenDialog() {
        if (dialog!=null){
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void setResult(boolean set) {
        if (set){
            handler.sendEmptyMessage(1);
        }else{
            handler.sendEmptyMessage(2);
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "设置失败，请重新设置", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



















}
