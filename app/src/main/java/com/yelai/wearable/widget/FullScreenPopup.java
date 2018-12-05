package com.yelai.wearable.widget;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.yelai.wearable.R;

import razerdp.basepopup.BasePopupWindow;


public class FullScreenPopup extends BasePopupWindow {

        public FullScreenPopup(Activity context, String title, String content, String btn, final View.OnClickListener listener) {
            super(context);
            /**全屏popup*/
            setPopupWindowFullScreen(true);
            setBlurBackgroundEnable(true);

            this.mDismissView.setOnClickListener(null);


            if(!(title == null || title.length() == 0)){
                TextView tvTitle = (TextView)findViewById(R.id.hit);
                TextView tvContent = (TextView)findViewById(R.id.title);
                TextView tvBtn = (TextView)findViewById(R.id.tv_create);

                tvTitle.setText(title);
                tvContent.setText(content);
                tvBtn.setText(btn);


                if(content.length() == 0){
                    tvContent.setVisibility(View.GONE);
                }

            }

            findViewById(R.id.tv_create).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if(listener != null)listener.onClick(view);
                }
            });

        }

        public void setTitleGravity(int gravity){
            TextView tvTitle = (TextView)findViewById(R.id.hit);
            tvTitle.setGravity(gravity);
        }

        public void setContentGravity(int gravity){
            TextView textView = (TextView) findViewById(R.id.title);
            textView.setGravity(gravity);
//            textView.setGravity(Gravity.CENTER);
//
//            textView.setGravity(Gravity.LEFT);

        }

        @Override
        protected Animation initShowAnimation() {
            return null;
        }

        @Override
        public Animator initShowAnimator() {
            AnimatorSet set;
            set = new AnimatorSet();
            ObjectAnimator transAnimator = ObjectAnimator.ofFloat(mAnimaView, "translationY", 250, 0).setDuration(300);
            transAnimator.setInterpolator(new OverShootInterpolator());
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mAnimaView, "alpha", 0.4f, 1).setDuration(250 * 3 / 2);
            set.playTogether(transAnimator, alphaAnimator);
            return set;
        }

        @Override
        public Animator initExitAnimator() {
            AnimatorSet set;
            set = new AnimatorSet();
            ObjectAnimator transAnimator = ObjectAnimator.ofFloat(mAnimaView, "translationY", 0, 250).setDuration(300);
            transAnimator.setInterpolator(new OverShootInterpolator(-6));
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mAnimaView, "alpha", 1f, 0).setDuration(300);
            set.playTogether(transAnimator, alphaAnimator);
            return set;
        }

        @Override
        public boolean onBackPressed() {
            return false;
//        return super.onBackPressed();
        }

        @Override
        public View getClickToDismissView() {
            return getPopupWindowView();
        }

        @Override
        public View onCreatePopupView() {
            return createPopupById(R.layout.popup_customer_new_fullscreen);
        }

        @Override
        public View initAnimaView() {
            return findViewById(R.id.popup_anima);
        }
    }