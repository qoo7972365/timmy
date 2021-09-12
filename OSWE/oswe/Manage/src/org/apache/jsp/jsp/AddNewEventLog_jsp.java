/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class AddNewEventLog_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  35 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  39 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  40 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  43 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  47 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  48 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  49 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
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
/*  76 */       out.write("\n\n\n\n\n\n\n\n\n\n\n<html>\n<head>\n\n<title>");
/*  77 */       out.print(FormatUtil.getString("Event Logs Rules"));
/*  78 */       out.write("</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<LINK REL=\"SHORTCUT ICON\" HREF=\"/images/logo32x32.ico\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  79 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  81 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<script language=\"javascript\">\nfunction checkandsubmi()\n{\n\t");
/*  82 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/*  84 */       out.write("\n\tif(document.AddNewEventName.eventname.value==\"\" || document.AddNewEventName.eventname.value==null)\n\t{\n\t\talert('");
/*  85 */       out.print(FormatUtil.getString("am.webclient.eventlog.alert.eventtype"));
/*  86 */       out.write("');\n\t\treturn;\n\t}\n\telse\n\t{\n\t\t\t");
/*  87 */       if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*     */       {
/*     */ 
/*  90 */         out.write("\n\t\t\t\t");
/*     */         
/*  92 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  93 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  94 */         _jspx_th_c_005fif_005f0.setParent(null);
/*     */         
/*  96 */         _jspx_th_c_005fif_005f0.setTest("${param.ruleid > 9999}");
/*  97 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  98 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */           for (;;) {
/* 100 */             out.write("\n\t\t\t\t\talert('");
/* 101 */             out.print(FormatUtil.getString("am.webclient.eventlog.adminid"));
/* 102 */             out.write("');\n\t\t\t\treturn ;\n\t\t\t\t");
/* 103 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 104 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 108 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 109 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*     */         }
/*     */         
/* 112 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 113 */         out.write("\n\t\t\t\t");
/*     */       }
/* 115 */       out.write("\n\t\t\t\t\n\t\tdocument.AddNewEventName.submit();\t\t\n\t}\n}\n</script>\n</head>\n");
/*     */       
/* 117 */       String Error = request.getParameter("errorMsg");
/* 118 */       String Msg = request.getParameter("successMsg");
/*     */       
/* 120 */       out.write("\t\n<body onLoad=\"javascript:myonload()\">\n<form name=\"AddNewEventName\" action=\"/eventlogrules.do?\" method=\"POST\" onSubmit=\"close_window()\">\n<input id=\"eventaction\" type=\"hidden\" name=\"eventaction\" class=\"formtext normal\" value=\"");
/* 121 */       out.print(request.getParameter("eventaction"));
/* 122 */       out.write("\" >\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n <tr><td>&nbsp;<span class=\"headingboldwhite\">\n ");
/* 123 */       if (request.getParameter("method").equals("editEventLog")) {
/* 124 */         out.write(10);
/* 125 */         out.write(32);
/* 126 */         out.write(32);
/* 127 */         out.print(FormatUtil.getString("am.webclient.eventlog.eventlogtypename.text", new String[] { FormatUtil.getString("am.webclient.eventlog.update.text") }));
/* 128 */         out.write(10);
/* 129 */         out.write(32);
/*     */       } else {
/* 131 */         out.write(10);
/* 132 */         out.write(32);
/* 133 */         out.write(32);
/* 134 */         out.print(FormatUtil.getString("am.webclient.eventlog.eventlogtypename.text", new String[] { FormatUtil.getString("am.webclient.eventlog.add.text") }));
/* 135 */         out.write(10);
/* 136 */         out.write(32);
/*     */       }
/* 138 */       out.write("\n </span></td></tr>\n</table>\n\n\n");
/* 139 */       if (Error != null) {
/* 140 */         out.write("\n\t\t<table width=\"98%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\" style=\"margin-top:10px;margin-left:7px;\">\n\t\t\t<tr> \n\t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t\t\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 141 */         out.print(Error);
/* 142 */         out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t\t<br>\n       ");
/*     */       } else {
/* 144 */         out.write(" \n\n\n\t");
/* 145 */         if (Msg != null) {
/* 146 */           out.write("\t\n\t\t<table width=\"98%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\" style=\"margin-top:10px;margin-left:7px;\">\n\t\t\t<tr> \n\t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t\t\t<td width=\"95%\" class=\"message\">\n                          ");
/* 147 */           out.print(Msg);
/* 148 */           out.write("\t\t\t\t\n                        </td>\n\t\t\t</tr>\n\t\t</table>\n\t\t<br>\n       ");
/*     */         }
/* 150 */         out.write(" \n   ");
/*     */       }
/* 152 */       out.write(" \n\n\n\n<table border=\"0\" cellpadding=\"1\" cellspacing=\"0\" width=\"98%\" style=\"margin-top:10px;margin-left:7px;\">\n\t<tbody>\n\t\t<tr>\n\t\t\t<td>\n\t\t\t<div id=\"tabledetails\">\n\t\t\t\t<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\" class=\"lrtbdarkborder\">\n\t\t\t\t\t<tbody>\n\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t<td colspan=\"2\" class=\"tableheadingbborder\">\n\t\t\t\t\t\t\t\t");
/* 153 */       if (request.getParameter("method").equals("editEventLog")) {
/* 154 */         out.write("\n\t\t\t\t\t\t\t\t  \t\t");
/* 155 */         out.print(FormatUtil.getString("am.webclient.eventlog.eventlogtypename.text", new String[] { FormatUtil.getString("am.webclient.eventlog.update.text") }));
/* 156 */         out.write("\n\t\t\t\t\t\t\t\t ");
/*     */       } else {
/* 158 */         out.write("\n\t\t\t\t\t\t\t\t  \t\t");
/* 159 */         out.print(FormatUtil.getString("am.webclient.eventlog.eventlogtypename.text", new String[] { FormatUtil.getString("am.webclient.eventlog.add.text") }));
/* 160 */         out.write("\n\t\t\t\t\t\t\t\t ");
/*     */       }
/* 162 */       out.write("\n </td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/* 163 */       if (request.getParameter("method").equals("editEventLog")) {
/* 164 */         out.write("\n\t\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t\t<td nowrap heigth=\"28\" width=\"20%\" class=\"label-align\">");
/* 165 */         out.print(FormatUtil.getString("am.webclient.eventlog.eventlogname.text"));
/* 166 */         out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"80%\"><input id=\"eventname\" type=\"text\" name=\"eventname\" class=\"formtext normal\" value=\"");
/* 167 */         out.print(request.getParameter("ruleName"));
/* 168 */         out.write("\" ></td>\n\t\t\t\t\t\t\t\t<input id=\"ruleid\" type=\"hidden\" name=\"ruleid\" class=\"formtext normal\" value=\"");
/* 169 */         out.print(request.getParameter("ruleid"));
/* 170 */         out.write("\" >\n\t\t\t\t\t\t\t\t<input id=\"ruleid\" type=\"hidden\" name=\"ruleName\" class=\"formtext normal\" value=\"");
/* 171 */         out.print(request.getParameter("ruleName"));
/* 172 */         out.write("\" >\n\t\t\t\t\t\t\t\t<input id=\"method\" type=\"hidden\" name=\"method\" class=\"formtext normal\" value=\"editEventLog\" >\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n                            \t<td width=\"20%\" class=\"tablebottom\"></td>\n\t\t\t\t\t\t\t\t<td width=\"80%\" class=\"tablebottom\" align=\"left\"><input value=\"");
/* 173 */         out.print(FormatUtil.getString("am.webclient.eventlog.update.text"));
/* 174 */         out.write("\" name=\"btnRegister\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"checkandsubmi()\">&nbsp;&nbsp;<input value=\"");
/* 175 */         out.print(FormatUtil.getString("am.webclient.eventlog.close.text"));
/* 176 */         out.write("\" name=\"btnClose\" class=\"buttons btn_link\" onClick=\"opener.location.reload(true);self.close()\" type=\"button\"></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*     */       }
/*     */       else {
/* 179 */         out.write("\n\t\t\t\t\t\t\t<tr>\n                            \t<td nowrap heigth=\"28\" width=\"20%\" class=\"label-align\">");
/* 180 */         out.print(FormatUtil.getString("am.webclient.eventlog.neweventlogname.text"));
/* 181 */         out.write("</td>\n                                <td width=\"80%\"><input type=\"text\" name=\"eventname\" class=\"formtext normal\" value=\"\" ></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n                            \t<td width=\"20%\" class=\"tablebottom\"></td>\n\t\t\t\t\t\t\t\t<td  width=\"80%\"class=\"tablebottom\" align=\"left\"><input value=\"");
/* 182 */         out.print(FormatUtil.getString("am.webclient.eventlog.add.text"));
/* 183 */         out.write("\" name=\"btnRegister\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"checkandsubmi()\">\n\t\t\t\t\t\t\t\t<input id=\"method\" type=\"hidden\" name=\"method\" class=\"formtext normal\" value=\"addNewEventLog\" >&nbsp;&nbsp;<input value=\"");
/* 184 */         out.print(FormatUtil.getString("am.webclient.eventlog.close.text"));
/* 185 */         out.write("\" name=\"btnClose\" class=\"buttons btn_link\" onClick=\"opener.location.reload(true);self.close()\" type=\"button\"></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\n\t\t\t\t\t\t");
/*     */       }
/* 187 */       out.write("\n\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t\t\t</div>\n\t\t\t\t\n\t\t\t\t<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\" class=\"lrtbdarkborder\">\n\t\t\t\t\t<tbody>\n\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t\t<td  colspan=\"2\" class=\"bodytext\"><b> ");
/* 188 */       out.print(FormatUtil.getString("am.webclient.eventloghelpcard.text1"));
/* 189 */       out.write("</b>");
/* 190 */       out.print(FormatUtil.getString("am.webclient.eventloghelpcard.text2"));
/* 191 */       out.write("<br></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</tbody>\n</table>\n</form>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 193 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 194 */         out = _jspx_out;
/* 195 */         if ((out != null) && (out.getBufferSize() != 0))
/* 196 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 197 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 200 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 206 */     PageContext pageContext = _jspx_page_context;
/* 207 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 209 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 210 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 211 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 213 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 215 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 216 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 217 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 218 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 219 */       return true;
/*     */     }
/* 221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 227 */     PageContext pageContext = _jspx_page_context;
/* 228 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 230 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 231 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 232 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 234 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 235 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 236 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 238 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 239 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 240 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 244 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 245 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 246 */       return true;
/*     */     }
/* 248 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 249 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AddNewEventLog_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */