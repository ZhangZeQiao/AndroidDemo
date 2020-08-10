package com.zzq.base.router;

import com.zzq.base.router.api.IHengRouter;

import java.util.HashMap;
import java.util.Map;

public class RouterManager {

    private volatile static RouterManager mInstance;
    private Map<String, IRouter> mRouterMap;

    private RouterManager() {
        mRouterMap = new HashMap<>();
    }

    public static RouterManager getInstance() {
        if (mInstance == null) {
            synchronized (RouterManager.class) {
                if (mInstance == null) {
                    mInstance = new RouterManager();
                }
            }
        }
        return mInstance;
    }

    // ---------------------------------------------------

    public IHengRouter getHengRouter() {
        return getRouter(IHengRouter.IMPL_CLASS_NAME);
    }

    // ---------------------------------------------------

    private <T extends IRouter> T getRouter(String implClassName) {
        IRouter iRouter = mRouterMap.get(implClassName);
        if (iRouter == null) {
            try {
                Class<?> clz = Class.forName(implClassName);
                T instance = (T) clz.newInstance();
                mRouterMap.put(implClassName, instance);
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) iRouter;
    }

}
