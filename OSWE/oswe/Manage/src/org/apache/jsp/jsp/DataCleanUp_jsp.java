/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.CheckboxTag;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.HiddenTag;
/*     */ import org.apache.struts.taglib.html.RadioTag;
/*     */ import org.apache.struts.taglib.html.TextTag;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.struts.taglib.tiles.PutTag;
/*     */ 
/*     */ public final class DataCleanUp_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  22 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  28 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  29 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  51 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  55 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  57 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  58 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  59 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  60 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  61 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  62 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  63 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  64 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  65 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  66 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  67 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  68 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  69 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  70 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  74 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  75 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  76 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  77 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  78 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  79 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  80 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*  81 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction.release();
/*  82 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*  83 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  84 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.release();
/*  85 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.release();
/*  86 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody.release();
/*  87 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  94 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  97 */     JspWriter out = null;
/*  98 */     Object page = this;
/*  99 */     JspWriter _jspx_out = null;
/* 100 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/* 104 */       response.setContentType("text/html;charset=UTF-8");
/* 105 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/* 107 */       _jspx_page_context = pageContext;
/* 108 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 109 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 110 */       session = pageContext.getSession();
/* 111 */       out = pageContext.getOut();
/* 112 */       _jspx_out = out;
/*     */       
/* 114 */       out.write("<!DOCTYPE html>\n");
/* 115 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 116 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 117 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 118 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 120 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 121 */       out.write(10);
/* 122 */       out.write(10);
/* 123 */       out.write(10);
/*     */       
/* 125 */       boolean isIT360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 126 */       String reportLogoStyle = "";
/* 127 */       if (isIT360)
/*     */       {
/* 129 */         reportLogoStyle = "style=\"display:none;\"";
/*     */       }
/* 131 */       request.setAttribute("HelpKey", "Reports Settings");
/* 132 */       String reportlogopath = (String)request.getAttribute("reportlogo");
/* 133 */       if (reportlogopath == null) {
/* 134 */         reportlogopath = "/images/am_logo.png";
/*     */       }
/*     */       
/* 137 */       out.write("\n\n<script language=\"JavaScript1.2\">\nfunction fnFormSubmit()\n{\n");
/* 138 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/* 140 */       out.write(10);
/*     */       
/* 142 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 143 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 144 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/* 146 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 147 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 148 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/* 150 */           out.write("\n\n\t//code to validate the values in the text field\n\tif(document.AMActionForm[0].dataclean.value!=''){\n\t\tvar val=document.AMActionForm[0].dataclean.value\n\t\tif(isNumber(val)==false){\n\t\t\talert(\"");
/* 151 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsalertforhourlyDecimal.text"));
/* 152 */           out.write("\");//No I18N\n\t\t\tdocument.AMActionForm[0].dataclean.focus();\n\t\t\treturn false;\n\t\t}\n\n\t}\n\tif(document.AMActionForm[0].dailyclean.value!=''){\n\t\tvar val=document.AMActionForm[0].dailyclean.value\n\t\tif(isNumber(val)==false){\n\t\t\talert(\"");
/* 153 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsalertforDailyDecimal.text"));
/* 154 */           out.write("\");//No I18N\n\t\t\tdocument.AMActionForm[0].dailyclean.focus();\n\t\t\treturn false;\n\t\t}\n\t}\n\t/*if(document.AMActionForm[0].alertclean.value!=''){\n\t\tvar val=document.AMActionForm[0].alertclean.value\n\t\tif(isNumber(val)==false){\n\t\t\talert(\"");
/* 155 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsalertforAlertDecimal.text"));
/* 156 */           out.write("\");//No I18N\n\t\t\tdocument.AMActionForm[0].alertclean.focus();\n\t\t\treturn false;\n\t\t}\n\t}*/\n\n\tif(document.AMActionForm[0].dataclean.value=='')\n\t{\n            alert(\"");
/* 157 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsalertforhourly.text"));
/* 158 */           out.write("\");\n                document.AMActionForm[0].dataclean.focus();\n                return false;\n          }\n          else if(document.AMActionForm[0].dataclean.value<1)\n          {\n                alert(\"");
/* 159 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsalertforhourlyposval.text"));
/* 160 */           out.write("\");\n                document.AMActionForm.dataclean.focus();\n                return false;\n           }\n          else if(document.AMActionForm[0].dailyclean.value=='')\n            {\n                 alert(\"");
/* 161 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsalertforDaily.text"));
/* 162 */           out.write("\");\n                document.AMActionForm.dailyclean.focus();\n                return false;\n            }\n           else if(document.AMActionForm[0].dailyclean.value<1)\n            {\n                alert(\"");
/* 163 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsalertforDailyposval.text"));
/* 164 */           out.write("\");\n                document.AMActionForm.dailyclean.focus();\n                return false;\n            }\n            /*else if(document.AMActionForm[0].alertclean.value=='')\n            {\n                 alert(\"");
/* 165 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsalertforAlert.text"));
/* 166 */           out.write("\");\n                document.AMActionForm.alertclean.focus();\n                return false;\n            }\n           else if(document.AMActionForm[0].alertclean.value<1)\n          {\n                alert(\"");
/* 167 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsalertforAlertposval.text"));
/* 168 */           out.write("\");\n                document.AMActionForm[0].alertclean.focus();\n                return false;\n           }*/\n          /* else if(document.AMActionForm[0].eventalert.value=='')\n            {\n                 alert(\"Event value cannot be Empty\");\n                document.AMActionForm[0].eventalert.focus();\n                return false;\n            }\n           else if(document.AMActionForm[0].eventalert.value<1)\n          {\n                alert(\"Event value cannot be less than 1\");\n                document.AMActionForm[0].eventalert.focus();\n                return false;\n           }*/\n          else if(confirm(\"");
/* 169 */           out.print(FormatUtil.getString("am.webclient.dbretention.jsconfirm.text"));
/* 170 */           out.write("\"))\n          {\n\n \t\tdocument.AMActionForm[0].submit();\n          }\n\n\t");
/* 171 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 172 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 176 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 177 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 180 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 181 */         out.write("\n\n}\n\nfunction showHide(tab)\n{\n\tvar isIt360 = \"");
/* 182 */         out.print(isIT360);
/* 183 */         out.write("\";\n\tif(tab==\"reportssetting\")\n\t{\n\n\tdocument.getElementById(\"customreplink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"customreplink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"customreplink-right\").className = \"tbSelected_Right\";\n\n\tif(document.getElementById(\"downsumreplink-left\")!=null){\n\t\tdocument.getElementById(\"downsumreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"downsumreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"downsumreplink-right\").className = \"tbUnSelected_Right\";\n\t}\n\tif(isIt360 == null || isIt360 != 'true')\n\t{\n\tdocument.getElementById(\"replogolink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"replogolink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"replogolink-right\").className = \"tbUnSelected_Right\";\n\t}\n\n\tjavascript:hideDiv('dataretention');\n\tjavascript:showDiv('reportssetting');\n\tjavascript:showDiv('reportssettingHelp');\n\tjavascript:hideDiv('dataretentionHelp');\n\tjavascript:hideDiv('reportlogosettings');\n");
/* 184 */         out.write("\tjavascript:hideDiv('reportlogoHelp');\n\t//document.getElementById(\"dataretention\").style.display='block';\n\t//document.getElementById(\"reportssetting\").style.display='none';\n\t}\n\n\telse if(tab==\"dataretention\")\n\t{\n\n\n\tdocument.getElementById(\"downsumreplink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"downsumreplink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"downsumreplink-right\").className = \"tbSelected_Right\";\n\n\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\n\tif(isIt360 == null || isIt360 != 'true')\n\t{\t\n\tdocument.getElementById(\"replogolink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"replogolink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"replogolink-right\").className = \"tbUnSelected_Right\";\n\t}\n\n\tjavascript:hideDiv('reportssetting');\n\tjavascript:showDiv('dataretention');\n");
/* 185 */         out.write("\tjavascript:hideDiv('reportssettingHelp');\n\tjavascript:showDiv('dataretentionHelp');\n\tjavascript:hideDiv('reportlogosettings');\n\tjavascript:hideDiv('reportlogoHelp');\n\t//document.getElementById(\"reportHelpcard\").style.display='none';\n\t//document.getElementById(\"summaryHelpcard\").style.display='block';\n\n\t}\n\telse if(tab==\"reportlogosettings\")\n\t{\n\t\n\tif(document.getElementById(\"downsumreplink-left\")!=null){\n\t\tdocument.getElementById(\"downsumreplink-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"downsumreplink\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"downsumreplink-right\").className = \"tbUnSelected_Right\";\n\t}\n\t\n\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\t\n\tdocument.getElementById(\"replogolink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"replogolink\").className = \"tbSelected_Middle\";\n");
/* 186 */         out.write("\tdocument.getElementById(\"replogolink-right\").className = \"tbSelected_Right\";\n\t\n\tjavascript:showDiv('reportlogosettings');\n\tjavascript:showDiv('reportlogoHelp');\n\tjavascript:hideDiv('reportssetting');\n\tjavascript:hideDiv('dataretention');\n\tjavascript:hideDiv('reportssettingHelp');\n\tjavascript:hideDiv('dataretentionHelp');\n\t\n\t//document.getElementById(\"reportHelpcard\").style.display='none';\n\t//document.getElementById(\"summaryHelpcard\").style.display='block';\n\t\n\t}\n\n}\nfunction myOnLoad()\n  {\n\n\t");
/* 187 */         if ((request.getAttribute("tabtoLoad") != null) && ("dataretention".equals(request.getAttribute("tabtoLoad")))) {
/* 188 */           out.write("\n\t\tshowHide('dataretention');//No i18n\n\t\tshowHide('dataretentionHelp');//No i18n\n\t");
/*     */         }
/* 190 */         else if ((request.getAttribute("tabtoLoad") != null) && ("reportlogosettings".equals(request.getAttribute("tabtoLoad"))))
/*     */         {
/* 192 */           out.write("\n\t\tshowHide('reportlogosettings');//No i18n\n\t\tshowHide('reportlogoHelp');//No i18n\n\t");
/*     */         }
/*     */         else
/*     */         {
/* 196 */           out.write("\n\t\tshowHide('reportssetting');//No i18n\n\t\tshowHide('reportssettingHelp');//No i18n\n\t");
/*     */         }
/* 198 */         out.write("\n  }\n\nfunction validateAndSubmit()\n{\n\n\t");
/* 199 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*     */           return;
/* 201 */         out.write(10);
/* 202 */         if (_jspx_meth_logic_005fnotPresent_005f1(_jspx_page_context))
/*     */           return;
/* 204 */         out.write("\n}\nfunction changeImage()\n{\n\tif(document.getElementById(\"changelogo\").style.display=='none')\n\t{\n\tdocument.getElementById(\"changelogo\").style.display=='block';\n\thideDiv('changeordelete');\n\tshowDiv('changelogo');\n\t}\n}\nfunction resetImage()\n{\n\tvar date = new Date();\n\tdocument.AMActionForm[2].action= \"adminAction.do?method=deleteReportLogo\";\n\tif(confirm('");
/* 205 */         out.print(FormatUtil.getString("am.webclient.deleimagealert.text"));
/* 206 */         out.write("'))\n\t{\n\t\tdocument.AMActionForm[2].submit();\n\t\t\n\t}\n\telse\n\t{\n\t}\n\t\n}\n\nfunction fileFormSubmit()\n{\n\tvar fileName=document.AMActionForm[2].reportLogo.value;\n\tif(trimAll(document.AMActionForm[2].reportLogo.value)!='')\n\t{\n\t      fileName=fileName.substring(fileName.lastIndexOf(\".\")+1,fileName.length).toLowerCase();\n\t      //alert(fileName);\n\t      if (fileName=='png'||fileName=='gif'||fileName=='jpg'||fileName=='jpeg')\n\t      {\n\t       \tdocument.AMActionForm[2].submit();\n\t      }\n\t      else\n\t      {\n\t      \talert('");
/* 207 */         out.print(FormatUtil.getString("am.webclient.selectimage.text"));
/* 208 */         out.write("');//No I18N\n\t      }\n\t}\n\telse\n\t{\n\t      return false;\t\t\n\t\t           \t\n    }\n}\n\n</script>\n\n\n\n\n\n");
/*     */         
/* 210 */         InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 211 */         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 212 */         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */         
/* 214 */         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/NewAdminLayout.jsp");
/* 215 */         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 216 */         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*     */           for (;;) {
/* 218 */             out.write(10);
/* 219 */             out.write(10);
/*     */             
/* 221 */             PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 222 */             _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 223 */             _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*     */             
/* 225 */             _jspx_th_tiles_005fput_005f0.setName("UserArea");
/*     */             
/* 227 */             _jspx_th_tiles_005fput_005f0.setType("string");
/* 228 */             int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 229 */             if (_jspx_eval_tiles_005fput_005f0 != 0) {
/* 230 */               if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 231 */                 out = _jspx_page_context.pushBody();
/* 232 */                 _jspx_th_tiles_005fput_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 233 */                 _jspx_th_tiles_005fput_005f0.doInitBody();
/*     */               }
/*     */               for (;;) {
/* 236 */                 out.write(10);
/* 237 */                 out.write(10);
/* 238 */                 String sucess = (String)request.getAttribute("sucess");
/* 239 */                 if (sucess != null)
/*     */                 {
/* 241 */                   out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n\t  <tr>\n\t    <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\">\n\t      <img src=\"/images/icon_message_success.gif\" width=\"25\" height=\"25\" vspace=\"5\"></td>\n\t    <td width=\"95%\" class=\"bodytext\"  > ");
/* 242 */                   out.print(sucess);
/* 243 */                   out.write("\n\t    </td>\n\t</tr>\n\t</table></br>\n\t");
/*     */                 }
/* 245 */                 out.write("\n\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\">\n\t<tr>\n\t  <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 246 */                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 247 */                 out.write(" &gt; <span class=\"bcactive\">");
/* 248 */                 out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 249 */                 out.write("</span></td>\n\t</tr>\n\n</table>\n\n\n <table width=\"100%\" class=\"itadmin-hide\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n   <tr class=\"tabBtmLine\">\n    \t<td >\n           \t<table id=\"InnerTab\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"innertab_btm_space\">\n               <tbody>\n                 <tr>\n                   <td width=\"17\">    </td>\n                   <td><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                       <tbody>\n\n                         <tr>\n                           <td class=\"tbSelected_Left\" id=\"customreplink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                           <td class=\"tbSelected_Middle\" id=\"customreplink\">\n                        <a href=\"javascript:showHide('reportssetting');\">&nbsp;<span class=\"tabLink\">");
/* 250 */                 out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 251 */                 out.write("</span></a></a>\n                           </td>\n                           <td class=\"tbselected_Right\" id=\"customreplink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                         </tr>\n                       </tbody>\n\n                     </table>\n                   </td>\n\t\t\t\t   ");
/*     */                 
/* 253 */                 boolean isdelegatedAdmin = com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(request.getRemoteUser());
/* 254 */                 if (!isdelegatedAdmin) {
/* 255 */                   out.write("\n                   <td><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                       <tbody>\n                         <tr>\n                           <td class=\"tbUnselected_Left\" id=\"downsumreplink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                           <td class=\"tbUnselected_Middle\" id=\"downsumreplink\">\n                           <a href=\"javascript:showHide('dataretention');\">&nbsp;<span class=\"tabLink\">");
/* 256 */                   out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.dataretention"));
/* 257 */                   out.write("</span></a>\n                           </td>\n                           <td class=\"tbUnselected_Right\" id=\"downsumreplink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n                         </tr>\n                       </tbody>\n                     </table>\n                   </td>\n                     ");
/*     */                 }
/* 259 */                 out.write("\n\t\t\t\t    <td ");
/* 260 */                 out.print(reportLogoStyle);
/* 261 */                 out.write("><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t      \t\t<tbody>\n\t\t      \t\t <tr>\n\t\t      \t\t <td class=\"tbUnSelected_Left\" id=\"replogolink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t      \t\t <td class=\"tbUnSelected_Middle\" id=\"replogolink\">\n\t\t       \t\t\t<a href=\"javascript:showHide('reportlogosettings');\">&nbsp;<span class=\"tabLink\">");
/* 262 */                 out.print(FormatUtil.getString("am.webclient.logosettings.text"));
/* 263 */                 out.write("</span></a>\n\t\t      \t\t </td>\n\t\t      \t\t <td class=\"tbUnSelected_Right\" id=\"replogolink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t       \t\t</tr></tbody>\n\t\t       \t\t</table>\n                   </td>\n\n  </tr></tbody></table></td></tr>\n </table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"60%\" valign=\"top\">\n");
/*     */                 
/* 265 */                 FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 266 */                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 267 */                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f0);
/*     */                 
/* 269 */                 _jspx_th_html_005fform_005f0.setAction("/adminAction.do?method=updateDataCleanUp");
/*     */                 
/* 271 */                 _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 272 */                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 273 */                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */                   for (;;) {
/* 275 */                     out.write("\n<input type=\"hidden\" name=\"method\" value=\"updateDataCleanUp\" >\n<div id=\"dataretention\" style=\"display:none\">\n\n <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\" align=\"center\" >\n        <tr>\n<td width='100%' >\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"6\"  align=\"left\" class='lrtbdarkborder'>\n     <tr><td  width=\"100%\" class=\"tableheadingbborder\"  colspan='2'>");
/* 276 */                     out.print(FormatUtil.getString("am.webclient.dbretention.performancedatabase.text"));
/* 277 */                     out.write(" </td>\n    </tr>    \n    <tr>\n      <td class=\"bodytext label-align\" width=\"40%\">&nbsp;");
/* 278 */                     out.print(FormatUtil.getString("am.webclient.dbretention.performancedatabase.hourly.text"));
/* 279 */                     out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"60%\">");
/* 280 */                     if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                       return;
/* 282 */                     out.write("<span class=\"bodytext\">  ");
/* 283 */                     out.print(FormatUtil.getString("am.webclient.dbretention.days.text"));
/* 284 */                     out.write("</span>\n\n      </td>\n    </tr>\n    <tr>\n      <td class=\"bodytext label-align\" width=\"40%\">&nbsp;");
/* 285 */                     out.print(FormatUtil.getString("am.webclient.dbretention.performancedatabase.daily.text"));
/* 286 */                     out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"60%\">");
/* 287 */                     if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                       return;
/* 289 */                     out.write("<span class=\"bodytext\"> ");
/* 290 */                     out.print(FormatUtil.getString("am.webclient.dbretention.days.text"));
/* 291 */                     out.write(" </span>\n\n      </td>\n    </tr>\n    <tr>\n      <td class=\"bodytext label-align\" width=\"40%\">&nbsp;");
/* 292 */                     out.print(FormatUtil.getString("am.webclient.cleanup.unsolicited.traps.heading.text"));
/* 293 */                     out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"60%\">");
/* 294 */                     if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                       return;
/* 296 */                     out.write("<span class=\"bodytext\"> ");
/* 297 */                     out.print(FormatUtil.getString("am.webclient.cleanup.unsolicited.traps.interval.hours.text"));
/* 298 */                     out.write(" </span>\n      </td>\n    </tr>\n<!--\n    <tr>\n      <td width=\"99%\" height=\"24\" class=\"plainheading1\"  colspan='2'>&nbsp;<span class=\"bodytextbold\">");
/* 299 */                     out.print(FormatUtil.getString("am.webclient.dbretention.alertdatabase.text"));
/* 300 */                     out.write(" </span></td>\n    </tr>\n    <tr>\n      <td class=\"bodytext label-align\" width=\"25%\">&nbsp;");
/* 301 */                     out.print(FormatUtil.getString("am.webclient.dbretention.alertdatabase.maintain.text"));
/* 302 */                     out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"75%\">");
/* 303 */                     if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                       return;
/* 305 */                     out.write("<span class=\"bodytext\"> ");
/* 306 */                     out.print(FormatUtil.getString("am.webclient.dbretention.alertdatabase.message.text"));
/* 307 */                     out.write(".</span>\n      </td>\n    </tr>\n-->\n    <tr>\n      <td width=\"25%\" class=\"tablebottom\" >&nbsp;</td>\n      <td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 308 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 309 */                     out.write("\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"fnFormSubmit();\">\n        &nbsp;&nbsp;<input type=\"reset\" name=\"reset\" value=\"");
/* 310 */                     out.print(FormatUtil.getString("am.webclient.global.Reset.text"));
/* 311 */                     out.write("\"  class=\"buttons btn_reset\">  &nbsp;&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 312 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 313 */                     out.write("\" onclick=\"history.back();\" class=\"buttons btn_link\"></td>\n       </td></tr></table>\n       </td></tr></table>\n\t</div>\n\n\t");
/* 314 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 315 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 319 */                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 320 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*     */                 }
/*     */                 
/* 323 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 324 */                 out.write("\n\n\n\n\n\n\n\t");
/*     */                 
/* 326 */                 String type = request.getParameter("typetoshow");
/*     */                 
/* 328 */                 out.write(10);
/* 329 */                 out.write(9);
/*     */                 
/* 331 */                 FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction.get(FormTag.class);
/* 332 */                 _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 333 */                 _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f0);
/*     */                 
/* 335 */                 _jspx_th_html_005fform_005f1.setAction("/adminAction.do?method=updateReportsSettings");
/*     */                 
/* 337 */                 _jspx_th_html_005fform_005f1.setStyle("display:inline;");
/*     */                 
/* 339 */                 _jspx_th_html_005fform_005f1.setScope("application");
/* 340 */                 int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 341 */                 if (_jspx_eval_html_005fform_005f1 != 0) {
/*     */                   for (;;) {
/* 343 */                     out.write(10);
/* 344 */                     out.write(32);
/* 345 */                     out.write(9);
/*     */                     
/* 347 */                     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 348 */                     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 349 */                     _jspx_th_html_005fhidden_005f0.setParent(_jspx_th_html_005fform_005f1);
/*     */                     
/* 351 */                     _jspx_th_html_005fhidden_005f0.setProperty("settings");
/*     */                     
/* 353 */                     _jspx_th_html_005fhidden_005f0.setValue(type);
/* 354 */                     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 355 */                     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 356 */                       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0); return;
/*     */                     }
/*     */                     
/* 359 */                     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 360 */                     out.write("\n\t<div id=\"reportssetting\" style=\"display:none\">\n\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr >\n\t<td>\n\n  <input type=\"hidden\" name=\"method\" value=\"updateReportsSettings\">\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder itadmin-no-decor\">\n\t<tr >\n    <td class=\"tableheadingbborder itadmin-hide\" colspan='2'>");
/* 361 */                     out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 362 */                     out.write("</td>\n\n  </tr>\n    <tr >\n    <td align=\"left\" class=\"bodytext\" nowrap colspan=\"2\">&nbsp;&nbsp;");
/* 363 */                     out.print(FormatUtil.getString("am.webclient.availablitysettings.MGheading.text"));
/* 364 */                     out.write(" </td>\n\n</tr>\n\n<tr>\n<td align=\"right\" class=\"bodytext\">");
/* 365 */                     if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*     */                       return;
/* 367 */                     out.write("</td>\n<td height=\"22\" class=\"bodytext\">");
/* 368 */                     out.print(FormatUtil.getString("am.webclient.availablitysettings.appcluster.text"));
/* 369 */                     out.write(10);
/* 370 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 371 */                       out.write("\n<a href=\"/help/administrative-operation/report-settings.html#reportssettings\" target=\"_blank\" class=\"staticlinks ithelplink-hide\"><img src=\"/images/icon_quicknote_help.gif\" align=\"absmiddle\" border=\"0\" hspace=\"5\" vspace=\"5\">");
/* 372 */                       out.print(FormatUtil.getString("am.webclient.globalsettings.help"));
/* 373 */                       out.write("</a>\n");
/*     */                     } else {
/* 375 */                       out.write("\n<a href=\"/help/administrative-operation/data-retention.html#reportssettings\" target=\"_blank\" class=\"staticlinks\"><img src=\"/images/icon_quicknote_help.gif\" align=\"absmiddle\" border=\"0\" hspace=\"5\" vspace=\"5\">");
/* 376 */                       out.print(FormatUtil.getString("am.webclient.globalsettings.help"));
/* 377 */                       out.write("</a>\n");
/*     */                     }
/* 379 */                     out.write("\n</td>\n</tr>\n\n<tr>\n<td align=\"right\" class=\"bodytext\">\n");
/* 380 */                     if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*     */                       return;
/* 382 */                     out.write("</td>\n\n\n<td height=\"22\" class=\"bodytext\">");
/* 383 */                     out.print(FormatUtil.getString("am.webclient.availablitysettings.servicegroup.text"));
/* 384 */                     out.write("</td>\n  </tr>\n\n\n<tr >\n  <td align=\"right\" class=\"bodytext\">");
/* 385 */                     if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*     */                       return;
/* 387 */                     out.write("</td>\n  <td height=\"22\" class=\"bodytext\">");
/* 388 */                     out.print(FormatUtil.getString("am.webclient.globalsettings.addscheduledmaintenance.text"));
/* 389 */                     out.write(" </td>\n  </tr>\n\n  <tr >\n    <td align=\"left\" class=\"bodytext\" nowrap colspan=\"2\">&nbsp;&nbsp;</td>\n\n</tr>\n\n<tr >\n    <td align=\"left\" class=\"bodytext\" nowrap colspan=\"2\">&nbsp;&nbsp;");
/* 390 */                     out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.attributes.reports.text"));
/* 391 */                     out.write(" </td>\n\n</tr>\n\n  <tr >\n  <td align=\"right\" class=\"bodytext\">");
/* 392 */                     if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*     */                       return;
/* 394 */                     out.write("</td>\n  <td height=\"22\" class=\"bodytext\" >\n\t\t");
/* 395 */                     out.print(FormatUtil.getString("am.mypage.barchart.text"));
/* 396 */                     out.write("\n  </td>\n  </tr>\n\n <tr >\n  <td align=\"right\" class=\"bodytext\">");
/* 397 */                     if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*     */                       return;
/* 399 */                     out.write("</td>\n  <td height=\"22\" class=\"bodytext\" colspan='2'>\n\t\t");
/* 400 */                     out.print(FormatUtil.getString("webclient.performance.reports.view.linegraph"));
/* 401 */                     out.write("\n  </td>\n  </tr>\n\n<tr >\n  <td align=\"right\" class=\"bodytext\">");
/* 402 */                     if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*     */                       return;
/* 404 */                     out.write("</td>\n  <td height=\"22\" class=\"bodytext\">");
/* 405 */                     out.print(FormatUtil.getString("am.webclient.globalsettings.plotshape.text"));
/* 406 */                     out.write("</td>\n  </tr>\n\n\n\n <tr >\n    <td height=\"22\" class=\"bodytext\">&nbsp;</td>\n    <td align=\"left\" class=\"bodytext\" >");
/* 407 */                     out.print(FormatUtil.getString("am.webclient.global.mvavg.text"));
/* 408 */                     out.write(32);
/* 409 */                     if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*     */                       return;
/* 411 */                     out.write("\n    ");
/* 412 */                     out.print(FormatUtil.getString("am.webclient.global.mvavg.days.text"));
/* 413 */                     out.write(" </td>\n\n  </tr>\n\n    <tr>\n      <td height=\"31\" class=\"tablebottom\" colspan=\"2\"><input name=\"Submit\" value=\"");
/* 414 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 415 */                     out.write("\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"validateAndSubmit();\">\n        &nbsp;&nbsp;<input type=\"reset\" name=\"reset\" value=\"");
/* 416 */                     out.print(FormatUtil.getString("am.webclient.global.Reset.text"));
/* 417 */                     out.write("\"  class=\"buttons btn_reset\">  &nbsp;&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 418 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 419 */                     out.write("\" onclick=\"history.back();\" class=\"buttons btn_link\"></td>\n       </td></tr>\n</table>\n</td>\n  </tr>\n</table>\n</div>\n");
/* 420 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 421 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 425 */                 if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 426 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*     */                 }
/*     */                 
/* 429 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 430 */                 out.write(10);
/*     */                 
/* 432 */                 FormTag _jspx_th_html_005fform_005f2 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.get(FormTag.class);
/* 433 */                 _jspx_th_html_005fform_005f2.setPageContext(_jspx_page_context);
/* 434 */                 _jspx_th_html_005fform_005f2.setParent(_jspx_th_tiles_005fput_005f0);
/*     */                 
/* 436 */                 _jspx_th_html_005fform_005f2.setAction("/adminAction.do?method=saveReportLogo");
/*     */                 
/* 438 */                 _jspx_th_html_005fform_005f2.setStyle("display:inline");
/*     */                 
/* 440 */                 _jspx_th_html_005fform_005f2.setEnctype("multipart/form-data");
/* 441 */                 int _jspx_eval_html_005fform_005f2 = _jspx_th_html_005fform_005f2.doStartTag();
/* 442 */                 if (_jspx_eval_html_005fform_005f2 != 0) {
/*     */                   for (;;) {
/* 444 */                     out.write("\n \n<div id=\"reportlogosettings\" style=\"display:none\">\n\n <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\" align=\"center\" >\n        <tr>\n<td width='100%' >\n<input type=\"hidden\" name=\"method\" value=\"saveReportLogo\" >\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" class='lrtbdarkborder'>\n     <tr class=\"itadmin-hide\"><td class=\"tableheadingbborder\"  colspan='2'>");
/* 445 */                     out.print(FormatUtil.getString("am.webclient.logosettings.text"));
/* 446 */                     out.write(" </td>\n    </tr>\n    <tr class=\"itadmin-hide\">\n    <td colspan='2'>&nbsp;</td></tr>\n\n    <tr>\n        <td class=\"bodytext label-align tdindent\">&nbsp;&nbsp; <b>");
/* 447 */                     out.print(FormatUtil.getString("am.webclient.logoImage.text"));
/* 448 */                     out.write("</b></td>\n        \n        <td class=\"bodytext\" width=\"100%\">\n           <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>\n        \t        \n        \t   \t<td width=\"90%\" class=\"bodytext\">\n        \t   \t<div id=\"changeordelete\" >\n        \t   \t<table><tr>\n        \t   \t<td><img src=\"");
/* 449 */                     out.print(reportlogopath);
/* 450 */                     out.write("\" height=\"54\" width=\"262\"></td><td style=\"padding-left:20px;\">\n        \t   \t<a href=\"javascript:changeImage();\" class=\"staticlinks\">");
/* 451 */                     out.print(FormatUtil.getString("am.webclient.logochangetab.text"));
/* 452 */                     out.write("</a>&nbsp;|&nbsp;<a href=\"javascript:resetImage();\" class=\"staticlinks\">");
/* 453 */                     out.print(FormatUtil.getString("Delete"));
/* 454 */                     out.write("</a></td>\n        \t   \t</tr></table>\n        \t   \t</div>\n        \t   \t<div id=\"changelogo\" style=\"display:none\">\n        \t   \t<table width=\"100%\">\n        \t   \t\t<tr><td><span>\n        \t   \t\t\n\t\t\t\t       ");
/* 455 */                     if (_jspx_meth_html_005ffile_005f0(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*     */                       return;
/* 457 */                     out.write("\n\t\t\t\t\n\t\t\t\t</span></td>\n        \t   \t\t</tr>\n        \t   \t\t<tr><td class=\"bodytext\" >");
/* 458 */                     out.print(FormatUtil.getString("am.webclient.admin.reportlogosettingnote.txt"));
/* 459 */                     out.write("</td></tr></table>\n        \t   \t</div>\n        \t   \t</td>\n        \t   \t\n           </tr></table>\n         </td>\n             \n </tr>\n  \n    <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n    <tr>\n      <td width=\"25%\" class=\"tablebottom\" >&nbsp;</td>\n      <td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 460 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 461 */                     out.write("\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"fileFormSubmit();\">\n        &nbsp;&nbsp;<input type=\"reset\" name=\"reset\" value=\"");
/* 462 */                     out.print(FormatUtil.getString("am.webclient.global.Reset.text"));
/* 463 */                     out.write("\"  class=\"buttons btn_reset\">  &nbsp;&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 464 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 465 */                     out.write("\" onclick=\"history.back();\" class=\"buttons btn_link\"></td>\n       </td></tr></table>\n       </td></tr></table>\n\t</div>\n\n\t");
/* 466 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f2.doAfterBody();
/* 467 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 471 */                 if (_jspx_th_html_005fform_005f2.doEndTag() == 5) {
/* 472 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f2); return;
/*     */                 }
/*     */                 
/* 475 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f2);
/* 476 */                 out.write("\n</td>\n<td width=\"40%\" valign=\"top\" class=\"itadmin-hide\">\n<div id=\"dataretentionHelp\" style=\"display:none\">\n\t");
/* 477 */                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.dataretentionHelp.helpcard")), request.getCharacterEncoding()), out, false);
/* 478 */                 out.write("\n</div>\n<div id=\"reportssettingHelp\" style=\"display:none\">\n\t");
/* 479 */                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.reportssettingHelp.helpcard")), request.getCharacterEncoding()), out, false);
/* 480 */                 out.write("\n</div>\n<div id=\"reportlogoHelp\" style=\"display:none\">\n\t");
/* 481 */                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.reportlogosettingHelp.helpcard")), request.getCharacterEncoding()), out, false);
/* 482 */                 out.write("\n</div>\n</td>\n</tr>\n</table>\n");
/* 483 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f0.doAfterBody();
/* 484 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/* 487 */               if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 488 */                 out = _jspx_page_context.popBody();
/*     */               }
/*     */             }
/* 491 */             if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 492 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0); return;
/*     */             }
/*     */             
/* 495 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0);
/* 496 */             out.write(10);
/* 497 */             if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */               return;
/* 499 */             out.write("\n    ");
/* 500 */             if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */               return;
/* 502 */             out.write(10);
/* 503 */             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 504 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 508 */         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 509 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*     */         }
/*     */         else {
/* 512 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 513 */           out.write(10);
/* 514 */           out.write(10);
/* 515 */           out.write(10);
/*     */         }
/* 517 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 518 */         out = _jspx_out;
/* 519 */         if ((out != null) && (out.getBufferSize() != 0))
/* 520 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 521 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 524 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 530 */     PageContext pageContext = _jspx_page_context;
/* 531 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 533 */     org.apache.taglibs.standard.tag.el.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.el.core.OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(org.apache.taglibs.standard.tag.el.core.OutTag.class);
/* 534 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 535 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 537 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 539 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 540 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 541 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 542 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 543 */       return true;
/*     */     }
/* 545 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 546 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 551 */     PageContext pageContext = _jspx_page_context;
/* 552 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 554 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 555 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 556 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 558 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 559 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 560 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 562 */         out.write("\nalertUser();\n");
/* 563 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 564 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 568 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 569 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 570 */       return true;
/*     */     }
/* 572 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 573 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 578 */     PageContext pageContext = _jspx_page_context;
/* 579 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 581 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 582 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 583 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*     */     
/* 585 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 586 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 587 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*     */       for (;;) {
/* 589 */         out.write("\nalertUser();\n");
/* 590 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 591 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 595 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 596 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 597 */       return true;
/*     */     }
/* 599 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 600 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fnotPresent_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 605 */     PageContext pageContext = _jspx_page_context;
/* 606 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 608 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 609 */     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 610 */     _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*     */     
/* 612 */     _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 613 */     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 614 */     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*     */       for (;;) {
/* 616 */         out.write("\n\n\tdocument.AMActionForm[1].submit();\n");
/* 617 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 618 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 622 */     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 623 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 624 */       return true;
/*     */     }
/* 626 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 627 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 632 */     PageContext pageContext = _jspx_page_context;
/* 633 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 635 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 636 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 637 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 639 */     _jspx_th_html_005ftext_005f0.setProperty("dataclean");
/*     */     
/* 641 */     _jspx_th_html_005ftext_005f0.setSize("4");
/*     */     
/* 643 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext small");
/*     */     
/* 645 */     _jspx_th_html_005ftext_005f0.setMaxlength("5");
/* 646 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 647 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 648 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 649 */       return true;
/*     */     }
/* 651 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 652 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 657 */     PageContext pageContext = _jspx_page_context;
/* 658 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 660 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 661 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 662 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 664 */     _jspx_th_html_005ftext_005f1.setProperty("dailyclean");
/*     */     
/* 666 */     _jspx_th_html_005ftext_005f1.setSize("4");
/*     */     
/* 668 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext small");
/*     */     
/* 670 */     _jspx_th_html_005ftext_005f1.setMaxlength("5");
/* 671 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 672 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 673 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 674 */       return true;
/*     */     }
/* 676 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 677 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 682 */     PageContext pageContext = _jspx_page_context;
/* 683 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 685 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 686 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 687 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 689 */     _jspx_th_html_005ftext_005f2.setProperty("unsolTrapsCleanHrs");
/*     */     
/* 691 */     _jspx_th_html_005ftext_005f2.setSize("4");
/*     */     
/* 693 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext small");
/*     */     
/* 695 */     _jspx_th_html_005ftext_005f2.setMaxlength("5");
/* 696 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 697 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 698 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 699 */       return true;
/*     */     }
/* 701 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 702 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 707 */     PageContext pageContext = _jspx_page_context;
/* 708 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 710 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 711 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 712 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 714 */     _jspx_th_html_005ftext_005f3.setProperty("alertclean");
/*     */     
/* 716 */     _jspx_th_html_005ftext_005f3.setSize("6");
/*     */     
/* 718 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext small");
/*     */     
/* 720 */     _jspx_th_html_005ftext_005f3.setMaxlength("10");
/* 721 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 722 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 723 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 724 */       return true;
/*     */     }
/* 726 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 727 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 732 */     PageContext pageContext = _jspx_page_context;
/* 733 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 735 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 736 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 737 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*     */     
/* 739 */     _jspx_th_html_005fradio_005f0.setProperty("rulefrom");
/*     */     
/* 741 */     _jspx_th_html_005fradio_005f0.setValue("false");
/* 742 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 743 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 744 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 745 */       return true;
/*     */     }
/* 747 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 748 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 753 */     PageContext pageContext = _jspx_page_context;
/* 754 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 756 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 757 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 758 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*     */     
/* 760 */     _jspx_th_html_005fradio_005f1.setProperty("rulefrom");
/*     */     
/* 762 */     _jspx_th_html_005fradio_005f1.setValue("true");
/* 763 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 764 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 765 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 766 */       return true;
/*     */     }
/* 768 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 769 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 774 */     PageContext pageContext = _jspx_page_context;
/* 775 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 777 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(CheckboxTag.class);
/* 778 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 779 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*     */     
/* 781 */     _jspx_th_html_005fcheckbox_005f0.setProperty("addmaintenance");
/*     */     
/* 783 */     _jspx_th_html_005fcheckbox_005f0.setValue("true");
/* 784 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 785 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 786 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 787 */       return true;
/*     */     }
/* 789 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 790 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 795 */     PageContext pageContext = _jspx_page_context;
/* 796 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 798 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 799 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 800 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f1);
/*     */     
/* 802 */     _jspx_th_html_005fradio_005f2.setProperty("graphType");
/*     */     
/* 804 */     _jspx_th_html_005fradio_005f2.setValue("true");
/* 805 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 806 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 807 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 808 */       return true;
/*     */     }
/* 810 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 811 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 816 */     PageContext pageContext = _jspx_page_context;
/* 817 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 819 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 820 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 821 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f1);
/*     */     
/* 823 */     _jspx_th_html_005fradio_005f3.setProperty("graphType");
/*     */     
/* 825 */     _jspx_th_html_005fradio_005f3.setValue("false");
/* 826 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 827 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 828 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 829 */       return true;
/*     */     }
/* 831 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 832 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 837 */     PageContext pageContext = _jspx_page_context;
/* 838 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 840 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(CheckboxTag.class);
/* 841 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 842 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*     */     
/* 844 */     _jspx_th_html_005fcheckbox_005f1.setProperty("mtype");
/*     */     
/* 846 */     _jspx_th_html_005fcheckbox_005f1.setValue("true");
/* 847 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 848 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 849 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 850 */       return true;
/*     */     }
/* 852 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 853 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 858 */     PageContext pageContext = _jspx_page_context;
/* 859 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 861 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 862 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 863 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f1);
/*     */     
/* 865 */     _jspx_th_html_005ftext_005f4.setProperty("seventhirtyMA");
/*     */     
/* 867 */     _jspx_th_html_005ftext_005f4.setSize("1");
/*     */     
/* 869 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext vsmall");
/*     */     
/* 871 */     _jspx_th_html_005ftext_005f4.setMaxlength("2");
/* 872 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 873 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 874 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 875 */       return true;
/*     */     }
/* 877 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 878 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ffile_005f0(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 883 */     PageContext pageContext = _jspx_page_context;
/* 884 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 886 */     org.apache.struts.taglib.html.FileTag _jspx_th_html_005ffile_005f0 = (org.apache.struts.taglib.html.FileTag)this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.FileTag.class);
/* 887 */     _jspx_th_html_005ffile_005f0.setPageContext(_jspx_page_context);
/* 888 */     _jspx_th_html_005ffile_005f0.setParent((Tag)_jspx_th_html_005fform_005f2);
/*     */     
/* 890 */     _jspx_th_html_005ffile_005f0.setProperty("reportLogo");
/* 891 */     int _jspx_eval_html_005ffile_005f0 = _jspx_th_html_005ffile_005f0.doStartTag();
/* 892 */     if (_jspx_th_html_005ffile_005f0.doEndTag() == 5) {
/* 893 */       this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 894 */       return true;
/*     */     }
/* 896 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 897 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 902 */     PageContext pageContext = _jspx_page_context;
/* 903 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 905 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 906 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 907 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 909 */     _jspx_th_tiles_005fput_005f1.setName("HelpContent");
/*     */     
/* 911 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/test.jsp");
/* 912 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 913 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 914 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 915 */       return true;
/*     */     }
/* 917 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 918 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 923 */     PageContext pageContext = _jspx_page_context;
/* 924 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 926 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 927 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 928 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 930 */     _jspx_th_tiles_005fput_005f2.setName("Footer");
/*     */     
/* 932 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/footer.jsp");
/* 933 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 934 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 935 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 936 */       return true;
/*     */     }
/* 938 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 939 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DataCleanUp_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */