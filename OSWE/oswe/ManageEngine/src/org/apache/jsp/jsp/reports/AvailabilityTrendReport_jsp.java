/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class AvailabilityTrendReport_jsp
/*     */   extends HttpJspBase implements JspSourceDependent
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
/*     */       
/*     */ 
/*  75 */       ArrayList Values = (ArrayList)request.getAttribute("allvalues");
/*  76 */       ArrayList time = (ArrayList)request.getAttribute("displaytime");
/*  77 */       if ((Values != null) && (Values.size() > 0)) {
/*     */         try {
/*  79 */           out.write("\n   <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder1\"  style=\"font-family:Arial, Helvetica, sans-serif;\">\n\n    <tr>\n     <td width=\"15%\" class=\"tableheadingbborder-reports\" align=\"left\">");
/*  80 */           out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.availability.text"));
/*  81 */           out.write(" <img src=\"../../images/img_dboard_heading2.gif\" style=\"position:relative; top:3px; left:0px;\"></td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-12</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-11</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-10</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-9</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-8</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-7</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-6</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-5</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-4</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-3</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-2</td>\n   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">-1</td>\n");
/*  82 */           out.write("   <td width=\"7%\" class=\"tableheadingbborder-reports\" align=\"center\">");
/*  83 */           out.print(FormatUtil.getString("am.webclient.common.current.text"));
/*  84 */           out.write("</td>\n\n\n\n    </tr>\n\n   <tr>\n     <td  width=\"15%\" class=\"whitegrayrightalign-reports\" align=\"left\"><b>");
/*  85 */           out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/*  86 */           out.write("</b> </td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n   <td width=\"7%\" class=\"whitegrayrightalign-reports\" align=\"left\">&nbsp;</td>\n");
/*  87 */           out.write("\n\n\n    </tr>\n\n     <tr class=\"whitegrayrightalign\" class=\"tableheadingbborder-reports\">\n    <td width=\"13%\" class=\"whitegrayrightalign-reports\">");
/*  88 */           out.print(FormatUtil.getString("am.reporting.timeperiods.name"));
/*  89 */           out.write("</td>\n     ");
/*     */           
/*  91 */           for (int i = 12; i >= 0; i--)
/*     */           {
/*  93 */             String timetodisplay = time.get(i).toString();
/*     */             
/*  95 */             out.write("\n    <td width=\"6%\" class=\"whitegrayrightalign-reports\" align=\"left\">");
/*  96 */             out.print(timetodisplay);
/*  97 */             out.write("</td>\n      ");
/*     */           }
/*  99 */           out.write("\n    </tr>\n\n    <tr>\n    ");
/*     */           
/* 101 */           for (int i = 0; i < Values.size(); i++) {
/* 102 */             ArrayList a1 = (ArrayList)Values.get(i);
/* 103 */             String mgname = a1.get(2).toString();
/*     */             
/* 105 */             String type = a1.get(3).toString();
/* 106 */             HashMap data = (HashMap)a1.get(4);
/* 107 */             String bgclass = "whitegrayrightalign";
/* 108 */             if ("HAI".equals(type)) {
/* 109 */               bgclass = "columnheading";
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 118 */               out.write("\n    <tr  class=\"tableheadingbborder-reports\">\n    <td width=\"13%\" class=\"whitegrayrightalign-reports\">");
/* 119 */               out.print(mgname);
/* 120 */               out.write("</td>\n   ");
/* 121 */               for (int j = 12; j >= 0; j--)
/*     */               {
/* 123 */                 int key = j + 1;
/*     */                 
/* 125 */                 String keyName = key + "";
/* 126 */                 String uptime = data.get(keyName).toString();
/* 127 */                 out.write("\n     <td width=\"7%\" class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 128 */                 out.print(uptime);
/* 129 */                 out.write("</td>\n     ");
/*     */               }
/* 131 */               out.write("\n    </tr>\n    ");
/*     */             }
/*     */           }
/* 134 */           out.write("\n\n</tr>\n   </table>\n\n\n");
/*     */         } catch (Exception ex) {
/* 136 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/* 140 */       out.write("\n</html>\n");
/*     */     } catch (Throwable t) {
/* 142 */       if (!(t instanceof SkipPageException)) {
/* 143 */         out = _jspx_out;
/* 144 */         if ((out != null) && (out.getBufferSize() != 0))
/* 145 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 146 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 149 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\AvailabilityTrendReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */