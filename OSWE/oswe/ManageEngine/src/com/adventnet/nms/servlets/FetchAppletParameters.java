/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.nms.fe.sas.NmsSAServerFE;
/*     */ import com.adventnet.nms.startnms.NmsMainFE;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import com.adventnet.nms.util.PureServerUtilsFE;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FetchAppletParameters
/*     */   extends HttpServlet
/*     */ {
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  30 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */   }
/*     */   
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException
/*     */   {
/*  35 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/*  36 */     String str1 = paramHttpServletRequest.getParameter("userName");
/*  37 */     if (str1 == null) {
/*  38 */       str1 = paramHttpServletRequest.getHeader("userName");
/*     */     }
/*     */     
/*  41 */     if ((!NmsUtil.webNMSModulesStarted) || (!NmsMainFE.isStarted))
/*     */     {
/*  43 */       localPrintWriter.println("Sorry");
/*  44 */       localPrintWriter.flush();
/*  45 */       localPrintWriter.close();
/*  46 */       return;
/*     */     }
/*     */     
/*  49 */     localPrintWriter.println("OK");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  54 */     String str2 = paramHttpServletRequest.getSession().getId();
/*  55 */     localPrintWriter.println("<PARAM name=jsessionid value=" + str2 + ">");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  61 */     localPrintWriter.println("<PARAM name=NMS_FE_SECONDARY_PORT value=\"" + PureServerUtilsFE.nms_fe_secondary_port + "\">");
/*  62 */     localPrintWriter.println("<PARAM name=NMS_FE_SECONDARY_PORT_DIR value=\"" + PureServerUtilsFE.nms_fe_secondary_port_dir + "\">");
/*     */     
/*  64 */     if (!NmsSAServerFE.isSAServerRunning())
/*     */     {
/*  66 */       localPrintWriter.println("<PARAM name=TRANSPORT_PROVIDER value=\"com.adventnet.nms.client.sas.SASClientTransporter\">");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  75 */     int i = NmsUtil.getRegistryPort();
/*     */     
/*  77 */     if (i == -1)
/*     */     {
/*  79 */       i = 1099;
/*     */     }
/*     */     
/*  82 */     localPrintWriter.println("<PARAM name=RMI_REG_PORT value=\"" + i + "\">");
/*     */     
/*     */ 
/*     */ 
/*  86 */     Calendar localCalendar = Calendar.getInstance();
/*  87 */     localCalendar.set(1998, 0, 1, 0, 0, 0);
/*  88 */     Date localDate = localCalendar.getTime();
/*  89 */     localPrintWriter.println("<PARAM name=jan1_98 value=" + localDate.getTime() + ">");
/*  90 */     String str3 = String.valueOf(TimeZone.getDefault().getRawOffset());
/*  91 */     localPrintWriter.println("<PARAM name=TIME_ZONE value=" + str3 + ">");
/*     */     
/*     */ 
/*  94 */     String str4 = PureServerUtilsFE.getClientTransportClassName();
/*  95 */     localPrintWriter.println("<PARAM name=CLIENT_CLASS_NAME value=" + str4 + ">");
/*     */     
/*  97 */     localPrintWriter.println("<PARAM name=KEEPALIVE_WINDOW_SIZE value=" + String.valueOf(NmsUtil.keepalive_window_size) + ">");
/*     */     
/*  99 */     localPrintWriter.flush();
/*     */     
/* 101 */     localPrintWriter.close();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\FetchAppletParameters.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */