package Adminui.Adminui;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
//import org.apache.tools.ant.types.resources.selectors.Compare;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import junit.framework.Assert;
public class Test_czentrix extends MethodClass
{   
	
  //private static final Assertion Assert = null;
	public static ExtentReports extent;
	public static  ExtentTest   logger;
  @BeforeTest
    public  void before() throws Exception 
	{
	  System.out.println("Start Browser test case");
	  Logger loggerr=Logger.getLogger("Test_czentrix");
	  PropertyConfigurator.configure("Log4j.properties");
	  extent=new ExtentReports(System.getProperty("user.dir")+"/test-output/STMExtentReport.html",true);
	  extent.addSystemInfo("Host name", "softwsre Testing Material");
	  extent.addSystemInfo("Environment", "Automation Testing");
	  extent.addSystemInfo("User Name", "Himanshu");
	  loggerr.info("start before testcase");
try {
	  System.out.println("Start Try block ");
	 logger = extent.startTest("openbrowser");
    //Assert.assertTrue(true);
	  logger.log(LogStatus.PASS, "browser test case is pass");
	  System.out.println("call open Browser method ");
	  MethodClass.openBrowser();
	  System.out.println("close open broeser method in try block");
	  loggerr.info("close browser test case in try block");
  } 
 catch(Exception e)
	  {
	   System.out.println(e.getMessage()+" catch block");
	  } 
        System.out.println(" closed Browser test case");
		loggerr.info("close Browser testcase");
	}
 
 
    
 	@AfterMethod
	 public void getResult(ITestResult result)
	 {     
	  if(result.getStatus()==ITestResult.FAILURE) 
	   {
	    logger.log(LogStatus.FAIL, "Test case failed is "+result.getName());
	    logger.log(LogStatus.FAIL, "Test case failed is "+result.getThrowable());
	   }
	  else if(result.getStatus()==ITestResult.SKIP)
	   {
	    logger.log(LogStatus.SKIP, "Test case skipped is"+result.getName());
	   }
	   extent.endTest(logger);
	}
	    @AfterTest
	    public void endReport()
	    {
	    	extent.flush();
	    	//extent.close();
	    }
	   // C:\Users\tvt\eclipse-workspace\org.C_zentrix_all_case9
	    //STMExtentReport.html
	    //Extent Reports
}
