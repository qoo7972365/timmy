/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class MySqlProcessInfo_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  39 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  43 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  50 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  53 */     JspWriter out = null;
/*  54 */     Object page = this;
/*  55 */     JspWriter _jspx_out = null;
/*  56 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  60 */       response.setContentType("text/html;charset=UTF-8");
/*  61 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  63 */       _jspx_page_context = pageContext;
/*  64 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  65 */       ServletConfig config = pageContext.getServletConfig();
/*  66 */       session = pageContext.getSession();
/*  67 */       out = pageContext.getOut();
/*  68 */       _jspx_out = out;
/*     */       
/*  70 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/");
/*  71 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  73 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<style type=\"text/css\">\n\n.bodytext{\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n}\n.critical{\n\tborder-bottom-width: 1px;\n\tborder-bottom-style: solid;\n\tborder-bottom-color: #DEDEDE;\n\theight: 20px;\n\tpadding-left: 3px;\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tbackground:red;\n}\n.whitegrayborder {\n\tborder-bottom-width: 1px;\n\tborder-bottom-style: solid;\n\tborder-bottom-color: #DEDEDE;\n\theight: 20px;\n\tpadding-left: 3px;\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tcolor: #000000;\n}\n.yellowgrayborder {\n\tborder-bottom-width: 1px;\n\tborder-bottom-style: solid;\n\tborder-bottom-color: #DEDEDE;\n\theight: 20px;\n\tpadding-left: 3px;\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tcolor: #000000;\n\tbackground: #E3F2FF;\n\n\n\n\n}\n.lrtbdarkborder {\n\tborder: 1px solid #4E83AF;\n}\n\n.red-table-head{background-color:#ff6e6e; color:#fff; font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold; border:0px solid #ff0000; line-height:22px; } \n");
/*  74 */       out.write(".red-table-td-white{background-color:#fff;  font-weight:normal; color:#000; font-size:11px; font-family:Arial, Helvetica, sans-serif; padding:3px;} \n.red-table-td-pink{background-color:#fff2f2; font-weight:normal; color:#000; font-size:11px; font-family:Arial, Helvetica, sans-serif; padding:3px; }\n\n</style>\n\n</meta>\n");
/*     */       
/*  76 */       DecimalFormat df = new DecimalFormat("#.##");
/*  77 */       String error = (String)request.getAttribute("error");
/*  78 */       if (error != null)
/*     */       {
/*     */ 
/*  81 */         out.write("\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr><td>\n<span class=\"bodytext\">");
/*  82 */         out.print(error);
/*  83 */         out.write("</span>\n</td></tr>\n</table>\n");
/*     */       }
/*     */       else
/*     */       {
/*  87 */         String resname = (String)request.getAttribute("displayname");
/*     */         
/*  89 */         out.write("\n\n<b><span class=\"bodytext\">");
/*  90 */         out.print(resname);
/*  91 */         out.write("</span></b>   ");
/*  92 */         out.write("\n<br><br>\n");
/*     */         
/*  94 */         ArrayList processlist = (ArrayList)request.getAttribute("processList");
/*     */         
/*  96 */         out.write("\n \n<table id=\"threadtable_ext\" border=\"0\" class=\"lrtbdarkborder\" width=\"100%\" cellpadding=\"2\" cellspacing=\"1\">\n<tr>\n<td class=\"tableheading\" width=\"5%\">");
/*  97 */         out.print(FormatUtil.getString("am.webclient.mysql.processlist.id"));
/*  98 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/*  99 */         out.print(FormatUtil.getString("am.webclient.mysql.processlist.user"));
/* 100 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/* 101 */         out.print(FormatUtil.getString("am.webclient.mysql.processlist.host"));
/* 102 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/* 103 */         out.print(FormatUtil.getString("am.webclient.mysql.processlist.database"));
/* 104 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/* 105 */         out.print(FormatUtil.getString("am.webclient.mysql.processlist.command"));
/* 106 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/* 107 */         out.print(FormatUtil.getString("am.webclient.mysql.processlist.time"));
/* 108 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/* 109 */         out.print(FormatUtil.getString("am.webclient.mysql.processlist.state"));
/* 110 */         out.write("</td>\n<td class=\"tableheading\" width=\"35%\">");
/* 111 */         out.print(FormatUtil.getString("am.webclient.mysql.processlist.info"));
/* 112 */         out.write("</td>\n</tr>\n");
/*     */         
/* 114 */         for (int i = 0; i < processlist.size(); i++) {
/* 115 */           Properties data = (Properties)processlist.get(i);
/* 116 */           String sclass = "yellowgrayborder";
/* 117 */           if (i % 2 == 0) {
/* 118 */             sclass = "whitegrayborder";
/*     */           }
/*     */           
/* 121 */           out.write("\n\t<tr class=\"");
/* 122 */           out.print(sclass);
/* 123 */           out.write("\">\n\t<td>");
/* 124 */           out.print(data.getProperty("Id"));
/* 125 */           out.write("</td>\n\t<td>");
/* 126 */           out.print(data.getProperty("User"));
/* 127 */           out.write("</td>\n\t<td>");
/* 128 */           out.print(data.getProperty("Host"));
/* 129 */           out.write("</td>\n\t<td>");
/* 130 */           out.print(data.getProperty("db"));
/* 131 */           out.write("</td>\n\t<td>");
/* 132 */           out.print(data.getProperty("Command"));
/* 133 */           out.write("</td>\n\t<td>");
/* 134 */           out.print(data.getProperty("Time"));
/* 135 */           out.write("</td>\n\t<td>");
/* 136 */           out.print(data.getProperty("State"));
/* 137 */           out.write("</td>\n\t<td>");
/* 138 */           out.print(data.getProperty("Info"));
/* 139 */           out.write("</td>\n    </tr>\n");
/*     */         }
/*     */         
/*     */ 
/* 143 */         out.write("\n</table>\n");
/*     */       }
/* 145 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 146 */         out = _jspx_out;
/* 147 */         if ((out != null) && (out.getBufferSize() != 0))
/* 148 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 149 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 152 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 158 */     PageContext pageContext = _jspx_page_context;
/* 159 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 161 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 162 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 163 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 165 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 167 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 168 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 169 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 170 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 171 */       return true;
/*     */     }
/* 173 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 174 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MySqlProcessInfo_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */