/*     */ package org.apache.jsp.webclient.common.jsp;
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
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class rightLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  24 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/*  25 */   static { _jspx_dependants.put("/WEB-INF/struts-tiles.tld", Long.valueOf(1473429283000L));
/*  26 */     _jspx_dependants.put("/webclient/common/jspf/commonIncludes.jspf", Long.valueOf(1473429417000L));
/*     */   }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  36 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  40 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  43 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  47 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  48 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  55 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  58 */     JspWriter out = null;
/*  59 */     Object page = this;
/*  60 */     JspWriter _jspx_out = null;
/*  61 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  65 */       response.setContentType("text/html;charset=UTF-8");
/*  66 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  68 */       _jspx_page_context = pageContext;
/*  69 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  70 */       ServletConfig config = pageContext.getServletConfig();
/*  71 */       session = pageContext.getSession();
/*  72 */       out = pageContext.getOut();
/*  73 */       _jspx_out = out;
/*     */       
/*  75 */       out.write("\n\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\">\n\n\n\n\n\n<html>\n<head>\n<title>");
/*  76 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/*  78 */       out.write("</title>\n");
/*  79 */       out.write("\n</head>\n<BODY>\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr class=\"tabsBg\">\n  <td>\n    ");
/*  80 */       if (_jspx_meth_tiles_005finsert_005f0(_jspx_page_context))
/*     */         return;
/*  82 */       out.write("\n  </td>\n</tr>\n<tr>\n  <td>\n    ");
/*  83 */       if (_jspx_meth_tiles_005finsert_005f1(_jspx_page_context))
/*     */         return;
/*  85 */       out.write("\n  </td>\n</tr>\n</table>\n\n</BODY>\n</html>\n");
/*     */     } catch (Throwable t) {
/*  87 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  88 */         out = _jspx_out;
/*  89 */         if ((out != null) && (out.getBufferSize() != 0))
/*  90 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  91 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/*  94 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 100 */     PageContext pageContext = _jspx_page_context;
/* 101 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 103 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 104 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 105 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 107 */     _jspx_th_fmt_005fmessage_005f0.setKey("webclient.mainviewpage.title");
/* 108 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 109 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 110 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 111 */       return true;
/*     */     }
/* 113 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 119 */     PageContext pageContext = _jspx_page_context;
/* 120 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 122 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 123 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 124 */     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */     
/* 126 */     _jspx_th_tiles_005finsert_005f0.setAttribute("Tab");
/* 127 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 128 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 129 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 130 */       return true;
/*     */     }
/* 132 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 133 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 138 */     PageContext pageContext = _jspx_page_context;
/* 139 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 141 */     InsertTag _jspx_th_tiles_005finsert_005f1 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 142 */     _jspx_th_tiles_005finsert_005f1.setPageContext(_jspx_page_context);
/* 143 */     _jspx_th_tiles_005finsert_005f1.setParent(null);
/*     */     
/* 145 */     _jspx_th_tiles_005finsert_005f1.setAttribute("MainPage");
/* 146 */     int _jspx_eval_tiles_005finsert_005f1 = _jspx_th_tiles_005finsert_005f1.doStartTag();
/* 147 */     if (_jspx_th_tiles_005finsert_005f1.doEndTag() == 5) {
/* 148 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 149 */       return true;
/*     */     }
/* 151 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f1);
/* 152 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\common\jsp\rightLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */