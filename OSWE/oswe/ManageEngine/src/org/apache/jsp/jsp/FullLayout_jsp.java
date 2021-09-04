/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.ErrorsTag;
/*     */ import org.apache.struts.taglib.tiles.GetAttributeTag;
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ 
/*     */ public final class FullLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  25 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/*  26 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/*  27 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*  28 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*     */   }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  39 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  43 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  47 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  51 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.release();
/*  52 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  53 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  60 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  63 */     JspWriter out = null;
/*  64 */     Object page = this;
/*  65 */     JspWriter _jspx_out = null;
/*  66 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  70 */       response.setContentType("text/html");
/*  71 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  73 */       _jspx_page_context = pageContext;
/*  74 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  75 */       ServletConfig config = pageContext.getServletConfig();
/*  76 */       session = pageContext.getSession();
/*  77 */       out = pageContext.getOut();
/*  78 */       _jspx_out = out;
/*     */       
/*  80 */       out.write("<!DOCTYPE html>\n");
/*  81 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  82 */       out.write("\n\n\n\n\n\n    \n<html>\n<head>\n<title>");
/*  83 */       if (_jspx_meth_tiles_005fgetAsString_005f0(_jspx_page_context))
/*     */         return;
/*  85 */       out.write("</title>\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
/*  86 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/<c:out value = \"${selectedskin}\" default=\"${initParam.defaultSkin}\"/>/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  87 */       out.write(10);
/*  88 */       out.write(10);
/*  89 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  90 */       out.write(10);
/*  91 */       out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/*  92 */       out.print(request.getContextPath());
/*  93 */       out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/*  94 */       out.print(request.getContextPath());
/*  95 */       out.write("'); //No I18N\n</script>\n");
/*  96 */       if (com.adventnet.appmanager.util.Constants.isIt360) {
/*  97 */         out.write("<script src='");
/*  98 */         out.print(request.getContextPath());
/*  99 */         out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*     */       }
/* 101 */       out.write("\n</head>\n\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
/* 102 */       if (_jspx_meth_tiles_005finsert_005f0(_jspx_page_context))
/*     */         return;
/* 104 */       out.write(" \n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr> \n<td >&nbsp;</td>\n</tr>\n<tr>\n\n<td  align=\"center\" valign=\"top\">\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">    \n\t");
/* 105 */       if (_jspx_meth_html_005ferrors_005f0(_jspx_page_context))
/*     */         return;
/* 107 */       out.write("        \n</table>\n");
/* 108 */       if (_jspx_meth_tiles_005finsert_005f1(_jspx_page_context))
/*     */         return;
/* 110 */       out.write("\n</td> \n</tr>\n</table>\n\n");
/* 111 */       if (_jspx_meth_tiles_005finsert_005f2(_jspx_page_context))
/*     */         return;
/* 113 */       out.write("\n\n</body>\n</html>\n\n");
/*     */     } catch (Throwable t) {
/* 115 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 116 */         out = _jspx_out;
/* 117 */         if ((out != null) && (out.getBufferSize() != 0))
/* 118 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 119 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 122 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fgetAsString_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 128 */     PageContext pageContext = _jspx_page_context;
/* 129 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 131 */     GetAttributeTag _jspx_th_tiles_005fgetAsString_005f0 = (GetAttributeTag)this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.get(GetAttributeTag.class);
/* 132 */     _jspx_th_tiles_005fgetAsString_005f0.setPageContext(_jspx_page_context);
/* 133 */     _jspx_th_tiles_005fgetAsString_005f0.setParent(null);
/*     */     
/* 135 */     _jspx_th_tiles_005fgetAsString_005f0.setName("title");
/* 136 */     int _jspx_eval_tiles_005fgetAsString_005f0 = _jspx_th_tiles_005fgetAsString_005f0.doStartTag();
/* 137 */     if (_jspx_th_tiles_005fgetAsString_005f0.doEndTag() == 5) {
/* 138 */       this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 139 */       return true;
/*     */     }
/* 141 */     this._005fjspx_005ftagPool_005ftiles_005fgetAsString_0026_005fname_005fnobody.reuse(_jspx_th_tiles_005fgetAsString_005f0);
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 147 */     PageContext pageContext = _jspx_page_context;
/* 148 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 150 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 151 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 152 */     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */     
/* 154 */     _jspx_th_tiles_005finsert_005f0.setAttribute("Header");
/* 155 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 156 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 157 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 158 */       return true;
/*     */     }
/* 160 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 161 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ferrors_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 166 */     PageContext pageContext = _jspx_page_context;
/* 167 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 169 */     ErrorsTag _jspx_th_html_005ferrors_005f0 = (ErrorsTag)this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.get(ErrorsTag.class);
/* 170 */     _jspx_th_html_005ferrors_005f0.setPageContext(_jspx_page_context);
/* 171 */     _jspx_th_html_005ferrors_005f0.setParent(null);
/* 172 */     int _jspx_eval_html_005ferrors_005f0 = _jspx_th_html_005ferrors_005f0.doStartTag();
/* 173 */     if (_jspx_th_html_005ferrors_005f0.doEndTag() == 5) {
/* 174 */       this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 175 */       return true;
/*     */     }
/* 177 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 178 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 183 */     PageContext pageContext = _jspx_page_context;
/* 184 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 186 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 187 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 188 */     _jspx_th_tiles_005finsert_005f1.setParent(null);
/*     */     
/* 190 */     _jspx_th_tiles_005finsert_005f1.setAttribute("UserArea");
/* 191 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 192 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 193 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 194 */       return true;
/*     */     }
/* 196 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 197 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 202 */     PageContext pageContext = _jspx_page_context;
/* 203 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 205 */     InsertTag _jspx_th_tiles_005finsert_005f2 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 206 */     _jspx_th_tiles_005finsert_005f2.setPageContext(_jspx_page_context);
/* 207 */     _jspx_th_tiles_005finsert_005f2.setParent(null);
/*     */     
/* 209 */     _jspx_th_tiles_005finsert_005f2.setAttribute("footer");
/* 210 */     int _jspx_eval_tiles_005finsert_005f2 = _jspx_th_tiles_005finsert_005f2.doStartTag();
/* 211 */     if (_jspx_th_tiles_005finsert_005f2.doEndTag() == 5) {
/* 212 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 213 */       return true;
/*     */     }
/* 215 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f2);
/* 216 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\FullLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */