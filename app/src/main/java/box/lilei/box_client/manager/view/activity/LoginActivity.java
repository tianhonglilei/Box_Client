package box.lilei.box_client.manager.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import box.lilei.box_client.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.login_edit_pwd)
    EditText loginEditPwd;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_cancel)
    Button loginCancel;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);
        ButterKnife.bind(this);
        mContext = this;



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                break;
            case R.id.login_cancel:
                finish();
                break;
        }
    }
}
