/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.html.MessagesTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class Popup_005fVMTestAction_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  40 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  41 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  46 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  47 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  54 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  57 */     JspWriter out = null;
/*  58 */     Object page = this;
/*  59 */     JspWriter _jspx_out = null;
/*  60 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  64 */       response.setContentType("text/html;charset=UTF-8");
/*  65 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  67 */       _jspx_page_context = pageContext;
/*  68 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  69 */       ServletConfig config = pageContext.getServletConfig();
/*  70 */       session = pageContext.getSession();
/*  71 */       out = pageContext.getOut();
/*  72 */       _jspx_out = out;
/*     */       
/*  74 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  76 */       String resourceType = request.getAttribute("resourceType") != null ? (String)request.getAttribute("resourceType") : null;
/*     */       
/*     */ 
/*  79 */       out.write("\n\n<link\n\thref=\"/images/");
/*  80 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  82 */       out.write("/style.css\"\n\trel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../../template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n\n</script>\n<title>");
/*  83 */       out.print((resourceType != null) && (resourceType.equals("Docker")) ? FormatUtil.getString("am.container.action.title") : FormatUtil.getString("am.vm.test.action"));
/*  84 */       out.write("</title>\n<body>\n\t\n");
/*  85 */       if (request.getAttribute("message") == null)
/*     */       {
/*  87 */         out.write("\n\t<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtborder\" width=\"100%\" align=\"center\">\n\t\t<tr align=\"left\">\n\t\t\t<td class=\"tableheadingbborder\">");
/*  88 */         out.print((resourceType != null) && (resourceType.equals("Docker")) ? FormatUtil.getString("am.container.action.title") : FormatUtil.getString("am.vm.test.action"));
/*  89 */         out.write("</td>\n\t\t</tr>\n\t</table>\n");
/*  90 */         if ((resourceType != null) && (resourceType.equals("Docker"))) {
/*  91 */           out.write("\n\t\t<form action=\"/confActions.do\" method=\"post\" name=\"AMActionForm\" style=\"display:inline;\">\n\t\t<input type=\"hidden\" id=\"method\" name=\"method\" value=\"executeDockerTestAction\"/>\n");
/*     */         } else {
/*  93 */           out.write("<form action=\"/testVMAction.do\" method=\"post\" name=\"AMActionForm\" style=\"display:inline;\">\n\t<input type=\"hidden\" id=\"method\" name=\"method\" value=\"testVMAction\"/>\n");
/*     */         }
/*  95 */         out.write("\n\t\n\t<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrborder\" width=\"100%\" align=\"center\">\n\t\t<tr>\n\t\t\t<td>&nbsp;&nbsp;</td>\n\t\t\t<td>\n\t\t\t\t<table class=\"bodytext\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"2\"><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<input type=\"radio\" name=\"executeVMActions\" value=\"false\" checked/>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td valign=\"center\" class=\"bodytext\">\n\t\t\t\t\t\t\t");
/*  96 */         out.print(FormatUtil.getString("am.vm.send.testmail"));
/*  97 */         out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\t\t\n\t\t\t\t  ");
/*  98 */         if (request.getAttribute("vmList") != null) {
/*  99 */           out.write("\n\t\t\t\t  \t<tr>\n\t\t\t\t\t\t<td colspan=\"2\"><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\"></td>\n\t\t\t\t\t</tr>\n\t  \t\t\t\t<tr>\n\t  \t\t\t\t\t<td><input type=\"radio\" name=\"executeVMActions\" value=\"true\"/></td>\n\t  \t\t\t\t\t<td valign=\"center\" class=\"bodytext\">\n\t  \t\t\t\t\t\t");
/* 100 */           out.print(request.getAttribute("actionAndMail"));
/* 101 */           out.write("\n\t\t\t\t\t\t </td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>&nbsp;</td>\n\t\t\t\t\t\t<td>&nbsp;&nbsp;");
/* 102 */           out.print(request.getAttribute("belowInstances"));
/* 103 */           out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/* 104 */           ArrayList vmList = (ArrayList)request.getAttribute("vmList");
/* 105 */           for (int i = 0; i < vmList.size();)
/*     */           {
/*     */ 
/* 108 */             out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>&nbsp;</td>\n\t\t\t\t\t\t<td>&nbsp;&nbsp;");
/* 109 */             out.print(i + 1);
/* 110 */             out.write(".&nbsp;");
/* 111 */             out.print(vmList.get(i++));
/* 112 */             out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*     */           }
/* 114 */           out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"2\"><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\n\t\t\t\t");
/*     */         }
/* 116 */         out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"2\"><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\"></td>\n\t\t\t\t</tr>\n\t\t\t\t\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n<table width=\"100%\" border=\"0\" class=\"lrtbdarkborder\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n\t<tr>\n\t\t<td height=\"35\" align=\"center\" class=\"tablebottom\">\n\t\t<input name=\"Submit\" type=\"submit\" class=\"buttons btn_test\"\tvalue='");
/* 117 */         out.print(FormatUtil.getString("am.vm.action.test"));
/* 118 */         out.write("'>\n\t\t&nbsp;&nbsp;\n\t\t<input name=\"cancel\" type=\"button\" class=\"buttons btn_link\"\tvalue='");
/* 119 */         out.print(FormatUtil.getString("am.vm.action.cancel"));
/* 120 */         out.write("' onClick=\"self.close();\">\n\t\t\n\t\n\t<input type=\"hidden\" id=\"actionID\" name=\"actionID\" value=\"");
/* 121 */         out.print(request.getParameter("actionID"));
/* 122 */         out.write("\"/>\n\t<input type=\"hidden\" id=\"haid\" name=\"haid\" value=\"");
/* 123 */         out.print(request.getParameter("haid"));
/* 124 */         out.write("\"/>\n\t\n\t\t\n\t\t</td>\n\t</tr>\n</table>\n</form>\n");
/*     */       } else {
/* 126 */         out.write("\n\n \t<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px 0px 0px 0px;\" width=\"99%\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td>&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td  class=\"msg-status-tp-left-corn\"></td>\n\t\t\t\t\t\t\t\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t\t\t\t\t\t\t\t<td  class=\"msg-status-tp-right-corn\"></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t     \t<tr>\n\t\t\t\t\t\t\t\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td  width=\"98%\" class=\"msg-table-width\">\n\t\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\">\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td width=\"35%\" class=\"msg-table-width-bg\" align=\"right\"><img src=\"../images/icon_message_success.gif\" alt=\"icon\" height=\"25\" width=\"25\"></td>\n\t                \t\t\t\t\t\t<td width=\"65%\" class=\"msg-table-width\" align=\"left\">&nbsp;");
/*     */         
/* 128 */         MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 129 */         _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 130 */         _jspx_th_html_005fmessages_005f0.setParent(null);
/*     */         
/* 132 */         _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */         
/* 134 */         _jspx_th_html_005fmessages_005f0.setMessage("true");
/* 135 */         int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 136 */         if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 137 */           String msg = null;
/* 138 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 139 */             out = _jspx_page_context.pushBody();
/* 140 */             _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 141 */             _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */           }
/* 143 */           msg = (String)_jspx_page_context.findAttribute("msg");
/*     */           for (;;) {
/* 145 */             out.write(32);
/* 146 */             if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */               return;
/* 148 */             int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 149 */             msg = (String)_jspx_page_context.findAttribute("msg");
/* 150 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 153 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 154 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 157 */         if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 158 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*     */         }
/*     */         
/* 161 */         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 162 */         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"msg-status-right-bg\"></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td class=\"msg-status-btm-right-corn\" >&nbsp;</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td>&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"center\">\n\t\t\t\t\t\t<input name=\"cancel\" type=\"button\" class=\"buttons btn_link\"\talign=\"center\" value='");
/* 163 */         out.print(FormatUtil.getString("am.webclient.amazon.close.text"));
/* 164 */         out.write("' onClick=\"self.close();\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t\n");
/*     */       }
/* 166 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 168 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 169 */         out = _jspx_out;
/* 170 */         if ((out != null) && (out.getBufferSize() != 0))
/* 171 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 172 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 175 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 181 */     PageContext pageContext = _jspx_page_context;
/* 182 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 184 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 185 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 186 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 188 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 190 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 191 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 192 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 193 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 194 */       return true;
/*     */     }
/* 196 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 197 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 202 */     PageContext pageContext = _jspx_page_context;
/* 203 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 205 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 206 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 207 */     _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 209 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*     */     
/* 211 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 212 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 213 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 214 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 215 */       return true;
/*     */     }
/* 217 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 218 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fVMTestAction_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */