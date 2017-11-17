package com.checheyun.vcm.VcmUiTest;

//import java.util.Random;

import java.io.File;




import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class VCMuitestOutputRpt extends UiAutomatorTestCase {
	String p="pass";
	String f="fail";
	Date dt = new Date();  
	SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss"); 
	static Map<String,String> testResult=new TreeMap<String,String>();
	String dtime = fmt.format(dt);
	String filename="testRpt"+dtime+".txt";
	String filepath="/sdcard/testpic/"+filename;
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
		 String jar_name = "CaseRpt";
		 //生成jar的名字
		 String test_class = "com.checheyun.vcm.VcmUiTest.VCMuitestOutputRpt";
		 String test_name = "testJieChe5";
		 //方法名
		 new UiAutomatorHelper(jar_name,test_class,test_name,android_id);
		 
		 
	}
	//用于截屏，图片以截屏时间命名
	public void screenshot(String caseno) {  
		Date a = new Date();  
		SimpleDateFormat b = new SimpleDateFormat("yyyyMMddHHmmss");  
		String c = b.format(a);  
		System.out.println(c);  
		File files = new File("/sdcard/testpic/"+caseno+"-"+c+".png");  
		   getUiDevice().takeScreenshot(files);  
		}

	//用于实现正常登录
	public void vcmlogginachieve(String usrname,String passwd) throws UiObjectNotFoundException{
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
		device.pressHome();
		UiObject obj1= new UiObject(new UiSelector().text("车况大师"));
		UiObject apps=new UiObject(new UiSelector().description("Apps"));
		if (obj1.exists()){
			obj1.clickAndWaitForNewWindow();
		}
		else{
			apps.click();
			obj1.clickAndWaitForNewWindow();
		}
		
		UiObject needloggin= new UiObject(new UiSelector().description("login_btn"));
		//判断是否有登录按钮，使用等待判断是否存在，否则很大概率不存在
		if (!needloggin.waitForExists(8000)){
			System.out.println("无需登录");
		}
		else {
			UiObject UsrNamebtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
			UsrNamebtn.clearTextField();
			UsrNamebtn.setText(usrname);
			
			UiObject pwdBtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
			pwdBtn.clearTextField();
			pwdBtn.setText(passwd);
		
			needloggin.clickAndWaitForNewWindow();
			}
		//点击左下角任务
		
		
	}
	
	//用于测试登录的场景和退出登录OK
	public void testLoggin() throws UiObjectNotFoundException, IOException{
		//初始化退出，避免session登录
		
		vcmlogginachieve("18581540581","123456");
		UiObject UserNamebtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
		UiObject passwordBtn= new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
		UiObject logginbtn= new UiObject(new UiSelector().description("login_btn"));
		UiObject MeBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("我的"));
		MeBtn.click();
		UiObject lgOutBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("退出登录"));
		lgOutBtn.click();
		if (logginbtn.exists()){
			testResult.put("2-015",p);
		}
		else{
			testResult.put("2-015",f);
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
		screenshot("1-002");
		if(MeBtn.exists()){
			testResult.put("1-002",f);
		}
		else {
			testResult.put("1-002",p);
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
		screenshot("1-003");
		if(MeBtn.exists()){
			testResult.put("1-003",f);
		}
		else {
			testResult.put("1-003",p);
			}
		//正常登录
		//UserNamebtn.click();
		vcmlogginachieve("18581540581","123456");

		//登录成功，点击我的
		MeBtn.click();
		
		device.waitForIdle(2000);
		UiObject lguser=new UiObject(new UiSelector().className("android.widget.TextView").instance(0));
		//获得登录用户的用户名
		String Metext=lguser.getText();
		System.out.println("登录的用户是:"+Metext);
		UiObject storename=new UiObject(new UiSelector().text("自动测试1店"));
		screenshot("1-001");
		//判断用户名是否显示正确
		if (Metext.equals("王杰")&&storename.exists()){
			testResult.put("1-001",p);
			testResult.put("2-001",p);
		}
		else{
			testResult.put("1-001",f);
			testResult.put("2-001",f);
		}
	}

	
	//用于测试接车接收新车辆 
	//db.car.remove({license:'蒙A93925'})
	//db.task.remove({license:'蒙A93925'})
	//db.task_flow.remove({license:'蒙A93925'})
	public void jieche(String a,String b,String c,String d,String e,String f,String g) throws UiObjectNotFoundException{
		UiObject AddCarBtn=new UiObject(new UiSelector().description("task_header_wrap").childSelector(new UiSelector().className("android.view.View").instance(3)));
		AddCarBtn.click();	
		device.waitForIdle(1000);
		UiObject keyBoardBtn=new UiObject(new UiSelector().text("手动输入"));
		keyBoardBtn.clickAndWaitForNewWindow();
		UiObject ZangBtn=new UiObject(new UiSelector().text(a));
		ZangBtn.clickAndWaitForNewWindow();
		UiObject BtnA=new UiObject(new UiSelector().text(b));
		BtnA.click();
		UiObject Btn1=new UiObject(new UiSelector().text(c));
		Btn1.click();
		UiObject Btn2=new UiObject(new UiSelector().text(d));
		Btn2.click();
		UiObject Btn3=new UiObject(new UiSelector().text(e));
		Btn3.click();
		UiObject Btn4=new UiObject(new UiSelector().text(f));
		Btn4.click();
		UiObject Btn5=new UiObject(new UiSelector().text(g));
		Btn5.click();
		UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
		BtnEnter.clickAndWaitForNewWindow();
	}
	public void testJieChe() throws UiObjectNotFoundException, IOException{
		//点击车况大师图标
		//判断是否需要登录并最终登录成功
		vcmlogginachieve("18581540581","123456");		
		//选中新增车辆的btn
		
		//接车方式为键盘输入，测试车辆
		/*1.新车 ：
		2.有任务正在进行的车辆 ： 
		3.近期检查过无问题的车辆 ：
		4.近期检查过有问题的车辆：
		5.上次检测有问题，时间大于30天：
		6.上次检测没问题，时间大于30天：
		 //*/
		jieche("浙","A","1","2","3","4","0");
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
				
				//UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测"));
				//kaishijiance.click();
	//学习mongodb了解业务后继续写点击任务流转人，校验车辆页面车辆信息，删除相关数据等操作（删除操作最好放在接车前）
				
				testResult.put("4-002",p);
				
					
			}
		
		}	
		else  {
			testResult.put("4-002",f);
			}
		
	}
	
	//用于测试接车接收有任务正在进行的车辆OK
	public void testJieChe2() throws UiObjectNotFoundException, IOException{
		//录入有任务正在进行的车辆，此车辆专用做测试此用例，不执行完成任务等操作
		vcmlogginachieve("18581540581","123456");	
		
		jieche("浙","A","1","2","3","4","1");
		
		UiObject alertwind=new UiObject(new UiSelector().resourceId("android:id/alertTitle"));
		String alerttitle=alertwind.getText();
		if (alerttitle.equals("提示")){
			System.out.println("该车有任务正在完成,给出提示");
			}
		UiObject RtnBtn=new UiObject(new UiSelector().text("返回"));
		RtnBtn.clickAndWaitForNewWindow();
		UiObject BtnEnter=new UiObject(new UiSelector().text("确定"));
		BtnEnter.click();
		UiObject continueBtn=new UiObject(new UiSelector().text("继续完成"));
		continueBtn.clickAndWaitForNewWindow();
		UiObject renwuliu=new UiObject(new UiSelector().className("android.widget.TextView").instance(0));
		String rwltitle=renwuliu.getText();
		if (rwltitle.equals("任务流程")){
			testResult.put("4-003",p);
			}
		else{
			testResult.put("4-003",f);}
	}
	
	//4-005用于测试接车接收超过30天检测过并且无问题车辆,更改上次检测时间到一月前//真是环境未改数据
	//db.car.update({"license":"浙A12343"},{$set:{"date_last_checked" : "2017-09-25 16:53:01"}})
	public void testJieChe3() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve("18581540581","123456");		
		jieche("浙","A","1","2","3","4","3");
		
		UiObject normalAlert=new UiObject(new UiSelector().text("车辆无异常"));
		UiObject jiance=new UiObject(new UiSelector().text("开始检测"));
		UiObject closebtn=new UiObject(new UiSelector().text("关 闭"));
		//验证历史车辆信息是否正确，别的接车场景不再验证待补充
		UiObject che=new UiObject(new UiSelector().text("别克_君越"));
		
		if (normalAlert.exists()&&jiance.exists()){
			System.out.println("远期检查车辆无问题，给出无异常提醒成功");
//点击关闭，后续补充
			closebtn.click();
			//重新录车
			jieche("浙","A","1","2","3","4","3");
			
			jiance.clickAndWaitForNewWindow();
			if (che.exists()){
				System.out.println("历史车辆信息展示正常");
			}
			else {
				System.out.println("接车页面历史车辆信息无法正常展示");
				testResult.put("4-005",f);
				return;
			}
			UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
			if(jianceleixin.exists()&&jiance.exists()){
				System.out.println("开始检测进入检测套餐选择页面成功");
				testResult.put("4-005",p);				
			}
			else{
				System.out.println("历史车辆开始检测按钮点击后不能正常跳转套餐选择页面");
				testResult.put("4-005",f);
			}
		}
		else{
			System.out.println("远期无问题车辆，未给出操作建议");
			testResult.put("4-005",f);
		}
		System.out.println(testResult);
	}
	
	//4-004用于测试接车接收超过30天检测过并且有遗留问题车辆//真实环境未修改数据
	public void testJieChe4() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve("18581540581","123456");
		
		jieche("浙","A","1","2","3","4","2");
		
		UiObject yichanginfo=new UiObject(new UiSelector().textMatches(".*个异常.*"));
		UiObject advice=new UiObject(new UiSelector().textMatches(".*次检测时间间隔太久了，重新.*"));
		UiObject jiance=new UiObject(new UiSelector().text("开始检测"));
		UiObject jianyi=new UiObject(new UiSelector().text("操作建议"));
		//
		
		if (jianyi.exists()){
			if (yichanginfo.exists()&&advice.exists()){
				System.out.println("远期检查车辆有问题，给出异常提醒成功");
			}
			else {
				System.out.println("远期异常车辆未给出提醒");
				testResult.put("4-004",f);
				return;
			}
			//点击关闭
			UiObject closebtn=new UiObject(new UiSelector().text("关 闭"));
			closebtn.click();
			jieche("浙","A","1","2","3","4","2");
			//点击检测进入正常接车页面
			jiance.clickAndWaitForNewWindow();
			
			UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
			if(jianceleixin.exists()&&jiance.exists()){
				System.out.println("开始检测进入检测套餐选择页面成功");
				testResult.put("4-004",p);
			}
			else{
				System.out.println("历史车辆开始检测按钮点击后不能正常跳转套餐选择页面");
				testResult.put("4-004",f);
			}
		}
		else{
			System.out.println("远期有问题车辆，未给出操作建议");
			testResult.put("4-004",f);
		}
	}

	//4-007用于测试接车近期接待过无异常的车辆
	public void testJieChe5() throws UiObjectNotFoundException, IOException{
	vcmlogginachieve("18581540581","123456");	
	
	
	jieche("浙","A","1","2","3","4","5");
	
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
					testResult.put("4-007",f);
					return;
				}
				//以下仍然执行检测，重新开始接车
				jieche("浙","A","1","2","3","4","5");
				
				jiance.click();
				UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
				if(jianceleixin.exists()&&kaishijiance.exists()){
					System.out.println("近期检测无异常车辆开始检测进入检测套餐选择页面成功");	
					testResult.put("4-007",p);
				}
				else{
					System.out.println("近期检测无异常车辆开始检测按钮点击后不能正常跳转套餐选择页面");
					testResult.put("4-007",f);
				}
			}
			else{
				System.out.println("界面异常");
				testResult.put("4-007",f);
			}
		}
		else{
			System.out.println("车辆状况信息异常");
			testResult.put("4-007",f);
		}
	}
	else{
		System.out.println("近期检测无异常车辆未给出操作建议");
		testResult.put("4-007",f);
	}
	}
	
	//4-006用于测试接车近期接待过有异常的车辆
	public void testJieChe6() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve("18581540581","123456");
		String s="开始测试接待近期接待过有异常车辆，执行时间:"+new Date()+"\r\n";
		System.out.println(s);
		jieche("浙","A","1","2","3","4","4");
		
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
					if (tasker.exists()&&queren.exists()){
						tasker.click();
						queren.click();
					}
					
					//2.1.0修改，点击不检测了直接跳转到任务界面
					//UiObject returnBtn=new UiObject(new UiSelector().className("android.widget.ImageView").instance(0));
					//returnBtn.clickAndWaitForNewWindow();
					//returnBtn.clickAndWaitForNewWindow();
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
						testResult.put("4-006",f);
						return;
					}
					//2.1.0修改，从任物流确认施工后跳转到任务界面
					//UiObject rtnbtn=new UiObject(new UiSelector().className("android.widget.ImageView").instance(0));
					//rtnbtn.click();
					//重新接车，执行重新检测
					//莫名其妙接车又没有反应
					renwuliu.click();
					jieche("浙","A","1","2","3","4","4");
					jiance.click();
					UiObject jianceleixin=new UiObject(new UiSelector().text("检测类型")); 
					if(jianceleixin.exists()&&kaishijiance.exists()){
						System.out.println("近期检测有异常车辆开始检测进入检测套餐选择页面成功");	
						testResult.put("4-006",p);
					}
					else{
						System.out.println("近期检测有异常车辆开始检测按钮点击后不能正常跳转套餐选择页面");
						testResult.put("4-006",f);
					}
				}
				else{
					System.out.println("界面异常");
					testResult.put("4-006",f);
				}
			}
			else{
				System.out.println("车辆状况信息异常");
				testResult.put("4-006",f);
			}
		}
		else{
			System.out.println("近期检测有异常车辆未给出操作建议");
			testResult.put("4-006",f);
		}
		}
	
	//2-002，今日数据，不测数据，仅测试跳转
	public void testtodayData() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve("18581540581","123456");
	
		String s="开始测试我的界面今日数据:"+new Date()+"\r\n";
		System.out.println(s);
		
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
			testResult.put("2-002",p);
		}
		else{
			testResult.put("2-002",f);
		}
	}
	
	//4-008,录入报告，修改车辆信息，前置条件，浙A12346近期检测无问题车辆
	public void report_modifycarinfo() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve("18581540581","123456");
		jieche("浙","A","1","2","3","4","6");
		UiObject jiance=new UiObject(new UiSelector().text("不，开始检测"));
		jiance.click();
		String s="开始测试录入报告时修改车辆信息:"+new Date()+"\r\n";
		System.out.println(s);
		UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测")); 
		UiObject byqm=new UiObject(new UiSelector().text("保养全面检测"));
		UiObject tasker=new UiObject(new UiSelector().textContains("(我)"));
		UiObject queren=new UiObject(new UiSelector().text("确定"));
		byqm.click();
		kaishijiance.clickAndWaitForNewWindow();
		if (tasker.exists()&&queren.exists()){
			tasker.click();
			queren.click();
		}
		UiObject modifycarinfo=new UiObject(new UiSelector().textContains("修改车辆信"));
		modifycarinfo.clickAndWaitForNewWindow(5000);
		UiObject updatecustinfo=new UiObject(new UiSelector().text("更新客户资料"));
		if (updatecustinfo.exists()){
			UiObject rtnBtn=new UiObject(new UiSelector().className("android.widget.ImageView").instance(0));
			rtnBtn.click();
			testResult.put("4-008",p);
			
		}
		else{
			testResult.put("4-008",f);
		}	
	}
	//4-009 浙A12346近期检测无问题车辆
	public void testbatchSetNormal() throws UiObjectNotFoundException, IOException{
		report_modifycarinfo();
		
		String s="开始测试录入报告时批量设置良好:"+new Date()+"\r\n";
		System.out.println(s);
		UiObject batSet=new UiObject(new UiSelector().text("批量设置良好"));
		batSet.click();
		UiObject preview=new UiObject(new UiSelector().text("预览"));
		preview.click();
		UiObject tasker=new UiObject(new UiSelector().textContains("(我)"));
		UiObject queren=new UiObject(new UiSelector().text("确定"));
		UiObject generateRpt=new UiObject(new UiSelector().description("generate_report"));
		generateRpt.clickAndWaitForNewWindow();
		UiObject previewtitle=new UiObject(new UiSelector().text("预览报告"));
		if (previewtitle.exists()){
			generateRpt.click();
		}
		UiObject alerttitle=new UiObject(new UiSelector().text("温馨提示"));
		if (alerttitle.exists()){
			UiObject queding=new UiObject(new UiSelector().text("确定"));
			queding.click();
		}
		if (tasker.exists()&&queren.exists()){
			tasker.click();
			queren.click();
		}
		device.waitForIdle(8000);
		UiObject sendRpt=new UiObject(new UiSelector().description("generate_report_btn")).getChild(new UiSelector().className("android.view.View"));
		sendRpt.click();
		//神奇，很多时候点了sendRpt没反应，再点一次
		UiObject newtitle=new UiObject(new UiSelector().text("推送报告"));
		if(!newtitle.exists()){
			sendRpt.clickAndWaitForNewWindow();
		}
		device.waitForIdle(5000);
		//补充RPT内容的校验，状况良好20项，100分
		UiObject skip=new UiObject(new UiSelector().text("跳过"));
		skip.click();
		//判断我的待办界面是否还有浙A12345相关任务
		UiObject daiban=new UiObject(new UiSelector().text("我的待办"));
		UiObject yiban=new UiObject(new UiSelector().text("我的已办"));
		daiban.clickAndWaitForNewWindow();
		device.waitForIdle(3000);
		device.swipe(544, 420, 544, 804, 30);
		yiban.click();
		daiban.click();
		UiObject chepai6=new UiObject(new UiSelector().text("浙A12346"));
		if (!chepai6.exists()){
			System.out.println("检测无问题车辆任务在推送报告后不会产生确认施工的任务");
			UiObject renwuliu=new UiObject(new UiSelector().text("任务流"));
			renwuliu.click();
			UiObject allTaskFlow=new UiObject(new UiSelector().text("全部"));
			allTaskFlow.clickAndWaitForNewWindow();
			device.waitForIdle(1000);
			device.swipe(544, 420, 544, 804, 30);
			device.waitForIdle(1000);
			UiObject carTaskFlow=new UiObject(new UiSelector().text("浙A12346").instance(0));
			carTaskFlow.clickAndWaitForNewWindow();
			UiObject title=new UiObject(new UiSelector().text("任务流程"));
			if (title.exists()){
				UiObject shigong=new UiObject(new UiSelector().text("确认施工"));
				if(!shigong.exists()){
					System.out.println("检测无问题车辆任务流不会流转到确认施工");
					testResult.put("4-009",p);
				}
				else{
					testResult.put("4-009",f);
				}
			}
			
		}
		else{
			testResult.put("4-009",f);
			System.out.println("批量设置良好产生确认施工任务");
		}
		
		
	}
	//4-010 浙A12347近期检测无问题车辆
	public void testbatchAndMoidfyIssue() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve("18581540581","123456");
		jieche("浙","A","1","2","3","4","7");
		UiObject jiance=new UiObject(new UiSelector().text("不，开始检测"));
		jiance.click();
		UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测")); 
		UiObject taocan=new UiObject(new UiSelector().text("洗车快速检测"));
		UiObject tasker=new UiObject(new UiSelector().textContains("(我)"));
		UiObject queren=new UiObject(new UiSelector().text("确定"));
		taocan.click();
		kaishijiance.clickAndWaitForNewWindow();
		if (tasker.exists()&&queren.exists()){
			tasker.click();
			queren.click();
		}
		
		String s="开始测试录入报告时批量设置良好后修改为问题项:"+new Date()+"\r\n";
		System.out.println(s);
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
		/*
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
		//从相册选择上传在别的用例体现*/
		
		issueEn.click();
		UiObject preview=new UiObject(new UiSelector().text("预览"));
		preview.click();
		UiObject generateRpt=new UiObject(new UiSelector().description("generate_report"));
		generateRpt.clickAndWaitForNewWindow();
		UiObject alerttitle=new UiObject(new UiSelector().text("温馨提示"));
		if (alerttitle.exists()){
			UiObject queding=new UiObject(new UiSelector().text("确定"));
			queding.click();
		}
		if (tasker.exists()&&queren.exists()){
			tasker.click();
			queren.click();
		}
		device.waitForIdle(10000);
		UiObject sendRpt=new UiObject(new UiSelector().description("generate_report_btn")).getChild(new UiSelector().className("android.view.View"));
		sendRpt.click();
		UiObject newtitle=new UiObject(new UiSelector().text("推送报告"));
		if(!newtitle.exists()){
			sendRpt.clickAndWaitForNewWindow();
		}
		device.waitForIdle(5000);
		//补充RPT内容的校验
		UiObject skip=new UiObject(new UiSelector().text("跳过"));
		skip.click();
		//判断我的待办界面是否还有浙A12345相关任务
		UiObject daiban=new UiObject(new UiSelector().text("我的待办"));
		daiban.click();
		device.swipe(544, 420, 544, 804, 30);
		device.waitForIdle(3000);
		UiObject chepai=new UiObject(new UiSelector().text("浙A12347"));
		if (chepai.exists()){
			//确认施工在此用例继续体现，从人物流进
			UiObject renwuliu=new UiObject(new UiSelector().text("任务流"));
			renwuliu.click();
			UiObject NotSolveTaskFlow=new UiObject(new UiSelector().text("未完成"));
			NotSolveTaskFlow.click();
			device.swipe(544, 420, 544, 804, 30);
			device.waitForIdle(1000);
			UiObject carTaskFlow=new UiObject(new UiSelector().text("浙A12347").instance(0));
			carTaskFlow.clickAndWaitForNewWindow();
			UiObject title=new UiObject(new UiSelector().text("任务流程"));
			if (title.exists()){
				UiObject quwancheng=new UiObject(new UiSelector().textContains("去完成").instance(1));
				quwancheng.clickAndWaitForNewWindow();
				UiObject bendianxiufu1=new UiObject(new UiSelector().text("本店修复").instance(0));
				UiObject bendianxiufu2=new UiObject(new UiSelector().text("本店修复").instance(1));
				bendianxiufu1.click();
				bendianxiufu2.click();
				UiObject tijiao=new UiObject(new UiSelector().text("提交"));
				tijiao.click();
				testResult.put("4-010",p);	
			}
			else{
				testResult.put("4-010",f);
			}
			
		}
	}
	//4-011 浙A12348，近期检测无问题车辆
	public void testissueAndBatchSet() throws IOException, UiObjectNotFoundException{
		vcmlogginachieve("18581540581","123456");
		jieche("浙","A","1","2","3","4","8");
		UiObject jiance=new UiObject(new UiSelector().text("不，开始检测"));
		jiance.click();
		UiObject kaishijiance=new UiObject(new UiSelector().text("开始检测")); 
		UiObject taocan=new UiObject(new UiSelector().text("洗车快速检测"));
		UiObject tasker=new UiObject(new UiSelector().textContains("(我)"));
		UiObject queren=new UiObject(new UiSelector().text("确定"));
		taocan.click();
		kaishijiance.clickAndWaitForNewWindow();
		if (tasker.exists()&&queren.exists()){
			tasker.click();
			queren.click();
		}
		//FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试录入报告时设置问题项后批量设置良好:"+new Date()+"\r\n";
		//fos.write(s.getBytes());
		System.out.println(s);
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
		UiObject generateRpt=new UiObject(new UiSelector().description("generate_report"));
		generateRpt.clickAndWaitForNewWindow();
		UiObject alerttitle=new UiObject(new UiSelector().text("温馨提示"));
		if (alerttitle.exists()){
			UiObject queding=new UiObject(new UiSelector().text("确定"));
			queding.click();
		}
		if (tasker.exists()&&queren.exists()){
			tasker.click();
			queren.click();
		}
		device.waitForIdle(5000);
		UiObject sendRpt=new UiObject(new UiSelector().description("generate_report_btn")).getChild(new UiSelector().className("android.view.View"));
		sendRpt.click();
		UiObject newtitle=new UiObject(new UiSelector().text("推送报告"));
		if(!newtitle.exists()){
			sendRpt.clickAndWaitForNewWindow();
		}
		device.waitForIdle(5000);
		//补充RPT内容的校验
		UiObject skip=new UiObject(new UiSelector().text("跳过"));
		skip.click();
		//判断我的待办界面是否还有浙A12345相关任务
		UiObject daiban=new UiObject(new UiSelector().text("我的待办"));
		daiban.click();
		device.swipe(544, 420, 544, 804, 30);
		device.waitForIdle(3000);
		UiObject chepai=new UiObject(new UiSelector().text("浙A12348"));
		if (chepai.exists()){
			//确认施工在此用例继续体现，从人物流进
			UiObject renwuliu=new UiObject(new UiSelector().text("任务流"));
			renwuliu.click();
			UiObject NotSolveTaskFlow=new UiObject(new UiSelector().text("未完成"));
			NotSolveTaskFlow.click();
			device.waitForIdle(1000);
			device.swipe(544, 420, 544, 804, 30);
			device.waitForIdle(1000);
			UiObject carTaskFlow=new UiObject(new UiSelector().text("浙A12348").instance(0));
			carTaskFlow.clickAndWaitForNewWindow();
			UiObject title=new UiObject(new UiSelector().text("任务流程"));
			if (title.exists()){
				UiObject quwancheng=new UiObject(new UiSelector().textContains("去完成").instance(1));
				quwancheng.clickAndWaitForNewWindow();
				UiObject bendianxiufu1=new UiObject(new UiSelector().text("他店修复"));
				bendianxiufu1.click();
				UiObject tijiao=new UiObject(new UiSelector().text("提交"));
				tijiao.click();
				testResult.put("4-011",p);	
			}
			else{
				testResult.put("4-011",f);	
			}
			
		}
	}
	public void testjingyingtongji() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve("18581540581","123456");
		//FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试我的界面经营统计:"+new Date()+"\r\n";
		//fos.write(s.getBytes());
		System.out.println(s);
		UiObject MeBtn=new UiObject(new UiSelector().text("我的"));
		MeBtn.click();
		UiObject jingyingBtn=new UiObject(new UiSelector().text("经营统计"));
		jingyingBtn.click();
		
		UiObject title=new UiObject(new UiSelector().text("经营统计"));
		UiObject anri=new UiObject(new UiSelector().text("按日"));
		UiObject anzhou=new UiObject(new UiSelector().text("按周"));
		UiObject anyue=new UiObject(new UiSelector().text("按月"));
		UiObject yuangong=new UiObject(new UiSelector().text("员工明细"));
		if (title.exists()&&anri.exists()&&anzhou.exists()&&anyue.exists()&&yuangong.exists()){
			anzhou.click();
			anyue.click();
			anri.click();
			testResult.put("2-009",p);	
		}
		else{
			testResult.put("2-009",f);	
		}
		System.out.println(testResult);
	}
	public void testmodifypwd() throws UiObjectNotFoundException, IOException{
	qiehuanzhanghao("18581540583","123456");
	UiObject MeBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("我的"));
	//FileOutputStream fos=new FileOutputStream(filepath,true);
	String s="开始执行修改密码，执行时间:"+new Date()+"\r\n";
	//fos.write(s.getBytes());
	System.out.println(s);
	UiObject modPwdBtn=new UiObject(new UiSelector().text("密码修改"));
	modPwdBtn.click();
	UiObject newpwd=new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
	UiObject newpwd2=new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
	newpwd.setText("123");
	newpwd2.setText("123");
	//获取toast信息比较麻烦，后续有时间校验
	UiObject modpwdbtn=new UiObject(new UiSelector().description("change_password_btn"));
	modpwdbtn.click();
	UiObject title=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.widget.TextView"));
	String titleContent=title.getText();
	if (titleContent.equals("重置密码")){
		testResult.put("2-010",p);	
	}
	else{
		testResult.put("2-010",f);
	}
	//点击返回
	UiObject rtnBtn=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.view.View").instance(0));
	rtnBtn.click();
	modPwdBtn.click();
	newpwd.setText("1234567");
	newpwd2.setText("12345678");
	modpwdbtn.click();
	titleContent=title.getText();
	if (titleContent.equals("重置密码")){
		testResult.put("2-011",p);
	}
	else{
		testResult.put("2-011",f);
	}
	rtnBtn.click();
	modPwdBtn.click();
	newpwd.setText("123456a");
	newpwd2.setText("123456a");
	modpwdbtn.click();
	titleContent=title.getText();
	
	if (titleContent.equals("登录")){
		testResult.put("2-012",p);
	}
	else{
		testResult.put("2-012",f);
	}
	//密码改回去
	vcmlogginachieve("18581540583","123456a");
	MeBtn.click();
	modPwdBtn.click();
	newpwd.setText("123456");
	newpwd2.setText("123456");
	modpwdbtn.click();
	}
	public void testfeedback() throws UiObjectNotFoundException, IOException{
	vcmlogginachieve("18581540581","123456");
	//FileOutputStream fos=new FileOutputStream(filepath,true);
	String s="开始测试帮助与反馈，执行时间:"+new Date()+"\r\n";
	//fos.write(s.getBytes());
	System.out.println(s);
	UiObject MeBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("我的"));
	MeBtn.click();
	UiObject feedback=new UiObject(new UiSelector().text("帮助与反馈"));
	feedback.click();
	UiObject feedback2=new UiObject(new UiSelector().text("反馈"));
	int xinban=0;
	if (feedback2.exists()){
		xinban=1;
		feedback2.click();
	}
	UiObject rtnBtn=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.view.View").instance(0));
	rtnBtn.click();
	if (xinban==1){
		feedback2.click();
	}
	else{
		feedback.click();
	}
	UiObject issueDesc=new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
	UiObject phone=new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
	issueDesc.setText("test input issue description lalala");
	phone.setText("15261180520");
	//按确认键，否则点击提交时被视为输入完毕的动作
	device.pressEnter();
	UiObject submitBtn=new UiObject(new UiSelector().text("提 交"));
	submitBtn.click();
	if(xinban==1){
		rtnBtn.click();
	}
	//数据库清除数据与校验暂时不做
	
	if (feedback.exists()&&MeBtn.exists()){
		testResult.put("2-013",p);
	}
	else{
		testResult.put("2-013",f);
	}
	}
	public void testaboutus() throws UiObjectNotFoundException, IOException{
		vcmlogginachieve("18581540581","123456");
		//FileOutputStream fos=new FileOutputStream(filepath,true);
		String s="开始测试关于车况大师，执行时间:"+new Date()+"\r\n";
		//fos.write(s.getBytes());
		System.out.println(s);
		UiObject MeBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("我的"));
		MeBtn.click();
		UiObject aboutus=new UiObject(new UiSelector().text("关于车况大师"));
		aboutus.click();
		UiObject rtnBtn=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.view.View").instance(0));
		rtnBtn.click();
		aboutus.click();
		UiObject banbenhao=new UiObject(new UiSelector().textMatches(".*版本号.*"));
		UiObject prodinfo=new UiObject(new UiSelector().textMatches("产品介绍"));
		UiObject funcdesc=new UiObject(new UiSelector().textMatches("功能描述"));
		//按确认键，否则点击提交时被视为输入完毕的动作
		//数据库清除数据与校验暂时不做
		
		if (banbenhao.exists()&&prodinfo.exists()&&funcdesc.exists()){
			testResult.put("2-014",p);
		}
		else{
			testResult.put("2-014",f);
		}	
	}
	//最后一个case,用于输出测试结果到文件
	public void testzzzlastcaseinput() throws IOException{
	System.out.println("输出结果");	
	testResult.put("9-999",p);
	FileOutputStream fos=new FileOutputStream(filepath,true);
	Set<String> set = testResult.keySet(); //key装到set中
    Iterator<String> it = set.iterator(); //返回set的迭代器 装的key值
    while(it.hasNext()){
    String key = (String)it.next();
    String value = (String)testResult.get(key);
    String s=key+":"+value+"\r\n";
    fos.write(s.getBytes());
	}
    Date dtend=new Date();
    long diff = dtend.getTime()-dt.getTime();
	int hh=(int) diff/3600000;
	int mm=(int)(diff-hh*3600000)/60000;
	int ss=(int)(diff-hh*3600000-mm*60000)/1000;
	int ms=(int) diff%1000;
	String s="执行耗时:"+hh+"时"+mm+"分"+ss+"秒"+ms+"毫秒";
	fos.write(s.getBytes());
    fos.close();
	}
	//切换账号，并登录到我的界面
	public void qiehuanzhanghao(String username,String pwd) throws UiObjectNotFoundException{
		vcmlogginachieve("18581540581","123456");
		//退出登录切换账号
		UiObject MeBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("我的"));
		MeBtn.click();
		UiObject lgOutBtn=new UiObject(new UiSelector().className("android.widget.TextView").text("退出登录"));
		lgOutBtn.click();
		vcmlogginachieve(username,pwd);
		MeBtn.click();
	}
	//查看车主评价,不要对此用户增加评价
	public void testEvatoMe() throws UiObjectNotFoundException{
		qiehuanzhanghao("18581540582","123456");
		UiObject carOwnerEva=new UiObject(new UiSelector().text("车主评价"));
		carOwnerEva.click();
		UiObject EvaToMe=new UiObject(new UiSelector().description("my_appraise_btn"));
		EvaToMe.click();
		UiObject evaitem1=new UiObject(new UiSelector().description("appraise_item").instance(0));
		UiObject evaitem2=new UiObject(new UiSelector().description("appraise_item").instance(1));
		UiObject evaitem3=new UiObject(new UiSelector().description("appraise_item").instance(2));
		UiObject evaitem4=new UiObject(new UiSelector().description("appraise_item").instance(3));
		String carNo,remark,evacontent,evacontent2;
		UiObject chepai4=evaitem4.getChild(new UiSelector().className("android.view.View").instance(0)).getChild(new UiSelector().className("android.widget.TextView").instance(0));
		UiObject content4=evaitem4.getChild(new UiSelector().className("android.view.View").instance(1)).getChild(new UiSelector().className("android.view.View")).getChild(new UiSelector().className("android.widget.TextView"));
		screenshot("2-007");
		carNo=chepai4.getText();
		evacontent=content4.getText();
		if (carNo.equals("川A12321")&&evacontent.equals("报告有用")){
			testResult.put("2-003", p);
			
		}
		else{
			testResult.put("2-003", f);
		}
		UiObject chepai3=evaitem3.getChild(new UiSelector().className("android.view.View").instance(0)).getChild(new UiSelector().className("android.widget.TextView").instance(0));
		UiObject content3=evaitem3.getChild(new UiSelector().className("android.view.View").instance(1)).getChild(new UiSelector().className("android.view.View")).getChild(new UiSelector().className("android.widget.TextView"));
		carNo=chepai3.getText();
		evacontent=content3.getText();
		if (carNo.equals("川A12322")&&evacontent.equals("报告看不懂")){
			testResult.put("2-004", p);
			
		}
		else{
			testResult.put("2-004", f);
		}
		UiObject chepai2=evaitem2.getChild(new UiSelector().className("android.view.View").instance(0)).getChild(new UiSelector().className("android.widget.TextView").instance(0));
		UiObject content2_1=evaitem2.getChild(new UiSelector().className("android.view.View").instance(1)).getChild(new UiSelector().className("android.view.View").instance(0)).getChild(new UiSelector().className("android.widget.TextView"));
		UiObject content2_2=evaitem2.getChild(new UiSelector().className("android.view.View").instance(1)).getChild(new UiSelector().className("android.view.View").instance(1)).getChild(new UiSelector().className("android.widget.TextView"));
		carNo=chepai2.getText();
		evacontent=content2_1.getText();
		evacontent2=content2_2.getText();
		if (carNo.equals("川A12323")&&evacontent.equals("报告有用")&&evacontent2.equals("推销很烦")){
			testResult.put("2-005", p);
			
		}
		else{
			testResult.put("2-005", f);
		}
		UiObject chepai1=evaitem1.getChild(new UiSelector().className("android.view.View").instance(0)).getChild(new UiSelector().className("android.widget.TextView").instance(0));
		UiObject content1_1=evaitem1.getChild(new UiSelector().className("android.view.View").instance(1)).getChild(new UiSelector().className("android.view.View").instance(0)).getChild(new UiSelector().className("android.widget.TextView"));
		UiObject content1_2=evaitem1.getChild(new UiSelector().className("android.view.View").instance(1)).getChild(new UiSelector().className("android.view.View").instance(1)).getChild(new UiSelector().className("android.widget.TextView"));
		carNo=chepai1.getText();
		evacontent=content1_1.getText();
		remark=content1_2.getText();
		if (carNo.equals("川A12321")&&evacontent.equals("推销很烦")&&remark.matches(".*测试写评价.*")){
			testResult.put("2-007", p);
			
		}
		else{
			testResult.put("2-007", f);
		}
		//退出当前账号
		qiehuanzhanghao("18581540581","123456");
		
	}
	//车主评价查看所有评价
	public void testEvaToAll() throws UiObjectNotFoundException{
		qiehuanzhanghao("18581540583","123456");
		UiObject carOwnerEva=new UiObject(new UiSelector().text("车主评价"));
		carOwnerEva.click();
		UiObject EvaToMe=new UiObject(new UiSelector().description("my_appraise_btn"));
		EvaToMe.click();
		int tempflag1,tempflag2;
		UiObject evaitem1=new UiObject(new UiSelector().description("appraise_item").instance(0));
		if (!evaitem1.exists()){
			tempflag1=1;
		}else tempflag1=0;
		UiObject AllEva=new UiObject(new UiSelector().description("all_appraise_btn"));
		AllEva.click();
		if (evaitem1.exists()){
			 tempflag2=1;
		}else tempflag2=0;
		if (tempflag1*tempflag2==1){
			testResult.put("2-008", p);
		}
		else{
			testResult.put("2-008", f);
		}
		qiehuanzhanghao("18581540581","123456");
	}
	//3-001~3-005
	public void testCar1() throws UiObjectNotFoundException{
		vcmlogginachieve("18581540581","123456");
		UiObject cars=new UiObject(new UiSelector().text("车辆"));
		cars.click();
		UiScrollable scroll=new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
		UiObject carNo=scroll.getChildByText(new UiSelector().className("android.widget.TextView"),"藏A12340", true);
		UiObject carBrand=new UiObject(new UiSelector().className("android.widget.TextView").text("保时捷"));
		UiObject carOwner=new UiObject(new UiSelector().className("android.widget.TextView").text("dummy"));
		if(carNo.exists()&&carBrand.exists()&&carOwner.exists()){
			testResult.put("3-001",p);
		}
		else{
			testResult.put("3-001",f);
		}
		
		
		UiObject searchBtn=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.view.View").instance(0));
		searchBtn.click();
		UiObject inputCar=new UiObject(new UiSelector().className("android.widget.EditText"));
		String temp="藏A123";
		inputCar.setText(Utf7ImeHelper.e(temp));
		screenshot("2-002");
		for(int i=4;i>0;i--){
			device.pressDelete();
		}
		temp="藏A12340";
		inputCar.setText(Utf7ImeHelper.e(temp));
		device.pressEnter();
		UiObject searchResult=new UiObject(new UiSelector().description("search_car_list_item").instance(0));
		UiObject licenseObj=searchResult.getChild(new UiSelector().className("android.widget.TextView").instance(0));
		UiObject dateObj=searchResult.getChild(new UiSelector().className("android.widget.TextView").instance(1));
		String license=licenseObj.getText();
		String cardate=dateObj.getText();
		if (license.equals("藏A12340")&&cardate.equals("2017.11.08")){
			testResult.put("3-002",p);
		}else testResult.put("3-002",f);
	
		
		
		searchResult.click();
		UiObject phonenumber=new UiObject(new UiSelector().text("17364782550"));
		if (carOwner.exists()&&phonenumber.exists()){
			testResult.put("3-003", p);
		}
		else{
			testResult.put("3-003", f);
		}
		
		
		UiObject updatecarinfo=new UiObject(new UiSelector().description("update_car_info"));
		updatecarinfo.click();
		UiObject editMileage=new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
		UiObject editOwner=new UiObject(new UiSelector().className("android.widget.EditText").instance(2));
		UiObject editPhone=new UiObject(new UiSelector().className("android.widget.EditText").instance(3));
		editMileage.clearTextField();
		for (int i=11;i>0;i--){
			device.pressDelete();
		}
		editOwner.clearTextField();
		for (int i=11;i>0;i--){
			device.pressDelete();
		}
		editPhone.clearTextField();
		for (int i=11;i>0;i--){
			device.pressDelete();
		}
		editMileage.setText(Utf7ImeHelper.e("10001"));
		editOwner.setText(Utf7ImeHelper.e("dog"));
		editPhone.setText(Utf7ImeHelper.e("17364782551"));
		device.pressEnter();
		UiObject saveinfo=new UiObject(new UiSelector().description("save_user_info"));
		saveinfo.click();
		device.waitForIdle(5000);
		UiObject newPhone=new UiObject(new UiSelector().text("17364782551"));
		UiObject newOwner=new UiObject(new UiSelector().text("dog"));
		UiObject newMile=new UiObject(new UiSelector().textContains("10001"));
		if (newPhone.exists()&&newOwner.exists()&&newMile.exists()){
			testResult.put("3-004", p);
		}
		else{
			testResult.put("3-004", f);
		}
		System.out.println("将车辆信息修改回去");
		updatecarinfo.click();
		editMileage.clearTextField();
		for (int i=11;i>0;i--){
			device.pressDelete();
		}
		editOwner.clearTextField();
		for (int i=11;i>0;i--){
			device.pressDelete();
		}
		editPhone.clearTextField();
		for (int i=11;i>0;i--){
			device.pressDelete();
		}
		editMileage.setText(Utf7ImeHelper.e("10000"));
		editOwner.setText(Utf7ImeHelper.e("dummy"));
		editPhone.setText(Utf7ImeHelper.e("17364782550"));
		device.pressEnter();
		saveinfo.click();
		
		UiObject querenshigongshu=new UiObject(new UiSelector().textContains("待施工确认 "));
		UiObject ququeren=new UiObject(new UiSelector().text("去确认"));
		String str=querenshigongshu.getText();
		if (str.equals("待施工确认 (0)")&&(!ququeren.exists())){
			testResult.put("3-005", p);
		}
		else{
			testResult.put("3-005", f);
		}
		
		System.out.println(testResult);
	
		
	}
	//从车辆页面进入某一辆车辆,temp是车牌
	public void car_list_detail(String temp) throws UiObjectNotFoundException{
		vcmlogginachieve("18581540581","123456");
		UiObject cars=new UiObject(new UiSelector().text("车辆"));
		cars.click();
		UiObject searchBtn=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.view.View").instance(0));
		searchBtn.click();
		UiObject inputCar=new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
		inputCar.click();
		inputCar.setText(Utf7ImeHelper.e(temp));
		device.pressEnter();
		UiObject searchResult=new UiObject(new UiSelector().description("search_car_list_item").instance(0));
		searchResult.click();
	}
	//3-009,3-010
	public void testCar1_wbjl() throws UiObjectNotFoundException{
		//以下实现进入车辆藏A12340
		car_list_detail("藏A12340");
		
		System.out.println("开始测试从车辆界面查看维保记录");
		UiObject wbjl=new UiObject(new UiSelector().description("car_check_detail"));
		wbjl.clickAndWaitForNewWindow();
		UiObject time=new UiObject(new UiSelector().text("2017.11.08"));
		UiObject item1=new UiObject(new UiSelector().text("前刹车片"));
		UiObject item2=new UiObject(new UiSelector().text("空调制冷"));
		int num=getObjectCNT();
		System.out.println("他店修复的数量是:"+num);
		if(time.exists()&&item1.exists()&&item2.exists()&&num==1){
			testResult.put("3-009",p);
		}
		else{
			testResult.put("3-009",f);
		}
		
		System.out.println("开始测试维保记录详情3-010");
		item2.click();
		UiObject issueinfo1=new UiObject(new UiSelector().textContains("出风口温度 : 10-15℃ "));
		UiObject issueinfo2=new UiObject(new UiSelector().text("他店修复"));
		UiObject issueinfo3=new UiObject(new UiSelector().text("密切关注"));
		UiObject issueinfo4=new UiObject(new UiSelector().textMatches(".*2017/11/08 09:40.*"));
		UiObject issueinfo5=new UiObject(new UiSelector().textMatches(".*2017/11/08 09:49.*"));
		int issueResult1=0,issueResult2=0;
		if (issueinfo1.exists()&&issueinfo2.exists()&&issueinfo3.exists()&&issueinfo4.exists()&&issueinfo5.exists()){
			issueResult1=1;
		}
		UiObject rtnBtn=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.view.View").instance(0));
		rtnBtn.click();
		item1.click();
		UiObject issue2info1=new UiObject(new UiSelector().textContains("剩余厚度 : <2mm"));
		UiObject issue2info2=new UiObject(new UiSelector().text("本店修复"));
		UiObject issue2info3=new UiObject(new UiSelector().text("急需处理"));
		UiObject issue2info4=new UiObject(new UiSelector().textMatches(".*2017/11/08 09:40.*"));
		UiObject issue2info5=new UiObject(new UiSelector().textMatches(".*2017/11/08 09:49.*"));
		if (issue2info1.exists()&&issue2info2.exists()&&issue2info3.exists()&&issue2info4.exists()&&issue2info5.exists()){
			issueResult2=1;
		}
		if (issueResult1==1&&issueResult2==1){
			testResult.put("3-010",p);
		}
		else{
			testResult.put("3-010", f);
		}
		System.out.println(testResult);
	}
	//3-011,3-012
	public void testCar1_check() throws UiObjectNotFoundException{
		car_list_detail("藏A12340");
		UiObject checkhis=new UiObject(new UiSelector().description("car_detection"));
		checkhis.click();
		UiObject check_his_list=new UiObject(new UiSelector().description("detection_item").instance(0));
		UiObject scoreobj=new UiObject(new UiSelector().textContains("分"));
		String score=scoreobj.getText();
		//System.out.println(score);
		if (score.equals("85分")){
			testResult.put("3-011",p);
		}
		else{
			testResult.put("3-011",f);
		}
		check_his_list.clickAndWaitForNewWindow();
		UiObject title=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.widget.TextView"));
		UiObject sendBtn=new UiObject(new UiSelector().text("发给车主"));
		if(title.getText().equals("藏A12340的检测报告")&&sendBtn.exists()){
			testResult.put("3-012", p);
		}
		else{
			testResult.put("3-012", f);
		}
		
		
	}
	//3-013	
	public void testCar1_eva() throws UiObjectNotFoundException{
		car_list_detail("藏A12340");
		UiObject car_eva=new UiObject(new UiSelector().description("car_appraise"));
		car_eva.click();
		UiObject title=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.widget.TextView"));
		UiObject eva_content=new UiObject(new UiSelector().text("检测专业"));
		//System.out.println(title.getText());
		if (title.getText().equals("车主评价")&&eva_content.exists()){
			testResult.put("3-013",p);
		}
		else{
			testResult.put("3-013",f);
		}
		
		
	}
	//3-014
	public void testCar2_eva() throws UiObjectNotFoundException{
		car_list_detail("藏A12341");
		UiObject car_eva=new UiObject(new UiSelector().description("car_appraise"));
		car_eva.click();
		UiObject title=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.widget.TextView"));
		UiObject eva_content=new UiObject(new UiSelector().text("暂无车主评价"));
		if (title.getText().equals("车主评价")&&eva_content.exists()){
			testResult.put("3-014",p);
		}
		else{
			testResult.put("3-014",f);
		}
		System.out.println(testResult);
	}
	//3-006
	public void testCar2_qrsg() throws UiObjectNotFoundException{
		car_list_detail("藏A12341");
		UiObject querenshigongshu=new UiObject(new UiSelector().textContains("待施工确认 "));
		UiObject ququeren=new UiObject(new UiSelector().text("去确认"));
		UiObject issue1=new UiObject(new UiSelector().text("空调制冷"));
		UiObject issue2=new UiObject(new UiSelector().text("前刹车片"));
		if (querenshigongshu.getText().equals("待施工确认 (2)")&&ququeren.exists()&&issue1.exists()&&issue2.exists()){
			testResult.put("3-006",p);
		}
		else{
			testResult.put("3-006",f);
		}
		ququeren.click();
		//here to continue
		UiObject title=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.widget.TextView").instance(0));
		if (title.getText().equals("确认施工")){
			testResult.put("3-007", p);
		}
		else{
			testResult.put("3-007", f);
		}
		
		System.out.println(testResult);
	}
	//3-008
	public void testCar2_wb() throws UiObjectNotFoundException{
		car_list_detail("藏A12341");
		UiObject wbjl=new UiObject(new UiSelector().description("car_check_detail"));
		wbjl.click();
		UiObject title=new UiObject(new UiSelector().description("header_wrap")).getChild(new UiSelector().className("android.widget.TextView").instance(0));
		UiObject remind=new UiObject(new UiSelector().text("暂无维保记录"));
		if (title.getText().equals("维保记录")&&remind.exists()){
			testResult.put("3-008", p);
		}
		else{
			testResult.put("3-008", f);
		}
		System.out.println(testResult);
	}
	
	
	
	public int getObjectCNT() throws UiObjectNotFoundException{
		int i=0;
		UiObject a=new UiObject(new UiSelector().className("android.widget.TextView").text("他店修复").instance(i));
		int num=0;
		for(;i<10;i++){
				a=new UiObject(new UiSelector().className("android.widget.TextView").text("他店修复").instance(i));	
				if(a.exists()){
				num=num+1;	
				}else break;
		}
		return num;
	}
	
	//用于某个用例失败继续调试
	public void testcontinue() throws IOException, UiObjectNotFoundException{
	
		
		/*comments:
		1.修改帮助与反馈层级目录，兼容老版本
		2.任务指派根据界面灵活判断
		3.修改输出报告的方式，将测试结果保存到内存中，最后一起输出到testreport，提升效率并且方便去重与排序。
		4.截图图片命名修改
		5.生成报告增加判断是否需要确认
		6.修改4-008,4-009,4-010，4-011用例车辆，减小用例相关性
		7.加载报告页面，sendRpt没反应，判断无跳转再点击一次
		8.修改4-006，不检测跳转任务页面逻辑。
		9.接车参数化
		10.新增车辆页面测试用例3-001~3-014
		*/
	}
}

	
	


