/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.SmtpMailer;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.management.scheduler.Scheduler;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AMProductCommunicator
/*     */   extends Thread
/*     */ {
/*  40 */   private String user_type = null; private String adminEmailId = ""; private String subject = null; private String msg_content = null;
/*  41 */   private int total_Licensed_Monitors = 0; private int totalNoOfMonitors = 0;
/*  42 */   private long evaluation_days = 0L; private long days_remaining = 0L; private long next_day = 0L;
/*  43 */   private int day_of_week = 0; private int day_of_month = 0; private int month_of_year = 0;
/*  44 */   private boolean isPreSalesEnabled = false; private boolean isSmtpConfigured = true;
/*  45 */   private Properties smtpProps = null; private Properties secSmtpProps = null;
/*  46 */   Calendar cal = new GregorianCalendar();
/*  47 */   Date date = new Date();
/*     */   private static final int SCHOUR = 9;
/*     */   
/*     */   public AMProductCommunicator() {
/*  51 */     AMLog.debug("###### Initializing the communicator....");
/*     */     
/*  53 */     AMLog.debug("days_remaining:" + this.days_remaining + " evaluation_days:" + this.evaluation_days);
/*     */     
/*  55 */     if (!DBUtil.hasGlobalConfigValue("presales_emails"))
/*     */     {
/*     */       try
/*     */       {
/*  59 */         DBUtil.insertIntoGlobalConfig("presales_emails", "true");
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  63 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*  67 */     if (!DBUtil.hasGlobalConfigValue("easyUpgrade"))
/*     */     {
/*     */       try
/*     */       {
/*  71 */         DBUtil.insertIntoGlobalConfig("easyUpgrade", "false");
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  75 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void run()
/*     */   {
/*  83 */     AMLog.debug("Runningggg the communicator........");
/*  84 */     if (!EnterpriseUtil.isManagedServer) {
/*  85 */       FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  86 */       this.total_Licensed_Monitors = fd.getNumberOfMonitorsPermitted();
/*  87 */       this.user_type = fd.getUserType();
/*  88 */       this.days_remaining = fd.getExpiryDate();
/*  89 */       this.evaluation_days = (30L - this.days_remaining);
/*     */       
/*  91 */       int curhour = this.cal.get(11);
/*  92 */       int curmin = this.cal.get(12);
/*     */       
/*  94 */       this.day_of_week = this.cal.get(7);
/*  95 */       this.day_of_month = this.cal.get(5);
/*     */       try
/*     */       {
/*  98 */         this.isPreSalesEnabled = DBUtil.getGlobalConfigValueasBoolean("presales_emails");
/*  99 */         this.isSmtpConfigured = DBUtil.getGlobalConfigValueasBoolean("mailserverconfigured");
/* 100 */         if ((this.user_type.equals("F")) && (!this.isPreSalesEnabled))
/*     */         {
/* 102 */           DBUtil.updateGlobalConfigValue("presales_emails", "true");
/* 103 */           this.isPreSalesEnabled = true;
/*     */         }
/* 105 */         initSmtpProps();
/*     */         
/* 107 */         Timestamp curTime = new Timestamp(System.currentTimeMillis());
/* 108 */         AMLog.debug("AMProductCommunicator :: Presales Check >>>===> isPreSalesEnabled:" + this.isPreSalesEnabled + " isadminEmailInitialized:" + IsAdminEmailIDInitilized() + " isSmtpConfigured:" + this.isSmtpConfigured);
/* 109 */         if ((this.isPreSalesEnabled) && (IsAdminEmailIDInitilized()) && (this.isSmtpConfigured))
/*     */         {
/* 111 */           AMLog.debug("AMProductCommunicator : Ready to Execute Communicator emails!");
/* 112 */           this.msg_content = getI18NedTemplate(0);
/* 113 */           SendAPMFeatures();
/*     */           
/*     */ 
/* 116 */           LicenseUpgradeReminder();
/* 117 */           LicenseExpiryReminder();
/* 118 */           SendWeeklyReport();
/*     */           
/*     */ 
/* 121 */           AMLog.debug("AMProductCommunicator : Successfully executed Communicator emails! @9 hrs");
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 126 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 130 */         if ((curhour < 9) || ((curhour == 9) && (curmin < 0)))
/*     */         {
/* 132 */           this.cal.set(11, 9);
/* 133 */           this.cal.set(12, 0);
/* 134 */           this.cal.set(13, 0);
/* 135 */           this.cal.set(14, 0);
/* 136 */           this.date = this.cal.getTime();
/* 137 */           AMLog.debug("AMProductCommunicator : date curhour < SCHOUR || curmin < SCMIN: " + this.date);
/*     */         }
/*     */         else
/*     */         {
/* 141 */           this.cal.add(11, 24 - curhour + 9);
/* 142 */           this.cal.set(12, 0);
/* 143 */           this.cal.set(13, 0);
/* 144 */           this.cal.set(14, 0);
/* 145 */           this.date = this.cal.getTime();
/* 146 */           AMLog.debug("AMProductCommunicator : date : " + this.date);
/*     */         }
/*     */       }
/*     */       
/* 150 */       AMLog.debug("AMProductCommunicator : Rescheduled to:" + this.date);
/* 151 */       Scheduler.getScheduler("main").scheduleTask(this, this.date);
/*     */     }
/*     */     else {
/* 154 */       AMLog.debug("No pre-sales emails will be sent for managed server..........");
/*     */     }
/*     */   }
/*     */   
/*     */   private String sendMessage(String mail_subject, String mail_message)
/*     */   {
/* 160 */     return sendMessage(mail_subject, mail_message, false, null);
/*     */   }
/*     */   
/*     */   private String sendMessage(String mail_subject, String mail_message, boolean isHtmlMsg, String html_message)
/*     */   {
/* 165 */     String message = null;
/*     */     try
/*     */     {
/* 168 */       AMLog.debug("AMProductCommunicator : smtpProps:" + this.smtpProps + " adminEmailId:" + this.adminEmailId);
/* 169 */       SmtpMailer mail = new SmtpMailer(this.smtpProps, this.adminEmailId, this.adminEmailId, mail_subject);
/* 170 */       if (!isHtmlMsg)
/*     */       {
/* 172 */         message = mail.sendMessage(mail_message, "Mail Sent Successfullly!", true, null, 1);
/*     */       }
/*     */       else
/*     */       {
/* 176 */         message = mail.sendMessage(mail_message, "Mail Sent Successfullly!", true, html_message, 0, null);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 181 */       e.printStackTrace();
/*     */     }
/* 183 */     return message;
/*     */   }
/*     */   
/*     */ 
/*     */   public void LicenseUpgradeConfirmation()
/*     */   {
/*     */     try
/*     */     {
/* 191 */       this.subject = FormatUtil.getString("am.product.license.success.subject.txt");
/* 192 */       String msgBody = FormatUtil.findAndReplaceAll(this.msg_content, "~content~", FormatUtil.getString("am.product.license.success.content.txt"));
/* 193 */       sendMessage(this.subject, msgBody);
/* 194 */       AMLog.debug("AMProductCommunicator : LicenseUpgradeConfirmation mail sent successfully");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 198 */       AMLog.debug("AMProductCommunicator : LicenseUpgradeConfirmation :" + e.getMessage());
/* 199 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void LicenseUpgradeReminder()
/*     */   {
/*     */     try
/*     */     {
/* 208 */       if (this.user_type.equals("F"))
/*     */       {
/* 210 */         this.subject = FormatUtil.getString("am.product.license.reminder.subject.txt");
/* 211 */         String msgBody = FormatUtil.findAndReplaceAll(this.msg_content, "~content~", FormatUtil.getString("am.product.license.reminder.content.txt"));
/*     */         
/* 213 */         AMLog.debug("AMProductCommunicator : LicenseUpgradeReminder: am.presales.buyproduct::db::" + DBUtil.getGlobalConfigValue("am.presales.buyproduct") + "  day_of_month:" + "day_" + this.evaluation_days);
/* 214 */         if (!DBUtil.getGlobalConfigValue("am.presales.buyproduct").equals("day_" + this.evaluation_days))
/*     */         {
/* 216 */           sendMessage(this.subject, msgBody);
/* 217 */           AMLog.debug("AMProductCommunicator : LicenseUpgradeReminder mail sent successfully");
/* 218 */           if (DBUtil.getGlobalConfigValue("am.presales.buyproduct").equals(""))
/*     */           {
/* 220 */             DBUtil.insertIntoGlobalConfig("am.presales.buyproduct", "day_" + this.evaluation_days);
/*     */           }
/*     */           else
/*     */           {
/* 224 */             DBUtil.updateGlobalConfigValue("am.presales.buyproduct", "day_" + this.evaluation_days);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 231 */       AMLog.debug("AMProductCommunicator : LicenseUpgradeReminder :" + e.getMessage());
/* 232 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void SendWeeklyReport()
/*     */   {
/*     */     try
/*     */     {
/* 241 */       if (DBUtil.getGlobalConfigValue("am.presales.weeklyreport").equals(""))
/*     */       {
/* 243 */         DBUtil.insertIntoGlobalConfig("am.presales.weeklyreport", "day_" + this.day_of_month);
/*     */       }
/* 245 */       AMLog.debug("AMProductCommunicator : SendWeeklyReport: day_of_week:" + this.day_of_week + " am.presales.weeklyreport::db::" + DBUtil.getGlobalConfigValue("am.presales.weeklyreport") + "  day_of_month:" + "day_" + this.day_of_month);
/*     */       
/* 247 */       if ((this.day_of_week == 1) && (!DBUtil.getGlobalConfigValue("am.presales.weeklyreport").equals("day_" + this.day_of_month)))
/*     */       {
/* 249 */         this.subject = FormatUtil.getString("am.product.weeklyreport.subject.txt");
/* 250 */         String msgBody = FormatUtil.findAndReplaceAll(this.msg_content, "~content~", FormatUtil.getString("am.product.weeklyreport.content.txt"));
/* 251 */         String htmlMsg = getWeeklyReportData();
/* 252 */         sendMessage(this.subject, msgBody, true, htmlMsg);
/* 253 */         AMLog.debug("AMProductCommunicator : SendWeeklyReport mail sent successfully");
/* 254 */         if (DBUtil.getGlobalConfigValue("am.presales.weeklyreport").equals(""))
/*     */         {
/* 256 */           DBUtil.insertIntoGlobalConfig("am.presales.weeklyreport", "day_" + this.day_of_month);
/*     */         }
/*     */         else
/*     */         {
/* 260 */           DBUtil.updateGlobalConfigValue("am.presales.weeklyreport", "day_" + this.day_of_month);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 266 */       AMLog.debug("AMProductCommunicator : SendWeeklyReport :" + e.getMessage());
/* 267 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void LicenseExpiryReminder()
/*     */   {
/*     */     try
/*     */     {
/* 276 */       String content = null;
/* 277 */       if (DBUtil.getGlobalConfigValue("am.presales.expiryreminder").equals(""))
/*     */       {
/* 279 */         DBUtil.insertIntoGlobalConfig("am.presales.expiryreminder", "days_remaining_" + this.evaluation_days);
/*     */       }
/* 281 */       AMLog.debug("AMProductCommunicator : LicenseExpiryReminder: days_remaining:" + this.days_remaining + " am.presales.expiryreminder::db::" + DBUtil.getGlobalConfigValue("am.presales.expiryreminder") + "  eval_day:" + "eval_day_" + this.evaluation_days);
/* 282 */       if ((this.days_remaining < 15L) && (!this.user_type.equals("F")) && (!DBUtil.getGlobalConfigValue("am.presales.expiryreminder").equals("days_remaining_" + this.evaluation_days)))
/*     */       {
/* 284 */         if (this.days_remaining == 0L)
/*     */         {
/* 286 */           content = this.subject = FormatUtil.getString("am.product.license.expiry.today.subject.txt");
/*     */         }
/*     */         else
/*     */         {
/* 290 */           content = this.subject = FormatUtil.getString("am.product.license.expiry.ndays.subject.txt", new String[] { new String(this.days_remaining + "") });
/*     */         }
/*     */         
/* 293 */         if (this.days_remaining > 3L)
/*     */         {
/* 295 */           content = content + "\r\n\n" + FormatUtil.getString("am.product.license.getquote.content.txt") + "\r\n\n";
/* 296 */           content = content + FormatUtil.getString("am.product.license.monitor.summary.txt") + " : \r\n";
/* 297 */           content = content + getAllAddedMonitorTypes();
/* 298 */           content = content + FormatUtil.getString("am.product.license.monitors.setup.txt");
/*     */         }
/*     */         else
/*     */         {
/* 302 */           content = content + "\r\n\n" + FormatUtil.getString("am.product.license.extend.content.txt");
/*     */         }
/* 304 */         String msgBody = FormatUtil.findAndReplaceAll(this.msg_content, "~content~", content);
/* 305 */         if ((this.days_remaining == 14L) || (this.days_remaining == 5L) || (this.days_remaining == 2L) || (this.days_remaining == 0L))
/*     */         {
/* 307 */           if (!DBUtil.getGlobalConfigValue("am.presales.expiryreminder").equals("days_remaining_" + this.days_remaining))
/*     */           {
/* 309 */             sendMessage(this.subject, msgBody);
/* 310 */             AMLog.debug("AMProductCommunicator : LicenseExpiryReminder mail sent successfully");
/*     */             
/* 312 */             if (DBUtil.getGlobalConfigValue("am.presales.expiryreminder").equals(""))
/*     */             {
/* 314 */               DBUtil.insertIntoGlobalConfig("am.presales.expiryreminder", "days_remaining_" + this.days_remaining);
/*     */             }
/*     */             else
/*     */             {
/* 318 */               DBUtil.updateGlobalConfigValue("am.presales.expiryreminder", "days_remaining_" + this.days_remaining);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 326 */       AMLog.debug("AMProductCommunicator : LicenseExpiryReminder :" + e.getMessage());
/* 327 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private String getAllAddedMonitorTypes()
/*     */   {
/* 333 */     String toReturn = null;
/* 334 */     MyPageAction mypage = new MyPageAction();
/* 335 */     HashMap map = mypage.getAllAddedMonitorTypes("admin", "user");
/* 336 */     AMLog.debug("All Added Monitor Types::::====>" + map);
/* 337 */     this.totalNoOfMonitors = Integer.parseInt((String)map.get("totalMonCnt"));
/* 338 */     map.remove("totalMonCnt");
/* 339 */     Map<String, Hashtable> sortedMap = new TreeMap(map);
/* 340 */     if (sortedMap.size() > 0)
/*     */     {
/* 342 */       Iterator<String> monTypeIter = sortedMap.keySet().iterator();
/* 343 */       while (monTypeIter.hasNext())
/*     */       {
/* 345 */         String monType = (String)monTypeIter.next();
/* 346 */         toReturn = toReturn != null ? toReturn + "\t" : "\t";
/* 347 */         toReturn = monType + " : " + ((Hashtable)sortedMap.get(monType)).get("count");
/* 348 */         toReturn = toReturn + "\r\n";
/*     */       }
/* 350 */       toReturn = toReturn + "\r\n";
/*     */     }
/* 352 */     AMLog.debug("AMProductCommunicator : All Added Monitor Types:" + toReturn);
/* 353 */     return toReturn;
/*     */   }
/*     */   
/*     */ 
/*     */   private void RegisterForSupport()
/*     */   {
/* 359 */     String techSupportVal = "day_" + this.day_of_month + "_" + this.month_of_year;
/*     */     try
/*     */     {
/* 362 */       AMLog.debug("AMProductCommunicator : RegisterForSupport: am.technicalsupportrequired:" + DBUtil.getGlobalConfigValueasBoolean("am.technicalsupportrequired") + "  User_Type:" + this.user_type);
/* 363 */       if ((!this.user_type.equals("R")) && (!DBUtil.getGlobalConfigValueasBoolean("am.technicalsupportrequired")) && (!DBUtil.getGlobalConfigValue("am.presales.techsupport").equals(techSupportVal)))
/*     */       {
/* 365 */         this.subject = FormatUtil.getString("am.product.update.subject.txt");
/* 366 */         String msgBody = FormatUtil.findAndReplaceAll(this.msg_content, "~content~", FormatUtil.getString("am.product.update.content.txt"));
/* 367 */         sendMessage(this.subject, msgBody);
/* 368 */         AMLog.debug("AMProductCommunicator : RegisterForSupport mail sent successfully");
/* 369 */         if (DBUtil.hasGlobalConfigValue("am.presales.techsupport"))
/*     */         {
/* 371 */           DBUtil.updateGlobalConfigValue("am.presales.techsupport", techSupportVal);
/*     */         }
/*     */         else
/*     */         {
/* 375 */           DBUtil.insertIntoGlobalConfig("am.presales.techsupport", techSupportVal);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 381 */       AMLog.debug("AMProductCommunicator : RegisterForSupport : " + e.getMessage());
/* 382 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void SendAPMFeatures()
/*     */   {
/*     */     try
/*     */     {
/* 391 */       this.subject = FormatUtil.getString("am.product.update.subject.txt");
/* 392 */       AMLog.debug("AMProductCommunicator : SendAPMfeatures: evaluationdays:" + this.evaluation_days + "  User_Type:" + this.user_type);
/*     */       
/* 394 */       if (this.user_type.equals("T")) {
/* 395 */         if ((this.evaluation_days == 4L) && (!DBUtil.getGlobalConfigValueasBoolean("am.presales.sendfeatures"))) {
/* 396 */           String msgBody = FormatUtil.findAndReplaceAll(this.msg_content, "~content~", FormatUtil.getString("am.product.apmfeatures.content.txt"));
/* 397 */           sendMessage(this.subject, msgBody);
/* 398 */           AMLog.debug("AMProductCommunicator : SendAPMFeatures mail sent successfully");
/* 399 */           DBUtil.insertIntoGlobalConfig("am.presales.sendfeatures", "true");
/*     */         }
/* 401 */         else if ((this.evaluation_days == 2L) && (!DBUtil.getGlobalConfigValueasBoolean("am.presales.24hrsmail"))) {
/* 402 */           String msgBody = FormatUtil.findAndReplaceAll(this.msg_content, "~content~", FormatUtil.getString("am.product.apmfeatures.24hrs.txt"));
/* 403 */           sendMessage(this.subject, msgBody);
/* 404 */           AMLog.debug("AMProductCommunicator : Send 24 hrs mail sent successfully");
/* 405 */           DBUtil.insertIntoGlobalConfig("am.presales.24hrsmail", "true");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 411 */       AMLog.debug("AMProductCommunicator : SendAPMFeatures :" + e.getMessage());
/* 412 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean IsAdminEmailIDInitilized()
/*     */   {
/* 418 */     if (!Constants.isAdminEmailIDInitilized())
/*     */     {
/* 420 */       this.adminEmailId = Constants.initAdminEmailID();
/*     */     }
/*     */     else
/*     */     {
/* 424 */       this.adminEmailId = Constants.ADMIN_EMAIL_ADDRESS;
/*     */     }
/*     */     
/* 427 */     return Constants.isAdminEmailIDInitilized();
/*     */   }
/*     */   
/*     */   private String getI18NedTemplate(String firstLine)
/*     */   {
/* 432 */     return ClientDBUtil.getI18NedTemplate(firstLine, 0);
/*     */   }
/*     */   
/*     */   private String getI18NedTemplate(int demovideoType)
/*     */   {
/* 437 */     String firstLine = FormatUtil.getString("Hello,");
/* 438 */     return ClientDBUtil.getI18NedTemplate(firstLine, demovideoType);
/*     */   }
/*     */   
/*     */   private static final int SCMIN = 0;
/*     */   private static final int SCSEC = 0;
/*     */   private void initSmtpProps()
/*     */   {
/* 445 */     ResultSet set = null;
/*     */     
/*     */     try
/*     */     {
/* 449 */       set = AMConnectionPool.executeQueryStmt("select HOST,PORT,USERNAME," + DBQueryUtil.decodeBytes("PASSWORD") + " as PASSWORD,TLSEnabled,ID from AM_MAILSETTINGS where ID=1");
/* 450 */       while (set.next())
/*     */       {
/* 452 */         Properties props = new Properties();
/*     */         
/* 454 */         props.put("mail.smtp.host", set.getString(1));
/* 455 */         props.put("mail.smtp.port", String.valueOf(set.getInt(2)));
/* 456 */         props.put("mail.smtp.username", set.getString(3));
/* 457 */         props.put("mail.smtp.password", new String(set.getBytes(4)));
/* 458 */         props.put("mail.smtp.auth", (set.getString(3) != null) && (set.getString(3).length() > 0) ? "true" : "false");
/* 459 */         props.put("mail.smtp.tlsauth", "1".equals(set.getString(5)) ? "true" : "false");
/*     */         
/* 461 */         if (set.getString("ID").equals("1"))
/*     */         {
/* 463 */           this.smtpProps = props;
/*     */         }
/*     */         else
/*     */         {
/* 467 */           this.secSmtpProps = props;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 473 */       exc.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 477 */       AMConnectionPool.closeStatement(set);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getWeeklyReportData()
/*     */   {
/* 483 */     SummaryMailer summMailer = new SummaryMailer();
/*     */     
/* 485 */     Hashtable mgData = ReportUtilities.getAvailabilityHistoryForMGs("HAI", 7, null, "admin");
/* 486 */     Hashtable moData = ReportUtilities.getallMOAvailabilityHistory(7);
/*     */     
/* 488 */     String htmlmess = ComparingSla.getSummaryMailTemplate();
/* 489 */     htmlmess = summMailer.getPopulatedSummaryMailerTmpl(htmlmess, 7);
/*     */     
/* 491 */     String subject = FormatUtil.getString("am.reporting.admin.summarymail.subject");
/* 492 */     String message = FormatUtil.getString("am.reporting.admin.summarymail.subject");
/*     */     
/* 494 */     ArrayList sortmglist = summMailer.getSorted(summMailer.getListOfAllMgs(mgData));
/* 495 */     String datarow = summMailer.getMgContentForTemplate(sortmglist, mgData);
/*     */     
/* 497 */     ArrayList sortavlist = summMailer.getSorted(summMailer.getListOfAllMos(moData));
/* 498 */     String morow = summMailer.getMoContentForTemplate(sortavlist, moData, 20);
/*     */     
/* 500 */     htmlmess = FormatUtil.findReplace(htmlmess, "~mgdata~", datarow);
/* 501 */     htmlmess = FormatUtil.findReplace(htmlmess, "~mondata~", morow);
/* 502 */     return htmlmess;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\AMProductCommunicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */