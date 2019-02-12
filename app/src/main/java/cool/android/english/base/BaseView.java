package cool.android.english.base;

import android.app.Activity;

public interface BaseView<T> {

    void setPresenter(T presenter);

    Activity getActivityContext();

}
