package com.checheyun.vcm.VcmUiTest;



public class StaticO{
		static int passCase=0;
		static int failCase=0;
		
		
		public static void main(String[] args){
			failCase=failCase+1;
			fun1();
			fun2();
			System.out.println("failCase="+failCase);
			System.out.println("passCase="+passCase);
			
			
		}
		public static void fun1(){
			passCase=passCase+1;
			failCase=failCase+1;
		}
		public static void fun2(){
			passCase=passCase+1;
			failCase=failCase+1;
		}

}

