package Adminui.Adminui;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.tools.ant.types.resources.selectors.Compare;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
//import Screenshot.Screen_shot;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
public class MethodClass 
{
	static String excel_url,s1,LicenseVersion,s3,s4,s5,zap_provider_name,campaign_id1,server_output,excel_user,campaign_user,campaign_state,sip_name,gateway_Interface,disposition_name,list_name,Source_Lead_name;
    static int state, Feature_Details_agents,SIP_Extensions,agent_id,group_id,campaign_id,camp_count,agent_coun,list_count,sip_count,gateway_count,list_id,source_count;
    public static int flag=0;
    public static  WebDriver driver;
    public static   FileInputStream f2;
    public static   HSSFWorkbook Hw;
    public static   HSSFSheet Hs;
    public static  SoftAssert assertion= new SoftAssert();
	public static void openBrowser() throws IOException 
	{
	  Logger logger=Logger.getLogger("MethodClass");
	  PropertyConfigurator.configure("Log4j.properties");
		//browser open
		System.out.println("browser open method is start");
		System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
		// System.setProperty("webdriver.chrome.driver", "D:\\himanshu\\d\\himanshu\\browser setup\\ch\\chromedriver.exe");
        //open firefox browser
		System.out.println("open fire fox browser ");
		driver=new FirefoxDriver();
		// driver= new ChromeDriver();                        
        logger.info("Firefox Opened");
		driver.manage().window().maximize();
		logger.info("window maximize");
		// collect url from excel sheet c-zentrix
		System.out.println(" collect url from excel sheet");
		f2=new FileInputStream("TestData/Czentrix_data.xls");
        Hw=new HSSFWorkbook(f2);
	    Hs=Hw.getSheet("Sheet3");
	    excel_url=Hs.getRow(1).getCell(0).getStringCellValue();
		System.out.println(excel_url);
		Hw.close();
	    driver.get("http://"+excel_url+"/");
		// screen shot method calling
	    logger.info("call screen shot method");
	     //System.out.println("call screen shot method");
		 screen(driver, "Browser start");
		// check assertion
		 logger.info(" check url assertion");
		 System.out.println("check url assertion");
		 String Actualurl = driver.getCurrentUrl();
		// Assert.assertEquals(Actualurl, excel_url);
		 logger.info("fill user name");
		 System.out.println("fill user name and password");
		 String USERNAME=Hs.getRow(1).getCell(1).getStringCellValue();
		 screen(driver, "Browser start");
		 driver.findElement(By.name("uid")).clear();
		 driver.findElement(By.name("uid")).sendKeys(USERNAME);
		// Assert.assertTrue(USERNAME.contains("adminrw"), "user name not match");
		// boolean actResult=Compare.validateElementVisibility(driver,"name","uid");
         String PASSWORD=Hs.getRow(1).getCell(2).getStringCellValue();
         logger.info("fill password");
		 driver.findElement(By.name("pwd")).clear();
		 driver.findElement(By.name("pwd")).sendKeys(PASSWORD);
		// System.out.println(PASSWORD+USERNAME);
	   //  Assert.assertTrue(PASSWORD.contains("server57"), "password not match");
		 driver.findElement(By.name("Submit")).click();
         logger.info("login success");
         //flag for session button
		 flag++;
		 if(flag==1)
		   {	    logger.info("new session is start");
			    driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[2]/td[1]/form/table/tbody/tr[7]/td/input[1]")).click();
				System.out.println("your new session is start");
		   }
		  else
		    {
			   logger.info("old session is continue");
		    	driver.findElement(By.name("logout_existing_agent")).click();
		    	System.out.println("your old session is continue");
		    }
		 System.out.println("click on slider button");
 		 driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/div/img")).click();
 		String s1=driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/table/tbody/tr/td[2]/div[2]/div[2]")).getText();
 		 screen(driver, "Browser Open");
 		 Data();
 		//server();
 		assertion.assertEquals(s1, "Welcome adminrw");
 		assertion.assertAll();
 	System.out.println("open browser method  is closed");
 }
	// ScreenShot method
	public static  void screen(WebDriver driver,String Screenshotname)
	{    
		System.out.println("start screen shot method start");
	  try {
		   TakesScreenshot ts= (TakesScreenshot)driver;
		   File source= ts.getScreenshotAs(OutputType.FILE);
		   FileUtils.copyFile(source, new File("./Screenshot1/"+Screenshotname+".png"));
		  System.out.println("taken");
		  } 
	 catch (Exception e) 
		   {
		    e.printStackTrace();
		    System.out.println("Exception while taking screen shot"+e.getMessage());
		  } 
	       System.out.println("close screen shot method");
   }
	//database
	public static void Data()
	{
		System.out.println("start database method");
		  //String input = args.length == 0 ? "1818-11-11" : args[0]; for save special type data
	      Connection con=null;
		  Statement st=null;
		  ResultSet rs=null;
  try{
		  try {
		  Class.forName("com.mysql.jdbc.Driver");//.newInstance();
		  }
		  catch(Exception e)
		  {
			  System.out.println(e.getMessage());
			  System.out.println("exception");
		  }
		     Class.forName("com.mysql.jdbc.Driver");
		  // Class.forName("oracle.jdbc.driver.oracleDriver");
			 System.out.println("Driver loaded");
			 con=DriverManager.getConnection("jdbc:mysql://"+excel_url+"/czentrix_campaign_manager","root","sqladmin");
	        //con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","himanshu","Himansu@123");
			 //172.16.2.179
			 if(con!=null) {
	             System.out.println("Connected to the database");
	         }else {
	             System.out.println("Failed to connect to  database");
	         }
			 String sql="select * from license";//for licences version
			 String sql1="select * from features"; // for agent detail
			 String sql2="select*from maximum";
			 String sql22="select max_value from maximum where features='SIP_Extensions'";
             String sql3="select list_id from list where list_name='List1'"; 
             String sql33= "select list_name from list where list_name='List1'";  
			 String sql4="select provider_name from zap where zap_id='zap/9'"; // for provider name value
			 String sql5="select groupId from zap where zap_id='zap/9'"; // for zap group id 
			// String sql6=" select campaign_id from campaign where autodial_level='1'";
			 String sql6="select campaign_id from campaign where campaign_name='User1'";// for inactive campaign
			 String sql66="select campaign_name from campaign where campaign_name='User1'";
			 String sql67="select campaign_state from campaign where campaign_name='User1'";
			 String sql7="select count(*) from campaign"; //for check campaign count
			 String sql8="select count(*) from agent"; //for check agent count
			 String sql9="select count(*) from list"; //for check list count
			 String sql10="select  sip_gateway_name from sip_gateway where  sip_gateway_name='server25'";
			 String sql11="select count(*) from sip_gateway";
			 String sql12="select exIfc from gateway where exIfc='eth1'";
			 String sql13="select count(*) from gateway";
			// String sql14= "select disposition_name from cust_disp_732 where disposition_name='basic_disp'";
			 String sql15="select list_id from list where list_name ='List1'";
			 String sql16="select count(*) from  source_lead_integration ";
		     String sql17="select source_lead_name from  source_lead_integration where source_lead_name='source1'";



             st=con.createStatement();
			 rs=st.executeQuery(sql);
			 System.out.println("database table row is\t"+rs.getRow()); 
		     while(rs.next())
			 {
			  LicenseVersion =rs.getString(2);
			  System.out.println("License version is::"+LicenseVersion);
	         }
			 rs=st.executeQuery(sql1);
			 while(rs.next())
			 {
		       //state =rs1.getString(2);
		         state=rs.getInt(2);
		         System.out.println("state value is::"+state);
		         break;
		     }
			 rs=st.executeQuery(sql2);
			 while(rs.next())
			 {
				 System.out.println("Feature_Details for agents ");
				 Feature_Details_agents=rs.getInt(3);
		         System.out.println("Feature_Details_agents::" + Feature_Details_agents);
			 }
			
		 rs=st.executeQuery(sql22);
			 while(rs.next())
			 {
				 System.out.println("Feature_Details for sip extensions");
			    SIP_Extensions=rs.getInt(1);
				 System.out.println("SIP_Extensions::" + SIP_Extensions);
			 }
			 rs=st.executeQuery(sql3);
		     while(rs.next())
			 {
				 System.out.println("upload list");
				 agent_id=rs.getInt(1);//for list upload
				 System.out.println("agent id is :" + agent_id);
			 }
		     rs=st.executeQuery(sql33);
		     while(rs.next())
			 {
				 System.out.println("list_name ");
				 list_name=rs.getString(1);//for list list_name
				 System.out.println("list_name  is :" + list_name);
			 }
			 rs=st.executeQuery(sql4);
			 while(rs.next())
			 {
			    System.out.println("zap");
				 zap_provider_name=rs.getString(1);
				// zap_provider_name=rs4.getInt(1);
				 System.out.println("zap provider name  is :" + zap_provider_name);
			 }
			 rs=st.executeQuery(sql5);
			 while(rs.next())
			 {
				 System.out.println("zap group id");
				 group_id=rs.getInt(1);
				 System.out.println("zap group id  is :" + group_id);
			 } 
			 rs=st.executeQuery(sql6);
			 while(rs.next())
			 {
				 System.out.println("campaign id");
				 campaign_id=rs.getInt(1);//for Sip_channel_configuration_Form
				 campaign_id1=rs.getString(1);
				 System.out.println(" campaign id  is :" + campaign_id);
			 }
			 rs=st.executeQuery(sql66);
			 while(rs.next())
			 {
				 System.out.println("campaign_user");
			     campaign_user=rs.getString(1);
				 System.out.println(" campaign_user  is :" + campaign_user);
			 }
			 rs=st.executeQuery(sql67);
			 while(rs.next())
			 {
				 System.out.println("campaign_state");
				 campaign_state=rs.getString(1);
				 System.out.println(" campaign_state  is :" + campaign_state);
			 }
			 rs=st.executeQuery(sql7);
			 while(rs.next())
			 {
			   System.out.println("campaign count");
			   camp_count=rs.getInt(1);
			   System.out.println(" camp_count   is :" + camp_count);
			 }
			 rs=st.executeQuery(sql8);
			 while(rs.next())
			 {
			   System.out.println("campaign count");
			   agent_coun=rs.getInt(1);
			   System.out.println(" agent_coun   is :" + agent_coun);
			 }
			 rs=st.executeQuery(sql9);
			 while(rs.next())
			 {
			   System.out.println("list_count count");
			   list_count=rs.getInt(1);
			   System.out.println(" list_count   is :" + list_count);
			 }
			 rs=st.executeQuery(sql10);
			 while(rs.next())
			 {
			   System.out.println("sip_gateway_namet");
			   sip_name=rs.getString(1);
			   System.out.println(" sip_gateway_name   is :" + sip_name);
			 }
			 rs=st.executeQuery(sql11);
			 while(rs.next())
			 {
			   System.out.println("sip_count");
			   sip_count=rs.getInt(1);
			   System.out.println(" sip_count   is :" + sip_count);
			 }
			 rs=st.executeQuery(sql12);
			 while(rs.next())
			 {
			   System.out.println("gateway_Interface");
			   gateway_Interface=rs.getString(1);
			   System.out.println(" gateway_Interface   is :" + gateway_Interface);
			 }
			 rs=st.executeQuery(sql13);
			 while(rs.next())
			 {
			   System.out.println("gateway_count");
			   gateway_count=rs.getInt(1);
			   System.out.println(" gateway_count   is :" + gateway_count);
			 }
//			 rs=st.executeQuery(sql14);
//			 while(rs.next())
//			 {
//			   System.out.println("disposition_name");
//			   disposition_name=rs.getString(1);
//			   System.out.println(" disposition_name   is :" + disposition_name);
//			 }
			 rs=st.executeQuery(sql15);
			 while(rs.next())
			 {
			   System.out.println("List_id");
			   list_id=rs.getInt(1);
			   System.out.println(" list_id   is :" + list_id);
			 }
			 rs=st.executeQuery(sql16);
			 while(rs.next())
			 {
			   System.out.println("source_count");
			   source_count=rs.getInt(1);
			   System.out.println(" source_count   is :" + source_count);
			 }
			 rs=st.executeQuery(sql17);
			 while(rs.next())
			 {
			   System.out.println("Source_Lead_name");
			   Source_Lead_name=rs.getString(1);
			   System.out.println(" Source_Lead_name   is :" + Source_Lead_name);
			 }
	    }	 
		 catch(Exception e)
		 {
			 System.out.println(e.getMessage());
		 }

	}
	//server connection
	public static void server()
	{
		System.out.println("start service method ");
	    String host="192.168.1.57";
        String user="root";
        String password="latest57";
        String command1="tail -f /var/log/mysqllog.log |grep 'insert into campaign'";
       // String CMD;
       // String[] SERVER_STATUS = new String[]{command1, "systemctl is-active czentrix.service"};
        try{
       //command1.substring(TIME, CMD);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command1);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();
            channel.connect();
        try {
            byte[] tmp=new byte[1024];
            while(true){
            	System.out.println("Connected 2");
            while(in.available()>0){
            	  System.out.println("Connected 3");
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
            System.out.print(new String(tmp, 0, i));
            server_output=new String(tmp,0,i);//initialize the string from server string
            System.out.println("server out put is"+server_output);
          }
               System.out.println(channel.toString());
               //System.out.println("---- End OF Line ----");

if(channel.isClosed())
              {
                System.out.println("exit-status:"+channel.getExitStatus());
                break;
              }
              try{Thread.sleep(1000);}catch(Exception ee){}
            }
        }
        catch(Exception e)
        {
        	System.out.println(e.getMessage());
        }
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	// Alert method

	  public static boolean isAlertPresent() {
		    try {
		      driver.switchTo().alert();
		      return true;
		    } catch (NoAlertPresentException e) {
		      return false;
		    }
		  }

		  public static String closeAlertAndGetItsText() {
		    boolean acceptNextAlert = false;
			try {
		      Alert alert = driver.switchTo().alert();
		      String alertText = alert.getText();
		      if (acceptNextAlert) {
		        alert.accept();
		      } else {
		        alert.dismiss();
		      }
		      return alertText;
		    } finally {
		      acceptNextAlert = true;
		    }
		  }
	
}


