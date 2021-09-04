/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.ImportTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class TemplateThresholdConfiguration_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  28 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  29 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  43 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  47 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  54 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  58 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  59 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  60 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*  61 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  62 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  63 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  70 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  73 */     JspWriter out = null;
/*  74 */     Object page = this;
/*  75 */     JspWriter _jspx_out = null;
/*  76 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  80 */       response.setContentType("text/html;charset=UTF-8");
/*  81 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  83 */       _jspx_page_context = pageContext;
/*  84 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  85 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  86 */       session = pageContext.getSession();
/*  87 */       out = pageContext.getOut();
/*  88 */       _jspx_out = out;
/*     */       
/*  90 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/validation.js\"></SCRIPT><head>\n");
/*  91 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  92 */       out.write("\n<title>");
/*  93 */       out.print(FormatUtil.getString("am.webclient.configurealert.thresholdactionconfigurationtitle"));
/*  94 */       out.write("</title>\n<link href=\"/images/");
/*  95 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  97 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
/*     */       
/*  99 */       HashMap list = new HashMap((Map)request.getAttribute("thresholddetail"));
/*     */       
/* 101 */       out.write("\n<script>\nfunction addThresholdActionConfig()\n{\n\t");
/* 102 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/* 104 */       out.write("\n\tvar attributeid=document.getElementById(\"attributeid\").value;\n\tvar params='&attributeid='+ attributeid;\t");
/* 105 */       out.write("\t\n\t\n\tvar attributename=document.getElementById(\"attributename\").value;\n\tparams=params + '&attributename='+ attributename;\t");
/* 106 */       out.write("\t\n\tvar selectedthresholdtxt='");
/* 107 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/* 109 */       out.write(39);
/* 110 */       out.write(59);
/* 111 */       out.write(32);
/* 112 */       out.write("\n\t\n\t");
/*     */       
/* 114 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 115 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 116 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 118 */       _jspx_th_c_005fif_005f0.setTest("${attributetype!= 1 && attributetype !=2}");
/* 119 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 120 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/* 122 */           out.write(" \t\t\n\tselectedthreshold=document.getElementById(\"thresholdlistid\").selectedIndex;\n\tif(selectedthreshold ==0){\n\t\talert('");
/* 123 */           if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */             return;
/* 125 */           out.write("'); ");
/* 126 */           out.write("\n\t\treturn;\n\t}\n\tvar thresholdid=document.getElementById(\"thresholdlistid\").options[selectedthreshold].value;\n\tif(thresholdid == 'Newfalse'){\n\t\tvar displayname = jQuery('input[name=displayname]').val()\t\t// NO I18N\n\t\tvar criticalthresholdvalue = jQuery('input[name=criticalthresholdvalue]').val()\t\t// NO I18N\n\t\tvar warningthresholdvalue = jQuery('input[name=warningthresholdvalue]').val()\t\t// NO I18N\n\t\tvar infothresholdvalue = jQuery('input[name=infothresholdvalue]').val()\t\t// NO I18N\n\t\t\n\t\tif(trimAll(displayname)==\"Threshold Name\" || trimAll(displayname)==\"\")\n\t\t{\n\t\t  window.alert('");
/* 127 */           out.print(FormatUtil.getString("am.webclient.configurealert.alertthresholdname"));
/* 128 */           out.write("');\n\t\t  return false;\n\t\t}\n\t\tif(displayNameHasQuotes(trimAll(displayname),'");
/* 129 */           out.print(FormatUtil.getString("am.webclient.configurealert.alertremovesinglequote"));
/* 130 */           out.write("'))\n\t\t{\n\t\treturn false;\n\t\t}\n\n\t\tif(trimAll(criticalthresholdvalue)==\"\" || isPositiveInteger(criticalthresholdvalue)==false)\n\t\t{\n\t\t    window.alert('");
/* 131 */           out.print(FormatUtil.getString("am.webclient.configurealert.alertpositivecriticalthreshold"));
/* 132 */           out.write("');\n\t\t    return false;\n\t\t}\n\t\tif(document.getElementById('thresholdInlineAdvanced').style.display != 'none')\n\t\t{\n\t\t  if(trimAll(warningthresholdvalue)==\"\" || isPositiveInteger(warningthresholdvalue)==false)\n\t\t  {\n\t\t\twindow.alert('");
/* 133 */           out.print(FormatUtil.getString("am.webclient.configurealert.alertpositivewarningthreshold"));
/* 134 */           out.write("');\n\t\t\treturn false;\n\t\t  }\n\t\t  else if(trimAll(infothresholdvalue)==\"\" || isPositiveInteger(infothresholdvalue)==false)\n\t\t  {\n\t\t\twindow.alert('");
/* 135 */           out.print(FormatUtil.getString("am.webclient.configurealert.alertpositiveclearthreshold"));
/* 136 */           out.write("');\n\t\t\treturn false;\n\t\t  }\n\t\t}\n\t\tdisplayname=jQuery('input[name=displayname]').val();\t\t// NO I18N\n\t\tparams=params+'&thresholdname='+displayname +'&thresholdactionexist=true'; ");
/* 137 */           out.write("\n\t\tselectedthresholdtxt=displayname ;\n\t\tif(window.opener.document.getElementById('thresholdname_'+attributeid)){\n\t\t\tvar thresholdnamehidden=window.opener.document.getElementById('thresholdname_'+attributeid);\t\n\t\t\tthresholdnamehidden.value=displayname;\t\n\t\t}else{\n\t\t\tvar thresholdnamehidden=window.opener.document.createElement(\"input\");\n\t\t        thresholdnamehidden.type=\"hidden\";\n\t\t        thresholdnamehidden.id=\"thresholdname_\"+attributeid;\n\t\t        thresholdnamehidden.name=\"thresholdname_\"+attributeid;\n\t\t        thresholdnamehidden.value=displayname;\n\t\t        window.opener.document.forms[\"AMProcessTemplateForm\"].appendChild(thresholdnamehidden);\n\t\t}\t\t\n\n\t}else{\n\t\tparams=params+'&thresholdid='+thresholdid +'&thresholdactionexist=true'; ");
/* 138 */           out.write("\n\t\tselectedthresholdtxt=document.getElementById(\"thresholdlistid\").options[selectedthreshold].text ;\n\t}\t\n\t\n\tif(window.opener.document.getElementById('threshold_'+attributeid)){\n\t\tvar thresholdhidden=window.opener.document.getElementById('threshold_'+attributeid);\n\t\tthresholdhidden.value=thresholdid;\t\n\t}else{\n\tvar thresholdhidden=window.opener.document.createElement(\"input\");\n        thresholdhidden.type=\"hidden\";\n\tthresholdhidden.id=\"threshold_\"+attributeid;\n        thresholdhidden.name=\"threshold_\"+attributeid;\n        thresholdhidden.value=thresholdid;\n        window.opener.document.forms[\"AMProcessTemplateForm\"].appendChild(thresholdhidden);\n\t}\n\t");
/* 139 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 140 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 144 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 145 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 148 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 149 */         out.write("\n\t\n\n\n\tvar criticalselected=getSelectedIdx(document.getElementById(\"selectedcritical\"));\n\tif(criticalselected){\n\n\t\tparams=params+'&critical='+criticalselected; ");
/* 150 */         out.write("\n\t\tif(window.opener.document.getElementById('criticalaction_'+attributeid)){\n                var criticalactions=window.opener.document.getElementById('criticalaction_'+attributeid);\n                criticalactions.value=criticalselected;\n\t        }else{\n\t\tvar criticalactions=window.opener.document.createElement(\"input\");\n\t        criticalactions.type=\"hidden\";\n\t\tcriticalactions.id=\"criticalaction_\"+attributeid;\n\t        criticalactions.name=\"criticalaction_\"+attributeid;\n\t        criticalactions.value=criticalselected;\t\n\t        window.opener.document.forms[\"AMProcessTemplateForm\"].appendChild(criticalactions);\n\t\t}\n\t}else{\n\t\tif(window.opener.document.getElementById('criticalaction_'+attributeid)){\n\t               \t var criticalactions=window.opener.document.getElementById('criticalaction_'+attributeid);\n                \tcriticalactions.value=\"\";\n        \t}\n\t}\n\n\n\t");
/* 151 */         if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */           return;
/* 153 */         out.write("\t\n\n\tvar clearselected=getSelectedIdx(document.getElementById(\"selectedclear\"));\n        if(clearselected){\n\t\t\n                params=params+'&clear='+clearselected; ");
/* 154 */         out.write("\n\t\tif(window.opener.document.getElementById('clearaction_'+attributeid)){\n                var clearactions=window.opener.document.getElementById('clearaction_'+attributeid);\n                clearactions.value=clearselected;\n                }else{\n\t\tvar clearactions=window.opener.document.createElement(\"input\");\n\t\tclearactions.id=\"clearaction_\"+attributeid;\n                clearactions.type=\"hidden\";\n                clearactions.name=\"clearaction_\"+attributeid;\n                clearactions.value=clearselected;\n                window.opener.document.AMProcessTemplateForm.appendChild(clearactions);\n\t\t}\n        }else{\n\t        if(window.opener.document.getElementById('clearaction_'+attributeid)){\n        \t        var clearactions=window.opener.document.getElementById('clearaction_'+attributeid);\n                \tclearactions.value=\"\";\n        \t}\n\t}\n\n\tvar tablecell=window.opener.document.getElementById(attributeid+\"_text\")\n\t\t\n\tvar templatetype=window.opener.document.getElementById('templatetype').value;\n\tvar url='<a href=\"javascript:MM_openBrWindow(\\'/ProcessTemplates.do?templatetype='+templatetype+'&method=getThresholdActionList'+params +'\\',\\'\\',\\'resizable=yes,scrollbars=yes,width=800,height=440\\');\" class=\"staticlinks\">';\n");
/* 155 */         out.write("\ttablecell.innerHTML=url + selectedthresholdtxt + '</a>';\n\n\t");
/* 156 */         if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*     */           return;
/* 158 */         out.write("\n\t window.close();\n}\n\nfunction getSelectedIdx(selectElement)\n{\n\tvar selected=new Array();\n\tfor(j = 0; j < selectElement.length; j++) { \n\t\tselected.push(selectElement.options[j].value);\n\t } \n\treturn selected.toString();\n}\n\nfunction removeThresholdActionConfig()\n{\n\t\n\t var attributeid=document.getElementById(\"attributeid\").value;\n\tvar params='&attributeid='+ attributeid;\t");
/* 159 */         out.write("\t\n\t\n\tvar attributename=document.getElementById(\"attributename\").value;\n\tparams=params + '&attributename='+ attributename;\t");
/* 160 */         out.write("\t\n\tvar selectedthresholdtxt='");
/* 161 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*     */           return;
/* 163 */         out.write(39);
/* 164 */         out.write(59);
/* 165 */         out.write(32);
/* 166 */         out.write("\n\tvar templatetype=window.opener.document.getElementById('templatetype').value;\n\tvar url='<a href=\"javascript:MM_openBrWindow(\\'/ProcessTemplates.do?templatetype='+templatetype+'&method=getThresholdActionList'+params +'\\',\\'\\',\\'resizable=yes,scrollbars=yes,width=800,height=440\\');\" class=\"staticlinks\">';\n\tvar tablecell=window.opener.document.getElementById(attributeid+\"_text\"); \n\ttablecell.innerHTML=url + selectedthresholdtxt + '</a>';\n\tif(window.opener.document.getElementById('threshold_'+attributeid)){\n                var thresholdhidden=window.opener.document.getElementById('threshold_'+attributeid);\n                thresholdhidden.value=\"\";\n        }\n\n\tif(window.opener.document.getElementById('criticalaction_'+attributeid)){\n                var criticalactions=window.opener.document.getElementById('criticalaction_'+attributeid);\n                criticalactions.value=\"\";\n        }\n\n\t\t\n\tif(window.opener.document.getElementById('warningaction_'+attributeid)){\n                var criticalactions=window.opener.document.getElementById('warningaction_'+attributeid);\n");
/* 167 */         out.write("                criticalactions.value=\"\";\n        }\n\n\tif(window.opener.document.getElementById('clearaction_'+attributeid)){\n                var clearactions=window.opener.document.getElementById('clearaction_'+attributeid);\n                clearactions.value=\"\";\n        }\n\twindow.close();\n}\n\nfunction createNewThreshold()\n{\n\tvar displayname=\"\";\n\tvar criticalthresholdvalue=\"\";\n\tvar warningthresholdvalue=\"\";\n\tvar clearthresholdvalue=\"\";\n\tvar criticalthresholdcondition=\"\";\n\tvar warningthresholdcondition=\"\";\n\tvar clearthresholdcondition=\"\";\n\n\tdisplayname=jQuery('input[name=displayname]').val();\t\t// NO I18N\n\tcriticalthresholdcondition=jQuery('input[name=criticalthresholdcondition]').val();\t\t// NO I18N\n\tcriticalthresholdvalue=jQuery('input[name=criticalthresholdvalue]').val();\t\t// NO I18N\n\n\tif(document.getElementById('thresholdInlineAdvanced').style.display == 'none')\n\t{\n\t\twarningthresholdvalue = jQuery('input[name=criticalthresholdvalue]').val();\t\t// NO I18N\n\t\tclearthresholdvalue = jQuery('input[name=criticalthresholdvalue]').val();\t\t// NO I18N\n");
/* 168 */         out.write("\n\t\tif (criticalthresholdvalue=='NE')\n\t\t{\n\t\t\twarningthresholdcondition='NE';\n\t\t}\n\t\telse\n\t\t{\n\t\t\twarningthresholdcondition='EQ';\n\t\t}\n\n\t\tif (criticalthresholdcondition=='LT' || criticalthresholdcondition=='LE')\n\t\t{\n\t\t\tclearthresholdcondition='GT';\n\t\t}\n\t\telse if (criticalthresholdcondition=='GT' || criticalthresholdcondition=='GE')\n\t\t{\n\t\t\tclearthresholdcondition='LT';\n\t\t}\n\t\telse if (criticalthresholdcondition=='EQ')\n\t\t{\n\t\t\tclearthresholdcondition='NE';\n\t\t}\n\t}else{\n\t\twarningthresholdvalue=jQuery('input[name=warningthresholdvalue]').val();\t\t// NO I18N\n\t\twarningthresholdcondition=jQuery('input[name=warningthresholdcondition]').val();\t\t// NO I18N\n\t\tclearthresholdvalue=jQuery('input[name=infothresholdvalue]').val();\t\t// NO I18N\n\t\tclearthresholdcondition=jQuery('input[name=infothresholdcondition]').val();\t\t// NO I18N\n\t}\n\n\tjQuery.ajax({\n\t\turl: \"/ProcessTemplates.do\",//No I18N\n\tdata: { method: \"createThreshold\", displayname: displayname, criticalthresholdcondition: criticalthresholdcondition, warningthresholdcondition: warningthresholdcondition, infothresholdcondition: clearthresholdcondition, criticalthresholdvalue: criticalthresholdvalue, warningthresholdvalue: warningthresholdvalue, infothresholdvalue: clearthresholdvalue},//No I18N\n");
/* 169 */         out.write("\ttype: 'POST',//No I18N\n\t//dataType: \"html\",//No I18N\n\tsuccess: function(result) {\n\t\t//alert(result);//No I18N\n\t\t window.close();\n\t}\n\t});\n\t\n}\n\n</script>\n\n\n<form>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n        <td height=\"29\" class=\"tableheading\">");
/* 170 */         out.print(FormatUtil.getString("am.webclient.configurealert.configurealerts"));
/* 171 */         out.write(" </td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"3\" cellpadding=\"2\" class=\"lrborder\">\n      <tr>\n\t      <td class=\"bodytext\" width=\"30%\"> ");
/* 172 */         out.print(FormatUtil.getString("am.webclient.configurealert.attribute"));
/* 173 */         out.write("</td>\n\t      <td width=\"100%\" >   <span class=\"bodytext\">\n\t\t<input type=hidden value='");
/* 174 */         if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */           return;
/* 176 */         out.write("' id=\"attributeid\">\n\t\t<input type=hidden value='");
/* 177 */         if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */           return;
/* 179 */         out.write("' id=\"attributename\">  <span class=\"bodytext\"> ");
/* 180 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*     */           return;
/* 182 */         out.write("</span>     </td>\n\t      <td class=\"bodytext\"></td>\n      </tr>\n\t");
/* 183 */         out.write(10);
/* 184 */         out.write(9);
/*     */         
/* 186 */         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 187 */         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 188 */         _jspx_th_c_005fif_005f3.setParent(null);
/*     */         
/* 190 */         _jspx_th_c_005fif_005f3.setTest("${attributetype != 1 &&  attributetype != 2}");
/* 191 */         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 192 */         if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */           for (;;) {
/* 194 */             out.write(" \n      <tr>\n     \t\t<td  width=\"30%\" class=\"bodytext\">");
/* 195 */             out.print(FormatUtil.getString("am.webclient.configurealert.associatethreshold"));
/* 196 */             out.write("</td>\t\n     \t\t<td  width=\"30%\" class=\"bodytext\">\n     \t\t\n     \t\t<select id=\"thresholdlistid\" name=\"thresholdList\" class=\"formtext\" onchange=\"showThresholdDetail(this.value)\" style=\"width: 180px;\">\n     \t\t\t<option value=\"Reset\">");
/* 197 */             out.print(FormatUtil.getString("am.webclient.configurealert.selectthresold"));
/* 198 */             out.write("</option>\n     \t\t\t<option value=\"Newfalse\" style=\"font-weight: bold;\">-- ");
/* 199 */             out.print(FormatUtil.getString("am.webclient.toolbar.newthreshold.text"));
/* 200 */             out.write(" --</option>\n     \t\t\t");
/*     */             
/* 202 */             java.util.Iterator listAttr = list.keySet().iterator();
/* 203 */             while (listAttr.hasNext())
/*     */             {
/* 205 */               Object obj = listAttr.next();
/* 206 */               if ((request.getParameter("thresholdid") != null) && (obj.toString().equalsIgnoreCase(request.getParameter("thresholdid"))))
/*     */               {
/* 208 */                 out.write("\n     \t\t\t<option value=\"");
/* 209 */                 out.print(obj);
/* 210 */                 out.write("\" SELECTED>");
/* 211 */                 out.print(FormatUtil.getString((String)list.get(obj)));
/* 212 */                 out.write("</option>\n     \t\t\t");
/* 213 */               } else if ((request.getParameter("thresholdname") != null) && (obj.toString().equalsIgnoreCase(request.getParameter("thresholdid")))) {
/* 214 */                 out.write("\n     \t\t\t<option value=\"");
/* 215 */                 out.print(obj);
/* 216 */                 out.write("\" SELECTED>");
/* 217 */                 out.print(FormatUtil.getString((String)list.get(obj)));
/* 218 */                 out.write("</option>\n     \t\t\t");
/*     */               }
/*     */               else {
/* 221 */                 out.write("\n     \t\t\t<option value=\"");
/* 222 */                 out.print(obj);
/* 223 */                 out.write(34);
/* 224 */                 out.write(62);
/* 225 */                 out.print(FormatUtil.getString((String)list.get(obj)));
/* 226 */                 out.write("</option>\n     \t\t\t");
/*     */               }
/*     */             }
/* 229 */             out.write("\n     \t\t</select>\n     \t\t\n     \t\t</td>\t\n     </tr>\n     <tr>\n\t\t<td colspan=\"2\">\n\t\t <div id=\"thresholddetail\"> </div>\n\t\t</td>\n     </tr>\n\t");
/* 230 */             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 231 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 235 */         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 236 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*     */         }
/*     */         else {
/* 239 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 240 */           out.write("\n     <tr>\n                <td colspan=\"5\">\n                        ");
/* 241 */           if (_jspx_meth_c_005fimport_005f0(_jspx_page_context))
/*     */             return;
/* 243 */           out.write("\n                </td>\n        </tr>\n\n</table>\n     <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n    <tr>\n      <td height=\"29\" align=\"center\" class=\"tablebottom\">\n\t<input name=\"savebutton\" type=\"button\" class=\"buttons\" value=\"");
/* 244 */           if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*     */             return;
/* 246 */           out.write("\" onClick=\"javascript:addThresholdActionConfig()\">\n        <input name=\"closebutton\" type=\"button\" class=\"buttons\" value=\"");
/* 247 */           out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 248 */           out.write("\" onClick=\"javascript:window.close();\">\n\t");
/*     */           
/* 250 */           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 251 */           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 252 */           _jspx_th_c_005fif_005f4.setParent(null);
/*     */           
/* 254 */           _jspx_th_c_005fif_005f4.setTest("${param.thresholdactionexist=='true'}");
/* 255 */           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 256 */           if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */             for (;;) {
/* 258 */               out.write("\n        <input name=\"removebutton\" type=\"button\" class=\"buttons\" value=\"");
/* 259 */               out.print(FormatUtil.getString("Remove Configuration"));
/* 260 */               out.write("\" onClick=\"javascript:removeThresholdActionConfig();\">\n\t");
/* 261 */               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 262 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 266 */           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 267 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*     */           }
/*     */           else {
/* 270 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 271 */             out.write("\n\n     </td>\n    </tr>\n   </table>\t\t\t\t\n</form>\n\n");
/*     */           }
/* 273 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 274 */         out = _jspx_out;
/* 275 */         if ((out != null) && (out.getBufferSize() != 0))
/* 276 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 277 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 280 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 286 */     PageContext pageContext = _jspx_page_context;
/* 287 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 289 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 290 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 291 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 293 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 295 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 296 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 297 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 298 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 299 */       return true;
/*     */     }
/* 301 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 302 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 307 */     PageContext pageContext = _jspx_page_context;
/* 308 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 310 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 311 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 312 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 314 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 315 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 316 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 318 */         out.write("\n\talertUser();\n\treturn false;\n");
/* 319 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 320 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 324 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 325 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 326 */       return true;
/*     */     }
/* 328 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 329 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 334 */     PageContext pageContext = _jspx_page_context;
/* 335 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 337 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 338 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 339 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 340 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 341 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 342 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 343 */         out = _jspx_page_context.pushBody();
/* 344 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 345 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 348 */         out.write("am.webclient.processtemplate.associate");
/* 349 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 350 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 353 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 354 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 357 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 358 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 359 */       return true;
/*     */     }
/* 361 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 362 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 367 */     PageContext pageContext = _jspx_page_context;
/* 368 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 370 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 371 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 372 */     _jspx_th_fmt_005fmessage_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f0);
/* 373 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 374 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 375 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 376 */         out = _jspx_page_context.pushBody();
/* 377 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 378 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 381 */         out.write("am.webclient.processtemplate.selectthreshold");
/* 382 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 383 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 386 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 387 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 390 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 391 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 392 */       return true;
/*     */     }
/* 394 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 395 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 400 */     PageContext pageContext = _jspx_page_context;
/* 401 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 403 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 404 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 405 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 407 */     _jspx_th_c_005fif_005f1.setTest("${attributetype!= 1}");
/* 408 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 409 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 411 */         out.write(" \n\tvar warningselected=getSelectedIdx(document.getElementById(\"selectedwarning\"));\n\tif(warningselected){\n\t\tparams=params+'&warning='+warningselected; ");
/* 412 */         out.write("\n\t\tif(window.opener.document.getElementById('warningaction_'+attributeid)){\n       \t\t        var warningactions=window.opener.document.getElementById('warningaction_'+attributeid);\n\t                warningactions.value=warningselected;\n                }else{\t\n\t\tvar warningactions=window.opener.document.createElement(\"input\");\n                warningactions.type=\"hidden\";\n                warningactions.name=\"warningaction_\"+attributeid;\n                warningactions.value=warningselected;\n                window.opener.document.AMProcessTemplateForm.appendChild(warningactions);\n\t\t}\n\t}else{\n\t\tif(window.opener.document.getElementById('warningaction_'+attributeid)){\n        \t        var criticalactions=window.opener.document.getElementById('warningaction_'+attributeid);\n                \tcriticalactions.value=\"\";\n\t        }\n\t}\n\t");
/* 413 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 414 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 418 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 419 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 420 */       return true;
/*     */     }
/* 422 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 423 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 428 */     PageContext pageContext = _jspx_page_context;
/* 429 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 431 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 432 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 433 */     _jspx_th_c_005fif_005f2.setParent(null);
/*     */     
/* 435 */     _jspx_th_c_005fif_005f2.setTest("${attributetype!= 1 && attributetype !=2}");
/* 436 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 437 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 439 */         out.write("\n\tif(thresholdid == 'Newfalse'){\n\t\tcreateNewThreshold();\n\t\treturn;\n\t}\n\t");
/* 440 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 441 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 445 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 446 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 447 */       return true;
/*     */     }
/* 449 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 450 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 455 */     PageContext pageContext = _jspx_page_context;
/* 456 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 458 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 459 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 460 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/* 461 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 462 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 463 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 464 */         out = _jspx_page_context.pushBody();
/* 465 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 466 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 469 */         out.write("am.webclient.processtemplate.associate");
/* 470 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 471 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 474 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 475 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 478 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 479 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 480 */       return true;
/*     */     }
/* 482 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 483 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 488 */     PageContext pageContext = _jspx_page_context;
/* 489 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 491 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 492 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 493 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 495 */     _jspx_th_c_005fout_005f1.setValue("${param.attributeid}");
/* 496 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 497 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 498 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 499 */       return true;
/*     */     }
/* 501 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 502 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 507 */     PageContext pageContext = _jspx_page_context;
/* 508 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 510 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 511 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 512 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 514 */     _jspx_th_c_005fout_005f2.setValue("${param.attributename}");
/* 515 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 516 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 517 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 518 */       return true;
/*     */     }
/* 520 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 521 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 526 */     PageContext pageContext = _jspx_page_context;
/* 527 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 529 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 530 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 531 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/* 532 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 533 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 534 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 535 */         out = _jspx_page_context.pushBody();
/* 536 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 537 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 540 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_fmt_005fmessage_005f3, _jspx_page_context))
/* 541 */           return true;
/* 542 */         out.write(32);
/* 543 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 544 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 547 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 548 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 551 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 552 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 553 */       return true;
/*     */     }
/* 555 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 556 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_fmt_005fmessage_005f3, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 561 */     PageContext pageContext = _jspx_page_context;
/* 562 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 564 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 565 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 566 */     _jspx_th_c_005fout_005f3.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_fmt_005fmessage_005f3);
/*     */     
/* 568 */     _jspx_th_c_005fout_005f3.setValue("${param.attributename}");
/* 569 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 570 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 571 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 572 */       return true;
/*     */     }
/* 574 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 575 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fimport_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 580 */     PageContext pageContext = _jspx_page_context;
/* 581 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 583 */     ImportTag _jspx_th_c_005fimport_005f0 = (ImportTag)this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.get(ImportTag.class);
/* 584 */     _jspx_th_c_005fimport_005f0.setPageContext(_jspx_page_context);
/* 585 */     _jspx_th_c_005fimport_005f0.setParent(null);
/*     */     
/* 587 */     _jspx_th_c_005fimport_005f0.setUrl("/jsp/TemplateActions.jsp");
/* 588 */     int[] _jspx_push_body_count_c_005fimport_005f0 = { 0 };
/*     */     try {
/* 590 */       int _jspx_eval_c_005fimport_005f0 = _jspx_th_c_005fimport_005f0.doStartTag();
/* 591 */       if (_jspx_th_c_005fimport_005f0.doEndTag() == 5)
/* 592 */         return true;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 595 */         int tmp108_107 = 0; int[] tmp108_105 = _jspx_push_body_count_c_005fimport_005f0; int tmp110_109 = tmp108_105[tmp108_107];tmp108_105[tmp108_107] = (tmp110_109 - 1); if (tmp110_109 <= 0) break;
/* 596 */         out = _jspx_page_context.popBody(); }
/* 597 */       _jspx_th_c_005fimport_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 599 */       _jspx_th_c_005fimport_005f0.doFinally();
/* 600 */       this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.reuse(_jspx_th_c_005fimport_005f0);
/*     */     }
/* 602 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 607 */     PageContext pageContext = _jspx_page_context;
/* 608 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 610 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 611 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 612 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/* 613 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 614 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 615 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 616 */         out = _jspx_page_context.pushBody();
/* 617 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 618 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 621 */         out.write("am.webclient.processtemplate.addtotemplate");
/* 622 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 623 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 626 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 627 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 630 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 631 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 632 */       return true;
/*     */     }
/* 634 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 635 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\TemplateThresholdConfiguration_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */