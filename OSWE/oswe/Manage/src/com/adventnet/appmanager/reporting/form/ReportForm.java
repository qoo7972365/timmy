/*      */ package com.adventnet.appmanager.reporting.form;
/*      */ 
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ 
/*      */ public class ReportForm extends org.apache.struts.action.ActionForm
/*      */ {
/*   24 */   private static ArrayList moAppServers = null;
/*   25 */   private static ArrayList middleware = null;
/*   26 */   private static ArrayList moDBServers = null;
/*   27 */   private static ArrayList moServices = null;
/*   28 */   private static ArrayList moWebServices = null;
/*   29 */   private static ArrayList moWebServers = null;
/*   30 */   private static ArrayList moUrls = null;
/*   31 */   private static ArrayList moSystems = null;
/*   32 */   private static ArrayList moMailServers = null;
/*   33 */   private static ArrayList moJavaServers = null;
/*   34 */   private static ArrayList moCustomTypes = null;
/*   35 */   private static ArrayList moSapServers = null;
/*   36 */   private ArrayList vmapplications = new ArrayList();
/*   37 */   private static ArrayList moVirtualServers = null;
/*   38 */   private static ArrayList availableMonTypes = null;
/*      */   
/*      */ 
/*   41 */   private static ArrayList moCloudApps = null;
/*   42 */   private static ArrayList moEUMMons = null;
/*      */   
/*      */ 
/*   45 */   public static Hashtable moTypeCount = null;
/*   46 */   private static Hashtable moDisNamCount = null;
/*   47 */   private static Hashtable allservers = new Hashtable();
/*   48 */   private static Hashtable responsetimeids = new Hashtable();
/*   49 */   private static Hashtable cpuids = new Hashtable(7);
/*   50 */   private static Hashtable jdkcpuids = new Hashtable(7);
/*   51 */   private static Hashtable jvmids = new Hashtable(6);
/*   52 */   private static Hashtable memids = new Hashtable(7);
/*   53 */   private static Hashtable jdkmemids = new Hashtable(7);
/*   54 */   private static Hashtable diskids = new Hashtable(7);
/*   55 */   private static Hashtable jdbcids = new Hashtable(6);
/*   56 */   private static Hashtable threadids = new Hashtable(6);
/*   57 */   private static Hashtable sessionids = new Hashtable(6);
/*   58 */   private static Hashtable bufferids = new Hashtable(4);
/*   59 */   private static Hashtable cacheids = new Hashtable(4);
/*   60 */   private static Hashtable throughputids = new Hashtable(6);
/*   61 */   private static Hashtable webappthroughputids = new Hashtable(6);
/*   62 */   private static Hashtable operationrestimeids = new Hashtable(2);
/*   63 */   private static Hashtable connectiontimeids = new Hashtable(2);
/*   64 */   private static Hashtable whHeadings = new Hashtable(10);
/*   65 */   private static Hashtable tempwhHeadings = new Hashtable();
/*      */   
/*   67 */   private static Hashtable sapcpuids = new Hashtable(2);
/*   68 */   private static Hashtable sapmemids = new Hashtable(2);
/*   69 */   private static Hashtable sapdiskids = new Hashtable(2);
/*   70 */   private static Hashtable sapspoolids = new Hashtable(2);
/*   71 */   private static Hashtable sapbackgroundids = new Hashtable(2);
/*   72 */   private static Hashtable sapenqids = new Hashtable(2);
/*   73 */   private static Hashtable sappageinids = new Hashtable(2);
/*   74 */   private static Hashtable sappageoutids = new Hashtable(2);
/*   75 */   private static Hashtable sapdrestimeids = new Hashtable(2);
/*   76 */   private static Hashtable sapccmscontimeids = new Hashtable(2);
/*      */   
/*   78 */   private static Hashtable wlisessionids = new Hashtable(2);
/*   79 */   private static Hashtable wlithreadids = new Hashtable(2);
/*   80 */   private static Hashtable wlijdbcids = new Hashtable(2);
/*   81 */   private static Hashtable wlijvmids = new Hashtable(2);
/*      */   
/*      */   private static String nwd;
/*      */   
/*   85 */   private static Hashtable attributeHeadings = new Hashtable();
/*      */   
/*   87 */   private String reporttype = "html";
/*   88 */   private String report = "true";
/*   89 */   private static String remoteUser = "";
/*      */   
/*      */   private static boolean isUserOpr;
/*      */   
/*   93 */   private ArrayList availMonitorTypes = new ArrayList();
/*   94 */   private String selectedMonitorType = "";
/*   95 */   private ArrayList availAttributes = new ArrayList();
/*   96 */   private String selectedAttribute = "";
/*   97 */   private String[] relatedAttributes = new String[0];
/*   98 */   private String selecttionType = "ALL";
/*   99 */   private ArrayList availMGs = new ArrayList();
/*      */   private String selectedMG;
/*  101 */   private String methodName = "";
/*  102 */   private static String customFormName = null;
/*  103 */   private static HashMap attributeIdMap = new HashMap();
/*  104 */   private static HashMap categoryDataMap = new HashMap();
/*      */   private static String san;
/*      */   
/*  107 */   public ReportForm() { callfirst();
/*  108 */     this.durations = new ArrayList();
/*  109 */     Properties data = new Properties();
/*  110 */     data.setProperty("label", FormatUtil.getString("am.webclient.period.today"));
/*  111 */     data.setProperty("value", "0");
/*  112 */     this.durations.add(data);
/*  113 */     Properties data1 = new Properties();
/*  114 */     data1.setProperty("label", FormatUtil.getString("am.webclient.period.last7days"));
/*  115 */     data1.setProperty("value", "1");
/*  116 */     this.durations.add(data1);
/*  117 */     Properties data2 = new Properties();
/*  118 */     data2.setProperty("label", FormatUtil.getString("am.webclient.period.last30days"));
/*  119 */     data2.setProperty("value", "2");
/*  120 */     this.durations.add(data2);
/*  121 */     Properties data3 = new Properties();
/*  122 */     data3.setProperty("label", FormatUtil.getString("am.webclient.period.yesterday"));
/*  123 */     data3.setProperty("value", "3");
/*  124 */     this.durations.add(data3);
/*  125 */     Properties data4 = new Properties();
/*  126 */     data4.setProperty("label", FormatUtil.getString("am.webclient.period.lastoneyear"));
/*  127 */     data4.setProperty("value", "5");
/*  128 */     this.durations.add(data4);
/*  129 */     Properties data5 = new Properties();
/*  130 */     data5.setProperty("label", FormatUtil.getString("am.webclient.period.thisweek"));
/*  131 */     data5.setProperty("value", "6");
/*  132 */     Properties data6 = new Properties();
/*  133 */     data6.setProperty("label", FormatUtil.getString("am.webclient.period.thismonth"));
/*  134 */     data6.setProperty("value", "7");
/*  135 */     Properties data7 = new Properties();
/*  136 */     data7.setProperty("label", FormatUtil.getString("am.webclient.period.thisyear"));
/*  137 */     data7.setProperty("value", "8");
/*  138 */     Properties data8 = new Properties();
/*  139 */     data8.setProperty("label", FormatUtil.getString("am.webclient.period.thisquarter"));
/*  140 */     data8.setProperty("value", "9");
/*      */   }
/*      */   
/*      */   static {
/*  144 */     callfirst();
/*      */   }
/*      */   
/*      */   public static void callfirst()
/*      */   {
/*  149 */     Properties data = new Properties();
/*      */     
/*      */ 
/*  152 */     initMOTypeCount();
/*  153 */     initComboxBox();
/*  154 */     initialiseHeadings();
/*  155 */     initAttributeHeading();
/*      */   }
/*      */   
/*      */ 
/*      */   private static void initAttributeHeading()
/*      */   {
/*  161 */     attributeHeadings.put("responseTime", FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/*  162 */     attributeHeadings.put("connectionTime", FormatUtil.getString("am.reporttab.connectiontime.text"));
/*  163 */     attributeHeadings.put("connectiontime", FormatUtil.getString("am.reporttab.connectiontime.text"));
/*  164 */     attributeHeadings.put("cpuid", FormatUtil.getString("am.reporttab.shortname.cpuutilisation.text"));
/*  165 */     attributeHeadings.put("jdkcpuid", FormatUtil.getString("am.reporttab.shortname.cpuutilisation.text"));
/*  166 */     attributeHeadings.put("jvm", FormatUtil.getString("am.reporttab.shortname.jvm.text"));
/*  167 */     attributeHeadings.put("mem", FormatUtil.getString("Memory Usage"));
/*  168 */     attributeHeadings.put("memmb", FormatUtil.getString("Memory Usage"));
/*  169 */     attributeHeadings.put("disk", FormatUtil.getString("am.reporttab.shortname.disk.text"));
/*  170 */     attributeHeadings.put("jdbc", FormatUtil.getString("am.reporttab.shortname.connectionpool.text"));
/*  171 */     attributeHeadings.put("thread", FormatUtil.getString("am.reporttab.shortname.thread.text"));
/*  172 */     attributeHeadings.put("session", FormatUtil.getString("am.reporttab.shortname.session.text"));
/*  173 */     attributeHeadings.put("buffer", FormatUtil.getString("am.reporttab.shortname.bufferhitratio.text"));
/*  174 */     attributeHeadings.put("cache", FormatUtil.getString("am.reporttab.shortname.cachehitratio.text"));
/*  175 */     attributeHeadings.put("throughput", FormatUtil.getString("am.reporttab.shortname.requestthroughput.text"));
/*  176 */     attributeHeadings.put("webappthroughput", FormatUtil.getString("am.reporttab.shortname.requestthroughput.text"));
/*  177 */     attributeHeadings.put("operationExecutionTime", FormatUtil.getString("am.reporttab.shortname.operationexecutiontime.text"));
/*      */     
/*  179 */     attributeHeadings.put("sapcpu", FormatUtil.getString("am.reporttab.shortname.cpuutilisation.text"));
/*  180 */     attributeHeadings.put("sapmemory", FormatUtil.getString("am.webclient.memoryutilization.heading"));
/*  181 */     attributeHeadings.put("sapdisk", FormatUtil.getString("am.reporttab.shortname.disk.text"));
/*  182 */     attributeHeadings.put("sappagein", FormatUtil.getString("PAGEIN"));
/*  183 */     attributeHeadings.put("sappageout", FormatUtil.getString("PAGEOUT"));
/*  184 */     attributeHeadings.put("sutilization", FormatUtil.getString("SUTILIZATION"));
/*  185 */     attributeHeadings.put("butilization", FormatUtil.getString("BUTILIZATION"));
/*  186 */     attributeHeadings.put("sapferestime", FormatUtil.getString("FRONTENDRESPONSETIME"));
/*  187 */     attributeHeadings.put("sapenqreq", FormatUtil.getString("ENQUEUEREQUESTS"));
/*  188 */     attributeHeadings.put("sapccmscontime", FormatUtil.getString("Connection Time"));
/*      */     
/*  190 */     attributeHeadings.put("wlithread", FormatUtil.getString("am.reporttab.shortname.thread.text"));
/*  191 */     attributeHeadings.put("wlisession", FormatUtil.getString("am.reporttab.shortname.session.text"));
/*  192 */     attributeHeadings.put("wlijdbc", FormatUtil.getString("am.reporttab.shortname.connectionpool.text"));
/*  193 */     attributeHeadings.put("wlijvm", FormatUtil.getString("am.reporttab.shortname.jvm.text"));
/*      */     
/*  195 */     ArrayList temp = new ArrayList(responsetimeids.values());
/*  196 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  198 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/*      */     }
/*  200 */     temp = new ArrayList(cpuids.values());
/*  201 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  203 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.cpuutilisation.text"));
/*      */     }
/*  205 */     temp = new ArrayList(jdkcpuids.values());
/*  206 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  208 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.cpuutilisation.text"));
/*      */     }
/*  210 */     temp = new ArrayList(jvmids.values());
/*      */     
/*  212 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  214 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.jvm.text"));
/*      */     }
/*  216 */     temp = new ArrayList(memids.values());
/*  217 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  219 */       if (temp.get(i).equals("2717")) {
/*  220 */         attributeHeadings.put(temp.get(i), FormatUtil.getString("am.webclient.as400.asppercentage"));
/*      */       }
/*      */       else {
/*  223 */         attributeHeadings.put(temp.get(i), FormatUtil.getString("Memory Usage"));
/*      */       }
/*      */     }
/*  226 */     temp = new ArrayList(jdkmemids.values());
/*  227 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  229 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("Memory Usage"));
/*      */     }
/*  231 */     temp = new ArrayList(diskids.values());
/*  232 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  234 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.disk.text"));
/*      */     }
/*  236 */     temp = new ArrayList(jdbcids.values());
/*  237 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  239 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.connectionpool.text"));
/*      */     }
/*  241 */     temp = new ArrayList(threadids.values());
/*  242 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  244 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.thread.text"));
/*      */     }
/*  246 */     temp = new ArrayList(sessionids.values());
/*  247 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  249 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.session.text"));
/*      */     }
/*  251 */     temp = new ArrayList(bufferids.values());
/*  252 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  254 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.bufferhitratio.text"));
/*      */     }
/*  256 */     temp = new ArrayList(cacheids.values());
/*  257 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  259 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.cachehitratio.text"));
/*      */     }
/*  261 */     temp = new ArrayList(throughputids.values());
/*  262 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  264 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.requestthroughput.text"));
/*      */     }
/*  266 */     temp = new ArrayList(webappthroughputids.values());
/*  267 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  269 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.webapprequestthroughput.text"));
/*      */     }
/*  271 */     temp = new ArrayList(operationrestimeids.values());
/*  272 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  274 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.operationexecutiontime.text"));
/*      */     }
/*  276 */     temp = new ArrayList(connectiontimeids.values());
/*  277 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  279 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.connectiontime.text"));
/*      */     }
/*      */     
/*      */ 
/*  283 */     temp = new ArrayList(sapcpuids.values());
/*  284 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  286 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.cpuutilisation.text"));
/*      */     }
/*  288 */     temp = new ArrayList(sapmemids.values());
/*  289 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  291 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("ESACT"));
/*      */     }
/*  293 */     temp = new ArrayList(sapdiskids.values());
/*  294 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  296 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("am.reporttab.shortname.disk.text"));
/*      */     }
/*  298 */     temp = new ArrayList(sappageinids.values());
/*  299 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  301 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("PAGEIN"));
/*      */     }
/*  303 */     temp = new ArrayList(sappageoutids.values());
/*  304 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  306 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("PAGEOUT"));
/*      */     }
/*  308 */     temp = new ArrayList(sapbackgroundids.values());
/*  309 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  311 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("BUTILIZATION"));
/*      */     }
/*  313 */     temp = new ArrayList(sapspoolids.values());
/*  314 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  316 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("SUTILIZATION"));
/*      */     }
/*  318 */     temp = new ArrayList(sapdrestimeids.values());
/*  319 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  321 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("FRONTENDRESPONSETIME"));
/*      */     }
/*  323 */     temp = new ArrayList(sapenqids.values());
/*  324 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  326 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("ENQUEUEREQUESTS"));
/*      */     }
/*  328 */     temp = new ArrayList(sapccmscontimeids.values());
/*  329 */     for (int i = 0; i < temp.size(); i++)
/*      */     {
/*  331 */       attributeHeadings.put(temp.get(i), FormatUtil.getString("Connection Time"));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setSelecttionType(String selecttionType)
/*      */   {
/*  339 */     this.selecttionType = selecttionType;
/*      */   }
/*      */   
/*      */   public String getSelecttionType() {
/*  343 */     return this.selecttionType;
/*      */   }
/*      */   
/*      */   public ArrayList getAvailMGs()
/*      */   {
/*  348 */     return this.availMGs;
/*      */   }
/*      */   
/*      */   public void setAvailMGs(ArrayList availMGs) {
/*  352 */     this.availMGs = availMGs;
/*      */   }
/*      */   
/*      */   public String getSelectedMG()
/*      */   {
/*  357 */     return this.selectedMG;
/*      */   }
/*      */   
/*      */   public void setSelectedMG(String selectedMG) {
/*  361 */     this.selectedMG = selectedMG;
/*      */   }
/*      */   
/*      */   public ArrayList getAvailMonitorTypes()
/*      */   {
/*  366 */     return this.availMonitorTypes;
/*      */   }
/*      */   
/*      */   public void setAvailMonitorTypes(ArrayList availMonitorTypes) {
/*  370 */     this.availMonitorTypes = availMonitorTypes;
/*      */   }
/*      */   
/*      */   public String getSelectedMonitorType()
/*      */   {
/*  375 */     return this.selectedMonitorType;
/*      */   }
/*      */   
/*      */   public void setSelectedMonitorType(String selectedMonitorType) {
/*  379 */     this.selectedMonitorType = selectedMonitorType;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getAvailAttributes()
/*      */   {
/*  385 */     return this.availAttributes;
/*      */   }
/*      */   
/*      */   public void setAvailAttributes(ArrayList availAttributes) {
/*  389 */     this.availAttributes = availAttributes;
/*      */   }
/*      */   
/*      */   public String getSelectedAttribute()
/*      */   {
/*  394 */     return this.selectedAttribute;
/*      */   }
/*      */   
/*      */   public void setSelectedAttribute(String selectedAttribute) {
/*  398 */     this.selectedAttribute = selectedAttribute;
/*      */   }
/*      */   
/*      */   public String[] getRelatedAttributes()
/*      */   {
/*  403 */     return this.relatedAttributes;
/*      */   }
/*      */   
/*      */ 
/*  407 */   public void setRelatedAttributes(String[] relatedAttributes) { this.relatedAttributes = relatedAttributes; }
/*      */   
/*      */   private static void initComboxBox() {
/*      */     try {
/*  411 */       HashMap servermap = ReportUtil.getServerMap();
/*  412 */       attributeIdMap = ReportUtil.getAttributeIdMap();
/*      */       
/*  414 */       if ((servermap == null) || (servermap.size() <= 0))
/*      */       {
/*      */ 
/*  417 */         ReportUtil.loadAllReportAttIds();
/*  418 */         servermap = ReportUtil.getServerMap();
/*  419 */         attributeIdMap = ReportUtil.getAttributeIdMap();
/*      */       }
/*  421 */       if (servermap != null) {
/*  422 */         moAppServers = (ArrayList)servermap.get("moAppServers");
/*  423 */         categoryDataMap.put("APP", moAppServers);
/*  424 */         remove_categorytype(moAppServers);
/*  425 */         moDBServers = (ArrayList)servermap.get("moDBServers");
/*  426 */         categoryDataMap.put("DBS", moDBServers);
/*  427 */         remove_categorytype(moDBServers);
/*  428 */         moServices = (ArrayList)servermap.get("moServices");
/*  429 */         categoryDataMap.put("SER", moServices);
/*  430 */         remove_categorytype(moServices);
/*  431 */         middleware = (ArrayList)servermap.get("middleware");
/*  432 */         categoryDataMap.put("MOM", middleware);
/*  433 */         remove_categorytype(middleware);
/*  434 */         moMailServers = (ArrayList)servermap.get("moMailServers");
/*  435 */         categoryDataMap.put("MS", moMailServers);
/*  436 */         remove_categorytype(moMailServers);
/*  437 */         moWebServices = (ArrayList)servermap.get("moWebServices");
/*  438 */         categoryDataMap.put("URL", moWebServices);
/*  439 */         remove_categorytype(moWebServices);
/*  440 */         moWebServers = (ArrayList)servermap.get("moWebServers");
/*  441 */         remove_categorytype(moWebServers);
/*  442 */         moUrls = (ArrayList)servermap.get("moUrls");
/*  443 */         remove_categorytype(moUrls);
/*  444 */         moSystems = (ArrayList)servermap.get("moSystems");
/*  445 */         categoryDataMap.put("SYS", moSystems);
/*  446 */         remove_categorytype(moSystems);
/*  447 */         moJavaServers = (ArrayList)servermap.get("moJavaServers");
/*  448 */         categoryDataMap.put("TM", moJavaServers);
/*  449 */         remove_categorytype(moJavaServers);
/*  450 */         moSapServers = (ArrayList)servermap.get("moSapServers");
/*  451 */         categoryDataMap.put("ERP", moSapServers);
/*  452 */         remove_categorytype(moSapServers);
/*  453 */         moVirtualServers = (ArrayList)servermap.get("moVirtualServers");
/*  454 */         categoryDataMap.put("VIR", moVirtualServers);
/*  455 */         remove_categorytype(moVirtualServers);
/*  456 */         moCloudApps = (ArrayList)servermap.get("moCloudApps");
/*  457 */         categoryDataMap.put("CLD", moCloudApps);
/*  458 */         remove_categorytype(moCloudApps);
/*  459 */         moEUMMons = (ArrayList)servermap.get("moEumMonitors");
/*  460 */         tempwhHeadings = ReportUtil.getTempwhHeadings();
/*  461 */         ArrayList moCustomTypesDetails = ReportUtil.getMONewCustomTypes();
/*  462 */         ArrayList customMonResGroups = (ArrayList)moCustomTypesDetails.get(0);
/*  463 */         HashMap custMonitormap = (HashMap)moCustomTypesDetails.get(1);
/*  464 */         moCustomTypes = (ArrayList)moCustomTypesDetails.get(2);
/*  465 */         categoryDataMap.put("CAM", moCustomTypes);
/*  466 */         if ((customMonResGroups != null) && (customMonResGroups.size() > 0)) {
/*  467 */           remove_customFromResGroup(customMonResGroups, custMonitormap);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  472 */         com.adventnet.appmanager.logging.AMLog.debug("ReportForm servermap is null");
/*      */       }
/*  474 */       if (Constants.isIt360) {
/*  475 */         getNWDSubGroups();
/*      */         
/*  477 */         getSANSubGroups();
/*      */       }
/*      */       
/*  480 */       if (attributeIdMap != null) {
/*  481 */         responsetimeids = (Hashtable)attributeIdMap.get("responsetimeids");
/*  482 */         cpuids = (Hashtable)attributeIdMap.get("cpuids");
/*  483 */         jdkcpuids = (Hashtable)attributeIdMap.get("jdkcpuids");
/*  484 */         jvmids = (Hashtable)attributeIdMap.get("jvmids");
/*  485 */         memids = (Hashtable)attributeIdMap.get("memids");
/*  486 */         jdkmemids = (Hashtable)attributeIdMap.get("jdkmemids");
/*  487 */         diskids = (Hashtable)attributeIdMap.get("diskids");
/*  488 */         jdbcids = (Hashtable)attributeIdMap.get("jdbcids");
/*  489 */         threadids = (Hashtable)attributeIdMap.get("threadids");
/*  490 */         sessionids = (Hashtable)attributeIdMap.get("sessionids");
/*  491 */         bufferids = (Hashtable)attributeIdMap.get("bufferids");
/*  492 */         cacheids = (Hashtable)attributeIdMap.get("cacheids");
/*  493 */         throughputids = (Hashtable)attributeIdMap.get("throughputids");
/*  494 */         webappthroughputids = (Hashtable)attributeIdMap.get("webappthroughputids");
/*  495 */         operationrestimeids = (Hashtable)attributeIdMap.get("operationrestimeids");
/*  496 */         connectiontimeids = (Hashtable)attributeIdMap.get("connectiontimeids");
/*  497 */         sapcpuids = (Hashtable)attributeIdMap.get("sapcpuids");
/*  498 */         sapmemids = (Hashtable)attributeIdMap.get("sapmemids");
/*  499 */         sapdiskids = (Hashtable)attributeIdMap.get("sapdiskids");
/*  500 */         sapspoolids = (Hashtable)attributeIdMap.get("sapspoolids");
/*  501 */         sapbackgroundids = (Hashtable)attributeIdMap.get("sapbackgroundids");
/*  502 */         sapenqids = (Hashtable)attributeIdMap.get("sapenqids");
/*  503 */         sappageinids = (Hashtable)attributeIdMap.get("sappageinids");
/*  504 */         sappageoutids = (Hashtable)attributeIdMap.get("sappageoutids");
/*  505 */         sapdrestimeids = (Hashtable)attributeIdMap.get("sapdrestimeids");
/*  506 */         sapccmscontimeids = (Hashtable)attributeIdMap.get("sapccmscontimeids");
/*  507 */         wlisessionids = (Hashtable)attributeIdMap.get("wlisessionids");
/*  508 */         wlithreadids = (Hashtable)attributeIdMap.get("wlithreadids");
/*  509 */         wlijdbcids = (Hashtable)attributeIdMap.get("wlijdbcids");
/*  510 */         wlijvmids = (Hashtable)attributeIdMap.get("wlijvmids");
/*      */       }
/*      */       else
/*      */       {
/*  514 */         com.adventnet.appmanager.logging.AMLog.debug("ReportForm attributeIdMap is null");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  518 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void getSANSubGroups()
/*      */   {
/*  530 */     ResultSet subGrpRs = null;
/*  531 */     ArrayList<String> subGrpList = new ArrayList();
/*      */     try
/*      */     {
/*  534 */       san = new String();
/*  535 */       subGrpRs = AMConnectionPool.executeQueryStmt("select DISTINCT SUBGROUP from AM_ManagedResourceType where RESOURCEGROUP='SAN' and SUBGROUP like 'OpStor-%'");
/*  536 */       while (subGrpRs.next())
/*      */       {
/*  538 */         subGrpList.add(subGrpRs.getString(1));
/*      */       }
/*  540 */       if (subGrpList.size() > 0)
/*      */       {
/*  542 */         for (int i = 0; i < subGrpList.size(); i++)
/*      */         {
/*  544 */           if (i != subGrpList.size() - 1)
/*      */           {
/*  546 */             san = san + (String)subGrpList.get(i) + "','";
/*      */           }
/*      */           else
/*      */           {
/*  550 */             san += (String)subGrpList.get(i);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  557 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  561 */       AMConnectionPool.closeStatement(subGrpRs);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getSan() {
/*  566 */     return san;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void getNWDSubGroups()
/*      */   {
/*  576 */     ResultSet subGrpRs = null;
/*  577 */     ArrayList<String> subGrpList = new ArrayList();
/*      */     try
/*      */     {
/*  580 */       nwd = new String();
/*  581 */       subGrpRs = AMConnectionPool.executeQueryStmt("select DISTINCT SUBGROUP from AM_ManagedResourceType where RESOURCEGROUP='NWD' and SUBGROUP like 'OpManager-%'");
/*  582 */       while (subGrpRs.next())
/*      */       {
/*  584 */         subGrpList.add(subGrpRs.getString(1));
/*      */       }
/*  586 */       if (subGrpList.size() > 0)
/*      */       {
/*  588 */         for (int i = 0; i < subGrpList.size(); i++)
/*      */         {
/*  590 */           if (i != subGrpList.size() - 1)
/*      */           {
/*  592 */             nwd = nwd + (String)subGrpList.get(i) + "','";
/*      */           }
/*      */           else
/*      */           {
/*  596 */             nwd += (String)subGrpList.get(i);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  603 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  607 */       AMConnectionPool.closeStatement(subGrpRs);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getNwd() {
/*  612 */     return nwd;
/*      */   }
/*      */   
/*      */ 
/*      */   private static void initMOTypeCount()
/*      */   {
/*  618 */     Hashtable getMOTypeCount = ReportUtilities.getMOTypeCount(isUserOpr, remoteUser);
/*  619 */     moTypeCount = new Hashtable();
/*  620 */     moDisNamCount = new Hashtable();
/*  621 */     moTypeCount = (Hashtable)getMOTypeCount.get("moTypeCount");
/*  622 */     moDisNamCount = (Hashtable)getMOTypeCount.get("moDisNamCount");
/*      */   }
/*      */   
/*      */   public ArrayList getCustomTypes() {
/*  626 */     return moCustomTypes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getSystems()
/*      */   {
/*  635 */     ArrayList retmoSystems = new ArrayList();
/*  636 */     Properties prop = (Properties)moSystems.get(0);
/*  637 */     retmoSystems.add(prop);
/*  638 */     for (int i = 1; i < moSystems.size(); i++)
/*      */     {
/*      */ 
/*  641 */       prop = (Properties)moSystems.get(i);
/*  642 */       if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */       {
/*  644 */         retmoSystems.add(prop);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  650 */     return retmoSystems;
/*      */   }
/*      */   
/*  653 */   private String customservice = null;
/*      */   
/*      */   public String getCustomservice()
/*      */   {
/*  657 */     return this.customservice;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setCustomservice(String customservice)
/*      */   {
/*  663 */     this.customservice = customservice;
/*      */   }
/*      */   
/*  666 */   private String customTypeAttrib = null;
/*      */   private ArrayList customserviceAttrib;
/*      */   
/*  669 */   public String getCustomTypeAttrib() { return this.customTypeAttrib; }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setCustomTypeAttrib(String customTypeAttrib)
/*      */   {
/*  675 */     this.customTypeAttrib = customTypeAttrib;
/*      */   }
/*      */   
/*      */   public void setCustomserviceAttrib(ArrayList attribs)
/*      */   {
/*  680 */     this.customserviceAttrib = attribs;
/*      */   }
/*      */   
/*      */ 
/*  684 */   public ArrayList getCustomserviceAttrib() { return this.customserviceAttrib; }
/*      */   
/*      */   private static void remove_customFromResGroup(ArrayList customMonResGroups, HashMap custMonitormap) {
/*  687 */     Iterator itr = customMonResGroups.iterator();
/*  688 */     while (itr.hasNext()) {
/*  689 */       String category = (String)itr.next();
/*  690 */       ArrayList moListofcategory = (ArrayList)categoryDataMap.get(category);
/*  691 */       if (moListofcategory != null)
/*      */       {
/*  693 */         ArrayList CustMonitorsofCategory = (ArrayList)custMonitormap.get(category);
/*  694 */         Iterator it = CustMonitorsofCategory.iterator();
/*  695 */         ArrayList propList = new ArrayList();
/*  696 */         while (it.hasNext()) {
/*  697 */           String monitorName = (String)it.next();
/*  698 */           if ((moListofcategory != null) && (!moListofcategory.isEmpty()))
/*      */           {
/*  700 */             Iterator listit = moListofcategory.iterator();
/*  701 */             while (listit.hasNext()) {
/*  702 */               Properties prop = (Properties)listit.next();
/*  703 */               if ((prop.containsValue(monitorName)) && (prop.getProperty("value").equals(monitorName))) {
/*  704 */                 propList.add(prop);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  711 */         String whHeadingsKey = null;
/*  712 */         Hashtable allServers = ReportUtil.getserversTypes();
/*  713 */         if (allServers != null) {
/*  714 */           if (category.equals("APP")) {
/*  715 */             whHeadingsKey = (String)allServers.get("Application servers");
/*  716 */           } else if (category.equals("DBS")) {
/*  717 */             whHeadingsKey = (String)allServers.get("Database Servers");
/*  718 */           } else if (category.equals("SER")) {
/*  719 */             whHeadingsKey = (String)allServers.get("Services");
/*  720 */           } else if (category.equals("MOM")) {
/*  721 */             whHeadingsKey = (String)allServers.get("Middleware Servers");
/*  722 */           } else if (category.equals("MS")) {
/*  723 */             whHeadingsKey = (String)allServers.get("Mail Servers");
/*  724 */           } else if (category.equals("URL")) {
/*  725 */             whHeadingsKey = (String)allServers.get("Web Services");
/*  726 */           } else if (category.equals("SYS")) {
/*  727 */             whHeadingsKey = (String)allServers.get("Servers");
/*  728 */           } else if (category.equals("TM")) {
/*  729 */             whHeadingsKey = (String)allServers.get("Java-Transactions");
/*  730 */           } else if (category.equals("ERP")) {
/*  731 */             whHeadingsKey = (String)allServers.get("ERP Servers");
/*  732 */           } else if (category.equals("VIR")) {
/*  733 */             whHeadingsKey = (String)allServers.get("Virtualization");
/*  734 */           } else if (category.equals("CLD")) {
/*  735 */             whHeadingsKey = (String)allServers.get("Cloud Apps");
/*      */           }
/*  737 */           String allType = remove_customFromHeading(whHeadingsKey, propList, moListofcategory);
/*  738 */           Iterator iterateProps = propList.iterator();
/*  739 */           while (iterateProps.hasNext()) {
/*  740 */             Properties prop = (Properties)iterateProps.next();
/*  741 */             moListofcategory.remove(prop);
/*      */           }
/*      */           
/*  744 */           if (allType != null) {
/*  745 */             Properties prop = new Properties();
/*  746 */             prop.setProperty("label", FormatUtil.getString("am.monitortab.all.text"));
/*  747 */             prop.setProperty("value", allType);
/*  748 */             if (moListofcategory.size() > 0) {
/*  749 */               moListofcategory.remove(0);
/*      */             }
/*  751 */             moListofcategory.add(0, prop);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private ArrayList applications;
/*      */   private ArrayList businessrules;
/*      */   private static String remove_customFromHeading(String whHeadingsKey, ArrayList propList, ArrayList moListofcategory)
/*      */   {
/*  763 */     String allType = null;
/*      */     try {
/*  765 */       if ((whHeadingsKey != null) && (tempwhHeadings.get(whHeadingsKey) != null)) {
/*  766 */         String whHeadingsValue = (String)tempwhHeadings.get(whHeadingsKey);
/*  767 */         Iterator iterateProps = propList.iterator();
/*  768 */         tempwhHeadings.remove(whHeadingsKey);
/*  769 */         while (iterateProps.hasNext()) {
/*  770 */           String tempKey = whHeadingsKey;
/*  771 */           Properties prop = (Properties)iterateProps.next();
/*  772 */           String custom_MonName = prop.getProperty("value");
/*      */           
/*      */ 
/*  775 */           if (whHeadingsKey.indexOf(custom_MonName) != -1) {
/*  776 */             if (whHeadingsKey.startsWith(custom_MonName)) {
/*  777 */               tempKey = tempKey.substring(custom_MonName.length() + 3, whHeadingsKey.length());
/*      */             }
/*  779 */             else if (whHeadingsKey.endsWith(custom_MonName)) {
/*  780 */               tempKey = tempKey.substring(0, whHeadingsKey.indexOf(custom_MonName) - 3);
/*      */             }
/*      */             else {
/*  783 */               tempKey = tempKey.substring(0, whHeadingsKey.indexOf(custom_MonName) - 3);
/*  784 */               tempKey = tempKey + whHeadingsKey.substring(tempKey.length() + custom_MonName.length() + 3, whHeadingsKey.length());
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  790 */           whHeadingsKey = tempKey;
/*      */         }
/*  792 */         tempwhHeadings.put(whHeadingsKey, whHeadingsValue);
/*  793 */         allType = whHeadingsKey;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  798 */       e.printStackTrace();
/*      */     }
/*  800 */     return allType;
/*      */   }
/*      */   
/*      */ 
/*      */   private static void remove_categorytype(ArrayList rows)
/*      */   {
/*      */     try
/*      */     {
/*  808 */       if (rows != null) {
/*  809 */         int[] temp = new int[rows.size()];
/*  810 */         for (int i = 0; i < temp.length; i++)
/*      */         {
/*  812 */           temp[i] = 0;
/*      */         }
/*      */         
/*  815 */         for (int i = 0; i < rows.size(); i++)
/*      */         {
/*  817 */           Properties row = (Properties)rows.get(i);
/*  818 */           for (int j = 0; j < Constants.resourceTypes_array.length; j++)
/*      */           {
/*  820 */             if ((row != null) && (Constants.resourceTypes_array[j] != null) && (row.containsValue(Constants.resourceTypes_array[j]))) {
/*      */               break;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  826 */             if (j == Constants.resourceTypes_array.length - 1)
/*      */             {
/*  828 */               temp[i] = 1;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  835 */         int i = 1; for (int j = 1; i < temp.length; i++)
/*      */         {
/*  837 */           if (temp[i] == 1)
/*      */           {
/*  839 */             rows.remove(j);
/*  840 */             j--;
/*      */           }
/*  842 */           j++;
/*      */         }
/*  844 */         if (((rows.size() == 2) || (rows.size() == 1)) && (((String)((Properties)rows.get(0)).get("label")).equals("All")))
/*      */         {
/*  846 */           rows.remove(0);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  853 */       exc.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setApplications(ArrayList app)
/*      */   {
/*  861 */     this.applications = app;
/*      */   }
/*      */   
/*      */   public ArrayList getApplications() {
/*  865 */     return this.applications;
/*      */   }
/*      */   
/*      */   public void setBusinessrules(ArrayList app)
/*      */   {
/*  870 */     this.businessrules = app;
/*      */   }
/*      */   
/*      */   public ArrayList getBusinessrules() {
/*  874 */     return this.businessrules;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setMonitors(ArrayList mon)
/*      */   {
/*  880 */     this.monitors = mon;
/*      */   }
/*      */   
/*      */   public ArrayList getMonitors() {
/*  884 */     return this.monitors;
/*      */   }
/*      */   
/*      */ 
/*      */   private ArrayList monitors;
/*      */   private ArrayList durations;
/*      */   private String haid;
/*      */   public void setDurations(ArrayList dura)
/*      */   {
/*  893 */     this.durations = dura;
/*      */   }
/*      */   
/*      */   public ArrayList getDurations() {
/*  897 */     return this.durations;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHaid()
/*      */   {
/*  905 */     return this.haid;
/*      */   }
/*      */   
/*      */   public void setHaid(String haid) {
/*  909 */     this.haid = haid;
/*      */   }
/*      */   
/*      */ 
/*  913 */   private String resid = "on";
/*      */   private String resourceType;
/*      */   
/*      */   public String getResid() {
/*  917 */     try { if (!this.resid.equals("on")) {
/*  918 */         Integer.parseInt(this.resid);
/*      */       }
/*      */     } catch (Exception e) {
/*  921 */       this.resid = "on";
/*  922 */       e.printStackTrace();
/*      */     }
/*  924 */     return this.resid;
/*      */   }
/*      */   
/*      */   public void setResid(String resid) {
/*  928 */     this.resid = resid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getResourceType()
/*      */   {
/*  937 */     return this.resourceType;
/*      */   }
/*      */   
/*      */   public void setResourceType(String type) {
/*  941 */     this.resourceType = type;
/*      */   }
/*      */   
/*      */ 
/*  945 */   private String period = "0";
/*      */   
/*      */   public void setPeriod(String period) {
/*  948 */     this.period = period;
/*      */   }
/*      */   
/*      */   public String getPeriod() {
/*  952 */     return this.period;
/*      */   }
/*      */   
/*  955 */   private int year = 0;
/*      */   
/*      */   public void setYear(int year) {
/*  958 */     this.year = year;
/*      */   }
/*      */   
/*      */   public int getYear() {
/*  962 */     return this.year;
/*      */   }
/*      */   
/*  965 */   private String month = null;
/*      */   
/*      */   public void setMonth(String month) {
/*  968 */     this.month = month;
/*      */   }
/*      */   
/*      */   public String getMonth() {
/*  972 */     return this.month;
/*      */   }
/*      */   
/*  975 */   private int fromYear = 0;
/*      */   
/*      */   public void setFromYear(int fromYear) {
/*  978 */     this.fromYear = fromYear;
/*      */   }
/*      */   
/*      */   public int getFromYear() {
/*  982 */     return this.fromYear;
/*      */   }
/*      */   
/*  985 */   private String fromMonth = null;
/*      */   
/*      */   public void setFromMonth(String fromMonth) {
/*  988 */     this.fromMonth = fromMonth;
/*      */   }
/*      */   
/*      */   public String getFromMonth() {
/*  992 */     return this.fromMonth;
/*      */   }
/*      */   
/*  995 */   private int toYear = 0;
/*      */   
/*      */   public void setToYear(int toYear) {
/*  998 */     this.toYear = toYear;
/*      */   }
/*      */   
/*      */   public int getToYear() {
/* 1002 */     return this.toYear;
/*      */   }
/*      */   
/* 1005 */   private String toMonth = null;
/*      */   
/*      */   public void setToMonth(String toMonth) {
/* 1008 */     this.toMonth = toMonth;
/*      */   }
/*      */   
/*      */   public String getToMonth() {
/* 1012 */     return this.toMonth;
/*      */   }
/*      */   
/* 1015 */   private String licDuration = null;
/*      */   
/*      */   public void setLicDuration(String licDuration) {
/* 1018 */     this.licDuration = licDuration;
/*      */   }
/*      */   
/*      */   public String getLicDuration() {
/* 1022 */     return this.licDuration;
/*      */   }
/*      */   
/* 1025 */   private String businessperiod = "oni";
/*      */   private String interval;
/*      */   
/* 1028 */   public void setBusinessPeriod(String businessperiod) { this.businessperiod = businessperiod; }
/*      */   
/*      */   public String getBusinessPeriod()
/*      */   {
/* 1032 */     return this.businessperiod;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setInterval(String interval)
/*      */   {
/* 1038 */     this.interval = interval;
/*      */   }
/*      */   
/*      */   public String getInterval() {
/* 1042 */     return this.interval;
/*      */   }
/*      */   
/*      */   public Hashtable getMonitorDisplayNames()
/*      */   {
/* 1047 */     Hashtable table = new Hashtable();
/* 1048 */     ArrayList list = getMonitors();
/* 1049 */     table.put("on", "-----" + FormatUtil.getString("am.reporttab.selectmonitor.text") + "------");
/* 1050 */     for (int i = 0; i < list.size(); i++) {
/* 1051 */       Properties props = (Properties)list.get(i);
/* 1052 */       table.put(props.get("value"), props.get("label"));
/*      */     }
/*      */     
/*      */ 
/* 1056 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1063 */   private ArrayList businessRuleNames = null;
/*      */   
/* 1065 */   public ArrayList getBusinessRuleNames() { ArrayList table = new ArrayList();
/* 1066 */     ArrayList list = getBusinessrules();
/* 1067 */     Properties dataProps1 = new Properties();
/* 1068 */     dataProps1.setProperty("label", "-----" + FormatUtil.getString("am.webclient.businesshours.select.default") + "------");
/* 1069 */     dataProps1.setProperty("value", "oni");
/* 1070 */     table.add(dataProps1);
/* 1071 */     for (int i = 0; i < list.size(); i++) {
/* 1072 */       Properties props = (Properties)list.get(i);
/* 1073 */       table.add(props);
/*      */     }
/*      */     
/*      */ 
/* 1077 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setBusinessRuleNames(ArrayList t)
/*      */   {
/* 1083 */     this.businessRuleNames = t;
/*      */   }
/*      */   
/*      */   public void setStarttime(String stTime)
/*      */   {
/* 1088 */     this.startTime = stTime;
/*      */   }
/*      */   
/*      */   public String getStarttime() {
/* 1092 */     return this.startTime;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setEndtime(String endTime)
/*      */   {
/* 1099 */     this.endTime = endTime;
/*      */   }
/*      */   
/*      */   public String getEndtime() {
/* 1103 */     return this.endTime;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setActionMethod(String method)
/*      */   {
/* 1110 */     this.actionMethod = method;
/*      */   }
/*      */   
/*      */   public String getActionMethod() {
/* 1114 */     return this.actionMethod;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setResourceTypes(ArrayList apps)
/*      */   {
/* 1120 */     this.resourceTypes = apps;
/*      */   }
/*      */   
/*      */   public ArrayList getResourceTypes() {
/* 1124 */     return this.resourceTypes;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setAttribute(String arg)
/*      */   {
/* 1130 */     this.attribute = arg;
/*      */   }
/*      */   
/*      */   public String getAttribute() {
/* 1134 */     return this.attribute;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setResourceid(String arg)
/*      */   {
/* 1140 */     this.resourceid = arg;
/*      */   }
/*      */   
/*      */   public String getResourceid() {
/* 1144 */     return this.resourceid;
/*      */   }
/*      */   
/*      */   public void setAvailableMonTypes(ArrayList availMon)
/*      */   {
/* 1149 */     availableMonTypes = availMon;
/*      */   }
/*      */   
/*      */   public ArrayList getAvailableMonTypes()
/*      */   {
/* 1154 */     return availableMonTypes;
/*      */   }
/*      */   
/*      */   public void setAppServers(ArrayList appservers)
/*      */   {
/* 1159 */     moAppServers = appservers;
/*      */   }
/*      */   
/*      */   public ArrayList getAppServers() {
/* 1163 */     ArrayList retAppServers = new ArrayList();
/* 1164 */     Properties prop = (Properties)moAppServers.get(0);
/* 1165 */     retAppServers.add(prop);
/* 1166 */     for (int i = 1; i < moAppServers.size(); i++)
/*      */     {
/*      */ 
/* 1169 */       prop = (Properties)moAppServers.get(i);
/* 1170 */       if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */       {
/* 1172 */         retAppServers.add(prop);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1178 */     return retAppServers;
/*      */   }
/*      */   
/*      */   public void setMiddleware(ArrayList middleware)
/*      */   {
/* 1183 */     middleware = middleware;
/*      */   }
/*      */   
/*      */   public ArrayList getMiddleware() {
/* 1187 */     ArrayList retmiddleware = new ArrayList();
/* 1188 */     if ((middleware != null) && (middleware.size() > 0))
/*      */     {
/* 1190 */       Properties prop = (Properties)middleware.get(0);
/* 1191 */       retmiddleware.add(prop);
/* 1192 */       for (int i = 1; i < middleware.size(); i++)
/*      */       {
/* 1194 */         prop = (Properties)middleware.get(i);
/* 1195 */         if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */         {
/* 1197 */           retmiddleware.add(prop);
/*      */         }
/*      */       }
/*      */     }
/* 1201 */     return retmiddleware;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList getDbServers()
/*      */   {
/* 1208 */     ArrayList retDBServers = new ArrayList();
/* 1209 */     Properties prop = (Properties)moDBServers.get(0);
/* 1210 */     retDBServers.add(prop);
/* 1211 */     for (int i = 1; i < moDBServers.size(); i++)
/*      */     {
/*      */ 
/* 1214 */       prop = (Properties)moDBServers.get(i);
/* 1215 */       if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */       {
/* 1217 */         retDBServers.add(prop);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1223 */     return retDBServers;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getServices()
/*      */   {
/* 1229 */     ArrayList retServices = new ArrayList();
/* 1230 */     Properties prop = (Properties)moServices.get(0);
/* 1231 */     retServices.add(prop);
/* 1232 */     for (int i = 1; i < moServices.size(); i++)
/*      */     {
/*      */ 
/* 1235 */       prop = (Properties)moServices.get(i);
/* 1236 */       if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */       {
/* 1238 */         retServices.add(prop);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1244 */     return retServices;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getWebservices()
/*      */   {
/* 1250 */     return moWebServices;
/*      */   }
/*      */   
/*      */   public ArrayList getWebservers() {
/* 1254 */     ArrayList retWebServers = new ArrayList();
/* 1255 */     Properties prop = (Properties)moWebServers.get(0);
/* 1256 */     retWebServers.add(prop);
/* 1257 */     for (int i = 1; i < moWebServers.size(); i++)
/*      */     {
/*      */ 
/* 1260 */       prop = (Properties)moWebServers.get(i);
/* 1261 */       if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */       {
/* 1263 */         retWebServers.add(prop);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1269 */     return retWebServers;
/*      */   }
/*      */   
/*      */   public ArrayList getUrls()
/*      */   {
/* 1274 */     ArrayList retUrls = new ArrayList();
/* 1275 */     Properties prop = (Properties)moUrls.get(0);
/* 1276 */     retUrls.add(prop);
/* 1277 */     for (int i = 1; i < moUrls.size(); i++)
/*      */     {
/*      */ 
/* 1280 */       prop = (Properties)moUrls.get(i);
/* 1281 */       if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */       {
/* 1283 */         retUrls.add(prop);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1289 */     return retUrls;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String startTime;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String endTime;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String actionMethod;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private ArrayList resourceTypes;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String attribute;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String resourceid;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1328 */   private ArrayList Mailservers = null;
/*      */   
/*      */   public ArrayList getMailservers() {
/* 1331 */     ArrayList retMailServers = new ArrayList();
/* 1332 */     if ((moMailServers != null) && (moMailServers.size() > 0))
/*      */     {
/* 1334 */       Properties prop = (Properties)moMailServers.get(0);
/* 1335 */       retMailServers.add(prop);
/* 1336 */       for (int i = 1; i < moMailServers.size(); i++)
/*      */       {
/* 1338 */         prop = (Properties)moMailServers.get(i);
/* 1339 */         if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */         {
/* 1341 */           retMailServers.add(prop);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1349 */     return retMailServers;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList getJavaservers()
/*      */   {
/* 1356 */     return moJavaServers;
/*      */   }
/*      */   
/*      */   public ArrayList getSapservers() {
/* 1360 */     ArrayList retSapServers = new ArrayList();
/* 1361 */     if ((moSapServers != null) && (moSapServers.size() > 0))
/*      */     {
/* 1363 */       Properties prop = (Properties)moSapServers.get(0);
/* 1364 */       retSapServers.add(prop);
/* 1365 */       for (int i = 1; i < moSapServers.size(); i++)
/*      */       {
/* 1367 */         prop = (Properties)moSapServers.get(i);
/* 1368 */         if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */         {
/* 1370 */           retSapServers.add(prop);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1375 */     return retSapServers;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getVirtualservers()
/*      */   {
/* 1383 */     ArrayList retVirServers = new ArrayList();
/* 1384 */     if ((moVirtualServers != null) && (moVirtualServers.size() > 0))
/*      */     {
/* 1386 */       Properties prop = (Properties)moVirtualServers.get(0);
/* 1387 */       retVirServers.add(prop);
/* 1388 */       for (int i = 1; i < moVirtualServers.size(); i++)
/*      */       {
/* 1390 */         prop = (Properties)moVirtualServers.get(i);
/* 1391 */         if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */         {
/* 1393 */           retVirServers.add(prop);
/*      */         }
/*      */       }
/*      */     }
/* 1397 */     return retVirServers;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getCloudApps()
/*      */   {
/* 1403 */     ArrayList retServers = new ArrayList();
/* 1404 */     if ((moCloudApps != null) && (moCloudApps.size() > 0))
/*      */     {
/* 1406 */       Properties prop = (Properties)moCloudApps.get(0);
/* 1407 */       retServers.add(prop);
/* 1408 */       for (int i = 1; i < moCloudApps.size(); i++)
/*      */       {
/* 1410 */         prop = (Properties)moCloudApps.get(i);
/* 1411 */         if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */         {
/* 1413 */           retServers.add(prop);
/*      */         }
/*      */       }
/*      */     }
/* 1417 */     return retServers;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 1422 */   private String dbserver = null;
/*      */   
/*      */   public String getDbserver() {
/* 1425 */     return this.dbserver;
/*      */   }
/*      */   
/*      */   public void setDbserver(String dbserver) {
/* 1429 */     this.dbserver = dbserver;
/*      */   }
/*      */   
/* 1432 */   private String appserver = null;
/*      */   
/*      */   public String getAppserver() {
/* 1435 */     return this.appserver;
/*      */   }
/*      */   
/*      */   public void setAppserver(String appserver) {
/* 1439 */     this.appserver = appserver;
/*      */   }
/*      */   
/* 1442 */   private String middlewareserver = null;
/*      */   
/*      */   public String getMiddlewareserver() {
/* 1445 */     return this.appserver;
/*      */   }
/*      */   
/*      */   public void setMiddlewareserver(String middlewareserver) {
/* 1449 */     this.middlewareserver = middlewareserver;
/*      */   }
/*      */   
/* 1452 */   private String service = null;
/*      */   
/*      */   public String getService() {
/* 1455 */     return this.service;
/*      */   }
/*      */   
/*      */ 
/* 1459 */   public void setService(String service) { this.service = service; }
/*      */   
/* 1461 */   private String mailservice = null;
/*      */   
/*      */   public String getMailservice() {
/* 1464 */     return this.mailservice;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 1469 */   public void setMailservice(String mailservice) { this.mailservice = mailservice; }
/*      */   
/* 1471 */   private String webservice = null;
/*      */   
/* 1473 */   public String getWebservice() { return this.webservice; }
/*      */   
/* 1475 */   private String webserver = null;
/*      */   
/* 1477 */   public String getWebserver() { return this.webserver; }
/*      */   
/* 1479 */   private String url = null;
/*      */   
/* 1481 */   public String getUrl() { return this.url; }
/*      */   
/*      */ 
/* 1484 */   private String virserver = null;
/*      */   
/* 1486 */   public String getVirserver() { return this.virserver; }
/*      */   
/*      */ 
/* 1489 */   private String cldApps = null;
/*      */   
/* 1491 */   public String getCldApps() { return this.cldApps; }
/*      */   
/*      */ 
/* 1494 */   private String javaservice = null;
/*      */   
/*      */   public String getJavaservice() {
/* 1497 */     return this.javaservice;
/*      */   }
/*      */   
/*      */   public void setJavaservice(String javaservice)
/*      */   {
/* 1502 */     this.javaservice = javaservice;
/*      */   }
/*      */   
/* 1505 */   private String sapservice = null;
/*      */   
/*      */   public String getSapservice() {
/* 1508 */     return this.sapservice;
/*      */   }
/*      */   
/*      */   public void setSapservice(String sapservice)
/*      */   {
/* 1513 */     this.sapservice = sapservice;
/*      */   }
/*      */   
/*      */   public void setWebservice(String webservice)
/*      */   {
/* 1518 */     this.webservice = webservice;
/*      */   }
/*      */   
/* 1521 */   public void setWebserver(String webserver) { this.webserver = webserver; }
/*      */   
/*      */ 
/* 1524 */   public void setUrl(String url) { this.url = url; }
/*      */   
/* 1526 */   private String system = null;
/*      */   
/*      */   public String getSystem() {
/* 1529 */     return this.system;
/*      */   }
/*      */   
/*      */   public void setSystem(String system) {
/* 1533 */     this.system = system;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getAttributeIds()
/*      */   {
/* 1540 */     return this.attributeids;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 1545 */   private void setAttributeIds(String attributeids) { this.attributeids = attributeids; }
/*      */   
/* 1547 */   private String attributeids = "";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public org.apache.struts.action.ActionErrors validate(org.apache.struts.action.ActionMapping mapping, HttpServletRequest request)
/*      */   {
/* 1554 */     String customfield = getCustomfield();
/* 1555 */     String customFieldValue = getCustomFieldValue();
/* 1556 */     String rid = null;
/*      */     
/* 1558 */     rid = getResid();
/* 1559 */     if ((rid != null) && (!rid.equals("all")))
/*      */     {
/*      */ 
/* 1562 */       String dname = ReportUtilities.getLabelName(rid);
/* 1563 */       setDisplayname(dname);
/*      */     }
/* 1565 */     String fromSchedule = request.getParameter("isschedule");
/*      */     
/* 1567 */     String tempheading = " ";
/* 1568 */     String gh = " ";
/* 1569 */     if (this.no_of_rows == -1)
/*      */     {
/* 1571 */       tempheading = FormatUtil.getString("am.monitortab.all.text");
/*      */     }
/*      */     else
/*      */     {
/* 1575 */       tempheading = FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { String.valueOf(this.no_of_rows) });
/*      */     }
/* 1577 */     String theading = this.resourceType;
/* 1578 */     if ((this.actionMethod != null) && (this.resourceType != null))
/*      */     {
/*      */ 
/*      */ 
/* 1582 */       if (whHeadings.get(this.resourceType) != null)
/*      */       {
/*      */ 
/* 1585 */         this.heading = (tempheading + " " + whHeadings.get(this.resourceType));
/* 1586 */         gh = this.heading;
/*      */       }
/* 1588 */       else if ((fromSchedule != null) && (fromSchedule.equals("true")) && (this.resourceType.indexOf("_") != -1)) {
/* 1589 */         ArrayList customMonitorNameList = ReportUtil.getNewCustomMonitorNames();
/* 1590 */         Iterator itr = customMonitorNameList.iterator();
/*      */         
/* 1592 */         while (itr.hasNext()) {
/* 1593 */           String tempKey = theading;
/*      */           
/* 1595 */           String customMonitorName = (String)itr.next();
/*      */           
/* 1597 */           if ((theading.indexOf(customMonitorName) != -1) && (!tempKey.equals(customMonitorName))) {
/* 1598 */             if (theading.startsWith(customMonitorName)) {
/* 1599 */               tempKey = tempKey.substring(customMonitorName.length() + 3, theading.length());
/*      */             }
/* 1601 */             else if (theading.endsWith(customMonitorName)) {
/* 1602 */               tempKey = tempKey.substring(0, theading.indexOf(customMonitorName) - 3);
/*      */             }
/*      */             else {
/* 1605 */               tempKey = tempKey.substring(0, theading.indexOf(customMonitorName) - 3);
/* 1606 */               tempKey = tempKey + theading.substring(tempKey.length() + customMonitorName.length() + 3, theading.length());
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1611 */           theading = tempKey;
/*      */         }
/*      */         
/* 1614 */         if (whHeadings.get(theading) != null) {
/* 1615 */           this.heading = (tempheading + " " + whHeadings.get(theading));
/*      */           
/* 1617 */           gh = tempheading + " " + whHeadings.get(theading);
/*      */         }
/*      */       }
/*      */       
/* 1621 */       if ((this.actionMethod.equals("generateAttributeReport")) || (this.actionMethod.equals("generateTrendReport")))
/*      */       {
/*      */ 
/* 1624 */         HashMap allAttributeNames = new HashMap();
/*      */         
/* 1626 */         if (((request.getParameter("workingdays") != null) && (request.getParameter("workingdays").equals("true"))) || ((request.getParameter("hid") != null) && (request.getParameter("hid").equals("true"))))
/*      */         {
/*      */ 
/*      */ 
/* 1630 */           this.heading = FormatUtil.getString("am.reporttab.heading.haattributereport.text", new String[] { (String)attributeHeadings.get(this.attribute), (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid()) });
/* 1631 */           this.heading = (this.heading + " - " + getReportPeriod());
/* 1632 */           gh = gh + " " + FormatUtil.getString("am.reporttab.heading.haattributereport.text", new String[] { (String)attributeHeadings.get(this.attribute), (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid()) }) + " - " + getReportPeriod();
/* 1633 */           request.setAttribute("heading", this.heading);
/* 1634 */           request.setAttribute("graphheading", gh);
/* 1635 */           request.setAttribute("attributeDispalyName", attributeHeadings.get(this.attribute));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/* 1641 */         else if (this.resourceType.indexOf("_") != -1) {
/* 1642 */           String temp = "";
/* 1643 */           if ((whHeadings.get(this.resourceType) == null) && (fromSchedule != null) && (fromSchedule.equals("true"))) {
/* 1644 */             temp = (String)whHeadings.get(theading);
/*      */           }
/*      */           else
/*      */           {
/* 1648 */             String[] temp1 = this.resourceType.split("_");
/* 1649 */             if (temp1.length > 2) {
/* 1650 */               temp = FormatUtil.getString("am.manage.custom.types");
/*      */             } else {
/* 1652 */               temp = temp1[0];
/*      */             }
/*      */           }
/*      */           
/* 1656 */           this.heading = FormatUtil.getString("am.reporttab.heading.customeattributereport.text", new String[] { tempheading, (String)attributeHeadings.get(this.attribute), temp });
/* 1657 */           this.heading = (this.heading + " - " + getReportPeriod());
/* 1658 */           gh = this.heading + " " + temp;
/*      */           
/* 1660 */           gh = gh + " - " + attributeHeadings.get(this.attribute) + " - " + getReportPeriod();
/* 1661 */           request.setAttribute("heading", this.heading);
/* 1662 */           request.setAttribute("graphheading", gh);
/* 1663 */           request.setAttribute("attributeDispalyName", attributeHeadings.get(this.attribute));
/*      */         }
/*      */         else
/*      */         {
/* 1667 */           if (attributeHeadings.get(this.attribute) != null)
/*      */           {
/* 1669 */             this.heading = (this.heading + " - " + attributeHeadings.get(this.attribute));
/* 1670 */             gh = gh + " - " + attributeHeadings.get(this.attribute);
/* 1671 */             if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 1672 */               HashMap<String, String> customDetail = MyFields.reportValues(customFieldValue);
/* 1673 */               this.heading = (this.heading + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") }));
/* 1674 */               gh = gh + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") });
/*      */             }
/* 1676 */             this.heading = (this.heading + " - " + getReportPeriod());
/* 1677 */             gh = gh + " - " + getReportPeriod();
/* 1678 */             request.setAttribute("attributeDispalyName", attributeHeadings.get(this.attribute));
/*      */           }
/*      */           else {
/* 1681 */             Map AttName = ReportUtil.getDisplayNameForAttributes();
/* 1682 */             String displayName = (String)AttName.get(this.attribute);
/* 1683 */             if (displayName == null)
/*      */             {
/* 1685 */               java.util.List vmClusterAttributeList = ReportUtil.getAttributesForVMClusters();
/* 1686 */               if (vmClusterAttributeList != null)
/*      */               {
/* 1688 */                 for (int i = 0; i < vmClusterAttributeList.size(); i++)
/*      */                 {
/* 1690 */                   String idWithName = (String)vmClusterAttributeList.get(i);
/* 1691 */                   int hashIndex = idWithName.indexOf("#");
/* 1692 */                   String aid = idWithName.substring(0, hashIndex);
/* 1693 */                   if (aid.equals(this.attribute))
/*      */                   {
/* 1695 */                     displayName = idWithName.substring(hashIndex + 1, idWithName.length());
/* 1696 */                     this.heading = tempheading;
/* 1697 */                     gh = tempheading;
/* 1698 */                     break;
/*      */                   }
/*      */                 }
/*      */               }
/* 1702 */               java.util.List vmRPoolAttributeList = ReportUtil.getAttributesForVMRPools();
/* 1703 */               if (vmRPoolAttributeList != null)
/*      */               {
/* 1705 */                 for (int i = 0; i < vmRPoolAttributeList.size(); i++)
/*      */                 {
/* 1707 */                   String idWithName = (String)vmRPoolAttributeList.get(i);
/* 1708 */                   int hashIndex = idWithName.indexOf("#");
/* 1709 */                   String aid = idWithName.substring(0, hashIndex);
/* 1710 */                   if (aid.equals(this.attribute))
/*      */                   {
/* 1712 */                     displayName = idWithName.substring(hashIndex + 1, idWithName.length());
/* 1713 */                     this.heading = tempheading;
/* 1714 */                     gh = tempheading;
/* 1715 */                     break;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 1720 */             if ("".equals(this.heading))
/*      */             {
/* 1722 */               this.heading = displayName;
/*      */             }
/*      */             else
/*      */             {
/* 1726 */               this.heading = (this.heading + " - " + displayName);
/*      */             }
/* 1728 */             this.heading = this.heading;
/* 1729 */             gh = gh + " - " + displayName;
/* 1730 */             if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 1731 */               HashMap<String, String> customDetail = MyFields.reportValues(customFieldValue);
/* 1732 */               this.heading = (this.heading + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") }));
/* 1733 */               gh = gh + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") });
/*      */             }
/* 1735 */             this.heading = (this.heading + " - " + getReportPeriod());
/* 1736 */             gh = gh + " - " + getReportPeriod();
/*      */             
/* 1738 */             request.setAttribute("attributeDispalyName", displayName);
/*      */           }
/* 1740 */           request.setAttribute("heading", this.heading);
/* 1741 */           request.setAttribute("graphheading", gh);
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */       }
/* 1748 */       else if (this.actionMethod.equals("generateHealthReport"))
/*      */       {
/* 1750 */         if (this.resourceType.indexOf("_") != -1)
/*      */         {
/* 1752 */           if ((whHeadings.get(this.resourceType) == null) && (fromSchedule != null) && (fromSchedule.equals("true"))) {
/* 1753 */             this.heading = (tempheading + " " + whHeadings.get(theading));
/*      */           }
/*      */           else {
/* 1756 */             String[] temp = this.resourceType.split("_");
/* 1757 */             if (temp.length > 2) {
/* 1758 */               this.heading = (tempheading + " " + FormatUtil.getString("am.manage.custom.types"));
/*      */             } else {
/* 1760 */               this.heading = (tempheading + " " + temp[0]);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1767 */         this.heading = FormatUtil.getString("am.reporttab.heading.healthreport.text", new String[] { this.heading });
/* 1768 */         if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 1769 */           HashMap<String, String> customDetail = MyFields.reportValues(customFieldValue);
/* 1770 */           this.heading = (this.heading + " " + FormatUtil.getString("am.webclient.reports.customfield.filterby.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") }));
/*      */         }
/* 1772 */         this.heading = (this.heading + " - " + getReportPeriod());
/* 1773 */         request.setAttribute("heading", this.heading);
/*      */ 
/*      */ 
/*      */       }
/* 1777 */       else if (this.actionMethod.equals("generateAvailabilityReport"))
/*      */       {
/*      */ 
/* 1780 */         if (this.resourceType.indexOf("_") != -1) {
/* 1781 */           if ((whHeadings.get(this.resourceType) == null) && (fromSchedule != null) && (fromSchedule.equals("true"))) {
/* 1782 */             this.heading = (tempheading + " " + whHeadings.get(theading));
/*      */           }
/*      */           else {
/* 1785 */             String[] temp = this.resourceType.split("_");
/* 1786 */             if (temp.length > 2) {
/* 1787 */               this.heading = (tempheading + " " + FormatUtil.getString("am.manage.custom.types"));
/*      */             } else {
/* 1789 */               this.heading = (tempheading + " " + temp[0]);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1796 */         this.heading = FormatUtil.getString("am.reporttab.heading.availablityreport.text", new String[] { this.heading });
/* 1797 */         if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 1798 */           HashMap<String, String> customDetail = MyFields.reportValues(customFieldValue);
/* 1799 */           this.heading = (this.heading + " " + FormatUtil.getString("am.webclient.reports.customfield.filterby.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") }));
/*      */         }
/* 1801 */         this.heading = (this.heading + " - " + getReportPeriod());
/*      */         
/* 1803 */         request.setAttribute("heading", this.heading);
/*      */ 
/*      */       }
/* 1806 */       else if (this.actionMethod.equals("generateCustomAttributeReport"))
/*      */       {
/* 1808 */         this.heading = FormatUtil.getString("am.reporttab.heading.customattributereport.text", new String[] { getAttributeName() });
/* 1809 */         this.heading = (this.heading + " - " + getReportPeriod());
/* 1810 */         request.setAttribute("heading", this.heading);
/* 1811 */         request.setAttribute("attributeDispalyName", getAttributeName());
/*      */ 
/*      */       }
/* 1814 */       else if (this.actionMethod.equals("generateMttrAvailablityReport"))
/*      */       {
/*      */ 
/*      */ 
/* 1818 */         this.heading = FormatUtil.getString("am.reporttab.heading.downtimereport.text", new String[] { getReportPeriod() });
/* 1819 */         request.setAttribute("heading", this.heading);
/* 1820 */         request.setAttribute("csv", "false");
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/* 1825 */       else if (this.actionMethod.equals("generateSummaryReport"))
/*      */       {
/*      */ 
/* 1828 */         this.heading = FormatUtil.getString("am.reporttab.heading.summaryreport.text", new String[] { getDisplayname() });
/* 1829 */         this.heading = (this.heading + " - " + getReportPeriod());
/* 1830 */         request.setAttribute("heading", this.heading);
/* 1831 */         request.setAttribute("csv", "false");
/*      */ 
/*      */       }
/* 1834 */       else if (this.actionMethod.equals("generateHAAvailabilityReport"))
/*      */       {
/* 1836 */         this.heading = FormatUtil.getString("am.reporttab.heading.haavailablityreport.text", new String[] { (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid()) });
/* 1837 */         this.heading = (this.heading + " - " + getReportPeriod());
/* 1838 */         request.setAttribute("heading", this.heading);
/*      */       }
/* 1840 */       else if (this.actionMethod.equals("generateLicUsageReport"))
/*      */       {
/* 1842 */         com.adventnet.appmanager.logging.AMLog.debug("License Usage Calculation ==> ReportForm");
/* 1843 */         this.heading = "License Usage Calculation";
/* 1844 */         request.setAttribute("heading", this.heading);
/*      */       }
/* 1846 */       else if (this.actionMethod.equals("generateHAHealthReport"))
/*      */       {
/* 1848 */         this.heading = FormatUtil.getString("am.reporttab.heading.hahealthreport.text", new String[] { (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid()) });
/* 1849 */         this.heading = (this.heading + " - " + getReportPeriod());
/* 1850 */         request.setAttribute("heading", this.heading);
/*      */       }
/* 1852 */       else if (this.actionMethod.equals("generateAvailabilitySnapShotReport"))
/*      */       {
/* 1854 */         String HID = getHaid();
/* 1855 */         String h = "";
/* 1856 */         if (HID.indexOf(",") == -1) {
/* 1857 */           h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid());
/* 1858 */           if (h == null) {
/* 1859 */             h = FormatUtil.getString("All Monitor Groups");
/*      */           }
/*      */         } else {
/* 1862 */           String[] IDtemp = HID.split(",");
/* 1863 */           for (int r = 0; r < IDtemp.length; r++) {
/* 1864 */             if (r == 0) {
/* 1865 */               h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             } else {
/* 1867 */               h = h + "," + (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             }
/*      */           }
/*      */         }
/* 1871 */         if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 1872 */           HashMap<String, String> customDetail = MyFields.reportValues(customFieldValue);
/* 1873 */           h = h + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") });
/*      */         }
/* 1875 */         this.heading = FormatUtil.getString("am.webclient.reports.AvailabilityStausReport.text", new String[] { h });
/* 1876 */         this.heading = (this.heading + " - " + getReportPeriod());
/* 1877 */         request.setAttribute("heading", this.heading);
/*      */       }
/* 1879 */       else if ((this.actionMethod.equals("generateHASnapShotReport")) || (this.actionMethod.equals("generateHASnapShotReportWithHostName")))
/*      */       {
/*      */ 
/* 1882 */         String HID = getHaid();
/* 1883 */         String h = "";
/* 1884 */         if (HID.indexOf(",") == -1) {
/* 1885 */           h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid());
/* 1886 */           if (h == null) {
/* 1887 */             h = FormatUtil.getString("All Monitor Groups");
/*      */           }
/*      */         } else {
/* 1890 */           String[] IDtemp = HID.split(",");
/* 1891 */           for (int r = 0; r < IDtemp.length; r++) {
/* 1892 */             if (r == 0) {
/* 1893 */               h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             } else {
/* 1895 */               h = h + "," + (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             }
/*      */           }
/*      */         }
/* 1899 */         if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 1900 */           HashMap<String, String> customDetail = MyFields.reportValues(customFieldValue);
/* 1901 */           h = h + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") });
/*      */         }
/* 1903 */         if (this.actionMethod.equals("generateHASnapShotReportWithHostName")) {
/* 1904 */           this.heading = FormatUtil.getString("am.webclient.reports.AvailabilitySnapshotSummaryReport.text", new String[] { h });
/*      */         } else {
/* 1906 */           this.heading = FormatUtil.getString("am.webclient.reports.AvailabilitySnapshotReport.text", new String[] { h });
/*      */         }
/*      */         
/* 1909 */         request.setAttribute("heading", this.heading);
/*      */ 
/*      */       }
/* 1912 */       else if (this.actionMethod.equals("generatePeriodAvailabilityDowntimeReport"))
/*      */       {
/*      */ 
/* 1915 */         String HID = getHaid();
/* 1916 */         String h = "";
/* 1917 */         if (HID.indexOf(",") == -1) {
/* 1918 */           h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid());
/*      */         }
/*      */         else {
/* 1921 */           String[] IDtemp = HID.split(",");
/* 1922 */           for (int r = 0; r < IDtemp.length; r++) {
/* 1923 */             if (r == 0) {
/* 1924 */               h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             } else {
/* 1926 */               h = h + "," + (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             }
/*      */           }
/*      */         }
/* 1930 */         String attri = getInterval();
/*      */         
/* 1932 */         if (("".equals(attri)) || ("day".equals(attri))) {
/* 1933 */           this.heading = FormatUtil.getString("am.webclient.reports.availabiityTrendDowntimeReport.text", new String[] { h, FormatUtil.getString("Daily") });
/*      */         }
/* 1935 */         else if ("week".equals(attri)) {
/* 1936 */           this.heading = FormatUtil.getString("am.webclient.reports.availabiityTrendDowntimeReport.text", new String[] { h, FormatUtil.getString("Weekly") });
/*      */         }
/* 1938 */         else if ("month".equals(attri)) {
/* 1939 */           this.heading = FormatUtil.getString("am.webclient.reports.availabiityTrendDowntimeReport.text", new String[] { h, FormatUtil.getString("Monthly") });
/*      */         }
/* 1941 */         request.setAttribute("heading", this.heading);
/*      */ 
/*      */       }
/* 1944 */       else if (this.actionMethod.equals("generateWeeklyMonthlyOutageReport"))
/*      */       {
/* 1946 */         String HID = getHaid();
/* 1947 */         String h = "";
/* 1948 */         if (HID.indexOf(",") == -1) {
/* 1949 */           h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid());
/* 1950 */           if (h == null) {
/* 1951 */             h = FormatUtil.getString("All Monitor Groups");
/*      */           }
/*      */         } else {
/* 1954 */           String[] IDtemp = HID.split(",");
/* 1955 */           for (int r = 0; r < IDtemp.length; r++) {
/* 1956 */             if (r == 0) {
/* 1957 */               h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             } else {
/* 1959 */               h = h + "," + (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             }
/*      */           }
/*      */         }
/* 1963 */         HashMap<String, String> customDetail = new HashMap();
/* 1964 */         if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 1965 */           customDetail = MyFields.reportValues(customFieldValue);
/* 1966 */           h = h + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") });
/*      */         }
/* 1968 */         String attri = getInterval();
/* 1969 */         String thisstartdate = getThisstart();
/* 1970 */         String thisenddate = getThisend();
/* 1971 */         String laststartdate = getLaststart();
/* 1972 */         String lastenddate = getLastend();
/*      */         
/*      */ 
/*      */ 
/* 1976 */         if ((thisstartdate == null) || (thisstartdate.equals("")) || (thisenddate.equals(""))) {
/* 1977 */           if ("day".equals(attri)) {
/* 1978 */             attri = "week";
/*      */           }
/* 1980 */           if (("".equals(attri)) || ("week".equals(attri))) {
/* 1981 */             this.heading = FormatUtil.getString("am.webclient.reports.outagecompariosonReport.text", new String[] { h, FormatUtil.getString("Weekly") });
/*      */           }
/* 1983 */           else if ("month".equals(attri)) {
/* 1984 */             this.heading = FormatUtil.getString("am.webclient.reports.outagecompariosonReport.text", new String[] { h, FormatUtil.getString("Monthly") });
/*      */           }
/*      */         } else {
/* 1987 */           String h1 = laststartdate + " - " + lastenddate;
/* 1988 */           String h2 = thisstartdate + " - " + thisenddate;
/* 1989 */           this.heading = FormatUtil.getString("am.webclient.reports.outagecompariosonReport.text", new String[] { h1, h2 });
/* 1990 */           if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1))
/*      */           {
/* 1992 */             this.heading = (this.heading + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") }));
/*      */           }
/*      */         }
/*      */         
/* 1996 */         request.setAttribute("heading", this.heading);
/*      */       }
/* 1998 */       else if (this.actionMethod.equals("generatePeriodAvailabilityReport"))
/*      */       {
/* 2000 */         String HID = getHaid();
/* 2001 */         String h = "";
/* 2002 */         if (HID.indexOf(",") == -1) {
/* 2003 */           h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid());
/* 2004 */           if (h == null) {
/* 2005 */             h = FormatUtil.getString("All Monitor Groups");
/*      */           }
/*      */         } else {
/* 2008 */           String[] IDtemp = HID.split(",");
/* 2009 */           for (int r = 0; r < IDtemp.length; r++) {
/* 2010 */             if (r == 0) {
/* 2011 */               h = (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             } else {
/* 2013 */               h = h + "," + (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(IDtemp[r]);
/*      */             }
/*      */           }
/*      */         }
/* 2017 */         if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 2018 */           HashMap<String, String> customDetail = MyFields.reportValues(customFieldValue);
/* 2019 */           h = h + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") });
/*      */         }
/* 2021 */         String attri = getInterval();
/*      */         
/* 2023 */         if (("".equals(attri)) || ("day".equals(attri))) {
/* 2024 */           this.heading = FormatUtil.getString("am.webclient.reports.availabiityTrendReport.text", new String[] { h, FormatUtil.getString("Daily") });
/*      */         }
/* 2026 */         else if ("week".equals(attri)) {
/* 2027 */           this.heading = FormatUtil.getString("am.webclient.reports.availabiityTrendReport.text", new String[] { h, FormatUtil.getString("Weekly") });
/*      */         }
/* 2029 */         else if ("month".equals(attri)) {
/* 2030 */           this.heading = FormatUtil.getString("am.webclient.reports.availabiityTrendReport.text", new String[] { h, FormatUtil.getString("Monthly") });
/*      */         }
/*      */         
/*      */ 
/* 2034 */         request.setAttribute("heading", this.heading);
/*      */       }
/* 2036 */       else if (this.actionMethod.equals("generateHAResponseTimeReport"))
/*      */       {
/* 2038 */         this.heading = FormatUtil.getString("am.reporttab.heading.haresponsereport.text", new String[] { (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid()) });
/* 2039 */         this.heading = (this.heading + " - " + getReportPeriod());
/* 2040 */         gh = gh + " " + FormatUtil.getString("am.reporttab.heading.haresponsereport.text", new String[] { (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid()) }) + " - " + getReportPeriod();
/* 2041 */         request.setAttribute("heading", this.heading);
/* 2042 */         request.setAttribute("attributeDispalyName", "Response Time");
/* 2043 */         request.setAttribute("graphheading", gh);
/* 2044 */         request.setAttribute("Units", "ms");
/* 2045 */         setUnit("ms");
/*      */       }
/* 2047 */       else if (this.actionMethod.equals("generateEventSummary"))
/*      */       {
/*      */ 
/* 2050 */         this.heading = FormatUtil.getString("am.reporttab.heading.haeventreport.text", new String[] { (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid()) });
/*      */         
/* 2052 */         this.heading = (this.heading + " - " + getReportPeriod());
/* 2053 */         request.setAttribute("heading", this.heading);
/*      */       }
/* 2055 */       else if ((this.actionMethod.equals("generateGlanceReport")) || (this.actionMethod.equals("generateIndividualGlanceReport")))
/*      */       {
/*      */ 
/* 2058 */         HashMap allAttributeNames = new HashMap();
/*      */         
/* 2060 */         if ((this.actionMethod.equals("generateGlanceReport")) && (getHaid() != null) && (!"null".equals(getHaid())))
/*      */         {
/*      */ 
/*      */ 
/* 2064 */           this.heading = (FormatUtil.getString("am.webclient.reports.ataglance.report") + " - " + (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid()));
/* 2065 */           this.heading = (this.heading + " - " + getReportPeriod());
/*      */           
/* 2067 */           request.setAttribute("heading", this.heading);
/* 2068 */           request.setAttribute("headingCategory", (String)((Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(getHaid()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/* 2074 */         else if (this.resourceType.indexOf("_") != -1)
/*      */         {
/* 2076 */           String[] temp = this.resourceType.split("_");
/*      */           
/* 2078 */           this.heading = FormatUtil.getString("am.webclient.reports.ataglance.report");
/* 2079 */           if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 2080 */             HashMap<String, String> customDetail = MyFields.reportValues(customFieldValue);
/* 2081 */             this.heading = (this.heading + " " + FormatUtil.getString("am.webclient.reports.customfield.text", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") }));
/*      */           }
/*      */           
/* 2084 */           if (temp.length > 2) {
/* 2085 */             request.setAttribute("headingCategory", FormatUtil.getString("am.manage.custom.types"));
/* 2086 */             this.heading = (this.heading + " - " + FormatUtil.getString("am.manage.custom.types"));
/*      */           } else {
/* 2088 */             this.heading = (this.heading + " - " + temp[0]);
/* 2089 */             request.setAttribute("headingCategory", temp[0]);
/*      */           }
/* 2091 */           this.heading = (this.heading + " - " + getReportPeriod());
/* 2092 */           request.setAttribute("heading", this.heading);
/*      */ 
/*      */ 
/*      */         }
/* 2096 */         else if (whHeadings.get(this.resourceType) != null)
/*      */         {
/* 2098 */           if ((customfield.equals("true")) && (customFieldValue.indexOf("$") != -1)) {
/* 2099 */             HashMap<String, String> customDetail = MyFields.reportValues(customFieldValue);
/* 2100 */             this.heading = FormatUtil.getString("am.webclient.reports.customfield.filter.heading.text", new String[] { FormatUtil.getString("am.webclient.reports.ataglance.report"), (String)whHeadings.get(this.resourceType), (String)customDetail.get("label"), (String)customDetail.get("value") });
/*      */           } else {
/* 2102 */             this.heading = (FormatUtil.getString("am.webclient.reports.ataglance.report") + " - " + whHeadings.get(this.resourceType) + " - " + getReportPeriod());
/*      */           }
/* 2104 */           request.setAttribute("heading", this.heading);
/* 2105 */           request.setAttribute("headingCategory", whHeadings.get(this.resourceType));
/*      */         }
/* 2107 */         else if (this.actionMethod.equals("generateUnderSizedMonitors"))
/*      */         {
/* 2109 */           request.setAttribute("tempheading", tempheading);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2114 */           this.heading = (FormatUtil.getString("am.webclient.reports.ataglance.report") + " - " + getDisplayname());
/* 2115 */           this.heading = (this.heading + " - " + getReportPeriod());
/* 2116 */           request.setAttribute("heading", this.heading);
/* 2117 */           request.setAttribute("headingCategory", getDisplayname());
/*      */         }
/*      */         
/* 2120 */         request.setAttribute("headingPeriod", getReportPeriod());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2125 */     if ((this.resourceType != null) && (this.attribute != null))
/*      */     {
/* 2127 */       String temp = (String)attributeHeadings.get(this.attribute);
/* 2128 */       if (temp != null) {
/* 2129 */         setAttributeName(temp);
/*      */       }
/* 2131 */       if ((this.attribute != null) && (this.attribute.equals("responseTime")))
/*      */       {
/* 2133 */         this.attribute = ((String)responsetimeids.get(this.resourceType));
/* 2134 */         request.setAttribute("Units", "ms");
/* 2135 */         setUnit("ms");
/*      */       }
/* 2137 */       if ((this.attribute != null) && (this.attribute.equals("connectionTime")))
/*      */       {
/* 2139 */         this.attribute = ((String)responsetimeids.get(this.resourceType));
/* 2140 */         request.setAttribute("Units", "ms");
/* 2141 */         setUnit("ms");
/*      */       }
/* 2143 */       else if ((this.attribute != null) && (this.attribute.equals("cpuid")))
/*      */       {
/*      */ 
/* 2146 */         this.attribute = ((String)cpuids.get(this.resourceType));
/* 2147 */         request.setAttribute("Units", "%");
/*      */         
/* 2149 */         setUnit("%");
/*      */       }
/* 2151 */       else if ((this.attribute != null) && (this.attribute.equals("jdkcpuid")))
/*      */       {
/*      */ 
/* 2154 */         this.attribute = ((String)jdkcpuids.get(this.resourceType));
/* 2155 */         request.setAttribute("Units", "%");
/*      */         
/* 2157 */         setUnit("%");
/*      */       }
/* 2159 */       else if ((this.attribute != null) && (this.attribute.equals("jvm")))
/*      */       {
/*      */ 
/* 2162 */         this.attribute = ((String)jvmids.get(this.resourceType));
/* 2163 */         request.setAttribute("Units", "MB");
/* 2164 */         request.setAttribute("AttDispalyName", "JVM");
/* 2165 */         setUnit("MB");
/*      */       }
/* 2167 */       else if ((this.attribute != null) && (this.attribute.equals("mem")))
/*      */       {
/* 2169 */         this.attribute = ((String)memids.get(this.resourceType));
/* 2170 */         request.setAttribute("Units", "%");
/* 2171 */         setUnit("%");
/*      */       }
/* 2173 */       else if ((this.attribute != null) && ((this.attribute.equals("memmb")) || (this.attribute.equals("3608"))))
/*      */       {
/* 2175 */         this.attribute = ((String)jdkmemids.get(this.resourceType));
/* 2176 */         request.setAttribute("Units", "MB");
/* 2177 */         setUnit("MB");
/*      */       }
/* 2179 */       else if ((this.attribute != null) && (this.attribute.equals("disk")))
/*      */       {
/* 2181 */         this.attribute = ((String)diskids.get(this.resourceType));
/* 2182 */         request.setAttribute("Units", "%");
/* 2183 */         setUnit("%");
/*      */       }
/* 2185 */       else if ((this.attribute != null) && (this.attribute.equals("jdbc")))
/*      */       {
/* 2187 */         this.attribute = ((String)jdbcids.get(this.resourceType));
/* 2188 */         request.setAttribute("Units", "");
/* 2189 */         setUnit("");
/*      */       }
/* 2191 */       else if ((this.attribute != null) && (this.attribute.equals("thread")))
/*      */       {
/* 2193 */         this.attribute = ((String)threadids.get(this.resourceType));
/* 2194 */         if (this.attribute == null)
/*      */         {
/* 2196 */           java.util.Enumeration enu = threadids.keys();
/* 2197 */           while (enu.hasMoreElements())
/*      */           {
/* 2199 */             String key = (String)enu.nextElement();
/* 2200 */             if (key.indexOf(this.resourceType) != -1)
/*      */             {
/* 2202 */               this.attribute = ((String)threadids.get(key));
/* 2203 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 2207 */         request.setAttribute("Units", "");
/* 2208 */         setUnit("");
/*      */       }
/* 2210 */       else if ((this.attribute != null) && (this.attribute.equals("session")))
/*      */       {
/*      */ 
/* 2213 */         this.attribute = ((String)sessionids.get(this.resourceType));
/* 2214 */         request.setAttribute("Units", "");
/* 2215 */         setUnit("");
/*      */       }
/* 2217 */       else if ((this.attribute != null) && (this.attribute.equals("buffer")))
/*      */       {
/*      */ 
/* 2220 */         this.attribute = ((String)bufferids.get(this.resourceType));
/* 2221 */         request.setAttribute("Units", "%");
/* 2222 */         setUnit("%");
/*      */       }
/* 2224 */       else if ((this.attribute != null) && (this.attribute.equals("cache")))
/*      */       {
/* 2226 */         this.attribute = ((String)cacheids.get(this.resourceType));
/* 2227 */         request.setAttribute("Units", "%");
/* 2228 */         setUnit("%");
/*      */       }
/* 2230 */       else if ((this.attribute != null) && (this.attribute.equals("throughput")))
/*      */       {
/* 2232 */         this.attribute = ((String)throughputids.get(this.resourceType));
/* 2233 */         request.setAttribute("Units", FormatUtil.getString("am.webclient.oracleas.throughputlabel"));
/* 2234 */         setUnit("");
/*      */       }
/* 2236 */       else if ((this.attribute != null) && (this.attribute.equals("webappthroughput")))
/*      */       {
/* 2238 */         this.attribute = ((String)webappthroughputids.get(this.resourceType));
/* 2239 */         request.setAttribute("Units", FormatUtil.getString("am.webclient.oracleas.throughputlabel"));
/* 2240 */         setUnit("");
/*      */       }
/* 2242 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("operationexecutiontime")))
/*      */       {
/* 2244 */         this.attribute = ((String)operationrestimeids.get(this.resourceType));
/* 2245 */         request.setAttribute("Units", "ms");
/* 2246 */         setUnit("ms");
/*      */       }
/* 2248 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("connectiontime")))
/*      */       {
/* 2250 */         this.attribute = ((String)connectiontimeids.get(this.resourceType));
/* 2251 */         request.setAttribute("Units", "ms");
/* 2252 */         setUnit("ms");
/*      */       }
/* 2254 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("sapcpu")))
/*      */       {
/* 2256 */         this.attribute = ((String)sapcpuids.get(this.resourceType));
/* 2257 */         request.setAttribute("Units", "%");
/* 2258 */         setUnit("%");
/*      */       }
/* 2260 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("sapmemory")))
/*      */       {
/* 2262 */         this.attribute = ((String)sapmemids.get(this.resourceType));
/* 2263 */         request.setAttribute("Units", "%");
/* 2264 */         setUnit("%");
/*      */       }
/* 2266 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("sapdisk")))
/*      */       {
/* 2268 */         this.attribute = ((String)sapdiskids.get(this.resourceType));
/* 2269 */         request.setAttribute("Units", "%");
/* 2270 */         setUnit("%");
/*      */       }
/* 2272 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("sappagein")))
/*      */       {
/* 2274 */         this.attribute = ((String)sappageinids.get(this.resourceType));
/* 2275 */         request.setAttribute("Units", "PageUnit");
/* 2276 */         setUnit(FormatUtil.getString("PageUnit"));
/*      */       }
/* 2278 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("sappageout")))
/*      */       {
/* 2280 */         this.attribute = ((String)sappageoutids.get(this.resourceType));
/* 2281 */         request.setAttribute("Units", "PageUnit");
/* 2282 */         setUnit(FormatUtil.getString("PageUnit"));
/*      */       }
/* 2284 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("sutilization")))
/*      */       {
/* 2286 */         this.attribute = ((String)sapspoolids.get(this.resourceType));
/* 2287 */         request.setAttribute("Units", "%");
/* 2288 */         setUnit("%");
/*      */       }
/* 2290 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("butilization")))
/*      */       {
/* 2292 */         this.attribute = ((String)sapbackgroundids.get(this.resourceType));
/* 2293 */         request.setAttribute("Units", "%");
/* 2294 */         setUnit("%");
/*      */       }
/* 2296 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("sapferestime")))
/*      */       {
/* 2298 */         this.attribute = ((String)sapdrestimeids.get(this.resourceType));
/* 2299 */         request.setAttribute("Units", "ms");
/* 2300 */         setUnit("ms");
/*      */       }
/* 2302 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("sapenqreq")))
/*      */       {
/* 2304 */         this.attribute = ((String)sapenqids.get(this.resourceType));
/* 2305 */         request.setAttribute("Units", "");
/* 2306 */         setUnit("");
/*      */       }
/* 2308 */       else if ((this.attribute != null) && (this.attribute.equalsIgnoreCase("sapccmscontime")))
/*      */       {
/* 2310 */         this.attribute = ((String)sapccmscontimeids.get(this.resourceType));
/* 2311 */         request.setAttribute("Units", "ms");
/* 2312 */         setUnit("ms");
/*      */       }
/* 2314 */       else if ((this.attribute != null) && (this.attribute.equals("wlijdbc")))
/*      */       {
/* 2316 */         this.attribute = ((String)wlijdbcids.get(this.resourceType));
/* 2317 */         request.setAttribute("Units", "");
/* 2318 */         setUnit("");
/*      */       }
/* 2320 */       else if ((this.attribute != null) && (this.attribute.equals("wlithread")))
/*      */       {
/* 2322 */         this.attribute = ((String)wlithreadids.get(this.resourceType));
/* 2323 */         request.setAttribute("Units", "");
/* 2324 */         setUnit("");
/*      */       }
/* 2326 */       else if ((this.attribute != null) && (this.attribute.equals("wlisession")))
/*      */       {
/*      */ 
/* 2329 */         this.attribute = ((String)wlisessionids.get(this.resourceType));
/* 2330 */         request.setAttribute("Units", "");
/* 2331 */         setUnit("");
/*      */       }
/* 2333 */       else if ((this.attribute != null) && (this.attribute.equals("wlijvm")))
/*      */       {
/* 2335 */         this.attribute = ((String)wlijvmids.get(this.resourceType));
/* 2336 */         request.setAttribute("Units", "MB");
/* 2337 */         request.setAttribute("AttDispalyName", "JVM");
/* 2338 */         setUnit("MB");
/*      */       }
/*      */     }
/*      */     
/* 2342 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getUnit()
/*      */   {
/* 2349 */     return this.unit;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2355 */   public void setUnit(String unit) { this.unit = unit; }
/*      */   
/* 2357 */   private String unit = null;
/*      */   
/* 2359 */   private int no_of_rows = 10;
/*      */   
/*      */   public int getNumberOfRows() {
/* 2362 */     return this.no_of_rows;
/*      */   }
/*      */   
/*      */   public void setNumberOfRows(int no) {
/* 2366 */     this.no_of_rows = no;
/*      */   }
/*      */   
/* 2369 */   private String startdate = "";
/*      */   
/*      */   public String getStartDate() {
/* 2372 */     return this.startdate;
/*      */   }
/*      */   
/*      */   public void setStartDate(String sts)
/*      */   {
/* 2377 */     this.startdate = sts;
/*      */   }
/*      */   
/* 2380 */   private String enddate = "";
/*      */   private String attributeName;
/*      */   
/* 2383 */   public String getEndDate() { return this.enddate; }
/*      */   
/*      */ 
/*      */   public void setEndDate(String sts)
/*      */   {
/* 2388 */     this.enddate = sts;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setAttributeName(String name)
/*      */   {
/* 2395 */     this.attributeName = name;
/*      */   }
/*      */   
/*      */   public String getAttributeName()
/*      */   {
/* 2400 */     return this.attributeName;
/*      */   }
/*      */   
/*      */ 
/* 2404 */   private String heading = "";
/*      */   
/*      */   public String getHeading() {
/* 2407 */     return this.heading;
/*      */   }
/*      */   
/*      */   public void setHeading(String heading) {
/* 2411 */     this.heading = heading;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static void initialiseHeadings()
/*      */   {
/* 2418 */     whHeadings = tempwhHeadings;
/* 2419 */     if (Constants.isIt360)
/*      */     {
/* 2421 */       whHeadings.put(nwd, FormatUtil.getString("Network Devices"));
/*      */     }
/*      */   }
/*      */   
/*      */   public String getReportPeriod()
/*      */   {
/* 2427 */     String rawval = "2";
/*      */     try {
/* 2429 */       String val = DBUtil.getServerConfigValue("am.rawdata.value");
/* 2430 */       if (val != null)
/*      */       {
/* 2432 */         if (Integer.parseInt(val) <= 7) {
/* 2433 */           rawval = val;
/*      */         } else {
/* 2435 */           rawval = "7";
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception o) {
/* 2440 */       o.printStackTrace();
/*      */     }
/* 2442 */     String[] periods = { FormatUtil.getString("am.webclient.period.today"), FormatUtil.getString("am.webclient.period.last7days"), FormatUtil.getString("am.webclient.period.last30days"), FormatUtil.getString("am.webclient.period.yesterday"), "", FormatUtil.getString("am.webclient.period.lastoneyear"), FormatUtil.getString("am.webclient.period.thisweek"), FormatUtil.getString("am.webclient.period.thismonth"), FormatUtil.getString("am.webclient.period.thisyear"), FormatUtil.getString("am.webclient.period.thisquarter"), FormatUtil.getString("am.webclient.period.thishalf"), FormatUtil.getString("am.webclient.period.lastmonth"), FormatUtil.getString("am.webclient.period.lastweek"), "", FormatUtil.getString("am.webclient.period.polleddata", new String[] { rawval }) };
/* 2443 */     if ((getStartDate().equals("")) || (getEndDate().equals("")))
/*      */     {
/*      */ 
/* 2446 */       if ("generateAttributeReport".equals(this.actionMethod))
/*      */       {
/* 2448 */         String attname = (String)attributeHeadings.get(this.attribute);
/* 2449 */         if ((!"CPU Utilization".equals(attname)) && (!"Memory Usage".equals(attname)) && 
/* 2450 */           ("14".equals(this.period))) {
/* 2451 */           this.period = "0";
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2456 */       else if ("14".equals(this.period)) {
/* 2457 */         this.period = "0";
/*      */       }
/*      */       
/*      */ 
/* 2461 */       return periods[Integer.parseInt(this.period)];
/*      */     }
/*      */     
/*      */ 
/* 2465 */     return "(" + getStartDate() + " : " + getEndDate() + ")";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 2470 */   private String workinghours = "on";
/*      */   private String starthours;
/*      */   private String startminutes;
/*      */   private String endhours;
/*      */   private String endminutes;
/*      */   private String workingdays;
/* 2476 */   private String monday = "on";
/* 2477 */   private String tuesday = "on";
/* 2478 */   private String wednesday = "on";
/* 2479 */   private String thursday = "on";
/* 2480 */   private String friday = "on";
/*      */   private String saturday;
/*      */   private String sunday;
/*      */   private String save;
/*      */   private String cancel;
/*      */   
/*      */   public String getWorkinghours() {
/* 2487 */     return this.workinghours;
/*      */   }
/*      */   
/*      */   public void setWorkinghours(String workinghours)
/*      */   {
/* 2492 */     this.workinghours = workinghours;
/*      */   }
/*      */   
/*      */   public String getStarthours()
/*      */   {
/* 2497 */     return this.starthours;
/*      */   }
/*      */   
/*      */   public void setStarthours(String starthours)
/*      */   {
/* 2502 */     this.starthours = starthours;
/*      */   }
/*      */   
/*      */   public String getStartminutes()
/*      */   {
/* 2507 */     return this.startminutes;
/*      */   }
/*      */   
/*      */   public void setStartminutes(String startminutes)
/*      */   {
/* 2512 */     this.startminutes = startminutes;
/*      */   }
/*      */   
/*      */   public String getEndhours()
/*      */   {
/* 2517 */     return this.endhours;
/*      */   }
/*      */   
/*      */   public void setEndhours(String endhours)
/*      */   {
/* 2522 */     this.endhours = endhours;
/*      */   }
/*      */   
/*      */   public String getEndminutes()
/*      */   {
/* 2527 */     return this.endminutes;
/*      */   }
/*      */   
/*      */   public void setEndminutes(String endminutes)
/*      */   {
/* 2532 */     this.endminutes = endminutes;
/*      */   }
/*      */   
/*      */   public String getWorkingdays()
/*      */   {
/* 2537 */     return this.workingdays;
/*      */   }
/*      */   
/*      */   public void setWorkingdays(String workingdays)
/*      */   {
/* 2542 */     this.workingdays = workingdays;
/*      */   }
/*      */   
/*      */   public String getMonday()
/*      */   {
/* 2547 */     return this.monday;
/*      */   }
/*      */   
/*      */   public void setMonday(String monday)
/*      */   {
/* 2552 */     this.monday = monday;
/*      */   }
/*      */   
/*      */   public String getTuesday()
/*      */   {
/* 2557 */     return this.tuesday;
/*      */   }
/*      */   
/*      */   public void setTuesday(String tuesday)
/*      */   {
/* 2562 */     this.tuesday = tuesday;
/*      */   }
/*      */   
/*      */   public String getWednesday()
/*      */   {
/* 2567 */     return this.wednesday;
/*      */   }
/*      */   
/*      */   public void setWednesday(String wednesday)
/*      */   {
/* 2572 */     this.wednesday = wednesday;
/*      */   }
/*      */   
/*      */   public String getThursday()
/*      */   {
/* 2577 */     return this.thursday;
/*      */   }
/*      */   
/*      */   public void setThursday(String thursday) {
/* 2581 */     this.thursday = thursday;
/*      */   }
/*      */   
/*      */   public String getFriday()
/*      */   {
/* 2586 */     return this.friday;
/*      */   }
/*      */   
/*      */   public void setFriday(String friday)
/*      */   {
/* 2591 */     this.friday = friday;
/*      */   }
/*      */   
/*      */   public String getSaturday()
/*      */   {
/* 2596 */     return this.saturday;
/*      */   }
/*      */   
/*      */   public void setSaturday(String saturday)
/*      */   {
/* 2601 */     this.saturday = saturday;
/*      */   }
/*      */   
/*      */   public String getSunday()
/*      */   {
/* 2606 */     return this.sunday;
/*      */   }
/*      */   
/*      */   public void setSunday(String sunday) {
/* 2610 */     this.sunday = sunday;
/*      */   }
/*      */   
/*      */   public String getSave()
/*      */   {
/* 2615 */     return this.save;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 2620 */   public void setSave(String save) { this.save = save; }
/*      */   
/* 2622 */   private String comment = "";
/*      */   
/* 2624 */   public String getComment() { return this.comment; }
/*      */   
/*      */ 
/*      */   public void setComment(String comment)
/*      */   {
/* 2629 */     this.comment = comment;
/*      */   }
/*      */   
/*      */   public String getCancel()
/*      */   {
/* 2634 */     return this.cancel;
/*      */   }
/*      */   
/*      */   public void setCancel(String cancel)
/*      */   {
/* 2639 */     this.cancel = cancel;
/*      */   }
/*      */   
/*      */ 
/*      */   private String slaname;
/*      */   
/*      */   private String sladesc;
/* 2646 */   private String slaapplication = "app";
/*      */   private String slasystem;
/*      */   private String slaticket;
/* 2649 */   private String appvalue = "99.9";
/*      */   private String sysvalue;
/* 2651 */   private String ticketvalue = "10";
/*      */   private String appoperator;
/*      */   private String sysoperator;
/*      */   private String ticketoperator;
/* 2655 */   private ArrayList toAdd = new ArrayList();
/* 2656 */   private ArrayList present = new ArrayList();
/* 2657 */   private ArrayList actionAdd = new ArrayList();
/* 2658 */   private ArrayList actionPresent = new ArrayList();
/* 2659 */   private ArrayList systemAdd = new ArrayList();
/* 2660 */   private ArrayList systemPresent = new ArrayList();
/*      */   private String duration;
/*      */   private String mailcheck;
/*      */   private String slamail;
/*      */   private String disablemail;
/*      */   private String actionname;
/* 2666 */   private String from = Constants.EMAIL_ADDRESS;
/*      */   private String to;
/* 2668 */   private String subject = FormatUtil.getString("am.webclient.managermail.bsm.subject.text");
/* 2669 */   private String message = FormatUtil.getString("am.webclient.managermail.bsm.message.text");
/*      */   private String availablity;
/* 2671 */   private String emailReport = "pdf";
/*      */   
/*      */ 
/* 2674 */   private String[] slaCombo = new String[0];
/* 2675 */   private String[] slaCombo1 = new String[0];
/*      */   
/* 2677 */   private String[] systemCombo1 = new String[0];
/* 2678 */   private String[] systemCombo2 = new String[0];
/*      */   private String displayname;
/*      */   private String pdfAttributeName;
/*      */   
/* 2682 */   public String getSlaname() { return this.slaname; }
/*      */   
/*      */ 
/*      */   public void setSlaname(String slaname)
/*      */   {
/* 2687 */     this.slaname = slaname;
/*      */   }
/*      */   
/*      */   public String getSladesc()
/*      */   {
/* 2692 */     return this.sladesc;
/*      */   }
/*      */   
/*      */   public void setSladesc(String sladesc)
/*      */   {
/* 2697 */     this.sladesc = sladesc;
/*      */   }
/*      */   
/*      */   public String getSlaapplication()
/*      */   {
/* 2702 */     return this.slaapplication;
/*      */   }
/*      */   
/*      */   public void setSlaapplication(String slaapplication)
/*      */   {
/* 2707 */     this.slaapplication = slaapplication;
/*      */   }
/*      */   
/*      */   public String getSlasystem()
/*      */   {
/* 2712 */     return this.slasystem;
/*      */   }
/*      */   
/*      */   public void setSlasystem(String slasystem)
/*      */   {
/* 2717 */     this.slasystem = slasystem;
/*      */   }
/*      */   
/*      */   public String getSlaticket()
/*      */   {
/* 2722 */     return this.slaticket;
/*      */   }
/*      */   
/*      */   public void setSlaticket(String slaticket)
/*      */   {
/* 2727 */     this.slaticket = slaticket;
/*      */   }
/*      */   
/*      */   public String getAppvalue() {
/* 2731 */     return this.appvalue;
/*      */   }
/*      */   
/*      */   public void setAppvalue(String appvalue)
/*      */   {
/* 2736 */     this.appvalue = appvalue;
/*      */   }
/*      */   
/*      */   public String getSysvalue()
/*      */   {
/* 2741 */     return this.sysvalue;
/*      */   }
/*      */   
/*      */   public void setSysvalue(String sysvalue)
/*      */   {
/* 2746 */     this.sysvalue = sysvalue;
/*      */   }
/*      */   
/*      */   public String getTicketvalue()
/*      */   {
/* 2751 */     return this.ticketvalue;
/*      */   }
/*      */   
/*      */   public void setTicketvalue(String ticketvalue)
/*      */   {
/* 2756 */     this.ticketvalue = ticketvalue;
/*      */   }
/*      */   
/*      */   public String getAppoperator()
/*      */   {
/* 2761 */     return this.appoperator;
/*      */   }
/*      */   
/*      */   public void setAppoperator(String appoperator)
/*      */   {
/* 2766 */     this.appoperator = appoperator;
/*      */   }
/*      */   
/*      */   public String getSysoperator()
/*      */   {
/* 2771 */     return this.sysoperator;
/*      */   }
/*      */   
/*      */   public void setSysoperator(String sysoperator)
/*      */   {
/* 2776 */     this.sysoperator = sysoperator;
/*      */   }
/*      */   
/*      */   public String getTicketoperator()
/*      */   {
/* 2781 */     return this.ticketoperator;
/*      */   }
/*      */   
/*      */   public void setTicketoperator(String ticketoperator)
/*      */   {
/* 2786 */     this.ticketoperator = ticketoperator;
/*      */   }
/*      */   
/*      */   public void setToAdd(ArrayList toAdd)
/*      */   {
/* 2791 */     this.toAdd = toAdd;
/*      */   }
/*      */   
/*      */   public ArrayList getToAdd()
/*      */   {
/* 2796 */     return this.toAdd;
/*      */   }
/*      */   
/*      */   public String[] getSlaCombo()
/*      */   {
/* 2801 */     return this.slaCombo;
/*      */   }
/*      */   
/*      */   public void setSlaCombo(String[] slaCombo)
/*      */   {
/* 2806 */     this.slaCombo = slaCombo;
/*      */   }
/*      */   
/*      */   public String getDuration()
/*      */   {
/* 2811 */     return this.duration;
/*      */   }
/*      */   
/*      */   public void setDuration(String duration)
/*      */   {
/* 2816 */     this.duration = duration;
/*      */   }
/*      */   
/*      */   public String getMailcheck()
/*      */   {
/* 2821 */     return this.mailcheck;
/*      */   }
/*      */   
/*      */   public void setMailcheck(String mailcheck)
/*      */   {
/* 2826 */     this.mailcheck = mailcheck;
/*      */   }
/*      */   
/*      */   public String getSlamail()
/*      */   {
/* 2831 */     return this.slamail;
/*      */   }
/*      */   
/*      */   public void setSlamail(String slamail)
/*      */   {
/* 2836 */     this.slamail = slamail;
/*      */   }
/*      */   
/*      */   public String[] getSlaCombo1()
/*      */   {
/* 2841 */     return this.slaCombo1;
/*      */   }
/*      */   
/*      */   public void setSlaCombo1(String[] slaCombo1)
/*      */   {
/* 2846 */     this.slaCombo1 = slaCombo1;
/*      */   }
/*      */   
/*      */   public ArrayList getPresent()
/*      */   {
/* 2851 */     return this.present;
/*      */   }
/*      */   
/*      */   public void setPresent(ArrayList present)
/*      */   {
/* 2856 */     this.present = present;
/*      */   }
/*      */   
/*      */   public ArrayList getActionAdd()
/*      */   {
/* 2861 */     return this.actionAdd;
/*      */   }
/*      */   
/*      */   public void setActionAdd(ArrayList actionAdd)
/*      */   {
/* 2866 */     this.actionAdd = actionAdd;
/*      */   }
/*      */   
/*      */   public ArrayList getActionPresent()
/*      */   {
/* 2871 */     return this.actionPresent;
/*      */   }
/*      */   
/*      */   public void setActionPresent(ArrayList actionPresent)
/*      */   {
/* 2876 */     this.actionPresent = actionPresent;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getActionname()
/*      */   {
/* 2882 */     return this.actionname;
/*      */   }
/*      */   
/*      */   public void setActionname(String actionname)
/*      */   {
/* 2887 */     this.actionname = actionname;
/*      */   }
/*      */   
/*      */   public String getFrom()
/*      */   {
/* 2892 */     return this.from;
/*      */   }
/*      */   
/*      */   public void setFrom(String from)
/*      */   {
/* 2897 */     this.from = from;
/*      */   }
/*      */   
/*      */   public String getTo()
/*      */   {
/* 2902 */     return this.to;
/*      */   }
/*      */   
/*      */   public void setTo(String to)
/*      */   {
/* 2907 */     this.to = to;
/*      */   }
/*      */   
/*      */   public String getSubject()
/*      */   {
/* 2912 */     return this.subject;
/*      */   }
/*      */   
/*      */   public void setSubject(String subject)
/*      */   {
/* 2917 */     this.subject = subject;
/*      */   }
/*      */   
/*      */   public String getMessage()
/*      */   {
/* 2922 */     return this.message;
/*      */   }
/*      */   
/*      */   public void setMessage(String message)
/*      */   {
/* 2927 */     this.message = message;
/*      */   }
/*      */   
/*      */   public ArrayList getSystemAdd()
/*      */   {
/* 2932 */     return this.systemAdd;
/*      */   }
/*      */   
/*      */   public void setSystemAdd(ArrayList systemAdd)
/*      */   {
/* 2937 */     this.systemAdd = systemAdd;
/*      */   }
/*      */   
/*      */   public ArrayList getSystemPresent()
/*      */   {
/* 2942 */     return this.systemPresent;
/*      */   }
/*      */   
/*      */   public void setSystemPresent(ArrayList systemPresent)
/*      */   {
/* 2947 */     this.systemPresent = systemPresent;
/*      */   }
/*      */   
/*      */   public String[] getSystemCombo1()
/*      */   {
/* 2952 */     return this.systemCombo1;
/*      */   }
/*      */   
/*      */   public void setSystemCombo1(String[] systemCombo1)
/*      */   {
/* 2957 */     this.systemCombo1 = systemCombo1;
/*      */   }
/*      */   
/*      */   public String[] getSystemCombo2()
/*      */   {
/* 2962 */     return this.systemCombo2;
/*      */   }
/*      */   
/*      */   public void setSystemCombo2(String[] systemCombo2)
/*      */   {
/* 2967 */     this.systemCombo2 = systemCombo2;
/*      */   }
/*      */   
/*      */   public String getAvailablity()
/*      */   {
/* 2972 */     return this.availablity;
/*      */   }
/*      */   
/*      */   public void setAvailablity(String availablity)
/*      */   {
/* 2977 */     this.availablity = availablity;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getReporttype()
/*      */   {
/* 2983 */     return this.reporttype;
/*      */   }
/*      */   
/*      */   public void setReporttype(String reporttype) {
/* 2987 */     this.reporttype = reporttype;
/*      */   }
/*      */   
/*      */   public String getDisablemail()
/*      */   {
/* 2992 */     return this.disablemail;
/*      */   }
/*      */   
/*      */   public void setDisablemail(String disablemail)
/*      */   {
/* 2997 */     this.disablemail = disablemail;
/*      */   }
/*      */   
/*      */   public String getReport()
/*      */   {
/* 3002 */     return this.report;
/*      */   }
/*      */   
/*      */   public void setReport(String report)
/*      */   {
/* 3007 */     this.report = report;
/*      */   }
/*      */   
/*      */   public String getDisplayname()
/*      */   {
/* 3012 */     return this.displayname;
/*      */   }
/*      */   
/*      */   public void setDisplayname(String displayname)
/*      */   {
/* 3017 */     this.displayname = displayname;
/*      */   }
/*      */   
/*      */   public String getPdfAttributeName()
/*      */   {
/* 3022 */     return this.pdfAttributeName;
/*      */   }
/*      */   
/*      */   public void setPdfAttributeName(String pdfAttributeName)
/*      */   {
/* 3027 */     this.pdfAttributeName = pdfAttributeName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] getSlavalue()
/*      */   {
/* 3035 */     return this.slavalue;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSlavalue(String[] slavalue)
/*      */   {
/* 3043 */     this.slavalue = slavalue;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getEmailReport()
/*      */   {
/* 3051 */     return this.emailReport;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEmailReport(String emailReport)
/*      */   {
/* 3059 */     this.emailReport = emailReport;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getLaststart()
/*      */   {
/* 3067 */     return this.laststart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLaststart(String laststart)
/*      */   {
/* 3075 */     this.laststart = laststart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getLastend()
/*      */   {
/* 3083 */     return this.lastend;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLastend(String lastend)
/*      */   {
/* 3091 */     this.lastend = lastend;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getThisend()
/*      */   {
/* 3099 */     return this.thisend;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setThisend(String thisend)
/*      */   {
/* 3107 */     this.thisend = thisend;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getThisstart()
/*      */   {
/* 3115 */     return this.thisstart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setThisstart(String thisstart)
/*      */   {
/* 3123 */     this.thisstart = thisstart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getApplicationAttribute()
/*      */   {
/* 3131 */     return this.applicationAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setApplicationAttribute(String applicationAttribute)
/*      */   {
/* 3139 */     this.applicationAttribute = applicationAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getAppArrayAttribute()
/*      */   {
/* 3148 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3151 */       Map hash = new HashMap();
/* 3152 */       if (DBUtil.resourceTypeList.containsKey("APP")) {
/* 3153 */         hash = ReportUtilities.getAttributesForResourceGroups("APP");
/*      */       } else {
/* 3155 */         hash = ReportUtilities.getAttributesForApplicationServer();
/*      */       }
/*      */       
/* 3158 */       Collection c = hash.keySet();
/* 3159 */       Iterator itr = c.iterator();
/*      */       
/* 3161 */       while (itr.hasNext()) {
/* 3162 */         String key = itr.next().toString();
/* 3163 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3165 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3166 */           if (a1.size() > 0) {
/* 3167 */             Properties p1 = new Properties();
/* 3168 */             p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3169 */             p1.setProperty("value", "resource");
/* 3170 */             value.add(p1);
/*      */             
/* 3172 */             for (int i = 0; i < a1.size(); i++) {
/* 3173 */               Properties p2 = (Properties)a1.get(i);
/* 3174 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3181 */       ex.printStackTrace();
/*      */     }
/* 3183 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAppArrayAttribute(ArrayList appArrayAttribute)
/*      */   {
/* 3191 */     this.appArrayAttribute = appArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getServerAttribute()
/*      */   {
/* 3199 */     return this.serverAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setServerAttribute(String serverAttribute)
/*      */   {
/* 3207 */     this.serverAttribute = serverAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getServerArrayAttribute()
/*      */   {
/* 3217 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3220 */       Map hash = new HashMap();
/* 3221 */       if (DBUtil.resourceTypeList.containsKey("SYS")) {
/* 3222 */         hash = ReportUtilities.getAttributesForResourceGroups("SYS");
/*      */       } else {
/* 3224 */         hash = ReportUtilities.getAttributesForSystemServer();
/*      */       }
/*      */       
/* 3227 */       Collection c = hash.keySet();
/* 3228 */       Iterator itr = c.iterator();
/*      */       
/* 3230 */       while (itr.hasNext()) {
/* 3231 */         String key = itr.next().toString();
/* 3232 */         if (moTypeCount.containsKey(key))
/*      */         {
/* 3234 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3235 */           if (a1.size() > 0) {
/* 3236 */             Properties p1 = new Properties();
/* 3237 */             p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3238 */             p1.setProperty("value", "resource");
/* 3239 */             value.add(p1);
/* 3240 */             for (int i = 0; i < a1.size(); i++)
/*      */             {
/* 3242 */               Properties p2 = (Properties)a1.get(i);
/* 3243 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3250 */       ex.printStackTrace();
/*      */     }
/* 3252 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setServerArrayAttribute(ArrayList serverArrayAttribute)
/*      */   {
/* 3260 */     this.serverArrayAttribute = serverArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDbAttribute()
/*      */   {
/* 3268 */     return this.dbAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDbAttribute(String dbAttribute)
/*      */   {
/* 3276 */     this.dbAttribute = dbAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getDbArrayAttribute()
/*      */   {
/* 3284 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3287 */       Map hash = new HashMap();
/* 3288 */       if (DBUtil.resourceTypeList.containsKey("DBS")) {
/* 3289 */         hash = ReportUtilities.getAttributesForResourceGroups("DBS");
/*      */       } else {
/* 3291 */         hash = ReportUtilities.getAttributesForDatabaseServer();
/*      */       }
/*      */       
/* 3294 */       Collection c = hash.keySet();
/* 3295 */       Iterator itr = c.iterator();
/*      */       
/* 3297 */       while (itr.hasNext()) {
/* 3298 */         String key = itr.next().toString();
/* 3299 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3301 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3302 */           if (a1.size() > 0) {
/* 3303 */             Properties p1 = new Properties();
/* 3304 */             if (!Constants.sqlManager)
/*      */             {
/* 3306 */               p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3307 */               p1.setProperty("value", "resource");
/* 3308 */               value.add(p1);
/*      */             }
/*      */             
/*      */ 
/* 3312 */             for (int i = 0; i < a1.size(); i++) {
/* 3313 */               Properties p2 = (Properties)a1.get(i);
/*      */               
/* 3315 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3322 */       ex.printStackTrace();
/*      */     }
/* 3324 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDbArrayAttribute(ArrayList dbArrayAttribute)
/*      */   {
/* 3332 */     this.dbArrayAttribute = dbArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getWebserviceAttribute()
/*      */   {
/* 3340 */     return this.webserviceAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWebserviceAttribute(String webserviceAttribute)
/*      */   {
/* 3348 */     this.webserviceAttribute = webserviceAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getWebserverAttribute()
/*      */   {
/* 3356 */     return this.webserverAttribute;
/*      */   }
/*      */   
/*      */   public String getVirserverAttribute() {
/* 3360 */     return this.virserverAttribute;
/*      */   }
/*      */   
/* 3363 */   public void setVirserverAttribute(String virserverAttribute) { this.virserverAttribute = virserverAttribute; }
/*      */   
/*      */   public String getCloudAppsAttribute()
/*      */   {
/* 3367 */     return this.cloudAppsAttribute;
/*      */   }
/*      */   
/* 3370 */   public void setCloudAppsAttribute(String cloudAppsAttribute) { this.cloudAppsAttribute = cloudAppsAttribute; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWebserverAttribute(String webserverAttribute)
/*      */   {
/* 3379 */     this.webserverAttribute = webserverAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getUrlsAttribute()
/*      */   {
/* 3387 */     return this.urlsAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setUrlsAttribute(String urlsAttribute)
/*      */   {
/* 3395 */     this.urlsAttribute = urlsAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getWebserviceArrayAttribute()
/*      */   {
/* 3403 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3406 */       Map hash = ReportUtilities.getAttributesForWebServicesServer();
/*      */       
/* 3408 */       Collection c = hash.keySet();
/* 3409 */       Iterator itr = c.iterator();
/*      */       
/* 3411 */       while (itr.hasNext()) {
/* 3412 */         String key = itr.next().toString();
/*      */         
/* 3414 */         ArrayList a1 = (ArrayList)hash.get(key);
/* 3415 */         if (a1.size() > 0) {
/* 3416 */           Properties p1 = new Properties();
/* 3417 */           p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3418 */           p1.setProperty("value", "resource");
/* 3419 */           value.add(p1);
/*      */           
/*      */ 
/* 3422 */           for (int i = 0; i < a1.size(); i++) {
/* 3423 */             Properties p2 = (Properties)a1.get(i);
/*      */             
/*      */ 
/* 3426 */             value.add(p2);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3433 */       ex.printStackTrace();
/*      */     }
/* 3435 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWebserviceArrayAttribute(ArrayList webserviceArrayAttribute)
/*      */   {
/* 3443 */     this.webserviceArrayAttribute = webserviceArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getWebserverArrayAttribute()
/*      */   {
/* 3451 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3454 */       Map hash = ReportUtilities.getAttributesForWebServer();
/* 3455 */       Collection c = hash.keySet();
/* 3456 */       Iterator itr = c.iterator();
/*      */       
/* 3458 */       while (itr.hasNext()) {
/* 3459 */         String key = itr.next().toString();
/* 3460 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3462 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3463 */           if (a1.size() > 0) {
/* 3464 */             Properties p1 = new Properties();
/* 3465 */             p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3466 */             p1.setProperty("value", "resource");
/* 3467 */             value.add(p1);
/*      */             
/*      */ 
/* 3470 */             for (int i = 0; i < a1.size(); i++) {
/* 3471 */               Properties p2 = (Properties)a1.get(i);
/*      */               
/*      */ 
/* 3474 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3481 */       ex.printStackTrace();
/*      */     }
/* 3483 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getVirserverArrayAttribute()
/*      */   {
/* 3491 */     ArrayList value = new ArrayList();
/*      */     try {
/* 3493 */       Map hash = new HashMap();
/* 3494 */       if (DBUtil.resourceTypeList.containsKey("VIR")) {
/* 3495 */         hash = ReportUtilities.getAttributesForResourceGroups("VIR");
/*      */       } else {
/* 3497 */         hash = ReportUtilities.getAttributesForCategory("VIR");
/*      */       }
/*      */       
/* 3500 */       Collection c = hash.keySet();
/* 3501 */       Iterator itr = c.iterator();
/*      */       
/* 3503 */       while (itr.hasNext())
/*      */       {
/* 3505 */         String key = itr.next().toString();
/* 3506 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3508 */           boolean addtoreturn = true;
/* 3509 */           if ((key.equals("VirtualMachine")) && (!moDisNamCount.containsKey(key)))
/*      */           {
/* 3511 */             addtoreturn = false;
/*      */           }
/* 3513 */           if (addtoreturn)
/*      */           {
/* 3515 */             ArrayList a1 = (ArrayList)hash.get(key);
/* 3516 */             if (a1.size() > 0)
/*      */             {
/* 3518 */               Properties p1 = new Properties();
/* 3519 */               p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3520 */               p1.setProperty("value", "resource");
/* 3521 */               value.add(p1);
/* 3522 */               for (int i = 0; i < a1.size(); i++)
/*      */               {
/* 3524 */                 Properties p2 = (Properties)a1.get(i);
/*      */                 
/*      */ 
/* 3527 */                 value.add(p2);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3535 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 3538 */     value.addAll(getVMClusterAttributes());
/* 3539 */     value.addAll(getVMRPoolAttributes());
/* 3540 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getCloudAppsArrayAttribute()
/*      */   {
/* 3548 */     ArrayList value = new ArrayList();
/*      */     try {
/* 3550 */       Map hash = new HashMap();
/* 3551 */       if (DBUtil.resourceTypeList.containsKey("CLD")) {
/* 3552 */         hash = ReportUtilities.getAttributesForResourceGroups("CLD");
/*      */       } else {
/* 3554 */         hash = ReportUtilities.getAttributesForCategory("CLD");
/*      */       }
/* 3556 */       Collection c = hash.keySet();
/* 3557 */       Iterator itr = c.iterator();
/*      */       
/* 3559 */       while (itr.hasNext())
/*      */       {
/* 3561 */         String key = itr.next().toString();
/* 3562 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3564 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3565 */           if (a1.size() > 0)
/*      */           {
/* 3567 */             Properties p1 = new Properties();
/* 3568 */             p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3569 */             p1.setProperty("value", "resource");
/* 3570 */             value.add(p1);
/* 3571 */             for (int i = 0; i < a1.size(); i++)
/*      */             {
/* 3573 */               Properties p2 = (Properties)a1.get(i);
/*      */               
/*      */ 
/* 3576 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3583 */       ex.printStackTrace();
/*      */     }
/* 3585 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVirserverArrayAttribute(ArrayList virserverArrayAttribute)
/*      */   {
/* 3595 */     this.virserverArrayAttribute = virserverArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCloudAppsArrayAttribute(ArrayList cloudAppsArrayAttribute)
/*      */   {
/* 3605 */     this.cloudAppsArrayAttribute = cloudAppsArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWebserverArrayAttribute(ArrayList webserverArrayAttribute)
/*      */   {
/* 3615 */     this.webserverArrayAttribute = webserverArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getUrlsArrayAttribute()
/*      */   {
/* 3623 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3626 */       Map hash = new HashMap();
/* 3627 */       if (DBUtil.resourceTypeList.containsKey("URL")) {
/* 3628 */         hash = ReportUtilities.getAttributesForResourceGroups("URL");
/*      */       } else {
/* 3630 */         hash = ReportUtilities.getAttributesForURLs();
/*      */       }
/*      */       
/* 3633 */       Collection c = hash.keySet();
/* 3634 */       Iterator itr = c.iterator();
/*      */       
/* 3636 */       while (itr.hasNext()) {
/* 3637 */         String key = itr.next().toString();
/* 3638 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3640 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3641 */           if (a1.size() > 0) {
/* 3642 */             Properties p1 = new Properties();
/* 3643 */             p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3644 */             p1.setProperty("value", "resource");
/* 3645 */             value.add(p1);
/*      */             
/*      */ 
/* 3648 */             for (int i = 0; i < a1.size(); i++) {
/* 3649 */               Properties p2 = (Properties)a1.get(i);
/*      */               
/*      */ 
/* 3652 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3660 */       ex.printStackTrace();
/*      */     }
/* 3662 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setUrlsArrayAttribute(ArrayList urlsArrayAttribute)
/*      */   {
/* 3670 */     this.urlsArrayAttribute = urlsArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getServicesAttribute()
/*      */   {
/* 3678 */     return this.servicesAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setServicesAttribute(String servicesAttribute)
/*      */   {
/* 3686 */     this.servicesAttribute = servicesAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getServicesArrayAttribute()
/*      */   {
/* 3694 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3697 */       Map hash = new HashMap();
/* 3698 */       if (DBUtil.resourceTypeList.containsKey("SER")) {
/* 3699 */         hash = ReportUtilities.getAttributesForResourceGroups("SER");
/*      */       } else {
/* 3701 */         hash = ReportUtilities.getAttributesForServicesServer();
/*      */       }
/*      */       
/* 3704 */       Collection c = hash.keySet();
/* 3705 */       Iterator itr = c.iterator();
/*      */       
/* 3707 */       while (itr.hasNext()) {
/* 3708 */         String key = itr.next().toString();
/* 3709 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3711 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3712 */           if (a1.size() > 0) {
/* 3713 */             Properties p1 = new Properties();
/* 3714 */             p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3715 */             p1.setProperty("value", "resource");
/* 3716 */             value.add(p1);
/*      */             
/*      */ 
/* 3719 */             for (int i = 0; i < a1.size(); i++) {
/* 3720 */               Properties p2 = (Properties)a1.get(i);
/*      */               
/* 3722 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3729 */       ex.printStackTrace();
/*      */     }
/* 3731 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setServicesArrayAttribute(ArrayList servicesArrayAttribute)
/*      */   {
/* 3739 */     this.servicesArrayAttribute = servicesArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMailserverAttribute()
/*      */   {
/* 3747 */     return this.mailserverAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMailserverAttribute(String mailserverAttribute)
/*      */   {
/* 3755 */     this.mailserverAttribute = mailserverAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getMailserverArrayAttribute()
/*      */   {
/* 3763 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3766 */       Map hash = new HashMap();
/* 3767 */       if (DBUtil.resourceTypeList.containsKey("MS")) {
/* 3768 */         hash = ReportUtilities.getAttributesForResourceGroups("MS");
/*      */       } else {
/* 3770 */         hash = ReportUtilities.getAttributesForMailServer();
/*      */       }
/*      */       
/* 3773 */       Collection c = hash.keySet();
/* 3774 */       Iterator itr = c.iterator();
/*      */       
/* 3776 */       while (itr.hasNext()) {
/* 3777 */         String key = itr.next().toString();
/* 3778 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3780 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3781 */           if (a1.size() > 0) {
/* 3782 */             Properties p1 = new Properties();
/* 3783 */             p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3784 */             p1.setProperty("value", "resource");
/* 3785 */             value.add(p1);
/*      */             
/*      */ 
/* 3788 */             for (int i = 0; i < a1.size(); i++) {
/* 3789 */               Properties p2 = (Properties)a1.get(i);
/*      */               
/* 3791 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 3798 */       ex.printStackTrace();
/*      */     }
/* 3800 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMailserverArrayAttribute(ArrayList mailserverArrayAttribute)
/*      */   {
/* 3808 */     this.mailserverArrayAttribute = mailserverArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTransactionAttribute()
/*      */   {
/* 3816 */     return this.transactionAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTransactionAttribute(String transactionAttribute)
/*      */   {
/* 3824 */     this.transactionAttribute = transactionAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getTransactionArrayAttribute()
/*      */   {
/* 3832 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3835 */       Map hash = new HashMap();
/* 3836 */       if (DBUtil.resourceTypeList.containsKey("TM")) {
/* 3837 */         hash = ReportUtilities.getAttributesForResourceGroups("TM");
/*      */       } else {
/* 3839 */         hash = ReportUtilities.getAttributesForTransactionServer();
/*      */       }
/*      */       
/* 3842 */       Collection c = hash.keySet();
/* 3843 */       Iterator itr = c.iterator();
/*      */       
/* 3845 */       while (itr.hasNext()) {
/* 3846 */         String key = itr.next().toString();
/*      */         
/* 3848 */         ArrayList a1 = (ArrayList)hash.get(key);
/* 3849 */         if (a1.size() > 0) {
/* 3850 */           Properties p1 = new Properties();
/* 3851 */           p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3852 */           p1.setProperty("value", "resource");
/* 3853 */           value.add(p1);
/*      */           
/*      */ 
/* 3856 */           for (int i = 0; i < a1.size(); i++) {
/* 3857 */             Properties p2 = (Properties)a1.get(i);
/*      */             
/*      */ 
/* 3860 */             value.add(p2);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3867 */       ex.printStackTrace();
/*      */     }
/* 3869 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTransactionArrayAttribute(ArrayList transactionArrayAttribute)
/*      */   {
/* 3877 */     this.transactionArrayAttribute = transactionArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getErpAttribute()
/*      */   {
/* 3885 */     return this.erpAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setErpAttribute(String erpAttribute)
/*      */   {
/* 3893 */     this.erpAttribute = erpAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getErpArrayAttribute()
/*      */   {
/* 3901 */     ArrayList value = new ArrayList();
/*      */     try
/*      */     {
/* 3904 */       Map hash = new HashMap();
/* 3905 */       if (DBUtil.resourceTypeList.containsKey("ERP")) {
/* 3906 */         hash = ReportUtilities.getAttributesForResourceGroups("ERP");
/*      */       } else {
/* 3908 */         hash = ReportUtilities.getAttributesForERPServer();
/*      */       }
/*      */       
/* 3911 */       Collection c = hash.keySet();
/* 3912 */       Iterator itr = c.iterator();
/*      */       
/* 3914 */       while (itr.hasNext()) {
/* 3915 */         String key = itr.next().toString();
/* 3916 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3918 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3919 */           if (a1.size() > 0) {
/* 3920 */             Properties p1 = new Properties();
/* 3921 */             p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3922 */             p1.setProperty("value", "resource");
/* 3923 */             value.add(p1);
/*      */             
/* 3925 */             for (int i = 0; i < a1.size(); i++) {
/* 3926 */               Properties p2 = (Properties)a1.get(i);
/* 3927 */               if (p2.getProperty("label").length() > 80) {
/* 3928 */                 p2.setProperty("label", FormatUtil.getTrimmedText(p2.getProperty("label"), 80));
/*      */               }
/* 3930 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3938 */       ex.printStackTrace();
/*      */     }
/* 3940 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setErpArrayAttribute(ArrayList erpArrayAttribute)
/*      */   {
/* 3948 */     this.erpArrayAttribute = erpArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMomAttribute()
/*      */   {
/* 3956 */     return this.momAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMomAttribute(String momAttribute)
/*      */   {
/* 3964 */     this.momAttribute = momAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getMomArrayAttribute()
/*      */   {
/* 3972 */     ArrayList value = new ArrayList();
/*      */     try {
/* 3974 */       Map hash = new HashMap();
/* 3975 */       if (DBUtil.resourceTypeList.containsKey("MOM")) {
/* 3976 */         hash = ReportUtilities.getAttributesForResourceGroups("MOM");
/*      */       } else {
/* 3978 */         hash = ReportUtilities.getAttributesForMiddlewareServer();
/*      */       }
/*      */       
/* 3981 */       Collection c = hash.keySet();
/* 3982 */       Iterator itr = c.iterator();
/*      */       
/* 3984 */       while (itr.hasNext()) {
/* 3985 */         String key = itr.next().toString();
/* 3986 */         if ((moTypeCount.containsKey(key)) || (moDisNamCount.containsKey(key)))
/*      */         {
/* 3988 */           ArrayList a1 = (ArrayList)hash.get(key);
/* 3989 */           if (a1.size() > 0) {
/* 3990 */             Properties p1 = new Properties();
/* 3991 */             p1.setProperty("label", "----" + FormatUtil.getString(key) + "------");
/* 3992 */             p1.setProperty("value", "resource");
/* 3993 */             value.add(p1);
/*      */             
/*      */ 
/* 3996 */             for (int i = 0; i < a1.size(); i++) {
/* 3997 */               Properties p2 = (Properties)a1.get(i);
/*      */               
/* 3999 */               value.add(p2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 4006 */       ex.printStackTrace();
/*      */     }
/* 4008 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMomArrayAttribute(ArrayList momArrayAttribute)
/*      */   {
/* 4016 */     this.momArrayAttribute = momArrayAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 4021 */   private String sevenThirtyAttrib = null;
/*      */   private ArrayList sevenThirtyAttribCln;
/*      */   
/* 4024 */   public String getSevenThirtyAttrib() { return this.sevenThirtyAttrib; }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setSevenThirtyAttrib(String sevenThirtyAttrib)
/*      */   {
/* 4030 */     this.sevenThirtyAttrib = sevenThirtyAttrib;
/*      */   }
/*      */   
/*      */   public void setSevenThirtyAttribCln(ArrayList attribs)
/*      */   {
/* 4035 */     this.sevenThirtyAttribCln = attribs;
/*      */   }
/*      */   
/*      */   public ArrayList getSevenThirtyAttribCln() {
/* 4039 */     return this.sevenThirtyAttribCln;
/*      */   }
/*      */   
/*      */ 
/* 4043 */   private String allPerformanceAttrbs = null;
/*      */   
/* 4045 */   public void setAllPerformanceAttrbs(String s) { this.allPerformanceAttrbs = s; }
/*      */   
/*      */   public String getAllPerformanceAttrbs() {
/* 4048 */     return this.allPerformanceAttrbs;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setAllPerformanceAttrbsCln(ArrayList attribs)
/*      */   {
/* 4054 */     this.allPerformanceAttrbsCln = attribs;
/*      */   }
/*      */   
/*      */   public ArrayList getAllPerformanceAttrbsCln() {
/* 4058 */     return this.allPerformanceAttrbsCln;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setIsUserOpr(boolean isUserOpr)
/*      */   {
/* 4064 */     isUserOpr = isUserOpr;
/*      */   }
/*      */   
/*      */   public void setRemoteUser(String remoteUser)
/*      */   {
/* 4069 */     remoteUser = remoteUser;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ArrayList allPerformanceAttrbsCln;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] slavalue;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 4086 */   public String laststart = "";
/* 4087 */   public String lastend = "";
/* 4088 */   public String thisstart = "";
/* 4089 */   public String thisend = "";
/*      */   
/*      */   private String applicationAttribute;
/*      */   
/*      */   private ArrayList appArrayAttribute;
/*      */   
/*      */   private String serverAttribute;
/*      */   
/*      */   private ArrayList serverArrayAttribute;
/*      */   
/*      */   private String dbAttribute;
/*      */   
/*      */   private ArrayList dbArrayAttribute;
/*      */   
/*      */   private String webserviceAttribute;
/*      */   
/*      */   private ArrayList webserviceArrayAttribute;
/*      */   
/*      */   private String webserverAttribute;
/*      */   
/*      */   private ArrayList webserverArrayAttribute;
/*      */   
/*      */   private String urlsAttribute;
/*      */   
/*      */   private ArrayList urlsArrayAttribute;
/*      */   
/*      */   private String servicesAttribute;
/*      */   
/*      */   private ArrayList servicesArrayAttribute;
/*      */   
/*      */   private String mailserverAttribute;
/*      */   
/*      */   private ArrayList mailserverArrayAttribute;
/*      */   
/*      */   private String transactionAttribute;
/*      */   
/*      */   private ArrayList transactionArrayAttribute;
/*      */   
/*      */   private String erpAttribute;
/*      */   
/*      */   private ArrayList erpArrayAttribute;
/*      */   
/*      */   private String momAttribute;
/*      */   private ArrayList momArrayAttribute;
/*      */   private String virserverAttribute;
/*      */   private ArrayList virserverArrayAttribute;
/*      */   private String cloudAppsAttribute;
/*      */   private ArrayList cloudAppsArrayAttribute;
/*      */   private static String eumMonId;
/*      */   
/*      */   public ArrayList getVMClusterAttributes()
/*      */   {
/* 4141 */     ArrayList toReturn = new ArrayList();
/* 4142 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4143 */     ResultSet set = null;
/* 4144 */     String attributeQuery = "select AM_ATTRIBUTES.ATTRIBUTEID,DISPLAYNAME,UNITS from AM_ATTRIBUTES,AM_ATTRIBUTES_EXT  where RESOURCETYPE='HAI' and TYPE=0 and AM_ATTRIBUTES.ATTRIBUTEID not in (17,18) and AM_ATTRIBUTES.ATTRIBUTEID > 7910 and AM_ATTRIBUTES.ATTRIBUTEID < 7940 and  AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and REPORTS_ENABLED =1 and ISARCHIVEING=1";
/*      */     try
/*      */     {
/* 4147 */       Properties p1 = new Properties();
/* 4148 */       p1.setProperty("label", "----" + FormatUtil.getString("am.webclient.vmware.clusters.text") + "------");
/* 4149 */       p1.setProperty("value", "resource");
/* 4150 */       toReturn.add(p1);
/* 4151 */       set = AMConnectionPool.executeQueryStmt(attributeQuery);
/* 4152 */       while (set.next())
/*      */       {
/* 4154 */         Properties props1 = new Properties();
/* 4155 */         String aid = set.getString("ATTRIBUTEID");
/* 4156 */         String aname = FormatUtil.getString(set.getString("DISPLAYNAME").trim());
/* 4157 */         String unit = FormatUtil.getString(set.getString("UNITS"));
/* 4158 */         if ((unit != null) && (!"-".equalsIgnoreCase(unit)) && (!"".equalsIgnoreCase(unit)))
/*      */         {
/* 4160 */           aname = aname + " " + FormatUtil.getString("in") + " " + unit;
/*      */         }
/*      */         
/* 4163 */         props1.setProperty("label", aname);
/* 4164 */         props1.setProperty("value", "HAI#" + aid);
/* 4165 */         toReturn.add(props1);
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 4170 */       ee.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4174 */       if (set != null)
/*      */       {
/* 4176 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */     }
/* 4179 */     if (toReturn.size() <= 1)
/*      */     {
/* 4181 */       toReturn = new ArrayList();
/*      */     }
/* 4183 */     return toReturn;
/*      */   }
/*      */   
/*      */   public ArrayList getVMRPoolAttributes()
/*      */   {
/* 4188 */     ArrayList toReturn = new ArrayList();
/* 4189 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4190 */     ResultSet set = null;
/* 4191 */     String attributeQuery = "select AM_ATTRIBUTES.ATTRIBUTEID,DISPLAYNAME,UNITS from AM_ATTRIBUTES,AM_ATTRIBUTES_EXT  where RESOURCETYPE='HAI' and TYPE=0 and AM_ATTRIBUTES.ATTRIBUTEID > 7939 and AM_ATTRIBUTES.ATTRIBUTEID < 7950 and AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and REPORTS_ENABLED =1 and ISARCHIVEING=1";
/*      */     try
/*      */     {
/* 4194 */       Properties p1 = new Properties();
/* 4195 */       p1.setProperty("label", "----" + FormatUtil.getString("am.webclient.vmware.rpools.text") + "------");
/* 4196 */       p1.setProperty("value", "resource");
/* 4197 */       toReturn.add(p1);
/* 4198 */       set = AMConnectionPool.executeQueryStmt(attributeQuery);
/* 4199 */       while (set.next())
/*      */       {
/* 4201 */         Properties props1 = new Properties();
/* 4202 */         String aid = set.getString("ATTRIBUTEID");
/* 4203 */         String aname = FormatUtil.getString(set.getString("DISPLAYNAME").trim());
/* 4204 */         String unit = FormatUtil.getString(set.getString("UNITS"));
/* 4205 */         if ((unit != null) && (!"-".equalsIgnoreCase(unit)) && (!"".equalsIgnoreCase(unit)))
/*      */         {
/* 4207 */           aname = aname + " " + FormatUtil.getString("in") + " " + unit;
/*      */         }
/*      */         
/* 4210 */         props1.setProperty("label", aname);
/* 4211 */         props1.setProperty("value", "HAI#" + aid);
/* 4212 */         toReturn.add(props1);
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 4217 */       ee.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4221 */       if (set != null)
/*      */       {
/* 4223 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */     }
/* 4226 */     if (toReturn.size() <= 1)
/*      */     {
/* 4228 */       toReturn = new ArrayList();
/*      */     }
/* 4230 */     return toReturn;
/*      */   }
/*      */   
/*      */   public ArrayList getEummonitors() {
/* 4234 */     ArrayList retServers = new ArrayList();
/* 4235 */     Properties prop = (Properties)moEUMMons.get(0);
/* 4236 */     retServers.add(prop);
/* 4237 */     for (int i = 1; i < moEUMMons.size(); i++)
/*      */     {
/* 4239 */       prop = (Properties)moEUMMons.get(i);
/* 4240 */       if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */       {
/* 4242 */         retServers.add(prop);
/*      */       }
/*      */     }
/* 4245 */     com.adventnet.appmanager.logging.AMLog.debug("ReportForm.getEummonitors() ==>" + retServers);
/* 4246 */     return retServers;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setEumMonId(String monId)
/*      */   {
/* 4252 */     eumMonId = monId;
/*      */   }
/*      */   
/*      */   public String getEumMonId()
/*      */   {
/* 4257 */     return eumMonId;
/*      */   }
/*      */   
/* 4260 */   private static String isEumReport = "false";
/*      */   
/*      */   public void setEumReport(String isEumReport)
/*      */   {
/* 4264 */     isEumReport = isEumReport;
/*      */   }
/*      */   
/*      */   public String getEumReport()
/*      */   {
/* 4269 */     return isEumReport;
/*      */   }
/*      */   
/*      */   public String getMethodName() {
/* 4273 */     return this.methodName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 4278 */   public void setMethodName(String methodName1) { this.methodName = methodName1; }
/*      */   
/* 4280 */   private String reportmethod = "generateUnderSizedMonitors";
/*      */   
/*      */   public String getReportmethod() {
/* 4283 */     return this.methodName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 4288 */   public void setReportmethod(String methodName1) { this.methodName = methodName1; }
/*      */   
/* 4290 */   private String reportname = "";
/*      */   
/* 4292 */   public String getReportname() { return this.reportname; }
/*      */   
/*      */   public void setReportname(String reportname) {
/* 4295 */     this.reportname = reportname;
/*      */   }
/*      */   
/*      */   public void setCustomFormName(String customFormName) {
/* 4299 */     customFormName = customFormName;
/*      */   }
/*      */   
/*      */   public String getCustomFormName()
/*      */   {
/* 4304 */     return customFormName;
/*      */   }
/*      */   
/* 4307 */   private String capacityserver = null;
/*      */   
/* 4309 */   public String getCapacityserver() { return this.capacityserver; }
/*      */   
/*      */ 
/*      */ 
/* 4313 */   public void setCapacityServer(String capacityserver) { capacityserver = this.capacityserver; }
/*      */   
/* 4315 */   String unconfiguredAttributes = "";
/*      */   
/*      */   public void setUnconfiguredAttributes(String unconfiguredAttributes) {
/* 4318 */     this.unconfiguredAttributes = unconfiguredAttributes;
/*      */   }
/*      */   
/*      */ 
/* 4322 */   public String getUnconfiguredAttributes() { return this.unconfiguredAttributes; }
/*      */   
/* 4324 */   String attributeIDS = "";
/*      */   
/*      */   public void setAttributeIDS(String attributeIDS) {
/* 4327 */     this.attributeIDS = attributeIDS;
/*      */   }
/*      */   
/*      */ 
/* 4331 */   public String getAttributeIDS() { return this.attributeIDS; }
/*      */   
/* 4333 */   private String mgCapacity = "";
/*      */   String mondaycapacity;
/*      */   
/* 4336 */   public String getMgCapacity() { return this.mgCapacity; }
/*      */   
/*      */   public void setMgCapacity(String mgCapacity)
/*      */   {
/* 4340 */     this.mgCapacity = mgCapacity;
/*      */   }
/*      */   
/*      */   public void setVmapplications(ArrayList vmapplications) {
/* 4344 */     this.vmapplications = vmapplications;
/*      */   }
/*      */   
/*      */   public ArrayList getVmapplications() {
/* 4348 */     return this.vmapplications;
/*      */   }
/*      */   
/*      */   public String getMondaycapacity() {
/* 4352 */     return this.mondaycapacity;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 4357 */   public void setMondaycapacity(String mondaycapacity) { this.mondaycapacity = mondaycapacity; }
/*      */   
/* 4359 */   ArrayList capacityServers = null;
/*      */   
/*      */   public void setCapacityServers(ArrayList capacityServers) {
/* 4362 */     this.capacityServers = capacityServers;
/*      */   }
/*      */   
/*      */   public ArrayList getCapacityServers() {
/* 4366 */     ArrayList capacityServers = new ArrayList();
/* 4367 */     if ((moVirtualServers != null) && (moVirtualServers.size() > 0))
/*      */     {
/* 4369 */       Properties prop = (Properties)moVirtualServers.get(0);
/* 4370 */       capacityServers.add(prop);
/* 4371 */       for (int i = 1; i < moVirtualServers.size(); i++)
/*      */       {
/* 4373 */         prop = (Properties)moVirtualServers.get(i);
/* 4374 */         if (moTypeCount.containsKey(prop.getProperty("value")))
/*      */         {
/* 4376 */           capacityServers.add(prop);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4381 */     if ((moSystems != null) && (moSystems.size() > 0))
/*      */     {
/*      */ 
/*      */ 
/* 4385 */       for (int i = 1; i < moSystems.size(); i++)
/*      */       {
/* 4387 */         Properties prop = (Properties)moSystems.get(i);
/* 4388 */         String value = prop.getProperty("value");
/* 4389 */         if ((moTypeCount.containsKey(value)) && (!value.equalsIgnoreCase("AS400/iSeries")))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 4394 */           capacityServers.add(prop);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4399 */     return capacityServers; }
/*      */   
/* 4401 */   private String capacityPlanningOptions = "0";
/* 4402 */   private String columnsAdded = null;
/*      */   
/*      */   public void setCapacityPlanningOptions(String capacityPlanningOptions) {
/* 4405 */     this.capacityPlanningOptions = capacityPlanningOptions;
/*      */   }
/*      */   
/*      */ 
/* 4409 */   public String getCapacityPlanningOptions() { return this.capacityPlanningOptions; }
/*      */   
/* 4411 */   private static String customfield = "false";
/* 4412 */   private static String showcfFilter = "false";
/*      */   
/* 4414 */   public void setCustomfield(String customfield) { customfield = customfield; }
/*      */   
/*      */   private static String customFieldValue;
/*      */   public String getCustomfield()
/*      */   {
/* 4419 */     return customfield;
/*      */   }
/*      */   
/*      */   public void setCustomFieldValue(String customvalue)
/*      */   {
/* 4424 */     customFieldValue = customvalue;
/*      */   }
/*      */   
/*      */   public String getCustomFieldValue()
/*      */   {
/* 4429 */     return customFieldValue;
/*      */   }
/*      */   
/*      */   public void setShowcfFilter(String filter) {
/* 4433 */     showcfFilter = filter;
/*      */   }
/*      */   
/*      */   public String getShowcfFilter()
/*      */   {
/* 4438 */     return showcfFilter;
/*      */   }
/*      */   
/*      */   public void setColumnsAdded(String columnsAdded) {
/* 4442 */     this.columnsAdded = columnsAdded;
/*      */   }
/*      */   
/*      */   public String getColumnsAdded() {
/* 4446 */     return this.columnsAdded;
/*      */   }
/*      */   
/*      */   public void setMonitorDisplayNames(Hashtable t) {}
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\form\ReportForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */