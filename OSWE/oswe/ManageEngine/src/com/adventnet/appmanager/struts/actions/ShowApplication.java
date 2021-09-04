/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObjectDao;
/*      */ import com.adventnet.appmanager.server.discovery.ADM.ADMUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.struts.beans.GroupComponent;
/*      */ import com.adventnet.appmanager.struts.beans.UrlData;
/*      */ import com.adventnet.appmanager.struts.form.FlashForm;
/*      */ import com.adventnet.appmanager.util.ChildMOHandler;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MGActionNotifier;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*      */ import com.adventnet.appmanager.utils.client.BusinessViewUtil;
/*      */ import com.adventnet.appmanager.utils.client.CommonAPIUtil;
/*      */ import com.adventnet.appmanager.utils.client.MapViewUtil;
/*      */ import com.adventnet.nms.topodb.TopoAPI;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.manageengine.appmanager.vservers.util.GetDrsMigration;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.lang3.StringEscapeUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.action.ActionServlet;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.json.JSONArray;
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
/*      */ public final class ShowApplication
/*      */   extends DispatchAction
/*      */ {
/*   97 */   private static Log log = LogFactory.getLog("WebClient");
/*      */   
/*   99 */   public ShowApplication() { this.mo = new ManagedApplication(); }
/*      */   
/*      */   private Hashtable getData(String haid, Hashtable ht, String resourcegroup)
/*      */   {
/*  103 */     ArrayList al = new ArrayList();
/*  104 */     String qry2 = "select * from AM_ManagedObject,AM_PARENTCHILDMAPPER,AM_ManagedResourceType where resourcegroup='" + resourcegroup + "' and resourcetype=type and parentid=" + haid + " and childid= AM_ManagedObject.resourceid order by type";
/*  105 */     ArrayList id = new ArrayList();
/*  106 */     ArrayList types = new ArrayList();
/*  107 */     Properties res_id_name_mapper = new Properties();
/*  108 */     Hashtable ht_data = new Hashtable();
/*  109 */     System.out.println("The resourcegroup  qry====>" + qry2);
/*  110 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/*  113 */       ResultSet rs2 = AMConnectionPool.executeQueryStmt(qry2);
/*  114 */       while (rs2.next())
/*      */       {
/*  116 */         String resid = rs2.getString("RESOURCEID");
/*  117 */         String disp_name = rs2.getString("DISPLAYNAME");
/*  118 */         id.add(resid);
/*  119 */         String type = rs2.getString("TYPE");
/*  120 */         types.add(type);
/*  121 */         res_id_name_mapper.setProperty(resid, disp_name);
/*  122 */         System.out.println("The type=====>" + type);
/*  123 */         ArrayList combo = (ArrayList)ht.get(type);
/*  124 */         System.out.println("The combo for the type===" + type + "====>" + combo);
/*  125 */         ArrayList att_al = (ArrayList)combo.get(0);
/*  126 */         ArrayList props_att = (ArrayList)combo.get(1);
/*      */         
/*  128 */         Properties data_id_mapper = new Properties();
/*  129 */         for (int i = 0; i < att_al.size(); i++)
/*      */         {
/*      */           try
/*      */           {
/*  133 */             Properties p = (Properties)props_att.get(i);
/*  134 */             String datatable = p.getProperty("DATATABLE");
/*  135 */             String resid_col = p.getProperty("RESID_COL");
/*  136 */             String attid_col = p.getProperty("ATTID_COL");
/*  137 */             String value_col = p.getProperty("VALUE_COL");
/*  138 */             String coltime_col = p.getProperty("COLTIME_VAL");
/*  139 */             String displayname = p.getProperty("DISPLAYNAME");
/*  140 */             String expression = p.getProperty("EXPRESSION");
/*  141 */             String data = "-1";
/*  142 */             String time_qry = "select max(" + coltime_col + ") from " + datatable + " where " + resid_col + "=" + resid;
/*  143 */             if (!attid_col.equals("-1"))
/*      */             {
/*  145 */               time_qry = "select max(" + coltime_col + ") from " + datatable + " where " + resid_col + "=" + resid + " and " + attid_col + "=" + att_al.get(i);
/*      */             }
/*  147 */             ResultSet rs = AMConnectionPool.executeQueryStmt(time_qry);
/*  148 */             String time = "-1";
/*  149 */             if (rs.next())
/*      */             {
/*  151 */               time = rs.getString(1);
/*      */             }
/*  153 */             closeResultSet(rs);
/*  154 */             String data_qry = "select " + value_col + expression + " from " + datatable + " where " + resid_col + "=" + resid + " and " + coltime_col + "=" + time;
/*  155 */             if (!attid_col.equals("-1"))
/*      */             {
/*  157 */               data_qry = "select " + value_col + expression + " from " + datatable + " where " + resid_col + "=" + resid + " and " + attid_col + "=" + att_al.get(i) + " and " + coltime_col + "=" + time;
/*      */             }
/*  159 */             System.out.println("The data qry formed===>" + data_qry);
/*  160 */             rs = AMConnectionPool.executeQueryStmt(data_qry);
/*  161 */             if (rs.next())
/*      */             {
/*  163 */               if (rs.getString(1) != null) {
/*  164 */                 data = rs.getString(1);
/*      */               }
/*      */             }
/*      */             
/*  168 */             if (data.equals("-1"))
/*      */             {
/*  170 */               data = "-";
/*      */             }
/*  172 */             data_id_mapper.setProperty((String)att_al.get(i), data);
/*  173 */             closeResultSet(rs);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  177 */             e.printStackTrace();
/*      */           }
/*      */         }
/*  180 */         ht_data.put(resid, data_id_mapper);
/*      */       }
/*  182 */       closeResultSet(rs2);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  186 */       exc.printStackTrace();
/*      */     }
/*  188 */     Hashtable ret_ht = null;
/*  189 */     if ((id.size() > 0) && (types.size() > 0) && (ht_data.size() > 0))
/*      */     {
/*  191 */       al.add(id);
/*  192 */       al.add(types);
/*  193 */       al.add(ht_data);
/*  194 */       ret_ht = arrangeByTypes(al);
/*  195 */       System.out.println("The res_id_name_mapper===>" + res_id_name_mapper);
/*  196 */       ret_ht.put("am_resids", res_id_name_mapper);
/*      */     }
/*  198 */     return ret_ht;
/*      */   }
/*      */   
/*      */   private static Hashtable arrangeByTypes(ArrayList al)
/*      */   {
/*  203 */     ArrayList toreturn = new ArrayList();
/*  204 */     ArrayList id = (ArrayList)al.get(0);
/*  205 */     ArrayList types = (ArrayList)al.get(1);
/*  206 */     Hashtable ht_data = (Hashtable)al.get(2);
/*  207 */     Hashtable type_data = new Hashtable();
/*  208 */     for (int i = 0; i < id.size(); i++)
/*      */     {
/*  210 */       if ((type_data.size() > 0) && (type_data.containsKey((String)types.get(i))))
/*      */       {
/*  212 */         Hashtable ht_temp = (Hashtable)type_data.get((String)types.get(i));
/*  213 */         ht_temp.put(id.get(i), ht_data.get((String)id.get(i)));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  218 */         Hashtable ht_temp = new Hashtable();
/*  219 */         ht_temp.put(id.get(i), ht_data.get((String)id.get(i)));
/*      */         
/*      */ 
/*  222 */         type_data.put((String)types.get(i), ht_temp);
/*      */       }
/*      */     }
/*  225 */     return type_data;
/*      */   }
/*      */   
/*      */   public ActionForward getUserDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  230 */     StringBuffer result = null;
/*      */     
/*      */     try
/*      */     {
/*  234 */       PrintWriter out = response.getWriter();
/*  235 */       result = new StringBuffer();
/*      */       
/*  237 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  240 */         String query = "select AM_UserPasswordTable.USERID, AM_UserPasswordTable.USERNAME, AM_UserPasswordTable.APIKEY, AM_UserPasswordTable.EMAILID, AM_UserPasswordTable.DESCRIPTION, AM_UserGroupTable.GROUPNAME from AM_UserPasswordTable LEFT OUTER JOIN AM_UserGroupTable ON AM_UserGroupTable.USERNAME = AM_UserPasswordTable.USERNAME WHERE AM_UserPasswordTable.USERNAME = '" + request.getParameter("username") + "' order by AM_UserGroupTable.GROUPNAME";
/*  241 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  242 */         if (rs.next())
/*      */         {
/*  244 */           result.append("Authentication:Success;");
/*  245 */           result.append("UserID:" + rs.getString("USERID").trim() + ";");
/*  246 */           result.append("UserName:" + rs.getString("USERNAME").trim() + ";");
/*  247 */           result.append("APIKey:" + rs.getString("APIKEY").trim() + ";");
/*  248 */           result.append("EmailID:" + ((rs.getString("EMAILID") != null) && (rs.getString("EMAILID").trim().length() > 0) ? rs.getString("EMAILID").trim() : "NA") + ";");
/*  249 */           result.append("GroupName:" + ((rs.getString("GROUPNAME") != null) && (rs.getString("GROUPNAME").trim().length() > 0) ? rs.getString("GROUPNAME").trim() : "NA") + ";");
/*  250 */           result.append("UserImage:" + CommonAPIUtil.getUserImagePath(request, rs.getString("USERNAME"), rs.getString("USERID")) + ";");
/*  251 */           result.append("Description:" + ((rs.getString("DESCRIPTION") != null) && (rs.getString("DESCRIPTION").trim().length() > 0) ? rs.getString("DESCRIPTION").trim() : "NA") + ";");
/*      */         }
/*      */         else
/*      */         {
/*  255 */           result.append("Authentication:Failure;");
/*  256 */           result.append("No records found for the user:" + request.getParameter("username"));
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  261 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  265 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*  267 */       out.println(result.toString());
/*  268 */       out.flush();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  272 */       e.printStackTrace();
/*      */     }
/*  274 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showDetailView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  279 */     String haid = request.getParameter("haid");
/*  280 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*  282 */     String qry = "select distinct(resourcetype),AM_ManagedResourceType.displayname,resourcegroup from AM_ManagedObject,AM_ManagedResourceType,AM_PARENTCHILDMAPPER where parentid=" + haid + " and childid=resourceid and resourcetype=type order by resourcetype";
/*  283 */     ArrayList al = new ArrayList();
/*  284 */     ArrayList systems_list = new ArrayList();
/*  285 */     Hashtable ht = new Hashtable();
/*  286 */     boolean isowner = true;
/*  287 */     if (request.isUserInRole("OPERATOR")) {
/*  288 */       Hashtable haidparents = DBUtil.getParentMGsforChildMGs("('" + haid + "')");
/*  289 */       String haidstoCheck = "(";
/*  290 */       ArrayList temp1 = (ArrayList)haidparents.get(haid);
/*  291 */       if (temp1 != null) {
/*  292 */         for (int i = 0; i < temp1.size(); i++) {
/*  293 */           haidstoCheck = haidstoCheck + "'" + temp1.get(i) + "',";
/*      */         }
/*      */       }
/*  296 */       haidstoCheck = haidstoCheck + "'" + haid + "')";
/*  297 */       String userName = request.getRemoteUser();
/*      */       try {
/*  299 */         String q1 = "select * from AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where USERNAME='" + userName + "' and USERID=OWNERID AND HAID IN " + haidstoCheck;
/*  300 */         if (com.adventnet.appmanager.util.Constants.isSsoEnabled()) {
/*  301 */           String loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/*  302 */           q1 = "select * from AM_USERRESOURCESTABLE where USERID=" + loginUserid + " AND RESOURCEID IN " + haidstoCheck;
/*      */         }
/*  304 */         ResultSet rs1 = AMConnectionPool.executeQueryStmt(q1);
/*  305 */         if (rs1.next())
/*      */         {
/*  307 */           isowner = true;
/*      */         }
/*      */         else {
/*  310 */           isowner = false;
/*      */         }
/*  312 */         rs1.close();
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */     
/*      */ 
/*  318 */     if (!isowner) {
/*  319 */       return new ActionForward("/jsp/ShowApplicationDetails.jsp?haid=" + haid);
/*      */     }
/*  321 */     Properties res_dis_mapper = new Properties();
/*      */     try
/*      */     {
/*  324 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  325 */       while (rs.next())
/*      */       {
/*  327 */         al.add(rs.getString(1));
/*  328 */         res_dis_mapper.setProperty(rs.getString(1), rs.getString(2));
/*      */       }
/*  330 */       closeResultSet(rs);
/*  331 */       String qry1 = "select distinct(AM_ManagedResourceType.resourcetype),AM_ATTRIBUTES.displayname,AM_ATTRIBUTES_EXT.* from AM_ATTRIBUTES_EXT,AM_ATTRIBUTES,AM_ManagedResourceType,AM_ManagedObject where AM_ManagedResourceType.resourcetype='Apache-server' and AM_ManagedResourceType.resourcetype=AM_ManagedObject.type and AM_ATTRIBUTES.resourcetype=AM_ManagedResourceType.resourcetype and AM_ATTRIBUTES.attributeid=AM_ATTRIBUTES_EXT.attributeid and MG_VIEW='YES'";
/*      */       
/*  333 */       for (int i = 0; i < al.size(); i++)
/*      */       {
/*      */ 
/*      */ 
/*  337 */         qry1 = "select AM_ATTRIBUTES_EXT.*,UNITS,DISPLAYNAME,TYPE from AM_ATTRIBUTES_EXT,AM_ATTRIBUTES,AM_MGVIEW WHERE RESOURCETYPE='" + al.get(i) + "'  and AM_ATTRIBUTES_EXT.attributeid=AM_ATTRIBUTES.ATTRIBUTEID and AM_MGVIEW.attributeid=AM_ATTRIBUTES_EXT.attributeid";
/*  338 */         System.out.println("The second level qry=====>" + qry1);
/*  339 */         ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry1);
/*  340 */         ArrayList combo = new ArrayList();
/*  341 */         ArrayList att_al = new ArrayList();
/*  342 */         ArrayList props_att = new ArrayList();
/*      */         
/*  344 */         while (rs1.next())
/*      */         {
/*  346 */           Properties p = new Properties();
/*  347 */           p.setProperty("ATTRIBUTEID", rs1.getString("ATTRIBUTEID"));
/*  348 */           p.setProperty("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/*  349 */           p.setProperty("DATATABLE", rs1.getString("DATATABLE"));
/*  350 */           p.setProperty("RESID_COL", rs1.getString("RESID_COL"));
/*  351 */           p.setProperty("ATTID_COL", rs1.getString("ATTID_COL"));
/*  352 */           p.setProperty("VALUE_COL", rs1.getString("VALUE_COL"));
/*  353 */           p.setProperty("COLTIME_VAL", rs1.getString("COLTIME_VAL"));
/*  354 */           p.setProperty("EXPRESSION", rs1.getString("EXPRESSION"));
/*  355 */           if ((rs1.getString("UNITS") != null) && (!rs1.getString("UNITS").equals("NULL")) && (!rs1.getString("UNITS").equals("")))
/*      */           {
/*  357 */             p.setProperty("UNITS", " (" + rs1.getString("UNITS") + ")");
/*      */           }
/*      */           else
/*      */           {
/*  361 */             p.setProperty("UNITS", "");
/*      */           }
/*      */           
/*  364 */           p.setProperty("EXPRESSION", rs1.getString("EXPRESSION"));
/*      */           
/*  366 */           if ((rs1.getString("TYPE") != null) && (rs1.getString("TYPE").equalsIgnoreCase("1")))
/*      */           {
/*  368 */             att_al.add(0, rs1.getString("ATTRIBUTEID"));
/*  369 */             props_att.add(0, p);
/*      */           }
/*  371 */           else if ((rs1.getString("TYPE") != null) && (rs1.getString("TYPE").equalsIgnoreCase("2")))
/*      */           {
/*  373 */             if (att_al.size() > 0)
/*      */             {
/*  375 */               att_al.add(1, rs1.getString("ATTRIBUTEID"));
/*  376 */               props_att.add(1, p);
/*      */             }
/*      */             else
/*      */             {
/*  380 */               att_al.add(0, rs1.getString("ATTRIBUTEID"));
/*  381 */               props_att.add(0, p);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  386 */             att_al.add(rs1.getString("ATTRIBUTEID"));
/*  387 */             props_att.add(p);
/*      */           }
/*      */         }
/*  390 */         combo.add(att_al);
/*  391 */         combo.add(props_att);
/*  392 */         System.out.println("The combo====>" + combo);
/*  393 */         ht.put(al.get(i), combo);
/*  394 */         System.out.println("Successs!!!!!!!!");
/*  395 */         closeResultSet(rs1);
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  400 */       exc.printStackTrace();
/*      */     }
/*      */     
/*  403 */     ArrayList lin_att = new ArrayList();
/*      */     try
/*      */     {
/*  406 */       String qry1 = "select AM_ATTRIBUTES_EXT.*,ATTRIBUTE,DISPLAYNAME,TYPE,UNITS from AM_ATTRIBUTES_EXT,AM_ATTRIBUTES,AM_MGVIEW WHERE RESOURCETYPE='Linux' and AM_ATTRIBUTES_EXT.attributeid=AM_ATTRIBUTES.ATTRIBUTEID and AM_MGVIEW.attributeid=AM_ATTRIBUTES_EXT.attributeid";
/*  407 */       System.out.println("The second level qry=====>" + qry1);
/*  408 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry1);
/*  409 */       ArrayList combo = new ArrayList();
/*  410 */       ArrayList lin_att_id = new ArrayList();
/*  411 */       ArrayList lin_att_name = new ArrayList();
/*  412 */       ArrayList lin_att_disp_name = new ArrayList();
/*  413 */       ArrayList lin_att_units = new ArrayList();
/*      */       
/*  415 */       while (rs1.next())
/*      */       {
/*  417 */         if ((rs1.getString("TYPE") != null) && (rs1.getString("TYPE").equalsIgnoreCase("1")))
/*      */         {
/*  419 */           lin_att_id.add(0, rs1.getString("ATTRIBUTEID"));
/*  420 */           lin_att_name.add(0, rs1.getString("ATTRIBUTE"));
/*  421 */           lin_att_disp_name.add(0, rs1.getString("DISPLAYNAME"));
/*  422 */           lin_att_units.add(0, "");
/*      */         }
/*  424 */         else if ((rs1.getString("TYPE") != null) && (rs1.getString("TYPE").equalsIgnoreCase("2")))
/*      */         {
/*  426 */           if (lin_att_id.size() > 0)
/*      */           {
/*  428 */             lin_att_id.add(1, rs1.getString("ATTRIBUTEID"));
/*  429 */             lin_att_name.add(1, rs1.getString("ATTRIBUTE"));
/*  430 */             lin_att_disp_name.add(1, rs1.getString("DISPLAYNAME"));
/*  431 */             lin_att_units.add(1, "");
/*      */           }
/*      */           else
/*      */           {
/*  435 */             lin_att_id.add(0, rs1.getString("ATTRIBUTEID"));
/*  436 */             lin_att_name.add(0, rs1.getString("ATTRIBUTE"));
/*  437 */             lin_att_disp_name.add(0, rs1.getString("DISPLAYNAME"));
/*  438 */             lin_att_units.add(0, "");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  443 */           lin_att_id.add(rs1.getString("ATTRIBUTEID"));
/*  444 */           lin_att_name.add(rs1.getString("ATTRIBUTE"));
/*  445 */           lin_att_disp_name.add(rs1.getString("DISPLAYNAME"));
/*  446 */           if ((rs1.getString("UNITS") != null) && (!rs1.getString("UNITS").trim().equals(""))) {
/*  447 */             lin_att_units.add("(" + rs1.getString("UNITS") + ")");
/*      */           } else
/*  449 */             lin_att_units.add("");
/*      */         }
/*      */       }
/*  452 */       lin_att.add(lin_att_id);
/*  453 */       lin_att.add(lin_att_name);
/*  454 */       lin_att.add(lin_att_disp_name);
/*  455 */       lin_att.add(lin_att_units);
/*  456 */       closeResultSet(rs1);
/*      */     }
/*      */     catch (Exception exc) {}
/*      */     
/*      */ 
/*      */ 
/*  462 */     request.setAttribute("lin_att", lin_att);
/*  463 */     request.setAttribute("res_dis_mapper", res_dis_mapper);
/*  464 */     Hashtable server_props = com.adventnet.appmanager.util.Constants.getHostProperties();
/*  465 */     Properties host_img_mapper = com.adventnet.appmanager.util.Constants.getImagesForServers();
/*  466 */     request.setAttribute("host_img_mapper", host_img_mapper);
/*  467 */     request.setAttribute("systems_list", systems_list);
/*  468 */     request.setAttribute("server_props", server_props);
/*  469 */     System.out.println("The server_props===>" + server_props);
/*  470 */     Hashtable ser_list = getData(haid, ht, "SER");
/*  471 */     Hashtable sys_list = getData(haid, ht, "SYS");
/*  472 */     Hashtable app_list = getData(haid, ht, "APP");
/*  473 */     Hashtable ms_list = getData(haid, ht, "MS");
/*  474 */     Hashtable tm_list = getData(haid, ht, "TM");
/*  475 */     Hashtable url_list = getData(haid, ht, "URL");
/*  476 */     Hashtable dbs_list = getData(haid, ht, "DBS");
/*  477 */     Hashtable cam_list = getData(haid, ht, "CAM");
/*  478 */     Hashtable erp_list = getData(haid, ht, "ERP");
/*  479 */     Hashtable mom_list = getData(haid, ht, "MOM");
/*  480 */     Hashtable vir_list = getData(haid, ht, "VIR");
/*  481 */     if (ser_list != null)
/*      */     {
/*  483 */       request.setAttribute("ser_list", ser_list);
/*      */     }
/*  485 */     if (url_list != null)
/*      */     {
/*  487 */       request.setAttribute("url_list", url_list);
/*      */     }
/*  489 */     if (dbs_list != null)
/*      */     {
/*  491 */       request.setAttribute("dbs_list", dbs_list);
/*      */     }
/*  493 */     if (tm_list != null)
/*      */     {
/*  495 */       request.setAttribute("tm_list", tm_list);
/*      */     }
/*  497 */     if (cam_list != null)
/*      */     {
/*  499 */       request.setAttribute("cam_list", cam_list);
/*      */     }
/*  501 */     if (ms_list != null)
/*      */     {
/*  503 */       request.setAttribute("ms_list", ms_list);
/*      */     }
/*  505 */     if (app_list != null)
/*      */     {
/*  507 */       request.setAttribute("app_list", app_list);
/*      */     }
/*  509 */     if (sys_list != null)
/*      */     {
/*  511 */       request.setAttribute("sys_list", sys_list);
/*      */     }
/*  513 */     if (erp_list != null)
/*      */     {
/*  515 */       request.setAttribute("erp_list", erp_list);
/*      */     }
/*  517 */     if (mom_list != null)
/*      */     {
/*  519 */       request.setAttribute("mom_list", mom_list);
/*      */     }
/*  521 */     if (vir_list != null)
/*      */     {
/*  523 */       request.setAttribute("vir_list", vir_list);
/*      */     }
/*  525 */     request.setAttribute("configdetails", ht);
/*      */     
/*  527 */     request.setAttribute("haid", haid);
/*  528 */     return new ActionForward("/jsp/ShowApplicationDetails.jsp?haid=" + haid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static void closeResultSet(ResultSet rs)
/*      */   {
/*      */     try
/*      */     {
/*  537 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception exc) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showRelationshipView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  551 */     String haid = (String)((DynaActionForm)form).get("haid");
/*  552 */     String parentName = (String)((DynaActionForm)form).get("name");
/*  553 */     request.setAttribute("resourceID", haid + "");
/*  554 */     return mapping.findForward("relationShipView");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getGroupDataAsJSONObject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  560 */     String resourceID = request.getParameter("resourceID");
/*  561 */     ParentChildRelationalUtil parentChildUtil = new ParentChildRelationalUtil();
/*      */     
/*  563 */     JSONObject dataForSpaceTree = new JSONObject();
/*      */     
/*      */     try
/*      */     {
/*  567 */       response.setContentType("text/json charset=UTF-8");
/*  568 */       PrintWriter out = response.getWriter();
/*  569 */       out.print(dataForSpaceTree);
/*  570 */       out.flush();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  574 */       ex.printStackTrace();
/*      */     }
/*  576 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showChildApplicationDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  581 */     String resId = request.getParameter("resId");
/*  582 */     String query = "SELECT PARENTMO.DISPLAYNAME AS SERVERNAME,PARENTMO.RESOURCENAME AS PARENT_RESNAME, PARENTMO.TYPE AS SERVERTYPE,PARENTMO.RESOURCEID AS PARENT_RESID,CHILDMO.DISPLAYNAME AS CHILDNAME,CHILDMO.TYPE AS CHILDTYPE FROM AM_ManagedObject AS PARENTMO,AM_PARENTCHILDMAPPER,AM_ManagedObject AS CHILDMO WHERE PARENTMO.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID AND CHILDMO.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_PARENTCHILDMAPPER.CHILDID=" + resId + " AND PARENTMO.TYPE NOT IN ('HAI')";
/*  583 */     Properties serverDetails = new Properties();
/*  584 */     ResultSet rs = null;
/*  585 */     String moType = null;
/*  586 */     String serverType = null;
/*  587 */     String serverResID = null;
/*  588 */     String serverResName = null;
/*      */     try
/*      */     {
/*  591 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  592 */       if (rs.next())
/*      */       {
/*  594 */         serverDetails.setProperty("SERVERNAME", rs.getString("SERVERNAME"));
/*  595 */         serverType = rs.getString("SERVERTYPE");
/*  596 */         serverResID = rs.getString("PARENT_RESID");
/*  597 */         serverDetails.setProperty("SERVERTYPE", serverType);
/*      */         
/*  599 */         serverDetails.setProperty("PARENT_RESID", serverResID);
/*  600 */         moType = rs.getString("CHILDTYPE");
/*  601 */         if (moType.equals("Service"))
/*      */         {
/*  603 */           serverDetails.setProperty("SERVICENAME", rs.getString("CHILDNAME"));
/*      */         }
/*  605 */         serverResName = rs.getString("PARENT_RESNAME");
/*      */       }
/*  607 */       AMConnectionPool.closeStatement(rs);
/*  608 */       if ((serverType != null) && (serverType.equals("VirtualMachine")))
/*      */       {
/*  610 */         serverDetails.setProperty("IPADDRESS", DBUtil.getConfigurationDetails(serverResID, "7615"));
/*      */       }
/*  612 */       else if ((serverType != null) && (serverType.equals("XenServerVM")))
/*      */       {
/*  614 */         serverDetails.setProperty("IPADDRESS", DBUtil.getConfigurationDetails(serverResID, "15538"));
/*      */       }
/*      */       else
/*      */       {
/*  618 */         query = "SELECT TARGETADDRESS FROM CollectData WHERE RESOURCENAME='" + serverResName + "'";
/*      */         try
/*      */         {
/*  621 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  622 */           if (rs.next())
/*      */           {
/*  624 */             serverDetails.setProperty("IPADDRESS", rs.getString(1));
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  629 */           e.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  637 */       if ((moType != null) && (moType.equals("Process")))
/*      */       {
/*  639 */         long time = 0L;
/*  640 */         query = "SELECT MAX(DCTIME) FROM AM_HOST_PROCESS_INSTANCE WHERE RESOURCEID=" + resId;
/*  641 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  642 */         if (rs.next())
/*      */         {
/*  644 */           time = rs.getLong(1);
/*      */         }
/*  646 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*  648 */         query = "SELECT PROCESSNAME, COMMAND, DISPLAYNAME FROM AM_HOST_PROCESS_INFO WHERE PARENTID=" + serverResID + " AND RESOURCEID=" + resId;
/*  649 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  650 */         if (rs.next())
/*      */         {
/*  652 */           serverDetails.setProperty("PROCESSNAME", rs.getString("PROCESSNAME"));
/*  653 */           serverDetails.setProperty("COMMAND", rs.getString("COMMAND"));
/*  654 */           serverDetails.setProperty("PROCESS_DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*      */         }
/*  656 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*  658 */         query = "SELECT COALESCE(INSTANCE,'-1') INSTANCE,COALESCE(PCPU,'-1') PCPU,COALESCE(PMEM,'-1') PMEM FROM AM_HOST_PROCESS_INSTANCE,AM_HOST_PROCESS_CPUMEM WHERE AM_HOST_PROCESS_CPUMEM.RESOURCEID=AM_HOST_PROCESS_INSTANCE.RESOURCEID AND AM_HOST_PROCESS_INSTANCE.DCTIME=" + time + " AND AM_HOST_PROCESS_CPUMEM.DCTIME=" + time + " AND AM_HOST_PROCESS_INSTANCE.RESOURCEID=" + resId;
/*  659 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  660 */         if (rs.next())
/*      */         {
/*  662 */           serverDetails.setProperty("PCPU", rs.getString("PCPU"));
/*  663 */           serverDetails.setProperty("INSTANCE", rs.getString("INSTANCE"));
/*  664 */           serverDetails.setProperty("PMEM", rs.getString("PMEM"));
/*      */         }
/*      */         else
/*      */         {
/*  668 */           serverDetails.setProperty("PCPU", "-");
/*  669 */           serverDetails.setProperty("INSTANCE", "-");
/*  670 */           serverDetails.setProperty("PMEM", "-");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  676 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  680 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  682 */     request.setAttribute("serverDetails", serverDetails);
/*  683 */     request.setAttribute("resourceType", moType);
/*  684 */     return new ActionForward("/jsp/ProcessAndServiceSnapshot.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward showApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  691 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  692 */     ActionMessages messages = new ActionMessages();
/*  693 */     ActionErrors errors = new ActionErrors();
/*  694 */     String haid = (String)((DynaActionForm)form).get("haid");
/*  695 */     String consoleHome = request.getParameter("consoleHome");
/*  696 */     request.setAttribute("isReadOnly", "false");
/*  697 */     request.setAttribute("displayAvailableViews", "false");
/*  698 */     if (consoleHome == null)
/*      */     {
/*  700 */       consoleHome = "false";
/*      */     }
/*  702 */     String groupType = "1";
/*  703 */     ResultSet disprs = null;
/*  704 */     boolean under_maintenance = DataCollectionControllerUtil.underMaintenance(haid);
/*  705 */     request.setAttribute("MGUnderMaintenance", Boolean.valueOf(under_maintenance));
/*  706 */     request.setAttribute("MGUnmanaged", Boolean.valueOf(DataCollectionControllerUtil.isUnManaged(haid)));
/*  707 */     if (under_maintenance)
/*      */     {
/*  709 */       request.setAttribute("MGMaintenanceMsg", DataCollectionControllerUtil.getMaintenanceMessageForMG(haid));
/*      */     }
/*      */     try
/*      */     {
/*  713 */       disprs = AMConnectionPool.executeQueryStmt("select DISPLAYNAME , GROUPTYPE  from AM_ManagedObject inner join AM_HOLISTICAPPLICATION on HAID=RESOURCEID where RESOURCEID=" + haid);
/*  714 */       if (disprs.next())
/*      */       {
/*  716 */         ((DynaActionForm)form).set("name", disprs.getString("DISPLAYNAME"));
/*  717 */         ((DynaActionForm)form).set("grouptype", disprs.getString("GROUPTYPE"));
/*  718 */         groupType = disprs.getString("GROUPTYPE");
/*  719 */         request.setAttribute("grouptype", disprs.getString("GROUPTYPE"));
/*      */       }
/*      */     }
/*      */     finally {
/*  723 */       if (disprs != null)
/*      */       {
/*  725 */         disprs.close();
/*      */       }
/*      */     }
/*      */     
/*  729 */     boolean isBusiness = false;
/*  730 */     String selectedFunction = "am.webclient.dasboard.summarytab.title";
/*  731 */     Cookie[] cookies = request.getCookies();
/*  732 */     for (int i = 0; i < cookies.length; i++) {
/*  733 */       if (cookies[i].getName().equals("am_mgview"))
/*      */       {
/*  735 */         if (!cookies[i].getValue().equals("business"))
/*      */           break;
/*  737 */         isBusiness = true; break;
/*      */       }
/*      */     }
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
/*  755 */     String businessTabAppend = "";
/*  756 */     if (isBusiness)
/*      */     {
/*  758 */       request.setAttribute("selectM", "flashview");
/*      */     }
/*  760 */     boolean isPrivilege = false;
/*  761 */     if (ClientDBUtil.isPrivilegedUser(request)) {
/*  762 */       isPrivilege = true;
/*      */     }
/*  764 */     request.setAttribute("isPrivilege", isPrivilege + "");
/*      */     
/*      */ 
/*  767 */     if (("3".equals(groupType)) || ("1009".equals(groupType)) || ("1010".equals(groupType)) || ("1012".equals(groupType)))
/*      */     {
/*  769 */       return showVCenterApplication(mapping, form, request, response);
/*      */     }
/*  771 */     if ("1013".equals(groupType))
/*      */     {
/*  773 */       return showXenResourcePoolApplication(mapping, form, request, response);
/*      */     }
/*  775 */     if (!groupType.equals("1"))
/*      */     {
/*  777 */       return showWebApplication(mapping, form, request, response);
/*      */     }
/*  779 */     String popup = request.getParameter("popup");
/*  780 */     if (isPrivilege)
/*      */     {
/*  782 */       boolean mgviewAllowed = checkForMGAssignedtoUser(haid, request);
/*      */       
/*      */ 
/*  785 */       if (!mgviewAllowed)
/*      */       {
/*  787 */         return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  797 */       request.setAttribute("reloadperiod", "300");
/*  798 */       boolean isowner = true;
/*  799 */       String owner_var = request.getParameter("isowner");
/*  800 */       boolean removeHaid = true;
/*  801 */       String loginUserid = null;
/*  802 */       boolean isSSOEnabled = false;
/*  803 */       if (com.adventnet.appmanager.util.Constants.isSsoEnabled()) {
/*  804 */         isSSOEnabled = true;
/*  805 */         loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/*      */       }
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
/*  819 */       String userName = request.getRemoteUser();
/*  820 */       ArrayList list = null;
/*  821 */       if (isPrivilege) {
/*  822 */         list = AlarmUtil.getTopNAlertsForResource(Integer.parseInt(haid), true, request, "5");
/*      */       }
/*      */       else {
/*  825 */         list = AlarmUtil.getTopNAlertsForResource(Integer.parseInt(haid), true, null, "5");
/*      */       }
/*  827 */       if (list.size() != 0)
/*      */       {
/*  829 */         request.setAttribute("recent5Alarms", list);
/*      */       }
/*  831 */       HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(haid, 300L);
/*  832 */       if (map != null)
/*      */       {
/*  834 */         request.setAttribute("systeminfo", map);
/*      */       }
/*  836 */       String messagetoshow = request.getParameter("messagetoshow");
/*  837 */       if (messagetoshow != null)
/*      */       {
/*  839 */         if (messagetoshow.equals("success"))
/*      */         {
/*  841 */           if (EnterpriseUtil.isAdminServer())
/*      */           {
/*  843 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("haid.applicationcreation.admin.success")));
/*      */           }
/*      */           else
/*      */           {
/*  847 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("haid.applicationcreation.success")));
/*      */           }
/*  849 */           saveMessages(request, messages);
/*      */         }
/*  851 */         else if (messagetoshow.equals("alreadyExists"))
/*      */         {
/*  853 */           request.setAttribute("alreadyExists", "true");
/*  854 */           String applicationName = request.getParameter("applicationName");
/*  855 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.namealreadyexists", applicationName));
/*  856 */           saveErrors(request, errors);
/*      */         }
/*  858 */         else if (messagetoshow.equals("subgrpAlreadyExists"))
/*      */         {
/*  860 */           request.setAttribute("alreadyExists", "true");
/*  861 */           String applicationName = request.getParameter("name");
/*  862 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("am.webclient.monitorgroupdetails.subgroup.namealreadyexists", applicationName));
/*  863 */           saveErrors(request, errors);
/*      */         }
/*  865 */         else if (messagetoshow.equals("subgroupsuccess"))
/*      */         {
/*  867 */           if (EnterpriseUtil.isAdminServer())
/*      */           {
/*  869 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupdetails.subgroup.created.admin.text"));
/*      */           }
/*      */           else
/*      */           {
/*  873 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupdetails.subgroup.created.text"));
/*      */           }
/*  875 */           saveMessages(request, messages);
/*      */         }
/*  877 */         else if (messagetoshow.equals("subgroupdeleted"))
/*      */         {
/*  879 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupdetails.subgroup.deleted.text"));
/*  880 */           saveMessages(request, messages);
/*      */         }
/*  882 */         else if (messagetoshow.equals("refreshstatus"))
/*      */         {
/*  884 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupdetails.refresh.success"));
/*  885 */           saveMessages(request, messages);
/*      */         }
/*      */       }
/*  888 */       request.setAttribute("haid", haid);
/*  889 */       editApplication(mapping, form, request, response);
/*  890 */       if (((request.getParameter("selectM") != null) && (request.getParameter("selectM").equals("flashview"))) || (isBusiness))
/*      */       {
/*  892 */         putFlashProps(haid, request);
/*  893 */         return mapping.findForward("application");
/*      */       }
/*  895 */       int noofmonitors = 0;
/*      */       
/*  897 */       String group = "APP";
/*      */       
/*      */ 
/*  900 */       String query = "";
/*  901 */       Hashtable haidparents = DBUtil.getParentMGsforChildMGs("('" + haid + "')");
/*  902 */       String haidstoCheck = "(";
/*  903 */       ArrayList temp1 = (ArrayList)haidparents.get(haid);
/*  904 */       if (temp1 != null) {
/*  905 */         for (int i = 0; i < temp1.size(); i++) {
/*  906 */           haidstoCheck = haidstoCheck + "'" + temp1.get(i) + "',";
/*      */         }
/*      */       }
/*  909 */       haidstoCheck = haidstoCheck + "'" + haid + "')";
/*      */       
/*  911 */       String q1 = "select * from AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where USERNAME='" + userName + "' and USERID=OWNERID AND HAID IN " + haidstoCheck;
/*  912 */       if (isSSOEnabled) {
/*  913 */         q1 = "select * from AM_USERRESOURCESTABLE where USERID=" + loginUserid + " AND RESOURCEID IN " + haidstoCheck;
/*      */       }
/*  915 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(q1);
/*  916 */       if (rs1.next())
/*      */       {
/*  918 */         isowner = true;
/*      */       }
/*      */       else {
/*  921 */         isowner = false;
/*      */       }
/*  923 */       if (!isPrivilege) {
/*  924 */         isowner = true;
/*      */       }
/*  926 */       closeResultSet(rs1);
/*  927 */       ArrayList rows = new ArrayList();
/*  928 */       ArrayList userids = new ArrayList();
/*  929 */       if (isowner)
/*      */       {
/*  931 */         query = getQuery(haid, group);
/*  932 */         rows = this.mo.getRows(query);
/*  933 */         request.setAttribute("appservers", rows);
/*  934 */         noofmonitors += rows.size();
/*  935 */         group = "DBS";
/*  936 */         query = getQuery(haid, group);
/*  937 */         rows = this.mo.getRows(query);
/*  938 */         request.setAttribute("dbservers", rows);
/*      */         
/*  940 */         noofmonitors += rows.size();
/*  941 */         group = "SER";
/*  942 */         query = getQuery(haid, group);
/*  943 */         rows = this.mo.getRows(query);
/*  944 */         request.setAttribute("services", rows);
/*  945 */         noofmonitors += rows.size();
/*      */         
/*  947 */         group = "SYS";
/*  948 */         query = getQuery(haid, group);
/*  949 */         rows = this.mo.getRows(query);
/*  950 */         request.setAttribute("systems", rows);
/*  951 */         noofmonitors += rows.size();
/*      */         
/*  953 */         group = "CAM";
/*  954 */         query = getQuery(haid, group);
/*  955 */         rows = this.mo.getRows(query);
/*  956 */         request.setAttribute("CAM", rows);
/*  957 */         noofmonitors += rows.size();
/*      */         
/*  959 */         group = "SCR";
/*  960 */         query = getQuery(haid, group);
/*  961 */         rows = this.mo.getRows(query);
/*  962 */         request.setAttribute("SCR", rows);
/*  963 */         noofmonitors += rows.size();
/*      */         
/*  965 */         group = "QA";
/*  966 */         query = getQuery(haid, group);
/*  967 */         rows = this.mo.getRows(query);
/*  968 */         request.setAttribute("QA", rows);
/*  969 */         noofmonitors += rows.size();
/*      */         
/*  971 */         group = "TM";
/*  972 */         query = getQuery(haid, group);
/*  973 */         rows = this.mo.getRows(query);
/*  974 */         request.setAttribute("TM", rows);
/*  975 */         noofmonitors += rows.size();
/*      */         
/*  977 */         group = "MS";
/*  978 */         query = getQuery(haid, group);
/*  979 */         rows = this.mo.getRows(query);
/*  980 */         request.setAttribute("MS", rows);
/*  981 */         noofmonitors += rows.size();
/*      */         
/*  983 */         group = "URL";
/*  984 */         query = getQuery(haid, group);
/*  985 */         rows = this.mo.getRows(query);
/*  986 */         request.setAttribute("URL", rows);
/*  987 */         noofmonitors += rows.size();
/*      */         
/*  989 */         ChildMOHandler.showApplication(request);
/*  990 */         String childResStr = (String)request.getAttribute("childmonitorsize");
/*  991 */         int childResSize = 0;
/*      */         try
/*      */         {
/*  994 */           childResSize = Integer.parseInt(childResStr);
/*      */         }
/*      */         catch (NumberFormatException nfe) {}
/*      */         
/*      */ 
/*  999 */         noofmonitors += childResSize;
/*      */         
/* 1001 */         if ((com.adventnet.appmanager.util.Constants.isExtDeviceConfigured()) || (consoleHome.equalsIgnoreCase("true")))
/*      */         {
/* 1003 */           group = "NWD";
/* 1004 */           query = getQuery(haid, group);
/* 1005 */           rows = this.mo.getRows(query);
/* 1006 */           request.setAttribute("NWD", rows);
/* 1007 */           noofmonitors += rows.size();
/*      */           
/* 1009 */           group = "SAN";
/* 1010 */           query = getQuery(haid, group);
/* 1011 */           rows = this.mo.getRows(query);
/* 1012 */           request.setAttribute("SAN", rows);
/* 1013 */           noofmonitors += rows.size();
/*      */         }
/*      */         
/*      */ 
/* 1017 */         group = "EMO";
/* 1018 */         query = getQuery(haid, group);
/* 1019 */         rows = this.mo.getRows(query);
/* 1020 */         request.setAttribute("EMO", rows);
/* 1021 */         noofmonitors += rows.size();
/*      */         
/*      */ 
/* 1024 */         group = "ERP";
/* 1025 */         query = getQuery(haid, group);
/* 1026 */         rows = this.mo.getRows(query);
/* 1027 */         request.setAttribute("ERP", rows);
/* 1028 */         noofmonitors += rows.size();
/*      */         
/* 1030 */         group = "MOM";
/* 1031 */         query = getQuery(haid, group);
/* 1032 */         rows = this.mo.getRows(query);
/* 1033 */         request.setAttribute("MOM", rows);
/* 1034 */         noofmonitors += rows.size();
/*      */         
/* 1036 */         group = "VIR";
/* 1037 */         query = getQuery(haid, group);
/* 1038 */         rows = this.mo.getRows(query);
/* 1039 */         request.setAttribute("VIR", rows);
/* 1040 */         noofmonitors += rows.size();
/*      */         
/* 1042 */         group = "CLD";
/* 1043 */         query = getQuery(haid, group);
/* 1044 */         rows = this.mo.getRows(query);
/* 1045 */         request.setAttribute("CLD", rows);
/* 1046 */         noofmonitors += rows.size();
/*      */       }
/* 1048 */       group = "HAI";
/* 1049 */       query = getQuery(haid, group);
/* 1050 */       if (!isowner)
/*      */       {
/* 1052 */         List mainlist = new ArrayList();
/* 1053 */         Hashtable parentlist = new Hashtable();
/* 1054 */         String qry = "SELECT AM_HOLISTICAPPLICATION.HAID,TYPE from  AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable,AM_HOLISTICAPPLICATION WHERE   AM_UserPasswordTable.USERNAME='" + userName + "' AND AM_UserPasswordTable.USERID= AM_HOLISTICAPPLICATION_OWNERS.OWNERID AND AM_HOLISTICAPPLICATION.HAID= AM_HOLISTICAPPLICATION_OWNERS.HAID";
/* 1055 */         if (isSSOEnabled) {
/* 1056 */           qry = "SELECT AM_HOLISTICAPPLICATION.HAID,TYPE from  AM_USERRESOURCESTABLE,AM_HOLISTICAPPLICATION WHERE AM_USERRESOURCESTABLE.USERID=" + loginUserid + " AND AM_HOLISTICAPPLICATION.HAID= AM_USERRESOURCESTABLE.RESOURCEID";
/*      */         }
/* 1058 */         ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*      */         
/* 1060 */         String mainhaids = "(";
/* 1061 */         String childhaids = "(";
/*      */         
/*      */ 
/* 1064 */         while (rs.next())
/*      */         {
/* 1066 */           if (rs.getInt(2) == 0)
/*      */           {
/* 1068 */             mainhaids = mainhaids + "'" + rs.getString(1) + "',";
/* 1069 */             mainlist.add(rs.getString(1));
/* 1070 */             userids.add(rs.getString(1));
/*      */           }
/*      */           else
/*      */           {
/* 1074 */             childhaids = childhaids + "'" + rs.getString(1) + "',";
/* 1075 */             userids.add(rs.getString(1));
/*      */           }
/*      */         }
/*      */         
/* 1079 */         childhaids = childhaids.substring(0, childhaids.length() - 1) + ")";
/*      */         
/*      */ 
/* 1082 */         String haids = "(";
/* 1083 */         if (childhaids.length() > 2)
/*      */         {
/* 1085 */           parentlist = DBUtil.getParentMGsforChildMGs(childhaids);
/* 1086 */           List rootlist = new ArrayList();
/* 1087 */           Set ks = parentlist.keySet();
/* 1088 */           Iterator it = ks.iterator();
/* 1089 */           while (it.hasNext()) {
/* 1090 */             String key = (String)it.next();
/* 1091 */             ArrayList temp = (ArrayList)parentlist.get(key);
/* 1092 */             int index = temp.indexOf(haid);
/* 1093 */             if ((temp.size() > index + 1) && (temp.get(index + 1) != null)) {
/* 1094 */               haids = haids + "'" + temp.get(index + 1) + "',";
/*      */             }
/*      */             else {
/* 1097 */               haids = haids + "'" + key + "',";
/*      */             }
/* 1099 */             mainhaids = mainhaids + "'" + ((ArrayList)parentlist.get(key)).get(0) + "',";
/*      */           }
/*      */         }
/* 1102 */         if (mainhaids.length() > 2)
/*      */         {
/* 1104 */           mainhaids = mainhaids.substring(0, mainhaids.length() - 1) + ")";
/*      */         }
/* 1106 */         if (haids.length() > 2)
/*      */         {
/* 1108 */           haids = haids.substring(0, haids.length() - 1) + ")";
/*      */         }
/* 1110 */         query = getQueryforOperator(haid, group, haids);
/*      */       }
/* 1112 */       request.setAttribute("mainlist", userids);
/* 1113 */       rows = this.mo.getRows(query);
/* 1114 */       request.setAttribute("HAI", rows);
/*      */       
/* 1116 */       request.setAttribute("noofMG", Integer.valueOf(rows.size()));
/* 1117 */       request.setAttribute("noofmonitors", String.valueOf(noofmonitors));
/*      */       
/*      */ 
/* 1120 */       ArrayList result = getDependentMGroupsDetails(haid);
/* 1121 */       request.setAttribute("DEPENDENT_HAI", result);
/* 1122 */       request.setAttribute("DEPENDENT_noofMG", Integer.valueOf(result.size()));
/*      */       
/*      */ 
/* 1125 */       Vector parentMos = new Vector();
/* 1126 */       this.mo.getParentMGs(parentMos, haid + "");
/* 1127 */       request.setAttribute("ParentMos", parentMos);
/* 1128 */       Hashtable resnameTable = new Hashtable();
/*      */       try {
/* 1130 */         String resourcenamequery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", parentMos);
/* 1131 */         System.out.println("resourcenamequery :" + resourcenamequery);
/* 1132 */         AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(resourcenamequery);
/*      */         
/* 1134 */         while (rs.next())
/*      */         {
/* 1136 */           String resid = rs.getString("RESOURCEID");
/* 1137 */           String resname = EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME"));
/* 1138 */           resnameTable.put(resid, resname);
/*      */         }
/* 1140 */         rs.close();
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1144 */         ex.printStackTrace();
/*      */       }
/* 1146 */       request.setAttribute("resnameTable", resnameTable);
/*      */       
/*      */ 
/* 1149 */       String wherecondition = "where AM_ATTRIBUTES.ATTRIBUTE='Availability'  and AM_PARENTCHILDMAPPER.PARENTID=" + haid;
/* 1150 */       String oldavailabilityquery = "select AM_ManagedObject.resourceid, IFNULL(min(Alert.SEVERITY),-1)   from AM_ManagedObject,AM_PARENTCHILDMAPPER,AM_ATTRIBUTES left outer join Alert on SOURCE=resourceid and CATEGORY =CAST(AM_ATTRIBUTES.ATTRIBUTEID AS CHAR) " + wherecondition + " group by resourceid";
/*      */       
/*      */ 
/* 1153 */       String availabilityquery = "select AM_ManagedObject.resourceid, COALESCE(min(Alert.SEVERITY),-1)   from AM_ManagedObject join AM_PARENTCHILDMAPPER on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE left outer join Alert on SOURCE=resourceid and CATEGORY =CAST(AM_ATTRIBUTES.ATTRIBUTEID AS CHAR) " + wherecondition + " group by resourceid";
/*      */       
/*      */ 
/* 1156 */       FormatUtil.printQueryChange("ShowApplication.java", oldavailabilityquery, availabilityquery);
/* 1157 */       ArrayList availabilitystatus = this.mo.getRows(availabilityquery);
/* 1158 */       Hashtable whavailabilitymap = null;
/* 1159 */       if (availabilitystatus.size() > 0)
/*      */       {
/* 1161 */         whavailabilitymap = new Hashtable();
/*      */       }
/* 1163 */       for (int i = 0; i < availabilitystatus.size(); i++)
/*      */       {
/* 1165 */         ArrayList row = (ArrayList)availabilitystatus.get(i);
/* 1166 */         String sev = (String)row.get(1);
/*      */         try
/*      */         {
/* 1169 */           Integer.parseInt(sev);
/* 1170 */           whavailabilitymap.put(row.get(0), sev);
/*      */         }
/*      */         catch (NumberFormatException ne)
/*      */         {
/* 1174 */           whavailabilitymap.put(row.get(0), "-1");
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 1179 */         String location_query = "select NAME from AM_GMapCountryResourceRel,AM_GMapCountryCoord where AM_GMapCountryResourceRel.ID=" + haid + " and AM_GMapCountryCoord.ID=AM_GMapCountryResourceRel.LOCATIONID";
/* 1180 */         AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(location_query);
/* 1181 */         if (rs.next())
/*      */         {
/* 1183 */           request.setAttribute("location", rs.getString("NAME"));
/*      */         }
/*      */         else
/*      */         {
/* 1187 */           request.setAttribute("location", "_");
/*      */         }
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 1192 */         exc.printStackTrace();
/*      */       }
/*      */       
/* 1195 */       if (availabilitystatus.size() > 0)
/*      */       {
/* 1197 */         request.setAttribute("availabilitymap", whavailabilitymap);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1203 */       String oldalertcountquery = "select AM_ManagedObject.resourceid, IF(min(Alert.SEVERITY) != null,count(*),0)   from AM_ManagedObject,AM_PARENTCHILDMAPPER left outer join Alert on SOURCE=resourceid  where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + haid + " group by resourceid";
/*      */       
/*      */ 
/* 1206 */       String alertcountquery = "select AM_ManagedObject.resourceid, CASE WHEN min(Alert.SEVERITY) != null THEN count(*) ELSE 0 END   from AM_ManagedObject join AM_PARENTCHILDMAPPER on  AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join Alert on SOURCE=resourceid  where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " group by resourceid";
/*      */       
/*      */ 
/* 1209 */       String prodName = OEMUtil.getOEMString("product.name");
/* 1210 */       if ("IT360".equals(prodName))
/*      */       {
/* 1212 */         alertcountquery = "select AM_ManagedObject.resourceid, CASE WHEN min(Alert.SEVERITY) != null THEN count(*) ELSE 0 END   from AM_ManagedObject join AM_PARENTCHILDMAPPER on  AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join Alert on SOURCE=resourceid  where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.TYPE NOT like 'OpManager-Interface-%' group by resourceid";
/*      */       }
/*      */       
/* 1215 */       String forwardType = request.getParameter("type");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1220 */       if (("true".equalsIgnoreCase(consoleHome)) && (forwardType != null) && (!"MGDetails".equals(forwardType)) && (!"alert".equals(forwardType)))
/*      */       {
/* 1222 */         FormatUtil.printQueryChange("ShowApplication.java.java", oldalertcountquery, alertcountquery);
/*      */         
/* 1224 */         ArrayList wvAlertcounts = this.mo.getRows(alertcountquery);
/* 1225 */         Hashtable whAlertcounts = new Hashtable(wvAlertcounts.size());
/* 1226 */         for (int i = 0; i < wvAlertcounts.size(); i++)
/*      */         {
/* 1228 */           ArrayList row = (ArrayList)wvAlertcounts.get(i);
/* 1229 */           Object resourceid = row.get(0);
/* 1230 */           Object alertcount = row.get(1);
/* 1231 */           whAlertcounts.put(resourceid, AlarmUtil.getAlertsCountForResource((String)resourceid));
/*      */         }
/* 1233 */         request.setAttribute("alertcounts", whAlertcounts);
/*      */       }
/*      */       
/* 1236 */       String availability = "select SEVERITY from Alert where Alert.SOURCE=" + haid + " and Alert.CATEGORY='17'";
/* 1237 */       ArrayList avail = this.mo.getRows(availability);
/* 1238 */       if (avail.size() != 0)
/*      */       {
/*      */ 
/* 1241 */         request.setAttribute("availability", ((ArrayList)avail.get(0)).get(0));
/*      */       }
/*      */       else
/*      */       {
/* 1245 */         request.setAttribute("availability", "-1");
/*      */       }
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
/* 1257 */       Map rtMap = DataCollectionDBUtil.getMonitorsInfoForBA(haid);
/* 1258 */       request.setAttribute("responsetimes", rtMap);
/*      */       
/* 1260 */       ResultSet rs = null;
/*      */       try
/*      */       {
/* 1263 */         String owner = "";
/* 1264 */         String selOwnQuery = "select USERNAME from AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and HAID=" + haid;
/* 1265 */         AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(selOwnQuery);
/* 1266 */         while (rs.next())
/*      */         {
/* 1268 */           if (owner.equals(""))
/*      */           {
/* 1270 */             owner = rs.getString("USERNAME");
/*      */           }
/*      */           else
/*      */           {
/* 1274 */             owner = owner + ", " + rs.getString("USERNAME");
/*      */           }
/*      */         }
/* 1277 */         rs.close();
/* 1278 */         ((DynaActionForm)form).set("owner", owner);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1282 */         e.printStackTrace();
/* 1283 */         rs = null;
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 1289 */       ee.printStackTrace();
/* 1290 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", ee.toString()));
/*      */     }
/*      */     
/*      */ 
/* 1294 */     if (!errors.isEmpty()) {
/* 1295 */       saveErrors(request, errors);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1305 */       request.setAttribute("type", request.getParameter("type"));
/* 1306 */       if ((consoleHome != null) && (consoleHome.equalsIgnoreCase("true")))
/*      */       {
/* 1308 */         String resStr = null;
/*      */         
/*      */         try
/*      */         {
/* 1312 */           Vector hrids = new Vector();
/* 1313 */           this.mo.getAllChildsinTree(hrids, haid);
/* 1314 */           resStr = hrids.toString();
/* 1315 */           resStr = resStr.substring(1, resStr.length() - 1);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1319 */           ex.printStackTrace();
/*      */         }
/* 1321 */         request.setAttribute("childMonitorsInBS", resStr);
/*      */         
/* 1323 */         return new ActionForward("/it360/jsp/consoleHealthandAvailabilityView.jsp?type=application&mgId=" + haid);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1328 */       e.printStackTrace();
/*      */     }
/* 1330 */     if ((popup != null) && (popup.equals("true")))
/*      */     {
/* 1332 */       return new ActionForward("/jsp/NewSubGroup.jsp?haid=" + haid);
/*      */     }
/* 1334 */     return mapping.findForward("application");
/*      */   }
/*      */   
/*      */   private void createMapView(ActionForm form, HttpServletRequest request) {
/* 1338 */     try { String createMV = (String)((DynaActionForm)form).get("createMV");
/*      */       
/* 1340 */       String haid = request.getParameter("haid");
/* 1341 */       String mapViewId = MapViewUtil.getMapViewIdForBSGId(haid);
/* 1342 */       String username = (String)((DynaActionForm)form).get("owner");
/* 1343 */       String applicationName = (String)((DynaActionForm)form).get("name");
/*      */       
/* 1345 */       if ("modifyMV".equalsIgnoreCase(createMV))
/*      */       {
/*      */         try
/*      */         {
/* 1349 */           AMLog.debug("Creating Mapviews for BSG while editing " + haid + " and its subgroups usename " + username);
/* 1350 */           MapViewUtil.createMVForBsgAndSubgroups(haid, username);
/* 1351 */           AMLog.debug("Created !!");
/*      */           
/*      */ 
/* 1354 */           ((DynaActionForm)form).set("createMV", "");
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1359 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 1362 */       else if ((createMV == null) && (mapViewId != null))
/*      */       {
/* 1364 */         MapViewUtil.editMapViewName(Integer.parseInt(mapViewId), applicationName);
/* 1365 */         AMLog.debug("Modified the Mapview name for MV Id " + mapViewId + " to " + applicationName);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1370 */       AMLog.debug("Exception while creating mapview :: ShowApplication " + ex);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean checkForMGAssignedtoUser(String haid, HttpServletRequest request)
/*      */   {
/* 1378 */     ResultSet res1 = null;
/*      */     try
/*      */     {
/* 1381 */       Vector mgsForOperator = null;
/* 1382 */       if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 1383 */         mgsForOperator = ClientDBUtil.getUserResourceID(request);
/*      */       } else {
/* 1385 */         mgsForOperator = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*      */       }
/*      */       
/* 1388 */       if (mgsForOperator.contains(haid))
/*      */       {
/* 1390 */         return true;
/*      */       }
/*      */       
/*      */ 
/* 1394 */       Vector allchildids = new Vector();
/* 1395 */       ManagedApplication.getChildIDs(allchildids, haid);
/* 1396 */       String subGroupsOwnerQuery; if (allchildids.size() > 0)
/*      */       {
/* 1398 */         subGroupsOwnerQuery = "select AM_UserPasswordTable.USERNAME from AM_UserPasswordTable,AM_UserGroupTable,AM_HOLISTICAPPLICATION_OWNERS where AM_UserPasswordTable.USERID=AM_HOLISTICAPPLICATION_OWNERS.OWNERID and " + ManagedApplication.getCondition("HAID", allchildids) + " and AM_UserGroupTable.USERNAME='" + request.getRemoteUser() + "' and GROUPNAME='OPERATOR'";
/* 1399 */         AMConnectionPool.getInstance();res1 = AMConnectionPool.executeQueryStmt(subGroupsOwnerQuery);
/* 1400 */         System.out.println("subGroupsOwnerQuery" + subGroupsOwnerQuery);
/* 1401 */         boolean bool2; if (res1.next())
/*      */         {
/* 1403 */           res1.close();
/* 1404 */           return true;
/*      */         }
/*      */         
/*      */ 
/* 1408 */         res1.close();
/* 1409 */         return false;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1414 */       return 0;
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 1420 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/* 1425 */         if (res1 != null) res1.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/* 1429 */     return false;
/*      */   }
/*      */   
/*      */   private String getQuery(String haid, String group) {
/* 1433 */     String excludeIntfCond = " ";
/* 1434 */     if ((EnterpriseUtil.isIt360MSPEdition()) && (group.equals("NWD"))) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1439 */     String eumChildListCond = " AND AM_ManagedObject.resourceid NOT IN (" + com.adventnet.appmanager.util.Constants.getEUMChildString() + ")";
/*      */     
/* 1441 */     if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */     {
/* 1443 */       eumChildListCond = "";
/*      */     }
/*      */     
/* 1446 */     String extDeviceType = "OpManager-%";
/* 1447 */     if (group.equals("SAN"))
/*      */     {
/* 1449 */       extDeviceType = "OpStor-%";
/*      */     }
/*      */     
/* 1452 */     String oldquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.displayname,AM_ManagedObject.type, COALESCE(SEVERITY,-1), IFNULL(AM_ManagedResourceType.SHORTNAME,AM_ManagedResourceType.RESOURCETYPE),AM_ManagedResourceType.IMAGEPATH,AM_UnManagedNodes.resid from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType,AM_ATTRIBUTES Left outer join Alert on AM_ManagedObject.RESOURCEID=source  and CATEGORY=CAST(AM_ATTRIBUTES.ATTRIBUTEID AS CHAR)   left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID  and AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type " + eumChildListCond + " and AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.type and AM_ATTRIBUTES.ATTRIBUTE='Health' order by AM_ManagedObject.RESOURCENAME";
/*      */     
/*      */ 
/* 1455 */     String query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.displayname,AM_ManagedObject.type, -1, COALESCE(AM_ManagedResourceType.SHORTNAME,AM_ManagedResourceType.RESOURCETYPE),AM_ManagedResourceType.IMAGEPATH,AM_UnManagedNodes.resid from AM_PARENTCHILDMAPPER join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID join AM_ManagedResourceType   on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type  left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedResourceType.RESOURCEGROUP='" + group + "'" + excludeIntfCond + eumChildListCond + " order by AM_ManagedObject.RESOURCENAME";
/* 1456 */     if ((group.equals("NWD")) || (group.equals("SAN")))
/*      */     {
/* 1458 */       query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.displayname,AM_ManagedObject.type, -1, COALESCE(AM_ManagedResourceType.SHORTNAME,AM_ManagedResourceType.RESOURCETYPE),AM_ManagedResourceType.IMAGEPATH,AM_UnManagedNodes.resid from AM_PARENTCHILDMAPPER join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID join AM_ManagedResourceType   on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type  left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedResourceType.RESOURCEGROUP='" + group + "'" + excludeIntfCond + eumChildListCond + " and AM_ManagedObject.type like '" + extDeviceType + "' order by AM_ManagedObject.RESOURCENAME";
/*      */     }
/*      */     
/*      */ 
/* 1462 */     FormatUtil.printQueryChange("ShowApplication.java", oldquery, query);
/* 1463 */     return query;
/*      */   }
/*      */   
/*      */   private String getQueryforOperator(String haid, String group, String haids) {
/* 1467 */     String eumChildListCond = " AND AM_ManagedObject.resourceid NOT IN (" + com.adventnet.appmanager.util.Constants.getEUMChildString() + ")";
/*      */     
/* 1469 */     if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */     {
/* 1471 */       eumChildListCond = "";
/*      */     }
/*      */     
/* 1474 */     String oldquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.displayname,AM_ManagedObject.type, IFNULL(SEVERITY,-1), IFNULL(AM_ManagedResourceType.SHORTNAME,AM_ManagedResourceType.RESOURCETYPE),AM_ManagedResourceType.IMAGEPATH,AM_UnManagedNodes.resid from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType,AM_ATTRIBUTES Left outer join Alert on AM_ManagedObject.RESOURCEID=source  and CATEGORY=CAST(AM_ATTRIBUTES.ATTRIBUTEID AS CHAR)   left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_PARENTCHILDMAPPER.PARENTID = " + haid + " and AM_PARENTCHILDMAPPER.CHILDID in " + haids + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID  and AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type and AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.type and AM_ATTRIBUTES.ATTRIBUTE='Health' order by AM_ManagedObject.RESOURCENAME";
/*      */     
/*      */ 
/* 1477 */     String query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.displayname,AM_ManagedObject.type, -1, coalesce(AM_ManagedResourceType.SHORTNAME,AM_ManagedResourceType.RESOURCETYPE),AM_ManagedResourceType.IMAGEPATH,AM_UnManagedNodes.resid from AM_PARENTCHILDMAPPER ,AM_ManagedResourceType, AM_ManagedObject    left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_PARENTCHILDMAPPER.PARENTID = " + haid + " and AM_PARENTCHILDMAPPER.CHILDID in " + haids + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID  and AM_ManagedResourceType.RESOURCEGROUP='" + group + "'  " + eumChildListCond + " and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type order by AM_ManagedObject.RESOURCENAME";
/*      */     
/*      */ 
/* 1480 */     FormatUtil.printQueryChange("ShowApplication.java", oldquery, query);
/* 1481 */     return query;
/*      */   }
/*      */   
/*      */   public ActionForward showURLDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1487 */     String haid = (String)((DynaActionForm)form).get("haid");
/*      */     
/* 1489 */     ArrayList urldetails = this.mo.getRows("select AM_ManagedObject.resourcename,AM_URL.* from AM_URL,AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_URL.urlid=AM_PARENTCHILDMAPPER.CHILDID AND AM_PARENTCHILDMAPPER.PARENTID=" + haid + " AND AM_ManagedObject.resourceid=AM_URL.urlid");
/* 1490 */     ArrayList updetails = this.mo.getRows("select RESID,count(*),avg(responsetime) from AM_ManagedObjectData where availability = 1 group by RESID");
/* 1491 */     ArrayList totaldetails = this.mo.getRows("select RESID,count(*),min(COLLECTIONTIME) from AM_ManagedObjectData  group by RESID");
/* 1492 */     ArrayList availabilitydetails = this.mo.getRows("select RESID,CASE when availability='0' then reason else '1' END  from AM_ManagedObjectData,AM_URLData,AM_PARENTCHILDMAPPER where AM_ManagedObjectData.resid=AM_URLData.urlid and  AM_ManagedObjectData.COLLECTIONTIME=AM_URLData.COLLECTIONTIME and AM_URLData.urlid=AM_PARENTCHILDMAPPER.CHILDID AND AM_PARENTCHILDMAPPER.PARENTID=" + haid + "   order by AM_ManagedObjectData.COLLECTIONTIME ");
/* 1493 */     int rowcount = urldetails.size();
/* 1494 */     if (rowcount == 0)
/*      */     {
/*      */ 
/* 1497 */       return null;
/*      */     }
/* 1499 */     Hashtable upcount = new Hashtable();
/* 1500 */     Hashtable totalcount = new Hashtable();
/* 1501 */     Hashtable responsedetails = new Hashtable();
/* 1502 */     Hashtable monitorstarttime = new Hashtable();
/* 1503 */     Hashtable wHAvailabilityDetails = new Hashtable();
/*      */     
/* 1505 */     for (int i = 0; i < updetails.size(); i++)
/*      */     {
/* 1507 */       if (upcount == null)
/*      */       {
/* 1509 */         upcount = new Hashtable();
/* 1510 */         responsedetails = new Hashtable();
/*      */       }
/* 1512 */       ArrayList row = (ArrayList)updetails.get(i);
/* 1513 */       String urlid = (String)row.get(0);
/* 1514 */       String wsupcount = (String)row.get(1);
/* 1515 */       if (wsupcount == null) wsupcount = "0";
/* 1516 */       String responsetime = (String)row.get(2);
/* 1517 */       upcount.put(urlid, wsupcount);
/* 1518 */       responsedetails.put(urlid, responsetime);
/*      */     }
/*      */     
/* 1521 */     for (int i = 0; i < totaldetails.size(); i++)
/*      */     {
/* 1523 */       if (totalcount == null)
/*      */       {
/* 1525 */         totalcount = new Hashtable();
/*      */       }
/* 1527 */       ArrayList row = (ArrayList)totaldetails.get(i);
/* 1528 */       String urlid = (String)row.get(0);
/* 1529 */       String wstotalcount = (String)row.get(1);
/*      */       
/* 1531 */       totalcount.put(urlid, wstotalcount);
/* 1532 */       String wsMonitorStartTime = (String)row.get(2);
/* 1533 */       monitorstarttime.put(urlid, wsMonitorStartTime);
/*      */     }
/* 1535 */     for (int i = 0; i < availabilitydetails.size(); i++)
/*      */     {
/* 1537 */       ArrayList templist = (ArrayList)availabilitydetails.get(i);
/* 1538 */       String urlid = (String)templist.get(0);
/* 1539 */       String availability = (String)templist.get(1);
/* 1540 */       if (availability == null)
/* 1541 */         availability = "";
/* 1542 */       wHAvailabilityDetails.put(urlid, availability);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1550 */     ArrayList urlstats = new ArrayList(urldetails.size());
/*      */     
/* 1552 */     for (int i = 0; i < urldetails.size(); i++)
/*      */     {
/* 1554 */       UrlData urldata = new UrlData();
/* 1555 */       String wsSeverity = "class=\"whitegrayborder\"";
/* 1556 */       if (i % 2 == 0)
/*      */       {
/* 1558 */         wsSeverity = "class=\"whitegrayborder\"";
/*      */       }
/*      */       else
/*      */       {
/* 1562 */         wsSeverity = "class=\"yellowgrayborder\"";
/*      */       }
/* 1564 */       ArrayList row = (ArrayList)urldetails.get(i);
/* 1565 */       String monitorname = (String)row.get(0);
/* 1566 */       urldata.setUrlName(monitorname);
/* 1567 */       String urlid = (String)row.get(1);
/* 1568 */       urldata.setUrlId(urlid);
/* 1569 */       String url = (String)row.get(2);
/* 1570 */       urldata.setURL(url);
/* 1571 */       String wsupcount = (String)upcount.get(urlid);
/* 1572 */       String wstotalcount = (String)totalcount.get(urlid);
/* 1573 */       String responsetime = (String)responsedetails.get(urlid);
/*      */       
/* 1575 */       String wsAvailability = (String)wHAvailabilityDetails.get(urlid);
/*      */       
/* 1577 */       urldata.setReason("&nbsp;");
/* 1578 */       if (wsAvailability == null)
/*      */       {
/* 1580 */         wsAvailability = "1";
/*      */       }
/* 1582 */       if (!wsAvailability.equals("1"))
/*      */       {
/* 1584 */         wsSeverity = "class=\"errorgrayborder\"";
/* 1585 */         urldata.setReason(wsAvailability);
/* 1586 */         wsAvailability = "0";
/*      */       }
/*      */       else
/*      */       {
/* 1590 */         wsAvailability = "5";
/*      */       }
/*      */       
/* 1593 */       if (responsetime == null)
/*      */       {
/* 1595 */         responsetime = "NA";
/*      */       }
/* 1597 */       int wnupcount = 0;
/* 1598 */       int wntotalcount = 0;
/* 1599 */       float wfavailabilty = 0.0F;
/*      */       try
/*      */       {
/* 1602 */         wnupcount = Integer.parseInt(wsupcount);
/* 1603 */         wntotalcount = Integer.parseInt(wstotalcount);
/* 1604 */         wfavailabilty = wnupcount * 100 / wntotalcount;
/*      */       }
/*      */       catch (Exception ee) {}
/*      */       
/*      */ 
/*      */ 
/* 1610 */       urldata.setAvailability(wfavailabilty);
/* 1611 */       String wsmonitorstarttime = (String)monitorstarttime.get(urlid);
/* 1612 */       if (wsmonitorstarttime == null)
/* 1613 */         wsmonitorstarttime = "-";
/* 1614 */       urldata.setMonitorStartTime(wsmonitorstarttime);
/* 1615 */       urldata.setSeverity(wsAvailability);
/* 1616 */       urldata.setResponseTime(responsetime);
/* 1617 */       urlstats.add(urldata);
/*      */     }
/*      */     
/*      */ 
/* 1621 */     request.setAttribute("urlstats", urlstats);
/* 1622 */     return new ActionForward("/jsp/urldetails.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward editApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1630 */     String haid = (String)((DynaActionForm)form).get("haid");
/* 1631 */     request.setAttribute("haid", haid);
/* 1632 */     request.setAttribute("gmapcountries", DBUtil.getGMapCountries());
/* 1633 */     Properties groupDetails = BusinessViewUtil.getGroupDetails(haid, this.mo);
/* 1634 */     String fromwhere = request.getParameter("fromwhere");
/* 1635 */     if (fromwhere != null) {
/* 1636 */       request.setAttribute("fromwhere", fromwhere);
/*      */     } else {
/* 1638 */       request.setAttribute("fromwhere", "detailspage");
/*      */     }
/* 1640 */     String name = groupDetails.getProperty("name");
/* 1641 */     String description = groupDetails.getProperty("description");
/* 1642 */     String creationdate = groupDetails.getProperty("creationdate");
/* 1643 */     String lastmodified = groupDetails.getProperty("lastmodified");
/* 1644 */     String MGtype = groupDetails.getProperty("MGtype");
/* 1645 */     String groupType = groupDetails.getProperty("grouptype");
/* 1646 */     boolean isUseADDM = ADMUtil.useADDM(haid);
/* 1647 */     request.setAttribute("MGtype", MGtype);
/* 1648 */     ((DynaActionForm)form).set("haid", haid);
/* 1649 */     ((DynaActionForm)form).set("name", name);
/* 1650 */     ((DynaActionForm)form).set("description", description);
/* 1651 */     ((DynaActionForm)form).set("grouptype", groupType);
/* 1652 */     request.setAttribute("isUseADDM", Boolean.valueOf(isUseADDM));
/* 1653 */     if (groupDetails.getProperty("locationid") != null) {
/* 1654 */       ((DynaActionForm)form).set("locationid", groupDetails.get("locationid"));
/*      */     }
/* 1656 */     if (groupDetails.get("allowners") != null) {
/* 1657 */       ((DynaActionForm)form).set("allowners", groupDetails.get("allowners"));
/*      */     } else {
/* 1659 */       ((DynaActionForm)form).set("allowners", new ArrayList());
/*      */     }
/* 1661 */     if (groupDetails.get("selectedowners") != null) {
/* 1662 */       ((DynaActionForm)form).set("selectedowners", groupDetails.get("selectedowners"));
/*      */     } else {
/* 1664 */       ((DynaActionForm)form).set("selectedowners", new ArrayList());
/*      */     }
/* 1666 */     ((DynaActionForm)form).set("creationdate", creationdate);
/* 1667 */     ((DynaActionForm)form).set("lastmodified", lastmodified);
/* 1668 */     if (groupDetails.size() > 0) {
/* 1669 */       if ("3".equals(groupType)) {
/* 1670 */         return new ActionForward("/admin/createapplication.do?method=createapp&isEdit=true&grouptype=3&haid=" + haid + "&haName=" + name, true);
/*      */       }
/* 1672 */       if ("4".equals(groupType)) {
/* 1673 */         return new ActionForward("/admin/createapplication.do?method=createapp&isEdit=true&grouptype=4&haid=" + haid + "&haName=" + name, true);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1678 */       return new ActionForward("/applications.do");
/*      */     }
/* 1680 */     return new ActionForward("/jsp/ModifyApplication.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1690 */     boolean isRESTAPI = false;
/* 1691 */     createMapView(form, request);
/* 1692 */     if ((request.getAttribute("isRESTAPI") != null) && (((String)request.getAttribute("isRESTAPI")).equalsIgnoreCase("true")))
/*      */     {
/*      */ 
/* 1695 */       isRESTAPI = true;
/*      */     }
/* 1697 */     String haid = null;
/* 1698 */     String applicationName = null;
/* 1699 */     String description = null;
/* 1700 */     String location = null;
/* 1701 */     String grouptype = null;
/* 1702 */     String fromwhere = null;
/* 1703 */     String ownerscoma = "";
/* 1704 */     BusinessViewUtil.deleteBussinessViewCache();
/* 1705 */     if (!isRESTAPI) {
/* 1706 */       haid = (String)((DynaActionForm)form).get("haid");
/*      */       
/* 1708 */       applicationName = (String)((DynaActionForm)form).get("name");
/* 1709 */       applicationName = StringEscapeUtils.unescapeXml(applicationName);
/* 1710 */       description = (String)((DynaActionForm)form).get("description");
/* 1711 */       description = StringEscapeUtils.unescapeXml(description);
/* 1712 */       location = (String)((DynaActionForm)form).get("locationid");
/* 1713 */       grouptype = (String)((DynaActionForm)form).get("grouptype");
/* 1714 */       fromwhere = request.getParameter("fromwhere");
/*      */     } else {
/* 1716 */       haid = (String)request.getAttribute("haid");
/* 1717 */       applicationName = (String)request.getAttribute("name");
/* 1718 */       description = (String)request.getAttribute("description");
/* 1719 */       location = (String)request.getAttribute("locationid");
/* 1720 */       grouptype = (String)request.getAttribute("grouptype");
/* 1721 */       fromwhere = (String)request.getAttribute("fromwhere");
/* 1722 */       ownerscoma = (String)request.getAttribute("owners");
/*      */     }
/*      */     
/* 1725 */     ArrayList<String> alreadyAssignedOwners = new ArrayList();
/* 1726 */     if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (com.adventnet.appmanager.util.Constants.doConcurrentUserResourceUpdate))
/*      */     {
/* 1728 */       alreadyAssignedOwners = DBUtil.getRowsForSingleColumn("select OWNERID from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + haid);
/*      */     }
/*      */     
/*      */ 
/* 1732 */     ActionMessages messages = new ActionMessages();
/* 1733 */     ActionErrors errors = new ActionErrors();
/* 1734 */     Hashtable toNotifier = new Hashtable();
/* 1735 */     ResultSet rs = null;
/* 1736 */     toNotifier.put("oldMGName", DBUtil.getDisplaynameforResourceID(haid));
/*      */     try {
/* 1738 */       AMManagedObject ammo = new AMManagedObject(Integer.parseInt(haid));
/* 1739 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/* 1740 */       if (ammo != null) {
/* 1741 */         ischild = false;
/*      */         try {
/* 1743 */           rs = AMConnectionPool.executeQueryStmt("select TYPE from AM_HOLISTICAPPLICATION where HAID=" + haid);
/* 1744 */           if ((rs.next()) && 
/* 1745 */             ("1".equals(rs.getString("TYPE")))) {
/* 1746 */             ischild = true;
/*      */           }
/*      */         }
/*      */         catch (Exception e) {
/* 1750 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/* 1753 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/* 1755 */         String condition = " and ha.TYPE=0";
/* 1756 */         if (ischild) {
/* 1757 */           condition = " and mo.RESOURCEID IN (select CHILDID from AM_PARENTCHILDMAPPER where parentid in (select parentid from AM_PARENTCHILDMAPPER where childid=" + haid + "))";
/*      */         }
/*      */         
/* 1760 */         ArrayList lists = this.mo.getRows("select mo.RESOURCEID from AM_ManagedObject mo,AM_HOLISTICAPPLICATION ha where mo.RESOURCEID=ha.HAID and mo.TYPE='HAI' AND mo.RESOURCENAME='" + applicationName + "' AND mo.RESOURCEID NOT IN(" + haid + ")" + condition);
/* 1761 */         if (lists.size() > 0)
/*      */         {
/*      */ 
/* 1764 */           if (isRESTAPI)
/*      */           {
/* 1766 */             request.setAttribute("responsemessage", FormatUtil.getString("haid.applicationcreation.namealreadyexists", new String[] { applicationName }));
/* 1767 */             return null;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1775 */           String monitorGroupName = request.getParameter("name");
/*      */           
/*      */ 
/* 1778 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("haid.applicationcreation.namealreadyexists", applicationName));
/* 1779 */           saveMessages(request, messages);
/* 1780 */           return new ActionForward("/showapplication.do?method=showApplication&haid=" + haid);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1785 */         if (isRESTAPI) {
/* 1786 */           String qry = "select RESOURCENAME,DESCRIPTION,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + haid;
/* 1787 */           rs = DBQueryUtil.executeQueryStmt(qry);
/* 1788 */           if (rs.next()) {
/* 1789 */             if (applicationName == null) {
/* 1790 */               applicationName = rs.getString("RESOURCENAME");
/*      */               
/* 1792 */               ammo.setDISPLAYNAME(applicationName);
/*      */             }
/* 1794 */             if (description == null) {
/* 1795 */               description = rs.getString("DESCRIPTION");
/* 1796 */               ammo.setDESCRIPTION(description);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1802 */         ammo.setDISPLAYNAME(applicationName);
/* 1803 */         ammo.setDESCRIPTION(description);
/*      */         
/* 1805 */         ammo.setType("HAI");
/* 1806 */         dao.update(ammo);
/* 1807 */         Properties applications = null;
/* 1808 */         if (isRESTAPI) {
/* 1809 */           if (applicationName != null) {
/* 1810 */             applications = (Properties)request.getAttribute("applications");
/*      */             
/* 1812 */             applications.setProperty(String.valueOf(haid), applicationName);
/*      */           }
/*      */         }
/*      */         else {
/* 1816 */           applications = (Properties)this.servlet.getServletConfig().getServletContext().getAttribute("applications");
/*      */           
/*      */ 
/* 1819 */           applications.setProperty(String.valueOf(haid), applicationName);
/*      */         }
/*      */         
/* 1822 */         String creationdate = new java.sql.Date(System.currentTimeMillis()).toString();
/*      */         
/* 1824 */         String updatequery = "";
/* 1825 */         if (!isRESTAPI) {
/* 1826 */           updatequery = "update AM_HOLISTICAPPLICATION set OWNER='admin',GROUPTYPE=" + grouptype + ",MODIFIEDDATE =" + System.currentTimeMillis() + "  where  HAID=" + haid;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1832 */           EnterpriseUtil.addUpdateQueryToFile(updatequery);
/*      */         } else {
/* 1834 */           updatequery = "update AM_HOLISTICAPPLICATION set OWNER='admin',MODIFIEDDATE =" + System.currentTimeMillis() + "  where  HAID=" + haid;
/*      */           
/*      */ 
/*      */ 
/* 1838 */           EnterpriseUtil.addUpdateQueryToFile(updatequery);
/*      */         }
/*      */         
/* 1841 */         this.mo.executeUpdateStmt(updatequery);
/* 1842 */         EnterpriseUtil.addUpdateQueryToFile(updatequery);
/* 1843 */         String tempString = description;
/* 1844 */         if (tempString == null) {
/* 1845 */           tempString = " ";
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1850 */         if (tempString.indexOf("\n") != -1) {
/* 1851 */           String tokenString = "";
/* 1852 */           StringTokenizer token = new StringTokenizer(tempString, "\n");
/*      */           
/* 1854 */           while (token.hasMoreTokens()) {
/* 1855 */             String tempToketString = token.nextToken();
/* 1856 */             tempToketString = tempToketString.trim();
/* 1857 */             tokenString = tokenString + " " + tempToketString;
/*      */           }
/* 1859 */           tempString = tokenString;
/*      */         }
/* 1861 */         String newapplicationName = DBQueryUtil.addEscapeSequence(applicationName);
/*      */         
/* 1863 */         String newtempString = DBQueryUtil.addEscapeSequence(tempString);
/*      */         
/* 1865 */         EnterpriseUtil.addUpdateQueryToFile("update AM_ManagedObject set DISPLAYNAME='" + newapplicationName + "',DESCRIPTION='" + newtempString + "' where RESOURCEID=" + haid);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 1872 */           if (location != null) {
/* 1873 */             int loc = Integer.parseInt(location);
/* 1874 */             String query = "insert into AM_GMapCountryResourceRel values(" + haid + "," + loc + ")";
/*      */             
/* 1876 */             System.out.println(query);
/* 1877 */             int rowsaffected = AMConnectionPool.executeUpdateStmt(query);
/*      */             
/* 1879 */             if (rowsaffected == -1) {
/* 1880 */               throw new SQLException();
/*      */             }
/*      */           }
/*      */         } catch (NumberFormatException e) {
/* 1884 */           String query1 = "delete from AM_GMapCountryResourceRel where ID=" + haid;
/*      */           
/*      */ 
/* 1887 */           AMConnectionPool.executeUpdateStmt(query1);
/* 1888 */           EnterpriseUtil.addUpdateQueryToFile(query1);
/*      */         }
/*      */         catch (SQLException e)
/*      */         {
/* 1892 */           String update = "update AM_GMapCountryResourceRel set LOCATIONID =" + Integer.parseInt(location) + " where ID= " + haid;
/*      */           
/*      */ 
/* 1895 */           System.out.println(update);
/* 1896 */           AMConnectionPool.executeUpdateStmt(update);
/* 1897 */           EnterpriseUtil.addUpdateQueryToFile(update);
/*      */         }
/*      */         
/* 1900 */         rs = null;
/* 1901 */         ArrayList<String> userList = new ArrayList();
/*      */         try {
/* 1903 */           rs = AMConnectionPool.executeQueryStmt("select VALUEID from AM_MYFIELDS_ENTITYDATA where RESOURCEID=" + haid + " and DATATABLE='AM_UserPasswordTable'");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1908 */           while (rs.next()) {
/* 1909 */             userList.add(rs.getString("VALUEID"));
/*      */           }
/*      */         }
/*      */         catch (Exception ex) {}finally
/*      */         {
/* 1914 */           if (rs != null) {
/* 1915 */             AMConnectionPool.closeStatement(rs);
/*      */           }
/*      */         }
/* 1918 */         String[] owners = null;
/* 1919 */         boolean updateOwnerInfo = true;
/* 1920 */         String userName = request.getRemoteUser();
/* 1921 */         if ((DBUtil.isDelegatedAdminEnabled()) && (DBUtil.isDelegatedAdmin(userName))) {
/* 1922 */           updateOwnerInfo = false;
/*      */         }
/* 1924 */         if (updateOwnerInfo) {
/* 1925 */           if (isRESTAPI) {
/* 1926 */             if (ownerscoma != null) {
/* 1927 */               owners = ownerscoma.split(",");
/* 1928 */               this.mo.executeUpdateStmt("delete from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + haid);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 1933 */             this.mo.executeUpdateStmt("delete from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + haid);
/*      */             
/* 1935 */             owners = request.getParameterValues("selectedowners_list");
/*      */           }
/* 1937 */           if ((owners != null) && (!owners.equals("null"))) {
/* 1938 */             AMLog.debug("[<ShowApplication>:<update>] owners: " + owners);
/* 1939 */             int relationid = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_ENTITYDATA") - 1;
/*      */             
/* 1941 */             for (int i = 0; i < owners.length; i++) {
/* 1942 */               String own = owners[i];
/* 1943 */               String query = "insert into AM_HOLISTICAPPLICATION_OWNERS values(" + haid + "," + own + ")";
/*      */               
/* 1945 */               if (!userList.contains(own)) {
/* 1946 */                 relationid += 1;
/* 1947 */                 String query1 = "insert into AM_MYFIELDS_ENTITYDATA values(" + relationid + "," + haid + ",'AM_UserPasswordTable'," + own + ")";
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1953 */                 this.mo.executeUpdateStmt(query1);
/*      */               }
/* 1955 */               this.mo.executeUpdateStmt(query);
/*      */             }
/*      */           }
/*      */         }
/* 1959 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationupdation.success"));
/*      */         
/* 1961 */         saveMessages(request, messages);
/*      */         try {
/* 1963 */           toNotifier.put("MGID", haid);
/* 1964 */           toNotifier.put("newMGName", applicationName);
/* 1965 */           toNotifier.put("EventType", "Modified");
/* 1966 */           MGActionNotifier notifyConsole = MGActionNotifier.getInstance();
/*      */           
/* 1968 */           notifyConsole.setProperties(toNotifier);
/* 1969 */           Thread t = new Thread(notifyConsole);
/* 1970 */           t.start();
/*      */         } catch (Exception e) {
/* 1972 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1977 */         int checkAdminId = Integer.parseInt(haid);
/* 1978 */         if ((!EnterpriseUtil.isAdminServer()) || (checkAdminId <= com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) {
/* 1979 */           new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 17, System.currentTimeMillis(), true, true, 1);
/*      */           
/*      */ 
/* 1982 */           new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 18, System.currentTimeMillis(), true, false, 2);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1988 */         if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (com.adventnet.appmanager.util.Constants.doConcurrentUserResourceUpdate))
/*      */         {
/*      */           try
/*      */           {
/* 1992 */             Vector<String> allOwnersInHaidHierarchy = new Vector();
/* 1993 */             RestrictedUsersViewUtil.getAllOwnersInMGTree(allOwnersInHaidHierarchy, new String[] { haid });
/* 1994 */             if (!alreadyAssignedOwners.isEmpty())
/*      */             {
/* 1996 */               for (String owner : alreadyAssignedOwners)
/*      */               {
/* 1998 */                 if ((!allOwnersInHaidHierarchy.contains(owner)) && (RestrictedUsersViewUtil.isRestrictedRole(owner)))
/*      */                 {
/* 2000 */                   allOwnersInHaidHierarchy.add(owner);
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/* 2005 */             if (!allOwnersInHaidHierarchy.isEmpty())
/*      */             {
/* 2007 */               AMLog.debug("[ShowApplication::(update)]ruser(s) : " + allOwnersInHaidHierarchy);
/* 2008 */               RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(new ArrayList(allOwnersInHaidHierarchy));
/*      */             }
/*      */             
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 2014 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2020 */         if (isRESTAPI) {
/* 2021 */           request.setAttribute("responsemessage", "success");
/* 2022 */           return null;
/*      */         }
/* 2024 */         if ((fromwhere != null) && (fromwhere.equals("allmonitorgroups"))) {
/* 2025 */           return new ActionForward("/showresource.do?method=showMonitorGroupView", true);
/*      */         }
/*      */         
/*      */ 
/* 2029 */         return new ActionForward("/showapplication.do?haid=" + haid + "&method=showApplication", true);
/*      */       }
/*      */       
/*      */ 
/* 2033 */       if (isRESTAPI) {
/* 2034 */         request.setAttribute("responsemessage", "invalid haid");
/* 2035 */         return null;
/*      */       }
/* 2037 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed"));
/*      */       
/* 2039 */       saveErrors(request, errors);
/* 2040 */       return mapping.getInputForward();
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*      */       boolean ischild;
/*      */       
/* 2046 */       errorcode = se.getErrorCode();
/* 2047 */       if (errorcode == 1062) {
/* 2048 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.namealreadyexists", applicationName));
/*      */       }
/*      */       else
/*      */       {
/* 2052 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", se.toString()));
/*      */       }
/*      */       
/* 2055 */       saveErrors(request, errors);
/* 2056 */       return new ActionForward("/showapplication.do?method=editApplication");
/*      */     }
/*      */     catch (Exception ee) {
/*      */       int errorcode;
/* 2060 */       ee.printStackTrace();
/* 2061 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", ee.toString()));
/*      */       
/* 2063 */       saveErrors(request, errors);
/* 2064 */       return new ActionForward("/showapplication.do?method=editApplication");
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2071 */         if (rs != null) {
/* 2072 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/* 2076 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showSnapshot(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2088 */     String haid = (String)((DynaActionForm)form).get("haid");
/* 2089 */     if (haid != null)
/*      */     {
/* 2091 */       ArrayList list = AlarmUtil.getAlertsForResource(Integer.parseInt(haid));
/* 2092 */       if (list.size() != 0)
/*      */       {
/* 2094 */         request.setAttribute("recent5Alarms", list);
/*      */       }
/*      */     }
/* 2097 */     editApplication(mapping, form, request, response);
/*      */     
/* 2099 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(haid, 300L);
/* 2100 */     if (map != null)
/*      */     {
/* 2102 */       request.setAttribute("systeminfo", map);
/*      */     }
/*      */     
/* 2105 */     String applicationName = (String)((DynaActionForm)form).get("name");
/* 2106 */     ArrayList maxtimeresult = this.mo.getRows("select max(COLLECTIONTIME) from AM_ManagedObjectData where AM_ManagedObjectData.RESID=" + haid + "");
/* 2107 */     String maxtime = null;
/* 2108 */     if (maxtimeresult.size() > 0)
/*      */     {
/* 2110 */       ArrayList maxtimelist = (ArrayList)maxtimeresult.get(0);
/* 2111 */       maxtime = (String)maxtimelist.get(0);
/*      */     }
/* 2113 */     if (maxtime != null)
/*      */     {
/* 2115 */       ArrayList status = this.mo.getRows("select AVAILABILITY , HEALTH from AM_ManagedObjectData where AM_ManagedObjectData.RESID=" + haid + " and AM_ManagedObjectData.COLLECTIONTIME=" + maxtime + "");
/* 2116 */       request.setAttribute("status", status);
/*      */     }
/*      */     else {
/* 2119 */       request.setAttribute("status", new ArrayList());
/*      */     }
/*      */     
/* 2122 */     String oldavailquery = "select AM_ManagedResourceType.RESOURCEGROUP,count(AM_ManagedObject.RESOURCEID),count(Alert.SEVERITY) ,IFNULL(min(Alert.Severity),-1) from AM_ATTRIBUTES,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType left outer join Alert on Alert.CATEGORY=AM_ATTRIBUTES.ATTRIBUTEID and Alert.SOURCE=AM_ManagedObject.RESOURCEID and Alert.SEVERITY=1 where AM_PARENTCHILDMAPPER.PARENTID='" + haid + "'and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and ( AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.Type) and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTE='Availability' group by RESOURCEGROUP";
/*      */     
/*      */ 
/* 2125 */     String availquery = "select AM_ManagedResourceType.RESOURCEGROUP,count(AM_ManagedObject.RESOURCEID),count(Alert.SEVERITY) ,COALESCE(min(Alert.Severity),-1) from AM_ATTRIBUTES join AM_PARENTCHILDMAPPER join AM_ManagedObject join AM_ManagedResourceType left outer join Alert on Alert.CATEGORY=AM_ATTRIBUTES.ATTRIBUTEID and Alert.SOURCE=AM_ManagedObject.RESOURCEID and Alert.SEVERITY=1 where AM_PARENTCHILDMAPPER.PARENTID='" + haid + "'and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and ( AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.Type) and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTE='Availability' group by RESOURCEGROUP";
/*      */     
/*      */ 
/* 2128 */     FormatUtil.printQueryChange("ShowApplication.java", oldavailquery, availquery);
/*      */     
/* 2130 */     String oldhealthquery = "select AM_ManagedResourceType.RESOURCEGROUP,count(AM_ManagedObject.RESOURCEID),count(Alert.SEVERITY) ,IFNULL(min(Alert.Severity),-1) from AM_ATTRIBUTES,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType left outer join Alert on Alert.CATEGORY=AM_ATTRIBUTES.ATTRIBUTEID and Alert.SOURCE=AM_ManagedObject.RESOURCEID and (Alert.SEVERITY=1 OR Alert.SEVERITY=4) where AM_PARENTCHILDMAPPER.PARENTID='" + haid + "' and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and ( AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.Type) and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTE='Health' group by RESOURCEGROUP";
/*      */     
/*      */ 
/* 2133 */     String healthquery = "select AM_ManagedResourceType.RESOURCEGROUP,count(AM_ManagedObject.RESOURCEID),count(Alert.SEVERITY) ,COALESCE(min(Alert.Severity),-1) from AM_ATTRIBUTES join AM_PARENTCHILDMAPPER join AM_ManagedObject join AM_ManagedResourceType left outer join Alert on Alert.CATEGORY=AM_ATTRIBUTES.ATTRIBUTEID and Alert.SOURCE=AM_ManagedObject.RESOURCEID and (Alert.SEVERITY=1 OR Alert.SEVERITY=4) where AM_PARENTCHILDMAPPER.PARENTID='" + haid + "' and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and ( AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.Type) and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTE='Health' group by RESOURCEGROUP";
/*      */     
/*      */ 
/* 2136 */     FormatUtil.printQueryChange("ShowApplication.java", oldhealthquery, healthquery);
/*      */     
/* 2138 */     Hashtable availabilityHt = new Hashtable();
/* 2139 */     ArrayList availabilityRows = this.mo.getRows(availquery);
/* 2140 */     ArrayList healthRows = this.mo.getRows(healthquery);
/* 2141 */     Properties monitorscount = new Properties();
/* 2142 */     for (int i = 0; i < availabilityRows.size(); i++)
/*      */     {
/* 2144 */       ArrayList avail = (ArrayList)availabilityRows.get(i);
/* 2145 */       Properties p = new Properties();
/* 2146 */       p.setProperty("Total", (String)avail.get(1));
/* 2147 */       p.setProperty("NotAvailable", (String)avail.get(2));
/* 2148 */       p.setProperty("AvailStatus", (String)avail.get(3));
/* 2149 */       availabilityHt.put(avail.get(0), p);
/* 2150 */       monitorscount.setProperty((String)avail.get(0), (String)avail.get(1));
/*      */     }
/* 2152 */     for (int i = 0; i < healthRows.size(); i++)
/*      */     {
/* 2154 */       ArrayList health = (ArrayList)healthRows.get(i);
/* 2155 */       Properties p = (Properties)availabilityHt.get(health.get(0));
/* 2156 */       p.setProperty("HealthDown", (String)health.get(2));
/* 2157 */       p.setProperty("HealthStatus", (String)health.get(3));
/* 2158 */       availabilityHt.put(health.get(0), p);
/*      */     }
/* 2160 */     request.setAttribute("MonitorsStatus", availabilityHt);
/* 2161 */     request.setAttribute("MonitorsCount", monitorscount);
/* 2162 */     String oldalertquery = "select severity, modtime, mmessage, IF(AM_ManagedObject.displayname is NULL,source,AM_ManagedObject.displayname) as RESOURCENAME , SOURCE , CATEGORY from Alert,AM_PARENTCHILDMAPPER left outer join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source where source=childid and parentid= " + haid + " order by MODTIME desc limit 5";
/*      */     
/*      */ 
/* 2165 */     String alertquery = "select severity, modtime, mmessage, (CASE when AM_ManagedObject.displayname is NULL then cast(source as char) else AM_ManagedObject.displayname end) as RESOURCENAME , SOURCE , CATEGORY from Alert inner  join AM_PARENTCHILDMAPPER on source=childid and parentid=" + haid + " left outer join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source  order by MODTIME ";
/* 2166 */     alertquery = DBQueryUtil.getTopNValues(alertquery, 5);
/*      */     
/*      */ 
/* 2169 */     FormatUtil.printQueryChange("ShowApplication.java", oldalertquery, alertquery);
/* 2170 */     ArrayList alerts = this.mo.getRows(alertquery);
/* 2171 */     request.setAttribute("alerts", alerts);
/* 2172 */     return new ActionForward("/jsp/SnapshotView.jsp");
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
/*      */   public ActionForward showApplications(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2187 */     HttpSession session = request.getSession();
/* 2188 */     ServletContext ctx = session.getServletContext();
/* 2189 */     String haid = null;
/*      */     
/* 2191 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2192 */     ActionErrors errors = new ActionErrors();
/* 2193 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/* 2195 */       ArrayList masServerList = new ArrayList();
/* 2196 */       masServerList = this.mo.getRows("select * from AM_MAS_SERVER");
/* 2197 */       if (masServerList.size() == 0)
/*      */       {
/* 2199 */         request.setAttribute("noMASServer", "No Managed Server is Added.");
/*      */       }
/*      */     }
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
/* 2231 */     new ShowResourceDetails().showResourceTypes(mapping, form, request, response);
/* 2232 */     String userName = request.getRemoteUser();
/*      */     
/* 2234 */     ArrayList list = new ArrayList(5);
/* 2235 */     List mainlist = new ArrayList();
/* 2236 */     Hashtable parentlist = new Hashtable();
/* 2237 */     if ((request.isUserInRole("OPERATOR")) && (ctx.getAttribute("mgdrilldown") != null) && (ctx.getAttribute("mgdrilldown").equals("true")))
/*      */     {
/*      */ 
/* 2240 */       String qry = "SELECT AM_HOLISTICAPPLICATION.HAID,TYPE from  AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable,AM_HOLISTICAPPLICATION WHERE   AM_UserPasswordTable.USERNAME='" + userName + "' AND AM_UserPasswordTable.USERID= AM_HOLISTICAPPLICATION_OWNERS.OWNERID AND AM_HOLISTICAPPLICATION.HAID= AM_HOLISTICAPPLICATION_OWNERS.HAID";
/* 2241 */       if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 2242 */         String loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 2243 */         qry = "SELECT AM_HOLISTICAPPLICATION.HAID,TYPE from  AM_HOLISTICAPPLICATION_OWNERS,AM_HOLISTICAPPLICATION WHERE  AM_HOLISTICAPPLICATION_OWNERS.OWNERID=" + loginUserid + " AND AM_HOLISTICAPPLICATION.HAID= AM_HOLISTICAPPLICATION_OWNERS.HAID";
/*      */       }
/* 2245 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*      */       
/* 2247 */       String mainhaids = "(";
/* 2248 */       String childhaids = "(";
/*      */       
/*      */ 
/* 2251 */       while (rs.next())
/*      */       {
/* 2253 */         if (rs.getInt(2) == 0)
/*      */         {
/* 2255 */           mainhaids = mainhaids + "'" + rs.getString(1) + "',";
/* 2256 */           mainlist.add(rs.getString(1));
/*      */         }
/*      */         else
/*      */         {
/* 2260 */           childhaids = childhaids + "'" + rs.getString(1) + "',";
/*      */         }
/*      */       }
/*      */       
/* 2264 */       childhaids = childhaids.substring(0, childhaids.length() - 1) + ")";
/*      */       
/*      */ 
/*      */ 
/* 2268 */       if (childhaids.length() > 2)
/*      */       {
/* 2270 */         parentlist = DBUtil.getParentMGsforChildMGs(childhaids);
/* 2271 */         List rootlist = new ArrayList();
/* 2272 */         Set ks = parentlist.keySet();
/* 2273 */         Iterator it = ks.iterator();
/* 2274 */         while (it.hasNext()) {
/* 2275 */           String key = (String)it.next();
/* 2276 */           mainhaids = mainhaids + "'" + ((ArrayList)parentlist.get(key)).get(0) + "',";
/*      */         }
/*      */       }
/* 2279 */       if (mainhaids.length() > 2)
/*      */       {
/* 2281 */         mainhaids = mainhaids.substring(0, mainhaids.length() - 1) + ")";
/*      */       }
/*      */       
/* 2284 */       ArrayList templist = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,COALESCE(AM_UserPasswordTable.USERNAME,''),CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION left outer join AM_HOLISTICAPPLICATION_OWNERS  on  AM_HOLISTICAPPLICATION_OWNERS.HAID= AM_HOLISTICAPPLICATION.HAID left outer join AM_UserPasswordTable on AM_UserPasswordTable.userid=AM_HOLISTICAPPLICATION_OWNERS.ownerid where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  AND  AM_HOLISTICAPPLICATION.HAID in " + mainhaids + " order by RESOURCENAME");
/* 2285 */       ArrayList listofHAID = new ArrayList(5);
/* 2286 */       for (int i = 0; i < templist.size(); i++)
/*      */       {
/* 2288 */         ArrayList oneRow = (ArrayList)templist.get(i);
/* 2289 */         String own = (String)oneRow.get(3);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2294 */         Object tempHaid = oneRow.get(6);
/* 2295 */         if (listofHAID.indexOf(tempHaid) == -1)
/*      */         {
/* 2297 */           listofHAID.add(tempHaid);
/* 2298 */           list.add(oneRow);
/*      */         }
/*      */       }
/* 2301 */       closeResultSet(rs);
/*      */     }
/* 2303 */     else if (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request)) {
/* 2304 */       String qry = "select AM_HOLISTICAPPLICATION.HAID,TYPE from AM_UserPasswordTable,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS where AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID AND  AM_UserPasswordTable.USERNAME='" + userName + "' AND USERID=OWNERID ORDER BY HAID";
/* 2305 */       if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 2306 */         String loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 2307 */         qry = "select AM_HOLISTICAPPLICATION.HAID,TYPE from AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS where AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID AND OWNERID=" + loginUserid + " ORDER BY HAID";
/*      */       }
/*      */       
/* 2310 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry);
/* 2311 */       String sg = "(";
/* 2312 */       List list1 = new ArrayList();
/* 2313 */       while (rs1.next()) {
/* 2314 */         list1.add(rs1.getString(1));
/* 2315 */         if (rs1.getInt(2) == 1) {
/* 2316 */           sg = sg + "'" + rs1.getString(1) + "',";
/*      */         }
/*      */       }
/* 2319 */       System.out.println("The user list===>" + list1);
/* 2320 */       if (sg.length() > 2)
/*      */       {
/* 2322 */         sg = sg.substring(0, sg.length() - 1) + ")";
/* 2323 */         Hashtable sglist = DBUtil.getParentMGsforChildMGs(sg);
/* 2324 */         System.out.println("The sglist===>" + sglist);
/* 2325 */         Set s1 = sglist.keySet();
/* 2326 */         Iterator it = s1.iterator();
/* 2327 */         String key; while (it.hasNext()) {
/* 2328 */           key = (String)it.next();
/* 2329 */           List l1 = (ArrayList)sglist.get(key);
/* 2330 */           for (Object temp : l1)
/*      */           {
/* 2332 */             if (list1.contains(temp))
/*      */             {
/*      */ 
/* 2335 */               list1.remove(key);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 2340 */       String haids = "(";
/* 2341 */       if ((list1 != null) && (list1.size() > 0)) {
/* 2342 */         for (int i = 0; i < list1.size(); i++) {
/* 2343 */           haids = haids + "'" + list1.get(i) + "',";
/*      */         }
/* 2345 */         if (haids.length() > 2) {
/* 2346 */           haids = haids.substring(0, haids.length() - 1) + ")";
/* 2347 */           System.out.println("The haid query===>select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID AND HAID in " + haids + " order by RESOURCENAME");
/* 2348 */           list = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  AND HAID in " + haids + " order by RESOURCENAME");
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 2354 */       list = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.TYPE=0  order by RESOURCENAME");
/*      */     }
/* 2356 */     request.setAttribute("mainlist", mainlist);
/* 2357 */     request.setAttribute("applications", list);
/* 2358 */     request.setAttribute("allapplications", parentlist);
/*      */     
/*      */ 
/* 2361 */     if (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request))
/*      */     {
/* 2363 */       int limit = 5;
/* 2364 */       ArrayList alertList = AlarmUtil.getRecentAlertsForOwner(request, limit);
/* 2365 */       if (alertList.size() != 0)
/*      */       {
/* 2367 */         request.setAttribute("recent5Alarms", alertList);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2374 */       ArrayList alertList = AlarmUtil.getRecent5Alerts();
/* 2375 */       if (alertList.size() != 0)
/*      */       {
/* 2377 */         request.setAttribute("recent5Alarms", alertList);
/*      */       }
/*      */     }
/*      */     
/* 2381 */     request.setAttribute("reloadperiod", "300");
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 2386 */       Properties licenseinfo = ClientDBUtil.getLicenseInfo();
/* 2387 */       String usertype = licenseinfo.getProperty("licensetype");
/* 2388 */       int numberOfUsers = 1;
/* 2389 */       boolean registered_unlimited_users = false;
/* 2390 */       if (licenseinfo.getProperty("numberofclients").equalsIgnoreCase("unlimited"))
/*      */       {
/* 2392 */         registered_unlimited_users = true;
/*      */       }
/*      */       else
/*      */       {
/* 2396 */         numberOfUsers = Integer.parseInt(licenseinfo.getProperty("numberofclients"));
/*      */       }
/* 2398 */       if ((usertype.equals("R")) && (!registered_unlimited_users) && (!OEMUtil.isOEM()))
/*      */       {
/*      */         try
/*      */         {
/* 2402 */           ArrayList rows = this.mo.getRows("select USERID from AM_UserPasswordTable where AM_UserPasswordTable.USERNAME NOT IN ('reportadmin','systemadmin_enterprise')");
/* 2403 */           if (rows.size() > numberOfUsers)
/*      */           {
/* 2405 */             System.out.println(" License Check Failed : The number of users allowed for this Registered License is " + numberOfUsers + ". But currently there are " + rows.size() + " users, so kindly delete the other users.");
/* 2406 */             String m1 = FormatUtil.getString("user.present.creation.exceedslimit.licensed", new String[] { String.valueOf(numberOfUsers), String.valueOf(rows.size()), OEMUtil.getOEMString("product.sales.mailid"), OEMUtil.getOEMString("product.talkback.mailid") });
/* 2407 */             errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(m1));
/*      */           }
/* 2409 */           saveErrors(request, errors);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 2413 */           ex.printStackTrace();
/*      */         }
/*      */         
/* 2416 */       } else if (usertype.equals("F"))
/*      */       {
/*      */         try
/*      */         {
/* 2420 */           ArrayList rows = this.mo.getRows("select USERID from AM_UserPasswordTable where AM_UserPasswordTable.USERNAME NOT IN ('reportadmin','systemadmin_enterprise')");
/* 2421 */           if (rows.size() > numberOfUsers)
/*      */           {
/* 2423 */             System.out.println(" License Check Failed : The number of users allowed for this Free License is " + numberOfUsers + ". But currently there are " + rows.size() + " users, so kindly delete the other users.");
/* 2424 */             errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("user.present.creation.exceedslimit", String.valueOf(numberOfUsers), String.valueOf(rows.size())));
/*      */           }
/* 2426 */           saveErrors(request, errors);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 2430 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2436 */       e.printStackTrace();
/*      */     }
/* 2438 */     if (request.isUserInRole("MANAGER"))
/*      */     {
/* 2440 */       return new ActionForward("/showBussiness.do?method=generateApplicationAvailablity");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2445 */     return new ActionForward("/jsp/applications.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showMonitorGroups(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2455 */     new ShowResourceDetails().showResourceTypes(mapping, form, request, response);
/* 2456 */     String userName = request.getRemoteUser();
/*      */     
/* 2458 */     ArrayList list = new ArrayList(5);
/* 2459 */     if (request.isUserInRole("OPERATOR"))
/*      */     {
/* 2461 */       ArrayList templist = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,AM_UserPasswordTable.USERNAME,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_HOLISTICAPPLICATION.TYPE=0 order by RESOURCENAME");
/* 2462 */       ArrayList listofHAID = new ArrayList(5);
/* 2463 */       for (int i = 0; i < templist.size(); i++)
/*      */       {
/* 2465 */         ArrayList oneRow = (ArrayList)templist.get(i);
/* 2466 */         String own = (String)oneRow.get(3);
/* 2467 */         if (own.equals(userName))
/*      */         {
/*      */ 
/*      */ 
/* 2471 */           Object tempHaid = oneRow.get(6);
/* 2472 */           if (listofHAID.indexOf(tempHaid) == -1)
/*      */           {
/* 2474 */             listofHAID.add(tempHaid);
/* 2475 */             list.add(oneRow);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 2481 */       list = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.TYPE=0  order by RESOURCENAME");
/*      */     }
/* 2483 */     request.setAttribute("applications", list);
/* 2484 */     if ((request.getParameter("showBS") != null) && (request.getParameter("showBS").equalsIgnoreCase("true")))
/*      */     {
/* 2486 */       return new ActionForward("/it360/jsp/ShowBusinessServices.jsp");
/*      */     }
/*      */     
/* 2489 */     return new ActionForward("/jsp/ShowGroups.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showRecentAlerts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2498 */     ArrayList alertList = AlarmUtil.getRecent5Alerts();
/* 2499 */     if (alertList.size() != 0)
/*      */     {
/* 2501 */       request.setAttribute("recent5Alarms", alertList);
/*      */     }
/*      */     
/* 2504 */     return new ActionForward("/jsp/Recent5Alarms.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward showRecentTenAlerts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2512 */     ArrayList alertList = AlarmUtil.getRecentTenAlerts();
/* 2513 */     if (alertList.size() != 0)
/*      */     {
/* 2515 */       request.setAttribute("recent5Alarms", alertList);
/*      */     }
/*      */     
/* 2518 */     return new ActionForward("/jsp/Recent5Alarms.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ManagedApplication mo;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward configureServiceParams(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2534 */     String pollInterval = request.getParameter("pollinterval");
/* 2535 */     String displayName = request.getParameter("displayname");
/* 2536 */     String resourceName = request.getParameter("resourcename");
/* 2537 */     String resourceID = request.getParameter("resourceid");
/* 2538 */     String type = request.getParameter("type");
/* 2539 */     String redirect = request.getParameter("redirectto");
/* 2540 */     ActionErrors errors = new ActionErrors();
/*      */     
/* 2542 */     String authEnabled = request.getParameter("authEnabled");
/* 2543 */     String username = request.getParameter("username");
/* 2544 */     String password = request.getParameter("password");
/* 2545 */     String jndipath = request.getParameter("jndipath");
/* 2546 */     String jmxurl = request.getParameter("jmxurl");
/* 2547 */     String port = request.getParameter("port");
/*      */     try
/*      */     {
/* 2550 */       pollInterval = Long.parseLong(pollInterval) * 60L + "";
/*      */       
/* 2552 */       String qry = "UPDATE AM_ManagedObject set DISPLAYNAME ='" + displayName + "' where RESOURCEID = " + resourceID;
/*      */       
/* 2554 */       this.mo.executeUpdateStmt(qry);
/* 2555 */       EnterpriseUtil.addUpdateQueryToFile(qry);
/* 2556 */       qry = "UPDATE ManagedObject set DISPLAYNAME ='" + displayName + "', POLLINTERVAL = " + pollInterval + " where NAME='" + resourceName + "'";
/* 2557 */       this.mo.executeUpdateStmt(qry);
/*      */       
/* 2559 */       if ((authEnabled != null) && (username != null) && (password != null))
/*      */       {
/* 2561 */         String query = "select * from AM_RESOURCECONFIG where RESOURCEID=" + resourceID;
/* 2562 */         ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 2563 */         boolean insert = true;
/* 2564 */         if (rs.next())
/*      */         {
/* 2566 */           insert = false;
/*      */         }
/* 2568 */         rs.close();
/* 2569 */         if (insert) {
/* 2570 */           query = "insert into AM_RESOURCECONFIG (RESOURCEID,USERNAME,PASSWORD) values (" + resourceID + ",'" + username + "'," + DBQueryUtil.encodetoBytes(password) + ")";
/*      */         }
/*      */         else {
/* 2573 */           query = "update AM_RESOURCECONFIG set USERNAME='" + username + "',PASSWORD=" + DBQueryUtil.encodetoBytes(password) + " where RESOURCEID=" + resourceID;
/*      */         }
/* 2575 */         AMConnectionPool.executeUpdateStmt(query);
/*      */       }
/* 2577 */       if ("SNMP".equalsIgnoreCase(type))
/*      */       {
/* 2579 */         ResultSet snmpResultSet = null;
/*      */         try
/*      */         {
/* 2582 */           String snmpQuery = "select * from AM_SNMP_EXT_INFO where resourceid=" + resourceID;
/* 2583 */           int snmpTimeOut = 5;
/* 2584 */           snmpResultSet = AMConnectionPool.executeQueryStmt(snmpQuery);
/* 2585 */           String snmpVersion = request.getParameter("snmpVersion");
/* 2586 */           String queryToExecute = "";
/* 2587 */           boolean toUpdate = false;
/* 2588 */           if (snmpResultSet.next())
/*      */           {
/* 2590 */             toUpdate = true;
/*      */           }
/*      */           
/* 2593 */           if (!snmpVersion.equalsIgnoreCase("v3"))
/*      */           {
/* 2595 */             String encodedSNMPCommunityString = DBQueryUtil.encode(request.getParameter("snmpCommunityString"));
/*      */             
/* 2597 */             if (toUpdate)
/*      */             {
/* 2599 */               queryToExecute = "update AM_SNMP_EXT_INFO set COMMUNITYSTRING=" + encodedSNMPCommunityString + ", VERSION='V1V2',SNMPPORT=" + port + " where RESOURCEID=" + resourceID;
/*      */             }
/*      */             else
/*      */             {
/* 2603 */               queryToExecute = "insert into AM_SNMP_EXT_INFO values (" + resourceID + "," + encodedSNMPCommunityString + "," + snmpTimeOut + ",'V1V2'," + port + ",'','','','','','','','')";
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 2608 */             String snmpSecurityLevel = request.getParameter("snmpSecurityLevel");
/* 2609 */             String snmpUserName = request.getParameter("snmpUserName");
/* 2610 */             String snmpContextName = request.getParameter("snmpContextName");
/* 2611 */             String snmpAuthProtocol = request.getParameter("snmpAuthProtocol");
/* 2612 */             String encodedSNMPAuthPassword = DBQueryUtil.encode(request.getParameter("snmpAuthPassword"));
/* 2613 */             String encodedSNMPPrivPassword = DBQueryUtil.encode(request.getParameter("snmpPrivPassword"));
/* 2614 */             if (toUpdate)
/*      */             {
/* 2616 */               queryToExecute = "update AM_SNMP_EXT_INFO set VERSION='V3', SNMPPORT=" + port + ", USERNAME='" + snmpUserName + "',CONTEXTNAME='" + snmpContextName + "',SECURITYLEVEL='" + snmpSecurityLevel + "',AUTHPROTOCOL='" + snmpAuthProtocol + "',AUTHPASSWORD=" + encodedSNMPAuthPassword + ",PRIVPROTOCOL='DES',PRIVPASSWORD=" + encodedSNMPPrivPassword + " where RESOURCEID=" + resourceID;
/*      */             }
/*      */             else
/*      */             {
/* 2620 */               queryToExecute = "insert into AM_SNMP_EXT_INFO values (" + resourceID + ",''," + snmpTimeOut + ",V3," + port + ",'" + snmpUserName + "','" + snmpContextName + "','" + snmpSecurityLevel + "','" + snmpAuthProtocol + "'," + encodedSNMPAuthPassword + ",'DES'," + encodedSNMPPrivPassword + ")";
/*      */             }
/*      */           }
/* 2623 */           AMLog.debug("Query To be Executed in edit monitor ===>" + queryToExecute);
/* 2624 */           AMConnectionPool.executeUpdateStmt(queryToExecute);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 2628 */           ex.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/* 2632 */           if (snmpResultSet != null)
/*      */           {
/* 2634 */             AMConnectionPool.closeStatement(snmpResultSet);
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/* 2640 */       else if (type.equalsIgnoreCase("JMX1.2-MX4J-RMI")) {
/* 2641 */         ResultSet rs = null;
/*      */         try {
/* 2643 */           String query = "SELECT * FROM AM_MX4J_EXT_INFO where RESOURCEID =" + resourceID;
/* 2644 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 2645 */           boolean insert = true;
/* 2646 */           if (rs.next()) {
/* 2647 */             insert = false;
/*      */           }
/* 2649 */           if (((jndipath != null) && (!"".equalsIgnoreCase(jndipath)) && (!"null".equalsIgnoreCase(jndipath))) || ((jmxurl != null) && (!"".equalsIgnoreCase(jmxurl)) && (!"null".equalsIgnoreCase(jmxurl)))) {
/* 2650 */             query = "update AM_MX4J_EXT_INFO set JNDIURL='" + jndipath + "',JMXURL='" + jmxurl + "' where RESOURCEID=" + resourceID;
/* 2651 */             AMConnectionPool.executeUpdateStmt(query);
/* 2652 */           } else if ("".equalsIgnoreCase(jmxurl)) {
/* 2653 */             AMConnectionPool.executeUpdateStmt("update AM_MX4J_EXT_INFO set JMXURL='' where RESOURCEID=" + resourceID);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2658 */           if (rs != null) {
/*      */             try {
/* 2660 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception e) {}
/*      */           }
/*      */           try
/*      */           {
/* 2666 */             if (port != null) {
/* 2667 */               String query = "update InetService set PORTNO=" + port + " where NAME='" + resourceName + "'";
/* 2668 */               AMConnectionPool.executeUpdateStmt(query);
/*      */             }
/*      */           } catch (Exception ep) {
/* 2671 */             ep.printStackTrace();
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 2656 */           ex.printStackTrace();
/*      */         } finally {
/* 2658 */           if (rs != null) {
/*      */             try {
/* 2660 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception e) {}
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2674 */       if ((redirect == null) || (redirect.length() == 0)) {
/* 2675 */         redirect = "/showresource.do?resourceid=" + resourceID + "&type=" + type + "&moname=" + resourceName + "&method=showdetails&resourcename=" + displayName;
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException e) {
/* 2679 */       redirect = "/jsp/configure_resource.jsp?resid=" + resourceID;
/* 2680 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Invalid poll interval " + pollInterval));
/* 2681 */       saveErrors(request, errors);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2685 */       redirect = "/jsp/configure_resource.jsp?resid=" + resourceID;
/* 2686 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Error occured while trying to update information. Please try again."));
/* 2687 */       saveErrors(request, errors);
/*      */     }
/*      */     
/* 2690 */     return new ActionForward(redirect);
/*      */   }
/*      */   
/*      */   public void putFlashProps(String haid, HttpServletRequest request) throws Exception
/*      */   {
/* 2695 */     boolean isHtml = request.getParameter("isHtml") != null ? Boolean.valueOf(request.getParameter("isHtml")).booleanValue() : true;
/* 2696 */     Vector parentMos = new Vector();
/* 2697 */     this.mo.getParentMGs(parentMos, haid + "");
/* 2698 */     request.setAttribute("ParentMos", parentMos);
/* 2699 */     Hashtable resnameTable = new Hashtable();
/*      */     try {
/* 2701 */       String resourcenamequery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", parentMos);
/* 2702 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(resourcenamequery);
/*      */       
/* 2704 */       while (rs.next())
/*      */       {
/* 2706 */         String resid = rs.getString("RESOURCEID");
/* 2707 */         String resname = EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME"));
/* 2708 */         resnameTable.put(resid, resname);
/*      */       }
/* 2710 */       rs.close();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2715 */       ex.printStackTrace();
/*      */     }
/* 2717 */     request.setAttribute("resnameTable", resnameTable);
/* 2718 */     FlashForm fm = new FlashForm();
/* 2719 */     String viewid = "-1";
/* 2720 */     String selectedview = "-1";
/* 2721 */     ArrayList availableViews = new ArrayList();
/*      */     try {
/* 2723 */       String userid = this.mo.getUserID(request);
/* 2724 */       ManagedApplication mo = new ManagedApplication();
/* 2725 */       if (request.getParameter("viewid") != null)
/*      */       {
/* 2727 */         viewid = request.getParameter("viewid");
/*      */       }
/* 2729 */       String query1 = "";
/* 2730 */       if (viewid.equals("-1"))
/*      */       {
/* 2732 */         query1 = "select VIEWID,ZOOMLEVEL,SELECTED,DISPLAYNAME,DESCRIPTION,LINECOLOR,LINETHICKNESS,LABELCOLOR,BGCOLOR,STATUSUPDATEINTERVAL,RELOADINTERVAL,SHOWLABEL,LINETRANSPARENCY,SHOWCRITICALMONITORS,SHOWONLYSUBGROUPS,SHOWTOPLEVELMGS,NOOFCOLUMNS,SHOWTOPLEVELSUBMGS,XCANVAS,YCANVAS,ISHTML from AM_MONITORGROUP_FLASHVIEWCONFIG where HAID=" + haid + " and USERID=" + userid + " and SELECTED=1 ";
/*      */       }
/*      */       else
/*      */       {
/* 2736 */         query1 = "select VIEWID,ZOOMLEVEL,SELECTED,DISPLAYNAME,DESCRIPTION,LINECOLOR,LINETHICKNESS,LABELCOLOR,BGCOLOR,STATUSUPDATEINTERVAL,RELOADINTERVAL,SHOWLABEL,LINETRANSPARENCY,SHOWCRITICALMONITORS,SHOWONLYSUBGROUPS,SHOWTOPLEVELMGS,NOOFCOLUMNS,SHOWTOPLEVELSUBMGS,XCANVAS,YCANVAS,ISHTML from AM_MONITORGROUP_FLASHVIEWCONFIG where viewid=" + viewid;
/*      */       }
/* 2738 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/* 2739 */       if (rs.next())
/*      */       {
/* 2741 */         viewid = rs.getString("VIEWID");
/* 2742 */         isHtml = Boolean.valueOf(rs.getString("ISHTML")).booleanValue();
/* 2743 */         AMLog.debug("Inside putFlashProps" + isHtml);
/* 2744 */         selectedview = viewid;
/* 2745 */         fm.setXcanvas(rs.getFloat("XCANVAS"));
/* 2746 */         fm.setYcanvas(rs.getFloat("YCANVAS"));
/* 2747 */         fm.setDisplayName(rs.getString("DISPLAYNAME"));
/*      */         
/* 2749 */         fm.setBgColor(rs.getString("BGCOLOR"));
/* 2750 */         fm.setLineColor(rs.getString("LINECOLOR").replaceFirst("#", "0x"));
/* 2751 */         fm.setLabelColor(rs.getString("LABELCOLOR").replaceFirst("#", "0x"));
/*      */         
/*      */ 
/* 2754 */         fm.setLinethickness(rs.getFloat("LINETHICKNESS"));
/* 2755 */         fm.setLineTransparency(rs.getFloat("LINETRANSPARENCY"));
/* 2756 */         fm.setReloadInterval(rs.getInt("RELOADINTERVAL"));
/* 2757 */         fm.setRefreshInterval(rs.getInt("STATUSUPDATEINTERVAL") * 1000);
/* 2758 */         fm.setShowCriticalMonitors(Boolean.valueOf(rs.getString("SHOWCRITICALMONITORS")).booleanValue());
/* 2759 */         fm.setShowOnlySubgroups(Boolean.valueOf(rs.getString("SHOWONLYSUBGROUPS")).booleanValue());
/* 2760 */         fm.setShowTopLevelMgs(Boolean.valueOf(rs.getString("SHOWTOPLEVELMGS")).booleanValue());
/* 2761 */         fm.setNoOfColumns(rs.getInt("NOOFCOLUMNS"));
/* 2762 */         fm.setShowTopLevelSubMgs(Boolean.valueOf(rs.getString("SHOWTOPLEVELSUBMGS")).booleanValue());
/* 2763 */         request.setAttribute("customreloadperiod", rs.getString("RELOADINTERVAL"));
/* 2764 */         fm.setIsHtml(isHtml);
/*      */         
/* 2766 */         AMLog.debug("Position of Canvas:" + fm.getXcanvas() + " " + fm.getYcanvas());
/* 2767 */         Properties dataProps = new Properties();
/* 2768 */         dataProps.setProperty("label", fm.getDisplayName());
/* 2769 */         dataProps.setProperty("value", viewid);
/* 2770 */         availableViews.add(dataProps);
/* 2771 */         if (isHtml) {
/* 2772 */           fm.setLineColor(rs.getString("LINECOLOR"));
/* 2773 */           fm.setLabelColor(rs.getString("LABELCOLOR"));
/*      */         } else {
/* 2775 */           fm.setLabelColor(rs.getString("LABELCOLOR").replaceFirst("#", "0x"));
/* 2776 */           fm.setLineColor(rs.getString("LINECOLOR").replaceFirst("#", "0x"));
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2781 */         fm.setReloadInterval(fm.getReloadInterval() * 60);
/* 2782 */         fm.setRefreshInterval(fm.getRefreshInterval() * 60 * 1000);
/* 2783 */         if (isHtml) {
/* 2784 */           fm.setLineColor("#ECECEC");
/* 2785 */           fm.setLabelColor("#444444");
/* 2786 */           fm.setLinethickness(2.0F);
/* 2787 */           fm.setLineTransparency(1.0F);
/*      */         } else {
/* 2789 */           fm.setLineColor("#000000");
/*      */         }
/*      */       }
/*      */       
/* 2793 */       rs.close();
/*      */       
/* 2795 */       String relatedviewsquery = " select AM_MONITORGROUP_FLASHVIEWCONFIG.VIEWID,AM_MONITORGROUP_FLASHVIEWCONFIG.DISPLAYNAME from AM_FLASHVIEW_FILTER,AM_MONITORGROUP_FLASHVIEWCONFIG where AM_FLASHVIEW_FILTER.HAID=" + haid + " and AM_MONITORGROUP_FLASHVIEWCONFIG.USERID=" + userid + " and AM_FLASHVIEW_FILTER.VIEWID=AM_MONITORGROUP_FLASHVIEWCONFIG.VIEWID and AM_FLASHVIEW_FILTER.VIEWID not in(" + selectedview + ") order by DISPLAYNAME";
/* 2796 */       AMConnectionPool.getInstance();ResultSet rs1 = AMConnectionPool.executeQueryStmt(relatedviewsquery);
/* 2797 */       while (rs1.next())
/*      */       {
/* 2799 */         Properties dataProps = new Properties();
/* 2800 */         dataProps.setProperty("label", rs1.getString("DISPLAYNAME"));
/* 2801 */         dataProps.setProperty("value", rs1.getString("VIEWID"));
/* 2802 */         availableViews.add(dataProps);
/*      */       }
/* 2804 */       rs1.close();
/* 2805 */       fm.setSelectedView(selectedview);
/* 2806 */       fm.setAvailableViews(availableViews);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2810 */       request.setAttribute("customreloadperiod", "300");
/* 2811 */       ex.printStackTrace();
/*      */     }
/* 2813 */     request.setAttribute("FlashForm", fm);
/* 2814 */     request.setAttribute("viewid", viewid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showSubGroupTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 2827 */       String haid = request.getParameter("haid");
/* 2828 */       ArrayList filteredHaids = new ArrayList();
/* 2829 */       ArrayList haids = new ArrayList();
/* 2830 */       ArrayList tempList = new ArrayList();
/* 2831 */       boolean isUserResourceEnabled = false;
/* 2832 */       String loginUserid = null;
/* 2833 */       if (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request))
/*      */       {
/* 2835 */         Vector mgsForOperator = new Vector();
/* 2836 */         if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 2837 */           isUserResourceEnabled = true;
/* 2838 */           loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/*      */         } else {
/* 2840 */           mgsForOperator = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*      */         }
/*      */         
/* 2843 */         haids.addAll(mgsForOperator);
/* 2844 */         haids.add(haid);
/* 2845 */         tempList.addAll(mgsForOperator);
/*      */       }
/*      */       else
/*      */       {
/* 2849 */         tempList.add(haid);
/* 2850 */         ReportUtil.getLastLevelSubGroup(tempList, haid);
/* 2851 */         haids.addAll(tempList);
/*      */       }
/*      */       
/* 2854 */       filteredHaids.addAll(tempList);
/* 2855 */       Vector mgsForOperator = null;
/* 2856 */       ArrayList MonitorsinMGs = new ArrayList();
/* 2857 */       String query = null;
/* 2858 */       if (isUserResourceEnabled) {
/* 2859 */         query = "select Distinct A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE,child.DCSTARTED,AM_HOLISTICAPPLICATION.GROUPTYPE from AM_USERRESOURCESTABLE,AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and AM_USERRESOURCESTABLE.RESOURCEID=A1.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + "  order by A1.DISPLAYNAME,child.DISPLAYNAME";
/*      */       } else {
/* 2861 */         query = "select Distinct A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE,child.DCSTARTED,AM_HOLISTICAPPLICATION.GROUPTYPE from AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and " + ManagedApplication.getCondition("AM_HOLISTICAPPLICATION.HAID", haids) + "  order by A1.DISPLAYNAME,child.DISPLAYNAME";
/*      */       }
/* 2863 */       System.out.println("Query for Sub Group Tree--->" + query);
/* 2864 */       ArrayList list = this.mo.getRows(query);
/* 2865 */       ArrayList listofGroups = new ArrayList();
/* 2866 */       Hashtable childMOs = new Hashtable();
/* 2867 */       Hashtable childMOsforMG = new Hashtable();
/* 2868 */       HashMap<String, String[]> childMonitorInfo = new HashMap();
/*      */       
/* 2870 */       for (int j = 0; j < list.size(); j++)
/*      */       {
/* 2872 */         ArrayList singlerow = (ArrayList)list.get(j);
/* 2873 */         String resourcename = (String)singlerow.get(0);
/* 2874 */         String displayname = (String)singlerow.get(1);
/* 2875 */         String childactionstatus = (String)singlerow.get(2);
/* 2876 */         String owner = (String)singlerow.get(3);
/* 2877 */         String CREATIONDATE = (String)singlerow.get(4);
/* 2878 */         String MODIFIEDDATE = (String)singlerow.get(5);
/* 2879 */         String MGresourceid = (String)singlerow.get(6);
/* 2880 */         String unmanagednodes = (String)singlerow.get(7);
/* 2881 */         String childid = (String)singlerow.get(8);
/* 2882 */         String childname = (String)singlerow.get(9);
/* 2883 */         String childtype = (String)singlerow.get(10);
/* 2884 */         String MGactionstatus = (String)singlerow.get(11);
/* 2885 */         String imagepath = (String)singlerow.get(12);
/* 2886 */         String shortname = (String)singlerow.get(13);
/* 2887 */         String unmanageChildmos = (String)singlerow.get(14);
/* 2888 */         String MGType = (String)singlerow.get(15);
/* 2889 */         String dcstarted = (String)singlerow.get(16);
/* 2890 */         String haiGroupType = (String)singlerow.get(17);
/* 2891 */         if (childname != null)
/*      */         {
/* 2893 */           childname = EnterpriseUtil.decodeString(childname);
/* 2894 */           Vector childVec = new Vector();
/* 2895 */           childVec.add(childid);
/* 2896 */           if (ChildMOHandler.isChildMonitorTypeSupportedForMG(childtype))
/*      */           {
/* 2898 */             HashMap<String, HashMap<String, String>> map = ChildMOHandler.getChildMonitorWithParentInfo(childVec);
/* 2899 */             if (map != null)
/*      */             {
/* 2901 */               HashMap<String, String> childInfo = (HashMap)map.get(childid);
/* 2902 */               if (childInfo != null)
/*      */               {
/* 2904 */                 childname = (String)childInfo.get("displayname");
/* 2905 */                 imagepath = (String)childInfo.get("imagepath");
/* 2906 */                 String[] arr = new String[2];
/* 2907 */                 arr[0] = ((String)childInfo.get("parentResourceid"));
/* 2908 */                 arr[1] = ((String)childInfo.get("childDisplayname"));
/* 2909 */                 childMonitorInfo.put(childid, arr);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 2915 */         if (haid.equals(MGresourceid))
/*      */         {
/*      */ 
/* 2918 */           MGType = "0";
/*      */         }
/* 2920 */         if (haids.contains(MGresourceid))
/*      */         {
/*      */ 
/*      */ 
/* 2924 */           MonitorsinMGs.add(childid);
/* 2925 */           if ((childMOs.containsKey(MGresourceid)) || (childMOsforMG.containsKey(MGresourceid)))
/*      */           {
/* 2927 */             ArrayList childmo = null;
/* 2928 */             if (childtype != null)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 2933 */               if (childtype.equals("HAI"))
/*      */               {
/* 2935 */                 if (!haids.contains(childid)) {
/*      */                   continue;
/*      */                 }
/*      */                 
/* 2939 */                 if (childMOsforMG.get(MGresourceid) != null)
/*      */                 {
/* 2941 */                   childmo = (ArrayList)childMOsforMG.get(MGresourceid);
/*      */                 }
/*      */                 else
/*      */                 {
/* 2945 */                   childmo = new ArrayList();
/* 2946 */                   childMOsforMG.put(MGresourceid, childmo);
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/* 2951 */                 if (!filteredHaids.contains(MGresourceid)) {
/*      */                   continue;
/*      */                 }
/*      */                 
/* 2955 */                 if (childMOs.get(MGresourceid) != null)
/*      */                 {
/* 2957 */                   childmo = (ArrayList)childMOs.get(MGresourceid);
/*      */                 }
/*      */                 else
/*      */                 {
/* 2961 */                   childmo = new ArrayList();
/* 2962 */                   childMOs.put(MGresourceid, childmo);
/*      */                 }
/*      */               }
/*      */               
/* 2966 */               ArrayList singrow = new ArrayList();
/* 2967 */               if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childmo != null))
/*      */               {
/* 2969 */                 singrow.add(childid);
/* 2970 */                 singrow.add(childname);
/* 2971 */                 singrow.add(childtype);
/* 2972 */                 singrow.add(imagepath);
/* 2973 */                 singrow.add(shortname);
/* 2974 */                 singrow.add(unmanageChildmos);
/* 2975 */                 singrow.add(childactionstatus);
/* 2976 */                 if (!filteredHaids.contains(childid))
/*      */                 {
/* 2978 */                   singrow.add("disable");
/*      */                 }
/*      */                 else
/*      */                 {
/* 2982 */                   singrow.add("enable");
/*      */                 }
/* 2984 */                 singrow.add(dcstarted);
/* 2985 */                 singrow.add(haiGroupType);
/* 2986 */                 childmo.add(singrow);
/*      */               }
/*      */             }
/*      */           }
/*      */           else {
/* 2991 */             ArrayList childmo1 = new ArrayList();
/* 2992 */             ArrayList singrow = new ArrayList();
/*      */             
/* 2994 */             if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childtype != null))
/*      */             {
/* 2996 */               singrow.add(childid);
/* 2997 */               singrow.add(childname);
/* 2998 */               singrow.add(childtype);
/* 2999 */               singrow.add(imagepath);
/* 3000 */               singrow.add(shortname);
/* 3001 */               singrow.add(unmanageChildmos);
/* 3002 */               singrow.add(childactionstatus);
/* 3003 */               if (!filteredHaids.contains(childid))
/*      */               {
/* 3005 */                 singrow.add("disable");
/*      */               }
/*      */               else
/*      */               {
/* 3009 */                 singrow.add("enable");
/*      */               }
/*      */               
/* 3012 */               singrow.add(dcstarted);
/* 3013 */               singrow.add(haiGroupType);
/* 3014 */               childmo1.add(singrow);
/*      */               
/* 3016 */               if (childtype.equals("HAI"))
/*      */               {
/* 3018 */                 if (!haids.contains(childid))
/*      */                 {
/* 3020 */                   System.out.println("int to continue " + childid);
/* 3021 */                   continue;
/*      */                 }
/* 3023 */                 childMOsforMG.put(MGresourceid, childmo1);
/*      */               }
/*      */               else
/*      */               {
/* 3027 */                 if (!filteredHaids.contains(MGresourceid))
/*      */                 {
/* 3029 */                   System.out.println("in to continue " + MGresourceid);
/* 3030 */                   continue;
/*      */                 }
/*      */                 
/* 3033 */                 childMOs.put(MGresourceid, childmo1);
/*      */               }
/*      */               
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 3040 */               ArrayList dummylist = new ArrayList();
/* 3041 */               childMOs.put(MGresourceid, dummylist);
/*      */             }
/*      */             
/* 3044 */             ArrayList singlemonitorgroup = new ArrayList();
/* 3045 */             singlemonitorgroup.add(resourcename);
/* 3046 */             singlemonitorgroup.add(displayname);
/* 3047 */             singlemonitorgroup.add(MGType);
/* 3048 */             singlemonitorgroup.add(owner);
/* 3049 */             singlemonitorgroup.add(CREATIONDATE);
/* 3050 */             singlemonitorgroup.add(MODIFIEDDATE);
/* 3051 */             singlemonitorgroup.add(MGresourceid);
/* 3052 */             singlemonitorgroup.add("HAI");
/* 3053 */             singlemonitorgroup.add(unmanagednodes);
/* 3054 */             singlemonitorgroup.add(MGactionstatus);
/* 3055 */             if (filteredHaids.contains(MGresourceid))
/*      */             {
/* 3057 */               singlemonitorgroup.add("enable");
/*      */             }
/*      */             else
/*      */             {
/* 3061 */               singlemonitorgroup.add("disable");
/*      */             }
/* 3063 */             singlemonitorgroup.add(haiGroupType);
/* 3064 */             listofGroups.add(singlemonitorgroup);
/*      */           }
/*      */         } }
/* 3067 */       Hashtable childlist = new Hashtable();
/*      */       try
/*      */       {
/* 3070 */         for (int k = 0; k < listofGroups.size(); k++)
/*      */         {
/* 3072 */           ArrayList singlerow = (ArrayList)listofGroups.get(k);
/* 3073 */           String tempid = (String)singlerow.get(6);
/* 3074 */           ArrayList mosinOrder = new ArrayList();
/* 3075 */           System.out.println("Sub Groups are " + (ArrayList)childMOsforMG.get(tempid));
/* 3076 */           if (childMOsforMG.get(tempid) != null)
/*      */           {
/* 3078 */             mosinOrder = (ArrayList)childMOsforMG.get(tempid);
/*      */           }
/* 3080 */           if (childMOs.get(tempid) != null)
/*      */           {
/* 3082 */             ArrayList monitors = (ArrayList)childMOs.get(tempid);
/* 3083 */             for (int w = 0; w < monitors.size(); w++)
/*      */             {
/* 3085 */               mosinOrder.add(monitors.get(w));
/*      */             }
/*      */           }
/* 3088 */           if ((mosinOrder != null) && (mosinOrder.size() > 0))
/*      */           {
/* 3090 */             childlist.put(tempid, mosinOrder);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3097 */         ex.printStackTrace();
/*      */       }
/* 3099 */       request.setAttribute("ProcessAndServiceInfo", childMonitorInfo);
/* 3100 */       request.setAttribute("applications", listofGroups);
/* 3101 */       request.setAttribute("childlist", childlist);
/* 3102 */       request.setAttribute("defaultview", "showMonitorGroupView");
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 3109 */       ex.printStackTrace();
/*      */     }
/* 3111 */     request.setAttribute("treewithLayout", "false");
/* 3112 */     request.setAttribute("forInventory", "true");
/* 3113 */     return new ActionForward("/jsp/MonitorGroupViewInclude.jsp");
/*      */   }
/*      */   
/*      */   private HashMap getComponentDetails(String haid, HashMap flatListingProps)
/*      */   {
/* 3118 */     HashMap toreturn = new HashMap();
/* 3119 */     ArrayList<HashMap> listOfComponents = new ArrayList();
/* 3120 */     ArrayList<HashMap> listOfMonitors = new ArrayList();
/* 3121 */     ArrayList residlistForalert = new ArrayList();
/* 3122 */     HashMap componetToClusterMapping = new HashMap();
/*      */     
/* 3124 */     ArrayList entityListForAlert = new ArrayList();
/* 3125 */     Hashtable healthkeys = (Hashtable)com.adventnet.appmanager.util.Constants.getGlobalObject("healthkeys");
/* 3126 */     Hashtable availabilitykeys = (Hashtable)com.adventnet.appmanager.util.Constants.getGlobalObject("availabilitykeys");
/* 3127 */     String underscore = "_";
/* 3128 */     int nofofComponents = 0;
/* 3129 */     ResultSet rs = null;
/* 3130 */     Vector mgsForOperator = (Vector)flatListingProps.get("mgsForOperator");
/* 3131 */     String isoperator = (String)flatListingProps.get("isoperator");
/* 3132 */     String groupCondition = "";
/* 3133 */     if ("true".equals(isoperator))
/*      */     {
/* 3135 */       groupCondition = " and " + ManagedApplication.getCondition("mapper1.CHILDID", mgsForOperator);
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 3141 */       String componentsQuery = "select managed1.DISPLAYNAME,mapper1.CHILDID,webappgroup.GROUPTYPE,managed1.TYPE from AM_PARENTCHILDMAPPER as mapper1  left outer  join   AM_HOLISTICAPPLICATION as webappgroup on mapper1.CHILDID=webappgroup.HAID inner join AM_ManagedObject as managed1 on mapper1.CHILDID=managed1.RESOURCEID left outer join AM_MONITORGROUP_TYPE_ORDER on AM_MONITORGROUP_TYPE_ORDER.TYPEID=webappgroup.GROUPTYPE  where mapper1.PARENTID=" + haid + groupCondition + " order by  AM_MONITORGROUP_TYPE_ORDER.ORDERID, managed1.DISPLAYNAME";
/* 3142 */       AMConnectionPool con = new AMConnectionPool();
/* 3143 */       rs = AMConnectionPool.executeQueryStmt(componentsQuery);
/* 3144 */       while (rs.next())
/*      */       {
/* 3146 */         String childtype = rs.getString("TYPE");
/*      */         
/* 3148 */         residlistForalert.add(rs.getString("CHILDID"));
/* 3149 */         entityListForAlert.add(rs.getString("CHILDID") + underscore + availabilitykeys.get(childtype));
/* 3150 */         entityListForAlert.add(rs.getString("CHILDID") + underscore + healthkeys.get(childtype));
/*      */         
/* 3152 */         HashMap<String, String> eachComponent = new HashMap(4);
/* 3153 */         eachComponent.put("displayname", EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME")));
/* 3154 */         eachComponent.put("resourceid", rs.getString("CHILDID"));
/* 3155 */         eachComponent.put("resourcetype", rs.getString("TYPE"));
/* 3156 */         eachComponent.put("grouptype", rs.getString("GROUPTYPE"));
/*      */         
/* 3158 */         if (childtype.equals("HAI"))
/*      */         {
/* 3160 */           listOfComponents.add(eachComponent);
/* 3161 */           nofofComponents++;
/*      */         }
/*      */         else
/*      */         {
/* 3165 */           listOfMonitors.add(eachComponent);
/*      */         }
/*      */       }
/* 3168 */       rs.close();
/* 3169 */       for (int i = 0; i < listOfComponents.size(); i++)
/*      */       {
/* 3171 */         HashMap<String, String> eachComponent = (HashMap)listOfComponents.get(i);
/* 3172 */         String componentid = (String)eachComponent.get("resourceid");
/* 3173 */         String resourcetype = (String)eachComponent.get("resourcetype");
/* 3174 */         if ("HAI".equals(resourcetype))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 3179 */           String clustersQuery = "select webappgroup.HAID,mapper1.PARENTID, managed1.DISPLAYNAME,mapper1.CHILDID,webappgroup.GROUPTYPE,managed1.TYPE from AM_PARENTCHILDMAPPER as mapper1  inner join  AM_HOLISTICAPPLICATION as webappgroup on mapper1.PARENTID=webappgroup.HAID inner join AM_ManagedObject as managed1 on mapper1.CHILDID=managed1.RESOURCEID where webappgroup.HAID=" + componentid + " order by managed1.DISPLAYNAME, webappgroup.GROUPTYPE";
/* 3180 */           ResultSet rs1 = AMConnectionPool.executeQueryStmt(clustersQuery);
/* 3181 */           ArrayList<HashMap> childList = new ArrayList();
/*      */           try {
/* 3183 */             while (rs1.next())
/*      */             {
/* 3185 */               HashMap<String, String> singleCluster = new HashMap(4);
/* 3186 */               String childtype = rs1.getString("TYPE");
/* 3187 */               entityListForAlert.add(rs1.getString("CHILDID") + underscore + availabilitykeys.get(childtype));
/* 3188 */               entityListForAlert.add(rs1.getString("CHILDID") + underscore + healthkeys.get(childtype));
/*      */               
/* 3190 */               singleCluster.put("displayname", EnterpriseUtil.decodeString(rs1.getString("DISPLAYNAME")));
/* 3191 */               singleCluster.put("resourceid", rs1.getString("CHILDID"));
/* 3192 */               singleCluster.put("resourcetype", rs1.getString("TYPE"));
/* 3193 */               singleCluster.put("grouptype", rs1.getString("GROUPTYPE"));
/*      */               
/* 3195 */               childList.add(singleCluster);
/*      */             }
/*      */           }
/*      */           finally {
/* 3199 */             if (rs1 != null)
/*      */             {
/* 3201 */               rs1.close();
/*      */             }
/*      */           }
/* 3204 */           componetToClusterMapping.put(componentid, childList);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 3213 */         if (rs != null)
/*      */         {
/* 3215 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {}
/*      */       
/*      */       try
/*      */       {
/* 3222 */         Properties alerts = FaultUtil.getStatus(entityListForAlert, false);
/* 3223 */         toreturn.put("alerts", alerts);
/* 3224 */         CriticalPrioritizer criticalComparator = new CriticalPrioritizer(alerts, healthkeys);
/* 3225 */         for (int i = 0; i < listOfComponents.size(); i++)
/*      */         {
/* 3227 */           HashMap<String, String> eachComponent = (HashMap)listOfComponents.get(i);
/* 3228 */           String componentid = (String)eachComponent.get("resourceid");
/* 3229 */           ArrayList childList = (ArrayList)componetToClusterMapping.get(componentid);
/* 3230 */           if (childList != null)
/*      */           {
/* 3232 */             Collections.sort(childList, criticalComparator);
/*      */           }
/*      */         }
/* 3235 */         Collections.sort(listOfMonitors, criticalComparator);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3239 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3209 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 3213 */         if (rs != null)
/*      */         {
/* 3215 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
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
/* 3241 */     toreturn.put("componentlist", listOfComponents);
/* 3242 */     toreturn.put("monitorslist", listOfMonitors);
/* 3243 */     toreturn.put("nofofComponents", Integer.valueOf(nofofComponents));
/* 3244 */     Vector<String> childList = new Vector();
/*      */     
/* 3246 */     Iterator itr = componetToClusterMapping.keySet().iterator();
/* 3247 */     while (itr.hasNext())
/*      */     {
/* 3249 */       String key = (String)itr.next();
/* 3250 */       ArrayList list = (ArrayList)componetToClusterMapping.get(key);
/* 3251 */       if (list != null)
/*      */       {
/* 3253 */         int size = list.size();
/* 3254 */         for (int i = 0; i < size; i++)
/*      */         {
/* 3256 */           HashMap<String, String> prop = (HashMap)list.get(i);
/* 3257 */           String type = (String)prop.get("resourcetype");
/* 3258 */           if (ChildMOHandler.isChildMonitorTypeSupportedForMG(type))
/*      */           {
/* 3260 */             String resId = (String)prop.get("resourceid");
/* 3261 */             childList.add(resId);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 3266 */     for (HashMap<String, String> monitorInfo : listOfMonitors)
/*      */     {
/* 3268 */       String resourceType = (String)monitorInfo.get("resourcetype");
/* 3269 */       if (ChildMOHandler.isChildMonitorTypeSupportedForMG(resourceType))
/*      */       {
/* 3271 */         String resId = (String)monitorInfo.get("resourceid");
/* 3272 */         childList.add(resId);
/*      */       }
/*      */     }
/* 3275 */     HashMap<String, HashMap<String, String>> childMonitorInfo = ChildMOHandler.getChildMonitorWithParentInfo(childList);
/* 3276 */     AMLog.debug("childMonitorInfo:" + childMonitorInfo + "\tchildList:" + childList);
/* 3277 */     HashMap processHostInfo = new HashMap();
/* 3278 */     JSONObject obj = new JSONObject();
/* 3279 */     itr = childMonitorInfo.keySet().iterator();
/* 3280 */     while (itr.hasNext())
/*      */     {
/* 3282 */       String resId = (String)itr.next();
/* 3283 */       HashMap<String, String> monitorInfo = (HashMap)childMonitorInfo.get(resId);
/* 3284 */       if (monitorInfo != null)
/*      */       {
/* 3286 */         String[] arr = new String[4];
/* 3287 */         arr[0] = ((String)monitorInfo.get("displayname"));
/* 3288 */         arr[1] = ((String)monitorInfo.get("trimmeddisplayname"));
/* 3289 */         arr[2] = ((String)monitorInfo.get("parentResourceid"));
/* 3290 */         arr[3] = ((String)monitorInfo.get("childDisplayname"));
/* 3291 */         JSONArray jsArr = new JSONArray();
/* 3292 */         jsArr.put(arr[0]);
/* 3293 */         jsArr.put(arr[1]);
/* 3294 */         jsArr.put(arr[2]);
/* 3295 */         jsArr.put(arr[3]);
/*      */         try
/*      */         {
/* 3298 */           obj.put(resId, arr);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3302 */           e.printStackTrace();
/*      */         }
/* 3304 */         processHostInfo.put(resId, arr);
/*      */       }
/*      */     }
/* 3307 */     toreturn.put("childAndParentInfo", processHostInfo);
/* 3308 */     toreturn.put("childAndParentInfoInJSON", obj);
/* 3309 */     toreturn.put("childlistmapping", componetToClusterMapping);
/* 3310 */     return toreturn;
/*      */   }
/*      */   
/*      */   public ActionForward showWebApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/* 3316 */       String haid = request.getParameter("haid");
/* 3317 */       if (ClientDBUtil.isPrivilegedUser(request)) {
/* 3318 */         String role = request.getRemoteUser();
/* 3319 */         boolean mgviewAllowed = checkForMGAssignedtoUser(haid, request);
/* 3320 */         if (!mgviewAllowed)
/*      */         {
/* 3322 */           return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3327 */       boolean isBusiness = false;
/* 3328 */       String selectedFunction = "am.webclient.dasboard.summarytab.title";
/* 3329 */       Cookie[] cookies = request.getCookies();
/* 3330 */       for (int i = 0; i < cookies.length; i++) {
/* 3331 */         if (cookies[i].getName().equals("am_mgview"))
/*      */         {
/* 3333 */           if (cookies[i].getValue().equals("business"))
/*      */           {
/* 3335 */             isBusiness = true;
/*      */           }
/* 3337 */           if (!cookies[i].getValue().equals("mypage"))
/*      */             break;
/* 3339 */           request.setAttribute("selectM", "mypageview");
/* 3340 */           String fromMyPage = (String)request.getAttribute("fromMyPage");
/* 3341 */           if (fromMyPage == null) {}
/*      */           
/*      */ 
/*      */ 
/* 3345 */           break;
/*      */         }
/*      */       }
/*      */       
/* 3349 */       String businessTabAppend = "";
/* 3350 */       if (isBusiness) {
/* 3351 */         request.setAttribute("selectM", "flashview");
/*      */       }
/*      */       
/*      */ 
/* 3355 */       request.setAttribute("mointorGroupTypes", GroupComponent.getMointorGroupTypes());
/* 3356 */       String groupType = "1";
/* 3357 */       ArrayList list = null;
/* 3358 */       AMConnectionPool con = new AMConnectionPool();
/* 3359 */       Properties alerts = new Properties();
/* 3360 */       if (((DynaActionForm)form).get("name") == null)
/*      */       {
/* 3362 */         ResultSet rs = AMConnectionPool.executeQueryStmt("select DISPLAYNAME , GROUPTYPE  from AM_ManagedObject inner join AM_HOLISTICAPPLICATION on HAID=RESOURCEID where RESOURCEID=" + haid);
/*      */         try {
/* 3364 */           if (rs.next())
/*      */           {
/* 3366 */             ((DynaActionForm)form).set("name", rs.getString("DISPLAYNAME"));
/* 3367 */             ((DynaActionForm)form).set("grouptype", rs.getString("GROUPTYPE"));
/* 3368 */             request.setAttribute("grouptype", rs.getString("GROUPTYPE"));
/*      */           }
/*      */         }
/*      */         finally {
/* 3372 */           if (rs != null)
/*      */           {
/* 3374 */             rs.close();
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 3380 */         groupType = (String)request.getAttribute("grouptype");
/*      */       }
/*      */       
/*      */ 
/* 3384 */       Vector mgsForOperator = null;
/* 3385 */       if (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request))
/*      */       {
/* 3387 */         if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 3388 */           mgsForOperator = ClientDBUtil.getUserResourceID(request);
/*      */         } else {
/* 3390 */           mgsForOperator = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3395 */       request.setAttribute("breadcrumb", this.mo.getBreadCrumbForMO(haid));
/* 3396 */       if (((request.getParameter("selectM") != null) && (request.getParameter("selectM").equals("flashview"))) || (isBusiness))
/*      */       {
/* 3398 */         putFlashProps(haid, request);
/* 3399 */         return new ActionForward("/jsp/ShowWebAppGroup.jsp");
/*      */       }
/*      */       
/* 3402 */       if (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request))
/*      */       {
/* 3404 */         list = AlarmUtil.getAlertsForResource(Integer.parseInt(haid), true, request);
/*      */       }
/*      */       else
/*      */       {
/* 3408 */         list = AlarmUtil.getAlertsForResource(Integer.parseInt(haid), true);
/*      */       }
/* 3410 */       request.setAttribute("recent5Alarms", list);
/* 3411 */       Object allChildsunderMG = new ArrayList();
/* 3412 */       ArrayList<String> allSubGroupsunderMG = new ArrayList();
/*      */       
/* 3414 */       HashMap allChildsSummary = getMGsAllChildsSummary(haid, mgsForOperator);
/* 3415 */       ((ArrayList)allChildsunderMG).addAll((ArrayList)allChildsSummary.get("allChildsunderMG"));
/* 3416 */       alerts.putAll((Properties)allChildsSummary.get("alerts"));
/* 3417 */       request.setAttribute("widgetData", allChildsSummary);
/*      */       
/*      */ 
/* 3420 */       HashMap componentToChildMapping = new HashMap();
/* 3421 */       HashMap flatListProps = new HashMap();
/* 3422 */       flatListProps.put("allChildsunderMG", allChildsunderMG);
/* 3423 */       flatListProps.put("haid", haid);
/* 3424 */       flatListProps.put("componentToChildMapping", componentToChildMapping);
/* 3425 */       flatListProps.put("allSubGroupsunderMG", allSubGroupsunderMG);
/* 3426 */       flatListProps.put("groupType", groupType);
/* 3427 */       flatListProps.put("isoperator", String.valueOf(request.isUserInRole("OPERATOR")));
/* 3428 */       flatListProps.put("mgsForOperator", mgsForOperator);
/* 3429 */       getTreeForFlatListingOfSubGroups(flatListProps, haid);
/* 3430 */       HashMap hostMoMapping = getHostDetailsForMO((ArrayList)allChildsunderMG);
/* 3431 */       request.setAttribute("hostMoMapping", hostMoMapping);
/* 3432 */       request.setAttribute("childsAsFlatTree", componentToChildMapping);
/* 3433 */       request.setAttribute("noofsubgroups", Integer.valueOf(allSubGroupsunderMG.size()));
/* 3434 */       if (componentToChildMapping.get("alerts") != null)
/*      */       {
/* 3436 */         alerts.putAll((Properties)componentToChildMapping.get("alerts"));
/*      */       }
/*      */       
/* 3439 */       HashMap componentInfo = getComponentDetails(haid, flatListProps);
/* 3440 */       request.setAttribute("componentlist", componentInfo.get("componentlist"));
/* 3441 */       request.setAttribute("childlistmapping", componentInfo.get("childlistmapping"));
/* 3442 */       request.setAttribute("monitorslist", componentInfo.get("monitorslist"));
/* 3443 */       request.setAttribute("nofofComponents", componentInfo.get("nofofComponents"));
/* 3444 */       request.setAttribute("childAndParentInfo", componentInfo.get("childAndParentInfo"));
/* 3445 */       JSONObject obj = (JSONObject)componentInfo.get("childAndParentInfoInJSON");
/* 3446 */       request.setAttribute("childAndParentInfoInJSON", obj.toString());
/* 3447 */       if (componentInfo.get("alerts") != null)
/*      */       {
/* 3449 */         alerts.putAll((Properties)componentInfo.get("alerts"));
/*      */       }
/*      */       
/* 3452 */       HashMap urlinfo = getTopNUrls((ArrayList)allChildsunderMG, 10, mgsForOperator);
/* 3453 */       request.setAttribute("urlinfo", urlinfo);
/* 3454 */       request.setAttribute("alerts", alerts);
/*      */       
/*      */ 
/* 3457 */       ArrayList listForUnManaged = new ArrayList();
/* 3458 */       listForUnManaged.addAll((Collection)allChildsunderMG);
/* 3459 */       listForUnManaged.addAll(allSubGroupsunderMG);
/* 3460 */       ArrayList<String> unManagedMonitors = getUnManagedMonitorsFromList(listForUnManaged);
/* 3461 */       request.setAttribute("unManagedMonitors", unManagedMonitors);
/* 3462 */       if ((allChildsunderMG == null) || (((ArrayList)allChildsunderMG).size() == 0))
/*      */       {
/* 3464 */         request.setAttribute("noMonitors", "true");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3470 */       ex.printStackTrace();
/*      */     }
/* 3472 */     ChildMOHandler.showWebApplication(request);
/* 3473 */     ActionMessages messages = new ActionMessages();
/* 3474 */     if (request.getParameter("fromwhere") != null)
/*      */     {
/* 3476 */       if (request.getParameter("fromwhere").equals("unmanagemonitorgroups"))
/*      */       {
/* 3478 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.unmanage.message.text")));
/*      */       }
/* 3480 */       else if (request.getParameter("fromwhere").equals("managemonitorgroups"))
/*      */       {
/* 3482 */         String messagetosay = FormatUtil.getString("am.webclient.monitorgroupview.manage.message.text");
/* 3483 */         if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*      */         {
/* 3485 */           messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*      */         }
/* 3487 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(messagetosay));
/*      */       }
/* 3489 */       else if (request.getParameter("fromwhere").equals("pollingMessage"))
/*      */       {
/* 3491 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.polling.updated.text")));
/*      */       }
/* 3493 */       else if (request.getParameter("fromwhere").equals("bulkupdate"))
/*      */       {
/* 3495 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.username.updated.text")));
/*      */       }
/* 3497 */       else if (request.getParameter("fromwhere").equals("enableactions"))
/*      */       {
/* 3499 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.enablemesaage.text")));
/*      */       }
/* 3501 */       else if (request.getParameter("fromwhere").equals("disableactions"))
/*      */       {
/* 3503 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.disablemesaage.text")));
/*      */       }
/* 3505 */       else if (request.getParameter("fromwhere").equals("afterdeletingMGs"))
/*      */       {
/* 3507 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.delete.SUBMG.success")));
/*      */       }
/* 3509 */       else if (request.getParameter("fromwhere").equals("afterdeleting"))
/*      */       {
/* 3511 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.delete.success")));
/*      */       }
/* 3513 */       else if (request.getParameter("fromwhere").equals("deletemonitorsonly"))
/*      */       {
/* 3515 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.delete.monitor.success")));
/*      */       }
/*      */     }
/* 3518 */     saveMessages(request, messages);
/*      */     
/* 3520 */     return new ActionForward("/jsp/ShowWebAppGroup.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward recentdownTimes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 3525 */     response.setContentType("text/xml; charset=UTF-8");
/*      */     
/*      */     try
/*      */     {
/* 3529 */       String resourceid = request.getParameter("resourceid");
/* 3530 */       HashMap downtimeDetails = getDownTimeDetails(resourceid, "5", "0");
/* 3531 */       ArrayList downtimesummary = (ArrayList)downtimeDetails.get("downtimesummary");
/* 3532 */       Properties summary = (Properties)downtimeDetails.get("summary");
/* 3533 */       request.setAttribute("downtimesummary", downtimesummary);
/* 3534 */       request.setAttribute("summary", summary);
/*      */       
/*      */ 
/* 3537 */       HashMap monitorDownTimeDetails = getDownTimeDetails(resourceid, "5", "0", false);
/* 3538 */       ArrayList monitordowntimesummary = (ArrayList)monitorDownTimeDetails.get("downtimesummary");
/* 3539 */       Vector childVec = new Vector();
/* 3540 */       for (Object obj : monitordowntimesummary)
/*      */       {
/* 3542 */         Properties prop = (Properties)obj;
/* 3543 */         String monitorType = prop.getProperty("MonitorType");
/* 3544 */         if (ChildMOHandler.isChildMonitorTypeSupportedForMG(monitorType))
/*      */         {
/* 3546 */           String resId = prop.getProperty("Resourceid");
/* 3547 */           childVec.add(resId);
/*      */         }
/*      */       }
/* 3550 */       HashMap<String, HashMap<String, String>> childMonitorMap = ChildMOHandler.getChildMonitorWithParentInfo(childVec);
/* 3551 */       if (childMonitorMap != null)
/*      */       {
/* 3553 */         for (Object obj : monitordowntimesummary)
/*      */         {
/* 3555 */           Properties prop = (Properties)obj;
/* 3556 */           String resId = prop.getProperty("Resourceid");
/* 3557 */           HashMap<String, String> childInfo = (HashMap)childMonitorMap.get(resId);
/* 3558 */           if (childInfo != null)
/*      */           {
/* 3560 */             String tmpName = (String)childInfo.get("displayname");
/* 3561 */             if (tmpName != null)
/*      */             {
/* 3563 */               prop.put("Displayname", childInfo.get("trimmeddisplayname"));
/* 3564 */               prop.put("ToolTipTitle", tmpName);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3569 */             prop.put("ToolTipTitle", prop.getProperty("Displayname"));
/*      */           }
/*      */         }
/*      */       }
/* 3573 */       Properties monitorSummary = (Properties)monitorDownTimeDetails.get("summary");
/* 3574 */       request.setAttribute("monitordowntimesummary", monitordowntimesummary);
/* 3575 */       request.setAttribute("monitorsummary", monitorSummary);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3580 */       ex.printStackTrace();
/*      */     }
/* 3582 */     return new ActionForward("/jsp/downtimesummary.jsp?fromMG=true");
/*      */   }
/*      */   
/*      */   public ActionForward recentEvents(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 3587 */     response.setContentType("text/xml; charset=UTF-8");
/*      */     try
/*      */     {
/* 3590 */       Vector mgsForOperator = null;
/* 3591 */       if (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request))
/*      */       {
/* 3593 */         if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 3594 */           mgsForOperator = ClientDBUtil.getUserResourceID(request);
/*      */         } else {
/* 3596 */           mgsForOperator = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3601 */       String haid = request.getParameter("resourceid");
/* 3602 */       HashMap allChildsSummary = getMGsAllChildsSummary(haid, mgsForOperator);
/* 3603 */       ArrayList allChildsunderMG = (ArrayList)allChildsSummary.get("allChildsunderMG");
/* 3604 */       HashMap eventsForMetrics = compareCategoryGroupedEvents(allChildsunderMG);
/* 3605 */       ArrayList compareEventCount = (ArrayList)eventsForMetrics.get("compareEventCount");
/* 3606 */       request.setAttribute("compareEventCount", compareEventCount);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3610 */       ex.printStackTrace();
/*      */     }
/* 3612 */     return new ActionForward("/jsp/recentEvents.jsp");
/*      */   }
/*      */   
/*      */   private ArrayList<String> getUnManagedMonitorsFromList(ArrayList<String> residList)
/*      */   {
/*      */     try {
/* 3618 */       String query = "select resid from AM_UnManagedNodes where " + ManagedApplication.getCondition("resid", residList);
/* 3619 */       return this.mo.getRowsForSingleColumn(query);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3623 */       ex.printStackTrace();
/*      */     }
/* 3625 */     return new ArrayList();
/*      */   }
/*      */   
/*      */   private void getTreeForFlatListingOfSubGroups(HashMap flatListingProps, String haid) {
/* 3629 */     HashMap componentToChildMapping = (HashMap)flatListingProps.get("componentToChildMapping");
/* 3630 */     ArrayList<String> residList = (ArrayList)flatListingProps.get("allChildsunderMG");
/* 3631 */     ArrayList<String> allSubGroupsunderMG = (ArrayList)flatListingProps.get("allSubGroupsunderMG");
/* 3632 */     Vector mgsForOperator = (Vector)flatListingProps.get("mgsForOperator");
/* 3633 */     String isoperator = (String)flatListingProps.get("isoperator");
/* 3634 */     String groupType = (String)flatListingProps.get("groupType");
/* 3635 */     String groupCondition = "";
/* 3636 */     if ("true".equals(isoperator))
/*      */     {
/* 3638 */       groupCondition = " and " + ManagedApplication.getCondition("mapper1.PARENTID", mgsForOperator);
/*      */     }
/* 3640 */     if ("2".equals(groupType))
/*      */     {
/*      */ 
/* 3643 */       groupCondition = groupCondition + " and managed1.TYPE not in ('HAI')";
/*      */     }
/* 3645 */     if (componentToChildMapping == null)
/*      */     {
/* 3647 */       componentToChildMapping = new HashMap();
/* 3648 */       flatListingProps.put("componentToChildMapping", componentToChildMapping);
/*      */     }
/* 3650 */     if (residList == null)
/*      */     {
/* 3652 */       residList = new ArrayList();
/* 3653 */       flatListingProps.put("allChildsunderMG", residList);
/*      */     }
/* 3655 */     if (allSubGroupsunderMG == null)
/*      */     {
/* 3657 */       allSubGroupsunderMG = new ArrayList();
/* 3658 */       flatListingProps.put("allSubGroupsunderMG", allSubGroupsunderMG);
/*      */     }
/* 3660 */     ArrayList<HashMap> listOfComponents = new ArrayList();
/* 3661 */     ArrayList residlistForalert = new ArrayList();
/* 3662 */     ArrayList entityListForAlert = new ArrayList();
/* 3663 */     Hashtable healthkeys = (Hashtable)com.adventnet.appmanager.util.Constants.getGlobalObject("healthkeys");
/* 3664 */     Hashtable availabilitykeys = (Hashtable)com.adventnet.appmanager.util.Constants.getGlobalObject("availabilitykeys");
/* 3665 */     String underscore = "_";
/*      */     
/*      */     try
/*      */     {
/* 3669 */       String componentsQuery = "select managed1.DISPLAYNAME,mapper1.CHILDID,webappgroup.GROUPTYPE,managed1.TYPE,managed1.DCSTARTED from AM_PARENTCHILDMAPPER as mapper1  inner join  AM_HOLISTICAPPLICATION as webappgroup on mapper1.PARENTID=webappgroup.HAID inner join AM_ManagedObject as managed1 on mapper1.CHILDID=managed1.RESOURCEID where webappgroup.HAID=" + haid + " " + groupCondition + " order by managed1.DISPLAYNAME, webappgroup.GROUPTYPE";
/* 3670 */       ArrayList childrenlist = this.mo.getRows(componentsQuery);
/* 3671 */       for (int i = 0; i < childrenlist.size(); i++)
/*      */       {
/* 3673 */         ArrayList singlerow = (ArrayList)childrenlist.get(i);
/* 3674 */         String displayname = (String)singlerow.get(0);
/* 3675 */         String childid = (String)singlerow.get(1);
/* 3676 */         String grouptype = (String)singlerow.get(2);
/* 3677 */         String resourcetype = (String)singlerow.get(3);
/*      */         
/*      */ 
/* 3680 */         String dcstarted = (String)singlerow.get(4);
/* 3681 */         residlistForalert.add(childid);
/* 3682 */         entityListForAlert.add(childid + underscore + availabilitykeys.get(resourcetype));
/* 3683 */         entityListForAlert.add(childid + underscore + healthkeys.get(resourcetype));
/*      */         
/* 3685 */         HashMap<String, String> eachComponent = new HashMap(4);
/* 3686 */         eachComponent.put("displayname", displayname);
/* 3687 */         eachComponent.put("resourceid", childid);
/* 3688 */         eachComponent.put("resourcetype", resourcetype);
/* 3689 */         eachComponent.put("grouptype", grouptype);
/*      */         
/*      */ 
/* 3692 */         eachComponent.put("dcstarted", dcstarted);
/* 3693 */         if (resourcetype.equals("HAI"))
/*      */         {
/* 3695 */           allSubGroupsunderMG.add(childid);
/* 3696 */           getTreeForFlatListingOfSubGroups(flatListingProps, childid);
/*      */         }
/*      */         else
/*      */         {
/* 3700 */           residList.add(childid);
/*      */         }
/* 3702 */         listOfComponents.add(eachComponent);
/*      */       }
/* 3704 */       Properties alerts = FaultUtil.getStatus(entityListForAlert, false);
/* 3705 */       if (componentToChildMapping.get("alerts") != null)
/*      */       {
/* 3707 */         Properties alerts_all = (Properties)componentToChildMapping.get("alerts");
/* 3708 */         alerts_all.putAll(alerts);
/*      */       }
/*      */       else
/*      */       {
/* 3712 */         componentToChildMapping.put("alerts", alerts);
/*      */       }
/* 3714 */       CriticalPrioritizer criticalComparator = new CriticalPrioritizer(alerts, healthkeys);
/* 3715 */       Collections.sort(listOfComponents, criticalComparator);
/*      */       
/* 3717 */       componentToChildMapping.put(haid, listOfComponents);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3722 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private HashMap getHostDetailsForMO(ArrayList<String> residList) {
/* 3727 */     toreturn = new HashMap();
/* 3728 */     ResultSet rs = null;
/* 3729 */     AMConnectionPool con = new AMConnectionPool();
/*      */     try
/*      */     {
/* 3732 */       String query = "select mo2.RESOURCEID as HOSTID, InetService.TARGETNAME HOST, InetService.TARGETADDRESS HOSTIP,mo1.RESOURCEID as MOID from AM_ManagedObject as mo1,InetService  inner join AM_ManagedObject as mo2 on mo2.RESOURCENAME=InetService.TARGETNAME  where InetService.NAME=mo1.RESOURCENAME and " + ManagedApplication.getCondition("mo1.RESOURCEID", residList);
/*      */       
/*      */ 
/* 3735 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 3736 */       while (rs.next())
/*      */       {
/* 3738 */         HashMap eachHost = new HashMap();
/* 3739 */         eachHost.put("hostname", rs.getString("HOST"));
/* 3740 */         eachHost.put("resourceid", rs.getString("HOSTID"));
/* 3741 */         eachHost.put("ipaddress", rs.getString("HOSTIP"));
/* 3742 */         toreturn.put(rs.getString("MOID"), eachHost);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3751 */       if (rs != null) {
/*      */         try
/*      */         {
/* 3754 */           rs.close();
/*      */         }
/*      */         catch (Exception ex) {}
/*      */       }
/*      */       
/*      */ 
/* 3760 */       it = residList.iterator();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3748 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/* 3751 */       if (rs != null) {
/*      */         try
/*      */         {
/* 3754 */           rs.close();
/*      */         }
/*      */         catch (Exception ex) {}
/*      */       }
/*      */     }
/*      */     
/*      */     Iterator it;
/* 3761 */     ArrayList<String> excluded_resIds = new ArrayList();
/* 3762 */     while (it.hasNext()) {
/* 3763 */       String resId = (String)it.next();
/* 3764 */       if (!toreturn.containsKey(resId)) {
/* 3765 */         excluded_resIds.add(resId);
/*      */       }
/*      */     }
/* 3768 */     if (excluded_resIds.size() > 0) {
/* 3769 */       ResultSet rs1 = null;
/*      */       try {
/* 3771 */         String confMonHostQuery = "select TYPE,RESOURCEID from AM_ManagedObject  inner join AM_MONITOR_TYPES on TYPENAME=TYPE where " + ManagedApplication.getCondition("RESOURCEID", excluded_resIds);
/* 3772 */         rs1 = AMConnectionPool.executeQueryStmt(confMonHostQuery);
/* 3773 */         while (rs1.next())
/*      */         {
/* 3775 */           HashMap eachHost = new HashMap();
/*      */           try {
/* 3777 */             ShowCustomDetails obj = new ShowCustomDetails();
/* 3778 */             String hostIp = obj.getHostName(rs1.getString("TYPE"), rs1.getString("RESOURCEID"));
/* 3779 */             if ((hostIp != null) && (!hostIp.equals(""))) {
/* 3780 */               TopoAPI tapi = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/*      */               
/* 3782 */               String checkalreadyexists = tapi.getNodeNameByIP(hostIp);
/* 3783 */               if (checkalreadyexists != null) {
/* 3784 */                 eachHost.put("hostname", checkalreadyexists);
/* 3785 */                 eachHost.put("resourceid", com.adventnet.appmanager.util.Constants.getResourceID(checkalreadyexists));
/* 3786 */                 eachHost.put("ipaddress", hostIp);
/* 3787 */                 toreturn.put(rs1.getString("RESOURCEID"), eachHost);
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/* 3792 */             e.printStackTrace();
/*      */           }
/*      */         }
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
/* 3809 */         return toreturn;
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3797 */         ex.printStackTrace();
/*      */       }
/*      */       finally {
/* 3800 */         if (rs1 != null) {
/*      */           try
/*      */           {
/* 3803 */             rs1.close();
/*      */           } catch (Exception ex) {
/* 3805 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private HashMap compareCategoryGroupedEvents(ArrayList resourceids)
/*      */   {
/* 3814 */     HashMap toreturn = new HashMap();
/* 3815 */     ArrayList compareEventCount = new ArrayList();
/*      */     try {
/* 3817 */       String today = "0";
/* 3818 */       String yesterday = "3";
/*      */       
/* 3820 */       long[] timeStamps = ReportUtilities.getTimeStamp(today);
/* 3821 */       long startTime = timeStamps[0];
/* 3822 */       long endTime = timeStamps[1];
/*      */       
/* 3824 */       HashMap todayEvents = getCategoryGroupedEvents(resourceids, startTime, endTime);
/*      */       
/* 3826 */       timeStamps = ReportUtilities.getTimeStamp(yesterday);
/* 3827 */       startTime = timeStamps[0];
/* 3828 */       endTime = timeStamps[1];
/*      */       
/* 3830 */       HashMap yesterdayEvents = getCategoryGroupedEvents(resourceids, startTime, endTime);
/*      */       
/* 3832 */       Set keys = todayEvents.keySet();
/* 3833 */       for (Iterator iter = keys.iterator(); iter.hasNext();)
/*      */       {
/* 3835 */         String attributeid = (String)iter.next();
/* 3836 */         HashMap todayCategory = (HashMap)todayEvents.get(attributeid);
/* 3837 */         String todayEventCount = (String)todayCategory.get("eventcount");
/* 3838 */         String yesterdayEventCount = "0";
/* 3839 */         if (yesterdayEvents.get(attributeid) != null)
/*      */         {
/* 3841 */           HashMap yesterdayCategory = (HashMap)yesterdayEvents.get(attributeid);
/* 3842 */           yesterdayEventCount = (String)yesterdayCategory.get("eventcount");
/*      */         }
/* 3844 */         todayCategory.put("todayEventCount", todayEventCount);
/* 3845 */         todayCategory.put("yesterdayEventCount", yesterdayEventCount);
/* 3846 */         compareEventCount.add(todayCategory);
/*      */       }
/* 3848 */       keys = yesterdayEvents.keySet();
/* 3849 */       for (Iterator iter = keys.iterator(); iter.hasNext();)
/*      */       {
/* 3851 */         String attributeid = (String)iter.next();
/* 3852 */         HashMap yesterdayCategory = (HashMap)yesterdayEvents.get(attributeid);
/* 3853 */         if (todayEvents.get(attributeid) == null)
/*      */         {
/* 3855 */           yesterdayCategory.put("todayEventCount", "0");
/* 3856 */           yesterdayCategory.put("yesterdayEventCount", yesterdayCategory.get("eventcount"));
/* 3857 */           compareEventCount.add(yesterdayCategory);
/*      */         }
/*      */       }
/*      */       
/* 3861 */       toreturn.put("compareEventCount", compareEventCount);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3865 */       ex.printStackTrace();
/*      */     }
/* 3867 */     return toreturn;
/*      */   }
/*      */   
/*      */   private HashMap getCategoryGroupedEvents(ArrayList resourceids, long startTime, long endTime) {
/* 3871 */     toreturn = new HashMap();
/* 3872 */     AMConnectionPool con = new AMConnectionPool();
/* 3873 */     ResultSet rs = null;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 3878 */       String query = " select count(ID) as eventcount,CATEGORY,max(AM_ATTRIBUTES.DISPLAYNAME) as displayname, AM_ATTRIBUTES.RESOURCETYPE  as type, AM_ManagedResourceType. DISPLAYNAME  as resourcetypeDisplayName from Event inner join AM_ATTRIBUTES on  CATEGORY=" + DBQueryUtil.castasVarchar("ATTRIBUTEID") + "  inner join AM_ManagedResourceType on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedResourceType.RESOURCETYPE  where TYPE not in (2) and " + ManagedApplication.getCondition("SOURCE", resourceids) + "  and SEVERITY in (1,4) and TTIME>=" + startTime + " and TTIME<=" + endTime + "  group by CATEGORY,AM_ATTRIBUTES.RESOURCETYPE,AM_ManagedResourceType. DISPLAYNAME";
/* 3879 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 3880 */       while (rs.next())
/*      */       {
/* 3882 */         String displayname = rs.getString("displayname");
/* 3883 */         String type = rs.getString("type");
/* 3884 */         String eventcount = rs.getString("eventcount");
/* 3885 */         String category = rs.getString("CATEGORY");
/* 3886 */         String resourcetypedisplayname = rs.getString("resourcetypeDisplayName");
/* 3887 */         HashMap singleCategory = new HashMap();
/* 3888 */         singleCategory.put("displayname", FormatUtil.getString(displayname));
/* 3889 */         singleCategory.put("resourcetype", type);
/* 3890 */         singleCategory.put("eventcount", eventcount);
/* 3891 */         singleCategory.put("attributeid", category);
/* 3892 */         singleCategory.put("resourcetypedisplayname", FormatUtil.getString(resourcetypedisplayname));
/* 3893 */         toreturn.put(category, singleCategory);
/*      */       }
/* 3895 */       rs.close();
/* 3896 */       toreturn.putAll(ChildMOHandler.getCategoryGroupedEvents(resourceids, startTime, endTime));
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
/* 3911 */       return toreturn;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3900 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/* 3905 */         if (rs != null)
/*      */         {
/* 3907 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap getMGsAllChildsSummary(String haid, Vector resIdsForOperator)
/*      */   {
/* 3919 */     HashMap availHealthData = new HashMap();
/* 3920 */     ArrayList<String> allChildsunderMG = new ArrayList();
/*      */     try {
/* 3922 */       Vector haids = new Vector();
/* 3923 */       haids.add(haid);
/* 3924 */       ManagedApplication.getChildIDs(haids, haid);
/* 3925 */       if (resIdsForOperator != null)
/*      */       {
/* 3927 */         Vector tempList = new Vector();
/* 3928 */         for (int i = 0; i < haids.size(); i++)
/*      */         {
/* 3930 */           String eachResId = (String)haids.get(i);
/* 3931 */           if (resIdsForOperator.contains(eachResId))
/*      */           {
/* 3933 */             tempList.add(eachResId);
/*      */           }
/*      */         }
/* 3936 */         haids = tempList;
/*      */       }
/*      */       
/*      */ 
/* 3940 */       String subgroupQuery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.TYPE from AM_PARENTCHILDMAPPER inner join AM_ManagedObject on CHILDID=RESOURCEID  where AM_ManagedObject.TYPE !='HAI' and  " + ManagedApplication.getCondition("PARENTID", haids);
/* 3941 */       Hashtable healthkeys = (Hashtable)com.adventnet.appmanager.util.Constants.getGlobalObject("healthkeys");
/* 3942 */       Hashtable availabilitykeys = (Hashtable)com.adventnet.appmanager.util.Constants.getGlobalObject("availabilitykeys");
/*      */       
/* 3944 */       AMConnectionPool con = new AMConnectionPool();
/* 3945 */       ArrayList availandHealthEntity = new ArrayList();
/* 3946 */       HashMap<String, String> residToTypeMaping = new HashMap();
/* 3947 */       availandHealthEntity.add(haid + "_" + healthkeys.get("HAI"));
/* 3948 */       availandHealthEntity.add(haid + "_" + availabilitykeys.get("HAI"));
/* 3949 */       ResultSet set1 = AMConnectionPool.executeQueryStmt(subgroupQuery);
/*      */       try {
/* 3951 */         while (set1.next())
/*      */         {
/* 3953 */           String child_resid = set1.getString("RESOURCEID");
/* 3954 */           String child_restype = set1.getString("TYPE");
/* 3955 */           allChildsunderMG.add(child_resid);
/* 3956 */           residToTypeMaping.put(child_resid, child_restype);
/* 3957 */           availandHealthEntity.add(child_resid + "_" + healthkeys.get(child_restype));
/* 3958 */           availandHealthEntity.add(child_resid + "_" + availabilitykeys.get(child_restype));
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/* 3963 */         if (set1 != null)
/*      */         {
/* 3965 */           set1.close();
/*      */         }
/*      */       }
/* 3968 */       Properties alerts = FaultUtil.getStatus(availandHealthEntity, false);
/*      */       
/* 3970 */       int totalCountMonitors = allChildsunderMG.size();
/* 3971 */       int warncount = 0;
/* 3972 */       int criticalcount = 0;
/* 3973 */       int downcount = 0;
/* 3974 */       int clearcount = 0;
/* 3975 */       for (String child_resid : allChildsunderMG)
/*      */       {
/* 3977 */         String child_restype = (String)residToTypeMaping.get(child_resid);
/* 3978 */         String healthentity = child_resid + "#" + healthkeys.get(child_restype);
/* 3979 */         String availentity = child_resid + "#" + availabilitykeys.get(child_restype);
/* 3980 */         String healthStatus = alerts.getProperty(healthentity);
/* 3981 */         String availStatus = alerts.getProperty(availentity);
/* 3982 */         if (String.valueOf(1).equals(healthStatus))
/*      */         {
/* 3984 */           criticalcount++;
/*      */         }
/* 3986 */         else if (String.valueOf(4).equals(healthStatus))
/*      */         {
/* 3988 */           warncount++;
/*      */         }
/* 3990 */         if (String.valueOf(1).equals(availStatus))
/*      */         {
/* 3992 */           downcount++;
/*      */         }
/*      */       }
/* 3995 */       clearcount = totalCountMonitors - warncount - criticalcount;
/* 3996 */       String parentmgHealthStatus = alerts.getProperty(haid + "#" + healthkeys.get("HAI"));
/* 3997 */       String parentAvailStatus = alerts.getProperty(haid + "#" + availabilitykeys.get("HAI"));
/*      */       
/* 3999 */       availHealthData.put("monitor_total", Integer.valueOf(totalCountMonitors));
/* 4000 */       availHealthData.put("monitor_critical", Integer.valueOf(criticalcount));
/* 4001 */       availHealthData.put("monitor_warning", Integer.valueOf(warncount));
/* 4002 */       availHealthData.put("monitor_clear", Integer.valueOf(clearcount));
/* 4003 */       if (String.valueOf(1).equals(parentAvailStatus))
/*      */       {
/* 4005 */         availHealthData.put("availability_up_status", Integer.valueOf(downcount));
/*      */       }
/*      */       else
/*      */       {
/* 4009 */         availHealthData.put("availability_up_status", Integer.valueOf(totalCountMonitors));
/*      */       }
/*      */       
/*      */ 
/* 4013 */       availHealthData.put("availability_status", parentAvailStatus);
/* 4014 */       availHealthData.put("health_status", parentmgHealthStatus);
/* 4015 */       availHealthData.put("allChildsunderMG", allChildsunderMG);
/* 4016 */       availHealthData.put("alerts", alerts);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4020 */       ex.printStackTrace();
/*      */     }
/* 4022 */     return availHealthData;
/*      */   }
/*      */   
/*      */   private HashMap getTopNUrls(ArrayList<String> resids, int topn, Vector resIdsForOperator)
/*      */   {
/* 4027 */     HashMap toreturn = new HashMap();
/* 4028 */     ResultSet rs2 = null;
/* 4029 */     ResultSet rs3 = null;
/* 4030 */     ArrayList<HashMap> topnurls = new ArrayList();
/* 4031 */     int maxvalue = 1000;
/* 4032 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 4035 */       long lastHoutTime = 0L;
/* 4036 */       lastHoutTime = System.currentTimeMillis() - 3600000L;
/* 4037 */       ArrayList topnurlsidList = new ArrayList();
/* 4038 */       String responseTimeQuery = "select RESID, AVG(responsetime) as avgresponsetime, MAX(AM_ManagedObject.DISPLAYNAME) as DISPLAYNAME from AM_ManagedObjectData, AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='URL' and RESOURCEID=RESID and " + ManagedApplication.getCondition("RESID", resids) + " and COLLECTIONTIME >" + lastHoutTime + "  group by RESID order by avgresponsetime desc";
/* 4039 */       if (resIdsForOperator != null)
/*      */       {
/* 4041 */         ArrayList tempList = new ArrayList();
/* 4042 */         for (int i = 0; i < resids.size(); i++)
/*      */         {
/* 4044 */           String eachResId = (String)resids.get(i);
/* 4045 */           if (resIdsForOperator.contains(eachResId))
/*      */           {
/* 4047 */             tempList.add(eachResId);
/*      */           }
/*      */         }
/* 4050 */         responseTimeQuery = "select RESID, AVG(responsetime) as avgresponsetime, MAX(AM_ManagedObject.DISPLAYNAME) as DISPLAYNAME from AM_ManagedObjectData, AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='URL' and RESOURCEID=RESID and " + ManagedApplication.getCondition("RESID", tempList) + " and COLLECTIONTIME >" + lastHoutTime + "  group by RESID order by avgresponsetime desc";
/*      */       }
/* 4052 */       rs2 = AMConnectionPool.executeQueryStmt(DBQueryUtil.getTopNValues(responseTimeQuery, topn));
/* 4053 */       while (rs2.next())
/*      */       {
/* 4055 */         int resTime = rs2.getInt("avgresponsetime");
/* 4056 */         HashMap eachMo = new HashMap(7);
/* 4057 */         eachMo.put("resourceid", rs2.getString("RESID"));
/* 4058 */         eachMo.put("displayname", rs2.getString("DISPLAYNAME"));
/* 4059 */         eachMo.put("value", Integer.valueOf(resTime));
/* 4060 */         topnurls.add(eachMo);
/* 4061 */         if (maxvalue < resTime)
/*      */         {
/* 4063 */           maxvalue = resTime;
/* 4064 */           maxvalue = (maxvalue / 1000 + 1) * 1000;
/*      */         }
/* 4066 */         topnurlsidList.add(rs2.getString("RESID"));
/*      */       }
/* 4068 */       if (topnurlsidList.size() > 0)
/*      */       {
/* 4070 */         String thresholdConfigQuery = "select AM_ATTRIBUTETHRESHOLDMAPPER.ID as ID, CRITICALTHRESHOLDCONDITION, CRITICALTHRESHOLDVALUE from AM_ATTRIBUTETHRESHOLDMAPPER inner join AM_ATTRIBUTES on ATTRIBUTEID=AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE and AM_ATTRIBUTES.ATTRIBUTE='ResponseTime'  inner join AM_THRESHOLDCONFIG on THRESHOLDCONFIGURATIONID  = AM_THRESHOLDCONFIG.ID where " + ManagedApplication.getCondition("AM_ATTRIBUTETHRESHOLDMAPPER.ID", topnurlsidList);
/* 4071 */         rs3 = AMConnectionPool.executeQueryStmt(thresholdConfigQuery);
/* 4072 */         while (rs3.next())
/*      */         {
/* 4074 */           String resid = rs3.getString("ID");
/* 4075 */           String condition = rs3.getString("CRITICALTHRESHOLDCONDITION");
/* 4076 */           if (condition.equals("GT"))
/*      */           {
/* 4078 */             long criticalvalue = rs3.getLong("CRITICALTHRESHOLDVALUE");
/* 4079 */             for (int i = 0; i < topnurls.size(); i++)
/*      */             {
/* 4081 */               HashMap eachMo = (HashMap)topnurls.get(i);
/* 4082 */               String residFromTopN = (String)eachMo.get("resourceid");
/* 4083 */               if (residFromTopN.equals(resid))
/*      */               {
/* 4085 */                 long maxvalue2 = criticalvalue * 100L / 80L;
/* 4086 */                 eachMo.put("maxvalue", String.valueOf(maxvalue2));
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 4102 */         if (rs2 != null)
/*      */         {
/* 4104 */           rs2.close();
/*      */         }
/* 4106 */         if (rs3 != null)
/*      */         {
/* 4108 */           rs3.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {}
/* 4112 */       toreturn.put("topnurls", topnurls);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4096 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 4102 */         if (rs2 != null)
/*      */         {
/* 4104 */           rs2.close();
/*      */         }
/* 4106 */         if (rs3 != null)
/*      */         {
/* 4108 */           rs3.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/* 4113 */     toreturn.put("maxvalue", Integer.valueOf(maxvalue));
/* 4114 */     return toreturn;
/*      */   }
/*      */   
/*      */   class CriticalPrioritizer
/*      */     implements Comparator
/*      */   {
/*      */     Properties alerts;
/*      */     Hashtable healthkeys;
/*      */     
/*      */     public CriticalPrioritizer() {}
/*      */     
/*      */     public CriticalPrioritizer(Properties alerts, Hashtable healthkeys)
/*      */     {
/* 4127 */       this.alerts = alerts;
/* 4128 */       this.healthkeys = healthkeys;
/*      */     }
/*      */     
/*      */     public int compare(Object o1, Object o2)
/*      */     {
/* 4133 */       HashMap obj1 = (HashMap)o1;
/* 4134 */       HashMap obj2 = (HashMap)o2;
/* 4135 */       String healthid1 = (String)this.healthkeys.get(obj1.get("resourcetype"));
/* 4136 */       String healthid2 = (String)this.healthkeys.get(obj2.get("resourcetype"));
/* 4137 */       String resourceid1 = (String)obj1.get("resourceid");
/* 4138 */       String resourceid2 = (String)obj2.get("resourceid");
/* 4139 */       String severity1_str = this.alerts.getProperty(resourceid1 + "#" + healthid1);
/* 4140 */       String severity2_str = this.alerts.getProperty(resourceid2 + "#" + healthid2);
/* 4141 */       int severity1 = 100;
/* 4142 */       int severity2 = 100;
/* 4143 */       if (severity1_str != null)
/*      */       {
/* 4145 */         severity1 = Integer.parseInt(severity1_str);
/*      */       }
/* 4147 */       if (severity2_str != null)
/*      */       {
/* 4149 */         severity2 = Integer.parseInt(severity2_str);
/*      */       }
/* 4151 */       if (severity1 > severity2)
/*      */       {
/* 4153 */         return 1;
/*      */       }
/* 4155 */       if (severity1 < severity2)
/*      */       {
/* 4157 */         return -1;
/*      */       }
/* 4159 */       if (severity2 == severity1)
/*      */       {
/* 4161 */         return 0;
/*      */       }
/*      */       
/*      */ 
/* 4165 */       return 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private HashMap getDownTimeDetails(String resourceid, String limitDowntimes, String period)
/*      */   {
/* 4173 */     return getDownTimeDetails(resourceid, limitDowntimes, period, true);
/*      */   }
/*      */   
/*      */   private HashMap getDownTimeDetails(String resourceid, String limitDowntimes, String period, boolean mgdowntime)
/*      */   {
/* 4178 */     long[] timeStamps = ReportUtilities.getTimeStamp(period);
/* 4179 */     long startTime = timeStamps[0];
/* 4180 */     long endTime = timeStamps[1];
/*      */     
/* 4182 */     ArrayList downtimesummary = new ArrayList();
/*      */     
/*      */ 
/* 4185 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4186 */     ResultSet set = null;
/* 4187 */     long[] time = ReportUtilities.getTimeStamp(period);
/* 4188 */     long customstartTime = 0L;
/* 4189 */     long customendTime = 0L;
/* 4190 */     long totalDuration = 0L;
/* 4191 */     long unmanagedtime = 0L;
/* 4192 */     long scheduledtime = 0L;
/* 4193 */     long totdowntime = 0L;
/*      */     
/*      */ 
/*      */ 
/* 4197 */     downtimeDetails = new HashMap();
/*      */     try
/*      */     {
/* 4200 */       String query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.REASONID,SHORT_DESCRIPTION,TYPE from AM_MO_DowntimeData left outer join AM_DOWNTIMEREASON on AM_MO_DowntimeData.REASONID = AM_DOWNTIMEREASON.REASONID where RESID=" + resourceid + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/*      */       
/* 4202 */       if (!mgdowntime)
/*      */       {
/* 4204 */         query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.REASONID,AM_DOWNTIMEREASON.SHORT_DESCRIPTION,AM_MO_DowntimeData.TYPE, AM_ManagedObject.TYPE as monitortype, AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID from AM_ManagedObject left outer join AM_MO_DowntimeData on AM_ManagedObject.RESOURCEID=AM_MO_DowntimeData.RESID left outer join AM_DOWNTIMEREASON on AM_MO_DowntimeData.REASONID = AM_DOWNTIMEREASON.REASONID where " + DependantMOUtil.getCondition("RESID", DependantMOUtil.getDependantResourceIDS(resourceid, true)) + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 4210 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 4212 */       while (set.next())
/*      */       {
/* 4214 */         int typeID = -1;
/* 4215 */         Properties rows = new Properties();
/*      */         
/* 4217 */         rows.put("downtime", new SimpleDateFormat("MMM d, h:mm a").format(new java.util.Date(set.getLong("DownTime"))));
/* 4218 */         if (set.getLong("UpTime") == endTime) {
/* 4219 */           rows.put("dontdelete", "true");
/*      */         }
/* 4221 */         rows.put("uptime", new SimpleDateFormat("MMM d, h:mm a").format(new java.util.Date(set.getLong("UpTime"))));
/* 4222 */         rows.put("downtimeinmillis", Long.toString(set.getLong("DownTime")));
/* 4223 */         rows.put("interval", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(set.getLong("TotalDownTime"))));
/* 4224 */         typeID = set.getInt("TYPE");
/*      */         
/* 4226 */         if (typeID == 1)
/*      */         {
/* 4228 */           totdowntime += set.getLong("TotalDownTime");
/*      */         }
/* 4230 */         else if (typeID == 2)
/*      */         {
/* 4232 */           unmanagedtime += set.getLong("TotalDownTime");
/*      */         }
/*      */         else
/*      */         {
/* 4236 */           scheduledtime += set.getLong("TotalDownTime");
/*      */         }
/* 4238 */         if (set.getString("SHORT_DESCRIPTION") != null) {
/* 4239 */           rows.put("Downtime_Reason", set.getString("SHORT_DESCRIPTION"));
/*      */         }
/*      */         else {
/* 4242 */           if (typeID == 2) {
/* 4243 */             rows.put("Downtime_Reason", "Monitor is Unmanaged");
/*      */           }
/* 4245 */           if (typeID == 3) {
/* 4246 */             rows.put("Downtime_Reason", "Scheduled Downtime");
/*      */           }
/*      */         }
/* 4249 */         rows.put("ReasonID", Integer.valueOf(set.getInt("REASONID")));
/* 4250 */         rows.put("typeID", Integer.valueOf(set.getInt("TYPE")));
/* 4251 */         if (totdowntime != 0L) {
/* 4252 */           rows.put("TotDownTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(totdowntime)));
/*      */         }
/* 4254 */         if (unmanagedtime != 0L) {
/* 4255 */           rows.put("UnManagedTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(unmanagedtime)));
/*      */         }
/* 4257 */         if (scheduledtime != 0L) {
/* 4258 */           rows.put("ScheduledTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(scheduledtime)));
/*      */         }
/*      */         
/* 4261 */         if (!mgdowntime)
/*      */         {
/* 4263 */           String displayName = EnterpriseUtil.decodeString(set.getString("DISPLAYNAME"));
/*      */           
/* 4265 */           rows.put("MonitorType", set.getString("monitortype"));
/* 4266 */           rows.put("Displayname", displayName);
/* 4267 */           rows.put("Resourceid", set.getString("RESOURCEID"));
/* 4268 */           rows.put("ImagePath", "");
/*      */         }
/*      */         
/* 4271 */         downtimesummary.add(rows);
/*      */       }
/*      */       
/* 4274 */       String showstartDate = FormatUtil.formatDT(String.valueOf(startTime));
/* 4275 */       String showendDate = FormatUtil.formatDT(String.valueOf(endTime));
/* 4276 */       Properties props4 = ReportUtilities.calculateAvailabilityDetails(resourceid, startTime, endTime);
/*      */       
/* 4278 */       downtimeDetails.put("summary", props4);
/* 4279 */       downtimeDetails.put("downtimesummary", downtimesummary);
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
/* 4295 */       return downtimeDetails;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4285 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/* 4288 */       if (set != null) {
/*      */         try
/*      */         {
/* 4291 */           set.close();
/*      */         }
/*      */         catch (Exception ex) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward showXenResourcePoolApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 4300 */     ResultSet rs = null;
/* 4301 */     String moName = null;
/* 4302 */     String groupType = null;
/* 4303 */     String haid = request.getParameter("haid");
/* 4304 */     ((DynaActionForm)form).set("haid", haid);
/* 4305 */     if (request.isUserInRole("OPERATOR"))
/*      */     {
/* 4307 */       String role = request.getRemoteUser();
/* 4308 */       boolean mgviewAllowed = checkForMGAssignedtoUser(haid, request);
/* 4309 */       if (!mgviewAllowed)
/*      */       {
/* 4311 */         return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */       }
/*      */     }
/* 4314 */     request.setAttribute("Haid", haid);
/* 4315 */     request.setAttribute("MGUnderMaintenance", DataCollectionControllerUtil.getMGUnderMaintenance(haid));
/*      */     
/*      */ 
/* 4318 */     boolean isBusiness = false;
/* 4319 */     String selectedFunction = "am.webclient.dasboard.summarytab.title";
/* 4320 */     Cookie[] cookies = request.getCookies();
/* 4321 */     for (int i = 0; i < cookies.length; i++)
/*      */     {
/* 4323 */       if (cookies[i].getName().equals("am_mgview"))
/*      */       {
/* 4325 */         if (cookies[i].getValue().equals("business"))
/*      */         {
/* 4327 */           isBusiness = true;
/*      */         }
/* 4329 */         if (!cookies[i].getValue().equals("mypage"))
/*      */           break;
/* 4331 */         request.setAttribute("selectM", "mypageview"); break;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4338 */     String businessTabAppend = "";
/* 4339 */     if (isBusiness) {
/* 4340 */       request.setAttribute("selectM", "flashview");
/*      */     }
/* 4342 */     String masterXenServerIpAddress = "";
/* 4343 */     String masterHostUuid = "";
/* 4344 */     String hostMessage = "";
/* 4345 */     String query = "SELECT DISPLAYNAME , GROUPTYPE FROM AM_ManagedObject INNER JOIN AM_HOLISTICAPPLICATION ON HAID=RESOURCEID WHERE RESOURCEID='" + haid + "'";
/*      */     
/*      */     try
/*      */     {
/* 4349 */       Vector parentMos = new Vector();
/* 4350 */       this.mo.getParentMGs(parentMos, haid + "");
/* 4351 */       request.setAttribute("ParentMos", parentMos);
/* 4352 */       Hashtable resnameTable = new Hashtable();
/* 4353 */       String resourcenamequery = "select RESOURCEID,RESOURCENAME from AM_ManagedObject where " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", parentMos);
/* 4354 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(resourcenamequery);
/* 4355 */       while (rs.next())
/*      */       {
/* 4357 */         String resid = rs.getString("RESOURCEID");
/* 4358 */         String resname = rs.getString("RESOURCENAME");
/* 4359 */         resnameTable.put(resid, resname);
/*      */       }
/* 4361 */       request.setAttribute("resnameTable", resnameTable);
/* 4362 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 4364 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 4365 */       if (rs.next())
/*      */       {
/* 4367 */         moName = rs.getString("DISPLAYNAME");
/* 4368 */         groupType = rs.getString("GROUPTYPE");
/* 4369 */         ((DynaActionForm)form).set("name", moName);
/* 4370 */         ((DynaActionForm)form).set("grouptype", groupType);
/* 4371 */         request.setAttribute("grouptype", groupType);
/*      */       }
/* 4373 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 4375 */       query = "SELECT MASTER_IPADDRESS,MASTER_UUID FROM AM_RESOURCEPOOL_ARGS WHERE RESOURCEID='" + haid + "'";
/* 4376 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 4377 */       if (rs.next())
/*      */       {
/* 4379 */         masterXenServerIpAddress = rs.getString(1);
/* 4380 */         masterHostUuid = rs.getString(2);
/*      */       }
/*      */       
/* 4383 */       AMConnectionPool.closeStatement(rs);
/* 4384 */       rs = AMConnectionPool.executeQueryStmt("select ERROR_TYPE,ERROR_MESSAGE from AM_MONITOR_ERRORS where RESOURCEID=" + haid);
/* 4385 */       if (rs.next())
/*      */       {
/* 4387 */         String err_code = rs.getString("ERROR_TYPE");
/* 4388 */         if (err_code.equals("1"))
/*      */         {
/* 4390 */           hostMessage = rs.getString("ERROR_MESSAGE");
/*      */         }
/*      */       }
/*      */       
/* 4394 */       int xenServerHostCount = 0;
/* 4395 */       int xenServerVMCount = 0;
/* 4396 */       int xenServerStorageCount = 0;
/* 4397 */       int xenServerNetworkCount = 0;
/* 4398 */       String xenServerResourceID = "";
/* 4399 */       HashMap<String, ArrayList<HashMap<String, String>>> hostList = new HashMap();
/* 4400 */       HashMap<String, ArrayList<HashMap<String, String>>> poolSummayList = new HashMap();
/* 4401 */       HashMap<String, HashMap<String, String>> metricList = new HashMap();
/* 4402 */       HashMap<String, String> attributeMap = new HashMap();
/* 4403 */       HashMap<String, String> poolDetailsMap = new HashMap();
/* 4404 */       query = "SELECT RESOURCEID,RESOURCENAME,TYPE,DISPLAYNAME,DESCRIPTION FROM AM_ManagedObject, AM_PARENTCHILDMAPPER WHERE AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_PARENTCHILDMAPPER.PARENTID='" + haid + "'";
/* 4405 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 4406 */       while (rs.next())
/*      */       {
/* 4408 */         String resourceId = rs.getString(1);
/* 4409 */         String resourceName = rs.getString(2);
/* 4410 */         String type = rs.getString(3);
/* 4411 */         String displayName = rs.getString(4);
/* 4412 */         String description = rs.getString(5);
/* 4413 */         boolean isUnManaged = DBUtil.isUnManaged(resourceId);
/* 4414 */         HashMap<String, String> map = new HashMap();
/* 4415 */         map.put("RESOURCEID", resourceId);
/* 4416 */         map.put("RESOURCENAME", resourceName);
/* 4417 */         map.put("TYPE", type);
/* 4418 */         map.put("DISPLAYNAME", displayName);
/* 4419 */         map.put("DESCRIPTION", description);
/* 4420 */         map.put("ISUNMANAGED", isUnManaged + "");
/* 4421 */         if (resourceName.equals(masterXenServerIpAddress))
/*      */         {
/* 4423 */           ArrayList<HashMap<String, String>> list = new ArrayList();
/* 4424 */           list.add(map);
/* 4425 */           hostList.put("MASTER", list);
/*      */         }
/*      */         else
/*      */         {
/* 4429 */           ArrayList<HashMap<String, String>> list = (ArrayList)hostList.get("SLAVE");
/* 4430 */           if (list == null)
/*      */           {
/* 4432 */             list = new ArrayList();
/* 4433 */             hostList.put("SLAVE", list);
/*      */           }
/* 4435 */           list.add(map);
/*      */         }
/* 4437 */         if (xenServerResourceID.equals(""))
/*      */         {
/* 4439 */           xenServerResourceID = resourceId;
/*      */         }
/*      */         else
/*      */         {
/* 4443 */           xenServerResourceID = xenServerResourceID + "," + resourceId;
/*      */         }
/* 4445 */         xenServerHostCount += 1;
/*      */       }
/*      */       
/* 4448 */       if (!xenServerResourceID.equals(""))
/*      */       {
/*      */ 
/* 4451 */         HashMap<String, String> attributeDataTableMap = new HashMap();
/*      */         
/* 4453 */         String rawTableQuery = "SELECT DATATABLE,AM_ATTRIBUTES.ATTRIBUTEID,ATTRIBUTE FROM AM_ATTRIBUTES,AM_ATTRIBUTES_EXT WHERE AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID AND AM_ATTRIBUTES.ATTRIBUTEID IN (15028,15017,15005,15010,15003,15004, 15700, 15701, 15702, 15703, 15704, 15705, 15706, 15707)";
/* 4454 */         rs = AMConnectionPool.executeQueryStmt(rawTableQuery);
/* 4455 */         while (rs.next())
/*      */         {
/* 4457 */           String dataTable = rs.getString("DATATABLE");
/* 4458 */           String value_col = (String)attributeDataTableMap.get(dataTable);
/* 4459 */           attributeMap.put(rs.getString(2), rs.getString(3));
/* 4460 */           AMLog.debug("value_col=" + value_col + "\tdataTable=" + dataTable);
/* 4461 */           if (dataTable.equals("AM_CONFIGURATION_INFO"))
/*      */           {
/* 4463 */             if (value_col == null)
/*      */             {
/* 4465 */               value_col = rs.getString(2);
/*      */             }
/*      */             else
/*      */             {
/* 4469 */               value_col = value_col + "," + rs.getString(2);
/*      */             }
/* 4471 */             attributeDataTableMap.put(dataTable, value_col);
/*      */           }
/* 4473 */           else if (!dataTable.equals("AM_RESOURCEPOOL_METRICS"))
/*      */           {
/* 4475 */             if (value_col == null)
/*      */             {
/* 4477 */               value_col = DBQueryUtil.escapeColumn(rs.getString(3), rs.getString(2)) + " AS \"" + rs.getString(2) + "\"";
/*      */             }
/*      */             else
/*      */             {
/* 4481 */               value_col = value_col + "," + DBQueryUtil.escapeColumn(rs.getString(3), rs.getString(2)) + " AS \"" + rs.getString(2) + "\"";
/*      */             }
/* 4483 */             attributeDataTableMap.put(dataTable, value_col);
/*      */           }
/*      */         }
/* 4486 */         rs.close();
/* 4487 */         for (String archiveTableName : attributeDataTableMap.keySet())
/*      */         {
/* 4489 */           String columns = (String)attributeDataTableMap.get(archiveTableName);
/* 4490 */           if (archiveTableName.equals("AM_CONFIGURATION_INFO"))
/*      */           {
/* 4492 */             query = "SELECT RESOURCEID, ATTRIBUTEID, CONFVALUE FROM " + archiveTableName + " WHERE RESOURCEID IN (" + xenServerResourceID + ") AND ATTRIBUTEID IN (" + columns + ") ORDER BY COLLECTIONTIME DESC";
/* 4493 */             AMLog.debug("DATA QUERY ::" + query);
/* 4494 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 4495 */             while (rs.next())
/*      */             {
/* 4497 */               String resourceId = rs.getString(1);
/* 4498 */               HashMap<String, String> map = (HashMap)metricList.get(resourceId);
/* 4499 */               if (map == null)
/*      */               {
/* 4501 */                 map = new HashMap();
/* 4502 */                 metricList.put(resourceId, map);
/*      */               }
/* 4504 */               String key = (String)attributeMap.get(rs.getString(2));
/* 4505 */               if (!map.containsKey(key))
/*      */               {
/* 4507 */                 map.put(key, rs.getString(3));
/*      */               }
/*      */             }
/* 4510 */             rs.close();
/*      */           }
/*      */           else
/*      */           {
/* 4514 */             query = "SELECT RESOURCEID, " + columns + ", COLLECTIONTIME FROM " + archiveTableName + " WHERE RESOURCEID IN (" + xenServerResourceID + ") ORDER BY COLLECTIONTIME DESC";
/* 4515 */             AMLog.debug("DATA QUERY ::" + query);
/* 4516 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 4517 */             ResultSetMetaData rsm = rs.getMetaData();
/* 4518 */             int columnCount = rsm.getColumnCount();
/* 4519 */             while (rs.next())
/*      */             {
/* 4521 */               String resourceId = rs.getString(1);
/* 4522 */               HashMap<String, String> map = (HashMap)metricList.get(resourceId);
/* 4523 */               if (map == null)
/*      */               {
/* 4525 */                 map = new HashMap();
/* 4526 */                 metricList.put(resourceId, map);
/*      */               }
/* 4528 */               for (int i = 2; i < columnCount; i++)
/*      */               {
/* 4530 */                 String key = (String)attributeMap.get(rsm.getColumnName(i));
/* 4531 */                 if (!map.containsKey(key))
/*      */                 {
/* 4533 */                   if ((key.equals("NumofVMs")) || (key.equals("NumofCPUCore")))
/*      */                   {
/* 4535 */                     map.put(key, rs.getInt(i) + "");
/*      */                   }
/*      */                   else
/*      */                   {
/* 4539 */                     map.put(key, rs.getString(i));
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 4544 */             rs.close();
/*      */           }
/*      */         }
/* 4547 */         query = "SELECT RESOURCEID,RESOURCENAME,TYPE,DISPLAYNAME,DESCRIPTION FROM AM_ManagedObject, AM_PARENTCHILDMAPPER WHERE AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_PARENTCHILDMAPPER.PARENTID IN (" + xenServerResourceID + ") AND AM_ManagedObject.TYPE IN ('XenServerVM','XenServerHost_Storage Details_ROW','XenServerHost_Network Utilization_ROW')";
/* 4548 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 4549 */         while (rs.next())
/*      */         {
/* 4551 */           String resourceId = rs.getString(1);
/* 4552 */           String resourceName = rs.getString(2);
/* 4553 */           String type = rs.getString(3);
/* 4554 */           String displayName = rs.getString(4);
/* 4555 */           String description = rs.getString(5);
/* 4556 */           HashMap<String, String> map = new HashMap();
/* 4557 */           map.put("RESOURCEID", resourceId);
/* 4558 */           map.put("RESOURCENAME", resourceName);
/* 4559 */           map.put("TYPE", type);
/* 4560 */           map.put("DISPLAYNAME", displayName);
/* 4561 */           map.put("DESCRIPTION", description);
/* 4562 */           if (type.equals("XenServerVM"))
/*      */           {
/* 4564 */             ArrayList<HashMap<String, String>> list = (ArrayList)poolSummayList.get("VM");
/* 4565 */             if (list == null)
/*      */             {
/* 4567 */               list = new ArrayList();
/* 4568 */               poolSummayList.put("VM", list);
/*      */             }
/* 4570 */             list.add(map);
/* 4571 */             xenServerVMCount += 1;
/*      */           }
/* 4573 */           else if (type.equals("XenServerHost_Storage Details_ROW"))
/*      */           {
/* 4575 */             ArrayList<HashMap<String, String>> list = (ArrayList)poolSummayList.get("STORAGE");
/* 4576 */             if (list == null)
/*      */             {
/* 4578 */               list = new ArrayList();
/* 4579 */               poolSummayList.put("STORAGE", list);
/*      */             }
/* 4581 */             list.add(map);
/* 4582 */             xenServerStorageCount += 1;
/*      */           }
/* 4584 */           else if (type.equals("XenServerHost_Network Utilization_ROW"))
/*      */           {
/* 4586 */             ArrayList<HashMap<String, String>> list = (ArrayList)poolSummayList.get("NETWORK");
/* 4587 */             if (list == null)
/*      */             {
/* 4589 */               list = new ArrayList();
/* 4590 */               poolSummayList.put("NETWORK", list);
/*      */             }
/* 4592 */             list.add(map);
/* 4593 */             xenServerNetworkCount += 1;
/*      */           }
/*      */         }
/*      */       }
/* 4597 */       query = "SELECT ATTRIBUTEID, VALUE, COLLECTIONTIME FROM AM_RESOURCEPOOL_METRICS WHERE RESOURCEID='" + haid + "' ORDER BY COLLECTIONTIME DESC";
/* 4598 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 4599 */       while (rs.next())
/*      */       {
/* 4601 */         String key = (String)attributeMap.get(rs.getString(1));
/* 4602 */         if (!poolDetailsMap.containsKey(key))
/*      */         {
/* 4604 */           poolDetailsMap.put(key, rs.getString(2));
/*      */         }
/*      */       }
/* 4607 */       rs.close();
/*      */       
/* 4609 */       query = "SELECT ATTRIBUTEID, CONFVALUE FROM AM_CONFIGURATION_INFO WHERE RESOURCEID='" + haid + "' AND ATTRIBUTEID IN (15704, 15705, 15706, 15707)";
/* 4610 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 4611 */       while (rs.next())
/*      */       {
/* 4613 */         String key = (String)attributeMap.get(rs.getString(1));
/* 4614 */         if (!poolDetailsMap.containsKey(key))
/*      */         {
/* 4616 */           poolDetailsMap.put(key, rs.getString(2));
/*      */         }
/*      */       }
/* 4619 */       rs.close();
/* 4620 */       AMLog.debug("poolDetailsMap:" + poolDetailsMap);
/*      */       
/* 4622 */       String message = "";
/* 4623 */       int firstIndex = -1;
/* 4624 */       if ((hostMessage != null) && (!hostMessage.equals("")) && ((firstIndex = hostMessage.indexOf(",")) != -1))
/*      */       {
/* 4626 */         String key = hostMessage.substring(0, firstIndex);
/* 4627 */         int secondIndex = hostMessage.indexOf(",", firstIndex + 1);
/* 4628 */         if (secondIndex != -1)
/*      */         {
/* 4630 */           String ipAddress = hostMessage.substring(key.length() + 1, secondIndex);
/* 4631 */           String displayName = hostMessage.substring(key.length() + ipAddress.length() + 2, hostMessage.length());
/* 4632 */           message = FormatUtil.getString(key, new String[] { ipAddress, displayName });
/*      */         }
/*      */         else
/*      */         {
/* 4636 */           String ipAddress = hostMessage.substring(key.length() + 1, hostMessage.length());
/* 4637 */           message = FormatUtil.getString(key, new String[] { ipAddress });
/*      */         }
/*      */       }
/* 4640 */       else if ((hostMessage != null) && (!hostMessage.equals("")))
/*      */       {
/* 4642 */         message = FormatUtil.getString(hostMessage);
/*      */       }
/* 4644 */       if (!message.equals(""))
/*      */       {
/* 4646 */         request.setAttribute("message", message);
/*      */       }
/* 4648 */       request.setAttribute("HostCount", Integer.valueOf(xenServerHostCount));
/* 4649 */       request.setAttribute("VMCount", Integer.valueOf(xenServerVMCount));
/* 4650 */       request.setAttribute("StorageCount", Integer.valueOf(xenServerStorageCount));
/* 4651 */       request.setAttribute("NetworkCount", Integer.valueOf(xenServerNetworkCount));
/* 4652 */       request.setAttribute("XenHostDetails", hostList);
/* 4653 */       request.setAttribute("PoolSummaryDetails", poolSummayList);
/* 4654 */       request.setAttribute("MetricDetails", metricList);
/* 4655 */       request.setAttribute("PoolMetricDetails", poolDetailsMap);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4659 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4663 */       if (rs != null)
/*      */       {
/* 4665 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 4669 */     if (((request.getParameter("selectM") != null) && (request.getParameter("selectM").equals("flashview"))) || (isBusiness))
/*      */     {
/* 4671 */       putFlashProps(haid, request);
/*      */     }
/* 4673 */     return new ActionForward("/jsp/XenPoolApplication.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward showVCenterApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 4679 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 4682 */       String haid = request.getParameter("haid");
/* 4683 */       ((DynaActionForm)form).set("haid", haid);
/*      */       
/*      */ 
/* 4686 */       if (request.isUserInRole("OPERATOR"))
/*      */       {
/* 4688 */         String role = request.getRemoteUser();
/* 4689 */         boolean mgviewAllowed = checkForMGAssignedtoUser(haid, request);
/* 4690 */         if (!mgviewAllowed)
/*      */         {
/* 4692 */           return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */         }
/*      */       }
/*      */       
/* 4696 */       String groupType = "1";
/* 4697 */       String moName = null;
/* 4698 */       ResultSet disprs = null;
/* 4699 */       request.setAttribute("MGUnderMaintenance", DataCollectionControllerUtil.getMGUnderMaintenance(haid));
/*      */       try
/*      */       {
/* 4702 */         disprs = AMConnectionPool.executeQueryStmt("select DISPLAYNAME , GROUPTYPE  from AM_ManagedObject inner join AM_HOLISTICAPPLICATION on HAID=RESOURCEID where RESOURCEID=" + haid);
/* 4703 */         if (disprs.next())
/*      */         {
/* 4705 */           ((DynaActionForm)form).set("name", disprs.getString("DISPLAYNAME"));
/* 4706 */           ((DynaActionForm)form).set("grouptype", disprs.getString("GROUPTYPE"));
/* 4707 */           groupType = disprs.getString("GROUPTYPE");
/* 4708 */           moName = disprs.getString("DISPLAYNAME");
/* 4709 */           request.setAttribute("grouptype", disprs.getString("GROUPTYPE"));
/*      */         }
/*      */       }
/*      */       finally {
/* 4713 */         if (disprs != null)
/*      */         {
/* 4715 */           disprs.close();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4720 */       Vector parentMos = new Vector();
/* 4721 */       this.mo.getParentMGs(parentMos, haid + "");
/* 4722 */       request.setAttribute("ParentMos", parentMos);
/* 4723 */       Hashtable resnameTable = new Hashtable();
/* 4724 */       ResultSet rs1 = null;
/*      */       try {
/* 4726 */         String resourcenamequery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", parentMos);
/*      */         
/* 4728 */         AMConnectionPool.getInstance();rs1 = AMConnectionPool.executeQueryStmt(resourcenamequery);
/*      */         
/* 4730 */         while (rs1.next())
/*      */         {
/* 4732 */           String resid = rs1.getString("RESOURCEID");
/* 4733 */           String resname = rs1.getString("DISPLAYNAME");
/* 4734 */           resnameTable.put(resid, resname);
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 4740 */         ex.printStackTrace();
/*      */       }
/*      */       finally {
/* 4743 */         AMConnectionPool.closeStatement(rs1);
/*      */       }
/* 4745 */       request.setAttribute("resnameTable", resnameTable);
/*      */       
/* 4747 */       boolean isBusiness = false;
/* 4748 */       String selectedFunction = "am.webclient.dasboard.summarytab.title";
/* 4749 */       Cookie[] cookies = request.getCookies();
/* 4750 */       for (int i = 0; i < cookies.length; i++)
/*      */       {
/* 4752 */         if (cookies[i].getName().equals("am_mgview"))
/*      */         {
/* 4754 */           if (cookies[i].getValue().equals("business"))
/*      */           {
/* 4756 */             isBusiness = true;
/*      */           }
/* 4758 */           if (!cookies[i].getValue().equals("mypage"))
/*      */             break;
/* 4760 */           request.setAttribute("selectM", "mypageview"); break;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4766 */       String businessTabAppend = "";
/* 4767 */       if (isBusiness) {
/* 4768 */         request.setAttribute("selectM", "flashview");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4773 */       ResultSet rs = null;
/* 4774 */       Hashtable dcClusterMapper = new Hashtable();
/* 4775 */       Hashtable clusterESXMapper = new Hashtable();
/* 4776 */       Hashtable clusterRPMapper = new Hashtable();
/* 4777 */       ArrayList dcList = new ArrayList();
/* 4778 */       Vector parentIDs = new Vector();
/* 4779 */       Vector dcResids = new Vector();
/* 4780 */       Vector clusterResids = new Vector();
/* 4781 */       Vector rpoolResids = new Vector();
/* 4782 */       Vector esxResids = new Vector();
/* 4783 */       Vector vmResids = new Vector();
/* 4784 */       Vector datastoreNames = new Vector();
/* 4785 */       Vector nicNames = new Vector();
/* 4786 */       String dcQuery = "select CHILDID,DISPLAYNAME from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_ManagedObject where PARENTID=" + haid + " and HAID=CHILDID and RESOURCEID=CHILDID and GROUPTYPE=1009 order by DISPLAYNAME";
/*      */       try
/*      */       {
/* 4789 */         if (!"1009".equals(groupType))
/*      */         {
/* 4791 */           rs = AMConnectionPool.executeQueryStmt(dcQuery);
/* 4792 */           while (rs.next())
/*      */           {
/* 4794 */             dcResids.add(rs.getString("CHILDID"));
/* 4795 */             Hashtable dcDetails = new Hashtable();
/* 4796 */             dcDetails.put("RESID", rs.getString("CHILDID"));
/* 4797 */             dcDetails.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 4798 */             dcList.add(dcDetails);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 4803 */         dcResids.add(haid);
/*      */         
/* 4805 */         String clusterQuery = "select CHILDID,PARENTID,DISPLAYNAME from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_ManagedObject where " + ManagedApplication.getCondition("PARENTID", dcResids) + " and HAID=CHILDID and RESOURCEID=CHILDID and GROUPTYPE=1010  order by DISPLAYNAME";
/*      */         
/* 4807 */         if (!"1010".equals(groupType))
/*      */         {
/* 4809 */           rs = AMConnectionPool.executeQueryStmt(clusterQuery);
/* 4810 */           while (rs.next())
/*      */           {
/* 4812 */             clusterResids.add(rs.getString("CHILDID"));
/*      */             
/* 4814 */             dcClusterMapper.put(rs.getString("CHILDID"), rs.getString("PARENTID"));
/* 4815 */             if ("1009".equals(groupType))
/*      */             {
/* 4817 */               Hashtable dcDetails = new Hashtable();
/* 4818 */               dcDetails.put("RESID", rs.getString("CHILDID"));
/* 4819 */               dcDetails.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 4820 */               dcList.add(dcDetails);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 4826 */         clusterResids.add(haid);
/*      */         
/* 4828 */         String rpQuery = "select CHILDID,PARENTID,DISPLAYNAME from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_ManagedObject where " + ManagedApplication.getCondition("PARENTID", clusterResids) + " and HAID=CHILDID and RESOURCEID=CHILDID and GROUPTYPE=1012  order by DISPLAYNAME";
/*      */         
/* 4830 */         rs = AMConnectionPool.executeQueryStmt(rpQuery);
/* 4831 */         while (rs.next())
/*      */         {
/* 4833 */           rpoolResids.add(rs.getString("CHILDID"));
/*      */           
/* 4835 */           if ("1010".equals(groupType))
/*      */           {
/* 4837 */             Hashtable dcDetails = new Hashtable();
/* 4838 */             dcDetails.put("RESID", rs.getString("CHILDID"));
/* 4839 */             dcDetails.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 4840 */             dcList.add(dcDetails);
/*      */           }
/*      */         }
/*      */         
/* 4844 */         String rpinRPQuery = "select CHILDID,PARENTID,DISPLAYNAME from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_ManagedObject where PARENTID=" + haid + " and HAID=CHILDID and RESOURCEID=CHILDID and GROUPTYPE=1012  order by DISPLAYNAME";
/* 4845 */         if ("1012".equals(groupType))
/*      */         {
/* 4847 */           ArrayList childRPList = new ArrayList();
/* 4848 */           rs = AMConnectionPool.executeQueryStmt(rpinRPQuery);
/* 4849 */           while (rs.next())
/*      */           {
/* 4851 */             rpoolResids.add(rs.getString("CHILDID"));
/*      */             
/* 4853 */             Hashtable dcDetails = new Hashtable();
/* 4854 */             dcDetails.put("RESID", rs.getString("CHILDID"));
/* 4855 */             dcDetails.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 4856 */             childRPList.add(dcDetails);
/*      */           }
/*      */           
/* 4859 */           if ((childRPList != null) && (childRPList.size() > 0))
/*      */           {
/* 4861 */             request.setAttribute("childRPList", childRPList);
/*      */           }
/*      */         }
/*      */         
/* 4865 */         if ("1012".equals(groupType))
/*      */         {
/* 4867 */           String vmQuery = "select pcm.CHILDID,pcm.PARENTID,mo.DISPLAYNAME from AM_ManagedObject as mo ,AM_PARENTCHILDMAPPER pcm where pcm.PARENTID=" + haid + " and pcm.CHILDID=mo.RESOURCEID and mo.TYPE='VirtualMachine' order by mo.DISPLAYNAME";
/*      */           
/* 4869 */           rs = AMConnectionPool.executeQueryStmt(vmQuery);
/* 4870 */           while (rs.next())
/*      */           {
/*      */ 
/*      */ 
/* 4874 */             vmResids.add(rs.getString("CHILDID"));
/* 4875 */             Hashtable dcDetails = new Hashtable();
/* 4876 */             dcDetails.put("RESID", rs.getString("CHILDID"));
/* 4877 */             dcDetails.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 4878 */             dcList.add(dcDetails);
/*      */           }
/*      */         }
/*      */         
/* 4882 */         parentIDs.add(haid);
/* 4883 */         parentIDs.addAll(dcResids);
/* 4884 */         parentIDs.addAll(clusterResids);
/* 4885 */         if ("1010".equals(groupType))
/*      */         {
/*      */ 
/* 4888 */           String dcParentQuery = "select PARENTID,DISPLAYNAME from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_ManagedObject where CHILDID=" + haid + " and HAID=PARENTID and RESOURCEID=PARENTID and GROUPTYPE=1009 order by DISPLAYNAME";
/*      */           
/* 4890 */           rs = AMConnectionPool.executeQueryStmt(dcParentQuery);
/* 4891 */           while (rs.next())
/*      */           {
/* 4893 */             dcClusterMapper.put(haid, rs.getString("PARENTID"));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4901 */         String esxQuery = "select CHILDID,PARENTID from AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ManagedApplication.getCondition("PARENTID", parentIDs) + " and RESOURCEID=CHILDID and TYPE='VMWare ESX/ESXi'";
/* 4902 */         rs = AMConnectionPool.executeQueryStmt(esxQuery);
/* 4903 */         while (rs.next())
/*      */         {
/* 4905 */           esxResids.add(rs.getString("CHILDID"));
/* 4906 */           clusterESXMapper.put(rs.getString("CHILDID"), rs.getString("PARENTID"));
/*      */         }
/* 4908 */         if (!"1012".equals(groupType))
/*      */         {
/* 4910 */           String vmQuery = "select CHILDID from AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ManagedApplication.getCondition("PARENTID", esxResids) + " and RESOURCEID=CHILDID and TYPE='VirtualMachine'";
/* 4911 */           rs = AMConnectionPool.executeQueryStmt(vmQuery);
/* 4912 */           while (rs.next())
/*      */           {
/* 4914 */             vmResids.add(rs.getString("CHILDID"));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 4919 */         String datastoreQuery = "select CONFVALUE as VALUE from AM_PARENTCHILDMAPPER, AM_ManagedObject,AM_CONFIGURATION_INFO as coinfo where " + ManagedApplication.getCondition("PARENTID", esxResids) + " and AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid and AM_ManagedObject.resourceid=coinfo.RESOURCEID and coinfo.ATTRIBUTEID=7732";
/* 4920 */         rs = AMConnectionPool.executeQueryStmt(datastoreQuery);
/* 4921 */         while (rs.next())
/*      */         {
/* 4923 */           if (!datastoreNames.contains(rs.getString("VALUE")))
/*      */           {
/* 4925 */             datastoreNames.add(rs.getString("VALUE"));
/*      */           }
/*      */         }
/*      */         
/* 4929 */         String nicQuery = "select CONFVALUE as VALUE from AM_PARENTCHILDMAPPER, AM_ManagedObject,AM_CONFIGURATION_INFO as coinfo where " + ManagedApplication.getCondition("PARENTID", esxResids) + " and AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid and AM_ManagedObject.resourceid=coinfo.RESOURCEID and coinfo.ATTRIBUTEID=7742";
/* 4930 */         rs = AMConnectionPool.executeQueryStmt(nicQuery);
/* 4931 */         while (rs.next())
/*      */         {
/* 4933 */           nicNames.add(rs.getString("VALUE"));
/*      */         }
/*      */         
/* 4936 */         request.setAttribute("DCCount", Integer.valueOf(dcResids.size()));
/* 4937 */         request.setAttribute("ClusterCount", Integer.valueOf(clusterResids.size()));
/* 4938 */         request.setAttribute("ResourcePoolCount", Integer.valueOf(rpoolResids.size()));
/* 4939 */         request.setAttribute("ESXCount", Integer.valueOf(esxResids.size()));
/* 4940 */         request.setAttribute("VMCount", Integer.valueOf(vmResids.size()));
/* 4941 */         request.setAttribute("DatastoreCount", Integer.valueOf(datastoreNames.size()));
/* 4942 */         request.setAttribute("NICCount", Integer.valueOf(nicNames.size()));
/* 4943 */         request.setAttribute("DCList", dcList);
/*      */         
/*      */ 
/*      */ 
/* 4947 */         if (("1010".equals(groupType)) && (dcClusterMapper.get(haid) != null))
/*      */         {
/* 4949 */           parentIDs.add(dcClusterMapper.get(haid));
/*      */         }
/* 4951 */         parentIDs.addAll(esxResids);
/* 4952 */         ArrayList esxList = new ArrayList();
/* 4953 */         Hashtable moResidHash = new Hashtable();
/* 4954 */         String moDisplayNameQuery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where " + ManagedApplication.getCondition("RESOURCEID", parentIDs);
/* 4955 */         rs = AMConnectionPool.executeQueryStmt(moDisplayNameQuery);
/* 4956 */         while (rs.next())
/*      */         {
/* 4958 */           moResidHash.put(rs.getString("RESOURCEID"), rs.getString("DISPLAYNAME"));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 4963 */         String rawTableQuery = "select DATATABLE from AM_ATTRIBUTES_EXT where ATTRIBUTEID in (7520,7522,7523,7524)";
/* 4964 */         String dataTable = null;
/* 4965 */         Hashtable esxMetHash = new Hashtable();
/* 4966 */         String status_sep = "#";
/*      */         try {
/* 4968 */           rs = AMConnectionPool.executeQueryStmt(rawTableQuery);
/* 4969 */           if (rs.next())
/*      */           {
/* 4971 */             dataTable = rs.getString("DATATABLE");
/*      */           }
/* 4973 */           if (dataTable != null)
/*      */           {
/* 4975 */             String dataQuery = "select RESOURCEID," + DBQueryUtil.escapeColumn("CPUUtil", "7520") + "," + DBQueryUtil.escapeColumn("MemUtil", "7522") + "," + DBQueryUtil.escapeColumn("DiskUsage", "7523") + "," + DBQueryUtil.escapeColumn("NetUsage", "7524") + ",COLLECTIONTIME from " + dataTable + " where  " + ManagedApplication.getCondition("RESOURCEID", esxResids) + "order by COLLECTIONTIME desc";
/*      */             
/* 4977 */             rs = AMConnectionPool.executeQueryStmt(dataQuery);
/* 4978 */             while (rs.next())
/*      */             {
/* 4980 */               String key = rs.getString("RESOURCEID") + status_sep + "7520";
/* 4981 */               if ((esxMetHash.get(key) == null) && (rs.getString("CPUUtil") != null))
/*      */               {
/* 4983 */                 esxMetHash.put(key, rs.getString("CPUUtil"));
/*      */               }
/* 4985 */               key = rs.getString("RESOURCEID") + status_sep + "7522";
/* 4986 */               if ((esxMetHash.get(key) == null) && (rs.getString("MemUtil") != null))
/*      */               {
/* 4988 */                 esxMetHash.put(key, rs.getString("MemUtil"));
/*      */               }
/* 4990 */               key = rs.getString("RESOURCEID") + status_sep + "7523";
/* 4991 */               if ((esxMetHash.get(key) == null) && (rs.getString("DiskUsage") != null))
/*      */               {
/* 4993 */                 esxMetHash.put(key, rs.getString("DiskUsage"));
/*      */               }
/* 4995 */               key = rs.getString("RESOURCEID") + status_sep + "7524";
/* 4996 */               if ((esxMetHash.get(key) == null) && (rs.getString("NetUsage") != null))
/*      */               {
/* 4998 */                 esxMetHash.put(key, rs.getString("NetUsage"));
/*      */               }
/*      */               
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/* 5007 */           ee.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/* 5011 */         for (int i = 0; i < esxResids.size(); i++)
/*      */         {
/* 5013 */           Hashtable singlerow = new Hashtable();
/* 5014 */           singlerow.put("RESOURCEID", esxResids.get(i));
/* 5015 */           singlerow.put("DISPLAYNAME", moResidHash.get(esxResids.get(i)));
/*      */           
/* 5017 */           if (clusterESXMapper.get(esxResids.get(i)) != null)
/*      */           {
/* 5019 */             String parentCluster = (String)clusterESXMapper.get(esxResids.get(i));
/* 5020 */             if (dcClusterMapper.get(parentCluster) != null)
/*      */             {
/* 5022 */               singlerow.put("DATACENTERID", dcClusterMapper.get(parentCluster));
/* 5023 */               singlerow.put("DATACENTER", moResidHash.get(dcClusterMapper.get(parentCluster)));
/* 5024 */               singlerow.put("CLUSTERID", parentCluster);
/* 5025 */               singlerow.put("CLUSTER", moResidHash.get(parentCluster));
/*      */             }
/* 5027 */             else if (dcResids.contains(parentCluster))
/*      */             {
/* 5029 */               singlerow.put("DATACENTERID", parentCluster);
/* 5030 */               singlerow.put("DATACENTER", moResidHash.get(parentCluster));
/* 5031 */               singlerow.put("CLUSTERID", "-");
/* 5032 */               singlerow.put("CLUSTER", "-");
/*      */             }
/*      */             else
/*      */             {
/* 5036 */               singlerow.put("DATACENTERID", "-");
/* 5037 */               singlerow.put("DATACENTER", "-");
/* 5038 */               singlerow.put("CLUSTERID", parentCluster);
/* 5039 */               singlerow.put("CLUSTER", moResidHash.get(parentCluster));
/*      */             }
/*      */             
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5046 */             singlerow.put("CLUSTERID", "-");
/* 5047 */             singlerow.put("DATACENTERID", "-");
/* 5048 */             singlerow.put("CLUSTER", "-");
/* 5049 */             singlerow.put("DATACENTER", "-");
/*      */           }
/*      */           
/* 5052 */           if (esxMetHash.get(esxResids.get(i) + status_sep + "7520") != null)
/*      */           {
/* 5054 */             singlerow.put("ESXCPU", esxMetHash.get(esxResids.get(i) + status_sep + "7520"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5059 */             singlerow.put("ESXCPU", "-");
/*      */           }
/*      */           
/* 5062 */           if (esxMetHash.get(esxResids.get(i) + status_sep + "7522") != null)
/*      */           {
/* 5064 */             singlerow.put("ESXMEM", esxMetHash.get(esxResids.get(i) + status_sep + "7522"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5069 */             singlerow.put("ESXMEM", "-");
/*      */           }
/*      */           
/* 5072 */           if (esxMetHash.get(esxResids.get(i) + status_sep + "7523") != null)
/*      */           {
/* 5074 */             singlerow.put("ESXDISK", esxMetHash.get(esxResids.get(i) + status_sep + "7523"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5079 */             singlerow.put("ESXDISK", "-");
/*      */           }
/*      */           
/* 5082 */           if (esxMetHash.get(esxResids.get(i) + status_sep + "7524") != null)
/*      */           {
/* 5084 */             singlerow.put("ESXNET", esxMetHash.get(esxResids.get(i) + status_sep + "7524"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5089 */             singlerow.put("ESXNET", "-");
/*      */           }
/* 5091 */           singlerow.put("ISUNMANAGED", Boolean.valueOf(DBUtil.isUnManaged((String)esxResids.get(i))));
/* 5092 */           esxList.add(singlerow);
/*      */         }
/*      */         
/* 5095 */         request.setAttribute("ESXList", esxList);
/*      */         
/*      */ 
/* 5098 */         Hashtable clusterMetHash = new Hashtable();
/* 5099 */         ArrayList clusterList = new ArrayList();
/* 5100 */         String dataQuery = "select RESOURCEID,ATTRIBUTEID,VALUE,COLLECTIONTIME from AM_VMWARECLUSTER_METRICS where ATTRIBUTEID in (7930,7932,7916,7917,7936) and " + ManagedApplication.getCondition("RESOURCEID", clusterResids) + " and VALUE != -1 order by COLLECTIONTIME desc";
/*      */         
/* 5102 */         rs = AMConnectionPool.executeQueryStmt(dataQuery);
/* 5103 */         while (rs.next())
/*      */         {
/* 5105 */           String key = rs.getString("RESOURCEID") + status_sep + rs.getString("ATTRIBUTEID");
/* 5106 */           if ((clusterMetHash.get(key) == null) && (rs.getString("VALUE") != null))
/*      */           {
/* 5108 */             clusterMetHash.put(key, rs.getString("VALUE"));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 5113 */         for (int i = 0; i < clusterResids.size(); i++)
/*      */         {
/* 5115 */           Hashtable singlerow = new Hashtable();
/* 5116 */           singlerow.put("RESOURCEID", clusterResids.get(i));
/* 5117 */           singlerow.put("DISPLAYNAME", moResidHash.get(clusterResids.get(i)));
/*      */           
/* 5119 */           if (dcClusterMapper.get(clusterResids.get(i)) != null)
/*      */           {
/* 5121 */             singlerow.put("DATACENTERID", dcClusterMapper.get(clusterResids.get(i)));
/* 5122 */             singlerow.put("DATACENTER", moResidHash.get(dcClusterMapper.get(clusterResids.get(i))));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5127 */             singlerow.put("DATACENTERID", "-");
/* 5128 */             singlerow.put("DATACENTER", "-");
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 5133 */           if ((clusterMetHash.get(clusterResids.get(i) + status_sep + "7930") != null) && (!"-1".equals((String)clusterMetHash.get(clusterResids.get(i) + status_sep + "7930"))))
/*      */           {
/* 5135 */             singlerow.put("ClusterCPUUtil", clusterMetHash.get(clusterResids.get(i) + status_sep + "7930"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5140 */             singlerow.put("ClusterCPUUtil", "-");
/*      */           }
/*      */           
/* 5143 */           if ((clusterMetHash.get(clusterResids.get(i) + status_sep + "7932") != null) && (!"-1".equals((String)clusterMetHash.get(clusterResids.get(i) + status_sep + "7932"))))
/*      */           {
/* 5145 */             singlerow.put("ClusterMEMUtil", clusterMetHash.get(clusterResids.get(i) + status_sep + "7932"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5150 */             singlerow.put("ClusterMEMUtil", "-");
/*      */           }
/*      */           
/* 5153 */           if ((clusterMetHash.get(clusterResids.get(i) + status_sep + "7916") != null) && (!"-1".equals((String)clusterMetHash.get(clusterResids.get(i) + status_sep + "7916"))))
/*      */           {
/* 5155 */             singlerow.put("ClusterCPUCores", clusterMetHash.get(clusterResids.get(i) + status_sep + "7916"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5160 */             singlerow.put("ClusterCPUCores", "-");
/*      */           }
/*      */           
/* 5163 */           if ((clusterMetHash.get(clusterResids.get(i) + status_sep + "7917") != null) && (!"-1".equals((String)clusterMetHash.get(clusterResids.get(i) + status_sep + "7917"))))
/*      */           {
/* 5165 */             singlerow.put("ClusterCPUThreads", clusterMetHash.get(clusterResids.get(i) + status_sep + "7917"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5170 */             singlerow.put("ClusterCPUThreads", "-");
/*      */           }
/* 5172 */           if ((clusterMetHash.get(clusterResids.get(i) + status_sep + "7936") != null) && (!"-1".equals((String)clusterMetHash.get(clusterResids.get(i) + status_sep + "7936"))))
/*      */           {
/* 5174 */             singlerow.put("ClusterFailover", clusterMetHash.get(clusterResids.get(i) + status_sep + "7936"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 5179 */             singlerow.put("ClusterFailover", "-");
/*      */           }
/* 5181 */           clusterList.add(singlerow);
/*      */         }
/*      */         
/* 5184 */         request.setAttribute("ClusterList", clusterList);
/*      */         
/*      */ 
/* 5187 */         Hashtable clusterDetailHash = new Hashtable();
/* 5188 */         if ("1010".equals(groupType))
/*      */         {
/* 5190 */           String clusterDataQuery = "select RESOURCEID,AM_ATTRIBUTES.ATTRIBUTEID,ATTRIBUTE,VALUE,COLLECTIONTIME from AM_VMWARECLUSTER_METRICS,AM_ATTRIBUTES where  RESOURCEID=" + haid + " and AM_ATTRIBUTES.ATTRIBUTEID=AM_VMWARECLUSTER_METRICS.ATTRIBUTEID order by COLLECTIONTIME desc";
/*      */           
/* 5192 */           rs = AMConnectionPool.executeQueryStmt(clusterDataQuery);
/* 5193 */           while (rs.next())
/*      */           {
/* 5195 */             String key = rs.getString("ATTRIBUTE");
/* 5196 */             if ((clusterDetailHash.get(key) == null) && (rs.getString("VALUE") != null))
/*      */             {
/*      */ 
/* 5199 */               if ("-1".equals(rs.getString("VALUE")))
/*      */               {
/* 5201 */                 clusterDetailHash.put(key, "-");
/*      */               }
/*      */               else
/*      */               {
/* 5205 */                 clusterDetailHash.put(key, rs.getString("VALUE"));
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 5213 */           request.setAttribute("ClusterDetails", clusterDetailHash);
/*      */           
/*      */ 
/*      */ 
/* 5217 */           ArrayList migrationList = GetDrsMigration.getMigrationReport((String)parentMos.get(parentMos.size() - 1), moName);
/* 5218 */           request.setAttribute("MigrationList", migrationList);
/*      */         }
/*      */         
/*      */ 
/* 5222 */         Hashtable rpDetailHash = new Hashtable();
/* 5223 */         if ("1012".equals(groupType))
/*      */         {
/* 5225 */           String rpDataQuery = "select RESOURCEID,AM_ATTRIBUTES.ATTRIBUTEID,ATTRIBUTE,VALUE,COLLECTIONTIME from AM_VMWARERESOURCEPOOL_METRICS,AM_ATTRIBUTES where  RESOURCEID=" + haid + " and AM_ATTRIBUTES.ATTRIBUTEID=AM_VMWARERESOURCEPOOL_METRICS.ATTRIBUTEID order by COLLECTIONTIME desc";
/*      */           
/* 5227 */           rs = AMConnectionPool.executeQueryStmt(rpDataQuery);
/* 5228 */           while (rs.next())
/*      */           {
/* 5230 */             String key = rs.getString("ATTRIBUTE");
/* 5231 */             if ((rpDetailHash.get(key) == null) && (rs.getString("VALUE") != null))
/*      */             {
/*      */ 
/* 5234 */               if ("-1".equals(rs.getString("VALUE")))
/*      */               {
/* 5236 */                 rpDetailHash.put(key, "-");
/*      */               }
/*      */               else
/*      */               {
/* 5240 */                 rpDetailHash.put(key, rs.getString("VALUE"));
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 5246 */           request.setAttribute("ResourcePoolDetails", rpDetailHash);
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 5252 */         exc.printStackTrace();
/*      */       }
/*      */       finally {
/* 5255 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 5260 */       if (((request.getParameter("selectM") != null) && (request.getParameter("selectM").equals("flashview"))) || (isBusiness))
/*      */       {
/*      */ 
/* 5263 */         putFlashProps(haid, request);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 5269 */       ee.printStackTrace();
/*      */     }
/* 5271 */     if (request.getParameter("alreadyexists") != null)
/*      */     {
/* 5273 */       ActionMessages messages = new ActionMessages();
/* 5274 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.vc.alreadyexists"));
/* 5275 */       saveMessages(request, messages);
/*      */     }
/* 5277 */     if (request.getParameter("isEdit") != null)
/*      */     {
/* 5279 */       ActionMessages messages = new ActionMessages();
/* 5280 */       if ((request.getParameter("status") != null) && ("success".equalsIgnoreCase(request.getParameter("status"))))
/*      */       {
/* 5282 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.vc.update.successmsg"));
/* 5283 */         saveMessages(request, messages);
/*      */       }
/*      */       else
/*      */       {
/* 5287 */         ActionErrors errors = new ActionErrors();
/* 5288 */         errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionError("am.webclient.vc.update.failedmsg"));
/* 5289 */         saveErrors(request, errors);
/*      */       }
/*      */     }
/*      */     
/* 5293 */     return new ActionForward("/jsp/VCenterApplication.jsp");
/*      */   }
/*      */   
/*      */   private void killCookie(HttpServletResponse response, String name)
/*      */   {
/* 5298 */     Cookie c = new Cookie(name, "false");
/* 5299 */     c.setMaxAge(0);
/* 5300 */     c.setPath("/");
/* 5301 */     response.addCookie(c);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getDependentMGroupsDetails(String haid)
/*      */   {
/* 5310 */     return BusinessViewUtil.getDependentMGroupsDetails(haid);
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowApplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */