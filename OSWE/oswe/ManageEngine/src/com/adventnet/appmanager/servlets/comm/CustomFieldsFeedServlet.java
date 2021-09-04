/*    */ package com.adventnet.appmanager.servlets.comm;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.net.ConnectException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class CustomFieldsFeedServlet extends javax.servlet.http.HttpServlet
/*    */ {
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException
/*    */   {
/* 16 */     doGet(request, response);
/*    */   }
/*    */   
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws javax.servlet.ServletException, IOException
/*    */   {
/*    */     try
/*    */     {
/* 25 */       fileName = request.getParameter("customFieldObject");
/* 26 */       if ((fileName != null) && (!fileName.equals(""))) {
/* 27 */         response.setHeader("Content-Type", "text/xml");
/* 28 */         ObjectOutputStream out = null;
/* 29 */         FileInputStream fis = null;
/* 30 */         ObjectInputStream ois = null;
/*    */         try
/*    */         {
/* 33 */           fis = new FileInputStream(fileName);
/* 34 */           ois = new ObjectInputStream(fis);
/* 35 */           java.util.HashMap today = (java.util.HashMap)ois.readObject();
/* 36 */           ois.close();
/* 37 */           out = new ObjectOutputStream(response.getOutputStream());
/* 38 */           out.writeObject(today);
/*    */           
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           try
/*    */           {
/* 48 */             if (out != null) {
/* 49 */               out.flush();
/* 50 */               out.close();
/*    */             }
/* 52 */             if (fis != null) {
/* 53 */               fis.close();
/*    */             }
/* 55 */             if (ois != null) {
/* 56 */               ois.close();
/*    */             }
/*    */           }
/*    */           catch (ConnectException ce) {
/* 60 */             ce.printStackTrace();
/*    */           }
/*    */           catch (IOException ex)
/*    */           {
/* 64 */             ex.printStackTrace();
/*    */           }
/*    */           
/*    */ 
/*    */ 
/*    */           try
/*    */           {
/* 71 */             File file = new File(fileName);
/* 72 */             if (file.exists()) {
/* 73 */               file.delete();
/*    */             }
/*    */           } catch (Exception ex) {
/* 76 */             ex.printStackTrace();
/*    */           }
/*    */         }
/*    */         catch (IOException ex)
/*    */         {
/* 41 */           ex.printStackTrace();
/*    */         } catch (Exception e) {
/* 43 */           e.printStackTrace();
/*    */         }
/*    */         finally
/*    */         {
/*    */           try {
/* 48 */             if (out != null) {
/* 49 */               out.flush();
/* 50 */               out.close();
/*    */             }
/* 52 */             if (fis != null) {
/* 53 */               fis.close();
/*    */             }
/* 55 */             if (ois != null) {
/* 56 */               ois.close();
/*    */             }
/*    */           }
/*    */           catch (ConnectException ce) {
/* 60 */             ce.printStackTrace();
/*    */           }
/*    */           catch (IOException ex)
/*    */           {
/* 64 */             ex.printStackTrace();
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */       String fileName;
/*    */       
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 81 */       com.adventnet.appmanager.logging.AMLog.debug("Problem while synching the custom fields to Probe server::" + e.getMessage());
/* 82 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\CustomFieldsFeedServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */