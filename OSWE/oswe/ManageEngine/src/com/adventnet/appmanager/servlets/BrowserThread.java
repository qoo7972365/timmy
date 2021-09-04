/*    */ package com.adventnet.appmanager.servlets;
/*    */ 
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*    */ import com.adventnet.appmanager.util.Constants;
/*    */ import com.adventnet.appmanager.utils.StartWebConsole;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class BrowserThread
/*    */   extends Thread
/*    */ {
/*    */   public void run()
/*    */   {
/* 56 */     boolean bo = checkwindows();
/*    */     try
/*    */     {
/* 59 */       AMAutomaticPortChanger.setproperties(System.getProperty("CONFIG_ROOT_DIR"));
/*    */     }
/*    */     catch (Exception propexc)
/*    */     {
/* 63 */       propexc.printStackTrace();
/* 64 */       AMLog.debug("Some problem in getting the properties in the AM_InitBrowser class");
/*    */     }
/* 66 */     String browserstatus = String.valueOf(AMAutomaticPortChanger.getbrowserstatus()).trim();
/* 67 */     if ((browserstatus.equals("true")) && (!Constants.isIt360))
/*    */     {
/* 69 */       if (bo == true)
/*    */       {
/* 71 */         String[] args = new String[2];
/* 72 */         args[0] = System.getProperty("CONFIG_ROOT_DIR");
/*    */         
/*    */         try
/*    */         {
/* 76 */           StartWebConsole.main(args);
/*    */         }
/*    */         catch (Exception e) {}
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean checkwindows()
/*    */   {
/* 88 */     if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows")))
/*    */     {
/* 90 */       return true;
/*    */     }
/* 92 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\BrowserThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */