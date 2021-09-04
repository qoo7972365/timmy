/*     */ package com.adventnet.appmanager.client.resourcemanagement;
/*     */ 
/*     */ import com.adventnet.appmanager.cam.CAMSNMPUtil;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*     */ import com.adventnet.appmanager.server.discovery.HostDiscoveryHandler;
/*     */ import com.adventnet.appmanager.server.framework.AMServerStartup;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.CAMServerDBUtil;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.WMIDBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AMManagedObjectCloning
/*     */ {
/*     */   public static void main(String[] args) {}
/*     */   
/*     */   public ArrayList addMonitors(String resid, Properties toadd, List details)
/*     */   {
/*  39 */     String hostname = null;
/*  40 */     String hosttype = null;
/*  41 */     String query = "select AM_ManagedObject.RESOURCENAME , TYPE  from AM_ManagedObject where RESOURCEID=" + resid;
/*     */     try
/*     */     {
/*  44 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  45 */       if (rs.next())
/*     */       {
/*  47 */         hostname = rs.getString("RESOURCENAME");
/*  48 */         hosttype = rs.getString("TYPE");
/*  49 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  54 */       e.printStackTrace();
/*     */     }
/*  56 */     List list = null;
/*  57 */     ArrayList output = new ArrayList();
/*  58 */     Properties toadd1 = (Properties)toadd.clone();
/*     */     try
/*     */     {
/*  61 */       list = details;
/*  62 */       System.out.println("Services in Host is " + list);
/*  63 */       Map det = new HashMap();
/*  64 */       det.put("TYPE", hosttype);
/*  65 */       det.put("RESOURCEID", resid);
/*  66 */       if ((toadd.getProperty("mode") != null) && (toadd.getProperty("mode").equals("All")))
/*     */       {
/*     */ 
/*     */ 
/*  70 */         addMonitor(det, resid, toadd, output);
/*  71 */         for (int i = 0; i < list.size(); i++)
/*     */         {
/*  73 */           Map map = (Map)list.get(i);
/*  74 */           addMonitor(map, resid, (Properties)toadd1.clone(), output);
/*  75 */           System.out.println("Map details is " + map);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  80 */         addMonitor(det, resid, toadd, output);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/*  86 */       e1.printStackTrace();
/*     */     }
/*  88 */     return output;
/*     */   }
/*     */   
/*     */   public void addMonitor(Map details, String id, Properties toadd, ArrayList output) {
/*  92 */     HostDiscoveryHandler addhostfordiscovery = new HostDiscoveryHandler();
/*  93 */     String type = (String)details.get("TYPE");
/*  94 */     String typeforadding = getType(type);
/*  95 */     String resid = (String)details.get("RESOURCEID");
/*  96 */     Properties prop = getConfiguration(typeforadding, resid, new Properties());
/*     */     
/*  98 */     if (prop.getProperty("port") == null)
/*     */     {
/* 100 */       prop.setProperty("port", "9999");
/*     */     }
/* 102 */     if (prop.getProperty("WTEnabled") == null)
/*     */     {
/* 104 */       prop.setProperty("WTEnabled", "false");
/*     */     }
/* 106 */     if (typeforadding.equalsIgnoreCase("mail"))
/*     */     {
/* 108 */       if ((prop.getProperty("popenabled") != null) && (prop.getProperty("popenabled").equals("false")))
/*     */       {
/* 110 */         prop.getProperty("popenabled", "false");
/*     */       }
/* 112 */       else if (prop.getProperty("popenabled") != null)
/*     */       {
/* 114 */         prop.getProperty("popenabled", "true");
/*     */       }
/*     */     }
/* 117 */     if (prop == null)
/*     */     {
/* 119 */       System.out.println("Properties is null for type" + type + " and ID" + resid);
/* 120 */       return;
/*     */     }
/*     */     
/*     */ 
/* 124 */     System.out.println("For type" + type + " and ID" + resid + " Properties is " + prop);
/*     */     
/* 126 */     prop.setProperty("addasip", "false");
/*     */     try
/*     */     {
/* 129 */       String poll = prop.getProperty("pollinterval");
/* 130 */       if (poll.indexOf(".") != -1)
/*     */       {
/* 132 */         poll = poll.substring(0, poll.indexOf("."));
/* 133 */         int pollint = Integer.parseInt(poll);
/* 134 */         pollint *= 60;
/* 135 */         System.out.println("Poll is " + poll);
/* 136 */         prop.setProperty("pollinterval", String.valueOf(pollint));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 141 */       prop.setProperty("pollinterval", "600");
/* 142 */       e.printStackTrace();
/*     */     }
/* 144 */     Hashtable configuredList = new Hashtable();
/* 145 */     Hashtable hostInformation = new Hashtable();
/* 146 */     configuredList.clear();
/* 147 */     hostInformation.clear();
/*     */     
/* 149 */     String hosts = (String)toadd.remove("hostip");
/* 150 */     StringTokenizer tokenizehosts = new StringTokenizer(hosts, ",");
/* 151 */     while (tokenizehosts.hasMoreTokens())
/*     */     {
/* 153 */       Properties tempProps = new Properties();
/* 154 */       String hostIp = tokenizehosts.nextToken();
/* 155 */       if (type.equals("Generic WMI"))
/*     */       {
/* 157 */         updateWMIAttributes(resid, hostIp, output);
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 164 */         toadd.setProperty("hostip", hostIp);
/* 165 */         prop.setProperty("DISPLAYNAME", toadd.getProperty("hostip"));
/*     */         try
/*     */         {
/* 168 */           if (toadd.getProperty("hostip").equals("localhost"))
/*     */           {
/* 170 */             hostIp = AMServerStartup.ipaddress;
/*     */           }
/* 172 */           hostIp = InetAddress.getByName(toadd.getProperty("hostip")).getHostAddress();
/* 173 */           if (InetAddress.getByName(hostIp).isLoopbackAddress())
/*     */           {
/* 175 */             hostIp = AMServerStartup.ipaddress;
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 182 */           e.printStackTrace();
/* 183 */           ArrayList row = new ArrayList();
/* 184 */           row.add(hostIp);
/* 185 */           row.add("-");
/* 186 */           row.add(FormatUtil.getString("Failed"));
/* 187 */           row.add(FormatUtil.getString("am.webclient.hostdiscovery.invalidhost"));
/* 188 */           output.add(row); }
/* 189 */         continue;
/*     */         
/* 191 */         toadd.setProperty("hostip", hostIp);
/* 192 */         tempProps.setProperty("HOST_IP", toadd.getProperty("hostip"));
/* 193 */         tempProps.setProperty("SUBNET_MASK", toadd.getProperty("subnet"));
/* 194 */         tempProps.setProperty("SERVER_TYPE", typeforadding);
/* 195 */         tempProps.setProperty("PORT", prop.getProperty("port"));
/* 196 */         if (prop.getProperty("popPort") != null)
/*     */         {
/* 198 */           tempProps.setProperty("popPort", prop.getProperty("popPort"));
/*     */         }
/* 200 */         if (prop.getProperty("smtpPort") != null)
/*     */         {
/* 202 */           tempProps.setProperty("PORT", prop.getProperty("smtpPort"));
/* 203 */           prop.setProperty("port", prop.getProperty("smtpPort"));
/*     */         }
/* 205 */         hostInformation.put(toadd.getProperty("hostip") + ":" + prop.getProperty("port"), tempProps);
/* 206 */         configuredList.put("HOST_CONFIG", hostInformation);
/* 207 */         Properties discoveryProperties = new Properties();
/* 208 */         discoveryProperties.setProperty("REDISCOVER_INTERVAL", "24");
/* 209 */         discoveryProperties.setProperty("HOUR", "-1");
/* 210 */         configuredList.put("DISC_DATA_PROP", discoveryProperties);
/* 211 */         boolean success = addhostfordiscovery.discoverHost(configuredList, prop, false);
/* 212 */         if (success)
/*     */         {
/* 214 */           if (typeforadding.equalsIgnoreCase("SNMP"))
/*     */           {
/* 216 */             updateSNMPCustomAttributes(id, prop.getProperty("id"));
/*     */           }
/* 218 */           ArrayList row = new ArrayList();
/* 219 */           row.add(toadd.getProperty("hostip"));
/* 220 */           if (typeforadding.equalsIgnoreCase("System"))
/*     */           {
/* 222 */             row.add("-");
/*     */           }
/*     */           else
/*     */           {
/* 226 */             row.add(prop.getProperty("port"));
/*     */           }
/* 228 */           row.add("Success");
/* 229 */           row.add("Monitoring Initiated Succesfully");
/* 230 */           output.add(row);
/* 231 */           updateConfigurations(id, prop.getProperty("id"));
/*     */         }
/*     */         else
/*     */         {
/* 235 */           ArrayList row = new ArrayList();
/* 236 */           row.add(toadd.getProperty("hostip"));
/* 237 */           if (typeforadding.equalsIgnoreCase("System"))
/*     */           {
/* 239 */             row.add("-");
/*     */           }
/*     */           else
/*     */           {
/* 243 */             row.add(prop.getProperty("port"));
/*     */           }
/* 245 */           row.add("Failed");
/* 246 */           row.add(FormatUtil.getString(prop.getProperty("error")));
/* 247 */           output.add(row);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Properties getConfiguration(String type, String id, Properties prop)
/*     */   {
/* 255 */     System.out.println("type is " + type);
/* 256 */     DataCollectionDBUtil dbutil = new DataCollectionDBUtil();
/* 257 */     String query = null;
/* 258 */     String oldquery = null;
/* 259 */     String eventlog_status = "false";
/* 260 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 261 */     if (type.equalsIgnoreCase("System"))
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/* 266 */         ResultSet rs = AMConnectionPool.executeQueryStmt("select STATUS from AM_HOSTEVENTLOGSTATUS where RESOURCEID=" + id);
/* 267 */         if (rs.next())
/*     */         {
/* 269 */           if (rs.getString("STATUS").equals("1")) {
/* 270 */             eventlog_status = "true";
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 275 */         e.printStackTrace();
/*     */       }
/* 277 */       query = "select HostDetails.USERNAME as username ," + DBQueryUtil.decode("PASSWORD") + " as password , HostDetails.PROMPT as prompt, AM_HOSTINFO.MODE as MODE , AM_HOSTINFO.SNMPCOMMUNITY as SNMPCOMMUNITY , AM_HOSTINFO.SNMPPORT as SNMPPORT , AM_HOSTINFO.TELNETPORT as TELNETPORT ,(CollectData.POLLINTERVAL/60) as pollinterval , CollectData.RESOURCETYPE as os  from AM_ManagedObject , HostDetails , AM_HOSTINFO, CollectData where AM_ManagedObject.RESOURCENAME=HostDetails.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_HOSTINFO.RESOURCEID and CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.RESOURCETYPE=AM_ManagedObject.TYPE";
/*     */ 
/*     */     }
/* 280 */     else if ((type.equalsIgnoreCase("mssqldb")) || (type.equalsIgnoreCase("sybasedb")) || (type.equalsIgnoreCase("mysqldb")) || (type.equalsIgnoreCase("db2")) || (type.equalsIgnoreCase(".Net")) || (type.equalsIgnoreCase("exchange")) || (type.equalsIgnoreCase("weblogic")) || (type.equalsIgnoreCase("wta")) || (type.equalsIgnoreCase("oracleas")) || (type.equalsIgnoreCase("wli")) || (type.equalsIgnoreCase("jdk1.5")))
/*     */     {
/* 282 */       query = "select AM_RESOURCECONFIG.USERNAME as username, " + DBQueryUtil.decode("PASSWORD") + " as password , AM_RESOURCECONFIG.DATABASENAME as instance , InetService.PROTOCOLVERSION as version ,  (CollectData.POLLINTERVAL/60) as pollinterval , CollectData.APPLNDISCPORT as port  from AM_ManagedObject , AM_RESOURCECONFIG , CollectData , InetService  where AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_RESOURCECONFIG.RESOURCEID  and CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.RESOURCENAME=InetService.NAME";
/*     */     }
/* 284 */     else if (type.equalsIgnoreCase("Tomcat"))
/*     */     {
/* 286 */       query = "select AM_RESOURCECONFIG.USERNAME as username, " + DBQueryUtil.decode("PASSWORD") + " as password , AM_RESOURCECONFIG.DATABASENAME as instance , InetService.PROTOCOLVERSION as version ,  (CollectData.POLLINTERVAL/60) as pollinterval , CollectData.APPLNDISCPORT as port ,AM_TOMCATINFO.SSLENABLED as sslenabled  from AM_ManagedObject , AM_RESOURCECONFIG , CollectData , InetService , AM_TOMCATINFO  where AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_RESOURCECONFIG.RESOURCEID  and AM_TOMCATINFO.RESOURCEID = AM_ManagedObject.RESOURCEID and CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.RESOURCENAME=InetService.NAME";
/*     */     }
/* 288 */     else if ((type.equalsIgnoreCase("apache")) || (type.equalsIgnoreCase("iis")))
/*     */     {
/* 290 */       oldquery = "select AM_RESOURCECONFIG.USERNAME as username, " + DBQueryUtil.decode("PASSWORD") + " as password , AM_RESOURCECONFIG.DATABASENAME as instance , InetService.PROTOCOLVERSION as version ,  (CollectData.POLLINTERVAL/60) as pollinterval , CollectData.APPLNDISCPORT as port ,COALESCE(AM_JBOSS_AUTHINFO.sslenabled,'false' ) as sslenabled  from AM_ManagedObject , AM_RESOURCECONFIG , CollectData , InetService left outer join AM_JBOSS_AUTHINFO  on AM_JBOSS_AUTHINFO.RESOURCEID=AM_ManagedObject.RESOURCEID where AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_RESOURCECONFIG.RESOURCEID  and  CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.RESOURCENAME=InetService.NAME";
/*     */       
/*     */ 
/* 293 */       query = "select AM_RESOURCECONFIG.USERNAME as username, " + DBQueryUtil.decode("PASSWORD") + " as password , AM_RESOURCECONFIG.DATABASENAME as instance , InetService.PROTOCOLVERSION as version ,  (CollectData.POLLINTERVAL/60) as pollinterval , CollectData.APPLNDISCPORT as port ,COALESCE(AM_JBOSS_AUTHINFO.sslenabled,'false' ) as sslenabled  from AM_ManagedObject join AM_RESOURCECONFIG on AM_ManagedObject.RESOURCEID=AM_RESOURCECONFIG.RESOURCEID join CollectData on CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME join InetService on  CollectData.RESOURCENAME=InetService.NAME left outer join AM_JBOSS_AUTHINFO  on AM_JBOSS_AUTHINFO.RESOURCEID=AM_ManagedObject.RESOURCEID where AM_ManagedObject.RESOURCEID=" + id;
/*     */       
/*     */ 
/* 296 */       FormatUtil.printQueryChange("AMManagedObjectCloning.java", oldquery, query);
/*     */ 
/*     */     }
/* 299 */     else if (type.equalsIgnoreCase("php"))
/*     */     {
/* 301 */       oldquery = "select AM_RESOURCECONFIG.USERNAME as username, " + DBQueryUtil.decode("PASSWORD") + " as password , AM_RESOURCECONFIG.DATABASENAME as instance , InetService.PROTOCOLVERSION as version ,  (CollectData.POLLINTERVAL/60) as pollinterval , CollectData.APPLNDISCPORT as port , AM_PHPPATH.PATH  as phpPath , COALESCE(AM_JBOSS_AUTHINFO.sslenabled,'false' ) as sslenabled  from AM_ManagedObject , AM_RESOURCECONFIG , CollectData , InetService ,AM_PHPPATH  left outer join AM_JBOSS_AUTHINFO  on AM_JBOSS_AUTHINFO.RESOURCEID=AM_ManagedObject.RESOURCEID where AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_RESOURCECONFIG.RESOURCEID  and  CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.RESOURCENAME=InetService.NAME and AM_PHPPATH.RESOURCEID=AM_ManagedObject.RESOURCEID";
/*     */       
/*     */ 
/* 304 */       query = "select AM_RESOURCECONFIG.USERNAME as username, " + DBQueryUtil.decode("PASSWORD") + " as password , AM_RESOURCECONFIG.DATABASENAME as instance , InetService.PROTOCOLVERSION as version ,  (CollectData.POLLINTERVAL/60) as pollinterval , CollectData.APPLNDISCPORT as port , AM_PHPPATH.PATH  as phpPath , COALESCE(AM_JBOSS_AUTHINFO.sslenabled,'false' ) as sslenabled  from AM_ManagedObject join AM_RESOURCECONFIG on AM_ManagedObject.RESOURCEID=AM_RESOURCECONFIG.RESOURCEID join CollectData on CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME join InetService on CollectData.RESOURCENAME=InetService.NAME join AM_PHPPATH on AM_PHPPATH.RESOURCEID=AM_ManagedObject.RESOURCEID left outer join AM_JBOSS_AUTHINFO  on AM_JBOSS_AUTHINFO.RESOURCEID=AM_ManagedObject.RESOURCEID where AM_ManagedObject.RESOURCEID=" + id;
/*     */       
/*     */ 
/* 307 */       FormatUtil.printQueryChange("AMManagedObjectCloning.java", oldquery, query);
/*     */ 
/*     */     }
/* 310 */     else if (type.equalsIgnoreCase("snmp"))
/*     */     {
/* 312 */       query = "select " + DBQueryUtil.decode("COMMUNITYSTRING") + " as snmpCommunityString  , AM_SNMP_EXT_INFO.TIMEOUT as timeout , AM_SNMP_EXT_INFO.VERSION as snmpversion ,(ManagedObject.POLLINTERVAL/60) as pollinterval , InetService.PORTNO  as port from  InetService, AM_ManagedObject , AM_SNMP_EXT_INFO , ManagedObject  where AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_SNMP_EXT_INFO.RESOURCEID  and InetService.NAME=AM_ManagedObject.RESOURCENAME and ManagedObject.NAME=AM_ManagedObject.RESOURCENAME";
/* 313 */       System.out.println("Query is " + query);
/*     */     }
/* 315 */     else if (type.equalsIgnoreCase("oracledb"))
/*     */     {
/* 317 */       query = "select OracleCollectData.USERNAME as username, " + DBQueryUtil.decode("OracleCollectData.PASSWORD") + " as password , OracleCollectData.INSTANCENAME as instance , (CollectData.POLLINTERVAL/60) as pollinterval , CollectData.APPLNDISCPORT as port from AM_ManagedObject , CollectData , OracleCollectData where AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and AM_ManagedObject.RESOURCENAME=OracleCollectData.RESOURCENAME";
/*     */     }
/* 319 */     else if (type.equalsIgnoreCase("mail"))
/*     */     {
/* 321 */       oldquery = "select AM_MailServerConfig.EMAILID as emailid, AM_MailServerConfig.SUBJECT , AM_MailServerConfig.TESTMESSAGE as mailMsg , (ManagedObject.POLLINTERVAL/60) as pollinterval , AM_SMTPServerConfig.PORT as smtpPort , AM_SMTPServerConfig.USERNAME as smtpUserName , " + DBQueryUtil.decode("AM_SMTPServerConfig.PASSWORD") + " as smtpPassword , COALESCE(AM_POPServerConfig.HOST,'pophost') as popHost , COALESCE(AM_POPServerConfig.PORT,'110') as popPort , COALESCE(AM_POPServerConfig.USERNAME,'user' )as username , COALESCE(" + DBQueryUtil.decode("AM_POPServerConfig.PASSWORD") + " as password , COALESCE(AM_POPServerConfig.ID,'false') as popenabled from AM_MailServerConfig , ManagedObject, AM_ManagedObject , AM_SMTPServerConfig  left outer join AM_POPServerConfig on  AM_POPServerConfig.ID=AM_MailServerConfig.POPID where AM_MailServerConfig.RESID=" + id + " and AM_ManagedObject.RESOURCENAME=ManagedObject.NAME and AM_ManagedObject.RESOURCEID=AM_MailServerConfig.RESID and AM_SMTPServerConfig.ID=AM_MailServerConfig.SMTPID";
/*     */       
/*     */ 
/* 324 */       query = "select AM_MailServerConfig.EMAILID as emailid, AM_MailServerConfig.SUBJECT , AM_MailServerConfig.TESTMESSAGE as mailMsg , (ManagedObject.POLLINTERVAL/60) as pollinterval , AM_SMTPServerConfig.PORT as smtpPort , AM_SMTPServerConfig.USERNAME as smtpUserName , " + DBQueryUtil.decode("AM_SMTPServerConfig.PASSWORD") + " as smtpPassword , COALESCE(AM_POPServerConfig.HOST,'pophost') as popHost , COALESCE(AM_POPServerConfig.PORT,'110') as popPort , COALESCE(AM_POPServerConfig.USERNAME,'user' )as username , COALESCE(" + DBQueryUtil.decode("AM_POPServerConfig.PASSWORD") + " as password , COALESCE(AM_POPServerConfig.ID,'false') as popenabled from AM_MailServerConfig join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_MailServerConfig.RESID join ManagedObject on AM_ManagedObject.RESOURCENAME=ManagedObject.NAME join AM_SMTPServerConfig on AM_SMTPServerConfig.ID=AM_MailServerConfig.SMTPID left outer join AM_POPServerConfig on  AM_POPServerConfig.ID=AM_MailServerConfig.POPID where AM_MailServerConfig.RESID=" + id;
/*     */       
/*     */ 
/*     */ 
/* 328 */       FormatUtil.printQueryChange("AMManagedObjectCloning.java", oldquery, query);
/*     */     }
/* 330 */     if ((type.equalsIgnoreCase("service")) || (type.equalsIgnoreCase("telnet")))
/*     */     {
/* 332 */       query = "select  (CollectData.POLLINTERVAL/60) as pollinterval , CollectData.APPLNDISCPORT as port , AM_PORTCONFIG.COMMAND as command , AM_PORTCONFIG.SEARCH as search  from AM_ManagedObject , AM_RESOURCECONFIG , CollectData , AM_PORTCONFIG  where AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_RESOURCECONFIG.RESOURCEID  and CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and AM_PORTCONFIG.RESOURCEID=AM_ManagedObject.RESOURCEID";
/*     */     }
/* 334 */     if (query != null)
/*     */     {
/* 336 */       Properties toreturn = (Properties)dbutil.getPropertiesList(query).get(0);
/* 337 */       if (DBQueryUtil.isPgsql()) {
/* 338 */         toreturn = handleColumnNames(toreturn);
/*     */       }
/* 340 */       if (type.equalsIgnoreCase("System"))
/*     */       {
/* 342 */         toreturn.setProperty("eventlog_status", eventlog_status);
/*     */       }
/* 344 */       return toreturn;
/*     */     }
/* 346 */     return prop;
/*     */   }
/*     */   
/*     */   private Properties handleColumnNames(Properties prop)
/*     */   {
/* 351 */     Properties toReturn = new Properties();
/*     */     try {
/* 353 */       String[] columnName = { "username", "password", "hostname", "name", "prompt", "pollinterval", "os", "instance", "version", "port", "sslenabled", "timeout", "snmpversion", "command", "search", "popenabled", "phpPath", "snmpCommunityString", "smtpPort", "smtpUserName", "smtpPassword", "popHost", "popPort", "mailMsg" };
/*     */       
/*     */ 
/*     */ 
/* 357 */       Iterator it = prop.keySet().iterator();
/* 358 */       while (it.hasNext())
/*     */       {
/* 360 */         String key = (String)it.next();
/* 361 */         String value = prop.getProperty(key);
/* 362 */         boolean added = false;
/* 363 */         for (int i = 0; i < columnName.length; i++) {
/* 364 */           String column = columnName[i];
/* 365 */           if (key.toLowerCase().equalsIgnoreCase(column.toLowerCase()))
/*     */           {
/* 367 */             toReturn.setProperty(column, value);
/* 368 */             added = true;
/* 369 */             i = columnName.length;
/*     */           }
/*     */         }
/*     */         
/* 373 */         if (!added) {
/* 374 */           toReturn.setProperty(key, value);
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/* 378 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 381 */     return toReturn;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void updateConfigurations(String idtotake, String idtoupdate)
/*     */   {
/* 387 */     String query = "select * from AM_ATTRIBUTETHRESHOLDMAPPER where ID=" + idtotake;
/* 388 */     PreparedStatement ps1 = null;
/* 389 */     PreparedStatement ps2 = null;
/*     */     try
/*     */     {
/* 392 */       ps1 = AMConnectionPool.getConnection().prepareStatement("insert into AM_ATTRIBUTETHRESHOLDMAPPER values ( ? , ? , ?)");
/* 393 */       ps2 = AMConnectionPool.getConnection().prepareStatement("insert into AM_ATTRIBUTEACTIONMAPPER values ( ? , ? , ?, ?)");
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 397 */       e1.printStackTrace();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 402 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 403 */       while (rs.next())
/*     */       {
/* 405 */         ps1.setInt(1, Integer.parseInt(idtoupdate));
/* 406 */         ps1.setInt(2, rs.getInt("ATTRIBUTE"));
/* 407 */         ps1.setInt(3, rs.getInt("THRESHOLDCONFIGURATIONID"));
/* 408 */         AMCacheHandler.setThresholdconfiguration(idtoupdate, rs.getString("ATTRIBUTE"), rs.getString("THRESHOLDCONFIGURATIONID"));
/* 409 */         ps1.addBatch();
/*     */       }
/* 411 */       if (rs != null)
/*     */       {
/* 413 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/* 415 */       query = "select * from AM_ATTRIBUTEACTIONMAPPER where ID=" + idtotake;
/* 416 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/* 417 */       while (rs.next())
/*     */       {
/* 419 */         ps2.setInt(1, Integer.parseInt(idtoupdate));
/* 420 */         ps2.setInt(2, rs.getInt("ATTRIBUTE"));
/* 421 */         ps2.setInt(3, rs.getInt("SEVERITY"));
/* 422 */         ps2.setInt(4, rs.getInt("ACTIONID"));
/* 423 */         ps2.addBatch();
/*     */       }
/*     */       
/* 426 */       if (rs != null)
/*     */       {
/* 428 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/* 430 */       ps1.executeBatch();
/* 431 */       ps2.executeBatch(); return;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 435 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 441 */         ps1.close();
/* 442 */         ps2.close();
/*     */       }
/*     */       catch (Exception e2)
/*     */       {
/* 446 */         e2.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateSNMPCustomAttributes(String id, String newid)
/*     */   {
/* 454 */     String query = "select SCREENID from AM_CAM_SCREEN where CAMID=" + id;
/* 455 */     DataCollectionDBUtil dbutil = new DataCollectionDBUtil();
/* 456 */     ResultSet rs = null;
/* 457 */     String screenid = null;
/*     */     try
/*     */     {
/* 460 */       query = "select " + DBQueryUtil.decode("COMMUNITYSTRING") + " as snmpCommunityString  , AM_SNMP_EXT_INFO.TIMEOUT as timeout ,(ManagedObject.POLLINTERVAL/60) as pollinterval , InetService.PORTNO  as port ,InetService.TARGETNAME as hostname ,InetService.NAME as name from AM_ManagedObject ,AM_SNMP_EXT_INFO , ManagedObject , InetService  where AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_SNMP_EXT_INFO.RESOURCEID  and InetService.NAME=AM_ManagedObject.RESOURCENAME and ManagedObject.NAME=AM_ManagedObject.RESOURCENAME";
/* 461 */       Properties details = (Properties)dbutil.getPropertiesList(query).get(0);
/* 462 */       if (DBQueryUtil.isPgsql()) {
/* 463 */         details = handleColumnNames(details);
/*     */       }
/* 465 */       System.out.println("Details is " + details);
/* 466 */       query = "select SCREENID from AM_CAM_SCREEN where CAMID=" + newid;
/* 467 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/* 468 */       if (rs.next())
/*     */       {
/* 470 */         screenid = rs.getString("SCREENID");
/* 471 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/* 473 */       query = "SELECT AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID, ATTRIBUTENAME,TYPE, AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID,DISPLAYNAME from AM_CAM_DC_ATTRIBUTES, AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID = AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID AND AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID=" + id + " AND (TYPE=0 OR TYPE=1)";
/* 474 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/* 475 */       while (rs.next())
/*     */       {
/* 477 */         CAMServerDBUtil.addSnmpAttributes(Integer.parseInt(newid), Integer.parseInt(screenid), Integer.parseInt(newid), rs.getString("ATTRIBUTENAME"), rs.getString("DISPLAYNAME"), rs.getInt("TYPE"), details.getProperty("hostname"), Integer.parseInt(details.getProperty("port")), "SNMP", details.getProperty("name"));
/*     */       }
/*     */       
/* 480 */       query = "SELECT AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID ATTRIBUTEID, AM_CAM_DC_ATTRIBUTES.DISPLAYNAME , AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME ATTRIBUTENAME, AM_CAM_DC_GROUPS.GROUPID GROUPID, AM_CAM_DC_GROUPS.GROUPNAME GROUPNAME, AM_ManagedObject.RESOURCEID from AM_ManagedObject , AM_ATTRIBUTES,AM_CAM_DC_ATTRIBUTES, AM_CAM_DC_GROUPS WHERE AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_CAM_DC_GROUPS.RESOURCEID and AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID AND AM_ATTRIBUTES.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID AND AM_CAM_DC_ATTRIBUTES.TYPE=5";
/* 481 */       ArrayList tableoids = dbutil.getRows(query);
/* 482 */       if (tableoids.size() != 0)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 488 */         for (int i = 0; i < tableoids.size(); i++)
/*     */         {
/* 490 */           ArrayList tableoid = (ArrayList)tableoids.get(i);
/* 491 */           String tableoidname = (String)tableoid.get(2);
/* 492 */           String tableoiddisplayname = (String)tableoid.get(1);
/* 493 */           String attributeid = (String)tableoid.get(0);
/* 494 */           query = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID ATTRIBUTEID,AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME ATTRIBUTENAME, AM_CAM_DC_ATTRIBUTES.DISPLAYNAME as displayname , AM_CAM_DC_ATTRIBUTES.TYPE as TYPE,AM_ATTRIBUTES.TYPE FOR_THRESHOLD from AM_CAM_DC_ATTRIBUTES,AM_CAM_TABLE_COLUMN_MAPPER,AM_ATTRIBUTES where AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_CAM_TABLE_COLUMN_MAPPER.COLUMNATTRIBUTEID AND AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID AND AM_CAM_TABLE_COLUMN_MAPPER.TABLEATTRIBUTEID=" + attributeid;
/* 495 */           ArrayList columns = dbutil.getRows(query);
/* 496 */           String[] tablecolumn = new String[columns.size()];
/* 497 */           Vector columnnames = new Vector();
/* 498 */           for (int j = 0; j < columns.size(); j++)
/*     */           {
/* 500 */             ArrayList column = (ArrayList)columns.get(j);
/* 501 */             tablecolumn[j] = ((String)column.get(1));
/* 502 */             columnnames.add((String)column.get(2));
/*     */           }
/* 504 */           CAMSNMPUtil.addSnmpTableAttributes(Integer.parseInt(newid), Integer.parseInt(screenid), Integer.parseInt(newid), tableoidname, tableoiddisplayname, details.getProperty("hostname"), Integer.parseInt(details.getProperty("port")), "SNMP", details.getProperty("name"), tablecolumn, columnnames);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 511 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateWMIAttributes(String id, String newhostname, ArrayList output)
/*     */   {
/* 518 */     int newid = -1;
/* 519 */     String attribute = "";
/* 520 */     String instance = "";
/* 521 */     String classname = "";
/* 522 */     String newresourcename = "";
/* 523 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 524 */     String query = "SELECT AM_ManagedObject.RESOURCENAME,AM_ManagedObject.TYPE,AM_ManagedObject.DESCRIPTION,AM_CAM_WMI_EXT_INFO.POLLINTERVAL,AM_SCRIPTHOSTDETAILS.HOSTNAME,AM_CAM_WMI_EXT_INFO.HOSTID,AM_SCRIPTHOSTDETAILS.USERNAME ," + DBQueryUtil.decode("AM_SCRIPTHOSTDETAILS.PASSWORD") + " as PASSWORD from AM_ManagedObject,AM_CAM_WMI_EXT_INFO,AM_SCRIPTHOSTDETAILS where AM_ManagedObject.RESOURCEID=" + id + " and AM_ManagedObject.RESOURCEID=AM_CAM_WMI_EXT_INFO.RESOURCEID and AM_SCRIPTHOSTDETAILS.ID=AM_CAM_WMI_EXT_INFO.HOSTID ";
/*     */     try
/*     */     {
/* 527 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 528 */       if (rs.next())
/*     */       {
/* 530 */         String resourcename = rs.getString("RESOURCENAME");
/* 531 */         String description = rs.getString("DESCRIPTION");
/* 532 */         String type = rs.getString("TYPE");
/* 533 */         int pollinterval = rs.getInt("POLLINTERVAL");
/* 534 */         String host = rs.getString("HOSTNAME");
/* 535 */         String username = rs.getString("USERNAME");
/* 536 */         String password = rs.getString("PASSWORD");
/* 537 */         rs.close();
/* 538 */         Hashtable test = WMIDBUtil.getclasseslist(newhostname, username, password);
/* 539 */         if (test.get("ErrorMsg") != null)
/*     */         {
/* 541 */           ArrayList row = new ArrayList();
/* 542 */           row.add(newhostname);
/* 543 */           row.add("-");
/* 544 */           row.add(FormatUtil.getString("Failed"));
/* 545 */           row.add(FormatUtil.getString((String)test.get("ErrorMsg")));
/* 546 */           output.add(row);
/* 547 */           return;
/*     */         }
/* 549 */         String hostid = WMIDBUtil.checkandaddHost(newhostname, username, password);
/* 550 */         newresourcename = resourcename + "_" + newhostname;
/* 551 */         newid = WMIDBUtil.mocreate(newresourcename, newresourcename, description, type);
/* 552 */         AMConnectionPool.executeUpdateStmt("insert into AM_CAM_WMI_EXT_INFO values (" + newid + "," + pollinterval + "," + hostid + ",'')");
/* 553 */         String query1 = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,AM_CAM_DC_GROUPS.GROUPNAME as CLASSNAME,AM_CAM_GROUP_INSTANCE_MAPPING.INSTANCENAME,AM_CAM_DC_GROUPS.GROUPID,AM_ATTRIBUTES.ATTRIBUTEID as HEALTHID from AM_CAM_DC_GROUPS inner join AM_CAM_GROUP_INSTANCE_MAPPING on AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID=AM_CAM_DC_GROUPS.GROUPID inner join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_CAM_DC_GROUPS.GROUPNAME and AM_ATTRIBUTES.TYPE=2 inner join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER on AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID=AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID where AM_CAM_DC_GROUPS.RESOURCEID=" + id;
/*     */         try
/*     */         {
/* 556 */           AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query1);
/* 557 */           while (rs.next())
/*     */           {
/* 559 */             attribute = rs.getString("ATTRIBUTENAME");
/* 560 */             instance = rs.getString("INSTANCENAME");
/* 561 */             classname = rs.getString("CLASSNAME");
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 567 */             WMIDBUtil.addAttribute(String.valueOf(newid), newhostname, classname, instance, attribute);
/*     */           }
/* 569 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 573 */           ex.printStackTrace();
/*     */         }
/* 575 */         updateConfigurations(id, String.valueOf(newid));
/* 576 */         AMConnectionPool.executeUpdateStmt("insert into AM_RCARULESMAPPER values (" + newid + ",7100,1)");
/* 577 */         AMConnectionPool.executeUpdateStmt("insert into AM_RCAMAPPER values (" + newid + ",7100," + newid + ",7101)");
/*     */         
/*     */ 
/* 580 */         WMIDBUtil.scheduleDatacollection(String.valueOf(newid), newresourcename, "Generic WMI", false);
/* 581 */         ArrayList row = new ArrayList();
/* 582 */         row.add(newhostname);
/* 583 */         row.add("-");
/* 584 */         row.add(FormatUtil.getString("Success"));
/* 585 */         row.add(FormatUtil.getString("Successfully Added"));
/* 586 */         output.add(row);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 591 */       ArrayList row = new ArrayList();
/* 592 */       row.add(newhostname);
/* 593 */       row.add("-");
/* 594 */       row.add(FormatUtil.getString("Failed"));
/* 595 */       row.add(FormatUtil.getString("-"));
/* 596 */       output.add(row);
/* 597 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getType(String type)
/*     */   {
/* 605 */     if (type.equalsIgnoreCase(".Net"))
/*     */     {
/* 607 */       return ".Net";
/*     */     }
/* 609 */     if ((type.equalsIgnoreCase("AIX")) || (type.equalsIgnoreCase("HP-UX")) || ((type.equalsIgnoreCase("FreeBSD") | type.equalsIgnoreCase("Linux"))) || (type.equalsIgnoreCase("SUN")) || (type.equalsIgnoreCase("SUN PC")) || (type.equalsIgnoreCase("Node")) || (type.equalsIgnoreCase("snmp-node")) || (type.toLowerCase().indexOf("windows") != -1))
/*     */     {
/* 611 */       return "SYSTEM";
/*     */     }
/* 613 */     if (type.equalsIgnoreCase("Apache-server"))
/*     */     {
/* 615 */       return "APACHE";
/*     */     }
/* 617 */     if (type.equalsIgnoreCase("DB2-server"))
/*     */     {
/* 619 */       return "DB2";
/*     */     }
/* 621 */     if (type.equalsIgnoreCase("Exchange-server"))
/*     */     {
/* 623 */       return "Exchange";
/*     */     }
/* 625 */     if (type.equalsIgnoreCase("IIS-server"))
/*     */     {
/* 627 */       return "IIS";
/*     */     }
/* 629 */     if (type.equalsIgnoreCase("JBOSS-server"))
/*     */     {
/* 631 */       return "JBoss";
/*     */     }
/* 633 */     if (type.equalsIgnoreCase("JMX1.2-MX4J-RMI"))
/*     */     {
/* 635 */       return "JMX1.2-MX4J-RMI";
/*     */     }
/* 637 */     if (type.equalsIgnoreCase("MAIL-server"))
/*     */     {
/* 639 */       return "MAIL";
/*     */     }
/* 641 */     if (type.equalsIgnoreCase("MSSQL-DB-server"))
/*     */     {
/* 643 */       return "MSSQLDB";
/*     */     }
/* 645 */     if (type.equalsIgnoreCase("SYBASE-DB-server"))
/*     */     {
/* 647 */       return "SYBASEDB";
/*     */     }
/* 649 */     if (type.equalsIgnoreCase("MYSQL-DB-server"))
/*     */     {
/* 651 */       return "MYSQLDB";
/*     */     }
/* 653 */     if (type.equalsIgnoreCase("ORACLE-DB-server"))
/*     */     {
/* 655 */       return "ORACLEDB";
/*     */     }
/* 657 */     if (type.equalsIgnoreCase("PHP"))
/*     */     {
/* 659 */       return "PHP";
/*     */     }
/* 661 */     if (type.equalsIgnoreCase("Port-Test"))
/*     */     {
/* 663 */       return "SERVICE";
/*     */     }
/* 665 */     if (type.equalsIgnoreCase("QENGINE"))
/*     */     {
/* 667 */       return "QENGINE";
/*     */     }
/* 669 */     if (type.equalsIgnoreCase("RMI"))
/*     */     {
/* 671 */       return "RMI";
/*     */     }
/* 673 */     if (type.equalsIgnoreCase("Script Monitor"))
/*     */     {
/* 675 */       return "Script Monitor";
/*     */     }
/* 677 */     if (type.equalsIgnoreCase("SNMP"))
/*     */     {
/* 679 */       return "SNMP";
/*     */     }
/* 681 */     if (type.equalsIgnoreCase("TELNET"))
/*     */     {
/* 683 */       return "TELNET";
/*     */     }
/* 685 */     if (type.equalsIgnoreCase("Tomcat-server"))
/*     */     {
/* 687 */       return "Tomcat";
/*     */     }
/* 689 */     if (type.equalsIgnoreCase("WEB-server"))
/*     */     {
/* 691 */       return "WEB";
/*     */     }
/* 693 */     if (type.equalsIgnoreCase("WEBLOGIC-server"))
/*     */     {
/* 695 */       return "WEBLOGIC";
/*     */     }
/* 697 */     if (type.equalsIgnoreCase("WebSphere-server"))
/*     */     {
/* 699 */       return "WEBSPHERE";
/*     */     }
/* 701 */     if (type.equalsIgnoreCase("WTA"))
/*     */     {
/* 703 */       return "WTA";
/*     */     }
/* 705 */     if (type.equalsIgnoreCase("ORACLE-APP-server"))
/*     */     {
/* 707 */       return "ORACLEAS";
/*     */     }
/* 709 */     if (type.equalsIgnoreCase("WEBLOGIC-Integration"))
/*     */     {
/* 711 */       return "WLI";
/*     */     }
/* 713 */     if (type.equalsIgnoreCase("JDK1.5")) {
/* 714 */       return "JDK1.5";
/*     */     }
/*     */     
/* 717 */     return "Unknown";
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\resourcemanagement\AMManagedObjectCloning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */