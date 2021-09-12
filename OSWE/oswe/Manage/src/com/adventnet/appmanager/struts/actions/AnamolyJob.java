/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.fault.AMThresholdApplier;
/*     */ import com.adventnet.appmanager.fault.SmtpMailer;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.util.CustomExpressionUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.appmanager.util.ReportDataUtilities;
/*     */ import com.adventnet.management.scheduler.Scheduler;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class AnamolyJob
/*     */   implements Runnable
/*     */ {
/*  49 */   public boolean isrunning = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void scheduledJob()
/*     */   {
/*     */     try
/*     */     {
/*  59 */       this.isrunning = true;
/*  60 */       String host = InetAddress.getLocalHost().getHostName();
/*  61 */       String port = System.getProperty("webserver.port");
/*  62 */       ReportDataUtilities.getAllAnomalyDetailsFromTable();
/*  63 */       String htmlmess = ReportDataUtilities.getHTMLMailTemplate();
/*  64 */       AMThresholdApplier app = new AMThresholdApplier();
/*     */       
/*  66 */       Map predicteddata = ReportDataUtilities.calculateAndAlert(null);
/*     */       
/*  68 */       System.out.println("*****the predicted Data in Anomaly Job is===>" + predicteddata);
/*  69 */       Collection c = predicteddata.keySet();
/*  70 */       Iterator itr = c.iterator();
/*     */       
/*     */ 
/*  73 */       while (itr.hasNext()) {
/*  74 */         String anomalyid = itr.next().toString();
/*     */         
/*  76 */         List anomalyValues = ReportDataUtilities.getAnomalyDataDetailsFromMap(anomalyid);
/*  77 */         String anomalyName = anomalyValues.get(1).toString();
/*  78 */         int week = Integer.parseInt(anomalyValues.get(3).toString());
/*  79 */         int month = Integer.parseInt(anomalyValues.get(4).toString());
/*  80 */         int year = Integer.parseInt(anomalyValues.get(5).toString());
/*  81 */         long[] timeList = ReportDataUtilities.returnStartTimeForMonth(week, month, year);
/*  82 */         long t1 = timeList[0];
/*  83 */         long t2 = timeList[1];
/*  84 */         long[] timestoquery = ReportDataUtilities.getTimeForComparisonValues(t1, t2);
/*  85 */         String typecalc = anomalyValues.get(6).toString();
/*  86 */         if ("1".equals(typecalc)) {
/*  87 */           typecalc = "%";
/*     */         } else {
/*  89 */           typecalc = " ";
/*     */         }
/*     */         
/*  92 */         String upperpercent = anomalyValues.get(7).toString();
/*  93 */         String lowerpercent = anomalyValues.get(8).toString();
/*  94 */         String upperpercentalarm = anomalyValues.get(9).toString();
/*  95 */         String lowerpercentalarm = anomalyValues.get(10).toString();
/*     */         
/*  97 */         String actid = anomalyValues.get(11).toString();
/*  98 */         String ftype = anomalyValues.get(15).toString();
/*     */         
/* 100 */         String upperexp = anomalyValues.get(13).toString();
/*     */         
/* 102 */         String lowerexp = anomalyValues.get(14).toString();
/*     */         
/*     */ 
/* 105 */         String fromadd = "";
/* 106 */         String toadd = "";
/* 107 */         SimpleDateFormat sdf = new SimpleDateFormat("MMM dd ,yyyy HH");
/* 108 */         Date resultdate = new Date(timestoquery[1]);
/*     */         
/* 110 */         String starttime = sdf.format(resultdate);
/* 111 */         List a1 = (List)predicteddata.get(anomalyid);
/* 112 */         Map messagemap = new HashMap();
/*     */         
/* 114 */         String tab = "<table width='100%'  cellpadding='0' cellspacing='0' >";
/* 115 */         tab = tab + "<tr><td width='25%' height='20'  align='center'> <b>" + FormatUtil.getString("am.webclient.camscreen.attributegraphs.resourcename.text") + "</b></td>";
/* 116 */         tab = tab + "<td width='25%' align='center'>  <b>" + FormatUtil.getString("am.webclient.wmi.attributename.text") + "</b></td>";
/* 117 */         if ("0".equals(ftype)) {
/* 118 */           tab = tab + "<td width='10%' align='center' > <b>" + FormatUtil.getString("am.webclient.anomaly.actualexpression.text") + "</b></td>";
/* 119 */           tab = tab + "<td width='10%' align='center' colspan='2'> <b>" + FormatUtil.getString("am.webclient.anomaly.expressionvalues.text") + "</b></td>";
/*     */         }
/*     */         else {
/* 122 */           tab = tab + "<td width='10%' align='center' > <b>" + FormatUtil.getString("am.webclient.anomalydetails.baselinevalue.text") + "</b></td>";
/* 123 */           tab = tab + "<td width='15%' align='center'> <b>" + FormatUtil.getString("am.webclient.anomalydetails.baselinerange.text") + "</b></td>";
/* 124 */           tab = tab + "<td width='10%' align='center'> <b>" + FormatUtil.getString("am.webclient.anomalydetails.thishour.text") + "</b></td>";
/*     */         }
/*     */         
/* 127 */         tab = tab + "<td width='15%' align='center'> <b>" + FormatUtil.getString("am.webclient.anomalydetails.status.text") + "</b></td>";
/*     */         
/* 129 */         tab = tab + "</tr>";
/* 130 */         for (int i = 0; i < a1.size(); i++) {
/* 131 */           List a2 = (List)a1.get(i);
/*     */           
/* 133 */           String resourcename = a2.get(1).toString();
/* 134 */           String attributename = a2.get(2).toString();
/* 135 */           String lasthourvalue = a2.get(3).toString();
/* 136 */           String thishourvalue = a2.get(4).toString();
/* 137 */           float upperrange = 0.0F;
/* 138 */           float lowerrange = 0.0F;
/* 139 */           float LastHVal = 0.0F;
/* 140 */           float ThisHVal = 0.0F;
/* 141 */           String exp = "";
/* 142 */           String actualexp = "";
/* 143 */           if ("1".equals(ftype)) {
/* 144 */             upperrange = Float.parseFloat(lasthourvalue) * Float.parseFloat(upperpercent) / 100.0F + Float.parseFloat(lasthourvalue);
/* 145 */             lowerrange = Float.parseFloat(lasthourvalue) - Float.parseFloat(lasthourvalue) * Float.parseFloat(lowerpercentalarm) / 100.0F;
/*     */             
/* 147 */             upperrange = Math.round(upperrange);
/* 148 */             lowerrange = Math.round(lowerrange);
/* 149 */             LastHVal = Math.round(Float.parseFloat(lasthourvalue));
/* 150 */             ThisHVal = Math.round(Float.parseFloat(thishourvalue));
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 155 */           String message = a2.get(8).toString();
/* 156 */           String key = a2.get(9).toString();
/*     */           
/*     */ 
/* 159 */           String[] t3 = key.split("_");
/*     */           
/* 161 */           String resourceid = t3[0];
/* 162 */           String attributeid = t3[2];
/*     */           
/* 164 */           String newkey = resourceid + "_" + attributeid;
/* 165 */           String m2 = "-";
/*     */           
/* 167 */           String[] temp = message.split("_");
/* 168 */           String alarm = temp[0];
/* 169 */           String typealert = temp[1];
/*     */           
/* 171 */           if ("1".equals(alarm)) {
/* 172 */             m2 = "Critical";
/* 173 */           } else if ("4".equals(alarm)) {
/* 174 */             m2 = "Warning";
/*     */           } else {
/* 176 */             m2 = "No Alarm";
/*     */           }
/*     */           
/* 179 */           if ("0".equals(ftype))
/*     */           {
/* 181 */             if ("upperAlert".equalsIgnoreCase(typealert))
/*     */             {
/* 183 */               exp = a2.get(3).toString();
/* 184 */               actualexp = upperexp;
/* 185 */             } else if ("lowerAlert".equalsIgnoreCase(typealert))
/*     */             {
/* 187 */               exp = a2.get(4).toString();
/* 188 */               actualexp = lowerexp;
/*     */             } else {
/* 190 */               exp = "";
/* 191 */               actualexp = "";
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 199 */           tab = tab + "<tr>";
/* 200 */           tab = tab + "<td  width='25%' align='center' height='20'>" + resourcename + "</td>";
/* 201 */           tab = tab + "<td width='25%' align='center'> " + attributename + "</td>";
/* 202 */           if ("1".equals(ftype)) {
/* 203 */             tab = tab + "<td width='10%' align='center'> " + LastHVal + "</td>";
/* 204 */             tab = tab + "<td width='15%' align='center'> " + lowerrange + " to " + upperrange + "</td>";
/* 205 */             tab = tab + "<td  width='10%' align='center'> " + ThisHVal + "</td>";
/*     */           } else {
/* 207 */             tab = tab + "<td  width='10%' align='center > " + actualexp + "</td>";
/* 208 */             tab = tab + "<td  width='10%' align='center colspan='2'> " + exp + "</td>";
/*     */           }
/* 210 */           tab = tab + "<td  width='15%' align='center'> " + m2 + "</td>";
/* 211 */           tab = tab + "</tr>";
/*     */           
/* 213 */           if ("0".equals(actid)) {
/* 214 */             String sev = m2;
/* 215 */             if ("No Alarm".equals(sev)) {
/* 216 */               sev = "Clear";
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 221 */             String RCAMessage = FormatUtil.getString("am.fault.rca.healthorattributemessage.start", new String[] { "Health", sev });
/* 222 */             if ("1".equals(ftype)) {
/* 223 */               if ("upperAlert".equalsIgnoreCase(typealert)) {
/* 224 */                 RCAMessage = FormatUtil.getString("am.webclient.anomaly.rcaupper.message", new String[] { FormatUtil.getString(attributename), String.valueOf(ThisHVal), String.valueOf(upperrange) });
/* 225 */               } else if ("lowerAlert".equalsIgnoreCase(typealert)) {
/* 226 */                 RCAMessage = FormatUtil.getString("am.webclient.anomaly.rcalower.message", new String[] { FormatUtil.getString(attributename), String.valueOf(ThisHVal), String.valueOf(lowerrange) });
/*     */               } else {
/* 228 */                 RCAMessage = FormatUtil.getString("am.webclient.anomaly.rcaclear.message");
/*     */               }
/*     */             }
/* 231 */             else if (!"0".equals(alarm))
/*     */             {
/* 233 */               String[] expvals = CustomExpressionUtil.getExecutedExpression(exp);
/*     */               
/* 235 */               String expval1 = expvals[0];
/* 236 */               String expval2 = expvals[1];
/* 237 */               String sep = expvals[2];
/* 238 */               RCAMessage = FormatUtil.getString("am.webclient.anomaly.rca.expression.message", new String[] { FormatUtil.getString(attributename), expval1, expval2, sep });
/*     */             } else {
/* 240 */               RCAMessage = FormatUtil.getString("am.webclient.anomaly.rcaclear.message");
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 246 */             if (!messagemap.containsKey(newkey))
/*     */             {
/* 248 */               List ax = new ArrayList();
/* 249 */               ax.add(resourceid);
/* 250 */               ax.add(attributeid);
/* 251 */               ax.add(alarm);
/* 252 */               ax.add(RCAMessage);
/* 253 */               messagemap.put(newkey, ax);
/*     */ 
/*     */             }
/* 256 */             else if (messagemap.get(newkey) != null) {
/* 257 */               List mx = (List)messagemap.get(newkey);
/* 258 */               List ax2 = new ArrayList();
/* 259 */               String mes = mx.get(3).toString();
/* 260 */               String alarm2 = mx.get(2).toString();
/* 261 */               ax2.add(resourceid);
/* 262 */               ax2.add(attributeid);
/* 263 */               ax2.add(alarm);
/* 264 */               if (alarm2.equals(alarm)) {
/* 265 */                 mes = mes + RCAMessage;
/* 266 */                 ax2.add(mes);
/* 267 */                 messagemap.put(newkey, ax2);
/*     */               }
/* 269 */               if (("1".equals(alarm)) && (!"1".equals(alarm2))) {
/* 270 */                 ax2.add(RCAMessage);
/* 271 */                 messagemap.put(newkey, ax2);
/*     */               }
/*     */             }
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
/*     */ 
/*     */ 
/* 289 */             toadd = "No";
/*     */           } else {
/* 291 */             String[] address = ReportDataUtilities.getActionAddress(actid);
/* 292 */             if (address == null) {
/* 293 */               System.out.println("No Address to send Anomaly Mail so returning -----");
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 298 */               fromadd = address[0];
/* 299 */               toadd = address[1];
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 306 */         tab = tab + "</table>";
/*     */         
/* 308 */         Collection cx = messagemap.keySet();
/* 309 */         Iterator ix = cx.iterator();
/* 310 */         while (ix.hasNext()) {
/* 311 */           String k4 = (String)ix.next();
/* 312 */           List rx = (List)messagemap.get(k4);
/*     */           
/* 314 */           String rid = rx.get(0).toString();
/* 315 */           String aid = rx.get(1).toString();
/* 316 */           String alarmid = rx.get(2).toString();
/* 317 */           String mess = rx.get(3).toString();
/*     */           
/* 319 */           if ("0".equals(alarmid)) {
/* 320 */             alarmid = "5";
/*     */           }
/* 322 */           app.applyThresholdForAnomaly(Integer.parseInt(rid), Integer.parseInt(aid), Long.parseLong(alarmid), System.currentTimeMillis(), mess);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 327 */         if (!"No".equalsIgnoreCase(toadd)) {
/* 328 */           String fline = FormatUtil.findReplace(htmlmess, "~topheading~", FormatUtil.getString("am.webclient.managermail.heading.text"));
/* 329 */           String resource = FormatUtil.findReplace(fline, "~source~", anomalyName);
/* 330 */           String attr = FormatUtil.findReplace(resource, "~attribute~", starttime + ":00");
/* 331 */           String messa = FormatUtil.findReplace(attr, "~UPR~", upperpercent + " " + typecalc);
/* 332 */           messa = FormatUtil.findReplace(messa, "~LPR~", lowerpercent + " " + typecalc);
/* 333 */           String heading = FormatUtil.findReplace(messa, "~heading~", "Anomaly Detection");
/* 334 */           String uinfo = FormatUtil.findReplace(heading, "~rootcause~", tab);
/* 335 */           String repby = FormatUtil.findReplace(uinfo, "~reportby~", FormatUtil.getString("am.webclient.managermail.reportby.text"));
/* 336 */           String hostFilled = FormatUtil.findReplace(repby, "~host~", host);
/*     */           
/*     */ 
/*     */ 
/* 340 */           String nameFilled = null;
/* 341 */           if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*     */           {
/* 343 */             nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("rebrand.product.name"));
/*     */           }
/*     */           else
/*     */           {
/* 347 */             nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("product.name"));
/*     */           }
/*     */           
/*     */ 
/* 351 */           String portFilled = FormatUtil.findReplace(nameFilled, "~port~", port);
/*     */           try {
/* 353 */             SmtpMailer mailer = new SmtpMailer(fromadd, toadd, "", "Anomaly Detection");
/*     */             
/* 355 */             returnVal = mailer.sendMessage("Anomaly Detection", "Success", true, portFilled, 0, null, null);
/*     */           }
/*     */           catch (Exception ex) {
/*     */             String returnVal;
/* 359 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/* 363 */       this.isrunning = false;
/*     */     }
/*     */     catch (Exception ex) {
/* 366 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/* 372 */     if (!FreeEditionDetails.getFreeEditionDetails().isAnomalyAllowed()) {
/* 373 */       this.isrunning = true;
/* 374 */       System.out.println("*******THERE IS NO ANOMALY ADD-ON IN THE LICENSE FILE********");
/* 375 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 381 */       scheduledJob();
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/*     */       Date date;
/* 386 */       e.printStackTrace();
/*     */     } finally { Date date;
/* 388 */       if (!this.isrunning) {
/* 389 */         Date date = new Date();
/* 390 */         date = new Date(ReportDataUtilities.returnNextHour());
/*     */         
/* 392 */         Scheduler.getScheduler("main").scheduleTask(this, date);
/*     */       }
/*     */       else {
/* 395 */         System.out.println("*******THE ANOMALY THREAD IS ALREADY RUNNING/THERE IS NO ANOMALY ADD-ON IN THE LICENSE FILE********");
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\AnamolyJob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */