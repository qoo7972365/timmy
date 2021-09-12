/*     */ package com.manageengine.appmanager.vservers.hyperv.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.manageengine.appmanager.vservers.hyperv.HyperVUtil;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ public class HypervActions extends DispatchAction
/*     */ {
/*     */   public ActionForward manageVMs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
/*     */   {
/*  33 */     ResultSet results = null;
/*     */     try
/*     */     {
/*  36 */       ActionMessages messages = new ActionMessages();
/*  37 */       String action = request.getParameter("action");
/*  38 */       request.setAttribute("action", action);
/*  39 */       String rowids = request.getParameter("resIds");
/*  40 */       Properties name_ids = new Properties();
/*     */       
/*     */ 
/*  43 */       String displayNames = "";
/*  44 */       StringTokenizer st = new StringTokenizer(rowids, ",");
/*  45 */       String parentID = "";
/*  46 */       if (st.hasMoreTokens())
/*     */       {
/*  48 */         int childID = Integer.parseInt(st.nextToken());
/*  49 */         parentID = com.adventnet.appmanager.util.DBUtil.getParentID(childID, "Hyper-V-Server");
/*     */       }
/*     */       
/*     */ 
/*  53 */       String query = "select RESOURCEID, RESOURCENAME from AM_ManagedObject where RESOURCEID in (" + rowids + ")";
/*     */       try
/*     */       {
/*  56 */         results = AMConnectionPool.executeQueryStmt(query);
/*     */         
/*     */ 
/*  59 */         while (results.next())
/*     */         {
/*  61 */           String guid = results.getString("RESOURCENAME");
/*  62 */           String id = results.getString("RESOURCEID");
/*  63 */           name_ids.setProperty(guid, id);
/*  64 */           if (displayNames.equals(""))
/*     */           {
/*  66 */             displayNames = guid;
/*     */           }
/*     */           else
/*     */           {
/*  70 */             displayNames = displayNames + "\" \"" + guid;
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception exc)
/*     */       {
/*  76 */         exc.printStackTrace();
/*     */       }
/*     */       finally {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  85 */       HyperVUtil util = new HyperVUtil();
/*  86 */       Properties loginProps = HyperVUtil.getHyperVLoginProps(parentID, "Hyper-V-Server");
/*  87 */       String commandToScript = "";
/*     */       
/*     */ 
/*  90 */       AMLog.debug("action==" + action + "==rowIds==" + rowids + "parentID==" + parentID);
/*     */       
/*  92 */       if ((displayNames != null) && (!displayNames.equalsIgnoreCase("")))
/*     */       {
/*     */ 
/*     */ 
/*  96 */         if (action.equalsIgnoreCase("start"))
/*     */         {
/*  98 */           commandToScript = "2\" \"" + displayNames;
/*     */ 
/*     */         }
/* 101 */         else if (action.equalsIgnoreCase("stop"))
/*     */         {
/* 103 */           commandToScript = "3\" \"" + displayNames;
/*     */         }
/* 105 */         else if (action.equalsIgnoreCase("restart"))
/*     */         {
/* 107 */           commandToScript = "10\" \"" + displayNames;
/*     */         }
/* 109 */         else if (action.equalsIgnoreCase("pause"))
/*     */         {
/* 111 */           commandToScript = "32768\" \"" + displayNames;
/*     */         }
/* 113 */         else if (action.equalsIgnoreCase("save"))
/*     */         {
/* 115 */           commandToScript = "32769\" \"" + displayNames;
/*     */         }
/*     */       }
/*     */       
/* 119 */       loginProps.setProperty("command", commandToScript);
/* 120 */       loginProps.setProperty("scriptfile", "hypervcontrollVM.vbs");
/* 121 */       HashMap actionProps = HyperVUtil.executeHyperVActions(loginProps);
/*     */       
/* 123 */       if (actionProps != null)
/*     */       {
/* 125 */         if (actionProps.get("Error") != null)
/*     */         {
/* 127 */           String ErrorMessage = (String)actionProps.get("Error");
/* 128 */           String setMessage = FormatUtil.getString("am.webclient.hyperv.actions.script.failed", new String[] { action, ErrorMessage });
/*     */           
/* 130 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/* 131 */           saveMessages(request, messages);
/*     */         }
/* 133 */         int hypervVMTypeId = NewMonitorUtil.getBaseId("HyperVVirtualMachine");
/*     */         
/* 135 */         Iterator it = actionProps.keySet().iterator();
/*     */         
/* 137 */         while (it.hasNext())
/*     */         {
/* 139 */           int Key = ((Integer)it.next()).intValue();
/*     */           
/* 141 */           Properties vmProps = (Properties)actionProps.get(Integer.valueOf(Key));
/* 142 */           String vmResourceName = vmProps.getProperty("VMNAME");
/* 143 */           String Guid = vmProps.getProperty("Name");
/* 144 */           String resid = name_ids.getProperty(Guid);
/*     */           
/* 146 */           String vmState = vmProps.getProperty("State");
/* 147 */           updateVMState(resid, vmState, hypervVMTypeId, "4236");
/*     */         }
/*     */       }
/* 150 */       request.setAttribute("actionProps", actionProps);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 154 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 158 */       AMConnectionPool.closeStatement(results);
/*     */     }
/*     */     
/*     */ 
/* 162 */     return mapping.findForward("success");
/*     */   }
/*     */   
/*     */   public ActionForward filterVMs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
/*     */   {
/*     */     try
/*     */     {
/* 169 */       ActionMessages messages = new ActionMessages();
/* 170 */       ArrayList<String> resourceIdsListToManage = new ArrayList();
/* 171 */       String setMessage = "";
/* 172 */       String unableToPerform = "";
/* 173 */       String action = request.getParameter("action");
/* 174 */       String rowids = request.getParameter("resIds");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 179 */       if ((request.getParameter("rowIds") != null) && (request.getParameter("action") != null) && (request.getParameter("rowIds").trim() != "") && (request.getParameter("action").trim() != ""))
/*     */       {
/*     */ 
/* 182 */         String hypervVMResourceIds = request.getParameter("rowIds");
/*     */         
/* 184 */         String actionState = "";
/* 185 */         if (request.getParameter("action").equalsIgnoreCase("stopVM"))
/*     */         {
/* 187 */           action = "Stop";
/* 188 */           actionState = "running";
/*     */         }
/* 190 */         else if (request.getParameter("action").equalsIgnoreCase("runVM"))
/*     */         {
/* 192 */           action = "Start";
/* 193 */           actionState = "stopped";
/*     */         }
/* 195 */         else if (request.getParameter("action").equalsIgnoreCase("restartVM"))
/*     */         {
/* 197 */           action = "restart";
/*     */         }
/* 199 */         else if (request.getParameter("action").equalsIgnoreCase("pauseVM"))
/*     */         {
/* 201 */           action = "pause";
/*     */         }
/* 203 */         else if (request.getParameter("action").equalsIgnoreCase("saveVM"))
/*     */         {
/* 205 */           action = "save";
/*     */         }
/* 207 */         String query = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where RESOURCEID in (" + hypervVMResourceIds + ")";
/* 208 */         StringTokenizer st = new StringTokenizer(hypervVMResourceIds, ",");
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 217 */         Hashtable<String, String> name_ids = new Hashtable();
/* 218 */         ResultSet results = null;
/* 219 */         ResultSet rsForTypeId = null;
/* 220 */         ResultSet rsForQuery = null;
/* 221 */         String toManage = "";
/*     */         
/*     */         try
/*     */         {
/* 225 */           results = AMConnectionPool.executeQueryStmt(query);
/* 226 */           int hypervVMTypeId = NewMonitorUtil.getBaseId("HyperVVirtualMachine");
/* 227 */           while (results.next())
/*     */           {
/* 229 */             String dispname = results.getString("DISPLAYNAME");
/* 230 */             String id = results.getString("RESOURCEID");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 259 */             if (toManage.length() > 0)
/*     */             {
/* 261 */               toManage = toManage + "," + id;
/*     */             }
/*     */             else
/*     */             {
/* 265 */               toManage = toManage + id;
/*     */             }
/* 267 */             resourceIdsListToManage.add(id);
/* 268 */             name_ids.put(id, dispname);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 275 */           request.setAttribute("resourceIdsToManage", toManage);
/* 276 */           request.setAttribute("nameids", name_ids);
/* 277 */           request.setAttribute("action", action);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/* 288 */             if (results != null)
/*     */             {
/* 290 */               AMConnectionPool.closeStatement(results);
/*     */             }
/*     */             
/* 293 */             if (rsForTypeId != null)
/*     */             {
/* 295 */               AMConnectionPool.closeStatement(rsForTypeId);
/*     */             }
/* 297 */             rsForQuery.close();
/*     */           }
/*     */           catch (Exception exc) {}
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 305 */           if (unableToPerform.trim().length() <= 0) {
/*     */             break label679;
/*     */           }
/*     */         }
/*     */         catch (Exception exception)
/*     */         {
/* 282 */           exception.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/*     */           try
/*     */           {
/* 288 */             if (results != null)
/*     */             {
/* 290 */               AMConnectionPool.closeStatement(results);
/*     */             }
/*     */             
/* 293 */             if (rsForTypeId != null)
/*     */             {
/* 295 */               AMConnectionPool.closeStatement(rsForTypeId);
/*     */             }
/* 297 */             rsForQuery.close();
/*     */           }
/*     */           catch (Exception exc) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 307 */         if (action.equalsIgnoreCase("start"))
/*     */         {
/*     */ 
/* 310 */           unableToPerform = FormatUtil.getString("am.hyperv.startvmfailed.text", new String[] { String.valueOf(unableToPerform), "Running" });
/*     */ 
/*     */         }
/* 313 */         else if (action.equalsIgnoreCase("stop"))
/*     */         {
/* 315 */           unableToPerform = FormatUtil.getString("am.hyperv.stopvmfailed.text", new String[] { String.valueOf(unableToPerform), "Turned Off/Suspended State" });
/*     */         }
/*     */         
/* 318 */         setMessage = unableToPerform;
/*     */         
/* 320 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/* 321 */         saveMessages(request, messages);
/*     */         break label737;
/* 323 */         label679: if (resourceIdsListToManage.size() <= 0)
/*     */         {
/* 325 */           setMessage = "<br>Unable to perform " + action + "  Operation for VMs";
/* 326 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/* 327 */           saveMessages(request, messages);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       label737:
/*     */       
/* 335 */       e.printStackTrace();
/*     */     }
/* 337 */     return mapping.findForward("success");
/*     */   }
/*     */   
/*     */   private static String getMaxCollectionTimeForAttributeId(String attributeID, String resourceID, int typeID) {
/* 341 */     String collectionTime = "";
/* 342 */     ResultSet rsForQuery = null;
/*     */     
/* 344 */     ArrayList<String> tables = ConfMonitorUtil.getInstance().getCurrentTable("HyperVVirtualMachine", null);
/* 345 */     String collectionTimeQuery = "select max(COLLECTIONTIME) as COLLECTIONTIME from " + (String)tables.get(0) + " where RESOURCEID = " + resourceID;
/*     */     try
/*     */     {
/* 348 */       rsForQuery = AMConnectionPool.executeQueryStmt(collectionTimeQuery);
/* 349 */       if (rsForQuery.next())
/*     */       {
/* 351 */         collectionTime = rsForQuery.getString("COLLECTIONTIME");
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 356 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 360 */       AMConnectionPool.closeStatement(rsForQuery);
/*     */     }
/* 362 */     return collectionTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void updateVMState(String resid, String vmState, int typeId, String attributeId)
/*     */   {
/*     */     try
/*     */     {
/* 374 */       HashMap configMap = new HashMap();
/* 375 */       configMap.put(attributeId, vmState);
/* 376 */       NewMonitorUtil.insertConfigurationDetails(configMap, Integer.parseInt(resid), System.currentTimeMillis(), false);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 380 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\vservers\hyperv\actions\HypervActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */