package com.heima.takeout31.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.heima.takeout31.R;
import com.heima.takeout31.model.dao.RecepitAddressBean;
import com.heima.takeout31.model.dao.RecepitAddressDao;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2016/12/11.
 */
public class AddRecepitAddressActivity extends AppCompatActivity {
    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.ib_delete)
    ImageButton mIbDelete;
    @InjectView(R.id.tv_name)
    TextView mTvName;
    @InjectView(R.id.et_name)
    EditText mEtName;
    @InjectView(R.id.rb_man)
    RadioButton mRbMan;
    @InjectView(R.id.rb_women)
    RadioButton mRbWomen;
    @InjectView(R.id.rg_sex)
    RadioGroup mRgSex;
    @InjectView(R.id.et_phone)
    EditText mEtPhone;
    @InjectView(R.id.ib_delete_phone)
    ImageButton mIbDeletePhone;
    @InjectView(R.id.ib_add_phone_other)
    ImageButton mIbAddPhoneOther;
    @InjectView(R.id.et_phone_other)
    EditText mEtPhoneOther;
    @InjectView(R.id.ib_delete_phone_other)
    ImageButton mIbDeletePhoneOther;
    @InjectView(R.id.rl_phone_other)
    RelativeLayout mRlPhoneOther;
    @InjectView(R.id.et_receipt_address)
    EditText mEtReceiptAddress;
    @InjectView(R.id.et_detail_address)
    EditText mEtDetailAddress;
    @InjectView(R.id.tv_label)
    TextView mTvLabel;
    @InjectView(R.id.ib_select_label)
    ImageView mIbSelectLabel;
    @InjectView(R.id.bt_ok)
    Button mBtOk;
    private RecepitAddressDao mAddressDao;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;
    private RecepitAddressBean mAddressBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_receipt_address);
        ButterKnife.inject(this);
        processIntent();
        mAddressDao = new RecepitAddressDao(this);
        //edittext焦点问题
        mEtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //有焦点
                } else {
                    //失去焦点时隐藏软键盘
                    hideSoftKeyboard();
                }
            }
        });

        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mIbDeletePhone.setVisibility(View.GONE);
                } else {
                    mIbDeletePhone.setVisibility(View.VISIBLE);
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void processIntent() {
        if(getIntent()!=null){
            mAddressBean = (RecepitAddressBean) getIntent().getSerializableExtra("addressBean");
            if(mAddressBean!=null){
                //修改地址
                mTvTitle.setText("修改地址");
                mIbDelete.setVisibility(View.VISIBLE);
                mIbDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAddress();
                    }
                });

                mEtName.setText(mAddressBean.name);
                if("女士".equals(mAddressBean.sex)){
                    mRbWomen.setChecked(true);
                }else{
                    mRbMan.setChecked(true);
                }
                mEtPhone.setText(mAddressBean.phone);
                mEtPhoneOther.setText(mAddressBean.phoneOther);
                mEtReceiptAddress.setText(mAddressBean.address);
                mEtDetailAddress.setText(mAddressBean.detailAddress);
            }
        }
    }

    private void deleteAddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除地址？");
        builder.setPositiveButton("是的，我已高新就业离校", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAddressDao.deleteRecepitAddress(mAddressBean);
                finish();
            }
        });
        builder.setNegativeButton("不，我还要在黑马奋斗，需要叫外卖", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0); //强制隐藏键盘
    }

    @OnClick({R.id.ib_back, R.id.ib_delete, R.id.ib_delete_phone, R.id.ib_add_phone_other,
            R.id.ib_delete_phone_other, R.id.tv_label, R.id.bt_ok, R.id.ib_select_label,R.id.et_detail_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_delete:
                break;
            case R.id.ib_delete_phone:
                mEtPhone.setText("");
                break;
            case R.id.ib_add_phone_other:
                //增加备用电话
                mRlPhoneOther.setVisibility(View.VISIBLE);
                mIbDeletePhoneOther.setVisibility(View.VISIBLE);
                break;
            case R.id.ib_delete_phone_other:
                mEtPhoneOther.setText("");
                mRlPhoneOther.setVisibility(View.GONE);
                break;
            case R.id.ib_select_label:
                showLabel();
                break;
            case R.id.bt_ok:
                boolean isOk = checkReceiptAddressInfo();
                if (isOk) {
                    if(mAddressBean == null) {
                        //保存到sqlite
                        saveRecepitAddressBean();
                    }else{
                        updateRecepitAddressBean();
                    }
                } else {
                    Toast.makeText(this, "填写地址要认真点！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.et_detail_address:
                goMapActivity();
                break;
        }
    }

    private void goMapActivity() {
        Intent intent = new Intent(this, AroundAddressActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
            String title = data.getStringExtra("title");
            String snippet = data.getStringExtra("snippet");
            mEtReceiptAddress.setText(title);
            mEtDetailAddress.setText(snippet);
        }
    }

    private void updateRecepitAddressBean() {
        String name = mEtName.getText().toString().trim();
        String sex = "女士";
        if (mRbMan.isChecked()) {
            sex = "先生";
        }
        String phone = mEtPhone.getText().toString().trim();
        String phoneOther = mEtPhoneOther.getText().toString().trim();
        String address = mEtReceiptAddress.getText().toString().trim();
        String detailAddress = mEtDetailAddress.getText().toString().trim();
        String label = mTvLabel.getText().toString().trim();

        mAddressBean.name = name;
        mAddressBean.sex = sex;
        mAddressBean.phoneOther = phoneOther;
        mAddressBean.phone = phone;
        mAddressBean.detailAddress = detailAddress;
        mAddressBean.address = address;
        mAddressBean.label = label;
        mAddressDao.updateRecepitAddress(mAddressBean);

        finish();
    }

    private void saveRecepitAddressBean() {
        String name = mEtName.getText().toString().trim();
        String sex = "女士";
        if (mRbMan.isChecked()) {
            sex = "先生";
        }
        String phone = mEtPhone.getText().toString().trim();
        String phoneOther = mEtPhoneOther.getText().toString().trim();
        String address = mEtReceiptAddress.getText().toString().trim();
        String detailAddress = mEtDetailAddress.getText().toString().trim();
        String label = mTvLabel.getText().toString().trim();
        RecepitAddressBean recepitAddressBean = new RecepitAddressBean(9999, name, sex, phone, phoneOther,
                address, detailAddress, label, 31);
        mAddressDao.addRecepitAddress(recepitAddressBean);

        finish();
    }

    String[] mTitles = new String[]{"无", "家", "学校", "公司"};
    String[] bgColors = new String[]{"#778899", "#33ffdd", "#2299aa", "#ee7744"};

    private void showLabel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择在哪叫外卖");
        builder.setItems(mTitles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTvLabel.setText(mTitles[which]);
                mTvLabel.setTextColor(Color.BLACK);
                mTvLabel.setBackgroundColor(Color.parseColor(bgColors[which]));
            }
        });
        builder.show();
    }

    public boolean checkReceiptAddressInfo() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        String receiptAddress = mEtReceiptAddress.getText().toString().trim();
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请填写收获地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        String address = mEtDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isMobileNO(String phone) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return phone.matches(telRegex);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddRecepitAddress Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }
}
