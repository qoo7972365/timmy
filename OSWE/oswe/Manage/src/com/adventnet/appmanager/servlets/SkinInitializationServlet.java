/*    */ package com.adventnet.appmanager.servlets;
/*    */ 
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.adventnet.appmanager.util.OEMUtil;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.util.Properties;
/*    */ import java.util.StringTokenizer;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SkinInitializationServlet
/*    */   extends HttpServlet
/*    */ {
/* 22 */   private ServletContext servletContext = null;
/* 23 */   private ServletConfig config = null;
/*    */   
/*    */   public void init(ServletConfig sConfig) throws ServletException {
/* 26 */     super.init(sConfig);
/* 27 */     this.servletContext = sConfig.getServletContext();
/* 28 */     String availableskins = this.servletContext.getInitParameter("availableSkins");
/*    */     
/* 30 */     if ((OEMUtil.isOEM()) && (OEMUtil.getOEMString("am.personalize.remove").equalsIgnoreCase("true")))
/*    */     {
/*    */ 
/* 33 */       this.servletContext.setAttribute("defaultSkin", OEMUtil.getOEMString("skin.color"));
/* 34 */       availableskins = availableskins + "," + OEMUtil.getOEMString("skin.color");
/*    */     }
/*    */     
/*    */ 
/* 38 */     StringTokenizer skins = new StringTokenizer(availableskins, ",");
/* 39 */     for (;;) { if (skins.hasMoreTokens())
/*    */       {
/* 41 */         String skin = skins.nextToken();
/* 42 */         if ((!skin.equals("Maroon")) && (!skin.equals("Gray")))
/*    */         {
/*    */ 
/*    */ 
/* 46 */           Properties skinprops = new Properties();
/* 47 */           FileInputStream fis = null;
/*    */           try
/*    */           {
/* 50 */             File skinProps = new File("images" + File.separator + skin + File.separator + "skin.properties");
/* 51 */             if (skinProps.exists())
/*    */             {
/* 53 */               fis = new FileInputStream(skinProps);
/* 54 */               skinprops.load(fis);
/* 55 */               this.servletContext.setAttribute(skin, skinprops);
/*    */             }
/*    */             else
/*    */             {
/* 59 */               AMLog.debug("WARNING::: File : " + skinProps + " not available");
/*    */             }
/*    */             
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             try
/*    */             {
/* 69 */               fis.close();
/*    */             }
/*    */             catch (NullPointerException ne) {}catch (Exception ee) {}
/*    */           }
/*    */           catch (Exception exp)
/*    */           {
/* 63 */             exp.printStackTrace();
/*    */           }
/*    */           finally
/*    */           {
/*    */             try
/*    */             {
/* 69 */               fis.close();
/*    */             }
/*    */             catch (NullPointerException ne) {}catch (Exception ee) {}
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\SkinInitializationServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */