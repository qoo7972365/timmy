/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*      */ import com.adventnet.appmanager.server.framework.MapViewServerUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.utils.client.MapViewUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.security.Principal;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.apache.struts.upload.FormFile;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class MapViewActions
/*      */   extends DispatchAction
/*      */ {
/*   70 */   private String[] resourceTypes = new String[0];
/*   71 */   private String type = "1";
/*   72 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showMapViewEditor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*   87 */       String type = request.getParameter("type");
/*   88 */       if ((type != null) && (!type.trim().equals("")))
/*      */       {
/*   90 */         if (type.equals("new"))
/*      */         {
/*   92 */           request.setAttribute("type", "new");
/*      */         }
/*      */         else
/*      */         {
/*   96 */           String mapViewName = request.getParameter("mapViewName");
/*   97 */           AMLog.debug("The mapViewName is " + mapViewName);
/*      */           try
/*      */           {
/*  100 */             JSONObject jsonObj = MapViewUtil.getMapViewObjects(mapViewName);
/*  101 */             request.setAttribute("jsonObj", jsonObj);
/*  102 */             request.setAttribute("type", "modify");
/*  103 */             response.setHeader("X-UA-Compatible", "IE=7,IE=9");
/*      */           }
/*      */           catch (Exception ex) {
/*  106 */             AMLog.debug(ex.getMessage());
/*  107 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  114 */       ex.printStackTrace();
/*      */     }
/*  116 */     String widgetId = request.getParameter("widgetid");
/*  117 */     request.setAttribute("widgetid", widgetId);
/*  118 */     request.setAttribute("resourceTypes", getResourceTypes());
/*      */     
/*  120 */     return new ActionForward("/jsp/MapViewInclude.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward saveMapView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  125 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/*  126 */     response.setContentType("text/plain; charset=UTF-8");
/*  127 */     PrintWriter out = response.getWriter();
/*      */     
/*      */     try
/*      */     {
/*  131 */       String data = request.getParameter("data");
/*  132 */       AMLog.debug("The data in saveMapView is " + data);
/*  133 */       data = data.replaceAll("&quot;", "\"");
/*      */       
/*      */ 
/*      */ 
/*  137 */       MapViewUtil.persistMapView(data, username, mapping, form, request, response);
/*  138 */       out.println(FormatUtil.getString("am.webclient.jsp.mapvieweditor.savemvsuccess"));
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  142 */       out.println(ex.getMessage());
/*  143 */       ex.printStackTrace();
/*      */     }
/*  145 */     out.flush();
/*  146 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward deleteMapView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  151 */     response.setContentType("text/plain");
/*  152 */     PrintWriter out = response.getWriter();
/*      */     
/*  154 */     HAIDManagerAction haidMa = new HAIDManagerAction();
/*  155 */     String bsgId = "";
/*  156 */     String widgetid = "";
/*      */     try
/*      */     {
/*  159 */       String mapViewName = request.getParameter("mapViewName");
/*  160 */       String deleteBSG = request.getParameter("bgToBeDeleted");
/*  161 */       widgetid = request.getParameter("widgetid");
/*  162 */       AMLog.debug("Deleting the mapview " + mapViewName);
/*      */       try {
/*  164 */         if ((widgetid != null) && (!widgetid.equals(""))) {
/*  165 */           String query1 = "update AM_MYPAGE_WIDGETS set WIDGETURL='' where WIDGETID=" + widgetid;
/*  166 */           AMConnectionPool.executeUpdateStmt(query1);
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  171 */         ex.printStackTrace();
/*      */       }
/*  173 */       bsgId = MapViewUtil.getBSGIdForMapName(mapViewName);
/*  174 */       if (("true".equalsIgnoreCase(deleteBSG)) || (MapViewUtil.isSubgroup(bsgId)))
/*      */       {
/*  176 */         MapViewServerUtil.deleteMapView(mapViewName);
/*  177 */         request.setAttribute("bsgId", bsgId);
/*  178 */         request.setAttribute("isMapView", "true");
/*  179 */         haidMa.delete(mapping, form, request, response);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  185 */         ArrayList<String> subMapList = new ArrayList();
/*  186 */         subMapList.add(mapViewName);
/*  187 */         ArrayList<String> finalList = new ArrayList();
/*  188 */         finalList.add(mapViewName);
/*  189 */         ArrayList<String> duplicateList = new ArrayList();
/*  190 */         duplicateList.add(mapViewName);
/*  191 */         ArrayList<String> mapList = MapViewUtil.getSubMaps(subMapList, finalList, duplicateList);
/*  192 */         for (String mapName : finalList) {
/*  193 */           MapViewServerUtil.deleteMapView(mapName);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  219 */       out.println(FormatUtil.getString("am.webclient.jsp.mapvieweditor.deletemvsuccess"));
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  223 */       out.println(ex.getMessage());
/*  224 */       ex.printStackTrace();
/*      */     }
/*  226 */     out.flush();
/*  227 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getListOfMapViews(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  232 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/*  233 */     response.setContentType("text/plain");
/*  234 */     PrintWriter out = response.getWriter();
/*      */     
/*      */     try
/*      */     {
/*  238 */       String listOfMapNames = MapViewUtil.getListOfMapViews(username, request);
/*  239 */       AMLog.debug("The Map views present are " + listOfMapNames);
/*  240 */       if (("".equals(listOfMapNames)) || (listOfMapNames == null))
/*      */       {
/*  242 */         listOfMapNames = "-1";
/*      */       }
/*  244 */       out.println(listOfMapNames);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  248 */       out.println(ex.getMessage());
/*  249 */       ex.printStackTrace();
/*      */     }
/*  251 */     out.flush();
/*  252 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getListOfMapViewsForSC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  257 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/*  258 */     response.setContentType("text/plain");
/*  259 */     PrintWriter out = response.getWriter();
/*      */     
/*      */     try
/*      */     {
/*  263 */       String mapViewName = request.getParameter("mapViewName");
/*  264 */       String listOfMapNames = MapViewUtil.getListOfMapViewsForSC(mapViewName, username, request);
/*  265 */       if (("".equals(listOfMapNames)) || (listOfMapNames == null))
/*      */       {
/*  267 */         listOfMapNames = "-1";
/*      */       }
/*  269 */       out.println(listOfMapNames);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  273 */       out.println(ex.getMessage());
/*  274 */       ex.printStackTrace();
/*      */     }
/*  276 */     out.flush();
/*  277 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward isMapPresent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  282 */     response.setContentType("text/plain");
/*  283 */     PrintWriter out = response.getWriter();
/*  284 */     String mapfile = request.getParameter("filePath");
/*  285 */     File dir = new File(mapfile);
/*  286 */     boolean isMapPresent = false;
/*  287 */     if (dir.exists())
/*      */     {
/*  289 */       isMapPresent = true;
/*      */     }
/*  291 */     out.println(isMapPresent);
/*  292 */     out.flush();
/*  293 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward showMap(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  300 */     String mapViewName = request.getParameter("mapViewName");
/*  301 */     AMLog.debug("The mapViewName is " + mapViewName);
/*      */     try
/*      */     {
/*  304 */       JSONObject jsonObj = MapViewUtil.getMapViewObjects(mapViewName);
/*  305 */       request.setAttribute("jsonObj", jsonObj);
/*  306 */       response.setHeader("X-UA-Compatible", "IE=7,IE=9");
/*  307 */       AMLog.debug("JSON Obj sent to client " + jsonObj.toString());
/*      */     } catch (Exception ex) {
/*  309 */       AMLog.debug(ex.getMessage());
/*  310 */       ex.printStackTrace();
/*      */     }
/*  312 */     return new ActionForward("/jsp/MapViewInclude.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward getSnapshotLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  317 */     response.setContentType("text/plain");
/*  318 */     PrintWriter out = response.getWriter();
/*      */     
/*      */     try
/*      */     {
/*  322 */       String deviceName = request.getParameter("deviceName");
/*  323 */       AMLog.debug("The deviceName is " + deviceName);
/*  324 */       String snapshotLink = MapViewUtil.getSnapshotLink(deviceName);
/*  325 */       out.println(snapshotLink);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  329 */       out.println(ex.getMessage());
/*  330 */       ex.printStackTrace();
/*      */     }
/*  332 */     out.flush();
/*  333 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getIFSnapshotLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  338 */     response.setContentType("text/plain");
/*  339 */     PrintWriter out = response.getWriter();
/*      */     
/*      */     try
/*      */     {
/*  343 */       String ifName = request.getParameter("ifName");
/*  344 */       AMLog.debug("The Interface Name is " + ifName);
/*  345 */       String snapshotLink = MapViewUtil.getIFSnapshotLink(ifName);
/*  346 */       out.println(snapshotLink);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  350 */       out.println(ex.getMessage());
/*  351 */       ex.printStackTrace();
/*      */     }
/*  353 */     out.flush();
/*  354 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getListOfInterfaces(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  359 */     response.setContentType("text/plain");
/*  360 */     PrintWriter out = response.getWriter();
/*      */     
/*      */     try
/*      */     {
/*  364 */       String deviceName = request.getParameter("deviceName");
/*  365 */       AMLog.debug("The deviceName is " + deviceName);
/*  366 */       String listOfInterfaces = MapViewUtil.getListOfInterfaces(deviceName);
/*  367 */       out.println(listOfInterfaces);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  371 */       out.println(ex.getMessage());
/*  372 */       ex.printStackTrace();
/*      */     }
/*  374 */     out.flush();
/*  375 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getAllDevices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  380 */     response.setContentType("text/plain; charset=UTF-8");
/*  381 */     PrintWriter out = response.getWriter();
/*      */     try
/*      */     {
/*  384 */       String category = request.getParameter("category");
/*  385 */       String mapName = request.getParameter("mapName");
/*  386 */       AMLog.debug("Getting the devices of category " + category);
/*  387 */       ArrayList<String> allDevices = MapViewUtil.getAllDevices(category, null, mapName);
/*  388 */       if (allDevices.size() == 0) {
/*  389 */         out.println("");
/*  390 */         return null;
/*      */       }
/*  392 */       String deviceList = allDevices.toString();
/*  393 */       deviceList = deviceList.substring(1, deviceList.length() - 1);
/*  394 */       out.println(deviceList);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  398 */       out.println(ex.getMessage());
/*  399 */       ex.printStackTrace();
/*      */     }
/*  401 */     out.flush();
/*  402 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getSitesListForCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  407 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/*  408 */     String customerName = request.getParameter("customerName");
/*  409 */     Properties custProps = null;
/*  410 */     String custId = null;
/*      */     try
/*      */     {
/*  413 */       PrintWriter out = response.getWriter();
/*  414 */       custProps = EnterpriseUtil.getAllCustomerProps(username);
/*  415 */       if ((custProps != null) && (custProps.size() > 0))
/*      */       {
/*  417 */         Enumeration custEnum = custProps.keys();
/*  418 */         while (custEnum.hasMoreElements())
/*      */         {
/*  420 */           String tempCustId = custEnum.nextElement().toString();
/*  421 */           String custName = custProps.getProperty(tempCustId);
/*  422 */           if (custName.equalsIgnoreCase(customerName))
/*      */           {
/*  424 */             custId = tempCustId;
/*  425 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  430 */       Properties siteProps = EnterpriseUtil.getAllSiteProps(custId, username);
/*  431 */       if (siteProps.size() == 0) {
/*  432 */         out.println("");
/*  433 */         return null;
/*      */       }
/*      */       
/*  436 */       ArrayList<String> sitesList = new ArrayList();
/*      */       
/*  438 */       if ((siteProps != null) && (siteProps.size() > 0))
/*      */       {
/*  440 */         Enumeration siteEnum = siteProps.keys();
/*  441 */         while (siteEnum.hasMoreElements())
/*      */         {
/*  443 */           String siteId = siteEnum.nextElement().toString();
/*  444 */           sitesList.add(siteProps.getProperty(siteId));
/*      */         }
/*  446 */         String siteList = sitesList.toString();
/*  447 */         siteList = siteList.substring(1, siteList.length() - 1);
/*  448 */         AMLog.debug("Sites present under Customer '" + customerName + "' are " + siteList);
/*  449 */         out.println(siteList);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  454 */       e.printStackTrace();
/*      */     }
/*  456 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getAllDevicesforCustomerSite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  461 */     PrintWriter out = response.getWriter();
/*  462 */     ArrayList<String> devicesList = new ArrayList();
/*  463 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/*      */     try
/*      */     {
/*  466 */       String custName = null;
/*  467 */       Properties custProps = EnterpriseUtil.getCustProp(request);
/*  468 */       Enumeration custEnum = custProps.keys();
/*  469 */       while (custEnum.hasMoreElements())
/*      */       {
/*  471 */         custName = custEnum.nextElement().toString();
/*      */       }
/*  473 */       String siteName = request.getParameter("siteName");
/*  474 */       String category = request.getParameter("category");
/*  475 */       String mapName = request.getParameter("mapName");
/*  476 */       AMLog.debug("Getting the devices of category " + category + " for site '" + siteName + "' under customer '" + custName + "'");
/*  477 */       if ((siteName != null) && (siteName.equalsIgnoreCase("All Sites")))
/*      */       {
/*  479 */         ArrayList<String> sitesArray = MapViewUtil.getSitesListForCustomer(custName, username);
/*  480 */         int noOfSites = sitesArray.size();
/*  481 */         for (Object site : sitesArray)
/*      */         {
/*  483 */           int tempSiteId = MapViewUtil.getSiteId(custName, (String)site);
/*  484 */           Vector resourceIds = EnterpriseUtil.getResourceIdsForSite(tempSiteId);
/*  485 */           String resourceidString = Constants.convertVectorToCSV(resourceIds);
/*  486 */           ArrayList<String> tempDevicesList = MapViewUtil.getAllDevices(category, resourceidString, mapName);
/*  487 */           if ((tempDevicesList != null) && (tempDevicesList.size() > 0))
/*      */           {
/*  489 */             devicesList.addAll(tempDevicesList);
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  495 */         int siteId = MapViewUtil.getSiteId(custName, siteName);
/*  496 */         Vector resourceIds = EnterpriseUtil.getResourceIdsForSite(siteId);
/*  497 */         String resourceidString = Constants.convertVectorToCSV(resourceIds);
/*  498 */         devicesList = MapViewUtil.getAllDevices(category, resourceidString, mapName);
/*      */       }
/*  500 */       if (devicesList.size() == 0) {
/*  501 */         out.println("");
/*  502 */         return null;
/*      */       }
/*  504 */       String deviceList = devicesList.toString();
/*  505 */       deviceList = deviceList.substring(1, deviceList.length() - 1);
/*  506 */       out.println(deviceList);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  510 */       e.printStackTrace();
/*      */     }
/*  512 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward saveBGMap(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  517 */     FileOutputStream outputStream = null;
/*      */     try
/*      */     {
/*  520 */       DynaActionForm df = (DynaActionForm)form;
/*  521 */       FormFile myFile = (FormFile)df.get("bgBrowseFile");
/*  522 */       String nmshome = System.getProperty("webnms.rootdir");
/*  523 */       String fileName = nmshome + File.separator + "images" + File.separator + "maps" + File.separator + myFile.getFileName();
/*  524 */       AMLog.debug("The BG file name is " + myFile.getFileName());
/*  525 */       outputStream = new FileOutputStream(new File(fileName));
/*  526 */       outputStream.write(myFile.getFileData());
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  539 */       return null;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  530 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/*  534 */         outputStream.close();
/*      */       } catch (Exception e) {
/*  536 */         AMLog.debug("Exception while closing the stream. This can be ignored ");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward saveDeviceImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  544 */     FileOutputStream outputStream = null;
/*      */     try
/*      */     {
/*  547 */       DynaActionForm df = (DynaActionForm)form;
/*  548 */       FormFile myFile = (FormFile)df.get("BrowseDevices");
/*  549 */       String nmshome = System.getProperty("webnms.rootdir");
/*  550 */       String fileName = nmshome + File.separator + "images" + File.separator + myFile.getFileName();
/*  551 */       AMLog.debug("The device file name is " + myFile.getFileName());
/*  552 */       outputStream = new FileOutputStream(new File(fileName));
/*  553 */       outputStream.write(myFile.getFileData());
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  566 */       return null;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  557 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/*  561 */         outputStream.close();
/*      */       } catch (Exception e) {
/*  563 */         AMLog.debug("Exception while closing the stream. This can be ignored ");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward isSubGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  571 */     PrintWriter out = response.getWriter();
/*      */     try
/*      */     {
/*  574 */       String mapViewName = request.getParameter("mapViewName");
/*  575 */       if ((mapViewName != null) && (!mapViewName.equals("")))
/*      */       {
/*  577 */         String bsgId = MapViewUtil.getBSGIdForMapName(mapViewName);
/*  578 */         boolean isSubgroup = MapViewUtil.isSubgroup(bsgId);
/*  579 */         if (isSubgroup)
/*      */         {
/*  581 */           out.println("TRUE");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  587 */       e.printStackTrace();
/*      */     }
/*  589 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward verifyMapName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  594 */     PrintWriter out = response.getWriter();
/*      */     try
/*      */     {
/*  597 */       String mapViewName = request.getParameter("mapViewName");
/*  598 */       if ((mapViewName != null) && (!mapViewName.equals("")))
/*      */       {
/*  600 */         boolean mapPresent = MapViewUtil.verifyMapName(mapViewName);
/*  601 */         out.println(mapPresent);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  606 */       e.printStackTrace();
/*      */     }
/*  608 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getResIDAndDispName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  613 */     response.setContentType("text/plain; charset=UTF-8");
/*  614 */     PrintWriter out = response.getWriter();
/*      */     try
/*      */     {
/*  617 */       String resIds = request.getParameter("resIds");
/*  618 */       String resIdAndDN = MapViewUtil.getResIDAndDispName(resIds);
/*  619 */       out.println(resIdAndDN);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  624 */       ex.printStackTrace();
/*      */     }
/*  626 */     out.flush();
/*  627 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getImagesForResources(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  632 */     response.setContentType("text/plain");
/*  633 */     PrintWriter out = response.getWriter();
/*      */     try
/*      */     {
/*  636 */       String resIds = request.getParameter("resIds");
/*  637 */       String devImages = MapViewUtil.getImagesForResources(resIds);
/*  638 */       out.println(devImages);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  643 */       ex.printStackTrace();
/*      */     }
/*  645 */     out.flush();
/*  646 */     return null;
/*      */   }
/*      */   
/*      */   public List getResourceTypes() {
/*  650 */     return getResourceTypes(null);
/*      */   }
/*      */   
/*      */ 
/*      */   public List getResourceTypes(HttpServletRequest request)
/*      */   {
/*  656 */     ArrayList<LinkedHashMap<String, String>> list = new ArrayList();
/*  657 */     LinkedHashMap<String, String> restypedisplaynameMapping = new LinkedHashMap();
/*  658 */     restypedisplaynameMapping.put(FormatUtil.getString("am.monitortab.allmonitors.text"), "All");
/*      */     
/*      */ 
/*  661 */     ArrayList addedList = new ArrayList();
/*  662 */     boolean nwdAvailable = false;
/*  663 */     boolean emAvailable = false;
/*      */     try
/*      */     {
/*  666 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  667 */       String groupCondition = "";
/*  668 */       String resIds = "";
/*  669 */       String qryAppend = "";
/*  670 */       Vector resIds_vector = null;
/*  671 */       String qry = "select  DISTINCT(AM_ManagedObject.TYPE),SUBGROUP,  AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE  where TYPE in " + Constants.resourceTypes + " and  RESOURCEGROUP not in ('NWD','EMO','SAN') " + qryAppend + " " + groupCondition + " order by AM_ManagedResourceType.DISPLAYNAME";
/*  672 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  673 */       String qry1 = "select RESOURCEGROUP from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('NWD','SAN')" + qryAppend;
/*  674 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry1);
/*  675 */       if (rs1.next()) {
/*  676 */         nwdAvailable = true;
/*      */       }
/*  678 */       AMConnectionPool.closeStatement(rs1);
/*      */       
/*  680 */       String qry2 = "select RESOURCEGROUP from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('EMO')" + qryAppend;
/*  681 */       rs1 = AMConnectionPool.executeQueryStmt(qry2);
/*  682 */       if (rs1.next()) {
/*  683 */         emAvailable = true;
/*      */       }
/*  685 */       AMConnectionPool.closeStatement(rs1);
/*      */       
/*  687 */       while (rs.next())
/*      */       {
/*      */ 
/*  690 */         if (rs.getString("SUBGROUP").equals("File System Monitor"))
/*      */         {
/*      */ 
/*  693 */           if (!addedList.contains("File System Monitor"))
/*      */           {
/*  695 */             addedList.add(rs.getString("SUBGROUP"));
/*  696 */             restypedisplaynameMapping.put(FormatUtil.getString(rs.getString("SUBGROUP")), FormatUtil.getString(rs.getString("DISPLAYNAME")));
/*      */           }
/*      */           
/*      */         }
/*  700 */         else if (rs.getString("SUBGROUP").equals("Windows"))
/*      */         {
/*  702 */           if (!addedList.contains("Windows"))
/*      */           {
/*  704 */             addedList.add(rs.getString("SUBGROUP"));
/*  705 */             restypedisplaynameMapping.put(rs.getString("SUBGROUP"), "Windows");
/*      */           }
/*      */         }
/*      */         else {
/*  709 */           addedList.add(rs.getString("SUBGROUP"));
/*      */           
/*  711 */           restypedisplaynameMapping.put(FormatUtil.getString(rs.getString("SUBGROUP")), FormatUtil.getString(rs.getString("DISPLAYNAME")));
/*      */         } }
/*  713 */       rs.close();
/*  714 */       rs1.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  718 */       exc.printStackTrace();
/*      */     }
/*      */     
/*  721 */     if (nwdAvailable)
/*      */     {
/*      */ 
/*  724 */       ArrayList<String> categories = MapViewUtil.getDeviceCategories();
/*  725 */       for (String tempCategory : categories)
/*      */       {
/*  727 */         String category = tempCategory;
/*  728 */         category = category.substring(category.lastIndexOf("-") + 1);
/*  729 */         restypedisplaynameMapping.put(tempCategory, category);
/*      */       }
/*      */     }
/*  732 */     if (emAvailable)
/*      */     {
/*  734 */       restypedisplaynameMapping.put("EMO", "Site24x7 Monitors");
/*      */     }
/*      */     
/*  737 */     list.add(restypedisplaynameMapping);
/*  738 */     return list;
/*      */   }
/*      */   
/*      */   public void setResourceTypes(String[] resourceTypes) {
/*  742 */     this.resourceTypes = resourceTypes;
/*      */   }
/*      */   
/*      */   public String getType() {
/*  746 */     return this.type;
/*      */   }
/*      */   
/*      */   public void setType(String temp) {
/*  750 */     this.type = temp;
/*      */   }
/*      */   
/*      */   public ActionForward createMonitorGroupForMap(ActionMapping mapping, HttpServletRequest request, boolean isNextButton, ActionMessages messages, ActionErrors errors)
/*      */   {
/*  755 */     String applicationName = request.getParameter("name");
/*  756 */     String description = request.getParameter("description");
/*  757 */     String parentHaid = request.getParameter("parentHaid");
/*  758 */     String username = request.getUserPrincipal().getName();
/*  759 */     String locationID = request.getParameter("locationid");
/*  760 */     String bsId = request.getParameter("haid");
/*  761 */     String buttonVal = request.getParameter("buttonType1");
/*  762 */     String createMV = request.getParameter("createMV");
/*  763 */     AMLog.debug("Create mapview : " + createMV);
/*      */     
/*  765 */     String fromMapview = (String)request.getAttribute("fromMapview");
/*  766 */     if ("true".equalsIgnoreCase(fromMapview))
/*      */     {
/*  768 */       applicationName = (String)request.getAttribute("name");
/*  769 */       description = FormatUtil.getString("am.webclient.createbusinessservice.mapview");
/*  770 */       parentHaid = (String)request.getAttribute("parentHaid");
/*  771 */       bsId = (String)request.getAttribute("haid");
/*  772 */       buttonVal = "";
/*      */     }
/*  774 */     applicationName = applicationName.trim();
/*  775 */     if (createMV != null)
/*      */     {
/*  777 */       request.getSession().setAttribute("createMV", createMV);
/*      */     }
/*  779 */     String siteId = "-1";
/*  780 */     String custId = "-1";
/*      */     try
/*      */     {
/*  783 */       AMManagedObject ammo = null;
/*      */       
/*  785 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  787 */         custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/*  788 */         if (parentHaid.equals("-1"))
/*      */         {
/*  790 */           parentHaid = custId;
/*      */         }
/*      */       }
/*      */       
/*  794 */       if (parentHaid.equals("-1"))
/*      */       {
/*  796 */         AMLog.debug("MapViewActions : About to add the business service with ID : " + bsId + " and name : " + applicationName);
/*  797 */         ammo = this.mo.createManagedApplication(applicationName, description, username, null, null);
/*      */       }
/*      */       else
/*      */       {
/*  801 */         AMLog.debug("MapViewActions  : About to add the SubGroup for ID : " + parentHaid + " and name : " + applicationName);
/*  802 */         ammo = this.mo.createManagedApplication(applicationName, description, username, null, null, true);
/*      */       }
/*  804 */       if (ammo != null)
/*      */       {
/*      */ 
/*  807 */         int haid = ammo.getRESOURCEID();
/*      */         try
/*      */         {
/*  810 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  811 */           AMConnectionPool.executeUpdateStmt("insert into AM_MinMaxAvgData (ARCHIVEDTIME, RESID, DURATION, ATTRIBUTEID, MINVALUE, MAXVALUE, TOTAL, TOTALCOUNT) values('" + System.currentTimeMillis() + "', '" + haid + "', '-1','17','0','0','0','1')");
/*      */         }
/*      */         catch (Exception exp)
/*      */         {
/*  815 */           exp.printStackTrace();
/*      */         }
/*      */         
/*      */         try
/*      */         {
/*  820 */           if ((locationID != null) && (!locationID.equals("")))
/*      */           {
/*  822 */             int loc = Integer.parseInt(locationID);
/*  823 */             String query = "insert into AM_GMapCountryResourceRel values(" + haid + "," + loc + ")";
/*  824 */             AMConnectionPool.executeUpdateStmt(query);
/*  825 */             AMLog.debug("***************Associated the location. Location ID " + loc);
/*      */           }
/*      */         }
/*      */         catch (NumberFormatException e)
/*      */         {
/*  830 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*  834 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  836 */           String act1 = null;
/*  837 */           String app_type = null;
/*  838 */           DBUtil.insertParentChildMapper(Integer.parseInt(parentHaid), haid);
/*  839 */           if (parentHaid.equals(custId))
/*      */           {
/*      */ 
/*  842 */             app_type = "BSG0";
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  847 */             app_type = "BSG1";
/*      */           }
/*  849 */           act1 = " INSERT INTO AM_HOLISTICAPPLICATION_EXT(RESOURCEID, APP_TYPE) VALUES (" + haid + ", '" + app_type + "')";
/*  850 */           AMConnectionPool.executeUpdateStmt(act1);
/*  851 */           AMLog.debug("Added the BSG : " + haid + " as a subgroup to parent : " + parentHaid + " with app_type in AM_HOLISTICAPPLICATION_EXT as : " + app_type);
/*  852 */           EnterpriseUtil.addUpdateQueryToFile(act1);
/*  853 */           request.setAttribute("custId", custId);
/*  854 */           Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/*  855 */           siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  891 */         request.setAttribute("haid", String.valueOf(haid));
/*  892 */         request.setAttribute("creationdate", String.valueOf(System.currentTimeMillis()));
/*  893 */         String[] owners = request.getParameterValues("selectedowners_list");
/*  894 */         if (owners != null)
/*      */         {
/*  896 */           PreparedStatement ps = null;
/*      */           try
/*      */           {
/*  899 */             ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_HOLISTICAPPLICATION_OWNERS values(?,?)");
/*  900 */             for (int i = 0; i < owners.length; i++)
/*      */             {
/*  902 */               ps.setInt(1, haid);
/*  903 */               ps.setString(2, owners[i]);
/*  904 */               ps.addBatch();
/*      */             }
/*  906 */             ps.executeBatch();
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/*  920 */               if (ps != null)
/*      */               {
/*  922 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/*  927 */               ee.printStackTrace();
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/*  951 */               new AMRCAnalyser().applyRCA(haid, 17, System.currentTimeMillis(), true, true, 1);
/*  952 */               new AMRCAnalyser().applyRCA(haid, 18, System.currentTimeMillis(), true, false, 2);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/*  956 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (SQLException se)
/*      */           {
/*  910 */             se.printStackTrace();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  914 */             e.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/*  920 */               if (ps != null)
/*      */               {
/*  922 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/*  927 */               ee.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  958 */         if ("true".equalsIgnoreCase(fromMapview))
/*      */         {
/*  960 */           request.setAttribute("bsgId", Integer.valueOf(haid));
/*  961 */           return null;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  966 */         return null;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  972 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", "Unable to create application"));
/*  973 */       saveErrors(request, errors);
/*  974 */       return mapping.getInputForward();
/*      */ 
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*  979 */       int errorcode = se.getErrorCode();
/*  980 */       if (errorcode == 1062)
/*      */       {
/*  982 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("am.webclient.bs.name.exists.txt", applicationName));
/*      */       }
/*      */       else
/*      */       {
/*  986 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", se.toString()));
/*      */       }
/*  988 */       saveErrors(request, errors);
/*  989 */       return mapping.getInputForward();
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  994 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", ee.toString()));
/*  995 */       saveErrors(request, errors); }
/*  996 */     return mapping.getInputForward();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/* 1006 */     if ((stringToTrim != null) && (lengthOfTrimmedString > 0))
/*      */     {
/* 1008 */       if (stringToTrim.length() > lengthOfTrimmedString)
/*      */       {
/* 1010 */         return stringToTrim.substring(0, lengthOfTrimmedString - 3) + "...";
/*      */       }
/*      */     }
/* 1013 */     return stringToTrim;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\MapViewActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */