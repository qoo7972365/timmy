/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.SmtpMailer;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.management.scheduler.Scheduler;
/*     */ import java.net.InetAddress;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BusyJob
/*     */   extends Thread
/*     */ {
/*  33 */   int resourceID = -1;
/*  34 */   volatile boolean markedForStopping = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/*  44 */       String host = InetAddress.getLocalHost().getHostName();
/*  45 */       String port = System.getProperty("webserver.port");
/*     */       
/*  47 */       String htmlmess = ComparingSla.getHTMLMailTemplate();
/*     */       
/*  49 */       String slaname = "";
/*  50 */       String rcause = "";
/*  51 */       String actid = "";
/*  52 */       String fromadd = "";
/*  53 */       String toadd = "";
/*  54 */       String subject = "";
/*  55 */       String message = "";
/*  56 */       String mesag = "";
/*  57 */       String fline = "";
/*  58 */       String addinfo = "";
/*  59 */       String repby = "";
/*  60 */       String resource = "";
/*  61 */       String attr = "";
/*  62 */       String messa = "";
/*  63 */       String hostFilled = "";
/*  64 */       String portFilled = "";
/*  65 */       String nameFilled = "";
/*  66 */       String ttsla = "";
/*  67 */       String ttvalue = "";
/*  68 */       String root = "";
/*  69 */       String user = "";
/*  70 */       String availsla = "";
/*  71 */       ResultSet rs = null;
/*     */       
/*  73 */       ComparingSla cs = new ComparingSla();
/*  74 */       Hashtable ht = new Hashtable();
/*  75 */       Hashtable ht1 = new Hashtable();
/*  76 */       Hashtable hb = new Hashtable();
/*  77 */       Hashtable hb1 = new Hashtable();
/*  78 */       ArrayList availdata = cs.checkingApplicationTroubleTicket(2);
/*     */       
/*  80 */       if (availdata != null)
/*     */       {
/*  82 */         ArrayList alist = new ArrayList();
/*     */         
/*  84 */         for (int g = 0; g < availdata.size(); g++)
/*     */         {
/*  86 */           alist = (ArrayList)availdata.get(g);
/*  87 */           String resid = (String)alist.get(0);
/*  88 */           String resname = (String)alist.get(1);
/*  89 */           String slid = (String)alist.get(6);
/*  90 */           slaname = (String)alist.get(7);
/*  91 */           String slaop = (String)alist.get(8);
/*  92 */           String slaval = (String)alist.get(9);
/*  93 */           Properties props = (Properties)alist.get(10);
/*  94 */           availsla = props.getProperty("SLA");
/*  95 */           String per = props.getProperty("period");
/*  96 */           String tickop = (String)alist.get(11);
/*  97 */           String tickval = (String)alist.get(12);
/*  98 */           String tickdur = (String)alist.get(13);
/*  99 */           ArrayList a1 = (ArrayList)alist.get(14);
/*     */           
/* 101 */           for (int h = 0; h < a1.size(); h++)
/*     */           {
/* 103 */             ttvalue = (String)a1.get(1);
/* 104 */             ttsla = (String)a1.get(3);
/*     */           }
/* 106 */           Properties p3 = (Properties)alist.get(15);
/* 107 */           String mailoption = p3.getProperty("mailoption");
/*     */           
/* 109 */           String slava = slaop + " " + slaval;
/* 110 */           String sltick = tickop + " " + tickval;
/* 111 */           Properties pr = new Properties();
/* 112 */           ArrayList arr = new ArrayList();
/* 113 */           if (!slid.equalsIgnoreCase("not set"))
/*     */           {
/* 115 */             pr.setProperty("sname", slaname);
/* 116 */             pr.setProperty("availop", slava);
/* 117 */             pr.setProperty("tickop", sltick);
/* 118 */             pr.setProperty("mailoption", mailoption);
/*     */             
/* 120 */             hb.put(slid, pr);
/* 121 */             arr = cs.getDataFromAction(slid);
/*     */           }
/*     */           
/* 124 */           if ((!slid.equalsIgnoreCase("not set")) && (arr.size() > 0))
/*     */           {
/* 126 */             if ((availsla.equalsIgnoreCase("FAIL")) && (ttsla.equalsIgnoreCase("FAIL")))
/*     */             {
/* 128 */               rcause = " <li> " + resname + "   (" + FormatUtil.getString("am.webclient.managermail.availability") + "," + FormatUtil.getString("am.webclient.managermail.troubleticket") + ")<br>";
/*     */             }
/* 130 */             else if (availsla.equalsIgnoreCase("FAIL"))
/*     */             {
/* 132 */               rcause = " <li> " + resname + "   (" + FormatUtil.getString("am.webclient.managermail.availability") + ")<br>";
/*     */             }
/* 134 */             else if (ttsla.equalsIgnoreCase("FAIL"))
/*     */             {
/* 136 */               rcause = " <li> " + resname + "   (" + FormatUtil.getString("am.webclient.managermail.troubleticket") + ")<br>";
/*     */             }
/*     */             else
/*     */             {
/* 140 */               rcause = "PASS";
/*     */             }
/*     */             
/* 143 */             if (!ht.containsKey(slid))
/*     */             {
/* 145 */               Hashtable htable = new Hashtable();
/* 146 */               htable.put(resid, rcause);
/* 147 */               ht.put(slid, htable);
/*     */             }
/*     */             else
/*     */             {
/* 151 */               Hashtable h = (Hashtable)ht.get(slid);
/* 152 */               h.put(resid, rcause);
/* 153 */               ht.put(slid, h);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 159 */       if (ht.size() > 0)
/*     */       {
/* 161 */         Enumeration en = ht.keys();
/* 162 */         String sid = "";
/* 163 */         String rc = "";
/* 164 */         String rid = "";
/* 165 */         String rooting = "";
/* 166 */         String defaultInfo = "";
/* 167 */         String returnVal; while (en.hasMoreElements())
/*     */         {
/* 169 */           sid = (String)en.nextElement();
/*     */           
/* 171 */           Hashtable hbl = (Hashtable)ht.get(sid);
/* 172 */           Enumeration et = hbl.keys();
/* 173 */           boolean isFailMessageExists = false;
/* 174 */           while (et.hasMoreElements())
/*     */           {
/* 176 */             rid = (String)et.nextElement();
/* 177 */             rooting = (String)hbl.get(rid);
/*     */             
/* 179 */             if (!rooting.equalsIgnoreCase("PASS"))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 185 */               rc = rc + rooting;
/* 186 */               isFailMessageExists = true;
/*     */             }
/*     */           }
/* 189 */           if (isFailMessageExists)
/*     */           {
/*     */ 
/*     */ 
/* 193 */             String inform = FormatUtil.replaceStringBySpecifiedString(rc, "<br>", "\n", 0);
/* 194 */             Properties pro = (Properties)hb.get(sid);
/* 195 */             String slaname1 = pro.getProperty("sname");
/* 196 */             String availop1 = pro.getProperty("availop");
/* 197 */             String ttop1 = pro.getProperty("tickop");
/* 198 */             String mailing = pro.getProperty("mailoption");
/*     */             
/* 200 */             defaultInfo = "\n *************************************************\n ";
/* 201 */             if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*     */             {
/* 203 */               defaultInfo = defaultInfo + FormatUtil.getString("am.webclient.managermail.firstline.text", new String[] { host, port, OEMUtil.getOEMString("rebrand.product.name") });
/*     */             }
/*     */             else
/*     */             {
/* 207 */               defaultInfo = defaultInfo + FormatUtil.getString("am.webclient.managermail.firstline.text", new String[] { host, port, OEMUtil.getOEMString("product.name") });
/*     */             }
/* 209 */             defaultInfo = defaultInfo + "\n " + FormatUtil.getString("am.webclient.managermail.secondline.text") + "\n\n";
/* 210 */             String sourc = FormatUtil.getString("am.webclient.managermail.slaname.text") + "             :" + slaname1 + "\n";
/* 211 */             String attrib = FormatUtil.getString("am.webclient.managermail.availability.text") + "            :" + availop1 + "\n";
/* 212 */             defaultInfo = defaultInfo + sourc + attrib + FormatUtil.getString("am.webclient.managermail.troubleticketvolume.text") + "  :" + ttop1 + "\n";
/* 213 */             defaultInfo = defaultInfo + FormatUtil.getString("am.webclient.managermail.rootcause.text") + "             : " + inform + " \n";
/*     */             
/* 215 */             ArrayList data11 = cs.getDataFromAction(sid);
/* 216 */             if (data11.size() != 0)
/*     */             {
/* 218 */               for (int k = 0; k < data11.size(); k++)
/*     */               {
/* 220 */                 actid = (String)data11.get(0);
/* 221 */                 fromadd = (String)data11.get(1);
/* 222 */                 toadd = (String)data11.get(2);
/* 223 */                 subject = (String)data11.get(3);
/* 224 */                 mesag = (String)data11.get(4);
/*     */               }
/*     */               
/* 227 */               SmtpMailer mailer = new SmtpMailer(fromadd, toadd, "", subject);
/* 228 */               Hashtable additionalInfo = new Hashtable();
/* 229 */               int actionID = Integer.parseInt(actid);
/* 230 */               if (!rc.equalsIgnoreCase("PASS"))
/*     */               {
/* 232 */                 fline = FormatUtil.findReplace(htmlmess, "~topheading~", FormatUtil.getString("am.webclient.managermail.heading.text"));
/* 233 */                 resource = FormatUtil.findReplace(fline, "~source~", slaname1);
/* 234 */                 attr = FormatUtil.findReplace(resource, "~attribute~", availop1);
/* 235 */                 messa = FormatUtil.findReplace(attr, "~message~", ttop1);
/* 236 */                 messa = FormatUtil.findReplace(messa, "~heading~", FormatUtil.getString("am.webclient.managermail.bsm.heading.text"));
/* 237 */                 root = FormatUtil.findReplace(messa, "~rootcause~", rc);
/* 238 */                 String uinfo = FormatUtil.findReplace(root, "~userinfo~", FormatUtil.getString("am.webclient.managermail.bsm.message.text"));
/* 239 */                 user = FormatUtil.findReplace(uinfo, "~message~", mesag);
/* 240 */                 addinfo = FormatUtil.findReplace(user, "~addinfo~", FormatUtil.getString("am.webclient.managermail.additionalinfo.text"));
/* 241 */                 repby = FormatUtil.findReplace(addinfo, "~reportby~", FormatUtil.getString("am.webclient.managermail.reportby.text"));
/* 242 */                 hostFilled = FormatUtil.findReplace(repby, "~host~", host);
/*     */                 
/*     */ 
/*     */ 
/*     */ 
/* 247 */                 if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*     */                 {
/* 249 */                   nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("rebrand.product.name"));
/*     */                 }
/*     */                 else
/*     */                 {
/* 253 */                   nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("product.name"));
/*     */                 }
/*     */                 
/* 256 */                 portFilled = FormatUtil.findReplace(nameFilled, "~port~", port);
/*     */                 
/* 258 */                 additionalInfo.put("message", defaultInfo);
/* 259 */                 additionalInfo.put("htmlmessage", portFilled);
/* 260 */                 additionalInfo.put("shortmessage", "");
/* 261 */                 additionalInfo.put("replacevalues", new HashMap());
/*     */               }
/* 263 */               String getquery = "SELECT MAILOPTION FROM AM_SLA_DISABLEMAIL where SLAID='" + sid + "'";
/* 264 */               rs = AMConnectionPool.executeQueryStmt(getquery);
/* 265 */               int sendmailonce = 0;
/* 266 */               if (rs.next())
/*     */               {
/* 268 */                 sendmailonce = rs.getInt("MAILOPTION");
/* 269 */                 rs.close();
/*     */               }
/*     */               else
/*     */               {
/* 273 */                 String insertquery = "INSERT INTO AM_SLA_DISABLEMAIL(ID,SLAID,MAILOPTION) VALUES('" + DBQueryUtil.getIncrementedID("ID", "AM_SLA_DISABLEMAIL") + "','" + sid + "'," + sendmailonce + ")";
/* 274 */                 AMConnectionPool.executeUpdateStmt(insertquery);
/*     */               }
/*     */               
/* 277 */               if (mailing.equalsIgnoreCase("true"))
/*     */               {
/* 279 */                 if (!rc.equalsIgnoreCase("PASS"))
/*     */                 {
/*     */ 
/* 282 */                   if (sendmailonce == 0)
/*     */                   {
/*     */ 
/* 285 */                     String returnVal = mailer.sendMessage(mesag, "Success", true, portFilled, 0, null, null);
/*     */                     
/*     */ 
/* 288 */                     String mailquery = "Update AM_SLA_DISABLEMAIL SET MAILOPTION=1 WHERE SLAID='" + sid + "'";
/* 289 */                     AMConnectionPool.executeUpdateStmt(mailquery);
/*     */                   }
/*     */                 }
/*     */                 else
/*     */                 {
/* 294 */                   String dmailquery = "Update AM_SLA_DISABLEMAIL SET MAILOPTION=0 WHERE SLAID='" + sid + "'";
/* 295 */                   AMConnectionPool.executeUpdateStmt(dmailquery);
/*     */                 }
/*     */                 
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 302 */                 returnVal = mailer.sendMessage(mesag, "Success", true, portFilled, 0, null, null);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 311 */       String slaname1 = "";
/* 312 */       String actid1 = "";
/* 313 */       String sla = "";
/* 314 */       String fromadd1 = "";
/* 315 */       String toadd1 = "";
/* 316 */       String subject1 = "";
/* 317 */       String message1 = "";
/* 318 */       String mess1 = "";
/* 319 */       String snam = "";
/*     */       
/*     */ 
/* 322 */       ArrayList data = new ArrayList();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 327 */       ArrayList sysdata = cs.checkingSystemAvailablityForManager(2);
/* 328 */       for (int g = 0; g < sysdata.size(); g++)
/*     */       {
/* 330 */         data = (ArrayList)sysdata.get(g);
/* 331 */         String resid1 = (String)data.get(0);
/*     */         
/* 333 */         String resname1 = (String)data.get(1);
/* 334 */         String slid1 = (String)data.get(2);
/* 335 */         slaname1 = (String)data.get(3);
/* 336 */         snam = slaname1;
/* 337 */         String slaop1 = (String)data.get(4);
/* 338 */         String slaval1 = (String)data.get(5);
/* 339 */         Properties p1 = (Properties)data.get(6);
/* 340 */         String sysavailable = p1.getProperty("available");
/*     */         
/* 342 */         sla = p1.getProperty("SLA");
/* 343 */         String tiop = (String)data.get(7);
/* 344 */         String tival = (String)data.get(8);
/* 345 */         ArrayList alist = (ArrayList)data.get(10);
/* 346 */         String ticketvalue = "";
/* 347 */         String ticksla = "";
/* 348 */         for (int j = 0; j < alist.size(); j++)
/*     */         {
/* 350 */           ticketvalue = (String)alist.get(1);
/* 351 */           ticksla = (String)alist.get(3);
/*     */         }
/* 353 */         Properties p4 = (Properties)data.get(11);
/* 354 */         String optionmail = p4.getProperty("mailoption");
/* 355 */         String slav = slaop1 + " " + slaval1;
/* 356 */         String tick = tiop + " " + tival;
/* 357 */         Properties pr = new Properties();
/* 358 */         if (!slid1.equalsIgnoreCase("not set"))
/*     */         {
/* 360 */           pr.setProperty("sname", slaname1);
/* 361 */           pr.setProperty("availop", slav);
/* 362 */           pr.setProperty("tickop", tick);
/* 363 */           pr.setProperty("mailoption", optionmail);
/*     */           
/* 365 */           hb1.put(slid1, pr);
/*     */         }
/* 367 */         ArrayList arr1 = cs.getDataFromAction(slid1);
/*     */         
/* 369 */         if ((!slid1.equalsIgnoreCase("not set")) && (arr1.size() > 0))
/*     */         {
/* 371 */           if ((sla.equalsIgnoreCase("FAIL")) && (ticksla.equalsIgnoreCase("FAIL")))
/*     */           {
/* 373 */             rcause = " <li> " + resname1 + "   (" + FormatUtil.getString("am.webclient.managermail.availability") + "," + FormatUtil.getString("am.webclient.managermail.troubleticket") + ")<br>";
/*     */           }
/* 375 */           else if (sla.equalsIgnoreCase("FAIL"))
/*     */           {
/* 377 */             rcause = "<li> " + resname1 + " (" + FormatUtil.getString("am.webclient.managermail.availability") + ")<br>";
/*     */           }
/* 379 */           else if (ticksla.equalsIgnoreCase("FAIL"))
/*     */           {
/* 381 */             rcause = "<li> " + resname1 + " (" + FormatUtil.getString("am.webclient.managermail.troubleticket") + ")<br>";
/*     */           }
/*     */           else
/*     */           {
/* 385 */             rcause = "PASS";
/*     */           }
/*     */           
/* 388 */           if (!ht1.containsKey(slid1))
/*     */           {
/* 390 */             Hashtable htable = new Hashtable();
/* 391 */             htable.put(resid1, rcause);
/* 392 */             ht1.put(slid1, htable);
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 397 */             Hashtable h = (Hashtable)ht1.get(slid1);
/* 398 */             h.put(resid1, rcause);
/* 399 */             ht1.put(slid1, h);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 404 */       if (ht1.size() > 0)
/*     */       {
/* 406 */         Enumeration en = ht1.keys();
/* 407 */         String sid1 = "";
/* 408 */         String rc1 = "";
/* 409 */         String root1 = "";
/* 410 */         String rid1 = "";
/* 411 */         String defaultInfo1 = "";
/* 412 */         String returnVal; while (en.hasMoreElements())
/*     */         {
/* 414 */           sid1 = (String)en.nextElement();
/* 415 */           Hashtable hbl = (Hashtable)ht1.get(sid1);
/*     */           
/* 417 */           Enumeration et = hbl.keys();
/* 418 */           while (et.hasMoreElements())
/*     */           {
/* 420 */             rid1 = (String)et.nextElement();
/* 421 */             root1 = (String)hbl.get(rid1);
/* 422 */             if (!root1.equalsIgnoreCase("PASS"))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 428 */               rc1 = rc1 + root1;
/*     */             }
/*     */           }
/*     */           
/* 432 */           String info = FormatUtil.replaceStringBySpecifiedString(rc1, "<br>", "\n", 0);
/* 433 */           Properties pro = (Properties)hb1.get(sid1);
/* 434 */           String sname1 = pro.getProperty("sname");
/* 435 */           String availop1 = pro.getProperty("availop");
/* 436 */           String ttop1 = pro.getProperty("tickop");
/* 437 */           String mailing1 = pro.getProperty("mailoption");
/* 438 */           defaultInfo1 = "\n *************************************************\n ";
/* 439 */           if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*     */           {
/* 441 */             defaultInfo1 = defaultInfo1 + FormatUtil.getString("am.webclient.managermail.firstline.text", new String[] { host, port, OEMUtil.getOEMString("rebrand.product.name") });
/*     */           }
/*     */           else
/*     */           {
/* 445 */             defaultInfo1 = defaultInfo1 + FormatUtil.getString("am.webclient.managermail.firstline.text", new String[] { host, port, OEMUtil.getOEMString("product.name") });
/*     */           }
/* 447 */           defaultInfo1 = defaultInfo1 + "\n  " + FormatUtil.getString("am.webclient.managermail.secondline.text") + "\n\n";
/* 448 */           String sourc = "  " + FormatUtil.getString("am.webclient.managermail.slaname.text") + "             :" + sname1 + "\n";
/* 449 */           String attrib = "   " + FormatUtil.getString("am.webclient.managermail.availability.text") + "         :" + availop1 + "\n";
/* 450 */           defaultInfo1 = defaultInfo1 + sourc + attrib + "  " + FormatUtil.getString("am.webclient.managermail.troubleticketvolume.text") + ":" + ttop1 + "\n";
/* 451 */           defaultInfo1 = defaultInfo1 + "  " + FormatUtil.getString("am.webclient.managermail.rootcause.text") + "           : " + info + " \n";
/*     */           
/* 453 */           ArrayList data1 = cs.getDataFromAction(sid1);
/*     */           
/* 455 */           if (data1.size() != 0)
/*     */           {
/* 457 */             for (int t = 0; t < data1.size(); t++)
/*     */             {
/* 459 */               actid1 = (String)data1.get(0);
/* 460 */               fromadd1 = (String)data1.get(1);
/* 461 */               toadd1 = (String)data1.get(2);
/* 462 */               subject1 = (String)data1.get(3);
/* 463 */               mess1 = (String)data1.get(4);
/*     */             }
/*     */             
/* 466 */             int actionID1 = Integer.parseInt(actid1);
/* 467 */             SmtpMailer mailer1 = new SmtpMailer(fromadd1, toadd1, "", subject1);
/* 468 */             Hashtable additionalInfo = new Hashtable();
/*     */             
/* 470 */             if (!rc1.equalsIgnoreCase("PASS"))
/*     */             {
/*     */ 
/* 473 */               fline = FormatUtil.findReplace(htmlmess, "~topheading~", FormatUtil.getString("am.webclient.managermail.heading.text"));
/* 474 */               resource = FormatUtil.findReplace(fline, "~source~", sname1);
/* 475 */               attr = FormatUtil.findReplace(resource, "~attribute~", availop1);
/* 476 */               messa = FormatUtil.findReplace(attr, "~message~", ttop1);
/* 477 */               messa = FormatUtil.findReplace(messa, "~heading~", FormatUtil.getString("am.webclient.managermail.bsm.servermessage.text"));
/* 478 */               root = FormatUtil.findReplace(messa, "~rootcause~", rc1);
/* 479 */               String uinfo = FormatUtil.findReplace(root, "~userinfo~", FormatUtil.getString("am.webclient.managermail.bsm.message.text"));
/* 480 */               user = FormatUtil.findReplace(uinfo, "~message~", mess1);
/* 481 */               addinfo = FormatUtil.findReplace(user, "~addinfo~", FormatUtil.getString("am.webclient.managermail.additionalinfo.text"));
/* 482 */               repby = FormatUtil.findReplace(addinfo, "~reportby~", FormatUtil.getString("am.webclient.managermail.reportby.text"));
/* 483 */               hostFilled = FormatUtil.findReplace(repby, "~host~", host);
/* 484 */               if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*     */               {
/* 486 */                 nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("rebrand.product.name"));
/*     */               }
/*     */               else
/*     */               {
/* 490 */                 nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("product.name"));
/*     */               }
/*     */               
/* 493 */               portFilled = FormatUtil.findReplace(nameFilled, "~port~", port);
/*     */               
/* 495 */               additionalInfo.put("message", defaultInfo1);
/* 496 */               additionalInfo.put("htmlmessage", portFilled);
/* 497 */               additionalInfo.put("shortmessage", "");
/* 498 */               additionalInfo.put("replacevalues", new HashMap());
/*     */             }
/* 500 */             String getquery = "SELECT MAILOPTION FROM AM_SLA_DISABLEMAIL where SLAID='" + sid1 + "'";
/* 501 */             rs = AMConnectionPool.executeQueryStmt(getquery);
/* 502 */             int sendmailonce = 0;
/* 503 */             if (rs.next())
/*     */             {
/* 505 */               sendmailonce = rs.getInt("MAILOPTION");
/*     */             }
/*     */             else
/*     */             {
/* 509 */               String insertquery = "INSERT INTO AM_SLA_DISABLEMAIL(SLAID,MAILOPTION) VALUES('" + sid1 + "',0)";
/* 510 */               AMConnectionPool.executeUpdateStmt(insertquery);
/*     */             }
/*     */             
/* 513 */             if (mailing1.equalsIgnoreCase("true"))
/*     */             {
/* 515 */               if (!rc1.equalsIgnoreCase("PASS"))
/*     */               {
/*     */ 
/* 518 */                 if (sendmailonce == 0)
/*     */                 {
/*     */ 
/* 521 */                   String returnVal = mailer1.sendMessage(mess1, "Success", true, portFilled, 0, null, null);
/*     */                   
/* 523 */                   String mailquery = "Update AM_SLA_DISABLEMAIL SET MAILOPTION=1 WHERE SLAID='" + sid1 + "'";
/* 524 */                   AMConnectionPool.executeUpdateStmt(mailquery);
/*     */                 }
/*     */               }
/*     */               else
/*     */               {
/* 529 */                 String dmailquery = "Update AM_SLA_DISABLEMAIL SET MAILOPTION=0 WHERE SLAID='" + sid1 + "'";
/* 530 */                 AMConnectionPool.executeUpdateStmt(dmailquery);
/*     */               }
/*     */               
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 537 */               returnVal = mailer1.sendMessage(mess1, "Success", true, portFilled, 0, null, null);
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 547 */       e.printStackTrace();
/*     */     }
/*     */     
/* 550 */     Date date = new Date();
/* 551 */     date = new Date(date.getTime() + 86400000L);
/*     */     
/* 553 */     Scheduler.getScheduler("main").scheduleTask(this, date);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\BusyJob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */