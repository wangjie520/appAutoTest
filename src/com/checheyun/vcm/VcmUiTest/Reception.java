package com.checheyun.vcm.VcmUiTest;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class Reception extends PublicPageUi {
	UiDevice device=UiDevice.getInstance();
	UiObject carInfoTab=new UiObject(new UiSelector().text("车辆信息"));
	UiObject custInfoTab=new UiObject(new UiSelector().text("客户信息"));
	UiObject CredInfoTab=new UiObject(new UiSelector().text("证件信息"));
	
	public void startCheck() throws UiObjectNotFoundException{
		UiObject startCheckBtn=new UiObject(new UiSelector().description("vc_form_confirm"));
		startCheckBtn.click();
	}
	

	
}
