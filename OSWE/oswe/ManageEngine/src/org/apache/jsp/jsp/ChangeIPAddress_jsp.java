/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ChangeIPAddress_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   24 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   30 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   31 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*   32 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   56 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   60 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   77 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   81 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   82 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   83 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   84 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*   85 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*   86 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*   87 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   88 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   89 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   90 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   91 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*   93 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*   94 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*   95 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*   96 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  103 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  106 */     JspWriter out = null;
/*  107 */     Object page = this;
/*  108 */     JspWriter _jspx_out = null;
/*  109 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  113 */       response.setContentType("text/html;charset=UTF-8");
/*  114 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  116 */       _jspx_page_context = pageContext;
/*  117 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  118 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  119 */       session = pageContext.getSession();
/*  120 */       out = pageContext.getOut();
/*  121 */       _jspx_out = out;
/*      */       
/*  123 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/*  124 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  125 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  127 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  128 */       out.write(10);
/*  129 */       out.write(10);
/*  130 */       out.write(10);
/*      */       
/*  132 */       request.setAttribute("HelpKey", "Configure Mail Server");
/*      */       
/*  134 */       out.write("\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n\n\nfunction myOnLoad()\n{\n ");
/*  135 */       if ((request.getParameter("type") != null) && ((request.getParameter("type").equals("dates")) || (request.getParameter("type").equals("showdates"))))
/*      */       {
/*  137 */         out.write("\n   \n    document.getElementById(\"iplink\").className = \"permissions\";\n    document.getElementById(\"datelink\").className = \"Profile\";\n    showDiv(\"dateconfig\");\n    hideDiv(\"ipconfig\");\n   ");
/*      */       }
/*  139 */       out.write("\n\n\n\n}\nfunction showHide(show,hide)\n{\nif(show==\"ipconfig\"){\ndocument.getElementById(\"iplink\").className = \"Profile\";\ndocument.getElementById(\"datelink\").className = \"permissions\";\n}\nelse{\ndocument.getElementById(\"iplink\").className = \"permissions\";\ndocument.getElementById(\"datelink\").className = \"Profile\";\n}\ndocument.getElementById(show).style.display = \"block\";\ndocument.getElementById(hide).style.display = \"none\";\n}\nfunction reDirect()\n{\n  ");
/*  140 */       if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */       {
/*  142 */         out.write("\n   location.href=\"/showTile.do?TileName=Tile.AdminConf\";\n  ");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  147 */         out.write("\n\t location.href=\"/showTile.do?TileName=Tile.EnterpriseAdminConf\";\t\n   ");
/*      */       }
/*      */       
/*  150 */       out.write("\n}\nfunction getEnable(a)\n{ \n\n    var inf=a.july.value;\n    \n   url=\"/changeip.do?method=getIPConfiguration&inface=\"+inf;\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getValueForEnable;\n    http.send(null);\n }\n function getValueForEnable()\n{ \n    if(http.readyState == 4)\n    {\n      var result = http.responseText;\n     \n      var id=result;\n      var stringtokens=id.split(\"#\");\n      shost=stringtokens[0];\n\n      sip=stringtokens[1];\n     \n      snet=stringtokens[2];\n      sgate=stringtokens[3];\n      \n      sdns=stringtokens[4];\n    \n      document.getElementById(\"ip\").value=sip;\n      document.getElementById(\"host\").value=shost;\n      document.getElementById(\"net\").value=snet;\n      document.getElementById(\"gate\").value=sgate;\n      document.getElementById(\"dns\").value=sdns;\n      \n    \n     } \n }\n\n\n\n\n//here forms[1] represent the form used to change ip configuration\nfunction formSubmitAction()\n{\ndocument.forms[1].submit();\n}\nfunction dateSubmitAction()\n{\ndocument.forms[2].submit();\n}\nfunction fnDateSubmit()\n");
/*  151 */       out.write("{\n\n");
/*  152 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  154 */       out.write(10);
/*      */       
/*  156 */       org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/*  157 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  158 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */       
/*  160 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  161 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  162 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */         for (;;) {
/*  164 */           out.write("\nif(document.forms[2].monday.value=='')\n\t{\n            alert(\"");
/*  165 */           out.print(FormatUtil.getString("am.webclient.systemsettings.datesettings.jsalert.text"));
/*  166 */           out.write("\");\n               \n                return false;\n          }\n          else\n          {\n          \n            document.forms[2].submit();\n          }\n\n");
/*  167 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  168 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  172 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  173 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */       }
/*      */       else {
/*  176 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  177 */         out.write("\n}\nfunction fnFormSubmit()\n{\n");
/*  178 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */           return;
/*  180 */         out.write(10);
/*      */         
/*  182 */         org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/*  183 */         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  184 */         _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */         
/*  186 */         _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/*  187 */         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  188 */         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */           for (;;) {
/*  190 */             out.write("\n\n\tif(document.forms[1].january.value=='')\n\t{\n            alert(\"");
/*  191 */             out.print(FormatUtil.getString("am.webclient.managedserver.hostname.alert"));
/*  192 */             out.write("\");\n                document.forms[1].january.focus();\n                return false;\n          }\n          \n\tif(document.forms[1].february.value=='')\n\t{\n            alert(\"");
/*  193 */             out.print(FormatUtil.getString("am.webclient.ipconfig.jsalert.emptyip.text"));
/*  194 */             out.write("\");\n                document.forms[1].february.focus();\n                return false;\n          }\n          var s1=document.forms[1].february.value;\n          var s2 = s1.split(\":\");\n          if(s2.length != 2)\n            {\n           \n\t\t\n                if(!isIpAddress(document.forms[1].february.value))\n                {\n          \n            \n                    alert(\"");
/*  195 */             out.print(FormatUtil.getString("am.webclient.ipconfig.jsalert.validip.text"));
/*  196 */             out.write("\");\n                    document.AMActionForm.february.focus();\n                    return false;\n            \n                }\n          \n             }\n             else\n             {\n             \n                if(!isIpAddress(s2[1]))\n                {\n          \n            \n                    alert(\"");
/*  197 */             out.print(FormatUtil.getString("am.webclient.ipconfig.jsalert.validip.text"));
/*  198 */             out.write(" \");\n                    document.forms[1].february.focus();\n                    return false;\n            \n                }\n             \n             }\n             \n             \n          \n          if(document.forms[1].march.value=='')\n\t{\n            alert(\"");
/*  199 */             out.print(FormatUtil.getString("am.webclient.ipconfig.jsalert.emptynetwork.text"));
/*  200 */             out.write("\");\n                document.forms[1].march.focus();\n                return false;\n          }\n           if (!isIpAddress(document.forms[1].march.value))\n          {\n                alert(\"");
/*  201 */             out.print(FormatUtil.getString("am.webclient.ipconfig.jsalert.validnetwork.text"));
/*  202 */             out.write(" \");\n                document.forms[1].march.focus();\n                return false;\n            \n          }\n          \n          if(document.forms[1].april.value=='')\n\t{\n            alert(\"");
/*  203 */             out.print(FormatUtil.getString("am.webclient.ipconfig.jsalert.emptydns.text"));
/*  204 */             out.write("\");\n                document.forms[1].april.focus();\n                return false;\n          }\n            /*if (!isIpAddress(document.forms[1].april.value))\n          {\n                alert(\"");
/*  205 */             out.print(FormatUtil.getString("am.webclient.ipconfig.jsalert.validdns.text"));
/*  206 */             out.write(" \");\n                document.forms[1].april.focus();\n                return false;\n            \n          }*/\n          if(document.forms[1].may.value=='')\n\t{\n            alert(\"");
/*  207 */             out.print(FormatUtil.getString("am.webclient.ipconfig.jsalert.emptygate.text"));
/*  208 */             out.write("\");\n                document.forms[1].may.focus();\n                return false;\n          }\n         \n         \n         \n          if (!isIpAddress(document.forms[1].may.value))\n          {\n                alert(\"");
/*  209 */             out.print(FormatUtil.getString("am.webclient.ipconfig.jsalert.validgate.text"));
/*  210 */             out.write("\");\n                document.forms[1].may.focus();\n                return false;\n            \n          }\n         \n         formSubmitAction();\n\t");
/*  211 */             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  212 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  216 */         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  217 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */         }
/*      */         else {
/*  220 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  221 */           out.write("\n\t\n}\n</script>\n\n\n\n\n\n");
/*      */           
/*  223 */           org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(org.apache.struts.taglib.tiles.InsertTag.class);
/*  224 */           _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  225 */           _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */           
/*  227 */           _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/*  228 */           int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  229 */           if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */             for (;;) {
/*  231 */               out.write(10);
/*  232 */               if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                 return;
/*  234 */               out.write(10);
/*  235 */               if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                 return;
/*  237 */               out.write(10);
/*      */               
/*  239 */               PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  240 */               _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/*  241 */               _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */               
/*  243 */               _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */               
/*  245 */               _jspx_th_tiles_005fput_005f2.setType("string");
/*  246 */               int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/*  247 */               if (_jspx_eval_tiles_005fput_005f2 != 0) {
/*  248 */                 if (_jspx_eval_tiles_005fput_005f2 != 1) {
/*  249 */                   out = _jspx_page_context.pushBody();
/*  250 */                   _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  251 */                   _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  254 */                   out.write(10);
/*  255 */                   out.write(32);
/*  256 */                   out.write(32);
/*  257 */                   out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                   
/*      */ 
/*  260 */                   String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                   
/*  262 */                   out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/*  263 */                   out.print(FormatUtil.getString("wizard.disabled"));
/*  264 */                   out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/*  265 */                   out.print(FormatUtil.getString("am.webclient.admin.heading"));
/*  266 */                   out.write("</td>\n  </tr>\n  \n ");
/*      */                   
/*  268 */                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                   {
/*      */ 
/*  271 */                     out.write("  \n  <tr>\n\n  ");
/*      */                     
/*  273 */                     if (request.getParameter("wiz") != null)
/*      */                     {
/*  275 */                       out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/*  276 */                       out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/*  277 */                       out.write("\" class='disabledlink'>");
/*  278 */                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  279 */                       out.write("</a></td>\n  ");
/*      */                     }
/*      */                     else
/*      */                     {
/*  283 */                       out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                       
/*  285 */                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  286 */                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  287 */                       _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*  288 */                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  289 */                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                         for (;;) {
/*  291 */                           out.write(10);
/*      */                           
/*  293 */                           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  294 */                           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  295 */                           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/*  297 */                           _jspx_th_c_005fwhen_005f0.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/*  298 */                           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  299 */                           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                             for (;;) {
/*  301 */                               out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/*  302 */                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  303 */                               out.write("\n    </a>\n ");
/*  304 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  305 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  309 */                           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  310 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                           }
/*      */                           
/*  313 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  314 */                           out.write(10);
/*  315 */                           out.write(32);
/*      */                           
/*  317 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  318 */                           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  319 */                           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  320 */                           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  321 */                           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                             for (;;) {
/*  323 */                               out.write(10);
/*  324 */                               out.write(9);
/*  325 */                               out.write(32);
/*  326 */                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  327 */                               out.write(10);
/*  328 */                               out.write(32);
/*  329 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  330 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  334 */                           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  335 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                           }
/*      */                           
/*  338 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  339 */                           out.write(10);
/*  340 */                           out.write(32);
/*  341 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  342 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  346 */                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  347 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                       }
/*      */                       
/*  350 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  351 */                       out.write("\n    </td>\n\t");
/*      */                     }
/*  353 */                     out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                     
/*  355 */                     if (request.getParameter("wiz") != null)
/*      */                     {
/*  357 */                       out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/*  358 */                       out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/*  359 */                       out.write("\" class='disabledlink'>");
/*  360 */                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  361 */                       out.write("</a></td>\n   ");
/*      */                     }
/*      */                     else
/*      */                     {
/*  365 */                       out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                       
/*  367 */                       ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  368 */                       _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  369 */                       _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*  370 */                       int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  371 */                       if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                         for (;;) {
/*  373 */                           out.write(10);
/*      */                           
/*  375 */                           WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  376 */                           _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  377 */                           _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                           
/*  379 */                           _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/*  380 */                           int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  381 */                           if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                             for (;;) {
/*  383 */                               out.write("\n   ");
/*  384 */                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  385 */                               out.write(10);
/*  386 */                               out.write(32);
/*  387 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  388 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  392 */                           if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  393 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                           }
/*      */                           
/*  396 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  397 */                           out.write(10);
/*  398 */                           out.write(32);
/*      */                           
/*  400 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  401 */                           _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  402 */                           _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  403 */                           int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  404 */                           if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                             for (;;) {
/*  406 */                               out.write(10);
/*  407 */                               String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/*  408 */                               out.write("\n\t \n <a href=\"");
/*  409 */                               out.print(link);
/*  410 */                               out.write("\" class=\"new-left-links\">\n               ");
/*  411 */                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  412 */                               out.write("\n    </a>    \n ");
/*  413 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  414 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  418 */                           if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  419 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                           }
/*      */                           
/*  422 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  423 */                           out.write(10);
/*  424 */                           out.write(32);
/*  425 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  426 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  430 */                       if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  431 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                       }
/*      */                       
/*  434 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  435 */                       out.write("\n</td>\n");
/*      */                     }
/*  437 */                     out.write("\n</tr>\n\n ");
/*      */                   }
/*      */                   
/*      */ 
/*  441 */                   out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/*  443 */                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  444 */                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  445 */                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*  446 */                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  447 */                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                     for (;;) {
/*  449 */                       out.write(10);
/*      */                       
/*  451 */                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  452 */                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  453 */                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                       
/*  455 */                       _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/*  456 */                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  457 */                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                         for (;;) {
/*  459 */                           out.write("\n    \n       ");
/*  460 */                           out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/*  461 */                           out.write(10);
/*  462 */                           out.write(32);
/*  463 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  464 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  468 */                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  469 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                       }
/*      */                       
/*  472 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  473 */                       out.write(10);
/*  474 */                       out.write(32);
/*      */                       
/*  476 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  477 */                       _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  478 */                       _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  479 */                       int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  480 */                       if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                         for (;;) {
/*  482 */                           out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/*  483 */                           out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/*  484 */                           out.write("\n    </a>\n ");
/*  485 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  486 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  490 */                       if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  491 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                       }
/*      */                       
/*  494 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  495 */                       out.write(10);
/*  496 */                       out.write(32);
/*  497 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  498 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  502 */                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  503 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                   }
/*      */                   
/*  506 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  507 */                   out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/*  509 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  510 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  511 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*  512 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  513 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/*  515 */                       out.write(10);
/*      */                       
/*  517 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  518 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  519 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/*  521 */                       _jspx_th_c_005fwhen_005f3.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/*  522 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  523 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/*  525 */                           out.write("\n    \n       ");
/*  526 */                           out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/*  527 */                           out.write(10);
/*  528 */                           out.write(32);
/*  529 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  530 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  534 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  535 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/*  538 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  539 */                       out.write(10);
/*  540 */                       out.write(32);
/*      */                       
/*  542 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  543 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  544 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  545 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  546 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/*  548 */                           out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/*  549 */                           out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/*  550 */                           out.write("\n\t </a>\n ");
/*  551 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  552 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  556 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  557 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/*  560 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  561 */                       out.write(10);
/*  562 */                       out.write(32);
/*  563 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  564 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  568 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  569 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/*  572 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  573 */                   out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                   
/*  575 */                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                   {
/*      */ 
/*  578 */                     out.write(32);
/*  579 */                     out.write(32);
/*  580 */                     out.write(10);
/*      */                     
/*  582 */                     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  583 */                     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  584 */                     _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*  585 */                     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  586 */                     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                       for (;;) {
/*  588 */                         out.write(10);
/*      */                         
/*  590 */                         WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  591 */                         _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  592 */                         _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                         
/*  594 */                         _jspx_th_c_005fwhen_005f4.setTest("${param.method !='showNetworkDiscoveryForm'}");
/*  595 */                         int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  596 */                         if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                           for (;;) {
/*  598 */                             out.write("\n<tr>\n    ");
/*  599 */                             if (!request.isUserInRole("OPERATOR")) {
/*  600 */                               out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/*  601 */                               out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/*  602 */                               out.write("\n    </a>\n        </td>\n     ");
/*      */                             } else {
/*  604 */                               out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/*  605 */                               out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/*  606 */                               out.write("\n\t</a>\n\t </td>\n\t");
/*      */                             }
/*  608 */                             out.write("\n    </tr>\n ");
/*  609 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  610 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  614 */                         if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  615 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                         }
/*      */                         
/*  618 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  619 */                         out.write(10);
/*  620 */                         out.write(32);
/*      */                         
/*  622 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  623 */                         _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  624 */                         _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*  625 */                         int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  626 */                         if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                           for (;;) {
/*  628 */                             out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/*  629 */                             out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/*  630 */                             out.write("\n\t </td>\n ");
/*  631 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  632 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  636 */                         if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  637 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                         }
/*      */                         
/*  640 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  641 */                         out.write(10);
/*  642 */                         out.write(32);
/*  643 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  644 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  648 */                     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  649 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                     }
/*      */                     
/*  652 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  653 */                     out.write("\n \n  ");
/*      */                   }
/*      */                   
/*      */ 
/*  657 */                   out.write("  \n \n ");
/*      */                   
/*  659 */                   if (!usertype.equals("F"))
/*      */                   {
/*  661 */                     out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                     
/*  663 */                     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  664 */                     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  665 */                     _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*  666 */                     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  667 */                     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                       for (;;) {
/*  669 */                         out.write(10);
/*  670 */                         out.write(9);
/*      */                         
/*  672 */                         WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  673 */                         _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  674 */                         _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                         
/*  676 */                         _jspx_th_c_005fwhen_005f5.setTest("${param.method !='maintenanceTaskListView'}");
/*  677 */                         int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  678 */                         if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                           for (;;) {
/*  680 */                             out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/*  681 */                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  682 */                             out.write("</a>\n  \t");
/*  683 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  684 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  688 */                         if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  689 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                         }
/*      */                         
/*  692 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  693 */                         out.write("\n  \t");
/*      */                         
/*  695 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  696 */                         _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  697 */                         _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*  698 */                         int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  699 */                         if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                           for (;;) {
/*  701 */                             out.write("\n \t\t");
/*  702 */                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  703 */                             out.write("\n  \t");
/*  704 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  705 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  709 */                         if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  710 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                         }
/*      */                         
/*  713 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  714 */                         out.write("\n  \t");
/*  715 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  716 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  720 */                     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  721 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                     }
/*      */                     
/*  724 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  725 */                     out.write("\n     </td>\n </tr>   \n \n ");
/*      */                     
/*  727 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                     {
/*      */ 
/*  730 */                       out.write(32);
/*  731 */                       out.write(32);
/*  732 */                       out.write(10);
/*      */                       
/*  734 */                       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  735 */                       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  736 */                       _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                       
/*  738 */                       _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/*  739 */                       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  740 */                       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                         for (;;) {
/*  742 */                           out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                           
/*  744 */                           ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  745 */                           _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  746 */                           _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_c_005fif_005f0);
/*  747 */                           int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  748 */                           if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                             for (;;) {
/*  750 */                               out.write(10);
/*  751 */                               out.write(32);
/*  752 */                               out.write(9);
/*      */                               
/*  754 */                               WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  755 */                               _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/*  756 */                               _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                               
/*  758 */                               _jspx_th_c_005fwhen_005f6.setTest("${param.method !='listTrapListener'}");
/*  759 */                               int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/*  760 */                               if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                                 for (;;) {
/*  762 */                                   out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/*  763 */                                   out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  764 */                                   out.write("</a>\n   \t");
/*  765 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/*  766 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  770 */                               if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/*  771 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                               }
/*      */                               
/*  774 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*  775 */                               out.write("\n   \t");
/*      */                               
/*  777 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  778 */                               _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  779 */                               _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*  780 */                               int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  781 */                               if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                 for (;;) {
/*  783 */                                   out.write("\n  \t\t");
/*  784 */                                   out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  785 */                                   out.write(" \n   \t");
/*  786 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/*  787 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  791 */                               if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/*  792 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                               }
/*      */                               
/*  795 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/*  796 */                               out.write("\n   \t");
/*  797 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  798 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  802 */                           if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  803 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                           }
/*      */                           
/*  806 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  807 */                           out.write("\n      </td>\n  </tr>   \n");
/*  808 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  809 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  813 */                       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  814 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                       }
/*      */                       
/*  817 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  818 */                       out.write(10);
/*  819 */                       out.write(32);
/*      */                     }
/*      */                     
/*      */ 
/*  823 */                     out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/*  825 */                     ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  826 */                     _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/*  827 */                     _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/*  828 */                     int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/*  829 */                     if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                       for (;;) {
/*  831 */                         out.write(10);
/*      */                         
/*  833 */                         WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  834 */                         _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/*  835 */                         _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                         
/*  837 */                         _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/*  838 */                         int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/*  839 */                         if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                           for (;;) {
/*  841 */                             out.write("\n       ");
/*  842 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  843 */                             out.write(10);
/*  844 */                             out.write(32);
/*  845 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/*  846 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  850 */                         if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/*  851 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                         }
/*      */                         
/*  854 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/*  855 */                         out.write(10);
/*  856 */                         out.write(32);
/*      */                         
/*  858 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  859 */                         _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/*  860 */                         _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*  861 */                         int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/*  862 */                         if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                           for (;;) {
/*  864 */                             out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/*  865 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  866 */                             out.write("\n\t </a>\n ");
/*  867 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/*  868 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  872 */                         if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/*  873 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                         }
/*      */                         
/*  876 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/*  877 */                         out.write(10);
/*  878 */                         out.write(32);
/*  879 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/*  880 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  884 */                     if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/*  885 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                     }
/*      */                     
/*  888 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/*  889 */                     out.write("\n    </td>\n</tr> \n");
/*      */                   } else {
/*  891 */                     out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/*  892 */                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  893 */                     out.write("</a>\n     </td>\n </tr>   \n");
/*      */                     
/*  895 */                     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  896 */                     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  897 */                     _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                     
/*  899 */                     _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/*  900 */                     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  901 */                     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                       for (;;) {
/*  903 */                         out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/*  904 */                         out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  905 */                         out.write("</a>\n\t  </td>\n  </tr>   \n");
/*  906 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  907 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  911 */                     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  912 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                     }
/*      */                     
/*  915 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  916 */                     out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/*  917 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  918 */                     out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                   }
/*  920 */                   out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/*  922 */                   ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  923 */                   _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/*  924 */                   _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/*  925 */                   int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/*  926 */                   if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                     for (;;) {
/*  928 */                       out.write(10);
/*      */                       
/*  930 */                       WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  931 */                       _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/*  932 */                       _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                       
/*  934 */                       _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/*  935 */                       int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/*  936 */                       if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                         for (;;) {
/*  938 */                           out.write("\n        ");
/*  939 */                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  940 */                           out.write(10);
/*  941 */                           out.write(32);
/*  942 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/*  943 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  947 */                       if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/*  948 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                       }
/*      */                       
/*  951 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/*  952 */                       out.write(10);
/*  953 */                       out.write(32);
/*      */                       
/*  955 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  956 */                       _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/*  957 */                       _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*  958 */                       int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/*  959 */                       if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                         for (;;) {
/*  961 */                           out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/*  962 */                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  963 */                           out.write("\n\t </a>\n ");
/*  964 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/*  965 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  969 */                       if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/*  970 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                       }
/*      */                       
/*  973 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/*  974 */                       out.write(10);
/*  975 */                       out.write(32);
/*  976 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/*  977 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  981 */                   if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/*  982 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                   }
/*      */                   
/*  985 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/*  986 */                   out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/*  988 */                   ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  989 */                   _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/*  990 */                   _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/*  991 */                   int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/*  992 */                   if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                     for (;;) {
/*  994 */                       out.write(10);
/*      */                       
/*  996 */                       WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  997 */                       _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/*  998 */                       _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                       
/* 1000 */                       _jspx_th_c_005fwhen_005f9.setTest("${param.method!='showMailServerConfiguration'}");
/* 1001 */                       int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1002 */                       if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                         for (;;) {
/* 1004 */                           out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 1005 */                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1006 */                           out.write("\n    </a>    \n ");
/* 1007 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1008 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1012 */                       if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1013 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                       }
/*      */                       
/* 1016 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1017 */                       out.write(10);
/* 1018 */                       out.write(32);
/*      */                       
/* 1020 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1021 */                       _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1022 */                       _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1023 */                       int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1024 */                       if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                         for (;;) {
/* 1026 */                           out.write(10);
/* 1027 */                           out.write(9);
/* 1028 */                           out.write(32);
/* 1029 */                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1030 */                           out.write(10);
/* 1031 */                           out.write(32);
/* 1032 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1033 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1037 */                       if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1038 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                       }
/*      */                       
/* 1041 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1042 */                       out.write(10);
/* 1043 */                       out.write(32);
/* 1044 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1045 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1049 */                   if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1050 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                   }
/*      */                   
/* 1053 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1054 */                   out.write("\n    </td>\n</tr>\n\n\n");
/* 1055 */                   if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 1056 */                     out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 1058 */                     ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1059 */                     _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 1060 */                     _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 1061 */                     int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 1062 */                     if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                       for (;;) {
/* 1064 */                         out.write(10);
/*      */                         
/* 1066 */                         WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1067 */                         _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1068 */                         _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                         
/* 1070 */                         _jspx_th_c_005fwhen_005f10.setTest("${param.method!='SMSServerConfiguration'}");
/* 1071 */                         int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1072 */                         if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                           for (;;) {
/* 1074 */                             out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 1075 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1076 */                             out.write("\n    </a>\n ");
/* 1077 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1078 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1082 */                         if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1083 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                         }
/*      */                         
/* 1086 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1087 */                         out.write(10);
/* 1088 */                         out.write(32);
/*      */                         
/* 1090 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1091 */                         _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 1092 */                         _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 1093 */                         int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 1094 */                         if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                           for (;;) {
/* 1096 */                             out.write("\n         ");
/* 1097 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1098 */                             out.write(10);
/* 1099 */                             out.write(32);
/* 1100 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 1101 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1105 */                         if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 1106 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                         }
/*      */                         
/* 1109 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 1110 */                         out.write(10);
/* 1111 */                         out.write(32);
/* 1112 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 1113 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1117 */                     if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 1118 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                     }
/*      */                     
/* 1121 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 1122 */                     out.write("\n    </td>\n</tr>\n");
/*      */                   }
/* 1124 */                   out.write("\n\n\n ");
/*      */                   
/* 1126 */                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                   {
/*      */ 
/* 1129 */                     out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                     
/* 1131 */                     ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1132 */                     _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 1133 */                     _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 1134 */                     int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 1135 */                     if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                       for (;;) {
/* 1137 */                         out.write(10);
/*      */                         
/* 1139 */                         WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1140 */                         _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 1141 */                         _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                         
/* 1143 */                         _jspx_th_c_005fwhen_005f11.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 1144 */                         int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 1145 */                         if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                           for (;;) {
/* 1147 */                             out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 1148 */                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1149 */                             out.write("\n    </a>\n ");
/* 1150 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 1151 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1155 */                         if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 1156 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                         }
/*      */                         
/* 1159 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 1160 */                         out.write(10);
/* 1161 */                         out.write(32);
/*      */                         
/* 1163 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1164 */                         _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 1165 */                         _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 1166 */                         int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 1167 */                         if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                           for (;;) {
/* 1169 */                             out.write(10);
/* 1170 */                             out.write(9);
/* 1171 */                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1172 */                             out.write(10);
/* 1173 */                             out.write(32);
/* 1174 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 1175 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1179 */                         if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 1180 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                         }
/*      */                         
/* 1183 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 1184 */                         out.write(10);
/* 1185 */                         out.write(32);
/* 1186 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 1187 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1191 */                     if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 1192 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                     }
/*      */                     
/* 1195 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 1196 */                     out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 1198 */                     ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1199 */                     _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 1200 */                     _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 1201 */                     int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 1202 */                     if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                       for (;;) {
/* 1204 */                         out.write(10);
/*      */                         
/* 1206 */                         WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1207 */                         _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 1208 */                         _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                         
/* 1210 */                         _jspx_th_c_005fwhen_005f12.setTest("${uri !='/Upload.do'}");
/* 1211 */                         int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 1212 */                         if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                           for (;;) {
/* 1214 */                             out.write("   \n        ");
/*      */                             
/* 1216 */                             AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1217 */                             _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 1218 */                             _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f12);
/*      */                             
/* 1220 */                             _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                             
/* 1222 */                             _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 1223 */                             int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 1224 */                             if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 1225 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 1226 */                                 out = _jspx_page_context.pushBody();
/* 1227 */                                 _jspx_th_am_005fadminlink_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1228 */                                 _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1231 */                                 out.write("\n           ");
/* 1232 */                                 out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 1233 */                                 out.write("\n            ");
/* 1234 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 1235 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1238 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 1239 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1242 */                             if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 1243 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                             }
/*      */                             
/* 1246 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 1247 */                             out.write(10);
/* 1248 */                             out.write(10);
/* 1249 */                             out.write(32);
/* 1250 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 1251 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1255 */                         if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 1256 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                         }
/*      */                         
/* 1259 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 1260 */                         out.write(10);
/* 1261 */                         out.write(32);
/*      */                         
/* 1263 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1264 */                         _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 1265 */                         _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 1266 */                         int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 1267 */                         if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                           for (;;) {
/* 1269 */                             out.write(10);
/* 1270 */                             out.write(9);
/* 1271 */                             out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 1272 */                             out.write(10);
/* 1273 */                             out.write(32);
/* 1274 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 1275 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1279 */                         if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 1280 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                         }
/*      */                         
/* 1283 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 1284 */                         out.write(10);
/* 1285 */                         out.write(32);
/* 1286 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 1287 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1291 */                     if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 1292 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                     }
/*      */                     
/* 1295 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 1296 */                     out.write("\n    </td>\n</tr>\n \n ");
/*      */                   }
/*      */                   
/*      */ 
/* 1300 */                   out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                   
/* 1302 */                   ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1303 */                   _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 1304 */                   _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 1305 */                   int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 1306 */                   if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                     for (;;) {
/* 1308 */                       out.write(10);
/*      */                       
/* 1310 */                       WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1311 */                       _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 1312 */                       _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                       
/* 1314 */                       _jspx_th_c_005fwhen_005f13.setTest("${uri !='/admin/userconfiguration.do'}");
/* 1315 */                       int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 1316 */                       if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                         for (;;) {
/* 1318 */                           out.write("\n    \n        ");
/*      */                           
/* 1320 */                           AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1321 */                           _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 1322 */                           _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f13);
/*      */                           
/* 1324 */                           _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                           
/* 1326 */                           _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 1327 */                           int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 1328 */                           if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 1329 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 1330 */                               out = _jspx_page_context.pushBody();
/* 1331 */                               _jspx_th_am_005fadminlink_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1332 */                               _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1335 */                               out.write("\n       ");
/* 1336 */                               out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1337 */                               out.write("\n        ");
/* 1338 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 1339 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1342 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 1343 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1346 */                           if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 1347 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                           }
/*      */                           
/* 1350 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 1351 */                           out.write(10);
/* 1352 */                           out.write(10);
/* 1353 */                           out.write(32);
/* 1354 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 1355 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1359 */                       if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 1360 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                       }
/*      */                       
/* 1363 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 1364 */                       out.write(10);
/* 1365 */                       out.write(32);
/*      */                       
/* 1367 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1368 */                       _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 1369 */                       _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 1370 */                       int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 1371 */                       if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                         for (;;) {
/* 1373 */                           out.write(10);
/* 1374 */                           out.write(9);
/* 1375 */                           out.write(32);
/* 1376 */                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1377 */                           out.write(10);
/* 1378 */                           out.write(32);
/* 1379 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 1380 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1384 */                       if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 1385 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                       }
/*      */                       
/* 1388 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 1389 */                       out.write(10);
/* 1390 */                       out.write(32);
/* 1391 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 1392 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1396 */                   if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 1397 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                   }
/*      */                   
/* 1400 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 1401 */                   out.write("\n    </td>\n</tr>\n   \n\n ");
/* 1402 */                   if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 1403 */                     out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                     
/* 1405 */                     ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1406 */                     _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 1407 */                     _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 1408 */                     int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 1409 */                     if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                       for (;;) {
/* 1411 */                         out.write("\n   ");
/*      */                         
/* 1413 */                         WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1414 */                         _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 1415 */                         _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                         
/* 1417 */                         _jspx_th_c_005fwhen_005f14.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 1418 */                         int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 1419 */                         if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                           for (;;) {
/* 1421 */                             out.write("\n    ");
/*      */                             
/* 1423 */                             AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1424 */                             _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 1425 */                             _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f14);
/*      */                             
/* 1427 */                             _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                             
/* 1429 */                             _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 1430 */                             int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 1431 */                             if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 1432 */                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 1433 */                                 out = _jspx_page_context.pushBody();
/* 1434 */                                 _jspx_th_am_005fadminlink_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1435 */                                 _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1438 */                                 out.write(10);
/* 1439 */                                 out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 1440 */                                 out.write("\n    ");
/* 1441 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 1442 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1445 */                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 1446 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1449 */                             if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 1450 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                             }
/*      */                             
/* 1453 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 1454 */                             out.write("\n   ");
/* 1455 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 1456 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1460 */                         if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 1461 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                         }
/*      */                         
/* 1464 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 1465 */                         out.write("\n   ");
/*      */                         
/* 1467 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1468 */                         _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 1469 */                         _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 1470 */                         int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 1471 */                         if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                           for (;;) {
/* 1473 */                             out.write(10);
/* 1474 */                             out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 1475 */                             out.write("\n   ");
/* 1476 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 1477 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1481 */                         if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 1482 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                         }
/*      */                         
/* 1485 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 1486 */                         out.write(10);
/* 1487 */                         out.write(32);
/* 1488 */                         out.write(32);
/* 1489 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 1490 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1494 */                     if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 1495 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                     }
/*      */                     
/* 1498 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 1499 */                     out.write("\n </td>\n</tr>\n  ");
/*      */                   }
/* 1501 */                   out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                   
/* 1503 */                   ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1504 */                   _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 1505 */                   _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_tiles_005fput_005f2);
/* 1506 */                   int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 1507 */                   if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                     for (;;) {
/* 1509 */                       out.write("\n   ");
/*      */                       
/* 1511 */                       WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1512 */                       _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 1513 */                       _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                       
/* 1515 */                       _jspx_th_c_005fwhen_005f15.setTest("${param.method!='showDataCleanUp'}");
/* 1516 */                       int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 1517 */                       if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                         for (;;) {
/* 1519 */                           out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 1520 */                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1521 */                           out.write("\n    </a>\n   ");
/* 1522 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 1523 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1527 */                       if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 1528 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                       }
/*      */                       
/* 1531 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 1532 */                       out.write("\n   ");
/*      */                       
/* 1534 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1535 */                       _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 1536 */                       _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 1537 */                       int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 1538 */                       if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                         for (;;) {
/* 1540 */                           out.write(10);
/* 1541 */                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1542 */                           out.write("\n   ");
/* 1543 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 1544 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1548 */                       if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 1549 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                       }
/*      */                       
/* 1552 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 1553 */                       out.write(10);
/* 1554 */                       out.write(32);
/* 1555 */                       out.write(32);
/* 1556 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 1557 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1561 */                   if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 1562 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                   }
/*      */                   
/* 1565 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 1566 */                   out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 1567 */                   out.write("\n  <br>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr> \n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 1568 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 1569 */                   out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 1570 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                     return;
/* 1572 */                   out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n  </tr>\n  <tr> \n    <td colspan=\"2\" class=\"quicknote\">");
/* 1573 */                   out.print(FormatUtil.getString("am.webclient.dbretention.quicknote.text"));
/* 1574 */                   out.write(".</td>\n  </tr>\n</table>\n");
/* 1575 */                   int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 1576 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 1579 */                 if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 1580 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 1583 */               if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 1584 */                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */               }
/*      */               
/* 1587 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 1588 */               out.write("      \n");
/*      */               
/* 1590 */               PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 1591 */               _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 1592 */               _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */               
/* 1594 */               _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */               
/* 1596 */               _jspx_th_tiles_005fput_005f3.setType("string");
/* 1597 */               int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 1598 */               if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 1599 */                 if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 1600 */                   out = _jspx_page_context.pushBody();
/* 1601 */                   _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1602 */                   _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 1605 */                   out.write("\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t  <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 1606 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 1607 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 1608 */                   out.print(FormatUtil.getString("am.webclient.systemsettings.heading.text"));
/* 1609 */                   out.write("</span></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"/images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"/images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n");
/* 1610 */                   if (request.getAttribute("datemessage") != null) {
/* 1611 */                     out.write("\n <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n    <tr> \n      <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\"> <img src=\"/images/icon_message_success.gif\" width=\"25\" height=\"25\" vspace=\"8\"></td>\n      <td width=\"95%\" class=\"bodytext\"  >");
/* 1612 */                     out.print((String)request.getAttribute("datemessage"));
/* 1613 */                     out.write("  </td>\n    </tr>\n  </table>\n");
/*      */                   }
/* 1615 */                   out.write("\n<table width=\"100%\">\n  <tr> \n    <td width=\"8%\" align=\"center\" valign=\"top\" class=\"Profile\" id=\"iplink\"><a href=\"javascript:showHide('ipconfig','dateconfig')\" class=\"staticlinks\">");
/* 1616 */                   out.print(FormatUtil.getString("am.webclient.systemsettings.ipsettings.text"));
/* 1617 */                   out.write("</a></td>\n    <td width=\"8%\" align=\"center\" valign=\"top\" Class=\"permissions\" id=\"datelink\"><a href=\"javascript:showHide('dateconfig','ipconfig')\" class=\"staticlinks\">");
/* 1618 */                   out.print(FormatUtil.getString("am.webclient.systemsettings.datesettings.text"));
/* 1619 */                   out.write("</a></td>\n    <td id=\"permlink\" width=\"86%\">&nbsp;</td>\n  </tr>\n</table>\n<div id=\"ipconfig\" style=\"display:block\">\n\n");
/*      */                   
/* 1621 */                   FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 1622 */                   _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 1623 */                   _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 1625 */                   _jspx_th_html_005fform_005f0.setAction("/changeip.do");
/*      */                   
/* 1627 */                   _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 1628 */                   int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 1629 */                   if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                     for (;;) {
/* 1631 */                       out.write("\n\n\n\n");
/* 1632 */                       if ((request.getParameter("showDiv") != null) && (request.getParameter("showDiv").equals("true"))) {
/* 1633 */                         out.write("\n\n                   \n\t<input type=\"hidden\" name=\"method\" value=\"ChangeIPConfiguration\" >\n\t<input type=\"hidden\" name=\"hostname\" value=\"");
/* 1634 */                         out.print((String)request.getAttribute("hostname"));
/* 1635 */                         out.write("\" >\n\t<input type=\"hidden\" name=\"ipaddress\" value=\"");
/* 1636 */                         out.print((String)request.getAttribute("ipaddress"));
/* 1637 */                         out.write("\" >\n\t<input type=\"hidden\" name=\"netmask\" value=\"");
/* 1638 */                         out.print((String)request.getAttribute("network"));
/* 1639 */                         out.write("\" >\n\t<input type=\"hidden\" name=\"dnsserver\" value=\"");
/* 1640 */                         out.print((String)request.getAttribute("dnsserver"));
/* 1641 */                         out.write("\" >\n\t<input type=\"hidden\" name=\"gateway\" value=\"");
/* 1642 */                         out.print((String)request.getAttribute("gateway"));
/* 1643 */                         out.write("\" >\n\t<input type=\"hidden\" name=\"interface\" value=\"");
/* 1644 */                         out.print((String)request.getAttribute("interface"));
/* 1645 */                         out.write("\" >\n\t\n\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\" align=\"center\" >\n        <tr>\n<td width='100%' >\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" class='lrtbdarkborder'>  \n     <tr><td  width=\"100%\" class=\"tableheading\"  colspan='2'>&nbsp;\n       ");
/* 1646 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.heading.confirmation.text"));
/* 1647 */                         out.write(" </td>\n    </tr>\n    <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n   <tr>\n      <td class=\"bodytext\" width=\"35%\" colspan='2'>&nbsp;");
/* 1648 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.interface.confirmation.text", new String[] { (String)request.getAttribute("interface") }));
/* 1649 */                         out.write(" </td>\n      \n     \n      </td>\n    </tr>\n    <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n     <tr>\n      <td class=\"bodytext\" width=\"35%\" colspan='2'>&nbsp;");
/* 1650 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.host.confirmation.text", new String[] { (String)request.getAttribute("hostname") }));
/* 1651 */                         out.write(" </td>\n      \n     \n      </td>\n    </tr>\n    <tr>\n    \n    ");
/*      */                         
/* 1653 */                         String sipadd = (String)request.getAttribute("ipaddress");
/* 1654 */                         String[] temp = sipadd.split(":");
/* 1655 */                         if (temp.length != 2)
/*      */                         {
/* 1657 */                           sipadd = sipadd;
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/* 1662 */                           sipadd = temp[1];
/*      */                         }
/*      */                         
/*      */ 
/* 1666 */                         out.write("\n    <td colspan='2'>&nbsp;</td></tr>\n  \n     <tr>\n      <td class=\"bodytext\" width=\"35%\" colspan='2'>&nbsp;");
/* 1667 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.ip.confirmation.text", new String[] { sipadd }));
/* 1668 */                         out.write(" </td>\n      \n     \n      </td>\n    </tr>\n    <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n    <tr>\n      <td class=\"bodytext\" width=\"35%\" colspan='2'>&nbsp; ");
/* 1669 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.netmask.confirmation.text", new String[] { (String)request.getAttribute("network") }));
/* 1670 */                         out.write("</td>\n      \n     \n      </td>\n    </tr>\n    <tr>\n     <td colspan='2'>&nbsp;</td></tr>\n   <tr>\n      <td class=\"bodytext\" width=\"35%\" colspan='2'>&nbsp;");
/* 1671 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.dns.confirmation.text", new String[] { (String)request.getAttribute("dnsserver") }));
/* 1672 */                         out.write("</td>\n      \n     \n      </td>\n    </tr>\n    <td colspan='2'>&nbsp;</td></tr>\n   <tr>\n      <td class=\"bodytext\" width=\"35%\" colspan='2'>&nbsp;");
/* 1673 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.gate.confirmation.text", new String[] { (String)request.getAttribute("gateway") }));
/* 1674 */                         out.write("</td>\n     \n     \n      </td>\n    </tr>\n     \n  \n    <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n    <tr>\n      <td width=\"25%\" class=\"tablebottom\" >&nbsp;</td>\n      <td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 1675 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.confirm.text"));
/* 1676 */                         out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"formSubmitAction();\">\n         &nbsp;&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 1677 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 1678 */                         out.write("\" onClick=\"reDirect();\" class=\"buttons btn_link\"></td>\n       </td></tr></table>\n       </td></tr></table>\n\t\n\t\n\t");
/*      */                       }
/*      */                       else {
/* 1681 */                         out.write(10);
/* 1682 */                         if (request.getAttribute("message") != null) {
/* 1683 */                           java.util.Properties p1 = (java.util.Properties)request.getAttribute("message");
/* 1684 */                           java.util.Properties p2 = (java.util.Properties)request.getAttribute("props");
/* 1685 */                           String host = p2.getProperty("hostname");
/* 1686 */                           String ip = p2.getProperty("ipaddress");
/* 1687 */                           String netmask = p2.getProperty("netmask");
/* 1688 */                           String dns = p2.getProperty("dnsserver");
/* 1689 */                           String gateway = p2.getProperty("gateway");
/* 1690 */                           String intface = p2.getProperty("interface");
/*      */                           
/*      */ 
/* 1693 */                           String s1 = p1.getProperty("hostsuccess");
/* 1694 */                           String s2 = p1.getProperty("ipsuccess");
/* 1695 */                           String s3 = p1.getProperty("netsuccess");
/* 1696 */                           String s4 = p1.getProperty("dnssuccess");
/* 1697 */                           String s5 = p1.getProperty("gatesuccess");
/* 1698 */                           String s6 = p1.getProperty("intersucess");
/*      */                           
/* 1700 */                           out.write("\n                   <table width=\"98%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" class=\"grayfullborder\">\n                   <tr> \n    <td class=\"columnheadingb\" height=\"19\" width=\"20%\"><span class=\"bodytextbold\">");
/* 1701 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 1702 */                           out.write("</span> </td>\n    \n    <td class=\"columnheadingb\" height=\"19\" width=\"11%\"><span class=\"bodytextbold\">");
/* 1703 */                           out.print(FormatUtil.getString("Status"));
/* 1704 */                           out.write("</span></td>\n    <td class=\"columnheadingb\" height=\"19\" width=\"62%\"><span class=\"bodytextbold\">");
/* 1705 */                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 1706 */                           out.write("\n </span></td>\n  </tr>\n  <tr>\n  <td class=\"bodytext\" height=\"18\">");
/* 1707 */                           out.print(intface);
/* 1708 */                           out.write("</td>\n");
/* 1709 */                           if ((s6 != null) && (s6.equals("true")))
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/* 1714 */                             out.write("<td height=\"18\" class=\"bodytext\"> <img src=\"/images/icon_monitor_success.gif\" border=\"0\"  align=\"absmiddle\">  <span class=\"bodytextbold\">");
/* 1715 */                             out.print(FormatUtil.getString("Success"));
/* 1716 */                             out.write("</span> </td>\n<td class=\"bodytext\" height=\"18\">");
/* 1717 */                             out.print(FormatUtil.getString("am.webclient.ipconfig.interface.success.text"));
/* 1718 */                             out.write("</td>\n     ");
/*      */                           } else {
/* 1720 */                             out.write("              \n                 <td height=\"18\" class=\"bodytext\">  <img src=\"/images/icon_monitor_failure.gif\" border=\"0\" align=\"absmiddle\"> <span class=\"bodytextbold\">");
/* 1721 */                             out.print(FormatUtil.getString("Failed"));
/* 1722 */                             out.write("</span> </td>\n                  <td class=\"bodytext\" height=\"18\">");
/* 1723 */                             out.print(s6);
/* 1724 */                             out.write("</td>\n                ");
/*      */                           }
/* 1726 */                           out.write(" \n           </tr>\n  \n  <tr>\n  <td class=\"bodytext\" height=\"18\">");
/* 1727 */                           out.print(host);
/* 1728 */                           out.write("</td>\n");
/* 1729 */                           if ((s1 != null) && (s1.equals("true")))
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/* 1734 */                             out.write("<td height=\"18\" class=\"bodytext\"> <img src=\"/images/icon_monitor_success.gif\" border=\"0\"  align=\"absmiddle\">  <span class=\"bodytextbold\">");
/* 1735 */                             out.print(FormatUtil.getString("Success"));
/* 1736 */                             out.write("</span> </td>\n<td class=\"bodytext\" height=\"18\">");
/* 1737 */                             out.print(FormatUtil.getString("am.webclient.ipconfig.host.success.text"));
/* 1738 */                             out.write("</td>\n     ");
/*      */                           } else {
/* 1740 */                             out.write("              \n                 <td height=\"18\" class=\"bodytext\">  <img src=\"/images/icon_monitor_failure.gif\" border=\"0\"  align=\"absmiddle\"> <span class=\"bodytextbold\">");
/* 1741 */                             out.print(FormatUtil.getString("Failed"));
/* 1742 */                             out.write("</span> </td>\n                  <td class=\"bodytext\" height=\"18\">");
/* 1743 */                             out.print(s1);
/* 1744 */                             out.write("</td>\n                ");
/*      */                           }
/* 1746 */                           out.write(" \n           </tr>   \n  <tr>\n  <td class=\"bodytext\" height=\"18\">");
/* 1747 */                           out.print(ip);
/* 1748 */                           out.write("</td>\n  ");
/* 1749 */                           if ((s2 != null) && (s2.equals("true")))
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/* 1754 */                             out.write("\n       <td height=\"18\" class=\"bodytext\"> <img src=\"/images/icon_monitor_success.gif\" border=\"0\"  align=\"absmiddle\">  <span class=\"bodytextbold\">");
/* 1755 */                             out.print(FormatUtil.getString("Success"));
/* 1756 */                             out.write("</span> </td>\n       <td class=\"bodytext\" height=\"18\">");
/* 1757 */                             out.print(FormatUtil.getString("am.webclient.ipconfig.ip.success.text"));
/* 1758 */                             out.write("</td>\n     ");
/*      */                           } else {
/* 1760 */                             out.write("              \n                  <td height=\"18\" class=\"bodytext\">  <img src=\"/images/icon_monitor_failure.gif\" border=\"0\"  align=\"absmiddle\"> <span class=\"bodytextbold\">");
/* 1761 */                             out.print(FormatUtil.getString("Failed"));
/* 1762 */                             out.write("</span> </td>\n                   <td class=\"bodytext\" height=\"18\">");
/* 1763 */                             out.print(s2);
/* 1764 */                             out.write("</td>\n                ");
/*      */                           }
/* 1766 */                           out.write(" \n                 </tr>   \n  <tr>\n  <td class=\"bodytext\" height=\"18\">");
/* 1767 */                           out.print(netmask);
/* 1768 */                           out.write("</td>\n                ");
/* 1769 */                           if ((s3 != null) && (s3.equals("true")))
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/* 1774 */                             out.write("\n        <td height=\"18\" class=\"bodytext\"> <img src=\"/images/icon_monitor_success.gif\" border=\"0\"  align=\"absmiddle\">  <span class=\"bodytextbold\">");
/* 1775 */                             out.print(FormatUtil.getString("Success"));
/* 1776 */                             out.write("</span> </td>\n        <td class=\"bodytext\" height=\"18\">");
/* 1777 */                             out.print(FormatUtil.getString("am.webclient.ipconfig.net.success.text"));
/* 1778 */                             out.write("</td>\n     ");
/*      */                           } else {
/* 1780 */                             out.write("              \n                  <td height=\"18\" class=\"bodytext\">  <img src=\"/images/icon_monitor_failure.gif\" border=\"0\"  align=\"absmiddle\"> <span class=\"bodytextbold\">");
/* 1781 */                             out.print(FormatUtil.getString("Failed"));
/* 1782 */                             out.write("</span> </td>\n                   <td class=\"bodytext\" height=\"18\">");
/* 1783 */                             out.print(s3);
/* 1784 */                             out.write("</td>\n                ");
/*      */                           }
/* 1786 */                           out.write(" \n                 </tr>   \n  <tr>\n  <td class=\"bodytext\" height=\"18\">");
/* 1787 */                           out.print(dns);
/* 1788 */                           out.write("</td>\n                ");
/* 1789 */                           if ((s4 != null) && (s4.equals("true")))
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/* 1794 */                             out.write("\n        <td height=\"18\" class=\"bodytext\"> <img src=\"/images/icon_monitor_success.gif\" border=\"0\"  align=\"absmiddle\">  <span class=\"bodytextbold\">");
/* 1795 */                             out.print(FormatUtil.getString("Success"));
/* 1796 */                             out.write("</span> </td>\n        <td class=\"bodytext\" height=\"18\">");
/* 1797 */                             out.print(FormatUtil.getString("am.webclient.ipconfig.dns.success.text"));
/* 1798 */                             out.write("</td>\n     ");
/*      */                           } else {
/* 1800 */                             out.write("              \n                  <td height=\"18\" class=\"bodytext\">  <img src=\"/images/icon_monitor_failure.gif\" border=\"0\"  align=\"absmiddle\"> <span class=\"bodytextbold\">");
/* 1801 */                             out.print(FormatUtil.getString("Failed"));
/* 1802 */                             out.write("</span> </td>\n                   <td class=\"bodytext\" height=\"18\">");
/* 1803 */                             out.print(s4);
/* 1804 */                             out.write("</td>\n                ");
/*      */                           }
/* 1806 */                           out.write(" \n                 </tr>   \n  <tr>\n  <td class=\"bodytext\" height=\"18\">");
/* 1807 */                           out.print(gateway);
/* 1808 */                           out.write("</td>\n                ");
/* 1809 */                           if ((s5 != null) && (s5.equals("true")))
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/* 1814 */                             out.write("\n        <td height=\"18\" class=\"bodytext\"> <img src=\"/images/icon_monitor_success.gif\" border=\"0\"  align=\"absmiddle\">  <span class=\"bodytextbold\">");
/* 1815 */                             out.print(FormatUtil.getString("Success"));
/* 1816 */                             out.write("</span> </td>\n        <td class=\"bodytext\" height=\"18\">");
/* 1817 */                             out.print(FormatUtil.getString("am.webclient.ipconfig.gate.success.text"));
/* 1818 */                             out.write("</td>\n     ");
/*      */                           } else {
/* 1820 */                             out.write("              \n                   <td height=\"18\" class=\"bodytext\">  <img src=\"/images/icon_monitor_failure.gif\" border=\"0\"  align=\"absmiddle\"> <span class=\"bodytextbold\">");
/* 1821 */                             out.print(FormatUtil.getString("Failed"));
/* 1822 */                             out.write("</span> </td>\n                   <td class=\"bodytext\" height=\"18\">");
/* 1823 */                             out.print(s5);
/* 1824 */                             out.write("</td>\n                ");
/*      */                           }
/* 1826 */                           out.write(" \n                   \n                   ");
/*      */                         }
/* 1828 */                         out.write("\n\n\t<input type=\"hidden\" name=\"method\" value=\"SaveIPConfiguration\" >\n <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\" align=\"center\" >\n        <tr>\n<td width='100%' >\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" class='lrtbdarkborder'>  \n     <tr><td  width=\"100%\" class=\"tableheading\"  colspan='2'>&nbsp;\n       ");
/* 1829 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.heading.text"));
/* 1830 */                         out.write(" </td>\n    </tr>\n    <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n  <tr>\n      <td class=\"bodytext\" width=\"35%\">&nbsp;");
/* 1831 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.interface.text"));
/* 1832 */                         out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"65%\"> ");
/* 1833 */                         if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1835 */                         out.write("\n\t\n     \n      </td>\n    </tr>\n     <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n     <tr>\n      <td class=\"bodytext\" width=\"35%\">&nbsp;");
/* 1836 */                         out.print(FormatUtil.getString("am.webclient.traplistener.hostname"));
/* 1837 */                         out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"65%\">");
/* 1838 */                         if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1840 */                         out.write("&nbsp;&nbsp;&nbsp;&nbsp;\n     \n      </td>\n    </tr>\n    <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n  \n     <tr>\n      <td class=\"bodytext\" width=\"35%\">&nbsp;");
/* 1841 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.ip.text"));
/* 1842 */                         out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"65%\">");
/* 1843 */                         if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1845 */                         out.write("&nbsp;&nbsp;&nbsp;&nbsp;\n     \n      </td>\n    </tr>\n    <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n    <tr>\n      <td class=\"bodytext\" width=\"35%\">&nbsp;");
/* 1846 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.net.text"));
/* 1847 */                         out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"65%\">");
/* 1848 */                         if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1850 */                         out.write("&nbsp;&nbsp;&nbsp;&nbsp;\n     \n      </td>\n    </tr>\n    <tr>\n     <td colspan='2'>&nbsp;</td></tr>\n   <tr>\n      <td class=\"bodytext\" width=\"35%\">&nbsp;");
/* 1851 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.dns.text"));
/* 1852 */                         out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"65%\">");
/* 1853 */                         if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1855 */                         out.write("&nbsp;&nbsp;&nbsp;&nbsp;\n     \n      </td>\n    </tr>\n    <td colspan='2'>&nbsp;</td></tr>\n   <tr>\n      <td class=\"bodytext\" width=\"35%\">&nbsp;");
/* 1856 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.gate.text"));
/* 1857 */                         out.write("<span class=\"mandatory\">*</span></td>\n      <td width=\"65%\">");
/* 1858 */                         if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1860 */                         out.write("&nbsp;&nbsp;&nbsp;&nbsp;\n     \n      </td>\n    </tr>\n     \n  \n    <tr>\n    <td colspan='2'>&nbsp;</td></tr>\n    <tr>\n      <td width=\"25%\" class=\"tablebottom\" >&nbsp;</td>\n      <td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 1861 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 1862 */                         out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnFormSubmit();\">\n         &nbsp;&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 1863 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 1864 */                         out.write("\" onClick=\"reDirect();\" class=\"buttons btn_link\"></td>\n       </td></tr></table>\n       </td></tr></table>");
/*      */                       }
/* 1866 */                       out.write("\n       ");
/* 1867 */                       int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 1868 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1872 */                   if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 1873 */                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                   }
/*      */                   
/* 1876 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 1877 */                   out.write("\n       </div>\n\n<div id=\"dateconfig\" style=\"display:none\">\n\n");
/*      */                   
/* 1879 */                   FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 1880 */                   _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 1881 */                   _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 1883 */                   _jspx_th_html_005fform_005f1.setAction("/changeip.do");
/*      */                   
/* 1885 */                   _jspx_th_html_005fform_005f1.setStyle("display:inline");
/* 1886 */                   int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 1887 */                   if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                     for (;;) {
/* 1889 */                       out.write(10);
/* 1890 */                       if ((request.getParameter("type") != null) && (request.getParameter("type").equals("dates"))) {
/* 1891 */                         out.write("\n<input type=\"hidden\" name=\"method\" value=\"ConfirmDateConfiguration\" >\n<input type=\"hidden\" name=\"dates\" value=\"");
/* 1892 */                         out.print((String)request.getAttribute("dates"));
/* 1893 */                         out.write("\" >\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\" align=\"center\" >\n        <tr>\n<td width='100%' >\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" class='lrtbdarkborder'>  \n     <tr><td  width=\"100%\" class=\"tableheading\"  colspan='2'>&nbsp;\n       ");
/* 1894 */                         out.print(FormatUtil.getString("am.webclient.systemsettings.datesettings.confirmation.heading"));
/* 1895 */                         out.write(" </td>\n    </tr>\n    <tr>\n      <td class=\"bodytext\" width=\"35%\" colspan='2'>&nbsp;</td>\n      \n     \n      </td>\n    </tr>\n  \n     <tr>\n      <td class=\"bodytext\" width=\"35%\" colspan='2'>&nbsp;");
/* 1896 */                         out.print(FormatUtil.getString("am.webclient.systemsettings.datesettings.confirmation.message", new String[] { (String)request.getAttribute("dates") }));
/* 1897 */                         out.write("&nbsp;");
/* 1898 */                         out.print(FormatUtil.getString("am.webclient.systemsettings.datesettings.confirmation.serverstartup.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/* 1899 */                         out.write("  </td>\n      \n     \n      </td>\n    </tr>\n     <tr>\n      <td class=\"bodytext\" width=\"35%\" colspan='2'>&nbsp;</td>\n      \n     \n      </td>\n    </tr>\n     \n    <tr>\n      <td width=\"25%\" class=\"tablebottom\" >&nbsp;</td>\n      <td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 1900 */                         out.print(FormatUtil.getString("am.webclient.ipconfig.confirm.text"));
/* 1901 */                         out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"dateSubmitAction();\">\n         &nbsp;&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 1902 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 1903 */                         out.write("\" onClick=\"reDirect();\" class=\"buttons btn_link\"></td>\n       </td></tr></table>\n       </td></tr></table>\n");
/*      */                       }
/*      */                       else {
/* 1906 */                         out.write("\n<input type=\"hidden\" name=\"method\" value=\"SaveDateConfiguration\" >\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"lrtbdarkborder\">\n<tr>\n<td colspan=\"2\" class=\"tableheadingbborder\">\n");
/* 1907 */                         out.print(FormatUtil.getString("am.webclient.systemsettings.datesettings.heading.text"));
/* 1908 */                         out.write("\n</td>\n</tr>\n\n              <tr class=\"whitegrayborder\">\n\n<td height=\"38\" colspan='2'>&nbsp;");
/* 1909 */                         out.print(FormatUtil.getString("am.webclient.systemsettings.datesettings.setdate.text"));
/* 1910 */                         out.write(" &nbsp;");
/* 1911 */                         if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                           return;
/* 1913 */                         out.write("\n                  <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/* 1914 */                         out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 1915 */                         out.write("\"></a>\n                  <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT></td>\n</tr>\n<tr>\n      <td width=\"25%\" class=\"tablebottom\" >&nbsp;</td>\n      <td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 1916 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 1917 */                         out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnDateSubmit();\">\n         &nbsp;&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 1918 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 1919 */                         out.write("\" onClick=\"reDirect();\" class=\"buttons btn_link\"></td>\n       </td></tr>\n</table>\n");
/*      */                       }
/* 1921 */                       out.write(10);
/* 1922 */                       int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 1923 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1927 */                   if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 1928 */                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                   }
/*      */                   
/* 1931 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 1932 */                   out.write("\n</div>\n");
/* 1933 */                   int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 1934 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 1937 */                 if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 1938 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 1941 */               if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 1942 */                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */               }
/*      */               
/* 1945 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 1946 */               out.write(32);
/* 1947 */               out.write(10);
/* 1948 */               if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                 return;
/* 1950 */               out.write(10);
/* 1951 */               int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 1952 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1956 */           if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 1957 */             this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */           }
/*      */           else {
/* 1960 */             this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 1961 */             out.write(10);
/* 1962 */             out.write(10);
/* 1963 */             out.write(10);
/*      */           }
/* 1965 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1966 */         out = _jspx_out;
/* 1967 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1968 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1969 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1972 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1978 */     PageContext pageContext = _jspx_page_context;
/* 1979 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1981 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1982 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1983 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1985 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1987 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1988 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1989 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1990 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1991 */       return true;
/*      */     }
/* 1993 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1994 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1999 */     PageContext pageContext = _jspx_page_context;
/* 2000 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2002 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2003 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2004 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2006 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2007 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2008 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2010 */         out.write("\nalertUser();\n");
/* 2011 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2012 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2016 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2017 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2018 */       return true;
/*      */     }
/* 2020 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2026 */     PageContext pageContext = _jspx_page_context;
/* 2027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2029 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2030 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2031 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 2033 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 2034 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2035 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 2037 */         out.write("\nalertUser();\n");
/* 2038 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2039 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2043 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2044 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2045 */       return true;
/*      */     }
/* 2047 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2053 */     PageContext pageContext = _jspx_page_context;
/* 2054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2056 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2057 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2058 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2060 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 2062 */     _jspx_th_tiles_005fput_005f0.setValue("IP Configuration");
/* 2063 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2064 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2065 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2066 */       return true;
/*      */     }
/* 2068 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2074 */     PageContext pageContext = _jspx_page_context;
/* 2075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2077 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2078 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2079 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2081 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2083 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 2084 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2085 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2086 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2087 */       return true;
/*      */     }
/* 2089 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2095 */     PageContext pageContext = _jspx_page_context;
/* 2096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2098 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2099 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2100 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 2102 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*      */     
/* 2104 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 2105 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2106 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2107 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2108 */       return true;
/*      */     }
/* 2110 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2111 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2116 */     PageContext pageContext = _jspx_page_context;
/* 2117 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2119 */     org.apache.struts.taglib.html.SelectTag _jspx_th_html_005fselect_005f0 = (org.apache.struts.taglib.html.SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(org.apache.struts.taglib.html.SelectTag.class);
/* 2120 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2121 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2123 */     _jspx_th_html_005fselect_005f0.setProperty("july");
/*      */     
/* 2125 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */     
/* 2127 */     _jspx_th_html_005fselect_005f0.setStyle("width:10%");
/*      */     
/* 2129 */     _jspx_th_html_005fselect_005f0.setOnchange("javascript:getEnable(this.form);");
/* 2130 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2131 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2132 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2133 */         out = _jspx_page_context.pushBody();
/* 2134 */         _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2135 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2138 */         out.write("\n\t\n              ");
/* 2139 */         if (_jspx_meth_html_005foption_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 2140 */           return true;
/* 2141 */         out.write("\n              ");
/* 2142 */         if (_jspx_meth_html_005foption_005f1(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 2143 */           return true;
/* 2144 */         out.write("\n              ");
/* 2145 */         if (_jspx_meth_html_005foption_005f2(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 2146 */           return true;
/* 2147 */         out.write("\n               ");
/* 2148 */         if (_jspx_meth_html_005foption_005f3(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 2149 */           return true;
/* 2150 */         out.write("\n               ");
/* 2151 */         if (_jspx_meth_html_005foption_005f4(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 2152 */           return true;
/* 2153 */         out.write("\n               ");
/* 2154 */         if (_jspx_meth_html_005foption_005f5(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 2155 */           return true;
/* 2156 */         out.write("\n               ");
/* 2157 */         if (_jspx_meth_html_005foption_005f6(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 2158 */           return true;
/* 2159 */         out.write("\n               \n\t ");
/* 2160 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 2161 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2164 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2165 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2168 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 2169 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 2170 */       return true;
/*      */     }
/* 2172 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 2173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2178 */     PageContext pageContext = _jspx_page_context;
/* 2179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2181 */     OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2182 */     _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2183 */     _jspx_th_html_005foption_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 2185 */     _jspx_th_html_005foption_005f0.setValue("eth1");
/* 2186 */     int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2187 */     if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2188 */       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2189 */         out = _jspx_page_context.pushBody();
/* 2190 */         _jspx_th_html_005foption_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2191 */         _jspx_th_html_005foption_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2194 */         out.write("eth1");
/* 2195 */         int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2196 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2199 */       if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2200 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2203 */     if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2204 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2205 */       return true;
/*      */     }
/* 2207 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f1(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2213 */     PageContext pageContext = _jspx_page_context;
/* 2214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2216 */     OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2217 */     _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2218 */     _jspx_th_html_005foption_005f1.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 2220 */     _jspx_th_html_005foption_005f1.setValue("eth2");
/* 2221 */     int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2222 */     if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2223 */       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2224 */         out = _jspx_page_context.pushBody();
/* 2225 */         _jspx_th_html_005foption_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2226 */         _jspx_th_html_005foption_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2229 */         out.write("eth2");
/* 2230 */         int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2231 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2234 */       if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2235 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2238 */     if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2239 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2240 */       return true;
/*      */     }
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2243 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f2(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2248 */     PageContext pageContext = _jspx_page_context;
/* 2249 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2251 */     OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2252 */     _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2253 */     _jspx_th_html_005foption_005f2.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 2255 */     _jspx_th_html_005foption_005f2.setValue("eth3");
/* 2256 */     int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2257 */     if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2258 */       if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2259 */         out = _jspx_page_context.pushBody();
/* 2260 */         _jspx_th_html_005foption_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2261 */         _jspx_th_html_005foption_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2264 */         out.write("eth3");
/* 2265 */         int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2266 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2269 */       if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2270 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2273 */     if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2274 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2275 */       return true;
/*      */     }
/* 2277 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2278 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f3(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2283 */     PageContext pageContext = _jspx_page_context;
/* 2284 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2286 */     OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2287 */     _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2288 */     _jspx_th_html_005foption_005f3.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 2290 */     _jspx_th_html_005foption_005f3.setValue("eth4");
/* 2291 */     int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2292 */     if (_jspx_eval_html_005foption_005f3 != 0) {
/* 2293 */       if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2294 */         out = _jspx_page_context.pushBody();
/* 2295 */         _jspx_th_html_005foption_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2296 */         _jspx_th_html_005foption_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2299 */         out.write("eth4");
/* 2300 */         int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 2301 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2304 */       if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2305 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2308 */     if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2309 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2310 */       return true;
/*      */     }
/* 2312 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f4(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2318 */     PageContext pageContext = _jspx_page_context;
/* 2319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2321 */     OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2322 */     _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2323 */     _jspx_th_html_005foption_005f4.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 2325 */     _jspx_th_html_005foption_005f4.setValue("eth5");
/* 2326 */     int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2327 */     if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2328 */       if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2329 */         out = _jspx_page_context.pushBody();
/* 2330 */         _jspx_th_html_005foption_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2331 */         _jspx_th_html_005foption_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2334 */         out.write("eth5");
/* 2335 */         int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2336 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2339 */       if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2340 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2343 */     if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2344 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2345 */       return true;
/*      */     }
/* 2347 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2348 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f5(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2353 */     PageContext pageContext = _jspx_page_context;
/* 2354 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2356 */     OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2357 */     _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2358 */     _jspx_th_html_005foption_005f5.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 2360 */     _jspx_th_html_005foption_005f5.setValue("eth6");
/* 2361 */     int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2362 */     if (_jspx_eval_html_005foption_005f5 != 0) {
/* 2363 */       if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2364 */         out = _jspx_page_context.pushBody();
/* 2365 */         _jspx_th_html_005foption_005f5.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2366 */         _jspx_th_html_005foption_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2369 */         out.write("eth6");
/* 2370 */         int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 2371 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2374 */       if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2375 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2378 */     if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2379 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 2380 */       return true;
/*      */     }
/* 2382 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 2383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foption_005f6(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2388 */     PageContext pageContext = _jspx_page_context;
/* 2389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2391 */     OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2392 */     _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2393 */     _jspx_th_html_005foption_005f6.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 2395 */     _jspx_th_html_005foption_005f6.setValue("eth7");
/* 2396 */     int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2397 */     if (_jspx_eval_html_005foption_005f6 != 0) {
/* 2398 */       if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2399 */         out = _jspx_page_context.pushBody();
/* 2400 */         _jspx_th_html_005foption_005f6.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2401 */         _jspx_th_html_005foption_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2404 */         out.write("eth7");
/* 2405 */         int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 2406 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2409 */       if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2410 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2413 */     if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2414 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 2415 */       return true;
/*      */     }
/* 2417 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 2418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2423 */     PageContext pageContext = _jspx_page_context;
/* 2424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2426 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2427 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 2428 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2430 */     _jspx_th_html_005ftext_005f0.setProperty("january");
/*      */     
/* 2432 */     _jspx_th_html_005ftext_005f0.setSize("15");
/*      */     
/* 2434 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/* 2436 */     _jspx_th_html_005ftext_005f0.setMaxlength("100");
/*      */     
/* 2438 */     _jspx_th_html_005ftext_005f0.setStyleId("host");
/* 2439 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 2440 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 2441 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2442 */       return true;
/*      */     }
/* 2444 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2450 */     PageContext pageContext = _jspx_page_context;
/* 2451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2453 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2454 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 2455 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2457 */     _jspx_th_html_005ftext_005f1.setProperty("february");
/*      */     
/* 2459 */     _jspx_th_html_005ftext_005f1.setSize("15");
/*      */     
/* 2461 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 2463 */     _jspx_th_html_005ftext_005f1.setMaxlength("25");
/*      */     
/* 2465 */     _jspx_th_html_005ftext_005f1.setStyleId("ip");
/* 2466 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 2467 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 2468 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2469 */       return true;
/*      */     }
/* 2471 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2472 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2477 */     PageContext pageContext = _jspx_page_context;
/* 2478 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2480 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2481 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 2482 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2484 */     _jspx_th_html_005ftext_005f2.setProperty("march");
/*      */     
/* 2486 */     _jspx_th_html_005ftext_005f2.setSize("15");
/*      */     
/* 2488 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 2490 */     _jspx_th_html_005ftext_005f2.setMaxlength("25");
/*      */     
/* 2492 */     _jspx_th_html_005ftext_005f2.setStyleId("net");
/* 2493 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 2494 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 2495 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2496 */       return true;
/*      */     }
/* 2498 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2504 */     PageContext pageContext = _jspx_page_context;
/* 2505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2507 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2508 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 2509 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2511 */     _jspx_th_html_005ftext_005f3.setProperty("april");
/*      */     
/* 2513 */     _jspx_th_html_005ftext_005f3.setSize("15");
/*      */     
/* 2515 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 2517 */     _jspx_th_html_005ftext_005f3.setMaxlength("100");
/*      */     
/* 2519 */     _jspx_th_html_005ftext_005f3.setStyleId("dns");
/* 2520 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 2521 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 2522 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2523 */       return true;
/*      */     }
/* 2525 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2531 */     PageContext pageContext = _jspx_page_context;
/* 2532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2534 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2535 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 2536 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2538 */     _jspx_th_html_005ftext_005f4.setProperty("may");
/*      */     
/* 2540 */     _jspx_th_html_005ftext_005f4.setSize("15");
/*      */     
/* 2542 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */     
/* 2544 */     _jspx_th_html_005ftext_005f4.setMaxlength("25");
/*      */     
/* 2546 */     _jspx_th_html_005ftext_005f4.setStyleId("gate");
/* 2547 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 2548 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 2549 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2550 */       return true;
/*      */     }
/* 2552 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2553 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2558 */     PageContext pageContext = _jspx_page_context;
/* 2559 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2561 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fnobody.get(TextTag.class);
/* 2562 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 2563 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 2565 */     _jspx_th_html_005ftext_005f5.setSize("17");
/*      */     
/* 2567 */     _jspx_th_html_005ftext_005f5.setProperty("monday");
/*      */     
/* 2569 */     _jspx_th_html_005ftext_005f5.setStyleId("start");
/*      */     
/* 2571 */     _jspx_th_html_005ftext_005f5.setReadonly(true);
/* 2572 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 2573 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 2574 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 2575 */       return true;
/*      */     }
/* 2577 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 2578 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2583 */     PageContext pageContext = _jspx_page_context;
/* 2584 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2586 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2587 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2588 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2590 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 2592 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 2593 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2594 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 2595 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 2596 */       return true;
/*      */     }
/* 2598 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 2599 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ChangeIPAddress_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */