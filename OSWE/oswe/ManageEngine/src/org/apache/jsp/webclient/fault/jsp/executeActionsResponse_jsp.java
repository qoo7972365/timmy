/*     */ package org.apache.jsp.webclient.fault.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class executeActionsResponse_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  26 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  27 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  49 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  56 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  59 */     JspWriter out = null;
/*  60 */     Object page = this;
/*  61 */     JspWriter _jspx_out = null;
/*  62 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  66 */       response.setContentType("text/html;charset=UTF-8");
/*  67 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  69 */       _jspx_page_context = pageContext;
/*  70 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  71 */       ServletConfig config = pageContext.getServletConfig();
/*  72 */       session = pageContext.getSession();
/*  73 */       out = pageContext.getOut();
/*  74 */       _jspx_out = out;
/*     */       
/*  76 */       out.write("<!DOCTYPE html>\n");
/*  77 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  78 */       out.write("\n\n\n\n\n\n\n\n<html>\n<head>\n<title>");
/*  79 */       out.print(com.adventnet.appmanager.util.FormatUtil.getString("webclient.fault.alarmdetails.executeAction.button.executeAction"));
/*  80 */       out.write("</title>\n");
/*  81 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  82 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  84 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  85 */       out.write(10);
/*  86 */       out.write("\n<LINK REL=\"SHORTCUT ICON\" HREF='");
/*  87 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/*  89 */       out.write(39);
/*  90 */       out.write(62);
/*  91 */       out.write("\n<script language=\"javascript\" src=\"/webclient/fault/js/fault.js\" type=\"text/javascript\"></script>\n\n<script>\n    window.opener.location.reload(true)\n</script>\n<script>\n\nfunction validateAction(errMsg)\n{\n\tdocument.executeAction.submit();\n\t\n}\n</script>\n</head>\n\n<body class=\"popupbg\" >\n\n\n\n\t<table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/*  92 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/*  94 */       out.write("</span><span class=\"headingwhite\"> </span>\n<input class=\"button\" onclick=\"javascript:window.close()\"type=\"button\" value='");
/*  95 */       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*     */         return;
/*  97 */       out.write("'/></td>\n</tr>\n\n\n</table>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/*  99 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 100 */         out = _jspx_out;
/* 101 */         if ((out != null) && (out.getBufferSize() != 0))
/* 102 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 103 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 106 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 112 */     PageContext pageContext = _jspx_page_context;
/* 113 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 115 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 116 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 117 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 119 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 121 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 122 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 123 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 124 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 125 */       return true;
/*     */     }
/* 127 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 133 */     PageContext pageContext = _jspx_page_context;
/* 134 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 136 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 137 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 138 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 140 */     _jspx_th_c_005fout_005f1.setValue("${faviconHref}");
/*     */     
/* 142 */     _jspx_th_c_005fout_005f1.setDefault("/favicon.ico");
/* 143 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 144 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 145 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 146 */       return true;
/*     */     }
/* 148 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 149 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 154 */     PageContext pageContext = _jspx_page_context;
/* 155 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 157 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 158 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 159 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 161 */     _jspx_th_fmt_005fmessage_005f0.setKey("webclient.fault.alarm.executeAction.header.text");
/* 162 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 163 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 164 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 165 */       return true;
/*     */     }
/* 167 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 168 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 173 */     PageContext pageContext = _jspx_page_context;
/* 174 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 176 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 177 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 178 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*     */     
/* 180 */     _jspx_th_fmt_005fmessage_005f1.setKey("webclient.fault.alarm.executeAction.close");
/* 181 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 182 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 183 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 184 */       return true;
/*     */     }
/* 186 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 187 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\fault\jsp\executeActionsResponse_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */