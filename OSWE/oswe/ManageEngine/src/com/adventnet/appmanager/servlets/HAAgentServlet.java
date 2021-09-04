/*    */ package com.adventnet.appmanager.servlets;
/*    */ 
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.adventnet.appmanager.util.HAAgentUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.sql.SQLException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.json.JSONException;
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
/*    */ public class HAAgentServlet
/*    */   extends HttpServlet
/*    */ {
/*    */   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws ServletException, IOException
/*    */   {
/* 37 */     resp.setContentType("text,xml; charset=UTF-8");
/* 38 */     PrintWriter out = resp.getWriter();
/* 39 */     String requester = req.getHeader("IsAPMAgent");
/* 40 */     StringBuffer toReturn = new StringBuffer();
/* 41 */     resp.setCharacterEncoding("UTF-8");
/* 42 */     HAAgentUtil agentUtil = new HAAgentUtil();
/* 43 */     if ("true".equals(requester)) {
/* 44 */       String command = req.getParameter("command");
/* 45 */       if ("Register".equals(command)) {
/* 46 */         String host = req.getParameter("host");
/* 47 */         String port = req.getParameter("port");
/* 48 */         String id = req.getParameter("id");
/*    */         try {
/* 50 */           String response = agentUtil.registerAgent(id, host, port);
/* 51 */           AMLog.debug("Response : " + response);
/* 52 */           toReturn.append(response);
/*    */         } catch (JSONException e) {
/* 54 */           e.printStackTrace();
/*    */         } catch (SQLException e) {
/* 56 */           if (e.getMessage().contains("duplicate")) {
/* 57 */             toReturn.append("Already registered with another agent");
/*    */           } else {
/* 59 */             e.printStackTrace();
/*    */           }
/*    */         }
/*    */       } else {
/*    */         try {
/* 64 */           toReturn.append(agentUtil.getPerformanceData());
/*    */         }
/*    */         catch (JSONException e) {
/* 67 */           e.printStackTrace();
/*    */         }
/*    */       }
/*    */     } else {
/* 71 */       resp.setStatus(401);
/* 72 */       throw new ServletException("Status Code : 401Authorization Failed");
/*    */     }
/* 74 */     AMLog.debug(toReturn);
/* 75 */     out.print(toReturn.toString());
/* 76 */     out.close();
/*    */   }
/*    */   
/*    */ 
/*    */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws ServletException, IOException
/*    */   {
/* 83 */     doPost(req, resp);
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\HAAgentServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */