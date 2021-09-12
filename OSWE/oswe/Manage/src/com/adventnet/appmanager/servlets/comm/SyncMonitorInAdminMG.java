/*     */ package com.adventnet.appmanager.servlets.comm;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.htmlparser.util.Translate;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ public class SyncMonitorInAdminMG
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public void init(ServletConfig config)
/*     */     throws ServletException
/*     */   {
/*     */     try
/*     */     {
/*  28 */       super.init(config);
/*     */     }
/*     */     catch (ServletException e) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  37 */     doGet(request, response);
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  42 */     String operation = request.getParameter("operation");
/*  43 */     if (operation != null)
/*     */     {
/*  45 */       String resourceList = request.getParameter("resourceList");
/*  46 */       boolean checkDependency = Boolean.parseBoolean(request.getParameter("checkDependency"));
/*  47 */       String mgResourceID = request.getParameter("mgResourceID");
/*  48 */       boolean status = false;
/*  49 */       AMLog.debug("SyncMonitorInAdminMG::: resourceList:" + resourceList + "\tcheckDependency:" + checkDependency + "\tmgResourceID:" + mgResourceID + "\toperation:" + operation);
/*  50 */       if ((resourceList != null) && (!resourceList.equals("")))
/*     */       {
/*  52 */         resourceList = Translate.decode(resourceList);
/*     */         try
/*     */         {
/*  55 */           if (Integer.parseInt(operation) == 1)
/*     */           {
/*  57 */             JSONArray jsArr = new JSONArray(resourceList);
/*  58 */             DBUtil.addMonitorsAssociatedToAdminMGForDependency(mgResourceID, jsArr, checkDependency);
/*     */           }
/*  60 */           else if (Integer.parseInt(operation) == 2)
/*     */           {
/*  62 */             JSONArray jsArr = new JSONArray(resourceList);
/*  63 */             DBUtil.deleteMonitorsAssociatedToAdminMGForDependency(mgResourceID, jsArr);
/*     */           }
/*  65 */           if (Integer.parseInt(operation) == 3)
/*     */           {
/*  67 */             JSONObject jsObj = new JSONObject(resourceList);
/*  68 */             Iterator itr = jsObj.keys();
/*  69 */             while (itr.hasNext())
/*     */             {
/*  71 */               String monitorGroupID = (String)itr.next();
/*  72 */               JSONArray jsArr = jsObj.getJSONArray(monitorGroupID);
/*  73 */               DBUtil.deleteMonitorsAssociatedToAdminMGForDependency(monitorGroupID, null);
/*  74 */               DBUtil.addMonitorsAssociatedToAdminMGForDependency(monitorGroupID, jsArr, true);
/*     */             }
/*     */           }
/*  77 */           status = true;
/*  78 */           sendResponse(response, String.valueOf(status));
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*  82 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendResponse(HttpServletResponse response, Object object) throws IOException
/*     */   {
/*  90 */     ObjectOutputStream oos = null;
/*     */     try
/*     */     {
/*  93 */       oos = new ObjectOutputStream(response.getOutputStream());
/*  94 */       oos.writeObject(object);
/*  95 */       oos.flush();
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/*  99 */       throw ex;
/*     */     }
/*     */     finally
/*     */     {
/* 103 */       oos.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\SyncMonitorInAdminMG.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */