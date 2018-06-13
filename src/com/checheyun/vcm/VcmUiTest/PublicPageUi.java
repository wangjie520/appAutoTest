package com.checheyun.vcm.VcmUiTest;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class PublicPageUi {
	UiObject header=new UiObject(new UiSelector().description("header_wrap")) ;
	
	
	public void getReturn() throws UiObjectNotFoundException{
		UiObject rtnBtn=header.getChild(new UiSelector().className("android.view.View").instance(0));
		rtnBtn.click();
	}
	public String getTitle() throws UiObjectNotFoundException{
		UiObject titleView=header.getChild(new UiSelector().className("android.widget.TextView").instance(0));
		String title=titleView.getText();
		return title;
	}
	
}
