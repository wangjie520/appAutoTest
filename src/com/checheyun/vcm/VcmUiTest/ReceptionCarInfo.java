package com.checheyun.vcm.VcmUiTest;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

public class ReceptionCarInfo extends Reception {
	UiScrollable scroll=new UiScrollable(new UiSelector().className("android.widget.ScrollView"));

	public ReceptionCarInfo() throws UiObjectNotFoundException{
		carInfoTab.click();
	}
	public void setBrandAndSeries(String brand,String series) throws UiObjectNotFoundException{
		UiObject chooseBrandBtn=scroll.getChild(new UiSelector().className("android.widget.ImageView").instance(0));
		chooseBrandBtn.click();
		UiScrollable scrollchexin=new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
		UiObject cadillac=scrollchexin.getChildByText(new UiSelector().className("android.widget.TextView"),brand, true);
		cadillac.clickAndWaitForNewWindow();
		UiObject cadillacseriers=new UiObject(new UiSelector().text(series));
		cadillacseriers.clickAndWaitForNewWindow();
	}
	public void setVIN(String vin) throws UiObjectNotFoundException{
		UiObject vinText=scroll.getChild(new UiSelector().className("android.widget.EditText").instance(0));
		vinText.setText(vin);
		device.pressEnter();

	}
	public void setMileage(String mileage) throws UiObjectNotFoundException{
		UiObject miles=scroll.getChild(new UiSelector().className("android.widget.EditText").instance(1));
		miles.setText(mileage);
		device.pressEnter();
	}
	public void takeVinPic(){
		
	}
	public void setCustDemand(){
		
	}
	public void choosePackage(String checkPackage) throws UiObjectNotFoundException{
		UiObject checkPackageBtn=new UiObject(new UiSelector().text(checkPackage));
		if (checkPackageBtn.exists()){
			checkPackageBtn.click();
		}else{
			System.out.println("检测套餐不存在");
		}
	}
}
