package com.zzq.heng;

import android.content.Context;
import android.content.Intent;

import com.zzq.base.router.api.IHengRouter;
import com.zzq.base.router.IRouterCallback;

public class IHengRouterImpl implements IHengRouter {

    @Override
    public void init() {

    }

    @Override
    public void startHengMainActivity(Context context, IRouterCallback iRouterCallback) {
        context.startActivity(new Intent(context, HengMainActivity.class));
        iRouterCallback.complete("HengMainActivity.class");
    }
}
