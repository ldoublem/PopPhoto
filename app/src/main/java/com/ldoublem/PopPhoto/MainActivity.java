package com.ldoublem.PopPhoto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ldoublem.PopPhoto.view.PopImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    PopImageView mPopImageView;
    Button btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_click = (Button) findViewById(R.id.btn_click);
        btn_click.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_click:
                if (mPopImageView == null)
                    mPopImageView = new PopImageView(MainActivity.this);
                mPopImageView.showPopWindow(v, R.mipmap.photo, new PopImageView.OnChangePhoto() {

                    @Override
                    public void OnChangePhotoClick() {
                        Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_LONG).show();

                    }
                });


                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && mPopImageView != null && mPopImageView.isShow()) { //按下的如果是BACK，同时没有重复
            mPopImageView.closePop(false);
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
}
