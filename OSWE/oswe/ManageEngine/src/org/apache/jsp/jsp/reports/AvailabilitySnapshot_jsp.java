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
/*     */ public final class AvailabilitySnapshot_jsp extends HttpJspBase implements JspSourceDependent
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
/*  72 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n</head>\n");
/*     */       try {
/*  74 */         ArrayList AllValues = (ArrayList)request.getAttribute("AllValues");
/*     */         
/*     */ 
/*     */ 
/*  78 */         out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder1\">\n   <tr>\n  <td width=\"20%\" class=\"tableheadingbborder-reports\">");
/*  79 */         out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/*  80 */         out.write("</td>\n     <td width=\"17%\" class=\"tableheadingbborder-reports\" colspan=\"2\" align=\"center\">");
/*  81 */         out.print(FormatUtil.getString("am.webclient.reports.OutagesHeading.text"));
/*  82 */         out.write("</td>\n     <td width=\"10%\" class=\"tableheadingbborder-reports\" colspan=\"2\" align=\"center\">");
/*  83 */         out.print(FormatUtil.getString("am.webclient.reports.WarningCriticalHeading.text"));
/*  84 */         out.write("</td>\n    <td width=\"14%\" class=\"tableheadingbborder-reports\" align=\"center\">");
/*  85 */         out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  86 */         out.write("</td>\n    <td width=\"14%\" class=\"tableheadingbborder-reports\" align=\"center\">");
/*  87 */         out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  88 */         out.write("</td>\n    <td width=\"14%\" class=\"tableheadingbborder-reports\" align=\"center\">");
/*  89 */         out.print(FormatUtil.getString("am.webclient.reports.functionalhostname.text"));
/*  90 */         out.write("</td>\n   <td width=\"24%\" class=\"tableheadingbborder-reports\">");
/*  91 */         out.print(FormatUtil.getString("am.webclient.newaction.message"));
/*  92 */         out.write("</td>\n    </tr>\n    <tr>\n    <td width=\"20%\" class=\"whitegrayrightalign-reports\" >&nbsp;</td>\n     <td width=\"12%\" class=\"whitegrayrightalign-reports\" align=\"center\"><b> ");
/*  93 */         out.print(FormatUtil.getString("am.webclient.historydata.duration.text"));
/*  94 */         out.write("</b></td>\n    <td width=\"5%\" class=\"whitegrayrightalign-reports\"><b>");
/*  95 */         out.print(FormatUtil.getString("am.webclient.mysql.graph.count"));
/*  96 */         out.write("</b></td>\n    <td width=\"5%\" class=\"whitegrayrightalign-reports\" align=\"center\"> <b>");
/*  97 */         out.print(FormatUtil.getString("am.webclient.historydata.duration.text"));
/*  98 */         out.write("</b></td>\n    <td width=\"5%\" class=\"whitegrayrightalign-reports\"><b>");
/*  99 */         out.print(FormatUtil.getString("am.webclient.mysql.graph.count"));
/* 100 */         out.write("</b></td>\n     <td width=\"14%\" class=\"whitegrayrightalign-reports\">&nbsp;</td>\n      <td width=\"14%\" class=\"whitegrayrightalign-reports\">&nbsp;</td>\n    <td width=\"14%\" class=\"whitegrayrightalign-reports\">&nbsp;</td>\n      <td width=\"24%\" class=\"whitegrayrightalign-reports\">&nbsp;</td>\n    </tr>\n    ");
/*     */         
/*     */ 
/* 103 */         int size = AllValues.size();
/*     */         
/* 105 */         for (int i = 0; i < size; i++)
/*     */         {
/* 107 */           ArrayList a1 = (ArrayList)AllValues.get(i);
/* 108 */           String resname = a1.get(1).toString();
/* 109 */           String type = a1.get(2).toString();
/* 110 */           ArrayList downtimesummary = (ArrayList)a1.get(3);
/* 111 */           String bgclass = "whitegrayrightalign";
/*     */           
/* 113 */           if ("HAI".equals(type)) {
/* 114 */             bgclass = "columnheading";
/*     */           }
/* 116 */           else if ("SUBGROUP".equals(type))
/*     */           {
/* 118 */             bgclass = "whitegrayrightalign-reports-bg";
/* 119 */           } else if ("TA".equals(type))
/*     */           {
/* 121 */             bgclass = "health-history-bg";
/*     */           }
/* 123 */           for (int j = 0; j < downtimesummary.size(); j++) {
/* 124 */             Properties rows = (Properties)downtimesummary.get(j);
/* 125 */             if (rows != null) {
/* 126 */               String from = (String)rows.get("downtime");
/* 127 */               String to = (String)rows.get("uptime");
/* 128 */               String hostname = (String)rows.get("moname");
/* 129 */               String Totaldowntime = (String)rows.get("TotalDownTime");
/*     */               
/* 131 */               String countVaraible = " ";
/* 132 */               if (!"".equals(Totaldowntime)) {
/* 133 */                 countVaraible = "1";
/* 134 */                 Totaldowntime = ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(Long.parseLong((String)rows.get("TotalDownTime"))));
/*     */               }
/* 136 */               String attributeType = (String)rows.get("type");
/*     */               
/* 138 */               if (j >= 1)
/*     */               {
/* 140 */                 out.write("\n\n<tr class=\"");
/* 141 */                 out.print(bgclass);
/* 142 */                 out.write("\">\n<td  class=\"whitegrayrightalign-reports\" width=\"20%\" valign=\"top\">&nbsp;</td>\n");
/*     */               } else {
/* 144 */                 out.write("\n\n</tr>\n\n\n<tr class=\"");
/* 145 */                 out.print(bgclass);
/* 146 */                 out.write("\">\n<td class=\"whitegrayrightalign-reports\" width=\"10%\" valign=\"top\">");
/* 147 */                 out.print(resname);
/* 148 */                 out.write("&nbsp;</td>\n");
/*     */               }
/* 150 */               out.write(10);
/* 151 */               if ("Availability".equals(attributeType))
/*     */               {
/* 153 */                 out.write("\n            <td class=\"whitegrayrightalign-reports-normal\" width=\"12%\" valign=\"top\">");
/* 154 */                 out.print(Totaldowntime);
/* 155 */                 out.write("&nbsp;</td>\n               <td class=\"whitegrayrightalign-reports-normal\" width=\"5%\" align=\"center\" valign=\"top\">");
/* 156 */                 out.print(countVaraible);
/* 157 */                 out.write("&nbsp;</td>\n                <td class=\"whitegrayrightalign-reports-normal\" width=\"5%\" align=\"center\" valign=\"top\">-</td>\n               <td class=\"whitegrayrightalign-reports-normal\" width=\"5%\" align=\"center\" valign=\"top\">-</td>\n                ");
/*     */               }
/* 159 */               out.write("\n                ");
/* 160 */               if ("Health".equals(attributeType))
/*     */               {
/* 162 */                 out.write("\n                 <td class=\"whitegrayrightalign-reports-normal\" width=\"10%\" align=\"center\" valign=\"top\">-</td>\n               <td class=\"whitegrayrightalign-reports-normal\" width=\"10%\" align=\"center\" valign=\"top\">-</td>\n            <td class=\"whitegrayrightalign-reports-normal\" width=\"10%\" valign=\"top\">");
/* 163 */                 out.print(Totaldowntime);
/* 164 */                 out.write("</td>\n               <td class=\"whitegrayrightalign-reports-normal\"  width=\"10%\" align=\"center\" valign=\"top\">");
/* 165 */                 out.print(countVaraible);
/* 166 */                 out.write("</td>\n\n               ");
/*     */               }
/* 168 */               out.write("\n                ");
/* 169 */               if ("TA".equals(attributeType))
/*     */               {
/* 171 */                 out.write("\n                 <td class=\"whitegrayrightalign-reports-normal\" width=\"10%\" align=\"center\" valign=\"top\">");
/* 172 */                 out.print(ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(Long.parseLong((String)rows.get("AvailabilityTotalDownTime")))));
/* 173 */                 out.write("</td>\n               <td class=\"whitegrayrightalign-reports-normal\" width=\"10%\" align=\"center\" valign=\"top\">");
/* 174 */                 out.print((String)rows.get("availcount"));
/* 175 */                 out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\" width=\"10%\" valign=\"top\">");
/* 176 */                 out.print(ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(Long.parseLong((String)rows.get("HealthTotalDownTime")))));
/* 177 */                 out.write("</td>\n               <td class=\"whitegrayrightalign-reports-normal\"  width=\"10%\" align=\"center\" valign=\"top\">");
/* 178 */                 out.print((String)rows.get("healthcount"));
/* 179 */                 out.write("</td>\n\n               ");
/*     */               }
/* 181 */               out.write("\n               ");
/* 182 */               if ("TA".equals(attributeType))
/*     */               {
/* 184 */                 out.write("\n                       <td class=\"whitegrayrightalign-reports-normal\"  width=\"10%\" valign=\"top\">-</td>\n            <td class=\"whitegrayrightalign-reports-normal\"  width=\"10%\" valign=\"top\">-</td>\n             <td class=\"whitegrayrightalign-reports-normal\"  width=\"10%\" valign=\"top\">-</td>\n                       ");
/*     */               } else {
/* 186 */                 out.write("\n            <td class=\"whitegrayrightalign-reports-normal\"  width=\"14%\" valign=\"top\">");
/* 187 */                 out.print(FormatUtil.formatDT(from));
/* 188 */                 out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\"  width=\"14%\" valign=\"top\">");
/* 189 */                 out.print(FormatUtil.formatDT(to));
/* 190 */                 out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\"  width=\"14%\" valign=\"top\">");
/* 191 */                 out.print(hostname);
/* 192 */                 out.write("</td>\n            ");
/*     */               }
/* 194 */               out.write("\n             ");
/* 195 */               if ("Health".equals(attributeType)) {
/* 196 */                 out.write("\n            <td class=\"whitegrayrightalign-reports-normal\"  width=\"24%\" valign=\"top\">");
/* 197 */                 out.print((String)rows.get("message"));
/* 198 */                 out.write("</td>\n               ");
/* 199 */               } else if ("Availability".equals(attributeType)) {
/* 200 */                 out.write("\n               <td class=\"whitegrayrightalign-reports-normal\" width=\"24%\" valign=\"top\">");
/* 201 */                 out.print(FormatUtil.getString("am.webclient.reports.resourcedownmessage.text"));
/* 202 */                 out.write("</td>\n               ");
/*     */               } else {
/* 204 */                 out.write("\n                 <td class=\"whitegrayrightalign-reports-normal\" width=\"24%\" valign=\"top\">");
/* 205 */                 out.print(FormatUtil.getString("am.webclient.reports.availabilitypercentagemessage.text", new String[] { (String)rows.get("Available") }));
/* 206 */                 out.write(" </td>\n               ");
/*     */               }
/* 208 */               out.write("\n</tr>\n\n\n\n\n\n\n");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 215 */         ex.printStackTrace();
/*     */       }
/*     */       
/* 218 */       out.write("\n</table>\n\n</html>\n");
/*     */     } catch (Throwable t) {
/* 220 */       if (!(t instanceof SkipPageException)) {
/* 221 */         out = _jspx_out;
/* 222 */         if ((out != null) && (out.getBufferSize() != 0))
/* 223 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 224 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 227 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\AvailabilitySnapshot_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */