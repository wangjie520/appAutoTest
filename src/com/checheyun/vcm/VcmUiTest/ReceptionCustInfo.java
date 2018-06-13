package com.checheyun.vcm.VcmUiTest;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class ReceptionCustInfo extends Reception{
	public ReceptionCustInfo() throws UiObjectNotFoundException{
		custInfoTab.click();
	}
	public void setCustName(String custname) throws UiObjectNotFoundException{
		UiObject custNameText=new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
		custNameText.setText(Utf7ImeHelper.e(custname));
		device.pressEnter();
	}
	public void setPhoneNumber(String phoneNumber) throws UiObjectNotFoundException{
		UiObject phoneNumberText=new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
		phoneNumberText.setText(Utf7ImeHelper.e(phoneNumber));
		device.pressEnter();
	}
	public void setSex(String sex) throws UiObjectNotFoundException{
		//F represent female,others for male
		UiObject male=new UiObject(new UiSelector().text("先生"));
		UiObject female=new UiObject(new UiSelector().text("女士"));
		if(sex.equals("F")){
			female.click();
		}else{
			male.click();
		}

		
	}
}
