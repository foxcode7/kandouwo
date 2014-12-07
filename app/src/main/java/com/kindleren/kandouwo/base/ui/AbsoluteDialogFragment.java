package com.kindleren.kandouwo.base.ui;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.kindleren.kandouwo.R;

import roboguice.fragment.RoboDialogFragment;

/**
 * Created by foxcoder on 14-12-7.
 */
public class AbsoluteDialogFragment extends RoboDialogFragment {
    public static final String ARG_TAG_POPUP = "popup";
    public static final String ARG_ANIMATION = "animation";
    protected String popupName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.App_NoTitleBar);
        if (getArguments() != null) {
            popupName = getArguments().getString(ARG_TAG_POPUP);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        windowDeploy(dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getShowsDialog() && showWithPadding()) {
            int padding = (int) (8 * getResources().getDisplayMetrics().density);
            view.setPadding(padding, 0, padding, 0);
        }
    }

    protected boolean showWithPadding() {
        return false;
    }

    public void removeSelf() {
        if (getDialog() != null) {
            dismissAllowingStateLoss();
        } else {
            if (!TextUtils.isEmpty(popupName)) {
                getFragmentManager().popBackStack();
            }

            getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();

            if (getParentFragment() instanceof AbsoluteDialogFragment) {
                ((AbsoluteDialogFragment) getParentFragment()).removeSelf();
            }
        }
    }

    /**
     * 设置窗口显示
     *
     * @param dialog
     */
    public void windowDeploy(Dialog dialog) {
        Window window = dialog.getWindow(); // 得到对话框
        window.setWindowAnimations(getArguments().containsKey(ARG_ANIMATION) ? getArguments().getInt(ARG_ANIMATION) : R.style.push_top); // 设置窗口弹出动画
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = getArguments() == null ? 0 : getArguments().getInt("x");
        wl.y = getArguments() == null ? 0 : getArguments().getInt("y");
        int activityHeight = getActivity().getWindow().getDecorView().getHeight();
        wl.width = getArguments() != null && getArguments().containsKey("width") ? getArguments().getInt("width") : ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = getArguments() != null && getArguments().containsKey("height") ? getArguments().getInt("height") : Math.min(activityHeight - wl.y, (int) (0.6 * activityHeight));
        wl.gravity = getArguments() != null && getArguments().containsKey("gravity") ? getArguments().getInt("gravity") : Gravity.LEFT | Gravity.TOP;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        wl.dimAmount = 0.6f;
        window.setAttributes(wl);
    }
}
