/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FileTag;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class MibUpload_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  46 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  50 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  51 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.release();
/*  52 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  53 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  60 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  63 */     JspWriter out = null;
/*  64 */     Object page = this;
/*  65 */     JspWriter _jspx_out = null;
/*  66 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  70 */       response.setContentType("text/html;charset=UTF-8");
/*  71 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  73 */       _jspx_page_context = pageContext;
/*  74 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  75 */       ServletConfig config = pageContext.getServletConfig();
/*  76 */       session = pageContext.getSession();
/*  77 */       out = pageContext.getOut();
/*  78 */       _jspx_out = out;
/*     */       
/*  80 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n<script LANGUAGE=\"JavaScript1.2\">\nfunction fnFormSubmit(f)\n{  \n\t\n   var val = f.theFile.value;\n\t\n   if(val ==\"\") \n   {\n        window.alert('");
/*  81 */       out.print(FormatUtil.getString("am.webclient.fileupload.alert1.text"));
/*  82 */       out.write("');\n   \treturn false;\n   }\n  \n   f.submit();\n   window.opener.location.reload();\n   window.close();\n}\n</script>\n<link href=\"/images/");
/*  83 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  85 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  90 */       String[] uploadDirs = null;
/*  91 */       String[] titles = null;
/*  92 */       String[] descriptions = null;
/*     */       
/*  94 */       uploadDirs = new String[] { "./mibs/" };
/*     */       
/*     */ 
/*     */ 
/*  98 */       titles = new String[] { FormatUtil.getString("am.webclient.fileupload.folder.uploadtomibs") + "/working/mibs/" + FormatUtil.getString("am.webclient.mib.loaction", new String[] { OEMUtil.getOEMString("product.name") }) };
/*     */       
/* 100 */       descriptions = new String[] { FormatUtil.getString("am.webclient.mib.info") };
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 106 */       out.write("\n\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n\n<!--html:base/-->\n");
/*     */       
/* 108 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.get(FormTag.class);
/* 109 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 110 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/* 112 */       _jspx_th_html_005fform_005f0.setAction("/MibUpload.do");
/*     */       
/* 114 */       _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/*     */       
/* 116 */       _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 117 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 118 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/* 120 */           out.write(" \n<input type=\"hidden\" name=\"method\" value=\"executeMib\">\n");
/*     */           
/* 122 */           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 123 */           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 124 */           _jspx_th_c_005fif_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 126 */           _jspx_th_c_005fif_005f0.setTest("${!empty param.returnpath}");
/* 127 */           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 128 */           if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */             for (;;) {
/* 130 */               out.write(" \n<input name=\"returnpath\" type=\"hidden\" value=\"");
/* 131 */               out.print(request.getParameter("returnpath"));
/* 132 */               out.write(34);
/* 133 */               out.write(62);
/* 134 */               out.write(10);
/* 135 */               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 136 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 140 */           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 141 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*     */           }
/*     */           
/* 144 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 145 */           out.write("\n<br>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr> \n    <td width=\"72%\" class=\"tableheading\" height=\"26\">");
/* 146 */           out.print(FormatUtil.getString("MIB Upload"));
/* 147 */           out.write(" <span class=\"bodytext\"></span></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n  <tr> \n    <td width=\"15%\" height=\"35\" class=\"bodytext\" align=\"right\">");
/* 148 */           out.print(FormatUtil.getString("MIB to Upload"));
/* 149 */           out.write("    :</td>\n    <td width=\"85%\" height=\"35\" class=\"bodytext\"> ");
/* 150 */           if (_jspx_meth_html_005ffile_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 152 */           out.write(" \n    </td>\n  </tr>\n  ");
/*     */           
/* 154 */           for (int i = 0; i < uploadDirs.length; i++)
/*     */           {
/*     */ 
/* 157 */             out.write("\n  <tr> \n      \n    <td colspan=\"2\"  align=\"center\" valign=\"top\" > \n    \n      <table width=\"90%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrbtborder\">\n          <tr align=\"center\"> \n            <td height=\"24\" colspan=\"2\" class=\"columnheadingb\"> ");
/* 158 */             out.print(titles[i]);
/* 159 */             out.write("</td>\n          </tr>\n          <tr> \n            \n          <td width=\"5%\" height=\"33\"  >  </td>\n            <td width=\"99%\" class=\"bodytext\" align=\"center\">");
/* 160 */             out.print(descriptions[i]);
/* 161 */             out.write("\n             </td>\n          </tr>\n        </table>\n      </td>\n  </tr>\n  ");
/*     */           }
/*     */           
/*     */ 
/* 165 */           out.write("\n\n    \n</table>\n");
/*     */           
/* 167 */           String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/* 168 */           String upload = FormatUtil.getString("am.webclient.common.upload.text");
/*     */           
/* 170 */           out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr> \n    <td height=\"30\" colspan=\"2\" align=\"center\"  class=\"tablebottom\"> \n      <input name=\"button1\" type=\"button\" class=\"buttons\" value=\"");
/* 171 */           out.print(upload);
/* 172 */           out.write("\" onClick=\"javascript:fnFormSubmit(this.form)\">\n      <input name=\"button2\" type=\"button\" class=\"buttons\" value=\"");
/* 173 */           out.print(cancel);
/* 174 */           out.write("\" onClick=\"javascript:window.close()\"> \n    </td>\n  </tr>\n</table>\n<br>\n");
/*     */           
/* 176 */           if (!OEMUtil.isOEM())
/*     */           {
/* 178 */             String appmanager_home = "";
/*     */             try
/*     */             {
/* 181 */               String home = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParentFile().getAbsolutePath();
/* 182 */               appmanager_home = "<span class=bodytext >&nbsp;&nbsp;&nbsp;&nbsp;*&nbsp; &lt;Product_Home&gt; = " + home + "</span>";
/*     */ 
/*     */             }
/*     */             catch (Exception exx)
/*     */             {
/* 187 */               exx.printStackTrace();
/*     */             }
/*     */             
/* 190 */             out.write(10);
/* 191 */             out.print(appmanager_home);
/* 192 */             out.write(10);
/*     */           }
/* 194 */           out.write(10);
/* 195 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 196 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 200 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 201 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 204 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 205 */         out.write(" \n<!--  Your area ends here -->\n\n");
/*     */       }
/* 207 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 208 */         out = _jspx_out;
/* 209 */         if ((out != null) && (out.getBufferSize() != 0))
/* 210 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 211 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 214 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 220 */     PageContext pageContext = _jspx_page_context;
/* 221 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 223 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 224 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 225 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 227 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 229 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 230 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 231 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 232 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 233 */       return true;
/*     */     }
/* 235 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 236 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ffile_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 241 */     PageContext pageContext = _jspx_page_context;
/* 242 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 244 */     FileTag _jspx_th_html_005ffile_005f0 = (FileTag)this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.get(FileTag.class);
/* 245 */     _jspx_th_html_005ffile_005f0.setPageContext(_jspx_page_context);
/* 246 */     _jspx_th_html_005ffile_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 248 */     _jspx_th_html_005ffile_005f0.setSize("70");
/*     */     
/* 250 */     _jspx_th_html_005ffile_005f0.setProperty("theFile");
/* 251 */     int _jspx_eval_html_005ffile_005f0 = _jspx_th_html_005ffile_005f0.doStartTag();
/* 252 */     if (_jspx_th_html_005ffile_005f0.doEndTag() == 5) {
/* 253 */       this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 254 */       return true;
/*     */     }
/* 256 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 257 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MibUpload_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */