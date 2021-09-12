/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParamTag;
/*      */ 
/*      */ public final class Admin_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   25 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   31 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*   32 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   51 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   55 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   67 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   71 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   72 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/*   73 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/*   74 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*   75 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   76 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   77 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   78 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*   79 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.release();
/*   80 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   81 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   88 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   91 */     JspWriter out = null;
/*   92 */     Object page = this;
/*   93 */     JspWriter _jspx_out = null;
/*   94 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   98 */       response.setContentType("text/html");
/*   99 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  101 */       _jspx_page_context = pageContext;
/*  102 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  103 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  104 */       session = pageContext.getSession();
/*  105 */       out = pageContext.getOut();
/*  106 */       _jspx_out = out;
/*      */       
/*  108 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n<!--ME-Solutions Standalone MGStatusview starts here -->\n\n\n\n<!--ME-Solutions Standalone MGStatusview starts here -->\n");
/*      */       
/*  110 */       String mgStatusViews = com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.admin.mgstatusview.enabled");
/*  111 */       boolean isdelegatedAdmin = com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(request.getRemoteUser());
/*      */       
/*  113 */       out.write(10);
/*  114 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  115 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  117 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  118 */       out.write(10);
/*  119 */       out.write("\n<style type=\"text/css\">\n  a.no-bg-change:hover {background-color: transparent; border:none; padding:4px 4px 4px 4px;  }\n</style>\n<body>\n<div class=\"admin-content\">\n  <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" width=\"100%\">\n    <tr>\n      <td class=\"vcenter-shadow-tp-lcorn\" width=\"8\"><img src=\"/images/spacer.gif\" width=\"7\" height=\"9\" /></td>\n      <td class=\"vcenter-shadow-tp-mtile\"></td>\n      <td class=\"vcenter-shadow-tp-rcorn\" width=\"7\"><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n    </tr>\n    <tr>\n      <td class=\"vcenter-shadow-lfttile\" ><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n      <!-- Inner tabel starts!-->\n      <td width=\"100%\" valign=\"top\">\n        <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n          <tr>\n            <td width=\"100%\" class=\"bodytextbold tableheadingbborder\" style=\"height:25px !important; padding:10px 1%\">&nbsp; <b>");
/*  120 */       out.print(FormatUtil.getString("am.webclient.admintab.monitors"));
/*  121 */       out.write("</b></td>\n          </tr>\n          <tr>\n            <td style=\"padding:10px 1%\">\n              <table width=\"100%\" border=\"0\" cellpadding=\"0\"cellspacing =\"0\">\n                <tr>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        ");
/*  122 */       if (!PluginUtil.isPlugin()) {
/*  123 */         out.write("\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=Windows&restype=WindowsNT_Server&haid=null\" class=\"no-bg-change\"><img src=\"/images/icon_admin_discover.gif\" vspace=\"5\" border=\"0\"  style=\"position:relative; top:4px;\"></a></td>\n                        <td align=\"left\" style=\"white-space:nowrap;\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=Windows&restype=WindowsNT_Server&haid=null\">");
/*  124 */         out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  125 */         out.write("</a> <span class=\"bodytext\">|</span> <a style=\"white-space:nowrap;\" href=\"javascript:void(0)\" onClick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" style=\"position:relative;\">");
/*  126 */         out.print(FormatUtil.getString("am.webclient.admin.bulkimport.text"));
/*  127 */         out.write("</a></td>\n                        ");
/*      */       }
/*      */       else {
/*  130 */         out.write("\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=Windows&restype=WindowsNT_Server&haid=null\" class=\"no-bg-change\"><img src=\"/images/icon_admin_discover.gif\" vspace=\"5\" border=\"0\"  style=\"position:relative; top:4px;\"></a></td>\n                        <td align=\"left\" style=\"white-space:nowrap;\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=Windows&restype=WindowsNT_Server&haid=null\">");
/*  131 */         out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  132 */         out.write("</a> <span class=\"bodytext\">|</span> <a style=\"white-space:nowrap;\" href=\"/admin/createapplication.do?method=createapp&grouptype=1\">");
/*  133 */         out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/*  134 */         out.write("</a></td>\n                        ");
/*      */       }
/*  136 */       out.write("\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*  137 */       if (!isdelegatedAdmin) {
/*  138 */         out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/newDiscoveryAction.do?method=getDiscoveryDetails\" class=\"no-bg-change\"><img src=\"/images/icon_admin_discoverserver.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/newDiscoveryAction.do?method=getDiscoveryDetails\" >");
/*  139 */         out.print(FormatUtil.getString("am.webclient.admin.networkdiscovery.link"));
/*  140 */         out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                ");
/*      */       }
/*  142 */       out.write("\n                  </td>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/monitorType.do?method=showTypes\" class=\"no-bg-change\"><img src=\"/images/icon_admin_addnewmonitortyp.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/monitorType.do?method=showTypes\">");
/*  143 */       out.print(FormatUtil.getString("am.manage.custom.types"));
/*  144 */       out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                   ");
/*      */       
/*  146 */       String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*  147 */       if (!usertype.equals("F"))
/*      */       {
/*  149 */         if (!isdelegatedAdmin)
/*      */         {
/*  151 */           out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance\" class=\"no-bg-change\"><img src=\"/images/icon_admin_perfpolling.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance\" >");
/*  152 */           out.print(FormatUtil.getString("am.webclient.admin.performancepoll.link"));
/*  153 */           out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */         }
/*      */       }
/*  156 */       out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"no-bg-change\"><img src=\"/images/icon_admin_maintenance.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/downTimeScheduler.do?method=maintenanceTaskListView\">");
/*  157 */       out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  158 */       out.write("</a> </td>\n                      </tr>\n                    </table>\n                  </td>\n                </tr>\n                <tr>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/ProcessTemplates.do?method=showAllTemplates&templatetype=0\" class=\"no-bg-change\"><img src=\"/images/icon_admin_process.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><span style=\"white-space:nowrap;\"><a href='/ProcessTemplates.do?method=showAllTemplates&templatetype=0' >\n                        ");
/*  159 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */         return;
/*  161 */       out.write("\n                         </a></span></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */       
/*  163 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  164 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  165 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/*  167 */       _jspx_th_c_005fif_005f0.setTest("${productEdition!='CLOUD'}");
/*  168 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  169 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/*  171 */           out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\" onClick=\"return checkforwindows('servicetemplate')\"><a href=\"/ProcessTemplates.do?method=showAllTemplates&templatetype=1\" class=\"no-bg-change\"><img src=\"/images/icon_admin_service.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\" onClick=\"return checkforwindows('servicetemplate')\"><a href='/ProcessTemplates.do?method=showAllTemplates&templatetype=1'><span style=\"white-space:nowrap;\">");
/*  172 */           if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */             return;
/*  174 */           out.write("</span></a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*  175 */           if (PluginUtil.isPlugin()) {
/*  176 */             out.write("\n                  <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/admin/createapplication.do?method=createapp&grouptype=3\"  class=\"no-bg-change\"><img src=\"/images/icon-admin-vm.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/admin/createapplication.do?method=createapp&grouptype=3\" >");
/*  177 */             out.print(FormatUtil.getString("am.webclient.mg.type.vcenter"));
/*  178 */             out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n\t\t   <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/admin/createapplication.do?method=createapp&grouptype=4\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_view.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/admin/createapplication.do?method=createapp&grouptype=4\" >");
/*  179 */             out.print(FormatUtil.getString("am.webclient.mg.type.vmware.horizon"));
/*  180 */             out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */           }
/*  182 */           out.write("\n                  ");
/*  183 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  184 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  188 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  189 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/*  192 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  193 */         out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\" onClick=\"return credentialManagerCheck()\"><a href='/credentialManager.do?method=showCredentialManager' class=\"no-bg-change\"><img src=\"/images/credentialManager.png\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\" onClick=\"return credentialManagerCheck()\"><a href='/credentialManager.do?method=showCredentialManager'><span style=\"white-space:nowrap;\">");
/*  194 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */           return;
/*  196 */         out.write("</span></a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  <!--ME-Solutions Standalone MGStatusview Alert Entry Starts here  -->\n                ");
/*      */         
/*  198 */         if ((mgStatusViews != null) && (mgStatusViews.equals("true")))
/*      */         {
/*      */ 
/*  201 */           out.write("\n                <td  width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/jsp/AllViews.jsp\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_monitorgroupstatusview.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/jsp/AllViews.jsp\">");
/*  202 */           out.print(FormatUtil.getString("am.webclient.alertviews.title"));
/*  203 */           out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                ");
/*      */         }
/*      */         
/*      */ 
/*  207 */         out.write("\n                <!--ME-Solutions Standalone MGStatusview Alert Entry ends here  -->\n                </tr>\n              </table>\n            </td>\n          </tr>\n        </table>\n\n        <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n          <tr>\n            <td width=\"100%\" class=\"bodytextbold tableheadingbborder\" style=\"height:25px !important; padding:10px 1%\">&nbsp; <b>");
/*  208 */         out.print(FormatUtil.getString("am.webclient.admintab.alarmaction"));
/*  209 */         out.write("</b></td>\n          </tr>\n          <tr>\n            <td style=\"padding:10px 1%\">\n              <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing =\"0\">\n                <tr>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_availabilitysett.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" >");
/*  210 */         out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/*  211 */         out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                 ");
/*  212 */         if (!isdelegatedAdmin) {
/*  213 */           out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_alertsettings.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\">");
/*  214 */           out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/*  215 */           out.write("</a>\n                        </td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */         }
/*  217 */         String val = "2";
/*  218 */         if ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP")) && (!OEMUtil.isRemove("am.wmimonitors.remove")))
/*      */         {
/*  220 */           val = "0";
/*      */           
/*  222 */           out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/eventlogrules.do?method=view\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_eventlog.gif\"   vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/eventlogrules.do?method=view\">");
/*  223 */           out.print(FormatUtil.getString("am.webclient.admin.eventlogrules.link"));
/*  224 */           out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */         }
/*      */         
/*      */ 
/*  228 */         out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"5%\" align=\"right\"><a href=\"/alertEscalation.do?method=showRules\" class=\"no-bg-change\"><img src=\"/images/icon_admin_alertescalation.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><span style=\"white-space:nowrap;\"><a  href='/alertEscalation.do?method=showRules'>");
/*  229 */         out.print(FormatUtil.getString("am.webclient.admin.alertescalation.link"));
/*  230 */         out.write(" </a></span></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*  231 */         boolean isDelegatedAdmin = ((Boolean)request.getSession().getAttribute("isDelegatedAdmin")).booleanValue();
/*      */         
/*  233 */         out.write("\n               ");
/*      */         
/*  235 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  236 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  237 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/*  238 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  239 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */           for (;;) {
/*  241 */             out.write("\n               ");
/*      */             
/*  243 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  244 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  245 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/*  247 */             _jspx_th_c_005fwhen_005f0.setTest("${isDelegatedAdmin=='false'}");
/*  248 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  249 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */               for (;;) {
/*  251 */                 out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalTrap&haid=null\"  class=\"no-bg-change\"><img src=\"/images/icon_global_trap.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showGlobalTrap&haid=null\">");
/*  252 */                 out.print(FormatUtil.getString("am.webclient.admin.globaltrap.link"));
/*  253 */                 out.write("</a>\n                        </td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*  254 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  255 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  259 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  260 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */             }
/*      */             
/*  263 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  264 */             out.write("\n                  \n                  ");
/*  265 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  266 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  270 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  271 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */         }
/*      */         else {
/*  274 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  275 */           out.write("\n                </tr>\n                <tr>\n              ");
/*      */           
/*  277 */           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  278 */           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  279 */           _jspx_th_c_005fif_005f1.setParent(null);
/*      */           
/*  281 */           _jspx_th_c_005fif_005f1.setTest("${isDelegatedAdmin=='false'}");
/*  282 */           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  283 */           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */             for (;;) {
/*  285 */               out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                      ");
/*      */               
/*  287 */               if (!usertype.equals("F"))
/*      */               {
/*  289 */                 out.write("\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=listTrapListener\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_snmptrap.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=listTrapListener\" >");
/*  290 */                 out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  291 */                 out.write("</a> </td>\n                        ");
/*      */               } else {
/*  293 */                 out.write("\n                        <td width=\"10%\" align=\"right\"><a  title='");
/*  294 */                 out.print(FormatUtil.getString("am.webclient.freeedition.disabled.function.message"));
/*  295 */                 out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\" valign=\"middle\" title='");
/*  296 */                 out.print(FormatUtil.getString("am.webclient.freeedition.disabled.function.message"));
/*  297 */                 out.write("'><a  class=\"disabledlink\">");
/*  298 */                 out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  299 */                 out.write("</a> </td>\n                        ");
/*      */               }
/*  301 */               out.write("\n                      </tr>\n                    </table>\n                  </td>\n                 ");
/*  302 */               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  303 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  307 */           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  308 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */           }
/*      */           else {
/*  311 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  312 */             out.write("\n                 \n                 ");
/*      */             
/*  314 */             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  315 */             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  316 */             _jspx_th_c_005fif_005f2.setParent(null);
/*      */             
/*  318 */             _jspx_th_c_005fif_005f2.setTest("${isDelegatedAdmin=='false'}");
/*  319 */             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  320 */             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */               for (;;) {
/*  322 */                 out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                      ");
/*      */                 
/*  324 */                 if (!usertype.equals("F"))
/*      */                 {
/*  326 */                   out.write("\n                        <td width=\"10%\" align=\"right\"><a href=\"/DiagnosticInfo.do?method=showDiagnosticInfo\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_snmptrap.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/DiagnosticInfo.do?method=showDiagnosticInfo\" >");
/*  327 */                   out.print(FormatUtil.getString("am.webclient.diagnostic.info.text"));
/*  328 */                   out.write("</a> </td>\n                        ");
/*      */                 } else {
/*  330 */                   out.write("\n                        <td width=\"10%\" align=\"right\"><a  title='");
/*  331 */                   out.print(FormatUtil.getString("am.webclient.freeedition.disabled.function.message"));
/*  332 */                   out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\" valign=\"middle\" title='");
/*  333 */                   out.print(FormatUtil.getString("am.webclient.freeedition.disabled.function.message"));
/*  334 */                   out.write("'><a  class=\"disabledlink\">");
/*  335 */                   out.print(FormatUtil.getString("am.webclient.diagnostic.info.text"));
/*  336 */                   out.write("</a> </td>\n                        ");
/*      */                 }
/*  338 */                 out.write("\n                      </tr>\n                    </table>\n                  </td>\n                 ");
/*  339 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  340 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  344 */             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  345 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */             }
/*      */             else {
/*  348 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  349 */               out.write("\n                 \n                    ");
/*  350 */               if (PluginUtil.isPlugin()) {
/*  351 */                 request.setAttribute("isPlugin", "true");
/*      */               }
/*  353 */               out.write("\n                  ");
/*      */               
/*  355 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  356 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  357 */               _jspx_th_c_005fif_005f3.setParent(null);
/*      */               
/*  359 */               _jspx_th_c_005fif_005f3.setTest("${selectedscheme == 'slim' || isPlugin == 'true'}");
/*  360 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  361 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/*  363 */                   out.write("\n                  \t<td width=\"20%\" align=\"center\">\n                      <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\n                        <tr>\n                          <td width=\"10%\" align=\"right\"><a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_config_alarms.gif\" vspace=\"5\" border=\"0\"></a></td>\n                          <td align=\"left\"><a  style=\"white-space:nowrap;\" href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true\" >");
/*  364 */                   out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/*  365 */                   out.write("</a></td>\n                        </tr>\n                      </table>\n                    </td>\n                    <td width=\"20%\" align=\"center\">\n                      <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\n                        <tr>\n                          <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showActionProfiles\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_assaction.gif\" vspace=\"5\" border=\"0\"></a></td>\n                          <td align=\"left\"><a  style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showActionProfiles\" >");
/*  366 */                   out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/*  367 */                   out.write("</a></td>\n                        </tr>\n                      </table>\n                    </td>\n                    <td width=\"20%\" align=\"center\">\n                      <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\n                        <tr>\n                          <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showThresholds\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_createthreshold.gif\" vspace=\"5\"  border=\"0\"></a></td>\n                          <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showThresholds\" >");
/*  368 */                   out.print(FormatUtil.getString("am.webclient.admin.threshold.link"));
/*  369 */                   out.write("</a></td>\n                        </tr>\n                      </table>\n                    </td>\n                  ");
/*  370 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  371 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  375 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  376 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */               }
/*      */               else {
/*  379 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  380 */                 out.write("\n                </tr>\n              </table>\n            </td>\n        </table>\n\t ");
/*  381 */                 if (!isdelegatedAdmin) {
/*  382 */                   out.write("\n        <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n          <tr>\n            <td class=\"bodytextbold tableheadingbborder\" style=\"height:25px !important; padding:10px 1%\">&nbsp; ");
/*  383 */                   out.print(FormatUtil.getString("am.webclient.admin.appmanagersettings.table.heading", new String[] { OEMUtil.getOEMString("product.name") }));
/*  384 */                   out.write("</td>\n          </tr>\n          <tr>\n            <td style=\"padding:10px 1%\">\n              <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                <tr>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_globalconfig.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\">");
/*  385 */                   out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  386 */                   out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                 <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showServerSettingsConfiguration&typetoshow=general\"  class=\"no-bg-change\"><img src=\"/images/icon_server_settings.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showServerSettingsConfiguration&typetoshow=general\">");
/*  387 */                   out.print(FormatUtil.getString("am.webclient.admin.serversettings.link"));
/*  388 */                   out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showMailServerConfiguration&admin=true\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_mailserver.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showMailServerConfiguration&admin=true\">");
/*  389 */                   out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  390 */                   out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                   
/*  392 */                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  393 */                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  394 */                   _jspx_th_c_005fif_005f4.setParent(null);
/*      */                   
/*  396 */                   _jspx_th_c_005fif_005f4.setTest("${productEdition!='CLOUD'}");
/*  397 */                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  398 */                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                     for (;;) {
/*  400 */                       out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" onClick=\"return checkforwindows('sms')\"  class=\"no-bg-change\"><img src=\"/images/sms-alert-icon.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\"  onClick=\"return checkforwindows('sms')\">");
/*  401 */                       out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  402 */                       out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*  403 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  404 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  408 */                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  409 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                   }
/*      */                   
/*  412 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  413 */                   out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/jsp/ProxyConfiguration.jsp\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_proxyconfig.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/jsp/ProxyConfiguration.jsp\" >");
/*  414 */                   out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  415 */                   out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                 \n                </tr>\n                <tr>\n              \t\t<td width=\"20%\" align=\"center\">\n                    ");
/*  416 */                   if (!PluginUtil.isPlugin()) {
/*  417 */                     out.write("\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\">");
/*  418 */                     if (_jspx_meth_am_005fadminlink_005f0(_jspx_page_context))
/*      */                       return;
/*  420 */                     out.write("</td>\n                        <td align=\"left\"><span style=\"white-space:nowrap;\">");
/*      */                     
/*  422 */                     AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.get(AdminLink.class);
/*  423 */                     _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/*  424 */                     _jspx_th_am_005fadminlink_005f1.setParent(null);
/*      */                     
/*  426 */                     _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*  427 */                     int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/*  428 */                     if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/*  429 */                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  430 */                         out = _jspx_page_context.pushBody();
/*  431 */                         _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/*  432 */                         _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  435 */                         out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/*  436 */                         out.write(32);
/*  437 */                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/*  438 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  441 */                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  442 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  445 */                     if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/*  446 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                     }
/*      */                     
/*  449 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f1);
/*  450 */                     out.write("</span></td>\n                      </tr>\n                    </table>\n                    ");
/*      */                   }
/*  452 */                   out.write("\n                  </td>\n                  ");
/*  453 */                   if ((!OEMUtil.isRemove("am.addonproducts.remove")) && (!isdelegatedAdmin)) {
/*  454 */                     out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&admin=true\"  class=\"no-bg-change\"><img src=\"/images/cfg-servicedesk.gif\" border=\"0\" vspace=\"5\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/extDeviceAction.do?method=showExtDeviceConfigurations&admin=true\">");
/*  455 */                     out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/*  456 */                     out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                   }
/*  458 */                   out.write("\n                  ");
/*  459 */                   if (((!com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) || (!OEMUtil.isRemove("am.admintab.registerlink.remove"))) && (!PluginUtil.isPlugin()) && (!isdelegatedAdmin))
/*      */                   {
/*      */ 
/*  462 */                     out.write("\n                  ");
/*      */                     
/*  464 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  465 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  466 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/*  468 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  469 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  470 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/*  472 */                         out.write("\n                  ");
/*  473 */                         if (!OEMUtil.isRemove("am.admintab.registerlink.remove")) {
/*  474 */                           out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"#\" onClick=\"MM_openBrWindow('/webclient/common/jsp/registerLicence.jsp','Register','width=800,height=550, scrollbars=yes')\"  class=\"no-bg-change\"><img src=\"/images/Register.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"#\" onClick=\"MM_openBrWindow('/webclient/common/jsp/registerLicence.jsp','Register','width=800,height=550, scrollbars=yes')\">");
/*  475 */                           out.print(FormatUtil.getString("am.webclient.admin.license.link"));
/*  476 */                           out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                         }
/*  478 */                         out.write("\n                  ");
/*  479 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  480 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  484 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  485 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                     }
/*      */                     
/*  488 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  489 */                     out.write("\n                  ");
/*      */                     
/*  491 */                     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  492 */                     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  493 */                     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                     
/*  495 */                     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/*  496 */                     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  497 */                     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                       for (;;) {
/*  499 */                         out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><span style=\"white-space:nowrap;\">");
/*  500 */                         if (_jspx_meth_am_005fadminlink_005f2(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                           return;
/*  502 */                         out.write("</span></td>\n                        <td align=\"left\"><span style=\"white-space:nowrap;\">");
/*      */                         
/*  504 */                         AdminLink _jspx_th_am_005fadminlink_005f3 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.get(AdminLink.class);
/*  505 */                         _jspx_th_am_005fadminlink_005f3.setPageContext(_jspx_page_context);
/*  506 */                         _jspx_th_am_005fadminlink_005f3.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                         
/*  508 */                         _jspx_th_am_005fadminlink_005f3.setHref("MM_openBrWindow('/webclient/common/jsp/registerLicence.jsp','Register','width=490,height=300, scrollbars=yes')");
/*  509 */                         int _jspx_eval_am_005fadminlink_005f3 = _jspx_th_am_005fadminlink_005f3.doStartTag();
/*  510 */                         if (_jspx_eval_am_005fadminlink_005f3 != 0) {
/*  511 */                           if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/*  512 */                             out = _jspx_page_context.pushBody();
/*  513 */                             _jspx_th_am_005fadminlink_005f3.setBodyContent((BodyContent)out);
/*  514 */                             _jspx_th_am_005fadminlink_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/*  517 */                             out.print(FormatUtil.getString("am.webclient.admin.license.link"));
/*  518 */                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f3.doAfterBody();
/*  519 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*  522 */                           if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/*  523 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/*  526 */                         if (_jspx_th_am_005fadminlink_005f3.doEndTag() == 5) {
/*  527 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f3); return;
/*      */                         }
/*      */                         
/*  530 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f3);
/*  531 */                         out.write("</span></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*  532 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  533 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  537 */                     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  538 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                     }
/*      */                     
/*  541 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  542 */                     out.write("\n                ");
/*      */                   }
/*  544 */                   out.write("\n                <td width=\"20%\" align=\"center\">\n                  <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                    <tr>\n                      <td width=\"10%\" align=\"right\"><a  href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=logging\" title='");
/*  545 */                   out.print(FormatUtil.getString("Logging"));
/*  546 */                   out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_admin_logging.gif\" vspace=\"5\" border=\"0\"></a></td>\n                      <td align=\"left\"><a  style=\"white-space:nowrap;\"  href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=logging\"> ");
/*  547 */                   out.print(FormatUtil.getString("am.webclient.global.logging.text"));
/*  548 */                   out.write("</a></td>\n                    </tr>\n                  </table>\n                </td>\n                ");
/*  549 */                   if (!OEMUtil.isRemove("am.personalize.remove")) {
/*  550 */                     out.write("\n                ");
/*      */                     
/*  552 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  553 */                     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  554 */                     _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */                     
/*  556 */                     _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/*  557 */                     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  558 */                     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                       for (;;) {
/*  560 */                         out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a style=\"cursor: pointer\" onClick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=690,height=450, scrollbars=yes')\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_personalize.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"cursor: pointer;white-space:nowrap;\" onClick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=690,height=450, scrollbars=yes')\">");
/*  561 */                         out.print(FormatUtil.getString("am.webclient.admin.personalize.link"));
/*  562 */                         out.write("</a></td>\n                      </tr>\n                      </table>\n                    </td>\n                  ");
/*  563 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  564 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  568 */                     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  569 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                     }
/*      */                     
/*  572 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  573 */                     out.write("\n                  ");
/*      */                   }
/*  575 */                   out.write("\n                </tr>\n              </table>\n            </td>\n          </tr>\n        </table>\n\t");
/*      */                 }
/*  577 */                 out.write("\n        <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n          <tr>\n            <td width=\"100%\"  class=\"bodytextbold tableheadingbborder\" style=\"height:25px !important; padding:10px 1%\">&nbsp; ");
/*  578 */                 out.print(FormatUtil.getString("am.webclient.admintab.integration.text"));
/*  579 */                 out.write("</td>\n          </tr>\n          <tr>\n            <td style=\"padding:10px 1%\">\n              <table  width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                <tr>\n                  ");
/*      */                 
/*  581 */                 if (!usertype.equals("F")) {
/*  582 */                   if ((OEMUtil.isOEM()) && (OEMUtil.isRemove("am.apikey.enable")))
/*      */                   {
/*  584 */                     out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=getAPIKey\" title='");
/*  585 */                     out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  586 */                     out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=getAPIKey\">");
/*  587 */                     out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  588 */                     out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                   } else {
/*  590 */                     out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=getAPIKey\" title='");
/*  591 */                     out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  592 */                     out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=getAPIKey\">");
/*  593 */                     out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  594 */                     out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                   }
/*      */                   
/*      */ 
/*      */                 }
/*  599 */                 else if ((OEMUtil.isOEM()) && (OEMUtil.isRemove("am.apikey.enable")))
/*      */                 {
/*  601 */                   out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a  title='");
/*  602 */                   out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  603 */                   out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a  class=\"disabledlink\">");
/*  604 */                   out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  605 */                   out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                 } else {
/*  607 */                   out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a  title='");
/*  608 */                   out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  609 */                   out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_generate_apikey.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a  class=\"disabledlink\">");
/*  610 */                   out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/*  611 */                   out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                 }
/*      */                 
/*  614 */                 out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=jsonfeed\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_jsonfeed.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=jsonfeed\" >");
/*  615 */                 out.print(FormatUtil.getString("am.webcleint.json.text"));
/*  616 */                 out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/MyPage.do?method=newMyPage\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_dashboard.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/MyPage.do?method=newMyPage\" >");
/*  617 */                 out.print(FormatUtil.getString("am.mypage.global.dashboards.text"));
/*  618 */                 out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*  619 */                 if (isdelegatedAdmin) {
/*  620 */                   out.write("\n                  </tr>\n                  <tr>\n                  ");
/*      */                 }
/*  622 */                 out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=gmapkey\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_googlemaps.gif\"  vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=gmapkey\" >");
/*  623 */                 out.print(FormatUtil.getString("am.monitortab.gmap.text"));
/*  624 */                 out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  \n                </tr>\n              </table>\n            </td>\n          </tr>\n        </table>\n\n        <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n          <tr>\n            <td width=\"100%\"  class=\"bodytextbold tableheadingbborder\" style=\"height:25px !important; padding:10px 1%\">&nbsp; ");
/*  625 */                 out.print(FormatUtil.getString("am.webclient.admintab.reporting"));
/*  626 */                 out.write("</td>\n          </tr>\n          <tr>\n            <td style=\"padding:10px 1%\">\n              <table  width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                <tr>\n                ");
/*  627 */                 if (!isdelegatedAdmin) {
/*  628 */                   out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/adminAction.do?method=showDataCleanUp\"  class=\"no-bg-change\"><img src=\"/images/data_cleanup.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/adminAction.do?method=showDataCleanUp\" >");
/*  629 */                   out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/*  630 */                   out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                 }
/*  632 */                 out.write("\n                  ");
/*  633 */                 if (!com.adventnet.appmanager.util.Constants.isPrivilegedUser(request)) {
/*  634 */                   out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/customReports.do?method=showCustomReports\" title='");
/*  635 */                   out.print(FormatUtil.getString("am.webclient.customattribute.image.heading.text"));
/*  636 */                   out.write("'  class=\"no-bg-change\"><img src=\"/images/icon_admin_reports.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/customReports.do?method=showCustomReports\" >");
/*  637 */                   out.print(FormatUtil.getString("am.webclient.customattribute.heading.text"));
/*  638 */                   out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                 }
/*  640 */                 out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/scheduleReports.do?method=showScheduleReports\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_schedulereports.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/scheduleReports.do?method=showScheduleReports\" >");
/*  641 */                 out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  642 */                 out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/businessHours.do?method=showBusinessHours\" title='");
/*  643 */                 out.print(FormatUtil.getString("am.webclient.businesshour.title.text"));
/*  644 */                 out.write("'  class=\"no-bg-change\"><img src=\"/images/icon-business-hours.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/businessHours.do?method=showBusinessHours\" >");
/*  645 */                 out.print(FormatUtil.getString("am.webclient.businesshour.title.text"));
/*  646 */                 out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*  647 */                 if (!isdelegatedAdmin) {
/*  648 */                   out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_sla.png\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/showBussiness.do?method=generateApplicationAvailablity\" >");
/*  649 */                   out.print(FormatUtil.getString("am.webclient.selectmonitorview.SLAview.text"));
/*  650 */                   out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                 } else {
/*  652 */                   out.write("\n                  \n                   <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td colspan=\"2\">&nbsp;</td>\n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                 }
/*  654 */                 out.write("\n                </tr>\n              </table>\n            </td>\n          </tr>\n        </table>\n\n        ");
/*      */                 
/*  656 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  657 */                 _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  658 */                 _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */                 
/*  660 */                 _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/*  661 */                 int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  662 */                 if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                   for (;;) {
/*  664 */                     out.write("\n        ");
/*  665 */                     if ((request.getRemoteUser() != null) && (request.getRemoteUser().equals("admin")))
/*      */                     {
/*  667 */                       out.write("\n        <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n          <tr>\n            <td width=\"100%\"  class=\"bodytextbold tableheadingbborder\" style=\"height:25px !important; padding:10px 1%\">&nbsp; ");
/*  668 */                       out.print(FormatUtil.getString("am.webclient.admin.tools.table.heading"));
/*  669 */                       out.write("</td>\n          </tr>\n          <tr>\n            <td style=\"padding:10px 1%\">\n              <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                <tr>\n                  ");
/*      */                       
/*  671 */                       if ((OEMUtil.isOEM()) && (OEMUtil.isRemove("am.ipaddresssetting.enable")))
/*      */                       {
/*  673 */                         out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>       \n                        <td width=\"10%\" align=\"right\"><a href=\"/changeip.do?method=SetIPConfiguration\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_changeip.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/changeip.do?method=SetIPConfiguration\" >");
/*  674 */                         out.print(FormatUtil.getString("am.webclient.systemsettings.heading.text"));
/*  675 */                         out.write("</a></td>           \n                      </tr>\n                    </table>\n                  </td>\n                  ");
/*      */                       }
/*  677 */                       out.write("\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\">");
/*  678 */                       if (_jspx_meth_am_005fadminlink_005f4(_jspx_th_logic_005fnotPresent_005f2, _jspx_page_context))
/*      */                         return;
/*  680 */                       out.write("</td>\n                        <td align=\"left\"><span style=\"white-space:nowrap;\">");
/*      */                       
/*  682 */                       AdminLink _jspx_th_am_005fadminlink_005f5 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.get(AdminLink.class);
/*  683 */                       _jspx_th_am_005fadminlink_005f5.setPageContext(_jspx_page_context);
/*  684 */                       _jspx_th_am_005fadminlink_005f5.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                       
/*  686 */                       _jspx_th_am_005fadminlink_005f5.setHref("/Upload.do");
/*  687 */                       int _jspx_eval_am_005fadminlink_005f5 = _jspx_th_am_005fadminlink_005f5.doStartTag();
/*  688 */                       if (_jspx_eval_am_005fadminlink_005f5 != 0) {
/*  689 */                         if (_jspx_eval_am_005fadminlink_005f5 != 1) {
/*  690 */                           out = _jspx_page_context.pushBody();
/*  691 */                           _jspx_th_am_005fadminlink_005f5.setBodyContent((BodyContent)out);
/*  692 */                           _jspx_th_am_005fadminlink_005f5.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/*  695 */                           out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/*  696 */                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f5.doAfterBody();
/*  697 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  700 */                         if (_jspx_eval_am_005fadminlink_005f5 != 1) {
/*  701 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/*  704 */                       if (_jspx_th_am_005fadminlink_005f5.doEndTag() == 5) {
/*  705 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f5); return;
/*      */                       }
/*      */                       
/*  708 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f5);
/*  709 */                       out.write("</span></td>\n                      </tr>\n                    </table>\n                  </td>\n                  <td width=\"20%\" align=\"center\">\n                    ");
/*  710 */                       if (!PluginUtil.isPlugin()) {
/*  711 */                         out.write("\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/GlobalActions.do?method=shutdownServer&alert=true\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_shutdown.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/GlobalActions.do?method=shutdownServer&alert=true\" >");
/*  712 */                         out.print(FormatUtil.getString("am.webclient.shutdown.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  713 */                         out.write("</a></td>\n                      </tr>\n                    </table>\n                    ");
/*      */                       } else {
/*  715 */                         out.write("\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td colspan=\"2\">&nbsp;</td>\n                      </tr>\n                    </table>\n                    ");
/*      */                       }
/*  717 */                       out.write("\n                  </td>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td width=\"10%\" align=\"right\"><a href=\"/common/serverinfo.do\"  class=\"no-bg-change\"><img src=\"/images/icon_admin_support.gif\" vspace=\"5\" border=\"0\"></a></td>\n                        <td align=\"left\"><a style=\"white-space:nowrap;\" href=\"/common/serverinfo.do\" >");
/*  718 */                       out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/*  719 */                       out.write("</a></td>\n                      </tr>\n                    </table>\n                  </td>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td colspan=\"2\">&nbsp;</td>\n                      </tr>\n                    </table>\n                  </td>\n                  <td width=\"20%\" align=\"center\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" >\n                      <tr>\n                        <td colspan=\"2\">&nbsp;</td>\n                      </tr>\n                    </table>\n                  </td>\n                </tr>\n              </table>\n                 \n            </td>\n          </tr>\n        </table>\n        ");
/*      */                     }
/*  721 */                     out.write(" \n        ");
/*  722 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/*  723 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  727 */                 if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/*  728 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*      */                 }
/*      */                 else {
/*  731 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*  732 */                   out.write("\n      </td>\n      <!-- Inner tabel ends!-->\n      <td class=\"vcenter-shadow-rigtile\"><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n    </tr>\n    <tr>\n      <td class=\"vcenter-shadow-btm-lcorn\" width=\"8\"><img src=\"/images/spacer.gif\" width=\"8\" /></td>\n      <td class=\"vcenter-shadow-btm-mtile\"></td>\n      <td class=\"vcenter-shadow-btm-rcorn\" width=\"7\"><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n    </tr>\n  </table>\n</div>\n<script language=\"JavaScript\" type=\"text/JavaScript\">\n<!--\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\n  window.open(theURL,winName,features);\n}\nfunction credentialManagerCheck()\n{\n    //include any check required\n    return true;\n}\n//-->\nfunction checkforwindows(type)\n{\n  ");
/*      */                   
/*  734 */                   String ostype = System.getProperty("os.name");
/*  735 */                   if (ostype.indexOf("Windows") != -1)
/*      */                   {
/*      */ 
/*  738 */                     out.write("\n    return true;\n  ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*  743 */                     out.write("\n  if(type ==\"sms\"){\n\n    alert('");
/*  744 */                     out.print(FormatUtil.getString("am.webclinet.smsmodemrules.windowscheck"));
/*  745 */                     out.write("');\n    return false;\n\n  }else if(type == 'servicetemplate'){\n    alert('");
/*  746 */                     out.print(FormatUtil.getString("am.webclient.servicetemplate.windowscheck"));
/*  747 */                     out.write("');\n    return false;\n    }else {\n    alert('");
/*  748 */                     out.print(FormatUtil.getString("am.webclinet.eventlogrules.windowscheck"));
/*  749 */                     out.write("');\n    return false;\n  }\n ");
/*      */                   }
/*      */                   
/*      */ 
/*  753 */                   out.write("\n}\n</script>\n");
/*      */                 }
/*  755 */               } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  756 */         out = _jspx_out;
/*  757 */         if ((out != null) && (out.getBufferSize() != 0))
/*  758 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  759 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  762 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  768 */     PageContext pageContext = _jspx_page_context;
/*  769 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  771 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  772 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  773 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  775 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  777 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  778 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  779 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  780 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  781 */       return true;
/*      */     }
/*  783 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  784 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  789 */     PageContext pageContext = _jspx_page_context;
/*  790 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  792 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/*  793 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/*  794 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/*  796 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.admin.showtemplate.link");
/*  797 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/*  798 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/*  799 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/*  800 */         out = _jspx_page_context.pushBody();
/*  801 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/*  802 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  805 */         out.write("\n                          ");
/*  806 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f0, _jspx_page_context))
/*  807 */           return true;
/*  808 */         out.write("\n                        ");
/*  809 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/*  810 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  813 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/*  814 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  817 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/*  818 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/*  819 */       return true;
/*      */     }
/*  821 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/*  822 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  827 */     PageContext pageContext = _jspx_page_context;
/*  828 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  830 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/*  831 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/*  832 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f0);
/*  833 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/*  834 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/*  835 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/*  836 */         out = _jspx_page_context.pushBody();
/*  837 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/*  838 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  841 */         out.write(32);
/*  842 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_fmt_005fparam_005f0, _jspx_page_context))
/*  843 */           return true;
/*  844 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/*  845 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  848 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/*  849 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  852 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/*  853 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/*  854 */       return true;
/*      */     }
/*  856 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/*  857 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_fmt_005fparam_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  862 */     PageContext pageContext = _jspx_page_context;
/*  863 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  865 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/*  866 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/*  867 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_fmt_005fparam_005f0);
/*      */     
/*  869 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.templatetype.process");
/*  870 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/*  871 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/*  872 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/*  873 */       return true;
/*      */     }
/*  875 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/*  876 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  881 */     PageContext pageContext = _jspx_page_context;
/*  882 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  884 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/*  885 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/*  886 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/*  888 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.admin.showservicetemplate.link");
/*  889 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/*  890 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/*  891 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/*  892 */       return true;
/*      */     }
/*  894 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/*  895 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  900 */     PageContext pageContext = _jspx_page_context;
/*  901 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  903 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/*  904 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/*  905 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/*      */     
/*  907 */     _jspx_th_fmt_005fmessage_005f3.setKey("Credential Manager");
/*  908 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/*  909 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/*  910 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/*  911 */       return true;
/*      */     }
/*  913 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/*  914 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fadminlink_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  919 */     PageContext pageContext = _jspx_page_context;
/*  920 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  922 */     AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  923 */     _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/*  924 */     _jspx_th_am_005fadminlink_005f0.setParent(null);
/*      */     
/*  926 */     _jspx_th_am_005fadminlink_005f0.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */     
/*  928 */     _jspx_th_am_005fadminlink_005f0.setEnableClass("no-bg-change");
/*  929 */     int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/*  930 */     if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/*  931 */       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  932 */         out = _jspx_page_context.pushBody();
/*  933 */         _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/*  934 */         _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  937 */         out.write("<img src=\"/images/icon_admin_useradministrati.gif\" vspace=\"5\" border=\"0\">");
/*  938 */         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/*  939 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  942 */       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  943 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  946 */     if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/*  947 */       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/*  948 */       return true;
/*      */     }
/*  950 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/*  951 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fadminlink_005f2(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  956 */     PageContext pageContext = _jspx_page_context;
/*  957 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  959 */     AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  960 */     _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/*  961 */     _jspx_th_am_005fadminlink_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/*  963 */     _jspx_th_am_005fadminlink_005f2.setHref("/webclient/common/jsp/registerLicence.jsp");
/*      */     
/*  965 */     _jspx_th_am_005fadminlink_005f2.setEnableClass("no-bg-change");
/*  966 */     int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/*  967 */     if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/*  968 */       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/*  969 */         out = _jspx_page_context.pushBody();
/*  970 */         _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/*  971 */         _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  974 */         out.write("<img src=\"/images/Register.gif\"  vspace=\"5\" border=\"0\">");
/*  975 */         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/*  976 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  979 */       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/*  980 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  983 */     if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/*  984 */       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/*  985 */       return true;
/*      */     }
/*  987 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/*  988 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fadminlink_005f4(JspTag _jspx_th_logic_005fnotPresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  993 */     PageContext pageContext = _jspx_page_context;
/*  994 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  996 */     AdminLink _jspx_th_am_005fadminlink_005f4 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  997 */     _jspx_th_am_005fadminlink_005f4.setPageContext(_jspx_page_context);
/*  998 */     _jspx_th_am_005fadminlink_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f2);
/*      */     
/* 1000 */     _jspx_th_am_005fadminlink_005f4.setHref("/Upload.do");
/*      */     
/* 1002 */     _jspx_th_am_005fadminlink_005f4.setEnableClass("no-bg-change");
/* 1003 */     int _jspx_eval_am_005fadminlink_005f4 = _jspx_th_am_005fadminlink_005f4.doStartTag();
/* 1004 */     if (_jspx_eval_am_005fadminlink_005f4 != 0) {
/* 1005 */       if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/* 1006 */         out = _jspx_page_context.pushBody();
/* 1007 */         _jspx_th_am_005fadminlink_005f4.setBodyContent((BodyContent)out);
/* 1008 */         _jspx_th_am_005fadminlink_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1011 */         out.write("<img src=\"/images/icon_admin_fileupload.gif\"  vspace=\"5\" border=\"0\">");
/* 1012 */         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f4.doAfterBody();
/* 1013 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1016 */       if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/* 1017 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1020 */     if (_jspx_th_am_005fadminlink_005f4.doEndTag() == 5) {
/* 1021 */       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f4);
/* 1022 */       return true;
/*      */     }
/* 1024 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f4);
/* 1025 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Admin_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */