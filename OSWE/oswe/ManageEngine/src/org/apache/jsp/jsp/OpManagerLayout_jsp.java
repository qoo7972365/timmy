/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
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
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class OpManagerLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  21 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  27 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/*  28 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*  29 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*  30 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*     */   }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  40 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  47 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  51 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  52 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  59 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  62 */     JspWriter out = null;
/*  63 */     Object page = this;
/*  64 */     JspWriter _jspx_out = null;
/*  65 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  69 */       response.setContentType("text/html;charset=UTF-8");
/*  70 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  72 */       _jspx_page_context = pageContext;
/*  73 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  74 */       ServletConfig config = pageContext.getServletConfig();
/*  75 */       session = pageContext.getSession();
/*  76 */       out = pageContext.getOut();
/*  77 */       _jspx_out = out;
/*     */       
/*  79 */       out.write("<!DOCTYPE html>\n");
/*  80 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  81 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  83 */       String resourceid = request.getParameter("resourceid");
/*  84 */       String uri = (String)request.getAttribute("uri");
/*     */       
/*  86 */       out.write(10);
/*  87 */       Properties applications = null;
/*  88 */       synchronized (application) {
/*  89 */         applications = (Properties)_jspx_page_context.getAttribute("applications", 4);
/*  90 */         if (applications == null) {
/*  91 */           applications = new Properties();
/*  92 */           _jspx_page_context.setAttribute("applications", applications, 4);
/*     */         }
/*     */       }
/*  95 */       out.write("    \n<html>\n<head>\n");
/*  96 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  97 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  99 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 100 */       out.write(10);
/* 101 */       out.write(10);
/* 102 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 103 */       out.write(10);
/* 104 */       out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/* 105 */       out.print(request.getContextPath());
/* 106 */       out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/* 107 */       out.print(request.getContextPath());
/* 108 */       out.write("'); //No I18N\n</script>\n");
/* 109 */       if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 110 */         out.write("<script src='");
/* 111 */         out.print(request.getContextPath());
/* 112 */         out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*     */       }
/* 114 */       out.write("\n</head>\n<table width=\"100%\" border=\"00\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr> \n    <td width=\"16%\" height=\"8\" class=\"leftcells\"><img src=\"/images/spacer.gif\" height=\"10\" ></td>\n  </tr>\n\n  <tr> \n    <td height=\"505\"  align=\"center\" valign=\"top\" class=\"leftcells\" > \n       ");
/* 115 */       if (_jspx_meth_tiles_005finsert_005f0(_jspx_page_context))
/*     */         return;
/* 117 */       out.write("\n      </td>\n    <td valign=\"top\"> \n\t  \n\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr> \n          <td height=\"90%\" colspan=\"2\" valign=\"top\" class=\"tdindent\"> ");
/* 118 */       if (_jspx_meth_tiles_005finsert_005f1(_jspx_page_context))
/*     */         return;
/* 120 */       out.write("</td>\n        </tr>\n        <tr> \n      </table></td>\n      <td  valign=\"top\" class=\"leftcells\" width=\"15%\">");
/* 121 */       if (_jspx_meth_tiles_005finsert_005f2(_jspx_page_context))
/*     */         return;
/* 123 */       out.write("\n      </td>\n  </tr>\n</table>\n</body>\n</html>\n\n");
/*     */     } catch (Throwable t) {
/* 125 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 126 */         out = _jspx_out;
/* 127 */         if ((out != null) && (out.getBufferSize() != 0))
/* 128 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 129 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 132 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 138 */     PageContext pageContext = _jspx_page_context;
/* 139 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 141 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 142 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 143 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 145 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 147 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 148 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 149 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 150 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 151 */       return true;
/*     */     }
/* 153 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 154 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 159 */     PageContext pageContext = _jspx_page_context;
/* 160 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 162 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 163 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 164 */     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */     
/* 166 */     _jspx_th_tiles_005finsert_005f0.setAttribute("LeftArea");
/* 167 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 168 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 169 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 170 */       return true;
/*     */     }
/* 172 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 178 */     PageContext pageContext = _jspx_page_context;
/* 179 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 181 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 182 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 183 */     _jspx_th_tiles_005finsert_005f1.setParent(null);
/*     */     
/* 185 */     _jspx_th_tiles_005finsert_005f1.setAttribute("UserArea");
/* 186 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 187 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 188 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 189 */       return true;
/*     */     }
/* 191 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 192 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 197 */     PageContext pageContext = _jspx_page_context;
/* 198 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 200 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 201 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 202 */     _jspx_th_tiles_005finsert_005f2.setParent(null);
/*     */     
/* 204 */     _jspx_th_tiles_005finsert_005f2.setAttribute("ServerLeftArea");
/* 205 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 206 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 207 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 208 */       return true;
/*     */     }
/* 210 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 211 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\OpManagerLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */