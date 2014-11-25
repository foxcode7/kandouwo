package com.kindleren.kandouwo.settings;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.google.inject.Inject;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.utils.BrightnessUtils;
import com.kindleren.kandouwo.utils.SetAndGetDataUtils;
import com.kindleren.kandouwo.widget.SwitchButton;

/**
 * Created by foxcoder on 14-11-24.
 */
public class SettingsFragment extends BaseFragment {
    @Inject
    private LayoutInflater inflater;

    private SeekBar screenSeekBar;
    private SwitchButton followScreenLightSwitchBtn;
    private int brightness;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        screenSeekBar = (SeekBar)view.findViewById(R.id.screen_light_seekbar);
        screenSeekBar.setOnSeekBarChangeListener(mSrceenSeekChange);
        initScreenSeekBar();

        followScreenLightSwitchBtn = (SwitchButton)view.findViewById(R.id.follow_screen_light_switch_btn);
        followScreenLightSwitchBtn.setOnChangeListener(new SwitchButton.OnChangeListener() {
            @Override
            public void onChange(SwitchButton sb, boolean state) {
                if(state){//根据开关状态来设置是否跟随系统亮度
                    BrightnessUtils.startAutoBrightness(getActivity());
                    screenSeekBar.setEnabled(false);
                } else {
                    BrightnessUtils.stopAutoBrightness(getActivity());
                    screenSeekBar.setEnabled(true);
                }
            }
        });

        return view;
    }

    private void initScreenSeekBar(){
        if(BrightnessUtils.isAutoBrightness(getActivity())){
            BrightnessUtils.stopAutoBrightness(getActivity());
        }

        String lightWeight = SetAndGetDataUtils.GetData(getActivity(), "settings", "light");
        if(!"".equals(lightWeight)){
            brightness = Integer.valueOf(lightWeight);
        } else {
            brightness = BrightnessUtils.getScreenBrightness(getActivity());
        }
        BrightnessUtils.setBrightness(getActivity(), brightness);
        screenSeekBar.setProgress(brightness);
    }

    /**
     * 屏幕亮度调节SeekBar的OnChange事件监听
     */
    private SeekBar.OnSeekBarChangeListener mSrceenSeekChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int curProgress = seekBar.getProgress();// 得到当前进度值
            // 当进度小于30时，设置成30，防止太黑看不见的情况。
            if (curProgress < 30) {
                curProgress = 30;
            }
            // 根据当前进度改变屏幕亮度
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, curProgress);
            curProgress = Settings.System.getInt(getActivity().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, -1);
            BrightnessUtils.setBrightness(getActivity(),
                    curProgress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            brightness = seekBar.getProgress();
            seekBar.setProgress(brightness);
            SetAndGetDataUtils.SetData(getActivity().getApplicationContext(), "settings",
                    "light", brightness + "");
        }
    };

}
