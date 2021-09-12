/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class AssignCustomFields_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   27 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   33 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*   34 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   56 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   60 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   75 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   79 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   80 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*   81 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*   82 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*   83 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*   84 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/*   86 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*   87 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   88 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   89 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/*   90 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   91 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   92 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   99 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  102 */     JspWriter out = null;
/*  103 */     Object page = this;
/*  104 */     JspWriter _jspx_out = null;
/*  105 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  109 */       response.setContentType("text/html;charset=UTF-8");
/*  110 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  112 */       _jspx_page_context = pageContext;
/*  113 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  114 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  115 */       session = pageContext.getSession();
/*  116 */       out = pageContext.getOut();
/*  117 */       _jspx_out = out;
/*      */       
/*  119 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  120 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  122 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n");
/*  123 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  124 */       out.write(10);
/*      */       
/*  126 */       String resids = request.getParameter("resids");
/*      */       
/*  128 */       MyFields fields = new MyFields();
/*  129 */       request.setAttribute("bulkassign", "true");
/*  130 */       java.util.ArrayList tags = MyFields.getDollarTags(true, "All", request, false);
/*  131 */       request.setAttribute("tags", tags);
/*  132 */       String selectedValue = request.getParameter("customField");
/*  133 */       String reloads = (String)request.getAttribute("reloads");
/*  134 */       String checkAllMonitors = "checked='checked'";
/*  135 */       String checkIndiMonitors = "";
/*  136 */       if (reloads != null)
/*      */       {
/*  138 */         if (reloads.equals("allmonitors")) {
/*  139 */           checkAllMonitors = "checked='checked'";
/*  140 */           checkIndiMonitors = "";
/*      */         } else {
/*  142 */           checkAllMonitors = "";
/*  143 */           checkIndiMonitors = "checked='checked'";
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  149 */       out.write("\n<script language=\"JavaScript1.2\">\n\n\n\n$(document).ready(function(){\n\tvar value = ");
/*  150 */       out.print((String)request.getAttribute("loadvalue"));
/*  151 */       out.write("\n\ttoggleDivs(value)\n//\tjQuery(\"#Toppings\").buttonset();\n//\tvar radioValue = $('input[name=myRadio]:checked').val()\n//\t$(\"#\"+radioValue).show();\n\t\n}); \n\n\n\n\t\n\nfunction myOnLoad(){\n\t\n// document.getElementById(\"showCustomFieldOption\").style.display=\"block\";\n// loadURLType('");
/*  152 */       out.print(request.getParameter("customValue"));
/*  153 */       out.write("',null,null,true);\n\n}\n\n\nfunction loadURLType(option,frm,a,forBulkAssign){\n\n\n\tvar resourceid = a.id;\n\t\n\tif(option != 'null'){\n                                var http1=getHTTPObject();\n\n                                http1.onreadystatechange= function (){showCustomFieldValues(http1,resourceid);};//No I18N\n\n                                 if(option.indexOf(\"$\") != -1){\n\n                                        URL = \"/myFields.do?method=getFieldValues&aliasName=\"+option.substring(0,option.indexOf(\"$\"))+\"&forBulkAssign=\"+forBulkAssign+\"&resourceid=\"+resourceid;//No I18N; // NO I18N\n\n                                }else{\n                                URL=\"/myFields.do?method=getFieldValues&aliasName=\"+option+\"&forBulkAssign=\"+forBulkAssign+\"&resourceid=\"+resourceid;//No I18N\n                                }\n                                http1.open(\"GET\",URL,true);//No I18N\n                                http1.send(null);//No I18N\n                        }\n}\n\nfunction loadBulkURLType(option,frm,a,forBulkAssign){\n");
/*  154 */       out.write("\n\n\tvar resourceid = a.id;\n\t\n\tif(option != 'null'){\n  \t\t\t\t\t   var http1=getHTTPObject();\n                       http1.onreadystatechange= function (){showCustomFieldValues(http1,resourceid);};//No I18N\n\t URL=\"/myFields.do?method=getFieldValues&aliasName=\"+option+\"&allmonitors=true&forBulkAssign=\"+forBulkAssign+\"&resourceid=\"+resourceid;//No I18N\n\n                                http1.open(\"GET\",URL,true);//No I18N\n                                http1.send(null);//No I18N\n                        }\n}\n\nfunction showCustomFieldValues(http1,displays)\n{\n\tif(http1.readyState==4)\n\t{\n\t\tdocument.getElementById(\"customFieldValues_\"+displays).style.display = \"block\";\n\t\tdocument.getElementById(\"customFieldValues_\"+displays).innerHTML=http1.responseText;//No I18N\n\t}\n}\n\nfunction assignCustomValues(value){\n\n                if(value != '-'){\n               document.AMActionForm.action=\"/myFields.do?method=bulkAssign&customVal=\"+value+\"&resids=");
/*  155 */       out.print(resids);
/*  156 */       out.write("\";//No I18N\n                document.AMActionForm.submit();\n                }\n}\n\nfunction toggleDivs(value){\n\n\t\n\tif(value == \"1\"){\n\t\t$(\"#assignAllCF\").show();\t// NO I18N\n\t\t$(\"#assignIndiCF\").hide();\t// NO I18N\n\t}else{\n\t\t$(\"#assignAllCF\").hide();\t// NO I18N\n\t\t$(\"#assignIndiCF\").show();\t// NO I18N\n\t\t\n\t}\n\t\n}\n\n</script>\n\n <table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n\t\t <tr>\n\t <td>&nbsp;\n\t <span class=\"headingboldwhite\">");
/*  157 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */         return;
/*  159 */       out.write("</span>  ");
/*  160 */       out.write("\n\t </td>\n\t\t </tr>\n\t\t</table>\n\n");
/*      */       
/*  162 */       MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  163 */       _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  164 */       _jspx_th_html_005fmessages_005f0.setParent(null);
/*      */       
/*  166 */       _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */       
/*  168 */       _jspx_th_html_005fmessages_005f0.setMessage("false");
/*  169 */       int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  170 */       if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  171 */         String msg = null;
/*  172 */         if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  173 */           out = _jspx_page_context.pushBody();
/*  174 */           _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  175 */           _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */         }
/*  177 */         msg = (String)_jspx_page_context.findAttribute("msg");
/*      */         for (;;) {
/*  179 */           out.write("         \n\t\t\n\t          \t\t<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n\t        \t\t\t<tr> \n\t                \t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t<td width=\"95%\" height=\"28\" class=\"message\">");
/*  180 */           if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */             return;
/*  182 */           out.write("</td>\n\t              \t\t\t</tr>\n\t            \t\t</table>\n\t            \t\t<br>\n\t\t\n\t\t");
/*  183 */           int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  184 */           msg = (String)_jspx_page_context.findAttribute("msg");
/*  185 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*  188 */         if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  189 */           out = _jspx_page_context.popBody();
/*      */         }
/*      */       }
/*  192 */       if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  193 */         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */       }
/*      */       else {
/*  196 */         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  197 */         out.write(10);
/*  198 */         out.write(9);
/*  199 */         out.write(9);
/*      */         
/*  201 */         MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  202 */         _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  203 */         _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*      */         
/*  205 */         _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  206 */         int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  207 */         if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */           for (;;) {
/*  209 */             out.write(" \n\t\t\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t\t\t\t<tr> \n\t                \t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t\t<td width=\"95%\" class=\"message\">\n\t                \t\t\t\t\t");
/*      */             
/*  211 */             MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  212 */             _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/*  213 */             _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */             
/*  215 */             _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */             
/*  217 */             _jspx_th_html_005fmessages_005f1.setMessage("true");
/*  218 */             int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/*  219 */             if (_jspx_eval_html_005fmessages_005f1 != 0) {
/*  220 */               String msg = null;
/*  221 */               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  222 */                 out = _jspx_page_context.pushBody();
/*  223 */                 _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/*  224 */                 _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */               }
/*  226 */               msg = (String)_jspx_page_context.findAttribute("msg");
/*      */               for (;;) {
/*  228 */                 out.write(" \n\t                \t  \t\t\t\t\t");
/*  229 */                 if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                   return;
/*  231 */                 out.write("<br>\n\t\t\t\t\t\t\t\t");
/*  232 */                 int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/*  233 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/*  234 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  237 */               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/*  238 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  241 */             if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/*  242 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */             }
/*      */             
/*  245 */             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/*  246 */             out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t");
/*  247 */             int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  248 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  252 */         if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  253 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */         }
/*      */         else {
/*  256 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  257 */           out.write(32);
/*  258 */           out.write("\n\n\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  class=\"lrtbdarkborder\" width=\"100%\" align=\"center\">\n\n\t<tr>\n       <td colspan=\"2\">\n\n\t\t\n\t\t </td> \n    </tr>\n\t<tr>\n<td colspan=\"3\">\n");
/*  259 */           if (_jspx_meth_html_005fform_005f0(_jspx_page_context))
/*      */             return;
/*  261 */           out.write("\n</td>\n\t</tr>\n\n\n\t<tr>\n\t<td>\n<div id=\"assignAllCF\" style=\"display:none\">\n<form  action=\"/myFields.do?method=bulkAssign\" method=\"post\"  name=\"AllMonitors\" style=\"display:inline\" >\n<input type=\"hidden\" name=\"resids\" value=\"");
/*  262 */           out.print(resids);
/*  263 */           out.write("\">\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"5\" border=\"0\"   width=\"100%\" align=\"center\"><tr>\n<td class=\"bodytext\" align=\"left\">&nbsp;&nbsp;<b>");
/*  264 */           if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */             return;
/*  266 */           out.write("</b>\t");
/*  267 */           out.write("\n\t  </td></tr>\n\t<tr><td class=\"bodytext\"><ul class=\"bodytext\">\n\n\t\t\t\t\t");
/*      */           
/*  269 */           ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  270 */           _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  271 */           _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */           
/*  273 */           _jspx_th_c_005fforEach_005f0.setVar("montype");
/*      */           
/*  275 */           _jspx_th_c_005fforEach_005f0.setItems("${monitorTypes}");
/*      */           
/*  277 */           _jspx_th_c_005fforEach_005f0.setVarStatus("loopstatus");
/*  278 */           int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */           try {
/*  280 */             int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/*  281 */             if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */               for (;;) {
/*  283 */                 out.write(9);
/*  284 */                 out.write("\n\t\t\t\t\t\t");
/*  285 */                 if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  303 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/*  304 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/*  287 */                 out.write("\n\t &nbsp;&nbsp;&nbsp;<li>\n\t \t \t\t\t\t\t");
/*  288 */                 out.print(com.adventnet.appmanager.util.FormatUtil.getString((String)pageContext.getAttribute("montype")));
/*  289 */                 out.write("\n\t \t\t\t\t\t</li>\n\t\t\t\t\t");
/*  290 */                 int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/*  291 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  295 */             if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  303 */               _jspx_th_c_005fforEach_005f0.doFinally();
/*  304 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */             }
/*      */           }
/*      */           catch (Throwable _jspx_exception)
/*      */           {
/*      */             for (;;)
/*      */             {
/*  299 */               int tmp1205_1204 = 0; int[] tmp1205_1202 = _jspx_push_body_count_c_005fforEach_005f0; int tmp1207_1206 = tmp1205_1202[tmp1205_1204];tmp1205_1202[tmp1205_1204] = (tmp1207_1206 - 1); if (tmp1207_1206 <= 0) break;
/*  300 */               out = _jspx_page_context.popBody(); }
/*  301 */             _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */           } finally {
/*  303 */             _jspx_th_c_005fforEach_005f0.doFinally();
/*  304 */             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */           }
/*  306 */           out.write("\n\t\t\t\t</ul></td>\n\t</tr>\n\t<tr>\n\t<td>\n\t\t\t\t<table width=\"100%\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"bodytext\" width=\"25%\" align=\"center\">");
/*  307 */           if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */             return;
/*  309 */           out.write("</td>\t");
/*  310 */           out.write("\n\t\t\t\t\t\t<td width=\"30%\">\n\t\t\t\t\n\t\t\t\t\t\t");
/*  311 */           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_Option.jsp" + ("MyField_Option.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("listid", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("bulkassign", request.getCharacterEncoding()), out, false);
/*  312 */           out.write(9);
/*  313 */           out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t<div id=\"customFieldValues_bulkassign\"\n\t\t\t\t\t\t\tstyle=\"display: none; float: left\"></div>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td></tr>\n\t<tr>\n\t\t\t<td height=\"35\" colspan=\"3\" align=\"center\" class=\"tablebottom\">\n       \t\t\t\t\t<input name=\"GoBack\" type=\"submit\" class=\"buttons btn_highlt\" value=\"");
/*  314 */           if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */             return;
/*  316 */           out.write("\" >\n        \t   \t\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons btn_link\" value=\"");
/*  317 */           if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */             return;
/*  319 */           out.write("\"  onClick=\"self.close();\">&nbsp;&nbsp;\n       \t\t\t\t\t</td>\n\t\t\t</tr>\n\t</table>\n\t</form>\n</div>\n<div id=\"assignIndiCF\" style=\"display:none\">\n<form  action=\"/myFields.do?method=bulkAssignSeparateMonitors\" method=\"post\" name=\"IndividualMonitors\" style=\"display:inline\" >\n<input type=\"hidden\" name=\"resids\" value=\"");
/*  320 */           out.print(resids);
/*  321 */           out.write("\">\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"   width=\"100%\" align=\"center\">\n\n\t\t\t");
/*  322 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_page_context))
/*      */             return;
/*  324 */           out.write("\n\t\t\t<tr>\n\t\t\t<td height=\"35\" colspan=\"3\" align=\"center\" class=\"tablebottom\">\n       \t\t\t\t\t<input name=\"GoBack\" type=\"submit\" class=\"buttons btn_highlt\" value=\"");
/*  325 */           out.print(com.adventnet.appmanager.util.FormatUtil.getString("Save"));
/*  326 */           out.write("\">\n        \t   \t\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons btn_link\" value=\"");
/*  327 */           out.print(com.adventnet.appmanager.util.FormatUtil.getString("webclient.common.printview.button.close"));
/*  328 */           out.write("\"  onClick=\"self.close();\">&nbsp;&nbsp;\n       \t\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t\n\t\t\t\t\t\t\n\t\t\t\t</table>\t\n\t\t\t\t</form>\t\n</div>\n</td>\n\t</tr>\n\n<tr><td>\n\n</td></tr>\n\n\t\t\t\t</table>\n       \t\t\t\t\n\t\n\n");
/*      */         }
/*  330 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  331 */         out = _jspx_out;
/*  332 */         if ((out != null) && (out.getBufferSize() != 0))
/*  333 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  334 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  337 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  343 */     PageContext pageContext = _jspx_page_context;
/*  344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  346 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  347 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  348 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  350 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  352 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  353 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  354 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  355 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  356 */       return true;
/*      */     }
/*  358 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  364 */     PageContext pageContext = _jspx_page_context;
/*  365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  367 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  368 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/*  369 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*  370 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/*  371 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/*  372 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/*  373 */         out = _jspx_page_context.pushBody();
/*  374 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/*  375 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  378 */         out.write("am.myfield.bulkassign.text");
/*  379 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/*  380 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  383 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/*  384 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  387 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/*  388 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/*  389 */       return true;
/*      */     }
/*  391 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/*  392 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  397 */     PageContext pageContext = _jspx_page_context;
/*  398 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  400 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/*  401 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/*  402 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/*  404 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/*  406 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/*  407 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/*  408 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/*  409 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/*  410 */       return true;
/*      */     }
/*  412 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/*  413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  418 */     PageContext pageContext = _jspx_page_context;
/*  419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  421 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/*  422 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/*  423 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/*  425 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/*  427 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/*  428 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/*  429 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/*  430 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/*  431 */       return true;
/*      */     }
/*  433 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/*  434 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fform_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  439 */     PageContext pageContext = _jspx_page_context;
/*  440 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  442 */     FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  443 */     _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  444 */     _jspx_th_html_005fform_005f0.setParent(null);
/*      */     
/*  446 */     _jspx_th_html_005fform_005f0.setAction("/myFields");
/*      */     
/*  448 */     _jspx_th_html_005fform_005f0.setStyle("display:inline");
/*  449 */     int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  450 */     if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */       for (;;) {
/*  452 */         out.write("\n\t\t\t<table width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr style=\"background-color: #F5F9FD\" height=\"30\">\n\t\t\t\t\t<td class=\"bodytext\" width=\"25%\">&nbsp;");
/*  453 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*  454 */           return true;
/*  455 */         out.write("</td>\t");
/*  456 */         out.write("\n\t\t\t\t\t<td class=\"bodytext\" width=\"5\">\n\t\t\t\t\t");
/*  457 */         if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*  458 */           return true;
/*  459 */         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"bodytext\" width=\"15%\">");
/*  460 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*  461 */           return true;
/*  462 */         out.write("</td>\t");
/*  463 */         out.write("\n\t\t\t\t\t<td class=\"bodytext\" width=\"5\">\n\t\t\t\t\t");
/*  464 */         if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*  465 */           return true;
/*  466 */         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"bodytext\">");
/*  467 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*  468 */           return true;
/*  469 */         out.write("</td>\t");
/*  470 */         out.write("\n\n\t\t\t\t\t<!--  <input name=\"myRadio\" type=\"radio\" value=\"assignAllCF\"  onclick=\"toggleDivs('assignAllCF','assignIndiCF')\"  checkAllMonitors%> id=\"A\" /><label for=\"A\">All Monitors</label> \n<input name=\"myRadio\" type=\"radio\" value=\"assignIndiCF\" onclick=\"toggleDivs('assignIndiCF','assignAllCF')\"  checkIndiMonitors%> id=\"I\"  /><label for=\"I\">Individual Monitor</label>\t-->\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t");
/*  471 */         int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  472 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  476 */     if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  477 */       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  478 */       return true;
/*      */     }
/*  480 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  481 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  486 */     PageContext pageContext = _jspx_page_context;
/*  487 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  489 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  490 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/*  491 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*  492 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/*  493 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/*  494 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/*  495 */         out = _jspx_page_context.pushBody();
/*  496 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/*  497 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  500 */         out.write("am.myfield.assign.text");
/*  501 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/*  502 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  505 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/*  506 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  509 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/*  510 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/*  511 */       return true;
/*      */     }
/*  513 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/*  514 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  519 */     PageContext pageContext = _jspx_page_context;
/*  520 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  522 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/*  523 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/*  524 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  526 */     _jspx_th_html_005fradio_005f0.setProperty("customgroupType");
/*      */     
/*  528 */     _jspx_th_html_005fradio_005f0.setValue("1");
/*      */     
/*  530 */     _jspx_th_html_005fradio_005f0.setStyleClass("radiobutton");
/*      */     
/*  532 */     _jspx_th_html_005fradio_005f0.setOnclick("toggleDivs(this.value)");
/*  533 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/*  534 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/*  535 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/*  536 */       return true;
/*      */     }
/*  538 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/*  539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  544 */     PageContext pageContext = _jspx_page_context;
/*  545 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  547 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  548 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/*  549 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*  550 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/*  551 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/*  552 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/*  553 */         out = _jspx_page_context.pushBody();
/*  554 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/*  555 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  558 */         out.write("am.myfield.allmonitor.text");
/*  559 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/*  560 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  563 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/*  564 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  567 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/*  568 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/*  569 */       return true;
/*      */     }
/*  571 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/*  572 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  577 */     PageContext pageContext = _jspx_page_context;
/*  578 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  580 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/*  581 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/*  582 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  584 */     _jspx_th_html_005fradio_005f1.setProperty("customgroupType");
/*      */     
/*  586 */     _jspx_th_html_005fradio_005f1.setValue("2");
/*      */     
/*  588 */     _jspx_th_html_005fradio_005f1.setStyleClass("radiobutton");
/*      */     
/*  590 */     _jspx_th_html_005fradio_005f1.setOnclick("toggleDivs(this.value)");
/*  591 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/*  592 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/*  593 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/*  594 */       return true;
/*      */     }
/*  596 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/*  597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  602 */     PageContext pageContext = _jspx_page_context;
/*  603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  605 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  606 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/*  607 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*  608 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/*  609 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/*  610 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/*  611 */         out = _jspx_page_context.pushBody();
/*  612 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/*  613 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  616 */         out.write("am.myfield.individualmonitor.text");
/*  617 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/*  618 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  621 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/*  622 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  625 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/*  626 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/*  627 */       return true;
/*      */     }
/*  629 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/*  630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  635 */     PageContext pageContext = _jspx_page_context;
/*  636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  638 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  639 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/*  640 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/*  641 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/*  642 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/*  643 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/*  644 */         out = _jspx_page_context.pushBody();
/*  645 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/*  646 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  649 */         out.write("am.webclient.bulkupdate.haveselectedfollowing.text");
/*  650 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/*  651 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  654 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/*  655 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  658 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/*  659 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/*  660 */       return true;
/*      */     }
/*  662 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/*  663 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  668 */     PageContext pageContext = _jspx_page_context;
/*  669 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  671 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  672 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  673 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*  674 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  675 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/*  677 */         out.write("\n\n\t\t\t\t\t\t\t");
/*  678 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  679 */           return true;
/*  680 */         out.write("\n\t\t\t\t\t\t\t");
/*  681 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  682 */           return true;
/*  683 */         out.write("\n\n\t\t\t\t\t\t");
/*  684 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  685 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  689 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  690 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  691 */       return true;
/*      */     }
/*  693 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  699 */     PageContext pageContext = _jspx_page_context;
/*  700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  702 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  703 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  704 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/*  706 */     _jspx_th_c_005fwhen_005f0.setTest("${loopstatus.count%2!=0}");
/*  707 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  708 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/*  710 */         out.write("\n\t\t\t\t\t\t\t\t");
/*  711 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  712 */           return true;
/*  713 */         out.write(9);
/*  714 */         out.write("\n\n\t\t\t\t\t\t\t");
/*  715 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  716 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  720 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  721 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  722 */       return true;
/*      */     }
/*  724 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  730 */     PageContext pageContext = _jspx_page_context;
/*  731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  733 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/*  734 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  735 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  737 */     _jspx_th_c_005fset_005f0.setVar("trclass");
/*      */     
/*  739 */     _jspx_th_c_005fset_005f0.setScope("page");
/*  740 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  741 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/*  742 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  743 */         out = _jspx_page_context.pushBody();
/*  744 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/*  745 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  746 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  749 */         out.write("yellowgrayborder");
/*  750 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  751 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  754 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  755 */         out = _jspx_page_context.popBody();
/*  756 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/*  759 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  760 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/*  761 */       return true;
/*      */     }
/*  763 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/*  764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  769 */     PageContext pageContext = _jspx_page_context;
/*  770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  772 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  773 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  774 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*  775 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  776 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/*  778 */         out.write("\n\n\t\t\t\t\t\t\t\t");
/*  779 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  780 */           return true;
/*  781 */         out.write(32);
/*  782 */         out.write("\n\n\t\t\t\t\t\t\t");
/*  783 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  784 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  788 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  789 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  790 */       return true;
/*      */     }
/*  792 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  798 */     PageContext pageContext = _jspx_page_context;
/*  799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  801 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/*  802 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  803 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/*  805 */     _jspx_th_c_005fset_005f1.setVar("trclass");
/*      */     
/*  807 */     _jspx_th_c_005fset_005f1.setScope("page");
/*  808 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  809 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/*  810 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/*  811 */         out = _jspx_page_context.pushBody();
/*  812 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/*  813 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  814 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  817 */         out.write("whitegrayborder");
/*  818 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  819 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  822 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/*  823 */         out = _jspx_page_context.popBody();
/*  824 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/*  827 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  828 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/*  829 */       return true;
/*      */     }
/*  831 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/*  832 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  837 */     PageContext pageContext = _jspx_page_context;
/*  838 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  840 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  841 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/*  842 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/*  843 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/*  844 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/*  845 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/*  846 */         out = _jspx_page_context.pushBody();
/*  847 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/*  848 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  851 */         out.write("am.myfield.selectcustomfield.text");
/*  852 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/*  853 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  856 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/*  857 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  860 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/*  861 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/*  862 */       return true;
/*      */     }
/*  864 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/*  865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  870 */     PageContext pageContext = _jspx_page_context;
/*  871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  873 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  874 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/*  875 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/*  876 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/*  877 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/*  878 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/*  879 */         out = _jspx_page_context.pushBody();
/*  880 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/*  881 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  884 */         out.write("Save");
/*  885 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/*  886 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  889 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/*  890 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  893 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/*  894 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/*  895 */       return true;
/*      */     }
/*  897 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/*  898 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  903 */     PageContext pageContext = _jspx_page_context;
/*  904 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  906 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  907 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/*  908 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/*  909 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/*  910 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/*  911 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/*  912 */         out = _jspx_page_context.pushBody();
/*  913 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/*  914 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  917 */         out.write("webclient.common.printview.button.close");
/*  918 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/*  919 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  922 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/*  923 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  926 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/*  927 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/*  928 */       return true;
/*      */     }
/*  930 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/*  931 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  936 */     PageContext pageContext = _jspx_page_context;
/*  937 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  939 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  940 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/*  941 */     _jspx_th_c_005fforEach_005f1.setParent(null);
/*      */     
/*  943 */     _jspx_th_c_005fforEach_005f1.setVar("eachresid");
/*      */     
/*  945 */     _jspx_th_c_005fforEach_005f1.setItems("${resourceids}");
/*      */     
/*  947 */     _jspx_th_c_005fforEach_005f1.setVarStatus("loopstatus");
/*  948 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/*  950 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/*  951 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/*  953 */           out.write("\n\t\t\t\t");
/*  954 */           boolean bool; if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  955 */             return true;
/*  956 */           out.write("\n\t\t\t\t\n\t\t\t\t<tr>\t\n\t\t\t\t\t\t\t<td class=\"");
/*  957 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  958 */             return true;
/*  959 */           out.write("\" style=\"height:30px\" width=\"40%\">");
/*  960 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  961 */             return true;
/*  962 */           out.write("</td>\n\t\t\t\t\t\t\t<td class=\"");
/*  963 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  964 */             return true;
/*  965 */           out.write("\" style=\"height:30px\" width=\"30%\" >\n\t\t\t\t\t\t\t<select name=\"");
/*  966 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  967 */             return true;
/*  968 */           out.write("\" id=\"");
/*  969 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  970 */             return true;
/*  971 */           out.write("\" class='formtext' style='background-color:#FFF8C6;' onchange='loadURLType(this.options[this.selectedIndex].value,this.form,this,\"true\")'>\n\t\t\t\t\t\t\t<option selected=\"selected\" style=\"background-color: #FFF8C6\" value=\"-\">--");
/*  972 */           if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  973 */             return true;
/*  974 */           out.write("--</option> ");
/*  975 */           out.write("\n\t\t\t\t\t\t\t");
/*  976 */           if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  977 */             return true;
/*  978 */           out.write("\n                            </select>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td class=\"");
/*  979 */           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  980 */             return true;
/*  981 */           out.write("\" style=\"height:30px\" width=\"30%\" align=\"left\"  >\n\n\t<div id=\"customFieldValues_");
/*  982 */           if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  983 */             return true;
/*  984 */           out.write("\" style=\" display:block; float:left;\" >\n\t\n\t</div>\n\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t");
/*  985 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/*  986 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  990 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*  991 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/*  994 */         int tmp551_550 = 0; int[] tmp551_548 = _jspx_push_body_count_c_005fforEach_005f1; int tmp553_552 = tmp551_548[tmp551_550];tmp551_548[tmp551_550] = (tmp553_552 - 1); if (tmp553_552 <= 0) break;
/*  995 */         out = _jspx_page_context.popBody(); }
/*  996 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/*  998 */       _jspx_th_c_005fforEach_005f1.doFinally();
/*  999 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1006 */     PageContext pageContext = _jspx_page_context;
/* 1007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1009 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1010 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1011 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 1012 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1013 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1015 */         out.write("\n\t\t\t\t\t\n\t\t\t\t\t");
/* 1016 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1017 */           return true;
/* 1018 */         out.write("\n\t\t\t\t\t");
/* 1019 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1020 */           return true;
/* 1021 */         out.write("\n\t\t\t\t\t\n\t\t\t\t");
/* 1022 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1023 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1027 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1028 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1029 */       return true;
/*      */     }
/* 1031 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1032 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1037 */     PageContext pageContext = _jspx_page_context;
/* 1038 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1040 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1041 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1042 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1044 */     _jspx_th_c_005fwhen_005f1.setTest("${loopstatus.count%2!=0}");
/* 1045 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1046 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1048 */         out.write("\n\t\t\t\t\t\t");
/* 1049 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1050 */           return true;
/* 1051 */         out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t");
/* 1052 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1053 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1057 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1058 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1059 */       return true;
/*      */     }
/* 1061 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1062 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1067 */     PageContext pageContext = _jspx_page_context;
/* 1068 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1070 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 1071 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 1072 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1074 */     _jspx_th_c_005fset_005f2.setVar("trclass");
/*      */     
/* 1076 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 1077 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 1078 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 1079 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 1080 */         out = _jspx_page_context.pushBody();
/* 1081 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 1082 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 1083 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1086 */         out.write("yellowgrayborder");
/* 1087 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 1088 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1091 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 1092 */         out = _jspx_page_context.popBody();
/* 1093 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 1096 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 1097 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 1098 */       return true;
/*      */     }
/* 1100 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 1101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1106 */     PageContext pageContext = _jspx_page_context;
/* 1107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1109 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1110 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1111 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1112 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1113 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1115 */         out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t\t");
/* 1116 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1117 */           return true;
/* 1118 */         out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t");
/* 1119 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1120 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1124 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1125 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1126 */       return true;
/*      */     }
/* 1128 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1129 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1134 */     PageContext pageContext = _jspx_page_context;
/* 1135 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1137 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 1138 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 1139 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1141 */     _jspx_th_c_005fset_005f3.setVar("trclass");
/*      */     
/* 1143 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 1144 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 1145 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 1146 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 1147 */         out = _jspx_page_context.pushBody();
/* 1148 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 1149 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 1150 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1153 */         out.write("whitegrayborder");
/* 1154 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 1155 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1158 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 1159 */         out = _jspx_page_context.popBody();
/* 1160 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 1163 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 1164 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 1165 */       return true;
/*      */     }
/* 1167 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 1168 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1173 */     PageContext pageContext = _jspx_page_context;
/* 1174 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1176 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1177 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1178 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1180 */     _jspx_th_c_005fout_005f1.setValue("${trclass}");
/* 1181 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1182 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1183 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1184 */       return true;
/*      */     }
/* 1186 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1192 */     PageContext pageContext = _jspx_page_context;
/* 1193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1195 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1196 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1197 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1199 */     _jspx_th_c_005fout_005f2.setValue("${mondisplayNames[eachresid]}");
/* 1200 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1201 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1202 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1203 */       return true;
/*      */     }
/* 1205 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1206 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1211 */     PageContext pageContext = _jspx_page_context;
/* 1212 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1214 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1215 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1216 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1218 */     _jspx_th_c_005fout_005f3.setValue("${trclass}");
/* 1219 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1220 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1221 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1222 */       return true;
/*      */     }
/* 1224 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1230 */     PageContext pageContext = _jspx_page_context;
/* 1231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1233 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1234 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1235 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1237 */     _jspx_th_c_005fout_005f4.setValue("${eachresid }");
/* 1238 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1239 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1240 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1241 */       return true;
/*      */     }
/* 1243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1249 */     PageContext pageContext = _jspx_page_context;
/* 1250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1252 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1253 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1254 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1256 */     _jspx_th_c_005fout_005f5.setValue("${eachresid }");
/* 1257 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1258 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1259 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1260 */       return true;
/*      */     }
/* 1262 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1268 */     PageContext pageContext = _jspx_page_context;
/* 1269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1271 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 1272 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 1273 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 1274 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 1275 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 1276 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 1277 */         out = _jspx_page_context.pushBody();
/* 1278 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 1279 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 1280 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1283 */         out.write("am.myfield.choosevalue.text");
/* 1284 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 1285 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1288 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 1289 */         out = _jspx_page_context.popBody();
/* 1290 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 1293 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 1294 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 1295 */       return true;
/*      */     }
/* 1297 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 1298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1303 */     PageContext pageContext = _jspx_page_context;
/* 1304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1306 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 1307 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 1308 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1310 */     _jspx_th_c_005fforEach_005f2.setVar("eachtag");
/*      */     
/* 1312 */     _jspx_th_c_005fforEach_005f2.setItems("${tags}");
/* 1313 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 1315 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 1316 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 1318 */           out.write("\n\t\t\t\t\t\t\t<option  style=\"background-color: #FFF8C6\" value=\"");
/* 1319 */           boolean bool; if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1320 */             return true;
/* 1321 */           out.write(34);
/* 1322 */           out.write(62);
/* 1323 */           if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1324 */             return true;
/* 1325 */           out.write("</option>\n                            ");
/* 1326 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 1327 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1331 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 1332 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1335 */         int tmp240_239 = 0; int[] tmp240_237 = _jspx_push_body_count_c_005fforEach_005f2; int tmp242_241 = tmp240_237[tmp240_239];tmp240_237[tmp240_239] = (tmp242_241 - 1); if (tmp242_241 <= 0) break;
/* 1336 */         out = _jspx_page_context.popBody(); }
/* 1337 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 1339 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 1340 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 1342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1347 */     PageContext pageContext = _jspx_page_context;
/* 1348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1350 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1351 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1352 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1354 */     _jspx_th_c_005fout_005f6.setValue("${eachtag.labelalias}");
/* 1355 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1356 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1357 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1358 */       return true;
/*      */     }
/* 1360 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1361 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1366 */     PageContext pageContext = _jspx_page_context;
/* 1367 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1369 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1370 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1371 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1373 */     _jspx_th_c_005fout_005f7.setValue("${eachtag.label}");
/* 1374 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1375 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1376 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1377 */       return true;
/*      */     }
/* 1379 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1380 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1385 */     PageContext pageContext = _jspx_page_context;
/* 1386 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1388 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1389 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1390 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1392 */     _jspx_th_c_005fout_005f8.setValue("${trclass}");
/* 1393 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1394 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1395 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1396 */       return true;
/*      */     }
/* 1398 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1399 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1404 */     PageContext pageContext = _jspx_page_context;
/* 1405 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1407 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1408 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1409 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1411 */     _jspx_th_c_005fout_005f9.setValue("${eachresid }");
/* 1412 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1413 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1414 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1415 */       return true;
/*      */     }
/* 1417 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1418 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AssignCustomFields_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */