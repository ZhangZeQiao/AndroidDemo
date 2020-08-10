package com.zzq.base.router.api;

import android.content.Context;

import com.zzq.base.router.IRouter;
import com.zzq.base.router.IRouterCallback;

public interface IHengRouter extends IRouter {
    String IMPL_CLASS_NAME = "com.zzq.heng.IHengRouterImpl";

    void startHengMainActivity(Context context, IRouterCallback iRouterCallback);
}
