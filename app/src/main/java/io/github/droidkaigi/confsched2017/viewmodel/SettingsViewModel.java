package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.BuildConfig;
import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.pref.DefaultPrefs;

public final class SettingsViewModel extends BaseObservable implements ViewModel {

    private Callback callback;

    private final Context context;

    private final DefaultPrefs defaultPrefs;

    @Inject
    SettingsViewModel(AppCompatActivity context, DefaultPrefs defaultPrefs) {
        this.context = context;
        this.defaultPrefs = defaultPrefs;
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public boolean shouldNotify() {
        return defaultPrefs.getNotificationFlag();
    }

    public boolean isHeadsUp() {
        return defaultPrefs.getHeadsUpFlag();
    }

    public boolean shouldShowLocalTime() {
        return defaultPrefs.getShowLocalTimeFlag();
    }

    public int getShowHeadsUpSettingVisibility() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public void onCheckedHeadsUpSetting(boolean isChecked) {
        defaultPrefs.putHeadsUpFlag(isChecked);
    }

    public void onCheckedShowLocalTimeSetting(boolean isChecked) {
        defaultPrefs.putShowLocalTimeFlag(isChecked);
    }

    public void onCheckedNotificationSetting(boolean isChecked) {
        defaultPrefs.putNotificationFlag(isChecked);
        if (callback != null) {
            callback.changeHeadsUpEnabled(isChecked);
        }
    }

    public String getDisplayVersion() {
        return context.getString(R.string.settings_display_version, BuildConfig.VERSION_NAME, BuildConfig.GIT_SHA);
    }

    @Override
    public void destroy() {
        this.callback = null;
    }

    public interface Callback {

        void changeHeadsUpEnabled(boolean enabled);
    }
}
