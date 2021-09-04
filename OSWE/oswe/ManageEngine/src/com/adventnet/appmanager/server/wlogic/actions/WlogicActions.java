/*      */ package com.adventnet.appmanager.server.wlogic.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.ScheduleWebLogicDataCollection;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WlogicActions
/*      */   extends DispatchAction
/*      */ {
/*   46 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */   public ActionForward getWebAppData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*   49 */     ActionMessages messages = new ActionMessages();
/*   50 */     ActionErrors errors = new ActionErrors();
/*   51 */     ResultSet set = null;
/*   52 */     request.removeAttribute("data");
/*   53 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*      */     
/*   55 */     Properties status = getStatus(request.getParameter("resourceid"));
/*   56 */     request.setAttribute("currentstatus", status);
/*   57 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*   58 */     long collectionTime = 0L;
/*      */     try
/*      */     {
/*   61 */       String query = "select max(COLLECTIONTIME) from AM_WLS_WebAppData,AM_WAR,AM_EAR where AM_WAR.WARID=AM_WLS_WebAppData.WEBAPPID and AM_WAR.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + resID;
/*   62 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*   64 */       if (set.next())
/*      */       {
/*   66 */         collectionTime = set.getLong(1);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*   73 */       closeResultSet(set);
/*      */     }
/*      */     
/*   76 */     if (collectionTime == 0L)
/*      */     {
/*      */ 
/*      */ 
/*   80 */       return null;
/*      */     }
/*      */     
/*   83 */     if (collectionTime == -1L)
/*      */     {
/*   85 */       request.setAttribute("note", "1");
/*   86 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("wlogic.nodata"));
/*   87 */       saveMessages(request, messages);
/*   88 */       return mapping.getInputForward();
/*      */     }
/*      */     try
/*      */     {
/*   92 */       ArrayList data = new ArrayList();
/*   93 */       query = "select AM_ManagedObject.DISPLAYNAME,OPENSESSIONCURRENTCOUNT,OPENSESSIONHIGHCOUNT,OPENSESSIONTOTALCOUNT,SERVLETSCOUNT,WEBAPPID,-1 from AM_WLS_WebAppData,AM_ManagedObject,AM_WAR,AM_EAR where AM_WLS_WebAppData.COLLECTIONTIME =" + collectionTime + " and AM_WLS_WebAppData.WEBAPPID=AM_WAR.WARID and AM_WAR.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + resID + " and AM_WAR.WARID=AM_ManagedObject.RESOURCEID";
/*      */       
/*   95 */       set = AMConnectionPool.executeQueryStmt((String)query);
/*   96 */       Properties props; while (set.next())
/*      */       {
/*   98 */         props = new Properties();
/*   99 */         props.setProperty("Name", set.getString(1));
/*  100 */         props.setProperty("currentCount", String.valueOf(set.getInt(2)));
/*  101 */         props.setProperty("highCount", String.valueOf(set.getInt(3)));
/*  102 */         props.setProperty("totalCount", String.valueOf(set.getInt(4)));
/*  103 */         props.setProperty("servletsCount", String.valueOf(set.getInt(5)));
/*  104 */         props.setProperty("webappid", String.valueOf(set.getInt(6)));
/*  105 */         props.setProperty("health", String.valueOf(set.getInt(7)));
/*  106 */         data.add(props);
/*      */       }
/*  108 */       if (data.size() != 0) {
/*  109 */         request.setAttribute("data", data);
/*      */       } else {
/*  111 */         request.removeAttribute("data");
/*      */       }
/*  113 */       request.setAttribute("note", "1");
/*  114 */       return returnForward(mapping.findForward("wlogic.success"), request);
/*      */     } catch (Exception exp) {
/*      */       Object query;
/*  117 */       exp.printStackTrace();
/*  118 */       request.setAttribute("note", "1");
/*  119 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exp.toString()));
/*  120 */       saveErrors(request, errors);
/*  121 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/*  125 */       closeResultSet(set);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getWebAppServletsData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  131 */     ActionMessages messages = new ActionMessages();
/*  132 */     this.mo.getReloadPeriod(request);
/*  133 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(request.getParameter("resourceid"), Long.parseLong((String)request.getAttribute("reloadperiod")));
/*  134 */     if (map != null)
/*      */     {
/*  136 */       request.setAttribute("systeminfo", map);
/*      */     }
/*  138 */     Properties status = getStatus(request.getParameter("resourceid"));
/*  139 */     request.setAttribute("currentstatus", status);
/*  140 */     ActionErrors errors = new ActionErrors();
/*  141 */     ResultSet set = null;
/*  142 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*      */     
/*  144 */     int webAppID = Integer.parseInt(request.getParameter("webAppID"));
/*  145 */     String webAppName = request.getParameter("warName");
/*  146 */     String selectedscheme = "";
/*  147 */     if (request.getParameter("selectedscheme") != null)
/*      */     {
/*  149 */       selectedscheme = request.getParameter("selectedscheme");
/*  150 */       request.setAttribute("selectedscheme", selectedscheme);
/*      */     }
/*  152 */     String selectedSkin = "Grey";
/*  153 */     if (request.getParameter("selectedSkin") != null)
/*      */     {
/*  155 */       selectedSkin = request.getParameter("selectedSkin");
/*  156 */       request.setAttribute("selectedskin", selectedSkin);
/*      */     }
/*  158 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  159 */     long collectionTime = 0L;
/*      */     try
/*      */     {
/*  162 */       String query = "select max(COLLECTIONTIME) from AM_WLS_ServletData,AM_Servlet where AM_WLS_ServletData.ID=AM_Servlet.ID and AM_Servlet.PARENTID=" + webAppID;
/*      */       
/*  164 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  166 */       if (set.next())
/*      */       {
/*  168 */         collectionTime = set.getLong(1);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  173 */       closeResultSet(set);
/*      */     }
/*      */     
/*  176 */     if (collectionTime == -1L)
/*      */     {
/*  178 */       request.setAttribute("webAppID", String.valueOf(webAppID));
/*  179 */       request.setAttribute("note", "2");
/*  180 */       request.setAttribute("warName", webAppName);
/*  181 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("wlogic.nodata"));
/*  182 */       saveMessages(request, messages);
/*  183 */       return mapping.getInputForward();
/*      */     }
/*      */     try
/*      */     {
/*  187 */       String query = "select AM_WLS_ServletData.*,-1 as health from AM_WLS_ServletData,AM_Servlet where AM_WLS_ServletData.ID=AM_Servlet.ID and AM_Servlet.PARENTID=" + webAppID + " and COLLECTIONTIME=" + collectionTime;
/*  188 */       set = AMConnectionPool.executeQueryStmt(query);
/*  189 */       servletdata = new ArrayList();
/*  190 */       Properties data; while (set.next())
/*      */       {
/*  192 */         data = new Properties();
/*  193 */         data.setProperty("ID", String.valueOf(set.getInt("ID")));
/*  194 */         data.setProperty("Name", set.getString("SERVLETNAME"));
/*  195 */         data.setProperty("execHigh", String.valueOf(set.getLong("EXECUTIONTIMEHIGH")));
/*  196 */         data.setProperty("execTotal", String.valueOf(set.getLong("EXECUTIONTIMETOTAL")));
/*  197 */         data.setProperty("execAvg", String.valueOf(set.getLong("EXECUTIONTIMEAVERAGE")));
/*  198 */         data.setProperty("execLow", String.valueOf(set.getLong("EXECUTIONTIMELOW")));
/*  199 */         data.setProperty("invocationTotal", String.valueOf(set.getInt("INVOCATIONTOTALCOUNT")));
/*  200 */         data.setProperty("url", set.getString("SERVLETURL"));
/*  201 */         data.setProperty("health", set.getString("health"));
/*  202 */         ((ArrayList)servletdata).add(data);
/*      */       }
/*  204 */       if (((ArrayList)servletdata).size() != 0)
/*  205 */         request.setAttribute("data", servletdata);
/*  206 */       request.setAttribute("webAppID", String.valueOf(webAppID));
/*  207 */       request.setAttribute("note", "2");
/*  208 */       request.setAttribute("warName", webAppName);
/*  209 */       return mapping.findForward("wlogic.success");
/*      */     } catch (Exception exp) {
/*      */       Object servletdata;
/*  212 */       exp.printStackTrace();
/*  213 */       request.setAttribute("webAppID", String.valueOf(webAppID));
/*  214 */       request.setAttribute("note", "2");
/*  215 */       request.setAttribute("warName", webAppName);
/*  216 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exp.toString()));
/*  217 */       saveErrors(request, errors);
/*  218 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/*  222 */       closeResultSet(set);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getEJBData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  228 */     request.removeAttribute("data");
/*  229 */     this.mo.getReloadPeriod(request);
/*  230 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(request.getParameter("resourceid"), Long.parseLong((String)request.getAttribute("reloadperiod")));
/*  231 */     if (map != null)
/*      */     {
/*  233 */       request.setAttribute("systeminfo", map);
/*      */     }
/*  235 */     Properties status = getStatus(request.getParameter("resourceid"));
/*  236 */     request.setAttribute("currentstatus", status);
/*  237 */     ActionMessages messages = new ActionMessages();
/*  238 */     ActionErrors errors = new ActionErrors();
/*  239 */     ResultSet set = null;
/*  240 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*      */     
/*  242 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  243 */     long collectionTime = 0L;
/*      */     try
/*      */     {
/*  246 */       String query = "select max(COLLECTIONTIME) from AM_EJBTxData,AM_EJB,AM_EAR where AM_EJB.EJBID=AM_EJBTxData.EJBID and AM_EJB.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + resID;
/*  247 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  249 */       if (set.next())
/*      */       {
/*  251 */         collectionTime = set.getLong(1);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  258 */       closeResultSet(set);
/*      */     }
/*      */     
/*  261 */     if (collectionTime == 0L)
/*      */     {
/*      */ 
/*      */ 
/*  265 */       return returnForward(mapping.getInputForward(), request);
/*      */     }
/*      */     
/*  268 */     if (collectionTime == -1L)
/*      */     {
/*  270 */       request.setAttribute("note", "3");
/*  271 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("wlogic.nodata"));
/*  272 */       saveMessages(request, messages);
/*  273 */       return mapping.getInputForward();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  284 */       String entity = "select AM_EJB.EJBID,RESOURCENAME,EJBJAR,EARNAME,EJBTYPE,ACTIVATIONCOUNT,PASSIVATIONCOUNT,CACHEDBEANSCURRENTCOUNT,BEANSINUSECOUNT,IDLEBEANSCOUNT,WAITERTOTALCOUNT,TRANSACTIONSTIMEDOUTTOTALCOUNT,TRANSACTIONSROLLEDBACKTOTALCOUNT,TRANSACTIONSCOMMITTEDTOTALCOUNT, -1 as health from AM_ManagedObject,AM_EAR,AM_EJB,AM_EJBPoolData,AM_EJBCacheData,AM_EJBTxData where AM_EJBPoolData.EJBID=AM_EJB.EJBID and AM_EJB.PARENTID=AM_EAR.EARID and AM_EJB.EJBID=AM_ManagedObject.RESOURCEID and AM_EAR.PARENTID=" + resID + " and AM_EJBPoolData.EJBID=AM_EJBTxData.EJBID and AM_EJBPoolData.EJBID=AM_EJBCacheData.EJBID and AM_EJBPoolData.COLLECTIONTIME =AM_EJBTxData.COLLECTIONTIME and AM_EJBPoolData.COLLECTIONTIME=AM_EJBCacheData.COLLECTIONTIME and AM_EJBTxData.COLLECTIONTIME=" + collectionTime + " and AM_EJB.EJBTYPE='ENTITY'";
/*  285 */       stateless = "select AM_EJB.EJBID,RESOURCENAME,EJBJAR,EARNAME,EJBTYPE,'-1','-1','-1',BEANSINUSECOUNT,IDLEBEANSCOUNT,WAITERTOTALCOUNT,TRANSACTIONSTIMEDOUTTOTALCOUNT,TRANSACTIONSROLLEDBACKTOTALCOUNT,TRANSACTIONSCOMMITTEDTOTALCOUNT, -1 as health  from AM_EJBTxData,AM_EJBPoolData,AM_ManagedObject,AM_EJB,AM_EAR where AM_EJBPoolData.COLLECTIONTIME=AM_EJBTxData.COLLECTIONTIME and AM_EJBPoolData.COLLECTIONTIME=" + collectionTime + " and AM_EJBPoolData.EJBID=AM_EJBTxData.EJBID and AM_EJBPoolData.EJBID=AM_EJB.EJBID and AM_EJB.PARENTID=AM_EAR.EARID and AM_EJB.EJBID=AM_ManagedObject.RESOURCEID and AM_EAR.PARENTID=" + resID + " and AM_EJB.EJBTYPE='STATELESS_SESSION'";
/*  286 */       String stateful = "select AM_EJB.EJBID,RESOURCENAME,EJBJAR,EARNAME,EJBTYPE,ACTIVATIONCOUNT,PASSIVATIONCOUNT,CACHEDBEANSCURRENTCOUNT,'-1','-1','-1',TRANSACTIONSTIMEDOUTTOTALCOUNT,TRANSACTIONSROLLEDBACKTOTALCOUNT,TRANSACTIONSCOMMITTEDTOTALCOUNT, -1 as health  from AM_EJBTxData,AM_EJBCacheData,AM_ManagedObject,AM_EJB,AM_EAR where AM_EJBCacheData.COLLECTIONTIME=AM_EJBTxData.COLLECTIONTIME and AM_EJBCacheData.COLLECTIONTIME=" + collectionTime + " and AM_EJBCacheData.EJBID=AM_EJBTxData.EJBID and AM_EJBCacheData.EJBID=AM_EJB.EJBID and AM_EJB.PARENTID=AM_EAR.EARID and AM_EJB.EJBID=AM_ManagedObject.RESOURCEID and AM_EAR.PARENTID=" + resID + " and AM_EJB.EJBTYPE='STATEFUL_SESSION'";
/*  287 */       String messagedriven = "select AM_EJB.EJBID,RESOURCENAME,EJBJAR,EARNAME,EJBTYPE,'-1','-1','-1',BEANSINUSECOUNT,IDLEBEANSCOUNT,WAITERTOTALCOUNT,TRANSACTIONSTIMEDOUTTOTALCOUNT,TRANSACTIONSROLLEDBACKTOTALCOUNT,TRANSACTIONSCOMMITTEDTOTALCOUNT, -1 as health  from AM_EJBTxData,AM_EJBPoolData,AM_ManagedObject,AM_EJB,AM_EAR where AM_EJBPoolData.COLLECTIONTIME=AM_EJBTxData.COLLECTIONTIME and AM_EJBPoolData.COLLECTIONTIME=" + collectionTime + " and AM_EJBPoolData.EJBID=AM_EJBTxData.EJBID and AM_EJBPoolData.EJBID=AM_EJB.EJBID and AM_EJB.PARENTID=AM_EAR.EARID and AM_EJB.EJBID=AM_ManagedObject.RESOURCEID and AM_EAR.PARENTID=" + resID + " and AM_EJB.EJBTYPE='MESSAGE_DRIVEN'";
/*  288 */       String query = entity + " union all " + (String)stateless + " union all " + stateful + " union all " + messagedriven;
/*      */       
/*  290 */       set = AMConnectionPool.executeQueryStmt(query);
/*  291 */       ArrayList ejbdata = new ArrayList();
/*  292 */       Properties data; while (set.next())
/*      */       {
/*  294 */         data = new Properties();
/*      */         try
/*      */         {
/*  297 */           data.setProperty("ID", String.valueOf(set.getInt(1)));
/*  298 */           data.setProperty("Name", set.getString(2));
/*  299 */           data.setProperty("jarName", set.getString(3));
/*  300 */           data.setProperty("earName", set.getString(4));
/*  301 */           data.setProperty("type", set.getString(5));
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  316 */           data.setProperty("activation", set.getString(6));
/*  317 */           data.setProperty("passivation", set.getString(7));
/*  318 */           data.setProperty("cached", set.getString(8));
/*  319 */           data.setProperty("beansinuse", set.getString(9));
/*  320 */           data.setProperty("idlebeans", set.getString(10));
/*  321 */           data.setProperty("threadswaiting", set.getString(11));
/*  322 */           data.setProperty("txtimedout", set.getString(12));
/*  323 */           data.setProperty("txrolledback", set.getString(13));
/*  324 */           data.setProperty("txcommited", set.getString(14));
/*  325 */           data.setProperty("health", String.valueOf(set.getLong(15)));
/*      */         }
/*      */         catch (Exception e1)
/*      */         {
/*  329 */           System.out.println("***************Exception in EJB data fetching**********" + e1.toString());
/*      */         }
/*  331 */         ejbdata.add(data);
/*      */       }
/*      */       
/*      */ 
/*  335 */       if (ejbdata.size() != 0) {
/*  336 */         request.setAttribute("data", ejbdata);
/*      */       } else
/*  338 */         request.removeAttribute("data");
/*  339 */       request.setAttribute("note", "3");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  344 */       return returnForward(mapping.findForward("wlogic.success"), request);
/*      */     } catch (Exception exp) {
/*      */       Object stateless;
/*  347 */       exp.printStackTrace();
/*  348 */       request.setAttribute("note", "3");
/*  349 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exp.toString()));
/*  350 */       saveErrors(request, errors);
/*  351 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/*  355 */       closeResultSet(set);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getThreadPoolData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  362 */     request.removeAttribute("data");
/*  363 */     this.mo.getReloadPeriod(request);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  373 */     Properties status = getStatus(request.getParameter("resourceid"));
/*  374 */     request.setAttribute("currentstatus", status);
/*  375 */     ActionMessages messages = new ActionMessages();
/*  376 */     ActionErrors errors = new ActionErrors();
/*  377 */     ResultSet set = null;
/*  378 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*  379 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  380 */     long collectionTime = 0L;
/*      */     try
/*      */     {
/*  383 */       String query = "select max(COLLECTIONTIME) from AM_WLS_ThreadData,AM_Thread where AM_WLS_ThreadData.ID=AM_Thread.ID and AM_Thread.PARENTID=" + resID;
/*  384 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  386 */       if (set.next())
/*      */       {
/*  388 */         collectionTime = set.getLong(1);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  393 */       closeResultSet(set);
/*      */     }
/*      */     
/*  396 */     if (collectionTime == 0L)
/*      */     {
/*      */ 
/*      */ 
/*  400 */       return returnForward(mapping.getInputForward(), request);
/*      */     }
/*      */     
/*  403 */     if (collectionTime == -1L)
/*      */     {
/*  405 */       request.setAttribute("note", "4");
/*  406 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("wlogic.nodata"));
/*  407 */       saveMessages(request, messages);
/*  408 */       return mapping.getInputForward();
/*      */     }
/*      */     try
/*      */     {
/*  412 */       String query = "select AM_ManagedObject.RESOURCENAME,AM_WLS_ThreadData.PENDINGREQUESTCURRENTCOUNT,AM_WLS_ThreadData.EXECUTETHREADS,AM_WLS_ThreadData.EXECUTETHREADCURRENTIDLECOUNT,AM_WLS_ThreadData.ID ,-1 as health  from AM_ManagedObject,AM_WLS_ThreadData,AM_Thread where AM_WLS_ThreadData.ID=AM_Thread.ID and AM_Thread.ID =AM_ManagedObject.RESOURCEID and AM_Thread.PARENTID=" + resID + " and COLLECTIONTIME=" + collectionTime;
/*  413 */       set = AMConnectionPool.executeQueryStmt(query);
/*  414 */       threaddata = new ArrayList();
/*  415 */       Properties data; while (set.next())
/*      */       {
/*  417 */         data = new Properties();
/*  418 */         data.setProperty("Name", set.getString(1));
/*  419 */         data.setProperty("pendingReq", String.valueOf(set.getInt(2)));
/*  420 */         data.setProperty("totalThreads", String.valueOf(set.getInt(3)));
/*  421 */         data.setProperty("idleThreads", String.valueOf(set.getInt(4)));
/*  422 */         data.setProperty("usedThreads", String.valueOf(set.getInt(3) - set.getInt(4)));
/*  423 */         data.setProperty("ID", String.valueOf(set.getInt(5)));
/*  424 */         data.setProperty("health", String.valueOf(set.getInt(6)));
/*  425 */         ((ArrayList)threaddata).add(data);
/*      */       }
/*  427 */       if (((ArrayList)threaddata).size() != 0)
/*  428 */         request.setAttribute("data", threaddata);
/*  429 */       request.setAttribute("note", "4");
/*      */       
/*  431 */       return returnForward(mapping.findForward("wlogic.success"), request);
/*      */     } catch (Exception exp) {
/*      */       Object threaddata;
/*  434 */       exp.printStackTrace();
/*  435 */       request.setAttribute("note", "4");
/*  436 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exp.toString()));
/*  437 */       saveErrors(request, errors);
/*  438 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/*  442 */       closeResultSet(set);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getJDBCPoolData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  448 */     request.removeAttribute("data");
/*  449 */     this.mo.getReloadPeriod(request);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  455 */     Properties status = getStatus(request.getParameter("resourceid"));
/*  456 */     request.setAttribute("currentstatus", status);
/*  457 */     ActionMessages messages = new ActionMessages();
/*  458 */     ActionErrors errors = new ActionErrors();
/*  459 */     ResultSet set = null;
/*  460 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*      */     
/*  462 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  463 */     long collectionTime = 0L;
/*      */     try
/*      */     {
/*  466 */       String query = "select max(COLLECTIONTIME) from AM_WLS_JDBCData,AM_JDBC  where AM_WLS_JDBCData.ID=AM_JDBC.ID and AM_JDBC.PARENTID=" + resID;
/*  467 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  469 */       if (set.next())
/*      */       {
/*  471 */         collectionTime = set.getLong(1);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  476 */       closeResultSet(set);
/*      */     }
/*      */     
/*  479 */     if (collectionTime == 0L)
/*      */     {
/*      */ 
/*      */ 
/*  483 */       return returnForward(mapping.getInputForward(), request);
/*      */     }
/*  485 */     if (collectionTime == -1L)
/*      */     {
/*  487 */       request.setAttribute("note", "5");
/*  488 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("wlogic.nodata"));
/*  489 */       saveMessages(request, messages);
/*  490 */       return mapping.getInputForward();
/*      */     }
/*      */     try
/*      */     {
/*  494 */       String query = "select RESOURCENAME,CONNECTIONSTOTALCOUNT,ACTIVECONNECTIONSCURRENTCOUNT,WAITINGFORCONNECTIONCURRENTCOUNT,POOLSTATE,CONNECTIONLEAKPROFILECOUNT,AM_WLS_JDBCData.ID,-1 as health  from AM_WLS_JDBCData,AM_ManagedObject,AM_JDBC where COLLECTIONTIME =" + collectionTime + " and AM_JDBC.ID=AM_WLS_JDBCData.ID and AM_JDBC.ID=AM_ManagedObject.RESOURCEID and AM_JDBC.PARENTID=" + resID;
/*  495 */       set = AMConnectionPool.executeQueryStmt(query);
/*  496 */       jdbcdata = new ArrayList();
/*  497 */       Properties data; while (set.next())
/*      */       {
/*  499 */         data = new Properties();
/*  500 */         data.setProperty("Name", set.getString(1));
/*  501 */         data.setProperty("total", String.valueOf(set.getInt(2)));
/*  502 */         data.setProperty("current", String.valueOf(set.getInt(3)));
/*      */         try
/*      */         {
/*  505 */           long currentval = set.getLong(3);
/*  506 */           long totalval = set.getLong(2);
/*  507 */           long currentpercent = totalval != 0L ? currentval * 100L / totalval : 0L;
/*  508 */           data.setProperty("current_percent", String.valueOf(currentpercent));
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  512 */           exc.printStackTrace();
/*  513 */           data.setProperty("current_percent", "-");
/*      */         }
/*  515 */         data.setProperty("threadsWaiting", String.valueOf(set.getInt(4)));
/*  516 */         data.setProperty("state", String.valueOf(set.getInt(5)));
/*  517 */         data.setProperty("leakedCon", String.valueOf(set.getInt(6)));
/*  518 */         data.setProperty("ID", String.valueOf(set.getInt(7)));
/*  519 */         data.setProperty("health", String.valueOf(set.getInt(8)));
/*  520 */         ((ArrayList)jdbcdata).add(data);
/*      */       }
/*  522 */       if (((ArrayList)jdbcdata).size() != 0)
/*  523 */         request.setAttribute("data", jdbcdata);
/*  524 */       request.setAttribute("note", "5");
/*      */       
/*  526 */       return returnForward(mapping.findForward("wlogic.success"), request);
/*      */     } catch (Exception exp) {
/*      */       Object jdbcdata;
/*  529 */       exp.printStackTrace();
/*  530 */       request.setAttribute("note", "5");
/*  531 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exp.toString()));
/*  532 */       saveErrors(request, errors);
/*  533 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/*  537 */       closeResultSet(set);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getWlogicConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  543 */     ActionMessages messages = new ActionMessages();
/*  544 */     ActionErrors errors = new ActionErrors();
/*      */     
/*      */ 
/*  547 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*  548 */     String poll = (String)request.getAttribute("reloadperiod");
/*      */     
/*      */ 
/*  551 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(request.getParameter("resourceid"), Long.parseLong(poll));
/*  552 */     if (map != null)
/*      */     {
/*  554 */       request.setAttribute("systeminfo", map);
/*      */     }
/*  556 */     Properties status = getStatus(request.getParameter("resourceid"));
/*  557 */     request.setAttribute("currentstatus", status);
/*  558 */     ResultSet set = null;
/*  559 */     String moname = request.getParameter("moname");
/*  560 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*  562 */     int interval = 300;
/*  563 */     String username = "";
/*  564 */     String password = "";
/*      */     try
/*      */     {
/*  567 */       String query = "select USERNAME," + DBQueryUtil.decodeBytes("PASSWORD") + " as PASSWORD,POLLINTERVAL,AM_ManagedObject.DISPLAYNAME,DATABASENAME from AM_RESOURCECONFIG,CollectData,AM_ManagedObject where CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and AM_RESOURCECONFIG.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.resourceid=" + resID;
/*  568 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  570 */       if (set.next())
/*      */       {
/*  572 */         username = set.getString(1);
/*  573 */         password = set.getString(2);
/*  574 */         interval = set.getInt(3);
/*  575 */         version = set.getString(5);
/*  576 */         ((DynaActionForm)form).set("username", username);
/*  577 */         ((DynaActionForm)form).set("password", password);
/*  578 */         ((DynaActionForm)form).set("pollinterval", new Integer(interval / 60));
/*  579 */         ((DynaActionForm)form).set("displayname", set.getString(4));
/*  580 */         ((DynaActionForm)form).set("version", version);
/*      */       }
/*  582 */       request.setAttribute("note", "6");
/*      */       
/*  584 */       return returnForward(mapping.findForward("wlogic.success"), request);
/*      */     }
/*      */     catch (Exception exp) {
/*      */       String version;
/*  588 */       exp.printStackTrace();
/*  589 */       request.setAttribute("note", "6");
/*  590 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exp.toString()));
/*  591 */       saveErrors(request, errors);
/*  592 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/*  596 */       closeResultSet(set);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward configure(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  604 */     ActionMessages messages = new ActionMessages();
/*  605 */     ActionErrors errors = new ActionErrors();
/*  606 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/*  609 */       String id = request.getParameter("resourceid");
/*  610 */       Properties dcprops = new Properties();
/*  611 */       dcprops.put("username", (String)((DynaActionForm)form).get("username"));
/*  612 */       dcprops.put("password", (String)((DynaActionForm)form).get("password"));
/*  613 */       dcprops.put("pollinterval", ((Integer)((DynaActionForm)form).get("pollinterval")).intValue() * 60 + "");
/*  614 */       dcprops.put("id", id);
/*  615 */       AMDCInf dcConfig = new ScheduleWebLogicDataCollection();
/*  616 */       dcConfig.ScheduleDataCollection(dcprops);
/*  617 */       request.setAttribute("note", "6");
/*  618 */       String displayname = (String)((DynaActionForm)form).get("displayname");
/*  619 */       String updateQuery1 = "update AM_ManagedObject set displayname='" + displayname + "' where resourceid=" + id;
/*  620 */       this.mo.executeUpdateStmt(updateQuery1);
/*  621 */       EnterpriseUtil.addUpdateQueryToFile(updateQuery1);
/*  622 */       String haid = "";
/*  623 */       String version = request.getParameter("version");
/*  624 */       if (version != null)
/*      */       {
/*      */ 
/*      */ 
/*  628 */         String updateQuery2 = "update AM_RESOURCECONFIG  set DATABASENAME='" + version + "' where AM_RESOURCECONFIG.RESOURCEID=" + id;
/*  629 */         String resourcename = null;
/*      */         try
/*      */         {
/*  632 */           ResultSet resname = AMConnectionPool.executeQueryStmt("select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + id);
/*      */           
/*  634 */           if (resname.next())
/*      */           {
/*  636 */             resourcename = resname.getString(1);
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  641 */           e.printStackTrace();
/*      */         }
/*  643 */         String updateQuery3 = "update InetService set PROTOCOLVERSION='" + version + "' where InetService.NAME='" + resourcename + "'";
/*      */         
/*  645 */         this.mo.executeUpdateStmt(updateQuery2);
/*  646 */         this.mo.executeUpdateStmt(updateQuery3);
/*  647 */         EnterpriseUtil.addUpdateQueryToFile(updateQuery2);
/*      */       }
/*  649 */       if (request.getParameter("haid") != null)
/*      */       {
/*  651 */         haid = "&haid=" + request.getParameter("haid");
/*      */       }
/*      */       
/*  654 */       if (!errors.isEmpty())
/*      */       {
/*  656 */         saveErrors(request, errors);
/*      */       }
/*      */       
/*  659 */       return new ActionForward("/showresource.do?resourceid=" + id + "&method=showResourceForResourceID" + haid, true);
/*      */ 
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*  664 */       exp.printStackTrace();
/*  665 */       request.setAttribute("note", "6");
/*  666 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exp.toString()));
/*  667 */       saveErrors(request, errors); }
/*  668 */     return mapping.getInputForward();
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward checkWlogicConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  675 */     ActionMessages messages = new ActionMessages();
/*  676 */     ActionErrors errors = new ActionErrors();
/*  677 */     ResultSet set = null;
/*  678 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*  679 */     String moname = request.getParameter("moname");
/*  680 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  681 */     boolean dcActive = false;
/*  682 */     boolean isConfigured = false;
/*      */     
/*  684 */     int interval = 300;
/*  685 */     String username = "";
/*  686 */     String password = "";
/*      */     try
/*      */     {
/*  689 */       String query = "select USERNAME,PASSWORD from ResourceConfig  where RESOURCENAME" + moname;
/*  690 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  692 */       if (set.next())
/*      */       {
/*  694 */         isConfigured = true;
/*  695 */         username = set.getString(1);
/*  696 */         password = set.getString(2);
/*      */       }
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*  701 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  705 */       closeResultSet(set);
/*      */     }
/*      */     try
/*      */     {
/*  709 */       String query = "select ACTIVE,POLLINTERVAL from CollectData where RESOURCENAME" + moname + " and RESOURCETYPE='WEBLOGIC-server'";
/*  710 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  712 */       if (set.next())
/*      */       {
/*  714 */         active = set.getString(1);
/*  715 */         interval = set.getInt(2);
/*  716 */         if (((String)active).equals("true"))
/*  717 */           dcActive = true;
/*      */       }
/*  719 */       if (isConfigured)
/*      */       {
/*  721 */         if (dcActive)
/*      */         {
/*  723 */           return mapping.findForward("wlogic.detailsPage");
/*      */         }
/*      */         
/*      */ 
/*  727 */         request.setAttribute("username", username);
/*  728 */         request.setAttribute("password", password);
/*  729 */         request.setAttribute("interval", String.valueOf(interval));
/*  730 */         return mapping.findForward("wlogic.configure");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  735 */       return mapping.findForward("wlogic.configure");
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*      */       Object active;
/*  740 */       exp.printStackTrace();
/*  741 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exp.toString()));
/*  742 */       saveErrors(request, errors);
/*  743 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/*  747 */       closeResultSet(set);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getJMSData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  752 */     ActionMessages messages = new ActionMessages();
/*  753 */     ActionErrors errors = new ActionErrors();
/*  754 */     ResultSet set = null;
/*  755 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*  756 */     String moname = request.getParameter("moname");
/*  757 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  758 */     HashMap toreturn = new HashMap();
/*      */     try
/*      */     {
/*  761 */       long collectiontime = -1L;
/*  762 */       ResultSet rs_childid = AMConnectionPool.executeQueryStmt("select CHILDID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.PARENTID=" + resID + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.type='JMS'");
/*  763 */       if (rs_childid.next())
/*      */       {
/*  765 */         String childid = rs_childid.getString(1);
/*  766 */         if (childid != null)
/*      */         {
/*  768 */           ResultSet rs_time = AMConnectionPool.executeQueryStmt("select max(collectiontime) from AM_JMSData where ID=" + childid);
/*  769 */           if (rs_time.next())
/*      */           {
/*  771 */             if ((rs_time.getString(1) != null) && (!rs_time.getString(1).equals("NULL")))
/*      */             {
/*  773 */               collectiontime = rs_time.getLong(1);
/*      */             }
/*      */           }
/*  776 */           closeResultSet(rs_time);
/*      */         }
/*      */       }
/*      */       
/*  780 */       closeResultSet(rs_childid);
/*  781 */       if (collectiontime == -1L)
/*      */       {
/*  783 */         ResultSet rsJMSErr = null;
/*      */         try
/*      */         {
/*  786 */           rsJMSErr = AMConnectionPool.executeQueryStmt("select ERRORMSG from AM_RESOURCECONFIG where RESOURCEID=" + resID);
/*  787 */           if (rsJMSErr.next())
/*      */           {
/*  789 */             String errorMsg = rsJMSErr.getString(1);
/*  790 */             if ((errorMsg != null) && (errorMsg.equals("jms.notdeployed.error")))
/*      */             {
/*  792 */               request.setAttribute("jmserror", errorMsg);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  815 */           return returnForward(mapping.getInputForward(), request);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  798 */           exc.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*  802 */           if (rsJMSErr != null)
/*      */           {
/*      */             try
/*      */             {
/*  806 */               rsJMSErr.close();
/*      */             }
/*      */             catch (Exception exc) {}
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  819 */         ResultSet rs_dep = AMConnectionPool.executeQueryStmt("select CHILDID,RESOURCENAME FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.PARENTID=" + resID + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.type='JMS'");
/*  820 */         Hashtable server_data = new Hashtable();
/*  821 */         ArrayList jmsids = new ArrayList();
/*  822 */         while (rs_dep.next())
/*      */         {
/*  824 */           long id = rs_dep.getLong(1);
/*      */           
/*  826 */           String resname = rs_dep.getString(2);
/*  827 */           String qry = "select * from AM_JMSData where ID=" + id + " and collectiontime=" + collectiontime;
/*  828 */           ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  829 */           Properties data = new Properties();
/*  830 */           if (rs.next())
/*      */           {
/*  832 */             data.setProperty("BYTESCURRENTCOUNT", rs.getString("BYTESCURRENTCOUNT"));
/*  833 */             data.setProperty("BYTESPENDINGCOUNT", rs.getString("BYTESPENDINGCOUNT"));
/*      */             
/*  835 */             if ((rs.getString("BYTESRECEIVEDPERMIN") != null) && (!rs.getString("BYTESRECEIVEDPERMIN").equals("-1")))
/*      */             {
/*  837 */               data.setProperty("BYTESRECEIVEDPERMIN", rs.getString("BYTESRECEIVEDPERMIN"));
/*      */             }
/*      */             else
/*      */             {
/*  841 */               data.setProperty("BYTESRECEIVEDPERMIN", "_");
/*      */             }
/*  843 */             data.setProperty("MSGCURRENTCOUNT", rs.getString("MSGCURRENTCOUNT"));
/*  844 */             data.setProperty("MSGPENDINGCOUNT", rs.getString("MSGPENDINGCOUNT"));
/*  845 */             if ((rs.getString("MSGRECEIVEDPERMIN") != null) && (!rs.getString("MSGRECEIVEDPERMIN").equals("-1")))
/*      */             {
/*  847 */               data.setProperty("MSGRECEIVEDPERMIN", rs.getString("MSGRECEIVEDPERMIN"));
/*      */             }
/*      */             else
/*      */             {
/*  851 */               data.setProperty("MSGRECEIVEDPERMIN", "_");
/*      */             }
/*      */             
/*  854 */             data.setProperty("ID", String.valueOf(id));
/*  855 */             data.setProperty("NAME", resname);
/*      */           }
/*  857 */           closeResultSet(rs);
/*  858 */           jmsids.add(Long.valueOf(id));
/*  859 */           server_data.put(Long.valueOf(id), data);
/*      */         }
/*  861 */         closeResultSet(rs_dep);
/*  862 */         toreturn.put("attids", jmsids);
/*  863 */         toreturn.put("data", server_data);
/*  864 */         toreturn.put("error", "None");
/*      */         
/*  866 */         request.setAttribute("jmsdata", toreturn);
/*  867 */         request.setAttribute("note", "6");
/*  868 */         return returnForward(mapping.findForward("wlogic.success"), request);
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/*  872 */         return returnForward(mapping.getInputForward(), request);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       HashMap data;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  884 */       return mapping.getInputForward();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  877 */       exc.printStackTrace();
/*  878 */       request.setAttribute("note", "6");
/*  879 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exc.toString()));
/*  880 */       saveErrors(request, errors);
/*  881 */       data = new HashMap();
/*  882 */       data.put("error", "No Data");
/*  883 */       request.setAttribute("data", data);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getSAFData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  890 */     ActionMessages messages = new ActionMessages();
/*  891 */     ActionErrors errors = new ActionErrors();
/*  892 */     ResultSet set = null;
/*  893 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*  894 */     String moname = request.getParameter("moname");
/*  895 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  896 */     HashMap toreturn = new HashMap();
/*      */     try
/*      */     {
/*  899 */       long collectiontime = -1L;
/*  900 */       ResultSet rs_childid = AMConnectionPool.executeQueryStmt("select CHILDID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.PARENTID=" + resID + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.type='SAF'");
/*  901 */       if (rs_childid.next())
/*      */       {
/*  903 */         String childid = rs_childid.getString(1);
/*  904 */         if (childid != null)
/*      */         {
/*  906 */           ResultSet rs_time = AMConnectionPool.executeQueryStmt("select max(collectiontime) from AM_SAFData where ID=" + childid);
/*  907 */           if (rs_time.next())
/*      */           {
/*  909 */             if ((rs_time.getString(1) != null) && (!rs_time.getString(1).equals("NULL")))
/*      */             {
/*  911 */               collectiontime = rs_time.getLong(1);
/*      */             }
/*      */           }
/*  914 */           closeResultSet(rs_time);
/*      */         }
/*      */       }
/*      */       
/*  918 */       closeResultSet(rs_childid);
/*  919 */       if (collectiontime == -1L)
/*      */       {
/*  921 */         return returnForward(mapping.getInputForward(), request);
/*      */       }
/*      */       try
/*      */       {
/*  925 */         ResultSet rs_dep = AMConnectionPool.executeQueryStmt("select CHILDID,RESOURCENAME FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.PARENTID=" + resID + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.type='SAF'");
/*  926 */         Hashtable server_data = new Hashtable();
/*  927 */         ArrayList jmsids = new ArrayList();
/*      */         
/*  929 */         while (rs_dep.next())
/*      */         {
/*  931 */           long id = rs_dep.getLong(1);
/*      */           
/*  933 */           String resname = rs_dep.getString(2);
/*  934 */           String qry = "select * from AM_SAFData where ID=" + id + " and collectiontime=" + collectiontime;
/*  935 */           ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  936 */           Properties data = new Properties();
/*  937 */           if (rs.next())
/*      */           {
/*  939 */             data.setProperty("CNVCURRENT", rs.getString("CNVCURRENT"));
/*  940 */             if ((rs.getString("CNVPOLL") != null) && (!rs.getString("CNVPOLL").equals("-1")))
/*      */             {
/*  942 */               data.setProperty("CNVPOLL", rs.getString("CNVPOLL"));
/*      */             }
/*      */             else
/*      */             {
/*  946 */               data.setProperty("CNVPOLL", "_");
/*      */             }
/*  948 */             data.setProperty("ENDPOINTSCURRENT", rs.getString("ENDPOINTSCURRENT"));
/*  949 */             if ((rs.getString("ENDPOINTSPOLL") != null) && (!rs.getString("ENDPOINTSPOLL").equals("-1")))
/*      */             {
/*  951 */               data.setProperty("ENDPOINTSPOLL", rs.getString("ENDPOINTSPOLL"));
/*      */             }
/*      */             else
/*      */             {
/*  955 */               data.setProperty("ENDPOINTSPOLL", "_");
/*      */             }
/*  957 */             data.setProperty("ID", String.valueOf(id));
/*  958 */             data.setProperty("NAME", resname);
/*      */           }
/*  960 */           closeResultSet(rs);
/*  961 */           jmsids.add(Long.valueOf(id));
/*  962 */           server_data.put(Long.valueOf(id), data);
/*      */         }
/*  964 */         closeResultSet(rs_dep);
/*  965 */         toreturn.put("attids", jmsids);
/*  966 */         toreturn.put("data", server_data);
/*  967 */         toreturn.put("error", "None");
/*      */         
/*  969 */         request.setAttribute("safdata", toreturn);
/*  970 */         request.setAttribute("note", "6");
/*  971 */         return returnForward(mapping.findForward("wlogic.success"), request);
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/*  975 */         exc.printStackTrace();
/*  976 */         return returnForward(mapping.getInputForward(), request);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       HashMap data;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  989 */       return mapping.getInputForward();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  982 */       exc.printStackTrace();
/*  983 */       request.setAttribute("note", "6");
/*  984 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exc.toString()));
/*  985 */       saveErrors(request, errors);
/*  986 */       data = new HashMap();
/*  987 */       data.put("error", "No Data");
/*  988 */       request.setAttribute("data", data);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Properties getStatus(String resourceid)
/*      */   {
/*  998 */     ArrayList rows = this.mo.getRows("select category,severity from Alert where source='" + resourceid + "' and category in ('216','217','224')");
/*  999 */     Properties status = new Properties();
/* 1000 */     status.put("216", "5");
/* 1001 */     status.put("217", "5");
/* 1002 */     status.put("224", "5");
/*      */     
/* 1004 */     for (int i = 0; i < rows.size(); i++)
/*      */     {
/* 1006 */       ArrayList row = (ArrayList)rows.get(i);
/* 1007 */       String category = (String)row.get(0);
/* 1008 */       String severity = (String)row.get(1);
/* 1009 */       status.setProperty(category, severity);
/*      */     }
/* 1011 */     return status;
/*      */   }
/*      */   
/*      */   private final void closeResultSet(ResultSet set)
/*      */   {
/* 1016 */     if (set != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/* 1021 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */       catch (Exception ex) {
/* 1024 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private ActionForward returnForward(ActionForward forward, HttpServletRequest request)
/*      */   {
/* 1031 */     if (request.getParameter("include") != null)
/*      */     {
/*      */ 
/* 1034 */       return new ActionForward("/applications.do", true);
/*      */     }
/*      */     
/*      */ 
/* 1038 */     return forward;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getBusinessProcessData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1045 */     request.removeAttribute("data");
/* 1046 */     this.mo.getReloadPeriod(request);
/* 1047 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(request.getParameter("resourceid"), Long.parseLong((String)request.getAttribute("reloadperiod")));
/*      */     
/* 1049 */     if (map != null)
/*      */     {
/* 1051 */       request.setAttribute("systeminfo", map);
/*      */     }
/*      */     
/* 1054 */     Properties status = getStatus(request.getParameter("resourceid"));
/* 1055 */     request.setAttribute("currentstatus", status);
/* 1056 */     ActionMessages messages = new ActionMessages();
/* 1057 */     ActionErrors errors = new ActionErrors();
/* 1058 */     ResultSet set = null;
/* 1059 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/* 1060 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1061 */     long maxtime = -1L;
/*      */     
/*      */     try
/*      */     {
/* 1065 */       String maxTimeQuery = "SELECT max(COLLECTIONTIME) from AM_WLI_BPM wli,AM_ManagedObject amo,AM_PARENTCHILDMAPPER apc  where apc.CHILDID=wli.PROCESSID and amo.RESOURCEID=apc.PARENTID and amo.RESOURCEID=" + resID;
/* 1066 */       set = AMConnectionPool.executeQueryStmt(maxTimeQuery);
/*      */       
/* 1068 */       if (set.next())
/*      */       {
/* 1070 */         maxtime = set.getLong(1);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1077 */       closeResultSet(set);
/*      */     }
/*      */     
/* 1080 */     if (maxtime == 0L)
/*      */     {
/* 1082 */       return returnForward(mapping.getInputForward(), request);
/*      */     }
/*      */     
/* 1085 */     if (maxtime == -1L)
/*      */     {
/* 1087 */       request.setAttribute("note", "3");
/* 1088 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("wlogic.nodata"));
/* 1089 */       saveMessages(request, messages);
/* 1090 */       return mapping.getInputForward();
/*      */     }
/* 1092 */     ResultSet businessProcIdSet = null;
/*      */     try
/*      */     {
/* 1095 */       String businessProcIdQuery = "select RESOURCEID,RESOURCENAME from AM_ManagedObject amo, AM_PARENTCHILDMAPPER apc where apc.CHILDID=amo.RESOURCEID and apc.PARENTID=" + resID + " and amo.TYPE='BusinessProcess'";
/* 1096 */       businessProcIdSet = AMConnectionPool.executeQueryStmt(businessProcIdQuery);
/* 1097 */       bpData = new ArrayList();
/* 1098 */       int childId; while (businessProcIdSet.next())
/*      */       {
/* 1100 */         childId = businessProcIdSet.getInt(1);
/* 1101 */         String resourceName = businessProcIdSet.getString(2);
/* 1102 */         String businessProcInfoQuery = "select * from AM_WLI_BPM where PROCESSID=" + childId + " and COLLECTIONTIME=" + maxtime;
/* 1103 */         ResultSet bpmInfoSet = AMConnectionPool.executeQueryStmt(businessProcInfoQuery);
/*      */         try
/*      */         {
/* 1106 */           if (bpmInfoSet.next())
/*      */           {
/* 1108 */             Properties bpmProps = new Properties();
/*      */             
/*      */ 
/* 1111 */             bpmProps.put("PROCESSID", String.valueOf(bpmInfoSet.getInt("PROCESSID")));
/* 1112 */             bpmProps.put("PROCESSNAME", bpmInfoSet.getString("PROCESSNAME"));
/* 1113 */             bpmProps.put("AVGEXECTIME", String.valueOf(bpmInfoSet.getInt("AVGEXECTIMEDIFF")));
/* 1114 */             bpmProps.put("TOTALINSTANCE", String.valueOf(bpmInfoSet.getInt("TOTALINSTANCEDIFF")));
/* 1115 */             bpmProps.put("COMPLETED", String.valueOf(bpmInfoSet.getInt("COMPLETEDDIFF")));
/* 1116 */             if (bpmInfoSet.getInt("SLASET") > 0)
/*      */             {
/* 1118 */               bpmProps.put("SLAEXCEEDED", String.valueOf(bpmInfoSet.getInt("SLAEXCEEDEDDIFF")));
/*      */             }
/*      */             else
/*      */             {
/* 1122 */               bpmProps.put("SLAEXCEEDED", "N/A");
/*      */             }
/*      */             
/* 1125 */             if (bpmInfoSet.getInt("STATE") > 0)
/*      */             {
/* 1127 */               bpmProps.put("RUNNING", String.valueOf(bpmInfoSet.getInt("RUNNINGDIFF")));
/*      */             }
/*      */             else
/*      */             {
/* 1131 */               bpmProps.put("RUNNING", "N/A");
/*      */             }
/*      */             
/* 1134 */             bpmProps.put("ABORTED", String.valueOf(bpmInfoSet.getInt("ABORTEDDIFF")));
/* 1135 */             bpmProps.put("TERMINATEDPR", String.valueOf(bpmInfoSet.getInt("TERMINATEDPRDIFF")));
/* 1136 */             bpmProps.put("FROZEN", String.valueOf(bpmInfoSet.getInt("FROZENPRDIFF")));
/* 1137 */             bpmProps.put("APPLICATIONAME", bpmInfoSet.getString("APPLICATIONAME"));
/* 1138 */             bpData.add(bpmProps);
/*      */           }
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1143 */           exc.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1151 */       request.setAttribute("data", bpData);
/* 1152 */       return returnForward(mapping.findForward("wlogic.success"), request);
/*      */     }
/*      */     catch (Exception exc) {
/*      */       ArrayList bpData;
/* 1156 */       exc.printStackTrace();
/* 1157 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exc.toString()));
/* 1158 */       saveErrors(request, errors);
/* 1159 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/* 1163 */       closeResultSet(businessProcIdSet);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getApplnIntegrationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1171 */     request.removeAttribute("data");
/* 1172 */     this.mo.getReloadPeriod(request);
/* 1173 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(request.getParameter("resourceid"), Long.parseLong((String)request.getAttribute("reloadperiod")));
/*      */     
/* 1175 */     if (map != null)
/*      */     {
/* 1177 */       request.setAttribute("systeminfo", map);
/*      */     }
/*      */     
/* 1180 */     Properties status = getStatus(request.getParameter("resourceid"));
/* 1181 */     request.setAttribute("currentstatus", status);
/* 1182 */     ActionMessages messages = new ActionMessages();
/* 1183 */     ActionErrors errors = new ActionErrors();
/* 1184 */     ResultSet set = null;
/* 1185 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/* 1186 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1187 */     long maxtime = -1L;
/*      */     
/*      */     try
/*      */     {
/* 1191 */       String maxTimeQuery = "SELECT max(COLLECTIONTIME) from AM_WLI_AI ai,AM_ManagedObject amo,AM_PARENTCHILDMAPPER apc  where apc.CHILDID=ai.APPVIEWID and amo.RESOURCEID=apc.PARENTID and amo.RESOURCEID=" + resID;
/* 1192 */       set = AMConnectionPool.executeQueryStmt(maxTimeQuery);
/*      */       
/* 1194 */       if (set.next())
/*      */       {
/* 1196 */         maxtime = set.getLong(1);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1203 */       closeResultSet(set);
/*      */     }
/*      */     
/* 1206 */     if (maxtime == 0L)
/*      */     {
/* 1208 */       return returnForward(mapping.getInputForward(), request);
/*      */     }
/*      */     
/* 1211 */     if (maxtime == -1L)
/*      */     {
/* 1213 */       request.setAttribute("note", "3");
/* 1214 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("wlogic.nodata"));
/* 1215 */       saveMessages(request, messages);
/* 1216 */       return mapping.getInputForward();
/*      */     }
/*      */     
/* 1219 */     ResultSet businessProcIdSet = null;
/*      */     try
/*      */     {
/* 1222 */       String businessProcIdQuery = "select RESOURCEID,RESOURCENAME from AM_ManagedObject amo, AM_PARENTCHILDMAPPER apc where apc.CHILDID=amo.RESOURCEID and apc.PARENTID=" + resID + " and amo.TYPE='ApplicationIntegration'";
/* 1223 */       businessProcIdSet = AMConnectionPool.executeQueryStmt(businessProcIdQuery);
/* 1224 */       bpData = new ArrayList();
/* 1225 */       int childId; while (businessProcIdSet.next())
/*      */       {
/* 1227 */         childId = businessProcIdSet.getInt(1);
/* 1228 */         String resourceName = businessProcIdSet.getString(2);
/* 1229 */         String businessProcInfoQuery = "select * from AM_WLI_AI where APPVIEWID=" + childId + " and COLLECTIONTIME=" + maxtime;
/* 1230 */         ResultSet bpmInfoSet = AMConnectionPool.executeQueryStmt(businessProcInfoQuery);
/*      */         try
/*      */         {
/* 1233 */           if (bpmInfoSet.next())
/*      */           {
/* 1235 */             Properties bpmProps = new Properties();
/*      */             
/* 1237 */             bpmProps.put("APPVIEWID", String.valueOf(bpmInfoSet.getInt("APPVIEWID")));
/* 1238 */             bpmProps.put("APPVIEWNAME", bpmInfoSet.getString("APPVIEWNAME"));
/* 1239 */             bpmProps.put("EVENTCOUNT", String.valueOf(bpmInfoSet.getInt("EVENTCOUNTDIFF")));
/* 1240 */             bpmProps.put("EVENTERRORCOUNT", String.valueOf(bpmInfoSet.getInt("EVENTERRORCOUNTDIFF")));
/* 1241 */             bpmProps.put("AVGSERVICETIME", String.valueOf(bpmInfoSet.getInt("AVGSERVICETIMEDIFF")));
/* 1242 */             bpmProps.put("SERVICECOUNT", String.valueOf(bpmInfoSet.getInt("SERVICECOUNTDIFF")));
/* 1243 */             bpmProps.put("SERVICEERRORCOUNT", String.valueOf(bpmInfoSet.getInt("SERVICEERRORCOUNTDIFF")));
/*      */             
/*      */ 
/* 1246 */             bpData.add(bpmProps);
/*      */           }
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1251 */           exc.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1259 */       request.setAttribute("data", bpData);
/* 1260 */       return returnForward(mapping.findForward("wlogic.success"), request);
/*      */     }
/*      */     catch (Exception exc) {
/*      */       ArrayList bpData;
/* 1264 */       exc.printStackTrace();
/* 1265 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exc.toString()));
/* 1266 */       saveErrors(request, errors);
/* 1267 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/* 1271 */       closeResultSet(businessProcIdSet);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getMessageBrokerData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1279 */     request.removeAttribute("data");
/* 1280 */     this.mo.getReloadPeriod(request);
/* 1281 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(request.getParameter("resourceid"), Long.parseLong((String)request.getAttribute("reloadperiod")));
/*      */     
/* 1283 */     if (map != null)
/*      */     {
/* 1285 */       request.setAttribute("systeminfo", map);
/*      */     }
/*      */     
/* 1288 */     Properties status = getStatus(request.getParameter("resourceid"));
/* 1289 */     request.setAttribute("currentstatus", status);
/* 1290 */     ActionMessages messages = new ActionMessages();
/* 1291 */     ActionErrors errors = new ActionErrors();
/* 1292 */     ResultSet set = null;
/* 1293 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/* 1294 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1295 */     long maxtime = -1L;
/*      */     
/*      */     try
/*      */     {
/* 1299 */       String maxTimeQuery = "SELECT max(COLLECTIONTIME) from AM_WLI_MB ai,AM_ManagedObject amo,AM_PARENTCHILDMAPPER apc  where apc.CHILDID=ai.MSGBROKERID and amo.RESOURCEID=apc.PARENTID and amo.RESOURCEID=" + resID;
/* 1300 */       set = AMConnectionPool.executeQueryStmt(maxTimeQuery);
/*      */       
/* 1302 */       if (set.next())
/*      */       {
/* 1304 */         maxtime = set.getLong(1);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1311 */       closeResultSet(set);
/*      */     }
/*      */     
/* 1314 */     if (maxtime == 0L)
/*      */     {
/* 1316 */       return returnForward(mapping.getInputForward(), request);
/*      */     }
/*      */     
/* 1319 */     if (maxtime == -1L)
/*      */     {
/* 1321 */       request.setAttribute("note", "3");
/* 1322 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("wlogic.nodata"));
/* 1323 */       saveMessages(request, messages);
/* 1324 */       return mapping.getInputForward();
/*      */     }
/*      */     
/* 1327 */     ResultSet businessProcIdSet = null;
/*      */     try
/*      */     {
/* 1330 */       String businessProcIdQuery = "select RESOURCEID,RESOURCENAME from AM_ManagedObject amo, AM_PARENTCHILDMAPPER apc where apc.CHILDID=amo.RESOURCEID and apc.PARENTID=" + resID + " and amo.TYPE='MessageBroker'";
/* 1331 */       businessProcIdSet = AMConnectionPool.executeQueryStmt(businessProcIdQuery);
/* 1332 */       bpData = new ArrayList();
/* 1333 */       int childId; while (businessProcIdSet.next())
/*      */       {
/* 1335 */         childId = businessProcIdSet.getInt(1);
/* 1336 */         String resourceName = businessProcIdSet.getString(2);
/* 1337 */         String businessProcInfoQuery = "select * from AM_WLI_MB where MSGBROKERID=" + childId + " and COLLECTIONTIME=" + maxtime;
/* 1338 */         ResultSet bpmInfoSet = AMConnectionPool.executeQueryStmt(businessProcInfoQuery);
/*      */         try
/*      */         {
/* 1341 */           if (bpmInfoSet.next())
/*      */           {
/* 1343 */             Properties bpmProps = new Properties();
/*      */             
/*      */ 
/* 1346 */             bpmProps.put("MSGBROKERID", String.valueOf(bpmInfoSet.getInt("MSGBROKERID")));
/* 1347 */             bpmProps.put("CHANNELNAME", bpmInfoSet.getString("CHANNELNAME"));
/* 1348 */             bpmProps.put("MESSAGECOUNT", String.valueOf(bpmInfoSet.getInt("MESSAGECOUNTDIFF")));
/* 1349 */             bpmProps.put("DEADMESSAGECOUNT", String.valueOf(bpmInfoSet.getInt("DEADMESSAGECOUNTDIFF")));
/* 1350 */             bpmProps.put("SUBSCRIPTIONCOUNT", String.valueOf(bpmInfoSet.getInt("SUBSCRIPTIONCOUNTDIFF")));
/* 1351 */             bpData.add(bpmProps);
/*      */           }
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1356 */           exc.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1364 */       request.setAttribute("data", bpData);
/* 1365 */       return returnForward(mapping.findForward("wlogic.success"), request);
/*      */     }
/*      */     catch (Exception exc) {
/*      */       ArrayList bpData;
/* 1369 */       exc.printStackTrace();
/* 1370 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("wlogic.exception", exc.toString()));
/* 1371 */       saveErrors(request, errors);
/* 1372 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/* 1376 */       closeResultSet(businessProcIdSet);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\wlogic\actions\WlogicActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */