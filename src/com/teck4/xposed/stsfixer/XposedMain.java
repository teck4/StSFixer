package com.teck4.xposed.stsfixer;

import static de.robv.android.xposed.XposedHelpers.findMethodExact;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.Intent;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedMain implements IXposedHookLoadPackage {

	private static Method mOnStartCommand;
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {		
		if (lpparam.packageName.equals("com.shootingstar067") == false)
			return;

		mOnStartCommand = findMethodExact("com.shootingstar067.service.AvatarCollectionService", lpparam.classLoader, "onStartCommand", Intent.class, int.class, int.class);

		XposedBridge.hookMethod(mOnStartCommand, new XC_MethodReplacement() {

			@Override
			protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
				
				if (param.args[0] == null) {
					return Service.START_STICKY_COMPATIBILITY;
				}

				return XposedBridge.invokeOriginalMethod(mOnStartCommand, param.thisObject, param.args);
			}
			
		});
		
	}

}
