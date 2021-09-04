/*     */ package org.apache.jsp.webclient.common.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
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
/*     */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class registerLicence_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  28 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/*  29 */   static { _jspx_dependants.put("/WEB-INF/struts-tiles.tld", Long.valueOf(1473429283000L));
/*  30 */     _jspx_dependants.put("/WEB-INF/struts-bean.tld", Long.valueOf(1473429283000L));
/*  31 */     _jspx_dependants.put("/WEB-INF/am_util.tld", Long.valueOf(1473429401000L));
/*  32 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*     */   }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  41 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  47 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  51 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  58 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  61 */     JspWriter out = null;
/*  62 */     Object page = this;
/*  63 */     JspWriter _jspx_out = null;
/*  64 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  68 */       response.setContentType("text/html;charset=UTF-8");
/*  69 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  71 */       _jspx_page_context = pageContext;
/*  72 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  73 */       ServletConfig config = pageContext.getServletConfig();
/*  74 */       session = pageContext.getSession();
/*  75 */       out = pageContext.getOut();
/*  76 */       _jspx_out = out;
/*     */       
/*  78 */       out.write("\n\n\n\n\n\n\n\n\n\t");
/*  79 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  80 */       out.write("\n\n<html>\n<head>\n");
/*     */       
/*  82 */       if (com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(request.getRemoteUser())) {
/*  83 */         response.sendRedirect("/jsp/formpages/AccessRestricted.jsp");
/*     */       }
/*     */       
/*  86 */       out.write("\n<title>");
/*  87 */       out.print(FormatUtil.getString("am.webclient.MGAM.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  88 */       out.write("</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n\n\n<LINK REL=\"SHORTCUT ICON\" HREF=\"/images/logo32x32.ico\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n<link href=\"/images/");
/*  89 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  91 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\"/>\n<script language=\"javascript\">\nfunction attachment()\n{\n        var str = document.frm.theFile.value;\n        if(str != \"\")\n\t\t{\n\t\t\tvar checkFile = str.match(\"xml\");\n\t\t\tvar checkDot = str.split(\".\");\n\t\t\tif(checkDot == str)\n\t\t\t{\n\t\t\t\talert(\"");
/*  92 */       out.print(FormatUtil.getString("am.webclient.prolic.jsalertforlicense.text"));
/*  93 */       out.write(".\");\n\t\t\t\treturn false;\n\t\t\t}\n\n\t\t\tif(checkFile != 'xml')\n\t\t\t{\n\t\t\t\talert(\"");
/*  94 */       out.print(FormatUtil.getString("am.webclient.prolic.jsalertforlicense.text"));
/*  95 */       out.write(".\");\n\t\t\t\treturn false;\n\t\t\t}\n\n\n\t\t\tdocument.frm.theFile.value = str;\n\t\t\treturn true;\n\t\t}\n        else\n        {\n                alert('");
/*  96 */       out.print(FormatUtil.getString("am.webclient.prolic.jsalertforconfirm.text"));
/*  97 */       out.write(".');\n                return false;\n        }\n}\n\n\n</script>\n</head>\n<body >\n<div id=\"containerDiv\">\n<form name=\"frm\" action=\"/register.do\" style=\"display:inline\" method=\"POST\" enctype=\"multipart/form-data\" onSubmit=\" return attachment()\">\n");
/*     */       
/*  99 */       if (request.getParameter("extra") != null)
/*     */       {
/*     */ 
/*     */ 
/* 103 */         out.write("\n        <input type=\"hidden\" name=\"extra\" value=\"true\">\n       \n        ");
/*     */       }
/*     */       
/*     */ 
/* 107 */       out.write("\n<!--<table border=0 cellspacing=0 cellpadding=0>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"/images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width='100%' align=center>-->\n\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg admin-hide \">\n <tr><td>&nbsp;<span class=\"headingboldwhite\">");
/* 108 */       out.print(FormatUtil.getString("am.webclient.common.view.product.information", new String[] { OEMUtil.getOEMString("product.name") }));
/* 109 */       out.write("</span></td></tr>\n</table>\n<table border=\"0\" cellpadding=\"1\" cellspacing=\"0\" width=\"98%\" style=\"margin-top:10px;margin-left:7px;\">\n<tbody>\n\n<tr>\n<td><!-- Display the file locator module -->\n                <table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\" class=\"lrtbdarkborder\">\n                  <tbody>\n                    <tr> \n                      <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 110 */       out.print(FormatUtil.getString("am.webclient.prolic.registerAPM.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 111 */       out.write(" </td>\n                    </tr>\n                    <tr> \n                      <td colspan=\"2\" class=\"bodytext\">");
/* 112 */       out.print(FormatUtil.getString("am.webclient.prolic.registerAPMmessage.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 113 */       out.write("</td>\n                    </tr>\n                    <tr> \n                      <td width=\"24%\"  class=\"bodytext label-align\">");
/* 114 */       out.print(FormatUtil.getString("am.webclient.prolic.licensefile.text"));
/* 115 */       out.write(" :</td> \n<td  width=\"76%\"><input name=\"theFile\" value=\"\" style=\"overflow:hidden\" type=\"file\"></td>\n                    </tr>\n                    <tr> \n\t\t                          <td colspan=\"2\" class=\"tablebottom\" align=\"center\"> \n                        <input value=\"");
/* 116 */       out.print(FormatUtil.getString("am.webclient.prolic.register.text"));
/* 117 */       out.write("\" name=\"btnRegister\" style=\"width: 90px;\" type=\"submit\" class=\"buttons btn_highlt\"> \n                    </tr>\n                    </tbody>\n                    </table><br>\n                    ");
/* 118 */       JspRuntimeLibrary.include(request, response, "/jsp/licenseinfo.jsp", out, true);
/* 119 */       out.write("\n                    <br>\n                <table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\" class=\"lrtbdarkborder\">\n                  <tbody>\n\n                    <tr> \n                      <td  colspan=\"2\" class=\"bodytext\"><b> ");
/* 120 */       out.print(FormatUtil.getString("am.webclient.prolic.contactsupport.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 121 */       out.write(":</b><br>\n                        <br>\n                       ");
/* 122 */       out.print(FormatUtil.getString("am.webclient.prolic.email.text"));
/* 123 */       out.write(" : <a href=\"mailto:");
/* 124 */       out.print(OEMUtil.getOEMString("product.talkback.mailid"));
/* 125 */       out.write("?subject=AppManager:Registration%20Error\" class=\"resourcename\">");
/* 126 */       out.print(OEMUtil.getOEMString("product.talkback.mailid"));
/* 127 */       out.write("</a> \n                        \n                        ");
/* 128 */       out.print(FormatUtil.getString("am.webclient.about.tollfree.text"));
/* 129 */       out.write(58);
/* 130 */       out.write(32);
/* 131 */       out.print(OEMUtil.getOEMString("product.tollfree.number"));
/* 132 */       out.write(" </td>\n                    </tr>\n                    <tr> \n                      <td colspan=\"2\" class=\"tablebottom admin-hide\" align=\"center\"> \n                        <input value=\"");
/* 133 */       out.print(FormatUtil.getString("am.webclient.prolic.close.text"));
/* 134 */       out.write("\" name=\"btnClose\" style=\"width: 90px;\" class=\"buttons btn_link\" onClick=\"window.close();\" type=\"button\"> \n                      </td>\n                    </tr>\n                  </tbody>\n                </table>\n</td>\n</tr>\n</tbody>\n</table>\n<!--\t\t</td>\n\t\t\n      <td class=dashRight>&nbsp;</td>\n\t</tr>\n\t<tr>\n\t\t\n      <td class=dashBottomLeft>&nbsp;</td>\n      <td class=dashBottom width=100%>&nbsp;</td>\n      <td class=dashBottomRight>&nbsp;</td>\n\t</tr>\n</table>-->\n\n</form>\n");
/* 135 */       JspRuntimeLibrary.include(request, response, "/jsp/footer.jsp", out, true);
/* 136 */       out.write("\n<script>\njQuery(document).ready(function(){\t\t\n\n\tif (document.referrer.toLowerCase().indexOf( \"adminlayout\")!=-1) \n\t{\n\t\tjQuery(\".admin-hide\").hide();// NO I18N\n\t}\t\n});\n\n\t</script>\n</div>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 138 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 139 */         out = _jspx_out;
/* 140 */         if ((out != null) && (out.getBufferSize() != 0))
/* 141 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 142 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 145 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 151 */     PageContext pageContext = _jspx_page_context;
/* 152 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 154 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 155 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 156 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 158 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 160 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 161 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 162 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 163 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 164 */       return true;
/*     */     }
/* 166 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 167 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\common\jsp\registerLicence_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */