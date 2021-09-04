/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FileTag;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class ImportThresholdTemplates_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  26 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  27 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  39 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  43 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  48 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  53 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.release();
/*  54 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.release();
/*  55 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  62 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  65 */     JspWriter out = null;
/*  66 */     Object page = this;
/*  67 */     JspWriter _jspx_out = null;
/*  68 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  72 */       response.setContentType("text/html");
/*  73 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  75 */       _jspx_page_context = pageContext;
/*  76 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  77 */       ServletConfig config = pageContext.getServletConfig();
/*  78 */       session = pageContext.getSession();
/*  79 */       out = pageContext.getOut();
/*  80 */       _jspx_out = out;
/*     */       
/*  82 */       out.write("\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
/*  83 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  84 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  86 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  87 */       out.write(10);
/*  88 */       out.write("\n<script type=\"text/javascript\">\nvar PDT  ={\n\tsubmit : function()\n\t{\n\t\tdocument.AMActionForm.submit();\n\t},\n\t\n\ttrimAll:function(sString)\n\t{\n\t    while (sString.substring(0,1) == ' ')\n\t    {\n\t    sString = sString.substring(1, sString.length);\n\t    }\n\t    while (sString.substring(sString.length-1, sString.length) == ' ')\n\t    {\n\t    sString = sString.substring(0,sString.length-1);\n\t    }\n\t    return sString;\n\t},\n\n\timportThresholds:function()\n\t{\n\t\tvar val = document.AMActionForm.theFile.value;\n\t\tif(val==\"\")\n\t\t{\n\t\t\twindow.alert('");
/*  89 */       out.print(FormatUtil.getString("am.webclient.fileupload.alert1.text"));
/*  90 */       out.write("');\n\t\t\treturn false;\n\t\t}\n\t\tif(val.match(/\\.xml$/gi)!=\".xml\")\n\t\t{\n\t\t\talert('");
/*  91 */       out.print(FormatUtil.getString("am.webclient.admin.pdam.import.notxml"));
/*  92 */       out.write("');\n\t\t\treturn false;\n\t\t}\t\t\n\t  \t//document.getElementById(\"method\").value=\"importMapping\";//No I18N\n\t  \tPDT.submit();\n\t  \t\n\t \t//document.AMActionForm.submit();\n\t},\n\trefreshParent:function() {\n\t\t /* window.opener.location.href = window.opener.location.href;\n\n\t\t  if (window.opener.progressWindow)\n\t\t\t\t\n\t\t {\n\t\t    window.opener.progressWindow.close()\n\t\t  }*/\n\t\t  window.opener.location.reload()\n\t\t  window.close();\n\t\t}\n\n}\n</script>\n</head>\n<body>\n");
/*     */       
/*  94 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.get(FormTag.class);
/*  95 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  96 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/*  98 */       _jspx_th_html_005fform_005f0.setAction("/PreDefinedAttributeMapperAction.do?method=importMapping");
/*     */       
/* 100 */       _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/*     */       
/* 102 */       _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 103 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 104 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/* 106 */           out.write(" \n<input type=\"hidden\" name=\"method\" id=\"method\" value=\"importMapping\"/>\n<br/><br/>\n <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n<tr>\n<td class=\"tableheading\" width=\"72%\" height=\"31\">");
/* 107 */           out.print(FormatUtil.getString("am.webclient.admin.pdam.import.threshold.templates"));
/* 108 */           out.write("</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" align=\"center\">\t \n\t<tr/><tr/>\t\n\t<tr>\n\t<td colspan=2 class=\"importTemplatesNote\"><br/>&nbsp;&nbsp;&nbsp;&nbsp;");
/* 109 */           out.print(FormatUtil.getString("am.webclient.admin.pdam.import.threshold.waningmsg"));
/* 110 */           out.write("</td>\n\t</tr>\n<tr>   \t\t  \n  <td width=\"15%\" height=\"35\" class=\"bodytext\" align=\"center\">");
/* 111 */           out.print(FormatUtil.getString("am.webclient.fileupload.filetoupload.text"));
/* 112 */           out.write("    :</td>\n  <td width=\"85%\" height=\"35\" class=\"bodytext\"> ");
/* 113 */           if (_jspx_meth_html_005ffile_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 115 */           out.write("(Ex:Thresholds.xml)</td> ");
/* 116 */           out.write("\n\t  </tr>\n\t  <tr/>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" align=\"center\">\n\t<tr>\n    <td height=\"40\" width=\"40%\" align=\"right\"  class=\"tablebottom\">\n\t    <input id=\"button1\" name=\"button1\" type=\"button\" class=\"buttons\" value='");
/* 117 */           out.print(FormatUtil.getString("am.webclient.admin.pdam.import.threshold.import"));
/* 118 */           out.write("' onClick=\"javascript:PDT.importThresholds();\">\n\t    <input name=\"button2\" type=\"button\" class=\"buttons\" value='");
/* 119 */           out.print(FormatUtil.getString("am.webclient.newaction.buttonLabel.cancel"));
/* 120 */           out.write("' onClick=\"javascript:PDT.refreshParent();\">\n</td>\n    <td height=\"40\" width=\"60%\"  align=\"center\"  class=\"tablebottom\">&nbsp; </td>\n  </tr>\n</table>\n<div>");
/* 121 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 123 */           out.write("</div>\n<br/>\n  ");
/* 124 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 125 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 129 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 130 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 133 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 134 */         out.write("\n</body>\n</html>");
/*     */       }
/* 136 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 137 */         out = _jspx_out;
/* 138 */         if ((out != null) && (out.getBufferSize() != 0))
/* 139 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 140 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 143 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 149 */     PageContext pageContext = _jspx_page_context;
/* 150 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 152 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 153 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 154 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 156 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 158 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 159 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 160 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 161 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 162 */       return true;
/*     */     }
/* 164 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ffile_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 170 */     PageContext pageContext = _jspx_page_context;
/* 171 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 173 */     FileTag _jspx_th_html_005ffile_005f0 = (FileTag)this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.get(FileTag.class);
/* 174 */     _jspx_th_html_005ffile_005f0.setPageContext(_jspx_page_context);
/* 175 */     _jspx_th_html_005ffile_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 177 */     _jspx_th_html_005ffile_005f0.setSize("30");
/*     */     
/* 179 */     _jspx_th_html_005ffile_005f0.setProperty("theFile");
/* 180 */     int _jspx_eval_html_005ffile_005f0 = _jspx_th_html_005ffile_005f0.doStartTag();
/* 181 */     if (_jspx_th_html_005ffile_005f0.doEndTag() == 5) {
/* 182 */       this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 183 */       return true;
/*     */     }
/* 185 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 186 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 191 */     PageContext pageContext = _jspx_page_context;
/* 192 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 194 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 195 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 196 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 198 */     _jspx_th_c_005fout_005f1.setValue("${MESSAGE}");
/* 199 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 200 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 201 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 202 */       return true;
/*     */     }
/* 204 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 205 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ImportThresholdTemplates_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */