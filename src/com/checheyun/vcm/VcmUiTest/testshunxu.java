package com.checheyun.vcm.VcmUiTest;

//import java.util.Random;

import java.io.File;




import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class testshunxu extends UiAutomatorTestCase {
	String p="pass";
	String f="fail";
	Date dt = new Date();  
	SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");  
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
		 String jar_name = "CaseOrder";
		 //生成jar的名字
		 String test_class = "com.checheyun.vcm.VcmUiTest.testshunxu";
		 String test_name = "";
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
	public void testa(){
		System.out.println("testa\r\n");
	}
	public void testc(){
		System.out.println("testc\r\n");
	}
	public void testbac(){
		System.out.println("testbac\r\n");
	}
	public void testbbc(){
		System.out.println("testbbc\r\n");
	}
	public void testB(){
		System.out.println("testB\r\n");
	}
}

	
	



