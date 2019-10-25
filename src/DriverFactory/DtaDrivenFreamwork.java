package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import CommanFunLibrary.ERP_Functions;
import Utilities.ExcelFileutil;

public class DtaDrivenFreamwork {
WebDriver driver;
//creating reference object for function library
ERP_Functions erp=new ERP_Functions();
@BeforeTest
public void launchapp() throws Throwable
{
	String app=erp.launchurl("http://webapp.qedge.com");
	System.out.println(app);
	//calling login
	String login=erp.verifyLogin("admin", "master");
	System.out.println(login);
}
@Test
public void suppliercreation()throws Throwable
{
	// to call excel util methods
	ExcelFileutil x1=new ExcelFileutil();
	//count no of rows in a sheet
	int rc=x1.rowCount("Supplier");
	// count no of columns
	int cc=x1.colCount("Supplier");
	Reporter.log("no.of rows are::"+rc+" "+"no of column are ::"+cc,true );
	for(int i=1;i<=rc;i++)
	{
	String sname=x1.getData("Supplier", i, 0);
	String address=x1.getData("Supplier", i, 1);
	String city=x1.getData("Supplier", i, 2);
	String country=x1.getData("Supplier", i, 3);
	String cperson=x1.getData("Supplier", i, 4);
	String pnumber=x1.getData("Supplier", i, 5);
	String mail=x1.getData("Supplier", i, 6);
	String mnumber=x1.getData("Supplier", i, 7);
	String note=x1.getData("Supplier", i, 8);
	String result=erp.verifySuppiler(sname, address, city, country, cperson, 
			pnumber, mail, mnumber, note);
	x1.setCellData("Supplier", i, 9, result);
	}
	}
@AfterTest
public void logout()throws Throwable
{
	String logoutapp=erp.verifyLogout();
	System.out.println(logoutapp);
	//driver.close();

			
	}
}
