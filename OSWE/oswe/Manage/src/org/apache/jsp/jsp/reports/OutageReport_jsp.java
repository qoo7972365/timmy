/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.SkipPageException;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class OutageReport_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  28 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  42 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  52 */     HttpSession session = null;
/*     */     
/*     */ 
/*  55 */     JspWriter out = null;
/*  56 */     Object page = this;
/*  57 */     JspWriter _jspx_out = null;
/*  58 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  62 */       response.setContentType("text/html");
/*  63 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  65 */       _jspx_page_context = pageContext;
/*  66 */       ServletContext application = pageContext.getServletContext();
/*  67 */       ServletConfig config = pageContext.getServletConfig();
/*  68 */       session = pageContext.getSession();
/*  69 */       out = pageContext.getOut();
/*  70 */       _jspx_out = out;
/*     */       
/*  72 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n<link href=\"../images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*     */       
/*     */ 
/*  75 */       ArrayList Values = (ArrayList)request.getAttribute("mgvalues");
/*  76 */       String attri = (String)request.getAttribute("timeperiod");
/*  77 */       String custom = (String)request.getAttribute("CUSTOM");
/*  78 */       Properties timeprops = (Properties)request.getAttribute("timeprops");
/*  79 */       String t1 = "";
/*  80 */       String t2 = "";
/*     */       
/*  82 */       if ((custom != null) && (custom.equals("true"))) {
/*  83 */         t1 = "(" + timeprops.getProperty("previousfrom") + " - " + timeprops.getProperty("previousto") + ")";
/*  84 */         t2 = "(" + timeprops.getProperty("currentfrom") + " - " + timeprops.getProperty("currentto") + ")";
/*     */ 
/*     */ 
/*     */       }
/*  88 */       else if ("month".equals(attri)) {
/*  89 */         t1 = FormatUtil.getString("Last Month");
/*  90 */         t2 = FormatUtil.getString("This Month");
/*     */       }
/*     */       else
/*     */       {
/*  94 */         t1 = FormatUtil.getString("Last Week");
/*  95 */         t2 = FormatUtil.getString("This Week");
/*     */       }
/*     */       
/*  98 */       if ((Values != null) && (Values.size() > 0)) {
/*     */         try {
/* 100 */           out.write("\n   <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder1\"  style=\"font-family:Arial, Helvetica, sans-serif; font-size:11px;\">\n    <tr >\n  <td width=\"20%\" class=\"tableheadingbborder-reports\">");
/* 101 */           out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/* 102 */           out.write("</td>\n   <td width=\"20%\" class=\"tableheadingbborder-reports\" colspan=\"2\" align=\"center\"> ");
/* 103 */           out.print(FormatUtil.getString("am.webclient.reports.outagetime.compariosonreport.tableheading.thisperiod.text", new String[] { t2 }));
/* 104 */           out.write("</td>\n    <td width=\"20%\" class=\"tableheadingbborder-reports\" colspan=\"2\" align=\"center\"> ");
/* 105 */           out.print(FormatUtil.getString("am.webclient.reports.outagetime.compariosonreport.tableheading.lastperiod.text", new String[] { t1 }));
/* 106 */           out.write("</td>\n    <td width=\"20%\" class=\"tableheadingbborder-reports\" colspan=\"2\" align=\"center\"> ");
/* 107 */           out.print(FormatUtil.getString("am.webclient.mysql.graph.count"));
/* 108 */           out.write("</td>\n    <td width=\"20%\" class=\"tableheadingbborder-reports\" colspan=\"2\" align=\"center\"> ");
/* 109 */           out.print(FormatUtil.getString("am.webclient.reports.downtimeduration.compariosonreport.text"));
/* 110 */           out.write("</td>\n    </tr>\n    <tr>\n    <td width=\"20%\" class=\"whitegrayrightalign-reports\" align=\"center\">&nbsp;</td>\n   <td width=\"10%\" class=\"whitegrayrightalign-reports\" align=\"center\"><b>");
/* 111 */           out.print(FormatUtil.getString("am.webclient.mysql.graph.count"));
/* 112 */           out.write("</b></td>\n   <td width=\"10%\" class=\"whitegrayrightalign-reports\" align=\"center\"><b>");
/* 113 */           out.print(FormatUtil.getString("am.webclient.historydata.duration.text"));
/* 114 */           out.write("</b></td>\n   <td width=\"10%\" class=\"whitegrayrightalign-reports\" align=\"center\"><b>");
/* 115 */           out.print(FormatUtil.getString("am.webclient.mysql.graph.count"));
/* 116 */           out.write("</b></td>\n   <td width=\"10%\" class=\"whitegrayrightalign-reports\" align=\"center\"><b>");
/* 117 */           out.print(FormatUtil.getString("am.webclient.historydata.duration.text"));
/* 118 */           out.write("</b></td>\n   <td width=\"10%\" class=\"whitegrayrightalign-reports\" align=\"center\"><b>");
/* 119 */           out.print(FormatUtil.getString("am.webclient.reports.change.compariosonreport.text"));
/* 120 */           out.write("</b></td>\n    <td width=\"10%\" class=\"whitegrayrightalign-reports\" align=\"center\"><b>%</b></td>\n    <td width=\"10%\" class=\"whitegrayrightalign-reports\" align=\"center\"><b>");
/* 121 */           out.print(FormatUtil.getString("am.webclient.reports.difference.compariosonreport.text"));
/* 122 */           out.write("</b></td>\n    <td width=\"10%\" class=\"whitegrayrightalign-reports\" align=\"center\"><b>%</b></td>\n    </tr>\n    <tr>\n    ");
/*     */           
/* 124 */           for (int i = 0; i < Values.size(); i++) {
/* 125 */             ArrayList a1 = (ArrayList)Values.get(i);
/* 126 */             String mgname = a1.get(1).toString();
/* 127 */             String LastOutage = a1.get(2).toString();
/* 128 */             String LastDuration = a1.get(3).toString();
/* 129 */             String ThisOutage = a1.get(4).toString();
/* 130 */             String ThisDuration = a1.get(5).toString();
/* 131 */             String cg = a1.get(6).toString();
/* 132 */             String cgpercent = a1.get(7).toString();
/*     */             
/* 134 */             String diff = ReportUtilities.formatWithoutSeconds(ReportUtilities.roundOffToNearestSeconds(Long.parseLong(a1.get(8).toString())));
/* 135 */             String diffpercent = a1.get(9).toString();
/*     */             
/* 137 */             String type = a1.get(10).toString();
/*     */             
/* 139 */             String bgclass = "whitegrayrightalign";
/* 140 */             if ("HAI".equals(type)) {
/* 141 */               bgclass = "columnheading";
/*     */               
/*     */ 
/*     */ 
/*     */ 
/* 146 */               out.write("\n    <tr class=\"");
/* 147 */               out.print(bgclass);
/* 148 */               out.write("\" class=\"whitegrayrightalign-reports\" >\n    <td width=\"20%\" class=\"whitegrayrightalign-reports\">");
/* 149 */               out.print(mgname);
/* 150 */               out.write("</td>\n\n     <td width=\"10%\" class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 151 */               out.print(ThisOutage);
/* 152 */               out.write("</td>\n    <td width=\"10%\" class=\"whitegrayrightalign-reports-normal\" align=\"left\">");
/* 153 */               out.print(ReportUtilities.formatWithoutSeconds(ReportUtilities.roundOffToNearestSeconds(Long.parseLong(ThisDuration))));
/* 154 */               out.write("</td>\n    <td width=\"10%\" class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 155 */               out.print(LastOutage);
/* 156 */               out.write("</td>\n   <td width=\"10%\" class=\"whitegrayrightalign-reports-normal\" align=\"left\">");
/* 157 */               out.print(ReportUtilities.formatWithoutSeconds(ReportUtilities.roundOffToNearestSeconds(Long.parseLong(LastDuration))));
/* 158 */               out.write("</td>\n\n    <td width=\"10%\" class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 159 */               out.print(cg);
/* 160 */               out.write("</td>\n     <td width=\"10%\" class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 161 */               out.print(cgpercent);
/* 162 */               out.write("</td>\n      <td width=\"10%\" class=\"whitegrayrightalign-reports-normal\" align=\"left\" >");
/* 163 */               out.print(diff);
/* 164 */               out.write("</td>\n       <td width=\"10%\" class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 165 */               out.print(diffpercent);
/* 166 */               out.write("</td>\n    </tr>\n    ");
/*     */             }
/*     */           }
/* 169 */           out.write("\n\n  </tr>\n   </table>\n\n\n");
/*     */         } catch (Exception ex) {
/* 171 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/* 175 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 177 */       if (!(t instanceof SkipPageException)) {
/* 178 */         out = _jspx_out;
/* 179 */         if ((out != null) && (out.getBufferSize() != 0))
/* 180 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 181 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 184 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\OutageReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */