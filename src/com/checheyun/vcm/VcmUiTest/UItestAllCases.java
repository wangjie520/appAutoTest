package com.checheyun.vcm.VcmUiTest;

//import java.util.Random;

import java.io.IOException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class UItestAllCases extends UiAutomatorTestCase {
	static int passCase=0;//用于统计通过用例数
	static int failCase=0;//用于统计失败用例数
	UiDevice device=UiDevice.getInstance();
	public static void main(String[] args){
		
		String android_id = "1";
		 //android list target id
		 String jar_name = "UItestAllCases";
		 //生成jar的名字
		 String test_class = "com.checheyun.vcm.VcmUiTest.UItestAllCases";
		 String test_name = "testJieChe3";
		 //方法名
		 new UiAutomatorHelper(jar_name,test_class,test_name,android_id);
		 
		 System.out.println("本次执行用例通过数:"+passCase);
		 System.out.println("本次执行用例失败数:"+failCase);
		 
	}
	//用于实现正常登录
	public void vcmlogginachieve() throws UiObjectNotFoundException{
		//结束进程
		try{
		Runtime.getRuntime().exec("am force-stop com.checheyun.vcm").waitFor();
		System.out.println("进程已结束");
		}catch(IOException e){
			e.printStackTrace();
		} catch (InterruptedException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
		//启动车况大师
		/*try {
			Runtime.getRuntime().exec("am start -n com.checheyun.vcm.MainActivity");
		} catch (IOException m) {
			// TODO Auto-generated catch block
			m.printStackTrace();
		}*/
		
		
		UiObject obj1= new UiObject(new UiSelector().text("车况大师"));
		obj1.clickAndWaitForNewWindow();
		UiObject titile= new UiObject(new UiSelector().description("header_wrap").childSelector(new UiSelector().className("android.widget.TextView")));
		if (titile.exists()){
		String strlg=titile.getText();
		System.out.println(strlg);
		if (strlg.equals("登录")){
			UiObject UsrNamebtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
			//UsrNamebtn.click();
			UsrNamebtn.clearTextField();
			UsrNamebtn.setText("15261180520");
			
			UiObject pwdBtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
			//pwdBtn.click();
			pwdBtn.clearTextField();
			pwdBtn.setText("123456");
			UiObject logginbtn= new UiObject(new UiSelector().description("login_btn"));
			logginbtn.clickAndWaitForNewWindow();
		}
		}
		else System.out.println("无需登录");
		//点击左下角任务
		
		
	}
	
	//用于测试登录的场景和退出登录
	public void testLoggin() throws UiObjectNotFoundException{
		// TODO Auto-generated constructor stub
		//初始化退出，避免session登录
		vcmlogginachieve();
		UiObject UserNamebtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
		UiObject passwordBtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
		UiObject logginbtn= new UiObject(new UiSelector().description("login_btn"));
		UiObject MeBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("我的"));
		MeBtn.click();
		UiObject lgOutBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("退出登录"));
		lgOutBtn.click();
		//以下测试异常登录
		//用户名错误无法登录
		//UserNamebtn.click();
		UserNamebtn.clearTextField();//clearTextField长按清除，setText先清除再输入
		UserNamebtn.setText("1526118052");
		//passwordBtn.click();
		passwordBtn.clearTextField();
		passwordBtn.setText("123456");

		logginbtn.clickAndWaitForNewWindow(2000);
	
		if(MeBtn.exists()){
			System.out.println("执行用例失败");
			failCase=failCase+1;
		}
		else {
			System.out.println("用户名错误,无法登录");
			passCase=passCase+1;
			}
		//密码错误无法登录
		//UserNamebtn.click();
		UserNamebtn.clearTextField();
		UserNamebtn.setText("15261180520");
		//passwordBtn.click();
		passwordBtn.clearTextField();
		passwordBtn.setText("1111222");

		logginbtn.clickAndWaitForNewWindow();
		if(MeBtn.exists()){
			System.out.println("执行用例失败");
			failCase=failCase+1;
		}
		else {
			System.out.println("密码错误,无法登录");
			passCase=passCase+1;
			}
		//正常登录
		//UserNamebtn.click();
		UserNamebtn.clearTextField();
		UserNamebtn.setText("15261180520");
		
		
		//passwordBtn.click();
		passwordBtn.clearTextField();
		passwordBtn.setText("123456");
		
		logginbtn.clickAndWaitForNewWindow();
		//登录成功，点击我的
		MeBtn.click();
		
		device.waitForIdle(2000);
		UiObject lguser=new UiObject(new UiSelector().className("android.widget.TextView").instance(0));
		//获得登录用户的用户名
		String Metext=lguser.getText();
		System.out.println("登录的用户是:"+Metext);
		//判断用户名是否显示正确
		if (Metext.equals("王杰")){
			passCase=passCase+1;
			System.out.println("测试正常登录成功，用例通过");
		}
		else failCase=failCase+1;	
	}
	
	//用于测试接车接收新车辆
	public void testJieChe() throws UiObjectNotFoundException{
		//点击车况大师图标
		
		//判断是否需要登录并最终登录成功
		vcmlogginachieve();		
		//选中新增车辆的btn
		UiObject AddCarBtn=new UiObject(new UiSelector().className("android.view.View").index(3).childSelector(new UiSelector().className("android.widget.ImageView")));
		AddCarBtn.click();
		device.waitForIdle(1000);
		//接车方式为键盘输入，测试车辆
		/*1.新车 ：
		2.有任务正在进行的车辆 ： 
		3.近期检查过无问题的车辆 ：
		4.近期检查过有问题的车辆：
		5.上次检测有问题，时间大于30天：
		6.上次检测没问题，时间大于30天：
		 */
		UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
		keyBoardBtn.clickAndWaitForNewWindow();
		UiObject ZangBtn=new UiObject(new UiSelector().text("藏"));
		ZangBtn.clickAndWaitForNewWindow();
		UiObject BtnA=new UiObject(new UiSelector().text("A"));
		BtnA.click();
		UiObject Btn1=new UiObject(new UiSelector().text("1"));
		Btn1.click();
		UiObject Btn2=new UiObject(new UiSelector().text("2"));
		Btn2.click();
		UiObject Btn3=new UiObject(new UiSelector().text("3"));
		Btn3.click();
		UiObject Btn4=new UiObject(new UiSelector().text("4"));
		Btn4.click();
		Btn1.click();
	/*	int x=new Random().nextInt(6)+1;
		switch(x){
		case 1:
			Btn1.click();
			break;
		case 2:
			Btn2.click();
			break;
		case 3:
			Btn3.click();
			break;
		case 4:
			Btn4.click();
			break;
		case 5:
			UiObject Btn5=new UiObject(new UiSelector().text("5"));
			Btn5.click();
			break;
		case 6:
			UiObject Btn6=new UiObject(new UiSelector().text("6"));
			Btn6.click();
			break;
		}*/
		UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
		BtnEnter.clickAndWaitForNewWindow();
		
		//判断车牌号，验证跳转页面
		UiObject chepai=new UiObject(new UiSelector().className("android.widget.TextView").instance(1));
		if (chepai.exists()){
			String cph=chepai.getText();
		
	//以下为第一次进店车辆接待
			if (cph.equals("藏A12341")){
				System.out.println("开始测试接待新车辆"+cph);
				//接车页面滚动对象
				UiScrollable scroll=new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
				UiObject xuanchebtn=scroll.getChild(new UiSelector().className("android.widget.ImageView").instance(0));
				xuanchebtn.clickAndWaitForNewWindow();
				//选择车型凯迪拉克
				UiScrollable scrollchexin=new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
				UiObject cadillac=scrollchexin.getChildByText(new UiSelector().className("android.widget.TextView"),"凯迪拉克", true);
				cadillac.clickAndWaitForNewWindow();
				//选择车系列ATS
				UiObject cadillacseriers=new UiObject(new UiSelector().text("ATS"));
				cadillacseriers.clickAndWaitForNewWindow();
				//后续增加里程数等信息以及相应的校验
				UiObject moreinfo=scroll.getChild(new UiSelector().className("android.widget.ImageView").instance(2));
				moreinfo.click();
				UiObject vin=scroll.getChild(new UiSelector().className("android.widget.EditText").instance(0));
				vin.click();
				vin.clearTextField();
				vin.setText("VINTEST8901234567");
				UiObject lichen=scroll.getChild(new UiSelector().className("android.widget.EditText").instance(1));
				lichen.click();
				lichen.clearTextField();
				lichen.setText("5000");
				UiObject custname=scroll.getChild(new UiSelector().className("android.widget.EditText").instance(2));
				custname.click();
				custname.clearTextField();
				custname.setText("TEST1");
				UiObject phonenum=scroll.getChild(new UiSelector().className("android.widget.EditText").instance(3));
				phonenum.click();
				phonenum.clearTextField();
				phonenum.setText("15261180001");
				//单选框的校验？性别的检测？
				UiObject sexman=scroll.getChild(new UiSelector().className("android.widget.ImageView").instance(3));
				sexman.click();
				UiObject sexwoman=scroll.getChild(new UiSelector().className("android.widget.ImageView").instance(4));
				sexwoman.click();
				
				
				UiObject quanMianJianCe=new UiObject(new UiSelector().text("保养全面检测"));
				quanMianJianCe.click();
				
				UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测"));
				kaishijiance.click();
	//学习mongodb了解业务后继续写点击任务流转人，校验车辆页面车辆信息，删除相关数据等操作（删除操作最好放在接车前）
					
			}
		
		}	
		else  {
			System.out.println("车辆录入有误");
			}
		
	}
	
	//用于测试接车接收有任务正在进行的车辆
	public void testJieChe2() throws UiObjectNotFoundException{
		//录入有任务正在进行的车辆藏A12342，此车辆专用做测试此用例，不执行完成任务等操作
		vcmlogginachieve();		
		UiObject AddCarBtn=new UiObject(new UiSelector().className("android.view.View").index(3).childSelector(new UiSelector().className("android.widget.ImageView")));
		AddCarBtn.click();
		device.waitForIdle(1000);
		UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
		keyBoardBtn.clickAndWaitForNewWindow();
		UiObject ZangBtn=new UiObject(new UiSelector().text("藏"));
		ZangBtn.clickAndWaitForNewWindow();
		UiObject BtnA=new UiObject(new UiSelector().text("A"));
		BtnA.click();
		UiObject Btn1=new UiObject(new UiSelector().text("1"));
		Btn1.click();
		UiObject Btn2=new UiObject(new UiSelector().text("2"));
		Btn2.click();
		UiObject Btn3=new UiObject(new UiSelector().text("3"));
		Btn3.click();
		UiObject Btn4=new UiObject(new UiSelector().text("4"));
		Btn4.click();
		Btn2.click();
		UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
		BtnEnter.click();
		
		UiObject alertwind=new UiObject(new UiSelector().resourceId("android:id/alertTitle"));
		String alerttitle=alertwind.getText();
		if (alerttitle.equals("提示")){
			System.out.println("该车有任务正在完成,给出提示");
			}
		UiObject continueBtn=new UiObject(new UiSelector().text("继续完成"));
		continueBtn.clickAndWaitForNewWindow();
		UiObject renwuliu=new UiObject(new UiSelector().className("android.widget.TextView").instance(0));
		String rwltitle=renwuliu.getText();
		if (rwltitle.equals("任务流程")){
			System.out.println("该车有任务正在完成,继续完成跳转至任务流页面");
			}
		else{ System.out.println("接车有任务正在进行的车辆失败，无法跳转继续处理");}
	}
	
	//用于测试接车接收超过30天检测过并且无问题车辆，待补充不检测情况
	public void testJieChe3() throws UiObjectNotFoundException{
		vcmlogginachieve();		
		UiObject AddCarBtn=new UiObject(new UiSelector().className("android.view.View").index(3).childSelector(new UiSelector().className("android.widget.ImageView")));
		AddCarBtn.click();
		device.waitForIdle(1000);
		UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
		keyBoardBtn.clickAndWaitForNewWindow();
		UiObject ZangBtn=new UiObject(new UiSelector().text("川"));
		ZangBtn.clickAndWaitForNewWindow();
		UiObject BtnA=new UiObject(new UiSelector().text("A"));
		BtnA.click();
		UiObject Btn1=new UiObject(new UiSelector().text("1"));
		Btn1.click();
		UiObject Btn2=new UiObject(new UiSelector().text("2"));
		Btn2.click();
		UiObject Btn4=new UiObject(new UiSelector().text("4"));
		Btn4.click();
		UiObject Btn5=new UiObject(new UiSelector().text("5"));
		Btn5.click();
		Btn2.click();
		UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
		BtnEnter.click();
		
		UiObject normalAlert=new UiObject(new UiSelector().text("车辆无异常"));
		UiObject jiance=new UiObject(new UiSelector().text("开始检测"));
		//验证历史车辆信息是否正确，别的接车场景不再验证
		UiObject che=new UiObject(new UiSelector().text("别克_君越"));
		
		if (normalAlert.exists()&&jiance.exists()){
			System.out.println("远期检查车辆无问题，给出无异常提醒成功");
//点击关闭，后续补充
			jiance.clickAndWaitForNewWindow();
			if (che.exists()){
				System.out.println("历史车辆信息展示正常");
			}
			else {
				System.out.println("接车页面历史车辆信息无法正常展示");
			}
			UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
			if(jianceleixin.exists()&&jiance.exists()){
				System.out.println("开始检测进入检测套餐选择页面成功");				
			}
			else{
				System.out.println("历史车辆开始检测按钮点击后不能正常跳转套餐选择页面");
			}
		}
		else{
			System.out.println("远期无问题车辆，未给出操作建议");
		}
	}
	
	//用于测试接车接收超过30天检测过并且有遗留问题车辆，待补充不检测情况
	public void testJieChe4() throws UiObjectNotFoundException{
		vcmlogginachieve();		
		UiObject AddCarBtn=new UiObject(new UiSelector().className("android.view.View").index(3).childSelector(new UiSelector().className("android.widget.ImageView")));
		AddCarBtn.click();
		device.waitForIdle(1000);
		UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
		keyBoardBtn.clickAndWaitForNewWindow();
		UiObject ZangBtn=new UiObject(new UiSelector().text("川"));
		ZangBtn.clickAndWaitForNewWindow();
		UiObject BtnA=new UiObject(new UiSelector().text("A"));
		BtnA.click();
		UiObject Btn5=new UiObject(new UiSelector().text("5"));
		Btn5.click();
		UiObject Btn3=new UiObject(new UiSelector().text("3"));
		Btn3.click();
		Btn3.click();
		UiObject Btn7=new UiObject(new UiSelector().text("7"));
		Btn7.click();
		Btn7.click();
		UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
		BtnEnter.click();
		
		UiObject yichanginfo=new UiObject(new UiSelector().textMatches(".*个异常.*"));
		UiObject jiance=new UiObject(new UiSelector().text("开始检测"));
		UiObject jianyi=new UiObject(new UiSelector().text("操作建议"));
		//
		UiObject che=new UiObject(new UiSelector().className("android.widget.TextView").text("奥迪_A6"));
		
		if (jianyi.exists()){
			if (yichanginfo.exists()){
				System.out.println("远期检查车辆有问题，给出异常提醒成功");
			}
			else {
				System.out.println("远期异常车辆未给出提醒");
			}
//点击关闭，后续补充
			//点击检测进入正常接车页面
			jiance.clickAndWaitForNewWindow();
			if (che.exists()){
				System.out.println("历史车辆信息展示正常");
			}
			else {
				System.out.println("接车页面历史车辆信息无法正常展示");
			}
			UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
			if(jianceleixin.exists()&&jiance.exists()){
				System.out.println("开始检测进入检测套餐选择页面成功");				
			}
			else{
				System.out.println("历史车辆开始检测按钮点击后不能正常跳转套餐选择页面");
			}
		}
		else{
			System.out.println("远期有问题车辆，未给出操作建议");
		}
	}

	//用于测试接车近期接待过无异常的车辆
	public void testJieChe5() throws UiObjectNotFoundException{
	vcmlogginachieve();		
	UiObject AddCarBtn=new UiObject(new UiSelector().className("android.view.View").index(3).childSelector(new UiSelector().className("android.widget.ImageView")));
	AddCarBtn.click();
	device.waitForIdle(1000);
	UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
	keyBoardBtn.clickAndWaitForNewWindow();
	UiObject ZangBtn=new UiObject(new UiSelector().text("川"));
	ZangBtn.clickAndWaitForNewWindow();
	UiObject BtnA=new UiObject(new UiSelector().text("A"));
	BtnA.click();
	UiObject Btn1=new UiObject(new UiSelector().text("9"));
	Btn1.click();
	UiObject Btn2=new UiObject(new UiSelector().text("8"));
	Btn2.click();
	UiObject Btn4=new UiObject(new UiSelector().text("0"));
	Btn4.click();
	UiObject Btn5=new UiObject(new UiSelector().text("6"));
	Btn5.click();
	UiObject Btn7=new UiObject(new UiSelector().text("7"));
	Btn7.click();
	UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
	BtnEnter.click();
	
	UiObject jianyi=new UiObject(new UiSelector().textMatches(".*建议.*"));
	UiObject normalAlert=new UiObject(new UiSelector().text("车辆无异常"));
	UiObject jiance=new UiObject(new UiSelector().text("不，开始检测"));
	UiObject bujiance=new UiObject(new UiSelector().text("好，不检测了"));
	UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测"));
	if (jianyi.exists()){
		if (normalAlert.exists()){
			System.out.println("车辆状况提示正确");
			if(jiance.exists()&&bujiance.exists()){
				//执行不检测，回答主页
				bujiance.click();
				UiObject task_header=new UiObject(new UiSelector().description("task_header_wrap"));
				if(task_header.exists()){
					System.out.println("近期检测过无异常车辆不进行检测，跳转成功");
				}
				else{
					System.out.println("近期检测过无异常车辆不进行检测，按钮异常");
				}
				//以下仍然执行检测，重新开始接车
				AddCarBtn.click();
				device.waitForIdle(1000);
				keyBoardBtn.clickAndWaitForNewWindow();
				ZangBtn.clickAndWaitForNewWindow();
				BtnA.click();
				Btn1.click();
				Btn2.click();
				Btn4.click();
				Btn5.click();
				Btn7.click();
				BtnEnter.click();
				
				jiance.click();
				UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
				if(jianceleixin.exists()&&kaishijiance.exists()){
					System.out.println("近期检测无异常车辆开始检测进入检测套餐选择页面成功");				
				}
				else{
					System.out.println("近期检测无异常车辆开始检测按钮点击后不能正常跳转套餐选择页面");
				}
			}
			else{
				System.out.println("界面异常");
			}
		}
		else{
			System.out.println("车辆状况信息异常");
		}
	}
	else{
		System.out.println("近期检测无异常车辆未给出操作建议");
	}
	}
	
	//用于测试接车近期接待过有异常的车辆，待补充不检测情况
	//用于测试接车近期接待过有异常车辆仍要检测
	public void testJieChe6() throws UiObjectNotFoundException{
		vcmlogginachieve();		
		UiObject AddCarBtn=new UiObject(new UiSelector().className("android.view.View").index(3).childSelector(new UiSelector().className("android.widget.ImageView")));
		AddCarBtn.click();
		device.waitForIdle(1000);
		UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
		keyBoardBtn.clickAndWaitForNewWindow();
		UiObject ZangBtn=new UiObject(new UiSelector().text("川"));
		ZangBtn.clickAndWaitForNewWindow();
		UiObject BtnA=new UiObject(new UiSelector().text("A"));
		BtnA.click();
		UiObject Btn1=new UiObject(new UiSelector().text("1"));
		Btn1.click();
		UiObject Btn2=new UiObject(new UiSelector().text("2"));
		Btn2.click();
		UiObject Btn4=new UiObject(new UiSelector().text("3"));
		Btn4.click();
		UiObject Btn5=new UiObject(new UiSelector().text("4"));
		Btn5.click();
		Btn1.click();
		UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
		BtnEnter.click();
		
		UiObject jianyi=new UiObject(new UiSelector().textContains("建议"));
		UiObject reminder=new UiObject(new UiSelector().textMatches(".*无需检测，直接沟通确认遗留问题是否施工.*"));
		UiObject jiance=new UiObject(new UiSelector().text("不，开始检测"));
		UiObject bujiance=new UiObject(new UiSelector().text("好，不检测了"));
		UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测"));
		if (jianyi.exists()){
			if (reminder.exists()){
				System.out.println("车辆状况提示正确");
				if(jiance.exists()&&bujiance.exists()){
//bujiance.click();//跳转到施工流转页面，转给自己，点击下次再说，在继续回到这边执行立即检测的操作，车辆场景不变后续写
					jiance.click();
					UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
					if(jianceleixin.exists()&&kaishijiance.exists()){
						System.out.println("近期检测有异常车辆开始检测进入检测套餐选择页面成功");				
					}
					else{
						System.out.println("近期检测有异常车辆开始检测按钮点击后不能正常跳转套餐选择页面");
					}
				}
				else{
					System.out.println("界面异常");
				}
			}
			else{
				System.out.println("车辆状况信息异常");
			}
		}
		else{
			System.out.println("近期检测有异常车辆未给出操作建议");
		}
		}
}

	
	


