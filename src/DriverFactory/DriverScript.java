package DriverFactory;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import CommanFunLibrary.FunctionLibrary1;
import CommanFunLibrary.FunctionLibrary1;
import Utilities.ExcelFileutil;
import Utilities.Screenshot;
public class DriverScript
{
	ExtentReports report;
    ExtentTest test;
	WebDriver driver;	
public void startTest() throws Throwable
{
//creating reference object for excel util methods
	ExcelFileutil excel=new ExcelFileutil();
	//iterating all row in masterTestCases sheet
	for(int i =1;i<=excel.rowCount("MasterTestCases");i++)
	{
		String ModuleStatus="";
		if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
				{
			//store module name into TCModule
			String TCModule=excel.getData("MasterTestCases", i, 1);
			report=new ExtentReports("./Reports/"+TCModule+FunctionLibrary1.generateDate()+".html");
			//iterate all rows in TVModule sheet
			for(int j=1;j<=excel.rowCount(TCModule);j++)
			{
				test=report.startTest(TCModule);
				//read all columns in TCModule testcase
				String Description=excel.getData(TCModule, j, 0);
				String Object_Type=excel.getData(TCModule, j, 1);
				String Locator_Type=excel.getData(TCModule, j, 2);
				String Locator_value=excel.getData(TCModule, j, 3);
				String Test_Data=excel.getData(TCModule, j, 4);
				try//calling methods from function library
				{
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
					driver=FunctionLibrary1.startBrowser(driver);
					System.out.println("Executing startBrowser");
					test.log(LogStatus.INFO,Description);
					}
					else if (Object_Type.equalsIgnoreCase("openApplication"))
					{
						FunctionLibrary1.openApplication(driver);
						System.out.println("Executing openApplication");
						test.log(LogStatus.INFO,Description);
					}
					else if (Object_Type.equalsIgnoreCase("waitForElement"))
					{
							
					FunctionLibrary1.waitForElement(driver, Locator_Type, Locator_value, Test_Data);
					System.out.println("Executing waitForElement");
					}
				else if (Object_Type.equalsIgnoreCase("typeAction"))
				{
					FunctionLibrary1.typeAction(driver, Locator_Type, Locator_value, Test_Data);
					test.log(LogStatus.INFO,Description);
				}
				else if(Object_Type.equalsIgnoreCase("clickAction"))
				{
					FunctionLibrary1.clickAction(driver, Locator_Type, Locator_value);
					test.log(LogStatus.INFO,Description);
				}
					else if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
					FunctionLibrary1.waitForElement(driver, Locator_Type, Locator_value, Test_Data);
					test.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("closeBrowser"))
				{
			FunctionLibrary1.closeBrowser(driver);
		    test.log(LogStatus.INFO,Description);
				}
				else if(Object_Type.equalsIgnoreCase("captureData"))
				{
					FunctionLibrary1.captureData(driver, Locator_Type, Locator_value);
					test.log(LogStatus.INFO,Description);
				}
				else if(Object_Type.equalsIgnoreCase("validateTable"))
				{
					FunctionLibrary1.tableValidation1(driver, Test_Data);
				}
				else if(Object_Type.equalsIgnoreCase("StockCategories"))
				{
					FunctionLibrary1.stockCategories(driver);
					 test.log(LogStatus.INFO,Description);
				}
				else if(Object_Type.equalsIgnoreCase("tableValidation1"))
				{
					FunctionLibrary1.tableValidation1(driver, Test_Data);
					test.log(LogStatus.INFO,Description);
				}
					//write as pass into status column
					excel.setCellData(TCModule, j, 5, "PASS");
					test.log(LogStatus.PASS, Description);
					Screenshot.Takescreen(driver, Description);
					ModuleStatus="true";
	}catch(Exception e)
				{
		System.out.println(e.getMessage());
		excel.setCellData(TCModule, j, 5, "FAIL");
		test.log(LogStatus.FAIL, Description);
		Screenshot.Takescreen(driver, Description);
		ModuleStatus="false";
		break;
				}
				if(ModuleStatus.equalsIgnoreCase("true"))
				{
					excel.setCellData("MasterTestCases", i, 3, "PASS");
				}
				else 
				    {
					if(ModuleStatus.equalsIgnoreCase("False"))
				    {
					excel.setCellData("MasterTestCases", i, 3, "FAIL");
				}}
				report.flush();//push to report html
				report.endTest(test);
			}}
				
		else
		{
			//write as not executed in status column for flag N
			excel.setCellData("MasterTestCases", i, 3, "Not Executed");
		}
        }
        }
        }