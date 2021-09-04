/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class mopaction_005fnombeans_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   25 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   31 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   32 */   static { _jspx_dependants.put("/jsp/includes/NewActions.jspf", Long.valueOf(1473429417000L));
/*   33 */     _jspx_dependants.put("/jsp/includes/CreateApplicationWizard.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   55 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   59 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   74 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   78 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   79 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
/*   80 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*   81 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   82 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   83 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   84 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   85 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*   86 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/*   87 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*   88 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*   89 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*   91 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   98 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  101 */     JspWriter out = null;
/*  102 */     Object page = this;
/*  103 */     JspWriter _jspx_out = null;
/*  104 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  108 */       response.setContentType("text/html");
/*  109 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  111 */       _jspx_page_context = pageContext;
/*  112 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  113 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  114 */       session = pageContext.getSession();
/*  115 */       out = pageContext.getOut();
/*  116 */       _jspx_out = out;
/*      */       
/*  118 */       out.write("\n\n\n\n\n\n");
/*      */       
/*  120 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  121 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  122 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/*  124 */       _jspx_th_c_005fif_005f0.setTest("${empty param.redirectto}");
/*  125 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  126 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/*  128 */           out.write(10);
/*      */           
/*  130 */           FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(FormTag.class);
/*  131 */           _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  132 */           _jspx_th_html_005fform_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */           
/*  134 */           _jspx_th_html_005fform_005f0.setMethod("POST");
/*      */           
/*  136 */           _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*  137 */           int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  138 */           if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */             for (;;) {
/*  140 */               out.write(10);
/*  141 */               out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */               
/*  143 */               if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */               {
/*      */ 
/*  146 */                 out.write("\n        myOnLoad1();\n        ");
/*      */               }
/*      */               
/*      */ 
/*  150 */               out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/*  151 */               if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  153 */               out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/*  154 */               out.write("\t  {\n\t\t\t");
/*  155 */               if (_jspx_meth_c_005fif_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  157 */               out.write("\n\t\t  ");
/*      */               
/*  159 */               if ((Constants.sqlManager) || (EnterpriseUtil.isAdminServer()))
/*      */               {
/*  161 */                 out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */               }
/*      */               else
/*      */               {
/*  165 */                 out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */               }
/*  167 */               out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/*  168 */               if (EnterpriseUtil.isManagedServer())
/*      */               {
/*  170 */                 out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */               }
/*      */               else
/*      */               {
/*  174 */                 out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */               }
/*  176 */               out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/*  177 */               if (EnterpriseUtil.isManagedServer())
/*      */               {
/*  179 */                 out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */               }
/*      */               else
/*      */               {
/*  183 */                 out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */               }
/*  185 */               out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/*  186 */               if (Constants.sqlManager) {
/*  187 */                 out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */               }
/*  189 */               else if (EnterpriseUtil.isManagedServer()) {
/*  190 */                 out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */               } else {
/*  192 */                 out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */               }
/*  194 */               out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/*  195 */               if (EnterpriseUtil.isManagedServer()) {
/*  196 */                 out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */               } else {
/*  198 */                 out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */               }
/*  200 */               out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */               
/*  202 */               String action_haid = request.getParameter("haid");
/*  203 */               String returnpath = "";
/*      */               
/*  205 */               if (request.getParameter("returnpath") != null)
/*      */               {
/*  207 */                 returnpath = "&returnpath=" + java.net.URLEncoder.encode(request.getParameter("returnpath"));
/*      */               }
/*      */               
/*      */ 
/*  211 */               out.write(10);
/*  212 */               out.write(10);
/*      */               
/*  214 */               SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  215 */               _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  216 */               _jspx_th_c_005fset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  218 */               _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/*  219 */               int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  220 */               if (_jspx_eval_c_005fset_005f0 != 0) {
/*  221 */                 if (_jspx_eval_c_005fset_005f0 != 1) {
/*  222 */                   out = _jspx_page_context.pushBody();
/*  223 */                   _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  224 */                   _jspx_th_c_005fset_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  227 */                   out.print(Constants.sqlManager);
/*  228 */                   int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  229 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  232 */                 if (_jspx_eval_c_005fset_005f0 != 1) {
/*  233 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  236 */               if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  237 */                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */               }
/*      */               
/*  240 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  241 */               out.write(10);
/*      */               
/*  243 */               SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  244 */               _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  245 */               _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  247 */               _jspx_th_c_005fset_005f1.setVar("isIt360");
/*  248 */               int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  249 */               if (_jspx_eval_c_005fset_005f1 != 0) {
/*  250 */                 if (_jspx_eval_c_005fset_005f1 != 1) {
/*  251 */                   out = _jspx_page_context.pushBody();
/*  252 */                   _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  253 */                   _jspx_th_c_005fset_005f1.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  256 */                   out.print(Constants.isIt360);
/*  257 */                   int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  258 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  261 */                 if (_jspx_eval_c_005fset_005f1 != 1) {
/*  262 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  265 */               if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  266 */                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */               }
/*      */               
/*  269 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  270 */               out.write(10);
/*      */               
/*  272 */               SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  273 */               _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  274 */               _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  276 */               _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/*  277 */               int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  278 */               if (_jspx_eval_c_005fset_005f2 != 0) {
/*  279 */                 if (_jspx_eval_c_005fset_005f2 != 1) {
/*  280 */                   out = _jspx_page_context.pushBody();
/*  281 */                   _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  282 */                   _jspx_th_c_005fset_005f2.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  285 */                   out.print(EnterpriseUtil.isAdminServer());
/*  286 */                   int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  287 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  290 */                 if (_jspx_eval_c_005fset_005f2 != 1) {
/*  291 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  294 */               if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  295 */                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */               }
/*      */               
/*  298 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  299 */               out.write(10);
/*      */               
/*  301 */               SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  302 */               _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  303 */               _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  305 */               _jspx_th_c_005fset_005f3.setVar("isProfServer");
/*  306 */               int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  307 */               if (_jspx_eval_c_005fset_005f3 != 0) {
/*  308 */                 if (_jspx_eval_c_005fset_005f3 != 1) {
/*  309 */                   out = _jspx_page_context.pushBody();
/*  310 */                   _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/*  311 */                   _jspx_th_c_005fset_005f3.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  314 */                   out.print(EnterpriseUtil.isProfEdition());
/*  315 */                   int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  316 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  319 */                 if (_jspx_eval_c_005fset_005f3 != 1) {
/*  320 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  323 */               if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  324 */                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */               }
/*      */               
/*  327 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  328 */               out.write(10);
/*      */               
/*  330 */               SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  331 */               _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  332 */               _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  334 */               _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/*  335 */               int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  336 */               if (_jspx_eval_c_005fset_005f4 != 0) {
/*  337 */                 if (_jspx_eval_c_005fset_005f4 != 1) {
/*  338 */                   out = _jspx_page_context.pushBody();
/*  339 */                   _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/*  340 */                   _jspx_th_c_005fset_005f4.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  343 */                   out.print(EnterpriseUtil.isCloudEdition());
/*  344 */                   int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/*  345 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  348 */                 if (_jspx_eval_c_005fset_005f4 != 1) {
/*  349 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  352 */               if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  353 */                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */               }
/*      */               
/*  356 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  357 */               out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/*  358 */               out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/*  359 */               out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */               
/*  361 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  362 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  363 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/*  364 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  365 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/*  367 */                   out.write(10);
/*  368 */                   out.write(9);
/*      */                   
/*  370 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  371 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  372 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/*  374 */                   _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/*  375 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  376 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/*  378 */                       out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/*  379 */                       out.print(action_haid);
/*  380 */                       out.print(returnpath);
/*  381 */                       out.write(34);
/*  382 */                       out.write(62);
/*  383 */                       out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  384 */                       out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/*  385 */                       out.print(action_haid);
/*  386 */                       out.print(returnpath);
/*  387 */                       out.write(34);
/*  388 */                       out.write(62);
/*  389 */                       out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  390 */                       out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/*  391 */                       out.print(action_haid);
/*  392 */                       out.print(returnpath);
/*  393 */                       out.write(34);
/*  394 */                       out.write(62);
/*  395 */                       out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  396 */                       out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/*  397 */                       out.print(action_haid);
/*  398 */                       out.print(returnpath);
/*  399 */                       out.write(34);
/*  400 */                       out.write(62);
/*  401 */                       out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  402 */                       out.write("</option>\n\t\t\n\t\t");
/*      */                       
/*  404 */                       ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  405 */                       _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  406 */                       _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  407 */                       int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  408 */                       if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                         for (;;) {
/*  410 */                           out.write("\n\t\t\t");
/*      */                           
/*  412 */                           WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  413 */                           _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  414 */                           _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                           
/*  416 */                           _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/*  417 */                           int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  418 */                           if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                             for (;;) {
/*  420 */                               out.write("\n\t\t\t\t");
/*      */                               
/*  422 */                               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  423 */                               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  424 */                               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/*  425 */                               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  426 */                               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                 for (;;) {
/*  428 */                                   out.write("\n\t\t\t\t\t");
/*      */                                   
/*  430 */                                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  431 */                                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  432 */                                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                   
/*  434 */                                   _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/*  435 */                                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  436 */                                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                     for (;;) {
/*  438 */                                       out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  439 */                                       out.print(action_haid);
/*  440 */                                       out.print(returnpath);
/*  441 */                                       out.write(34);
/*  442 */                                       out.write(62);
/*  443 */                                       out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  444 */                                       out.write("</option>\n\t\t\t\t\t");
/*  445 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  446 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/*  450 */                                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  451 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                   }
/*      */                                   
/*  454 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  455 */                                   out.write("\n\t\t\t\t\t");
/*      */                                   
/*  457 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  458 */                                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  459 */                                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/*  460 */                                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  461 */                                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                     for (;;) {
/*  463 */                                       out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  464 */                                       out.print(action_haid);
/*  465 */                                       out.print(returnpath);
/*  466 */                                       out.write(34);
/*  467 */                                       out.write(62);
/*  468 */                                       out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  469 */                                       out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  470 */                                       out.print(action_haid);
/*  471 */                                       out.print(returnpath);
/*  472 */                                       out.write(34);
/*  473 */                                       out.write(62);
/*  474 */                                       out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  475 */                                       out.write("</option>\n\t\t\t\t\t");
/*  476 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  477 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/*  481 */                                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  482 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                   }
/*      */                                   
/*  485 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  486 */                                   out.write("\n\t\t\t\t");
/*  487 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  488 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  492 */                               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  493 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                               }
/*      */                               
/*  496 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  497 */                               out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/*  498 */                               if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  499 */                                 out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  500 */                                 out.print(action_haid);
/*  501 */                                 out.print(returnpath);
/*  502 */                                 out.write(34);
/*  503 */                                 out.write(62);
/*  504 */                                 out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  505 */                                 out.write("</option> ");
/*      */                               }
/*  507 */                               out.write("\n\t\t\t");
/*  508 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  509 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  513 */                           if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  514 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                           }
/*      */                           
/*  517 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  518 */                           out.write("\n\t\t\t");
/*      */                           
/*  520 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  521 */                           _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  522 */                           _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  523 */                           int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  524 */                           if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                             for (;;) {
/*  526 */                               out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  527 */                               out.print(action_haid);
/*  528 */                               out.print(returnpath);
/*  529 */                               out.write(34);
/*  530 */                               out.write(62);
/*  531 */                               out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  532 */                               out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                               
/*  534 */                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  535 */                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  536 */                               _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                               
/*  538 */                               _jspx_th_c_005fif_005f3.setTest("${isProfServer || isAdminServer}");
/*  539 */                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  540 */                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                 for (;;) {
/*  542 */                                   out.write("\n\t\t\t\t\t");
/*  543 */                                   if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  544 */                                     out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  545 */                                     out.print(action_haid);
/*  546 */                                     out.print(returnpath);
/*  547 */                                     out.write(34);
/*  548 */                                     out.write(62);
/*  549 */                                     out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  550 */                                     out.write("</option> ");
/*      */                                   }
/*  552 */                                   out.write("\n\t\t   \t\t");
/*  553 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  554 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  558 */                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  559 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                               }
/*      */                               
/*  562 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  563 */                               out.write("\n\t\t\t");
/*  564 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  565 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  569 */                           if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  570 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                           }
/*      */                           
/*  573 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  574 */                           out.write(10);
/*  575 */                           out.write(9);
/*  576 */                           out.write(9);
/*  577 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  578 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  582 */                       if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  583 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                       }
/*      */                       
/*  586 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  587 */                       out.write(10);
/*  588 */                       out.write(9);
/*  589 */                       out.write(9);
/*      */                       
/*  591 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  592 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  593 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/*  595 */                       _jspx_th_c_005fif_005f4.setTest("${!isAdminServer}");
/*  596 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  597 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/*  599 */                           out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  600 */                           out.print(action_haid);
/*  601 */                           out.print(returnpath);
/*  602 */                           out.write(34);
/*  603 */                           out.write(62);
/*  604 */                           out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  605 */                           out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  606 */                           out.print(action_haid);
/*  607 */                           out.print(returnpath);
/*  608 */                           out.write(34);
/*  609 */                           out.write(62);
/*  610 */                           out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  611 */                           out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/*  612 */                           out.print(action_haid);
/*  613 */                           out.print(returnpath);
/*  614 */                           out.write(34);
/*  615 */                           out.write(62);
/*  616 */                           out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  617 */                           out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                           
/*  619 */                           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  620 */                           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  621 */                           _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                           
/*  623 */                           _jspx_th_c_005fif_005f5.setTest("${!isCloudServer}");
/*  624 */                           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  625 */                           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                             for (;;) {
/*  627 */                               out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  628 */                               out.print(action_haid);
/*  629 */                               out.print(returnpath);
/*  630 */                               out.write(34);
/*  631 */                               out.write(62);
/*  632 */                               out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  633 */                               out.write("</option>\n\t   \t\t");
/*  634 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  635 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  639 */                           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/*  640 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                           }
/*      */                           
/*  643 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*  644 */                           out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/*  645 */                           out.print(action_haid);
/*  646 */                           out.print(returnpath);
/*  647 */                           out.write(34);
/*  648 */                           out.write(62);
/*  649 */                           out.print(FormatUtil.getString("am.container.action.createnew"));
/*  650 */                           out.write("</option>\n   \t\t");
/*  651 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  652 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  656 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  657 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/*  660 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  661 */                       out.write(10);
/*  662 */                       out.write(9);
/*  663 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  664 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  668 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  669 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/*  672 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  673 */                   out.write(10);
/*  674 */                   out.write(9);
/*      */                   
/*  676 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  677 */                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  678 */                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*  679 */                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  680 */                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                     for (;;) {
/*  682 */                       out.write(10);
/*      */                       
/*  684 */                       String redirectTo = null;
/*  685 */                       if (request.getParameter("redirectto") != null)
/*      */                       {
/*  687 */                         redirectTo = java.net.URLEncoder.encode(request.getParameter("redirectto"));
/*      */                       }
/*      */                       else
/*      */                       {
/*  691 */                         redirectTo = java.net.URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                       }
/*      */                       
/*  694 */                       out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/*  695 */                       out.print(action_haid);
/*  696 */                       out.write("&global=true");
/*  697 */                       out.print(returnpath);
/*  698 */                       out.write(34);
/*  699 */                       out.write(62);
/*  700 */                       out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  701 */                       out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/*  702 */                       out.print(action_haid);
/*  703 */                       out.write("&global=true");
/*  704 */                       out.print(returnpath);
/*  705 */                       out.write(34);
/*  706 */                       out.write(62);
/*  707 */                       out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  708 */                       out.write("</option>\n\t");
/*      */                       
/*  710 */                       org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/*  711 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  712 */                       _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                       
/*  714 */                       _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/*  715 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  716 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/*  718 */                           out.write(32);
/*  719 */                           out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/*  720 */                           out.print(action_haid);
/*  721 */                           out.write("&global=true");
/*  722 */                           out.print(returnpath);
/*  723 */                           out.write(34);
/*  724 */                           out.write(62);
/*  725 */                           out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  726 */                           out.write("</option>\n\t");
/*  727 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  728 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  732 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  733 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                       }
/*      */                       
/*  736 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  737 */                       out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/*  738 */                       out.print(action_haid);
/*  739 */                       out.write("&global=true");
/*  740 */                       out.print(returnpath);
/*  741 */                       out.write(34);
/*  742 */                       out.write(62);
/*  743 */                       out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/*  744 */                       out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/*  745 */                       out.print(action_haid);
/*  746 */                       out.write("&global=true");
/*  747 */                       out.print(returnpath);
/*  748 */                       out.write(34);
/*  749 */                       out.write(62);
/*  750 */                       out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/*  751 */                       out.write("</option>\n\t");
/*  752 */                       if ((!Constants.sqlManager) && ((!Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/*  753 */                         out.write(32);
/*  754 */                         out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/*  755 */                         out.print(action_haid);
/*  756 */                         out.print(returnpath);
/*  757 */                         out.write(34);
/*  758 */                         out.write(62);
/*  759 */                         out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  760 */                         out.write("</option>\n\t");
/*      */                       }
/*  762 */                       out.write(10);
/*  763 */                       out.write(9);
/*  764 */                       out.write(9);
/*  765 */                       out.write(10);
/*  766 */                       out.write(9);
/*  767 */                       if ((!Constants.isIt360) || (EnterpriseUtil.isProfEdition()) || (EnterpriseUtil.isAdminServer()))
/*      */                       {
/*  769 */                         out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/*  770 */                         out.print(action_haid);
/*  771 */                         out.write("&redirectTo=");
/*  772 */                         out.print(redirectTo);
/*  773 */                         out.write(34);
/*  774 */                         out.write(62);
/*  775 */                         out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  776 */                         out.write("</option> ");
/*      */                       }
/*      */                       
/*  779 */                       out.write("\n\t\n\t");
/*  780 */                       if ((!Constants.sqlManager) && ((!Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/*  781 */                         out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  782 */                         out.print(action_haid);
/*  783 */                         out.write("&global=true");
/*  784 */                         out.print(returnpath);
/*  785 */                         out.write("&ext=true\">");
/*  786 */                         out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  787 */                         out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/*  788 */                         out.print(action_haid);
/*  789 */                         out.print(returnpath);
/*  790 */                         out.write("&ext=true&global=true\">");
/*  791 */                         out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  792 */                         out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  793 */                         out.print(action_haid);
/*  794 */                         out.write("&global=true");
/*  795 */                         out.print(returnpath);
/*  796 */                         out.write("&ext=true\">");
/*  797 */                         out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  798 */                         out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/*  799 */                         out.print(action_haid);
/*  800 */                         out.print(returnpath);
/*  801 */                         out.write("&ext=true&global=true\">");
/*  802 */                         out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  803 */                         out.write("</option>\n\t ");
/*  804 */                       } else if (Constants.sqlManager) {
/*  805 */                         out.write(32);
/*  806 */                         out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  807 */                         out.print(action_haid);
/*  808 */                         out.write("&global=true");
/*  809 */                         out.print(returnpath);
/*  810 */                         out.write(34);
/*  811 */                         out.write(62);
/*  812 */                         out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  813 */                         out.write("</option>\n\t");
/*      */                       }
/*  815 */                       out.write(10);
/*  816 */                       out.write(9);
/*  817 */                       if ((!Constants.sqlManager) && ((!Constants.isIt360) || (!EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(Constants.getCategorytype()))) {
/*  818 */                         out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  819 */                         out.print(action_haid);
/*  820 */                         out.print(returnpath);
/*  821 */                         out.write(34);
/*  822 */                         out.write(62);
/*  823 */                         out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  824 */                         out.write("</option>\t\n\t");
/*      */                       }
/*  826 */                       out.write(10);
/*  827 */                       out.write(9);
/*  828 */                       out.write(32);
/*  829 */                       if (Constants.sqlManager) {
/*  830 */                         out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  831 */                         out.print(action_haid);
/*  832 */                         out.print(returnpath);
/*  833 */                         out.write(34);
/*  834 */                         out.write(62);
/*  835 */                         out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  836 */                         out.write("</option>\n\t");
/*      */                       }
/*  838 */                       out.write(10);
/*  839 */                       out.write(9);
/*  840 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  841 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  845 */                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  846 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                   }
/*      */                   
/*  849 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  850 */                   out.write(10);
/*  851 */                   out.write(9);
/*  852 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  853 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  857 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  858 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/*  861 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  862 */               out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */               
/*  864 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  865 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/*  866 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  868 */               _jspx_th_c_005fif_005f6.setTest("${param.global=='true'}");
/*  869 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/*  870 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/*  872 */                   out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/*  873 */                   out.write("<!--$Id$-->\n\n\n\n");
/*  874 */                   if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/*  876 */                   out.write("\n      \n\n");
/*      */                   
/*  878 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  879 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/*  880 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/*  882 */                   _jspx_th_c_005fif_005f7.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/*  883 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/*  884 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/*  886 */                       out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/*  887 */                       if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/*  889 */                       out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                       
/*  891 */                       IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  892 */                       _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/*  893 */                       _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/*  895 */                       _jspx_th_c_005fif_005f8.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/*  896 */                       int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/*  897 */                       if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                         for (;;) {
/*  899 */                           out.write("\n    \t");
/*      */                           
/*  901 */                           SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  902 */                           _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/*  903 */                           _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f8);
/*      */                           
/*  905 */                           _jspx_th_c_005fset_005f6.setVar("wizimage");
/*  906 */                           int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/*  907 */                           if (_jspx_eval_c_005fset_005f6 != 0) {
/*  908 */                             if (_jspx_eval_c_005fset_005f6 != 1) {
/*  909 */                               out = _jspx_page_context.pushBody();
/*  910 */                               _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/*  911 */                               _jspx_th_c_005fset_005f6.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  914 */                               out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/*  915 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/*  916 */                               out.write(" </b></font>\n    \t");
/*  917 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/*  918 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  921 */                             if (_jspx_eval_c_005fset_005f6 != 1) {
/*  922 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  925 */                           if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/*  926 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                           }
/*      */                           
/*  929 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/*  930 */                           out.write("\n    ");
/*  931 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/*  932 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  936 */                       if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/*  937 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                       }
/*      */                       
/*  940 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*  941 */                       out.write("\n    ");
/*      */                       
/*  943 */                       IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  944 */                       _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/*  945 */                       _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/*  947 */                       _jspx_th_c_005fif_005f9.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/*  948 */                       int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/*  949 */                       if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                         for (;;) {
/*  951 */                           out.write("\n    \t");
/*      */                           
/*  953 */                           SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  954 */                           _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/*  955 */                           _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f9);
/*      */                           
/*  957 */                           _jspx_th_c_005fset_005f7.setVar("wizimage");
/*  958 */                           int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/*  959 */                           if (_jspx_eval_c_005fset_005f7 != 0) {
/*  960 */                             if (_jspx_eval_c_005fset_005f7 != 1) {
/*  961 */                               out = _jspx_page_context.pushBody();
/*  962 */                               _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/*  963 */                               _jspx_th_c_005fset_005f7.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  966 */                               out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/*  967 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/*  968 */                               out.write("</font>  \t");
/*  969 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/*  970 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  973 */                             if (_jspx_eval_c_005fset_005f7 != 1) {
/*  974 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  977 */                           if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/*  978 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                           }
/*      */                           
/*  981 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/*  982 */                           out.write("\n    ");
/*  983 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/*  984 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  988 */                       if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/*  989 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                       }
/*      */                       
/*  992 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*  993 */                       out.write("      \n\t</td>\n    <td width=\"20%\">");
/*  994 */                       if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/*  996 */                       out.write("</td>  \n   \n");
/*      */                       
/*  998 */                       ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  999 */                       _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1000 */                       _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f7);
/* 1001 */                       int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1002 */                       if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                         for (;;) {
/* 1004 */                           out.write("\n    ");
/*      */                           
/* 1006 */                           WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1007 */                           _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1008 */                           _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                           
/* 1010 */                           _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 1011 */                           int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1012 */                           if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                             for (;;) {
/* 1014 */                               out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                               
/* 1016 */                               SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1017 */                               _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 1018 */                               _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                               
/* 1020 */                               _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 1021 */                               int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1022 */                               if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1023 */                                 if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1024 */                                   out = _jspx_page_context.pushBody();
/* 1025 */                                   _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 1026 */                                   _jspx_th_c_005fset_005f8.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1029 */                                   out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1030 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1031 */                                   out.write(" </b></font>\n    \t");
/* 1032 */                                   int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1033 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1036 */                                 if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1037 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1040 */                               if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1041 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                               }
/*      */                               
/* 1044 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1045 */                               out.write("\n   ");
/* 1046 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1047 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1051 */                           if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1052 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                           }
/*      */                           
/* 1055 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1056 */                           out.write("\n   ");
/*      */                           
/* 1058 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1059 */                           _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1060 */                           _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 1061 */                           int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1062 */                           if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                             for (;;) {
/* 1064 */                               out.write("  \n    \t\n\t\t");
/*      */                               
/* 1066 */                               SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1067 */                               _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 1068 */                               _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                               
/* 1070 */                               _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 1071 */                               int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 1072 */                               if (_jspx_eval_c_005fset_005f9 != 0) {
/* 1073 */                                 if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1074 */                                   out = _jspx_page_context.pushBody();
/* 1075 */                                   _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 1076 */                                   _jspx_th_c_005fset_005f9.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1079 */                                   out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1080 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1081 */                                   out.write(" </font>\n    \t");
/* 1082 */                                   int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 1083 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1086 */                                 if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1087 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1090 */                               if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 1091 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                               }
/*      */                               
/* 1094 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1095 */                               out.write("\n\t\n\t\t\n   ");
/* 1096 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1097 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1101 */                           if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1102 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                           }
/*      */                           
/* 1105 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1106 */                           out.write(10);
/* 1107 */                           out.write(32);
/* 1108 */                           out.write(32);
/* 1109 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1110 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1114 */                       if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1115 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                       }
/*      */                       
/* 1118 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1119 */                       out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 1120 */                       if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/* 1122 */                       out.write("\n    \t");
/* 1123 */                       if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/* 1125 */                       out.write("\n    \t\n    \t");
/* 1126 */                       if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/* 1128 */                       out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                       
/* 1130 */                       ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1131 */                       _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1132 */                       _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f7);
/* 1133 */                       int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1134 */                       if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                         for (;;) {
/* 1136 */                           out.write("\n       ");
/*      */                           
/* 1138 */                           WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1139 */                           _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1140 */                           _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                           
/* 1142 */                           _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 1143 */                           int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1144 */                           if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                             for (;;) {
/* 1146 */                               out.write("\n   \n   \t    \t");
/*      */                               
/* 1148 */                               SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1149 */                               _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1150 */                               _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                               
/* 1152 */                               _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 1153 */                               int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1154 */                               if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1155 */                                 if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1156 */                                   out = _jspx_page_context.pushBody();
/* 1157 */                                   _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 1158 */                                   _jspx_th_c_005fset_005f10.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1161 */                                   out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 1162 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1163 */                                   out.write(" </b></font>\n   \t    \t");
/* 1164 */                                   int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1165 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1168 */                                 if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1169 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1172 */                               if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1173 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                               }
/*      */                               
/* 1176 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1177 */                               out.write("\n       ");
/* 1178 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1179 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1183 */                           if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1184 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                           }
/*      */                           
/* 1187 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1188 */                           out.write("\n        ");
/*      */                           
/* 1190 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1191 */                           _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1192 */                           _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1193 */                           int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1194 */                           if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                             for (;;) {
/* 1196 */                               out.write("  \n   \t    \t");
/*      */                               
/* 1198 */                               SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1199 */                               _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 1200 */                               _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                               
/* 1202 */                               _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 1203 */                               int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 1204 */                               if (_jspx_eval_c_005fset_005f11 != 0) {
/* 1205 */                                 if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1206 */                                   out = _jspx_page_context.pushBody();
/* 1207 */                                   _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 1208 */                                   _jspx_th_c_005fset_005f11.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1211 */                                   out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1212 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1213 */                                   out.write(" </font>\n   \t    \t");
/* 1214 */                                   int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 1215 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1218 */                                 if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1219 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1222 */                               if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 1223 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                               }
/*      */                               
/* 1226 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1227 */                               out.write("\n   \t");
/* 1228 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1229 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1233 */                           if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1234 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                           }
/*      */                           
/* 1237 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1238 */                           out.write(10);
/* 1239 */                           out.write(32);
/* 1240 */                           out.write(32);
/* 1241 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1242 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1246 */                       if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1247 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                       }
/*      */                       
/* 1250 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1251 */                       out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 1252 */                       if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/* 1254 */                       out.write("\n       ");
/* 1255 */                       if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/* 1257 */                       out.write("\n       ");
/* 1258 */                       out.write("\n       \t");
/* 1259 */                       if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/* 1261 */                       out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                       
/* 1263 */                       IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1264 */                       _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1265 */                       _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/* 1267 */                       _jspx_th_c_005fif_005f14.setTest("${param.method=='getHAProfiles'}");
/* 1268 */                       int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1269 */                       if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                         for (;;) {
/* 1271 */                           out.write(10);
/* 1272 */                           out.write(9);
/*      */                           
/* 1274 */                           SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1275 */                           _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 1276 */                           _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f14);
/*      */                           
/* 1278 */                           _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 1279 */                           int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 1280 */                           if (_jspx_eval_c_005fset_005f12 != 0) {
/* 1281 */                             if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1282 */                               out = _jspx_page_context.pushBody();
/* 1283 */                               _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 1284 */                               _jspx_th_c_005fset_005f12.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1287 */                               out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 1288 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1289 */                               out.write(" </b></font>\n    \t");
/* 1290 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 1291 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1294 */                             if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1295 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1298 */                           if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 1299 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                           }
/*      */                           
/* 1302 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1303 */                           out.write(10);
/* 1304 */                           out.write(9);
/* 1305 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1306 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1310 */                       if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1311 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                       }
/*      */                       
/* 1314 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1315 */                       out.write(10);
/* 1316 */                       out.write(9);
/*      */                       
/* 1318 */                       IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1319 */                       _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 1320 */                       _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/* 1322 */                       _jspx_th_c_005fif_005f15.setTest("${param.method!='getHAProfiles'}");
/* 1323 */                       int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 1324 */                       if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                         for (;;) {
/* 1326 */                           out.write(10);
/* 1327 */                           out.write(9);
/*      */                           
/* 1329 */                           SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1330 */                           _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 1331 */                           _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f15);
/*      */                           
/* 1333 */                           _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 1334 */                           int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 1335 */                           if (_jspx_eval_c_005fset_005f13 != 0) {
/* 1336 */                             if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1337 */                               out = _jspx_page_context.pushBody();
/* 1338 */                               _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 1339 */                               _jspx_th_c_005fset_005f13.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1342 */                               out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1343 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1344 */                               out.write(" </font>\n    \t");
/* 1345 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 1346 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1349 */                             if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1350 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1353 */                           if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 1354 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                           }
/*      */                           
/* 1357 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1358 */                           out.write("\n\t\n\t");
/* 1359 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 1360 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1364 */                       if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 1365 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                       }
/*      */                       
/* 1368 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 1369 */                       out.write(10);
/* 1370 */                       if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/* 1372 */                       out.write("   \n ");
/* 1373 */                       if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/* 1375 */                       out.write(10);
/* 1376 */                       out.write(32);
/* 1377 */                       out.write(10);
/* 1378 */                       if (_jspx_meth_c_005fif_005f17(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                         return;
/* 1380 */                       out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 1381 */                       out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 1382 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1383 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1387 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1388 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 1391 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1392 */                   out.write(10);
/* 1393 */                   out.write(10);
/* 1394 */                   if (request.getAttribute("EmailForm") == null) {
/* 1395 */                     out.write(10);
/*      */                     
/* 1397 */                     MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1398 */                     _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 1399 */                     _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                     
/* 1401 */                     _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                     
/* 1403 */                     _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 1404 */                     int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 1405 */                     if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 1406 */                       String msg = null;
/* 1407 */                       if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1408 */                         out = _jspx_page_context.pushBody();
/* 1409 */                         _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 1410 */                         _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                       }
/* 1412 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/* 1414 */                         out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\"><bean:write name=\"msg\"  filter=\"false\"  /></td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 1415 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 1416 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/* 1417 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 1420 */                       if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1421 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 1424 */                     if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 1425 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                     }
/*      */                     
/* 1428 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */                   }
/* 1430 */                   out.write(32);
/*      */                   
/* 1432 */                   org.apache.struts.taglib.logic.MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (org.apache.struts.taglib.logic.MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(org.apache.struts.taglib.logic.MessagesPresentTag.class);
/* 1433 */                   _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 1434 */                   _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1436 */                   _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 1437 */                   int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 1438 */                   if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                     for (;;) {
/* 1440 */                       out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                       
/* 1442 */                       MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1443 */                       _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 1444 */                       _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                       
/* 1446 */                       _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                       
/* 1448 */                       _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 1449 */                       int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 1450 */                       if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 1451 */                         String msg = null;
/* 1452 */                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1453 */                           out = _jspx_page_context.pushBody();
/* 1454 */                           _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 1455 */                           _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                         }
/* 1457 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                         for (;;) {
/* 1459 */                           out.write("\n\t  <bean:write name=\"msg\" filter=\"false\" /><br>\n\t  ");
/* 1460 */                           int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 1461 */                           msg = (String)_jspx_page_context.findAttribute("msg");
/* 1462 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1465 */                         if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1466 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1469 */                       if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 1470 */                         this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                       }
/*      */                       
/* 1473 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 1474 */                       out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 1475 */                       int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 1476 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1480 */                   if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 1481 */                     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                   }
/*      */                   
/* 1484 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 1485 */                   out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 1486 */                   out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 1487 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1488 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1492 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1493 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 1496 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1497 */               out.write(10);
/* 1498 */               out.write(10);
/* 1499 */               int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 1500 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1504 */           if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 1505 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */           }
/*      */           
/* 1508 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 1509 */           out.write(10);
/* 1510 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1511 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1515 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1516 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/* 1519 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1520 */         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n    <tr>\n      <td width=\"72%\" height=\"31\" class=\"tableheading\" >&nbsp;\n        ");
/* 1521 */         out.print(FormatUtil.getString("am.webclient.newaction.mbeannone"));
/* 1522 */         out.write("</td>\n    </tr>\n  </table>\n<table valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  width=\"99%\" class=\"lrtbdarkborder\">\n  <tr>\n  <td colspan=\"3\" class=\"whitegrayborder\">&nbsp; ");
/* 1523 */         out.print(FormatUtil.getString("am.webclient.newaction.mbeannooperations"));
/* 1524 */         out.write(" <a href=\"javascript:void(0)\" onClick=\"javascript:history.back()\" class=\"staticlinks\">");
/* 1525 */         out.print(FormatUtil.getString("am.webclient.newaction.mbeanback"));
/* 1526 */         out.write("\t</a></td> \n  </tr>\n </table>\n");
/*      */       }
/* 1528 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1529 */         out = _jspx_out;
/* 1530 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1531 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1532 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1535 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1541 */     PageContext pageContext = _jspx_page_context;
/* 1542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1544 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1545 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1546 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1548 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 1549 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1550 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1552 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 1553 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1554 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1558 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1559 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1560 */       return true;
/*      */     }
/* 1562 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1563 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1568 */     PageContext pageContext = _jspx_page_context;
/* 1569 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1571 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1572 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 1573 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1575 */     _jspx_th_c_005fif_005f2.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 1576 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 1577 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 1579 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 1580 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 1581 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1585 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 1586 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1587 */       return true;
/*      */     }
/* 1589 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1590 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1595 */     PageContext pageContext = _jspx_page_context;
/* 1596 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1598 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 1599 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 1600 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 1602 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 1603 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 1605 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 1606 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 1608 */           out.write(" \n      ");
/* 1609 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 1610 */             return true;
/* 1611 */           out.write(32);
/* 1612 */           out.write(10);
/* 1613 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 1614 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1618 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 1619 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1622 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 1623 */         out = _jspx_page_context.popBody(); }
/* 1624 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1626 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 1627 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 1629 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 1634 */     PageContext pageContext = _jspx_page_context;
/* 1635 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1637 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 1638 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 1639 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 1641 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 1643 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 1644 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 1645 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 1646 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 1647 */       return true;
/*      */     }
/* 1649 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 1650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1655 */     PageContext pageContext = _jspx_page_context;
/* 1656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1658 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 1659 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 1660 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1662 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 1664 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 1666 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 1667 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 1668 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 1669 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 1670 */       return true;
/*      */     }
/* 1672 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 1673 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1678 */     PageContext pageContext = _jspx_page_context;
/* 1679 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1681 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 1682 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1683 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1685 */     _jspx_th_c_005fout_005f0.setValue("${wizimage}");
/*      */     
/* 1687 */     _jspx_th_c_005fout_005f0.setEscapeXml("false");
/* 1688 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1689 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1690 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1691 */       return true;
/*      */     }
/* 1693 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1699 */     PageContext pageContext = _jspx_page_context;
/* 1700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1702 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1703 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 1704 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1706 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 1707 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 1708 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 1710 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 1711 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 1712 */           return true;
/* 1713 */         out.write("&wiz=true\">\n    ");
/* 1714 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 1715 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1719 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 1720 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1721 */       return true;
/*      */     }
/* 1723 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1724 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1729 */     PageContext pageContext = _jspx_page_context;
/* 1730 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1732 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1733 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1734 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 1736 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 1737 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1738 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1739 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1740 */       return true;
/*      */     }
/* 1742 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1743 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1748 */     PageContext pageContext = _jspx_page_context;
/* 1749 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1751 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 1752 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1753 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1755 */     _jspx_th_c_005fout_005f2.setValue("${wizimage}");
/*      */     
/* 1757 */     _jspx_th_c_005fout_005f2.setEscapeXml("false");
/* 1758 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1759 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1760 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1761 */       return true;
/*      */     }
/* 1763 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1769 */     PageContext pageContext = _jspx_page_context;
/* 1770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1772 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1773 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 1774 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1776 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 1777 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 1778 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 1780 */         out.write("\n    \t</a>\n    \t");
/* 1781 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 1782 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1786 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 1787 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1788 */       return true;
/*      */     }
/* 1790 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1791 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1796 */     PageContext pageContext = _jspx_page_context;
/* 1797 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1799 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1800 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 1801 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1803 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 1804 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 1805 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 1807 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 1808 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 1809 */           return true;
/* 1810 */         out.write("&wiz=true\">\n       ");
/* 1811 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 1812 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1816 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 1817 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 1818 */       return true;
/*      */     }
/* 1820 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 1821 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1826 */     PageContext pageContext = _jspx_page_context;
/* 1827 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1829 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1830 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1831 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 1833 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 1834 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1835 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1836 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1837 */       return true;
/*      */     }
/* 1839 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1845 */     PageContext pageContext = _jspx_page_context;
/* 1846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1848 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 1849 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1850 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1852 */     _jspx_th_c_005fout_005f4.setValue("${wizimage}");
/*      */     
/* 1854 */     _jspx_th_c_005fout_005f4.setEscapeXml("false");
/* 1855 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1856 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1857 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1858 */       return true;
/*      */     }
/* 1860 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1866 */     PageContext pageContext = _jspx_page_context;
/* 1867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1869 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1870 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1871 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1873 */     _jspx_th_c_005fif_005f13.setTest("${wizimage=='/images/new_high.gif'}");
/* 1874 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1875 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 1877 */         out.write("\n       \t</a>\n       \t");
/* 1878 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1879 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1883 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1884 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1885 */       return true;
/*      */     }
/* 1887 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1893 */     PageContext pageContext = _jspx_page_context;
/* 1894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1896 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1897 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 1898 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1900 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 1901 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 1902 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 1904 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 1905 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 1906 */           return true;
/* 1907 */         out.write("&wiz=true\">\n ");
/* 1908 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 1909 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1913 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 1914 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 1915 */       return true;
/*      */     }
/* 1917 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 1918 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1923 */     PageContext pageContext = _jspx_page_context;
/* 1924 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1926 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1927 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1928 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 1930 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 1931 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1932 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1933 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1934 */       return true;
/*      */     }
/* 1936 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1937 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1942 */     PageContext pageContext = _jspx_page_context;
/* 1943 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1945 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 1946 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1947 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1949 */     _jspx_th_c_005fout_005f6.setValue("${wizimage}");
/*      */     
/* 1951 */     _jspx_th_c_005fout_005f6.setEscapeXml("false");
/* 1952 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1953 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1954 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1955 */       return true;
/*      */     }
/* 1957 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1963 */     PageContext pageContext = _jspx_page_context;
/* 1964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1966 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1967 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 1968 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 1970 */     _jspx_th_c_005fif_005f17.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 1971 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 1972 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 1974 */         out.write("\t    \n    </a>\n ");
/* 1975 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 1976 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1980 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 1981 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1982 */       return true;
/*      */     }
/* 1984 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1985 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\mopaction_005fnombeans_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */