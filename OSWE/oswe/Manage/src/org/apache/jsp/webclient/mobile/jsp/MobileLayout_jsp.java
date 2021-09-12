/*     */ package org.apache.jsp.webclient.mobile.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class MobileLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  25 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  26 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  38 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  42 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  47 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  51 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  52 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  53 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.release();
/*  54 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  61 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  64 */     JspWriter out = null;
/*  65 */     Object page = this;
/*  66 */     JspWriter _jspx_out = null;
/*  67 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  71 */       response.setContentType("text/html;charset=UTF-8");
/*  72 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  74 */       _jspx_page_context = pageContext;
/*  75 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  76 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  77 */       session = pageContext.getSession();
/*  78 */       out = pageContext.getOut();
/*  79 */       _jspx_out = out;
/*     */       
/*  81 */       out.write("<!DOCTYPE html>\n<!--  Do not move down the above DOCTYPE definition(let that be first line) -->\n");
/*  82 */       out.write("\n<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0\" />\n<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />\n\n\n\n\n\n\n<html>\n\t<head>\n\t\t<title>");
/*  83 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/*  85 */       out.write("</title>\n\t\t<link rel=\"apple-touch-icon-precomposed\" href=\"/images/mobile/me_apm_icon.png\"/>\n\t\t<link rel=\"apple-touch-startup-image\" href=\"/images/mobile/apm_startup.png\"/>\n\t</head>\n\t<link href=\"/images/mobile/mobile.css\" rel=\"stylesheet\" type=\"text/css\" />\n\t");
/*  86 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  87 */       out.write("\n\t<script src=\"/template/mobile.js\" type=\"text/javascript\"></script>\n\t<style type=\"text/css\">\n\t\thtml, body {-webkit-text-size-adjust: none;-webkit-touch-callout: none;-webkit-user-select: none;} \n\t\t/* This will avoid pop-up for saving images and iphone copy functions -webkit-tap-highlight-color: rgba(0,0,0,0);*/ \n\t</style>\n\t<script type=\"text/javascript\">\n\t\t$(document).ready(function() {\n\t\t\tbindEvents();\n\t\t\tcontrolDropdown();\n\t\t\t$('img').css('border',0);\t\t\t//No I18N\t\n\t\t});\n\t</script>\n\t<body style=\"-webkit-transition: all 2s  ease-in-out;\">\n\t\t<div id=\"titleDiv\">\n\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"1%\" style=\"position: absolute; top: 15px;\">\n\t\t\t\t\t\t<a href=\"javascript:history.back();\"><img height=\"16\" width=\"11\" style=\"padding-left: 10px;\" src=\"/images/mobile/arrow-left.gif\"/></a>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td align=\"center\" width=\"98%\">\n\t\t\t\t\t\t<div id=\"logoDiv\" class=\"logo\" width=\"100%\"><img src=\"/images/mobile/spacer.gif\" onclick=\"javascript:location.href='/mobile/HomeDetails.do?method=showHomePage'\" width=\"175\" height=\"24\" class=\"logoBGsmall\" alt=\"Applications Manager Logo\" />\n");
/*  88 */       out.write("\t\t\t\t\t\t\t<span class=\"navi-left-sec\">\n\t\t\t\t                <a href=\"javascript:void(0)\" class=\"dropdown\"><img border=\"0\" align=\"top\" width=\"15\" height=\"15\" style=\"padding-top: 20px; margin-left: -3px;\" src=\"/images/mobile/dropdown.png\"></a>\n\t\t\t\t\t\t\t\t<ul id=\"infra-dropdown\" style=\"display:none; position: relative;\">\n\t\t\t\t\t\t\t\t  <img src=\"/images/mobile/infra-drop-arrow.png\" width=\"33\" height=\"11\" class=\"infra-drop-arrow\">\n\t\t\t\t\t\t\t\t  <div class=\"infra-drop-box\" align=\"left\">\n\t\t\t\t\t\t\t\t  \t<li><a href=\"javascript:location.href='/mobile/overview.do?method=infrastructureView'\"><img class=\"infra-dropdown-img\" src=\"/images/spacer.gif\" width=\"15\" height=\"21\" border=\"0\" align=\"absmiddle\">");
/*  89 */       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*     */         return;
/*  91 */       out.write("</a></li>\n\t\t\t\t\t\t\t\t  \t<li><a href=\"javascript:location.href='/mobile/overview.do?method=monitorGroupView'\"><img class=\"mggrp-dropdown-img\" src=\"/images/spacer.gif\" width=\"15\" height=\"21\" border=\"0\" align=\"absmiddle\">");
/*  92 */       if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*     */         return;
/*  94 */       out.write("</a> </li>\n\t\t\t\t\t\t\t\t  \t<li><a href=\"javascript:location.href='/mobile/overview.do?method=showDashboards'\"><img class=\"dashbrd-dropdown-img\" src=\"/images/spacer.gif\" width=\"15\" height=\"21\" border=\"0\" align=\"absmiddle\">");
/*  95 */       if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*     */         return;
/*  97 */       out.write("</a></li>\n\t\t\t\t\t\t\t\t  \t<li><a href=\"javascript:location.href='/mobile/AlarmViewAction.do?method=listAlarms&type=critical_warning&viewName=Alarms'\"><img class=\"alarm-dropdown-img\" src=\"/images/spacer.gif\" width=\"15\" height=\"21\" border=\"0\" align=\"absmiddle\">");
/*  98 */       if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*     */         return;
/* 100 */       out.write("</a></li>\n\t\t\t\t\t\t\t\t  \t<li><a href=\"javascript:location.href='/mobile/overview.do?method=downDevicesViewAction'\"><img class=\"down-dropdown-img\" src=\"/images/spacer.gif\" width=\"14\" height=\"22\" border=\"0\" align=\"absmiddle\">");
/* 101 */       if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*     */         return;
/* 103 */       out.write("</a></li>\n\t\t\t\t\t\t\t\t  \t<li><a href=\"javascript:location.href='/mobile/Search.do?method=mobileSearch&viewName=Search'\"><img class=\"search-dropdown-img\" src=\"/images/spacer.gif\" width=\"15\" height=\"21\" border=\"0\" align=\"absmiddle\">");
/* 104 */       if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*     */         return;
/* 106 */       out.write("</a></li>\n\t\t\t\t\t\t\t\t\t");
/* 107 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/* 109 */       out.write("\n\t\t\t\t\t\t\t\t  </div>\n\t\t\t\t\t\t\t\t</ul>             \n\t\t\t\t        \t</span> \n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"1%\">\n\t\t\t\t\t\t<img height=\"16\" width=\"11\" style=\"padding-top: 10px; padding-left: 10px;\" src=\"/images/mobile/spacer.gif\"/>\n\t\t\t\t\t</td>\t\t\t\t\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</div>\n\t\t<span style=\"clear:both\"></span>\n\t\t<div id=\"titleDiv1\">\n\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"1%\" style=\"position: absolute; top: 15px;\">\n\t\t\t\t\t\t<a href=\"javascript:history.back();\"><img height=\"16\" width=\"11\" style=\"padding-left: 10px;\" src=\"/images/mobile/arrow-left.gif\"/></a>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td align=\"center\" width=\"98%\">\n\t\t\t\t\t\t<div id=\"logoDiv\" class=\"logo\" width=\"100%\"><img src=\"/images/mobile/spacer.gif\" onclick=\"javascript:location.href='/mobile/HomeDetails.do?method=showHomePage'\" width=\"175\" height=\"24\" class=\"logoBGsmall\" alt=\"Applications Manager Logo\" /></div>\n\t\t\t\t\t\t<span class=\"navi-left-sec\">\n\t\t\t                <a href=\"javascript:void(0)\" class=\"dropdown\"><img border=\"0\" align=\"top\" width=\"15\" height=\"15\" style=\"padding-top: 20px; margin-left: -3px;\" src=\"/images/mobile/dropdown.png\"></a>\n");
/* 110 */       out.write("\t\t\t        \t</span>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"1%\">\n\t\t\t\t\t\t<img height=\"16\" width=\"11\" style=\"padding-top: 10px; padding-left: 10px;\" src=\"/images/mobile/spacer.gif\"/>\n\t\t\t\t\t</td>\t\t\t\t\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</div>\n\t\t<div id=\"maincontent\" width=\"100%\">\n\t\t\t");
/* 111 */       if (_jspx_meth_tiles_005finsert_005f0(_jspx_page_context))
/*     */         return;
/* 113 */       out.write("\n\t\t</div>\n\t\t<div style=\"clear:both\"></div>\n\t\t<div id=\"footerDiv\">\n\t\t<table width=\"100%\" height=\"44\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"tableMenu\">\n\t\t\t<tr>\n\t\t\t\t<td width=\"25%\" align=\"center\" valign=\"middle\" onclick=\"location.href='/mobile/HomeDetails.do?method=showHomePage'\"><img src=\"/images/mobile/spacer.gif\" width=\"27\" height=\"21\" class=\"btnHome\"/>");
/* 114 */       if (_jspx_meth_fmt_005fmessage_005f8(_jspx_page_context))
/*     */         return;
/* 116 */       out.write("</td>\n\t\t\t\t<td width=\"25%\" align=\"center\" valign=\"middle\" ");
/* 117 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */         return;
/* 119 */       out.write(" onclick=\"javascript:location.href='/mobile/AlarmViewAction.do?method=listAlarms&type=critical_warning&viewName=Alarms';\"><img src=\"/images/mobile/spacer.gif\" width=\"27\" height=\"21\" class=\"btnAlarms\"/>");
/* 120 */       if (_jspx_meth_fmt_005fmessage_005f9(_jspx_page_context))
/*     */         return;
/* 122 */       out.write(" </td>");
/* 123 */       out.write(" \n\t\t\t\t<td width=\"25%\" align=\"center\" valign=\"middle\" ");
/* 124 */       if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*     */         return;
/* 126 */       out.write(" onclick=\"location.href='/mobile/overview.do?method=downDevicesViewAction&viewName=DownDevices'\"><img src=\"/images/mobile/spacer.gif\" width=\"27\" height=\"21\" class=\"btnDwnDevices\"/>");
/* 127 */       if (_jspx_meth_fmt_005fmessage_005f10(_jspx_page_context))
/*     */         return;
/* 129 */       out.write(" </td>");
/* 130 */       out.write(" \n\t\t\t\t<td width=\"25%\" align=\"center\" valign=\"middle\" ");
/* 131 */       if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*     */         return;
/* 133 */       out.write(" onclick=\"javascript:location.href='/mobile/Search.do?method=mobileSearch&viewName=Search'\"><img src=\"/images/mobile/spacer.gif\" width=\"27\" height=\"21\" class=\"btnSearch\"/>");
/* 134 */       if (_jspx_meth_fmt_005fmessage_005f11(_jspx_page_context))
/*     */         return;
/* 136 */       out.write("</td>");
/* 137 */       out.write(" \n\t\t\t</tr>\n\t\t</table>\n\t\t</div>\n\t\t");
/* 138 */       if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*     */         return;
/* 140 */       out.write("\n\t<body>\n</html>");
/*     */     } catch (Throwable t) {
/* 142 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 143 */         out = _jspx_out;
/* 144 */         if ((out != null) && (out.getBufferSize() != 0))
/* 145 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 146 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 149 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 155 */     PageContext pageContext = _jspx_page_context;
/* 156 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 158 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 159 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 160 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 162 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.mobile.login.title.text");
/* 163 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 164 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 165 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 166 */       return true;
/*     */     }
/* 168 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 169 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 174 */     PageContext pageContext = _jspx_page_context;
/* 175 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 177 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 178 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 179 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*     */     
/* 181 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.mobile.infrastructure.txt");
/* 182 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 183 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 184 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 185 */       return true;
/*     */     }
/* 187 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 193 */     PageContext pageContext = _jspx_page_context;
/* 194 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 196 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 197 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 198 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/*     */     
/* 200 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.mobile.monitorgroups.txt");
/* 201 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 202 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 203 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 204 */       return true;
/*     */     }
/* 206 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 207 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 212 */     PageContext pageContext = _jspx_page_context;
/* 213 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 215 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 216 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 217 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/*     */     
/* 219 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.mobile.dashboards.txt");
/* 220 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 221 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 222 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 223 */       return true;
/*     */     }
/* 225 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 226 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 231 */     PageContext pageContext = _jspx_page_context;
/* 232 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 234 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 235 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 236 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/*     */     
/* 238 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.mobile.alarms.txt");
/* 239 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 240 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 241 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 242 */       return true;
/*     */     }
/* 244 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 245 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 250 */     PageContext pageContext = _jspx_page_context;
/* 251 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 253 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 254 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 255 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/*     */     
/* 257 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.mobile.downdevices.txt");
/* 258 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 259 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 260 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 261 */       return true;
/*     */     }
/* 263 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 264 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 269 */     PageContext pageContext = _jspx_page_context;
/* 270 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 272 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 273 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 274 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/*     */     
/* 276 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.mobile.search.txt");
/* 277 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 278 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 279 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 280 */       return true;
/*     */     }
/* 282 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 283 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 288 */     PageContext pageContext = _jspx_page_context;
/* 289 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 291 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 292 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 293 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 295 */     _jspx_th_c_005fif_005f0.setTest("${serverType!='EnterpriseAdmin'}");
/* 296 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 297 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 299 */         out.write("\n\t\t\t\t\t\t\t\t\t\t<li><a href=\"javascript:location.href='/mobile/overview.do?method=listActions'\"><img class=\"action-dropdown-img\" src=\"/images/spacer.gif\" width=\"15\" height=\"21\" border=\"0\" align=\"absmiddle\">");
/* 300 */         if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 301 */           return true;
/* 302 */         out.write("</a></li>\n\t\t\t\t\t\t\t\t\t");
/* 303 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 304 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 308 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 309 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 310 */       return true;
/*     */     }
/* 312 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 313 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 318 */     PageContext pageContext = _jspx_page_context;
/* 319 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 321 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 322 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 323 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 325 */     _jspx_th_fmt_005fmessage_005f7.setKey("Actions");
/* 326 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 327 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 328 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 329 */       return true;
/*     */     }
/* 331 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 332 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005finsert_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 337 */     PageContext pageContext = _jspx_page_context;
/* 338 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 340 */     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.get(InsertTag.class);
/* 341 */     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 342 */     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */     
/* 344 */     _jspx_th_tiles_005finsert_005f0.setAttribute("MainPage");
/* 345 */     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 346 */     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 347 */       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 348 */       return true;
/*     */     }
/* 350 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fattribute_005fnobody.reuse(_jspx_th_tiles_005finsert_005f0);
/* 351 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f8(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 356 */     PageContext pageContext = _jspx_page_context;
/* 357 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 359 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 360 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 361 */     _jspx_th_fmt_005fmessage_005f8.setParent(null);
/*     */     
/* 363 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.mobile.home.txt");
/* 364 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 365 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 366 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 367 */       return true;
/*     */     }
/* 369 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 370 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 375 */     PageContext pageContext = _jspx_page_context;
/* 376 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 378 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 379 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 380 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 382 */     _jspx_th_c_005fif_005f1.setTest("${viewName=='Alarms'}");
/* 383 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 384 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 386 */         out.write(" class=\"menuTdBg\"");
/* 387 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 388 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 392 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 393 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 394 */       return true;
/*     */     }
/* 396 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 397 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f9(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 402 */     PageContext pageContext = _jspx_page_context;
/* 403 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 405 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 406 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 407 */     _jspx_th_fmt_005fmessage_005f9.setParent(null);
/*     */     
/* 409 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.mobile.alarms.txt");
/* 410 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 411 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 412 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 413 */       return true;
/*     */     }
/* 415 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 416 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 421 */     PageContext pageContext = _jspx_page_context;
/* 422 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 424 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 425 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 426 */     _jspx_th_c_005fif_005f2.setParent(null);
/*     */     
/* 428 */     _jspx_th_c_005fif_005f2.setTest("${viewName=='DownDevices'}");
/* 429 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 430 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 432 */         out.write(" class=\"menuTdBg\"");
/* 433 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 434 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 438 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 439 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 440 */       return true;
/*     */     }
/* 442 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 443 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f10(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 448 */     PageContext pageContext = _jspx_page_context;
/* 449 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 451 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 452 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 453 */     _jspx_th_fmt_005fmessage_005f10.setParent(null);
/*     */     
/* 455 */     _jspx_th_fmt_005fmessage_005f10.setKey("am.mobile.downdevices.txt");
/* 456 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 457 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 458 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 459 */       return true;
/*     */     }
/* 461 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 462 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 467 */     PageContext pageContext = _jspx_page_context;
/* 468 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 470 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 471 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 472 */     _jspx_th_c_005fif_005f3.setParent(null);
/*     */     
/* 474 */     _jspx_th_c_005fif_005f3.setTest("${viewName=='Search'}");
/* 475 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 476 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */       for (;;) {
/* 478 */         out.write(" class=\"menuTdBg\"");
/* 479 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 480 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 484 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 485 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 486 */       return true;
/*     */     }
/* 488 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 489 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f11(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 494 */     PageContext pageContext = _jspx_page_context;
/* 495 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 497 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 498 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 499 */     _jspx_th_fmt_005fmessage_005f11.setParent(null);
/*     */     
/* 501 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.mobile.search.txt");
/* 502 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 503 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 504 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 505 */       return true;
/*     */     }
/* 507 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 508 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 513 */     PageContext pageContext = _jspx_page_context;
/* 514 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 516 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 517 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 518 */     _jspx_th_c_005fif_005f4.setParent(null);
/*     */     
/* 520 */     _jspx_th_c_005fif_005f4.setTest("${!empty success}");
/* 521 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 522 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */       for (;;) {
/* 524 */         out.write("\n\t\t<script>alert(\"");
/* 525 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 526 */           return true;
/* 527 */         out.write("\");</script>\n\t\t");
/* 528 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 529 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 533 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 534 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 535 */       return true;
/*     */     }
/* 537 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 538 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 543 */     PageContext pageContext = _jspx_page_context;
/* 544 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 546 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 547 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 548 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*     */     
/* 550 */     _jspx_th_c_005fout_005f0.setValue("${success}");
/* 551 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 552 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 553 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 554 */       return true;
/*     */     }
/* 556 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 557 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\mobile\jsp\MobileLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */