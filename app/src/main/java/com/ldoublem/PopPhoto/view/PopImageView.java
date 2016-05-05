package com.ldoublem.PopPhoto.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ldoublem.PopPhoto.R;


public class PopImageView {

    private View vPopWindow;
    //	PhotoView iv_pop;
    private PopupWindow mpopWindow;
    private Context context;
    LinearLayout ly_image, ly_btns, ly_main;
    Button btn_changephoto, btn_cancel;
    TextView tv_info;
    Animation popup_enter_top;

    Animation popup_exit_top;

    Animation popup_enter_bottom;

    Animation popup_exit_bottom;

    boolean changePhoto = false;

    public PopImageView(Context context) {
        initPopWindow(context);
    }

    //	OnChangePhoto mOnChangePhoto;

    private void initPopWindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vPopWindow = inflater.inflate(R.layout.sport_pop_image, null, false);
        ly_image = (LinearLayout) vPopWindow.findViewById(R.id.ly_image);
        ly_btns = (LinearLayout) vPopWindow.findViewById(R.id.ly_btns);
        ly_main = (LinearLayout) vPopWindow.findViewById(R.id.ly_main);
        //		iv_pop = (PhotoView) vPopWindow.findViewById(R.id.pop_image);
        btn_changephoto = (Button) vPopWindow.findViewById(R.id.btn_changephoto);
        btn_cancel = (Button) vPopWindow.findViewById(R.id.btn_cancel);
        tv_info = (TextView) vPopWindow.findViewById(R.id.tv_info);
        this.context = context;

        popup_enter_top = AnimationUtils.loadAnimation(context, R.anim.push_up_in);
        popup_enter_bottom = AnimationUtils.loadAnimation(context, R.anim.push_bottom_in);

        popup_exit_top = AnimationUtils.loadAnimation(context, R.anim.push_up_out);
        popup_exit_bottom = AnimationUtils.loadAnimation(context, R.anim.push_bottom_out);

        popup_exit_top.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {

                ly_btns.post(new Runnable() {

                    @Override
                    public void run() {
                        if (mpopWindow != null)
                            mpopWindow.dismiss();
                    }
                });

            }
        });

    }


    @SuppressLint("NewApi")
    public void showPopWindow(View parent, int photoid, final OnChangePhoto mOnChangePhoto) {


        if (ly_image.getChildCount() > 0) {
            ly_image.removeAllViews();
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        LinearLayout.LayoutParams _llp = new LinearLayout.LayoutParams(width, width);
        ly_image.setLayoutParams(_llp);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        //		params.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
        ImageView iv_pop = new ImageView(context);
        iv_pop.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv_pop.setLayoutParams(params);
        ly_image.setBackgroundResource(photoid);
        ly_image.addView(iv_pop);

        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                closePop(false);

            }
        });

        btn_changephoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                closePop(true);

            }
        });

        mpopWindow = new PopupWindow(vPopWindow, width, height, true);

        mpopWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                if (changePhoto) {
                    if (mOnChangePhoto != null) {
                        mOnChangePhoto.OnChangePhotoClick();
                    }
                }
            }
        });


        mpopWindow.setTouchable(true);
        mpopWindow.setFocusable(false);//返回键捕捉  如果是true关闭pop false到activity onkeydown
        mpopWindow.setBackgroundDrawable(new ColorDrawable(0));
        mpopWindow.showAtLocation(parent, Gravity.TOP, 0, 0);
        ly_btns.startAnimation(popup_enter_bottom);
        ly_image.startAnimation(popup_enter_top);

    }

    public interface OnChangePhoto {
        void OnChangePhotoClick();
    }

    public boolean isShow() {
        if (mpopWindow != null)
            return mpopWindow.isShowing();
        return false;
    }

    public void closePop(boolean chp) {
        changePhoto = chp;
        ly_btns.startAnimation(popup_exit_bottom);
        ly_image.startAnimation(popup_exit_top);
    }

}
