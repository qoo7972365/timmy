/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.SmtpMailer;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.management.scheduler.Scheduler;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SummaryMailer
/*     */   extends Thread
/*     */ {
/*  34 */   Calendar cal = new GregorianCalendar();
/*  35 */   Date date = new Date();
/*  36 */   public static HashMap obj_ref = new HashMap();
/*  37 */   public int schour = 8;
/*  38 */   public int scmin = 0;
/*  39 */   public int scsec = 0;
/*  40 */   public String fromadd = "";
/*  41 */   public String toadd = "";
/*  42 */   public boolean status = true;
/*  43 */   public boolean enabled = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/*  52 */     AMLog.info("Checking the SummaryMailer");
/*  53 */     if ((this.enabled) && (!EnterpriseUtil.isManagedServer()))
/*     */     {
/*  55 */       boolean checktodo = getDataFromDB();
/*  56 */       int curhour = this.cal.get(11);
/*  57 */       int curmin = this.cal.get(12);
/*     */       
/*     */ 
/*  60 */       Hashtable mgData = ReportUtilities.getAvailabilityHistoryForMGs("HAI", 1, null, "admin");
/*  61 */       Hashtable moData = ReportUtilities.getallMOAvailabilityHistory(1);
/*     */       
/*  63 */       if ((curhour == this.schour) && (curmin == this.scmin) && (this.status))
/*     */       {
/*     */         try
/*     */         {
/*  67 */           if (checktodo)
/*     */           {
/*  69 */             String htmlmess = ComparingSla.getSummaryMailTemplate();
/*  70 */             htmlmess = getPopulatedSummaryMailerTmpl(htmlmess, 1);
/*     */             
/*  72 */             String subject = FormatUtil.getString("am.reporting.admin.summarymail.subject");
/*  73 */             String message = FormatUtil.getString("am.reporting.admin.summarymail.subject");
/*     */             
/*  75 */             ArrayList sortmglist = getSorted(getListOfAllMgs(mgData));
/*  76 */             String datarow = getMgContentForTemplate(sortmglist, mgData);
/*     */             
/*  78 */             ArrayList sortavlist = getSorted(getListOfAllMos(moData));
/*  79 */             String morow = getMoContentForTemplate(sortavlist, moData, 20);
/*     */             
/*  81 */             String withmg = FormatUtil.findReplace(htmlmess, "~mgdata~", datarow);
/*  82 */             String htmlmai = FormatUtil.findReplace(withmg, "~mondata~", morow);
/*     */             
/*  84 */             SmtpMailer mailer = new SmtpMailer(this.fromadd, this.toadd, "", subject);
/*  85 */             String returnVal = mailer.sendMessage(message, "Success", true, htmlmai, 0, null, null);
/*  86 */             AMLog.debug("SummaryMailer: Status:" + returnVal);
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/*  90 */           e.printStackTrace();
/*     */         }
/*     */         catch (Throwable e)
/*     */         {
/*  94 */           e.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/*  98 */           this.cal.add(11, 24 - curhour + this.schour);
/*  99 */           this.cal.set(12, this.scmin);
/* 100 */           this.cal.set(13, this.scsec);
/* 101 */           this.cal.set(14, 0);
/* 102 */           this.date = this.cal.getTime();
/* 103 */           AMLog.info("SummaryMailer: date inside finally block: " + this.date);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 108 */       else if ((curhour < this.schour) || ((curhour == this.schour) && (curmin < this.scmin)))
/*     */       {
/* 110 */         this.cal.set(11, this.schour);
/* 111 */         this.cal.set(12, this.scmin);
/* 112 */         this.cal.set(13, this.scsec);
/* 113 */         this.cal.set(14, 0);
/* 114 */         this.date = this.cal.getTime();
/* 115 */         AMLog.info("SummaryMailer: date curhour < schour || curmin < scmin: " + this.date);
/*     */       }
/*     */       else {
/* 118 */         this.cal.add(11, 24 - curhour + this.schour);
/* 119 */         this.cal.set(12, this.scmin);
/* 120 */         this.cal.set(13, this.scsec);
/* 121 */         this.cal.set(14, 0);
/* 122 */         this.date = this.cal.getTime();
/* 123 */         AMLog.info("SummaryMailer: date : " + this.date);
/*     */       }
/*     */       
/* 126 */       Scheduler.getScheduler("main").scheduleTask(this, this.date);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getMoContentForTemplate(ArrayList sortavlist, Hashtable moData, int noOfRows)
/*     */   {
/* 133 */     String morow = "";
/* 134 */     int size = sortavlist.size();
/* 135 */     String[] orderkey = new String[noOfRows];
/* 136 */     if (size > noOfRows)
/*     */     {
/* 138 */       size = noOfRows; }
/*     */     Enumeration enu;
/* 140 */     for (int k = 0; k < size; k++)
/*     */     {
/* 142 */       Hashtable temptab = (Hashtable)sortavlist.get(k);
/* 143 */       for (enu = temptab.keys(); enu.hasMoreElements();)
/*     */       {
/* 145 */         orderkey[k] = ((String)enu.nextElement());
/*     */       }
/*     */     }
/*     */     
/* 149 */     int mocount = 1;
/* 150 */     for (int p = 0; p < noOfRows; p++)
/*     */     {
/* 152 */       if (orderkey[p] == null)
/*     */         break;
/* 154 */       AMLog.info("^^^^^^^^^^^^^^^^^Order Key ^^^^^^^^^^^^^^^" + orderkey[p]);
/* 155 */       ArrayList modata = (ArrayList)moData.get(orderkey[p]);
/* 156 */       String resid = orderkey[p].substring(orderkey[p].indexOf("$") + 1, orderkey[p].indexOf("#"));
/* 157 */       int residint = Integer.parseInt(resid);
/* 158 */       String dispname = orderkey[p].substring(orderkey[p].indexOf("#") + 1);
/* 159 */       if ((EnterpriseUtil.isAdminServer()) && (residint > EnterpriseUtil.RANGE))
/*     */       {
/* 161 */         dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/*     */       }
/*     */       
/* 164 */       double perc = 0.0D;
/* 165 */       double unmg = 0.0D;
/* 166 */       double sch = 0.0D;
/* 167 */       String dtime = new String();
/* 168 */       String uptime = new String();
/* 169 */       String downfor = new String();
/* 170 */       String reason = new String();
/* 171 */       for (int i = 0; i < modata.size(); i++)
/*     */       {
/*     */         try
/*     */         {
/* 175 */           Hashtable avltab = (Hashtable)modata.get(i);
/* 176 */           String status = (String)avltab.get("STATUS");
/*     */           
/* 178 */           if (!status.trim().equals("UNAVAILABLE"))
/*     */           {
/* 180 */             double perinst = Double.parseDouble(String.valueOf((Float)avltab.get("PERCENTAGE")));
/* 181 */             perc = BigDecimal.valueOf(perc).add(BigDecimal.valueOf(perinst)).doubleValue();
/*     */           }
/*     */           else
/*     */           {
/* 185 */             dtime = dtime + avltab.get("STARTTIME").toString().substring(4, 20) + "<br/>";
/* 186 */             uptime = uptime + avltab.get("ENDTIME").toString().substring(4, 20) + "<br/>";
/* 187 */             long down_long = ((Date)avltab.get("STARTTIME")).getTime();
/* 188 */             long up_long = ((Date)avltab.get("ENDTIME")).getTime();
/* 189 */             long diff = up_long - down_long;
/* 190 */             downfor = downfor + ReportUtilities.format(diff) + "<br/>";
/* 191 */             if (avltab.get("TYPE") != null)
/*     */             {
/* 193 */               if (((Long)avltab.get("TYPE")).intValue() == 2)
/*     */               {
/* 195 */                 double perinst = Double.parseDouble(String.valueOf((Float)avltab.get("PERCENTAGE")));
/* 196 */                 unmg = BigDecimal.valueOf(unmg).add(BigDecimal.valueOf(perinst)).doubleValue();
/*     */                 
/* 198 */                 if (((Long)avltab.get("REASONID")).intValue() == -1) {
/* 199 */                   reason = reason + FormatUtil.getString("am.reporting.admin.summaryreport.Unmanaged") + "<br/>";
/*     */                 }
/*     */                 else {
/* 202 */                   reason = reason + getReason(((Long)avltab.get("REASONID")).intValue()) + "<br/>";
/*     */                 }
/*     */               }
/* 205 */               if (((Long)avltab.get("TYPE")).intValue() == 3)
/*     */               {
/* 207 */                 double perinst = Double.parseDouble(String.valueOf((Float)avltab.get("PERCENTAGE")));
/* 208 */                 sch = BigDecimal.valueOf(sch).add(BigDecimal.valueOf(perinst)).doubleValue();
/*     */                 
/* 210 */                 if (((Long)avltab.get("REASONID")).intValue() == -1) {
/* 211 */                   reason = reason + FormatUtil.getString("am.reporting.admin.summaryreport.Maintenance") + "<br/>";
/*     */                 }
/*     */                 else {
/* 214 */                   reason = reason + getReason(((Long)avltab.get("REASONID")).intValue()) + "<br/>";
/*     */                 }
/*     */               }
/* 217 */               if (((Long)avltab.get("TYPE")).intValue() == 1)
/*     */               {
/* 219 */                 double perinst = Double.parseDouble(String.valueOf((Float)avltab.get("PERCENTAGE")));
/*     */                 
/* 221 */                 if (((Long)avltab.get("REASONID")).intValue() == -1) {
/* 222 */                   reason = reason + FormatUtil.getString("am.reporting.admin.summaryreport.MoDown") + "<br/>";
/*     */                 }
/*     */                 else {
/* 225 */                   reason = reason + getReason(((Long)avltab.get("REASONID")).intValue()) + "<br/>";
/*     */                 }
/*     */                 
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 234 */           AMLog.info("^^^^^^^^^^^^^^^^^Exception occured in parsing prop of mo");
/* 235 */           ex.printStackTrace();
/*     */         }
/*     */       }
/* 238 */       if ((dtime.length() == 0) || (uptime.length() == 0)) {
/* 239 */         dtime = uptime = downfor = reason = "&nbsp;-";
/* 240 */         perc = 100.0D;
/*     */       }
/*     */       
/* 243 */       long upwidth = Math.round(perc);
/* 244 */       long unmgwidth = Math.round(unmg);
/* 245 */       long schwidth = Math.round(sch);
/* 246 */       long downwidth = 0L;
/* 247 */       downwidth = 100L - (upwidth + unmgwidth + schwidth);
/*     */       
/* 249 */       morow = morow + "<tr><td class=\"whitegrayborder-rig\">" + Integer.toString(mocount) + "</td><td class=\"whitegrayborder-rig\">" + dispname + "</td><td class=\"whitegrayborder-rig\">" + dtime + "</td><td class=\"whitegrayborder-rig\">" + uptime + "</td><td class=\"whitegrayborder-rig\">" + downfor + "</td><td class=\"whitegrayborder-rig\">" + reason + "</td><td class=\"whitegrayborder-lft\"><table width=\"100%\"><tr><td width=\"85%\">";
/* 250 */       if (upwidth == 100L)
/*     */       {
/* 252 */         morow = morow + "<table width=\"100%\"><tr><td width=\"" + upwidth + "%\" bgcolor=\"#00FF00\" height=\"5\"></td></tr></table>";
/*     */       }
/* 254 */       else if (downwidth == 100L)
/*     */       {
/* 256 */         morow = morow + "<table width=\"100%\"><tr><td width=\"" + downwidth + "%\" bgcolor=\"#FF0000\" height=\"5\"></td></tr></table>";
/*     */       }
/*     */       else
/*     */       {
/* 260 */         morow = morow + "<table cellspacing=\"0\" cellpadding=\"0\" width=\"95%\"><tr><td width=\"" + upwidth + "%\" bgcolor=\"#00FF00\" height=\"5\"></td><td width=\"" + downwidth + "%\" bgcolor=\"#FF0000\" height=\"5\"></td> <td width=\"" + unmgwidth + "%\" bgcolor=\"#0066CC\" height=\"5\"></td> <td width=\"" + schwidth + "%\" bgcolor=\"#FF00FF\" height=\"5\"></td> </tr></table>";
/*     */       }
/* 262 */       morow = morow + "</td><td width=\"15%\" class=\"body-text\">" + perc + " % </td></tr></table></td></tr>";
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 268 */       mocount++;
/*     */     }
/* 270 */     return morow;
/*     */   }
/*     */   
/*     */   public ArrayList getListOfAllMos(Hashtable moData)
/*     */   {
/* 275 */     Hashtable allmoavail = new Hashtable();
/* 276 */     Hashtable lowtenavail = new Hashtable();
/* 277 */     Hashtable temptable = new Hashtable();
/* 278 */     ArrayList allavalist = new ArrayList();
/* 279 */     Integer[] percorder = new Integer['ߐ'];
/*     */     
/* 281 */     int dumcount = 0;
/* 282 */     for (Enumeration enu = moData.keys(); enu.hasMoreElements();)
/*     */     {
/* 284 */       String temp = (String)enu.nextElement();
/* 285 */       ArrayList datalist = (ArrayList)moData.get(temp);
/* 286 */       double perc = 0.0D;
/* 287 */       double unmg = 0.0D;
/* 288 */       double sch = 0.0D;
/* 289 */       for (int i = 0; i < datalist.size(); i++)
/*     */       {
/*     */         try
/*     */         {
/* 293 */           Hashtable avltab = (Hashtable)datalist.get(i);
/* 294 */           String status = (String)avltab.get("STATUS");
/* 295 */           if (!status.trim().equals("UNAVAILABLE"))
/*     */           {
/* 297 */             double perinst = Double.parseDouble(String.valueOf((Float)avltab.get("PERCENTAGE")));
/* 298 */             perc = BigDecimal.valueOf(perc).add(BigDecimal.valueOf(perinst)).doubleValue();
/*     */           }
/* 300 */           else if (avltab.get("TYPE") != null)
/*     */           {
/* 302 */             if (((Long)avltab.get("TYPE")).intValue() == 2)
/*     */             {
/* 304 */               double perinst = Double.parseDouble(String.valueOf((Float)avltab.get("PERCENTAGE")));
/* 305 */               unmg = BigDecimal.valueOf(unmg).add(BigDecimal.valueOf(perinst)).doubleValue();
/*     */             }
/* 307 */             else if (((Long)avltab.get("TYPE")).intValue() != 1)
/*     */             {
/* 309 */               double perinst = Double.parseDouble(String.valueOf((Float)avltab.get("PERCENTAGE")));
/* 310 */               sch = BigDecimal.valueOf(sch).add(BigDecimal.valueOf(perinst)).doubleValue();
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 316 */           AMLog.info("^^^^^^^^^^^^^^^^^Exception occured in parsing prop");
/* 317 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/* 321 */       Hashtable htab = new Hashtable();
/* 322 */       ArrayList moValues = new ArrayList();
/* 323 */       if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/* 324 */         allmoavail.put(temp, Double.valueOf(perc - (unmg + sch)));
/* 325 */         moValues.add(Double.valueOf(perc - (unmg + sch)));
/* 326 */         moValues.add(Double.valueOf(unmg));
/* 327 */         moValues.add(Double.valueOf(sch));
/*     */       } else {
/* 329 */         allmoavail.put(temp, Double.valueOf(perc));
/* 330 */         moValues.add(Double.valueOf(perc));
/* 331 */         moValues.add(Integer.valueOf(0));
/* 332 */         moValues.add(Integer.valueOf(0));
/*     */       }
/* 334 */       htab.put(temp, moValues);
/* 335 */       allavalist.add(htab);
/* 336 */       dumcount++;
/*     */     }
/* 338 */     return allavalist;
/*     */   }
/*     */   
/*     */   public String getMgContentForTemplate(ArrayList sortmglist, Hashtable mgData)
/*     */   {
/* 343 */     String datarow = "";
/* 344 */     int count = 1;
/*     */     try
/*     */     {
/* 347 */       String dbval = DBUtil.hasGlobalConfigValue("MGServiceAvailability") ? DBUtil.getGlobalConfigValue("MGServiceAvailability") : "false";
/* 348 */       for (int p = 0; p < sortmglist.size(); p++)
/*     */       {
/* 350 */         Hashtable temptab = (Hashtable)sortmglist.get(p);
/* 351 */         Enumeration enu = temptab.keys();
/* 352 */         String temp = (String)enu.nextElement();
/* 353 */         ArrayList datalist = (ArrayList)mgData.get(temp);
/* 354 */         int tem = temp.indexOf("#");
/* 355 */         String resid = temp.substring(temp.indexOf("$") + 1, tem);
/* 356 */         int residint = Integer.parseInt(resid);
/* 357 */         String dispname = temp.substring(tem + 1);
/* 358 */         if ((EnterpriseUtil.isAdminServer()) && (residint > EnterpriseUtil.RANGE))
/*     */         {
/* 360 */           dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/*     */         }
/* 362 */         double perc = 0.0D;
/* 363 */         double unmg = 0.0D;
/* 364 */         double sch = 0.0D;
/* 365 */         String dtime = new String();
/* 366 */         String uptime = new String();
/* 367 */         String downfor = new String();
/* 368 */         String reason = new String();
/* 369 */         for (int i = 0; i < datalist.size(); i++)
/*     */         {
/*     */           try
/*     */           {
/* 373 */             Hashtable avltab = (Hashtable)datalist.get(i);
/* 374 */             String status = (String)avltab.get("STATUS");
/* 375 */             if (!status.trim().equals("UNAVAILABLE"))
/*     */             {
/* 377 */               double perinst = Double.parseDouble(String.valueOf((Float)avltab.get("PERCENTAGE")));
/* 378 */               perc = BigDecimal.valueOf(perc).add(BigDecimal.valueOf(perinst)).doubleValue();
/*     */             }
/*     */             else
/*     */             {
/* 382 */               dtime = dtime + avltab.get("STARTTIME").toString().substring(4, 20) + "<br/>";
/* 383 */               uptime = uptime + avltab.get("ENDTIME").toString().substring(4, 20) + "<br/>";
/* 384 */               long down_long = ((Date)avltab.get("STARTTIME")).getTime();
/* 385 */               long up_long = ((Date)avltab.get("ENDTIME")).getTime();
/* 386 */               long diff = up_long - down_long;
/* 387 */               downfor = downfor + ReportUtilities.format(diff) + "<br/>";
/*     */               
/* 389 */               if (((Long)avltab.get("TYPE")).intValue() == 1)
/*     */               {
/* 391 */                 if (((Long)avltab.get("REASONID")).intValue() == -1) {
/* 392 */                   reason = reason + FormatUtil.getString("am.reporting.admin.summaryreport.MgDown") + "<br/>";
/*     */                 }
/*     */                 else {
/* 395 */                   reason = reason + getReason(((Long)avltab.get("REASONID")).intValue()) + "<br/>";
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 402 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 405 */         if ((dtime.length() == 0) || (uptime.length() == 0)) {
/* 406 */           dtime = uptime = downfor = reason = "&nbsp; -";
/* 407 */           perc = 100.0D;
/*     */         }
/* 409 */         if ("true".equalsIgnoreCase(dbval))
/*     */         {
/* 411 */           ArrayList availVal = (ArrayList)temptab.get(temp);
/* 412 */           perc = ((Double)availVal.get(0)).doubleValue();
/* 413 */           unmg = ((Double)availVal.get(1)).doubleValue();
/* 414 */           sch = ((Double)availVal.get(2)).doubleValue();
/*     */         }
/*     */         
/* 417 */         long downwidth = 0L;
/* 418 */         long upwidth = Long.valueOf(Math.round(perc)).longValue();
/* 419 */         long unmgwidth = Long.valueOf(Math.round(unmg)).longValue();
/* 420 */         long schwidth = Long.valueOf(Math.round(sch)).longValue();
/* 421 */         downwidth = 100L - (upwidth + unmgwidth + schwidth);
/* 422 */         if ((perc > 99.0D) && (perc < 100.0D))
/*     */         {
/* 424 */           upwidth = 99L;
/* 425 */           downwidth = 1L;
/*     */         }
/*     */         
/* 428 */         datarow = datarow + "<tr><td class=\"whitegrayborder-rig\">" + Integer.toString(count) + "</td><td class=\"whitegrayborder-rig\">" + dispname + "</td><td class=\"whitegrayborder-rig\">" + dtime + "</td><td class=\"whitegrayborder-rig\">" + uptime + "</td><td class=\"whitegrayborder-rig\">" + downfor + "</td><td class=\"whitegrayborder-rig\">" + reason + "</td><td class=\"whitegrayborder-lft\"><table width=\"100%\"><tr><td width=\"85%\">";
/* 429 */         if (upwidth == 100L)
/*     */         {
/* 431 */           datarow = datarow + "<table width=\"100%\"><tr><td width=\"" + upwidth + "%\" bgcolor=\"#00FF00\" height=\"5\"></td></tr></table>";
/*     */         }
/* 433 */         else if (downwidth == 100L)
/*     */         {
/* 435 */           datarow = datarow + "<table width=\"100%\"><tr><td width=\"" + downwidth + "%\" bgcolor=\"#FF0000\" height=\"5\"></td></tr></table>";
/*     */         }
/*     */         else
/*     */         {
/* 439 */           datarow = datarow + "<table cellspacing=\"0\" cellpadding=\"0\" width=\"95%\"><tr><td width=\"" + upwidth + "%\" bgcolor=\"#00FF00\" height=\"5\"></td><td width=\"" + downwidth + "%\" bgcolor=\"#FF0000\" height=\"5\"></td> <td width=\"" + unmgwidth + "%\" bgcolor=\"#0066CC\" height=\"5\"></td> <td width=\"" + schwidth + "%\" bgcolor=\"#FF00FF\" height=\"5\"></td> </tr></table>";
/*     */         }
/* 441 */         datarow = datarow + "</td><td width=\"15%\" class=\"body-text\">" + perc + " % </td></tr></table></td></tr>";
/* 442 */         count++;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 447 */       e.printStackTrace();
/*     */     }
/* 449 */     return datarow;
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayList getListOfAllMgs(Hashtable datatable)
/*     */   {
/* 455 */     ArrayList allmglist = new ArrayList();
/*     */     
/*     */     try
/*     */     {
/* 459 */       dbval = DBUtil.hasGlobalConfigValue("MGServiceAvailability") ? DBUtil.getGlobalConfigValue("MGServiceAvailability") : "false";
/*     */       
/* 461 */       for (enu = datatable.keys(); enu.hasMoreElements();)
/*     */       {
/* 463 */         String temp = (String)enu.nextElement();
/* 464 */         ArrayList datalist = (ArrayList)datatable.get(temp);
/* 465 */         int tem = temp.indexOf("#");
/* 466 */         String resid = temp.substring(temp.indexOf("$") + 1, tem);
/*     */         
/* 468 */         Properties prop = new Properties();
/* 469 */         double perc = 0.0D;
/* 470 */         double unmg = 0.0D;
/* 471 */         double sch = 0.0D;
/* 472 */         String dtime = "";
/* 473 */         String uptime = "";
/* 474 */         if ("true".equalsIgnoreCase(dbval))
/*     */         {
/* 476 */           prop = ReportUtilities.getServiceAvailabilityStatsForMO(resid, "0", "0", "29", "", "oni");
/* 477 */           if (!prop.getProperty("available").equalsIgnoreCase("NA")) {
/* 478 */             perc = Double.valueOf(String.valueOf(Float.valueOf(prop.getProperty("available")))).doubleValue();
/*     */           }
/* 480 */           if (!prop.getProperty("ServicesUnMgPercent").equalsIgnoreCase("NA")) {
/* 481 */             unmg = Double.valueOf(String.valueOf(Float.valueOf(prop.getProperty("ServicesUnMgPercent")))).doubleValue();
/*     */           }
/* 483 */           if (!prop.getProperty("ServicesSchPercent").equalsIgnoreCase("NA")) {
/* 484 */             sch = Double.valueOf(String.valueOf(Float.valueOf(prop.getProperty("ServicesSchPercent")))).doubleValue();
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 489 */           for (int i = 0; i < datalist.size(); i++)
/*     */           {
/*     */             try
/*     */             {
/* 493 */               Hashtable avltab = (Hashtable)datalist.get(i);
/* 494 */               String status = (String)avltab.get("STATUS");
/* 495 */               if (!status.trim().equals("UNAVAILABLE"))
/*     */               {
/* 497 */                 double perinst = Double.valueOf(String.valueOf((Float)avltab.get("PERCENTAGE"))).doubleValue();
/* 498 */                 perc = BigDecimal.valueOf(perc).add(BigDecimal.valueOf(perinst)).doubleValue();
/*     */               }
/*     */             }
/*     */             catch (Exception ex)
/*     */             {
/* 503 */               ex.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/* 507 */         Hashtable htab = new Hashtable();
/* 508 */         ArrayList values = new ArrayList();
/* 509 */         values.add(Double.valueOf(perc));
/* 510 */         values.add(Double.valueOf(unmg));
/* 511 */         values.add(Double.valueOf(sch));
/* 512 */         htab.put(temp, values);
/* 513 */         allmglist.add(htab);
/*     */       }
/*     */     } catch (Exception e) {
/*     */       String dbval;
/*     */       Enumeration enu;
/* 518 */       e.printStackTrace();
/*     */     }
/* 520 */     return allmglist;
/*     */   }
/*     */   
/*     */   public String getPopulatedSummaryMailerTmpl(String htmlmess, int no_of_days)
/*     */   {
/*     */     try
/*     */     {
/* 527 */       String host = Constants.getAppHostName();
/* 528 */       String port = System.getProperty("webserver.port");
/*     */       
/* 530 */       long t = System.currentTimeMillis();
/* 531 */       this.cal.setTime(new Date(t));
/* 532 */       String end_tim = new Date(t).toString().substring(4, 16);
/* 533 */       String st_tim = new Date(ReportUtilities.getStartTime(no_of_days)).toString().substring(4, 16);
/* 534 */       String dbval = DBUtil.hasGlobalConfigValue("MGServiceAvailability") ? DBUtil.getGlobalConfigValue("MGServiceAvailability") : "false";
/*     */       
/* 536 */       String fline = FormatUtil.findReplace(htmlmess, "~reportheading~", FormatUtil.getString("am.reporting.admin.summarymail.heading", new String[] { FormatUtil.getString(st_tim), FormatUtil.getString(end_tim) }));
/* 537 */       String mghline = FormatUtil.findReplace(fline, "~mgtopheading~", FormatUtil.getString("am.reporting.admin.summarymail.mgheading"));
/* 538 */       String mgnoco = FormatUtil.findReplace(mghline, "~no~", FormatUtil.getString("am.reporting.admin.summarymail.coloumn.number"));
/* 539 */       String mgco = FormatUtil.findReplace(mgnoco, "~monitor groups~", FormatUtil.getString("am.reporting.admin.summarymail.coloumn.mgname"));
/* 540 */       String mgfrom = FormatUtil.findReplace(mgco, "~from~", FormatUtil.getString("am.reporting.admin.summarymail.coloumn.downfrom"));
/* 541 */       String mgto = FormatUtil.findReplace(mgfrom, "~to~", FormatUtil.getString("am.reporting.admin.summarymail.coloumn.upto"));
/* 542 */       String mgdwfor = FormatUtil.findReplace(mgto, "~downfor~", FormatUtil.getString("am.reporting.admin.summarymail.coloumn.downfor"));
/*     */       
/* 544 */       String mgdwreason = FormatUtil.findReplace(mgdwfor, "~reason~", FormatUtil.getString("am.webclient.historydata.downtime.reason.text"));
/*     */       
/* 546 */       String mgavail = FormatUtil.findReplace(mgdwreason, "~availibility~", FormatUtil.getString("am.reporting.admin.summarymail.coloumn.availibility"));
/* 547 */       String mgspcl = "";
/* 548 */       if ("true".equalsIgnoreCase(dbval))
/*     */       {
/* 550 */         mgspcl = FormatUtil.findReplace(mgavail, "~mg~", FormatUtil.getString("am.reporting.admin.summarymail.mgserviceavail.text"));
/*     */       }
/*     */       else
/*     */       {
/* 554 */         mgspcl = FormatUtil.findReplace(mgavail, "~mg~", "");
/*     */       }
/*     */       
/* 557 */       String mohline = FormatUtil.findReplace(mgspcl, "~montopheading~", FormatUtil.getString("am.reporting.admin.summarymail.moheading"));
/* 558 */       String moco = FormatUtil.findReplace(mohline, "~monitor~", FormatUtil.getString("am.reporting.admin.summarymail.coloumn.moname"));
/* 559 */       String uinfo = FormatUtil.findReplace(moco, "~userinfo~", FormatUtil.getString("am.reporting.admin.summarymail.userinfo"));
/*     */       
/* 561 */       String addinfo = FormatUtil.findReplace(uinfo, "~addinfo~", FormatUtil.getString("am.webclient.managermail.additionalinfo.text"));
/* 562 */       String repby = FormatUtil.findReplace(addinfo, "~reportby~", FormatUtil.getString("am.webclient.managermail.reportby.text"));
/* 563 */       String hostFilled = FormatUtil.findReplace(repby, "~host~", host);
/*     */       
/*     */ 
/*     */ 
/* 567 */       String nameFilled = null;
/* 568 */       if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*     */       {
/* 570 */         nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("rebrand.product.name"));
/*     */       }
/*     */       else
/*     */       {
/* 574 */         nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("product.name"));
/*     */       }
/*     */       
/* 577 */       htmlmess = FormatUtil.findReplace(nameFilled, "~port~", port);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 581 */       e.printStackTrace();
/*     */     }
/* 583 */     return htmlmess;
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayList getSorted(ArrayList alldata)
/*     */   {
/* 589 */     for (int i = 0; i < alldata.size() - 1; i++)
/*     */     {
/* 591 */       for (int j = 0; j < alldata.size() - 1 - i; j++)
/*     */       {
/* 593 */         Hashtable dat1 = (Hashtable)alldata.get(j);
/* 594 */         Hashtable dat2 = (Hashtable)alldata.get(j + 1);
/* 595 */         Enumeration enu1 = dat1.keys();
/* 596 */         String mntrkey1 = (String)enu1.nextElement();
/*     */         
/* 598 */         double availib1 = ((Double)((ArrayList)dat1.get(mntrkey1)).get(0)).doubleValue();
/* 599 */         Enumeration enu2 = dat2.keys();
/* 600 */         String mntrkey2 = (String)enu2.nextElement();
/* 601 */         double availib2 = ((Double)((ArrayList)dat2.get(mntrkey2)).get(0)).doubleValue();
/*     */         
/* 603 */         if (availib2 < availib1)
/*     */         {
/* 605 */           alldata.set(j, dat2);
/* 606 */           alldata.set(j + 1, dat1);
/*     */         }
/*     */       }
/*     */     }
/* 610 */     return alldata;
/*     */   }
/*     */   
/*     */   public boolean getDataFromDB()
/*     */   {
/* 615 */     boolean checktodo = false;
/* 616 */     ResultSet rs = null;
/* 617 */     boolean isNotInt = false;
/*     */     try
/*     */     {
/* 620 */       this.status = (DBUtil.hasGlobalConfigValue("SummaryMailer") ? DBUtil.getGlobalConfigValue("SummaryMailer").equals("enable") : false);
/* 621 */       if (!this.status)
/*     */       {
/* 623 */         return false;
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 628 */       ex.printStackTrace();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 633 */       if (DBUtil.hasGlobalConfigValue("SummaryMailer.Time")) {
/* 634 */         String value = DBUtil.getGlobalConfigValue("SummaryMailer.Time");
/* 635 */         String[] timearry = value.split(":");
/* 636 */         this.schour = Integer.parseInt(timearry[0]);
/* 637 */         this.scmin = Integer.parseInt(timearry[1]);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 642 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 645 */     String actionid = "";
/* 646 */     if (DBUtil.hasGlobalConfigValue("SummaryMailer.Emailid"))
/*     */     {
/*     */       try
/*     */       {
/* 650 */         actionid = DBUtil.getGlobalConfigValue("SummaryMailer.Emailid");
/* 651 */         if ((actionid != null) && (!actionid.equals("")))
/*     */         {
/* 653 */           Integer.parseInt(actionid);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 658 */         isNotInt = true;
/*     */       }
/*     */     }
/*     */     
/* 662 */     if (isNotInt)
/*     */     {
/* 664 */       this.fromadd = actionid;
/* 665 */       this.toadd = actionid;
/* 666 */       checktodo = true;
/* 667 */       return checktodo;
/*     */     }
/* 669 */     if ((actionid != null) && (!actionid.equals("")))
/*     */     {
/*     */       try
/*     */       {
/* 673 */         String query = "select  AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,AM_EMAILACTION.TOADDRESS,AM_EMAILACTION.FROMADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1 and AM_ACTIONPROFILE.ID=" + actionid;
/* 674 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 675 */         if (rs.next())
/*     */         {
/* 677 */           this.fromadd = rs.getString("FROMADDRESS");
/* 678 */           this.toadd = rs.getString("TOADDRESS");
/* 679 */           checktodo = true;
/* 680 */           return true;
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 685 */         ex.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 689 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 696 */     ResultSet adminset = null;
/*     */     try
/*     */     {
/* 699 */       String adminid_query = "select EMAILID from AM_UserPasswordTable where USERNAME='admin'";
/* 700 */       adminset = AMConnectionPool.executeQueryStmt(adminid_query);
/* 701 */       adminset.next();
/*     */       
/* 703 */       this.fromadd = adminset.getString("EMAILID");
/* 704 */       if ((this.fromadd != null) && (this.fromadd.length() > 1))
/*     */       {
/* 706 */         this.toadd = adminset.getString("EMAILID");
/* 707 */         checktodo = true;
/* 708 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 712 */       this.fromadd = (this.toadd = DBUtil.getGlobalConfigValue("GlobalEmailAddress"));
/* 713 */       checktodo = true;
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 718 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 722 */       AMConnectionPool.closeStatement(adminset);
/*     */     }
/* 724 */     return checktodo;
/*     */   }
/*     */   
/*     */   public String getReason(int id) {
/* 728 */     ResultSet rs = null;
/* 729 */     String newreason = null;
/* 730 */     String query = "select SHORT_DESCRIPTION from AM_DOWNTIMEREASON where REASONID=" + id;
/*     */     try
/*     */     {
/* 733 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 734 */       if (rs.next()) {
/* 735 */         newreason = rs.getString("SHORT_DESCRIPTION");
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 739 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 743 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 745 */     return newreason;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\SummaryMailer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */