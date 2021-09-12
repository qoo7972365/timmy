/*     */ package org.apache.jsp.webclient.mobile.jsp;
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
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class MobileWidget_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  24 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  25 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  34 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  38 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  51 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  54 */     JspWriter out = null;
/*  55 */     Object page = this;
/*  56 */     JspWriter _jspx_out = null;
/*  57 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  61 */       response.setContentType("text/html;charset=UTF-8");
/*  62 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  64 */       _jspx_page_context = pageContext;
/*  65 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  66 */       ServletConfig config = pageContext.getServletConfig();
/*  67 */       session = pageContext.getSession();
/*  68 */       out = pageContext.getOut();
/*  69 */       _jspx_out = out;
/*     */       
/*  71 */       out.write("<!DOCTYPE html>\n<!--  Do not move down the above DOCTYPE definition(let that be first line) -->\n");
/*  72 */       out.write("\n\n\n<head>\n<title>");
/*  73 */       out.print(request.getParameter("dashboard"));
/*  74 */       out.write(32);
/*  75 */       out.write(58);
/*  76 */       out.write(32);
/*  77 */       out.print(request.getParameter("widget"));
/*  78 */       out.write("</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />      \n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\" />\n<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />\n<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black-translucent\" />\n<head>\n<link href=\"/images/mobile/mobile.css\" rel=\"stylesheet\" type=\"text/css\" />\n<style>\n\t.darkheaderbg {\n\t\tbackground-color: #468ED5;\n\t\tpadding: 0px 0px 0px 10px;\n\t\tposition:fixed;\n\t\tz-index:100;\n\t}\n\t.widgetwrapper{\n\t\tposition:relative;\n\t\ttop:55px;\n\t}\n</style>\n");
/*  79 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  80 */       out.write("\n<script src=\"/template/mobile.js\"></script>\n<script scr=\"/template/appmanager.js\"></script>\n<script>\n$(document).ready(function() {\n\tmyOnLoad();\n\t});\n</script>\n</head>\n<body>\t\n\t<table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n\t\t<tr>\n\t\t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/*  81 */       out.print(request.getParameter("dashboard"));
/*  82 */       out.write(" : <span style=\"font-size:0.8em;\">");
/*  83 */       out.print(request.getParameter("widget"));
/*  84 */       out.write(" </span></span></td><td align=\"right\"><input type=\"button\" onclick=\"javascript:history.back();\" value='");
/*  85 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/*  87 */       out.write("'></td>\n\t\t</tr>\n\t</table>\n\t<div id=\"wrapper\" class=\"widgetwrapper\">\n        <table width=\"100%\"  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t<tr>\n\t\t\t\t<td><div id=\"widget\"></div></td>\n\t\t\t</tr>\n\t\t</table>\n\t</div>\n</body>\n</html>");
/*     */     } catch (Throwable t) {
/*  89 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  90 */         out = _jspx_out;
/*  91 */         if ((out != null) && (out.getBufferSize() != 0))
/*  92 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  93 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/*  96 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 102 */     PageContext pageContext = _jspx_page_context;
/* 103 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 105 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 106 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 107 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 109 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.common.close.text");
/* 110 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 111 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 112 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 113 */       return true;
/*     */     }
/* 115 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 116 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\mobile\jsp\MobileWidget_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */