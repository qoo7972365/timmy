/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.nms.poll.PollAPI;
/*     */ import com.adventnet.nms.util.GenericUtility;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PollerStatus
/*     */   extends AuthenticationServlet
/*     */ {
/*     */   public String getServletInfo()
/*     */   {
/*  24 */     return "This servlet returns status of the pollers";
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException
/*     */   {
/*  29 */     doPost(paramHttpServletRequest, paramHttpServletResponse);
/*     */   }
/*     */   
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException
/*     */   {
/*  34 */     paramHttpServletResponse.setStatus(200);
/*  35 */     paramHttpServletResponse.setContentType("text/html");
/*     */     
/*     */ 
/*     */ 
/*  39 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/*  40 */     PollAPI localPollAPI = GenericUtility.getPollAPI();
/*  41 */     if (localPollAPI == null)
/*     */     {
/*  43 */       errorPage("PollerStatus Error getting PollAPI: ", paramHttpServletRequest, paramHttpServletResponse);
/*  44 */       return;
/*     */     }
/*  46 */     Vector localVector = null;
/*  47 */     localVector = localPollAPI.getActivePollers();
/*  48 */     if (localVector == null)
/*     */     {
/*  50 */       errorPage("Apparently No Pollers are running", paramHttpServletRequest, paramHttpServletResponse);
/*  51 */       return;
/*     */     }
/*     */     
/*     */ 
/*  55 */     String str1 = paramHttpServletRequest.getParameter("userName");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  64 */     if (str1 == null) {
/*  65 */       return;
/*     */     }
/*  67 */     localPrintWriter.println("<HTML><HEAD>");
/*  68 */     localPrintWriter.println("<TITLE>");
/*  69 */     localPrintWriter.println(NmsUtil.GetString("AdventNet"));
/*  70 */     localPrintWriter.println(" ");
/*  71 */     localPrintWriter.print(NmsUtil.GetString("WebNMS"));
/*  72 */     localPrintWriter.println(" PollerStatus</TITLE>");
/*  73 */     localPrintWriter.println("<meta http-equiv=\"pragma\" content=\"no-cache\">");
/*  74 */     localPrintWriter.println("</HEAD><BODY bgcolor=\"#ADD8E6\">");
/*  75 */     localPrintWriter.println("<table border=0><tr><td>");
/*  76 */     localPrintWriter.println("<center><TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0  bgcolor=\"#add8e6\">");
/*     */     
/*  78 */     localPrintWriter.println("<tr><td align=\"center\" bgcolor=\"#003399\"><font color=\"#ffffff\" size=+1>Table showing Active Pollers </font></td></tr>");
/*     */     
/*     */ 
/*  81 */     localPrintWriter.println("<tr><td><center>");
/*  82 */     if (localVector.size() != 0)
/*     */     {
/*     */ 
/*  85 */       String[] arrayOfString1 = (String[])localVector.elementAt(0);
/*  86 */       String[] arrayOfString2 = (String[])localVector.elementAt(1);
/*  87 */       localPrintWriter.println("<TABLE  BORDER=2 CELLPADDING=0 CELLSPACING=0 bgcolor=\"add8e6\">");
/*  88 */       localPrintWriter.println("<tr bgcolor=\"#4169e1\" ><td><font color=\"#ffff00\">POLLER NAME.</td><td><font color=\"#ffff00\">STATUS.</td><td><font color=\"#ffff00\">HOSTNAME.</td></tr>");
/*  89 */       for (int i = 0; i < arrayOfString1.length; i++)
/*     */       {
/*  91 */         String str2 = arrayOfString1[i];
/*  92 */         String str3 = arrayOfString2[i];
/*  93 */         String str4 = "true";
/*  94 */         localPrintWriter.println("<tr><td>" + str2 + "</td><td>" + str4 + "</td><td>" + str3 + "</td></tr>");
/*     */       }
/*  96 */       localPrintWriter.println("</table></center>");
/*  97 */       localPrintWriter.println("<br><font color=\"#ADD8E6\"><P>&nbsp;</P><P>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</font></center><br>");
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 102 */       localPrintWriter.println("<h3><center>No Active Pollers Available </center><br></h3>");
/* 103 */       localPrintWriter.println("<font color=\"#ADD8E6\">a<br>a<br>a<br>a<br>a<br>a<br>a<br></font>");
/* 104 */       localPrintWriter.println("<br><font color=\"#ADD8E6\"><P>&nbsp;</P><P>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</font></center><br>");
/*     */     }
/* 106 */     localPrintWriter.println("<br><br></center>");
/* 107 */     localPrintWriter.println("</td></tr></table>");
/* 108 */     localPrintWriter.println("</td><td>");
/* 109 */     localPrintWriter.println("</td></tr></table>");
/* 110 */     localPrintWriter.println("</BODY>");
/* 111 */     localPrintWriter.println("</HTML>");
/* 112 */     localPrintWriter.flush();
/* 113 */     localPrintWriter.close();
/*     */   }
/*     */   
/*     */   void errorPage(String paramString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws IOException
/*     */   {
/* 118 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/* 119 */     localPrintWriter.println(paramString);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\PollerStatus.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */