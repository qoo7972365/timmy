/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.ScheduleScriptDataCollection;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ public class ManageMonitorType
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward createType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  42 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  43 */     ActionMessages messages = new ActionMessages();
/*  44 */     ActionErrors errors = new ActionErrors();
/*  45 */     String resourcetype = ((AMActionForm)form).getDisplayname();
/*  46 */     String string_att = ((AMActionForm)form).getString_att().trim();
/*  47 */     String numeric_att = ((AMActionForm)form).getNumeric_att().trim();
/*  48 */     ArrayList stringattributes = CreateScriptMonitor.getAttributes(string_att);
/*  49 */     ArrayList numericattributes = CreateScriptMonitor.getAttributes(numeric_att);
/*  50 */     String delimiter = ((AMActionForm)form).getDelimiter();
/*  51 */     String hastables = request.getParameter("tablespresent");
/*  52 */     String resourcegroup = ((AMActionForm)form).getChoosehost();
/*     */     
/*  54 */     String baseType = request.getParameter("type");
/*  55 */     int serverid = EnterpriseUtil.getManagedServerIndex();
/*  56 */     String displayname = resourcetype;
/*  57 */     String locale = System.getProperty("locale");
/*  58 */     if ((locale != null) && (!locale.trim().equals("en_US")))
/*     */     {
/*  60 */       StringTokenizer str = new StringTokenizer(locale, "_");
/*  61 */       resourcetype = getResType(str.nextToken()) + "_" + String.valueOf(serverid);
/*     */     }
/*     */     else
/*     */     {
/*  65 */       resourcetype = resourcetype + "_" + String.valueOf(serverid);
/*     */     }
/*  67 */     HttpSession ht = request.getSession();
/*  68 */     ServletContext sct = ht.getServletContext();
/*  69 */     int typeid = NewMonitorUtil.createNewType(resourcetype, resourcegroup, baseType, displayname, sct, resourcetype, resourcetype, "/images/ScriptMonitoring.gif");
/*  70 */     String error = "success";
/*  71 */     String res_group = "";
/*     */     
/*  73 */     if (resourcegroup.equals("APP"))
/*     */     {
/*  75 */       res_group = "Application servers";
/*     */     }
/*  77 */     else if (resourcegroup.equals("TM"))
/*     */     {
/*  79 */       res_group = "Transaction Monitors";
/*     */     }
/*  81 */     else if (resourcegroup.equals("MS"))
/*     */     {
/*  83 */       res_group = "Mail Servers";
/*     */     }
/*  85 */     else if (resourcegroup.equals("DBS"))
/*     */     {
/*  87 */       res_group = "Database Servers";
/*     */     }
/*  89 */     else if (resourcegroup.equals("WEB"))
/*     */     {
/*  91 */       res_group = "Web Services";
/*     */     }
/*  93 */     else if (resourcegroup.equals("SER"))
/*     */     {
/*  95 */       res_group = "Services";
/*     */     }
/*  97 */     else if (resourcegroup.equals("SYS"))
/*     */     {
/*  99 */       res_group = "Servers";
/*     */     }
/* 101 */     else if (resourcegroup.equals("CAM"))
/*     */     {
/* 103 */       res_group = "Custom Monitors";
/* 104 */     } else if (resourcegroup.equals("MOM"))
/*     */     {
/* 106 */       res_group = "Middleware Servers";
/* 107 */     } else if (resourcegroup.equals("ERP"))
/*     */     {
/* 109 */       res_group = "ERP Monitors";
/*     */     }
/* 111 */     else if (resourcegroup.equals("VIR"))
/*     */     {
/* 113 */       res_group = "Virtual Servers";
/*     */     }
/* 115 */     else if (resourcegroup.equals("CLD"))
/*     */     {
/* 117 */       res_group = "Cloud Apps";
/*     */     }
/*     */     
/* 120 */     if (typeid == -1)
/*     */     {
/* 122 */       error = "Monitor Type " + resourcetype + " is already Present";
/* 123 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("am.webclient.monitortypealready.exists"));
/* 124 */       saveErrors(request, errors);
/*     */     }
/* 126 */     else if (typeid == -2)
/*     */     {
/* 128 */       error = "Exception while trying to create a new Monitor Type. Check the displayname and other attributes specified.";
/* 129 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("am.webclient.monitortypecreation.failure"));
/* 130 */       saveErrors(request, errors);
/*     */     }
/*     */     else
/*     */     {
/* 134 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitortypecreation.success"));
/* 135 */       if ((delimiter == null) || (delimiter.trim().equals("")))
/*     */       {
/* 137 */         delimiter = "s";
/*     */       }
/* 139 */       String version = "1";
/* 140 */       NewMonitorUtil.typeMapping(typeid, resourcetype, baseType, res_group, delimiter, version);
/*     */       try
/*     */       {
/* 143 */         String archivalTable = "AM_" + typeid + "_MinMaxAvgData";
/* 144 */         String archivalMappingQry = "insert into AM_Custom_Archival_Mapping(NAME,TABLENAME) values('" + resourcetype + "','" + archivalTable + "')";
/* 145 */         AMConnectionPool.executeUpdateStmt(archivalMappingQry);
/*     */       }
/*     */       catch (Exception exc)
/*     */       {
/* 149 */         exc.printStackTrace();
/*     */       }
/* 151 */       NewMonitorUtil.createDataTables(typeid, resourcetype, null);
/* 152 */       saveMessages(request, messages);
/* 153 */       ArrayList num_str = new ArrayList();
/* 154 */       num_str.add(stringattributes);
/* 155 */       num_str.add(numericattributes);
/* 156 */       String toappend = "_" + typeid;
/*     */       
/* 158 */       Properties props = new Properties();
/* 159 */       String archivalTable = "AM" + toappend + "_MinMaxAvgData";
/*     */       
/* 161 */       ArrayList idList = new ArrayList();
/* 162 */       Properties typeProps = NewMonitorUtil.getAttributeProps(resourcetype);
/* 163 */       idList.add(typeProps.getProperty("Availability"));
/* 164 */       idList.add(typeProps.getProperty("Health"));
/* 165 */       idList.add(typeProps.getProperty("ResponseTime"));
/* 166 */       int healthid = NewMonitorUtil.insertAttributes(typeid, num_str, resourcetype, toappend, props, "NO", idList, null, null, archivalTable);
/* 167 */       CreateScriptMonitor.manageTableAtts(request, typeid, healthid, toappend, resourcetype, "usebaseidandtableid");
/* 168 */       Constants.setResourceTypes(resourcetype);
/*     */     }
/* 170 */     request.setAttribute("error", error);
/*     */     
/* 172 */     return new ActionForward("/monitorType.do?method=showTypes");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getResType(String token)
/*     */   {
/* 181 */     String restype = token + "1";
/* 182 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 183 */     int max = 0;
/*     */     try
/*     */     {
/* 186 */       String qry = "select TYPENAME from AM_MONITOR_TYPES where AMCREATED='NO'";
/* 187 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 188 */       while (rs.next())
/*     */       {
/* 190 */         StringTokenizer st1 = new StringTokenizer(rs.getString(1), "_");
/* 191 */         String type = st1.nextToken();
/* 192 */         int max1 = Integer.parseInt(type.substring(token.length()));
/* 193 */         if (max1 > max)
/*     */         {
/* 195 */           max = max1;
/*     */         }
/*     */       }
/* 198 */       rs.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 202 */       exc.printStackTrace();
/*     */     }
/* 204 */     return token + String.valueOf(max + 1);
/*     */   }
/*     */   
/*     */   public ActionForward callReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 209 */     int resourceid = Integer.parseInt(request.getParameter("baseid"));
/* 210 */     Hashtable ht = getAttributesForReports(resourceid);
/* 211 */     request.setAttribute("attributes", ht);
/* 212 */     return new ActionForward("/jsp/NewTypeReports.jsp?baseid=" + resourceid);
/*     */   }
/*     */   
/*     */   public ActionForward enableReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 217 */     String[] applications = request.getParameterValues("checkbox");
/* 218 */     String baseid = request.getParameter("baseid");
/* 219 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 220 */     ActionMessages messages = new ActionMessages();
/* 221 */     ActionErrors errors = new ActionErrors();
/* 222 */     String qry = "select attributeid from AM_Script_Resource_Attributes_Mapper WHERE resourceid=" + baseid;
/* 223 */     String qry1 = "";
/* 224 */     ArrayList al = new ArrayList();
/*     */     try
/*     */     {
/* 227 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 228 */       while (rs.next())
/*     */       {
/* 230 */         al.add(rs.getString(1));
/*     */       }
/* 232 */       rs.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 236 */       exc.printStackTrace();
/*     */     }
/*     */     try
/*     */     {
/* 240 */       AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/* 241 */       ManagedApplication manAppUtil = new ManagedApplication();
/* 242 */       String appAttrIds = StringUtils.join(applications, ",");
/* 243 */       ArrayList archEnabledAttrs = manAppUtil.getRowsForSingleColumn("select ATTRIBUTEID from AM_ArchiverConfig where ATTRIBUTEID in (" + appAttrIds + ")");
/* 244 */       for (int i = 0; i < applications.length; i++)
/*     */       {
/* 246 */         if ((applications[i] != null) && (!archEnabledAttrs.contains(applications[i]))) {
/* 247 */           if (al.contains(applications[i]))
/*     */           {
/* 249 */             qry = "insert into AM_ArchiverConfig values ('AM_Script_Numeric_Data','VALUE','RESOURCEID'," + applications[i] + ")";
/* 250 */             qry1 = "update AM_Script_Resource_Attributes_Mapper set REPORTS='1' where attributeid=" + applications[i];
/*     */           }
/*     */           else
/*     */           {
/* 254 */             qry = "insert into AM_ArchiverConfig values ('AM_SCRIPT_TABULAR_NUMERIC_DATA','VALUE','RESID'," + applications[i] + ")";
/* 255 */             qry1 = "update AM_CAM_DC_ATTRIBUTES set DISPLAYTYPE=1 where ATTRIBUTEID=" + applications[i];
/*     */           }
/* 257 */           String qry2 = "update AM_ATTRIBUTES_EXT set isarchiveing=1,REPORTS_ENABLED=1 where attributeid=" + applications[i];
/*     */           
/* 259 */           EnterpriseUtil.addUpdateQueryToFile(qry1);
/* 260 */           EnterpriseUtil.addUpdateQueryToFile(qry2);
/* 261 */           toinsert.addBatch(qry);
/* 262 */           toinsert.addBatch(qry1);
/* 263 */           toinsert.addBatch(qry2);
/*     */         }
/*     */       }
/* 266 */       toinsert.executeBatch();
/* 267 */       toinsert.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 271 */       exc.printStackTrace();
/*     */     }
/*     */     try {
/* 274 */       ReportUtil.loadAllAttributeDetails();
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 278 */       ee.printStackTrace();
/*     */     }
/* 280 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.reportsenabled.success")));
/* 281 */     saveMessages(request, messages);
/* 282 */     return new ActionForward("/monitorType.do?method=showTypes");
/*     */   }
/*     */   
/*     */   public ActionForward disableReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 287 */     String[] applications = request.getParameterValues("checkbox");
/* 288 */     String baseid = request.getParameter("baseid");
/* 289 */     ActionMessages messages = new ActionMessages();
/* 290 */     ActionErrors errors = new ActionErrors();
/*     */     try
/*     */     {
/* 293 */       AMConnectionPool.getInstance();Statement todelete = AMConnectionPool.getConnection().createStatement();
/* 294 */       for (int i = 0; i < applications.length; i++)
/*     */       {
/* 296 */         String qry = "delete from AM_ArchiverConfig where ATTRIBUTEID=" + applications[i];
/* 297 */         String qry1 = "update AM_ATTRIBUTES_EXT set isarchiveing=0,REPORTS_ENABLED=0 where attributeid=" + applications[i];
/* 298 */         String qry2 = "update AM_Script_Resource_Attributes_Mapper set REPORTS='0' where attributeid=" + applications[i];
/* 299 */         todelete.addBatch(qry);
/* 300 */         todelete.addBatch(qry1);
/* 301 */         todelete.addBatch(qry2);
/*     */         
/*     */ 
/* 304 */         EnterpriseUtil.addUpdateQueryToFile(qry2);
/*     */       }
/* 306 */       todelete.executeBatch();
/* 307 */       todelete.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 311 */       exc.printStackTrace();
/*     */     }
/*     */     try {
/* 314 */       ReportUtil.loadAllAttributeDetails();
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 318 */       ee.printStackTrace();
/*     */     }
/* 320 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.reportsdisabled.success")));
/* 321 */     saveMessages(request, messages);
/* 322 */     return new ActionForward("/monitorType.do?method=showTypes");
/*     */   }
/*     */   
/*     */   private static Hashtable getAttributesForReports(int resourceid)
/*     */   {
/* 327 */     Hashtable ht = new Hashtable();
/* 328 */     ResultSet rs1 = null;
/* 329 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 334 */     String query = "select DISPLAYNAME,COALESCE(DATATABLE,'NOTABLE'),AM_Script_Resource_Attributes_Mapper.attributeid from AM_ATTRIBUTES left join AM_Script_Resource_Attributes_Mapper on AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.attributeid=AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID and AM_ATTRIBUTES_EXT.isarchiveing=1 where resourceid=" + resourceid + " and TYPE=0";
/*     */     try
/*     */     {
/* 337 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 338 */       Properties p = new Properties();
/* 339 */       Hashtable main = new Hashtable();
/* 340 */       while (rs1.next())
/*     */       {
/* 342 */         Properties p1 = new Properties();
/* 343 */         p1.setProperty("NAME", rs1.getString(1));
/* 344 */         if ((rs1.getString(2) != null) && (rs1.getString(2).equals("NOTABLE")))
/*     */         {
/* 346 */           p1.setProperty("ISPRESENT", "NO");
/*     */         }
/*     */         else
/*     */         {
/* 350 */           p1.setProperty("ISPRESENT", "YES");
/*     */         }
/* 352 */         main.put(rs1.getString(3), p1);
/*     */       }
/* 354 */       ht.put("SIMPLE", main);
/* 355 */       rs1.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 359 */       System.out.println("Exception in getting the Numeric Attributes!!!");
/* 360 */       exc.printStackTrace();
/*     */     }
/* 362 */     query = "select tableid,tablename from AM_SCRIPT_TABLES WHERE SCRIPTID=" + resourceid;
/* 363 */     ResultSet rs2 = null;
/*     */     try
/*     */     {
/* 366 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 367 */       Hashtable main = new Hashtable();
/* 368 */       while (rs1.next())
/*     */       {
/*     */ 
/* 371 */         query = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID,ATTRIBUTENAME,COALESCE(DATATABLE ,'NOTABLE') from AM_CAM_DC_ATTRIBUTES LEFT OUTER JOIN AM_ATTRIBUTES_EXT on AM_ATTRIBUTES_EXT.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID  and AM_ATTRIBUTES_EXT.ISARCHIVEING =1 where GROUPID=" + rs1.getString(1) + " and type=0";
/*     */         try
/*     */         {
/* 374 */           rs2 = AMConnectionPool.executeQueryStmt(query);
/* 375 */           while (rs2.next())
/*     */           {
/* 377 */             Properties p1 = new Properties();
/* 378 */             p1.setProperty("NAME", rs2.getString(2));
/* 379 */             if ((rs2.getString(3) != null) && (rs2.getString(3).equals("NOTABLE")))
/*     */             {
/* 381 */               p1.setProperty("ISPRESENT", "NO");
/*     */             }
/*     */             else
/*     */             {
/* 385 */               p1.setProperty("ISPRESENT", "YES");
/*     */             }
/* 387 */             p1.setProperty("TABLENAME", rs1.getString(2));
/* 388 */             main.put(rs2.getString(1), p1);
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 393 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 396 */       ht.put("TABLE", main);
/* 397 */       rs1.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 401 */       exc.printStackTrace();
/*     */     }
/* 403 */     return ht;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void closeResultSet(ResultSet rs)
/*     */   {
/*     */     try
/*     */     {
/* 415 */       rs.close();
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward showTypes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 424 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 425 */     String qry1 = "select AM_ManagedResourceType.DISPLAYNAME,count(*) as num  from AM_MONITOR_TYPES,AM_ManagedObject,AM_ManagedResourceType where TYPENAME=TYPE  and RESOURCETYPE=TYPE GROUP BY AM_ManagedResourceType.DISPLAYNAME";
/* 426 */     Properties p1 = new Properties();
/* 427 */     String qry = "select TYPEID,DISPLAYNAME,BASETYPE,TYPENAME from AM_MONITOR_TYPES,AM_ManagedResourceType where resourcetype=typename and  RESOURCEGROUP NOT IN('NWD','EMO','SAN') and AMCREATED <> 'YES'";
/* 428 */     Hashtable ht = new Hashtable();
/* 429 */     ResultSet rs = null;
/* 430 */     ResultSet rs1 = null;
/*     */     try
/*     */     {
/*     */       try
/*     */       {
/* 435 */         rs1 = AMConnectionPool.executeQueryStmt(qry1);
/* 436 */         while (rs1.next())
/*     */         {
/* 438 */           p1.setProperty(rs1.getString(1), rs1.getString(2));
/*     */         }
/* 440 */         rs1.close();
/*     */       }
/*     */       catch (Exception exc) {}
/*     */       
/*     */ 
/*     */ 
/* 446 */       ArrayList al = new ArrayList();
/* 447 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 448 */       while (rs.next())
/*     */       {
/* 450 */         al.add(rs.getString("TYPEID"));
/* 451 */         Properties p = new Properties();
/* 452 */         p.setProperty("Name", rs.getString("DISPLAYNAME"));
/* 453 */         p.setProperty("TypeName", rs.getString("TYPENAME"));
/* 454 */         p.setProperty("Base", rs.getString("BASETYPE"));
/* 455 */         if (p1.getProperty(rs.getString("DISPLAYNAME")) != null)
/*     */         {
/* 457 */           p.setProperty("Number", p1.getProperty(rs.getString("DISPLAYNAME")));
/*     */         }
/*     */         else
/*     */         {
/* 461 */           p.setProperty("Number", "0");
/*     */         }
/* 463 */         ht.put(rs.getString("TYPEID"), p);
/*     */       }
/* 465 */       ht.put("elements", al);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 475 */         rs.close();
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 482 */       request.setAttribute("monitortypes", ht);
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 469 */       exc.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 475 */         rs.close();
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 483 */     return new ActionForward("/jsp/ManageMonitorType.jsp");
/*     */   }
/*     */   
/*     */   private static String[] getResourceDetailsForTable(String tableid)
/*     */   {
/* 488 */     String qry = "select  TYPEID,TYPENAME from AM_SCRIPT_TABLES,AM_MONITOR_TYPES WHERE TABLEID='" + tableid + "' AND SCRIPTID=TYPEID";
/* 489 */     String[] toreturn = new String[2];
/* 490 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     try
/*     */     {
/* 493 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 494 */       if (rs.next())
/*     */       {
/* 496 */         toreturn[0] = rs.getString(1);
/* 497 */         toreturn[1] = rs.getString(2);
/*     */       }
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 502 */       exc.printStackTrace();
/*     */     }
/* 504 */     return toreturn;
/*     */   }
/*     */   
/*     */   public ActionForward updateTable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 509 */     String tableid = request.getParameter("tableid");
/* 510 */     System.out.println("Coming inside the updateTabele for tableid===>" + tableid);
/* 511 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     try
/*     */     {
/* 514 */       String tablename = request.getParameter("tname");
/* 515 */       String stratts = request.getParameter("stratts");
/* 516 */       String numatts = request.getParameter("numatts");
/* 517 */       String col_del = request.getParameter("coldelimiter");
/* 518 */       String pr_col = request.getParameter("uncol");
/* 519 */       Properties props = new Properties();
/* 520 */       props.setProperty("tableid", tableid);
/* 521 */       props.setProperty("tname", tablename);
/* 522 */       props.setProperty("stratts", stratts);
/* 523 */       props.setProperty("numatts", numatts);
/* 524 */       props.setProperty("coldelimiter", col_del);
/* 525 */       props.setProperty("uncol", pr_col);
/* 526 */       String[] resDetails = getResourceDetailsForTable(tableid);
/* 527 */       String archivalTable = "AM_" + resDetails[1] + "_" + resDetails[0] + "_MinMaxAvgData";
/* 528 */       props.setProperty("archivalTable", archivalTable);
/* 529 */       props.setProperty("monitorname", resDetails[1]);
/* 530 */       NewMonitorUtil.updateTable(props);
/* 531 */       ActionMessages messages = new ActionMessages();
/* 532 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Table updated Successfully"));
/* 533 */       saveMessages(request, messages);
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 537 */       exc.printStackTrace();
/*     */     }
/*     */     
/* 540 */     return new ActionForward("/jsp/EditTable.jsp?toclose=true&tableid=" + tableid + "&baseid=" + request.getParameter("baseid") + "&basetype=" + request.getParameter("basetype"), true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward editType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 547 */     int resourceid = Integer.parseInt(request.getParameter("baseid"));
/* 548 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 549 */     String query = "select * from AM_MONITOR_TYPES where typeid=" + resourceid;
/* 550 */     String grp = "select resourcegroup,displayname from AM_ManagedResourceType where resourcetypeid=" + resourceid;
/* 551 */     System.out.println("The grp==>" + grp);
/* 552 */     ResultSet rs = null;
/* 553 */     ResultSet rs1 = null;
/*     */     try
/*     */     {
/* 556 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 557 */       rs1 = AMConnectionPool.executeQueryStmt(grp);
/* 558 */       String opdelimiter_new = "";
/* 559 */       if (rs.next())
/*     */       {
/* 561 */         if ((rs.getString("opdelimiter") == null) || (rs.getString("opdelimiter").equals("s")))
/*     */         {
/* 563 */           opdelimiter_new = "";
/*     */         }
/*     */         else
/*     */         {
/* 567 */           opdelimiter_new = rs.getString("opdelimiter");
/*     */         }
/* 569 */         ((AMActionForm)form).setDelimiter(opdelimiter_new);
/* 570 */         if (rs1.next())
/*     */         {
/* 572 */           ((AMActionForm)form).setChoosehost(rs1.getString(1));
/* 573 */           ArrayList<String> aListCategoryLink = new ArrayList(Arrays.asList(Constants.categoryLink));
/* 574 */           ArrayList<String> aListCategoryTitle = new ArrayList(Arrays.asList(Constants.categoryTitle));
/* 575 */           String val = "am.webclient.monitorgroupsecond.category.appserver";
/* 576 */           if (rs1.getString(1) != null)
/*     */           {
/* 578 */             int idx = aListCategoryLink.indexOf(rs1.getString(1));
/* 579 */             if (idx != -1) {
/* 580 */               val = (String)aListCategoryTitle.get(idx);
/*     */             }
/*     */           }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 624 */           val = FormatUtil.getString(val);
/* 625 */           ((AMActionForm)form).setCategory(val);
/* 626 */           ((AMActionForm)form).setDisplayname(rs1.getString("displayname"));
/*     */         }
/* 628 */         ArrayList string_numeric = CreateScriptMonitor.getStringNumeric(resourceid);
/* 629 */         ((AMActionForm)form).setString_att((String)string_numeric.get(0));
/* 630 */         ((AMActionForm)form).setNumeric_att((String)string_numeric.get(1));
/*     */         
/* 632 */         CreateScriptMonitor.setTableParams(request, resourceid);
/*     */         
/* 634 */         rs.close();
/* 635 */         rs1.close();
/*     */       }
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 640 */       exc.printStackTrace();
/*     */     }
/* 642 */     return new ActionForward("/jsp/NewMonitorType.jsp?baseid=" + resourceid);
/*     */   }
/*     */   
/*     */   public String getType(int resourceid)
/*     */   {
/* 647 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 648 */     String toreturn = "";
/*     */     try
/*     */     {
/* 651 */       ResultSet rs = AMConnectionPool.executeQueryStmt("select TYPENAME from AM_MONITOR_TYPES where typeid=" + resourceid);
/* 652 */       if (rs.next())
/*     */       {
/* 654 */         toreturn = rs.getString("TYPENAME");
/*     */       }
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 659 */       exc.printStackTrace();
/*     */     }
/* 661 */     return toreturn;
/*     */   }
/*     */   
/*     */   public ActionForward deleteTable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 666 */     String tableid = request.getParameter("tableid");
/* 667 */     String baseid = request.getParameter("baseid");
/* 668 */     String basetype = request.getParameter("basetype");
/*     */     try {
/* 670 */       Long[] resourceids = DBUtil.getResourceIdForType(new String[] { basetype });
/* 671 */       if (resourceids.length == 0) {
/* 672 */         ScheduleScriptDataCollection.deleteTable(-1, tableid, basetype, baseid, true);
/*     */       }
/* 674 */       for (int i = 0; i < resourceids.length; i++) {
/* 675 */         int resourceid = Integer.parseInt(String.valueOf(resourceids[i]));
/* 676 */         boolean dropTable = false;
/* 677 */         if (i == resourceids.length - 1) {
/* 678 */           dropTable = true;
/*     */         }
/* 680 */         ScheduleScriptDataCollection.deleteTable(resourceid, tableid, basetype, baseid, dropTable);
/*     */       }
/*     */     } catch (Exception ex) {
/* 683 */       ex.printStackTrace();
/*     */     }
/* 685 */     ActionMessages messages = new ActionMessages();
/* 686 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Table Deleted Successfully"));
/* 687 */     saveMessages(request, messages);
/* 688 */     return new ActionForward("/monitorType.do?method=showTypes");
/*     */   }
/*     */   
/*     */   private static String getAttributeName(Object id)
/*     */   {
/* 693 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 694 */     String attribute = null;
/*     */     try
/*     */     {
/* 697 */       String q1 = "select ATTRIBUTE from AM_ATTRIBUTES WHERE ATTRIBUTEID=" + id;
/* 698 */       ResultSet rs = AMConnectionPool.executeQueryStmt(q1);
/* 699 */       if (rs.next())
/*     */       {
/* 701 */         attribute = rs.getString(1);
/*     */       }
/* 703 */       rs.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 707 */       exc.printStackTrace();
/*     */     }
/* 709 */     return attribute;
/*     */   }
/*     */   
/*     */   private static void removeUISettings(ArrayList todelete, int resourceid)
/*     */   {
/* 714 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 715 */     for (int i = 0; i < todelete.size(); i++)
/*     */     {
/*     */       try
/*     */       {
/* 719 */         String attribute = getAttributeName(todelete.get(i));
/* 720 */         String sel1 = "select groupid from AM_CUSTOM_MONITOR_GROUPS_CAPTION where TYPEID=" + resourceid + " and CAPTION='" + attribute + "'";
/* 721 */         ResultSet rs = AMConnectionPool.executeQueryStmt(sel1);
/* 722 */         int gcount = 0;
/* 723 */         if (rs.next())
/*     */         {
/* 725 */           gcount = rs.getInt(1);
/*     */         }
/* 727 */         String del1 = "delete from AM_CUSTOM_MONITOR_GROUPS_DEFINITION where ATTRIBUTEID=" + todelete.get(i);
/* 728 */         String del2 = "delete from AM_CUSTOM_MONITOR_GROUPS_CAPTION where TYPEID=" + resourceid + " and CAPTION='" + attribute + "'";
/* 729 */         String upd1 = "update AM_CUSTOM_MONITOR_GROUPS_CAPTION set groupid=groupid-1 where TYPEID=" + resourceid + " and groupid>" + gcount;
/* 730 */         String upd2 = "update AM_CUSTOM_MONITOR_GROUPS_DEFINITION set groupid=groupid-1 where TYPEID=" + resourceid + " and groupid>" + gcount;
/* 731 */         AMConnectionPool.executeUpdateStmt(del1);
/* 732 */         AMConnectionPool.executeUpdateStmt(del2);
/* 733 */         if (gcount != 0)
/*     */         {
/* 735 */           AMConnectionPool.executeUpdateStmt(upd1);
/* 736 */           AMConnectionPool.executeUpdateStmt(upd2);
/*     */         }
/*     */       }
/*     */       catch (Exception exc)
/*     */       {
/* 741 */         exc.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public ActionForward updateType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 748 */     ActionMessages messages = new ActionMessages();
/* 749 */     ActionErrors errors = new ActionErrors();
/* 750 */     ArrayList todelete = new ArrayList();
/* 751 */     ArrayList toadd = new ArrayList();
/* 752 */     int resourceid = Integer.parseInt(request.getParameter("baseid"));
/*     */     
/* 754 */     String type = request.getParameter("basetype");
/* 755 */     String newname = ((AMActionForm)form).getDisplayname();
/* 756 */     System.out.println("The baseytpe   ========>" + type);
/*     */     
/* 758 */     String delimiter_new_str = "";
/* 759 */     if (((AMActionForm)form).getDelimiter() != null)
/*     */     {
/* 761 */       delimiter_new_str = ((AMActionForm)form).getDelimiter();
/*     */     }
/*     */     else
/*     */     {
/* 765 */       delimiter_new_str = "s";
/*     */     }
/* 767 */     String type1 = getType(resourceid);
/* 768 */     if (!type1.equals(""))
/*     */     {
/* 770 */       CreateScriptMonitor.checkAndUpdateScalar(form, resourceid, todelete, toadd);
/* 771 */       removeUISettings(todelete, resourceid);
/* 772 */       CreateScriptMonitor.deleteAttributes(todelete);
/* 773 */       String toappend = "_" + resourceid;
/* 774 */       String archivalTable = "AM" + toappend + "_MinMaxAvgData";
/* 775 */       Properties props = new Properties();
/* 776 */       ArrayList idList = new ArrayList();
/* 777 */       Properties typeProps = NewMonitorUtil.getAttributeProps(type1);
/* 778 */       idList.add(typeProps.getProperty("Availability"));
/* 779 */       idList.add(typeProps.getProperty("Health"));
/* 780 */       int healthid = Integer.parseInt(typeProps.getProperty("Health"));
/* 781 */       idList.add(typeProps.getProperty("ResponseTime"));
/* 782 */       System.out.println("ToADD===>" + toadd);
/* 783 */       System.out.println("ToDelete===>" + todelete);
/* 784 */       if ((toadd != null) && (toadd.size() > 0))
/*     */       {
/* 786 */         NewMonitorUtil.insertAttributes(resourceid, toadd, type1, toappend, props, "NO", idList, null, null, archivalTable);
/*     */       }
/* 788 */       CreateScriptMonitor.updateTables(request, resourceid, healthid, type1, toappend, type1, "usebaseidandtableid");
/*     */       
/* 790 */       updatedelimiter(resourceid, delimiter_new_str);
/* 791 */       updateDisplayName(resourceid, newname);
/*     */     }
/*     */     else
/*     */     {
/* 795 */       System.out.println("Such Monitor Type is not present");
/*     */     }
/* 797 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitortypeupdation.success"));
/* 798 */     saveMessages(request, messages);
/* 799 */     return new ActionForward("/monitorType.do?method=showTypes");
/*     */   }
/*     */   
/*     */   public void updateDisplayName(int resourceid, String newname)
/*     */   {
/* 804 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     try
/*     */     {
/* 807 */       String q1 = "update AM_ManagedResourceType set DISPLAYNAME='" + newname + "' where resourcetypeid=" + resourceid;
/* 808 */       AMConnectionPool.executeUpdateStmt(q1);
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 812 */       exc.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void updatedelimiter(int resourceid, String delimiter) {
/* 817 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     try
/*     */     {
/* 820 */       String qry = "update AM_MONITOR_TYPES set OPDELIMITER='" + delimiter + "' where typeid=" + resourceid;
/* 821 */       AMConnectionPool.executeUpdateStmt(qry);
/* 822 */       String q1 = "select resourceid from AM_ManagedObject,AM_MONITOR_TYPES where typeid=" + resourceid + " and type=typename";
/* 823 */       ResultSet rs = null;
/* 824 */       rs = AMConnectionPool.executeQueryStmt(q1);
/* 825 */       while (rs.next())
/*     */       {
/*     */         try
/*     */         {
/* 829 */           AMConnectionPool.executeUpdateStmt("update AM_ScriptArgs set opdelimiter='" + delimiter + "' where resourceid=" + rs.getString(1));
/*     */         }
/*     */         catch (Exception exc)
/*     */         {
/* 833 */           exc.printStackTrace();
/*     */         }
/*     */       }
/* 836 */       rs.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 840 */       exc.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward deleteType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 854 */     String type = request.getParameter("type");
/* 855 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 856 */     String baseid = request.getParameter("baseid");
/* 857 */     ArrayList attlist = new ArrayList();
/* 858 */     ArrayList tableName = new ArrayList();
/*     */     try
/*     */     {
/* 861 */       String qry = "select RESOURCETYPE from AM_ManagedResourceType where RESOURCETYPEID=" + baseid;
/* 862 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 863 */       if (rs.next())
/*     */       {
/* 865 */         type = rs.getString("RESOURCETYPE");
/*     */       }
/* 867 */       closeResultSet(rs);
/*     */       
/* 869 */       String tableQry = "select TABLENAME from AM_SCRIPT_TABLES where SCRIPTID=" + baseid;
/*     */       
/* 871 */       ResultSet rsTableQry = AMConnectionPool.executeQueryStmt(tableQry);
/* 872 */       while (rsTableQry.next())
/*     */       {
/* 874 */         tableName.add(rsTableQry.getString("TABLENAME"));
/*     */       }
/* 876 */       closeResultSet(rs);
/*     */       
/* 878 */       AMConnectionPool.getInstance();Statement delqry = AMConnectionPool.getConnection().createStatement();
/*     */       
/* 880 */       for (int i = 0; i < tableName.size(); i++)
/*     */       {
/* 882 */         delqry.addBatch("delete from AM_Custom_Archival_Mapping where NAME='" + type + "#" + tableName.get(i) + "'");
/*     */       }
/*     */       
/* 885 */       delqry.addBatch("delete from AM_Script_Resource_Attributes_Mapper where RESOURCEID=" + baseid);
/* 886 */       EnterpriseUtil.addUpdateQueryToFile("delete from AM_Script_Resource_Attributes_Mapper where RESOURCEID=" + baseid);
/* 887 */       delqry.addBatch("delete from AM_MONITOR_TYPES where TYPENAME='" + type + "'");
/* 888 */       delqry.addBatch("delete from AM_Custom_Archival_Mapping where NAME like '" + type + "'");
/* 889 */       delqry.addBatch("delete from AM_CUSTOM_MONITOR_GROUPS_DEFINITION where TYPEID=" + baseid);
/* 890 */       delqry.addBatch("delete from AM_CUSTOM_MONITOR_GROUPS_CAPTION where TYPEID=" + baseid);
/* 891 */       delqry.addBatch("delete from AM_SCHEDULER_RESOURCETYPE where RESOURCETYPE='" + type + "'");
/* 892 */       delqry.addBatch("delete from AM_ManagedObject where RESOURCEID=" + baseid);
/* 893 */       EnterpriseUtil.addUpdateQueryToFile("delete from AM_MONITOR_TYPES where TYPENAME='" + type + "'");
/* 894 */       EnterpriseUtil.addUpdateQueryToFile("delete from AM_SCHEDULER_RESOURCETYPE where RESOURCETYPE='" + type + "'");
/* 895 */       EnterpriseUtil.addUpdateQueryToFile("delete from AM_ManagedObject where RESOURCEID=" + baseid);
/* 896 */       attlist = getAttributesListforType(baseid, type);
/* 897 */       for (int i = 0; i < attlist.size(); i++)
/*     */       {
/*     */ 
/* 900 */         delqry.addBatch("delete from AM_ATTRIBUTES where attributeid=" + attlist.get(i));
/* 901 */         delqry.addBatch("delete from AM_ATTRIBUTES_EXT where attributeid=" + attlist.get(i));
/* 902 */         delqry.addBatch("delete from AM_ArchiverConfig where attributeid=" + attlist.get(i));
/* 903 */         delqry.addBatch("delete from AM_CAM_DC_ATTRIBUTES where attributeid=" + attlist.get(i));
/* 904 */         delqry.addBatch("delete from AM_RCAMAPPER where PARENT_RESOURCEATTRIBUTEMAPPERID=" + attlist.get(i) + " OR PARENT_RESOURCEATTRIBUTEMAPPERID=" + attlist.get(i));
/* 905 */         delqry.addBatch("delete from AM_TABLE_ATTRIBUTES where ATTRIBUTEID=" + attlist.get(i));
/* 906 */         delqry.addBatch("delete from AM_ATTRIBUTESDEPENDENCY where PARENTID=" + attlist.get(i) + " OR CHILDID=" + attlist.get(i));
/* 907 */         delqry.addBatch("delete from AM_MGVIEW where ATTRIBUTEID=" + attlist.get(i));
/* 908 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_ATTRIBUTES_EXT where attributeid=" + attlist.get(i));
/* 909 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_AchiverConfig where attributeid=" + attlist.get(i));
/* 910 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_ATTRIBUTES where attributeid=" + attlist.get(i));
/* 911 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_CAM_DC_ATTRIBUTES where attributeid=" + attlist.get(i));
/* 912 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_RCAMAPPER where PARENT_RESOURCEATTRIBUTEMAPPERID=" + attlist.get(i) + " OR PARENT_RESOURCEATTRIBUTEMAPPERID=" + attlist.get(i));
/*     */       }
/* 914 */       delqry.addBatch("delete from AM_ManagedResourceType where resourcetype='" + type + "'");
/*     */       
/* 916 */       System.out.println("Following query is added in the AddUpdateQueryToFile==>delete from AM_ManagedResourceType where resourcetype='" + type + "'");
/* 917 */       EnterpriseUtil.addUpdateQueryToFile("delete from AM_ManagedResourceType where resourcetype='" + type + "'");
/* 918 */       dropTablesForType(type, baseid, delqry);
/*     */       
/* 920 */       delqry.executeBatch();
/* 921 */       delqry.close();
/* 922 */       if (Constants.isIt360)
/*     */       {
/*     */         try
/*     */         {
/* 926 */           Class c = Class.forName("com.adventnet.it360.licensing.IT360LicenseUtil");
/* 927 */           Method meth = c.getMethod("reloadResourcetypesAndDescr", new Class[0]);
/* 928 */           meth.invoke(new Object[0], new Object[0]);
/*     */         } catch (Exception e) {
/* 930 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 936 */       exc.printStackTrace();
/*     */     }
/* 938 */     return new ActionForward("/monitorType.do?method=showTypes");
/*     */   }
/*     */   
/*     */   public static void dropTablesForType(String type, String baseid, Statement delqry) throws SQLException
/*     */   {
/* 943 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 944 */     String qry = "DROP TABLE AM_SCRIPT_TABULAR_NUMERIC_DATA_" + baseid;
/* 945 */     String qry1 = "DROP TABLE AM_Script_Numeric_Data_" + baseid;
/* 946 */     String qry2 = "DROP TABLE AM_Script_String_Data_" + baseid;
/* 947 */     String qry3 = "DROP TABLE AM_CAM_COLUMNAR_DATA_" + baseid;
/* 948 */     String qry4 = "DROP TABLE AM_" + baseid + "_MinMaxAvgData";
/* 949 */     String qry5 = "select tableid from AM_SCRIPT_TABLES WHERE SCRIPTID=" + baseid;
/*     */     try
/*     */     {
/* 952 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry5);
/* 953 */       while (rs.next())
/*     */       {
/* 955 */         String temp = "AM_" + baseid + "_" + rs.getString(1) + "_MinMaxAvgData";
/* 956 */         delqry.addBatch("DROP TABLE " + temp);
/*     */       }
/* 958 */       rs.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 962 */       exc.printStackTrace();
/*     */     }
/* 964 */     delqry.addBatch("delete from AM_SCRIPT_TABLES WHERE SCRIPTID=" + baseid);
/* 965 */     delqry.addBatch(qry);
/* 966 */     delqry.addBatch(qry1);
/* 967 */     delqry.addBatch(qry2);
/* 968 */     delqry.addBatch(qry3);
/* 969 */     delqry.addBatch(qry4);
/*     */   }
/*     */   
/*     */   public static ArrayList getAttributesListforType(String baseid, String type)
/*     */   {
/* 974 */     ArrayList al = new ArrayList();
/* 975 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     try
/*     */     {
/* 978 */       String qry = "select attributeid from AM_ATTRIBUTES where resourcetype='" + type + "'";
/* 979 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 980 */       while (rs.next())
/*     */       {
/* 982 */         al.add(rs.getString(1));
/*     */       }
/* 984 */       closeResultSet(rs);
/* 985 */       qry = "select attributeid from AM_SCRIPT_TABLES,AM_CAM_DC_ATTRIBUTES where scriptid=" + baseid + " and tableid=groupid";
/* 986 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 987 */       while (rs.next())
/*     */       {
/* 989 */         al.add(rs.getString(1));
/*     */       }
/* 991 */       rs.close();
/*     */     }
/*     */     catch (Exception exc) {}
/*     */     
/*     */ 
/*     */ 
/* 997 */     return al;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ManageMonitorType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */