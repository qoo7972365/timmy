/*       */ package com.adventnet.appmanager.reporting.actions;
/*       */ 
/*       */ import HTTPClient.HTTPConnection;
/*       */ import HTTPClient.HTTPResponse;
/*       */ import HTTPClient.NVPair;
/*       */ import HTTPClient.URI;
/*       */ import com.adventnet.appmanager.bean.SummaryBean;
/*       */ import com.adventnet.appmanager.customfields.MyFields;
/*       */ import com.adventnet.appmanager.db.AMConnectionPool;
/*       */ import com.adventnet.appmanager.db.DBQueryUtil;
/*       */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*       */ import com.adventnet.appmanager.fault.FaultUtil;
/*       */ import com.adventnet.appmanager.fault.SmtpMailer;
/*       */ import com.adventnet.appmanager.logging.AMLog;
/*       */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*       */ import com.adventnet.appmanager.reporting.dataproducer.ReportGraphs;
/*       */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*       */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*       */ import com.adventnet.appmanager.util.AMFlyingSaucerUtil;
/*       */ import com.adventnet.appmanager.util.Constants;
/*       */ import com.adventnet.appmanager.util.DBUtil;
/*       */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*       */ import com.adventnet.appmanager.util.FormatUtil;
/*       */ import com.adventnet.appmanager.util.OEMUtil;
/*       */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*       */ import com.adventnet.appmanager.util.ReportUtil;
/*       */ import com.adventnet.appmanager.util.SSORestClient;
/*       */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*       */ import com.adventnet.appmanager.util.StartUtil;
/*       */ import com.adventnet.appmanager.util.VMReportUtilities;
/*       */ import com.adventnet.awolf.chart.ChartInfo;
/*       */ import com.adventnet.awolf.data.DatasetProducer;
/*       */ import com.adventnet.management.log.LogUser;
/*       */ import com.adventnet.nms.util.NmsLogMgr;
/*       */ import com.adventnet.tools.prevalent.Wield;
/*       */ import java.awt.Color;
/*       */ import java.awt.Paint;
/*       */ import java.io.BufferedInputStream;
/*       */ import java.io.BufferedOutputStream;
/*       */ import java.io.BufferedWriter;
/*       */ import java.io.ByteArrayInputStream;
/*       */ import java.io.EOFException;
/*       */ import java.io.File;
/*       */ import java.io.FileInputStream;
/*       */ import java.io.FileOutputStream;
/*       */ import java.io.FileWriter;
/*       */ import java.io.IOException;
/*       */ import java.io.InputStream;
/*       */ import java.io.InterruptedIOException;
/*       */ import java.io.OutputStream;
/*       */ import java.io.PrintStream;
/*       */ import java.io.PrintWriter;
/*       */ import java.net.ConnectException;
/*       */ import java.net.InetAddress;
/*       */ import java.net.NoRouteToHostException;
/*       */ import java.net.ProtocolException;
/*       */ import java.net.URLEncoder;
/*       */ import java.net.UnknownHostException;
/*       */ import java.sql.ResultSet;
/*       */ import java.text.DecimalFormat;
/*       */ import java.text.SimpleDateFormat;
/*       */ import java.util.ArrayList;
/*       */ import java.util.Arrays;
/*       */ import java.util.Calendar;
/*       */ import java.util.Collections;
/*       */ import java.util.Comparator;
/*       */ import java.util.Enumeration;
/*       */ import java.util.HashMap;
/*       */ import java.util.HashSet;
/*       */ import java.util.Hashtable;
/*       */ import java.util.Iterator;
/*       */ import java.util.LinkedHashMap;
/*       */ import java.util.List;
/*       */ import java.util.Locale;
/*       */ import java.util.Map;
/*       */ import java.util.Map.Entry;
/*       */ import java.util.Properties;
/*       */ import java.util.Set;
/*       */ import java.util.StringTokenizer;
/*       */ import java.util.Vector;
/*       */ import javax.servlet.ServletContext;
/*       */ import javax.servlet.ServletOutputStream;
/*       */ import javax.servlet.http.HttpServletRequest;
/*       */ import javax.servlet.http.HttpServletResponse;
/*       */ import javax.servlet.http.HttpSession;
/*       */ import javax.swing.tree.DefaultMutableTreeNode;
/*       */ import org.apache.commons.logging.Log;
/*       */ import org.apache.struts.action.ActionErrors;
/*       */ import org.apache.struts.action.ActionForm;
/*       */ import org.apache.struts.action.ActionForward;
/*       */ import org.apache.struts.action.ActionMapping;
/*       */ import org.apache.struts.action.ActionMessage;
/*       */ import org.apache.struts.action.ActionMessages;
/*       */ import org.apache.struts.actions.DispatchAction;
/*       */ import org.jfree.data.general.DefaultPieDataset;
/*       */ import org.jfree.data.general.SubSeriesDataset;
/*       */ import org.jfree.data.time.RegularTimePeriod;
/*       */ import org.jfree.data.time.TimeSeries;
/*       */ import org.jfree.data.time.TimeSeriesCollection;
/*       */ import org.jfree.data.time.TimeSeriesDataItem;
/*       */ import org.jsoup.Jsoup;
/*       */ import org.jsoup.nodes.Document;
/*       */ import org.jsoup.select.Elements;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public class AMReportActions
/*       */   extends DispatchAction
/*       */ {
/*   128 */   private static HashMap helpKeyMap = new HashMap();
/*       */   
/*   130 */   private static boolean privilegedUser = false;
/*       */   
/*       */   static
/*       */   {
/*   134 */     helpKeyMap.put("WEBLOGIC-server','JBOSS-server','WEBSPHERE-server','TOMCAT-server", "Application Server Reports");
/*   135 */     helpKeyMap.put("JBOSS-server", "Application Server Reports");
/*   136 */     helpKeyMap.put("WEBLOGIC-server", "Application Server Reports");
/*   137 */     helpKeyMap.put("WEBSPHERE-server", "Application Server Reports");
/*   138 */     helpKeyMap.put("TOMCAT-server", "Application Server Reports");
/*       */     
/*   140 */     helpKeyMap.put("ORACLE-DB-server','MYSQL-DB-server','MSSQL-DB-server", "Database Server Reports");
/*   141 */     helpKeyMap.put("ORACLE-DB-server", "Database Server Reports");
/*   142 */     helpKeyMap.put("MYSQL-DB-server", "Database Server Reports");
/*   143 */     helpKeyMap.put("MSSQL-DB-server", "Database Server Reports");
/*   144 */     helpKeyMap.put("ActiveDirectory','JMX1.2-MX4J-RMI','MAIL-server','Port-Test','TELNET','RMI','SNMP','WEB-server','UrlMonitor','UrlSeq','RBM", "Service Reports");
/*   145 */     helpKeyMap.put("ActiveDirectory", "Service Reports");
/*   146 */     helpKeyMap.put("JMX1.2-MX4J-RMI", "Service Reports");
/*   147 */     helpKeyMap.put("MAIL-server", "Service Reports");
/*   148 */     helpKeyMap.put("Port-Test", "Service Reports");
/*   149 */     helpKeyMap.put("TELNET", "Service Reports");
/*   150 */     helpKeyMap.put("RMI", "Service Reports");
/*   151 */     helpKeyMap.put("SNMP", "Service Reports");
/*   152 */     helpKeyMap.put("WEB-server", "Service Reports");
/*   153 */     helpKeyMap.put("UrlMonitor", "Service Reports");
/*   154 */     helpKeyMap.put("UrlSeq", "Service Reports");
/*   155 */     helpKeyMap.put("RBM", "Service Reports");
/*       */     
/*       */ 
/*   158 */     helpKeyMap.put("Windows','Linux','Solaris", "System Reports");
/*   159 */     helpKeyMap.put("Windows", "System Reports");
/*   160 */     helpKeyMap.put("Linux", "System Reports");
/*   161 */     helpKeyMap.put("Solaris", "System Reports"); }
/*   162 */   private static String htmlMailTpl = getHTMLMailTpl();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*   170 */     if (Constants.isPrivilegedUser(request)) {
/*   171 */       privilegedUser = true;
/*       */     } else {
/*   173 */       privilegedUser = false;
/*       */     }
/*   175 */     return super.execute(mapping, form, request, response);
/*       */   }
/*       */   
/*       */   private final void getCustomApplications(HttpServletRequest request, String limit) throws Exception {
/*   179 */     ResultSet set = null;
/*   180 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*   181 */     String[] query = new String[2];
/*   182 */     String oldquery1 = null;
/*   183 */     if (limit.equals("0"))
/*       */     {
/*       */ 
/*       */ 
/*   187 */       if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*   188 */         String owner = request.getRemoteUser();
/*   189 */         Vector resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*   190 */         query[0] = ("select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID AND " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + " and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.TYPE!='SAP-CCMS' order by AM_ManagedObject.RESOURCEID,AM_CAM_DC_GROUPS.GROUPID");
/*   191 */         oldquery1 = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_SCRIPTHOSTDETAILS.HOSTNAME,AM_CAM_DC_GROUPS.GROUPID,CONCAT(AM_CAM_DC_GROUPS.GROUPNAME,CONCAT('- Instance ',AM_CAM_GROUP_INSTANCE_MAPPING.INSTANCENAME)) as GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_GROUPS,AM_CAM_WMI_EXT_INFO inner join AM_CAM_GROUP_INSTANCE_MAPPING on AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID=AM_CAM_DC_GROUPS.GROUPID inner join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_CAM_DC_GROUPS.GROUPNAME and AM_ATTRIBUTES.TYPE=2 inner join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER on AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID=AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID inner join AM_ARCHIVERCONFIG_EXTN on AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID inner join AM_SCRIPTHOSTDETAILS on AM_SCRIPTHOSTDETAILS.ID=AM_CAM_WMI_EXT_INFO.HOSTID and AM_CAM_DC_GROUPS.RESOURCEID=AM_CAM_WMI_EXT_INFO.RESOURCEID";
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*   196 */         query[1] = ("select AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,  AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME as HOSTNAME,AM_CAM_DC_GROUPS.GROUPID,AM_CAM_DC_GROUPS.GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_ATTRIBUTES,AM_ARCHIVERCONFIG_EXTN,AM_CAM_DC_GROUPS,AM_ManagedObject where AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.RESOURCEID=AM_ARCHIVERCONFIG_EXTN.RESOURCEID and " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids));
/*       */         
/*       */ 
/*   199 */         FormatUtil.printQueryChange("AMReportActions.java", oldquery1, query[1]);
/*       */       }
/*       */       else {
/*   202 */         query[0] = "select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME  from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.TYPE!='SAP-CCMS' order by AM_ManagedObject.RESOURCEID,AM_CAM_DC_GROUPS.GROUPID";
/*   203 */         oldquery1 = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_SCRIPTHOSTDETAILS.HOSTNAME,AM_CAM_DC_GROUPS.GROUPID,CONCAT(AM_CAM_DC_GROUPS.GROUPNAME,CONCAT('- Instance ',AM_CAM_GROUP_INSTANCE_MAPPING.INSTANCENAME)) as GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_GROUPS,AM_CAM_WMI_EXT_INFO inner join AM_CAM_GROUP_INSTANCE_MAPPING on AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID=AM_CAM_DC_GROUPS.GROUPID inner join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_CAM_DC_GROUPS.GROUPNAME and AM_ATTRIBUTES.TYPE=2 inner join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER on AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID=AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID inner join AM_ARCHIVERCONFIG_EXTN on AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID inner join AM_SCRIPTHOSTDETAILS on AM_SCRIPTHOSTDETAILS.ID=AM_CAM_WMI_EXT_INFO.HOSTID and AM_CAM_DC_GROUPS.RESOURCEID=AM_CAM_WMI_EXT_INFO.RESOURCEID";
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   209 */         query[1] = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,  AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME as HOSTNAME,AM_CAM_DC_GROUPS.GROUPID,AM_CAM_DC_GROUPS.GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_ATTRIBUTES,AM_ARCHIVERCONFIG_EXTN,AM_CAM_DC_GROUPS,AM_ManagedObject where AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.RESOURCEID=AM_ARCHIVERCONFIG_EXTN.RESOURCEID";
/*       */         
/*       */ 
/*   212 */         FormatUtil.printQueryChange("AMReportActions.java", oldquery1, query[1]);
/*       */ 
/*       */ 
/*       */ 
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */     }
/*   222 */     else if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*   223 */       String owner = request.getRemoteUser();
/*   224 */       Vector resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*   225 */       query[0] = getTopNValues("select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID  AND " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + " and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.TYPE!='SAP-CCMS' order by AM_ManagedObject.RESOURCEID,AM_CAM_DC_GROUPS.GROUPID ", limit);
/*   226 */       oldquery1 = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_SCRIPTHOSTDETAILS.HOSTNAME,AM_CAM_DC_GROUPS.GROUPID,CONCAT(AM_CAM_DC_GROUPS.GROUPNAME,CONCAT('- Instance ',AM_CAM_GROUP_INSTANCE_MAPPING.INSTANCENAME)) as GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_GROUPS,AM_CAM_WMI_EXT_INFO inner join AM_CAM_GROUP_INSTANCE_MAPPING on AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID=AM_CAM_DC_GROUPS.GROUPID inner join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_CAM_DC_GROUPS.GROUPNAME and AM_ATTRIBUTES.TYPE=2 inner join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER on AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID=AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID inner join AM_ARCHIVERCONFIG_EXTN on AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID inner join AM_SCRIPTHOSTDETAILS on AM_SCRIPTHOSTDETAILS.ID=AM_CAM_WMI_EXT_INFO.HOSTID and AM_CAM_DC_GROUPS.RESOURCEID=AM_CAM_WMI_EXT_INFO.RESOURCEID order by AM_CAM_DC_GROUPS.GROUPID limit " + limit;
/*       */       
/*       */ 
/*   229 */       query[1] = ("select AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_SCRIPTHOSTDETAILS.HOSTNAME,AM_CAM_DC_GROUPS.GROUPID,CONCAT(AM_CAM_DC_GROUPS.GROUPNAME,CONCAT('- Instance ',AM_CAM_GROUP_INSTANCE_MAPPING.INSTANCENAME)) as GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_GROUPS join AM_CAM_WMI_EXT_INFO inner join AM_CAM_GROUP_INSTANCE_MAPPING on AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID=AM_CAM_DC_GROUPS.GROUPID inner join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_CAM_DC_GROUPS.GROUPNAME and AM_ATTRIBUTES.TYPE=2 inner join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER on AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID=AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID inner join AM_ARCHIVERCONFIG_EXTN on AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID inner join AM_SCRIPTHOSTDETAILS on AM_SCRIPTHOSTDETAILS.ID=AM_CAM_WMI_EXT_INFO.HOSTID and AM_CAM_DC_GROUPS.RESOURCEID=AM_CAM_WMI_EXT_INFO.RESOURCEID order by AM_CAM_DC_GROUPS.GROUPID limit " + limit);
/*   230 */       query[1] = DBQueryUtil.getDBQuery("am.reportaction.getCustomApplications.adminrole.query2", new String[] { limit });
/*       */       
/*       */ 
/*   233 */       FormatUtil.printQueryChange("AMReportActions.java", oldquery1, query[1]);
/*       */     }
/*       */     else
/*       */     {
/*   237 */       query[0] = getTopNValues("select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.TYPE!='SAP-CCMS' order by AM_ManagedObject.RESOURCEID,AM_CAM_DC_GROUPS.GROUPID ", limit);
/*   238 */       oldquery1 = getTopNValues("select AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_SCRIPTHOSTDETAILS.HOSTNAME,AM_CAM_DC_GROUPS.GROUPID,CONCAT(AM_CAM_DC_GROUPS.GROUPNAME,CONCAT('- Instance ',AM_CAM_GROUP_INSTANCE_MAPPING.INSTANCENAME)) as GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_GROUPS,AM_CAM_WMI_EXT_INFO inner join AM_CAM_GROUP_INSTANCE_MAPPING on AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID=AM_CAM_DC_GROUPS.GROUPID inner join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_CAM_DC_GROUPS.GROUPNAME and AM_ATTRIBUTES.TYPE=2 inner join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER on AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID=AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID inner join AM_ARCHIVERCONFIG_EXTN on AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID inner join AM_SCRIPTHOSTDETAILS on AM_SCRIPTHOSTDETAILS.ID=AM_CAM_WMI_EXT_INFO.HOSTID and AM_CAM_DC_GROUPS.RESOURCEID=AM_CAM_WMI_EXT_INFO.RESOURCEID order by AM_CAM_DC_GROUPS.GROUPID ", limit);
/*       */       
/*       */ 
/*   241 */       query[1] = DBQueryUtil.getDBQuery("am.reportaction.getCustomApplications.adminrole.query2", new String[] { limit });
/*       */       
/*       */ 
/*   244 */       FormatUtil.printQueryChange("AMReportActions.java", oldquery1, query[1]);
/*       */     }
/*       */     
/*       */     try
/*       */     {
/*   249 */       AMLog.debug("Reports :: CAM Data Query : " + query);
/*   250 */       ArrayList rows = new ArrayList();
/*       */       
/*   252 */       for (int i = 0; i < query.length; i++) {
/*   253 */         set = AMConnectionPool.executeQueryStmt(query[i]);
/*   254 */         while (set.next()) {
/*   255 */           Properties dataProps = new Properties();
/*   256 */           dataProps.setProperty("ATTRIBUTENAME", set.getString(1));
/*   257 */           dataProps.setProperty("ATTRIBUTEID", String.valueOf(set.getInt(2)));
/*   258 */           dataProps.setProperty("NAME", set.getString(3));
/*   259 */           dataProps.setProperty("RESOURCEID", String.valueOf(set.getInt(4)));
/*   260 */           dataProps.setProperty("GROUPNAME", String.valueOf(set.getString(5)));
/*   261 */           dataProps.setProperty("DISPNAME", String.valueOf(set.getString(6)));
/*   262 */           rows.add(dataProps);
/*       */         }
/*       */       }
/*   265 */       request.setAttribute("CAMData", rows);
/*       */     } catch (Exception exp) {
/*   267 */       AMLog.fatal("Exception Occured ", exp);
/*   268 */       request.setAttribute("heading", "0");
/*   269 */       request.setAttribute("strTime", "0");
/*   270 */       throw new Exception(exp);
/*       */     }
/*       */     finally {
/*   273 */       closeResultSet(set);
/*   274 */       set = null;
/*       */     }
/*       */   }
/*       */   
/*       */   private final void getCustomCCMS(HttpServletRequest request, String limit) throws Exception
/*       */   {
/*   280 */     ResultSet set = null;
/*   281 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*   282 */     String query = "";
/*   283 */     if (limit.equals("0"))
/*       */     {
/*       */ 
/*   286 */       if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */       {
/*   288 */         String owner = request.getRemoteUser();
/*   289 */         Vector resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*   290 */         query = "select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID AND " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + " and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.TYPE='SAP-CCMS' order by AM_ManagedObject.RESOURCEID,AM_CAM_DC_GROUPS.GROUPID";
/*       */       }
/*       */       else
/*       */       {
/*   294 */         query = "select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME  from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.TYPE='SAP-CCMS' order by AM_ManagedObject.RESOURCEID,AM_CAM_DC_GROUPS.GROUPID";
/*       */ 
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*   300 */     else if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */     {
/*   302 */       String owner = request.getRemoteUser();
/*   303 */       Vector resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*   304 */       query = getTopNValues("select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID  AND " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + " and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.TYPE='SAP-CCMS' order by AM_ManagedObject.RESOURCEID,AM_CAM_DC_GROUPS.GROUPID ", limit);
/*       */     }
/*       */     else
/*       */     {
/*   308 */       query = getTopNValues("select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.TYPE='SAP-CCMS' order by AM_ManagedObject.RESOURCEID,AM_CAM_DC_GROUPS.GROUPID ", limit);
/*       */     }
/*       */     
/*       */     try
/*       */     {
/*   313 */       AMLog.debug("Reports CCMS Custom Attributes query : " + query);
/*   314 */       ArrayList rows = new ArrayList();
/*   315 */       set = AMConnectionPool.executeQueryStmt(query);
/*   316 */       while (set.next())
/*       */       {
/*   318 */         Properties dataProps = new Properties();
/*   319 */         dataProps.setProperty("ATTRIBUTENAME", set.getString(1));
/*   320 */         dataProps.setProperty("ATTRIBUTEID", String.valueOf(set.getInt(2)));
/*   321 */         dataProps.setProperty("NAME", set.getString(3));
/*   322 */         dataProps.setProperty("RESOURCEID", String.valueOf(set.getInt(4)));
/*   323 */         dataProps.setProperty("GROUPNAME", String.valueOf(set.getString(5)));
/*   324 */         dataProps.setProperty("DISPNAME", String.valueOf(set.getString(6)));
/*   325 */         rows.add(dataProps);
/*       */       }
/*   327 */       request.setAttribute("CCMSData", rows);
/*       */     }
/*       */     catch (Exception exp)
/*       */     {
/*   331 */       AMLog.debug("Exception Occured in reports CCMS Custom Attributes query. Error Message : " + exp.getMessage());
/*   332 */       request.setAttribute("heading", "0");
/*   333 */       request.setAttribute("strTime", "0");
/*   334 */       throw new Exception(exp);
/*       */     }
/*       */     finally
/*       */     {
/*   338 */       closeResultSet(set);
/*   339 */       set = null;
/*       */     }
/*       */   }
/*       */   
/*       */   public ActionForward getCCMSAttributes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*   344 */     request.setAttribute("HelpKey", "Custom Application Reports");
/*   345 */     getCustomCCMS(request, "0");
/*   346 */     getHolisticApps(mapping, form, request, response);
/*   347 */     getMonitors(mapping, form, request, response);
/*   348 */     return mapping.findForward("report.CAMAttributes");
/*       */   }
/*       */   
/*       */   public ActionForward getCAMAttributes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*   353 */     request.setAttribute("HelpKey", "Custom Application Reports");
/*   354 */     getCustomApplications(request, "0");
/*   355 */     getHolisticApps(mapping, form, request, response);
/*   356 */     getMonitors(mapping, form, request, response);
/*   357 */     return mapping.findForward("report.CAMAttributes");
/*       */   }
/*       */   
/*       */   public ActionForward generateCustomAttributeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*   361 */     request.setAttribute("HelpKey", "Custom Application Reports");
/*   362 */     ActionMessages messages = new ActionMessages();
/*   363 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*   364 */     ReportForm controls = (ReportForm)form;
/*   365 */     String resourceid = controls.getResourceid();
/*   366 */     String attribute = controls.getAttribute();
/*   367 */     String period = controls.getPeriod();
/*       */     
/*   369 */     getHolisticApps(mapping, form, request, response);
/*   370 */     getMonitors(mapping, form, request, response);
/*   371 */     getCustomApplications(request, "3");
/*   372 */     long[] timeStamps = null;
/*   373 */     ResultSet set = null;
/*   374 */     if ((controls.getStartDate().equals("")) || (controls.getEndDate().equals(""))) {
/*   375 */       timeStamps = ReportUtilities.getTimeStamp(period);
/*       */     }
/*       */     else {
/*   378 */       controls.setPeriod("4");
/*       */       try {
/*   380 */         timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */       } catch (IllegalArgumentException iae) {
/*   382 */         String errMsg = iae.getMessage();
/*   383 */         AMLog.debug("Reports :  " + errMsg);
/*   384 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*   385 */         saveMessages(request, messages);
/*   386 */         request.setAttribute("heading", "0");
/*   387 */         request.setAttribute("strTime", "0");
/*   388 */         return mapping.findForward("report.message");
/*       */       }
/*       */     }
/*       */     
/*   392 */     long[] dailyRptTimestamp = ReportUtilities.getDailyStartEndTime(timeStamps[0], timeStamps[1], "AM_MinMaxAvgData");
/*   393 */     String dailyRptCondition = " and AM_MinMaxAvgData.DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1];
/*       */     
/*   395 */     if (dailyRptTimestamp[2] > 0L) {
/*   396 */       dailyRptCondition = " and ( AM_MinMaxAvgData.DURATION=1 and ARCHIVEDTIME >=" + dailyRptTimestamp[0] + " and ARCHIVEDTIME <=" + dailyRptTimestamp[1] + " OR AM_MinMaxAvgData.DURATION=2 and ARCHIVEDTIME >=" + dailyRptTimestamp[2] + " and ARCHIVEDTIME <=" + dailyRptTimestamp[3] + ") ";
/*       */     }
/*       */     
/*   399 */     String query = "";
/*   400 */     String resourcetype = "";
/*   401 */     ResultSet typers = null;
/*       */     
/*   403 */     query = DBQueryUtil.getDBQuery("am.amreportAction.generateCustomAttbributeReport1", new String[] { attribute, resourceid, dailyRptCondition });
/*       */     try {
/*   405 */       typers = AMConnectionPool.executeQueryStmt("SELECT TYPE FROM AM_ManagedObject,AM_CAM_DC_GROUPS where (AM_CAM_DC_GROUPS.GROUPID=" + resourceid + " or AM_CAM_DC_GROUPS.RESOURCEID=" + resourceid + ") and AM_CAM_DC_GROUPS.RESOURCEID=AM_ManagedObject.RESOURCEID");
/*   406 */       if (typers.next()) {
/*   407 */         resourcetype = typers.getString("TYPE");
/*   408 */         if ((resourcetype.equals("Generic WMI")) || ("SAP-CCMS".equals(resourcetype)))
/*       */         {
/*   410 */           query = DBQueryUtil.getDBQuery("am.amreportAction.generateCustomAttbributeReport2", new String[] { attribute, resourceid, dailyRptCondition });
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ex) {
/*   415 */       ex.printStackTrace();
/*       */     } finally {
/*   417 */       AMConnectionPool.closeStatement(typers);
/*       */     }
/*       */     try
/*       */     {
/*   421 */       ArrayList modata = ReportUtilities.getTabularData(query, false);
/*   422 */       if ((modata.size() == 0) && (controls.getReporttype().equals("html"))) {
/*   423 */         AMLog.debug("Reports : No data for " + attribute);
/*   424 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.time"));
/*   425 */         saveMessages(request, messages);
/*       */         
/*       */ 
/*   428 */         request.setAttribute("heading", "0");
/*   429 */         request.setAttribute("strTime", "0");
/*   430 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/*       */ 
/*   434 */       query = DBQueryUtil.getDBQuery("am.amreportAction.generateCustomAttbributeReport3", new String[] { resourceid, attribute + dailyRptCondition });
/*   435 */       set = AMConnectionPool.executeQueryStmt(query);
/*       */       
/*   437 */       AMLog.debug("Reports : " + query);
/*   438 */       data = new ArrayList();
/*   439 */       while (set.next()) {
/*   440 */         ArrayList rows = new ArrayList();
/*   441 */         java.util.Date d = new java.util.Date(set.getLong("ARCHIVEDTIME"));
/*   442 */         rows.add(d);
/*   443 */         rows.add(new Long(set.getLong("AVG")));
/*   444 */         rows.add(set.getString("MINVALUE"));
/*   445 */         rows.add(set.getString("MAXVALUE"));
/*   446 */         ((ArrayList)data).add(rows);
/*       */       }
/*       */       
/*   449 */       ArrayList first = (ArrayList)((ArrayList)data).get(0);
/*   450 */       request.setAttribute("strTime", first.get(0));
/*   451 */       ArrayList last = (ArrayList)((ArrayList)data).get(((ArrayList)data).size() - 1);
/*   452 */       request.setAttribute("endTime", last.get(0));
/*   453 */       request.setAttribute("modata", modata);
/*   454 */       request.setAttribute("data", data);
/*   455 */       request.setAttribute("period", period);
/*       */       
/*   457 */       request.setAttribute("attributename", controls.getAttributeName());
/*   458 */       String mbQuery = "select GROUPNAME from AM_CAM_DC_ATTRIBUTES,AM_CAM_DC_GROUPS where AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and  AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=" + attribute;
/*   459 */       if (resourcetype.equals("Generic WMI")) {
/*   460 */         mbQuery = "select RESOURCETYPE from AM_ATTRIBUTES where AM_ATTRIBUTES.ATTRIBUTEID=10000050";
/*   461 */         request.setAttribute("childMO", "true");
/*       */       }
/*   463 */       ResultSet set1 = AMConnectionPool.executeQueryStmt(mbQuery);
/*   464 */       if (set1.next()) {
/*   465 */         request.setAttribute("mBeanName", set1.getString(1));
/*       */       }
/*   467 */       AMConnectionPool.closeStatement(set1);
/*       */       ReportGraphs rg;
/*   469 */       if (controls.getReporttype().equals("pdf")) {
/*   470 */         rg = new ReportGraphs();
/*   471 */         Map params = new Hashtable();
/*   472 */         params.put("type", "ATTRIBUTE_GRAPH");
/*   473 */         params.put("id", resourceid);
/*   474 */         params.put("attid", attribute);
/*   475 */         params.put("period", period);
/*   476 */         params.put("startTime", controls.getStartDate());
/*   477 */         params.put("endTime", controls.getEndDate());
/*   478 */         params.put("attributeName", FormatUtil.getString(controls.getAttributeName()));
/*   479 */         rg.setParams(params);
/*   480 */         ChartInfo cinfo = new ChartInfo();
/*   481 */         cinfo.setDataSet(rg);
/*   482 */         cinfo.setHeight("200");
/*   483 */         cinfo.setWidth("500");
/*       */         
/*   485 */         String image = cinfo.getTimeChartAsJPG();
/*   486 */         request.setAttribute("camattribImage", image);
/*   487 */         request.setAttribute("report-type-template", "report.CustomAttribute");
/*   488 */         request.setAttribute("sp-report-type", "pdf");
/*       */         
/*   490 */         return mapping.findForward("report.CustomAttribute.pdf");
/*       */       }
/*   492 */       if (controls.getReporttype().equals("csv")) {
/*   493 */         return mapping.findForward("report.CustomAttribute.csv");
/*       */       }
/*       */       
/*   496 */       return mapping.findForward("report.CustomAttribute");
/*       */     }
/*       */     catch (Exception exp) {
/*       */       Object data;
/*   500 */       AMLog.fatal("Reports :  Exception :", exp);
/*   501 */       request.setAttribute("heading", "0");
/*   502 */       request.setAttribute("strTime", "0");
/*   503 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*   504 */       saveMessages(request, messages);
/*   505 */       return mapping.findForward("report.message");
/*       */     }
/*       */     finally {
/*   508 */       closeResultSet(set);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public static ArrayList getAttributes(String type)
/*       */   {
/*   515 */     String resourceid = "-1";
/*   516 */     ArrayList main = new ArrayList();
/*   517 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */     
/*       */     try
/*       */     {
/*   521 */       String qry = "select typeid from AM_MONITOR_TYPES where TYPENAME='" + type + "'";
/*   522 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*   523 */       if (rs.next())
/*       */       {
/*   525 */         resourceid = rs.getString(1);
/*       */       }
/*       */       else
/*       */       {
/*   529 */         return new ArrayList();
/*       */       }
/*   531 */       String query = "select DISPLAYNAME,TABLENAME,AM_Script_Resource_Attributes_Mapper.attributeid from AM_ATTRIBUTES left join AM_Script_Resource_Attributes_Mapper on AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID left outer join AM_ArchiverConfig on AM_ArchiverConfig.attributeid=AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID where resourceid=" + resourceid + " and TYPE=0";
/*   532 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(query);
/*   533 */       ArrayList attids = new ArrayList();
/*   534 */       ArrayList attnames = new ArrayList();
/*   535 */       while (rs1.next())
/*       */       {
/*   537 */         if ((rs1.getString(2) != null) && (!rs1.getString(2).equals("null")))
/*       */         {
/*   539 */           attids.add(rs1.getString(3));
/*   540 */           attnames.add(rs1.getString(1));
/*       */         }
/*       */       }
/*   543 */       main.add(attids);
/*   544 */       main.add(attnames);
/*       */       try
/*       */       {
/*   547 */         AMConnectionPool.closeStatement(rs1);
/*   548 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */       catch (Exception exc)
/*       */       {
/*   552 */         exc.printStackTrace();
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*   557 */       e.printStackTrace();
/*       */     }
/*   559 */     return main;
/*       */   }
/*       */   
/*       */   public ActionForward getAttributesforType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*   564 */     String type = request.getParameter("type");
/*   565 */     ArrayList main = getAttributes(type);
/*   566 */     request.setAttribute("customattributes", main);
/*   567 */     request.setAttribute("fromtype", "true");
/*   568 */     ((ReportForm)form).setCustomservice(type);
/*       */     
/*   570 */     return mapping.findForward("/showReports.do?actionMethod=getReportIndex&fromtype=true");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public String getDisplayName(String type)
/*       */   {
/*   577 */     String toreturn = type;
/*   578 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */     try
/*       */     {
/*   581 */       String q1 = "select displayname from AM_ManagedResourceType where RESOURCETYPE='" + type + "'";
/*   582 */       ResultSet rs = AMConnectionPool.executeQueryStmt(q1);
/*   583 */       if (rs.next())
/*       */       {
/*   585 */         toreturn = rs.getString(1);
/*       */       }
/*   587 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */     catch (Exception exc) {}
/*       */     
/*       */ 
/*       */ 
/*   593 */     return toreturn;
/*       */   }
/*       */   
/*       */   public ActionForward getReportIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*   599 */     if (EnterpriseUtil.isIt360MSPEdition())
/*       */     {
/*   601 */       Properties custProp = null;
/*   602 */       if (request.getSession().getAttribute("custProp") != null)
/*       */       {
/*   604 */         custProp = (Properties)request.getSession().getAttribute("custProp");
/*       */       }
/*   606 */       if ((custProp != null) && (custProp.size() > 0))
/*       */       {
/*   608 */         String custName = custProp.keys().nextElement().toString();
/*   609 */         String custId = custProp.getProperty(custName);
/*   610 */         AMLog.debug("custName:" + custName + ":::custId:" + custId);
/*   611 */         if ((custName != null) && (custId != null))
/*       */         {
/*   613 */           Properties siteProps = EnterpriseUtil.getAllSiteProps(custId, request.getRemoteUser());
/*   614 */           if (siteProps != null)
/*       */           {
/*   616 */             request.setAttribute("siteProps", siteProps);
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   623 */     ResultSet rs1 = null;
/*       */     try
/*       */     {
/*   626 */       Properties props = (Properties)request.getSession().getServletContext().getAttribute("applications");
/*   627 */       String query = "SELECT RESOURCEID,DISPLAYNAME FROM AM_ManagedObject WHERE TYPE='HAI'";
/*   628 */       AMConnectionPool.getInstance();rs1 = AMConnectionPool.executeQueryStmt(query);
/*   629 */       while (rs1.next())
/*       */       {
/*   631 */         props.setProperty(rs1.getString(1), rs1.getString(2));
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*   636 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/*   640 */       AMConnectionPool.closeStatement(rs1);
/*       */     }
/*       */     
/*   643 */     ReportForm rf = (ReportForm)form;
/*   644 */     rf.setIsUserOpr(Boolean.valueOf(privilegedUser).booleanValue());
/*   645 */     rf.setRemoteUser(request.getRemoteUser());
/*   646 */     String headerval = request.getHeader("reportagent");
/*   647 */     String type = request.getParameter("type");
/*   648 */     if ((request.isUserInRole("REPORTER")) && (
/*   649 */       (headerval == null) || ((headerval != null) && (!headerval.equals(Constants.RY)))))
/*       */     {
/*   651 */       return null;
/*       */     }
/*       */     
/*   654 */     ActionMessages messages = new ActionMessages();
/*       */     try {
/*   656 */       getHolisticApps(mapping, form, request, response);
/*   657 */       request.setAttribute("excludeExtDevicesCond", "true");
/*   658 */       getMonitors(mapping, form, request, response);
/*   659 */       getCustomApplications(request, "10");
/*   660 */       getCustomCCMS(request, "5");
/*   661 */       request.setAttribute("heading", "0");
/*   662 */       request.setAttribute("strTime", "0");
/*   663 */       request.setAttribute("HelpKey", "Reports Page");
/*   664 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   665 */       String qry = getTopNValues("select typename from AM_MONITOR_TYPES where amcreated='NO' order by typeid ", "1");
/*   666 */       if ((request.getParameter("type") == null) || (request.getParameter("type").equals("")))
/*       */       {
/*       */ 
/*       */         try
/*       */         {
/*   671 */           ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*   672 */           if (rs.next())
/*       */           {
/*   674 */             type = rs.getString(1);
/*   675 */             rf.setCustomservice(type);
/*   676 */             ArrayList main = getAttributes(type);
/*   677 */             request.setAttribute("customattributes", main);
/*   678 */             request.setAttribute("customtype", getDisplayName(type));
/*   679 */             ArrayList rows = new ArrayList();
/*       */             
/*   681 */             String qry2 = "select typename from AM_ManagedResourceType,AM_MONITOR_TYPES where AMCREATED='NO' and RESOURCEGROUP NOT IN('NWD','EMO','SAN') and resourcetype=AM_MONITOR_TYPES.typename order by TYPEID";
/*   682 */             ResultSet rs2 = AMConnectionPool.executeQueryStmt(qry2);
/*   683 */             while (rs2.next())
/*       */             {
/*   685 */               String restype = rs2.getString(1);
/*   686 */               ArrayList attList = ReportUtilities.getAttributeListForResourcetype(restype);
/*   687 */               if (attList.size() != 0)
/*       */               {
/*   689 */                 String restypeDisname = getDisplayName(restype);
/*   690 */                 Properties RdataProps = new Properties();
/*   691 */                 RdataProps.setProperty("label", FormatUtil.getString(" ----" + restypeDisname + "----"));
/*   692 */                 RdataProps.setProperty("value", "resource");
/*   693 */                 rows.add(RdataProps);
/*   694 */                 for (int i = 0; i < attList.size(); i++)
/*       */                 {
/*   696 */                   rows.add((Properties)attList.get(i));
/*       */                 }
/*       */               }
/*       */             }
/*       */             
/*   701 */             AMConnectionPool.closeStatement(rs2);
/*   702 */             rf.setCustomserviceAttrib(rows);
/*       */           }
/*       */           else
/*       */           {
/*   706 */             ArrayList main = new ArrayList();
/*   707 */             request.setAttribute("customattributes", main);
/*       */           }
/*   709 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*       */         catch (Exception exc)
/*       */         {
/*   713 */           exc.printStackTrace();
/*       */         }
/*       */       }
/*       */       else {
/*   717 */         ArrayList main = getAttributes(type);
/*   718 */         String restypeDisname = getDisplayName(type);
/*   719 */         request.setAttribute("customattributes", main);
/*   720 */         request.setAttribute("customtype", restypeDisname);
/*       */         
/*   722 */         ArrayList rows = new ArrayList();
/*   723 */         ArrayList names = (ArrayList)main.get(1);
/*   724 */         ArrayList ids = (ArrayList)main.get(0);
/*   725 */         if (names.size() != 0)
/*       */         {
/*   727 */           Properties RdataProps = new Properties();
/*   728 */           RdataProps.setProperty("label", FormatUtil.getString(" ----" + restypeDisname + "----"));
/*   729 */           RdataProps.setProperty("value", type + "#" + type);
/*   730 */           rows.add(RdataProps);
/*   731 */           for (int i = 0; i < names.size(); i++)
/*       */           {
/*   733 */             Properties dataProps = new Properties();
/*   734 */             dataProps.setProperty("label", FormatUtil.getString((String)names.get(i)));
/*   735 */             dataProps.setProperty("value", type + "#" + (String)ids.get(i));
/*   736 */             rows.add(dataProps);
/*       */           }
/*       */         }
/*       */         
/*   740 */         rf.setCustomserviceAttrib(rows);
/*       */       }
/*   742 */       String monitorsForOpr = "";
/*       */       
/*   744 */       boolean isUserResourceEnabled = false;
/*   745 */       String loginUserid = null;
/*   746 */       String rGroupCountQuery = "select mrt.RESOURCEGROUP,count(mo.TYPE) as count from AM_ManagedObject as mo ,AM_ManagedResourceType as mrt where mo.TYPE=mrt.RESOURCETYPE group by mrt.RESOURCEGROUP order by RESOURCEGROUP";
/*   747 */       if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */       {
/*   749 */         if (EnterpriseUtil.isIt360MSPEdition())
/*       */         {
/*   751 */           monitorsForOpr = ReportUtilities.getCondition("mo.RESOURCEID", ReportUtilities.getResourceIdentity(request.getRemoteUser(), request));
/*   752 */           rGroupCountQuery = "select mrt.RESOURCEGROUP,count(mo.TYPE) as count from AM_ManagedObject as mo ,AM_ManagedResourceType as mrt where mo.TYPE=mrt.RESOURCETYPE and " + monitorsForOpr + " group by mrt.RESOURCEGROUP order by RESOURCEGROUP";
/*       */ 
/*       */ 
/*       */         }
/*   756 */         else if ((Constants.isPrivilegedUser(request)) && (Constants.isSsoEnabled())) {
/*   757 */           isUserResourceEnabled = true;
/*   758 */           loginUserid = Constants.getLoginUserid(request);
/*   759 */           rGroupCountQuery = "select mrt.RESOURCEGROUP,count(mo.TYPE) as count from AM_ManagedObject as mo ,AM_ManagedResourceType as mrt,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE=mrt.RESOURCETYPE  group by mrt.RESOURCEGROUP order by RESOURCEGROUP";
/*       */         } else {
/*   761 */           monitorsForOpr = ReportUtilities.getQueryCondition("mo.RESOURCEID", request.getRemoteUser());
/*   762 */           rGroupCountQuery = "select mrt.RESOURCEGROUP,count(mo.TYPE) as count from AM_ManagedObject as mo ,AM_ManagedResourceType as mrt where mo.TYPE=mrt.RESOURCETYPE and " + monitorsForOpr + " group by mrt.RESOURCEGROUP order by RESOURCEGROUP";
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*   768 */       ResultSet rs = AMConnectionPool.executeQueryStmt(rGroupCountQuery);
/*   769 */       Hashtable rGroupCountHash = new Hashtable();
/*   770 */       Hashtable rGroupCustomHash = new Hashtable();
/*   771 */       while (rs.next())
/*       */       {
/*   773 */         rGroupCountHash.put(rs.getString("RESOURCEGROUP"), Integer.valueOf(rs.getInt("count")));
/*       */       }
/*       */       
/*   776 */       rGroupCountQuery = "select TYPE, count(TYPE) as count from AM_ManagedObject where TYPE in ('UrlMonitor','UrlSeq','Web Service','WEB-server','Apache-server','IIS-server','Nginx','PHP','RBM','RESTAPIMonitor') group by TYPE";
/*   777 */       if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */       {
/*   779 */         if (isUserResourceEnabled) {
/*   780 */           rGroupCountQuery = "select mo.TYPE, count(mo.TYPE) as count from AM_ManagedObject as mo, AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE in ('UrlMonitor','UrlSeq','Web Service','WEB-server','Apache-server','IIS-server','Nginx','PHP','RBM','RESTAPIMonitor')   group by mo.TYPE";
/*       */         } else {
/*   782 */           rGroupCountQuery = "select mo.TYPE, count(mo.TYPE) as count from AM_ManagedObject as mo where mo.TYPE in ('UrlMonitor','UrlSeq','Web Service','WEB-server','Apache-server','IIS-server','Nginx','PHP','RBM','RESTAPIMonitor')  and " + monitorsForOpr + " group by mo.TYPE";
/*       */         }
/*       */       }
/*       */       
/*   786 */       rs = AMConnectionPool.executeQueryStmt(rGroupCountQuery);
/*   787 */       int webserviceCount = 0;
/*   788 */       int webserverCount = 0;
/*   789 */       int urlCount = 0;
/*       */       
/*   791 */       while (rs.next())
/*       */       {
/*   793 */         if (rs.getString("TYPE").equals("Web Service"))
/*       */         {
/*   795 */           webserviceCount = rs.getInt("count");
/*       */         }
/*   797 */         else if ((rs.getString("TYPE").equals("UrlMonitor")) || (rs.getString("TYPE").equals("UrlSeq")) || (rs.getString("TYPE").equals("RBM")))
/*       */         {
/*   799 */           urlCount += rs.getInt("count");
/*       */         }
/*       */         else
/*       */         {
/*   803 */           webserverCount += rs.getInt("count");
/*       */         }
/*       */       }
/*       */       
/*   807 */       if (webserviceCount > 0)
/*       */       {
/*   809 */         rGroupCountHash.put("Web Service", Integer.valueOf(webserviceCount));
/*       */       }
/*   811 */       if (webserverCount > 0)
/*       */       {
/*   813 */         rGroupCountHash.put("WEB-server", Integer.valueOf(webserverCount));
/*       */       }
/*   815 */       if (urlCount > 0)
/*       */       {
/*   817 */         rGroupCountHash.put("UrlMonitor", Integer.valueOf(urlCount));
/*       */       }
/*   819 */       rGroupCountQuery = "select mo.TYPE, count(mo.TYPE) as count,mrt.RESOURCEGROUP as RESOURCEGROUP from AM_ManagedObject as mo, AM_MONITOR_TYPES as mt , AM_ManagedResourceType as mrt where mo.TYPE=mt.TYPENAME and mt.AMCREATED='NO' and mo. TYPE= mrt.RESOURCETYPE and  mrt.RESOURCEGROUP NOT IN('NWD','EMO','SAN') group by mo.TYPE,mrt.RESOURCEGROUP";
/*   820 */       if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */       {
/*   822 */         if (isUserResourceEnabled) {
/*   823 */           rGroupCountQuery = "select mo.TYPE, count(mo.TYPE) as count,mrt.RESOURCEGROUP RESOURCEGROUP from AM_USERRESOURCESTABLE, AM_ManagedObject as mo, AM_MONITOR_TYPES as mt , AM_ManagedResourceType as mrt where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE=mt.TYPENAME and mt.AMCREATED='NO' and mo. TYPE= mrt.RESOURCETYPE and mrt.RESOURCEGROUP NOT IN('NWD','EMO')  group by mo.TYPE,mrt.RESOURCEGROUP";
/*       */         } else {
/*   825 */           rGroupCountQuery = "select mo.TYPE, count(mo.TYPE) as count,mrt.RESOURCEGROUP RESOURCEGROUP from AM_ManagedObject as mo, AM_MONITOR_TYPES as mt , AM_ManagedResourceType as mrt where mo.TYPE=mt.TYPENAME and mt.AMCREATED='NO' and mo. TYPE= mrt.RESOURCETYPE and mrt.RESOURCEGROUP NOT IN('NWD','EMO','SAN') and " + monitorsForOpr + "  group by mo.TYPE,mrt.RESOURCEGROUP";
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*   830 */       rs = AMConnectionPool.executeQueryStmt(rGroupCountQuery);
/*   831 */       int customMOTypeCount = 0;
/*       */       
/*   833 */       while (rs.next())
/*       */       {
/*   835 */         customMOTypeCount += rs.getInt("count");
/*   836 */         rGroupCustomHash.put(rs.getString("RESOURCEGROUP"), Integer.valueOf(rs.getInt("count")));
/*       */       }
/*       */       
/*   839 */       if (customMOTypeCount > 0)
/*       */       {
/*   841 */         rGroupCountHash.put("CustomMOType", Integer.valueOf(customMOTypeCount));
/*       */       }
/*       */       
/*   844 */       int eumMOTypeCount = 0;
/*   845 */       String eumChildListCond = " and  mo.resourceid NOT IN (" + Constants.getEUMChildString() + ") ";
/*   846 */       rGroupCountQuery = "select mo.TYPE, count(mo.TYPE) as count from AM_ManagedObject as mo where mo.TYPE in " + Constants.resourceTypesEUM + eumChildListCond + " group by mo.TYPE";
/*   847 */       if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */       {
/*   849 */         if (isUserResourceEnabled) {
/*   850 */           rGroupCountQuery = "select mo.TYPE, count(mo.TYPE) as count from AM_ManagedObject as mo,AM_USERRESOURCESTABLE where mo.TYPE in " + Constants.resourceTypesEUM + " and AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + "  " + eumChildListCond + " group by mo.TYPE";
/*       */         } else {
/*   852 */           rGroupCountQuery = "select mo.TYPE, count(mo.TYPE) as count from AM_ManagedObject as mo where mo.TYPE in " + Constants.resourceTypesEUM + "  and " + monitorsForOpr + eumChildListCond + " group by mo.TYPE";
/*       */         }
/*       */       }
/*       */       
/*   856 */       rs = AMConnectionPool.executeQueryStmt(rGroupCountQuery);
/*   857 */       while (rs.next())
/*       */       {
/*   859 */         eumMOTypeCount += rs.getInt("count");
/*       */       }
/*   861 */       if (eumMOTypeCount > 0)
/*       */       {
/*   863 */         rGroupCountHash.put("eumMOType", Integer.valueOf(eumMOTypeCount));
/*       */       }
/*       */       
/*   866 */       ArrayList availableTypes = new ArrayList();
/*   867 */       List<String> resourceList = new ArrayList(rGroupCountHash.keySet());
/*   868 */       Collections.sort(resourceList);
/*   869 */       Properties disp = new Properties();
/*   870 */       disp.put("label", FormatUtil.getString("am.reporttab.selecttype.text"));
/*   871 */       disp.put("value", "-");
/*   872 */       availableTypes.add(disp);
/*   873 */       for (String resourceGroup : resourceList) {
/*       */         try {
/*   875 */           if ((!"HAI".equals(resourceGroup)) && (!"NWD".equals(resourceGroup)) && (!"NET".equals(resourceGroup)) && (!"CAM".equals(resourceGroup))) {
/*   876 */             String displayName = (String)Constants.resGrpMap.get(resourceGroup);
/*   877 */             Properties prop = new Properties();
/*   878 */             if (displayName != null) {
/*   879 */               prop.put("label", displayName);
/*   880 */               prop.put("value", resourceGroup);
/*   881 */               availableTypes.add(prop);
/*       */             }
/*       */           }
/*       */         }
/*       */         catch (Exception ex) {
/*   886 */           ex.printStackTrace();
/*       */         }
/*       */       }
/*   889 */       rf.setAvailableMonTypes(availableTypes);
/*       */       
/*   891 */       request.setAttribute("ResourceGroupCount", rGroupCountHash);
/*   892 */       request.setAttribute("CustomResGroupCount", rGroupCustomHash);
/*       */       
/*   894 */       rf.setWebservice("Web Service");
/*   895 */       if (Constants.isIt360)
/*       */       {
/*   897 */         return mapping.findForward("report.it360Success");
/*       */       }
/*   899 */       return mapping.findForward("report.success");
/*       */     } catch (Exception exp) {
/*   901 */       AMLog.fatal("Reports :  Exception ", exp);
/*   902 */       request.setAttribute("heading", "0");
/*   903 */       request.setAttribute("strTime", "0");
/*   904 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*   905 */       String qry = getTopNValues("select typename from AM_MONITOR_TYPES where amcreated='NO' order by typeid ", "1");
/*   906 */       if ((request.getParameter("type") == null) || (request.getParameter("type").equals("")))
/*       */       {
/*       */         try
/*       */         {
/*   910 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*   911 */           ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*   912 */           if (rs.next())
/*       */           {
/*   914 */             type = rs.getString(1);
/*   915 */             rf.setCustomservice(type);
/*   916 */             ArrayList main = getAttributes(type);
/*   917 */             request.setAttribute("customattributes", main);
/*   918 */             request.setAttribute("customtype", getDisplayName(type));
/*       */           }
/*       */           else
/*       */           {
/*   922 */             ArrayList main = new ArrayList();
/*   923 */             request.setAttribute("customattributes", main);
/*       */           }
/*   925 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*       */         catch (Exception exc)
/*       */         {
/*   929 */           exc.printStackTrace();
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/*   934 */         request.setAttribute("customattributes", getAttributes(type));
/*   935 */         request.setAttribute("customtype", getDisplayName(type));
/*       */       }
/*   937 */       saveMessages(request, messages);
/*       */     }
/*       */     
/*   940 */     return mapping.findForward("report.message");
/*       */   }
/*       */   
/*       */ 
/*       */   public final void getHolisticApps(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*   947 */     ResultSet set = null;
/*   948 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*   949 */     String query = null;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*   956 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*   958 */         String bsgFilterCondn = "";
/*   959 */         String bsgType = "0";
/*   960 */         if (EnterpriseUtil.isIt360MSPEdition())
/*       */         {
/*   962 */           bsgType = "1";
/*   963 */           Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request, Constants.subGroupsEnabled.equals("true"));
/*   964 */           bsgFilterCondn = " and " + EnterpriseUtil.getCondition("hai.HAID", haidVector);
/*       */         }
/*   966 */         query = "select mo.RESOURCENAME,mo.RESOURCEID,mo.DISPLAYNAME,PARENTID from AM_ManagedObject mo JOIN AM_HOLISTICAPPLICATION hai on mo.RESOURCEID=hai.HAID JOIN AM_PARENTCHILDMAPPER pcmapper on pcmapper.CHILDID=mo.RESOURCEID JOIN AM_HOLISTICAPPLICATION_EXT ext on ext.RESOURCEID=pcmapper.PARENTID where hai.TYPE=" + bsgType + bsgFilterCondn + " and (ext.APP_TYPE not like 'SLA' or ext.APP_TYPE is NULL) order by PARENTID,RESOURCENAME";
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*   971 */         AMLog.debug("getHolisticApps:query" + query);
/*       */ 
/*       */ 
/*       */       }
/*   975 */       else if (privilegedUser)
/*       */       {
/*   977 */         String owner = request.getRemoteUser();
/*   978 */         if (Constants.subGroupsEnabled.equals("true"))
/*       */         {
/*   980 */           if (Constants.isSsoEnabled()) {
/*   981 */             String loginUserid = Constants.getLoginUserid(request);
/*   982 */             query = "select RESOURCENAME,AM_ManagedObject.RESOURCEID,DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_USERRESOURCESTABLE where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " order by RESOURCEID, UPPER(DISPLAYNAME)";
/*       */           } else {
/*   984 */             query = "select RESOURCENAME,RESOURCEID,DISPLAYNAME from AM_ManagedObject mo\tJOIN AM_HOLISTICAPPLICATION hai ON (mo.RESOURCEID=hai.HAID)\tJOIN AM_HOLISTICAPPLICATION_OWNERS ho ON (hai.HAID=ho.HAID)\tJOIN AM_UserPasswordTable pass ON (ho.OWNERID=pass.USERID)  WHERE pass.USERNAME='" + owner + "' order by mo.RESOURCEID,UPPER(mo.DISPLAYNAME)";
/*       */ 
/*       */ 
/*       */           }
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*       */         }
/*   993 */         else if (Constants.isSsoEnabled()) {
/*   994 */           String loginUserid = Constants.getLoginUserid(request);
/*   995 */           query = "select RESOURCENAME,AM_ManagedObject.RESOURCEID,DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_USERRESOURCESTABLE where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=0 and AM_HOLISTICAPPLICATION.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " order by RESOURCEID";
/*       */         } else {
/*   997 */           query = "select RESOURCENAME,RESOURCEID,DISPLAYNAME from AM_ManagedObject mo JOIN AM_HOLISTICAPPLICATION hai ON (mo.RESOURCEID=hai.HAID and hai.TYPE=0) JOIN AM_HOLISTICAPPLICATION_OWNERS ho ON (hai.HAID=ho.HAID) JOIN AM_UserPasswordTable pass ON (ho.OWNERID=pass.USERID) WHERE pass.USERNAME='" + owner + "' order by RESOURCEID\t";
/*       */ 
/*       */ 
/*       */ 
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */       }
/*  1007 */       else if (Constants.subGroupsEnabled.equals("true"))
/*       */       {
/*  1009 */         query = "select RESOURCENAME,RESOURCEID,DISPLAYNAME,PARENTID from AM_HOLISTICAPPLICATION hai  join AM_ManagedObject mo on (mo.RESOURCEID=hai.HAID) left outer join AM_PARENTCHILDMAPPER pcm on (pcm.CHILDID=mo.RESOURCEID) order by PARENTID,DISPLAYNAME";
/*       */         
/*       */ 
/*       */ 
/*  1013 */         if (DBQueryUtil.isPgsql())
/*       */         {
/*  1015 */           query = "SELECT RESOURCENAME , RESOURCEID , DISPLAYNAME , PARENTID FROM AM_HOLISTICAPPLICATION JOIN AM_ManagedObject ON AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID LEFT OUTER JOIN AM_PARENTCHILDMAPPER ON AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID ORDER BY PARENTID NULLS FIRST , UPPER(DISPLAYNAME)";
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*  1023 */         query = "select mo.RESOURCENAME , mo.RESOURCEID, mo.DISPLAYNAME from AM_ManagedObject mo JOIN AM_HOLISTICAPPLICATION hai ON (mo.RESOURCEID=hai.HAID and hai.TYPE=0) ORDER BY RESOURCEID";
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  1028 */       AMLog.debug("Reports : getHolisticApps Query " + query);
/*  1029 */       ArrayList rows = new ArrayList();
/*  1030 */       boolean isMonitors = false;
/*  1031 */       if (Constants.subGroupsEnabled.equals("true"))
/*       */       {
/*  1033 */         ArrayList completeList = new ArrayList();
/*       */         
/*       */ 
/*  1036 */         if ((privilegedUser) && (!EnterpriseUtil.isIt360MSPEdition()))
/*       */         {
/*  1038 */           ArrayList rows1 = new ArrayList();
/*  1039 */           ArrayList groupsList = DBUtil.getRows("select RESOURCENAME,RESOURCEID,DISPLAYNAME,PARENTID from AM_HOLISTICAPPLICATION left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID order by PARENTID,RESOURCENAME");
/*  1040 */           AMLog.debug("Reports : " + query);
/*       */           
/*  1042 */           set = AMConnectionPool.executeQueryStmt(query);
/*  1043 */           AMLog.debug("Reports : " + query);
/*  1044 */           while (set.next())
/*       */           {
/*  1046 */             ArrayList tempList = new ArrayList();
/*  1047 */             tempList.add(set.getString(1));
/*  1048 */             tempList.add(set.getString(2));
/*  1049 */             tempList.add(set.getString(3));
/*  1050 */             rows1.add(tempList);
/*       */           }
/*       */           
/*  1053 */           System.out.println("the list of groups assigned to operator are====>" + rows1);
/*       */           
/*  1055 */           rows1 = ReportUtil.getGroupOrigin(groupsList, rows1);
/*  1056 */           for (int i = 0; i < rows1.size(); i++)
/*       */           {
/*  1058 */             ArrayList InsideRows = (ArrayList)rows1.get(i);
/*  1059 */             Properties dataProps = new Properties();
/*  1060 */             dataProps.setProperty("label", (String)InsideRows.get(0));
/*  1061 */             dataProps.setProperty("value", (String)InsideRows.get(1));
/*  1062 */             rows.add(dataProps);
/*       */           }
/*  1064 */           System.out.println("the operator rows are===>" + rows);
/*  1065 */           AMConnectionPool.closeStatement(set);
/*  1066 */           Collections.sort(rows, new NameComparator());
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*  1071 */           completeList = DBUtil.getRows(query);
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1077 */           DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
/*  1078 */           getHierarchicalNodesFromList(rootNode, completeList);
/*  1079 */           request.setAttribute("MGTreeNode", rootNode);
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1087 */         if (EnterpriseUtil.isAdminServer())
/*       */         {
/*  1089 */           for (int i = 0; i < completeList.size(); i++)
/*       */           {
/*  1091 */             isMonitors = true;
/*  1092 */             ArrayList InsideRows = (ArrayList)completeList.get(i);
/*  1093 */             Properties dataProps = new Properties();
/*       */             
/*  1095 */             if (Integer.parseInt((String)InsideRows.get(1)) >= EnterpriseUtil.RANGE) {
/*  1096 */               String ManagedServerName = getManagedServerNameWithPort((String)InsideRows.get(1));
/*  1097 */               String labelName = (String)InsideRows.get(0) + "(" + ManagedServerName + ")";
/*  1098 */               dataProps.setProperty("label", labelName);
/*       */             }
/*       */             else {
/*  1101 */               dataProps.setProperty("label", (String)InsideRows.get(0));
/*       */             }
/*  1103 */             dataProps.setProperty("value", (String)InsideRows.get(1));
/*  1104 */             rows.add(dataProps);
/*       */           }
/*  1106 */           System.out.println("the rows==>" + rows);
/*       */         }
/*       */         else
/*       */         {
/*  1110 */           for (int i = 0; i < completeList.size(); i++)
/*       */           {
/*  1112 */             isMonitors = true;
/*       */             
/*  1114 */             ArrayList InsideRows = (ArrayList)completeList.get(i);
/*  1115 */             Properties dataProps = new Properties();
/*  1116 */             dataProps.setProperty("label", (String)InsideRows.get(0));
/*  1117 */             dataProps.setProperty("value", (String)InsideRows.get(1));
/*  1118 */             rows.add(dataProps);
/*       */           }
/*       */           
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  1127 */         set = AMConnectionPool.executeQueryStmt(query);
/*  1128 */         AMLog.debug("Constants.subGroupsEnabled is False :: Query : " + query);
/*       */         
/*  1130 */         if (EnterpriseUtil.isAdminServer()) {
/*  1131 */           while (set.next()) {
/*  1132 */             isMonitors = true;
/*  1133 */             Properties dataProps = new Properties();
/*  1134 */             if (set.getInt(2) >= EnterpriseUtil.RANGE) {
/*  1135 */               String ManagedServerName = getManagedServerNameWithPort(String.valueOf(set.getInt(2)));
/*  1136 */               String labelName = set.getString(3) + "(" + ManagedServerName + ")";
/*  1137 */               dataProps.setProperty("label", labelName);
/*       */             }
/*       */             else {
/*  1140 */               dataProps.setProperty("label", set.getString(3));
/*       */             }
/*  1142 */             dataProps.setProperty("value", String.valueOf(set.getInt(2)));
/*  1143 */             rows.add(dataProps);
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*  1148 */         while (set.next()) {
/*  1149 */           isMonitors = true;
/*  1150 */           Properties dataProps = new Properties();
/*  1151 */           dataProps.setProperty("label", set.getString(3));
/*  1152 */           dataProps.setProperty("value", String.valueOf(set.getInt(2)));
/*  1153 */           rows.add(dataProps);
/*       */         }
/*       */         
/*       */ 
/*  1157 */         AMConnectionPool.closeStatement(set);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  1162 */       if (isMonitors) {
/*  1163 */         Properties dataProps1 = new Properties();
/*  1164 */         dataProps1.setProperty("label", FormatUtil.getString("All Monitor Groups"));
/*  1165 */         dataProps1.setProperty("value", "all");
/*  1166 */         rows.add(dataProps1);
/*       */       }
/*  1168 */       System.out.println("the diff list rows==>" + rows);
/*       */       
/*       */ 
/*  1171 */       Properties p1 = new Properties();
/*  1172 */       for (int u = 0; u < rows.size(); u++) {
/*  1173 */         p1 = (Properties)rows.get(0);
/*       */       }
/*       */       
/*  1176 */       String quer = "Select ID,Name from AM_BUSINESSHOURSDETAILS";
/*  1177 */       ArrayList bh = new ArrayList();
/*  1178 */       set = AMConnectionPool.executeQueryStmt(quer);
/*       */       
/*  1180 */       while (set.next()) {
/*  1181 */         Properties dataProps1 = new Properties();
/*  1182 */         dataProps1.setProperty("label", set.getString("Name"));
/*  1183 */         dataProps1.setProperty("value", set.getString("ID"));
/*  1184 */         bh.add(dataProps1); }
/*  1185 */       AMConnectionPool.closeStatement(set);
/*       */       
/*  1187 */       ReportForm rf = (ReportForm)form;
/*       */       
/*  1189 */       String ID = rf.getHaid();
/*  1190 */       if (ID == null) {
/*  1191 */         ID = p1.getProperty("value");
/*       */       }
/*  1193 */       ArrayList capacitPlanningRows = new ArrayList();
/*       */       try
/*       */       {
/*  1196 */         List cpHaids = getHAIDforResourceTypes();
/*  1197 */         for (int i = 0; i < rows.size(); i++)
/*       */         {
/*  1199 */           Properties MGProps = (Properties)rows.get(i);
/*  1200 */           String haid = MGProps.getProperty("value");
/*  1201 */           if (haid.equalsIgnoreCase("all"))
/*       */           {
/*  1203 */             capacitPlanningRows.add(MGProps);
/*       */ 
/*       */           }
/*  1206 */           else if (cpHaids.contains(haid)) {
/*  1207 */             capacitPlanningRows.add(MGProps);
/*       */           }
/*       */           
/*       */         }
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/*  1214 */         e.printStackTrace();
/*       */       }
/*       */       
/*  1217 */       ArrayList al = getHAAttributes(ID);
/*       */       
/*  1219 */       ((ReportForm)form).setCapacityPlanningOptions("0");
/*  1220 */       ((ReportForm)form).setApplications(rows);
/*  1221 */       ((ReportForm)form).setDurations(al);
/*  1222 */       ((ReportForm)form).setBusinessrules(bh);
/*  1223 */       ((ReportForm)form).setVmapplications(capacitPlanningRows);
/*       */     } catch (Exception exp) {
/*  1225 */       AMLog.fatal("Reports :  Exception ", exp);
/*  1226 */       request.setAttribute("heading", "0");
/*  1227 */       request.setAttribute("strTime", "0");
/*  1228 */       exp.printStackTrace();
/*  1229 */       throw new Exception(exp);
/*       */     }
/*       */     finally {
/*  1232 */       closeResultSet(set);
/*  1233 */       set = null;
/*       */     }
/*       */   }
/*       */   
/*       */   private void getHierarchicalNodesFromList(DefaultMutableTreeNode rootNode, ArrayList<ArrayList<String>> completeList)
/*       */   {
/*  1239 */     String isCustIdQuery = "select RESOURCEID from AM_HOLISTICAPPLICATION_EXT where APP_TYPE='CUSTOMER'";
/*  1240 */     List<String> custIdList = new ArrayList();
/*  1241 */     Map<String, DefaultMutableTreeNode> idNode = new HashMap();
/*  1242 */     ResultSet rs = null;
/*       */     try {
/*  1244 */       rs = AMConnectionPool.executeQueryStmt(isCustIdQuery);
/*  1245 */       while (rs.next()) {
/*  1246 */         custIdList.add(rs.getString(0));
/*       */       }
/*       */     } catch (Exception exc) {
/*  1249 */       exc.printStackTrace();
/*       */     } finally {
/*  1251 */       AMConnectionPool.closeResultSet(rs);
/*       */     }
/*  1253 */     long startTime = System.currentTimeMillis();
/*  1254 */     long itrcount = 0L;
/*  1255 */     long init = 0L;
/*  1256 */     for (ArrayList<String> list : completeList)
/*       */     {
/*       */ 
/*  1259 */       if ((list.get(2) != null) && (list.get(1) != null))
/*       */       {
/*       */ 
/*  1262 */         String resourceID = (String)list.get(1);
/*  1263 */         String parentID = (String)list.get(3);
/*  1264 */         Properties prop = new Properties();
/*  1265 */         prop.setProperty("label", resourceID);
/*  1266 */         prop.setProperty("value", (String)list.get(2));
/*  1267 */         if ((parentID == null) || (parentID.equals("null")) || (custIdList.contains(parentID)))
/*       */         {
/*  1269 */           prop.setProperty("isParent", "true");
/*  1270 */           DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(resourceID);
/*  1271 */           childNode.setUserObject(prop);
/*  1272 */           rootNode.add(childNode);
/*  1273 */           idNode.put(resourceID, childNode);
/*       */         }
/*       */         else
/*       */         {
/*  1277 */           prop.setProperty("parentID", parentID);
/*  1278 */           findParentAndAddNode(rootNode, prop, idNode);
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private void findParentAndAddNode(DefaultMutableTreeNode rootNode, Properties prop, Map<String, DefaultMutableTreeNode> idNode) {
/*  1285 */     String parentID = prop.getProperty("parentID");
/*  1286 */     String resourceID = prop.getProperty("label");
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1293 */     if (idNode.containsKey(parentID))
/*       */     {
/*  1295 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)idNode.get(parentID);
/*  1296 */       Object tempObject = node.getUserObject();
/*  1297 */       Properties temp = (Properties)tempObject;
/*  1298 */       if (temp.getProperty("label").equals(parentID))
/*       */       {
/*  1300 */         boolean isLastLeaf = false;
/*  1301 */         if (node.getChildCount() > 0)
/*       */         {
/*  1303 */           for (Enumeration<DefaultMutableTreeNode> enu1 = node.children(); enu1.hasMoreElements();)
/*       */           {
/*  1305 */             DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)enu1.nextElement();
/*  1306 */             ((Properties)childNode.getUserObject()).setProperty("isLastLeaf", "false");
/*       */           }
/*  1308 */           isLastLeaf = true;
/*       */         }
/*       */         else
/*       */         {
/*  1312 */           isLastLeaf = true;
/*       */         }
/*  1314 */         DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(resourceID);
/*  1315 */         prop.setProperty("isLastLeaf", Boolean.toString(isLastLeaf));
/*  1316 */         newChild.setUserObject(prop);
/*  1317 */         node.add(newChild);
/*  1318 */         idNode.put(resourceID, newChild);
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*  1325 */       NmsLogMgr.MISCUSER.log("AMREPORTACTIONS Node Not ADDED PID " + parentID + " RID " + resourceID, 4);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public ActionForward getDivIdsToDisplay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  1333 */     AMLog.debug("Inside getDivIdsToDisplay");
/*       */     try {
/*  1335 */       ReportForm rf = (ReportForm)form;
/*  1336 */       String monitorType = request.getParameter("monitorType");
/*  1337 */       String divids = request.getParameter("divIds");
/*  1338 */       String attributes = request.getParameter("attributes");
/*  1339 */       StringBuilder toreturn = new StringBuilder();
/*  1340 */       StringTokenizer divs = new StringTokenizer(divids, ",");
/*       */       
/*  1342 */       while (divs.hasMoreTokens()) {
/*  1343 */         String divid = divs.nextToken();
/*  1344 */         toreturn.append("if(document.getElementById(\"" + divid + "\")){");
/*  1345 */         toreturn.append("document.getElementById(\"" + divid + "\").style.display = \"block\";");
/*  1346 */         toreturn.append("}\n");
/*       */       }
/*  1348 */       HashMap displayDivmap = ReportUtil.getDisplayDivmap();
/*  1349 */       StringTokenizer att = new StringTokenizer(attributes, ",");
/*  1350 */       while (att.hasMoreTokens()) {
/*  1351 */         String attId = att.nextToken();
/*  1352 */         ArrayList divList = (ArrayList)displayDivmap.get(attId);
/*  1353 */         if ((attId.equals("responsetime")) && ((monitorType.equals("Exchange-server")) || (monitorType.equals("MAIL-server")) || (monitorType.equals("Exchange-server','MAIL-server")))) {
/*  1354 */           attId = "M" + attId;
/*       */         }
/*  1356 */         if (!divList.contains(monitorType)) {
/*  1357 */           toreturn.append("if(document.getElementById(\"" + attId + "_1\")){");
/*  1358 */           toreturn.append("document.getElementById(\"" + attId + "_1\").style.display = \"none\";");
/*  1359 */           toreturn.append("}\n");
/*  1360 */           toreturn.append("if(document.getElementById(\"" + attId + "_2\")){");
/*  1361 */           toreturn.append("document.getElementById(\"" + attId + "_2\").style.display = \"none\";");
/*  1362 */           toreturn.append("}\n");
/*       */         }
/*       */       }
/*  1365 */       PrintWriter pw = response.getWriter();
/*  1366 */       pw.print(toreturn.toString());
/*       */     }
/*       */     catch (Exception e) {
/*  1369 */       AMLog.debug("Exception in getDivIdsToDisplay()");
/*       */     }
/*  1371 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward sendAttributeDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  1375 */     try { if (request.getParameter("hid") != null)
/*       */       {
/*  1377 */         ArrayList a1 = getHAAttributes(request.getParameter("hid"));
/*  1378 */         response.setContentType("text/html;charset=UTF-8");
/*  1379 */         PrintWriter pw = response.getWriter();
/*  1380 */         pw.write("<select name=\"sunday\" class=\"formtextselected chosenselect\" style=\"width:250px\" onchange=\"javascript:attributeForm(this.form)\">");
/*       */         
/*  1382 */         for (int i = 0; i < a1.size(); i++) {
/*  1383 */           Properties p1 = (Properties)a1.get(i);
/*  1384 */           String label = p1.getProperty("label");
/*  1385 */           String value = p1.getProperty("value");
/*  1386 */           pw.write("<option value=\"" + value + "\">" + label + "</option>");
/*       */         }
/*  1388 */         pw.write("</select>");
/*       */       }
/*       */     }
/*       */     catch (Exception es) {
/*  1392 */       es.printStackTrace();
/*       */     }
/*  1394 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward sendAttributeDetailsForMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*       */     try {
/*  1400 */       String restype = request.getParameter("restype");
/*  1401 */       if (restype != null)
/*       */       {
/*  1403 */         String groupname = request.getParameter("groupname");
/*  1404 */         String divname = request.getParameter("divname");
/*  1405 */         ArrayList a1 = ReportUtilities.getAttributeListForResourcetype(restype);
/*       */         
/*  1407 */         response.setContentType("text/html;charset=UTF-8");
/*  1408 */         PrintWriter pw = response.getWriter();
/*  1409 */         pw.write(divname + "#<select name='" + groupname + "' id ='" + groupname + "'class=\"formtextselected\" style=\"width:150px\" onchange=\"javascript:attributeFormAction('" + restype + "','" + groupname + "')\">");
/*       */         
/*  1411 */         pw.write("<option value=\"att\">---Select Attribute Type---</option>");
/*  1412 */         for (int i = 0; i < a1.size(); i++) {
/*  1413 */           Properties p1 = (Properties)a1.get(i);
/*  1414 */           String label = p1.getProperty("label");
/*  1415 */           String value = p1.getProperty("value");
/*  1416 */           pw.write("<option value=\"" + value + "\">" + label + "</option>");
/*       */         }
/*       */         
/*       */ 
/*  1420 */         pw.write("</select>");
/*       */       }
/*       */     }
/*       */     catch (Exception es) {
/*  1424 */       es.printStackTrace();
/*       */     }
/*  1426 */     return null;
/*       */   }
/*       */   
/*       */   public ArrayList getHAAttributes(String haid) {
/*  1430 */     ResultSet set = null;
/*  1431 */     ArrayList atts = new ArrayList();
/*  1432 */     Properties p1 = new Properties();
/*  1433 */     p1.setProperty("label", "-----" + FormatUtil.getString("am.reporttab.selectreports.text") + "-----");
/*  1434 */     p1.setProperty("value", "select");
/*  1435 */     atts.add(p1);
/*  1436 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  1437 */     ReportForm rf = new ReportForm();
/*  1438 */     Hashtable allservers = ReportUtil.getserversTypes();
/*  1439 */     String query = "select DISTINCT AM_ManagedResourceType.RESOURCEGROUP as RESGROUP from AM_ManagedObject ,AM_PARENTCHILDMAPPER,AM_ManagedResourceType where AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID='" + haid + "' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE ORDER BY RESOURCEGROUP";
/*       */     
/*       */     try
/*       */     {
/*  1443 */       HashMap map = new HashMap();
/*  1444 */       map.put("APP", FormatUtil.getString("Application Servers-JVM Details") + ":jvm_" + (String)allservers.get("Application servers") + "&" + FormatUtil.getString("Application Servers-JDBC Connection Pool Usage") + ":jdbc_" + (String)allservers.get("Application servers") + "&" + FormatUtil.getString("Application Servers-Thread Details") + ":thread_" + (String)allservers.get("Application servers") + "&" + FormatUtil.getString("Application Servers-HTTP Session Details") + ":session_" + (String)allservers.get("Application servers") + "&" + FormatUtil.getString("Application Servers-Request Throughput") + ":throughput_" + (String)allservers.get("Application servers") + "&" + FormatUtil.getString("Application Servers-Web Application Throughput") + ":webappthroughput_" + (String)allservers.get("Application servers"));
/*  1445 */       map.put("DBS", FormatUtil.getString("Databases-Buffer Hit Ratio") + ":buffer_" + (String)allservers.get("Database Servers") + "&" + FormatUtil.getString("Databases-Cache Hit Ratio") + ":cache_" + (String)allservers.get("Database Servers"));
/*  1446 */       map.put("SYS", FormatUtil.getString("Servers-CPU Usage") + ":cpuid_" + (String)allservers.get("Servers") + "&" + FormatUtil.getString("Servers-Memory Usage") + ":mem_" + (String)allservers.get("Servers") + "&" + FormatUtil.getString("Servers-Disk Usage") + ":disk_" + (String)allservers.get("Servers"));
/*  1447 */       map.put("URL", FormatUtil.getString("Web Services-Operation Execution Time") + ":operationExecutionTime_" + (String)allservers.get("Web Services"));
/*  1448 */       map.put("TM", FormatUtil.getString("Java Runtime-CPU Usage") + ":jdkcpuid_" + (String)allservers.get("Java-Transactions") + "&" + FormatUtil.getString("Java Runtime-Memory Usage") + ":memmb_" + (String)allservers.get("Java-Transactions"));
/*       */       
/*  1450 */       String type = FormatUtil.getString("am.webclient.sap.server.type");
/*  1451 */       StringBuffer buf = new StringBuffer();
/*  1452 */       buf.append(type).append("-").append(FormatUtil.getString("CPU Utilization")).append(":sapcpu_").append((String)allservers.get("ERP Servers"));
/*  1453 */       buf.append("&").append(type).append("-").append(FormatUtil.getString("ESACT")).append(":sapmemory_").append((String)allservers.get("ERP Servers"));
/*  1454 */       buf.append("&").append(type).append("-").append(FormatUtil.getString("Disk Utilization")).append(":sapdisk_").append((String)allservers.get("ERP Servers"));
/*  1455 */       buf.append("&").append(type).append("-").append(FormatUtil.getString("PAGEIN")).append(":sappagein_").append((String)allservers.get("ERP Servers"));
/*  1456 */       buf.append("&").append(type).append("-").append(FormatUtil.getString("PAGEOUT")).append(":sappageout_").append((String)allservers.get("ERP Servers"));
/*  1457 */       buf.append("&").append(type).append("-").append(FormatUtil.getString("SUTILIZATION")).append(":sutilization_").append((String)allservers.get("ERP Servers"));
/*  1458 */       buf.append("&").append(type).append("-").append(FormatUtil.getString("BUTILIZATION")).append(":butilization_").append((String)allservers.get("ERP Servers"));
/*  1459 */       buf.append("&").append(type).append("-").append(FormatUtil.getString("FRONTENDRESPONSETIME")).append(":sapferestime_").append((String)allservers.get("ERP Servers"));
/*  1460 */       buf.append("&").append(type).append("-").append(FormatUtil.getString("ENQUEUEREQUESTS")).append(":sapenqreq_").append((String)allservers.get("ERP Servers"));
/*       */       
/*  1462 */       map.put("ERP", buf.toString());
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1469 */       Vector v = new Vector();
/*  1470 */       Vector tempvector = new Vector();
/*  1471 */       tempvector.add(haid);
/*       */       
/*  1473 */       if ((haid != null) && (!"all".equals(haid)) && (!"allmonitors".equals(haid)) && (haid.indexOf(",") == -1)) {
/*  1474 */         v = ParentChildRelationalUtil.getResourceGroupOfChildMonitors(tempvector, haid);
/*       */       }
/*  1476 */       ArrayList allArrays = new ArrayList();
/*  1477 */       String restype = null;
/*       */       
/*  1479 */       if (v.size() > 0)
/*       */       {
/*  1481 */         for (int y = 0; y < v.size(); y++) {
/*  1482 */           String resgroup = (String)v.get(y);
/*       */           
/*  1484 */           String attri = (String)map.get(resgroup);
/*  1485 */           if (attri != null) {
/*  1486 */             String attributes = attri;
/*       */             
/*  1488 */             String[] temp = attributes.split("&");
/*  1489 */             for (int r = 0; r < temp.length; r++) {
/*  1490 */               String[] t1 = temp[r].split(":");
/*  1491 */               Properties attprops = new Properties();
/*  1492 */               attprops.setProperty("label", t1[0]);
/*  1493 */               attprops.setProperty("value", t1[1]);
/*  1494 */               atts.add(attprops);
/*       */             }
/*       */           }
/*       */         }
/*       */         
/*  1499 */         for (int y = 0; y < v.size(); y++) {
/*  1500 */           String resgroup = (String)v.get(y);
/*  1501 */           if ("APP".equals(resgroup)) {
/*  1502 */             restype = (String)allservers.get("Application servers");
/*  1503 */             allArrays = rf.getAppArrayAttribute();
/*  1504 */           } else if ("DBS".equals(resgroup)) {
/*  1505 */             restype = (String)allservers.get("Database Servers");
/*  1506 */             allArrays = rf.getDbArrayAttribute();
/*  1507 */           } else if ("SYS".equals(resgroup)) {
/*  1508 */             restype = (String)allservers.get("Servers");
/*  1509 */             allArrays = rf.getServerArrayAttribute();
/*  1510 */           } else if ("URL".equals(resgroup)) {
/*  1511 */             restype = (String)allservers.get("Web Services");
/*  1512 */             allArrays = rf.getWebserviceArrayAttribute();
/*  1513 */             ArrayList webserverArray = rf.getWebserverArrayAttribute();
/*  1514 */             for (int h = 0; h < webserverArray.size(); h++) {
/*  1515 */               Properties p11 = (Properties)webserverArray.get(h);
/*  1516 */               allArrays.add(p11);
/*       */             }
/*  1518 */             ArrayList urlArray = rf.getUrlsArrayAttribute();
/*  1519 */             for (int h = 0; h < urlArray.size(); h++) {
/*  1520 */               Properties p11 = (Properties)urlArray.get(h);
/*  1521 */               allArrays.add(p11);
/*       */             }
/*  1523 */           } else if ("TM".equals(resgroup)) {
/*  1524 */             restype = (String)allservers.get("Java-Transactions");
/*  1525 */             allArrays = rf.getTransactionArrayAttribute();
/*  1526 */           } else if ("ERP".equals(resgroup)) {
/*  1527 */             restype = (String)allservers.get("ERP Servers");
/*  1528 */             allArrays = rf.getErpArrayAttribute();
/*  1529 */           } else if ("MS".equals(resgroup)) {
/*  1530 */             restype = (String)allservers.get("Mail Servers");
/*  1531 */             allArrays = rf.getMailserverArrayAttribute();
/*  1532 */           } else if ("SER".equals(resgroup)) {
/*  1533 */             restype = (String)allservers.get("Services");
/*  1534 */             allArrays = rf.getServicesArrayAttribute();
/*  1535 */           } else if ("MOM".equals(resgroup)) {
/*  1536 */             restype = (String)allservers.get("Middleware Servers");
/*  1537 */             allArrays = rf.getMomArrayAttribute();
/*  1538 */           } else if ("VIR".equals(resgroup)) {
/*  1539 */             restype = (String)allservers.get("Virtualization");
/*  1540 */             allArrays = rf.getVirserverArrayAttribute();
/*       */           }
/*  1542 */           else if ("CLD".equals(resgroup)) {
/*  1543 */             restype = (String)allservers.get("Cloud Apps");
/*  1544 */             allArrays = rf.getCloudAppsArrayAttribute();
/*       */           }
/*       */           
/*  1547 */           for (int h = 0; h < allArrays.size(); h++) {
/*  1548 */             Properties p11 = (Properties)allArrays.get(h);
/*       */             
/*  1550 */             String label = p11.getProperty("label");
/*  1551 */             String value = p11.getProperty("value");
/*  1552 */             Properties attprops1 = new Properties();
/*  1553 */             attprops1.setProperty("label", label);
/*  1554 */             if (!"resource".equals(value)) {
/*  1555 */               String[] tempArray = value.split("#");
/*       */               
/*       */ 
/*  1558 */               String newValue = tempArray[1] + "_" + tempArray[0];
/*       */               
/*       */ 
/*  1561 */               attprops1.setProperty("value", newValue);
/*       */             } else {
/*  1563 */               attprops1.setProperty("value", value);
/*       */             }
/*       */             
/*       */ 
/*  1567 */             atts.add(attprops1);
/*       */ 
/*       */ 
/*       */ 
/*       */           }
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*  1591 */       ex.printStackTrace();
/*       */     }
/*  1593 */     return atts;
/*       */   }
/*       */   
/*       */   private static String getManagedServerNameWithPort(String resourceId) {
/*  1597 */     String managedServerName = "";
/*       */     try {
/*  1599 */       int rangeId = Integer.parseInt(resourceId) / EnterpriseUtil.RANGE;
/*  1600 */       rangeId *= EnterpriseUtil.RANGE;
/*  1601 */       String query = "select DISPLAYNAME AS NAME from AM_MAS_SERVER where ALLOTED_GLOBAL_RANGE =" + rangeId;
/*  1602 */       ResultSet set = null;
/*  1603 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  1604 */       set = AMConnectionPool.executeQueryStmt(query);
/*  1605 */       while (set.next()) {
/*  1606 */         managedServerName = set.getString("NAME");
/*       */       }
/*       */     }
/*       */     catch (Exception e) {}
/*       */     
/*       */ 
/*  1612 */     return managedServerName;
/*       */   }
/*       */   
/*       */   private final void getMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  1618 */     String excludeIntfCond = " and AM_ManagedObject.type not like ('OpManager-Interface%') ";
/*  1619 */     if ((request.getAttribute("excludeExtDevicesCond") != null) && ("true".equalsIgnoreCase(request.getAttribute("excludeExtDevicesCond").toString())))
/*       */     {
/*  1621 */       excludeIntfCond = " and AM_ManagedObject.type not like ('OpManager%') and AM_ManagedObject.type not like ('OpStor%')";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1632 */     ResultSet set = null;
/*  1633 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  1634 */     String query = null;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  1639 */     if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  1640 */       String owner = request.getRemoteUser();
/*  1641 */       Vector resourceids = new Vector();
/*  1642 */       if (Constants.subGroupsEnabled.equals("true"))
/*       */       {
/*  1644 */         resourceids = ReportUtilities.getResourceIdentity(owner, Boolean.valueOf(true), request);
/*       */       }
/*       */       else
/*       */       {
/*  1648 */         resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */       }
/*       */       
/*  1651 */       query = "SELECT RESOURCEID,DISPLAYNAME FROM AM_ManagedObject where " + ReportUtilities.getCondition("RESOURCEID", resourceids) + "  and TYPE in " + Constants.resourceTypes + excludeIntfCond + " ORDER BY UPPER(DISPLAYNAME)";
/*       */     }
/*       */     else
/*       */     {
/*  1655 */       query = "SELECT RESOURCEID,DISPLAYNAME FROM AM_ManagedObject where TYPE in " + Constants.resourceTypes + excludeIntfCond + " ORDER BY UPPER(DISPLAYNAME)";
/*       */     }
/*       */     
/*  1658 */     if (Constants.sqlManager)
/*       */     {
/*  1660 */       if (privilegedUser) {
/*  1661 */         Vector resids = ReportUtilities.getResourceIdentity(request.getRemoteUser());
/*  1662 */         query = "SELECT RESOURCEID,DISPLAYNAME FROM AM_ManagedObject where TYPE in " + Constants.sqlManagerresourceTypes + excludeIntfCond + " and " + ReportUtilities.getCondition("RESOURCEID", resids) + " ORDER BY DISPLAYNAME";
/*       */       } else {
/*  1664 */         query = "SELECT RESOURCEID,DISPLAYNAME FROM AM_ManagedObject where TYPE in " + Constants.sqlManagerresourceTypes + excludeIntfCond + " ORDER BY DISPLAYNAME";
/*       */       }
/*       */     }
/*       */     try {
/*  1668 */       set = AMConnectionPool.executeQueryStmt(query);
/*  1669 */       AMLog.debug("Reports : " + query);
/*  1670 */       ArrayList rows = new ArrayList();
/*  1671 */       while (set.next()) {
/*  1672 */         Properties dataProps = new Properties();
/*       */         
/*  1674 */         String monName = set.getString(2);
/*  1675 */         monName = EnterpriseUtil.decodeString(monName);
/*  1676 */         monName = VMReportUtilities.escape(monName, false).replaceAll("\"", "");
/*  1677 */         dataProps.setProperty("label", monName);
/*  1678 */         dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/*  1679 */         rows.add(dataProps);
/*       */       }
/*  1681 */       AMConnectionPool.closeStatement(set);
/*  1682 */       ((ReportForm)form).setMonitors(rows);
/*       */     }
/*       */     catch (Exception exp) {
/*  1685 */       AMLog.fatal("Reports :  Exception ", exp);
/*  1686 */       request.setAttribute("heading", "0");
/*  1687 */       request.setAttribute("strTime", "0");
/*  1688 */       throw new Exception(exp);
/*       */     }
/*       */     finally {
/*  1691 */       closeResultSet(set);
/*  1692 */       set = null;
/*       */     }
/*       */   }
/*       */   
/*       */   public ActionForward generateHealthReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*  1698 */     ActionMessages messages = new ActionMessages();
/*  1699 */     ResultSet set = null;
/*  1700 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  1701 */     ReportForm controls = (ReportForm)form;
/*  1702 */     String resourceType = controls.getResourceType();
/*  1703 */     getHolisticApps(mapping, form, request, response);
/*  1704 */     getMonitors(mapping, form, request, response);
/*  1705 */     request.setAttribute("HelpKey", getMonitorHelpKey(resourceType));
/*  1706 */     getCustomApplications(request, "3");
/*  1707 */     if (!areMonitorsPresent(resourceType)) {
/*  1708 */       AMLog.debug("Reports : No data for " + resourceType);
/*  1709 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.monitors"));
/*  1710 */       saveMessages(request, messages);
/*  1711 */       request.setAttribute("heading", "0");
/*  1712 */       request.setAttribute("strTime", "0");
/*  1713 */       return mapping.findForward("report.message");
/*       */     }
/*       */     
/*  1716 */     String customfield = controls.getCustomfield();
/*  1717 */     String customValue = controls.getCustomFieldValue();
/*  1718 */     List datacollectionStartedMOs = null;
/*       */     
/*       */ 
/*       */ 
/*  1722 */     Vector resourceids = new Vector();
/*  1723 */     if ((customfield != null) && (customfield.equals("true")) && (customValue != null) && (customValue.indexOf("$") != -1)) {
/*  1724 */       datacollectionStartedMOs = MyFields.customFieldResourceIDs(customValue, resourceType);
/*       */     }
/*  1726 */     else if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  1727 */       if (Constants.isUserResourceEnabled()) {
/*  1728 */         resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */       } else {
/*  1730 */         String owner = request.getRemoteUser();
/*  1731 */         resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */       }
/*  1733 */       datacollectionStartedMOs = getDataCollectionStartedMonitors(EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resourceids), false);
/*       */     }
/*       */     else
/*       */     {
/*  1737 */       datacollectionStartedMOs = getDataCollectionStartedMonitors(resourceType);
/*       */     }
/*       */     
/*  1740 */     AMLog.debug("generateHealthReport data collection started MO Size " + datacollectionStartedMOs.size());
/*       */     
/*  1742 */     if (datacollectionStartedMOs.size() == 0) {
/*  1743 */       AMLog.debug("Reports : Data Collection not started for " + resourceType);
/*  1744 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodatacollection.monitors"));
/*  1745 */       saveMessages(request, messages);
/*  1746 */       request.setAttribute("heading", "0");
/*  1747 */       request.setAttribute("strTime", "0");
/*  1748 */       return mapping.findForward("report.message");
/*       */     }
/*       */     
/*  1751 */     String nodata = "report.nodata";
/*       */     
/*  1753 */     String scheduleid = request.getParameter("scheduleid");
/*  1754 */     int cust = -1;
/*  1755 */     int site = -1;
/*       */     
/*       */ 
/*  1758 */     if ((EnterpriseUtil.isIt360MSPEdition()) && (scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */     {
/*  1760 */       ArrayList al = EnterpriseUtil.getCustomerSiteFromScheduleDetails(scheduleid);
/*  1761 */       if ((al != null) && (al.size() >= 2))
/*       */       {
/*  1763 */         cust = Integer.parseInt(al.get(0).toString());
/*  1764 */         site = Integer.parseInt(al.get(1).toString());
/*       */       }
/*       */     }
/*       */     
/*  1768 */     if (hasAnyData(resourceType)) {
/*  1769 */       nodata = "report.nodata.time";
/*       */     }
/*       */     
/*  1772 */     String period = controls.getPeriod();
/*  1773 */     AMLog.debug("Reports : Generating Health Report for " + resourceType + " period " + period);
/*  1774 */     long[] timeStamps = null;
/*  1775 */     if ((controls.getStartDate().equals("")) || (controls.getEndDate().equals(""))) {
/*  1776 */       timeStamps = ReportUtilities.getTimeStamp(period);
/*       */     }
/*       */     else {
/*  1779 */       controls.setPeriod("4");
/*       */       try {
/*  1781 */         timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */       } catch (IllegalArgumentException iae) {
/*  1783 */         String errMsg = iae.getMessage();
/*  1784 */         AMLog.debug("Reports :  " + errMsg);
/*  1785 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  1786 */         saveMessages(request, messages);
/*  1787 */         request.setAttribute("heading", "0");
/*  1788 */         request.setAttribute("strTime", "0");
/*  1789 */         return mapping.findForward("report.message");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1794 */     String query = "";
/*       */     
/*       */ 
/*       */     String test;
/*       */     
/*       */ 
/*       */     String tempResourceType;
/*       */     
/*  1802 */     if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1808 */       if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */       {
/*  1810 */         resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */       }
/*       */       
/*  1813 */       query = getTopNValues(DBQueryUtil.getDBQuery("am.reportaction.generatehealthreport.operator.query", new String[] { ReportUtilities.getCondition("RESOURCEID", resourceids), resourceType, String.valueOf(timeStamps[0]), String.valueOf(timeStamps[1]) }), String.valueOf(controls.getNumberOfRows()));
/*       */     }
/*       */     else
/*       */     {
/*  1817 */       if (EnterpriseUtil.isAdminServer())
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1827 */         StringTokenizer token = new StringTokenizer(resourceType, ",");
/*  1828 */         test = "";
/*  1829 */         tempResourceType = "";
/*  1830 */         while (token.hasMoreTokens())
/*       */         {
/*  1832 */           test = token.nextToken();
/*  1833 */           if (!test.contains("OpManager-Interface"))
/*       */           {
/*  1835 */             tempResourceType = tempResourceType + test + ",";
/*       */           }
/*       */         }
/*  1838 */         tempResourceType = tempResourceType.substring(0, tempResourceType.length() - 1);
/*  1839 */         if (tempResourceType.startsWith("'"))
/*       */         {
/*  1841 */           tempResourceType = tempResourceType.substring(1);
/*       */         }
/*  1843 */         if (tempResourceType.endsWith("'"))
/*       */         {
/*  1845 */           tempResourceType = tempResourceType.substring(0, tempResourceType.length() - 1);
/*       */         }
/*  1847 */         resourceType = tempResourceType;
/*  1848 */         AMLog.debug(" resourceType after replacing interfaces " + resourceType);
/*       */       }
/*  1850 */       query = getTopNValues(DBQueryUtil.getDBQuery("am.reportaction.generatehealthreport.query", new String[] { resourceType, String.valueOf(timeStamps[0]), String.valueOf(timeStamps[1]) }), String.valueOf(controls.getNumberOfRows()));
/*       */     }
/*  1852 */     if ((customfield != null) && (customfield.equals("true")) && (customValue != null) && (customValue.indexOf("$") != -1)) {
/*  1853 */       Vector resids = MyFields.getcustomFieldResidinVector(customValue, resourceType);
/*  1854 */       query = getTopNValues(DBQueryUtil.getDBQuery("am.reportaction.generatehealthreport.operator.query", new String[] { ReportUtilities.getCondition("RESOURCEID", resids), resourceType, String.valueOf(timeStamps[0]), String.valueOf(timeStamps[1]) }), String.valueOf(controls.getNumberOfRows()));
/*       */     }
/*       */     try {
/*  1857 */       ArrayList data = ReportUtilities.getTabularData(query, false);
/*  1858 */       if (data.size() == 0) {
/*  1859 */         AMLog.debug("Reports : No data for " + resourceType);
/*       */         
/*  1861 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(nodata));
/*  1862 */         saveMessages(request, messages);
/*  1863 */         request.setAttribute("heading", "0");
/*  1864 */         request.setAttribute("strTime", "0");
/*  1865 */         return mapping.findForward("report.message");
/*       */       }
/*  1867 */       singleData = (ArrayList)data.get(0);
/*  1868 */       request.setAttribute("period", period);
/*  1869 */       request.setAttribute("strTime", new java.util.Date(Long.parseLong(singleData.get(5).toString())));
/*  1870 */       request.setAttribute("endTime", new java.util.Date(Long.parseLong(singleData.get(6).toString())));
/*  1871 */       request.setAttribute("data", data);
/*  1872 */       if (controls.getReporttype().equals("pdf")) {
/*  1873 */         request.setAttribute("report-type-template", "report.health");
/*  1874 */         return mapping.findForward("report.health.pdf");
/*       */       }
/*  1876 */       if (controls.getReporttype().equals("csv")) {
/*  1877 */         return mapping.findForward("report.health.csv");
/*       */       }
/*       */       
/*  1880 */       return mapping.findForward("report.health");
/*       */     }
/*       */     catch (Exception exp) {
/*       */       ArrayList singleData;
/*  1884 */       AMLog.fatal("Reports :  Exception ", exp);
/*  1885 */       request.setAttribute("heading", "0");
/*  1886 */       request.setAttribute("strTime", "0");
/*  1887 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  1888 */       saveMessages(request, messages);
/*  1889 */       return mapping.findForward("report.message");
/*       */     }
/*       */     finally {
/*  1892 */       closeResultSet(set);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward generateAttributeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  1904 */     ActionMessages messages = new ActionMessages();
/*  1905 */     ResultSet set = null;
/*  1906 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  1907 */     ReportForm controls = (ReportForm)form;
/*  1908 */     String resourceType = controls.getResourceType();
/*       */     
/*  1910 */     String attributeids = controls.getAttribute();
/*       */     
/*  1912 */     if ((attributeids == null) || (attributeids.equals("null")) || (attributeids.equals(""))) {
/*  1913 */       AMLog.debug("Reports : No data for " + resourceType + " because attributeid is " + attributeids);
/*  1914 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.nodata.text"));
/*  1915 */       saveMessages(request, messages);
/*  1916 */       request.setAttribute("heading", "0");
/*  1917 */       request.setAttribute("strTime", "0");
/*  1918 */       return mapping.findForward("report.message");
/*       */     }
/*  1920 */     ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(attributeids);
/*  1921 */     String archivedTableName = (String)attribDetails.get(0);
/*  1922 */     String expression = (String)attribDetails.get(1);
/*       */     
/*  1924 */     String hid = controls.getWorkingdays();
/*       */     
/*  1926 */     String attname = controls.getAttributeName();
/*  1927 */     String businessPeriod = controls.getBusinessPeriod();
/*       */     
/*  1929 */     getHolisticApps(mapping, form, request, response);
/*  1930 */     getMonitors(mapping, form, request, response);
/*  1931 */     getCustomApplications(request, "3");
/*  1932 */     String attribute = request.getParameter("attribute");
/*       */     
/*  1934 */     if ((request.getParameter("getpdf") != null) && (request.getParameter("getpdf").equals("true"))) {
/*  1935 */       hid = request.getParameter("hid");
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  1940 */     request.setAttribute("HelpKey", getMonitorHelpKey(resourceType));
/*  1941 */     if (!areMonitorsPresent(resourceType)) {
/*  1942 */       AMLog.debug("Reports : No data for " + resourceType);
/*  1943 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.monitors"));
/*  1944 */       saveMessages(request, messages);
/*  1945 */       request.setAttribute("heading", "0");
/*  1946 */       request.setAttribute("strTime", "0");
/*  1947 */       return mapping.findForward("report.message");
/*       */     }
/*  1949 */     String customfield = controls.getCustomfield();
/*  1950 */     String customValue = controls.getCustomFieldValue();
/*  1951 */     String customFieldCondition = "";
/*  1952 */     List datacollectionStartedMOs = null;
/*  1953 */     if ((customfield != null) && (customfield.equals("true")) && (customValue != null) && (customValue.indexOf("$") != -1)) {
/*  1954 */       datacollectionStartedMOs = MyFields.customFieldResourceIDs(customValue, resourceType);
/*  1955 */       customFieldCondition = " and " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", MyFields.getcustomFieldResidinVector(customValue, resourceType));
/*       */     }
/*       */     else {
/*  1958 */       datacollectionStartedMOs = getDataCollectionStartedMonitors(resourceType);
/*       */     }
/*  1960 */     if (datacollectionStartedMOs.size() == 0)
/*       */     {
/*  1962 */       AMLog.debug("Reports : Data Collection not started for " + resourceType);
/*  1963 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodatacollection.monitors"));
/*  1964 */       saveMessages(request, messages);
/*  1965 */       request.setAttribute("heading", "0");
/*  1966 */       request.setAttribute("strTime", "0");
/*  1967 */       return mapping.findForward("report.message");
/*       */     }
/*       */     
/*  1970 */     String scheduleid = request.getParameter("scheduleid");
/*  1971 */     int cust = -1;
/*  1972 */     int site = -1;
/*       */     
/*  1974 */     if ((EnterpriseUtil.isIt360MSPEdition()) && (scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */     {
/*  1976 */       ArrayList al = EnterpriseUtil.getCustomerSiteFromScheduleDetails(scheduleid);
/*  1977 */       if ((al != null) && (al.size() >= 2))
/*       */       {
/*  1979 */         cust = Integer.parseInt(al.get(0).toString());
/*  1980 */         site = Integer.parseInt(al.get(1).toString());
/*       */       }
/*       */     }
/*       */     
/*  1984 */     String nodata = "report.nodata.time";
/*  1985 */     String RES_ID = null;
/*  1986 */     String ATT_ID = null;
/*  1987 */     String valueforpdf = null;
/*  1988 */     int array_size = 0;
/*  1989 */     List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/*  1990 */     String period = controls.getPeriod();
/*       */     
/*  1992 */     if ((period != null) && (period.equals("14")))
/*       */     {
/*  1994 */       if ((!"cpuid".equals(attribute)) && (!"mem".equals(attribute)) && (!"Memory Usage".equals(attname)) && (!"CPU Utilization".equals(attname))) {
/*  1995 */         period = "0";
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  2000 */     request.setAttribute("period", period);
/*       */     
/*       */     try
/*       */     {
/*  2004 */       Vector resourceids = null;
/*       */       
/*  2006 */       if ((period != null) && (period.equals("14")))
/*       */       {
/*  2008 */         ArrayList list1 = new ArrayList();
/*       */         
/*       */ 
/*  2011 */         Properties rawproperties = DBUtil.getRawValue();
/*  2012 */         String RawVal = rawproperties.getProperty("rawvalue");
/*  2013 */         long TI = Long.parseLong(RawVal) * 24L * 60L * 60L * 1000L;
/*  2014 */         long ET = System.currentTimeMillis();
/*  2015 */         long ST = ET - TI;
/*  2016 */         String q1 = null;
/*  2017 */         String namequery = null;
/*       */         
/*       */ 
/*  2020 */         if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  2021 */           if (Constants.isUserResourceEnabled()) {
/*  2022 */             resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */           } else {
/*  2024 */             String owner = request.getRemoteUser();
/*  2025 */             resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */           }
/*       */           
/*       */ 
/*  2029 */           if ((scheduleid != null) && (!scheduleid.trim().isEmpty()))
/*       */           {
/*  2031 */             resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */           }
/*  2033 */           if (((attribute != null) && (attribute.equals("cpuid"))) || ((attname != null) && (attname.equals("CPU Utilization"))))
/*       */           {
/*       */ 
/*       */ 
/*  2037 */             q1 = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport1", new String[] { ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "" + customFieldCondition, "" + ST, "" + ET });
/*  2038 */             namequery = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "" + customFieldCondition + " and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTEID in (1357,1377,1457,1473,708,1394,1107,1207,1307,1657,1957,1937,807,1977) ORDER BY AM_ManagedObject.RESOURCEID";
/*       */           }
/*  2040 */           if (((attribute != null) && (attribute.equals("mem"))) || ((attname != null) && (attname.equals("Memory Usage"))))
/*       */           {
/*       */ 
/*       */ 
/*  2044 */             q1 = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport2", new String[] { ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "" + customFieldCondition, "" + ST, "" + ET });
/*  2045 */             namequery = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "" + customFieldCondition + " and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTEID in (1352,1372,1452,1472,702,1392,1102,1202,803,1302,1652,1952,1932,1972) ORDER BY AM_ManagedObject.RESOURCEID";
/*       */           }
/*       */           
/*       */ 
/*       */         }
/*  2050 */         else if ((hid != null) && (hid.equals("true")))
/*       */         {
/*       */ 
/*  2053 */           ArrayList al = getHAAttributes(controls.getHaid());
/*       */           
/*       */ 
/*  2056 */           controls.setDurations(al);
/*  2057 */           resourceids = ReportUtilities.getChildIDs(Integer.parseInt(controls.getHaid()));
/*  2058 */           if (((attribute != null) && (attribute.equals("cpuid"))) || ((attname != null) && (attname.equals("CPU Utilization"))))
/*       */           {
/*  2060 */             q1 = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport1", new String[] { ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "" + customFieldCondition, "" + ST, "" + ET });
/*  2061 */             namequery = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "" + customFieldCondition + " and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTEID in (1357,1377,1457,1473,708,1394,1107,1207,1307,1657,1957,1937,807,1977) ORDER BY AM_ManagedObject.RESOURCEID";
/*       */           }
/*       */           
/*       */ 
/*       */ 
/*  2066 */           if (((attribute != null) && (attribute.equals("mem"))) || ((attname != null) && (attname.equals("Memory Usage"))))
/*       */           {
/*       */ 
/*       */ 
/*  2070 */             q1 = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport2", new String[] { ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "" + customFieldCondition, "" + ST, "" + ET });
/*  2071 */             namequery = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "" + customFieldCondition + " and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTEID in (1352,1372,1452,1472,702,1392,1102,1202,803,1302,1652,1952,1932,1972) ORDER BY AM_ManagedObject.RESOURCEID";
/*       */           }
/*       */         }
/*       */         else {
/*  2075 */           if (((attribute != null) && (attribute.equals("cpuid"))) || ((attname != null) && (attname.equals("CPU Utilization"))))
/*       */           {
/*       */ 
/*       */ 
/*  2079 */             q1 = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport3", new String[] { resourceType, customFieldCondition, "" + ST, "" + ET });
/*  2080 */             namequery = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject,AM_ATTRIBUTES,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ATTRIBUTES.RESOURCETYPE  and AM_ATTRIBUTES.ATTRIBUTEID in (1357,1377,1457,1473,708,1394,1107,1207,1307,1657,1957,1937,807,1977) " + customFieldCondition + " and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.Type  and AM_ManagedResourceType.SUBGROUP  in ('" + resourceType + "') ORDER BY AM_ManagedObject.RESOURCEID " + getLimitCondition(controls);
/*       */           }
/*       */           
/*  2083 */           if (((attribute != null) && (attribute.equals("mem"))) || ((attname != null) && (attname.equals("Memory Usage"))))
/*       */           {
/*       */ 
/*       */ 
/*  2087 */             q1 = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport4", new String[] { customFieldCondition, resourceType, "" + ST, "" + ET });
/*  2088 */             namequery = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject,AM_ATTRIBUTES,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ATTRIBUTES.RESOURCETYPE  and AM_ATTRIBUTES.ATTRIBUTEID in (1352,1372,1452,1472,702,1392,1102,1202,803,1302,1652,1952,1932,1972) " + customFieldCondition + " and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.Type  and AM_ManagedResourceType.SUBGROUP  in ('" + resourceType + "') ORDER BY AM_ManagedObject.RESOURCEID " + getLimitCondition(controls);
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*  2093 */         AMLog.debug("Reports :RAW DATA Attribute Query " + q1);
/*       */         
/*  2095 */         set = AMConnectionPool.executeQueryStmt(q1);
/*  2096 */         ArrayList rawdata = new ArrayList();
/*  2097 */         Vector v = new Vector();
/*       */         
/*  2099 */         ResultSet rs_urls = AMConnectionPool.executeQueryStmt(namequery);
/*       */         
/*       */ 
/*  2102 */         while (rs_urls.next()) {
/*  2103 */           ArrayList l1 = new ArrayList();
/*  2104 */           String rid = rs_urls.getString("RESOURCEID");
/*  2105 */           v.add(rs_urls.getString("RESOURCEID"));
/*  2106 */           String attid = rs_urls.getString("ATTRIBUTEID");
/*  2107 */           l1.add(rid + "_" + attid);
/*  2108 */           l1.add(EnterpriseUtil.decodeString(rs_urls.getString("DISPLAYNAME")));
/*  2109 */           list1.add(l1);
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*  2115 */         HashMap dataprops = null;
/*       */         String resid;
/*  2117 */         while (set.next())
/*       */         {
/*  2119 */           long archivedTime = set.getLong("COLLECTIONTIME");
/*       */           
/*  2121 */           float avgValue = set.getFloat("VALUE");
/*  2122 */           resid = set.getString("RESOURCEID");
/*  2123 */           String attid = set.getString("ATTRIBUTEID");
/*  2124 */           String key = resid + "_" + attid;
/*  2125 */           Properties prop = new Properties();
/*       */           
/*  2127 */           prop.setProperty("AVGVALUE", String.valueOf(avgValue));
/*       */           
/*       */ 
/*  2130 */           ArrayList a1 = ReportUtilities.checkArchivedtime(rawdata, archivedTime);
/*  2131 */           if (a1 == null) {
/*  2132 */             a1 = new ArrayList();
/*  2133 */             a1.add(new Long(archivedTime));
/*  2134 */             dataprops = new HashMap();
/*  2135 */             a1.add(dataprops);
/*  2136 */             rawdata.add(a1);
/*       */           }
/*  2138 */           dataprops.put(key, prop);
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*  2143 */         AMConnectionPool.closeStatement(rs_urls);
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2159 */         array_size = rawdata.size();
/*  2160 */         HashMap has = ReportUtilities.getResourceIdInOrder(q1, "rawvalue", archivedTableName);
/*       */         
/*  2162 */         Vector RESV = (Vector)has.get("RESOUREID");
/*  2163 */         Vector ATTV = (Vector)has.get("ATTRIBUTEID");
/*  2164 */         if (array_size == 0) {
/*  2165 */           AMLog.debug("Reports : No data for " + resourceType);
/*       */           
/*  2167 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(nodata));
/*  2168 */           saveMessages(request, messages);
/*  2169 */           request.setAttribute("heading", "0");
/*  2170 */           request.setAttribute("strTime", "0");
/*  2171 */           return mapping.findForward("report.message");
/*       */         }
/*       */         
/*  2174 */         String RID = ReportUtilities.getConvertedToString(RESV);
/*       */         
/*  2176 */         RES_ID = RID;
/*  2177 */         ATT_ID = attributeids;
/*  2178 */         request.setAttribute("strTime", new java.util.Date(ST));
/*  2179 */         request.setAttribute("endTime", new java.util.Date(ET));
/*  2180 */         request.setAttribute("STIME", ST + "");
/*  2181 */         request.setAttribute("ETIME", ET + "");
/*  2182 */         request.setAttribute("RESOURCEIDS", RID);
/*  2183 */         request.setAttribute("ATTRIBUTEIDS", attributeids);
/*  2184 */         request.setAttribute("methodName", "generateAttributeReport");
/*  2185 */         request.setAttribute("ATTNAME", attname);
/*  2186 */         request.setAttribute("rawdata", rawdata);
/*  2187 */         request.setAttribute("list", list1);
/*       */ 
/*       */ 
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*       */ 
/*  2196 */         ReportUtilities rep = new ReportUtilities();
/*  2197 */         valueforpdf = rep.getValueForPeriodForPDF(period);
/*       */         
/*       */ 
/*       */ 
/*  2201 */         long[] timeStamps = null;
/*  2202 */         if ((controls.getStartDate().equals("")) || (controls.getEndDate().equals(""))) {
/*  2203 */           timeStamps = ReportUtilities.getTimeStamp(period);
/*       */         }
/*       */         else {
/*  2206 */           controls.setPeriod("4");
/*       */           try {
/*  2208 */             timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */ 
/*       */           }
/*       */           catch (IllegalArgumentException iae)
/*       */           {
/*  2213 */             String errMsg = iae.getMessage();
/*  2214 */             AMLog.debug("Reports :  " + errMsg);
/*  2215 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  2216 */             saveMessages(request, messages);
/*  2217 */             request.setAttribute("heading", "0");
/*  2218 */             request.setAttribute("strTime", "0");
/*  2219 */             return mapping.findForward("report.message");
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*  2225 */         long[] dailyRptTimestamp = ReportUtilities.getDailyStartEndTime(timeStamps[0], timeStamps[1], archivedTableName);
/*       */         
/*       */ 
/*  2228 */         String dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1];
/*       */         
/*  2230 */         request.setAttribute("dailyStime", dailyRptTimestamp[2] + "");
/*  2231 */         request.setAttribute("dailyEtime", dailyRptTimestamp[3] + "");
/*       */         
/*       */ 
/*       */ 
/*  2235 */         if (dailyRptTimestamp[2] > 0L)
/*       */         {
/*  2237 */           dailyRptCondition = " and ( " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + dailyRptTimestamp[0] + " and ARCHIVEDTIME <=" + dailyRptTimestamp[1] + " OR " + archivedTableName + ".DURATION=2 and ARCHIVEDTIME >=" + dailyRptTimestamp[2] + " and ARCHIVEDTIME <=" + dailyRptTimestamp[3] + ") ";
/*       */         }
/*       */         
/*       */ 
/*  2241 */         request.setAttribute("timeStamps", timeStamps);
/*  2242 */         String query = "";
/*  2243 */         String groupbyStr = "";
/*  2244 */         ArrayList residsinorder = new ArrayList();
/*  2245 */         Vector nametreelist = new Vector();
/*  2246 */         boolean hasSubGroups = false;
/*  2247 */         boolean as400Disk = false;
/*       */         
/*  2249 */         if ((resourceType.equals("AS400/iSeries")) && ((attribute.contains("711")) || (attribute.contains("712")) || (attribute.contains("736")) || (attribute.contains("737")) || (attribute.contains("738")))) {
/*  2250 */           as400Disk = true;
/*       */         }
/*       */         String queryforresid;
/*  2253 */         if ((hid != null) && (hid.equals("true")))
/*       */         {
/*  2255 */           ArrayList al = getHAAttributes(controls.getHaid());
/*       */           
/*       */ 
/*  2258 */           controls.setDurations(al);
/*  2259 */           resourceids = ReportUtilities.getChildIDs(Integer.parseInt(controls.getHaid()));
/*       */           
/*  2261 */           resourceids.add(controls.getHaid());
/*       */           
/*  2263 */           hasSubGroups = addAllMonitorsinSubGroups(nametreelist, residsinorder, resourceids, controls.getHaid() + "", true);
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2269 */           query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport5", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("RESOURCEID", resourceids) });
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*  2274 */           groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  2275 */           query = query + dailyRptCondition + groupbyStr;
/*       */           
/*  2277 */           if (("buffer".equals(attribute)) || ("cache".equals(attribute)) || ("2411,3103,2619".equals(attribute)) || ("3138,2617".equals(attribute)))
/*       */           {
/*  2279 */             if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  2280 */               if (Constants.isUserResourceEnabled()) {
/*  2281 */                 resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */               } else {
/*  2283 */                 String owner = request.getRemoteUser();
/*  2284 */                 resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */               }
/*  2286 */               if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */               {
/*  2288 */                 resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */               }
/*       */             }
/*       */             
/*       */ 
/*  2293 */             String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE IN ('" + resourceType + "','DB2-DataBase')";
/*       */             
/*  2295 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2296 */             Vector v = new Vector();
/*  2297 */             while (set.next()) {
/*  2298 */               String id = set.getString("RESID");
/*  2299 */               v.add(id);
/*       */             }
/*  2301 */             AMConnectionPool.closeStatement(set);
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2309 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport6", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("RESOURCEID", v) });
/*  2310 */             groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  2311 */             query = query + dailyRptCondition + groupbyStr;
/*       */           }
/*       */           
/*  2314 */           if (((attribute != null) && ((attribute.equalsIgnoreCase("disk")) || (attribute.indexOf("711") != -1))) || ((attname != null) && (attname.equalsIgnoreCase("Disk Utilisation"))))
/*       */           {
/*  2316 */             if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  2317 */               if (Constants.isUserResourceEnabled()) {
/*  2318 */                 resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */               } else {
/*  2320 */                 String owner = request.getRemoteUser();
/*  2321 */                 resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */               }
/*       */               
/*  2324 */               if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */               {
/*  2326 */                 resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */               }
/*       */             }
/*       */             
/*       */ 
/*  2331 */             String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE in ('Disk','AS400_disk')";
/*  2332 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2333 */             Vector v = new Vector();
/*  2334 */             while (set.next()) {
/*  2335 */               String id = set.getString("RESID");
/*  2336 */               v.add(id);
/*       */             }
/*  2338 */             AMConnectionPool.closeStatement(set);
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2345 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport7", new String[] { archivedTableName, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v) });
/*  2346 */             groupbyStr = " group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc";
/*  2347 */             query = query + dailyRptCondition + groupbyStr;
/*       */ 
/*       */           }
/*  2350 */           else if (((attribute != null) && (attribute.equalsIgnoreCase("jdbc"))) || ((attname != null) && (attname.equalsIgnoreCase("Connection Pool Usage"))))
/*       */           {
/*  2352 */             if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  2353 */               if (Constants.isUserResourceEnabled()) {
/*  2354 */                 resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */               } else {
/*  2356 */                 String owner = request.getRemoteUser();
/*  2357 */                 resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */               }
/*  2359 */               if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */               {
/*  2361 */                 resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */               }
/*       */             }
/*       */             
/*  2365 */             String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='JDBC'";
/*       */             
/*  2367 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2368 */             Vector v1 = new Vector();
/*  2369 */             while (set.next()) {
/*  2370 */               String id = set.getString("RESID");
/*  2371 */               v1.add(id);
/*       */             }
/*  2373 */             AMConnectionPool.closeStatement(set);
/*       */             
/*       */ 
/*       */ 
/*  2377 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport8", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) });
/*  2378 */             groupbyStr = " group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2379 */             query = query + dailyRptCondition + groupbyStr;
/*       */ 
/*       */           }
/*  2382 */           else if (((attribute != null) && (attribute.equalsIgnoreCase("thread"))) || ((attname != null) && (attname.equalsIgnoreCase("Thread Details"))))
/*       */           {
/*  2384 */             if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  2385 */               if (Constants.isUserResourceEnabled()) {
/*  2386 */                 resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */               } else {
/*  2388 */                 String owner = request.getRemoteUser();
/*  2389 */                 resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */               }
/*  2391 */               if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */               {
/*  2393 */                 resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */               }
/*       */             }
/*  2396 */             String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='THREAD'";
/*       */             
/*  2398 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2399 */             Vector v1 = new Vector();
/*  2400 */             while (set.next()) {
/*  2401 */               String id = set.getString("RESID");
/*  2402 */               v1.add(id);
/*       */             }
/*  2404 */             AMConnectionPool.closeStatement(set);
/*       */             
/*  2406 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport9", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) });
/*       */             
/*  2408 */             groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2409 */             query = query + dailyRptCondition + groupbyStr;
/*       */           }
/*  2411 */           else if (((attribute != null) && (attribute.equalsIgnoreCase("session"))) || ((attname != null) && (attname.equalsIgnoreCase("Session Details"))))
/*       */           {
/*  2413 */             if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  2414 */               if (Constants.isUserResourceEnabled()) {
/*  2415 */                 resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */               } else {
/*  2417 */                 String owner = request.getRemoteUser();
/*  2418 */                 resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */               }
/*  2420 */               if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */               {
/*  2422 */                 resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */               }
/*       */             }
/*  2425 */             String disidsquery = " select DISTINCT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ATTRIBUTES.ATTRIBUTEID in (213,213,508,3424) and AM_ATTRIBUTES.RESOURCETYPE IN ('WEBAPP','OracleWEBAPP')";
/*       */             
/*       */ 
/*  2428 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2429 */             Vector v1 = new Vector();
/*  2430 */             while (set.next()) {
/*  2431 */               String id = set.getString("RESID");
/*  2432 */               v1.add(id);
/*       */             }
/*       */             
/*  2435 */             disidsquery = " select DISTINCT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("PARENTID", v1) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ATTRIBUTES.ATTRIBUTEID in (213,213,508,3424) and AM_ATTRIBUTES.RESOURCETYPE IN ('WEBAPP','OracleWEBAPP')";
/*  2436 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2437 */             while (set.next()) {
/*  2438 */               String id = set.getString("RESID");
/*  2439 */               v1.add(id);
/*       */             }
/*       */             
/*       */ 
/*  2443 */             AMConnectionPool.closeStatement(set);
/*       */             
/*  2445 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport10", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) });
/*       */             
/*  2447 */             groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2448 */             query = query + dailyRptCondition + groupbyStr;
/*       */ 
/*       */ 
/*       */           }
/*  2452 */           else if (((attribute != null) && (attribute.equalsIgnoreCase("operationExecutionTime"))) || ((attname != null) && (attname.equalsIgnoreCase("Operation Execution Time"))))
/*       */           {
/*  2454 */             if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  2455 */               if (Constants.isUserResourceEnabled()) {
/*  2456 */                 resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */               } else {
/*  2458 */                 String owner = request.getRemoteUser();
/*  2459 */                 resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */               }
/*  2461 */               if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */               {
/*  2463 */                 resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */               }
/*       */             }
/*  2466 */             String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='Web_Service_Operation'";
/*       */             
/*  2468 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2469 */             Vector v1 = new Vector();
/*  2470 */             while (set.next()) {
/*  2471 */               String id = set.getString("RESID");
/*  2472 */               v1.add(id);
/*       */             }
/*  2474 */             AMConnectionPool.closeStatement(set);
/*       */             
/*       */ 
/*       */ 
/*  2478 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport11", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) });
/*       */             
/*  2480 */             groupbyStr = " group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2481 */             query = query + dailyRptCondition + groupbyStr;
/*       */           }
/*  2483 */           else if (((attribute != null) && ((attribute.equalsIgnoreCase("webappthroughput")) || (attribute.equalsIgnoreCase("3421")))) || ((attname != null) && (attname.equalsIgnoreCase("Web Application Request Throughput"))))
/*       */           {
/*  2485 */             if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  2486 */               if (Constants.isUserResourceEnabled()) {
/*  2487 */                 resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */               } else {
/*  2489 */                 String owner = request.getRemoteUser();
/*  2490 */                 resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */               }
/*  2492 */               if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */               {
/*  2494 */                 resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */               }
/*       */             }
/*  2497 */             String disidsquery = " select DISTINCT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ATTRIBUTES.ATTRIBUTEID in (3421) and AM_ATTRIBUTES.RESOURCETYPE IN ('OracleWEBAPP')";
/*       */             
/*       */ 
/*  2500 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2501 */             Vector v1 = new Vector();
/*  2502 */             while (set.next()) {
/*  2503 */               String id = set.getString("RESID");
/*  2504 */               v1.add(id);
/*       */             }
/*       */             
/*  2507 */             disidsquery = " select DISTINCT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("PARENTID", v1) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ATTRIBUTES.ATTRIBUTEID in (3421) and AM_ATTRIBUTES.RESOURCETYPE IN ('OracleWEBAPP')";
/*  2508 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2509 */             while (set.next()) {
/*  2510 */               String id = set.getString("RESID");
/*  2511 */               v1.add(id);
/*       */             }
/*       */             
/*       */ 
/*  2515 */             AMConnectionPool.closeStatement(set);
/*       */             
/*  2517 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport12", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) });
/*       */             
/*       */ 
/*  2520 */             groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2521 */             query = query + dailyRptCondition + groupbyStr;
/*       */           }
/*  2523 */           else if ((attribute != null) && (allSecondLevelAttribute.contains(attribute)))
/*       */           {
/*  2525 */             String queryforresid = "SELECT RESOURCEID FROM AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "') and " + ReportUtilities.getCondition("RESOURCEID", resourceids);
/*       */             
/*  2527 */             if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  2528 */               if (Constants.isUserResourceEnabled()) {
/*  2529 */                 resourceids = Constants.getUserResourceID(Constants.getLoginUserid(request));
/*       */               } else {
/*  2531 */                 String owner = request.getRemoteUser();
/*  2532 */                 resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*       */               }
/*  2534 */               if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */               {
/*  2536 */                 resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */               }
/*  2538 */               queryforresid = queryforresid + " and " + ReportUtilities.getCondition("RESOURCEID", resourceids);
/*       */             }
/*  2540 */             set = AMConnectionPool.executeQueryStmt(queryforresid);
/*  2541 */             Vector v = new Vector();
/*  2542 */             while (set.next()) {
/*  2543 */               String id = set.getString("RESOURCEID");
/*  2544 */               v.add(id);
/*       */             }
/*       */             
/*  2547 */             String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='" + ReportUtilities.getResourceTypeForAttribute(attribute) + "'";
/*  2548 */             if (as400Disk) {
/*  2549 */               disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='AS400_disk'";
/*       */             }
/*  2551 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2552 */             v = new Vector();
/*  2553 */             while (set.next()) {
/*  2554 */               String id = set.getString("RESID");
/*  2555 */               v.add(id);
/*       */             }
/*  2557 */             AMConnectionPool.closeStatement(set);
/*       */             
/*  2559 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport13", new String[] { archivedTableName, attribute, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v) });
/*       */             
/*       */ 
/*  2562 */             if (!as400Disk) {
/*  2563 */               query = query + " and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE ";
/*       */             } else {
/*  2565 */               query = query + " and AM_ManagedObject.TYPE='AS400_disk' and AM_ATTRIBUTES.RESOURCETYPE='Disk' ";
/*       */             }
/*  2567 */             groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  2568 */             query = query + dailyRptCondition + groupbyStr;
/*       */ 
/*       */ 
/*       */ 
/*       */           }
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*  2584 */           String condition = "";
/*  2585 */           if (((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) && (!"true".equals(hid))) {
/*  2586 */             if (Constants.isUserResourceEnabled()) {
/*  2587 */               String loginUserid = Constants.getLoginUserid(request);
/*  2588 */               condition = " RESOURCEID IN (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")";
/*       */             } else {
/*  2590 */               String owner = request.getRemoteUser();
/*  2591 */               resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*  2592 */               resourceids.addAll(DBUtil.getChildMonitors(resourceids));
/*  2593 */               condition = ReportUtilities.getCondition("RESOURCEID", resourceids);
/*       */             }
/*  2595 */             if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */             {
/*  2597 */               resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */             }
/*       */             
/*  2600 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport14", new String[] { archivedTableName, attributeids, condition + customFieldCondition });
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2607 */             groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  2608 */             query = query + dailyRptCondition + groupbyStr;
/*  2609 */             query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */             
/*  2611 */             if (((attribute != null) && ((attribute.equalsIgnoreCase("disk")) || (attribute.indexOf("711") != -1))) || ((attname != null) && (attname.equalsIgnoreCase("Disk Utilisation"))))
/*       */             {
/*       */ 
/*  2614 */               String disidsquery = "select childmo.RESOURCEID AS RESID from AM_ManagedObject childmo join AM_PARENTCHILDMAPPER pc on childmo.RESOURCEID=pc.CHILDID  join AM_ManagedObject parentmo on parentmo.RESOURCEID=pc.PARENTID join AM_ManagedResourceType mrt on mrt.RESOURCETYPE=parentmo.TYPE where childmo.TYPE in ('Disk','AS400_disk') and mrt.SUBGROUP in ('" + resourceType + "') and " + ReportUtilities.getCondition("PARENTID", resourceids) + " ";
/*  2615 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2616 */               Vector v = new Vector();
/*  2617 */               while (set.next()) {
/*  2618 */                 String id = set.getString("RESID");
/*  2619 */                 v.add(id);
/*       */               }
/*  2621 */               AMConnectionPool.closeStatement(set);
/*       */               
/*       */ 
/*  2624 */               query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport15", new String[] { archivedTableName, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v) + customFieldCondition });
/*       */               
/*       */ 
/*  2627 */               groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  2628 */               query = query + dailyRptCondition + groupbyStr;
/*  2629 */               query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */ 
/*       */             }
/*  2633 */             else if (((attribute != null) && (attribute.equalsIgnoreCase("jdbc"))) || ((attname != null) && (attname.equalsIgnoreCase("Connection Pool Usage")))) {
/*  2634 */               String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID " + customFieldCondition + " AND AM_ManagedObject.TYPE ='JDBC'";
/*       */               
/*  2636 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2637 */               Vector v1 = new Vector();
/*  2638 */               while (set.next()) {
/*  2639 */                 String id = set.getString("RESID");
/*  2640 */                 v1.add(id);
/*       */               }
/*  2642 */               AMConnectionPool.closeStatement(set);
/*       */               
/*  2644 */               query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport16", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) + customFieldCondition });
/*       */               
/*       */ 
/*  2647 */               groupbyStr = " group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2648 */               query = query + dailyRptCondition + groupbyStr;
/*  2649 */               query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */             }
/*  2652 */             else if (((attribute != null) && (attribute.equalsIgnoreCase("thread"))) || ((attname != null) && (attname.equalsIgnoreCase("Thread Details")))) {
/*  2653 */               String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", resourceids) + "" + customFieldCondition + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='THREAD'";
/*       */               
/*  2655 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2656 */               Vector v1 = new Vector();
/*  2657 */               while (set.next()) {
/*  2658 */                 String id = set.getString("RESID");
/*  2659 */                 v1.add(id);
/*       */               }
/*  2661 */               AMConnectionPool.closeStatement(set);
/*       */               
/*  2663 */               query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport17", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) + customFieldCondition });
/*       */               
/*       */ 
/*  2666 */               groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2667 */               query = query + dailyRptCondition + groupbyStr;
/*  2668 */               query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */             }
/*  2671 */             else if (((attribute != null) && (attribute.equalsIgnoreCase("session"))) || ((attname != null) && (attname.equalsIgnoreCase("Session Details")))) {
/*  2672 */               String disidsquery = " select DISTINCT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID " + customFieldCondition + " and AM_ATTRIBUTES.ATTRIBUTEID in (213,213,508,3424) and AM_ATTRIBUTES.RESOURCETYPE IN ('WEBAPP','OracleWEBAPP')";
/*       */               
/*       */ 
/*  2675 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2676 */               Vector v1 = new Vector();
/*  2677 */               while (set.next()) {
/*  2678 */                 String id = set.getString("RESID");
/*  2679 */                 v1.add(id);
/*       */               }
/*       */               
/*  2682 */               disidsquery = " select DISTINCT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("PARENTID", v1) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID " + customFieldCondition + " and AM_ATTRIBUTES.ATTRIBUTEID in (213,213,508,3424) and AM_ATTRIBUTES.RESOURCETYPE IN ('WEBAPP','OracleWEBAPP')";
/*  2683 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2684 */               while (set.next()) {
/*  2685 */                 String id = set.getString("RESID");
/*  2686 */                 v1.add(id);
/*       */               }
/*       */               
/*       */ 
/*  2690 */               AMConnectionPool.closeStatement(set);
/*       */               
/*  2692 */               query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport18", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) + customFieldCondition });
/*       */               
/*       */ 
/*  2695 */               groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2696 */               query = query + dailyRptCondition + groupbyStr;
/*  2697 */               query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */             }
/*  2700 */             else if (((attribute != null) && ((attribute.equalsIgnoreCase("webappthroughput")) || (attribute.equalsIgnoreCase("3421")))) || ((attname != null) && (attname.equalsIgnoreCase("Web Application Request Throughput")))) {
/*  2701 */               String disidsquery = " select DISTINCT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID " + customFieldCondition + " and AM_ATTRIBUTES.ATTRIBUTEID in (3421) and AM_ATTRIBUTES.RESOURCETYPE IN ('OracleWEBAPP')";
/*       */               
/*       */ 
/*  2704 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2705 */               Vector v1 = new Vector();
/*  2706 */               while (set.next()) {
/*  2707 */                 String id = set.getString("RESID");
/*  2708 */                 v1.add(id);
/*       */               }
/*       */               
/*  2711 */               disidsquery = " select DISTINCT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("PARENTID", v1) + "" + customFieldCondition + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ATTRIBUTES.ATTRIBUTEID in (3421) and AM_ATTRIBUTES.RESOURCETYPE IN ('OracleWEBAPP')";
/*  2712 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2713 */               while (set.next()) {
/*  2714 */                 String id = set.getString("RESID");
/*  2715 */                 v1.add(id);
/*       */               }
/*       */               
/*       */ 
/*  2719 */               AMConnectionPool.closeStatement(set);
/*       */               
/*  2721 */               query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport19", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) + customFieldCondition });
/*       */               
/*       */ 
/*  2724 */               groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2725 */               query = query + dailyRptCondition + groupbyStr;
/*  2726 */               query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */             }
/*  2729 */             else if (((attribute != null) && (attribute.equalsIgnoreCase("operationExecutionTime"))) || ((attname != null) && (attname.equalsIgnoreCase("Operation Execution Time")))) {
/*  2730 */               String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", resourceids) + "" + customFieldCondition + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='Web_Service_Operation'";
/*       */               
/*  2732 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2733 */               Vector v1 = new Vector();
/*  2734 */               while (set.next()) {
/*  2735 */                 String id = set.getString("RESID");
/*  2736 */                 v1.add(id);
/*       */               }
/*  2738 */               AMConnectionPool.closeStatement(set);
/*       */               
/*  2740 */               query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport20", new String[] { archivedTableName, attributeids, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v1) + customFieldCondition });
/*       */               
/*       */ 
/*  2743 */               groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2744 */               query = query + dailyRptCondition + groupbyStr;
/*  2745 */               query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */             }
/*  2748 */             else if ((attribute != null) && (allSecondLevelAttribute.contains(attribute)))
/*       */             {
/*  2750 */               String queryforresid = "SELECT RESOURCEID FROM AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "') and " + ReportUtilities.getCondition("RESOURCEID", resourceids) + customFieldCondition;
/*       */               
/*  2752 */               set = AMConnectionPool.executeQueryStmt(queryforresid);
/*  2753 */               Vector v = new Vector();
/*  2754 */               while (set.next()) {
/*  2755 */                 String id = set.getString("RESOURCEID");
/*  2756 */                 if (resourceids.contains(id))
/*       */                 {
/*  2758 */                   v.add(id);
/*       */                 }
/*       */               }
/*       */               
/*  2762 */               String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + "" + customFieldCondition + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='" + ReportUtilities.getResourceTypeForAttribute(attribute) + "'";
/*  2763 */               if (as400Disk) {
/*  2764 */                 disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='AS400_disk'";
/*       */               }
/*       */               
/*  2767 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2768 */               v = new Vector();
/*  2769 */               while (set.next()) {
/*  2770 */                 String id = set.getString("RESID");
/*       */                 
/*  2772 */                 v.add(id);
/*       */               }
/*       */               
/*  2775 */               AMConnectionPool.closeStatement(set);
/*       */               
/*  2777 */               query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport21", new String[] { archivedTableName, attribute, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v) + customFieldCondition });
/*  2778 */               if (!as400Disk) {
/*  2779 */                 query = query + " and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE ";
/*       */               } else {
/*  2781 */                 query = query + " and AM_ManagedObject.TYPE='AS400_disk' and AM_ATTRIBUTES.RESOURCETYPE='Disk' ";
/*       */               }
/*       */               
/*       */ 
/*  2785 */               groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  2786 */               query = query + dailyRptCondition + groupbyStr;
/*  2787 */               query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */             }
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           }
/*  2801 */           else if (((attribute != null) && ((attribute.equalsIgnoreCase("disk")) || (attribute.indexOf("711") != -1))) || ((attname != null) && (attname.equalsIgnoreCase("Disk Utilisation"))))
/*       */           {
/*       */ 
/*       */ 
/*  2805 */             if ((resourceType != null) && (resourceType.equalsIgnoreCase("AIX','AS400/iSeries','FreeBSD','HP-UX / Tru64','Linux','Mac OS','Sun Solaris','Windows")))
/*       */             {
/*  2807 */               query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport22", new String[] { archivedTableName, attributeids, customFieldCondition });
/*       */               
/*       */ 
/*       */ 
/*  2811 */               groupbyStr = " group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  2812 */               query = query + dailyRptCondition + groupbyStr;
/*  2813 */               query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */             }
/*       */             else
/*       */             {
/*       */ 
/*  2819 */               String queryforresid = "SELECT RESOURCEID FROM AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "')" + customFieldCondition;
/*       */               
/*  2821 */               set = AMConnectionPool.executeQueryStmt(queryforresid);
/*  2822 */               Vector v = new Vector();
/*  2823 */               while (set.next()) {
/*  2824 */                 String id = set.getString("RESOURCEID");
/*  2825 */                 v.add(id);
/*       */               }
/*       */               
/*  2828 */               String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE in ('Disk','AS400_disk')";
/*  2829 */               set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2830 */               v = new Vector();
/*  2831 */               while (set.next()) {
/*  2832 */                 String id = set.getString("RESID");
/*  2833 */                 v.add(id);
/*       */               }
/*  2835 */               AMConnectionPool.closeStatement(set);
/*       */               
/*  2837 */               query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport23", new String[] { archivedTableName, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v) });
/*       */               
/*       */ 
/*  2840 */               groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  2841 */               query = query + dailyRptCondition + groupbyStr;
/*  2842 */               query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */             }
/*       */             
/*       */ 
/*       */           }
/*  2848 */           else if (((attribute != null) && ((attribute.equalsIgnoreCase("jdbc")) || (attribute.equalsIgnoreCase("thread")))) || ((attname != null) && ((attname.equalsIgnoreCase("Connection Pool Usage")) || (attname.equalsIgnoreCase("Thread Details"))))) {
/*  2849 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport24", new String[] { archivedTableName, attributeids, customFieldCondition });
/*       */             
/*       */ 
/*  2852 */             groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2853 */             query = query + dailyRptCondition + groupbyStr;
/*  2854 */             query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */           }
/*  2856 */           else if (((attribute != null) && (attribute.equalsIgnoreCase("operationExecutionTime"))) || ((attname != null) && (attname.equalsIgnoreCase("Operation Execution Time"))))
/*       */           {
/*       */ 
/*       */ 
/*  2860 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport25", new String[] { archivedTableName, customFieldCondition });
/*  2861 */             groupbyStr = " group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  2862 */             query = query + dailyRptCondition + groupbyStr;
/*  2863 */             query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*  2864 */           } else if ((attribute != null) && (allSecondLevelAttribute.contains(attribute)))
/*       */           {
/*  2866 */             queryforresid = "SELECT RESOURCEID FROM AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "')" + customFieldCondition;
/*       */             
/*  2868 */             set = AMConnectionPool.executeQueryStmt(queryforresid);
/*  2869 */             Vector v = new Vector();
/*  2870 */             while (set.next()) {
/*  2871 */               String id = set.getString("RESOURCEID");
/*  2872 */               v.add(id);
/*       */             }
/*  2874 */             if ("DB2-tablespace".equals(ReportUtilities.getResourceTypeForAttribute(attribute))) {
/*  2875 */               String secondLevelquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='DB2-DataBase'";
/*  2876 */               ArrayList seconflevelMos = DBUtil.getRowsForSingleColumn(secondLevelquery);
/*  2877 */               v = new Vector();
/*  2878 */               v.addAll(seconflevelMos);
/*       */             }
/*  2880 */             String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='" + ReportUtilities.getResourceTypeForAttribute(attribute) + "'";
/*  2881 */             if (as400Disk) {
/*  2882 */               disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='AS400_disk'";
/*       */             }
/*       */             
/*  2885 */             set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  2886 */             v = new Vector();
/*  2887 */             while (set.next()) {
/*  2888 */               String id = set.getString("RESID");
/*  2889 */               v.add(id);
/*       */             }
/*  2891 */             AMConnectionPool.closeStatement(set);
/*       */             
/*  2893 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport26", new String[] { expression, archivedTableName, attribute, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v) });
/*  2894 */             if (!as400Disk) {
/*  2895 */               query = query + " and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE ";
/*       */             } else {
/*  2897 */               query = query + " and AM_ManagedObject.TYPE='AS400_disk' and AM_ATTRIBUTES.RESOURCETYPE='Disk' ";
/*       */             }
/*       */             
/*       */ 
/*  2901 */             groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  2902 */             query = query + dailyRptCondition + groupbyStr;
/*  2903 */             query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */           }
/*       */           else
/*       */           {
/*       */ 
/*  2909 */             if ((attributeids != null) && (attributeids.equalsIgnoreCase("")))
/*       */             {
/*  2911 */               attributeids = null;
/*       */             }
/*  2913 */             query = DBQueryUtil.getDBQuery("am.amreportAction.generateAttbributeReport27", new String[] { archivedTableName, attributeids, customFieldCondition });
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2920 */             groupbyStr = "  group by AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME," + archivedTableName + ".ATTRIBUTEID order by Average desc ";
/*  2921 */             query = query + dailyRptCondition + groupbyStr;
/*  2922 */             query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2935 */         request.setAttribute("STIME", timeStamps[0] + "");
/*  2936 */         request.setAttribute("ETIME", timeStamps[1] + "");
/*  2937 */         AMLog.debug("Reports :Attribute Query ---------> " + query);
/*  2938 */         ArrayList data = ReportUtilities.getTabularData(query, false);
/*  2939 */         if (data.size() == 0)
/*       */         {
/*  2941 */           AMLog.debug("Reports : No data for " + resourceType);
/*       */           
/*  2943 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(nodata));
/*  2944 */           saveMessages(request, messages);
/*  2945 */           if ((controls.getReporttype().equals("pdf")) || (controls.getReporttype().equals("html")))
/*       */           {
/*  2947 */             request.setAttribute("heading", "0");
/*  2948 */             request.setAttribute("strTime", "0");
/*  2949 */             return mapping.findForward("report.message");
/*       */           }
/*       */         }
/*  2952 */         HashMap p1 = ReportUtilities.getResourceIdInOrder(query, null, archivedTableName);
/*       */         
/*  2954 */         Vector RESV = (Vector)p1.get("RESOUREID");
/*  2955 */         Vector ATTV = (Vector)p1.get("ATTRIBUTEID");
/*  2956 */         String csvResIds = ReportUtilities.getConvertedToString(RESV);
/*  2957 */         String csvAttIds = ReportUtilities.getConvertedToString(ATTV);
/*  2958 */         String bhperiod = controls.getBusinessPeriod();
/*  2959 */         if ((bhperiod != null) && (!"oni".equals(bhperiod))) {
/*  2960 */           SummaryBean sumgraph = new SummaryBean();
/*  2961 */           HashMap residmap = ReportUtilities.getResourceIdInOrder(query, null, archivedTableName, 1000000);
/*  2962 */           Vector RESVALL = (Vector)residmap.get("RESOUREID");
/*  2963 */           Vector ATTVALL = (Vector)residmap.get("ATTRIBUTEID");
/*  2964 */           String csvResIdsAll = ReportUtilities.getConvertedToString(RESVALL);
/*  2965 */           String csvAttIdsAll = ReportUtilities.getConvertedToString(ATTVALL);
/*  2966 */           sumgraph.setResid(csvResIdsAll);
/*  2967 */           sumgraph.setAttributeid(csvAttIdsAll);
/*  2968 */           sumgraph.setStarttime((String)request.getAttribute("STIME"));
/*  2969 */           sumgraph.setDailyRptStarttime((String)request.getAttribute("dailyStime"));
/*  2970 */           sumgraph.setDailyRptEndtime((String)request.getAttribute("dailyEtime"));
/*  2971 */           sumgraph.setEndtime((String)request.getAttribute("ETIME"));
/*  2972 */           Hashtable bhrDetails = SegmentReportUtil.getBusinessRule(businessPeriod);
/*  2973 */           sumgraph.setBhrDetails(bhrDetails);
/*       */           
/*  2975 */           sumgraph.setArchivedforUrl(true);
/*  2976 */           sumgraph.setCompareUrls(true);
/*  2977 */           sumgraph.setPeriod(period);
/*  2978 */           sumgraph.setCategory(attname);
/*  2979 */           LinkedHashMap<String, Object> bhdata = sumgraph.getBHfilterData(true);
/*       */           
/*  2981 */           if ((bhdata != null) && (!bhdata.isEmpty())) {
/*  2982 */             HashMap<String, ArrayList<Object>> bhminmaxAlldata = (HashMap)bhdata.remove("MINMAXAVGDATA");
/*  2983 */             request.setAttribute("timeseriesBHData", bhdata);
/*  2984 */             ArrayList<ArrayList<Object>> bhminmaxdata = new ArrayList();
/*  2985 */             if (bhminmaxAlldata != null) {
/*  2986 */               for (ArrayList<Object> minmaxlocal : bhminmaxAlldata.values())
/*       */               {
/*  2988 */                 long total = ((Long)minmaxlocal.get(3)).longValue();
/*  2989 */                 long count = ((Long)minmaxlocal.get(8)).longValue();
/*  2990 */                 DecimalFormat twoD = new DecimalFormat("#.##");
/*  2991 */                 double avg = Double.valueOf(twoD.format(total / count)).doubleValue();
/*  2992 */                 minmaxlocal.set(3, Double.valueOf(avg));
/*  2993 */                 bhminmaxdata.add(minmaxlocal);
/*       */               }
/*  2995 */               if ((bhminmaxdata != null) && (!bhminmaxdata.isEmpty())) {
/*  2996 */                 request.setAttribute("data", bhminmaxdata);
/*       */               }
/*       */               
/*       */             }
/*       */           }
/*       */           else
/*       */           {
/*  3003 */             AMLog.debug("Business Hour Data Not Available for Resource - Attributes " + residmap);
/*       */           }
/*       */         }
/*       */         
/*       */         try
/*       */         {
/*  3009 */           ArrayList tempdata = null;
/*  3010 */           if (hasSubGroups)
/*       */           {
/*  3012 */             tempdata = addMGTreetoMonitorName(data, nametreelist, residsinorder, "attribute");
/*  3013 */             if ((tempdata != null) && (tempdata.size() > 0))
/*       */             {
/*  3015 */               data = tempdata;
/*       */             }
/*       */           }
/*       */         }
/*       */         catch (Exception exc)
/*       */         {
/*  3021 */           exc.printStackTrace();
/*       */         }
/*       */         
/*       */ 
/*  3025 */         array_size = data.size();
/*       */         
/*  3027 */         Hashtable ht = new Hashtable();
/*       */         
/*  3029 */         for (int k = 0; k < data.size(); k++) {
/*  3030 */           ArrayList dataend = (ArrayList)data.get(k);
/*       */           
/*  3032 */           int attributeid = Integer.parseInt(dataend.get(7).toString());
/*       */           
/*       */ 
/*       */ 
/*  3036 */           int resoid = Integer.parseInt(dataend.get(4).toString());
/*  3037 */           String dname = (String)dataend.get(0);
/*  3038 */           if ((attributeid == 319) || (attributeid == 219) || (attributeid == 519) || (attributeid == 35) || (attributeid == 525) || (attributeid == 235) || (attributeid == 213) || (attributeid == 508) || (attributeid == 2619) || (attributeid == 2617))
/*       */           {
/*  3040 */             ht = ReportUtilities.getDisplayName(resoid);
/*       */             
/*  3042 */             dname = (String)ht.get(String.valueOf(resoid));
/*       */ 
/*       */ 
/*       */           }
/*  3046 */           else if (attributeid == 711) {
/*  3047 */             HashMap alldisplayname = DBUtil.getDisplayNameForDisk();
/*  3048 */             String name = (String)dataend.get(0);
/*  3049 */             dname = FormatUtil.findReplace(name, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/*  3050 */             String[] temp1 = dname.split(":", 2);
/*  3051 */             if ((temp1[0] != null) && (alldisplayname.get(temp1[0]) != null))
/*       */             {
/*  3053 */               String s1 = alldisplayname.get(temp1[0]).toString();
/*  3054 */               if ((s1 != null) && (temp1.length > 1))
/*       */               {
/*  3056 */                 dname = s1 + ":" + temp1[1];
/*       */               }
/*       */             }
/*       */             else
/*       */             {
/*  3061 */               System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& DISK UTILIZATION: NULL in DBUtil.getDisplayNameForDisk() &&&&&&&&&&&&");
/*  3062 */               System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& DBUtil.getDisplayNameForDisk() -------->" + alldisplayname);
/*  3063 */               System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& NULL for Key : temp1[0] -------->" + temp1[0]);
/*       */ 
/*       */             }
/*       */             
/*       */ 
/*       */           }
/*  3069 */           else if ((attributeid == 2625) || (attributeid == 2626)) {
/*  3070 */             String displayname = ReportUtil.getDisplayNameForThridLevelAttribute(resoid + "");
/*  3071 */             String name = (String)dataend.get(0);
/*  3072 */             dname = displayname + "_" + name;
/*       */           }
/*  3074 */           else if (allSecondLevelAttribute.contains(attributeid + ""))
/*       */           {
/*  3076 */             String displayname = ReportUtil.getDisplayNameForAttribute(resoid);
/*       */             
/*  3078 */             String name = (String)dataend.get(0);
/*       */             
/*  3080 */             dname = displayname + "_" + name;
/*       */           }
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3087 */           dataend.add(dname);
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3094 */         RES_ID = csvResIds;
/*  3095 */         ATT_ID = csvAttIds;
/*  3096 */         AMLog.debug(RES_ID + "<------>RES_ID ^^^^^^^^^^^^^^^^^^^^^^$$$$$$$$$$$$$$$$$$$$$$$$$$$$$^^^^^^^^^^^^^^^^^^^^^^^^^ ATT_ID<---->" + ATT_ID);
/*  3097 */         request.setAttribute("strTime", new java.util.Date(timeStamps[0]));
/*  3098 */         request.setAttribute("endTime", new java.util.Date(timeStamps[1]));
/*  3099 */         request.setAttribute("data", data);
/*  3100 */         request.setAttribute("RESOURCEIDS", csvResIds);
/*  3101 */         request.setAttribute("ATTRIBUTEIDS", csvAttIds);
/*  3102 */         request.setAttribute("methodName", "generateAttributeReport");
/*  3103 */         request.setAttribute("AttDispalyName", request.getAttribute("AttDispalyName"));
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  3109 */       Units = controls.getUnit();
/*       */       
/*  3111 */       if ((Units != null) && (!Units.equals(""))) {
/*  3112 */         request.setAttribute("Units", Units);
/*  3113 */         Units = "(" + Units + ")";
/*       */ 
/*       */ 
/*       */       }
/*  3117 */       else if (request.getAttribute("Units") != null) {
/*  3118 */         Units = "(" + (String)request.getAttribute("Units") + ")";
/*       */       }
/*       */       else {
/*  3121 */         Units = "";
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  3126 */       String yaxis = "";
/*  3127 */       String token; if (request.getAttribute("attributeDispalyName") != null)
/*       */       {
/*  3129 */         yaxis = (String)request.getAttribute("attributeDispalyName") + " " + Units;
/*       */       }
/*       */       else
/*       */       {
/*  3133 */         String qry = "select DISPLAYNAME from AM_ATTRIBUTES WHERE ATTRIBUTEID in(" + attributeids + ")";
/*       */         
/*  3135 */         AMConnectionPool cp1 = AMConnectionPool.getInstance();
/*       */         try
/*       */         {
/*  3138 */           ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  3139 */           if (rs.next())
/*       */           {
/*  3141 */             yaxis = rs.getString(1);
/*  3142 */             request.setAttribute("attributeDispalyName", yaxis);
/*  3143 */             StringTokenizer str = new StringTokenizer((String)request.getAttribute("heading"), "-");
/*  3144 */             token = "Today";
/*       */             try
/*       */             {
/*  3147 */               while (str.hasMoreTokens())
/*       */               {
/*  3149 */                 token = str.nextToken();
/*       */               }
/*       */             }
/*       */             catch (Exception exc) {}
/*       */             
/*       */ 
/*       */ 
/*  3156 */             request.setAttribute("heading", FormatUtil.getString(yaxis) + " - " + token);
/*  3157 */             request.setAttribute("graphheading", FormatUtil.getString(yaxis) + " - " + token);
/*       */           }
/*  3159 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*       */         catch (Exception exc)
/*       */         {
/*  3163 */           exc.printStackTrace();
/*       */         }
/*       */       }
/*  3166 */       String[] t = yaxis.split(" ");
/*  3167 */       request.setAttribute("AttDispalyName", t[0]);
/*  3168 */       request.setAttribute("unitstoshow", Units);
/*  3169 */       ChartInfo cinfo; if (controls.getReporttype().equals("pdf")) {
/*  3170 */         cinfo = new ChartInfo();
/*  3171 */         SummaryBean sumgraph = new SummaryBean();
/*  3172 */         sumgraph.setResid(RES_ID);
/*  3173 */         sumgraph.setAttributeid(ATT_ID);
/*  3174 */         sumgraph.setStarttime((String)request.getAttribute("STIME"));
/*  3175 */         sumgraph.setDailyRptStarttime((String)request.getAttribute("dailyStime"));
/*  3176 */         sumgraph.setDailyRptEndtime((String)request.getAttribute("dailyEtime"));
/*  3177 */         sumgraph.setEndtime((String)request.getAttribute("ETIME"));
/*  3178 */         sumgraph.setArchivedforUrl(true);
/*  3179 */         sumgraph.setCompareUrls(true);
/*  3180 */         sumgraph.setPeriod(period);
/*  3181 */         sumgraph.setCategory(attname);
/*  3182 */         sumgraph.setTimeSeriesData((LinkedHashMap)request.getAttribute("timeseriesBHData"));
/*       */         
/*  3184 */         if ("true".equals(Constants.attributesReportGraphType))
/*       */         {
/*  3186 */           String unitToPDF = controls.getUnit();
/*  3187 */           if ((unitToPDF != null) && (!unitToPDF.equals("")))
/*       */           {
/*  3189 */             sumgraph.setUnit(unitToPDF);
/*       */           }
/*       */           else
/*       */           {
/*  3193 */             sumgraph.setUnit("");
/*       */           }
/*  3195 */           sumgraph.setBarData((ArrayList)request.getAttribute("data"));
/*  3196 */           cinfo.setXaxisLabel(FormatUtil.getString("Monitors"));
/*  3197 */           cinfo.setMaxBarWidth(0.03D);
/*  3198 */           cinfo.setLegend("false");
/*  3199 */           cinfo.setHeight("300");
/*  3200 */           cinfo.setWidth("700");
/*       */         }
/*       */         else
/*       */         {
/*  3204 */           cinfo.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*  3205 */           int sizeofdata = array_size;
/*  3206 */           if (sizeofdata < 5)
/*       */           {
/*  3208 */             cinfo.setHeight("300");
/*  3209 */             cinfo.setWidth("700");
/*       */           } else {
/*  3211 */             cinfo.setHeight("500");
/*  3212 */             cinfo.setWidth("700");
/*       */           }
/*       */         }
/*       */         
/*  3216 */         sumgraph.setMethodName("generateAttributeReport");
/*  3217 */         if ((t != null) && (t.length > 0)) {
/*  3218 */           sumgraph.setResourceName(t[0]);
/*       */         }
/*  3220 */         cinfo.setYaxisLabel(yaxis);
/*  3221 */         cinfo.setShape(true);
/*  3222 */         cinfo.setCustomDateAxis(true);
/*  3223 */         cinfo.setCustomAngle(270.0D);
/*  3224 */         cinfo.setDataSet(sumgraph);
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3250 */         request.setAttribute("reportname", "AttributeReport");
/*  3251 */         request.setAttribute("height", cinfo.getHeight());
/*  3252 */         String image = null;
/*  3253 */         if ("true".equals(Constants.attributesReportGraphType))
/*       */         {
/*  3255 */           image = cinfo.get3DbarChartAsJPG();
/*       */         }
/*       */         else
/*       */         {
/*  3259 */           image = cinfo.getTimeChartAsJPG();
/*       */         }
/*       */         
/*  3262 */         request.setAttribute("attributeImage", image);
/*  3263 */         request.setAttribute("period", period);
/*  3264 */         request.setAttribute("periodvalue", valueforpdf);
/*  3265 */         request.setAttribute("report-type-template", "report.perf");
/*  3266 */         return mapping.findForward("report.perf.pdf");
/*       */       }
/*  3268 */       if (controls.getReporttype().equals("csv")) {
/*  3269 */         if (request.getParameter("from_s247") != null)
/*       */         {
/*  3271 */           return mapping.findForward("report.s247.csv");
/*       */         }
/*       */         
/*       */ 
/*  3275 */         return mapping.findForward("report.perf.csv");
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  3280 */       return mapping.findForward("report.perf");
/*       */     }
/*       */     catch (Exception exp) {
/*       */       String Units;
/*  3284 */       exp.printStackTrace();
/*  3285 */       AMLog.fatal("Reports :  Exception ", exp);
/*  3286 */       request.setAttribute("heading", "0");
/*  3287 */       request.setAttribute("strTime", "0");
/*  3288 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  3289 */       saveMessages(request, messages);
/*  3290 */       return mapping.findForward("report.message");
/*       */     }
/*       */     finally {
/*  3293 */       closeResultSet(set);
/*       */     }
/*       */   }
/*       */   
/*       */   public ActionForward generateMttrAvailablityReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  3300 */     ActionMessages messages = new ActionMessages();
/*  3301 */     ResultSet set = null;
/*  3302 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  3303 */     ReportForm controls = (ReportForm)form;
/*       */     try
/*       */     {
/*  3306 */       String resourceType = controls.getResourceType();
/*  3307 */       String resid = controls.getResid();
/*       */       
/*  3309 */       if ((request.getParameter("resourceid") != null) && ("on".equals(resid))) {
/*  3310 */         resid = request.getParameter("resourceid");
/*  3311 */         controls.setResid(resid);
/*       */       }
/*       */       
/*       */ 
/*  3315 */       String dname = ReportUtilities.getLabelName(resid);
/*  3316 */       controls.setDisplayname(dname);
/*       */       
/*  3318 */       String period = null;
/*  3319 */       String stdat = controls.getStartDate();
/*       */       
/*  3321 */       if ((controls.getStartDate().equals("")) || (controls.getEndDate().equals("")))
/*       */       {
/*  3323 */         period = controls.getPeriod();
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*  3329 */         controls.setPeriod("4");
/*  3330 */         period = controls.getPeriod();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  3336 */       getHolisticApps(mapping, form, request, response);
/*  3337 */       getMonitors(mapping, form, request, response);
/*  3338 */       getCustomApplications(request, "3");
/*  3339 */       request.setAttribute("HelpKey", getMonitorHelpKey(resourceType));
/*  3340 */       String nodata = "report.nodata.time";
/*  3341 */       String attributeids = controls.getAttribute();
/*       */       
/*  3343 */       getAvailabilityData(mapping, form, request, response);
/*       */     }
/*       */     catch (Exception e) {
/*  3346 */       e.printStackTrace();
/*       */     }
/*  3348 */     if (controls.getReporttype().equals("pdf"))
/*       */     {
/*  3350 */       return new ActionForward("/showHistoryData.do?method=getAvailabilityData");
/*       */     }
/*       */     
/*  3353 */     if (controls.getReporttype().equals("csv")) {
/*  3354 */       return mapping.findForward("report.mttr.csv");
/*       */     }
/*       */     
/*  3357 */     if (request.getAttribute("STATUS") == null) {
/*  3358 */       request.setAttribute("STATUS", "SUCCESS");
/*  3359 */       return mapping.findForward("report.mttr");
/*       */     }
/*       */     
/*  3362 */     request.setAttribute("heading", "0");
/*  3363 */     request.setAttribute("strTime", "0");
/*  3364 */     messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.nodata.time"));
/*  3365 */     saveMessages(request, messages);
/*  3366 */     return mapping.findForward("report.message");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward generateSummaryReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  3376 */     ActionMessages messages = new ActionMessages();
/*  3377 */     ResultSet set = null;
/*  3378 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  3379 */     ReportForm controls = (ReportForm)form;
/*  3380 */     String dname = null;
/*  3381 */     String urlsequence = "false";
/*  3382 */     int sizeofseq = -1;
/*  3383 */     int sizeofdisk = -1;
/*  3384 */     String type = null;
/*       */     try {
/*  3386 */       String resourceType = controls.getResourceType();
/*  3387 */       resid = controls.getResid();
/*  3388 */       if ((resid.equalsIgnoreCase("-1")) && (controls.getEummonitors().toString().contains(resourceType)))
/*       */       {
/*  3390 */         resid = controls.getEumMonId();
/*       */       }
/*  3392 */       dname = ReportUtilities.getLabelName(resid);
/*       */       
/*  3394 */       controls.setDisplayname(dname);
/*       */       
/*  3396 */       getHolisticApps(mapping, form, request, response);
/*  3397 */       getMonitors(mapping, form, request, response);
/*  3398 */       getCustomApplications(request, "3");
/*  3399 */       request.setAttribute("HelpKey", getMonitorHelpKey(resourceType));
/*       */       
/*       */ 
/*  3402 */       String nodata = "report.nodata.time";
/*       */       
/*  3404 */       String attributeids = controls.getAttribute();
/*       */       
/*  3406 */       String period = controls.getPeriod();
/*  3407 */       request.setAttribute("period", period);
/*  3408 */       String attribute = request.getParameter("attribute");
/*       */       
/*  3410 */       long[] timeStamps = null;
/*  3411 */       long customstartTime = 0L;
/*  3412 */       long customendTime = 0L;
/*  3413 */       long[] time = ReportUtilities.getTimeStamp(period);
/*  3414 */       long mintimeindb = ReportUtilities.getMinTimeInDB(resid);
/*  3415 */       long startTime = 0L;
/*  3416 */       long endTime = 0L;
/*  3417 */       long totalDuration = 0L;
/*  3418 */       if (period.equals("4")) {
/*  3419 */         String startdate = controls.getStartDate();
/*  3420 */         String enddate = controls.getEndDate();
/*  3421 */         customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  3422 */         customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*  3423 */         if (mintimeindb > customstartTime) {
/*  3424 */           startTime = mintimeindb;
/*       */         }
/*  3426 */         else if (mintimeindb != 0L) {
/*  3427 */           startTime = customstartTime;
/*       */         }
/*  3429 */         long currenttime = System.currentTimeMillis();
/*  3430 */         if (customendTime > currenttime) {
/*  3431 */           endTime = currenttime;
/*       */         }
/*       */         else {
/*  3434 */           endTime = customendTime;
/*       */         }
/*  3436 */         totalDuration = endTime - startTime;
/*       */ 
/*       */ 
/*       */ 
/*       */       }
/*  3441 */       else if (mintimeindb > time[0]) {
/*  3442 */         startTime = mintimeindb;
/*       */         
/*  3444 */         endTime = time[1];
/*       */         
/*  3446 */         totalDuration = endTime - startTime;
/*       */       }
/*  3448 */       else if (mintimeindb != 0L) {
/*  3449 */         startTime = time[0];
/*       */         
/*  3451 */         endTime = time[1];
/*       */         
/*  3453 */         totalDuration = endTime - startTime;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3460 */       String typequery = "SELECT TYPE FROM AM_ManagedObject where RESOURCEID= '" + resid + "'";
/*  3461 */       set = AMConnectionPool.executeQueryStmt(typequery);
/*  3462 */       AMLog.debug("Reports ;" + typequery);
/*       */       
/*  3464 */       if (set.next()) {
/*  3465 */         type = set.getString("TYPE");
/*       */       }
/*       */       
/*  3468 */       ArrayList data = new ArrayList();
/*  3469 */       data.add(resid);
/*  3470 */       data.add(type);
/*  3471 */       data.add(new Long(startTime));
/*  3472 */       data.add(new Long(endTime));
/*       */       ArrayList graphData;
/*  3474 */       if (type != null)
/*       */       {
/*  3476 */         graphData = new ArrayList();
/*  3477 */         if ((type.equals("QueryMonitor")) || ((type.equals("Script Monitor")) && (resid != null) && (!resid.equals("")))) {
/*  3478 */           graphData = ReportUtilities.getGraphDataForConfigAttList(type + "#" + resid);
/*       */         }
/*  3480 */         else if ((type.equals("SNMP")) || (type.equals("SAP-CCMS")))
/*       */         {
/*  3482 */           graphData = ReportUtilities.getGraphDataForConfigAttList(type + "#" + resid);
/*       */         }
/*       */         else {
/*  3485 */           graphData = ReportUtilities.getGraphDataForConfigAttList(type);
/*       */         }
/*  3487 */         System.out.println("the graph data for the configured att list====>" + graphData);
/*       */         
/*  3489 */         List scriptAttributes = null;
/*  3490 */         boolean isSciptMonitor = false;
/*  3491 */         if ("Script Monitor".equals(type))
/*       */         {
/*  3493 */           isSciptMonitor = true;
/*  3494 */           scriptAttributes = ReportUtil.getAttributesForScript(resid);
/*       */         }
/*       */         
/*       */ 
/*  3498 */         for (int k = 0; k < graphData.size(); k++)
/*       */         {
/*  3500 */           ArrayList tempAttList = (ArrayList)graphData.get(k);
/*  3501 */           if ((isSciptMonitor) && ((scriptAttributes.contains((String)tempAttList.get(1))) || ("2202".equals((String)tempAttList.get(1)))))
/*       */           {
/*  3503 */             data.add(tempAttList);
/*       */           }
/*  3505 */           else if (!isSciptMonitor)
/*       */           {
/*  3507 */             data.add(tempAttList);
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  3512 */       request.setAttribute("strTime", new java.util.Date(((Long)data.get(2)).longValue()));
/*  3513 */       request.setAttribute("endTime", new java.util.Date(((Long)data.get(3)).longValue()));
/*  3514 */       request.setAttribute("data", data);
/*       */       
/*       */ 
/*  3517 */       if (startTime > endTime) {
/*  3518 */         request.setAttribute("heading", "0");
/*  3519 */         request.setAttribute("strTime", "0");
/*  3520 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.nodata.time"));
/*  3521 */         saveMessages(request, messages);
/*  3522 */         return mapping.findForward("report.message");
/*       */       }
/*       */     }
/*       */     catch (Exception exp)
/*       */     {
/*       */       String resid;
/*       */       
/*       */ 
/*  3530 */       exp.printStackTrace();
/*  3531 */       AMLog.fatal("Reports :  Exception ", exp);
/*  3532 */       request.setAttribute("heading", "0");
/*  3533 */       request.setAttribute("strTime", "0");
/*  3534 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  3535 */       saveMessages(request, messages);
/*  3536 */       return mapping.findForward("report.message");
/*       */     }
/*       */     finally {
/*  3539 */       closeResultSet(set);
/*       */     }
/*  3541 */     ArrayList images = new ArrayList();
/*  3542 */     ArrayList attrinames = new ArrayList();
/*  3543 */     HashMap graphSizes = new HashMap();
/*  3544 */     String timeseriesImage = null;
/*       */     
/*       */     try
/*       */     {
/*  3548 */       ArrayList data = (ArrayList)request.getAttribute("data");
/*  3549 */       System.out.println("the data we are sending are===>" + data);
/*  3550 */       ArrayList attName = new ArrayList();
/*  3551 */       String resid = (String)data.get(0);
/*  3552 */       String restype = (String)data.get(1);
/*  3553 */       Hashtable colors = new Hashtable();
/*  3554 */       colors.put("1", "#00FF00");
/*  3555 */       colors.put("0", "#FF0000");
/*  3556 */       colors.put("2", "#0066CC");
/*  3557 */       colors.put("3", "#FF00FF");
/*  3558 */       long starttime = ((Long)data.get(2)).longValue();
/*  3559 */       long endtime = ((Long)data.get(3)).longValue();
/*  3560 */       String attributename = null;
/*  3561 */       String attributeid = null;
/*  3562 */       String attrLevel = null;
/*       */       
/*       */ 
/*  3565 */       ReportGraphs rg = new ReportGraphs();
/*  3566 */       Map params = new Hashtable();
/*  3567 */       params.put("type", "AVAILABILITY");
/*  3568 */       params.put("id", resid);
/*  3569 */       params.put("attid", "-1");
/*  3570 */       params.put("period", controls.getPeriod());
/*  3571 */       params.put("startTime", controls.getStartDate());
/*  3572 */       params.put("endTime", controls.getEndDate());
/*  3573 */       params.put("attributeName", "Availability");
/*  3574 */       rg.setParams(params);
/*       */       
/*  3576 */       ChartInfo cinfo = new ChartInfo();
/*  3577 */       cinfo.setDataSet(rg);
/*  3578 */       cinfo.setHeight("200");
/*  3579 */       cinfo.setWidth("500");
/*  3580 */       cinfo.setColors(colors);
/*  3581 */       cinfo.setDecimal(true);
/*  3582 */       cinfo.setUnits("%");
/*       */       
/*  3584 */       timeseriesImage = cinfo.getPieChartAsJPG();
/*       */       
/*       */ 
/*       */ 
/*  3588 */       attrinames.add(FormatUtil.getString("am.webclient.common.availability.text"));
/*  3589 */       images.add(timeseriesImage);
/*  3590 */       graphSizes.put(FormatUtil.getString("am.webclient.common.availability.text"), "500:200");
/*       */       
/*  3592 */       String hostresid = null;
/*  3593 */       String queryresid = null;
/*  3594 */       if (Constants.sqlManager) {
/*       */         try {
/*  3596 */           HashMap sqlChildids = DBUtil.getSqlchildids(resid);
/*  3597 */           if (sqlChildids.size() > 0) {
/*  3598 */             hostresid = (String)sqlChildids.get("hostresid");
/*  3599 */             queryresid = (String)sqlChildids.get("queryresid");
/*       */           }
/*       */         }
/*       */         catch (Exception eq) {
/*  3603 */           eq.printStackTrace();
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  3609 */       for (int i = 4; i < data.size(); i++)
/*       */       {
/*  3611 */         ArrayList a1 = (ArrayList)data.get(i);
/*  3612 */         attributename = (String)a1.get(0);
/*  3613 */         attributeid = (String)a1.get(1);
/*  3614 */         System.out.println("the attribute id===>" + attributeid);
/*  3615 */         SummaryBean sumgraph = new SummaryBean();
/*  3616 */         sumgraph.setResid(resid);
/*       */         
/*  3618 */         if (Constants.sqlManager)
/*       */         {
/*  3620 */           if (DBUtil.windowsAttributeList.contains(attributeid)) {
/*  3621 */             sumgraph.setResid(hostresid);
/*  3622 */           } else if (DBUtil.querymonitorAttributeList.contains(attributeid)) {
/*  3623 */             sumgraph.setResid(queryresid);
/*       */           }
/*       */         }
/*  3626 */         sumgraph.setAttributeid(attributeid);
/*  3627 */         sumgraph.setStarttime(String.valueOf(starttime));
/*  3628 */         sumgraph.setEndtime(String.valueOf(endtime));
/*  3629 */         sumgraph.setEntity(attributename);
/*  3630 */         String archivedTableName = DBUtil.getArchiveingTableName(attributeid);
/*  3631 */         long[] dailyRptTimestamp = ReportUtilities.getDailyStartEndTime(starttime, endtime, archivedTableName);
/*  3632 */         if (dailyRptTimestamp[2] > 0L)
/*       */         {
/*  3634 */           sumgraph.setDailyRptStarttime(String.valueOf(dailyRptTimestamp[2]));
/*  3635 */           sumgraph.setDailyRptEndtime(String.valueOf(dailyRptTimestamp[3]));
/*       */         }
/*  3637 */         ArrayList allSecondLevelAttribute = (ArrayList)((ArrayList)ReportUtil.getAllSecondLevelAttribute()).clone();
/*       */         
/*  3639 */         if ("VMWare ESX/ESXi".equals(type))
/*       */         {
/*  3641 */           allSecondLevelAttribute.addAll(ReportUtil.getAttributeIDsforResType("VirtualMachine"));
/*       */         }
/*  3643 */         if ("Amazon".equals(type)) {
/*  3644 */           allSecondLevelAttribute.addAll(ReportUtil.getAttributeIDsforResType("EC2Instance"));
/*  3645 */           allSecondLevelAttribute.addAll(ReportUtil.getAttributeIDsforResType("RDSInstance"));
/*       */         }
/*  3647 */         if ("XenServerHost".equals(type))
/*       */         {
/*  3649 */           allSecondLevelAttribute.addAll(ReportUtil.getAttributeIDsforResType("XenServerVM"));
/*       */         }
/*  3651 */         if ("Hyper-V-Server".equals(type)) {
/*  3652 */           allSecondLevelAttribute.addAll(ReportUtil.getAttributeIDsforResType("HyperVVirtualMachine"));
/*       */         }
/*       */         
/*  3655 */         if ((attributeid != null) && ((attributeid.equalsIgnoreCase("410")) || (allSecondLevelAttribute.contains(attributeid))))
/*       */         {
/*  3657 */           String rids = getResIds(resid, attributeid);
/*       */           
/*       */ 
/*       */ 
/*  3661 */           if (Constants.sqlManager)
/*       */           {
/*  3663 */             if (DBUtil.windowsAttributeList.contains(attributeid)) {
/*  3664 */               rids = getResIds(hostresid, attributeid);
/*  3665 */             } else if (DBUtil.querymonitorAttributeList.contains(attributeid)) {
/*  3666 */               rids = getResIds(queryresid, attributeid);
/*       */             }
/*       */           }
/*  3669 */           sumgraph.setResid(rids);
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3675 */           sumgraph.setArchivedforUrl(true);
/*  3676 */           sumgraph.setCompareUrls(true);
/*       */         }
/*  3678 */         ChartInfo cinfo1 = new ChartInfo();
/*  3679 */         cinfo1.setDataSet(sumgraph);
/*  3680 */         cinfo1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*  3681 */         cinfo1.setYaxisLabel(attributename);
/*  3682 */         cinfo1.setShape(true);
/*  3683 */         cinfo1.setCustomDateAxis(true);
/*  3684 */         cinfo1.setCustomAngle(270.0D);
/*       */         
/*  3686 */         int height = 0;
/*  3687 */         if ((attributeid != null) && (attributeid.equals("711")))
/*       */         {
/*  3689 */           String q2 = "select count(*) as value from AM_ManagedObject,AM_PARENTCHILDMAPPER  where AM_PARENTCHILDMAPPER.PARENTID='" + resid + "'  and  AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID  and type='Disk'";
/*       */           
/*  3691 */           set = AMConnectionPool.executeQueryStmt(q2);
/*  3692 */           ArrayList aq = new ArrayList();
/*  3693 */           if (set.next())
/*       */           {
/*  3695 */             sizeofdisk = set.getInt("value");
/*       */           }
/*  3697 */           if (sizeofdisk <= 5)
/*       */           {
/*  3699 */             cinfo1.setHeight("250");
/*  3700 */             height = 250;
/*       */           }
/*  3702 */           else if ((sizeofdisk > 5) && (sizeofdisk <= 10))
/*       */           {
/*  3704 */             height = 400;
/*  3705 */             cinfo1.setHeight("400");
/*       */           }
/*  3707 */           else if ((sizeofdisk > 10) && (sizeofdisk <= 20)) {
/*  3708 */             height = 600;
/*  3709 */             cinfo1.setHeight("600");
/*       */           }
/*  3711 */           else if ((sizeofdisk > 20) && (sizeofdisk <= 30)) {
/*  3712 */             height = 750;
/*  3713 */             cinfo1.setHeight("750");
/*       */           }
/*  3715 */           else if ((sizeofdisk > 30) && (sizeofdisk <= 40)) {
/*  3716 */             height = 900;
/*  3717 */             cinfo1.setHeight("900");
/*       */           }
/*  3719 */           else if ((sizeofdisk > 40) && (sizeofdisk <= 50)) {
/*  3720 */             height = 1000;
/*  3721 */             cinfo1.setHeight("1000");
/*       */           }
/*       */           else {
/*  3724 */             height = 1200;
/*  3725 */             cinfo1.setHeight("1200");
/*       */           }
/*       */           
/*       */ 
/*       */         }
/*  3730 */         else if ((attributeid.equals("410")) || (allSecondLevelAttribute.contains(attributeid))) {
/*  3731 */           String q3 = "select count(*) as value FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resid + " AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID AND AM_ManagedObject.TYPE ='" + ReportUtilities.getResourceTypeForAttribute(attributeid) + "'";
/*  3732 */           if (DBUtil.isEUMParent(resid))
/*       */           {
/*  3734 */             Vector<String> agentIds = new Vector();
/*  3735 */             ParentChildRelationalUtil.getAllChildMOs(agentIds, resid);
/*  3736 */             String rid = agentIds.toString().substring(1, agentIds.toString().length() - 1);
/*  3737 */             q3 = "select count(*) as value FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID IN (" + rid + ") AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID AND AM_ManagedObject.TYPE ='" + ReportUtilities.getResourceTypeForAttribute(attributeid) + "'";
/*       */           }
/*  3739 */           set = AMConnectionPool.executeQueryStmt(q3);
/*  3740 */           ArrayList aq = new ArrayList();
/*  3741 */           if (set.next())
/*       */           {
/*  3743 */             sizeofseq = set.getInt("value");
/*       */           }
/*  3745 */           if (sizeofseq <= 5) {
/*  3746 */             height = 250;
/*  3747 */             cinfo1.setHeight("250");
/*       */           }
/*  3749 */           else if ((sizeofseq > 5) && (sizeofseq <= 10)) {
/*  3750 */             height = 450;
/*  3751 */             cinfo1.setHeight("450");
/*       */ 
/*       */           }
/*  3754 */           else if ((sizeofseq > 10) && (sizeofseq <= 20)) {
/*  3755 */             height = 750;
/*  3756 */             cinfo1.setHeight("750");
/*       */           }
/*  3758 */           else if ((sizeofseq > 20) && (sizeofseq <= 30)) {
/*  3759 */             height = 850;
/*  3760 */             cinfo1.setHeight("850");
/*       */           }
/*  3762 */           else if ((sizeofseq > 30) && (sizeofseq <= 40)) {
/*  3763 */             height = 950;
/*  3764 */             cinfo1.setHeight("950");
/*       */           }
/*       */           else {
/*  3767 */             height = 1050;
/*  3768 */             cinfo1.setHeight("1050");
/*       */           }
/*       */           
/*       */         }
/*       */         else
/*       */         {
/*  3774 */           height = 200;
/*  3775 */           cinfo1.setHeight("200");
/*       */         }
/*       */         
/*       */ 
/*  3779 */         cinfo1.setWidth("600");
/*       */         
/*       */ 
/*  3782 */         timeseriesImage = cinfo1.getTimeChartAsJPG();
/*  3783 */         images.add(timeseriesImage);
/*  3784 */         attrinames.add(attributename);
/*  3785 */         graphSizes.put(attributename, "600:" + height);
/*  3786 */         attName.add(attributename);
/*       */       }
/*       */       
/*  3789 */       request.setAttribute("graphs", images);
/*       */       
/*  3791 */       request.setAttribute("attributename", attName);
/*       */     }
/*       */     catch (Exception ed) {
/*  3794 */       ed.printStackTrace();
/*       */     }
/*       */     
/*  3797 */     if ("pdf".equals(controls.getReporttype()))
/*       */     {
/*       */ 
/*  3800 */       request.setAttribute("reportType", "pdf");
/*  3801 */       request.setAttribute("images", images);
/*  3802 */       request.setAttribute("attrinames", attrinames);
/*  3803 */       request.setAttribute("report-type-template", "report.summary");
/*  3804 */       request.setAttribute("GraphSize", graphSizes);
/*       */       
/*       */ 
/*       */ 
/*  3808 */       return mapping.findForward("report.summary.pdf");
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  3813 */     return mapping.findForward("report.summary");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public String getResIds(String rid, String attid)
/*       */   {
/*  3821 */     ResultSet rs = null;
/*  3822 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  3823 */     String resids = null;
/*       */     try
/*       */     {
/*  3826 */       String attrType = ReportUtilities.getResourceTypeForAttribute(attid);
/*  3827 */       boolean queryMon = false;
/*  3828 */       ArrayList curResids = new ArrayList();
/*  3829 */       if (attrType.startsWith("QueryMonitor_")) {
/*  3830 */         curResids = getCurrentQueryMonitorResourceIDs(rid, "QueryMonitor");
/*  3831 */         queryMon = true;
/*       */       }
/*       */       
/*  3834 */       String query12 = "select RESOURCENAME,RESOURCEID FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + rid + " AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID AND AM_ManagedObject.TYPE ='" + ReportUtilities.getResourceTypeForAttribute(attid) + "' ORDER BY AM_ManagedObject.RESOURCEID";
/*  3835 */       if (DBUtil.isEUMParent(rid))
/*       */       {
/*  3837 */         Vector<String> agentIds = new Vector();
/*  3838 */         ParentChildRelationalUtil.getAllChildMOs(agentIds, rid);
/*  3839 */         rid = agentIds.toString().substring(1, agentIds.toString().length() - 1);
/*  3840 */         query12 = "select RESOURCENAME,RESOURCEID FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID IN (" + rid + ") AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID AND AM_ManagedObject.TYPE ='" + ReportUtilities.getResourceTypeForAttribute(attid) + "' ORDER BY AM_ManagedObject.RESOURCEID";
/*       */       }
/*  3842 */       System.out.println("the query for getting resids are===>" + query12);
/*  3843 */       rs = AMConnectionPool.executeQueryStmt(query12);
/*  3844 */       while (rs.next())
/*       */       {
/*  3846 */         String id = rs.getString("RESOURCEID");
/*       */         
/*  3848 */         if ((!queryMon) || ("QueryMonitor_Execution Time_ROW".equalsIgnoreCase(attrType)) || (curResids.contains(id)))
/*       */         {
/*       */ 
/*       */ 
/*  3852 */           if (resids != null) {
/*  3853 */             resids = resids + "," + id;
/*       */           }
/*       */           else {
/*  3856 */             resids = id;
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  3861 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */     catch (Exception ex) {
/*  3864 */       ex.printStackTrace();
/*       */     }
/*       */     
/*  3867 */     return resids;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ActionForward getAvailabilityData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  3875 */     ReportForm reportform = (ReportForm)form;
/*  3876 */     ActionMessages messages = new ActionMessages();
/*  3877 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  3878 */     ResultSet set = null;
/*  3879 */     String startdate = reportform.getStartDate();
/*  3880 */     String enddate = reportform.getEndDate();
/*       */     
/*  3882 */     String period = reportform.getPeriod();
/*       */     
/*  3884 */     String resourceid = request.getParameter("resourceid");
/*       */     
/*  3886 */     long[] time = ReportUtilities.getTimeStamp(period);
/*  3887 */     long customstartTime = 0L;
/*  3888 */     long customendTime = 0L;
/*  3889 */     long startTime = 0L;
/*  3890 */     long endTime = 0L;
/*  3891 */     long totalDuration = 0L;
/*  3892 */     long unmanagedtime = 0L;
/*  3893 */     long scheduledtime = 0L;
/*  3894 */     long totdowntime = 0L;
/*  3895 */     ArrayList downtimesummary = new ArrayList();
/*  3896 */     ArrayList downtimehistory = new ArrayList();
/*  3897 */     Properties summary = null;
/*  3898 */     long tempST = 0L;
/*  3899 */     long tempET = 0L;
/*  3900 */     ResultSet displayNameSet = null;
/*       */     
/*       */     try
/*       */     {
/*       */       try
/*       */       {
/*  3906 */         String displayNameQuery = "select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resourceid;
/*  3907 */         displayNameSet = AMConnectionPool.executeQueryStmt(displayNameQuery);
/*  3908 */         if (displayNameSet.next()) {
/*  3909 */           displayName = displayNameSet.getString("DISPLAYNAME");
/*  3910 */           displayName = EnterpriseUtil.decodeString(displayName);
/*  3911 */           request.setAttribute("resourcename", displayName);
/*       */         }
/*       */       }
/*       */       catch (Exception ex)
/*       */       {
/*  3916 */         log.fatal("Exception in getting RESOURCENAME details ", ex);
/*       */       }
/*  3918 */       long mintimeindb = ReportUtilities.getMinTimeInDB(resourceid);
/*  3919 */       long[] temptime = ReportUtilities.getTimeStamp("0");
/*  3920 */       if (period.equals("4"))
/*       */       {
/*  3922 */         request.setAttribute("period", period);
/*  3923 */         customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  3924 */         customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*       */         
/*       */ 
/*       */ 
/*  3928 */         if (customstartTime > customendTime) {
/*  3929 */           period = "0";
/*       */         }
/*       */         else {
/*       */           java.util.Date errorendtime;
/*  3933 */           if (customendTime < mintimeindb) {
/*  3934 */             period = "0";
/*  3935 */             errorendtime = new java.util.Date(mintimeindb);
/*       */           }
/*       */           else {
/*       */             java.util.Date errorstarttime;
/*  3939 */             if (customstartTime > System.currentTimeMillis()) {
/*  3940 */               period = "0";
/*  3941 */               errorstarttime = new java.util.Date(System.currentTimeMillis());
/*       */ 
/*       */             }
/*       */             else
/*       */             {
/*       */ 
/*  3947 */               if (mintimeindb > customstartTime) {
/*  3948 */                 startTime = mintimeindb;
/*       */               }
/*  3950 */               else if (mintimeindb != 0L) {
/*  3951 */                 startTime = customstartTime;
/*       */               }
/*  3953 */               long currenttime = System.currentTimeMillis();
/*       */               
/*  3955 */               if (customendTime > currenttime) {
/*  3956 */                 endTime = currenttime;
/*       */               }
/*       */               else {
/*  3959 */                 endTime = customendTime;
/*  3960 */                 totalDuration = endTime - startTime;
/*       */               }
/*  3962 */               String showstartDate = FormatUtil.formatDT(String.valueOf(startTime));
/*  3963 */               String showendDate = FormatUtil.formatDT(String.valueOf(endTime));
/*       */               
/*  3965 */               Properties props4 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*       */               
/*  3967 */               props4.setProperty("period", "From " + showstartDate + " to " + showendDate);
/*       */               
/*  3969 */               request.setAttribute("timeperiod", FormatUtil.getString("am.webclient.availablityreport.customtimeheading.fromtotext", new String[] { showstartDate, showendDate }));
/*  3970 */               request.setAttribute("summary", props4);
/*  3971 */               summary = props4;
/*  3972 */               tempST = startTime;
/*  3973 */               tempET = endTime;
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  3979 */       request.setAttribute("period", period);
/*  3980 */       if (mintimeindb > temptime[0])
/*       */       {
/*  3982 */         startTime = mintimeindb;
/*  3983 */         endTime = temptime[1];
/*  3984 */         totalDuration = endTime - startTime;
/*       */       }
/*  3986 */       else if (mintimeindb != 0L)
/*       */       {
/*  3988 */         startTime = temptime[0];
/*  3989 */         endTime = temptime[1];
/*  3990 */         totalDuration = endTime - startTime;
/*       */       }
/*  3992 */       Properties props = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  3993 */       props.setProperty("period", "Today");
/*  3994 */       downtimehistory.add(props);
/*  3995 */       if (period.equals("0"))
/*       */       {
/*  3997 */         request.setAttribute("timeperiod", FormatUtil.getString("Today's"));
/*  3998 */         request.setAttribute("summary", props);
/*  3999 */         summary = props;
/*  4000 */         tempST = startTime;
/*  4001 */         tempET = endTime;
/*       */       }
/*  4003 */       long todayST = temptime[0];
/*  4004 */       temptime = ReportUtilities.getTimeStamp("3");
/*       */       
/*  4006 */       if (mintimeindb > todayST)
/*       */       {
/*  4008 */         Properties props3 = new Properties();
/*  4009 */         props3.setProperty("period", "Yesterday");
/*  4010 */         props3.setProperty("yesterdaydata", "Not Applicable");
/*       */         
/*  4012 */         downtimehistory.add(props3);
/*  4013 */         if (period.equals("3"))
/*       */         {
/*       */ 
/*  4016 */           String status1 = "No data is available from " + new java.util.Date(startTime) + " to " + new java.util.Date(endTime);
/*  4017 */           request.setAttribute("STATUS", status1);
/*       */         }
/*       */         
/*       */       }
/*       */       else
/*       */       {
/*  4023 */         if ((mintimeindb > temptime[0]) && (mintimeindb < todayST))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4030 */           startTime = mintimeindb;
/*  4031 */           endTime = temptime[1];
/*  4032 */           totalDuration = endTime - startTime;
/*       */         }
/*  4034 */         else if (mintimeindb != 0L)
/*       */         {
/*  4036 */           startTime = temptime[0];
/*  4037 */           endTime = temptime[1];
/*  4038 */           totalDuration = endTime - startTime;
/*       */         }
/*  4040 */         Properties props3 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*       */         
/*  4042 */         props3.setProperty("period", "Yesterday");
/*  4043 */         props3.setProperty("yesterdaydata", "Applicable");
/*  4044 */         downtimehistory.add(props3);
/*       */         
/*       */ 
/*  4047 */         if (period.equals("3"))
/*       */         {
/*  4049 */           request.setAttribute("timeperiod", FormatUtil.getString("Yesterday's"));
/*  4050 */           request.setAttribute("summary", props3);
/*  4051 */           summary = props3;
/*  4052 */           tempST = startTime;
/*  4053 */           tempET = endTime;
/*       */         }
/*       */       }
/*       */       
/*  4057 */       temptime = ReportUtilities.getTimeStamp("1");
/*  4058 */       if (mintimeindb > temptime[0]) {
/*  4059 */         startTime = mintimeindb;
/*  4060 */         endTime = temptime[1];
/*  4061 */         totalDuration = endTime - startTime;
/*       */       }
/*  4063 */       else if (mintimeindb != 0L) {
/*  4064 */         startTime = temptime[0];
/*  4065 */         endTime = temptime[1];
/*  4066 */         totalDuration = endTime - startTime;
/*       */       }
/*  4068 */       Properties props1 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  4069 */       props1.setProperty("period", "Last 7 Days");
/*  4070 */       downtimehistory.add(props1);
/*  4071 */       if (period.equals("1")) {
/*  4072 */         request.setAttribute("timeperiod", "Last 7 Days");
/*  4073 */         request.setAttribute("summary", props1);
/*  4074 */         summary = props1;
/*  4075 */         tempST = startTime;
/*  4076 */         tempET = endTime;
/*       */       }
/*  4078 */       temptime = ReportUtilities.getTimeStamp("2");
/*  4079 */       if (mintimeindb > temptime[0]) {
/*  4080 */         startTime = mintimeindb;
/*  4081 */         endTime = temptime[1];
/*  4082 */         totalDuration = endTime - startTime;
/*       */       }
/*  4084 */       else if (mintimeindb != 0L) {
/*  4085 */         startTime = temptime[0];
/*  4086 */         endTime = temptime[1];
/*  4087 */         totalDuration = endTime - startTime;
/*       */       }
/*  4089 */       Properties props2 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  4090 */       props2.setProperty("period", "Last 30 Days");
/*  4091 */       downtimehistory.add(props2);
/*  4092 */       if (period.equals("2")) {
/*  4093 */         request.setAttribute("timeperiod", "Last 30 Days");
/*  4094 */         request.setAttribute("summary", props2);
/*  4095 */         summary = props2;
/*  4096 */         tempST = startTime;
/*  4097 */         tempET = endTime;
/*       */       }
/*  4099 */       temptime = ReportUtilities.getTimeStamp("5");
/*  4100 */       if (mintimeindb > temptime[0]) {
/*  4101 */         startTime = mintimeindb;
/*  4102 */         endTime = temptime[1];
/*  4103 */         totalDuration = endTime - startTime;
/*       */       }
/*  4105 */       else if (mintimeindb != 0L) {
/*  4106 */         startTime = temptime[0];
/*  4107 */         endTime = temptime[1];
/*  4108 */         totalDuration = endTime - startTime;
/*       */       }
/*  4110 */       Properties props5 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  4111 */       props5.setProperty("period", "Last One Year");
/*  4112 */       downtimehistory.add(props5);
/*  4113 */       if (period.equals("5")) {
/*  4114 */         request.setAttribute("timeperiod", FormatUtil.getString("Last 1 Year's"));
/*  4115 */         request.setAttribute("summary", props5);
/*  4116 */         summary = props5;
/*  4117 */         tempST = startTime;
/*  4118 */         tempET = endTime;
/*       */       }
/*       */       
/*  4121 */       temptime = ReportUtilities.getTimeStamp("6");
/*  4122 */       if (mintimeindb > temptime[0]) {
/*  4123 */         startTime = mintimeindb;
/*  4124 */         endTime = temptime[1];
/*  4125 */         totalDuration = endTime - startTime;
/*       */       }
/*  4127 */       else if (mintimeindb != 0L) {
/*  4128 */         startTime = temptime[0];
/*  4129 */         endTime = temptime[1];
/*  4130 */         totalDuration = endTime - startTime;
/*       */       }
/*       */       
/*       */ 
/*  4134 */       if (totalDuration < 0L)
/*       */       {
/*       */ 
/*       */ 
/*  4138 */         Properties props6 = new Properties();
/*  4139 */         props6.setProperty("period", "This Week");
/*  4140 */         props6.setProperty("thisweekdata", "Not Applicable");
/*  4141 */         downtimehistory.add(props6);
/*  4142 */         if (period.equals("6"))
/*       */         {
/*       */ 
/*       */ 
/*  4146 */           String status1 = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(temptime[0]).toString(), new java.util.Date(temptime[1]).toString() });
/*  4147 */           request.setAttribute("STATUS", status1);
/*  4148 */           return mapping.getInputForward();
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/*  4153 */         Properties props6 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  4154 */         props6.setProperty("period", "This Week");
/*  4155 */         props6.setProperty("thisweekdata", "Applicable");
/*  4156 */         downtimehistory.add(props6);
/*  4157 */         if (period.equals("6")) {
/*  4158 */           request.setAttribute("timeperiod", FormatUtil.getString("This Week's"));
/*  4159 */           request.setAttribute("summary", props6);
/*  4160 */           summary = props6;
/*  4161 */           tempST = startTime;
/*  4162 */           tempET = endTime;
/*       */         }
/*       */       }
/*       */       
/*  4166 */       long weekST = temptime[0];
/*  4167 */       temptime = ReportUtilities.getTimeStamp("7");
/*  4168 */       if (mintimeindb > temptime[0]) {
/*  4169 */         startTime = mintimeindb;
/*  4170 */         endTime = temptime[1];
/*  4171 */         totalDuration = endTime - startTime;
/*       */       }
/*  4173 */       else if (mintimeindb != 0L) {
/*  4174 */         startTime = temptime[0];
/*  4175 */         endTime = temptime[1];
/*  4176 */         totalDuration = endTime - startTime;
/*       */       }
/*  4178 */       Properties props7 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  4179 */       props7.setProperty("period", "This Month");
/*  4180 */       downtimehistory.add(props7);
/*  4181 */       if (period.equals("7")) {
/*  4182 */         request.setAttribute("timeperiod", FormatUtil.getString("This Month's"));
/*  4183 */         request.setAttribute("summary", props7);
/*  4184 */         summary = props7;
/*  4185 */         tempST = startTime;
/*  4186 */         tempET = endTime;
/*       */       }
/*  4188 */       long monthST = temptime[0];
/*  4189 */       temptime = ReportUtilities.getTimeStamp("8");
/*  4190 */       if (mintimeindb > temptime[0]) {
/*  4191 */         startTime = mintimeindb;
/*  4192 */         endTime = temptime[1];
/*  4193 */         totalDuration = endTime - startTime;
/*       */       }
/*  4195 */       else if (mintimeindb != 0L) {
/*  4196 */         startTime = temptime[0];
/*  4197 */         endTime = temptime[1];
/*  4198 */         totalDuration = endTime - startTime;
/*       */       }
/*  4200 */       Properties props8 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  4201 */       props8.setProperty("period", "This Year");
/*  4202 */       downtimehistory.add(props8);
/*  4203 */       if (period.equals("8")) {
/*  4204 */         request.setAttribute("timeperiod", FormatUtil.getString("This Year's"));
/*  4205 */         request.setAttribute("summary", props8);
/*  4206 */         summary = props8;
/*  4207 */         tempST = startTime;
/*  4208 */         tempET = endTime;
/*       */       }
/*  4210 */       temptime = ReportUtilities.getTimeStamp("9");
/*  4211 */       if (mintimeindb > temptime[0]) {
/*  4212 */         startTime = mintimeindb;
/*  4213 */         endTime = temptime[1];
/*  4214 */         totalDuration = endTime - startTime;
/*       */       }
/*  4216 */       else if (mintimeindb != 0L) {
/*  4217 */         startTime = temptime[0];
/*  4218 */         endTime = temptime[1];
/*  4219 */         totalDuration = endTime - startTime;
/*       */       }
/*  4221 */       Properties props9 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  4222 */       props9.setProperty("period", "This Quarter");
/*  4223 */       downtimehistory.add(props9);
/*  4224 */       if (period.equals("9")) {
/*  4225 */         request.setAttribute("timeperiod", FormatUtil.getString("This Quarter's"));
/*  4226 */         request.setAttribute("summary", props9);
/*  4227 */         summary = props9;
/*  4228 */         tempST = startTime;
/*  4229 */         tempET = endTime;
/*       */       }
/*       */       
/*  4232 */       temptime = ReportUtilities.getTimeStamp("11");
/*  4233 */       if (mintimeindb > monthST)
/*       */       {
/*  4235 */         Properties props11 = new Properties();
/*  4236 */         props11.setProperty("period", "Last Month");
/*  4237 */         props11.setProperty("LastMonthdata", "Not Applicable");
/*  4238 */         downtimehistory.add(props11);
/*       */         
/*  4240 */         if (period.equals("11"))
/*       */         {
/*  4242 */           String status1 = "No data is available from " + new java.util.Date(startTime) + " to " + new java.util.Date(endTime);
/*  4243 */           request.setAttribute("STATUS", status1);
/*  4244 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(status1));
/*  4245 */           saveMessages(request, messages);
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  4252 */         if ((mintimeindb > temptime[0]) && (mintimeindb < monthST))
/*       */         {
/*       */ 
/*       */ 
/*  4256 */           startTime = mintimeindb;
/*  4257 */           endTime = temptime[1];
/*  4258 */           totalDuration = endTime - startTime;
/*       */         }
/*  4260 */         else if (mintimeindb != 0L) {
/*  4261 */           startTime = temptime[0];
/*  4262 */           endTime = temptime[1];
/*  4263 */           totalDuration = endTime - startTime;
/*       */         }
/*       */         
/*  4266 */         Properties props11 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  4267 */         props11.setProperty("period", "Last Month");
/*  4268 */         props11.setProperty("LastMonthdata", "Applicable");
/*  4269 */         downtimehistory.add(props11);
/*       */         
/*  4271 */         if (period.equals("11")) {
/*  4272 */           request.setAttribute("timeperiod", FormatUtil.getString("Last Month's"));
/*  4273 */           request.setAttribute("summary", props11);
/*  4274 */           summary = props11;
/*  4275 */           tempST = startTime;
/*  4276 */           tempET = endTime;
/*       */         }
/*       */       }
/*       */       
/*  4280 */       temptime = ReportUtilities.getTimeStamp("12");
/*       */       
/*  4282 */       if (mintimeindb > weekST)
/*       */       {
/*  4284 */         Properties props12 = new Properties();
/*  4285 */         props12.setProperty("period", "Last Week");
/*  4286 */         props12.setProperty("LastWeekdata", "Not Applicable");
/*  4287 */         downtimehistory.add(props12);
/*       */         
/*  4289 */         if (period.equals("12"))
/*       */         {
/*  4291 */           String status1 = "No data is available from " + new java.util.Date(startTime) + " to " + new java.util.Date(endTime);
/*  4292 */           request.setAttribute("STATUS", status1);
/*       */         }
/*       */         
/*       */       }
/*       */       else
/*       */       {
/*  4298 */         if ((mintimeindb > temptime[0]) && (mintimeindb < weekST))
/*       */         {
/*  4300 */           startTime = mintimeindb;
/*  4301 */           endTime = temptime[1];
/*  4302 */           totalDuration = endTime - startTime;
/*       */         }
/*  4304 */         else if (mintimeindb != 0L) {
/*  4305 */           startTime = temptime[0];
/*  4306 */           endTime = temptime[1];
/*  4307 */           totalDuration = endTime - startTime;
/*       */         }
/*       */         
/*  4310 */         Properties props12 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*       */         
/*  4312 */         props12.setProperty("period", "Last Week");
/*  4313 */         props12.setProperty("LastWeekdata", "Applicable");
/*  4314 */         downtimehistory.add(props12);
/*  4315 */         if (period.equals("12")) {
/*  4316 */           request.setAttribute("timeperiod", FormatUtil.getString("Last Week's"));
/*  4317 */           request.setAttribute("summary", props12);
/*  4318 */           summary = props12;
/*  4319 */           tempST = startTime;
/*  4320 */           tempET = endTime;
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  4326 */       request.setAttribute("downtimehistory", downtimehistory);
/*       */       
/*  4328 */       startTime = tempST;
/*  4329 */       endTime = tempET;
/*       */       
/*  4331 */       String query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.REASONID,SHORT_DESCRIPTION,TYPE from AM_MO_DowntimeData left outer join AM_DOWNTIMEREASON on AM_MO_DowntimeData.REASONID = AM_DOWNTIMEREASON.REASONID where RESID=" + resourceid + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/*  4332 */       if ("true".equals(Constants.addMaintenanceToAvailablity))
/*  4333 */         query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.REASONID,SHORT_DESCRIPTION,TYPE from AM_MO_DowntimeData left outer join AM_DOWNTIMEREASON on AM_MO_DowntimeData.REASONID = AM_DOWNTIMEREASON.REASONID where RESID=" + resourceid + " and TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/*       */       int typeID;
/*  4335 */       if ((summary != null) && ((summary.getProperty("totaldowntime").indexOf("0 Min(s) 0 Sec(s)") == -1) || (summary.getProperty("showManaged").equals("true")) || (summary.getProperty("showScheduled").equals("true")))) {
/*  4336 */         set = AMConnectionPool.executeQueryStmt(query);
/*  4337 */         while (set.next()) {
/*  4338 */           typeID = -1;
/*  4339 */           Properties rows = new Properties();
/*  4340 */           rows.put("downtime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("DownTime"))));
/*  4341 */           if (set.getLong("UpTime") == endTime) {
/*  4342 */             rows.put("uptime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("UpTime"))));
/*  4343 */             rows.put("dontdelete", "true");
/*       */           }
/*       */           else {
/*  4346 */             rows.put("uptime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("UpTime"))));
/*       */           }
/*  4348 */           rows.put("downtimeinmillis", Long.toString(set.getLong("DownTime")));
/*  4349 */           typeID = set.getInt("TYPE");
/*       */           
/*  4351 */           rows.put("ReasonID", Integer.valueOf(set.getInt("REASONID")));
/*  4352 */           rows.put("typeID", Integer.valueOf(set.getInt("TYPE")));
/*       */           
/*  4354 */           rows.put("interval", ReportUtilities.format(set.getLong("TotalDownTime")));
/*  4355 */           if (typeID == 1)
/*       */           {
/*  4357 */             totdowntime += set.getLong("TotalDownTime");
/*       */           }
/*  4359 */           else if (typeID == 2)
/*       */           {
/*  4361 */             unmanagedtime += set.getLong("TotalDownTime");
/*       */           }
/*       */           else
/*       */           {
/*  4365 */             scheduledtime += set.getLong("TotalDownTime");
/*       */           }
/*       */           
/*  4368 */           if (set.getString("SHORT_DESCRIPTION") != null) {
/*  4369 */             rows.put("Downtime_Reason", set.getString("SHORT_DESCRIPTION"));
/*       */           }
/*       */           else {
/*  4372 */             if (typeID == 2) {
/*  4373 */               rows.put("Downtime_Reason", "Monitor is Unmanaged");
/*       */             }
/*  4375 */             if (typeID == 3) {
/*  4376 */               rows.put("Downtime_Reason", "Scheduled Downtime");
/*       */             }
/*       */           }
/*       */           
/*  4380 */           if (totdowntime != 0L) {
/*  4381 */             rows.put("TotDownTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(totdowntime)));
/*       */           }
/*  4383 */           if (unmanagedtime != 0L) {
/*  4384 */             rows.put("UnManagedTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(unmanagedtime)));
/*       */           }
/*  4386 */           if (scheduledtime != 0L) {
/*  4387 */             rows.put("ScheduledTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(scheduledtime)));
/*       */           }
/*  4389 */           downtimesummary.add(rows);
/*       */         }
/*       */       }
/*       */       
/*  4393 */       request.setAttribute("downtimesummary", downtimesummary);
/*  4394 */       request.setAttribute("size", String.valueOf(downtimesummary.size()));
/*  4395 */       request.setAttribute("strTime", new java.util.Date(startTime));
/*  4396 */       request.setAttribute("endTime", new java.util.Date(endTime));
/*       */       
/*       */ 
/*       */ 
/*  4400 */       if ("pdf".equals(reportform.getReporttype())) {
/*  4401 */         request.setAttribute("report-type-template", "report.mttravail");
/*  4402 */         request.setAttribute("sp-report-type", "pdf");
/*  4403 */         request.setAttribute("downtimereport", "true");
/*  4404 */         return mapping.findForward("availabilitydata.success.pdf");
/*       */       }
/*       */       
/*  4407 */       return mapping.findForward("availabilitydata.success");
/*       */     }
/*       */     catch (Exception exp) {
/*       */       String displayName;
/*  4411 */       log.fatal("Exception in getting down time history details ", exp);
/*  4412 */       exp.printStackTrace();
/*  4413 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  4414 */       saveMessages(request, messages);
/*  4415 */       return mapping.getInputForward();
/*       */     }
/*       */     finally
/*       */     {
/*  4419 */       closeResultSet(set);
/*       */     }
/*       */   }
/*       */   
/*  4423 */   public Properties calculateConfMonitorAvailabilityDetails(String resourceid) { long[] time = ReportUtilities.getTimeStamp("0");
/*  4424 */     long startTime = time[0];
/*  4425 */     long endTime = time[1];
/*  4426 */     long mintimeindb = ReportUtilities.getMinTimeInDB(resourceid);
/*  4427 */     if (mintimeindb > time[0]) {
/*  4428 */       startTime = mintimeindb;
/*  4429 */       endTime = time[1];
/*       */     }
/*  4431 */     else if (mintimeindb != 0L) {
/*  4432 */       startTime = time[0];
/*  4433 */       endTime = time[1];
/*       */     }
/*  4435 */     Properties availabilityData = calculateAvailabilityDetails(resourceid, startTime, endTime);
/*  4436 */     String lastDowntimeQuery = getTopNValues("select DOWNTIME from AM_MO_DowntimeData where RESID=" + resourceid + " order by DOWNTIME desc", "1");
/*  4437 */     ResultSet set = null;
/*  4438 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */     try {
/*  4440 */       set = AMConnectionPool.executeQueryStmt(lastDowntimeQuery);
/*  4441 */       if (set.next()) {
/*  4442 */         availabilityData.setProperty("lastDownTime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("DOWNTIME"))).toString());
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/*  4446 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/*  4450 */       closeResultSet(set);
/*       */     }
/*  4452 */     return availabilityData;
/*       */   }
/*       */   
/*       */   private final Properties calculateAvailabilityDetails(String resourceid, long startTime, long endTime)
/*       */   {
/*  4457 */     return ReportUtilities.calculateAvailabilityDetails(resourceid, startTime, endTime);
/*       */   }
/*       */   
/*       */   public ActionForward generateDashboardReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  4463 */     AMLog.debug("******* Generate Dashboard Report Method ************");
/*  4464 */     String host = "localhost";
/*  4465 */     String port = System.getProperty("webserver.port");
/*  4466 */     String protocol = "http";
/*  4467 */     int responsecode = 200;
/*  4468 */     String repname = null;
/*  4469 */     String absolutepath = null;
/*  4470 */     String pdfname = null;
/*  4471 */     StringBuffer resPonse = new StringBuffer();
/*  4472 */     ResultSet rs = null;
/*       */     
/*  4474 */     boolean isHTTPS = false;
/*  4475 */     if ((port.equals("0")) || (AMAutomaticPortChanger.useSecuredConForScheduledReports)) {
/*  4476 */       isHTTPS = true;
/*  4477 */       protocol = "https";
/*  4478 */       port = System.getProperty("ssl.port");
/*       */     }
/*       */     
/*  4481 */     String headerval = Constants.RY;
/*  4482 */     String pwd = Constants.RPWD;
/*  4483 */     String pageid = request.getParameter("pageid");
/*       */     
/*  4485 */     if (pageid != null) {
/*  4486 */       if (pageid.indexOf("?") != -1) {
/*  4487 */         pageid = pageid.substring(0, pageid.indexOf("?"));
/*       */       }
/*  4489 */       Map<String, String> processData = new HashMap();
/*  4490 */       String query = "select PAGENAME,widg.WIDGETID,widg.DISPLAYNAME,widg.WIDGETTYPE from AM_MYPAGE_WIDGETS widg,AM_MYPAGES page,AM_MYPAGE_WIDGET_MAPPING map where widg.WIDGETID=map.WIDGETID and map.PAGEID=page.PAGEID and map.PAGEID=" + pageid + " and widg.DISPLAYNAME <> 'Help Card'";
/*       */       try {
/*  4492 */         AMLog.debug("generateDashboardReport===>query :" + query);
/*  4493 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  4494 */         while (rs.next()) {
/*  4495 */           repname = rs.getString("PAGENAME");
/*  4496 */           int widgettype = Integer.parseInt(rs.getString("WIDGETTYPE"));
/*  4497 */           if ((widgettype != 21) && (widgettype != 204) && (widgettype != 301))
/*       */           {
/*       */ 
/*  4500 */             processData.put(rs.getString("WIDGETID"), rs.getString("DISPLAYNAME")); }
/*       */         }
/*       */       } catch (Exception se) {
/*  4503 */         se.printStackTrace();
/*       */       }
/*       */       finally {
/*  4506 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */       
/*  4509 */       if (!processData.isEmpty()) {
/*  4510 */         Iterator it = processData.entrySet().iterator();
/*  4511 */         while (it.hasNext()) {
/*  4512 */           Map.Entry pairs = (Map.Entry)it.next();
/*  4513 */           String widgetid = (String)pairs.getKey();
/*  4514 */           String dspName = (String)pairs.getValue();
/*       */           
/*       */ 
/*  4517 */           resPonse.append("<div id='' class='tabdiv gridItem' style='display:block'><table class='lrtbdarkborder-dashboards' style='background-color:white;' border='0' cellpadding='0' cellspacing='0' width='100%'>");
/*  4518 */           resPonse.append("<tbody><tr height='26'><td class='tableHeader1' style='cursor: move; width: 100%;' align='left' height='26' width='80%'>");
/*  4519 */           resPonse.append("<div style='float:left; width:17px; position:reltive; margin-top:2px;'><img src='/images/icon_dashboard_drag.gif' />");
/*  4520 */           resPonse.append("</div><div id='widgetname#1'>").append(dspName).append("</div></td></tr><tr><td colspan='2' align='left' valign='top'>");
/*       */           
/*  4522 */           String type1 = "/MyPage.do?method=getWidget&widgetid=" + widgetid + "&pageid=" + pageid + "&";
/*  4523 */           String reporturl = protocol + "://" + host + ":" + port + type1;
/*  4524 */           if (AMAutomaticPortChanger.isSsoEnabled()) {
/*  4525 */             String casclienthostport = System.getProperty("CASClientUrl", protocol + "://" + host + ":" + port);
/*  4526 */             reporturl = casclienthostport + type1;
/*       */           }
/*  4528 */           AMLog.debug("Schedule Reports  URL===> for dashboards : " + reporturl);
/*  4529 */           URI uri = new URI(getHostPort(reporturl));
/*  4530 */           HTTPConnection con = new HTTPConnection(uri);
/*  4531 */           con.setContext("schedulereports" + String.valueOf(hashCode()));
/*  4532 */           String tempuri = getURI(reporturl);
/*       */           
/*  4534 */           con.setDefaultHeaders(new NVPair[] { new NVPair("Referer", tempuri) });
/*  4535 */           int available = 1;
/*  4536 */           NVPair nvpair2 = new NVPair("j_username", "reportadmin");
/*  4537 */           NVPair nvpair3 = new NVPair("j_password", pwd);
/*  4538 */           NVPair[] formelements = new NVPair[2];
/*  4539 */           formelements[0] = nvpair2;
/*  4540 */           formelements[1] = nvpair3;
/*  4541 */           NVPair nvpair = new NVPair("Pragma", "no-cache");
/*  4542 */           NVPair nvpair4 = new NVPair("reportagent", headerval);
/*  4543 */           NVPair[] headers = new NVPair[2];
/*  4544 */           headers[0] = nvpair;
/*  4545 */           headers[1] = nvpair4;
/*  4546 */           HTTPResponse rsp1 = null;
/*  4547 */           if ((AMAutomaticPortChanger.isSsoEnabled()) && ("true".equalsIgnoreCase(System.getProperty("adminConnected")))) {
/*  4548 */             pwd = Constants.rpwdADMIN;
/*  4549 */             if (EnterpriseUtil.isAdminServer()) {
/*  4550 */               pwd = Constants.RPWD;
/*       */             }
/*  4552 */             SSORestClient ssorestclient = new SSORestClient("reportadmin", pwd);
/*  4553 */             String sticket = ssorestclient.generateServiceTicket(reporturl);
/*  4554 */             tempuri = tempuri + "&ticket=" + sticket;
/*  4555 */             rsp1 = con.Get(tempuri.trim(), "", headers);
/*       */           }
/*       */           else {
/*  4558 */             rsp1 = con.Get(tempuri.trim(), formelements, headers);
/*       */           }
/*       */           
/*  4561 */           byte[] wbresponse = rsp1.getData();
/*  4562 */           byte[] temp = new byte[wbresponse.length];
/*  4563 */           System.arraycopy(wbresponse, 0, temp, 0, wbresponse.length);
/*  4564 */           String line = new String(temp);
/*  4565 */           responsecode = rsp1.getStatusCode();
/*       */           
/*  4567 */           if (line.indexOf("j_username") != -1)
/*       */           {
/*  4569 */             String urlforreport = protocol + "://" + host + ":" + port + "/j_security_check";
/*  4570 */             URI uri1 = new URI(getHostPort(urlforreport));
/*  4571 */             HTTPConnection con1 = new HTTPConnection(uri1);
/*  4572 */             con1.setContext("schedulereports" + String.valueOf(hashCode()));
/*  4573 */             String tempuri1 = getURI(urlforreport);
/*  4574 */             HTTPResponse rsp2 = con1.Post(getURI(urlforreport), formelements, headers);
/*  4575 */             byte[] wbresponse1 = rsp2.getData();
/*  4576 */             byte[] temp1 = new byte[wbresponse1.length];
/*  4577 */             System.arraycopy(wbresponse1, 0, temp1, 0, wbresponse1.length);
/*  4578 */             String line1 = new String(temp1);
/*  4579 */             resPonse.append(line1);
/*       */           }
/*       */           else {
/*  4582 */             resPonse.append(line);
/*       */           }
/*  4584 */           resPonse.append("</td></tr></tbody></table></table></div><br>");
/*       */         }
/*       */       }
/*       */       try
/*       */       {
/*  4589 */         String opfile = repname + "_Report";
/*  4590 */         String Root_Dir = System.getProperty("webnms.rootdir");
/*  4591 */         absolutepath = Root_Dir + File.separator + "Widgets";
/*  4592 */         boolean success; if (new File(absolutepath).exists()) {
/*  4593 */           AMLog.debug("*******the dir exists in mentioned path **********" + absolutepath);
/*       */         }
/*       */         else {
/*  4596 */           success = new File(absolutepath).mkdir();
/*       */         }
/*  4598 */         absolutepath = absolutepath + File.separator + opfile + ".html";
/*  4599 */         File fileName = new File(absolutepath);
/*       */         
/*  4601 */         if (!fileName.exists()) {
/*  4602 */           fileName.createNewFile();
/*       */         }
/*       */         
/*  4605 */         String scheme = request.getScheme();
/*  4606 */         String serverName = request.getServerName();
/*  4607 */         int serverPort = request.getServerPort();
/*  4608 */         StringBuffer baseurl = new StringBuffer();
/*  4609 */         baseurl.append(scheme).append("://").append(serverName);
/*       */         
/*  4611 */         if ((serverPort != 80) && (serverPort != 443)) {
/*  4612 */           baseurl.append(":").append(serverPort);
/*       */         }
/*  4614 */         String replceImages = baseurl.toString();
/*  4615 */         String widgetWebURL = baseurl + "/Widgets/" + URLEncoder.encode(opfile, "UTF-8").replace("+", "%20") + ".html?isInternalRequest=true";
/*  4616 */         FileWriter fw = new FileWriter(fileName.getAbsoluteFile());
/*  4617 */         BufferedWriter bw = new BufferedWriter(fw);
/*  4618 */         String locale = System.getProperty("locale");
/*  4619 */         Locale currentLocale = new Locale("en", "US");
/*       */         try {
/*  4621 */           if ((locale != null) && (locale.indexOf("_") != -1))
/*       */           {
/*  4623 */             String[] lan_Loc = locale.trim().split("_");
/*  4624 */             currentLocale = new Locale(lan_Loc[0], lan_Loc[1]);
/*       */           }
/*       */         }
/*       */         catch (Exception ee)
/*       */         {
/*  4629 */           ee.printStackTrace();
/*       */         }
/*  4631 */         SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aaa", currentLocale);
/*  4632 */         java.util.Date time = new java.util.Date(System.currentTimeMillis());
/*       */         
/*  4634 */         if (processData.isEmpty())
/*       */         {
/*  4636 */           resPonse.append("<div id='' class='tabdiv gridItem' style='display:block'><table class='lrtbdarkborder-dashboards' style='background-color:white;' border='0' cellpadding='0' cellspacing='0' width='100%'>");
/*  4637 */           resPonse.append("<tbody><tr height='26'><td  style='cursor: move; width: 100%;' align='center' height='26' width='80%'>");
/*  4638 */           resPonse.append(FormatUtil.getString("am.webclient.schedulereport.newschedule.dashboards.text") + "</td></tr></tbody></table></div>");
/*       */           
/*  4640 */           String style = "<link rel=\"stylesheet\" type=\"text/css\" href=\"../images/commonstyle.css\"/><link rel=\"stylesheet\" type=\"text/css\" href=\"../images/Blue/style.css\"/>";
/*       */           
/*  4642 */           String reportHeader = "<table width=\"100%\"><tr><td align=\"left\"><img src=\"../images/am_logo.gif\"/></td></tr><tr><td align=\"center\" style=\"font-size:20px;font-weight:bold;\">" + repname + " Report</td></tr><tr><td align=\"right\" style=\"font-size:15px;\">" + FormatUtil.getString("am.webclient.pdfreport.greatertime.text", new String[] { sdf.format(time).toString() }) + "</td></tr></table>";
/*  4643 */           StringBuffer header = new StringBuffer().append("<!DOCTYPE html><html><head>").append(style).append("</head><body>");
/*  4644 */           header.append(reportHeader).append(resPonse);
/*  4645 */           header.append("</body></html>");
/*  4646 */           bw.write(header.toString());
/*  4647 */           bw.close();
/*       */         }
/*  4649 */         else if (resPonse != null) {
/*  4650 */           String content = resPonse.toString();
/*       */           
/*  4652 */           String repeatedDomain = replceImages + replceImages;
/*  4653 */           if (content.indexOf("/images/") != -1)
/*       */           {
/*  4655 */             if (content.indexOf(replceImages) != -1)
/*       */             {
/*  4657 */               content = content.replaceAll("/images", replceImages + "/images");
/*  4658 */               if (content.indexOf(repeatedDomain) != -1)
/*       */               {
/*  4660 */                 content = content.replaceAll(repeatedDomain, replceImages);
/*       */               }
/*       */             }
/*       */             else
/*       */             {
/*  4665 */               content = content.replaceAll("/images", replceImages + "/images");
/*       */             }
/*       */           }
/*  4668 */           if (content.indexOf("temp/") != -1)
/*       */           {
/*  4670 */             if (content.indexOf(replceImages) != -1)
/*       */             {
/*  4672 */               content = content.replaceAll("/webclient/temp", replceImages + "/webclient/temp");
/*  4673 */               if (content.indexOf(repeatedDomain) != -1)
/*       */               {
/*  4675 */                 content = content.replaceAll(repeatedDomain, replceImages);
/*       */               }
/*       */             }
/*       */             else
/*       */             {
/*  4680 */               content = content.replaceAll("/webclient/temp", replceImages + "/webclient/temp");
/*       */             }
/*       */           }
/*  4683 */           if (content.indexOf("<?xml:namespace prefix = bean />") != -1)
/*       */           {
/*  4685 */             content = content.replaceAll("<\\?xml:namespace prefix = bean />", "");
/*       */           }
/*  4687 */           if (content.indexOf("&amp;nbsp") != -1)
/*       */           {
/*  4689 */             content = content.replaceAll("&amp;nbsp;", " ");
/*       */           }
/*  4691 */           if (content.indexOf("raquo;") != -1)
/*       */           {
/*  4693 */             content = content.replaceAll("&raquo;", "");
/*       */           }
/*  4695 */           if (content.indexOf("nbsp") != -1)
/*       */           {
/*  4697 */             content = content.replaceAll("&nbsp;", " ");
/*       */           }
/*  4699 */           if (content.indexOf("nbsp") != -1)
/*       */           {
/*  4701 */             content = content.replaceAll("&nbsp", " ");
/*       */           }
/*  4703 */           if (content.indexOf("&") != -1)
/*       */           {
/*  4705 */             content = content.replaceAll("&", "&amp;");
/*       */           }
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*  4711 */           String style = "<link rel=\"stylesheet\" type=\"text/css\" href=\"../images/commonstyle.css\"/><link rel=\"stylesheet\" type=\"text/css\" href=\"../images/Blue/style.css\"/>";
/*       */           
/*  4713 */           String reportHeader = "<table width=\"100%\"><tr><td align=\"left\"><img src=\"../images/am_logo.gif\"/></td></tr><tr><td align=\"center\" style=\"font-size:20px;font-weight:bold;\">" + repname + " Report</td></tr><tr><td align=\"right\" style=\"font-size:15px;\">" + FormatUtil.getString("am.webclient.pdfreport.greatertime.text", new String[] { sdf.format(time).toString() }) + "</td></tr></table>";
/*  4714 */           StringBuffer header = new StringBuffer().append("<!DOCTYPE html><html><head>").append(style).append("</head><body>");
/*  4715 */           header.append(reportHeader).append(content);
/*  4716 */           header.append("</body></html>");
/*       */           try
/*       */           {
/*  4719 */             content = header.toString();
/*  4720 */             Document doc = Jsoup.parse(content);
/*  4721 */             doc.getElementsByTag("script").remove();
/*  4722 */             doc.getElementsByTag("map").remove();
/*  4723 */             doc.getElementsByTag("meta").remove();
/*  4724 */             doc.getElementsByTag("style").remove();
/*  4725 */             doc.getElementsByTag("select").remove();
/*  4726 */             doc.getElementsByTag("option").remove();
/*  4727 */             doc.getElementsByTag("bean:define").remove();
/*  4728 */             doc.getElementsByAttribute("onmouseover").removeAttr("onmouseover");
/*  4729 */             doc.getElementsByAttribute("onmouseout").removeAttr("onmouseout");
/*  4730 */             doc.select("a").unwrap();
/*  4731 */             doc.getElementsByAttribute("onclick").removeAttr("onclick");
/*  4732 */             content = doc.toString();
/*       */             
/*       */ 
/*  4735 */             String apmLang = AMAutomaticPortChanger.props.getProperty("am.server.language");
/*  4736 */             if (("fr".equalsIgnoreCase(apmLang)) || ("es".equalsIgnoreCase(apmLang))) {
/*  4737 */               content = replaceNonEnglishCharsInContent(content, apmLang);
/*       */             }
/*       */           } catch (Exception ee) {
/*  4740 */             ee.printStackTrace();
/*       */           }
/*  4742 */           bw.write(content);
/*  4743 */           bw.close();
/*       */         }
/*  4745 */         String outputFile = AMFlyingSaucerUtil.createPDFForSavedHTMLFile(widgetWebURL, opfile);
/*  4746 */         response.setContentType("application/pdf");
/*  4747 */         SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
/*  4748 */         response.setCharacterEncoding("UTF-8");
/*  4749 */         String reportName = opfile + "_" + sdf1.format(time) + ".pdf";
/*  4750 */         response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + "\"");
/*  4751 */         ServletOutputStream os = null;
/*  4752 */         InputStream in = null;
/*       */         try {
/*  4754 */           os = response.getOutputStream();
/*  4755 */           in = new BufferedInputStream(new FileInputStream(new File(outputFile)));
/*       */           int ch;
/*  4757 */           while ((ch = in.read()) != -1) {
/*  4758 */             os.print((char)ch);
/*       */           }
/*       */         }
/*       */         catch (IOException e) {
/*  4762 */           e.printStackTrace();
/*       */         }
/*       */         finally {
/*       */           try {
/*  4766 */             if (in != null) {
/*  4767 */               in.close();
/*       */             }
/*  4769 */             if (os != null) {
/*  4770 */               os.flush();
/*  4771 */               os.close();
/*       */             }
/*       */           }
/*       */           catch (Exception e) {}
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4781 */         return null;
/*       */       }
/*       */       catch (IOException e)
/*       */       {
/*  4778 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   private String replaceNonEnglishCharsInContent(String content, String userLang)
/*       */   {
/*  4786 */     if ("fr".equalsIgnoreCase(userLang)) {
/*  4787 */       if (content.contains("&Agrave;"))
/*  4788 */         content = content.replaceAll("&Agrave;", "A");
/*  4789 */       if (content.contains("&agrave;"))
/*  4790 */         content = content.replaceAll("&agrave;", "a");
/*  4791 */       if (content.contains("&Atilde;"))
/*  4792 */         content = content.replaceAll("&Atilde;", "A");
/*  4793 */       if (content.contains("&atilde;"))
/*  4794 */         content = content.replaceAll("&atilde;", "a");
/*  4795 */       if (content.contains("&Egrave;"))
/*  4796 */         content = content.replaceAll("&Egrave;", "E");
/*  4797 */       if (content.contains("&egrave;"))
/*  4798 */         content = content.replaceAll("&egrave;", "e");
/*  4799 */       if (content.contains("&Eacute;"))
/*  4800 */         content = content.replaceAll("&Eacute;", "E");
/*  4801 */       if (content.contains("&eacute;"))
/*  4802 */         content = content.replaceAll("&eacute;", "e");
/*  4803 */       if (content.contains("&Ecirc;"))
/*  4804 */         content = content.replaceAll("&Ecirc;", "E");
/*  4805 */       if (content.contains("&ecirc;"))
/*  4806 */         content = content.replaceAll("&ecirc;", "e");
/*  4807 */       if (content.contains("&Euml;"))
/*  4808 */         content = content.replaceAll("&Euml;", "E");
/*  4809 */       if (content.contains("&euml;"))
/*  4810 */         content = content.replaceAll("&euml;", "e");
/*  4811 */       if (content.contains("&Icirc;"))
/*  4812 */         content = content.replaceAll("&Icirc;", "I");
/*  4813 */       if (content.contains("&icirc;"))
/*  4814 */         content = content.replaceAll("&icirc;", "i");
/*  4815 */       if (content.contains("&Iuml;"))
/*  4816 */         content = content.replaceAll("&Iuml;", "I");
/*  4817 */       if (content.contains("&iuml;"))
/*  4818 */         content = content.replaceAll("&iuml;", "i");
/*  4819 */       if (content.contains("&Ocirc;"))
/*  4820 */         content = content.replaceAll("&Ocirc;", "O");
/*  4821 */       if (content.contains("&ocirc;"))
/*  4822 */         content = content.replaceAll("&ocirc;", "o");
/*  4823 */       if (content.contains("&Ugrave;"))
/*  4824 */         content = content.replaceAll("&Ugrave;", "U");
/*  4825 */       if (content.contains("&ugrave;"))
/*  4826 */         content = content.replaceAll("&ugrave;", "u");
/*  4827 */       if (content.contains("&Ucirc;"))
/*  4828 */         content = content.replaceAll("&Ucirc;", "U");
/*  4829 */       if (content.contains("&ucirc;"))
/*  4830 */         content = content.replaceAll("&ucirc;", "u");
/*  4831 */       if (content.contains("&Uuml;"))
/*  4832 */         content = content.replaceAll("&Uuml;", "U");
/*  4833 */       if (content.contains("&uuml;"))
/*  4834 */         content = content.replaceAll("&uuml;", "u");
/*  4835 */       if (content.contains("&Yuml;"))
/*  4836 */         content = content.replaceAll("&Yuml;", "Y");
/*  4837 */       if (content.contains("&yuml;")) {
/*  4838 */         content = content.replaceAll("&yuml;", "y");
/*       */       }
/*  4840 */     } else if ("es".equalsIgnoreCase(userLang)) {
/*  4841 */       if (content.contains("&Aacute;"))
/*  4842 */         content = content.replaceAll("&Aacute;", "A");
/*  4843 */       if (content.contains("&Eacute;"))
/*  4844 */         content = content.replaceAll("&Eacute;", "E");
/*  4845 */       if (content.contains("&Iacute;"))
/*  4846 */         content = content.replaceAll("&Iacute;", "I");
/*  4847 */       if (content.contains("&Oacute;"))
/*  4848 */         content = content.replaceAll("&Oacute;", "O");
/*  4849 */       if (content.contains("&Ntilde;"))
/*  4850 */         content = content.replaceAll("&Ntilde;", "N");
/*  4851 */       if (content.contains("&Uacute;"))
/*  4852 */         content = content.replaceAll("&Uacute;", "U");
/*  4853 */       if (content.contains("&Uuml;"))
/*  4854 */         content = content.replaceAll("&Uuml;", "U");
/*  4855 */       if (content.contains("&aacute;"))
/*  4856 */         content = content.replaceAll("&aacute;", "a");
/*  4857 */       if (content.contains("&eacute;"))
/*  4858 */         content = content.replaceAll("&eacute;", "e");
/*  4859 */       if (content.contains("&iacute;"))
/*  4860 */         content = content.replaceAll("&iacute;", "i");
/*  4861 */       if (content.contains("&oacute;"))
/*  4862 */         content = content.replaceAll("&oacute;", "o");
/*  4863 */       if (content.contains("&ntilde;"))
/*  4864 */         content = content.replaceAll("&ntilde;", "n");
/*  4865 */       if (content.contains("&uacute;"))
/*  4866 */         content = content.replaceAll("&uacute;", "u");
/*  4867 */       if (content.contains("&uuml;"))
/*  4868 */         content = content.replaceAll("&uuml;", "u");
/*  4869 */       if (content.contains("&ordf;"))
/*  4870 */         content = content.replaceAll("&ordf;", "a");
/*  4871 */       if (content.contains("&ordm;")) {
/*  4872 */         content = content.replaceAll("&ordm;", "o");
/*       */       }
/*       */     }
/*  4875 */     return content;
/*       */   }
/*       */   
/*       */   public ActionForward generateAvailabilityReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*  4880 */     ActionMessages messages = new ActionMessages();
/*  4881 */     ResultSet set = null;
/*  4882 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  4883 */     ReportForm controls = (ReportForm)form;
/*  4884 */     String attribute = request.getParameter("attribute");
/*  4885 */     String resourceType = controls.getResourceType();
/*  4886 */     String period = controls.getPeriod();
/*  4887 */     request.setAttribute("HelpKey", getMonitorHelpKey(resourceType));
/*  4888 */     AMLog.debug("Reports : Generating availability Report for " + resourceType + " period " + period);
/*  4889 */     getHolisticApps(mapping, form, request, response);
/*  4890 */     getMonitors(mapping, form, request, response);
/*  4891 */     getCustomApplications(request, "3");
/*  4892 */     long[] timeStamps = null;
/*  4893 */     String nodata = "report.nodata.time";
/*  4894 */     String customfield = controls.getCustomfield();
/*  4895 */     String customValue = controls.getCustomFieldValue();
/*       */     
/*  4897 */     String scheduleid = request.getParameter("scheduleid");
/*  4898 */     int cust = -1;
/*  4899 */     int site = -1;
/*       */     
/*  4901 */     if ((EnterpriseUtil.isIt360MSPEdition()) && (scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */     {
/*  4903 */       ArrayList al = EnterpriseUtil.getCustomerSiteFromScheduleDetails(scheduleid);
/*  4904 */       if ((al != null) && (al.size() >= 2))
/*       */       {
/*  4906 */         cust = Integer.parseInt(al.get(0).toString());
/*  4907 */         site = Integer.parseInt(al.get(1).toString());
/*       */       }
/*       */     }
/*       */     
/*  4911 */     Vector resourceids = null;
/*       */     
/*  4913 */     if ((controls.getStartDate().equals("")) || (controls.getEndDate().equals(""))) {
/*  4914 */       timeStamps = ReportUtilities.getTimeStamp(period);
/*       */     }
/*       */     else {
/*  4917 */       controls.setPeriod("4");
/*       */       try {
/*  4919 */         timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */       } catch (IllegalArgumentException iae) {
/*  4921 */         String errMsg = iae.getMessage();
/*  4922 */         AMLog.debug("Reports :  " + errMsg);
/*  4923 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  4924 */         saveMessages(request, messages);
/*  4925 */         request.setAttribute("heading", "0");
/*  4926 */         request.setAttribute("strTime", "0");
/*  4927 */         return mapping.findForward("report.message");
/*       */       }
/*       */     }
/*       */     
/*  4931 */     List datacollectionStartedMOs = null;
/*  4932 */     if ((customfield != null) && (customfield.equals("true")) && (customValue != null) && (customValue.indexOf("$") != -1)) {
/*  4933 */       datacollectionStartedMOs = MyFields.customFieldResourceIDs(customValue, resourceType);
/*       */     } else {
/*  4935 */       datacollectionStartedMOs = getDataCollectionStartedMonitors(resourceType);
/*       */     }
/*       */     
/*  4938 */     long startTime = timeStamps[0];
/*  4939 */     long endTime = timeStamps[1];
/*  4940 */     String getresids = null;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  4945 */     if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  4946 */       String owner = request.getRemoteUser();
/*  4947 */       resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*  4948 */       if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */       {
/*  4950 */         resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */       }
/*       */       
/*  4953 */       if (Constants.isUserResourceEnabled()) {
/*  4954 */         String loginUserid = Constants.getLoginUserid(request);
/*  4955 */         getresids = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType,AM_USERRESOURCESTABLE where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "') and AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*       */       } else {
/*  4957 */         getresids = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "') and " + ReportUtilities.getCondition("RESOURCEID", resourceids);
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*       */ 
/*  4965 */       getresids = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "')";
/*  4966 */       if (EnterpriseUtil.isAdminServer())
/*       */       {
/*  4968 */         getresids = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "') and AM_ManagedResourceType.SUBGROUP <> 'OpManager-Interface' ";
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  4973 */     AMLog.debug("generateAvailabilityReport data collection started MO size " + datacollectionStartedMOs.size());
/*       */     
/*  4975 */     ArrayList resources = new ArrayList();
/*  4976 */     Properties residdisplaynamemapping = new Properties();
/*  4977 */     Properties residVsMinTime = new Properties();
/*  4978 */     Vector resIds = new Vector();
/*  4979 */     int limit = controls.getNumberOfRows();
/*       */     try
/*       */     {
/*  4982 */       set = AMConnectionPool.executeQueryStmt(getresids);
/*  4983 */       boolean hasMonitorInstance = false;
/*  4984 */       dataCollectionNotStarted = false;
/*  4985 */       while (set.next()) {
/*  4986 */         String resid = String.valueOf(set.getInt("RESOURCEID"));
/*  4987 */         if (!datacollectionStartedMOs.contains(resid)) {
/*  4988 */           dataCollectionNotStarted = true;
/*       */         }
/*       */         else {
/*  4991 */           resources.add(resid);
/*  4992 */           String dispName = EnterpriseUtil.decodeString(set.getString("DISPLAYNAME"));
/*  4993 */           residdisplaynamemapping.setProperty(resid, dispName);
/*  4994 */           resIds.add(resid);
/*       */           
/*       */ 
/*  4997 */           hasMonitorInstance = true;
/*       */         } }
/*  4999 */       closeResultSet(set);
/*       */       Hashtable h;
/*  5001 */       if (hasMonitorInstance) {
/*  5002 */         h = ReportUtilities.getMinTimeInDB(resIds);
/*  5003 */         Enumeration e = h.keys();
/*  5004 */         while (e.hasMoreElements()) {
/*  5005 */           String resourceId = (String)e.nextElement();
/*  5006 */           residVsMinTime.setProperty(resourceId, ((Long)h.get(resourceId)).toString());
/*       */         }
/*       */       }
/*       */       
/*  5010 */       AMLog.debug("Reports :  residdisplaynamemapping " + residdisplaynamemapping.toString());
/*  5011 */       AMLog.debug("Reports :  resources " + resources.toString());
/*  5012 */       if (!hasMonitorInstance) {
/*  5013 */         if (dataCollectionNotStarted) {
/*  5014 */           AMLog.debug("Reports : Data Collection not started for " + resourceType);
/*  5015 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodatacollection.monitors"));
/*  5016 */           saveMessages(request, messages);
/*  5017 */           request.setAttribute("heading", "0");
/*  5018 */           request.setAttribute("strTime", "0");
/*  5019 */           return mapping.findForward("report.message");
/*       */         }
/*  5021 */         AMLog.debug("Reports : No data for " + resourceType);
/*  5022 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.monitors"));
/*  5023 */         saveMessages(request, messages);
/*  5024 */         request.setAttribute("heading", "0");
/*  5025 */         request.setAttribute("strTime", "0");
/*  5026 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/*  5029 */       long stTime = startTime;
/*  5030 */       Enumeration enum1 = residVsMinTime.keys();
/*  5031 */       ArrayList data = new ArrayList();
/*       */       
/*  5033 */       long totalduration = 0L;
/*  5034 */       Properties durationProp = new Properties();
/*  5035 */       while (enum1.hasMoreElements()) {
/*  5036 */         startTime = stTime;
/*  5037 */         String resourceID = (String)enum1.nextElement();
/*  5038 */         String minTime = residVsMinTime.getProperty(resourceID);
/*  5039 */         long minTimeInDB = Long.parseLong(minTime);
/*  5040 */         if (minTimeInDB > startTime) {
/*  5041 */           startTime = minTimeInDB;
/*       */         }
/*       */         
/*  5044 */         if (startTime > endTime) {
/*  5045 */           resources.remove(resourceID);
/*       */         }
/*       */         
/*  5048 */         String query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,TYPE from AM_MO_DowntimeData where RESID=" + resourceID + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,TYPE";
/*  5049 */         if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/*  5050 */           query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,TYPE from AM_MO_DowntimeData where RESID=" + resourceID + " and TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,TYPE";
/*       */         }
/*  5052 */         AMLog.debug("REPORTS => AVAILABILITY DATA QUERY => " + query);
/*  5053 */         set = AMConnectionPool.executeQueryStmt(query);
/*       */         
/*       */ 
/*       */ 
/*  5057 */         long totaldowntime = 0L;
/*  5058 */         long totalUnmanagedtime = 0L;
/*  5059 */         long totalScheduledtime = 0L;
/*  5060 */         totalduration = endTime - startTime;
/*  5061 */         durationProp.setProperty(resourceID, String.valueOf(totalduration));
/*  5062 */         Properties rows = new Properties();
/*  5063 */         while (set.next())
/*       */         {
/*  5065 */           int typeID = 0;
/*  5066 */           int resourceid = set.getInt("RESID");
/*  5067 */           if (resources.contains(String.valueOf(resourceid))) {
/*  5068 */             resources.remove(String.valueOf(resourceid));
/*       */           }
/*  5070 */           int count = set.getInt("DownCount");
/*  5071 */           typeID = set.getInt("TYPE");
/*  5072 */           if (typeID == 1)
/*       */           {
/*  5074 */             totaldowntime = set.getLong("TotalDownTime");
/*       */           }
/*  5076 */           else if (typeID == 2)
/*       */           {
/*  5078 */             totalUnmanagedtime = set.getLong("TotalDownTime");
/*       */           }
/*       */           else
/*       */           {
/*  5082 */             totalScheduledtime = set.getLong("TotalDownTime");
/*       */           }
/*  5084 */           long uptime = totalduration - (totaldowntime + totalUnmanagedtime + totalScheduledtime);
/*  5085 */           System.out.println("============= -------- ============== -------> UPTIME :" + uptime);
/*  5086 */           if (uptime < 0L) {
/*  5087 */             AMLog.fatal("Reports :  Fatal Error****************************************************");
/*  5088 */             AMLog.fatal("Reports :  Error in the stored  availability information for " + resourceid);
/*  5089 */             AMLog.fatal("Reports :  totalduration " + totalduration);
/*  5090 */             AMLog.fatal("Reports :  totaldowntime " + totaldowntime);
/*  5091 */             AMLog.fatal("Reports :  execute :select * from AM_MO_DowntimeData where RESID " + resourceid);
/*  5092 */             AMLog.fatal("Reports :  Error : It has multiple rows with UPTIME=0");
/*  5093 */             AMLog.fatal("Reports :  ***************************************************************");
/*       */           }
/*       */           else {
/*  5096 */             String moname = residdisplaynamemapping.getProperty(String.valueOf(resourceid));
/*  5097 */             rows.setProperty("Name", moname);
/*  5098 */             rows.setProperty("resourceid", String.valueOf(resourceid));
/*  5099 */             rows.setProperty("totaldowntime", ReportUtilities.format(totaldowntime));
/*  5100 */             rows.put("DowntimeInLong", new Long(totaldowntime));
/*  5101 */             long mttr = totaldowntime / count;
/*  5102 */             long mtbf = uptime / count;
/*  5103 */             rows.setProperty("mttr", ReportUtilities.format(mttr));
/*  5104 */             rows.setProperty("mtbf", ReportUtilities.format(mtbf));
/*  5105 */             float upPercent = (float)uptime / (float)totalduration * 100.0F;
/*  5106 */             float downPercent = (float)totaldowntime / (float)totalduration * 100.0F;
/*  5107 */             rows.setProperty("available", String.valueOf(Math.round(upPercent * 1000.0F) / 1000.0F));
/*       */           }
/*       */         }
/*  5110 */         if (!rows.isEmpty()) {
/*  5111 */           data.add(rows);
/*       */         }
/*       */       }
/*  5114 */       int currentDataSize = data.size();
/*  5115 */       int size = resources.size();
/*  5116 */       if ((currentDataSize < limit) || (limit == -1)) {
/*  5117 */         for (int i = 0; i < size; i++) {
/*  5118 */           String resourceid = (String)resources.get(i);
/*  5119 */           String moname = residdisplaynamemapping.getProperty(resourceid);
/*  5120 */           Properties rows1 = new Properties();
/*  5121 */           rows1.setProperty("Name", moname);
/*  5122 */           rows1.setProperty("resourceid", resourceid);
/*  5123 */           rows1.setProperty("totaldowntime", ReportUtilities.format(0L));
/*  5124 */           rows1.put("DowntimeInLong", new Long(0L));
/*  5125 */           rows1.setProperty("mttr", ReportUtilities.format(0L));
/*  5126 */           totalduration = Long.parseLong(durationProp.getProperty(resourceid));
/*  5127 */           rows1.setProperty("mtbf", ReportUtilities.format(totalduration));
/*  5128 */           rows1.setProperty("available", "100");
/*  5129 */           data.add(rows1);
/*       */         }
/*       */       }
/*       */       
/*  5133 */       Properties[] propArray = (Properties[])data.toArray(new Properties[data.size()]);
/*  5134 */       Arrays.sort(propArray, new ComparatorImpl());
/*  5135 */       data.clear();
/*  5136 */       int propArraySize = propArray.length;
/*  5137 */       for (int i = 0; i < propArraySize; i++) {
/*  5138 */         data.add(propArray[i]);
/*       */       }
/*       */       
/*  5141 */       if (data.size() == 0)
/*       */       {
/*  5143 */         AMLog.debug("Reports : No data for " + resourceType);
/*       */         
/*  5145 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(nodata));
/*  5146 */         saveMessages(request, messages);
/*  5147 */         request.setAttribute("heading", "0");
/*  5148 */         request.setAttribute("strTime", "0");
/*  5149 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/*  5152 */       int datasize = data.size();
/*  5153 */       if ((limit != -1) && (datasize > limit))
/*       */       {
/*  5155 */         for (int i = datasize - 1; i > limit - 1; i--) {
/*  5156 */           data.remove(i);
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  5162 */       request.setAttribute("strTime", new java.util.Date(stTime));
/*  5163 */       request.setAttribute("endTime", new java.util.Date(endTime));
/*  5164 */       request.setAttribute("data", data);
/*  5165 */       if (controls.getReporttype().equals("pdf"))
/*       */       {
/*  5167 */         request.setAttribute("report-type-template", "report.availability");
/*  5168 */         return mapping.findForward("report.availability.pdf");
/*       */       }
/*  5170 */       if (controls.getReporttype().equals("csv")) {
/*  5171 */         return mapping.findForward("report.availability.csv");
/*       */       }
/*       */       
/*  5174 */       return mapping.findForward("report.availability");
/*       */     }
/*       */     catch (Exception exp) {
/*       */       boolean dataCollectionNotStarted;
/*  5178 */       exp.printStackTrace();
/*  5179 */       AMLog.fatal("Reports :  Exception ", exp);
/*  5180 */       request.setAttribute("heading", "0");
/*  5181 */       request.setAttribute("strTime", "0");
/*  5182 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  5183 */       saveMessages(request, messages);
/*  5184 */       return mapping.findForward("report.message");
/*       */     }
/*       */     finally {
/*  5187 */       closeResultSet(set);
/*       */     }
/*       */   }
/*       */   
/*       */   public ActionForward generateEventSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*  5193 */     ActionMessages messages = new ActionMessages();
/*  5194 */     ResultSet set = null;
/*       */     try
/*       */     {
/*  5197 */       ReportForm controls = (ReportForm)form;
/*  5198 */       String haid = controls.getHaid();
/*  5199 */       String period = controls.getPeriod();
/*       */       
/*  5201 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*  5202 */       AMLog.debug("Reports : Generating Fault Report for " + haid + " period " + period);
/*  5203 */       getHolisticApps(mapping, form, request, response);
/*  5204 */       getMonitors(mapping, form, request, response);
/*  5205 */       getCustomApplications(request, "3");
/*  5206 */       long[] timeStamps = null;
/*  5207 */       boolean isCustomTime = false;
/*  5208 */       if ((controls.getStartDate().equals("")) || (controls.getEndDate().equals("")))
/*       */       {
/*  5210 */         timeStamps = ReportUtilities.getTimeStamp(period);
/*       */       }
/*       */       else {
/*  5213 */         controls.setPeriod("4");
/*  5214 */         isCustomTime = true;
/*       */         try
/*       */         {
/*  5217 */           timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */         } catch (IllegalArgumentException iae) {
/*  5219 */           String errMsg = iae.getMessage();
/*  5220 */           AMLog.debug("Reports :  " + errMsg);
/*  5221 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  5222 */           saveMessages(request, messages);
/*  5223 */           request.setAttribute("heading", "0");
/*  5224 */           request.setAttribute("strTime", "0");
/*  5225 */           return mapping.findForward("report.message");
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  5230 */       long sttime = timeStamps[0];
/*  5231 */       if (!isCustomTime)
/*       */       {
/*  5233 */         sttime += 600000L;
/*       */       }
/*       */       
/*  5236 */       long endtime = timeStamps[1];
/*  5237 */       String query = "select CATEGORY,RESID,AM_ATTRIBUTES.DISPLAYNAME as attname,AM_ManagedObject.DISPLAYNAME as moname,TEXT,SEVERITY,sum(OCCURANCES) as OCCURANCES,AM_ATTRIBUTES.ATTRIBUTEID attributeid from AM_EventHistoryData,AM_ManagedObject,AM_ATTRIBUTES,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=RESID and AM_ATTRIBUTES.ATTRIBUTEID=CATEGORY  and AM_PARENTCHILDMAPPER.CHILDID=RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and  ARCHIVEDTIME >=" + sttime + "  and ARCHIVEDTIME <=" + endtime + " group by CATEGORY,SEVERITY,RESID,AM_ATTRIBUTES.DISPLAYNAME,AM_ManagedObject.DISPLAYNAME,TEXT,AM_ATTRIBUTES.ATTRIBUTEID order by AM_ManagedObject.DISPLAYNAME,CATEGORY,SEVERITY";
/*       */       
/*  5239 */       AMLog.debug("Event Reports Query ======>:  " + query);
/*       */       
/*  5241 */       ArrayList data = getDataFromEventTable(query);
/*  5242 */       if ((((String)data.get(0)).equals("0")) && (((String)data.get(1)).equals("0")) && (((String)data.get(2)).equals("0")))
/*       */       {
/*  5244 */         AMLog.debug("Reports : No data in Events Table ");
/*  5245 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.alerts"));
/*  5246 */         saveMessages(request, messages);
/*  5247 */         request.setAttribute("heading", "0");
/*  5248 */         request.setAttribute("strTime", "0");
/*  5249 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/*  5252 */       request.setAttribute("ClearEvents", (String)data.remove(0));
/*  5253 */       request.setAttribute("CriticalEvents", (String)data.remove(0));
/*  5254 */       request.setAttribute("WarningEvents", (String)data.remove(0));
/*  5255 */       request.setAttribute("strTime", new java.util.Date(timeStamps[0]));
/*  5256 */       request.setAttribute("endTime", new java.util.Date(timeStamps[1]));
/*  5257 */       request.setAttribute("data", data);
/*  5258 */       query = "select RESID,SEVERITY,AM_ManagedObject.DISPLAYNAME,sum(OCCURANCES) as OCCURANCES from AM_EventHistoryData,AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=RESID and AM_PARENTCHILDMAPPER.CHILDID=RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and  ARCHIVEDTIME >=" + timeStamps[0] + "  and ARCHIVEDTIME <=" + timeStamps[1] + " and SEVERITY <> 5 group by AM_ManagedObject.DISPLAYNAME,RESID,SEVERITY";
/*       */       
/*  5260 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  5261 */       ArrayList toReturn = new ArrayList();
/*       */       try
/*       */       {
/*  5264 */         set = AMConnectionPool.executeQueryStmt(query);
/*  5265 */         AMLog.debug("Reports : " + query);
/*  5266 */         int previousid = -1;
/*  5267 */         int critical = 0;
/*  5268 */         int warning = 0;
/*  5269 */         String moname = "";
/*  5270 */         boolean hasData = false;
/*  5271 */         while (set.next())
/*       */         {
/*  5273 */           hasData = true;
/*  5274 */           int resid = set.getInt("RESID");
/*  5275 */           if ((resid != previousid) && (previousid != -1))
/*       */           {
/*  5277 */             Properties dataProps = new Properties();
/*  5278 */             dataProps.setProperty("resourceid", String.valueOf(previousid));
/*  5279 */             dataProps.setProperty("moname", moname);
/*  5280 */             dataProps.setProperty("critical", String.valueOf(critical));
/*  5281 */             dataProps.setProperty("warning", String.valueOf(warning));
/*  5282 */             toReturn.add(dataProps);
/*       */             
/*  5284 */             critical = 0;
/*  5285 */             warning = 0;
/*  5286 */             moname = "";
/*       */           }
/*  5288 */           int severity = set.getInt("SEVERITY");
/*  5289 */           moname = EnterpriseUtil.decodeString(set.getString("DISPLAYNAME"));
/*  5290 */           if (severity == 1)
/*       */           {
/*  5292 */             critical = set.getInt("OCCURANCES");
/*       */           }
/*  5294 */           else if (severity == 4)
/*       */           {
/*  5296 */             warning = set.getInt("OCCURANCES");
/*       */           }
/*  5298 */           previousid = resid;
/*       */         }
/*  5300 */         if (hasData)
/*       */         {
/*  5302 */           Properties dataProps = new Properties();
/*  5303 */           dataProps.setProperty("moname", moname);
/*  5304 */           dataProps.setProperty("resourceid", String.valueOf(previousid));
/*  5305 */           dataProps.setProperty("critical", String.valueOf(critical));
/*  5306 */           dataProps.setProperty("warning", String.valueOf(warning));
/*  5307 */           toReturn.add(dataProps);
/*       */         }
/*  5309 */         request.setAttribute("data1", toReturn);
/*       */       } catch (Exception exp) {
/*  5311 */         AMLog.fatal("Reports :  Exception ", exp);
/*  5312 */         exp.printStackTrace();
/*       */       }
/*       */       finally {
/*  5315 */         closeResultSet(set);
/*       */       }
/*  5317 */       request.setAttribute("size", String.valueOf(toReturn.size()));
/*       */       
/*       */ 
/*       */ 
/*  5321 */       if (controls.getReporttype().equals("csv")) {
/*  5322 */         return mapping.findForward("report.eventreport.csv");
/*       */       }
/*  5324 */       if (controls.getReporttype().equals("pdf")) {
/*  5325 */         request.setAttribute("report-type-template", "report.eventreport");
/*  5326 */         request.setAttribute("sp-report-type", "pdf");
/*  5327 */         return mapping.findForward("report.eventreport.pdf");
/*       */       }
/*       */       
/*  5330 */       if ("true".equals(request.getParameter("PRINTER_FRIENDLY"))) {
/*  5331 */         request.setAttribute("heading", FormatUtil.getString("am.reporttab.eventreport.heading.text"));
/*       */       }
/*  5333 */       return mapping.findForward("report.eventreport");
/*       */     }
/*       */     catch (Exception exp) {
/*  5336 */       exp.printStackTrace();
/*  5337 */       AMLog.fatal("Reports :  Exception in generating Alert Report ", exp);
/*  5338 */       request.setAttribute("heading", "0");
/*  5339 */       request.setAttribute("strTime", "0");
/*  5340 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  5341 */       saveMessages(request, messages);
/*       */     }
/*       */     
/*  5344 */     return mapping.findForward("report.message");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public final ArrayList getDataFromEventTable(String query)
/*       */   {
/*  5352 */     ArrayList toReturn = new ArrayList();
/*  5353 */     ResultSet set = null;
/*  5354 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  5355 */     int totalclear = 0;
/*  5356 */     int totalcritical = 0;
/*  5357 */     int totalwarning = 0;
/*       */     try {
/*  5359 */       set = AMConnectionPool.executeQueryStmt(query);
/*  5360 */       AMLog.debug("Reports : " + query);
/*  5361 */       String previouscategory = "-1";
/*  5362 */       int clear = 0;
/*  5363 */       int critical = 0;
/*  5364 */       int warning = 0;
/*  5365 */       String monitorname = "";
/*  5366 */       String attributename = "";
/*  5367 */       int attributeID = 0;
/*  5368 */       String text = "";
/*  5369 */       boolean hasData = false;
/*  5370 */       while (set.next()) {
/*  5371 */         hasData = true;
/*  5372 */         int category = set.getInt("CATEGORY");
/*  5373 */         int resid = set.getInt("RESID");
/*  5374 */         String currentData = category + ":" + resid;
/*  5375 */         if ((!currentData.equals(previouscategory)) && (!previouscategory.equals("-1"))) {
/*  5376 */           Properties dataProps = new Properties();
/*  5377 */           dataProps.setProperty("resourceid", previouscategory.substring(previouscategory.indexOf(":") + 1, previouscategory.length()));
/*  5378 */           dataProps.setProperty("attributename", attributename);
/*  5379 */           dataProps.setProperty("moname", monitorname);
/*  5380 */           dataProps.setProperty("text", text);
/*  5381 */           dataProps.setProperty("critical", String.valueOf(critical));
/*  5382 */           dataProps.setProperty("clear", String.valueOf(clear));
/*  5383 */           dataProps.setProperty("warning", String.valueOf(warning));
/*  5384 */           dataProps.setProperty("category", previouscategory.substring(0, previouscategory.indexOf(":")));
/*       */           
/*  5386 */           String alertQuery = "select ENTITY from Alert where ENTITY='" + dataProps.getProperty("resourceid") + "_" + attributeID + "'";
/*       */           
/*  5388 */           ResultSet s = AMConnectionPool.executeQueryStmt(alertQuery);
/*  5389 */           dataProps.setProperty("enablelink", "" + s.first());
/*  5390 */           AMConnectionPool.closeStatement(s);
/*  5391 */           toReturn.add(dataProps);
/*       */           
/*  5393 */           clear = 0;
/*  5394 */           critical = 0;
/*  5395 */           warning = 0;
/*  5396 */           monitorname = "";
/*  5397 */           attributename = "";
/*  5398 */           attributeID = 0;
/*  5399 */           text = "";
/*       */         }
/*  5401 */         int severity = set.getInt("SEVERITY");
/*  5402 */         monitorname = EnterpriseUtil.decodeString(set.getString("moname"));
/*  5403 */         attributename = set.getString("attname");
/*  5404 */         attributeID = set.getInt("attributeid");
/*  5405 */         text = set.getString("TEXT");
/*  5406 */         if (severity == 1) {
/*  5407 */           critical += set.getInt("OCCURANCES");
/*  5408 */           totalcritical += set.getInt("OCCURANCES");
/*       */         }
/*  5410 */         else if (severity == 4) {
/*  5411 */           warning += set.getInt("OCCURANCES");
/*  5412 */           totalwarning += set.getInt("OCCURANCES");
/*       */         }
/*  5414 */         else if (severity == 5) {
/*  5415 */           clear += set.getInt("OCCURANCES");
/*  5416 */           totalclear += set.getInt("OCCURANCES");
/*       */         }
/*  5418 */         previouscategory = currentData;
/*       */       }
/*  5420 */       if (hasData) {
/*  5421 */         Properties dataProps = new Properties();
/*  5422 */         dataProps.setProperty("attributename", attributename);
/*  5423 */         dataProps.setProperty("resourceid", previouscategory.substring(previouscategory.indexOf(":") + 1, previouscategory.length()));
/*  5424 */         dataProps.setProperty("moname", monitorname);
/*  5425 */         dataProps.setProperty("text", text);
/*  5426 */         dataProps.setProperty("critical", String.valueOf(critical));
/*  5427 */         dataProps.setProperty("clear", String.valueOf(clear));
/*  5428 */         dataProps.setProperty("warning", String.valueOf(warning));
/*  5429 */         dataProps.setProperty("category", previouscategory.substring(0, previouscategory.indexOf(":")));
/*  5430 */         String alertQuery = "select ENTITY from Alert where ENTITY='" + dataProps.getProperty("resourceid") + "_" + attributeID + "'";
/*       */         
/*  5432 */         ResultSet st = AMConnectionPool.executeQueryStmt(alertQuery);
/*  5433 */         dataProps.setProperty("enablelink", "" + st.first());
/*  5434 */         AMConnectionPool.closeStatement(st);
/*  5435 */         toReturn.add(dataProps);
/*       */       }
/*       */     } catch (Exception exp) {
/*  5438 */       AMLog.fatal("Reports :  Exception ", exp);
/*       */     }
/*       */     finally {
/*  5441 */       closeResultSet(set);
/*       */     }
/*  5443 */     toReturn.add(0, String.valueOf(totalclear));
/*  5444 */     toReturn.add(1, String.valueOf(totalcritical));
/*  5445 */     toReturn.add(2, String.valueOf(totalwarning));
/*  5446 */     return toReturn;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private final void closeResultSet(ResultSet set)
/*       */   {
/*  5453 */     if (set != null) {
/*       */       try {
/*  5455 */         AMConnectionPool.closeStatement(set);
/*       */       } catch (Exception ex) {
/*  5457 */         ex.printStackTrace();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   public ActionForward generateHAHealthReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*  5464 */     ActionMessages messages = new ActionMessages();
/*  5465 */     ResultSet set = null;
/*  5466 */     ResultSet set1 = null;
/*  5467 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  5468 */     ReportForm controls = (ReportForm)form;
/*  5469 */     String haid = controls.getHaid();
/*  5470 */     String period = controls.getPeriod();
/*  5471 */     long[] timeStamps = null;
/*  5472 */     request.setAttribute("HelpKey", "Monitor Group Reports");
/*  5473 */     getCustomApplications(request, "3");
/*  5474 */     getHolisticApps(mapping, form, request, response);
/*  5475 */     getMonitors(mapping, form, request, response);
/*  5476 */     if ((controls.getStartDate().equals("")) || (controls.getEndDate().equals(""))) {
/*  5477 */       timeStamps = ReportUtilities.getTimeStamp(period);
/*       */     }
/*       */     else {
/*  5480 */       controls.setPeriod("4");
/*       */       try {
/*  5482 */         timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */       } catch (IllegalArgumentException iae) {
/*  5484 */         String errMsg = iae.getMessage();
/*  5485 */         AMLog.debug("Reports :  " + errMsg);
/*  5486 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  5487 */         saveMessages(request, messages);
/*  5488 */         request.setAttribute("heading", "0");
/*  5489 */         request.setAttribute("strTime", "0");
/*  5490 */         return mapping.findForward("report.message");
/*       */       }
/*       */     }
/*       */     
/*  5494 */     AMLog.debug("Reports : Getting health report for HAID " + haid + " " + period);
/*       */     
/*  5496 */     String query = "select RESID from AM_ManagedObjectHistoryData where RESID=" + haid;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5513 */     long mintimeindb = ReportUtilities.getMinTimeInDB(haid);
/*       */     
/*  5515 */     if (mintimeindb > timeStamps[0])
/*       */     {
/*  5517 */       timeStamps[0] = mintimeindb;
/*       */     }
/*  5519 */     Properties overAllHealth = ReportUtilities.getHealthStatsForMO(haid, String.valueOf(timeStamps[0]), String.valueOf(timeStamps[1]), "", "");
/*  5520 */     if (overAllHealth.size() > 0)
/*       */     {
/*  5522 */       float criticalPercent = Float.parseFloat(overAllHealth.getProperty("Critical"));
/*  5523 */       float clearPercent = Float.parseFloat(overAllHealth.getProperty("Clear"));
/*  5524 */       float warningPercent = Float.parseFloat(overAllHealth.getProperty("Warning"));
/*  5525 */       float unmanagedPercent = Float.parseFloat(overAllHealth.getProperty("Unmanaged"));
/*  5526 */       overAllHealth.put("Critical", String.valueOf(Math.round(criticalPercent)));
/*  5527 */       overAllHealth.put("Clear", String.valueOf(Math.round(clearPercent)));
/*  5528 */       overAllHealth.put("Warning", String.valueOf(Math.round(warningPercent)));
/*  5529 */       overAllHealth.put("Unmanaged", String.valueOf(Math.round(unmanagedPercent)));
/*       */     }
/*  5531 */     String displayNameQuery = "select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + haid;
/*  5532 */     set1 = AMConnectionPool.executeQueryStmt(displayNameQuery);
/*  5533 */     if (set1.next())
/*       */     {
/*  5535 */       if (overAllHealth != null)
/*       */       {
/*  5537 */         overAllHealth.put("Name", set1.getString("DISPLAYNAME"));
/*       */       }
/*       */     }
/*  5540 */     closeResultSet(set1);
/*  5541 */     if (overAllHealth.size() == 0) {
/*  5542 */       AMLog.debug("Reports : No data for " + haid);
/*       */       
/*  5544 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.time"));
/*  5545 */       saveMessages(request, messages);
/*  5546 */       request.setAttribute("heading", "0");
/*  5547 */       request.setAttribute("strTime", "0");
/*  5548 */       return mapping.findForward("report.message");
/*       */     }
/*  5550 */     request.setAttribute("overAllHealth", overAllHealth);
/*  5551 */     String startTime = (String)overAllHealth.remove("Mintime");
/*  5552 */     String endTime = (String)overAllHealth.remove("Maxtime");
/*  5553 */     ArrayList rows = new ArrayList();
/*       */     
/*  5555 */     ArrayList residsinorder = new ArrayList();
/*  5556 */     Vector resourceids = new Vector();
/*  5557 */     Vector nametreelist = new Vector();
/*  5558 */     resourceids.add(haid);
/*  5559 */     request.setAttribute("haid", haid);
/*       */     
/*  5561 */     boolean hasSubGroups = false;
/*  5562 */     if (!Constants.slaSubGroupsEnabled.equals("true"))
/*       */     {
/*  5564 */       hasSubGroups = addAllMonitorsinSubGroups(nametreelist, residsinorder, resourceids, controls.getHaid() + "", false);
/*       */     }
/*       */     
/*       */ 
/*  5568 */     AMLog.debug("generateHAHealthReport tempresidsinorder : " + residsinorder + "  resourceids : " + resourceids);
/*  5569 */     query = DBQueryUtil.getDBQuery("am.reportaction.generatehahealthreport.query", new String[] { ReportUtilities.getCondition("AM_PARENTCHILDMAPPER.PARENTID", new Vector(residsinorder)), startTime, endTime });
/*       */     try
/*       */     {
/*  5572 */       AMLog.debug("Reports : HA child Health  query executed -->" + query);
/*  5573 */       set = AMConnectionPool.executeQueryStmt(query);
/*       */       
/*  5575 */       boolean hasData = false;
/*  5576 */       Properties dataProps; while (set.next()) {
/*  5577 */         if (("HAI".equals(set.getString("TYPE"))) && (Constants.slaSubGroupsEnabled.equals("true")))
/*       */         {
/*  5579 */           Properties dataProps = new Properties();
/*  5580 */           dataProps.setProperty("Name", set.getString(1));
/*  5581 */           float critical = set.getFloat(2);
/*  5582 */           dataProps.setProperty("Critical", String.valueOf(Math.round(critical)));
/*  5583 */           float clear = set.getFloat(3);
/*  5584 */           dataProps.setProperty("Clear", String.valueOf(Math.round(clear)));
/*  5585 */           float warning = set.getFloat(4);
/*  5586 */           dataProps.setProperty("Warning", String.valueOf(Math.round(warning)));
/*  5587 */           dataProps.setProperty("resourceid", String.valueOf(set.getInt(5)));
/*  5588 */           dataProps.setProperty("mintime", String.valueOf(set.getLong(6)));
/*  5589 */           dataProps.setProperty("maxtime", String.valueOf(set.getLong(7)));
/*  5590 */           rows.add(dataProps);
/*  5591 */           hasData = true;
/*       */         }
/*       */         
/*  5594 */         if (!"HAI".equals(set.getString("TYPE"))) {
/*  5595 */           dataProps = new Properties();
/*  5596 */           dataProps.setProperty("Name", EnterpriseUtil.decodeString(set.getString(1)));
/*  5597 */           float critical = set.getFloat(2);
/*  5598 */           dataProps.setProperty("Critical", String.valueOf(Math.round(critical)));
/*  5599 */           float clear = set.getFloat(3);
/*  5600 */           dataProps.setProperty("Clear", String.valueOf(Math.round(clear)));
/*  5601 */           float warning = set.getFloat(4);
/*  5602 */           dataProps.setProperty("Warning", String.valueOf(Math.round(warning)));
/*  5603 */           dataProps.setProperty("resourceid", String.valueOf(set.getInt(5)));
/*  5604 */           dataProps.setProperty("mintime", String.valueOf(set.getLong(6)));
/*  5605 */           dataProps.setProperty("maxtime", String.valueOf(set.getLong(7)));
/*  5606 */           rows.add(dataProps);
/*  5607 */           hasData = true;
/*       */         }
/*       */       }
/*  5610 */       if (!hasData) {
/*  5611 */         AMLog.debug("Reports : No data for " + haid);
/*       */         
/*  5613 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.time"));
/*  5614 */         saveMessages(request, messages);
/*  5615 */         request.setAttribute("heading", "0");
/*  5616 */         request.setAttribute("strTime", "0");
/*  5617 */         return mapping.findForward("report.message");
/*       */       }
/*  5619 */       singleProps = (Properties)rows.get(0);
/*  5620 */       request.setAttribute("strTime", new java.util.Date(Long.parseLong(singleProps.getProperty("mintime"))));
/*  5621 */       request.setAttribute("endTime", new java.util.Date(Long.parseLong(singleProps.getProperty("maxtime"))));
/*       */       try
/*       */       {
/*  5624 */         if (hasSubGroups)
/*       */         {
/*  5626 */           ArrayList tempdata = null;
/*  5627 */           tempdata = addMGTreetoMonitorName(rows, nametreelist, residsinorder, "Health");
/*  5628 */           if ((tempdata != null) && (tempdata.size() > 0))
/*       */           {
/*  5630 */             rows = tempdata;
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception exc)
/*       */       {
/*  5636 */         exc.printStackTrace();
/*       */       }
/*  5638 */       request.setAttribute("data", rows);
/*  5639 */       if (controls.getReporttype().equals("pdf")) {
/*  5640 */         request.setAttribute("report-type-template", "report.ha.health");
/*  5641 */         return mapping.findForward("report.ha.health.pdf");
/*       */       }
/*  5643 */       if (controls.getReporttype().equals("csv")) {
/*  5644 */         return mapping.findForward("report.ha.health.csv");
/*       */       }
/*       */       
/*  5647 */       return mapping.findForward("report.ha.health");
/*       */     }
/*       */     catch (Exception exp) {
/*       */       Properties singleProps;
/*  5651 */       AMLog.fatal("Reports :  Exception", exp);
/*  5652 */       request.setAttribute("heading", "0");
/*  5653 */       request.setAttribute("strTime", "0");
/*  5654 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  5655 */       saveMessages(request, messages);
/*  5656 */       return mapping.findForward("report.message");
/*       */     }
/*       */     finally {
/*  5659 */       closeResultSet(set);
/*  5660 */       closeResultSet(set1);
/*       */     }
/*       */   }
/*       */   
/*  5664 */   public void createTempTable() { AMConnectionPool cp = AMConnectionPool.getInstance();
/*  5665 */     ResultSet rs = null;
/*       */     try {
/*  5667 */       String selectQuery = getTopNValues("select * from AM_TEMP_RESPONSETIME_MinMaxAvgData ", "1");
/*  5668 */       String dropQuery = "drop table AM_TEMP_RESPONSETIME_MinMaxAvgData";
/*  5669 */       String createQuery = DBQueryUtil.getDBQuery("am.reportaction.createtemptable.query");
/*       */       try {
/*  5671 */         rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*  5672 */         if (rs.next()) {
/*  5673 */           AMConnectionPool.executeUpdateStmt(dropQuery);
/*       */         }
/*  5675 */         AMConnectionPool.executeUpdateStmt(createQuery);
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/*  5679 */         AMConnectionPool.executeUpdateStmt(createQuery);
/*       */         
/*  5681 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  5686 */       ex.printStackTrace();
/*       */     }
/*       */     finally {
/*  5689 */       closeResultSet(rs);
/*       */     }
/*       */   }
/*       */   
/*       */   public ActionForward generateHAResponseTimeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*  5695 */     ActionMessages messages = new ActionMessages();
/*  5696 */     ResultSet set = null;
/*  5697 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  5698 */     ReportForm controls = (ReportForm)form;
/*  5699 */     String haid = controls.getHaid();
/*  5700 */     String period = controls.getPeriod();
/*  5701 */     ReportUtilities rep = new ReportUtilities();
/*  5702 */     String valueforpdf = rep.getValueForPeriodForPDF(period);
/*  5703 */     long[] timeStamps = null;
/*  5704 */     request.setAttribute("HelpKey", "Monitor Group Reports");
/*  5705 */     getCustomApplications(request, "3");
/*  5706 */     getHolisticApps(mapping, form, request, response);
/*  5707 */     getMonitors(mapping, form, request, response);
/*  5708 */     String nodata = "report.nodata";
/*  5709 */     if ((controls.getStartDate().equals("")) || (controls.getEndDate().equals(""))) {
/*  5710 */       timeStamps = ReportUtilities.getTimeStamp(period);
/*       */     }
/*       */     else {
/*  5713 */       controls.setPeriod("4");
/*       */       try {
/*  5715 */         timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */       } catch (IllegalArgumentException iae) {
/*  5717 */         String errMsg = iae.getMessage();
/*  5718 */         AMLog.debug("Reports :  " + errMsg);
/*  5719 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  5720 */         saveMessages(request, messages);
/*  5721 */         request.setAttribute("heading", "0");
/*  5722 */         request.setAttribute("strTime", "0");
/*  5723 */         return mapping.findForward("report.message");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  5728 */     createTempTable();
/*       */     
/*  5730 */     Vector resvector = new Vector();
/*  5731 */     resvector.add(haid);
/*  5732 */     ParentChildRelationalUtil.getAllChildMOs(resvector, haid);
/*       */     
/*       */ 
/*  5735 */     String query = "select AM_ManagedObject.RESOURCEID as RESID from AM_TEMP_RESPONSETIME_MinMaxAvgData,AM_ManagedObject,AM_ATTRIBUTES_EXT,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=AM_TEMP_RESPONSETIME_MinMaxAvgData.RESID and AM_TEMP_RESPONSETIME_MinMaxAvgData.ATTRIBUTEID = AM_ATTRIBUTES_EXT.ATTRIBUTEID and AM_TEMP_RESPONSETIME_MinMaxAvgData.DURATION=1 and AM_ATTRIBUTES_EXT.VALUE_COL in ('RESPONSETIME','rtt_avearage') and AM_ATTRIBUTES_EXT.ATTRIBUTEID NOT IN('9891') and AM_TEMP_RESPONSETIME_MinMaxAvgData.RESID = AM_PARENTCHILDMAPPER.CHILDID and " + ReportUtilities.getCondition("AM_PARENTCHILDMAPPER.PARENTID", resvector) + "  group by AM_ManagedObject.RESOURCEID";
/*  5736 */     set = AMConnectionPool.executeQueryStmt(query);
/*       */     
/*  5738 */     boolean hasAnyData = false;
/*  5739 */     if (set.next())
/*       */     {
/*  5741 */       hasAnyData = true;
/*       */     }
/*       */     
/*  5744 */     closeResultSet(set);
/*  5745 */     if (!hasAnyData)
/*       */     {
/*  5747 */       AMLog.debug("Reports : Data Collection not started for " + haid);
/*  5748 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodatacollection.monitors"));
/*  5749 */       saveMessages(request, messages);
/*  5750 */       request.setAttribute("heading", "0");
/*  5751 */       request.setAttribute("strTime", "0");
/*  5752 */       return mapping.findForward("report.message");
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  5757 */     AMLog.debug("Reports : Getting response time report for HAID " + haid + " " + period);
/*  5758 */     long[] dailyRptTimestamp = ReportUtilities.getDailyStartEndTime(timeStamps[0], timeStamps[1], "AM_TEMP_RESPONSETIME_MinMaxAvgData");
/*       */     
/*  5760 */     String dailyRptCondition = " and AM_TEMP_RESPONSETIME_MinMaxAvgData.DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1];
/*       */     
/*  5762 */     request.setAttribute("dailyStime", dailyRptTimestamp[2] + "");
/*  5763 */     request.setAttribute("dailyEtime", dailyRptTimestamp[3] + "");
/*  5764 */     if (dailyRptTimestamp[2] > 0L)
/*       */     {
/*  5766 */       dailyRptCondition = " and ( AM_TEMP_RESPONSETIME_MinMaxAvgData.DURATION=1 and ARCHIVEDTIME >=" + dailyRptTimestamp[0] + " and ARCHIVEDTIME <=" + dailyRptTimestamp[1] + " OR AM_TEMP_RESPONSETIME_MinMaxAvgData.DURATION=2 and ARCHIVEDTIME >=" + dailyRptTimestamp[2] + " and ARCHIVEDTIME <=" + dailyRptTimestamp[3] + ") ";
/*       */     }
/*       */     
/*  5769 */     ArrayList residsinorder = new ArrayList();
/*  5770 */     Vector resourceids = new Vector();
/*  5771 */     Vector nametreelist = new Vector();
/*  5772 */     resourceids.add(haid);
/*  5773 */     boolean hasSubGroups = addAllMonitorsinSubGroups(nametreelist, residsinorder, resourceids, controls.getHaid() + "", false);
/*       */     
/*       */ 
/*  5776 */     if ("true".equals(Constants.attributesReportGraphType))
/*       */     {
/*  5778 */       query = DBQueryUtil.getDBQuery("am.amreportAction.generateHAResponseTimeReport1", new String[] { ReportUtilities.getCondition("AM_PARENTCHILDMAPPER.PARENTID", resvector) + dailyRptCondition, "'RESPONSETIME','rtt_avearage'" });
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*  5783 */       query = DBQueryUtil.getDBQuery("am.amreportAction.generateHAResponseTimeReport2", new String[] { ReportUtilities.getCondition("AM_PARENTCHILDMAPPER.PARENTID", resvector) + dailyRptCondition, "'RESPONSETIME','rtt_avearage'" });
/*       */     }
/*       */     
/*       */     try
/*       */     {
/*  5788 */       request.setAttribute("STIME", dailyRptTimestamp[0] + "");
/*  5789 */       request.setAttribute("ETIME", dailyRptTimestamp[1] + "");
/*  5790 */       request.setAttribute("dailyStime", dailyRptTimestamp[2] + "");
/*  5791 */       request.setAttribute("dailyEtime", dailyRptTimestamp[3] + "");
/*       */       
/*  5793 */       ArrayList data = ReportUtilities.getTabularData(query, false);
/*       */       try
/*       */       {
/*  5796 */         ArrayList tempdata = null;
/*  5797 */         if (hasSubGroups)
/*       */         {
/*  5799 */           tempdata = addMGTreetoMonitorName(data, nametreelist, residsinorder, "attribute");
/*  5800 */           if ((tempdata != null) && (tempdata.size() > 0))
/*       */           {
/*  5802 */             data = tempdata;
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception exc)
/*       */       {
/*  5808 */         exc.printStackTrace();
/*       */       }
/*  5810 */       p1 = ReportUtilities.getResourceIdInOrder(query, null, "AM_TEMP_RESPONSETIME_MinMaxAvgData");
/*       */       
/*  5812 */       Vector RESV = (Vector)p1.get("RESOUREID");
/*  5813 */       Vector ATTV = (Vector)p1.get("ATTRIBUTEID");
/*       */       
/*  5815 */       if (data.size() == 0) {
/*  5816 */         AMLog.debug("Reports :No data for " + haid);
/*       */         
/*  5818 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.time"));
/*  5819 */         saveMessages(request, messages);
/*  5820 */         request.setAttribute("heading", "0");
/*  5821 */         request.setAttribute("strTime", "0");
/*  5822 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/*  5825 */       String s = ReportUtilities.getConvertedToString(RESV);
/*  5826 */       String a = ReportUtilities.getConvertedToString(ATTV);
/*  5827 */       for (int k = 0; k < data.size(); k++) {
/*  5828 */         ArrayList dataend = (ArrayList)data.get(k);
/*  5829 */         String dname = (String)dataend.get(0);
/*  5830 */         dataend.add(dname);
/*       */       }
/*       */       
/*  5833 */       ArrayList singleData = (ArrayList)data.get(0);
/*  5834 */       request.setAttribute("strTime", new java.util.Date(((Long)singleData.get(5)).longValue()));
/*  5835 */       request.setAttribute("endTime", new java.util.Date(((Long)singleData.get(6)).longValue()));
/*  5836 */       request.setAttribute("RESOURCEIDS", s);
/*  5837 */       request.setAttribute("ATTRIBUTEIDS", a);
/*  5838 */       request.setAttribute("data", data);
/*  5839 */       request.setAttribute("methodName", "generateHAResponseTimeReport");
/*  5840 */       ChartInfo cinfo; if (controls.getReporttype().equals("pdf")) {
/*  5841 */         controls.setPdfAttributeName("responseTime");
/*  5842 */         cinfo = new ChartInfo();
/*  5843 */         SummaryBean sumgraph = new SummaryBean();
/*       */         
/*  5845 */         sumgraph.setResid(s);
/*  5846 */         sumgraph.setAttributeid(a);
/*  5847 */         sumgraph.setStarttime((String)request.getAttribute("STIME"));
/*  5848 */         sumgraph.setEndtime((String)request.getAttribute("ETIME"));
/*       */         
/*  5850 */         sumgraph.setDailyRptStarttime((String)request.getAttribute("dailyStime"));
/*  5851 */         sumgraph.setDailyRptEndtime((String)request.getAttribute("dailyEtime"));
/*  5852 */         sumgraph.setArchivedforUrl(true);
/*  5853 */         sumgraph.setCompareUrls(true);
/*  5854 */         sumgraph.setMethodName("generateHAResponseTimeReport");
/*  5855 */         if ("true".equals(Constants.attributesReportGraphType)) {
/*  5856 */           sumgraph.setBarData(data);
/*  5857 */           cinfo.setMaxBarWidth(0.03D);
/*  5858 */           cinfo.setLegend("false");
/*  5859 */           cinfo.setXaxisLabel(FormatUtil.getString("Monitors"));
/*  5860 */           cinfo.setHeight("300");
/*  5861 */           cinfo.setWidth("700");
/*       */         }
/*       */         else
/*       */         {
/*  5865 */           cinfo.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*  5866 */           int sizeofdata = data.size();
/*  5867 */           if (sizeofdata < 5)
/*       */           {
/*  5869 */             cinfo.setHeight("300");
/*  5870 */             cinfo.setWidth("700");
/*       */           }
/*  5872 */           else if ((sizeofdata >= 5) && (sizeofdata < 10)) {
/*  5873 */             cinfo.setHeight("500");
/*  5874 */             cinfo.setWidth("700");
/*       */ 
/*       */           }
/*  5877 */           else if ((sizeofdata >= 10) && (sizeofdata < 20)) {
/*  5878 */             cinfo.setHeight("800");
/*  5879 */             cinfo.setWidth("700");
/*       */           }
/*  5881 */           else if ((sizeofdata >= 20) && (sizeofdata < 30)) {
/*  5882 */             cinfo.setHeight("900");
/*  5883 */             cinfo.setWidth("700");
/*       */           }
/*  5885 */           else if ((sizeofdata >= 30) && (sizeofdata < 40)) {
/*  5886 */             cinfo.setHeight("1000");
/*  5887 */             cinfo.setWidth("800");
/*       */           }
/*       */           else {
/*  5890 */             cinfo.setHeight("1100");
/*  5891 */             cinfo.setWidth("900");
/*       */           }
/*       */         }
/*  5894 */         cinfo.setYaxisLabel(FormatUtil.getString("am.webclient.db2.graph.responsetimeinms"));
/*  5895 */         cinfo.setShape(true);
/*  5896 */         cinfo.setCustomDateAxis(true);
/*  5897 */         cinfo.setCustomAngle(270.0D);
/*  5898 */         cinfo.setDataSet(sumgraph);
/*       */         
/*       */ 
/*  5901 */         request.setAttribute("reportname", "AttributeReport");
/*  5902 */         request.setAttribute("height", cinfo.getHeight());
/*  5903 */         String image = null;
/*  5904 */         if ("true".equals(Constants.attributesReportGraphType)) {
/*  5905 */           image = cinfo.get3DbarChartAsJPG();
/*       */         }
/*       */         else
/*       */         {
/*  5909 */           image = cinfo.getTimeChartAsJPG();
/*       */         }
/*       */         
/*  5912 */         request.setAttribute("attributeImage", image);
/*  5913 */         request.setAttribute("period", period);
/*  5914 */         request.setAttribute("periodvalue", valueforpdf);
/*  5915 */         request.setAttribute("report-type-template", "report.perf");
/*  5916 */         return mapping.findForward("report.perf.pdf");
/*       */       }
/*  5918 */       if (controls.getReporttype().equals("csv")) {
/*  5919 */         return mapping.findForward("report.perf.csv");
/*       */       }
/*       */       
/*       */ 
/*  5923 */       return mapping.findForward("report.perf");
/*       */     }
/*       */     catch (Exception exp) {
/*       */       HashMap p1;
/*  5927 */       AMLog.fatal("Reports :  Exception", exp);
/*  5928 */       request.setAttribute("heading", "0");
/*  5929 */       request.setAttribute("strTime", "0");
/*  5930 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  5931 */       saveMessages(request, messages);
/*  5932 */       return mapping.findForward("report.message");
/*       */     }
/*       */     finally {
/*  5935 */       closeResultSet(set);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public ActionForward generateHASnapShotReportWithHostName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  5943 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  5944 */     ActionMessages messages = new ActionMessages();
/*  5945 */     ResultSet set = null;
/*       */     try {
/*  5947 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*  5948 */       getCustomApplications(request, "3");
/*  5949 */       getHolisticApps(mapping, form, request, response);
/*  5950 */       getMonitors(mapping, form, request, response);
/*  5951 */       ReportForm controls = (ReportForm)form;
/*  5952 */       String haid = controls.getHaid();
/*  5953 */       String fromSchedule = request.getParameter("isschedule");
/*  5954 */       String customfield = controls.getCustomFieldValue();
/*  5955 */       boolean hasAnyData = false;
/*  5956 */       String reportByCustomField = controls.getCustomfield();
/*  5957 */       ResultSet haidset = null;
/*  5958 */       String haidquery = null;
/*       */       
/*  5960 */       ArrayList allHaids = new ArrayList();
/*       */       
/*  5962 */       if ((("all".equals(haid)) || ("allmonitors".equals(haid))) && (!reportByCustomField.equals("true"))) {
/*       */         try {
/*  5964 */           if ((privilegedUser) && (!EnterpriseUtil.isIt360MSPEdition())) {
/*  5965 */             haidquery = getHaidResourceQuery(request);
/*       */           } else {
/*  5967 */             String bsgFilterCondn = "";
/*  5968 */             String bsgType = "0";
/*  5969 */             if (EnterpriseUtil.isIt360MSPEdition())
/*       */             {
/*  5971 */               bsgType = "1";
/*  5972 */               Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  5973 */               bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */             }
/*  5975 */             haidquery = "select HAID,DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + " order by DISPLAYNAME";
/*       */           }
/*  5977 */           haidset = AMConnectionPool.executeQueryStmt(haidquery);
/*  5978 */           while (haidset.next()) {
/*  5979 */             String highlevelhaid = haidset.getString("HAID");
/*  5980 */             String highleveldispname = haidset.getString("DISPLAYNAME");
/*       */             
/*  5982 */             allHaids.add(highlevelhaid);
/*       */           }
/*       */           
/*       */         }
/*       */         catch (Exception ex)
/*       */         {
/*  5988 */           ex.printStackTrace();
/*       */ 
/*       */         }
/*       */         finally {}
/*       */ 
/*       */       }
/*  5994 */       else if ((customfield != null) && (customfield.indexOf("$") != -1) && (reportByCustomField.equals("true"))) {
/*  5995 */         allHaids = MyFields.customFieldsMGs(request, customfield);
/*       */       }
/*  5997 */       else if ("true".equals(fromSchedule))
/*       */       {
/*  5999 */         String[] temp = haid.split(",");
/*  6000 */         if (temp.length > 0) {
/*  6001 */           for (int k = 0; k < temp.length; k++)
/*       */           {
/*  6003 */             allHaids.add(temp[k]);
/*       */           }
/*       */         }
/*  6006 */       } else if (haid.indexOf("$") != -1) {
/*  6007 */         allHaids = MyFields.customFieldsMGs(request, haid);
/*       */       } else {
/*  6009 */         allHaids.add(haid);
/*       */       }
/*       */       
/*  6012 */       ArrayList ALLDATA = null;
/*  6013 */       if ("allmonitors".equals(haid))
/*       */       {
/*  6015 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids, true);
/*       */       }
/*       */       else
/*       */       {
/*  6019 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  6024 */       System.out.println("the alldata==>" + ALLDATA);
/*  6025 */       Hashtable appkeys = (Hashtable)request.getSession().getServletContext().getAttribute("availabilitykeys");
/*  6026 */       Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/*       */       
/*       */ 
/*  6029 */       ArrayList allvalues = ReportUtilities.getHostnameForHASnapshotReport(ALLDATA, appkeys, healthkeys);
/*       */       
/*  6031 */       request.setAttribute("withhostname", "true");
/*       */       
/*       */ 
/*  6034 */       request.setAttribute("strTime", "0");
/*       */       
/*  6036 */       request.setAttribute("data", allvalues);
/*  6037 */       ActionForward localActionForward; if (controls.getReporttype().equals("pdf")) {
/*  6038 */         request.setAttribute("reportname", "HASnapshotReport");
/*  6039 */         request.setAttribute("reportType", "pdf");
/*  6040 */         request.setAttribute("report-type-template", "report.snapshot");
/*  6041 */         return mapping.findForward("report.snapshot.pdf"); }
/*  6042 */       if (controls.getReporttype().equals("excel")) {
/*  6043 */         request.setAttribute("reportname", "HASnapshotReport");
/*       */         
/*  6045 */         request.setAttribute("report-type-template", "report.snapshot");
/*  6046 */         request.setAttribute("reportType", "excel");
/*  6047 */         return mapping.findForward("report.snapshot.pdf");
/*       */       }
/*       */       
/*  6050 */       if (controls.getReporttype().equals("csv")) {
/*  6051 */         return mapping.findForward("report.snapshot.csv");
/*       */       }
/*       */       
/*       */ 
/*  6055 */       return mapping.findForward("report.snapshot");
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  6060 */       ex.printStackTrace();
/*       */     }
/*       */     finally {
/*  6063 */       closeResultSet(set);
/*       */     }
/*  6065 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward generateLicUsageReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  6071 */     String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
/*       */     
/*       */     try
/*       */     {
/*  6075 */       ReportForm controls = (ReportForm)form;
/*  6076 */       ActionMessages messages = new ActionMessages();
/*       */       
/*       */ 
/*  6079 */       Wield wield = Wield.getInstance();
/*  6080 */       String companyName = wield.getCompanyName();
/*  6081 */       String userName = wield.getUserName();
/*       */       
/*       */ 
/*  6084 */       Properties wieldProps = wield.getModuleProperties("LicenseDetails");
/*  6085 */       AMLog.logLicUsage("[IT360 License Usage Calculation] PROPERTIES from License file " + wieldProps);
/*       */       
/*  6087 */       String emailID = wieldProps.getProperty("PrimaryContact");
/*  6088 */       String licDuration = request.getParameter("licDuration");
/*  6089 */       AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: generateLicUsageReport ::: The Report Period is " + licDuration);
/*       */       
/*  6091 */       if ((licDuration != null) && ("lastMonth".equalsIgnoreCase(licDuration)))
/*       */       {
/*  6093 */         java.util.Date todayDate = new java.util.Date();
/*  6094 */         Calendar calendar = Calendar.getInstance();
/*  6095 */         calendar.setTime(todayDate);
/*  6096 */         calendar.add(2, -1);
/*       */         
/*  6098 */         int monthInNumber = calendar.get(2);
/*       */         
/*  6100 */         String month = months[monthInNumber];
/*  6101 */         int year = calendar.get(1);
/*       */         
/*  6103 */         LinkedHashMap licMonthlyUsageProps = ReportUtil.getMonthlyLicUsage(month, year);
/*       */         
/*  6105 */         Properties licDisProps = (Properties)ReportUtil.getLicenseCategoryDisplayNameProps();
/*  6106 */         AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: month ::: " + month + " licDisProps " + licDisProps + " year " + year);
/*       */         
/*  6108 */         if (licMonthlyUsageProps.size() == 0)
/*       */         {
/*  6110 */           AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: No data available for the Month " + month + " to show");
/*  6111 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.schedulemail.nodatamessage.text"));
/*  6112 */           saveMessages(request, messages);
/*  6113 */           request.setAttribute("heading", "0");
/*  6114 */           request.setAttribute("strTime", "0");
/*  6115 */           request.setAttribute("licDuration", licDuration);
/*  6116 */           request.setAttribute("month", month);
/*  6117 */           request.setAttribute("year", year + "");
/*  6118 */           return mapping.findForward("report.message");
/*       */         }
/*       */         
/*  6121 */         request.setAttribute("displayData", licDisProps);
/*  6122 */         request.setAttribute("licUsage", licMonthlyUsageProps);
/*  6123 */         request.setAttribute("licDuration", licDuration);
/*  6124 */         request.setAttribute("month", month);
/*  6125 */         request.setAttribute("year", year + "");
/*  6126 */         request.setAttribute("userName", userName);
/*  6127 */         request.setAttribute("companyName", companyName);
/*  6128 */         request.setAttribute("emailID", emailID);
/*  6129 */         request.setAttribute("heading", FormatUtil.getString("am.webclient.reports.it360.msp.license.usage.report"));
/*       */         
/*  6131 */         if (controls.getReporttype().equals("pdf"))
/*       */         {
/*  6133 */           AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: Entering default pdf reports::: ");
/*  6134 */           String reportName = "LicenseUsage_" + companyName + "_" + month + "_" + year;
/*  6135 */           request.setAttribute("reportname", reportName);
/*  6136 */           request.setAttribute("reportType", "pdf");
/*  6137 */           request.setAttribute("report-type-template", "report.lic.snapshot");
/*  6138 */           return mapping.findForward("report.lic.snapshot.pdf");
/*       */         }
/*  6140 */         AMLog.logLicUsage("[IT360 License Usage Calculation] Entering default html reports::: displayData " + licDisProps + " ::: licUsage " + licMonthlyUsageProps);
/*  6141 */         return mapping.findForward("report.lic.snapshot");
/*       */       }
/*  6143 */       if (licDuration != null)
/*       */       {
/*  6145 */         String fromMonth = null;
/*  6146 */         int fromYear = 0;
/*  6147 */         String toMonth = null;
/*  6148 */         int toYear = 0;
/*  6149 */         String middleMonth = null;
/*       */         
/*  6151 */         Calendar cal = Calendar.getInstance();
/*  6152 */         cal.setTime(new java.util.Date());
/*  6153 */         int month = cal.get(2);
/*  6154 */         int year = cal.get(1);
/*       */         
/*  6156 */         if ("thisQuarter".equalsIgnoreCase(licDuration))
/*       */         {
/*  6158 */           switch (month)
/*       */           {
/*       */           case 0: 
/*  6161 */             fromMonth = "January";
/*  6162 */             toMonth = "January";
/*  6163 */             fromYear = year;
/*  6164 */             toYear = year;
/*  6165 */             break;
/*       */           case 1: 
/*  6167 */             fromMonth = "January";
/*  6168 */             toMonth = "Feburary";
/*  6169 */             fromYear = year;
/*  6170 */             toYear = year;
/*  6171 */             break;
/*       */           case 2: 
/*  6173 */             fromMonth = "January";
/*  6174 */             middleMonth = "Feburary";
/*  6175 */             toMonth = "March";
/*  6176 */             fromYear = year;
/*  6177 */             toYear = year;
/*  6178 */             break;
/*       */           case 3: 
/*  6180 */             fromMonth = "April";
/*  6181 */             toMonth = "April";
/*  6182 */             fromYear = year;
/*  6183 */             toYear = year;
/*  6184 */             break;
/*       */           case 4: 
/*  6186 */             fromMonth = "April";
/*  6187 */             toMonth = "May";
/*  6188 */             fromYear = year;
/*  6189 */             toYear = year;
/*  6190 */             break;
/*       */           case 5: 
/*  6192 */             fromMonth = "April";
/*  6193 */             middleMonth = "May";
/*  6194 */             toMonth = "June";
/*  6195 */             fromYear = year;
/*  6196 */             toYear = year;
/*  6197 */             break;
/*       */           case 6: 
/*  6199 */             fromMonth = "July";
/*  6200 */             toMonth = "July";
/*  6201 */             fromYear = year;
/*  6202 */             toYear = year;
/*  6203 */             break;
/*       */           case 7: 
/*  6205 */             fromMonth = "July";
/*  6206 */             toMonth = "August";
/*  6207 */             fromYear = year;
/*  6208 */             toYear = year;
/*  6209 */             break;
/*       */           case 8: 
/*  6211 */             fromMonth = "July";
/*  6212 */             middleMonth = "August";
/*  6213 */             toMonth = "September";
/*  6214 */             fromYear = year;
/*  6215 */             toYear = year;
/*  6216 */             break;
/*       */           case 9: 
/*  6218 */             fromMonth = "October";
/*  6219 */             toMonth = "October";
/*  6220 */             fromYear = year;
/*  6221 */             toYear = year;
/*  6222 */             break;
/*       */           case 10: 
/*  6224 */             fromMonth = "October";
/*  6225 */             toMonth = "November";
/*  6226 */             fromYear = year;
/*  6227 */             toYear = year;
/*  6228 */             break;
/*       */           case 11: 
/*  6230 */             fromMonth = "October";
/*  6231 */             middleMonth = "November";
/*  6232 */             toMonth = "December";
/*  6233 */             fromYear = year;
/*  6234 */             toYear = year;
/*       */           }
/*       */           
/*  6237 */           AMLog.logLicUsage("AMReportAction ::: This Quarter ::: fromMonth " + fromMonth + " toMonth " + toMonth + " fromYear " + fromYear + " toYear " + toYear);
/*       */           
/*  6239 */           StringBuffer sb = new StringBuffer();
/*  6240 */           if (fromMonth != null)
/*       */           {
/*  6242 */             sb.append("'").append(fromMonth).append("'");
/*       */           }
/*  6244 */           if (middleMonth != null)
/*       */           {
/*  6246 */             sb.append(",'").append(middleMonth).append("'");
/*       */           }
/*  6248 */           if (!toMonth.equals(fromMonth))
/*       */           {
/*  6250 */             sb.append(",'").append(toMonth).append("'");
/*       */           }
/*       */           
/*       */ 
/*  6254 */           if (!ReportUtil.isQuarterlyDataAvailable(sb.toString(), year))
/*       */           {
/*  6256 */             AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: No data available for this Quarter to show");
/*  6257 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.schedulemail.nodatamessage.text"));
/*  6258 */             saveMessages(request, messages);
/*  6259 */             request.setAttribute("heading", "0");
/*  6260 */             request.setAttribute("strTime", "0");
/*  6261 */             request.setAttribute("licDuration", licDuration);
/*  6262 */             request.setAttribute("fromMonth", fromMonth);
/*  6263 */             request.setAttribute("fromYear", fromYear + "");
/*  6264 */             request.setAttribute("toMonth", toMonth);
/*  6265 */             request.setAttribute("toYear", toYear + "");
/*  6266 */             return mapping.findForward("report.message");
/*       */           }
/*       */         }
/*  6269 */         else if ("lastYear".equalsIgnoreCase(licDuration))
/*       */         {
/*  6271 */           Calendar calYear = Calendar.getInstance();
/*  6272 */           calYear.setTime(new java.util.Date());
/*  6273 */           calYear.add(1, -1);
/*  6274 */           int lastYear = calYear.get(1);
/*  6275 */           fromMonth = "January";
/*  6276 */           fromYear = lastYear;
/*  6277 */           toYear = lastYear;
/*  6278 */           toMonth = "December";
/*       */           
/*  6280 */           if (!ReportUtil.isYearlyDataAvailable(lastYear))
/*       */           {
/*  6282 */             AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: No data available for to show for Last Year " + lastYear);
/*  6283 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.schedulemail.nodatamessage.text"));
/*  6284 */             saveMessages(request, messages);
/*  6285 */             request.setAttribute("heading", "0");
/*  6286 */             request.setAttribute("strTime", "0");
/*  6287 */             request.setAttribute("licDuration", licDuration);
/*  6288 */             request.setAttribute("fromMonth", fromMonth);
/*  6289 */             request.setAttribute("fromYear", fromYear + "");
/*  6290 */             request.setAttribute("toMonth", toMonth);
/*  6291 */             request.setAttribute("toYear", toYear + "");
/*  6292 */             return mapping.findForward("report.message");
/*       */           }
/*       */         }
/*  6295 */         else if ("lastQuarter".equalsIgnoreCase(licDuration))
/*       */         {
/*  6297 */           int correctYear = year;
/*  6298 */           switch (month)
/*       */           {
/*       */           case 0: 
/*       */           case 1: 
/*       */           case 2: 
/*  6303 */             fromMonth = "October";
/*  6304 */             middleMonth = "November";
/*  6305 */             toMonth = "December";
/*  6306 */             fromYear = year - 1;
/*  6307 */             toYear = year - 1;
/*  6308 */             correctYear = year - 1;
/*  6309 */             break;
/*       */           case 3: 
/*       */           case 4: 
/*       */           case 5: 
/*  6313 */             fromMonth = "January";
/*  6314 */             middleMonth = "Feburary";
/*  6315 */             toMonth = "March";
/*  6316 */             fromYear = year;
/*  6317 */             toYear = year;
/*  6318 */             break;
/*       */           case 6: 
/*       */           case 7: 
/*       */           case 8: 
/*  6322 */             fromMonth = "April";
/*  6323 */             middleMonth = "May";
/*  6324 */             toMonth = "June";
/*  6325 */             fromYear = year;
/*  6326 */             toYear = year;
/*  6327 */             break;
/*       */           case 9: 
/*       */           case 10: 
/*       */           case 11: 
/*  6331 */             fromMonth = "July";
/*  6332 */             middleMonth = "August";
/*  6333 */             toMonth = "September";
/*  6334 */             fromYear = year;
/*  6335 */             toYear = year;
/*       */           }
/*       */           
/*  6338 */           StringBuffer sb = new StringBuffer();
/*  6339 */           if (fromMonth != null)
/*       */           {
/*  6341 */             sb.append("'").append(fromMonth).append("'");
/*       */           }
/*  6343 */           if (middleMonth != null)
/*       */           {
/*  6345 */             sb.append(",'").append(middleMonth).append("'");
/*       */           }
/*  6347 */           if (!toMonth.equals(fromMonth))
/*       */           {
/*  6349 */             sb.append(",'").append(toMonth).append("'");
/*       */           }
/*       */           
/*  6352 */           if (!ReportUtil.isQuarterlyDataAvailable(sb.toString(), correctYear))
/*       */           {
/*  6354 */             AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: No data available for Last Quarter to show");
/*  6355 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.schedulemail.nodatamessage.text"));
/*  6356 */             saveMessages(request, messages);
/*  6357 */             request.setAttribute("heading", "0");
/*  6358 */             request.setAttribute("strTime", "0");
/*  6359 */             request.setAttribute("licDuration", licDuration);
/*  6360 */             request.setAttribute("fromMonth", fromMonth);
/*  6361 */             request.setAttribute("fromYear", fromYear + "");
/*  6362 */             request.setAttribute("toMonth", toMonth);
/*  6363 */             request.setAttribute("toYear", toYear + "");
/*  6364 */             return mapping.findForward("report.message");
/*       */           }
/*       */         }
/*  6367 */         else if ("thisYear".equalsIgnoreCase(licDuration))
/*       */         {
/*  6369 */           Calendar calYear = Calendar.getInstance();
/*  6370 */           calYear.setTime(new java.util.Date());
/*  6371 */           int thisYear = calYear.get(1);
/*  6372 */           int thisMonth = calYear.get(2);
/*  6373 */           fromMonth = "January";
/*  6374 */           fromYear = thisYear;
/*  6375 */           toYear = thisYear;
/*       */           
/*  6377 */           toMonth = months[(thisMonth - 1)];
/*       */           
/*  6379 */           if (!ReportUtil.isYearlyDataAvailable(thisYear))
/*       */           {
/*  6381 */             AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: No data available for to show this Year " + thisYear);
/*  6382 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.schedulemail.nodatamessage.text"));
/*  6383 */             saveMessages(request, messages);
/*  6384 */             request.setAttribute("heading", "0");
/*  6385 */             request.setAttribute("strTime", "0");
/*  6386 */             request.setAttribute("licDuration", licDuration);
/*  6387 */             request.setAttribute("fromMonth", fromMonth);
/*  6388 */             request.setAttribute("fromYear", fromYear + "");
/*  6389 */             request.setAttribute("toMonth", toMonth);
/*  6390 */             request.setAttribute("toYear", toYear + "");
/*  6391 */             return mapping.findForward("report.message");
/*       */           }
/*       */         }
/*  6394 */         else if ("customPeriod".equalsIgnoreCase(licDuration))
/*       */         {
/*  6396 */           AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions :::  fromMonth " + request.getParameter("fromMonth") + " fromYear " + request.getParameter("fromYear") + " toMonth " + request.getParameter("toMonth") + " toYear " + request.getParameter("toYear"));
/*  6397 */           fromMonth = request.getParameter("fromMonth");
/*  6398 */           fromYear = Integer.parseInt(request.getParameter("fromYear"));
/*  6399 */           toMonth = request.getParameter("toMonth");
/*  6400 */           toYear = Integer.parseInt(request.getParameter("toYear"));
/*       */         }
/*       */         
/*       */ 
/*  6404 */         HashMap licMonthlyUsageMap = ReportUtil.getMonthlyLicUsageForAPeriod(fromMonth, fromYear, toMonth, toYear);
/*       */         
/*  6406 */         Properties licDisProps = (Properties)ReportUtil.getLicenseCategoryDisplayNameProps();
/*       */         
/*  6408 */         request.setAttribute("displayData", licDisProps);
/*  6409 */         request.setAttribute("licUsage", licMonthlyUsageMap);
/*  6410 */         request.setAttribute("licDuration", licDuration);
/*  6411 */         request.setAttribute("fromMonth", fromMonth);
/*  6412 */         request.setAttribute("fromYear", fromYear + "");
/*  6413 */         request.setAttribute("toMonth", toMonth);
/*  6414 */         request.setAttribute("toYear", toYear + "");
/*  6415 */         request.setAttribute("userName", userName);
/*  6416 */         request.setAttribute("companyName", companyName);
/*  6417 */         request.setAttribute("emailID", emailID);
/*  6418 */         request.setAttribute("heading", FormatUtil.getString("am.webclient.reports.it360.msp.license.usage.report"));
/*       */         
/*  6420 */         if (controls.getReporttype().equals("pdf"))
/*       */         {
/*  6422 */           AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: Entering default pdf reports::: ");
/*  6423 */           String reportName = "LicenseUsage_" + companyName;
/*  6424 */           request.setAttribute("reportname", reportName);
/*  6425 */           request.setAttribute("reportType", "pdf");
/*  6426 */           request.setAttribute("report-type-template", "report.lic.snapshot");
/*  6427 */           return mapping.findForward("report.lic.snapshot.pdf");
/*       */         }
/*  6429 */         AMLog.logLicUsage("[IT360 License Usage Calculation] AMReportActions ::: Entering default html reports:::  displayData " + licDisProps + " ::: licUsage Map " + licMonthlyUsageMap);
/*  6430 */         return mapping.findForward("report.lic.period.snapshot");
/*       */       }
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  6435 */       ex.printStackTrace();
/*       */     }
/*  6437 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */   public ActionForward generateHASnapShotReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  6444 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  6445 */     ActionMessages messages = new ActionMessages();
/*  6446 */     ResultSet set = null;
/*       */     try {
/*  6448 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*  6449 */       getCustomApplications(request, "3");
/*  6450 */       getHolisticApps(mapping, form, request, response);
/*  6451 */       getMonitors(mapping, form, request, response);
/*  6452 */       ReportForm controls = (ReportForm)form;
/*  6453 */       String haid = controls.getHaid();
/*  6454 */       String fromSchedule = request.getParameter("isschedule");
/*  6455 */       String customfield = controls.getCustomFieldValue();
/*  6456 */       boolean hasAnyData = false;
/*  6457 */       String reportByCustomField = controls.getCustomfield();
/*  6458 */       ResultSet haidset = null;
/*  6459 */       String haidquery = null;
/*       */       
/*  6461 */       ArrayList allHaids = new ArrayList();
/*       */       
/*  6463 */       if ((("all".equals(haid)) || ("allmonitors".equals(haid))) && (!reportByCustomField.equals("true"))) {
/*       */         try {
/*  6465 */           if ((privilegedUser) && (!EnterpriseUtil.isIt360MSPEdition())) {
/*  6466 */             haidquery = getHaidResourceQuery(request);
/*       */           } else {
/*  6468 */             String bsgFilterCondn = "";
/*  6469 */             String bsgType = "0";
/*  6470 */             if (EnterpriseUtil.isIt360MSPEdition())
/*       */             {
/*  6472 */               bsgType = "1";
/*  6473 */               Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  6474 */               bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */             }
/*  6476 */             haidquery = "select HAID,DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn;
/*       */           }
/*  6478 */           haidset = AMConnectionPool.executeQueryStmt(haidquery);
/*  6479 */           while (haidset.next()) {
/*  6480 */             String highlevelhaid = haidset.getString("HAID");
/*  6481 */             String highleveldispname = haidset.getString("DISPLAYNAME");
/*       */             
/*  6483 */             allHaids.add(highlevelhaid);
/*       */           }
/*       */         }
/*       */         catch (Exception ex)
/*       */         {
/*  6488 */           ex.printStackTrace();
/*       */ 
/*       */         }
/*       */         finally {}
/*       */       }
/*  6493 */       else if ((customfield != null) && (customfield.indexOf("$") != -1) && (reportByCustomField.equals("true"))) {
/*  6494 */         allHaids = MyFields.customFieldsMGs(request, customfield);
/*       */       }
/*  6496 */       else if ("true".equals(fromSchedule))
/*       */       {
/*  6498 */         String[] temp = haid.split(",");
/*  6499 */         if (temp.length > 0) {
/*  6500 */           for (int k = 0; k < temp.length; k++)
/*       */           {
/*  6502 */             allHaids.add(temp[k]);
/*       */           }
/*       */         }
/*       */       } else {
/*  6506 */         allHaids.add(haid);
/*       */       }
/*       */       
/*  6509 */       ArrayList ALLDATA = null;
/*  6510 */       if ("allmonitors".equals(haid))
/*       */       {
/*  6512 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids, true);
/*       */       }
/*       */       else
/*       */       {
/*  6516 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids);
/*       */       }
/*       */       
/*       */ 
/*  6520 */       Hashtable appkeys = (Hashtable)request.getSession().getServletContext().getAttribute("availabilitykeys");
/*  6521 */       Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/*  6522 */       ArrayList allvalues = new ArrayList();
/*       */       ArrayList ResultData;
/*  6524 */       if (Constants.slaSubGroupsEnabled.equals("true"))
/*       */       {
/*  6526 */         ResultData = new ArrayList();
/*  6527 */         ArrayList WorkingData = new ArrayList();
/*  6528 */         boolean flag = false;
/*  6529 */         for (int i = 0; i < ALLDATA.size(); i++)
/*       */         {
/*  6531 */           System.out.println("Alldata main loop");
/*  6532 */           ArrayList Main = (ArrayList)ALLDATA.get(i);
/*  6533 */           if (Main.size() > 0)
/*       */           {
/*  6535 */             for (int s = 0; s < Main.size(); s++)
/*       */             {
/*  6537 */               System.out.println("inside main loop");
/*  6538 */               ArrayList insertAllDetails = new ArrayList();
/*  6539 */               ArrayList insideMain = (ArrayList)Main.get(s);
/*       */               
/*  6541 */               String TYPE = insideMain.get(3).toString();
/*       */               
/*  6543 */               if ("SUBGROUP".equals(TYPE))
/*       */               {
/*  6545 */                 flag = true;
/*  6546 */                 WorkingData.add(insideMain);
/*       */               }
/*  6548 */               if (!flag)
/*       */               {
/*  6550 */                 WorkingData.add(insideMain);
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*  6557 */         ResultData.add(WorkingData);
/*  6558 */         System.out.println("the resultdata==>" + ResultData);
/*       */         
/*  6560 */         allvalues = ReportUtilities.getValueForHASnapshotReport(ResultData, appkeys, healthkeys);
/*       */       }
/*       */       else
/*       */       {
/*  6564 */         allvalues = ReportUtilities.getValueForHASnapshotReport(ALLDATA, appkeys, healthkeys);
/*       */       }
/*       */       
/*  6567 */       request.setAttribute("data", allvalues);
/*  6568 */       if (controls.getReporttype().equals("pdf")) {
/*  6569 */         request.setAttribute("reportname", "HASnapshotReport");
/*  6570 */         request.setAttribute("reportType", "pdf");
/*  6571 */         request.setAttribute("report-type-template", "report.snapshot");
/*  6572 */         return mapping.findForward("report.snapshot.pdf"); }
/*  6573 */       if (controls.getReporttype().equals("excel")) {
/*  6574 */         request.setAttribute("reportname", "HASnapshotReport");
/*       */         
/*  6576 */         request.setAttribute("report-type-template", "report.snapshot");
/*  6577 */         request.setAttribute("reportType", "excel");
/*  6578 */         return mapping.findForward("report.snapshot.pdf");
/*       */       }
/*       */       
/*  6581 */       if (controls.getReporttype().equals("csv")) {
/*  6582 */         return mapping.findForward("report.snapshot.csv");
/*       */       }
/*       */       
/*       */ 
/*  6586 */       return mapping.findForward("report.snapshot");
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  6591 */       ex.printStackTrace();
/*       */     }
/*       */     finally {
/*  6594 */       closeResultSet(set);
/*       */     }
/*  6596 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */   public Properties getAlerts(ArrayList monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*       */   {
/*  6602 */     Properties alert = null;
/*       */     try {
/*  6604 */       ArrayList attribIDs = new ArrayList();
/*  6605 */       ArrayList resIDs = new ArrayList();
/*       */       
/*  6607 */       for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++) {
/*  6608 */         ArrayList row = (ArrayList)monitorList.get(j);
/*  6609 */         String resourceid = (String)row.get(6);
/*  6610 */         resIDs.add(resourceid);
/*  6611 */         String resourceType = (String)row.get(7);
/*  6612 */         Object healthkey = healthkeys.get(resourceType);
/*  6613 */         if (attribIDs.indexOf(healthkey) == -1) {
/*  6614 */           attribIDs.add(healthkey);
/*       */         }
/*  6616 */         Object availabilitykey = availabilitykeys.get(resourceType);
/*  6617 */         if (attribIDs.indexOf(availabilitykey) == -1) {
/*  6618 */           attribIDs.add(availabilitykey);
/*       */         }
/*       */       }
/*  6621 */       alert = FaultUtil.getStatus(resIDs, attribIDs);
/*       */     }
/*       */     catch (Exception ex) {
/*  6624 */       ex.printStackTrace();
/*       */     }
/*       */     
/*  6627 */     return alert;
/*       */   }
/*       */   
/*       */   public ActionForward generateHAAvailabilityReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*  6632 */     ActionMessages messages = new ActionMessages();
/*  6633 */     ResultSet set = null;
/*  6634 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  6635 */     ReportForm controls = (ReportForm)form;
/*  6636 */     String haid = controls.getHaid();
/*  6637 */     String period = controls.getPeriod();
/*  6638 */     String businessRule = controls.getBusinessPeriod();
/*  6639 */     AMLog.debug("Reports : Getting HAAvailability report for HAID " + haid + " " + period);
/*  6640 */     getCustomApplications(request, "3");
/*  6641 */     getHolisticApps(mapping, form, request, response);
/*  6642 */     getMonitors(mapping, form, request, response);
/*       */     
/*  6644 */     long[] timeStamps = null;
/*  6645 */     request.setAttribute("HelpKey", "Monitor Group Reports");
/*  6646 */     String nodata = "report.nodata";
/*  6647 */     if ((controls.getStartDate() == null) || (controls.getStartDate().equals("")) || (controls.getEndDate().equals("")))
/*       */     {
/*  6649 */       timeStamps = ReportUtilities.getTimeStamp(period);
/*       */     }
/*       */     else
/*       */     {
/*  6653 */       controls.setPeriod("4");
/*  6654 */       period = "4";
/*       */       try {
/*  6656 */         timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */       } catch (IllegalArgumentException iae) {
/*  6658 */         String errMsg = iae.getMessage();
/*  6659 */         AMLog.debug("Reports :  " + errMsg);
/*  6660 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  6661 */         saveMessages(request, messages);
/*  6662 */         request.setAttribute("heading", "0");
/*  6663 */         request.setAttribute("strTime", "0");
/*  6664 */         return mapping.findForward("report.message");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  6669 */     String monTypeQuery = "select TYPE from AM_HOLISTICAPPLICATION where HAID=" + haid;
/*  6670 */     set = AMConnectionPool.executeQueryStmt(monTypeQuery);
/*  6671 */     if (set.next())
/*       */     {
/*  6673 */       request.setAttribute("MonType", set.getString("TYPE"));
/*       */     }
/*       */     
/*  6676 */     String query = "select RESID from AM_ManagedObjectHistoryData where RESID=" + haid;
/*       */     
/*  6678 */     set = AMConnectionPool.executeQueryStmt(query);
/*  6679 */     boolean hasAnyData = false;
/*  6680 */     if (set.next())
/*       */     {
/*  6682 */       hasAnyData = true;
/*       */     }
/*       */     
/*  6685 */     closeResultSet(set);
/*  6686 */     if (hasAnyData) {
/*  6687 */       nodata = "report.nodata.time";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  6692 */     Vector datacollectionStartedMOs = new Vector();
/*  6693 */     ParentChildRelationalUtil.getAllChildMapper(datacollectionStartedMOs, haid, true, true);
/*  6694 */     Properties overAllAvailability = null;
/*       */     try {
/*  6696 */       overAllAvailability = ReportUtilities.getAvailabilityStatsForMO(haid, String.valueOf(timeStamps[0]), String.valueOf(timeStamps[1]), period, "", "oni", null);
/*       */     }
/*       */     catch (IllegalStateException ise)
/*       */     {
/*  6700 */       ise.printStackTrace();
/*  6701 */       AMLog.debug(" Reports : Inconsistent Data " + haid);
/*       */       
/*  6703 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(ise.getMessage()));
/*  6704 */       saveMessages(request, messages);
/*  6705 */       request.setAttribute("heading", "0");
/*  6706 */       request.setAttribute("strTime", "0");
/*  6707 */       return mapping.findForward("report.message");
/*       */     }
/*  6709 */     if ((overAllAvailability == null) || (overAllAvailability.size() == 0))
/*       */     {
/*  6711 */       AMLog.debug(" Reports : No data for " + haid);
/*       */       
/*  6713 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(nodata));
/*  6714 */       saveMessages(request, messages);
/*  6715 */       request.setAttribute("heading", "0");
/*  6716 */       request.setAttribute("strTime", "0");
/*  6717 */       return mapping.findForward("report.message");
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6724 */     long startTime = timeStamps[0];
/*  6725 */     long endTime = timeStamps[1];
/*  6726 */     long parentmintimeindb = ReportUtilities.getMinTimeInDB(haid);
/*  6727 */     ArrayList residsinorder = new ArrayList();
/*  6728 */     Vector nametreelist = new Vector();
/*  6729 */     Vector resourceids = new Vector();
/*  6730 */     resourceids.add(haid);
/*  6731 */     boolean hasSubGroups = false;
/*  6732 */     if (!Constants.slaSubGroupsEnabled.equals("true"))
/*       */     {
/*  6734 */       hasSubGroups = addAllMonitorsinSubGroups(nametreelist, residsinorder, resourceids, controls.getHaid() + "", false);
/*       */     }
/*       */     
/*  6737 */     String residquery = "select CHILDID,DISPLAYNAME,TYPE from AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("AM_PARENTCHILDMAPPER.PARENTID", resourceids) + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*       */     
/*  6739 */     ArrayList resources = new ArrayList();
/*  6740 */     Properties residdisplaynamemapping = new Properties();
/*  6741 */     Properties residVsMinTime = new Properties();
/*       */     
/*       */ 
/*       */     try
/*       */     {
/*  6746 */       set = AMConnectionPool.executeQueryStmt(residquery);
/*  6747 */       boolean hasMonitorInstance = false;
/*  6748 */       dataCollectionNotStarted = false;
/*       */       String resid;
/*  6750 */       while (set.next())
/*       */       {
/*  6752 */         resid = String.valueOf(set.getInt("CHILDID"));
/*       */         
/*  6754 */         if (!datacollectionStartedMOs.contains(resid)) {
/*  6755 */           dataCollectionNotStarted = true;
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*  6760 */           if (("HAI".equals(set.getString("TYPE"))) && (Constants.slaSubGroupsEnabled.equals("true")))
/*       */           {
/*  6762 */             resources.add(resid);
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6770 */             residdisplaynamemapping.setProperty(resid, "Sub-Group->" + EnterpriseUtil.decodeString(set.getString("DISPLAYNAME")));
/*  6771 */             long mintimeindb = ReportUtilities.getMinTimeInDB(resid);
/*  6772 */             residVsMinTime.setProperty(resid, String.valueOf(parentmintimeindb));
/*  6773 */             hasMonitorInstance = true;
/*       */           }
/*       */           
/*  6776 */           if (!"HAI".equals(set.getString("TYPE"))) {
/*  6777 */             resources.add(resid);
/*  6778 */             residdisplaynamemapping.setProperty(resid, EnterpriseUtil.decodeString(set.getString("DISPLAYNAME")));
/*  6779 */             long mintimeindb = ReportUtilities.getMinTimeInDB(resid);
/*  6780 */             residVsMinTime.setProperty(resid, String.valueOf(parentmintimeindb));
/*  6781 */             hasMonitorInstance = true;
/*       */           }
/*       */         }
/*       */       }
/*  6785 */       closeResultSet(set);
/*       */       
/*  6787 */       if (!hasMonitorInstance)
/*       */       {
/*  6789 */         if (dataCollectionNotStarted)
/*       */         {
/*  6791 */           AMLog.debug("Reports : Data Collection not started for " + haid);
/*  6792 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodatacollection.monitors"));
/*  6793 */           saveMessages(request, messages);
/*  6794 */           request.setAttribute("heading", "0");
/*  6795 */           request.setAttribute("strTime", "0");
/*  6796 */           return mapping.findForward("report.message");
/*       */         }
/*       */         
/*  6799 */         AMLog.debug("Reports : No data for " + haid);
/*  6800 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(nodata));
/*  6801 */         saveMessages(request, messages);
/*  6802 */         request.setAttribute("heading", "0");
/*  6803 */         request.setAttribute("strTime", "0");
/*  6804 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/*  6807 */       ArrayList data = new ArrayList();
/*  6808 */       for (Enumeration enum2 = residVsMinTime.keys(); enum2.hasMoreElements();)
/*       */       {
/*  6810 */         String resourceID = (String)enum2.nextElement();
/*  6811 */         String minTime = residVsMinTime.getProperty(resourceID);
/*  6812 */         long minTimeInDB = Long.parseLong(minTime);
/*  6813 */         if (minTimeInDB > startTime)
/*       */         {
/*  6815 */           startTime = minTimeInDB;
/*       */         }
/*  6817 */         if (startTime > endTime)
/*       */         {
/*  6819 */           resources.remove(resourceID);
/*       */         }
/*       */         else {
/*  6822 */           rows = new Properties();
/*  6823 */           long totalDuration = 0L;
/*  6824 */           if ((businessRule != null) && (!businessRule.equals("oni")))
/*       */           {
/*  6826 */             totalDuration = ReportUtilities.getTotalDurationFromBH(startTime, endTime, businessRule);
/*       */           }
/*       */           else
/*       */           {
/*  6830 */             totalDuration = endTime - startTime;
/*       */           }
/*  6832 */           AMLog.debug("totalDuration=" + totalDuration);
/*  6833 */           ArrayList list = ReportUtilities.getDowntimeDetails(resourceID, startTime, endTime, businessRule);
/*  6834 */           int size = list.size();
/*  6835 */           AMLog.debug("list = " + list + "\tsize=" + size);
/*  6836 */           if (size > 0)
/*       */           {
/*  6838 */             long totalDowntime = 0L;
/*  6839 */             long upTime = 0L;
/*  6840 */             int downCount = 0;
/*  6841 */             if (resources.contains(resourceID))
/*       */             {
/*  6843 */               resources.remove(resourceID);
/*       */             }
/*  6845 */             for (int i = 0; i < size; i++)
/*       */             {
/*  6847 */               Properties df = (Properties)list.get(i);
/*       */               
/*  6849 */               totalDowntime += Long.parseLong(df.getProperty("downtimeinmillis"));
/*       */               
/*  6851 */               downCount++;
/*       */             }
/*  6853 */             upTime = totalDuration - totalDowntime;
/*  6854 */             float upPercent = (float)upTime / (float)totalDuration * 100.0F;
/*  6855 */             float downPercent = (float)totalDowntime / (float)totalDuration * 100.0F;
/*  6856 */             long mttr = totalDowntime / downCount;
/*  6857 */             long mtbf = upTime / downCount;
/*  6858 */             rows.setProperty("Name", residdisplaynamemapping.getProperty(resourceID));
/*  6859 */             rows.setProperty("resourceid", String.valueOf(resourceID));
/*  6860 */             rows.setProperty("totaldowntime", ReportUtilities.format(totalDowntime));
/*  6861 */             rows.put("DowntimeInLong", new Long(totalDowntime));
/*  6862 */             rows.setProperty("mttr", ReportUtilities.format(mttr));
/*  6863 */             rows.setProperty("mtbf", ReportUtilities.format(mtbf));
/*  6864 */             rows.setProperty("available", String.valueOf(Math.round(upPercent * 100.0F) / 100.0F));
/*  6865 */             data.add(rows);
/*       */           }
/*       */           else
/*       */           {
/*  6869 */             String moname = residdisplaynamemapping.getProperty(resourceID);
/*  6870 */             rows.setProperty("Name", moname);
/*  6871 */             rows.setProperty("resourceid", resourceID);
/*  6872 */             if (totalDuration >= 0L)
/*       */             {
/*  6874 */               rows.setProperty("totaldowntime", ReportUtilities.format(0L));
/*  6875 */               rows.put("DowntimeInLong", new Long(0L));
/*  6876 */               rows.setProperty("mttr", ReportUtilities.format(0L));
/*  6877 */               rows.setProperty("mtbf", ReportUtilities.format(totalDuration));
/*       */             }
/*       */             else
/*       */             {
/*  6881 */               rows.setProperty("totaldowntime", FormatUtil.getString("NA"));
/*  6882 */               rows.put("DowntimeInLong", new Long(0L));
/*  6883 */               rows.setProperty("mttr", FormatUtil.getString("NA"));
/*  6884 */               rows.setProperty("mtbf", FormatUtil.getString("NA"));
/*       */             }
/*  6886 */             rows.setProperty("available", "100");
/*  6887 */             data.add(rows);
/*       */           }
/*       */         } }
/*       */       Properties rows;
/*  6891 */       Properties per = ReportUtilities.getMonitorGroupAvailability(controls.getHaid(), period, startTime, endTime, businessRule);
/*       */       
/*       */ 
/*  6894 */       String ServicesDownPercent = per.getProperty("unavailable");
/*       */       
/*  6896 */       String ServicesUpPercent = per.getProperty("available");
/*  6897 */       String ServicesUnmanagedPercent = per.getProperty("ServicesUnMgPercent");
/*  6898 */       String ServicesScheduledPercent = per.getProperty("ServicesSchPercent");
/*       */       
/*       */ 
/*       */ 
/*  6902 */       overAllAvailability.setProperty("ServicesUpPercent", ServicesUpPercent);
/*  6903 */       overAllAvailability.setProperty("ServicesDownPercent", ServicesDownPercent);
/*  6904 */       if (ServicesUnmanagedPercent != null) {
/*  6905 */         overAllAvailability.setProperty("ServicesUnMgPercent", ServicesUnmanagedPercent);
/*       */       }
/*  6907 */       if (ServicesScheduledPercent != null) {
/*  6908 */         overAllAvailability.setProperty("ServicesSchPercent", ServicesScheduledPercent);
/*       */       }
/*       */       
/*  6911 */       if (data.size() == 0)
/*       */       {
/*  6913 */         AMLog.debug("Reports :  No data for " + haid);
/*       */         
/*  6915 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(nodata));
/*  6916 */         saveMessages(request, messages);
/*  6917 */         request.setAttribute("heading", "0");
/*  6918 */         request.setAttribute("strTime", "0");
/*  6919 */         return mapping.findForward("report.message");
/*       */       }
/*       */       try
/*       */       {
/*  6923 */         if (hasSubGroups)
/*       */         {
/*  6925 */           ArrayList tempdata = null;
/*  6926 */           tempdata = addMGTreetoMonitorName(data, nametreelist, residsinorder, "Availability");
/*  6927 */           if ((tempdata != null) && (tempdata.size() > 0))
/*       */           {
/*  6929 */             data = tempdata;
/*       */           }
/*       */           
/*       */ 
/*  6933 */           Properties[] propArray = (Properties[])data.toArray(new Properties[data.size()]);
/*  6934 */           Arrays.sort(propArray, new ComparatorImplForSubGroups());
/*  6935 */           data.clear();
/*  6936 */           int propArraySize = propArray.length;
/*  6937 */           for (int i = 0; i < propArraySize; i++) {
/*  6938 */             data.add(propArray[i]);
/*       */           }
/*       */         } else {
/*  6941 */           Properties[] propArray = (Properties[])data.toArray(new Properties[data.size()]);
/*  6942 */           Arrays.sort(propArray, new ComparatorImpl());
/*  6943 */           data.clear();
/*  6944 */           int propArraySize = propArray.length;
/*  6945 */           for (int i = 0; i < propArraySize; i++) {
/*  6946 */             data.add(propArray[i]);
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception exc)
/*       */       {
/*  6952 */         exc.printStackTrace();
/*       */       }
/*       */       
/*  6955 */       request.setAttribute("overAllAvailability", overAllAvailability);
/*  6956 */       request.setAttribute("strTime", new java.util.Date(startTime));
/*  6957 */       request.setAttribute("endTime", new java.util.Date(endTime));
/*  6958 */       request.setAttribute("data", data);
/*  6959 */       if (controls.getReporttype().equals("pdf")) {
/*  6960 */         request.setAttribute("report-type-template", "report.ha.availability");
/*  6961 */         return mapping.findForward("report.ha.availability.pdf");
/*       */       }
/*  6963 */       if (controls.getReporttype().equals("csv")) {
/*  6964 */         return mapping.findForward("report.ha.availability.csv");
/*       */       }
/*       */       
/*  6967 */       if ("true".equals(request.getParameter("PRINTER_FRIENDLY"))) {
/*  6968 */         request.setAttribute("heading", FormatUtil.getString("am.reporttab.heading.haavailablityreport.text", new String[] { overAllAvailability.getProperty("Name") }));
/*       */       }
/*  6970 */       return mapping.findForward("report.ha.availability");
/*       */     } catch (Exception exp) {
/*       */       boolean dataCollectionNotStarted;
/*  6973 */       exp.printStackTrace();
/*  6974 */       AMLog.fatal("Reports :  Exception", exp);
/*  6975 */       request.setAttribute("heading", "0");
/*  6976 */       request.setAttribute("strTime", "0");
/*  6977 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  6978 */       saveMessages(request, messages);
/*  6979 */       return mapping.findForward("report.message");
/*       */     }
/*       */     finally {
/*  6982 */       closeResultSet(set);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ActionForward emailPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  6991 */     ActionMessages messages = new ActionMessages();
/*       */     
/*  6993 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  6994 */     ReportUtilities ru = new ReportUtilities();
/*  6995 */     ReportForm rf = (ReportForm)form;
/*  6996 */     String reason = null;
/*  6997 */     String successvalue = "true";
/*  6998 */     String reportType = "pdf";
/*  6999 */     String extension = "pdf";
/*  7000 */     int responsecode = 200;
/*       */     try
/*       */     {
/*  7003 */       String protocol = EnterpriseUtil.isSslEnabled ? "https" : "http";
/*  7004 */       String host = InetAddress.getLocalHost().getHostName();
/*  7005 */       String port = EnterpriseUtil.isSslEnabled ? System.getProperty("ssl.port") : System.getProperty("webserver.port");
/*  7006 */       String htmlmess = getHTMLMailTemplate();
/*       */       
/*  7008 */       String period = request.getParameter("period");
/*  7009 */       String aMethod = request.getParameter("aMethod");
/*  7010 */       String hid = request.getParameter("hid");
/*  7011 */       String attribute = request.getParameter("attribute");
/*       */       
/*       */ 
/*  7014 */       String attributeName = request.getParameter("attributeName");
/*  7015 */       String getpdf = request.getParameter("emailpdf");
/*  7016 */       String unit = request.getParameter("unit");
/*  7017 */       String businessRule = request.getParameter("brule");
/*  7018 */       String reportmethod = request.getParameter("reportmethod");
/*  7019 */       String report = null;
/*  7020 */       reportType = request.getParameter("reportType");
/*       */       
/*  7022 */       if ("excel".equals(reportType)) {
/*  7023 */         extension = "xls";
/*       */       }
/*  7025 */       com.adventnet.appmanager.util.SmtpEMailer.reportType = reportType;
/*  7026 */       String resourceid = request.getParameter("resourceid");
/*  7027 */       String resid = request.getParameter("resid");
/*  7028 */       String haid = request.getParameter("haid");
/*       */       
/*  7030 */       String resourceType = rf.getResourceType();
/*       */       
/*  7032 */       String numberOfRows = request.getParameter("numberOfRows");
/*  7033 */       String startDate = request.getParameter("startDate");
/*  7034 */       String thisstartDate = request.getParameter("thisstart");
/*  7035 */       String thisendDate = request.getParameter("thisend");
/*  7036 */       String laststartDate = request.getParameter("laststart");
/*  7037 */       String lastendDate = request.getParameter("lastend");
/*  7038 */       String endDate = request.getParameter("endDate");
/*  7039 */       String pdfAttributeName = request.getParameter("pdfAttributeName");
/*  7040 */       String reporturl = null;
/*  7041 */       String mondaycapacity = null;
/*  7042 */       String Root_Dir = System.getProperty("webnms.rootdir");
/*  7043 */       String[] dirs = Root_Dir.split("working");
/*  7044 */       String Work_Dir = dirs[0];
/*  7045 */       String absolutepath = Work_Dir + "Reports/EmailPDF";
/*  7046 */       ArrayList filelist = new ArrayList();
/*       */       
/*  7048 */       String periodfordisplay = null;
/*  7049 */       if (aMethod.equals("generateMSSQLPerformanceReport")) {
/*  7050 */         if ("Execution Time".equals(period)) {
/*  7051 */           String topqrycnt = request.getParameter("topqrycnt");
/*  7052 */           periodfordisplay = FormatUtil.getString("am.webclient.common.toppolledvalues.text");
/*  7053 */           if ((!topqrycnt.equals("polledValues")) && (!topqrycnt.equals(""))) {
/*  7054 */             periodfordisplay = FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { topqrycnt });
/*       */           }
/*  7056 */         } else if (("".equals(period)) || (period == null)) {
/*  7057 */           periodfordisplay = "Today's";
/*       */         } else {
/*  7059 */           periodfordisplay = ru.getValueForPeriodForPDF(period);
/*       */         }
/*       */       } else {
/*  7062 */         periodfordisplay = ru.getValueForPeriodForPDF(period);
/*       */       }
/*       */       
/*  7065 */       String displayname = null;
/*  7066 */       String typeofmonitor = null;
/*  7067 */       String topHeading = FormatUtil.getString("am.webclient.managermail.schedulemail.topheading.text");
/*  7068 */       String addInfo = FormatUtil.getString("am.webclient.managermail.additionalinfo.text");
/*  7069 */       String reportBy = FormatUtil.getString("am.webclient.managermail.reportby.text");
/*  7070 */       String productName = OEMUtil.getOEMString("product.name");
/*  7071 */       String reportAction = "/showReports.do";
/*  7072 */       if ((aMethod.equals("generateHAAvailabilityReport")) || (aMethod.equals("generateHAHealthReport")) || (aMethod.equals("generateHASnapShotReport")) || (aMethod.equals("generateHASnapShotReportWithHostName")) || (aMethod.equals("generateHAResponseTimeReport")) || (aMethod.equals("generateAvailabilitySnapShotReport")) || (aMethod.equals("generateWeeklyMonthlyOutageReport")) || (aMethod.equals("generatePeriodAvailabilityReport")) || (aMethod.equals("generateEventSummary")) || (aMethod.equals("generatePeriodAvailabilityDowntimeReport")))
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7081 */         displayname = ReportUtilities.getLabelName(haid);
/*  7082 */         typeofmonitor = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*       */       }
/*  7084 */       else if ((aMethod.equals("generateAttributeReport")) && (hid != null) && (hid.equals("true")))
/*       */       {
/*  7086 */         displayname = ReportUtilities.getLabelName(haid);
/*  7087 */         typeofmonitor = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*       */       }
/*  7089 */       else if ((aMethod.equals("generateMttrAvailablityReport")) || (aMethod.equals("generateSummaryReport")))
/*       */       {
/*  7091 */         displayname = ReportUtilities.getLabelName(resid);
/*  7092 */         typeofmonitor = "Monitor";
/*       */       }
/*  7094 */       else if ((aMethod.equalsIgnoreCase("generateUnderSizedMonitors")) || (aMethod.equalsIgnoreCase("generateIndividualReportCapacityPlanning")))
/*       */       {
/*  7096 */         resourceType = request.getParameter("resourceType");
/*  7097 */         mondaycapacity = request.getParameter("mondaycapacity");
/*  7098 */         String disnameRestype = resourceType;
/*  7099 */         if ("VMWare ESX/ESXi".equalsIgnoreCase(resourceType))
/*       */         {
/*  7101 */           disnameRestype = "VMWare_ESX_ESXi";
/*       */         }
/*       */         
/*  7104 */         typeofmonitor = "Monitor";
/*  7105 */         reportAction = "/showCustomReports.do";
/*  7106 */         if (reportmethod.equalsIgnoreCase("generateUnderSizedMonitors"))
/*       */         {
/*  7108 */           displayname = FormatUtil.getString("am.reporttab.header.undersized") + "_" + disnameRestype;
/*       */         }
/*  7110 */         else if (reportmethod.equalsIgnoreCase("generateOverSizedMonitors"))
/*       */         {
/*  7112 */           displayname = FormatUtil.getString("am.reporttab.header.oversized") + "_" + disnameRestype;
/*       */         }
/*  7114 */         else if (reportmethod.equalsIgnoreCase("generateIdleMonitors"))
/*       */         {
/*  7116 */           displayname = FormatUtil.getString("am.reporttab.header.idlesized") + "_" + disnameRestype;
/*       */         }
/*       */         else
/*       */         {
/*  7120 */           displayname = request.getParameter("resourceType");
/*       */         }
/*  7122 */         if ((aMethod.equalsIgnoreCase("generateIndividualReportCapacityPlanning")) || (aMethod.equalsIgnoreCase("generateUnderSizedMonitors")))
/*       */         {
/*  7124 */           report = request.getParameter("report");
/*       */         }
/*       */       }
/*  7127 */       else if (aMethod.equals("generateCustomAttributeReport")) {
/*  7128 */         if (attribute != null) {
/*  7129 */           ResultSet rs = AMConnectionPool.executeQueryStmt("select DISPLAYNAME from AM_ATTRIBUTES where ATTRIBUTEID=" + attribute);
/*  7130 */           if (rs.next()) {
/*  7131 */             displayname = rs.getString("DISPLAYNAME");
/*       */           }
/*       */         }
/*       */         else {
/*  7135 */           displayname = attributeName;
/*       */         }
/*       */         
/*  7138 */         typeofmonitor = "Monitor";
/*       */       }
/*  7140 */       else if (("true".equals(hid)) && (aMethod.equals("generateGlanceReport")))
/*       */       {
/*  7142 */         displayname = ReportUtilities.getLabelName(haid);
/*  7143 */         typeofmonitor = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*       */       }
/*  7145 */       else if (aMethod.equals("generateIndividualGlanceReport"))
/*       */       {
/*  7147 */         displayname = ReportUtilities.getLabelName(resourceid);
/*  7148 */         typeofmonitor = "Monitor";
/*       */       }
/*  7150 */       else if (aMethod.equals("showEumSummaryReport"))
/*       */       {
/*  7152 */         displayname = FormatUtil.getString("am.webclient.eum.summaryreport");
/*  7153 */         typeofmonitor = FormatUtil.getString("am.webclient.eumagents.text");
/*       */       }
/*       */       else
/*       */       {
/*  7157 */         displayname = getResourceTypeValueForEmailPDF(resourceType);
/*  7158 */         typeofmonitor = "Monitor";
/*       */       }
/*  7160 */       String disname = null;
/*  7161 */       if ("HP-UX / Tru64".equals(displayname))
/*       */       {
/*  7163 */         disname = "HP-UX_Tru64";
/*       */       }
/*  7165 */       else if ("FreeBSD / OpenBSD".equals(displayname))
/*       */       {
/*  7167 */         disname = "FreeBSD_OpenBSD";
/*       */       }
/*  7169 */       else if ("AS400/iSeries".equals(displayname))
/*       */       {
/*  7171 */         disname = "AS400_iSeries";
/*       */       }
/*       */       else
/*       */       {
/*  7175 */         disname = FormatUtil.findReplace(displayname, " ", "_");
/*       */       }
/*  7177 */       String customField = "false";
/*  7178 */       if (request.getParameter("customfield") != null)
/*       */       {
/*  7180 */         customField = request.getParameter("customfield");
/*       */       }
/*  7182 */       String customFieldValue = request.getParameter("customFieldValue");
/*  7183 */       String reportname = getReportNameForEmailPDF(aMethod);
/*       */       
/*  7185 */       String inter = request.getParameter("interval");
/*       */       boolean success;
/*  7187 */       if (new File(absolutepath).exists()) {
/*  7188 */         System.out.println("*******the dir exists*********" + absolutepath);
/*       */       }
/*       */       else {
/*  7191 */         success = new File(absolutepath).mkdir();
/*       */       }
/*  7193 */       host = "localhost";
/*  7194 */       String hostport = protocol + "://" + host + ":" + port;
/*       */       
/*  7196 */       if (AMAutomaticPortChanger.isSsoEnabled()) {
/*  7197 */         hostport = System.getProperty("CASClientUrl", protocol + "://" + host + ":" + port);
/*  7198 */         attributeName = URLEncoder.encode(attributeName);
/*       */       }
/*       */       
/*  7201 */       if ((period != null) && (period.equals("4")))
/*       */       {
/*  7203 */         reporturl = hostport + "" + reportAction + "" + "?actionMethod=" + aMethod + "&period=" + period + "&interval=" + inter + "&businessPeriod=" + businessRule + "&numberOfRows=" + numberOfRows + "&Report=true&reporttype=" + reportType + "&PRINTER_FRIENDLY=false&haid=" + haid + "&resid=" + resid + "&resourceid=" + resourceid + "&resourceType=" + URLEncoder.encode(resourceType) + "&attribute=" + attribute + "&attributeName=" + attributeName + "&pdfAttributeName=" + pdfAttributeName + "&startDate=" + startDate + "&endDate=" + endDate + "&thisstart=" + thisstartDate + "&thisend=" + thisendDate + "&laststart=" + laststartDate + "&lastend=" + lastendDate + "&unit=" + URLEncoder.encode(unit) + "&hid=" + hid + "&getpdf=" + getpdf + "&report=" + report + "&reportmethod=" + reportmethod + "&mondaycapacity=" + mondaycapacity + "&customfield=" + customField + "&customFieldValue=" + customFieldValue;
/*       */       }
/*       */       else {
/*  7206 */         reporturl = hostport + "" + reportAction + "?actionMethod=" + aMethod + "&period=" + period + "&interval=" + inter + "&businessPeriod=" + businessRule + "&numberOfRows=" + numberOfRows + "&Report=true&reporttype=" + reportType + "&PRINTER_FRIENDLY=false&haid=" + haid + "&resid=" + resid + "&resourceid=" + resourceid + "&resourceType=" + URLEncoder.encode(resourceType) + "&attribute=" + attribute + "&attributeName=" + attributeName + "&pdfAttributeName=" + pdfAttributeName + "&startDate=" + startDate + "&endDate=" + endDate + "&thisstart=" + thisstartDate + "&thisend=" + thisendDate + "&laststart=" + laststartDate + "&lastend=" + lastendDate + "&unit=" + URLEncoder.encode(unit) + "&hid=" + hid + "&getpdf=" + getpdf + "&report=" + report + "&reportmethod=" + reportmethod + "&mondaycapacity=" + mondaycapacity + "&customfield=" + customField + "&customFieldValue=" + customFieldValue;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  7211 */       if (aMethod.equals("generateMSSQLPerformanceReport")) {
/*  7212 */         String fromDate = request.getParameter("fromDate");
/*  7213 */         String toDate = request.getParameter("toDate");
/*  7214 */         String topqrycnt = request.getParameter("topqrycnt");
/*  7215 */         String hrefname = request.getParameter("hrefname");
/*  7216 */         String database = request.getParameter("database");
/*  7217 */         reporturl = protocol + "://" + host + ":" + port + "/showMSSQLReports.do?actionMethod=" + aMethod + "&period=" + period + "&Report=true&reportType=" + reportType + "&PRINTER_FRIENDLY=false&haid=" + haid + "&resourceid=" + resourceid + "&resourceType=" + URLEncoder.encode(resourceType) + "&hid=" + hid + "&getpdf=" + getpdf + "&report=" + report + "&hrefname=" + hrefname + "&fromDate=" + startDate + "&toDate=" + endDate + "&database=" + database + "&topqrycnt=" + topqrycnt;
/*       */ 
/*       */       }
/*  7220 */       else if (aMethod.equals("generateLicUsageReport")) {
/*  7221 */         String month = request.getParameter("month");
/*  7222 */         String year = request.getParameter("year");
/*  7223 */         String licDuration = request.getParameter("licDuration");
/*  7224 */         String fromMonth = request.getParameter("fromMonth");
/*  7225 */         String fromYear = request.getParameter("fromYear");
/*  7226 */         String toMonth = request.getParameter("toMonth");
/*  7227 */         String toYear = request.getParameter("toYear");
/*  7228 */         reporturl = reporturl + "&month=" + month + "&year=" + year + "&licDuration=" + licDuration + "&fromMonth=" + fromMonth + "&fromYear=" + fromYear + "&toMonth=" + toMonth + "&toYear=" + toYear;
/*       */       }
/*  7230 */       if ((Constants.sqlManager) && (aMethod.equals("generateMSSQLGeneralDetailsReport"))) {
/*  7231 */         String selectedColumns = request.getParameter("selectedColumns");
/*  7232 */         String columnHeadings = request.getParameter("columnHeadings");
/*  7233 */         String columnsAdded = request.getParameter("columnsAdded");
/*  7234 */         reporturl = protocol + "://" + host + ":" + port + "/showReports.do?actionMethod=" + aMethod + "&period=" + period + "&Report=true&reportType=" + reportType + "&PRINTER_FRIENDLY=false&haid=" + haid + "&resourceid=" + resourceid + "&resourceType=" + URLEncoder.encode(resourceType) + "&hid=" + hid + "&getpdf=" + getpdf + "&report=" + report + "&selectedColumns=" + selectedColumns + "&columnHeadings=" + columnHeadings + "&columnsAdded=";
/*       */       }
/*  7236 */       if (Constants.isIt360)
/*       */       {
/*  7238 */         String ticket = EnterpriseUtil.getTicket(null, null, "loginuser", "U2FuZ2VldGhN", false);
/*  7239 */         AMLog.debug("[SCHEDULE-REPORTS] Ticket to be used : " + ticket);
/*  7240 */         reporturl = reporturl + "&ticket=" + ticket;
/*       */       }
/*       */       
/*       */ 
/*  7244 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*  7246 */         Properties custProp = EnterpriseUtil.getCustProp(request);
/*  7247 */         if ((custProp != null) && (custProp.size() > 0))
/*       */         {
/*  7249 */           String custName = custProp.keys().nextElement().toString();
/*  7250 */           String custId = custProp.getProperty(custName);
/*  7251 */           reporturl = reporturl.trim() + "&custName=" + custName + "&custId=" + custId;
/*       */         }
/*  7253 */         Properties siteProp = EnterpriseUtil.getSiteProp(request);
/*  7254 */         if ((siteProp != null) && (siteProp.size() > 0))
/*       */         {
/*  7256 */           String siteName = siteProp.keys().nextElement().toString();
/*  7257 */           String siteId = siteProp.getProperty(siteName);
/*  7258 */           reporturl = reporturl.trim() + "&siteName=" + siteName + "&siteId=" + siteId;
/*       */         }
/*       */       }
/*       */       
/*  7262 */       String toadd = request.getParameter("emailid");
/*       */       
/*  7264 */       String[] temp2 = toadd.split(":");
/*       */       
/*  7266 */       if (temp2.length > 1) {
/*  7267 */         toadd = temp2[1];
/*       */       }
/*       */       
/*  7270 */       String pwd = Constants.RPWD;
/*       */       
/*       */ 
/*  7273 */       String from = request.getParameter("save");
/*  7274 */       String comment = request.getParameter("comment");
/*  7275 */       if (comment == null)
/*       */       {
/*  7277 */         comment = " ";
/*       */       }
/*  7279 */       if ((from == null) || (from.equals("select"))) {
/*  7280 */         from = toadd;
/*  7281 */         String[] te = toadd.split(",");
/*  7282 */         from = te[0];
/*       */       }
/*  7284 */       AMLog.debug("EMAIL PDF Reports  URL EMail PDF===> : " + reporturl);
/*  7285 */       URI uri = new URI(getHostPort(reporturl));
/*  7286 */       HTTPConnection con = new HTTPConnection(uri);
/*  7287 */       NVPair nvpair2 = new NVPair("j_username", "reportadmin");
/*  7288 */       NVPair nvpair3 = new NVPair("j_password", pwd);
/*  7289 */       NVPair[] formelements = new NVPair[2];
/*  7290 */       formelements[0] = nvpair2;
/*  7291 */       formelements[1] = nvpair3;
/*  7292 */       String tempuri = getURI(reporturl);
/*       */       
/*  7294 */       con.setDefaultHeaders(new NVPair[] { new NVPair("Referer", tempuri) });
/*  7295 */       String headerval = Constants.RY;
/*       */       
/*  7297 */       NVPair nvpair = new NVPair("Pragma", "no-cache");
/*  7298 */       NVPair nvpair4 = new NVPair("reportagent", headerval);
/*  7299 */       NVPair[] headers = new NVPair[2];
/*  7300 */       headers[0] = nvpair;
/*  7301 */       headers[1] = nvpair4;
/*  7302 */       HTTPResponse rsp1 = null;
/*  7303 */       if (AMAutomaticPortChanger.isSsoEnabled())
/*       */       {
/*  7305 */         SSORestClient ssorestclient = new SSORestClient("reportadmin", pwd);
/*       */         
/*  7307 */         String sticket = ssorestclient.generateServiceTicket(reporturl);
/*  7308 */         tempuri = tempuri + "&ticket=" + sticket;
/*  7309 */         rsp1 = con.Get(tempuri.trim(), "", headers);
/*       */       }
/*       */       else {
/*  7312 */         rsp1 = con.Post(tempuri.trim(), "", headers);
/*       */       }
/*       */       
/*  7315 */       byte[] wbresponse = rsp1.getData();
/*  7316 */       byte[] temp = new byte[wbresponse.length];
/*       */       
/*       */ 
/*  7319 */       System.arraycopy(wbresponse, 0, temp, 0, wbresponse.length);
/*  7320 */       String line = new String(temp);
/*  7321 */       responsecode = rsp1.getStatusCode();
/*  7322 */       if ((Constants.isIt360) && (resourceType != null) && (resourceType.indexOf("OpManager-") != -1))
/*       */       {
/*       */ 
/*       */ 
/*  7326 */         disname = "Networks";
/*  7327 */         displayname = "Networks";
/*       */       }
/*  7329 */       String fname = disname + "_" + periodfordisplay + "_" + reportname + "." + extension;
/*  7330 */       if (("generateHASnapShotReport".equals(aMethod)) || ("generateHASnapShotReportWithHostName".equals(aMethod))) {
/*  7331 */         fname = FormatUtil.getString("am.webclient.reports.emailpdf.filename.availabilitySnapshot.text") + "." + extension;
/*  7332 */       } else if ("generateAvailabilitySnapShotReport".equals(aMethod)) {
/*  7333 */         fname = FormatUtil.getString("am.webclient.reports.emailpdf.filename.availabilityHistory.text") + "." + extension;
/*       */       }
/*  7335 */       else if ("generateWeeklyMonthlyOutageReport".equals(aMethod)) {
/*  7336 */         fname = FormatUtil.getString("am.webclient.reports.emailpdf.filemname.outagecomparison.text") + "." + extension;
/*       */       }
/*  7338 */       else if ("generatePeriodAvailabilityReport".equals(aMethod)) {
/*  7339 */         fname = FormatUtil.getString("am.webclient.reports.emailpdf.filename.availabilitytrend.text") + "." + extension;
/*       */       }
/*  7341 */       else if ("generateUnderSizedMonitors".equals(aMethod)) {
/*  7342 */         fname = FormatUtil.getString("am.webclient.reports.emailpdf.filename.capacityplanning.text") + "_" + FormatUtil.getString(displayname) + "." + extension;
/*       */       }
/*  7344 */       else if ("generateIndividualReportCapacityPlanning".equals(aMethod)) {
/*  7345 */         fname = FormatUtil.getString("am.vmreports.capacityplanning.utilizationsummary") + "_" + FormatUtil.getString(displayname) + "." + extension;
/*       */       }
/*  7347 */       else if ("generateLicUsageReport".equals(aMethod)) {
/*  7348 */         Wield wield = Wield.getInstance();
/*  7349 */         String companyName = wield.getCompanyName();
/*  7350 */         fname = "LicenseUsage_" + companyName + "." + extension;
/*       */       }
/*  7352 */       if (line.indexOf("j_username") != -1)
/*       */       {
/*  7354 */         String urlforreport = protocol + "://" + host + ":" + port + "/j_security_check";
/*  7355 */         URI uri1 = new URI(getHostPort(urlforreport));
/*  7356 */         HTTPConnection con1 = new HTTPConnection(uri1);
/*       */         
/*  7358 */         String tempuri1 = getURI(urlforreport);
/*  7359 */         NVPair nvpair1 = new NVPair("Pragma", "no-cache");
/*  7360 */         NVPair[] headers1 = { nvpair1 };
/*       */         
/*  7362 */         HTTPResponse rsp2 = con1.Post(getURI(urlforreport), formelements, headers);
/*  7363 */         byte[] wbresponse1 = rsp2.getData();
/*  7364 */         byte[] temp1 = new byte[wbresponse1.length];
/*  7365 */         System.arraycopy(wbresponse1, 0, temp1, 0, wbresponse1.length);
/*       */         
/*       */ 
/*  7368 */         FileOutputStream fo1 = new FileOutputStream(absolutepath + File.separator + fname);
/*  7369 */         fo1.write(temp1);
/*  7370 */         fo1.flush();
/*  7371 */         filelist.add(absolutepath + File.separator + fname);
/*  7372 */         responsecode = rsp2.getStatusCode();
/*  7373 */         fo1.close();
/*       */       }
/*       */       else
/*       */       {
/*  7377 */         FileOutputStream fo = new FileOutputStream(absolutepath + File.separator + fname);
/*  7378 */         fo.write(temp);
/*  7379 */         fo.flush();
/*  7380 */         filelist.add(absolutepath + File.separator + fname);
/*  7381 */         fo.close();
/*       */       }
/*       */       try
/*       */       {
/*  7385 */         if (toadd != null)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*  7390 */           String mailContent = FormatUtil.findReplace(htmlmess, "~topheading~", FormatUtil.getString("am.webclient.managermail.schedulemail.topheading.text"));
/*  7391 */           String mailheading = FormatUtil.findReplace(mailContent, "~Name~", FormatUtil.getString("am.webclient.maintenance.schedulename"));
/*  7392 */           String startmail = FormatUtil.findReplace(mailheading, "~source~", displayname);
/*  7393 */           String attr = FormatUtil.findReplace(startmail, "~Report~", FormatUtil.getString("am.webclient.managermail.schedulemail.reportname.text"));
/*  7394 */           String messa = FormatUtil.findReplace(attr, "~attribute~", reportname);
/*  7395 */           String schper = FormatUtil.findReplace(messa, "~Period~", FormatUtil.getString("am.webclient.schedulereport.showschedule.reportperiod.text"));
/*  7396 */           String schmess = FormatUtil.findReplace(schper, "~message~", FormatUtil.getString(periodfordisplay));
/*  7397 */           String schgen = FormatUtil.findReplace(schmess, "~Time~", FormatUtil.getString("webclient.common.messagepage.description"));
/*  7398 */           String addInfoFill = FormatUtil.findReplace(schgen, "~addinfo~", addInfo);
/*  7399 */           String genat = FormatUtil.findReplace(addInfoFill, "~date~", comment);
/*  7400 */           String mytab = FormatUtil.findReplace(genat, "~mytable~", "");
/*  7401 */           String user = FormatUtil.findReplace(mytab, "~userinfo~", FormatUtil.getString("am.webclient.emailpdf.userinfo.text", new String[] { productName }));
/*  7402 */           String reportByFill = FormatUtil.findReplace(user, "~reportby~", reportBy);
/*  7403 */           reportByFill = FormatUtil.findReplace(reportByFill, "~protocol~", protocol);
/*  7404 */           String hostFilled = FormatUtil.findReplace(reportByFill, "~host~", host);
/*  7405 */           String portFilled = FormatUtil.findReplace(hostFilled, "~port~", port + "/showReports.do?actionMethod=getReportIndex");
/*       */           
/*       */ 
/*  7408 */           String productNameFill = null;
/*  7409 */           if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*       */           {
/*  7411 */             productNameFill = FormatUtil.findReplace(portFilled, "~product name~", OEMUtil.getOEMString("rebrand.product.name"));
/*       */           }
/*       */           else
/*       */           {
/*  7415 */             productNameFill = FormatUtil.findReplace(portFilled, "~product name~", OEMUtil.getOEMString("product.name"));
/*       */           }
/*       */           
/*  7418 */           periodfordisplay = FormatUtil.getString(periodfordisplay);
/*  7419 */           String sub = periodfordisplay + " " + reportname + " for " + displayname;
/*  7420 */           if (("generateHASnapShotReport".equals(aMethod)) || ("generateHASnapShotReportWithHostName".equals(aMethod))) {
/*  7421 */             sub = FormatUtil.getString("am.webclient.reports.emailpdf.availabilitySnapshot.text");
/*  7422 */           } else if ("generateAvailabilitySnapShotReport".equals(aMethod)) {
/*  7423 */             sub = FormatUtil.getString("am.webclient.reports.emailpdf.availabilityHistory.text");
/*       */           }
/*  7425 */           else if ("generateWeeklyMonthlyOutageReport".equals(aMethod)) {
/*  7426 */             sub = FormatUtil.getString("am.webclient.reports.emailpdf.outagecomparison.text");
/*       */           }
/*  7428 */           else if ("generatePeriodAvailabilityReport".equals(aMethod)) {
/*  7429 */             sub = FormatUtil.getString("am.webclient.reports.emailpdf.availabilitytrend.text");
/*       */           }
/*  7431 */           else if ("generateLicUsageReport".equals(aMethod)) {
/*  7432 */             Wield wield = Wield.getInstance();
/*  7433 */             String companyName = wield.getCompanyName();
/*  7434 */             sub = "License Usage for " + companyName;
/*       */             
/*  7436 */             String month = request.getParameter("month");
/*  7437 */             String year = request.getParameter("year");
/*  7438 */             String licDuration = request.getParameter("licDuration");
/*  7439 */             String fromMonth = request.getParameter("fromMonth");
/*  7440 */             String fromYear = request.getParameter("fromYear");
/*  7441 */             String toMonth = request.getParameter("toMonth");
/*  7442 */             String toYear = request.getParameter("toYear");
/*       */             
/*  7444 */             String licPeriodForMail = null;
/*       */             
/*  7446 */             if (licDuration != null)
/*       */             {
/*  7448 */               if ("lastMonth".equals(licDuration))
/*       */               {
/*  7450 */                 licPeriodForMail = month + "," + year;
/*       */ 
/*       */ 
/*       */               }
/*  7454 */               else if ((fromMonth != null) && (toMonth != null) && (fromMonth.equals(toMonth)) && (fromYear.equals(toYear)))
/*       */               {
/*  7456 */                 licPeriodForMail = fromMonth + "," + fromYear;
/*       */               }
/*       */               else
/*       */               {
/*  7460 */                 licPeriodForMail = fromMonth + "," + fromYear + " to " + toMonth + "," + toYear;
/*       */               }
/*       */             }
/*       */             
/*  7464 */             productNameFill = ReportUtil.getLicMailContent(licPeriodForMail, comment);
/*       */           }
/*  7466 */           SmtpMailer mailer = new SmtpMailer(from, toadd, "", sub);
/*  7467 */           String returnVal = mailer.sendMessage("", "Success", true, productNameFill, 0, null, filelist);
/*       */           
/*  7469 */           if (!returnVal.equalsIgnoreCase("Success")) {
/*  7470 */             successvalue = "false";
/*       */           }
/*       */           else {
/*  7473 */             successvalue = "true";
/*       */           }
/*       */         }
/*       */         else {
/*  7477 */           successvalue = "false";
/*       */         }
/*       */       }
/*       */       catch (Exception ef) {
/*  7481 */         successvalue = "false";
/*  7482 */         ef.printStackTrace();
/*       */ 
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (ConnectException ce)
/*       */     {
/*       */ 
/*  7491 */       System.out.println("*The Response code for EMail PDF is====>" + responsecode);
/*       */       
/*  7493 */       ce.printStackTrace();
/*       */     }
/*       */     catch (UnknownHostException ee)
/*       */     {
/*  7497 */       System.out.println("*The Response code for EMail PDF is====>" + responsecode);
/*  7498 */       reason = FormatUtil.getString("am.webclient.urlavailability.hostunavailablereason.text");
/*  7499 */       System.out.println("*The Reason is====>" + reason);
/*       */ 
/*       */     }
/*       */     catch (NoRouteToHostException nre)
/*       */     {
/*  7504 */       System.out.println("*The Response code for EMail PDF is====>" + responsecode);
/*  7505 */       reason = FormatUtil.getString("am.webclient.urlavailability.networksettingsreason.text");
/*  7506 */       System.out.println("*The Reason is====>" + reason);
/*       */ 
/*       */     }
/*       */     catch (InterruptedIOException soc)
/*       */     {
/*  7511 */       System.out.println("*The Response code for EMail PDF is====>" + responsecode);
/*  7512 */       reason = FormatUtil.getString("am.webclient.urlavailability.connectiontimeoutreason.text");
/*  7513 */       System.out.println("*The Reason is====>" + reason);
/*       */     }
/*       */     catch (ProtocolException pr)
/*       */     {
/*  7517 */       System.out.println("*The Response code for EMail PDF is====>" + responsecode);
/*  7518 */       reason = FormatUtil.getString("am.webclient.urlavailability.protocalexceptionreason.text");
/*  7519 */       System.out.println("*The Reason is====>" + reason);
/*       */     }
/*       */     catch (EOFException e)
/*       */     {
/*  7523 */       e.printStackTrace();
/*       */ 
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*       */ 
/*  7529 */       e.printStackTrace();
/*  7530 */       String message = e.getMessage();
/*  7531 */       System.out.println("*The Response code for EMail PDF is====>" + responsecode);
/*  7532 */       if ((message != null) && (message.startsWith("Network is unreachable."))) {
/*  7533 */         reason = FormatUtil.getString("am.webclient.urlavailability.networksettingsreason.text");
/*  7534 */         System.out.println("*The Reason is====>" + reason);
/*       */       }
/*       */       else
/*       */       {
/*  7538 */         reason = FormatUtil.getString("am.webclient.urlavailability.exceptionreason.text");
/*  7539 */         System.out.println("*The Reason is====>" + reason);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  7546 */     return new ActionForward("/jsp/Popup_EmailPDF.jsp?sucess=" + successvalue);
/*       */   }
/*       */   
/*       */   public String getResourceTypeValueForEmailPDF(String restype)
/*       */   {
/*  7551 */     AMLog.debug("Inside getResourceTypeValueForEmailPDF ::" + restype);
/*       */     try {
/*  7553 */       Hashtable allservers = ReportUtil.getserversTypes();
/*  7554 */       if (allservers != null) {
/*  7555 */         if (restype.equalsIgnoreCase((String)allservers.get("Application servers"))) {
/*  7556 */           return "Application servers";
/*       */         }
/*  7558 */         if (restype.equalsIgnoreCase((String)allservers.get("Database Servers"))) {
/*  7559 */           return "Database Servers";
/*       */         }
/*  7561 */         if (restype.equalsIgnoreCase((String)allservers.get("Servers"))) {
/*  7562 */           return "Servers";
/*       */         }
/*  7564 */         if (restype.equalsIgnoreCase((String)allservers.get("Web Services"))) {
/*  7565 */           return "Web Services";
/*       */         }
/*  7567 */         if (restype.equalsIgnoreCase((String)allservers.get("Mail Servers"))) {
/*  7568 */           return "Mail Servers";
/*       */         }
/*  7570 */         if (restype.equalsIgnoreCase((String)allservers.get("Services"))) {
/*  7571 */           return "Services";
/*       */         }
/*  7573 */         if (restype.equalsIgnoreCase((String)allservers.get("Middleware Servers"))) {
/*  7574 */           return "Middleware Servers";
/*       */         }
/*  7576 */         if (restype.equalsIgnoreCase((String)allservers.get("ERP Servers"))) {
/*  7577 */           return "ERP Servers";
/*       */         }
/*  7579 */         if (restype.equalsIgnoreCase((String)allservers.get("URLs-Web Apps"))) {
/*  7580 */           return "URLs-Web Apps";
/*       */         }
/*  7582 */         if (restype.equalsIgnoreCase((String)allservers.get("Web Servers"))) {
/*  7583 */           return "Web Servers";
/*       */         }
/*  7585 */         if (restype.equalsIgnoreCase("JDK1.5")) {
/*  7586 */           return "Java-Transactions";
/*       */         }
/*  7588 */         if (restype.equalsIgnoreCase((String)allservers.get("Virtualization"))) {
/*  7589 */           return "Virtual Servers";
/*       */         }
/*       */         
/*  7592 */         if (restype.equalsIgnoreCase((String)allservers.get("Cloud Apps")))
/*       */         {
/*  7594 */           return "Cloud Apps";
/*       */         }
/*       */         
/*       */ 
/*  7598 */         return restype;
/*       */       }
/*       */       
/*       */ 
/*  7602 */       return restype;
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  7606 */       e.printStackTrace();
/*  7607 */       AMLog.debug("AMReportActions allservers got from ReportUtil.getserversTypes is null"); }
/*  7608 */     return restype;
/*       */   }
/*       */   
/*       */ 
/*       */   public String getReportNameForEmailPDF(String amethod)
/*       */   {
/*  7614 */     if ((amethod.equalsIgnoreCase("generateAvailabilityReport")) || (amethod.equalsIgnoreCase("generateHAAvailabilityReport")))
/*  7615 */       return "AvailabilityReport";
/*  7616 */     if ((amethod.equalsIgnoreCase("generateHAHealthReport")) || (amethod.equalsIgnoreCase("generateHealthReport")))
/*  7617 */       return "HealthReport";
/*  7618 */     if ((amethod.equalsIgnoreCase("generateHAResponseTimeReport")) || (amethod.equalsIgnoreCase("generateAttributeReport")) || (amethod.equalsIgnoreCase("generateTrendReport")))
/*       */     {
/*  7620 */       return FormatUtil.getString("am.webclient.reports.email.attributereport.name");
/*       */     }
/*  7622 */     if (amethod.equalsIgnoreCase("generateEventSummary"))
/*       */     {
/*  7624 */       return FormatUtil.getString("am.webclient.reports.emailpdf.filename.eventreport.text");
/*       */     }
/*  7626 */     if ((amethod.equalsIgnoreCase("generateHASnapShotReport")) || (amethod.equalsIgnoreCase("generateHASnapShotReportWithHostName")))
/*  7627 */       return FormatUtil.getString("am.webclient.reports.emailpdf.filename.availabilitySnapshot.text");
/*  7628 */     if (amethod.equalsIgnoreCase("generateAvailabilitySnapShotReport"))
/*  7629 */       return FormatUtil.getString("am.webclient.reports.emailpdf.filename.availabilityHistory.text");
/*  7630 */     if (amethod.equalsIgnoreCase("generateWeeklyMonthlyOutageReport"))
/*  7631 */       return FormatUtil.getString("am.webclient.reports.emailpdf.filemname.outagecomparison.text");
/*  7632 */     if (amethod.equalsIgnoreCase("generatePeriodAvailabilityReport"))
/*  7633 */       return FormatUtil.getString("am.webclient.reports.emailpdf.filename.availabilitytrend.text");
/*  7634 */     if (amethod.equalsIgnoreCase("generateMttrAvailablityReport"))
/*  7635 */       return "DowntimeReport";
/*  7636 */     if (amethod.equalsIgnoreCase("generateSummaryReport"))
/*  7637 */       return "SummaryReport";
/*  7638 */     if (amethod.equalsIgnoreCase("generateCustomAttributeReport"))
/*  7639 */       return "CustomReport";
/*  7640 */     if ((amethod.equalsIgnoreCase("generateGlanceReport")) || (amethod.equalsIgnoreCase("generateIndividualGlanceReport")))
/*       */     {
/*  7642 */       return FormatUtil.getString("am.webclient.reports.ataglance.report");
/*       */     }
/*  7644 */     if (amethod.equals("generateUnderSizedMonitors"))
/*       */     {
/*       */ 
/*  7647 */       return FormatUtil.getString("am.webclient.reports.emailpdf.filename.capacityplanning.text");
/*       */     }
/*  7649 */     if (amethod.equals("generateIndividualReportCapacityPlanning"))
/*       */     {
/*  7651 */       return FormatUtil.getString("am.vmreports.capacityplanning.utilizationsummary");
/*       */     }
/*  7653 */     if (amethod.equals("generatePeriodAvailabilityDowntimeReport"))
/*       */     {
/*  7655 */       return FormatUtil.getString("am.webclient.reports.emailpdf.filename.availabilitydowntrend.text");
/*       */     }
/*  7657 */     if (amethod.equals("showEumSummaryReport"))
/*       */     {
/*  7659 */       return FormatUtil.getString("am.webclient.eum.report.location");
/*       */     }
/*  7661 */     if (amethod.equals("generateMSSQLPerformanceReport"))
/*       */     {
/*  7663 */       return FormatUtil.getString("am.reporttab.mssql.reports.performancereport.text");
/*       */     }
/*  7665 */     if (amethod.equals("generateMSSQLGeneralDetailsReport"))
/*       */     {
/*  7667 */       return FormatUtil.getString("am.reporttab.mssql.reports.mssqlgeneraldetailsreport.text");
/*       */     }
/*  7669 */     if (amethod.equals("generateLicUsageReport"))
/*       */     {
/*  7671 */       return "License Usage Report";
/*       */     }
/*       */     
/*       */ 
/*  7675 */     return null;
/*       */   }
/*       */   
/*       */   private static String getHostPort(String url) {
/*  7679 */     StringTokenizer tokens = new StringTokenizer(url, "/");
/*  7680 */     String http = tokens.nextToken();
/*  7681 */     String hostport = tokens.nextToken();
/*  7682 */     return http + "//" + hostport + "/";
/*       */   }
/*       */   
/*       */   private static String getURI(String url) {
/*  7686 */     StringTokenizer tokens = new StringTokenizer(url, "/");
/*  7687 */     StringBuffer uri = new StringBuffer();
/*  7688 */     int i = 0;
/*  7689 */     for (i = 0; tokens.hasMoreTokens(); i++)
/*  7690 */       if (i < 2) {
/*  7691 */         tokens.nextToken();
/*       */       }
/*       */       else {
/*  7694 */         uri.append("/");
/*  7695 */         uri.append(tokens.nextToken());
/*       */       }
/*  7697 */     if (i < 2) {
/*  7698 */       uri = new StringBuffer("/");
/*       */     }
/*  7700 */     return uri.toString();
/*       */   }
/*       */   
/*  7703 */   private String getLimitCondition(ReportForm frm) { int num = frm.getNumberOfRows();
/*  7704 */     if (num != -1) {
/*  7705 */       return " limit " + num;
/*       */     }
/*  7707 */     return "";
/*       */   }
/*       */   
/*       */   private static String getHTMLMailTpl()
/*       */   {
/*       */     try {
/*  7713 */       return FormatUtil.getContentsAsString("./conf/SchedulerMail.html");
/*       */     }
/*       */     catch (IOException io) {
/*  7716 */       System.out.println("Comparing : Problem encountered when trying to form the HTML Mail template"); }
/*  7717 */     return "error in sending mail";
/*       */   }
/*       */   
/*       */   public static String getHTMLMailTemplate()
/*       */   {
/*  7722 */     return htmlMailTpl;
/*       */   }
/*       */   
/*       */ 
/*       */   public String getMonitorHelpKey(String resType)
/*       */   {
/*  7728 */     String ret = null;
/*  7729 */     return (String)helpKeyMap.get(resType);
/*       */   }
/*       */   
/*       */   private boolean areMonitorsPresent(String resourceType) throws Exception
/*       */   {
/*  7734 */     Properties typeDescription = ConfMonitorConfiguration.getInstance().getTypeDescription(resourceType);
/*  7735 */     resourceType = typeDescription != null ? typeDescription.getProperty("Type") : resourceType;
/*  7736 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  7737 */     String query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "')";
/*  7738 */     ResultSet set = AMConnectionPool.executeQueryStmt(query);
/*  7739 */     boolean areMonitorsPresent = false;
/*  7740 */     if (set.next()) {
/*  7741 */       areMonitorsPresent = true;
/*       */     }
/*       */     
/*  7744 */     closeResultSet(set);
/*  7745 */     return areMonitorsPresent;
/*       */   }
/*       */   
/*       */   private List getDataCollectionStartedMonitors(String resourceType) throws Exception
/*       */   {
/*  7750 */     return getDataCollectionStartedMonitors(resourceType, true);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   private List getDataCollectionStartedMonitors(String resourceType, boolean isResourceType)
/*       */     throws Exception
/*       */   {
/*  7759 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  7760 */     Properties typeDescription = ConfMonitorConfiguration.getInstance().getTypeDescription(resourceType);
/*  7761 */     resourceType = typeDescription != null ? typeDescription.getProperty("Type") : resourceType;
/*  7762 */     String query = "select DISTINCT(AM_ManagedObjectData.RESID) from AM_ManagedObjectData, AM_ManagedObject, AM_ManagedResourceType where AM_ManagedObjectData.RESID=AM_ManagedObject.RESOURCEID and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and ";
/*       */     
/*  7764 */     if (!isResourceType)
/*       */     {
/*  7766 */       query = query + " " + resourceType;
/*       */     }
/*       */     else
/*       */     {
/*  7770 */       query = query + "AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "')";
/*       */     }
/*       */     
/*  7773 */     AMLog.debug(" ReportsAction getDataCollectionStartedMonitors query " + query);
/*  7774 */     ResultSet set = AMConnectionPool.executeQueryStmt(query);
/*  7775 */     List toRet = new ArrayList();
/*  7776 */     while (set.next()) {
/*  7777 */       toRet.add(String.valueOf(set.getInt("RESID")));
/*       */     }
/*  7779 */     closeResultSet(set);
/*  7780 */     if (EnterpriseUtil.isAdminServer())
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*  7785 */       query = "select DISTINCT(RESOURCEID) AS RESID from AM_ManagedObject, AM_ManagedResourceType  where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and ";
/*       */       
/*  7787 */       if (!isResourceType)
/*       */       {
/*  7789 */         query = query + " " + resourceType;
/*       */       }
/*       */       else
/*       */       {
/*  7793 */         query = query + "AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "')";
/*       */       }
/*  7795 */       AMLog.debug(" ReportsAction getDataCollectionStartedMonitors query_1 " + query);
/*  7796 */       set = AMConnectionPool.executeQueryStmt(query);
/*  7797 */       String tmp = "";
/*  7798 */       while (set.next()) {
/*  7799 */         tmp = String.valueOf(set.getInt("RESID"));
/*  7800 */         if (!toRet.contains(tmp)) {
/*  7801 */           toRet.add(tmp);
/*       */         }
/*       */       }
/*  7804 */       closeResultSet(set);
/*       */     }
/*       */     
/*       */ 
/*  7808 */     return toRet;
/*       */   }
/*       */   
/*       */   private List getCreationTimeForResourceTypes(String resourceType) throws Exception
/*       */   {
/*  7813 */     List toRet = new ArrayList();
/*  7814 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  7815 */     ResultSet set = null;
/*  7816 */     String query = "select DISTINCT(AM_ManagedObject.RESOURCEID) from AM_ManagedObject, AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "')";
/*       */     try {
/*  7818 */       set = AMConnectionPool.executeQueryStmt(query);
/*       */       
/*  7820 */       while (set.next()) {
/*  7821 */         toRet.add(String.valueOf(set.getInt("RESOURCEID")));
/*       */       }
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/*  7826 */       ee.printStackTrace();
/*       */     }
/*  7828 */     closeResultSet(set);
/*  7829 */     return toRet;
/*       */   }
/*       */   
/*       */   private boolean hasAnyData(String resourceType) throws Exception {
/*  7833 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  7834 */     String query = getTopNValues("select AM_ManagedObjectHistoryData.RESID from AM_ManagedObjectHistoryData, AM_ManagedObject, AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.RESOURCEID=AM_ManagedObjectHistoryData.RESID and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "') ", "1");
/*  7835 */     ResultSet set = AMConnectionPool.executeQueryStmt(query);
/*  7836 */     boolean hasAnyData = false;
/*  7837 */     if (set.next()) {
/*  7838 */       hasAnyData = true;
/*       */     }
/*       */     
/*  7841 */     closeResultSet(set);
/*  7842 */     return hasAnyData;
/*       */   }
/*       */   
/*       */   private List getDataCollectionStartedMonitorsForHaid(String haid)
/*       */     throws Exception
/*       */   {
/*  7848 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  7849 */     String query = "select DISTINCT(AM_ManagedObject.RESOURCEID) from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*  7850 */     ResultSet set = AMConnectionPool.executeQueryStmt(query);
/*  7851 */     List toRet = new ArrayList();
/*  7852 */     while (set.next()) {
/*  7853 */       toRet.add(String.valueOf(set.getInt("RESOURCEID")));
/*       */     }
/*  7855 */     closeResultSet(set);
/*  7856 */     return toRet;
/*       */   }
/*       */   
/*       */   private List getDataCollectionStartedMonitorsForHaid(String haid, boolean drilldownMGs) throws Exception {
/*  7860 */     List toRet = new ArrayList();
/*       */     try {
/*  7862 */       Vector haids = new Vector();
/*  7863 */       haids.add(haid);
/*  7864 */       DBUtil.getChildIDs(haids, haid);
/*  7865 */       for (int i = 0; (haids != null) && (i < haids.size()); i++)
/*       */       {
/*  7867 */         String temphaid = (String)haids.get(i);
/*  7868 */         List singlelist = getDataCollectionStartedMonitorsForHaid(temphaid);
/*  7869 */         for (int j = 0; j < singlelist.size(); j++)
/*       */         {
/*  7871 */           toRet.add(singlelist.get(j));
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  7877 */       ex.printStackTrace();
/*       */     }
/*  7879 */     return toRet;
/*       */   }
/*       */   
/*       */   private ArrayList addMGTreetoMonitorName(ArrayList data, Vector nametreelist, ArrayList residsinorder, String attribute)
/*       */   {
/*  7884 */     ArrayList tempdata = new ArrayList();
/*  7885 */     HashMap moNames = new HashMap();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  7896 */       Hashtable temptable = new Hashtable();
/*  7897 */       for (int l = 0; l < data.size(); l++)
/*       */       {
/*  7899 */         String tree = "";
/*  7900 */         if ((attribute.equals("Availability")) || (attribute.equals("Health")))
/*       */         {
/*  7902 */           Hashtable temphash = (Hashtable)data.get(l);
/*  7903 */           String tempresid = (String)temphash.get("resourceid");
/*  7904 */           String actualName = (String)temphash.get("Name");
/*  7905 */           moNames.put(tempresid + "", actualName);
/*  7906 */           temptable.put("" + tempresid, temphash);
/*       */ 
/*       */         }
/*  7909 */         else if (attribute.equals("HASnapShotReport"))
/*       */         {
/*  7911 */           ArrayList singlerow = (ArrayList)data.get(l);
/*  7912 */           int resid = Integer.parseInt((String)singlerow.get(1));
/*  7913 */           moNames.put(resid + "", singlerow.get(0));
/*  7914 */           temptable.put("" + resid, singlerow);
/*       */         }
/*       */         else
/*       */         {
/*  7918 */           ArrayList singlerow = (ArrayList)data.get(l);
/*  7919 */           int resid = ((Integer)singlerow.get(4)).intValue();
/*  7920 */           moNames.put(resid + "", singlerow.get(0));
/*  7921 */           temptable.put("" + resid, singlerow);
/*       */         }
/*       */       }
/*  7924 */       for (int i = 0; i < residsinorder.size(); i++)
/*       */       {
/*  7926 */         String resid = (String)residsinorder.get(i);
/*  7927 */         if ((attribute.equals("Availability")) || (attribute.equals("Health")))
/*       */         {
/*  7929 */           if (temptable.get("" + resid) != null)
/*       */           {
/*  7931 */             Hashtable temphash = (Hashtable)temptable.get(resid);
/*  7932 */             Hashtable clonedObject = (Hashtable)temphash.clone();
/*  7933 */             String actualName = (String)moNames.get(resid);
/*  7934 */             clonedObject.put("Name", nametreelist.get(i) + actualName);
/*  7935 */             tempdata.add(clonedObject);
/*       */           }
/*       */         }
/*  7938 */         else if (attribute.equals("HASnapShotReport"))
/*       */         {
/*  7940 */           String resid1 = (String)residsinorder.get(i);
/*  7941 */           if (temptable.get("" + resid1) != null)
/*       */           {
/*  7943 */             ArrayList singlerow = (ArrayList)temptable.get(resid1);
/*  7944 */             ArrayList clonedObject = (ArrayList)singlerow.clone();
/*  7945 */             String actualName = (String)moNames.get(resid);
/*  7946 */             clonedObject.set(0, nametreelist.get(i) + actualName);
/*  7947 */             tempdata.add(clonedObject);
/*       */ 
/*       */           }
/*       */           
/*       */ 
/*       */         }
/*  7953 */         else if (temptable.get("" + resid) != null)
/*       */         {
/*  7955 */           ArrayList singlerow = (ArrayList)temptable.get(resid);
/*  7956 */           String actualName = (String)moNames.get(resid);
/*  7957 */           ArrayList clonedObject = (ArrayList)singlerow.clone();
/*  7958 */           clonedObject.set(0, nametreelist.get(i) + actualName);
/*  7959 */           tempdata.add(clonedObject);
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  7968 */       ex.printStackTrace();
/*  7969 */       return null;
/*       */     }
/*  7971 */     return tempdata;
/*       */   }
/*       */   
/*       */ 
/*       */   private boolean addAllMonitorsinSubGroups(Vector nametreelist, ArrayList residsinorder, Vector resourceids, String haid, boolean processChildMos)
/*       */   {
/*  7977 */     if ((DBQueryUtil.isMssql()) || (DBQueryUtil.isPgsql())) {
/*  7978 */       return addAllMonitorsinSubGroups_1(nametreelist, residsinorder, resourceids, haid, processChildMos);
/*       */     }
/*  7980 */     return addAllMonitorsinSubGroups_2(nametreelist, residsinorder, resourceids, haid, processChildMos);
/*       */   }
/*       */   
/*       */ 
/*       */   private boolean addAllMonitorsinSubGroups_1(Vector nametreelist, ArrayList residsinorder, Vector resourceids, String haid, boolean processChildMos)
/*       */   {
/*  7986 */     ResultSet rs = null;
/*  7987 */     boolean to_return = false;
/*       */     try {
/*  7989 */       LinkedHashMap orderedMap = new LinkedHashMap();
/*  7990 */       StringBuilder builder = new StringBuilder();
/*  7991 */       if (DBQueryUtil.isMssql()) {
/*  7992 */         builder.append("WITH CHILDRESOURCES_CTE(PARENTRESOURCEID,PARENTTYPE,PARENTRESOURCENAME,CHILDRESOURCEID,CHILDTYPE,CHILDRESOURCENAME,DISPLAYNAME,TREENAME) AS (");
/*       */       } else {
/*  7994 */         builder.append("WITH RECURSIVE CHILDRESOURCES_CTE(PARENTRESOURCEID,PARENTTYPE,PARENTRESOURCENAME,CHILDRESOURCEID,CHILDTYPE,CHILDRESOURCENAME,DISPLAYNAME,TREENAME) AS (");
/*       */       }
/*  7996 */       builder.append("SELECT DISTINCT AM_ManagedObject.RESOURCEID AS PARENTRESOURCEID, AM_ManagedObject.TYPE AS PARENTTYPE,AM_ManagedObject.RESOURCENAME AS PARENTRESOURCENAME,AM_ManagedObject.RESOURCEID AS CHILDRESOURCEID, AM_ManagedObject.TYPE AS CHILDTYPE,AM_ManagedObject.RESOURCENAME AS CHILDRESOURCENAME,DISPLAYNAME,DISPLAYNAME FROM AM_ManagedObject,AM_PARENTCHILDMAPPER WHERE AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID AND AM_ManagedObject.TYPE='HAI' AND AM_PARENTCHILDMAPPER.PARENTID=").append(haid);
/*  7997 */       builder.append(" UNION ALL ");
/*       */       
/*       */ 
/*  8000 */       if (DBQueryUtil.isMssql()) {
/*  8001 */         builder.append("SELECT parentmo.RESOURCEID AS RESOURCEID,parentmo.TYPE AS TYPE,parentmo.RESOURCENAME AS RESOURCENAME,childmo.RESOURCEID AS RESOURCEID1,childmo.TYPE AS TYPE1,childmo.RESOURCENAME aS RESOURCENAME1,childmo.DISPLAYNAME,CAST(((CHILDRESOURCES_CTE.TREENAME + ('->' + childmo.RESOURCENAME))) AS VARCHAR(515)) AS TREENAME1 FROM AM_PARENTCHILDMAPPER INNER JOIN AM_ManagedObject parentmo on AM_PARENTCHILDMAPPER.PARENTID = parentmo.RESOURCEID INNER JOIN AM_ManagedObject childmo on AM_PARENTCHILDMAPPER.CHILDID= childmo.RESOURCEID INNER JOIN CHILDRESOURCES_CTE ON CHILDRESOURCES_CTE.CHILDRESOURCEID = AM_PARENTCHILDMAPPER.PARENTID where CHILDRESOURCES_CTE.CHILDTYPE='HAI')");
/*       */       }
/*       */       else
/*       */       {
/*  8005 */         builder.append("SELECT parentmo.RESOURCEID AS RESOURCEID,parentmo.TYPE AS TYPE,parentmo.RESOURCENAME AS RESOURCENAME,childmo.RESOURCEID AS RESOURCEID1,childmo.TYPE AS TYPE1,childmo.RESOURCENAME aS RESOURCENAME1,childmo.DISPLAYNAME,CAST((CONCAT(CHILDRESOURCES_CTE.TREENAME,CONCAT('-> ',childmo.DISPLAYNAME))) AS VARCHAR(515)) AS TREENAME1 FROM AM_PARENTCHILDMAPPER INNER JOIN AM_ManagedObject parentmo on AM_PARENTCHILDMAPPER.PARENTID = parentmo.RESOURCEID INNER JOIN AM_ManagedObject childmo on AM_PARENTCHILDMAPPER.CHILDID= childmo.RESOURCEID INNER JOIN CHILDRESOURCES_CTE ON CHILDRESOURCES_CTE.CHILDRESOURCEID = AM_PARENTCHILDMAPPER.PARENTID where CHILDRESOURCES_CTE.CHILDTYPE='HAI')");
/*       */       }
/*  8007 */       builder.append(" SELECT PARENTRESOURCEID,PARENTTYPE,PARENTRESOURCENAME,CHILDRESOURCEID,CHILDTYPE,CHILDRESOURCENAME,TREENAME FROM CHILDRESOURCES_CTE WHERE CHILDRESOURCEID!=").append(haid).append(" ORDER BY TREENAME ASC");
/*  8008 */       StartUtil.printStr("AMReportActions.addAllMonitorsinSubGroups_1:Query ===> " + builder);
/*  8009 */       rs = AMConnectionPool.executeQueryStmt(builder.toString());
/*  8010 */       String parentResId; while (rs.next()) {
/*  8011 */         parentResId = rs.getString(1);
/*  8012 */         String childResId = rs.getString(4);
/*  8013 */         String childResType = rs.getString(5);
/*  8014 */         String treeName = rs.getString(7);
/*  8015 */         ArrayList al1 = (ArrayList)orderedMap.get(parentResId);
/*  8016 */         if (al1 == null) {
/*  8017 */           al1 = new ArrayList();
/*  8018 */           orderedMap.put(parentResId, al1);
/*       */         }
/*  8020 */         ArrayList al2 = new ArrayList();
/*  8021 */         al2.add(childResId);
/*  8022 */         al2.add(childResType);
/*  8023 */         al2.add(treeName);
/*  8024 */         al1.add(al2);
/*       */       }
/*  8026 */       if (orderedMap.size() == 0) {
/*  8027 */         return 0;
/*       */       }
/*  8029 */       Iterator itr = orderedMap.keySet().iterator();
/*  8030 */       while (itr.hasNext()) {
/*  8031 */         String parentGrpId = (String)itr.next();
/*  8032 */         ArrayList al1 = (ArrayList)orderedMap.get(parentGrpId);
/*  8033 */         String treeName = "";
/*  8034 */         for (int i = 0; i < al1.size(); i++) {
/*  8035 */           ArrayList inner = (ArrayList)al1.get(i);
/*  8036 */           residsinorder.add((String)inner.get(0));
/*  8037 */           if (Integer.parseInt(haid) == Integer.parseInt(parentGrpId)) {
/*  8038 */             nametreelist.add("");
/*       */           } else {
/*  8040 */             to_return = true;
/*  8041 */             treeName = (String)inner.get(2);
/*  8042 */             treeName = treeName.substring(treeName.indexOf("->") + 3);
/*  8043 */             int idx = treeName.lastIndexOf("->");
/*  8044 */             if (idx == -1) {
/*  8045 */               idx = treeName.length() - 1;
/*       */             }
/*  8047 */             treeName = treeName.substring(0, idx);
/*  8048 */             nametreelist.add(treeName + "->");
/*       */           }
/*  8050 */           if (processChildMos) {
/*  8051 */             resourceids.add((String)inner.get(0));
/*  8052 */           } else if (((String)inner.get(1)).equals("HAI")) {
/*  8053 */             resourceids.add((String)inner.get(0));
/*       */           }
/*       */         }
/*  8056 */         residsinorder.add(parentGrpId);
/*  8057 */         if (Integer.parseInt(haid) == Integer.parseInt(parentGrpId)) {
/*  8058 */           nametreelist.add("");
/*       */         } else {
/*  8060 */           nametreelist.add(treeName + "->");
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ex) {
/*  8065 */       ex.printStackTrace();
/*       */     } finally {
/*  8067 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*  8069 */     return to_return;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private boolean addAllMonitorsinSubGroups_2(Vector nametreelist, ArrayList residsinorder, Vector resourceids, String haid, boolean processChildMos)
/*       */   {
/*  8078 */     boolean hasSubGroups = false;
/*       */     try {
/*  8080 */       Vector tempvector = new Vector();
/*  8081 */       Vector treelist = new Vector();
/*  8082 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  8083 */       String MGtree = "";
/*  8084 */       ReportUtilities.getMGtreeforMonitors(tempvector, treelist, haid, MGtree);
/*  8085 */       int subgroupnumber = 0;
/*  8086 */       int vectorSize = tempvector.size();
/*  8087 */       if ((vectorSize > 0) && 
/*  8088 */         (vectorSize > subgroupnumber)) {
/*  8089 */         subgroupnumber = vectorSize;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  8094 */       if ((tempvector != null) && (tempvector.size() > 0))
/*       */       {
/*  8096 */         hasSubGroups = true;
/*       */       }
/*       */       
/*       */ 
/*  8100 */       Vector tempresidsinorder = new Vector();
/*  8101 */       ReportUtilities.getChildIDs(tempresidsinorder, Integer.parseInt(haid));
/*  8102 */       if ((tempresidsinorder != null) && (tempresidsinorder.size() > 0))
/*       */       {
/*  8104 */         for (int k = 0; k < tempresidsinorder.size(); k++)
/*       */         {
/*  8106 */           residsinorder.add(tempresidsinorder.get(k));
/*  8107 */           nametreelist.add("");
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  8112 */       for (int i = 0; i < tempvector.size(); i++)
/*       */       {
/*  8114 */         String subgroupid = (String)tempvector.get(i);
/*  8115 */         resourceids.add(subgroupid);
/*  8116 */         Vector tempresourceids = new Vector();
/*  8117 */         ReportUtilities.getChildIDs(tempresourceids, Integer.parseInt(subgroupid));
/*  8118 */         for (int j = 0; j < tempresourceids.size(); j++)
/*       */         {
/*  8120 */           if (processChildMos)
/*       */           {
/*  8122 */             resourceids.add(tempresourceids.get(j));
/*       */           }
/*  8124 */           residsinorder.add(tempresourceids.get(j));
/*  8125 */           nametreelist.add(treelist.get(i));
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  8131 */       ex.printStackTrace();
/*       */     }
/*  8133 */     return hasSubGroups;
/*       */   }
/*       */   
/*       */   public ActionForward generateAvailabilitySnapShotReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*  8138 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  8139 */     ActionMessages messages = new ActionMessages();
/*  8140 */     ResultSet set = null;
/*       */     try {
/*  8142 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*       */       
/*       */ 
/*  8145 */       ReportForm controls = (ReportForm)form;
/*       */       
/*  8147 */       String period = controls.getPeriod();
/*  8148 */       String businessRule = controls.getBusinessPeriod();
/*       */       
/*  8150 */       getCustomApplications(request, "3");
/*  8151 */       getHolisticApps(mapping, form, request, response);
/*  8152 */       getMonitors(mapping, form, request, response);
/*       */       
/*  8154 */       String haid = controls.getHaid();
/*  8155 */       String fromSchedule = request.getParameter("isschedule");
/*  8156 */       long[] timeStamps = null;
/*  8157 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*  8158 */       String nodata = "report.nodata";
/*  8159 */       if ((controls.getStartDate() == null) || (controls.getStartDate().equals("")) || (controls.getEndDate().equals("")))
/*       */       {
/*  8161 */         timeStamps = ReportUtilities.getTimeStamp(period);
/*       */       }
/*       */       else
/*       */       {
/*  8165 */         controls.setPeriod("4");
/*  8166 */         period = "4";
/*       */         try {
/*  8168 */           timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */         } catch (IllegalArgumentException iae) {
/*  8170 */           String errMsg = iae.getMessage();
/*  8171 */           AMLog.debug("Reports :  " + errMsg);
/*  8172 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  8173 */           saveMessages(request, messages);
/*  8174 */           request.setAttribute("heading", "0");
/*  8175 */           request.setAttribute("strTime", "0");
/*  8176 */           return mapping.findForward("report.message");
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  8181 */       long startTime = timeStamps[0];
/*  8182 */       long endTime = timeStamps[1];
/*  8183 */       ArrayList allHaids = new ArrayList();
/*  8184 */       ArrayList haSnapshot = new ArrayList();
/*  8185 */       Vector v = new Vector();
/*  8186 */       ArrayList als = null;
/*       */       
/*  8188 */       boolean hasAnyData = false;
/*  8189 */       String reportByCustomField = controls.getCustomfield();
/*  8190 */       String customfield = controls.getCustomFieldValue();
/*  8191 */       ResultSet haidset = null;
/*  8192 */       String haidquery = null;
/*       */       
/*  8194 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy, HH:mm");
/*  8195 */       java.util.Date resultdate = new java.util.Date(startTime);
/*  8196 */       java.util.Date resultdate1 = new java.util.Date(endTime);
/*       */       
/*       */ 
/*  8199 */       Properties timeprops = new Properties();
/*  8200 */       timeprops.setProperty("from", sdf.format(resultdate));
/*  8201 */       timeprops.setProperty("to", sdf.format(resultdate1));
/*  8202 */       request.setAttribute("timeprops", timeprops);
/*       */       
/*  8204 */       if ((("all".equals(haid)) || ("allmonitors".equals(haid))) && (!reportByCustomField.equals("true"))) {
/*       */         try {
/*  8206 */           if ((privilegedUser) && (!EnterpriseUtil.isIt360MSPEdition())) {
/*  8207 */             haidquery = getHaidResourceQuery(request);
/*       */           } else {
/*  8209 */             String bsgFilterCondn = "";
/*  8210 */             String bsgType = "0";
/*  8211 */             if (EnterpriseUtil.isIt360MSPEdition())
/*       */             {
/*  8213 */               bsgType = "1";
/*  8214 */               Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  8215 */               bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */             }
/*  8217 */             haidquery = "select HAID,DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + " order by DISPLAYNAME";
/*       */           }
/*       */           
/*  8220 */           haidset = AMConnectionPool.executeQueryStmt(haidquery);
/*  8221 */           ArrayList subgroupNumber = new ArrayList();
/*  8222 */           subgroupNumber.add("2");
/*       */           
/*  8224 */           while (haidset.next()) {
/*  8225 */             String highlevelhaid = haidset.getString("HAID");
/*  8226 */             String highleveldispname = haidset.getString("DISPLAYNAME");
/*       */             
/*  8228 */             allHaids.add(highlevelhaid);
/*       */           }
/*       */         }
/*       */         catch (Exception ex) {
/*  8232 */           ex.printStackTrace();
/*       */ 
/*       */         }
/*       */         finally {}
/*       */ 
/*       */       }
/*  8238 */       else if ((customfield != null) && (customfield.indexOf("$") != -1) && (reportByCustomField.equals("true"))) {
/*  8239 */         allHaids = MyFields.customFieldsMGs(request, customfield);
/*       */       }
/*  8241 */       else if ("true".equals(fromSchedule))
/*       */       {
/*  8243 */         String[] temp = haid.split(",");
/*  8244 */         if (temp.length > 0) {
/*  8245 */           for (int k = 0; k < temp.length; k++)
/*       */           {
/*  8247 */             allHaids.add(temp[k]);
/*       */           }
/*       */         }
/*  8250 */       } else if (haid.indexOf("$") != -1) {
/*  8251 */         allHaids = MyFields.customFieldsMGs(request, haid);
/*       */       } else {
/*  8253 */         allHaids.add(haid);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  8258 */       ArrayList ALLDATA = null;
/*  8259 */       if ("allmonitors".equals(haid))
/*       */       {
/*  8261 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids, true);
/*       */       }
/*       */       else
/*       */       {
/*  8265 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids);
/*       */       }
/*       */       
/*       */ 
/*  8269 */       LinkedHashMap mapids = ReportUtilities.getMonitorsInMonitorGroup(ALLDATA);
/*       */       
/*  8271 */       ArrayList valueCompleteData = ReportUtilities.getValueForSnapshotReport(mapids, startTime, endTime, businessRule);
/*       */       
/*  8273 */       String bName = ReportUtilities.getBusinessRuleName(businessRule);
/*       */       
/*  8275 */       request.setAttribute("BNAME", bName);
/*  8276 */       if (valueCompleteData.size() == 0)
/*       */       {
/*  8278 */         AMLog.debug("Reports :  No data for " + haid);
/*  8279 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.time"));
/*       */         
/*  8281 */         saveMessages(request, messages);
/*  8282 */         request.setAttribute("heading", "0");
/*  8283 */         request.setAttribute("strTime", "0");
/*  8284 */         return mapping.findForward("report.message");
/*       */       }
/*  8286 */       String headvalue = (String)request.getAttribute("heading");
/*  8287 */       String newheadvalue; if (!"oni".equals(bName)) {
/*  8288 */         newheadvalue = FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { headvalue, bName });
/*  8289 */         request.setAttribute("heading", newheadvalue);
/*       */       }
/*  8291 */       request.setAttribute("AllValues", valueCompleteData);
/*  8292 */       request.setAttribute("strTime", new java.util.Date(startTime));
/*  8293 */       request.setAttribute("endTime", new java.util.Date(endTime));
/*       */       
/*  8295 */       if (controls.getReporttype().equals("pdf")) {
/*  8296 */         request.setAttribute("reportname", "AvailabilitySnapshotReport");
/*       */         
/*  8298 */         request.setAttribute("report-type-template", "report.availabilitysnapshot");
/*  8299 */         return mapping.findForward("report.availabilitysnapshot.pdf");
/*       */       }
/*       */       
/*  8302 */       if (controls.getReporttype().equals("excel")) {
/*  8303 */         request.setAttribute("reportname", "AvailabilitySnapshotReport");
/*       */         
/*  8305 */         request.setAttribute("report-type-template", "report.availabilitysnapshot");
/*  8306 */         request.setAttribute("reportType", "excel");
/*  8307 */         return mapping.findForward("report.availabilitysnapshot.pdf");
/*       */       }
/*       */       
/*  8310 */       if (controls.getReporttype().equals("csv")) {
/*  8311 */         return mapping.findForward("report.availabilitysnapshot.csv");
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  8316 */       return mapping.findForward("report.availabilitysnapshot");
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*       */ 
/*  8322 */       ex.printStackTrace();
/*       */     }
/*       */     finally {
/*  8325 */       closeResultSet(set);
/*       */     }
/*  8327 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward generateWeeklyMonthlyOutageReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  8333 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  8334 */     ActionMessages messages = new ActionMessages();
/*  8335 */     ResultSet set = null;
/*       */     try {
/*  8337 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*       */       
/*       */ 
/*  8340 */       ReportForm controls = (ReportForm)form;
/*       */       
/*  8342 */       String period = controls.getPeriod();
/*  8343 */       String attribute = controls.getInterval();
/*  8344 */       String businessRule = controls.getBusinessPeriod();
/*  8345 */       String thisstartdate = controls.getThisstart();
/*  8346 */       String thisenddate = controls.getThisend();
/*  8347 */       String laststartdate = controls.getLaststart();
/*  8348 */       String lastenddate = controls.getLastend();
/*  8349 */       String customfield = controls.getCustomFieldValue();
/*  8350 */       String reportByCustomField = controls.getCustomfield();
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  8355 */       if (("".equals(attribute)) || (attribute == null)) {
/*  8356 */         attribute = "week";
/*       */       }
/*       */       
/*       */ 
/*  8360 */       getCustomApplications(request, "3");
/*  8361 */       getHolisticApps(mapping, form, request, response);
/*  8362 */       getMonitors(mapping, form, request, response);
/*  8363 */       String haid = controls.getHaid();
/*  8364 */       String fromSchedule = request.getParameter("isschedule");
/*  8365 */       long[] timeStamps = null;
/*  8366 */       ArrayList allHaids = new ArrayList();
/*  8367 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*  8368 */       String nodata = "report.nodata";
/*  8369 */       timeStamps = ReportUtilities.getTimeStamp("12");
/*  8370 */       long lastweekstartTime = timeStamps[0];
/*  8371 */       long lastweekendTime = timeStamps[1];
/*  8372 */       timeStamps = ReportUtilities.getTimeStamp("15");
/*  8373 */       long thisweekstartTime = timeStamps[0];
/*  8374 */       long thisweekendTime = timeStamps[1];
/*  8375 */       timeStamps = ReportUtilities.getTimeStamp("11");
/*  8376 */       long lastmonthstartTime = timeStamps[0];
/*  8377 */       long lastmonthendTime = timeStamps[1];
/*  8378 */       timeStamps = ReportUtilities.getTimeStamp("7");
/*  8379 */       long thismonthstartTime = timeStamps[0];
/*  8380 */       long thismonthendTime = timeStamps[1];
/*       */       
/*  8382 */       long lastStartTime = 0L;
/*  8383 */       long lastEndTime = 0L;
/*  8384 */       long thisStartTime = 0L;
/*  8385 */       long thisEndTime = 0L;
/*       */       
/*  8387 */       if ((thisstartdate == null) || (thisstartdate.equals("")) || (thisenddate.equals("")))
/*       */       {
/*  8389 */         if ("day".equals(attribute)) {
/*  8390 */           attribute = "week";
/*       */         }
/*  8392 */         if ("week".equals(attribute)) {
/*  8393 */           lastStartTime = lastweekstartTime;
/*  8394 */           lastEndTime = lastweekendTime;
/*  8395 */           thisStartTime = thisweekstartTime;
/*  8396 */           thisEndTime = thisweekendTime;
/*  8397 */         } else if ("month".equals(attribute)) {
/*  8398 */           lastStartTime = lastmonthstartTime;
/*  8399 */           lastEndTime = lastmonthendTime;
/*  8400 */           thisStartTime = thismonthstartTime;
/*  8401 */           thisEndTime = thismonthendTime;
/*       */         }
/*       */       }
/*       */       else {
/*  8405 */         request.setAttribute("CUSTOM", "true");
/*  8406 */         long[] lasttimeStamps = ReportUtilities.parseTimeAndDate(laststartdate, lastenddate);
/*  8407 */         lastStartTime = lasttimeStamps[0];
/*  8408 */         lastEndTime = lasttimeStamps[1];
/*  8409 */         long[] thistimeStamps = ReportUtilities.parseTimeAndDate(thisstartdate, thisenddate);
/*  8410 */         thisStartTime = thistimeStamps[0];
/*  8411 */         thisEndTime = thistimeStamps[1];
/*  8412 */         String fromtime = laststartdate + " - " + lastenddate;
/*  8413 */         String totime = thisstartdate + " - " + thisenddate;
/*  8414 */         String tempHeading = FormatUtil.getString("am.webclient.reports.outagecompariosoncustomReport.text", new String[] { fromtime, totime });
/*  8415 */         if ((reportByCustomField.equals("true")) && (customfield.indexOf("$") != -1)) {
/*  8416 */           HashMap<String, String> customDetail = MyFields.reportValues(customfield);
/*  8417 */           tempHeading = tempHeading + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") });
/*       */         }
/*  8419 */         request.setAttribute("heading", tempHeading);
/*       */       }
/*  8421 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy, HH:mm");
/*  8422 */       java.util.Date resultdate = new java.util.Date(lastStartTime);
/*  8423 */       java.util.Date resultdate1 = new java.util.Date(lastEndTime);
/*  8424 */       java.util.Date resultdate2 = new java.util.Date(thisStartTime);
/*  8425 */       java.util.Date resultdate3 = new java.util.Date(thisEndTime);
/*       */       
/*  8427 */       Properties timeprops = new Properties();
/*  8428 */       timeprops.setProperty("previousfrom", sdf.format(resultdate));
/*  8429 */       timeprops.setProperty("previousto", sdf.format(resultdate1));
/*  8430 */       timeprops.setProperty("currentfrom", sdf.format(resultdate2));
/*  8431 */       timeprops.setProperty("currentto", sdf.format(resultdate3));
/*  8432 */       ArrayList haSnapshot = new ArrayList();
/*  8433 */       Vector v = new Vector();
/*  8434 */       ArrayList als = null;
/*  8435 */       Map myTreeMap = null;
/*  8436 */       boolean hasAnyData = false;
/*  8437 */       ResultSet haidset = null;
/*  8438 */       String haidquery = null;
/*       */       
/*       */ 
/*  8441 */       if ((("all".equals(haid)) || ("allmonitors".equals(haid))) && (!reportByCustomField.equals("true"))) {
/*       */         try {
/*  8443 */           if ((privilegedUser) && (!EnterpriseUtil.isIt360MSPEdition())) {
/*  8444 */             haidquery = getHaidResourceQuery(request);
/*       */           } else {
/*  8446 */             String bsgFilterCondn = "";
/*  8447 */             String bsgType = "0";
/*  8448 */             if (EnterpriseUtil.isIt360MSPEdition())
/*       */             {
/*  8450 */               bsgType = "1";
/*  8451 */               Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  8452 */               bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */             }
/*  8454 */             haidquery = "select HAID,DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + " order by AM_ManagedObject.DISPLAYNAME";
/*       */           }
/*       */           
/*  8457 */           haidset = AMConnectionPool.executeQueryStmt(haidquery);
/*  8458 */           while (haidset.next()) {
/*  8459 */             String highlevelhaid = haidset.getString("HAID");
/*  8460 */             String highleveldispname = haidset.getString("DISPLAYNAME");
/*  8461 */             allHaids.add(highlevelhaid);
/*       */           }
/*       */         }
/*       */         catch (Exception ex) {
/*  8465 */           ex.printStackTrace();
/*       */ 
/*       */         }
/*       */         finally {}
/*       */ 
/*       */       }
/*  8471 */       else if ((customfield != null) && (customfield.indexOf("$") != -1) && (reportByCustomField.equals("true"))) {
/*  8472 */         allHaids = MyFields.customFieldsMGs(request, customfield);
/*       */       }
/*  8474 */       else if ("true".equals(fromSchedule))
/*       */       {
/*  8476 */         String[] temp = haid.split(",");
/*  8477 */         if (temp.length > 0) {
/*  8478 */           for (int k = 0; k < temp.length; k++)
/*       */           {
/*  8480 */             allHaids.add(temp[k]);
/*       */           }
/*       */         }
/*       */       } else {
/*  8484 */         allHaids.add(haid);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8496 */       String bName = ReportUtilities.getBusinessRuleName(businessRule);
/*       */       
/*  8498 */       request.setAttribute("BNAME", bName);
/*       */       
/*  8500 */       ArrayList ALLDATA = null;
/*  8501 */       if ("allmonitors".equals(haid))
/*       */       {
/*  8503 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids, true);
/*       */       }
/*       */       else
/*       */       {
/*  8507 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids);
/*       */       }
/*       */       
/*       */ 
/*  8511 */       ArrayList AllData = new ArrayList();
/*  8512 */       for (int r = 0; r < ALLDATA.size(); r++)
/*       */       {
/*  8514 */         ArrayList M1 = (ArrayList)ALLDATA.get(r);
/*       */         
/*  8516 */         if (M1.size() > 0) {
/*  8517 */           for (int s = 0; s < M1.size(); s++) {
/*  8518 */             ArrayList mg = new ArrayList();
/*  8519 */             ArrayList insideM1 = (ArrayList)M1.get(s);
/*       */             
/*  8521 */             String RID = insideM1.get(0).toString();
/*       */             
/*  8523 */             String DISNAME = insideM1.get(2).toString();
/*  8524 */             String TYPE = insideM1.get(3).toString();
/*  8525 */             ArrayList rows = ReportUtilities.getDowntimeDetails(RID, lastStartTime, lastEndTime, businessRule);
/*       */             
/*  8527 */             int lastoutage = rows.size();
/*  8528 */             long lastDowntime = getTotalDownTime(rows);
/*  8529 */             ArrayList rows1 = ReportUtilities.getDowntimeDetails(RID, thisStartTime, thisEndTime, businessRule);
/*  8530 */             int thisoutage = rows1.size();
/*  8531 */             long thisDowntime = getTotalDownTime(rows1);
/*  8532 */             int change = thisoutage - lastoutage;
/*  8533 */             float CPercent = 0.0F;
/*  8534 */             if (lastoutage != 0) {
/*  8535 */               CPercent = change / lastoutage * 100.0F;
/*  8536 */               CPercent = Math.round(CPercent * 100.0F) / 100.0F;
/*       */             }
/*  8538 */             long diff = thisDowntime - lastDowntime;
/*  8539 */             float diffPercent = 0.0F;
/*  8540 */             if (lastDowntime != 0L) {
/*  8541 */               diffPercent = (float)diff / (float)lastDowntime * 100.0F;
/*  8542 */               diffPercent = Math.round(diffPercent * 100.0F) / 100.0F;
/*       */             }
/*       */             
/*  8545 */             mg.add(RID);
/*  8546 */             mg.add(DISNAME);
/*  8547 */             mg.add(Integer.valueOf(lastoutage));
/*  8548 */             mg.add(Long.valueOf(lastDowntime));
/*  8549 */             mg.add(Integer.valueOf(thisoutage));
/*  8550 */             mg.add(Long.valueOf(thisDowntime));
/*  8551 */             mg.add(Integer.valueOf(change));
/*  8552 */             if ((lastoutage == 0) && (thisoutage > 0)) {
/*  8553 */               mg.add("NA");
/*       */             } else {
/*  8555 */               mg.add(Float.valueOf(CPercent));
/*       */             }
/*  8557 */             mg.add(Long.valueOf(diff));
/*  8558 */             if ((lastDowntime == 0L) && (thisDowntime > 0L)) {
/*  8559 */               mg.add("NA");
/*       */             } else {
/*  8561 */               mg.add(Float.valueOf(diffPercent));
/*       */             }
/*  8563 */             mg.add(TYPE);
/*  8564 */             AllData.add(mg);
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  8569 */       if (AllData.size() == 0)
/*       */       {
/*  8571 */         AMLog.debug("Reports :  No data for " + haid);
/*  8572 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.time"));
/*       */         
/*  8574 */         saveMessages(request, messages);
/*  8575 */         request.setAttribute("heading", "0");
/*  8576 */         request.setAttribute("strTime", "0");
/*  8577 */         return mapping.findForward("report.message");
/*       */       }
/*  8579 */       String headvalue = (String)request.getAttribute("heading");
/*  8580 */       String newheadvalue; if (!"oni".equals(bName)) {
/*  8581 */         newheadvalue = FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { headvalue, bName });
/*  8582 */         request.setAttribute("heading", newheadvalue);
/*       */       }
/*       */       
/*  8585 */       request.setAttribute("mgvalues", AllData);
/*  8586 */       request.setAttribute("timeperiod", attribute);
/*  8587 */       request.setAttribute("timeprops", timeprops);
/*  8588 */       request.setAttribute("strTime", new java.util.Date(lastStartTime));
/*  8589 */       request.setAttribute("endTime", new java.util.Date(thisEndTime));
/*       */       
/*       */ 
/*  8592 */       if (controls.getReporttype().equals("pdf")) {
/*  8593 */         request.setAttribute("reportname", "OutageReport");
/*       */         
/*  8595 */         request.setAttribute("report-type-template", "report.outagereport");
/*  8596 */         return mapping.findForward("report.outagereport.pdf");
/*       */       }
/*       */       
/*  8599 */       if (controls.getReporttype().equals("excel")) {
/*  8600 */         request.setAttribute("reportname", "OutageReport");
/*       */         
/*  8602 */         request.setAttribute("report-type-template", "report.outagereport");
/*  8603 */         request.setAttribute("reportType", "excel");
/*  8604 */         return mapping.findForward("report.outagereport.pdf");
/*       */       }
/*       */       
/*  8607 */       if (controls.getReporttype().equals("csv")) {
/*  8608 */         return mapping.findForward("report.outagereport.csv");
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  8613 */       return mapping.findForward("report.outagereport");
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*       */ 
/*  8619 */       ex.printStackTrace();
/*       */     }
/*       */     finally {
/*  8622 */       closeResultSet(set);
/*       */     }
/*  8624 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward generatePeriodAvailabilityDowntimeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  8630 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  8631 */     ActionMessages messages = new ActionMessages();
/*  8632 */     ResultSet set = null;
/*  8633 */     String value = "trendDowntimeReport";
/*  8634 */     ReportGraphs rg = new ReportGraphs();
/*  8635 */     String timeseriesImage = null;
/*  8636 */     ArrayList images = new ArrayList();
/*       */     try {
/*  8638 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*  8639 */       ReportForm controls = (ReportForm)form;
/*  8640 */       String haid = controls.getHaid();
/*  8641 */       String period = controls.getPeriod();
/*  8642 */       String attribute = controls.getAttribute();
/*  8643 */       String interval = controls.getInterval();
/*  8644 */       String businessRule = controls.getBusinessPeriod();
/*       */       
/*  8646 */       String fromSchedule = request.getParameter("isschedule");
/*       */       
/*  8648 */       getCustomApplications(request, "3");
/*  8649 */       getHolisticApps(mapping, form, request, response);
/*  8650 */       getMonitors(mapping, form, request, response);
/*       */       
/*  8652 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*  8653 */       String nodata = "report.nodata";
/*       */       
/*  8655 */       if (("".equals(interval)) || (interval == null)) {
/*  8656 */         interval = "day";
/*       */       }
/*  8658 */       ArrayList allTimes = ReportUtilities.getLastTwelveTimeStamps(interval);
/*       */       
/*  8660 */       ArrayList distime = ReportUtilities.getTimeToDisplayInTrendReport(allTimes, interval);
/*       */       
/*  8662 */       request.setAttribute("displaytime", distime);
/*  8663 */       ArrayList allHaids = new ArrayList();
/*       */       
/*  8665 */       boolean hasAnyData = false;
/*  8666 */       ResultSet haidset = null;
/*  8667 */       String haidquery = null;
/*       */       
/*       */ 
/*  8670 */       if (("all".equals(haid)) || ("allmonitors".equals(haid))) {
/*       */         try {
/*  8672 */           if ((privilegedUser) && (!EnterpriseUtil.isIt360MSPEdition())) {
/*  8673 */             haidquery = getHaidResourceQuery(request);
/*       */           } else {
/*  8675 */             String bsgFilterCondn = "";
/*  8676 */             String bsgType = "0";
/*  8677 */             if (EnterpriseUtil.isIt360MSPEdition())
/*       */             {
/*  8679 */               bsgType = "1";
/*  8680 */               Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  8681 */               bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */             }
/*  8683 */             haidquery = "select HAID,DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn;
/*       */           }
/*  8685 */           haidset = AMConnectionPool.executeQueryStmt(haidquery);
/*  8686 */           while (haidset.next()) {
/*  8687 */             String highlevelhaid = haidset.getString("HAID");
/*  8688 */             String highleveldispname = haidset.getString("DISPLAYNAME");
/*  8689 */             allHaids.add(highlevelhaid);
/*       */           }
/*       */         }
/*       */         catch (Exception ex) {
/*  8693 */           ex.printStackTrace();
/*       */ 
/*       */         }
/*       */         finally {}
/*       */ 
/*       */       }
/*  8699 */       else if ("true".equals(fromSchedule))
/*       */       {
/*  8701 */         String[] temp = haid.split(",");
/*  8702 */         if (temp.length > 0) {
/*  8703 */           for (int k = 0; k < temp.length; k++)
/*       */           {
/*  8705 */             allHaids.add(temp[k]);
/*       */           }
/*       */         }
/*       */       }
/*       */       else {
/*  8710 */         allHaids.add(haid);
/*       */       }
/*  8712 */       AMLog.debug("HAIDs are ===> : " + allHaids);
/*  8713 */       String targetAvail = null;
/*  8714 */       String queryAvail = "select PERCENTAVAIL from AM_SLO_APPLICATIONAVAILABLITY,AM_SLA_RESID_MAPPER,AM_SLO where  AM_SLO.ID=AM_SLO_APPLICATIONAVAILABLITY.SLO_ID and AM_SLA_RESID_MAPPER.SLA_ID =AM_SLO.SLA_ID and AM_SLA_RESID_MAPPER.RESID=" + haid;
/*  8715 */       AMLog.debug("Target Availability Query : Excel " + queryAvail);
/*  8716 */       ResultSet setAvail = null;
/*       */       try
/*       */       {
/*  8719 */         setAvail = AMConnectionPool.executeQueryStmt(queryAvail);
/*  8720 */         while (setAvail.next())
/*       */         {
/*  8722 */           targetAvail = String.valueOf(setAvail.getFloat("PERCENTAVAIL"));
/*  8723 */           AMLog.debug("============ > : " + targetAvail);
/*       */         }
/*       */       } catch (Exception ex) {
/*  8726 */         ex.printStackTrace();
/*       */       }
/*       */       finally {}
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8734 */       ArrayList ALLDATA = null;
/*  8735 */       if ("allmonitors".equals(haid))
/*       */       {
/*  8737 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids, true);
/*       */       }
/*       */       else
/*       */       {
/*  8741 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids);
/*       */       }
/*       */       
/*  8744 */       ArrayList ALLVALUES = ReportUtilities.getDataForAvailabilityTrendReport(ALLDATA, allTimes, businessRule, value);
/*  8745 */       String bName = ReportUtilities.getBusinessRuleName(businessRule);
/*  8746 */       String headvalue = (String)request.getAttribute("heading");
/*  8747 */       if (!"oni".equals(bName)) {
/*  8748 */         String newheadvalue = FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { headvalue, bName });
/*  8749 */         request.setAttribute("heading", newheadvalue);
/*       */       }
/*  8751 */       request.setAttribute("BNAME", bName);
/*  8752 */       request.setAttribute("targetAvail", targetAvail);
/*  8753 */       request.setAttribute("allvalues", ALLVALUES);
/*  8754 */       request.setAttribute("strTime", "0");
/*  8755 */       request.setAttribute("timeperiod", interval);
/*       */       
/*  8757 */       if ((controls.getReporttype().equals("excel")) || (controls.getReporttype().equals("pdf"))) {
/*       */         try {
/*  8759 */           AMLog.debug("INSIDE GRAPH genetation Loop 11111 ");
/*  8760 */           Map params = new Hashtable();
/*       */           
/*  8762 */           params.put("type", "TRENDAVAILABILITYLINE");
/*  8763 */           params.put("id", request.getParameter("haid"));
/*  8764 */           params.put("interval", request.getParameter("interval"));
/*  8765 */           params.put("data", ALLVALUES);
/*  8766 */           rg.setParams(params);
/*  8767 */           AMLog.debug("PARAMS value ====> : 11111 " + params);
/*  8768 */           ChartInfo cinfo = new ChartInfo();
/*  8769 */           cinfo.setDataSet(rg);
/*  8770 */           cinfo.setLegend("false");
/*  8771 */           cinfo.setWidth("800");
/*  8772 */           cinfo.setHeight("300");
/*  8773 */           cinfo.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*  8774 */           cinfo.setLabelRotation(false);
/*  8775 */           cinfo.setXaxisLabel(FormatUtil.getString("am.reporting.timeperiods.name"));
/*  8776 */           cinfo.setYaxisLabel(FormatUtil.getString("am.webclient.reports.excel.trendreport.availability.text"));
/*       */           
/*  8778 */           timeseriesImage = cinfo.getTimeChartAsJPG();
/*  8779 */           images.add(timeseriesImage);
/*  8780 */           AMLog.debug("AFTER SETTING THE IMAGES 11111 " + timeseriesImage);
/*       */           
/*  8782 */           AMLog.debug("INSIDE GRAPH genetation Loop 11111 ");
/*       */           
/*       */ 
/*  8785 */           params.put("type", "TRENDDOWNCOUNTREPORT");
/*  8786 */           params.put("id", request.getParameter("haid"));
/*  8787 */           params.put("interval", request.getParameter("interval"));
/*  8788 */           params.put("data", ALLVALUES);
/*  8789 */           rg.setParams(params);
/*  8790 */           AMLog.debug("PARAMS value ====> : 22222 " + params);
/*  8791 */           ChartInfo cinfo1 = new ChartInfo();
/*  8792 */           cinfo1.setDataSet(rg);
/*  8793 */           cinfo1.setLegend("false");
/*  8794 */           cinfo1.setWidth("800");
/*  8795 */           cinfo1.setHeight("250");
/*  8796 */           cinfo1.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*  8797 */           cinfo1.setMaxBarWidth(0.07D);
/*  8798 */           cinfo1.setBarcolor(new Paint[] { new Color(135, 206, 250) });
/*  8799 */           cinfo1.setLabelRotation(false);
/*  8800 */           cinfo1.setTwoDimensionBar(true);
/*  8801 */           cinfo1.setSkipLabelRotation(true);
/*       */           
/*  8803 */           cinfo1.setXaxisLabel(FormatUtil.getString("am.reporting.timeperiods.name"));
/*  8804 */           cinfo1.setYaxisLabel(FormatUtil.getString("am.webclient.reports.excel.trendreport.downtimecount.text"));
/*  8805 */           cinfo1.setChartTitle(FormatUtil.getString("am.webclient.reports.excel.trendreport.downtimecount.text"));
/*       */           
/*  8807 */           timeseriesImage = cinfo1.get3DbarChartAsJPG();
/*  8808 */           images.add(timeseriesImage);
/*  8809 */           AMLog.debug("AFTER SETTING THE IMAGES 22222 " + timeseriesImage);
/*       */           
/*  8811 */           AMLog.debug("INSIDE GRAPH genetation Loop 22222 ");
/*       */           
/*       */ 
/*  8814 */           params.put("type", "TRENDDOWNTIMEREPORT");
/*  8815 */           params.put("id", request.getParameter("haid"));
/*  8816 */           params.put("interval", request.getParameter("interval"));
/*  8817 */           params.put("data", ALLVALUES);
/*  8818 */           rg.setParams(params);
/*  8819 */           AMLog.debug("PARAMS value ====> : 33333 " + params);
/*  8820 */           ChartInfo cinfo2 = new ChartInfo();
/*  8821 */           cinfo2.setDataSet(rg);
/*  8822 */           cinfo2.setLegend("false");
/*  8823 */           cinfo2.setWidth("800");
/*  8824 */           cinfo2.setHeight("250");
/*  8825 */           cinfo2.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*  8826 */           cinfo2.setMaxBarWidth(0.07D);
/*  8827 */           cinfo2.setBarcolor(new Paint[] { new Color(135, 206, 250) });
/*  8828 */           cinfo2.setLabelRotation(false);
/*  8829 */           cinfo2.setTwoDimensionBar(true);
/*  8830 */           cinfo2.setSkipLabelRotation(true);
/*       */           
/*  8832 */           cinfo2.setXaxisLabel(FormatUtil.getString("am.reporting.timeperiods.name"));
/*  8833 */           cinfo2.setYaxisLabel(FormatUtil.getString("am.webclient.reports.availabiityTrendDowntimeReport.downtimetext"));
/*  8834 */           cinfo2.setChartTitle(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/*       */           
/*  8836 */           timeseriesImage = cinfo2.get3DbarChartAsJPG();
/*  8837 */           images.add(timeseriesImage);
/*  8838 */           AMLog.debug("AFTER SETTING THE IMAGES 33333 " + timeseriesImage);
/*  8839 */           AMLog.debug("INSIDE GRAPH genetation Loop 33333 ");request.setAttribute("images", images);
/*       */         }
/*       */         catch (Exception exx)
/*       */         {
/*  8843 */           exx.printStackTrace();
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*  8848 */         AMLog.debug("INSIDE THE EXCEL SHEET FORM for AvailabilityTrendReport 111 ");
/*  8849 */         request.setAttribute("reportname", "AvailabilityTrendDowntimeReport");
/*       */         
/*  8851 */         request.setAttribute("report-type-template", "report.availabilitytrenddowntimereport");
/*  8852 */         if (controls.getReporttype().equals("excel"))
/*       */         {
/*  8854 */           request.setAttribute("reportType", "excel");
/*       */         }
/*       */         else
/*       */         {
/*  8858 */           request.setAttribute("reportType", "pdf");
/*       */         }
/*  8860 */         request.setAttribute("images", images);
/*  8861 */         AMLog.debug("INSIDE THE EXCEL SHEET FORM for AvailabilityTrendDowntimeReport PRINT 222");
/*  8862 */         return mapping.findForward("report.availabilitytrenddowntimereport.pdf");
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  8867 */       return mapping.findForward("report.availabilitytrenddowntimereport");
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  8872 */       ex.printStackTrace();
/*       */     }
/*       */     finally {
/*  8875 */       closeResultSet(set);
/*       */     }
/*  8877 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward generatePeriodAvailabilityReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  8883 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  8884 */     ActionMessages messages = new ActionMessages();
/*  8885 */     ResultSet set = null;
/*  8886 */     String value = "trendReport";
/*       */     try {
/*  8888 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*       */       
/*       */ 
/*  8891 */       ReportForm controls = (ReportForm)form;
/*       */       
/*  8893 */       String period = controls.getPeriod();
/*  8894 */       String attribute = controls.getAttribute();
/*  8895 */       String interval = controls.getInterval();
/*  8896 */       String businessRule = controls.getBusinessPeriod();
/*       */       
/*       */ 
/*  8899 */       String fromSchedule = request.getParameter("isschedule");
/*       */       
/*  8901 */       getCustomApplications(request, "3");
/*  8902 */       getHolisticApps(mapping, form, request, response);
/*  8903 */       getMonitors(mapping, form, request, response);
/*  8904 */       String haid = controls.getHaid();
/*       */       
/*       */ 
/*  8907 */       request.setAttribute("HelpKey", "Monitor Group Reports");
/*  8908 */       String nodata = "report.nodata";
/*       */       
/*  8910 */       if (("".equals(interval)) || (interval == null)) {
/*  8911 */         interval = "day";
/*       */       }
/*  8913 */       ArrayList allTimes = ReportUtilities.getLastTwelveTimeStamps(interval);
/*       */       
/*  8915 */       ArrayList distime = ReportUtilities.getTimeToDisplayInTrendReport(allTimes, interval);
/*       */       
/*  8917 */       request.setAttribute("displaytime", distime);
/*  8918 */       ArrayList allHaids = new ArrayList();
/*       */       
/*  8920 */       String customfield = controls.getCustomFieldValue();
/*  8921 */       boolean hasAnyData = false;
/*  8922 */       String reportByCustomField = controls.getCustomfield();
/*  8923 */       ResultSet haidset = null;
/*  8924 */       String haidquery = null;
/*       */       
/*       */ 
/*  8927 */       if ((("all".equals(haid)) || ("allmonitors".equals(haid))) && (!reportByCustomField.equals("true"))) {
/*       */         try {
/*  8929 */           if ((privilegedUser) && (!EnterpriseUtil.isIt360MSPEdition())) {
/*  8930 */             haidquery = getHaidResourceQuery(request);
/*       */           } else {
/*  8932 */             String bsgFilterCondn = "";
/*  8933 */             String bsgType = "0";
/*  8934 */             if (EnterpriseUtil.isIt360MSPEdition())
/*       */             {
/*  8936 */               bsgType = "1";
/*  8937 */               Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  8938 */               bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */             }
/*  8940 */             haidquery = "select HAID,DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + " order by DISPLAYNAME";
/*       */           }
/*  8942 */           haidset = AMConnectionPool.executeQueryStmt(haidquery);
/*  8943 */           while (haidset.next()) {
/*  8944 */             String highlevelhaid = haidset.getString("HAID");
/*  8945 */             String highleveldispname = haidset.getString("DISPLAYNAME");
/*  8946 */             allHaids.add(highlevelhaid);
/*       */           }
/*       */         }
/*       */         catch (Exception ex) {
/*  8950 */           ex.printStackTrace();
/*       */ 
/*       */         }
/*       */         finally {}
/*       */ 
/*       */       }
/*  8956 */       else if ((customfield != null) && (customfield.indexOf("$") != -1) && (reportByCustomField.equals("true"))) {
/*  8957 */         allHaids = MyFields.customFieldsMGs(request, customfield);
/*       */       }
/*  8959 */       else if ("true".equals(fromSchedule))
/*       */       {
/*  8961 */         String[] temp = haid.split(",");
/*  8962 */         if (temp.length > 0) {
/*  8963 */           for (int k = 0; k < temp.length; k++)
/*       */           {
/*  8965 */             allHaids.add(temp[k]);
/*       */           }
/*       */         }
/*       */       }
/*       */       else {
/*  8970 */         allHaids.add(haid);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  8975 */       ArrayList ALLDATA = null;
/*  8976 */       if ("allmonitors".equals(haid))
/*       */       {
/*  8978 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids, true);
/*       */       }
/*       */       else
/*       */       {
/*  8982 */         ALLDATA = ReportUtilities.getStructuredDataForMonitorGroup(allHaids);
/*       */       }
/*       */       
/*  8985 */       ArrayList ALLVALUES = ReportUtilities.getDataForAvailabilityTrendReport(ALLDATA, allTimes, businessRule, value);
/*  8986 */       String bName = ReportUtilities.getBusinessRuleName(businessRule);
/*  8987 */       String headvalue = (String)request.getAttribute("heading");
/*  8988 */       String newheadvalue; if (!"oni".equals(bName)) {
/*  8989 */         newheadvalue = FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { headvalue, bName });
/*  8990 */         request.setAttribute("heading", newheadvalue);
/*       */       }
/*  8992 */       request.setAttribute("BNAME", bName);
/*  8993 */       request.setAttribute("allvalues", ALLVALUES);
/*  8994 */       request.setAttribute("strTime", "0");
/*  8995 */       request.setAttribute("timeperiod", interval);
/*  8996 */       if (controls.getReporttype().equals("pdf")) {
/*  8997 */         request.setAttribute("reportname", "AvailabilityTrendReport");
/*       */         
/*  8999 */         request.setAttribute("report-type-template", "report.availabilitytrendreport");
/*  9000 */         return mapping.findForward("report.availabilitytrendreport.pdf");
/*       */       }
/*       */       
/*  9003 */       if (controls.getReporttype().equals("excel")) {
/*  9004 */         request.setAttribute("reportname", "AvailabilityTrendReport");
/*       */         
/*  9006 */         request.setAttribute("report-type-template", "report.availabilitytrendreport");
/*  9007 */         request.setAttribute("reportType", "excel");
/*  9008 */         return mapping.findForward("report.availabilitytrendreport.pdf");
/*       */       }
/*       */       
/*  9011 */       if (controls.getReporttype().equals("csv")) {
/*  9012 */         return mapping.findForward("report.availabilitytrendreport.csv");
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  9017 */       return mapping.findForward("report.availabilitytrendreport");
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*       */ 
/*  9023 */       ex.printStackTrace();
/*       */     }
/*       */     finally {
/*  9026 */       closeResultSet(set);
/*       */     }
/*  9028 */     return null;
/*       */   }
/*       */   
/*       */   public long getTotalDownTime(ArrayList rows)
/*       */   {
/*  9033 */     long totaldowntime = 0L;
/*       */     try {
/*  9035 */       for (int i = 0; i < rows.size(); i++) {
/*  9036 */         Properties p1 = (Properties)rows.get(i);
/*  9037 */         long time = Long.parseLong(p1.getProperty("downtimeinmillis"));
/*  9038 */         totaldowntime += time;
/*       */       }
/*       */     }
/*       */     catch (Exception ex) {}
/*       */     
/*       */ 
/*  9044 */     return totaldowntime;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ActionForward generateTrendReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9052 */     ActionMessages messages = new ActionMessages();
/*  9053 */     ResultSet set = null;
/*  9054 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  9055 */     ReportForm controls = (ReportForm)form;
/*  9056 */     String resourceType = controls.getResourceType();
/*       */     
/*  9058 */     String attributeids = controls.getAttribute();
/*       */     
/*  9060 */     String archivedTableName = DBUtil.getArchiveingTableName(attributeids);
/*       */     
/*  9062 */     String hid = controls.getWorkingdays();
/*       */     
/*  9064 */     String attname = controls.getAttributeName();
/*       */     
/*  9066 */     getHolisticApps(mapping, form, request, response);
/*  9067 */     getMonitors(mapping, form, request, response);
/*  9068 */     getCustomApplications(request, "3");
/*  9069 */     String attribute = request.getParameter("attribute");
/*  9070 */     System.out.println("the attribute is===>" + attribute);
/*  9071 */     String resID = request.getParameter("resourceid");
/*  9072 */     if ((request.getParameter("getpdf") != null) && (request.getParameter("getpdf").equals("true"))) {
/*  9073 */       hid = request.getParameter("hid");
/*       */     }
/*       */     
/*       */ 
/*  9077 */     String hostresid = "0";
/*  9078 */     String queryresid = "0";
/*  9079 */     if (Constants.sqlManager) {
/*       */       try
/*       */       {
/*  9082 */         HashMap sqlChildids = DBUtil.getSqlchildids(resID);
/*  9083 */         if (sqlChildids.size() > 0) {
/*  9084 */           hostresid = (String)sqlChildids.get("hostresid");
/*  9085 */           queryresid = (String)sqlChildids.get("queryresid");
/*       */         }
/*       */       }
/*       */       catch (Exception eq) {
/*  9089 */         eq.printStackTrace();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  9095 */     request.setAttribute("HelpKey", getMonitorHelpKey(resourceType));
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9102 */     if ((request.getParameter("ischildReport") != null) && (request.getParameter("ischildReport").equals("true")))
/*       */     {
/*       */ 
/*  9105 */       request.setAttribute("ischildReport", "true");
/*       */       try
/*       */       {
/*  9108 */         if (resID != null)
/*       */         {
/*       */ 
/*  9111 */           ReportUtilities repUtil = new ReportUtilities();
/*  9112 */           ArrayList sevenThirtyAttribCln = repUtil.getAttributeCollection(resID, attribute, request.getParameter("resourceType"));
/*       */           
/*  9114 */           controls.setSevenThirtyAttribCln(sevenThirtyAttribCln);
/*  9115 */           controls.setSevenThirtyAttrib(resID + "#" + attribute);
/*       */         }
/*       */       }
/*       */       catch (Exception e) {
/*  9119 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9127 */     if (!areMonitorsPresent(resourceType))
/*       */     {
/*  9129 */       AMLog.debug("Reports : No data for " + resourceType);
/*  9130 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.monitors"));
/*  9131 */       saveMessages(request, messages);
/*  9132 */       request.setAttribute("heading", "0");
/*  9133 */       request.setAttribute("strTime", "0");
/*  9134 */       return mapping.findForward("report.message");
/*       */     }
/*  9136 */     List datacollectionStartedMOs = getDataCollectionStartedMonitors(resourceType);
/*  9137 */     if (datacollectionStartedMOs.size() == 0)
/*       */     {
/*  9139 */       AMLog.debug("Reports : Data Collection not started for " + resourceType);
/*  9140 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodatacollection.monitors"));
/*  9141 */       saveMessages(request, messages);
/*  9142 */       request.setAttribute("heading", "0");
/*  9143 */       request.setAttribute("strTime", "0");
/*  9144 */       return mapping.findForward("report.message");
/*       */     }
/*       */     
/*       */ 
/*  9148 */     String nodata = "report.nodata.time";
/*  9149 */     String RES_ID = null;
/*  9150 */     String ATT_ID = null;
/*  9151 */     String valueforpdf = null;
/*  9152 */     int array_size = 0;
/*  9153 */     List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/*  9154 */     String period = controls.getPeriod();
/*       */     
/*  9156 */     if ((period != null) && (period.equals("14")))
/*       */     {
/*       */ 
/*  9159 */       if ((attribute != null) && (attname != null))
/*       */       {
/*  9161 */         if ((!attribute.equals("cpuid")) && (!attribute.equals("mem")) && (!attname.equals("Memory Usage")) && (!attname.equals("CPU Utilization")))
/*       */         {
/*       */ 
/*  9164 */           period = "0";
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*  9169 */     controls.setPeriod(period);
/*  9170 */     request.setAttribute("period", period);
/*       */     
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  9176 */       ReportUtilities rep = new ReportUtilities();
/*  9177 */       valueforpdf = rep.getValueForPeriodForPDF(period);
/*       */       
/*  9179 */       timeStamps = null;
/*  9180 */       if ((controls.getStartDate().equals("")) || (controls.getEndDate().equals(""))) {
/*  9181 */         timeStamps = ReportUtilities.getTimeStamp(period);
/*       */       }
/*       */       else {
/*  9184 */         controls.setPeriod("4");
/*       */         try {
/*  9186 */           timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */ 
/*       */         }
/*       */         catch (IllegalArgumentException iae)
/*       */         {
/*  9191 */           String errMsg = iae.getMessage();
/*  9192 */           AMLog.debug("Reports :  " + errMsg);
/*  9193 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  9194 */           saveMessages(request, messages);
/*  9195 */           request.setAttribute("heading", "0");
/*  9196 */           request.setAttribute("strTime", "0");
/*  9197 */           return mapping.findForward("report.message");
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  9203 */       long[] dailyRptTimestamp = ReportUtilities.getDailyStartEndTime(timeStamps[0], timeStamps[1], archivedTableName);
/*       */       
/*       */ 
/*  9206 */       String dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1];
/*       */       
/*  9208 */       request.setAttribute("dailyStime", dailyRptTimestamp[2] + "");
/*  9209 */       request.setAttribute("dailyEtime", dailyRptTimestamp[3] + "");
/*       */       
/*       */ 
/*       */ 
/*  9213 */       if (dailyRptTimestamp[2] > 0L)
/*       */       {
/*  9215 */         dailyRptCondition = " and ( " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + dailyRptTimestamp[0] + " and ARCHIVEDTIME <=" + dailyRptTimestamp[1] + " OR " + archivedTableName + ".DURATION=2 and ARCHIVEDTIME >=" + dailyRptTimestamp[2] + " and ARCHIVEDTIME <=" + dailyRptTimestamp[3] + ") ";
/*       */       }
/*       */       
/*       */ 
/*  9219 */       request.setAttribute("timeStamps", timeStamps);
/*  9220 */       String query = "";
/*  9221 */       String groupbyStr = "";
/*  9222 */       ArrayList residsinorder = new ArrayList();
/*  9223 */       Vector nametreelist = new Vector();
/*  9224 */       boolean hasSubGroups = false;
/*  9225 */       boolean as400Disk = false;
/*       */       
/*  9227 */       if ((resourceType.equals("AS400/iSeries")) && ((attribute.contains("711")) || (attribute.contains("712")) || (attribute.contains("736")) || (attribute.contains("737")) || (attribute.contains("738")))) {
/*  9228 */         as400Disk = true;
/*       */       }
/*       */       
/*  9231 */       if (((attribute != null) && ((attribute.equalsIgnoreCase("disk")) || (attribute.indexOf("711") != -1))) || ((attname != null) && (attname.equalsIgnoreCase("Disk Utilisation"))))
/*       */       {
/*       */ 
/*       */ 
/*  9235 */         if ((resourceType != null) && (resourceType.equalsIgnoreCase("AIX','AS400/iSeries','FreeBSD','HP-UX / Tru64','Linux','Mac OS','Sun Solaris','Windows")))
/*       */         {
/*       */ 
/*  9238 */           query = DBQueryUtil.getDBQuery("am.amreportAction.generateTrendReport1", new String[] { archivedTableName, attributeids });
/*  9239 */           groupbyStr = " group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  9240 */           query = query + dailyRptCondition + groupbyStr;
/*  9241 */           query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*  9246 */           Vector v = new Vector();
/*  9247 */           v.add(resID);
/*       */           
/*  9249 */           String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE in ('Disk','AS400_disk')";
/*  9250 */           set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  9251 */           v = new Vector();
/*  9252 */           while (set.next()) {
/*  9253 */             String id = set.getString("RESID");
/*  9254 */             v.add(id);
/*       */           }
/*  9256 */           AMConnectionPool.closeStatement(set);
/*  9257 */           query = DBQueryUtil.getDBQuery("am.amreportAction.generateTrendReport2", new String[] { archivedTableName, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v) });
/*       */           
/*  9259 */           groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  9260 */           query = query + dailyRptCondition + groupbyStr;
/*  9261 */           query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*  9266 */       else if (((attribute != null) && ((attribute.equalsIgnoreCase("jdbc")) || (attribute.equalsIgnoreCase("thread")))) || ((attname != null) && ((attname.equalsIgnoreCase("Connection Pool Usage")) || (attname.equalsIgnoreCase("Thread Details")))))
/*       */       {
/*  9268 */         query = DBQueryUtil.getDBQuery("am.amreportAction.generateTrendReport3", new String[] { archivedTableName, attributeids });
/*       */         
/*  9270 */         groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  9271 */         query = query + dailyRptCondition + groupbyStr;
/*  9272 */         query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */       }
/*  9275 */       else if (((attribute != null) && (attribute.equalsIgnoreCase("operationExecutionTime"))) || ((attname != null) && (attname.equalsIgnoreCase("Operation Execution Time"))))
/*       */       {
/*  9277 */         query = DBQueryUtil.getDBQuery("am.amreportAction.generateTrendReport4", new String[] { archivedTableName });
/*       */         
/*       */ 
/*  9280 */         groupbyStr = " group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Name,Average desc ";
/*  9281 */         query = query + dailyRptCondition + groupbyStr;
/*  9282 */         query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*  9283 */       } else if ((attribute != null) && (allSecondLevelAttribute.contains(attribute)))
/*       */       {
/*       */ 
/*  9286 */         Vector v = new Vector();
/*       */         
/*  9288 */         v.add(resID);
/*       */         
/*       */ 
/*  9291 */         ArrayList curResids = new ArrayList();
/*  9292 */         if ("QueryMonitor".equalsIgnoreCase(resourceType)) {
/*  9293 */           curResids = getCurrentQueryMonitorResourceIDs(resID, "QueryMonitor");
/*       */         }
/*  9295 */         String attrType = ReportUtilities.getResourceTypeForAttribute(attribute);
/*       */         
/*       */ 
/*  9298 */         String disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='" + ReportUtilities.getResourceTypeForAttribute(attribute) + "'";
/*  9299 */         if (as400Disk) {
/*  9300 */           disidsquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ReportUtilities.getCondition("PARENTID", v) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedObject.TYPE ='AS400_disk'";
/*       */         }
/*       */         
/*  9303 */         set = AMConnectionPool.executeQueryStmt(disidsquery);
/*  9304 */         v = new Vector();
/*  9305 */         while (set.next()) {
/*  9306 */           String id = set.getString("RESID");
/*       */           
/*  9308 */           if ((!"QueryMonitor".equalsIgnoreCase(resourceType)) || ("QueryMonitor_Execution Time_ROW".equalsIgnoreCase(attrType)) || (curResids.contains(id)))
/*       */           {
/*       */ 
/*  9311 */             v.add(id); }
/*       */         }
/*  9313 */         AMConnectionPool.closeStatement(set);
/*       */         
/*       */ 
/*  9316 */         query = DBQueryUtil.getDBQuery("am.amreportAction.generateTrendReport5", new String[] { archivedTableName, attribute, ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", v) });
/*       */         
/*  9318 */         if (!as400Disk) {
/*  9319 */           query = query + " and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE ";
/*       */         } else {
/*  9321 */           query = query + " and AM_ManagedObject.TYPE='AS400_disk' and AM_ATTRIBUTES.RESOURCETYPE='Disk' ";
/*       */         }
/*  9323 */         groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  9324 */         query = query + dailyRptCondition + groupbyStr;
/*  9325 */         query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*  9331 */         if ((attributeids != null) && (attributeids.equalsIgnoreCase(""))) {
/*  9332 */           attributeids = null;
/*       */         }
/*  9334 */         query = DBQueryUtil.getDBQuery("am.amreportAction.generateTrendReport6", new String[] { archivedTableName, attributeids });
/*       */         
/*  9336 */         groupbyStr = "  group by AM_ManagedObject.RESOURCEID," + archivedTableName + ".ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME order by Average desc ";
/*  9337 */         query = query + dailyRptCondition + groupbyStr;
/*  9338 */         query = getTopNValues(query, String.valueOf(controls.getNumberOfRows()));
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  9343 */       request.setAttribute("STIME", timeStamps[0] + "");
/*  9344 */       request.setAttribute("ETIME", timeStamps[1] + "");
/*       */       
/*       */ 
/*       */ 
/*  9348 */       AMLog.debug("Reports :Attribute Query " + query);
/*       */       
/*  9350 */       ArrayList data = ReportUtilities.getTabularData(query, false);
/*       */       try
/*       */       {
/*  9353 */         ArrayList tempdata = null;
/*  9354 */         if (hasSubGroups)
/*       */         {
/*  9356 */           tempdata = addMGTreetoMonitorName(data, nametreelist, residsinorder, "attribute");
/*  9357 */           if ((tempdata != null) && (tempdata.size() > 0))
/*       */           {
/*  9359 */             data = tempdata;
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception exc)
/*       */       {
/*  9365 */         exc.printStackTrace();
/*       */       }
/*       */       
/*       */ 
/*  9369 */       array_size = data.size();
/*       */       
/*  9371 */       HashMap p1 = ReportUtilities.getResourceIdInOrder(query, null, archivedTableName);
/*       */       
/*  9373 */       Vector RESV = (Vector)p1.get("RESOUREID");
/*  9374 */       Vector ATTV = (Vector)p1.get("ATTRIBUTEID");
/*       */       
/*  9376 */       if (data.size() == 0) {
/*  9377 */         AMLog.debug("Reports : No data for " + resourceType);
/*       */         
/*  9379 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(nodata));
/*  9380 */         saveMessages(request, messages);
/*  9381 */         request.setAttribute("heading", "0");
/*  9382 */         request.setAttribute("strTime", "0");
/*  9383 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/*  9386 */       String s = ReportUtilities.getConvertedToString(RESV);
/*  9387 */       String a = ReportUtilities.getConvertedToString(ATTV);
/*       */       
/*  9389 */       Hashtable ht = new Hashtable();
/*       */       
/*  9391 */       int attributeid = 0;
/*       */       String s1;
/*  9393 */       for (int k = 0; k < data.size(); k++) {
/*  9394 */         ArrayList dataend = (ArrayList)data.get(k);
/*       */         
/*  9396 */         if (dataend.get(7).getClass().getName().equalsIgnoreCase("java.lang.Integer"))
/*       */         {
/*  9398 */           attributeid = ((Integer)dataend.get(7)).intValue();
/*       */         } else {
/*  9400 */           attributeid = ((Long)dataend.get(7)).intValue();
/*       */         }
/*       */         
/*       */ 
/*  9404 */         int resoid = Integer.parseInt(String.valueOf(dataend.get(4)));
/*  9405 */         String dname = (String)dataend.get(0);
/*  9406 */         if ((attributeid == 319) || (attributeid == 219) || (attributeid == 519) || (attributeid == 35) || (attributeid == 525) || (attributeid == 235) || (attributeid == 213) || (attributeid == 508) || (attributeid == 2619) || (attributeid == 2617))
/*       */         {
/*  9408 */           ht = ReportUtilities.getDisplayName(resoid);
/*       */           
/*  9410 */           dname = (String)ht.get(String.valueOf(resoid));
/*       */ 
/*       */ 
/*       */         }
/*  9414 */         else if (attributeid == 711) {
/*  9415 */           HashMap alldisplayname = DBUtil.getDisplayNameForDisk();
/*  9416 */           String name = (String)dataend.get(0);
/*  9417 */           dname = FormatUtil.findReplace(name, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/*  9418 */           String[] temp1 = dname.split(":", 2);
/*  9419 */           if ((temp1[0] != null) && (alldisplayname.get(temp1[0]) != null))
/*       */           {
/*  9421 */             s1 = alldisplayname.get(temp1[0]).toString();
/*  9422 */             if ((s1 != null) && (temp1.length > 1))
/*       */             {
/*  9424 */               dname = s1 + ":" + temp1[1];
/*       */             }
/*       */           }
/*       */           else
/*       */           {
/*  9429 */             System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& DISK UTILIZATION: NULL in DBUtil.getDisplayNameForDisk() &&&&&&&&&&&&");
/*  9430 */             System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& DBUtil.getDisplayNameForDisk() -------->" + alldisplayname);
/*  9431 */             System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& NULL for Key : temp1[0] -------->" + temp1[0]);
/*       */           }
/*       */           
/*       */ 
/*       */         }
/*  9436 */         else if (allSecondLevelAttribute.contains(attributeid + ""))
/*       */         {
/*  9438 */           String displayname = ReportUtil.getDisplayNameForAttribute(resoid);
/*       */           
/*  9440 */           String name = (String)dataend.get(0);
/*       */           
/*  9442 */           dname = displayname + "_" + name;
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9449 */         dataend.add(dname);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9456 */       ArrayList singleData = (ArrayList)data.get(0);
/*  9457 */       RES_ID = s;
/*  9458 */       ATT_ID = a;
/*  9459 */       request.setAttribute("strTime", new java.util.Date(((Long)singleData.get(5)).longValue()));
/*  9460 */       request.setAttribute("endTime", new java.util.Date(((Long)singleData.get(6)).longValue()));
/*  9461 */       request.setAttribute("data", data);
/*  9462 */       request.setAttribute("RESOURCEIDS", s);
/*  9463 */       request.setAttribute("ATTRIBUTEIDS", a);
/*  9464 */       request.setAttribute("methodName", "generateTrendReport");
/*  9465 */       request.setAttribute("AttDispalyName", request.getAttribute("AttDispalyName"));
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9471 */       String Units = controls.getUnit();
/*       */       
/*  9473 */       if ((Units != null) && (!Units.equals(""))) {
/*  9474 */         Units = "(" + Units + ")";
/*       */ 
/*       */ 
/*       */       }
/*  9478 */       else if (request.getAttribute("Units") != null)
/*       */       {
/*  9480 */         Units = "(" + (String)request.getAttribute("Units") + ")";
/*       */       }
/*       */       else
/*       */       {
/*  9484 */         Units = "";
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  9489 */       String yaxis = "";
/*  9490 */       if (request.getAttribute("attributeDispalyName") != null)
/*       */       {
/*  9492 */         yaxis = (String)request.getAttribute("attributeDispalyName") + " " + Units;
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  9497 */         List attList = ReportUtil.getAttribsForResType(resourceType);
/*  9498 */         for (int j = 0; j < attList.size(); j++) {
/*  9499 */           String res = attList.get(j).toString();
/*  9500 */           String[] temp = res.split("#");
/*  9501 */           if (temp[0].equals(attribute))
/*       */           {
/*  9503 */             yaxis = temp[1];
/*  9504 */             break;
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*  9509 */         String heading = yaxis + " - " + valueforpdf;
/*  9510 */         String graphheading = yaxis + " - " + valueforpdf;
/*  9511 */         request.setAttribute("attributeDispalyName", yaxis);
/*  9512 */         request.setAttribute("heading", heading);
/*  9513 */         request.setAttribute("graphheading", graphheading);
/*       */       }
/*       */       
/*  9516 */       String[] t = yaxis.split(" ");
/*  9517 */       request.setAttribute("AttDispalyName", t[0]);
/*  9518 */       request.setAttribute("unitstoshow", Units);
/*  9519 */       ChartInfo cinfo; if (controls.getReporttype().equals("pdf")) {
/*  9520 */         cinfo = new ChartInfo();
/*  9521 */         SummaryBean sumgraph = new SummaryBean();
/*       */         
/*  9523 */         sumgraph.setResid(RES_ID);
/*  9524 */         if (Constants.sqlManager)
/*       */         {
/*  9526 */           if (DBUtil.windowsAttributeList.contains(ATT_ID)) {
/*  9527 */             sumgraph.setResid(hostresid);
/*  9528 */           } else if (DBUtil.querymonitorAttributeList.contains(ATT_ID)) {
/*  9529 */             sumgraph.setResid(queryresid);
/*       */           }
/*       */         }
/*  9532 */         sumgraph.setAttributeid(ATT_ID);
/*  9533 */         sumgraph.setStarttime((String)request.getAttribute("STIME"));
/*  9534 */         sumgraph.setDailyRptStarttime((String)request.getAttribute("dailyStime"));
/*  9535 */         sumgraph.setDailyRptEndtime((String)request.getAttribute("dailyEtime"));
/*  9536 */         sumgraph.setEndtime((String)request.getAttribute("ETIME"));
/*  9537 */         sumgraph.setArchivedforUrl(true);
/*  9538 */         sumgraph.setCompareUrls(true);
/*  9539 */         sumgraph.setPeriod(period);
/*  9540 */         sumgraph.setCategory(attname);
/*  9541 */         sumgraph.setMethodName("generateAttributeReport");
/*  9542 */         if ("true".equals(Constants.attributesReportGraphType)) {
/*  9543 */           sumgraph.setBarData(data);
/*  9544 */           String unitToPDF = controls.getUnit();
/*  9545 */           if ((unitToPDF != null) && (!unitToPDF.equals("")))
/*       */           {
/*  9547 */             sumgraph.setUnit(unitToPDF);
/*       */           }
/*       */           else
/*       */           {
/*  9551 */             sumgraph.setUnit("");
/*       */           }
/*  9553 */           cinfo.setXaxisLabel(FormatUtil.getString("Monitors"));
/*  9554 */           cinfo.setMaxBarWidth(0.03D);
/*  9555 */           cinfo.setLegend("false");
/*  9556 */           cinfo.setHeight("300");
/*  9557 */           cinfo.setWidth("700");
/*       */         }
/*       */         else
/*       */         {
/*  9561 */           cinfo.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*  9562 */           int sizeofdata = array_size;
/*  9563 */           if (sizeofdata < 5) {
/*  9564 */             cinfo.setHeight("300");
/*  9565 */             cinfo.setWidth("700");
/*       */           } else {
/*  9567 */             cinfo.setHeight("500");
/*  9568 */             cinfo.setWidth("700");
/*       */           }
/*       */         }
/*  9571 */         if ((t != null) && (t.length > 0)) {
/*  9572 */           sumgraph.setResourceName(t[0]);
/*       */         }
/*  9574 */         cinfo.setYaxisLabel(yaxis);
/*  9575 */         cinfo.setShape(true);
/*  9576 */         cinfo.setCustomDateAxis(true);
/*  9577 */         cinfo.setCustomAngle(270.0D);
/*  9578 */         cinfo.setDataSet(sumgraph);
/*  9579 */         request.setAttribute("reportname", "AttributeReport");
/*  9580 */         request.setAttribute("height", cinfo.getHeight());
/*  9581 */         String image = null;
/*  9582 */         if ("true".equals(Constants.attributesReportGraphType))
/*       */         {
/*  9584 */           image = cinfo.get3DbarChartAsJPG();
/*       */         }
/*       */         else
/*       */         {
/*  9588 */           image = cinfo.getTimeChartAsJPG();
/*       */         }
/*       */         
/*       */ 
/*  9592 */         request.setAttribute("attributeImage", image);
/*  9593 */         request.setAttribute("period", period);
/*  9594 */         request.setAttribute("periodvalue", valueforpdf);
/*  9595 */         request.setAttribute("report-type-template", "report.perf");
/*  9596 */         return mapping.findForward("report.perf.pdf");
/*       */       }
/*  9598 */       if (controls.getReporttype().equals("csv")) {
/*  9599 */         return mapping.findForward("report.perf.csv");
/*       */       }
/*       */       
/*       */ 
/*  9603 */       return mapping.findForward("report.perf");
/*       */     }
/*       */     catch (Exception exp) {
/*       */       long[] timeStamps;
/*  9607 */       exp.printStackTrace();
/*  9608 */       AMLog.fatal("Reports :  Exception ", exp);
/*  9609 */       request.setAttribute("heading", "0");
/*  9610 */       request.setAttribute("strTime", "0");
/*  9611 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  9612 */       saveMessages(request, messages);
/*  9613 */       return mapping.findForward("report.message");
/*       */     }
/*       */     finally {
/*  9616 */       closeResultSet(set);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private String getTopNValues(String query, String limit)
/*       */   {
/*       */     try
/*       */     {
/*  9626 */       if ((limit == null) || (limit.equals("")) || (limit.equals("-1")) || (limit.equals("0")))
/*       */       {
/*  9628 */         return query;
/*       */       }
/*       */       
/*       */ 
/*  9632 */       return DBQueryUtil.getTopNValues(query, limit);
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  9637 */       ex.printStackTrace();
/*       */     }
/*  9639 */     return DBQueryUtil.getTopNValues(query, limit);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ActionForward generateGlanceReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9647 */     ActionMessages messages = new ActionMessages();
/*  9648 */     ResultSet set = null;
/*  9649 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  9650 */     ReportForm controls = (ReportForm)form;
/*  9651 */     String haid = controls.getHaid();
/*  9652 */     String period = controls.getPeriod();
/*  9653 */     String resourceType = controls.getResourceType();
/*  9654 */     String eumCondition = "";
/*  9655 */     String resid = controls.getResid();
/*  9656 */     String dname = ReportUtilities.getLabelName(resid);
/*  9657 */     String customfield = controls.getCustomfield();
/*  9658 */     String customValue = controls.getCustomFieldValue();
/*  9659 */     request.setAttribute("customfield", customfield);
/*  9660 */     request.setAttribute("customFieldValue", customValue);
/*  9661 */     controls.setDisplayname(dname);
/*  9662 */     boolean isHAI = false;
/*  9663 */     ReportGraphs rg = new ReportGraphs();
/*  9664 */     String timeseriesImage = null;
/*  9665 */     ArrayList images = new ArrayList();
/*  9666 */     Hashtable sla = new Hashtable();
/*  9667 */     String eumMonId = controls.getEumMonId();
/*  9668 */     String validEumRep = controls.getEumReport();
/*  9669 */     boolean isValidEumRep = false;
/*  9670 */     request.setAttribute("eumReport", validEumRep);
/*       */     
/*  9672 */     if ((request.getParameter("isEUM") != null) && (request.getParameter("isEUM").equals("true"))) {
/*  9673 */       validEumRep = "true";
/*  9674 */       eumMonId = request.getParameter("resourceid");
/*  9675 */       resourceType = request.getParameter("resourceType");
/*  9676 */       controls.setResourceType(resourceType);
/*       */     }
/*       */     
/*  9679 */     if ((validEumRep != null) && (validEumRep.equals("true")))
/*       */     {
/*  9681 */       isValidEumRep = true;
/*  9682 */       if ((eumMonId != null) && (!"all".equalsIgnoreCase(eumMonId.trim())) && (eumMonId.trim().length() > 0)) {
/*  9683 */         resourceType = ReportUtilities.getResourceType(eumMonId);
/*  9684 */         Vector<String> res_ids = Constants.getEUMChildMonitorIds(eumMonId, false);
/*  9685 */         String query = "select RESOURCEID,TYPE from AM_PARENTCHILDMAPPER,AM_ManagedObject where PARENTID in (" + res_ids.toString().substring(1, res_ids.toString().length() - 1) + ") and CHILDID=RESOURCEID and TYPE like '%ROW%'";
/*  9686 */         ResultSet rs = null;
/*  9687 */         HashSet restype = new HashSet();
/*       */         try
/*       */         {
/*  9690 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  9691 */           while ((rs != null) && (rs.next()))
/*       */           {
/*  9693 */             res_ids.add(rs.getString(1));
/*  9694 */             restype.add(rs.getString(2));
/*       */           }
/*  9696 */           resourceType = resourceType + (restype.size() > 0 ? "," + restype.toString().substring(1, restype.toString().length() - 1) : "");
/*  9697 */           resourceType = resourceType.replaceAll(",", "','");
/*       */         }
/*       */         catch (Exception e)
/*       */         {
/*  9701 */           e.printStackTrace();
/*       */         }
/*       */         finally
/*       */         {
/*  9705 */           if (rs != null)
/*       */           {
/*  9707 */             AMConnectionPool.closeResultSet(rs);
/*       */           }
/*       */         }
/*  9710 */         eumCondition = " and " + ReportUtilities.getCondition("RESOURCEID", res_ids);
/*  9711 */         request.setAttribute("headingCategory", ReportUtilities.getLabelName(eumMonId));
/*       */       }
/*       */     }
/*       */     
/*  9715 */     controls.setEumReport("false");
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9721 */     Color[] colorsArray = { new Color(153, 0, 153), new Color(51, 204, 0), new Color(255, 0, 0), new Color(245, 16, 236), new Color(0, 89, 189), new Color(140, 100, 40), new Color(71, 190, 250), new Color(93, 18, 225), new Color(52, 255, 50) };
/*  9722 */     long[] timeStamps = null;
/*  9723 */     request.setAttribute("HelpKey", "Monitor Group Reports");
/*  9724 */     String nodata = "report.nodata";
/*  9725 */     if ((controls.getStartDate() == null) || (controls.getStartDate().equals("")) || (controls.getEndDate().equals("")))
/*       */     {
/*  9727 */       timeStamps = ReportUtilities.getTimeStamp(period);
/*       */     }
/*       */     else
/*       */     {
/*  9731 */       period = "4";
/*       */       try {
/*  9733 */         timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */       } catch (IllegalArgumentException iae) {
/*  9735 */         String errMsg = iae.getMessage();
/*  9736 */         AMLog.debug("Reports :  " + errMsg);
/*  9737 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/*  9738 */         saveMessages(request, messages);
/*  9739 */         request.setAttribute("heading", "0");
/*  9740 */         request.setAttribute("strTime", "0");
/*  9741 */         return mapping.findForward("report.message");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  9746 */     controls.setPeriod(period);
/*  9747 */     request.setAttribute("period", period);
/*  9748 */     String resourceidsinMG = "";
/*  9749 */     Vector resTypeinMG = new Vector();
/*  9750 */     String hid = request.getParameter("hid");
/*       */     
/*  9752 */     ResultSet rs = null;
/*  9753 */     List datacollectionStartedMOs = null;
/*  9754 */     StringBuffer condition = new StringBuffer();
/*       */     
/*       */ 
/*       */     try
/*       */     {
/*  9759 */       if (((hid == null) || ((hid != null) && (hid.equals("false")))) && ((haid == null) || ((haid != null) && (haid.equals("null")))) && (resourceType != null))
/*       */       {
/*  9761 */         getMonitors(mapping, form, request, response);
/*  9762 */         request.setAttribute("hid", "false");
/*  9763 */         controls.setWorkingdays("false");
/*       */         
/*  9765 */         if ((customfield != null) && (customfield.equals("true")) && (customValue != null) && (customValue.indexOf("$") != -1)) {
/*  9766 */           datacollectionStartedMOs = MyFields.customFieldResourceIDs(customValue, resourceType);
/*       */         } else {
/*  9768 */           datacollectionStartedMOs = getCreationTimeForResourceTypes(resourceType);
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/*  9773 */         if (request.getAttribute("headingCategory") == null)
/*       */         {
/*  9775 */           request.setAttribute("headingCategory", dname);
/*       */         }
/*  9777 */         if (request.getAttribute("headingPeriod") == null)
/*       */         {
/*  9779 */           request.setAttribute("headingPeriod", controls.getReportPeriod());
/*       */         }
/*  9781 */         getHolisticApps(mapping, form, request, response);
/*  9782 */         controls.setWorkingdays("true");
/*  9783 */         controls.setHaid(haid);
/*  9784 */         request.setAttribute("hid", "true");
/*  9785 */         isHAI = true;
/*  9786 */         StringBuffer tempBuffer = ReportUtil.getAllMonitorsInMG(haid, null, null);
/*  9787 */         resourceidsinMG = tempBuffer.deleteCharAt(tempBuffer.length() - 1).toString();
/*  9788 */         String resTypeQuery = "select amt.SUBGROUP from AM_ManagedResourceType as amt,AM_ManagedObject as mo where mo.RESOURCEID in (" + resourceidsinMG + ") and mo.TYPE = amt.RESOURCETYPE and amt.SUBGROUP != 'HAI' group by SUBGROUP";
/*       */         try {
/*  9790 */           rs = AMConnectionPool.executeQueryStmt(resTypeQuery);
/*  9791 */           while (rs.next())
/*       */           {
/*  9793 */             resTypeinMG.add(rs.getString("SUBGROUP"));
/*       */           }
/*       */           
/*  9796 */           if ((resTypeinMG != null) && (resTypeinMG.size() > 0))
/*       */           {
/*       */ 
/*       */ 
/*  9800 */             for (int i = 0; i < resTypeinMG.size(); i++)
/*       */             {
/*  9802 */               if (i + 1 == resTypeinMG.size())
/*       */               {
/*  9804 */                 condition.append(resTypeinMG.get(i));
/*       */ 
/*       */               }
/*       */               else
/*       */               {
/*  9809 */                 condition.append(resTypeinMG.get(i) + "','");
/*       */               }
/*       */             }
/*       */             
/*  9813 */             resourceType = condition.toString();
/*  9814 */             datacollectionStartedMOs = getCreationTimeForResourceTypes(resourceType);
/*       */           }
/*       */           
/*       */         }
/*       */         catch (Exception ee)
/*       */         {
/*  9820 */           ee.printStackTrace();
/*       */         }
/*  9822 */         controls.setHaid(haid);
/*  9823 */         if ((resTypeinMG == null) || ((resTypeinMG != null) && (resTypeinMG.size() == 0)))
/*       */         {
/*  9825 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodatacollection.monitors"));
/*  9826 */           saveMessages(request, messages);
/*  9827 */           request.setAttribute("heading", "0");
/*  9828 */           request.setAttribute("strTime", "0");
/*  9829 */           return mapping.findForward("report.message");
/*       */         }
/*       */         
/*       */       }
/*       */     }
/*       */     catch (Exception eeee)
/*       */     {
/*  9836 */       eeee.printStackTrace();
/*       */     }
/*  9838 */     long startTime = timeStamps[0];
/*  9839 */     long endTime = timeStamps[1];
/*  9840 */     String getresids = null;
/*       */     
/*       */ 
/*  9843 */     ArrayList data = new ArrayList();
/*       */     
/*  9845 */     String scheduleid = request.getParameter("scheduleid");
/*  9846 */     int cust = -1;
/*  9847 */     int site = -1;
/*       */     
/*  9849 */     if ((EnterpriseUtil.isIt360MSPEdition()) && (scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */     {
/*  9851 */       ArrayList al = EnterpriseUtil.getCustomerSiteFromScheduleDetails(scheduleid);
/*  9852 */       if ((al != null) && (al.size() >= 2))
/*       */       {
/*  9854 */         cust = Integer.parseInt(al.get(0).toString());
/*  9855 */         site = Integer.parseInt(al.get(1).toString());
/*       */       }
/*       */     }
/*       */     
/*  9859 */     Vector resourceids = null;
/*       */     
/*       */ 
/*  9862 */     if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  9863 */       String owner = request.getRemoteUser();
/*  9864 */       resourceids = ReportUtilities.getResourceIdentity(owner, request);
/*  9865 */       if ((scheduleid != null) && (!scheduleid.trim().equals("")))
/*       */       {
/*  9867 */         resourceids = EnterpriseUtil.getResourceIdsForSite(site);
/*       */       }
/*       */       
/*  9870 */       if (Constants.isUserResourceEnabled()) {
/*  9871 */         String loginuserid = Constants.getLoginUserid(request);
/*  9872 */         getresids = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType,AM_USERRESOURCESTABLE where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "') and AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginuserid + eumCondition;
/*       */       } else {
/*  9874 */         getresids = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "') and " + ReportUtilities.getCondition("RESOURCEID", resourceids) + eumCondition;
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*  9881 */       getresids = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.SUBGROUP in ('" + resourceType + "') " + eumCondition;
/*       */     }
/*       */     
/*       */ 
/*  9885 */     if ((isHAI) && (resTypeinMG != null) && (resTypeinMG.size() > 0))
/*       */     {
/*  9887 */       getresids = getresids + " and AM_ManagedObject.RESOURCEID in (" + resourceidsinMG + " ) and AM_ManagedResourceType.SUBGROUP != 'HAI'";
/*       */     }
/*       */     
/*  9890 */     ArrayList resources = new ArrayList();
/*  9891 */     Properties residdisplaynamemapping = new Properties();
/*  9892 */     Hashtable residVsMinTime = new Hashtable();
/*  9893 */     ArrayList availList = new ArrayList();
/*  9894 */     ArrayList parentAvailList = new ArrayList();
/*  9895 */     int limit = "csv".equals(controls.getReporttype()) ? -1 : 10;
/*       */     try
/*       */     {
/*  9898 */       set = AMConnectionPool.executeQueryStmt(getresids);
/*  9899 */       boolean hasMonitorInstance = false;
/*  9900 */       boolean dataCollectionNotStarted = false;
/*  9901 */       while (set.next()) {
/*  9902 */         String resID = String.valueOf(set.getInt("RESOURCEID"));
/*  9903 */         if (!datacollectionStartedMOs.contains(resID)) {
/*  9904 */           dataCollectionNotStarted = true;
/*       */         }
/*       */         else {
/*  9907 */           resources.add(resID);
/*  9908 */           String displayName = EnterpriseUtil.decodeString(set.getString("DISPLAYNAME"));
/*  9909 */           residdisplaynamemapping.setProperty(resID, displayName);
/*  9910 */           hasMonitorInstance = true;
/*       */         } }
/*  9912 */       closeResultSet(set);
/*  9913 */       AMLog.debug("Reports :  residdisplaynamemapping " + residdisplaynamemapping.toString());
/*  9914 */       AMLog.debug("Reports :  resources " + resources.toString());
/*  9915 */       if (!hasMonitorInstance) {
/*  9916 */         if (dataCollectionNotStarted) {
/*  9917 */           AMLog.debug("Reports : Data Collection not started for " + resourceType);
/*  9918 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodatacollection.monitors"));
/*  9919 */           saveMessages(request, messages);
/*  9920 */           request.setAttribute("heading", "0");
/*  9921 */           request.setAttribute("strTime", "0");
/*  9922 */           return mapping.findForward("report.message");
/*       */         }
/*  9924 */         AMLog.debug("Reports : No data for " + resourceType);
/*  9925 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.monitors"));
/*  9926 */         saveMessages(request, messages);
/*  9927 */         request.setAttribute("heading", "0");
/*  9928 */         request.setAttribute("strTime", "0");
/*  9929 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/*  9932 */       long stTime = startTime;
/*  9933 */       residVsMinTime = ReportUtilities.getMinTimeInDB(new Vector(resources));
/*  9934 */       Enumeration enum1 = residVsMinTime.keys();
/*       */       
/*  9936 */       long totalduration = 0L;
/*  9937 */       Properties durationProp = new Properties();
/*  9938 */       while (enum1.hasMoreElements()) {
/*  9939 */         startTime = stTime;
/*  9940 */         String resourceID = (String)enum1.nextElement();
/*  9941 */         String minTime = String.valueOf(residVsMinTime.get(resourceID));
/*  9942 */         long minTimeInDB = Long.parseLong(minTime);
/*  9943 */         long tempStartTime = startTime;
/*  9944 */         if (minTimeInDB > tempStartTime) {
/*  9945 */           tempStartTime = minTimeInDB;
/*       */         }
/*  9947 */         if (tempStartTime > endTime) {
/*  9948 */           resources.remove(resourceID);
/*       */         }
/*       */       }
/*  9951 */       if (resources.size() == 0)
/*       */       {
/*  9953 */         AMLog.debug("Reports : No data for " + resourceType);
/*  9954 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.time"));
/*  9955 */         saveMessages(request, messages);
/*  9956 */         request.setAttribute("heading", "0");
/*  9957 */         request.setAttribute("strTime", "0");
/*  9958 */         return mapping.findForward("report.message");
/*       */       }
/*  9960 */       Vector resIDVect = new Vector(resources);
/*  9961 */       ArrayList residsWithDownEntry = new ArrayList();
/*  9962 */       String query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,TYPE from AM_MO_DowntimeData where " + ReportUtilities.getCondition("RESID", resIDVect) + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,TYPE order by RESID";
/*  9963 */       if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/*  9964 */         query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,TYPE from AM_MO_DowntimeData where " + ReportUtilities.getCondition("RESID", resIDVect) + " and TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,TYPE order by RESID";
/*       */       }
/*  9966 */       if (isValidEumRep)
/*       */       {
/*  9968 */         String downTimeQuery = "select mo.DISPLAYNAME,RESID,(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end ) as UPTIME,(case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as DOWNTIME,reason.SHORT_DESCRIPTION,downtable.TYPE,downtable.REASONID from AM_ManagedObject mo,AM_MO_DowntimeData downtable left outer join AM_DOWNTIMEREASON reason on reason.REASONID=downtable.REASONID where " + ReportUtilities.getCondition("RESID", resIDVect) + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and downtable.RESID=mo.RESOURCEID order by RESID";
/*  9969 */         updateEumDownData(downTimeQuery, request);
/*       */       }
/*  9971 */       AMLog.debug("REPORTS : GLANCE REPORT=> AVAILABILITY DATA QUERY => " + query);
/*  9972 */       set = AMConnectionPool.executeQueryStmt(query);
/*       */       
/*       */ 
/*       */ 
/*  9976 */       Properties rows = new Properties();
/*  9977 */       long totaldowntime = 0L;
/*  9978 */       long totalUnmanagedtime = 0L;
/*  9979 */       long totalScheduledtime = 0L;
/*  9980 */       int count = 0;
/*  9981 */       long uptime = 0L;
/*  9982 */       String prevResID = null;
/*  9983 */       String resourceID = "";
/*  9984 */       while (set.next()) {
/*  9985 */         resourceID = set.getString("RESID");
/*  9986 */         String minTime = String.valueOf(residVsMinTime.get(resourceID));
/*  9987 */         long minTimeInDB = Long.parseLong(minTime);
/*  9988 */         long tempStartTime = startTime;
/*  9989 */         if (minTimeInDB > tempStartTime) {
/*  9990 */           tempStartTime = minTimeInDB;
/*       */         }
/*  9992 */         totalduration = endTime - tempStartTime;
/*  9993 */         if ((prevResID == null) || ((prevResID != null) && (!prevResID.equals(resourceID))))
/*       */         {
/*  9995 */           residsWithDownEntry.add(resourceID);
/*  9996 */           prevResID = resourceID;
/*  9997 */           rows = new Properties();
/*  9998 */           data.add(rows);
/*  9999 */           totaldowntime = 0L;
/* 10000 */           totalUnmanagedtime = 0L;
/* 10001 */           totalScheduledtime = 0L;
/*       */         }
/* 10003 */         long downtime = set.getLong("TotalDownTime");
/* 10004 */         count = set.getInt("DownCount");
/* 10005 */         uptime = totalduration - downtime;
/* 10006 */         int downType = set.getInt("TYPE");
/* 10007 */         if (uptime < 0L) {
/* 10008 */           AMLog.fatal("Reports :  Fatal Error****************************************************");
/* 10009 */           AMLog.fatal("Reports :  Error in the stored  availability information for " + set.getString("RESID"));
/* 10010 */           AMLog.fatal("Reports :  totalduration " + totalduration);
/* 10011 */           AMLog.fatal("Reports :  downtime " + downtime);
/* 10012 */           AMLog.fatal("Reports :  type " + downType);
/* 10013 */           AMLog.fatal("Reports :  execute :select * from AM_MO_DowntimeData where RESID " + resourceID);
/* 10014 */           AMLog.fatal("Reports :  Error : It has multiple rows with UPTIME=0");
/* 10015 */           AMLog.fatal("Reports :  ***************************************************************");
/*       */         }
/*       */         else {
/* 10018 */           if (1 == downType)
/*       */           {
/* 10020 */             totaldowntime += downtime;
/*       */           }
/* 10022 */           else if (2 == downType)
/*       */           {
/* 10024 */             totalUnmanagedtime += downtime;
/*       */           }
/*       */           else
/*       */           {
/* 10028 */             totalScheduledtime += downtime;
/*       */           }
/*       */           
/* 10031 */           String moname = residdisplaynamemapping.getProperty(resourceID);
/* 10032 */           rows.setProperty("Name", moname);
/* 10033 */           rows.setProperty("resourceid", String.valueOf(resourceID));
/*       */           
/* 10035 */           rows.put("DowntimeInLong", new Long(totaldowntime + totalUnmanagedtime + totalScheduledtime));
/* 10036 */           uptime = totalduration - (totaldowntime + totalUnmanagedtime + totalScheduledtime);
/* 10037 */           float upPercent = (float)uptime / (float)totalduration * 100.0F;
/* 10038 */           float downPercent = (float)totaldowntime / (float)totalduration * 100.0F;
/* 10039 */           float unManagedPercent = (float)totalUnmanagedtime / (float)totalduration * 100.0F;
/* 10040 */           float scheduledDownPercent = (float)totalScheduledtime / (float)totalduration * 100.0F;
/* 10041 */           rows.setProperty("available", String.valueOf(Math.round(upPercent * 100.0F) / 100.0F));
/* 10042 */           rows.setProperty("unavailable", String.valueOf(Math.round(downPercent * 100.0F) / 100.0F));
/* 10043 */           rows.setProperty("unmanaged", String.valueOf(Math.round(unManagedPercent * 100.0F) / 100.0F));
/* 10044 */           rows.setProperty("scheduledDown", String.valueOf(Math.round(scheduledDownPercent * 100.0F) / 100.0F));
/*       */         }
/*       */       }
/* 10047 */       int currentDataSize = data.size();
/* 10048 */       int size = resources.size();
/* 10049 */       if ((currentDataSize < limit) || (limit == -1) || (isValidEumRep))
/*       */       {
/* 10051 */         for (int i = 0; i < size; i++) {
/* 10052 */           String resourceid = (String)resources.get(i);
/* 10053 */           if (!residsWithDownEntry.contains(resourceid))
/*       */           {
/* 10055 */             String moname = residdisplaynamemapping.getProperty(resourceid);
/* 10056 */             Properties rows1 = new Properties();
/* 10057 */             rows1.setProperty("Name", moname);
/* 10058 */             rows1.setProperty("resourceid", resourceid);
/*       */             
/* 10060 */             rows1.put("DowntimeInLong", new Long(0L));
/*       */             
/*       */ 
/*       */ 
/* 10064 */             rows1.setProperty("available", "100");
/* 10065 */             rows1.setProperty("unavailable", "0");
/* 10066 */             rows1.setProperty("unmanaged", "0");
/* 10067 */             rows1.setProperty("scheduledDown", "0");
/* 10068 */             data.add(rows1);
/*       */           }
/*       */         }
/*       */       }
/*       */       
/* 10073 */       Properties[] propArray = (Properties[])data.toArray(new Properties[data.size()]);
/* 10074 */       Arrays.sort(propArray, new ComparatorImpl());
/* 10075 */       data.clear();
/* 10076 */       int propArraySize = propArray.length;
/* 10077 */       for (int i = 0; i < propArraySize; i++) {
/* 10078 */         data.add(propArray[i]);
/*       */       }
/* 10080 */       if (data.size() == 0) {
/* 10081 */         AMLog.debug("Reports : No data for " + resourceType);
/*       */         
/* 10083 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(nodata));
/* 10084 */         saveMessages(request, messages);
/* 10085 */         request.setAttribute("heading", "0");
/* 10086 */         request.setAttribute("strTime", "0");
/* 10087 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/* 10090 */       int datasize = data.size();
/* 10091 */       if (isValidEumRep)
/*       */       {
/* 10093 */         for (int i = 0; i < datasize; i++)
/*       */         {
/* 10095 */           Properties availProp = (Properties)data.get(i);
/* 10096 */           Hashtable availHash = new Hashtable();
/* 10097 */           availHash.put("DISPLAYNAME", availProp.getProperty("Name"));
/* 10098 */           availHash.put("VALUE", new Float(availProp.getProperty("available")));
/* 10099 */           availHash.put("VALUE_DOWN", new Float(availProp.getProperty("unavailable")));
/* 10100 */           availHash.put("VALUE_UNMAN", new Float(availProp.getProperty("unmanaged")));
/* 10101 */           availHash.put("VALUE_SCHED", new Float(availProp.getProperty("scheduledDown")));
/* 10102 */           availHash.put("RESOURCEID", availProp.getProperty("resourceid"));
/* 10103 */           if ((Constants.getEUMChildMonitorIds(availProp.getProperty("resourceid"), false).size() > 0) && (parentAvailList.size() < limit))
/*       */           {
/* 10105 */             parentAvailList.add(availHash);
/*       */           }
/* 10107 */           else if (availList.size() < limit)
/*       */           {
/* 10109 */             availList.add(availHash);
/*       */           } else {
/* 10111 */             if ((parentAvailList.size() == limit) && (availList.size() == limit)) {
/*       */               break;
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/* 10119 */         if ((limit != -1) && (datasize > limit))
/*       */         {
/* 10121 */           for (int i = datasize - 1; i > limit - 1; i--) {
/* 10122 */             data.remove(i);
/*       */           }
/*       */         }
/* 10125 */         for (int n = 0; n < data.size(); n++)
/*       */         {
/* 10127 */           Properties availProp = (Properties)data.get(n);
/* 10128 */           Hashtable availHash = new Hashtable();
/* 10129 */           availHash.put("DISPLAYNAME", availProp.getProperty("Name"));
/* 10130 */           availHash.put("VALUE", new Float(availProp.getProperty("available")));
/* 10131 */           availHash.put("VALUE_DOWN", new Float(availProp.getProperty("unavailable")));
/* 10132 */           availHash.put("VALUE_UNMAN", new Float(availProp.getProperty("unmanaged")));
/* 10133 */           availHash.put("VALUE_SCHED", new Float(availProp.getProperty("scheduledDown")));
/* 10134 */           availHash.put("RESOURCEID", availProp.getProperty("resourceid"));
/* 10135 */           availList.add(availHash);
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/*       */ 
/* 10145 */       ee.printStackTrace();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 10152 */     ArrayList attList = new ArrayList();
/* 10153 */     ArrayList attList_Coln = new ArrayList();
/*       */     
/* 10155 */     String attids_query = "select att.ATTRIBUTEID,att.ATTRIBUTE,att.DISPLAYNAME ,att.RESOURCETYPE,att.UNITS,ext.ARCHIVEDDATA_TABLENAME ,ext.EXPRESSION from AM_ATTRIBUTES as att, AM_ManagedResourceType as amt,AM_ATTRIBUTES_EXT as ext where amt.SUBGROUP in ('" + resourceType + "') and amt.RESOURCETYPE=att.RESOURCETYPE and att.ATTRIBUTEID=ext.ATTRIBUTEID and att.TYPE <> 2 and att.TYPE <> 1 and ext.REPORTS_ENABLED=1 and ext.ATTRIBUTE_LEVEL=1 order by ATTRIBUTE";
/* 10156 */     if ((isHAI) && (resTypeinMG != null) && (resTypeinMG.size() > 0))
/*       */     {
/* 10158 */       attids_query = "select att.ATTRIBUTEID,att.ATTRIBUTE,att.DISPLAYNAME ,att.RESOURCETYPE,att.UNITS,ext.ARCHIVEDDATA_TABLENAME ,ext.EXPRESSION from AM_ATTRIBUTES as att, AM_ManagedResourceType as amt,AM_ATTRIBUTES_EXT as ext where " + ReportUtilities.getConditionWithQuotes("amt.SUBGROUP", resTypeinMG) + " and amt.RESOURCETYPE=att.RESOURCETYPE and att.ATTRIBUTEID=ext.ATTRIBUTEID and att.TYPE <> 2 and att.TYPE <> 1 and ext.REPORTS_ENABLED=1 and ext.ATTRIBUTE_LEVEL=1 order by ATTRIBUTE";
/*       */ 
/*       */     }
/* 10161 */     else if (isValidEumRep)
/*       */     {
/* 10163 */       attids_query = "select att.ATTRIBUTEID,att.ATTRIBUTE,att.DISPLAYNAME ,att.RESOURCETYPE,att.UNITS,ext.ARCHIVEDDATA_TABLENAME ,ext.EXPRESSION,ext.REPORTS_ENABLED from AM_ATTRIBUTES as att,AM_ATTRIBUTES_EXT as ext where att.RESOURCETYPE in ('" + resourceType + "') and att.ATTRIBUTEID=ext.ATTRIBUTEID and att.TYPE <> 2 and att.TYPE <> 1 and ext.REPORTS_ENABLED=1 order by ATTRIBUTE";
/*       */     }
/*       */     
/*       */ 
/*       */     try
/*       */     {
/* 10169 */       rs = AMConnectionPool.executeQueryStmt(attids_query);
/* 10170 */       while (rs.next())
/*       */       {
/* 10172 */         Hashtable attribProp = new Hashtable();
/* 10173 */         attribProp.put("ATTRIBUTE", rs.getString("ATTRIBUTE"));
/* 10174 */         attribProp.put("ATTRIBUTEID", rs.getString("ATTRIBUTEID"));
/* 10175 */         attribProp.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 10176 */         attribProp.put("RESOURCETYPE", rs.getString("RESOURCETYPE"));
/* 10177 */         attribProp.put("ARCHIVEDDATA_TABLENAME", rs.getString("ARCHIVEDDATA_TABLENAME"));
/* 10178 */         attribProp.put("EXPRESSION", rs.getString("EXPRESSION"));
/* 10179 */         if (rs.getString("UNITS") != null)
/*       */         {
/* 10181 */           attribProp.put("UNIT", rs.getString("UNITS"));
/*       */         }
/*       */         else
/*       */         {
/* 10185 */           attribProp.put("UNIT", " ");
/*       */         }
/* 10187 */         attList.add(attribProp);
/*       */       }
/*       */       
/* 10190 */       Hashtable attribHash = new Hashtable();
/* 10191 */       Vector attids = new Vector();
/* 10192 */       String previousAttrib = "";
/*       */       
/*       */ 
/* 10195 */       for (int i = 0; i < attList.size(); i++)
/*       */       {
/* 10197 */         Hashtable attribProp = (Hashtable)attList.get(i);
/* 10198 */         if (i == 0)
/*       */         {
/* 10200 */           previousAttrib = (String)attribProp.get("ATTRIBUTE");
/* 10201 */           attids.addElement((String)attribProp.get("ATTRIBUTEID"));
/* 10202 */           attribHash = (Hashtable)attList.get(i);
/*       */           
/*       */ 
/* 10205 */           if (i == attList.size() - 1)
/*       */           {
/* 10207 */             attribHash.put("ATTRIBUTEID", attids);
/* 10208 */             attList_Coln.add(attribHash);
/*       */           }
/*       */         }
/* 10211 */         else if ((i < attList.size() - 1) && (previousAttrib.equals((String)attribProp.get("ATTRIBUTE"))))
/*       */         {
/* 10213 */           attids.addElement((String)attribProp.get("ATTRIBUTEID"));
/*       */         }
/* 10215 */         else if ((i < attList.size()) && (!previousAttrib.equals((String)attribProp.get("ATTRIBUTE"))))
/*       */         {
/*       */ 
/* 10218 */           previousAttrib = (String)attribProp.get("ATTRIBUTE");
/* 10219 */           attribHash.remove("ATTRIBUTEID");
/* 10220 */           attribHash.put("ATTRIBUTEID", attids);
/* 10221 */           attList_Coln.add(attribHash);
/* 10222 */           attribHash = new Hashtable();
/* 10223 */           attribHash = attribProp;
/* 10224 */           attids = new Vector();
/* 10225 */           attids.addElement((String)attribProp.get("ATTRIBUTEID"));
/*       */           
/*       */ 
/* 10228 */           if (i == attList.size() - 1)
/*       */           {
/* 10230 */             attribHash.put("ATTRIBUTEID", attids);
/* 10231 */             attList_Coln.add(attribHash);
/*       */           }
/*       */           
/*       */         }
/*       */         else
/*       */         {
/* 10237 */           attids.addElement((String)attribProp.get("ATTRIBUTEID"));
/* 10238 */           attribHash.remove("ATTRIBUTEID");
/* 10239 */           attribHash.put("ATTRIBUTEID", attids);
/* 10240 */           attList_Coln.add(attribHash);
/* 10241 */           attribHash = new Hashtable();
/* 10242 */           attribHash = attribProp;
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/* 10251 */       ee.printStackTrace();
/*       */     }
/* 10253 */     request.setAttribute("strTime", new java.util.Date(timeStamps[0]));
/* 10254 */     request.setAttribute("endTime", new java.util.Date(timeStamps[1]));
/* 10255 */     ArrayList retList = new ArrayList();
/* 10256 */     ArrayList attribList = new ArrayList();
/* 10257 */     ArrayList attribUnitList = new ArrayList();
/*       */     
/* 10259 */     if (isValidEumRep)
/*       */     {
/* 10261 */       retList.add(parentAvailList);
/* 10262 */       attribList.add("Availability # Availability");
/* 10263 */       attribUnitList.add("%");
/* 10264 */       if ((controls.getReporttype().equals("pdf")) && (parentAvailList.size() > 0))
/*       */       {
/* 10266 */         Map params = new Hashtable();
/* 10267 */         params.put("type", "ATAGLANCEAVAILABILITY");
/* 10268 */         params.put("data", parentAvailList);
/* 10269 */         rg.setParams(params);
/* 10270 */         ChartInfo cinfo = new ChartInfo();
/* 10271 */         cinfo.setDataSet(rg);
/* 10272 */         cinfo.setLegend("false");
/* 10273 */         cinfo.setWidth("500");
/* 10274 */         cinfo.setHeight("300");
/* 10275 */         cinfo.setXaxisLabel("");
/* 10276 */         cinfo.setYaxisLabel(FormatUtil.getString("%"));
/* 10277 */         cinfo.setChartTitle(FormatUtil.getString("am.webclient.common.availability.text"));
/* 10278 */         cinfo.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 10279 */         cinfo.setMaxBarWidth(0.05D);
/* 10280 */         cinfo.setBarcolor(new Paint[] { new Color(0, 255, 0), new Color(255, 0, 0), new Color(255, 0, 255), new Color(93, 18, 225) });
/* 10281 */         cinfo.setLabelRotation(false);
/* 10282 */         cinfo.setTwoDimensionBar(false);
/* 10283 */         timeseriesImage = cinfo.getStackedBarChartAsJPG();
/* 10284 */         images.add(timeseriesImage);
/*       */       }
/*       */     }
/*       */     
/* 10288 */     retList.add(availList);
/* 10289 */     attribList.add("Availability # Availability");
/* 10290 */     attribUnitList.add("%");
/* 10291 */     if (controls.getReporttype().equals("pdf"))
/*       */     {
/* 10293 */       Map params = new Hashtable();
/* 10294 */       params.put("type", "ATAGLANCEAVAILABILITY");
/* 10295 */       params.put("data", availList);
/* 10296 */       rg.setParams(params);
/* 10297 */       ChartInfo cinfo = new ChartInfo();
/* 10298 */       cinfo.setDataSet(rg);
/* 10299 */       cinfo.setLegend("false");
/* 10300 */       cinfo.setWidth("500");
/* 10301 */       cinfo.setHeight("300");
/* 10302 */       cinfo.setXaxisLabel("");
/* 10303 */       cinfo.setYaxisLabel(FormatUtil.getString("%"));
/* 10304 */       cinfo.setChartTitle(FormatUtil.getString("am.webclient.common.availability.text"));
/* 10305 */       if ((isValidEumRep) && (parentAvailList.size() > 0))
/*       */       {
/* 10307 */         cinfo.setChartTitle(FormatUtil.getString("am.webclient.common.agent.availability.text"));
/*       */       }
/* 10309 */       cinfo.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 10310 */       cinfo.setMaxBarWidth(0.05D);
/* 10311 */       cinfo.setBarcolor(new Paint[] { new Color(0, 255, 0), new Color(255, 0, 0), new Color(255, 0, 255), new Color(93, 18, 225) });
/* 10312 */       cinfo.setLabelRotation(false);
/* 10313 */       if (isValidEumRep)
/*       */       {
/* 10315 */         cinfo.setTwoDimensionBar(false);
/*       */       }
/*       */       else
/*       */       {
/* 10319 */         cinfo.setTwoDimensionBar(true);
/*       */       }
/* 10321 */       timeseriesImage = cinfo.getStackedBarChartAsJPG();
/* 10322 */       images.add(timeseriesImage);
/*       */     }
/*       */     
/* 10325 */     ArrayList attList_OrdrColn = new ArrayList();
/* 10326 */     if (isHAI)
/*       */     {
/* 10328 */       attList_OrdrColn = ReportUtilities.getGlanceAttListInOrder("", attList_Coln);
/*       */     }
/*       */     else
/*       */     {
/* 10332 */       attList_OrdrColn = ReportUtilities.getGlanceAttListInOrder(resourceType, attList_Coln);
/*       */     }
/*       */     
/*       */ 
/* 10336 */     String operatorCondition = "";
/*       */     
/* 10338 */     if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition())) {
/* 10339 */       if (Constants.isUserResourceEnabled()) {
/* 10340 */         String loginUserid = Constants.getLoginUserid(request);
/* 10341 */         operatorCondition = " and mo.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")";
/*       */       } else {
/* 10343 */         operatorCondition = " and " + ReportUtilities.getCondition("mo.RESOURCEID", resourceids);
/*       */       }
/*       */     }
/* 10346 */     String customFieldCondition = "";
/* 10347 */     if ((customfield != null) && (customfield.equals("true")) && (customValue != null) && (customValue.indexOf("$") != -1)) {
/* 10348 */       customFieldCondition = " and " + ReportUtilities.getCondition("mo.RESOURCEID", MyFields.getcustomFieldResidinVector(customValue, resourceType));
/*       */     }
/* 10350 */     int colNo = 0;
/*       */     
/* 10352 */     for (int n = 0; n < attList_OrdrColn.size(); n++)
/*       */     {
/* 10354 */       Hashtable attribProp = (Hashtable)attList_OrdrColn.get(n);
/* 10355 */       if (attribProp != null)
/*       */       {
/* 10357 */         String attributeName = (String)attribProp.get("ATTRIBUTE");
/* 10358 */         String attributeDisName = (String)attribProp.get("DISPLAYNAME");
/* 10359 */         String expression = (String)attribProp.get("EXPRESSION");
/* 10360 */         String archivedTableName = (String)attribProp.get("ARCHIVEDDATA_TABLENAME");
/* 10361 */         Vector attids = (Vector)attribProp.get("ATTRIBUTEID");
/* 10362 */         String value_query = "";
/*       */         
/* 10364 */         long[] dailyRptTimestamp = ReportUtilities.getDailyStartEndTime(timeStamps[0], timeStamps[1], archivedTableName);
/*       */         
/* 10366 */         String dailyRptCondition = " and arch.DURATION=1 and arch.ARCHIVEDTIME >=" + timeStamps[0] + " and arch.ARCHIVEDTIME <=" + timeStamps[1];
/*       */         
/* 10368 */         if (dailyRptTimestamp[2] > 0L)
/*       */         {
/* 10370 */           dailyRptCondition = " and ( arch.DURATION=1 and arch.ARCHIVEDTIME >=" + dailyRptTimestamp[0] + " and arch.ARCHIVEDTIME <=" + dailyRptTimestamp[1] + " OR arch.DURATION=2 and arch.ARCHIVEDTIME >=" + dailyRptTimestamp[2] + " and arch.ARCHIVEDTIME <=" + dailyRptTimestamp[3] + ") ";
/*       */         }
/* 10372 */         String attribValueQuery = DBQueryUtil.getDBQuery("am.amreportAction.generateGlanceReport1", new String[] { archivedTableName, ReportUtilities.getCondition("arch.ATTRIBUTEID", attids), dailyRptCondition + operatorCondition + eumCondition + customFieldCondition });
/*       */         
/* 10374 */         if ((resTypeinMG != null) && (resTypeinMG.size() > 0))
/*       */         {
/*       */ 
/* 10377 */           attribValueQuery = DBQueryUtil.getDBQuery("am.amreportAction.generateGlanceReport2", new String[] { archivedTableName, ReportUtilities.getCondition("arch.ATTRIBUTEID", attids), resourceidsinMG, dailyRptCondition + operatorCondition });
/*       */         }
/* 10379 */         if (!"csv".equals(controls.getReporttype())) {
/* 10380 */           attribValueQuery = getTopNValues(attribValueQuery, "10");
/*       */         }
/* 10382 */         System.out.println("AMReportActions : Glance Report Data Query ==========>" + attribValueQuery);
/* 10383 */         ArrayList moList = new ArrayList();
/*       */         try
/*       */         {
/* 10386 */           rs = AMConnectionPool.executeQueryStmt(attribValueQuery);
/* 10387 */           while (rs.next())
/*       */           {
/* 10389 */             Hashtable datahash = new Hashtable();
/* 10390 */             datahash.put("DISPLAYNAME", EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME")));
/* 10391 */             datahash.put("RESOURCETYPE", rs.getString("TYPE"));
/* 10392 */             datahash.put("RESOURCEID", rs.getString("RESOURCEID"));
/* 10393 */             datahash.put("ATTRIBUTEID", rs.getString("ATTRIBUTEID"));
/* 10394 */             datahash.put("STARTTIME", Long.valueOf(timeStamps[0]));
/* 10395 */             datahash.put("ENDTIME", Long.valueOf(timeStamps[1]));
/* 10396 */             datahash.put("ARCHIVEDDATA_TABLENAME", archivedTableName);
/* 10397 */             int attID = rs.getInt("ATTRIBUTEID");
/* 10398 */             float avgValue = rs.getFloat("avg");
/*       */             
/* 10400 */             if ((attID == 11) || (attID == 224))
/*       */             {
/* 10402 */               avgValue /= 1024.0F;
/*       */             }
/* 10404 */             else if ((attID >= 3608) && (attID <= 3619))
/*       */             {
/* 10406 */               avgValue /= 1048576.0F;
/*       */             }
/* 10408 */             if (rs.getString("TYPE").contains("_ROW"))
/*       */             {
/* 10410 */               String query = "select RESOURCENAME from AM_PARENTCHILDMAPPER,AM_ManagedObject where CHILDID=" + rs.getString("RESOURCEID") + " and PARENTID=RESOURCEID";
/* 10411 */               ResultSet agent = null;
/*       */               try
/*       */               {
/* 10414 */                 agent = AMConnectionPool.executeQueryStmt(query);
/* 10415 */                 if ((agent != null) && (agent.next()))
/*       */                 {
/* 10417 */                   datahash.put("AGENT", agent.getString("RESOURCENAME"));
/*       */                 }
/*       */               }
/*       */               catch (Exception e)
/*       */               {
/* 10422 */                 e.printStackTrace();
/*       */               }
/*       */               finally
/*       */               {
/* 10426 */                 if (agent != null)
/*       */                 {
/* 10428 */                   AMConnectionPool.closeResultSet(agent);
/*       */                 }
/*       */               }
/*       */             }
/* 10432 */             datahash.put("VALUE", Float.valueOf(avgValue));
/* 10433 */             datahash.put("MINVALUE", Integer.valueOf(rs.getInt("min")));
/* 10434 */             datahash.put("MAXVALUE", Integer.valueOf(rs.getInt("max")));
/* 10435 */             moList.add(datahash);
/*       */           }
/* 10437 */           if ((moList != null) && (moList.size() > 0))
/*       */           {
/* 10439 */             if (!isValidEumRep)
/*       */             {
/* 10441 */               attribList.add(attributeName + " # " + attributeDisName);
/* 10442 */               attribUnitList.add((String)attribProp.get("UNIT"));
/* 10443 */               retList.add(moList);
/* 10444 */               colNo++;
/* 10445 */               if (controls.getReporttype().equals("pdf"))
/*       */               {
/* 10447 */                 Map params = new Hashtable();
/* 10448 */                 params.put("type", "ATAGLANCE");
/* 10449 */                 params.put("data", moList);
/* 10450 */                 rg.setParams(params);
/* 10451 */                 ChartInfo cinfo = new ChartInfo();
/* 10452 */                 cinfo.setDataSet(rg);
/* 10453 */                 cinfo.setLegend("false");
/* 10454 */                 cinfo.setWidth("500");
/* 10455 */                 cinfo.setHeight("300");
/* 10456 */                 cinfo.setXaxisLabel("");
/* 10457 */                 cinfo.setYaxisLabel(FormatUtil.getString((String)attribProp.get("UNIT")));
/* 10458 */                 cinfo.setChartTitle(FormatUtil.getString(attributeDisName));
/* 10459 */                 cinfo.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 10460 */                 cinfo.setMaxBarWidth(0.05D);
/* 10461 */                 cinfo.setBarcolor(new Paint[] { colorsArray[((colNo + 1) % 8)] });
/* 10462 */                 cinfo.setLabelRotation(false);
/* 10463 */                 cinfo.setTwoDimensionBar(true);
/* 10464 */                 timeseriesImage = cinfo.get3DbarChartAsJPG();
/* 10465 */                 images.add(timeseriesImage);
/*       */               }
/*       */             }
/*       */             else
/*       */             {
/* 10470 */               attribList.add(attributeName + " # " + attributeDisName);
/* 10471 */               attribUnitList.add((String)attribProp.get("UNIT"));
/* 10472 */               retList.add(moList);
/* 10473 */               colNo++;
/* 10474 */               if (controls.getReporttype().equals("pdf"))
/*       */               {
/* 10476 */                 System.out.println("AMReportActions.generateGlanceReport() moList ===>" + moList);
/* 10477 */                 Hashtable hash = (Hashtable)moList.get(0);
/* 10478 */                 List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/* 10479 */                 if (allSecondLevelAttribute.contains(hash.get("ATTRIBUTEID")))
/*       */                 {
/* 10481 */                   ArrayList table_data = new ArrayList();
/* 10482 */                   ArrayList val1 = new ArrayList();
/* 10483 */                   val1.add("STEP NAME");
/* 10484 */                   val1.add("AGENT");
/* 10485 */                   val1.add("MINIMUM (ms)");
/* 10486 */                   val1.add("MAXIMUM (ms)");
/* 10487 */                   val1.add("AVERAGE (ms)");
/* 10488 */                   table_data.add(val1);
/* 10489 */                   for (int i = 0; i < moList.size(); i++)
/*       */                   {
/* 10491 */                     Hashtable ht = (Hashtable)moList.get(i);
/* 10492 */                     ArrayList val = new ArrayList();
/* 10493 */                     val.add(ht.get("DISPLAYNAME"));
/* 10494 */                     val.add(ht.get("AGENT"));
/* 10495 */                     val.add(ht.get("MINVALUE").toString());
/* 10496 */                     val.add(ht.get("MAXVALUE").toString());
/* 10497 */                     val.add(ht.get("VALUE").toString());
/* 10498 */                     table_data.add(val);
/*       */                   }
/* 10500 */                   sla.put(attributeDisName, table_data);
/*       */                 }
/*       */                 else
/*       */                 {
/* 10504 */                   Map params = new Hashtable();
/* 10505 */                   params.put("type", "ATAGLANCETIMESERIES");
/* 10506 */                   params.put("data", moList);
/* 10507 */                   rg.setParams(params);
/* 10508 */                   ChartInfo cinfo = new ChartInfo();
/* 10509 */                   cinfo.setDataSet(rg);
/* 10510 */                   cinfo.setChartTitle(ConfMonitorConfiguration.getInstance().getAttributeName(hash.get("RESOURCETYPE").toString(), hash.get("ATTRIBUTEID").toString()));
/* 10511 */                   cinfo.setWidth("300");
/* 10512 */                   cinfo.setHeight("300");
/* 10513 */                   timeseriesImage = cinfo.getTimeChartAsJPG();
/* 10514 */                   images.add(timeseriesImage);
/*       */                 }
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*       */         catch (Exception ee)
/*       */         {
/* 10522 */           ee.printStackTrace();
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 10528 */     request.setAttribute("DATALIST", retList);
/* 10529 */     request.setAttribute("attributename", attribList);
/* 10530 */     request.setAttribute("ATTRIBUTEUNITLIST", attribUnitList);
/*       */     
/* 10532 */     request.setAttribute("actionMethod", "generateGlanceReport");
/* 10533 */     if (controls.getReporttype().equals("pdf")) {
/* 10534 */       request.setAttribute("reportType", "pdf");
/* 10535 */       request.setAttribute("graphs", images);
/* 10536 */       if (sla.size() > 0)
/*       */       {
/* 10538 */         request.setAttribute("secondLvlAttr", sla);
/*       */       }
/*       */       else
/*       */       {
/* 10542 */         request.setAttribute("secondLvlAttr", new Hashtable());
/*       */       }
/* 10544 */       request.setAttribute("report-type-template", "report.glancereport");
/* 10545 */       return mapping.findForward("report.glancereport.pdf");
/*       */     }
/* 10547 */     if (controls.getReporttype().equals("csv"))
/*       */     {
/* 10549 */       BufferedInputStream bis = null;
/* 10550 */       BufferedOutputStream bos = null;
/* 10551 */       OutputStream out = null;
/* 10552 */       StringBuilder builder = new StringBuilder();
/* 10553 */       String reportName = "GlanceReport_" + new java.sql.Date(System.currentTimeMillis()) + ".csv";
/* 10554 */       response.setContentType("text/csv;charset=" + Constants.getCharSet());
/* 10555 */       response.setHeader("Content-disposition", "attachment; filename=" + reportName);
/*       */       try
/*       */       {
/* 10558 */         Properties idNameMapping = new Properties();
/* 10559 */         ArrayList<String> resList = new ArrayList();
/* 10560 */         HashMap<String, String> resProp = new HashMap();
/* 10561 */         builder.append("At a Glance Report - ").append((String)request.getAttribute("headingCategory"));
/* 10562 */         builder.append(" - ").append((String)request.getAttribute("headingPeriod") + "\n");
/* 10563 */         builder.append("Host Name");
/* 10564 */         int size = retList.size();
/* 10565 */         for (int i = 0; i < size; i++)
/*       */         {
/* 10567 */           ArrayList list = (ArrayList)retList.get(i);
/* 10568 */           int listSize = list.size();
/* 10569 */           String attributeStr = (String)attribList.get(i);
/* 10570 */           String[] attributeArr = attributeStr.split(" # ");
/* 10571 */           if (attributeArr[0].equals("Availability"))
/*       */           {
/* 10573 */             attribList.add("Availability-Uptime # Availability-Uptime");
/* 10574 */             attribUnitList.add(String.valueOf("%"));
/* 10575 */             attribList.add("Availability-Downtime # Availability-Downtime");
/* 10576 */             attribUnitList.add(String.valueOf("%"));
/* 10577 */             attribList.add("Availability-UnManaged # Availability-UnManaged");
/* 10578 */             attribUnitList.add(String.valueOf("%"));
/* 10579 */             attribList.add("Availability-Maintenance # Availability-Maintenance");
/* 10580 */             attribUnitList.add(String.valueOf("%"));
/*       */           }
/* 10582 */           for (int j = 0; j < listSize; j++)
/*       */           {
/* 10584 */             Hashtable<String, String> map = (Hashtable)list.get(j);
/* 10585 */             String resourceID = (String)map.get("RESOURCEID");
/* 10586 */             String displayName = (String)map.get("DISPLAYNAME");
/* 10587 */             idNameMapping.setProperty(resourceID, displayName);
/* 10588 */             if (!resList.contains(resourceID))
/*       */             {
/* 10590 */               resList.add(resourceID);
/*       */             }
/* 10592 */             if (attributeArr[0].equals("Availability"))
/*       */             {
/* 10594 */               String key = resourceID + " # " + "Availability-UnManaged";
/* 10595 */               resProp.put(key, String.valueOf(map.get("VALUE_UNMAN")));
/* 10596 */               key = resourceID + " # " + "Availability-Downtime";
/* 10597 */               resProp.put(key, String.valueOf(map.get("VALUE_DOWN")));
/* 10598 */               key = resourceID + " # " + "Availability-Maintenance";
/* 10599 */               resProp.put(key, String.valueOf(map.get("VALUE_SCHED")));
/* 10600 */               key = resourceID + " # " + "Availability-Uptime";
/* 10601 */               resProp.put(key, String.valueOf(map.get("VALUE")));
/*       */             }
/*       */             else
/*       */             {
/* 10605 */               String key = resourceID + " # " + attributeArr[0];
/* 10606 */               resProp.put(key, String.valueOf(map.get("VALUE")));
/*       */             }
/*       */           }
/*       */         }
/* 10610 */         if (resList.size() > 0)
/*       */         {
/* 10612 */           builder.append(",");
/*       */         }
/* 10614 */         for (int i = 0; i < attribList.size(); i++)
/*       */         {
/* 10616 */           String attr = (String)attribList.get(i);
/* 10617 */           String units = (String)attribUnitList.get(i);
/* 10618 */           if (!attr.equals("Availability # Availability"))
/*       */           {
/*       */ 
/*       */ 
/* 10622 */             String[] attributeArr = attr.split(" # ");
/* 10623 */             String attributeName = attributeArr[1];
/* 10624 */             builder.append(attributeName);
/* 10625 */             if ((units != null) && (!units.equals(" ")))
/*       */             {
/* 10627 */               builder.append("(" + FormatUtil.getString(units) + ")");
/*       */             }
/* 10629 */             builder.append(",");
/*       */           } }
/* 10631 */         int endIndex = builder.toString().length();
/* 10632 */         builder.replace(endIndex - 1, endIndex, "\n");
/* 10633 */         for (String resourceID : resList)
/*       */         {
/* 10635 */           String resourceName = idNameMapping.getProperty(resourceID);
/* 10636 */           builder.append(resourceName).append(",");
/* 10637 */           for (int i = 0; i < attribList.size(); i++)
/*       */           {
/* 10639 */             String attr = (String)attribList.get(i);
/* 10640 */             if (!attr.equals("Availability # Availability"))
/*       */             {
/*       */ 
/*       */ 
/* 10644 */               String[] attributeArr = attr.split(" # ");
/* 10645 */               String attributeName = attributeArr[0];
/* 10646 */               String key = resourceID + " # " + attributeName;
/* 10647 */               builder.append(resProp.get(key) != null ? String.valueOf(resProp.get(key)) : "").append(",");
/*       */             } }
/* 10649 */           endIndex = builder.toString().length();
/* 10650 */           builder.replace(endIndex - 1, endIndex, "\n");
/*       */         }
/* 10652 */         String outputData = builder.toString();
/*       */         try
/*       */         {
/* 10655 */           out = response.getOutputStream();
/*       */         }
/*       */         catch (IOException ioe)
/*       */         {
/* 10659 */           ioe.printStackTrace();
/*       */         }
/* 10661 */         bis = new BufferedInputStream(new ByteArrayInputStream(outputData.getBytes()));
/* 10662 */         bos = new BufferedOutputStream(out);
/* 10663 */         byte[] buff = new byte['ࠀ'];
/*       */         int bytesRead;
/* 10665 */         while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
/*       */         {
/* 10667 */           bos.write(buff, 0, bytesRead);
/* 10668 */           bos.flush();
/*       */         }
/* 10670 */         out.flush();
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/* 10674 */         e.printStackTrace();
/*       */       }
/*       */       finally
/*       */       {
/* 10678 */         if (out != null)
/*       */         {
/* 10680 */           out.close();
/*       */         }
/* 10682 */         if (bis != null)
/*       */         {
/* 10684 */           bis.close();
/*       */         }
/* 10686 */         if (bos != null)
/*       */         {
/* 10688 */           bos.close();
/*       */         }
/*       */       }
/* 10691 */       return null;
/*       */     }
/* 10693 */     if (isValidEumRep)
/*       */     {
/* 10695 */       return mapping.findForward("report.eum.glancereport");
/*       */     }
/* 10697 */     return mapping.findForward("report.glancereport");
/*       */   }
/*       */   
/*       */ 
/*       */   public ActionForward generateIndividualGlanceReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/* 10704 */     ActionMessages messages = new ActionMessages();
/* 10705 */     ResultSet set = null;
/* 10706 */     ResultSet rs = null;
/* 10707 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 10708 */     ReportForm controls = (ReportForm)form;
/* 10709 */     String dname = null;
/* 10710 */     String urlsequence = "false";
/* 10711 */     int sizeofseq = -1;
/* 10712 */     int sizeofdisk = -1;
/* 10713 */     ArrayList data = new ArrayList();
/* 10714 */     String type = null;
/* 10715 */     long startTime = 0L;
/* 10716 */     long endTime = 0L;
/*       */     
/* 10718 */     String hostresid = null;
/* 10719 */     String queryresid = null;
/*       */     try {
/* 10721 */       String resourceType = controls.getResourceType();
/*       */       
/* 10723 */       String resid = controls.getResid();
/* 10724 */       if (DBUtil.isEUMParent(resid))
/*       */       {
/* 10726 */         controls.setEumMonId(resid);
/* 10727 */         controls.setEumReport("true");
/* 10728 */         return generateGlanceReport(mapping, controls, request, response);
/*       */       }
/*       */       
/* 10731 */       request.setAttribute("resourceid", resid);
/*       */       
/* 10733 */       dname = ReportUtilities.getLabelName(resid);
/*       */       
/* 10735 */       controls.setDisplayname(EnterpriseUtil.decodeString(dname));
/*       */       
/* 10737 */       getHolisticApps(mapping, form, request, response);
/* 10738 */       getMonitors(mapping, form, request, response);
/* 10739 */       getCustomApplications(request, "3");
/* 10740 */       request.setAttribute("HelpKey", getMonitorHelpKey(resourceType));
/*       */       
/*       */ 
/* 10743 */       String nodata = "report.nodata.time";
/*       */       
/* 10745 */       String attributeids = controls.getAttribute();
/*       */       
/* 10747 */       String period = controls.getPeriod();
/* 10748 */       request.setAttribute("period", period);
/* 10749 */       String attribute = request.getParameter("attribute");
/*       */       
/* 10751 */       long[] timeStamps = null;
/* 10752 */       long customstartTime = 0L;
/* 10753 */       long customendTime = 0L;
/* 10754 */       long[] time = ReportUtilities.getTimeStamp(period);
/* 10755 */       long mintimeindb = ReportUtilities.getMinTimeInDB(resid);
/*       */       
/* 10757 */       long totalDuration = 0L;
/* 10758 */       if (period.equals("4")) {
/* 10759 */         String startdate = controls.getStartDate();
/* 10760 */         String enddate = controls.getEndDate();
/* 10761 */         customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/* 10762 */         customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/* 10763 */         if (mintimeindb > customstartTime) {
/* 10764 */           startTime = mintimeindb;
/*       */         }
/* 10766 */         else if (mintimeindb != 0L) {
/* 10767 */           startTime = customstartTime;
/*       */         }
/* 10769 */         long currenttime = System.currentTimeMillis();
/* 10770 */         if (customendTime > currenttime) {
/* 10771 */           endTime = currenttime;
/*       */         }
/*       */         else {
/* 10774 */           endTime = customendTime;
/*       */         }
/* 10776 */         totalDuration = endTime - startTime;
/*       */ 
/*       */ 
/*       */ 
/*       */       }
/* 10781 */       else if (mintimeindb > time[0]) {
/* 10782 */         startTime = mintimeindb;
/*       */         
/* 10784 */         endTime = time[1];
/*       */         
/* 10786 */         totalDuration = endTime - startTime;
/*       */       }
/* 10788 */       else if (mintimeindb != 0L) {
/* 10789 */         startTime = time[0];
/*       */         
/* 10791 */         endTime = time[1];
/*       */         
/* 10793 */         totalDuration = endTime - startTime;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 10800 */       String typequery = "SELECT TYPE FROM AM_ManagedObject where RESOURCEID= '" + resid + "'";
/* 10801 */       set = AMConnectionPool.executeQueryStmt(typequery);
/* 10802 */       AMLog.debug("Reports ;" + typequery);
/*       */       
/* 10804 */       if (set.next()) {
/* 10805 */         type = set.getString("TYPE");
/*       */       }
/*       */       
/*       */ 
/*       */ 
/* 10810 */       data.add(resid);
/* 10811 */       data.add(type);
/* 10812 */       data.add(new Long(startTime));
/* 10813 */       data.add(new Long(endTime));
/*       */       
/* 10815 */       if (type != null)
/*       */       {
/* 10817 */         ArrayList graphData = new ArrayList();
/*       */         
/* 10819 */         if ((type.equals("QueryMonitor")) || ((type.equals("Script Monitor")) && (resid != null) && (!resid.equals("")))) {
/* 10820 */           graphData = ReportUtilities.getGraphDataForConfigAttListInOrder(type + "#" + resid);
/*       */         }
/* 10822 */         else if ((type.equals("SNMP")) || (type.equals("SAP-CCMS")))
/*       */         {
/* 10824 */           graphData = ReportUtilities.getGraphDataForConfigAttListInOrder(type + "#" + resid);
/*       */         }
/*       */         else {
/* 10827 */           graphData = ReportUtilities.getGraphDataForConfigAttListInOrder(type);
/*       */         }
/* 10829 */         System.out.println("the graph data for the configured att list====>" + graphData);
/* 10830 */         for (int k = 0; k < graphData.size(); k++)
/*       */         {
/* 10832 */           data.add(graphData.get(k));
/*       */         }
/*       */       }
/*       */       
/* 10836 */       request.setAttribute("strTime", new java.util.Date(((Long)data.get(2)).longValue()));
/* 10837 */       request.setAttribute("endTime", new java.util.Date(((Long)data.get(3)).longValue()));
/* 10838 */       request.setAttribute("data", data);
/*       */       
/*       */ 
/* 10841 */       if (startTime > endTime) {
/* 10842 */         request.setAttribute("heading", "0");
/* 10843 */         request.setAttribute("strTime", "0");
/* 10844 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.nodata.time"));
/* 10845 */         saveMessages(request, messages);
/* 10846 */         return mapping.findForward("report.message");
/*       */       }
/*       */       
/*       */ 
/* 10850 */       if (Constants.sqlManager) {
/*       */         try {
/* 10852 */           HashMap sqlChildids = DBUtil.getSqlchildids(resid);
/* 10853 */           if (sqlChildids.size() > 0) {
/* 10854 */             hostresid = (String)sqlChildids.get("hostresid");
/* 10855 */             queryresid = (String)sqlChildids.get("queryresid");
/*       */           }
/*       */         }
/*       */         catch (Exception eq) {
/* 10859 */           eq.printStackTrace();
/*       */         }
/*       */       }
/* 10862 */       request.setAttribute("hostresid", hostresid);
/* 10863 */       request.setAttribute("sqlresid", hostresid);
/*       */ 
/*       */     }
/*       */     catch (Exception exp)
/*       */     {
/* 10868 */       exp.printStackTrace();
/* 10869 */       AMLog.fatal("Reports :  Exception ", exp);
/* 10870 */       request.setAttribute("heading", "0");
/* 10871 */       request.setAttribute("strTime", "0");
/* 10872 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/* 10873 */       saveMessages(request, messages);
/* 10874 */       return mapping.findForward("report.message");
/*       */     }
/*       */     
/* 10877 */     closeResultSet(set);
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 10885 */     StringBuffer secLvlAttrib = new StringBuffer();
/* 10886 */     ArrayList images = new ArrayList();
/* 10887 */     ArrayList attrinames = new ArrayList();
/* 10888 */     HashMap dataList = new HashMap();
/* 10889 */     String timeseriesImage = null;
/*       */     
/*       */ 
/*       */     try
/*       */     {
/* 10894 */       System.out.println("the data we are sending are===>" + data);
/* 10895 */       ArrayList attName = new ArrayList();
/* 10896 */       String resid = (String)data.get(0);
/* 10897 */       String restype = (String)data.get(1);
/* 10898 */       Hashtable colors = new Hashtable();
/* 10899 */       colors.put("1", "#00FF00");
/* 10900 */       colors.put("0", "#FF0000");
/* 10901 */       colors.put("2", "#0066CC");
/* 10902 */       colors.put("3", "#FF00FF");
/* 10903 */       long starttime = ((Long)data.get(2)).longValue();
/* 10904 */       long endtime = ((Long)data.get(3)).longValue();
/* 10905 */       String attributename = null;
/* 10906 */       String attributeid = null;
/* 10907 */       String attrLevel = null;
/* 10908 */       request.setAttribute("startTime", Long.valueOf(starttime));
/* 10909 */       request.setAttribute("endtime", Long.valueOf(endtime));
/* 10910 */       ReportGraphs rg = new ReportGraphs();
/* 10911 */       Map params = new Hashtable();
/* 10912 */       params.put("type", "AVAILABILITY");
/* 10913 */       params.put("id", resid);
/* 10914 */       params.put("attid", "-1");
/* 10915 */       params.put("period", controls.getPeriod());
/* 10916 */       params.put("startTime", controls.getStartDate());
/* 10917 */       params.put("endTime", controls.getEndDate());
/* 10918 */       params.put("attributeName", "Availability");
/* 10919 */       rg.setParams(params);
/*       */       
/* 10921 */       ChartInfo cinfo = new ChartInfo();
/* 10922 */       cinfo.setDataSet(rg);
/* 10923 */       cinfo.setHeight("190");
/* 10924 */       cinfo.setWidth("250");
/* 10925 */       cinfo.setColors(colors);
/* 10926 */       cinfo.setDecimal(true);
/* 10927 */       cinfo.setUnits("%");
/* 10928 */       if (controls.getReporttype().equals("csv"))
/*       */       {
/* 10930 */         dataList.put("Availability", rg);
/*       */       }
/* 10932 */       timeseriesImage = cinfo.getPieChartAsJPG();
/* 10933 */       Color[] colorsArray = { new Color(153, 0, 153), new Color(51, 204, 0), new Color(255, 0, 0), new Color(245, 16, 236), new Color(0, 89, 189), new Color(140, 0, 0), new Color(71, 190, 250), new Color(93, 18, 225), new Color(52, 255, 50) };
/*       */       
/*       */ 
/* 10936 */       attrinames.add(FormatUtil.getString("am.webclient.common.availability.text"));
/* 10937 */       images.add(timeseriesImage);
/*       */       
/* 10939 */       for (int i = 4; i < data.size(); i++)
/*       */       {
/* 10941 */         ArrayList a1 = (ArrayList)data.get(i);
/* 10942 */         attributename = (String)a1.get(0);
/* 10943 */         attributeid = (String)a1.get(1);
/* 10944 */         List allSecondLevelAttribute = (ArrayList)((ArrayList)ReportUtil.getAllSecondLevelAttribute()).clone();
/*       */         
/* 10946 */         ArrayList childAttributes = new ArrayList();
/*       */         
/* 10948 */         if ("VMWare ESX/ESXi".equals(type))
/*       */         {
/* 10950 */           childAttributes = (ArrayList)ReportUtil.getAttributeIDsforResType("VirtualMachine");
/*       */         }
/*       */         
/* 10953 */         if ("Amazon".equals(type)) {
/* 10954 */           childAttributes = (ArrayList)ReportUtil.getAttributeIDsforResType("EC2Instance");
/* 10955 */           childAttributes.addAll((ArrayList)ReportUtil.getAttributeIDsforResType("RDSInstance"));
/*       */         }
/* 10957 */         if ("XenServerHost".equals(type))
/*       */         {
/* 10959 */           childAttributes = (ArrayList)ReportUtil.getAttributeIDsforResType("XenServerVM");
/*       */         }
/* 10961 */         if ("Hyper-V-Server".equals(type)) {
/* 10962 */           childAttributes = (ArrayList)ReportUtil.getAttributeIDsforResType("HyperVVirtualMachine");
/*       */         }
/*       */         
/* 10965 */         if (!allSecondLevelAttribute.containsAll(childAttributes)) {
/* 10966 */           allSecondLevelAttribute.addAll(childAttributes);
/*       */         }
/* 10968 */         request.setAttribute("childAttributes", childAttributes);
/*       */         
/*       */ 
/* 10971 */         if (allSecondLevelAttribute.contains(attributeid))
/*       */         {
/* 10973 */           if (secLvlAttrib.length() > 0)
/*       */           {
/* 10975 */             secLvlAttrib.append("," + attributeid);
/*       */           }
/*       */           else
/*       */           {
/* 10979 */             secLvlAttrib.append(attributeid);
/*       */           }
/*       */         }
/*       */         else {
/* 10983 */           if ((controls.getReporttype().equals("pdf")) || (controls.getReporttype().equals("csv")))
/*       */           {
/* 10985 */             System.out.println("the attribute id===>" + attributeid);
/* 10986 */             SummaryBean sumgraph = new SummaryBean();
/* 10987 */             sumgraph.setResid(resid);
/* 10988 */             if (Constants.sqlManager)
/*       */             {
/* 10990 */               if (DBUtil.windowsAttributeList.contains(attributeid)) {
/* 10991 */                 sumgraph.setResid(hostresid);
/* 10992 */               } else if (DBUtil.querymonitorAttributeList.contains(attributeid)) {
/* 10993 */                 sumgraph.setResid(queryresid);
/*       */               }
/*       */             }
/* 10996 */             sumgraph.setAttributeid(attributeid);
/* 10997 */             sumgraph.setStarttime(String.valueOf(starttime));
/* 10998 */             sumgraph.setEndtime(String.valueOf(endtime));
/* 10999 */             sumgraph.setEntity(attributename);
/* 11000 */             sumgraph.setXYAreaChart(true);
/*       */             
/*       */ 
/*       */ 
/* 11004 */             if ((attributeid != null) && ((attributeid.equalsIgnoreCase("410")) || (allSecondLevelAttribute.contains(attributeid)))) {
/* 11005 */               String rids = getResIds(resid, attributeid);
/*       */               
/* 11007 */               sumgraph.setResid(rids);
/* 11008 */               sumgraph.setArchivedforUrl(true);
/* 11009 */               sumgraph.setCompareUrls(true);
/*       */             }
/* 11011 */             ChartInfo cinfo1 = new ChartInfo();
/* 11012 */             cinfo1.setDataSet(sumgraph);
/* 11013 */             cinfo1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/* 11014 */             cinfo1.setYaxisLabel(attributename);
/* 11015 */             cinfo1.setShape(true);
/* 11016 */             cinfo1.setCustomDateAxis(true);
/* 11017 */             cinfo1.setCustomAngle(270.0D);
/* 11018 */             cinfo1.setLegend("false");
/* 11019 */             cinfo1.setLabelRotation(false);
/* 11020 */             cinfo1.setSeriesPaintColor(colorsArray[((i + 1) % 8)]);
/*       */             
/*       */ 
/* 11023 */             cinfo1.setHeight("210");
/*       */             
/*       */ 
/* 11026 */             cinfo1.setWidth("300");
/* 11027 */             if (controls.getReporttype().equals("csv"))
/*       */             {
/* 11029 */               dataList.put(attributename, sumgraph);
/*       */             }
/* 11031 */             else if (controls.getReporttype().equals("pdf"))
/*       */             {
/* 11033 */               timeseriesImage = cinfo1.getXYAreaChartAsJPG();
/* 11034 */               images.add(timeseriesImage);
/*       */             }
/*       */           }
/* 11037 */           attrinames.add(attributename);
/* 11038 */           attName.add(attributename);
/*       */         }
/*       */       }
/* 11041 */       request.setAttribute("graphs", images);
/* 11042 */       request.setAttribute("data", data);
/* 11043 */       request.setAttribute("attributename", attName);
/*       */     }
/*       */     catch (Exception ed) {
/* 11046 */       ed.printStackTrace();
/*       */     }
/*       */     
/* 11049 */     StringBuffer secLvlType = new StringBuffer();
/* 11050 */     ArrayList secLvlAttribList = new ArrayList();
/* 11051 */     Hashtable secLvlAttribHash = new Hashtable();
/* 11052 */     Hashtable childReshash = new Hashtable();
/*       */     
/*       */ 
/* 11055 */     String secLvlAttQuery = "select att.ATTRIBUTEID,att.DISPLAYNAME,att.UNITS,att.RESOURCETYPE, ext.ARCHIVEDDATA_TABLENAME ,ext.EXPRESSION from AM_ATTRIBUTES_EXT as ext,AM_ATTRIBUTES  as att where ext.ATTRIBUTEID in (" + secLvlAttrib + ") and ext.ATTRIBUTEID=att.ATTRIBUTEID order by RESOURCETYPE";
/* 11056 */     System.out.println("############### ======================>secLvlAttQuery = " + secLvlAttQuery);
/*       */     try {
/* 11058 */       Hashtable secLvlRetList = new Hashtable();
/* 11059 */       if (secLvlAttrib.length() > 0)
/*       */       {
/* 11061 */         String prevType = "";
/* 11062 */         ArrayList secLvlAttList = ReportUtilities.getTabularData(secLvlAttQuery, false);
/* 11063 */         for (int i = 0; i < secLvlAttList.size(); i++) {
/*       */           try {
/* 11065 */             ArrayList tempList = (ArrayList)secLvlAttList.get(i);
/* 11066 */             if (secLvlType.length() > 0)
/*       */             {
/* 11068 */               secLvlType.append(",'" + String.valueOf(tempList.get(3)) + "'");
/*       */             }
/*       */             else
/*       */             {
/* 11072 */               secLvlType.append("'" + String.valueOf(tempList.get(3)) + "'");
/*       */             }
/* 11074 */             Hashtable attHash = new Hashtable();
/* 11075 */             attHash.put("ATTRIBUTEID", String.valueOf(tempList.get(0)));
/* 11076 */             attHash.put("DISPLAYNAME", EnterpriseUtil.decodeString(String.valueOf(tempList.get(1))));
/* 11077 */             String unit = String.valueOf(tempList.get(2));
/* 11078 */             if ((unit != null) && (!"null".equalsIgnoreCase(unit)))
/*       */             {
/* 11080 */               attHash.put("UNITS", unit);
/*       */             }
/*       */             else
/*       */             {
/* 11084 */               attHash.put("UNITS", "");
/*       */             }
/* 11086 */             attHash.put("ARCHIVEDDATA_TABLENAME", String.valueOf(tempList.get(4)));
/* 11087 */             attHash.put("EXPRESSION", String.valueOf(tempList.get(5)));
/* 11088 */             if (i == 0)
/*       */             {
/* 11090 */               secLvlAttribList.add(attHash);
/* 11091 */               prevType = String.valueOf(tempList.get(3));
/*       */             }
/* 11093 */             else if ((prevType != null) && (!prevType.equals(String.valueOf(tempList.get(3)))))
/*       */             {
/* 11095 */               secLvlAttribHash.put(prevType, secLvlAttribList);
/* 11096 */               secLvlAttribList = new ArrayList();
/* 11097 */               secLvlAttribList.add(attHash);
/* 11098 */               prevType = String.valueOf(tempList.get(3));
/*       */ 
/*       */             }
/*       */             else
/*       */             {
/* 11103 */               secLvlAttribList.add(attHash);
/*       */             }
/* 11105 */             if (i == secLvlAttList.size() - 1)
/*       */             {
/* 11107 */               secLvlAttribHash.put(String.valueOf(tempList.get(3)), secLvlAttribList);
/*       */             }
/*       */           }
/*       */           catch (Exception eee)
/*       */           {
/* 11112 */             eee.printStackTrace();
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/* 11119 */         String childIDQuery = "select mo.RESOURCEID,mo.DISPLAYNAME,mo.TYPE from AM_ManagedObject as mo,AM_PARENTCHILDMAPPER as pcm where pcm.PARENTID=" + controls.getResid() + " and mo.RESOURCEID=pcm.CHILDID and mo.TYPE in (" + secLvlType.toString() + ") order by pcm.RELATIONSHIPID";
/* 11120 */         System.out.println("childIDQuery ||||||||||||||| ==========================>>>>" + childIDQuery);
/*       */         
/*       */ 
/* 11123 */         ArrayList curResids = getCurrentQueryMonitorResourceIDs(controls.getResid(), type);
/*       */         
/*       */         try
/*       */         {
/* 11127 */           ArrayList secLvlSet = ReportUtilities.getTabularData(childIDQuery, false);
/* 11128 */           String prevResType = "";
/* 11129 */           LinkedHashMap temphash = new LinkedHashMap();
/*       */           
/* 11131 */           for (int i = 0; i < secLvlSet.size(); i++)
/*       */           {
/* 11133 */             ArrayList tempList = (ArrayList)secLvlSet.get(i);
/* 11134 */             prevResType = String.valueOf(tempList.get(2));
/* 11135 */             if ((!"QueryMonitor".equalsIgnoreCase(type)) || ("QueryMonitor_Execution Time_ROW".equalsIgnoreCase(prevResType)) || (curResids.contains(String.valueOf(tempList.get(0)).trim())))
/*       */             {
/*       */ 
/* 11138 */               temphash = new LinkedHashMap();
/* 11139 */               if (childReshash.containsKey(prevResType)) {
/* 11140 */                 temphash = (LinkedHashMap)childReshash.get(prevResType);
/*       */               }
/* 11142 */               temphash.put(String.valueOf(tempList.get(0)), FormatUtil.findReplace(String.valueOf(tempList.get(1)), "DiskUtilization", FormatUtil.getString("DiskUtilization")));
/* 11143 */               childReshash.put(prevResType, temphash);
/*       */             }
/*       */           }
/*       */         }
/*       */         catch (Exception exe) {
/* 11148 */           exe.printStackTrace();
/*       */         }
/*       */         
/* 11151 */         System.out.println("childReshash ||||||||||||||| ==========================>>>>" + childReshash);
/*       */         
/* 11153 */         Enumeration tableEnum = secLvlAttribHash.keys();
/*       */         
/* 11155 */         while (tableEnum.hasMoreElements())
/*       */         {
/* 11157 */           String tableName = (String)tableEnum.nextElement();
/*       */           
/* 11159 */           ArrayList attrList = (ArrayList)secLvlAttribHash.get(tableName);
/* 11160 */           if (childReshash.get(tableName) != null)
/*       */           {
/*       */ 
/*       */ 
/* 11164 */             LinkedHashMap typetable = (LinkedHashMap)childReshash.get(tableName);
/* 11165 */             Vector typeResVect = new Vector();
/* 11166 */             Hashtable dataHash = new Hashtable();
/* 11167 */             ArrayList aIDList = new ArrayList();
/* 11168 */             ArrayList dispList = new ArrayList();
/* 11169 */             ArrayList unitList = new ArrayList();
/*       */             
/*       */ 
/*       */ 
/* 11173 */             Set entrySet = typetable.keySet();
/* 11174 */             Iterator it = entrySet.iterator();
/* 11175 */             while (it.hasNext()) {
/* 11176 */               typeResVect.add((String)it.next());
/*       */             }
/*       */             
/* 11179 */             for (int i = 0; i < attrList.size(); i++)
/*       */             {
/* 11181 */               Hashtable attHash = (Hashtable)attrList.get(i);
/* 11182 */               String attID = (String)attHash.get("ATTRIBUTEID");
/* 11183 */               aIDList.add(attID);
/* 11184 */               dispList.add((String)attHash.get("DISPLAYNAME"));
/* 11185 */               unitList.add((String)attHash.get("UNITS"));
/* 11186 */               String archivedTableName = (String)attHash.get("ARCHIVEDDATA_TABLENAME");
/* 11187 */               String expression = (String)attHash.get("EXPRESSION");
/* 11188 */               if (expression == null)
/*       */               {
/* 11190 */                 expression = "";
/*       */               }
/*       */               try {
/* 11193 */                 String dataQuery = "select RESID,(sum(TOTAL)/sum(TOTALCOUNT)" + expression + ") as Avg from " + archivedTableName + " where " + ReportUtilities.getCondition("RESID", typeResVect) + " and ATTRIBUTEID = " + attID + " and ARCHIVEDTIME >=" + startTime + " and ARCHIVEDTIME <=" + endTime + " group by RESID ";
/* 11194 */                 System.out.println("dataQuery ||||||||||||||| ==========================>>>>" + dataQuery);
/* 11195 */                 set = AMConnectionPool.executeQueryStmt(dataQuery);
/* 11196 */                 while (set.next())
/*       */                 {
/* 11198 */                   long avg = Math.round(set.getDouble("Avg"));
/* 11199 */                   dataHash.put(set.getString("RESID") + "#" + attID, Long.toString(avg));
/*       */                 }
/*       */                 
/*       */ 
/*       */               }
/*       */               catch (Exception exc)
/*       */               {
/* 11206 */                 exc.printStackTrace();
/*       */               }
/*       */             }
/*       */             
/* 11210 */             ArrayList rowList = new ArrayList();
/* 11211 */             ArrayList tempList = new ArrayList();
/* 11212 */             ArrayList availList = new ArrayList();
/* 11213 */             tempList.add(FormatUtil.getString(tableName));
/* 11214 */             if ((tableName != null) && ((tableName.equals("Process")) || (tableName.equals("DataBase"))))
/*       */             {
/* 11216 */               availList = ReportUtilities.getAvailabilityDataForMonitors(typeResVect, startTime, endTime, "oni");
/* 11217 */               tempList.add(FormatUtil.getString("Availability") + " (%)");
/* 11218 */               System.out.println("availList ||||||||||||||| ==========================>>>>" + availList);
/*       */             }
/* 11220 */             for (int n = 0; n < aIDList.size(); n++)
/*       */             {
/* 11222 */               if (((String)unitList.get(n) != null) && (!"null".equalsIgnoreCase((String)unitList.get(n))) && (((String)unitList.get(n)).length() > 0))
/*       */               {
/* 11224 */                 if (((String)unitList.get(n)).indexOf("(") == -1) {
/* 11225 */                   tempList.add(FormatUtil.getString((String)dispList.get(n)) + " (" + (String)unitList.get(n) + ")");
/*       */                 }
/*       */                 else {
/* 11228 */                   tempList.add(FormatUtil.getString((String)dispList.get(n)) + " " + (String)unitList.get(n));
/*       */                 }
/*       */                 
/*       */               }
/*       */               else {
/* 11233 */                 tempList.add(FormatUtil.getString((String)dispList.get(n)));
/*       */               }
/*       */             }
/* 11236 */             rowList.add(tempList);
/* 11237 */             for (int i = 0; i < typeResVect.size(); i++) {
/* 11238 */               ArrayList detailList = new ArrayList();
/* 11239 */               String resID = (String)typeResVect.elementAt(i);
/* 11240 */               detailList.add((String)typetable.get(resID));
/* 11241 */               if ((tableName != null) && ((tableName.equals("Process")) || (tableName.equals("DataBase"))))
/*       */               {
/* 11243 */                 for (int k = 0; k < availList.size(); k++)
/*       */                 {
/* 11245 */                   Properties tempProp = (Properties)availList.get(k);
/* 11246 */                   String tempRes = tempProp.getProperty("resourceid");
/* 11247 */                   if ((tempRes != null) && (tempRes.equals(resID)))
/*       */                   {
/* 11249 */                     detailList.add(tempProp.getProperty("available"));
/*       */                   }
/*       */                 }
/*       */               }
/*       */               
/*       */ 
/* 11255 */               for (int j = 0; j < aIDList.size(); j++)
/*       */               {
/* 11257 */                 String value = (String)dataHash.get(resID + "#" + (String)aIDList.get(j));
/* 11258 */                 if (value != null)
/*       */                 {
/* 11260 */                   detailList.add(value);
/*       */                 }
/*       */                 else
/*       */                 {
/* 11264 */                   detailList.add("-");
/*       */                 }
/*       */               }
/*       */               
/* 11268 */               rowList.add(detailList);
/*       */             }
/* 11270 */             secLvlRetList.put(tableName, rowList);
/*       */           }
/*       */         }
/*       */       }
/* 11274 */       System.out.println("secLvlRetList @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ==========================>>>>" + secLvlRetList);
/*       */       
/* 11276 */       request.setAttribute("SECONDLEVELATTRIBS", secLvlRetList);
/*       */ 
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/*       */ 
/* 11282 */       ee.printStackTrace();
/*       */     }
/*       */     finally {
/* 11285 */       closeResultSet(set);
/* 11286 */       closeResultSet(rs);
/*       */     }
/* 11288 */     request.setAttribute("actionMethod", "generateIndividualGlanceReport");
/*       */     
/* 11290 */     if (controls.getReporttype().equals("pdf"))
/*       */     {
/* 11292 */       request.setAttribute("reportType", "pdf");
/* 11293 */       request.setAttribute("images", images);
/* 11294 */       request.setAttribute("report-type-template", "report.glancereport");
/*       */       
/*       */ 
/*       */ 
/* 11298 */       return mapping.findForward("report.glancereport.pdf");
/*       */     }
/* 11300 */     if (controls.getReporttype().equals("csv"))
/*       */     {
/* 11302 */       BufferedInputStream bis = null;
/* 11303 */       BufferedOutputStream bos = null;
/* 11304 */       OutputStream out = null;
/* 11305 */       StringBuilder builder = new StringBuilder();
/* 11306 */       String reportName = "GlanceReport_" + new java.sql.Date(System.currentTimeMillis()) + ".csv";
/* 11307 */       response.setContentType("text/csv;charset=" + Constants.getCharSet());
/* 11308 */       response.setHeader("Content-disposition", "attachment; filename=" + reportName);
/*       */       try
/*       */       {
/* 11311 */         builder.append("At a Glance Report - ").append((String)request.getAttribute("headingCategory"));
/* 11312 */         builder.append(" - ").append((String)request.getAttribute("headingPeriod") + "\n\n");
/* 11313 */         builder.append("Attribute Name, CollectionTime, Value").append("\n");
/* 11314 */         Iterator itr = dataList.keySet().iterator();
/* 11315 */         while (itr.hasNext())
/*       */         {
/* 11317 */           TimeSeriesCollection tempdataset = null;
/* 11318 */           String attributeName = (String)itr.next();
/* 11319 */           DatasetProducer dsp1 = (DatasetProducer)dataList.get(attributeName);
/* 11320 */           Object tmp = dsp1.produceDataset(null);
/* 11321 */           DefaultPieDataset setData; if (attributeName.equals("Availability"))
/*       */           {
/* 11323 */             if ((tmp instanceof DefaultPieDataset))
/*       */             {
/* 11325 */               setData = (DefaultPieDataset)tmp;
/* 11326 */               List<String> keyList = setData.getKeys();
/* 11327 */               for (String key : keyList)
/*       */               {
/* 11329 */                 builder.append(FormatUtil.getString("am.webclient.common.availability.text") + "_");
/* 11330 */                 if (key.indexOf(FormatUtil.getString("am.reporttab.availablityreport.downtime.text")) != -1)
/*       */                 {
/* 11332 */                   builder.append(FormatUtil.getString("am.reporttab.availablityreport.downtime.text")).append(",");
/*       */                 }
/* 11334 */                 else if (key.indexOf(FormatUtil.getString("am.webclient.reports.uptime")) != -1)
/*       */                 {
/* 11336 */                   builder.append(FormatUtil.getString("am.webclient.reports.uptime")).append(",");
/*       */                 }
/* 11338 */                 else if (key.indexOf(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text")) != -1)
/*       */                 {
/* 11340 */                   builder.append(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text")).append(",");
/*       */                 }
/* 11342 */                 else if (key.indexOf(FormatUtil.getString("am.reporttab.availablityreport.scheduled.text")) != -1)
/*       */                 {
/* 11344 */                   builder.append(FormatUtil.getString("am.reporttab.availablityreport.scheduled.text")).append(",");
/*       */                 }
/* 11346 */                 float val = setData.getValue(key).floatValue();
/* 11347 */                 builder.append("-,").append(String.valueOf(val)).append("\n");
/*       */               }
/*       */             }
/*       */           }
/*       */           else
/*       */           {
/* 11353 */             if ((tmp instanceof SubSeriesDataset))
/*       */             {
/* 11355 */               SubSeriesDataset subseries = (SubSeriesDataset)tmp;
/* 11356 */               tempdataset = (TimeSeriesCollection)subseries.getParent();
/*       */             }
/* 11358 */             else if ((tmp instanceof TimeSeriesCollection))
/*       */             {
/* 11360 */               tempdataset = (TimeSeriesCollection)tmp;
/*       */             }
/* 11362 */             List list = tempdataset.getSeries();
/* 11363 */             builder.append(attributeName).append(",");
/* 11364 */             for (int i = 0; i < list.size(); i++)
/*       */             {
/* 11366 */               TimeSeries series = (TimeSeries)list.get(i);
/* 11367 */               List<TimeSeriesDataItem> dataItem = series.getItems();
/* 11368 */               int size = dataItem.size();
/* 11369 */               if (size == 0)
/*       */               {
/* 11371 */                 builder.append("-,-").append("\n");
/*       */               }
/* 11373 */               for (TimeSeriesDataItem item : dataItem)
/*       */               {
/* 11375 */                 RegularTimePeriod rgPeriod = item.getPeriod();
/* 11376 */                 long val = item.getValue().longValue();
/* 11377 */                 builder.append(rgPeriod.toString()).append(",").append(String.valueOf(val)).append("\n");
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/* 11382 */         Hashtable tableHash = (Hashtable)request.getAttribute("SECONDLEVELATTRIBS");
/* 11383 */         Enumeration enum1 = tableHash.keys();
/* 11384 */         builder.append("\n\n");
/* 11385 */         while (enum1.hasMoreElements())
/*       */         {
/* 11387 */           String tableName = (String)enum1.nextElement();
/* 11388 */           builder.append(tableName).append("\n\n");
/* 11389 */           ArrayList contentList = (ArrayList)tableHash.get(tableName);
/* 11390 */           for (int i = 0; i < contentList.size(); i++)
/*       */           {
/* 11392 */             ArrayList listData = (ArrayList)contentList.get(i);
/* 11393 */             int listSize = listData.size();
/* 11394 */             for (int j = 0; j < listSize; j++)
/*       */             {
/* 11396 */               if ((tableName != null) && ((tableName.equals("Process")) || (tableName.equals("DataBase"))) && (j == 1) && (i > 0))
/*       */               {
/* 11398 */                 int avail = Math.round(Float.valueOf(listData.get(j).toString()).floatValue());
/* 11399 */                 int notavail = 100 - avail;
/* 11400 */                 builder.append(FormatUtil.getString("am.webclient.historydata.uptime.text"));
/* 11401 */                 builder.append("-").append((String)listData.get(j));
/*       */               }
/*       */               else
/*       */               {
/* 11405 */                 builder.append((String)listData.get(j));
/*       */               }
/* 11407 */               if (j != listSize - 1)
/*       */               {
/* 11409 */                 builder.append(",");
/*       */               }
/*       */             }
/* 11412 */             builder.append("\n");
/*       */           }
/*       */         }
/* 11415 */         String outputData = builder.toString();
/*       */         try
/*       */         {
/* 11418 */           out = response.getOutputStream();
/*       */         }
/*       */         catch (IOException ioe)
/*       */         {
/* 11422 */           ioe.printStackTrace();
/*       */         }
/* 11424 */         bis = new BufferedInputStream(new ByteArrayInputStream(outputData.getBytes()));
/* 11425 */         bos = new BufferedOutputStream(out);
/* 11426 */         byte[] buff = new byte['ࠀ'];
/*       */         int bytesRead;
/* 11428 */         while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
/*       */         {
/* 11430 */           bos.write(buff, 0, bytesRead);
/* 11431 */           bos.flush();
/*       */         }
/* 11433 */         out.flush();
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/* 11437 */         e.printStackTrace();
/*       */       }
/*       */       finally
/*       */       {
/* 11441 */         if (out != null)
/*       */         {
/* 11443 */           out.close();
/*       */         }
/* 11445 */         if (bis != null)
/*       */         {
/* 11447 */           bis.close();
/*       */         }
/* 11449 */         if (bos != null)
/*       */         {
/* 11451 */           bos.close();
/*       */         }
/*       */       }
/* 11454 */       return null;
/*       */     }
/* 11456 */     return mapping.findForward("report.glancereport");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showEumSummaryReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */   {
/* 11470 */     ActionMessages messages = new ActionMessages();
/* 11471 */     ReportForm controls = (ReportForm)form;
/* 11472 */     LinkedHashMap<String, HashMap<String, Object>> agentVSresTypeVSAvg = new LinkedHashMap();
/* 11473 */     LinkedHashMap<String, String> agentIdVSname = new LinkedHashMap();
/* 11474 */     LinkedHashMap<String, String> allToolTips = new LinkedHashMap();
/* 11475 */     ArrayList<String> resTypeList = new ArrayList();
/* 11476 */     String period = controls.getPeriod();
/* 11477 */     boolean hasSummaryData = false;
/* 11478 */     boolean hasDownData = false;
/*       */     
/* 11480 */     long[] timeStamps = null;
/* 11481 */     if ((controls.getStartDate() == null) || (controls.getStartDate().equals("")) || (controls.getEndDate().equals("")))
/*       */     {
/* 11483 */       timeStamps = ReportUtilities.getTimeStamp(period);
/*       */     }
/*       */     else
/*       */     {
/* 11487 */       period = "4";
/*       */       try
/*       */       {
/* 11490 */         timeStamps = ReportUtilities.parseTimeAndDate(controls.getStartDate(), controls.getEndDate());
/*       */       }
/*       */       catch (IllegalArgumentException iae)
/*       */       {
/* 11494 */         String errMsg = iae.getMessage();
/* 11495 */         AMLog.debug("Reports :  " + errMsg);
/* 11496 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errMsg));
/* 11497 */         saveMessages(request, messages);
/* 11498 */         request.setAttribute("heading", "0");
/* 11499 */         request.setAttribute("strTime", "0");
/* 11500 */         return mapping.findForward("report.message");
/*       */       }
/*       */     }
/* 11503 */     controls.setPeriod(period);
/*       */     
/* 11505 */     StringBuilder ownerCondition = new StringBuilder();
/* 11506 */     if ((privilegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */     {
/* 11508 */       if (Constants.isUserResourceEnabled()) {
/* 11509 */         String loginUserid = Constants.getLoginUserid(request);
/* 11510 */         ownerCondition.append(" and mo.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")");
/*       */       } else {
/* 11512 */         Vector resourceids = ReportUtilities.getResourceIdentity(request.getRemoteUser(), request);
/* 11513 */         ownerCondition.append(" and ").append(ReportUtilities.getCondition("mo.RESOURCEID", resourceids)).append(" ");
/*       */       }
/*       */     }
/* 11516 */     HashMap<String, ArrayList<String>> archTable = new HashMap();
/* 11517 */     String attrQuery = "select att.ATTRIBUTEID,att.ATTRIBUTE,att.DISPLAYNAME ,att.RESOURCETYPE,att.UNITS,ext.ARCHIVEDDATA_TABLENAME ,ext.EXPRESSION from AM_ATTRIBUTES as att, AM_ManagedResourceType as amt,AM_ATTRIBUTES_EXT as ext where amt.SUBGROUP in " + Constants.resourceTypesEUM + " and amt.RESOURCETYPE=att.RESOURCETYPE and att.ATTRIBUTEID=ext.ATTRIBUTEID and att.ATTRIBUTE in  ('Total Response Time','ResponseTime','rtt_avearage') and ext.REPORTS_ENABLED=1 and ext.ATTRIBUTE_LEVEL=1 order by ATTRIBUTE";
/* 11518 */     ResultSet attRs = null;
/*       */     try
/*       */     {
/* 11521 */       attRs = AMConnectionPool.executeQueryStmt(attrQuery);
/* 11522 */       while (attRs.next())
/*       */       {
/* 11524 */         String archivedTableName = attRs.getString("ARCHIVEDDATA_TABLENAME");
/* 11525 */         String attrId = attRs.getString("ATTRIBUTEID");
/* 11526 */         ArrayList list = null;
/* 11527 */         if (!archTable.containsKey(archivedTableName))
/*       */         {
/* 11529 */           list = new ArrayList();
/* 11530 */           list.add(attrId);
/*       */         }
/*       */         else
/*       */         {
/* 11534 */           list = (ArrayList)archTable.get(archivedTableName);
/* 11535 */           list.add(attrId);
/*       */         }
/* 11537 */         archTable.put(archivedTableName, list);
/*       */ 
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (Exception e) {}finally
/*       */     {
/*       */ 
/* 11546 */       AMConnectionPool.closeStatement(attRs);
/*       */     }
/*       */     try
/*       */     {
/* 11550 */       Iterator<String> it = archTable.keySet().iterator();
/* 11551 */       while (it.hasNext())
/*       */       {
/* 11553 */         String archTableStr = (String)it.next();
/* 11554 */         long[] dailyRptTimestamp = ReportUtilities.getDailyStartEndTime(timeStamps[0], timeStamps[1], archTableStr);
/* 11555 */         String attrCon = " and " + ReportUtilities.getCondition("AM_ATTRIBUTES.ATTRIBUTEID", new Vector((ArrayList)archTable.get(archTableStr)));
/* 11556 */         String dailyRptCondition = " and arch.DURATION=1 and arch.ARCHIVEDTIME >=" + timeStamps[0] + " and arch.ARCHIVEDTIME <=" + timeStamps[1];
/*       */         
/* 11558 */         if (dailyRptTimestamp[2] > 0L)
/*       */         {
/* 11560 */           dailyRptCondition = " and ( arch.DURATION=1 and arch.ARCHIVEDTIME >=" + dailyRptTimestamp[0] + " and arch.ARCHIVEDTIME <=" + dailyRptTimestamp[1] + " OR arch.DURATION=2 and arch.ARCHIVEDTIME >=" + dailyRptTimestamp[2] + " and arch.ARCHIVEDTIME <=" + dailyRptTimestamp[3] + ") ";
/*       */         }
/* 11562 */         String dataQuery = DBQueryUtil.getDBQuery("am.amreportAction.showEumSummaryReport1", new String[] { archTableStr, ownerCondition.toString() + attrCon, dailyRptCondition });
/*       */         
/* 11564 */         dataQuery = getTopNValues(dataQuery, String.valueOf(controls.getNumberOfRows()));
/* 11565 */         System.out.println("AMReportActions.showEumSummaryReport() dataQuery ==>" + dataQuery);
/* 11566 */         ResultSet dataRs = null;
/*       */         try
/*       */         {
/* 11569 */           dataRs = AMConnectionPool.executeQueryStmt(dataQuery);
/* 11570 */           HashMap<String, Object> resTypeVSAvgVal = null;
/* 11571 */           HashMap<String, String> toolTipMap = null;
/* 11572 */           int typeCount = 0;
/* 11573 */           String tempResType = null;
/* 11574 */           String tempAgentId = null;
/* 11575 */           while (dataRs.next())
/*       */           {
/* 11577 */             hasSummaryData = true;
/* 11578 */             boolean isLastRow = dataRs.isLast();
/* 11579 */             String agentId = dataRs.getString("AGENTID");
/* 11580 */             String resType = dataRs.getString("ResType");
/* 11581 */             String agentName = dataRs.getString("DISPLAYNAME");
/* 11582 */             String monitorName = dataRs.getString("Name");
/* 11583 */             float avgFloat = dataRs.getFloat("Average");
/* 11584 */             float tmpFloat = 0.0F;
/* 11585 */             StringBuilder toolTipVal = new StringBuilder("&lt;b&gt;");
/* 11586 */             agentIdVSname.put(agentId, agentName);
/*       */             
/* 11588 */             if (!resTypeList.contains(resType))
/*       */             {
/* 11590 */               resTypeList.add(resType);
/*       */             }
/*       */             
/* 11593 */             if (agentVSresTypeVSAvg.containsKey(agentId))
/*       */             {
/* 11595 */               resTypeVSAvgVal = (HashMap)agentVSresTypeVSAvg.get(agentId);
/* 11596 */               tmpFloat = avgFloat;
/* 11597 */               if (resTypeVSAvgVal.get(resType) != null)
/*       */               {
/* 11599 */                 tmpFloat = ((Float)resTypeVSAvgVal.get(resType)).floatValue();
/* 11600 */                 tmpFloat += avgFloat;
/*       */               }
/* 11602 */               resTypeVSAvgVal.put(resType, Float.valueOf(tmpFloat));
/*       */             }
/*       */             else
/*       */             {
/* 11606 */               resTypeVSAvgVal = new HashMap();
/* 11607 */               resTypeVSAvgVal.put(resType, Float.valueOf(avgFloat));
/*       */             }
/*       */             
/*       */ 
/* 11611 */             typeCount++;
/* 11612 */             if (tempResType == null)
/*       */             {
/* 11614 */               tempResType = resType;
/* 11615 */               tempAgentId = agentId;
/*       */ 
/*       */ 
/*       */             }
/* 11619 */             else if (tempAgentId.equals(agentId))
/*       */             {
/* 11621 */               if ((!tempResType.equals(resType)) || (isLastRow))
/*       */               {
/* 11623 */                 Float valToAvg = (Float)resTypeVSAvgVal.get(tempResType);
/* 11624 */                 if (isLastRow)
/*       */                 {
/* 11626 */                   valToAvg = Float.valueOf(valToAvg.floatValue() / typeCount);
/*       */                 }
/*       */                 else
/*       */                 {
/* 11630 */                   valToAvg = Float.valueOf(valToAvg.floatValue() / (typeCount - 1));
/*       */                 }
/* 11632 */                 resTypeVSAvgVal.put(tempResType, Float.valueOf(ReportUtilities.roundOff(valToAvg.floatValue(), 2)));
/* 11633 */                 tempResType = resType;
/* 11634 */                 typeCount = 1;
/*       */               }
/*       */             }
/*       */             else
/*       */             {
/* 11639 */               HashMap tempMap = (HashMap)agentVSresTypeVSAvg.get(tempAgentId);
/* 11640 */               Float fl = (Float)tempMap.get(tempResType);
/* 11641 */               fl = Float.valueOf(fl.floatValue() / (typeCount - 1));
/* 11642 */               tempMap.put(tempResType, Float.valueOf(ReportUtilities.roundOff(fl.floatValue(), 2)));
/* 11643 */               agentVSresTypeVSAvg.put(tempAgentId, tempMap);
/* 11644 */               typeCount = 1;
/* 11645 */               tempAgentId = agentId;
/* 11646 */               tempResType = resType;
/*       */             }
/*       */             
/*       */ 
/*       */ 
/*       */ 
/* 11652 */             agentVSresTypeVSAvg.put(agentId, resTypeVSAvgVal);
/*       */             
/* 11654 */             toolTipVal.append(monitorName).append("&lt;&#47b&gt; : ").append(avgFloat).append("ms");
/* 11655 */             if ((toolTipMap != null) && (toolTipMap.containsKey(agentId + "#" + resType)))
/*       */             {
/* 11657 */               toolTipVal = toolTipVal.append("&lt;br&gt;").append((String)toolTipMap.get(agentId + "#" + resType)).append("&lt;br&gt;");
/*       */             }
/*       */             else
/*       */             {
/* 11661 */               toolTipMap = new HashMap();
/*       */             }
/* 11663 */             toolTipMap.put(agentId + "#" + resType, toolTipVal.toString());
/* 11664 */             allToolTips.putAll(toolTipMap);
/*       */           }
/*       */         }
/*       */         catch (Exception e)
/*       */         {
/* 11669 */           e.printStackTrace();
/*       */         }
/*       */         finally
/*       */         {
/* 11673 */           AMConnectionPool.closeStatement(dataRs);
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 11679 */       e.printStackTrace();
/*       */     }
/* 11681 */     request.setAttribute("agentIdVSname", agentIdVSname);
/* 11682 */     request.setAttribute("agentVSresTypeVSAvg", agentVSresTypeVSAvg);
/* 11683 */     request.setAttribute("allToolTips", allToolTips);
/* 11684 */     request.setAttribute("resTypeList", resTypeList);
/* 11685 */     request.setAttribute("csv", "false");
/*       */     
/*       */ 
/*       */ 
/* 11689 */     ArrayList<String> agentIdList = Constants.getEUMAgentIds();
/* 11690 */     ArrayList<ArrayList> downTimeList = new ArrayList();
/* 11691 */     if (!agentIdList.isEmpty())
/*       */     {
/* 11693 */       Hashtable agentIdVsMinTime = new Hashtable();
/* 11694 */       long startTime = timeStamps[0];
/* 11695 */       long endTime = timeStamps[1];
/*       */       
/* 11697 */       long stTime = startTime;
/*       */       
/* 11699 */       agentIdVsMinTime = ReportUtilities.getMinTimeForEUMAgent(new Vector(agentIdList));
/*       */       
/* 11701 */       Enumeration enum1 = agentIdVsMinTime.keys();
/*       */       
/* 11703 */       long totalduration = 0L;
/* 11704 */       Properties durationProp = new Properties();
/* 11705 */       while (enum1.hasMoreElements())
/*       */       {
/* 11707 */         startTime = stTime;
/* 11708 */         String agentID = (String)enum1.nextElement();
/* 11709 */         String minTime = String.valueOf(agentIdVsMinTime.get(agentID));
/* 11710 */         long minTimeInDB = Long.parseLong(minTime);
/* 11711 */         long tempStartTime = startTime;
/* 11712 */         if (minTimeInDB > tempStartTime)
/*       */         {
/* 11714 */           tempStartTime = minTimeInDB;
/*       */         }
/*       */         
/* 11717 */         if (tempStartTime > endTime)
/*       */         {
/* 11719 */           agentIdList.remove(agentID);
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/* 11725 */       String downTimeQuery = "select agent.DISPLAYNAME,agent.CREATIONTIME,data.AGENTID,max(DOWNTIME) as LASTDOWN, sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TOTALDOWNTIME,count(DOWNTIME) as NOOFDOWNTIMES from AM_EUMAGENT_DOWNTIMEDATA data left outer join AM_RBMAGENTDATA agent on data.AGENTID=agent.AGENTID where (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and data.AGENTID <> -1 group by agent.DISPLAYNAME,data.AGENTID,agent.CREATIONTIME";
/* 11726 */       System.out.println("AMReportActions.showEumSummaryReport() downTimeQuery ==>" + downTimeQuery);
/* 11727 */       ResultSet downRs = null;
/*       */       
/*       */       try
/*       */       {
/* 11731 */         downRs = AMConnectionPool.executeQueryStmt(downTimeQuery);
/* 11732 */         while (downRs.next())
/*       */         {
/* 11734 */           hasDownData = true;
/* 11735 */           String agentName = downRs.getString("DISPLAYNAME");
/* 11736 */           long creationTime = downRs.getLong("CREATIONTIME");
/* 11737 */           String agentId = downRs.getString("AGENTID");
/* 11738 */           String noOfDownTimes = downRs.getString("NOOFDOWNTIMES");
/* 11739 */           long totaldowntime = downRs.getLong("TOTALDOWNTIME");
/*       */           
/* 11741 */           long totalDuration = endTime - creationTime;
/* 11742 */           long uptime = totalDuration - totaldowntime;
/* 11743 */           float upPercent = ReportUtilities.roundOff((float)uptime / (float)totalDuration * 100.0F, 2);
/*       */           
/* 11745 */           ArrayList childList = new ArrayList();
/* 11746 */           childList.add(agentName == null ? "Local Server" : agentName);
/* 11747 */           childList.add(Float.valueOf(upPercent));
/* 11748 */           childList.add(noOfDownTimes);
/* 11749 */           childList.add(ReportUtilities.format(totaldowntime));
/* 11750 */           childList.add(FormatUtil.formatDT(downRs.getString("LASTDOWN")));
/* 11751 */           downTimeList.add(childList);
/*       */         }
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/* 11756 */         e.printStackTrace();
/*       */       }
/*       */       finally
/*       */       {
/* 11760 */         AMConnectionPool.closeStatement(downRs);
/*       */       }
/* 11762 */       request.setAttribute("strTime", new java.util.Date(new Long(startTime).longValue()));
/* 11763 */       request.setAttribute("endTime", new java.util.Date(new Long(endTime).longValue()));
/*       */     }
/* 11765 */     request.setAttribute("downTimeList", downTimeList);
/* 11766 */     if (controls.getReporttype().equals("pdf"))
/*       */     {
/* 11768 */       request.setAttribute("heading1", FormatUtil.getString("am.webclient.eum.report.avgresponsetime"));
/* 11769 */       request.setAttribute("heading2", FormatUtil.getString("am.webclient.eum.report.avgavailability"));
/* 11770 */       request.setAttribute("reportname", "EUMSummary");
/* 11771 */       request.setAttribute("reportType", "pdf");
/* 11772 */       request.setAttribute("report-type-template", "report.eumsummary");
/* 11773 */       return mapping.findForward("report.eumsummary.pdf");
/*       */     }
/* 11775 */     if ((!hasDownData) && (!hasSummaryData))
/*       */     {
/* 11777 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("report.nodata.alerts"));
/* 11778 */       saveMessages(request, messages);
/* 11779 */       request.setAttribute("heading", "0");
/* 11780 */       request.setAttribute("strTime", "0");
/* 11781 */       return mapping.findForward("report.message");
/*       */     }
/* 11783 */     if (request.getAttribute("headingPeriod") == null)
/*       */     {
/* 11785 */       request.setAttribute("headingPeriod", controls.getReportPeriod());
/*       */     }
/* 11787 */     if (request.getAttribute("headingCategory") == null)
/*       */     {
/* 11789 */       request.setAttribute("headingCategory", FormatUtil.getString("am.webclient.eumagents.text"));
/*       */     }
/* 11791 */     request.setAttribute("heading", "0");
/* 11792 */     return new ActionForward("/showTile.do?TileName=.EumSummartReport");
/*       */   }
/*       */   
/*       */   public ActionForward generateUnderSizedMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 11796 */     String mapforward = "report.customreports";
/* 11797 */     return mapping.findForward(mapforward);
/*       */   }
/*       */   
/*       */   public ActionForward generateIndividualReportCapacityPlanning(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/* 11802 */     String mapforward = "report.customreports.individual";
/* 11803 */     return mapping.findForward(mapforward);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   private void updateEumDownData(String downTimequery, HttpServletRequest request)
/*       */   {
/* 11811 */     System.out.println("AMReportActions.updateEumDownData() ==>" + downTimequery);
/* 11812 */     ArrayList<ArrayList<String>> downTimeParentList = new ArrayList();
/* 11813 */     ResultSet downTimeRs = null;
/*       */     try
/*       */     {
/* 11816 */       downTimeRs = AMConnectionPool.executeQueryStmt(downTimequery);
/* 11817 */       while (downTimeRs.next())
/*       */       {
/* 11819 */         ArrayList<String> downTimechildList = new ArrayList();
/* 11820 */         String resName = downTimeRs.getString("DISPLAYNAME");
/* 11821 */         String childID = downTimeRs.getString("RESID");
/* 11822 */         long downTime = downTimeRs.getLong("DOWNTIME");
/* 11823 */         long upTime = downTimeRs.getLong("UPTIME");
/* 11824 */         String description = downTimeRs.getString("SHORT_DESCRIPTION");
/* 11825 */         if (description == null)
/*       */         {
/* 11827 */           description = "";
/*       */         }
/*       */         
/* 11830 */         if (downTimeRs.getInt("TYPE") == 2)
/*       */         {
/* 11832 */           description = FormatUtil.getString("am.modowntime.reason.unmanaged");
/*       */         }
/* 11834 */         else if (downTimeRs.getInt("TYPE") == 3)
/*       */         {
/* 11836 */           description = FormatUtil.getString("am.modowntime.reason.maintenance");
/*       */         }
/*       */         
/* 11839 */         downTimechildList.add(downTimeRs.getString("DISPLAYNAME"));
/* 11840 */         downTimechildList.add(FormatUtil.formatDT(String.valueOf(downTime)));
/* 11841 */         downTimechildList.add(FormatUtil.formatDT(String.valueOf(upTime)));
/* 11842 */         downTimechildList.add(ReportUtilities.format(upTime - downTime));
/* 11843 */         downTimechildList.add(description);
/* 11844 */         downTimechildList.add("" + downTime);
/* 11845 */         downTimechildList.add(downTimeRs.getString("REASONID"));
/* 11846 */         downTimechildList.add(downTimeRs.getString("TYPE"));
/* 11847 */         downTimechildList.add(childID);
/* 11848 */         downTimeParentList.add(downTimechildList);
/*       */       }
/* 11850 */       request.setAttribute("downTimeParentList", downTimeParentList);
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 11854 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/* 11858 */       AMConnectionPool.closeStatement(downTimeRs);
/*       */     }
/*       */   }
/*       */   
/*       */   class ComparatorImpl implements Comparator
/*       */   {
/*       */     public ComparatorImpl() {}
/*       */     
/*       */     public int compare(Object o1, Object o2) {
/* 11867 */       if ((o1 == null) || (o2 == null)) {
/* 11868 */         return 0;
/*       */       }
/*       */       
/* 11871 */       Properties prop1 = (Properties)o1;
/* 11872 */       Properties prop2 = (Properties)o2;
/*       */       
/* 11874 */       long val1 = ((Long)prop1.get("DowntimeInLong")).longValue();
/* 11875 */       long val2 = ((Long)prop2.get("DowntimeInLong")).longValue();
/*       */       
/*       */ 
/* 11878 */       if (val1 == val2) {
/* 11879 */         return 0;
/*       */       }
/* 11881 */       if (val1 > val2) {
/* 11882 */         return -1;
/*       */       }
/*       */       
/* 11885 */       return 1;
/*       */     }
/*       */     
/*       */ 
/*       */     public boolean equals(Object obj)
/*       */     {
/* 11891 */       return false;
/*       */     }
/*       */   }
/*       */   
/*       */   class ComparatorImplForSubGroups implements Comparator
/*       */   {
/*       */     public ComparatorImplForSubGroups() {}
/*       */     
/*       */     public int compare(Object o1, Object o2) {
/* 11900 */       if ((o1 == null) || (o2 == null)) {
/* 11901 */         return 0;
/*       */       }
/*       */       
/* 11904 */       Properties prop1 = (Properties)o1;
/* 11905 */       Properties prop2 = (Properties)o2;
/*       */       
/* 11907 */       long val1 = ((Long)prop1.get("DowntimeInLong")).longValue();
/* 11908 */       long val2 = ((Long)prop2.get("DowntimeInLong")).longValue();
/*       */       
/* 11910 */       String name1 = (String)prop1.get("Name");
/* 11911 */       String name2 = (String)prop2.get("Name");
/*       */       
/* 11913 */       String subgroupPrefix1 = name1.substring(0, name1.lastIndexOf("->") == -1 ? 0 : name1.lastIndexOf("->"));
/* 11914 */       String subgroupPrefix2 = name2.substring(0, name2.lastIndexOf("->") == -1 ? 0 : name2.lastIndexOf("->"));
/*       */       
/* 11916 */       if (subgroupPrefix1.equals(subgroupPrefix2)) {
/* 11917 */         if (val1 == val2) {
/* 11918 */           return 0;
/*       */         }
/* 11920 */         if (val1 > val2) {
/* 11921 */           return -1;
/*       */         }
/*       */         
/* 11924 */         return 1;
/*       */       }
/*       */       
/* 11927 */       return 0;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/* 11932 */     public boolean equals(Object obj) { return false; }
/*       */   }
/*       */   
/*       */   class NameComparator implements Comparator {
/*       */     NameComparator() {}
/*       */     
/* 11938 */     public int compare(Object o1, Object o2) { Properties ohm1 = (Properties)o1;
/* 11939 */       Properties ohm2 = (Properties)o2;
/* 11940 */       String lab1 = ohm1.getProperty("label");
/* 11941 */       String lab2 = ohm2.getProperty("label");
/* 11942 */       return lab1.compareToIgnoreCase(lab2);
/*       */     }
/*       */   }
/*       */   
/*       */   public ActionForward generateMSSQLGeneralDetailsReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 11947 */     ActionMessages messages = new ActionMessages();
/* 11948 */     ActionErrors errors = new ActionErrors();
/*       */     
/* 11950 */     ResultSet set = null;
/* 11951 */     ResultSet results = null;
/* 11952 */     ArrayList sqlServerInfoList = null;
/*       */     try {
/* 11954 */       String heading = FormatUtil.getString("am.reporttab.mssql.reports.mssqlgeneraldetailsreport.text");
/* 11955 */       controls = (ReportForm)form;
/* 11956 */       request.setAttribute("heading", heading);
/* 11957 */       request.setAttribute("reportType", controls.getReporttype());
/* 11958 */       request.setAttribute("HelpKey", getMonitorHelpKey(request.getParameter("resourceType").toString()));
/* 11959 */       AMLog.debug("Reports : Generating Fault Report for " + request.getParameter("resourceType").toString());
/* 11960 */       String query = "";
/* 11961 */       String[] selectedColumns = { "DISPLAYNAME", "VERSION", "PRODUCTLEVEL", "ODBCDRIVERVERSION", "PROCESSID", "INSTANCENAME", "SERVERNAME", "ISCLUSTERED", "MACHINENAME" };
/* 11962 */       if (request.getParameter("selectedColumns") != null) {
/* 11963 */         selectedColumns = request.getParameter("selectedColumns").split(",");
/*       */       }
/* 11965 */       HashMap columnHeadings = new HashMap();
/* 11966 */       columnHeadings.put("selectedColumns", selectedColumns);
/* 11967 */       columnHeadings.put("DISPLAYNAME", "Display Name");
/* 11968 */       columnHeadings.put("VERSION", "Version");
/* 11969 */       columnHeadings.put("PRODUCTLEVEL", "Product Level");
/* 11970 */       columnHeadings.put("ODBCDRIVERVERSION", "ODBCDriver Version");
/* 11971 */       columnHeadings.put("SQLCOLLATION", "SQL Collation");
/* 11972 */       columnHeadings.put("PROCESSID", "Process ID");
/* 11973 */       columnHeadings.put("RESOURCEVERSION", "Resource Version");
/* 11974 */       columnHeadings.put("INSTANCENAME", "Instance Name");
/* 11975 */       columnHeadings.put("SERVERNAME", "Server Name");
/* 11976 */       columnHeadings.put("ISCLUSTERED", "IsClustered");
/* 11977 */       columnHeadings.put("COMPUTERNAMEPHYSICALNETBIOS", "ComputerNamePhysicalNetBios");
/* 11978 */       columnHeadings.put("MACHINENAME", "Machine Name");
/* 11979 */       query = "SELECT AM_ManagedObject.DISPLAYNAME, AM_MSSQLDETAILS.VERSION,AM_MSSQLDETAILS.PRODUCTLEVEL,AM_MSSQLDETAILS.ODBCDRIVERVERSION,AM_MSSQLDETAILS.SQLCOLLATION,AM_MSSQLDETAILS.PROCESSID,AM_MSSQLDETAILS.RESOURCEVERSION,AM_MSSQLDETAILS.INSTANCENAME,AM_MSSQLDETAILS.SERVERNAME,AM_MSSQLDETAILS.ISCLUSTERED,AM_MSSQLDETAILS.COMPUTERNAMEPHYSICALNETBIOS,AM_MSSQLDETAILS.MACHINENAME from AM_MSSQLDETAILS JOIN AM_ManagedObject ON AM_MSSQLDETAILS.RESOURCEID=AM_ManagedObject.RESOURCEID";
/* 11980 */       if ((request.isUserInRole("OPERATOR")) || (EnterpriseUtil.isIt360MSPEdition())) {
/* 11981 */         String owner = request.getRemoteUser();
/* 11982 */         Vector resourceids = ReportUtilities.getResourceIdentity(owner, request);
/* 11983 */         query = "SELECT AM_ManagedObject.DISPLAYNAME, AM_MSSQLDETAILS.VERSION,AM_MSSQLDETAILS.PRODUCTLEVEL,AM_MSSQLDETAILS.ODBCDRIVERVERSION,AM_MSSQLDETAILS.SQLCOLLATION,AM_MSSQLDETAILS.PROCESSID,AM_MSSQLDETAILS.RESOURCEVERSION,AM_MSSQLDETAILS.INSTANCENAME,AM_MSSQLDETAILS.SERVERNAME,AM_MSSQLDETAILS.ISCLUSTERED,AM_MSSQLDETAILS.COMPUTERNAMEPHYSICALNETBIOS,AM_MSSQLDETAILS.MACHINENAME from AM_MSSQLDETAILS JOIN AM_ManagedObject ON AM_MSSQLDETAILS.RESOURCEID=AM_ManagedObject.RESOURCEID where " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids);
/*       */       }
/* 11985 */       ArrayList checkBoxList = new ArrayList();
/* 11986 */       if ((controls.getColumnsAdded() != null) || (request.getParameter("columnsAdded") != null)) {
/* 11987 */         String columnsAdded = request.getParameter("columnsAdded") != null ? request.getParameter("columnsAdded") : controls.getColumnsAdded();
/* 11988 */         if ((!columnsAdded.equals("")) && (columnsAdded.length() > 0)) {
/* 11989 */           selectedColumns = columnsAdded.split(",");
/* 11990 */           String outerQuery = "";
/* 11991 */           for (int i = 0; i < selectedColumns.length; i++) {
/* 11992 */             if (i == 0) {
/* 11993 */               outerQuery = outerQuery + selectedColumns[i];
/*       */             } else {
/* 11995 */               outerQuery = outerQuery + "," + selectedColumns[i];
/*       */             }
/* 11997 */             checkBoxList.add(selectedColumns[i]);
/*       */           }
/* 11999 */           query = "select " + outerQuery + " from(" + query + ")aa";
/*       */         }
/*       */       }
/*       */       
/* 12003 */       sqlServerInfoList = new ArrayList();
/* 12004 */       AMLog.debug("controls.getColumnsAdded() : " + controls.getColumnsAdded());
/* 12005 */       AMLog.debug("controls.getReporttype() : " + controls.getReporttype());
/* 12006 */       AMLog.debug("MSSQLGeneralDetailsReport ---> query ---> " + query);
/* 12007 */       results = AMConnectionPool.executeQueryStmt(query);
/* 12008 */       int i; while (results.next()) {
/* 12009 */         Properties sqlServerInfo = new Properties();
/* 12010 */         for (i = 0; i < selectedColumns.length; i++) {
/* 12011 */           if (results.getString(selectedColumns[i]) != null) {
/* 12012 */             sqlServerInfo.setProperty(selectedColumns[i], results.getString(selectedColumns[i]));
/*       */           } else {
/* 12014 */             sqlServerInfo.setProperty(selectedColumns[i], "-");
/*       */           }
/*       */         }
/* 12017 */         sqlServerInfoList.add(sqlServerInfo);
/*       */       }
/* 12019 */       AMConnectionPool.closeStatement(results);
/* 12020 */       request.setAttribute("getColumnsAdded", controls.getColumnsAdded());
/* 12021 */       request.setAttribute("checkBoxList", checkBoxList);
/* 12022 */       request.setAttribute("selectedColumns", selectedColumns);
/* 12023 */       request.setAttribute("columnHeadings", columnHeadings);
/* 12024 */       request.setAttribute("data", sqlServerInfoList);
/* 12025 */       saveErrors(request, errors);
/* 12026 */       String emailPDF = null;
/* 12027 */       if (request.getParameter("getpdf") != null) {
/* 12028 */         emailPDF = request.getParameter("getpdf");
/*       */       }
/* 12030 */       if (((controls.getReporttype() != null) && ((controls.getReporttype().equals("pdf")) || (controls.getReporttype().equals("excel")))) || ((emailPDF != null) && (emailPDF.equals("true")))) {
/* 12031 */         request.setAttribute("report-type-template", "report.mssqlgeneraldetails");
/* 12032 */         request.setAttribute("sp-report-type", controls.getReporttype().toString());
/* 12033 */         return mapping.findForward("report.mssqlgeneraldetails.pdf"); }
/* 12034 */       if ((controls.getReporttype() != null) && (controls.getReporttype().equals("csv"))) {
/* 12035 */         request.setAttribute("reportType", controls.getReporttype());
/* 12036 */         request.setAttribute("PRINTER_FRIENDLY", "true");
/* 12037 */         return mapping.findForward("report.mssqlgeneraldetails.csv");
/*       */       }
/* 12039 */       request.setAttribute("reportType", controls.getReporttype());
/* 12040 */       request.setAttribute("PRINTER_FRIENDLY", "true");
/* 12041 */       return mapping.findForward("report.mssqlgeneraldetails");
/*       */     } catch (Exception exp) {
/*       */       ReportForm controls;
/* 12044 */       exp.printStackTrace();
/* 12045 */       AMLog.fatal("Reports :  Exception in generating MS-SQL Performance Report", exp);
/* 12046 */       request.setAttribute("heading", "0");
/* 12047 */       request.setAttribute("strTime", "0");
/* 12048 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/* 12049 */       saveMessages(request, messages);
/* 12050 */       return mapping.findForward("report.message");
/*       */     } finally {
/*       */       try {
/* 12053 */         if (set != null) {
/* 12054 */           set.close();
/*       */         }
/* 12056 */         if (results != null) {
/* 12057 */           results.close();
/*       */         }
/*       */       } catch (Exception e) {
/* 12060 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   public static List getHAIDforResourceTypes()
/*       */   {
/* 12067 */     return getHAIDforResourceTypes(DBQueryUtil.getDBType());
/*       */   }
/*       */   
/*       */   public static List getHAIDforResourceTypes(String dbtype) {
/* 12071 */     List<String> haids = new ArrayList();
/* 12072 */     ResultSet rs = null;
/*       */     try
/*       */     {
/* 12075 */       if ("mssql".equalsIgnoreCase(dbtype)) {
/* 12076 */         String parentchildQry = "WITH tblParent(PARENTID) AS ( select HAID from AM_HOLISTICAPPLICATION where HAID IN (select distinct PARENTID  from AM_PARENTCHILDMAPPER where CHILDID IN (select RESOURCEID  from AM_ManagedObject where TYPE IN ('AIX','FreeBSD / OpenBSD','HP-UX / Tru64','Mac OS','Novell','Sun Solaris','Hyper-V-Server','HyperVVirtualMachine','VMWare ESX/ESXi','VirtualMachine','XenServerHost','XenServerVM','Linux','Windows 2003','Windows Vista','WindowsNT','WindowsNT_Server','Windows95','Windows 2000','Windows XP','Windows 2008','Windows 7','Windows 8','Windows 10','Windows 2012'))) UNION ALL SELECT AM_PARENTCHILDMAPPER.PARENTID FROM AM_PARENTCHILDMAPPER JOIN tblParent  ON AM_PARENTCHILDMAPPER.CHILDID = tblParent.ParentId ) SELECT DISTINCT PARENTID FROM  tblParent OPTION(MAXRECURSION 100)";
/* 12077 */         rs = AMConnectionPool.executeQueryStmt(parentchildQry);
/* 12078 */         while (rs.next()) {
/* 12079 */           haids.add(rs.getString("PARENTID"));
/*       */         }
/* 12081 */       } else if ("pgsql".equalsIgnoreCase(dbtype)) {
/* 12082 */         String parentchildQry = "WITH RECURSIVE tblParent(PARENTID) AS ( select HAID from AM_HOLISTICAPPLICATION where HAID IN (select distinct PARENTID  from AM_PARENTCHILDMAPPER where CHILDID IN (select RESOURCEID  from AM_ManagedObject where TYPE IN ('AIX','FreeBSD / OpenBSD','HP-UX / Tru64','Mac OS','Novell','Sun Solaris','Hyper-V-Server','HyperVVirtualMachine','VMWare ESX/ESXi','VirtualMachine','XenServerHost','XenServerVM','Linux','Windows 2003','Windows Vista','WindowsNT','WindowsNT_Server','Windows95','Windows 2000','Windows XP','Windows 2008','Windows 7','Windows 8','Windows 10','Windows 2012'))) UNION ALL SELECT AM_PARENTCHILDMAPPER.PARENTID FROM AM_PARENTCHILDMAPPER JOIN tblParent  ON AM_PARENTCHILDMAPPER.CHILDID = tblParent.ParentId ) SELECT DISTINCT PARENTID FROM  tblParent";
/* 12083 */         rs = AMConnectionPool.executeQueryStmt(parentchildQry);
/* 12084 */         while (rs.next()) {
/* 12085 */           haids.add(rs.getString("PARENTID"));
/*       */         }
/*       */       } else {
/* 12088 */         List<String> subgroupIds = new ArrayList();
/* 12089 */         String query = null;
/* 12090 */         query = "select HAID,TYPE from AM_HOLISTICAPPLICATION where HAID IN (select distinct PARENTID  from AM_PARENTCHILDMAPPER where CHILDID IN (select RESOURCEID  from AM_ManagedObject where TYPE IN ('AIX','FreeBSD / OpenBSD','HP-UX / Tru64','Mac OS','Novell','Sun Solaris','Hyper-V-Server','HyperVVirtualMachine','VMWare ESX/ESXi','VirtualMachine','XenServerHost','XenServerVM','Linux','Windows 2003','Windows Vista','WindowsNT','WindowsNT_Server','Windows95','Windows 2000','Windows XP','Windows 2008','Windows 7','Windows 8','Windows 10','Windows 2012')))";
/* 12091 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 12092 */         while (rs.next())
/*       */         {
/* 12094 */           haids.add(rs.getString("HAID"));
/* 12095 */           if ("1".equals(rs.getString("TYPE")))
/* 12096 */             subgroupIds.add(rs.getString("HAID"));
/*       */         }
/* 12098 */         AMConnectionPool.closeStatement(rs);
/* 12099 */         for (String haid : subgroupIds) {
/* 12100 */           getMainGroupIdforSubGroup(haids, haid);
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 12106 */       e.printStackTrace();
/*       */     } finally {
/* 12108 */       if (rs != null) {
/* 12109 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */     }
/* 12112 */     return haids;
/*       */   }
/*       */   
/*       */   public static void getMainGroupIdforSubGroup(List haids, String haid) {
/* 12116 */     ResultSet rs = null;
/* 12117 */     Map<Integer, Integer> parentIds = new HashMap();
/*       */     try {
/* 12119 */       rs = AMConnectionPool.executeQueryStmt("select HAID,TYPE from AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER  where HAID=PARENTID and  CHILDID=" + haid);
/* 12120 */       if (rs.next()) {
/* 12121 */         parentIds.put(Integer.valueOf(rs.getInt("HAID")), Integer.valueOf(rs.getInt("TYPE")));
/*       */       }
/*       */     } catch (Exception ex) {
/* 12124 */       ex.printStackTrace();
/*       */     } finally {
/* 12126 */       if (rs != null) {
/* 12127 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */     }
/*       */     
/* 12131 */     for (Integer key : parentIds.keySet()) {
/* 12132 */       haids.add(key.toString());
/* 12133 */       if (((Integer)parentIds.get(key)).intValue() == 1) {
/* 12134 */         getMainGroupIdforSubGroup(haids, key.toString());
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private String getHaidResourceQuery(HttpServletRequest request) {
/* 12140 */     String haidquery = null;
/*       */     try
/*       */     {
/* 12143 */       if (Constants.isSsoEnabled()) {
/* 12144 */         String loginUserid = Constants.getLoginUserid(request);
/* 12145 */         haidquery = "select RESOURCEID as HAID,RESOURCENAME as DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_USERRESOURCESTABLE where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + "  and AM_HOLISTICAPPLICATION.TYPE=0 order by RESOURCENAME";
/*       */       } else {
/* 12147 */         String owner = request.getRemoteUser();
/* 12148 */         haidquery = "select RESOURCEID as HAID,RESOURCENAME as DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID  and AM_HOLISTICAPPLICATION.TYPE=0 and AM_UserPasswordTable.USERNAME='" + owner + "' order by RESOURCENAME";
/*       */       }
/*       */     }
/*       */     catch (Exception ex) {
/* 12152 */       ex.printStackTrace();
/*       */     }
/* 12154 */     return haidquery;
/*       */   }
/*       */   
/*       */   private static ArrayList getCurrentQueryMonitorResourceIDs(String resId, String type)
/*       */   {
/* 12159 */     ArrayList<String> curResids = new ArrayList();
/* 12160 */     ResultSet rs = null;
/* 12161 */     if ("QueryMonitor".equalsIgnoreCase(type)) {
/* 12162 */       rs = null;
/* 12163 */       String query = "select max(collectiontime) from AM_ManagedObjectData where RESID=" + resId;
/* 12164 */       long collectiontime = 0L;
/*       */       try {
/* 12166 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 12167 */         if (rs.next()) {
/* 12168 */           collectiontime = rs.getLong(1);
/*       */         }
/*       */       } catch (Exception exc) {
/* 12171 */         exc.printStackTrace();
/*       */       }
/* 12173 */       query = "select distinct RESID from AM_SCRIPT_TABULAR_NUMERIC_DATA_" + resId + " where COLLECTIONTIME=" + collectiontime;
/*       */       try {
/* 12175 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 12176 */         while (rs.next()) {
/* 12177 */           curResids.add(rs.getString(1));
/*       */         }
/*       */       } catch (Exception exc) {
/* 12180 */         exc.printStackTrace();
/*       */       }
/*       */       finally {
/* 12183 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */     }
/* 12186 */     return curResids;
/*       */   }
/*       */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\actions\AMReportActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */