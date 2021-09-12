/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.tags.EnterpriseAdminLink;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import java.io.IOException;
/*      */ import java.util.Map;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParamTag;
/*      */ 
/*      */ public final class EnterpriseAdmin_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   27 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */   private static Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*   43 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   47 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   48 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   49 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   50 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   51 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   52 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   53 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   54 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   58 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.release();
/*   59 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.release();
/*   60 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   61 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/*   62 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/*   63 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*   70 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   73 */     JspWriter out = null;
/*   74 */     Object page = this;
/*   75 */     JspWriter _jspx_out = null;
/*   76 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   80 */       response.setContentType("text/html");
/*   81 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   83 */       _jspx_page_context = pageContext;
/*   84 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*   85 */       ServletConfig config = pageContext.getServletConfig();
/*   86 */       session = pageContext.getSession();
/*   87 */       out = pageContext.getOut();
/*   88 */       _jspx_out = out;
/*      */       
/*   90 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n<!--ME-Solutions Standalone MGStatusview Alert starts here -->\n\n\n\n<!--ME-Solutions Standalone MGStatusview Alert ends here -->\n\n<body >\n\n<style type=\"text/css\">\n\na.no-bg-change:hover {background-color: transparent; border:none; padding:4px 4px 4px 4px;  }\n\n\n</style>\n\n");
/*      */       
/*   92 */       request.setAttribute("enableUpdateOperation", Boolean.valueOf(!com.adventnet.appmanager.util.Constants.isPrivilegedUser(request)));
/*   93 */       boolean isdelegatedAdmin = DBUtil.isDelegatedAdmin(request.getRemoteUser());
/*      */       
/*   95 */       out.write("\n<div class=\"admin-content\">\n\n<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  \t<tr>\n    \t<td width=\"100%\"  class=\"tableheadingbborder\">");
/*   96 */       out.print(FormatUtil.getString("am.webclient.admintab.monitors"));
/*   97 */       out.write("</td>\n  \t</tr>\n\n\t<tr>\n\t\t<td class=\"admin-bg\" >\n\t\t\t<table  width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<br>\n\t\t\t\t<tr>\n          \t\t\t<td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td>\n      \t\t\t</tr>      \n        \t\t<tr>\n        \t        <td width=\"15%\" align=\"center\">\n\t\t                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t                      <tr>\n\t\t                        ");
/*   98 */       if (!PluginUtil.isPlugin()) {
/*   99 */         out.write("\n\t\t                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999\" class=\"no-bg-change\"><img src=\"/images/icon_admin_discover.gif\" vspace=\"5\" border=\"0\"  style=\"position:relative; top:4px;\"></a></td>\n\t\t                        <td align=\"left\" style=\"white-space:nowrap;\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999\">");
/*  100 */         out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  101 */         out.write("</a> <span class=\"bodytext\">|</span> <a style=\"white-space:nowrap;\" href=\"javascript:void(0)\" onClick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" style=\"position:relative;\">");
/*  102 */         out.print(FormatUtil.getString("am.webclient.admin.bulkimport.text"));
/*  103 */         out.write("</a></td>\n\t\t                        ");
/*      */       }
/*      */       else {
/*  106 */         out.write("\n\t\t                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999\" class=\"no-bg-change\"><img src=\"/images/icon_admin_discover.gif\" vspace=\"5\" border=\"0\"  style=\"position:relative; top:4px;\"></a></td>\n\t\t                        <td align=\"left\" style=\"white-space:nowrap;\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999\">");
/*  107 */         out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  108 */         out.write("</a> <span class=\"bodytext\">|</span> <a style=\"white-space:nowrap;\" href=\"/admin/createapplication.do?method=createapp&grouptype=1\">");
/*  109 */         out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/*  110 */         out.write("</a></td>\n\t\t                        ");
/*      */       }
/*  112 */       out.write("\n\t\t                      </tr>\n\t\t                    </table>\n\t\t         \t</td>\n        \n        \t\t\t");
/*      */       
/*  114 */       String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*  115 */       if (!usertype.equals("F"))
/*      */       {
/*  117 */         if (!isdelegatedAdmin)
/*      */         {
/*  119 */           out.write("\n                  <td width=\"15%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance\" class=\"no-bg-change\"><img src=\"/images/icon_admin_perfpolling.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance\" >");
/*  120 */           out.print(FormatUtil.getString("am.webclient.admin.performancepoll.link"));
/*  121 */           out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */         }
/*      */       }
/*  124 */       out.write("\n                   \t\n                   \t<td width=\"15%\" align=\"center\">\n\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  125 */       if (_jspx_meth_ea_005feeadminlink_005f0(_jspx_page_context))
/*      */         return;
/*  127 */       out.write("</td>");
/*  128 */       out.write("\n\t\t\t\t\t\t<td align=\"left\">");
/*      */       
/*  130 */       EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f1 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  131 */       _jspx_th_ea_005feeadminlink_005f1.setPageContext(_jspx_page_context);
/*  132 */       _jspx_th_ea_005feeadminlink_005f1.setParent(null);
/*      */       
/*  134 */       _jspx_th_ea_005feeadminlink_005f1.setHref("/downTimeScheduler.do?method=maintenanceTaskListView");
/*  135 */       int _jspx_eval_ea_005feeadminlink_005f1 = _jspx_th_ea_005feeadminlink_005f1.doStartTag();
/*  136 */       if (_jspx_eval_ea_005feeadminlink_005f1 != 0) {
/*  137 */         if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  138 */           out = _jspx_page_context.pushBody();
/*  139 */           _jspx_th_ea_005feeadminlink_005f1.setBodyContent((BodyContent)out);
/*  140 */           _jspx_th_ea_005feeadminlink_005f1.doInitBody();
/*      */         }
/*      */         for (;;) {
/*  143 */           out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  144 */           int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f1.doAfterBody();
/*  145 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*  148 */         if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  149 */           out = _jspx_page_context.popBody();
/*      */         }
/*      */       }
/*  152 */       if (_jspx_th_ea_005feeadminlink_005f1.doEndTag() == 5) {
/*  153 */         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f1);
/*      */       }
/*      */       else {
/*  156 */         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f1);
/*  157 */         out.write("</td>");
/*  158 */         out.write("\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\n\t\t\t\t\t<td width=\"15%\" align=\"center\">\n                \t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                        \t<tr>\n\t\t                  \t\t<td width=\"10%\" align=\"right\" onClick=\"return credentialManagerCheck()\"><a href='/credentialManager.do?method=showCredentialManager' class=\"no-bg-change\"><img src=\"/images/credentialManager.png\" vspace=\"5\" border=\"0\"></a></td>\n\t\t                  \t\t<td align=\"left\" onClick=\"return credentialManagerCheck()\"><a href='/credentialManager.do?method=showCredentialManager'><span style=\"white-space:nowrap;\">");
/*  159 */         out.print(FormatUtil.getString("Credential Manager"));
/*  160 */         out.write("</span></a></td>\n\t               \t\t\t</tr>\n             \t\t\t</table>\n            \t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n</table>\n\n<br>\n\n<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t<tr>\n\t\t<td width=\"100%\" class=\"tableheadingbborder\">");
/*  161 */         out.print(FormatUtil.getString("am.webclient.admin.appmanagersettings.table.heading", new String[] { OEMUtil.getOEMString("product.name") }));
/*  162 */         out.write("</td>\n\t</tr>\n\t<tr>\n    <td class=\"admin-bg\">\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<br>\n        <tr>\n\t        <td width=\"15%\" align=\"center\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t<tr>\n\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  163 */         if (_jspx_meth_ea_005feeadminlink_005f2(_jspx_page_context))
/*      */           return;
/*  165 */         out.write("</td> ");
/*  166 */         out.write("\n\t\t\t\t<td align=\"left\"> ");
/*      */         
/*  168 */         EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f3 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  169 */         _jspx_th_ea_005feeadminlink_005f3.setPageContext(_jspx_page_context);
/*  170 */         _jspx_th_ea_005feeadminlink_005f3.setParent(null);
/*      */         
/*  172 */         _jspx_th_ea_005feeadminlink_005f3.setHref("/alertEscalation.do?method=showRules");
/*  173 */         int _jspx_eval_ea_005feeadminlink_005f3 = _jspx_th_ea_005feeadminlink_005f3.doStartTag();
/*  174 */         if (_jspx_eval_ea_005feeadminlink_005f3 != 0) {
/*  175 */           if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/*  176 */             out = _jspx_page_context.pushBody();
/*  177 */             _jspx_th_ea_005feeadminlink_005f3.setBodyContent((BodyContent)out);
/*  178 */             _jspx_th_ea_005feeadminlink_005f3.doInitBody();
/*      */           }
/*      */           for (;;) {
/*  181 */             out.print(FormatUtil.getString("am.webclient.admin.alertescalation.link"));
/*  182 */             out.write(32);
/*  183 */             int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f3.doAfterBody();
/*  184 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*  187 */           if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/*  188 */             out = _jspx_page_context.popBody();
/*      */           }
/*      */         }
/*  191 */         if (_jspx_th_ea_005feeadminlink_005f3.doEndTag() == 5) {
/*  192 */           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f3);
/*      */         }
/*      */         else {
/*  195 */           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f3);
/*  196 */           out.write("</td>  ");
/*  197 */           out.write("\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\t\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t");
/*  198 */           if (!isdelegatedAdmin) {
/*  199 */             out.write("\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t<tr>\n\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  200 */             if (_jspx_meth_ea_005feeadminlink_005f4(_jspx_page_context))
/*      */               return;
/*  202 */             out.write("</td> ");
/*  203 */             out.write("\n\t\t\t\t<td align=\"left\">");
/*      */             
/*  205 */             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f5 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  206 */             _jspx_th_ea_005feeadminlink_005f5.setPageContext(_jspx_page_context);
/*  207 */             _jspx_th_ea_005feeadminlink_005f5.setParent(null);
/*      */             
/*  209 */             _jspx_th_ea_005feeadminlink_005f5.setHref("/adminAction.do?method=showManagedServers");
/*  210 */             int _jspx_eval_ea_005feeadminlink_005f5 = _jspx_th_ea_005feeadminlink_005f5.doStartTag();
/*  211 */             if (_jspx_eval_ea_005feeadminlink_005f5 != 0) {
/*  212 */               if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/*  213 */                 out = _jspx_page_context.pushBody();
/*  214 */                 _jspx_th_ea_005feeadminlink_005f5.setBodyContent((BodyContent)out);
/*  215 */                 _jspx_th_ea_005feeadminlink_005f5.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  218 */                 out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/*  219 */                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f5.doAfterBody();
/*  220 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  223 */               if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/*  224 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  227 */             if (_jspx_th_ea_005feeadminlink_005f5.doEndTag() == 5) {
/*  228 */               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f5); return;
/*      */             }
/*      */             
/*  231 */             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f5);
/*  232 */             out.write(" </td> ");
/*  233 */             out.write("\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t");
/*      */           }
/*  235 */           out.write("\n\t\t\t</td>\n\t\t\t\n\t\t\t");
/*      */           
/*  237 */           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  238 */           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  239 */           _jspx_th_c_005fif_005f0.setParent(null);
/*      */           
/*  241 */           _jspx_th_c_005fif_005f0.setTest("${enableUpdateOperation}");
/*  242 */           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  243 */           if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */             for (;;) {
/*  245 */               out.write("\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t  <tr>\n\t\t\t\t    <td width=\"10%\" align=\"right\">");
/*  246 */               if (_jspx_meth_ea_005feeadminlink_005f6(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                 return;
/*  248 */               out.write("</td> ");
/*  249 */               out.write("\n\t\t\t\t\t<td align=\"left\">");
/*      */               
/*  251 */               EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f7 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  252 */               _jspx_th_ea_005feeadminlink_005f7.setPageContext(_jspx_page_context);
/*  253 */               _jspx_th_ea_005feeadminlink_005f7.setParent(_jspx_th_c_005fif_005f0);
/*      */               
/*  255 */               _jspx_th_ea_005feeadminlink_005f7.setHref("/jsp/ProxyConfiguration.jsp");
/*  256 */               int _jspx_eval_ea_005feeadminlink_005f7 = _jspx_th_ea_005feeadminlink_005f7.doStartTag();
/*  257 */               if (_jspx_eval_ea_005feeadminlink_005f7 != 0) {
/*  258 */                 if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/*  259 */                   out = _jspx_page_context.pushBody();
/*  260 */                   _jspx_th_ea_005feeadminlink_005f7.setBodyContent((BodyContent)out);
/*  261 */                   _jspx_th_ea_005feeadminlink_005f7.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  264 */                   out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  265 */                   int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f7.doAfterBody();
/*  266 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  269 */                 if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/*  270 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  273 */               if (_jspx_th_ea_005feeadminlink_005f7.doEndTag() == 5) {
/*  274 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f7); return;
/*      */               }
/*      */               
/*  277 */               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f7);
/*  278 */               out.write("</td> ");
/*  279 */               out.write("\n\t\t\t\t  </tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\t");
/*  280 */               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  281 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  285 */           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  286 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */           }
/*      */           else {
/*  289 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  290 */             out.write("\t\n\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t  <tr>\n\t\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  291 */             if (_jspx_meth_ea_005feeadminlink_005f8(_jspx_page_context))
/*      */               return;
/*  293 */             out.write("</td> ");
/*  294 */             out.write("\n\t\t\t\t\t<td align=\"left\">");
/*      */             
/*  296 */             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f9 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  297 */             _jspx_th_ea_005feeadminlink_005f9.setPageContext(_jspx_page_context);
/*  298 */             _jspx_th_ea_005feeadminlink_005f9.setParent(null);
/*      */             
/*  300 */             _jspx_th_ea_005feeadminlink_005f9.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*  301 */             int _jspx_eval_ea_005feeadminlink_005f9 = _jspx_th_ea_005feeadminlink_005f9.doStartTag();
/*  302 */             if (_jspx_eval_ea_005feeadminlink_005f9 != 0) {
/*  303 */               if (_jspx_eval_ea_005feeadminlink_005f9 != 1) {
/*  304 */                 out = _jspx_page_context.pushBody();
/*  305 */                 _jspx_th_ea_005feeadminlink_005f9.setBodyContent((BodyContent)out);
/*  306 */                 _jspx_th_ea_005feeadminlink_005f9.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  309 */                 out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  310 */                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f9.doAfterBody();
/*  311 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  314 */               if (_jspx_eval_ea_005feeadminlink_005f9 != 1) {
/*  315 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  318 */             if (_jspx_th_ea_005feeadminlink_005f9.doEndTag() == 5) {
/*  319 */               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f9);
/*      */             }
/*      */             else {
/*  322 */               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f9);
/*  323 */               out.write("</td> ");
/*  324 */               out.write("\n\t\t\t\t  </tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\t\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t  <tr>\n\t\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  325 */               if (_jspx_meth_ea_005feeadminlink_005f10(_jspx_page_context))
/*      */                 return;
/*  327 */               out.write("</td> ");
/*  328 */               out.write("\n\t\t\t\t\t<td align=\"left\">");
/*      */               
/*  330 */               EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f11 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  331 */               _jspx_th_ea_005feeadminlink_005f11.setPageContext(_jspx_page_context);
/*  332 */               _jspx_th_ea_005feeadminlink_005f11.setParent(null);
/*      */               
/*  334 */               _jspx_th_ea_005feeadminlink_005f11.setHref("/adminAction.do?method=showServerSettingsConfiguration&typetoshow=general");
/*  335 */               int _jspx_eval_ea_005feeadminlink_005f11 = _jspx_th_ea_005feeadminlink_005f11.doStartTag();
/*  336 */               if (_jspx_eval_ea_005feeadminlink_005f11 != 0) {
/*  337 */                 if (_jspx_eval_ea_005feeadminlink_005f11 != 1) {
/*  338 */                   out = _jspx_page_context.pushBody();
/*  339 */                   _jspx_th_ea_005feeadminlink_005f11.setBodyContent((BodyContent)out);
/*  340 */                   _jspx_th_ea_005feeadminlink_005f11.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  343 */                   out.print(FormatUtil.getString("am.webclient.admin.serversettings.link"));
/*  344 */                   int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f11.doAfterBody();
/*  345 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  348 */                 if (_jspx_eval_ea_005feeadminlink_005f11 != 1) {
/*  349 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  352 */               if (_jspx_th_ea_005feeadminlink_005f11.doEndTag() == 5) {
/*  353 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f11);
/*      */               }
/*      */               else {
/*  356 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f11);
/*  357 */                 out.write("</td> ");
/*  358 */                 out.write("\n\t\t\t\t  </tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\n\t\t");
/*  359 */                 if (!isdelegatedAdmin) {
/*  360 */                   out.write("\n\t\t<tr>\n\t\t\t");
/*  361 */                   if (!PluginUtil.isPlugin()) {
/*  362 */                     out.write("\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t  <tr>\n\t\t\t\t    <td width=\"10%\" align=\"right\">");
/*  363 */                     if (_jspx_meth_ea_005feeadminlink_005f12(_jspx_page_context))
/*      */                       return;
/*  365 */                     out.write("</td> ");
/*  366 */                     out.write("\n\t\t\t\t    <td align=\"left\">");
/*      */                     
/*  368 */                     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f13 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  369 */                     _jspx_th_ea_005feeadminlink_005f13.setPageContext(_jspx_page_context);
/*  370 */                     _jspx_th_ea_005feeadminlink_005f13.setParent(null);
/*      */                     
/*  372 */                     _jspx_th_ea_005feeadminlink_005f13.setHref("/admin/userconfiguration.do?method=showUsers");
/*  373 */                     int _jspx_eval_ea_005feeadminlink_005f13 = _jspx_th_ea_005feeadminlink_005f13.doStartTag();
/*  374 */                     if (_jspx_eval_ea_005feeadminlink_005f13 != 0) {
/*  375 */                       if (_jspx_eval_ea_005feeadminlink_005f13 != 1) {
/*  376 */                         out = _jspx_page_context.pushBody();
/*  377 */                         _jspx_th_ea_005feeadminlink_005f13.setBodyContent((BodyContent)out);
/*  378 */                         _jspx_th_ea_005feeadminlink_005f13.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  381 */                         out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/*  382 */                         out.write(32);
/*  383 */                         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f13.doAfterBody();
/*  384 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  387 */                       if (_jspx_eval_ea_005feeadminlink_005f13 != 1) {
/*  388 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  391 */                     if (_jspx_th_ea_005feeadminlink_005f13.doEndTag() == 5) {
/*  392 */                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f13); return;
/*      */                     }
/*      */                     
/*  395 */                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f13);
/*  396 */                     out.write("</td> ");
/*  397 */                     out.write("\n\t\t\t\t  </tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\t");
/*      */                   }
/*  399 */                   out.write("\n\t\t\t\n\t\t\t\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t  <tr>\n\t\t\t\t    <td width=\"10%\" align=\"right\">");
/*  400 */                   if (_jspx_meth_ea_005feeadminlink_005f14(_jspx_page_context))
/*      */                     return;
/*  402 */                   out.write("</td> ");
/*  403 */                   out.write("\n\t\t\t\t    <td align=\"left\">");
/*      */                   
/*  405 */                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f15 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  406 */                   _jspx_th_ea_005feeadminlink_005f15.setPageContext(_jspx_page_context);
/*  407 */                   _jspx_th_ea_005feeadminlink_005f15.setParent(null);
/*      */                   
/*  409 */                   _jspx_th_ea_005feeadminlink_005f15.setHref("/adminAction.do?method=showMailServerConfiguration&admin=true");
/*  410 */                   int _jspx_eval_ea_005feeadminlink_005f15 = _jspx_th_ea_005feeadminlink_005f15.doStartTag();
/*  411 */                   if (_jspx_eval_ea_005feeadminlink_005f15 != 0) {
/*  412 */                     if (_jspx_eval_ea_005feeadminlink_005f15 != 1) {
/*  413 */                       out = _jspx_page_context.pushBody();
/*  414 */                       _jspx_th_ea_005feeadminlink_005f15.setBodyContent((BodyContent)out);
/*  415 */                       _jspx_th_ea_005feeadminlink_005f15.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/*  418 */                       out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  419 */                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f15.doAfterBody();
/*  420 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  423 */                     if (_jspx_eval_ea_005feeadminlink_005f15 != 1) {
/*  424 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  427 */                   if (_jspx_th_ea_005feeadminlink_005f15.doEndTag() == 5) {
/*  428 */                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f15); return;
/*      */                   }
/*      */                   
/*  431 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f15);
/*  432 */                   out.write("</td> ");
/*  433 */                   out.write("\n\t\t\t\t  </tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t\t");
/*  434 */                   if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/*  435 */                     out.write("\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t  <tr>\n\t\t\t\t    <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\"  class=\"no-bg-change\"><img src=\"/images/sms-alert-icon.gif\"  vspace=\"3\" border=\"\"></a></td>\n\t\t\t\t    <td align=\"left\"><a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" >");
/*  436 */                     out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  437 */                     out.write("</a></td>\n\t\t\t\t  </tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\t");
/*      */                   }
/*  439 */                   out.write("\n\t\t\t\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t <tr>\n\t\t\t\t\t   <td width=\"10%\" align=\"right\"><a  href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=logging\" title='");
/*  440 */                   out.print(FormatUtil.getString("Logging"));
/*  441 */                   out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_admin_logging.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t\t\t<td align=\"left\"><a  style=\"white-space:nowrap;\"  href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=logging\"> ");
/*  442 */                   out.print(FormatUtil.getString("am.webclient.global.logging.text"));
/*  443 */                   out.write("</a></td>\n\t\t\t\t\t </tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\n\t\t");
/*  444 */                   if ((!OEMUtil.isRemove("am.addonproducts.remove")) && (!isdelegatedAdmin)) {
/*  445 */                     out.write("\n                  <td width=\"15%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&admin=true\"  class=\"no-bg-change\"><img src=\"/images/cfg-servicedesk.gif\" border=\"0\" vspace=\"5\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&admin=true\">");
/*  446 */                     out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/*  447 */                     out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                   }
/*  449 */                   out.write("\n\t\t</tr>\n\t\t\n\t\t<tr>\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t");
/*  450 */                   if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  451 */                     out.write("\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t<tr>\n\t\t\t\t\t  <td width=\"10%\" align=\"right\"><a href='javascript:void(0)' onClick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=600,height=420, scrollbars=yes')\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_personalize.gif\" vspace=\"5\" border=\"\"></a></td>\n\t\t\t\t\t  <td align=\"left\"><a href='javascript:void(0)'  onClick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=600,height=420, scrollbars=yes')\" >");
/*  452 */                     out.print(FormatUtil.getString("am.webclient.admin.personalize.link"));
/*  453 */                     out.write("</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t");
/*      */                   }
/*      */                   
/*  456 */                   out.write("\n\t\t\t</td>\n\t\t\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t");
/*  457 */                   if (!PluginUtil.isPlugin()) {
/*  458 */                     out.write("\n\t\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t");
/*  459 */                     if ((request.getRemoteUser() != null) && (!request.getRemoteUser().equals("systemadmin_enterprise"))) {
/*  460 */                       out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"10%\" align=\"right\"><a href=\"#\" onClick=\"MM_openBrWindow('/webclient/common/jsp/registerLicence.jsp','Register','width=650,height=520, scrollbars=yes')\"  class=\"no-bg-change\"><img src=\"/images/Register.gif\"  vspace=\"2\" border=\"\"></a></td>\n\t\t\t\t\t<td align=\"left\"><a  href=\"#\" onClick=\"MM_openBrWindow('/webclient/common/jsp/registerLicence.jsp','Register','width=650,height=520, scrollbars=yes')\">");
/*  461 */                       out.print(FormatUtil.getString("am.webclient.admin.license.link"));
/*  462 */                       out.write("</a>\n\t\t\t\t\t");
/*      */                     } else {
/*  464 */                       out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  465 */                       if (_jspx_meth_ea_005feeadminlink_005f16(_jspx_page_context))
/*      */                         return;
/*  467 */                       out.write("</td> ");
/*  468 */                       out.write("\n\t\t\t\t\t<td align=\"left\">");
/*      */                       
/*  470 */                       EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f17 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  471 */                       _jspx_th_ea_005feeadminlink_005f17.setPageContext(_jspx_page_context);
/*  472 */                       _jspx_th_ea_005feeadminlink_005f17.setParent(null);
/*      */                       
/*  474 */                       _jspx_th_ea_005feeadminlink_005f17.setHref("/webclient/common/jsp/registerLicence.jsp");
/*  475 */                       int _jspx_eval_ea_005feeadminlink_005f17 = _jspx_th_ea_005feeadminlink_005f17.doStartTag();
/*  476 */                       if (_jspx_eval_ea_005feeadminlink_005f17 != 0) {
/*  477 */                         if (_jspx_eval_ea_005feeadminlink_005f17 != 1) {
/*  478 */                           out = _jspx_page_context.pushBody();
/*  479 */                           _jspx_th_ea_005feeadminlink_005f17.setBodyContent((BodyContent)out);
/*  480 */                           _jspx_th_ea_005feeadminlink_005f17.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/*  483 */                           out.print(FormatUtil.getString("am.webclient.admin.license.link"));
/*  484 */                           int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f17.doAfterBody();
/*  485 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  488 */                         if (_jspx_eval_ea_005feeadminlink_005f17 != 1) {
/*  489 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/*  492 */                       if (_jspx_th_ea_005feeadminlink_005f17.doEndTag() == 5) {
/*  493 */                         this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f17); return;
/*      */                       }
/*      */                       
/*  496 */                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f17);
/*  497 */                       out.write("</td> ");
/*  498 */                       out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                     }
/*  500 */                     out.write("\n\t\t\t\t\t</table>\n\t\t\t\t");
/*      */                   }
/*  502 */                   out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t");
/*      */                 }
/*  504 */                 out.write("\n\n\t\t<!--ME-Solutions Standalone MGStatusview Alert entries starts here -->\n\n\t\t");
/*      */                 
/*  506 */                 String mgStatusViews = DBUtil.getServerConfigValue("am.admin.mgstatusview.enabled");
/*  507 */                 if ((mgStatusViews != null) && (mgStatusViews.equals("true")))
/*      */                 {
/*      */ 
/*      */ 
/*  511 */                   out.write("\n\n\t\t</tr>\n\n\n\t\t<tr>\n\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t<tr>\n\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  512 */                   if (_jspx_meth_ea_005feeadminlink_005f18(_jspx_page_context))
/*      */                     return;
/*  514 */                   out.write("</td>");
/*  515 */                   out.write("\n\t\t\t\t<td align=\"left\">");
/*      */                   
/*  517 */                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f19 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  518 */                   _jspx_th_ea_005feeadminlink_005f19.setPageContext(_jspx_page_context);
/*  519 */                   _jspx_th_ea_005feeadminlink_005f19.setParent(null);
/*      */                   
/*  521 */                   _jspx_th_ea_005feeadminlink_005f19.setHref("/jsp/AllViews.jsp");
/*  522 */                   int _jspx_eval_ea_005feeadminlink_005f19 = _jspx_th_ea_005feeadminlink_005f19.doStartTag();
/*  523 */                   if (_jspx_eval_ea_005feeadminlink_005f19 != 0) {
/*  524 */                     if (_jspx_eval_ea_005feeadminlink_005f19 != 1) {
/*  525 */                       out = _jspx_page_context.pushBody();
/*  526 */                       _jspx_th_ea_005feeadminlink_005f19.setBodyContent((BodyContent)out);
/*  527 */                       _jspx_th_ea_005feeadminlink_005f19.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/*  530 */                       out.print(FormatUtil.getString("am.webclient.alertviews.title"));
/*  531 */                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f19.doAfterBody();
/*  532 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  535 */                     if (_jspx_eval_ea_005feeadminlink_005f19 != 1) {
/*  536 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  539 */                   if (_jspx_th_ea_005feeadminlink_005f19.doEndTag() == 5) {
/*  540 */                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f19); return;
/*      */                   }
/*      */                   
/*  543 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f19);
/*  544 */                   out.write(" </td>");
/*  545 */                   out.write("\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\n\t\t");
/*      */                 }
/*      */                 
/*      */ 
/*  549 */                 out.write("\n\t\t<!--ME-Solutions Standalone MGStatusview Alert entries ends here -->\n\n\t\t</table>\n\n\t\t</td>\n\t\t</tr>\n\t\t</table>\n");
/*  550 */                 if (OEMUtil.isOEM()) {
/*  551 */                   out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t<tr>\n\t\t<td width=\"72%\"  class=\"tableheadingbborder\">");
/*  552 */                   out.print(FormatUtil.getString("am.webclient.admin.tools.table.heading"));
/*  553 */                   out.write("</td>\n\t</tr>\n\t<tr>\n\t    <td class=\"admin-bg\">\n\t\t\t<table width=\"795\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\n            <tr>\n                 <td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td>\n             </tr>\t\t\t\n\t\t\t<tr>\n\t\t\t\t");
/*  554 */                   if ((request.isUserInRole("ENTERPRISEADMIN")) && (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())) {
/*  555 */                     out.write("\n\t\t\t\t<td align=\"center\">\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"10%\" align=\"right\"><a href=\"/GlobalActions.do?method=shutdownServer&alert=true\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_shutdown.gif\" vspace=\"7\" border=\"\"></a></td>\n\t\t\t\t\t<td align=\"left\"><a href=\"/GlobalActions.do?method=shutdownServer&alert=true\" >");
/*  556 */                     out.print(FormatUtil.getString("am.webclient.shutdown.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  557 */                     out.write("</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\n\t\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t");
/*  558 */                     if (OEMUtil.isRemove("am.ipaddresssetting.enable")) {
/*  559 */                       out.write("\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"10%\" align=\"right\"><a href=\"/changeip.do?method=SetIPConfiguration\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_changeip.gif\" vspace=\"7\" border=\"\"></a></td>\n\t\t\t\t\t<td align=\"left\"><a href=\"/changeip.do?method=SetIPConfiguration\">");
/*  560 */                       out.print(FormatUtil.getString("am.webclient.systemsettings.heading.text"));
/*  561 */                       out.write("</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t");
/*      */                     }
/*  563 */                     out.write("\n\t\t\t\t</td>\n\t\t\t\t");
/*      */                   }
/*  565 */                   out.write("\n\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n</table>\n");
/*      */                 }
/*  567 */                 out.write("\n\n<br>\n<!-- pasting here  -->\n<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"100%\"  class=\"tableheadingbborder\">");
/*  568 */                 out.print(FormatUtil.getString("am.webclient.admintab.alarmaction"));
/*  569 */                 out.write("</td>\n  </tr>\n\t\t<tr> \n\t\t\t<td  class=\"admin-bg\" >\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<br>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"20%\">\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  570 */                 if (_jspx_meth_ea_005feeadminlink_005f20(_jspx_page_context))
/*      */                   return;
/*  572 */                 out.write("</td> ");
/*  573 */                 out.write("\n\t\t\t\t\t\t\t<td align=\"left\">");
/*      */                 
/*  575 */                 EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f21 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  576 */                 _jspx_th_ea_005feeadminlink_005f21.setPageContext(_jspx_page_context);
/*  577 */                 _jspx_th_ea_005feeadminlink_005f21.setParent(null);
/*      */                 
/*  579 */                 _jspx_th_ea_005feeadminlink_005f21.setHref("/adminAction.do?method=showActionProfiles");
/*  580 */                 int _jspx_eval_ea_005feeadminlink_005f21 = _jspx_th_ea_005feeadminlink_005f21.doStartTag();
/*  581 */                 if (_jspx_eval_ea_005feeadminlink_005f21 != 0) {
/*  582 */                   if (_jspx_eval_ea_005feeadminlink_005f21 != 1) {
/*  583 */                     out = _jspx_page_context.pushBody();
/*  584 */                     _jspx_th_ea_005feeadminlink_005f21.setBodyContent((BodyContent)out);
/*  585 */                     _jspx_th_ea_005feeadminlink_005f21.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  588 */                     out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/*  589 */                     int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f21.doAfterBody();
/*  590 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  593 */                   if (_jspx_eval_ea_005feeadminlink_005f21 != 1) {
/*  594 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  597 */                 if (_jspx_th_ea_005feeadminlink_005f21.doEndTag() == 5) {
/*  598 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f21);
/*      */                 }
/*      */                 else {
/*  601 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f21);
/*  602 */                   out.write("</td> ");
/*  603 */                   out.write("\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\n\t\t\t\t\t\t<td width=\"20%\">\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  604 */                   if (_jspx_meth_ea_005feeadminlink_005f22(_jspx_page_context))
/*      */                     return;
/*  606 */                   out.write("</td> ");
/*  607 */                   out.write("\n\t\t\t\t\t\t\t<td align=\"left\">");
/*      */                   
/*  609 */                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f23 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  610 */                   _jspx_th_ea_005feeadminlink_005f23.setPageContext(_jspx_page_context);
/*  611 */                   _jspx_th_ea_005feeadminlink_005f23.setParent(null);
/*      */                   
/*  613 */                   _jspx_th_ea_005feeadminlink_005f23.setHref("common/viewThreshold.do");
/*  614 */                   int _jspx_eval_ea_005feeadminlink_005f23 = _jspx_th_ea_005feeadminlink_005f23.doStartTag();
/*  615 */                   if (_jspx_eval_ea_005feeadminlink_005f23 != 0) {
/*  616 */                     if (_jspx_eval_ea_005feeadminlink_005f23 != 1) {
/*  617 */                       out = _jspx_page_context.pushBody();
/*  618 */                       _jspx_th_ea_005feeadminlink_005f23.setBodyContent((BodyContent)out);
/*  619 */                       _jspx_th_ea_005feeadminlink_005f23.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/*  622 */                       out.print(FormatUtil.getString("am.webclient.configurealert.thresholdprofile"));
/*  623 */                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f23.doAfterBody();
/*  624 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  627 */                     if (_jspx_eval_ea_005feeadminlink_005f23 != 1) {
/*  628 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  631 */                   if (_jspx_th_ea_005feeadminlink_005f23.doEndTag() == 5) {
/*  632 */                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f23);
/*      */                   }
/*      */                   else {
/*  635 */                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f23);
/*  636 */                     out.write("</td> ");
/*  637 */                     out.write("\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t\t<td width=\"20%\">\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"10%\" align=\"right\">");
/*  638 */                     if (_jspx_meth_ea_005feeadminlink_005f24(_jspx_page_context))
/*      */                       return;
/*  640 */                     out.write("</td> ");
/*  641 */                     out.write("\n\t\t\t\t\t\t\t<td align=\"left\">");
/*      */                     
/*  643 */                     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f25 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.get(EnterpriseAdminLink.class);
/*  644 */                     _jspx_th_ea_005feeadminlink_005f25.setPageContext(_jspx_page_context);
/*  645 */                     _jspx_th_ea_005feeadminlink_005f25.setParent(null);
/*      */                     
/*  647 */                     _jspx_th_ea_005feeadminlink_005f25.setHref("/showActionProfiles.do?method=getResourceProfiles&admin=true");
/*  648 */                     int _jspx_eval_ea_005feeadminlink_005f25 = _jspx_th_ea_005feeadminlink_005f25.doStartTag();
/*  649 */                     if (_jspx_eval_ea_005feeadminlink_005f25 != 0) {
/*  650 */                       if (_jspx_eval_ea_005feeadminlink_005f25 != 1) {
/*  651 */                         out = _jspx_page_context.pushBody();
/*  652 */                         _jspx_th_ea_005feeadminlink_005f25.setBodyContent((BodyContent)out);
/*  653 */                         _jspx_th_ea_005feeadminlink_005f25.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  656 */                         out.print(FormatUtil.getString("am.webclient.common.configurealerts.text"));
/*  657 */                         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f25.doAfterBody();
/*  658 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  661 */                       if (_jspx_eval_ea_005feeadminlink_005f25 != 1) {
/*  662 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  665 */                     if (_jspx_th_ea_005feeadminlink_005f25.doEndTag() == 5) {
/*  666 */                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f25);
/*      */                     }
/*      */                     else {
/*  669 */                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref.reuse(_jspx_th_ea_005feeadminlink_005f25);
/*  670 */                       out.write("</td> ");
/*  671 */                       out.write("\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t\t<td width=\"20%\">\n\t\t\t\t\t\t\t<table border=\"0\">\n\t\t\t\t\t\t\t<td  align=\"right\"><a href=\"/eventlogrules.do?method=view\" class=\"no-bg-change\"><img src=\"/images/icon_admin_eventlog.gif\" vspace=\"5\" border=\"0\"></a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/eventlogrules.do?method=view\">");
/*  672 */                       out.print(FormatUtil.getString("am.webclient.admin.eventlogrules.link"));
/*  673 */                       out.write("</a></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                       
/*  675 */                       if ((com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isMonitorGroupRulesEnabled()) && (!isdelegatedAdmin))
/*      */                       {
/*      */ 
/*  678 */                         out.write("\n\t\t\t\t\t\t<td width=\"20%\">\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"10%\" align=\"right\"><a href=\"/monitorGrpRule.do?method=showMonitorGroupRules\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_eventlog.gif\"  vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t\t\t\t<td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/monitorGrpRule.do?method=showMonitorGroupRules\">");
/*  679 */                         out.print(FormatUtil.getString("am.webclient.admin.mgRule.link"));
/*  680 */                         out.write("</a>\n\t\t\t\t\t\t\t </td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                       }
/*      */                       
/*      */ 
/*  684 */                       out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td width=\"20%\">\n\t\t\t\t\t<table border=\"0\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td align=\"right\"><a href=\"/ProcessTemplates.do?method=showAllTemplates&templatetype=0\" class=\"no-bg-change\"><img src=\"/images/icon_admin_process.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t\t\t\t<td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/ProcessTemplates.do?method=showAllTemplates&templatetype=0\">");
/*  685 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                         return;
/*  687 */                       out.write("</a></td>\t");
/*  688 */                       out.write("\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t<td width=\"20%\">\n\t\t\t\t\t<table border=\"0\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td align=\"right\"><a href=\"/ProcessTemplates.do?method=showAllTemplates&templatetype=1\" class=\"no-bg-change\"><img src=\"images/icon_admin_service.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t\t\t\t<td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/ProcessTemplates.do?method=showAllTemplates&templatetype=1\">");
/*  689 */                       if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */                         return;
/*  691 */                       out.write("</a></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t");
/*  692 */                       if (!isdelegatedAdmin) {
/*  693 */                         out.write("\n\t\t\t\t<td width=\"20%\">\n\t\t\t\t\t<table border=\"0\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td align=\"right\"><a href=\"/DiagnosticInfo.do?method=showDiagnosticInfo\" class=\"no-bg-change\"><img src=\"images/icon_admin_service.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t\t\t\t<td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/DiagnosticInfo.do?method=showDiagnosticInfo\">");
/*  694 */                         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */                           return;
/*  696 */                         out.write("</a></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t");
/*      */                       }
/*  698 */                       out.write("\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t\t</tr>\n  </table>\n<!-- pasting ends -->\n<br>\n<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  \t\t<tr>\n    \t\t<td width=\"100%\"  class=\"tableheadingbborder\">");
/*  699 */                       out.print(FormatUtil.getString("am.webclient.admintab.integration.text"));
/*  700 */                       out.write("</td>\n  \t\t</tr>\n  \t\t<tr>\n            <td class=\"admin-bg\" >\n        \t\t<table  width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n      \t\t\t<br>\n\t\t\t      <tr>\n\t\t\t          <td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td>\n\t\t\t      </tr>      \n\t\t\t\t  <tr>\n\t\t\t");
/*      */                       
/*  702 */                       if (!usertype.equals("F"))
/*      */                       {
/*  704 */                         out.write("\n\t\t\t\t\t");
/*  705 */                         if (OEMUtil.isOEM()) {
/*  706 */                           if (OEMUtil.isRemove("am.apikey.enable"))
/*      */                           {
/*  708 */                             out.write("\n\t\t\t         <td width=\"15%\" align=\"center\">\n\t\t\t\t       <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t      \t<tr>\n\t\t\t\t      \t\t<td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=getAPIKey\" title='");
/*  709 */                             out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  710 */                             out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t      \t\t<td align=\"left\"><a href=\"/adminAction.do?method=getAPIKey\" >");
/*  711 */                             out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  712 */                             out.write("</a></td>\n\t\t\t\t      \t</tr>\n\t\t\t\t      </table>\n\t\t\t\t\t</td>\n\t\t\t\t");
/*      */                           }
/*  714 */                         } else { out.write("\n\t\t\t         <td width=\"15%\" align=\"center\">\n\t\t\t\t       <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t      \t<tr>\n\t\t\t\t      \t\t<td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=getAPIKey\" title='");
/*  715 */                           out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  716 */                           out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t      \t\t<td align=\"left\"><a href=\"/adminAction.do?method=getAPIKey\" >");
/*  717 */                           out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  718 */                           out.write("</a></td>\n\t\t\t\t      \t</tr>\n\t\t\t\t      </table>\n\t\t\t\t\t</td>\n\t\t\t\t");
/*      */                         }
/*  720 */                         out.write("\n\t\t\t\t\t");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/*      */ 
/*  726 */                         out.write("\n\t\t\t\t\t");
/*  727 */                         if (OEMUtil.isOEM()) {
/*  728 */                           if (OEMUtil.isRemove("am.apikey.enable"))
/*      */                           {
/*  730 */                             out.write("\n\t\t\t         <td width=\"15%\" align=\"center\">\n\t\t\t\t       \t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"10%\" align=\"right\"><a  title='");
/*  731 */                             out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  732 */                             out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t\t\t\t<td align=\"left\"><a  class=\"disabledlink\">");
/*  733 */                             out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  734 */                             out.write("</a></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                           }
/*  736 */                         } else { out.write("\n\t\t\t         <td width=\"15%\" align=\"center\">\n\t\t\t\t       \t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t      \t<tr>\n\t\t\t\t      \t<td width=\"10%\" align=\"right\"><a  title='");
/*  737 */                           out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  738 */                           out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t\t\t<td align=\"left\"><a  class=\"disabledlink\">");
/*  739 */                           out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  740 */                           out.write("</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t      \t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                         }
/*  742 */                         out.write("\n\t\t\t\t\t");
/*      */                       }
/*  744 */                       out.write("\n\t\t\t\n\t\t\t            <td width=\"15%\" align=\"center\">\n\t\t\t              <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t  \t\t\t\t<tr>\n\t\t\t  \t\t\t    <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=jsonfeed\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_jsonfeed.gif\"  vspace=\"5\" border=\"0\"></a></td>\n\t\t\t  \t\t\t\t<td align=\"left\"> <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=jsonfeed\">");
/*  745 */                       out.print(FormatUtil.getString("am.webcleint.json.text"));
/*  746 */                       out.write("</a></td>\n\t\t\t    \t\t\t</tr>\n\t\t\t  \t\t\t  </table>\n\t\t\t  \t\t\t</td>\n\t\t\t            <td width=\"15%\" align=\"center\">\n\t\t\t              <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t  \t\t\t\t<tr>\n\t\t\t  \t\t\t    <td width=\"10%\" align=\"right\"><a href=\"/MyPage.do?method=newMyPage\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_dashboard.gif\"  vspace=\"5\" border=\"0\"></a></td>\n\t\t\t  \t\t\t\t<td align=\"left\"><a href=\"/MyPage.do?method=newMyPage\" >");
/*  747 */                       out.print(FormatUtil.getString("am.mypage.dashboards.text"));
/*  748 */                       out.write("</a></td>\n\t\t\t    \t\t\t</tr>\n\t\t\t  \t\t\t  </table>\n\t\t\t  \t\t\t</td>\n\t\t\t\n\t\t\t\n\t\t\t\n\t\t\t            <td width=\"15%\" align=\"center\">\n\t\t\t              <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t  \t\t\t\t<tr>\n\t\t\t  \t\t\t    <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=gmapkey\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_googlemaps.gif\"  vspace=\"5\" border=\"0\"></a></td>\n\t\t\t  \t\t\t\t<td align=\"left\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=gmapkey\" >");
/*  749 */                       out.print(FormatUtil.getString("am.monitortab.gmap.text"));
/*  750 */                       out.write("</a></td>\n\t\t\t    \t\t\t</tr>\n\t\t\t  \t\t\t  </table>\n\t\t\t  \t\t\t</td>\n\t\t\t\n\t\t\t\t\t\t<td width=\"15%\" align=\"center\">\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t\t   <td colspan=\"2\">&nbsp;</td>\n\t\t\t\t\t\t\t  </tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n\n<br>\n\n\n<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"100%\"  class=\"tableheadingbborder\">");
/*  751 */                       out.print(FormatUtil.getString("am.webclient.admintab.reporting"));
/*  752 */                       out.write("</td>\n  </tr>\n\n\t <tr>\n\t\t    <td class=\"admin-bg\" >\n\n\t<table  width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n      <br>\n      <tr>\n          <td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td>\n      </tr>      \n        <tr>\n        ");
/*  753 */                       if (!isdelegatedAdmin) {
/*  754 */                         out.write("\n\t<td width=\"15%\" align=\"center\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t<tr>\n\t<td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showDataCleanUp\" class=\"no-bg-change\"><img src=\"/images/data_cleanup.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t<td align=\"left\"><a href=\"/adminAction.do?method=showDataCleanUp\" >");
/*  755 */                         out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/*  756 */                         out.write("</a></td>\n\t</tr>\n\t</table>\n\t\t</td>\n\t");
/*      */                       }
/*  758 */                       out.write("\n\n\t      <td width=\"15%\" align=\"center\">\n\t      <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t      <tr>\n\t      <td width=\"10%\" align=\"right\"><a href=\"/scheduleReports.do?method=showScheduleReports\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_schedulereports.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t      <td align=\"left\"><a href=\"/scheduleReports.do?method=showScheduleReports\" >");
/*  759 */                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  760 */                       out.write("</a></td>\n\t      </tr>\n\t      </table>\n\t      </td>\n              <td width=\"15%\" align=\"center\">\n\t\t       <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t      <tr>\n\t\t\t      \t<td width=\"10%\" align=\"right\"><a href=\"/businessHours.do?method=showBusinessHours\" title='");
/*  761 */                       out.print(FormatUtil.getString("am.webclient.businesshour.title.text"));
/*  762 */                       out.write("' class=\"no-bg-change\"><img src=\"/images/icon-business-hours.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t      \t<td align=\"left\"><a href=\"/businessHours.do?method=showBusinessHours\" >");
/*  763 */                       out.print(FormatUtil.getString("am.webclient.businesshour.title.text"));
/*  764 */                       out.write("</a></td>\n\t\t\t      </tr>\n\t\t      </table>\n\t\t     </td>\n\t\t     ");
/*  765 */                       if (!isdelegatedAdmin) {
/*  766 */                         out.write("\n\t\t     <td width=\"15%\" align=\"center\">\n\t            <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t    \t        <tr>\n    \t    \t\t    <td width=\"10%\" align=\"right\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_sla.png\" vspace=\"5\" border=\"0\"></a></td>\n        \t    \t\t<td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/showBussiness.do?method=generateApplicationAvailablity\" >");
/*  767 */                         out.print(FormatUtil.getString("am.webclient.selectmonitorview.SLAview.text"));
/*  768 */                         out.write("</a></td>\n\t            \t</tr>\n    \t        </table>\n            </td>\n            ");
/*      */                       }
/*  770 */                       out.write("\n\t        <td width=\"15%\" align=\"center\">\n\t\t\t\n\t\t\t</td>\n\t</tr>\n\t</table>\n\n\t</td>\n</tr>\n\n</table>\n\n<br>\n\n<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"100%\"  class=\"tableheadingbborder\">");
/*  771 */                       out.print(FormatUtil.getString("am.webclient.admin.tools.table.heading"));
/*  772 */                       out.write("</td>\n  </tr>\n\n\t <tr>\n\t\t    <td class=\"admin-bg\" >\n\n\t<table  width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n      <br>\n      <tr>\n          <td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td><td width=\"15%\" align=\"center\"></td>\n      </tr>      \n        <tr>\n        \n        <td width=\"15%\" align=\"center\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t  <tr>\n\t\t\t    <td width=\"10%\" align=\"right\"><a href=\"/common/serverinfo.do\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_support.gif\" vspace=\"5\" border=\"0\"></a></td>\n\t\t\t    <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/common/serverinfo.do\" >");
/*  773 */                       out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/*  774 */                       out.write("</a></td>\n\t\t\t  </tr>\n\t\t\t</table>\n\t\t</td>\n        \n        <td  width=\"15%\" align=\"center\">\n\t\t");
/*  775 */                       if ((!OEMUtil.isOEM()) && (request.isUserInRole("ENTERPRISEADMIN")) && (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */                       {
/*      */ 
/*  778 */                         out.write("\n\t\t\t<table width=\"100%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n\t\t\t<tr>\n\t\t\t<td width=\"6%\" align=\"right\"  ><a href=\"/GlobalActions.do?method=shutdownServer&alert=true\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_shutdown.gif\" vspace=\"7\" border=\"\"></a></td>\n\t\t\t<td align=\"left\"><a href=\"/GlobalActions.do?method=shutdownServer&alert=true\" >");
/*  779 */                         out.print(FormatUtil.getString("am.webclient.shutdown.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  780 */                         out.write("</a></td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t");
/*      */                       }
/*  782 */                       out.write("\n\t\t</td>\n\n\t</tr>\n\t</table>\n\n\t</td>\n</tr>\n\n</table>\n</div>\n<br>\n\n\n\n\n\n");
/*      */                     }
/*  784 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  785 */         out = _jspx_out;
/*  786 */         if ((out != null) && (out.getBufferSize() != 0))
/*  787 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  788 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  791 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  797 */     PageContext pageContext = _jspx_page_context;
/*  798 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  800 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f0 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  801 */     _jspx_th_ea_005feeadminlink_005f0.setPageContext(_jspx_page_context);
/*  802 */     _jspx_th_ea_005feeadminlink_005f0.setParent(null);
/*      */     
/*  804 */     _jspx_th_ea_005feeadminlink_005f0.setHref("/downTimeScheduler.do?method=maintenanceTaskListView");
/*      */     
/*  806 */     _jspx_th_ea_005feeadminlink_005f0.setEnableClass("no-bg-change");
/*  807 */     int _jspx_eval_ea_005feeadminlink_005f0 = _jspx_th_ea_005feeadminlink_005f0.doStartTag();
/*  808 */     if (_jspx_eval_ea_005feeadminlink_005f0 != 0) {
/*  809 */       if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  810 */         out = _jspx_page_context.pushBody();
/*  811 */         _jspx_th_ea_005feeadminlink_005f0.setBodyContent((BodyContent)out);
/*  812 */         _jspx_th_ea_005feeadminlink_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  815 */         out.write("<img src=\"/images/icon_admin_maintenance.gif\" vspace=\"2\" border=\"\">");
/*  816 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f0.doAfterBody();
/*  817 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  820 */       if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  821 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  824 */     if (_jspx_th_ea_005feeadminlink_005f0.doEndTag() == 5) {
/*  825 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0);
/*  826 */       return true;
/*      */     }
/*  828 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0);
/*  829 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  834 */     PageContext pageContext = _jspx_page_context;
/*  835 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  837 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f2 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  838 */     _jspx_th_ea_005feeadminlink_005f2.setPageContext(_jspx_page_context);
/*  839 */     _jspx_th_ea_005feeadminlink_005f2.setParent(null);
/*      */     
/*  841 */     _jspx_th_ea_005feeadminlink_005f2.setHref("/alertEscalation.do?method=showRules");
/*      */     
/*  843 */     _jspx_th_ea_005feeadminlink_005f2.setEnableClass("no-bg-change");
/*  844 */     int _jspx_eval_ea_005feeadminlink_005f2 = _jspx_th_ea_005feeadminlink_005f2.doStartTag();
/*  845 */     if (_jspx_eval_ea_005feeadminlink_005f2 != 0) {
/*  846 */       if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  847 */         out = _jspx_page_context.pushBody();
/*  848 */         _jspx_th_ea_005feeadminlink_005f2.setBodyContent((BodyContent)out);
/*  849 */         _jspx_th_ea_005feeadminlink_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  852 */         out.write("<img src=\"/images/icon_admin_alertescalation.gif\" vspace=\"5\" border=\"\">");
/*  853 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f2.doAfterBody();
/*  854 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  857 */       if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  858 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  861 */     if (_jspx_th_ea_005feeadminlink_005f2.doEndTag() == 5) {
/*  862 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2);
/*  863 */       return true;
/*      */     }
/*  865 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2);
/*  866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  871 */     PageContext pageContext = _jspx_page_context;
/*  872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  874 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f4 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  875 */     _jspx_th_ea_005feeadminlink_005f4.setPageContext(_jspx_page_context);
/*  876 */     _jspx_th_ea_005feeadminlink_005f4.setParent(null);
/*      */     
/*  878 */     _jspx_th_ea_005feeadminlink_005f4.setHref("/adminAction.do?method=showManagedServers");
/*      */     
/*  880 */     _jspx_th_ea_005feeadminlink_005f4.setEnableClass("no-bg-change");
/*  881 */     int _jspx_eval_ea_005feeadminlink_005f4 = _jspx_th_ea_005feeadminlink_005f4.doStartTag();
/*  882 */     if (_jspx_eval_ea_005feeadminlink_005f4 != 0) {
/*  883 */       if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/*  884 */         out = _jspx_page_context.pushBody();
/*  885 */         _jspx_th_ea_005feeadminlink_005f4.setBodyContent((BodyContent)out);
/*  886 */         _jspx_th_ea_005feeadminlink_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  889 */         out.write("<img src=\"/images/cfg-servicedesk.gif\" align=\"middle\" border=\"0\" vspace=\"2\">");
/*  890 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f4.doAfterBody();
/*  891 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  894 */       if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/*  895 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  898 */     if (_jspx_th_ea_005feeadminlink_005f4.doEndTag() == 5) {
/*  899 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4);
/*  900 */       return true;
/*      */     }
/*  902 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4);
/*  903 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f6(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  908 */     PageContext pageContext = _jspx_page_context;
/*  909 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  911 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f6 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  912 */     _jspx_th_ea_005feeadminlink_005f6.setPageContext(_jspx_page_context);
/*  913 */     _jspx_th_ea_005feeadminlink_005f6.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/*  915 */     _jspx_th_ea_005feeadminlink_005f6.setHref("/jsp/ProxyConfiguration.jsp");
/*      */     
/*  917 */     _jspx_th_ea_005feeadminlink_005f6.setEnableClass("no-bg-change");
/*  918 */     int _jspx_eval_ea_005feeadminlink_005f6 = _jspx_th_ea_005feeadminlink_005f6.doStartTag();
/*  919 */     if (_jspx_eval_ea_005feeadminlink_005f6 != 0) {
/*  920 */       if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/*  921 */         out = _jspx_page_context.pushBody();
/*  922 */         _jspx_th_ea_005feeadminlink_005f6.setBodyContent((BodyContent)out);
/*  923 */         _jspx_th_ea_005feeadminlink_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  926 */         out.write("<img src=\"/images/icon_admin_proxyconfig.gif\"  vspace=\"5\" border=\"\">");
/*  927 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f6.doAfterBody();
/*  928 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  931 */       if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/*  932 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  935 */     if (_jspx_th_ea_005feeadminlink_005f6.doEndTag() == 5) {
/*  936 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6);
/*  937 */       return true;
/*      */     }
/*  939 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6);
/*  940 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  945 */     PageContext pageContext = _jspx_page_context;
/*  946 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  948 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f8 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  949 */     _jspx_th_ea_005feeadminlink_005f8.setPageContext(_jspx_page_context);
/*  950 */     _jspx_th_ea_005feeadminlink_005f8.setParent(null);
/*      */     
/*  952 */     _jspx_th_ea_005feeadminlink_005f8.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*      */     
/*  954 */     _jspx_th_ea_005feeadminlink_005f8.setEnableClass("no-bg-change");
/*  955 */     int _jspx_eval_ea_005feeadminlink_005f8 = _jspx_th_ea_005feeadminlink_005f8.doStartTag();
/*  956 */     if (_jspx_eval_ea_005feeadminlink_005f8 != 0) {
/*  957 */       if (_jspx_eval_ea_005feeadminlink_005f8 != 1) {
/*  958 */         out = _jspx_page_context.pushBody();
/*  959 */         _jspx_th_ea_005feeadminlink_005f8.setBodyContent((BodyContent)out);
/*  960 */         _jspx_th_ea_005feeadminlink_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  963 */         out.write("<img src=\"/images/icon_admin_globalconfig.gif\" vspace=\"5\" border=\"\">");
/*  964 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f8.doAfterBody();
/*  965 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  968 */       if (_jspx_eval_ea_005feeadminlink_005f8 != 1) {
/*  969 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  972 */     if (_jspx_th_ea_005feeadminlink_005f8.doEndTag() == 5) {
/*  973 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f8);
/*  974 */       return true;
/*      */     }
/*  976 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f8);
/*  977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  982 */     PageContext pageContext = _jspx_page_context;
/*  983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  985 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f10 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  986 */     _jspx_th_ea_005feeadminlink_005f10.setPageContext(_jspx_page_context);
/*  987 */     _jspx_th_ea_005feeadminlink_005f10.setParent(null);
/*      */     
/*  989 */     _jspx_th_ea_005feeadminlink_005f10.setHref("/adminAction.do?method=showServerSettingsConfiguration&typetoshow=general");
/*      */     
/*  991 */     _jspx_th_ea_005feeadminlink_005f10.setEnableClass("no-bg-change");
/*  992 */     int _jspx_eval_ea_005feeadminlink_005f10 = _jspx_th_ea_005feeadminlink_005f10.doStartTag();
/*  993 */     if (_jspx_eval_ea_005feeadminlink_005f10 != 0) {
/*  994 */       if (_jspx_eval_ea_005feeadminlink_005f10 != 1) {
/*  995 */         out = _jspx_page_context.pushBody();
/*  996 */         _jspx_th_ea_005feeadminlink_005f10.setBodyContent((BodyContent)out);
/*  997 */         _jspx_th_ea_005feeadminlink_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1000 */         out.write("<img src=\"/images/icon_server_settings.gif\" vspace=\"5\" border=\"\">");
/* 1001 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f10.doAfterBody();
/* 1002 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1005 */       if (_jspx_eval_ea_005feeadminlink_005f10 != 1) {
/* 1006 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1009 */     if (_jspx_th_ea_005feeadminlink_005f10.doEndTag() == 5) {
/* 1010 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f10);
/* 1011 */       return true;
/*      */     }
/* 1013 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f10);
/* 1014 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1019 */     PageContext pageContext = _jspx_page_context;
/* 1020 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1022 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f12 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1023 */     _jspx_th_ea_005feeadminlink_005f12.setPageContext(_jspx_page_context);
/* 1024 */     _jspx_th_ea_005feeadminlink_005f12.setParent(null);
/*      */     
/* 1026 */     _jspx_th_ea_005feeadminlink_005f12.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */     
/* 1028 */     _jspx_th_ea_005feeadminlink_005f12.setEnableClass("no-bg-change");
/* 1029 */     int _jspx_eval_ea_005feeadminlink_005f12 = _jspx_th_ea_005feeadminlink_005f12.doStartTag();
/* 1030 */     if (_jspx_eval_ea_005feeadminlink_005f12 != 0) {
/* 1031 */       if (_jspx_eval_ea_005feeadminlink_005f12 != 1) {
/* 1032 */         out = _jspx_page_context.pushBody();
/* 1033 */         _jspx_th_ea_005feeadminlink_005f12.setBodyContent((BodyContent)out);
/* 1034 */         _jspx_th_ea_005feeadminlink_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1037 */         out.write("<img src=\"/images/icon_admin_useradministrati.gif\" vspace=\"5\" border=\"\">");
/* 1038 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f12.doAfterBody();
/* 1039 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1042 */       if (_jspx_eval_ea_005feeadminlink_005f12 != 1) {
/* 1043 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1046 */     if (_jspx_th_ea_005feeadminlink_005f12.doEndTag() == 5) {
/* 1047 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f12);
/* 1048 */       return true;
/*      */     }
/* 1050 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f12);
/* 1051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1056 */     PageContext pageContext = _jspx_page_context;
/* 1057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1059 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f14 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1060 */     _jspx_th_ea_005feeadminlink_005f14.setPageContext(_jspx_page_context);
/* 1061 */     _jspx_th_ea_005feeadminlink_005f14.setParent(null);
/*      */     
/* 1063 */     _jspx_th_ea_005feeadminlink_005f14.setHref("/adminAction.do?method=showMailServerConfiguration&admin=true");
/*      */     
/* 1065 */     _jspx_th_ea_005feeadminlink_005f14.setEnableClass("no-bg-change");
/* 1066 */     int _jspx_eval_ea_005feeadminlink_005f14 = _jspx_th_ea_005feeadminlink_005f14.doStartTag();
/* 1067 */     if (_jspx_eval_ea_005feeadminlink_005f14 != 0) {
/* 1068 */       if (_jspx_eval_ea_005feeadminlink_005f14 != 1) {
/* 1069 */         out = _jspx_page_context.pushBody();
/* 1070 */         _jspx_th_ea_005feeadminlink_005f14.setBodyContent((BodyContent)out);
/* 1071 */         _jspx_th_ea_005feeadminlink_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1074 */         out.write("<img src=\"/images/icon_admin_mailserver.gif\"  vspace=\"5\" border=\"\">");
/* 1075 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f14.doAfterBody();
/* 1076 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1079 */       if (_jspx_eval_ea_005feeadminlink_005f14 != 1) {
/* 1080 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1083 */     if (_jspx_th_ea_005feeadminlink_005f14.doEndTag() == 5) {
/* 1084 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f14);
/* 1085 */       return true;
/*      */     }
/* 1087 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f14);
/* 1088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1093 */     PageContext pageContext = _jspx_page_context;
/* 1094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1096 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f16 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1097 */     _jspx_th_ea_005feeadminlink_005f16.setPageContext(_jspx_page_context);
/* 1098 */     _jspx_th_ea_005feeadminlink_005f16.setParent(null);
/*      */     
/* 1100 */     _jspx_th_ea_005feeadminlink_005f16.setHref("/webclient/common/jsp/registerLicence.jsp");
/*      */     
/* 1102 */     _jspx_th_ea_005feeadminlink_005f16.setEnableClass("no-bg-change");
/* 1103 */     int _jspx_eval_ea_005feeadminlink_005f16 = _jspx_th_ea_005feeadminlink_005f16.doStartTag();
/* 1104 */     if (_jspx_eval_ea_005feeadminlink_005f16 != 0) {
/* 1105 */       if (_jspx_eval_ea_005feeadminlink_005f16 != 1) {
/* 1106 */         out = _jspx_page_context.pushBody();
/* 1107 */         _jspx_th_ea_005feeadminlink_005f16.setBodyContent((BodyContent)out);
/* 1108 */         _jspx_th_ea_005feeadminlink_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1111 */         out.write("<img src=\"/images/Register.gif\"  vspace=\"2\" border=\"\">");
/* 1112 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f16.doAfterBody();
/* 1113 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1116 */       if (_jspx_eval_ea_005feeadminlink_005f16 != 1) {
/* 1117 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1120 */     if (_jspx_th_ea_005feeadminlink_005f16.doEndTag() == 5) {
/* 1121 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f16);
/* 1122 */       return true;
/*      */     }
/* 1124 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f16);
/* 1125 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1130 */     PageContext pageContext = _jspx_page_context;
/* 1131 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1133 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f18 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1134 */     _jspx_th_ea_005feeadminlink_005f18.setPageContext(_jspx_page_context);
/* 1135 */     _jspx_th_ea_005feeadminlink_005f18.setParent(null);
/*      */     
/* 1137 */     _jspx_th_ea_005feeadminlink_005f18.setHref("/jsp/AllViews.jsp");
/*      */     
/* 1139 */     _jspx_th_ea_005feeadminlink_005f18.setEnableClass("no-bg-change");
/* 1140 */     int _jspx_eval_ea_005feeadminlink_005f18 = _jspx_th_ea_005feeadminlink_005f18.doStartTag();
/* 1141 */     if (_jspx_eval_ea_005feeadminlink_005f18 != 0) {
/* 1142 */       if (_jspx_eval_ea_005feeadminlink_005f18 != 1) {
/* 1143 */         out = _jspx_page_context.pushBody();
/* 1144 */         _jspx_th_ea_005feeadminlink_005f18.setBodyContent((BodyContent)out);
/* 1145 */         _jspx_th_ea_005feeadminlink_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1148 */         out.write("<img src=\"/images/icon_admin_monitorgroupstatusview.gif\" align=\"middle\" border=\"0\" vspace=\"2\">");
/* 1149 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f18.doAfterBody();
/* 1150 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1153 */       if (_jspx_eval_ea_005feeadminlink_005f18 != 1) {
/* 1154 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1157 */     if (_jspx_th_ea_005feeadminlink_005f18.doEndTag() == 5) {
/* 1158 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f18);
/* 1159 */       return true;
/*      */     }
/* 1161 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f18);
/* 1162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1167 */     PageContext pageContext = _jspx_page_context;
/* 1168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1170 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f20 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1171 */     _jspx_th_ea_005feeadminlink_005f20.setPageContext(_jspx_page_context);
/* 1172 */     _jspx_th_ea_005feeadminlink_005f20.setParent(null);
/*      */     
/* 1174 */     _jspx_th_ea_005feeadminlink_005f20.setHref("/adminAction.do?method=showActionProfiles");
/*      */     
/* 1176 */     _jspx_th_ea_005feeadminlink_005f20.setEnableClass("no-bg-change");
/* 1177 */     int _jspx_eval_ea_005feeadminlink_005f20 = _jspx_th_ea_005feeadminlink_005f20.doStartTag();
/* 1178 */     if (_jspx_eval_ea_005feeadminlink_005f20 != 0) {
/* 1179 */       if (_jspx_eval_ea_005feeadminlink_005f20 != 1) {
/* 1180 */         out = _jspx_page_context.pushBody();
/* 1181 */         _jspx_th_ea_005feeadminlink_005f20.setBodyContent((BodyContent)out);
/* 1182 */         _jspx_th_ea_005feeadminlink_005f20.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1185 */         out.write("<img src=\"/images/icon_admin_assaction.gif\" align=\"middle\" border=\"0\" vspace=\"2\">");
/* 1186 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f20.doAfterBody();
/* 1187 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1190 */       if (_jspx_eval_ea_005feeadminlink_005f20 != 1) {
/* 1191 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1194 */     if (_jspx_th_ea_005feeadminlink_005f20.doEndTag() == 5) {
/* 1195 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f20);
/* 1196 */       return true;
/*      */     }
/* 1198 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f20);
/* 1199 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f22(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1204 */     PageContext pageContext = _jspx_page_context;
/* 1205 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1207 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f22 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1208 */     _jspx_th_ea_005feeadminlink_005f22.setPageContext(_jspx_page_context);
/* 1209 */     _jspx_th_ea_005feeadminlink_005f22.setParent(null);
/*      */     
/* 1211 */     _jspx_th_ea_005feeadminlink_005f22.setHref("common/viewThreshold.do");
/*      */     
/* 1213 */     _jspx_th_ea_005feeadminlink_005f22.setEnableClass("no-bg-change");
/* 1214 */     int _jspx_eval_ea_005feeadminlink_005f22 = _jspx_th_ea_005feeadminlink_005f22.doStartTag();
/* 1215 */     if (_jspx_eval_ea_005feeadminlink_005f22 != 0) {
/* 1216 */       if (_jspx_eval_ea_005feeadminlink_005f22 != 1) {
/* 1217 */         out = _jspx_page_context.pushBody();
/* 1218 */         _jspx_th_ea_005feeadminlink_005f22.setBodyContent((BodyContent)out);
/* 1219 */         _jspx_th_ea_005feeadminlink_005f22.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1222 */         out.write("<img src=\"/images/trap.png\" align=\"middle\" border=\"0\" vspace=\"2\">");
/* 1223 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f22.doAfterBody();
/* 1224 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1227 */       if (_jspx_eval_ea_005feeadminlink_005f22 != 1) {
/* 1228 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1231 */     if (_jspx_th_ea_005feeadminlink_005f22.doEndTag() == 5) {
/* 1232 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f22);
/* 1233 */       return true;
/*      */     }
/* 1235 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f22);
/* 1236 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_ea_005feeadminlink_005f24(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1241 */     PageContext pageContext = _jspx_page_context;
/* 1242 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1244 */     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f24 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1245 */     _jspx_th_ea_005feeadminlink_005f24.setPageContext(_jspx_page_context);
/* 1246 */     _jspx_th_ea_005feeadminlink_005f24.setParent(null);
/*      */     
/* 1248 */     _jspx_th_ea_005feeadminlink_005f24.setHref("/showActionProfiles.do?method=getResourceProfiles&admin=true");
/*      */     
/* 1250 */     _jspx_th_ea_005feeadminlink_005f24.setEnableClass("no-bg-change");
/* 1251 */     int _jspx_eval_ea_005feeadminlink_005f24 = _jspx_th_ea_005feeadminlink_005f24.doStartTag();
/* 1252 */     if (_jspx_eval_ea_005feeadminlink_005f24 != 0) {
/* 1253 */       if (_jspx_eval_ea_005feeadminlink_005f24 != 1) {
/* 1254 */         out = _jspx_page_context.pushBody();
/* 1255 */         _jspx_th_ea_005feeadminlink_005f24.setBodyContent((BodyContent)out);
/* 1256 */         _jspx_th_ea_005feeadminlink_005f24.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1259 */         out.write("<img src=\"images/icon_admin_alertsettings.gif\" align=\"middle\" border=\"0\" vspace=\"2\">");
/* 1260 */         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f24.doAfterBody();
/* 1261 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1264 */       if (_jspx_eval_ea_005feeadminlink_005f24 != 1) {
/* 1265 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1268 */     if (_jspx_th_ea_005feeadminlink_005f24.doEndTag() == 5) {
/* 1269 */       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f24);
/* 1270 */       return true;
/*      */     }
/* 1272 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f24);
/* 1273 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1278 */     PageContext pageContext = _jspx_page_context;
/* 1279 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1281 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 1282 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 1283 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 1285 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.admin.showtemplate.link");
/* 1286 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 1287 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 1288 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 1289 */         out = _jspx_page_context.pushBody();
/* 1290 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 1291 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1294 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f0, _jspx_page_context))
/* 1295 */           return true;
/* 1296 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 1297 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1300 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 1301 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1304 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 1305 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1306 */       return true;
/*      */     }
/* 1308 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1309 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1314 */     PageContext pageContext = _jspx_page_context;
/* 1315 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1317 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 1318 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 1319 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f0);
/* 1320 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 1321 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 1322 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 1323 */         out = _jspx_page_context.pushBody();
/* 1324 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/* 1325 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1328 */         out.write(32);
/* 1329 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_fmt_005fparam_005f0, _jspx_page_context))
/* 1330 */           return true;
/* 1331 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 1332 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1335 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 1336 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1339 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 1340 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 1341 */       return true;
/*      */     }
/* 1343 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 1344 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_fmt_005fparam_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1349 */     PageContext pageContext = _jspx_page_context;
/* 1350 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1352 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1353 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 1354 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_fmt_005fparam_005f0);
/*      */     
/* 1356 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.templatetype.process");
/* 1357 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 1358 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 1359 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1360 */       return true;
/*      */     }
/* 1362 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1368 */     PageContext pageContext = _jspx_page_context;
/* 1369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1371 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1372 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 1373 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/*      */     
/* 1375 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.admin.showservicetemplate.link");
/* 1376 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 1377 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 1378 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1379 */       return true;
/*      */     }
/* 1381 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1382 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1387 */     PageContext pageContext = _jspx_page_context;
/* 1388 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1390 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1391 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 1392 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/*      */     
/* 1394 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.diagnostic.info.text");
/* 1395 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 1396 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 1397 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 1398 */       return true;
/*      */     }
/* 1400 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 1401 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\EnterpriseAdmin_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */