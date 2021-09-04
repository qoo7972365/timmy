/*     */ package org.apache.jsp.webclient.mobile.jsp;
/*     */ 
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class MobileHomePage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  24 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  25 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  39 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  43 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  50 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  54 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  55 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  57 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  58 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  59 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  66 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  69 */     JspWriter out = null;
/*  70 */     Object page = this;
/*  71 */     JspWriter _jspx_out = null;
/*  72 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  76 */       response.setContentType("text/html;charset=UTF-8");
/*  77 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  79 */       _jspx_page_context = pageContext;
/*  80 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  81 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  82 */       session = pageContext.getSession();
/*  83 */       out = pageContext.getOut();
/*  84 */       _jspx_out = out;
/*     */       
/*  86 */       out.write("<!DOCTYPE html>\n<!--  Do not move down the above DOCTYPE definition(let that be first line) -->\n");
/*  87 */       out.write("\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n\t\n\t\n\t\n\t\n\t<head>\n\t\t<title>");
/*  88 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/*  90 */       out.write("</title>\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n\t\t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0\">\n\t\t<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n\t\t<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />\n\t\t<link href=\"/images/mobile/mobile.css\" rel=\"stylesheet\" type=\"text/css\" />\n\t\t<link rel=\"apple-touch-icon-precomposed\" href=\"/images/mobile/me_apm_icon.png\"/>\n\t\t<link rel=\"apple-touch-startup-image\" href=\"/images/mobile/apm_startup.png\"/>\n\t</head>\n\t");
/*  91 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  92 */       out.write("\n\t<script type=\"text/javascript\">\n\t$(document).ready(function() {\n\t\twindow.addEventListener(\"load\",function() {\n\t\t  setTimeout(function(){\n\t\t    // Hide the address bar!\n\t\t    window.scrollTo(0, 1);\n\t\t  }, 10);\n\t\t});\n\t\t\n\t\t//Used to set the Height of the screen in server, so that it will serve us the rows \n\t\t$.ajax({\n\t\t\t  url: \"/mobile/HomeDetails.do\",//NO I18N\n\t\t\t  data:\"method=setEnvironment&pageScreenHeight=\"+window.innerHeight,//NO I18N\n\t\t\t  dataType:\"text\",\t\t//NO I18N\n\t\t\t  success: function(html){\n\t\t\t\t  //alert('hi')\n\t\t\t\t}\n\t\t\t});\t\n\t});\n\t</script>\n\t<body style=\"background:#264a7a url(/images/mobile/bg.png) repeat-x scroll left bottom;\">\n\t\t<div id=\"contentDiv\" class=\"indexBG\"   ui-background-style=\"striped\" ui-navigation-status=\"current\">\n\t\t\t<div >\n\t\t\t\t<div id=\"\" style=\"display:inline;margin-left:25px;\" align=\"center\"><img src=\"/images/mobile/spacer.gif\" width=\"205\" height=\"69\" class=\"logoBGlarge\"/></div>\n\t\t\t\t<div class=\"signOut\"><a href=\"javascript:location.href='/Logout.do'\"><span  style=\"font-weight: bold;color:#EEE;text-shadow: 0px 1px Black;\">");
/*  93 */       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*     */         return;
/*  95 */       out.write("</span></a></div>\n\t\t\t\t");
/*  96 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/*  98 */       out.write("\n\t\t\t\t<table border=\"0\" cellspacing=\"6\" cellpadding=\"0\" class=\"homeMenu\" align=\"center\" width=\"320\" style=\"text-shadow: 0px 1px Black;\">\n\t\t\t\t\t<tr>\t\t\t\n\t\t\t\t\t\t<td width=\"90\" height=\"100\" align=\"center\" valign=\"top\" onclick=\"javascript:location.href='/mobile/overview.do?method=infrastructureView'\"><img src=\"/images/mobile/spacer.gif\" width=\"62\" height=\"63\" class=\"homeMenuBtn2\"/><b>");
/*  99 */       if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*     */         return;
/* 101 */       out.write("</b><br/><br/> </td>\n\t\t\t\t\t\t<td width=\"90\" height=\"100\" align=\"center\" valign=\"top\" onclick=\"javascript:location.href='/mobile/overview.do?method=monitorGroupView'\"><img src=\"/images/mobile/spacer.gif\" width=\"62\" height=\"63\" class=\"homeMenuBtn1\"/><b><span style=\"white-space: nowrap;\">");
/* 102 */       if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*     */         return;
/* 104 */       out.write("</span></b><br/><br/> </td>\n\t\t\t\t\t\t<td width=\"90\" height=\"100\" align=\"center\" valign=\"top\" onclick=\"javascript:location.href='/mobile/overview.do?method=showDashboards'\"><img src=\"/images/mobile/spacer.gif\" width=\"62\" height=\"63\" class=\"homeMenuBtn7\"/><b><span style=\"white-space: nowrap;\">");
/* 105 */       if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*     */         return;
/* 107 */       out.write("</span></b><br/><br/></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"90\" height=\"100\" align=\"center\" valign=\"top\" onclick=\"javascript:location.href='/mobile/AlarmViewAction.do?method=listAlarms&type=critical_warning&viewName=Alarms'\"><div id=\"alarmsDiv\"><img src=\"/images/mobile/spacer.gif\" width=\"44\" height=\"23\" class=\"intimationCount\"/><a href=\"javascript:location.href='/mobile/AlarmViewAction.do?method=listAlarms&type=critical&viewName=Alarms'\"><div style=\"text-shadow: 0px 1px black;\">");
/* 108 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/* 110 */       out.write("</div></a></div><img src=\"/images/mobile/spacer.gif\" width=\"62\" height=\"63\" class=\"homeMenuBtn4\"/><b><span style=\"white-space: nowrap;\">");
/* 111 */       if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*     */         return;
/* 113 */       out.write("</span></b></td>\n\t\t\t\t\t\t<td width=\"90\" height=\"100\" align=\"center\" valign=\"top\" onclick=\"javascript:location.href='/mobile/overview.do?method=downDevicesViewAction'\"><div id=\"deviceDownDiv\"><div>");
/* 114 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */         return;
/* 116 */       out.write("</div><img src=\"/images/mobile/spacer.gif\" width=\"44\" height=\"23\" class=\"intimationCount\"/></div><img src=\"/images/mobile/spacer.gif\" width=\"62\" height=\"63\" class=\"homeMenuBtn3\"/><b><span style=\"white-space: nowrap;\">");
/* 117 */       if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*     */         return;
/* 119 */       out.write("</span></b><br/><br/> </td>\n\t\t\t\t\t\t");
/* 120 */       if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*     */         return;
/* 122 */       out.write("\t\t\t\t\t\t\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t<div align=\"center\" style=\"color:#EEE;font-family: arial;font-size: 0.75em;text-shadow:0px -1px black;\">");
/* 123 */       if (_jspx_meth_fmt_005fmessage_005f9(_jspx_page_context))
/*     */         return;
/* 125 */       out.write("</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<script>\n\t\t</script>\n\t</body>\n</html>");
/*     */     } catch (Throwable t) {
/* 127 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 128 */         out = _jspx_out;
/* 129 */         if ((out != null) && (out.getBufferSize() != 0))
/* 130 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 131 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 134 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 140 */     PageContext pageContext = _jspx_page_context;
/* 141 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 143 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 144 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 145 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 147 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.mobile.login.title.text");
/* 148 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 149 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 150 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 151 */       return true;
/*     */     }
/* 153 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 154 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 159 */     PageContext pageContext = _jspx_page_context;
/* 160 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 162 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 163 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 164 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*     */     
/* 166 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.mobile.logout.txt");
/* 167 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 168 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 169 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 170 */       return true;
/*     */     }
/* 172 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 178 */     PageContext pageContext = _jspx_page_context;
/* 179 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 181 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 182 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 183 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 185 */     _jspx_th_c_005fif_005f0.setTest("${ProxyError != null}");
/* 186 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 187 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 189 */         out.write("\n\t\t\t\t\t<div width=\"90%\" align=center class=\"errorMsg\">");
/* 190 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 191 */           return true;
/* 192 */         out.write("</div>\n\t\t\t\t");
/* 193 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 194 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 198 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 199 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 200 */       return true;
/*     */     }
/* 202 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 208 */     PageContext pageContext = _jspx_page_context;
/* 209 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 211 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 212 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 213 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 215 */     _jspx_th_c_005fout_005f0.setValue("${ProxyError}");
/* 216 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 217 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 218 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 219 */       return true;
/*     */     }
/* 221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 227 */     PageContext pageContext = _jspx_page_context;
/* 228 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 230 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 231 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 232 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/*     */     
/* 234 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.mobile.infrastructure.txt");
/* 235 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 236 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 237 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 238 */       return true;
/*     */     }
/* 240 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 241 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 246 */     PageContext pageContext = _jspx_page_context;
/* 247 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 249 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 250 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 251 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/*     */     
/* 253 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.mobile.monitorgroups.txt");
/* 254 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 255 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 256 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 257 */       return true;
/*     */     }
/* 259 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 260 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 265 */     PageContext pageContext = _jspx_page_context;
/* 266 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 268 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 269 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 270 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/*     */     
/* 272 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.mobile.dashboards.txt");
/* 273 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 274 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 275 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 276 */       return true;
/*     */     }
/* 278 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 279 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 284 */     PageContext pageContext = _jspx_page_context;
/* 285 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 287 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 288 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 289 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 291 */     _jspx_th_c_005fout_005f1.setValue("${alarms}");
/* 292 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 293 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 294 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 295 */       return true;
/*     */     }
/* 297 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 298 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 303 */     PageContext pageContext = _jspx_page_context;
/* 304 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 306 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 307 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 308 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/*     */     
/* 310 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.mobile.alarms.txt");
/* 311 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 312 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 313 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 314 */       return true;
/*     */     }
/* 316 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 317 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 322 */     PageContext pageContext = _jspx_page_context;
/* 323 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 325 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 326 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 327 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 329 */     _jspx_th_c_005fout_005f2.setValue("${DownDevices}");
/* 330 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 331 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 332 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 333 */       return true;
/*     */     }
/* 335 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 336 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 341 */     PageContext pageContext = _jspx_page_context;
/* 342 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 344 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 345 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 346 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/*     */     
/* 348 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.mobile.downdevices.txt");
/* 349 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 350 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 351 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 352 */       return true;
/*     */     }
/* 354 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 355 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 360 */     PageContext pageContext = _jspx_page_context;
/* 361 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 363 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 364 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 365 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 366 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 367 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */       for (;;) {
/* 369 */         out.write("\n\t\t\t\t\t\t");
/* 370 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 371 */           return true;
/* 372 */         out.write("\n\t\t\t\t\t\t");
/* 373 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 374 */           return true;
/* 375 */         out.write("\n\t\t\t\t\t\t");
/* 376 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 377 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 381 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 382 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 383 */       return true;
/*     */     }
/* 385 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 386 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 391 */     PageContext pageContext = _jspx_page_context;
/* 392 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 394 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 395 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 396 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*     */     
/* 398 */     _jspx_th_c_005fwhen_005f0.setTest("${serverType!='EnterpriseAdmin'}");
/* 399 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 400 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */       for (;;) {
/* 402 */         out.write("\n\t\t\t\t\t\t\t<td width=\"90\" height=\"100\" align=\"center\" valign=\"top\" onclick=\"javascript:location.href='/mobile/overview.do?method=listActions'\"><img src=\"/images/mobile/spacer.gif\" width=\"63\" height=\"63\" class=\"homeMenuBtn8\"/><b><span style=\"white-space: nowrap;\">");
/* 403 */         if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 404 */           return true;
/* 405 */         out.write("</span></b></td>\n\t\t\t\t\t\t");
/* 406 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 407 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 411 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 412 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 413 */       return true;
/*     */     }
/* 415 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 416 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 421 */     PageContext pageContext = _jspx_page_context;
/* 422 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 424 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 425 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 426 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*     */     
/* 428 */     _jspx_th_fmt_005fmessage_005f7.setKey("Actions");
/* 429 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 430 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 431 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 432 */       return true;
/*     */     }
/* 434 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 435 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 440 */     PageContext pageContext = _jspx_page_context;
/* 441 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 443 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 444 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 445 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 446 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 447 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */       for (;;) {
/* 449 */         out.write("\n\t\t\t\t\t\t\t<td width=\"90\" height=\"100\" align=\"center\" valign=\"top\" onclick=\"javascript:location.href='/mobile/Search.do?method=mobileSearch'\"><img src=\"/images/mobile/spacer.gif\" width=\"62\" height=\"63\" class=\"homeMenuBtn5\"/><b><span style=\"white-space: nowrap;\">");
/* 450 */         if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 451 */           return true;
/* 452 */         out.write("</span></b></td>\n\t\t\t\t\t\t");
/* 453 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 454 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 458 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 459 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 460 */       return true;
/*     */     }
/* 462 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 463 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 468 */     PageContext pageContext = _jspx_page_context;
/* 469 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 471 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 472 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 473 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 475 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.mobile.search.txt");
/* 476 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 477 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 478 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 479 */       return true;
/*     */     }
/* 481 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 482 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f9(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 487 */     PageContext pageContext = _jspx_page_context;
/* 488 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 490 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 491 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 492 */     _jspx_th_fmt_005fmessage_005f9.setParent(null);
/*     */     
/* 494 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.mobileview.footer.txt");
/* 495 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 496 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 497 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 498 */       return true;
/*     */     }
/* 500 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 501 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\mobile\jsp\MobileHomePage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */