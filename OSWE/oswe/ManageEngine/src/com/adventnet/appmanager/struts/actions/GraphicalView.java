/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.fault.RuleAnalyser;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.discovery.ADM.ADMUtil;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.struts.form.FlashForm;
/*      */ import com.adventnet.appmanager.util.ChildMOHandler;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.utils.client.BusinessViewUtil;
/*      */ import com.manageengine.appmanager.utils.client.ClientAuditUtil;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.apache.struts.mock.MockHttpServletRequest;
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONException;
/*      */ import org.json.JSONObject;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GraphicalView
/*      */   extends DispatchAction
/*      */ {
/*      */   public ActionForward saveState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   72 */     response.setContentType("text/xml;charset=UTF-8");
/*   73 */     String displayName = request.getParameter("displayName");
/*   74 */     String userid = "-1";
/*   75 */     String viewid = "-1";
/*   76 */     BusinessViewUtil businessViewUtil = new BusinessViewUtil();
/*      */     try
/*      */     {
/*   79 */       boolean isHtml = Boolean.valueOf(request.getParameter("isHtml")).booleanValue();
/*   80 */       AMLog.debug("Inside saveState" + isHtml);
/*   81 */       String haid = request.getParameter("haid");
/*   82 */       if (request.getParameter("viewid") != null)
/*      */       {
/*   84 */         viewid = request.getParameter("viewid");
/*      */       }
/*   86 */       ManagedApplication mo = new ManagedApplication();
/*   87 */       userid = mo.getUserID(request);
/*      */       
/*   89 */       String nodesForSave = request.getParameter("nodesForSave");
/*   90 */       nodesForSave = businessViewUtil.decodeSpecialCharactersFromJSONString(nodesForSave);
/*   91 */       JSONObject nodeJSON = new JSONObject(nodesForSave);
/*   92 */       HashMap map = (HashMap)request.getAttribute("map");
/*   93 */       String xCanvas = request.getParameter("xcanvas");
/*   94 */       String yCanvas = request.getParameter("ycanvas");
/*   95 */       AMLog.debug("Position of the canvas" + xCanvas + " " + yCanvas);
/*   96 */       viewid = businessViewUtil.saveStateForJIT(haid, userid, viewid, nodeJSON, xCanvas, yCanvas, new JSONObject(), Boolean.FALSE.booleanValue());
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  103 */       if (displayName != null) {
/*  104 */         String updateQuery = "update AM_MONITORGROUP_FLASHVIEWCONFIG set DISPLAYNAME='" + displayName + "' where VIEWID=" + viewid;
/*  105 */         AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */       } else {
/*  107 */         displayName = FormatUtil.getString("am.webclient.flashview.displayname");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  113 */       ex.printStackTrace();
/*      */     }
/*  115 */     response.setContentType("text/json;charset=UTF-8");
/*  116 */     PrintWriter out = response.getWriter();
/*      */     try
/*      */     {
/*  119 */       JSONObject result = new JSONObject();
/*  120 */       result.put("viewid", viewid);
/*  121 */       out.println(result);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  125 */       exc.printStackTrace();
/*      */     }
/*  127 */     out.flush();
/*  128 */     return null;
/*      */   }
/*      */   
/*      */   public static String removeAllControlCharacter(String inString) {
/*  132 */     byte[] byteArr = inString.getBytes();
/*      */     try {
/*  134 */       if (null == inString) return null;
/*  135 */       for (int i = 0; i < byteArr.length; i++) {
/*  136 */         byte ch = byteArr[i];
/*      */         
/*  138 */         if (((ch > 0) && (ch < 32)) || (ch == Byte.MAX_VALUE))
/*      */         {
/*  140 */           byteArr[i] = 32;
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/*  144 */       e.printStackTrace();
/*      */     }
/*  146 */     return new String(byteArr);
/*      */   }
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
/*      */ 
/*      */   public String parseJSON(String groupId, HashMap map, JSONObject json, Statement statement, String viewid)
/*      */   {
/*  219 */     String condition = "(";
/*  220 */     String query = "";
/*      */     try {
/*  222 */       int totalNumberOfNodes = json.getInt("totalNumberOfNodes");
/*  223 */       for (int i = 2; i <= totalNumberOfNodes; i++) {
/*  224 */         String parentId = json.getString("parentID_" + i);
/*  225 */         String childId = json.getString("resourceID_" + i);
/*  226 */         String positionX = json.getString("posX_" + i);
/*  227 */         String positionY = json.getString("posY_" + i);
/*  228 */         if (!viewid.equals("-1")) {
/*  229 */           query = "INSERT INTO AM_FLASHVIEW_COORDINATES (VIEWID,PARENTID,CHILDID,XCOORD,YCOORD)  VALUES (" + viewid + "," + parentId + "," + childId + "," + positionX + "," + positionY + ")";
/*      */         }
/*      */         else {
/*  232 */           query = "INSERT INTO AM_FLASHVIEW_COORDINATES (VIEWID,PARENTID,CHILDID,XCOORD,YCOORD)  VALUES (" + DBQueryUtil.getIncrementedID("VIEWID", "AM_FLASHVIEW_COORDINATES") + "," + parentId + "," + childId + "," + positionX + "," + positionY + ")";
/*      */         }
/*  234 */         condition = condition + "(PARENTID=" + parentId + " AND CHILDID=" + childId + ") OR";
/*  235 */         statement.addBatch(query);
/*      */       }
/*      */     } catch (JSONException e) {
/*  238 */       e.printStackTrace();
/*      */     } catch (SQLException e) {
/*  240 */       e.printStackTrace();
/*      */     }
/*  242 */     condition = condition + ")";
/*  243 */     return condition;
/*      */   }
/*      */   
/*      */   private void parseXML(NodeList mgNodes, String haid, String paranetid, String viewid, Statement statement, String userid) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  250 */       int monitorNodesSize = mgNodes.getLength();
/*  251 */       for (int i = 0; i < monitorNodesSize; i++)
/*      */       {
/*  253 */         Node monitorNode = mgNodes.item(i);
/*      */         
/*  255 */         String nodetype = monitorNode.getNodeName();
/*  256 */         Element node = null;
/*  257 */         if ((monitorNode instanceof Element))
/*      */         {
/*  259 */           node = (Element)monitorNode;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  266 */           String x = node.getAttribute("x");
/*  267 */           String y = node.getAttribute("y");
/*  268 */           String chilid = node.getAttribute("id");
/*  269 */           String query = "";
/*  270 */           if (!viewid.equals("-1")) {
/*  271 */             query = "INSERT INTO AM_FLASHVIEW_COORDINATES (VIEWID,PARENTID,CHILDID,XCOORD,YCOORD)  VALUES (" + viewid + "," + paranetid + "," + chilid + "," + x + "," + y + ")";
/*      */           }
/*      */           else {
/*  274 */             query = "INSERT INTO AM_FLASHVIEW_COORDINATES (VIEWID,PARENTID,CHILDID,XCOORD,YCOORD)  VALUES (" + DBQueryUtil.getIncrementedID("VIEWID", "AM_FLASHVIEW_COORDINATES") + "," + paranetid + "," + chilid + "," + x + "," + y + ")";
/*      */           }
/*  276 */           statement.addBatch(query);
/*  277 */           if (nodetype.equals("SubGroup"))
/*      */           {
/*  279 */             NodeList nodeNodes = monitorNode.getChildNodes();
/*  280 */             parseXML(nodeNodes, haid, chilid, viewid, statement, userid);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  286 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getMGXML(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  293 */     boolean isHtml = request.getParameter("isHtml") != null ? Boolean.valueOf(request.getParameter("isHtml")).booleanValue() : true;
/*      */     
/*  295 */     BusinessViewUtil businessViewUtil = new BusinessViewUtil();
/*      */     try
/*      */     {
/*  298 */       String haid = "0";
/*  299 */       String viewid = "-1";
/*  300 */       String zoomlevel = "1";
/*  301 */       String groupName = "";
/*  302 */       String haStatus = "";
/*  303 */       String mgmessage = "";
/*  304 */       boolean showlabel = true;
/*  305 */       ArrayList resIdList = new ArrayList();
/*  306 */       ArrayList attIdList = new ArrayList();
/*  307 */       ArrayList childList = new ArrayList();
/*  308 */       ArrayList filterList = new ArrayList();
/*  309 */       Hashtable<String, String> resIdVsType = new Hashtable();
/*  310 */       HashMap map = new HashMap();
/*  311 */       haid = request.getParameter("haid");
/*      */       try
/*      */       {
/*  314 */         if (haid != null) {
/*  315 */           Long.parseLong(haid);
/*      */         }
/*      */       } catch (Exception e) {
/*  318 */         AMLog.debug("GraphicalView : getMGXML incorrect haid passed. haid : " + haid + ". Error : " + e.getMessage());
/*  319 */         e.printStackTrace();
/*  320 */         return null;
/*      */       }
/*  322 */       Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/*  323 */       ManagedApplication mo = new ManagedApplication();
/*  324 */       String userid = mo.getUserID(request);
/*  325 */       Properties view = new Properties();
/*  326 */       String groupDisplayName = "";
/*  327 */       boolean isOperator = request.isUserInRole("OPERATOR");
/*  328 */       response.setCharacterEncoding("UTF-8");
/*  329 */       PrintWriter out = response.getWriter();
/*  330 */       if (isHtml) {
/*  331 */         response.setContentType("text/json;charset=UTF-8");
/*      */         try
/*      */         {
/*  334 */           viewid = request.getParameter("viewid");
/*      */           try
/*      */           {
/*  337 */             if (viewid != null) {
/*  338 */               Long.parseLong(viewid);
/*      */             }
/*      */           } catch (Exception e) {
/*  341 */             AMLog.debug("GraphicalView : getMGXML incorrect viewid passed. viewid : " + viewid + ". Error : " + e.getMessage());
/*  342 */             e.printStackTrace();
/*  343 */             return null;
/*      */           }
/*  345 */           if ((request.getParameter("viewid") != null) && (!request.getParameter("viewid").equals("-1")))
/*      */           {
/*  347 */             viewid = request.getParameter("viewid");
/*  348 */             view = BusinessViewUtil.getViewforViewid(viewid);
/*      */           }
/*      */           else
/*      */           {
/*  352 */             view = getViewforUser(userid, haid);
/*  353 */             if (view.getProperty("VIEWID") == null)
/*      */             {
/*  355 */               view = getViewforUser("1", haid);
/*      */             }
/*      */           }
/*  358 */           view.setProperty("VIEWID", viewid);
/*  359 */           if (DBUtil.doesKeyPresentInBusinessCache(viewid + "_" + haid)) {
/*  360 */             boolean onChangeViewProps = (request.getParameter("onChangeViewProps") != null ? Boolean.valueOf(request.getParameter("onChangeViewProps")) : Boolean.FALSE).booleanValue();
/*      */             
/*  362 */             if (onChangeViewProps) {
/*  363 */               String showCritical = request.getParameter("showCritical");
/*  364 */               if (showCritical != null) {
/*  365 */                 String showTopOnly = request.getParameter("showTopOnly");
/*  366 */                 String all = request.getParameter("all");
/*  367 */                 String showGroups = request.getParameter("showGroups");
/*  368 */                 JSONObject dispProps = new JSONObject(businessViewUtil.decodeSpecialCharactersFromJSONString(request.getParameter("displayProps")));
/*  369 */                 view.setProperty("SHOWTOPLEVELSUBMGS", showTopOnly);
/*  370 */                 view.setProperty("SHOWONLYSUBGROUPS", showGroups);
/*  371 */                 view.setProperty("SHOWCRITICALMONITORS", showCritical);
/*  372 */                 view.setProperty("DISPLAYNAME", dispProps.getString("displayName"));
/*      */                 try {
/*  374 */                   view.setProperty("BGCOLOR", dispProps.getString("bgcolor"));
/*  375 */                   view.setProperty("LINECOLOR", dispProps.getString("lineColor"));
/*  376 */                   view.setProperty("LABELCOLOR", dispProps.getString("labelColor"));
/*  377 */                   view.setProperty("LINETHICKNESS", dispProps.getString("lineThickness"));
/*  378 */                   view.setProperty("LINETRANSPARENCY", dispProps.getString("lineTransparency"));
/*      */                 } catch (JSONException e) {
/*  380 */                   e.printStackTrace();
/*      */                 }
/*  382 */                 BusinessViewUtil.updateViewPropsInDB(viewid, view);
/*      */               }
/*      */             }
/*  385 */             JSONObject jsonFromCache = businessViewUtil.getDataFromBusinessCache(haid, view, healthkeys);
/*      */             
/*  387 */             if (jsonFromCache != null) {
/*  388 */               jsonFromCache.write(out);
/*  389 */               out.flush();
/*  390 */               out.close();
/*  391 */               return null;
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  397 */           e.printStackTrace();
/*      */         }
/*      */       }
/*  400 */       response.setContentType("text/xml;charset=UTF-8");
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
/*  415 */       if (view.getProperty("VIEWID") == null)
/*      */       {
/*  417 */         view.setProperty("VIEWID", "-1");
/*  418 */         view.setProperty("ZOOMLEVEL", "1");
/*  419 */         view.setProperty("SHOWLABEL", "true");
/*  420 */         view.setProperty("SHOWCRITICALMONITORS", "false");
/*  421 */         view.setProperty("SHOWONLYSUBGROUPS", "false");
/*  422 */         view.setProperty("SHOWTOPLEVELSUBMGS", "false");
/*  423 */         view.setProperty("XCANVAS", "20");
/*  424 */         view.setProperty("YCANVAS", "20");
/*      */       }
/*      */       try
/*      */       {
/*  428 */         String query = "select RESOURCENAME, DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + haid;
/*  429 */         ResultSet set = AMConnectionPool.executeQueryStmt(query);
/*      */         try
/*      */         {
/*  432 */           if (set.next())
/*      */           {
/*  434 */             groupName = set.getString("RESOURCENAME");
/*  435 */             groupDisplayName = set.getString("DISPLAYNAME");
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  440 */           e.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*  444 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         
/*  447 */         attIdList.add("18");
/*  448 */         if (haid.equals("0"))
/*      */         {
/*  450 */           filterList = getFilterList(view.getProperty("VIEWID"));
/*  451 */           mgmessage = "";
/*      */         }
/*      */         else
/*      */         {
/*  455 */           resIdList.add(haid);
/*  456 */           Properties alert = FaultUtil.getStatus(resIdList, attIdList);
/*  457 */           mgmessage = (String)alert.get(haid + "#18#MESSAGE");
/*  458 */           if (mgmessage != null)
/*      */           {
/*  460 */             if (!isHtml) {
/*  461 */               mgmessage = encodeSpecialCharecters(mgmessage);
/*      */             }
/*      */             
/*      */           }
/*      */           else {
/*  466 */             mgmessage = "";
/*      */           }
/*  468 */           haStatus = alert.getProperty(haid + "#" + "18");
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  473 */         ex.printStackTrace();
/*      */       }
/*      */       
/*  476 */       boolean isPrivillagedUser = ClientDBUtil.isPrivilegedUser(request);
/*  477 */       String userName = request.getRemoteUser();
/*  478 */       String userRole = DBUtil.getUserRole(userName);
/*  479 */       boolean isEditable = ("DELEGATEDADMIN".equals(userRole)) || ("ADMIN".equals(userRole)) || ("ENTERPRISEADMIN".equals(userRole));
/*  480 */       Vector haidVector = new Vector();
/*  481 */       boolean isIT360 = EnterpriseUtil.isIt360MSPEdition();
/*  482 */       if (isIT360)
/*      */       {
/*  484 */         haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*      */       }
/*  486 */       if (isOperator) {
/*  487 */         String loginUserid = "";
/*  488 */         if (Constants.isSsoEnabled()) {
/*  489 */           loginUserid = Constants.getLoginUserid(request);
/*      */         }
/*  491 */         businessViewUtil.setOperatorHAIDs(haid, userName, loginUserid);
/*      */       }
/*  493 */       String query = businessViewUtil.getQueryForFirstLevelChildren(haid, isPrivillagedUser, userName, isIT360, haidVector, userid, isOperator);
/*  494 */       ResultSet childMoRs = null;
/*  495 */       int level = 1;
/*      */       try
/*      */       {
/*  498 */         AMLog.debug("getMGXML query is : " + query);
/*  499 */         childMoRs = AMConnectionPool.executeQueryStmt(query);
/*  500 */         String resIdStr = null;
/*  501 */         String typeName = null;
/*  502 */         while (childMoRs.next())
/*      */         {
/*  504 */           resIdStr = childMoRs.getString("RESOURCEID");
/*  505 */           typeName = childMoRs.getString("TYPE");
/*  506 */           if ((filterList.size() == 0) || (filterList.contains(resIdStr)))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  511 */             ArrayList list = new ArrayList();
/*  512 */             list.add(resIdStr);
/*  513 */             list.add(childMoRs.getString("DISPLAYNAME"));
/*  514 */             list.add(typeName);
/*  515 */             if (isHtml) {
/*  516 */               String imagePath = childMoRs.getString("IMAGEPATH");
/*  517 */               list.add(imagePath);
/*      */             } else {
/*  519 */               list.add(businessViewUtil.getImage(childMoRs.getString("RESOURCEGROUP"), typeName));
/*      */             }
/*  521 */             list.add(businessViewUtil.getImageLable(typeName));
/*  522 */             if ((typeName.equals("HAI")) && (!haid.equals("0")))
/*      */             {
/*  524 */               list.add(businessViewUtil.getMGTypeName(resIdStr));
/*      */             }
/*      */             else
/*      */             {
/*  528 */               list.add(childMoRs.getString("TYPEDISPLAYNAME"));
/*      */             }
/*  530 */             list.add(FormatUtil.getString(childMoRs.getString("TYPEDISPLAYNAME")));
/*  531 */             resIdList.add(resIdStr);
/*  532 */             attIdList.add(healthkeys.get(typeName));
/*  533 */             childList.add(list);
/*  534 */             resIdVsType.put(resIdStr, typeName);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e1) {
/*  539 */         e1.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  543 */         AMConnectionPool.closeStatement(childMoRs);
/*      */       }
/*  545 */       ArrayList<HashMap<String, String>> childMonitorInfo = ChildMOHandler.listChildMonitorUnderMG(haid);
/*      */       try
/*      */       {
/*  548 */         resIdStr = null;
/*  549 */         typeName = null;
/*  550 */         for (Object monitorInfo : childMonitorInfo)
/*      */         {
/*  552 */           resIdStr = (String)((HashMap)monitorInfo).get("resourceid");
/*  553 */           typeName = (String)((HashMap)monitorInfo).get("type");
/*  554 */           if ((filterList.size() == 0) || (filterList.contains(resIdStr)))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  559 */             ArrayList list = new ArrayList();
/*  560 */             list.add(resIdStr);
/*  561 */             list.add(((HashMap)monitorInfo).get("displayname"));
/*  562 */             list.add(typeName);
/*  563 */             list.add(ChildMOHandler.getImageForChildType(typeName));
/*  564 */             list.add(businessViewUtil.getImageLable(typeName));
/*  565 */             if ((typeName.equals("HAI")) && (!haid.equals("0")))
/*      */             {
/*  567 */               list.add(businessViewUtil.getMGTypeName(resIdStr));
/*      */             }
/*      */             else
/*      */             {
/*  571 */               list.add(typeName);
/*      */             }
/*  573 */             list.add(FormatUtil.getString(typeName));
/*  574 */             resIdList.add(resIdStr);
/*  575 */             attIdList.add(healthkeys.get(typeName));
/*  576 */             childList.add(list);
/*  577 */             resIdVsType.put(resIdStr, typeName);
/*      */           }
/*      */         }
/*      */       } catch (Exception e1) { String resIdStr;
/*      */         String typeName;
/*  582 */         e1.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  586 */         AMConnectionPool.closeStatement(childMoRs);
/*      */       }
/*  588 */       businessViewUtil.addDependentMGroups(haid, resIdList, attIdList, childList, healthkeys, resIdVsType);
/*  589 */       map.put(haid, childList);
/*  590 */       for (Enumeration<String> typeKeyEnum = resIdVsType.keys(); typeKeyEnum.hasMoreElements();)
/*      */       {
/*  592 */         String resIdKey = (String)typeKeyEnum.nextElement();
/*  593 */         String resType = (String)resIdVsType.get(resIdKey);
/*  594 */         if ((resType.equals("HAI")) || (resType.equals("MongoDB")) || (resType.equals("Dependent-Group")))
/*      */         {
/*  596 */           Properties requestDetails = new Properties();
/*      */           
/*      */ 
/*  599 */           requestDetails.put("isOperator", Boolean.valueOf(isOperator));
/*  600 */           if (isOperator)
/*      */           {
/*  602 */             businessViewUtil.checkForChildsAndAddToMap(map, haid, resIdList, attIdList, healthkeys, requestDetails, level, isHtml);
/*      */           }
/*      */           else
/*      */           {
/*  606 */             businessViewUtil.checkForChildsAndAddToMap(map, resIdKey, resIdList, attIdList, healthkeys, requestDetails, level, isHtml);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  612 */       if (isHtml) {
/*  613 */         businessViewUtil.generateFullJSON(map, groupName, resIdList, attIdList, healthkeys, haStatus, haid, out, view, mgmessage, groupDisplayName, isEditable);
/*      */       } else {
/*  615 */         generateFullXML(map, groupName, resIdList, attIdList, healthkeys, haStatus, haid, out, view, mgmessage);
/*      */       }
/*      */       try
/*      */       {
/*  619 */         out.flush();
/*  620 */         out.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  624 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  629 */       e.printStackTrace();
/*      */     }
/*  631 */     return null;
/*      */   }
/*      */   
/*      */   public void generateFullXML(HashMap map, String rootName, ArrayList resIdList, ArrayList attIdList, Hashtable healthkeys, String haStatus, String groupId, PrintWriter out, Properties view, String mgmessage)
/*      */   {
/*  636 */     String haImage = "/images/fl/HAI.swf";
/*  637 */     if ((haStatus == null) || (groupId.equals("0")))
/*      */     {
/*  639 */       haStatus = "0";
/*  640 */       haImage = "/images/fl/BSM.swf";
/*      */     }
/*  642 */     double xFactor = 20.0D;
/*  643 */     double yFactor = 20.0D;
/*      */     
/*  645 */     Properties coordinates = BusinessViewUtil.getCoordProps(view.getProperty("VIEWID"));
/*  646 */     if (!coordinates.isEmpty()) {
/*  647 */       xFactor = 600.0D + Double.parseDouble(view.getProperty("XCANVAS"));
/*  648 */       yFactor = 380.0D + Double.parseDouble(view.getProperty("YCANVAS"));
/*      */     }
/*  650 */     out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
/*      */     
/*  652 */     out.print("<MonitorGroup type='HAI'  id='" + groupId + "' imagepath='" + haImage + "' scale='" + view.getProperty("ZOOMLEVEL") + "' viewtype='exploded' severity='" + haStatus + "'  url='/showapplication.do?method=showApplication&haid=" + groupId + "'  " + " message='" + mgmessage + "'  x='" + xFactor + "' y='" + yFactor + "'>");
/*  653 */     Properties alert = FaultUtil.getStatus(resIdList, attIdList);
/*  654 */     ArrayList childList = (ArrayList)map.get(groupId);
/*  655 */     HashMap extDeviceMap = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  665 */     HashMap site24x7List = null;
/*      */     try {
/*  667 */       site24x7List = DBUtil.getAllsite24x7MonitorsLink();
/*      */     }
/*      */     catch (Exception ex) {
/*  670 */       ex.printStackTrace();
/*      */     }
/*  672 */     for (int i = 0; i < childList.size(); i++)
/*      */     {
/*  674 */       ArrayList elementList = (ArrayList)childList.get(i);
/*  675 */       String resId = (String)elementList.get(0);
/*  676 */       String dispName = (String)elementList.get(1);
/*  677 */       String type = (String)elementList.get(2);
/*  678 */       String imagepath = (String)elementList.get(3);
/*  679 */       String imagelable = (String)elementList.get(4);
/*  680 */       String typedisplayname = (String)elementList.get(5);
/*  681 */       String groupType = (String)elementList.get(5);
/*  682 */       String severity = alert.getProperty(resId + "#" + healthkeys.get(type));
/*  683 */       String messageKey = resId + "#" + healthkeys.get(type) + "#MESSAGE";
/*  684 */       String message = (String)alert.get(messageKey);
/*  685 */       boolean showCriticalMonitors = view.getProperty("SHOWCRITICALMONITORS").equals("true");
/*  686 */       boolean showOnlySubgroups = view.getProperty("SHOWONLYSUBGROUPS").equals("true");
/*  687 */       boolean showOnlyTopSubgroups = false;
/*  688 */       boolean isDependentMGroup = groupType.equals("Dependent-Group");
/*  689 */       if (isDependentMGroup) {
/*  690 */         typedisplayname = FormatUtil.getString("am.webclient.monitorgroupdetails.dependentgroup.text");
/*      */       }
/*  692 */       showOnlyTopSubgroups = (view.getProperty("SHOWTOPLEVELSUBMGS") != null) && (view.getProperty("SHOWTOPLEVELSUBMGS").equals("true"));
/*  693 */       if (message != null)
/*      */       {
/*  695 */         message = FormatUtil.getString("am.webclient.common.name.text") + " : <b>" + dispName + "</b><br>" + FormatUtil.getString("am.webclient.common.type.text") + " : <b>" + typedisplayname + "</b><br>" + message;
/*  696 */         message = encodeSpecialCharecters(message);
/*      */       }
/*      */       else
/*      */       {
/*  700 */         message = FormatUtil.getString("am.webclient.common.name.text") + " : <b>" + dispName + "</b><br>" + FormatUtil.getString("am.webclient.common.type.text") + " :  <b>" + typedisplayname + "</b><br>";
/*  701 */         message = encodeSpecialCharecters(message);
/*      */       }
/*  703 */       if (severity == null)
/*      */       {
/*  705 */         severity = "0";
/*      */       }
/*  707 */       if ((view.getProperty("SHOWLABEL") != null) && (view.getProperty("SHOWLABEL").equals("false")) && (!type.equals("HAI")))
/*      */       {
/*  709 */         dispName = "";
/*      */       }
/*  711 */       if ((!showCriticalMonitors) || (severity.equals("1")) || (showOnlySubgroups))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  716 */         if ((type.equals("HAI")) || (type.equals("MongoDB")))
/*      */         {
/*  718 */           String url = "url='/showapplication.do?method=showApplication&haid=" + resId + "'";
/*  719 */           if (type.equals("MongoDB"))
/*      */           {
/*  721 */             url = "url='/showresource.do?method=showResourceForResourceID&resourceid=" + resId + "'";
/*      */           }
/*  723 */           out.print("<SubGroup type='" + type + "' name='" + encodeSpecialCharecters(dispName) + "' id='" + resId + "' imagepath='" + imagepath + "' imagelable='" + imagelable + "' message='" + message + "' severity='" + severity + "' " + url + " " + getCoordinates(coordinates, groupId, resId) + ">");
/*  724 */           if ((!showOnlyTopSubgroups) && (!isDependentMGroup)) {
/*  725 */             addChildElement(map, dispName, healthkeys, alert, resId, out, coordinates, view, extDeviceMap, site24x7List);
/*      */           }
/*  727 */           out.print("</SubGroup>");
/*      */ 
/*      */ 
/*      */         }
/*  731 */         else if ((!showOnlySubgroups) || ((showCriticalMonitors) && (showOnlySubgroups)))
/*      */         {
/*  733 */           if ((!showCriticalMonitors) || (severity.equals("1")))
/*      */           {
/*      */ 
/*      */ 
/*  737 */             String url = "url='/showresource.do?method=showResourceForResourceID&resourceid=" + resId + "'";
/*  738 */             extDeviceMap = IntegProdDBUtil.getExtDeviceLinkForResourceId(resId);
/*  739 */             if ((extDeviceMap != null) && (extDeviceMap.get(resId) != null))
/*      */             {
/*  741 */               url = "url='" + (String)extDeviceMap.get(resId) + "'";
/*      */             }
/*      */             
/*  744 */             if ((site24x7List != null) && (site24x7List.get(resId) != null))
/*      */             {
/*  746 */               url = "url='/extDeviceAction.do?method=site24x7Reports&resourceid=" + resId + "'";
/*      */             }
/*      */             
/*      */ 
/*  750 */             out.print("<Monitor type='" + type + "' name='" + encodeSpecialCharecters(dispName) + "' id='" + resId + "' imagepath='" + imagepath + "' message='" + message + "' severity='" + severity + "' imagelable='" + imagelable + "' " + url + " " + getCoordinates(coordinates, groupId, resId) + ">");
/*  751 */             out.print("</Monitor>");
/*      */           }
/*      */         } }
/*      */     }
/*  755 */     out.print("</MonitorGroup>");
/*      */   }
/*      */   
/*      */   public void addChildElement(HashMap map, String groupName, Hashtable healthkeys, Properties alert, String tempId, PrintWriter out, Properties coordinates, Properties view, HashMap extDeviceMap, HashMap site24x7List)
/*      */   {
/*  760 */     ArrayList childList = (ArrayList)map.get(tempId);
/*  761 */     for (int i = 0; i < childList.size(); i++)
/*      */     {
/*  763 */       ArrayList elementList = (ArrayList)childList.get(i);
/*  764 */       String resId = (String)elementList.get(0);
/*  765 */       String dispName = (String)elementList.get(1);
/*  766 */       String type = (String)elementList.get(2);
/*  767 */       String imagepath = (String)elementList.get(3);
/*  768 */       String imagelable = (String)elementList.get(4);
/*  769 */       String typedisplayname = (String)elementList.get(5);
/*  770 */       String groupType = (String)elementList.get(5);
/*  771 */       String severity = alert.getProperty(resId + "#" + healthkeys.get(type));
/*  772 */       String messageKey = resId + "#" + healthkeys.get(type) + "#MESSAGE";
/*  773 */       String message = (String)alert.get(messageKey);
/*  774 */       boolean showCriticalMonitors = view.getProperty("SHOWCRITICALMONITORS").equals("true");
/*  775 */       boolean showOnlySubgroups = view.getProperty("SHOWONLYSUBGROUPS").equals("true");
/*  776 */       boolean showOnlyTopSubgroups = false;
/*  777 */       boolean isDependentMGroup = groupType.equals("Dependent-Group");
/*  778 */       if (isDependentMGroup) {
/*  779 */         typedisplayname = FormatUtil.getString("am.webclient.monitorgroupdetails.dependentgroup.text");
/*      */       }
/*  781 */       showOnlyTopSubgroups = (view.getProperty("SHOWTOPLEVELSUBMGS") != null) && (view.getProperty("SHOWTOPLEVELSUBMGS").equals("true"));
/*  782 */       if (message != null)
/*      */       {
/*  784 */         message = FormatUtil.getString("am.webclient.common.name.text") + " : <b>" + dispName + "</b><br>" + FormatUtil.getString("am.webclient.common.type.text") + " : <b>" + typedisplayname + "</b><br>" + message;
/*  785 */         message = encodeSpecialCharecters(message);
/*      */       }
/*      */       else
/*      */       {
/*  789 */         message = FormatUtil.getString("am.webclient.common.name.text") + " : <b>" + dispName + "</b><br>" + FormatUtil.getString("am.webclient.common.type.text") + " : <b>" + typedisplayname + "</b><br>";
/*  790 */         message = encodeSpecialCharecters(message);
/*      */       }
/*      */       
/*  793 */       if (severity == null)
/*      */       {
/*  795 */         severity = "0";
/*      */       }
/*  797 */       if ((!showCriticalMonitors) || (severity.equals("1")) || (showOnlySubgroups))
/*      */       {
/*      */ 
/*      */ 
/*  801 */         if ((view.getProperty("SHOWLABEL") != null) && (view.getProperty("SHOWLABEL").equals("false")) && (!type.equals("HAI")))
/*      */         {
/*  803 */           dispName = "";
/*      */         }
/*  805 */         String tempType = "HAI";
/*  806 */         if ((type.equals("HAI")) || (type.equals("MongoDB")))
/*      */         {
/*  808 */           String url = "url='/showapplication.do?method=showApplication&haid=" + resId + "'";
/*  809 */           if (type.equals("MongoDB"))
/*      */           {
/*  811 */             url = "url='/showresource.do?method=showResourceForResourceID&resourceid=" + resId + "'";
/*      */           }
/*  813 */           out.print("<SubGroup type='" + type + "' name='" + encodeSpecialCharecters(dispName) + "' id='" + resId + "' imagepath='" + imagepath + "' imagelable='" + imagelable + "' message='" + message + "' severity='" + severity + "'  " + url + "  " + getCoordinates(coordinates, tempId, resId) + ">");
/*  814 */           if ((!showOnlyTopSubgroups) && (!isDependentMGroup)) {
/*  815 */             addChildElement(map, dispName, healthkeys, alert, resId, out, coordinates, view, extDeviceMap, site24x7List);
/*      */           }
/*  817 */           out.print("</SubGroup>");
/*      */ 
/*      */ 
/*      */         }
/*  821 */         else if ((!showOnlySubgroups) || ((showCriticalMonitors) && (showOnlySubgroups)))
/*      */         {
/*  823 */           if ((!showCriticalMonitors) || (severity.equals("1")))
/*      */           {
/*      */ 
/*      */ 
/*  827 */             String url = "url='/showresource.do?method=showResourceForResourceID&resourceid=" + resId + "'";
/*  828 */             extDeviceMap = IntegProdDBUtil.getExtDeviceLinkForResourceId(resId);
/*  829 */             if ((extDeviceMap != null) && (extDeviceMap.get(resId) != null))
/*      */             {
/*  831 */               url = "url='" + (String)extDeviceMap.get(resId) + "'";
/*      */             }
/*      */             
/*  834 */             if ((site24x7List != null) && (site24x7List.get(resId) != null))
/*      */             {
/*  836 */               url = "url='/extDeviceAction.do?method=site24x7Reports&resourceid=" + resId + "'";
/*      */             }
/*  838 */             out.print("<Monitor type='" + type + "' name='" + encodeSpecialCharecters(dispName) + "' id='" + resId + "' imagepath='" + imagepath + "' imagelable='" + imagelable + "' message='" + message + "'  severity='" + severity + "' " + url + "  " + getCoordinates(coordinates, tempId, resId) + ">");
/*  839 */             out.print("</Monitor>");
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward resetDesign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  848 */     String haid = request.getParameter("haid");
/*      */     try {
/*  850 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/*  851 */       ManagedApplication mo = new ManagedApplication();
/*  852 */       String userid = mo.getUserID(request);
/*  853 */       String viewid = request.getParameter("viewid");
/*  854 */       isReseted = bUtil.resetDesign(viewid, haid);
/*      */     } catch (Exception e) { boolean isReseted;
/*  856 */       e.printStackTrace();
/*      */     }
/*  858 */     return new ActionForward("/GraphicalView.do?method=getMGXML&haid=" + haid + "&isHtml=true");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getCoordinates(Properties coordinates, String parentid, String chilid)
/*      */   {
/*  866 */     boolean noCoordinates = false;
/*      */     try
/*      */     {
/*  869 */       if (coordinates == null)
/*      */       {
/*  871 */         return "";
/*      */       }
/*  873 */       if ((coordinates.getProperty(parentid + "_" + chilid + "_x") != null) && (coordinates.getProperty(parentid + "_" + chilid + "_y") != null))
/*      */       {
/*  875 */         return " x='" + coordinates.getProperty(new StringBuilder().append(parentid).append("_").append(chilid).append("_x").toString()) + "' y='" + coordinates.getProperty(new StringBuilder().append(parentid).append("_").append(chilid).append("_y").toString()) + "' ";
/*      */       }
/*      */       
/*      */ 
/*  879 */       return "";
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*  885 */       ex.printStackTrace();
/*      */     }
/*  887 */     return "";
/*      */   }
/*      */   
/*      */   public Properties getViewforUser(String userid, String haid)
/*      */   {
/*  892 */     String query = "select VIEWID,ZOOMLEVEL,SHOWLABEL,SHOWCRITICALMONITORS,SHOWONLYSUBGROUPS,SHOWTOPLEVELMGS,SHOWTOPLEVELSUBMGS,XCANVAS,YCANVAS,BGCOLOR,LINECOLOR,LABELCOLOR,LINETRANSPARENCY,LINETHICKNESS from AM_MONITORGROUP_FLASHVIEWCONFIG where HAID=" + haid + " and USERID=" + userid + " and SELECTED=1";
/*      */     
/*  894 */     Properties view = new Properties();
/*      */     try
/*      */     {
/*  897 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  898 */       if (rs.next())
/*      */       {
/*  900 */         view.setProperty("VIEWID", rs.getString("VIEWID"));
/*  901 */         view.setProperty("ZOOMLEVEL", rs.getString("ZOOMLEVEL"));
/*  902 */         view.setProperty("SHOWLABEL", rs.getString("SHOWLABEL"));
/*  903 */         view.setProperty("SHOWCRITICALMONITORS", rs.getString("SHOWCRITICALMONITORS"));
/*  904 */         view.setProperty("SHOWONLYSUBGROUPS", rs.getString("SHOWONLYSUBGROUPS"));
/*  905 */         view.setProperty("XCANVAS", rs.getString("XCANVAS"));
/*  906 */         view.setProperty("YCANVAS", rs.getString("YCANVAS"));
/*  907 */         view.setProperty("BGCOLOR", rs.getString("BGCOLOR"));
/*  908 */         view.setProperty("LINECOLOR", rs.getString("LINECOLOR"));
/*  909 */         view.setProperty("LABELCOLOR", rs.getString("LABELCOLOR"));
/*  910 */         view.setProperty("LINETRANSPARENCY", rs.getString("LINETRANSPARENCY"));
/*  911 */         view.setProperty("LINETHICKNESS", rs.getString("LINETHICKNESS"));
/*  912 */         view.setProperty("SHOWTOPLEVELMGS", rs.getString("SHOWTOPLEVELMGS"));
/*  913 */         String toplevelsubmg = rs.getString("SHOWTOPLEVELSUBMGS");
/*  914 */         if (toplevelsubmg == null) {
/*  915 */           toplevelsubmg = "false";
/*      */         }
/*  917 */         view.setProperty("SHOWTOPLEVELSUBMGS", toplevelsubmg);
/*  918 */         return view;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  923 */       ex.printStackTrace();
/*      */     }
/*  925 */     return view;
/*      */   }
/*      */   
/*      */   public ArrayList getFilterList(String viewid)
/*      */   {
/*  930 */     ArrayList filterlist = new ArrayList();
/*      */     try {
/*  932 */       String query = "select HAID from AM_FLASHVIEW_FILTER where  VIEWID=" + viewid;
/*  933 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  934 */       while (rs.next())
/*      */       {
/*  936 */         filterlist.add(rs.getString("HAID"));
/*      */       }
/*  938 */       rs.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  942 */       ex.printStackTrace();
/*      */     }
/*  944 */     return filterlist;
/*      */   }
/*      */   
/*      */   public ActionForward getLatestStatusForJIT(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  950 */     response.setContentType("text/json;charset=UTF-8");
/*  951 */     PrintWriter out = response.getWriter();
/*      */     
/*  953 */     ManagedApplication mo = new ManagedApplication();
/*  954 */     String resources = request.getParameter("resourceIDs");
/*  955 */     Vector resourceids = new Vector();
/*  956 */     JSONObject rootNode = new JSONObject();
/*  957 */     String haid = request.getParameter("haid");
/*  958 */     String viewid = request.getParameter("viewid");
/*  959 */     Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/*      */     
/*  961 */     Properties view = BusinessViewUtil.getViewforViewid(viewid);
/*  962 */     String statusinterval = view.getProperty("STATUSUPDATEINTERVAL");
/*  963 */     rootNode = BusinessViewUtil.getLatestStatusForBusinessView(healthkeys, resources, haid, viewid, false);
/*  964 */     out.print(rootNode);
/*  965 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getLatestStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  971 */     PrintWriter out = response.getWriter();
/*  972 */     response.setContentType("text/xml;charset=UTF-8");
/*  973 */     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/*  974 */     DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/*  975 */     Document doc = docBuilder.newDocument();
/*  976 */     ManagedApplication mo = new ManagedApplication();
/*  977 */     Vector resourceids = new Vector();
/*  978 */     String haid = request.getParameter("haid");
/*  979 */     String viewid = request.getParameter("viewid");
/*  980 */     Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/*  981 */     StringBuilder query = new StringBuilder("select AM_ManagedObject.RESOURCEID ,SEVERITY ,MMESSAGE,CATEGORY  from Alert inner join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type  WHERE   GROUPNAME='AppManager'");
/*      */     
/*  983 */     long defaultOldTime = System.currentTimeMillis() - 60000L;
/*  984 */     ArrayList<String> mappedMGs = new ArrayList();
/*      */     
/*  986 */     if (haid.equals("0"))
/*      */     {
/*  988 */       if ((viewid != null) && (!viewid.equals("-1")))
/*      */       {
/*  990 */         mappedMGs = getFilterList(viewid);
/*  991 */         for (String mg : mappedMGs)
/*      */         {
/*  993 */           mo.getAllChildsinTree(resourceids, mg);
/*      */         }
/*  995 */         resourceids.addAll(mappedMGs);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1000 */       mo.getAllChildsinTree(resourceids, haid);
/* 1001 */       resourceids.add(haid);
/*      */     }
/*      */     
/* 1004 */     if ((viewid != null) && (!viewid.equals("-1")))
/*      */     {
/*      */       try
/*      */       {
/* 1008 */         long interval = 600000L;
/* 1009 */         Properties view = BusinessViewUtil.getViewforViewid(viewid);
/* 1010 */         String statusinterval = view.getProperty("STATUSUPDATEINTERVAL");
/* 1011 */         if (statusinterval != null)
/*      */         {
/* 1013 */           interval = Long.parseLong(statusinterval);
/* 1014 */           defaultOldTime = System.currentTimeMillis() - (2L + interval) * 1000L;
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1019 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 1023 */     query = query.append(" and Alert.MODTIME > " + defaultOldTime).append(resourceids.size() > 0 ? " and " + DependantMOUtil.getCondition("SOURCE", resourceids) : "");
/* 1024 */     AMLog.debug("GraphicalView.getLatestStatus() query : " + query.toString());
/* 1025 */     Element rootNode = doc.createElement("RefreshedStatus");
/* 1026 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1029 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query.toString());
/* 1030 */       while (rs.next())
/*      */       {
/* 1032 */         String attributeid = rs.getString("CATEGORY");
/* 1033 */         if (healthkeys.containsValue(attributeid))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1038 */           String resourceid = rs.getString("RESOURCEID");
/* 1039 */           String severity = rs.getString("SEVERITY");
/* 1040 */           String message = rs.getString("MMESSAGE");
/* 1041 */           Element node = doc.createElement("Monitor");
/* 1042 */           node.setAttribute("id", resourceid);
/* 1043 */           node.setAttribute("severity", severity);
/* 1044 */           node.setAttribute("message", message);
/* 1045 */           rootNode.appendChild(node);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1050 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1054 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1056 */     doc.appendChild(rootNode);
/* 1057 */     Source source = new DOMSource(doc);
/* 1058 */     Result result = new StreamResult(out);
/* 1059 */     Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 1060 */     transformer.setOutputProperty("method", "xml");
/* 1061 */     transformer.setOutputProperty("encoding", "ISO-8859-1");
/* 1062 */     transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "20");
/* 1063 */     transformer.setOutputProperty("indent", "yes");
/* 1064 */     transformer.transform(source, result);
/* 1065 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward showEditView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1072 */     String viewid = "-1";
/* 1073 */     String haid = "-1";
/* 1074 */     viewid = request.getParameter("viewid");
/* 1075 */     haid = request.getParameter("haid");
/* 1076 */     boolean fromMonitorTab = false;
/* 1077 */     boolean customizetab = false;
/* 1078 */     FlashForm fm = (FlashForm)form;
/* 1079 */     String fromWhere = request.getParameter("fromWhere");
/* 1080 */     if (haid.equals("0"))
/*      */     {
/* 1082 */       fromMonitorTab = true;
/*      */     }
/* 1084 */     if (request.getParameter("isPopUp") != null)
/*      */     {
/* 1086 */       fm.setPopUp(true);
/*      */     }
/* 1088 */     if ((fromWhere != null) && (fromWhere.equals("hometab")))
/*      */     {
/* 1090 */       fm.setFromWhere("hometab");
/*      */     }
/* 1092 */     if ((request.getParameter("customizetab") != null) && (request.getParameter("customizetab").equals("true")))
/*      */     {
/* 1094 */       customizetab = true;
/*      */     }
/* 1096 */     fm.setFromMonitorTab(fromMonitorTab);
/*      */     try
/*      */     {
/* 1099 */       String query = "select * from AM_MONITORGROUP_FLASHVIEWCONFIG where VIEWID=" + viewid;
/* 1100 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 1101 */       if (rs.next())
/*      */       {
/* 1103 */         fm.setDisplayName(rs.getString("DISPLAYNAME"));
/* 1104 */         fm.setDescription(rs.getString("DESCRIPTION"));
/* 1105 */         fm.setBgColor(rs.getString("BGCOLOR"));
/* 1106 */         fm.setLineColor(rs.getString("LINECOLOR").replaceFirst("0x", "#"));
/* 1107 */         fm.setLabelColor(rs.getString("LABELCOLOR").replaceFirst("0x", "#"));
/* 1108 */         fm.setShowLabel(Boolean.valueOf(rs.getString("SHOWLABEL")).booleanValue());
/*      */         
/* 1110 */         fm.setLinethickness(rs.getFloat("LINETHICKNESS"));
/* 1111 */         fm.setLineTransparency(rs.getFloat("LINETRANSPARENCY"));
/* 1112 */         fm.setReloadInterval(rs.getInt("RELOADINTERVAL") / 60);
/* 1113 */         fm.setRefreshInterval(rs.getInt("STATUSUPDATEINTERVAL") / 60);
/* 1114 */         fm.setShowCriticalMonitors(Boolean.valueOf(rs.getString("SHOWCRITICALMONITORS")).booleanValue());
/* 1115 */         fm.setShowOnlySubgroups(Boolean.valueOf(rs.getString("SHOWONLYSUBGROUPS")).booleanValue());
/* 1116 */         fm.setShowTopLevelMgs(Boolean.valueOf(rs.getString("SHOWTOPLEVELMGS")).booleanValue());
/* 1117 */         fm.setShowTopLevelSubMgs(Boolean.valueOf(rs.getString("SHOWTOPLEVELSUBMGS")).booleanValue());
/* 1118 */         fm.setNoOfColumns(rs.getInt("NOOFCOLUMNS"));
/* 1119 */         String selected = rs.getString("SELECTED");
/* 1120 */         String haidFortheView = rs.getString("HAID");
/* 1121 */         fm.setIsHtml(rs.getBoolean("ISHTML"));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1126 */         fm.setLineColor(fm.getLineColor().replaceFirst("0x", "#"));
/* 1127 */         fm.setLabelColor(fm.getLabelColor().replaceFirst("0x", "#"));
/* 1128 */         fm.setShowLabel(true);
/* 1129 */         fm.setLinethickness(2.0F);
/* 1130 */         fm.setLineTransparency(1.0F);
/*      */       }
/* 1132 */       rs.close();
/* 1133 */       if (fromMonitorTab)
/*      */       {
/* 1135 */         ManagedApplication mo = new ManagedApplication();
/* 1136 */         String bsgFilterCondn = "";
/* 1137 */         String bsgType = "0";
/* 1138 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 1140 */           bsgType = "1";
/* 1141 */           Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/* 1142 */           bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*      */         }
/* 1144 */         String mgquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + " order by AM_ManagedObject.DISPLAYNAME";
/* 1145 */         if ((ClientDBUtil.isPrivilegedUser(request)) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */         {
/* 1147 */           String userid = mo.getUserID(request);
/* 1148 */           mgquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject,AM_HOLISTICAPPLICATION_OWNERS where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=0  and AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=" + userid + " order by AM_ManagedObject.DISPLAYNAME";
/*      */         }
/* 1150 */         ArrayList availableMgs = mo.getRows(mgquery);
/* 1151 */         fm.setAvailableMgs(availableMgs);
/* 1152 */         fm.setFromMonitorTab(true);
/*      */         
/* 1154 */         String filterquery = "select HAID from AM_FLASHVIEW_FILTER where VIEWID=" + viewid;
/*      */         
/* 1156 */         ArrayList filterlist = mo.getRows(filterquery);
/* 1157 */         String[] mgs = new String[filterlist.size()];
/* 1158 */         for (int i = 0; i < filterlist.size(); i++)
/*      */         {
/* 1160 */           ArrayList singleRow = (ArrayList)filterlist.get(i);
/* 1161 */           mgs[i] = ((String)singleRow.get(0));
/*      */         }
/* 1163 */         fm.setMonitorGroups(mgs);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1168 */       ex.printStackTrace();
/*      */     }
/* 1170 */     return new ActionForward("/jsp/FlashViewEdit.jsp?viewid=" + viewid + "&haid=" + haid + "&customizetab=" + customizetab);
/*      */   }
/*      */   
/*      */   public ActionForward getDisplayPropsForGraph(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1175 */     String viewid = "-1";
/* 1176 */     String haid = "-1";
/*      */     try {
/* 1178 */       viewid = request.getParameter("viewid");
/* 1179 */       haid = request.getParameter("haid");
/* 1180 */       response.setContentType("text/json;charset=UTF-8");
/* 1181 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 1182 */       JSONObject displayProps = bUtil.getAllGraphProperties(viewid);
/* 1183 */       PrintWriter out = response.getWriter();
/* 1184 */       AMLog.debug("Display properties==>" + displayProps);
/* 1185 */       out.println(displayProps);
/* 1186 */       out.flush();
/* 1187 */       out.close();
/*      */     } catch (Exception e) {
/* 1189 */       e.printStackTrace();
/*      */     }
/* 1191 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward saveEditView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1196 */     FlashForm fm = (FlashForm)form;
/* 1197 */     viewid = request.getParameter("viewid");
/* 1198 */     haidForDetailsPage = request.getParameter("haid");
/* 1199 */     String haid = haidForDetailsPage;
/* 1200 */     createNew = false;
/* 1201 */     customizetab = false;
/* 1202 */     String selected = "1";
/* 1203 */     BusinessViewUtil.deleteBussinessViewCache();
/* 1204 */     if (request.getParameter("createnewview") != null)
/*      */     {
/* 1206 */       createNew = true;
/* 1207 */       haid = "0";
/*      */     }
/* 1209 */     if ((request.getParameter("customizetab") != null) && (request.getParameter("customizetab").equals("true")))
/*      */     {
/* 1211 */       customizetab = true;
/*      */     }
/* 1213 */     if (fm.isFromMonitorTab())
/*      */     {
/* 1215 */       if (!viewid.equals("-1"))
/*      */       {
/*      */ 
/* 1218 */         selected = "0";
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/* 1223 */       String displayName = fm.getDisplayName();
/* 1224 */       String description = fm.getDescription();
/* 1225 */       String bgColor = fm.getBgColor();
/* 1226 */       String lineColor = fm.getLineColor();
/* 1227 */       String labelColor = fm.getLabelColor();
/* 1228 */       boolean isHtml = fm.getIsHtml();
/* 1229 */       boolean showLabel = fm.isShowLabel();
/* 1230 */       float linethickness = fm.getLinethickness();
/* 1231 */       float lineTransparency = fm.getLineTransparency();
/* 1232 */       int reloadInterval = 60 * fm.getReloadInterval();
/* 1233 */       int refreshInterval = 60 * fm.getRefreshInterval();
/* 1234 */       boolean showCriticalMonitors = fm.isShowCriticalMonitors();
/* 1235 */       boolean showOnlySubgroups = fm.isShowOnlySubgroups();
/* 1236 */       boolean showTopLevelMgs = fm.isShowTopLevelMgs();
/* 1237 */       boolean showTopLevelSubMgs = fm.isShowTopLevelSubMgs();
/* 1238 */       int noOfColumns = fm.getNoOfColumns();
/*      */       
/* 1240 */       String query = "select * from AM_MONITORGROUP_FLASHVIEWCONFIG where VIEWID=" + viewid;
/* 1241 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 1242 */       if ((rs.next()) && (!createNew))
/*      */       {
/* 1244 */         String updatequery = "update  AM_MONITORGROUP_FLASHVIEWCONFIG set DISPLAYNAME='" + displayName + "' where VIEWID=" + viewid;
/*      */         
/* 1246 */         AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(updatequery);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1251 */         ManagedApplication mo = new ManagedApplication();
/* 1252 */         String userid = mo.getUserID(request);
/* 1253 */         AMConnectionPool pool = AMConnectionPool.getInstance();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1260 */         int viewid_int = DBQueryUtil.getIncrementedID("VIEWID", "AM_MONITORGROUP_FLASHVIEWCONFIG");
/* 1261 */         viewid = String.valueOf(viewid_int);
/* 1262 */         String querytoinsert = "insert into AM_MONITORGROUP_FLASHVIEWCONFIG(VIEWID,USERID,HAID,ZOOMLEVEL,SELECTED,DISPLAYNAME,DESCRIPTION,LINECOLOR,LINETHICKNESS,LABELCOLOR,BGCOLOR,STATUSUPDATEINTERVAL,RELOADINTERVAL,SHOWLABEL,LINETRANSPARENCY,SHOWCRITICALMONITORS,SHOWONLYSUBGROUPS,SHOWTOPLEVELMGS,NOOFCOLUMNS,SHOWTOPLEVELSUBMGS,XCANVAS,YCANVAS,ISHTML) Values(" + viewid + "," + userid + "," + haid + ",1," + selected + ",'" + displayName + "','" + description + "','" + lineColor + "'," + linethickness + ",'" + labelColor + "','" + bgColor + "'," + refreshInterval + "," + reloadInterval + ",'" + showLabel + "'," + lineTransparency + ",'" + showCriticalMonitors + "','" + showOnlySubgroups + "','" + showTopLevelMgs + "','" + noOfColumns + "','" + showTopLevelSubMgs + "',-47.551020408163261,0,'" + isHtml + "')";
/* 1263 */         AMConnectionPool.executeUpdateStmt(querytoinsert);
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
/* 1275 */       rs.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1279 */       ex.printStackTrace();
/*      */     }
/* 1281 */     if (fm.isFromMonitorTab())
/*      */     {
/* 1283 */       Statement statement = null;
/*      */       try {
/* 1285 */         AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("delete from AM_FLASHVIEW_FILTER where VIEWID=" + viewid);
/* 1286 */         AMConnectionPool.getInstance();statement = AMConnectionPool.getConnection().createStatement();
/* 1287 */         String[] monitorGroups = fm.getMonitorGroups();
/* 1288 */         for (int i = 0; i < monitorGroups.length; i++)
/*      */         {
/*      */ 
/* 1291 */           statement.addBatch("insert into AM_FLASHVIEW_FILTER(VIEWID,HAID) values(" + viewid + "," + monitorGroups[i] + ")");
/*      */         }
/* 1293 */         statement.executeBatch();
/* 1294 */         statement.clearBatch();
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
/* 1306 */         return new ActionForward("/jsp/FlashViewEdit.jsp?configurationsaved=true&haid=" + haidForDetailsPage + "&viewid=" + viewid + "&createNew=" + createNew + "&customizetab=" + customizetab);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1298 */         ex.printStackTrace();
/*      */       }
/*      */       finally {
/*      */         try {
/* 1302 */           statement.close();
/*      */         }
/*      */         catch (Exception ex) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward createNewView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1312 */     boolean isHtml = request.getParameter("isHtml") != null ? Boolean.valueOf(request.getParameter("isHtml")).booleanValue() : true;
/*      */     
/* 1314 */     ManagedApplication mo = new ManagedApplication();
/* 1315 */     String haid = request.getParameter("haid");
/* 1316 */     String fromWhere = request.getParameter("fromWhere");
/* 1317 */     FlashForm fm = (FlashForm)form;
/* 1318 */     if (request.getParameter("isPopUp") != null)
/*      */     {
/* 1320 */       fm.setPopUp(true);
/*      */     }
/* 1322 */     String bsgFilterCondn = "";
/* 1323 */     String bsgType = "0";
/* 1324 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/* 1326 */       bsgType = "1";
/* 1327 */       Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/* 1328 */       bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*      */     }
/* 1330 */     String mgquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn;
/* 1331 */     if ((ClientDBUtil.isPrivilegedUser(request)) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 1333 */       String userid = mo.getUserID(request);
/* 1334 */       mgquery = " select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject,AM_HOLISTICAPPLICATION_OWNERS where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=0  and AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=" + userid + " order by AM_ManagedObject.DISPLAYNAME";
/*      */     }
/* 1336 */     ArrayList availableMgs = mo.getRows(mgquery);
/* 1337 */     if (isHtml) {
/* 1338 */       fm.setLineColor("#ECECEC");
/* 1339 */       fm.setLabelColor("#444444");
/* 1340 */       fm.setLinethickness(2.0F);
/* 1341 */       fm.setLineTransparency(1.0F);
/*      */     } else {
/* 1343 */       fm.setLineColor(fm.getLineColor().replaceFirst("0x", "#"));
/* 1344 */       fm.setLabelColor(fm.getLabelColor().replaceFirst("0x", "#"));
/*      */     }
/* 1346 */     fm.setAvailableMgs(availableMgs);
/* 1347 */     fm.setFromMonitorTab(true);
/* 1348 */     fm.setShowLabel(true);
/* 1349 */     if ((fromWhere != null) && (fromWhere.equals("hometab")))
/*      */     {
/* 1351 */       fm.setFromWhere("hometab");
/*      */     }
/* 1353 */     return new ActionForward("/jsp/FlashViewEdit.jsp?createnewview=true&haid=" + haid);
/*      */   }
/*      */   
/*      */   public ActionForward setAsDefaultView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1359 */     String viewid = request.getParameter("viewid");
/* 1360 */     String haid = request.getParameter("haid");
/* 1361 */     String fromWhere = request.getParameter("fromWhere");
/* 1362 */     String isPopUp = "&isPopUp=true";
/*      */     try
/*      */     {
/* 1365 */       ManagedApplication mo = new ManagedApplication();
/* 1366 */       String userid = mo.getUserID(request);
/* 1367 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 1368 */       AMConnectionPool.executeUpdateStmt("update  AM_MONITORGROUP_FLASHVIEWCONFIG set SELECTED=0 where HAID=" + haid + " and USERID=" + userid);
/* 1369 */       AMConnectionPool.executeUpdateStmt("update  AM_MONITORGROUP_FLASHVIEWCONFIG set SELECTED=1 where VIEWID=" + viewid);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1373 */       ex.printStackTrace();
/*      */     }
/* 1375 */     if ((fromWhere != null) && (fromWhere.equals("hometab")))
/*      */     {
/* 1377 */       return new ActionForward("/applications.do?selectTab=MGStatus&viewid=" + viewid + isPopUp);
/*      */     }
/* 1379 */     if ((request.getParameter("customizetab") != null) && (request.getParameter("customizetab").equals("true")))
/*      */     {
/* 1381 */       return new ActionForward("/GraphicalView.do?method=popUp&customizetab=true&haid=" + haid + "&viewid=" + viewid);
/*      */     }
/*      */     
/*      */ 
/* 1385 */     return new ActionForward("/GraphicalView.do?method=popUp&haid=" + haid + "&viewid=" + viewid + isPopUp);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1394 */     String viewid = request.getParameter("viewid");
/* 1395 */     String haid = request.getParameter("haid");
/* 1396 */     String fromWhere = request.getParameter("fromWhere");
/* 1397 */     String isPopUp = "&isPopUp=true";
/* 1398 */     BusinessViewUtil bvUtil = new BusinessViewUtil();
/*      */     try
/*      */     {
/* 1401 */       ClientAuditUtil clientAuditUtil = new ClientAuditUtil();
/* 1402 */       clientAuditUtil.setDetailsForAuditInThreadLocal(request);
/*      */       
/*      */ 
/* 1405 */       JSONObject result = new JSONObject();
/* 1406 */       response.setContentType("text/json; charset=UTF-8");
/* 1407 */       PrintWriter out = response.getWriter();
/* 1408 */       boolean isDeleted = bvUtil.deleteView(viewid);
/* 1409 */       if (isDeleted)
/*      */       {
/* 1411 */         BusinessViewUtil.deleteBussinessViewCache(viewid + "_" + haid);
/* 1412 */         AMLog.debug("Deleted Business view::" + viewid);
/*      */       }
/* 1414 */       result.put("isDeleted", isDeleted);
/* 1415 */       out.println(result);
/* 1416 */       out.flush();
/* 1417 */       out.close();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1422 */       ex.printStackTrace();
/*      */     }
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
/* 1436 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getAvailableViews(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1442 */     String haid = request.getParameter("haid");
/* 1443 */     BusinessViewUtil bvUtil = new BusinessViewUtil();
/*      */     try {
/* 1445 */       response.setContentType("text/json; charset=UTF-8");
/* 1446 */       PrintWriter out = response.getWriter();
/* 1447 */       JSONObject result = bvUtil.getAvailableViews(haid);
/* 1448 */       out.println(result);
/* 1449 */       out.flush();
/* 1450 */       out.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1454 */       e.printStackTrace();
/*      */     }
/* 1456 */     return new ActionForward(null);
/*      */   }
/*      */   
/*      */   public ActionForward popUp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1460 */     AMLog.debug("Inside pop up");
/* 1461 */     String viewid = "-1";
/* 1462 */     String haid = request.getParameter("haid");
/* 1463 */     String selectedview = "-1";
/* 1464 */     String bvName = URLEncoder.encode(FormatUtil.getString("am.webclient.flashview.displayname"));
/*      */     
/* 1466 */     FlashForm fm = (FlashForm)form;
/* 1467 */     fm.setPopUp(true);
/* 1468 */     request.setAttribute("displayAvailableViews", "true");
/* 1469 */     if (request.getParameter("viewid") != null)
/*      */     {
/* 1471 */       viewid = request.getParameter("viewid");
/* 1472 */       selectedview = viewid;
/*      */     }
/* 1474 */     if (request.getParameter("publishview") != null)
/*      */     {
/* 1476 */       request.setAttribute("publishview", "true");
/* 1477 */       request.setAttribute("displayAvailableViews", "false");
/*      */     }
/* 1479 */     String hideControls = request.getParameter("hideControls");
/* 1480 */     if (hideControls == null)
/*      */     {
/* 1482 */       hideControls = "false";
/*      */     }
/* 1484 */     ManagedApplication mo = new ManagedApplication();
/* 1485 */     String userid = mo.getUserID(request);
/* 1486 */     ArrayList availableViews = new ArrayList();
/*      */     
/* 1488 */     List<String> availableViewsInList = new ArrayList();
/* 1489 */     JSONObject availableViewsInMap = new JSONObject();
/* 1490 */     boolean consoleHomeView = false;
/* 1491 */     boolean isHtml = request.getParameter("isHtml") != null ? Boolean.valueOf(request.getParameter("isHtml")).booleanValue() : true;
/* 1492 */     if ((request.getParameter("consoleHome") != null) && (request.getParameter("consoleHome").equalsIgnoreCase("true")))
/*      */     {
/* 1494 */       consoleHomeView = true;
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1499 */       String query1 = "";
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1508 */       if (!viewid.equals("-1")) {
/* 1509 */         query1 = "select VIEWID,ZOOMLEVEL,SELECTED,DISPLAYNAME,DESCRIPTION,LINECOLOR,LINETHICKNESS,LABELCOLOR,BGCOLOR,STATUSUPDATEINTERVAL,RELOADINTERVAL,SHOWLABEL,LINETRANSPARENCY,SHOWCRITICALMONITORS,SHOWONLYSUBGROUPS,SHOWTOPLEVELMGS,NOOFCOLUMNS,XCANVAS,YCANVAS,ISHTML from AM_MONITORGROUP_FLASHVIEWCONFIG where viewid=" + viewid;
/*      */         
/* 1511 */         ResultSet rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 1512 */         if (rs1.next())
/*      */         {
/* 1514 */           viewid = rs1.getString("VIEWID");
/* 1515 */           isHtml = rs1.getBoolean("ISHTML");
/* 1516 */           selectedview = viewid;
/* 1517 */           fm.setDisplayName(rs1.getString("DISPLAYNAME"));
/* 1518 */           fm.setBgColor(rs1.getString("BGCOLOR"));
/* 1519 */           if (isHtml) {
/* 1520 */             fm.setLineColor(rs1.getString("LINECOLOR"));
/* 1521 */             fm.setLabelColor(rs1.getString("LABELCOLOR"));
/* 1522 */             AMLog.debug("xcanvas:==>" + rs1.getFloat("XCANVAS"));
/* 1523 */             fm.setXcanvas(rs1.getFloat("XCANVAS"));
/* 1524 */             fm.setYcanvas(rs1.getFloat("YCANVAS"));
/*      */           } else {
/* 1526 */             fm.setLineColor(rs1.getString("LINECOLOR").replaceFirst("#", "0x"));
/* 1527 */             fm.setLabelColor(rs1.getString("LABELCOLOR").replaceFirst("#", "0x"));
/*      */           }
/*      */           
/* 1530 */           fm.setLinethickness(rs1.getFloat("LINETHICKNESS"));
/* 1531 */           fm.setLineTransparency(rs1.getFloat("LINETRANSPARENCY"));
/* 1532 */           fm.setReloadInterval(rs1.getInt("RELOADINTERVAL"));
/* 1533 */           fm.setRefreshInterval(rs1.getInt("STATUSUPDATEINTERVAL") * 1000);
/* 1534 */           fm.setShowCriticalMonitors(Boolean.valueOf(rs1.getString("SHOWCRITICALMONITORS")).booleanValue());
/* 1535 */           fm.setShowOnlySubgroups(Boolean.valueOf(rs1.getString("SHOWONLYSUBGROUPS")).booleanValue());
/* 1536 */           fm.setShowTopLevelMgs(Boolean.valueOf(rs1.getString("SHOWTOPLEVELMGS")).booleanValue());
/* 1537 */           fm.setNoOfColumns(rs1.getInt("NOOFCOLUMNS"));
/* 1538 */           request.setAttribute("reloadperiod", rs1.getString("RELOADINTERVAL"));
/* 1539 */           AMLog.debug("Inside  pop up setting 1" + isHtml);
/* 1540 */           fm.setIsHtml(isHtml);
/*      */         }
/*      */         
/*      */ 
/* 1544 */         closeResultSet(rs1);
/*      */       }
/*      */       else
/*      */       {
/* 1548 */         fm.setReloadInterval(fm.getReloadInterval() * 60);
/* 1549 */         fm.setRefreshInterval(fm.getRefreshInterval() * 60 * 1000);
/* 1550 */         if (isHtml) {
/* 1551 */           fm.setLineColor("#ECECEC");
/* 1552 */           fm.setLabelColor("#444444");
/* 1553 */           fm.setLinethickness(2.0F);
/* 1554 */           fm.setLineTransparency(1.0F);
/*      */         } else {
/* 1556 */           fm.setLineColor("#000000");
/*      */         }
/* 1558 */         AMLog.debug("Inside  pop up setting 2" + isHtml);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1564 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 1568 */     if (haid.equals("0"))
/*      */     {
/* 1570 */       fm.setFromMonitorTab(true);
/* 1571 */       if (!viewid.equals("-1"))
/*      */       {
/* 1573 */         String viewquery = "select VIEWID,DISPLAYNAME from AM_MONITORGROUP_FLASHVIEWCONFIG where HAID=" + haid + " and USERID=" + userid + " order by DISPLAYNAME";
/* 1574 */         AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(viewquery);
/* 1575 */         while (rs.next())
/*      */         {
/* 1577 */           Properties dataProps = new Properties();
/* 1578 */           dataProps.setProperty("label", rs.getString("DISPLAYNAME"));
/* 1579 */           bvName = rs.getString("DISPLAYNAME");
/* 1580 */           dataProps.setProperty("value", rs.getString("VIEWID"));
/* 1581 */           availableViews.add(dataProps);
/* 1582 */           availableViewsInList.add(rs.getString("VIEWID") + "#" + rs.getString("DISPLAYNAME"));
/*      */         }
/* 1584 */         closeResultSet(rs);
/*      */       }
/* 1586 */       if (consoleHomeView)
/*      */       {
/* 1588 */         fm.setShowTopLevelMgs(true);
/*      */       }
/* 1590 */       if (fm.isShowTopLevelMgs())
/*      */       {
/* 1592 */         HashMap displayNames = new HashMap();
/* 1593 */         String filterquery = "select HAID,AM_ManagedObject.DISPLAYNAME from AM_FLASHVIEW_FILTER,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_FLASHVIEW_FILTER.HAID and VIEWID=" + viewid + " order by DISPLAYNAME";
/* 1594 */         ArrayList filterlist = mo.getRows(filterquery);
/* 1595 */         if (filterlist.size() <= 0)
/*      */         {
/* 1597 */           filterquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=0  order by DISPLAYNAME";
/* 1598 */           filterlist = mo.getRows(filterquery);
/*      */         }
/*      */         
/* 1601 */         String[] mgs = new String[filterlist.size()];
/* 1602 */         for (int i = 0; i < filterlist.size(); i++)
/*      */         {
/* 1604 */           ArrayList singleRow = (ArrayList)filterlist.get(i);
/* 1605 */           mgs[i] = ((String)singleRow.get(0));
/* 1606 */           displayNames.put(mgs[i], singleRow.get(1));
/*      */         }
/* 1608 */         request.setAttribute("displayNames", displayNames);
/* 1609 */         fm.setMonitorGroups(mgs);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1614 */       String displayNameQuery = "select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + haid;
/* 1615 */       ResultSet rs2 = AMConnectionPool.executeQueryStmt(displayNameQuery);
/* 1616 */       if (rs2.next())
/*      */       {
/* 1618 */         request.setAttribute("mgDisplayName", rs2.getString("DISPLAYNAME"));
/* 1619 */         bvName = rs2.getString("DISPLAYNAME");
/*      */       }
/* 1621 */       closeResultSet(rs2);
/*      */     }
/* 1623 */     fm.setSelectedView(selectedview);
/* 1624 */     fm.setAvailableViews(availableViews);
/* 1625 */     request.setAttribute("FlashForm", fm);
/* 1626 */     request.setAttribute("viewid", viewid);
/* 1627 */     request.setAttribute("availableViewsInList", availableViewsInList);
/* 1628 */     String customizetab = request.getParameter("customizetab");
/* 1629 */     request.setAttribute("customizetab", customizetab);
/* 1630 */     if (request.getParameter("readOnly") != null)
/*      */     {
/* 1632 */       request.setAttribute("isReadOnly", request.getParameter("readOnly"));
/*      */     }
/*      */     else
/*      */     {
/* 1636 */       request.setAttribute("isReadOnly", "false");
/*      */     }
/*      */     
/* 1639 */     if ((bvName != null) && (!bvName.isEmpty()))
/*      */     {
/* 1641 */       AMLog.debug("##BusinessView:: DisplayName::Inside isempty checkkk:::" + bvName);
/* 1642 */       request.setAttribute("bvName", bvName);
/*      */     }
/* 1644 */     request.setAttribute("haid", haid);
/* 1645 */     if (customizetab != null) {
/* 1646 */       return new ActionForward("/jsp/FlashViewFrmTab.jsp?haid=" + haid + "&viewid=" + viewid + "&hideControls=" + hideControls);
/*      */     }
/* 1648 */     if (hideControls.equals("false"))
/*      */     {
/* 1650 */       return new ActionForward("/jsp/ShowRelationshipView.jsp?haid=" + haid + "&viewid=" + viewid + "&hideControls=" + hideControls);
/*      */     }
/* 1652 */     return new ActionForward("/jsp/ShowRelationshipView.jsp?haid=" + haid + "&isPopUp=" + request.getParameter("isPopUp") + "&viewid=" + viewid + "&hideControls=" + hideControls);
/*      */   }
/*      */   
/*      */   public ActionForward homeTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1657 */     String viewid = "-1";
/* 1658 */     String haid = request.getParameter("haid");
/* 1659 */     FlashForm fm = (FlashForm)form;
/* 1660 */     fm.setFromWhere("hometab");
/* 1661 */     popUp(mapping, form, request, response);
/*      */     
/* 1663 */     if (request.getAttribute("viewid") != null)
/*      */     {
/* 1665 */       viewid = (String)request.getAttribute("viewid");
/*      */     }
/* 1667 */     return new ActionForward("/jsp/ShowRelationshipView.jsp?fromhomepage=true&haid=" + haid + "&viewid=" + viewid);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getGraphAccordingToType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1674 */     Properties view = new Properties();
/* 1675 */     BusinessViewUtil businessview = new BusinessViewUtil();
/* 1676 */     String showCritical = request.getParameter("showCritical");
/* 1677 */     String showTopOnly = request.getParameter("showTopOnly");
/* 1678 */     String all = request.getParameter("all");
/* 1679 */     String showGroups = request.getParameter("showGroups");
/* 1680 */     AMLog.debug("DDDDDDDD #####" + request.getParameter("displayProps"));
/* 1681 */     String viewId = request.getParameter("viewid");
/* 1682 */     String haid = request.getParameter("haid");
/*      */     
/*      */     try
/*      */     {
/* 1686 */       if (("0".equals(viewId)) && (!"0".equals(haid)))
/*      */       {
/* 1688 */         viewId = String.valueOf(businessview.getViewIdForExistingBV(haid));
/*      */       }
/*      */       
/* 1691 */       String displayPropsString = request.getParameter("displayProps");
/* 1692 */       displayPropsString = businessview.decodeSpecialCharactersFromJSONString(displayPropsString);
/* 1693 */       JSONObject dispProps = new JSONObject(displayPropsString);
/* 1694 */       String displayName = dispProps.getString("displayName");
/* 1695 */       boolean updated = Boolean.FALSE.booleanValue();
/* 1696 */       if ("-1".equals(viewId)) {
/* 1697 */         ManagedApplication mo = new ManagedApplication();
/* 1698 */         String userid = mo.getUserID(request);
/* 1699 */         businessview.saveViewConfig(dispProps, userid, haid);
/*      */       } else {
/* 1701 */         view.setProperty("DISPLAYNAME", displayName);
/* 1702 */         view.setProperty("SHOWCRITICALMONITORS", showCritical);
/* 1703 */         view.setProperty("SHOWTOPLEVELSUBMGS", showTopOnly);
/* 1704 */         view.setProperty("SHOWALLMONITORS", all);
/* 1705 */         view.setProperty("SHOWONLYSUBGROUPS", showGroups);
/* 1706 */         view.setProperty("SHOWLABEL", dispProps.getString("showLabel"));
/* 1707 */         AMLog.debug("DDD DISP PROPS " + dispProps);
/* 1708 */         view.setProperty("BGCOLOR", dispProps.getString("backgroundColorVal"));
/* 1709 */         view.setProperty("LINECOLOR", dispProps.getString("lineColorVal"));
/* 1710 */         view.setProperty("LABELCOLOR", dispProps.getString("textColorVal"));
/* 1711 */         view.setProperty("LINETHICKNESS", dispProps.getString("lineThickness"));
/* 1712 */         view.setProperty("LINETRANSPARENCY", dispProps.getString("lineTransparency"));
/* 1713 */         BusinessViewUtil.updateViewPropsInDB(viewId, view);
/*      */       }
/* 1715 */       BusinessViewUtil.deleteBussinessViewCache(viewId + "_" + haid);
/*      */     } catch (JSONException e) {
/* 1717 */       e.printStackTrace();
/*      */     }
/* 1719 */     return new ActionForward("/GraphicalView.do?method=getMGXML&haid=" + haid + "&viewid=" + viewId + "&isHtml=true");
/*      */   }
/*      */   
/*      */   public ActionForward getDataForGlobalView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1724 */       String viewid = "-1";
/* 1725 */       if (request.getParameter("viewid") != null) {
/* 1726 */         viewid = request.getParameter("viewid");
/*      */       }
/* 1728 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 1729 */       int noOfColumns = bUtil.getNoOfColumnsForGlobalView(viewid);
/* 1730 */       JSONObject dataForGlobalView = bUtil.getDataForGlobalView(viewid, noOfColumns);
/* 1731 */       response.setContentType("text/json;charset=UTF-8");
/* 1732 */       PrintWriter out = response.getWriter();
/* 1733 */       out.println(dataForGlobalView);
/* 1734 */       out.flush();
/* 1735 */       out.close();
/*      */     } catch (Exception e) {
/* 1737 */       e.printStackTrace();
/*      */     }
/* 1739 */     return null;
/*      */   }
/*      */   
/*      */   public void closeResultSet(ResultSet rs)
/*      */   {
/*      */     try
/*      */     {
/* 1746 */       if (rs != null)
/*      */       {
/* 1748 */         rs.close();
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1753 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private String encodeSpecialCharecters(String mmessage)
/*      */   {
/* 1759 */     String toreturn = mmessage;
/*      */     try
/*      */     {
/* 1762 */       toreturn = removeAllControlCharacter(toreturn);
/*      */       
/* 1764 */       toreturn = toreturn.replaceAll("&", "&amp;");
/* 1765 */       toreturn = toreturn.replaceAll("<", "&lt;");
/* 1766 */       toreturn = toreturn.replaceAll(">", "&gt;");
/* 1767 */       toreturn = toreturn.replaceAll("'", "&apos;");
/* 1768 */       AMLog.audit("[GraphicalView][encodeSpecialCharecters]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 1779 */       ex.printStackTrace();
/*      */     }
/* 1781 */     return toreturn;
/*      */   }
/*      */   
/*      */   public ActionForward ListSPsAsPerRequirement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1786 */     BusinessViewUtil businessViewUtil = new BusinessViewUtil();
/* 1787 */     JSONObject monitorList = new JSONObject();
/* 1788 */     String monitorsToShow = request.getParameter("monitosToShow");
/* 1789 */     String type = "allMonitors";
/* 1790 */     if ("webServers".equalsIgnoreCase(monitorsToShow)) {
/* 1791 */       monitorList = businessViewUtil.getAllMonitorList(response, "webservers");
/* 1792 */     } else if ("monitorsWithConnection".equalsIgnoreCase(monitorsToShow)) {
/* 1793 */       monitorList = businessViewUtil.getAllMonitorsWithADDMConnections();
/* 1794 */     } else if ("allMonitors".equalsIgnoreCase(monitorsToShow)) {
/* 1795 */       monitorList = businessViewUtil.getAllMonitorList(response, "all");
/*      */     }
/*      */     
/* 1798 */     response.setContentType("text/json;charset=UTF-8");
/* 1799 */     PrintWriter out = response.getWriter();
/*      */     try {
/* 1801 */       out.println(monitorList);
/* 1802 */       out.flush();
/* 1803 */       out.close();
/*      */     } catch (Exception e) {
/* 1805 */       e.printStackTrace();
/*      */     }
/* 1807 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward createBusinessService(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1811 */     BusinessViewUtil businessViewUtil = new BusinessViewUtil();
/* 1812 */     boolean isADDMAddOnEnabled = FreeEditionDetails.getFreeEditionDetails().isADDMAllowed();
/* 1813 */     String addmMessage = FreeEditionDetails.addmMessage != null ? FreeEditionDetails.addmMessage : "";
/* 1814 */     String isFreeEdition = "f".equalsIgnoreCase(FreeEditionDetails.getFreeEditionDetails().getUserType()) ? "true" : "false";
/* 1815 */     JSONObject webServers = businessViewUtil.getAllWebAndUrlServerMonitors();
/* 1816 */     AMLog.debug("Creating Business Service in Action" + webServers);
/* 1817 */     String fromDiscovery = request.getParameter("fromDiscovery") != null ? request.getParameter("fromDiscovery") : "false";
/* 1818 */     request.setAttribute("startingpoints", webServers);
/* 1819 */     request.setAttribute("isADDMAddOnEnabled", Boolean.valueOf(isADDMAddOnEnabled));
/* 1820 */     request.setAttribute("addmMessage", addmMessage);
/* 1821 */     request.setAttribute("isFreeEdition", isFreeEdition);
/* 1822 */     return new ActionForward("/showTile.do?TileName=Tile.NewBusinessService&fromDiscovery=" + fromDiscovery);
/*      */   }
/*      */   
/*      */   public ActionForward drawGraphWithADDM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1826 */     BusinessViewUtil businessVewUtil = new BusinessViewUtil();
/* 1827 */     String groupName = request.getParameter("serviceName");
/* 1828 */     JSONArray resourceList = new JSONArray(request.getParameter("resourceList"));
/* 1829 */     response.setContentType("text/json;charset=UTF-8");
/* 1830 */     PrintWriter out = response.getWriter();
/*      */     
/* 1832 */     businessVewUtil.drawBusinessServiceGraph(groupName, out, resourceList);
/*      */     try
/*      */     {
/* 1835 */       out.flush();
/* 1836 */       out.close();
/*      */     } catch (Exception e) {
/* 1838 */       e.printStackTrace();
/*      */     }
/* 1840 */     return new ActionForward("/jsp/createBusinessService.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward createGroupFromGraph(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1844 */     AMLog.debug("Inside createGroupFromGraph Action" + request.getParameter("graph"));
/* 1845 */     response.setContentType("text/json;charset=UTF-8");
/* 1846 */     String graph = request.getParameter("graph");
/* 1847 */     boolean useADDMForRediscovery = Boolean.valueOf(request.getParameter("useADDM")).booleanValue();
/* 1848 */     BusinessViewUtil businessview = new BusinessViewUtil();
/* 1849 */     graph = businessview.decodeSpecialCharactersFromJSONString(graph);
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
/*      */     try
/*      */     {
/* 1863 */       ClientAuditUtil clientAuditUtil = new ClientAuditUtil();
/* 1864 */       clientAuditUtil.setDetailsForAuditInThreadLocal(request);
/* 1865 */       BusinessViewUtil businessView = new BusinessViewUtil();
/* 1866 */       JSONObject tree = new JSONObject(graph);
/* 1867 */       HashMap nodeIdVsResourceId = new HashMap();
/* 1868 */       String haid = businessView.createMonitorGroupFromGraph(tree, response, nodeIdVsResourceId, useADDMForRediscovery);
/* 1869 */       PrintWriter out = response.getWriter();
/* 1870 */       tree = new JSONObject();
/* 1871 */       if ("-1".equals(haid)) {
/* 1872 */         tree.put("message", "fail");
/*      */       } else {
/* 1874 */         tree.put("message", "success");
/* 1875 */         tree.put("haid", haid);
/* 1876 */         tree.put("nodeIdVsResourceId", nodeIdVsResourceId);
/*      */       }
/* 1878 */       out.print(tree);
/* 1879 */       out.flush();
/* 1880 */       out.close();
/*      */     } catch (JSONException e) {
/* 1882 */       e.printStackTrace();
/*      */     } catch (Exception e) {
/* 1884 */       e.printStackTrace();
/*      */     }
/* 1886 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showEditGroupPopForGraph(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1890 */     BusinessViewUtil businessViewUtil = new BusinessViewUtil();
/* 1891 */     ADMUtil admutil = new ADMUtil();
/* 1892 */     String filterList = request.getParameter("filterList");
/* 1893 */     JSONObject monitorList = businessViewUtil.getAllMonitorList(response, filterList, "all");
/* 1894 */     JSONObject ownerList = admutil.getOwnersList();
/* 1895 */     JSONObject returnValue = new JSONObject();
/* 1896 */     returnValue.put("monitorList", monitorList);
/* 1897 */     returnValue.put("ownerList", ownerList);
/* 1898 */     response.setContentType("text/json;charset=UTF-8");
/* 1899 */     PrintWriter out = response.getWriter();
/* 1900 */     AMLog.debug("Got All the Monitor Types =========>>" + monitorList);
/* 1901 */     AMLog.debug("Users List==>" + ownerList);
/*      */     try {
/* 1903 */       out.println(returnValue);
/* 1904 */       out.flush();
/* 1905 */       out.close();
/*      */     } catch (Exception e) {
/* 1907 */       e.printStackTrace();
/*      */     }
/* 1909 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward saveBusinessViewPropsForADDM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1914 */       response.setContentType("text/json;charset=UTF-8");
/* 1915 */       JSONObject result = new JSONObject();
/* 1916 */       long viewid = -1L;
/* 1917 */       BusinessViewUtil businessview = new BusinessViewUtil();
/* 1918 */       ManagedApplication mo = new ManagedApplication();
/* 1919 */       String userid = mo.getUserID(request);
/* 1920 */       String haid = request.getParameter("haid");
/*      */       
/* 1922 */       String viewPropsString = request.getParameter("viewProps");
/* 1923 */       viewPropsString = businessview.decodeSpecialCharactersFromJSONString(viewPropsString);
/* 1924 */       JSONObject viewProps = new JSONObject(viewPropsString);
/*      */       
/* 1926 */       String nodeIdVsResourceIdString = request.getParameter("nodeIdVsResourceId");
/* 1927 */       nodeIdVsResourceIdString = businessview.decodeSpecialCharactersFromJSONString(nodeIdVsResourceIdString);
/* 1928 */       JSONObject nodeIdVsResourceId = new JSONObject(nodeIdVsResourceIdString);
/*      */       
/* 1930 */       viewid = businessview.createBusinessView(viewProps, userid, haid, Boolean.TRUE.booleanValue(), nodeIdVsResourceId, viewid);
/* 1931 */       if (viewid != -1L) {
/* 1932 */         result.put("message", "success");
/* 1933 */         result.put("viewId", viewid);
/*      */       } else {
/* 1935 */         result.put("message", "fail");
/*      */       }
/* 1937 */       PrintWriter out = response.getWriter();
/*      */       
/* 1939 */       out.println(result);
/* 1940 */       out.flush();
/* 1941 */       out.close();
/*      */     } catch (JSONException e) {
/* 1943 */       e.printStackTrace();
/*      */     } catch (Exception e) {
/* 1945 */       e.printStackTrace();
/*      */     }
/* 1947 */     return null;
/*      */   }
/*      */   
/* 1950 */   public ActionForward getLocations(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { DBUtil db = new DBUtil();
/* 1951 */     List<Properties> locations = new ArrayList();
/* 1952 */     JSONObject jsonLocation = new JSONObject();
/* 1953 */     JSONObject gmaplocations = new JSONObject();
/*      */     
/*      */     try
/*      */     {
/* 1957 */       locations = DBUtil.getGMapCountries();
/* 1958 */       for (Properties p : locations)
/*      */       {
/* 1960 */         String id = p.getProperty("value");
/* 1961 */         String name = p.getProperty("label");
/* 1962 */         jsonLocation.put(name, id);
/*      */       }
/* 1964 */       gmaplocations.put("locations", jsonLocation);
/* 1965 */       PrintWriter out = response.getWriter();
/* 1966 */       AMLog.debug("locations==>" + jsonLocation);
/* 1967 */       out.println(gmaplocations);
/* 1968 */       out.flush();
/* 1969 */       out.close();
/*      */     }
/*      */     catch (Exception e) {
/* 1972 */       e.printStackTrace();
/*      */     }
/* 1974 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward updateNodePosOnDrag(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1979 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 1980 */       long resourceId = Long.parseLong(request.getParameter("nodeResId"));
/* 1981 */       long parentResId = Long.parseLong(request.getParameter("parentNodeResId"));
/* 1982 */       double relativeX = Double.parseDouble(request.getParameter("relativeX"));
/* 1983 */       double relativeY = Double.parseDouble(request.getParameter("relativeY"));
/* 1984 */       long viewId = Long.parseLong(request.getParameter("viewid"));
/* 1985 */       AMLog.debug("The coordinates" + resourceId + " " + parentResId + " " + relativeX + " " + relativeY + " " + viewId);
/* 1986 */       bUtil.updateNodePositionForANode(resourceId, parentResId, relativeX, relativeY, viewId);
/*      */     } catch (Exception e) {
/* 1988 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1991 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward updateCanvasPosOnDrag(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1996 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 1997 */       long haid = Long.parseLong(request.getParameter("resId"));
/* 1998 */       long viewId = Long.parseLong(request.getParameter("viewid"));
/* 1999 */       JSONObject result = new JSONObject();
/* 2000 */       double translateOffsetX = Double.parseDouble(request.getParameter("translateOffsetX"));
/* 2001 */       double translateOffsetY = Double.parseDouble(request.getParameter("translateOffsetY"));
/* 2002 */       if (viewId == -1L) {
/* 2003 */         String displayPropsString = request.getParameter("displayProps");
/* 2004 */         displayPropsString = bUtil.decodeSpecialCharactersFromJSONString(displayPropsString);
/* 2005 */         JSONObject displayProps = new JSONObject(displayPropsString);
/* 2006 */         ManagedApplication mo = new ManagedApplication();
/* 2007 */         String userid = mo.getUserID(request);
/* 2008 */         AMLog.debug("\n DisplayProps" + displayProps);
/* 2009 */         viewId = bUtil.saveViewConfig(displayProps, userid, haid + "");
/*      */       } else {
/* 2011 */         bUtil.updateCanvasPositionForANode(haid, translateOffsetX, translateOffsetY, viewId);
/*      */       }
/* 2013 */       response.setContentType("text/json;charset=UTF-8");
/* 2014 */       PrintWriter out = response.getWriter();
/* 2015 */       result.put("viewid", viewId);
/* 2016 */       out.println(result);
/* 2017 */       AMLog.debug("The coordinates" + haid + " " + translateOffsetX + " " + translateOffsetY);
/*      */     } catch (Exception e) {
/* 2019 */       e.printStackTrace();
/*      */     }
/*      */     
/* 2022 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward saveCoordinates(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 2026 */     String displayName = request.getParameter("displayName");
/* 2027 */     String userid = "-1";
/* 2028 */     String viewid = "-1";
/* 2029 */     BusinessViewUtil businessViewUtil = new BusinessViewUtil();
/* 2030 */     String condition = "";
/* 2031 */     Statement statement = null;
/*      */     try
/*      */     {
/* 2034 */       boolean isHtml = Boolean.valueOf(request.getParameter("isHtml")).booleanValue();
/* 2035 */       AMLog.debug("saveCoordinates:" + isHtml);
/* 2036 */       String haid = request.getParameter("haid");
/* 2037 */       ManagedApplication mo = new ManagedApplication();
/* 2038 */       userid = mo.getUserID(request);
/* 2039 */       String nodesForSave = request.getParameter("nodesForSave");
/* 2040 */       nodesForSave = businessViewUtil.decodeSpecialCharactersFromJSONString(nodesForSave);
/*      */       
/* 2042 */       JSONObject nodeJSON = new JSONObject(nodesForSave);
/* 2043 */       AMConnectionPool.getInstance();statement = AMConnectionPool.getConnection().createStatement();
/*      */       
/* 2045 */       if (request.getParameter("viewid") != null) {
/* 2046 */         viewid = request.getParameter("viewid");
/* 2047 */         condition = businessViewUtil.parseJSONAndSaveCoordinates(haid, nodeJSON, statement, viewid, new JSONObject(), false);
/*      */       }
/*      */       else
/*      */       {
/* 2051 */         AMLog.debug("ViewId not present in a request for the BusinessView::" + haid);
/*      */       }
/* 2053 */       statement.executeBatch();
/* 2054 */       statement.clearBatch();
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
/* 2066 */       return null;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2056 */       e.getNextException().printStackTrace();
/* 2057 */       e.printStackTrace();
/*      */     } catch (Exception e) {
/* 2059 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 2062 */         statement.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getDisplayPropsForGlobalGraph(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2070 */     JSONObject result = new JSONObject();
/*      */     try {
/* 2072 */       boolean isPrivillagedUser = ClientDBUtil.isPrivilegedUser(request);
/* 2073 */       String userid = "-1";
/* 2074 */       if (isPrivillagedUser) {
/* 2075 */         ManagedApplication mo = new ManagedApplication();
/* 2076 */         userid = mo.getUserID(request);
/*      */       }
/* 2078 */       String viewid = request.getParameter("viewid");
/* 2079 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 2080 */       String resultString = bUtil.getDisplayPropsForGlobalGraphUtil(viewid, isPrivillagedUser, userid);
/* 2081 */       result.put("resultString", resultString);
/* 2082 */       response.setContentType("text/json;charset=UTF-8");
/* 2083 */       PrintWriter out = response.getWriter();
/* 2084 */       out.println(result);
/*      */     } catch (Exception e) {
/* 2086 */       e.printStackTrace();
/*      */     }
/* 2088 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward updateMonitorGroupsToFilterList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 2092 */     JSONObject result = new JSONObject();
/*      */     try {
/* 2094 */       String viewid = request.getParameter("viewid");
/* 2095 */       String noOfColumns = request.getParameter("noOfColumns");
/* 2096 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 2097 */       String monitorGroupString = request.getParameter("monitorList");
/* 2098 */       JSONArray monitorGroups = null;
/* 2099 */       if (monitorGroupString != null) {
/* 2100 */         monitorGroupString = bUtil.decodeSpecialCharactersFromJSONString(monitorGroupString);
/* 2101 */         monitorGroups = new JSONArray(monitorGroupString);
/*      */       }
/* 2103 */       boolean isUpdated = bUtil.updateMonitorGroupsToFilterListUtil(viewid, monitorGroups, noOfColumns);
/* 2104 */       result.put("isUpdated", isUpdated);
/* 2105 */       String bvName = bUtil.getBVNameForViewID(viewid);
/* 2106 */       result.put("bvName", bvName);
/* 2107 */       response.setContentType("text/json;charset=UTF-8");
/* 2108 */       PrintWriter out = response.getWriter();
/* 2109 */       out.println(result);
/*      */     } catch (Exception e) {
/* 2111 */       e.printStackTrace();
/*      */     }
/* 2113 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward updateShowTopLevelFlag(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 2117 */     JSONObject result = new JSONObject();
/*      */     try {
/* 2119 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 2120 */       String viewId = request.getParameter("viewid");
/* 2121 */       String haid = request.getParameter("haid");
/* 2122 */       String isShowTopLevel = request.getParameter("showTopLevelFlag");
/* 2123 */       boolean isUpdated = bUtil.updateShowTopLevelFlag(viewId, isShowTopLevel);
/* 2124 */       BusinessViewUtil.deleteBussinessViewCache(viewId + "_" + haid);
/* 2125 */       String bvName = bUtil.getBVNameForViewID(viewId);
/* 2126 */       result.put("isUpdated", isUpdated);
/* 2127 */       result.put("bvName", bvName);
/* 2128 */       response.setContentType("text/json;charset=UTF-8");
/* 2129 */       PrintWriter out = response.getWriter();
/* 2130 */       out.println(result);
/*      */     } catch (Exception e) {
/* 2132 */       e.printStackTrace();
/*      */     }
/* 2134 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward checkBusinessServiceName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2139 */     boolean bsNameCheck = true;
/* 2140 */     ResultSet rs = null;
/*      */     try {
/* 2142 */       String bsName = "";
/* 2143 */       bsName = request.getParameter("bsName");
/* 2144 */       String queryTocheckBSname = "select RESOURCEID from AM_ManagedObject where AM_ManagedObject.TYPE='HAI' AND AM_ManagedObject.RESOURCENAME='" + bsName + "'";
/* 2145 */       rs = AMConnectionPool.executeQueryStmt(queryTocheckBSname);
/* 2146 */       if (!rs.next())
/*      */       {
/* 2148 */         bsNameCheck = false;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 2156 */         rs.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 2165 */         String returnValue = bsNameCheck ? "true" : "false";
/* 2166 */         response.setContentType("text/html;charset=UTF-8");
/* 2167 */         PrintWriter out = response.getWriter();
/* 2168 */         out.println(returnValue);
/* 2169 */         out.flush();
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 2173 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2152 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 2156 */         rs.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
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
/* 2175 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward removeMonitorsInBV(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try {
/* 2181 */       ClientAuditUtil clientAuditUtil = new ClientAuditUtil();
/* 2182 */       clientAuditUtil.setDetailsForAuditInThreadLocal(request);
/* 2183 */       String resourceId = request.getParameter("resourceId");
/* 2184 */       String haid = request.getParameter("haid");
/* 2185 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 2186 */       isSuccess = BusinessViewUtil.unAssociateMonitor(haid, resourceId, response);
/*      */     } catch (Exception e) { boolean isSuccess;
/* 2188 */       e.printStackTrace();
/*      */     }
/* 2190 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward removeGroupInBV(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*      */     try {
/* 2195 */       ClientAuditUtil clientAuditUtil = new ClientAuditUtil();
/* 2196 */       clientAuditUtil.setDetailsForAuditInThreadLocal(request);
/* 2197 */       String haID = request.getParameter("haid");
/* 2198 */       HAIDManagerAction hm = new HAIDManagerAction();
/* 2199 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 2200 */       MSreq.setContentType("text/xml; charset=UTF-8");
/* 2201 */       ServletContext servletContext = request.getSession().getServletContext();
/* 2202 */       Hashtable ht = (Hashtable)servletContext.getAttribute("applications");
/* 2203 */       MSreq.addParameter("select", haID);
/* 2204 */       MSreq.setAttribute("applications", ht);
/* 2205 */       hm.delete(null, null, MSreq, response);
/*      */     } catch (Exception e) {
/* 2207 */       e.printStackTrace();
/*      */     }
/* 2209 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward editGroupForBV(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 2213 */     response.setContentType("text/json;charset=UTF-8");
/*      */     try {
/* 2215 */       String haid = request.getParameter("haid");
/* 2216 */       PrintWriter out = response.getWriter();
/* 2217 */       BusinessViewUtil bv = new BusinessViewUtil();
/* 2218 */       ManagedApplication mo = new ManagedApplication();
/* 2219 */       Properties groupDetails = BusinessViewUtil.getGroupDetails(haid, mo);
/* 2220 */       JSONObject result = new JSONObject();
/*      */       try {
/* 2222 */         result.put("name", groupDetails.getProperty("name"));
/* 2223 */         result.put("MGtype", groupDetails.getProperty("MGtype"));
/* 2224 */         result.put("haid", haid);
/* 2225 */         result.put("description", groupDetails.getProperty("description"));
/* 2226 */         result.put("grouptype", groupDetails.getProperty("grouptype"));
/* 2227 */         if (groupDetails.get("locationid") != null) {
/* 2228 */           result.put("locationid", groupDetails.get("locationid"));
/*      */         }
/* 2230 */         JSONArray ownerListJson = new JSONArray();
/* 2231 */         ArrayList allOwnersList = (ArrayList)groupDetails.get("allowners");
/* 2232 */         int size = allOwnersList.size();
/* 2233 */         for (int i = 0; i < size; i++) {
/* 2234 */           Properties props = (Properties)allOwnersList.get(i);
/* 2235 */           ownerListJson.put(new JSONObject().put(props.getProperty("label"), props.getProperty("value")));
/*      */         }
/* 2237 */         result.put("allowners", ownerListJson);
/* 2238 */         JSONArray selectedOwners = new JSONArray();
/* 2239 */         ArrayList selectedOwnersList = (ArrayList)groupDetails.get("selectedowners");
/* 2240 */         size = selectedOwnersList.size();
/* 2241 */         for (int i = 0; i < size; i++) {
/* 2242 */           Properties props = (Properties)selectedOwnersList.get(i);
/* 2243 */           selectedOwners.put(new JSONObject().put(props.getProperty("label"), props.getProperty("value")));
/*      */         }
/* 2245 */         result.put("selectedowners", selectedOwners);
/* 2246 */         result.put("creationdate", groupDetails.getProperty("creationdate"));
/* 2247 */         result.put("lastmodified", groupDetails.getProperty("lastmodified"));
/*      */       } catch (Exception e) {
/* 2249 */         e.printStackTrace();
/*      */       }
/* 2251 */       out.println(result);
/* 2252 */       out.flush();
/*      */     } catch (Exception e) {
/* 2254 */       e.printStackTrace();
/*      */     }
/* 2256 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward updateGroupInfoForBVGraphNode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*      */     try {
/* 2261 */       BusinessViewUtil businessVewUtil = new BusinessViewUtil();
/* 2262 */       response.setContentType("text/json;charset=UTF-8");
/* 2263 */       PrintWriter out = response.getWriter();
/* 2264 */       String haid = request.getParameter("haid");
/* 2265 */       String groupName = request.getParameter("groupName");
/* 2266 */       String description = request.getParameter("description");
/* 2267 */       String locId = request.getParameter("locId");
/* 2268 */       String groupType = request.getParameter("groupType");
/* 2269 */       String UserName = request.getRemoteUser();
/* 2270 */       String owners = request.getParameter("owners");
/* 2271 */       JSONObject selectedOwners = new JSONObject();
/* 2272 */       JSONObject result = new JSONObject();
/*      */       
/* 2274 */       ServletContext servletContext = request.getSession().getServletContext();
/* 2275 */       Properties applications = null;
/*      */       try {
/* 2277 */         applications = (Properties)servletContext.getAttribute("applications");
/*      */       }
/*      */       catch (Exception e) {
/* 2280 */         e.printStackTrace();
/*      */       }
/* 2282 */       boolean isUpdated = BusinessViewUtil.updateGroupDetailsForBVGraphNode(haid, groupName, description, locId, owners, applications, groupType, response, UserName);
/*      */       
/*      */ 
/* 2285 */       result.put("isUpdated", isUpdated);
/*      */       try {
/* 2287 */         out.println(result);
/* 2288 */         out.flush();
/* 2289 */         out.close();
/*      */       } catch (Exception e) {
/* 2291 */         e.printStackTrace();
/*      */       }
/*      */     } catch (Exception e) {
/* 2294 */       e.printStackTrace();
/*      */     }
/* 2296 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward associateMonitorToBVAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*      */     try {
/* 2301 */       long startTime = System.currentTimeMillis();
/* 2302 */       ClientAuditUtil clientAuditUtil = new ClientAuditUtil();
/* 2303 */       clientAuditUtil.setDetailsForAuditInThreadLocal(request);
/* 2304 */       String haid = request.getParameter("haid");
/* 2305 */       String monList = request.getParameter("monList");
/* 2306 */       String nodeId = request.getParameter("nodeId");
/* 2307 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/*      */       
/*      */ 
/* 2310 */       boolean monitorsAssociated = BusinessViewUtil.associateMonitors(haid, monList, response, "-1", Boolean.FALSE.booleanValue(), "");
/* 2311 */       PrintWriter out = response.getWriter();
/* 2312 */       JSONObject result = new JSONObject();
/* 2313 */       result.put("success", monitorsAssociated);
/* 2314 */       long timeTaken = System.currentTimeMillis();
/* 2315 */       AMLog.debug("Time Taken For Associating Monitors " + (timeTaken - startTime) / 1000L + "secs");
/* 2316 */       if (monitorsAssociated) {
/* 2317 */         int nextNodeId = Integer.parseInt(request.getParameter("nextNodeId"));
/* 2318 */         String nodeIdentifier = request.getParameter("nodeIdentifier");
/* 2319 */         String[] monitorList = monList.split(",");
/* 2320 */         String healthKeysString = request.getParameter("healthKeySet");
/* 2321 */         String[] dispNameList = request.getParameter("displayNameList").split(",");
/* 2322 */         String[] typeList = request.getParameter("typeList").split(",");
/* 2323 */         String[] imagePathList = request.getParameter("imagePathList").split(",");
/* 2324 */         AMLog.debug("Associating monitor to BV " + haid + "\tmonList: " + monList);
/* 2325 */         HashMap extDeviceMap = null;
/* 2326 */         HashMap site24x7List = null;
/*      */         
/* 2328 */         List<String> resIdList = new ArrayList(Arrays.asList(monitorList));
/* 2329 */         List<String> attIdList = new ArrayList(Arrays.asList(healthKeysString.split(",")));
/* 2330 */         Properties alert = FaultUtil.getStatus(resIdList, attIdList);
/* 2331 */         AMLog.debug("Associating monitor to BV 444" + haid + "\talert: " + alert);
/* 2332 */         Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/* 2333 */         int length = resIdList.size();
/*      */         
/* 2335 */         JSONObject subTree = new JSONObject();
/* 2336 */         JSONArray childTree = new JSONArray();
/*      */         
/* 2338 */         for (int index = 0; index < length; index++) {
/* 2339 */           String eachMonitor = monitorList[index];
/* 2340 */           String eachKey = (String)attIdList.get(index);
/* 2341 */           String type = typeList[index];
/* 2342 */           String dispName = dispNameList[index];
/* 2343 */           String imagepath = imagePathList[index];
/* 2344 */           String childId = nodeIdentifier + nextNodeId++;
/* 2345 */           AMLog.debug("Associating monitor to BV 555" + eachMonitor + "\teachKey" + eachKey + " type:" + type + " dispName" + dispName + " imagepath " + imagepath);
/* 2346 */           String userName = request.getRemoteUser();
/* 2347 */           boolean isEditable = !"USERS".equals(DBUtil.getUserRole(userName));
/*      */           try {
/* 2349 */             extDeviceMap = IntegProdDBUtil.getExtAllDevicesLink();
/* 2350 */             site24x7List = DBUtil.getAllsite24x7MonitorsLink();
/* 2351 */             String severity = alert.getProperty(eachMonitor + "#" + eachKey);
/* 2352 */             String statusClass = BusinessViewUtil.severityClassDetails.getProperty(severity, "");
/* 2353 */             AMLog.debug("status class: " + statusClass);
/* 2354 */             String url = bUtil.getURLForRedirection(eachMonitor, type, site24x7List, extDeviceMap);
/* 2355 */             String monDivForBV = BusinessViewUtil.getMonitorDivForBV(extDeviceMap, site24x7List, eachMonitor, haid, childId, imagepath, statusClass, url, dispName, isEditable);
/* 2356 */             AMLog.debug("The monDiv for BV addd ::::::::::" + monDivForBV);
/* 2357 */             String message = alert.getProperty(eachMonitor + "#" + eachKey + "#" + "MESSAGE");
/*      */             
/*      */ 
/*      */ 
/* 2361 */             JSONObject data = new JSONObject();
/* 2362 */             data.put("$resourceID", eachMonitor);
/* 2363 */             data.put("$parentID", haid);
/* 2364 */             data.put("$parentNodeID", nodeId);
/* 2365 */             data.put("$message", message);
/* 2366 */             data.put("$severity", severity);
/* 2367 */             data.put("$monType", type);
/* 2368 */             data.put("$edgeType", "styled_arrow");
/* 2369 */             data.put("$nodeX", "default");
/* 2370 */             data.put("$nodeY", "default");
/*      */             
/* 2372 */             JSONObject childObj = new JSONObject();
/* 2373 */             childObj.put("id", childId);
/* 2374 */             childObj.put("name", monDivForBV);
/* 2375 */             childObj.put("data", data);
/*      */             
/* 2377 */             childTree.put(childObj);
/* 2378 */             AMLog.debug("ChildObj ===== >>>>" + childObj);
/*      */           } catch (Exception e) {
/* 2380 */             e.printStackTrace();
/*      */           }
/*      */         }
/* 2383 */         subTree.put("id", nodeId);
/* 2384 */         subTree.put("children", childTree);
/* 2385 */         AMLog.debug("subTreeee ...." + subTree);
/* 2386 */         result.put("subTree", subTree);
/* 2387 */         result.put("nextNodeId", nextNodeId);
/* 2388 */         long endTime = System.currentTimeMillis();
/* 2389 */         AMLog.debug("Time Taken For Associating And Return " + (endTime - startTime) / 1000L + "secs");
/*      */       }
/*      */       try {
/* 2392 */         out.println(result);
/* 2393 */         out.flush();
/* 2394 */         out.close();
/*      */       } catch (Exception e) {
/* 2396 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2400 */       e.printStackTrace();
/*      */     }
/* 2402 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward createSubGroupTreeToBVAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*      */     try {
/* 2407 */       String statusClassForGroup = "groupMon";
/* 2408 */       String expandClass = "iconAddMinus";
/* 2409 */       response.setContentType("text/json;charset=UTF-8");
/* 2410 */       PrintWriter out = response.getWriter();
/* 2411 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 2412 */       JSONObject result = new JSONObject();
/* 2413 */       String haid = request.getParameter("haid");
/* 2414 */       String type = request.getParameter("type");
/* 2415 */       String nodeId = request.getParameter("nodeId");
/* 2416 */       String subGroupData = request.getParameter("subGroupData");
/* 2417 */       ClientAuditUtil clientAuditUtil = new ClientAuditUtil();
/* 2418 */       clientAuditUtil.setDetailsForAuditInThreadLocal(request);
/* 2419 */       subGroupData = bUtil.decodeSpecialCharactersFromJSONString(subGroupData);
/* 2420 */       JSONObject subGroupInfo = new JSONObject(subGroupData);
/*      */       
/* 2422 */       haid = BusinessViewUtil.createSubgroup(haid, response, subGroupInfo);
/* 2423 */       String userName = request.getRemoteUser();
/* 2424 */       boolean isEditable = !"USERS".equals(DBUtil.getUserRole(userName));
/*      */       try {
/* 2426 */         if (!"-1".equals(haid)) {
/* 2427 */           String url = bUtil.getURLForRedirection(haid, type, null, null);
/* 2428 */           String dispName = subGroupInfo.getString("$displayName");
/* 2429 */           String divToAdd = BusinessViewUtil.getDivToAddToBVGroupNode(expandClass);
/* 2430 */           AMLog.debug("The display name is " + dispName);
/* 2431 */           AMLog.debug("The divToAdd name is " + divToAdd);
/* 2432 */           String groupDivToReturn = BusinessViewUtil.getGroupDiv(nodeId, haid, statusClassForGroup, divToAdd, url, dispName, Boolean.FALSE.booleanValue(), isEditable);
/* 2433 */           result.put("groupDivNode", groupDivToReturn);
/*      */         }
/* 2435 */         result.put("createdHaid", haid);
/*      */       } catch (JSONException e) {
/* 2437 */         e.printStackTrace();
/*      */       }
/* 2439 */       out.println(result);
/* 2440 */       out.flush();
/*      */     } catch (Exception e) {
/* 2442 */       e.printStackTrace();
/*      */     }
/* 2444 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward createDependentGroupBVAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*      */     try {
/* 2449 */       response.setCharacterEncoding("UTF-8");
/* 2450 */       PrintWriter out = response.getWriter();
/* 2451 */       BusinessViewUtil bUtil = new BusinessViewUtil();
/* 2452 */       ADMUtil admUtil = new ADMUtil();
/* 2453 */       JSONObject result = new JSONObject();
/* 2454 */       String haid = request.getParameter("haid");
/* 2455 */       String dependentList = request.getParameter("dependentList");
/* 2456 */       String dependentKey = "18";
/* 2457 */       AMLog.debug("HAID " + haid);
/* 2458 */       AMLog.debug("dependentList " + dependentList);
/* 2459 */       RuleAnalyser.addDependentMG(haid, dependentList, Boolean.TRUE.booleanValue(), Boolean.FALSE.booleanValue());
/* 2460 */       String typedisplayname = FormatUtil.getString("am.webclient.monitorgroupdetails.dependentgroup.text");
/*      */       try {
/* 2462 */         ClientAuditUtil clientAuditUtil = new ClientAuditUtil();
/* 2463 */         clientAuditUtil.setDetailsForAuditInThreadLocal(request);
/* 2464 */         String userName = request.getRemoteUser();
/* 2465 */         boolean isEditable = !"USERS".equals(DBUtil.getUserRole(userName));
/* 2466 */         String[] dependentArray = dependentList.split(",");
/* 2467 */         String[] haidDispNameList = request.getParameter("haidDispNameList").split(",");
/* 2468 */         String nodeId = request.getParameter("nodeId");
/* 2469 */         int nextNodeId = Integer.parseInt(request.getParameter("nextNodeId"));
/* 2470 */         String nodeIdentifier = request.getParameter("nodeIdentifier");
/* 2471 */         List<String> dependentGroupArrayList = new ArrayList(Arrays.asList(dependentArray));
/* 2472 */         List<String> attIdList = new ArrayList(Arrays.asList(new String[] { dependentKey }));
/* 2473 */         Properties alert = FaultUtil.getStatus(dependentGroupArrayList, attIdList);
/* 2474 */         int length = dependentGroupArrayList.size();
/* 2475 */         JSONObject subTree = new JSONObject();
/* 2476 */         JSONArray childTree = new JSONArray();
/* 2477 */         for (int index = 0; index < length; index++) {
/* 2478 */           String childId = nodeIdentifier + nextNodeId;
/* 2479 */           String dependentHaid = (String)dependentGroupArrayList.get(index);
/* 2480 */           String message = alert.getProperty(dependentHaid + "#" + dependentKey + "#" + "MESSAGE");
/* 2481 */           String severity = alert.getProperty(dependentHaid + "#" + dependentKey);
/* 2482 */           String dispName = haidDispNameList[index];
/* 2483 */           String url = bUtil.getURLForRedirection(dependentHaid, "HAI", null, null);
/* 2484 */           String divToAdd = BusinessViewUtil.getDivToAddToBVGroupNode("iconD");
/* 2485 */           String nameOfChild = BusinessViewUtil.getGroupDiv(childId + "", dependentHaid, "", divToAdd, url, dispName, Boolean.TRUE.booleanValue(), isEditable);
/* 2486 */           if (message != null)
/*      */           {
/* 2488 */             message = "<p> <label>" + FormatUtil.getString("am.webclient.common.name.text") + " : </label><b>" + dispName + "</b></p><p><label>" + FormatUtil.getString("am.webclient.common.type.text") + " : </label><b>" + typedisplayname + "</b></p><p id=\"messageBV\">" + message + "</p>";
/*      */           }
/*      */           else
/*      */           {
/* 2492 */             message = admUtil.getMessageForTooltip(dispName, typedisplayname);
/*      */           }
/*      */           
/* 2495 */           JSONObject data = new JSONObject();
/* 2496 */           data.put("$resourceID", dependentHaid);
/* 2497 */           data.put("$parentID", haid);
/* 2498 */           data.put("$parentNodeID", nodeId);
/* 2499 */           data.put("$message", message);
/* 2500 */           data.put("$severity", severity);
/* 2501 */           data.put("$monType", "HAI");
/* 2502 */           data.put("$edgeType", "styled_arrow");
/* 2503 */           data.put("$nodeX", "default");
/* 2504 */           data.put("$nodeY", "default");
/*      */           
/* 2506 */           JSONObject childObj = new JSONObject();
/* 2507 */           childObj.put("id", childId);
/* 2508 */           childObj.put("name", nameOfChild);
/* 2509 */           childObj.put("data", data);
/* 2510 */           nextNodeId++;
/* 2511 */           childTree.put(childObj);
/* 2512 */           AMLog.debug("ChildObj DG===== >>>>" + childObj);
/*      */         }
/* 2514 */         subTree.put("id", nodeId);
/* 2515 */         subTree.put("children", childTree);
/*      */         
/* 2517 */         result.put("message", true);
/* 2518 */         result.put("subTree", subTree);
/* 2519 */         result.put("nextNodeId", nextNodeId);
/*      */       } catch (Exception e) {
/* 2521 */         e.printStackTrace();
/*      */       }
/* 2523 */       out.println(result);
/* 2524 */       out.flush();
/*      */     } catch (Exception e) {
/* 2526 */       e.printStackTrace();
/*      */     }
/* 2528 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\GraphicalView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */