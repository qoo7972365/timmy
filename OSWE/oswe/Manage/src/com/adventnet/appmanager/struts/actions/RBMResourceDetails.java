/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RBMResourceDetails
/*     */   extends DispatchAction
/*     */ {
/*  57 */   private static Log log = LogFactory.getLog("WebClient");
/*     */   
/*  59 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showRBMDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  67 */     response.setContentType("text/html; charset=UTF-8");
/*  68 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  69 */     AMActionForm am = (AMActionForm)form;
/*  70 */     ActionMessages messages = new ActionMessages();
/*  71 */     ActionErrors errors = new ActionErrors();
/*  72 */     ResultSet rset = null;
/*  73 */     String error = null;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  78 */       String webscript = request.getParameter("webscript");
/*  79 */       String qury = "select DISPLAYNAME from AM_RBMAGENTDATA where STATUS <> 0";
/*  80 */       rset = AMConnectionPool.executeQueryStmt(qury);
/*  81 */       ArrayList agtNotRun = new ArrayList();
/*  82 */       String id = "";
/*  83 */       while (rset.next())
/*     */       {
/*  85 */         id = rset.getString(1);
/*  86 */         agtNotRun.add("" + id);
/*     */       }
/*     */       
/*  89 */       request.setAttribute("downagents", agtNotRun);
/*  90 */       Properties prop = getResponsetimeForScript(request, webscript);
/*  91 */       request.setAttribute("responsetimeforscript", prop);
/*  92 */       ArrayList resIDs = new ArrayList();
/*  93 */       if (prop != null)
/*     */       {
/*  95 */         resIDs = (ArrayList)prop.get("resourceid");
/*     */       }
/*  97 */       AMConnectionPool.closeStatement(rset);
/*     */       
/*     */ 
/* 100 */       return new ActionForward("/jsp/rbmresourcedetails.jsp?test=test&webscript=" + webscript + "");
/*     */ 
/*     */     }
/*     */     catch (Exception es)
/*     */     {
/* 105 */       System.out.println("Exception found in rbmresourcesdetails java");
/* 106 */       es.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 110 */       if (rset != null)
/*     */       {
/*     */         try
/*     */         {
/* 114 */           rset.close();
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 121 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Properties getResponsetimeForScript(HttpServletRequest request, String webscript)
/*     */   {
/* 127 */     ResultSet rs = null;
/* 128 */     prop = new Properties();
/* 129 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 130 */     ArrayList resID = new ArrayList();
/* 131 */     ArrayList agents = new ArrayList();
/* 132 */     ArrayList responseTime = new ArrayList();
/* 133 */     Hashtable resIDVsResTime = new Hashtable();
/*     */     
/*     */     try
/*     */     {
/* 137 */       String con = "";
/* 138 */       if (request.isUserInRole("OPERATOR"))
/*     */       {
/* 140 */         String owner = request.getRemoteUser();
/* 141 */         con = " and " + ReportUtilities.getQueryCondition("a.RESOURCEID", owner);
/*     */       }
/* 143 */       String query = "select a.RESOURCEID,b.DISPLAYNAME,c.DISPLAYNAME from AM_RBMDATA a,AM_RBMAGENTDATA b,AM_ManagedObject c where a.AGENTID=b.AGENTID and a.SCRIPT='" + webscript + "'" + con + " and a.RESOURCEID=c.RESOURCEID order by a.AGENTID ";
/* 144 */       System.out.println("sdeKKKKKKK : " + query);
/* 145 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 146 */       String rsId = "";
/* 147 */       String agent = "";
/* 148 */       String rsIDStr = "";
/* 149 */       String maxColTimeStr = "";
/* 150 */       String sep = "";
/* 151 */       HashMap resIdVsDisplayName = new HashMap();
/* 152 */       while (rs.next())
/*     */       {
/* 154 */         rsId = "" + rs.getObject(1);
/* 155 */         agent = "" + rs.getString(2);
/* 156 */         resID.add(rsId);
/* 157 */         agents.add(agent);
/* 158 */         rsIDStr = rsIDStr + sep + rsId;
/* 159 */         sep = ",";
/* 160 */         resIdVsDisplayName.put(rsId, rs.getString(3));
/*     */       }
/*     */       
/* 163 */       String query1 = "SELECT max(COLLECTIONTIME) as ColTime FROM AM_ManagedObjectData WHERE RESID in (" + rsIDStr + ") group by RESID";
/*     */       
/*     */ 
/* 166 */       rs = AMConnectionPool.executeQueryStmt(query1);
/*     */       
/* 168 */       long collectiontime = 0L;
/* 169 */       sep = "";
/* 170 */       while (rs.next())
/*     */       {
/* 172 */         collectiontime = rs.getLong("ColTime");
/* 173 */         maxColTimeStr = maxColTimeStr + sep + collectiontime;
/* 174 */         sep = ",";
/*     */       }
/*     */       
/* 177 */       ArrayList urlid = new ArrayList();
/* 178 */       ArrayList responses = new ArrayList();
/* 179 */       Hashtable residVsResponse = new Hashtable();
/* 180 */       Hashtable residVsChildId = new Hashtable();
/*     */       
/* 182 */       String prevParentID = "";
/* 183 */       String parentID = "";
/* 184 */       String childID = "";
/* 185 */       prop.put("resourceid", resID);
/* 186 */       prop.put("agents", agents);
/* 187 */       prop.put("resIdVsDisplayName", resIdVsDisplayName);
/* 188 */       if (!maxColTimeStr.equals(""))
/*     */       {
/* 190 */         String query2 = "select RESID,RESPONSETIME from AM_ManagedObjectData where COLLECTIONTIME in (" + maxColTimeStr + ") AND RESID in (" + rsIDStr + ") order by RESPONSETIME,COLLECTIONTIME";
/* 191 */         System.out.println("query2 : " + query2);
/* 192 */         rs = AMConnectionPool.executeQueryStmt(query2);
/*     */         
/*     */ 
/* 195 */         long restime = 0L;
/* 196 */         while (rs.next())
/*     */         {
/* 198 */           rsId = "" + rs.getObject(1);
/* 199 */           restime = rs.getLong("RESPONSETIME");
/* 200 */           resIDVsResTime.put(rsId, Long.valueOf(restime));
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 205 */         prop.put("responsetime", resIDVsResTime);
/* 206 */         prop.put("maxresponsetime", "" + restime);
/*     */         
/* 208 */         query = "select apc.PARENTID,apc.CHILDID,amod.RESPONSETIME from AM_PARENTCHILDMAPPER apc, AM_ManagedObjectData amod where apc.childid=amod.resid and apc.parentid in (" + rsIDStr + ") and amod.COLLECTIONTIME in (" + maxColTimeStr + ") order by apc.parentid,apc.childid,amod.COLLECTIONTIME";
/* 209 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 215 */         while (rs.next())
/*     */         {
/* 217 */           parentID = rs.getString("PARENTID");
/* 218 */           childID = rs.getString("CHILDID");
/* 219 */           restime = rs.getLong("RESPONSETIME");
/* 220 */           residVsResponse.put(childID, Long.valueOf(restime));
/*     */         }
/*     */       }
/*     */       
/* 224 */       query = "select PARENTID,CHILDID from AM_PARENTCHILDMAPPER where PARENTID IN (" + rsIDStr + ") order by PARENTID,CHILDID";
/* 225 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 226 */       prevParentID = "";
/* 227 */       urlid.clear();
/* 228 */       urlid = new ArrayList();
/* 229 */       while (rs.next())
/*     */       {
/* 231 */         parentID = rs.getString("PARENTID");
/* 232 */         childID = rs.getString("CHILDID");
/* 233 */         if ((prevParentID.equals("")) || (prevParentID.equals(parentID)))
/*     */         {
/* 235 */           urlid.add("" + childID);
/* 236 */           prevParentID = parentID;
/*     */         }
/*     */         else
/*     */         {
/* 240 */           residVsChildId.put(prevParentID, urlid.clone());
/* 241 */           urlid.clear();
/* 242 */           urlid = new ArrayList();
/* 243 */           urlid.add("" + childID);
/* 244 */           prevParentID = parentID;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 249 */       if (!residVsChildId.containsKey(prevParentID))
/*     */       {
/* 251 */         residVsChildId.put(prevParentID, urlid.clone());
/*     */       }
/*     */       
/* 254 */       AMConnectionPool.closeStatement(rs);
/*     */       
/* 256 */       prop.put("childresponsetime", residVsResponse);
/* 257 */       prop.put("childid", residVsChildId);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 276 */       return prop;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 263 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 267 */       if (rs != null)
/*     */       {
/*     */         try
/*     */         {
/* 271 */           rs.close();
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward showRBMDashboard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 284 */       String con = "";
/* 285 */       String con1 = "";
/*     */       
/* 287 */       String it360SPCondition = "";
/* 288 */       String mspScriptQuery = "";
/* 289 */       if ((EnterpriseUtil.isIt360MSPEdition()) && (request.getSession().getAttribute("custProp") != null))
/*     */       {
/* 291 */         it360SPCondition = " and " + CustomerManagementAPI.getCondition("AM_ParentChildMapper.PARENTID", CustomerManagementAPI.filterResourceIds(request)) + " ";
/* 292 */         mspScriptQuery = "select DISTINCT(SCRIPT) from AM_RBMDATA join AM_ParentChildMapper on AM_RBMDATA.RESOURCEID = AM_ParentChildMapper.CHILDID " + it360SPCondition;
/*     */       }
/* 294 */       if (request.isUserInRole("OPERATOR"))
/*     */       {
/* 296 */         String owner = request.getRemoteUser();
/* 297 */         con = " and " + ReportUtilities.getQueryCondition("a.RESOURCEID", owner);
/* 298 */         con1 = " and " + ReportUtilities.getQueryCondition("AM_ManagedObject.RESOURCEID", owner);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 309 */       String argsTable = NewMonitorUtil.getArgsTableName("RBM");
/* 310 */       String qury = "select AM_PARENTCHILDMAPPER.CHILDID as RESOURCEID,a.DISPLAYNAME AS SCRIPT,b.AGENTNAME,b.AGENTID,b.DISPLAYNAME from " + argsTable + " as args,AM_ManagedObject a,AM_PARENTCHILDMAPPER,AM_RESOURCE_AGENT_MAPPING,AM_RBMAGENTDATA b where TYPE='RBM' and a.RESOURCEID=args.RESOURCEID and " + DBQueryUtil.getSpecialCharToAppend() + "isParent" + DBQueryUtil.getSpecialCharToAppend() + "='true' and AM_PARENTCHILDMAPPER.PARENTID=args.RESOURCEID and  AM_PARENTCHILDMAPPER.childid=AM_RESOURCE_AGENT_MAPPING.RESOURCEID and b.AGENTID=AM_RESOURCE_AGENT_MAPPING.AGENTID " + con;
/*     */       
/* 312 */       List rbmAgentData = this.mo.getRows(qury);
/* 313 */       request.setAttribute("rbmagenttable", rbmAgentData);
/* 314 */       qury = "select AM_ManagedObject.DISPLAYNAME as SCRIPT from " + argsTable + " as args,AM_ManagedObject where " + DBQueryUtil.getSpecialCharToAppend() + "isParent" + DBQueryUtil.getSpecialCharToAppend() + "='true' and args.RESOURCEID=AM_ManagedObject.RESOURCEID " + con1;
/* 315 */       if ((EnterpriseUtil.isIt360MSPEdition()) && (request.getSession().getAttribute("custProp") != null))
/*     */       {
/* 317 */         qury = mspScriptQuery;
/* 318 */         if (request.isUserInRole("OPERATOR"))
/*     */         {
/* 320 */           qury = qury + con1;
/*     */         }
/*     */       }
/*     */       
/* 324 */       List webscrs = this.mo.getRows(qury);
/* 325 */       request.setAttribute("rbmscripts", webscrs);
/* 326 */       qury = "select DISTINCT(AGENTNAME),DISPLAYNAME from AM_RBMAGENTDATA where AGENTID > " + EnterpriseUtil.getDistributedStartResourceId() + " and AGENTNAME NOT LIKE ('%(Local)')";
/* 327 */       List agts = this.mo.getRows(qury);
/* 328 */       request.setAttribute("availableagents", agts);
/* 329 */       qury = "select DISPLAYNAME from AM_RBMAGENTDATA where STATUS <> 0 and AGENTID > " + EnterpriseUtil.getDistributedStartResourceId() + " and AGENTNAME NOT LIKE ('%(Local)')";
/* 330 */       List downagts = this.mo.getRows(qury);
/* 331 */       request.setAttribute("downagents", downagts);
/*     */       
/* 333 */       request.setAttribute("parentVsChildMap", getParentVsChildMap(con));
/* 334 */       return new ActionForward("/jsp/rbmresources.jsp");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 338 */       e.printStackTrace(); }
/* 339 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   private HashMap<String, String> getParentVsChildMap(String condition)
/*     */   {
/* 345 */     HashMap<String, String> childVsParentIds = new HashMap();
/* 346 */     String qury = "select map.PARENTID,map.CHILDID,mo.displayname from AM_EUM_PARENTCHILD_MAPPING map,AM_ManagedObject mo where map.CHILDID IN (select a.RESOURCEID from AM_ManagedObject a where a.TYPE='RBM') and mo.RESOURCEID=map.PARENTID";
/* 347 */     AMLog.debug("RBMResourceDetails.getParentVsChildMap : " + qury);
/* 348 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 351 */       rs = AMConnectionPool.executeQueryStmt(qury);
/* 352 */       while (rs.next())
/*     */       {
/* 354 */         String parentId = rs.getString("PARENTID");
/* 355 */         String childId = rs.getString("CHILDID");
/* 356 */         childVsParentIds.put(childId, parentId);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 361 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 365 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 367 */     return childVsParentIds;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {}
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\RBMResourceDetails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */