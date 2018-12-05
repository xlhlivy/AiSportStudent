package com.yelai.wearable.base;

import com.yelai.wearable.model.Page;

import java.util.List;

/**
 * Created by hr on 18/11/7.
 */

public interface ViewPage<D> {

    public void list(Page<List<D>> data);

}
