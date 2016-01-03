package com.bluemobi.cnpc.app;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by liufy on 2015/6/23.
 */
public class CnpcActivityManager
{
    public static CnpcActivityManager instance;

    private Stack<Activity> activityStack;// activityջ

    private CnpcActivityManager()
    {
    }

    public static CnpcActivityManager getInstance()
    {
        if (instance == null)
        {
            instance = new CnpcActivityManager();
        }
        return instance;
    }


    public void pushOneActivity(Activity actvity)
    {
        if (activityStack == null)
        {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
    }

    public Activity getLastActivity()
    {
        return activityStack.lastElement();
    }

    public void popOneActivity(Activity activity)
    {
        if (activityStack != null && activityStack.size() > 0)
        {
            if (activity != null)
            {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
                activityStack.remove(activity);
                activity = null;
            }

        }
    }


    public void finishAllActivity()
    {
        if (activityStack != null)
        {
            while (activityStack.size() > 0)
            {
                Activity activity = getLastActivity();
                if (activity == null)
                    break;
                popOneActivity(activity);
            }
        }
    }

    public void popAllActivityExceptOne(Object obj)
    {
        if (activityStack != null)
        {
            for (int i = 0, isize = activityStack.size(); i < isize; i++)
            {
                Activity act = activityStack.get(i);
                if (act == null)
                {
                    continue;
                }
                if (act.toString().equals(obj.toString()))
                    continue;
                act.finish();
            }
        }
    }

    /**
     * 注销登录
     */

    public void popAllActivityExceptOne()
    {
        if (activityStack != null)
        {
            for (int i = 0, isize = activityStack.size(); i < isize; i++)
            {
                Activity act = activityStack.get(i);
                if (act == null)
                {
                    continue;
                }
                act.finish();
            }
        }
    }
}
