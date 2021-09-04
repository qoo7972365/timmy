/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.nms.poll.PollAPI;
/*     */ import com.adventnet.nms.poll.PolledData;
/*     */ import com.adventnet.nms.util.GenericUtility;
/*     */ import com.adventnet.nms.util.NmsClientUtil;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.swing.JOptionPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TL1CurrentPollServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   public String getServletInfo()
/*     */   {
/*  34 */     return "TL1 Current Poll : This servlet returns the parameter needed for TL1 Graphs";
/*     */   }
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
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  50 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */   }
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
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  66 */     Hashtable localHashtable = new Hashtable();
/*  67 */     PolledData localPolledData = null;
/*     */     
/*  69 */     String str1 = null;
/*     */     
/*     */     try
/*     */     {
/*  73 */       Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
/*  74 */       while (localEnumeration.hasMoreElements())
/*     */       {
/*  76 */         str3 = (String)localEnumeration.nextElement();
/*  77 */         str4 = paramHttpServletRequest.getParameter(str3);
/*     */         
/*  79 */         if (str4 == null)
/*     */         {
/*  81 */           JOptionPane.showMessageDialog(NmsClientUtil.applet, "Invalid key value for the servlet.", "Error", 0);
/*  82 */           return;
/*     */         }
/*  84 */         localHashtable.put(str3, str4);
/*     */       }
/*     */       
/*  87 */       PollAPI localPollAPI = GenericUtility.getPollAPI();
/*  88 */       if (localPollAPI == null)
/*     */       {
/*  90 */         System.err.println("TL1 Current Graph : PollAPI is NULL.");
/*  91 */         return;
/*     */       }
/*  93 */       str1 = localHashtable.get("name").toString().trim() + "\t" + localHashtable.get("agent").toString().trim() + "\t" + localHashtable.get("oid").toString().trim();
/*     */       
/*     */ 
/*  96 */       localPolledData = localPollAPI.getPolledData(str1);
/*  97 */       if (localPolledData == null)
/*     */       {
/*  99 */         System.err.println("TL1 Current Graph : PolledData is NULL.");
/* 100 */         return;
/*     */       }
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/* 105 */       System.err.println("TL1 Current Poll : Exception while reading the \"UserProperty\" of the current poll data.");
/* 106 */       return;
/*     */     }
/*     */     
/* 109 */     String str2 = getIndexList(localPolledData);
/* 110 */     String str3 = getComponentId(localPolledData);
/* 111 */     String str4 = str2 + "##" + str3;
/*     */     
/*     */     try
/*     */     {
/* 115 */       DataOutputStream localDataOutputStream = new DataOutputStream(paramHttpServletResponse.getOutputStream());
/* 116 */       localDataOutputStream.writeUTF(str4);
/* 117 */       localDataOutputStream.flush();
/* 118 */       localDataOutputStream.close();
/*     */     }
/*     */     catch (Exception localException2)
/*     */     {
/* 122 */       System.err.println("TL1 Current Poll : Exception while reading the \"UserProperty\" of the current poll data.");
/* 123 */       localException2.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private String getComponentId(PolledData paramPolledData)
/*     */   {
/* 129 */     String str = null;
/*     */     try
/*     */     {
/* 132 */       str = paramPolledData.getUserProperty("componentId");
/* 133 */       return str.trim();
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 137 */       System.err.println("TL1 Current Graph : Component Id @ Servlet Exception is : " + str);
/* 138 */       str = "noresponse";
/* 139 */       str = str.trim(); }
/* 140 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */   private String getIndexList(PolledData paramPolledData)
/*     */   {
/* 146 */     String str = null;
/*     */     try
/*     */     {
/* 149 */       str = paramPolledData.getUserProperty("response");
/* 150 */       return str.trim();
/*     */ 
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 155 */       System.err.println("TL1 Current Graph : Index List @ Servlet is : " + str);
/* 156 */       str = "noresponse"; }
/* 157 */     return str.trim();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\TL1CurrentPollServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */