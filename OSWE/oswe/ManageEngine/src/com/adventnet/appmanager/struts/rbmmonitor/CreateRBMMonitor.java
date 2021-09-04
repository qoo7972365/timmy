/*     */ package com.adventnet.appmanager.struts.rbmmonitor;
/*     */ 
/*     */ import com.adventnet.agent.utilities.xml.XMLDataReader;
/*     */ import com.adventnet.agent.utilities.xml.XMLNode;
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.AgentUtil;
/*     */ import com.adventnet.appmanager.server.framework.CreateEUMMonitor;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.RBMMonitor;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.google.gson.Gson;
/*     */ import com.me.apm.server.selenium.datacollection.RealBrowserException;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateRBMMonitor
/*     */   extends DispatchAction
/*     */ {
/*     */   public static void main(String[] args) {}
/*     */   
/*     */   public int createMonitor(Properties props, Properties url_prop)
/*     */   {
/*  84 */     String monType = props.getProperty("monType");
/*  85 */     String agentList = props.getProperty("agentIds");
/*  86 */     long poll = Long.parseLong(props.getProperty("poll"));
/*  87 */     long time_out = Long.parseLong(props.getProperty("time_out"));
/*  88 */     String script = props.getProperty("script");
/*  89 */     ArrayList args = NewMonitorUtil.getArgsforConfMon(monType);
/*  90 */     Properties argsprops = new Properties();
/*  91 */     argsprops.put("ScriptName", String.valueOf(props.getProperty("script")));
/*  92 */     argsprops.put("updatedTime", String.valueOf(props.get("collectionTime")));
/*  93 */     argsprops.put("thinkTime", String.valueOf(props.get("thinkTime")));
/*  94 */     argsprops.put("pageLoadTime", String.valueOf(props.get("pageLoadTime")));
/*  95 */     argsprops.put("browserType", String.valueOf(props.get("browserType")));
/*  96 */     CreateEUMMonitor createMon = new CreateEUMMonitor(props);
/*  97 */     createMon.setArgs(args);
/*  98 */     createMon.setArgsprops(argsprops);
/*  99 */     int eumParentMOId = createMon.createEUMParent();
/*     */     
/* 101 */     String[] agentIds = agentList.split(",");
/*     */     
/* 103 */     for (String agent : agentIds) {
/* 104 */       int eumAgentId = 0;
/*     */       try {
/* 106 */         eumAgentId = Integer.parseInt(agent);
/* 107 */         if (eumAgentId < 10000000) {
/*     */           continue;
/*     */         }
/*     */       } catch (Exception e) {
/* 111 */         e.printStackTrace();
/* 112 */         continue;
/*     */       }
/*     */       try
/*     */       {
/* 116 */         createAgentLevelMO(createMon, eumAgentId, script, poll, time_out, url_prop);
/*     */       } catch (RealBrowserException e) {
/* 118 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 123 */     return -1;
/*     */   }
/*     */   
/*     */   public int createAgentLevelMO(CreateEUMMonitor createMonitor, int agentId, String script, long poll, long time_out, Properties url_prop) throws RealBrowserException
/*     */   {
/* 128 */     String agentName = null;
/*     */     try {
/* 130 */       agentName = AgentUtil.getAgentName(agentId);
/*     */     } catch (SQLException e3) {
/* 132 */       e3.printStackTrace();
/*     */     }
/*     */     
/* 135 */     if (agentName == null) {
/* 136 */       throw new RealBrowserException("Agent does not exist for id : " + agentId);
/*     */     }
/*     */     
/* 139 */     String resourceName = createMonitor.getResourceName();
/* 140 */     String resourceNameWithAgent = resourceName + "-" + agentName;
/* 141 */     Properties argsprops = createMonitor.getArgsprops();
/* 142 */     argsprops.put("ScriptName", script);
/* 143 */     createMonitor.setArgsprops(argsprops);
/* 144 */     createMonitor.setResourceNameWithAgent(resourceNameWithAgent);
/* 145 */     createMonitor.setAgentId(agentId);
/* 146 */     int resourceId = createMonitor.createEUMMOForAgent();
/*     */     
/* 148 */     Hashtable indexVsUrls = (Hashtable)url_prop.get("indexVsUrls");
/* 149 */     Hashtable indexVsDisplayNames = (Hashtable)url_prop.get("indexVsDisplayNames");
/* 150 */     Hashtable indexVsChild = (Hashtable)url_prop.get("indexVsChild");
/*     */     
/* 152 */     Vector urlIDs = new Vector();
/* 153 */     Vector urls = new Vector();
/* 154 */     Vector v = new Vector(indexVsUrls.keySet());
/* 155 */     Collections.sort(v);
/* 156 */     for (int i = 0; i < v.size(); i++)
/*     */     {
/*     */       try
/*     */       {
/* 160 */         int index = Integer.parseInt("" + v.elementAt(i));
/* 161 */         String url = (String)indexVsUrls.get(Integer.valueOf(index));
/* 162 */         String dispname = url;
/* 163 */         urls.addElement(url);
/* 164 */         if (indexVsDisplayNames.containsKey(Integer.valueOf(index)))
/*     */         {
/* 166 */           dispname = (String)indexVsDisplayNames.get(Integer.valueOf(index));
/*     */         }
/* 168 */         int stepId = createRBMStep(dispname, dispname, resourceId);
/* 169 */         urlIDs.addElement("" + stepId);
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 174 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 180 */       createMonitor.insertEUMParentChildTable(createMonitor.getParentId(), resourceId);
/*     */     } catch (SQLException e2) {
/* 182 */       e2.printStackTrace();
/*     */     }
/* 184 */     RBMMonitor r = new RBMMonitor(true);
/* 185 */     r.setMonitorDetails(resourceId, urlIDs, urls, script, poll, time_out);
/* 186 */     RBMMonitor.rIdVsScheduler.put("" + resourceId, r);
/*     */     try
/*     */     {
/* 189 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("insert into AM_RBMDATA(RESOURCEID,SCRIPT,AGENTID,TIMEOUT,CURRENTSTATUS,POLLINTERVAL) values(" + resourceId + ",'" + script + "'," + agentId + "," + time_out + ",0," + poll + ")");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 193 */       e.printStackTrace();
/*     */     }
/*     */     
/* 196 */     return -1;
/*     */   }
/*     */   
/*     */   public int createRBMStep(String stepName, String displayName, int parentId) {
/* 200 */     AMAttributesDependencyAdder attributesAdder = new AMAttributesDependencyAdder();
/* 201 */     int stepId = NewMonitorUtil.mocreate(displayName, stepName, "RBMStep");
/* 202 */     DBUtil.insertParentChildMapper(parentId, stepId);
/* 203 */     attributesAdder.addInterDependentAttributes(stepId);
/* 204 */     attributesAdder.addDependentAttributes(parentId, stepId);
/* 205 */     return stepId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward createrbm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 215 */     Properties props = new Properties();
/* 216 */     ResultSet rs = null;
/* 217 */     String haid = request.getParameter("haid");
/* 218 */     String displayName = "";
/*     */     try
/*     */     {
/* 221 */       String error = "";
/* 222 */       displayName = request.getParameter("displayname");
/*     */       
/* 224 */       String description = displayName;
/* 225 */       String scriptName = request.getParameter("scriptname");
/*     */       
/* 227 */       String[] agentstr = request.getParameterValues("selectedAgents");
/* 228 */       String agentId = "";
/* 229 */       for (int i = 0; i < agentstr.length; i++)
/*     */       {
/* 231 */         agentId = agentId + agentstr[i] + ",";
/*     */       }
/* 233 */       String pollInterval = request.getParameter("pollinterval");
/* 234 */       String timeout = request.getParameter("timeout");
/* 235 */       String pollnow = request.getParameter("pollnow");
/* 236 */       String parentId = request.getParameter("haid");
/* 237 */       Long collectionTime = Long.valueOf(System.currentTimeMillis());
/*     */       
/* 239 */       if (pollnow == null)
/*     */       {
/* 241 */         pollnow = "true";
/*     */       }
/* 243 */       System.out.println("Agents Name  : " + agentstr);
/* 244 */       if ((agentstr == null) || (agentstr.length < 1))
/*     */       {
/* 246 */         error = FormatUtil.getString("am.webclient.rbm.errormessage.selectagent");
/*     */       }
/* 248 */       else if ((displayName == null) || (displayName.equalsIgnoreCase("")))
/*     */       {
/* 250 */         error = FormatUtil.getString("am.webclient.rbm.errormessage.displaynameempty");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 256 */       else if ((scriptName == null) || (scriptName.equalsIgnoreCase("")))
/*     */       {
/* 258 */         error = FormatUtil.getString("am.webclient.rbm.errormessage.webscriptnotselect");
/*     */       }
/* 260 */       else if ((pollInterval == null) || (pollInterval.equalsIgnoreCase("")))
/*     */       {
/*     */ 
/* 263 */         error = FormatUtil.getString("am.webclient.rbm.errormessage.pollintervalempty");
/*     */       }
/* 265 */       long poll = 5L;
/*     */       try
/*     */       {
/* 268 */         poll = Long.parseLong(pollInterval) * 60L * 1000L;
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 273 */         error = FormatUtil.getString("am.webclient.rbm.errormessage.pollintervalnotnumeric");
/*     */       }
/* 275 */       long time_out = 10000L;
/*     */       try
/*     */       {
/* 278 */         time_out = Long.parseLong(timeout) * 60L * 1000L;
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 282 */         error = FormatUtil.getString("am.webclient.rbm.errormessage.timeoutnotnumeric");
/*     */       }
/*     */       
/* 285 */       String[] agents = agentstr;
/*     */       
/* 287 */       String agentName = "";
/* 288 */       ManagedApplication mo = new ManagedApplication();
/* 289 */       ResultSet rsd = null;
/* 290 */       for (int i = 0; i < agents.length;)
/*     */       {
/*     */         try
/*     */         {
/* 294 */           agentName = agents[i];
/* 295 */           ArrayList rows = mo.getRows("select RESOURCEID,AGENTID,SCRIPT from AM_RBMDATA where AGENTID=" + agentName + " and SCRIPT='" + scriptName + "'");
/*     */           
/* 297 */           if (rows.size() > 0)
/*     */           {
/* 299 */             String agtDispName = "";
/* 300 */             AMConnectionPool.getInstance();rsd = AMConnectionPool.executeQueryStmt("select DISPLAYNAME from AM_RBMAGENTDATA where AGENTID='" + agentName + "'");
/* 301 */             if (rsd.next())
/*     */             {
/* 303 */               agtDispName = rsd.getString(1);
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 308 */             error = error + FormatUtil.getString("am.webclient.rbm.errormessage.agentscriptalreadyexist", new String[] { agtDispName, scriptName });
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 317 */           if (rsd != null)
/*     */           {
/*     */             try
/*     */             {
/* 321 */               rsd.close();
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/* 290 */           i++;
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
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 313 */           e.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/* 317 */           if (rsd != null)
/*     */           {
/*     */             try
/*     */             {
/* 321 */               rsd.close();
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 331 */       props.setProperty("monType", "RBM");
/* 332 */       props.setProperty("resourceName", displayName);
/* 333 */       props.setProperty("displayName", displayName);
/* 334 */       props.setProperty("pollingInterval", pollInterval);
/* 335 */       props.setProperty("agentIds", agentId);
/* 336 */       props.setProperty("script", scriptName);
/* 337 */       props.setProperty("browserType", "3");
/* 338 */       props.put("thinkTime", "3");
/* 339 */       props.put("pageLoadTime", "10");
/* 340 */       props.put("collectionTime", collectionTime);
/* 341 */       props.put("poll", "" + poll);
/* 342 */       props.put("time_out", "" + time_out);
/*     */       
/* 344 */       Vector urls = new Vector();
/* 345 */       Hashtable indexVsUrls = new Hashtable();
/* 346 */       Hashtable indexVsDisplayNames = new Hashtable();
/* 347 */       Hashtable indexVsChild = new Hashtable();
/*     */       
/*     */       try
/*     */       {
/* 351 */         File f = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts" + File.separator + scriptName + File.separator + "PageProperties.dat");
/* 352 */         System.out.println("File : " + f.getPath());
/* 353 */         if (f.exists())
/*     */         {
/* 355 */           XMLDataReader reader = new XMLDataReader(f.getAbsolutePath(), false);
/* 356 */           XMLNode rootNode = reader.getRootNode();
/* 357 */           int index; Enumeration en; if (rootNode != null)
/*     */           {
/* 359 */             Vector childVec = rootNode.getChildNodes();
/* 360 */             index = 0;
/* 361 */             for (en = childVec.elements(); en.hasMoreElements();)
/*     */             {
/* 363 */               XMLNode childNode = (XMLNode)en.nextElement();
/* 364 */               String url = (String)childNode.getAttribute("url");
/* 365 */               String ind = (String)childNode.getAttribute("urlindex");
/* 366 */               String dName = (String)childNode.getAttribute("displayName");
/* 367 */               String child = (String)childNode.getAttribute("wintype");
/* 368 */               if ((child == null) || (!child.equalsIgnoreCase("child")))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/* 373 */                 if ((ind == null) || (ind.equalsIgnoreCase("")))
/*     */                 {
/* 375 */                   index = indexVsUrls.size();
/*     */                 }
/*     */                 try
/*     */                 {
/* 379 */                   index = Integer.parseInt(ind);
/*     */                 }
/*     */                 catch (Exception nfe)
/*     */                 {
/* 383 */                   index = indexVsUrls.size();
/*     */                 }
/* 385 */                 while (indexVsUrls.containsKey(Integer.valueOf(index)))
/*     */                 {
/* 387 */                   index += 1;
/*     */                 }
/*     */                 
/* 390 */                 indexVsUrls.put(Integer.valueOf(index), url);
/* 391 */                 if (child != null)
/*     */                 {
/* 393 */                   indexVsChild.put(Integer.valueOf(index), child);
/*     */                 }
/*     */                 else
/*     */                 {
/* 397 */                   indexVsChild.put(Integer.valueOf(index), "parent");
/*     */                 }
/*     */                 
/*     */ 
/* 401 */                 if ((dName == null) || (dName.equalsIgnoreCase("NONE")) || (dName.equalsIgnoreCase("")))
/*     */                 {
/* 403 */                   dName = url;
/*     */                 }
/* 405 */                 indexVsDisplayNames.put(Integer.valueOf(index), dName);
/*     */               }
/*     */               
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 412 */             System.out.println("RootNode null");
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 419 */           error = FormatUtil.getString("am.webclient.rbm.errormessage.nourlsexists", new String[] { scriptName });
/* 420 */           System.out.println("Script " + f.getPath() + "  not exists");
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 427 */         e.printStackTrace();
/*     */       }
/*     */       
/* 430 */       if (indexVsUrls.size() <= 0)
/*     */       {
/* 432 */         if (error.equalsIgnoreCase(""))
/*     */         {
/*     */ 
/* 435 */           error = FormatUtil.getString("am.webclient.rbm.errormessage.nourlsexists", new String[] { scriptName });
/*     */         }
/*     */       }
/*     */       
/* 439 */       Properties url_prop = new Properties();
/* 440 */       url_prop.put("urls", urls);
/* 441 */       url_prop.put("indexVsUrls", indexVsUrls);
/* 442 */       url_prop.put("indexVsDisplayNames", indexVsDisplayNames);
/* 443 */       url_prop.put("indexVsChild", indexVsChild);
/*     */       
/* 445 */       int resourceid = createMonitor(props, url_prop);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       String error;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       ActionMessages messages;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       ArrayList al1;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 476 */       return new ActionForward("/showresource.do?method=showResourceTypes&direct=true&network=RBM&detailspage=true");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 450 */       e.printStackTrace();
/* 451 */       error = e.getMessage();
/* 452 */       messages = new ActionMessages();
/* 453 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage(error));
/* 454 */       saveMessages(request, messages);
/* 455 */       al1 = new ArrayList();
/* 456 */       al1.add(displayName);
/* 457 */       al1.add("Failed");
/* 458 */       al1.add(error);
/* 459 */       request.setAttribute("discoverystatus", al1);
/* 460 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=RBM&restype=RBM&haid=" + haid);
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 467 */         if (rs != null)
/*     */         {
/* 469 */           rs.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean doLicenseCheck(HttpServletRequest request)
/*     */   {
/* 480 */     ActionMessages messages = new ActionMessages();
/* 481 */     ManagedApplication mo = new ManagedApplication();
/* 482 */     int numberofmonitors = Constants.getNoofMonitors_withoutnatives();
/* 483 */     int maxallowed = FreeEditionDetails.getFreeEditionDetails().getNumberOfMonitorsPermitted();
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
/* 505 */     String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 506 */     if ((maxallowed != -1) && (maxallowed <= numberofmonitors))
/*     */     {
/*     */ 
/* 509 */       if (usertype.equals("F"))
/*     */       {
/* 511 */         String m1 = FormatUtil.getString("freeedition.monitor.restriction1", new String[] { OEMUtil.getOEMString("product.name") });
/* 512 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(m1));
/* 513 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("freeedition.monitor.restriction2", String.valueOf(maxallowed)));
/* 514 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("freeedition.monitor.restriction3"));
/*     */       }
/*     */       else
/*     */       {
/* 518 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction1", String.valueOf(maxallowed)));
/* 519 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction2"));
/*     */       }
/* 521 */       saveMessages(request, messages);
/* 522 */       return false;
/*     */     }
/* 524 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void createUrlMonitor(int urlseqid, XMLNode node, int pollinterval)
/*     */     throws Exception
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward validateScript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 540 */     String outputData = request.getParameter("wpwebscripttxt");
/* 541 */     boolean scriptModified = false;
/* 542 */     if ((outputData == null) || (outputData.isEmpty()))
/*     */     {
/* 544 */       return null;
/*     */     }
/* 546 */     StringBuilder outputBuilder = new StringBuilder();
/* 547 */     List<String> linesList = Arrays.asList(outputData.split("\\r?\\n"));
/* 548 */     ArrayList<String> lines = new ArrayList(linesList);
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 554 */       int urlTakenFrom = 0;
/* 555 */       if ((lines.size() <= 2) || ((!((String)lines.get(2)).contains("launchApplication")) && (!((String)lines.get(2)).contains("changeURL"))))
/*     */       {
/* 557 */         for (int i = 0; i < lines.size(); i++)
/*     */         {
/* 559 */           String line = (String)lines.get(i);
/* 560 */           if (line.indexOf("# URL : ") == 0)
/*     */           {
/* 562 */             line = line.substring(line.indexOf("http") - 1, line.length());
/* 563 */             line = "launchApplication(" + line + ")";
/* 564 */             lines.add(2, line);
/* 565 */             scriptModified = true;
/* 566 */             urlTakenFrom = i;
/* 567 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 573 */       if (scriptModified)
/*     */       {
/* 575 */         lines.add("# This script has been modified and url taken from line no : " + (urlTakenFrom + 2));
/* 576 */         outputBuilder.append(StringUtils.join(lines.toArray(), System.getProperty("line.separator")));
/* 577 */         outputData = outputBuilder.toString();
/* 578 */         AMLog.info("CreateRBMMonitor.validateScript() modified outputData : " + outputData);
/*     */       }
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 583 */       e1.printStackTrace();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 588 */       response.setContentType("application/json");
/* 589 */       response.setCharacterEncoding("UTF-8");
/* 590 */       Map<String, String> resultMap = new HashMap();
/* 591 */       resultMap.put("result", (outputData.contains("launchApplication")) || (outputData.contains("changeURL")) ? "true" : "false");
/* 592 */       resultMap.put("value", outputData);
/* 593 */       response.getWriter().write(new Gson().toJson(resultMap));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 597 */       e.printStackTrace();
/*     */     }
/* 599 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\rbmmonitor\CreateRBMMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */