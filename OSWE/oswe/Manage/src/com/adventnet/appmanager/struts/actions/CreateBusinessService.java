/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObjectDao;
/*      */ import com.adventnet.appmanager.util.AppManagerUtil;
/*      */ import com.adventnet.appmanager.util.BSIntegUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MGActionNotifier;
/*      */ import com.adventnet.appmanager.util.SANUtil;
/*      */ import com.adventnet.appmanager.utils.client.MapViewUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import java.io.File;
/*      */ import java.security.Principal;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.action.ActionServlet;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class CreateBusinessService
/*      */   extends DispatchAction
/*      */ {
/*   74 */   private ManagedApplication mo = new ManagedApplication();
/*   75 */   MGActionNotifier notifyConsole = MGActionNotifier.getInstance();
/*      */   
/*   77 */   Vector selectedNwd = new Vector();
/*   78 */   Vector selectedServ = new Vector();
/*      */   
/*   80 */   Vector selectedSAN = new Vector();
/*      */   
/*   82 */   boolean isEdit = false;
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward handleBSCreationAndModification(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   89 */     String applicationName = request.getParameter("name");
/*   90 */     String parentHaid = request.getParameter("parentHaid");
/*   91 */     String buttonVal = request.getParameter("buttonType1");
/*   92 */     String bsId = request.getParameter("haid");
/*      */     
/*   94 */     String fromMapview = (String)request.getAttribute("fromMapview");
/*   95 */     if ("true".equalsIgnoreCase(fromMapview))
/*      */     {
/*   97 */       applicationName = (String)request.getAttribute("name");
/*   98 */       AMLog.debug("Application name : " + applicationName);
/*   99 */       parentHaid = (String)request.getAttribute("parentHaid");
/*  100 */       bsId = (String)request.getAttribute("haid");
/*  101 */       buttonVal = "";
/*      */     }
/*      */     
/*  104 */     applicationName = applicationName.trim();
/*      */     
/*  106 */     boolean isNextButton = true;
/*  107 */     AMLog.debug("handleBSCreationAndModification method : applicationName - " + applicationName + " , bsId -" + bsId);
/*      */     
/*      */ 
/*  110 */     Hashtable toNotifier = null;
/*  111 */     if (this.notifyConsole.shouldNotify())
/*      */     {
/*  113 */       toNotifier = new Hashtable();
/*  114 */       toNotifier.put("MGNAME", applicationName);
/*  115 */       toNotifier.put("MGID", bsId);
/*  116 */       toNotifier.put("DISPLAYNAME", applicationName);
/*      */       
/*  118 */       String oldMgname = DBUtil.getDisplaynameforResourceID(bsId);
/*  119 */       if ((oldMgname != null) && (!oldMgname.equals(applicationName)))
/*      */       {
/*      */         try
/*      */         {
/*  123 */           toNotifier.put("action", "Rename");
/*  124 */           boolean isSubGroup = Constants.isSubGroup(bsId);
/*  125 */           String parentGrpNames = BSIntegUtil.getTOPLevelAPMMGsNames("", bsId);
/*  126 */           String oldIntegNamePattern = oldMgname;
/*  127 */           if (isSubGroup)
/*      */           {
/*  129 */             parentGrpNames = parentGrpNames.substring(0, parentGrpNames.length() - 1);
/*  130 */             toNotifier.put("MGNAME", applicationName + "-" + parentGrpNames);
/*  131 */             oldIntegNamePattern = oldIntegNamePattern + "-" + parentGrpNames;
/*      */           }
/*      */           else
/*      */           {
/*  135 */             toNotifier.put("MGNAME", applicationName);
/*      */           }
/*  137 */           toNotifier.put("oldIntegNamePattern", oldIntegNamePattern);
/*  138 */           ExtProdUtil.doBVNotificationToOPM(toNotifier, request, bsId);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  142 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  147 */     if (buttonVal.equals("finishButton"))
/*      */     {
/*  149 */       isNextButton = false;
/*      */     }
/*      */     
/*  152 */     ActionMessages messages = new ActionMessages();
/*  153 */     ActionErrors errors = new ActionErrors();
/*      */     
/*  155 */     if ((!EnterpriseUtil.isAdminServer) || (Integer.parseInt(bsId) <= EnterpriseUtil.RANGE))
/*      */     {
/*  157 */       int conflictKey = checkForNameConflict(bsId, parentHaid, applicationName);
/*  158 */       if (conflictKey != 0)
/*      */       {
/*  160 */         return returnNameConflict(conflictKey, bsId, parentHaid, applicationName, request);
/*      */       }
/*      */     }
/*      */     
/*  164 */     if (!bsId.equals("-1"))
/*      */     {
/*  166 */       return modifyBS(mapping, request, isNextButton, messages, errors, toNotifier);
/*      */     }
/*      */     
/*      */ 
/*  170 */     return createBS(mapping, request, isNextButton, messages, errors);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward createBS(ActionMapping mapping, HttpServletRequest request, boolean isNextButton, ActionMessages messages, ActionErrors errors)
/*      */   {
/*  177 */     String applicationName = request.getParameter("name");
/*  178 */     String description = request.getParameter("description");
/*  179 */     String parentHaid = request.getParameter("parentHaid");
/*  180 */     String username = request.getUserPrincipal().getName();
/*  181 */     String locationID = request.getParameter("locationid");
/*  182 */     String bsId = request.getParameter("haid");
/*  183 */     String buttonVal = request.getParameter("buttonType1");
/*  184 */     String createMV = request.getParameter("createMV");
/*  185 */     AMLog.debug("Create mapview : " + createMV);
/*      */     
/*  187 */     String fromMapview = (String)request.getAttribute("fromMapview");
/*  188 */     if ("true".equalsIgnoreCase(fromMapview))
/*      */     {
/*  190 */       applicationName = (String)request.getAttribute("name");
/*  191 */       description = FormatUtil.getString("am.webclient.createbusinessservice.mapview");
/*  192 */       parentHaid = (String)request.getAttribute("parentHaid");
/*  193 */       bsId = (String)request.getAttribute("haid");
/*  194 */       buttonVal = "";
/*      */     }
/*  196 */     applicationName = applicationName.trim();
/*  197 */     if (createMV != null)
/*      */     {
/*  199 */       request.getSession().setAttribute("createMV", createMV);
/*      */     }
/*  201 */     String siteId = "-1";
/*  202 */     String custId = "-1";
/*      */     try
/*      */     {
/*  205 */       AMManagedObject ammo = null;
/*      */       
/*  207 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  209 */         custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/*  210 */         if (parentHaid.equals("-1"))
/*      */         {
/*  212 */           parentHaid = custId;
/*      */         }
/*      */       }
/*      */       
/*  216 */       if (parentHaid.equals("-1"))
/*      */       {
/*  218 */         AMLog.debug("CreateBusinessService : About to add the business service with ID : " + bsId + " and name : " + applicationName);
/*  219 */         ammo = this.mo.createManagedApplication(applicationName, description, username, null, null);
/*      */       }
/*      */       else
/*      */       {
/*  223 */         AMLog.debug("CreateBusinessService : About to add the SubGroup for ID : " + parentHaid + " and name : " + applicationName);
/*  224 */         ammo = this.mo.createManagedApplication(applicationName, description, username, null, null, true);
/*      */       }
/*  226 */       if (ammo != null)
/*      */       {
/*      */ 
/*  229 */         int haid = ammo.getRESOURCEID();
/*      */         try
/*      */         {
/*  232 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  233 */           AMConnectionPool.executeUpdateStmt("insert into AM_MinMaxAvgData (ARCHIVEDTIME, RESID, DURATION, ATTRIBUTEID, MINVALUE, MAXVALUE, TOTAL, TOTALCOUNT) values('" + System.currentTimeMillis() + "', '" + haid + "', '-1','17','0','0','0','1')");
/*      */         }
/*      */         catch (Exception exp)
/*      */         {
/*  237 */           exp.printStackTrace();
/*      */         }
/*      */         
/*      */         try
/*      */         {
/*  242 */           if ((locationID != null) && (!locationID.equals("")))
/*      */           {
/*  244 */             int loc = Integer.parseInt(locationID);
/*  245 */             String query = "insert into AM_GMapCountryResourceRel values(" + haid + "," + loc + ")";
/*  246 */             AMConnectionPool.executeUpdateStmt(query);
/*  247 */             AMLog.debug("***************Associated the location. Location ID " + loc);
/*      */           }
/*      */         }
/*      */         catch (NumberFormatException e)
/*      */         {
/*  252 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*  256 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  258 */           String act1 = null;
/*  259 */           String app_type = null;
/*  260 */           DBUtil.insertParentChildMapper(Integer.parseInt(parentHaid), haid);
/*  261 */           if (parentHaid.equals(custId))
/*      */           {
/*      */ 
/*  264 */             app_type = "BSG0";
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  269 */             app_type = "BSG1";
/*      */           }
/*  271 */           act1 = " INSERT INTO AM_HOLISTICAPPLICATION_EXT(RESOURCEID, APP_TYPE) VALUES (" + haid + ", '" + app_type + "')";
/*  272 */           AMConnectionPool.executeUpdateStmt(act1);
/*  273 */           AMLog.debug("Added the BSG : " + haid + " as a subgroup to parent : " + parentHaid + " with app_type in AM_HOLISTICAPPLICATION_EXT as : " + app_type);
/*  274 */           EnterpriseUtil.addUpdateQueryToFile(act1);
/*  275 */           request.setAttribute("custId", custId);
/*  276 */           Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/*  277 */           siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */ 
/*      */         }
/*  280 */         else if (!parentHaid.equals("-1"))
/*      */         {
/*  282 */           addSubGroup(String.valueOf(haid), parentHaid);
/*      */         }
/*      */         
/*  285 */         if ((Constants.isIt360) && (!"true".equalsIgnoreCase(fromMapview)) && (buttonVal.equals("finishButton")))
/*      */         {
/*  287 */           boolean isSubgroup = false;
/*  288 */           if (!parentHaid.equals("-1"))
/*      */           {
/*  290 */             isSubgroup = MapViewUtil.isSubgroup(Integer.toString(haid));
/*      */           }
/*  292 */           String mapViewId = MapViewUtil.getMapViewIdForBSGId(parentHaid);
/*      */           
/*  294 */           if (("true".equalsIgnoreCase(createMV)) || ((createMV == null) && (isSubgroup) && (mapViewId != null)))
/*      */           {
/*      */             try
/*      */             {
/*  298 */               AMLog.debug("Creating mapview for BSG " + haid);
/*  299 */               MapViewUtil.createMVForBsg(Integer.toString(haid), username);
/*  300 */               AMLog.debug("Created !!");
/*      */               
/*  302 */               request.getSession().setAttribute("createMV", "false");
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/*  306 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  311 */         request.setAttribute("haid", String.valueOf(haid));
/*  312 */         request.setAttribute("creationdate", String.valueOf(System.currentTimeMillis()));
/*  313 */         String[] owners = request.getParameterValues("selectedowners_list");
/*  314 */         if (owners != null)
/*      */         {
/*  316 */           PreparedStatement ps = null;
/*      */           try
/*      */           {
/*  319 */             ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_HOLISTICAPPLICATION_OWNERS values(?,?)");
/*  320 */             for (int i = 0; i < owners.length; i++)
/*      */             {
/*  322 */               ps.setInt(1, haid);
/*  323 */               ps.setLong(2, Long.parseLong(owners[i]));
/*  324 */               ps.addBatch();
/*      */             }
/*  326 */             ps.executeBatch();
/*      */             
/*      */ 
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
/*  340 */               if (ps != null)
/*      */               {
/*  342 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/*  347 */               ee.printStackTrace();
/*      */             }
/*      */             
/*      */ 
/*  351 */             path = "/createBS.do?method=getNetworkDevices&productName=OpManager&devtype=All&haid=" + haid + "&applicationName=" + applicationName + "&parentHaid=" + parentHaid + CustomerManagementAPI.appendSiteInRequestParam(siteId);
/*      */           }
/*      */           catch (SQLException se)
/*      */           {
/*  330 */             AMLog.warning("Exception while executing query : " + se);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  334 */             e.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/*  340 */               if (ps != null)
/*      */               {
/*  342 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/*  347 */               ee.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         String path;
/*  352 */         if (!"true".equalsIgnoreCase(fromMapview))
/*      */         {
/*  354 */           Properties applications = (Properties)this.servlet.getServletConfig().getServletContext().getAttribute("applications");
/*  355 */           applications.setProperty(String.valueOf(haid), applicationName);
/*  356 */           if (!isNextButton)
/*      */           {
/*  358 */             path = mapping.findForward("showBusinessService").getPath() + "&haid=" + haid;
/*      */           }
/*  360 */           AMLog.debug("int to the return1 - " + path);
/*  361 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.admin.success"));
/*  362 */           if (applicationName.length() > 28)
/*      */           {
/*  364 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.success.exceedslength", getTrimmedText(applicationName, 25)));
/*      */           }
/*  366 */           saveMessages(request, messages);
/*  367 */           AMLog.debug("int to the return2 - " + path);
/*      */         }
/*      */         try
/*      */         {
/*  371 */           new AMRCAnalyser().applyRCA(haid, 17, System.currentTimeMillis(), true, true, 1);
/*  372 */           new AMRCAnalyser().applyRCA(haid, 18, System.currentTimeMillis(), true, false, 2);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  376 */           e.printStackTrace();
/*      */         }
/*  378 */         if ("true".equalsIgnoreCase(fromMapview))
/*      */         {
/*  380 */           request.setAttribute("bsgId", Integer.valueOf(haid));
/*  381 */           return null;
/*      */         }
/*      */         
/*      */ 
/*  385 */         return new ActionForward(path, true);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  391 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", "Unable to create application"));
/*  392 */       saveErrors(request, errors);
/*  393 */       return mapping.getInputForward();
/*      */ 
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*  398 */       int errorcode = se.getErrorCode();
/*  399 */       if (errorcode == 1062)
/*      */       {
/*  401 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("am.webclient.bs.name.exists.txt", applicationName));
/*      */       }
/*      */       else
/*      */       {
/*  405 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", se.toString()));
/*      */       }
/*  407 */       saveErrors(request, errors);
/*  408 */       return mapping.getInputForward();
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  413 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", ee.toString()));
/*  414 */       saveErrors(request, errors); }
/*  415 */     return mapping.getInputForward();
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward modifyBS(ActionMapping mapping, HttpServletRequest request, boolean isNextButton, ActionMessages messages, ActionErrors errors, Hashtable toNotifier)
/*      */   {
/*  421 */     String applicationName = request.getParameter("name").trim();
/*  422 */     String description = request.getParameter("description");
/*  423 */     String parentHaid = request.getParameter("parentHaid");
/*  424 */     String username = request.getUserPrincipal().getName();
/*  425 */     String locationID = request.getParameter("locationid");
/*  426 */     String buttonVal = request.getParameter("buttonType1");
/*  427 */     String bsId = request.getParameter("haid");
/*  428 */     String createMV = request.getParameter("createMV");
/*  429 */     AMLog.debug("Create Mapview : " + createMV);
/*      */     
/*  431 */     if (createMV != null)
/*      */     {
/*  433 */       request.getSession().setAttribute("createMV", createMV);
/*      */     }
/*      */     
/*  436 */     String siteId = "-1";
/*      */     
/*      */ 
/*      */ 
/*  440 */     AMLog.debug("CreateBusinessService : About to edit the business service with ID : " + bsId + " and name : " + applicationName);
/*  441 */     messages = new ActionMessages();
/*  442 */     errors = new ActionErrors();
/*      */     try
/*      */     {
/*  445 */       AMManagedObject ammo = new AMManagedObject(Integer.parseInt(bsId));
/*  446 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/*  447 */       if (ammo != null)
/*      */       {
/*  449 */         ArrayList lists = this.mo.getRows("select RESOURCEID from AM_ManagedObject where AM_ManagedObject.TYPE='HAI' AND AM_ManagedObject.RESOURCENAME='" + applicationName + "' AND RESOURCEID NOT IN(" + bsId + ")");
/*  450 */         if (lists.size() > 0)
/*      */         {
/*  452 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("haid.applicationcreation.namealreadyexists", applicationName));
/*  453 */           saveMessages(request, messages);
/*  454 */           return new ActionForward("/jsp/CreateApplication.jsp");
/*      */         }
/*      */         
/*  457 */         ammo.setRESOURCENAME(applicationName);
/*  458 */         ammo.setDESCRIPTION(description);
/*  459 */         ammo.setType("HAI");
/*  460 */         ammo.setDISPLAYNAME(applicationName);
/*  461 */         dao.update(ammo);
/*      */         
/*  463 */         Properties applications = (Properties)this.servlet.getServletConfig().getServletContext().getAttribute("applications");
/*  464 */         applications.setProperty(String.valueOf(bsId), applicationName);
/*      */         
/*  466 */         String creationdate = new Date(System.currentTimeMillis()).toString();
/*  467 */         String updatequery = "update AM_HOLISTICAPPLICATION set OWNER='admin',MODIFIEDDATE =" + System.currentTimeMillis() + "  where  HAID=" + bsId;
/*  468 */         this.mo.executeUpdateStmt(updatequery);
/*  469 */         EnterpriseUtil.addUpdateQueryToFile(updatequery);
/*  470 */         String tempString = description;
/*  471 */         if (tempString == null)
/*      */         {
/*  473 */           tempString = " ";
/*      */         }
/*  475 */         if (tempString.indexOf("'") != -1)
/*      */         {
/*  477 */           tempString = FormatUtil.findReplace(tempString, "'", " ");
/*      */         }
/*  479 */         if (tempString.indexOf("\n") != -1)
/*      */         {
/*  481 */           String tokenString = "";
/*  482 */           StringTokenizer token = new StringTokenizer(tempString, "\n");
/*  483 */           while (token.hasMoreTokens())
/*      */           {
/*  485 */             String tempToketString = token.nextToken();
/*  486 */             tempToketString = tempToketString.trim();
/*  487 */             tokenString = tokenString + " " + tempToketString;
/*      */           }
/*  489 */           tempString = tokenString;
/*      */         }
/*  491 */         EnterpriseUtil.addUpdateQueryToFile("update AM_ManagedObject set RESOURCENAME='" + applicationName + "',DISPLAYNAME='" + applicationName + "',DESCRIPTION='" + tempString + "' where RESOURCEID=" + bsId);
/*      */         
/*  493 */         ResultSet rs = null;
/*      */         try
/*      */         {
/*  496 */           int loc = Integer.parseInt(locationID);
/*  497 */           String isExistQuery = "select LOCATIONID from AM_GMapCountryResourceRel where ID=" + bsId;
/*  498 */           rs = AMConnectionPool.executeQueryStmt(isExistQuery);
/*  499 */           String query = "";
/*  500 */           if (rs.next())
/*      */           {
/*  502 */             query = "update AM_GMapCountryResourceRel set LOCATIONID =" + loc + " where ID= " + bsId;
/*      */           }
/*      */           else
/*      */           {
/*  506 */             query = "insert into AM_GMapCountryResourceRel values(" + bsId + "," + loc + ")";
/*      */           }
/*  508 */           AMConnectionPool.executeUpdateStmt(query);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           try
/*      */           {
/*  529 */             if (rs != null)
/*      */             {
/*  531 */               rs.close();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}
/*      */           
/*      */           String query1;
/*      */           
/*      */           String update;
/*  539 */           if (!Constants.isIt360) {
/*      */             break label1067;
/*      */           }
/*      */         }
/*      */         catch (NumberFormatException e)
/*      */         {
/*  512 */           query1 = "delete from AM_GMapCountryResourceRel where ID=" + bsId;
/*  513 */           AMLog.debug("No location or Invalid location specified.");
/*  514 */           AMConnectionPool.executeUpdateStmt(query1);
/*  515 */           EnterpriseUtil.addUpdateQueryToFile(query1);
/*  516 */           AMLog.debug("Deleted from the AM_GMapCountryResourceRel table");
/*      */         }
/*      */         catch (SQLException e)
/*      */         {
/*  520 */           update = "update AM_GMapCountryResourceRel set LOCATIONID =" + Integer.parseInt(locationID) + " where ID= " + bsId;
/*  521 */           AMLog.debug(update);
/*  522 */           AMConnectionPool.executeUpdateStmt(update);
/*  523 */           EnterpriseUtil.addUpdateQueryToFile(update);
/*      */         }
/*      */         finally
/*      */         {
/*      */           try
/*      */           {
/*  529 */             if (rs != null)
/*      */             {
/*  531 */               rs.close();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  541 */         String mapViewId = MapViewUtil.getMapViewIdForBSGId(bsId);
/*  542 */         if ((buttonVal.equals("finishButton")) && ("true".equalsIgnoreCase(createMV)))
/*      */         {
/*      */           try
/*      */           {
/*  546 */             AMLog.debug("Creating Mapviews for BSG " + bsId + " and its subgroups");
/*  547 */             MapViewUtil.createMVForBsgAndSubgroups(bsId, username);
/*  548 */             AMLog.debug("Created !!");
/*      */             
/*  550 */             request.getSession().setAttribute("createMV", "false");
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  554 */             e.printStackTrace();
/*      */           }
/*      */         }
/*  557 */         else if ((createMV == null) && (mapViewId != null))
/*      */         {
/*  559 */           MapViewUtil.editMapViewName(Integer.parseInt(mapViewId), applicationName);
/*  560 */           AMLog.debug("Modified the Mapview name for MV Id " + mapViewId + " to " + applicationName);
/*      */         }
/*      */         
/*      */         label1067:
/*  564 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  566 */           String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/*  567 */           if (!custId.equals("-1"))
/*      */           {
/*  569 */             Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/*  570 */             siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */           }
/*      */         }
/*  573 */         String query = "select AM_UserPasswordTable.USERID from AM_UserPasswordTable Inner Join AM_HOLISTICAPPLICATION_OWNERS on AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID  where AM_HOLISTICAPPLICATION_OWNERS.HAID=" + bsId;
/*  574 */         if (Constants.isUserResourceEnabled()) {
/*  575 */           query = "select AM_HOLISTICAPPLICATION_OWNERS.OWNERID from AM_HOLISTICAPPLICATION_OWNERS where AM_HOLISTICAPPLICATION_OWNERS.HAID=" + bsId;
/*      */         }
/*  577 */         Vector oldOwnerIds = ExtProdUtil.getQueryResultInVector(query);
/*  578 */         if (!EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  580 */           this.mo.executeUpdateStmt("delete from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + bsId);
/*      */         }
/*  582 */         String[] owners = request.getParameterValues("selectedowners_list");
/*  583 */         if ((owners != null) && (!owners.equals("null")))
/*      */         {
/*  585 */           boolean isOwnersChanged = false;
/*  586 */           if ((oldOwnerIds != null) && (!oldOwnerIds.isEmpty()))
/*      */           {
/*  588 */             List newOwnersList = Arrays.asList(owners);
/*  589 */             if ((owners.length != oldOwnerIds.size()) || (!oldOwnerIds.containsAll(newOwnersList)))
/*      */             {
/*  591 */               isOwnersChanged = true;
/*      */             }
/*      */           }
/*  594 */           AMLog.debug("isOwnersChanged? " + isOwnersChanged);
/*  595 */           PreparedStatement ps = null;
/*      */           try
/*      */           {
/*  598 */             ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_HOLISTICAPPLICATION_OWNERS values(?,?)");
/*  599 */             for (int i = 0; i < owners.length; i++)
/*      */             {
/*  601 */               ps.setInt(1, Integer.parseInt(bsId));
/*  602 */               ps.setLong(2, Long.parseLong(owners[i]));
/*  603 */               ps.addBatch();
/*      */             }
/*  605 */             ps.executeBatch();
/*      */             
/*      */ 
/*      */ 
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
/*  620 */               if (ps != null)
/*      */               {
/*  622 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/*  627 */               ee.printStackTrace();
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  632 */             if (!this.notifyConsole.shouldNotify()) {
/*      */               break label1518;
/*      */             }
/*      */           }
/*      */           catch (SQLException se)
/*      */           {
/*  609 */             se.printStackTrace();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  613 */             e.printStackTrace();
/*      */ 
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/*  620 */               if (ps != null)
/*      */               {
/*  622 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/*  627 */               ee.printStackTrace();
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  634 */           if (isOwnersChanged) {
/*      */             try
/*      */             {
/*  637 */               ExtProdUtil.doBVNotificationToOPM(toNotifier, request, bsId);
/*      */             } catch (Exception ex) {
/*  639 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         label1518:
/*  644 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationupdation.success"));
/*  645 */         saveMessages(request, messages);
/*      */         
/*  647 */         String path = "/createBS.do?method=getNetworkDevices&productName=OpManager&devtype=All&haid=" + bsId + "&applicationName=" + applicationName + "&parentHaid=" + parentHaid + CustomerManagementAPI.appendSiteInRequestParam(siteId);
/*  648 */         if (!isNextButton)
/*      */         {
/*  650 */           path = mapping.findForward("showBusinessService").getPath() + "&haid=" + bsId;
/*      */         }
/*  652 */         AMLog.debug("int to the return1 - " + path);
/*  653 */         if (applicationName.length() > 28)
/*      */         {
/*  655 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.success.exceedslength", getTrimmedText(applicationName, 25)));
/*      */         }
/*  657 */         saveMessages(request, messages);
/*  658 */         AMLog.debug("int to the return2 - " + path);
/*  659 */         AMLog.debug("CreateBusinessService : Successfully updated the business service : " + bsId);
/*      */         try
/*      */         {
/*  662 */           new AMRCAnalyser().applyRCA(Integer.parseInt(bsId), 17, System.currentTimeMillis(), true, true, 1);
/*  663 */           new AMRCAnalyser().applyRCA(Integer.parseInt(bsId), 18, System.currentTimeMillis(), true, false, 2);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  667 */           e.printStackTrace();
/*      */         }
/*  669 */         return new ActionForward(path, true);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  674 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed"));
/*  675 */       saveErrors(request, errors);
/*  676 */       return mapping.getInputForward();
/*      */ 
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*  681 */       int errorcode = se.getErrorCode();
/*  682 */       if (errorcode == 1062)
/*      */       {
/*  684 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("am.webclient.bs.name.exists.txt", applicationName));
/*      */       }
/*      */       else
/*      */       {
/*  688 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", se.toString()));
/*      */       }
/*  690 */       saveErrors(request, errors);
/*  691 */       return mapping.getInputForward();
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  696 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", ee.toString()));
/*  697 */       saveErrors(request, errors); }
/*  698 */     return mapping.getInputForward();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getNetworkDevices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  707 */     String parentHaid = request.getParameter("parentHaid");
/*  708 */     String prodName = request.getParameter("productName");
/*  709 */     String type = request.getParameter("devtype");
/*  710 */     String devName = request.getParameter("devname");
/*  711 */     String isPopup = request.getParameter("isPopup");
/*  712 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  713 */     int prodId = -1;
/*  714 */     String haid = request.getParameter("haid");
/*  715 */     String host = new String();
/*  716 */     String port = new String();
/*  717 */     String protocol = new String();
/*  718 */     String urlToContact = new String();
/*  719 */     String userName = new String();
/*  720 */     String password = new String();
/*  721 */     String pollingInterval = new String();
/*  722 */     String checkBox = new String();
/*  723 */     String byPassAuth = new String();
/*  724 */     AMLog.debug("CreateBusinessService : About to get Network Devices for business service : " + haid + " for type : " + type);
/*      */     
/*  726 */     String query = "select PROTOCOL,HOST,PORT,ID,URL,USERNAME,POLLINGINTERVAL,STATUS,BYPASSAUTH from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME='" + prodName + "'";
/*  727 */     ResultSet set1 = AMConnectionPool.executeQueryStmt(query);
/*  728 */     if (set1.next())
/*      */     {
/*  730 */       prodId = set1.getInt("ID");
/*  731 */       host = set1.getString("HOST");
/*  732 */       port = set1.getString("PORT");
/*  733 */       protocol = set1.getString("PROTOCOL");
/*  734 */       urlToContact = set1.getString("URL");
/*  735 */       userName = set1.getString("USERNAME");
/*      */       
/*  737 */       pollingInterval = set1.getString("POLLINGINTERVAL");
/*  738 */       checkBox = set1.getString("STATUS");
/*  739 */       byPassAuth = set1.getString("BYPASSAUTH");
/*      */     }
/*  741 */     ActionMessages messages = new ActionMessages();
/*  742 */     saveMessages(request, messages);
/*  743 */     String devicetoconfigure = new String();
/*      */     
/*  745 */     String siteId = "-1";
/*  746 */     HashMap filterprop = new HashMap();
/*  747 */     String siteFilterCondn = "";
/*  748 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/*  750 */       String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/*  751 */       if (!custId.equals("-1"))
/*      */       {
/*  753 */         Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/*  754 */         siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */       }
/*  756 */       filterprop.put("siteId", siteId);
/*  757 */       filterprop.put("excludeInterfaces", Boolean.valueOf(false));
/*  758 */       siteFilterCondn = " and " + CustomerManagementAPI.getCondition("AM_ManagedObject.RESOURCEID", CustomerManagementAPI.filterResourceIds(filterprop));
/*  759 */       if ((devName != null) && (!devName.equals("null")))
/*      */       {
/*  761 */         devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID, SiteInfo.SITENAME from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join SiteInfo on AM_PARENTCHILDMAPPER.PARENTID=SiteInfo.SITEID join AM_AssociatedExtDevices on AM_AssociatedExtDevices.PRODUCTID = ExternalDeviceDetails.PRODUCTID where AM_AssociatedExtDevices.RESID= AM_ManagedObject.RESOURCEID and ExternalDeviceDetails.CATEGORY not in('OpManager-WAN RTT Monitor','OpManager-VoIP Monitor') and ExternalDeviceDetails.CATEGORY = '" + type + "'  and ExternalDeviceDetails.DISPLAYNAME = '" + devName + "' " + SANUtil.getExcludeSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true) + siteFilterCondn;
/*      */       }
/*  763 */       else if ((type != null) && (!type.equals("null")) && (!type.equals("All")))
/*      */       {
/*  765 */         devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID, SiteInfo.SITENAME from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join SiteInfo on AM_PARENTCHILDMAPPER.PARENTID=SiteInfo.SITEID join AM_AssociatedExtDevices on AM_AssociatedExtDevices.PRODUCTID = ExternalDeviceDetails.PRODUCTID where AM_AssociatedExtDevices.RESID= AM_ManagedObject.RESOURCEID and ExternalDeviceDetails.CATEGORY not in('OpManager-WAN RTT Monitor','OpManager-VoIP Monitor') and ExternalDeviceDetails.CATEGORY = '" + type + "' " + SANUtil.getExcludeSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true) + siteFilterCondn;
/*      */       }
/*      */       else
/*      */       {
/*  769 */         devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID, SiteInfo.SITENAME from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join SiteInfo on AM_PARENTCHILDMAPPER.PARENTID=SiteInfo.SITEID join AM_AssociatedExtDevices on AM_AssociatedExtDevices.PRODUCTID = ExternalDeviceDetails.PRODUCTID where AM_AssociatedExtDevices.RESID= AM_ManagedObject.RESOURCEID and ExternalDeviceDetails.CATEGORY not in('OpManager-WAN RTT Monitor','OpManager-VoIP Monitor') and ExternalDeviceDetails.CATEGORY NOT in ('OpManager-Interface') " + SANUtil.getExcludeSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true) + siteFilterCondn;
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*  774 */     else if ((devName != null) && (!devName.equals("null")))
/*      */     {
/*  776 */       devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME join AM_AssociatedExtDevices on AM_AssociatedExtDevices.PRODUCTID = ExternalDeviceDetails.PRODUCTID where AM_AssociatedExtDevices.RESID= AM_ManagedObject.RESOURCEID and ExternalDeviceDetails.CATEGORY='" + type + "' and ExternalDeviceDetails.DISPLAYNAME = '" + devName + "'" + SANUtil.getExcludeSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true);
/*      */     }
/*  778 */     else if ((type != null) && (!type.equals("null")) && (!type.equals("All")))
/*      */     {
/*  780 */       devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME join AM_AssociatedExtDevices on AM_AssociatedExtDevices.PRODUCTID = ExternalDeviceDetails.PRODUCTID where AM_AssociatedExtDevices.RESID= AM_ManagedObject.RESOURCEID and ExternalDeviceDetails.CATEGORY not in('OpManager-WAN RTT Monitor','OpManager-VoIP Monitor') and ExternalDeviceDetails.CATEGORY='" + type + "'" + SANUtil.getExcludeSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true);
/*      */     }
/*      */     else
/*      */     {
/*  784 */       devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME join AM_AssociatedExtDevices on AM_AssociatedExtDevices.PRODUCTID = ExternalDeviceDetails.PRODUCTID where AM_AssociatedExtDevices.RESID= AM_ManagedObject.RESOURCEID and ExternalDeviceDetails.CATEGORY not in('OpManager-WAN RTT Monitor','OpManager-VoIP Monitor') and ExternalDeviceDetails.CATEGORY NOT in ('OpManager-Interface')" + SANUtil.getExcludeSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true);
/*      */     }
/*      */     
/*  787 */     String configuredNwd = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_ManagedResourceType, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE  and AM_ManagedResourceType.RESOURCEGROUP='NWD' and relationshipid is not null " + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true);
/*  788 */     ArrayList rows1 = this.mo.getRows(devicetoconfigure);
/*  789 */     this.selectedNwd = ExtProdUtil.getQueryResultInVector(configuredNwd);
/*  790 */     HashMap devSiteMap = CustomerManagementAPI.groupDevicesBySites(rows1, 5);
/*  791 */     request.setAttribute("devicetoconfigure", devSiteMap);
/*  792 */     request.setAttribute("configuredNwd", this.selectedNwd);
/*  793 */     if ((isPopup != null) && (isPopup.equals("true")))
/*      */     {
/*  795 */       return new ActionForward("/jsp/nwContents.jsp?haid=" + haid + "&parentHaid=" + parentHaid);
/*      */     }
/*      */     
/*      */ 
/*  799 */     return new ActionForward("/jsp/CreateBusinessService.jsp?stepNo=2&haid=" + haid + "&parentHaid=" + parentHaid + CustomerManagementAPI.appendSiteInRequestParam(siteId));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward addNetworkDevices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  807 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/*  808 */     String parentHaid = request.getParameter("parentHaid");
/*  809 */     String buttonVal = request.getParameter("buttonType1");
/*  810 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  811 */     String createMV = (String)request.getSession().getAttribute("createMV");
/*  812 */     if (createMV != null)
/*      */     {
/*  814 */       request.getSession().setAttribute("createMV", createMV);
/*      */     }
/*  816 */     String fromMapview = (String)request.getAttribute("fromMapview");
/*  817 */     if ("true".equalsIgnoreCase(fromMapview))
/*      */     {
/*  819 */       parentHaid = (String)request.getAttribute("parentHaid");
/*  820 */       buttonVal = "";
/*      */     }
/*  822 */     boolean isNextButton = true;
/*  823 */     if (buttonVal.equals("finishButton"))
/*      */     {
/*  825 */       isNextButton = false;
/*      */     }
/*      */     try
/*      */     {
/*  829 */       String haid = request.getParameter("haid");
/*  830 */       if ("true".equalsIgnoreCase(fromMapview))
/*      */       {
/*  832 */         haid = Integer.toString(((Integer)request.getAttribute("bsgId")).intValue());
/*      */       }
/*  834 */       String mgName = DBUtil.getDisplaynameforResourceID(haid);
/*      */       
/*  836 */       Hashtable toNotifier = null;
/*  837 */       if (this.notifyConsole.shouldNotify())
/*      */       {
/*  839 */         toNotifier = new Hashtable();
/*  840 */         toNotifier.put("MGID", haid);
/*  841 */         toNotifier.put("MGNAME", mgName);
/*      */       }
/*  843 */       Vector currentResources = (Vector)this.selectedNwd.clone();
/*  844 */       String newVal = request.getParameter("newVal");
/*  845 */       if ("true".equalsIgnoreCase(fromMapview))
/*      */       {
/*  847 */         newVal = (String)request.getAttribute("newVal");
/*      */       }
/*  849 */       String selDevices = new String();
/*  850 */       String unselDevices = new String();
/*  851 */       StringTokenizer st = new StringTokenizer(newVal, ";");
/*  852 */       while (st.hasMoreTokens())
/*      */       {
/*  854 */         String tempToken = st.nextToken().trim();
/*  855 */         if (tempToken.startsWith("unSelectedDevices="))
/*      */         {
/*  857 */           unselDevices = tempToken.substring("unSelectedDevices=".length());
/*      */         }
/*  859 */         else if (tempToken.startsWith("selectedDevices="))
/*      */         {
/*  861 */           selDevices = tempToken.substring("selectedDevices=".length());
/*      */         }
/*      */       }
/*      */       
/*  865 */       Vector selDevVec = Constants.convertCSVToVector(selDevices);
/*  866 */       Vector resToRemove = Constants.convertCSVToVector(unselDevices);
/*  867 */       Vector resToAdd = new Vector();
/*  868 */       for (int i = 0; i < selDevVec.size(); i++)
/*      */       {
/*  870 */         String tempSel = ((String)selDevVec.elementAt(i)).trim();
/*  871 */         if (!currentResources.contains(tempSel))
/*      */         {
/*  873 */           resToAdd.addElement(tempSel);
/*      */         }
/*      */       }
/*  876 */       String[] resources = Constants.convertVectorToArray(resToAdd);
/*  877 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/*  878 */       Vector<String> toDeleteFromMapView = new Vector();
/*  879 */       Vector<String> toAddToMapView = new Vector();
/*  880 */       for (int i = 0; i < resToRemove.size(); i++)
/*      */       {
/*      */         try
/*      */         {
/*  884 */           String childID = ((String)resToRemove.elementAt(i)).trim();
/*  885 */           toDeleteFromMapView.add(childID);
/*      */           
/*  887 */           String query = "delete from AM_PARENTCHILDMAPPER where CHILDID=" + childID + " and PARENTID=" + haid;
/*  888 */           int count = AMConnectionPool.executeUpdateStmt(query);
/*  889 */           adder.deleteDependantAttributes(Integer.parseInt(haid), Integer.parseInt(childID));
/*  890 */           EnterpriseUtil.addUpdateQueryToFile(query);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  894 */           exc.printStackTrace();
/*      */         }
/*      */       }
/*  897 */       if (resources != null)
/*      */       {
/*  899 */         for (String resID : resources)
/*      */         {
/*  901 */           String resName = Constants.getResName(resID);
/*  902 */           toAddToMapView.add(resID);
/*  903 */           int prodId = EnterpriseUtil.getManagedServerIndex(Integer.parseInt(resID));
/*  904 */           if (prodId != -1)
/*      */           {
/*  906 */             prodId = prodId * EnterpriseUtil.RANGE + 1;
/*      */           }
/*  908 */           prodId = EnterpriseUtil.getProdId("OpManager");
/*  909 */           ExtProdUtil.addAndAssociateDevices(resName, haid, request, prodId + "");
/*      */         }
/*  911 */         if (haid != null) {
/*  912 */           ExtProdUtil.callApplyRCAForThisMG(haid);
/*      */         }
/*      */       }
/*      */       String mapViewId;
/*  916 */       if ((Constants.isIt360) && (!"true".equalsIgnoreCase(fromMapview)))
/*      */       {
/*      */ 
/*  919 */         mapViewId = MapViewUtil.getMapViewIdForBSGId(haid);
/*  920 */         String parentMapViewId = MapViewUtil.getMapViewIdForBSGId(parentHaid);
/*  921 */         if ((mapViewId == null) && (buttonVal.equals("finishButton")))
/*      */         {
/*      */           try
/*      */           {
/*  925 */             boolean isSubgroup = MapViewUtil.isSubgroup(haid);
/*      */             
/*  927 */             if (("true".equalsIgnoreCase(createMV)) || ((parentMapViewId != null) && (("false".equalsIgnoreCase(createMV)) || (createMV == null)) && (isSubgroup)))
/*      */             {
/*  929 */               AMLog.debug("Going to create Mapviews for BSG " + haid + " and its subgroups");
/*  930 */               MapViewUtil.createMVForBsgAndSubgroups(haid, username);
/*  931 */               AMLog.debug("Created !!");
/*      */               
/*  933 */               request.getSession().setAttribute("createMV", "false");
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  938 */             e.printStackTrace();
/*      */           }
/*      */         }
/*  941 */         else if ((mapViewId != null) && ((selDevices != null) || (unselDevices != null)))
/*      */         {
/*      */ 
/*  944 */           if ((toAddToMapView != null) && (toAddToMapView.size() > 0))
/*      */           {
/*  946 */             for (String deviceId : toAddToMapView)
/*      */             {
/*  948 */               String deviceName = Constants.getResName(deviceId);
/*      */               
/*  950 */               if (!deviceName.startsWith("IF-"))
/*      */               {
/*  952 */                 MapViewUtil.addDevice(mapViewId, deviceId);
/*  953 */                 AMLog.debug("Added device " + deviceId + " to mapview with id " + mapViewId);
/*      */               }
/*      */             }
/*      */           }
/*  957 */           if ((toDeleteFromMapView != null) && (toDeleteFromMapView.size() > 0))
/*      */           {
/*      */ 
/*  960 */             for (String deviceId : toDeleteFromMapView)
/*      */             {
/*  962 */               String deviceName = Constants.getResName(deviceId);
/*      */               
/*  964 */               if (!deviceName.startsWith("IF-"))
/*      */               {
/*  966 */                 MapViewUtil.deleteDeviceAndLinks(deviceId, mapViewId);
/*  967 */                 AMLog.debug("Deleted device " + deviceId + " from mapview with id " + mapViewId);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  976 */       if (this.notifyConsole.shouldNotify())
/*      */       {
/*      */         try
/*      */         {
/*  980 */           if (haid != null)
/*      */           {
/*  982 */             boolean isSubGroup = false;
/*  983 */             isSubGroup = Constants.isSubGroup(haid);
/*  984 */             toNotifier.put("IS_SUBGROUP", Boolean.valueOf(isSubGroup));
/*  985 */             toNotifier.put("DISPLAYNAME", mgName);
/*  986 */             if (isSubGroup)
/*      */             {
/*  988 */               String parentGrpNames = BSIntegUtil.getTOPLevelIntegMGsNames("", haid);
/*  989 */               parentGrpNames = parentGrpNames.substring(0, parentGrpNames.length() - 1);
/*  990 */               toNotifier.put("MGNAME", mgName + "-" + parentGrpNames);
/*      */             }
/*  992 */             ExtProdUtil.doBVNotificationToOPM(toNotifier, request, haid);
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  997 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 1002 */         new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 17, System.currentTimeMillis(), true, true, 1);
/* 1003 */         new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 18, System.currentTimeMillis(), true, false, 2);
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1008 */         e.printStackTrace();
/*      */       }
/* 1010 */       AMLog.debug("CreateBusinessService : Successfully added Network Devices for business service : " + haid);
/*      */       
/* 1012 */       String siteId = "-1";
/* 1013 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/* 1015 */         String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 1016 */         if (!custId.equals("-1"))
/*      */         {
/* 1018 */           Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/* 1019 */           siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */         }
/*      */       }
/*      */       
/* 1023 */       if ("true".equalsIgnoreCase(fromMapview))
/*      */       {
/* 1025 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 1029 */       if (isNextButton)
/*      */       {
/* 1031 */         return new ActionForward("/createBS.do?method=getSANDevices&haid=" + request.getParameter("haid") + "&devtype=All&parentHaid=" + parentHaid + CustomerManagementAPI.appendSiteInRequestParam(siteId));
/*      */       }
/*      */       
/*      */ 
/* 1035 */       String redirPath = mapping.findForward("showBusinessService").getPath();
/* 1036 */       return new ActionForward(redirPath + "&haid=" + haid + CustomerManagementAPI.appendSiteInRequestParam(siteId), true);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 1042 */       ex.printStackTrace(); }
/* 1043 */     return mapping.getInputForward();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getSANDevices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1054 */     String parentHaid = request.getParameter("parentHaid");
/* 1055 */     String type = request.getParameter("devtype");
/* 1056 */     String devName = request.getParameter("devname");
/* 1057 */     String isPopup = request.getParameter("isPopup");
/*      */     
/* 1059 */     String haid = request.getParameter("haid");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1086 */     ActionMessages messages = new ActionMessages();
/* 1087 */     saveMessages(request, messages);
/* 1088 */     String devicetoconfigure = null;
/*      */     
/*      */ 
/* 1091 */     String siteId = "-1";
/* 1092 */     HashMap filterprop = new HashMap();
/* 1093 */     String siteFilterCondn = "";
/* 1094 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/* 1096 */       String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 1097 */       if (!custId.equals("-1"))
/*      */       {
/* 1099 */         Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/* 1100 */         siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */       }
/*      */       
/*      */ 
/* 1104 */       filterprop.put("siteId", siteId);
/* 1105 */       filterprop.put("excludeInterfaces", Boolean.valueOf(false));
/* 1106 */       siteFilterCondn = " and " + CustomerManagementAPI.getCondition("AM_ManagedObject.RESOURCEID", CustomerManagementAPI.filterResourceIds(filterprop));
/* 1107 */       if ((devName != null) && (!devName.equals("null")))
/*      */       {
/*      */ 
/* 1110 */         devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID, SiteInfo.SITENAME from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join SiteInfo on AM_PARENTCHILDMAPPER.PARENTID=SiteInfo.SITEID join AM_AssociatedExtDevices on AM_AssociatedExtDevices.PRODUCTID = ExternalDeviceDetails.PRODUCTID where AM_AssociatedExtDevices.RESID= AM_ManagedObject.RESOURCEID and ExternalDeviceDetails.CATEGORY = '" + type + "'  and ExternalDeviceDetails.DISPLAYNAME = '" + devName + "' " + SANUtil.getSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true) + siteFilterCondn;
/*      */       }
/* 1112 */       else if ((type != null) && (!type.equals("null")) && (!type.equals("All")))
/*      */       {
/*      */ 
/* 1115 */         devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID, SiteInfo.SITENAME from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join SiteInfo on AM_PARENTCHILDMAPPER.PARENTID=SiteInfo.SITEID join AM_AssociatedExtDevices on AM_AssociatedExtDevices.PRODUCTID = ExternalDeviceDetails.PRODUCTID where AM_AssociatedExtDevices.RESID= AM_ManagedObject.RESOURCEID and ExternalDeviceDetails.CATEGORY = '" + type + "' " + SANUtil.getSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true) + siteFilterCondn;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1120 */         devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID, SiteInfo.SITENAME from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join SiteInfo on AM_PARENTCHILDMAPPER.PARENTID=SiteInfo.SITEID join AM_AssociatedExtDevices on AM_AssociatedExtDevices.PRODUCTID = ExternalDeviceDetails.PRODUCTID where AM_AssociatedExtDevices.RESID= AM_ManagedObject.RESOURCEID and ExternalDeviceDetails.CATEGORY NOT in ('OpManager-Interface') " + SANUtil.getSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true) + siteFilterCondn;
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 1125 */     else if ((devName != null) && (!devName.equals("null")))
/*      */     {
/* 1127 */       devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME where ExternalDeviceDetails.CATEGORY = '" + type + "' and ExternalDeviceDetails.DISPLAYNAME = '" + devName + "'" + SANUtil.getSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true);
/*      */     }
/* 1129 */     else if ((type != null) && (!type.equals("null")) && (!type.equals("All")))
/*      */     {
/* 1131 */       devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME where ExternalDeviceDetails.CATEGORY = '" + type + "'" + SANUtil.getSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true);
/*      */     }
/*      */     else
/*      */     {
/* 1135 */       devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID,AM_ManagedObject.RESOURCEID from AM_ManagedObject join ExternalDeviceDetails on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME where ExternalDeviceDetails.CATEGORY NOT in ('OpManager-Interface')" + SANUtil.getSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", true);
/*      */     }
/*      */     
/* 1138 */     String configuredSAN = configuredSAN = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_ManagedResourceType, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE  and AM_ManagedResourceType.RESOURCEGROUP='NWD' and relationshipid is not null " + SANUtil.getSanConditionForAmMrtTable("AM_ManagedResourceType.SUBGROUP", true);
/* 1139 */     ArrayList rows1 = this.mo.getRows(devicetoconfigure);
/* 1140 */     this.selectedSAN = ExtProdUtil.getQueryResultInVector(configuredSAN);
/* 1141 */     HashMap devSiteMap = CustomerManagementAPI.groupDevicesBySites(rows1, 5);
/* 1142 */     request.setAttribute("devicetoconfigure", devSiteMap);
/* 1143 */     request.setAttribute("configuredSAN", this.selectedSAN);
/* 1144 */     if ((isPopup != null) && (isPopup.equals("true")))
/*      */     {
/* 1146 */       return new ActionForward("/jsp/sanContents.jsp?haid=" + haid + "&parentHaid=" + parentHaid);
/*      */     }
/*      */     
/*      */ 
/* 1150 */     return new ActionForward("/jsp/CreateBusinessService.jsp?stepNo=6&haid=" + haid + "&parentHaid=" + parentHaid + CustomerManagementAPI.appendSiteInRequestParam(siteId));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward addSANDevices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1158 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/* 1159 */     String parentHaid = request.getParameter("parentHaid");
/* 1160 */     String buttonVal = request.getParameter("buttonType1");
/* 1161 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1162 */     String createMV = (String)request.getSession().getAttribute("createMV");
/* 1163 */     if (createMV != null)
/*      */     {
/* 1165 */       request.getSession().setAttribute("createMV", createMV);
/*      */     }
/* 1167 */     String fromMapview = (String)request.getAttribute("fromMapview");
/* 1168 */     if ("true".equalsIgnoreCase(fromMapview))
/*      */     {
/* 1170 */       parentHaid = (String)request.getAttribute("parentHaid");
/* 1171 */       buttonVal = "";
/*      */     }
/* 1173 */     boolean isNextButton = true;
/* 1174 */     if (buttonVal.equals("finishButton"))
/*      */     {
/* 1176 */       isNextButton = false;
/*      */     }
/*      */     try
/*      */     {
/* 1180 */       String haid = request.getParameter("haid");
/* 1181 */       if ("true".equalsIgnoreCase(fromMapview))
/*      */       {
/* 1183 */         haid = Integer.toString(((Integer)request.getAttribute("bsgId")).intValue());
/*      */       }
/* 1185 */       String mgName = DBUtil.getDisplaynameforResourceID(haid);
/*      */       
/* 1187 */       Hashtable toNotifier = null;
/* 1188 */       if (this.notifyConsole.shouldNotify())
/*      */       {
/* 1190 */         toNotifier = new Hashtable();
/* 1191 */         toNotifier.put("MGID", haid);
/* 1192 */         toNotifier.put("MGNAME", mgName);
/*      */       }
/* 1194 */       Vector currentResources = (Vector)this.selectedSAN.clone();
/* 1195 */       String newVal = request.getParameter("newVal");
/* 1196 */       if ("true".equalsIgnoreCase(fromMapview))
/*      */       {
/* 1198 */         newVal = (String)request.getAttribute("newVal");
/*      */       }
/* 1200 */       String selDevices = new String();
/* 1201 */       String unselDevices = new String();
/* 1202 */       StringTokenizer st = new StringTokenizer(newVal, ";");
/* 1203 */       while (st.hasMoreTokens())
/*      */       {
/* 1205 */         String tempToken = st.nextToken().trim();
/* 1206 */         if (tempToken.startsWith("unSelectedDevices="))
/*      */         {
/* 1208 */           unselDevices = tempToken.substring("unSelectedDevices=".length());
/*      */         }
/* 1210 */         else if (tempToken.startsWith("selectedDevices="))
/*      */         {
/* 1212 */           selDevices = tempToken.substring("selectedDevices=".length());
/*      */         }
/*      */       }
/*      */       
/* 1216 */       Vector selDevVec = Constants.convertCSVToVector(selDevices);
/* 1217 */       Vector resToRemove = Constants.convertCSVToVector(unselDevices);
/* 1218 */       Vector resToAdd = new Vector();
/* 1219 */       for (int i = 0; i < selDevVec.size(); i++)
/*      */       {
/* 1221 */         String tempSel = ((String)selDevVec.elementAt(i)).trim();
/* 1222 */         if (!currentResources.contains(tempSel))
/*      */         {
/* 1224 */           resToAdd.addElement(tempSel);
/*      */         }
/*      */       }
/* 1227 */       String[] resources = Constants.convertVectorToArray(resToAdd);
/* 1228 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 1229 */       Vector<String> toDeleteFromMapView = new Vector();
/* 1230 */       Vector<String> toAddToMapView = new Vector();
/* 1231 */       for (int i = 0; i < resToRemove.size(); i++)
/*      */       {
/*      */         try
/*      */         {
/* 1235 */           String tempResId = (String)resToRemove.elementAt(i);
/* 1236 */           toDeleteFromMapView.add(tempResId);
/*      */           
/* 1238 */           String query = "delete from AM_PARENTCHILDMAPPER where CHILDID=" + tempResId + " and PARENTID=" + haid;
/* 1239 */           int count = AMConnectionPool.executeUpdateStmt(query);
/* 1240 */           adder.deleteDependantAttributes(Integer.parseInt(haid), Integer.parseInt(tempResId));
/* 1241 */           EnterpriseUtil.addUpdateQueryToFile(query);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1245 */           exc.printStackTrace();
/*      */         }
/*      */       }
/* 1248 */       if (resources != null)
/*      */       {
/* 1250 */         for (String resID : resources)
/*      */         {
/* 1252 */           String resName = Constants.getResName(resID);
/* 1253 */           toAddToMapView.add(resID);
/* 1254 */           int prodId = EnterpriseUtil.getProdId("OpStor");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1260 */           ExtProdUtil.addAndAssociateDevices(resName, haid, request, prodId + "");
/*      */         }
/* 1262 */         if (haid != null) {
/* 1263 */           ExtProdUtil.callApplyRCAForThisMG(haid);
/*      */         }
/*      */       }
/*      */       String mapViewId;
/* 1267 */       if ((Constants.isIt360) && (!"true".equalsIgnoreCase(fromMapview)))
/*      */       {
/*      */ 
/* 1270 */         mapViewId = MapViewUtil.getMapViewIdForBSGId(haid);
/* 1271 */         String parentMapViewId = MapViewUtil.getMapViewIdForBSGId(parentHaid);
/* 1272 */         if ((mapViewId == null) && (buttonVal.equals("finishButton")))
/*      */         {
/*      */           try
/*      */           {
/* 1276 */             boolean isSubgroup = MapViewUtil.isSubgroup(haid);
/*      */             
/* 1278 */             if (("true".equalsIgnoreCase(createMV)) || ((parentMapViewId != null) && (("false".equalsIgnoreCase(createMV)) || (createMV == null)) && (isSubgroup)))
/*      */             {
/* 1280 */               AMLog.debug("Going to create Mapviews for BSG " + haid + " and its subgroups");
/* 1281 */               MapViewUtil.createMVForBsgAndSubgroups(haid, username);
/* 1282 */               AMLog.debug("Created !!");
/*      */               
/* 1284 */               request.getSession().setAttribute("createMV", "false");
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1289 */             e.printStackTrace();
/*      */           }
/*      */         }
/* 1292 */         else if ((mapViewId != null) && ((selDevices != null) || (unselDevices != null)))
/*      */         {
/*      */ 
/* 1295 */           if ((toAddToMapView != null) && (toAddToMapView.size() > 0))
/*      */           {
/* 1297 */             for (String deviceId : toAddToMapView)
/*      */             {
/* 1299 */               String deviceName = Constants.getResName(deviceId);
/*      */               
/* 1301 */               if (!deviceName.startsWith("IF-"))
/*      */               {
/* 1303 */                 MapViewUtil.addDevice(mapViewId, deviceId);
/* 1304 */                 AMLog.debug("Added device " + deviceId + " to mapview with id " + mapViewId);
/*      */               }
/*      */             }
/*      */           }
/* 1308 */           if ((toDeleteFromMapView != null) && (toDeleteFromMapView.size() > 0))
/*      */           {
/*      */ 
/* 1311 */             for (String deviceId : toDeleteFromMapView)
/*      */             {
/* 1313 */               String deviceName = Constants.getResName(deviceId);
/*      */               
/* 1315 */               if (!deviceName.startsWith("IF-"))
/*      */               {
/* 1317 */                 MapViewUtil.deleteDeviceAndLinks(deviceId, mapViewId);
/* 1318 */                 AMLog.debug("Deleted device " + deviceId + " from mapview with id " + mapViewId);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1327 */       if (this.notifyConsole.shouldNotify())
/*      */       {
/*      */         try
/*      */         {
/* 1331 */           if (haid != null)
/*      */           {
/* 1333 */             boolean isSubGroup = false;
/* 1334 */             isSubGroup = Constants.isSubGroup(haid);
/* 1335 */             toNotifier.put("IS_SUBGROUP", Boolean.valueOf(isSubGroup));
/* 1336 */             toNotifier.put("DISPLAYNAME", mgName);
/* 1337 */             if (isSubGroup)
/*      */             {
/* 1339 */               String parentGrpNames = BSIntegUtil.getTOPLevelIntegMGsNames("", haid);
/* 1340 */               parentGrpNames = parentGrpNames.substring(0, parentGrpNames.length() - 1);
/* 1341 */               toNotifier.put("MGNAME", mgName + "-" + parentGrpNames);
/*      */             }
/* 1343 */             ExtProdUtil.doBVNotificationToOPM(toNotifier, request, haid);
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1348 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 1353 */         new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 17, System.currentTimeMillis(), true, true, 1);
/* 1354 */         new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 18, System.currentTimeMillis(), true, false, 2);
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1359 */         e.printStackTrace();
/*      */       }
/* 1361 */       AMLog.debug("CreateBusinessService : Successfully added SAN Devices for business service : " + haid);
/*      */       
/* 1363 */       String siteId = "-1";
/* 1364 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/* 1366 */         String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 1367 */         if (!custId.equals("-1"))
/*      */         {
/* 1369 */           Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/* 1370 */           siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */         }
/*      */       }
/*      */       
/* 1374 */       if ("true".equalsIgnoreCase(fromMapview))
/*      */       {
/* 1376 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 1380 */       if (isNextButton)
/*      */       {
/* 1382 */         return new ActionForward("/createBS.do?method=getServAndAppl&haid=" + request.getParameter("haid") + "&devtype=All&parentHaid=" + parentHaid + CustomerManagementAPI.appendSiteInRequestParam(siteId));
/*      */       }
/*      */       
/*      */ 
/* 1386 */       String redirPath = mapping.findForward("showBusinessService").getPath();
/* 1387 */       return new ActionForward(redirPath + "&haid=" + haid + CustomerManagementAPI.appendSiteInRequestParam(siteId), true);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 1393 */       ex.printStackTrace(); }
/* 1394 */     return mapping.getInputForward();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Hashtable getAddedSANDevices(String owner, String role, String type, HttpServletRequest request)
/*      */   {
/* 1404 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1405 */     rethash = new Hashtable();
/*      */     
/* 1407 */     ResultSet set = null;
/* 1408 */     String qry = "select CATEGORY, count(*) as count from ExternalDeviceDetails where  " + SANUtil.getSanConditionForExternalDevicesTable("CATEGORY", false) + " group by CATEGORY";
/*      */     
/* 1410 */     String siteId = "-1";
/* 1411 */     HashMap filterprop = new HashMap();
/* 1412 */     String siteFilterCondn = "";
/* 1413 */     if (EnterpriseUtil.isIt360MSPEdition)
/*      */     {
/* 1415 */       String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 1416 */       if (!custId.equals("-1"))
/*      */       {
/* 1418 */         Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/* 1419 */         siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */       }
/* 1421 */       filterprop.put("siteId", siteId);
/* 1422 */       siteFilterCondn = " and " + CustomerManagementAPI.getCondition("AM_ManagedObject.RESOURCEID", CustomerManagementAPI.filterResourceIds(filterprop));
/* 1423 */       qry = "select ExternalDeviceDetails.CATEGORY, count(*) as count from ExternalDeviceDetails,AM_ManagedObject,AM_AssociatedExtDevices where " + SANUtil.getSanConditionForExternalDevicesTable("ExternalDeviceDetails.CATEGORY", false) + " and AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID and AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME " + siteFilterCondn + " group by ExternalDeviceDetails.CATEGORY";
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1428 */       set = AMConnectionPool.executeQueryStmt(qry);
/* 1429 */       while (set.next())
/*      */       {
/* 1431 */         Hashtable hash = new Hashtable();
/* 1432 */         ArrayList alist = new ArrayList();
/* 1433 */         String category = set.getString("CATEGORY");
/* 1434 */         hash.put("count", set.getString("count"));
/* 1435 */         hash.put("type", category);
/*      */         
/* 1437 */         ArrayList lists = new ArrayList();
/* 1438 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 1440 */           lists = this.mo.getRows("select ExternalDeviceDetails.DISPLAYNAME,AM_ManagedObject.RESOURCEID from AM_ManagedObject, ExternalDeviceDetails, AM_AssociatedExtDevices where AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID and AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME and AM_ManagedObject.TYPE=ExternalDeviceDetails.TYPE and ExternalDeviceDetails.CATEGORY='" + category + "'" + siteFilterCondn);
/*      */         }
/*      */         else
/*      */         {
/* 1444 */           lists = this.mo.getRows("select DISPLAYNAME from ExternalDeviceDetails where CATEGORY='" + category + "'");
/*      */         }
/*      */         
/* 1447 */         hash.put("devices", lists);
/* 1448 */         alist.add(hash);
/* 1449 */         rethash.put(category, alist);
/*      */       }
/* 1451 */       AMConnectionPool.closeStatement(set);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1471 */       return rethash;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1455 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1461 */         if (set != null)
/*      */         {
/* 1463 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */       }
/*      */       catch (Exception exp) {
/* 1467 */         exp.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getOPSTORUrl()
/*      */   {
/* 1478 */     opmUrl = "http://localhost";
/* 1479 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1482 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1483 */       String qry = "select PROTOCOL,HOST,PORT from AM_IntegratedProducts where PRODUCT_NAME='OpStor'";
/* 1484 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 1485 */       String protocol; String hostname; String port_number; if (rs.next())
/*      */       {
/* 1487 */         protocol = rs.getString(1);
/* 1488 */         hostname = rs.getString(2);
/* 1489 */         port_number = rs.getString(3); }
/* 1490 */       return protocol + "://" + hostname + ":" + port_number;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1494 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1500 */         if (rs != null)
/*      */         {
/* 1502 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       catch (Exception exp) {
/* 1506 */         exp.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getServAndAppl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1518 */     String parentHaid = request.getParameter("parentHaid");
/* 1519 */     String haid = request.getParameter("haid");
/* 1520 */     String type = request.getParameter("devtype");
/* 1521 */     String resGrp = request.getParameter("resgrp");
/* 1522 */     String dispName = request.getParameter("dispname");
/* 1523 */     String isPopup = request.getParameter("isPopup");
/* 1524 */     AMLog.debug("CreateBusinessService : About to fetch servers and applications for business service : " + haid + " for type : " + type);
/* 1525 */     String toconfigure = new String();
/* 1526 */     String orphanedMonitors = new String();
/* 1527 */     String configuredServAndAppl = new String();
/*      */     
/*      */ 
/* 1530 */     String siteId = "-1";
/* 1531 */     HashMap filterprop = new HashMap();
/* 1532 */     String siteFilterCondn = "";
/* 1533 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/* 1535 */       String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 1536 */       if (!custId.equals("-1"))
/*      */       {
/* 1538 */         Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/* 1539 */         siteId = Constants.convertVectorToCSV(siteIdVec);
/* 1540 */         filterprop.put("siteId", siteId);
/* 1541 */         siteFilterCondn = " and " + CustomerManagementAPI.getCondition("AM_ManagedObject.RESOURCEID", CustomerManagementAPI.filterResourceIds(filterprop));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1546 */       if ((type != null) && (!type.equals("null")) && (!type.equals("All")))
/*      */       {
/* 1548 */         toconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH,AM_ManagedObject.RESOURCENAME, SiteInfo.SITENAME from AM_ManagedObject,AM_ManagedResourceType,AM_PARENTCHILDMAPPER, SiteInfo where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID=SiteInfo.SITEID and AM_ManagedResourceType.SUBGROUP='" + type + "'" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true) + siteFilterCondn;
/* 1549 */         configuredServAndAppl = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_ManagedResourceType, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE  and AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','HAI','EMO') and relationshipid is not null and AM_ManagedResourceType.SUBGROUP='" + type + "'" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true);
/*      */       }
/* 1551 */       else if ((resGrp != null) && (!resGrp.equals("null")))
/*      */       {
/* 1553 */         toconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH,AM_ManagedObject.RESOURCENAME, SiteInfo.SITENAME from AM_ManagedObject,AM_ManagedResourceType,AM_PARENTCHILDMAPPER, SiteInfo where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID=SiteInfo.SITEID and AM_ManagedResourceType.RESOURCEGROUP='" + resGrp + "'" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true) + siteFilterCondn;
/* 1554 */         configuredServAndAppl = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_ManagedResourceType, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE  and AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','HAI','EMO') and relationshipid is not null and AM_ManagedResourceType.RESOURCEGROUP='" + resGrp + "'" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true);
/*      */       }
/*      */       else
/*      */       {
/* 1558 */         toconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH,AM_ManagedObject.RESOURCENAME, SiteInfo.SITENAME from AM_ManagedObject,AM_ATTRIBUTES,AM_ManagedResourceType,AM_PARENTCHILDMAPPER, SiteInfo where AM_ManagedObject.Type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ATTRIBUTES.TYPE=1 and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID=SiteInfo.SITEID" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true) + siteFilterCondn;
/*      */         
/* 1560 */         orphanedMonitors = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH,AM_ManagedObject.RESOURCENAME, SiteInfo.SITENAME from AM_ManagedResourceType JOIN AM_ManagedObject on TYPE=RESOURCETYPE LEFT OUTER JOIN InetService on AM_ManagedObject.RESOURCENAME=InetService.NAME LEFT JOIN AM_PARENTCHILDMAPPER on CHILDID=RESOURCEID JOIN SiteInfo on SITEID=AM_PARENTCHILDMAPPER.PARENTID where AM_ManagedResourceType.RESOURCEGROUP NOT IN ('SYS','NET','HAI','NWD','EMO') " + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true) + siteFilterCondn + " and (InetService.TARGETNAME is  NULL or InetService.TARGETNAME not in (select RESOURCENAME from AM_ManagedObject))";
/* 1561 */         configuredServAndAppl = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_ManagedResourceType, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE  and AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','HAI','EMO') and relationshipid is not null" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 1566 */     else if ((type != null) && (!type.equals("null")) && (!type.equals("All")))
/*      */     {
/* 1568 */       toconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH,AM_ManagedObject.RESOURCENAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP='" + type + "'" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true);
/* 1569 */       configuredServAndAppl = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_ManagedResourceType, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE  and AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','HAI','EMO') and relationshipid is not null and AM_ManagedResourceType.SUBGROUP='" + type + "'";
/*      */     }
/* 1571 */     else if ((resGrp != null) && (!resGrp.equals("null")))
/*      */     {
/* 1573 */       toconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH,AM_ManagedObject.RESOURCENAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='" + resGrp + "'" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true);
/* 1574 */       configuredServAndAppl = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_ManagedResourceType, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE  and AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','HAI','EMO') and relationshipid is not null and AM_ManagedResourceType.RESOURCEGROUP='" + resGrp + "'";
/*      */     }
/*      */     else
/*      */     {
/* 1578 */       toconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH,AM_ManagedObject.RESOURCENAME from AM_ManagedObject,AM_ATTRIBUTES,AM_ManagedResourceType  where AM_ManagedObject.Type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ATTRIBUTES.TYPE=1" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true);
/*      */       
/* 1580 */       orphanedMonitors = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH,AM_ManagedObject.RESOURCENAME from AM_ManagedResourceType JOIN AM_ManagedObject on TYPE=RESOURCETYPE LEFT OUTER JOIN InetService on AM_ManagedObject.RESOURCENAME=InetService.NAME where AM_ManagedResourceType.RESOURCEGROUP NOT IN ('SYS','NET','HAI','NWD')" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true) + " and (InetService.TARGETNAME is  NULL or InetService.TARGETNAME not in (select RESOURCENAME from AM_ManagedObject))";
/* 1581 */       configuredServAndAppl = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_ManagedResourceType, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE  and AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','HAI','EMO') and relationshipid is not null" + SANUtil.getExcludeSANConditionForAMMrtTable("AM_ManagedResourceType.SUBGROUP", true);
/*      */     }
/*      */     
/* 1584 */     ArrayList rows1 = this.mo.getRows(toconfigure);
/* 1585 */     if ((!orphanedMonitors.trim().equals("")) && ((resGrp == null) || (resGrp.equals("null"))))
/*      */     {
/* 1587 */       ArrayList tempList = this.mo.getRows(orphanedMonitors);
/* 1588 */       if (rows1 != null)
/*      */       {
/* 1590 */         rows1.addAll(tempList);
/*      */       }
/*      */     }
/* 1593 */     this.selectedServ = ExtProdUtil.getQueryResultInVector(configuredServAndAppl);
/* 1594 */     HashMap devSiteMap = CustomerManagementAPI.groupDevicesBySites(rows1, 4);
/* 1595 */     request.setAttribute("toconfigure", devSiteMap);
/* 1596 */     request.setAttribute("configuredServAndAppl", this.selectedServ);
/* 1597 */     if ((isPopup != null) && (isPopup.equals("true")))
/*      */     {
/* 1599 */       return new ActionForward("/jsp/appContents.jsp?haid=" + haid + "&parentHaid=" + parentHaid + CustomerManagementAPI.appendSiteInRequestParam(siteId));
/*      */     }
/*      */     
/*      */ 
/* 1603 */     return new ActionForward("/jsp/CreateBusinessService.jsp?stepNo=3&haid=" + haid + "&parentHaid=" + parentHaid + CustomerManagementAPI.appendSiteInRequestParam(siteId));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addServAndAppl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1613 */     String username = EnterpriseUtil.getLoggedInUserName(request);
/* 1614 */     String parentHaid = request.getParameter("parentHaid");
/* 1615 */     String buttonVal = request.getParameter("buttonType1");
/* 1616 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1617 */     String createMV = (String)request.getSession().getAttribute("createMV");
/* 1618 */     boolean isNextButton = true;
/*      */     
/* 1620 */     String haid = request.getParameter("haid");
/* 1621 */     String fromMapview = (String)request.getAttribute("fromMapview");
/* 1622 */     if ("true".equalsIgnoreCase(fromMapview))
/*      */     {
/* 1624 */       haid = Integer.toString(((Integer)request.getAttribute("bsgId")).intValue());
/* 1625 */       parentHaid = (String)request.getAttribute("parentHaid");
/* 1626 */       buttonVal = "";
/*      */     }
/* 1628 */     if (buttonVal.equals("finishButton"))
/*      */     {
/* 1630 */       isNextButton = false;
/*      */     }
/* 1632 */     AMLog.debug("CreateBusinessService : About to add servers and applications for business service : " + haid);
/*      */     
/* 1634 */     Vector currentResources = (Vector)this.selectedServ.clone();
/* 1635 */     String newVal = request.getParameter("newVal");
/* 1636 */     if ("true".equalsIgnoreCase(fromMapview))
/*      */     {
/* 1638 */       newVal = (String)request.getAttribute("newVal");
/*      */     }
/* 1640 */     String selDevices = new String();
/* 1641 */     String unselDevices = new String();
/* 1642 */     StringTokenizer st = new StringTokenizer(newVal, ";");
/* 1643 */     while (st.hasMoreTokens())
/*      */     {
/* 1645 */       String tempToken = st.nextToken().trim();
/* 1646 */       if (tempToken.startsWith("unSelectedDevices="))
/*      */       {
/* 1648 */         unselDevices = tempToken.substring("unSelectedDevices=".length());
/*      */       }
/* 1650 */       else if (tempToken.startsWith("selectedDevices="))
/*      */       {
/* 1652 */         selDevices = tempToken.substring("selectedDevices=".length());
/*      */       }
/*      */     }
/*      */     
/* 1656 */     Vector selDevVec = Constants.convertCSVToVector(selDevices);
/* 1657 */     Vector resToRemove = Constants.convertCSVToVector(unselDevices);
/* 1658 */     Vector resToAdd = new Vector();
/* 1659 */     for (int i = 0; i < selDevVec.size(); i++)
/*      */     {
/* 1661 */       String tempSel = ((String)selDevVec.elementAt(i)).trim();
/* 1662 */       if (!currentResources.contains(tempSel))
/*      */       {
/* 1664 */         resToAdd.addElement(tempSel);
/*      */       }
/* 1666 */       String parentDevId = getParentServerId(tempSel);
/*      */       
/* 1668 */       if ((EnterpriseUtil.isIt360MSPEdition()) && (!parentDevId.equals("-1")) && (!selDevVec.contains(parentDevId)))
/*      */       {
/*      */ 
/* 1671 */         resToAdd.addElement(parentDevId);
/*      */       }
/*      */     }
/* 1674 */     String[] resources = Constants.convertVectorToArray(resToAdd);
/*      */     
/* 1676 */     AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 1677 */     for (int i = 0; i < resToRemove.size(); i++)
/*      */     {
/*      */       try
/*      */       {
/* 1681 */         String query = "delete from AM_PARENTCHILDMAPPER where PARENTID=" + haid + " and CHILDID=" + resToRemove.elementAt(i);
/* 1682 */         int count = AMConnectionPool.executeUpdateStmt(query);
/* 1683 */         adder.deleteDependantAttributes(Integer.parseInt(haid), Integer.parseInt((String)resToRemove.elementAt(i)));
/* 1684 */         EnterpriseUtil.addUpdateQueryToFile(query);
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 1688 */         exc.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 1692 */     int result = 0;
/* 1693 */     if (resources != null)
/*      */     {
/* 1695 */       Vector forUpdate = new Vector();
/* 1696 */       result = this.mo.updateManagedApplicationResourcesForEnterprise(haid, "xyz", resources, forUpdate);
/* 1697 */       if (forUpdate != null)
/*      */       {
/*      */ 
/* 1700 */         for (int i = 0; i < forUpdate.size(); i++)
/*      */         {
/* 1702 */           EnterpriseUtil.addUpdateQueryToFile(forUpdate.get(i) + "");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1707 */     if ((Constants.isIt360) && (!"true".equalsIgnoreCase(fromMapview)))
/*      */     {
/*      */       try
/*      */       {
/* 1711 */         mapViewId = MapViewUtil.getMapViewIdForBSGId(haid);
/* 1712 */         String parentMapViewId = MapViewUtil.getMapViewIdForBSGId(parentHaid);
/* 1713 */         if ((mapViewId == null) && (buttonVal.equals("finishButton")))
/*      */         {
/*      */           try
/*      */           {
/* 1717 */             boolean isSubgroup = MapViewUtil.isSubgroup(haid);
/*      */             
/* 1719 */             if (("true".equalsIgnoreCase(createMV)) || ((parentMapViewId != null) && (("false".equalsIgnoreCase(createMV)) || (createMV == null)) && (isSubgroup)))
/*      */             {
/* 1721 */               AMLog.debug("Going to create Mapviews for BSG " + haid + " and its subgroups");
/* 1722 */               MapViewUtil.createMVForBsgAndSubgroups(haid, username);
/* 1723 */               AMLog.debug("Created !!");
/*      */               
/* 1725 */               request.getSession().setAttribute("createMV", "false");
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1730 */             e.printStackTrace();
/*      */           }
/*      */         }
/* 1733 */         else if (mapViewId != null)
/*      */         {
/* 1735 */           if ((selDevices != null) && (selDevices.length() != 0) && (selDevices.charAt(selDevices.length() - 1) == ','))
/*      */           {
/* 1737 */             selDevices = selDevices.substring(0, selDevices.length() - 1);
/*      */           }
/* 1739 */           if ((unselDevices != null) && (unselDevices.length() != 0) && (unselDevices.charAt(unselDevices.length() - 1) == ','))
/*      */           {
/* 1741 */             unselDevices = unselDevices.substring(0, unselDevices.length() - 1);
/*      */           }
/*      */           
/* 1744 */           Vector<String> devicesToAdd = MapViewUtil.removeAppsAndIFs(selDevices, true);
/* 1745 */           Vector<String> devicesToDelete = MapViewUtil.removeAppsAndIFs(unselDevices, true);
/* 1746 */           if ((devicesToAdd != null) && (devicesToAdd.size() > 0))
/*      */           {
/* 1748 */             for (String deviceName : devicesToAdd)
/*      */             {
/* 1750 */               MapViewUtil.addDevice(mapViewId, deviceName);
/*      */             }
/*      */           }
/* 1753 */           if ((devicesToDelete != null) && (devicesToDelete.size() > 0))
/*      */           {
/* 1755 */             for (String deviceName : devicesToDelete)
/*      */             {
/* 1757 */               MapViewUtil.deleteDeviceAndLinks(deviceName, mapViewId);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */         String mapViewId;
/*      */         
/* 1766 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1773 */       new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 17, System.currentTimeMillis(), true, true, 1);
/* 1774 */       new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 18, System.currentTimeMillis(), true, false, 2);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1778 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1781 */     if (this.notifyConsole.shouldNotify())
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/* 1786 */         Hashtable toNotifier = null;
/* 1787 */         String mgName = DBUtil.getDisplaynameforResourceID(haid);
/* 1788 */         toNotifier = new Hashtable();
/* 1789 */         toNotifier.put("MGID", haid);
/* 1790 */         toNotifier.put("MGNAME", mgName);
/* 1791 */         if (haid != null)
/*      */         {
/* 1793 */           boolean isSubGroup = false;
/* 1794 */           isSubGroup = Constants.isSubGroup(haid);
/* 1795 */           toNotifier.put("IS_SUBGROUP", Boolean.valueOf(isSubGroup));
/* 1796 */           toNotifier.put("DISPLAYNAME", mgName);
/* 1797 */           if (isSubGroup)
/*      */           {
/* 1799 */             String parentGrpNames = BSIntegUtil.getTOPLevelIntegMGsNames("", haid);
/* 1800 */             parentGrpNames = parentGrpNames.substring(0, parentGrpNames.length() - 1);
/* 1801 */             toNotifier.put("MGNAME", mgName + "-" + parentGrpNames);
/*      */           }
/* 1803 */           toNotifier.put("EventType", "Updated");
/* 1804 */           AMLog.debug("In createBusinessService : toNotifier -  " + toNotifier);
/* 1805 */           MGActionNotifier notifyConsole = MGActionNotifier.getInstance();
/* 1806 */           notifyConsole.setProperties(toNotifier);
/* 1807 */           Thread t = new Thread(notifyConsole);
/* 1808 */           t.start();
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1814 */         ex.printStackTrace();
/*      */       }
/*      */     }
/* 1817 */     AMLog.debug("CreateBusinessService : Successfully added servers and applications for business service : " + haid);
/* 1818 */     String path = mapping.findForward("showBusinessService").getPath();
/* 1819 */     if ("true".equalsIgnoreCase(fromMapview))
/*      */     {
/* 1821 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 1825 */     return new ActionForward(path + "&haid=" + haid, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getWebTransactions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1834 */     String haid = request.getParameter("haid");
/* 1835 */     String parentHaid = request.getParameter("parentHaid");
/* 1836 */     AMLog.debug("CreateBusinessService : About to fetch web transactions for business service : " + haid);
/* 1837 */     return new ActionForward("/jsp/CreateBusinessService.jsp?stepNo=4&parentHaid=" + parentHaid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward addWebTransactions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1845 */     String haid = request.getParameter("haid");
/* 1846 */     String parentHaid = request.getParameter("parentHaid");
/* 1847 */     AMLog.debug("CreateBusinessService : Successfully added web transactions for business service : " + haid);
/* 1848 */     return new ActionForward("/jsp/CreateBusinessService.jsp?stepNo=5&haid=" + haid + "&parentHaid=" + parentHaid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward editApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1857 */     this.isEdit = true;
/* 1858 */     String haid = request.getParameter("haid");
/*      */     
/* 1860 */     String siteId = "-1";
/* 1861 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/* 1863 */       String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 1864 */       if (!custId.equals("-1"))
/*      */       {
/* 1866 */         Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/* 1867 */         siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1872 */     AMLog.debug("CreateBusinessService : About to edit the business service : " + haid);
/* 1873 */     request.setAttribute("haid", haid);
/* 1874 */     request.setAttribute("gmapcountries", DBUtil.getGMapCountries());
/* 1875 */     String fromwhere = request.getParameter("fromwhere");
/* 1876 */     if (fromwhere != null)
/*      */     {
/* 1878 */       request.setAttribute("fromwhere", fromwhere);
/*      */     }
/*      */     else
/*      */     {
/* 1882 */       request.setAttribute("fromwhere", "detailspage");
/*      */     }
/* 1884 */     ArrayList applist = this.mo.getRows("select AM_ManagedObject.DESCRIPTION,CREATIONDATE,MODIFIEDDATE,RESOURCENAME,AM_HOLISTICAPPLICATION.TYPE,AM_ManagedObject.DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_HOLISTICAPPLICATION.HAID=" + haid + " and  AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID");
/* 1885 */     ArrayList locationid = this.mo.getRows("select LOCATIONID from AM_GMapCountryResourceRel where ID=" + haid);
/* 1886 */     ArrayList row = null;
/* 1887 */     if (applist.size() > 0)
/*      */     {
/*      */ 
/* 1890 */       row = (ArrayList)applist.get(0);
/* 1891 */       String name = (String)row.get(3);
/* 1892 */       String description = (String)row.get(0);
/* 1893 */       String creationdate = (String)row.get(1);
/* 1894 */       String lastmodified = (String)row.get(2);
/* 1895 */       String MGtype = (String)row.get(4);
/* 1896 */       String displayName = (String)row.get(5);
/* 1897 */       request.setAttribute("MGtype", MGtype);
/* 1898 */       request.setAttribute("haid", haid);
/* 1899 */       request.setAttribute("name", name);
/* 1900 */       request.setAttribute("description", description);
/* 1901 */       request.setAttribute("displayName", displayName);
/*      */       
/* 1903 */       if (locationid.size() == 1)
/*      */       {
/* 1905 */         List cidList = (List)locationid.get(0);
/* 1906 */         request.setAttribute("locationid", cidList.get(0));
/*      */       }
/*      */       
/* 1909 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1910 */       ResultSet rs = null;
/*      */       try
/*      */       {
/* 1913 */         ArrayList selectedOwnersList = null;
/* 1914 */         String selOwnQuery = "select AM_HOLISTICAPPLICATION_OWNERS.OWNERID,USERNAME from AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and HAID=" + haid;
/* 1915 */         rs = AMConnectionPool.executeQueryStmt(selOwnQuery);
/* 1916 */         while (rs.next())
/*      */         {
/* 1918 */           if (selectedOwnersList == null)
/*      */           {
/* 1920 */             selectedOwnersList = new ArrayList(4);
/*      */           }
/* 1922 */           Properties p = new Properties();
/* 1923 */           p.setProperty("label", rs.getString("USERNAME"));
/* 1924 */           p.setProperty("value", rs.getString("OWNERID"));
/* 1925 */           selectedOwnersList.add(p);
/*      */         }
/* 1927 */         AMConnectionPool.closeStatement(rs);
/* 1928 */         if (selectedOwnersList != null)
/*      */         {
/* 1930 */           request.setAttribute("selectedowners", selectedOwnersList);
/*      */         }
/*      */         else
/*      */         {
/* 1934 */           request.setAttribute("selectedowners", new ArrayList());
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1939 */         e.printStackTrace();
/* 1940 */         rs = null;
/*      */       }
/* 1942 */       request.setAttribute("creationdate", creationdate);
/* 1943 */       request.setAttribute("lastmodified", lastmodified);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 1949 */       return new ActionForward("/applications.do");
/*      */     }
/* 1951 */     return new ActionForward("/jsp/CreateBusinessService.jsp?stepNo=1&isEdit=true" + CustomerManagementAPI.appendSiteInRequestParam(siteId));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/* 1959 */     lengthOfTrimmedString = AppManagerUtil.getWebclientDisplayLength(lengthOfTrimmedString);
/* 1960 */     if ((stringToTrim != null) && (lengthOfTrimmedString > 0))
/*      */     {
/* 1962 */       if (stringToTrim.length() > lengthOfTrimmedString)
/*      */       {
/* 1964 */         return stringToTrim.substring(0, lengthOfTrimmedString - 3) + "...";
/*      */       }
/*      */     }
/* 1967 */     return stringToTrim;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isAssociated(ArrayList selectedList, String deviceName)
/*      */   {
/* 1975 */     for (int i = 0; i < selectedList.size(); i++)
/*      */     {
/* 1977 */       ArrayList tempList = (ArrayList)selectedList.get(i);
/* 1978 */       String tempDeviceName = (String)tempList.get(0);
/* 1979 */       if (deviceName.indexOf("$") != -1)
/*      */       {
/* 1981 */         deviceName = deviceName.substring(0, deviceName.indexOf("$"));
/*      */       }
/* 1983 */       if (deviceName.equals(tempDeviceName))
/*      */       {
/* 1985 */         return true;
/*      */       }
/*      */     }
/* 1988 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Hashtable getAddedNetworkDevices(String owner, String role, String type, HttpServletRequest request)
/*      */   {
/* 1996 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1997 */     Hashtable rethash = new Hashtable();
/*      */     
/* 1999 */     ResultSet set = null;
/* 2000 */     String qry = "select CATEGORY, count(*) as count from ExternalDeviceDetails where CATEGORY like 'OpManager-%' and CATEGORY NOT in ('OpManager-Interface') group by CATEGORY";
/*      */     
/* 2002 */     String siteId = "-1";
/* 2003 */     HashMap filterprop = new HashMap();
/* 2004 */     String siteFilterCondn = "";
/* 2005 */     if (EnterpriseUtil.isIt360MSPEdition)
/*      */     {
/* 2007 */       String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 2008 */       if (!custId.equals("-1"))
/*      */       {
/* 2010 */         Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/* 2011 */         siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */       }
/* 2013 */       filterprop.put("siteId", siteId);
/* 2014 */       siteFilterCondn = " and " + CustomerManagementAPI.getCondition("AM_ManagedObject.RESOURCEID", CustomerManagementAPI.filterResourceIds(filterprop));
/* 2015 */       qry = "select ExternalDeviceDetails.CATEGORY, count(*) as count from ExternalDeviceDetails,AM_ManagedObject,AM_AssociatedExtDevices where ExternalDeviceDetails.CATEGORY like 'OpManager-%' and ExternalDeviceDetails.CATEGORY NOT in ('OpManager-Interface') and AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID and AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME " + siteFilterCondn + " group by ExternalDeviceDetails.CATEGORY";
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 2020 */       set = AMConnectionPool.executeQueryStmt(qry);
/* 2021 */       while (set.next())
/*      */       {
/* 2023 */         Hashtable hash = new Hashtable();
/* 2024 */         ArrayList alist = new ArrayList();
/* 2025 */         String category = set.getString("CATEGORY");
/* 2026 */         hash.put("count", set.getString("count"));
/* 2027 */         hash.put("type", category);
/*      */         
/* 2029 */         ArrayList lists = new ArrayList();
/* 2030 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 2032 */           lists = this.mo.getRows("select ExternalDeviceDetails.DISPLAYNAME,AM_ManagedObject.RESOURCEID from AM_ManagedObject, ExternalDeviceDetails, AM_AssociatedExtDevices where AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID and AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME and AM_ManagedObject.TYPE=ExternalDeviceDetails.TYPE and ExternalDeviceDetails.CATEGORY='" + category + "'" + siteFilterCondn);
/*      */         }
/*      */         else
/*      */         {
/* 2036 */           lists = this.mo.getRows("select DISPLAYNAME from ExternalDeviceDetails where CATEGORY='" + category + "'");
/*      */         }
/*      */         
/* 2039 */         hash.put("devices", lists);
/* 2040 */         alist.add(hash);
/* 2041 */         rethash.put(category, alist);
/*      */       }
/* 2043 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2047 */       e.printStackTrace();
/*      */     }
/*      */     
/* 2050 */     return rethash;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Hashtable getAddedServAppl(String owner, String role, String type, HttpServletRequest request)
/*      */   {
/* 2060 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2061 */     Hashtable rethash = new Hashtable();
/*      */     
/* 2063 */     ResultSet set = null;
/* 2064 */     ResultSet setWin = null;
/* 2065 */     String qry = new String();
/* 2066 */     String qryWin = new String();
/*      */     
/* 2068 */     String siteId = "-1";
/* 2069 */     HashMap filterprop = new HashMap();
/* 2070 */     String siteFilterCondn = "";
/* 2071 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/* 2073 */       String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 2074 */       if (!custId.equals("-1"))
/*      */       {
/* 2076 */         Vector siteIdVec = CustomerManagementAPI.getInstance().getAllSiteIdForCustomer(custId, request);
/* 2077 */         siteId = Constants.convertVectorToCSV(siteIdVec);
/*      */       }
/* 2079 */       filterprop.put("siteId", siteId);
/* 2080 */       siteFilterCondn = " and " + CustomerManagementAPI.getCondition("bd.RESOURCEID", CustomerManagementAPI.filterResourceIds(filterprop));
/*      */     }
/*      */     
/*      */ 
/* 2084 */     if (!role.equals("operator"))
/*      */     {
/* 2086 */       if (type.equalsIgnoreCase("server"))
/*      */       {
/* 2088 */         qry = "select ab.DISPLAYNAME, count(*) as count,ab.RESOURCEGROUP,ab.SUBGROUP from AM_ManagedResourceType as ab,AM_ManagedObject as bd where ab.RESOURCETYPE=bd.TYPE and ab.RESOURCEGROUP like 'SYS' " + siteFilterCondn + " group by ab.DISPLAYNAME,ab.RESOURCEGROUP,ab.SUBGROUP order by ab.DISPLAYNAME";
/*      */       }
/* 2090 */       else if (type.equalsIgnoreCase("application"))
/*      */       {
/* 2092 */         qry = "select ab.DISPLAYNAME, count(*) as count,ab.RESOURCEGROUP,ab.SUBGROUP from AM_ManagedResourceType as ab,AM_ManagedObject as bd where ab.RESOURCETYPE=bd.TYPE and ab.RESOURCEGROUP not in ('SYS','NWD','EMO') " + siteFilterCondn + " group by ab.DISPLAYNAME,ab.RESOURCEGROUP,ab.SUBGROUP order by ab.DISPLAYNAME";
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2097 */         qry = "select ab.DISPLAYNAME, count(*) as count,ab.RESOURCEGROUP,ab.SUBGROUP from AM_ManagedResourceType as ab,AM_ManagedObject as bd where ab.RESOURCETYPE=bd.TYPE and SUBGROUP !='Windows' " + siteFilterCondn + " group by ab.DISPLAYNAME,ab.RESOURCEGROUP,ab.SUBGROUP order by ab.DISPLAYNAME";
/*      */         
/* 2099 */         qryWin = "select ab.SUBGROUP, count(*) as count,ab.RESOURCEGROUP from AM_ManagedResourceType as ab,AM_ManagedObject as bd where ab.RESOURCETYPE=bd.TYPE and SUBGROUP = 'Windows' " + siteFilterCondn + " group by ab.SUBGROUP,ab.RESOURCEGROUP order by ab.SUBGROUP";
/*      */       }
/*      */     }
/*      */     else {
/* 2103 */       Vector ridlist = Constants.getResourceIdentity(owner);
/* 2104 */       String resids = Constants.getCondition("pcm.PARENTID", ridlist);
/* 2105 */       String query = "select pcm.CHILDID from AM_PARENTCHILDMAPPER as pcm,AM_HOLISTICAPPLICATION as ha where " + resids + " and ha.HAID=pcm.PARENTID and ha.TYPE=1 " + siteFilterCondn + " group by pcm.CHILDID";
/*      */       try
/*      */       {
/* 2108 */         set = AMConnectionPool.executeQueryStmt(query);
/* 2109 */         while (set.next())
/*      */         {
/* 2111 */           ridlist.add(set.getString("CHILDID"));
/*      */         }
/*      */       }
/*      */       catch (Exception e2)
/*      */       {
/* 2116 */         e2.printStackTrace();
/*      */       }
/* 2118 */       String condition = Constants.getCondition("bd.RESOURCEID ", ridlist);
/*      */       
/*      */ 
/* 2121 */       if (type.equalsIgnoreCase("server"))
/*      */       {
/* 2123 */         qry = "select ab.DISPLAYNAME, count(*) as count,ab.RESOURCEGROUP,ab.SUBGROUP from AM_ManagedResourceType as ab,AM_ManagedObject as bd where ab.RESOURCETYPE=bd.TYPE and bd.TYPE in " + Constants.serverTypes + " and " + condition + " group by ab.DISPLAYNAME,ab.RESOURCEGROUP,ab.SUBGROUP order by ab.DISPLAYNAME";
/*      */       }
/* 2125 */       else if (type.equalsIgnoreCase("application"))
/*      */       {
/* 2127 */         qry = "select ab.DISPLAYNAME, count(*) as count,ab.RESOURCEGROUP,ab.SUBGROUP from AM_ManagedResourceType as ab,AM_ManagedObject as bd where ab.RESOURCETYPE=bd.TYPE and bd.TYPE in " + Constants.resourceTypes + " and bd.TYPE not in " + Constants.serverTypes + " and " + condition + " group by ab.DISPLAYNAME,ab.RESOURCEGROUP,ab.SUBGROUP order by ab.DISPLAYNAME";
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2132 */         qry = "select ab.DISPLAYNAME, count(*) as count,ab.RESOURCEGROUP,ab.SUBGROUP from AM_ManagedResourceType as ab,AM_ManagedObject as bd where ab.RESOURCETYPE=bd.TYPE and bd.TYPE in " + Constants.resourceTypesNonWin + " and " + condition + " group by ab.DISPLAYNAME,ab.RESOURCEGROUP,ab.SUBGROUP order by ab.DISPLAYNAME";
/*      */         
/* 2134 */         qryWin = "select ab.SUBGROUP, count(*) as count,ab.RESOURCEGROUP from AM_ManagedResourceType as ab,AM_ManagedObject as bd where ab.RESOURCETYPE=bd.TYPE and bd.TYPE in " + Constants.resourceTypesWin + " and " + condition + " group by ab.SUBGROUP,ab.RESOURCEGROUP order by ab.SUBGROUP";
/*      */       }
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 2140 */       ArrayList aListCategoryLink = new ArrayList(Arrays.asList(Constants.categoryLink));
/* 2141 */       ArrayList aListCategoryTitle = new ArrayList(Arrays.asList(Constants.categoryTitle));
/*      */       
/* 2143 */       String sevMsg = null;
/* 2144 */       set = AMConnectionPool.executeQueryStmt(qry);
/* 2145 */       while (set.next())
/*      */       {
/* 2147 */         String resourceGrp = set.getString("RESOURCEGROUP");
/* 2148 */         int idx = aListCategoryLink.indexOf(resourceGrp);
/* 2149 */         if (idx != -1)
/*      */         {
/*      */ 
/* 2152 */           Hashtable hash = new Hashtable();
/* 2153 */           hash.put("displayname", set.getString("DISPLAYNAME"));
/* 2154 */           hash.put("count", set.getString("count"));
/* 2155 */           hash.put("subgroup", set.getString("SUBGROUP"));
/* 2156 */           if ((!resourceGrp.equals("NWD")) || (!resourceGrp.equals("EMO")))
/*      */           {
/* 2158 */             String keyString = FormatUtil.getString(Constants.categoryTitle[idx]) + ":" + resourceGrp;
/* 2159 */             ArrayList aListTemp = (ArrayList)rethash.get(keyString);
/* 2160 */             if (aListTemp != null)
/*      */             {
/* 2162 */               aListTemp.add(hash);
/*      */             } else {
/* 2164 */               ArrayList list = new ArrayList();
/* 2165 */               list.add(hash);
/* 2166 */               rethash.put(keyString, list);
/*      */             }
/*      */           }
/*      */         } }
/* 2170 */       AMConnectionPool.closeStatement(set);
/* 2171 */       if (!qryWin.trim().equals(""))
/*      */       {
/* 2173 */         setWin = AMConnectionPool.executeQueryStmt(qryWin);
/* 2174 */         while (setWin.next())
/*      */         {
/* 2176 */           Hashtable hash = new Hashtable();
/* 2177 */           if (setWin.getString("RESOURCEGROUP").equals("SYS"))
/*      */           {
/* 2179 */             hash.put("displayname", setWin.getString("SUBGROUP"));
/* 2180 */             hash.put("count", setWin.getString("count"));
/* 2181 */             hash.put("subgroup", setWin.getString("SUBGROUP"));
/* 2182 */             String keyString = FormatUtil.getString("Servers") + ":SYS";
/* 2183 */             ArrayList aListTemp = (ArrayList)rethash.get(keyString);
/* 2184 */             if (aListTemp != null)
/*      */             {
/* 2186 */               aListTemp.add(hash);
/*      */             } else {
/* 2188 */               ArrayList list = new ArrayList();
/* 2189 */               list.add(hash);
/* 2190 */               rethash.put(keyString, list);
/*      */             }
/*      */           }
/*      */         }
/* 2194 */         AMConnectionPool.closeStatement(setWin);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2199 */       e.printStackTrace();
/*      */     }
/* 2201 */     return rethash;
/*      */   }
/*      */   
/*      */   public String getImageForCategory(String category)
/*      */   {
/* 2206 */     String getImage = "select IMAGEPATH from AM_ManagedResourceType where SUBGROUP='" + category + "'";
/* 2207 */     ArrayList imgList = this.mo.getRows(getImage);
/* 2208 */     String imgPath = File.separator + "images" + File.separator + "icon_monitors_nwd.gif";
/* 2209 */     if (imgList.size() > 0)
/*      */     {
/* 2211 */       List tempImg = (List)imgList.get(0);
/* 2212 */       imgPath = (String)tempImg.get(0);
/*      */     }
/* 2214 */     return imgPath;
/*      */   }
/*      */   
/*      */   public String getOPMUrl()
/*      */   {
/* 2219 */     opmUrl = "http://localhost";
/* 2220 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2223 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2224 */       String qry = "select PROTOCOL,HOST,PORT from AM_IntegratedProducts where PRODUCT_NAME='OpManager'";
/* 2225 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 2226 */       String protocol; String hostname; String port_number; if (rs.next())
/*      */       {
/* 2228 */         protocol = rs.getString(1);
/* 2229 */         hostname = rs.getString(2);
/* 2230 */         port_number = rs.getString(3); }
/* 2231 */       return protocol + "://" + hostname + ":" + port_number;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2235 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2241 */         if (rs != null)
/*      */         {
/* 2243 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       catch (Exception exp) {
/* 2247 */         exp.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static void addSubGroup(String childHaid, String parentHaid)
/*      */   {
/* 2255 */     AMLog.debug("CreateBusinessService : About to add as a subgroup with haid : " + childHaid + " for parent  : " + parentHaid);
/*      */     try
/*      */     {
/* 2258 */       long id = DBUtil.insertParentChildMapper(Integer.parseInt(parentHaid), Integer.parseInt(childHaid));
/* 2259 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 2260 */       added = adder.addDependentAttributes(Integer.parseInt(parentHaid), Integer.parseInt(childHaid));
/*      */     }
/*      */     catch (Exception exp) {
/*      */       boolean added;
/* 2264 */       exp.printStackTrace();
/*      */     }
/* 2266 */     AMLog.debug("CreateBusinessService : Successfully added as a subgroup with haid : " + childHaid + " for parent  : " + parentHaid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int checkForNameConflict(String bsId, String parentHaid, String applicationName)
/*      */   {
/* 2275 */     boolean isSubGroup = false;
/* 2276 */     if (!parentHaid.equals("-1"))
/*      */     {
/* 2278 */       isSubGroup = true;
/*      */     }
/* 2280 */     if (!bsId.equals("-1"))
/*      */     {
/* 2282 */       isSubGroup = Constants.isSubGroup(bsId);
/* 2283 */       if ((isSubGroup) && (parentHaid.equals("-1")))
/*      */       {
/* 2285 */         parentHaid = Constants.getParentHaid(bsId);
/*      */       }
/*      */     }
/* 2288 */     if (!isSubGroup)
/*      */     {
/*      */ 
/* 2291 */       ArrayList lists = this.mo.getRows("select AM_ManagedObject.RESOURCEID from AM_ManagedObject where AM_ManagedObject.RESOURCENAME='" + applicationName + "' and AM_ManagedObject.TYPE='HAI' ");
/* 2292 */       String resultHaid = null;
/* 2293 */       if (lists.size() > 0)
/*      */       {
/* 2295 */         ArrayList list = (ArrayList)lists.get(0);
/* 2296 */         resultHaid = (String)list.get(0);
/* 2297 */         if (bsId.equals("-1"))
/*      */         {
/*      */ 
/* 2300 */           return 1;
/*      */         }
/* 2302 */         if (!bsId.equals(resultHaid))
/*      */         {
/*      */ 
/* 2305 */           return 2;
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 2312 */       ArrayList sgList = Constants.getSubGroupsForThisBS(parentHaid);
/* 2313 */       if (sgList.size() > 0)
/*      */       {
/* 2315 */         for (Object subgroup : sgList)
/*      */         {
/* 2317 */           ArrayList tempList = (ArrayList)subgroup;
/* 2318 */           String tempSgId = (String)tempList.get(0);
/* 2319 */           String tempSgName = (String)tempList.get(1);
/* 2320 */           if ((bsId.equals("-1")) && (tempSgName.equals(applicationName)))
/*      */           {
/*      */ 
/* 2323 */             return 3;
/*      */           }
/* 2325 */           if ((!bsId.equals(tempSgId)) && (tempSgName.equals(applicationName)))
/*      */           {
/*      */ 
/* 2328 */             return 4;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2333 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private ActionForward returnNameConflict(int key, String bsId, String parentHaid, String applicationName, HttpServletRequest request)
/*      */   {
/* 2341 */     ActionMessages messages = new ActionMessages();
/* 2342 */     ActionErrors errors = new ActionErrors();
/* 2343 */     if (key == 1)
/*      */     {
/* 2345 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("am.webclient.bs.name.exists.txt", applicationName));
/* 2346 */       saveMessages(request, messages);
/* 2347 */       return new ActionForward("/jsp/CreateBusinessService.jsp");
/*      */     }
/* 2349 */     if (key == 2)
/*      */     {
/* 2351 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("am.webclient.bs.name.exists.txt", applicationName));
/* 2352 */       saveMessages(request, messages);
/* 2353 */       return new ActionForward("/createBS.do?method=editApplication&haid=" + bsId);
/*      */     }
/* 2355 */     if (key == 3)
/*      */     {
/* 2357 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("am.webclient.bs.subgroup.name.exists.txt", applicationName));
/* 2358 */       saveMessages(request, messages);
/* 2359 */       return new ActionForward("/jsp/CreateBusinessService.jsp?parentHaid" + parentHaid);
/*      */     }
/*      */     
/*      */ 
/* 2363 */     messages.add("org.apache.struts.action.ERROR", new ActionMessage("am.webclient.bs.subgroup.name.exists.txt", applicationName));
/* 2364 */     saveMessages(request, messages);
/* 2365 */     return new ActionForward("/createBS.do?method=editApplication&haid=" + bsId);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getParentServerId(String resId)
/*      */   {
/* 2376 */     parentDevId = "-1";
/* 2377 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2380 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2381 */       String qry = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject where AM_ManagedObject.RESOURCENAME = (select InetService.TARGETNAME from InetService,AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + resId + " and AM_ManagedObject.RESOURCENAME=InetService.NAME)";
/* 2382 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 2383 */       if (rs.next()) {}
/*      */       
/* 2385 */       return rs.getString(1);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2389 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2395 */         if (rs != null)
/*      */         {
/* 2397 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       catch (Exception exp) {
/* 2401 */         exp.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getSiteIdFromReqOrDB(HttpServletRequest request, String haid)
/*      */   {
/* 2412 */     String siteId = "-1";
/* 2413 */     if ((request.getParameter("siteId") != null) && (!request.getParameter("siteId").equals("-1")))
/*      */     {
/* 2415 */       siteId = request.getParameter("siteId");
/* 2416 */       AMLog.debug("siteId setting from request::: " + siteId);
/*      */     }
/*      */     else
/*      */     {
/* 2420 */       if ((!CustomerManagementAPI.isSiteId(haid)) && (!haid.equals("-1")))
/*      */       {
/* 2422 */         String[] siteIds = CustomerManagementAPI.getSiteIdsFromChildBS(haid);
/* 2423 */         siteId = Constants.convertArrayToCSV(siteIds);
/*      */       }
/*      */       else
/*      */       {
/* 2427 */         siteId = haid;
/*      */       }
/* 2429 */       AMLog.debug("siteId setting from DB:::: " + siteId);
/*      */     }
/* 2431 */     return siteId;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\CreateBusinessService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */