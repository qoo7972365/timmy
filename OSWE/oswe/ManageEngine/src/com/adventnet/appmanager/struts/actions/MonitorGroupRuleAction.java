/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.manageengine.appmanager.util.RuleManagerUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.apache.struts.util.LabelValueBean;
/*     */ import org.apache.struts.validator.DynaValidatorForm;
/*     */ import org.htmlparser.util.Translate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MonitorGroupRuleAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward showMonitorGroupRules(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  42 */     ArrayList<Properties> list = RuleManagerUtil.getInstance().getMonitorGroupRuleList();
/*  43 */     request.setAttribute("RuleList", list);
/*  44 */     return mapping.findForward("RuleManagerPage");
/*     */   }
/*     */   
/*     */   private static String getManagedServerNameWithPort(String resourceId)
/*     */   {
/*  49 */     return EnterpriseUtil.getManagedServerNameWithPort(resourceId);
/*     */   }
/*     */   
/*     */   public ActionForward createRule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  54 */     DynaValidatorForm ruleForm = (DynaValidatorForm)form;
/*  55 */     ArrayList<LabelValueBean> monitorTypeList = new ArrayList();
/*  56 */     monitorTypeList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.selectMonitorType"), "-1"));
/*  57 */     HashMap confMonitorsList = ConfMonitorConfiguration.getInstance().getConfMonitorsByCategory();
/*  58 */     String resQuery = "select RESOURCETYPE,DISPLAYNAME from AM_ManagedResourceType WHERE RESOURCETYPE NOT IN ('Node', 'HAI', 'snmp-node')";
/*  59 */     ResultSet rs = null;
/*     */     try
/*     */     {
/*  62 */       rs = AMConnectionPool.executeQueryStmt(resQuery);
/*  63 */       while (rs.next())
/*     */       {
/*  65 */         String propKey = rs.getString(2);
/*  66 */         String value = rs.getString(1);
/*  67 */         monitorTypeList.add(new LabelValueBean(propKey, value));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  72 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*  76 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     
/*  79 */     ArrayList<LabelValueBean> monitorGroupList = new ArrayList();
/*  80 */     monitorGroupList.add(new LabelValueBean(FormatUtil.getString("am.reporttab.selectmg.text"), "-1"));
/*  81 */     boolean isPriviledgedUser = Constants.isPrivilegedUser(request);
/*  82 */     String owner = request.getRemoteUser();
/*     */     try {
/*     */       String query;
/*     */       String query;
/*  86 */       if (isPriviledgedUser) {
/*     */         String query;
/*  88 */         if (Constants.subGroupsEnabled.equals("true"))
/*     */         {
/*  90 */           query = "select RESOURCENAME,RESOURCEID,DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + owner + "' order by RESOURCEID,RESOURCENAME";
/*     */         }
/*     */         else
/*     */         {
/*  94 */           query = "select RESOURCENAME,RESOURCEID,DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=0 and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + owner + "' order by RESOURCEID";
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*  99 */       else if (Constants.subGroupsEnabled.equals("true"))
/*     */       {
/* 101 */         String query = "select RESOURCENAME,RESOURCEID,PARENTID,DISPLAYNAME from AM_HOLISTICAPPLICATION left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID order by PARENTID,RESOURCENAME";
/* 102 */         if (DBQueryUtil.isPgsql())
/*     */         {
/* 104 */           query = "SELECT RESOURCENAME , RESOURCEID , PARENTID , DISPLAYNAME FROM AM_HOLISTICAPPLICATION LEFT OUTER JOIN AM_ManagedObject ON AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID LEFT OUTER JOIN AM_PARENTCHILDMAPPER ON AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID ORDER BY PARENTID NULLS FIRST , RESOURCENAME";
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 109 */         query = "select AM_ManagedObject.RESOURCENAME , AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_ManagedObject , AM_HOLISTICAPPLICATION where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=0 ORDER BY RESOURCEID";
/*     */       }
/*     */       
/* 112 */       boolean isMonitors = false;
/* 113 */       if (Constants.subGroupsEnabled.equals("true"))
/*     */       {
/* 115 */         ArrayList completeList = new ArrayList();
/* 116 */         if (isPriviledgedUser)
/*     */         {
/* 118 */           ArrayList rows1 = new ArrayList();
/* 119 */           ArrayList groupsList = DBUtil.getRows("select RESOURCENAME,RESOURCEID,PARENTID,DISPLAYNAME from AM_HOLISTICAPPLICATION left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID order by PARENTID,RESOURCENAME");
/* 120 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 121 */           while (rs.next())
/*     */           {
/* 123 */             ArrayList tempList = new ArrayList();
/* 124 */             tempList.add(rs.getString(1));
/* 125 */             tempList.add(rs.getString(2));
/* 126 */             tempList.add(rs.getString(3));
/* 127 */             rows1.add(tempList);
/*     */           }
/* 129 */           rows1 = ReportUtil.getGroupOrigin(groupsList, rows1);
/* 130 */           for (int i = 0; i < rows1.size(); i++)
/*     */           {
/* 132 */             ArrayList InsideRows = (ArrayList)rows1.get(i);
/* 133 */             int resourceId = Integer.parseInt((String)InsideRows.get(1));
/* 134 */             if (resourceId < EnterpriseUtil.RANGE)
/*     */             {
/* 136 */               monitorGroupList.add(new LabelValueBean((String)InsideRows.get(0), (String)InsideRows.get(1)));
/*     */             }
/*     */           }
/* 139 */           AMConnectionPool.closeStatement(rs);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 144 */           completeList = DBUtil.getRows(query);
/* 145 */           if (!EnterpriseUtil.isIt360MSPEdition())
/*     */           {
/* 147 */             completeList = ReportUtil.getDifferntiatedList(completeList);
/*     */           }
/* 149 */           for (int i = 0; i < completeList.size(); i++)
/*     */           {
/* 151 */             isMonitors = true;
/* 152 */             ArrayList InsideRows = (ArrayList)completeList.get(i);
/* 153 */             int resourceId = Integer.parseInt((String)InsideRows.get(1));
/* 154 */             if (resourceId < EnterpriseUtil.RANGE)
/*     */             {
/* 156 */               monitorGroupList.add(new LabelValueBean((String)InsideRows.get(0), (String)InsideRows.get(1)));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 163 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 164 */         while (rs.next())
/*     */         {
/* 166 */           isMonitors = true;
/* 167 */           int resourceId = rs.getInt(2);
/* 168 */           if (resourceId < EnterpriseUtil.RANGE)
/*     */           {
/* 170 */             monitorGroupList.add(new LabelValueBean(rs.getString(3), String.valueOf(resourceId)));
/*     */           }
/*     */         }
/* 173 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 178 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 182 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 184 */     ArrayList<LabelValueBean> overallConditionList = new ArrayList();
/* 185 */     overallConditionList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.and"), "&&"));
/* 186 */     overallConditionList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.or"), "||"));
/* 187 */     ArrayList<LabelValueBean> nameFieldList = new ArrayList();
/* 188 */     nameFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.name"), "Name"));
/* 189 */     nameFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.type"), "Type"));
/* 190 */     ArrayList<LabelValueBean> conditionFieldList = new ArrayList();
/* 191 */     conditionFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.equals"), "1"));
/* 192 */     conditionFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.notEquals"), "2"));
/* 193 */     conditionFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.contains"), "3"));
/* 194 */     conditionFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.startsWith"), "4"));
/* 195 */     conditionFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.endsWith"), "5"));
/* 196 */     conditionFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.notContains"), "6"));
/* 197 */     conditionFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.notStarts"), "7"));
/* 198 */     conditionFieldList.add(new LabelValueBean(FormatUtil.getString("am.webclient.ruleMgr.condition.notEnds"), "8"));
/* 199 */     request.setAttribute("monitorGroupList", monitorGroupList);
/* 200 */     request.setAttribute("OverallConditionList", overallConditionList);
/* 201 */     request.setAttribute("NameFieldList", nameFieldList);
/* 202 */     request.setAttribute("ConditionFieldList", conditionFieldList);
/* 203 */     request.setAttribute("monitorTypeList", monitorTypeList);
/*     */     
/* 205 */     String ruleId = request.getParameter("ruleId");
/* 206 */     if ((ruleId != null) && (!ruleId.equals("")))
/*     */     {
/* 208 */       HashMap<String, String> ruleDetails = RuleManagerUtil.getInstance().getRuleDetails(ruleId);
/* 209 */       String ruleName = (String)ruleDetails.get("Name");
/* 210 */       String description = (String)ruleDetails.get("Description");
/* 211 */       String haid = (String)ruleDetails.get("Haid");
/* 212 */       ruleForm.set("ruleName", ruleName);
/* 213 */       ruleForm.set("description", description);
/* 214 */       ruleForm.set("monitorGroupHaid", haid);
/* 215 */       String condition = (String)ruleDetails.get("Condition");
/* 216 */       ArrayList<HashMap<String, String>> conditionList = new ArrayList();
/* 217 */       if ((condition != null) && (!condition.equals("")))
/*     */       {
/* 219 */         JsonParser parser = new JsonParser();
/* 220 */         JsonElement elem = parser.parse(condition);
/* 221 */         JsonArray jsArr = elem.getAsJsonArray();
/* 222 */         int size = jsArr.size();
/* 223 */         for (int i = 0; i < size; i++)
/*     */         {
/* 225 */           HashMap<String, String> map = new HashMap();
/* 226 */           JsonObject criteriaObj = jsArr.get(i).getAsJsonObject();
/* 227 */           map.put("NameField", criteriaObj.get("Field").getAsString());
/* 228 */           map.put("conditionField", criteriaObj.get("Condition").getAsString());
/* 229 */           map.put("valueField", criteriaObj.get("Value").getAsString());
/* 230 */           map.put("OverallCondition", criteriaObj.get("OverallCondition").getAsString());
/* 231 */           conditionList.add(map);
/*     */         }
/*     */       }
/* 234 */       request.setAttribute("ruleName", ruleName);
/* 235 */       request.setAttribute("description", description);
/* 236 */       request.setAttribute("monitorGroupHaid", haid);
/* 237 */       request.setAttribute("condition", condition);
/* 238 */       request.setAttribute("criteriaList", conditionList);
/*     */     }
/* 240 */     request.setAttribute("RuleID", ruleId);
/* 241 */     return mapping.findForward("AddRulePage");
/*     */   }
/*     */   
/*     */   public ActionForward saveRule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 246 */     DynaValidatorForm ruleForm = (DynaValidatorForm)form;
/* 247 */     String ruleId = request.getParameter("ruleId");
/* 248 */     String condition = Translate.decode(request.getParameter("condition"));
/* 249 */     String ruleName = (String)ruleForm.get("ruleName");
/* 250 */     String description = (String)ruleForm.get("description");
/* 251 */     String monitorGroupHaid = (String)ruleForm.get("monitorGroupHaid");
/* 252 */     HashMap<String, String> map = new HashMap();
/* 253 */     map.put("Name", ruleName);
/* 254 */     map.put("Condition", condition);
/* 255 */     map.put("Description", description);
/* 256 */     map.put("Haid", monitorGroupHaid);
/* 257 */     String message = null;
/* 258 */     boolean update = false;
/*     */     try
/*     */     {
/* 261 */       if ((ruleId != null) && (!ruleId.equals("")))
/*     */       {
/* 263 */         update = true;
/* 264 */         map.put("RuleID", ruleId);
/* 265 */         RuleManagerUtil.getInstance().updateRule(map);
/* 266 */         message = "&successMsg=am.webclient.rule.created.success";
/*     */       }
/*     */       else
/*     */       {
/* 270 */         int id = RuleManagerUtil.getInstance().addRule(map);
/* 271 */         ruleId = String.valueOf(id);
/* 272 */         message = "&successMsg=am.webclient.rule.updated.success";
/*     */       }
/*     */     }
/*     */     catch (SQLException sqe)
/*     */     {
/* 277 */       sqe.printStackTrace();
/* 278 */       message = "&failureMsg=am.webclient.rule." + (update ? "created" : "updated") + ".failed";
/*     */     }
/* 280 */     return new ActionForward("/monitorGrpRule.do?method=createRule&ruleId=" + ruleId + "&saved=true", true);
/*     */   }
/*     */   
/*     */   public ActionForward deleteRule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 285 */     String ruleId = request.getParameter("ruleId");
/* 286 */     if ((ruleId != null) && (!ruleId.equals("")))
/*     */     {
/*     */       try
/*     */       {
/* 290 */         RuleManagerUtil.getInstance().deleteRule(ruleId);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 294 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 297 */     return new ActionForward("/monitorGrpRule.do?method=showMonitorGroupRules", true);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\MonitorGroupRuleAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */