/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.MessageTag;
/*     */ import org.apache.struts.taglib.logic.MessagesNotPresentTag;
/*     */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*     */ 
/*     */ public final class StorageMappingPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fname;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fname;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  36 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  40 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  48 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fname.release();
/*  49 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.release();
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fname.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  57 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  60 */     JspWriter out = null;
/*  61 */     Object page = this;
/*  62 */     JspWriter _jspx_out = null;
/*  63 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  67 */       response.setContentType("text/html;charset=UTF-8");
/*  68 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  70 */       _jspx_page_context = pageContext;
/*  71 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  72 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  73 */       session = pageContext.getSession();
/*  74 */       out = pageContext.getOut();
/*  75 */       _jspx_out = out;
/*     */       
/*  77 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n<html>\n\t<head>\n\t\t<script type=\"text/javascript\" src=\"/template/Utils.js\"></script>\n\t\t");
/*  78 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  79 */       out.write("\n\t\t<script type=\"text/javascript\" src=\"/template/d3.v3.js\"></script>\n\t\t<script type=\"text/javascript\" src=\"/template/StorageMapping.js\"></script>\n\t\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n\t\t<style type=\"text/css\">\n\t\t\t.header2 {\n\t\t\tfont-family: Arial, Helvetica, sans-serif;\n\t\t\tfont-size: 14px;\n\t\t\tfont-style: normal;\n\t\t\tfont-weight: bold;\n\t\t\tfont-variant: normal;\n\t\t\ttext-transform: none;\n\t\t\tcolor: #F26522;\n\t\t\ttext-decoration: none;\n\t\t\tpadding: 3px 3px 3px 3px;\n\t\t\t}\n\n\t\t\t.report-heading-tile {\n\t\t\tborder-bottom: 1px solid \n\t\t\t#E8E8E8;\n\t\t\twidth: 96%;\n\t\t\theight: 3px;\n\t\t\t}\n\t\t\n\t\ta {\n\t\t  outline: none;\n\t\t  }\n\n\t\tbody{margin-left:0;margin-top:0;margin-right:0;margin-bottom:0; outline: none;}\n\t      #image_map{height:1500px; background:url(images/apm_link1.png) no-repeat; position:relative; outline:none; bottom:0px; top:20px;} \n\t\n\t\n\t\t.mseOut{background-color:none;}\t\n\t\t#apmmsg{width:1145px; height:50px; background:url(images/storagemap_msgbox.png) no-repeat; position:relative;}\n\t\t#apmmsg a{display:block;position:absolute;}\n");
/*  80 */       out.write("\t\t#apmmsg a#link6{width:126px; height:27px; top:10px; left:798px;}\n\t\t#apmmsg a#link7{width:126px; height:27px; top:10px; left:948px;}\n                #apmmsg a#link8{width:26px; height:27px; top:10px; left:1100px;}\t\n\t\t#msgboxtxt{display:block; position:absolute; width:720px; line-height:15px; height:27px; top:16px; left:10px; font:14px Arial, Helvetica, sans-serif;color:#595959; font-weight:bold;}\n\t\n\t\n\t      .storage-heading{ font-family:Verdana, Geneva, sans-serif; color:#595959; font-size:14px;}\n\t      .storage-desc{ font-family:Verdana, Geneva, sans-serif; font-size:11px; color:#595959; line-height:22px;}\n\n\t\n\t    </style>\n\t</head>\n\n\t<body>\n\n\t\t");
/*  81 */       out.write(10);
/*  82 */       out.write(9);
/*  83 */       out.write(9);
/*  84 */       if (_jspx_meth_logic_005fmessagesPresent_005f0(_jspx_page_context))
/*     */         return;
/*  86 */       out.write("\n\t\t\n\t\t\t \n\t\t\t");
/*  87 */       if (_jspx_meth_logic_005fmessagesNotPresent_005f0(_jspx_page_context))
/*     */         return;
/*  89 */       out.write("\n\t</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/*  91 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  92 */         out = _jspx_out;
/*  93 */         if ((out != null) && (out.getBufferSize() != 0))
/*  94 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  95 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/*  98 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fmessagesPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 104 */     PageContext pageContext = _jspx_page_context;
/* 105 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 107 */     MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fname.get(MessagesPresentTag.class);
/* 108 */     _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 109 */     _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*     */     
/* 111 */     _jspx_th_logic_005fmessagesPresent_005f0.setName("SAN_NO_OPSTOR");
/* 112 */     int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 113 */     if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */       for (;;) {
/* 115 */         out.write("\n\t\t<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:5px 0px 5px 15px;\" width=\"98%\">\n\t\t\t<tr>\n\t\t\t\t<td align=\"center\" width=100%'>\n\t\t\t\t\t\n\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  align=\"center\">\n\n\t\t\t\t\t<tbody><tr>\n\t\t\t\t\t<td aling=\"left\" colspan=\"4\" valign=\"top\">\n\t\n\t\t\t\t\t<div id=\"apmmsg\">\n\t\t\t\t\t<div id=\"msgboxtxt\">");
/* 116 */         if (_jspx_meth_bean_005fmessage_005f0(_jspx_th_logic_005fmessagesPresent_005f0, _jspx_page_context))
/* 117 */           return true;
/* 118 */         out.write("<span style=\"font-weight:normal; font-size:13px;\">&nbsp;(");
/* 119 */         if (_jspx_meth_bean_005fmessage_005f1(_jspx_th_logic_005fmessagesPresent_005f0, _jspx_page_context))
/* 120 */           return true;
/* 121 */         out.write(")</span></div>");
/* 122 */         out.write("  \n\t\t\t\t\t<a target=\"_blank\" href=\"https://www.manageengine.com/products/opstor/download.html\" id=\"link6\"><div style=\"width:126px; height:27px;\"></div></a>");
/* 123 */         out.write("\n\t\t\t\t\t<a target=\"_blank\" href=\"https://demo.opstor.com/\" id=\"link7\"><div style=\"width:126px; height:27px;\"></div></a>");
/* 124 */         out.write("\n\t\t\t\t\t<a target=\"_blank\" href=\"https://www.manageengine.com/products/applications_manager/help/monitors/san-monitoring-connector.html\" id=\"link8\"><div style=\"width:26px; height:27px;\"></div></a>");
/* 125 */         out.write("\t\n\n\t\t\t\t\t</div>\n\n\t\t\n\t\t\t\t\t</td>\n\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr><td colspan=\"4\" height=\"10\"></td></tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"10\"></td>\n\t\t\t\t\t<td valign=\"top\" width=\"430\"><h5 class=\"storage-heading\">");
/* 126 */         if (_jspx_meth_bean_005fmessage_005f2(_jspx_th_logic_005fmessagesPresent_005f0, _jspx_page_context))
/* 127 */           return true;
/* 128 */         out.write("</h5><div class=\"storage-desc\">");
/* 129 */         if (_jspx_meth_bean_005fmessage_005f3(_jspx_th_logic_005fmessagesPresent_005f0, _jspx_page_context))
/* 130 */           return true;
/* 131 */         out.write("</div></td>");
/* 132 */         out.write("\n\t\t\t\t\t<td width=\"10\"></td>\n\t\t\t\t\t <td><img src=\"images/opstor_connector.png\"></td>");
/* 133 */         out.write("\n\n\t\t\t\t      </tr>\n\t\t\t\t  </tbody></table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t\t");
/* 134 */         int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 135 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 139 */     if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 140 */       this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fname.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 141 */       return true;
/*     */     }
/* 143 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fname.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 144 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fmessage_005f0(JspTag _jspx_th_logic_005fmessagesPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 149 */     PageContext pageContext = _jspx_page_context;
/* 150 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 152 */     MessageTag _jspx_th_bean_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 153 */     _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 154 */     _jspx_th_bean_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fmessagesPresent_005f0);
/*     */     
/* 156 */     _jspx_th_bean_005fmessage_005f0.setKey("am.storage.monitoring.connector");
/* 157 */     int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 158 */     if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 159 */       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 160 */       return true;
/*     */     }
/* 162 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 163 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fmessage_005f1(JspTag _jspx_th_logic_005fmessagesPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 168 */     PageContext pageContext = _jspx_page_context;
/* 169 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 171 */     MessageTag _jspx_th_bean_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 172 */     _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 173 */     _jspx_th_bean_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fmessagesPresent_005f0);
/*     */     
/* 175 */     _jspx_th_bean_005fmessage_005f1.setKey("am.storage.monitoring.connector.text");
/* 176 */     int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
/* 177 */     if (_jspx_th_bean_005fmessage_005f1.doEndTag() == 5) {
/* 178 */       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
/* 179 */       return true;
/*     */     }
/* 181 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
/* 182 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fmessage_005f2(JspTag _jspx_th_logic_005fmessagesPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 187 */     PageContext pageContext = _jspx_page_context;
/* 188 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 190 */     MessageTag _jspx_th_bean_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 191 */     _jspx_th_bean_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 192 */     _jspx_th_bean_005fmessage_005f2.setParent((Tag)_jspx_th_logic_005fmessagesPresent_005f0);
/*     */     
/* 194 */     _jspx_th_bean_005fmessage_005f2.setKey("am.storage.monitoring.connector");
/* 195 */     int _jspx_eval_bean_005fmessage_005f2 = _jspx_th_bean_005fmessage_005f2.doStartTag();
/* 196 */     if (_jspx_th_bean_005fmessage_005f2.doEndTag() == 5) {
/* 197 */       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
/* 198 */       return true;
/*     */     }
/* 200 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
/* 201 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fmessage_005f3(JspTag _jspx_th_logic_005fmessagesPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 206 */     PageContext pageContext = _jspx_page_context;
/* 207 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 209 */     MessageTag _jspx_th_bean_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 210 */     _jspx_th_bean_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 211 */     _jspx_th_bean_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fmessagesPresent_005f0);
/*     */     
/* 213 */     _jspx_th_bean_005fmessage_005f3.setKey("am.storage.monitoring.tab.content");
/* 214 */     int _jspx_eval_bean_005fmessage_005f3 = _jspx_th_bean_005fmessage_005f3.doStartTag();
/* 215 */     if (_jspx_th_bean_005fmessage_005f3.doEndTag() == 5) {
/* 216 */       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f3);
/* 217 */       return true;
/*     */     }
/* 219 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f3);
/* 220 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fmessagesNotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 225 */     PageContext pageContext = _jspx_page_context;
/* 226 */     JspWriter out = _jspx_page_context.getOut();
/* 227 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 228 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*     */     
/* 230 */     MessagesNotPresentTag _jspx_th_logic_005fmessagesNotPresent_005f0 = (MessagesNotPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fname.get(MessagesNotPresentTag.class);
/* 231 */     _jspx_th_logic_005fmessagesNotPresent_005f0.setPageContext(_jspx_page_context);
/* 232 */     _jspx_th_logic_005fmessagesNotPresent_005f0.setParent(null);
/*     */     
/* 234 */     _jspx_th_logic_005fmessagesNotPresent_005f0.setName("SAN_NO_OPSTOR");
/* 235 */     int _jspx_eval_logic_005fmessagesNotPresent_005f0 = _jspx_th_logic_005fmessagesNotPresent_005f0.doStartTag();
/* 236 */     if (_jspx_eval_logic_005fmessagesNotPresent_005f0 != 0) {
/*     */       for (;;) {
/* 238 */         out.write("\n\t\t\t\n\t\t\t    <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" width=\"100%\">\n\t\t\t      \n\t\t\t      <tr>\n\t\t\t\t\n<!-- Inner tabel starts!-->\n\t\t\t\t<td width=\"100%\" valign=\"top\">\n\t\t\t\t  <div id=\"displayArea\"> \n\t\t\t\t      ");
/* 239 */         JspRuntimeLibrary.include(request, response, "/StorageMapping.do" + ("/StorageMapping.do".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("method", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("showStorageMapping", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("displayMode", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("Map", request.getCharacterEncoding()), out, false);
/* 240 */         out.write("\n\t\t\t\t  </div>\n\t\t\t\t</td>\n\t\t\t\t\n\t\t\t      </tr>\n\t\t\t     </table>\n\t\t\t  \n\t\t\t");
/* 241 */         int evalDoAfterBody = _jspx_th_logic_005fmessagesNotPresent_005f0.doAfterBody();
/* 242 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 246 */     if (_jspx_th_logic_005fmessagesNotPresent_005f0.doEndTag() == 5) {
/* 247 */       this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fname.reuse(_jspx_th_logic_005fmessagesNotPresent_005f0);
/* 248 */       return true;
/*     */     }
/* 250 */     this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fname.reuse(_jspx_th_logic_005fmessagesNotPresent_005f0);
/* 251 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\StorageMappingPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */