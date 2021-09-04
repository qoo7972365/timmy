/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.sql.ResultSet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.MultiboxTag;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class FaultTemplateOptions_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  21 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  48 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  53 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  54 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  55 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  56 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/*  57 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  64 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  67 */     JspWriter out = null;
/*  68 */     Object page = this;
/*  69 */     JspWriter _jspx_out = null;
/*  70 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  74 */       response.setContentType("text/html;charset=UTF-8");
/*  75 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  77 */       _jspx_page_context = pageContext;
/*  78 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  79 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  80 */       session = pageContext.getSession();
/*  81 */       out = pageContext.getOut();
/*  82 */       _jspx_out = out;
/*     */       
/*  84 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  85 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  87 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\nfunction fnSelectAll(e,name)\n{\n    ToggleAll(e,document.AMActionForm,name)\n}\nfunction submitForm(z)\n{\n   ");
/*  88 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/*  90 */       out.write("\n   ");
/*     */       
/*  92 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  93 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  94 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/*  96 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  97 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  98 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/* 100 */           out.write("\n   var j=0;\n   for(k=1;k<=z;k++)\n   {\n    var x='check'+k;\n\tif(document.getElementById(x).checked)\n\t{\n\t\tj++;\n\t\tbreak;\n\t}\n   }\n   if(j==0)\n   {\n           alert('");
/* 101 */           out.print(FormatUtil.getString("am.webclient.faulttemplate.alert1.text"));
/* 102 */           out.write("');\n\t   return;\n   }\n   else\n   {\n\t   document.AMActionForm.submit();\n   }\n   ");
/* 103 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 104 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 108 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 109 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 112 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 113 */         out.write("\n}\n</script>\n<title>");
/* 114 */         out.print(FormatUtil.getString("am.webclient.faulttemplate.alertconfigtemplate.text"));
/* 115 */         out.write("</title>\n<table width=\"100%\"  height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/* 116 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */           return;
/* 118 */         out.write("</span><span class=\"headingwhite\"> </span>\n</td>\n</tr>\n</table>\n");
/*     */         
/* 120 */         FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 121 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 122 */         _jspx_th_html_005fform_005f0.setParent(null);
/*     */         
/* 124 */         _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*     */         
/* 126 */         _jspx_th_html_005fform_005f0.setMethod("post");
/*     */         
/* 128 */         _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 129 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 130 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */           for (;;) {
/* 132 */             out.write("\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 133 */             out.print(request.getParameter("resourceid"));
/* 134 */             out.write("\">\n<input type=\"hidden\" name=\"resourcedisplayname\" value=\"");
/* 135 */             out.print(request.getParameter("resourcename"));
/* 136 */             out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 137 */             out.print(request.getParameter("resourcetype"));
/* 138 */             out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"applyTemplateSettings\">\n   <br>\n   <table width=\"98%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\"  >\n       <tr align=\"left\">\n         <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 139 */             out.print(FormatUtil.getString("am.webclient.faulttemplate.message1.text", new String[] { FormatUtil.getString(String.valueOf(request.getParameter("resourcetypedisplayname"))) }));
/* 140 */             out.write("</td>\n       </tr>\n       <tr>\n                <td width=\"0%\" valign=\"top\" > </td>\n                <td width=\"100%\" class=\"bodytext\">");
/* 141 */             out.print(FormatUtil.getString("am.webclient.faulttemplate.message2.text"));
/* 142 */             out.write("</td>\n       </tr>\n       <tr>\n         <td valign=\"top\" > ");
/* 143 */             if (_jspx_meth_html_005fmultibox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */               return;
/* 145 */             out.write("</td>\n         <td class=\"bodytext\"><b>");
/* 146 */             out.print(FormatUtil.getString("am.webclient.faulttemplate.message3.text"));
/* 147 */             out.write("</b></td>\n       </tr>\n       <tr>\n         <td width=\"0%\" valign=\"top\" > </td>\n         <td width=\"100%\" class=\"bodytext\"> ");
/* 148 */             out.print(FormatUtil.getString("am.webclient.faulttemplate.message4.text"));
/* 149 */             out.write("</td>\n       </tr>\n       <tr>\n             <td valign=\"top\" > ");
/* 150 */             if (_jspx_meth_html_005fmultibox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */               return;
/* 152 */             out.write("</td>\n             <td class=\"bodytext\"><b>");
/* 153 */             out.print(FormatUtil.getString("am.webclient.faulttemplate.message5.text"));
/* 154 */             out.write("</b></td>\n           </tr>\n           <tr>\n             <td width=\"0%\" valign=\"top\" > </td>\n             <td width=\"100%\" class=\"bodytext\">");
/* 155 */             out.print(FormatUtil.getString("am.webclient.faulttemplate.message6.text"));
/* 156 */             out.write("</td>\n\t\t\t </tr>\n                          <tr>\n         <td valign=\"top\" > ");
/* 157 */             if (_jspx_meth_html_005fmultibox_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */               return;
/* 159 */             out.write("</td>\n         <td class=\"bodytext\"><b>");
/* 160 */             out.print(FormatUtil.getString("am.webclient.faulttemplate.anomalymessage1.text"));
/* 161 */             out.write("</b></td>\n       </tr>\n       <tr>\n         <td width=\"0%\" valign=\"top\" > </td>\n         <td width=\"100%\" class=\"bodytext\"> ");
/* 162 */             out.print(FormatUtil.getString("am.webclient.faulttemplate.anomalymessage2.text"));
/* 163 */             out.write("</td>\n       </tr>\n\t\t  ");
/*     */             
/* 165 */             int i = 0;
/* 166 */             String templateResourceType = request.getParameter("resourcetype");
/* 167 */             String templateResourceId = request.getParameter("resourceid");
/* 168 */             String resIDsQuery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE='" + templateResourceType + "'and RESOURCEID!=" + templateResourceId + " ORDER BY DISPLAYNAME";
/* 169 */             com.adventnet.appmanager.db.AMConnectionPool.getInstance();ResultSet res1 = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(resIDsQuery);
/* 170 */             if (res1.next())
/*     */             {
/*     */ 
/* 173 */               out.write("\n\t\t\t <tr>\n                           <td valign=\"top\" class=\"bodytext\" colspan=\"3\" align=\"left\" onclick=\"javascript:toggleDiv('monitorname');\"><a href=\"javascript:void(0);\" class=\"staticlinks\">");
/* 174 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.advancedlink.text"));
/* 175 */               out.write("</a> <img src=\"../images/img_arrow.gif\" border=\"0\"></td>\n\t\t\t </tr>\n\t\t\t <tr>\n\t\t\t   <td colspan=\"2\">\n\t\t\t\t<div id=\"monitorname\"   style=\"DISPLAY: none\">\n\t\t\t\t<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=1 WIDTH=\"100%\"  class=\"lrtbdarkborder\" align=\"center\">\n\t\t\t\t<tr>\n\t\t\t\t <td width=\"3%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">\n\t\t\t\t   <input type=\"checkbox\" name=\"headercheckbox\" checked=\"true\" onClick=\"javascript:fnSelectAll(this,'monitors')\">\n\t\t\t\t </td>\n                                 <td width=\"24%\"height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">");
/* 176 */               out.print(FormatUtil.getString("am.webclient.configurealert.monitorname"));
/* 177 */               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t");
/*     */               
/* 179 */               res1.beforeFirst();
/* 180 */               while (res1.next())
/*     */               {
/* 182 */                 int relatedResId = res1.getInt(1);
/* 183 */                 String relatedDisName = res1.getString(2);
/* 184 */                 i++;
/*     */                 
/* 186 */                 out.write("\n               <tr>\n\t\t\t     <td class=\"bodytext\">&nbsp;<input type=\"checkbox\" checked=\"true\" name=\"monitors\" id=\"check");
/* 187 */                 out.print(i);
/* 188 */                 out.write("\" value=\"");
/* 189 */                 out.print(relatedResId);
/* 190 */                 out.write("\"></td>\n\t\t\t     <td class=\"bodytext\" title=\"");
/* 191 */                 out.print(relatedDisName);
/* 192 */                 out.write("\" > ");
/* 193 */                 out.print(relatedDisName);
/* 194 */                 out.write("</td>\n\t\t\t   </tr>\n\t\t\t  ");
/*     */               }
/*     */               
/* 197 */               out.write("\n\t\t\t     </table>\n\t\t\t  </div>\n\t\t\t </td>\n\t\t\t</tr>\n\t\t     ");
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 202 */               out.write("\n\t\t\t<tr>\n\t\t\t  <td class=\"bodytext\" colspan=\"5\">&nbsp;&nbsp;&nbsp;<b>");
/* 203 */               out.print(FormatUtil.getString("am.webclient.faulttemplate.message7.text"));
/* 204 */               out.write("</b></td>\n\t\t\t</tr>\n\t\t\t");
/*     */             }
/* 206 */             out.write("\n   </table>\n\n   ");
/*     */             
/* 208 */             String apply = FormatUtil.getString("webclient.common.skinselection.apply");
/* 209 */             String close = FormatUtil.getString("am.webclient.common.close.text");
/*     */             
/* 211 */             out.write("\n   <table width=\"98%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrbborder\" align=\"center\">\n       <tr class=\"tablebottom\">\n       <td height=\"27\"  align=\"center\">\n\t   ");
/*     */             
/* 213 */             if (res1.first())
/*     */             {
/*     */ 
/* 216 */               out.write("\n           <input name=\"Submit\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 217 */               out.print(apply);
/* 218 */               out.write("\" onClick=\"javascript:submitForm(");
/* 219 */               out.print(i);
/* 220 */               out.write(");\">\n\t\t   ");
/*     */             }
/* 222 */             res1.close();
/*     */             
/* 224 */             out.write("\n           <input name=\"GoBack\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 225 */             out.print(close);
/* 226 */             out.write("\"  onClick=\"window.close();\">&nbsp;&nbsp;\n       </td>\n        </tr>\n    </table>\n</body>\n");
/* 227 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 228 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 232 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 233 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */         }
/*     */         else {
/* 236 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 237 */           out.write("\n</html>\n\n");
/*     */         }
/* 239 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 240 */         out = _jspx_out;
/* 241 */         if ((out != null) && (out.getBufferSize() != 0))
/* 242 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 243 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 246 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 252 */     PageContext pageContext = _jspx_page_context;
/* 253 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 255 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 256 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 257 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 259 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 261 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 262 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 263 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 264 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 265 */       return true;
/*     */     }
/* 267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 268 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 273 */     PageContext pageContext = _jspx_page_context;
/* 274 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 276 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 277 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 278 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 280 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 281 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 282 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 284 */         out.write("\n   alertUser();\n   return;\n   ");
/* 285 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 286 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 290 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 291 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 292 */       return true;
/*     */     }
/* 294 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 295 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 300 */     PageContext pageContext = _jspx_page_context;
/* 301 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 303 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 304 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 305 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 307 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.faulttemplate.alertconfigtemplate.text");
/* 308 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 309 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 310 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 311 */       return true;
/*     */     }
/* 313 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 314 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fmultibox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 319 */     PageContext pageContext = _jspx_page_context;
/* 320 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 322 */     MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 323 */     _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 324 */     _jspx_th_html_005fmultibox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 326 */     _jspx_th_html_005fmultibox_005f0.setProperty("deleteexistingthresholds");
/*     */     
/* 328 */     _jspx_th_html_005fmultibox_005f0.setValue("true");
/* 329 */     int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 330 */     if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 331 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 332 */       return true;
/*     */     }
/* 334 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 335 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fmultibox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 340 */     PageContext pageContext = _jspx_page_context;
/* 341 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 343 */     MultiboxTag _jspx_th_html_005fmultibox_005f1 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 344 */     _jspx_th_html_005fmultibox_005f1.setPageContext(_jspx_page_context);
/* 345 */     _jspx_th_html_005fmultibox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 347 */     _jspx_th_html_005fmultibox_005f1.setProperty("deleteexistingactions");
/*     */     
/* 349 */     _jspx_th_html_005fmultibox_005f1.setValue("true");
/* 350 */     int _jspx_eval_html_005fmultibox_005f1 = _jspx_th_html_005fmultibox_005f1.doStartTag();
/* 351 */     if (_jspx_th_html_005fmultibox_005f1.doEndTag() == 5) {
/* 352 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1);
/* 353 */       return true;
/*     */     }
/* 355 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1);
/* 356 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fmultibox_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 361 */     PageContext pageContext = _jspx_page_context;
/* 362 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 364 */     MultiboxTag _jspx_th_html_005fmultibox_005f2 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 365 */     _jspx_th_html_005fmultibox_005f2.setPageContext(_jspx_page_context);
/* 366 */     _jspx_th_html_005fmultibox_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 368 */     _jspx_th_html_005fmultibox_005f2.setProperty("deleteexistinganomaly");
/*     */     
/* 370 */     _jspx_th_html_005fmultibox_005f2.setValue("true");
/* 371 */     int _jspx_eval_html_005fmultibox_005f2 = _jspx_th_html_005fmultibox_005f2.doStartTag();
/* 372 */     if (_jspx_th_html_005fmultibox_005f2.doEndTag() == 5) {
/* 373 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2);
/* 374 */       return true;
/*     */     }
/* 376 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2);
/* 377 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\FaultTemplateOptions_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */