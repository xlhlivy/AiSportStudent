package com.yelai.wearable.ui.course;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yelai.wearable.R;
import com.yelai.wearable.base.SoftKeyboardStateHelper;

/**
 * Created by showzeng on 17-8-11.
 * Email: kingstageshow@gmail.com
 * GitHub: https://github.com/showzeng
 */


public class CommentDialogFragment extends DialogFragment implements View.OnClickListener{



    public interface DialogFragmentDataCallback {

        String getCommentText();

        void setCommentText(String commentTextTemp);
    }

    private EditText commentEditText;
    private ImageView sendButton;
    private InputMethodManager inputMethodManager;
    private DialogFragmentDataCallback dataCallback;

    public void setCallback(DialogFragmentDataCallback callback){
        this.dataCallback = callback;
    }

//    @Override
//    public void onAttach(Context context) {
//        if (!(getActivity() instanceof DialogFragmentDataCallback)) {
//            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 DialogFragmentDataCallback 接口");
//        }
//        super.onAttach(context);
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_fragment_comment_layout);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams;
        if (window != null) {
            layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }

        commentEditText = (EditText) mDialog.findViewById(R.id.etComment);
        sendButton = (ImageView) mDialog.findViewById(R.id.btnSend);

        fillEditText();
        setSoftKeyboard();

        commentEditText.addTextChangedListener(mTextWatcher);

        sendButton.setOnClickListener(this);


        return mDialog;
    }

    private void fillEditText() {
//        dataCallback = (DialogFragmentDataCallback) getActivity();
        commentEditText.setText(dataCallback.getCommentText());
        commentEditText.setSelection(dataCallback.getCommentText().length());
        if (dataCallback.getCommentText().length() == 0) {
            sendButton.setEnabled(false);
            sendButton.setColorFilter(ContextCompat.getColor(getActivity(), R.color.iconCover));
        }
    }

    private void setSoftKeyboard() {
        commentEditText.setFocusable(true);
        commentEditText.setFocusableInTouchMode(true);
        commentEditText.requestFocus();

        //为 commentEditText 设置监听器，在 DialogFragment 绘制完后立即呼出软键盘，呼出成功后即注销
        commentEditText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    if (inputMethodManager.showSoftInput(commentEditText, 0)) {
                        commentEditText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() > 0) {
                sendButton.setEnabled(true);
                sendButton.setClickable(true);
                sendButton.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tab_text_selected));
            } else {
                sendButton.setEnabled(false);
                sendButton.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tab_text_unselected));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
//                Toast.makeText(getActivity(), commentEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                dataCallback.setCommentText(commentEditText.getText().toString());
                commentEditText.setText("");
                dismiss();
                break;
            default:
                break;
        }
    }

//    @Override
//    public void show(FragmentManager manager, String tag) {
//        super.show(manager, tag);
//        if(softKeyboardStateHelper == null){
//            softKeyboardStateHelper = new SoftKeyboardStateHelper(getActivity());
//        }
//
//        softKeyboardStateHelper.addSoftKeyboardStateListener(this);
//    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
//        softKeyboardStateHelper.removeSoftKeyboardStateListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
//        softKeyboardStateHelper.removeSoftKeyboardStateListener(this);
    }
}