package com.checheyun.vcm.VcmUiTest;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;

public class UiObjectDefine {
	//任务流返回
	UiObject rtnBtn_rwl=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(0)));
	//其他左上返回
	UiObject rtnBtn=new UiObject(new UiSelector().description("header_wrap").childSelector(new UiSelector().className("android.view.View").instance(0)));
	//头部接车按钮
	UiObject AddCarBtn=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(2)));
	//底部接车按钮
	UiObject AddCarBtnNew=new UiObject(new UiSelector().description("bottom_icon").childSelector(new UiSelector().className("android.view.View").instance(2))) ;
	//顶部任务/任务流切换View
	private UiObject Task2Flow=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(0)));
	//车辆页面的搜索按钮
	private UiObject searchBtn=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(0)));
	public UiObject getSearchBtn() {
		return searchBtn;
	}

	public UiObject getTask2Flow() {
		return Task2Flow;
	}

	public UiObject getAddCarBtnNew() {
		return AddCarBtnNew;
	}

	public UiObject getAddCarBtn() {
		return AddCarBtn;
	}
	

}
