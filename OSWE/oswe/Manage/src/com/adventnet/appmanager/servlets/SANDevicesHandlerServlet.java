/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.extprod.OPMEventTypeParser;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.ExtProdUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URLEncoder;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class SANDevicesHandlerServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   public void service(HttpServletRequest rq, HttpServletResponse rs)
/*     */     throws ServletException
/*     */   {
/*  39 */     FileInputStream in = null;
/*     */     try {
/*  41 */       String requestType = rq.getParameter("requestType");
/*  42 */       if ((requestType != null) && (requestType.equals("getAPMData")))
/*     */       {
/*  44 */         String custName = rq.getParameter("custName") != null ? rq.getParameter("custName") : "";
/*  45 */         String method = rq.getParameter("method");
/*  46 */         getAPMData(rs, method, custName);
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
/*     */       }
/*  58 */       else if ((requestType != null) && (requestType.equals("getConsolePort")))
/*     */       {
/*  60 */         String iamServer = System.getProperty("iam.server");
/*  61 */         int index = iamServer.indexOf(":");
/*  62 */         String consolePort = iamServer.substring(index + 1);
/*  63 */         PrintWriter out = rs.getWriter();
/*  64 */         out.println(consolePort);
/*     */       }
/*     */       else
/*     */       {
/*  68 */         InputStream inStream = rq.getInputStream();
/*  69 */         Element root = ExtProdUtil.getRootElement(inStream);
/*  70 */         AMLog.debug("SAN - Root Element - " + root);
/*  71 */         if (root != null)
/*     */         {
/*  73 */           NodeList listOfDevices = root.getElementsByTagName("Device");
/*  74 */           Element el = (Element)listOfDevices.item(0);
/*  75 */           String prodId = ExtProdUtil.getProdId(ExtProdUtil.opstor);
/*  76 */           String eventType = el.getAttribute("EVENT_TYPE");
/*  77 */           String resName = el.getAttribute("name");
/*  78 */           String category = el.getAttribute("category");
/*  79 */           String devType = el.getAttribute("type");
/*  80 */           if ((el.getAttribute("message") != null) && (el.getAttribute("message").contains("'")))
/*     */           {
/*  82 */             el.setAttribute("message", ExtProdUtil.removeChar(el.getAttribute("message"), '\''));
/*     */           }
/*  84 */           if (((category == null) || (category.equals(""))) && (resName != null))
/*     */           {
/*  86 */             category = ExtProdUtil.getCategory(resName);
/*     */           }
/*  88 */           if ((eventType.equals("DEVICE_ADDED")) || (eventType.equals("DEVICE_UPDATED")))
/*     */           {
/*     */             try
/*     */             {
/*  92 */               boolean isDeviceExist = ExtProdUtil.isExtDeviceExist(resName, Integer.parseInt(prodId));
/*  93 */               if (!isDeviceExist)
/*     */               {
/*  95 */                 String siteID = null;
/*  96 */                 if (EnterpriseUtil.isIt360MSPEdition())
/*     */                 {
/*  98 */                   siteID = el.getAttribute("siteID");
/*     */                 }
/* 100 */                 addDevice(rq, root, resName, prodId, category, siteID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 116 */                 AMLog.debug("updateDevice case");
/* 117 */                 updateDevice(rq, el, resName, devType, category, prodId);
/*     */               }
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 122 */               e.printStackTrace();
/*     */             }
/*     */           }
/* 125 */           else if (eventType.equals("DEVICE_DELETED"))
/*     */           {
/*     */             try {
/* 128 */               AMLog.debug("deleteDevice case");
/* 129 */               deleteDevice(resName, Integer.parseInt(prodId));
/*     */             } catch (Exception e) {
/* 131 */               e.printStackTrace();
/*     */             }
/*     */           }
/* 134 */           else if (eventType.equals("ALERT_UPDATED"))
/*     */           {
/*     */             try
/*     */             {
/* 138 */               Properties alarmProps = ExtProdUtil.getAlarmDetails(el);
/* 139 */               AMLog.debug("alarmProps = " + alarmProps);
/* 140 */               if ((alarmProps != null) && (!alarmProps.isEmpty()))
/*     */               {
/* 142 */                 String entity = alarmProps.getProperty("entity");
/* 143 */                 if ((entity != null) && (!entity.equals("")))
/*     */                 {
/* 145 */                   ExtProdUtil.updateAlertInfo(el, entity);
/*     */                 }
/*     */               }
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 151 */               e.printStackTrace();
/*     */             }
/*     */           }
/* 154 */           else if ((eventType.equals("ALERT_PICKED")) || (eventType.equals("ALERT_UNPICKED")))
/*     */           {
/*     */             try
/*     */             {
/* 158 */               ExtProdUtil.handleAlertPickupAndUnpickup(el);
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 162 */               e.printStackTrace();
/*     */             }
/*     */           }
/* 165 */           else if ((eventType.equals("ALERT_CLEARED")) || (eventType.contains("ALERT_DELETED")))
/*     */           {
/*     */             try
/*     */             {
/* 169 */               String alarmGenInterface = ExtProdUtil.getAlarmDetails(el).getProperty("alarmGenDev");
/* 170 */               if ((alarmGenInterface != null) && (!alarmGenInterface.equals("")))
/*     */               {
/* 172 */                 OPMEventTypeParser.clearAlertForDevice(alarmGenInterface, devType, prodId);
/*     */               }
/* 174 */               ExtProdUtil.updateAlertInfo(el, resName);
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 178 */               e.printStackTrace();
/*     */             }
/*     */           }
/* 181 */           else if (eventType.equals("SITE_MAPPING"))
/*     */           {
/* 183 */             String siteId = null;
/* 184 */             if (EnterpriseUtil.isIt360MSPEdition())
/*     */             {
/* 186 */               siteId = el.getAttribute("siteID");
/*     */             }
/* 188 */             String resourceId = ExtProdUtil.getResId(resName, FormatUtil.getModifiedType(devType, ExtProdUtil.opstor));
/* 189 */             if ((siteId != null) && (resourceId != null) && (!siteId.trim().equals("")) && (!resourceId.trim().equals("")))
/*     */             {
/* 191 */               ExtProdUtil.insertParentChild(siteId, resourceId);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       return;
/*     */     }
/*     */     catch (Exception ex) {
/* 199 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 204 */         in.close();
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */   }
/*     */   
/*     */   public static void addDevice(HttpServletRequest rq, Element root, String resName, String prodId, String category)
/*     */   {
/* 212 */     addDevice(rq, root, resName, prodId, category, null);
/*     */   }
/*     */   
/*     */   public static void addDevice(HttpServletRequest rq, Element root, String resName, String prodId, String category, String monitorGroup)
/*     */   {
/*     */     try
/*     */     {
/* 219 */       HashMap hmap = new HashMap();
/* 220 */       hmap = ExtProdUtil.insertIntoExtDevicesTables(root, Integer.parseInt(prodId), new HashMap(), new HashMap(), ExtProdUtil.opstor, true);
/* 221 */       AMLog.debug("insertIntoExtDevicesTables method - hmap ::: " + hmap);
/* 222 */       AMLog.debug("Setting DEVICE_ADDED action for the thread local");
/* 223 */       ExtProdUtil.addAndAssociateDevices(resName, monitorGroup, rq, prodId);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 227 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean deleteDevice(String resName, int prodId)
/*     */   {
/*     */     try
/*     */     {
/* 235 */       ExtProdUtil.deleteDevice(resName, prodId);
/* 236 */       return true;
/*     */     }
/*     */     catch (Exception e) {
/* 239 */       e.printStackTrace(); }
/* 240 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void updateDevice(HttpServletRequest rq, Element root, String resName, String devType, String category, String prodId)
/*     */   {
/*     */     try
/*     */     {
/* 248 */       String idToAppend = "";
/* 249 */       if (!EnterpriseUtil.isAdminServer())
/*     */       {
/* 251 */         int masId = EnterpriseUtil.getManagedServerIndex();
/* 252 */         idToAppend = String.valueOf(masId);
/*     */       }
/* 254 */       devType = "OpStor-" + devType + "-" + idToAppend;
/* 255 */       String resId = ExtProdUtil.getResId(resName, devType);
/* 256 */       int healthAttriId = -1;
/* 257 */       Hashtable attributeTable = ExtProdUtil.addAMAttributesTable(devType);
/* 258 */       if ((attributeTable.get("ALREADYADDED") != null) && (((Boolean)attributeTable.get("ALREADYADDED")).booleanValue()))
/*     */       {
/* 260 */         healthAttriId = ((Integer)attributeTable.get("HEALTHATTRID")).intValue();
/*     */       }
/*     */       else
/*     */       {
/* 264 */         healthAttriId = ((Integer)attributeTable.get("HEALTHATTRID")).intValue();
/* 265 */         ExtProdUtil.handleNewTypeAddition(rq, devType, "OpStor-" + category + "-" + idToAppend, ExtProdUtil.opstor, attributeTable);
/*     */       }
/* 267 */       ExtProdUtil.doDeviceUpdation(root, Integer.parseInt(resId), healthAttriId, Integer.parseInt(prodId), ExtProdUtil.opstor);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 271 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void getAPMData(HttpServletResponse response, String method, String custName)
/*     */   {
/* 278 */     if (method.equalsIgnoreCase("getOrganizations"))
/*     */     {
/*     */       try
/*     */       {
/* 282 */         getOrganizations(response);
/*     */       }
/*     */       catch (Exception e) {
/* 285 */         e.printStackTrace();
/*     */       }
/*     */       
/* 288 */     } else if (method.equalsIgnoreCase("getAssociatedSiteIDs"))
/*     */     {
/*     */       try
/*     */       {
/* 292 */         getAssociatedSiteIDs(response, custName);
/*     */       }
/*     */       catch (Exception e) {
/* 295 */         e.printStackTrace();
/*     */       }
/*     */       
/* 298 */     } else if (method.equalsIgnoreCase("getAllSiteProps"))
/*     */     {
/*     */       try
/*     */       {
/* 302 */         getAllSiteProps(response, custName);
/*     */       }
/*     */       catch (Exception e) {
/* 305 */         e.printStackTrace();
/*     */       }
/*     */       
/* 308 */     } else if (method.equalsIgnoreCase("getAssociatedSites"))
/*     */     {
/*     */       try
/*     */       {
/* 312 */         getAssociatedSites(response, custName);
/*     */       }
/*     */       catch (Exception e) {
/* 315 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void getOrganizations(HttpServletResponse response) throws Exception {
/* 321 */     ResultSet rs = null;
/* 322 */     String result = null;
/* 323 */     StringBuffer custNames = new StringBuffer();
/* 324 */     PrintWriter out = response.getWriter();
/* 325 */     String query = "select CUSTOMERNAME from CustomerINFO";
/*     */     try
/*     */     {
/* 328 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 329 */       while (rs.next())
/*     */       {
/* 331 */         result = rs.getString(1).trim();
/* 332 */         if (result != null)
/*     */         {
/* 334 */           custNames.append(result);
/* 335 */           custNames.append(',');
/*     */         }
/*     */       }
/* 338 */       String custString = custNames.toString();
/* 339 */       custString = custString.substring(0, custString.length() - 1);
/* 340 */       String encodedString = URLEncoder.encode(custString, "UTF-8");
/* 341 */       out.println(encodedString);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 349 */       if (rs != null)
/*     */       {
/*     */         try
/*     */         {
/* 353 */           rs.close();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 357 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 362 */       out.flush();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 345 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 349 */       if (rs != null)
/*     */       {
/*     */         try
/*     */         {
/* 353 */           rs.close();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 357 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void getAssociatedSiteIDs(HttpServletResponse response, String organizationName)
/*     */     throws Exception
/*     */   {
/* 370 */     ResultSet rs = null;
/* 371 */     String result = null;
/* 372 */     StringBuffer sites = new StringBuffer();
/* 373 */     PrintWriter out = response.getWriter();
/* 374 */     String query = "select SITEID from SiteINFO where CUSTOMERID in(Select CustomerID from CustomerINFO where CustomerName='" + organizationName + "')";
/*     */     try
/*     */     {
/* 377 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 378 */       while (rs.next())
/*     */       {
/* 380 */         result = rs.getString(1).trim();
/* 381 */         if (result != null)
/*     */         {
/* 383 */           sites.append(result);
/* 384 */           sites.append(',');
/*     */         }
/*     */       }
/* 387 */       String siteString = sites.toString();
/* 388 */       siteString = siteString.substring(0, siteString.length() - 1);
/* 389 */       out.println(siteString);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 397 */       if (rs != null)
/*     */       {
/*     */         try
/*     */         {
/* 401 */           rs.close();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 405 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 410 */       out.flush();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 393 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 397 */       if (rs != null)
/*     */       {
/*     */         try
/*     */         {
/* 401 */           rs.close();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 405 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void getAllSiteProps(HttpServletResponse response, String customerName)
/*     */     throws Exception
/*     */   {
/* 415 */     ResultSet rs = null;
/* 416 */     Properties siteProps = new Properties();
/*     */     
/*     */     try
/*     */     {
/* 420 */       String sitePropsQuery = "SELECT SITE.SITEID,SITE.SITENAME,SITE.CUSTOMERID,SITE.CONTACTPERSON,SITE.CONTACTEMAIL FROM CUSTOMERINFO AS CUST,SITEINFO AS SITE WHERE CUST.CUSTOMERID=SITE.CUSTOMERID AND CUST.CUSTOMERNAME='" + customerName + "'";
/* 421 */       rs = AMConnectionPool.executeQueryStmt(sitePropsQuery);
/* 422 */       while (rs.next())
/*     */       {
/* 424 */         String siteId = rs.getString("SITEID");
/* 425 */         String siteName = rs.getString("SITENAME");
/* 426 */         if ((siteId != null) && (siteName != null)) {
/* 427 */           siteProps.setProperty(siteId, siteName);
/*     */         }
/*     */       }
/* 430 */       siteProps.store(response.getOutputStream(), ""); return;
/*     */     }
/*     */     catch (Exception exx)
/*     */     {
/* 434 */       exx.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 440 */         if (rs != null) {
/* 441 */           rs.close();
/*     */         }
/*     */       } catch (Exception ex) {
/* 444 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void getAssociatedSites(HttpServletResponse response, String organizationName)
/*     */     throws Exception
/*     */   {
/* 452 */     ResultSet rs = null;
/* 453 */     String result = null;
/* 454 */     StringBuffer sites = new StringBuffer();
/* 455 */     PrintWriter out = response.getWriter();
/* 456 */     String query = "select SITENAME from SiteINFO where CUSTOMERID in(Select CustomerID from CustomerINFO where CustomerName='" + organizationName + "')";
/*     */     try
/*     */     {
/* 459 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 460 */       while (rs.next())
/*     */       {
/* 462 */         result = rs.getString(1).trim();
/* 463 */         if (result != null)
/*     */         {
/* 465 */           sites.append(result);
/* 466 */           sites.append(',');
/*     */         }
/*     */       }
/* 469 */       String siteString = sites.toString();
/* 470 */       siteString = siteString.substring(0, siteString.length() - 1);
/* 471 */       out.println(siteString);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 479 */       if (rs != null)
/*     */       {
/*     */         try
/*     */         {
/* 483 */           rs.close();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 487 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 492 */       out.flush();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 475 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 479 */       if (rs != null)
/*     */       {
/*     */         try
/*     */         {
/* 483 */           rs.close();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 487 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\SANDevicesHandlerServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */