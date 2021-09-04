/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.EnterpriseAdminLink;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class GlobalSettings_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   34 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   40 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   41 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*   42 */     _jspx_dependants.put("/jsp/includes/EnterpriseAdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fdisabled_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fdisabled_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   90 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   94 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  101 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  102 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  103 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  104 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  105 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  106 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  107 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  108 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  109 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  110 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  111 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  112 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  113 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  114 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  115 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  116 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  117 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  118 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  119 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  120 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  121 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  122 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  123 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  124 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  125 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  126 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  127 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  128 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  129 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  130 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  131 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  132 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  133 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  134 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  135 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  139 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  140 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  141 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  142 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  143 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  144 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  145 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  146 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.release();
/*  147 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  148 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*  149 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  150 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction.release();
/*  151 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*  152 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*  153 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  154 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.release();
/*  155 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.release();
/*  156 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fnobody.release();
/*  157 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fdisabled_005fnobody.release();
/*  158 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/*  159 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.release();
/*  160 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  161 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.release();
/*  162 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*  163 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  164 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fmaxlength_005fnobody.release();
/*  165 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.release();
/*  166 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  167 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.release();
/*  168 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.release();
/*  169 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  170 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/*  171 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fdisabled_005fnobody.release();
/*  172 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  173 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  174 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  175 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.release();
/*  176 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
/*  177 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.release();
/*  178 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  185 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  188 */     JspWriter out = null;
/*  189 */     Object page = this;
/*  190 */     JspWriter _jspx_out = null;
/*  191 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  195 */       response.setContentType("text/html;charset=UTF-8");
/*  196 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  198 */       _jspx_page_context = pageContext;
/*  199 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  200 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  201 */       session = pageContext.getSession();
/*  202 */       out = pageContext.getOut();
/*  203 */       _jspx_out = out;
/*      */       
/*  205 */       out.write("<!DOCTYPE html>\n");
/*  206 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  207 */       out.write("\n\n\n\n\n\n\n\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/differentialPolling.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/optimizedDC.js\" type=\"text/javascript\"></SCRIPT>\n");
/*      */       
/*  209 */       String reloadParent = request.getParameter("reloadParent");
/*  210 */       String type = request.getParameter("typetoshow");
/*  211 */       String fromwhere = (String)request.getAttribute("viewthreshold");
/*  212 */       String heading = FormatUtil.getString("am.webclient.global.settingsheading.text");
/*  213 */       String helpcardKey = FormatUtil.getString("am.webclient.performancepolling.helpcard");
/*  214 */       String monitorType = request.getAttribute("MonitorType") != null ? (String)request.getAttribute("MonitorType") : "";
/*  215 */       String resourceID = request.getParameter("resourceID") != null ? request.getParameter("resourceID") : "";
/*  216 */       String dcComponentName = request.getAttribute("dcComponentName") != null ? (String)request.getAttribute("dcComponentName") : "";
/*  217 */       String showMsg = request.getAttribute("showMsg") != null ? (String)request.getAttribute("showMsg") : "";
/*  218 */       if (dcComponentName.equals("")) {
/*  219 */         dcComponentName = request.getParameter("dcComponentName") != null ? request.getParameter("dcComponentName") : "";
/*      */       }
/*  221 */       String showMsgNonConf = request.getParameter("showMsgNonConf") != null ? request.getParameter("showMsgNonConf") : "false";
/*      */       
/*  223 */       if ((type != null) && (type.equals("general")))
/*      */       {
/*  225 */         heading = FormatUtil.getString("am.webclient.global.settingsheading.text");
/*  226 */         request.setAttribute("HelpKey", "Global Settings");
/*      */       }
/*  228 */       else if (type.equalsIgnoreCase("Actionalert"))
/*      */       {
/*  230 */         heading = FormatUtil.getString("am.webclient.global.actionheading.text");
/*  231 */         request.setAttribute("HelpKey", "Action Alert Settings");
/*      */       }
/*  233 */       else if (type.equalsIgnoreCase("Availablity"))
/*      */       {
/*  235 */         heading = FormatUtil.getString("am.webclient.global.availablityheading.text");
/*  236 */         request.setAttribute("HelpKey", "Availability Settings");
/*      */       }
/*  238 */       else if (type.equalsIgnoreCase("gmapkey"))
/*      */       {
/*  240 */         heading = FormatUtil.getString("am.webclient.gmapsettings.text");
/*  241 */         request.setAttribute("HelpKey", "Google Map");
/*      */       }
/*  243 */       else if (type.equalsIgnoreCase("performance"))
/*      */       {
/*      */ 
/*  246 */         heading = FormatUtil.getString("am.webclient.performancepolling.datacollectiontext");
/*  247 */         request.setAttribute("HelpKey", "Performance Polling");
/*  248 */       } else if (type.equalsIgnoreCase("logging"))
/*      */       {
/*      */ 
/*  251 */         heading = FormatUtil.getString("am.webclient.global.logging.text");
/*  252 */         request.setAttribute("HelpKey", "Global Settings");
/*      */       }
/*  254 */       else if (type.equalsIgnoreCase("jsonfeed"))
/*      */       {
/*  256 */         heading = FormatUtil.getString("am.webcleint.json.text");
/*  257 */         request.setAttribute("HelpKey", "JSON Feed");
/*      */       }
/*      */       
/*  260 */       if ((reloadParent == null) || (reloadParent.trim().length() == 0)) {
/*  261 */         reloadParent = "false";
/*      */       }
/*      */       
/*  264 */       out.write("\n<script>\nfunction replHistChange() {\n\tif($(\"input[name='sqlReplMonHistoryStatus']:checked\").length==0) {\t\n\t\tjQuery(\"#replHist\").hide(); //No I18N\n\t} else {\n\t\tjQuery(\"#replHist\").show(); //No I18N\n\t}\n}\n\nfunction replMonChange() {\n\tif($(\"input[name='sqlReplMonStatus']:checked\").length==0) {\t\n\t\t$(\"input[name='sqlReplMonHistoryStatus']\").attr(\"disabled\",\"disabled\"); // NO I18N\n\t\t$(\"input[name='sqlReplMonHistoryStatus']\").removeAttr(\"checked\"); // NO I18N\n\t} else {\n\t\t$(\"input[name='sqlReplMonHistoryStatus']\").removeAttr(\"disabled\"); // NO I18N\n\t}\t\n}\n\nvar http = getHTTPObject(); // We create the HTTP Object\nfunction getHTTPObject() {\n  var xmlhttp;\n  if (window.ActiveXObject){\n    try {\n      xmlhttp = new ActiveXObject(\"Msxml2.XMLHTTP\");\n    } catch (e) {\n      try {\n        xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n      } catch (E) {\n        xmlhttp = false;\n      }\n    }\n}\n  else if (typeof XMLHttpRequest != 'undefined') {\n    try {\n      xmlhttp = new XMLHttpRequest();\n    } catch (e) {\n      xmlhttp = false;\n");
/*  265 */       out.write("    }\n  }\n  return xmlhttp;\n}\n//var  x='p'arun';\n//x.replace(\"'\",\"\\\\'\");\n//alert(x);\n\nfunction addLocation() {\n\n\tvar url = \"/adminAction.do?method=addLocation\";\t\t// NO I18N\n\tvar win=window.open(url,'','resizable=yes,scrollbars=yes,width=600,height=600');\n\twin.focus();\n\treturn false;\n}\n\nfunction deleteLocation()\n{\n\tvar id=document.AMActionForm.locationid.value;\n\tif(id==\"\")\n\t{\n\t\talert(\"");
/*  266 */       out.print(FormatUtil.getString("am.webclient.gmap.selectlocation.text"));
/*  267 */       out.write("\");\n\t\treturn false;\n\t}\n\tif(confirm('");
/*  268 */       out.print(FormatUtil.getString("am.webclient.gmap.confirmdelete.text"));
/*  269 */       out.write("'))\n\t{\n\t\tdocument.AMActionForm.action=\"/adminAction.do?method=deleteLocation&id=\"+id;\n\t\tdocument.AMActionForm.submit();\n\t}\n\telse\n\t\treturn false;\n\n}\n\nfunction addLocationEntry(id,name) {\n\n\tdocument.AMActionForm.locationid.options[document.AMActionForm.locationid.length] =\n\t\tnew Option(name,id,false,true);\n}\n\n\nfunction checkUrl(val)\n{\n\n\tif(val==\"\")\n\t{\n\t\talert(\"");
/*  270 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.url"));
/*  271 */       out.write("\");\n\n\t\treturn false;\n\t}\n\tif(!checkUrlPattern(val))\n\t{\n\t\talert(\"");
/*  272 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.urladd"));
/*  273 */       out.write("\");\n\n\t\treturn ;\n\t}\n\tvar url=\"/adminAction.do?method=checkUrlAvailability&urlcheck=\"+val;\n\thttp.open(\"GET\",url,true);\n\thttp.onreadystatechange = handleUrlCheckDetail;\n\thttp.send(null);\n}\nfunction handleUrlCheckDetail()\n{\n\n\tif(http.readyState == 4)\n\t{\n\t\tresult = http.responseText;\n\n\t\tvar element=document.AMActionForm.gatewayUrlName;\n\t\tvar temp=null;\n\t\tvar isPointerReq=false;\n\t\tvar red=\"#FF0000\";\n\t\tddrivetip(element,temp,result,isPointerReq,null,red,300);\n\t}\n}\nfunction changeTabStyle(selectedTabId){\t\n\tvar tabIds=[\"performancelink\",\"diskiolink\",\"wlslink\",\"amazonlink\",\"snmplink\",\"urllink\",\"DBlink\",\"webserviceslink\",\"pinglink\",\"configureDClink\",\"configureMSSQLlink\"];//No I18N\n\tfor (var i = 0; i < tabIds.length; i++) {\n\t\tvar tabId=tabIds[i];\n\t\tif(selectedTabId ==tabId){\n\t\t\tdocument.getElementById(tabId+\"-left\").className = \"tbSelected_Left\";//No I18N\n\t\t\tdocument.getElementById(tabId).className = \"tbSelected_Middle\";//No I18N\n\t\t\tdocument.getElementById(tabId+\"-right\").className = \"tbSelected_Right\";//No I18N\n\t\t}\n\t\telse{\n");
/*  274 */       out.write("\t\t\tdocument.getElementById(tabId+\"-left\").className = \"tbUnSelected_Left\";//No I18N\n\t\t\tdocument.getElementById(tabId).className = \"tbUnSelected_Middle\";//No I18N\n\t\t\tdocument.getElementById(tabId+\"-right\").className = \"tbUnSelected_Right\";//No I18N\n\t\t}\n\t}\n}\nfunction showHideContent(selectedDivId,hidemainDiv){\t\n\tvar divIds=[\"performance\",\"diskio\",\"weblogic\",\"amazon\",\"snmp\",\"url\",\"Database\",\"webservices\",\"ping\",\"configureDC\",\"configureMSSQL\"];//No I18N\n\tfor (var i = 0; i < divIds.length; i++) {\n\t\tvar divId=divIds[i];\n\t\tif(selectedDivId ==divId){\n\t\t\tjavascript:showDiv(divId+\"Heading\");//No I18N\n\t\t\tjavascript:showDiv(divId);//No I18N\n\t\t\tjavascript:showDiv(divId+\"Help\");//No I18N\n\t\t}\n\t\telse{\n\t\t\tjavascript:hideDiv(divId+\"Heading\");//No I18N\n\t\t\tjavascript:hideDiv(divId);//No I18N\n\t\t\tjavascript:hideDiv(divId+\"Help\");\t\t//No I18N\t\t\n\t\t}\n\t}\n\tif(hidemainDiv){\n\t\tjavascript:hideDiv(\"mainDiv\");//No I18N\n\t}\n\telse{\n\t\tjavascript:showDiv(\"mainDiv\");//No I18N\n\t}\n\tif(selectedDivId=='configureDC'){\n\t\tgetDCComponents('");
/*  275 */       out.print(monitorType);
/*  276 */       out.write(39);
/*  277 */       out.write(44);
/*  278 */       out.write(39);
/*  279 */       out.print(dcComponentName);
/*  280 */       out.write(39);
/*  281 */       out.write(44);
/*  282 */       out.write(39);
/*  283 */       out.print(showMsg);
/*  284 */       out.write("');\n\t}\n\t");
/*  285 */       if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) {
/*  286 */         out.write("\n\tif(selectedDivId=='configureMSSQL') {\n\t\tgetDCComponentsNonConf('MSSQL-DB-server','");
/*  287 */         out.print(resourceID);
/*  288 */         out.write(39);
/*  289 */         out.write(44);
/*  290 */         out.write(39);
/*  291 */         out.print(dcComponentName);
/*  292 */         out.write(39);
/*  293 */         out.write(44);
/*  294 */         out.write(39);
/*  295 */         out.print(showMsgNonConf);
/*  296 */         out.write("'); //No I18N\n\t}\n\t");
/*      */       }
/*  298 */       out.write("\n}\n\nfunction showHide(show)\n{\n\t\tif(show==\"performance\")\t{//No I18N\n\t\t\tchangeTabStyle(\"performancelink\");//No I18N\n\t\t\tshowHideContent(\"performance\",false);//No I18N\n\t\t}\t\n\t\telse if(show=='diskio'){//No I18N\n\t\t\tchangeTabStyle(\"diskiolink\");//No I18N\n\t\t\tshowHideContent(\"diskio\",false);//No I18N\n\t\t}\n\t\telse if(show=='DataBase'){//No I18N\n\t\t\tchangeTabStyle(\"DBlink\");//No I18N\n\t\t\tshowHideContent(\"Database\",true);\t\t//No I18N\n\t\t}\t\t\n\t\telse  if(show=='snmp'){//No I18N\n\t\t\tchangeTabStyle(\"snmplink\");//No I18N\n\t\t\tshowHideContent(\"snmp\",false);\t\t//No I18N\n\t\t\t}\n\t\t\telse  if(show=='url-div')\t{//No I18N\n\t\t\t\tchangeTabStyle(\"urllink\");//No I18N\n\t\t\t\tshowHideContent(\"url\",false);\t\t//No I18N\n\t\t\t}\n\t\t\telse  if(show=='amazon')\t\t\t\t    {//No I18N\n\t\t\t\tchangeTabStyle(\"amazonlink\");//No I18N\n\t\t\t\tshowHideContent(\"amazon\",false);\t\t\t//No I18N\n\t\t\t}\n\t\t\telse  if(show=='webservices') {//No I18N\n\t\t\t\tchangeTabStyle(\"webserviceslink\");//No I18N\n\t\t\t\tshowHideContent(\"webservices\",false);\t\t\t//No I18N\n\t\t\t}\n\t\t\telse  if(show=='ping') {//No I18N\n\t\t\t\tchangeTabStyle(\"pinglink\");//No I18N\n");
/*  299 */       out.write("\t\t\t\tshowHideContent(\"ping\",false);\t\t\t//No I18N\n\t\t\t}\n\t\t\telse if(show=='configureDC'){\n\t\t\t\tchangeTabStyle(\"configureDClink\");//No I18N\n\t\t\t\tshowHideContent(\"configureDC\",true);\t\t\t//No I18N\n\t\t\t}\n\t\t\telse if(show=='configureMSSQL') {\n\t\t\t\tchangeTabStyle(\"configureMSSQLlink\");//No I18N\n\t\t\t\tshowHideContent(\"configureMSSQL\",true);\t\t\t//No I18N\n\t\t\t}\n\t\t\telse\t\t\t{\n\t\t\t\tchangeTabStyle(\"wlslink\");//No I18N\n\t\t\t\tshowHideContent(\"weblogic\",false);\t\t\t//No I18N\n\t\t\t}\t\t\n}\n\n\nfunction disableWebApp(checkboxObj)\n{\n\n                if(!checkboxObj.checked)\n                {\n                        hideDiv('wlswebappselect');\n\t\t\thideDiv('processingmonitors');\n                }\n                else\n                {\n\t\t\tshowDiv('processingmonitors');\n\t\t\tshowApplySelectedOnChange('webapp') ;\n                }\n}\n\nfunction showApplySelectedOnChange(comptype)\n{\n\tvar url;\n       \tobj = getHTTPObject();\n\t");
/*  300 */       long millis = System.currentTimeMillis();
/*  301 */       out.write("\n\tif(comptype == 'webapp')\n\t{\n\t\turl = '/jsp/includes/WLSDisableWebapp.jsp?cach='+ '");
/*  302 */       out.print(millis);
/*  303 */       out.write("';\n        \tobj.open(\"GET\",url,true);\n\t        obj.onreadystatechange = handleWebappResponse;\n\t}\n\telse if(comptype == 'ejb')\n\t{\n\t\turl = '/jsp/includes/WLSDisableEjb.jsp?cach='+ '");
/*  304 */       out.print(millis);
/*  305 */       out.write("' ;\n\n        \tobj.open(\"GET\",url,true);\n\t        obj.onreadystatechange = handleEjbResponse;\n\t}\n\telse\n\t{\n\t\turl = '/jsp/includes/WLSDisableServlet.jsp?cach=' + '");
/*  306 */       out.print(millis);
/*  307 */       out.write("';\n        \tobj.open(\"GET\",url,true);\n\t        obj.onreadystatechange = handleServletResponse;\n\t}\n\n        obj.send(null);\n}\nfunction enableWebappCheckbox()\n{\n        obj1 = getHTTPObject();\n\t\tvar url = '/jsp/includes/WLSDisableWebapp.jsp?cach='+ '");
/*  308 */       out.print(millis);
/*  309 */       out.write("';\n        \tobj1.open(\"GET\",url,true);\n\t        obj1.onreadystatechange = new Function('if(obj1.readyState == 4 && obj1.status == 200){document.getElementById(\"wlswebappselect\").innerHTML = obj1.responseText,document.getElementById(\"wlswebappselect\").style.display=\"block\";}');\n        obj1.send(null);\n}\nfunction enableEjbCheckbox()\n{\n\n        obj2 = getHTTPObject();\n\t\tvar url = '/jsp/includes/WLSDisableEjb.jsp?cach='+ '");
/*  310 */       out.print(millis);
/*  311 */       out.write("' ;\n        \tobj2.open(\"GET\",url,true);\n        obj2.onreadystatechange = new Function('if(obj2.readyState == 4 && obj2.status == 200){document.getElementById(\"wlsejbselect\").innerHTML = obj2.responseText,document.getElementById(\"wlsejbselect\").style.display=\"block\";}');\n\n\n        obj2.send(null);\n}\n\nfunction enableServletCheckbox()\n{\n        obj3 = getHTTPObject();\n\t\tvar url = '/jsp/includes/WLSDisableServlet.jsp?cach=' + '");
/*  312 */       out.print(millis);
/*  313 */       out.write("';\n        \tobj3.open(\"GET\",url,true);\n\t        obj3.onreadystatechange = new Function('if(obj3.readyState == 4 && obj3.status == 200){document.getElementById(\"wlsservletselect\").innerHTML = obj3.responseText,document.getElementById(\"wlsservletselect\").style.display=\"block\";}')\n\t\t\t;\n        obj3.send(null);\n\n}\nfunction handleWebappResponse()\n{\n        if(obj.readyState == 4)\n        {\n                var result = obj.responseText;\n                document.getElementById(\"wlswebappselect\").innerHTML = result;\n                document.getElementById(\"wlswebappselect\").style.display='block';\n                document.getElementById(\"processingmonitors\").style.display='none';\n        }\n}\n\nfunction handleEjbResponse()\n{\n        if(obj.readyState == 4)\n        {\n                var result = obj.responseText;\n                document.getElementById(\"wlsejbselect\").innerHTML = result;\n                document.getElementById(\"wlsejbselect\").style.display='block';\n                document.getElementById(\"processingmonitors\").style.display='none';\n");
/*  314 */       out.write("        }\n}\n\nfunction handleServletResponse()\n{\n        if(obj.readyState == 4)\n        {\n                var result = obj.responseText;\n                document.getElementById(\"wlsservletselect\").innerHTML = result;\n                document.getElementById(\"wlsservletselect\").style.display='block';\n                document.getElementById(\"processingmonitors\").style.display='none';\n        }\n}\n\nfunction disableEjb(checkboxObj)\n{\n      if(!checkboxObj.checked)\n      {\n           hideDiv('wlsejbselect');\n       \t   hideDiv('processingmonitors');\n      }\n      else\n      {\n           showDiv('processingmonitors');\n\t   showApplySelectedOnChange('ejb') ;\n      }\n}\n\nfunction disableServlet(checkboxObj)\n{\n      if(!checkboxObj.checked)\n      {\n           hideDiv('wlsservletselect');\n       \t   hideDiv('processingmonitors');\n      }\n      else\n      {\n           showDiv('processingmonitors');\n\t   showApplySelectedOnChange('servlet') ;\n      }\n}\n\nfunction OnDivScroll(divObject)\n{\n      var lstAvailableMonitors = \"\";\n\n      if(divObject.id == \"divEnableWebapp\")\n");
/*  315 */       out.write("                lstAvailableMonitors = document.getElementById(\"webapp_enableid\");\n      else if(divObject.id == \"divDisableWebapp\")\n\t\tlstAvailableMonitors = document.getElementById(\"webapp_disableid\");\n      else if(divObject.id == \"divEjbEnable\")\n\t\tlstAvailableMonitors = document.getElementById(\"ejb_enableid\");\n      else if(divObject.id == \"divEjbDisable\")\n\t\tlstAvailableMonitors = document.getElementById(\"ejb_disableid\");\n      else if(divObject.id == \"divServletEnable\")\n\t\tlstAvailableMonitors = document.getElementById(\"servlet_enableid\");\n      else\n\t\tlstAvailableMonitors = document.getElementById(\"servlet_disableid\");\n\n\t\n      if (lstAvailableMonitors.options.length > 8)\n      {\n          lstAvailableMonitors.size=lstAvailableMonitors.options.length;\n      }\n      else\n      {\n         lstAvailableMonitors.size=8;\n      }\n}\nfunction OnSelectFocus(selectObject)\n{\n        var divObjectId = \"\";\n        if(selectObject.id == \"webapp_enableid\")\n\t{\n                divObjectId = \"divEnableWebapp\";\n\t}\n\telse if(selectObject.id == \"webapp_disableid\")\n");
/*  316 */       out.write("\t{\n                divObjectId = \"divDisableWebapp\";\n\t}\n        else if(selectObject.id == \"ejb_enableid\")\n\t{\n                divObjectId = \"divEjbEnable\";\n\t}\n        else if(selectObject.id == \"ejb_disableid\")\n\t{\n                divObjectId = \"divEjbDisable\";\n\t}\n        else if(selectObject.id == \"servlet_enableid\")\n\t{\n                divObjectId = \"divServletEnable\" ;\n\t}\n        else\n\t{\n                divObjectId = \"divServletDisable\";\n\t}\n\n        if (document.getElementById(divObjectId).scrollLeft != 0)\n        {\n              document.getElementById(divObjectId).scrollLeft = 0;\n        }\n\n        var lstAvailableMonitors = document.getElementById(selectObject.id);\n        if( lstAvailableMonitors.options.length > 8)\n        {\n              lstAvailableMonitors.focus();\n             // lstAvailableMonitors.size=8;\n        }\n}\n\nfunction fnAddToRightAndSetScrollSize(fromCombo,toCombo)\n{\n        //fnAddToRightCombo(fromCombo,toCombo);\n\tfnAddToRightAndRemoveSelected(fromCombo,toCombo);\n\n        if(toCombo.options.length > 8)                  // For Scrollbar adjustments in div tag\n");
/*  317 */       out.write("                toCombo.size = toCombo.options.length;\n        if(fromCombo.options.length > 8)\n                fromCombo.size = fromCombo.options.length;\n        else\n                fromCombo.size = 8;\n}\n\nfunction fnRemoveAndSetScrollSize(clearCombo)\n{\n        fnRemoveFromRightCombo(clearCombo);\n        if(clearCombo.length == 0)\n        {\n                clearCombo.size = 8;\n        }\n}\n\nfunction fnAddAllFrmCombo(fromCombo, toCombo)\n{\n\n        frmSelectAll(fromCombo);\n       // fnAddToRightCombo(fromCombo, toCombo);\n\tfnAddToRightAndRemoveSelected(fromCombo,toCombo);\n        if(toCombo.options.length > 8)\n        toCombo.size = toCombo.options.length;\n}\n\nfunction fnAddToRightAndRemoveSelected(availableresource, selectedcombo)\n{\n        var count = availableresource.length;\n           for(k=0;k<parseInt(count);k++)\n           {\n               if(availableresource.options[k].selected)\n               {\n                   value = availableresource.options[k].value;\n                   var option0 = new Option(availableresource.options[k].text, value);\n");
/*  318 */       out.write("                   selectedcombo.options[selectedcombo.length]=option0;\n                   availableresource.options[k]=null;\n                   count--;\n                   k--;\n               }\n           }\n           return;\n}\n\nfunction fnRemoveAllFrmCombo(clearCombo)\n{\n        clearCombo.length=0;\n        clearCombo.size = 8;\n}\n\nvar bIsOpenhosttoappDiv=false;\n\nvar bIsOpenrcaDiv=false;\n\nvar bIsOpennetworkDiv=true;\n\nvar bIsOpensmsDiv=true;\n\nvar bIsOpenactionDiv=true;\nvar bIsOpenintroDiv=true;\nvar bIsOpenmonitorhelpDiv=true;\nvar bIsOpenretrypollsDiv=true;\nvar bIsOpenlogDiv=true;\nvar bIsOpenrepactionsDiv=false;\n\n\n\n\n\nvar plusSign                    = \"../images/icon_plus.gif\";\nvar minusSign                   = \"../images/icon_minus.gif\";\nvar currentProfileId            = \"-1\";\n\n\n// Inverts the state of the given expand menu name + the related div (if any).\nfunction InvertDivState(name, divName){\n\tvar oDiv  = document.getElementById(name+\"Div\");\n\tvar oSpan = document.getElementById(name+\"Sign\");\n\tvar oTd   = document.getElementById(name+\"Td\");\n");
/*  319 */       out.write("\tvar bIsOpen = eval(oDiv.style.display=='none');\n\teval(bIsOpen);\n\tif(bIsOpen){\n\t\tif(typeof(divName)!=\"undefined\") document.getElementById(divName).style.display = \"block\";\n\t\toDiv.style.display = \"block\";\n\t\toSpan.src = minusSign;\n\t}else {\n\t\tif(typeof(divName)!=\"undefined\") document.getElementById(divName).style.display = \"none\";\n\t\toDiv.style.display = \"none\";\n\t\toSpan.src = plusSign;\n\t}\n\ttoggle(name+\"Show\");\n\ttoggle(name+\"Hide\");\n}\n\nfunction toggle(divname)\n{\n\tif(document.getElementById(divname).style.display == 'inline')\n\t\tdocument.getElementById(divname).style.display='none';\n\telse\n\t\tdocument.getElementById(divname).style.display='inline';\n}\n");
/*  320 */       if ((type != null) && ((type.equals("Availablity")) || (type.equals("performance"))))
/*      */       {
/*  322 */         String fromadd = com.adventnet.appmanager.util.Constants.EMAIL_ADDRESS;
/*  323 */         out.write("\n\tfunction myOnLoad()\n\t{//this method - myOnLoad - gets called from BasicLayoutNoLeft.jsp\n        \n        var productEdition=\"");
/*  324 */         out.print(request.getAttribute("productEdition"));
/*  325 */         out.write("\";\n               \n                   if(productEdition=='CLOUD')\n                       {\n                         jQuery(\"#oracletab\").hide();//NO I18N  \n                           jQuery(\"#weblogictab\").hide();//NO I18N  \n                            jQuery(\"#mssqltab\").hide();//NO I18N\n                       }\n\t\t");
/*      */         
/*      */ 
/*  328 */         if (type.equals("performance"))
/*      */         {
/*  330 */           if (request.getParameter("showDiskIOLink") != null)
/*      */           {
/*  332 */             out.write("\n\t\t\t\t changeTabStyle(\"diskiolink\");\t\t//NO I18N\t\t\n\t\t\t\t showHideContent(\"diskio\",false);\t//NO I18N\t\n\t\t\t");
/*      */           }
/*  334 */           else if (request.getParameter("showDBLink") != null)
/*      */           {
/*  336 */             out.write("\n\t\t\t\t changeTabStyle(\"DBlink\");\t\t\t\t//NO I18N\n\t\t\t\t showHideContent(\"Database\",true);\t\t//NO I18N\t\t\t\t\n\t\t\t");
/*      */           }
/*  338 */           else if (request.getParameter("showWeblogicLink") != null)
/*      */           {
/*  340 */             out.write("\n\t\t\t \tchangeTabStyle(\"wlslink\");\t//NO I18N\n\t\t\t \tshowHideContent(\"weblogic\",false);\t\t\t\t//NO I18N\t\t\t\n\t\t\t");
/*      */           }
/*  342 */           else if (request.getParameter("showSnmpLink") != null)
/*      */           {
/*  344 */             out.write("\n\t\t\t\tchangeTabStyle(\"snmplink\");\t//NO I18N\n\t\t\t \tshowHideContent(\"snmp\",false);\t\t//NO I18N\n\t\t\t");
/*      */           }
/*  346 */           else if (request.getParameter("showUrlLink") != null)
/*      */           {
/*  348 */             out.write("\n\t\t\t\tchangeTabStyle(\"urllink\");\t//NO I18N\n\t\t\t \tshowHideContent(\"url\",false);\t\t\t\t//NO I18N\n\t\t\t");
/*      */           }
/*  350 */           else if (request.getParameter("showAmazonLink") != null)
/*      */           {
/*  352 */             out.write("\n\t\t\t\tchangeTabStyle(\"amazonlink\");\t//NO I18N\n\t\t\t \tshowHideContent(\"amazon\",false);\t\t\t//NO I18N\n\t\t\t");
/*      */           }
/*  354 */           else if (request.getParameter("showWebservicesLink") != null)
/*      */           {
/*  356 */             out.write("\n\t\t\t\tchangeTabStyle(\"webserviceslink\");\t//NO I18N\n\t\t\t \tshowHideContent(\"webservices\",false);\t\t\t//NO I18N\n\t\t\t");
/*      */           }
/*  358 */           else if (request.getParameter("showPingLink") != null)
/*      */           {
/*  360 */             out.write("\n\t\t\t\tchangeTabStyle(\"pinglink\");\t//NO I18N\n\t\t\t \tshowHideContent(\"ping\",false);\t\t\t//NO I18N\n\t\t\t");
/*      */           }
/*  362 */           else if (request.getParameter("showconfigureDC") != null) {
/*  363 */             out.write("\n\t\t\t\tchangeTabStyle(\"configureDClink\");\t//NO I18N\n\t\t\t \tshowHideContent(\"configureDC\",true);\t\t\t//NO I18N\t\t\t\n\t\t\t");
/*      */           }
/*  365 */           else if (request.getParameter("showconfigureMSSQL") != null) {
/*  366 */             out.write("\n\t\t\t\tchangeTabStyle(\"configureMSSQLlink\");\t//NO I18N\n\t\t\t \tshowHideContent(\"configureMSSQL\",true);\t\t\t//NO I18N\t\t\t\n\t\t\t");
/*      */           }
/*      */           else
/*      */           {
/*  370 */             out.write("\n\t\t\t\tshowHideContent(\"performance\",false);\t//NO I18N\t\t\t\t\n\t\t ");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  375 */           out.write("\n\t\t javascript:showDiv(\"AvailabilityHelp\");\n\t\t\tif(document.AMActionForm.gatewayCheckStatus.checked)\n\t\t\t{\n\t\t\t\t//document.AMActionForm.gatewayName.disabled=false;\n\t\t\t\tdocument.getElementById(\"networkgate\").style.display=\"block\";\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t//document.AMActionForm.gatewayName.disabled=true;\n\t\t\t\tdocument.getElementById(\"networkgate\").style.display=\"none\";\n\t\t\t}\n\t\t\tif(document.AMActionForm.gatewayUrlStatus.checked)\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"urlgate\").style.display=\"block\";\n\t\t\t\t/*document.AMActionForm.gatewayUrlName.disabled=false;\n\t\t\t\tdocument.AMActionForm.sendmail.disabled=false;*/\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"urlgate\").style.display=\"none\";\n\t\t\t\t/* document.AMActionForm.gatewayUrlName.disabled=true;\n\t\t\t\tdocument.AMActionForm.sendmail.disabled=true;*/\n\t\t\t}\n\t\t\tif((trimAll(document.AMActionForm.fromaddress.value)==\"\"))\n\t\t\t{\n\t\t\t\tdocument.AMActionForm.fromaddress.value=\"");
/*  376 */           out.print(fromadd);
/*  377 */           out.write("\";\n\t\t\t}\n\t\t\t\n\t  ");
/*      */         }
/*  379 */         out.write("\n\n\t}\n\tfunction onCheckBox()\n\t{\n\t\tif(document.AMActionForm.gatewayUrlStatus.checked)\n\t\t{\n\t\t\tdocument.getElementById(\"urlgate\").style.display=\"block\";\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"urlgate\").style.display=\"none\";\n\t\t}\n\t}\n\tfunction onCheckBox1()\n\t{\n\t\tif(document.AMActionForm.gatewayCheckStatus.checked)\n\t\t{\n\t\t\tdocument.getElementById(\"networkgate\").style.display=\"block\";\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"networkgate\").style.display=\"none\";\n\t\t}\n\t}\n\t");
/*      */       }
/*  381 */       out.write("\n\n\nfunction checkForPollCount()\n{\n\tif(document.AMActionForm.enableErrorMail.checked)\n\t{\n\t\tdocument.AMActionForm.errorpollCount.disabled = false;\n\t}\n\telse\n\t{\n\t\tdocument.AMActionForm.errorpollCount.disabled = true\n\t}\n}\n\nfunction checkFn()\n{\n  if(document.AMActionForm.monthlyday.checked)\n  {\n     javascript:showDiv(\"showreports\");\n  }\n  else\n  {\n     javascript:hideDiv(\"showreports\");\n  }\n}\n\n\nfunction ioAlert(checkboxObj){\n\nif(!checkboxObj.checked){\n\tif(confirm(\"");
/*  382 */       out.print(FormatUtil.getString("am.webclient.diskio.disable.text"));
/*  383 */       out.write("\"))\n\t{\n\n\t\tcheckboxObj.checked = false;\n\n\t}else{\n\n\t\tcheckboxObj.checked = true;\n\n\t}\n\n}\n\n}\n\nfunction hwHealthMonDisablePrompt(obj){\n\tif (obj != null && obj.value == \"disable\") {\n\t\tvar disable=confirm(\"");
/*  384 */       out.print(FormatUtil.getString("am.webclient.server.hardware.monitoring.disable.text"));
/*  385 */       out.write("\"); \n\t\tif(!disable) {\n\t\t\tvar elemnts=document.getElementsByName(\"hostHwMonitoring\");\n\t\t\telemnts[0].checked=true\n\t\t\tobj.checked=false;\n\t\t} else {\n\t\t\thideHWTable();\n\t\t}\n\t} else {\n\t\tshowHWTable()\n\t}\n}\n\nfunction hideHWTable() {\n\t$('.hwComponent').hide();\t// NO I18N\n\t$('.hwComponent_Space').hide();\t// NO I18N\n\t$('.hwComponent_Critical_Severity').hide();\t// NO I18N\n\t$('.hwComponent_Major_Severity').hide();\t// NO I18N\n\t$('.hwComponent_Clear_Severity').hide();\t// NO I18N\n\t$('.hwComponent_Critical_Severity_Space').hide();\t// NO I18N\n\t$('.hwComponent_Major_Severity_Space').hide();\t// NO I18N\n\t$('.hwComponent_Clear_Severity_Space').hide();\t// NO I18N\t\n}\n\nfunction showHWTable() {\n\t$('.hwComponent').show();\t// NO I18N\n\t$('.hwComponent_Space').show();\t// NO I18N\n\t$('.hwComponent_Critical_Severity').show();\t// NO I18N\n\t$('.hwComponent_Major_Severity').show();\t// NO I18N\n\t$('.hwComponent_Clear_Severity').show();\t// NO I18N\n\t$('.hwComponent_Critical_Severity_Space').show();\t// NO I18N\n\t$('.hwComponent_Major_Severity_Space').show();\t// NO I18N\n");
/*  386 */       out.write("\t$('.hwComponent_Clear_Severity_Space').show();\t// NO I18N\t\n}\n\nfunction winDiskDisableAlert(checkboxObj,checkboxIdx){\n\t\n\tif(checkboxIdx=='2' && !checkboxObj.checked){\n\t\tif(confirm(\"");
/*  387 */       out.print(FormatUtil.getString("am.webclient.windows.netmappeddrive.disable.text"));
/*  388 */       out.write("\"))\n\t\t{\n\t\t\t// validateAndSubmit();\n\n\t\t}else{\n\t\t\tcheckboxObj.checked = true;\n\t\t}\n\t} else \tif(checkboxIdx=='3' && !checkboxObj.checked){\n\t\tif(confirm(\"");
/*  389 */       out.print(FormatUtil.getString("am.webclient.windows.volume.mount.disable.text"));
/*  390 */       out.write("\"))\n\t\t{\n\t\t\t// validateAndSubmit();\n\n\t\t}else{\n\t\t\tcheckboxObj.checked = true;\n\t\t}\n\t}\n}\n\nfunction submitMSSQLForm()\n{\n\t");
/*  391 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  393 */       out.write(10);
/*  394 */       out.write(9);
/*      */       
/*  396 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  397 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  398 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */       
/*  400 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  401 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  402 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */         for (;;) {
/*  404 */           out.write("\n\t\t$('#cb81').val($('#cb82').prop('checked'));\n\t\tvar backupVal = document.MSSQLForm.backupCollectionPeriod.value;\n\t\tvar repVal = document.MSSQLForm.replAgentHistoryMaintenancePeriod.value;\n\t\tif((trimAll(repVal)==\"\") || (isPositiveInteger(repVal)==false))\n\t\t{\n\t\t\twindow.alert('");
/*  405 */           out.print(FormatUtil.getString("am.webclient.global.jsalertforreplicationagenthistory.retentionvalue.text"));
/*  406 */           out.write("');\n\t\t\treturn false;\n\t\t}\n\t\tif((trimAll(backupVal)==\"\") || (isPositiveInteger(backupVal)==false))\n\t\t{\n\t\t\twindow.alert('");
/*  407 */           out.print(FormatUtil.getString("am.webclient.global.jsalertforrbackuphistory.text"));
/*  408 */           out.write("');\n\t\t\treturn false;\n\t\t}\t\n\t\t$(\"#MSSQLForm\").submit();\n\t\tif ('");
/*  409 */           out.print(reloadParent);
/*  410 */           out.write("'.toLowerCase()==\"true\") {\n\t\t\twindow.opener.location.reload();\n\t\t}\n\t");
/*  411 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  412 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  416 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  417 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */       }
/*      */       else {
/*  420 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  421 */         out.write("\n}\n\n\n\nfunction validateAndSubmit()\n{\n\t");
/*      */         
/*  423 */         String alertMsg = FormatUtil.getString("am.webclient.saveKeyValues.confirm");
/*  424 */         if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */         {
/*  426 */           alertMsg = FormatUtil.getString("am.webclient.serverproperties.alert") + alertMsg;
/*      */         }
/*      */         
/*  429 */         out.write("\n\tvar alertMsg = '");
/*  430 */         out.print(alertMsg);
/*  431 */         out.write(39);
/*  432 */         out.write(59);
/*  433 */         out.write(10);
/*  434 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */           return;
/*  436 */         out.write(10);
/*      */         
/*  438 */         NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  439 */         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  440 */         _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */         
/*  442 */         _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/*  443 */         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  444 */         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */           for (;;) {
/*  446 */             out.write("\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)\n\t{\n\t\tif(document.AMActionForm.elements[i].type==\"text\")\n\t\t{\n\t\t\tvar name = document.AMActionForm.elements[i].name;\n\t\t\tvar value = document.AMActionForm.elements[i].value;\n\t\t\tif(name==\"min_criticalpollscount\")\n\t\t\t{\n\t\t\t\tif((trimAll(value)==\"\") || (isPositiveInteger(value)==false))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  447 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforminimumcriticalpollcount.text"));
/*  448 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if((value>10)||(value<1))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  449 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforminimumcriticalpollvalue.text"));
/*  450 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\tvar criticalPolls = document.AMActionForm.criticalpollscount.value\n\t\t\t\tif(isPositiveInteger(criticalPolls) && value != 1 && parseInt(value) > parseInt(criticalPolls)){\n\t\t\t\t\twindow.alert(\"");
/*  451 */             out.print(FormatUtil.getString("am.webclient.threshold.pollstotry.greater.text"));
/*  452 */             out.write("\");\n\t\t\t\t\tdocument.AMActionForm.elements[i].focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"min_warningpollscount\")\n\t\t\t{\n\t\t\t\tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  453 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforminimumwarningpollcount.text"));
/*  454 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if((value>10)||(value<1))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  455 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforminimumwarningpollvalue.text"));
/*  456 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\t\n\t\t\t\tvar warningPolls = document.AMActionForm.warningpollscount.value\n\t\t\t\tif(isPositiveInteger(warningPolls) && value != 1 && parseInt(value) > parseInt(warningPolls)){\n\t\t\t\t\twindow.alert(\"");
/*  457 */             out.print(FormatUtil.getString("am.webclient.threshold.pollstotry.greater.text"));
/*  458 */             out.write("\");\n\t\t\t\t\tdocument.AMActionForm.elements[i].focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"min_clearpollscount\")\n\t\t\t{\n\t\t\t\tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  459 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforminimumclearpollcount.text"));
/*  460 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if((value>10)||(value<1))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  461 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforminimumclearpollvalue.text"));
/*  462 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\t\n\t\t\t\tvar clearPolls = document.AMActionForm.clearpollscount.value\n\t\t\t\tif(isPositiveInteger(clearPolls) && value != 1 && parseInt(value) > parseInt(clearPolls)){\n\t\t\t\t\twindow.alert(\"");
/*  463 */             out.print(FormatUtil.getString("am.webclient.threshold.pollstotry.greater.text"));
/*  464 */             out.write("\");\n\t\t\t\t\tdocument.AMActionForm.elements[i].focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"criticalpollscount\")\n\t\t\t{\n\t\t\t\tif((trimAll(value)==\"\") || (isPositiveInteger(value)==false))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  465 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforcripollcount.text"));
/*  466 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if((value>10)||(value<1))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  467 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforcripollvalue.text"));
/*  468 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"warningpollscount\")\n\t\t\t{\n\t\t\t\tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  469 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforwarpollcount.text"));
/*  470 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if((value>10)||(value<1))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  471 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforwarpollvalue.text"));
/*  472 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"clearpollscount\")\n\t\t\t{\n\n\t\t\t\tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  473 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforclrpollcount.text"));
/*  474 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if((value>10)||(value<1))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  475 */             out.print(FormatUtil.getString("am.webclient.global.jsalertforclrpollvalue.text"));
/*  476 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"seventhirtyMA\")\n\t\t\t{\n\t\t\t\tif(trimAll(value)==\"\")\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  477 */             out.print(FormatUtil.getString("am.webclient.jsalertempty.mvavg.text"));
/*  478 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if(value<1)\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  479 */             out.print(FormatUtil.getString("am.webclient.jsalertpositive.mvavg.text"));
/*  480 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse if(name==\"gmapheight\")\n\t\t\t{\n\t\t\t\tif((value==\"\"))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  481 */             out.print(FormatUtil.getString("am.webclient.gmap.gmapheightalert.text"));
/*  482 */             out.write("\");\n\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if(isNaN(value))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  483 */             out.print(FormatUtil.getString("am.webclient.gmap.gmapheightinvalid.text"));
/*  484 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if((value<500))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  485 */             out.print(FormatUtil.getString("am.webclient.gmap.gmaphlowvalue.text"));
/*  486 */             out.write("\");\n\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if((value<0))\n                                {\n                                        window.alert(\"");
/*  487 */             out.print(FormatUtil.getString("am.webclient.gmap.gmappositivevalue.text"));
/*  488 */             out.write("\");\n\n                                        return false;\n                                }\n\n\t\t\t}\n\t\t\telse if(name==\"gmapwidth\")\n\t\t\t{\n\t\t\t\tif((value==\"\"))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  489 */             out.print(FormatUtil.getString("am.webclient.gmap.gmapwidthalert.text"));
/*  490 */             out.write("\");\n\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if(isNaN(value))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  491 */             out.print(FormatUtil.getString("am.webclient.gmap.gmapwidthinvalid.text"));
/*  492 */             out.write("\");\n\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if((value<500))\n                                {\n                                        window.alert(\"");
/*  493 */             out.print(FormatUtil.getString("am.webclient.gmap.gmapwlowvalue.text"));
/*  494 */             out.write("\");\n\n                                        return false;\n                                }\n\t\t\t\telse if((value<0))\n\t\t\t\t{\n\t\t\t\t\twindow.alert(\"");
/*  495 */             out.print(FormatUtil.getString("am.webclient.gmap.gmappositivevalue.text"));
/*  496 */             out.write("\");\n\n                                        return false;\n\t\t\t\t}\n\n\t\t\t}\n\n\t\t\telse if(name==\"errorpollCount\"){\n\n\t\t\t\tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false) || value == '0')\n                                {\n                                        window.alert(\"");
/*  497 */             out.print(FormatUtil.getString("am.webclient.sendmonerror.pollcount.error"));
/*  498 */             out.write("\");\n                                        return false;\n                                }\n\n\t\t\t}\n\t\t\telse if(name==\"displaynamelength\")\n\t\t\t{\n\t\t\t\tif(!(trimAll(value)==\"\") && isPositiveInteger(value)==false)\n\t\t\t\t{\n                \twindow.alert(\"");
/*  499 */             out.print(FormatUtil.getString("smsaction.mobilenumber.positivevalue.text"));
/*  500 */             out.write("\");\n                    return false;\n                }\n\t\t\t}\n\t\t}\n\n\t\tif(document.AMActionForm.elements[i].type==\"radio\")\n\t\t{\n\t\t\tvar name = document.AMActionForm.elements[i].name;\n\t\t\tvar value = document.AMActionForm.elements[i].value;\n\t\t\tif(name==\"repeatHealthActions\" && value==\"tillActionCleared\")\n\t\t\t{\n\t\t\t\tvar val = document.AMActionForm.healthActionCount.value;\n\t\t\t\tif(!(trimAll(val)==\"\") && (isPositiveInteger(val)==false))\n\t\t\t\t{\n                                        window.alert(\"");
/*  501 */             out.print(FormatUtil.getString("am.webclient.global.repeatHealth.count.error"));
/*  502 */             out.write("\");\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t\tif(document.AMActionForm.elements[i].type==\"checkbox\")\n\t\t{\n\t\t\tvar name = document.AMActionForm.elements[i].name;\n                        var value = document.AMActionForm.elements[i].value;\n\t\t\tvar isChecked = $(\"#cb64\").prop('checked');//NO I18N\n\t\t\tif(name==\"mgProcessAndServiceAssoicated\" && !isChecked)\n\t\t\t{\n\t\t\t\t");
/*  503 */             if (com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.mg.processAndService.associate").equals("true")) {
/*  504 */               out.write("\n\t\t\t\t\talertMsg = '");
/*  505 */               out.print(FormatUtil.getString("am.webclient.mgassociate.saveKeyValues.confirm"));
/*  506 */               out.write("';\n\t\t\t\t");
/*      */             }
/*  508 */             out.write("\n\t\t\t}\n\t\t}\n\t}\n\n\t");
/*  509 */             if ((type != null) && (type.equals("Availablity")))
/*      */             {
/*  511 */               out.write("\n\t\tif(document.AMActionForm.gatewayCheckStatus.checked)\n\t\t{\n\t\t\tif((trimAll(document.AMActionForm.gatewayName.value)==\"\"))\n\t\t\t{\n\t\t\t\twindow.alert(\"");
/*  512 */               out.print(FormatUtil.getString("am.webclient.global.jsalertforgatewaycheck.text"));
/*  513 */               out.write("\");\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\t\tif(document.AMActionForm.gatewayUrlStatus.checked)\n\t\t{\n\t\t\tif((trimAll(document.AMActionForm.gatewayUrlName.value)==\"\"))\n\t\t\t{\n\t\t\t\twindow.alert(\"");
/*  514 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.url"));
/*  515 */               out.write("\");\n\t\t\t\tdocument.AMActionForm.gatewayUrlName.focus();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\tif(!checkUrlPattern(document.AMActionForm.gatewayUrlName.value))\n\t\t\t{\n\t\t\t\twindow.alert(\"");
/*  516 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.urladd"));
/*  517 */               out.write("\");\n\t\t\t\tdocument.AMActionForm.gatewayUrlName.focus();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\tif((trimAll(document.AMActionForm.fromaddress.value)==\"\"))\n\t\t\t{\n\t\t\t\twindow.alert(\"");
/*  518 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.email"));
/*  519 */               out.write("\");\n\t\t\t\tdocument.AMActionForm.fromaddress.focus();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\tif((trimAll(document.AMActionForm.sendmail.value)==\"\"))\n\t\t\t{\n\t\t\t\twindow.alert(\"");
/*  520 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.email"));
/*  521 */               out.write("\");\n\t\t\t\tdocument.AMActionForm.sendmail.focus();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\n\t\tvar timeoutValue=document.AMActionForm.timeout.value;\n\t\tif((trimAll(timeoutValue)==\"\") || (isPositiveInteger(timeoutValue)==false) || (trimAll(timeoutValue)==0))\n\t\t{\n\t\t\twindow.alert(\"");
/*  522 */               out.print(FormatUtil.getString("am.webclient.global.jsalertforavailabilitytimeout.text"));
/*  523 */               out.write("\");\n\t\t\treturn false;\n\t\t}\n\n\t\t");
/*      */             }
/*  525 */             else if ((type != null) && (type.equals("unmanage")))
/*      */             {
/*      */ 
/*  528 */               out.write("\n\t\t\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.maintenanceCombo1);\n\t\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.maintenanceCombo2);\n\t\t\t");
/*      */ 
/*      */             }
/*  531 */             else if ((type != null) && (type.equals("performance")))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*  536 */               out.write("\n    \t\t\n    \t\t if(document.getElementById(\"DBlink\").className =='tbSelected_Middle')\n \t\t\t{\n \t\t\t  document.AMActionForm.showDatabaseTab.value='true';//NO I18N\n \t\t\t  document.AMActionForm.pollInterval.value=trimAll(document.AMActionForm.pollInterval.value);\n\t \t\t var mysqlvar=document.getElementById(\"mysqldata\").value;\n\t\t\t if(mysqlvar ==\"\" ||!(isPositiveInteger(mysqlvar)) || mysqlvar =='0')\n\t\t\t {\n\t\t\t\talert(\"");
/*  537 */               out.print(FormatUtil.getString("am.webclient.newscript.alert.daystoretian.text"));
/*  538 */               out.write("\");\n\t\t\t\treturn false;\n\t\t\t }\n\t\t\t var nonSqlInterval=document.getElementById(\"nonSqlInterval\").value;\n\t\t\t if(nonSqlInterval ==\"\" ||!(isPositiveInteger(nonSqlInterval))|| nonSqlInterval =='0')\n\t\t\t {\n\t\t\t\talert(\"");
/*  539 */               out.print(FormatUtil.getString("am.webclient.newscript.alert.timeval.text"));
/*  540 */               out.write("\");\n\t\t\t\treturn false;\n\t\t\t }\n \t\t\t\n \t\t\t}\t\t   \n\t\t\tif(document.getElementById(\"diskiolink\").className =='tbSelected_Middle')\n\t\t\t{\n\t\t\t document.AMActionForm.showDiskIoTab.value='true';\n\t\t\t document.AMActionForm.pollInterval.value=trimAll(document.AMActionForm.pollInterval.value);\n\t\t\t var telnetLoginTimeout=document.getElementById(\"cb5\").value;\n\t\t\t if(telnetLoginTimeout ==\"\" ||!(isPositiveInteger(telnetLoginTimeout)) || telnetLoginTimeout =='0' )\n\t\t\t {\n\t\t\t\talert(\"");
/*  541 */               out.print(FormatUtil.getString("am.webclient.newscript.alert.timeval.text"));
/*  542 */               out.write("\");\n\t\t\t\treturn false;\n\t\t\t }\n\t\t\t var execResTimeout=document.getElementById(\"cb6\").value;\n\t\t\t if(execResTimeout ==\"\" ||!(isPositiveInteger(execResTimeout)) || execResTimeout =='0' )\n\t\t\t {\n\t\t\t\talert(\"");
/*  543 */               out.print(FormatUtil.getString("am.webclient.newscript.alert.timeval.text"));
/*  544 */               out.write("\");\n\t\t\t\treturn false;\n\t\t\t }\n\t\t\t var cmdResTimeout=document.getElementById(\"cb9\").value;\n\t\t\t if(cmdResTimeout ==\"\" ||!(isPositiveInteger(cmdResTimeout)) || cmdResTimeout =='0' )\n\t\t\t {\n\t\t\t\talert(\"");
/*  545 */               out.print(FormatUtil.getString("am.webclient.newscript.alert.timeval.text"));
/*  546 */               out.write("\");\n\t\t\t\treturn false;\n\t\t\t }\n\t\t\t var ignoredisks=document.getElementById(\"ignoredisks\").value;\n\t\t\t var wmiencoding=document.getElementById(\"wmiencoding\").value;\n\t\t\t if(isPositiveInteger(ignoredisks) || isPositiveInteger(wmiencoding))\n\t\t\t {\n\t\t\t\talert(\"");
/*  547 */               out.print(FormatUtil.getString("Please enter string values only."));
/*  548 */               out.write("\");\n\t\t\t\treturn false;\n\t\t\t }\n\t\t\t\t\t\t\n\t\t\t}\n\t        if(document.getElementById(\"performancelink\").className =='tbSelected_Middle')\n\t\t\t{\n\t\t\t document.AMActionForm.showDataCollectionTab.value='true';\n\t\t\t var pollinterval=trimAll(document.AMActionForm.pollInterval.value);\n\t\t\t if(pollinterval ==\"\" ||!(isPositiveInteger(pollinterval)) || pollinterval =='0' )\n\t\t\t {\n\t\t\t\talert(\"");
/*  549 */               out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/*  550 */               out.write("\");\n\t\t\t\tdocument.AMActionForm.pollInterval.focus();\n\t\t\t\treturn false;\n\t\t\t }\n\t\t\t}\n\n\n\t\t\tif(document.getElementById(\"wlslink\").className =='tbSelected_Middle')\n\t\t\t{\n\t\t\t  \tdocument.AMActionForm.showWeblogicTab.value='true';\n\t\t\t\tif(document.getElementById(\"wlswebappid\").checked)\n\t\t\t\t{\n\t\t\t\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.wlswebapp_enable);\n\t\t\t\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.wlswebapp_disable);\n\t\t\t\t}\n\n\t\t\t\tif(document.getElementById(\"wlsejbid\").checked)\n\t\t\t\t{\n\t\t\t\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.wlsejb_enable);\n\t\t\t\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.wlsejb_disable);\n\t\t\t\t}\n\n\t\t\t\tif(document.getElementById(\"wlsservletid\").checked)\n\t\t\t\t{\n\t\t\t\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.wlsservlet_enable);\n\t\t\t\t\tfrmSelectAllIncludingEmpty(document.AMActionForm.wlsservlet_disable);\n\t\t\t\t}\n\t\t\t}\t\t\n\t\t\tif(document.getElementById(\"amazonlink\").className =='tbSelected_Middle')\n\t\t\t{\n\t\t\t      document.AMActionForm.showAmazonTab.value='true';\n\t\t\t}\n\t\t\tif(document.getElementById(\"configureDC\").className =='tbSelected_Middle')\n");
/*  551 */               out.write("\t\t\t{\n\t\t\t      document.AMActionForm.showConfigureDCTab.value='true';\n\t\t\t}\n\t\t\tif(document.getElementById(\"configureMSSQL\").className =='tbSelected_Middle')\n\t\t\t{\n\t\t\t      document.AMActionForm.showConfigureTabMSSQL.value='true';\n\t\t\t}\n\t\t\tif(document.getElementById(\"snmplink\").className =='tbSelected_Middle')\n\t\t\t{\n\t\t\t      document.AMActionForm.showSnmpTab.value='true';\n\t\t\t}\n\t\t\tif(document.getElementById(\"urllink\").className =='tbSelected_Middle')\n\t\t\t{\n\t\t\t      document.AMActionForm.showUrlTab.value='true';\n\t\t\t\t\n\t\t\t}\n\t\t\tif(document.getElementById(\"webserviceslink\").className =='tbSelected_Middle')\n\t\t\t{\n\t\t\t    document.AMActionForm.showWebservicesTab.value='true';\n\t\t\t\tvar wsmTime = trimAll(document.AMActionForm.webServicesOperationTime.value);\n\t\t\t    if(trimAll(wsmTime)==\"\" || !isPositiveInteger(wsmTime)|| wsmTime == '0')\n\t\t\t\t{\n\t            \twindow.alert(\"");
/*  552 */               out.print(FormatUtil.getString("smsaction.mobilenumber.positivevalue.text"));
/*  553 */               out.write("\");\n\t            \treturn false;\n\t            }\n\t\t\t}\n\t\t\tif(document.getElementById(\"pinglink\").className =='tbSelected_Middle')\n\t\t\t{\n\t\t\t\tdocument.AMActionForm.showPingTab.value='true';\n\t\t\t\tvar packSend = trimAll(document.AMActionForm.pingPackToSend.value);\n\t\t\t\tif(trimAll(packSend)==\"\" || !isPositiveInteger(packSend) || packSend == '0')\n\t\t\t\t{\n                \twindow.alert(\"");
/*  554 */               out.print(FormatUtil.getString("smsaction.mobilenumber.positivevalue.text"));
/*  555 */               out.write("\");\n                    return false;\n                }\n\t\t\t}\n\t\t\t\n    \t\t\n\t\tif(!confirm(alertMsg))\n    \t\t{ \n    \t\t\tlocation.reload(); \n    \t\t\treturn false;\n    \t\t}\n\t\t\t");
/*      */             }
/*      */             
/*      */ 
/*  559 */             out.write("\n    \t\t\n    \t\t\n    \t\t$('#cb01').val($('#cb02').prop('checked'));\n    \t\t$('#cb11').val($('#cb12').prop('checked'));\n    \t\t$('#cb21').val($('#cb22').prop('checked'));\n    \t\t$('#cb31').val($('#cb32').prop('checked'));\n    \t\t$('#cb41').val($('#cb42').prop('checked'));\n    \t\t$('#cb61').val($('#cb62').prop('checked'));\n    \t\t$('#cb63').val($('#cb64').prop('checked'));\n    \t\t$('#cb71').val($('#cb72').prop('checked'));\n\t\t\tdocument.AMActionForm.submit();\n    \t\tif ('");
/*  560 */             out.print(reloadParent);
/*  561 */             out.write("'.toLowerCase()==\"true\") {\n    \t\t\twindow.opener.location.reload();\n    \t\t}\n");
/*  562 */             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  563 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  567 */         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  568 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */         }
/*      */         else {
/*  571 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  572 */           out.write("\n}\n\nfunction changeIdentifier() \n{\n    if (!confirm(\"");
/*  573 */           out.print(FormatUtil.getString("am.webclient.amazon.perfpolling.caution.text"));
/*  574 */           out.write("\")) \n    {\n    \t$('input:radio[name=amazonEC2PrimaryKey]:not(:checked)').attr(\"checked\", true); //NO I18N\n    }\n}\n\nfunction setDefaultValue(elementName) {\n\tif (confirm('");
/*  575 */           out.print(FormatUtil.getString("Do you really want to set default value?"));
/*  576 */           out.write("')) { \n\t\tif (elementName == 'All_Fields_In_Server_Tab') {\n\t\t\tdocument.AMActionForm.diskIOAix.checked=false;\n\t\t\tdocument.AMActionForm.diskIOLinux.checked=false;\n\t\t\tdocument.AMActionForm.diskIOWindows.checked=false;\n\t\t\tdocument.AMActionForm.diskIOSun.checked=false;\n\t\t\tdocument.AMActionForm.winDiskLocal.checked=true;\n\t\t\tdocument.AMActionForm.winDiskNetwork.checked=false;\n\t\t\tdocument.AMActionForm.winDiskMount.checked=false;\n\t\t\tdocument.AMActionForm.hostProcDown.checked=false;\n\t\t\tdocument.AMActionForm.winServDown.checked=false;\n\t\t\tdocument.AMActionForm.errorAlertDisk.checked=true;\n\t\t\tdocument.AMActionForm.errorAlertNwInter.checked=true;\n\t\t\tdocument.AMActionForm.errorAlertNwAdapter.checked=true;\n\t\t\tdocument.AMActionForm.errorAlertRestart.checked=true;\n\t\t\tdocument.AMActionForm.errorAlertScheduledTask.checked=true;\n\t\t\tif(document.AMActionForm.hostHwMonitoring){\n\t\t\t\tdocument.AMActionForm.hostHwMonitoring[0].checked=true;\n\t\t\t\tdocument.AMActionForm.hostHwMonitoring[1].checked=false;\n\t\t\t}\n\t\t\tshowHWTable();\n\t\t\tdocument.AMActionForm.temperature.checked=true;\n");
/*  577 */           out.write("\t\t\tdocument.AMActionForm.power.checked=true;\n\t\t\tdocument.AMActionForm.fan.checked=true;\n\t\t\tdocument.AMActionForm.processor.checked=true;\n\t\t\tdocument.AMActionForm.disk.checked=true;\n\t\t\tdocument.AMActionForm.array.checked=true;\n\t\t\tdocument.AMActionForm.chassis.checked=true;\n\t\t\tdocument.AMActionForm.memorydevice.checked=true;\n\t\t\tdocument.AMActionForm.voltage.checked=true;\n\t\t\tdocument.AMActionForm.battery.checked=true;\n\t\t\tdocument.AMActionForm.hwCriticalStatusMessage.value=\"failed,error,failure,nonRecoverable,criticalUpper,criticalLower,nonRecoverableLower,critical\"; // NO I18N\n\t\t\tdocument.AMActionForm.hwWarningStatusMessage.value=\"degraded,warning,nonCritical,nonCriticalUpper,nonRecoverableUpper,nonCriticalLower\"; // NO I18N\n\t\t\tdocument.AMActionForm.hwClearStatusMessage.value=\"ok\"; // NO I18N\n\t\t}\n\t\telse if (elementName == 'hwCriticalStatusMessage') { // NO I18N\n\t\t\tdocument.AMActionForm.hwCriticalStatusMessage.value=\"failed,error,failure,nonRecoverable,criticalUpper,criticalLower,nonRecoverableLower,critical\";// NO I18N\n");
/*  578 */           out.write("\t\t} else if (elementName == 'hwWarningStatusMessage') { // NO I18N\n\t\t\tdocument.AMActionForm.hwWarningStatusMessage.value=\"degraded,warning,nonCritical,nonCriticalUpper,nonRecoverableUpper,nonCriticalLower\"; // NO I18N\n\t\t} else {\n\t\t\tdocument.AMActionForm.hwClearStatusMessage.value=\"ok\"; // NO I18N\n\t\t}\n\t}\n}\n</script>\n");
/*      */           
/*  580 */           pageContext.setAttribute("gmapcountries", com.adventnet.appmanager.util.DBUtil.getGMapCountries());
/*      */           
/*  582 */           out.write("\n\n\n\n\n\n\n");
/*      */           
/*  584 */           org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(org.apache.struts.taglib.tiles.InsertTag.class);
/*  585 */           _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  586 */           _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */           
/*  588 */           _jspx_th_tiles_005finsert_005f0.setPage("/jsp/NewAdminLayout.jsp");
/*  589 */           int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  590 */           if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */             for (;;) {
/*  592 */               out.write(10);
/*  593 */               out.write(10);
/*      */               
/*  595 */               PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  596 */               _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/*  597 */               _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */               
/*  599 */               _jspx_th_tiles_005fput_005f0.setName("LeftArea");
/*      */               
/*  601 */               _jspx_th_tiles_005fput_005f0.setType("string");
/*  602 */               int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/*  603 */               if (_jspx_eval_tiles_005fput_005f0 != 0) {
/*  604 */                 if (_jspx_eval_tiles_005fput_005f0 != 1) {
/*  605 */                   out = _jspx_page_context.pushBody();
/*  606 */                   _jspx_th_tiles_005fput_005f0.setBodyContent((BodyContent)out);
/*  607 */                   _jspx_th_tiles_005fput_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  610 */                   out.write(10);
/*  611 */                   out.write(32);
/*      */                   
/*  613 */                   if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                   {
/*  615 */                     out.write(10);
/*  616 */                     out.write(32);
/*  617 */                     out.write(32);
/*  618 */                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                     
/*      */ 
/*  621 */                     String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                     
/*  623 */                     out.write("\n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n <tr>\n\t     <td height=\"21\"  class=\"leftlinksheading\">");
/*  624 */                     out.print(FormatUtil.getString("am.webclient.admin.heading"));
/*  625 */                     out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/*  627 */                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  628 */                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  629 */                     _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f0);
/*  630 */                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  631 */                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                       for (;;) {
/*  633 */                         out.write(10);
/*      */                         
/*  635 */                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  636 */                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  637 */                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                         
/*  639 */                         _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/*  640 */                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  641 */                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                           for (;;) {
/*  643 */                             out.write("\n        ");
/*  644 */                             out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  645 */                             out.write(10);
/*  646 */                             out.write(32);
/*  647 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  648 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  652 */                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  653 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                         }
/*      */                         
/*  656 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  657 */                         out.write(10);
/*  658 */                         out.write(32);
/*      */                         
/*  660 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  661 */                         _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  662 */                         _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  663 */                         int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  664 */                         if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                           for (;;) {
/*  666 */                             out.write("\n     ");
/*      */                             
/*  668 */                             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f0 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  669 */                             _jspx_th_ea_005feeadminlink_005f0.setPageContext(_jspx_page_context);
/*  670 */                             _jspx_th_ea_005feeadminlink_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                             
/*  672 */                             _jspx_th_ea_005feeadminlink_005f0.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*      */                             
/*  674 */                             _jspx_th_ea_005feeadminlink_005f0.setEnableClass("new-left-links");
/*  675 */                             int _jspx_eval_ea_005feeadminlink_005f0 = _jspx_th_ea_005feeadminlink_005f0.doStartTag();
/*  676 */                             if (_jspx_eval_ea_005feeadminlink_005f0 != 0) {
/*  677 */                               if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  678 */                                 out = _jspx_page_context.pushBody();
/*  679 */                                 _jspx_th_ea_005feeadminlink_005f0.setBodyContent((BodyContent)out);
/*  680 */                                 _jspx_th_ea_005feeadminlink_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/*  683 */                                 out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  684 */                                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f0.doAfterBody();
/*  685 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*  688 */                               if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  689 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/*  692 */                             if (_jspx_th_ea_005feeadminlink_005f0.doEndTag() == 5) {
/*  693 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0); return;
/*      */                             }
/*      */                             
/*  696 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0);
/*  697 */                             out.write(10);
/*  698 */                             out.write(32);
/*  699 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  700 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  704 */                         if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  705 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                         }
/*      */                         
/*  708 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  709 */                         out.write(10);
/*  710 */                         out.write(32);
/*  711 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  712 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  716 */                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  717 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                     }
/*      */                     
/*  720 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  721 */                     out.write("\n    </td>\n</tr>\n\n<tr>\n\n<tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/*  723 */                     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  724 */                     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  725 */                     _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f0);
/*  726 */                     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  727 */                     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                       for (;;) {
/*  729 */                         out.write(10);
/*      */                         
/*  731 */                         WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  732 */                         _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  733 */                         _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                         
/*  735 */                         _jspx_th_c_005fwhen_005f1.setTest("${param.method!='showMailServerConfiguration'}");
/*  736 */                         int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  737 */                         if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                           for (;;) {
/*  739 */                             out.write("\n    ");
/*      */                             
/*  741 */                             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f1 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  742 */                             _jspx_th_ea_005feeadminlink_005f1.setPageContext(_jspx_page_context);
/*  743 */                             _jspx_th_ea_005feeadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                             
/*  745 */                             _jspx_th_ea_005feeadminlink_005f1.setHref("/adminAction.do?method=showMailServerConfiguration");
/*      */                             
/*  747 */                             _jspx_th_ea_005feeadminlink_005f1.setEnableClass("new-left-links");
/*  748 */                             int _jspx_eval_ea_005feeadminlink_005f1 = _jspx_th_ea_005feeadminlink_005f1.doStartTag();
/*  749 */                             if (_jspx_eval_ea_005feeadminlink_005f1 != 0) {
/*  750 */                               if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  751 */                                 out = _jspx_page_context.pushBody();
/*  752 */                                 _jspx_th_ea_005feeadminlink_005f1.setBodyContent((BodyContent)out);
/*  753 */                                 _jspx_th_ea_005feeadminlink_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/*  756 */                                 out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  757 */                                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f1.doAfterBody();
/*  758 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*  761 */                               if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  762 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/*  765 */                             if (_jspx_th_ea_005feeadminlink_005f1.doEndTag() == 5) {
/*  766 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1); return;
/*      */                             }
/*      */                             
/*  769 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1);
/*  770 */                             out.write(10);
/*  771 */                             out.write(32);
/*  772 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  773 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  777 */                         if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  778 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                         }
/*      */                         
/*  781 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  782 */                         out.write(10);
/*  783 */                         out.write(32);
/*      */                         
/*  785 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  786 */                         _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  787 */                         _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  788 */                         int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  789 */                         if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                           for (;;) {
/*  791 */                             out.write(10);
/*  792 */                             out.write(9);
/*  793 */                             out.write(32);
/*  794 */                             out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  795 */                             out.write(10);
/*  796 */                             out.write(32);
/*  797 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  798 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  802 */                         if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  803 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                         }
/*      */                         
/*  806 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  807 */                         out.write(10);
/*  808 */                         out.write(32);
/*  809 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  810 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  814 */                     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  815 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                     }
/*      */                     
/*  818 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  819 */                     out.write("\n    </td>\n</tr>\n\n");
/*  820 */                     if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/*  821 */                       out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                       
/*  823 */                       ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  824 */                       _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  825 */                       _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f0);
/*  826 */                       int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  827 */                       if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                         for (;;) {
/*  829 */                           out.write(10);
/*      */                           
/*  831 */                           WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  832 */                           _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  833 */                           _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                           
/*  835 */                           _jspx_th_c_005fwhen_005f2.setTest("${param.method!='SMSServerConfiguration'}");
/*  836 */                           int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  837 */                           if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                             for (;;) {
/*  839 */                               out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/*  840 */                               out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  841 */                               out.write("\n    </a>\n ");
/*  842 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  843 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  847 */                           if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  848 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                           }
/*      */                           
/*  851 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  852 */                           out.write(10);
/*  853 */                           out.write(32);
/*      */                           
/*  855 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  856 */                           _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  857 */                           _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  858 */                           int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  859 */                           if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                             for (;;) {
/*  861 */                               out.write("\n         ");
/*  862 */                               out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  863 */                               out.write(10);
/*  864 */                               out.write(32);
/*  865 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  866 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  870 */                           if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  871 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                           }
/*      */                           
/*  874 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  875 */                           out.write(10);
/*  876 */                           out.write(32);
/*  877 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  878 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  882 */                       if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  883 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                       }
/*      */                       
/*  886 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  887 */                       out.write("\n    </td>\n</tr>\n");
/*      */                     }
/*  889 */                     out.write("\n\n\n<tr>\n\n    <td class=\"leftlinkstd\">\n");
/*      */                     
/*  891 */                     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  892 */                     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  893 */                     _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f0);
/*  894 */                     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  895 */                     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                       for (;;) {
/*  897 */                         out.write(10);
/*      */                         
/*  899 */                         WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  900 */                         _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  901 */                         _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                         
/*  903 */                         _jspx_th_c_005fwhen_005f3.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/*  904 */                         int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  905 */                         if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                           for (;;) {
/*  907 */                             out.write("\n    ");
/*      */                             
/*  909 */                             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f2 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  910 */                             _jspx_th_ea_005feeadminlink_005f2.setPageContext(_jspx_page_context);
/*  911 */                             _jspx_th_ea_005feeadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                             
/*  913 */                             _jspx_th_ea_005feeadminlink_005f2.setHref("/jsp/ProxyConfiguration.jsp");
/*      */                             
/*  915 */                             _jspx_th_ea_005feeadminlink_005f2.setEnableClass("new-left-links");
/*  916 */                             int _jspx_eval_ea_005feeadminlink_005f2 = _jspx_th_ea_005feeadminlink_005f2.doStartTag();
/*  917 */                             if (_jspx_eval_ea_005feeadminlink_005f2 != 0) {
/*  918 */                               if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  919 */                                 out = _jspx_page_context.pushBody();
/*  920 */                                 _jspx_th_ea_005feeadminlink_005f2.setBodyContent((BodyContent)out);
/*  921 */                                 _jspx_th_ea_005feeadminlink_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/*  924 */                                 out.write("\n    ");
/*  925 */                                 out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  926 */                                 out.write("\n    ");
/*  927 */                                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f2.doAfterBody();
/*  928 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*  931 */                               if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  932 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/*  935 */                             if (_jspx_th_ea_005feeadminlink_005f2.doEndTag() == 5) {
/*  936 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2); return;
/*      */                             }
/*      */                             
/*  939 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2);
/*  940 */                             out.write(10);
/*  941 */                             out.write(32);
/*  942 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  943 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  947 */                         if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  948 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                         }
/*      */                         
/*  951 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  952 */                         out.write(10);
/*  953 */                         out.write(32);
/*      */                         
/*  955 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  956 */                         _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  957 */                         _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  958 */                         int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  959 */                         if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                           for (;;) {
/*  961 */                             out.write(10);
/*  962 */                             out.write(9);
/*  963 */                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  964 */                             out.write(10);
/*  965 */                             out.write(32);
/*  966 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  967 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  971 */                         if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  972 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                         }
/*      */                         
/*  975 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  976 */                         out.write(10);
/*  977 */                         out.write(32);
/*  978 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  979 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  983 */                     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  984 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                     }
/*      */                     
/*  987 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  988 */                     out.write("\n    </td>\n</tr>\n<tr>\n\n<td class=\"leftlinkstd\">\n");
/*      */                     
/*  990 */                     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  991 */                     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  992 */                     _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f0);
/*  993 */                     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  994 */                     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                       for (;;) {
/*  996 */                         out.write(10);
/*      */                         
/*  998 */                         WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  999 */                         _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1000 */                         _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                         
/* 1002 */                         _jspx_th_c_005fwhen_005f4.setTest("${uri !='/admin/userconfiguration.do'}");
/* 1003 */                         int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1004 */                         if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                           for (;;) {
/* 1006 */                             out.write("\n\n        ");
/*      */                             
/* 1008 */                             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f3 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1009 */                             _jspx_th_ea_005feeadminlink_005f3.setPageContext(_jspx_page_context);
/* 1010 */                             _jspx_th_ea_005feeadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                             
/* 1012 */                             _jspx_th_ea_005feeadminlink_005f3.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                             
/* 1014 */                             _jspx_th_ea_005feeadminlink_005f3.setEnableClass("new-left-links");
/* 1015 */                             int _jspx_eval_ea_005feeadminlink_005f3 = _jspx_th_ea_005feeadminlink_005f3.doStartTag();
/* 1016 */                             if (_jspx_eval_ea_005feeadminlink_005f3 != 0) {
/* 1017 */                               if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/* 1018 */                                 out = _jspx_page_context.pushBody();
/* 1019 */                                 _jspx_th_ea_005feeadminlink_005f3.setBodyContent((BodyContent)out);
/* 1020 */                                 _jspx_th_ea_005feeadminlink_005f3.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1023 */                                 out.write("\n       ");
/* 1024 */                                 out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1025 */                                 out.write("\n        ");
/* 1026 */                                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f3.doAfterBody();
/* 1027 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1030 */                               if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/* 1031 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1034 */                             if (_jspx_th_ea_005feeadminlink_005f3.doEndTag() == 5) {
/* 1035 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3); return;
/*      */                             }
/*      */                             
/* 1038 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3);
/* 1039 */                             out.write(10);
/* 1040 */                             out.write(10);
/* 1041 */                             out.write(32);
/* 1042 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1043 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1047 */                         if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1048 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                         }
/*      */                         
/* 1051 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1052 */                         out.write(10);
/* 1053 */                         out.write(32);
/*      */                         
/* 1055 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1056 */                         _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1057 */                         _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1058 */                         int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1059 */                         if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                           for (;;) {
/* 1061 */                             out.write(10);
/* 1062 */                             out.write(9);
/* 1063 */                             out.write(32);
/* 1064 */                             out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1065 */                             out.write(10);
/* 1066 */                             out.write(32);
/* 1067 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1068 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1072 */                         if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1073 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                         }
/*      */                         
/* 1076 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1077 */                         out.write(10);
/* 1078 */                         out.write(32);
/* 1079 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1080 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1084 */                     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1085 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                     }
/*      */                     
/* 1088 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1089 */                     out.write("\n</td>\n</tr>\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                     
/* 1091 */                     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1092 */                     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 1093 */                     _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f0);
/* 1094 */                     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 1095 */                     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                       for (;;) {
/* 1097 */                         out.write("\n   ");
/*      */                         
/* 1099 */                         WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1100 */                         _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1101 */                         _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                         
/* 1103 */                         _jspx_th_c_005fwhen_005f5.setTest("${param.method!='showManagedServers'}");
/* 1104 */                         int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1105 */                         if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                           for (;;) {
/* 1107 */                             out.write("\n    ");
/*      */                             
/* 1109 */                             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f4 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1110 */                             _jspx_th_ea_005feeadminlink_005f4.setPageContext(_jspx_page_context);
/* 1111 */                             _jspx_th_ea_005feeadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                             
/* 1113 */                             _jspx_th_ea_005feeadminlink_005f4.setHref("/adminAction.do?method=showManagedServers");
/*      */                             
/* 1115 */                             _jspx_th_ea_005feeadminlink_005f4.setEnableClass("new-left-links");
/* 1116 */                             int _jspx_eval_ea_005feeadminlink_005f4 = _jspx_th_ea_005feeadminlink_005f4.doStartTag();
/* 1117 */                             if (_jspx_eval_ea_005feeadminlink_005f4 != 0) {
/* 1118 */                               if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/* 1119 */                                 out = _jspx_page_context.pushBody();
/* 1120 */                                 _jspx_th_ea_005feeadminlink_005f4.setBodyContent((BodyContent)out);
/* 1121 */                                 _jspx_th_ea_005feeadminlink_005f4.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1124 */                                 out.write("\n     ");
/* 1125 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 1126 */                                 out.write(10);
/* 1127 */                                 out.write(9);
/* 1128 */                                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f4.doAfterBody();
/* 1129 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1132 */                               if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/* 1133 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1136 */                             if (_jspx_th_ea_005feeadminlink_005f4.doEndTag() == 5) {
/* 1137 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4); return;
/*      */                             }
/*      */                             
/* 1140 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4);
/* 1141 */                             out.write("\n   ");
/* 1142 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1143 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1147 */                         if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1148 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                         }
/*      */                         
/* 1151 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1152 */                         out.write("\n   ");
/*      */                         
/* 1154 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1155 */                         _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 1156 */                         _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 1157 */                         int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 1158 */                         if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                           for (;;) {
/* 1160 */                             out.write("\n     ");
/* 1161 */                             out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 1162 */                             out.write("\n   ");
/* 1163 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1164 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1168 */                         if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1169 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                         }
/*      */                         
/* 1172 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1173 */                         out.write("\n   ");
/* 1174 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 1175 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1179 */                     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 1180 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                     }
/*      */                     
/* 1183 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1184 */                     out.write("\n  </td>\n</tr>\n\n\n<td class=\"leftlinkstd\" >\n");
/*      */                     
/* 1186 */                     ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1187 */                     _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 1188 */                     _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_tiles_005fput_005f0);
/* 1189 */                     int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 1190 */                     if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                       for (;;) {
/* 1192 */                         out.write(10);
/*      */                         
/* 1194 */                         WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1195 */                         _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 1196 */                         _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                         
/* 1198 */                         _jspx_th_c_005fwhen_005f6.setTest("${param.server!='admin'}");
/* 1199 */                         int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 1200 */                         if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                           for (;;) {
/* 1202 */                             out.write(10);
/*      */                             
/* 1204 */                             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f5 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1205 */                             _jspx_th_ea_005feeadminlink_005f5.setPageContext(_jspx_page_context);
/* 1206 */                             _jspx_th_ea_005feeadminlink_005f5.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                             
/* 1208 */                             _jspx_th_ea_005feeadminlink_005f5.setHref("/adminAction.do?method=showActionProfiles");
/*      */                             
/* 1210 */                             _jspx_th_ea_005feeadminlink_005f5.setEnableClass("new-left-links");
/* 1211 */                             int _jspx_eval_ea_005feeadminlink_005f5 = _jspx_th_ea_005feeadminlink_005f5.doStartTag();
/* 1212 */                             if (_jspx_eval_ea_005feeadminlink_005f5 != 0) {
/* 1213 */                               if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/* 1214 */                                 out = _jspx_page_context.pushBody();
/* 1215 */                                 _jspx_th_ea_005feeadminlink_005f5.setBodyContent((BodyContent)out);
/* 1216 */                                 _jspx_th_ea_005feeadminlink_005f5.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1219 */                                 out.write(10);
/* 1220 */                                 out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 1221 */                                 out.write(10);
/* 1222 */                                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f5.doAfterBody();
/* 1223 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1226 */                               if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/* 1227 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1230 */                             if (_jspx_th_ea_005feeadminlink_005f5.doEndTag() == 5) {
/* 1231 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5); return;
/*      */                             }
/*      */                             
/* 1234 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5);
/* 1235 */                             out.write(10);
/* 1236 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1237 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1241 */                         if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1242 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                         }
/*      */                         
/* 1245 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1246 */                         out.write(10);
/*      */                         
/* 1248 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1249 */                         _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 1250 */                         _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 1251 */                         int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 1252 */                         if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                           for (;;) {
/* 1254 */                             out.write(10);
/* 1255 */                             out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 1256 */                             out.write(10);
/* 1257 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1258 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1262 */                         if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1263 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                         }
/*      */                         
/* 1266 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1267 */                         out.write(10);
/* 1268 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 1269 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1273 */                     if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 1274 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                     }
/*      */                     
/* 1277 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 1278 */                     out.write("\n</td>\n</tr>\n");
/*      */                     
/* 1280 */                     if ((!usertype.equals("F")) || (com.adventnet.appmanager.util.Constants.isIt360))
/*      */                     {
/* 1282 */                       out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                       
/* 1284 */                       ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1285 */                       _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 1286 */                       _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f0);
/* 1287 */                       int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 1288 */                       if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                         for (;;) {
/* 1290 */                           out.write(10);
/* 1291 */                           out.write(32);
/* 1292 */                           out.write(32);
/*      */                           
/* 1294 */                           WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1295 */                           _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 1296 */                           _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                           
/* 1298 */                           _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/* 1299 */                           int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 1300 */                           if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                             for (;;) {
/* 1302 */                               out.write(10);
/* 1303 */                               out.write(32);
/* 1304 */                               out.write(32);
/* 1305 */                               out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1306 */                               out.write(10);
/* 1307 */                               out.write(32);
/* 1308 */                               out.write(32);
/* 1309 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1310 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1314 */                           if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1315 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                           }
/*      */                           
/* 1318 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1319 */                           out.write(10);
/* 1320 */                           out.write(32);
/*      */                           
/* 1322 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1323 */                           _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 1324 */                           _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 1325 */                           int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 1326 */                           if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                             for (;;) {
/* 1328 */                               out.write("\n   ");
/*      */                               
/* 1330 */                               EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f6 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1331 */                               _jspx_th_ea_005feeadminlink_005f6.setPageContext(_jspx_page_context);
/* 1332 */                               _jspx_th_ea_005feeadminlink_005f6.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                               
/* 1334 */                               _jspx_th_ea_005feeadminlink_005f6.setHref("/scheduleReports.do?method=showScheduleReports");
/*      */                               
/* 1336 */                               _jspx_th_ea_005feeadminlink_005f6.setEnableClass("new-left-links");
/* 1337 */                               int _jspx_eval_ea_005feeadminlink_005f6 = _jspx_th_ea_005feeadminlink_005f6.doStartTag();
/* 1338 */                               if (_jspx_eval_ea_005feeadminlink_005f6 != 0) {
/* 1339 */                                 if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 1340 */                                   out = _jspx_page_context.pushBody();
/* 1341 */                                   _jspx_th_ea_005feeadminlink_005f6.setBodyContent((BodyContent)out);
/* 1342 */                                   _jspx_th_ea_005feeadminlink_005f6.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1345 */                                   out.write("\n   ");
/* 1346 */                                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1347 */                                   out.write(10);
/* 1348 */                                   out.write(32);
/* 1349 */                                   out.write(32);
/* 1350 */                                   int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f6.doAfterBody();
/* 1351 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1354 */                                 if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 1355 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1358 */                               if (_jspx_th_ea_005feeadminlink_005f6.doEndTag() == 5) {
/* 1359 */                                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6); return;
/*      */                               }
/*      */                               
/* 1362 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6);
/* 1363 */                               out.write(10);
/* 1364 */                               out.write(32);
/* 1365 */                               out.write(32);
/* 1366 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 1367 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1371 */                           if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 1372 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                           }
/*      */                           
/* 1375 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 1376 */                           out.write(10);
/* 1377 */                           out.write(32);
/* 1378 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 1379 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1383 */                       if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 1384 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                       }
/*      */                       
/* 1387 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 1388 */                       out.write("\n</td>\n</tr>\n");
/*      */                     } else {
/* 1390 */                       out.write("\n<tr>\n    <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 1391 */                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1392 */                       out.write("\n    </td>\n</tr>\n");
/*      */                     }
/* 1394 */                     out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                     
/* 1396 */                     ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1397 */                     _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 1398 */                     _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f0);
/* 1399 */                     int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 1400 */                     if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                       for (;;) {
/* 1402 */                         out.write(10);
/* 1403 */                         out.write(32);
/* 1404 */                         out.write(32);
/*      */                         
/* 1406 */                         WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1407 */                         _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 1408 */                         _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                         
/* 1410 */                         _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showDataCleanUp'}");
/* 1411 */                         int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 1412 */                         if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                           for (;;) {
/* 1414 */                             out.write(10);
/* 1415 */                             out.write(32);
/* 1416 */                             out.write(32);
/* 1417 */                             out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1418 */                             out.write(10);
/* 1419 */                             out.write(32);
/* 1420 */                             out.write(32);
/* 1421 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 1422 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1426 */                         if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 1427 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                         }
/*      */                         
/* 1430 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 1431 */                         out.write(10);
/* 1432 */                         out.write(32);
/*      */                         
/* 1434 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1435 */                         _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 1436 */                         _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 1437 */                         int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 1438 */                         if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                           for (;;) {
/* 1440 */                             out.write("\n   ");
/*      */                             
/* 1442 */                             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f7 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1443 */                             _jspx_th_ea_005feeadminlink_005f7.setPageContext(_jspx_page_context);
/* 1444 */                             _jspx_th_ea_005feeadminlink_005f7.setParent(_jspx_th_c_005fotherwise_005f8);
/*      */                             
/* 1446 */                             _jspx_th_ea_005feeadminlink_005f7.setHref("/adminAction.do?method=showDataCleanUp");
/*      */                             
/* 1448 */                             _jspx_th_ea_005feeadminlink_005f7.setEnableClass("new-left-links");
/* 1449 */                             int _jspx_eval_ea_005feeadminlink_005f7 = _jspx_th_ea_005feeadminlink_005f7.doStartTag();
/* 1450 */                             if (_jspx_eval_ea_005feeadminlink_005f7 != 0) {
/* 1451 */                               if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 1452 */                                 out = _jspx_page_context.pushBody();
/* 1453 */                                 _jspx_th_ea_005feeadminlink_005f7.setBodyContent((BodyContent)out);
/* 1454 */                                 _jspx_th_ea_005feeadminlink_005f7.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1457 */                                 out.write("\n   ");
/* 1458 */                                 out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1459 */                                 out.write(10);
/* 1460 */                                 out.write(32);
/* 1461 */                                 out.write(32);
/* 1462 */                                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f7.doAfterBody();
/* 1463 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1466 */                               if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 1467 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1470 */                             if (_jspx_th_ea_005feeadminlink_005f7.doEndTag() == 5) {
/* 1471 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7); return;
/*      */                             }
/*      */                             
/* 1474 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7);
/* 1475 */                             out.write(10);
/* 1476 */                             out.write(32);
/* 1477 */                             out.write(32);
/* 1478 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 1479 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1483 */                         if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 1484 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                         }
/*      */                         
/* 1487 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 1488 */                         out.write(10);
/* 1489 */                         out.write(32);
/* 1490 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 1491 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1495 */                     if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 1496 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                     }
/*      */                     
/* 1499 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 1500 */                     out.write("\n</td>\n</tr>\n</table>\n\n");
/* 1501 */                     out.write(10);
/* 1502 */                     out.write(32);
/*      */                   }
/*      */                   else
/*      */                   {
/* 1506 */                     out.write("\n   ");
/* 1507 */                     out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                     
/*      */ 
/* 1510 */                     String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                     
/* 1512 */                     out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 1513 */                     out.print(FormatUtil.getString("wizard.disabled"));
/* 1514 */                     out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 1515 */                     out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 1516 */                     out.write("</td>\n  </tr>\n  \n ");
/*      */                     
/* 1518 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                     {
/*      */ 
/* 1521 */                       out.write("  \n  <tr>\n\n  ");
/*      */                       
/* 1523 */                       if (request.getParameter("wiz") != null)
/*      */                       {
/* 1525 */                         out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 1526 */                         out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1527 */                         out.write("\" class='disabledlink'>");
/* 1528 */                         out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1529 */                         out.write("</a></td>\n  ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 1533 */                         out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                         
/* 1535 */                         ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1536 */                         _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 1537 */                         _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f0);
/* 1538 */                         int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 1539 */                         if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                           for (;;) {
/* 1541 */                             out.write(10);
/*      */                             
/* 1543 */                             WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1544 */                             _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1545 */                             _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                             
/* 1547 */                             _jspx_th_c_005fwhen_005f9.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 1548 */                             int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1549 */                             if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                               for (;;) {
/* 1551 */                                 out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 1552 */                                 out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1553 */                                 out.write("\n    </a>\n ");
/* 1554 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1555 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1559 */                             if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1560 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                             }
/*      */                             
/* 1563 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1564 */                             out.write(10);
/* 1565 */                             out.write(32);
/*      */                             
/* 1567 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1568 */                             _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1569 */                             _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1570 */                             int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1571 */                             if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                               for (;;) {
/* 1573 */                                 out.write(10);
/* 1574 */                                 out.write(9);
/* 1575 */                                 out.write(32);
/* 1576 */                                 out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1577 */                                 out.write(10);
/* 1578 */                                 out.write(32);
/* 1579 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1580 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1584 */                             if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1585 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                             }
/*      */                             
/* 1588 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1589 */                             out.write(10);
/* 1590 */                             out.write(32);
/* 1591 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1592 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1596 */                         if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1597 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                         }
/*      */                         
/* 1600 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1601 */                         out.write("\n    </td>\n\t");
/*      */                       }
/* 1603 */                       out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                       
/* 1605 */                       if (request.getParameter("wiz") != null)
/*      */                       {
/* 1607 */                         out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 1608 */                         out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1609 */                         out.write("\" class='disabledlink'>");
/* 1610 */                         out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1611 */                         out.write("</a></td>\n   ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 1615 */                         out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                         
/* 1617 */                         ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1618 */                         _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 1619 */                         _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f0);
/* 1620 */                         int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 1621 */                         if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                           for (;;) {
/* 1623 */                             out.write(10);
/*      */                             
/* 1625 */                             WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1626 */                             _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1627 */                             _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                             
/* 1629 */                             _jspx_th_c_005fwhen_005f10.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 1630 */                             int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1631 */                             if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                               for (;;) {
/* 1633 */                                 out.write("\n   ");
/* 1634 */                                 out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1635 */                                 out.write(10);
/* 1636 */                                 out.write(32);
/* 1637 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1638 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1642 */                             if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1643 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                             }
/*      */                             
/* 1646 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1647 */                             out.write(10);
/* 1648 */                             out.write(32);
/*      */                             
/* 1650 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1651 */                             _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 1652 */                             _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 1653 */                             int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 1654 */                             if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                               for (;;) {
/* 1656 */                                 out.write(10);
/* 1657 */                                 String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 1658 */                                 out.write("\n\t \n <a href=\"");
/* 1659 */                                 out.print(link);
/* 1660 */                                 out.write("\" class=\"new-left-links\">\n               ");
/* 1661 */                                 out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1662 */                                 out.write("\n    </a>    \n ");
/* 1663 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 1664 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1668 */                             if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 1669 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                             }
/*      */                             
/* 1672 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 1673 */                             out.write(10);
/* 1674 */                             out.write(32);
/* 1675 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 1676 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1680 */                         if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 1681 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                         }
/*      */                         
/* 1684 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 1685 */                         out.write("\n</td>\n");
/*      */                       }
/* 1687 */                       out.write("\n</tr>\n\n ");
/*      */                     }
/*      */                     
/*      */ 
/* 1691 */                     out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 1693 */                     ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1694 */                     _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 1695 */                     _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f0);
/* 1696 */                     int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 1697 */                     if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                       for (;;) {
/* 1699 */                         out.write(10);
/*      */                         
/* 1701 */                         WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1702 */                         _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 1703 */                         _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                         
/* 1705 */                         _jspx_th_c_005fwhen_005f11.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 1706 */                         int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 1707 */                         if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                           for (;;) {
/* 1709 */                             out.write("\n    \n       ");
/* 1710 */                             out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1711 */                             out.write(10);
/* 1712 */                             out.write(32);
/* 1713 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 1714 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1718 */                         if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 1719 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                         }
/*      */                         
/* 1722 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 1723 */                         out.write(10);
/* 1724 */                         out.write(32);
/*      */                         
/* 1726 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1727 */                         _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 1728 */                         _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 1729 */                         int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 1730 */                         if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                           for (;;) {
/* 1732 */                             out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 1733 */                             out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1734 */                             out.write("\n    </a>\n ");
/* 1735 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 1736 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1740 */                         if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 1741 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                         }
/*      */                         
/* 1744 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 1745 */                         out.write(10);
/* 1746 */                         out.write(32);
/* 1747 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 1748 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1752 */                     if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 1753 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                     }
/*      */                     
/* 1756 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 1757 */                     out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 1759 */                     ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1760 */                     _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 1761 */                     _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f0);
/* 1762 */                     int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 1763 */                     if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                       for (;;) {
/* 1765 */                         out.write(10);
/*      */                         
/* 1767 */                         WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1768 */                         _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 1769 */                         _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                         
/* 1771 */                         _jspx_th_c_005fwhen_005f12.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 1772 */                         int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 1773 */                         if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                           for (;;) {
/* 1775 */                             out.write("\n    \n       ");
/* 1776 */                             out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 1777 */                             out.write(10);
/* 1778 */                             out.write(32);
/* 1779 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 1780 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1784 */                         if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 1785 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                         }
/*      */                         
/* 1788 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 1789 */                         out.write(10);
/* 1790 */                         out.write(32);
/*      */                         
/* 1792 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1793 */                         _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 1794 */                         _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 1795 */                         int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 1796 */                         if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                           for (;;) {
/* 1798 */                             out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 1799 */                             out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 1800 */                             out.write("\n\t </a>\n ");
/* 1801 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 1802 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1806 */                         if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 1807 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                         }
/*      */                         
/* 1810 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 1811 */                         out.write(10);
/* 1812 */                         out.write(32);
/* 1813 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 1814 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1818 */                     if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 1819 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                     }
/*      */                     
/* 1822 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 1823 */                     out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                     
/* 1825 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                     {
/*      */ 
/* 1828 */                       out.write(32);
/* 1829 */                       out.write(32);
/* 1830 */                       out.write(10);
/*      */                       
/* 1832 */                       ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1833 */                       _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 1834 */                       _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f0);
/* 1835 */                       int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 1836 */                       if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                         for (;;) {
/* 1838 */                           out.write(10);
/*      */                           
/* 1840 */                           WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1841 */                           _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 1842 */                           _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                           
/* 1844 */                           _jspx_th_c_005fwhen_005f13.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 1845 */                           int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 1846 */                           if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                             for (;;) {
/* 1848 */                               out.write("\n<tr>\n    ");
/* 1849 */                               if (!request.isUserInRole("OPERATOR")) {
/* 1850 */                                 out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 1851 */                                 out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1852 */                                 out.write("\n    </a>\n        </td>\n     ");
/*      */                               } else {
/* 1854 */                                 out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 1855 */                                 out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1856 */                                 out.write("\n\t</a>\n\t </td>\n\t");
/*      */                               }
/* 1858 */                               out.write("\n    </tr>\n ");
/* 1859 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 1860 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1864 */                           if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 1865 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                           }
/*      */                           
/* 1868 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 1869 */                           out.write(10);
/* 1870 */                           out.write(32);
/*      */                           
/* 1872 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1873 */                           _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 1874 */                           _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 1875 */                           int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 1876 */                           if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                             for (;;) {
/* 1878 */                               out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 1879 */                               out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1880 */                               out.write("\n\t </td>\n ");
/* 1881 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 1882 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1886 */                           if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 1887 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                           }
/*      */                           
/* 1890 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 1891 */                           out.write(10);
/* 1892 */                           out.write(32);
/* 1893 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 1894 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1898 */                       if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 1899 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                       }
/*      */                       
/* 1902 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 1903 */                       out.write("\n \n  ");
/*      */                     }
/*      */                     
/*      */ 
/* 1907 */                     out.write("  \n \n ");
/*      */                     
/* 1909 */                     if (!usertype.equals("F"))
/*      */                     {
/* 1911 */                       out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                       
/* 1913 */                       ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1914 */                       _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 1915 */                       _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f0);
/* 1916 */                       int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 1917 */                       if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                         for (;;) {
/* 1919 */                           out.write(10);
/* 1920 */                           out.write(9);
/*      */                           
/* 1922 */                           WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1923 */                           _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 1924 */                           _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                           
/* 1926 */                           _jspx_th_c_005fwhen_005f14.setTest("${param.method !='maintenanceTaskListView'}");
/* 1927 */                           int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 1928 */                           if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                             for (;;) {
/* 1930 */                               out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 1931 */                               out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1932 */                               out.write("</a>\n  \t");
/* 1933 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 1934 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1938 */                           if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 1939 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                           }
/*      */                           
/* 1942 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 1943 */                           out.write("\n  \t");
/*      */                           
/* 1945 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1946 */                           _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 1947 */                           _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 1948 */                           int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 1949 */                           if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                             for (;;) {
/* 1951 */                               out.write("\n \t\t");
/* 1952 */                               out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1953 */                               out.write("\n  \t");
/* 1954 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 1955 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1959 */                           if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 1960 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                           }
/*      */                           
/* 1963 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 1964 */                           out.write("\n  \t");
/* 1965 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 1966 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1970 */                       if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 1971 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                       }
/*      */                       
/* 1974 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 1975 */                       out.write("\n     </td>\n </tr>   \n \n ");
/*      */                       
/* 1977 */                       if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                       {
/*      */ 
/* 1980 */                         out.write(32);
/* 1981 */                         out.write(32);
/* 1982 */                         out.write(10);
/*      */                         
/* 1984 */                         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1985 */                         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1986 */                         _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f0);
/*      */                         
/* 1988 */                         _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/* 1989 */                         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1990 */                         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                           for (;;) {
/* 1992 */                             out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                             
/* 1994 */                             ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1995 */                             _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 1996 */                             _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_c_005fif_005f0);
/* 1997 */                             int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 1998 */                             if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                               for (;;) {
/* 2000 */                                 out.write(10);
/* 2001 */                                 out.write(32);
/* 2002 */                                 out.write(9);
/*      */                                 
/* 2004 */                                 WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2005 */                                 _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 2006 */                                 _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                                 
/* 2008 */                                 _jspx_th_c_005fwhen_005f15.setTest("${param.method !='listTrapListener'}");
/* 2009 */                                 int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 2010 */                                 if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                   for (;;) {
/* 2012 */                                     out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 2013 */                                     out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 2014 */                                     out.write("</a>\n   \t");
/* 2015 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 2016 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2020 */                                 if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 2021 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                                 }
/*      */                                 
/* 2024 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 2025 */                                 out.write("\n   \t");
/*      */                                 
/* 2027 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2028 */                                 _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 2029 */                                 _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 2030 */                                 int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 2031 */                                 if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                   for (;;) {
/* 2033 */                                     out.write("\n  \t\t");
/* 2034 */                                     out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 2035 */                                     out.write(" \n   \t");
/* 2036 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 2037 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2041 */                                 if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 2042 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                                 }
/*      */                                 
/* 2045 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 2046 */                                 out.write("\n   \t");
/* 2047 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 2048 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2052 */                             if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 2053 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                             }
/*      */                             
/* 2056 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 2057 */                             out.write("\n      </td>\n  </tr>   \n");
/* 2058 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2059 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2063 */                         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2064 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                         }
/*      */                         
/* 2067 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2068 */                         out.write(10);
/* 2069 */                         out.write(32);
/*      */                       }
/*      */                       
/*      */ 
/* 2073 */                       out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                       
/* 2075 */                       ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2076 */                       _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 2077 */                       _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_tiles_005fput_005f0);
/* 2078 */                       int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 2079 */                       if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                         for (;;) {
/* 2081 */                           out.write(10);
/*      */                           
/* 2083 */                           WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2084 */                           _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 2085 */                           _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */                           
/* 2087 */                           _jspx_th_c_005fwhen_005f16.setTest("${param.method =='showScheduleReports'}");
/* 2088 */                           int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 2089 */                           if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                             for (;;) {
/* 2091 */                               out.write("\n       ");
/* 2092 */                               out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 2093 */                               out.write(10);
/* 2094 */                               out.write(32);
/* 2095 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 2096 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2100 */                           if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 2101 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */                           }
/*      */                           
/* 2104 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 2105 */                           out.write(10);
/* 2106 */                           out.write(32);
/*      */                           
/* 2108 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2109 */                           _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 2110 */                           _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/* 2111 */                           int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 2112 */                           if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                             for (;;) {
/* 2114 */                               out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 2115 */                               out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 2116 */                               out.write("\n\t </a>\n ");
/* 2117 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 2118 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2122 */                           if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 2123 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */                           }
/*      */                           
/* 2126 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 2127 */                           out.write(10);
/* 2128 */                           out.write(32);
/* 2129 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 2130 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2134 */                       if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 2135 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */                       }
/*      */                       
/* 2138 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 2139 */                       out.write("\n    </td>\n</tr> \n");
/*      */                     } else {
/* 2141 */                       out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 2142 */                       out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 2143 */                       out.write("</a>\n     </td>\n </tr>   \n");
/*      */                       
/* 2145 */                       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2146 */                       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2147 */                       _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f0);
/*      */                       
/* 2149 */                       _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 2150 */                       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2151 */                       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                         for (;;) {
/* 2153 */                           out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 2154 */                           out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 2155 */                           out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 2156 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2157 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2161 */                       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2162 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                       }
/*      */                       
/* 2165 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2166 */                       out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 2167 */                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 2168 */                       out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                     }
/* 2170 */                     out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 2172 */                     ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2173 */                     _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 2174 */                     _jspx_th_c_005fchoose_005f17.setParent(_jspx_th_tiles_005fput_005f0);
/* 2175 */                     int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 2176 */                     if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */                       for (;;) {
/* 2178 */                         out.write(10);
/*      */                         
/* 2180 */                         WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2181 */                         _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 2182 */                         _jspx_th_c_005fwhen_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/*      */                         
/* 2184 */                         _jspx_th_c_005fwhen_005f17.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 2185 */                         int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 2186 */                         if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */                           for (;;) {
/* 2188 */                             out.write("\n        ");
/* 2189 */                             out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 2190 */                             out.write(10);
/* 2191 */                             out.write(32);
/* 2192 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 2193 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2197 */                         if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 2198 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17); return;
/*      */                         }
/*      */                         
/* 2201 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 2202 */                         out.write(10);
/* 2203 */                         out.write(32);
/*      */                         
/* 2205 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2206 */                         _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 2207 */                         _jspx_th_c_005fotherwise_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/* 2208 */                         int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 2209 */                         if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */                           for (;;) {
/* 2211 */                             out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 2212 */                             out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 2213 */                             out.write("\n\t </a>\n ");
/* 2214 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 2215 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2219 */                         if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 2220 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17); return;
/*      */                         }
/*      */                         
/* 2223 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 2224 */                         out.write(10);
/* 2225 */                         out.write(32);
/* 2226 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 2227 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2231 */                     if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 2232 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17); return;
/*      */                     }
/*      */                     
/* 2235 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 2236 */                     out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 2238 */                     ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2239 */                     _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 2240 */                     _jspx_th_c_005fchoose_005f18.setParent(_jspx_th_tiles_005fput_005f0);
/* 2241 */                     int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 2242 */                     if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */                       for (;;) {
/* 2244 */                         out.write(10);
/*      */                         
/* 2246 */                         WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2247 */                         _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 2248 */                         _jspx_th_c_005fwhen_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/*      */                         
/* 2250 */                         _jspx_th_c_005fwhen_005f18.setTest("${param.method!='showMailServerConfiguration'}");
/* 2251 */                         int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 2252 */                         if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */                           for (;;) {
/* 2254 */                             out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 2255 */                             out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 2256 */                             out.write("\n    </a>    \n ");
/* 2257 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 2258 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2262 */                         if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 2263 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18); return;
/*      */                         }
/*      */                         
/* 2266 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 2267 */                         out.write(10);
/* 2268 */                         out.write(32);
/*      */                         
/* 2270 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2271 */                         _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 2272 */                         _jspx_th_c_005fotherwise_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/* 2273 */                         int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 2274 */                         if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */                           for (;;) {
/* 2276 */                             out.write(10);
/* 2277 */                             out.write(9);
/* 2278 */                             out.write(32);
/* 2279 */                             out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 2280 */                             out.write(10);
/* 2281 */                             out.write(32);
/* 2282 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 2283 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2287 */                         if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 2288 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18); return;
/*      */                         }
/*      */                         
/* 2291 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 2292 */                         out.write(10);
/* 2293 */                         out.write(32);
/* 2294 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 2295 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2299 */                     if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 2300 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18); return;
/*      */                     }
/*      */                     
/* 2303 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 2304 */                     out.write("\n    </td>\n</tr>\n\n\n");
/* 2305 */                     if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 2306 */                       out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                       
/* 2308 */                       ChooseTag _jspx_th_c_005fchoose_005f19 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2309 */                       _jspx_th_c_005fchoose_005f19.setPageContext(_jspx_page_context);
/* 2310 */                       _jspx_th_c_005fchoose_005f19.setParent(_jspx_th_tiles_005fput_005f0);
/* 2311 */                       int _jspx_eval_c_005fchoose_005f19 = _jspx_th_c_005fchoose_005f19.doStartTag();
/* 2312 */                       if (_jspx_eval_c_005fchoose_005f19 != 0) {
/*      */                         for (;;) {
/* 2314 */                           out.write(10);
/*      */                           
/* 2316 */                           WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2317 */                           _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 2318 */                           _jspx_th_c_005fwhen_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/*      */                           
/* 2320 */                           _jspx_th_c_005fwhen_005f19.setTest("${param.method!='SMSServerConfiguration'}");
/* 2321 */                           int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 2322 */                           if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */                             for (;;) {
/* 2324 */                               out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 2325 */                               out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2326 */                               out.write("\n    </a>\n ");
/* 2327 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 2328 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2332 */                           if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 2333 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19); return;
/*      */                           }
/*      */                           
/* 2336 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 2337 */                           out.write(10);
/* 2338 */                           out.write(32);
/*      */                           
/* 2340 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f19 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2341 */                           _jspx_th_c_005fotherwise_005f19.setPageContext(_jspx_page_context);
/* 2342 */                           _jspx_th_c_005fotherwise_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/* 2343 */                           int _jspx_eval_c_005fotherwise_005f19 = _jspx_th_c_005fotherwise_005f19.doStartTag();
/* 2344 */                           if (_jspx_eval_c_005fotherwise_005f19 != 0) {
/*      */                             for (;;) {
/* 2346 */                               out.write("\n         ");
/* 2347 */                               out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2348 */                               out.write(10);
/* 2349 */                               out.write(32);
/* 2350 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f19.doAfterBody();
/* 2351 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2355 */                           if (_jspx_th_c_005fotherwise_005f19.doEndTag() == 5) {
/* 2356 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19); return;
/*      */                           }
/*      */                           
/* 2359 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 2360 */                           out.write(10);
/* 2361 */                           out.write(32);
/* 2362 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f19.doAfterBody();
/* 2363 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2367 */                       if (_jspx_th_c_005fchoose_005f19.doEndTag() == 5) {
/* 2368 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19); return;
/*      */                       }
/*      */                       
/* 2371 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 2372 */                       out.write("\n    </td>\n</tr>\n");
/*      */                     }
/* 2374 */                     out.write("\n\n\n ");
/*      */                     
/* 2376 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                     {
/*      */ 
/* 2379 */                       out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                       
/* 2381 */                       ChooseTag _jspx_th_c_005fchoose_005f20 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2382 */                       _jspx_th_c_005fchoose_005f20.setPageContext(_jspx_page_context);
/* 2383 */                       _jspx_th_c_005fchoose_005f20.setParent(_jspx_th_tiles_005fput_005f0);
/* 2384 */                       int _jspx_eval_c_005fchoose_005f20 = _jspx_th_c_005fchoose_005f20.doStartTag();
/* 2385 */                       if (_jspx_eval_c_005fchoose_005f20 != 0) {
/*      */                         for (;;) {
/* 2387 */                           out.write(10);
/*      */                           
/* 2389 */                           WhenTag _jspx_th_c_005fwhen_005f20 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2390 */                           _jspx_th_c_005fwhen_005f20.setPageContext(_jspx_page_context);
/* 2391 */                           _jspx_th_c_005fwhen_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/*      */                           
/* 2393 */                           _jspx_th_c_005fwhen_005f20.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 2394 */                           int _jspx_eval_c_005fwhen_005f20 = _jspx_th_c_005fwhen_005f20.doStartTag();
/* 2395 */                           if (_jspx_eval_c_005fwhen_005f20 != 0) {
/*      */                             for (;;) {
/* 2397 */                               out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 2398 */                               out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2399 */                               out.write("\n    </a>\n ");
/* 2400 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f20.doAfterBody();
/* 2401 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2405 */                           if (_jspx_th_c_005fwhen_005f20.doEndTag() == 5) {
/* 2406 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20); return;
/*      */                           }
/*      */                           
/* 2409 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 2410 */                           out.write(10);
/* 2411 */                           out.write(32);
/*      */                           
/* 2413 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f20 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2414 */                           _jspx_th_c_005fotherwise_005f20.setPageContext(_jspx_page_context);
/* 2415 */                           _jspx_th_c_005fotherwise_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/* 2416 */                           int _jspx_eval_c_005fotherwise_005f20 = _jspx_th_c_005fotherwise_005f20.doStartTag();
/* 2417 */                           if (_jspx_eval_c_005fotherwise_005f20 != 0) {
/*      */                             for (;;) {
/* 2419 */                               out.write(10);
/* 2420 */                               out.write(9);
/* 2421 */                               out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2422 */                               out.write(10);
/* 2423 */                               out.write(32);
/* 2424 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f20.doAfterBody();
/* 2425 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2429 */                           if (_jspx_th_c_005fotherwise_005f20.doEndTag() == 5) {
/* 2430 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20); return;
/*      */                           }
/*      */                           
/* 2433 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20);
/* 2434 */                           out.write(10);
/* 2435 */                           out.write(32);
/* 2436 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f20.doAfterBody();
/* 2437 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2441 */                       if (_jspx_th_c_005fchoose_005f20.doEndTag() == 5) {
/* 2442 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20); return;
/*      */                       }
/*      */                       
/* 2445 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20);
/* 2446 */                       out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                       
/* 2448 */                       ChooseTag _jspx_th_c_005fchoose_005f21 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2449 */                       _jspx_th_c_005fchoose_005f21.setPageContext(_jspx_page_context);
/* 2450 */                       _jspx_th_c_005fchoose_005f21.setParent(_jspx_th_tiles_005fput_005f0);
/* 2451 */                       int _jspx_eval_c_005fchoose_005f21 = _jspx_th_c_005fchoose_005f21.doStartTag();
/* 2452 */                       if (_jspx_eval_c_005fchoose_005f21 != 0) {
/*      */                         for (;;) {
/* 2454 */                           out.write(10);
/*      */                           
/* 2456 */                           WhenTag _jspx_th_c_005fwhen_005f21 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2457 */                           _jspx_th_c_005fwhen_005f21.setPageContext(_jspx_page_context);
/* 2458 */                           _jspx_th_c_005fwhen_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/*      */                           
/* 2460 */                           _jspx_th_c_005fwhen_005f21.setTest("${uri !='/Upload.do'}");
/* 2461 */                           int _jspx_eval_c_005fwhen_005f21 = _jspx_th_c_005fwhen_005f21.doStartTag();
/* 2462 */                           if (_jspx_eval_c_005fwhen_005f21 != 0) {
/*      */                             for (;;) {
/* 2464 */                               out.write("   \n        ");
/*      */                               
/* 2466 */                               AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2467 */                               _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 2468 */                               _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f21);
/*      */                               
/* 2470 */                               _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                               
/* 2472 */                               _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 2473 */                               int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 2474 */                               if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 2475 */                                 if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2476 */                                   out = _jspx_page_context.pushBody();
/* 2477 */                                   _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 2478 */                                   _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2481 */                                   out.write("\n           ");
/* 2482 */                                   out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 2483 */                                   out.write("\n            ");
/* 2484 */                                   int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 2485 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2488 */                                 if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2489 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2492 */                               if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 2493 */                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                               }
/*      */                               
/* 2496 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 2497 */                               out.write(10);
/* 2498 */                               out.write(10);
/* 2499 */                               out.write(32);
/* 2500 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f21.doAfterBody();
/* 2501 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2505 */                           if (_jspx_th_c_005fwhen_005f21.doEndTag() == 5) {
/* 2506 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21); return;
/*      */                           }
/*      */                           
/* 2509 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21);
/* 2510 */                           out.write(10);
/* 2511 */                           out.write(32);
/*      */                           
/* 2513 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f21 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2514 */                           _jspx_th_c_005fotherwise_005f21.setPageContext(_jspx_page_context);
/* 2515 */                           _jspx_th_c_005fotherwise_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/* 2516 */                           int _jspx_eval_c_005fotherwise_005f21 = _jspx_th_c_005fotherwise_005f21.doStartTag();
/* 2517 */                           if (_jspx_eval_c_005fotherwise_005f21 != 0) {
/*      */                             for (;;) {
/* 2519 */                               out.write(10);
/* 2520 */                               out.write(9);
/* 2521 */                               out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 2522 */                               out.write(10);
/* 2523 */                               out.write(32);
/* 2524 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f21.doAfterBody();
/* 2525 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2529 */                           if (_jspx_th_c_005fotherwise_005f21.doEndTag() == 5) {
/* 2530 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21); return;
/*      */                           }
/*      */                           
/* 2533 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21);
/* 2534 */                           out.write(10);
/* 2535 */                           out.write(32);
/* 2536 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f21.doAfterBody();
/* 2537 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2541 */                       if (_jspx_th_c_005fchoose_005f21.doEndTag() == 5) {
/* 2542 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21); return;
/*      */                       }
/*      */                       
/* 2545 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21);
/* 2546 */                       out.write("\n    </td>\n</tr>\n \n ");
/*      */                     }
/*      */                     
/*      */ 
/* 2550 */                     out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                     
/* 2552 */                     ChooseTag _jspx_th_c_005fchoose_005f22 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2553 */                     _jspx_th_c_005fchoose_005f22.setPageContext(_jspx_page_context);
/* 2554 */                     _jspx_th_c_005fchoose_005f22.setParent(_jspx_th_tiles_005fput_005f0);
/* 2555 */                     int _jspx_eval_c_005fchoose_005f22 = _jspx_th_c_005fchoose_005f22.doStartTag();
/* 2556 */                     if (_jspx_eval_c_005fchoose_005f22 != 0) {
/*      */                       for (;;) {
/* 2558 */                         out.write(10);
/*      */                         
/* 2560 */                         WhenTag _jspx_th_c_005fwhen_005f22 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2561 */                         _jspx_th_c_005fwhen_005f22.setPageContext(_jspx_page_context);
/* 2562 */                         _jspx_th_c_005fwhen_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/*      */                         
/* 2564 */                         _jspx_th_c_005fwhen_005f22.setTest("${uri !='/admin/userconfiguration.do'}");
/* 2565 */                         int _jspx_eval_c_005fwhen_005f22 = _jspx_th_c_005fwhen_005f22.doStartTag();
/* 2566 */                         if (_jspx_eval_c_005fwhen_005f22 != 0) {
/*      */                           for (;;) {
/* 2568 */                             out.write("\n    \n        ");
/*      */                             
/* 2570 */                             AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2571 */                             _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 2572 */                             _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f22);
/*      */                             
/* 2574 */                             _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                             
/* 2576 */                             _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 2577 */                             int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 2578 */                             if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 2579 */                               if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2580 */                                 out = _jspx_page_context.pushBody();
/* 2581 */                                 _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 2582 */                                 _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2585 */                                 out.write("\n       ");
/* 2586 */                                 out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2587 */                                 out.write("\n        ");
/* 2588 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 2589 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2592 */                               if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2593 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2596 */                             if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 2597 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                             }
/*      */                             
/* 2600 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 2601 */                             out.write(10);
/* 2602 */                             out.write(10);
/* 2603 */                             out.write(32);
/* 2604 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f22.doAfterBody();
/* 2605 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2609 */                         if (_jspx_th_c_005fwhen_005f22.doEndTag() == 5) {
/* 2610 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22); return;
/*      */                         }
/*      */                         
/* 2613 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22);
/* 2614 */                         out.write(10);
/* 2615 */                         out.write(32);
/*      */                         
/* 2617 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f22 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2618 */                         _jspx_th_c_005fotherwise_005f22.setPageContext(_jspx_page_context);
/* 2619 */                         _jspx_th_c_005fotherwise_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/* 2620 */                         int _jspx_eval_c_005fotherwise_005f22 = _jspx_th_c_005fotherwise_005f22.doStartTag();
/* 2621 */                         if (_jspx_eval_c_005fotherwise_005f22 != 0) {
/*      */                           for (;;) {
/* 2623 */                             out.write(10);
/* 2624 */                             out.write(9);
/* 2625 */                             out.write(32);
/* 2626 */                             out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2627 */                             out.write(10);
/* 2628 */                             out.write(32);
/* 2629 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f22.doAfterBody();
/* 2630 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2634 */                         if (_jspx_th_c_005fotherwise_005f22.doEndTag() == 5) {
/* 2635 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22); return;
/*      */                         }
/*      */                         
/* 2638 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22);
/* 2639 */                         out.write(10);
/* 2640 */                         out.write(32);
/* 2641 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f22.doAfterBody();
/* 2642 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2646 */                     if (_jspx_th_c_005fchoose_005f22.doEndTag() == 5) {
/* 2647 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22); return;
/*      */                     }
/*      */                     
/* 2650 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22);
/* 2651 */                     out.write("\n    </td>\n</tr>\n   \n\n ");
/* 2652 */                     if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2653 */                       out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                       
/* 2655 */                       ChooseTag _jspx_th_c_005fchoose_005f23 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2656 */                       _jspx_th_c_005fchoose_005f23.setPageContext(_jspx_page_context);
/* 2657 */                       _jspx_th_c_005fchoose_005f23.setParent(_jspx_th_tiles_005fput_005f0);
/* 2658 */                       int _jspx_eval_c_005fchoose_005f23 = _jspx_th_c_005fchoose_005f23.doStartTag();
/* 2659 */                       if (_jspx_eval_c_005fchoose_005f23 != 0) {
/*      */                         for (;;) {
/* 2661 */                           out.write("\n   ");
/*      */                           
/* 2663 */                           WhenTag _jspx_th_c_005fwhen_005f23 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2664 */                           _jspx_th_c_005fwhen_005f23.setPageContext(_jspx_page_context);
/* 2665 */                           _jspx_th_c_005fwhen_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/*      */                           
/* 2667 */                           _jspx_th_c_005fwhen_005f23.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 2668 */                           int _jspx_eval_c_005fwhen_005f23 = _jspx_th_c_005fwhen_005f23.doStartTag();
/* 2669 */                           if (_jspx_eval_c_005fwhen_005f23 != 0) {
/*      */                             for (;;) {
/* 2671 */                               out.write("\n    ");
/*      */                               
/* 2673 */                               AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2674 */                               _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 2675 */                               _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f23);
/*      */                               
/* 2677 */                               _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                               
/* 2679 */                               _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 2680 */                               int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 2681 */                               if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 2682 */                                 if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2683 */                                   out = _jspx_page_context.pushBody();
/* 2684 */                                   _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 2685 */                                   _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2688 */                                   out.write(10);
/* 2689 */                                   out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 2690 */                                   out.write("\n    ");
/* 2691 */                                   int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 2692 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2695 */                                 if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2696 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2699 */                               if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 2700 */                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                               }
/*      */                               
/* 2703 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 2704 */                               out.write("\n   ");
/* 2705 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f23.doAfterBody();
/* 2706 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2710 */                           if (_jspx_th_c_005fwhen_005f23.doEndTag() == 5) {
/* 2711 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23); return;
/*      */                           }
/*      */                           
/* 2714 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23);
/* 2715 */                           out.write("\n   ");
/*      */                           
/* 2717 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f23 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2718 */                           _jspx_th_c_005fotherwise_005f23.setPageContext(_jspx_page_context);
/* 2719 */                           _jspx_th_c_005fotherwise_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/* 2720 */                           int _jspx_eval_c_005fotherwise_005f23 = _jspx_th_c_005fotherwise_005f23.doStartTag();
/* 2721 */                           if (_jspx_eval_c_005fotherwise_005f23 != 0) {
/*      */                             for (;;) {
/* 2723 */                               out.write(10);
/* 2724 */                               out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 2725 */                               out.write("\n   ");
/* 2726 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f23.doAfterBody();
/* 2727 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2731 */                           if (_jspx_th_c_005fotherwise_005f23.doEndTag() == 5) {
/* 2732 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23); return;
/*      */                           }
/*      */                           
/* 2735 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23);
/* 2736 */                           out.write(10);
/* 2737 */                           out.write(32);
/* 2738 */                           out.write(32);
/* 2739 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f23.doAfterBody();
/* 2740 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2744 */                       if (_jspx_th_c_005fchoose_005f23.doEndTag() == 5) {
/* 2745 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23); return;
/*      */                       }
/*      */                       
/* 2748 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23);
/* 2749 */                       out.write("\n </td>\n</tr>\n  ");
/*      */                     }
/* 2751 */                     out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                     
/* 2753 */                     ChooseTag _jspx_th_c_005fchoose_005f24 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2754 */                     _jspx_th_c_005fchoose_005f24.setPageContext(_jspx_page_context);
/* 2755 */                     _jspx_th_c_005fchoose_005f24.setParent(_jspx_th_tiles_005fput_005f0);
/* 2756 */                     int _jspx_eval_c_005fchoose_005f24 = _jspx_th_c_005fchoose_005f24.doStartTag();
/* 2757 */                     if (_jspx_eval_c_005fchoose_005f24 != 0) {
/*      */                       for (;;) {
/* 2759 */                         out.write("\n   ");
/*      */                         
/* 2761 */                         WhenTag _jspx_th_c_005fwhen_005f24 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2762 */                         _jspx_th_c_005fwhen_005f24.setPageContext(_jspx_page_context);
/* 2763 */                         _jspx_th_c_005fwhen_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/*      */                         
/* 2765 */                         _jspx_th_c_005fwhen_005f24.setTest("${param.method!='showDataCleanUp'}");
/* 2766 */                         int _jspx_eval_c_005fwhen_005f24 = _jspx_th_c_005fwhen_005f24.doStartTag();
/* 2767 */                         if (_jspx_eval_c_005fwhen_005f24 != 0) {
/*      */                           for (;;) {
/* 2769 */                             out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 2770 */                             out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 2771 */                             out.write("\n    </a>\n   ");
/* 2772 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f24.doAfterBody();
/* 2773 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2777 */                         if (_jspx_th_c_005fwhen_005f24.doEndTag() == 5) {
/* 2778 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24); return;
/*      */                         }
/*      */                         
/* 2781 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24);
/* 2782 */                         out.write("\n   ");
/*      */                         
/* 2784 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f24 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2785 */                         _jspx_th_c_005fotherwise_005f24.setPageContext(_jspx_page_context);
/* 2786 */                         _jspx_th_c_005fotherwise_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/* 2787 */                         int _jspx_eval_c_005fotherwise_005f24 = _jspx_th_c_005fotherwise_005f24.doStartTag();
/* 2788 */                         if (_jspx_eval_c_005fotherwise_005f24 != 0) {
/*      */                           for (;;) {
/* 2790 */                             out.write(10);
/* 2791 */                             out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 2792 */                             out.write("\n   ");
/* 2793 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f24.doAfterBody();
/* 2794 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2798 */                         if (_jspx_th_c_005fotherwise_005f24.doEndTag() == 5) {
/* 2799 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24); return;
/*      */                         }
/*      */                         
/* 2802 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24);
/* 2803 */                         out.write(10);
/* 2804 */                         out.write(32);
/* 2805 */                         out.write(32);
/* 2806 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f24.doAfterBody();
/* 2807 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2811 */                     if (_jspx_th_c_005fchoose_005f24.doEndTag() == 5) {
/* 2812 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24); return;
/*      */                     }
/*      */                     
/* 2815 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24);
/* 2816 */                     out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 2817 */                     out.write(10);
/* 2818 */                     out.write(32);
/*      */                   }
/* 2820 */                   out.write("\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr>\n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2821 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2822 */                   out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 2823 */                   if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*      */                     return;
/* 2825 */                   out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n  </tr>\n  <tr>\n    <td colspan=\"2\" class=\"quicknote\"> ");
/* 2826 */                   out.print(FormatUtil.getString("am.webclient.global.quicknote.text"));
/* 2827 */                   out.write(".</td>\n  </tr>\n</table>\n");
/* 2828 */                   int evalDoAfterBody = _jspx_th_tiles_005fput_005f0.doAfterBody();
/* 2829 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2832 */                 if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 2833 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2836 */               if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2837 */                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */               }
/*      */               
/* 2840 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0);
/* 2841 */               out.write(10);
/*      */               
/* 2843 */               PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2844 */               _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2845 */               _jspx_th_tiles_005fput_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */               
/* 2847 */               _jspx_th_tiles_005fput_005f1.setName("UserArea");
/*      */               
/* 2849 */               _jspx_th_tiles_005fput_005f1.setType("string");
/* 2850 */               int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2851 */               if (_jspx_eval_tiles_005fput_005f1 != 0) {
/* 2852 */                 if (_jspx_eval_tiles_005fput_005f1 != 1) {
/* 2853 */                   out = _jspx_page_context.pushBody();
/* 2854 */                   _jspx_th_tiles_005fput_005f1.setBodyContent((BodyContent)out);
/* 2855 */                   _jspx_th_tiles_005fput_005f1.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2858 */                   out.write("\n\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\">\n  <tr>\n   ");
/*      */                   
/* 2860 */                   if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                   {
/* 2862 */                     out.write("\n    <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2863 */                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 2864 */                     out.write("\n      &gt;<span class=\"bcactive\"> ");
/* 2865 */                     out.print(heading);
/* 2866 */                     out.write("</span></td>\n\t");
/*      */                   }
/*      */                   else
/*      */                   {
/* 2870 */                     out.write("\n\t   <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2871 */                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2872 */                     out.write("\n\t    &gt;<span class=\"bcactive\"> ");
/* 2873 */                     out.print(heading);
/* 2874 */                     out.write("</span></td>\n\t ");
/*      */                   }
/*      */                   
/* 2877 */                   if ((request.getParameter("frommondetails") != null) && (request.getParameter("frommondetails").equals("true")))
/*      */                   {
/* 2879 */                     out.write("\n\t   <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\" align=\"right\">\n\t   <a href=\"/showresource.do?resourceid=");
/* 2880 */                     out.print(request.getParameter("resourceid"));
/* 2881 */                     out.write("&method=showResourceForResourceID#2\" class=\"staticlinks\">\n\t   <span class=\"bcactive\">\n\t   ");
/* 2882 */                     out.print(FormatUtil.getString("am.webclient.wsm.monitordetailspage.text"));
/* 2883 */                     out.write("</span></a></td>\n\t ");
/*      */                   }
/*      */                   
/*      */ 
/* 2887 */                   out.write("\n\n  </tr>\n  ");
/*      */                   
/* 2889 */                   if ((request.getParameter("frommondetails") != null) && (request.getParameter("frommondetails").equals("true")))
/*      */                   {
/* 2891 */                     out.write("\n   ");
/*      */                   }
/*      */                   else
/*      */                   {
/* 2895 */                     out.write(10);
/* 2896 */                     out.write(32);
/*      */                   }
/*      */                   
/*      */ 
/* 2900 */                   out.write("\n</table>\n");
/*      */                   
/* 2902 */                   org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/* 2903 */                   _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2904 */                   _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f1);
/*      */                   
/* 2906 */                   _jspx_th_html_005fform_005f0.setAction("/adminAction.do?method=saveGlobalSettingsConfiguration");
/*      */                   
/* 2908 */                   _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/*      */                   
/* 2910 */                   _jspx_th_html_005fform_005f0.setScope("application");
/* 2911 */                   int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2912 */                   if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                     for (;;) {
/* 2914 */                       out.write(10);
/* 2915 */                       out.write(32);
/*      */                       
/* 2917 */                       org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
/* 2918 */                       _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 2919 */                       _jspx_th_html_005fhidden_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 2921 */                       _jspx_th_html_005fhidden_005f0.setProperty("settings");
/*      */                       
/* 2923 */                       _jspx_th_html_005fhidden_005f0.setValue(type);
/* 2924 */                       int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 2925 */                       if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 2926 */                         this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0); return;
/*      */                       }
/*      */                       
/* 2929 */                       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2930 */                       out.write("\n<input type=\"hidden\" name=\"method\" value=\"saveGlobalSettingsConfiguration\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2931 */                       out.print(request.getParameter("resourceid"));
/* 2932 */                       out.write("\">\n<input type=\"hidden\" name=\"frommondetails\" value=\"");
/* 2933 */                       out.print(request.getParameter("frommondetails"));
/* 2934 */                       out.write("\">\n\n");
/*      */                       
/*      */ 
/* 2937 */                       if (type.equalsIgnoreCase("performance"))
/*      */                       {
/*      */ 
/* 2940 */                         out.write("\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr class=\"tabBtmLine\">\n     <td nowrap=\"nowrap\">\n         <table id=\"InnerTab\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"innertab_btm_space\">\n               <tbody>\n                  <tr>\n                    <td width=\"17\"></td>\n\t\t\t\t\t<td id=\"performancetab\">\n\t\t\t\t\t\t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td class=\"tbSelected_Left\" id=\"performancelink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t\t\t\t\t\t\t<td class=\"tbSelected_Middle\"id=\"performancelink\"><a href=\"javascript:showHide('performance')\">&nbsp;<span  class=\"tabLink\">");
/* 2941 */                         out.print(FormatUtil.getString("am.webclient.performancepolling.collectiontext"));
/* 2942 */                         out.write("</span></a></td>\n\t\t\t\t\t\t\t\t\t<td class=\"tbselected_Right\" id=\"performancelink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td id=\"disktab\">\n\t\t\t\t\t\t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td class=\"tbUnselected_Left\" id=\"diskiolink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t\t\t\t\t\t\t<td class=\"tbUnselected_Middle\"  id=\"diskiolink\"><a href=\"javascript:showHide('diskio')\" >&nbsp;<span  class=\"tabLink\">");
/* 2943 */                         out.print(FormatUtil.getString("Systems"));
/* 2944 */                         out.write("</span></a></td>\n\t\t\t\t\t\t\t\t\t<td class=\"tbUnselected_Right\" id=\"diskiolink-right\"> <img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td id=\"Database Servers\">\n\t\t\t\t\t\t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td class=\"tbUnselected_Left\" id=\"DBlink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t\t\t\t\t\t\t<td class=\"tbUnselected_Middle\"  id=\"DBlink\"><a href=\"javascript:showHide('DataBase')\">&nbsp;<span  class=\"tabLink\">");
/* 2945 */                         out.print(FormatUtil.getString("Database Servers"));
/* 2946 */                         out.write("</span></a></td>\n\t\t\t\t\t\t\t\t\t<td class=\"tbUnselected_Right\" id=\"DBlink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\t\t\t\t\n\t\t\t\t\t<td id=\"configureMSSQLtab\">\n\t\t\t\t\t\t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td class=\"tbUnselected_Left\" id=\"configureMSSQLlink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t\t\t\t\t\t\t<td class=\"tbUnselected_Middle\"  id=\"configureMSSQLlink\"><a href=\"javascript:showHide('configureMSSQL')\">&nbsp;<span  class=\"tabLink\">");
/* 2947 */                         out.print(FormatUtil.getString("am.admin.perfPolling.tab.dc.title.sql"));
/* 2948 */                         out.write("</span></a></td>\n\t\t\t     \t\t    \t\t<td class=\"tbUnselected_Right\" id=\"configureMSSQLlink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t\t     \t\t  \t\t</tr>\n\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t</table>\n                    </td>\n\t\t\t\t\t<td id=\"weblogictab\">\n\t\t\t\t\t\t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t \t\t\t    <td class=\"tbUnselected_Left\" id=\"wlslink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t\t\t \t\t\t    <td class=\"tbUnselected_Middle\"  id=\"wlslink\"><a href=\"javascript:showHide('weblogic')\">&nbsp;<span  class=\"tabLink\">");
/* 2949 */                         out.print(FormatUtil.getString("am.webclient.performancepolling.wlstext"));
/* 2950 */                         out.write("</span></a></td>\n\t\t\t\t\t \t\t\t    <td class=\"tbUnselected_Right\" id=\"wlslink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t\t\t\t \t\t\t</tr>\n\t\t\t\t\t \t\t</tbody>\n \t\t      \t\t\t</table>\n                    </td>\n\t\t\t\t\n                    <td id=\"snmptab\">\n                    \t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    \t\t<tbody>\n                    \t\t\t<tr>\n                    \t\t\t\t<td class=\"tbUnselected_Left\" id=\"snmplink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                    \t\t\t\t<td class=\"tbUnselected_Middle\"  id=\"snmplink\"><a href=\"javascript:showHide('snmp')\">&nbsp;<span  class=\"tabLink\">");
/* 2951 */                         out.print(FormatUtil.getString("SNMP"));
/* 2952 */                         out.write("</span></a></td>\n                    \t\t\t\t<td class=\"tbUnselected_Right\" id=\"snmplink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n                    \t\t\t</tr>\n                    \t\t</tbody>\n                    \t</table>\n                    </td>\n                    <td id=\"urltab\">\n                    \t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    \t\t<tbody>\n                    \t\t\t<tr>\n                    \t\t\t\t<td class=\"tbUnselected_Left\" id=\"urllink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                    \t\t\t\t<td class=\"tbUnselected_Middle\"  id=\"urllink\"><a href=\"javascript:showHide('url-div')\">&nbsp;<span  class=\"tabLink\">");
/* 2953 */                         out.print(FormatUtil.getString("HTTP(s) URLs"));
/* 2954 */                         out.write("</span></a></td>\n                    \t\t\t\t<td class=\"tbUnselected_Right\" id=\"urllink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n                    \t\t\t</tr>\n                    \t\t</tbody>\n                    \t</table>\n                    </td>\n                    <td id=\"amazontab\">\n\t\t    \t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t    \t\t<tbody>\n\t\t    \t\t\t<tr>\n\t\t    \t\t\t\t<td class=\"tbUnselected_Left\" id=\"amazonlink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t     \t\t    \t\t<td class=\"tbUnselected_Middle\"  id=\"amazonlink\"><a href=\"javascript:showHide('amazon')\">&nbsp;<span  class=\"tabLink\">");
/* 2955 */                         out.print(FormatUtil.getString("am.webclient.performancepolling.amazonS3Bucket.heading"));
/* 2956 */                         out.write("</span></a></td>\n\t\t     \t\t    \t\t<td class=\"tbUnselected_Right\" id=\"amazonlink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t     \t\t  \t\t</tr>\n\t\t     \t\t</tbody>\n\t\t     \t </table>\n                    </td>\n                    <td id=\"webservicestab\">\n\t\t\t\t    \t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t    \t\t<tbody>\n\t\t\t\t    \t\t\t<tr>\n\t\t\t\t    \t\t\t\t<td class=\"tbUnselected_Left\" id=\"webserviceslink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t\t     \t\t    \t\t<td class=\"tbUnselected_Middle\"  id=\"webserviceslink\"><a href=\"javascript:showHide('webservices')\">&nbsp;<span  class=\"tabLink\">");
/* 2957 */                         out.print(FormatUtil.getString("Web Services"));
/* 2958 */                         out.write("</span></a></td>\n\t\t\t\t     \t\t    \t\t<td class=\"tbUnselected_Right\" id=\"webserviceslink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t\t\t     \t\t  \t\t</tr>\n\t\t\t\t     \t\t</tbody>\n\t\t\t\t     \t </table>\n                    </td>\n                     <td id=\"pingtab\">\n\t\t\t\t    \t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t    \t\t<tbody>\n\t\t\t\t    \t\t\t<tr>\n\t\t\t\t    \t\t\t\t<td class=\"tbUnselected_Left\" id=\"pinglink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t\t     \t\t    \t\t<td class=\"tbUnselected_Middle\"  id=\"pinglink\"><a href=\"javascript:showHide('ping')\">&nbsp;<span  class=\"tabLink\">");
/* 2959 */                         out.print(FormatUtil.getString("Ping Monitor"));
/* 2960 */                         out.write("</span></a></td>\n\t\t\t\t     \t\t    \t\t<td class=\"tbUnselected_Right\" id=\"pinglink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t\t\t     \t\t  \t\t</tr>\n\t\t\t\t     \t\t</tbody>\n\t\t\t\t     \t </table>\n                    </td>\n\t\t\t\t\t<td id=\"configureDCtab\">\n\t\t\t    \t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t    \t\t<tbody>\n\t\t\t    \t\t\t<tr>\n\t\t\t    \t\t\t\t<td class=\"tbUnselected_Left\" id=\"configureDClink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t     \t\t    \t\t<td class=\"tbUnselected_Middle\"  id=\"configureDClink\"><a href=\"javascript:showHide('configureDC')\">&nbsp;<span  class=\"tabLink\">");
/* 2961 */                         out.print(FormatUtil.getString("am.admin.perfPolling.tab.dc.title"));
/* 2962 */                         out.write("</span></a></td>\n\t\t\t     \t\t    \t\t<td class=\"tbUnselected_Right\" id=\"configureDClink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t\t     \t\t  \t\t</tr>\n\t\t\t     \t\t</tbody>\n\t\t\t     \t </table>\n                    </td>\n\t\t\t\t</tr>\n\t\t\t\t</tbody>\n \t\t</table>\n \t\t</td>\n \t\t</tr>\n \t\t</table>\n");
/*      */                       }
/* 2964 */                       out.write("\n\t<div id=\"mainDiv\" style=\"display:block\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n\t\t\t<div id=\"mainDiv1\" style=\"display:block\">\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  class=\"lrtborder itadmin-hide\">\n ");
/*      */                       
/* 2966 */                       if (!type.equalsIgnoreCase("performance"))
/*      */                       {
/*      */ 
/* 2969 */                         out.write("\n  <tr>\n    <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2970 */                         out.print(heading);
/* 2971 */                         out.write("</td>\n  </tr>\n\n ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2975 */                         out.write("\n\t <tr class=\"display-hide\">\n\t  <td width=\"100%\" colspan=\"2\">\n\t   <div id=\"diskioHeading\" style=\"display:none\">\n\t    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t   <td colspan=\"2\" class=\"tableheadingbborder\"></td>\n\t\t </tr>\n \t    </table>\n\t   </div>\n\t  </td>\n\t </tr>\n\t <tr>\n\t   <td width=\"100%\" colspan=\"2\">\n\t    <div id=\"performanceHeading\" style=\"display:none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t    <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2976 */                         out.print(FormatUtil.getString("am.webclient.performancepolling.datacollectiontext"));
/* 2977 */                         out.write("</td>\n\t\t  </tr>\n\t\t </table>\n\t\t</div>\n\t   </td>\n\t  </tr>\n\t  <tr>\n\t   <td width=\"100%\" colspan=\"2\">\n\t    <div id=\"oracleHeading\" style=\"display:none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t  <tr>\n\t\t   <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2978 */                         out.print(FormatUtil.getString("am.webclient.performancepolling.diskiotext"));
/* 2979 */                         out.write("</td>\n\t\t  </tr>\n\t\t </table>\n\t\t</div>\n\t    <div id=\"weblogicHeading\" style=\"display:none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t   <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2980 */                         out.print(FormatUtil.getString("am.webclient.performancepolling.disablewlstext"));
/* 2981 */                         out.write("</td>\n\t\t  </tr>\n\t\t </table>\n\t\t</div>\n\t   <div id=\"mssqlHeading\" style=\"display:none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t   <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2982 */                         out.print(FormatUtil.getString("am.webclient.performancepolling.diskiotext"));
/* 2983 */                         out.write("</td>\n\t\t  </tr>\n\t\t </table>\n\t\t</div>\n\t\t<div id=\"mysqlHeading\" style=\"display:none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t   <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2984 */                         out.print(FormatUtil.getString("am.webclient.performancepolling.mysql.enabledatacollection"));
/* 2985 */                         out.write("</td>\n\t\t  </tr>\n\t\t </table>\n\t\t</div>\n\t\t<div id=\"snmpHeading\" style=\"display:none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t   <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2986 */                         out.print(FormatUtil.getString("am.webclient.newaction.snmpversion"));
/* 2987 */                         out.write("</td>\n\t\t  </tr>\n\t\t </table>\n\t\t</div>\n\t\t<div id=\"urlHeading\" style=\"display:none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t   <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2988 */                         out.print(FormatUtil.getString("HTTP(s) URLs"));
/* 2989 */                         out.write(32);
/* 2990 */                         out.write(45);
/* 2991 */                         out.write(32);
/* 2992 */                         out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/* 2993 */                         out.write("</td>\n\t\t  </tr>\n\t\t </table>\n\t\t</div>\n\t\t<div id=\"amazonHeading\" style=\"display:none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t   <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2994 */                         out.print(FormatUtil.getString("am.webclient.amazon.perfpolling.settings.header"));
/* 2995 */                         out.write("</td>\n\t\t  </tr>\n\t\t </table>\n\t\t</div>\n\t\t<div id=\"webservicesHeading\" style=\"display:none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t   <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2996 */                         out.print(FormatUtil.getString("am.webclient.webservice.perfpolling.settings"));
/* 2997 */                         out.write("</td>\n\t\t  </tr>\n\t\t </table>\n\t\t</div>\n\t\t<div id=\"pingHeading\" style=\"display:none\">\n\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t \t\t<tr>\n\t\t  \t\t\t<td colspan=\"2\" class=\"tableheadingbborder\">\n\t\t  \t\t\t\t");
/* 2998 */                         out.print(FormatUtil.getString("Ping Monitor"));
/* 2999 */                         out.write(32);
/* 3000 */                         out.write(45);
/* 3001 */                         out.write(32);
/* 3002 */                         out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/* 3003 */                         out.write("\n\t\t  \t\t\t</td>\n\t\t \t\t</tr>\n\t\t \t</table>\n\t\t</div>\n\t   </td>\n\t  </tr>\n ");
/*      */                       }
/* 3005 */                       out.write("\n\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"6\" class=\"lrborder itadmin-no-decor\">\n");
/*      */                       
/* 3007 */                       if ((type != null) && (type.equals("logging"))) {
/* 3008 */                         out.write("\n  <tr >   \n    <td height=\"22\" class=\"bodytext label-align\" width=\"25%\">");
/* 3009 */                         out.print(FormatUtil.getString("am.webclient.global.currentsetting.text"));
/* 3010 */                         out.write(" </td>\n    \n    <td height=\"22\" class=\"bodytext\">");
/*      */                         
/* 3012 */                         SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3013 */                         _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3014 */                         _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3016 */                         _jspx_th_html_005fselect_005f0.setProperty("logConfig");
/*      */                         
/* 3018 */                         _jspx_th_html_005fselect_005f0.setStyleClass("formtext normal");
/* 3019 */                         int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3020 */                         if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3021 */                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3022 */                             out = _jspx_page_context.pushBody();
/* 3023 */                             _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3024 */                             _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3027 */                             out.write(32);
/*      */                             
/* 3029 */                             OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3030 */                             _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3031 */                             _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                             
/* 3033 */                             _jspx_th_html_005foption_005f0.setValue("-1");
/* 3034 */                             int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3035 */                             if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3036 */                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3037 */                                 out = _jspx_page_context.pushBody();
/* 3038 */                                 _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3039 */                                 _jspx_th_html_005foption_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3042 */                                 out.write("\n            ");
/* 3043 */                                 out.print(FormatUtil.getString("am.webclient.global.stoplogging.text"));
/* 3044 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3045 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3048 */                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3049 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3052 */                             if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3053 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                             }
/*      */                             
/* 3056 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3057 */                             out.write(32);
/*      */                             
/* 3059 */                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3060 */                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3061 */                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                             
/* 3063 */                             _jspx_th_html_005foption_005f1.setValue("1");
/* 3064 */                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3065 */                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3066 */                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3067 */                                 out = _jspx_page_context.pushBody();
/* 3068 */                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3069 */                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3072 */                                 out.print(FormatUtil.getString("am.webclient.global.fatalerror.text"));
/* 3073 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3074 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3077 */                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3078 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3081 */                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3082 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                             }
/*      */                             
/* 3085 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3086 */                             out.write(32);
/*      */                             
/* 3088 */                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3089 */                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3090 */                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                             
/* 3092 */                             _jspx_th_html_005foption_005f2.setValue("2");
/* 3093 */                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3094 */                             if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3095 */                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3096 */                                 out = _jspx_page_context.pushBody();
/* 3097 */                                 _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3098 */                                 _jspx_th_html_005foption_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3101 */                                 out.write("\n            ");
/* 3102 */                                 out.print(FormatUtil.getString("am.webclient.global.warningerror.text"));
/* 3103 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3104 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3107 */                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3108 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3111 */                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3112 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                             }
/*      */                             
/* 3115 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3116 */                             out.write(32);
/*      */                             
/* 3118 */                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3119 */                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3120 */                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                             
/* 3122 */                             _jspx_th_html_005foption_005f3.setValue("3");
/* 3123 */                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3124 */                             if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3125 */                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3126 */                                 out = _jspx_page_context.pushBody();
/* 3127 */                                 _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3128 */                                 _jspx_th_html_005foption_005f3.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3131 */                                 out.print(FormatUtil.getString("am.webclient.global.debugerror.text"));
/* 3132 */                                 out.write("\n            ");
/* 3133 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3134 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3137 */                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3138 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3141 */                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3142 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                             }
/*      */                             
/* 3145 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/*      */                             
/* 3147 */                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3148 */                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3149 */                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                             
/* 3151 */                             _jspx_th_html_005foption_005f4.setValue("4");
/* 3152 */                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3153 */                             if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3154 */                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3155 */                                 out = _jspx_page_context.pushBody();
/* 3156 */                                 _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3157 */                                 _jspx_th_html_005foption_005f4.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3160 */                                 out.print(FormatUtil.getString("am.webclient.global.alllogs.text"));
/* 3161 */                                 out.write("\n            ");
/* 3162 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3163 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3166 */                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3167 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3170 */                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3171 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                             }
/*      */                             
/* 3174 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3175 */                             out.write(32);
/* 3176 */                             int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3177 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3180 */                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3181 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3184 */                         if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3185 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                         }
/*      */                         
/* 3188 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 3189 */                         out.write("</td>\n  </tr>\n\n");
/* 3190 */                       } else if ((type != null) && (type.equals("jsonfeed"))) {
/* 3191 */                         out.write("\n  <tr>\n    <td colspan=\"2\" class=\"message\">\n");
/* 3192 */                         out.print(FormatUtil.getString("am.webclient.json.msg1", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/* 3193 */                         out.write("\n</td>\n</tr>\n<tr>\n<td colspan=\"2\" class=\"message\">\n<li>");
/* 3194 */                         out.print(FormatUtil.getString("am.webclient.json.info"));
/* 3195 */                         out.write("\n</td>\n</tr>\n<tr>\n<td colspan=\"2\" class=\"message\">\n<li>");
/* 3196 */                         out.print(FormatUtil.getString("am.webclient.json.example", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.homedirectory.name") }));
/* 3197 */                         out.write("\n</td>\n</tr>\n<tr>\n<td colspan=\"2\" class=\"message\">\n<li>");
/* 3198 */                         out.print(FormatUtil.getString("am.webclient.json.changes"));
/* 3199 */                         out.write("\n</td>\n</tr>\n<tr>\n<td colspan=\"2\" class=\"message bborder itadmin-no-decor\">\n<li>");
/* 3200 */                         out.print(FormatUtil.getString("am.webclient.jsonsample.txt"));
/* 3201 */                         out.write("\n</td>\n</tr>\n\n\n</td>\n  </tr>\n\n\n\n");
/*      */ 
/*      */ 
/*      */                       }
/* 3205 */                       else if ((type != null) && (type.equals("general")))
/*      */                       {
/* 3207 */                         out.write("\n    <tr >\n    <td class=\"bodytextbold\" colspan=\"2\">");
/* 3208 */                         out.print(FormatUtil.getString("am.webclient.global.general.text"));
/* 3209 */                         out.write("</td>\n  </tr>\n  ");
/* 3210 */                         if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 3211 */                           out.write("\n  <tr >\n    <td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n    <td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3212 */                           if (_jspx_meth_html_005fmultibox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 3214 */                           out.write(32);
/* 3215 */                           out.print(FormatUtil.getString("am.webclient.global.intro.text"));
/* 3216 */                           out.write("</td>\n  </tr>\n  <tr >\n    <td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n    <td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3217 */                           if (_jspx_meth_html_005fmultibox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 3219 */                           out.write(32);
/* 3220 */                           out.print(FormatUtil.getString("am.webclient.global.logintop.text"));
/* 3221 */                           out.write("</td>\n  </tr>\n  <tr >\n    <td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n    <td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3222 */                           if (_jspx_meth_html_005fmultibox_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 3224 */                           out.write(32);
/* 3225 */                           out.print(FormatUtil.getString("am.webclient.global.loginslider.text"));
/* 3226 */                           out.write("</td>\n  </tr>\n  <tr >\n    <td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n    <td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3227 */                           if (_jspx_meth_html_005fmultibox_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 3229 */                           out.write(32);
/* 3230 */                           out.print(FormatUtil.getString("am.webclient.global.loginfeatures.text"));
/* 3231 */                           out.write("</td>\n  </tr>\n  ");
/*      */                         }
/* 3233 */                         if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 3234 */                           out.write("\n  <tr >\n  \t\t<td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n        <td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3235 */                           if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 3237 */                           out.write(32);
/* 3238 */                           out.print(FormatUtil.getString("am.webclient.global.nologoutpagetoexternalwebsite.text"));
/* 3239 */                           out.write("</td>");
/* 3240 */                           out.write("\n        \n    </tr>\n  ");
/*      */                         }
/* 3242 */                         out.write(10);
/* 3243 */                         out.write(32);
/* 3244 */                         out.write(32);
/*      */                         
/* 3246 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3247 */                         _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3248 */                         _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3250 */                         _jspx_th_logic_005fnotPresent_005f2.setRole("ENTERPRISEADMIN");
/* 3251 */                         int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3252 */                         if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                           for (;;) {
/* 3254 */                             out.write("\n  <tr >\n  \t<td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n      <td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3255 */                             if (_jspx_meth_html_005fmultibox_005f4(_jspx_th_logic_005fnotPresent_005f2, _jspx_page_context))
/*      */                               return;
/* 3257 */                             out.write(32);
/* 3258 */                             out.print(FormatUtil.getString("am.webclient.global.advancedalert.text"));
/* 3259 */                             out.write("</td>\n  </tr>\n");
/* 3260 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3261 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3265 */                         if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3266 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                         }
/*      */                         
/* 3269 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3270 */                         out.write("\n\n  <tr >\n  \t<td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n    <td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3271 */                         if (_jspx_meth_html_005fmultibox_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3273 */                         out.write(32);
/* 3274 */                         out.print(FormatUtil.getString("am.webclient.global.addhost.text"));
/* 3275 */                         out.write("</td>\n  </tr>\n ");
/*      */                         
/* 3277 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3278 */                         _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3279 */                         _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3281 */                         _jspx_th_logic_005fnotPresent_005f3.setRole("ENTERPRISEADMIN");
/* 3282 */                         int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3283 */                         if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                           for (;;) {
/* 3285 */                             out.write("\n\n  <tr >\n  \t<td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n\t<td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3286 */                             if (_jspx_meth_html_005fmultibox_005f6(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                               return;
/* 3288 */                             out.write(32);
/* 3289 */                             out.print(FormatUtil.getString("am.webclient.global.discoverhost.text"));
/* 3290 */                             out.write("</td>\n  </tr>\n  ");
/* 3291 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3292 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3296 */                         if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3297 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                         }
/*      */                         
/* 3300 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3301 */                         out.write("\n  <tr >\n\t<td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n    <td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3302 */                         if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3304 */                         out.write(32);
/* 3305 */                         out.print(FormatUtil.getString("am.webclient.global.restart.text"));
/* 3306 */                         out.write("</td>\n  </tr> \n  <tr >\n      <td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n    <td width=\"98%\" height=\"22\" class=\"bodytext\">\n      \t");
/*      */                         
/* 3308 */                         CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fdisabled_005fnobody.get(CheckboxTag.class);
/* 3309 */                         _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 3310 */                         _jspx_th_html_005fcheckbox_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3312 */                         _jspx_th_html_005fcheckbox_005f2.setProperty("presales_emails");
/*      */                         
/* 3314 */                         _jspx_th_html_005fcheckbox_005f2.setValue("true");
/*      */                         
/* 3316 */                         _jspx_th_html_005fcheckbox_005f2.setDisabled(com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().equals("F"));
/*      */                         
/* 3318 */                         _jspx_th_html_005fcheckbox_005f2.setStyle("position:relative;");
/* 3319 */                         int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 3320 */                         if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 3321 */                           this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2); return;
/*      */                         }
/*      */                         
/* 3324 */                         this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 3325 */                         out.write(" \n      \t");
/*      */                         
/* 3327 */                         if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                         {
/* 3329 */                           out.write("\n\t\t\t");
/* 3330 */                           out.print(FormatUtil.getString("it360.globalsettings"));
/* 3331 */                           out.write("\n\t\t\t");
/*      */                         }
/*      */                         else {
/* 3334 */                           out.write(10);
/* 3335 */                           out.write(9);
/* 3336 */                           out.write(9);
/* 3337 */                           out.print(FormatUtil.getString("am.product.enable.presales.txt"));
/*      */                         }
/* 3339 */                         out.write("</td>\n  </tr>\n   ");
/*      */                         
/* 3341 */                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3342 */                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3343 */                         _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3345 */                         _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,ENTERPRISEADMIN");
/* 3346 */                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3347 */                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                           for (;;) {
/* 3349 */                             out.write(10);
/* 3350 */                             out.write(32);
/* 3351 */                             out.write(32);
/*      */                             
/* 3353 */                             if (request.getUserPrincipal().getName().equalsIgnoreCase("admin"))
/*      */                             {
/* 3355 */                               out.write("\n  <tr >\n      <td align=\"right\" class=\"bodytext\"></td>\n      <td width=\"80%\" height=\"22\" class=\"bodytext\">\n      \t");
/* 3356 */                               if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                                 return;
/* 3358 */                               out.write(" \n      \t");
/* 3359 */                               out.print(FormatUtil.getString("am.webclient.adminserver.enable.salesforce.text"));
/* 3360 */                               out.write("\n     </td>\n  </tr>\n  ");
/*      */                             }
/*      */                             
/*      */ 
/* 3364 */                             out.write(10);
/* 3365 */                             out.write(32);
/* 3366 */                             out.write(32);
/* 3367 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3368 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3372 */                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3373 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                         }
/*      */                         
/* 3376 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3377 */                         out.write("  \n  ");
/*      */                         
/* 3379 */                         PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3380 */                         _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3381 */                         _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3383 */                         _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,ENTERPRISEADMIN");
/* 3384 */                         int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3385 */                         if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                           for (;;) {
/* 3387 */                             out.write("\n  \t");
/*      */                             
/* 3389 */                             if (request.getUserPrincipal().getName().equalsIgnoreCase("admin"))
/*      */                             {
/*      */ 
/* 3392 */                               out.write("\n\t\t  <tr >\n      \t<td  width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n      <td width=\"98%\" height=\"22\" class=\"bodytext\">\n      \t");
/* 3393 */                               if (_jspx_meth_html_005fcheckbox_005f4(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                                 return;
/* 3395 */                               out.print(FormatUtil.getString("am.webclient.adminserver.easyupgrade.text"));
/* 3396 */                               out.write("\n     </td>\n  </tr>\n  ");
/*      */                             }
/*      */                             
/*      */ 
/* 3400 */                             out.write(10);
/* 3401 */                             out.write(9);
/* 3402 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3403 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3407 */                         if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3408 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                         }
/*      */                         
/* 3411 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3412 */                         out.write(10);
/* 3413 */                         out.write(9);
/*      */                         
/* 3415 */                         PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3416 */                         _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3417 */                         _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3419 */                         _jspx_th_logic_005fpresent_005f4.setRole("ADMIN,ENTERPRISEADMIN");
/* 3420 */                         int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3421 */                         if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                           for (;;) {
/* 3423 */                             out.write("\n\t\t<tr >\n      \t\t<td  width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n      \t\t<td width=\"98%\" height=\"22\" class=\"bodytext\">\n      \t\t\t");
/* 3424 */                             if (_jspx_meth_html_005fcheckbox_005f5(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                               return;
/* 3426 */                             out.print(FormatUtil.getString("am.webclient.global.self.monitoring.text"));
/* 3427 */                             out.write("\n     \t\t</td>\n  \t\t</tr>\n\t");
/* 3428 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3429 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3433 */                         if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3434 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                         }
/*      */                         
/* 3437 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3438 */                         out.write("\n  \n\t<tr>\n\t\t<td width=\"2%\" align=\"right\" class=\"bodytext\"></td>\n    \t<td width=\"98%\" height=\"22\" class=\"bodytext\">\n      \t");
/*      */                         
/* 3440 */                         DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3441 */                         _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 3442 */                         _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3444 */                         _jspx_th_bean_005fdefine_005f0.setId("displaynamelength");
/*      */                         
/* 3446 */                         _jspx_th_bean_005fdefine_005f0.setName("AMActionForm");
/*      */                         
/* 3448 */                         _jspx_th_bean_005fdefine_005f0.setProperty("displaynamelength");
/*      */                         
/* 3450 */                         _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 3451 */                         int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 3452 */                         if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 3453 */                           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */                         }
/*      */                         
/* 3456 */                         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 3457 */                         String displaynamelength = null;
/* 3458 */                         displaynamelength = (String)_jspx_page_context.findAttribute("displaynamelength");
/* 3459 */                         out.write("\n      \t");
/* 3460 */                         out.print(FormatUtil.getString("webclient.globalsettings.displaylength", new String[] { "<input type=text name=displaynamelength maxlength=2 size=1 class=formtext value=" + displaynamelength + ">" }));
/* 3461 */                         out.write("\n  \t</tr>\n\t\n\n<!-- <tr >\n  <td align=\"right\" class=\"bodytext\"><//html:checkbox property=\"monthlyday\" value=\"true\" onclick=\"checkFn()\"/></td>\n  <td height=\"22\" class=\"bodytext\">");
/*      */                         
/* 3463 */                         out.write("</td>\n  </tr>\n\n   <tr >\n    <td align=\"right\" class=\"bodytext\">&nbsp;</td>\n   <td height=\"22\" class=\"bodytext\"> <div id='showreports' style='display:block;'>");
/*      */                         
/* 3465 */                         out.write(" <//html:select property=\"selectday\" styleClass=\"formtext-normal\"> <//html:option value=\"7\">\n            7<///html:option> <//html:option value=\"6\">6<///html:option> <//html:option value=\"5\">5<///html:option> <//html:option value=\"4\">4 <///html:option> <//html:option value=\"3\">3 <///html:option> <//html:option value=\"2\">2 <///html:option> <//html:option value=\"1\">1 <///html:option> <///html:select>\n    ");
/*      */                         
/* 3467 */                         out.write(" </div></td>\n  </tr>-->\n\n\n  ");
/*      */                         
/* 3469 */                         if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 3470 */                           if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) || ((com.adventnet.appmanager.util.EnterpriseUtil.getServerType().equals(com.adventnet.appmanager.util.EnterpriseUtil.NORMAL_SERVER)) && (!com.adventnet.appmanager.util.EnterpriseUtil.isUpgradeFromVersion6()) && (!com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().equals("F")) && (!com.adventnet.appmanager.util.OEMUtil.isOEM())))
/*      */                           {
/* 3472 */                             out.write("\n\t\t <tr >\n\t\t <td align=\"left\" colspan=\"2\" class=\"bodytextbold padd_tb_10px\">");
/* 3473 */                             out.print(FormatUtil.getString("am.webclient.global.enterprise.text"));
/* 3474 */                             out.write("\n\t\t </td>\n\t\t </tr>\n\t\t ");
/*      */                             
/* 3476 */                             PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3477 */                             _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3478 */                             _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 3480 */                             _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/* 3481 */                             int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3482 */                             if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                               for (;;) {
/* 3484 */                                 out.write("\n\t\t <tr >\n         <td width=\"2%\" class=\"bodytext\"><img src=\"/images/spacer.gif\" height='8' width='1'></td>\n\t\t <td width=\"98%\" height=\"22\" class=\"bodytext\">");
/* 3485 */                                 if (_jspx_meth_html_005fcheckbox_005f6(_jspx_th_logic_005fpresent_005f5, _jspx_page_context))
/*      */                                   return;
/* 3487 */                                 out.write("&nbsp;");
/* 3488 */                                 out.print(FormatUtil.getString("am.webclient.adminserver.managedserver.proxy.text"));
/* 3489 */                                 out.write("</td>\n\t\t </tr>\n\t\t ");
/* 3490 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3491 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3495 */                             if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3496 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                             }
/*      */                             
/* 3499 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3500 */                             out.write("\n\n\n\t ");
/*      */                           }
/* 3502 */                           if ((com.adventnet.appmanager.util.EnterpriseUtil.getServerType().equals(com.adventnet.appmanager.util.EnterpriseUtil.NORMAL_SERVER)) && (!com.adventnet.appmanager.util.EnterpriseUtil.isUpgradeFromVersion6()) && (!com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().equals("F")) && (!com.adventnet.appmanager.util.OEMUtil.isOEM()))
/*      */                           {
/*      */ 
/*      */ 
/* 3506 */                             out.write("\n    <tr >\n    \t<td width=\"2%\" align=\"right\" class=\"bodytext\">&nbsp;</td>\n        <td width=\"98%\" colspan=\"2\" class=\"bodytext\">");
/* 3507 */                             out.print((!com.adventnet.appmanager.util.EnterpriseUtil.isUpgradeFromVersion6()) && (com.adventnet.appmanager.util.EnterpriseUtil.getServerType().equals(com.adventnet.appmanager.util.EnterpriseUtil.NORMAL_SERVER)) ? "<a href='javascript:void(0)'onClick=\"MM_openBrWindow('/jsp/AdminServerDetails.jsp','AlertSummary','width=700,height=280,top=250,left=275, scrollbars=yes,resizable=yes');\" class=\"staticlinks\">" + FormatUtil.getString("am.webclient.managedserver.changetype.link.text") + "</a>" : "");
/* 3508 */                             out.write("</td>\n\n    </tr>\n    ");
/*      */                           }
/*      */                           
/*      */ 
/* 3512 */                           out.write("\n    ");
/*      */                           
/* 3514 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3515 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3516 */                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 3518 */                           _jspx_th_c_005fif_005f2.setTest("${AMActionForm.showUsageStatistics == true }");
/* 3519 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3520 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/* 3522 */                               out.write("\n    <tr><td height=\"5\"></td></tr>\n    <tr><td align=\"left\" colspan=\"2\" class=\"bodytextbold padd_tb_10px\">");
/* 3523 */                               out.print(FormatUtil.getString("am.webclient.global.usagestatistics.text"));
/* 3524 */                               out.write("</td></tr>\n    <tr>\n    <td width=\"2%\" align=\"right\" class=\"bodytext\">&nbsp;</td>\n    <td width=\"98%\" class=\"bodytext\">");
/* 3525 */                               if (_jspx_meth_html_005fradio_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                 return;
/* 3527 */                               out.print(FormatUtil.getString("Enabled"));
/* 3528 */                               out.write("&nbsp;");
/* 3529 */                               if (_jspx_meth_html_005fradio_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                 return;
/* 3531 */                               out.print(FormatUtil.getString("Disabled"));
/* 3532 */                               out.write("</td>\n    </tr>\n    <tr>\n    <td width=\"2%\" align=\"right\" class=\"bodytext\">&nbsp;</td>\n    <td width=\"98%\" class=\"bodytext\">");
/* 3533 */                               out.print(FormatUtil.getString("am.webclient.global.usagestatistics.description.text"));
/* 3534 */                               out.write("</td>\n    </tr>\n    ");
/* 3535 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3536 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3540 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3541 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/* 3544 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3545 */                           out.write(10);
/* 3546 */                           out.write(32);
/* 3547 */                           out.write(32);
/*      */                         }
/*      */                         
/*      */                       }
/* 3551 */                       else if (type.equals("Actionalert"))
/*      */                       {
/* 3553 */                         out.write("\n\t<tr>\n\t\t<td style=\"padding-left:5px;\">\n\t\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"5\" width=\"100%\">\n\t\t\t\t<tr>\n   \t\t\t\t\t<td align=\"left\" class=\"bodytextbold label-align\" width=\"25%\">");
/* 3554 */                         out.print(FormatUtil.getString("webclient.fault.eventdetails.general.image.message"));
/* 3555 */                         out.write("</td>\n     \t\t\t\t\t<td align=\"right\" width=\"10px\">");
/* 3556 */                         if (_jspx_meth_html_005fmultibox_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3558 */                         out.write("</td>\n     \t\t\t\t\t<td height=\"22\" width=\"70%\" class=\"bodytext\">");
/* 3559 */                         out.print(FormatUtil.getString("am.webclient.global.enableaction.text"));
/* 3560 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 3561 */                         if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin()) {
/* 3562 */                           out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"left\" class=\"bodytextbold label-align\" width=\"25%\">&nbsp;</td>\n \t\t\t\t\t<td align=\"right\" width=\"10px\">");
/* 3563 */                           if (_jspx_meth_html_005fmultibox_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 3565 */                           out.write("</td>\n \t\t\t\t\t<td height=\"22\" width=\"70%\" class=\"bodytext\">");
/* 3566 */                           out.print(FormatUtil.getString("am.webclient.plugin.enableaction.text"));
/* 3567 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                         }
/* 3569 */                         out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\">\n\t\t\t\t\t\t<span class=\"hr-divider\">&nbsp;</span>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"bodytextbold\" align=\"right\">");
/* 3570 */                         out.print(FormatUtil.getString("am.webclient.global.executeRepeatAction.text"));
/* 3571 */                         out.write("</td>\n\t\t\t\t\t<td align=\"right\" colspan=\"2\">&nbsp;</td>\n\t\t\t\t</tr>\n   \t\t\t\t<tr >\n   \t\t\t\t\t<td align=\"left\" width=\"25%\" class=\"bodytext label-align\">");
/* 3572 */                         out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 3573 */                         out.write("</td>\n     \t\t\t\t\t<td align=\"right\" width=\"10px\">");
/* 3574 */                         if (_jspx_meth_html_005fmultibox_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3576 */                         out.write("</td>\n     \t\t\t\t\t<td height=\"22\" width=\"70%\" class=\"bodytext\">");
/* 3577 */                         out.print(FormatUtil.getString("am.webclient.global.repeatavailablity.text"));
/* 3578 */                         out.write("</td>\n   \t\t\t\t</tr>\n   \t\t\t\t<tr>\n   \t\t\t\t\t<td align=\"left\" width=\"25%\" class=\"bodytext label-align align-top\">");
/* 3579 */                         out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3580 */                         out.write("</td>\n     \t\t\t\t\t<td align=\"right\" valign=\"top\" width=\"10px\">");
/* 3581 */                         if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3583 */                         out.write("</td>\n     \t\t\t\t\t<td height=\"22\" width=\"70%\" class=\"bodytext\">");
/* 3584 */                         out.print(FormatUtil.getString("am.webclient.global.repeatHealth.action.text"));
/* 3585 */                         out.write("&nbsp;</td>\n   \t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"right\" colspan=\"2\">&nbsp;</td>\n\t\t\t\t\t<td height=\"22\" class=\"bodytext\">");
/* 3586 */                         out.print(FormatUtil.getString("am.webclient.global.restricActions.text", new String[] { "" }));
/* 3587 */                         out.write("&nbsp;");
/* 3588 */                         if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3590 */                         out.write(40);
/* 3591 */                         out.print(FormatUtil.getString("am.webclient.global.unrestricted.text"));
/* 3592 */                         out.write(")</td>\n\t\t\t\t</tr>\n   \t\t\t\t<tr>\n\t   \t\t\t\t<td align=\"right\" colspan=\"2\" valign=\"top\">");
/* 3593 */                         if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3595 */                         out.write("</td>\n\t   \t\t\t\t<td height=\"22\" class=\"bodytext\">");
/* 3596 */                         out.print(FormatUtil.getString("am.webclient.global.repeatHealthActionsUntilPicked.text"));
/* 3597 */                         out.write("</td>\n   \t\t\t\t</tr>\n   \t\t\t\t<tr>\n   \t\t\t\t\t<td align=\"left\" class=\"bodytext label-align\">");
/* 3598 */                         out.print(FormatUtil.getString("am.webclient.common.attributeLevel.text"));
/* 3599 */                         out.write("</td>\n     \t\t\t\t\t<td align=\"right\">");
/* 3600 */                         if (_jspx_meth_html_005fmultibox_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3602 */                         out.write("</td>\n     \t\t\t\t\t<td height=\"22\" class=\"bodytext\">");
/* 3603 */                         out.print(FormatUtil.getString("am.webclient.global.repeatattribute.text"));
/* 3604 */                         out.write("</td>\n   \t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t</tr>\n    \t\t\t\t<tr>\n    \t\t\t\t\t<td align=\"left\" class=\"bodytextbold label-align\">");
/* 3605 */                         out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.email.text"));
/* 3606 */                         out.write("</td>\n     \t\t\t\t\t<td align=\"right\">");
/* 3607 */                         if (_jspx_meth_html_005fmultibox_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3609 */                         out.write("</td>\n     \t\t\t\t\t<td height=\"22\" class=\"bodytext\">");
/* 3610 */                         out.print(FormatUtil.getString("am.webclient.global.actionalert.emailmessage.text"));
/* 3611 */                         out.write("</td>\n\t\t\t\t<tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"right\" colspan=\"2\">&nbsp;</td>\n\t\t\t\t\t<td height=\"22\" class=\"bodytext\">");
/* 3612 */                         out.print(FormatUtil.getString("am.webclient.global.transferEncoding.text"));
/* 3613 */                         out.write("\n\t\t\t\t\t\t");
/*      */                         
/* 3615 */                         SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3616 */                         _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3617 */                         _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3619 */                         _jspx_th_html_005fselect_005f1.setProperty("transferEncoding");
/*      */                         
/* 3621 */                         _jspx_th_html_005fselect_005f1.setStyleClass("formtext medium");
/* 3622 */                         int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3623 */                         if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3624 */                           if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3625 */                             out = _jspx_page_context.pushBody();
/* 3626 */                             _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 3627 */                             _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3630 */                             out.write("\n\t\t\t\t\t\t\t");
/*      */                             
/* 3632 */                             OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3633 */                             _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3634 */                             _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f1);
/*      */                             
/* 3636 */                             _jspx_th_html_005foption_005f5.setValue("base64");
/* 3637 */                             int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3638 */                             if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3639 */                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3640 */                                 out = _jspx_page_context.pushBody();
/* 3641 */                                 _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3642 */                                 _jspx_th_html_005foption_005f5.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3645 */                                 out.write(32);
/* 3646 */                                 out.print(FormatUtil.getString("base64"));
/* 3647 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3648 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3651 */                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3652 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3655 */                             if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3656 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                             }
/*      */                             
/* 3659 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3660 */                             out.write("\n\t\t\t\t\t\t\t");
/*      */                             
/* 3662 */                             OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3663 */                             _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3664 */                             _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f1);
/*      */                             
/* 3666 */                             _jspx_th_html_005foption_005f6.setValue("quoted-printable");
/* 3667 */                             int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3668 */                             if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3669 */                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3670 */                                 out = _jspx_page_context.pushBody();
/* 3671 */                                 _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3672 */                                 _jspx_th_html_005foption_005f6.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3675 */                                 out.print(FormatUtil.getString("quoted-printable"));
/* 3676 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3677 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3680 */                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3681 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3684 */                             if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3685 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                             }
/*      */                             
/* 3688 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3689 */                             out.write("\n\t\t\t\t\t\t\t");
/*      */                             
/* 3691 */                             OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3692 */                             _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3693 */                             _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f1);
/*      */                             
/* 3695 */                             _jspx_th_html_005foption_005f7.setValue("7bit");
/* 3696 */                             int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3697 */                             if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3698 */                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3699 */                                 out = _jspx_page_context.pushBody();
/* 3700 */                                 _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3701 */                                 _jspx_th_html_005foption_005f7.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3704 */                                 out.write(32);
/* 3705 */                                 out.print(FormatUtil.getString("7bit"));
/* 3706 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3707 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3710 */                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3711 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3714 */                             if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3715 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                             }
/*      */                             
/* 3718 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3719 */                             out.write("\n\t\t\t\t\t\t\t");
/*      */                             
/* 3721 */                             OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3722 */                             _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3723 */                             _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f1);
/*      */                             
/* 3725 */                             _jspx_th_html_005foption_005f8.setValue("8bit");
/* 3726 */                             int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3727 */                             if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3728 */                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3729 */                                 out = _jspx_page_context.pushBody();
/* 3730 */                                 _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3731 */                                 _jspx_th_html_005foption_005f8.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3734 */                                 out.print(FormatUtil.getString("8bit"));
/* 3735 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3736 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3739 */                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3740 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3743 */                             if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3744 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                             }
/*      */                             
/* 3747 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3748 */                             out.write("\n\t\t\t\t\t\t\t");
/*      */                             
/* 3750 */                             OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3751 */                             _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3752 */                             _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f1);
/*      */                             
/* 3754 */                             _jspx_th_html_005foption_005f9.setValue("binary");
/* 3755 */                             int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3756 */                             if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3757 */                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3758 */                                 out = _jspx_page_context.pushBody();
/* 3759 */                                 _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3760 */                                 _jspx_th_html_005foption_005f9.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3763 */                                 out.print(FormatUtil.getString("binary"));
/* 3764 */                                 out.write(32);
/* 3765 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3766 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3769 */                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3770 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3773 */                             if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3774 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                             }
/*      */                             
/* 3777 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3778 */                             out.write("\n\t\t\t\t\t\t");
/* 3779 */                             int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3780 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3783 */                           if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3784 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3787 */                         if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3788 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                         }
/*      */                         
/* 3791 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 3792 */                         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"right\" colspan=\"2\">");
/* 3793 */                         if (_jspx_meth_html_005fmultibox_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3795 */                         out.write("</td>\n\t\t\t\t\t<td height=\"22\" class=\"bodytext\">");
/* 3796 */                         out.print(FormatUtil.getString("am.webclient.global.actionalert.multiplemailassinglemail.text"));
/* 3797 */                         out.write("</td>\n\t\t\t\t<tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t</tr>\n \t\t\t\t<tr>\n \t\t\t\t\t<td align=\"left\" class=\"bodytextbold label-align\">");
/* 3798 */                         out.print(FormatUtil.getString("Monitor Error Mail"));
/* 3799 */                         out.write("</td>\n \t\t\t\t\t<td align=\"right\">");
/* 3800 */                         if (_jspx_meth_html_005fmultibox_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3802 */                         out.write("</td>\n \t\t\t\t\t<td height=\"22\" class=\"bodytext\">");
/* 3803 */                         out.print(FormatUtil.getString("am.webclient.sendmonerror.enable"));
/* 3804 */                         out.write("</td>\n \t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"right\" colspan=\"2\">&nbsp;</td>\n\t\t\t\t\t<td height=\"22\" class=\"bodytext\">");
/* 3805 */                         out.print(FormatUtil.getString("am.webclient.sendmonerror.checkfor.text", new String[] { "" }));
/* 3806 */                         out.write("&nbsp;");
/* 3807 */                         if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3809 */                         out.write(32);
/* 3810 */                         out.print(FormatUtil.getString("am.webclient.sendmonerror.pollcount"));
/* 3811 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"left\" class=\"bodytextbold label-align\" width=\"25%\">");
/* 3812 */                         out.print(FormatUtil.getString("am.webclient.globalsettings.emaildateformat.text"));
/* 3813 */                         out.write("</td>\n     \t\t\t\t\t<td align=\"right\" class=\"bodytextbold\" width=\"10px\">");
/* 3814 */                         if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3816 */                         out.write("</td>\n\t\t\t\t\t<td class=\"bodytext\" width=\"70%\">");
/* 3817 */                         out.print(FormatUtil.getString("am.webclient.globalsettings.defaultdate"));
/* 3818 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n     \t\t\t\t\t<td align=\"right\" class=\"bodytextbold\" colspan=\"2\">");
/* 3819 */                         if (_jspx_meth_html_005fradio_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3821 */                         out.write("</td>\n\t\t\t\t\t<td class=\"bodytext\">");
/* 3822 */                         out.print(FormatUtil.getString("am.webclient.globalsettings.customdate"));
/* 3823 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t</tr>\n   \t\t\t\t<tr>\n     \t\t\t\t\t<td align=\"left\" class=\"bodytextbold label-align\">");
/* 3824 */                         out.print(FormatUtil.getString("am.webclient.global.actionalert.sms.text"));
/* 3825 */                         out.write("</td>\n     \t\t\t\t\t<td align=\"right\"> ");
/* 3826 */                         if (_jspx_meth_html_005fmultibox_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3828 */                         out.write("</td>\n     \t\t\t\t\t<td height=\"22\" class=\"bodytext\">");
/* 3829 */                         out.print(FormatUtil.getString("am.webclient.global.smsinfo.text"));
/* 3830 */                         out.write("</td>\n   \t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t</tr>\n   \t\t\t\t<tr>\n   \t\t\t\t\t<td align=\"right\" class=\"bodytextbold label-align\">");
/* 3831 */                         out.print(FormatUtil.getString("am.webclient.global.retry.header.text"));
/* 3832 */                         out.write("</td>\n     \t\t\t\t<td align=\"left\" class=\"bodytext\" style=\"padding-left: 25px;\" colspan=\"2\"><span class=\"fl\">");
/* 3833 */                         out.print(FormatUtil.getString("am.webclient.common.check.text"));
/* 3834 */                         out.write("&nbsp;&nbsp;");
/* 3835 */                         if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3837 */                         out.write("&nbsp;");
/* 3838 */                         out.print(FormatUtil.getString("am.webclient.threshold.out.text"));
/* 3839 */                         out.write("&nbsp;");
/* 3840 */                         out.print(FormatUtil.getString("of"));
/* 3841 */                         out.write("&nbsp;&nbsp;");
/* 3842 */                         if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3844 */                         out.write("&nbsp;</span>");
/* 3845 */                         out.print(FormatUtil.getString("am.webclient.global.cricticalpollcount.text"));
/* 3846 */                         out.write("</td>\n   \t\t\t\t</tr>\n   \t\t\t\t<tr>\n\t\t\t\t\t<td align=\"right\" class=\"bodytextbold label-align\">&nbsp;</td>\n  \t\t\t\t\t<td align=\"left\" class=\"bodytext\" style=\"padding-left: 25px;\" colspan=\"2\"><span class=\"fl\">");
/* 3847 */                         out.print(FormatUtil.getString("am.webclient.common.check.text"));
/* 3848 */                         out.write("&nbsp;&nbsp;");
/* 3849 */                         if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3851 */                         out.write("&nbsp;");
/* 3852 */                         out.print(FormatUtil.getString("am.webclient.threshold.out.text"));
/* 3853 */                         out.write("&nbsp;");
/* 3854 */                         out.print(FormatUtil.getString("of"));
/* 3855 */                         out.write("&nbsp;&nbsp;");
/* 3856 */                         if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3858 */                         out.write("&nbsp;</span>");
/* 3859 */                         out.print(FormatUtil.getString("am.webclient.global.warningpollcount.text"));
/* 3860 */                         out.write("</td>\n   \t\t\t\t</tr>\n   \t\t\t\t<tr>\n   \t\t\t\t\t<td align=\"right\" class=\"bodytextbold label-align\">&nbsp;</td>\n     \t\t\t\t<td align=\"left\" class=\"bodytext\" style=\"padding-left: 25px;\" colspan=\"2\"><span class=\"fl\">");
/* 3861 */                         out.print(FormatUtil.getString("am.webclient.common.check.text"));
/* 3862 */                         out.write("&nbsp;&nbsp;");
/* 3863 */                         if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3865 */                         out.write("&nbsp;");
/* 3866 */                         out.print(FormatUtil.getString("am.webclient.threshold.out.text"));
/* 3867 */                         out.write("&nbsp;");
/* 3868 */                         out.print(FormatUtil.getString("of"));
/* 3869 */                         out.write("&nbsp;&nbsp;");
/* 3870 */                         if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3872 */                         out.write("&nbsp;</span>");
/* 3873 */                         out.print(FormatUtil.getString("am.webclient.global.clearpollcount.text"));
/* 3874 */                         out.write("</td>\n  \t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n  ");
/*      */                       }
/* 3876 */                       else if (type.equals("Availablity"))
/*      */                       {
/* 3878 */                         out.write("\n  <tr >\n    <td align=\"right\" width=\"2%\" class=\"bodytext\">");
/* 3879 */                         if (_jspx_meth_html_005fmultibox_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3881 */                         out.write("</td>\n    <td height=\"22\" width=\"98%\" class=\"bodytext\">");
/* 3882 */                         out.print(FormatUtil.getString("am.webclient.global.availablitymaintenance.text"));
/* 3883 */                         out.write("</td>\n  </tr>\n <tr >\n    <td align=\"right\" width=\"2%\" class=\"bodytext\">");
/* 3884 */                         if (_jspx_meth_html_005fmultibox_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3886 */                         out.write("</td>\n    <td height=\"22\" width=\"98%\" class=\"bodytext\">");
/* 3887 */                         out.print(FormatUtil.getString("am.webclient.global.healthmaintenance.text"));
/* 3888 */                         out.write("</td>\n  </tr>\n  <tr >\n    <td align=\"right\" width=\"2%\" class=\"bodytext\">");
/* 3889 */                         if (_jspx_meth_html_005fmultibox_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3891 */                         out.write(" </td>\n    <td height=\"22\" width=\"98%\" class=\"bodytext\"> ");
/* 3892 */                         out.print(FormatUtil.getString("am.webclient.global.checkgateway.text"));
/* 3893 */                         out.write("  </td>\n  </tr>  \n  <tr>\n    <td colspan='2' class=\"cellpadd-none\">\n      <div id=\"networkgate\" style=\"display:none\">\n  <table width=\"100%\">\n   <tr class=\"yellowgrayborder\">\n\n    <td height=\"22\" class=\"bodytext\" width='92%'> ");
/* 3894 */                         out.print(FormatUtil.getString("am.webclient.global.ping.text"));
/* 3895 */                         out.write(32);
/* 3896 */                         if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3898 */                         out.write(32);
/* 3899 */                         out.print(FormatUtil.getString("am.webclient.global.namegateway.text"));
/* 3900 */                         out.write(". </td>\n  </tr></table>\n      </div></td></tr>\n   \n  <tr >\n   <td align=\"right\" width=\"2%\" class=\"bodytext\">");
/* 3901 */                         if (_jspx_meth_html_005fmultibox_005f18(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3903 */                         out.write(" </td>\n    <td height=\"22\" width=\"98%\" class=\"bodytext\">");
/* 3904 */                         out.print(FormatUtil.getString("am.webclient.urlavailability.checkurlavailability.text"));
/* 3905 */                         out.write("  </td>\n  </tr>\n\n  <tr>\n    <td colspan='2' class=\"cellpadd-none\">\n      <div id=\"urlgate\" style=\"display:none\">\n        <table width='100%'  border='0' class=\"yellowgrayborder\">\n          <tr >\n            <td height=\"22\" class=\"bodytext\" width='92%'><span class=\"label_align1\">");
/* 3906 */                         out.print(FormatUtil.getString("am.webclient.urlavailability.ensure.text"));
/* 3907 */                         out.write("</span>\n              ");
/* 3908 */                         if (_jspx_meth_html_005ftext_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3910 */                         out.write("\n\n            ");
/* 3911 */                         out.print(FormatUtil.getString("am.webclient.urlavailability.monitordown.text"));
/* 3912 */                         out.write("\n\n            </td>\n          </tr>\n          <tr >\n            <td height=\"22\"  width='92%' align='left'><span class='bodytextbold'>\n              ");
/* 3913 */                         out.print(FormatUtil.getString("am.webclient.urlavailability.sendmail.text"));
/* 3914 */                         out.write("</span>\n              ");
/*      */                         
/* 3916 */                         boolean ismailset = com.adventnet.appmanager.util.DBUtil.checkMailSettings();
/* 3917 */                         if (!ismailset)
/*      */                         {
/* 3919 */                           out.write("\n              <span class='bodytext'> ");
/* 3920 */                           out.print(FormatUtil.getString("am.webclient.manager.newsla.esclateemailmessage.text"));
/* 3921 */                           out.write("\n              </span>\n              ");
/*      */                         }
/* 3923 */                         out.write("\n            </td>\n          </tr>\n          <tr >\n            <td height=\"22\" class=\"bodytext\" width='92%'><span class=\"label_align1\">");
/* 3924 */                         out.print(FormatUtil.getString("am.webclient.newaction.fromaddress"));
/* 3925 */                         out.write("\n              &nbsp;</span>");
/* 3926 */                         if (_jspx_meth_html_005ftext_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3928 */                         out.write("\n            </td>\n          </tr>\n          <tr >\n            <td height=\"22\" class=\"bodytext\" width='92%'><span class=\"label_align1\">");
/* 3929 */                         out.print(FormatUtil.getString("am.webclient.newaction.toaddress"));
/* 3930 */                         out.write("</span>");
/* 3931 */                         if (_jspx_meth_html_005ftext_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3933 */                         out.write("\n            </td>\n          </tr>\n        </table>\n      </div>\n  </td></tr>\n  ");
/*      */                         
/* 3935 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3936 */                         _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3937 */                         _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3939 */                         _jspx_th_logic_005fnotPresent_005f4.setRole("ENTERPRISEADMIN");
/* 3940 */                         int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 3941 */                         if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                           for (;;) {
/* 3943 */                             out.write("\n   <tr >\n    <td height=\"22\" class=\"bodytext\">&nbsp;</td>\n    <td align=\"left\" class=\"bodytext\" >");
/* 3944 */                             out.print(FormatUtil.getString("am.webclient.availabilitytest.timeout"));
/* 3945 */                             out.write(32);
/* 3946 */                             if (_jspx_meth_html_005ftext_005f12(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                               return;
/* 3948 */                             out.print(FormatUtil.getString("Seconds"));
/* 3949 */                             out.write("\n  </tr>\n  ");
/* 3950 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 3951 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3955 */                         if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 3956 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                         }
/*      */                         
/* 3959 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 3960 */                         out.write(10);
/* 3961 */                         out.write(10);
/*      */                       }
/* 3963 */                       else if (type.equals("gmapkey"))
/*      */                       {
/* 3965 */                         out.write(10);
/* 3966 */                         out.write(9);
/*      */                         
/* 3968 */                         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3969 */                         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3970 */                         _jspx_th_c_005fif_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3972 */                         _jspx_th_c_005fif_005f3.setTest("${AMActionForm.showMapView == false }");
/* 3973 */                         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3974 */                         if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                           for (;;) {
/* 3976 */                             out.write("\n\t<tr><td width=\"100%\" colspan=\"2\" class=\"bodytext\">");
/* 3977 */                             out.print(FormatUtil.getString("am.webclient.globalsetting.configuremap.text"));
/* 3978 */                             out.write("</td></tr>\n\t");
/* 3979 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3980 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3984 */                         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3985 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                         }
/*      */                         
/* 3988 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3989 */                         out.write("\n    \n\t\t\t<tr >\n\t\t\t  <td width=\"20%\" class=\"bodytext label-align\" align=\"left\" >");
/* 3990 */                         out.print(FormatUtil.getString("am.webclient.gmap.managelocations.text"));
/* 3991 */                         out.write("</td>\n\t\t\t  <td  colspan=\"2\">\n\t\t\t  ");
/*      */                         
/* 3993 */                         SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3994 */                         _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 3995 */                         _jspx_th_html_005fselect_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3997 */                         _jspx_th_html_005fselect_005f2.setProperty("locationid");
/*      */                         
/* 3999 */                         _jspx_th_html_005fselect_005f2.setStyleClass("formtext chosenselect");
/* 4000 */                         int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 4001 */                         if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 4002 */                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4003 */                             out = _jspx_page_context.pushBody();
/* 4004 */                             _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 4005 */                             _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4008 */                             out.write("\n\t\t\t  <option value=\"\" selected=\"selected\">");
/* 4009 */                             out.print(FormatUtil.getString("am.webclient.gmap.createmonitorgroup.noneselected.text"));
/* 4010 */                             out.write("</option>\n\t\t\t  ");
/* 4011 */                             if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/*      */                               return;
/* 4013 */                             out.write("\n\t\t\t  ");
/* 4014 */                             int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 4015 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4018 */                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4019 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4022 */                         if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 4023 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                         }
/*      */                         
/* 4026 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 4027 */                         out.write("\n\t\t\t &nbsp;&nbsp; <smaller><a href=\"#\" onclick=\"javascript:return addLocation();\" class=\"staticlinks\">");
/* 4028 */                         out.print(FormatUtil.getString("am.webclient.gmap.createmonitorgroup.link"));
/* 4029 */                         out.write("</a></smaller> &nbsp;&nbsp;\n\t\t\t  <smaller><a href=\"#\" onclick=\"javascript:return deleteLocation();\" class=\"staticlinks\">");
/* 4030 */                         out.print(FormatUtil.getString("am.webclient.gmap.deletelocation.text"));
/* 4031 */                         out.write("</a></smaller>\n\t\t\t  </td>\n\t\t\t  </tr>\n\t\t\t                                                                                                             </tr>\n\n\n\t\t\t<tr >\n\t\t\t<td width=\"20%\" class=\"bodytext label-align\" align=\"left\">");
/* 4032 */                         out.print(FormatUtil.getString("am.webclient.gmap.height.text"));
/* 4033 */                         out.write("</td>\n\t\t\t<td align=\"left\" class=\"bodytext\" colspan=\"2\">");
/* 4034 */                         if (_jspx_meth_html_005ftext_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4036 */                         out.write("</td></tr>\n\n\t\t\t</td>\n                          </tr>\n\n\n\n                        <tr >\n                        <td width=\"20%\" class=\"bodytext label-align\" align=\"left\">");
/* 4037 */                         out.print(FormatUtil.getString("am.webclient.gmap.width.text"));
/* 4038 */                         out.write("</td>\n                        <td align=\"left\" class=\"bodytext\" colspan=\"2\">");
/* 4039 */                         if (_jspx_meth_html_005ftext_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4041 */                         out.write("</td></tr>\n\n\n\n\n\n\n\t\t\t<!--tr >\n\t\t\t  <td align=\"right\" height=\"22\" class=\"bodytext\">Zoom Level : </td>\n\t\t\t  <td align=\"left\" class=\"bodytext\">");
/* 4042 */                         if (_jspx_meth_html_005ftext_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4044 */                         out.write(" * Enter zoom level between 1 to 18</td>\n\t\t\t  </tr>\n\n\t\t\t  <tr >\n            <td width=\"22%\" height=\"35\" class=\"bodytext\" align=\"right\">");
/* 4045 */                         out.print(FormatUtil.getString("Location"));
/* 4046 */                         out.write(" :    </td>\n            <td width=\"78%\" height=\"35\">\n                    ");
/* 4047 */                         if (_jspx_meth_html_005fselect_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4049 */                         out.write("  <smaller><a href=\"#\" onclick=\"javascript:return addLocation();\" class=\"staticlinks\">");
/* 4050 */                         out.print(FormatUtil.getString("am.webclient.gmap.createmonitorgroup.link"));
/* 4051 */                         out.write("</a></smaller>\n            </td>\n          </tr-->\n\n\n\n");
/*      */                       }
/* 4053 */                       else if (type.equals("performance"))
/*      */                       {
/*      */ 
/* 4056 */                         out.write("\n      <input type=\"hidden\" name=\"showDiskIoTab\" value=\"false\">\n\t  <input type=\"hidden\" name=\"showOracleTab\" value=\"false\">\n\t  <input type=\"hidden\" name=\"showDataCollectionTab\" value=\"false\">\n\t  <input type=\"hidden\" name=\"showWeblogicTab\" value=\"false\">\n\t  <input type=\"hidden\" name=\"showMsSqlTab\" value=\"false\">\n  \t  <input type=\"hidden\" name=\"showMySqlTab\" value=\"false\">\n  \t  <input type=\"hidden\" name=\"showAmazonTab\" value=\"false\">\n  \t  <input type=\"hidden\" name=\"showSnmpTab\" value=\"false\">\n  \t  <input type=\"hidden\" name=\"showUrlTab\" value=\"false\">\n  \t    <input type=\"hidden\" name=\"showDatabaseTab\" value=\"false\">\n  \t    <input type=\"hidden\" name=\"showWebservicesTab\" value=\"false\">\n  \t    <input type=\"hidden\" name=\"showPingTab\" value=\"false\">\n\t\t<input type=\"hidden\" name=\"showConfigureDCTab\" value=\"false\">\n\t\t<input type=\"hidden\" name=\"showConfigureMSSQLTab\" value=\"false\">\n\t\t<tr>\n\t\t   <td width=\"100%\" colspan=\"2\">\n\t\t\t   <div id=\"diskio\" style=\"display:none;\">\n\t\t\t     <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
/* 4057 */                         out.write("\t\t\t\t   <tr>\n\t\t\t\t   \t <td width=\"100%\">\t\n\t\t\t\t   \t \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t\t\t\t\t<td align=\"left\" width=\"30%\" class=\"bodytext label-align\" style=\"padding-top:5px;\" valign=\"top\">");
/* 4058 */                         out.print(FormatUtil.getString("am.webclient.server.diskio.monitoring.text"));
/* 4059 */                         out.write(":&nbsp;</td> \n\t\t\t\t\t\t\t    <td height=\"30\" width=\"70%\" class=\"bodytext\">\n\t\t\t\t\t\t\t    \t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t    \t");
/*      */                         
/* 4061 */                         IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4062 */                         _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 4063 */                         _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4065 */                         _jspx_th_c_005fif_005f4.setTest("${productEdition!='CLOUD'}");
/* 4066 */                         int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 4067 */                         if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                           for (;;) {
/* 4069 */                             out.write("\t\n                                                                    <tr><td class=\"bodytext\">");
/* 4070 */                             if (_jspx_meth_html_005fcheckbox_005f7(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                               return;
/* 4072 */                             out.write("&nbsp;");
/* 4073 */                             out.print(FormatUtil.getString("AIX"));
/* 4074 */                             out.write("</td></tr>\n\t\t\t\t\t\t\t    \t");
/* 4075 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4076 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4080 */                         if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 4081 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                         }
/*      */                         
/* 4084 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4085 */                         out.write("\t\n                                                                    <tr><td class=\"bodytext\">");
/* 4086 */                         if (_jspx_meth_html_005fcheckbox_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4088 */                         out.write("&nbsp;");
/* 4089 */                         out.print(FormatUtil.getString("Linux"));
/* 4090 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t");
/*      */                         
/* 4092 */                         IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4093 */                         _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 4094 */                         _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4096 */                         _jspx_th_c_005fif_005f5.setTest("${productEdition!='CLOUD'}");
/* 4097 */                         int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 4098 */                         if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                           for (;;) {
/* 4100 */                             out.write("\t\n                                                                    <tr><td class=\"bodytext\">");
/* 4101 */                             if (_jspx_meth_html_005fcheckbox_005f9(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                               return;
/* 4103 */                             out.write("&nbsp;");
/* 4104 */                             out.print(FormatUtil.getString("Sun Solaris"));
/* 4105 */                             out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4106 */                             if (_jspx_meth_html_005fcheckbox_005f10(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                               return;
/* 4108 */                             out.write("&nbsp;");
/* 4109 */                             out.print(FormatUtil.getString("Windows"));
/* 4110 */                             out.write("</td></tr>\n\t\t\t\t\t\t\t\t\t");
/* 4111 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 4112 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4116 */                         if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 4117 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                         }
/*      */                         
/* 4120 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4121 */                         out.write("\t    \n                                                                </table>\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\n                                                        \t");
/*      */                         
/* 4123 */                         IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4124 */                         _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 4125 */                         _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4127 */                         _jspx_th_c_005fif_005f6.setTest("${productEdition!='CLOUD'}");
/* 4128 */                         int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 4129 */                         if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                           for (;;) {
/* 4131 */                             out.write("\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\">\n                                <span class=\"hr-divider\">&nbsp;</span>\n                                </td>\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t\t\t\t    <td align=\"left\" width=\"30%\" class=\"bodytext label-align\" style=\"padding-top:5px;\" valign=\"top\">");
/* 4132 */                             out.print(FormatUtil.getString("am.webclient.windows.disk.monitoring.text"));
/* 4133 */                             out.write(":&nbsp;</td>\n\t\t\t\t\t\t\t    <td height=\"30\" width=\"70%\" class=\"bodytext\">\n\t\t\t\t\t\t\t    \t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4134 */                             if (_jspx_meth_html_005fcheckbox_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                               return;
/* 4136 */                             out.write("&nbsp;");
/* 4137 */                             out.print(FormatUtil.getString("am.webclient.windows.localdisk.text"));
/* 4138 */                             out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4139 */                             if (_jspx_meth_html_005fcheckbox_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                               return;
/* 4141 */                             out.write("&nbsp;");
/* 4142 */                             out.print(FormatUtil.getString("am.webclient.windows.netmappeddrive.text"));
/* 4143 */                             out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4144 */                             if (_jspx_meth_html_005fcheckbox_005f13(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                               return;
/* 4146 */                             out.write("&nbsp;");
/* 4147 */                             out.print(FormatUtil.getString("am.webclient.windows.volume.mount.text"));
/* 4148 */                             out.write("</td></tr>\n\t\t\t\t\t\t\t\t\t    ");
/*      */                             
/* 4150 */                             SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4151 */                             _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4152 */                             _jspx_th_c_005fset_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                             
/* 4154 */                             _jspx_th_c_005fset_005f0.setVar("deleteDisk");
/* 4155 */                             int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4156 */                             if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4157 */                               if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4158 */                                 out = _jspx_page_context.pushBody();
/* 4159 */                                 _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4160 */                                 _jspx_th_c_005fset_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4163 */                                 out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.host.deleteDisk"));
/* 4164 */                                 int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4165 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4168 */                               if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4169 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4172 */                             if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4173 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                             }
/*      */                             
/* 4176 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4177 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t<tr><td class=\"bodytext\"><input id=\"cb71\" name=\"am.host.deleteDisk\" type=\"hidden\" value=\"false\"/><input type=\"checkbox\" id=\"cb72\" value='");
/* 4178 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                               return;
/* 4180 */                             out.write("'  style=\"position:relative;\" ");
/* 4181 */                             if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                               return;
/* 4183 */                             out.write("/>&nbsp;");
/* 4184 */                             out.print(FormatUtil.getString("am.host.deleteDisk"));
/* 4185 */                             out.write("<input type=\"text\" value='");
/* 4186 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.host.disk.delete.consecutivePollingCount"));
/* 4187 */                             out.write("' name=\"am.host.disk.delete.consecutivePollingCount\" style=\"position:relative;\" size=\"5\"/>&nbsp;");
/* 4188 */                             out.print(FormatUtil.getString("am.host.disk.delete.consecutivePollingCount"));
/* 4189 */                             out.write("</td></tr>\n\t\t\t\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 4190 */                             out.print(FormatUtil.getString("am.disks.ignore"));
/* 4191 */                             out.write("&nbsp;&nbsp;<input type=\"text\"  value='");
/* 4192 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.disks.ignore"));
/* 4193 */                             out.write("' id=\"ignoredisks\" name=\"am.disks.ignore\" style=\"position:relative;\" size=\"36\"/></td></tr>\n\t\t\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\t\n                                                        ");
/* 4194 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4195 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4199 */                         if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4200 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                         }
/*      */                         
/* 4203 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4204 */                         out.write("\t\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t   \t \t\t\t");
/*      */                         
/* 4206 */                         SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4207 */                         _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4208 */                         _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4210 */                         _jspx_th_c_005fset_005f1.setVar("processinstance");
/* 4211 */                         int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4212 */                         if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4213 */                           if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4214 */                             out = _jspx_page_context.pushBody();
/* 4215 */                             _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 4216 */                             _jspx_th_c_005fset_005f1.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4219 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.processinstance.equals"));
/* 4220 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4221 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4224 */                           if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4225 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4228 */                         if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4229 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                         }
/*      */                         
/* 4232 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4233 */                         out.write("\n\t\t\t\t   \t \t\t\t");
/*      */                         
/* 4235 */                         SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4236 */                         _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 4237 */                         _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4239 */                         _jspx_th_c_005fset_005f2.setVar("mgProcessAndServiceAssoicated");
/* 4240 */                         int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 4241 */                         if (_jspx_eval_c_005fset_005f2 != 0) {
/* 4242 */                           if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4243 */                             out = _jspx_page_context.pushBody();
/* 4244 */                             _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 4245 */                             _jspx_th_c_005fset_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4248 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.mg.processAndService.associate"));
/* 4249 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 4250 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4253 */                           if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4254 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4257 */                         if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 4258 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                         }
/*      */                         
/* 4261 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 4262 */                         out.write("\n\t\t\t\t   \t \t\t\t<td align=\"left\" width=\"30%\" class=\"bodytext label-align\" style=\"padding-top:5px;\" valign=\"top\">");
/* 4263 */                         out.print(FormatUtil.getString("am.webclient.server.process.services.availability.text"));
/* 4264 */                         out.write(":&nbsp;</td>\n\t\t\t\t\t\t\t    <td height=\"30\" width=\"70%\" class=\"bodytext\">\n\t\t\t\t\t\t\t    \t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4265 */                         if (_jspx_meth_html_005fcheckbox_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4267 */                         out.write("&nbsp;");
/* 4268 */                         out.print(FormatUtil.getString("am.webclient.server.process.down.text"));
/* 4269 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t\t\t");
/*      */                         
/* 4271 */                         IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4272 */                         _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4273 */                         _jspx_th_c_005fif_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4275 */                         _jspx_th_c_005fif_005f8.setTest("${productEdition!='CLOUD'}");
/* 4276 */                         int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4277 */                         if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                           for (;;) {
/* 4279 */                             out.write("\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4280 */                             if (_jspx_meth_html_005fcheckbox_005f15(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                               return;
/* 4282 */                             out.write("&nbsp;");
/* 4283 */                             out.print(FormatUtil.getString("am.webclient.server.windows.services.down.text"));
/* 4284 */                             out.write("</td></tr>\n\t\t\t\t\t\t\t\t\t");
/* 4285 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4286 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4290 */                         if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4291 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                         }
/*      */                         
/* 4294 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4295 */                         out.write("\n\t\t\t\t\t\t\t\t\t<tr><td class=\"bodytext\"><input id=\"cb61\" name=\"am.processinstance.equals\" type=\"hidden\" value=\"false\"/><input type=\"checkbox\" id=\"cb62\" value='");
/* 4296 */                         if (_jspx_meth_c_005fout_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4298 */                         out.write("'  style=\"position:relative;\" ");
/* 4299 */                         if (_jspx_meth_c_005fif_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4301 */                         out.write("/>&nbsp;");
/* 4302 */                         out.print(FormatUtil.getString("am.processinstance.equals"));
/* 4303 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t\t\t<tr><td class=\"bodytext\"><input id=\"cb63\" name=\"am.mg.processAndService.associate\" type=\"hidden\" value=\"false\"/><input type=\"checkbox\" id=\"cb64\" value='");
/* 4304 */                         if (_jspx_meth_c_005fout_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4306 */                         out.write("' name=\"mgProcessAndServiceAssoicated\" style=\"position:relative;\" ");
/* 4307 */                         if (_jspx_meth_c_005fif_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4309 */                         out.write("/>&nbsp;");
/* 4310 */                         out.print(FormatUtil.getString("am.mg.processAndService.associate"));
/* 4311 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t\t\t\t\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t\t\t\t    <td align=\"left\" width=\"30%\" class=\"bodytext label-align\" style=\"padding-top:5px;\" valign=\"top\">");
/* 4312 */                         out.print(FormatUtil.getString("am.webclient.server.error.alert.settings.title"));
/* 4313 */                         out.write(":&nbsp;</td>\n\t\t\t\t\t\t\t    <td height=\"30\" width=\"70%\" class=\"bodytext\">\n\t\t\t\t\t\t\t    \t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4314 */                         if (_jspx_meth_html_005fcheckbox_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4316 */                         out.write("&nbsp;");
/* 4317 */                         out.print(FormatUtil.getString("am.webclient.server.error.disk.alert"));
/* 4318 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4319 */                         if (_jspx_meth_html_005fcheckbox_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4321 */                         out.write("&nbsp;");
/* 4322 */                         out.print(FormatUtil.getString("am.webclient.server.error.netwrokinterface.alert"));
/* 4323 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4324 */                         if (_jspx_meth_html_005fcheckbox_005f18(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4326 */                         out.write("&nbsp;");
/* 4327 */                         out.print(FormatUtil.getString("am.webclient.server.error.networkadapter.alert"));
/* 4328 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4329 */                         if (_jspx_meth_html_005fcheckbox_005f19(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4331 */                         out.write("&nbsp;");
/* 4332 */                         out.print(FormatUtil.getString("am.webclient.server.error.restart.alert"));
/* 4333 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4334 */                         if (_jspx_meth_html_005fcheckbox_005f20(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4336 */                         out.write("&nbsp;");
/* 4337 */                         out.print(FormatUtil.getString("am.webclient.server.error.processrestart.alert"));
/* 4338 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4339 */                         if (_jspx_meth_html_005fcheckbox_005f21(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4341 */                         out.write("&nbsp;");
/* 4342 */                         out.print(FormatUtil.getString("am.webclient.server.error.scheduledtasks.alert"));
/* 4343 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t   \t \t\t  ");
/*      */                         
/* 4345 */                         SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4346 */                         _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 4347 */                         _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4349 */                         _jspx_th_c_005fset_005f3.setVar("bulkexec");
/* 4350 */                         int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 4351 */                         if (_jspx_eval_c_005fset_005f3 != 0) {
/* 4352 */                           if (_jspx_eval_c_005fset_005f3 != 1) {
/* 4353 */                             out = _jspx_page_context.pushBody();
/* 4354 */                             _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 4355 */                             _jspx_th_c_005fset_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4358 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.cli.command.bulk.execute"));
/* 4359 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 4360 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4363 */                           if (_jspx_eval_c_005fset_005f3 != 1) {
/* 4364 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4367 */                         if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 4368 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                         }
/*      */                         
/* 4371 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 4372 */                         out.write("\n\t\t\t\t   \t \t\t  ");
/*      */                         
/* 4374 */                         SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4375 */                         _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 4376 */                         _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4378 */                         _jspx_th_c_005fset_005f4.setVar("loginprefix");
/* 4379 */                         int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 4380 */                         if (_jspx_eval_c_005fset_005f4 != 0) {
/* 4381 */                           if (_jspx_eval_c_005fset_005f4 != 1) {
/* 4382 */                             out = _jspx_page_context.pushBody();
/* 4383 */                             _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 4384 */                             _jspx_th_c_005fset_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4387 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.cli.loginprefix"));
/* 4388 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 4389 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4392 */                           if (_jspx_eval_c_005fset_005f4 != 1) {
/* 4393 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4396 */                         if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 4397 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */                         }
/*      */                         
/* 4400 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 4401 */                         out.write("\n\t\t\t\t   \t \t\t  ");
/*      */                         
/* 4403 */                         SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4404 */                         _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 4405 */                         _jspx_th_c_005fset_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4407 */                         _jspx_th_c_005fset_005f5.setVar("cliCaching");
/* 4408 */                         int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 4409 */                         if (_jspx_eval_c_005fset_005f5 != 0) {
/* 4410 */                           if (_jspx_eval_c_005fset_005f5 != 1) {
/* 4411 */                             out = _jspx_page_context.pushBody();
/* 4412 */                             _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 4413 */                             _jspx_th_c_005fset_005f5.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4416 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.cliSession.caching"));
/* 4417 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 4418 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4421 */                           if (_jspx_eval_c_005fset_005f5 != 1) {
/* 4422 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4425 */                         if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 4426 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5); return;
/*      */                         }
/*      */                         
/* 4429 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 4430 */                         out.write("\n\t\t\t\t   \t \t\t  ");
/*      */                         
/* 4432 */                         SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4433 */                         _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 4434 */                         _jspx_th_c_005fset_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4436 */                         _jspx_th_c_005fset_005f6.setVar("matchIndex");
/* 4437 */                         int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 4438 */                         if (_jspx_eval_c_005fset_005f6 != 0) {
/* 4439 */                           if (_jspx_eval_c_005fset_005f6 != 1) {
/* 4440 */                             out = _jspx_page_context.pushBody();
/* 4441 */                             _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 4442 */                             _jspx_th_c_005fset_005f6.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4445 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.server.cli.match.category.index"));
/* 4446 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 4447 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4450 */                           if (_jspx_eval_c_005fset_005f6 != 1) {
/* 4451 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4454 */                         if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 4455 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                         }
/*      */                         
/* 4458 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 4459 */                         out.write("\n\t\t\t\t\t\t\t    <td align=\"left\" width=\"30%\" class=\"bodytext label-align\" style=\"padding-top:5px;\" valign=\"top\">");
/* 4460 */                         out.print(FormatUtil.getString("am.webclient.server.telnet.settings.title"));
/* 4461 */                         out.write(":&nbsp;</td>\n\t\t\t\t\t\t\t    <td height=\"30\" width=\"70%\" class=\"bodytext\">\n\t\t\t\t\t\t\t    \t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\"><input id=\"cb21\" name=\"am.cli.command.bulk.execute\" type=\"hidden\" value=\"false\"/><input type=\"checkbox\" id=\"cb22\" value='");
/* 4462 */                         if (_jspx_meth_c_005fout_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4464 */                         out.write("'  style=\"position:relative;\" ");
/* 4465 */                         if (_jspx_meth_c_005fif_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4467 */                         out.write("/>&nbsp;");
/* 4468 */                         out.print(FormatUtil.getString("am.cli.command.bulk.execute"));
/* 4469 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\"><input id=\"cb31\" name=\"am.cli.loginprefix\" type=\"hidden\" value=\"false\"/><input type=\"checkbox\" id=\"cb32\" value='");
/* 4470 */                         if (_jspx_meth_c_005fout_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4472 */                         out.write("'  style=\"position:relative;\" ");
/* 4473 */                         if (_jspx_meth_c_005fif_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4475 */                         out.write("/>&nbsp;");
/* 4476 */                         out.print(FormatUtil.getString("am.cli.loginprefix"));
/* 4477 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\"><input id=\"cb41\" name=\"am.cliSession.caching\" type=\"hidden\" value=\"false\"/><input type=\"checkbox\" id=\"cb42\" value='");
/* 4478 */                         if (_jspx_meth_c_005fout_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4480 */                         out.write("'  style=\"position:relative;\" ");
/* 4481 */                         if (_jspx_meth_c_005fif_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4483 */                         out.write("/>&nbsp;");
/* 4484 */                         out.print(FormatUtil.getString("am.cliSession.caching"));
/* 4485 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t    <tr><td class=\"bodytext\">\n\t\t\t\t\t\t\t    \t\t<table>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4486 */                         out.print(FormatUtil.getString("am.telnet.loginTimeOut"));
/* 4487 */                         out.write("</td><td><input type=\"text\" id=\"cb5\" value='");
/* 4488 */                         out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.telnet.loginTimeOut"));
/* 4489 */                         out.write("' name=\"am.telnet.loginTimeOut\" maxlength=\"10\" style=\"position:relative;\" /></td></tr>\n\t\t   \t\t   \t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 4490 */                         out.print(FormatUtil.getString("am.cli.bulkcmd.exec.responsetimeout"));
/* 4491 */                         out.write("</td><td><input type=\"text\" id=\"cb6\" value='");
/* 4492 */                         out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.cli.bulkcmd.exec.responsetimeout"));
/* 4493 */                         out.write("' name=\"am.cli.bulkcmd.exec.responsetimeout\" maxlength=\"10\" style=\"position:relative;\" /></td></tr>\n\t\t   \t\t   \t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 4494 */                         out.print(FormatUtil.getString("am.server.command.responsetimeout"));
/* 4495 */                         out.write("</td><td><input type=\"text\" id=\"cb9\"  value='");
/* 4496 */                         out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.server.command.responsetimeout"));
/* 4497 */                         out.write("' name=\"am.server.command.responsetimeout\" maxlength=\"10\" style=\"position:relative;\" /></td></tr>\n\t\t\t\t\t\t\t    \t\t</table> \n\t\t\t\t\t\t\t    \t\t</td></tr>\n\t\t\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t   \t \t\t  ");
/*      */                         
/* 4499 */                         SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4500 */                         _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 4501 */                         _jspx_th_c_005fset_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4503 */                         _jspx_th_c_005fset_005f7.setVar("resendvbscripts");
/* 4504 */                         int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 4505 */                         if (_jspx_eval_c_005fset_005f7 != 0) {
/* 4506 */                           if (_jspx_eval_c_005fset_005f7 != 1) {
/* 4507 */                             out = _jspx_page_context.pushBody();
/* 4508 */                             _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 4509 */                             _jspx_th_c_005fset_005f7.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4512 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.server.windows.resend.vbscripts"));
/* 4513 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 4514 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4517 */                           if (_jspx_eval_c_005fset_005f7 != 1) {
/* 4518 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4521 */                         if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 4522 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                         }
/*      */                         
/* 4525 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 4526 */                         out.write("\n\t\t\t\t   \t \t\t  ");
/*      */                         
/* 4528 */                         SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4529 */                         _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 4530 */                         _jspx_th_c_005fset_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4532 */                         _jspx_th_c_005fset_005f8.setVar("enableRawData");
/* 4533 */                         int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 4534 */                         if (_jspx_eval_c_005fset_005f8 != 0) {
/* 4535 */                           if (_jspx_eval_c_005fset_005f8 != 1) {
/* 4536 */                             out = _jspx_page_context.pushBody();
/* 4537 */                             _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 4538 */                             _jspx_th_c_005fset_005f8.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4541 */                             out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.rawdata.enabled"));
/* 4542 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 4543 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4546 */                           if (_jspx_eval_c_005fset_005f8 != 1) {
/* 4547 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4550 */                         if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 4551 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                         }
/*      */                         
/* 4554 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 4555 */                         out.write("\n\t\t\t\t\t\t\t    <td align=\"left\" width=\"30%\" class=\"bodytext label-align\" style=\"padding-top:5px;\" valign=\"top\">");
/* 4556 */                         out.print(FormatUtil.getString("am.webclient.server.wmi.settings.title"));
/* 4557 */                         out.write(":&nbsp;</td>\n\t\t\t\t\t\t\t    <td height=\"30\" width=\"70%\" class=\"bodytext\">\n\t\t\t\t\t\t\t    \t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\"><input id=\"cb01\" name=\"am.server.windows.resend.vbscripts\" type=\"hidden\" value=\"false\"/><input type=\"checkbox\" value='");
/* 4558 */                         if (_jspx_meth_c_005fout_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4560 */                         out.write("' id=\"cb02\" style=\"position:relative;\" ");
/* 4561 */                         if (_jspx_meth_c_005fif_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4563 */                         out.write("/>&nbsp;");
/* 4564 */                         out.print(FormatUtil.getString("am.server.windows.resend.vbscripts"));
/* 4565 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\"><input id=\"cb11\" name=\"am.rawdata.enabled\" type=\"hidden\" value=\"false\"/>  <input type=\"checkbox\" value='");
/* 4566 */                         if (_jspx_meth_c_005fout_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4568 */                         out.write("' id=\"cb12\" style=\"position:relative;\" ");
/* 4569 */                         if (_jspx_meth_c_005fif_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4571 */                         out.write("/>&nbsp;");
/* 4572 */                         out.print(FormatUtil.getString("am.rawdata.enabled"));
/* 4573 */                         out.write("</td></tr>\n\t\t\t\t\t\t \t\t\t\t");
/*      */                         
/* 4575 */                         String encoding = "";
/* 4576 */                         if (com.adventnet.appmanager.util.Constants.getWmiEncoding() != null) {
/* 4577 */                           encoding = com.adventnet.appmanager.util.Constants.getWmiEncoding();
/*      */                         }
/*      */                         
/* 4580 */                         out.write("\n\t\t\t\t\t\t\t    \t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td width=\"30%\"  class=\"tdindent\" align=\"left\">\n\t\t\t\t\t\t\t\t\t<table>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4581 */                         out.print(FormatUtil.getString("am.wmi.encoding"));
/* 4582 */                         out.write(" : </td>\n\t\t\t\t\t\t\t    \t\t    <td><input type=\"text\"  value='");
/* 4583 */                         out.print(encoding);
/* 4584 */                         out.write("' id=\"wmiencoding\" name=\"am.wmi.encoding\" style=\"position:relative;\" /></td>\n\t\t\t\t\t\t\t    \t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t</td></tr>\n\t\t\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\"><span class=\"hr-divider\">&nbsp;</span></td>\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t   \t \t\t<td width=\"30%\" class=\"bodytext label-align\" style=\"padding-top:5px;padding-bottom:5px\">");
/* 4585 */                         out.print(FormatUtil.getString("am.webclient.admin.performancepoll.servers.enable.networkadapter"));
/* 4586 */                         out.write(":</td>\n\t\t\t\t   \t \t\t<td width=\"70%\">&nbsp;&nbsp;");
/* 4587 */                         if (_jspx_meth_html_005fcheckbox_005f22(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4589 */                         out.write("</td>\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t<tr>\n\t\t\t\t   \t \t\t\t<td align=\"left\" width=\"30%\" class=\"bodytext label-align\" style=\"padding-top:5px;\" valign=\"top\">");
/* 4590 */                         out.print(FormatUtil.getString("am.webclient.admin.performancepoll.servers.hardwareHealth"));
/* 4591 */                         out.write(":&nbsp;</td>\n\t\t\t\t\t\t\t    <td height=\"30\" width=\"70%\" class=\"bodytext\">\n\t\t\t\t\t\t\t    \t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n  \t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t    \t\t<td width=\"55%\"  align=\"left\">\n\t\t\t\t\t\t\t    \t\t\t");
/* 4592 */                         if (_jspx_meth_html_005fradio_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4594 */                         out.write("<span class=\"bodytext\" >");
/* 4595 */                         out.print(FormatUtil.getString("Enable"));
/* 4596 */                         out.write("</span>&nbsp;&nbsp;&nbsp;");
/* 4597 */                         if (_jspx_meth_html_005fradio_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4599 */                         out.write("<span class=\"bodytext\" >");
/* 4600 */                         out.print(FormatUtil.getString("Disable"));
/* 4601 */                         out.write("</span></td>\n\t\t\t\t\t\t\t    \t\t</tr>\n\t\t\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr class=\"hwComponent_Space\">\n\t\t\t\t   \t \t\t\t<td colspan=\"2\">&nbsp;</td>\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t   \t \t\t<tr class=\"hwComponent\">\n\t\t\t\t\t\t\t    <td align=\"left\" width=\"30%\" class=\"bodytext label-align\" style=\"padding-top:5px;\" valign=\"top\">");
/* 4602 */                         out.print(FormatUtil.getString("am.hardware.attributes.text"));
/* 4603 */                         out.write(":&nbsp;</td>\n\t\t\t\t\t\t\t    <td height=\"30\" width=\"70%\" class=\"bodytext\">\n\t\t\t\t\t\t\t    \t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4604 */                         if (_jspx_meth_html_005fcheckbox_005f23(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4606 */                         out.write("&nbsp;");
/* 4607 */                         out.print(FormatUtil.getString("am.hardware.component.temperature"));
/* 4608 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4609 */                         if (_jspx_meth_html_005fcheckbox_005f24(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4611 */                         out.write("&nbsp;");
/* 4612 */                         out.print(FormatUtil.getString("am.hardware.component.power"));
/* 4613 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4614 */                         if (_jspx_meth_html_005fcheckbox_005f25(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4616 */                         out.write("&nbsp;");
/* 4617 */                         out.print(FormatUtil.getString("am.hardware.component.fan"));
/* 4618 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4619 */                         if (_jspx_meth_html_005fcheckbox_005f26(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4621 */                         out.write("&nbsp;");
/* 4622 */                         out.print(FormatUtil.getString("am.hardware.component.processor"));
/* 4623 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4624 */                         if (_jspx_meth_html_005fcheckbox_005f27(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4626 */                         out.write("&nbsp;");
/* 4627 */                         out.print(FormatUtil.getString("am.hardware.component.disk"));
/* 4628 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4629 */                         if (_jspx_meth_html_005fcheckbox_005f28(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4631 */                         out.write("&nbsp;");
/* 4632 */                         out.print(FormatUtil.getString("am.hardware.component.array"));
/* 4633 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4634 */                         if (_jspx_meth_html_005fcheckbox_005f29(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4636 */                         out.write("&nbsp;");
/* 4637 */                         out.print(FormatUtil.getString("am.hardware.component.chassis"));
/* 4638 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t\t    \t<tr><td class=\"bodytext\">");
/* 4639 */                         if (_jspx_meth_html_005fcheckbox_005f30(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4641 */                         out.write("&nbsp;");
/* 4642 */                         out.print(FormatUtil.getString("am.hardware.component.memorydevice"));
/* 4643 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4644 */                         if (_jspx_meth_html_005fcheckbox_005f31(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4646 */                         out.write("&nbsp;");
/* 4647 */                         out.print(FormatUtil.getString("am.hardware.component.voltages"));
/* 4648 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 4649 */                         if (_jspx_meth_html_005fcheckbox_005f32(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4651 */                         out.write("&nbsp;");
/* 4652 */                         out.print(FormatUtil.getString("am.hardware.component.battery"));
/* 4653 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   \t \t\t\n\t\t\t\t\t\t\t<tr class=\"hwComponent_Critical_Severity_Space\">\n\t\t\t\t   \t \t\t\t<td colspan=\"2\">&nbsp;</td>\n\t\t\t\t   \t \t\t</tr>\t\t\t\t   \t \t\t  \t \t\t\n\t\t\t\t   \t \t\t<tr class=\"hwComponent_Critical_Severity\">\n\t\t\t\t\t\t\t    <td align=\"left\" width=\"30%\" class=\"bodytext label-align tdindent\" style=\"padding-top:10px;\" valign=\"top\">");
/* 4654 */                         out.print(FormatUtil.getString("am.hardware.critical.severity.text"));
/* 4655 */                         out.write("</td>\n\t\t\t\t\t\t\t    <td height=\"30\" class=\"bodytext\">\n\t\t\t\t\t\t\t    ");
/* 4656 */                         if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4658 */                         out.write("\n\t\t\t\t\t\t\t    <!--<span class=\"bodytext\"><a href=\"javascript:setDefaultValue('hwCriticalStatusMessage')\" class=\"new-monitordiv-link\" style=\"position:relative;bottom:30px\">Reset</a></span>-->\n\t\t\t\t\t\t\t    </td>\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t\t\t\t<tr class=\"hwComponent_Major_Severity_Space\">\n\t\t\t\t   \t \t\t\t<td colspan=\"2\">&nbsp;</td>\n\t\t\t\t   \t \t\t</tr>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t<tr class=\"hwComponent_Major_Severity\">\n\t\t\t\t\t\t\t    <td align=\"left\" width=\"30%\" class=\"bodytext label-align tdindent\" style=\"padding-top:10px;\" valign=\"top\">");
/* 4659 */                         out.print(FormatUtil.getString("am.hardware.warning.severity.text"));
/* 4660 */                         out.write("</td>\n\t\t\t\t\t\t\t    <td height=\"30\" class=\"bodytext\">\n\t\t\t\t\t\t\t    ");
/* 4661 */                         if (_jspx_meth_html_005ftextarea_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4663 */                         out.write("\n\t\t\t\t\t\t\t    <!--<span class=\"bodytext\"><a href=\"javascript:setDefaultValue('hwWarningStatusMessage')\" class=\"new-monitordiv-link\" style=\"position:relative;bottom:30px\">Reset</a></span>-->\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\n\t\t\t\t\t\t\t<tr class=\"hwComponent_Clear_Severity_Space\">\n\t\t\t\t   \t \t\t\t<td colspan=\"2\">&nbsp;</td>\n\t\t\t\t   \t \t\t</tr>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t<tr class=\"hwComponent_Clear_Severity\">\n\t\t\t\t\t\t\t    <td align=\"left\" width=\"30%\" class=\"bodytext label-align tdindent\" style=\"padding-top:10px;\" valign=\"top\">");
/* 4664 */                         out.print(FormatUtil.getString("am.hardware.clear.severity.text"));
/* 4665 */                         out.write("</td>\n\t\t\t\t\t\t\t    <td height=\"30\" class=\"bodytext\">\n\t\t\t\t\t\t\t    ");
/* 4666 */                         if (_jspx_meth_html_005ftextarea_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4668 */                         out.write("\n\t\t\t\t\t\t\t    <!--<span class=\"bodytext\"><a href=\"javascript:setDefaultValue('hwClearStatusMessage')\" class=\"new-monitordiv-link\" style=\"position:relative;bottom:30px\">Reset</a></span>-->\n\t\t\t\t\t\t\t    </td>\t\t\t\t   \t \t\t\n\t\t\t\t   \t \t\t</tr>\t\n\t\t\t\t   \t \t\t\t\t\t\t\t   \t \t\t\t\t\t\t   \t \t\t\t\t\t\t   \t \t\t\t\t\t\t\t\n\t\t\t\t   \t \t</table>\n\t\t\t\t   \t </td>\n\t\t\t\t   </tr>\n\t\t\t\t </table>\n\t\t\t    </div>\n\t\t    \n\t     <div id=\"performance\" style=\"display:none;\">\n\t\t  <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t    <tr >\n\t\t      <td align=\"left\" height=\"22\" width=\"100%\" class=\"bodytext\">");
/* 4669 */                         out.print(FormatUtil.getString("am.webclient.performancetext.text"));
/* 4670 */                         out.write(" &nbsp; ");
/* 4671 */                         if (_jspx_meth_html_005ftext_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4673 */                         out.write("&nbsp;");
/* 4674 */                         out.print(FormatUtil.getString("pollings"));
/* 4675 */                         out.write("</td>\n\t\t    </tr>\n\t\t   </table>\n\t     </div>\n\t\t\t <div id=\"oracle\" style=\"display:none;\">\n\t\t\t  \n\t\t\t </div>\n\t\t   \t   <div id=\"mssql\" style=\"display:none;\">\n\t\t   \t  \n\t\t   \t    </div>\n\t\t   \t   <div id=\"snmp\" style=\"display:none;\">\n\t\t   \t     <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t   \t\t   <tr>\n\t\t   \t\t    <td align=\"left\" width=\"45%\" class=\"label-align\"><span class=\"bodytext\">");
/* 4676 */                         out.print(FormatUtil.getString("am.webclient.global.snmpversion.text"));
/* 4677 */                         out.write("</span></td>\n\t\t   \t\t    <td width=\"55%\" align=\"left\">");
/* 4678 */                         if (_jspx_meth_html_005fradio_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4680 */                         out.write("<span class=\"bodytext\">");
/* 4681 */                         out.print(FormatUtil.getString("SNMPv1"));
/* 4682 */                         out.write("</span>&nbsp;&nbsp;");
/* 4683 */                         if (_jspx_meth_html_005fradio_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4685 */                         out.write("<span class=\"bodytext\">");
/* 4686 */                         out.print(FormatUtil.getString("SNMPv2c"));
/* 4687 */                         out.write("</span></td>\n\t\t   \t\t   </tr>\n\t\t   \t\t </table>\n\t\t   \t    </div>\n\t\t   \t   <div id=\"url\" style=\"display:none;\">\n\t\t   \t     <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"2\">\n\t\t   \t\t   <tr>\n\t\t   \t\t    <td width=\"2%\"  align=\"right\">");
/* 4688 */                         if (_jspx_meth_html_005fcheckbox_005f33(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4690 */                         out.write("</td>\n\t\t   \t\t    <td align=\"left\" width=\"98%\"  align=\"left\"><span class=\"bodytext\">");
/* 4691 */                         out.print(FormatUtil.getString("am.webclient.urldebug.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/* 4692 */                         out.write("</span></td>");
/* 4693 */                         out.write("\n\t\t   \t\t   </tr>\n\t\t   \t\t   <tr>\n\t\t   \t\t    <td width=\"2%\"  align=\"right\">");
/* 4694 */                         if (_jspx_meth_html_005fcheckbox_005f34(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4696 */                         out.write("</td>\n\t\t   \t\t    <td align=\"left\" width=\"98%\"  align=\"left\"><span class=\"bodytext\">");
/* 4697 */                         out.print(FormatUtil.getString("am.webclient.urlresponses.text"));
/* 4698 */                         out.write("</span></td>\n\t\t   \t\t   </tr>\n\t\t   \t\t   ");
/* 4699 */                         if (com.adventnet.appmanager.util.Constants.isRepollEnabled()) {
/* 4700 */                           out.write("\n\t\t   \t\t   <tr>\n\t\t   \t\t    <td colspan=\"2\">\n\t\t   \t\t     <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t   \t\t    <td>");
/* 4701 */                           out.print(FormatUtil.getString("am.repoll.interval"));
/* 4702 */                           out.write("  <input type=\"text\" id=\"repollInterval\" value='");
/* 4703 */                           out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.repoll.interval"));
/* 4704 */                           out.write("' name=\"am.repoll.interval\" maxlength=\"5\" size=\"4\" style=\"position:relative;\" /></td>\n\t\t   \t\t     </table>\n\t\t   \t\t    </td>\n\t\t   \t\t   </tr>\n\t\t   \t\t   ");
/*      */                         }
/* 4706 */                         out.write("\n\t\t   \t\t </table>\n\t\t   \t    </div>\n\t    \t\t     \n   \t\t     <div id=\"amazon\" style=\"display:none;\">\n   \t\t       <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t    \t\t  \n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"left\" width=\"30%\" class=\"bodytextbold label-align\">");
/* 4707 */                         out.print(FormatUtil.getString("am.webclient.amazon.perfpolling.ec2identifier.header"));
/* 4708 */                         out.write("</td>\n\t\t\t\t\t\t<td align=\"left\" width=\"70%\">\n\t\t\t\t\t\t\t");
/* 4709 */                         if (_jspx_meth_html_005fradio_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4711 */                         out.write("\n\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 4712 */                         out.print(FormatUtil.getString("am.webclient.amazon.perfpolling.instanceid.text"));
/* 4713 */                         out.write("</span>&nbsp;&nbsp;\n\t\t\t\t\t\t\t");
/* 4714 */                         if (_jspx_meth_html_005fradio_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4716 */                         out.write("\n\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 4717 */                         out.print(FormatUtil.getString("am.webclient.amazon.perfpolling.tagname.text"));
/* 4718 */                         out.write("</span>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"left\" width=\"30%\">&nbsp;</td>\n\t\t\t\t\t\t<td align=\"left\" width=\"70%\" class=\"bodytext\" style=\"padding-left: 10px;\">");
/* 4719 */                         out.print(FormatUtil.getString("am.webclient.amazon.perfpolling.caution.text"));
/* 4720 */                         out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"left\" width=\"30%\">&nbsp;</td>\n\t\t\t\t\t\t<td align=\"left\" width=\"70%\" class=\"bodytext\" style=\"padding-left: 10px;\">");
/* 4721 */                         out.print(FormatUtil.getString("am.webclient.amazon.perfpolling.allowmerge.text"));
/* 4722 */                         out.write("</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"left\" width=\"30%\" class=\"bodytext label-align tdindent\">");
/* 4723 */                         out.print(FormatUtil.getString("am.webclient.amazon.perfpolling.terminatedec2.text"));
/* 4724 */                         out.write("</td>\n\t\t\t\t\t\t<td align=\"left\" width=\"70%\">\n\t\t\t\t\t\t\t");
/* 4725 */                         if (_jspx_meth_html_005fcheckbox_005f35(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4727 */                         out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"left\" valign=\"top\" width=\"30%\" class=\"bodytext label-align tdindent\" title='");
/* 4728 */                         out.print(FormatUtil.getString("am.webclient.amazonperf.tooltip"));
/* 4729 */                         out.write(39);
/* 4730 */                         out.write(62);
/* 4731 */                         out.print(FormatUtil.getString("am.webclient.amazon.perfpolling.signature4.text"));
/* 4732 */                         out.write("</td>\n\t\t\t\t\t\t<td align=\"left\" valign=\"top\" width=\"70%\" title='");
/* 4733 */                         out.print(FormatUtil.getString("am.webclient.amazonperf.tooltip"));
/* 4734 */                         out.write(39);
/* 4735 */                         out.write(62);
/* 4736 */                         if (_jspx_meth_html_005fcheckbox_005f36(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4738 */                         out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/* 4739 */                         out.write("\n   \t\t       </table>\n\t\t</div>\n\t\t<div id=\"webservices\" style=\"display:none;\">\n\t\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t    <tr >\n\t\t     ");
/*      */                         
/* 4741 */                         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 4742 */                         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 4743 */                         _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4745 */                         _jspx_th_bean_005fdefine_005f1.setId("webServicesOperationTime");
/*      */                         
/* 4747 */                         _jspx_th_bean_005fdefine_005f1.setName("AMActionForm");
/*      */                         
/* 4749 */                         _jspx_th_bean_005fdefine_005f1.setProperty("webServicesOperationTime");
/*      */                         
/* 4751 */                         _jspx_th_bean_005fdefine_005f1.setType("java.lang.Integer");
/* 4752 */                         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 4753 */                         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 4754 */                           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */                         }
/*      */                         
/* 4757 */                         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 4758 */                         Integer webServicesOperationTime = null;
/* 4759 */                         webServicesOperationTime = (Integer)_jspx_page_context.findAttribute("webServicesOperationTime");
/* 4760 */                         out.write("\n\t\t      \t<td align=\"left\" height=\"22\" width=\"100%\" class=\"bodytext\">\n      \t\t\t\t");
/* 4761 */                         out.print(FormatUtil.getString("am.webclient.webservice.perfpolling.content", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name"), "<input type=text class=formtext name=webServicesOperationTime maxlength=6 size=6 value=" + webServicesOperationTime + ">", FormatUtil.getString("am.reporttab.footer.messagemillisec.text") }));
/* 4762 */                         out.write("\n      \t\t\t</td>\n\t\t    </tr>\n\t\t   </table>\n\t\t</div>\n\t\t<div id=\"ping\"  style=\"display:none;padding-top:8px;\">\n\t\t<table width=\"100%\" cellpadding=\"1\" cellspacing=\"0\" border=\"0\">\n\t\t    <tr>\n\t\t      \t");
/*      */                         
/* 4764 */                         DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 4765 */                         _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 4766 */                         _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 4768 */                         _jspx_th_bean_005fdefine_005f2.setId("pingPackToSend");
/*      */                         
/* 4770 */                         _jspx_th_bean_005fdefine_005f2.setName("AMActionForm");
/*      */                         
/* 4772 */                         _jspx_th_bean_005fdefine_005f2.setProperty("pingPackToSend");
/*      */                         
/* 4774 */                         _jspx_th_bean_005fdefine_005f2.setType("java.lang.Integer");
/* 4775 */                         int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 4776 */                         if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 4777 */                           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */                         }
/*      */                         
/* 4780 */                         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 4781 */                         Integer pingPackToSend = null;
/* 4782 */                         pingPackToSend = (Integer)_jspx_page_context.findAttribute("pingPackToSend");
/* 4783 */                         out.write("\n\t\t      \t<td align=\"left\" height=\"22\" width=\"100%\" class=\"bodytext\">\n      \t\t\t\t");
/* 4784 */                         out.print(FormatUtil.getString("am.webclient.ping.perfpolling.content", new String[] { "<input type=text class=formtext name=pingPackToSend maxlength=1 size=1 value=" + pingPackToSend + ">" }));
/* 4785 */                         out.write("\n      \t\t\t</td>\n      \t\t\t</tr>\n      \t\t\t<tr>\n      \t\t\t<td align=\"left\" height=\"22\" width=\"100%\" class=\"bodytext\">\n      \t\t\t  <table width=\"50%\" cellpadding=\"1\" cellspacing=\"0\" border=\"0\">\n      \t\t\t  <tr>\n      \t\t\t  <td width=\"30%\"  class=\"bodytext\" height=\"21\">");
/* 4786 */                         out.print(FormatUtil.getString("am.pingtest.command"));
/* 4787 */                         out.write("</td>\n\t\t\t\t  <td width=\"5%\"  class=\"bodytext\">:</td>\n\t\t\t\t  <td class=\"bodytext\"><input type=\"text\"  value='");
/* 4788 */                         out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.pingtest.command"));
/* 4789 */                         out.write("' name=\"am.pingtest.command\" style=\"position:relative;\" /></td>\n      \t\t\t  </tr>\n      \t\t\t  \n      \t\t\t  </table>\n      \t\t\t</td>\n\t\t    </tr>\n\t\t   </table>\n\t\t</div>\n\t\t\n\t   ");
/*      */                         
/* 4791 */                         String disableWebapp = request.getParameter("wlswebapp");
/* 4792 */                         String disableEjb = request.getParameter("wlsejb");
/* 4793 */                         String disableServlet = request.getParameter("wlsservlet");
/* 4794 */                         com.adventnet.appmanager.db.AMConnectionPool pool = com.adventnet.appmanager.db.AMConnectionPool.getInstance();
/* 4795 */                         String wlsquery = "select RESOURCEID from AM_ManagedObject where type='WEBLOGIC-server'";
/* 4796 */                         java.sql.ResultSet similarMonitorSet = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(wlsquery);
/* 4797 */                         boolean enablecheckbox = similarMonitorSet.next();
/* 4798 */                         similarMonitorSet.close();
/* 4799 */                         boolean isWebEdit = false;
/* 4800 */                         boolean isEjbEdit = false;
/* 4801 */                         boolean isServletEdit = false;
/* 4802 */                         if (enablecheckbox)
/*      */                         {
/* 4804 */                           wlsquery = "select distinct(ENABLEWEBAPP) from AM_WLS_DISABLE_STATS where ENABLEWEBAPP=0";
/* 4805 */                           similarMonitorSet = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(wlsquery);
/* 4806 */                           isWebEdit = similarMonitorSet.next();
/* 4807 */                           similarMonitorSet.close();
/* 4808 */                           wlsquery = "select distinct(ENABLEEJB) from AM_WLS_DISABLE_STATS where ENABLEEJB=1";
/* 4809 */                           similarMonitorSet = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(wlsquery);
/* 4810 */                           isEjbEdit = similarMonitorSet.next();
/* 4811 */                           similarMonitorSet.close();
/* 4812 */                           wlsquery = "select distinct(ENABLESERVLET) from AM_WLS_DISABLE_STATS where ENABLESERVLET=1";
/* 4813 */                           similarMonitorSet = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(wlsquery);
/* 4814 */                           isServletEdit = similarMonitorSet.next();
/* 4815 */                           similarMonitorSet.close();
/*      */                         }
/*      */                         
/* 4818 */                         out.write("\n\t\t <div id=\"weblogic\" style=\"display:none;\">\n\n\t\t \t<table border=\"0\"  cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n              \t\t <tr>\n\n\t\t\t <td height=\"30\" class=\"bodytext\">\n\n\t\t\t ");
/* 4819 */                         if (enablecheckbox)
/*      */                         {
/*      */ 
/* 4822 */                           if (((disableWebapp != null) && (disableWebapp.equals("disablewebapp"))) || (isWebEdit)) {
/* 4823 */                             out.write("\n\t\t\t\t\t <input id=\"wlswebappid\"  type=\"checkbox\" name=\"wlswebapp\" value=\"disablewebapp\" checked onclick=\"javascript:disableWebApp(this);\">\n\t\t\t\t ");
/*      */                           } else {
/* 4825 */                             out.write("\n\t\t\t      \t <input  id=\"wlswebappid\"  type=\"checkbox\" name=\"wlswebapp\" value=\"disablewebapp\" onclick=\"javascript:disableWebApp(this);\">\n\t\t\t\t\t ");
/*      */                           }
/*      */                         }
/*      */                         else
/*      */                         {
/* 4830 */                           out.write("\n\t\t\t\t      <table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\" class=\"messagebox\" align=\"center\">  <tr>\n\t\t\t\t\t <td width=\"100%\" class=\"message\" > ");
/* 4831 */                           out.print(FormatUtil.getString("am.webclient.nowlsmon.txt"));
/* 4832 */                           out.write("</td>\n\t\t\t\t       </tr>\n\t\t\t\t       </table>\n\t\t\t\t  </td>\n\t\t\t     \t  <tr>\n\n\t\t\t\t  <td height=\"30\" class=\"bodytext\">\n\t\t\t      \t <input  id=\"wlswebappid\"  type=\"checkbox\" name=\"wlswebapp\" value=\"disablewebapp\" disabled=true >\n\t\t\t\t");
/*      */                         }
/* 4834 */                         out.write("\n\t\t\t <span class='bodytext'>");
/* 4835 */                         out.print(FormatUtil.getString("am.webclient.disablewebapp.txt"));
/* 4836 */                         out.write("</span></td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<div id=\"processingmonitors\" style=\"display:none;\">\n\t\t\t<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" class=\"messagebox\" align=\"center\">\n                        <tr>\n                        <td width=\"94%\" class=\"message\" > ");
/* 4837 */                         out.print(FormatUtil.getString("am.webclient.configurealert.collectmonitors"));
/* 4838 */                         out.write(" </td>\n                        </tr>\n                 \t</table>\n\t\t\t</div>\n            <div id=\"wlswebappselect\" style=\"display:block;\">\n\t\t\t</div>\n\t\t\t");
/* 4839 */                         if (((disableWebapp != null) && (disableWebapp.equals("disablewebapp"))) || (isWebEdit)) {
/* 4840 */                           out.write("\n\t\t\t <script> enableWebappCheckbox()</script>\n\t\t\t ");
/*      */                         }
/* 4842 */                         out.write("\n\t\t \t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" cellpadding=\"5\" width=\"100%\">\n              \t\t <tr>\n\n\n\t\t\t <td height=\"30\" class=\"bodytext\">\n\t\t\t ");
/*      */                         
/* 4844 */                         if (enablecheckbox)
/*      */                         {
/*      */ 
/* 4847 */                           if (((disableEjb != null) && (disableEjb.equals("disableejb"))) || (isEjbEdit)) {
/* 4848 */                             out.write("\n\t\t\t\t\t <input id=\"wlsejbid\"  type=\"checkbox\" name=\"wlsejb\" value=\"disableejb\" checked onclick=\"javascript:disableEjb(this);\">\n\t\t\t\t ");
/*      */                           } else {
/* 4850 */                             out.write("\n\t               \t <input id=\"wlsejbid\"  type=\"checkbox\" name=\"wlsejb\" value=\"disableejb\" onclick=\"javascript:disableEjb(this);\">\n\t\t\t ");
/*      */                           }
/*      */                           
/*      */ 
/*      */                         }
/*      */                         else {
/* 4856 */                           out.write("\n\t\t\t      \t <input  id=\"wlsejbid\"  type=\"checkbox\" name=\"wlsejb\" value=\"disableejb\" disabled=true >\n\n\t\t\t");
/*      */                         }
/* 4858 */                         out.write("\n\t\t\t <span class='bodytext'>");
/* 4859 */                         out.print(FormatUtil.getString("am.webclient.disableejb.txt"));
/* 4860 */                         out.write("</span></td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<div id=\"wlsejbselect\" style=\"display:block;\">\n\t\t\t</div>\n\t\t\t ");
/* 4861 */                         if (((disableEjb != null) && (disableEjb.equals("disableejb"))) || (isEjbEdit)) {
/* 4862 */                           out.write("\n\t\t\t <script> enableEjbCheckbox()</script>\n\t\t\t ");
/*      */                         }
/* 4864 */                         out.write("\n\t\t \t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" cellpadding=\"5\" width=\"100%\">\n              \t\t <tr>\n\n\t\t\t <td height=\"30\" class=\"bodytext\">\n\t\t\t ");
/*      */                         
/* 4866 */                         if (enablecheckbox)
/*      */                         {
/* 4868 */                           if (((disableServlet != null) && (disableServlet.equals("disableservlet"))) || (isServletEdit)) {
/* 4869 */                             out.write("\n\t\t\t\t\t <input  id=\"wlsservletid\"  type=\"checkbox\" name=\"wlsservlet\" value=\"disableservlet\" checked onclick=\"javascript:disableServlet(this);\">\n\t\t\t\t ");
/*      */                           } else {
/* 4871 */                             out.write("\n\t               \t <input  id=\"wlsservletid\"  type=\"checkbox\" name=\"wlsservlet\" value=\"disableservlet\" onclick=\"javascript:disableServlet(this);\">\n\t\t\t ");
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         else {
/* 4876 */                           out.write("\n\t\t\t      \t <input  id=\"wlsejbid\"  type=\"checkbox\" name=\"wlsejb\" value=\"disableejb\" disabled=true >\n\t\t\t ");
/*      */                         }
/* 4878 */                         out.write("\n\t\t\t <span class='bodytext'>");
/* 4879 */                         out.print(FormatUtil.getString("am.webclient.disableservlet.txt"));
/* 4880 */                         out.write("</span></td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<div id=\"wlsservletselect\" style=\"display:block;\">\n\t\t\t</div>\n\t\t\t ");
/* 4881 */                         if (((disableServlet != null) && (disableServlet.equals("disableservlet"))) || (isServletEdit)) {
/* 4882 */                           out.write("\n\t\t\t<script> enableServletCheckbox()</script>\n\t\t\t");
/*      */                         }
/* 4884 */                         out.write("\n\n</div>\n\t</td>\n\t   </tr>\n    ");
/*      */ 
/*      */                       }
/* 4887 */                       else if (type.equals("unmanage"))
/*      */                       {
/* 4889 */                         java.util.ArrayList manage_unmanage = (java.util.ArrayList)request.getAttribute("manage_unmanage");
/*      */                         
/* 4891 */                         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n    <tr>\n      <td width=\"72%\" height=\"31\" class=\"tableheading\">");
/* 4892 */                         out.print(FormatUtil.getString("am.webclient.maintenance.monitorsdetails"));
/* 4893 */                         out.write("</td>\n    </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrbborder itadmin-no-decor\">\n\t<tr>\n\t<td width=\"46%\" align=\"center\" class=\"bodytext\">");
/* 4894 */                         out.print(FormatUtil.getString("am.webclient.maintenance.unassociatedmonitors"));
/* 4895 */                         out.write("</td>\n\t<td width=\"8%\">&nbsp;</td>\n\t<td width=\"46%\" align=\"center\" class=\"bodytext\">");
/* 4896 */                         out.print(FormatUtil.getString("am.webclient.maintenance.associatedmonitors"));
/* 4897 */                         out.write("</td>\n\t</tr>\n\t<tr>\n\t<td width=\"46%\">\n\t  ");
/* 4898 */                         if (_jspx_meth_html_005fselect_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4900 */                         out.write("\n\t</td>\n\t<td width=\"8%\" align=\"center\" class=\"bodytext\">\n\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t  <tr>\n\t\t\t<td><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"javascript:fnAddToRightCombo(document.AMActionForm.maintenanceCombo1,document.AMActionForm.maintenanceCombo2),fnRemoveFromRightCombo(document.AMActionForm.maintenanceCombo1);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t\t<td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t\t<td><input name=\"AddButton2\" type=\"button\" class=\"buttons btn_highlt\" onclick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.maintenanceCombo1),fnAddToRightCombo(document.AMActionForm.maintenanceCombo1,document.AMActionForm.maintenanceCombo2),fnRemoveFromRightCombo(document.AMActionForm.maintenanceCombo1);\" value=\"&gt;&gt;\"></td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t\t<td><img src=\"../images/spacer.gif\" height=\"24\" width=\"5\"></td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t\t<td><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onclick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.maintenanceCombo2),fnAddToRightCombo(document.AMActionForm.maintenanceCombo2,document.AMActionForm.maintenanceCombo1),fnRemoveFromRightCombo(document.AMActionForm.maintenanceCombo2);\" value=\"&lt;&lt;\"></td>\n");
/* 4901 */                         out.write("\t\t  </tr>\n\t\t  <tr>\n\t\t\t<td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t\t<td><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onclick=\"javascript:fnAddToRightCombo(document.AMActionForm.maintenanceCombo2,document.AMActionForm.maintenanceCombo1),fnRemoveFromRightCombo(document.AMActionForm.maintenanceCombo2);\" value=\"&nbsp;&lt;&nbsp;\"></td>\n\t\t  </tr>\n        </table>\n    </td>\n\t<td width=\"46%\">\n\t  ");
/* 4902 */                         if (_jspx_meth_html_005fselect_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 4904 */                         out.write("\n\t</td>\n\t</tr>\n</table>\n\n");
/*      */                       }
/*      */                       
/*      */ 
/* 4908 */                       out.write("\n  </table>\n\t\n");
/* 4909 */                       if ((type != null) && (!type.equalsIgnoreCase("jsonfeed"))) {
/* 4910 */                         out.write("\n        <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  class=\"lrbborder itadmin-no-decor\">\n  <tr>\n  <td height=\"28\" colspan=\"3\" align=\"center\" class=\"tablebottom itadmin-no-decor\"><input type=\"button\" name=\"sub_button\" value=\"");
/* 4911 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 4912 */                         out.write("\" class=\"buttons btn_highlt\" onclick=\"javascript:validateAndSubmit()\">\n      <input type=\"reset\" name=\"reset\" value=\"");
/* 4913 */                         out.print(FormatUtil.getString("am.webclient.global.Reset.text"));
/* 4914 */                         out.write("\"  class=\"buttons btn_reset\">  <input type=\"button\" name=\"Submit3\" value=\"");
/* 4915 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 4916 */                         out.write("\" onclick=\"history.back();\" class=\"buttons btn_link\">\n  </td>\n</tr>\n</table>\n\n");
/*      */                       }
/* 4918 */                       out.write("\n</div>\t\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/*      */                       
/* 4920 */                       if (type.equals("performance"))
/*      */                       {
/* 4922 */                         String note = "";
/* 4923 */                         if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) {
/* 4924 */                           note = FormatUtil.getString("am.admin.optimizeDC.helpCard.note");
/*      */                         }
/*      */                         
/* 4927 */                         out.write("\n<div id=\"performanceHelp\" style=\"display:none\">\n\t");
/* 4928 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.performancepolling.helpcard")).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4929 */                         out.write("\t\n</div>\n<div id=\"diskioHelp\" style=\"display:none\">\n\t");
/* 4930 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.diskio.helpcard")).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4931 */                         out.write("\n</div>\n<div id=\"oracleHelp\" style=\"display:none\">\n\t");
/* 4932 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.oracledisablecollection.helpcard")).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4933 */                         out.write("\n</div>\n<div id=\"weblogicHelp\" style=\"display:none\">\n\t");
/* 4934 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.wlsdisablecollection.helpcard")).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4935 */                         out.write("\n</div>\n<div id=\"mssqlHelp\" style=\"display:none\">\n\t");
/* 4936 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.mssqldisablecollection.helpcard")).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4937 */                         out.write("\n</div>\n<div id=\"mysqlHelp\" style=\"display:none\">\n\t");
/* 4938 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.mysqlChkTableStatus.helpcard")), request.getCharacterEncoding()), out, false);
/* 4939 */                         out.write("\n</div>\n<div id=\"snmpHelp\" style=\"display:none\">\n\t");
/* 4940 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.snmpversion.helpcard")).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4941 */                         out.write("\n</div>\n<div id=\"urlHelp\" style=\"display:none\">\n\t");
/* 4942 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.httpurl.helpcard", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name"), com.adventnet.appmanager.util.OEMUtil.getOEMString("product.homedirectory.name") })).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4943 */                         out.write("\n</div>\n<div id=\"amazonHelp\" style=\"display:none\">\n\t");
/* 4944 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.amazonChkS3BucketsStatus.helpcard")).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4945 */                         out.write("\n</div>\n<div id=\"webservicesHelp\" style=\"display:none\">\n\t");
/* 4946 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.webservice.perfpolling.helpcard", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") })).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4947 */                         out.write("\n</div>\n<div id=\"pingHelp\" style=\"display:none\">\n\t");
/* 4948 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(new StringBuilder().append(FormatUtil.getString("am.webclient.ping.perfpolling.helpcard", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") })).append(note).toString()), request.getCharacterEncoding()), out, false);
/* 4949 */                         out.write("\n</div>\n"); }
/* 4950 */                       if (type.equals("Availablity"))
/*      */                       {
/* 4952 */                         out.write("\n<div id=\"AvailabilityHelp\" style=\"display:none\">\n\t");
/* 4953 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.availabilitysettings.helpcard")), request.getCharacterEncoding()), out, false);
/* 4954 */                         out.write("\n</div>\n"); }
/* 4955 */                       if (type.equals("Actionalert")) {
/* 4956 */                         out.write("\n<div id=\"ActionalertHelp\" style=\"display:none\">\n\t");
/* 4957 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.Actionalertsettings.helpcard")), request.getCharacterEncoding()), out, false);
/* 4958 */                         out.write("\n</div>\n"); }
/* 4959 */                       if (type.equals("general")) {
/* 4960 */                         out.write("\n<div id=\"GlobalHelp\" style=\"display:none\">\n\t");
/* 4961 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.globalsettings.helpcard")), request.getCharacterEncoding()), out, false);
/* 4962 */                         out.write("\n</div>\n"); }
/* 4963 */                       if (type.equalsIgnoreCase("logging")) {
/* 4964 */                         out.write("\n<div id=\"LoggingHelp\" style=\"display:none\">\n\t");
/* 4965 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.logging.helpcard")), request.getCharacterEncoding()), out, false);
/* 4966 */                         out.write("\n</div>\n"); }
/* 4967 */                       if (type.equalsIgnoreCase("jsonfeed")) {
/* 4968 */                         out.write("\n<div id=\"JsonHelp\" style=\"display:none\">\n\t");
/* 4969 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.jsonfeed.helpcard")), request.getCharacterEncoding()), out, false);
/* 4970 */                         out.write("\n</div>\n"); }
/* 4971 */                       if (type.equalsIgnoreCase("gmapkey")) {
/* 4972 */                         out.write("\n<div id=\"GMapHelp\" style=\"display:none\">\n\t");
/* 4973 */                         JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.gmap.helpcard")), request.getCharacterEncoding()), out, false);
/* 4974 */                         out.write("\n</div>\n");
/* 4975 */                         if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 4976 */                           out.write("\n  \t </td>\n  \t </tr>\n  \t </table>\n");
/*      */                         }
/*      */                       }
/* 4979 */                       out.write("\n</td>\n</tr>\n</table>\n</div>\n <div id=\"Database\" style=\"display:none;\"> \n \t\t<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t\t<td>\t\n\t\t\t\t\t<div id=\"two\">\n\t\t\t\t\t\t<fieldset>\n\t\t\t\t\t\t\t<legend> &nbsp; ");
/* 4980 */                       out.print(FormatUtil.getString("am.monitortab.tableview.mysql.text"));
/* 4981 */                       out.write(" </legend> ");
/* 4982 */                       out.write("\t\t\t\t\t\t\n\t\t\t\t\t\t\t <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" >\n\t\t\t\t\t\t\t \t<tr>\t<td colspan=\"2\"><div class=\"bodytextbold\"> &nbsp;");
/* 4983 */                       out.print(FormatUtil.getString("am.webclient.performancepolling.mysql.enabledatacollection"));
/* 4984 */                       out.write("</div> ");
/* 4985 */                       out.write("\t</td></tr>\n\t\t\t\t\t\t\t    <tr>\t<td width=\"100%\" colspan=\"2\" class=\"tdindent\" align=\"left\"><span class=\"bodytext\">");
/* 4986 */                       if (_jspx_meth_html_005fradio_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 4988 */                       out.write("<span class=\"bodytext\">");
/* 4989 */                       out.print(FormatUtil.getString("am.webclient.global.mysql.everypolling.text"));
/* 4990 */                       out.write("</span>&nbsp;");
/* 4991 */                       if (_jspx_meth_html_005fradio_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 4993 */                       out.write("<span class=\"bodytext\">");
/* 4994 */                       out.print(FormatUtil.getString("am.webclient.global.mysql.onceaday.text"));
/* 4995 */                       out.write("</span>&nbsp;");
/* 4996 */                       if (_jspx_meth_html_005fradio_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 4998 */                       out.write("<span class=\"bodytext\">");
/* 4999 */                       out.print(FormatUtil.getString("am.webclient.global.mysql.neverpolling.text"));
/* 5000 */                       out.write("</span></span></td>\t</tr>\t\t\t\t\n\t\t\t\t\t\t\t\t<tr><td width=\"30%\"  class=\"tdindent\" align=\"left\">\n\t\t\t\t\t\t\t\t\t<table>\n\t\t\t\t\t\t\t    \t\t<tr><td class=\"bodytext\">");
/* 5001 */                       out.print(FormatUtil.getString("am.htmldata.mysql.daystoretain"));
/* 5002 */                       out.write(" : </td>\n\t\t\t\t\t\t\t    \t\t    <td><input type=\"text\" id=\"mysqldata\" value='");
/* 5003 */                       out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.htmldata.mysql.daystoretain"));
/* 5004 */                       out.write("' name=\"am.htmldata.mysql.daystoretain\" maxlength=\"10\" style=\"position:relative;\" />\n\t\t\t\t\t\t\t    \t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</fieldset>\n\t\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t\t<td width=\"35%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n\t\t\t\t\t<em>\n        \t    \t");
/* 5005 */                       out.print(FormatUtil.getString("am.webclient.mysql.tableStatus.heading"));
/* 5006 */                       out.write("\n\t\t\t\t\t</em> \t\n\t\t\t\t\t");
/* 5007 */                       out.print(FormatUtil.getString("am.webclient.mysql.tableStatus.helpcard"));
/* 5008 */                       out.write("\n\t\t\t\t\t</span>\n\t\t\t\t</td>\t\n\t\t\t</tr>\t\n \t\t </table>\n \t \n \t\t <table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t  <td>\t\n\t\t\t   <div id=\"two\">\n\t\t\t     <fieldset>\n\t\t\t      <legend> &nbsp; ");
/* 5009 */                       out.print(FormatUtil.getString("Sybase"));
/* 5010 */                       out.write(" </legend> ");
/* 5011 */                       out.write("\t\t\t\t\t\t\n\t\t\t     <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >\n\t\t\t       <tr><td colspan=\"2\"><div class=\"bodytextbold\"> &nbsp;");
/* 5012 */                       out.print(FormatUtil.getString("am.webclient.sybasedisablecollection.dbdetails"));
/* 5013 */                       out.write("</div> ");
/* 5014 */                       out.write("\t</td></tr>\t\n\t\t\t       <tr><td width=\"100%\" colspan=\"2\" align=\"left\"> </div><span style=\"padding-left:10px;\" class=\"bodytext\">");
/* 5015 */                       if (_jspx_meth_html_005fradio_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 5017 */                       out.write("<span class=\"bodytext\">");
/* 5018 */                       out.print(FormatUtil.getString("am.webclient.global.sybase.everypolling.text"));
/* 5019 */                       out.write("</span>&nbsp;");
/* 5020 */                       if (_jspx_meth_html_005fradio_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 5022 */                       out.write("<span class=\"bodytext\">");
/* 5023 */                       out.print(FormatUtil.getString("am.webclient.global.sybase.onceaday.text"));
/* 5024 */                       out.write("</span>&nbsp;");
/* 5025 */                       if (_jspx_meth_html_005fradio_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 5027 */                       out.write("<span class=\"bodytext\">");
/* 5028 */                       out.print(FormatUtil.getString("am.webclient.global.sybase.neverpolling.text"));
/* 5029 */                       out.write("</span></span></td>\t</tr>\n\t\t\t     </table>\n\t\t\t     </fieldset>\n\t\t\t   </div>\n\t\t\t  </td>\n\t\t\t  <td width=\"35%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n\t\t\t    <em>\n\t\t\t      ");
/* 5030 */                       out.print(FormatUtil.getString("am.webclient.sybasedisablecollection.heading"));
/* 5031 */                       out.write("\n\t\t\t    </em> \t\n\t\t\t       ");
/* 5032 */                       out.print(FormatUtil.getString("am.webclient.sybasedisablecollection.helpcard"));
/* 5033 */                       out.write("\n\t\t\t    </span>\n\t\t\t  </td>\t\n\t\t\t</tr>\t\n \t\t </table>\n \t\n \t<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t  <td>\t\n\t\t\t   <div id=\"two\">\n\t\t\t     <fieldset>\n\t\t\t      <legend> &nbsp; ");
/* 5034 */                       out.print(FormatUtil.getString("am.webclient.nonSQLrediscover.text"));
/* 5035 */                       out.write(" </legend> ");
/* 5036 */                       out.write("\t\t\t\t\t\t\n\t\t\t     <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >\n\t\t\t       <tr>\t<td width=\"35%\"  class=\"tdindent\" align=\"left\">\n\t\t\t\t\t<table>\n\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 5037 */                       out.print(FormatUtil.getString("am.nosql.rediscover.interval"));
/* 5038 */                       out.write(" : </td>\n\t\t\t\t\t\t\t<td><input type=\"text\"  value='");
/* 5039 */                       out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.nosql.rediscover.interval"));
/* 5040 */                       out.write("' id=\"nonSqlInterval\" name=\"am.nosql.rediscover.interval\" maxlength=\"10\" style=\"position:relative;\" /></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t   </td></tr>\n\t\t\t     </table>\n\t\t\t     </fieldset>\n\t\t\t   </div>\n\t\t\t  </td>\n\t\t\t  <td width=\"35%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n\t\t\t    <em>\n\t\t\t      ");
/* 5041 */                       out.print(FormatUtil.getString("am.webclient.nonSQLrediscover.heading"));
/* 5042 */                       out.write("\n\t\t\t    </em> \t\n\t\t\t       ");
/* 5043 */                       out.print(FormatUtil.getString("am.webclient.nonSQLrediscover.helpcard"));
/* 5044 */                       out.write("\n\t\t\t    </span>\n\t\t\t  </td>\t\n\t\t\t</tr>\t\n \t\t </table>\n\t\t<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t  <td>\t\n\t\t\t   <div id=\"two\">\n\t\t\t     <fieldset>\n\t\t\t      <legend> &nbsp; ");
/* 5045 */                       out.print(FormatUtil.getString("ORACLE-DB-server"));
/* 5046 */                       out.write(" </legend> ");
/* 5047 */                       out.write("\t\t\t\t\t\t\n\t\t\t     <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >\n\t\t\t       <tr>\t<td width=\"35%\"  class=\"tdindent\" align=\"left\">\n\t\t\t\t\t<table>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"100%\" colspan=\"2\" align=\"left\"><span class=\"bodytext tdindent\">");
/* 5048 */                       if (_jspx_meth_html_005fcheckbox_005f37(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 5050 */                       out.write("<span class=\"bodytext\">");
/* 5051 */                       out.print(FormatUtil.getString("am.oracle.scheduledjobs.failed"));
/* 5052 */                       out.write(" </span></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t   </td></tr>\n\t\t\t     </table>\n\t\t\t     </fieldset>\n\t\t\t   </div>\n\t\t\t  </td>\n\t\t\t  <td width=\"35%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n\t\t\t    <em>\n\t\t\t      ");
/* 5053 */                       out.print(FormatUtil.getString("am.webclient.oraclefailedbackupjobs.heading"));
/* 5054 */                       out.write("\n\t\t\t    </em> \t\n\t\t\t       ");
/* 5055 */                       out.print(FormatUtil.getString("am.webclient.oraclefailedbackupjobs.helpcard"));
/* 5056 */                       out.write("\n\t\t\t    </span>\n\t\t\t  </td>\t\n\t\t\t</tr>\t\n \t\t</table>\n \t\n \t\t <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n \t\t \t<tbody><tr>\n\t\t\t\t<td width=\"60%\" align=\"center\">\t\t       \n\t\t          <input type=\"button\" onclick=\"javascript:validateAndSubmit();\" class=\"buttons btn_highlt\" value=\"");
/* 5057 */                       out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 5058 */                       out.write("\" name=\"sub_button\">\t\t          \n\t\t\t\t\t &nbsp;&nbsp;<input type=\"reset\" class=\"buttons btn_reset\" value=\"");
/* 5059 */                       out.print(FormatUtil.getString("am.webclient.global.Reset.text"));
/* 5060 */                       out.write("\" name=\"reset\">  &nbsp;&nbsp;<input type=\"button\" class=\"buttons btn_link\" onclick=\"history.back();\" value=\"");
/* 5061 */                       out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 5062 */                       out.write("\" name=\"Submit3\">\n\t\t\t\t</td>\n\t\t\t\t<td><img width=\"10\" height=\"2\" src=\"/images/spacer.gif\"></td>\n\t\t\t</tr></tbody>\n\t\t</table>\n\t </div>\n");
/* 5063 */                       int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5064 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5068 */                   if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5069 */                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                   }
/*      */                   
/* 5072 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fscope_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5073 */                   out.write("\n<div id=\"configureMSSQL\" style=\"display:none\">\n\t");
/* 5074 */                   if (!"CLOUD".equals((String)request.getAttribute("productEdition"))) {
/* 5075 */                     out.write("\n\t<form id=\"MSSQLForm\" name=\"MSSQLForm\" action=\"adminAction.do\" style=\"display:inline;\">\n\t\t<input type=\"hidden\" id=\"method\" name=\"method\" value=\"saveMSSQLGlobalSettingsConfiguration\"/>\n\t\t<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t\t<td>");
/*      */                     
/* 5077 */                     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5078 */                     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 5079 */                     _jspx_th_c_005fset_005f9.setParent(_jspx_th_tiles_005fput_005f1);
/*      */                     
/* 5081 */                     _jspx_th_c_005fset_005f9.setVar("mssqldeleteJobs");
/* 5082 */                     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 5083 */                     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 5084 */                       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 5085 */                         out = _jspx_page_context.pushBody();
/* 5086 */                         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 5087 */                         _jspx_th_c_005fset_005f9.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/* 5090 */                         out.print(com.adventnet.appmanager.util.DBUtil.getServerConfigValue("am.mssql.deleteJobs"));
/* 5091 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 5092 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 5095 */                       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 5096 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 5099 */                     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 5100 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                     }
/*      */                     
/* 5103 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 5104 */                     out.write("\n\t\t\t\t\t\t<div id=\"two\">\n\t\t\t\t\t\t\t<fieldset>\n\t\t\t\t\t\t\t\t<legend> &nbsp;");
/* 5105 */                     out.print(FormatUtil.getString("am.monitortab.tableview.mssql.text"));
/* 5106 */                     out.write("</legend> ");
/* 5107 */                     out.write("\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"5\" >\n\t\t\t\t\t\t\t\t\t<tr><td colspan=\"2\"><div class=\"bodytextbold\"> &nbsp;");
/* 5108 */                     out.print(FormatUtil.getString("am.webclient.performancepolling.diskiotext"));
/* 5109 */                     out.write("</div> ");
/* 5110 */                     out.write("\t</td></tr>\n\t\t\t\t\t\t\t\t\t<tr><td width=\"100%\" colspan=\"2\" align=\"left\"><span class=\"bodytext tdindent\"><input type=\"checkbox\" name=\"mssqlScheduledJobs\" ");
/* 5111 */                     if (_jspx_meth_c_005fif_005f16(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
/*      */                       return;
/* 5113 */                     out.write("/><span class=\"bodytext\">");
/* 5114 */                     out.print(FormatUtil.getString("am.webclient.mssqldisablecollection.jobs.text"));
/* 5115 */                     out.write(" </span></td>\t</tr>\t\t\t\t\n\t\t\t\t\t\t\t\t\t<tr><td width=\"100%\" colspan=\"2\" align=\"left\"><span class=\"bodytext tdindent\"><input id=\"cb81\" name=\"am.mssql.deleteJobs\" type=\"hidden\" value=\"false\"/><input type=\"checkbox\" value='");
/* 5116 */                     if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
/*      */                       return;
/* 5118 */                     out.write("' id=\"cb82\" style=\"position:relative;\" ");
/* 5119 */                     if (_jspx_meth_c_005fif_005f17(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
/*      */                       return;
/* 5121 */                     out.write("/><span class=\"bodytext\">");
/* 5122 */                     out.print(FormatUtil.getString("am.mssql.deleteJobs"));
/* 5123 */                     out.write(" </span></td></tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"5\">\n\t\t\t\t\t\t\t\t\t<tr><td colspan=\"2\"><img src=\"images/gray_line1.gif\" width=\"100%\" height=\"1\"></td></tr>\n\t\t\t\t\t\t\t\t\t<tr><td colspan=\"2\"><div class=\"bodytextbold\"> &nbsp;");
/* 5124 */                     out.print(FormatUtil.getString("BACKUP"));
/* 5125 */                     out.write("</div> ");
/* 5126 */                     out.write("\t</td></tr>\n\t\t\t\t\t\t\t\t\t<tr><td width=\"100%\" colspan=\"2\" align=\"left\"><span class=\"bodytext tdindent\">");
/* 5127 */                     out.print(FormatUtil.getString("am.webclient.performancetext.collectbackup"));
/* 5128 */                     out.write("&nbsp;<input type=\"text\" name=\"backupCollectionPeriod\" size=\"3\" value='");
/* 5129 */                     if (_jspx_meth_c_005fout_005f10(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
/*      */                       return;
/* 5131 */                     out.write("'/>&nbsp;");
/* 5132 */                     out.print(FormatUtil.getString("am.webclient.sqldbm.performancepolling.dayscount.text"));
/* 5133 */                     out.write("</span></td></tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"5\" >\n\t\t\t\t\t\t\t\t\t<tr><td colspan=\"2\"><img src=\"images/gray_line1.gif\" width=\"100%\" height=\"1\"></td></tr>\n\t\t\t\t\t\t\t\t\t<tr><td class=\"bodytextbold\">");
/* 5134 */                     out.print(FormatUtil.getString("am.webclient.performancetext.replications.text"));
/* 5135 */                     out.write("</td></tr>\n\t\t\t\t\t\t\t\t\t<tr><td width=\"100%\" colspan=\"2\" align=\"left\"><span class=\"bodytext tdindent\"><input type=\"checkbox\" name=\"sqlReplMonStatus\" id=\"sqlReplMonStatus\" ");
/* 5136 */                     if (_jspx_meth_c_005fif_005f18(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
/*      */                       return;
/* 5138 */                     out.write(" onchange=\"javascript:replMonChange();\"/>&nbsp;");
/* 5139 */                     out.print(FormatUtil.getString("am.webclient.sqldbmanager.db.replication.enable.text"));
/* 5140 */                     out.write("</td></tr>");
/* 5141 */                     out.write("\n\t\t\t\t\t\t\t\t\t<tr><td width=\"100%\" colspan=\"2\" align=\"left\"><span class=\"bodytext tdindent\"><input type=\"checkbox\" name=\"sqlReplMonHistoryStatus\" id=\"sqlReplMonHistoryStatus\" ");
/* 5142 */                     if (_jspx_meth_c_005fif_005f19(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
/*      */                       return;
/* 5144 */                     out.write(" onchange=\"javascript:replHistChange();\"/>&nbsp;");
/* 5145 */                     out.print(FormatUtil.getString("am.webclient.sqldbmanager.db.replicationhistory.enable.text"));
/* 5146 */                     out.write("</td></tr>");
/* 5147 */                     out.write("\n\t\t\t\t\t\t\t\t\t<tr id=\"replHist\"><td width=\"100%\" colspan=\"2\" align=\"left\"><span class=\"bodytext tdindent\">");
/* 5148 */                     out.print(FormatUtil.getString("am.webclient.performancetext.sqlreplagenthistory.retention.text"));
/* 5149 */                     out.write("<span class=\"mandatory\">*</span>&nbsp;<input type=\"text\" name=\"replAgentHistoryMaintenancePeriod\" id=replAgentHistoryMaintenancePeriod\" size=\"3\" value='");
/* 5150 */                     if (_jspx_meth_c_005fout_005f11(_jspx_th_tiles_005fput_005f1, _jspx_page_context))
/*      */                       return;
/* 5152 */                     out.write("'/>&nbsp;");
/* 5153 */                     out.print(FormatUtil.getString("am.webclient.sqldbm.performancepolling.dayscount.text"));
/* 5154 */                     out.write("</td></tr>\t\t   \n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</fieldset>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"30%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\" style=\"width: 86%;\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n\t\t\t\t\t\t<em>\n\t\t\t\t\t\t");
/* 5155 */                     out.print(FormatUtil.getString("am.webclient.mssql.jobs.heading"));
/* 5156 */                     out.write("\n\t\t\t\t\t\t</em> \t\n\t\t\t\t\t\t");
/* 5157 */                     out.print(FormatUtil.getString("am.webclient.mssql.jobs.helpcard"));
/* 5158 */                     out.write("\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\t\n\t\t\t </table>\n\t\t\t  <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t<tbody><tr>\n\t\t\t\t\t<td width=\"66%\" align=\"center\">\t\t       \n\t\t\t\t\t  <input type=\"button\" onclick=\"javascript:submitMSSQLForm();\" class=\"buttons btn_highlt\" value=\"");
/* 5159 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 5160 */                     out.write("\" name=\"submitbutton\">\t\t          \n\t\t\t\t\t\t &nbsp;&nbsp;<input type=\"reset\" class=\"buttons btn_reset\" value=\"");
/* 5161 */                     out.print(FormatUtil.getString("am.webclient.global.Reset.text"));
/* 5162 */                     out.write("\" name=\"resetbutton\">  &nbsp;&nbsp;<input type=\"button\" class=\"buttons btn_link\" onclick=\"history.back();\" value=\"");
/* 5163 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 5164 */                     out.write("\" name=\"cancelbutton\">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td><img width=\"10\" height=\"2\" src=\"/images/spacer.gif\"></td>\n\t\t\t\t</tr></tbody>\n\t\t</table>\n\t\t</form>\n\t\t");
/*      */                   }
/* 5166 */                   out.write("\n\t<br/>\n\t<br/>\n\t<div id=\"configureDCMSSQL\">\n\t</div>\n</div> \n<div id=\"configureDC\" style=\"display:none;\"></div>\n\n");
/* 5167 */                   int evalDoAfterBody = _jspx_th_tiles_005fput_005f1.doAfterBody();
/* 5168 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 5171 */                 if (_jspx_eval_tiles_005fput_005f1 != 1) {
/* 5172 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 5175 */               if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5176 */                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f1); return;
/*      */               }
/*      */               
/* 5179 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f1);
/* 5180 */               out.write("  \n\n");
/* 5181 */               if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                 return;
/* 5183 */               out.write("\n    ");
/* 5184 */               if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                 return;
/* 5186 */               out.write(10);
/* 5187 */               int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5188 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 5192 */           if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5193 */             this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */           }
/*      */           else {
/* 5196 */             this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5197 */             out.write("\n\n<script>\nfunction doInitStuffOnBodyLoad(){\n\tif(jQuery(\".chosenselect\").length>0){\n\tjQuery(\".chosenselect\").chosen();\t\t// NO I18N\t\n\t}\n           if(");
/* 5198 */             out.print(com.adventnet.appmanager.util.Constants.getSenderrormail().equals("true"));
/* 5199 */             out.write("){\n\t               if(document.AMActionForm.errorpollCount){\n                                      document.AMActionForm.errorpollCount.disabled = false;\n\t              }\n\n           }\n           else{\n\t            if(document.AMActionForm.errorpollCount){\n                                    document.AMActionForm.errorpollCount.disabled = true;\n\t       \t\t}\n\n        \t}\n       ");
/*      */             
/* 5201 */             if ((fromwhere != null) && (fromwhere.equals("viewthreshold")))
/*      */             {
/* 5203 */               out.write("\n  \t\t\t//document.location=#configurepoll;\n  \t\t\twindow.location.hash=\"configurepoll\"; //No I18N\n  \t\t");
/*      */             }
/*      */             
/* 5206 */             if ((type != null) && ((type.equals("Availablity")) || (type.equals("performance"))))
/*      */             {
/* 5208 */               out.write("\n        \tmyOnLoad();\n        "); }
/* 5209 */             if ((type != null) && (type.equals("Actionalert")))
/*      */             {
/* 5211 */               out.write("\n        \t\tjavascript:showDiv('ActionalertHelp');\n        "); }
/* 5212 */             if ((type != null) && (type.equals("general")))
/* 5213 */               out.write("\n     \t\t   javascript:showDiv('GlobalHelp');\n        ");
/* 5214 */             if ((type != null) && (type.equalsIgnoreCase("logging")))
/* 5215 */               out.write("\n       \t\t javascript:showDiv(\"LoggingHelp\");\n        ");
/* 5216 */             if (type.equalsIgnoreCase("jsonfeed"))
/* 5217 */               out.write("\n        \tjavascript:showDiv(\"JsonHelp\");        \n\t\t");
/* 5218 */             if (type.equalsIgnoreCase("gmapkey")) {
/* 5219 */               out.write("\n\t\t\tjavascript:showDiv(\"GMapHelp\");        \n\t\t");
/*      */             }
/* 5221 */             out.write("\n\t\t\n}\nif($(\"input[name='hostHwMonitoring']\") && $(\"input[name='hostHwMonitoring']:checked\").val()==\"disable\") {\t//NO I18N\n\thideHWTable();\n}\n\n\n</script>\n");
/*      */           }
/* 5223 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5224 */         out = _jspx_out;
/* 5225 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5226 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5227 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5230 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5236 */     PageContext pageContext = _jspx_page_context;
/* 5237 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5239 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5240 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 5241 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 5243 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 5244 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 5245 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 5247 */         out.write("\n\t\talertUser();\n\t");
/* 5248 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 5249 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5253 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 5254 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5255 */       return true;
/*      */     }
/* 5257 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5258 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5263 */     PageContext pageContext = _jspx_page_context;
/* 5264 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5266 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5267 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 5268 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 5270 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 5271 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 5272 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 5274 */         out.write("\nalertUser();\n");
/* 5275 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 5276 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5280 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 5281 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5282 */       return true;
/*      */     }
/* 5284 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5285 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5290 */     PageContext pageContext = _jspx_page_context;
/* 5291 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5293 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 5294 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5295 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/*      */     
/* 5297 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 5299 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 5300 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5301 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5302 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5303 */       return true;
/*      */     }
/* 5305 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5311 */     PageContext pageContext = _jspx_page_context;
/* 5312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5314 */     MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5315 */     _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 5316 */     _jspx_th_html_005fmultibox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5318 */     _jspx_th_html_005fmultibox_005f0.setProperty("gettingstarted");
/*      */     
/* 5320 */     _jspx_th_html_005fmultibox_005f0.setValue("true");
/*      */     
/* 5322 */     _jspx_th_html_005fmultibox_005f0.setStyle("position:relative;");
/* 5323 */     int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 5324 */     if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 5325 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 5326 */       return true;
/*      */     }
/* 5328 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 5329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5334 */     PageContext pageContext = _jspx_page_context;
/* 5335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5337 */     MultiboxTag _jspx_th_html_005fmultibox_005f1 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5338 */     _jspx_th_html_005fmultibox_005f1.setPageContext(_jspx_page_context);
/* 5339 */     _jspx_th_html_005fmultibox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5341 */     _jspx_th_html_005fmultibox_005f1.setProperty("loginTopLinks");
/*      */     
/* 5343 */     _jspx_th_html_005fmultibox_005f1.setValue("true");
/*      */     
/* 5345 */     _jspx_th_html_005fmultibox_005f1.setStyle("position:relative;");
/* 5346 */     int _jspx_eval_html_005fmultibox_005f1 = _jspx_th_html_005fmultibox_005f1.doStartTag();
/* 5347 */     if (_jspx_th_html_005fmultibox_005f1.doEndTag() == 5) {
/* 5348 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1);
/* 5349 */       return true;
/*      */     }
/* 5351 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1);
/* 5352 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5357 */     PageContext pageContext = _jspx_page_context;
/* 5358 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5360 */     MultiboxTag _jspx_th_html_005fmultibox_005f2 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5361 */     _jspx_th_html_005fmultibox_005f2.setPageContext(_jspx_page_context);
/* 5362 */     _jspx_th_html_005fmultibox_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5364 */     _jspx_th_html_005fmultibox_005f2.setProperty("loginSlider");
/*      */     
/* 5366 */     _jspx_th_html_005fmultibox_005f2.setValue("true");
/*      */     
/* 5368 */     _jspx_th_html_005fmultibox_005f2.setStyle("position:relative;");
/* 5369 */     int _jspx_eval_html_005fmultibox_005f2 = _jspx_th_html_005fmultibox_005f2.doStartTag();
/* 5370 */     if (_jspx_th_html_005fmultibox_005f2.doEndTag() == 5) {
/* 5371 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2);
/* 5372 */       return true;
/*      */     }
/* 5374 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2);
/* 5375 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5380 */     PageContext pageContext = _jspx_page_context;
/* 5381 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5383 */     MultiboxTag _jspx_th_html_005fmultibox_005f3 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5384 */     _jspx_th_html_005fmultibox_005f3.setPageContext(_jspx_page_context);
/* 5385 */     _jspx_th_html_005fmultibox_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5387 */     _jspx_th_html_005fmultibox_005f3.setProperty("loginFeatures");
/*      */     
/* 5389 */     _jspx_th_html_005fmultibox_005f3.setValue("true");
/*      */     
/* 5391 */     _jspx_th_html_005fmultibox_005f3.setStyle("position:relative;");
/* 5392 */     int _jspx_eval_html_005fmultibox_005f3 = _jspx_th_html_005fmultibox_005f3.doStartTag();
/* 5393 */     if (_jspx_th_html_005fmultibox_005f3.doEndTag() == 5) {
/* 5394 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f3);
/* 5395 */       return true;
/*      */     }
/* 5397 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f3);
/* 5398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5403 */     PageContext pageContext = _jspx_page_context;
/* 5404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5406 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(CheckboxTag.class);
/* 5407 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 5408 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5410 */     _jspx_th_html_005fcheckbox_005f0.setProperty("doNotGoToLogoutPage");
/*      */     
/* 5412 */     _jspx_th_html_005fcheckbox_005f0.setValue("True");
/*      */     
/* 5414 */     _jspx_th_html_005fcheckbox_005f0.setStyle("position:relative;");
/* 5415 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 5416 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 5417 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 5418 */       return true;
/*      */     }
/* 5420 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 5421 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f4(JspTag _jspx_th_logic_005fnotPresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5426 */     PageContext pageContext = _jspx_page_context;
/* 5427 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5429 */     MultiboxTag _jspx_th_html_005fmultibox_005f4 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5430 */     _jspx_th_html_005fmultibox_005f4.setPageContext(_jspx_page_context);
/* 5431 */     _jspx_th_html_005fmultibox_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f2);
/*      */     
/* 5433 */     _jspx_th_html_005fmultibox_005f4.setProperty("advancedUser");
/*      */     
/* 5435 */     _jspx_th_html_005fmultibox_005f4.setValue("true");
/*      */     
/* 5437 */     _jspx_th_html_005fmultibox_005f4.setStyle("position:relative;");
/* 5438 */     int _jspx_eval_html_005fmultibox_005f4 = _jspx_th_html_005fmultibox_005f4.doStartTag();
/* 5439 */     if (_jspx_th_html_005fmultibox_005f4.doEndTag() == 5) {
/* 5440 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f4);
/* 5441 */       return true;
/*      */     }
/* 5443 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f4);
/* 5444 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5449 */     PageContext pageContext = _jspx_page_context;
/* 5450 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5452 */     MultiboxTag _jspx_th_html_005fmultibox_005f5 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5453 */     _jspx_th_html_005fmultibox_005f5.setPageContext(_jspx_page_context);
/* 5454 */     _jspx_th_html_005fmultibox_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5456 */     _jspx_th_html_005fmultibox_005f5.setProperty("addHostToHolisticApplication");
/*      */     
/* 5458 */     _jspx_th_html_005fmultibox_005f5.setValue("true");
/*      */     
/* 5460 */     _jspx_th_html_005fmultibox_005f5.setStyle("position:relative;");
/* 5461 */     int _jspx_eval_html_005fmultibox_005f5 = _jspx_th_html_005fmultibox_005f5.doStartTag();
/* 5462 */     if (_jspx_th_html_005fmultibox_005f5.doEndTag() == 5) {
/* 5463 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f5);
/* 5464 */       return true;
/*      */     }
/* 5466 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f5);
/* 5467 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f6(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5472 */     PageContext pageContext = _jspx_page_context;
/* 5473 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5475 */     MultiboxTag _jspx_th_html_005fmultibox_005f6 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5476 */     _jspx_th_html_005fmultibox_005f6.setPageContext(_jspx_page_context);
/* 5477 */     _jspx_th_html_005fmultibox_005f6.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 5479 */     _jspx_th_html_005fmultibox_005f6.setProperty("discoverHostAlso");
/*      */     
/* 5481 */     _jspx_th_html_005fmultibox_005f6.setValue("true");
/*      */     
/* 5483 */     _jspx_th_html_005fmultibox_005f6.setStyle("position:relative;");
/* 5484 */     int _jspx_eval_html_005fmultibox_005f6 = _jspx_th_html_005fmultibox_005f6.doStartTag();
/* 5485 */     if (_jspx_th_html_005fmultibox_005f6.doEndTag() == 5) {
/* 5486 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f6);
/* 5487 */       return true;
/*      */     }
/* 5489 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f6);
/* 5490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5495 */     PageContext pageContext = _jspx_page_context;
/* 5496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5498 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fnobody.get(CheckboxTag.class);
/* 5499 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 5500 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5502 */     _jspx_th_html_005fcheckbox_005f1.setProperty("auto_restart");
/*      */     
/* 5504 */     _jspx_th_html_005fcheckbox_005f1.setStyle("position:relative;");
/* 5505 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 5506 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 5507 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 5508 */       return true;
/*      */     }
/* 5510 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 5511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5516 */     PageContext pageContext = _jspx_page_context;
/* 5517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5519 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(CheckboxTag.class);
/* 5520 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 5521 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 5523 */     _jspx_th_html_005fcheckbox_005f3.setProperty("showSalesForce");
/*      */     
/* 5525 */     _jspx_th_html_005fcheckbox_005f3.setValue("true");
/*      */     
/* 5527 */     _jspx_th_html_005fcheckbox_005f3.setStyle("position:relative; top:2px;");
/* 5528 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 5529 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 5530 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 5531 */       return true;
/*      */     }
/* 5533 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 5534 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f4(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5539 */     PageContext pageContext = _jspx_page_context;
/* 5540 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5542 */     CheckboxTag _jspx_th_html_005fcheckbox_005f4 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(CheckboxTag.class);
/* 5543 */     _jspx_th_html_005fcheckbox_005f4.setPageContext(_jspx_page_context);
/* 5544 */     _jspx_th_html_005fcheckbox_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 5546 */     _jspx_th_html_005fcheckbox_005f4.setProperty("easyUpgrade");
/*      */     
/* 5548 */     _jspx_th_html_005fcheckbox_005f4.setValue("true");
/*      */     
/* 5550 */     _jspx_th_html_005fcheckbox_005f4.setStyle("position:relative;");
/* 5551 */     int _jspx_eval_html_005fcheckbox_005f4 = _jspx_th_html_005fcheckbox_005f4.doStartTag();
/* 5552 */     if (_jspx_th_html_005fcheckbox_005f4.doEndTag() == 5) {
/* 5553 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 5554 */       return true;
/*      */     }
/* 5556 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 5557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f5(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5562 */     PageContext pageContext = _jspx_page_context;
/* 5563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5565 */     CheckboxTag _jspx_th_html_005fcheckbox_005f5 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.get(CheckboxTag.class);
/* 5566 */     _jspx_th_html_005fcheckbox_005f5.setPageContext(_jspx_page_context);
/* 5567 */     _jspx_th_html_005fcheckbox_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 5569 */     _jspx_th_html_005fcheckbox_005f5.setProperty("selfMonitoring");
/*      */     
/* 5571 */     _jspx_th_html_005fcheckbox_005f5.setValue("true");
/*      */     
/* 5573 */     _jspx_th_html_005fcheckbox_005f5.setStyle("position:relative;");
/* 5574 */     int _jspx_eval_html_005fcheckbox_005f5 = _jspx_th_html_005fcheckbox_005f5.doStartTag();
/* 5575 */     if (_jspx_th_html_005fcheckbox_005f5.doEndTag() == 5) {
/* 5576 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 5577 */       return true;
/*      */     }
/* 5579 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 5580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f6(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5585 */     PageContext pageContext = _jspx_page_context;
/* 5586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5588 */     CheckboxTag _jspx_th_html_005fcheckbox_005f6 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(CheckboxTag.class);
/* 5589 */     _jspx_th_html_005fcheckbox_005f6.setPageContext(_jspx_page_context);
/* 5590 */     _jspx_th_html_005fcheckbox_005f6.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 5592 */     _jspx_th_html_005fcheckbox_005f6.setProperty("useWebServerPort");
/*      */     
/* 5594 */     _jspx_th_html_005fcheckbox_005f6.setValue("true");
/* 5595 */     int _jspx_eval_html_005fcheckbox_005f6 = _jspx_th_html_005fcheckbox_005f6.doStartTag();
/* 5596 */     if (_jspx_th_html_005fcheckbox_005f6.doEndTag() == 5) {
/* 5597 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f6);
/* 5598 */       return true;
/*      */     }
/* 5600 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f6);
/* 5601 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5606 */     PageContext pageContext = _jspx_page_context;
/* 5607 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5609 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5610 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 5611 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5613 */     _jspx_th_html_005fradio_005f0.setProperty("usagestatistics");
/*      */     
/* 5615 */     _jspx_th_html_005fradio_005f0.setValue("true");
/* 5616 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 5617 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 5618 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 5619 */       return true;
/*      */     }
/* 5621 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 5622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5627 */     PageContext pageContext = _jspx_page_context;
/* 5628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5630 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5631 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 5632 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5634 */     _jspx_th_html_005fradio_005f1.setProperty("usagestatistics");
/*      */     
/* 5636 */     _jspx_th_html_005fradio_005f1.setValue("false");
/* 5637 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 5638 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 5639 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 5640 */       return true;
/*      */     }
/* 5642 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 5643 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5648 */     PageContext pageContext = _jspx_page_context;
/* 5649 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5651 */     MultiboxTag _jspx_th_html_005fmultibox_005f7 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5652 */     _jspx_th_html_005fmultibox_005f7.setPageContext(_jspx_page_context);
/* 5653 */     _jspx_th_html_005fmultibox_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5655 */     _jspx_th_html_005fmultibox_005f7.setProperty("actionsEnabled");
/*      */     
/* 5657 */     _jspx_th_html_005fmultibox_005f7.setValue("true");
/* 5658 */     int _jspx_eval_html_005fmultibox_005f7 = _jspx_th_html_005fmultibox_005f7.doStartTag();
/* 5659 */     if (_jspx_th_html_005fmultibox_005f7.doEndTag() == 5) {
/* 5660 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f7);
/* 5661 */       return true;
/*      */     }
/* 5663 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f7);
/* 5664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5669 */     PageContext pageContext = _jspx_page_context;
/* 5670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5672 */     MultiboxTag _jspx_th_html_005fmultibox_005f8 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5673 */     _jspx_th_html_005fmultibox_005f8.setPageContext(_jspx_page_context);
/* 5674 */     _jspx_th_html_005fmultibox_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5676 */     _jspx_th_html_005fmultibox_005f8.setProperty("pluginActionEnabled");
/*      */     
/* 5678 */     _jspx_th_html_005fmultibox_005f8.setValue("true");
/* 5679 */     int _jspx_eval_html_005fmultibox_005f8 = _jspx_th_html_005fmultibox_005f8.doStartTag();
/* 5680 */     if (_jspx_th_html_005fmultibox_005f8.doEndTag() == 5) {
/* 5681 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f8);
/* 5682 */       return true;
/*      */     }
/* 5684 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f8);
/* 5685 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5690 */     PageContext pageContext = _jspx_page_context;
/* 5691 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5693 */     MultiboxTag _jspx_th_html_005fmultibox_005f9 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5694 */     _jspx_th_html_005fmultibox_005f9.setPageContext(_jspx_page_context);
/* 5695 */     _jspx_th_html_005fmultibox_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5697 */     _jspx_th_html_005fmultibox_005f9.setProperty("repeatAvailabilityActions");
/*      */     
/* 5699 */     _jspx_th_html_005fmultibox_005f9.setValue("true");
/* 5700 */     int _jspx_eval_html_005fmultibox_005f9 = _jspx_th_html_005fmultibox_005f9.doStartTag();
/* 5701 */     if (_jspx_th_html_005fmultibox_005f9.doEndTag() == 5) {
/* 5702 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f9);
/* 5703 */       return true;
/*      */     }
/* 5705 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f9);
/* 5706 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5711 */     PageContext pageContext = _jspx_page_context;
/* 5712 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5714 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5715 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 5716 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5718 */     _jspx_th_html_005fradio_005f2.setProperty("repeatHealthActions");
/*      */     
/* 5720 */     _jspx_th_html_005fradio_005f2.setValue("tillActionCleared");
/* 5721 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 5722 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 5723 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 5724 */       return true;
/*      */     }
/* 5726 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 5727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5732 */     PageContext pageContext = _jspx_page_context;
/* 5733 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5735 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 5736 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 5737 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5739 */     _jspx_th_html_005ftext_005f0.setProperty("healthActionCount");
/*      */     
/* 5741 */     _jspx_th_html_005ftext_005f0.setSize("1");
/*      */     
/* 5743 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext vsmall");
/*      */     
/* 5745 */     _jspx_th_html_005ftext_005f0.setMaxlength("2");
/* 5746 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 5747 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 5748 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5749 */       return true;
/*      */     }
/* 5751 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 5752 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5757 */     PageContext pageContext = _jspx_page_context;
/* 5758 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5760 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5761 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 5762 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5764 */     _jspx_th_html_005fradio_005f3.setProperty("repeatHealthActions");
/*      */     
/* 5766 */     _jspx_th_html_005fradio_005f3.setValue("tillActionPicked");
/* 5767 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 5768 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 5769 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 5770 */       return true;
/*      */     }
/* 5772 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 5773 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5778 */     PageContext pageContext = _jspx_page_context;
/* 5779 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5781 */     MultiboxTag _jspx_th_html_005fmultibox_005f10 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5782 */     _jspx_th_html_005fmultibox_005f10.setPageContext(_jspx_page_context);
/* 5783 */     _jspx_th_html_005fmultibox_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5785 */     _jspx_th_html_005fmultibox_005f10.setProperty("repeatAttributeActions");
/*      */     
/* 5787 */     _jspx_th_html_005fmultibox_005f10.setValue("true");
/* 5788 */     int _jspx_eval_html_005fmultibox_005f10 = _jspx_th_html_005fmultibox_005f10.doStartTag();
/* 5789 */     if (_jspx_th_html_005fmultibox_005f10.doEndTag() == 5) {
/* 5790 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f10);
/* 5791 */       return true;
/*      */     }
/* 5793 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f10);
/* 5794 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5799 */     PageContext pageContext = _jspx_page_context;
/* 5800 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5802 */     MultiboxTag _jspx_th_html_005fmultibox_005f11 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5803 */     _jspx_th_html_005fmultibox_005f11.setPageContext(_jspx_page_context);
/* 5804 */     _jspx_th_html_005fmultibox_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5806 */     _jspx_th_html_005fmultibox_005f11.setProperty("enableServerSnapshot");
/*      */     
/* 5808 */     _jspx_th_html_005fmultibox_005f11.setValue("true");
/* 5809 */     int _jspx_eval_html_005fmultibox_005f11 = _jspx_th_html_005fmultibox_005f11.doStartTag();
/* 5810 */     if (_jspx_th_html_005fmultibox_005f11.doEndTag() == 5) {
/* 5811 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f11);
/* 5812 */       return true;
/*      */     }
/* 5814 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f11);
/* 5815 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5820 */     PageContext pageContext = _jspx_page_context;
/* 5821 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5823 */     MultiboxTag _jspx_th_html_005fmultibox_005f12 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5824 */     _jspx_th_html_005fmultibox_005f12.setPageContext(_jspx_page_context);
/* 5825 */     _jspx_th_html_005fmultibox_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5827 */     _jspx_th_html_005fmultibox_005f12.setProperty("enableConsolidatedMail");
/*      */     
/* 5829 */     _jspx_th_html_005fmultibox_005f12.setValue("true");
/* 5830 */     int _jspx_eval_html_005fmultibox_005f12 = _jspx_th_html_005fmultibox_005f12.doStartTag();
/* 5831 */     if (_jspx_th_html_005fmultibox_005f12.doEndTag() == 5) {
/* 5832 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f12);
/* 5833 */       return true;
/*      */     }
/* 5835 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f12);
/* 5836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5841 */     PageContext pageContext = _jspx_page_context;
/* 5842 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5844 */     MultiboxTag _jspx_th_html_005fmultibox_005f13 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 5845 */     _jspx_th_html_005fmultibox_005f13.setPageContext(_jspx_page_context);
/* 5846 */     _jspx_th_html_005fmultibox_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5848 */     _jspx_th_html_005fmultibox_005f13.setProperty("enableErrorMail");
/*      */     
/* 5850 */     _jspx_th_html_005fmultibox_005f13.setValue("true");
/*      */     
/* 5852 */     _jspx_th_html_005fmultibox_005f13.setOnclick("javascript:checkForPollCount()");
/* 5853 */     int _jspx_eval_html_005fmultibox_005f13 = _jspx_th_html_005fmultibox_005f13.doStartTag();
/* 5854 */     if (_jspx_th_html_005fmultibox_005f13.doEndTag() == 5) {
/* 5855 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f13);
/* 5856 */       return true;
/*      */     }
/* 5858 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f13);
/* 5859 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5864 */     PageContext pageContext = _jspx_page_context;
/* 5865 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5867 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 5868 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 5869 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5871 */     _jspx_th_html_005ftext_005f1.setProperty("errorpollCount");
/*      */     
/* 5873 */     _jspx_th_html_005ftext_005f1.setSize("1");
/*      */     
/* 5875 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext vsmall");
/*      */     
/* 5877 */     _jspx_th_html_005ftext_005f1.setMaxlength("2");
/* 5878 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 5879 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 5880 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5881 */       return true;
/*      */     }
/* 5883 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 5884 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5889 */     PageContext pageContext = _jspx_page_context;
/* 5890 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5892 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5893 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 5894 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5896 */     _jspx_th_html_005fradio_005f4.setProperty("dateformat");
/*      */     
/* 5898 */     _jspx_th_html_005fradio_005f4.setValue("default");
/* 5899 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 5900 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 5901 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 5902 */       return true;
/*      */     }
/* 5904 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 5905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5910 */     PageContext pageContext = _jspx_page_context;
/* 5911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5913 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 5914 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 5915 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5917 */     _jspx_th_html_005fradio_005f5.setProperty("dateformat");
/*      */     
/* 5919 */     _jspx_th_html_005fradio_005f5.setValue("custom");
/* 5920 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 5921 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 5922 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 5923 */       return true;
/*      */     }
/* 5925 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 5926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5931 */     PageContext pageContext = _jspx_page_context;
/* 5932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5934 */     MultiboxTag _jspx_th_html_005fmultibox_005f14 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5935 */     _jspx_th_html_005fmultibox_005f14.setPageContext(_jspx_page_context);
/* 5936 */     _jspx_th_html_005fmultibox_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5938 */     _jspx_th_html_005fmultibox_005f14.setProperty("enableCompleteInfoForSMS");
/*      */     
/* 5940 */     _jspx_th_html_005fmultibox_005f14.setValue("true");
/* 5941 */     int _jspx_eval_html_005fmultibox_005f14 = _jspx_th_html_005fmultibox_005f14.doStartTag();
/* 5942 */     if (_jspx_th_html_005fmultibox_005f14.doEndTag() == 5) {
/* 5943 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f14);
/* 5944 */       return true;
/*      */     }
/* 5946 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f14);
/* 5947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5952 */     PageContext pageContext = _jspx_page_context;
/* 5953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5955 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 5956 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 5957 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5959 */     _jspx_th_html_005ftext_005f2.setProperty("min_criticalpollscount");
/*      */     
/* 5961 */     _jspx_th_html_005ftext_005f2.setSize("1");
/*      */     
/* 5963 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext vsmall");
/*      */     
/* 5965 */     _jspx_th_html_005ftext_005f2.setMaxlength("2");
/* 5966 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 5967 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 5968 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5969 */       return true;
/*      */     }
/* 5971 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 5972 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5977 */     PageContext pageContext = _jspx_page_context;
/* 5978 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5980 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 5981 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 5982 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5984 */     _jspx_th_html_005ftext_005f3.setProperty("criticalpollscount");
/*      */     
/* 5986 */     _jspx_th_html_005ftext_005f3.setSize("1");
/*      */     
/* 5988 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext vsmall");
/*      */     
/* 5990 */     _jspx_th_html_005ftext_005f3.setMaxlength("2");
/* 5991 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 5992 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 5993 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5994 */       return true;
/*      */     }
/* 5996 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 5997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6002 */     PageContext pageContext = _jspx_page_context;
/* 6003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6005 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6006 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 6007 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6009 */     _jspx_th_html_005ftext_005f4.setProperty("min_warningpollscount");
/*      */     
/* 6011 */     _jspx_th_html_005ftext_005f4.setSize("1");
/*      */     
/* 6013 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext vsmall");
/*      */     
/* 6015 */     _jspx_th_html_005ftext_005f4.setMaxlength("2");
/* 6016 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 6017 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 6018 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 6019 */       return true;
/*      */     }
/* 6021 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 6022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6027 */     PageContext pageContext = _jspx_page_context;
/* 6028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6030 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6031 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 6032 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6034 */     _jspx_th_html_005ftext_005f5.setProperty("warningpollscount");
/*      */     
/* 6036 */     _jspx_th_html_005ftext_005f5.setSize("1");
/*      */     
/* 6038 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext vsmall");
/*      */     
/* 6040 */     _jspx_th_html_005ftext_005f5.setMaxlength("2");
/* 6041 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 6042 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 6043 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 6044 */       return true;
/*      */     }
/* 6046 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 6047 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6052 */     PageContext pageContext = _jspx_page_context;
/* 6053 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6055 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6056 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 6057 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6059 */     _jspx_th_html_005ftext_005f6.setProperty("min_clearpollscount");
/*      */     
/* 6061 */     _jspx_th_html_005ftext_005f6.setSize("1");
/*      */     
/* 6063 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext vsmall");
/*      */     
/* 6065 */     _jspx_th_html_005ftext_005f6.setMaxlength("2");
/* 6066 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 6067 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 6068 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 6069 */       return true;
/*      */     }
/* 6071 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 6072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6077 */     PageContext pageContext = _jspx_page_context;
/* 6078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6080 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6081 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 6082 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6084 */     _jspx_th_html_005ftext_005f7.setProperty("clearpollscount");
/*      */     
/* 6086 */     _jspx_th_html_005ftext_005f7.setSize("1");
/*      */     
/* 6088 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext vsmall");
/*      */     
/* 6090 */     _jspx_th_html_005ftext_005f7.setMaxlength("2");
/* 6091 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 6092 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 6093 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 6094 */       return true;
/*      */     }
/* 6096 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 6097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6102 */     PageContext pageContext = _jspx_page_context;
/* 6103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6105 */     MultiboxTag _jspx_th_html_005fmultibox_005f15 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6106 */     _jspx_th_html_005fmultibox_005f15.setPageContext(_jspx_page_context);
/* 6107 */     _jspx_th_html_005fmultibox_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6109 */     _jspx_th_html_005fmultibox_005f15.setProperty("availabilityUpUnderMaintenance");
/*      */     
/* 6111 */     _jspx_th_html_005fmultibox_005f15.setValue("true");
/* 6112 */     int _jspx_eval_html_005fmultibox_005f15 = _jspx_th_html_005fmultibox_005f15.doStartTag();
/* 6113 */     if (_jspx_th_html_005fmultibox_005f15.doEndTag() == 5) {
/* 6114 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f15);
/* 6115 */       return true;
/*      */     }
/* 6117 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f15);
/* 6118 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6123 */     PageContext pageContext = _jspx_page_context;
/* 6124 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6126 */     MultiboxTag _jspx_th_html_005fmultibox_005f16 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6127 */     _jspx_th_html_005fmultibox_005f16.setPageContext(_jspx_page_context);
/* 6128 */     _jspx_th_html_005fmultibox_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6130 */     _jspx_th_html_005fmultibox_005f16.setProperty("healthClearUnderMaintenance");
/*      */     
/* 6132 */     _jspx_th_html_005fmultibox_005f16.setValue("true");
/* 6133 */     int _jspx_eval_html_005fmultibox_005f16 = _jspx_th_html_005fmultibox_005f16.doStartTag();
/* 6134 */     if (_jspx_th_html_005fmultibox_005f16.doEndTag() == 5) {
/* 6135 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f16);
/* 6136 */       return true;
/*      */     }
/* 6138 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f16);
/* 6139 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6144 */     PageContext pageContext = _jspx_page_context;
/* 6145 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6147 */     MultiboxTag _jspx_th_html_005fmultibox_005f17 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 6148 */     _jspx_th_html_005fmultibox_005f17.setPageContext(_jspx_page_context);
/* 6149 */     _jspx_th_html_005fmultibox_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6151 */     _jspx_th_html_005fmultibox_005f17.setProperty("gatewayCheckStatus");
/*      */     
/* 6153 */     _jspx_th_html_005fmultibox_005f17.setValue("true");
/*      */     
/* 6155 */     _jspx_th_html_005fmultibox_005f17.setOnclick("javascript:onCheckBox1()");
/* 6156 */     int _jspx_eval_html_005fmultibox_005f17 = _jspx_th_html_005fmultibox_005f17.doStartTag();
/* 6157 */     if (_jspx_th_html_005fmultibox_005f17.doEndTag() == 5) {
/* 6158 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f17);
/* 6159 */       return true;
/*      */     }
/* 6161 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f17);
/* 6162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6167 */     PageContext pageContext = _jspx_page_context;
/* 6168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6170 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6171 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 6172 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6174 */     _jspx_th_html_005ftext_005f8.setProperty("gatewayName");
/*      */     
/* 6176 */     _jspx_th_html_005ftext_005f8.setSize("10");
/*      */     
/* 6178 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext medium");
/*      */     
/* 6180 */     _jspx_th_html_005ftext_005f8.setMaxlength("40");
/* 6181 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 6182 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 6183 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 6184 */       return true;
/*      */     }
/* 6186 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 6187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f18(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6192 */     PageContext pageContext = _jspx_page_context;
/* 6193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6195 */     MultiboxTag _jspx_th_html_005fmultibox_005f18 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 6196 */     _jspx_th_html_005fmultibox_005f18.setPageContext(_jspx_page_context);
/* 6197 */     _jspx_th_html_005fmultibox_005f18.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6199 */     _jspx_th_html_005fmultibox_005f18.setProperty("gatewayUrlStatus");
/*      */     
/* 6201 */     _jspx_th_html_005fmultibox_005f18.setValue("true");
/*      */     
/* 6203 */     _jspx_th_html_005fmultibox_005f18.setOnclick("javascript:onCheckBox()");
/* 6204 */     int _jspx_eval_html_005fmultibox_005f18 = _jspx_th_html_005fmultibox_005f18.doStartTag();
/* 6205 */     if (_jspx_th_html_005fmultibox_005f18.doEndTag() == 5) {
/* 6206 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f18);
/* 6207 */       return true;
/*      */     }
/* 6209 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f18);
/* 6210 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6215 */     PageContext pageContext = _jspx_page_context;
/* 6216 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6218 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fmaxlength_005fnobody.get(TextTag.class);
/* 6219 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 6220 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6222 */     _jspx_th_html_005ftext_005f9.setProperty("gatewayUrlName");
/*      */     
/* 6224 */     _jspx_th_html_005ftext_005f9.setSize("25");
/*      */     
/* 6226 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext normal");
/*      */     
/* 6228 */     _jspx_th_html_005ftext_005f9.setMaxlength("40");
/*      */     
/* 6230 */     _jspx_th_html_005ftext_005f9.setOnblur("javascript:checkUrl(document.AMActionForm.gatewayUrlName.value)");
/* 6231 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 6232 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 6233 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 6234 */       return true;
/*      */     }
/* 6236 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonblur_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 6237 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6242 */     PageContext pageContext = _jspx_page_context;
/* 6243 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6245 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6246 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/* 6247 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6249 */     _jspx_th_html_005ftext_005f10.setProperty("fromaddress");
/*      */     
/* 6251 */     _jspx_th_html_005ftext_005f10.setSize("25");
/*      */     
/* 6253 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext normal");
/*      */     
/* 6255 */     _jspx_th_html_005ftext_005f10.setMaxlength("100");
/* 6256 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/* 6257 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/* 6258 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 6259 */       return true;
/*      */     }
/* 6261 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 6262 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6267 */     PageContext pageContext = _jspx_page_context;
/* 6268 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6270 */     TextTag _jspx_th_html_005ftext_005f11 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6271 */     _jspx_th_html_005ftext_005f11.setPageContext(_jspx_page_context);
/* 6272 */     _jspx_th_html_005ftext_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6274 */     _jspx_th_html_005ftext_005f11.setProperty("sendmail");
/*      */     
/* 6276 */     _jspx_th_html_005ftext_005f11.setSize("25");
/*      */     
/* 6278 */     _jspx_th_html_005ftext_005f11.setStyleClass("formtext normal");
/*      */     
/* 6280 */     _jspx_th_html_005ftext_005f11.setMaxlength("255");
/* 6281 */     int _jspx_eval_html_005ftext_005f11 = _jspx_th_html_005ftext_005f11.doStartTag();
/* 6282 */     if (_jspx_th_html_005ftext_005f11.doEndTag() == 5) {
/* 6283 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 6284 */       return true;
/*      */     }
/* 6286 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 6287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f12(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6292 */     PageContext pageContext = _jspx_page_context;
/* 6293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6295 */     TextTag _jspx_th_html_005ftext_005f12 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6296 */     _jspx_th_html_005ftext_005f12.setPageContext(_jspx_page_context);
/* 6297 */     _jspx_th_html_005ftext_005f12.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6299 */     _jspx_th_html_005ftext_005f12.setProperty("timeout");
/*      */     
/* 6301 */     _jspx_th_html_005ftext_005f12.setSize("3");
/*      */     
/* 6303 */     _jspx_th_html_005ftext_005f12.setStyleClass("formtext small");
/*      */     
/* 6305 */     _jspx_th_html_005ftext_005f12.setMaxlength("3");
/* 6306 */     int _jspx_eval_html_005ftext_005f12 = _jspx_th_html_005ftext_005f12.doStartTag();
/* 6307 */     if (_jspx_th_html_005ftext_005f12.doEndTag() == 5) {
/* 6308 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 6309 */       return true;
/*      */     }
/* 6311 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 6312 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6317 */     PageContext pageContext = _jspx_page_context;
/* 6318 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6320 */     org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (org.apache.struts.taglib.html.OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
/* 6321 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 6322 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 6324 */     _jspx_th_html_005foptionsCollection_005f0.setName("gmapcountries");
/* 6325 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 6326 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 6327 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6328 */       return true;
/*      */     }
/* 6330 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6331 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6336 */     PageContext pageContext = _jspx_page_context;
/* 6337 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6339 */     TextTag _jspx_th_html_005ftext_005f13 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6340 */     _jspx_th_html_005ftext_005f13.setPageContext(_jspx_page_context);
/* 6341 */     _jspx_th_html_005ftext_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6343 */     _jspx_th_html_005ftext_005f13.setProperty("gmapheight");
/*      */     
/* 6345 */     _jspx_th_html_005ftext_005f13.setSize("5");
/*      */     
/* 6347 */     _jspx_th_html_005ftext_005f13.setStyleClass("formtext small");
/* 6348 */     int _jspx_eval_html_005ftext_005f13 = _jspx_th_html_005ftext_005f13.doStartTag();
/* 6349 */     if (_jspx_th_html_005ftext_005f13.doEndTag() == 5) {
/* 6350 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 6351 */       return true;
/*      */     }
/* 6353 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 6354 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6359 */     PageContext pageContext = _jspx_page_context;
/* 6360 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6362 */     TextTag _jspx_th_html_005ftext_005f14 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6363 */     _jspx_th_html_005ftext_005f14.setPageContext(_jspx_page_context);
/* 6364 */     _jspx_th_html_005ftext_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6366 */     _jspx_th_html_005ftext_005f14.setProperty("gmapwidth");
/*      */     
/* 6368 */     _jspx_th_html_005ftext_005f14.setStyleClass("formtext small");
/*      */     
/* 6370 */     _jspx_th_html_005ftext_005f14.setSize("5");
/* 6371 */     int _jspx_eval_html_005ftext_005f14 = _jspx_th_html_005ftext_005f14.doStartTag();
/* 6372 */     if (_jspx_th_html_005ftext_005f14.doEndTag() == 5) {
/* 6373 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/* 6374 */       return true;
/*      */     }
/* 6376 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/* 6377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6382 */     PageContext pageContext = _jspx_page_context;
/* 6383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6385 */     TextTag _jspx_th_html_005ftext_005f15 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.get(TextTag.class);
/* 6386 */     _jspx_th_html_005ftext_005f15.setPageContext(_jspx_page_context);
/* 6387 */     _jspx_th_html_005ftext_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6389 */     _jspx_th_html_005ftext_005f15.setProperty("zoomlevel");
/* 6390 */     int _jspx_eval_html_005ftext_005f15 = _jspx_th_html_005ftext_005f15.doStartTag();
/* 6391 */     if (_jspx_th_html_005ftext_005f15.doEndTag() == 5) {
/* 6392 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f15);
/* 6393 */       return true;
/*      */     }
/* 6395 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f15);
/* 6396 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6401 */     PageContext pageContext = _jspx_page_context;
/* 6402 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6404 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.get(SelectTag.class);
/* 6405 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 6406 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6408 */     _jspx_th_html_005fselect_005f3.setProperty("category");
/*      */     
/* 6410 */     _jspx_th_html_005fselect_005f3.setStyle("width:20%");
/*      */     
/* 6412 */     _jspx_th_html_005fselect_005f3.setSize("1");
/* 6413 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 6414 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 6415 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 6416 */         out = _jspx_page_context.pushBody();
/* 6417 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 6418 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6421 */         out.write("\n                        ");
/* 6422 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 6423 */           return true;
/* 6424 */         out.write("\n                    ");
/* 6425 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 6426 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6429 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 6430 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6433 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 6434 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 6435 */       return true;
/*      */     }
/* 6437 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 6438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6443 */     PageContext pageContext = _jspx_page_context;
/* 6444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6446 */     org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (org.apache.struts.taglib.html.OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
/* 6447 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 6448 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 6450 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("gmapcountries");
/* 6451 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 6452 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 6453 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 6454 */       return true;
/*      */     }
/* 6456 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 6457 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f7(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6462 */     PageContext pageContext = _jspx_page_context;
/* 6463 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6465 */     CheckboxTag _jspx_th_html_005fcheckbox_005f7 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6466 */     _jspx_th_html_005fcheckbox_005f7.setPageContext(_jspx_page_context);
/* 6467 */     _jspx_th_html_005fcheckbox_005f7.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6469 */     _jspx_th_html_005fcheckbox_005f7.setProperty("diskIOAix");
/*      */     
/* 6471 */     _jspx_th_html_005fcheckbox_005f7.setOnclick("javascript:ioAlert(this)");
/* 6472 */     int _jspx_eval_html_005fcheckbox_005f7 = _jspx_th_html_005fcheckbox_005f7.doStartTag();
/* 6473 */     if (_jspx_th_html_005fcheckbox_005f7.doEndTag() == 5) {
/* 6474 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f7);
/* 6475 */       return true;
/*      */     }
/* 6477 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f7);
/* 6478 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6483 */     PageContext pageContext = _jspx_page_context;
/* 6484 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6486 */     CheckboxTag _jspx_th_html_005fcheckbox_005f8 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6487 */     _jspx_th_html_005fcheckbox_005f8.setPageContext(_jspx_page_context);
/* 6488 */     _jspx_th_html_005fcheckbox_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6490 */     _jspx_th_html_005fcheckbox_005f8.setProperty("diskIOLinux");
/*      */     
/* 6492 */     _jspx_th_html_005fcheckbox_005f8.setOnclick("javascript:ioAlert(this)");
/* 6493 */     int _jspx_eval_html_005fcheckbox_005f8 = _jspx_th_html_005fcheckbox_005f8.doStartTag();
/* 6494 */     if (_jspx_th_html_005fcheckbox_005f8.doEndTag() == 5) {
/* 6495 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f8);
/* 6496 */       return true;
/*      */     }
/* 6498 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f8);
/* 6499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f9(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6504 */     PageContext pageContext = _jspx_page_context;
/* 6505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6507 */     CheckboxTag _jspx_th_html_005fcheckbox_005f9 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6508 */     _jspx_th_html_005fcheckbox_005f9.setPageContext(_jspx_page_context);
/* 6509 */     _jspx_th_html_005fcheckbox_005f9.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 6511 */     _jspx_th_html_005fcheckbox_005f9.setProperty("diskIOSun");
/*      */     
/* 6513 */     _jspx_th_html_005fcheckbox_005f9.setOnclick("javascript:ioAlert(this)");
/* 6514 */     int _jspx_eval_html_005fcheckbox_005f9 = _jspx_th_html_005fcheckbox_005f9.doStartTag();
/* 6515 */     if (_jspx_th_html_005fcheckbox_005f9.doEndTag() == 5) {
/* 6516 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f9);
/* 6517 */       return true;
/*      */     }
/* 6519 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f9);
/* 6520 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f10(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6525 */     PageContext pageContext = _jspx_page_context;
/* 6526 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6528 */     CheckboxTag _jspx_th_html_005fcheckbox_005f10 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6529 */     _jspx_th_html_005fcheckbox_005f10.setPageContext(_jspx_page_context);
/* 6530 */     _jspx_th_html_005fcheckbox_005f10.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 6532 */     _jspx_th_html_005fcheckbox_005f10.setProperty("diskIOWindows");
/*      */     
/* 6534 */     _jspx_th_html_005fcheckbox_005f10.setOnclick("javascript:ioAlert(this)");
/* 6535 */     int _jspx_eval_html_005fcheckbox_005f10 = _jspx_th_html_005fcheckbox_005f10.doStartTag();
/* 6536 */     if (_jspx_th_html_005fcheckbox_005f10.doEndTag() == 5) {
/* 6537 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f10);
/* 6538 */       return true;
/*      */     }
/* 6540 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f10);
/* 6541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6546 */     PageContext pageContext = _jspx_page_context;
/* 6547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6549 */     CheckboxTag _jspx_th_html_005fcheckbox_005f11 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fdisabled_005fnobody.get(CheckboxTag.class);
/* 6550 */     _jspx_th_html_005fcheckbox_005f11.setPageContext(_jspx_page_context);
/* 6551 */     _jspx_th_html_005fcheckbox_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6553 */     _jspx_th_html_005fcheckbox_005f11.setProperty("winDiskLocal");
/*      */     
/* 6555 */     _jspx_th_html_005fcheckbox_005f11.setOnclick("");
/*      */     
/* 6557 */     _jspx_th_html_005fcheckbox_005f11.setDisabled(true);
/* 6558 */     int _jspx_eval_html_005fcheckbox_005f11 = _jspx_th_html_005fcheckbox_005f11.doStartTag();
/* 6559 */     if (_jspx_th_html_005fcheckbox_005f11.doEndTag() == 5) {
/* 6560 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f11);
/* 6561 */       return true;
/*      */     }
/* 6563 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f11);
/* 6564 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6569 */     PageContext pageContext = _jspx_page_context;
/* 6570 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6572 */     CheckboxTag _jspx_th_html_005fcheckbox_005f12 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6573 */     _jspx_th_html_005fcheckbox_005f12.setPageContext(_jspx_page_context);
/* 6574 */     _jspx_th_html_005fcheckbox_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6576 */     _jspx_th_html_005fcheckbox_005f12.setProperty("winDiskNetwork");
/*      */     
/* 6578 */     _jspx_th_html_005fcheckbox_005f12.setOnclick("javascript:winDiskDisableAlert(this,'2')");
/* 6579 */     int _jspx_eval_html_005fcheckbox_005f12 = _jspx_th_html_005fcheckbox_005f12.doStartTag();
/* 6580 */     if (_jspx_th_html_005fcheckbox_005f12.doEndTag() == 5) {
/* 6581 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f12);
/* 6582 */       return true;
/*      */     }
/* 6584 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f12);
/* 6585 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f13(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6590 */     PageContext pageContext = _jspx_page_context;
/* 6591 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6593 */     CheckboxTag _jspx_th_html_005fcheckbox_005f13 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6594 */     _jspx_th_html_005fcheckbox_005f13.setPageContext(_jspx_page_context);
/* 6595 */     _jspx_th_html_005fcheckbox_005f13.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6597 */     _jspx_th_html_005fcheckbox_005f13.setProperty("winDiskMount");
/*      */     
/* 6599 */     _jspx_th_html_005fcheckbox_005f13.setOnclick("javascript:winDiskDisableAlert(this,'3')");
/* 6600 */     int _jspx_eval_html_005fcheckbox_005f13 = _jspx_th_html_005fcheckbox_005f13.doStartTag();
/* 6601 */     if (_jspx_th_html_005fcheckbox_005f13.doEndTag() == 5) {
/* 6602 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f13);
/* 6603 */       return true;
/*      */     }
/* 6605 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f13);
/* 6606 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6611 */     PageContext pageContext = _jspx_page_context;
/* 6612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6614 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6615 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6616 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6618 */     _jspx_th_c_005fout_005f1.setValue("${deleteDisk}");
/* 6619 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6620 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6621 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6622 */       return true;
/*      */     }
/* 6624 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6625 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6630 */     PageContext pageContext = _jspx_page_context;
/* 6631 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6633 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6634 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 6635 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6637 */     _jspx_th_c_005fif_005f7.setTest("${deleteDisk eq 'true'}");
/* 6638 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 6639 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 6641 */         out.write("checked=\"checked\"");
/* 6642 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 6643 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6647 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 6648 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6649 */       return true;
/*      */     }
/* 6651 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6652 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6657 */     PageContext pageContext = _jspx_page_context;
/* 6658 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6660 */     CheckboxTag _jspx_th_html_005fcheckbox_005f14 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6661 */     _jspx_th_html_005fcheckbox_005f14.setPageContext(_jspx_page_context);
/* 6662 */     _jspx_th_html_005fcheckbox_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6664 */     _jspx_th_html_005fcheckbox_005f14.setProperty("hostProcDown");
/*      */     
/* 6666 */     _jspx_th_html_005fcheckbox_005f14.setOnclick("");
/* 6667 */     int _jspx_eval_html_005fcheckbox_005f14 = _jspx_th_html_005fcheckbox_005f14.doStartTag();
/* 6668 */     if (_jspx_th_html_005fcheckbox_005f14.doEndTag() == 5) {
/* 6669 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f14);
/* 6670 */       return true;
/*      */     }
/* 6672 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f14);
/* 6673 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f15(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6678 */     PageContext pageContext = _jspx_page_context;
/* 6679 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6681 */     CheckboxTag _jspx_th_html_005fcheckbox_005f15 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6682 */     _jspx_th_html_005fcheckbox_005f15.setPageContext(_jspx_page_context);
/* 6683 */     _jspx_th_html_005fcheckbox_005f15.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6685 */     _jspx_th_html_005fcheckbox_005f15.setProperty("winServDown");
/*      */     
/* 6687 */     _jspx_th_html_005fcheckbox_005f15.setOnclick("");
/* 6688 */     int _jspx_eval_html_005fcheckbox_005f15 = _jspx_th_html_005fcheckbox_005f15.doStartTag();
/* 6689 */     if (_jspx_th_html_005fcheckbox_005f15.doEndTag() == 5) {
/* 6690 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f15);
/* 6691 */       return true;
/*      */     }
/* 6693 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f15);
/* 6694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6699 */     PageContext pageContext = _jspx_page_context;
/* 6700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6702 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6703 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6704 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6706 */     _jspx_th_c_005fout_005f2.setValue("${processinstance}");
/* 6707 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6708 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6709 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6710 */       return true;
/*      */     }
/* 6712 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6718 */     PageContext pageContext = _jspx_page_context;
/* 6719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6721 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6722 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 6723 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6725 */     _jspx_th_c_005fif_005f9.setTest("${processinstance eq 'true'}");
/* 6726 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 6727 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 6729 */         out.write("checked=\"checked\"");
/* 6730 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 6731 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6735 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 6736 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6737 */       return true;
/*      */     }
/* 6739 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6740 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6745 */     PageContext pageContext = _jspx_page_context;
/* 6746 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6748 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6749 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6750 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6752 */     _jspx_th_c_005fout_005f3.setValue("${mgProcessAndServiceAssoicated}");
/* 6753 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6754 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6755 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6756 */       return true;
/*      */     }
/* 6758 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6759 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6764 */     PageContext pageContext = _jspx_page_context;
/* 6765 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6767 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6768 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 6769 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6771 */     _jspx_th_c_005fif_005f10.setTest("${mgProcessAndServiceAssoicated eq 'true'}");
/* 6772 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 6773 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 6775 */         out.write("checked=\"checked\"");
/* 6776 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 6777 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6781 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 6782 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6783 */       return true;
/*      */     }
/* 6785 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6786 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6791 */     PageContext pageContext = _jspx_page_context;
/* 6792 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6794 */     CheckboxTag _jspx_th_html_005fcheckbox_005f16 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6795 */     _jspx_th_html_005fcheckbox_005f16.setPageContext(_jspx_page_context);
/* 6796 */     _jspx_th_html_005fcheckbox_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6798 */     _jspx_th_html_005fcheckbox_005f16.setProperty("errorAlertDisk");
/*      */     
/* 6800 */     _jspx_th_html_005fcheckbox_005f16.setOnclick("");
/* 6801 */     int _jspx_eval_html_005fcheckbox_005f16 = _jspx_th_html_005fcheckbox_005f16.doStartTag();
/* 6802 */     if (_jspx_th_html_005fcheckbox_005f16.doEndTag() == 5) {
/* 6803 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f16);
/* 6804 */       return true;
/*      */     }
/* 6806 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f16);
/* 6807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6812 */     PageContext pageContext = _jspx_page_context;
/* 6813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6815 */     CheckboxTag _jspx_th_html_005fcheckbox_005f17 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6816 */     _jspx_th_html_005fcheckbox_005f17.setPageContext(_jspx_page_context);
/* 6817 */     _jspx_th_html_005fcheckbox_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6819 */     _jspx_th_html_005fcheckbox_005f17.setProperty("errorAlertNwInter");
/*      */     
/* 6821 */     _jspx_th_html_005fcheckbox_005f17.setOnclick("");
/* 6822 */     int _jspx_eval_html_005fcheckbox_005f17 = _jspx_th_html_005fcheckbox_005f17.doStartTag();
/* 6823 */     if (_jspx_th_html_005fcheckbox_005f17.doEndTag() == 5) {
/* 6824 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f17);
/* 6825 */       return true;
/*      */     }
/* 6827 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f17);
/* 6828 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f18(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6833 */     PageContext pageContext = _jspx_page_context;
/* 6834 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6836 */     CheckboxTag _jspx_th_html_005fcheckbox_005f18 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6837 */     _jspx_th_html_005fcheckbox_005f18.setPageContext(_jspx_page_context);
/* 6838 */     _jspx_th_html_005fcheckbox_005f18.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6840 */     _jspx_th_html_005fcheckbox_005f18.setProperty("errorAlertNwAdapter");
/*      */     
/* 6842 */     _jspx_th_html_005fcheckbox_005f18.setOnclick("");
/* 6843 */     int _jspx_eval_html_005fcheckbox_005f18 = _jspx_th_html_005fcheckbox_005f18.doStartTag();
/* 6844 */     if (_jspx_th_html_005fcheckbox_005f18.doEndTag() == 5) {
/* 6845 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f18);
/* 6846 */       return true;
/*      */     }
/* 6848 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f18);
/* 6849 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f19(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6854 */     PageContext pageContext = _jspx_page_context;
/* 6855 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6857 */     CheckboxTag _jspx_th_html_005fcheckbox_005f19 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6858 */     _jspx_th_html_005fcheckbox_005f19.setPageContext(_jspx_page_context);
/* 6859 */     _jspx_th_html_005fcheckbox_005f19.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6861 */     _jspx_th_html_005fcheckbox_005f19.setProperty("errorAlertRestart");
/*      */     
/* 6863 */     _jspx_th_html_005fcheckbox_005f19.setOnclick("");
/* 6864 */     int _jspx_eval_html_005fcheckbox_005f19 = _jspx_th_html_005fcheckbox_005f19.doStartTag();
/* 6865 */     if (_jspx_th_html_005fcheckbox_005f19.doEndTag() == 5) {
/* 6866 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f19);
/* 6867 */       return true;
/*      */     }
/* 6869 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f19);
/* 6870 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f20(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6875 */     PageContext pageContext = _jspx_page_context;
/* 6876 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6878 */     CheckboxTag _jspx_th_html_005fcheckbox_005f20 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6879 */     _jspx_th_html_005fcheckbox_005f20.setPageContext(_jspx_page_context);
/* 6880 */     _jspx_th_html_005fcheckbox_005f20.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6882 */     _jspx_th_html_005fcheckbox_005f20.setProperty("errorAlertProcessRestart");
/*      */     
/* 6884 */     _jspx_th_html_005fcheckbox_005f20.setOnclick("");
/* 6885 */     int _jspx_eval_html_005fcheckbox_005f20 = _jspx_th_html_005fcheckbox_005f20.doStartTag();
/* 6886 */     if (_jspx_th_html_005fcheckbox_005f20.doEndTag() == 5) {
/* 6887 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f20);
/* 6888 */       return true;
/*      */     }
/* 6890 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f20);
/* 6891 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f21(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6896 */     PageContext pageContext = _jspx_page_context;
/* 6897 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6899 */     CheckboxTag _jspx_th_html_005fcheckbox_005f21 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 6900 */     _jspx_th_html_005fcheckbox_005f21.setPageContext(_jspx_page_context);
/* 6901 */     _jspx_th_html_005fcheckbox_005f21.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6903 */     _jspx_th_html_005fcheckbox_005f21.setProperty("errorAlertScheduledTask");
/*      */     
/* 6905 */     _jspx_th_html_005fcheckbox_005f21.setOnclick("");
/* 6906 */     int _jspx_eval_html_005fcheckbox_005f21 = _jspx_th_html_005fcheckbox_005f21.doStartTag();
/* 6907 */     if (_jspx_th_html_005fcheckbox_005f21.doEndTag() == 5) {
/* 6908 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f21);
/* 6909 */       return true;
/*      */     }
/* 6911 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f21);
/* 6912 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6917 */     PageContext pageContext = _jspx_page_context;
/* 6918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6920 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6921 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6922 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6924 */     _jspx_th_c_005fout_005f4.setValue("${bulkexec}");
/* 6925 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6926 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6927 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6928 */       return true;
/*      */     }
/* 6930 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6931 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6936 */     PageContext pageContext = _jspx_page_context;
/* 6937 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6939 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6940 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 6941 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6943 */     _jspx_th_c_005fif_005f11.setTest("${bulkexec eq 'true'}");
/* 6944 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 6945 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 6947 */         out.write("checked=\"checked\"");
/* 6948 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 6949 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6953 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 6954 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 6955 */       return true;
/*      */     }
/* 6957 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 6958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6963 */     PageContext pageContext = _jspx_page_context;
/* 6964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6966 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6967 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6968 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6970 */     _jspx_th_c_005fout_005f5.setValue("${loginprefix}");
/* 6971 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6972 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6973 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6974 */       return true;
/*      */     }
/* 6976 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6982 */     PageContext pageContext = _jspx_page_context;
/* 6983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6985 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6986 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 6987 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6989 */     _jspx_th_c_005fif_005f12.setTest("${loginprefix eq 'true'}");
/* 6990 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 6991 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 6993 */         out.write("checked=\"checked\"");
/* 6994 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 6995 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6999 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 7000 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 7001 */       return true;
/*      */     }
/* 7003 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 7004 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7009 */     PageContext pageContext = _jspx_page_context;
/* 7010 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7012 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7013 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 7014 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7016 */     _jspx_th_c_005fout_005f6.setValue("${cliCaching}");
/* 7017 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 7018 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 7019 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7020 */       return true;
/*      */     }
/* 7022 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7023 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7028 */     PageContext pageContext = _jspx_page_context;
/* 7029 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7031 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7032 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 7033 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7035 */     _jspx_th_c_005fif_005f13.setTest("${cliCaching eq 'true'}");
/* 7036 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 7037 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 7039 */         out.write("checked=\"checked\"");
/* 7040 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 7041 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7045 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 7046 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 7047 */       return true;
/*      */     }
/* 7049 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 7050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7055 */     PageContext pageContext = _jspx_page_context;
/* 7056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7058 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7059 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 7060 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7062 */     _jspx_th_c_005fout_005f7.setValue("${resendvbscripts}");
/* 7063 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 7064 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 7065 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7066 */       return true;
/*      */     }
/* 7068 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7074 */     PageContext pageContext = _jspx_page_context;
/* 7075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7077 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7078 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 7079 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7081 */     _jspx_th_c_005fif_005f14.setTest("${resendvbscripts eq 'true'}");
/* 7082 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 7083 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 7085 */         out.write("checked=\"checked\"");
/* 7086 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 7087 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7091 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 7092 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 7093 */       return true;
/*      */     }
/* 7095 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 7096 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7101 */     PageContext pageContext = _jspx_page_context;
/* 7102 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7104 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7105 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 7106 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7108 */     _jspx_th_c_005fout_005f8.setValue("${enableRawData}");
/* 7109 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 7110 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 7111 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7112 */       return true;
/*      */     }
/* 7114 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7115 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7120 */     PageContext pageContext = _jspx_page_context;
/* 7121 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7123 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7124 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 7125 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7127 */     _jspx_th_c_005fif_005f15.setTest("${enableRawData eq 'true'}");
/* 7128 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 7129 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 7131 */         out.write("checked=\"checked\"");
/* 7132 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 7133 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7137 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 7138 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 7139 */       return true;
/*      */     }
/* 7141 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 7142 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f22(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7147 */     PageContext pageContext = _jspx_page_context;
/* 7148 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7150 */     CheckboxTag _jspx_th_html_005fcheckbox_005f22 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7151 */     _jspx_th_html_005fcheckbox_005f22.setPageContext(_jspx_page_context);
/* 7152 */     _jspx_th_html_005fcheckbox_005f22.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7154 */     _jspx_th_html_005fcheckbox_005f22.setProperty("enableNetAdapterMonitor");
/*      */     
/* 7156 */     _jspx_th_html_005fcheckbox_005f22.setOnclick("");
/* 7157 */     int _jspx_eval_html_005fcheckbox_005f22 = _jspx_th_html_005fcheckbox_005f22.doStartTag();
/* 7158 */     if (_jspx_th_html_005fcheckbox_005f22.doEndTag() == 5) {
/* 7159 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f22);
/* 7160 */       return true;
/*      */     }
/* 7162 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f22);
/* 7163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7168 */     PageContext pageContext = _jspx_page_context;
/* 7169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7171 */     RadioTag _jspx_th_html_005fradio_005f6 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7172 */     _jspx_th_html_005fradio_005f6.setPageContext(_jspx_page_context);
/* 7173 */     _jspx_th_html_005fradio_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7175 */     _jspx_th_html_005fradio_005f6.setProperty("hostHwMonitoring");
/*      */     
/* 7177 */     _jspx_th_html_005fradio_005f6.setValue("enable");
/*      */     
/* 7179 */     _jspx_th_html_005fradio_005f6.setOnclick("javascript:hwHealthMonDisablePrompt(this)");
/* 7180 */     int _jspx_eval_html_005fradio_005f6 = _jspx_th_html_005fradio_005f6.doStartTag();
/* 7181 */     if (_jspx_th_html_005fradio_005f6.doEndTag() == 5) {
/* 7182 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 7183 */       return true;
/*      */     }
/* 7185 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 7186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7191 */     PageContext pageContext = _jspx_page_context;
/* 7192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7194 */     RadioTag _jspx_th_html_005fradio_005f7 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7195 */     _jspx_th_html_005fradio_005f7.setPageContext(_jspx_page_context);
/* 7196 */     _jspx_th_html_005fradio_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7198 */     _jspx_th_html_005fradio_005f7.setProperty("hostHwMonitoring");
/*      */     
/* 7200 */     _jspx_th_html_005fradio_005f7.setValue("disable");
/*      */     
/* 7202 */     _jspx_th_html_005fradio_005f7.setOnclick("javascript:hwHealthMonDisablePrompt(this)");
/* 7203 */     int _jspx_eval_html_005fradio_005f7 = _jspx_th_html_005fradio_005f7.doStartTag();
/* 7204 */     if (_jspx_th_html_005fradio_005f7.doEndTag() == 5) {
/* 7205 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 7206 */       return true;
/*      */     }
/* 7208 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 7209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f23(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7214 */     PageContext pageContext = _jspx_page_context;
/* 7215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7217 */     CheckboxTag _jspx_th_html_005fcheckbox_005f23 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7218 */     _jspx_th_html_005fcheckbox_005f23.setPageContext(_jspx_page_context);
/* 7219 */     _jspx_th_html_005fcheckbox_005f23.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7221 */     _jspx_th_html_005fcheckbox_005f23.setProperty("temperature");
/*      */     
/* 7223 */     _jspx_th_html_005fcheckbox_005f23.setOnclick("");
/* 7224 */     int _jspx_eval_html_005fcheckbox_005f23 = _jspx_th_html_005fcheckbox_005f23.doStartTag();
/* 7225 */     if (_jspx_th_html_005fcheckbox_005f23.doEndTag() == 5) {
/* 7226 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f23);
/* 7227 */       return true;
/*      */     }
/* 7229 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f23);
/* 7230 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f24(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7235 */     PageContext pageContext = _jspx_page_context;
/* 7236 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7238 */     CheckboxTag _jspx_th_html_005fcheckbox_005f24 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7239 */     _jspx_th_html_005fcheckbox_005f24.setPageContext(_jspx_page_context);
/* 7240 */     _jspx_th_html_005fcheckbox_005f24.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7242 */     _jspx_th_html_005fcheckbox_005f24.setProperty("power");
/*      */     
/* 7244 */     _jspx_th_html_005fcheckbox_005f24.setOnclick("");
/* 7245 */     int _jspx_eval_html_005fcheckbox_005f24 = _jspx_th_html_005fcheckbox_005f24.doStartTag();
/* 7246 */     if (_jspx_th_html_005fcheckbox_005f24.doEndTag() == 5) {
/* 7247 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f24);
/* 7248 */       return true;
/*      */     }
/* 7250 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f24);
/* 7251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f25(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7256 */     PageContext pageContext = _jspx_page_context;
/* 7257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7259 */     CheckboxTag _jspx_th_html_005fcheckbox_005f25 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7260 */     _jspx_th_html_005fcheckbox_005f25.setPageContext(_jspx_page_context);
/* 7261 */     _jspx_th_html_005fcheckbox_005f25.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7263 */     _jspx_th_html_005fcheckbox_005f25.setProperty("fan");
/*      */     
/* 7265 */     _jspx_th_html_005fcheckbox_005f25.setOnclick("");
/* 7266 */     int _jspx_eval_html_005fcheckbox_005f25 = _jspx_th_html_005fcheckbox_005f25.doStartTag();
/* 7267 */     if (_jspx_th_html_005fcheckbox_005f25.doEndTag() == 5) {
/* 7268 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f25);
/* 7269 */       return true;
/*      */     }
/* 7271 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f25);
/* 7272 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f26(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7277 */     PageContext pageContext = _jspx_page_context;
/* 7278 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7280 */     CheckboxTag _jspx_th_html_005fcheckbox_005f26 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7281 */     _jspx_th_html_005fcheckbox_005f26.setPageContext(_jspx_page_context);
/* 7282 */     _jspx_th_html_005fcheckbox_005f26.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7284 */     _jspx_th_html_005fcheckbox_005f26.setProperty("processor");
/*      */     
/* 7286 */     _jspx_th_html_005fcheckbox_005f26.setOnclick("");
/* 7287 */     int _jspx_eval_html_005fcheckbox_005f26 = _jspx_th_html_005fcheckbox_005f26.doStartTag();
/* 7288 */     if (_jspx_th_html_005fcheckbox_005f26.doEndTag() == 5) {
/* 7289 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f26);
/* 7290 */       return true;
/*      */     }
/* 7292 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f26);
/* 7293 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f27(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7298 */     PageContext pageContext = _jspx_page_context;
/* 7299 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7301 */     CheckboxTag _jspx_th_html_005fcheckbox_005f27 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7302 */     _jspx_th_html_005fcheckbox_005f27.setPageContext(_jspx_page_context);
/* 7303 */     _jspx_th_html_005fcheckbox_005f27.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7305 */     _jspx_th_html_005fcheckbox_005f27.setProperty("disk");
/*      */     
/* 7307 */     _jspx_th_html_005fcheckbox_005f27.setOnclick("");
/* 7308 */     int _jspx_eval_html_005fcheckbox_005f27 = _jspx_th_html_005fcheckbox_005f27.doStartTag();
/* 7309 */     if (_jspx_th_html_005fcheckbox_005f27.doEndTag() == 5) {
/* 7310 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f27);
/* 7311 */       return true;
/*      */     }
/* 7313 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f27);
/* 7314 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f28(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7319 */     PageContext pageContext = _jspx_page_context;
/* 7320 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7322 */     CheckboxTag _jspx_th_html_005fcheckbox_005f28 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7323 */     _jspx_th_html_005fcheckbox_005f28.setPageContext(_jspx_page_context);
/* 7324 */     _jspx_th_html_005fcheckbox_005f28.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7326 */     _jspx_th_html_005fcheckbox_005f28.setProperty("array");
/*      */     
/* 7328 */     _jspx_th_html_005fcheckbox_005f28.setOnclick("");
/* 7329 */     int _jspx_eval_html_005fcheckbox_005f28 = _jspx_th_html_005fcheckbox_005f28.doStartTag();
/* 7330 */     if (_jspx_th_html_005fcheckbox_005f28.doEndTag() == 5) {
/* 7331 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f28);
/* 7332 */       return true;
/*      */     }
/* 7334 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f28);
/* 7335 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f29(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7340 */     PageContext pageContext = _jspx_page_context;
/* 7341 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7343 */     CheckboxTag _jspx_th_html_005fcheckbox_005f29 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7344 */     _jspx_th_html_005fcheckbox_005f29.setPageContext(_jspx_page_context);
/* 7345 */     _jspx_th_html_005fcheckbox_005f29.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7347 */     _jspx_th_html_005fcheckbox_005f29.setProperty("chassis");
/*      */     
/* 7349 */     _jspx_th_html_005fcheckbox_005f29.setOnclick("");
/* 7350 */     int _jspx_eval_html_005fcheckbox_005f29 = _jspx_th_html_005fcheckbox_005f29.doStartTag();
/* 7351 */     if (_jspx_th_html_005fcheckbox_005f29.doEndTag() == 5) {
/* 7352 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f29);
/* 7353 */       return true;
/*      */     }
/* 7355 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f29);
/* 7356 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f30(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7361 */     PageContext pageContext = _jspx_page_context;
/* 7362 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7364 */     CheckboxTag _jspx_th_html_005fcheckbox_005f30 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7365 */     _jspx_th_html_005fcheckbox_005f30.setPageContext(_jspx_page_context);
/* 7366 */     _jspx_th_html_005fcheckbox_005f30.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7368 */     _jspx_th_html_005fcheckbox_005f30.setProperty("memorydevice");
/*      */     
/* 7370 */     _jspx_th_html_005fcheckbox_005f30.setOnclick("");
/* 7371 */     int _jspx_eval_html_005fcheckbox_005f30 = _jspx_th_html_005fcheckbox_005f30.doStartTag();
/* 7372 */     if (_jspx_th_html_005fcheckbox_005f30.doEndTag() == 5) {
/* 7373 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f30);
/* 7374 */       return true;
/*      */     }
/* 7376 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f30);
/* 7377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f31(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7382 */     PageContext pageContext = _jspx_page_context;
/* 7383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7385 */     CheckboxTag _jspx_th_html_005fcheckbox_005f31 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7386 */     _jspx_th_html_005fcheckbox_005f31.setPageContext(_jspx_page_context);
/* 7387 */     _jspx_th_html_005fcheckbox_005f31.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7389 */     _jspx_th_html_005fcheckbox_005f31.setProperty("voltage");
/*      */     
/* 7391 */     _jspx_th_html_005fcheckbox_005f31.setOnclick("");
/* 7392 */     int _jspx_eval_html_005fcheckbox_005f31 = _jspx_th_html_005fcheckbox_005f31.doStartTag();
/* 7393 */     if (_jspx_th_html_005fcheckbox_005f31.doEndTag() == 5) {
/* 7394 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f31);
/* 7395 */       return true;
/*      */     }
/* 7397 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f31);
/* 7398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f32(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7403 */     PageContext pageContext = _jspx_page_context;
/* 7404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7406 */     CheckboxTag _jspx_th_html_005fcheckbox_005f32 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7407 */     _jspx_th_html_005fcheckbox_005f32.setPageContext(_jspx_page_context);
/* 7408 */     _jspx_th_html_005fcheckbox_005f32.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7410 */     _jspx_th_html_005fcheckbox_005f32.setProperty("battery");
/*      */     
/* 7412 */     _jspx_th_html_005fcheckbox_005f32.setOnclick("");
/* 7413 */     int _jspx_eval_html_005fcheckbox_005f32 = _jspx_th_html_005fcheckbox_005f32.doStartTag();
/* 7414 */     if (_jspx_th_html_005fcheckbox_005f32.doEndTag() == 5) {
/* 7415 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f32);
/* 7416 */       return true;
/*      */     }
/* 7418 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f32);
/* 7419 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7424 */     PageContext pageContext = _jspx_page_context;
/* 7425 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7427 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 7428 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 7429 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7431 */     _jspx_th_html_005ftextarea_005f0.setProperty("hwCriticalStatusMessage");
/*      */     
/* 7433 */     _jspx_th_html_005ftextarea_005f0.setCols("40");
/*      */     
/* 7435 */     _jspx_th_html_005ftextarea_005f0.setRows("3");
/*      */     
/* 7437 */     _jspx_th_html_005ftextarea_005f0.setStyle("width: 400px; margin: 2px 0px; height: 69px;");
/*      */     
/* 7439 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea");
/* 7440 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 7441 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 7442 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 7443 */       return true;
/*      */     }
/* 7445 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 7446 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7451 */     PageContext pageContext = _jspx_page_context;
/* 7452 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7454 */     TextareaTag _jspx_th_html_005ftextarea_005f1 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 7455 */     _jspx_th_html_005ftextarea_005f1.setPageContext(_jspx_page_context);
/* 7456 */     _jspx_th_html_005ftextarea_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7458 */     _jspx_th_html_005ftextarea_005f1.setProperty("hwWarningStatusMessage");
/*      */     
/* 7460 */     _jspx_th_html_005ftextarea_005f1.setCols("40");
/*      */     
/* 7462 */     _jspx_th_html_005ftextarea_005f1.setRows("3");
/*      */     
/* 7464 */     _jspx_th_html_005ftextarea_005f1.setStyle("width: 400px; margin: 2px 0px; height: 69px;");
/*      */     
/* 7466 */     _jspx_th_html_005ftextarea_005f1.setStyleClass("formtextarea");
/* 7467 */     int _jspx_eval_html_005ftextarea_005f1 = _jspx_th_html_005ftextarea_005f1.doStartTag();
/* 7468 */     if (_jspx_th_html_005ftextarea_005f1.doEndTag() == 5) {
/* 7469 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 7470 */       return true;
/*      */     }
/* 7472 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 7473 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7478 */     PageContext pageContext = _jspx_page_context;
/* 7479 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7481 */     TextareaTag _jspx_th_html_005ftextarea_005f2 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 7482 */     _jspx_th_html_005ftextarea_005f2.setPageContext(_jspx_page_context);
/* 7483 */     _jspx_th_html_005ftextarea_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7485 */     _jspx_th_html_005ftextarea_005f2.setProperty("hwClearStatusMessage");
/*      */     
/* 7487 */     _jspx_th_html_005ftextarea_005f2.setCols("40");
/*      */     
/* 7489 */     _jspx_th_html_005ftextarea_005f2.setRows("3");
/*      */     
/* 7491 */     _jspx_th_html_005ftextarea_005f2.setStyle("width: 400px; margin: 2px 0px; height: 69px;");
/*      */     
/* 7493 */     _jspx_th_html_005ftextarea_005f2.setStyleClass("formtextarea");
/* 7494 */     int _jspx_eval_html_005ftextarea_005f2 = _jspx_th_html_005ftextarea_005f2.doStartTag();
/* 7495 */     if (_jspx_th_html_005ftextarea_005f2.doEndTag() == 5) {
/* 7496 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 7497 */       return true;
/*      */     }
/* 7499 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 7500 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7505 */     PageContext pageContext = _jspx_page_context;
/* 7506 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7508 */     TextTag _jspx_th_html_005ftext_005f16 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7509 */     _jspx_th_html_005ftext_005f16.setPageContext(_jspx_page_context);
/* 7510 */     _jspx_th_html_005ftext_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7512 */     _jspx_th_html_005ftext_005f16.setProperty("pollInterval");
/*      */     
/* 7514 */     _jspx_th_html_005ftext_005f16.setStyleClass("formtext small");
/*      */     
/* 7516 */     _jspx_th_html_005ftext_005f16.setSize("3");
/* 7517 */     int _jspx_eval_html_005ftext_005f16 = _jspx_th_html_005ftext_005f16.doStartTag();
/* 7518 */     if (_jspx_th_html_005ftext_005f16.doEndTag() == 5) {
/* 7519 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f16);
/* 7520 */       return true;
/*      */     }
/* 7522 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f16);
/* 7523 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7528 */     PageContext pageContext = _jspx_page_context;
/* 7529 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7531 */     RadioTag _jspx_th_html_005fradio_005f8 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7532 */     _jspx_th_html_005fradio_005f8.setPageContext(_jspx_page_context);
/* 7533 */     _jspx_th_html_005fradio_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7535 */     _jspx_th_html_005fradio_005f8.setProperty("snmpVersion");
/*      */     
/* 7537 */     _jspx_th_html_005fradio_005f8.setValue("v1");
/* 7538 */     int _jspx_eval_html_005fradio_005f8 = _jspx_th_html_005fradio_005f8.doStartTag();
/* 7539 */     if (_jspx_th_html_005fradio_005f8.doEndTag() == 5) {
/* 7540 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 7541 */       return true;
/*      */     }
/* 7543 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 7544 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7549 */     PageContext pageContext = _jspx_page_context;
/* 7550 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7552 */     RadioTag _jspx_th_html_005fradio_005f9 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7553 */     _jspx_th_html_005fradio_005f9.setPageContext(_jspx_page_context);
/* 7554 */     _jspx_th_html_005fradio_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7556 */     _jspx_th_html_005fradio_005f9.setProperty("snmpVersion");
/*      */     
/* 7558 */     _jspx_th_html_005fradio_005f9.setValue("v2c");
/* 7559 */     int _jspx_eval_html_005fradio_005f9 = _jspx_th_html_005fradio_005f9.doStartTag();
/* 7560 */     if (_jspx_th_html_005fradio_005f9.doEndTag() == 5) {
/* 7561 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 7562 */       return true;
/*      */     }
/* 7564 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 7565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f33(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7570 */     PageContext pageContext = _jspx_page_context;
/* 7571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7573 */     CheckboxTag _jspx_th_html_005fcheckbox_005f33 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 7574 */     _jspx_th_html_005fcheckbox_005f33.setPageContext(_jspx_page_context);
/* 7575 */     _jspx_th_html_005fcheckbox_005f33.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7577 */     _jspx_th_html_005fcheckbox_005f33.setProperty("urlDebug");
/* 7578 */     int _jspx_eval_html_005fcheckbox_005f33 = _jspx_th_html_005fcheckbox_005f33.doStartTag();
/* 7579 */     if (_jspx_th_html_005fcheckbox_005f33.doEndTag() == 5) {
/* 7580 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f33);
/* 7581 */       return true;
/*      */     }
/* 7583 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f33);
/* 7584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f34(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7589 */     PageContext pageContext = _jspx_page_context;
/* 7590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7592 */     CheckboxTag _jspx_th_html_005fcheckbox_005f34 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 7593 */     _jspx_th_html_005fcheckbox_005f34.setPageContext(_jspx_page_context);
/* 7594 */     _jspx_th_html_005fcheckbox_005f34.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7596 */     _jspx_th_html_005fcheckbox_005f34.setProperty("urlResponses");
/* 7597 */     int _jspx_eval_html_005fcheckbox_005f34 = _jspx_th_html_005fcheckbox_005f34.doStartTag();
/* 7598 */     if (_jspx_th_html_005fcheckbox_005f34.doEndTag() == 5) {
/* 7599 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f34);
/* 7600 */       return true;
/*      */     }
/* 7602 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f34);
/* 7603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7608 */     PageContext pageContext = _jspx_page_context;
/* 7609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7611 */     RadioTag _jspx_th_html_005fradio_005f10 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7612 */     _jspx_th_html_005fradio_005f10.setPageContext(_jspx_page_context);
/* 7613 */     _jspx_th_html_005fradio_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7615 */     _jspx_th_html_005fradio_005f10.setProperty("amazonEC2PrimaryKey");
/*      */     
/* 7617 */     _jspx_th_html_005fradio_005f10.setValue("instanceId");
/*      */     
/* 7619 */     _jspx_th_html_005fradio_005f10.setOnclick("javascript:changeIdentifier()");
/* 7620 */     int _jspx_eval_html_005fradio_005f10 = _jspx_th_html_005fradio_005f10.doStartTag();
/* 7621 */     if (_jspx_th_html_005fradio_005f10.doEndTag() == 5) {
/* 7622 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 7623 */       return true;
/*      */     }
/* 7625 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 7626 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7631 */     PageContext pageContext = _jspx_page_context;
/* 7632 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7634 */     RadioTag _jspx_th_html_005fradio_005f11 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7635 */     _jspx_th_html_005fradio_005f11.setPageContext(_jspx_page_context);
/* 7636 */     _jspx_th_html_005fradio_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7638 */     _jspx_th_html_005fradio_005f11.setProperty("amazonEC2PrimaryKey");
/*      */     
/* 7640 */     _jspx_th_html_005fradio_005f11.setValue("nameTag");
/*      */     
/* 7642 */     _jspx_th_html_005fradio_005f11.setOnclick("javascript:changeIdentifier()");
/* 7643 */     int _jspx_eval_html_005fradio_005f11 = _jspx_th_html_005fradio_005f11.doStartTag();
/* 7644 */     if (_jspx_th_html_005fradio_005f11.doEndTag() == 5) {
/* 7645 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 7646 */       return true;
/*      */     }
/* 7648 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 7649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f35(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7654 */     PageContext pageContext = _jspx_page_context;
/* 7655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7657 */     CheckboxTag _jspx_th_html_005fcheckbox_005f35 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(CheckboxTag.class);
/* 7658 */     _jspx_th_html_005fcheckbox_005f35.setPageContext(_jspx_page_context);
/* 7659 */     _jspx_th_html_005fcheckbox_005f35.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7661 */     _jspx_th_html_005fcheckbox_005f35.setValue("true");
/*      */     
/* 7663 */     _jspx_th_html_005fcheckbox_005f35.setProperty("ec2AlertTerminatedInstance");
/* 7664 */     int _jspx_eval_html_005fcheckbox_005f35 = _jspx_th_html_005fcheckbox_005f35.doStartTag();
/* 7665 */     if (_jspx_th_html_005fcheckbox_005f35.doEndTag() == 5) {
/* 7666 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f35);
/* 7667 */       return true;
/*      */     }
/* 7669 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f35);
/* 7670 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f36(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7675 */     PageContext pageContext = _jspx_page_context;
/* 7676 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7678 */     CheckboxTag _jspx_th_html_005fcheckbox_005f36 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 7679 */     _jspx_th_html_005fcheckbox_005f36.setPageContext(_jspx_page_context);
/* 7680 */     _jspx_th_html_005fcheckbox_005f36.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7682 */     _jspx_th_html_005fcheckbox_005f36.setProperty("signatureVersion4SigningEnabled");
/* 7683 */     int _jspx_eval_html_005fcheckbox_005f36 = _jspx_th_html_005fcheckbox_005f36.doStartTag();
/* 7684 */     if (_jspx_th_html_005fcheckbox_005f36.doEndTag() == 5) {
/* 7685 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f36);
/* 7686 */       return true;
/*      */     }
/* 7688 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f36);
/* 7689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7694 */     PageContext pageContext = _jspx_page_context;
/* 7695 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7697 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 7698 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 7699 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7701 */     _jspx_th_html_005fselect_005f4.setProperty("maintenanceCombo1");
/*      */     
/* 7703 */     _jspx_th_html_005fselect_005f4.setStyle("width:100%");
/*      */     
/* 7705 */     _jspx_th_html_005fselect_005f4.setMultiple("true");
/*      */     
/* 7707 */     _jspx_th_html_005fselect_005f4.setSize("10");
/* 7708 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 7709 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 7710 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 7711 */         out = _jspx_page_context.pushBody();
/* 7712 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 7713 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7716 */         out.write(10);
/* 7717 */         out.write(9);
/* 7718 */         out.write(9);
/* 7719 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 7720 */           return true;
/* 7721 */         out.write("\n\t  ");
/* 7722 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 7723 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7726 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 7727 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7730 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 7731 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f4);
/* 7732 */       return true;
/*      */     }
/* 7734 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f4);
/* 7735 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7740 */     PageContext pageContext = _jspx_page_context;
/* 7741 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7743 */     org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (org.apache.struts.taglib.html.OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
/* 7744 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 7745 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 7747 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("toAdd");
/* 7748 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 7749 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 7750 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 7751 */       return true;
/*      */     }
/* 7753 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 7754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7759 */     PageContext pageContext = _jspx_page_context;
/* 7760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7762 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 7763 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 7764 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7766 */     _jspx_th_html_005fselect_005f5.setProperty("maintenanceCombo2");
/*      */     
/* 7768 */     _jspx_th_html_005fselect_005f5.setStyle("width:100%");
/*      */     
/* 7770 */     _jspx_th_html_005fselect_005f5.setMultiple("true");
/*      */     
/* 7772 */     _jspx_th_html_005fselect_005f5.setSize("10");
/* 7773 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 7774 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 7775 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 7776 */         out = _jspx_page_context.pushBody();
/* 7777 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 7778 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7781 */         out.write(10);
/* 7782 */         out.write(9);
/* 7783 */         out.write(9);
/* 7784 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 7785 */           return true;
/* 7786 */         out.write("\n\t  ");
/* 7787 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 7788 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7791 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 7792 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7795 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 7796 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f5);
/* 7797 */       return true;
/*      */     }
/* 7799 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f5);
/* 7800 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7805 */     PageContext pageContext = _jspx_page_context;
/* 7806 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7808 */     org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (org.apache.struts.taglib.html.OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
/* 7809 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 7810 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 7812 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("present");
/* 7813 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 7814 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 7815 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 7816 */       return true;
/*      */     }
/* 7818 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 7819 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7824 */     PageContext pageContext = _jspx_page_context;
/* 7825 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7827 */     RadioTag _jspx_th_html_005fradio_005f12 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7828 */     _jspx_th_html_005fradio_005f12.setPageContext(_jspx_page_context);
/* 7829 */     _jspx_th_html_005fradio_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7831 */     _jspx_th_html_005fradio_005f12.setProperty("mysqlTableData");
/*      */     
/* 7833 */     _jspx_th_html_005fradio_005f12.setValue("every");
/* 7834 */     int _jspx_eval_html_005fradio_005f12 = _jspx_th_html_005fradio_005f12.doStartTag();
/* 7835 */     if (_jspx_th_html_005fradio_005f12.doEndTag() == 5) {
/* 7836 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f12);
/* 7837 */       return true;
/*      */     }
/* 7839 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f12);
/* 7840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7845 */     PageContext pageContext = _jspx_page_context;
/* 7846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7848 */     RadioTag _jspx_th_html_005fradio_005f13 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7849 */     _jspx_th_html_005fradio_005f13.setPageContext(_jspx_page_context);
/* 7850 */     _jspx_th_html_005fradio_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7852 */     _jspx_th_html_005fradio_005f13.setProperty("mysqlTableData");
/*      */     
/* 7854 */     _jspx_th_html_005fradio_005f13.setValue("onceaday");
/* 7855 */     int _jspx_eval_html_005fradio_005f13 = _jspx_th_html_005fradio_005f13.doStartTag();
/* 7856 */     if (_jspx_th_html_005fradio_005f13.doEndTag() == 5) {
/* 7857 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f13);
/* 7858 */       return true;
/*      */     }
/* 7860 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f13);
/* 7861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7866 */     PageContext pageContext = _jspx_page_context;
/* 7867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7869 */     RadioTag _jspx_th_html_005fradio_005f14 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7870 */     _jspx_th_html_005fradio_005f14.setPageContext(_jspx_page_context);
/* 7871 */     _jspx_th_html_005fradio_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7873 */     _jspx_th_html_005fradio_005f14.setProperty("mysqlTableData");
/*      */     
/* 7875 */     _jspx_th_html_005fradio_005f14.setValue("never");
/* 7876 */     int _jspx_eval_html_005fradio_005f14 = _jspx_th_html_005fradio_005f14.doStartTag();
/* 7877 */     if (_jspx_th_html_005fradio_005f14.doEndTag() == 5) {
/* 7878 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f14);
/* 7879 */       return true;
/*      */     }
/* 7881 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f14);
/* 7882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7887 */     PageContext pageContext = _jspx_page_context;
/* 7888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7890 */     RadioTag _jspx_th_html_005fradio_005f15 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7891 */     _jspx_th_html_005fradio_005f15.setPageContext(_jspx_page_context);
/* 7892 */     _jspx_th_html_005fradio_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7894 */     _jspx_th_html_005fradio_005f15.setProperty("sybaseDBDetails");
/*      */     
/* 7896 */     _jspx_th_html_005fradio_005f15.setValue("every");
/* 7897 */     int _jspx_eval_html_005fradio_005f15 = _jspx_th_html_005fradio_005f15.doStartTag();
/* 7898 */     if (_jspx_th_html_005fradio_005f15.doEndTag() == 5) {
/* 7899 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f15);
/* 7900 */       return true;
/*      */     }
/* 7902 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f15);
/* 7903 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7908 */     PageContext pageContext = _jspx_page_context;
/* 7909 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7911 */     RadioTag _jspx_th_html_005fradio_005f16 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7912 */     _jspx_th_html_005fradio_005f16.setPageContext(_jspx_page_context);
/* 7913 */     _jspx_th_html_005fradio_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7915 */     _jspx_th_html_005fradio_005f16.setProperty("sybaseDBDetails");
/*      */     
/* 7917 */     _jspx_th_html_005fradio_005f16.setValue("onceaday");
/* 7918 */     int _jspx_eval_html_005fradio_005f16 = _jspx_th_html_005fradio_005f16.doStartTag();
/* 7919 */     if (_jspx_th_html_005fradio_005f16.doEndTag() == 5) {
/* 7920 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f16);
/* 7921 */       return true;
/*      */     }
/* 7923 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f16);
/* 7924 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7929 */     PageContext pageContext = _jspx_page_context;
/* 7930 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7932 */     RadioTag _jspx_th_html_005fradio_005f17 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7933 */     _jspx_th_html_005fradio_005f17.setPageContext(_jspx_page_context);
/* 7934 */     _jspx_th_html_005fradio_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7936 */     _jspx_th_html_005fradio_005f17.setProperty("sybaseDBDetails");
/*      */     
/* 7938 */     _jspx_th_html_005fradio_005f17.setValue("never");
/* 7939 */     int _jspx_eval_html_005fradio_005f17 = _jspx_th_html_005fradio_005f17.doStartTag();
/* 7940 */     if (_jspx_th_html_005fradio_005f17.doEndTag() == 5) {
/* 7941 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f17);
/* 7942 */       return true;
/*      */     }
/* 7944 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f17);
/* 7945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f37(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7950 */     PageContext pageContext = _jspx_page_context;
/* 7951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7953 */     CheckboxTag _jspx_th_html_005fcheckbox_005f37 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fnobody.get(CheckboxTag.class);
/* 7954 */     _jspx_th_html_005fcheckbox_005f37.setPageContext(_jspx_page_context);
/* 7955 */     _jspx_th_html_005fcheckbox_005f37.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7957 */     _jspx_th_html_005fcheckbox_005f37.setProperty("failedScheduledBackupJobs");
/*      */     
/* 7959 */     _jspx_th_html_005fcheckbox_005f37.setStyle("position:relative;");
/* 7960 */     int _jspx_eval_html_005fcheckbox_005f37 = _jspx_th_html_005fcheckbox_005f37.doStartTag();
/* 7961 */     if (_jspx_th_html_005fcheckbox_005f37.doEndTag() == 5) {
/* 7962 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f37);
/* 7963 */       return true;
/*      */     }
/* 7965 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f37);
/* 7966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7971 */     PageContext pageContext = _jspx_page_context;
/* 7972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7974 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7975 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 7976 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f1);
/*      */     
/* 7978 */     _jspx_th_c_005fif_005f16.setTest("${mssqlScheduledJobs eq 'true'}");
/* 7979 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 7980 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 7982 */         out.write("checked=\"checked\"");
/* 7983 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 7984 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7988 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 7989 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 7990 */       return true;
/*      */     }
/* 7992 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 7993 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7998 */     PageContext pageContext = _jspx_page_context;
/* 7999 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8001 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8002 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 8003 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f1);
/*      */     
/* 8005 */     _jspx_th_c_005fout_005f9.setValue("${mssqldeleteJobs}");
/* 8006 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 8007 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 8008 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 8009 */       return true;
/*      */     }
/* 8011 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 8012 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8017 */     PageContext pageContext = _jspx_page_context;
/* 8018 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8020 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8021 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 8022 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_tiles_005fput_005f1);
/*      */     
/* 8024 */     _jspx_th_c_005fif_005f17.setTest("${mssqldeleteJobs eq 'true'}");
/* 8025 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 8026 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 8028 */         out.write("checked=\"checked\"");
/* 8029 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 8030 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8034 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 8035 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 8036 */       return true;
/*      */     }
/* 8038 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 8039 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8044 */     PageContext pageContext = _jspx_page_context;
/* 8045 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8047 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8048 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 8049 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f1);
/*      */     
/* 8051 */     _jspx_th_c_005fout_005f10.setValue("${backupCollectionPeriod}");
/* 8052 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 8053 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 8054 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 8055 */       return true;
/*      */     }
/* 8057 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 8058 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8063 */     PageContext pageContext = _jspx_page_context;
/* 8064 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8066 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8067 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 8068 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f1);
/*      */     
/* 8070 */     _jspx_th_c_005fif_005f18.setTest("${sqlReplMonStatus eq 'true'}");
/* 8071 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 8072 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 8074 */         out.write("checked=\"checked\"");
/* 8075 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 8076 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8080 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 8081 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 8082 */       return true;
/*      */     }
/* 8084 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 8085 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8090 */     PageContext pageContext = _jspx_page_context;
/* 8091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8093 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 8094 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 8095 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f1);
/*      */     
/* 8097 */     _jspx_th_c_005fif_005f19.setTest("${sqlReplMonHistoryStatus eq 'true'}");
/* 8098 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 8099 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 8101 */         out.write("checked=\"checked\"");
/* 8102 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 8103 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 8107 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 8108 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 8109 */       return true;
/*      */     }
/* 8111 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 8112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_tiles_005fput_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8117 */     PageContext pageContext = _jspx_page_context;
/* 8118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8120 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8121 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 8122 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f1);
/*      */     
/* 8124 */     _jspx_th_c_005fout_005f11.setValue("${replAgentHistoryMaintenancePeriod}");
/* 8125 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 8126 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 8127 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 8128 */       return true;
/*      */     }
/* 8130 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 8131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8136 */     PageContext pageContext = _jspx_page_context;
/* 8137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8139 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 8140 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 8141 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 8143 */     _jspx_th_tiles_005fput_005f2.setName("HelpContent");
/*      */     
/* 8145 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/test.jsp");
/* 8146 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 8147 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 8148 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 8149 */       return true;
/*      */     }
/* 8151 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 8152 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8157 */     PageContext pageContext = _jspx_page_context;
/* 8158 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8160 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 8161 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 8162 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 8164 */     _jspx_th_tiles_005fput_005f3.setName("Footer");
/*      */     
/* 8166 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 8167 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 8168 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 8169 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 8170 */       return true;
/*      */     }
/* 8172 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 8173 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\GlobalSettings_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */