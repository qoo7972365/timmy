/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.utilities.stringutils.StrUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class HASnapshot_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  38 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  48 */     HttpSession session = null;
/*     */     
/*     */ 
/*  51 */     JspWriter out = null;
/*  52 */     Object page = this;
/*  53 */     JspWriter _jspx_out = null;
/*  54 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  58 */       response.setContentType("text/html");
/*  59 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  61 */       _jspx_page_context = pageContext;
/*  62 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  63 */       ServletConfig config = pageContext.getServletConfig();
/*  64 */       session = pageContext.getSession();
/*  65 */       out = pageContext.getOut();
/*  66 */       _jspx_out = out;
/*     */       
/*  68 */       out.write("\n\n\n\n\n\n\n\n \n\n\n\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n</head>\n");
/*     */       
/*  70 */       ArrayList data = (ArrayList)request.getAttribute("data");
/*  71 */       String withhost = (String)request.getAttribute("withhostname");
/*  72 */       boolean isHost = false;
/*  73 */       if ("true".equals(withhost)) {
/*  74 */         isHost = true;
/*     */       }
/*  76 */       if (data != null) {
/*     */         try {
/*  78 */           out.write("\n\n\n <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtborder\">\n  <tr> \n  <td width=\"25%\" class=\"tableheadingbborder-reports\">");
/*  79 */           if (isHost) {
/*  80 */             out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/*     */           } else {
/*  82 */             out.print(FormatUtil.getString("webclient.fault.details.properties.source"));
/*     */           }
/*  84 */           out.write("</td>\n    <td width=\"11%\" class=\"tableheadingbborder-reports\" align=\"center\">");
/*  85 */           out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.columnheader.availability"));
/*  86 */           out.write("</td>\n    <td width=\"11%\" class=\"tableheadingbborder-reports\" align=\"center\">");
/*  87 */           out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.health"));
/*  88 */           out.write("</td>\n    ");
/*  89 */           if (isHost) {
/*  90 */             out.write("<td width=\"20%\" class=\"tableheadingbborder-reports\" align=\"center\">");
/*  91 */             out.print(FormatUtil.getString("am.webclient.reports.functionalhostname.text"));
/*  92 */             out.write("</td>");
/*     */           }
/*  94 */           out.write("\n    <td width=\"50%\" class=\"tableheadingbborder-reports\">");
/*  95 */           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/*  96 */           out.write("</td>\n    </tr>\n");
/*     */           
/*     */ 
/*  99 */           int size = data.size();
/* 100 */           for (int i = 0; i < size; i++)
/*     */           {
/* 102 */             ArrayList a1 = (ArrayList)data.get(i);
/* 103 */             int a1Size = a1.size();
/* 104 */             String name = a1.get(1).toString();
/* 105 */             String type = a1.get(2).toString();
/* 106 */             String avail = "-";
/* 107 */             String Aimg = "";
/* 108 */             String Himg = "";
/* 109 */             String text = " ";
/* 110 */             String bgclass = "whitegrayrightalign";
/* 111 */             if ("HAI".equals(type)) {
/* 112 */               bgclass = "columnheading";
/* 113 */             } else if ("SUBGROUP".equals(type))
/*     */             {
/* 115 */               bgclass = "whitegrayrightalign-reports-bg";
/*     */             }
/* 117 */             if (a1Size > 3)
/*     */             {
/* 119 */               avail = a1.get(3).toString();
/*     */               
/* 121 */               String[] t1 = avail.split("#");
/*     */               
/*     */ 
/* 124 */               if (t1.length > 1)
/* 125 */                 Aimg = t1[1];
/* 126 */               String health = a1.get(4).toString();
/* 127 */               String[] t2 = health.split("#");
/*     */               
/* 129 */               if (t2.length > 1) {
/* 130 */                 Himg = t2[1];
/*     */               }
/* 132 */               text = a1.get(5).toString();
/*     */             }
/*     */             
/* 135 */             if (isHost) {
/* 136 */               ArrayList allMonitors = (ArrayList)a1.get(6);
/* 137 */               for (int j = 0; j < allMonitors.size(); j++) {
/* 138 */                 Properties rows = (Properties)allMonitors.get(j);
/* 139 */                 String moname = rows.getProperty("moname");
/* 140 */                 String momessage = rows.getProperty("momessage");
/* 141 */                 if (j == 0) {
/* 142 */                   out.write("\n\n<tr class='");
/* 143 */                   out.print(bgclass);
/* 144 */                   out.write("'>\n  \n            <td class=\"whitegrayrightalign-reports\">");
/* 145 */                   out.print(name);
/* 146 */                   out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 147 */                   out.print(Aimg);
/* 148 */                   out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 149 */                   out.print(Himg);
/* 150 */                   out.write("</td>\n             <td class=\"whitegrayrightalign-reports-normal\">");
/* 151 */                   out.print(moname);
/* 152 */                   out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\">");
/* 153 */                   out.print(StrUtil.wrapWord(momessage, 60, " "));
/* 154 */                   out.write("</td>\n           \n            \n</tr>\n");
/*     */                 }
/*     */                 else {
/* 157 */                   out.write("\n   <tr class='");
/* 158 */                   out.print(bgclass);
/* 159 */                   out.write("'>\n  \n            <td class=\"whitegrayrightalign-reports-normal\">&nbsp;</td>\n            <td class=\"whitegrayrightalign-reports-normal\" align=\"center\">&nbsp;</td>\n            <td class=\"whitegrayrightalign-reports-normal\" align=\"center\">&nbsp;</td>\n             <td class=\"whitegrayrightalign-reports-normal\">");
/* 160 */                   out.print(moname);
/* 161 */                   out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\">");
/* 162 */                   out.print(StrUtil.wrapWord(momessage, 60, " "));
/* 163 */                   out.write("</td>\n           \n            \n</tr> \n    \n");
/*     */                 }
/*     */               }
/*     */             } else {
/* 167 */               out.write("\n\n<tr class='");
/* 168 */               out.print(bgclass);
/* 169 */               out.write("'>\n  \n            <td class=\"whitegrayrightalign-reports\">");
/* 170 */               out.print(name);
/* 171 */               out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 172 */               out.print(Aimg);
/* 173 */               out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\" align=\"center\">");
/* 174 */               out.print(Himg);
/* 175 */               out.write("</td>\n            <td class=\"whitegrayrightalign-reports-normal\">");
/* 176 */               out.print(StrUtil.wrapWord(text, 60, " "));
/* 177 */               out.write("</td>\n           \n            \n</tr>\n\n\n");
/*     */             }
/*     */           }
/* 180 */           out.write("\n</table>\n");
/*     */         } catch (Exception ex) {
/* 182 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/* 186 */       out.write("\n</html>\n<br>\n");
/*     */     } catch (Throwable t) {
/* 188 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 189 */         out = _jspx_out;
/* 190 */         if ((out != null) && (out.getBufferSize() != 0))
/* 191 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 192 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 195 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\HASnapshot_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */