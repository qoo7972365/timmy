/*      */ package com.manageengine.appmanager.utils;
/*      */ 
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.appmanager.APIRequestHandler;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.servlet.http.HttpServletRequest;
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
/*      */ public class MobileUtils
/*      */ {
/*      */   public static void getDashboardDetails(HttpServletRequest request)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*   35 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/*      */       
/*   37 */       request.setAttribute("uri", "/AppManager/json/ListDashboards?apikey=api_key");
/*   38 */       String response = apiReqHandler.getRequestData(request);
/*      */       
/*   40 */       JSONObject jsonOutput = new JSONObject(response);
/*      */       
/*   42 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/*   44 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/*   45 */         ArrayList<Hashtable> dashboards = new ArrayList();
/*   46 */         Hashtable<String, ArrayList<Properties>> widgets = new Hashtable();
/*   47 */         if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */         {
/*   49 */           for (int i = 0; i < jsonResult.length(); i++)
/*      */           {
/*   51 */             Hashtable dashboardsTable = new Hashtable();
/*   52 */             ArrayList<Properties> widgetList = new ArrayList();
/*   53 */             JSONObject jsonDashboard = jsonResult.optJSONObject(i);
/*   54 */             if (jsonDashboard != null)
/*      */             {
/*   56 */               dashboardsTable.put("DASHBOARDNAME", jsonDashboard.optString("DashboardName"));
/*   57 */               dashboardsTable.put("DASHBOARDID", jsonDashboard.optString("DashboardId"));
/*   58 */               JSONArray jsonWidgets = jsonDashboard.optJSONArray("Widget");
/*   59 */               if (jsonWidgets.length() > 0)
/*      */               {
/*   61 */                 for (int j = 0; j < jsonWidgets.length(); j++)
/*      */                 {
/*   63 */                   Properties widgetProps = new Properties();
/*   64 */                   widgetProps.setProperty("WIDGETID", jsonWidgets.optJSONObject(j).optString("WidgetId"));
/*   65 */                   widgetProps.setProperty("WIDGETNAME", jsonWidgets.optJSONObject(j).optString("WidgetName"));
/*   66 */                   widgetProps.setProperty("WIDGETURL", jsonWidgets.optJSONObject(j).optString("WidgetURL"));
/*   67 */                   widgetList.add(widgetProps);
/*      */                 }
/*      */               }
/*   70 */               dashboardsTable.put("widgetList", widgetList);
/*   71 */               dashboards.add(dashboardsTable);
/*   72 */               widgets.put(jsonDashboard.optString("DashboardId"), widgetList);
/*      */             }
/*      */           }
/*   75 */           request.setAttribute("dashboards", dashboards);
/*   76 */           request.setAttribute("widgets", widgets);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*   82 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static void getMonitorDetails(HttpServletRequest request)
/*      */   {
/*      */     try
/*      */     {
/*   91 */       Properties monitorDetails = new Properties();
/*   92 */       ArrayList<Properties> attributeDetails = new ArrayList();
/*   93 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/*   94 */       String resId = request.getParameter("resourceid");
/*   95 */       request.setAttribute("uri", "/AppManager/json/ListMonitorData?apikey=api_key&resourceid=" + resId);
/*   96 */       String response = apiReqHandler.getRequestData(request);
/*   97 */       JSONObject jsonOutput = new JSONObject(response);
/*   98 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/*  100 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/*  101 */         if ((jsonResult != null) && (jsonResult.length() == 1))
/*      */         {
/*  103 */           JSONObject jsonMonitor = jsonResult.getJSONObject(0);
/*  104 */           String[] monDetails = JSONObject.getNames(jsonMonitor);
/*      */           
/*      */           try
/*      */           {
/*  108 */             String hostIP = jsonMonitor.optString("TARGETADDRESS");
/*  109 */             String hostIPHeader = FormatUtil.getString("am.mobile.hostorip.txt");
/*  110 */             if (hostIP.equals(""))
/*      */             {
/*  112 */               hostIP = jsonMonitor.optString("TARGETNAME");
/*  113 */               String type = jsonMonitor.optString("TYPE");
/*  114 */               if (type.contains("VirtualMachine"))
/*      */               {
/*  116 */                 hostIP = jsonMonitor.optString("DISPLAYNAME");
/*      */               }
/*  118 */               if (hostIP.equals(""))
/*      */               {
/*  120 */                 hostIP = jsonMonitor.optString("RESOURCENAME");
/*  121 */                 hostIP = hostIP.split("_")[0].replaceAll("IF-", "");
/*  122 */                 if ((hostIP.length() > 21) && (!hostIP.contains(" ")))
/*      */                 {
/*  124 */                   hostIP = hostIP.substring(0, 21) + " " + hostIP.substring(21);
/*      */                 }
/*  126 */                 hostIPHeader = FormatUtil.getString("am.mobile.resourcename.txt");
/*      */               }
/*      */             }
/*  129 */             monitorDetails.setProperty("hostIPHeader", hostIPHeader);
/*  130 */             monitorDetails.setProperty("hostIP", hostIP);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  134 */             e.printStackTrace();
/*      */           }
/*      */           
/*  137 */           for (int i = 0; i < monDetails.length; i++)
/*      */           {
/*  139 */             String key = monDetails[i];
/*  140 */             String value = "";
/*  141 */             if (key != null)
/*      */             {
/*  143 */               value = jsonMonitor.optString(key);
/*  144 */               if (value.equals("No Data Available."))
/*      */               {
/*  146 */                 value = FormatUtil.getString("NA");
/*      */               }
/*  148 */               if (key.equals("Attribute"))
/*      */               {
/*  150 */                 JSONArray attrbs = jsonMonitor.optJSONArray("Attribute");
/*  151 */                 for (int k = 0; k < attrbs.length(); k++)
/*      */                 {
/*  153 */                   if (!attrbs.optJSONObject(k).optString("DISPLAYNAME").equals(""))
/*      */                   {
/*  155 */                     Properties attrbDetails = new Properties();
/*  156 */                     String attId = attrbs.optJSONObject(k).optString("AttributeID");
/*  157 */                     attrbDetails.setProperty("AttributeID", attId);
/*  158 */                     attrbDetails.setProperty("ReportsUrl", "/mobile/DetailsView.do?method=showHistoryDetails&resourceid=" + resId + "&attributeID=" + attId);
/*  159 */                     attrbDetails.setProperty("DISPLAYNAME", attrbs.optJSONObject(k).optString("DISPLAYNAME"));
/*  160 */                     attrbDetails.setProperty("Value", attrbs.optJSONObject(k).optString("Value"));
/*  161 */                     attrbDetails.setProperty("Units", attrbs.optJSONObject(k).optString("Units").trim());
/*  162 */                     attributeDetails.add(attrbDetails);
/*      */                   }
/*      */                 }
/*      */               }
/*  166 */               if (key.equals("CHILDMONITORS"))
/*      */               {
/*  168 */                 JSONArray childMonitorTypes = jsonMonitor.optJSONArray("CHILDMONITORS");
/*  169 */                 ArrayList<Hashtable> childMonitorTypesList = new ArrayList();
/*  170 */                 for (int k = 0; k < childMonitorTypes.length(); k++)
/*      */                 {
/*  172 */                   Hashtable childMonitorTypeTable = new Hashtable();
/*  173 */                   JSONObject childMonitorType = childMonitorTypes.optJSONObject(k);
/*  174 */                   String[] childMonitorTypeKeys = JSONObject.getNames(childMonitorType);
/*  175 */                   if (!childMonitorType.optString("DISPLAYNAME").equals(""))
/*      */                   {
/*  177 */                     for (int j = 0; j < childMonitorTypeKeys.length; j++)
/*      */                     {
/*  179 */                       String childMonTypeKey = childMonitorTypeKeys[j];
/*      */                       
/*  181 */                       if (!childMonTypeKey.equals("CHILDMONITORINFO"))
/*      */                       {
/*  183 */                         childMonitorTypeTable.put(childMonTypeKey, childMonitorType.optString(childMonTypeKey));
/*      */                       }
/*      */                       else
/*      */                       {
/*  187 */                         ArrayList<Hashtable> childMonitorList = new ArrayList();
/*  188 */                         JSONArray childMonitorsArray = childMonitorType.optJSONArray(childMonTypeKey);
/*  189 */                         if (childMonitorsArray.length() > 0)
/*      */                         {
/*  191 */                           for (int l = 0; l < childMonitorsArray.length(); l++)
/*      */                           {
/*  193 */                             JSONObject childMonitor = childMonitorsArray.optJSONObject(l);
/*  194 */                             String[] childMonitorKeys = JSONObject.getNames(childMonitor);
/*      */                             
/*  196 */                             if (!childMonitor.optString("DISPLAYNAME").equals(""))
/*      */                             {
/*  198 */                               Hashtable childMonitorTable = new Hashtable();
/*  199 */                               for (int m = 0; m < childMonitorKeys.length; m++)
/*      */                               {
/*  201 */                                 String childMonKey = childMonitorKeys[m];
/*  202 */                                 if (!childMonKey.equals("CHILDATTRIBUTES"))
/*      */                                 {
/*  204 */                                   String childMonValue = childMonitor.optString(childMonKey);
/*  205 */                                   if (childMonKey.equals("AVAILABILITYSEVERITY"))
/*      */                                   {
/*  207 */                                     childMonitorTable.put("AVAILABILITYSTATUS", childMonValue.equals("-") ? childMonValue : getSeverityIcon("availability", childMonValue));
/*      */                                   }
/*  209 */                                   else if (childMonKey.equals("HEALTHSEVERITY"))
/*      */                                   {
/*  211 */                                     childMonitorTable.put("HEALTHSTATUS", childMonValue.equals("-") ? childMonValue : getSeverityIcon("health", childMonValue));
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*  215 */                                     childMonitorTable.put(childMonKey, childMonValue);
/*      */                                   }
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*  220 */                                   JSONArray childMonitorAttrs = childMonitor.optJSONArray(childMonKey);
/*  221 */                                   if (childMonitorAttrs.length() > 0)
/*      */                                   {
/*  223 */                                     ArrayList<Properties> childAttrDetails = new ArrayList();
/*  224 */                                     for (int n = 0; n < childMonitorAttrs.length(); n++)
/*      */                                     {
/*  226 */                                       if (childMonitorAttrs.optJSONObject(n).length() > 0)
/*      */                                       {
/*  228 */                                         Properties childAttrbDetails = new Properties();
/*  229 */                                         childAttrbDetails.setProperty("AttributeID", childMonitorAttrs.optJSONObject(n).optString("AttributeID"));
/*  230 */                                         childAttrbDetails.setProperty("DISPLAYNAME", childMonitorAttrs.optJSONObject(n).optString("DISPLAYNAME"));
/*  231 */                                         childAttrbDetails.setProperty("Value", childMonitorAttrs.optJSONObject(n).optString("Value"));
/*  232 */                                         childAttrbDetails.setProperty("Units", childMonitorAttrs.optJSONObject(n).optString("Units").trim());
/*  233 */                                         childAttrDetails.add(childAttrbDetails);
/*      */                                       }
/*      */                                     }
/*  236 */                                     childMonitorTable.put(childMonKey, childAttrDetails);
/*      */                                   }
/*      */                                 }
/*      */                               }
/*  240 */                               childMonitorList.add(childMonitorTable);
/*      */                             }
/*      */                           }
/*      */                         }
/*  244 */                         childMonitorTypeTable.put(childMonTypeKey, childMonitorList);
/*      */                       }
/*      */                     }
/*  247 */                     childMonitorTypesList.add(childMonitorTypeTable);
/*      */                   }
/*      */                 }
/*  250 */                 request.setAttribute("childMonDetails", childMonitorTypesList);
/*      */               }
/*  252 */               else if (key.equals("LASTPOLLEDTIME"))
/*      */               {
/*  254 */                 if (value.length() < 0)
/*      */                 {
/*  256 */                   value = "-";
/*      */                 }
/*  258 */                 monitorDetails.setProperty(key, value);
/*      */               }
/*  260 */               else if ((key.startsWith("TODAY")) && (key.contains("PERCENT")))
/*      */               {
/*  262 */                 monitorDetails.setProperty(key.replaceAll("TODAY", ""), getFormattedValue(value));
/*      */               }
/*  264 */               else if ((key.equals("CPUUTIL")) || (key.equals("PHYMEMUTIL")) || (key.equals("DISKUTIL")))
/*      */               {
/*  266 */                 if (value.equals("-1"))
/*      */                 {
/*  268 */                   value = FormatUtil.getString("NA");
/*      */                 }
/*      */                 else
/*      */                 {
/*  272 */                   value = value + " %";
/*      */                 }
/*  274 */                 monitorDetails.setProperty(key, value);
/*      */               }
/*      */               else
/*      */               {
/*  278 */                 monitorDetails.setProperty(key, value);
/*      */               }
/*      */               
/*  281 */               if (key.equals("HEALTHSEVERITY"))
/*      */               {
/*  283 */                 monitorDetails.setProperty("HEALTHICON", getSeverityIcon("health", value));
/*      */               }
/*  285 */               else if (key.equals("AVAILABILITYSEVERITY"))
/*      */               {
/*  287 */                 monitorDetails.setProperty("AVAILABILITYICON", getSeverityIcon("availability", value));
/*      */               }
/*      */             }
/*      */           }
/*  291 */           request.setAttribute("monitorDetails", monitorDetails);
/*  292 */           request.setAttribute("attributeDetails", attributeDetails);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  298 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void getMGDetails(HttpServletRequest request)
/*      */   {
/*      */     try
/*      */     {
/*  306 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/*  307 */       String requestCondition = "&type=all";
/*      */       try
/*      */       {
/*  310 */         if (request.getParameter("groupId") != null)
/*      */         {
/*  312 */           String groupId = request.getParameter("groupId");
/*  313 */           Integer.parseInt(groupId);
/*  314 */           requestCondition = "&groupId=" + groupId;
/*      */         }
/*      */         else
/*      */         {
/*  318 */           return;
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  323 */         AMLog.debug("MOBILE UTILS: GetMGDetails: Not a valid groupId");
/*      */       }
/*      */       
/*  326 */       request.setAttribute("uri", "/AppManager/json/ListMGDetails?apikey=api_key" + requestCondition);
/*  327 */       String response = apiReqHandler.getRequestData(request);
/*  328 */       JSONObject jsonOutput = new JSONObject(response);
/*  329 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/*  331 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/*  332 */         if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */         {
/*  334 */           Properties monitorGroupDetails = new Properties();
/*  335 */           ArrayList<Properties> monitorDetails = new ArrayList();
/*  336 */           ArrayList<Properties> subGroupsDetails = new ArrayList();
/*  337 */           int monitorOutageCount = 0;int subGrpOutageCount = 0;
/*      */           
/*  339 */           JSONObject jsonMG = jsonResult.getJSONObject(0);
/*  340 */           String[] mgDetails = JSONObject.getNames(jsonMG);
/*  341 */           for (int i = 0; i < mgDetails.length; i++)
/*      */           {
/*  343 */             String key = mgDetails[i];
/*  344 */             String value = "";
/*  345 */             if (key != null)
/*      */             {
/*  347 */               value = jsonMG.optString(key);
/*  348 */               if (value.equals("No Data Available."))
/*      */               {
/*  350 */                 value = FormatUtil.getString("NA");
/*      */               }
/*  352 */               if (key.equals("Monitors"))
/*      */               {
/*  354 */                 JSONArray monitors = jsonMG.optJSONArray("Monitors");
/*  355 */                 if (monitors.length() > 0)
/*      */                 {
/*  357 */                   String[] monitorKeys = JSONObject.getNames(monitors.getJSONObject(0));
/*  358 */                   for (int k = 0; k < monitors.length(); k++)
/*      */                   {
/*  360 */                     JSONObject jsonMonitor = monitors.optJSONObject(k);
/*  361 */                     Properties monDetails = new Properties();
/*  362 */                     for (int l = 0; l < monitorKeys.length; l++)
/*      */                     {
/*  364 */                       String monitorkey = monitorKeys[l];
/*  365 */                       String monitorValue = "";
/*  366 */                       monitorValue = jsonMonitor.optString(monitorkey);
/*  367 */                       if ((monitorkey.startsWith("TODAY")) && (monitorkey.contains("PERCENT")))
/*      */                       {
/*  369 */                         monDetails.setProperty(monitorkey.replaceAll("TODAY", ""), getFormattedValue(monitorValue));
/*      */                       }
/*  371 */                       else if (monitorkey.contains("NAME"))
/*      */                       {
/*  373 */                         monDetails.setProperty("NAME", monitorValue.replaceAll("_", " _").replaceAll("-", "-"));
/*      */                       }
/*  375 */                       else if (monitorkey.toLowerCase().indexOf("message") > 0)
/*      */                       {
/*  377 */                         monDetails.setProperty(monitorkey, getFormattedMsg(monitorkey, monitorValue, false));
/*      */                       }
/*      */                       else
/*      */                       {
/*  381 */                         monDetails.setProperty(monitorkey, monitorValue);
/*      */                       }
/*  383 */                       if ((monitorkey.equals("HEALTHSEVERITY")) && ((monitorValue.equals("1")) || (monitorValue.equals("4"))))
/*      */                       {
/*  385 */                         monitorOutageCount++;
/*      */                       }
/*      */                       
/*  388 */                       if (monitorkey.equals("HEALTHSEVERITY"))
/*      */                       {
/*  390 */                         monDetails.setProperty("HEALTHICON", getSeverityIcon("health", monitorValue));
/*      */                       }
/*  392 */                       else if (monitorkey.equals("AVAILABILITYSEVERITY"))
/*      */                       {
/*  394 */                         monDetails.setProperty("AVAILABILITYICON", getSeverityIcon("availability", monitorValue));
/*      */                       }
/*      */                     }
/*  397 */                     monitorDetails.add(monDetails);
/*      */                   }
/*      */                 }
/*      */               }
/*  401 */               else if (key.equals("SubMonitorGroup"))
/*      */               {
/*  403 */                 JSONArray subGroups = jsonMG.optJSONArray("SubMonitorGroup");
/*  404 */                 if (subGroups.length() > 0)
/*      */                 {
/*  406 */                   String[] subGrpKeys = JSONObject.getNames(subGroups.getJSONObject(0));
/*  407 */                   for (int k = 0; k < subGroups.length(); k++)
/*      */                   {
/*  409 */                     JSONObject jsonSubGrp = subGroups.optJSONObject(k);
/*  410 */                     Properties subGrpDetails = new Properties();
/*  411 */                     for (int l = 0; l < subGrpKeys.length; l++)
/*      */                     {
/*      */ 
/*  414 */                       String subGrpkey = subGrpKeys[l];
/*  415 */                       String subGrpValue = "";
/*  416 */                       subGrpValue = jsonSubGrp.optString(subGrpkey);
/*  417 */                       if ((subGrpkey.startsWith("TODAY")) && (subGrpkey.contains("PERCENT")))
/*      */                       {
/*  419 */                         subGrpDetails.setProperty(subGrpkey.replaceAll("TODAY", ""), getFormattedValue(subGrpValue));
/*      */                       }
/*  421 */                       else if (subGrpkey.contains("NAME"))
/*      */                       {
/*  423 */                         subGrpDetails.setProperty("NAME", subGrpValue.replaceAll("_", " _").replaceAll("-", " -"));
/*      */                       }
/*  425 */                       else if (subGrpkey.toLowerCase().indexOf("message") > 0)
/*      */                       {
/*  427 */                         subGrpDetails.setProperty(subGrpkey, getFormattedMsg(subGrpkey, subGrpValue, false));
/*      */                       }
/*      */                       else
/*      */                       {
/*  431 */                         subGrpDetails.setProperty(subGrpkey, subGrpValue);
/*      */                       }
/*  433 */                       if ((subGrpkey.equals("HEALTHSEVERITY")) && ((subGrpValue.equals("1")) || (subGrpValue.equals("4"))))
/*      */                       {
/*  435 */                         subGrpOutageCount++;
/*      */                       }
/*      */                       
/*  438 */                       if (subGrpkey.equals("HEALTHSEVERITY"))
/*      */                       {
/*  440 */                         subGrpDetails.setProperty("HEALTHICON", getSeverityIcon("health", subGrpValue));
/*      */                       }
/*  442 */                       else if (subGrpkey.equals("AVAILABILITYSEVERITY"))
/*      */                       {
/*  444 */                         subGrpDetails.setProperty("AVAILABILITYICON", getSeverityIcon("availability", subGrpValue));
/*      */                       }
/*      */                     }
/*  447 */                     subGroupsDetails.add(subGrpDetails);
/*      */                   }
/*      */                 }
/*      */               }
/*  451 */               else if ((key.startsWith("TODAY")) && (key.contains("PERCENT")))
/*      */               {
/*  453 */                 monitorGroupDetails.setProperty(key.replaceAll("TODAY", ""), getFormattedValue(value));
/*      */               }
/*  455 */               else if (key.equalsIgnoreCase("healthmessage"))
/*      */               {
/*  457 */                 String rcaHealthMsg = "";
/*  458 */                 if ((value != null) && (value.contains("<br>")))
/*      */                 {
/*  460 */                   rcaHealthMsg = value.substring(value.indexOf("<br>")).replaceFirst("<br>", "");
/*  461 */                   rcaHealthMsg = rcaHealthMsg.substring(rcaHealthMsg.indexOf("<br>")).replaceFirst("<br>", "");
/*  462 */                   value = value.substring(0, value.indexOf("<br>"));
/*      */                 }
/*  464 */                 monitorGroupDetails.setProperty(key, value);
/*  465 */                 monitorGroupDetails.setProperty("HEALTHRCAMESSAGE", rcaHealthMsg);
/*      */               }
/*      */               else
/*      */               {
/*  469 */                 monitorGroupDetails.setProperty(key, FormatUtil.getString(value));
/*      */               }
/*      */               
/*  472 */               if (key.equals("HEALTHSEVERITY"))
/*      */               {
/*  474 */                 monitorGroupDetails.setProperty("HEALTHICON", getSeverityIcon("health", value));
/*      */               }
/*  476 */               else if (key.equals("AVAILABILITYSEVERITY"))
/*      */               {
/*  478 */                 monitorGroupDetails.setProperty("AVAILABILITYICON", getSeverityIcon("availability", value));
/*      */               }
/*      */             }
/*      */           }
/*  482 */           monitorGroupDetails.setProperty("monitorsCount", monitorOutageCount + "/" + monitorDetails.size());
/*  483 */           monitorGroupDetails.setProperty("subGrpsCount", subGrpOutageCount + "/" + subGroupsDetails.size());
/*      */           
/*  485 */           request.setAttribute("mgDetails", monitorGroupDetails);
/*  486 */           request.setAttribute("subGroups", subGroupsDetails);
/*  487 */           request.setAttribute("AssociatedMons", monitorDetails);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  493 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private static String getFormattedMsg(String msgType, String rcaMsg, boolean needNestedMsg)
/*      */   {
/*      */     try {
/*  500 */       if (rcaMsg != null)
/*      */       {
/*  502 */         rcaMsg = replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(rcaMsg, "<br>", "<newline>"), "</ ol>", "</ol>"), "Root Cause :", "RC:"), "</ li>", "</li>"), "Root Cause :", "RC:");
/*  503 */         if (msgType.toLowerCase().indexOf("health") >= 0)
/*      */         {
/*  505 */           rcaMsg = getPrimaryMsg(rcaMsg, needNestedMsg);
/*  506 */           String spacer = "";
/*  507 */           rcaMsg = replaceAll(replaceAll(rcaMsg, "</li>", spacer + spacer), "</ol>", spacer).replaceFirst("<ol>", spacer).replaceFirst("<li>", spacer + "  ").replaceFirst("RC:", spacer + "  Root Cause :");
/*  508 */           rcaMsg = replaceAll(replaceAll(rcaMsg, "</li>", ""), "</ol>", "");
/*  509 */           for (; (rcaMsg.contains("<ol>")) || (rcaMsg.contains("<li>")) || (rcaMsg.contains("RC:")); 
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  515 */               goto 270)
/*      */           {
/*  511 */             spacer = spacer + "   ";
/*  512 */             rcaMsg = rcaMsg.replaceFirst("RC:", spacer + spacer + "Root Cause :").replaceFirst("<ol>", "").replaceFirst("<li>", spacer + spacer);
/*  513 */             if ((rcaMsg.indexOf("<li>") < rcaMsg.indexOf("<ol>")) || ((rcaMsg.indexOf("<ol>") < 0) && (rcaMsg.indexOf("<li>") >= 0)))
/*      */             {
/*  515 */               rcaMsg = rcaMsg.replaceFirst("<li>", spacer + spacer);
/*      */             }
/*      */           }
/*      */         }
/*  519 */         rcaMsg = replaceAll(replaceAll(rcaMsg, "<newline><newline>", "<newline>"), "<newline>", "\\\\n").replaceFirst("RC:", "Root Cause :");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  523 */       e.printStackTrace();
/*  524 */       rcaMsg = rcaMsg.contains("<br>") ? rcaMsg.substring(0, rcaMsg.indexOf("<br>")) : rcaMsg;
/*      */     }
/*      */     
/*  527 */     return rcaMsg;
/*      */   }
/*      */   
/*      */ 
/*      */   private static String getPrimaryMsg(String rcaMsg, boolean needNestedMsg)
/*      */   {
/*  533 */     if ((!needNestedMsg) && (rcaMsg != null) && (rcaMsg.indexOf("<ol>") > 0))
/*      */     {
/*  535 */       String mainMsg = rcaMsg.substring(0, rcaMsg.indexOf("<ol>") + 4);
/*      */       try
/*      */       {
/*  538 */         String msgTobeParsed = rcaMsg.substring(rcaMsg.indexOf("<ol>") + 8, rcaMsg.lastIndexOf("</li></ol>"));
/*  539 */         msgTobeParsed = replaceAll(replaceAll(msgTobeParsed, "<ol><li>", "<remove>"), "</li></ol>", "</remove>") + "  ";
/*  540 */         while ((msgTobeParsed.contains("<remove>")) || (msgTobeParsed.contains("</remove>")))
/*      */         {
/*  542 */           if ((msgTobeParsed.contains("<remove>")) && (msgTobeParsed.contains("</remove>")))
/*      */           {
/*  544 */             msgTobeParsed = msgTobeParsed.substring(0, msgTobeParsed.indexOf("<remove>")) + msgTobeParsed.substring(msgTobeParsed.indexOf("</remove>") + 9);
/*      */           }
/*  546 */           else if (msgTobeParsed.contains("<remove>"))
/*      */           {
/*  548 */             msgTobeParsed = msgTobeParsed.substring(0, msgTobeParsed.indexOf("<remove>"));
/*      */           }
/*  550 */           else if (msgTobeParsed.contains("</remove>"))
/*      */           {
/*  552 */             msgTobeParsed = msgTobeParsed.substring(msgTobeParsed.indexOf("</remove>") + 9);
/*      */           }
/*  554 */           msgTobeParsed = msgTobeParsed.trim();
/*      */         }
/*  556 */         String[] msgArray = msgTobeParsed.split("<li>");
/*  557 */         for (int i = 0; i < msgArray.length; i++)
/*      */         {
/*  559 */           if (msgArray[i].indexOf("<newline>") >= 0)
/*      */           {
/*  561 */             msgArray[i] = msgArray[i].substring(0, msgArray[i].indexOf("<newline>"));
/*      */           }
/*  563 */           mainMsg = mainMsg + (msgArray[i].contains("RC:") ? "<newline><li>" + msgArray[i].substring(0, msgArray[i].indexOf("RC:")) + "</li>" : new StringBuilder().append("<newline><li>").append(msgArray[i]).append("</li>").toString());
/*      */         }
/*  565 */         mainMsg = mainMsg + "</ol>";
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  569 */         AMLog.debug("MobileUtils: getPrimaryMsg: " + e.getMessage());
/*  570 */         mainMsg = mainMsg.contains("<ol>") ? mainMsg.substring(0, mainMsg.indexOf("<ol>")) : mainMsg;
/*      */       }
/*      */       catch (OutOfMemoryError oome) {
/*  573 */         AMLog.debug("MobileUtils: getPrimaryMsg: OutOfMemoryError " + oome.getMessage());
/*  574 */         mainMsg = mainMsg.contains("<ol>") ? mainMsg.substring(0, mainMsg.indexOf("<ol>")) : mainMsg;
/*      */       }
/*  576 */       return mainMsg;
/*      */     }
/*  578 */     return rcaMsg;
/*      */   }
/*      */   
/*      */   public static String replaceAll(String source, String target, String replacement)
/*      */   {
/*  583 */     Pattern p = Pattern.compile(target);
/*  584 */     Matcher m = p.matcher(source);
/*      */     
/*  586 */     StringBuffer sb = new StringBuffer();
/*  587 */     while (m.find()) {
/*  588 */       m.appendReplacement(sb, replacement);
/*      */     }
/*  590 */     m.appendTail(sb);
/*  591 */     return sb.toString();
/*      */   }
/*      */   
/*      */   public static List<Properties> getAlarmsDetails(HttpServletRequest request)
/*      */   {
/*  596 */     ArrayList<Properties> alarmsList = new ArrayList();
/*      */     try
/*      */     {
/*  599 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/*  600 */       String resourceid = request.getParameter("resourceid");
/*  601 */       if (resourceid == null)
/*      */       {
/*  603 */         return alarmsList;
/*      */       }
/*      */       
/*  606 */       request.setAttribute("entity", resourceid);
/*  607 */       request.setAttribute("uri", "/AppManager/json/ListAlarms?apikey=api_key&resourceid=" + resourceid);
/*  608 */       String response = apiReqHandler.getRequestData(request);
/*      */       
/*  610 */       JSONObject jsonOutput = new JSONObject(response);
/*      */       
/*  612 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/*  614 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/*  615 */         if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */         {
/*  617 */           for (int i = 0; i < jsonResult.length(); i++)
/*      */           {
/*  619 */             Properties alarm = new Properties();
/*  620 */             JSONObject jsonAlarm = jsonResult.optJSONObject(i);
/*  621 */             if ((jsonAlarm != null) && (jsonAlarm.length() > 1))
/*      */             {
/*  623 */               String type = jsonAlarm.optString("TYPE");
/*  624 */               String resId = jsonAlarm.optString("RESOURCEID");
/*      */               
/*  626 */               alarm.setProperty("resourceid", resId);
/*  627 */               alarm.setProperty("severity", jsonAlarm.optString("HEALTHSEVERITY"));
/*  628 */               alarm.setProperty("status", FormatUtil.getString(jsonAlarm.optString("STATUS")));
/*  629 */               alarm.setProperty("displayname", jsonAlarm.optString("DISPLAYNAME"));
/*  630 */               alarm.setProperty("message", jsonAlarm.optString("MESSAGE"));
/*  631 */               alarm.setProperty("type", type);
/*  632 */               alarm.setProperty("detailsUrl", getDetailsUrl(type, resId));
/*  633 */               alarm.setProperty("healthicon", getSeverityIcon("health", jsonAlarm.optString("HEALTHSEVERITY")));
/*  634 */               alarm.setProperty("healthid", jsonAlarm.optString("ATTRIBUTEID"));
/*  635 */               alarm.setProperty("time", new Date(jsonAlarm.optLong("MODTIME")).toString());
/*  636 */               alarm.setProperty("annotation", jsonAlarm.optString("ANNOTATION"));
/*  637 */               alarm.setProperty("technician", FormatUtil.getString(jsonAlarm.optString("TECHNICIAN")));
/*  638 */               request.setAttribute("prop", alarm);
/*  639 */               alarmsList.add(alarm);
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  644 */               request.setAttribute("uri", "/AppManager/json/ListMonitor?apikey=api_key&resourceid=" + resourceid);
/*  645 */               response = apiReqHandler.getRequestData(request);
/*      */               
/*  647 */               jsonOutput = new JSONObject(response);
/*  648 */               if (jsonOutput.get("response-code").equals("4000"))
/*      */               {
/*  650 */                 jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/*  651 */                 if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */                 {
/*  653 */                   for (int j = 0; j < jsonResult.length(); j++)
/*      */                   {
/*  655 */                     jsonAlarm = jsonResult.optJSONObject(j);
/*  656 */                     if (jsonAlarm != null)
/*      */                     {
/*  658 */                       String type = jsonAlarm.optString("TYPE");
/*  659 */                       String resId = jsonAlarm.optString("RESOURCEID");
/*      */                       
/*  661 */                       alarm.setProperty("resourceid", resId);
/*  662 */                       alarm.setProperty("severity", jsonAlarm.optString("HEALTHSEVERITY"));
/*  663 */                       alarm.setProperty("status", FormatUtil.getString(jsonAlarm.optString("HEALTHSTATUS")));
/*  664 */                       alarm.setProperty("displayname", jsonAlarm.optString("DISPLAYNAME"));
/*  665 */                       alarm.setProperty("message", jsonAlarm.optString("HEALTHMESSAGE"));
/*  666 */                       alarm.setProperty("type", type);
/*  667 */                       alarm.setProperty("detailsUrl", getDetailsUrl(type, resId));
/*  668 */                       alarm.setProperty("healthicon", getSeverityIcon("health", jsonAlarm.optString("HEALTHSEVERITY")));
/*  669 */                       alarm.setProperty("healthid", jsonAlarm.optString("HEALTHATTRIBUTEID"));
/*  670 */                       alarm.setProperty("time", jsonAlarm.optString("LASTALARMTIME").equalsIgnoreCase("0") ? FormatUtil.getString("NA") : new Date(jsonAlarm.optLong("MODTIME")).toString());
/*  671 */                       alarm.setProperty("annotation", FormatUtil.getString("NO"));
/*  672 */                       alarm.setProperty("technician", FormatUtil.getString("None"));
/*  673 */                       request.setAttribute("prop", alarm);
/*  674 */                       alarmsList.add(alarm);
/*      */                     }
/*      */                     
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  687 */       e.printStackTrace();
/*      */     }
/*  689 */     return alarmsList;
/*      */   }
/*      */   
/*      */   private static String getDetailsUrl(String type, String resId)
/*      */   {
/*  694 */     if (type.equals("HAI"))
/*      */     {
/*  696 */       return "/mobile/DetailsView.do?method=showMGDetails&groupId=" + resId;
/*      */     }
/*  698 */     return "/mobile/DetailsView.do?method=showMonitorDetails&resourceid=" + resId;
/*      */   }
/*      */   
/*      */   public static void getActions(HttpServletRequest request) {
/*  702 */     ArrayList<Hashtable> actionTypesList = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/*  706 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/*  707 */       request.setAttribute("uri", "/AppManager/json/ListActions?apikey=api_key&type=all");
/*  708 */       String response = apiReqHandler.getRequestData(request);
/*      */       
/*  710 */       JSONObject jsonOutput = new JSONObject(response);
/*      */       
/*  712 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/*  714 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/*  715 */         if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */         {
/*  717 */           HashMap<String, HashMap<String, String>> actionPropsMap = new HashMap();
/*  718 */           for (int i = 0; i < jsonResult.length(); i++)
/*      */           {
/*  720 */             Hashtable actionType = new Hashtable();
/*  721 */             JSONObject jsonActionType = jsonResult.optJSONObject(i);
/*  722 */             String displayName = jsonActionType.optString("DisplayName");
/*  723 */             JSONArray actions = jsonActionType.optJSONArray("Action");
/*  724 */             String[] actionKeys = JSONObject.getNames(actions.optJSONObject(0));
/*  725 */             ArrayList<Properties> actionsList = new ArrayList();
/*  726 */             ArrayList<String> keys = new ArrayList(Arrays.asList(actionKeys));
/*  727 */             for (int j = 0; j < actions.length(); j++)
/*      */             {
/*      */ 
/*  730 */               Properties actionProps = new Properties();
/*  731 */               HashMap<String, String> props = new HashMap();
/*  732 */               JSONObject jsonAction = actions.optJSONObject(j);
/*  733 */               for (int l = 0; l < actionKeys.length; l++)
/*      */               {
/*  735 */                 String actionId = jsonAction.optString("ID");
/*  736 */                 String actionkey = actionKeys[l];
/*  737 */                 String actionValue = jsonAction.optString(actionkey);
/*  738 */                 if ((actionkey.equalsIgnoreCase("name")) || (actionkey.equalsIgnoreCase("id")) || (actionkey.equalsIgnoreCase("ExecuteActionPath")))
/*      */                 {
/*  740 */                   actionProps.setProperty(actionkey, actionValue);
/*  741 */                   if (actionkey.equalsIgnoreCase("ExecuteActionPath"))
/*      */                   {
/*  743 */                     boolean isCompleAction = true;
/*  744 */                     if (actionValue.contains("common"))
/*      */                     {
/*  746 */                       isCompleAction = false;
/*      */                     }
/*  748 */                     actionProps.setProperty("ComplexAction", isCompleAction + "");
/*      */                   }
/*      */                 }
/*  751 */                 else if (actionkey.equalsIgnoreCase("ActionProps"))
/*      */                 {
/*  753 */                   String[] actionPropsKeys = JSONObject.getNames(jsonAction.optJSONObject("ActionProps"));
/*  754 */                   for (int k = 0; k < actionPropsKeys.length; k++)
/*      */                   {
/*  756 */                     actionkey = actionPropsKeys[k];
/*  757 */                     actionValue = jsonAction.optJSONObject("ActionProps").optString(actionkey);
/*      */                     
/*      */ 
/*  760 */                     props.put(FormatUtil.getString("am.mobile.actions." + actionkey.replaceAll(" ", "_").toLowerCase() + ".txt"), actionValue);
/*      */                   }
/*  762 */                   actionPropsMap.put(actionId, props);
/*      */                 }
/*      */               }
/*  765 */               actionsList.add(actionProps);
/*      */             }
/*  767 */             actionType.put("DISPLAYNAME", displayName);
/*  768 */             actionType.put("ACTIONS", actionsList);
/*  769 */             actionType.put("ACTIONKEYS", keys);
/*  770 */             actionTypesList.add(actionType);
/*      */           }
/*  772 */           request.setAttribute("ActionsList", actionTypesList);
/*  773 */           request.setAttribute("ActionPropMap", actionPropsMap);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  779 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static List<Properties> getAlarmsList(HttpServletRequest request) {
/*  784 */     ArrayList<Properties> alarmsList = new ArrayList();
/*      */     try
/*      */     {
/*  787 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/*  788 */       String requestType = "all";
/*  789 */       String type = request.getParameter("type");
/*  790 */       if (type != null)
/*      */       {
/*  792 */         requestType = type;
/*      */       }
/*  794 */       else if (request.getAttribute("type") != null)
/*      */       {
/*  796 */         requestType = (String)request.getAttribute("type");
/*      */       }
/*  798 */       request.setAttribute("type", requestType);
/*  799 */       if (requestType.contains("_"))
/*      */       {
/*  801 */         requestType = requestType.replaceAll("_", ",");
/*      */       }
/*  803 */       request.setAttribute("uri", "/AppManager/json/ListAlarms?apikey=api_key&type=" + requestType);
/*  804 */       String response = apiReqHandler.getRequestData(request);
/*      */       
/*  806 */       JSONObject jsonOutput = new JSONObject(response);
/*      */       
/*  808 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/*  810 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/*  811 */         if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */         {
/*  813 */           for (int i = 0; i < jsonResult.length(); i++)
/*      */           {
/*  815 */             Properties alarm = new Properties();
/*  816 */             JSONObject jsonAlarm = jsonResult.optJSONObject(i);
/*  817 */             if ((jsonAlarm != null) && (jsonAlarm.length() > 1))
/*      */             {
/*  819 */               String montype = jsonAlarm.optString("TYPE");
/*  820 */               String resId = jsonAlarm.optString("RESOURCEID");
/*      */               
/*  822 */               alarm.setProperty("resourceid", resId);
/*  823 */               alarm.setProperty("healthseverity", jsonAlarm.optString("HEALTHSEVERITY"));
/*  824 */               alarm.setProperty("availseverity", jsonAlarm.optString("AVAILABILITYSEVERITY"));
/*  825 */               alarm.setProperty("status", jsonAlarm.optString("STATUS"));
/*  826 */               alarm.setProperty("displayname", jsonAlarm.optString("DISPLAYNAME"));
/*  827 */               alarm.setProperty("message", getFormattedMsg("HEALTHMESSAGE", jsonAlarm.optString("MESSAGE"), false));
/*  828 */               alarm.setProperty("shortmessage", jsonAlarm.optString("SHORTMESSAGE"));
/*  829 */               alarm.setProperty("modtime", FormatUtil.formatDT(jsonAlarm.optString("MODTIME")));
/*  830 */               alarm.setProperty("type", montype);
/*  831 */               alarm.setProperty("detailsUrl", getDetailsUrl(montype, resId));
/*  832 */               alarm.setProperty("healthicon", getSeverityIcon("health", jsonAlarm.optString("HEALTHSEVERITY")));
/*  833 */               alarm.setProperty("healthid", jsonAlarm.optString("ATTRIBUTEID"));
/*      */               
/*  835 */               alarmsList.add(alarm);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (JSONException jse)
/*      */     {
/*  843 */       String jsonErrMsg = jse.getMessage().toLowerCase();
/*  844 */       if ((jsonErrMsg != null) && (jsonErrMsg.contains("proxy configuration")))
/*      */       {
/*  846 */         request.setAttribute("ProxyError", FormatUtil.getString("am.mobile.proxyerror.txt"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  851 */       e.printStackTrace();
/*      */     }
/*  853 */     return alarmsList;
/*      */   }
/*      */   
/*      */   public static List<Properties> getMonitorGrpsList(HttpServletRequest request)
/*      */   {
/*  858 */     ArrayList<Properties> monitorGrps = new ArrayList();
/*      */     try
/*      */     {
/*  861 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/*  862 */       String requestCondition = "&type=all";
/*  863 */       if (request.getParameter("groupId") != null)
/*      */       {
/*  865 */         requestCondition = "&groupId=" + request.getParameter("groupId");
/*      */       }
/*  867 */       else if (request.getParameter("groupName") != null)
/*      */       {
/*  869 */         requestCondition = "&groupName=" + request.getParameter("groupName");
/*      */       }
/*  871 */       request.setAttribute("uri", "/AppManager/json/ListMonitorGroups?apikey=api_key" + requestCondition);
/*  872 */       String response = apiReqHandler.getRequestData(request);
/*  873 */       JSONObject jsonOutput = new JSONObject(response);
/*  874 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/*  876 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/*  877 */         if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */         {
/*  879 */           for (int i = 0; i < jsonResult.length(); i++)
/*      */           {
/*  881 */             Properties monitorGrp = new Properties();
/*  882 */             JSONObject jsonMgGroups = jsonResult.optJSONObject(i);
/*  883 */             if (jsonMgGroups != null)
/*      */             {
/*  885 */               monitorGrp.setProperty("RESOURCEID", jsonMgGroups.optString("RESOURCEID"));
/*  886 */               monitorGrp.setProperty("DISPLAYNAME", jsonMgGroups.optString("DISPLAYNAME").replaceAll("_", " _"));
/*  887 */               monitorGrp.setProperty("AVAILALERTMSG", getFormattedMsg("AVAILABILITYMESSAGE", jsonMgGroups.optString("AVAILABILITYMESSAGE"), false));
/*  888 */               monitorGrp.setProperty("HEALTHALERTMSG", getFormattedMsg("HEALTHMESSAGE", jsonMgGroups.optString("HEALTHMESSAGE"), false));
/*  889 */               monitorGrp.setProperty("AVAILABILITYSEVERITY", jsonMgGroups.optString("AVAILABILITYSEVERITY"));
/*  890 */               monitorGrp.setProperty("HEALTHSEVERITY", jsonMgGroups.optString("HEALTHSEVERITY"));
/*  891 */               monitorGrp.setProperty("HEALTHICON", getSeverityIcon("health", jsonMgGroups.optString("HEALTHSEVERITY")));
/*  892 */               monitorGrp.setProperty("UNAVAILPERCENT", getFormattedValue(jsonMgGroups.optString("TODAYUNAVAILPERCENT")));
/*  893 */               monitorGrp.setProperty("SCHEDDOWNPERCENT", getFormattedValue(jsonMgGroups.optString("TODAYSCHEDDOWNPERCENT")));
/*  894 */               monitorGrp.setProperty("UNMANGDPERCENT", getFormattedValue(jsonMgGroups.optString("TODAYUNMANGDPERCENT")));
/*  895 */               monitorGrp.setProperty("AVAILPERCENT", getFormattedValue(jsonMgGroups.optString("TODAYAVAILPERCENT")));
/*  896 */               monitorGrp.setProperty("OUTAGES", jsonMgGroups.optString("OUTAGES"));
/*      */               
/*  898 */               monitorGrps.add(monitorGrp);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  906 */       e.printStackTrace();
/*      */     }
/*  908 */     return monitorGrps;
/*      */   }
/*      */   
/*      */   public static String getFormattedValue(String percentVal) {
/*  912 */     if ((percentVal != null) && (percentVal.contains(".")) && (percentVal.length() - percentVal.indexOf(".") > 3))
/*      */     {
/*  914 */       percentVal = percentVal.substring(0, percentVal.indexOf(".") + 3);
/*  915 */       if (percentVal.equals("0.00"))
/*      */       {
/*  917 */         percentVal = "0.0";
/*      */       }
/*      */     }
/*  920 */     return percentVal;
/*      */   }
/*      */   
/*      */   public static List<Properties> getSearchResults(HttpServletRequest request)
/*      */   {
/*  925 */     ArrayList<Properties> monitors = new ArrayList();
/*      */     try
/*      */     {
/*  928 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/*  929 */       request.setAttribute("uri", "/AppManager/json/search?apikey=api_key&query=" + request.getParameter("searchTerm"));
/*  930 */       String response = apiReqHandler.getRequestData(request);
/*  931 */       JSONObject jsonOutput = new JSONObject(response);
/*  932 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/*  934 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/*  935 */         if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */         {
/*  937 */           request.setAttribute("Matches", Integer.valueOf(jsonResult.length()));
/*  938 */           for (int i = 0; i < jsonResult.length(); i++)
/*      */           {
/*  940 */             Properties monitorDetails = new Properties();
/*  941 */             JSONObject jsonType = jsonResult.optJSONObject(i);
/*  942 */             if (jsonType != null)
/*      */             {
/*  944 */               String type = jsonType.optString("Type");
/*  945 */               String resId = jsonType.optString("ResourceId");
/*      */               
/*  947 */               monitorDetails.setProperty("resourceid", resId);
/*  948 */               monitorDetails.setProperty("displayname", jsonType.optString("DisplayName"));
/*  949 */               monitorDetails.setProperty("imagepath", jsonType.optString("ImagePath"));
/*  950 */               monitorDetails.setProperty("type", type);
/*  951 */               monitorDetails.setProperty("detailsUrl", getDetailsUrl(type, resId));
/*  952 */               monitorDetails.setProperty("imgclass", getImagePathForType(jsonType.optString("SubGroup")));
/*  953 */               monitorDetails.setProperty("availalertmsg", jsonType.optString("AvailabilityMessage").replaceAll("<br>", "\\\\n"));
/*  954 */               monitorDetails.setProperty("healthalertmsg", jsonType.optString("HealthMessage").replaceAll("<br>", "\\\\n"));
/*  955 */               monitorDetails.setProperty("availicon", getSeverityIcon("availability", jsonType.optString("AvailabilitySeverity")));
/*  956 */               monitorDetails.setProperty("healthicon", getSeverityIcon("health", jsonType.optString("HealthSeverity")));
/*      */               
/*  958 */               monitors.add(monitorDetails);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  966 */       e.printStackTrace();
/*      */     }
/*  968 */     return monitors;
/*      */   }
/*      */   
/*      */   public static String getSeverityIcon(String type, String severity)
/*      */   {
/*  973 */     String imagePathClass = "";
/*  974 */     if (type.equalsIgnoreCase("availability"))
/*      */     {
/*      */ 
/*  977 */       if (severity.equals("5"))
/*      */       {
/*  979 */         imagePathClass = "availstatus5";
/*      */       }
/*  981 */       else if (severity.equals("1"))
/*      */       {
/*  983 */         imagePathClass = "availstatus1";
/*      */       }
/*      */       else
/*      */       {
/*  987 */         imagePathClass = "availstatus0";
/*      */       }
/*      */     }
/*  990 */     else if (type.equalsIgnoreCase("health"))
/*      */     {
/*      */ 
/*  993 */       if (severity.equals("1"))
/*      */       {
/*  995 */         imagePathClass = "healthstatus1";
/*      */       }
/*  997 */       else if (severity.equals("4"))
/*      */       {
/*  999 */         imagePathClass = "healthstatus4";
/*      */       }
/* 1001 */       else if (severity.equals("5"))
/*      */       {
/* 1003 */         imagePathClass = "healthstatus5";
/*      */       }
/*      */       else
/*      */       {
/* 1007 */         imagePathClass = "healthstatus0";
/*      */       }
/*      */     }
/* 1010 */     return imagePathClass;
/*      */   }
/*      */   
/*      */   public static List<Properties> getInfraDetails(HttpServletRequest request) {
/* 1014 */     ArrayList<Properties> monitors = new ArrayList();
/*      */     try
/*      */     {
/* 1017 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/* 1018 */       request.setAttribute("uri", "/AppManager/json/ListMonitorTypes?apikey=api_key&type=all");
/* 1019 */       String response = apiReqHandler.getRequestData(request);
/* 1020 */       JSONObject jsonOutput = new JSONObject(response);
/* 1021 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/* 1023 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/* 1024 */         if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */         {
/* 1026 */           for (int i = 0; i < jsonResult.length(); i++)
/*      */           {
/* 1028 */             Properties monitorDetails = new Properties();
/* 1029 */             JSONObject jsonType = jsonResult.optJSONObject(i);
/* 1030 */             if (jsonType != null)
/*      */             {
/* 1032 */               monitorDetails.setProperty("img", getImagePathForType(jsonType.optString("SUBGROUP")));
/* 1033 */               monitorDetails.setProperty("displayname", FormatUtil.getString(jsonType.optString("DISPLAYNAME")));
/* 1034 */               monitorDetails.setProperty("severityImg", getSeverityIcon("health", jsonType.optString("HEALTHSEVERITY")));
/* 1035 */               monitorDetails.setProperty("alertmsg", jsonType.optString("HEALTHMSG").replaceAll("<br>", "\\\\n"));
/* 1036 */               monitorDetails.setProperty("url", "/mobile/overview.do?method=ListMonitorsForType&type=" + jsonType.optString("SUBGROUP") + "&typeName=" + jsonType.optString("DISPLAYNAME"));
/* 1037 */               monitorDetails.setProperty("alarmurl", "/mobile/AlarmViewAction.do?method=listAlarms&type=" + jsonType.optString("SUBGROUP"));
/* 1038 */               String outages = jsonType.optString("OUTAGES").length() > 0 ? jsonType.optString("OUTAGES") : "0";
/* 1039 */               String count = "0";
/* 1040 */               if (outages.contains("/"))
/*      */               {
/* 1042 */                 String[] sarray = outages.split("/");
/* 1043 */                 outages = sarray[0];
/* 1044 */                 count = sarray[1];
/* 1045 */                 monitorDetails.setProperty("outages", outages);
/* 1046 */                 monitorDetails.setProperty("count", count);
/*      */               }
/*      */               
/* 1049 */               monitors.add(monitorDetails);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1057 */       e.printStackTrace();
/*      */     }
/* 1059 */     return monitors;
/*      */   }
/*      */   
/*      */   public static List<Properties> getMonitorsForType(HttpServletRequest request) {
/* 1063 */     ArrayList<Properties> monitors = new ArrayList();
/*      */     try
/*      */     {
/* 1066 */       String viewId = (String)request.getAttribute("viewId");
/* 1067 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/* 1068 */       String response = apiReqHandler.getRequestData(request);
/* 1069 */       JSONObject jsonOutput = new JSONObject(response);
/* 1070 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/* 1072 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/* 1073 */         if ((jsonResult != null) && (jsonResult.length() > 0))
/*      */         {
/* 1075 */           for (int i = 0; i < jsonResult.length(); i++)
/*      */           {
/* 1077 */             Properties monitorDetails = new Properties();
/* 1078 */             JSONObject jsonType = jsonResult.optJSONObject(i);
/* 1079 */             String type = jsonType.optString("TYPE");
/* 1080 */             String resId = jsonType.optString("RESOURCEID");
/*      */             
/* 1082 */             if (jsonType != null)
/*      */             {
/* 1084 */               monitorDetails.setProperty("RESOURCEID", resId);
/* 1085 */               monitorDetails.setProperty("DISPLAYNAME", jsonType.optString("DISPLAYNAME").replaceAll("_", " _"));
/* 1086 */               monitorDetails.setProperty("TYPE", type);
/* 1087 */               monitorDetails.setProperty("availalertmsg", jsonType.optString("AVAILABILITYMESSAGE").replaceAll("<br>", "\\\\n"));
/* 1088 */               monitorDetails.setProperty("healthalertmsg", jsonType.optString("HEALTHMESSAGE").replaceAll("<br>", "\\\\n"));
/* 1089 */               monitorDetails.setProperty("availicon", getSeverityIcon("availability", jsonType.optString("AVAILABILITYSEVERITY")));
/* 1090 */               monitorDetails.setProperty("healthicon", getSeverityIcon("health", jsonType.optString("HEALTHSEVERITY")));
/* 1091 */               monitorDetails.setProperty("url", getDetailsUrl(type, resId));
/* 1092 */               if ((viewId != null) && (viewId.equalsIgnoreCase("downDevicesViewAction")))
/*      */               {
/* 1094 */                 monitorDetails.setProperty("IMAGEPATH", getImagePathForType(jsonType.optString("TYPE")));
/* 1095 */                 monitorDetails.setProperty("modtime", FormatUtil.formatDT(jsonType.optString("LASTALARMTIME")));
/* 1096 */                 monitorDetails.setProperty("downPeriod", getdownTimePeriod(jsonType.optLong("LASTALARMTIME")));
/*      */               }
/* 1098 */               monitors.add(monitorDetails);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1106 */       e.printStackTrace();
/*      */     }
/* 1108 */     return monitors;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getdownTimePeriod(long optLong)
/*      */   {
/* 1114 */     String duration = "";
/*      */     try
/*      */     {
/* 1117 */       long diff = new Date().getTime() - new Date(optLong).getTime();
/* 1118 */       long days = diff / 86400000L;
/* 1119 */       long hrs = diff % 86400000L / 3600000L;
/* 1120 */       long mins = diff % 86400000L % 3600000L / 60000L;
/*      */       
/* 1122 */       if (days > 0L)
/*      */       {
/*      */ 
/* 1125 */         duration = days + "d";
/*      */       }
/* 1127 */       if ((hrs > 0L) && (mins > 0L))
/*      */       {
/* 1129 */         if (!duration.equals(""))
/*      */         {
/* 1131 */           duration = duration + ", ";
/*      */         }
/*      */         
/* 1134 */         duration = duration + hrs + "h and " + mins + "m";
/*      */       }
/* 1136 */       else if ((hrs > 0L) || (mins > 0L))
/*      */       {
/* 1138 */         if (!duration.equals(""))
/*      */         {
/* 1140 */           duration = duration + " and ";
/*      */         }
/* 1142 */         if (hrs > 0L)
/*      */         {
/*      */ 
/* 1145 */           duration = duration + hrs + "h ";
/*      */         }
/* 1147 */         if (mins > 0L)
/*      */         {
/*      */ 
/* 1150 */           duration = duration + mins + "m ";
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1155 */         long secs = diff % 86400000L % 3600000L % 60000L / 1000L;
/*      */         
/* 1157 */         duration = duration + secs + "s";
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1162 */       e.printStackTrace();
/*      */     }
/* 1164 */     return duration;
/*      */   }
/*      */   
/*      */ 
/*      */   public static void getHistoryDetails(HttpServletRequest request)
/*      */   {
/*      */     try
/*      */     {
/* 1172 */       ArrayList<Properties> rawdataDetails = new ArrayList();
/* 1173 */       ArrayList<Properties> dataList = new ArrayList();
/* 1174 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/* 1175 */       String period = request.getParameter("period");
/* 1176 */       if (period == null)
/*      */       {
/* 1178 */         period = "20";
/*      */       }
/* 1180 */       String haid = request.getParameter("haid") != null ? request.getParameter("haid") : request.getParameter("resourceid");
/* 1181 */       if (haid != null)
/*      */       {
/* 1183 */         request.setAttribute("haid", haid);
/*      */       }
/* 1185 */       request.setAttribute("uri", "/AppManager/json/showPolledData?apikey=api_key&resourceid=" + request.getParameter("resourceid") + "&period=" + period + "&attributeID=" + request.getParameter("attributeID"));
/* 1186 */       String response1 = apiReqHandler.getRequestData(request);
/* 1187 */       JSONObject jsonOutput = new JSONObject(response1);
/*      */       
/* 1189 */       if (jsonOutput.get("response-code").equals("4000"))
/*      */       {
/* 1191 */         JSONArray jsonResult = jsonOutput.getJSONObject("response").optJSONArray("result");
/* 1192 */         if ((jsonResult != null) && (jsonResult.length() == 1))
/*      */         {
/* 1194 */           JSONObject jsonMonitor = jsonResult.getJSONObject(0);
/* 1195 */           String[] historyDetails = JSONObject.getNames(jsonMonitor);
/* 1196 */           for (int i = 0; i < historyDetails.length; i++)
/*      */           {
/* 1198 */             String key = historyDetails[i];
/* 1199 */             String value = "";
/* 1200 */             if (key != null)
/*      */             {
/* 1202 */               value = jsonMonitor.optString(key);
/* 1203 */               if (value.equals("No Data Available."))
/*      */               {
/* 1205 */                 value = FormatUtil.getString("NA");
/*      */               }
/* 1207 */               if (key.equals("RawData"))
/*      */               {
/* 1209 */                 JSONArray rawdata = jsonMonitor.optJSONArray("RawData");
/* 1210 */                 for (int k = 0; k < rawdata.length(); k++)
/*      */                 {
/* 1212 */                   if (!rawdata.optJSONObject(k).optString("CollectionTime").equals(""))
/*      */                   {
/* 1214 */                     Properties rawDetails = new Properties();
/* 1215 */                     rawDetails.setProperty("COLLECTIONTIME", rawdata.optJSONObject(k).optString("CollectionTime"));
/* 1216 */                     rawDetails.setProperty("VALUE", rawdata.optJSONObject(k).optString("Value"));
/* 1217 */                     rawDetails.setProperty("DATETIME", rawdata.optJSONObject(k).optString("DateTime"));
/* 1218 */                     rawdataDetails.add(rawDetails);
/*      */                   }
/*      */                 }
/*      */               }
/* 1222 */               else if (key.equals("ArchiveData"))
/*      */               {
/* 1224 */                 JSONArray data = jsonMonitor.optJSONArray("ArchiveData");
/* 1225 */                 for (int k = 0; k < data.length(); k++)
/*      */                 {
/* 1227 */                   if (!data.optJSONObject(k).optString("AvgValue").equals(""))
/*      */                   {
/* 1229 */                     Properties dataDetails = new Properties();
/* 1230 */                     dataDetails.setProperty("AVGVALUE", data.optJSONObject(k).optString("AvgValue"));
/* 1231 */                     dataDetails.setProperty("ARCHIVEDTIME", data.optJSONObject(k).optString("ArchivedTime"));
/* 1232 */                     dataDetails.setProperty("MINVALUE", data.optJSONObject(k).optString("MinValue"));
/* 1233 */                     dataDetails.setProperty("MAXVALUE", data.optJSONObject(k).optString("MaxValue").trim());
/* 1234 */                     dataList.add(dataDetails);
/*      */                   }
/*      */                 }
/*      */               }
/* 1238 */               else if (key.equals("ThresholdDetails"))
/*      */               {
/* 1240 */                 AMLog.info("key:" + key + "  value:" + value);
/* 1241 */                 JSONArray thresholds = jsonMonitor.optJSONArray("ThresholdDetails");
/* 1242 */                 if (thresholds.length() > 0)
/*      */                 {
/* 1244 */                   ArrayList<String> thresholdDetails = new ArrayList();
/* 1245 */                   thresholdDetails.add(thresholds.optString(0));
/* 1246 */                   thresholdDetails.add(thresholds.optString(1));
/* 1247 */                   thresholdDetails.add(thresholds.optString(2));
/* 1248 */                   thresholdDetails.add(thresholds.optString(3));
/* 1249 */                   request.setAttribute(key, thresholdDetails);
/* 1250 */                   AMLog.info("key:" + key + "  value:" + thresholdDetails);
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/* 1255 */                 AMLog.info("key:" + key + "  value:" + value);
/* 1256 */                 request.setAttribute(key, value);
/*      */               }
/*      */             }
/*      */           }
/* 1260 */           AMLog.info("data  value  in  final:" + dataList);
/* 1261 */           AMLog.info("raw data  value  in  final:" + rawdataDetails);
/*      */           
/* 1263 */           request.setAttribute("data", dataList);
/* 1264 */           request.setAttribute("rawdata", rawdataDetails);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1270 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static String getImagePathForType(String subgroup)
/*      */   {
/* 1276 */     subgroup = subgroup.toLowerCase();
/* 1277 */     Pattern p = Pattern.compile("^(opmanager|opstor)-.*");
/* 1278 */     Matcher m = p.matcher(subgroup);
/* 1279 */     if (m.matches())
/*      */     {
/* 1281 */       subgroup = m.replaceAll("nwd");
/*      */     }
/* 1283 */     else if (subgroup.startsWith("windows"))
/*      */     {
/* 1285 */       subgroup = "windows";
/*      */     }
/*      */     else
/*      */     {
/* 1289 */       subgroup = subgroup.replaceAll("([\\s\\.\\/])", "");
/*      */     }
/* 1291 */     return "sprite-icon_" + subgroup;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\utils\MobileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */