/*    */ package com.adventnet.appmanager.servlets;
/*    */ 
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.adventnet.appmanager.server.framework.extprod.OPMEventQueue;
/*    */ import com.adventnet.appmanager.server.framework.extprod.ProdIntegUpdate;
/*    */ import com.adventnet.appmanager.util.ExtConnectorUtil;
/*    */ import com.adventnet.appmanager.util.ExtProdUtil;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.w3c.dom.Element;
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
/*    */ public class OPMDevicesHandlerServlet
/*    */   extends HttpServlet
/*    */ {
/* 53 */   public static boolean firstTime = false;
/*    */   
/*    */   public void service(HttpServletRequest rq, HttpServletResponse rs) throws ServletException
/*    */   {
/*    */     try {
/* 58 */       String opmProductStatus = rq.getParameter("productStatus");
/* 59 */       if (opmProductStatus != null)
/*    */       {
/* 61 */         String prevStatus = (String)ExtConnectorUtil.prodNameVsStatus.get("OpManager");
/* 62 */         if ((prevStatus != null) && (prevStatus.equals(ExtConnectorUtil.prodStatusDown)) && (opmProductStatus.equals(ExtConnectorUtil.prodStatusUp)))
/*    */         {
/*    */ 
/* 65 */           AMLog.audit("Fetching devices for OPM up status");
/* 66 */           ProdIntegUpdate integ = new ProdIntegUpdate();
/* 67 */           integ.fullUpdate("OpManager");
/*    */         }
/* 69 */         ExtConnectorUtil.prodNameVsStatus.put("OpManager", opmProductStatus);
/* 70 */         return;
/*    */       }
/*    */       
/* 73 */       if ((ExtConnectorUtil.isPushEnabled) && (ExtConnectorUtil.enableOPMSync))
/*    */       {
/* 75 */         InputStream inStream = rq.getInputStream();
/* 76 */         String xmlString = ExtProdUtil.sanitizeXmlChars(inStream);
/* 77 */         Element root = ExtProdUtil.getRootElement(xmlString);
/* 78 */         if (root != null)
/*    */         {
/* 80 */           OPMEventQueue.getInstance().addObjToQueue(root);
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception ex)
/*    */     {
/* 86 */       AMLog.warning("", ex);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\OPMDevicesHandlerServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */