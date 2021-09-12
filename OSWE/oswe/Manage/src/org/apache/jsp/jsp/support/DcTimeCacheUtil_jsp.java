/*     */ package org.apache.jsp.jsp.support;
/*     */ 
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.util.AMDiagnoseUtil;
/*     */ import com.adventnet.appmanager.util.AppManagerUtil;
/*     */ import com.adventnet.management.scheduler.Scheduler;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class DcTimeCacheUtil_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*     */   public TreeMap<String, Integer> getAvgDctimeinSecs(ConcurrentHashMap<String, ArrayList<Long>> dctime)
/*     */   {
/*  29 */     HashMap<String, Integer> hmap = new HashMap();
/*  30 */     Enumeration<String> e = dctime.keys();
/*  31 */     while (e.hasMoreElements()) {
/*  32 */       String resid = (String)e.nextElement();
/*  33 */       ArrayList<Long> al = (ArrayList)dctime.get(resid);
/*  34 */       if ((al != null) && (al.size() == 2))
/*     */       {
/*     */ 
/*     */ 
/*  38 */         long dc_time = ((Long)al.get(0)).longValue();
/*  39 */         long dc_count = ((Long)al.get(1)).longValue();
/*  40 */         int dctimeSecs = (int)Math.round(dc_time / dc_count);
/*  41 */         hmap.put(resid, Integer.valueOf(dctimeSecs));
/*     */       } }
/*  43 */     TreeMap<String, Integer> sorted_map = new TreeMap(new ValueComparator(hmap));
/*  44 */     sorted_map.putAll(hmap);
/*  45 */     return sorted_map;
/*     */   }
/*     */   
/*     */   class ValueComparator implements java.util.Comparator {
/*     */     Map<String, Integer> base;
/*     */     
/*     */     public ValueComparator() {
/*  52 */       this.base = base;
/*     */     }
/*     */     
/*     */     public int compare(Object key1, Object key2) {
/*  56 */       int avgDctime1 = ((Integer)this.base.get(key1)).intValue();
/*  57 */       int avgDctime2 = ((Integer)this.base.get(key2)).intValue();
/*     */       
/*  59 */       if (avgDctime1 >= avgDctime2) {
/*  60 */         return -1;
/*     */       }
/*  62 */       return 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*  67 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  76 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  80 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  81 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  91 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  94 */     JspWriter out = null;
/*  95 */     Object page = this;
/*  96 */     JspWriter _jspx_out = null;
/*  97 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/* 101 */       response.setContentType("text/html");
/* 102 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/* 104 */       _jspx_page_context = pageContext;
/* 105 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 106 */       ServletConfig config = pageContext.getServletConfig();
/* 107 */       session = pageContext.getSession();
/* 108 */       out = pageContext.getOut();
/* 109 */       _jspx_out = out;
/*     */       
/* 111 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*     */       
/* 113 */       int greater_than = 0;
/* 114 */       String scheduler = request.getParameter("scheduler");
/* 115 */       String restype = request.getParameter("restype");
/* 116 */       String[] tempList = { ".Net", "AIX", "Apache-server", "AS400/iSeries", "DB2-server", "FreeBSD", "HP-TRU64", "HP-UX", "IIS-server", "JDK1.5", "Linux", "Mac OS", "MSSQL-DB-server", "MYSQL-DB-server", "Node", "Novell", "ORACLE-APP-server", "PHP", "SAP", "SAP-CCMS", "SUN", "SYBASE-DB-server", "WEBLOGIC-Integration", "WEBLOGIC-server", "Windows 2000", "Windows 2003", "Windows 2008", "Windows 2012", "Windows 7", "Windows 8", "Windows Vista", "Windows XP", "Windows95", "WindowsNT" };
/* 117 */       List perfSchTypes = new ArrayList(Arrays.asList(tempList));
/* 118 */       tempList = new String[] { "UrlMonitor", "UrlSeq" };
/* 119 */       List urlSchTypes = new ArrayList(Arrays.asList(tempList));
/* 120 */       if ((scheduler == null) || (scheduler.trim().length() == 0)) {
/* 121 */         scheduler = "all";
/*     */       }
/* 123 */       if ((restype == null) || (restype.trim().length() == 0)) {
/* 124 */         restype = "all";
/*     */       }
/*     */       try {
/* 127 */         greater_than = Integer.parseInt(request.getParameter("greater_than"));
/*     */       }
/*     */       catch (Exception ex) {}
/* 130 */       out.println("<B>DataCollection</B>&nbsp;&nbsp;" + Scheduler.getScheduler("DataCollection").getMaxThreads());
/* 131 */       out.println("<BR>");
/* 132 */       out.println("<B>KeyValue_Monitor</B>&nbsp;&nbsp;" + Scheduler.getScheduler("KeyValue_Monitor").getMaxThreads());
/* 133 */       out.println("<BR>");
/* 134 */       out.println("<B>CustomMonitor</B>&nbsp;&nbsp;" + Scheduler.getScheduler("CustomMonitor").getMaxThreads());
/* 135 */       out.println("<BR>");
/* 136 */       out.println("<B>URLMonitor</B>&nbsp;&nbsp;" + Scheduler.getScheduler("URLMonitor").getMaxThreads());
/* 137 */       out.println("<BR>");
/* 138 */       out.println("<BR>");
/* 139 */       out.println("<B>Avg DataCollection time</B>&nbsp;&nbsp;" + AMDiagnoseUtil.getAMDiagnoseUtil().getAvgDatacollectionTime() + " seconds");
/* 140 */       out.println("<BR>");
/* 141 */       out.println("<BR>");
/* 142 */       out.println("<B>Resource Vs Data collection time (milli secs)</B>" + (scheduler.equals("all") ? "" : new StringBuilder().append(" for this scheduler&nbsp;<B>").append(scheduler).append("</B>").toString()));
/* 143 */       ConcurrentHashMap<String, ArrayList<Long>> ht2 = AMDiagnoseUtil.getAMDiagnoseUtil().getDatacollectionTimeCache();
/* 144 */       out.println("<BR>");
/* 145 */       out.println("<BR>");
/* 146 */       long sum = 0L;
/* 147 */       int count = 0;
/* 148 */       TreeMap<String, Integer> sortedMap = getAvgDctimeinSecs(ht2);
/* 149 */       for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
/* 150 */         String key = (String)entry.getKey();
/* 151 */         Integer value = (Integer)entry.getValue();
/* 152 */         if ((greater_than <= 0) || (value.intValue() >= greater_than * 1000))
/*     */         {
/*     */ 
/* 155 */           String resourceName = AppManagerUtil.getResourceNameandType(key, "_");
/* 156 */           if ((resourceName == null) || (resourceName.length() == 0)) {
/* 157 */             out.println(key + " ---------------- " + value);
/*     */           } else {
/* 159 */             String resourceType = resourceName.substring(resourceName.lastIndexOf("_") + 1);
/* 160 */             resourceName = resourceName.substring(0, resourceName.lastIndexOf("_"));
/* 161 */             if ((!restype.equals("all")) && (resourceType.toLowerCase().indexOf(restype.toLowerCase()) == -1)) {
/*     */               continue;
/*     */             }
/* 164 */             if (!scheduler.equals("all")) {
/* 165 */               if (scheduler.equalsIgnoreCase("KeyValue_Monitor")) {
/* 166 */                 List<String> confMonitorsList = ConfMonitorConfiguration.getInstance().getConfSupportedMonTypes();
/* 167 */                 if ((confMonitorsList != null) && (!confMonitorsList.contains(resourceType)))
/*     */                   continue;
/*     */               } else {
/* 170 */                 if (((scheduler.equalsIgnoreCase("URLMonitor")) && (!urlSchTypes.contains(resourceType))) || (
/*     */                 
/* 172 */                   (scheduler.equalsIgnoreCase("DataCollection")) && (!perfSchTypes.contains(resourceType))))
/*     */                   continue;
/*     */               }
/* 175 */               if (restype.equals("all")) {
/* 176 */                 sum += value.intValue();
/* 177 */                 count++;
/*     */               }
/*     */             }
/* 180 */             out.println(key + " ---------------- " + resourceName + "_" + resourceType + " ---------------- " + value);
/*     */           }
/* 182 */           out.println("<BR>");
/*     */         } }
/* 184 */       if ((sum > 0L) && (count > 0)) {
/* 185 */         out.println("<BR>");
/* 186 */         int t = (int)Math.round(sum / count / 1000.0D);
/* 187 */         out.println("<B>Avg DataCollection time in this scheduler</B>&nbsp;&nbsp;" + t + " seconds");
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
/* 210 */       out.write(10);
/* 211 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 213 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 214 */         out = _jspx_out;
/* 215 */         if ((out != null) && (out.getBufferSize() != 0))
/* 216 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 217 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 220 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\support\DcTimeCacheUtil_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */