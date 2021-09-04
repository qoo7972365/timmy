/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class IngresProcessInfo_jsp extends HttpJspBase implements JspSourceDependent
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
/*  73 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<style type=\"text/css\">\n\n.bodytext{\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n}\n.critical{\n\tborder-bottom-width: 1px;\n\tborder-bottom-style: solid;\n\tborder-bottom-color: #DEDEDE;\n\theight: 20px;\n\tpadding-left: 3px;\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tbackground:red;\n}\n.whitegrayborder {\n\tborder-bottom-width: 1px;\n\tborder-bottom-style: solid;\n\tborder-bottom-color: #DEDEDE;\n\theight: 20px;\n\tpadding-left: 3px;\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tcolor: #000000;\n}\n.yellowgrayborder {\n\tborder-bottom-width: 1px;\n\tborder-bottom-style: solid;\n\tborder-bottom-color: #DEDEDE;\n\theight: 20px;\n\tpadding-left: 3px;\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tcolor: #000000;\n\tbackground: #E3F2FF;\n\n\n\n\n}\n.lrtbdarkborder {\n\tborder: 1px solid #4E83AF;\n}\n\n.red-table-head{background-color:#ff6e6e; color:#fff; font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold; border:0px solid #ff0000; line-height:22px; }\n");
/*  74 */       out.write(".red-table-td-white{background-color:#fff;  font-weight:normal; color:#000; font-size:11px; font-family:Arial, Helvetica, sans-serif; padding:3px;}\n.red-table-td-pink{background-color:#fff2f2; font-weight:normal; color:#000; font-size:11px; font-family:Arial, Helvetica, sans-serif; padding:3px; }\n\n</style>\n\n</meta>\n");
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
/*  92 */         out.write("\n<br><br>\n<table id=\"threadtable_ext\" border=\"0\" class=\"lrtbdarkborder\" width=\"100%\" cellpadding=\"2\" cellspacing=\"1\">\n<tr>\n<td class=\"tableheading\" width=\"10%\">");
/*  93 */         out.print(FormatUtil.getString("am.webclient.ingres.sessionid"));
/*  94 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/*  95 */         out.print(FormatUtil.getString("am.webclient.ingres.database"));
/*  96 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/*  97 */         out.print(FormatUtil.getString("am.webclient.ingres.user"));
/*  98 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/*  99 */         out.print(FormatUtil.getString("am.webclient.ingres.terminal"));
/* 100 */         out.write("</td>\n<td class=\"tableheading\" width=\"10%\">");
/* 101 */         out.print(FormatUtil.getString("am.webclient.ingres.elapsedtime"));
/* 102 */         out.write("</td>\n<td class=\"tableheading\" width=\"35%\">");
/* 103 */         out.print(FormatUtil.getString("am.webclient.ingres.sessionquery"));
/* 104 */         out.write("</td>\n</tr>\n");
/*     */         
/* 106 */         ArrayList processlist = (ArrayList)request.getAttribute("processList");
/* 107 */         for (int i = 0; i < processlist.size(); i++) {
/* 108 */           ArrayList process = (ArrayList)processlist.get(i);
/* 109 */           String sclass = "yellowgrayborder";
/* 110 */           if (i % 2 == 0) {
/* 111 */             sclass = "whitegrayborder";
/*     */           }
/*     */           
/* 114 */           out.write("\n\t<tr class=\"");
/* 115 */           out.print(sclass);
/* 116 */           out.write(34);
/* 117 */           out.write(62);
/* 118 */           out.write(10);
/*     */           
/* 120 */           for (int j = 0; j < process.size(); j++)
/*     */           {
/* 122 */             out.write("\n        <td>");
/* 123 */             out.print((String)process.get(j));
/* 124 */             out.write("</td>\n");
/*     */           }
/*     */           
/*     */ 
/* 128 */           out.write("\n</tr>    \n");
/*     */         }
/*     */         
/*     */ 
/* 132 */         out.write("\n</table>\n");
/*     */       }
/* 134 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 135 */         out = _jspx_out;
/* 136 */         if ((out != null) && (out.getBufferSize() != 0))
/* 137 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 138 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 141 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 147 */     PageContext pageContext = _jspx_page_context;
/* 148 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 150 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 151 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 152 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 154 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 156 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 157 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 158 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 159 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 160 */       return true;
/*     */     }
/* 162 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 163 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\IngresProcessInfo_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */