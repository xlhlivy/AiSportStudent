package com.yelai.wearable.contract;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * Created by hr on 18/11/8.
 */

public interface HRIView<P> extends IView<P> {

    public void showError(String msg);

    public void showIndicator();

    public void hideIndicator();

}
