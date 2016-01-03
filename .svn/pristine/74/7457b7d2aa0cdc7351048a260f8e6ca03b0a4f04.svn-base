package com.bluemobi.cnpc.view;

/**
 * Created by wangzhijun on 2015/7/21.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.graphics.Color.argb;
import static android.graphics.Color.rgb;

import com.bluemobi.cnpc.util.Utils;

public final class AlertDialog {

    private Dialog dialog;
    private Context context;
    private LinearLayout contentLayout, buttonLayout;
    private int gray;
    private int dp15, dp10, dp20, dp100;
    private TextView messageTextView;
    private TextView rightButton, leftButton;
    private TextView titleView;
    private int contentHeight;

    public AlertDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dp15 = Utils.dip2px(context, 15);
        dp10 = Utils.dip2px(context, 10);
        dp20 = Utils.dip2px(context, 20);
        dp100 = Utils.dip2px(context, 100);
        gray = rgb(220, 220, 220);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        contentHeight = dp100;
    }

    public AlertDialog addView(View v) {
        if (contentLayout == null) {
            contentLayout = createContentLayout();
        }
        contentLayout.addView(v);
        return this;
    }


    public AlertDialog setTitle(String text) {
        if (titleView == null) {
            titleView = createTitleView(text);
        }
        titleView.setText(text);
        return this;
    }

    public void setCustomTextColor(int titleColor, int contentColor, int btnColor) {
        titleView.setTextColor(titleColor);
        messageTextView.setTextColor(contentColor);
        rightButton.setTextColor(btnColor);

    }

    public CharSequence getTitle() {
        return titleView.getText();
    }

    public AlertDialog setMessage(String message) {
        if (messageTextView == null) {
            messageTextView = new TextView(context);
            messageTextView.setPadding(dp20, dp10, dp20, dp10);
            messageTextView.setTextColor(context.getResources().getColor(
                    android.R.color.black));
        }
        messageTextView.setText(message);
        return this;
    }

    public AlertDialog setMessageFromHtml(String htmlMessage) {
        if (messageTextView == null) {
            messageTextView = new TextView(context);
            messageTextView.setPadding(dp20, dp10, dp20, dp10);
            messageTextView.setTextColor(context.getResources().getColor(
                    android.R.color.black));
        }
        messageTextView.setText(Html.fromHtml(htmlMessage));
        return this;
    }

    public CharSequence getMessage() {
        return messageTextView.getText();
    }

    public void show() {
        layout();

        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public AlertDialog setNegativeButtonClickListener(String text,
                                                      View.OnClickListener listener) {
        setNegativeButtonText(text);
        rightButton.setOnClickListener(listener);
        return this;
    }

    public AlertDialog setPositiveButtonClickListener(String text,
                                                      View.OnClickListener listener) {
        setPositiveButtonText(text);
        leftButton.setOnClickListener(listener);
        return this;
    }

    public AlertDialog setPositiveButtonText(String text) {
        if (leftButton == null) {
            leftButton = createButton(text);
        } else {
            leftButton.setText(text);
        }
        return this;
    }

    public AlertDialog setNegativeButtonText(String text) {
        if (rightButton == null) {
            rightButton = createButton(text);
        } else {
            rightButton.setText(text);
        }
        return this;
    }

    private View createVerticalDivider() {
        View divider = new View(context);
        LinearLayout.LayoutParams dividerParam = new LinearLayout.LayoutParams(
                1, LinearLayout.LayoutParams.MATCH_PARENT);
        divider.setBackgroundColor(gray);
        divider.setLayoutParams(dividerParam);
        return divider;
    }

    private View createHorizontalDivider() {
        View divider = new View(context);
        RelativeLayout.LayoutParams dividerParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(context, 1));
//		dividerParam.setMargins(dp10, 0, dp10, 0);
        divider.setBackgroundColor(gray);
        divider.setLayoutParams(dividerParam);
        return divider;
    }

    private TextView createButton(String text) {
        TextView button = new TextView(context);
        button.setPadding(0, dp15, 0, dp15);
        button.setText(text);
        button.setTextColor(context.getResources().getColor(android.R.color.black));
        button.setClickable(true);
        button.setGravity(Gravity.CENTER);
        StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{android.R.attr.state_pressed},
                new ColorDrawable(gray));
        sld.addState(new int[]{}, new ColorDrawable(argb(0, 0, 0, 0)));
        button.setBackgroundDrawable(sld);
        return button;
    }

    private TextView createTitleView(String text) {
        RelativeLayout.LayoutParams titleParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleView = new TextView(context);
        titleView.setId(10010);
        titleView.setPadding(dp20, dp15, dp20, dp15);
        titleView.setSingleLine();
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        titleView.setText(text);
        titleView.setTextColor(context.getResources().getColor(
                android.R.color.black));
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        titleView.setLayoutParams(titleParam);
        return titleView;
    }

    private LinearLayout createContentLayout() {
        contentLayout = new LinearLayout(context);
        contentLayout.setPadding(dp10, 0, dp10, 0);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setBackgroundColor(context.getResources().getColor(
                android.R.color.white));
        return contentLayout;
    }

    private boolean isContentLayoutEmpty() {
        return (messageTextView == null || messageTextView.getText().equals(""))
                && (contentLayout == null || (contentLayout != null && contentLayout
                .getChildCount() <= 0));
    }

    private void layout() {
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        rl.setBackgroundColor(context.getResources().getColor(
                android.R.color.white));
        View upperDivider = null;
        if (titleView != null) {
            upperDivider = createHorizontalDivider();
            upperDivider.setId(10011);
            RelativeLayout.LayoutParams upperParam = (android.widget.RelativeLayout.LayoutParams) upperDivider
                    .getLayoutParams();
            titleView.setGravity(Gravity.CENTER);
            upperParam.addRule(RelativeLayout.BELOW, titleView.getId());
        }

        if (contentLayout == null) {
            contentLayout = createContentLayout();
        }

        contentLayout.setId(10013);
        RelativeLayout.LayoutParams contentParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                contentHeight);
        if (titleView != null) {
            contentParam.addRule(RelativeLayout.BELOW, upperDivider.getId());
        } else {
            contentParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        }

        contentLayout.setLayoutParams(contentParam);
        contentLayout.setGravity(Gravity.CENTER);

        if (messageTextView != null) {
            RelativeLayout.LayoutParams temp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            temp.addRule(RelativeLayout.CENTER_IN_PARENT);
            messageTextView.setLayoutParams(temp);
            messageTextView.setGravity(Gravity.CENTER);
            contentLayout.removeAllViews();
            contentLayout.addView(messageTextView, 0);
        }
        View lowerDivider = null;
        if (!isContentLayoutEmpty()) {
            lowerDivider = createHorizontalDivider();
            lowerDivider.setId(10014);
            RelativeLayout.LayoutParams lowerParam = (android.widget.RelativeLayout.LayoutParams) lowerDivider
                    .getLayoutParams();
            lowerParam.addRule(RelativeLayout.BELOW, contentLayout.getId());
        }

        buttonLayout = new LinearLayout(context);
        buttonLayout.setId(10015);
        RelativeLayout.LayoutParams buttonParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (lowerDivider == null)
            buttonParam.addRule(RelativeLayout.BELOW, contentLayout.getId());
        else
            buttonParam.addRule(RelativeLayout.BELOW, lowerDivider.getId());
        buttonLayout.setLayoutParams(buttonParam);

        LinearLayout.LayoutParams rightButtonParam = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        if (rightButton == null) {
            rightButton = createButton("取消");
            rightButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }

            });
        }
        rightButtonParam.width = Utils.dip2px(context, 0);
        rightButtonParam.weight = 1;
        rightButton.setLayoutParams(rightButtonParam);
        buttonLayout.addView(rightButton);

        if (leftButton != null) {
            LinearLayout.LayoutParams leftButtonParam = new LinearLayout.LayoutParams(
                    rightButtonParam.width, rightButtonParam.height);
            leftButtonParam.width = Utils.dip2px(context, 0);
            leftButtonParam.weight = 1;
            leftButton.setLayoutParams(leftButtonParam);
            buttonLayout.addView(leftButton, 0);
            buttonLayout.addView(createVerticalDivider(), 1);
        }

        if (titleView != null)
            rl.addView(titleView);
        if (upperDivider != null)
            rl.addView(upperDivider);
        rl.addView(contentLayout);
        if (lowerDivider != null)
            rl.addView(lowerDivider);
        rl.addView(buttonLayout);
        dialog.setContentView(rl);
    }

    public boolean isShowing() {
        return dialog == null ? false : dialog.isShowing();
    }

    public void cancel() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    public void setCanceledOnTouchOutside(boolean b) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(b);
        }
    }

    public void setCancelable(boolean b) {
        dialog.setCancelable(b);
    }
}

