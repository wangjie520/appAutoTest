package com.checheyun.vcm.VcmUiTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class VCMuiTestRptInMongo extends UiAutomatorTestCase {

	/**
	 * @param args
	 * 
	 */
	static Mongo ipRpt=new Mongo("192.168.24.108",27017);
	static DB dbRpt=ipRpt.getDB("testreport");
	//static Mongo ipTestEnv=new Mongo("101.37.86.138",27017);
	//static DB dbTestEnv=ipTestEnv.getDB("vcm");
	String p="pass";
	String f="fail";
	Date dt = new Date();  
	SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");  
	String dtime = fmt.format(dt);
	String filename="testRpt"+dtime+".txt";
	String filepath="/sdcard/testpic"+filename;
	DBCollection testResult=dbRpt.getCollection("testResult");
	/*---------------IO输出流，注释于此用于复制----------------
	FileOutputStream fos=new FileOutputStream(filepath,true);
	String s="start to test loggin,excute time:"+new Date()+"\r\n";
	fos.write(s.getBytes());
	fos.close();
	---------------------------------------------------------*/
	UiDevice device=UiDevice.getInstance();
	
	public static void main(String[] args){
		
		String android_id = "1";
		 //android list target id
		 String jar_name = "CaseRptMongo";
		 //生成jar的名字
		 String test_class = "com.checheyun.vcm.VcmUiTest.VCMuiTestRptInMongo";
		 String test_name = "todayData";
		 //方法名
		 new UiAutomatorHelper(jar_name,test_class,test_name,android_id);
		 
		 
	}
	//用于截屏，图片以截屏时间命名
	public void screenshot() {  
		Date a = new Date();  
		SimpleDateFormat b = new SimpleDateFormat("yyyyMMddHHmmss");  
		String c = b.format(a);  
		System.out.println(c);  
		File files = new File("/sdcard/testpic/"+c+".png");  
		   getUiDevice().takeScreenshot(files);  
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
			f.printStackTrace();
		}
		//启动车况大师
		UiObject obj1= new UiObject(new UiSelector().text("车况大师"));
		obj1.clickAndWaitForNewWindow();
		UiObject needloggin= new UiObject(new UiSelector().description("login_btn"));
		//判断是否有登录按钮，使用等待判断是否存在，否则很大概率不存在
		if (!needloggin.waitForExists(5000)){
			System.out.println("无需登录");
		}
		else {
			UiObject UsrNamebtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
			UsrNamebtn.clearTextField();
			UsrNamebtn.setText("18581540581");
			
			UiObject pwdBtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
			pwdBtn.clearTextField();
			pwdBtn.setText("123456");
		
			needloggin.clickAndWaitForNewWindow();
			}
		//点击左下角任务
		
		
	}
	
	//用于测试登录的场景和退出登录OK
	public void testLoggin() throws UiObjectNotFoundException, IOException{
		//初始化退出，避免session登录
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="start to test loggin,excute time:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		vcmlogginachieve();
		UiObject UserNamebtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
		UiObject passwordBtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
		UiObject logginbtn= new UiObject(new UiSelector().description("login_btn"));
		UiObject MeBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("我的"));
		MeBtn.click();
		UiObject lgOutBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("退出登录"));
		lgOutBtn.click();
		if (logginbtn.exists()){
			s="2-015:"+p+"\r\n";
			fos.write(s.getBytes());
		}
		else{
			s="2-015:"+p+"\r\n";
			fos.write(s.getBytes());
		}
		//以下测试异常登录
		//用户名错误无法登录
		//UserNamebtn.click();
		UserNamebtn.clearTextField();//clearTextField长按清除，setText先清除再输入
		device.waitForIdle(1000);
		UserNamebtn.setText("18581540588");
		//passwordBtn.click();
		passwordBtn.clearTextField();
		device.waitForIdle(1000);
		passwordBtn.setText("123456");
		logginbtn.clickAndWaitForNewWindow(2000);
		device.waitForIdle(1000);
		screenshot();
		if(MeBtn.exists()){
			s="1-002:"+f+"\r\n";
			fos.write(s.getBytes());
		}
		else {
			 s="1-002:"+p+"\r\n";
			fos.write(s.getBytes());
			}
		//密码错误无法登录
		//UserNamebtn.click();
		UserNamebtn.clearTextField();
		device.waitForIdle(1000);
		UserNamebtn.setText("18581540581");
		//passwordBtn.click();
		passwordBtn.clearTextField();
		device.waitForIdle(1000);
		passwordBtn.setText("123455");

		logginbtn.clickAndWaitForNewWindow();
		device.waitForIdle(1000);
		screenshot();
		if(MeBtn.exists()){
			s="1-003:"+f+"\r\n";
			fos.write(s.getBytes());
		}
		else {
			s="1-003:"+p+"\r\n";
			fos.write(s.getBytes());
			}
		//正常登录
		//UserNamebtn.click();
		device.waitForIdle(1000);
		UserNamebtn.clearTextField();
		device.waitForIdle(1000);
		UserNamebtn.setText("15261180520");
		
		
		//passwordBtn.click();
		passwordBtn.clearTextField();
		device.waitForIdle(1000);
		passwordBtn.setText("123456");
		screenshot();
		logginbtn.clickAndWaitForNewWindow();
		//登录成功，点击我的
		MeBtn.click();
		
		device.waitForIdle(2000);
		UiObject lguser=new UiObject(new UiSelector().className("android.widget.TextView").instance(0));
		//获得登录用户的用户名
		String Metext=lguser.getText();
		System.out.println("登录的用户是:"+Metext);
		UiObject storename=new UiObject(new UiSelector().text("自动测试1店"));
		screenshot();
		//判断用户名是否显示正确
		if (Metext.equals("王杰")&&storename.exists()){
			s="1-001:"+p+"\r\n";
			fos.write(s.getBytes());
			s="2-001:"+p+"\r\n";
			fos.write(s.getBytes());
			fos.close();
		}
		else{
			s="1-001:"+f+"\r\n";
			fos.write(s.getBytes());
			s="2-001:"+f+"\r\n";
			fos.write(s.getBytes());
			fos.close();
		}
	}

	
	//用于测试接车接收新车辆 
	//db.car.remove({license:'蒙A93925'})
	//db.task.remove({license:'蒙A93925'})
	//db.task_flow.remove({license:'蒙A93925'})
	public void testJieChe() throws UiObjectNotFoundException, IOException{
		//点击车况大师图标
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试待新车,执行时间:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		//判断是否需要登录并最终登录成功
		vcmlogginachieve();		
		//选中新增车辆的btn
		UiObject AddCarBtn=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(3)));
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
		UiObject ZangBtn=new UiObject(new UiSelector().text("浙"));
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
		UiObject Btn0=new UiObject(new UiSelector().text("0"));
		Btn0.click();
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
			if (cph.equals("浙A12340")){
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
				
				s="4-002:"+p+"\r\n";
				fos.write(s.getBytes());
				fos.close();
					
			}
		
		}	
		else  {
			s="4-002:"+f+"\r\n";
			fos.write(s.getBytes());
			fos.close();
			}
		
	}
	
	//用于测试接车接收有任务正在进行的车辆OK
	public void testJieChe2() throws UiObjectNotFoundException, IOException{
		//录入有任务正在进行的车辆，此车辆专用做测试此用例，不执行完成任务等操作
		vcmlogginachieve();		
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试接收有任务正在进行车辆:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		UiObject AddCarBtn=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(3)));
		AddCarBtn.click();
		device.waitForIdle(1000);
		UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
		keyBoardBtn.clickAndWaitForNewWindow();
		UiObject ZangBtn=new UiObject(new UiSelector().text("浙"));
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
		UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
		BtnEnter.click();
		
		UiObject alertwind=new UiObject(new UiSelector().resourceId("android:id/alertTitle"));
		String alerttitle=alertwind.getText();
		if (alerttitle.equals("提示")){
			System.out.println("该车有任务正在完成,给出提示");
			}
		UiObject RtnBtn=new UiObject(new UiSelector().text("返回"));
		RtnBtn.clickAndWaitForNewWindow();
		BtnEnter.click();
		UiObject continueBtn=new UiObject(new UiSelector().text("继续完成"));
		continueBtn.clickAndWaitForNewWindow();
		UiObject renwuliu=new UiObject(new UiSelector().className("android.widget.TextView").instance(0));
		String rwltitle=renwuliu.getText();
		if (rwltitle.equals("任务流程")){
			s="4-003:"+p+"\r\n";
			fos.write(s.getBytes());
			fos.close();
			}
		else{
			s="4-003:"+f+"\r\n";
			fos.write(s.getBytes());
			fos.close();}
	}
	
	//4-005用于测试接车接收超过30天检测过并且无问题车辆,需要修改数据OK
	public void testJieChe3() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve();		
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试接车超过30天检测并且无问题车辆:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		UiObject AddCarBtn=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(3)));
		AddCarBtn.click();
		device.waitForIdle(1000);
		UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
		keyBoardBtn.clickAndWaitForNewWindow();
		UiObject ZangBtn=new UiObject(new UiSelector().text("浙"));
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
		Btn4.click();
		UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
		BtnEnter.click();
		
		UiObject normalAlert=new UiObject(new UiSelector().text("车辆无异常"));
		UiObject jiance=new UiObject(new UiSelector().text("开始检测"));
		UiObject closebtn=new UiObject(new UiSelector().text("关闭"));
		//验证历史车辆信息是否正确，别的接车场景不再验证待补充
		UiObject che=new UiObject(new UiSelector().text("别克_君越"));
		
		if (normalAlert.exists()&&jiance.exists()){
			System.out.println("远期检查车辆无问题，给出无异常提醒成功");
//点击关闭，后续补充
			closebtn.click();
			//重新录车
			AddCarBtn.click();
			device.waitForIdle(1000);
			keyBoardBtn.clickAndWaitForNewWindow();
			ZangBtn.clickAndWaitForNewWindow();
			BtnA.click();
			Btn1.click();
			Btn2.click();
			Btn4.click();
			Btn5.click();
			Btn4.click();
			BtnEnter.click();
			
			jiance.clickAndWaitForNewWindow();
			if (che.exists()){
				System.out.println("历史车辆信息展示正常");
			}
			else {
				System.out.println("接车页面历史车辆信息无法正常展示");
				s="4-005:"+f+"\r\n";
				fos.write(s.getBytes());
				fos.close();
			}
			UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
			if(jianceleixin.exists()&&jiance.exists()){
				System.out.println("开始检测进入检测套餐选择页面成功");
				s="4-005:"+p+"\r\n";
				fos.write(s.getBytes());
				fos.close();				
			}
			else{
				System.out.println("历史车辆开始检测按钮点击后不能正常跳转套餐选择页面");
				s="4-005:"+f+"\r\n";
				fos.write(s.getBytes());
				fos.close();
			}
		}
		else{
			System.out.println("远期无问题车辆，未给出操作建议");
			s="4-005:"+f+"\r\n";
			fos.write(s.getBytes());
			fos.close();
		}
	}
	
	//用于测试接车接收超过30天检测过并且有遗留问题车辆，需要修改数据，后续写
	public void testJieChe4() throws UiObjectNotFoundException{
		vcmlogginachieve();		
		UiObject AddCarBtn=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(3)));
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
			//点击关闭
			UiObject closebtn=new UiObject(new UiSelector().text("关闭"));
			closebtn.click();
			AddCarBtn.click();
			device.waitForIdle(1000);
			keyBoardBtn.clickAndWaitForNewWindow();
			ZangBtn.clickAndWaitForNewWindow();
			BtnA.click();
			Btn5.click();
			Btn3.click();
			Btn3.click();
			Btn7.click();
			Btn7.click();
			BtnEnter.click();
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

	//4-007用于测试接车近期接待过无异常的车辆
	public void testJieChe5() throws UiObjectNotFoundException, IOException{
	vcmlogginachieve();		
	FileOutputStream fos=new FileOutputStream(filepath,true);
	
	UiObject AddCarBtn=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(3)));
	AddCarBtn.click();
	device.waitForIdle(1000);
	UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
	keyBoardBtn.clickAndWaitForNewWindow();
	UiObject ZangBtn=new UiObject(new UiSelector().text("浙"));
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
	UiObject Btn7=new UiObject(new UiSelector().text("5"));
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
					String s="4-007:"+f+"\r\n";
					fos.write(s.getBytes());
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
					String s="4-007:"+p+"\r\n";
					fos.write(s.getBytes());
					fos.close();
				}
				else{
					System.out.println("近期检测无异常车辆开始检测按钮点击后不能正常跳转套餐选择页面");
					String s="4-007:"+f+"\r\n";
					fos.write(s.getBytes());
					fos.close();
				}
			}
			else{
				System.out.println("界面异常");
				String s="4-007:"+f+"\r\n";
				fos.write(s.getBytes());
				fos.close();
			}
		}
		else{
			System.out.println("车辆状况信息异常");
			String s="4-007:"+f+"\r\n";
			fos.write(s.getBytes());
			fos.close();
		}
	}
	else{
		System.out.println("近期检测无异常车辆未给出操作建议");
		String s="4-007:"+f+"\r\n";
		fos.write(s.getBytes());
		fos.close();
	}
	}
	
	//用于测试接车近期接待过有异常的车辆
	public void testJieChe6() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve();	
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试接待近期接待过有异常车辆，执行时间:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		UiObject AddCarBtn=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(3)));
		AddCarBtn.click();
		UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
		keyBoardBtn.clickAndWaitForNewWindow();
		UiObject ZangBtn=new UiObject(new UiSelector().text("浙"));
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
		Btn5.click();
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
//跳转到施工流转页面，转给自己，点击下次再说，在继续回到这边执行立即检测的操作，车辆场景不变后续写
					bujiance.click();
					UiObject tasker=new UiObject(new UiSelector().textContains("(我)"));
					UiObject queren=new UiObject(new UiSelector().text("确定"));
					tasker.click();
					queren.click();
//补充返回到任务界面
					UiObject returnBtn=new UiObject(new UiSelector().className("android.widget.ImageView").instance(0));
					returnBtn.clickAndWaitForNewWindow();
					returnBtn.clickAndWaitForNewWindow();
					UiObject renwuliu=new UiObject(new UiSelector().text("任务流"));
					renwuliu.click();
					UiObject NotSolveTaskFlow=new UiObject(new UiSelector().text("未完成"));
					NotSolveTaskFlow.click();
					device.waitForIdle(1000);
					device.swipe(544, 420, 544, 804, 30);
					device.waitForIdle(1000);
					UiObject carTaskFlow=new UiObject(new UiSelector().text("浙A12344").instance(0));
					carTaskFlow.clickAndWaitForNewWindow();
					UiObject title=new UiObject(new UiSelector().text("任务流程"));
					if (title.exists()){
						UiObject quwancheng=new UiObject(new UiSelector().text("去完成 "));
						quwancheng.clickAndWaitForNewWindow();
						UiObject bendianxiufu1=new UiObject(new UiSelector().text("下次再说"));
						bendianxiufu1.click();
						UiObject tijiao=new UiObject(new UiSelector().text("提交"));
						tijiao.click();		
					}
					else{
						s="4-006:"+f+"\r\n";
						fos.write(s.getBytes());
						fos.close();
						return;
					}
					UiObject rtnbtn=new UiObject(new UiSelector().className("android.widget.ImageView").instance(0));
					rtnbtn.click();
					//重新接车，执行重新检测
					AddCarBtn.click();
					keyBoardBtn.clickAndWaitForNewWindow();
					ZangBtn.clickAndWaitForNewWindow();
					BtnA.click();
					Btn1.click();
					Btn2.click();
					Btn4.click();
					Btn5.click();
					Btn5.click();
					BtnEnter.click();
					jiance.click();
					UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
					if(jianceleixin.exists()&&kaishijiance.exists()){
						System.out.println("近期检测有异常车辆开始检测进入检测套餐选择页面成功");	
						s="4-006:"+p+"\r\n";
						fos.write(s.getBytes());
						fos.close();
					}
					else{
						System.out.println("近期检测有异常车辆开始检测按钮点击后不能正常跳转套餐选择页面");
						s="4-006:"+f+"\r\n";
						fos.write(s.getBytes());
						fos.close();
					}
				}
				else{
					System.out.println("界面异常");
					s="4-006:"+f+"\r\n";
					fos.write(s.getBytes());
					fos.close();
				}
			}
			else{
				System.out.println("车辆状况信息异常");
				s="4-006:"+f+"\r\n";
				fos.write(s.getBytes());
				fos.close();
			}
		}
		else{
			System.out.println("近期检测有异常车辆未给出操作建议");
			s="4-006:"+f+"\r\n";
			fos.write(s.getBytes());
			fos.close();
		}
		}
	
	//2-002，今日数据，不测数据，仅测试跳转
	public void todayData() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve();
		DBObject result=new BasicDBObject();
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试我的界面今日数据:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		
		UiObject MeBtn=new UiObject(new UiSelector().text("我的"));
		MeBtn.click();
		UiObject todayDataBtn=new UiObject(new UiSelector().text("今日数据"));
		todayDataBtn.click();
		
		UiObject title=new UiObject(new UiSelector().text("历史数据"));
		UiObject anri=new UiObject(new UiSelector().text("按日"));
		UiObject anzhou=new UiObject(new UiSelector().text("按周"));
		UiObject anyue=new UiObject(new UiSelector().text("按月"));
		if (title.exists()&&anri.exists()&&anzhou.exists()&&anyue.exists()){
			anzhou.click();
			anyue.click();
			anri.click();
			s="2-002:"+p+"\r\n";
			fos.write(s.getBytes());
			fos.close();
			result.put("caseNo","2-002");
			result.put("status", p);
			result.put("excute_batch", dtime);
			testResult.insert(result);
		}
		else{
			s="2-002:"+f+"\r\n";
			fos.write(s.getBytes());
			fos.close();
			result.put("caseNo","2-002");
			result.put("status", f);
			result.put("excute_batch", dtime);
			testResult.insert(result);
		}
	}
	
	//4-008,录入报告，修改车辆信息
	public void report_modifycarinfo() throws UiObjectNotFoundException, IOException{
		testJieChe5();
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试录入报告时修改车辆信息:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测")); 
		UiObject byqm=new UiObject(new UiSelector().text("保养全面检测"));
		UiObject tasker=new UiObject(new UiSelector().textContains("(我)"));
		UiObject queren=new UiObject(new UiSelector().text("确定"));
		byqm.click();
		kaishijiance.clickAndWaitForNewWindow();
		tasker.click();
		queren.clickAndWaitForNewWindow();
		UiObject modifycarinfo=new UiObject(new UiSelector().textContains("修改车辆信"));
		modifycarinfo.clickAndWaitForNewWindow(5000);
		UiObject updatecustinfo=new UiObject(new UiSelector().text("更新客户资料"));
		if (updatecustinfo.exists()){
			UiObject rtnBtn=new UiObject(new UiSelector().className("android.widget.ImageView").instance(0));
			rtnBtn.click();
			s="4-008:"+p+"\r\n";
			fos.write(s.getBytes());
			fos.close();
			
		}
		else{
			s="4-008:"+f+"\r\n";
			fos.write(s.getBytes());
			fos.close();
		}	
	}
	//4-009
	public void batchSetNormal() throws UiObjectNotFoundException, IOException{
		report_modifycarinfo();
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试录入报告时批量设置良好:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		UiObject batSet=new UiObject(new UiSelector().text("批量设置良好"));
		batSet.click();
		UiObject preview=new UiObject(new UiSelector().text("预览"));
		preview.click();
		UiObject generateRpt=new UiObject(new UiSelector().text("生成报告"));
		generateRpt.click();
		UiObject tasker=new UiObject(new UiSelector().textContains("(我)"));
		UiObject queren=new UiObject(new UiSelector().text("确定"));
		tasker.click();
		queren.clickAndWaitForNewWindow();
		UiObject sendRpt=new UiObject(new UiSelector().text("发给车主"));
		sendRpt.click();
		//补充RPT内容的校验，状况良好20项，100分
		UiObject skip=new UiObject(new UiSelector().text("跳过"));
		skip.click();
		//判断我的待办界面是否还有浙A12345相关任务
		UiObject daiban=new UiObject(new UiSelector().text("我的待办"));
		daiban.click();
		device.swipe(544, 420, 544, 804, 30);
		device.waitForIdle(3000);
		UiObject chepai=new UiObject(new UiSelector().text("浙A12345"));
		if (!chepai.exists()){
			System.out.println("检测无问题车辆任务在推送报告后不会产生确认施工的任务");
			UiObject renwuliu=new UiObject(new UiSelector().text("任务流"));
			renwuliu.click();
			UiObject allTaskFlow=new UiObject(new UiSelector().text("全部"));
			allTaskFlow.click();
			device.swipe(544, 420, 544, 804, 30);
			device.waitForIdle(1000);
			UiObject carTaskFlow=new UiObject(new UiSelector().text("浙A12345").instance(0));
			carTaskFlow.clickAndWaitForNewWindow();
			UiObject title=new UiObject(new UiSelector().text("任务流程"));
			if (title.exists()){
				UiObject shigong=new UiObject(new UiSelector().text("确认施工"));
				if(!shigong.exists()){
					System.out.println("检测无问题车辆任务流不会流转到确认施工");
					s="4-009:"+p+"\r\n";
					fos.write(s.getBytes());
					fos.close();
				}
				else{
					s="4-009:"+f+"\r\n";
					fos.write(s.getBytes());
					fos.close();
				}
			}
			
		}
		else{
			s="4-009:"+f+"\r\n";
			fos.write(s.getBytes());
			fos.close();
		}
		
		
	}
	//4-010
	public void batchAndMoidfyIssue() throws UiObjectNotFoundException, IOException{
		testJieChe5();
		UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测")); 
		UiObject taocan=new UiObject(new UiSelector().text("洗车快速检测"));
		UiObject tasker=new UiObject(new UiSelector().textContains("(我)"));
		UiObject queren=new UiObject(new UiSelector().text("确定"));
		taocan.click();
		kaishijiance.clickAndWaitForNewWindow();
		tasker.click();
		queren.clickAndWaitForNewWindow();
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试录入报告时批量设置良好后修改为问题项:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		UiObject batSet=new UiObject(new UiSelector().text("批量设置良好"));
		batSet.click();
		UiObject tab1=new UiObject(new UiSelector().textContains("已检测"));
		UiObject tab2=new UiObject(new UiSelector().textContains("未检测"));
		UiObject tab3=new UiObject(new UiSelector().text("发动机舱"));
		UiObject tab4=new UiObject(new UiSelector().text("车轮相关"));
		UiObject tab5=new UiObject(new UiSelector().text("驾驶室"));
		UiObject tab6=new UiObject(new UiSelector().textContains("全部 "));
		tab1.click();
		tab2.click();
		tab6.click();
		tab4.click();
		tab5.click();
		tab3.click();
		UiScrollable scroll=new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
		UiObject qianShaChePian=scroll.getChild(new UiSelector().className("android.widget.TextView").text("前刹车片"));
		UiObject kongTiaoLvXin=scroll.getChild(new UiSelector().className("android.widget.TextView").text("空调滤芯"));
		qianShaChePian.clickAndWaitForNewWindow();
		UiObject issue1=new UiObject(new UiSelector().className("android.widget.TextView").text("2~5mm"));
		issue1.click();
		UiObject issueEn=new UiObject(new UiSelector().className("android.widget.ImageView").instance(1));
		issueEn.click();
		kongTiaoLvXin.clickAndWaitForNewWindow();
		UiObject issue2=new UiObject(new UiSelector().className("android.widget.TextView").text("超过100"));
		issue2.click();
		UiObject beizhu=new UiObject(new UiSelector().className("android.widget.EditText"));
		beizhu.click();
		beizhu.setText("test single issue remark");
//mark		//上传图片,问题在此，添加图片空间获取错误
		UiObject addPic=new UiObject(new UiSelector().className("android.view.View").index(2));
		addPic.click();
		UiObject cancel=new UiObject(new UiSelector().text("取消"));
		cancel.click();
		addPic.click();
		//通过拍摄上传图片
		UiObject paishe=new UiObject(new UiSelector().text("拍摄"));
		paishe.clickAndWaitForNewWindow();
		//此段代码在不同手机可通用
		UiObject kuaimen=new UiObject(new UiSelector().className("android.view.View").index(1));
		kuaimen.click();
		//从相册选择上传在别的用例体现
		issueEn.click();
		UiObject preview=new UiObject(new UiSelector().text("预览"));
		preview.click();
		UiObject generateRpt=new UiObject(new UiSelector().text("生成报告"));
		generateRpt.click();
		tasker.click();
		queren.clickAndWaitForNewWindow();
		UiObject sendRpt=new UiObject(new UiSelector().text("发给车主"));
		sendRpt.click();
		//补充RPT内容的校验
		UiObject skip=new UiObject(new UiSelector().text("跳过"));
		skip.click();
		//判断我的待办界面是否还有浙A12345相关任务
		UiObject daiban=new UiObject(new UiSelector().text("我的待办"));
		daiban.click();
		device.swipe(544, 420, 544, 804, 30);
		device.waitForIdle(3000);
		UiObject chepai=new UiObject(new UiSelector().text("浙A12345"));
		if (chepai.exists()){
			//确认施工在此用例继续体现，从人物流进
			UiObject renwuliu=new UiObject(new UiSelector().text("任务流"));
			renwuliu.click();
			UiObject NotSolveTaskFlow=new UiObject(new UiSelector().text("未完成"));
			NotSolveTaskFlow.click();
			device.swipe(544, 420, 544, 804, 30);
			device.waitForIdle(1000);
			UiObject carTaskFlow=new UiObject(new UiSelector().text("浙A12345").instance(0));
			carTaskFlow.clickAndWaitForNewWindow();
			UiObject title=new UiObject(new UiSelector().text("任务流程"));
			if (title.exists()){
				UiObject quwancheng=new UiObject(new UiSelector().text("去完成").instance(1));
				quwancheng.clickAndWaitForNewWindow();
				UiObject bendianxiufu1=new UiObject(new UiSelector().text("本店修复").instance(0));
				UiObject bendianxiufu2=new UiObject(new UiSelector().text("本店修复").instance(1));
				bendianxiufu1.click();
				bendianxiufu2.click();
				UiObject tijiao=new UiObject(new UiSelector().text("提交"));
				tijiao.click();
				s="4-010:"+p+"\r\n";
				fos.write(s.getBytes());
				fos.close();	
			}
			else{
				s="4-010:"+f+"\r\n";
				fos.write(s.getBytes());
				fos.close();
			}
			
		}
	}
	//4-011
	public void issueAndBatchSet() throws IOException, UiObjectNotFoundException{
		testJieChe5();
		UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测")); 
		UiObject taocan=new UiObject(new UiSelector().text("洗车快速检测"));
		UiObject tasker=new UiObject(new UiSelector().textContains("(我)"));
		UiObject queren=new UiObject(new UiSelector().text("确定"));
		taocan.click();
		kaishijiance.clickAndWaitForNewWindow();
		tasker.click();
		queren.clickAndWaitForNewWindow();
		FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试录入报告时设置问题项后批量设置良好:"+new Date()+"\r\n";
		fos.write(s.getBytes());
		UiScrollable scroll=new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
		UiObject zhidongye=scroll.getChild(new UiSelector().className("android.widget.TextView").text("制动液"));
		zhidongye.click();
		UiObject issue1=new UiObject(new UiSelector().className("android.widget.TextView").text("3%"));
		issue1.click();
		//mark		//上传图片,问题在此，添加图片空间获取错误
				//UiObject addPic=new UiObject(new UiSelector().className("android.view.View").index(2));
				//addPic.click();
				//UiObject cancel=new UiObject(new UiSelector().text("取消"));
				//cancel.click();
				//addPic.click();
				//从相册选择上传
				//			
		UiObject issueEn=new UiObject(new UiSelector().className("android.widget.ImageView").instance(1));
		issueEn.click();
		UiObject batSet=new UiObject(new UiSelector().text("批量设置良好"));
		batSet.click();
		UiObject remarkbtn=new UiObject(new UiSelector().text("备注"));
		remarkbtn.clickAndWaitForNewWindow();
		UiObject beizhu=new UiObject(new UiSelector().className("android.widget.EditText"));
		beizhu.click();
		beizhu.setText("test report remark");
		UiObject okbtn=new UiObject(new UiSelector().text("确定"));
		okbtn.click();

		UiObject preview=new UiObject(new UiSelector().text("预览"));
		preview.click();
		UiObject generateRpt=new UiObject(new UiSelector().text("生成报告"));
		generateRpt.click();
		tasker.click();
		queren.clickAndWaitForNewWindow();
		UiObject sendRpt=new UiObject(new UiSelector().text("发给车主"));
		sendRpt.click();
		//补充RPT内容的校验
		UiObject skip=new UiObject(new UiSelector().text("跳过"));
		skip.click();
		//判断我的待办界面是否还有浙A12345相关任务
		UiObject daiban=new UiObject(new UiSelector().text("我的待办"));
		daiban.click();
		device.swipe(544, 420, 544, 804, 30);
		device.waitForIdle(3000);
		UiObject chepai=new UiObject(new UiSelector().text("浙A12345"));
		if (chepai.exists()){
			//确认施工在此用例继续体现，从人物流进
			UiObject renwuliu=new UiObject(new UiSelector().text("任务流"));
			renwuliu.click();
			UiObject NotSolveTaskFlow=new UiObject(new UiSelector().text("未完成"));
			NotSolveTaskFlow.click();
			device.waitForIdle(1000);
			device.swipe(544, 420, 544, 804, 30);
			device.waitForIdle(1000);
			UiObject carTaskFlow=new UiObject(new UiSelector().text("浙A12345").instance(0));
			carTaskFlow.clickAndWaitForNewWindow();
			UiObject title=new UiObject(new UiSelector().text("任务流程"));
			if (title.exists()){
				UiObject quwancheng=new UiObject(new UiSelector().text("去完成 ").instance(1));
				quwancheng.clickAndWaitForNewWindow();
				UiObject bendianxiufu1=new UiObject(new UiSelector().text("他店修复"));
				bendianxiufu1.click();
				UiObject tijiao=new UiObject(new UiSelector().text("提交"));
				tijiao.click();
				s="4-011:"+p+"\r\n";
				fos.write(s.getBytes());
				fos.close();	
			}
			else{
				s="4-011:"+f+"\r\n";
				fos.write(s.getBytes());
				fos.close();
			}
			
		}
	}
	//用于某个用例失败继续调试
	//public void testcontinue() throws IOException, UiObjectNotFoundException{
	//	FileOutputStream fos=new FileOutputStream(filepath,true);
	//	String s="继续测试:"+new Date()+"\r\n";
	//	fos.write(s.getBytes());
		//未执行的代码拷贝到此处
	//kfkskdfksfkdsk
		
		
	//}
}
