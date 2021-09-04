/*     */ package com.adventnet.appmanager.utils.client;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.template.AMProcessMOUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.htmlparser.util.Translate;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcessAPIUtil
/*     */ {
/*     */   public static String addProcess(HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  27 */     int resID = -1;
/*  28 */     int processid = -1;
/*  29 */     if (request.getParameter("resourceid") != null) {
/*  30 */       if ((CommonAPIUtil.isOperatorRole(request)) && (!CommonAPIUtil.isAssociatedToOperator(request, request.getParameter("resourceid")))) {
/*  31 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*     */       }
/*     */     } else {
/*  34 */       AMLog.debug("Process Add REST API : Server resourceid not in the request");
/*  35 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/*     */     }
/*     */     try {
/*  38 */       resID = Integer.parseInt(request.getParameter("resourceid"));
/*     */     } catch (Exception e) {
/*  40 */       AMLog.debug("Process Add REST API : Improper resourceid in the request : " + request.getParameter("resourceid"));
/*  41 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/*     */     }
/*     */     
/*  44 */     if (request.getParameter("name") == null) {
/*  45 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.hostresourceconfig.processname.alert"), "4561");
/*     */     }
/*     */     try
/*     */     {
/*  49 */       String displayname = request.getParameter("displayname");
/*  50 */       String name = request.getParameter("name");
/*  51 */       String command = request.getParameter("command") != null ? request.getParameter("command") : "";
/*  52 */       String matchcriteria = "Contains";
/*  53 */       if (request.getParameter("matchcriteria") != null)
/*     */       {
/*  55 */         matchcriteria = request.getParameter("matchcriteria");
/*     */       }
/*  57 */       if ((displayname == null) || (displayname.trim().length() == 0)) {
/*  58 */         displayname = name;
/*     */       }
/*  60 */       processid = AMProcessMOUtil.createProcessMO(resID, name, command, 0, matchcriteria, displayname);
/*     */     } catch (Exception e) {
/*  62 */       e.printStackTrace();
/*     */     }
/*  64 */     String output = FormatUtil.getString("Unable able to add process");
/*  65 */     String status = "4562";
/*  66 */     if (processid != -1) {
/*  67 */       output = FormatUtil.getString("Process added successfully");
/*  68 */       request.setAttribute("resourceid", String.valueOf(processid));
/*  69 */       status = "4000";
/*     */     }
/*  71 */     return URITree.generateXML(request, response, output, status);
/*     */   }
/*     */   
/*     */   public static String addThresholdtoProcess(HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*     */     try
/*     */     {
/*  78 */       String processlist = request.getParameter("processmolist");
/*  79 */       String attributeParams = request.getParameter("attributethresholdmap");
/*  80 */       String decryptedValue = URLDecoder.decode(attributeParams, "UTF-8");
/*  81 */       JSONObject processThresholds = new JSONObject(decryptedValue);
/*     */       
/*  83 */       Map attributethresholdmap = new HashMap();
/*  84 */       Iterator<String> keys = processThresholds.keys();
/*     */       
/*  86 */       while (keys.hasNext()) {
/*  87 */         String attributeid = (String)keys.next();
/*     */         try {
/*  89 */           JSONObject thresholdParams = processThresholds.getJSONObject(attributeid);
/*     */           
/*  91 */           String thresholdid = null;
/*  92 */           String attributetype = null;
/*  93 */           String thresholdname = null;
/*  94 */           String attrid = null;
/*  95 */           String displayname = null;
/*     */           
/*  97 */           if ((thresholdParams.getString("thresholdid") != null) && (!"null".equalsIgnoreCase(thresholdParams.getString("thresholdid")))) {
/*  98 */             thresholdid = thresholdParams.getString("thresholdid");
/*     */           }
/*     */           
/* 101 */           if ((thresholdParams.getString("attributetype") != null) && (!"null".equalsIgnoreCase(thresholdParams.getString("attributetype")))) {
/* 102 */             attributetype = thresholdParams.getString("attributetype");
/*     */           }
/*     */           
/* 105 */           if ((thresholdParams.getString("thresholdname") != null) && (!"null".equalsIgnoreCase(thresholdParams.getString("thresholdname")))) {
/* 106 */             thresholdname = thresholdParams.getString("thresholdname");
/*     */           }
/*     */           
/* 109 */           if ((thresholdParams.getString("attributeid") != null) && (!"null".equalsIgnoreCase(thresholdParams.getString("attributeid")))) {
/* 110 */             attrid = thresholdParams.getString("attributeid");
/*     */           }
/*     */           
/* 113 */           if ((thresholdParams.getString("displayname") != null) && (!"null".equalsIgnoreCase(thresholdParams.getString("displayname")))) {
/* 114 */             displayname = thresholdParams.getString("displayname");
/*     */           }
/*     */           
/* 117 */           JSONArray clActions = thresholdParams.getJSONArray("clearactions");
/* 118 */           JSONArray cractions = thresholdParams.getJSONArray("criticalactions");
/* 119 */           JSONArray waactions = thresholdParams.getJSONArray("warningactions");
/*     */           
/* 121 */           ArrayList clearActions = new ArrayList();
/* 122 */           int clearSize = clActions.length();
/* 123 */           for (int i = 0; i < clearSize; i++) {
/* 124 */             String id = clActions.get(i) + "";
/* 125 */             clearActions.add(id);
/*     */           }
/*     */           
/* 128 */           ArrayList criticalActions = new ArrayList();
/* 129 */           int criticalSize = cractions.length();
/* 130 */           for (int i = 0; i < criticalSize; i++) {
/* 131 */             String id = cractions.get(i) + "";
/* 132 */             criticalActions.add(id);
/*     */           }
/*     */           
/* 135 */           ArrayList warningActions = new ArrayList();
/* 136 */           int warningSize = waactions.length();
/* 137 */           for (int i = 0; i < warningSize; i++) {
/* 138 */             String id = waactions.get(i) + "";
/* 139 */             warningActions.add(id);
/*     */           }
/* 141 */           HashMap thresholdDetails = new HashMap();
/* 142 */           thresholdDetails.put("thresholdid", thresholdid);
/* 143 */           thresholdDetails.put("attributetype", attributetype);
/* 144 */           thresholdDetails.put("thresholdname", thresholdname);
/* 145 */           thresholdDetails.put("attributeid", attrid);
/* 146 */           thresholdDetails.put("displayname", displayname);
/* 147 */           thresholdDetails.put("clearactions", clearActions);
/* 148 */           thresholdDetails.put("criticalactions", criticalActions);
/* 149 */           thresholdDetails.put("warningactions", warningActions);
/* 150 */           attributethresholdmap.put(attributeid, thresholdDetails);
/*     */         } catch (Exception ex) {
/* 152 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 159 */       String[] processIDS = processlist.split(",");
/* 160 */       for (String id : processIDS) {
/* 161 */         id = id.trim();
/* 162 */         int processID = -1;
/*     */         try {
/* 164 */           processID = Integer.valueOf(id).intValue();
/*     */         } catch (NumberFormatException num) {
/* 166 */           num.printStackTrace();
/*     */         }
/* 168 */         AMProcessMOUtil.associateThresholdToProcessMO(processID, attributethresholdmap, null);
/*     */       }
/*     */     } catch (Exception e) {
/* 171 */       e.printStackTrace();
/* 172 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.processtemplate.actions.failure.text"), "4000");
/*     */     }
/*     */     
/* 175 */     return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.processtemplate.actions.success.text"), "4000");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String editProcess(HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 183 */     String processIDs = request.getParameter("processid");
/* 184 */     String displayname = request.getParameter("displayname");
/* 185 */     String name = request.getParameter("name");
/* 186 */     String cmd = request.getParameter("command") == null ? "" : request.getParameter("command");
/* 187 */     String templatetype = request.getParameter("type");
/* 188 */     String matchcriteria = request.getParameter("matchcriteria");
/* 189 */     matchcriteria = (matchcriteria == null) || (matchcriteria.trim().length() == 0) ? "Contains" : matchcriteria;
/* 190 */     if ((processIDs == null) || (processIDs.trim().length() == 0))
/*     */     {
/* 192 */       String errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "processid" });
/* 193 */       return URITree.generateXML(request, response, errormsg, "4100");
/*     */     }
/* 195 */     if ((name == null) || (name.trim().length() == 0))
/*     */     {
/* 197 */       String errormsg = FormatUtil.getString("am.webclient,api.common.parameter.missing.text", new String[] { "name" });
/* 198 */       return URITree.generateXML(request, response, errormsg, "4100");
/*     */     }
/* 200 */     if ((templatetype == null) || (templatetype.trim().length() == 0))
/*     */     {
/* 202 */       String errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "type" });
/* 203 */       return URITree.generateXML(request, response, errormsg, "4100");
/*     */     }
/* 205 */     if ((!"1".equalsIgnoreCase(templatetype)) && (!"0".equalsIgnoreCase(templatetype)))
/*     */     {
/* 207 */       String errormsg = FormatUtil.getString("am.webclient.api.common.param.wrong.list", new String[] { "type", "1,4" });
/* 208 */       return URITree.generateXML(request, response, errormsg, "4102");
/*     */     }
/* 210 */     if ((displayname == null) || (displayname.trim().length() == 0))
/*     */     {
/* 212 */       displayname = name;
/*     */     }
/*     */     try
/*     */     {
/* 216 */       cmd = Translate.decode(cmd);
/* 217 */       if (AMProcessMOUtil.updateProcessMOFromAdmin(processIDs, templatetype, name, cmd, matchcriteria, displayname))
/*     */       {
/* 219 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.process.edit.success"), "4000");
/*     */       }
/*     */       
/*     */ 
/* 223 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 228 */       e.printStackTrace(); }
/* 229 */     return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/*     */   }
/*     */   
/*     */   public static String deleteProcess(HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 235 */     String processids = request.getParameter("processid");
/* 236 */     String monitorIds = request.getParameter("monitorid");
/* 237 */     if ((processids == null) || (processids.trim().length() == 0))
/*     */     {
/* 239 */       String errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "processid" });
/* 240 */       return URITree.generateXML(request, response, errormsg, "4100");
/*     */     }
/* 242 */     if ((monitorIds == null) || (monitorIds.trim().length() == 0))
/*     */     {
/* 244 */       String errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "monitorid" });
/* 245 */       return URITree.generateXML(request, response, errormsg, "4100");
/*     */     }
/*     */     try
/*     */     {
/* 249 */       HashSet<String> monitorset = new HashSet();
/* 250 */       HashSet<String> processidset = new HashSet();
/* 251 */       for (String processid : processids.split(","))
/*     */       {
/* 253 */         processidset.add(processid.trim());
/*     */       }
/* 255 */       for (String monitorid : monitorIds.split(","))
/*     */       {
/* 257 */         monitorset.add(monitorid.trim());
/*     */       }
/* 259 */       boolean isdeleted = AMProcessMOUtil.deleteProcessMO(processidset, monitorset);
/* 260 */       if (isdeleted)
/*     */       {
/* 262 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.process.delete.success"), "4000");
/*     */       }
/*     */       
/*     */ 
/* 266 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 271 */       e.printStackTrace(); }
/* 272 */     return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\ProcessAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */