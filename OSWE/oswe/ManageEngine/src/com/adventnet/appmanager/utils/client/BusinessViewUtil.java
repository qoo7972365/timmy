/*      */ package com.adventnet.appmanager.utils.client;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.fault.RuleAnalyser;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.discovery.ADM.ADMUtil;
/*      */ import com.adventnet.appmanager.server.framework.confparser.PreConfMonitorXMLParser;
/*      */ import com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil;
/*      */ import com.adventnet.appmanager.struts.actions.HAIDManagerAction;
/*      */ import com.adventnet.appmanager.struts.actions.NewMonitorConf;
/*      */ import com.adventnet.appmanager.struts.actions.ShowApplication;
/*      */ import com.adventnet.appmanager.util.ChildMOHandler;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.mock.MockHttpServletRequest;
/*      */ import org.htmlparser.util.Translate;
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONException;
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
/*      */ public class BusinessViewUtil
/*      */ {
/*   65 */   public int id = 1;
/*      */   public String haids;
/*      */   public boolean isOwner;
/*      */   public static Properties severityClassDetails;
/*   69 */   private static String divStyleRegex = "span id=\"nameIdentifier\" class=\"[a-zA-Z\\s]+";
/*   70 */   private static String divStyleReplace = "span id=\"nameIdentifier\" class=\"";
/*      */   private ADMUtil admUtil;
/*      */   
/*      */   static {
/*   74 */     severityClassDetails = new Properties();
/*   75 */     severityClassDetails.setProperty("5", "stsUp");
/*   76 */     severityClassDetails.setProperty("4", "stsWarning");
/*   77 */     severityClassDetails.setProperty("1", "stsDown");
/*      */   }
/*      */   
/*      */   public BusinessViewUtil() {
/*   81 */     this.haids = "";
/*   82 */     this.isOwner = true;
/*   83 */     this.admUtil = new ADMUtil();
/*      */   }
/*      */   
/*      */   public void setOperatorHAIDs(String haid, String userName, String loginUserid) {
/*   87 */     Hashtable haidparents = DBUtil.getParentMGsforChildMGs("('" + haid + "')");
/*   88 */     String haidstoCheck = "(";
/*   89 */     ArrayList temp1 = (ArrayList)haidparents.get(haid);
/*   90 */     if (temp1 != null)
/*      */     {
/*   92 */       for (int i = 0; i < temp1.size(); i++)
/*      */       {
/*   94 */         haidstoCheck = haidstoCheck + "'" + temp1.get(i) + "',";
/*      */       }
/*      */     }
/*   97 */     haidstoCheck = haidstoCheck + "'" + haid + "')";
/*   98 */     String q1 = "select * from AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where USERNAME='" + userName + "' and USERID=OWNERID AND HAID IN " + haidstoCheck;
/*   99 */     if (Constants.isSsoEnabled()) {
/*  100 */       q1 = "select * from AM_USERRESOURCESTABLE where RESOURCEID=" + loginUserid + " AND RESOURCEID IN " + haidstoCheck;
/*      */     }
/*  102 */     ResultSet ownerRs = null;
/*      */     try
/*      */     {
/*  105 */       ownerRs = AMConnectionPool.executeQueryStmt(q1);
/*  106 */       if (ownerRs.next())
/*      */       {
/*  108 */         this.isOwner = true;
/*      */       }
/*      */       else
/*      */       {
/*  112 */         this.isOwner = false;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  117 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  121 */       AMConnectionPool.closeStatement(ownerRs);
/*      */     }
/*  123 */     if (!this.isOwner) {
/*  124 */       Hashtable<String, String> resIdVsType = new Hashtable();
/*  125 */       Object mainlist = new ArrayList();
/*  126 */       Hashtable parentlist = new Hashtable();
/*  127 */       List userids = new ArrayList();
/*  128 */       String qry = "SELECT AM_HOLISTICAPPLICATION.HAID,TYPE from  AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable,AM_HOLISTICAPPLICATION WHERE   AM_UserPasswordTable.USERNAME='" + userName + "' AND AM_UserPasswordTable.USERID= AM_HOLISTICAPPLICATION_OWNERS.OWNERID AND AM_HOLISTICAPPLICATION.HAID= AM_HOLISTICAPPLICATION_OWNERS.HAID";
/*  129 */       if (Constants.isSsoEnabled()) {
/*  130 */         qry = "SELECT AM_HOLISTICAPPLICATION.HAID,TYPE from  AM_USERRESOURCESTABLE,AM_HOLISTICAPPLICATION WHERE AM_USERRESOURCESTABLE.USERID=" + loginUserid + " AND AM_HOLISTICAPPLICATION.HAID= AM_USERRESOURCESTABLE.RESOURCEID";
/*      */       }
/*  132 */       ResultSet operatorRs = null;
/*      */       
/*  134 */       String mainhaids = "(";
/*  135 */       String childhaids = "(";
/*      */       try
/*      */       {
/*  138 */         operatorRs = AMConnectionPool.executeQueryStmt(qry);
/*  139 */         while (operatorRs.next())
/*      */         {
/*  141 */           if (operatorRs.getInt(2) == 0)
/*      */           {
/*  143 */             mainhaids = mainhaids + "'" + operatorRs.getString(1) + "',";
/*  144 */             ((List)mainlist).add(operatorRs.getString(1));
/*  145 */             userids.add(operatorRs.getString(1));
/*      */           }
/*      */           else
/*      */           {
/*  149 */             childhaids = childhaids + "'" + operatorRs.getString(1) + "',";
/*  150 */             userids.add(operatorRs.getString(1));
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e1)
/*      */       {
/*  156 */         e1.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  160 */         AMConnectionPool.closeStatement(operatorRs);
/*      */       }
/*  162 */       childhaids = childhaids.substring(0, childhaids.length() - 1) + ")";
/*      */       
/*      */ 
/*  165 */       String haids = "(";
/*  166 */       if (childhaids.length() > 2)
/*      */       {
/*  168 */         parentlist = DBUtil.getParentMGsforChildMGs(childhaids);
/*  169 */         Object rootlist = new ArrayList();
/*  170 */         Set ks = parentlist.keySet();
/*  171 */         Iterator it = ks.iterator();
/*  172 */         while (it.hasNext()) {
/*  173 */           String key = (String)it.next();
/*  174 */           ArrayList temp = (ArrayList)parentlist.get(key);
/*  175 */           int index = temp.indexOf(haid);
/*  176 */           if ((temp.size() > index + 1) && (temp.get(index + 1) != null)) {
/*  177 */             haids = haids + "'" + temp.get(index + 1) + "',";
/*      */           }
/*      */           else {
/*  180 */             haids = haids + "'" + key + "',";
/*      */           }
/*  182 */           mainhaids = mainhaids + "'" + ((ArrayList)parentlist.get(key)).get(0) + "',";
/*      */         }
/*      */       }
/*  185 */       if (mainhaids.length() > 2)
/*      */       {
/*  187 */         mainhaids = mainhaids.substring(0, mainhaids.length() - 1) + ")";
/*      */       }
/*  189 */       if (haids.length() > 2)
/*      */       {
/*  191 */         haids = haids.substring(0, haids.length() - 1) + ")";
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getQueryForFirstLevelChildren(String haid, boolean isPrivillagedUser, String userName, boolean isIT360, Vector haidVector, String userid, boolean isOperator) {
/*  197 */     String query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.RESOURCEGROUP,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME, AM_ManagedResourceType.IMAGEPATH as IMAGEPATH from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + haid;
/*  198 */     boolean isowner = true;
/*  199 */     if (haid.equals("0"))
/*      */     {
/*  201 */       if ((isPrivillagedUser) && (!isIT360))
/*      */       {
/*  203 */         query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,'HAI' as RESOURCEGROUP,'" + FormatUtil.getString("am.webclient.common.util.MGSTR") + "' as TYPEDISPLAYNAME, AM_ManagedResourceType.IMAGEPATH as IMAGEPATH from AM_HOLISTICAPPLICATION,AM_ManagedObject,AM_HOLISTICAPPLICATION_OWNERS,AM_ManagedResourceType where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=0 and AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=" + userid + " and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE";
/*      */       }
/*      */       else
/*      */       {
/*  207 */         String bsgFilterCondn = "";
/*  208 */         String bsgType = "0";
/*  209 */         if (isIT360)
/*      */         {
/*  211 */           bsgType = "1";
/*  212 */           bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*      */         }
/*  214 */         query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,'HAI' as RESOURCEGROUP,'" + FormatUtil.getString("am.webclient.common.util.MGSTR") + "' as TYPEDISPLAYNAME, AM_ManagedResourceType.IMAGEPATH as IMAGEPATH from AM_HOLISTICAPPLICATION,AM_ManagedObject, AM_ManagedResourceType where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + " and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE";
/*      */       }
/*      */     }
/*      */     
/*  218 */     if ((isOperator) && (!this.isOwner)) {
/*  219 */       query = getQueryforOperator(haid);
/*      */     }
/*  221 */     return query;
/*      */   }
/*      */   
/*      */   private static String getQueryForChildren(boolean isOperator, String haid, String userName, String loginUserid)
/*      */   {
/*  226 */     boolean isowner = true;
/*  227 */     String q1 = "";
/*      */     
/*  229 */     if (!isOperator)
/*      */     {
/*  231 */       isowner = true;
/*      */     }
/*      */     else {
/*  234 */       Hashtable haidparents = DBUtil.getParentMGsforChildMGs("('" + haid + "')");
/*      */       
/*  236 */       AMLog.debug("Inside checkForChildsAndAddToMap......" + haidparents);
/*  237 */       String haidstoCheck = "(";
/*      */       
/*  239 */       ArrayList temp1 = (ArrayList)haidparents.get(haid);
/*  240 */       if (temp1 != null)
/*      */       {
/*  242 */         for (int i = 0; i < temp1.size(); i++)
/*      */         {
/*  244 */           haidstoCheck = haidstoCheck + "'" + temp1.get(i) + "',";
/*      */         }
/*      */       }
/*  247 */       haidstoCheck = haidstoCheck + "'" + haid + "')";
/*  248 */       q1 = "select * from AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where USERNAME='" + userName + "' and USERID=OWNERID AND HAID IN " + haidstoCheck;
/*  249 */       if (Constants.isSsoEnabled()) {
/*  250 */         q1 = "select * from AM_USERRESOURCESTABLE where RESOURCEID=" + loginUserid + " AND RESOURCEID IN " + haidstoCheck;
/*      */       }
/*  252 */       ResultSet ownerRs = null;
/*      */       try
/*      */       {
/*  255 */         ownerRs = AMConnectionPool.executeQueryStmt(q1);
/*  256 */         if (ownerRs.next())
/*      */         {
/*  258 */           isowner = true;
/*      */         }
/*      */         else
/*      */         {
/*  262 */           isowner = false;
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */ 
/*  270 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  274 */         AMConnectionPool.closeStatement(ownerRs);
/*      */       }
/*      */     }
/*  277 */     return q1;
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
/*      */   public void generateFullJSON(HashMap map, String rootName, ArrayList resIdList, ArrayList attIdList, Hashtable healthkeys, String haStatus, String groupId, PrintWriter out, Properties view, String mgmessage, String displayName, boolean isEditable)
/*      */   {
/*  291 */     long startTime = System.currentTimeMillis();
/*  292 */     String haImage = "";
/*  293 */     JSONObject parentJsonObject = new JSONObject();
/*  294 */     JSONObject data = new JSONObject();
/*  295 */     String viewId = view.getProperty("VIEWID");
/*  296 */     Properties coordinates = getCoordProps(viewId);
/*  297 */     Properties parentCoordinates = new Properties();
/*  298 */     HashMap deviceLinkDetails = new HashMap();
/*      */     
/*      */ 
/*      */ 
/*  302 */     int saveFlag = 1;
/*  303 */     this.id = 1;
/*  304 */     String statusClass = "";
/*  305 */     boolean isTop = true;
/*  306 */     Properties alert = FaultUtil.getStatus(resIdList, attIdList);
/*  307 */     AMLog.debug("The attribute Ids:" + attIdList);
/*  308 */     AMLog.debug("The alert Is:" + alert);
/*  309 */     ArrayList childList = (ArrayList)map.get(groupId);
/*  310 */     HashMap extDeviceMap = IntegProdDBUtil.getExtAllDevicesLink(false);
/*  311 */     deviceLinkDetails.put("extDeviceMap", extDeviceMap);
/*  312 */     if ((haStatus == null) || (groupId.equals("0"))) {
/*  313 */       haStatus = "0";
/*  314 */       haImage = "";
/*      */     }
/*  316 */     statusClass = severityClassDetails.getProperty(haStatus, "");
/*      */     
/*  318 */     AMLog.debug("COORDINATES : For Business View" + coordinates);
/*  319 */     if ("-1".equals(viewId)) {
/*  320 */       saveFlag = 0;
/*  321 */     } else if (!coordinates.isEmpty()) {
/*  322 */       saveFlag = 2;
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  327 */       HashMap site24x7List = null;
/*      */       try {
/*  329 */         site24x7List = DBUtil.getAllsite24x7MonitorsLink();
/*  330 */         deviceLinkDetails.put("site24x7List", site24x7List);
/*      */       }
/*      */       catch (Exception ex) {
/*  333 */         ex.printStackTrace();
/*      */       }
/*  335 */       int parentId = this.id;
/*  336 */       parentJsonObject.put("id", this.id);
/*  337 */       parentJsonObject.put("resourceId", groupId + "");
/*  338 */       parentJsonObject.put("type", "HAI");
/*  339 */       data.put("$x", 0);
/*  340 */       data.put("$y", 0);
/*  341 */       data.put("$resourceID", groupId + "");
/*  342 */       data.put("$parentNodeID", "-1");
/*  343 */       data.put("$parentID", "-1");
/*  344 */       String actionDiv = "<span class='iconAddNode' onclick='addToBusinessService(\"" + this.id + "\")'></span><span class='iconEdit' onclick='editGroupNode(\"" + groupId + "\", \"" + this.id + "\")'></span><span class=\"iconDelete\" onclick='removeGroupFromBV(\"" + groupId + "\",\"" + this.id + "\")'></span>";
/*  345 */       if (groupId.equals("0")) {
/*  346 */         actionDiv = "";
/*  347 */         mgmessage = FormatUtil.getString("Home");
/*  348 */       } else if (mgmessage.isEmpty()) {
/*  349 */         mgmessage = this.admUtil.getMessageForTooltip(rootName, FormatUtil.getString("am.webclient.common.util.MGSTR"));
/*      */       } else {
/*  351 */         mgmessage = "<p> <label>" + FormatUtil.getString("am.webclient.common.name.text") + " : </label><b>" + rootName + "</b></p><p><label>" + FormatUtil.getString("am.webclient.common.type.text") + " :  </label><b>" + FormatUtil.getString("am.webclient.common.util.MGSTR") + "</b></p><p id=\"messageBV\">" + mgmessage + "</p>";
/*      */       }
/*  353 */       if (!isEditable) {
/*  354 */         actionDiv = "";
/*      */       }
/*  356 */       data.put("$message", mgmessage);
/*  357 */       parentCoordinates.put(groupId + "_x", "0");
/*  358 */       parentCoordinates.put(groupId + "_y", "0");
/*  359 */       parentJsonObject.put("data", data);
/*  360 */       AMLog.debug("Before Recursion  ....." + (System.currentTimeMillis() - startTime));
/*      */       
/*  362 */       JSONArray children = getChildrenAsJSONArrayForSpaceTree(view, groupId, map, isTop, alert, healthkeys, deviceLinkDetails, coordinates, parentCoordinates, this.id, isEditable);
/*  363 */       String divToAdd = "";
/*  364 */       if (children.length() > 0) {
/*  365 */         divToAdd = "<u class=\"iconAddMinus\"><p class=\"hoverCon\">" + FormatUtil.getString("am.webclient.jitview.tooltip.text") + "</p></u>";
/*      */       }
/*  367 */       displayName = EnterpriseUtil.decodeString(displayName);
/*  368 */       String parentNameWithImage = "<div class=\"iconBox\">" + actionDiv + "<span id=\"nameIdentifier\" class=\"" + statusClass + " cloudBox\"><img src='../images/icon_sg.png'>" + divToAdd + "</span><i><a class=\"redirectToDetails\" href=\"showresource.do?resourceid=" + groupId + "&method=showResourceForResourceID\" target=\"_blank\" id=\"dispName_" + parentId + "\"> " + displayName + "</a></i></div>";
/*  369 */       parentJsonObject.put("name", parentNameWithImage);
/*  370 */       parentJsonObject.put("children", children);
/*  371 */       parentJsonObject.put("saveFlag", saveFlag);
/*  372 */       parentJsonObject.put("SHOWTOPLEVELMGS", view.getProperty("SHOWTOPLEVELMGS"));
/*  373 */       if (saveFlag != 0) {
/*  374 */         parentJsonObject.put("backgroundColorVal", view.getProperty("BGCOLOR"));
/*  375 */         parentJsonObject.put("lineColorVal", view.getProperty("LINECOLOR"));
/*  376 */         parentJsonObject.put("textColorVal", view.getProperty("LABELCOLOR"));
/*  377 */         parentJsonObject.put("lineThickness", view.getProperty("LINETHICKNESS"));
/*  378 */         parentJsonObject.put("lineTransparency", view.getProperty("LINETRANSPARENCY"));
/*  379 */         parentJsonObject.put("showLabel", view.getProperty("SHOWLABEL"));
/*  380 */         parentJsonObject.put("xCanvas", view.getProperty("XCANVAS"));
/*  381 */         parentJsonObject.put("yCanvas", view.getProperty("YCANVAS"));
/*      */       } else {
/*  383 */         parentJsonObject.put("xCanvas", 64986);
/*  384 */         parentJsonObject.put("yCanvas", 0);
/*      */       }
/*  386 */       DBUtil.addBusinessCache(viewId + "_" + groupId, parentJsonObject);
/*  387 */       JSONObject jsonToReturn = new JSONObject(parentJsonObject.toString());
/*      */       
/*  389 */       filterCritical(jsonToReturn, new JSONObject(), view, new Properties());
/*  390 */       out.print(jsonToReturn);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  394 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private JSONArray getChildrenAsJSONArrayForSpaceTree(Properties view, String groupId, HashMap map, boolean isTopLevel, Properties alert, Hashtable healthkeys, HashMap deviceLinkDetails, Properties coordinates, Properties parentCoordinates, int parentID, boolean isEditable) {
/*  399 */     ArrayList childList = (ArrayList)map.get(groupId);
/*  400 */     JSONArray groupWithArray = new JSONArray();
/*  401 */     if (childList == null) {
/*  402 */       return groupWithArray;
/*      */     }
/*  404 */     HashMap extDeviceMap = (HashMap)deviceLinkDetails.get("extDeviceMap");
/*  405 */     HashMap site24x7List = (HashMap)deviceLinkDetails.get("site24x7List");
/*      */     
/*  407 */     for (int i = 0; i < childList.size(); i++)
/*      */     {
/*  409 */       ArrayList elementList = (ArrayList)childList.get(i);
/*  410 */       String resId = (String)elementList.get(0);
/*  411 */       String dispName = (String)elementList.get(1);
/*  412 */       dispName = EnterpriseUtil.decodeString(dispName);
/*  413 */       String type = (String)elementList.get(2);
/*  414 */       String imagepath = (String)elementList.get(3);
/*  415 */       String typedisplayname = (String)elementList.get(5);
/*  416 */       String groupType = (String)elementList.get(5);
/*  417 */       String severity = alert.getProperty(resId + "#" + healthkeys.get(type));
/*  418 */       String messageKey = resId + "#" + healthkeys.get(type) + "#MESSAGE";
/*  419 */       String message = (String)alert.get(messageKey);
/*  420 */       JSONObject childDetails = new JSONObject();
/*  421 */       JSONObject data = new JSONObject();
/*  422 */       String statusClass = "";
/*  423 */       String expandClass = "iconAddMinus";
/*  424 */       if (severity == null)
/*      */       {
/*  426 */         severity = "0";
/*      */       }
/*      */       
/*  429 */       boolean isDependentMGroup = "Dependent-Group".equals(groupType);
/*  430 */       if (isDependentMGroup) {
/*  431 */         typedisplayname = FormatUtil.getString("am.webclient.monitorgroupdetails.dependentgroup.text");
/*  432 */         expandClass = "iconD";
/*      */       }
/*  434 */       String url = getURLForRedirection(resId, type, site24x7List, extDeviceMap);
/*  435 */       if (message != null)
/*      */       {
/*  437 */         message = "<p> <label>" + FormatUtil.getString("am.webclient.common.name.text") + " : </label><b>" + dispName + "</b></p><p><label>" + FormatUtil.getString("am.webclient.common.type.text") + " : </label><b>" + typedisplayname + "</b></p><p id=\"messageBV\">" + message + "</p>";
/*      */       }
/*      */       else
/*      */       {
/*  441 */         message = this.admUtil.getMessageForTooltip(dispName, typedisplayname);
/*      */       }
/*  443 */       statusClass = severityClassDetails.getProperty(severity, "");
/*  444 */       if ((view.getProperty("SHOWLABEL") != null) && (view.getProperty("SHOWLABEL").equals("false")) && (!type.equals("HAI")))
/*      */       {
/*  446 */         dispName = "";
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  451 */         if ((coordinates.containsKey(groupId + "_" + resId + "_x")) && (coordinates.containsKey(groupId + "_" + resId + "_y")) && (parentCoordinates.containsKey(groupId + "_x")) && (parentCoordinates.containsKey(groupId + "_y")))
/*      */         {
/*  453 */           double xPos = Double.parseDouble(parentCoordinates.getProperty(groupId + "_x")) + Double.parseDouble(coordinates.getProperty(groupId + "_" + resId + "_x"));
/*  454 */           double yPos = Double.parseDouble(parentCoordinates.getProperty(groupId + "_y")) + Double.parseDouble(coordinates.getProperty(groupId + "_" + resId + "_y"));
/*  455 */           data.put("$nodeX", xPos);
/*  456 */           data.put("$nodeY", yPos);
/*  457 */           parentCoordinates.setProperty(resId + "_x", xPos + "");
/*  458 */           parentCoordinates.setProperty(resId + "_y", yPos + "");
/*      */         } else {
/*  460 */           data.put("$nodeX", "default");
/*  461 */           data.put("$nodeY", "default");
/*      */         }
/*  463 */         data.put("$resourceID", resId + "");
/*  464 */         data.put("$parentID", groupId + "");
/*  465 */         data.put("$parentNodeID", parentID + "");
/*  466 */         data.put("$message", message);
/*      */         
/*  468 */         data.put("$severity", severity);
/*  469 */         data.put("$monType", type);
/*  470 */         int nodeId = ++this.id;
/*  471 */         childDetails.put("id", nodeId);
/*  472 */         data.put("$edgeType", "styled_arrow");
/*  473 */         if ("HAI".equalsIgnoreCase(type))
/*      */         {
/*  475 */           JSONArray jsonArrayForChildren = new JSONArray();
/*  476 */           if (!isDependentMGroup) {
/*  477 */             jsonArrayForChildren = getChildrenAsJSONArrayForSpaceTree(view, resId, map, false, alert, healthkeys, deviceLinkDetails, coordinates, parentCoordinates, this.id, isEditable);
/*      */           }
/*  479 */           else if (checkParentAsDependentChild(groupId + "", resId + "", map)) {
/*  480 */             data.put("$edgeType", "double_way_arrow");
/*      */           }
/*      */           
/*  483 */           String divToAdd = "";
/*  484 */           if ((jsonArrayForChildren.length() > 0) || (isDependentMGroup)) {
/*  485 */             divToAdd = getDivToAddToBVGroupNode(expandClass);
/*      */           }
/*  487 */           childDetails.put("children", jsonArrayForChildren);
/*  488 */           String nameOfChild = getGroupDiv(nodeId + "", resId, statusClass, divToAdd, url, dispName, isDependentMGroup, isEditable);
/*      */           
/*  490 */           childDetails.put("name", nameOfChild);
/*      */         } else {
/*  492 */           imagepath = this.admUtil.getNewDependencyImage(type, imagepath);
/*  493 */           String childNameWithImage = getMonitorDivForBV(extDeviceMap, site24x7List, resId, groupId, nodeId + "", imagepath, statusClass, url, dispName, isEditable);
/*  494 */           childDetails.put("name", childNameWithImage);
/*      */         }
/*  496 */         childDetails.put("data", data);
/*      */       }
/*      */       catch (JSONException e) {
/*  499 */         e.printStackTrace();
/*      */       }
/*  501 */       groupWithArray.put(childDetails);
/*      */     }
/*  503 */     return groupWithArray;
/*      */   }
/*      */   
/*      */   public static String getMonitorDivForBV(HashMap extDeviceMap, HashMap site24x7List, String resId, String groupId, String nodeId, String imagepath, String statusClass, String url, String dispName, boolean isEditable) {
/*  507 */     String childNameWithImage = "<div class=\"iconBox monitorIcon\">";
/*  508 */     String redirect = "<a class=\"redirectToDetails\"  href=\"javascript:MM_openBrWindow('" + url + "','extDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\">";
/*  509 */     if ((!extDeviceMap.containsKey(resId)) && (!site24x7List.containsKey(resId))) {
/*  510 */       if (isEditable) {
/*  511 */         childNameWithImage = childNameWithImage + "<span class='iconDelete'  onclick='removeMonitorsFromBV(\"" + resId + "\",\"" + groupId + "\",\"" + nodeId + "\")'></span>";
/*      */       }
/*  513 */       redirect = "<a class=\"redirectToDetails\"  href=\"" + url + "\" target=\"_blank\">";
/*      */     }
/*  515 */     childNameWithImage = childNameWithImage + "<span id=\"nameIdentifier\" class=\"" + statusClass + "\"><img src='" + imagepath + "'></span><i> " + redirect + dispName + "</a></i></div>";
/*  516 */     return childNameWithImage;
/*      */   }
/*      */   
/*      */   public static String getDivToAddToBVGroupNode(String expandClass) {
/*  520 */     return "<u class=\"" + expandClass + "\"><p class=\"hoverCon\">" + FormatUtil.getString("am.webclient.jitview.tooltip.text") + "</p></u>";
/*      */   }
/*      */   
/*      */   public static String getGroupDiv(String nodeId, String resId, String statusClass, String divToAdd, String url, String dispName, boolean isDependentGroup, boolean isEditable) {
/*  524 */     String groupFunc = "<span class='iconAddNode' onclick='addToBusinessService(\"" + nodeId + "\")'></span><span class='iconEdit' onclick='editGroupNode(\"" + resId + "\", \"" + nodeId + "\")'></span><span class=\"iconDelete\" onclick='removeGroupFromBV(\"" + resId + "\",\"" + nodeId + "\")'></span>";
/*  525 */     if ((isDependentGroup) || (!isEditable)) {
/*  526 */       groupFunc = "";
/*      */     }
/*  528 */     return "<div class=\"iconBox\">" + groupFunc + "<span id=\"nameIdentifier\" class=\"" + statusClass + " groupMon\"><img src='../images/icon_sg.png'/>" + divToAdd + "</span><i><a class=\"redirectToDetails\"  href=\"" + url + "\" target=\"_blank\" id=\"dispName_" + nodeId + "\"> " + dispName + "</a></i></div>";
/*      */   }
/*      */   
/*      */   public static Properties getCoordProps(String viewid)
/*      */   {
/*  533 */     Properties coordinates = new Properties();
/*  534 */     ResultSet rs = null;
/*      */     try {
/*  536 */       String query = "select PARENTID,CHILDID,XCOORD ,YCOORD from AM_FLASHVIEW_COORDINATES where  VIEWID=" + viewid;
/*  537 */       AMLog.debug("Inside getCoordProps ...." + query);
/*  538 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/*  539 */       while (rs.next())
/*      */       {
/*  541 */         String parentid = rs.getString("PARENTID");
/*  542 */         String chilid = rs.getString("CHILDID");
/*  543 */         String xcoord = rs.getString("XCOORD");
/*  544 */         String ycoord = rs.getString("YCOORD");
/*  545 */         coordinates.setProperty(parentid + "_" + chilid + "_x", xcoord);
/*  546 */         coordinates.setProperty(parentid + "_" + chilid + "_y", ycoord);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  551 */       ex.printStackTrace();
/*      */     } finally {
/*  553 */       AMConnectionPool.getInstance();AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  555 */     return coordinates;
/*      */   }
/*      */   
/*      */   private boolean checkWhetherToAdd(Properties view, String type, String severity, boolean isTopLevel) {
/*  559 */     boolean showCriticalMonitors = view.getProperty("SHOWCRITICALMONITORS").equals("true");
/*  560 */     boolean showOnlySubgroups = view.getProperty("SHOWONLYSUBGROUPS").equals("true");
/*  561 */     boolean showOnlyTopSubgroups = false;
/*  562 */     showOnlyTopSubgroups = (view.getProperty("SHOWTOPLEVELSUBMGS") != null) && (view.getProperty("SHOWTOPLEVELSUBMGS").equals("true"));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  569 */     if ((type.equals("HAI")) || (type.equals("MongoDB")))
/*      */     {
/*  571 */       if ((showOnlyTopSubgroups) && (!isTopLevel)) {
/*  572 */         return false;
/*      */       }
/*  574 */       return true;
/*      */     }
/*      */     
/*  577 */     if (((!showOnlySubgroups) && (!showOnlyTopSubgroups)) || ((showCriticalMonitors) && ((showOnlySubgroups) || (showOnlyTopSubgroups)))) {
/*  578 */       if ((showCriticalMonitors) && (!severity.equals("1"))) {
/*  579 */         return false;
/*      */       }
/*  581 */       return true;
/*      */     }
/*      */     
/*  584 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getURLForRedirection(String resourceId, String type, HashMap site24x7List, HashMap extDeviceMap)
/*      */   {
/*  591 */     String url = "";
/*      */     
/*  593 */     if ((site24x7List != null) && (site24x7List.get(resourceId) != null)) {
/*  594 */       return "/extDeviceAction.do?method=site24x7Reports&resourceid=" + resourceId;
/*      */     }
/*  596 */     if ((type.equals("HAI")) || (type.equals("MongoDB"))) {
/*  597 */       url = "/showapplication.do?method=showApplication&haid=" + resourceId + "&selectM=flashview";
/*  598 */       if (type.equals("MongoDB"))
/*      */       {
/*  600 */         url = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceId + "&selectM=flashview";
/*      */       }
/*      */     }
/*  603 */     else if (ChildMOHandler.isChildMonitorTypeSupportedForMG(type))
/*      */     {
/*  605 */       url = "/showapplication.do?method=showChildApplicationDetail&resId=" + resourceId;
/*      */     }
/*      */     else
/*      */     {
/*  609 */       url = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceId;
/*  610 */       if ((extDeviceMap != null) && (extDeviceMap.get(resourceId) != null))
/*      */       {
/*  612 */         url = (String)extDeviceMap.get(resourceId);
/*      */       }
/*      */     }
/*  615 */     return url;
/*      */   }
/*      */   
/*      */   public boolean checkParentAsDependentChild(String parentId, String groupId, HashMap map)
/*      */   {
/*  620 */     ArrayList childList = (ArrayList)map.get(groupId);
/*  621 */     if (childList == null) {
/*  622 */       return false;
/*      */     }
/*  624 */     for (int i = 0; i < childList.size(); i++)
/*      */     {
/*  626 */       ArrayList elementList = (ArrayList)childList.get(i);
/*  627 */       String resId = (String)elementList.get(0);
/*  628 */       if (parentId.equals(resId)) {
/*  629 */         return true;
/*      */       }
/*      */     }
/*  632 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getQueryforOperator(String haid)
/*      */   {
/*  643 */     String haidConditon = " ";
/*  644 */     if (this.haids.length() > 2) {
/*  645 */       haidConditon = " and MAP.CHILDID in " + this.haids;
/*      */     }
/*  647 */     String query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.displayname,AM_ManagedObject.TYPE,AM_ManagedResourceType.RESOURCEGROUP, AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.IMAGEPATH,AM_UnManagedNodes.resid from AM_PARENTCHILDMAPPER as MAP join AM_ManagedObject  on AM_ManagedObject.RESOURCEID=MAP.CHILDID  join AM_ManagedResourceType as AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where MAP.PARENTID= " + haid + haidConditon;
/*  648 */     return query;
/*      */   }
/*      */   
/*      */   public void checkForChildsAndAddToMap(HashMap map, String haid, ArrayList resIdList, ArrayList attIdList, Hashtable healthkeys, Properties requestProps, int level, boolean isHtml)
/*      */   {
/*  653 */     ArrayList childList = new ArrayList();
/*  654 */     String userName = (String)requestProps.get("userNamae");
/*  655 */     String query = "";
/*  656 */     boolean isOperator = ((Boolean)requestProps.get("isOperator")).booleanValue();
/*  657 */     if ((!isOperator) && (!this.isOwner)) {
/*  658 */       query = getQueryforOperator(haid);
/*      */     }
/*      */     else {
/*  661 */       query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.RESOURCEGROUP, AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME, AM_ManagedResourceType.IMAGEPATH as IMAGEPATH, AM_ManagedObject.RESOURCENAME as RESOURCENAME from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + haid;
/*      */     }
/*  663 */     ResultSet adminRs = null;
/*  664 */     Hashtable<String, String> resIdVsType = new Hashtable();
/*      */     try
/*      */     {
/*  667 */       adminRs = AMConnectionPool.executeQueryStmt(query);
/*  668 */       String resIdStr = null;
/*  669 */       String resTypeStr = null;
/*  670 */       String resourceName = null;
/*  671 */       while (adminRs.next())
/*      */       {
/*  673 */         resIdStr = adminRs.getString("RESOURCEID");
/*  674 */         resTypeStr = adminRs.getString("TYPE");
/*  675 */         resourceName = adminRs.getString("RESOURCENAME");
/*  676 */         ArrayList list = new ArrayList();
/*  677 */         list.add(resIdStr);
/*  678 */         list.add(adminRs.getString("DISPLAYNAME"));
/*  679 */         list.add(resTypeStr);
/*  680 */         if (isHtml) {
/*  681 */           list.add(adminRs.getString("IMAGEPATH"));
/*      */         } else {
/*  683 */           list.add(getImage(adminRs.getString("RESOURCEGROUP"), resTypeStr));
/*      */         }
/*  685 */         list.add(getImageLable(resTypeStr));
/*  686 */         if (resTypeStr.equals("HAI"))
/*      */         {
/*      */ 
/*  689 */           list.add(getMGTypeName(resIdStr));
/*      */         }
/*      */         else
/*      */         {
/*  693 */           list.add(FormatUtil.getString(adminRs.getString("TYPEDISPLAYNAME")));
/*      */         }
/*  695 */         list.add(resourceName);
/*  696 */         resIdList.add(resIdStr);
/*  697 */         attIdList.add(healthkeys.get(resTypeStr));
/*  698 */         childList.add(list);
/*  699 */         resIdVsType.put(resIdStr, resTypeStr);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  704 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  708 */       AMConnectionPool.closeStatement(adminRs);
/*      */     }
/*      */     
/*  711 */     ArrayList<HashMap<String, String>> childMonitorInfo = ChildMOHandler.listChildMonitorUnderMG(haid);
/*  712 */     String resIdStr; String resTypeStr; if (childMonitorInfo != null)
/*      */     {
/*  714 */       resIdStr = null;
/*  715 */       resTypeStr = null;
/*  716 */       for (Object monitorInfo : childMonitorInfo)
/*      */       {
/*  718 */         resIdStr = (String)((HashMap)monitorInfo).get("resourceid");
/*  719 */         resTypeStr = (String)((HashMap)monitorInfo).get("type");
/*  720 */         ArrayList list = new ArrayList();
/*  721 */         list.add(resIdStr);
/*  722 */         list.add(((HashMap)monitorInfo).get("displayname"));
/*  723 */         list.add(resTypeStr);
/*  724 */         if (isHtml) {
/*  725 */           list.add(ChildMOHandler.getImageForChildType(resTypeStr));
/*      */         } else {
/*  727 */           list.add(ChildMOHandler.getImageForChildType(resTypeStr));
/*      */         }
/*  729 */         list.add(getImageLable(resTypeStr));
/*  730 */         if (resTypeStr.equals("HAI"))
/*      */         {
/*  732 */           list.add(getMGTypeName(resIdStr));
/*      */         }
/*      */         else
/*      */         {
/*  736 */           list.add(FormatUtil.getString(resTypeStr));
/*      */         }
/*  738 */         resIdList.add(resIdStr);
/*  739 */         attIdList.add(healthkeys.get(resTypeStr));
/*  740 */         childList.add(list);
/*  741 */         resIdVsType.put(resIdStr, resTypeStr);
/*      */       }
/*      */     }
/*  744 */     addDependentMGroups(haid, resIdList, attIdList, childList, healthkeys, resIdVsType);
/*  745 */     for (Enumeration<String> typeKeyEnum = resIdVsType.keys(); typeKeyEnum.hasMoreElements();)
/*      */     {
/*  747 */       String resIdKey = (String)typeKeyEnum.nextElement();
/*  748 */       String resType = (String)resIdVsType.get(resIdKey);
/*  749 */       if ((resType.equals("HAI")) || (resType.equals("MongoDB")) || (("Dependent-Group".equals(resType)) && (level < 2)))
/*      */       {
/*  751 */         if ("Dependent-Group".equals(resType)) {
/*  752 */           level++;
/*      */         }
/*  754 */         checkForChildsAndAddToMap(map, resIdKey, resIdList, attIdList, healthkeys, requestProps, level, isHtml);
/*      */       }
/*      */     }
/*  757 */     map.put(haid, childList);
/*      */   }
/*      */   
/*      */ 
/*      */   public String getImage(String resourcegroup, String resourcetype)
/*      */   {
/*  763 */     String imagepath = "";
/*      */     try
/*      */     {
/*  766 */       if (resourcetype.equals("Script Monitor"))
/*      */       {
/*  768 */         imagepath = "/images/fl/SCR.swf";
/*      */       }
/*  770 */       else if (resourcetype.equals("OracleEBS"))
/*      */       {
/*  772 */         imagepath = "/images/fl/APP.swf";
/*      */       }
/*  774 */       else if (resourcetype.equals("Generic WMI"))
/*      */       {
/*  776 */         imagepath = "/images/fl/WMI.swf";
/*      */       }
/*  778 */       else if (resourcetype.equals("file"))
/*      */       {
/*  780 */         imagepath = "/images/fl/FIL.swf";
/*      */       }
/*  782 */       else if (resourcetype.equals("SNMP"))
/*      */       {
/*  784 */         imagepath = "/images/fl/SNM.swf";
/*      */       }
/*  786 */       else if (resourcetype.equals("QueryMonitor"))
/*      */       {
/*  788 */         imagepath = "/images/fl/DBQ.swf";
/*      */       }
/*  790 */       else if (resourcetype.equals("HAI"))
/*      */       {
/*  792 */         imagepath = "/images/fl/HAI.swf";
/*      */       }
/*  794 */       else if (resourcegroup.equals("EMO"))
/*      */       {
/*  796 */         imagepath = "/images/fl/URL.swf";
/*      */       }
/*      */       else
/*      */       {
/*  800 */         imagepath = "/images/fl/" + resourcegroup + ".swf";
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  805 */       ex.printStackTrace();
/*      */     }
/*  807 */     return imagepath;
/*      */   }
/*      */   
/*      */   public void addDependentMGroups(String haid, ArrayList resIdList, ArrayList attIdList, ArrayList childList, Hashtable healthkeys, Hashtable<String, String> resIdVsType) {
/*      */     try {
/*  812 */       if (haid.equals("0"))
/*      */       {
/*  814 */         return;
/*      */       }
/*      */       
/*  817 */       ArrayList result = getDependentMGroupsDetails(haid);
/*  818 */       if ((result == null) || (result.size() == 0)) {
/*  819 */         return;
/*      */       }
/*  821 */       for (int i = 0; i < result.size(); i++) {
/*  822 */         ArrayList innerList = (ArrayList)result.get(i);
/*  823 */         String resIdStr = (String)innerList.get(0);
/*  824 */         String resTypeStr = (String)innerList.get(3);
/*  825 */         ArrayList list = new ArrayList();
/*  826 */         list.add(resIdStr);
/*  827 */         list.add((String)innerList.get(1));
/*  828 */         list.add(resTypeStr);
/*  829 */         list.add("/images/fl/HAI.swf");
/*  830 */         list.add("");
/*  831 */         list.add("Dependent-Group");
/*  832 */         resIdList.add(resIdStr);
/*  833 */         attIdList.add(healthkeys.get(resTypeStr));
/*  834 */         childList.add(list);
/*  835 */         resIdVsType.put(resIdStr, "Dependent-Group");
/*      */       }
/*      */     } catch (Exception ex) {
/*  838 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getDependentMGroupsDetails(String haid)
/*      */   {
/*  848 */     ArrayList result = new ArrayList();
/*      */     try {
/*  850 */       HashSet<String> dependentMGroups = RuleAnalyser.getDependentMonitorGroups(haid, false);
/*  851 */       if (dependentMGroups.size() == 0) {
/*  852 */         return result;
/*      */       }
/*  854 */       String condn = dependentMGroups.toString();
/*  855 */       condn = condn.substring(1, condn.length() - 1);
/*  856 */       condn = condn.replaceAll(", ", ",");
/*  857 */       String query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.displayname,AM_ManagedObject.type, -1, COALESCE(AM_ManagedResourceType.SHORTNAME,AM_ManagedResourceType.RESOURCETYPE),AM_ManagedResourceType.IMAGEPATH,AM_UnManagedNodes.resid from  AM_ManagedResourceType join AM_ManagedObject on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_ManagedObject.RESOURCEID in (" + condn + ") and AM_ManagedResourceType.RESOURCEGROUP='HAI' order by AM_ManagedObject.RESOURCENAME";
/*  858 */       result = new ManagedApplication().getRows(query);
/*      */     } catch (Exception ex) {
/*  860 */       ex.printStackTrace();
/*      */     }
/*  862 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getImageLable(String resourcetype)
/*      */   {
/*  868 */     String imagelable = "";
/*      */     try
/*      */     {
/*  871 */       if (resourcetype.equals("SNMP"))
/*      */       {
/*  873 */         imagelable = "snm";
/*      */       }
/*  875 */       else if (resourcetype.equals("WindowsNT "))
/*      */       {
/*  877 */         imagelable = "win";
/*      */       }
/*  879 */       else if (resourcetype.equals("Linux"))
/*      */       {
/*  881 */         imagelable = "lin";
/*      */       }
/*  883 */       else if (resourcetype.equals("SUN"))
/*      */       {
/*  885 */         imagelable = "sun";
/*      */       }
/*  887 */       else if (resourcetype.equals("SUN PC"))
/*      */       {
/*  889 */         imagelable = "sun pc";
/*      */       }
/*  891 */       else if (resourcetype.equals("Windows 2000"))
/*      */       {
/*  893 */         imagelable = "win";
/*      */       }
/*  895 */       else if (resourcetype.equals("Windows 2003"))
/*      */       {
/*  897 */         imagelable = "win";
/*      */       }
/*  899 */       else if (resourcetype.equals("Windows XP"))
/*      */       {
/*  901 */         imagelable = "win";
/*      */       }
/*  903 */       else if (resourcetype.equals("WEB-server"))
/*      */       {
/*  905 */         imagelable = "web";
/*      */       }
/*  907 */       else if (resourcetype.equals("MAIL-server"))
/*      */       {
/*  909 */         imagelable = "mail";
/*      */       }
/*  911 */       else if (resourcetype.equals("Port-Test"))
/*      */       {
/*  913 */         imagelable = "port";
/*      */       }
/*  915 */       else if (resourcetype.equals("WebSphere-server"))
/*      */       {
/*  917 */         imagelable = "was";
/*      */       }
/*  919 */       else if (resourcetype.equals("WEBLOGIC-server"))
/*      */       {
/*  921 */         imagelable = "wls";
/*      */       }
/*  923 */       else if (resourcetype.equals("WLS-Cluster"))
/*      */       {
/*  925 */         imagelable = "wlc";
/*      */       }
/*  927 */       else if (resourcetype.equals("Tomcat-server"))
/*      */       {
/*  929 */         imagelable = "tom";
/*      */       }
/*  931 */       else if (resourcetype.equals("JBOSS-server"))
/*      */       {
/*  933 */         imagelable = "jbo";
/*      */       }
/*  935 */       else if (resourcetype.equals("MYSQL-DB-server"))
/*      */       {
/*  937 */         imagelable = "mys";
/*      */       }
/*  939 */       else if (resourcetype.equals("MSSQL-DB-server"))
/*      */       {
/*  941 */         imagelable = "mss";
/*      */       }
/*  943 */       else if (resourcetype.equals("ORACLE-DB-server"))
/*      */       {
/*  945 */         imagelable = "ora";
/*      */       }
/*  947 */       else if (resourcetype.equals("UrlMonitor"))
/*      */       {
/*  949 */         imagelable = "url";
/*      */       }
/*  951 */       else if (resourcetype.equals("UrlSeq"))
/*      */       {
/*  953 */         imagelable = "urs";
/*      */       }
/*  955 */       else if (resourcetype.equals("RBM"))
/*      */       {
/*  957 */         imagelable = "rbm";
/*      */       }
/*  959 */       else if (resourcetype.equals("RMI"))
/*      */       {
/*  961 */         imagelable = "rmi";
/*      */       }
/*  963 */       else if (resourcetype.equals("JMX1.2-MX4J-RMI"))
/*      */       {
/*  965 */         imagelable = "jmx";
/*      */       }
/*  967 */       else if (resourcetype.equals("Apache-server"))
/*      */       {
/*  969 */         imagelable = "apa";
/*      */       }
/*  971 */       else if (resourcetype.equals("IIS-server"))
/*      */       {
/*  973 */         imagelable = "iis";
/*      */       }
/*  975 */       else if (resourcetype.equals("DB2-server"))
/*      */       {
/*  977 */         imagelable = "db2";
/*      */       }
/*  979 */       else if (resourcetype.equals("Script Monitor"))
/*      */       {
/*  981 */         imagelable = "scr";
/*      */       }
/*  983 */       else if (resourcetype.equals("PHP"))
/*      */       {
/*  985 */         imagelable = "php";
/*      */       }
/*  987 */       else if (resourcetype.equals("AIX"))
/*      */       {
/*  989 */         imagelable = "aix";
/*      */       }
/*  991 */       else if (resourcetype.equals("HP-UX"))
/*      */       {
/*  993 */         imagelable = "hpu";
/*      */       }
/*  995 */       else if (resourcetype.equals("Ping Monitor"))
/*      */       {
/*  997 */         imagelable = "ping";
/*      */       }
/*  999 */       else if (resourcetype.equals("FreeBSD"))
/*      */       {
/* 1001 */         imagelable = "bsd";
/*      */       }
/* 1003 */       else if (resourcetype.equals("TELNET"))
/*      */       {
/* 1005 */         imagelable = "tel";
/*      */       }
/* 1007 */       else if (resourcetype.equals("Exchange-server"))
/*      */       {
/* 1009 */         imagelable = "exc";
/*      */       }
/* 1011 */       else if (resourcetype.equals(".Net"))
/*      */       {
/* 1013 */         imagelable = ".net";
/*      */       }
/* 1015 */       else if (resourcetype.equals("WTA"))
/*      */       {
/* 1017 */         imagelable = "wta";
/*      */       }
/* 1019 */       else if (resourcetype.equals("HP-TRU64"))
/*      */       {
/* 1021 */         imagelable = "hp64";
/*      */       }
/* 1023 */       else if (resourcetype.equals("ORACLE-APP-server"))
/*      */       {
/* 1025 */         imagelable = "oas";
/*      */       }
/* 1027 */       else if (resourcetype.equals("Mac OS"))
/*      */       {
/* 1029 */         imagelable = "mac";
/*      */       }
/* 1031 */       else if (resourcetype.equals("Generic WMI"))
/*      */       {
/* 1033 */         imagelable = "perf";
/*      */       }
/* 1035 */       else if (resourcetype.equals("Web Service"))
/*      */       {
/* 1037 */         imagelable = "wseb";
/*      */       }
/* 1039 */       else if (resourcetype.equals("JDK1.5"))
/*      */       {
/* 1041 */         imagelable = "jdk";
/*      */       }
/* 1043 */       else if (resourcetype.equals("WEBLOGIC-Integration"))
/*      */       {
/* 1045 */         imagelable = "wli";
/*      */       }
/* 1047 */       else if (resourcetype.equals("file"))
/*      */       {
/* 1049 */         imagelable = "file";
/*      */       }
/* 1051 */       else if (resourcetype.equals("SAP"))
/*      */       {
/* 1053 */         imagelable = "sap";
/*      */       }
/* 1055 */       else if (resourcetype.equals("SYBASE-DB-server"))
/*      */       {
/* 1057 */         imagelable = "syb";
/*      */       }
/* 1059 */       else if (resourcetype.equals("Windows Vista"))
/*      */       {
/* 1061 */         imagelable = "win";
/*      */       }
/* 1063 */       else if (resourcetype.equals("GlassFish"))
/*      */       {
/* 1065 */         imagelable = "gla";
/*      */       }
/* 1067 */       else if (resourcetype.equals("WebsphereMQ"))
/*      */       {
/* 1069 */         imagelable = "mq";
/*      */       }
/* 1071 */       else if (resourcetype.equals("QueryMonitor"))
/*      */       {
/* 1073 */         imagelable = "sql";
/*      */       }
/* 1075 */       else if (resourcetype.equals("OfficeSharePointServer"))
/*      */       {
/* 1077 */         imagelable = "oss";
/*      */       }
/* 1079 */       else if (resourcetype.equals("SilverStream"))
/*      */       {
/* 1081 */         imagelable = "sil";
/*      */       }
/* 1083 */       else if (resourcetype.equals("HAI"))
/*      */       {
/* 1085 */         imagelable = "";
/*      */       }
/*      */       else
/*      */       {
/* 1089 */         imagelable = "";
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1094 */       ex.printStackTrace();
/*      */     }
/* 1096 */     return imagelable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getMGTypeName(String resid)
/*      */   {
/* 1103 */     ManagedApplication mo = new ManagedApplication();
/* 1104 */     String retName = FormatUtil.getString("am.webclient.common.util.MGSTR");
/* 1105 */     String typeQuery = "select ha.TYPE,ha.GROUPTYPE, AMT.DISPLAYNAME from AM_HOLISTICAPPLICATION ha,AM_MONITORGROUP_TYPES as AMT where GROUPTYPE=TYPEID and ha.HAID=" + resid;
/* 1106 */     ArrayList mgType = mo.getRows(typeQuery);
/* 1107 */     for (int n = 0; n < mgType.size(); n++)
/*      */     {
/* 1109 */       ArrayList mgDetails = (ArrayList)mgType.get(n);
/* 1110 */       String type = (String)mgDetails.get(0);
/* 1111 */       String gtype = (String)mgDetails.get(1);
/* 1112 */       String displayName = (String)mgDetails.get(2);
/* 1113 */       if ((!"0".equals(type)) || (!"1".equals(gtype)))
/*      */       {
/* 1115 */         retName = FormatUtil.getString(displayName.trim());
/*      */       }
/*      */     }
/*      */     
/* 1119 */     return retName;
/*      */   }
/*      */   
/*      */ 
/*      */   private static void getResourceIdsForGraph(JSONObject jsonFromCache, Properties resourceIdProps)
/*      */   {
/* 1125 */     getResourceIdsFromJSON(jsonFromCache, resourceIdProps);
/* 1126 */     resourceIdProps.setProperty("resourceId", "(" + resourceIdProps.getProperty("resourceId") + "-1)");
/*      */   }
/*      */   
/*      */   private static void getResourceIdsFromJSON(JSONObject jsonFromCache, Properties resourceIdProps) {
/* 1130 */     String resourceId = resourceIdProps.getProperty("resourceId", "");
/*      */     try {
/* 1132 */       if (jsonFromCache.has("children")) {
/* 1133 */         resourceId = resourceId + jsonFromCache.getJSONObject("data").getString("$resourceID") + ",";
/* 1134 */         JSONArray children = jsonFromCache.getJSONArray("children");
/* 1135 */         resourceIdProps.setProperty("resourceId", resourceId);
/* 1136 */         for (int i = 0; i < children.length(); i++) {
/* 1137 */           getResourceIdsFromJSON(children.getJSONObject(i), resourceIdProps);
/*      */         }
/*      */       } else {
/* 1140 */         String monitorResourceId = jsonFromCache.getJSONObject("data").getString("$resourceID");
/* 1141 */         if (!resourceId.contains(monitorResourceId)) {
/* 1142 */           resourceId = resourceId + monitorResourceId + ",";
/* 1143 */           resourceIdProps.setProperty("resourceId", resourceId);
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 1147 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void deleteBussinessViewCache(String key)
/*      */   {
/* 1156 */     if (DBUtil.businessViewDetails != null) {
/* 1157 */       DBUtil.businessViewDetails.remove(key);
/*      */     }
/*      */   }
/*      */   
/*      */   public JSONObject getDataForGlobalView(String viewid, int noOfColumns) {
/* 1162 */     JSONObject dataForGlobalView = new JSONObject();
/*      */     try {
/* 1164 */       HashMap displayNames = new HashMap();
/* 1165 */       ManagedApplication mo = new ManagedApplication();
/* 1166 */       String filterquery = "select HAID,AM_ManagedObject.DISPLAYNAME from AM_FLASHVIEW_FILTER,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_FLASHVIEW_FILTER.HAID and VIEWID=" + viewid + " order by DISPLAYNAME";
/* 1167 */       ArrayList filterlist = mo.getRows(filterquery);
/* 1168 */       if (filterlist.size() <= 0)
/*      */       {
/* 1170 */         filterquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=0  order by DISPLAYNAME";
/* 1171 */         filterlist = mo.getRows(filterquery);
/*      */       }
/* 1173 */       ArrayList resIDs_mg = new ArrayList();
/* 1174 */       ArrayList attribIDs_mg = new ArrayList();
/* 1175 */       attribIDs_mg.add("17");
/* 1176 */       attribIDs_mg.add("18");
/*      */       
/*      */ 
/* 1179 */       String[] mgs = new String[filterlist.size()];
/* 1180 */       for (int i = 0; i < filterlist.size(); i++)
/*      */       {
/* 1182 */         ArrayList singleRow = (ArrayList)filterlist.get(i);
/* 1183 */         mgs[i] = ((String)singleRow.get(0));
/* 1184 */         displayNames.put(mgs[i], singleRow.get(1));
/* 1185 */         resIDs_mg.add(mgs[i]);
/*      */       }
/*      */       
/* 1188 */       Properties alert_mg = FaultUtil.getStatus(resIDs_mg, attribIDs_mg);
/* 1189 */       String innerHtml = "";
/* 1190 */       String tdClass = "";
/* 1191 */       for (int i = 0; i < mgs.length; i++) {
/* 1192 */         String monitorGroup = mgs[i];
/* 1193 */         String mgurl = "/showapplication.do?method=showApplication&haid=" + monitorGroup;
/* 1194 */         String key1 = monitorGroup + "#" + "18" + "#" + "MESSAGE";
/* 1195 */         String key2 = monitorGroup + "#" + "18";
/* 1196 */         String key3 = monitorGroup + "#" + "17";
/* 1197 */         String message = alert_mg.getProperty(key1);
/* 1198 */         if (message == null) {
/* 1199 */           message = "unknown";
/*      */         }
/* 1201 */         if (((i + 1) % noOfColumns == 1) || (noOfColumns == 1)) {
/* 1202 */           innerHtml = innerHtml + "<tr>";
/*      */         }
/* 1204 */         if ("5".equals(alert_mg.getProperty(key2))) {
/* 1205 */           tdClass = "bgUp";
/* 1206 */         } else if ("1".equals(alert_mg.getProperty(key2))) {
/* 1207 */           if ("1".equals(alert_mg.getProperty(key3))) {
/* 1208 */             tdClass = "bgDown";
/* 1209 */           } else if ("5".equals(alert_mg.getProperty(key3))) {
/* 1210 */             tdClass = "bgCritical";
/*      */           } else {
/* 1212 */             tdClass = "";
/*      */           }
/* 1214 */         } else if ("4".equals(alert_mg.getProperty(key2))) {
/* 1215 */           tdClass = "bgWarning";
/*      */         } else {
/* 1217 */           tdClass = "";
/*      */         }
/* 1219 */         innerHtml = innerHtml + "<td class='statusMonitorGlobal " + tdClass + "'>  <div class='statusImgBlock'>" + "<a href=" + mgurl + " target='_top' onMouseOut='hideddrivetip()'" + " onMouseOver=\"ddrivetip(this,event,'" + message + "',false,true,'#000000',200,'lightyellow')\" style='cursor: pointer;'>" + "<img src=/images/icon_monitor_status.png><span>" + displayNames.get(monitorGroup) + "</span></a></div>";
/*      */         
/*      */ 
/*      */ 
/* 1223 */         if (((i + 1) % noOfColumns == 0) || (noOfColumns == 1)) {
/* 1224 */           innerHtml = innerHtml + "</tr>";
/*      */         }
/*      */       }
/* 1227 */       dataForGlobalView.put("innerHtml", innerHtml);
/* 1228 */       dataForGlobalView.put("noOfColumns", noOfColumns);
/*      */     } catch (Exception e) {
/* 1230 */       e.printStackTrace();
/*      */     }
/* 1232 */     return dataForGlobalView;
/*      */   }
/*      */   
/*      */   public static JSONObject getLatestStatusForBusinessView(Hashtable healthkeys, String resources, String haid, String viewid, boolean isCache) {
/* 1236 */     StringBuilder query = new StringBuilder("select AM_ManagedObject.RESOURCEID ,SEVERITY ,MMESSAGE,CATEGORY,AM_ManagedObject.DISPLAYNAME as displayName,AM_ManagedResourceType.DISPLAYNAME as typeDisplayName, AM_ManagedObject.TYPE as groupType  from Alert inner join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type  WHERE   GROUPNAME='AppManager'");
/* 1237 */     JSONObject rootNode = new JSONObject();
/* 1238 */     if (resources.length() < 3) {
/* 1239 */       return rootNode;
/*      */     }
/* 1241 */     if (!isCache) {
/* 1242 */       long defaultOldTime = System.currentTimeMillis() - 60000L;
/* 1243 */       if ((viewid != null) && (!viewid.equals("-1")))
/*      */       {
/*      */         try
/*      */         {
/* 1247 */           long interval = 600000L;
/* 1248 */           Properties view = getViewforViewid(viewid);
/* 1249 */           String statusinterval = view.getProperty("STATUSUPDATEINTERVAL");
/* 1250 */           if (statusinterval != null)
/*      */           {
/* 1252 */             interval = Long.parseLong(statusinterval);
/* 1253 */             defaultOldTime = System.currentTimeMillis() - (2L + interval) * 1000L;
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1258 */           ex.printStackTrace();
/*      */         }
/*      */       }
/* 1261 */       query = query.append(" and Alert.MODTIME > " + defaultOldTime);
/*      */     }
/* 1263 */     query = query.append(" and SOURCE IN " + resources);
/* 1264 */     AMLog.debug("GraphicalView.getLatestStatus() query : " + query.toString());
/* 1265 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1268 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query.toString());
/* 1269 */       while (rs.next())
/*      */       {
/* 1271 */         String attributeid = rs.getString("CATEGORY");
/* 1272 */         if (healthkeys.containsValue(attributeid))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1277 */           String resourceid = rs.getString("RESOURCEID");
/* 1278 */           String severity = rs.getString("SEVERITY");
/* 1279 */           String message = rs.getString("MMESSAGE");
/* 1280 */           String type = rs.getString("groupType");
/* 1281 */           String dispName = rs.getString("displayName");
/* 1282 */           String typedisplayname = rs.getString("typeDisplayName");
/*      */           
/*      */ 
/* 1285 */           if (message != null)
/*      */           {
/* 1287 */             message = "<p> <label>" + FormatUtil.getString("am.webclient.common.name.text") + " : </label><b>" + dispName + "</b></p><p><label>" + FormatUtil.getString("am.webclient.common.type.text") + " : </label><b>" + typedisplayname + "</b></p><p>" + message + "</p>";
/*      */           }
/*      */           else
/*      */           {
/* 1291 */             message = "<p> <label>" + FormatUtil.getString("am.webclient.common.name.text") + " : </label><b>" + dispName + "</b></p><p><label>" + FormatUtil.getString("am.webclient.common.type.text") + " :  </label><b>" + typedisplayname + "</b></p>";
/*      */           }
/*      */           
/* 1294 */           String statusClass = severityClassDetails.getProperty(severity, "");
/* 1295 */           if ("HAI".equals(type)) {
/* 1296 */             if (haid.equals(resourceid)) {
/* 1297 */               statusClass = statusClass + " cloudBox";
/*      */             } else {
/* 1299 */               statusClass = statusClass + " groupMon";
/*      */             }
/*      */           }
/* 1302 */           JSONObject node = new JSONObject();
/* 1303 */           node.put("severity", statusClass);
/* 1304 */           node.put("message", message);
/* 1305 */           rootNode.put(resourceid, node);
/*      */         }
/*      */       }
/* 1308 */       if (haid.equals("0"))
/*      */       {
/* 1310 */         JSONObject node = new JSONObject();
/* 1311 */         String message = FormatUtil.getString("am.webclient.hometab.text");
/* 1312 */         node.put("message", message);
/* 1313 */         node.put("severity", " cloudBox");
/* 1314 */         rootNode.put(haid, node);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1318 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1322 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1324 */     return rootNode;
/*      */   }
/*      */   
/*      */   public static Properties getViewforViewid(String viewid)
/*      */   {
/* 1329 */     String query = "select VIEWID,ZOOMLEVEL,SHOWLABEL,STATUSUPDATEINTERVAL,SHOWCRITICALMONITORS,SHOWONLYSUBGROUPS,SHOWTOPLEVELMGS,SHOWTOPLEVELSUBMGS,XCANVAS,YCANVAS,BGCOLOR,LINECOLOR,LABELCOLOR,LINETRANSPARENCY,LINETHICKNESS from AM_MONITORGROUP_FLASHVIEWCONFIG where viewid=" + viewid;
/*      */     
/* 1331 */     Properties view = new Properties();
/* 1332 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1335 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/* 1336 */       if (rs.next())
/*      */       {
/* 1338 */         view.setProperty("VIEWID", rs.getString("VIEWID"));
/* 1339 */         view.setProperty("ZOOMLEVEL", rs.getString("ZOOMLEVEL"));
/* 1340 */         view.setProperty("SHOWLABEL", rs.getString("SHOWLABEL"));
/* 1341 */         view.setProperty("SHOWCRITICALMONITORS", rs.getString("SHOWCRITICALMONITORS"));
/* 1342 */         view.setProperty("STATUSUPDATEINTERVAL", rs.getString("STATUSUPDATEINTERVAL"));
/* 1343 */         view.setProperty("SHOWONLYSUBGROUPS", rs.getString("SHOWONLYSUBGROUPS"));
/* 1344 */         view.setProperty("XCANVAS", rs.getString("XCANVAS"));
/* 1345 */         view.setProperty("YCANVAS", rs.getString("YCANVAS"));
/*      */         
/* 1347 */         view.setProperty("BGCOLOR", rs.getString("BGCOLOR"));
/* 1348 */         view.setProperty("LINECOLOR", rs.getString("LINECOLOR"));
/* 1349 */         view.setProperty("LABELCOLOR", rs.getString("LABELCOLOR"));
/* 1350 */         view.setProperty("LINETRANSPARENCY", rs.getString("LINETRANSPARENCY"));
/* 1351 */         view.setProperty("LINETHICKNESS", rs.getString("LINETHICKNESS"));
/*      */         
/* 1353 */         view.setProperty("SHOWTOPLEVELMGS", rs.getString("SHOWTOPLEVELMGS"));
/*      */         
/* 1355 */         String toplevelsubmg = rs.getString("SHOWTOPLEVELSUBMGS");
/* 1356 */         if (toplevelsubmg == null) {
/* 1357 */           toplevelsubmg = "false";
/*      */         }
/* 1359 */         view.setProperty("SHOWTOPLEVELSUBMGS", toplevelsubmg);
/* 1360 */         return view;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1365 */       ex.printStackTrace();
/*      */     } finally {
/* 1367 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1369 */     return view;
/*      */   }
/*      */   
/*      */   private static void setStatusToAJITObject(JSONObject jitObject, JSONObject status) {
/* 1373 */     String currentDivStyle = "";
/*      */     try {
/* 1375 */       JSONObject data = jitObject.getJSONObject("data");
/* 1376 */       String resourceID = data.getString("$resourceID");
/*      */       
/* 1378 */       if (status.has(resourceID)) {
/* 1379 */         JSONObject statusNode = status.getJSONObject(resourceID);
/* 1380 */         String recentSeverity = statusNode.getString("severity");
/* 1381 */         String name = jitObject.getString("name");
/* 1382 */         String recentMessage = statusNode.getString("message");
/*      */         
/* 1384 */         currentDivStyle = currentDivStyle + divStyleReplace + recentSeverity;
/*      */         
/* 1386 */         Pattern p = Pattern.compile(divStyleRegex);
/* 1387 */         Matcher m = p.matcher(name);
/* 1388 */         name = m.replaceAll(currentDivStyle);
/*      */         
/* 1390 */         jitObject.put("name", name);
/* 1391 */         jitObject.getJSONObject("data").put("$message", recentMessage);
/*      */       }
/*      */       
/*      */ 
/* 1395 */       if (jitObject.has("children")) {
/* 1396 */         JSONArray children = jitObject.getJSONArray("children");
/* 1397 */         for (int i = 0; i < children.length(); i++) {
/* 1398 */           setStatusToAJITObject(children.getJSONObject(i), status);
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 1402 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public JSONObject getDataFromBusinessCache(String haid, Properties view, Hashtable healthkeys)
/*      */   {
/* 1409 */     JSONObject jsonFromCache = new JSONObject();
/* 1410 */     String viewid = view.getProperty("VIEWID");
/* 1411 */     AMLog.debug("Getting Data From Cache for viewid_haid::" + viewid + "_" + haid);
/*      */     try
/*      */     {
/* 1414 */       JSONObject jsonFromCache1 = DBUtil.businessViewDetails.getJSONObject(viewid + "_" + haid).getJSONObject("all");
/* 1415 */       jsonFromCache = new JSONObject(jsonFromCache1.toString());
/* 1416 */       if (jsonFromCache != null) {
/* 1417 */         AMLog.debug("Data avaialble in cache for viewid_haid::" + viewid + "_" + haid);
/* 1418 */         JSONObject newJ = new JSONObject();
/* 1419 */         Properties neededPropsFromCache = new Properties();
/* 1420 */         getResourceIdsForGraph(jsonFromCache, neededPropsFromCache);
/* 1421 */         String resourceList = neededPropsFromCache.getProperty("resourceId");
/* 1422 */         JSONObject latestStatus = getLatestStatusForBusinessView(healthkeys, resourceList, haid, viewid, true);
/* 1423 */         setStatusToAJITObject(jsonFromCache, latestStatus);
/* 1424 */         filterForCriticalWithRecentStatus(jsonFromCache, newJ, latestStatus, view, new Properties());
/*      */       }
/*      */     } catch (Exception e) {
/* 1427 */       e.printStackTrace();
/*      */     }
/* 1429 */     return jsonFromCache;
/*      */   }
/*      */   
/*      */   public static void filterCritical(JSONObject oldJSON, JSONObject newJSON, Properties view, Properties neededPropsForRecursion) {
/* 1433 */     boolean showCriticalMonitors = Boolean.valueOf(view.getProperty("SHOWCRITICALMONITORS")).booleanValue();
/* 1434 */     boolean showTopOnly = Boolean.valueOf(view.getProperty("SHOWTOPLEVELSUBMGS")).booleanValue();
/*      */     try
/*      */     {
/* 1437 */       String id = oldJSON.getString("id");
/* 1438 */       JSONArray currentObj = new JSONArray();
/* 1439 */       if (oldJSON.has("children")) {
/* 1440 */         JSONArray children = oldJSON.getJSONArray("children");
/* 1441 */         for (int i = 0; i < children.length(); i++) {
/* 1442 */           JSONObject childrenObject = children.getJSONObject(i);
/* 1443 */           if (!showTopOnly) {
/* 1444 */             filterCritical(childrenObject, newJSON, view, neededPropsForRecursion);
/* 1445 */             boolean toAdd = nodeToBeDisplayed(childrenObject, view, neededPropsForRecursion);
/* 1446 */             if ((toAdd) || (childrenObject.getJSONArray("children").length() > 0)) {
/* 1447 */               currentObj.put(childrenObject);
/*      */             }
/*      */           } else {
/* 1450 */             JSONObject data = childrenObject.getJSONObject("data");
/* 1451 */             String severity = data.getString("$severity");
/* 1452 */             String monType = data.getString("$monType");
/* 1453 */             if (("HAI".equals(monType)) && (((showCriticalMonitors) && ("1".equals(severity))) || (!showCriticalMonitors))) {
/* 1454 */               childrenObject.remove("children");
/* 1455 */               currentObj.put(childrenObject);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1460 */       oldJSON.put("children", currentObj);
/*      */     } catch (Exception e) {
/* 1462 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static boolean nodeToBeDisplayed(JSONObject json, Properties view, Properties neededPropsForRecursion)
/*      */   {
/* 1468 */     boolean showCriticalMonitors = Boolean.valueOf(view.getProperty("SHOWCRITICALMONITORS")).booleanValue();
/* 1469 */     boolean hideMonitors = Boolean.valueOf(view.getProperty("SHOWONLYSUBGROUPS")).booleanValue();
/*      */     try {
/* 1471 */       JSONObject data = json.getJSONObject("data");
/* 1472 */       String monType = data.getString("$monType");
/* 1473 */       if ((!"HAI".equals(monType)) && (hideMonitors == true)) {
/* 1474 */         return false;
/*      */       }
/* 1476 */       String resourceID = data.getString("$resourceID");
/* 1477 */       String severity = data.getString("$severity");
/* 1478 */       if ((!showCriticalMonitors) || ((showCriticalMonitors) && (("1".equals(severity)) || (severity.contains("stsDown"))))) {
/* 1479 */         return true;
/*      */       }
/* 1481 */       return false;
/*      */     }
/*      */     catch (Exception e) {
/* 1484 */       e.printStackTrace();
/*      */     }
/* 1486 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public static void filterForCriticalWithRecentStatus(JSONObject oldJSON, JSONObject newJSON, JSONObject status, Properties view, Properties neededPropsForRecursion)
/*      */   {
/* 1492 */     boolean showCriticalMonitors = Boolean.valueOf(view.getProperty("SHOWCRITICALMONITORS")).booleanValue();
/* 1493 */     boolean showTopOnly = Boolean.valueOf(view.getProperty("SHOWTOPLEVELSUBMGS")).booleanValue();
/*      */     try {
/* 1495 */       String id = oldJSON.getString("id");
/* 1496 */       JSONArray currentObj = new JSONArray();
/* 1497 */       if (oldJSON.has("children")) {
/* 1498 */         JSONArray children = oldJSON.getJSONArray("children");
/* 1499 */         for (int i = 0; i < children.length(); i++) {
/* 1500 */           JSONObject childrenObject = children.getJSONObject(i);
/* 1501 */           if (!showTopOnly) {
/* 1502 */             filterForCriticalWithRecentStatus(childrenObject, newJSON, status, view, neededPropsForRecursion);
/* 1503 */             boolean toAdd = checkWhetherCriticalPathAlongWithHierrarchy(childrenObject, status, view, neededPropsForRecursion);
/* 1504 */             if ((toAdd) || (childrenObject.getJSONArray("children").length() > 0)) {
/* 1505 */               currentObj.put(childrenObject);
/*      */             }
/*      */           } else {
/* 1508 */             JSONObject data = childrenObject.getJSONObject("data");
/* 1509 */             String monType = data.getString("$monType");
/* 1510 */             String severity = data.getString("$severity");
/* 1511 */             if (("HAI".equals(monType)) && (((showCriticalMonitors) && ("1".equals(severity))) || (!showCriticalMonitors))) {
/* 1512 */               childrenObject.remove("children");
/* 1513 */               currentObj.put(childrenObject);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1518 */       oldJSON.put("children", currentObj);
/*      */     } catch (Exception e) {
/* 1520 */       e.printStackTrace();
/*      */     }
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
/*      */   public static boolean checkWhetherCriticalPathAlongWithHierrarchy(JSONObject json, JSONObject status, Properties view, Properties neededPropsForRecursion)
/*      */   {
/* 1555 */     boolean showCriticalMonitors = Boolean.valueOf(view.getProperty("SHOWCRITICALMONITORS")).booleanValue();
/* 1556 */     boolean hideMonitors = Boolean.valueOf(view.getProperty("SHOWONLYSUBGROUPS")).booleanValue();
/*      */     try {
/* 1558 */       JSONObject data = json.getJSONObject("data");
/* 1559 */       String monType = data.getString("$monType");
/* 1560 */       if ((!"HAI".equals(monType)) && (hideMonitors == true)) {
/* 1561 */         return false;
/*      */       }
/* 1563 */       String resourceID = data.getString("$resourceID");
/* 1564 */       String severity = data.getString("$severity");
/* 1565 */       if (status.has(resourceID)) {
/* 1566 */         severity = status.getJSONObject(resourceID).getString("severity");
/*      */       }
/* 1568 */       if ((!showCriticalMonitors) || ((showCriticalMonitors) && (("1".equals(severity)) || (severity.contains("stsDown"))))) {
/* 1569 */         return true;
/*      */       }
/* 1571 */       return false;
/*      */     }
/*      */     catch (Exception e) {
/* 1574 */       e.printStackTrace();
/*      */     }
/* 1576 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean checkWhetherCriticalPathAndAdd(JSONObject json, JSONObject status, Properties view, Properties neededPropsForRecursion) {
/* 1580 */     boolean showCriticalMonitors = Boolean.valueOf(view.getProperty("SHOWCRITICALMONITORS")).booleanValue();
/*      */     try {
/* 1582 */       JSONObject data = json.getJSONObject("data");
/* 1583 */       String resourceID = data.getString("$resourceID");
/* 1584 */       String severity = data.getString("$severity");
/* 1585 */       if (status.has(resourceID)) {
/* 1586 */         severity = status.getJSONObject(resourceID).getString("severity");
/*      */       }
/* 1588 */       if ((!showCriticalMonitors) || ((showCriticalMonitors) && (("1".equals(severity)) || (severity.contains("stsDown"))))) {
/* 1589 */         return true;
/*      */       }
/* 1591 */       return false;
/*      */     }
/*      */     catch (Exception e) {
/* 1594 */       e.printStackTrace();
/*      */     }
/* 1596 */     return false;
/*      */   }
/*      */   
/*      */   public static void setStatusAndViewPropsToAJITObject(JSONObject jitObject, JSONObject status, Properties view, Properties neededPropsForRecursion) {
/*      */     try {
/* 1601 */       JSONObject data = jitObject.getJSONObject("data");
/* 1602 */       String resourceID = data.getString("$resourceID");
/* 1603 */       String currentDivStyle = "";
/* 1604 */       String name = jitObject.getString("name");
/* 1605 */       String severity = data.getString("$severity");
/*      */       
/* 1607 */       if (status.has(resourceID)) {
/* 1608 */         JSONObject statusNode = status.getJSONObject(resourceID);
/* 1609 */         severity = statusNode.getString("severity");
/* 1610 */         name = jitObject.getString("name");
/* 1611 */         String recentMessage = statusNode.getString("message");
/*      */         
/* 1613 */         currentDivStyle = divStyleReplace + severity;
/*      */         
/* 1615 */         Pattern p = Pattern.compile(divStyleRegex);
/* 1616 */         Matcher m = p.matcher(name);
/* 1617 */         name = m.replaceAll(currentDivStyle);
/* 1618 */         jitObject.put("name", name);
/* 1619 */         jitObject.getJSONObject("data").put("$message", recentMessage);
/*      */       }
/*      */       
/* 1622 */       if (jitObject.has("children")) {
/* 1623 */         JSONArray children = jitObject.getJSONArray("children");
/* 1624 */         for (int i = 0; i < children.length(); i++) {
/* 1625 */           setStatusAndViewPropsToAJITObject(children.getJSONObject(i), status, view, neededPropsForRecursion);
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 1629 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static boolean updateCriticalValueInDB(String viewid, boolean criticalValue)
/*      */   {
/* 1635 */     String updateQuery = "update AM_MONITORGROUP_FLASHVIEWCONFIG set SHOWCRITICALMONITORS=" + criticalValue + " where VIEWID=" + viewid;
/*      */     try {
/* 1637 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */     } catch (SQLException e) {
/* 1639 */       e.printStackTrace();
/* 1640 */       return false;
/*      */     } catch (Exception e) {
/* 1642 */       e.printStackTrace();
/* 1643 */       return false;
/*      */     }
/* 1645 */     return true;
/*      */   }
/*      */   
/*      */   public static boolean updateViewPropsInDB(String viewid, Properties view) {
/* 1649 */     boolean showCritical = "true".equals(view.getProperty("SHOWCRITICALMONITORS"));
/* 1650 */     boolean showGroups = "true".equals(view.getProperty("SHOWONLYSUBGROUPS"));
/* 1651 */     boolean all = "true".equals(view.getProperty("SHOWALLMONITORS"));
/* 1652 */     boolean showtoponly = "true".equals(view.getProperty("SHOWTOPLEVELSUBMGS"));
/* 1653 */     boolean showLabel = "true".equals(view.getProperty("SHOWLABEL"));
/* 1654 */     String bgColor = view.getProperty("BGCOLOR");
/* 1655 */     String lineColor = view.getProperty("LINECOLOR");
/* 1656 */     String labelColor = view.getProperty("LABELCOLOR");
/* 1657 */     String lineThickness = view.getProperty("LINETHICKNESS");
/* 1658 */     String lineTransparency = view.getProperty("LINETRANSPARENCY");
/* 1659 */     String displayName = view.getProperty("DISPLAYNAME");
/*      */     
/*      */ 
/* 1662 */     String updateQuery = "update AM_MONITORGROUP_FLASHVIEWCONFIG set DISPLAYNAME='" + displayName + "', SHOWCRITICALMONITORS='" + showCritical + "', SHOWONLYSUBGROUPS='" + showGroups + "', SHOWTOPLEVELSUBMGS='" + showtoponly + "', SHOWLABEL='" + showLabel + "', BGCOLOR='" + bgColor + "', LINECOLOR='" + lineColor + "', LABELCOLOR='" + labelColor + "', LINETHICKNESS=" + lineThickness + ", LINETRANSPARENCY=" + lineTransparency + " where VIEWID=" + viewid;
/*      */     
/* 1664 */     AMLog.debug("THE UPDATE QUERY ::" + updateQuery);
/*      */     try {
/* 1666 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */     }
/*      */     catch (SQLException e) {
/* 1669 */       e.printStackTrace();
/* 1670 */       return false;
/*      */     } catch (Exception e) {
/* 1672 */       e.printStackTrace();
/* 1673 */       return false;
/*      */     }
/* 1675 */     return true;
/*      */   }
/*      */   
/*      */   public static Properties getDefaultViewProps() {
/* 1679 */     Properties view = new Properties();
/* 1680 */     view.setProperty("VIEWID", "-1");
/* 1681 */     view.setProperty("ZOOMLEVEL", "1");
/* 1682 */     view.setProperty("SHOWLABEL", "true");
/* 1683 */     view.setProperty("SHOWCRITICALMONITORS", "false");
/* 1684 */     view.setProperty("SHOWONLYSUBGROUPS", "false");
/* 1685 */     view.setProperty("SHOWTOPLEVELSUBMGS", "false");
/* 1686 */     view.setProperty("XCANVAS", "20");
/* 1687 */     view.setProperty("YCANVAS", "20");
/* 1688 */     return view;
/*      */   }
/*      */   
/*      */   public JSONObject getAllWebAndUrlServerMonitors() {
/* 1692 */     JSONObject servers = new JSONObject();
/* 1693 */     String query = "select RESOURCEID,RESOURCENAME,RESOURCETYPE,RESOURCEGROUP,AM_ManagedObject.DISPLAYNAME as OBJECTDISPLAYNAME,AM_ManagedResourceType.IMAGEPATH, AM_ManagedResourceType.SHORTNAME as typeDisplayName from AM_ManagedObject left join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE where RESOURCEGROUP IN ('URL') order by AM_ManagedObject.DISPLAYNAME asc";
/* 1694 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1695 */     ResultSet rs = null;
/*      */     try {
/* 1697 */       String resourceId = null;
/* 1698 */       String resourceName = null;
/* 1699 */       String resourceType = null;
/* 1700 */       String resourceGroup = null;
/* 1701 */       String objDisplayName = null;
/* 1702 */       String imagePath = null;
/* 1703 */       String typeDisplayName = null;
/* 1704 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1705 */       while (rs.next()) {
/* 1706 */         JSONObject each = new JSONObject();
/* 1707 */         resourceId = rs.getString("RESOURCEID");
/* 1708 */         resourceName = rs.getString("RESOURCENAME");
/* 1709 */         resourceType = rs.getString("RESOURCETYPE");
/* 1710 */         resourceGroup = rs.getString("RESOURCEGROUP");
/* 1711 */         objDisplayName = rs.getString("OBJECTDISPLAYNAME");
/* 1712 */         imagePath = rs.getString("IMAGEPATH");
/* 1713 */         typeDisplayName = rs.getString("typeDisplayName");
/* 1714 */         each.put("typeDisplayName", FormatUtil.getString(typeDisplayName));
/* 1715 */         each.put("resourcename", resourceName);
/* 1716 */         each.put("resourcetype", resourceType);
/* 1717 */         each.put("resourceGroup", resourceGroup);
/* 1718 */         each.put("objDisplayName", objDisplayName);
/* 1719 */         each.put("imagepath", this.admUtil.getNewDependencyImage(resourceType, imagePath));
/* 1720 */         servers.put(resourceId, each);
/* 1721 */         AMLog.debug("Getting all Web and Url servers" + servers);
/*      */       }
/*      */     } catch (Exception e) {
/* 1724 */       e.printStackTrace();
/*      */     } finally {
/* 1726 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*      */     
/* 1729 */     return servers;
/*      */   }
/*      */   
/*      */   public void drawBusinessServiceGraph(String groupName, PrintWriter out, JSONArray resourceList) {
/* 1733 */     JSONObject graph = new JSONObject();
/* 1734 */     graph = getGraph(groupName, resourceList);
/* 1735 */     AMLog.debug("Drawing the graph For Business Service" + graph.toString());
/* 1736 */     out.println(graph);
/*      */   }
/*      */   
/*      */   private JSONObject getGraph(String groupName, JSONArray resourceList) {
/* 1740 */     JSONObject graph = new JSONObject();
/* 1741 */     graph = getCreateNewServiceGraph(groupName, resourceList);
/* 1742 */     return graph;
/*      */   }
/*      */   
/*      */   private JSONObject getCreateNewServiceGraph(String groupName, JSONArray resourceList) {
/* 1746 */     int nextId = 1;
/* 1747 */     JSONObject graph = new JSONObject();
/* 1748 */     int parentId = nextId++;
/* 1749 */     String nodeId = "node_" + parentId;
/* 1750 */     String description = FormatUtil.getString("am.webclient.newmonitorgroup.description.textbox");
/* 1751 */     String owners = "1";
/* 1752 */     String locationid = "-1";
/* 1753 */     String actionDiv = "<span class='iconAddNode' onclick='addToBusinessService(\"" + nodeId + "\")'></span><span class='iconEdit' onclick='editGroupNode(\"" + nodeId + "\")'></span>";
/* 1754 */     String parentNameWithImage = "<div class='iconBox'>" + actionDiv + "<span class='cloudBox'><img src='../images/icon_sg.png'></span><i><a class='redirectToDetails' target='_blank'> " + groupName + "</a></i></div>";
/*      */     try {
/* 1756 */       graph.put("name", parentNameWithImage);
/* 1757 */       graph.put("id", nodeId);
/* 1758 */       JSONObject data = new JSONObject();
/* 1759 */       data.put("$message", this.admUtil.getMessageForTooltip(groupName, "Group"));
/* 1760 */       JSONObject childrenDetails = getAllChildrenToBeDisplayed(resourceList, nextId, parentId);
/* 1761 */       JSONArray children = childrenDetails.getJSONArray("childrenToBeReturned");
/* 1762 */       nextId = childrenDetails.getInt("nextId");
/* 1763 */       graph.put("children", children);
/* 1764 */       data.put("$nodeType", "group");
/* 1765 */       data.put("$displayName", groupName);
/* 1766 */       data.put("$description", description);
/* 1767 */       data.put("$owners", owners);
/* 1768 */       data.put("$locationid", locationid);
/* 1769 */       data.put("$isAutoMapped", Boolean.TRUE);
/* 1770 */       graph.put("data", data);
/* 1771 */       return new JSONObject().put("graph", graph).put("nextId", nextId);
/*      */     } catch (JSONException e) {
/* 1773 */       e.printStackTrace();
/*      */     } catch (Exception e) {
/* 1775 */       e.printStackTrace();
/*      */     }
/* 1777 */     return new JSONObject();
/*      */   }
/*      */   
/*      */   public JSONObject getAllChildrenToBeDisplayed(JSONArray resourceList, int nextId, int parentId) {
/* 1781 */     JSONObject childArray = null;
/* 1782 */     JSONArray childrenToBeReturned = new JSONArray();
/*      */     try {
/* 1784 */       if (resourceList.length() > 0)
/*      */       {
/*      */ 
/*      */ 
/* 1788 */         childArray = this.admUtil.getChildrenForResources(resourceList, nextId, childrenToBeReturned, parentId + "");
/* 1789 */         AMLog.debug("Inside getAllChildrenToBeDisplayed For Graph====>" + childrenToBeReturned);
/*      */       } else {
/* 1791 */         childArray = new JSONObject();
/* 1792 */         childArray.put("childrenToBeReturned", childrenToBeReturned).put("nextId", nextId);
/*      */       }
/*      */     } catch (JSONException e) {
/* 1795 */       e.printStackTrace();
/*      */     } catch (Exception e) {
/* 1797 */       e.printStackTrace();
/*      */     }
/* 1799 */     return childArray;
/*      */   }
/*      */   
/*      */   private int getStartingResourcesAsChildren(JSONArray resourceList, JSONArray children, int nextId, JSONObject resourceDetails, int parentId)
/*      */   {
/* 1804 */     for (int i = 0; i < resourceList.length(); i++) {
/*      */       try {
/* 1806 */         String resourceId = resourceList.getString(i);
/* 1807 */         nextId = this.admUtil.addMonitorNode(children, nextId, resourceDetails.getJSONObject(resourceId), resourceId, parentId + "");
/*      */       } catch (JSONException e) {
/* 1809 */         e.printStackTrace();
/*      */       } catch (Exception e) {
/* 1811 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 1814 */     children.put(this.admUtil.getDivToAdd(nextId++, 1));
/* 1815 */     return nextId;
/*      */   }
/*      */   
/*      */   public long createBusinessView(JSONObject viewProps, String userid, String haid, boolean isADDM, JSONObject nodeIdVsResourceId, long viewId) {
/* 1819 */     String createdHaid = "-1";
/*      */     try {
/* 1821 */       String displayPropsString = viewProps.getString("displayProps");
/* 1822 */       AMLog.debug("Coordinates for saving business view properties :" + displayPropsString);
/* 1823 */       displayPropsString = decodeSpecialCharactersFromJSONString(displayPropsString);
/* 1824 */       JSONObject displayProps = new JSONObject(displayPropsString);
/* 1825 */       viewId = saveViewConfig(displayProps, userid, haid);
/*      */       
/* 1827 */       String coordinateString = viewProps.getString("coordinates");
/* 1828 */       AMLog.debug("Coordinates for saving business coordinates properties :" + coordinateString);
/* 1829 */       coordinateString = decodeSpecialCharactersFromJSONString(coordinateString);
/* 1830 */       JSONObject coordinates = new JSONObject(coordinateString);
/*      */       
/* 1832 */       AMLog.debug("Creating the Business View for Business service through graphical view: " + coordinates);
/* 1833 */       saveStateForJIT(haid, userid, viewId + "", coordinates, "-1", "-1", nodeIdVsResourceId, isADDM);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1837 */       e.printStackTrace();
/*      */     }
/* 1839 */     return viewId;
/*      */   }
/*      */   
/*      */   public long saveViewConfig(JSONObject dispProps, String userid, String haid) {
/* 1843 */     String zoomlevel = "1";
/* 1844 */     String showLabel = "";
/* 1845 */     String showOnlyMGs = "";
/* 1846 */     String showOnlyTopMGs = "";
/* 1847 */     String showOnlyCritical = "";
/* 1848 */     String showOnlyMGStatus = "";
/* 1849 */     String backgroundColorVal = "";
/* 1850 */     String lineColorVal = "";
/* 1851 */     String textColorVal = "";
/* 1852 */     String lineThickness = "";
/* 1853 */     String querytoCreateBusinessView = "";
/* 1854 */     String selected = "1";
/* 1855 */     String isHtml = "true";
/* 1856 */     String xCanvas = "";
/* 1857 */     String yCanvas = "";
/* 1858 */     String lineTransparency = "";
/* 1859 */     String displayName = "";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1868 */       if (dispProps.has("displayName")) {
/* 1869 */         displayName = dispProps.getString("displayName").trim();
/*      */       } else {
/* 1871 */         displayName = FormatUtil.getString("am.webclient.flashview.displayname");
/*      */       }
/*      */       
/* 1874 */       if (dispProps.has("backgroundColorVal")) {
/* 1875 */         showLabel = dispProps.getString("showLabel");
/* 1876 */         showOnlyMGs = dispProps.getString("showOnlyMGs");
/* 1877 */         showOnlyTopMGs = dispProps.getString("showOnlyTopMGs");
/* 1878 */         showOnlyCritical = dispProps.getString("showOnlyCritical");
/* 1879 */         showOnlyMGStatus = dispProps.getString("showOnlyMGStatus");
/* 1880 */         backgroundColorVal = dispProps.getString("backgroundColorVal");
/* 1881 */         lineColorVal = dispProps.getString("lineColorVal");
/* 1882 */         textColorVal = dispProps.getString("textColorVal");
/* 1883 */         lineThickness = dispProps.getString("lineThickness");
/* 1884 */         lineTransparency = dispProps.getString("lineTransparency");
/*      */       } else {
/* 1886 */         showLabel = "true";
/* 1887 */         showOnlyMGs = "false";
/* 1888 */         showOnlyTopMGs = "false";
/* 1889 */         showOnlyCritical = "false";
/* 1890 */         showOnlyMGStatus = "false";
/* 1891 */         backgroundColorVal = "#FFFFFF";
/* 1892 */         lineColorVal = "#ECECEC";
/* 1893 */         textColorVal = "#444444";
/* 1894 */         lineThickness = "2.0";
/* 1895 */         lineTransparency = "1";
/*      */       }
/* 1897 */       xCanvas = dispProps.getString("xCanvas");
/* 1898 */       yCanvas = dispProps.getString("yCanvas");
/*      */       
/* 1900 */       AMLog.debug("Saving Business view configuration............." + xCanvas + "  " + yCanvas);
/* 1901 */       long viewid = getViewIdForExistingBV(haid);
/* 1902 */       if (viewid == -1L)
/*      */       {
/* 1904 */         viewid = this.admUtil.getNextID("AM_MONITORGROUP_FLASHVIEWCONFIG", "VIEWID");
/* 1905 */         querytoCreateBusinessView = "insert into AM_MONITORGROUP_FLASHVIEWCONFIG(VIEWID,USERID,HAID,ZOOMLEVEL,SELECTED,DISPLAYNAME,DESCRIPTION,LINECOLOR,LINETHICKNESS,LABELCOLOR,BGCOLOR,STATUSUPDATEINTERVAL,RELOADINTERVAL,SHOWLABEL,LINETRANSPARENCY,SHOWCRITICALMONITORS,SHOWONLYSUBGROUPS,SHOWTOPLEVELMGS,NOOFCOLUMNS,XCANVAS,YCANVAS,ISHTML) Values(" + viewid + "," + userid + "," + haid + "," + zoomlevel + "," + selected + ",'" + displayName + "','','" + lineColorVal + "'," + lineThickness + ",'" + textColorVal + "','" + backgroundColorVal + "',60,840,'" + showLabel + "','" + lineTransparency + "','" + showOnlyCritical + "','" + showOnlyMGs + "','" + showOnlyTopMGs + "',2," + xCanvas + "," + yCanvas + ",'" + isHtml + "')";
/* 1906 */         AMLog.debug("Saving Business view configuration query............." + querytoCreateBusinessView);
/*      */         try {
/* 1908 */           AMConnectionPool.executeUpdateStmt(querytoCreateBusinessView);
/* 1909 */           return viewid;
/*      */         } catch (SQLException e) {
/* 1911 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1916 */         String updateQuery = "update AM_MONITORGROUP_FLASHVIEWCONFIG set XCANVAS=" + xCanvas + ",YCANVAS=" + yCanvas + " where VIEWID=" + viewid;
/*      */         try {
/* 1918 */           AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */         }
/*      */         catch (SQLException e) {
/* 1921 */           e.printStackTrace();
/*      */         }
/* 1923 */         AMLog.debug("Returning existing viewid " + viewid + " for BV :: " + haid);
/* 1924 */         return viewid;
/*      */       }
/*      */     } catch (JSONException e) {
/* 1927 */       e.printStackTrace();
/*      */     } catch (Exception e) {
/* 1929 */       e.printStackTrace();
/*      */     }
/* 1931 */     return -1L;
/*      */   }
/*      */   
/*      */   public long getViewIdForExistingBV(String haid)
/*      */   {
/* 1936 */     ResultSet rs = null;
/* 1937 */     String viewid = "-1";
/*      */     try {
/* 1939 */       String query = "select VIEWID from AM_MONITORGROUP_FLASHVIEWCONFIG where HAID=" + haid;
/* 1940 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1941 */       if (rs.next())
/*      */       {
/* 1943 */         viewid = rs.getString("VIEWID");
/*      */       }
/*      */     }
/*      */     catch (SQLException e) {
/* 1947 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1950 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 1952 */     return Long.parseLong(viewid);
/*      */   }
/*      */   
/*      */   public String createMonitorGroupFromGraph(JSONObject tree, HttpServletResponse response, HashMap nodeIdVsResourceId, boolean useADDMForRediscovery) {
/*      */     try {
/* 1957 */       JSONObject dataForGroup = tree.getJSONObject("data");
/* 1958 */       String name = dataForGroup.getString("$displayName");
/* 1959 */       String description = dataForGroup.getString("$description");
/* 1960 */       String locationid = dataForGroup.getString("$locationid");
/* 1961 */       String owners = dataForGroup.getString("$owners");
/* 1962 */       String SERVLET_PATH = "/AppManager";
/* 1963 */       String PATH_INFO = "/json/AddMonitorGroup";
/* 1964 */       MockHttpServletRequest MSreq = new MockHttpServletRequest("", "/AppManager", "/json/AddMonitorGroup", "");
/* 1965 */       MSreq.addParameter("grouptype", "monitorgroup");
/* 1966 */       MSreq.addParameter("name", name);
/* 1967 */       MSreq.addParameter("description", description);
/* 1968 */       MSreq.addParameter("locationid", locationid);
/* 1969 */       MSreq.addParameter("userid", owners);
/* 1970 */       MSreq.addParameter("useADDM", useADDMForRediscovery + "");
/* 1971 */       String monitorToAssociate = "";
/* 1972 */       JSONObject result = new JSONObject(CommonAPIUtil.addMonitorGroup(MSreq, response));
/* 1973 */       String groupid = "-1";
/* 1974 */       AMLog.debug("Creating monitor group : from Business Service Graph" + result);
/* 1975 */       if ("4000".equals(result.getString("response-code"))) {
/* 1976 */         JSONArray children = tree.getJSONArray("children");
/* 1977 */         String groupResourceId = result.getString("resourceid");
/* 1978 */         JSONArray monList = new JSONArray();
/* 1979 */         JSONArray dependentList = new JSONArray();
/* 1980 */         String resourceId = "-1";
/* 1981 */         JSONArray isAutoMapped = new JSONArray();
/* 1982 */         for (int i = 0; i < children.length(); i++) {
/* 1983 */           JSONObject each = children.getJSONObject(i);
/* 1984 */           String childNodeId = each.getString("id");
/* 1985 */           JSONObject data = each.getJSONObject("data");
/* 1986 */           String nodeType = data.getString("$nodeType");
/* 1987 */           if ("monitor".equals(nodeType)) {
/* 1988 */             resourceId = data.getString("$resourceID");
/* 1989 */             if (monList.toString().indexOf(resourceId) == -1) {
/* 1990 */               monList.put(resourceId);
/* 1991 */               nodeIdVsResourceId.put(childNodeId, resourceId);
/* 1992 */               isAutoMapped.put(data.has("$isAutoMapped"));
/*      */             }
/* 1994 */           } else if ("Dependent-group".equals(nodeType)) {
/* 1995 */             resourceId = data.getString("$resourceID");
/* 1996 */             dependentList.put(resourceId);
/* 1997 */             nodeIdVsResourceId.put(childNodeId, resourceId);
/*      */           } else {
/* 1999 */             createRecursiveSubGroupFromGraph(groupResourceId, each, response, nodeIdVsResourceId, groupResourceId, useADDMForRediscovery);
/*      */           }
/*      */         }
/* 2002 */         monitorToAssociate = this.admUtil.getQueryStringForNumberFormat(monList);
/* 2003 */         AMLog.debug("Calling monitors to associate..." + name + " " + monitorToAssociate.toString());
/* 2004 */         if (monList.length() > 0) {
/* 2005 */           associateMonitors(groupResourceId, monitorToAssociate.toString(), response, groupResourceId, useADDMForRediscovery, this.admUtil.getQueryStringForNumberFormat(isAutoMapped));
/*      */         }
/* 2007 */         AMLog.debug("Calling dependent groups to associate...." + this.admUtil.getQueryStringForNumberFormat(dependentList));
/* 2008 */         if (dependentList.length() > 0) {
/* 2009 */           RuleAnalyser.addDependentMG(groupResourceId, this.admUtil.getQueryStringForNumberFormat(dependentList), Boolean.TRUE.booleanValue());
/*      */         }
/* 2011 */         groupid = result.getString("resourceid");
/* 2012 */         nodeIdVsResourceId.put(tree.getString("id"), groupid);
/* 2013 */         AMLog.debug("The mapping for node id vs resource id" + nodeIdVsResourceId);
/* 2014 */         return groupid;
/*      */       }
/*      */     } catch (JSONException e) {
/* 2017 */       e.printStackTrace();
/*      */     } catch (Exception e) {
/* 2019 */       e.printStackTrace();
/*      */     }
/* 2021 */     return "-1";
/*      */   }
/*      */   
/*      */   private boolean createRecursiveSubGroupFromGraph(String haid, JSONObject tree, HttpServletResponse response, HashMap nodeIdVsResourceId, String topLevelGroupId, boolean useADDMForRediscovery) {
/*      */     try {
/* 2026 */       JSONObject parentData = tree.getJSONObject("data");
/* 2027 */       String subgroupId = createSubgroup(haid, response, parentData);
/* 2028 */       nodeIdVsResourceId.put(tree.getString("id"), subgroupId);
/* 2029 */       JSONArray childrenOfSG = tree.getJSONArray("children");
/* 2030 */       int len = childrenOfSG.length();
/* 2031 */       JSONArray monList = new JSONArray();
/* 2032 */       JSONArray dependentList = new JSONArray();
/* 2033 */       JSONArray isAutoMapped = new JSONArray();
/* 2034 */       boolean createSubGroup = false;
/* 2035 */       if (!"-1".equals(subgroupId)) {
/* 2036 */         for (int j = 0; j < len; j++) {
/* 2037 */           AMLog.debug("Recursive call for creating group from graph" + len);
/* 2038 */           JSONObject each = childrenOfSG.getJSONObject(j);
/* 2039 */           JSONObject data = each.getJSONObject("data");
/* 2040 */           String nodeType = data.getString("$nodeType");
/* 2041 */           String resourceId = "-1";
/* 2042 */           if ("monitor".equals(nodeType)) {
/* 2043 */             resourceId = data.getString("$resourceID");
/* 2044 */             if (monList.toString().indexOf(resourceId) == -1) {
/* 2045 */               monList.put(resourceId);
/* 2046 */               nodeIdVsResourceId.put(each.getString("id"), resourceId);
/* 2047 */               isAutoMapped.put(data.has("$isAutoMapped"));
/*      */             }
/* 2049 */           } else if ("Dependent-group".equals(nodeType)) {
/* 2050 */             resourceId = data.getString("$resourceID");
/* 2051 */             dependentList.put(resourceId);
/* 2052 */             nodeIdVsResourceId.put(each.getString("id"), resourceId);
/*      */           } else {
/* 2054 */             createRecursiveSubGroupFromGraph(subgroupId, childrenOfSG.getJSONObject(j), response, nodeIdVsResourceId, topLevelGroupId, useADDMForRediscovery);
/* 2055 */             createSubGroup = true;
/*      */           }
/* 2057 */           AMLog.debug("Inside Recursive call for creating group from graph" + monList + "isAutoMapped:" + isAutoMapped);
/*      */         }
/* 2059 */         if (monList.length() > 0) {
/* 2060 */           associateMonitors(subgroupId, this.admUtil.getQueryStringForNumberFormat(monList), response, topLevelGroupId, useADDMForRediscovery, this.admUtil.getQueryStringForNumberFormat(isAutoMapped));
/* 2061 */           if (!createSubGroup) {
/* 2062 */             long startTime = System.currentTimeMillis();
/* 2063 */             new AMRCAnalyser().applyRCA(Integer.parseInt(subgroupId), 17, System.currentTimeMillis(), true, true, 1);
/* 2064 */             new AMRCAnalyser().applyRCA(Integer.parseInt(subgroupId), 18, System.currentTimeMillis(), true, false, 2);
/* 2065 */             long endTime = System.currentTimeMillis();
/* 2066 */             AMLog.debug("BusinessViewUtil :: createRecursiveSubGroupFromGraph :: Time Taken ::: Apply RCA HAID:" + subgroupId + " time taken :: " + (endTime - startTime) / 1000L + " secs");
/*      */           }
/*      */         }
/* 2069 */         if (dependentList.length() > 0) {
/* 2070 */           RuleAnalyser.addDependentMG(subgroupId, this.admUtil.getQueryStringForNumberFormat(dependentList), Boolean.TRUE.booleanValue());
/*      */         }
/*      */       }
/*      */     } catch (JSONException e) {
/* 2074 */       e.printStackTrace();
/*      */     } catch (Exception e) {
/* 2076 */       e.printStackTrace();
/*      */     }
/* 2078 */     return true;
/*      */   }
/*      */   
/*      */   public static String createSubgroup(String haid, HttpServletResponse response, JSONObject subGroupData) {
/*      */     try {
/* 2083 */       String sgName = subGroupData.getString("$displayName");
/* 2084 */       String groupType = subGroupData.getString("$groupType");
/* 2085 */       String description = subGroupData.getString("$description");
/* 2086 */       String owners = subGroupData.getString("$owners");
/* 2087 */       String locationid = subGroupData.getString("$locationid");
/* 2088 */       AMLog.debug("Inside createSubgroup From Graph" + sgName + " " + groupType + " " + haid + " " + subGroupData);
/* 2089 */       String SERVLET_PATH = "/AppManager";
/* 2090 */       String PATH_INFO = "/json/AddSubGroup";
/* 2091 */       MockHttpServletRequest MSreq = new MockHttpServletRequest("", "/AppManager", "/json/AddSubGroup", "");
/* 2092 */       MSreq.addParameter("name", sgName);
/* 2093 */       MSreq.addParameter("grouptype", groupType);
/* 2094 */       MSreq.addParameter("haid", haid);
/* 2095 */       MSreq.addParameter("description", description);
/* 2096 */       MSreq.addParameter("userid", owners);
/* 2097 */       MSreq.addParameter("locationid", locationid);
/* 2098 */       MSreq.addParameter("isADDM", "true");
/* 2099 */       MSreq.addParameter("useADDM", "true");
/* 2100 */       JSONObject result = new JSONObject(CommonAPIUtil.AddSubGroup(MSreq, response));
/* 2101 */       AMLog.debug("Response Result from createSubgroup " + result);
/* 2102 */       if ("4000".equals(result.getString("response-code"))) {
/* 2103 */         return result.getString("resourceid");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2107 */       e.printStackTrace();
/*      */     }
/* 2109 */     return "-1";
/*      */   }
/*      */   
/*      */   public static boolean associateMonitors(String resourceId, String monitorsToAssociate, HttpServletResponse response, String topLevelId, boolean useADDMForRediscovery, String isAutoMapped) {
/*      */     try {
/* 2114 */       AMLog.debug("Associating monitors from Graph" + monitorsToAssociate + " Associate to:" + resourceId);
/* 2115 */       String SERVLET_PATH = "/AppManager";
/* 2116 */       String PATH_INFO = "/json/AssociateMonitortoMG";
/* 2117 */       MockHttpServletRequest MSreq = new MockHttpServletRequest("", "/AppManager", "/json/AssociateMonitortoMG", "");
/* 2118 */       MSreq.addParameter("resourceid", monitorsToAssociate);
/* 2119 */       MSreq.addParameter("haid", resourceId);
/* 2120 */       MSreq.addParameter("topLevelGroupId", topLevelId);
/* 2121 */       MSreq.addParameter("useADDM", useADDMForRediscovery + "");
/* 2122 */       MSreq.addParameter("isAutoMapped", isAutoMapped);
/* 2123 */       long startTime = System.currentTimeMillis();
/* 2124 */       String result = CommonAPIUtil.AssociateMonitorstoGroup(MSreq, response);
/* 2125 */       long endTime = System.currentTimeMillis();
/* 2126 */       AMLog.debug("Time Taken for associate monitors in BussinessView Util " + (endTime - startTime) / 1000L + "secs");
/* 2127 */       if (result.contains("4000")) {
/* 2128 */         return true;
/*      */       }
/*      */     } catch (Exception e) {
/* 2131 */       e.printStackTrace();
/*      */     }
/* 2133 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static boolean unAssociateMonitor(String haid, String monitorsToUnAssociate, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 2142 */       String[] resID = monitorsToUnAssociate.split(",");
/* 2143 */       HAIDManagerAction hm = new HAIDManagerAction();
/* 2144 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 2145 */       MSreq.setContentType("text/xml; charset=UTF-8");
/* 2146 */       MSreq.addParameter("removefromhaid", haid);
/* 2147 */       MSreq.setAttribute("monitors", resID);
/* 2148 */       MSreq.setAttribute("uri", "removeMonitors.do");
/* 2149 */       MSreq.setAttribute("useADDM", "true");
/* 2150 */       hm.removeMonitors(null, null, MSreq, response);
/*      */       
/* 2152 */       return true;
/*      */     } catch (Exception e) {
/* 2154 */       e.printStackTrace();
/*      */     }
/* 2156 */     return false;
/*      */   }
/*      */   
/*      */   private static long getHaidFromGroupName(String applicationName) {
/* 2160 */     String query = "select AM_ManagedObject.RESOURCEID as haid from AM_ManagedObject where AM_ManagedObject.RESOURCENAME='" + applicationName + "' and AM_ManagedObject.TYPE='HAI'";
/* 2161 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2162 */     ResultSet rs = null;
/*      */     try {
/* 2164 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2165 */       if (rs.next()) {
/* 2166 */         long haid = rs.getLong("haid");
/* 2167 */         return haid;
/*      */       }
/*      */     } catch (SQLException e) {
/* 2170 */       e.printStackTrace();
/*      */     } finally {
/* 2172 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 2174 */     return 0L;
/*      */   }
/*      */   
/*      */ 
/*      */   public JSONObject getAllMonitorsWithADDMConnections()
/*      */   {
/* 2180 */     JSONObject monitorList = new JSONObject();
/* 2181 */     JSONObject monitors = null;
/* 2182 */     ResultSet rs = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 2190 */       String query = "select amo.RESOURCEID,amo.DISPLAYNAME,amt.SHORTNAME as typeshortname,amt.IMAGEPATH from AM_ManagedObject amo left join AM_ManagedResourceType amt on amo.TYPE=amt.RESOURCETYPE where RESOURCEID in (select RESOURCEID from AM_ManagedObject amoforsourceapp where exists (select RESOURCEID from ADMDISCOVERYINFO ad where (amoforsourceapp.RESOURCEID = ad.SOURCEAPPID) and ad.SOURCEAPPID != -1 and ad.DESTINATIONAPPID != -1 ) union all select RESOURCEID from AM_ManagedObject amofordestapp where exists (select RESOURCEID from ADMDISCOVERYINFO adfordestapp where (amofordestapp.RESOURCEID = adfordestapp.DESTINATIONAPPID) and adfordestapp.SOURCEAPPID != -1 and adfordestapp.DESTINATIONAPPID != -1))";
/* 2191 */       if (DBQueryUtil.isMysql())
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2197 */         query = "select distinct(amo.RESOURCEID),amo.DISPLAYNAME,amt.SHORTNAME as typeshortname,amt.IMAGEPATH from AM_ManagedObject amo,  AM_ManagedResourceType amt, ADMDISCOVERYINFO ad where RESOURCEID = ad.SOURCEAPPID and ad.SOURCEAPPID <> -1 and ad.DESTINATIONAPPID <> -1 and amo.TYPE=amt.RESOURCETYPE union all select distinct(amo.RESOURCEID),amo.DISPLAYNAME,amt.SHORTNAME as typeshortname,amt.IMAGEPATH from AM_ManagedObject amo,  AM_ManagedResourceType amt, ADMDISCOVERYINFO ad where RESOURCEID = ad.DESTINATIONAPPID and ad.SOURCEAPPID <> -1 and ad.DESTINATIONAPPID <> -1 and amo.TYPE=amt.RESOURCETYPE";
/*      */       }
/* 2199 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2200 */       String resourceId = null;
/* 2201 */       String imagepath = null;
/* 2202 */       String typeDisplayname = null;
/* 2203 */       String displayName = null;
/* 2204 */       while (rs.next()) {
/* 2205 */         resourceId = rs.getString("RESOURCEID");
/* 2206 */         imagepath = rs.getString("IMAGEPATH");
/* 2207 */         typeDisplayname = rs.getString("typeshortname");
/* 2208 */         displayName = rs.getString("DISPLAYNAME");
/* 2209 */         AMLog.debug("resourceId" + resourceId);
/* 2210 */         monitorList.put(resourceId, new JSONObject().put("DISPLAYNAME", displayName).put("IMAGEPATH", imagepath).put("typeDisplayName", typeDisplayname));
/*      */       }
/* 2212 */       monitors = new JSONObject().put("monitors", monitorList);
/*      */     } catch (Exception e) {
/* 2214 */       e.printStackTrace();
/*      */     } finally {
/* 2216 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 2218 */     return monitors;
/*      */   }
/*      */   
/*      */   public JSONObject getAllMonitorList(HttpServletResponse response, String montype) {
/* 2222 */     return getAllMonitorList(response, "", montype);
/*      */   }
/*      */   
/*      */   public JSONObject getAllMonitorList(HttpServletResponse response, String filterList, String montype) {
/* 2226 */     JSONObject monitorList = new JSONObject();
/* 2227 */     String SERVLET_PATH = "/AppManager";
/* 2228 */     String PATH_INFO = "/json/ListMonitor";
/* 2229 */     MockHttpServletRequest MSreq = new MockHttpServletRequest("", "/AppManager", "/json/ListMonitor", "");
/* 2230 */     MSreq.addParameter("type", montype);
/* 2231 */     MSreq.addParameter("avoidGroupDetailsToSpeedUp", "true");
/* 2232 */     JSONArray availableResourceTypes = new JSONArray();
/*      */     try {
/* 2234 */       JSONObject json = new JSONObject(URITree.getMonitorsList(MSreq, response, filterList, true));
/* 2235 */       AMLog.debug("Inside getAllMonitorList" + json);
/* 2236 */       int responseCode = json.getInt("response-code");
/* 2237 */       if (responseCode == 4000) {
/* 2238 */         JSONObject eachMonitor = null;
/* 2239 */         JSONObject eachInFor = null;
/* 2240 */         String type = null;
/* 2241 */         String imagePath = null;
/* 2242 */         String typeDisplayName = null;
/* 2243 */         JSONArray list = json.getJSONObject("response").getJSONArray("result");
/* 2244 */         for (int i = 0; i < list.length(); i++) {
/* 2245 */           eachMonitor = new JSONObject();
/* 2246 */           eachInFor = list.getJSONObject(i);
/* 2247 */           typeDisplayName = FormatUtil.getString(eachInFor.getString("TYPESHORTNAME"));
/* 2248 */           type = eachInFor.getString("TYPE");
/* 2249 */           eachMonitor.put("typeDisplayName", typeDisplayName);
/* 2250 */           eachMonitor.put("TYPE", type);
/* 2251 */           String healthKey = AMAttributesCache.getHealthId(type);
/* 2252 */           eachMonitor.put("healthKey", healthKey);
/* 2253 */           imagePath = eachInFor.getString("IMAGEPATH");
/* 2254 */           eachMonitor.put("IMAGEPATH", this.admUtil.getNewDependencyImage(type, imagePath));
/* 2255 */           eachMonitor.put("DISPLAYNAME", eachInFor.getString("DISPLAYNAME"));
/* 2256 */           monitorList.put(eachInFor.getString("RESOURCEID"), eachMonitor);
/* 2257 */           if (availableResourceTypes.toString().indexOf(typeDisplayName) == -1) {
/* 2258 */             availableResourceTypes.put(typeDisplayName);
/*      */           }
/*      */         }
/* 2261 */         return new JSONObject().put("monitors", monitorList).put("availableResourceTypes", availableResourceTypes);
/*      */       }
/*      */     } catch (JSONException e) {
/* 2264 */       e.printStackTrace();
/*      */     } catch (Exception ex) {
/* 2266 */       ex.printStackTrace();
/*      */     }
/* 2268 */     return monitorList;
/*      */   }
/*      */   
/*      */   public String saveStateForJIT(String haid, String userid, String viewid, JSONObject nodeJSON, String xCanvas, String yCanvas, JSONObject resourceIdVsNodeId, boolean isADDM)
/*      */   {
/* 2273 */     boolean isdefaultview = true;
/* 2274 */     String zoomlevel = "1";
/* 2275 */     Statement statement = null;
/* 2276 */     String condition = "";
/* 2277 */     if (haid.equals("0")) {
/* 2278 */       if (!viewid.equals("-1"))
/*      */       {
/*      */ 
/* 2281 */         isdefaultview = false;
/*      */       } else {
/* 2283 */         isdefaultview = true;
/*      */       }
/*      */     }
/*      */     
/* 2287 */     AMLog.debug("Inside saveStateForJIT" + isdefaultview);
/* 2288 */     if ((!isADDM) && ("-1".equals(viewid))) {
/* 2289 */       JSONObject displayProps = new JSONObject();
/*      */       try {
/* 2291 */         displayProps.put("xCanvas", xCanvas);
/* 2292 */         displayProps.put("yCanvas", yCanvas);
/*      */       } catch (JSONException e) {
/* 2294 */         e.printStackTrace();
/*      */       }
/* 2296 */       viewid = saveViewConfig(displayProps, userid, haid) + "";
/*      */     }
/*      */     try {
/* 2299 */       AMConnectionPool.getInstance();statement = AMConnectionPool.getConnection().createStatement();
/*      */       
/* 2301 */       condition = parseJSONAndSaveCoordinates(haid, nodeJSON, statement, viewid, resourceIdVsNodeId, isADDM);
/* 2302 */       clearAllCoordinates(viewid, condition);
/* 2303 */       statement.executeBatch();
/* 2304 */       statement.clearBatch();
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
/* 2316 */       return viewid;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2306 */       e.getNextException().printStackTrace();
/* 2307 */       e.printStackTrace();
/*      */     } catch (Exception ex) {
/* 2309 */       ex.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 2312 */         statement.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void clearAllCoordinates(String viewid, String condition)
/*      */   {
/*      */     try
/*      */     {
/* 2323 */       if (!viewid.equals("-1"))
/*      */       {
/* 2325 */         if (condition.length() > 2) {
/* 2326 */           condition = condition.substring(0, condition.length() - 3);
/* 2327 */           condition = condition + ")";
/* 2328 */           AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("delete from AM_FLASHVIEW_COORDINATES where  VIEWID=" + viewid + " and " + condition);
/*      */         } else {
/* 2330 */           AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("delete from AM_FLASHVIEW_COORDINATES where  VIEWID=" + viewid);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2336 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public void clearAllCoordinates(String viewid)
/*      */   {
/*      */     try
/*      */     {
/* 2344 */       if (!viewid.equals("-1"))
/*      */       {
/* 2346 */         AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("delete from AM_FLASHVIEW_COORDINATES where  VIEWID=" + viewid);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2351 */       e.printStackTrace();
/*      */     }
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
/*      */   public String parseJSONAndSaveCoordinates(String groupId, JSONObject json, Statement statement, String viewid, JSONObject resourceVsNodeIdMap, boolean isADDM)
/*      */   {
/* 2472 */     String condition = "(";
/* 2473 */     String query = "";
/* 2474 */     deleteBussinessViewCache();
/*      */     try {
/* 2476 */       int totalNumberOfNodes = json.getInt("totalNumberOfNodes");
/* 2477 */       JSONArray nodeList = json.getJSONArray("nodeIdList");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2483 */       for (int i = 0; i < totalNumberOfNodes; i++) {
/* 2484 */         String index = nodeList.getString(i);
/* 2485 */         String parentId = json.getString("parentID_" + index);
/* 2486 */         String childId; if (isADDM) {
/* 2487 */           String childId = resourceVsNodeIdMap.getString(index);
/* 2488 */           parentId = resourceVsNodeIdMap.getString(parentId);
/*      */         } else {
/* 2490 */           childId = json.getString("resourceID_" + index);
/*      */         }
/* 2492 */         String positionX = json.getString("posX_" + index);
/* 2493 */         String positionY = json.getString("posY_" + index);
/*      */         
/*      */ 
/* 2496 */         if (!viewid.equals("-1")) {
/* 2497 */           query = "INSERT INTO AM_FLASHVIEW_COORDINATES (VIEWID,PARENTID,CHILDID,XCOORD,YCOORD)  VALUES (" + viewid + "," + parentId + "," + childId + "," + positionX + "," + positionY + ")";
/*      */         }
/*      */         else {
/* 2500 */           query = "INSERT INTO AM_FLASHVIEW_COORDINATES (VIEWID,PARENTID,CHILDID,XCOORD,YCOORD)  VALUES (" + DBQueryUtil.getIncrementedID("VIEWID", "AM_FLASHVIEW_COORDINATES") + "," + parentId + "," + childId + "," + positionX + "," + positionY + ")";
/*      */         }
/* 2502 */         AMLog.debug("Inserting to AM_FLASHVIEW_COORDINATES" + query);
/* 2503 */         condition = condition + "(PARENTID=" + parentId + " AND CHILDID=" + childId + ") OR";
/* 2504 */         statement.addBatch(query);
/*      */       }
/*      */     } catch (JSONException e) {
/* 2507 */       e.printStackTrace();
/*      */     } catch (SQLException e) {
/* 2509 */       e.printStackTrace();
/*      */     }
/* 2511 */     condition = condition + ")";
/* 2512 */     return condition;
/*      */   }
/*      */   
/*      */   public String decodeSpecialCharactersFromJSONString(String json) {
/* 2516 */     json = json.replaceAll("&quot;", "\"");
/* 2517 */     json = json.replaceAll("&lt;", "<");
/* 2518 */     json = json.replaceAll("&gt;", ">");
/* 2519 */     json = json.replaceAll("&#39;", "'");
/* 2520 */     return json;
/*      */   }
/*      */   
/*      */   public JSONObject getAvailableViews(String haid) {
/* 2524 */     ResultSet rs = null;
/* 2525 */     JSONObject availableViews = new JSONObject();
/* 2526 */     JSONObject result = new JSONObject();
/* 2527 */     String query = "select VIEWID,DISPLAYNAME from AM_MONITORGROUP_FLASHVIEWCONFIG where HAID=" + haid + " order by UPPER(DISPLAYNAME)";
/*      */     try {
/* 2529 */       List<String> dispNameList = new ArrayList();
/* 2530 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2531 */       while (rs.next())
/*      */       {
/* 2533 */         availableViews.put(rs.getString("DISPLAYNAME"), rs.getString("VIEWID"));
/* 2534 */         dispNameList.add(rs.getString("DISPLAYNAME"));
/*      */       }
/* 2536 */       result.put("views", availableViews);
/* 2537 */       result.put("dispNameList", dispNameList);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2541 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2545 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 2547 */     return result;
/*      */   }
/*      */   
/*      */   public boolean deleteView(String viewid)
/*      */   {
/* 2552 */     boolean isDeleted = true;
/*      */     try
/*      */     {
/* 2555 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 2556 */       AMConnectionPool.executeUpdateStmt("delete from  AM_MONITORGROUP_FLASHVIEWCONFIG  where VIEWID=" + viewid);
/* 2557 */       AMConnectionPool.executeUpdateStmt("delete from  AM_FLASHVIEW_FILTER  where VIEWID=" + viewid);
/* 2558 */       AMConnectionPool.executeUpdateStmt("delete from  AM_FLASHVIEW_COORDINATES  where VIEWID=" + viewid);
/* 2559 */       AMConnectionPool.executeUpdateStmt("delete from  AM_MYPAGE_WIDGET_FLASHVIEWS  where VIEWID=" + viewid);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2563 */       e.printStackTrace();
/* 2564 */       isDeleted = false;
/*      */     }
/* 2566 */     return isDeleted;
/*      */   }
/*      */   
/*      */   public boolean updateNodePositionForANode(long resId, long parentResId, double relativeX, double relativeY, long viewId)
/*      */   {
/*      */     try
/*      */     {
/* 2573 */       if ((!Double.isNaN(relativeX)) && (!Double.isNaN(relativeY)))
/*      */       {
/* 2575 */         String delQuery = "delete from AM_FLASHVIEW_COORDINATES where PARENTID=" + parentResId + " and CHILDID=" + resId + " and VIEWID=" + viewId;
/* 2576 */         AMLog.debug("Delete Query" + delQuery);
/* 2577 */         AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(delQuery);
/* 2578 */         String query = "insert into AM_FLASHVIEW_COORDINATES values(" + viewId + "," + parentResId + "," + resId + "," + relativeX + "," + relativeY + ")";
/* 2579 */         AMLog.debug("Update Query" + query);
/* 2580 */         AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(query);
/* 2581 */         deleteBussinessViewCache();
/*      */       }
/*      */       else
/*      */       {
/* 2585 */         AMLog.debug("Position not saved for a node::" + resId);
/*      */       }
/*      */     } catch (SQLException e) {
/* 2588 */       e.printStackTrace();
/*      */     }
/*      */     catch (Exception e) {
/* 2591 */       e.printStackTrace();
/*      */     }
/* 2593 */     return false;
/*      */   }
/*      */   
/*      */   public boolean updateCanvasPositionForANode(long haid, double translateOffsetX, double translateOffsetY, long viewId)
/*      */   {
/*      */     try {
/* 2599 */       if (viewId != -1L) {
/* 2600 */         String updateQuery = "update AM_MONITORGROUP_FLASHVIEWCONFIG set XCANVAS=" + translateOffsetX + ", YCANVAS=" + translateOffsetY + "where VIEWID=" + viewId;
/* 2601 */         AMLog.debug("updateQuery" + updateQuery);
/* 2602 */         AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(updateQuery);
/* 2603 */         deleteBussinessViewCache(viewId + "_" + haid);
/*      */       }
/*      */     } catch (SQLException e) {
/* 2606 */       e.printStackTrace();
/*      */     }
/*      */     catch (Exception e) {
/* 2609 */       e.printStackTrace();
/*      */     }
/* 2611 */     return false;
/*      */   }
/*      */   
/*      */   public String getBVNameForViewID(String viewid)
/*      */   {
/* 2616 */     String displayName = FormatUtil.getString("am.webclient.flashview.displayname");
/* 2617 */     ResultSet rs = null;
/*      */     try {
/* 2619 */       String query = "select DISPLAYNAME from AM_MONITORGROUP_FLASHVIEWCONFIG where VIEWID=" + viewid;
/* 2620 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/* 2621 */       if (rs.next())
/*      */       {
/* 2623 */         displayName = rs.getString("DISPLAYNAME");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2628 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2631 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2633 */     AMLog.debug("DisplayName for BusinessView ViewID::" + viewid);
/* 2634 */     return displayName;
/*      */   }
/*      */   
/*      */   public JSONObject getAllGraphProperties(String viewid) {
/* 2638 */     JSONObject displayProps = new JSONObject();
/* 2639 */     JSONObject resultObj = new JSONObject();
/* 2640 */     ResultSet rs = null;
/*      */     try {
/* 2642 */       String query = "select * from AM_MONITORGROUP_FLASHVIEWCONFIG where VIEWID=" + viewid;
/* 2643 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/* 2644 */       if (rs.next())
/*      */       {
/* 2646 */         displayProps.put("displayName", rs.getString("DISPLAYNAME"));
/* 2647 */         displayProps.put("description", rs.getString("DESCRIPTION"));
/* 2648 */         displayProps.put("backgroundColorVal", rs.getString("BGCOLOR"));
/* 2649 */         displayProps.put("lineColorVal", rs.getString("LINECOLOR").replaceFirst("0x", "#"));
/* 2650 */         displayProps.put("textColorVal", rs.getString("LABELCOLOR").replaceFirst("0x", "#"));
/* 2651 */         displayProps.put("showLabel", Boolean.valueOf(rs.getString("SHOWLABEL")));
/*      */         
/* 2653 */         displayProps.put("lineThickness", rs.getFloat("LINETHICKNESS"));
/* 2654 */         displayProps.put("lineTransparency", rs.getFloat("LINETRANSPARENCY"));
/*      */         
/*      */ 
/* 2657 */         displayProps.put("showOnlyCritical", Boolean.valueOf(rs.getString("SHOWCRITICALMONITORS")));
/* 2658 */         displayProps.put("showOnlyMGs", Boolean.valueOf(rs.getString("SHOWONLYSUBGROUPS")));
/* 2659 */         displayProps.put("showOnlyMGStatus", Boolean.valueOf(rs.getString("SHOWTOPLEVELMGS")));
/* 2660 */         displayProps.put("showOnlyTopMGs", Boolean.valueOf(rs.getString("SHOWTOPLEVELSUBMGS")));
/* 2661 */         displayProps.put("noOfColumns", rs.getInt("NOOFCOLUMNS"));
/*      */         
/* 2663 */         displayProps.put("haid", rs.getString("HAID"));
/*      */         
/* 2665 */         resultObj.put("isConfigSaved", Boolean.TRUE);
/* 2666 */         resultObj.put("displayProps", displayProps);
/*      */       } else {
/* 2668 */         resultObj.put("isConfigSaved", Boolean.FALSE);
/* 2669 */         resultObj.put("displayName", FormatUtil.getString("am.webclient.flashview.displayname"));
/*      */       }
/*      */     } catch (SQLException e) {
/* 2672 */       e.printStackTrace();
/*      */     } catch (Exception e) {
/* 2674 */       e.printStackTrace();
/*      */     } finally {
/* 2676 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2678 */     return resultObj;
/*      */   }
/*      */   
/*      */   public String getDisplayPropsForGlobalGraphUtil(String viewid, boolean isPrivillagedUser, String userid) {
/* 2682 */     String bsgType = "0";
/* 2683 */     String resultToReturn = "<ul>";
/*      */     try {
/* 2685 */       String mgquery = "select AM_ManagedObject.RESOURCEID as RESID,AM_ManagedObject.DISPLAYNAME as DISPNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + " order by AM_ManagedObject.DISPLAYNAME";
/*      */       
/* 2687 */       if (isPrivillagedUser) {
/* 2688 */         mgquery = "select AM_ManagedObject.RESOURCEID as RESID,AM_ManagedObject.DISPLAYNAME as DISPNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject,AM_HOLISTICAPPLICATION_OWNERS where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=0  and AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=" + userid + " order by AM_ManagedObject.DISPLAYNAME";
/*      */       }
/*      */       
/* 2691 */       String filterquery = "select HAID from AM_FLASHVIEW_FILTER where VIEWID=" + viewid;
/* 2692 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2693 */       ResultSet rs = null;
/* 2694 */       ResultSet filterRs = null;
/*      */       try {
/* 2696 */         ArrayList haidList = new ArrayList();
/* 2697 */         boolean isFilterAvailable = false;
/* 2698 */         filterRs = AMConnectionPool.executeQueryStmt(filterquery);
/* 2699 */         while (filterRs.next()) {
/* 2700 */           String haid = filterRs.getString("HAID");
/* 2701 */           haidList.add(haid);
/* 2702 */           isFilterAvailable = true;
/*      */         }
/* 2704 */         rs = AMConnectionPool.executeQueryStmt(mgquery);
/* 2705 */         while (rs.next()) {
/* 2706 */           String resId = rs.getString("RESID");
/* 2707 */           String groupName = rs.getString("DISPNAME");
/* 2708 */           resultToReturn = resultToReturn + "<li><label><input type='checkbox' name=checkbox_'" + resId + " id=showMG_" + resId + " value='" + resId + "' ";
/*      */           
/* 2710 */           if ((!isFilterAvailable) || (haidList.contains(resId))) {
/* 2711 */             resultToReturn = resultToReturn + "checked";
/*      */           }
/* 2713 */           resultToReturn = resultToReturn + ">" + groupName + "</label></li>";
/*      */         }
/*      */       } catch (SQLException e) {
/* 2716 */         e.printStackTrace();
/*      */       } finally {
/* 2718 */         AMConnectionPool.closeResultSet(rs);
/* 2719 */         AMConnectionPool.closeResultSet(filterRs);
/*      */       }
/*      */     } catch (Exception e) {
/* 2722 */       e.printStackTrace();
/*      */     }
/* 2724 */     resultToReturn = resultToReturn + "</ul>";
/* 2725 */     return resultToReturn;
/*      */   }
/*      */   
/*      */   public boolean updateMonitorGroupsToFilterListUtil(String viewid, JSONArray monitorGroups, String noOfColumns)
/*      */   {
/* 2730 */     String deleteQuery = "delete from AM_FLASHVIEW_FILTER where VIEWID=" + viewid;
/*      */     try {
/* 2732 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(deleteQuery);
/*      */     } catch (Exception e) {
/* 2734 */       e.printStackTrace();
/*      */     }
/* 2736 */     String updateNoOfColQuery = "update AM_MONITORGROUP_FLASHVIEWCONFIG set NOOFCOLUMNS=" + noOfColumns + " where VIEWID=" + viewid;
/* 2737 */     AMLog.debug("QUERY updateNoOfColQuery" + updateNoOfColQuery);
/* 2738 */     isUpdated = false;
/*      */     try {
/* 2740 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(updateNoOfColQuery);
/*      */     } catch (SQLException e) {
/* 2742 */       e.printStackTrace();
/*      */     }
/* 2744 */     if (monitorGroups != null) {
/* 2745 */       Statement statement = null;
/*      */       try {
/* 2747 */         AMConnectionPool.getInstance();statement = AMConnectionPool.getConnection().createStatement();
/* 2748 */         for (int i = 0; i < monitorGroups.length(); i++) {
/* 2749 */           String insertFilterListQuery = "insert into AM_FLASHVIEW_FILTER(VIEWID,HAID) values(" + viewid + "," + monitorGroups.getString(i) + ")";
/* 2750 */           statement.addBatch(insertFilterListQuery);
/* 2751 */           statement.executeBatch();
/* 2752 */           statement.clearBatch();
/*      */         }
/* 2754 */         return true;
/*      */       } catch (SQLException e) {
/* 2756 */         e.printStackTrace();
/* 2757 */         e.getNextException().printStackTrace();
/*      */       } catch (JSONException e) {
/* 2759 */         e.printStackTrace();
/*      */       } catch (Exception e) {
/* 2761 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*      */         try {
/* 2765 */           statement.close();
/*      */         }
/*      */         catch (Exception ex) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public int getNoOfColumnsForGlobalView(String viewid)
/*      */   {
/* 2774 */     String columnNumberQuery = "select NOOFCOLUMNS from AM_MONITORGROUP_FLASHVIEWCONFIG where VIEWID=" + viewid;
/* 2775 */     ResultSet colRs = null;
/* 2776 */     int noOfColumns = 4;
/*      */     try {
/* 2778 */       AMConnectionPool.getInstance();colRs = AMConnectionPool.executeQueryStmt(columnNumberQuery);
/* 2779 */       if (colRs.next()) {
/* 2780 */         noOfColumns = colRs.getInt("NOOFCOLUMNS");
/*      */       }
/*      */     } catch (SQLException e) {
/* 2783 */       e.printStackTrace();
/*      */     } finally {
/* 2785 */       AMConnectionPool.closeStatement(colRs);
/*      */     }
/* 2787 */     return noOfColumns;
/*      */   }
/*      */   
/*      */   public boolean updateShowTopLevelFlag(String viewid, String isShowTopLevel) {
/* 2791 */     String updateQuery = "update AM_MONITORGROUP_FLASHVIEWCONFIG set SHOWTOPLEVELMGS='" + isShowTopLevel + "' where VIEWID=" + viewid;
/* 2792 */     boolean isUpdated = Boolean.FALSE.booleanValue();
/*      */     try {
/* 2794 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(updateQuery);
/* 2795 */       isUpdated = Boolean.TRUE.booleanValue();
/*      */     } catch (SQLException e) {
/* 2797 */       e.printStackTrace();
/*      */     }
/* 2799 */     return isUpdated;
/*      */   }
/*      */   
/*      */   public String getEditLinksForMonitors(String resourceId, String resourceName, String monType, String displayName)
/*      */   {
/* 2804 */     String link = "";
/* 2805 */     NewMonitorConf newMonUtil = new NewMonitorConf();
/* 2806 */     resourceName = newMonUtil.getResourceTypeForPreConf(resourceName);
/*      */     try {
/* 2808 */       if (ConfMonitorConfiguration.getInstance().isConfMonitor(monType)) {
/* 2809 */         link = "/manageConfMons.do?method=editMonitor&resourceid=" + resourceId + "&type=" + monType;
/* 2810 */       } else if (new PreConfMonitorXMLParser().getPreConfMonitorListSupported().containsKey(resourceName)) {
/* 2811 */         link = "/manageConfMons.do?resourceid=" + resourceId + "&method=editPreConfMonitor&type=" + monType + "&resourcename=" + resourceName;
/*      */       } else {
/* 2813 */         link = "/showresource.do?resourceid=" + resourceId + "&type=" + monType + "&method=showdetails&editPage=true";
/*      */       }
/*      */     } catch (Exception e) {
/* 2816 */       e.printStackTrace();
/*      */     }
/* 2818 */     return link;
/*      */   }
/*      */   
/*      */   public boolean resetDesign(String viewid, String haid)
/*      */   {
/*      */     try {
/* 2824 */       AMLog.debug("Inside reset Design" + viewid);
/*      */       
/* 2826 */       String query1 = "update AM_MONITORGROUP_FLASHVIEWCONFIG set XCANVAS=-47.551020408163261,YCANVAS=0,LINECOLOR='#888c8f',LINETHICKNESS=1,LABELCOLOR='#444444',BGCOLOR='#FFFFFF',LINETRANSPARENCY=1,SHOWCRITICALMONITORS='false',SHOWONLYSUBGROUPS='false',SHOWTOPLEVELMGS='false',SHOWLABEL='true',SHOWTOPLEVELSUBMGS='false' where VIEWID=" + viewid;
/* 2827 */       String query2 = "delete from AM_FLASHVIEW_COORDINATES where VIEWID=" + viewid;
/* 2828 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(query1);
/* 2829 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(query2);
/* 2830 */       deleteBussinessViewCache(viewid + "_" + haid);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2834 */       ex.printStackTrace();
/* 2835 */       return false;
/*      */     }
/* 2837 */     return true;
/*      */   }
/*      */   
/*      */   public static Properties getGroupDetails(String haid, ManagedApplication mo) {
/* 2841 */     Properties groupDetails = new Properties();
/* 2842 */     ResultSet rs = null;
/*      */     try {
/* 2844 */       ArrayList applist = mo.getRows("select AM_ManagedObject.DESCRIPTION,CREATIONDATE,MODIFIEDDATE,DISPLAYNAME,AM_HOLISTICAPPLICATION.TYPE,AM_HOLISTICAPPLICATION.GROUPTYPE from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_HOLISTICAPPLICATION.HAID=" + haid + " and  AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID");
/* 2845 */       ArrayList locationid = mo.getRows("select LOCATIONID from AM_GMapCountryResourceRel where ID=" + haid);
/* 2846 */       ArrayList row = null;
/* 2847 */       if (applist.size() > 0)
/*      */       {
/*      */ 
/* 2850 */         row = (ArrayList)applist.get(0);
/* 2851 */         String name = (String)row.get(3);
/* 2852 */         String description = Translate.decode((String)row.get(0));
/* 2853 */         String creationdate = (String)row.get(1);
/* 2854 */         String lastmodified = (String)row.get(2);
/* 2855 */         String MGtype = (String)row.get(4);
/* 2856 */         String groupType = (String)row.get(5);
/* 2857 */         groupDetails.setProperty("name", name);
/* 2858 */         groupDetails.setProperty("MGtype", MGtype);
/* 2859 */         groupDetails.setProperty("haid", haid);
/* 2860 */         groupDetails.setProperty("description", description);
/* 2861 */         groupDetails.setProperty("grouptype", groupType);
/* 2862 */         if (locationid.size() == 1) {
/* 2863 */           List cidList = (List)locationid.get(0);
/* 2864 */           groupDetails.put("locationid", cidList.get(0));
/*      */         } else {
/* 2866 */           groupDetails.put("locationid", "-1");
/*      */         }
/*      */         
/* 2869 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */         try
/*      */         {
/* 2872 */           ArrayList allOwnersList = null;
/*      */           
/* 2874 */           String allOwnQuery = null;
/* 2875 */           if (EnterpriseUtil.isAdminServer) {
/* 2876 */             allOwnQuery = "select USERID,AM_UserPasswordTable.USERNAME from AM_UserPasswordTable left outer join AM_HOLISTICAPPLICATION_OWNERS on AM_UserPasswordTable.USERID=AM_HOLISTICAPPLICATION_OWNERS.OWNERID and HAID=" + haid + " left outer join AM_UserGroupTable on AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME where AM_HOLISTICAPPLICATION_OWNERS.HAID IS NULL and AM_UserPasswordTable.USERNAME !='reportadmin' and AM_UserPasswordTable.USERNAME !='systemadmin_enterprise'  group by AM_UserPasswordTable.USERNAME,USERID";
/*      */           }
/*      */           else {
/* 2879 */             allOwnQuery = "select Distinct USERID,AM_UserPasswordTable.USERNAME from AM_UserPasswordTable left outer join AM_HOLISTICAPPLICATION_OWNERS on AM_UserPasswordTable.USERID=AM_HOLISTICAPPLICATION_OWNERS.OWNERID and HAID=" + haid + " left outer join AM_UserGroupTable on AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME where AM_HOLISTICAPPLICATION_OWNERS.HAID IS NULL and AM_UserGroupTable.GROUPNAME!='ENTERPRISEADMIN' and  AM_UserPasswordTable.USERNAME !='reportadmin' ";
/*      */           }
/* 2881 */           rs = AMConnectionPool.executeQueryStmt(allOwnQuery);
/* 2882 */           while (rs.next())
/*      */           {
/* 2884 */             if (allOwnersList == null)
/*      */             {
/* 2886 */               allOwnersList = new ArrayList(4);
/*      */             }
/* 2888 */             Properties p = new Properties();
/* 2889 */             p.setProperty("label", rs.getString("USERNAME"));
/* 2890 */             p.setProperty("value", rs.getString("USERID"));
/* 2891 */             allOwnersList.add(p);
/*      */           }
/* 2893 */           rs.close();
/* 2894 */           AMLog.debug("allOwnersList" + allOwnersList);
/* 2895 */           if (allOwnersList != null)
/*      */           {
/* 2897 */             groupDetails.put("allowners", allOwnersList);
/*      */           }
/*      */           else
/*      */           {
/* 2901 */             groupDetails.put("allowners", new ArrayList());
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2906 */           e.printStackTrace();
/* 2907 */           rs = null;
/*      */         }
/*      */         try
/*      */         {
/* 2911 */           ArrayList selectedOwnersList = null;
/* 2912 */           String selOwnQuery = "select AM_HOLISTICAPPLICATION_OWNERS.OWNERID,USERNAME from AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and HAID=" + haid;
/* 2913 */           rs = AMConnectionPool.executeQueryStmt(selOwnQuery);
/* 2914 */           while (rs.next())
/*      */           {
/* 2916 */             if (selectedOwnersList == null)
/*      */             {
/* 2918 */               selectedOwnersList = new ArrayList(4);
/*      */             }
/* 2920 */             Properties p = new Properties();
/* 2921 */             p.setProperty("label", rs.getString("USERNAME"));
/* 2922 */             p.setProperty("value", rs.getString("OWNERID"));
/* 2923 */             selectedOwnersList.add(p);
/*      */           }
/* 2925 */           if (selectedOwnersList != null)
/*      */           {
/* 2927 */             groupDetails.put("selectedowners", selectedOwnersList);
/*      */           }
/*      */           else
/*      */           {
/* 2931 */             groupDetails.put("selectedowners", new ArrayList());
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2936 */           e.printStackTrace();
/* 2937 */           rs = null;
/*      */         }
/* 2939 */         groupDetails.setProperty("creationdate", creationdate);
/* 2940 */         groupDetails.setProperty("lastmodified", lastmodified);
/*      */       }
/*      */     } catch (Exception e) {
/* 2943 */       e.printStackTrace();
/*      */     } finally {
/* 2945 */       AMConnectionPool.getInstance();AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 2947 */     return groupDetails;
/*      */   }
/*      */   
/*      */   public static boolean updateGroupDetailsForBVGraphNode(String haid, String groupName, String description, String locId, String owners, Properties applications, String groupType, HttpServletResponse response, String userName)
/*      */   {
/*      */     try {
/* 2953 */       String SERVLET_PATH = "/AppManager";
/* 2954 */       String PATH_INFO = "/json/group/edit";
/* 2955 */       String apiKey = EnterpriseUtil.getAPIKey(userName);
/* 2956 */       MockHttpServletRequest MSreq = new MockHttpServletRequest("", "/AppManager", "/json/group/edit", "");
/* 2957 */       ShowApplication sa = new ShowApplication();
/* 2958 */       MSreq.setAttribute("haid", haid);
/* 2959 */       MSreq.setAttribute("name", groupName);
/* 2960 */       MSreq.setAttribute("description", description);
/* 2961 */       MSreq.setAttribute("locationid", locId);
/* 2962 */       MSreq.setAttribute("owners", owners);
/* 2963 */       MSreq.setAttribute("fromwhere", "allmonitorgroups");
/* 2964 */       MSreq.setAttribute("applications", applications);
/* 2965 */       MSreq.setAttribute("isRESTAPI", "true");
/* 2966 */       sa.update(null, null, MSreq, response);
/* 2967 */       return Boolean.TRUE.booleanValue();
/*      */     } catch (Exception e) {
/* 2969 */       e.printStackTrace();
/*      */     }
/* 2971 */     return Boolean.FALSE.booleanValue();
/*      */   }
/*      */   
/*      */   public static void deleteBussinessViewCache() {}
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\BusinessViewUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */