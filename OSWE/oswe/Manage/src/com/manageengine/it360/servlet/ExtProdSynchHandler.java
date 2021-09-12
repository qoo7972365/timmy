/*    */ package com.manageengine.it360.servlet;
/*    */ 
/*    */ import com.adventnet.appmanager.fault.FaultUtil;
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.adventnet.appmanager.server.framework.extprod.ProdIntegUpdate;
/*    */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*    */ import com.adventnet.appmanager.util.MyThreadLocal;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
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
/*    */ public class ExtProdSynchHandler
/*    */   extends HttpServlet
/*    */ {
/*    */   public void doPost(HttpServletRequest req, HttpServletResponse res)
/*    */     throws ServletException, IOException
/*    */   {
/* 30 */     doGet(req, res);
/*    */   }
/*    */   
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*    */   {
/* 35 */     String prodName = request.getParameter("prodName");
/* 36 */     PrintWriter out = response.getWriter();
/* 37 */     AMLog.debug("ExtProdSynchHandler **** Entering ExtProdSynchHandler doGet method");
/* 38 */     out.println("ExtProdSynchHandler **** Entering ExtProdSynchHandler doGet method");
/* 39 */     if (!EnterpriseUtil.isAdminServer())
/*    */     {
/*    */ 
/* 42 */       MyThreadLocal.setSource("FROM_EXT_SYNCH_SERVLET");
/* 43 */       ProdIntegUpdate integ = new ProdIntegUpdate();
/* 44 */       if (prodName != null)
/*    */       {
/* 46 */         integ.fullUpdate(prodName);
/*    */       }
/*    */       else
/*    */       {
/* 50 */         integ.fullUpdate();
/* 51 */         prodName = "";
/*    */       }
/* 53 */       AMLog.debug("ExtProdSynchHandler **** Full Update - done " + prodName);
/* 54 */       out.println("ExtProdSynchHandler **** Full Update - done " + prodName);
/* 55 */       FaultUtil.applyRCAForExtProdDeviceAssociatedMonitorGroups();
/* 56 */       AMLog.debug("ExtProdSynchHandler **** RCA Update - done " + prodName);
/* 57 */       out.println("ExtProdSynchHandler **** RCA Update - done " + prodName);
/*    */       
/*    */ 
/* 60 */       MyThreadLocal.setSource("");
/*    */     }
/*    */     else
/*    */     {
/* 64 */       AMLog.debug("ExtProdSynchHandler **** This servlet cannot be invoked in the Central server.");
/* 65 */       out.println("ExtProdSynchHandler **** This servlet cannot be invoked in the Central server.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\it360\servlet\ExtProdSynchHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */