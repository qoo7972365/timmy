/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.EnterpriseAdminLink;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class ScheduleReportDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   39 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   45 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(3);
/*   46 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*   47 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*   48 */     _jspx_dependants.put("/jsp/includes/EnterpriseAdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonmouseout_005fonblur_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   90 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   94 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  101 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  102 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  103 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  104 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  105 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  106 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  107 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  108 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  109 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  110 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonmouseout_005fonblur_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  111 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  112 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  113 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  114 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  115 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  116 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  117 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  118 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  119 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  120 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  121 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  122 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  123 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  124 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  125 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  126 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  127 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  128 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  129 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  133 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  134 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  135 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  136 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  137 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  138 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/*  139 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  140 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*  141 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  142 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  143 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  144 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  145 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.release();
/*  146 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*  147 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  148 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  149 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonmouseout_005fonblur_005fmaxlength_005fnobody.release();
/*  150 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.release();
/*  151 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  152 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  153 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  154 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.release();
/*  155 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*  156 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  157 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  158 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  159 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  160 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange.release();
/*  161 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  162 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.release();
/*  163 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  164 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.release();
/*  165 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.release();
/*  166 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  173 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  176 */     JspWriter out = null;
/*  177 */     Object page = this;
/*  178 */     JspWriter _jspx_out = null;
/*  179 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  183 */       response.setContentType("text/html;charset=UTF-8");
/*  184 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  186 */       _jspx_page_context = pageContext;
/*  187 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  188 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  189 */       session = pageContext.getSession();
/*  190 */       out = pageContext.getOut();
/*  191 */       _jspx_out = out;
/*      */       
/*  193 */       out.write("<!DOCTYPE html>\n");
/*  194 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  195 */       out.write(10);
/*      */       
/*  197 */       request.setAttribute("HelpKey", "Schedule Reports");
/*      */       
/*  199 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  200 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  202 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  203 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  204 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\n");
/*      */       try {
/*  206 */         out.write("\n\n\n   ");
/*      */         
/*  208 */         String loginUserName = request.getRemoteUser();
/*  209 */         String sizeofmo = (String)request.getAttribute("mosize");
/*  210 */         int SIZE = Integer.parseInt(sizeofmo);
/*  211 */         Properties pq = com.adventnet.appmanager.util.DBUtil.getManagedResourceTypesCount();
/*  212 */         String RV = pq.getProperty("israw");
/*  213 */         String PV = pq.getProperty("rawvalue");
/*  214 */         com.adventnet.appmanager.struts.form.AMActionForm frm = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*      */         
/*  216 */         if (com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(loginUserName)) {
/*  217 */           com.adventnet.appmanager.reporting.form.ReportForm rfrm = new com.adventnet.appmanager.reporting.form.ReportForm();
/*  218 */           rfrm.setRemoteUser(loginUserName);
/*  219 */           rfrm.setIsUserOpr(true);
/*      */         } else {
/*  221 */           com.adventnet.appmanager.reporting.form.ReportForm rfrm = new com.adventnet.appmanager.reporting.form.ReportForm();
/*  222 */           rfrm.setRemoteUser(null);
/*  223 */           rfrm.setIsUserOpr(false);
/*      */         }
/*  225 */         String newperiod = frm.getTypeofperiod();
/*  226 */         String appcount = pq.getProperty("APP");
/*  227 */         String dbcount = pq.getProperty("DBS");
/*  228 */         String erpcount = pq.getProperty("ERP");
/*  229 */         String syscount = pq.getProperty("SYS");
/*  230 */         String sercount = pq.getProperty("SER");
/*  231 */         String urlcount = pq.getProperty("URL");
/*      */         
/*  233 */         String mscount = pq.getProperty("MS");
/*  234 */         String camcount = pq.getProperty("CAM");
/*  235 */         String mgcount = pq.getProperty("HAI");
/*  236 */         String momcount = pq.getProperty("MOM");
/*  237 */         String virCount = pq.getProperty("VIR");
/*  238 */         String cldCount = pq.getProperty("CLD");
/*  239 */         System.out.println("########## cldCount : " + cldCount);
/*  240 */         String customFieldID = request.getAttribute("customFieldID") != null ? (String)request.getAttribute("customFieldID") : "";
/*  241 */         String customFieldValueID = request.getAttribute("customFieldValueID") != null ? (String)request.getAttribute("customFieldValueID") : "";
/*      */         
/*      */ 
/*  244 */         if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  246 */           ArrayList dynamicSites = com.adventnet.appmanager.struts.beans.AlarmUtil.getSiteMonitorGroups();
/*  247 */           if (dynamicSites != null)
/*      */           {
/*  249 */             request.setAttribute("dynamicSites", dynamicSites);
/*      */           }
/*      */         }
/*      */         
/*  253 */         out.write("\n<script>\n\njQuery(document).ready(function() //No I18N\n{\n\tdocument.getElementById(\"customFieldID\").value=\"");
/*  254 */         out.print(customFieldID);
/*  255 */         out.write("\";\n\tdocument.getElementById(\"customFieldValueID\").value=\"");
/*  256 */         out.print(customFieldValueID);
/*  257 */         out.write("\";\n\n\tsortSelectItemsAndRetainFirstElement(\"trapName\",\"");
/*  258 */         out.print(FormatUtil.getString("am.monitortab.allmonitors.text"));
/*  259 */         out.write("\");//No I18N\n\t\n\tjQuery(\".chosenselect\").chosen();\t\t// NO I18N\t\n\tvar growthTrend = '");
/*  260 */         if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */           return;
/*  262 */         out.write("'\n\tif(growthTrend != ''){\n\tjQuery(\"[name=forecastTrend]\").val(growthTrend)\t\t// NO I18N\n\t}\n\tvar selectedVal = jQuery('select.actionSelectCSS option:selected').val();\t//No I18N\n\tvar urlToPass = '/adminAction.do?method=showEmailAction&haid=null&fromSchedule=true&actionID='+selectedVal+'&sid=");
/*  263 */         out.print(request.getParameter("sid"));
/*  264 */         out.write("';\t//No I18N\n\t\n\tjQuery(\"#actionEditLink\").attr(\"href\",urlToPass);\t//No I18N\n\t \n\tvar actionIDs=");
/*  265 */         out.print(com.manageengine.appmanager.util.DelegatedUserRoleUtil.getConfigIDsOwnedByUser(com.manageengine.appmanager.util.DelegatedUserRoleUtil.getLoginUserid(request), 2));
/*  266 */         out.write("\n\tfor(var i=0; i<actionIDs.length; i++){\n       \t\tif(actionIDs[i] != selectedVal){\n       \t\t\tjQuery(\"#actionEditLink,.ancillary1\").hide();\t\t//NO I18N\n       \t\t}else{\n       \t\t\tjQuery(\"#actionEditLink,.ancillary1\").show();\t\t//NO I18N\t\n       \t\t}\n       \t}\n\tjQuery('.actionSelectCSS').change(function()  //No I18N\n        {\n        \n    \tselectedVal = jQuery(this).val();\t//No I18N   \t\n    \turlToPass = '/adminAction.do?method=showEmailAction&haid=null&fromSchedule=true&actionID='+selectedVal+'&sid=");
/*  267 */         out.print(request.getParameter("sid"));
/*  268 */         out.write("';\t//No I18N\n        jQuery(\"#actionEditLink\").attr(\"href\",urlToPass);\t//No I18N\n        ");
/*  269 */         if (com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(request.getRemoteUser())) {
/*  270 */           out.write("\n        for(var i=0; i<actionIDs.length; i++){\n       \t\tif(actionIDs[i] != selectedVal){\n       \t\t\tjQuery(\"#actionEditLink,.ancillary1\").hide();\t\t//NO I18N\n       \t\t}else{\n       \t\t\tjQuery(\"#actionEditLink,.ancillary1\").show();\t\t//NO I18N\n       \t\t}\n       \t}\n       \t");
/*      */         }
/*  272 */         out.write("\n       \t\n    \t})\n    \t\n    \t");
/*      */         
/*  274 */         if (com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(loginUserName))
/*      */         {
/*  276 */           out.write("\n    \tjQuery(\"#sqlmanOP2\").hide();\t//NO I18N\n    \t");
/*      */         }
/*  278 */         out.write("\n});\n\nfunction loadSite()\n\t{\n  \t         document.AMActionForm.haid.options.length=0;\n  \t         var formCustomerId = document.AMActionForm.organization.value;\n  \t         var siteName;\n  \t         var siteId;\n  \t         var customerId;\n  \t         var rowCount=0;\n  \t         document.AMActionForm.haid.options[rowCount++] = new Option('-Select Site-','-1'); //No I18N\n  \t         ");
/*  279 */         if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */           return;
/*  281 */         out.write("\n\t }\t\nfunction checkMonitorGroup()\n{\nif(document.getElementById(\"MG-99\")){\nif(document.getElementById(\"MG-99\").checked==true)\n        {\n            ");
/*  282 */         for (int i = 0; i < Integer.parseInt(mgcount); i++)
/*      */         {
/*  284 */           out.write("\n            document.getElementById(\"MG-");
/*  285 */           out.print(i);
/*  286 */           out.write("\").checked=true;\n            document.getElementById(\"MG-");
/*  287 */           out.print(i);
/*  288 */           out.write("\").disabled=true;\n            ");
/*      */         }
/*  290 */         out.write("\n\n\n        }\n\n   else\n   {\n\n             ");
/*  291 */         for (int i = 0; i < Integer.parseInt(mgcount); i++)
/*      */         {
/*  293 */           out.write("\n            document.getElementById(\"MG-");
/*  294 */           out.print(i);
/*  295 */           out.write("\").checked=false;\n             document.getElementById(\"MG-");
/*  296 */           out.print(i);
/*  297 */           out.write("\").disabled=false;\n            ");
/*      */         }
/*  299 */         out.write("\n\n\n\n   }\n\n}\n}\nfunction checkAppServer()\n{\n\n        if(document.getElementById(\"1\").checked==true)\n        {\n          ");
/*  300 */         for (int i = 0; i < Integer.parseInt(appcount); i++)
/*      */         {
/*  302 */           out.write("\n            document.getElementById(\"APP-");
/*  303 */           out.print(i);
/*  304 */           out.write("\").checked=true;\n            document.getElementById(\"APP-");
/*  305 */           out.print(i);
/*  306 */           out.write("\").disabled=true;\n          ");
/*      */         }
/*  308 */         out.write("\n        }\n\n\n      else\n        {\n           ");
/*  309 */         for (int i = 0; i < Integer.parseInt(appcount); i++)
/*      */         {
/*  311 */           out.write("\n            document.getElementById(\"APP-");
/*  312 */           out.print(i);
/*  313 */           out.write("\").checked=false;\n             document.getElementById(\"APP-");
/*  314 */           out.print(i);
/*  315 */           out.write("\").disabled=false;\n          ");
/*      */         }
/*  317 */         out.write("\n\n        }\n\n\n }\n function checkDBServer()\n {\n      if(document.getElementById(\"7\").checked==true)\n        {\n            ");
/*  318 */         for (int i = 0; i < Integer.parseInt(dbcount); i++)
/*      */         {
/*  320 */           out.write("\n            document.getElementById(\"DB-");
/*  321 */           out.print(i);
/*  322 */           out.write("\").checked=true;\n            document.getElementById(\"DB-");
/*  323 */           out.print(i);
/*  324 */           out.write("\").disabled=true;\n            ");
/*      */         }
/*  326 */         out.write("\n\n\n        }\n\n    else\n    {\n              ");
/*  327 */         for (int i = 0; i < Integer.parseInt(dbcount); i++)
/*      */         {
/*  329 */           out.write("\n            document.getElementById(\"DB-");
/*  330 */           out.print(i);
/*  331 */           out.write("\").checked=false;\n             document.getElementById(\"DB-");
/*  332 */           out.print(i);
/*  333 */           out.write("\").disabled=false;\n            ");
/*      */         }
/*  335 */         out.write("\n\n\n     }\n\n\n }\n function checkServer()\n {\n         if(document.getElementById(\"19\").checked==true)\n            {\n                ");
/*  336 */         for (int i = 0; i < Integer.parseInt(syscount); i++)
/*      */         {
/*  338 */           out.write("\n                document.getElementById(\"servers-");
/*  339 */           out.print(i);
/*  340 */           out.write("\").checked=true;\n                document.getElementById(\"servers-");
/*  341 */           out.print(i);
/*  342 */           out.write("\").disabled=true;\n                ");
/*      */         }
/*  344 */         out.write("\n\n            }\n\n\n        else\n        {\n\n                ");
/*  345 */         for (int i = 0; i < Integer.parseInt(syscount); i++)
/*      */         {
/*  347 */           out.write("\n                document.getElementById(\"servers-");
/*  348 */           out.print(i);
/*  349 */           out.write("\").checked=false;\n                document.getElementById(\"servers-");
/*  350 */           out.print(i);
/*  351 */           out.write("\").disabled=false;\n                ");
/*      */         }
/*  353 */         out.write("\n\n\n        }\n\n }\n\n function checkWebServer()\n {\n\n         if(document.getElementById(\"12\").checked==true)\n        {\n            ");
/*  354 */         for (int i = 0; i < Integer.parseInt(urlcount); i++)
/*      */         {
/*  356 */           out.write("\n            document.getElementById(\"web-");
/*  357 */           out.print(i);
/*  358 */           out.write("\").checked=true;\n            document.getElementById(\"web-");
/*  359 */           out.print(i);
/*  360 */           out.write("\").disabled=true;\n            ");
/*      */         }
/*  362 */         out.write("\n\n        }\n   else\n   {\n\n            ");
/*  363 */         for (int i = 0; i < Integer.parseInt(urlcount); i++)
/*      */         {
/*  365 */           out.write("\n            document.getElementById(\"web-");
/*  366 */           out.print(i);
/*  367 */           out.write("\").checked=false;\n             document.getElementById(\"web-");
/*  368 */           out.print(i);
/*  369 */           out.write("\").disabled=false;\n            ");
/*      */         }
/*  371 */         out.write("\n\n   }\n}\nfunction checkMailServer()\n {\n\n         if(document.getElementById(\"26\").checked==true)\n        {\n          ");
/*  372 */         for (int i = 0; i < Integer.parseInt(mscount); i++)
/*      */         {
/*  374 */           out.write("\n            document.getElementById(\"mail-");
/*  375 */           out.print(i);
/*  376 */           out.write("\").checked=true;\n            document.getElementById(\"mail-");
/*  377 */           out.print(i);
/*  378 */           out.write("\").disabled=true;\n\n            ");
/*      */         }
/*  380 */         out.write("\n        }\n\n\n   else\n   {\n\n             ");
/*  381 */         for (int i = 0; i < Integer.parseInt(mscount); i++)
/*      */         {
/*  383 */           out.write("\n            document.getElementById(\"mail-");
/*  384 */           out.print(i);
/*  385 */           out.write("\").checked=false;\n            document.getElementById(\"mail-");
/*  386 */           out.print(i);
/*  387 */           out.write("\").disabled=false;\n\n            ");
/*      */         }
/*  389 */         out.write("\n\n\n   }\n}\n\nfunction checkCustomAttributeServer()\n {\n\t\tvar len=document.getElementsByName(\"resourcestypes\").length;\n\t\tvar i;\n        if(document.getElementById('10000').checked==true){\n\n        \tfor(i=1;i<len;i++){\n        \t\tdocument.getElementsByName(\"resourcestypes\")[i].checked=true;\n        \t\tdocument.getElementsByName(\"resourcestypes\")[i].disabled=true;\n        \t}\n\n        }else{\n\n        \tfor(i=1;i<len;i++){\n        \t\tdocument.getElementsByName(\"resourcestypes\")[i].checked=false;\n        \t\tdocument.getElementsByName(\"resourcestypes\")[i].disabled=false;\n        \t}\n\n        }\n        //var allResourcetype=document.getElementById('10000').value;\n        //var url=\"/scheduleReports.do?method=sendCountDetails&resgroup=\"+allResourcetype;\n        //http.open(\"GET\",url,true);\n        //http.onreadystatechange = checkResourceTypes;\n        //http.send(null);\n\n}\nfunction checkResourceTypes()\n{\n    if(http.readyState == 4)\n    {\n\n        var result = http.responseText;\n\t\tvar len=document.getElementsByName(\"resourcestypes\").length;\n");
/*  390 */         out.write("\t\tvar i;\n        if(document.getElementById('10000').checked==true){\n\n        \tfor(i=1;i<len;i++){\n        \t\tdocument.getElementsByName(\"resourcestypes\")[i].checked=true;\n        \t\tdocument.getElementsByName(\"resourcestypes\")[i].disabled=true;\n        \t}\n\n        }else{\n        \tfor(i=1;i<len;i++){\n        \t\tdocument.getElementsByName(\"resourcestypes\")[i].checked=false;\n        \t\tdocument.getElementsByName(\"resourcestypes\")[i].disabled=false;\n        \t}\n        }\n\n\n    }\n }\nfunction checkERPServer()\n {\n\n         if(document.getElementById(\"70\").checked==true)\n        {\n             ");
/*  391 */         for (int i = 0; i < Integer.parseInt(erpcount); i++)
/*      */         {
/*  393 */           out.write("\n            document.getElementById(\"erpservers-");
/*  394 */           out.print(i);
/*  395 */           out.write("\").checked=true;\n            document.getElementById(\"erpservers-");
/*  396 */           out.print(i);
/*  397 */           out.write("\").disabled=true;\n           ");
/*      */         }
/*  399 */         out.write("\n        }\n\n\n   else\n   {\n              ");
/*  400 */         for (int i = 0; i < Integer.parseInt(erpcount); i++)
/*      */         {
/*  402 */           out.write("\n            document.getElementById(\"erpservers-");
/*  403 */           out.print(i);
/*  404 */           out.write("\").checked=false;\n            document.getElementById(\"erpservers-");
/*  405 */           out.print(i);
/*  406 */           out.write("\").disabled=false;\n            ");
/*      */         }
/*  408 */         out.write("\n   }\n}\nfunction checkMOMServer()\n {\n\n         if(document.getElementById(\"80\").checked==true)\n        {\n             ");
/*  409 */         for (int i = 0; i < Integer.parseInt(momcount); i++)
/*      */         {
/*  411 */           out.write("\n            document.getElementById(\"middleware-");
/*  412 */           out.print(i);
/*  413 */           out.write("\").checked=true;\n            document.getElementById(\"middleware-");
/*  414 */           out.print(i);
/*  415 */           out.write("\").disabled=true;\n           ");
/*      */         }
/*  417 */         out.write("\n        }\n\n\n   else\n   {\n              ");
/*  418 */         for (int i = 0; i < Integer.parseInt(momcount); i++)
/*      */         {
/*  420 */           out.write("\n            document.getElementById(\"middleware-");
/*  421 */           out.print(i);
/*  422 */           out.write("\").checked=false;\n            document.getElementById(\"middleware-");
/*  423 */           out.print(i);
/*  424 */           out.write("\").disabled=false;\n            ");
/*      */         }
/*  426 */         out.write("\n   }\n}\n\nfunction checkVirtualServer()\n{\n\n        if(document.getElementById(\"100\").checked==true)\n       {\n            ");
/*  427 */         for (int i = 0; i < Integer.parseInt(virCount); i++)
/*      */         {
/*  429 */           out.write("\n           document.getElementById(\"vir-");
/*  430 */           out.print(i);
/*  431 */           out.write("\").checked=true;\n           document.getElementById(\"vir-");
/*  432 */           out.print(i);
/*  433 */           out.write("\").disabled=true;\n          ");
/*      */         }
/*  435 */         out.write("\n       }\n\n\n  else\n  {\n             ");
/*  436 */         for (int i = 0; i < Integer.parseInt(virCount); i++)
/*      */         {
/*  438 */           out.write("\n           document.getElementById(\"vir-");
/*  439 */           out.print(i);
/*  440 */           out.write("\").checked=false;\n           document.getElementById(\"vir-");
/*  441 */           out.print(i);
/*  442 */           out.write("\").disabled=false;\n           ");
/*      */         }
/*  444 */         out.write("\n  }\n}\n\nfunction checkCloudApps()\n{\n\n        if(document.getElementById(\"110\").checked==true)\n       {\n            ");
/*  445 */         for (int i = 0; i < Integer.parseInt(cldCount); i++)
/*      */         {
/*  447 */           out.write("\n           document.getElementById(\"cld-");
/*  448 */           out.print(i);
/*  449 */           out.write("\").checked=true;\n           document.getElementById(\"cld-");
/*  450 */           out.print(i);
/*  451 */           out.write("\").disabled=true;\n          ");
/*      */         }
/*  453 */         out.write("\n       }\n\n\n  else\n  {\n             ");
/*  454 */         for (int i = 0; i < Integer.parseInt(cldCount); i++)
/*      */         {
/*  456 */           out.write("\n           document.getElementById(\"cld-");
/*  457 */           out.print(i);
/*  458 */           out.write("\").checked=false;\n           document.getElementById(\"cld-");
/*  459 */           out.print(i);
/*  460 */           out.write("\").disabled=false;\n           ");
/*      */         }
/*  462 */         out.write("\n  }\n}\n\n\nfunction checkServices()\n {\n\n        if(document.getElementById(\"29\").checked==true)\n        {\n            ");
/*  463 */         for (int i = 0; i < Integer.parseInt(sercount); i++)
/*      */         {
/*  465 */           out.write("\n            document.getElementById(\"services-");
/*  466 */           out.print(i);
/*  467 */           out.write("\").checked=true;\n            document.getElementById(\"services-");
/*  468 */           out.print(i);
/*  469 */           out.write("\").disabled=true;\n            ");
/*      */         }
/*  471 */         out.write("\n\n\n        }\n\n   else\n   {\n\n             ");
/*  472 */         for (int i = 0; i < Integer.parseInt(sercount); i++)
/*      */         {
/*  474 */           out.write("\n            document.getElementById(\"services-");
/*  475 */           out.print(i);
/*  476 */           out.write("\").checked=false;\n             document.getElementById(\"services-");
/*  477 */           out.print(i);
/*  478 */           out.write("\").disabled=false;\n            ");
/*      */         }
/*  480 */         out.write("\n\n\n\n   }\n}\n\n\n\n\nfunction multiCheckBox(a)\n{\n  var str=document.getElementsByName(a);\n\n   var checked=false;\n   for(i=0;i<str.length;i++)\n   {\n\n     if(str[i].checked)\n     {\n       checked=true;\n\n     }\n   }\n   return checked;\n }\n\n\nfunction multiSelectBox()\n{\n\tvar str=document.getElementsByName(\"scheduleReportResCombo2\");\n\tvar selected=false;\n\tfor(i=0;i<str.length;i++)\n   \t{\n\t\t$(\"[name=scheduleReportResCombo2] option\").each(function(){\n\t\t    $(this).attr(\"selected\",\"selected\");  //No I18N\n\t\t    selected=true;\n\t\t});\n   \t}\n   \treturn selected;\n}\nfunction multiSelectBox1()\n{\n\tvar str=document.getElementsByName(\"scheduleReportDashboardCombo2\");\n\tvar selected=false;\n\tfor(i=0;i<str.length;i++)\n   \t{\n\t\t$(\"[name=scheduleReportDashboardCombo2] option\").each(function(){\n\t\t    $(this).attr(\"selected\",\"selected\");  //No I18N\n\t\t    selected=true;\n\t\t});\n   \t}\n   \treturn selected;\n}\nfunction fnFormSubmit()\n{\n\t");
/*  481 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */           return;
/*  483 */         out.write("\n  if(document.AMActionForm.taskName.value=='')\n  {\n    alert(\"");
/*  484 */         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforschedulename.text"));
/*  485 */         out.write("\");\n     document.AMActionForm.taskName.focus();\n     return false;\n   }\n\n\n   var r=multiCheckBox('resourcestypes');\n");
/*      */         
/*  487 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/*  490 */           out.write("\n\nif(document.AMActionForm.typeofreport.value == \"forecast\") {\n\tdocument.AMActionForm.typeofperiod.value = document.AMActionForm.forecastTypeofperiod.value \n}\n\nif(document.AMActionForm.typeofreport.value == \"sqlperformance\") {\n\t//document.AMActionForm.typeofperiod.value = document.AMActionForm.typeofperformanceperiod.value; \n\tr=true;\n}\n\nif(document.AMActionForm.typeofreport.value == \"sla\" ) {\n\tr=multiSelectBox();  //No I18N\n\tif(!r){\n        alert(\"");
/*  491 */           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforscheduleresource.text"));
/*  492 */           out.write("\");\n          return false;\n     }\n}\nif(document.AMActionForm.typeofreport.value == \"dashboard\" ) {\n\tr=multiSelectBox1();  //No I18N\n\tif(!r){\n        alert(\"");
/*  493 */           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforscheduledashboard.text"));
/*  494 */           out.write("\");\n        return false;\n   }\n}\n\n\n   if(!r && (document.AMActionForm.typeofreport.value != \"eumSummary\"))\n   {\n        alert(\"");
/*  495 */           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforschedulerestypes.text"));
/*  496 */           out.write("\");\n        return false;\n   }\n   else if(document.AMActionForm.typeofreport.value == \"eumSummary\" && (typeof document.AMActionForm.filterScheduleReport != 'undefined') && (document.AMActionForm.filterScheduleReport.value != 'no'))\n   {\n        alert(\"");
/*  497 */           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforschedulerestypes.text"));
/*  498 */           out.write("\");\n        return false;\n   }\n");
/*      */         }
/*      */         else
/*      */         {
/*  502 */           out.write("\n  if(!document.AMActionForm.reportmonitor[1].checked){   \n     if(!r){\n        alert(\"");
/*  503 */           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforschedulerestypes.text"));
/*  504 */           out.write("\");\n          return false;\n     }\n   }\n");
/*      */         }
/*  506 */         out.write("\n   var d=multiCheckBox('days');\n   var m=multiCheckBox('months');\n\n   if(document.AMActionForm.taskMethod[1].checked==true)\n   {\n\n        if(document.AMActionForm.taskMethod[1].value=='weekly')\n        {\n\n\n           if(!d)\n            {\n                alert(\"");
/*  507 */         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforscheduledays.text"));
/*  508 */         out.write("\");\n                return false;\n            }\n        }\n\n   }\n   if(document.AMActionForm.taskMethod[2].checked==true)\n   {\n\n        if(document.AMActionForm.taskMethod[2].value=='monthly')\n        {\n\n            if(!m)\n            {\n                alert(\"");
/*  509 */         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforschedulemonths.text"));
/*  510 */         out.write("\");\n                return false;\n            }\n         }\n     }\n     if(document.AMActionForm.sendmail.value=='')\n     {\n        alert(\"");
/*  511 */         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforsendmail.text"));
/*  512 */         out.write("\");\n\n       return false;\n     }\n\n    document.AMActionForm.submit();\n}\n\nfunction editCheckAttribute()\n{\nvar sid=");
/*  513 */         out.print(request.getParameter("sid"));
/*  514 */         out.write(59);
/*  515 */         out.write(10);
/*      */         
/*  517 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/*  520 */           out.write("\n\tshowDiv(\"showdefault\");\n\thideDiv(\"forecastTimePeriod\");\n\thideDiv(\"showperformance\");\n\thideRow(\"showperformancereportname\");  //No i18n\n\thideRow(\"showsqlservers\");  //No i18n\n\thideRow(\"showsqlserversdb\");  //No i18n\n   var sid=");
/*  521 */           out.print(request.getParameter("sid"));
/*  522 */           out.write(";\n   showRow(\"reportperiodid\"); //No i18n\n     hideDiv(\"takeaction\");\n     hideDiv(\"SLAOPT1\"); //No i18n\n\t  hideDiv(\"SLAOPT2\"); //No i18n\n   hideDiv(\"SLAOPT3\"); //No i18n\n   hideDiv(\"SLAOPT4\"); //No i18n\n   hideDiv(\"SLAOPT5\"); //No i18n\n\n     if(document.AMActionForm.typeofreport.value=='sla'){\n  \t   \n  \t   hideDiv(\"showresources\"); //No i18n\n  \t   hideDiv(\"showattribute\"); //No i18n\n         hideDiv(\"sqlmanGRF\"); //No i18n\n  \t   hideDiv(\"sqlmanOP1\"); //No i18n\n  \t   hideDiv(\"sqlmanOP2\"); //No i18n\n  \t   hideDiv(\"sqlmanOP3\"); //No i18n\n  \t   showDiv(\"showcsv\");\n  \t   showDiv(\"SLAOPT1\"); //No i18n\n  \t   showDiv(\"SLAOPT2\"); //No i18n\n  \t   showDiv(\"SLAOPT3\"); //No i18n\n  \t   showDiv(\"SLAOPT4\"); //No i18n\n  \t   showDiv(\"SLAOPT5\"); //No i18n\n  \t   constructSLAUrl('true',sid);\n  \t   return;\n     }  \n     else if(document.AMActionForm.typeofreport.value=='dashboard'){\n\t\t\thideDiv(\"showresources\"); //No i18n\n\t\t\thideDiv(\"showattribute\"); //No i18n\n\t\t\thideRow(\"reportperiodid\"); //No i18n\n\t\t\thideDiv(\"customFilter\"); //No i18n\n");
/*  523 */           out.write("\t\t\thideDiv(\"sqlman\"); //No i18n\n\t\t\thideDiv(\"sqlmanGRF\"); //No i18n\n\t\t\thideDiv(\"sqlmanOP1\"); //No i18n\n\t\t\thideDiv(\"sqlmanOP2\"); //No i18n\n\t\t\thideDiv(\"sqlmanOP3\"); //No i18n\n\t\t\thideDiv(\"showcsv\");\n\t\t\tconstructDashboardUrl(sid);\n\t\t\treturn;\n\t\t}\n\n\t\tshowDiv(\"sqlmanGRF\"); //No i18n\n\t\tshowDiv(\"sqlmanOP1\"); //No i18n\n\t\tshowDiv(\"sqlmanOP2\"); //No i18n\n\t\tshowDiv(\"sqlmanOP3\"); //No i18n\n\n\n     if(document.AMActionForm.typeofreport.value=='attribute')\n   {\n\t\t\tshowRow(\"showattribute\");\n\t\t\thideDiv(\"showresources\");\n\t\t\tshowDiv(\"showbusiness\");\n\n   }\n   else  if(document.AMActionForm.typeofreport.value=='forecast')\n   {\n\t\t\tdisplayforecast()\n\t} else if (document.AMActionForm.typeofreport.value == 'sqlperformance') {  //No I18N\n\t\tdisplayperformance();\n\t} else {\n\t\tshowDiv(\"showresources\");\n\t\thideRow(\"showattribute\");\n\t}\n\n   if(document.AMActionForm.typeofreport.value=='event' || document.AMActionForm.typeofreport.value=='hasnapshot'|| document.AMActionForm.typeofreport.value=='hasnapshotHost' || document.AMActionForm.typeofreport.value=='availabilitysnapshot' || document.AMActionForm.typeofreport.value=='weeklyoutage' || document.AMActionForm.typeofreport.value=='availabilitytrenddowntime' || document.AMActionForm.typeofreport.value=='availabilitytrend')\n");
/*  524 */           out.write("   {\n\t\t\tif (document.AMActionForm.typeofreport.value == 'weeklyoutage') {\n\t\t\t\thideDiv(\"showdefault\");\n\t\t\t\thideDiv(\"showtrend\");\n\t\t\t\tshowDiv(\"showoutage\");\n\t\t\t\tshowDiv(\"showexcel\");\n\t\t\t\tshowDiv(\"showbusiness\");\n\t\t\t\tshowDiv(\"showcsv\");\n\t\t\t} else if (document.AMActionForm.typeofreport.value == 'availabilitytrend') {\n\t\t\t\thideDiv(\"showdefault\");\n\t\t\t\tshowDiv(\"showtrend\");\n\t\t\t\thideDiv(\"showoutage\");\n\t\t\t\tshowDiv(\"showexcel\");\n\t\t\t\tshowDiv(\"showbusiness\");\n\t\t\t\tshowDiv(\"showcsv\");\n\t\t\t} else if (document.AMActionForm.typeofreport.value == 'availabilitytrenddowntime') { //No i18n\n\t\t\t\thideDiv(\"showdefault\");\n\t\t\t\tshowDiv(\"showtrend\");\n\t\t\t\thideDiv(\"showoutage\");\n\t\t\t\tshowDiv(\"showexcel\");\n\t\t\t\tshowDiv(\"showbusiness\");\n\t\t\t\thideDiv(\"showcsv\");\n\t\t\t} else if (document.AMActionForm.typeofreport.value == 'availabilitysnapshot') {\n\t\t\t\tshowDiv(\"showexcel\");\n\t\t\t\tshowDiv(\"showbusiness\");\n\t\t\t\tshowDiv(\"showcsv\");\n     }else if(document.AMActionForm.typeofreport.value=='hasnapshot' || document.AMActionForm.typeofreport.value=='hasnapshotHost'){\n");
/*  525 */           out.write("\t\t\t\tshowDiv(\"showcsv\");\n\t\t\t\tshowDiv(\"showexcel\");\n\t\t\t} else if (document.AMActionForm.typeofreport.value == 'event') {\n\t\t\t\tshowDiv(\"showcsv\");\n\t\t\t} else {\n\t\t\t\tshowDiv(\"showdefault\");\n\t\t\t\thideDiv(\"showtrend\");\n\t\t\t\thideDiv(\"showoutage\");\n\t\t\t\thideDiv(\"showexcel\");\n\t\t\t\thideDiv(\"showbusiness\");\n\t\t\t}\n\t\t\thideDiv(\"showresources\");\n\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = false;\n\t\t\tdocument.AMActionForm.reportmonitor[0].checked = true;\n\t\t\tconstructUrl('true', sid);\n    }\n    else if(document.AMActionForm.typeofreport.value=='eumGlancereport'){\n\t\t\tshowDiv(\"showEUMResources\");\n\t\t\thideDiv(\"showresources\");\n\t\t\thideDiv(\"showcsv\");\n\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = false;\n\t\t\tdocument.AMActionForm.reportmonitor[2].checked = true;\n\n\t\t\tconstructUrl('true', sid);\n     }\n");
/*  526 */           out.write("    else if(document.AMActionForm.typeofreport.value=='eumSummary'){\n\t\t\tdocument.getElementById(\"sqlman\").style.display = 'none';\n\t\t\thideDiv(\"showresources\");\n\t\t\thideDiv(\"showcsv\");\n\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = false;\n\t\t\tdocument.AMActionForm.reportmonitor[2].checked = true;\n\t\t\tconstructUrl('true', sid);\n\n\t\t}\n\n    else if(document.AMActionForm.typeofreport.value=='downtime')\n    {\n\t\t\tshowDiv(\"showresources\");\n\t\t\thideDiv(\"showcsv\");\n\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = false;\n\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[2].checked = true;\n\t\t\tconstructUrl('true', sid);\n     }\n     else if(document.AMActionForm.typeofreport.value==\"summary\")\n    {\n\t\t\tshowDiv(\"showresources\");\n\t\t\thideDiv(\"showcsv\");\n\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = false;\n");
/*  527 */           out.write("\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[2].checked = true;\n\t\t\tconstructUrl('true', sid);\n     }\n      else if((document.AMActionForm.typeofreport.value==\"attribute\")&&(document.AMActionForm.typeofattribute.value==\"connectionTime\"))\n      {\n\t\t\thideDiv(\"showresources\");\n\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = false;\n\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[1].checked = true;\n\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = true;\n\t\t\tconstructUrl('true', sid);\n      }\n\t else if(document.AMActionForm.typeofreport.value==\"custom\")\n    {\n\t\t\thideDiv(\"showresources\");\n\t\t\tshowDiv(\"showcsv\");\n\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = true;\n\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = false;\n\t\t\tdocument.AMActionForm.reportmonitor[2].checked = true;\n\t\t\tconstructUrl('true', sid);\n");
/*  528 */           out.write("     }\n    else if(document.AMActionForm.reportmonitor[0].checked)\n    {\n         if(document.AMActionForm.typeofreport.value && document.AMActionForm.typeofreport.value==\"glancereport\")\n\t{\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].checked = true;\n\t\t\t\tconstructUrl('true', sid);\n\t}\n\telse\n\t{\n\t\t\t\thideDiv(\"showresources\");\n\t\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].checked = true;\n\t\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = true;\n\t\t\t\tconstructUrl('true', sid);\n\t\t\t}\n    }\n    else if(document.AMActionForm.reportmonitor[1].checked)\n\t{\n\t\tif(document.AMActionForm.typeofreport.value && document.AMActionForm.typeofreport.value==\"glancereport\")\n\t\t{\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = false;\n");
/*  529 */           out.write("\t\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[1].checked = true;\n\t\t\t\tconstructUrl('true', sid);\n\n\t\t}\n\t\telse\n\t\t{\n\t\t\t\thideDiv(\"showresources\");\n\t\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[1].checked = true;\n\t\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = true;\n\t\t\t\tconstructUrl('true', sid);\n\n\t\t\t}\n\t}\n    else\n    {\n    \t if(document.AMActionForm.typeofreport.value && document.AMActionForm.typeofreport.value==\"glancereport\")\n\t{\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[2].checked = true;\n\t\t\t\tconstructUrl('true', sid);\n\n\t}\n\telse\n\t{\n\t\t\t\thideDiv(\"showresources\");\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].disabled = false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[1].disabled = false;\n");
/*  530 */           out.write("\t\t\t\tdocument.AMActionForm.reportmonitor[1].checked = true;\n\t\t\t\tdocument.AMActionForm.reportmonitor[2].disabled = true;\n\t\t\t\tconstructUrl('true', sid);\n\t\t\t}\n\t\t}\n");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  536 */           out.write("\n\n hideDiv(\"takeaction\");\n  showDiv(\"showdefault\");\n     hideDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     hideDiv(\"showexcel\");\n     hideDiv(\"showbusiness\");\n   if(document.AMActionForm.typeofreport.value==\"attribute\")\n   {\n      showRow(\"showattribute\"); // No I18N\n       hideDiv(\"showresources\");\n       if(document.AMActionForm.typeofattribute.value=='resource'){\n       alert(\"");
/*  537 */           out.print(FormatUtil.getString("am.webclient.customattribute.jsalert.text"));
/*  538 */           out.write("\");\n       return;\n\n       }\n\n   }\n   else\n   {\n\n     showDiv(\"showresources\");\n     hideRow(\"showattribute\"); // No I18N\n   }\n\n   if(document.AMActionForm.typeofreport.value=='event' || document.AMActionForm.typeofreport.value=='hasnapshot'|| document.AMActionForm.typeofreport.value=='hasnapshotHost' || document.AMActionForm.typeofreport.value=='availabilitysnapshot' || document.AMActionForm.typeofreport.value=='weeklyoutage' || document.AMActionForm.typeofreport.value=='availabilitytrenddowntime' || document.AMActionForm.typeofreport.value=='availabilitytrend')\n   {\n   if(document.AMActionForm.typeofreport.value=='weeklyoutage'){\n     hideDiv(\"showdefault\");\n     hideDiv(\"showtrend\");\n     showDiv(\"showoutage\");\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     }else if(document.AMActionForm.typeofreport.value=='availabilitytrend'){ // No I18N\n     hideDiv(\"showdefault\");\n     showDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     }else if(document.AMActionForm.typeofreport.value=='availabilitytrenddowntime'){ //No i18n\n");
/*  539 */           out.write("     hideDiv(\"showdefault\");\n     showDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     }else if(document.AMActionForm.typeofreport.value=='availabilitysnapshot'){ //No i18n\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     }else if(document.AMActionForm.typeofreport.value=='hasnapshot' || document.AMActionForm.typeofreport.value=='hasnapshotHost'){ //No i18n\n     showDiv(\"showexcel\");\n\n     }else{\n     showDiv(\"showdefault\");\n     hideDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     hideDiv(\"showexcel\");\n     hideDiv(\"showbusiness\");\n     }\n      hideDiv(\"showresources\");\n      document.AMActionForm.reportmonitor[1].disabled=false;\n\tdocument.AMActionForm.reportmonitor[2].disabled=true;\n      document.AMActionForm.reportmonitor[0].disabled=false;\n      document.AMActionForm.reportmonitor[0].checked=true;\n       constructUrl('true',sid);\n    }\n\n    else if(document.AMActionForm.typeofreport.value==\"downtime\")\n    {\n        hideDiv(\"showresources\");\n");
/*  540 */           out.write("       document.AMActionForm.reportmonitor[0].disabled=true;\n      document.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n      document.AMActionForm.reportmonitor[2].checked=true;\n\n\t  \t\tdocument.getElementById(\"sqlman\").style.display='block';\n\t\t\t//document.getElementById(\"sqlmanOP1\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP2\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP3\").style.display='none';\n\t\t\tdocument.getElementById(\"monitordetail\").style.height='200px';\n\n\n       constructUrl('true',sid);\n     }\n     else if(document.AMActionForm.typeofreport.value==\"summary\")\n    {\n       hideDiv(\"showresources\");\n\n        document.AMActionForm.reportmonitor[0].disabled=true;\n        document.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n        document.AMActionForm.reportmonitor[2].checked=true;\n\n\n\t\t\tdocument.getElementById(\"sqlman\").style.display='block';\n\t\t\t//document.getElementById(\"sqlmanOP1\").style.display='none';\n");
/*  541 */           out.write("\t\t\t//document.getElementById(\"sqlmanOP2\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP3\").style.display='none';\n\t\t\tdocument.getElementById(\"monitordetail\").style.height='200px';\n\n        constructUrl('true',sid);\n     }\n\n     /*else if((document.AMActionForm.typeofreport.value==\"attribute\")&&((document.AMActionForm.typeofattribute.value==\"cpuid\")||(document.AMActionForm.typeofattribute.value==\"mem\")))\n      {\n          alert(\"%%%%%\"+document.getElementById(\"per\").options[0]);\n          var len=document.getElementById(\"per\").options.length;\n          document.getElementById(\"per\").options[len]=new Option(\"arun\",\"14\");\n      }*/\n       else if(document.AMActionForm.typeofreport.value==\"custom\")\n    {\n        hideDiv(\"showresources\");\n\n        document.AMActionForm.reportmonitor[0].disabled=true;\n\tdocument.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n        document.AMActionForm.reportmonitor[2].checked=true;\n         constructUrl('true',sid);\n");
/*  542 */           out.write("     }\n\n    else\n    {\n        if(document.AMActionForm.typeofreport.value && document.AMActionForm.typeofreport.value==\"glancereport\") //No i18n\n\t{\n\n\t\tdocument.AMActionForm.reportmonitor[0].disabled=true; //No i18n\n\t\tdocument.AMActionForm.reportmonitor[2].disabled=false; //No i18n\n\t\t    hideDiv(\"showresources\");\n\t\t\tdocument.getElementById(\"sqlmanGRF\").style.display='block';\n\t\t\tdocument.getElementById(\"sqlmanOP1\").style.display='block';\n\t\t\tdocument.getElementById(\"sqlmanOP2\").style.display='block';\n\t\t\tdocument.getElementById(\"sqlmanOP3\").style.display='block';\n\t\t\tdocument.getElementById(\"monitordetail\").style.height='1px';\n\n\t\t\tif(document.AMActionForm.reportmonitor[2].checked == true)\n\t\t\t{\n\t\t\t\tconstructUrl('true',sid); //No i18n\n\t\t\t}\n\t}\n        else if((document.AMActionForm.typeofreport.value && document.AMActionForm.typeofreport.value==\"attribute\") && (document.AMActionForm.typeofattribute.value && document.AMActionForm.typeofattribute.value==\"connectionTime\")){\n\n\n        hideDiv(\"showresources\");\n        document.AMActionForm.reportmonitor[0].disabled=true;\n");
/*  543 */           out.write("        document.AMActionForm.reportmonitor[1].disabled=false;\n\tdocument.AMActionForm.reportmonitor[2].disabled=true;\n        document.AMActionForm.reportmonitor[1].checked=true;\n         constructUrl('true',sid);\n\n        }\n\t\telse if(document.AMActionForm.typeofreport.value==\"availability\" || document.AMActionForm.typeofreport.value==\"health\" || document.AMActionForm.typeofreport.value==\"attribute\")\n\t\t{\n        hideDiv(\"showresources\");\n        document.AMActionForm.reportmonitor[0].disabled=false;\n        document.AMActionForm.reportmonitor[1].disabled=false;\n\t    document.AMActionForm.reportmonitor[2].disabled=true;\n       // document.AMActionForm.reportmonitor[1].checked=true;\n        constructUrl('true',sid);\n\n\t\t\tdocument.getElementById(\"sqlman\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanGRF\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP1\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP2\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP3\").style.display='none';\n");
/*  544 */           out.write("\t\t\t//document.getElementById(\"monitordetail\").style.height='1px';\n      }\n\t  else\n\t  {\n\t    hideDiv(\"showresources\");\n        document.AMActionForm.reportmonitor[0].disabled=false;\n        document.AMActionForm.reportmonitor[1].disabled=false;\n\t    document.AMActionForm.reportmonitor[2].disabled=true;\n        document.AMActionForm.reportmonitor[1].checked=true;\n\n\t\t\tdocument.getElementById(\"sqlman\").style.display='block';\n\t\t\tdocument.getElementById(\"sqlmanGRF\").style.display='none';\n\t\t\tdocument.getElementById(\"sqlmanOP1\").style.display='none';\n\t\t\tdocument.getElementById(\"sqlmanOP2\").style.display='none';\n\t\t\tdocument.getElementById(\"sqlmanOP3\").style.display='block';\n\t\t\tdocument.getElementById(\"monitordetail\").style.height='200px';\n\n        constructUrl('true',sid);\n\n\t  }\n    }\n\n\n\n");
/*      */         }
/*      */         
/*      */ 
/*  548 */         out.write("\n\n }\n\n\n\nfunction checkAttribute()\n{\n\tdocument.getElementById('reportDeliveryPDF').checked=\"checked\"; // NO I18N\n\tdocument.getElementById('reportDeliveryPDF').disabled=false;\n\tdocument.getElementById('publishlink').disabled=false;\n\thideDiv(\"showEUMResources\");\n");
/*      */         
/*  550 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/*  553 */           out.write("\n\n\n\n    hideDiv(\"takeaction\");\n    showRow(\"reportperiodid\"); //No i18n\n    showDiv(\"showdefault\");\n     hideDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     hideDiv(\"showexcel\");\n     hideDiv(\"showbusiness\");\n\t  hideDiv(\"SLAOPT1\"); //No i18n\n   \t  hideDiv(\"SLAOPT2\"); //No i18n\n      hideDiv(\"SLAOPT3\"); //No i18n\n      hideDiv(\"SLAOPT4\"); //No i18n\n      hideDiv(\"SLAOPT5\"); //No i18n\n      showDiv(\"showpdf\"); // NO I18N\n      hideDiv(\"forecastGrowthTrend\");\n      hideRow(\"showforecastattribute\"); //No I18N\n      showDiv(\"showdefault\");\n  \thideDiv(\"forecastTimePeriod\");\n\thideRow(\"showperformancereportname\");  //No i18n\n\thideRow(\"showsqlservers\");  //No i18n\n\thideRow(\"showsqlserversdb\");  //No i18n\n\thideDiv(\"showperformance\");\n      jQuery(\"#showcsv\").addClass(\"tdindent\")\t\t// NO I18N\n    \n   if(document.AMActionForm.typeofreport.value=='sla'){\n\t   \n\t   hideDiv(\"showresources\"); //No i18n\n\t   hideDiv(\"showattribute\"); //No i18n\n       hideDiv(\"sqlmanGRF\"); //No i18n\n\t   hideDiv(\"sqlmanOP1\"); //No i18n\n\t   hideDiv(\"sqlmanOP2\"); //No i18n\n");
/*  554 */           out.write("\t   hideDiv(\"sqlmanOP3\"); //No i18n\n\t   hideDiv(\"customFilter\"); //No i18n\n\t   showDiv(\"showcsv\");\n\t   showDiv(\"SLAOPT1\"); //No i18n\n\t   showDiv(\"SLAOPT2\"); //No i18n\n\t   showDiv(\"SLAOPT3\"); //No i18n\n\t   showDiv(\"SLAOPT4\"); //No i18n\n\t   showDiv(\"SLAOPT5\"); //No i18n\n\t   constructSLAUrl();\n\t   return;\n   }  \nif(document.AMActionForm.typeofreport.value=='dashboard'){\n\t   \n\t   hideDiv(\"showresources\"); //No i18n\n\t   hideDiv(\"showattribute\"); //No i18n\n\t   hideRow(\"reportperiodid\"); //No i18n\n\t   hideDiv(\"customFilter\"); //No i18n\n\t   hideDiv(\"sqlman\"); //No i18n\n       hideDiv(\"sqlmanGRF\"); //No i18n\n\t   hideDiv(\"sqlmanOP1\"); //No i18n\n\t   hideDiv(\"sqlmanOP2\"); //No i18n\n\t   hideDiv(\"sqlmanOP3\"); //No i18n\n\t   hideDiv(\"showcsv\");\n\t   constructDashboardUrl();\n\t   return;\n   } \n   \n   \n      showDiv(\"sqlmanGRF\"); //No i18n\n      showDiv(\"sqlmanOP1\"); //No i18n\n      showDiv(\"sqlmanOP2\"); //No i18n\n      showDiv(\"sqlmanOP3\"); //No i18n\n   \n  \n     \n     \n     \n   if(document.AMActionForm.typeofreport.value==\"attribute\")\n");
/*  555 */           out.write("   {\n        showDiv(\"sqlman\");// NO I18N\n        showRow(\"showattribute\");\n       hideDiv(\"showresources\");\n\tshowDiv(\"showcsv\");\n\tshowDiv(\"showbusiness\");\n       if(document.AMActionForm.typeofattribute.value=='resource'){\n       alert(\"");
/*  556 */           out.print(FormatUtil.getString("am.webclient.customattribute.jsalert.text"));
/*  557 */           out.write("\");\n       return;\n       }\n\n   }else if(document.AMActionForm.typeofreport.value==\"forecast\"){\n\t   \t \n\t   \t\tdisplayforecast();\n\t   \t\tif(document.AMActionForm.typeofforecastattribute.value=='resource'){\n\t\t\t\talert(\"");
/*  558 */           out.print(FormatUtil.getString("am.webclient.customattribute.jsalert.text"));
/*  559 */           out.write("\");\n\t\t\t\treturn;\n\t        }\n\t}\n    else if(document.AMActionForm.typeofreport.value==\"sqlperformance\") {\n\t\tdisplayperformance();\n    }\n   else\n   {\n     showDiv(\"sqlman\");// NO I18N\n    showDiv(\"showresources\");\n     hideRow(\"showattribute\");\n   }\n    var sid=");
/*  560 */           out.print(request.getParameter("sid"));
/*  561 */           out.write(";\n   if(document.AMActionForm.typeofreport.value=='event' || document.AMActionForm.typeofreport.value=='hasnapshot'|| document.AMActionForm.typeofreport.value=='hasnapshotHost' || document.AMActionForm.typeofreport.value=='availabilitysnapshot' || document.AMActionForm.typeofreport.value=='weeklyoutage' || document.AMActionForm.typeofreport.value=='availabilitytrenddowntime' || document.AMActionForm.typeofreport.value=='availabilitytrend')\n   {\n   if(document.AMActionForm.typeofreport.value=='weeklyoutage'){\n     hideDiv(\"showdefault\");\n     hideDiv(\"showtrend\");\n     showDiv(\"showoutage\");\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     showDiv(\"showcsv\");\n     }else if(document.AMActionForm.typeofreport.value=='availabilitytrend'){\n     hideDiv(\"showdefault\");\n     showDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     showDiv(\"showcsv\");\n     }else if(document.AMActionForm.typeofreport.value=='availabilitytrenddowntime'){ //No i18n\n     hideDiv(\"showdefault\");\n");
/*  562 */           out.write("     showDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     hideDiv(\"showcsv\");\n     }else if(document.AMActionForm.typeofreport.value=='availabilitysnapshot'){\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     showDiv(\"showcsv\");\n     }else if(document.AMActionForm.typeofreport.value=='hasnapshot' || document.AMActionForm.typeofreport.value=='hasnapshotHost'){\n     showDiv(\"showexcel\");\n     showDiv(\"showcsv\");\n     }else{\n     showDiv(\"showdefault\");\n     showDiv(\"showcsv\");\n     hideDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     hideDiv(\"showexcel\");\n     hideDiv(\"showbusiness\");\n     }\n      hideDiv(\"showresources\");\n      document.AMActionForm.reportmonitor[1].disabled=true;\n\t  document.AMActionForm.reportmonitor[2].disabled=true;\n      document.AMActionForm.reportmonitor[0].disabled=false;\n      document.AMActionForm.reportmonitor[0].checked=true;\n      constructUrl();\n    }\n    else if(document.AMActionForm.typeofreport.value=='eumGlancereport'){\n");
/*  563 */           out.write("    \t     showDiv(\"showEUMResources\");\n    \t     hideDiv(\"showresources\");\n    \t     hideDiv(\"showcsv\"); \n       document.AMActionForm.reportmonitor[0].disabled=true;\n      document.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n      document.AMActionForm.reportmonitor[2].checked=true;\n    \t      \n      constructUrl();\n     }\n    else if(document.AMActionForm.typeofreport.value=='eumSummary'){\n    \t    document.getElementById(\"sqlman\").style.display='none';\n    \t    hideDiv(\"showresources\");\n\t    hideDiv(\"showcsv\");\n        document.AMActionForm.reportmonitor[0].disabled=true;\n        document.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n        document.AMActionForm.reportmonitor[2].checked=true;\n        constructUrl();\n   \n    }\n\n    else if(document.AMActionForm.typeofreport.value==\"downtime\")\n    {\n        showDiv(\"showresources\");\n\thideDiv(\"showcsv\");\n       document.AMActionForm.reportmonitor[0].disabled=true;\n");
/*  564 */           out.write("      document.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n      document.AMActionForm.reportmonitor[2].checked=true;\n\tconstructUrl('true',sid);\n     }\n     else if(document.AMActionForm.typeofreport.value==\"summary\")\n    {\n        showDiv(\"showresources\");\n\thideDiv(\"showcsv\");\n        document.AMActionForm.reportmonitor[0].disabled=true;\n        document.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n        document.AMActionForm.reportmonitor[2].checked=true;\n        constructUrl('true',sid);\n     }\n\n     /*else if((document.AMActionForm.typeofreport.value==\"attribute\")&&((document.AMActionForm.typeofattribute.value==\"cpuid\")||(document.AMActionForm.typeofattribute.value==\"mem\")))\n      {\n          alert(\"%%%%%\"+document.getElementById(\"per\").options[0]);\n          var len=document.getElementById(\"per\").options.length;\n          document.getElementById(\"per\").options[len]=new Option(\"arun\",\"14\");\n      }*/\n");
/*  565 */           out.write("       else if(document.AMActionForm.typeofreport.value==\"custom\")\n    {\n        hideDiv(\"showresources\");\n\tshowDiv(\"showcsv\");\n        jQuery(\"#customfieldfilter\").hide();\t\t// NO I18N\n        document.AMActionForm.reportmonitor[0].disabled=true;\n\tdocument.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n        document.AMActionForm.reportmonitor[2].checked=true;\n        constructUrl();\n     }\n\n    else\n    {\n        if(document.AMActionForm.typeofreport.value && document.AMActionForm.typeofreport.value==\"glancereport\") //No i18n\n\t{\n\t\tdocument.AMActionForm.reportmonitor[0].disabled=false; //No i18n\n        \tdocument.AMActionForm.reportmonitor[1].disabled=false; //No i18n\n\t\tdocument.AMActionForm.reportmonitor[2].disabled=false; //No i18n\n\t\thideDiv(\"showcsv\");\n       \t \tconstructUrl();\n\t}\n        else if((document.AMActionForm.typeofreport.value && (document.AMActionForm.typeofreport.value==\"attribute\" || document.AMActionForm.typeofreport.value==\"forecast\")) && (document.AMActionForm.typeofattribute.value && document.AMActionForm.typeofattribute.value==\"connectionTime\")){\n");
/*  566 */           out.write("\n        hideDiv(\"showresources\");\n        document.AMActionForm.reportmonitor[0].disabled=true;\n        document.AMActionForm.reportmonitor[1].disabled=false;\n\t\tdocument.AMActionForm.reportmonitor[2].disabled=true;\n        document.AMActionForm.reportmonitor[1].checked=true;\n        constructUrl();\n\n        }else{\n\t\t\tif(document.AMActionForm.typeofreport.value != 'sqlperformance') {\n\t\t\t\thideDiv(\"showresources\");\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].disabled=false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[1].disabled=false;\n\t\t\t\tdocument.AMActionForm.reportmonitor[2].disabled=true;\n\t\t\t\t");
/*  567 */           if (com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(loginUserName)) {
/*  568 */             out.write("\n\t\t\t\tdocument.AMActionForm.reportmonitor[0].checked=true;\n\t\t\t\t");
/*      */           } else {
/*  570 */             out.write("\n\t\t\t\t document.AMActionForm.reportmonitor[1].checked=true;\n\t\t\t\t ");
/*      */           }
/*  572 */           out.write("\n\t\t\t}\n\t\t\tconstructUrl();\n      }\n    }\n\n\n");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  578 */           out.write("\n   hideDiv(\"takeaction\");\n   showDiv(\"showdefault\");\n     hideDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     hideDiv(\"showexcel\");\n     hideDiv(\"showbusiness\");\n   if(document.AMActionForm.typeofreport.value==\"attribute\")\n   {\n  \n\n       showRow(\"showattribute\"); //No i18n\n       hideDiv(\"showresources\");\n       if(document.AMActionForm.typeofattribute.value=='resource'){\n       alert(\"");
/*  579 */           out.print(FormatUtil.getString("am.webclient.customattribute.jsalert.text"));
/*  580 */           out.write("\");\n       return;\n\n       }\n\n   }\n   else\n   {\n\n     showDiv(\"showresources\");\n     hideRow(\"showattribute\"); //No i18n\n   }\n\n   if(document.AMActionForm.typeofreport.value=='event' || document.AMActionForm.typeofreport.value=='hasnapshot'|| document.AMActionForm.typeofreport.value=='hasnapshotHost' || document.AMActionForm.typeofreport.value=='availabilitysnapshot' || document.AMActionForm.typeofreport.value=='weeklyoutage' || document.AMActionForm.typeofreport.value=='availabilitytrenddowntime' || document.AMActionForm.typeofreport.value=='availabilitytrend')\n   {\n   if(document.AMActionForm.typeofreport.value=='weeklyoutage'){\n     hideDiv(\"showdefault\");\n     hideDiv(\"showtrend\");\n     showDiv(\"showoutage\");\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     }else if(document.AMActionForm.typeofreport.value=='availabilitytrend'){ //No i18n\n     hideDiv(\"showdefault\");\n     showDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     }else if(document.AMActionForm.typeofreport.value=='availabilitytrenddowntime'){ //No i18n\n");
/*  581 */           out.write("     hideDiv(\"showdefault\");\n     showDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     }else if(document.AMActionForm.typeofreport.value=='availabilitysnapshot'){ //No i18n\n     showDiv(\"showexcel\");\n     showDiv(\"showbusiness\");\n     }else if(document.AMActionForm.typeofreport.value=='hasnapshot' || document.AMActionForm.typeofreport.value=='hasnapshotHost'){ //No i18n\n     showDiv(\"showexcel\");\n\n     }else{\n     showDiv(\"showdefault\");\n     hideDiv(\"showtrend\");\n     hideDiv(\"showoutage\");\n     hideDiv(\"showexcel\");\n     hideDiv(\"showbusiness\");\n     }\n      hideDiv(\"showresources\");\n      document.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=true;\n      document.AMActionForm.reportmonitor[0].disabled=false;\n      document.AMActionForm.reportmonitor[0].checked=true;\n\t  document.getElementById(\"monitordetail\").style.height='100%';\n\t  \n      constructUrl();\n    }\n\n    else if(document.AMActionForm.typeofreport.value==\"downtime\")\n");
/*  582 */           out.write("    {\n         hideDiv(\"showresources\");\n       document.AMActionForm.reportmonitor[0].disabled=true;\n      document.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n      document.AMActionForm.reportmonitor[2].checked=true;\n\n\t  \t\tdocument.getElementById(\"sqlman\").style.display='block';\n\t\t\t//document.getElementById(\"sqlmanOP1\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP2\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP3\").style.display='none';\n\t\t\tdocument.getElementById(\"monitordetail\").style.height='200px';\n\n\n      constructUrl();\n     }\n     else if(document.AMActionForm.typeofreport.value==\"summary\")\n    {\n        hideDiv(\"showresources\");\n\n        document.AMActionForm.reportmonitor[0].disabled=true;\n        document.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n        document.AMActionForm.reportmonitor[2].checked=true;\n\n\n\t\t\tdocument.getElementById(\"sqlman\").style.display='block';\n");
/*  583 */           out.write("\t\t\t//document.getElementById(\"sqlmanOP1\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP2\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP3\").style.display='none';\n\t\t\tdocument.getElementById(\"monitordetail\").style.height='200px';\n\n        constructUrl();\n     }\n\n     /*else if((document.AMActionForm.typeofreport.value==\"attribute\")&&((document.AMActionForm.typeofattribute.value==\"cpuid\")||(document.AMActionForm.typeofattribute.value==\"mem\")))\n      {\n          alert(\"%%%%%\"+document.getElementById(\"per\").options[0]);\n          var len=document.getElementById(\"per\").options.length;\n          document.getElementById(\"per\").options[len]=new Option(\"arun\",\"14\");\n      }*/\n       else if(document.AMActionForm.typeofreport.value==\"custom\")\n    {\n\t\n        hideDiv(\"showresources\");\n\n        document.AMActionForm.reportmonitor[0].disabled=true;\n\tdocument.AMActionForm.reportmonitor[1].disabled=true;\n\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n        document.AMActionForm.reportmonitor[2].checked=true;\n");
/*  584 */           out.write("        constructUrl();\n     }\n\n    else\n    {\n\t   \n        if(document.AMActionForm.typeofreport.value && document.AMActionForm.typeofreport.value==\"glancereport\") //No i18n\n\t{\n\t\tdocument.AMActionForm.reportmonitor[0].disabled=true; //No i18n\n\t\tdocument.AMActionForm.reportmonitor[1].disabled=false; //No i18n\n\t\tdocument.AMActionForm.reportmonitor[2].disabled=false; //No i18n\n\t\t\n\t\t    hideDiv(\"showresources\");\n\t\t\tdocument.getElementById(\"sqlmanGRF\").style.display='block';\n\t\t\tdocument.getElementById(\"sqlmanOP1\").style.display='block';\n\t\t\tdocument.getElementById(\"sqlmanOP2\").style.display='block';\n\t\t\tdocument.getElementById(\"sqlmanOP3\").style.display='block';\n\t\t\tdocument.getElementById(\"monitordetail\").style.height='1px';\n            document.AMActionForm.reportmonitor[1].checked=true;\n       \t \tif(document.AMActionForm.reportmonitor[2].checked == true)\n\t\t\t{\n       \t \t    constructUrl();\n\t\t\t}\n\t}\n        \n\t\telse if((document.AMActionForm.typeofreport.value && document.AMActionForm.typeofreport.value==\"attribute\") && (document.AMActionForm.typeofattribute.value && document.AMActionForm.typeofattribute.value==\"connectionTime\")){\n");
/*  585 */           out.write("\n\n        hideDiv(\"showresources\");\n        document.AMActionForm.reportmonitor[0].disabled=true;\n        document.AMActionForm.reportmonitor[1].disabled=false;\n\t    document.AMActionForm.reportmonitor[2].disabled=true;\n        document.AMActionForm.reportmonitor[1].checked=true;\n        constructUrl();\n\n        }\n\t\telse if(document.AMActionForm.typeofreport.value==\"availability\" || document.AMActionForm.typeofreport.value==\"health\" || document.AMActionForm.typeofreport.value==\"attribute\")\n\t\t{\n\t\t\n        hideDiv(\"showresources\");\n        document.AMActionForm.reportmonitor[0].disabled=false;\n        document.AMActionForm.reportmonitor[1].disabled=false;\n\t    document.AMActionForm.reportmonitor[2].disabled=true;\n        document.AMActionForm.reportmonitor[1].checked=true;\n        constructUrl();\n\n\t\t\t document.getElementById(\"sqlman\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanGRF\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP1\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP2\").style.display='none';\n");
/*  586 */           out.write("\t\t\t//document.getElementById(\"sqlmanOP3\").style.display='none';\n\t\t//\tdocument.getElementById(\"monitordetail\").style.height='1px';\n\t\tdocument.getElementById(\"monitordetail\").style.height='100%';\n       }\n\t   else\n\t   {\n\t \n\t    hideDiv(\"showresources\");\n        document.AMActionForm.reportmonitor[0].disabled=false;\n        document.AMActionForm.reportmonitor[1].disabled=false;\n\t    document.AMActionForm.reportmonitor[2].disabled=true;\n        document.AMActionForm.reportmonitor[1].checked=true;\n            \n\t\t\tdocument.getElementById(\"sqlman\").style.display='block';\n\t\t\t//document.getElementById(\"sqlmanGRF\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP1\").style.display='none';\n\t\t\t//document.getElementById(\"sqlmanOP2\").style.display='none';\n\t\t\tdocument.getElementById(\"sqlmanOP3\").style.display='block';\n\t\t\tdocument.getElementById(\"monitordetail\").style.height='200px';\n\n        constructUrl();\n\n\t  }\n    }\n\n\n\n");
/*      */         }
/*      */         
/*      */ 
/*  590 */         out.write("\n\n }\n \n function displayforecast(){\n\t hideDiv(\"showdefault\");\n\t showDiv(\"forecastTimePeriod\");\n\t showDiv(\"sqlman\");// NO I18N\n\t hideRow(\"showattribute\");\t\t// NO I18N\n     showRow(\"showforecastattribute\");\t\t// NO I18N\n     hideDiv(\"showresources\");\n\t  showDiv(\"showcsv\");\n\t   hideDiv(\"showbusiness\");\n\t   showDiv(\"forecastGrowthTrend\")\n\t   hideDiv(\"showpdf\"); // NO I18N\n\t  jQuery(\"#showcsv\").removeClass(\"tdindent\")\t\t// NO I18N\n\t  $(\"input[name=reportDelivery][value=csv]\").prop('checked', true);\t\t// NO I18N\n }\n\n function displayperformance() {\n\t\thideDiv(\"showdefault\");\n\t\tshowDiv(\"sqlman\");// NO I18N\n\t\tshowDiv(\"showperformance\");\n\t\tshowRow(\"showperformancereportname\"); //No I18N\n\t\thideDiv(\"showresources\");\n\t\thideDiv(\"showcsv\");\n\t\tshowDiv(\"showexcel\");\n\t\thideRow(\"showattribute\"); //No I18N\n\t\tif(document.AMActionForm.selectedMSSQLResource.value=='') {\n\t\t\tloadSQLServers();\n\t\t}\n\t\tshowRow(\"showsqlservers\"); //No I18N\n\t\thideDiv(\"customFilter\"); //No i18n\n\t\tif(document.AMActionForm.typeofperformance.value != \"QUERYBYSRQ\" && document.AMActionForm.typeofperformance.value != \"QUERYBYCMI\" && document.AMActionForm.typeofperformance.value != \"MEMORY\" && document.AMActionForm.typeofperformance.value != \"WAITSTATS\") {\n");
/*  591 */         out.write("\t\t\tshowRow(\"showsqlserversdb\"); // No I18N\n\t\t} else {\n\t\t\tdocument.AMActionForm.sqlDBforPerformance.value=\"All\"; // No I18N\n\t\t}\n\t\t\n\t\thideDiv(\"sqlmanGRF\");\n\t\thideDiv(\"sqlmanOP1\");\n\t\thideDiv(\"sqlmanOP2\");\n\t\thideDiv(\"sqlmanOP3\");\n\t\t\n\t\tdocument.AMActionForm.reportmonitor[0].disabled=true;\n\t\tdocument.AMActionForm.reportmonitor[1].disabled=true;\n\t\tdocument.AMActionForm.reportmonitor[2].disabled=false;\n\t\tdocument.AMActionForm.reportmonitor[2].checked=true;\n }\n \n function loadDBs(resid)\n {\n\tvar random = Math.random();\n\tvar flag = 'false';\n\tjQuery.ajax({\n\t\turl:'/scheduleReports.do?resourceID='+resid+'&method=getDBServersForResID&random='+random, //NO I18N\n\t\tcache: false,\n\t\tdataType: \"json\", //NO I18N\n\t\tsuccess:function(result) {\n\t\t\tvar list=[];\n\t\t\tfor (var key in result) {\n\t\t\t  if (result.hasOwnProperty(key)) {\n\t\t\t\t\tvar val = result[key];\n\t\t\t\t\tlist.push({'dbkey': key, 'dbval': val}); //NO I18N\n\t\t\t\t}\n\t\t\t}\n\t\t\tlist.sort(function(a, b) {\n\t\t\t\treturn ((a.dbval < b.dbval) ? -1 : ((a.dbval == b.dbval) ? 0 : 1));\n\t\t\t});\n\t\t\tvar sqlDBPerf = document.AMActionForm.sqlDBPerf.value;\n");
/*  592 */         out.write("\t\t\t$(\"#sqlDBforPerformance\").empty();\n\t\t\tfor (var k=0; k<list.length; k++) {\n\t\t\t\tif(flag == \"false\" || list[k].dbkey==sqlDBPerf)\n\t\t\t\t{\n\t\t\t\t\t$('#sqlDBforPerformance').append(\"<option value='\"+list[k].dbkey+\"' selected='selected'>\"+list[k].dbval+\"</option>\");\n\t\t\t\t\tflag = list[k].dbkey;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\t$('#sqlDBforPerformance').append(\"<option value='\"+list[k].dbkey+\"'>\"+list[k].dbval+\"</option>\");\n\t\t\t\t}\n\t\t\t}\n\t\t\tif(document.AMActionForm.typeofperformance.value == \"QUERYBYSRQ\" || document.AMActionForm.typeofperformance.value == \"QUERYBYCMI\" || document.AMActionForm.typeofperformance.value == \"MEMORY\" || document.AMActionForm.typeofperformance.value == \"WAITSTATS\") {\n\t\t\t\t$('#sqlDBforPerformance option[value=All]').prop(\"selected\",true); //No I18N\n\t\t\t}\n\t\t}\n\t});\n }\n \n function loadSQLServers()\n {\n\tvar random = Math.random();\n\tvar flag = 'false';\n\tjQuery.ajax({\n\t\turl:'/scheduleReports.do?method=getSQLServersForUser&random='+random, //NO I18N\n\t\tcache: false,\n\t\tdataType: \"json\", //NO I18N\n\t\tsuccess:function(result) {\n");
/*  593 */         out.write("\t\t\tvar list=[];\n\t\t\tfor (var key in result) {\n\t\t\t  if (result.hasOwnProperty(key)) {\n\t\t\t\t\tvar val = result[key];\n\t\t\t\t\tlist.push({'sqlkey': key, 'sqlval': val}); //NO I18N\n\t\t\t\t}\n\t\t\t}\n\t\t\tlist.sort(function(a, b) {\n\t\t\t\treturn ((a.sqlval < b.sqlval) ? -1 : ((a.sqlval == b.sqlval) ? 0 : 1));\n\t\t\t});\n\t\t\tvar sqlServerPerf = document.AMActionForm.sqlServerPerf.value;\n\t\t\t$(\"#selectedMSSQLResource\").empty();\n\t\t\tfor (var k=0; k<list.length; k++) {\n\t\t\t\tif(flag == \"false\" || list[k].sqlkey==sqlServerPerf)\n\t\t\t\t{\n\t\t\t\t\t$('#selectedMSSQLResource').append(\"<option value='\"+list[k].sqlkey+\"' selected='selected'>\"+list[k].sqlval+\"</option>\");\n\t\t\t\t\tflag = list[k].sqlkey;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\t$('#selectedMSSQLResource').append(\"<option value='\"+list[k].sqlkey+\"'>\"+list[k].sqlval+\"</option>\");\n\t\t\t\t}\n\t\t\t}\n\t\t\tloadDBs($(\"#selectedMSSQLResource\").val());\n\t\t}\n\t});\n }\n\n\tfunction constructDashboardUrl(sid)\n\t{\n\n\t\thideDiv(\"showresources\"); //No i18n\t\n\t\tshowDiv(\"resourceloading\");\n\n\t var url=\"/scheduleReports.do?method=sendAjaxDetails&report=dashboard&resourcetypes=dashboard&sid=\"+sid+\"&todaytime=\"+");
/*  594 */         out.print(System.currentTimeMillis());
/*  595 */         out.write(";//No i18n\n\t http1.open(\"GET\",url,true);\n\t http1.onreadystatechange =getslatypes;\n\t http1.send(null);\n}\n\n function constructSLAUrl(e,s)\n {\n\t hideDiv(\"showresources\"); //No i18n\t\n\t showDiv(\"resourceloading\");\n\t var raidoValue = jQuery(\"input[name='slatype']:checked\").val()\t// NO I18N\n\t url=\"/scheduleReports.do?method=sendAjaxDetails&report=sla&resourcetypes=sla&resource=\"+raidoValue+\"&edit=\"+e+\"&sid=\"+s+\"&todaytime=\"+");
/*  596 */         out.print(System.currentTimeMillis());
/*  597 */         out.write(";//No i18n\n\t \n\t http1.open(\"GET\",url,true);\n\t http1.onreadystatechange =getslatypes;\n\t http1.send(null);\n }\n \n function getslatypes()\n {\n   if(http1.readyState ==4){ \n      hideDiv(\"resourceloading\");\n      var result = http1.responseText;\n      jQuery(\"#monitordetail\").html(result)\t//No I18N\n      jQuery(\"#monitordetail\").show()\t//No I18N\n  }\n }\n\n\n function constructUrl()\n {\n   constructUrl('false',null);\n }\n\n\n\nvar http1 = getHTTPObject();\nvar http2 = getHTTPObject();\n\n  function constructUrl(e,s,cf,option)\n {\n \nif(document.AMActionForm.typeofreport.value==\"glancereport\" && document.AMActionForm.reportmonitor[2].checked) //No i18n\n    {\n        showDiv(\"showresources\"); //No i18n\n\n     }\nelse if(document.AMActionForm.typeofreport.value==\"glancereport\" && ! document.AMActionForm.reportmonitor[2].checked)\t//No i18n\n\t{\n\t\thideDiv(\"showresources\"); //No i18n\n\t}\n\n\n\n\n  showDiv(\"resourceloading\");\n\n   ");
/*      */         
/*  599 */         if (Constants.sqlManager)
/*      */         {
/*      */ 
/*  602 */           out.write("\n if(document.AMActionForm.typeofreport.value==\"glancereport\" && document.AMActionForm.reportmonitor[2].checked)\n {\n hideDiv(\"showresources\"); //No i18n\n            document.getElementById(\"sqlman\").style.display='block';\n\t\t\tdocument.getElementById(\"monitordetail\").style.height='200px';\n\n }\n ");
/*      */         }
/*      */         
/*      */ 
/*  606 */         out.write("\n\n var resourceType='';\n var a=document.AMActionForm.typeofreport.value;\n\n var b=document.AMActionForm.reportmonitor.value;\n\n var c=document.AMActionForm.typeofattribute.value;\n if(document.AMActionForm.typeofreport.value=='forecast') {\n\t\tc=document.AMActionForm.typeofforecastattribute.value; \n }\n\n var d=\"\";\n if(document.AMActionForm.typeofreport.value=='eumGlancereport'){\n \t d=document.AMActionForm.selectedEumInSchedule.value;\n }\n else{\n \t d=document.AMActionForm.trapName.value;\n }\n \n   var cust='-1';\n     var site='-1';\n     var custStr = '';\n     var siteStr = '';\n\n");
/*      */         
/*  608 */         if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*      */ 
/*  611 */           out.write("\n    cust=document.AMActionForm.organization.value;\n    custStr = '&cust='+cust;//No I18N\n    site=document.AMActionForm.haid.value;\n    siteStr = '&site='+site;//No I18N\n");
/*      */         }
/*      */         
/*      */ 
/*  615 */         out.write("\n \n var isCustom='false';\n  var url;\n  if(c.indexOf(\"#\")!=-1){\n var temp=c.split(\"#\");\n if(temp.length>0){\n c=temp[1];\n d=temp[0];\n isCustom='true';\n \n }\n }\n\n");
/*      */         
/*  617 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/*  620 */           out.write("\nvar raidoValue = jQuery(\"input[name='reportmonitor']:checked\").val()\t// NO I18N\nif(raidoValue == 'monitorgroup'){\n\t  resourceType = \"HAI\"\t// NO I18N\n\t  jQuery(\"#togglecustomfield\").html('");
/*  621 */           out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/*  622 */           out.write("')\t// NO I18N\n\t  ");
/*  623 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition()) {
/*  624 */             out.write("\n\t  document.AMActionForm.haid.disabled=true;\n\t  document.AMActionForm.haid.value='-1';\n\t  ");
/*      */           }
/*  626 */           out.write("\n\t  \n}else if(raidoValue == 'indimonitor'){\t// NO I18N\n\t if(document.AMActionForm.typeofreport.value=='eumGlancereport'){\n\t\t resourceType = document.AMActionForm.selectedEumInSchedule.value;\n\t } else if(document.AMActionForm.typeofreport.value=='sqlperformance') { //No I18N\n\t\t resourceType = 'MSSQL-DB-server'; //No I18N\n\t } else{\n\t\t resourceType = document.AMActionForm.trapName.value;\n\t }\n\t  \n\t jQuery(\"#togglecustomfield\").html('");
/*  627 */           out.print(FormatUtil.getString("am.webclient.schedulereport.reportfor.monitor"));
/*  628 */           out.write("')\t\t//No I18N\n\t ");
/*  629 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition()) {
/*  630 */             out.write("\n\t document.AMActionForm.haid.disabled=false;\n\t ");
/*      */           }
/*  632 */           out.write("\n}else{\n\t  resourceType = \"All\"\t\t//No I18N\n\t  jQuery(\"#togglecustomfield\").html('");
/*  633 */           out.print(FormatUtil.getString("am.webclient.schedulereport.reportfor.monitortypes"));
/*  634 */           out.write("')\t\t//No I18N\n\t  ");
/*  635 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition()) {
/*  636 */             out.write("\n\t  document.AMActionForm.haid.disabled=false;\n\t  ");
/*      */           }
/*  638 */           out.write("\n}\n\tif((!cf && resourceType != 'All') && document.AMActionForm.typeofreport.value != 'forecast' && document.AMActionForm.typeofreport.value != 'sqlperformance'){\n\t\tvar cURL=\"/myFields.do?method=getScheduledReportFilter&reportname=\"+a+\"&resourcetype=\"+resourceType+\"&isCustom=\"+isCustom+\"&attribute=\"+c+\"&resourcegroup=\"+d;\t//No I18N\n\t\tif(document.getElementById(\"customFieldID\") && document.getElementById(\"customFieldID\").value.trim()!=\"\"){\n\t\t\tcURL=cURL+\"&customFieldId=\"+document.getElementById(\"customFieldID\").value;\t//No I18N\t\t\n\t\t}\t\t\n\t\tif(document.getElementById(\"customFieldValueID\") && document.getElementById(\"customFieldValueID\").value.trim()!=\"\"){\n\t\t\tcURL=cURL+\"&customFieldValue=\"+document.getElementById(\"customFieldValueID\").value;//No I18N\n\t\t}\n   \t\tjQuery.ajax({\n\t\turl:cURL,\n\t\t//url:\"/myFields.do?method=getScheduledReportFilter&reportname=\"+a+\"&resourcetype=\"+resourceType+\"&isCustom=\"+isCustom+\"&attribute=\"+c+\"&resourcegroup=\"+d,\t\t//No I18N\n\t\tsuccess:function(data){\n\t\t\t");
/*  639 */           if (!com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(loginUserName)) {
/*  640 */             out.write("\n\t\t\tjQuery(\"#customFilter\").show()\t\t//No I18N\n\t\t\tshowCustomFieldValues(data,'customfieldfilter')\t\t//NO I18N\n\t\t\tif(document.getElementById(\"filterScheduleReport\") && document.getElementById(\"filterScheduleReport\").value!='no'){\n\t\t\t\t//\talert(document.getElementById(\"filterScheduleReport\").value);\n\t\t\t\t\tdocument.getElementById(\"customFieldID\").value=document.getElementById(\"filterScheduleReport\").value;\n\t\t\t\t\tloadURLType(document.getElementById(\"filterScheduleReport\").value,document.getElementById(\"filterScheduleReport\"));\n\t\t\t\t}\n\t\t\t");
/*      */           }
/*  642 */           out.write("\t\t//No I18N\n\t\t}\n\t});\n\n}\nif(resourceType == 'All'){\n\tjQuery(\"#customfieldfilter\").hide()\t//No I18N\n\tjQuery(\"#customFilter\").hide()\t\t//No I18N\n}\t\nvar condition = ''\nif(cf){\n\tcondition = \"&customfield=yes&customFieldValue=\"+option\t\t//No I18N\n}\n\n  if(document.AMActionForm.reportmonitor[0].checked)\n  {\n    url=\"/scheduleReports.do?method=sendAjaxDetails&report=\"+a+\"&resource=monitorgroup&attribute=\"+c+\"&resourcetypes=\"+d+\"&edit=\"+e+\"&sid=\"+s+\"&customAttribute=\"+isCustom+condition+custStr+siteStr+\"&todaytime=\"+");
/*  643 */           out.print(System.currentTimeMillis());
/*  644 */           out.write(";//No i18n\n\n  }\n  else if(document.AMActionForm.typeofreport.value==\"glancereport\" && document.AMActionForm.reportmonitor[2].checked)\n  {   \n    url=\"/scheduleReports.do?method=sendAjaxDetails&report=\"+a+\"&resource=indimonitor&attribute=\"+c+\"&resourcetypes=\"+d+\"&edit=\"+e+\"&sid=\"+s+\"&customAttribute=\"+isCustom+condition+custStr+siteStr+\"&todaytime=\"+");
/*  645 */           out.print(System.currentTimeMillis());
/*  646 */           out.write("; //No i18n\n  }\n   else if(document.AMActionForm.typeofreport.value==\"sqlperformance\")\n   {\n\t\turl=\"/scheduleReports.do?method=sendAjaxDetails&report=\"+a+\"&resource=indimonitor&attribute=\"+c+\"&resourcetypes=MSSQL-DB-server&edit=\"+e+\"&sid=\"+s+\"&customAttribute=\"+isCustom+\"&todaytime=\"+");
/*  647 */           out.print(System.currentTimeMillis());
/*  648 */           out.write("; //No i18n\n  }\n  else\n  {\n\t  url=\"/scheduleReports.do?method=sendAjaxDetails&report=\"+a+\"&resource=monitor&attribute=\"+c+\"&resourcetypes=\"+d+\"&edit=\"+e+\"&sid=\"+s+\"&customAttribute=\"+isCustom+condition+custStr+siteStr+\"&todaytime=\"+");
/*  649 */           out.print(System.currentTimeMillis());
/*  650 */           out.write(";//No i18n\n  }\n   \n");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  656 */           out.write("\n  \n  if(document.AMActionForm.reportmonitor[0].checked)\n  {\n        var attrtype=document.AMActionForm.typeofattribute.value;\n        if(attrtype.indexOf(\"#\")!=-1){\n\t        url=\"/scheduleReports.do?method=sendAjaxDetails&report=\"+a+\"&resource=monitorgroup&attribute=\"+c+\"&resourcetypes=\"+d+\"&edit=\"+e+\"&sid=\"+s+\"&customAttribute=\"+isCustom+\"&todaytime=\"+");
/*  657 */           out.print(System.currentTimeMillis());
/*  658 */           out.write(";//No i18n\n\t    }else {\n\t        url=\"/scheduleReports.do?method=sendAjaxDetails&report=\"+a+\"&resource=monitorgroup&attribute=\"+c+\"&resourcetypes='MSSQL-DB-server','Windows 2003'&edit=\"+e+\"&sid=\"+s+\"&customAttribute=\"+isCustom+\"&todaytime=\"+");
/*  659 */           out.print(System.currentTimeMillis());
/*  660 */           out.write(";//No i18n\n        }\n  }\n  else if(document.AMActionForm.typeofreport.value==\"glancereport\" && document.AMActionForm.reportmonitor[2].checked)\n  {\n    url=\"/scheduleReports.do?method=sendAjaxDetails&report=\"+a+\"&resource=indimonitor&attribute=\"+c+\"&resourcetypes=MSSQL-DB-server&edit=\"+e+\"&sid=\"+s+\"&customAttribute=\"+isCustom+\"&todaytime=\"+");
/*  661 */           out.print(System.currentTimeMillis());
/*  662 */           out.write("; //No i18n\n  }\n  else\n  {\n\t url=\"/scheduleReports.do?method=sendAjaxDetails&report=\"+a+\"&resource=monitor&attribute=\"+c+\"&resourcetypes=MSSQL-DB-server&edit=\"+e+\"&sid=\"+s+\"&customAttribute=\"+isCustom+\"&todaytime=\"+");
/*  663 */           out.print(System.currentTimeMillis());
/*  664 */           out.write(";//No i18n\n  }\n \n    \n\n");
/*      */         }
/*      */         
/*      */ 
/*  668 */         out.write("\n\n    http1.open(\"GET\",url,true);\n    http1.onreadystatechange =getmonitortypes;\n    http1.send(null);\n  }\n\n  function loadURLType(option,obj){\n\t  \n  \t\tif(option != 'no'){\n\t\tvar monitorType ;\n\t\tobj.style.backgroundColor = '#FFF8C6';\n  \t\tvar radioOption = jQuery(\"input[name='reportmonitor']:checked\").val()\t//No I18N\n  \t\t  if(radioOption == 'monitorgroup'){\n  \t\t\t  monitorType = \"HAI\"\t\t//No I18N\n  \t\t  }else if(radioOption == 'indimonitor'){\t\t//No I18N\n  \t\t\t if(document.AMActionForm.typeofreport.value=='eumGlancereport'){\n  \t\t\t\tmonitorType = document.AMActionForm.selectedEumInSchedule.value\n  \t\t\t }else{\n  \t\t\t\tmonitorType = document.AMActionForm.trapName.value\n  \t\t\t }\n  \t\t  }else{\n  \t\t\t  monitorType = \"All\"\t\t//No I18N\n  \t\t  }\n  \t\tvar d=\"\";\n  \t\t var c=document.AMActionForm.typeofattribute.value;\n  \t\t var isCustom='false';\n  \t\t  var url;\n  \t\t  if(c.indexOf(\"#\")!=-1){\n  \t\t var temp=c.split(\"#\");\n  \t\t if(temp.length>0){\n  \t\t c=temp[1];\n  \t\t d=temp[0];\n  \t\t isCustom='true';\n  \t\t \n  \t\t }\n  \t\t }\n  \t\t\n  \t\t\tURL = \"/myFields.do?method=getFieldValues&aliasName=\"+option.substring(0,option.indexOf(\"$\"))+\"&optionSel=\"+option.substring(option.indexOf(\"$\")+1)+\"&reportspage=schedulereport&monitortype=\"+monitorType+\"&reportname=\"+document.AMActionForm.typeofreport.value+\"&attribute=\"+c+\"&iscustom=\"+isCustom+\"&resgroup=\"+d; // NO I18N\n");
/*  669 */         out.write("\t\t\tif(document.getElementById(\"customFieldID\") && document.getElementById(\"customFieldID\").value.trim()!=\"\"){\n  \t\t\t\tURL=URL+\"&customFieldId=\"+document.getElementById(\"customFieldID\").value;\t\t\t//No I18N\n  \t\t\t}\t\t\n  \t\t\tif(document.getElementById(\"customFieldValueID\") && document.getElementById(\"customFieldValueID\").value.trim()!=\"\"){\n  \t\t\t\tURL=URL+\"&customFieldValue=\"+document.getElementById(\"customFieldValueID\").value;//No I18N\n  \t\t\t}\n              jQuery.ajax({\n          \t\turl:URL,\n          \t\tsuccess:function(data){\n          \t\t\tshowCustomFieldValues(data,'filterByOption');\t\t//No I18N\n          \t\t\tloadUrl(jQuery(\"#selectFieldVal\").val())\t//No I18N\n\t\t\t\t\tdocument.getElementById(\"customFieldValueID\").value=document.getElementById(\"selectFieldVal\").value;\n          \t\t}\n          \t});\n  \t\t}else{\n  \t\t\tconstructUrl()\n  \t\t\tobj.style.backgroundColor = '';\n  \t\t\tjQuery(\"#filterByOption\").hide();\t//No I18N\n  \t\t}\n  \t\n  \t\t\n  \t}\n  \t\n  function loadUrl(option){\n\t   var sid=\"");
/*  670 */         out.print(request.getParameter("sid"));
/*  671 */         out.write("\";\n\t  constructUrl('false',sid,true,option)\n\t  \n  }\n  \n  \n  function showCustomFieldValues(data,divname)\n  {\n                  jQuery(\"#\"+divname).show();\t//No I18N\n                  jQuery(\"#\"+divname).html(data);//No I18N\n  }\n\n function getmonitortypes()\n{\n  if(http1.readyState ==4){ \n     hideDiv(\"resourceloading\");\n     var result = http1.responseText;\n     jQuery(\"#monitordetail\").html(result)\t//No I18N\n\t if(document.AMActionForm.typeofreport.value != 'sqlperformance') {\n\t\t jQuery(\"#monitordetail\").show();\t//No I18N\n\t } else {\n\t\t jQuery(\"#monitordetail\").hide();\t//No I18N\n\t }\n    \n \n");
/*  672 */         if (Constants.sqlManager)
/*      */         {
/*      */ 
/*  675 */           out.write("\nif(document.AMActionForm.reportmonitor[1].checked)\n{\ndocument.getElementById(\"sqlman\").style.display='none';\ndocument.getElementById(\"monitordetail\").style.display='none';\n}\n");
/*      */         }
/*      */         
/*      */ 
/*  679 */         out.write("\n\n   ");
/*      */         
/*      */ 
/*      */ 
/*  683 */         if ((request.getParameter("edit") != null) && (!Constants.isMinimizedversion()))
/*      */         {
/*      */ 
/*      */ 
/*  687 */           out.write("\n\n if(document.getElementById(\"MG-99\") && document.getElementById(\"MG-99\").checked==true)\n        {\n            ");
/*  688 */           for (int i = 0; i < Integer.parseInt(mgcount); i++)
/*      */           {
/*  690 */             out.write("\n            document.getElementById(\"MG-");
/*  691 */             out.print(i);
/*  692 */             out.write("\").checked=true;\n            document.getElementById(\"MG-");
/*  693 */             out.print(i);
/*  694 */             out.write("\").disabled=true;\n            ");
/*      */           }
/*  696 */           out.write("\n\n\n        }\n\n\n if((document.AMActionForm.typeofreport.value==\"attribute\")&&((document.AMActionForm.typeofattribute.value==\"cpuid\")||(document.AMActionForm.typeofattribute.value==\"mem\")))\n      {\n\t\t   ");
/*  697 */           if ((!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (RV != null) && (RV.equals("true"))) {
/*  698 */             out.write("\n           document.getElementById(\"per\").options[11]=new Option(\"");
/*  699 */             out.print(FormatUtil.getString("am.webclient.period.polleddata", new String[] { PV }));
/*  700 */             out.write("\",\"14\");\n\t\t   ");
/*      */           }
/*  702 */           out.write("\n\n      }\n\n if((document.AMActionForm.reportmonitor[1].checked==true)&&(document.AMActionForm.typeofreport.value!=\"custom\" && document.AMActionForm.typeofreport.value!=\"downtime\"))\n {\n\t //if (document.getElementById('10000')!=null && document.getElementById('10000').checked) {\n\t //\t document.getElementById(\"customAttribute-1\").checked=true;\n     //    document.getElementById(\"customAttribute-1\").disabled=true;\n\t//}\n\n   if((document.getElementById(\"1\"))&&(document.getElementById(\"1\").checked==true))\n        {\n          ");
/*  703 */           for (int i = 0; i < Integer.parseInt(appcount); i++)
/*      */           {
/*  705 */             out.write("\n            document.getElementById(\"APP-");
/*  706 */             out.print(i);
/*  707 */             out.write("\").checked=true;\n            document.getElementById(\"APP-");
/*  708 */             out.print(i);
/*  709 */             out.write("\").disabled=true;\n          ");
/*      */           }
/*  711 */           out.write("\n        }\n        if((document.getElementById(\"19\"))&&(document.getElementById(\"19\").checked==true))\n            {\n\n\n                ");
/*  712 */           for (int i = 0; i < Integer.parseInt(syscount); i++)
/*      */           {
/*  714 */             out.write("\n                document.getElementById(\"servers-");
/*  715 */             out.print(i);
/*  716 */             out.write("\").checked=true;\n                document.getElementById(\"servers-");
/*  717 */             out.print(i);
/*  718 */             out.write("\").disabled=true;\n                ");
/*      */           }
/*  720 */           out.write("\n\n            }\n            if((document.getElementById(\"7\"))&&(document.getElementById(\"7\").checked==true))\n        {\n            ");
/*  721 */           for (int i = 0; i < Integer.parseInt(dbcount); i++)
/*      */           {
/*  723 */             out.write("\n            document.getElementById(\"DB-");
/*  724 */             out.print(i);
/*  725 */             out.write("\").checked=true;\n            document.getElementById(\"DB-");
/*  726 */             out.print(i);
/*  727 */             out.write("\").disabled=true;\n            ");
/*      */           }
/*  729 */           out.write("\n\n\n        }\n         if((document.getElementById(\"29\"))&&(document.getElementById(\"29\").checked==true))\n        {\n            ");
/*  730 */           for (int i = 0; i < Integer.parseInt(sercount); i++)
/*      */           {
/*  732 */             out.write("\n            document.getElementById(\"services-");
/*  733 */             out.print(i);
/*  734 */             out.write("\").checked=true;\n            document.getElementById(\"services-");
/*  735 */             out.print(i);
/*  736 */             out.write("\").disabled=true;\n            ");
/*      */           }
/*  738 */           out.write("\n\n\n        }\n         if((document.getElementById(\"26\"))&&(document.getElementById(\"26\").checked==true))\n        {\n\n             ");
/*  739 */           for (int i = 0; i < Integer.parseInt(mscount); i++)
/*      */           {
/*  741 */             out.write("\n            document.getElementById(\"mail-");
/*  742 */             out.print(i);
/*  743 */             out.write("\").checked=true;\n            document.getElementById(\"mail-");
/*  744 */             out.print(i);
/*  745 */             out.write("\").disabled=true;\n\n           ");
/*      */           }
/*  747 */           out.write("\n        }\n        if((document.getElementById(\"70\"))&&(document.getElementById(\"70\").checked==true))\n\t        {\n\t            ");
/*  748 */           for (int i = 0; i < Integer.parseInt(erpcount); i++)
/*      */           {
/*  750 */             out.write("\n\t            document.getElementById(\"erpservers-");
/*  751 */             out.print(i);
/*  752 */             out.write("\").checked=true;\n\t            document.getElementById(\"erpservers-");
/*  753 */             out.print(i);
/*  754 */             out.write("\").disabled=true;\n\t            ");
/*      */           }
/*  756 */           out.write("\n\n        }\n        if((document.getElementById(\"80\"))&&(document.getElementById(\"80\").checked==true))\n\t        {\n\t            ");
/*  757 */           for (int i = 0; i < Integer.parseInt(momcount); i++)
/*      */           {
/*  759 */             out.write("\n\t            document.getElementById(\"middleware-");
/*  760 */             out.print(i);
/*  761 */             out.write("\").checked=true;\n\t            document.getElementById(\"middleware-");
/*  762 */             out.print(i);
/*  763 */             out.write("\").disabled=true;\n\t            ");
/*      */           }
/*  765 */           out.write("\n\n        }\n         if((document.getElementById(\"12\"))&&(document.getElementById(\"12\").checked==true))\n        {\n            ");
/*  766 */           for (int i = 0; i < Integer.parseInt(urlcount); i++)
/*      */           {
/*  768 */             out.write("\n            document.getElementById(\"web-");
/*  769 */             out.print(i);
/*  770 */             out.write("\").checked=true;\n            document.getElementById(\"web-");
/*  771 */             out.print(i);
/*  772 */             out.write("\").disabled=true;\n            ");
/*      */           }
/*  774 */           out.write("\n\n        }\n         if((document.getElementById(\"100\"))&&(document.getElementById(\"100\").checked==true))\n         {\n             ");
/*  775 */           for (int i = 0; i < Integer.parseInt(virCount); i++)
/*      */           {
/*  777 */             out.write("\n             document.getElementById(\"vir-");
/*  778 */             out.print(i);
/*  779 */             out.write("\").checked=true;\n             document.getElementById(\"vir-");
/*  780 */             out.print(i);
/*  781 */             out.write("\").disabled=true;\n             ");
/*      */           }
/*  783 */           out.write("\n\n         }\n         if((document.getElementById(\"110\"))&&(document.getElementById(\"110\").checked==true))\n         {\n             ");
/*  784 */           for (int i = 0; i < Integer.parseInt(cldCount); i++)
/*      */           {
/*  786 */             out.write("\n             document.getElementById(\"cld-");
/*  787 */             out.print(i);
/*  788 */             out.write("\").checked=true;\n             document.getElementById(\"cld-");
/*  789 */             out.print(i);
/*  790 */             out.write("\").disabled=true;\n             ");
/*      */           }
/*  792 */           out.write("\n\n         }\n\n    }\n      ");
/*      */         }
/*  794 */         out.write("\n\n\n       if((document.AMActionForm.typeofreport.value==\"attribute\")&&((document.AMActionForm.typeofattribute.value==\"cpuid\")||(document.AMActionForm.typeofattribute.value==\"mem\")))\n      {\n\t\t");
/*  795 */         if ((!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (RV != null) && (RV.equals("true"))) {
/*  796 */           out.write("\n           document.getElementById(\"per\").options[11]=new Option(\"");
/*  797 */           out.print(FormatUtil.getString("am.webclient.period.polleddata", new String[] { PV }));
/*  798 */           out.write("\",\"14\");\n\t\t ");
/*      */         }
/*  800 */         out.write("\n      }\n\n      if((document.AMActionForm.typeofreport.value==\"attribute\")&&((document.AMActionForm.typeofattribute.value!=\"cpuid\")&&(document.AMActionForm.typeofattribute.value!=\"mem\")))\n      {\n        var combo = document.getElementById(\"per\");\n        var len=combo.options.length;\n\n        if(len==12)\n        {\n\n            if(combo.options[11])\n            {\n\n               combo.removeChild(combo.options[11]);\n\n             }\n        }\n\n    }\n\n       if((document.AMActionForm.typeofreport.value==\"attribute\")&&(document.AMActionForm.reportmonitor[1].checked==true))\n        {\n            if(document.AMActionForm.typeofattribute.value=='responseTime')\n            {\n               ");
/*  801 */         if (!Constants.isMinimizedversion())
/*      */         {
/*      */ 
/*  804 */           out.write("\n\n                    document.getElementById(\"7\").disabled=true;\n\n\n\n                ");
/*  805 */           for (int i = 0; i < Integer.parseInt(dbcount); i++)
/*      */           {
/*  807 */             out.write("\n\n                    document.getElementById(\"DB-");
/*  808 */             out.print(i);
/*  809 */             out.write("\").disabled=true;\n                ");
/*      */           }
/*      */         }
/*      */         else {
/*  813 */           out.write("\n            document.getElementById(\"DB-0\").disabled=true;\n                 ");
/*      */         }
/*  815 */         out.write("\n            }\n        }\n\n\n\n\n\n             ");
/*      */         
/*  817 */         if ((newperiod != null) && (newperiod.equals("14"))) {
/*  818 */           out.write("\n\n           document.getElementById(\"per\").options[11].selected=true;\n           ");
/*      */         }
/*  820 */         out.write("\n\n\tif(document.AMActionForm.reportmonitor[0].checked)\n\t{\n\t     toggleallDivs('show');\n\t}\n\tif(document.AMActionForm.typeofreport.value==\"availability\")\n\t{\n\t\tif(document.AMActionForm.reportmonitor[0].checked)\n\t\t{\n\t\t\tshowDiv(\"showbusiness\");\n\t\t\tshowDiv(\"showcsv\");\n\t\t}\n\t\telse if(document.AMActionForm.reportmonitor[1].checked)\n\t\t{\n\t\t\thideDiv(\"showbusiness\");\n\t\t\tshowDiv(\"showcsv\");\n\t\t}\n\t}\n\tif(document.AMActionForm.typeofreport.value==\"glancereport\")\n\t{\n\t\t\tshowDiv(\"showcsv\");\n\t}\n\tif(document.AMActionForm.typeofreport.value==\"health\")\n\t{\n\t\tshowDiv(\"showcsv\");\n\t}\n\t\t\n    }\n\n }\n\n function callAction()\n {\n\t\t");
/*  821 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */           return;
/*  823 */         out.write("\n   showDiv(\"takeaction\");\n  }\n  function removeAction()\n  {\n    hideDiv(\"takeaction\");\n  }\n function getAction()\n{\n\n    if(document.AMActionForm.taskName.value=='')\n  {\n    alert(\"");
/*  824 */         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforschedulename.text"));
/*  825 */         out.write("\");\n     document.AMActionForm.taskName.focus();\n     return false;\n   }\n   if(document.AMActionForm.priority.value=='')\n  {\n\n    alert(\"");
/*  826 */         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforscheduleemail.text"));
/*  827 */         out.write("\");\n     document.AMActionForm.priority.focus();\n     return false;\n   }\n   else\n   {\n    var a=document.AMActionForm.priority.value;\n    var b=encodeURIComponent(document.AMActionForm.taskName.value);\n    url=\"/scheduleReports.do?method=sendActionDetails&emailid=\"+a+\"&emailname=\"+b;\n\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getActionTypes;\n    http.send(null);\n   }\n\n\n }\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n      var result = http.responseText;\n      hideDiv(\"takeaction\");\n      var id=result;\n      var stringtokens=id.split(\",\");\n      smessage=stringtokens[0];\n      sid=stringtokens[1];\n      hideDiv(\"actionmessage\");\n     if(smessage=='null')\n     {\n             hideDiv(\"actionmessage\");\n            var name=document.AMActionForm.taskName.value+\"_Action\";\n            document.AMActionForm.sendmail.options[document.AMActionForm.sendmail.length] =new Option(name,sid,false,true);\n     }\n     else\n     {\n            showDiv(\"actionmessage\");\n            document.getElementById(\"actionmessage\").innerHTML=smessage+\" <a class='staticlinks' href='/adminAction.do?method=showMailServerConfiguration&admin=true'><font color='red'>");
/*  828 */         out.print(FormatUtil.getString("am.webclient.schedulereport.click.here.text"));
/*  829 */         out.write("</a>.</font>\";\n     }\n\n\n    }\n }\n\n  function myOnLoad()\n  {\n        hideDiv(\"showtrend\");\n        hideDiv(\"showoutage\");\n        hideDiv(\"showexcel\");\n        hideDiv(\"showbusiness\");\n        hideDiv(\"resourceloading\");\n        hideDiv(\"actionmessage\");\n     ");
/*      */         
/*      */ 
/*      */ 
/*  833 */         if (request.getParameter("edit") != null)
/*      */         {
/*      */ 
/*      */ 
/*  837 */           out.write("\n\n        editCheckAttribute();\n\t\n         if('");
/*  838 */           if (_jspx_meth_bean_005fwrite_005f0(_jspx_page_context))
/*      */             return;
/*  840 */           out.write("'=='Daily')\n\n         {\n           hideDiv('view_weekly$view_custom');\n         }\n         else if('");
/*  841 */           if (_jspx_meth_bean_005fwrite_005f1(_jspx_page_context))
/*      */             return;
/*  843 */           out.write("'=='Weekly')\n         {\n            showDiv('view_weekly');\n            hideDiv('view_custom');\n         }\n         else\n         {\n            hideDiv('view_weekly');\n            showDiv('view_custom');\n\n         }\n\n\n\n\n\n    ");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  848 */           out.write("\n      document.AMActionForm.taskMethod[0].checked=true;\n       checkAttribute();\n       ");
/*      */         }
/*  850 */         out.write("\n  }\n\n function checkMonth()\n {\n\n   if(document.AMActionForm.mode.checked)\n   {\n       document.getElementById(\"jan\").checked=true;\n       document.getElementById(\"feb\").checked=true;\n       document.getElementById(\"mar\").checked=true;\n       document.getElementById(\"apr\").checked=true;\n       document.getElementById(\"may\").checked=true;\n       document.getElementById(\"jun\").checked=true;\n       document.getElementById(\"jul\").checked=true;\n       document.getElementById(\"aug\").checked=true;\n       document.getElementById(\"sep\").checked=true;\n       document.getElementById(\"oct\").checked=true;\n       document.getElementById(\"nov\").checked=true;\n       document.getElementById(\"dec\").checked=true;\n   }\n   else\n   {\n       document.getElementById(\"jan\").checked=false;\n       document.getElementById(\"feb\").checked=false;\n       document.getElementById(\"mar\").checked=false;\n       document.getElementById(\"apr\").checked=false;\n       document.getElementById(\"may\").checked=false;\n       document.getElementById(\"jun\").checked=false;\n");
/*  851 */         out.write("       document.getElementById(\"jul\").checked=false;\n       document.getElementById(\"aug\").checked=false;\n       document.getElementById(\"sep\").checked=false;\n       document.getElementById(\"oct\").checked=false;\n       document.getElementById(\"nov\").checked=false;\n       document.getElementById(\"dec\").checked=false;\n   }\n\n }\n\n function checkName(val)\n{\n var url;\n\tif(val==\"\")\n\t{\n               // document.AMActionForm.taskName.focus();\n                return false;\n\n\t}\n\t");
/*  852 */         if (request.getParameter("edit") != null)
/*      */         {
/*  854 */           out.write("\n\n\t url=\"/scheduleReports.do?method=checkScheduleName&schedulename=\"+val+\"&edit=true\";\n\t ");
/*      */         } else {
/*  856 */           out.write("\n\t url=\"/scheduleReports.do?method=checkScheduleName&schedulename=\"+val+\"&edit=false\";\n\t ");
/*      */         }
/*  858 */         out.write("\n\thttp.open(\"GET\",url,true);\n\thttp.onreadystatechange = handleScheduleName;\n\thttp.send(null);\n\n}\n\nfunction handleScheduleName()\n{\n\n\tif(http.readyState == 4)\n\t{\n\t\tvar result = http.responseText;\n\n\t\tvar element=document.AMActionForm.taskName;\n\t\tvar temp=null;\n\t\tvar isPointerReq=false;\n\t\tvar red=\"#FF0000\";\n\t\tddrivetip(element,temp,result,isPointerReq,true,red,300);\n\t}\n}\n\nvar retaintree;\n\nfunction selectAllChildCKbs(obname,ckb,myfrm)\n{\n    if(typeof(myfrm.length)==\"undefined\") {\n\n        return;\n    }\n\n    for(i=0;i<myfrm.length;i++) {\n\n        if(myfrm.elements[i].type == \"checkbox\" && myfrm.elements[i].id.indexOf(obname) == 0) {\n                myfrm.elements[i].checked = ckb.checked;\n        }\n    }\n\n}\n\n\nfunction deselectParentCKbs(obname1,ckb1,myfrm)\n{\n\tif(ckb1.checked)\n\treturn;\n\n\tvar temp1=obname1.split(\"|\");\n\tfor(i=0;i<temp1.length;i++)\n\t{\n\tif(i==0)\n\tparentresid=temp1[i];\n\telse\n\tparentresid=parentresid+\"|\"+temp1[i];\n\n\tfor(j=0;j<myfrm.length;j++) {\n\n\t\tif(myfrm.elements[j].id == parentresid) {\n\t\t\tmyfrm.elements[j].checked = false;\n");
/*  859 */         out.write("\t\t}\n\t    }\n\t}\n\n}\n\nfunction toggleallDivs(action)\n{\n\tvar newDisplay ;\n\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n\telse newDisplay = \"table-row\"; //Netscape and Mozilla\n\tvar collapseid ;\n\tcollapseid= document.getElementById(\"showall\");\n\tif(collapseid.style.display==\"inline\")\n\t{\n\t\tdocument.getElementById('showall').style.display=\"none\";\n\t\tdocument.getElementById('hideall').style.display=\"inline\";\n\t}\n\telse\n\t{\n\t\tnewDisplay=\"none\";\n\t\tdocument.getElementById('showall').style.display=\"inline\";\n\t\tdocument.getElementById('hideall').style.display=\"none\";\n\t}\n\tvar table = document.getElementById(\"allMonitorGroups\");\n\trows = table.rows;\n\trowcount = rows.length;\n\tfor( i=1;i<rowcount;i++)\n\t{\n\t\tif(rows[i].id.indexOf(\"#monitor\")>=0)\n\t\trows[i].style.display=newDisplay;\n\t}\n\n\tvar plus,minus;\n\tif(newDisplay=='none')\n\t{\n\t\tplus=\"inline\";\n\t\tminus=\"none\"\n\t}\n\telse\n\t{\n\t\tplus=\"none\";\n\t\tminus=\"inline\"\n\t}\n\tvar alldivs =document.getElementsByTagName(\"div\");\n\tfor(var j=0; j < alldivs.length; j++)\n\t{\n\t\tvar singlediv = alldivs[j];\n");
/*  860 */         out.write("\t\tvar id = singlediv.id;\n\t\tif(id.indexOf(\"monitorHide\")>=0)\n\t\t{\n\t\t\tsinglediv.style.display =minus ;\n\t\t}\n\t\tif(id.indexOf(\"monitorShow\")>=0)\n\t\t{\n\t\t\tsinglediv.style.display =plus;\n\t\t}\n\t}\n}\n\nvar rows;\nvar rowcount,start;\nvar idtotoggle;\nvar toggletype;\n\nfunction toggleChildMos(tempidtotoggle)\n{\n\tidtotoggle=tempidtotoggle;\n\tvar table = document.getElementById(\"allMonitorGroups\");\n\trows = table.rows;\n\trowcount = rows.length;\n\tfor( i=1;i<rowcount;i++)\n\t{\n\t\tvar myrow = rows[i];\n\t\tif(myrow.id==idtotoggle)\n\t\t{\n\t\t\tif(rows[i].style.display=='none')\n\t\t\t{\n\t\t\t    toggletype='none';\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t    toggletype='block';\n\t\t\t}\n\t\t\tbreak;\n\t\t}\n\t}\n\tif(toggletype=='none')\n\t{\n\t  slideDown();\n\t}\n\telse\n\t{\n\thideOtherRows();\n\t}\n        return;\n}\n\nfunction hideOtherRows()\n{\n\tvar newDisplay = \"block\";\n\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n\telse newDisplay = \"table-row\"; //Netscape and Mozilla\n\tvar i;\n\tfor(i=1;i<rowcount;i++)\n\t{\n\t\tif(rows[i].id.indexOf( idtotoggle)!=-1)\n\t\t{\n\n\t\t\trows[i].style.display = \"none\";\n");
/*  861 */         out.write("\t\t}\n\n\t}\n\treturn;\n\n}\n\nfunction slideDown()\n{\n\tvar newDisplay = \"block\";\n\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n\telse newDisplay = \"table-row\"; //Netscape and Mozilla\n\tvar i;\n\tfor(i=1;i<rowcount;i++)\n\t{\n\t\tif(rows[i].id == idtotoggle)\n\t\t{\n\t\t\trows[i].style.display = newDisplay;\n\t\t\trows[i].removeAttribute(\"style\");\n\t\t\trows[i].className = \"leftcells\";\n\t\t}\n\t\telse\n\t\t{\n\t\t\trows[i].style.background = \"#FFFFFF\";\n\t\t}\n\t}\n\treturn;\n\n}\n\nfunction toggleTreeImage(divname)\n{\n\t var hide1=\"monitorHide\"+divname;\n\t var show1=\"monitorShow\"+divname;\n\t if(document.getElementById(show1))\n\t {\n\t if(document.getElementById(show1).style.display == 'inline')\n\t {\n\t\t//if it is to show the child elements just return changing the image of current monitor group level and return\n\t\tdocument.getElementById(show1).style.display='none';\n\t\tdocument.getElementById(hide1).style.display='inline';\n\t\treturn;\n\t }\n\t }\n\t else\n\t {\n\t \treturn;\n\t }\n\t //else if it is to hide an monitor group then parse through all the child elements and find subgroups and change the images to minus\n");
/*  862 */         out.write("\tvar alldivs =document.getElementsByTagName(\"div\");\n\tvar i;\n\tfor(i=0; i <alldivs.length ; i++)\n\t{\n\t\tif((alldivs[i].id.indexOf(hide1)) >= 0)\n\t\t{\n\t\t\thidediv=document.getElementById(alldivs[i].id) ;\n\t\t\tif(hidediv)\n\t\t\t{\n\t\t\t\tif(hidediv.style.display == 'inline')\n\t\t\t\thidediv.style.display='none';\n\t\t\t}\n\t\t}\n\t\tif((alldivs[i].id.indexOf(show1)) >= 0)\n\t\t{\n\t\t\tvar showdiv;\n\t\t\tshowdiv=document.getElementById(alldivs[i].id) ;\n\t\t\tif(showdiv)\n\t\t\t{\n\t\t\t\tif(showdiv.style.display == 'none')\n\t\t\t\tshowdiv.style.display='inline';\n\t\t\t}\n\t\t}\n\t}\n}\n\nfunction showAllmngrp(){\n\nshowDiv(\"resourceloading\");\n\nif(document.AMActionForm.monitortype[1].checked){\n\t");
/*  863 */         String rid = request.getParameter("sid");
/*  864 */         out.write("\n\n\turl=\"/alertEscalation.do?method=sendAjaxDetails&sid=");
/*  865 */         out.print(rid);
/*  866 */         out.write("&resource=monitorgroup\";\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getMonitorGroup ;\n    http.send(null);\n}else{\n\thideDiv(\"resourceloading\");\n hideDiv('monitorgroups');\n\n}\n\n\n}\n\nfunction getMonitorGroup(){\nhideDiv(\"resourceloading\");\n\n\tif(http.readyState == 4)\n\t{\n\t\t\tvar result = http.responseText;\n\t\t\tif(result != null){\n\t\t\tshowDiv('monitorgroups');\n\t\t\tdocument.getElementById(\"monitorgroups\").innerHTML=result;\n\n\t}else{\n\n\t\thideDiv('monitorgroups');\n\n\t}\n\t}\n\n\n}\n\n\n\n\n\n</script>\n\n\n");
/*      */         
/*  868 */         org.apache.struts.taglib.tiles.InsertTag _jspx_th_tiles_005finsert_005f0 = (org.apache.struts.taglib.tiles.InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(org.apache.struts.taglib.tiles.InsertTag.class);
/*  869 */         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  870 */         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */         
/*  872 */         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/*  873 */         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  874 */         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */           for (;;) {
/*  876 */             out.write(10);
/*      */             
/*  878 */             PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/*  879 */             _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/*  880 */             _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/*  882 */             _jspx_th_tiles_005fput_005f0.setName("title");
/*      */             
/*  884 */             _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.schedulereport.newschedule.title.text"));
/*  885 */             int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/*  886 */             if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/*  887 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */             }
/*      */             
/*  890 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/*  891 */             out.write(10);
/*  892 */             if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  894 */             out.write(10);
/*      */             
/*  896 */             PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  897 */             _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/*  898 */             _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/*  900 */             _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */             
/*  902 */             _jspx_th_tiles_005fput_005f2.setType("string");
/*  903 */             int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/*  904 */             if (_jspx_eval_tiles_005fput_005f2 != 0) {
/*  905 */               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/*  906 */                 out = _jspx_page_context.pushBody();
/*  907 */                 _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/*  908 */                 _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  911 */                 out.write(10);
/*  912 */                 out.write(9);
/*      */                 
/*  914 */                 if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                 {
/*  916 */                   out.write(10);
/*  917 */                   out.write(9);
/*  918 */                   out.write(9);
/*  919 */                   out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                   
/*      */ 
/*  922 */                   String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                   
/*  924 */                   out.write("\n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n <tr>\n\t     <td height=\"21\"  class=\"leftlinksheading\">");
/*  925 */                   out.print(FormatUtil.getString("am.webclient.admin.heading"));
/*  926 */                   out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/*  928 */                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  929 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  930 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*  931 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  932 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/*  934 */                       out.write(10);
/*      */                       
/*  936 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  937 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  938 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/*  940 */                       _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/*  941 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  942 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/*  944 */                           out.write("\n        ");
/*  945 */                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  946 */                           out.write(10);
/*  947 */                           out.write(32);
/*  948 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  949 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  953 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  954 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/*  957 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  958 */                       out.write(10);
/*  959 */                       out.write(32);
/*      */                       
/*  961 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  962 */                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  963 */                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  964 */                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  965 */                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                         for (;;) {
/*  967 */                           out.write("\n     ");
/*      */                           
/*  969 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f0 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  970 */                           _jspx_th_ea_005feeadminlink_005f0.setPageContext(_jspx_page_context);
/*  971 */                           _jspx_th_ea_005feeadminlink_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                           
/*  973 */                           _jspx_th_ea_005feeadminlink_005f0.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*      */                           
/*  975 */                           _jspx_th_ea_005feeadminlink_005f0.setEnableClass("new-left-links");
/*  976 */                           int _jspx_eval_ea_005feeadminlink_005f0 = _jspx_th_ea_005feeadminlink_005f0.doStartTag();
/*  977 */                           if (_jspx_eval_ea_005feeadminlink_005f0 != 0) {
/*  978 */                             if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  979 */                               out = _jspx_page_context.pushBody();
/*  980 */                               _jspx_th_ea_005feeadminlink_005f0.setBodyContent((BodyContent)out);
/*  981 */                               _jspx_th_ea_005feeadminlink_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  984 */                               out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  985 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f0.doAfterBody();
/*  986 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  989 */                             if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  990 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  993 */                           if (_jspx_th_ea_005feeadminlink_005f0.doEndTag() == 5) {
/*  994 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0); return;
/*      */                           }
/*      */                           
/*  997 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0);
/*  998 */                           out.write(10);
/*  999 */                           out.write(32);
/* 1000 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1001 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1005 */                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1006 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                       }
/*      */                       
/* 1009 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1010 */                       out.write(10);
/* 1011 */                       out.write(32);
/* 1012 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1013 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1017 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1018 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/* 1021 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1022 */                   out.write("\n    </td>\n</tr>\n\n<tr>\n\n<tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/* 1024 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1025 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1026 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/* 1027 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1028 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/* 1030 */                       out.write(10);
/*      */                       
/* 1032 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1033 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1034 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/* 1036 */                       _jspx_th_c_005fwhen_005f1.setTest("${param.method!='showMailServerConfiguration'}");
/* 1037 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1038 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/* 1040 */                           out.write("\n    ");
/*      */                           
/* 1042 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f1 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1043 */                           _jspx_th_ea_005feeadminlink_005f1.setPageContext(_jspx_page_context);
/* 1044 */                           _jspx_th_ea_005feeadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                           
/* 1046 */                           _jspx_th_ea_005feeadminlink_005f1.setHref("/adminAction.do?method=showMailServerConfiguration");
/*      */                           
/* 1048 */                           _jspx_th_ea_005feeadminlink_005f1.setEnableClass("new-left-links");
/* 1049 */                           int _jspx_eval_ea_005feeadminlink_005f1 = _jspx_th_ea_005feeadminlink_005f1.doStartTag();
/* 1050 */                           if (_jspx_eval_ea_005feeadminlink_005f1 != 0) {
/* 1051 */                             if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/* 1052 */                               out = _jspx_page_context.pushBody();
/* 1053 */                               _jspx_th_ea_005feeadminlink_005f1.setBodyContent((BodyContent)out);
/* 1054 */                               _jspx_th_ea_005feeadminlink_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1057 */                               out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1058 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f1.doAfterBody();
/* 1059 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1062 */                             if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/* 1063 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1066 */                           if (_jspx_th_ea_005feeadminlink_005f1.doEndTag() == 5) {
/* 1067 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1); return;
/*      */                           }
/*      */                           
/* 1070 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1);
/* 1071 */                           out.write(10);
/* 1072 */                           out.write(32);
/* 1073 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1074 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1078 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1079 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/* 1082 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1083 */                       out.write(10);
/* 1084 */                       out.write(32);
/*      */                       
/* 1086 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1087 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1088 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 1089 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1090 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/* 1092 */                           out.write(10);
/* 1093 */                           out.write(9);
/* 1094 */                           out.write(32);
/* 1095 */                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1096 */                           out.write(10);
/* 1097 */                           out.write(32);
/* 1098 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1099 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1103 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1104 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/* 1107 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1108 */                       out.write(10);
/* 1109 */                       out.write(32);
/* 1110 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1111 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1115 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1116 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/* 1119 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1120 */                   out.write("\n    </td>\n</tr>\n\n");
/* 1121 */                   if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 1122 */                     out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 1124 */                     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1125 */                     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1126 */                     _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/* 1127 */                     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1128 */                     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                       for (;;) {
/* 1130 */                         out.write(10);
/*      */                         
/* 1132 */                         WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1133 */                         _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1134 */                         _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                         
/* 1136 */                         _jspx_th_c_005fwhen_005f2.setTest("${param.method!='SMSServerConfiguration'}");
/* 1137 */                         int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1138 */                         if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                           for (;;) {
/* 1140 */                             out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 1141 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1142 */                             out.write("\n    </a>\n ");
/* 1143 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1144 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1148 */                         if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1149 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                         }
/*      */                         
/* 1152 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1153 */                         out.write(10);
/* 1154 */                         out.write(32);
/*      */                         
/* 1156 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1157 */                         _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 1158 */                         _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 1159 */                         int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 1160 */                         if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                           for (;;) {
/* 1162 */                             out.write("\n         ");
/* 1163 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1164 */                             out.write(10);
/* 1165 */                             out.write(32);
/* 1166 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 1167 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1171 */                         if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 1172 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                         }
/*      */                         
/* 1175 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1176 */                         out.write(10);
/* 1177 */                         out.write(32);
/* 1178 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1179 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1183 */                     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1184 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                     }
/*      */                     
/* 1187 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1188 */                     out.write("\n    </td>\n</tr>\n");
/*      */                   }
/* 1190 */                   out.write("\n\n\n<tr>\n\n    <td class=\"leftlinkstd\">\n");
/*      */                   
/* 1192 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1193 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1194 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/* 1195 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1196 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/* 1198 */                       out.write(10);
/*      */                       
/* 1200 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1201 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1202 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/* 1204 */                       _jspx_th_c_005fwhen_005f3.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 1205 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1206 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/* 1208 */                           out.write("\n    ");
/*      */                           
/* 1210 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f2 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1211 */                           _jspx_th_ea_005feeadminlink_005f2.setPageContext(_jspx_page_context);
/* 1212 */                           _jspx_th_ea_005feeadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                           
/* 1214 */                           _jspx_th_ea_005feeadminlink_005f2.setHref("/jsp/ProxyConfiguration.jsp");
/*      */                           
/* 1216 */                           _jspx_th_ea_005feeadminlink_005f2.setEnableClass("new-left-links");
/* 1217 */                           int _jspx_eval_ea_005feeadminlink_005f2 = _jspx_th_ea_005feeadminlink_005f2.doStartTag();
/* 1218 */                           if (_jspx_eval_ea_005feeadminlink_005f2 != 0) {
/* 1219 */                             if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/* 1220 */                               out = _jspx_page_context.pushBody();
/* 1221 */                               _jspx_th_ea_005feeadminlink_005f2.setBodyContent((BodyContent)out);
/* 1222 */                               _jspx_th_ea_005feeadminlink_005f2.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1225 */                               out.write("\n    ");
/* 1226 */                               out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1227 */                               out.write("\n    ");
/* 1228 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f2.doAfterBody();
/* 1229 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1232 */                             if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/* 1233 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1236 */                           if (_jspx_th_ea_005feeadminlink_005f2.doEndTag() == 5) {
/* 1237 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2); return;
/*      */                           }
/*      */                           
/* 1240 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2);
/* 1241 */                           out.write(10);
/* 1242 */                           out.write(32);
/* 1243 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1244 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1248 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1249 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/* 1252 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1253 */                       out.write(10);
/* 1254 */                       out.write(32);
/*      */                       
/* 1256 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1257 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1258 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 1259 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1260 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/* 1262 */                           out.write(10);
/* 1263 */                           out.write(9);
/* 1264 */                           out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1265 */                           out.write(10);
/* 1266 */                           out.write(32);
/* 1267 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1268 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1272 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1273 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/* 1276 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1277 */                       out.write(10);
/* 1278 */                       out.write(32);
/* 1279 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1280 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1284 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1285 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/* 1288 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1289 */                   out.write("\n    </td>\n</tr>\n<tr>\n\n<td class=\"leftlinkstd\">\n");
/*      */                   
/* 1291 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1292 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1293 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/* 1294 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1295 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/* 1297 */                       out.write(10);
/*      */                       
/* 1299 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1300 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1301 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/* 1303 */                       _jspx_th_c_005fwhen_005f4.setTest("${uri !='/admin/userconfiguration.do'}");
/* 1304 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1305 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/* 1307 */                           out.write("\n\n        ");
/*      */                           
/* 1309 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f3 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1310 */                           _jspx_th_ea_005feeadminlink_005f3.setPageContext(_jspx_page_context);
/* 1311 */                           _jspx_th_ea_005feeadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                           
/* 1313 */                           _jspx_th_ea_005feeadminlink_005f3.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                           
/* 1315 */                           _jspx_th_ea_005feeadminlink_005f3.setEnableClass("new-left-links");
/* 1316 */                           int _jspx_eval_ea_005feeadminlink_005f3 = _jspx_th_ea_005feeadminlink_005f3.doStartTag();
/* 1317 */                           if (_jspx_eval_ea_005feeadminlink_005f3 != 0) {
/* 1318 */                             if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/* 1319 */                               out = _jspx_page_context.pushBody();
/* 1320 */                               _jspx_th_ea_005feeadminlink_005f3.setBodyContent((BodyContent)out);
/* 1321 */                               _jspx_th_ea_005feeadminlink_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1324 */                               out.write("\n       ");
/* 1325 */                               out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1326 */                               out.write("\n        ");
/* 1327 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f3.doAfterBody();
/* 1328 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1331 */                             if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/* 1332 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1335 */                           if (_jspx_th_ea_005feeadminlink_005f3.doEndTag() == 5) {
/* 1336 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3); return;
/*      */                           }
/*      */                           
/* 1339 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3);
/* 1340 */                           out.write(10);
/* 1341 */                           out.write(10);
/* 1342 */                           out.write(32);
/* 1343 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1344 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1348 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1349 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/* 1352 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1353 */                       out.write(10);
/* 1354 */                       out.write(32);
/*      */                       
/* 1356 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1357 */                       _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1358 */                       _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1359 */                       int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1360 */                       if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                         for (;;) {
/* 1362 */                           out.write(10);
/* 1363 */                           out.write(9);
/* 1364 */                           out.write(32);
/* 1365 */                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1366 */                           out.write(10);
/* 1367 */                           out.write(32);
/* 1368 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1369 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1373 */                       if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1374 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                       }
/*      */                       
/* 1377 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1378 */                       out.write(10);
/* 1379 */                       out.write(32);
/* 1380 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1381 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1385 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1386 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/* 1389 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1390 */                   out.write("\n</td>\n</tr>\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                   
/* 1392 */                   ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1393 */                   _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 1394 */                   _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/* 1395 */                   int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 1396 */                   if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                     for (;;) {
/* 1398 */                       out.write("\n   ");
/*      */                       
/* 1400 */                       WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1401 */                       _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1402 */                       _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                       
/* 1404 */                       _jspx_th_c_005fwhen_005f5.setTest("${param.method!='showManagedServers'}");
/* 1405 */                       int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1406 */                       if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                         for (;;) {
/* 1408 */                           out.write("\n    ");
/*      */                           
/* 1410 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f4 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1411 */                           _jspx_th_ea_005feeadminlink_005f4.setPageContext(_jspx_page_context);
/* 1412 */                           _jspx_th_ea_005feeadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                           
/* 1414 */                           _jspx_th_ea_005feeadminlink_005f4.setHref("/adminAction.do?method=showManagedServers");
/*      */                           
/* 1416 */                           _jspx_th_ea_005feeadminlink_005f4.setEnableClass("new-left-links");
/* 1417 */                           int _jspx_eval_ea_005feeadminlink_005f4 = _jspx_th_ea_005feeadminlink_005f4.doStartTag();
/* 1418 */                           if (_jspx_eval_ea_005feeadminlink_005f4 != 0) {
/* 1419 */                             if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/* 1420 */                               out = _jspx_page_context.pushBody();
/* 1421 */                               _jspx_th_ea_005feeadminlink_005f4.setBodyContent((BodyContent)out);
/* 1422 */                               _jspx_th_ea_005feeadminlink_005f4.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1425 */                               out.write("\n     ");
/* 1426 */                               out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 1427 */                               out.write(10);
/* 1428 */                               out.write(9);
/* 1429 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f4.doAfterBody();
/* 1430 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1433 */                             if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/* 1434 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1437 */                           if (_jspx_th_ea_005feeadminlink_005f4.doEndTag() == 5) {
/* 1438 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4); return;
/*      */                           }
/*      */                           
/* 1441 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4);
/* 1442 */                           out.write("\n   ");
/* 1443 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1444 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1448 */                       if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1449 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                       }
/*      */                       
/* 1452 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1453 */                       out.write("\n   ");
/*      */                       
/* 1455 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1456 */                       _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 1457 */                       _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 1458 */                       int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 1459 */                       if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                         for (;;) {
/* 1461 */                           out.write("\n     ");
/* 1462 */                           out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 1463 */                           out.write("\n   ");
/* 1464 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1465 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1469 */                       if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1470 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                       }
/*      */                       
/* 1473 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1474 */                       out.write("\n   ");
/* 1475 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 1476 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1480 */                   if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 1481 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                   }
/*      */                   
/* 1484 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1485 */                   out.write("\n  </td>\n</tr>\n\n\n<td class=\"leftlinkstd\" >\n");
/*      */                   
/* 1487 */                   ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1488 */                   _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 1489 */                   _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/* 1490 */                   int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 1491 */                   if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                     for (;;) {
/* 1493 */                       out.write(10);
/*      */                       
/* 1495 */                       WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1496 */                       _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 1497 */                       _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                       
/* 1499 */                       _jspx_th_c_005fwhen_005f6.setTest("${param.server!='admin'}");
/* 1500 */                       int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 1501 */                       if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                         for (;;) {
/* 1503 */                           out.write(10);
/*      */                           
/* 1505 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f5 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1506 */                           _jspx_th_ea_005feeadminlink_005f5.setPageContext(_jspx_page_context);
/* 1507 */                           _jspx_th_ea_005feeadminlink_005f5.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                           
/* 1509 */                           _jspx_th_ea_005feeadminlink_005f5.setHref("/adminAction.do?method=showActionProfiles");
/*      */                           
/* 1511 */                           _jspx_th_ea_005feeadminlink_005f5.setEnableClass("new-left-links");
/* 1512 */                           int _jspx_eval_ea_005feeadminlink_005f5 = _jspx_th_ea_005feeadminlink_005f5.doStartTag();
/* 1513 */                           if (_jspx_eval_ea_005feeadminlink_005f5 != 0) {
/* 1514 */                             if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/* 1515 */                               out = _jspx_page_context.pushBody();
/* 1516 */                               _jspx_th_ea_005feeadminlink_005f5.setBodyContent((BodyContent)out);
/* 1517 */                               _jspx_th_ea_005feeadminlink_005f5.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1520 */                               out.write(10);
/* 1521 */                               out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 1522 */                               out.write(10);
/* 1523 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f5.doAfterBody();
/* 1524 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1527 */                             if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/* 1528 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1531 */                           if (_jspx_th_ea_005feeadminlink_005f5.doEndTag() == 5) {
/* 1532 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5); return;
/*      */                           }
/*      */                           
/* 1535 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5);
/* 1536 */                           out.write(10);
/* 1537 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1538 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1542 */                       if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1543 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                       }
/*      */                       
/* 1546 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1547 */                       out.write(10);
/*      */                       
/* 1549 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1550 */                       _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 1551 */                       _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 1552 */                       int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 1553 */                       if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                         for (;;) {
/* 1555 */                           out.write(10);
/* 1556 */                           out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 1557 */                           out.write(10);
/* 1558 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1559 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1563 */                       if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1564 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                       }
/*      */                       
/* 1567 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1568 */                       out.write(10);
/* 1569 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 1570 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1574 */                   if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 1575 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                   }
/*      */                   
/* 1578 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 1579 */                   out.write("\n</td>\n</tr>\n");
/*      */                   
/* 1581 */                   if ((!usertype.equals("F")) || (Constants.isIt360))
/*      */                   {
/* 1583 */                     out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                     
/* 1585 */                     ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1586 */                     _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 1587 */                     _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/* 1588 */                     int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 1589 */                     if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                       for (;;) {
/* 1591 */                         out.write(10);
/* 1592 */                         out.write(32);
/* 1593 */                         out.write(32);
/*      */                         
/* 1595 */                         WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1596 */                         _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 1597 */                         _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                         
/* 1599 */                         _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/* 1600 */                         int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 1601 */                         if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                           for (;;) {
/* 1603 */                             out.write(10);
/* 1604 */                             out.write(32);
/* 1605 */                             out.write(32);
/* 1606 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1607 */                             out.write(10);
/* 1608 */                             out.write(32);
/* 1609 */                             out.write(32);
/* 1610 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1611 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1615 */                         if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1616 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                         }
/*      */                         
/* 1619 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1620 */                         out.write(10);
/* 1621 */                         out.write(32);
/*      */                         
/* 1623 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1624 */                         _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 1625 */                         _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 1626 */                         int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 1627 */                         if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                           for (;;) {
/* 1629 */                             out.write("\n   ");
/*      */                             
/* 1631 */                             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f6 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1632 */                             _jspx_th_ea_005feeadminlink_005f6.setPageContext(_jspx_page_context);
/* 1633 */                             _jspx_th_ea_005feeadminlink_005f6.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                             
/* 1635 */                             _jspx_th_ea_005feeadminlink_005f6.setHref("/scheduleReports.do?method=showScheduleReports");
/*      */                             
/* 1637 */                             _jspx_th_ea_005feeadminlink_005f6.setEnableClass("new-left-links");
/* 1638 */                             int _jspx_eval_ea_005feeadminlink_005f6 = _jspx_th_ea_005feeadminlink_005f6.doStartTag();
/* 1639 */                             if (_jspx_eval_ea_005feeadminlink_005f6 != 0) {
/* 1640 */                               if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 1641 */                                 out = _jspx_page_context.pushBody();
/* 1642 */                                 _jspx_th_ea_005feeadminlink_005f6.setBodyContent((BodyContent)out);
/* 1643 */                                 _jspx_th_ea_005feeadminlink_005f6.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1646 */                                 out.write("\n   ");
/* 1647 */                                 out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1648 */                                 out.write(10);
/* 1649 */                                 out.write(32);
/* 1650 */                                 out.write(32);
/* 1651 */                                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f6.doAfterBody();
/* 1652 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1655 */                               if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 1656 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1659 */                             if (_jspx_th_ea_005feeadminlink_005f6.doEndTag() == 5) {
/* 1660 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6); return;
/*      */                             }
/*      */                             
/* 1663 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6);
/* 1664 */                             out.write(10);
/* 1665 */                             out.write(32);
/* 1666 */                             out.write(32);
/* 1667 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 1668 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1672 */                         if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 1673 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                         }
/*      */                         
/* 1676 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 1677 */                         out.write(10);
/* 1678 */                         out.write(32);
/* 1679 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 1680 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1684 */                     if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 1685 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                     }
/*      */                     
/* 1688 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 1689 */                     out.write("\n</td>\n</tr>\n");
/*      */                   } else {
/* 1691 */                     out.write("\n<tr>\n    <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 1692 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1693 */                     out.write("\n    </td>\n</tr>\n");
/*      */                   }
/* 1695 */                   out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                   
/* 1697 */                   ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1698 */                   _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 1699 */                   _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/* 1700 */                   int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 1701 */                   if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                     for (;;) {
/* 1703 */                       out.write(10);
/* 1704 */                       out.write(32);
/* 1705 */                       out.write(32);
/*      */                       
/* 1707 */                       WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1708 */                       _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 1709 */                       _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                       
/* 1711 */                       _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showDataCleanUp'}");
/* 1712 */                       int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 1713 */                       if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                         for (;;) {
/* 1715 */                           out.write(10);
/* 1716 */                           out.write(32);
/* 1717 */                           out.write(32);
/* 1718 */                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1719 */                           out.write(10);
/* 1720 */                           out.write(32);
/* 1721 */                           out.write(32);
/* 1722 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 1723 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1727 */                       if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 1728 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                       }
/*      */                       
/* 1731 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 1732 */                       out.write(10);
/* 1733 */                       out.write(32);
/*      */                       
/* 1735 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1736 */                       _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 1737 */                       _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 1738 */                       int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 1739 */                       if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                         for (;;) {
/* 1741 */                           out.write("\n   ");
/*      */                           
/* 1743 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f7 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1744 */                           _jspx_th_ea_005feeadminlink_005f7.setPageContext(_jspx_page_context);
/* 1745 */                           _jspx_th_ea_005feeadminlink_005f7.setParent(_jspx_th_c_005fotherwise_005f8);
/*      */                           
/* 1747 */                           _jspx_th_ea_005feeadminlink_005f7.setHref("/adminAction.do?method=showDataCleanUp");
/*      */                           
/* 1749 */                           _jspx_th_ea_005feeadminlink_005f7.setEnableClass("new-left-links");
/* 1750 */                           int _jspx_eval_ea_005feeadminlink_005f7 = _jspx_th_ea_005feeadminlink_005f7.doStartTag();
/* 1751 */                           if (_jspx_eval_ea_005feeadminlink_005f7 != 0) {
/* 1752 */                             if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 1753 */                               out = _jspx_page_context.pushBody();
/* 1754 */                               _jspx_th_ea_005feeadminlink_005f7.setBodyContent((BodyContent)out);
/* 1755 */                               _jspx_th_ea_005feeadminlink_005f7.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1758 */                               out.write("\n   ");
/* 1759 */                               out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1760 */                               out.write(10);
/* 1761 */                               out.write(32);
/* 1762 */                               out.write(32);
/* 1763 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f7.doAfterBody();
/* 1764 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1767 */                             if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 1768 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1771 */                           if (_jspx_th_ea_005feeadminlink_005f7.doEndTag() == 5) {
/* 1772 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7); return;
/*      */                           }
/*      */                           
/* 1775 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7);
/* 1776 */                           out.write(10);
/* 1777 */                           out.write(32);
/* 1778 */                           out.write(32);
/* 1779 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 1780 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1784 */                       if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 1785 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                       }
/*      */                       
/* 1788 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 1789 */                       out.write(10);
/* 1790 */                       out.write(32);
/* 1791 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 1792 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1796 */                   if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 1797 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                   }
/*      */                   
/* 1800 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 1801 */                   out.write("\n</td>\n</tr>\n</table>\n\n");
/* 1802 */                   out.write(10);
/* 1803 */                   out.write(9);
/*      */                 }
/*      */                 else
/*      */                 {
/* 1807 */                   out.write(10);
/* 1808 */                   out.write(9);
/* 1809 */                   out.write(9);
/* 1810 */                   out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                   
/*      */ 
/* 1813 */                   String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                   
/* 1815 */                   out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 1816 */                   out.print(FormatUtil.getString("wizard.disabled"));
/* 1817 */                   out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 1818 */                   out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 1819 */                   out.write("</td>\n  </tr>\n  \n ");
/*      */                   
/* 1821 */                   if (!Constants.sqlManager)
/*      */                   {
/*      */ 
/* 1824 */                     out.write("  \n  <tr>\n\n  ");
/*      */                     
/* 1826 */                     if (request.getParameter("wiz") != null)
/*      */                     {
/* 1828 */                       out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 1829 */                       out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1830 */                       out.write("\" class='disabledlink'>");
/* 1831 */                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1832 */                       out.write("</a></td>\n  ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 1836 */                       out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                       
/* 1838 */                       ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1839 */                       _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 1840 */                       _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 1841 */                       int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 1842 */                       if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                         for (;;) {
/* 1844 */                           out.write(10);
/*      */                           
/* 1846 */                           WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1847 */                           _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1848 */                           _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                           
/* 1850 */                           _jspx_th_c_005fwhen_005f9.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 1851 */                           int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1852 */                           if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                             for (;;) {
/* 1854 */                               out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 1855 */                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1856 */                               out.write("\n    </a>\n ");
/* 1857 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1858 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1862 */                           if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1863 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                           }
/*      */                           
/* 1866 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1867 */                           out.write(10);
/* 1868 */                           out.write(32);
/*      */                           
/* 1870 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1871 */                           _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1872 */                           _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1873 */                           int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1874 */                           if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                             for (;;) {
/* 1876 */                               out.write(10);
/* 1877 */                               out.write(9);
/* 1878 */                               out.write(32);
/* 1879 */                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1880 */                               out.write(10);
/* 1881 */                               out.write(32);
/* 1882 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1883 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1887 */                           if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1888 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                           }
/*      */                           
/* 1891 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1892 */                           out.write(10);
/* 1893 */                           out.write(32);
/* 1894 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1895 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1899 */                       if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1900 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                       }
/*      */                       
/* 1903 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1904 */                       out.write("\n    </td>\n\t");
/*      */                     }
/* 1906 */                     out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                     
/* 1908 */                     if (request.getParameter("wiz") != null)
/*      */                     {
/* 1910 */                       out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 1911 */                       out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1912 */                       out.write("\" class='disabledlink'>");
/* 1913 */                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1914 */                       out.write("</a></td>\n   ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 1918 */                       out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                       
/* 1920 */                       ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1921 */                       _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 1922 */                       _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 1923 */                       int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 1924 */                       if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                         for (;;) {
/* 1926 */                           out.write(10);
/*      */                           
/* 1928 */                           WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1929 */                           _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1930 */                           _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                           
/* 1932 */                           _jspx_th_c_005fwhen_005f10.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 1933 */                           int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1934 */                           if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                             for (;;) {
/* 1936 */                               out.write("\n   ");
/* 1937 */                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1938 */                               out.write(10);
/* 1939 */                               out.write(32);
/* 1940 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1941 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1945 */                           if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1946 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                           }
/*      */                           
/* 1949 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1950 */                           out.write(10);
/* 1951 */                           out.write(32);
/*      */                           
/* 1953 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1954 */                           _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 1955 */                           _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 1956 */                           int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 1957 */                           if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                             for (;;) {
/* 1959 */                               out.write(10);
/* 1960 */                               String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 1961 */                               out.write("\n\t \n <a href=\"");
/* 1962 */                               out.print(link);
/* 1963 */                               out.write("\" class=\"new-left-links\">\n               ");
/* 1964 */                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1965 */                               out.write("\n    </a>    \n ");
/* 1966 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 1967 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1971 */                           if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 1972 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                           }
/*      */                           
/* 1975 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 1976 */                           out.write(10);
/* 1977 */                           out.write(32);
/* 1978 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 1979 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1983 */                       if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 1984 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                       }
/*      */                       
/* 1987 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 1988 */                       out.write("\n</td>\n");
/*      */                     }
/* 1990 */                     out.write("\n</tr>\n\n ");
/*      */                   }
/*      */                   
/*      */ 
/* 1994 */                   out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/* 1996 */                   ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1997 */                   _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 1998 */                   _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 1999 */                   int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 2000 */                   if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                     for (;;) {
/* 2002 */                       out.write(10);
/*      */                       
/* 2004 */                       WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2005 */                       _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 2006 */                       _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                       
/* 2008 */                       _jspx_th_c_005fwhen_005f11.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 2009 */                       int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 2010 */                       if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                         for (;;) {
/* 2012 */                           out.write("\n    \n       ");
/* 2013 */                           out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2014 */                           out.write(10);
/* 2015 */                           out.write(32);
/* 2016 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 2017 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2021 */                       if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 2022 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                       }
/*      */                       
/* 2025 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 2026 */                       out.write(10);
/* 2027 */                       out.write(32);
/*      */                       
/* 2029 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2030 */                       _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 2031 */                       _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 2032 */                       int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 2033 */                       if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                         for (;;) {
/* 2035 */                           out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 2036 */                           out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2037 */                           out.write("\n    </a>\n ");
/* 2038 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 2039 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2043 */                       if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 2044 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                       }
/*      */                       
/* 2047 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 2048 */                       out.write(10);
/* 2049 */                       out.write(32);
/* 2050 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 2051 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2055 */                   if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 2056 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                   }
/*      */                   
/* 2059 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 2060 */                   out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/* 2062 */                   ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2063 */                   _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 2064 */                   _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 2065 */                   int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 2066 */                   if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                     for (;;) {
/* 2068 */                       out.write(10);
/*      */                       
/* 2070 */                       WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2071 */                       _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 2072 */                       _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                       
/* 2074 */                       _jspx_th_c_005fwhen_005f12.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 2075 */                       int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 2076 */                       if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                         for (;;) {
/* 2078 */                           out.write("\n    \n       ");
/* 2079 */                           out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2080 */                           out.write(10);
/* 2081 */                           out.write(32);
/* 2082 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 2083 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2087 */                       if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 2088 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                       }
/*      */                       
/* 2091 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 2092 */                       out.write(10);
/* 2093 */                       out.write(32);
/*      */                       
/* 2095 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2096 */                       _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 2097 */                       _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 2098 */                       int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 2099 */                       if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                         for (;;) {
/* 2101 */                           out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 2102 */                           out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2103 */                           out.write("\n\t </a>\n ");
/* 2104 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 2105 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2109 */                       if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 2110 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                       }
/*      */                       
/* 2113 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 2114 */                       out.write(10);
/* 2115 */                       out.write(32);
/* 2116 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 2117 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2121 */                   if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 2122 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                   }
/*      */                   
/* 2125 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 2126 */                   out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                   
/* 2128 */                   if (!Constants.sqlManager)
/*      */                   {
/*      */ 
/* 2131 */                     out.write(32);
/* 2132 */                     out.write(32);
/* 2133 */                     out.write(10);
/*      */                     
/* 2135 */                     ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2136 */                     _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 2137 */                     _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 2138 */                     int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 2139 */                     if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                       for (;;) {
/* 2141 */                         out.write(10);
/*      */                         
/* 2143 */                         WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2144 */                         _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 2145 */                         _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                         
/* 2147 */                         _jspx_th_c_005fwhen_005f13.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 2148 */                         int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 2149 */                         if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                           for (;;) {
/* 2151 */                             out.write("\n<tr>\n    ");
/* 2152 */                             if (!request.isUserInRole("OPERATOR")) {
/* 2153 */                               out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 2154 */                               out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2155 */                               out.write("\n    </a>\n        </td>\n     ");
/*      */                             } else {
/* 2157 */                               out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 2158 */                               out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2159 */                               out.write("\n\t</a>\n\t </td>\n\t");
/*      */                             }
/* 2161 */                             out.write("\n    </tr>\n ");
/* 2162 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 2163 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2167 */                         if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 2168 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                         }
/*      */                         
/* 2171 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 2172 */                         out.write(10);
/* 2173 */                         out.write(32);
/*      */                         
/* 2175 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2176 */                         _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 2177 */                         _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 2178 */                         int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 2179 */                         if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                           for (;;) {
/* 2181 */                             out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 2182 */                             out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2183 */                             out.write("\n\t </td>\n ");
/* 2184 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 2185 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2189 */                         if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 2190 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                         }
/*      */                         
/* 2193 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 2194 */                         out.write(10);
/* 2195 */                         out.write(32);
/* 2196 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 2197 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2201 */                     if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 2202 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                     }
/*      */                     
/* 2205 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 2206 */                     out.write("\n \n  ");
/*      */                   }
/*      */                   
/*      */ 
/* 2210 */                   out.write("  \n \n ");
/*      */                   
/* 2212 */                   if (!usertype.equals("F"))
/*      */                   {
/* 2214 */                     out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                     
/* 2216 */                     ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2217 */                     _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 2218 */                     _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 2219 */                     int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 2220 */                     if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                       for (;;) {
/* 2222 */                         out.write(10);
/* 2223 */                         out.write(9);
/*      */                         
/* 2225 */                         WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2226 */                         _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 2227 */                         _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                         
/* 2229 */                         _jspx_th_c_005fwhen_005f14.setTest("${param.method !='maintenanceTaskListView'}");
/* 2230 */                         int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 2231 */                         if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                           for (;;) {
/* 2233 */                             out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 2234 */                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 2235 */                             out.write("</a>\n  \t");
/* 2236 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 2237 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2241 */                         if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 2242 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                         }
/*      */                         
/* 2245 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 2246 */                         out.write("\n  \t");
/*      */                         
/* 2248 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2249 */                         _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 2250 */                         _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 2251 */                         int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 2252 */                         if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                           for (;;) {
/* 2254 */                             out.write("\n \t\t");
/* 2255 */                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 2256 */                             out.write("\n  \t");
/* 2257 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 2258 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2262 */                         if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 2263 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                         }
/*      */                         
/* 2266 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 2267 */                         out.write("\n  \t");
/* 2268 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 2269 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2273 */                     if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 2274 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                     }
/*      */                     
/* 2277 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 2278 */                     out.write("\n     </td>\n </tr>   \n \n ");
/*      */                     
/* 2280 */                     if (!Constants.sqlManager)
/*      */                     {
/*      */ 
/* 2283 */                       out.write(32);
/* 2284 */                       out.write(32);
/* 2285 */                       out.write(10);
/*      */                       
/* 2287 */                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2288 */                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2289 */                       _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                       
/* 2291 */                       _jspx_th_c_005fif_005f3.setTest("${category!='LAMP'}");
/* 2292 */                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2293 */                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                         for (;;) {
/* 2295 */                           out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                           
/* 2297 */                           ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2298 */                           _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 2299 */                           _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_c_005fif_005f3);
/* 2300 */                           int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 2301 */                           if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                             for (;;) {
/* 2303 */                               out.write(10);
/* 2304 */                               out.write(32);
/* 2305 */                               out.write(9);
/*      */                               
/* 2307 */                               WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2308 */                               _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 2309 */                               _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                               
/* 2311 */                               _jspx_th_c_005fwhen_005f15.setTest("${param.method !='listTrapListener'}");
/* 2312 */                               int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 2313 */                               if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                 for (;;) {
/* 2315 */                                   out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 2316 */                                   out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 2317 */                                   out.write("</a>\n   \t");
/* 2318 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 2319 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2323 */                               if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 2324 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                               }
/*      */                               
/* 2327 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 2328 */                               out.write("\n   \t");
/*      */                               
/* 2330 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2331 */                               _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 2332 */                               _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 2333 */                               int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 2334 */                               if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                 for (;;) {
/* 2336 */                                   out.write("\n  \t\t");
/* 2337 */                                   out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 2338 */                                   out.write(" \n   \t");
/* 2339 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 2340 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2344 */                               if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 2345 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                               }
/*      */                               
/* 2348 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 2349 */                               out.write("\n   \t");
/* 2350 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 2351 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2355 */                           if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 2356 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                           }
/*      */                           
/* 2359 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 2360 */                           out.write("\n      </td>\n  </tr>   \n");
/* 2361 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2362 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2366 */                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2367 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                       }
/*      */                       
/* 2370 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2371 */                       out.write(10);
/* 2372 */                       out.write(32);
/*      */                     }
/*      */                     
/*      */ 
/* 2376 */                     out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 2378 */                     ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2379 */                     _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 2380 */                     _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_tiles_005fput_005f2);
/* 2381 */                     int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 2382 */                     if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                       for (;;) {
/* 2384 */                         out.write(10);
/*      */                         
/* 2386 */                         WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2387 */                         _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 2388 */                         _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */                         
/* 2390 */                         _jspx_th_c_005fwhen_005f16.setTest("${param.method =='showScheduleReports'}");
/* 2391 */                         int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 2392 */                         if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                           for (;;) {
/* 2394 */                             out.write("\n       ");
/* 2395 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 2396 */                             out.write(10);
/* 2397 */                             out.write(32);
/* 2398 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 2399 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2403 */                         if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 2404 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */                         }
/*      */                         
/* 2407 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 2408 */                         out.write(10);
/* 2409 */                         out.write(32);
/*      */                         
/* 2411 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2412 */                         _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 2413 */                         _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/* 2414 */                         int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 2415 */                         if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                           for (;;) {
/* 2417 */                             out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 2418 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 2419 */                             out.write("\n\t </a>\n ");
/* 2420 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 2421 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2425 */                         if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 2426 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */                         }
/*      */                         
/* 2429 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 2430 */                         out.write(10);
/* 2431 */                         out.write(32);
/* 2432 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 2433 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2437 */                     if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 2438 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */                     }
/*      */                     
/* 2441 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 2442 */                     out.write("\n    </td>\n</tr> \n");
/*      */                   } else {
/* 2444 */                     out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 2445 */                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 2446 */                     out.write("</a>\n     </td>\n </tr>   \n");
/*      */                     
/* 2448 */                     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2449 */                     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2450 */                     _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                     
/* 2452 */                     _jspx_th_c_005fif_005f4.setTest("${category!='LAMP'}");
/* 2453 */                     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2454 */                     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                       for (;;) {
/* 2456 */                         out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 2457 */                         out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 2458 */                         out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 2459 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2460 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2464 */                     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2465 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                     }
/*      */                     
/* 2468 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2469 */                     out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 2470 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 2471 */                     out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                   }
/* 2473 */                   out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/* 2475 */                   ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2476 */                   _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 2477 */                   _jspx_th_c_005fchoose_005f17.setParent(_jspx_th_tiles_005fput_005f2);
/* 2478 */                   int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 2479 */                   if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */                     for (;;) {
/* 2481 */                       out.write(10);
/*      */                       
/* 2483 */                       WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2484 */                       _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 2485 */                       _jspx_th_c_005fwhen_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/*      */                       
/* 2487 */                       _jspx_th_c_005fwhen_005f17.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 2488 */                       int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 2489 */                       if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */                         for (;;) {
/* 2491 */                           out.write("\n        ");
/* 2492 */                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 2493 */                           out.write(10);
/* 2494 */                           out.write(32);
/* 2495 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 2496 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2500 */                       if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 2501 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17); return;
/*      */                       }
/*      */                       
/* 2504 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 2505 */                       out.write(10);
/* 2506 */                       out.write(32);
/*      */                       
/* 2508 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2509 */                       _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 2510 */                       _jspx_th_c_005fotherwise_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/* 2511 */                       int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 2512 */                       if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */                         for (;;) {
/* 2514 */                           out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 2515 */                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 2516 */                           out.write("\n\t </a>\n ");
/* 2517 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 2518 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2522 */                       if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 2523 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17); return;
/*      */                       }
/*      */                       
/* 2526 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 2527 */                       out.write(10);
/* 2528 */                       out.write(32);
/* 2529 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 2530 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2534 */                   if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 2535 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17); return;
/*      */                   }
/*      */                   
/* 2538 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 2539 */                   out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/* 2541 */                   ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2542 */                   _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 2543 */                   _jspx_th_c_005fchoose_005f18.setParent(_jspx_th_tiles_005fput_005f2);
/* 2544 */                   int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 2545 */                   if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */                     for (;;) {
/* 2547 */                       out.write(10);
/*      */                       
/* 2549 */                       WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2550 */                       _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 2551 */                       _jspx_th_c_005fwhen_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/*      */                       
/* 2553 */                       _jspx_th_c_005fwhen_005f18.setTest("${param.method!='showMailServerConfiguration'}");
/* 2554 */                       int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 2555 */                       if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */                         for (;;) {
/* 2557 */                           out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 2558 */                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 2559 */                           out.write("\n    </a>    \n ");
/* 2560 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 2561 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2565 */                       if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 2566 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18); return;
/*      */                       }
/*      */                       
/* 2569 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 2570 */                       out.write(10);
/* 2571 */                       out.write(32);
/*      */                       
/* 2573 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2574 */                       _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 2575 */                       _jspx_th_c_005fotherwise_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/* 2576 */                       int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 2577 */                       if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */                         for (;;) {
/* 2579 */                           out.write(10);
/* 2580 */                           out.write(9);
/* 2581 */                           out.write(32);
/* 2582 */                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 2583 */                           out.write(10);
/* 2584 */                           out.write(32);
/* 2585 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 2586 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2590 */                       if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 2591 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18); return;
/*      */                       }
/*      */                       
/* 2594 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 2595 */                       out.write(10);
/* 2596 */                       out.write(32);
/* 2597 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 2598 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2602 */                   if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 2603 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18); return;
/*      */                   }
/*      */                   
/* 2606 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 2607 */                   out.write("\n    </td>\n</tr>\n\n\n");
/* 2608 */                   if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 2609 */                     out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 2611 */                     ChooseTag _jspx_th_c_005fchoose_005f19 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2612 */                     _jspx_th_c_005fchoose_005f19.setPageContext(_jspx_page_context);
/* 2613 */                     _jspx_th_c_005fchoose_005f19.setParent(_jspx_th_tiles_005fput_005f2);
/* 2614 */                     int _jspx_eval_c_005fchoose_005f19 = _jspx_th_c_005fchoose_005f19.doStartTag();
/* 2615 */                     if (_jspx_eval_c_005fchoose_005f19 != 0) {
/*      */                       for (;;) {
/* 2617 */                         out.write(10);
/*      */                         
/* 2619 */                         WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2620 */                         _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 2621 */                         _jspx_th_c_005fwhen_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/*      */                         
/* 2623 */                         _jspx_th_c_005fwhen_005f19.setTest("${param.method!='SMSServerConfiguration'}");
/* 2624 */                         int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 2625 */                         if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */                           for (;;) {
/* 2627 */                             out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 2628 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2629 */                             out.write("\n    </a>\n ");
/* 2630 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 2631 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2635 */                         if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 2636 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19); return;
/*      */                         }
/*      */                         
/* 2639 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 2640 */                         out.write(10);
/* 2641 */                         out.write(32);
/*      */                         
/* 2643 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f19 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2644 */                         _jspx_th_c_005fotherwise_005f19.setPageContext(_jspx_page_context);
/* 2645 */                         _jspx_th_c_005fotherwise_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/* 2646 */                         int _jspx_eval_c_005fotherwise_005f19 = _jspx_th_c_005fotherwise_005f19.doStartTag();
/* 2647 */                         if (_jspx_eval_c_005fotherwise_005f19 != 0) {
/*      */                           for (;;) {
/* 2649 */                             out.write("\n         ");
/* 2650 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2651 */                             out.write(10);
/* 2652 */                             out.write(32);
/* 2653 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f19.doAfterBody();
/* 2654 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2658 */                         if (_jspx_th_c_005fotherwise_005f19.doEndTag() == 5) {
/* 2659 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19); return;
/*      */                         }
/*      */                         
/* 2662 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 2663 */                         out.write(10);
/* 2664 */                         out.write(32);
/* 2665 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f19.doAfterBody();
/* 2666 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2670 */                     if (_jspx_th_c_005fchoose_005f19.doEndTag() == 5) {
/* 2671 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19); return;
/*      */                     }
/*      */                     
/* 2674 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 2675 */                     out.write("\n    </td>\n</tr>\n");
/*      */                   }
/* 2677 */                   out.write("\n\n\n ");
/*      */                   
/* 2679 */                   if (!Constants.sqlManager)
/*      */                   {
/*      */ 
/* 2682 */                     out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                     
/* 2684 */                     ChooseTag _jspx_th_c_005fchoose_005f20 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2685 */                     _jspx_th_c_005fchoose_005f20.setPageContext(_jspx_page_context);
/* 2686 */                     _jspx_th_c_005fchoose_005f20.setParent(_jspx_th_tiles_005fput_005f2);
/* 2687 */                     int _jspx_eval_c_005fchoose_005f20 = _jspx_th_c_005fchoose_005f20.doStartTag();
/* 2688 */                     if (_jspx_eval_c_005fchoose_005f20 != 0) {
/*      */                       for (;;) {
/* 2690 */                         out.write(10);
/*      */                         
/* 2692 */                         WhenTag _jspx_th_c_005fwhen_005f20 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2693 */                         _jspx_th_c_005fwhen_005f20.setPageContext(_jspx_page_context);
/* 2694 */                         _jspx_th_c_005fwhen_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/*      */                         
/* 2696 */                         _jspx_th_c_005fwhen_005f20.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 2697 */                         int _jspx_eval_c_005fwhen_005f20 = _jspx_th_c_005fwhen_005f20.doStartTag();
/* 2698 */                         if (_jspx_eval_c_005fwhen_005f20 != 0) {
/*      */                           for (;;) {
/* 2700 */                             out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 2701 */                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2702 */                             out.write("\n    </a>\n ");
/* 2703 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f20.doAfterBody();
/* 2704 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2708 */                         if (_jspx_th_c_005fwhen_005f20.doEndTag() == 5) {
/* 2709 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20); return;
/*      */                         }
/*      */                         
/* 2712 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 2713 */                         out.write(10);
/* 2714 */                         out.write(32);
/*      */                         
/* 2716 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f20 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2717 */                         _jspx_th_c_005fotherwise_005f20.setPageContext(_jspx_page_context);
/* 2718 */                         _jspx_th_c_005fotherwise_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/* 2719 */                         int _jspx_eval_c_005fotherwise_005f20 = _jspx_th_c_005fotherwise_005f20.doStartTag();
/* 2720 */                         if (_jspx_eval_c_005fotherwise_005f20 != 0) {
/*      */                           for (;;) {
/* 2722 */                             out.write(10);
/* 2723 */                             out.write(9);
/* 2724 */                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2725 */                             out.write(10);
/* 2726 */                             out.write(32);
/* 2727 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f20.doAfterBody();
/* 2728 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2732 */                         if (_jspx_th_c_005fotherwise_005f20.doEndTag() == 5) {
/* 2733 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20); return;
/*      */                         }
/*      */                         
/* 2736 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20);
/* 2737 */                         out.write(10);
/* 2738 */                         out.write(32);
/* 2739 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f20.doAfterBody();
/* 2740 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2744 */                     if (_jspx_th_c_005fchoose_005f20.doEndTag() == 5) {
/* 2745 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20); return;
/*      */                     }
/*      */                     
/* 2748 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20);
/* 2749 */                     out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 2751 */                     ChooseTag _jspx_th_c_005fchoose_005f21 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2752 */                     _jspx_th_c_005fchoose_005f21.setPageContext(_jspx_page_context);
/* 2753 */                     _jspx_th_c_005fchoose_005f21.setParent(_jspx_th_tiles_005fput_005f2);
/* 2754 */                     int _jspx_eval_c_005fchoose_005f21 = _jspx_th_c_005fchoose_005f21.doStartTag();
/* 2755 */                     if (_jspx_eval_c_005fchoose_005f21 != 0) {
/*      */                       for (;;) {
/* 2757 */                         out.write(10);
/*      */                         
/* 2759 */                         WhenTag _jspx_th_c_005fwhen_005f21 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2760 */                         _jspx_th_c_005fwhen_005f21.setPageContext(_jspx_page_context);
/* 2761 */                         _jspx_th_c_005fwhen_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/*      */                         
/* 2763 */                         _jspx_th_c_005fwhen_005f21.setTest("${uri !='/Upload.do'}");
/* 2764 */                         int _jspx_eval_c_005fwhen_005f21 = _jspx_th_c_005fwhen_005f21.doStartTag();
/* 2765 */                         if (_jspx_eval_c_005fwhen_005f21 != 0) {
/*      */                           for (;;) {
/* 2767 */                             out.write("   \n        ");
/*      */                             
/* 2769 */                             AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2770 */                             _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 2771 */                             _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f21);
/*      */                             
/* 2773 */                             _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                             
/* 2775 */                             _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 2776 */                             int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 2777 */                             if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 2778 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2779 */                                 out = _jspx_page_context.pushBody();
/* 2780 */                                 _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 2781 */                                 _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2784 */                                 out.write("\n           ");
/* 2785 */                                 out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 2786 */                                 out.write("\n            ");
/* 2787 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 2788 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2791 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2792 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2795 */                             if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 2796 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                             }
/*      */                             
/* 2799 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 2800 */                             out.write(10);
/* 2801 */                             out.write(10);
/* 2802 */                             out.write(32);
/* 2803 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f21.doAfterBody();
/* 2804 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2808 */                         if (_jspx_th_c_005fwhen_005f21.doEndTag() == 5) {
/* 2809 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21); return;
/*      */                         }
/*      */                         
/* 2812 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21);
/* 2813 */                         out.write(10);
/* 2814 */                         out.write(32);
/*      */                         
/* 2816 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f21 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2817 */                         _jspx_th_c_005fotherwise_005f21.setPageContext(_jspx_page_context);
/* 2818 */                         _jspx_th_c_005fotherwise_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/* 2819 */                         int _jspx_eval_c_005fotherwise_005f21 = _jspx_th_c_005fotherwise_005f21.doStartTag();
/* 2820 */                         if (_jspx_eval_c_005fotherwise_005f21 != 0) {
/*      */                           for (;;) {
/* 2822 */                             out.write(10);
/* 2823 */                             out.write(9);
/* 2824 */                             out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 2825 */                             out.write(10);
/* 2826 */                             out.write(32);
/* 2827 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f21.doAfterBody();
/* 2828 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2832 */                         if (_jspx_th_c_005fotherwise_005f21.doEndTag() == 5) {
/* 2833 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21); return;
/*      */                         }
/*      */                         
/* 2836 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21);
/* 2837 */                         out.write(10);
/* 2838 */                         out.write(32);
/* 2839 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f21.doAfterBody();
/* 2840 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2844 */                     if (_jspx_th_c_005fchoose_005f21.doEndTag() == 5) {
/* 2845 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21); return;
/*      */                     }
/*      */                     
/* 2848 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21);
/* 2849 */                     out.write("\n    </td>\n</tr>\n \n ");
/*      */                   }
/*      */                   
/*      */ 
/* 2853 */                   out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                   
/* 2855 */                   ChooseTag _jspx_th_c_005fchoose_005f22 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2856 */                   _jspx_th_c_005fchoose_005f22.setPageContext(_jspx_page_context);
/* 2857 */                   _jspx_th_c_005fchoose_005f22.setParent(_jspx_th_tiles_005fput_005f2);
/* 2858 */                   int _jspx_eval_c_005fchoose_005f22 = _jspx_th_c_005fchoose_005f22.doStartTag();
/* 2859 */                   if (_jspx_eval_c_005fchoose_005f22 != 0) {
/*      */                     for (;;) {
/* 2861 */                       out.write(10);
/*      */                       
/* 2863 */                       WhenTag _jspx_th_c_005fwhen_005f22 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2864 */                       _jspx_th_c_005fwhen_005f22.setPageContext(_jspx_page_context);
/* 2865 */                       _jspx_th_c_005fwhen_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/*      */                       
/* 2867 */                       _jspx_th_c_005fwhen_005f22.setTest("${uri !='/admin/userconfiguration.do'}");
/* 2868 */                       int _jspx_eval_c_005fwhen_005f22 = _jspx_th_c_005fwhen_005f22.doStartTag();
/* 2869 */                       if (_jspx_eval_c_005fwhen_005f22 != 0) {
/*      */                         for (;;) {
/* 2871 */                           out.write("\n    \n        ");
/*      */                           
/* 2873 */                           AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2874 */                           _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 2875 */                           _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f22);
/*      */                           
/* 2877 */                           _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                           
/* 2879 */                           _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 2880 */                           int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 2881 */                           if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 2882 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2883 */                               out = _jspx_page_context.pushBody();
/* 2884 */                               _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 2885 */                               _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2888 */                               out.write("\n       ");
/* 2889 */                               out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2890 */                               out.write("\n        ");
/* 2891 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 2892 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2895 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2896 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2899 */                           if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 2900 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                           }
/*      */                           
/* 2903 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 2904 */                           out.write(10);
/* 2905 */                           out.write(10);
/* 2906 */                           out.write(32);
/* 2907 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f22.doAfterBody();
/* 2908 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2912 */                       if (_jspx_th_c_005fwhen_005f22.doEndTag() == 5) {
/* 2913 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22); return;
/*      */                       }
/*      */                       
/* 2916 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22);
/* 2917 */                       out.write(10);
/* 2918 */                       out.write(32);
/*      */                       
/* 2920 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f22 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2921 */                       _jspx_th_c_005fotherwise_005f22.setPageContext(_jspx_page_context);
/* 2922 */                       _jspx_th_c_005fotherwise_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/* 2923 */                       int _jspx_eval_c_005fotherwise_005f22 = _jspx_th_c_005fotherwise_005f22.doStartTag();
/* 2924 */                       if (_jspx_eval_c_005fotherwise_005f22 != 0) {
/*      */                         for (;;) {
/* 2926 */                           out.write(10);
/* 2927 */                           out.write(9);
/* 2928 */                           out.write(32);
/* 2929 */                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2930 */                           out.write(10);
/* 2931 */                           out.write(32);
/* 2932 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f22.doAfterBody();
/* 2933 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2937 */                       if (_jspx_th_c_005fotherwise_005f22.doEndTag() == 5) {
/* 2938 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22); return;
/*      */                       }
/*      */                       
/* 2941 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22);
/* 2942 */                       out.write(10);
/* 2943 */                       out.write(32);
/* 2944 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f22.doAfterBody();
/* 2945 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2949 */                   if (_jspx_th_c_005fchoose_005f22.doEndTag() == 5) {
/* 2950 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22); return;
/*      */                   }
/*      */                   
/* 2953 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22);
/* 2954 */                   out.write("\n    </td>\n</tr>\n   \n\n ");
/* 2955 */                   if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2956 */                     out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                     
/* 2958 */                     ChooseTag _jspx_th_c_005fchoose_005f23 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2959 */                     _jspx_th_c_005fchoose_005f23.setPageContext(_jspx_page_context);
/* 2960 */                     _jspx_th_c_005fchoose_005f23.setParent(_jspx_th_tiles_005fput_005f2);
/* 2961 */                     int _jspx_eval_c_005fchoose_005f23 = _jspx_th_c_005fchoose_005f23.doStartTag();
/* 2962 */                     if (_jspx_eval_c_005fchoose_005f23 != 0) {
/*      */                       for (;;) {
/* 2964 */                         out.write("\n   ");
/*      */                         
/* 2966 */                         WhenTag _jspx_th_c_005fwhen_005f23 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2967 */                         _jspx_th_c_005fwhen_005f23.setPageContext(_jspx_page_context);
/* 2968 */                         _jspx_th_c_005fwhen_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/*      */                         
/* 2970 */                         _jspx_th_c_005fwhen_005f23.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 2971 */                         int _jspx_eval_c_005fwhen_005f23 = _jspx_th_c_005fwhen_005f23.doStartTag();
/* 2972 */                         if (_jspx_eval_c_005fwhen_005f23 != 0) {
/*      */                           for (;;) {
/* 2974 */                             out.write("\n    ");
/*      */                             
/* 2976 */                             AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2977 */                             _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 2978 */                             _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f23);
/*      */                             
/* 2980 */                             _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                             
/* 2982 */                             _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 2983 */                             int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 2984 */                             if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 2985 */                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2986 */                                 out = _jspx_page_context.pushBody();
/* 2987 */                                 _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 2988 */                                 _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2991 */                                 out.write(10);
/* 2992 */                                 out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 2993 */                                 out.write("\n    ");
/* 2994 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 2995 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2998 */                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2999 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3002 */                             if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 3003 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                             }
/*      */                             
/* 3006 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 3007 */                             out.write("\n   ");
/* 3008 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f23.doAfterBody();
/* 3009 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3013 */                         if (_jspx_th_c_005fwhen_005f23.doEndTag() == 5) {
/* 3014 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23); return;
/*      */                         }
/*      */                         
/* 3017 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23);
/* 3018 */                         out.write("\n   ");
/*      */                         
/* 3020 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f23 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3021 */                         _jspx_th_c_005fotherwise_005f23.setPageContext(_jspx_page_context);
/* 3022 */                         _jspx_th_c_005fotherwise_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/* 3023 */                         int _jspx_eval_c_005fotherwise_005f23 = _jspx_th_c_005fotherwise_005f23.doStartTag();
/* 3024 */                         if (_jspx_eval_c_005fotherwise_005f23 != 0) {
/*      */                           for (;;) {
/* 3026 */                             out.write(10);
/* 3027 */                             out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 3028 */                             out.write("\n   ");
/* 3029 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f23.doAfterBody();
/* 3030 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3034 */                         if (_jspx_th_c_005fotherwise_005f23.doEndTag() == 5) {
/* 3035 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23); return;
/*      */                         }
/*      */                         
/* 3038 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23);
/* 3039 */                         out.write(10);
/* 3040 */                         out.write(32);
/* 3041 */                         out.write(32);
/* 3042 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f23.doAfterBody();
/* 3043 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3047 */                     if (_jspx_th_c_005fchoose_005f23.doEndTag() == 5) {
/* 3048 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23); return;
/*      */                     }
/*      */                     
/* 3051 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23);
/* 3052 */                     out.write("\n </td>\n</tr>\n  ");
/*      */                   }
/* 3054 */                   out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                   
/* 3056 */                   ChooseTag _jspx_th_c_005fchoose_005f24 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3057 */                   _jspx_th_c_005fchoose_005f24.setPageContext(_jspx_page_context);
/* 3058 */                   _jspx_th_c_005fchoose_005f24.setParent(_jspx_th_tiles_005fput_005f2);
/* 3059 */                   int _jspx_eval_c_005fchoose_005f24 = _jspx_th_c_005fchoose_005f24.doStartTag();
/* 3060 */                   if (_jspx_eval_c_005fchoose_005f24 != 0) {
/*      */                     for (;;) {
/* 3062 */                       out.write("\n   ");
/*      */                       
/* 3064 */                       WhenTag _jspx_th_c_005fwhen_005f24 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3065 */                       _jspx_th_c_005fwhen_005f24.setPageContext(_jspx_page_context);
/* 3066 */                       _jspx_th_c_005fwhen_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/*      */                       
/* 3068 */                       _jspx_th_c_005fwhen_005f24.setTest("${param.method!='showDataCleanUp'}");
/* 3069 */                       int _jspx_eval_c_005fwhen_005f24 = _jspx_th_c_005fwhen_005f24.doStartTag();
/* 3070 */                       if (_jspx_eval_c_005fwhen_005f24 != 0) {
/*      */                         for (;;) {
/* 3072 */                           out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 3073 */                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3074 */                           out.write("\n    </a>\n   ");
/* 3075 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f24.doAfterBody();
/* 3076 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3080 */                       if (_jspx_th_c_005fwhen_005f24.doEndTag() == 5) {
/* 3081 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24); return;
/*      */                       }
/*      */                       
/* 3084 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24);
/* 3085 */                       out.write("\n   ");
/*      */                       
/* 3087 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f24 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3088 */                       _jspx_th_c_005fotherwise_005f24.setPageContext(_jspx_page_context);
/* 3089 */                       _jspx_th_c_005fotherwise_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/* 3090 */                       int _jspx_eval_c_005fotherwise_005f24 = _jspx_th_c_005fotherwise_005f24.doStartTag();
/* 3091 */                       if (_jspx_eval_c_005fotherwise_005f24 != 0) {
/*      */                         for (;;) {
/* 3093 */                           out.write(10);
/* 3094 */                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3095 */                           out.write("\n   ");
/* 3096 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f24.doAfterBody();
/* 3097 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3101 */                       if (_jspx_th_c_005fotherwise_005f24.doEndTag() == 5) {
/* 3102 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24); return;
/*      */                       }
/*      */                       
/* 3105 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24);
/* 3106 */                       out.write(10);
/* 3107 */                       out.write(32);
/* 3108 */                       out.write(32);
/* 3109 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f24.doAfterBody();
/* 3110 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3114 */                   if (_jspx_th_c_005fchoose_005f24.doEndTag() == 5) {
/* 3115 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24); return;
/*      */                   }
/*      */                   
/* 3118 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24);
/* 3119 */                   out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 3120 */                   out.write(10);
/* 3121 */                   out.write(9);
/*      */                 }
/* 3123 */                 out.write("\n\n <br>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr>\n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 3124 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 3125 */                 out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 3126 */                 if (_jspx_meth_c_005fout_005f5(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                   return;
/* 3128 */                 out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n  </tr>\n  <tr>\n    <td colspan=\"2\" class=\"quicknote\">");
/* 3129 */                 out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.quicknote.text"));
/* 3130 */                 out.write("</td>\n  </tr>\n</table>\n");
/* 3131 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3132 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 3135 */               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3136 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 3139 */             if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3140 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */             }
/*      */             
/* 3143 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3144 */             out.write(10);
/*      */             
/* 3146 */             PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3147 */             _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3148 */             _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/* 3150 */             _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */             
/* 3152 */             _jspx_th_tiles_005fput_005f3.setType("string");
/* 3153 */             int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3154 */             if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 3155 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3156 */                 out = _jspx_page_context.pushBody();
/* 3157 */                 _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 3158 */                 _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 3161 */                 out.write(10);
/* 3162 */                 if (request.getAttribute("message") != null)
/*      */                 {
/* 3164 */                   out.write("\n\n <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n <tr>\n          <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n              <tr>\n               <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" class=\"message\">  ");
/* 3165 */                   out.print(request.getAttribute("message"));
/* 3166 */                   out.write("\n                 </td>\n              </tr>\n          </table></td></tr></table>\n\n\n  ");
/*      */                 }
/* 3168 */                 out.write(10);
/* 3169 */                 out.write(32);
/* 3170 */                 out.write(32);
/*      */                 
/* 3172 */                 org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/* 3173 */                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 3174 */                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3176 */                 _jspx_th_html_005fform_005f0.setAction("/scheduleReports");
/*      */                 
/* 3178 */                 _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 3179 */                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 3180 */                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                   for (;;) {
/* 3182 */                     out.write("\n\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\">\n\t<tr>\n\t");
/*      */                     
/* 3184 */                     if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                     {
/*      */ 
/* 3187 */                       out.write("\n\t  <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3188 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 3189 */                       out.write(" &gt; <span class=\"bcactive\">");
/* 3190 */                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3191 */                       out.write("</span></td>\n\t");
/*      */                     }
/*      */                     else {
/* 3194 */                       out.write("\n\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3195 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 3196 */                       out.write(" &gt; <span class=\"bcactive\">");
/* 3197 */                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3198 */                       out.write("</span></td>\n\t");
/*      */                     }
/* 3200 */                     out.write("\n\t</tr>\n</table>\n\n\n\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\t<tr>\n\t\t<td>\t\n\t\t\t<div id=\"two\">\n\t\t\t\t<fieldset>\n\t\t\t\t<div class=\"new-report-heading\"><img src=\"/images/process-step1.png\" width=\"27\" height=\"27\"> &nbsp; ");
/* 3201 */                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3203 */                     out.write(" </div> ");
/* 3204 */                     out.write("\n\t\t\t\t <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" >\n\t\t\t\t    <tr>\n\t\t\t\t\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 3205 */                     out.print(FormatUtil.getString("am.webclient.maintenance.schedulename"));
/* 3206 */                     out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t  <td width=\"75%\" colspan='2'>");
/* 3207 */                     if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3209 */                     out.write("</td>\n\t\t\t\t\t  </tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 3210 */                     out.print(FormatUtil.getString("am.webclient.maintenance.description"));
/* 3211 */                     out.write("</td>\n\t\t\t\t\t  <td width=\"75%\" colspan='2'>");
/* 3212 */                     if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3214 */                     out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 3215 */                     out.print(FormatUtil.getString("am.webclient.maintenance.status"));
/* 3216 */                     out.write("</td>\n\t\t\t\t\t  <td class=\"bodytext\" width=\"75%\" valign=\"middle\" colspan='2'>");
/* 3217 */                     if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3219 */                     out.write("&nbsp;");
/* 3220 */                     out.print(FormatUtil.getString("am.webclient.maintenance.enable"));
/* 3221 */                     out.write(" &nbsp;&nbsp;");
/* 3222 */                     if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3224 */                     out.write("&nbsp;");
/* 3225 */                     out.print(FormatUtil.getString("am.webclient.maintenance.disable"));
/* 3226 */                     out.write(" </td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</fieldset>\n\t\t\t</div>\n\t\t</td>\n\t\t<td width=\"35%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n\t\t\t<em>\n                ");
/* 3227 */                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3229 */                     out.write("\n\t\t\t</em> \t\n\t\t\t");
/* 3230 */                     if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3232 */                     out.write("\n\t\t</td>\n\t</tr>\n</table>\t\t\t\t\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\t<tr>\n\t\t<td>\t\n\t\t\t<div id=\"two\">\n\t\t\t\t<fieldset>\n\t\t\t\t<div class=\"new-report-heading\"><img src=\"/images/process-step2.png\" width=\"27\" height=\"27\"> &nbsp; ");
/* 3233 */                     if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3235 */                     out.write(" </div> ");
/* 3236 */                     out.write("\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 3237 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.reporttype.text"));
/* 3238 */                     out.write("</td>\n\t\t\t\t\t\t  <td class=\"bodytext\" width=\"75%\" valign=\"middle\" colspan='2'>");
/* 3239 */                     if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3241 */                     out.write("\n\t\t \t\t\t\t  </td>\n\t \t\t\t\t</tr>\n\t\t\t\t\t<tr id='showattribute'>\n\t\t\t\t\t\t  <td class=\"bodytext label-align\" width=\"25%\" >");
/* 3242 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 3243 */                     out.write("</td>\n\t\t\t\t\t\t <td class=\"bodytext\" width=\"75%\" valign=\"middle\"  align=left colspan=\"2\">\n\t\t\t\t\t\t ");
/* 3244 */                     if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3246 */                     out.write("\n\t\t\t\t\t\t </td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr id='showperformancereportname'>\n\t\t\t\t\t\t <td class=\"bodytext label-align\" width=\"25%\" >");
/* 3247 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectperformance.text"));
/* 3248 */                     out.write("</td>\n\t\t\t\t\t\t <td class=\"bodytext\" width=\"75%\" valign=\"middle\"  align=\"left\" colspan=\"2\">\n\t\t\t\t\t\t ");
/* 3249 */                     if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3251 */                     out.write("\n\t\t\t\t\t\t </td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr id='showforecastattribute'>\n\t\t\t\t\t\t  <td class=\"bodytext label-align\" width=\"25%\" >");
/* 3252 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 3253 */                     out.write("</td>\n\t\t\t\t\t\t <td class=\"bodytext\" width=\"75%\" valign=\"middle\"  align=left colspan=\"2\">\n\t\t\t\t\t\t ");
/* 3254 */                     if (_jspx_meth_html_005fselect_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3256 */                     out.write("\n\t\t\t\t\t\t </td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr id='reportperiodid'>\n\t\t\t\t\t\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 3257 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.reportperiod.text"));
/* 3258 */                     out.write("</td>\n\t\t\t\t\t\t <td class=\"bodytext\" width=\"75%\" valign=\"middle\" colspan='2'>\n\t\t\t\t\t          <div id='showdefault' style='display:block;'>\n\t\t\t\t\t          \t");
/* 3259 */                     if (_jspx_meth_html_005fselect_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3261 */                     out.write("\n\t\t\t                 </div>\n\t\t\t                 <div id='showperformance' style='display:block;'>\n\t\t\t                 \t");
/* 3262 */                     if (_jspx_meth_html_005fselect_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3264 */                     out.write("\n\t \t\t\t\t\t\t </div>\n\t\t\t                 <div id='forecastTimePeriod' style='display:none;'>\n\t\t\t\t\t          \t");
/* 3265 */                     if (_jspx_meth_html_005fselect_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3267 */                     out.write("\n\t\t\t                 </div>\n\t \t\t\t\t\t\t <div id='showoutage' style='display:block;'>\n\t \t\t\t\t\t\t ");
/* 3268 */                     if (_jspx_meth_html_005fselect_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3270 */                     out.write("\n\t \t\t\t\t\t\t</div>\n\t \t\t\t\t\t\t<div id='showtrend' style='display:block;'>\n\t \t\t\t\t\t\t");
/* 3271 */                     if (_jspx_meth_html_005fselect_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3273 */                     out.write("\n\t \t\t\t\t\t\t</div>\n\t \t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t  \t\t\t\t\t<td class=\"bodytext\" width=\"100%\" colspan='3' class=\"cellpadd-none\">\n\t\t\t\t\t\t\t<div id='showbusiness' style='display:block;'>\n\t\t\t\t\t\t\t\t<table border=\"0\" width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\" align=\"left\">");
/* 3274 */                     out.print(FormatUtil.getString("am.webclient.schreporting.brule.text"));
/* 3275 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t   <td align=\"left\" >\n\t\t\t\t\t\t\t                ");
/* 3276 */                     if (_jspx_meth_html_005fselect_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3278 */                     out.write("\n\t \t\t\t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div id=\"forecastGrowthTrend\" style=\"display:none\">\n\t\t\t\t\t\t\t<table border=\"0\" width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\" align=\"left\">");
/* 3279 */                     out.print(FormatUtil.getString("Growth Trend"));
/* 3280 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t   <td align=\"left\" >\n\t\t\t\t\t\t\t\t\t\t\t<select name=\"forecastTrend\" class=\"formtext medium\" id=\"growthtrend\">\n\t\t\t\t             \t  \t\t\t\t<option selected=\"selected\" value=\"1\">");
/* 3281 */                     out.print(FormatUtil.getString("am.webclient.forecast.growthtrend.1month.text"));
/* 3282 */                     out.write("</option>\n\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"2\">");
/* 3283 */                     out.print(FormatUtil.getString("am.webclient.forecast.growthtrend.2month.text"));
/* 3284 */                     out.write("</option>\n\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"3\">");
/* 3285 */                     out.print(FormatUtil.getString("am.webclient.forecast.growthtrend.3month.text"));
/* 3286 */                     out.write("</option>\n\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"4\">");
/* 3287 */                     out.print(FormatUtil.getString("am.webclient.forecast.growthtrend.4month.text"));
/* 3288 */                     out.write("</option>\n\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"5\">");
/* 3289 */                     out.print(FormatUtil.getString("am.webclient.forecast.growthtrend.5month.text"));
/* 3290 */                     out.write("</option>\n\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"6\">");
/* 3291 */                     out.print(FormatUtil.getString("am.webclient.forecast.growthtrend.6month.text"));
/* 3292 */                     out.write("</option>\n\t\t\t\t             \t  \t \t\t<select>\n\t \t\t\t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t \t\t  \t</div>\n\t \t\t\t\t\t</td>\n\t\t\t\t\t</tr>\t\n\t\t\t\t");
/*      */                     
/* 3294 */                     if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition())
/*      */                     {
/*      */ 
/* 3297 */                       ArrayList orgs = com.adventnet.appmanager.struts.beans.AlarmUtil.getCustomerNames();
/* 3298 */                       if (orgs != null)
/*      */                       {
/* 3300 */                         request.setAttribute("customers", orgs);
/*      */                       }
/*      */                       
/*      */ 
/* 3304 */                       out.write("\n\t\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"bodytext\" height=\"10\">");
/* 3305 */                       out.print(FormatUtil.getString("am.webclient.customer.selectcustomer"));
/* 3306 */                       out.write("</td>\n\t\t\t\t\t<td>\n\t\t\t\t\t");
/*      */                       
/* 3308 */                       SelectTag _jspx_th_html_005fselect_005f10 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 3309 */                       _jspx_th_html_005fselect_005f10.setPageContext(_jspx_page_context);
/* 3310 */                       _jspx_th_html_005fselect_005f10.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 3312 */                       _jspx_th_html_005fselect_005f10.setProperty("organization");
/*      */                       
/* 3314 */                       _jspx_th_html_005fselect_005f10.setStyleClass("formtext normal");
/*      */                       
/* 3316 */                       _jspx_th_html_005fselect_005f10.setOnchange("javascript:loadSite();javascript:constructUrl();");
/* 3317 */                       int _jspx_eval_html_005fselect_005f10 = _jspx_th_html_005fselect_005f10.doStartTag();
/* 3318 */                       if (_jspx_eval_html_005fselect_005f10 != 0) {
/* 3319 */                         if (_jspx_eval_html_005fselect_005f10 != 1) {
/* 3320 */                           out = _jspx_page_context.pushBody();
/* 3321 */                           _jspx_th_html_005fselect_005f10.setBodyContent((BodyContent)out);
/* 3322 */                           _jspx_th_html_005fselect_005f10.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3325 */                           out.write("\n\t\t\t\t");
/*      */                           
/* 3327 */                           if (request.getAttribute("CUSTOMER") == null)
/*      */                           {
/*      */ 
/* 3330 */                             out.write("\n\t\t\t\t\t<option value=\"-1\" selected='selected'>");
/* 3331 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.customer"));
/* 3332 */                             out.write("</option>\n\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 3336 */                           out.write("\n\t\t\t\t\t");
/*      */                           
/* 3338 */                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3339 */                           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3340 */                           _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fselect_005f10);
/*      */                           
/* 3342 */                           _jspx_th_logic_005fnotEmpty_005f0.setName("customers");
/* 3343 */                           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3344 */                           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                             for (;;) {
/* 3346 */                               out.write("\n\t\t\t\t\t\t");
/*      */                               
/* 3348 */                               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3349 */                               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3350 */                               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                               
/* 3352 */                               _jspx_th_logic_005fiterate_005f0.setName("customers");
/*      */                               
/* 3354 */                               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                               
/* 3356 */                               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                               
/* 3358 */                               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 3359 */                               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3360 */                               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3361 */                                 ArrayList row = null;
/* 3362 */                                 Integer j = null;
/* 3363 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3364 */                                   out = _jspx_page_context.pushBody();
/* 3365 */                                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3366 */                                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                 }
/* 3368 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3369 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                 for (;;) {
/* 3371 */                                   out.write("\n\t\t\t\t\t\t");
/*      */                                   
/* 3373 */                                   if (request.getAttribute("CUSTOMER") != null)
/*      */                                   {
/* 3375 */                                     if (request.getAttribute("CUSTOMER").equals(row.get(1)))
/*      */                                     {
/*      */ 
/* 3378 */                                       out.write("\n\t\t\t\t\t\t\t\t<option value=\"");
/* 3379 */                                       out.print((String)row.get(1));
/* 3380 */                                       out.write("\" selected='selected'>");
/* 3381 */                                       out.print(row.get(0));
/* 3382 */                                       out.write("</option>\n\t\t\t\t\t\t\t");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 3388 */                                       out.write("\n\t\t\t\t\t\t\t<option value=\"");
/* 3389 */                                       out.print((String)row.get(1));
/* 3390 */                                       out.write(34);
/* 3391 */                                       out.write(62);
/* 3392 */                                       out.print(row.get(0));
/* 3393 */                                       out.write("</option>\n\t\t\t\t\t\t\t");
/*      */                                     }
/*      */                                     
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3399 */                                     out.write("\n\t\t\t\t\t\t\t<option value=\"");
/* 3400 */                                     out.print((String)row.get(1));
/* 3401 */                                     out.write(34);
/* 3402 */                                     out.write(62);
/* 3403 */                                     out.print(row.get(0));
/* 3404 */                                     out.write("</option>\n\t\t\t\t\t\t");
/*      */                                   }
/* 3406 */                                   out.write("\n\t\t\t\t\t\t");
/* 3407 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3408 */                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3409 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 3410 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3413 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3414 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3417 */                               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3418 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                               }
/*      */                               
/* 3421 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3422 */                               out.write(" \n\t\t\t\t\t");
/* 3423 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3424 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3428 */                           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3429 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                           }
/*      */                           
/* 3432 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3433 */                           out.write("\n\t\t\t ");
/* 3434 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f10.doAfterBody();
/* 3435 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3438 */                         if (_jspx_eval_html_005fselect_005f10 != 1) {
/* 3439 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3442 */                       if (_jspx_th_html_005fselect_005f10.doEndTag() == 5) {
/* 3443 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f10); return;
/*      */                       }
/*      */                       
/* 3446 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f10);
/* 3447 */                       out.write("\t</td>\n\t\t\t<tr>\n\t\t\t\t<td class=\"bodytext\" height=\"10\">");
/* 3448 */                       out.print(FormatUtil.getString("am.webclient.reports.select.site.text"));
/* 3449 */                       out.write("</td>\n\t\t\t\t<td>\n\t\t\t\t\t");
/*      */                       
/* 3451 */                       SelectTag _jspx_th_html_005fselect_005f11 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 3452 */                       _jspx_th_html_005fselect_005f11.setPageContext(_jspx_page_context);
/* 3453 */                       _jspx_th_html_005fselect_005f11.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 3455 */                       _jspx_th_html_005fselect_005f11.setProperty("haid");
/*      */                       
/* 3457 */                       _jspx_th_html_005fselect_005f11.setStyleClass("formtext normal");
/*      */                       
/* 3459 */                       _jspx_th_html_005fselect_005f11.setOnchange("javascript:constructUrl()");
/* 3460 */                       int _jspx_eval_html_005fselect_005f11 = _jspx_th_html_005fselect_005f11.doStartTag();
/* 3461 */                       if (_jspx_eval_html_005fselect_005f11 != 0) {
/* 3462 */                         if (_jspx_eval_html_005fselect_005f11 != 1) {
/* 3463 */                           out = _jspx_page_context.pushBody();
/* 3464 */                           _jspx_th_html_005fselect_005f11.setBodyContent((BodyContent)out);
/* 3465 */                           _jspx_th_html_005fselect_005f11.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3468 */                           out.write("\n\t\t\t\t");
/*      */                           
/* 3470 */                           if ((request.getAttribute("SITE") != null) && (!request.getAttribute("SITE").equals("null")))
/*      */                           {
/*      */ 
/* 3473 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 3475 */                             OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3476 */                             _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3477 */                             _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f11);
/*      */                             
/* 3479 */                             _jspx_th_html_005foption_005f0.setValue((String)request.getAttribute("SITE"));
/* 3480 */                             int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3481 */                             if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3482 */                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3483 */                                 out = _jspx_page_context.pushBody();
/* 3484 */                                 _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3485 */                                 _jspx_th_html_005foption_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3488 */                                 out.print((String)request.getAttribute("SITENAME"));
/* 3489 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3490 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3493 */                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3494 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3497 */                             if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3498 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                             }
/*      */                             
/* 3501 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3502 */                             out.write("\n\t\t\t\t");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 3508 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 3510 */                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3511 */                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3512 */                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f11);
/*      */                             
/* 3514 */                             _jspx_th_html_005foption_005f1.setValue("-1");
/* 3515 */                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3516 */                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3517 */                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3518 */                                 out = _jspx_page_context.pushBody();
/* 3519 */                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3520 */                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3523 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup"));
/* 3524 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3525 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3528 */                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3529 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3532 */                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3533 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                             }
/*      */                             
/* 3536 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3537 */                             out.write("\n\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 3541 */                           out.write("\n\t\t\t\t");
/*      */                           
/* 3543 */                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3544 */                           _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3545 */                           _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_html_005fselect_005f11);
/*      */                           
/* 3547 */                           _jspx_th_logic_005fnotEmpty_005f1.setName("applications2");
/* 3548 */                           int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3549 */                           if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                             for (;;) {
/* 3551 */                               out.write(32);
/*      */                               
/* 3553 */                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3554 */                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 3555 */                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                               
/* 3557 */                               _jspx_th_logic_005fiterate_005f1.setName("applications2");
/*      */                               
/* 3559 */                               _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                               
/* 3561 */                               _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                               
/* 3563 */                               _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 3564 */                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 3565 */                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 3566 */                                 ArrayList row = null;
/* 3567 */                                 Integer j = null;
/* 3568 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3569 */                                   out = _jspx_page_context.pushBody();
/* 3570 */                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 3571 */                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                 }
/* 3573 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3574 */                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                 for (;;) {
/* 3576 */                                   out.write("\n\t\t\t\t");
/*      */                                   
/* 3578 */                                   OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3579 */                                   _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3580 */                                   _jspx_th_html_005foption_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                   
/* 3582 */                                   _jspx_th_html_005foption_005f2.setValue((String)row.get(1));
/* 3583 */                                   int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3584 */                                   if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3585 */                                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3586 */                                       out = _jspx_page_context.pushBody();
/* 3587 */                                       _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3588 */                                       _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3591 */                                       out.print(row.get(0));
/* 3592 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3593 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3596 */                                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3597 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3600 */                                   if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3601 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                   }
/*      */                                   
/* 3604 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3605 */                                   out.write("\n\t\t\t\t");
/* 3606 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3607 */                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3608 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 3609 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3612 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3613 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3616 */                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3617 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                               }
/*      */                               
/* 3620 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3621 */                               out.write(32);
/* 3622 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3623 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3627 */                           if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3628 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                           }
/*      */                           
/* 3631 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3632 */                           out.write(32);
/* 3633 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f11.doAfterBody();
/* 3634 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3637 */                         if (_jspx_eval_html_005fselect_005f11 != 1) {
/* 3638 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3641 */                       if (_jspx_th_html_005fselect_005f11.doEndTag() == 5) {
/* 3642 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f11); return;
/*      */                       }
/*      */                       
/* 3645 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f11);
/* 3646 */                       out.write("</td>\n\t\t\t</tr>\t\n\t\t\t");
/*      */                     }
/*      */                     
/*      */ 
/* 3650 */                     out.write("\n\t\t\t\t\t\n\t\t\t\t\t \n\t\t\t\t\t<tr>\n\t  \t\t\t\t\t<td class=\"bodytext label-align\" width=\"10%\">\n\t\t\t\t\t\t\t  <div id='SLAOPT1' style='display:block;'>\n\t\t\t\t\t\t\t \t\t");
/* 3651 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.generatereport.text"));
/* 3652 */                     out.write("\n\t\t\t\t\t\t\t  </div>\n\t \t\t\t \t\t</td>\n\t \t\t\t\t\t<td class=\"bodytext\" width=\"90%\" valign=\"middle\" colspan='2'>\n\t\t\t\t\t\t\t<div id='SLAOPT2' style='display:inline;padding-bottom:4px'>\n\t\t\t\t\t\t\t         ");
/* 3653 */                     if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3655 */                     out.write("&nbsp;");
/* 3656 */                     out.print(FormatUtil.getString("All BA(s)"));
/* 3657 */                     out.write(" \n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div id='SLAOPT3' style='display:inline;padding-bottom:4px'>\n\t\t\t\t\t\t\t\t\t");
/* 3658 */                     if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3660 */                     out.write("&nbsp;");
/* 3661 */                     out.print(FormatUtil.getString("Show only SLA assigned BA(s)"));
/* 3662 */                     out.write(" \n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div id='SLAOPT4' style='display:inline;padding-bottom:4px'>\n\t\t\t\t\t\t\t\t");
/* 3663 */                     if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3665 */                     out.write("&nbsp;");
/* 3666 */                     out.print(FormatUtil.getString("All Server(s)"));
/* 3667 */                     out.write(" \n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t <div id='SLAOPT5' style='display:inline;padding-bottom:4px'>\n\t\t\t\t\t\t\t\t");
/* 3668 */                     if (_jspx_meth_html_005fradio_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3670 */                     out.write("&nbsp;");
/* 3671 */                     out.print(FormatUtil.getString("Show only SLA assigned Server(s)"));
/* 3672 */                     out.write(" \n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t\n\t\t\t\t\t<tr>\n\t  \t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\">\n\t\t\t\t\t\t\t  <div id='sqlmanGRF' style='display:block;'>\n\t\t\t\t\t\t\t \t\t");
/* 3673 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.generatereport.text"));
/* 3674 */                     out.write("\n\t\t\t\t\t\t\t  </div>\n\t \t\t\t \t\t</td>\n\t \t\t\t\t\t<td class=\"bodytext\" width=\"75%\" valign=\"middle\" colspan='2'>\n\t\t\t\t\t\t\t<div id='sqlmanOP1' style='display:inline;padding-bottom:4px'>\n\t\t\t\t\t\t\t         ");
/* 3675 */                     if (_jspx_meth_html_005fradio_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3677 */                     out.write("&nbsp;");
/* 3678 */                     out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/* 3679 */                     out.write(" &nbsp;&nbsp;\n\t\t\t\t\t\t\t</div>\n\t\t\n\t\t\t\t\t\t\t<div id='sqlmanOP2' style='display:inline;padding-bottom:4px'>\n\t\t\t\t\t\t\t\t\t");
/* 3680 */                     if (_jspx_meth_html_005fradio_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3682 */                     out.write("&nbsp;");
/* 3683 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.reportfor.monitortypes"));
/* 3684 */                     out.write("&nbsp;&nbsp;\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div id='sqlmanOP3' style='display:inline;padding-bottom:4px'>\n\t\t\t\t\t\t\t\t");
/* 3685 */                     if (_jspx_meth_html_005fradio_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3687 */                     out.write("&nbsp;");
/* 3688 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.reportfor.monitor"));
/* 3689 */                     out.write("\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t\n\t\t\t\t\t <tr>\n\t \t\t\t\t\t<td class='bodytext cellpadd-none' colspan='3' width='100%'>\n\t\t\t\t\t\t\t <div id=\"showEUMResources\" style=\"display:none\">\n\t\t\t\t\t\t\t \t <table border='0' width='100%' cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t\t\t\t\t \t \t<tr>\n\t\t\t\t\t\t\t \t \t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 3690 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.resourcetypes.text"));
/* 3691 */                     out.write("\n\t\t\t\t\t\t\t \t \t\t</td>\n\t\t\t\t\t\t\t \t \t\t<td class=\"bodytext\" width=\"75%\" valign=\"middle\"  align=left>\n\t\t\t\t\t\t\t \t \t\t");
/* 3692 */                     if (_jspx_meth_html_005fselect_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3694 */                     out.write("\n\t\t\t\t\t\t\t \t \t\t</td>\n\t\t\t\t\t\t\t \t \t</tr>\n\t\t\t\t\t\t\t \t </table>\t\t \n\t\t\t\t\t\t\t </div>\t \n\t\t\t\t\t\t\t <div id='showresources' style='display:block;'>\n\t\t\t\t\t\t\t\t <table border='0' width='100%' cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t \t<div id='showtrap' style='display:block;'>\n\t\t\t\t\t\t\t\t\t \t<tr>\t\t\t\t\t\t\t\t  \n\t\t\t\t\t\t\t\t\t\t\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 3695 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.resourcetypes.text"));
/* 3696 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t  <td class=\"bodytext\" width=\"75%\" valign=\"middle\"  align=left>\n\t\t\t\t\t\t\t\t         \t\t  ");
/* 3697 */                     if (_jspx_meth_html_005fselect_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3699 */                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t </td>\n\t\t\t\t\t\t\t\t\t    </tr>\n\t\t\t\t\t\t\t     \t</div>\n\t\t\t\t\t\t\t\t </table>\n\t\t\t\t\t\t\t</div>\n\t\t \t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr id=\"customFilter\">\n\t\t\t\t\t\t\t<td colspan=\"3\" width=\"100%\">\n\t\t\t\t\t\t\t\t<div id=\"customfieldfilter\" style=\"display: block;\"></div>\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t \t\t\t\t\t <td class=\"bodytext plainheading1 align-left\" width=\"25%\" colspan=\"3\" valign=top>\n\t\t\t\t\t\t\t <div id='sqlman' style='display:block;'>\n\t\t\t\t\t\t\t  &nbsp;<b>");
/* 3700 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectresources.text"));
/* 3701 */                     out.write("</b>\n\t\t\t\t\t\t\t  </div>\n\t  \t\t\t\t\t</td>\n\t\t\t\t\t </tr>\n\t\t\t\t\t <tr id='showsqlservers'>\n\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\" >");
/* 3702 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectsqlserver.text"));
/* 3703 */                     out.write("</td>\n\t\t\t\t\t\t<td class=\"bodytext\" width=\"75%\" valign=\"middle\"  align=\"left\" colspan=\"2\">\n\t\t\t\t\t\t");
/* 3704 */                     if (_jspx_meth_html_005fselect_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3706 */                     out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr id='showsqlserversdb'>\n\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"24%\" >");
/* 3707 */                     out.print(FormatUtil.getString("am.webclient.common.mssql.filterbydatabase.notselected.text"));
/* 3708 */                     out.write("</td>\n\t\t\t\t\t\t<td class=\"bodytext\" width=\"75%\" valign=\"middle\"  align=\"left\" colspan=\"2\">\n\t\t\t\t\t\t");
/* 3709 */                     if (_jspx_meth_html_005fselect_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3711 */                     out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/* 3712 */                     if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3714 */                     out.write("\n\t\t\t\t\t");
/* 3715 */                     if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3717 */                     out.write("\n\t\t\t\t\t <tr>\n\t\t\t\t\t\t  <td class=\"bodytext\" width=\"100%\" valign=\"middle\" colspan='3'>\n\t\t\t\t\t\t\t  <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t\t\t\t  <td> \n\t\t\t\t\t\t\t\t\t  \t<div id=\"resourceloading\" style=\"display:block\" >\n\t\t\t\t\t\t\t\t\t\t  <img border=\"0\" src=\"/images/icon_cogwheel.gif\"  >&nbsp;");
/* 3718 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.loadingmessage.text"));
/* 3719 */                     out.write("...\n\t\t\t\t\t\t\t\t\t \t </div>\n\t\t\t\t\t\t\t\t\t </td>\n\t\t\t\t\t\t\t\t\t  <td > \n\t\t\t\t\t\t\t\t\t  \t\t<div id=\"monitordetail\"  style=\"display:block;overflow:auto;width:100%;height:100%\" >\n\t\t\t\t\t\t\t\t\t \t </div>\n\t\t\t\t\t\t\t\t\t </td>\n\t\t\t\t\t\t\t\t  </tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t </tr>\n\t\t\t\t\t</table>\n\t\t\t\t</fieldset>\n\t\t\t</div>\n\t\t</td>\n\t\t<td width=\"35%\" valign=\"center\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n\t\t\t<em>\n                ");
/* 3720 */                     if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3722 */                     out.write("\n\t\t\t</em> \t\n\t\t\t\t");
/* 3723 */                     if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3725 */                     out.write("\n\t\t</td>\n\t</tr>\t\n</table>\t\t\t\t\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\t<tr>\n\t\t<td>\t\n\t\t\t<div id=\"two\">\n\t\t\t\t<fieldset>\n\t\t\t\t<div class=\"new-report-heading\"><img src=\"/images/process-step3.png\" width=\"27\" height=\"27\"> &nbsp; ");
/* 3726 */                     if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3728 */                     out.write(" </div> ");
/* 3729 */                     out.write("\n\t\t\t\t <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t \t\t  <tr>\n\t\t\t\t\t\t  <td width=\"25%\" class=\"bodytext label-align\">");
/* 3730 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.timesettingsheading.text"));
/* 3731 */                     out.write("</td>\n\t\t\t\t\t\t  <td class=\"bodytext\" width=\"75%\" valign=\"middle\">\t\t\t\t\t\t  \n\t\t\t\t\t\t\t  ");
/* 3732 */                     if (_jspx_meth_html_005fradio_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3734 */                     out.write("\n\t\t\t\t\t\t\t\t  ");
/* 3735 */                     out.print(FormatUtil.getString("am.webclient.maintenance.daily"));
/* 3736 */                     out.write("\t\t&nbsp;&nbsp;\t\t\t\t  \n\t\t\t\t\t\t\t  ");
/* 3737 */                     if (_jspx_meth_html_005fradio_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3739 */                     out.write("\n\t\t\t\t\t\t\t \t ");
/* 3740 */                     out.print(FormatUtil.getString("am.webclient.maintenance.weekly"));
/* 3741 */                     out.write("\t&nbsp;&nbsp;\t\t\t\t\t \n\t\t\t\t\t\t\t  ");
/* 3742 */                     if (_jspx_meth_html_005fradio_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3744 */                     out.write("\n\t\t\t\t\t\t\t\t  ");
/* 3745 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.monthly.text"));
/* 3746 */                     out.write(" \n\t\t\t\t\t\t  </td>\n \t\t\t\t\t </tr>\n \t\t\t\t\t <tr>\n \t\t\t\t\t \t<td colspan=\"2\">\n \t\t\t\t\t \t\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n \t\t\t\t\t \t\t\t<tr>\n\t\t\t \t\t\t\t\t \t <td class=\"bodytext label-align\" width=\"25%\">");
/* 3747 */                     out.print(FormatUtil.getString("webclient.performance.reports.index.transmittraffic.xaxisname1"));
/* 3748 */                     out.write("</td>\n\t\t\t \t\t\t\t\t \t <td class=\"bodytext\" width=\"75%\">\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t ");
/* 3749 */                     if (_jspx_meth_html_005fselect_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3751 */                     out.write("&nbsp; ");
/* 3752 */                     out.print(FormatUtil.getString("webclient.performance.reports.index.transmittraffic.hours"));
/* 3753 */                     out.write(" &nbsp;\n\t\t\t             \t\t\t\t");
/* 3754 */                     if (_jspx_meth_html_005fselect_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3756 */                     out.write("&nbsp; ");
/* 3757 */                     out.print(FormatUtil.getString("webclient.performance.reports.index.transmittraffic.Minutes"));
/* 3758 */                     out.write(" &nbsp;\n\t\t\t\t  \t\t\t\t\t</td>\n \t\t\t\t\t\t\t\t </tr>\n \t\t\t\t\t\t\t</table> \t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<div id=\"view_weekly\" style=\"display:none;\">\n\t\t\t\t\t\t\t\t<div style=\"padding:2px\"></div>\n\t\t\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\" border='0'>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext valign-padd-top label-align\" width=\"25%\">");
/* 3759 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.daysinweek.text"));
/* 3760 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" width=\"75%\" align=\"left\">\n\t\t\t\t\t\t\t\t\t\t\t<table width=\"75%\" cellpadding=\"5\" cellspacing=\"0\" border='0'>\n\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3761 */                     if (_jspx_meth_html_005fmultibox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3763 */                     out.write(32);
/* 3764 */                     out.print(FormatUtil.getString("Sunday"));
/* 3765 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3766 */                     if (_jspx_meth_html_005fmultibox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3768 */                     out.write(32);
/* 3769 */                     out.print(FormatUtil.getString("Monday"));
/* 3770 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" colspan='2'>");
/* 3771 */                     if (_jspx_meth_html_005fmultibox_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3773 */                     out.write(32);
/* 3774 */                     out.print(FormatUtil.getString("Tuesday"));
/* 3775 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3776 */                     if (_jspx_meth_html_005fmultibox_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3778 */                     out.write(32);
/* 3779 */                     out.print(FormatUtil.getString("Wednesday"));
/* 3780 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3781 */                     if (_jspx_meth_html_005fmultibox_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3783 */                     out.write(32);
/* 3784 */                     out.print(FormatUtil.getString("Thursday"));
/* 3785 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3786 */                     if (_jspx_meth_html_005fmultibox_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3788 */                     out.write(32);
/* 3789 */                     out.print(FormatUtil.getString("Friday"));
/* 3790 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3791 */                     if (_jspx_meth_html_005fmultibox_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3793 */                     out.write(32);
/* 3794 */                     out.print(FormatUtil.getString("Saturday"));
/* 3795 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t  </table>\t\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div id=\"view_custom\" style=\"display:none;\">\n\t\t\t\t\t\t\t\t<div style=\"padding:5px\"></div>\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\" border='0'>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 3796 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.daysinmonth.text"));
/* 3797 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" width=\"75%\">\n\t \t\t\t\t\t\t\t\t\t\t\t");
/* 3798 */                     if (_jspx_meth_html_005fselect_005f18(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3800 */                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext valign-padd-top label-align\" width=\"25%\">");
/* 3801 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.daysinweek.text"));
/* 3802 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" width=\"75%\">\n\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"99%\" cellpadding=\"5\" cellspacing=\"0\" border='0'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" colspan='5'>");
/* 3803 */                     if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3805 */                     out.write(32);
/* 3806 */                     out.print(FormatUtil.getString("am.monitortab.all.text"));
/* 3807 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" >");
/* 3808 */                     if (_jspx_meth_html_005fmultibox_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3810 */                     out.write(32);
/* 3811 */                     out.print(FormatUtil.getString("January"));
/* 3812 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3813 */                     if (_jspx_meth_html_005fmultibox_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3815 */                     out.write(32);
/* 3816 */                     out.print(FormatUtil.getString("February"));
/* 3817 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3818 */                     if (_jspx_meth_html_005fmultibox_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3820 */                     out.write(32);
/* 3821 */                     out.print(FormatUtil.getString("March"));
/* 3822 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" >");
/* 3823 */                     if (_jspx_meth_html_005fmultibox_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3825 */                     out.write(32);
/* 3826 */                     out.print(FormatUtil.getString("April"));
/* 3827 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n                                                    <tr>\n                                                    <td class=\"bodytext\">");
/* 3828 */                     if (_jspx_meth_html_005fmultibox_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3830 */                     out.write(32);
/* 3831 */                     out.print(FormatUtil.getString("May"));
/* 3832 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3833 */                     if (_jspx_meth_html_005fmultibox_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3835 */                     out.print(FormatUtil.getString("June"));
/* 3836 */                     out.write(" </td>\t\t\t\t\t\n                                                        <td class=\"bodytext\">");
/* 3837 */                     if (_jspx_meth_html_005fmultibox_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3839 */                     out.write(32);
/* 3840 */                     out.print(FormatUtil.getString("July"));
/* 3841 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3842 */                     if (_jspx_meth_html_005fmultibox_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3844 */                     out.write(32);
/* 3845 */                     out.print(FormatUtil.getString("August"));
/* 3846 */                     out.write("</td>\n                                                    </tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3847 */                     if (_jspx_meth_html_005fmultibox_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3849 */                     out.write(32);
/* 3850 */                     out.print(FormatUtil.getString("September"));
/* 3851 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3852 */                     if (_jspx_meth_html_005fmultibox_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3854 */                     out.write(32);
/* 3855 */                     out.print(FormatUtil.getString("October"));
/* 3856 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3857 */                     if (_jspx_meth_html_005fmultibox_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3859 */                     out.write(32);
/* 3860 */                     out.print(FormatUtil.getString("November"));
/* 3861 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 3862 */                     if (_jspx_meth_html_005fmultibox_005f18(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3864 */                     out.write(32);
/* 3865 */                     out.print(FormatUtil.getString("December"));
/* 3866 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</div>\t\n\t\t\t\t\t\t</td>\t\n \t\t\t\t\t </tr> \t\t\t\t\t \n\t\t\t\t </table>\n\t\t\t\t</fieldset>\n\t\t\t</div>\t\n\t\t</td>\n\t\t<td width=\"35%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n\t\t\t<em>\n                ");
/* 3867 */                     if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3869 */                     out.write("\n\t\t\t</em> \n\t\t\t ");
/* 3870 */                     if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3872 */                     out.write("\t\t\t\n\t\t</td>\t\t\n\t</tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\t<tr>\n\t\t<td>\t\n\t\t\t<div id=\"two\">\n\t\t\t\t<fieldset>\n\t\t\t\t\t<div class=\"new-report-heading\"><img src=\"/images/process-step4.png\" width=\"27\" height=\"27\"> &nbsp; ");
/* 3873 */                     if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3875 */                     out.write(" </div> ");
/* 3876 */                     out.write("\n\t\t\t\t\t <table  width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t\t\t \t<tr>\n\t\t\t\t\t \t\t<td class=\"bodytext label-align\" width=\"25%\" align=\"left\"> ");
/* 3877 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliveryas.text"));
/* 3878 */                     out.write("</td>\n\t\t\t\t\t \t\t<td class=\"bodytext\" width=\"75%\">\n\t\t\t\t\t \t\t\t");
/* 3879 */                     if (_jspx_meth_html_005fradio_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3881 */                     out.write("\n\t  \t\t\t\t\t\t\t\t");
/* 3882 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliveryasattachment.text"));
/* 3883 */                     out.write("\n\t\t\t\t\t\t\t\t &nbsp; &nbsp;");
/* 3884 */                     if (_jspx_meth_html_005fradio_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3886 */                     out.write(" \n\t\t\t\t\t\t\t\t\t ");
/* 3887 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliverylink.text"));
/* 3888 */                     out.write("\n\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\n\t\t\t\t\t \t</tr>\n\t\t\t\t\t \t<tr>\n\t\t\t\t\t \t\t<td class=\"bodytext label-align\" width=\"25%\"> ");
/* 3889 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.reporttype.text"));
/* 3890 */                     out.write("</td>\n\t\t\t\t\t \t\t<td class=\"bodytext\" width=\"75%\">\n\t\t\t\t\t \t\t\t<span id='showpdf' class=\"leftF\" style='display:block;'>");
/* 3891 */                     if (_jspx_meth_html_005fradio_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3893 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.pdf.text"));
/* 3894 */                     out.write("</span>\n\t\t\t\t\t \t\t\t<span id='showexcel' class=\"leftF tdindent\">");
/* 3895 */                     if (_jspx_meth_html_005fradio_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3897 */                     out.print(FormatUtil.getString("Excel"));
/* 3898 */                     out.write("  </span>\n\t\t\t\t\t \t\t\t<span id='showcsv' class=\"leftF tdindent\">");
/* 3899 */                     if (_jspx_meth_html_005fradio_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3901 */                     out.print(FormatUtil.getString("CSV"));
/* 3902 */                     out.write("  </span>\n\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\n\t\t\t\t\t \t</tr>\n\t\t\t\t\t \t<tr>\n\t  \t\t\t\t\t\t<td  colspan=\"2\" width='100%' class=\"cellpadd-none\" >\n          \t\t\t\t\t\t <div id=\"actionmessage\" style=\"display:block;\"  class='error-text pad-tp-btm'> </div></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"2\" width=\"100%\" class=\"cellpadd-none\">\n\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 3903 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliverysentemail.text"));
/* 3904 */                     out.write(" \n\t\t\t\t\t\t            \t</td>\n\t\t\t\t\t\t            \t<td width=\"75%\">\t\n\t\t\t\t\t\t            \t\t");
/* 3905 */                     if (_jspx_meth_html_005fselect_005f19(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3907 */                     out.write("&nbsp;&nbsp;&nbsp; <a href='javascript:callAction()' class='staticlinks'> ");
/* 3908 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliverynewaction.text"));
/* 3909 */                     out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/* 3910 */                     if (request.getParameter("sid") != null)
/*      */                     {
/* 3912 */                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t <span class=\"ancillary1\">|</span>&nbsp;<a id=\"actionEditLink\" href='/adminAction.do?method=showEmailAction&haid=null&fromSchedule=true&sid=");
/* 3913 */                       out.print(request.getParameter("sid"));
/* 3914 */                       out.write("' class='staticlinks'>");
/* 3915 */                       out.print(FormatUtil.getString("am.webclient.schedulereport.editactions.link"));
/* 3916 */                       out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                     }
/* 3918 */                     out.write("\n\t\t \t\t\t\t\t\t\t\t</td>\n\t\t \t\t\t\t\t\t\t</tr>\n\t\t \t\t\t\t\t\t\t<tr>\n\t\t \t\t\t\t\t\t\t\t <td colspan=\"2\" class=\"cellpadd-none\">\n\t\t\t \t\t\t\t\t\t\t\t <div id='takeaction' style=\"display:block;\">\n\t\t\t\t\t\t\t\t\t\t\t\t<table width='100%' cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class='bodytext label-align' width=\"25%\">");
/* 3919 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliveryemailid.text"));
/* 3920 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t  <td width='75%'>");
/* 3921 */                     if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3923 */                     out.write(" <input name=\"save\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:getAction();\" value=\"");
/* 3924 */                     out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 3925 */                     out.write("\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input name=\"cancel\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 3926 */                     out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3927 */                     out.write("\" onclick='javascript:removeAction()'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t </div>\n       \t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\t\t\t\t\t\t\n\t\t \t\t\t\t\t\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t </table>\n\t\t\t\t</fieldset>\n\t\t\t</div>\n\t\t</td>\n\t\t<td width=\"35%\" valign=\"top\"><p>  <a class=\"process-tooltip\" href=\"#\"><span class=\"process-custom process-warning\"><img src=\"/images/arrow-help.gif\" alt=\"Warning\" class=\"process-tooltip-img \"  width=\"15\" height=\"27\"/>\n\t\t\t<em>\n                ");
/* 3928 */                     if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3930 */                     out.write("\n\t\t\t</em> \t\n\t\t\t ");
/* 3931 */                     if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/* 3933 */                     out.write(" \n\t\t\t</span> \n\t\t</td>\n\t</tr>\n</table>\t\t\t\t\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"60%\" align=\"center\">\n\t\t  \t");
/* 3934 */                     if ((request.getParameter("edit") != null) && (!request.getParameter("edit").equals("check")))
/*      */                     {
/* 3936 */                       out.write("\n\t\t           <input type=\"hidden\" name='method' value='updateScheduleDetails'>\n\t\t            <input type=\"hidden\" name='sid' value='");
/* 3937 */                       out.print(request.getParameter("sid"));
/* 3938 */                       out.write("'>\n\t\t           <input name=\"Submit\" value=\" ");
/* 3939 */                       out.print(FormatUtil.getString("Update"));
/* 3940 */                       out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnFormSubmit();\">\n\t\t           ");
/*      */                     } else {
/* 3942 */                       out.write("\n\t\t           <input type=\"hidden\" name='method' value='addScheduleDetails'>\n\t\t          <input name=\"Submit\" value=\"");
/* 3943 */                       out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 3944 */                       out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnFormSubmit();\">\n\t\t          ");
/*      */                     }
/* 3946 */                     out.write("\n\t\t\t &nbsp;&nbsp;<input type=\"reset\" name=\"reset\" value=\"");
/* 3947 */                     out.print(FormatUtil.getString("am.webclient.global.Reset.text"));
/* 3948 */                     out.write("\"  class=\"buttons btn_reset\">  &nbsp;&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 3949 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 3950 */                     out.write("\" onClick=\"history.back();\" class=\"buttons btn_link\">\n\t\t\t</td>\n\t\t\t<td><img src=\"/images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n</table>\n   <input type=\"hidden\" name='customFieldID' id=\"customFieldID\" value=\"\">\n   <input type=\"hidden\" name='customFieldValueID' id=\"customFieldValueID\" value=\"\">\n");
/* 3951 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3952 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3956 */                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3957 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                 }
/*      */                 
/* 3960 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3961 */                 out.write(10);
/* 3962 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3963 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 3966 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3967 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 3970 */             if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3971 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */             }
/*      */             
/* 3974 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3975 */             out.write(10);
/* 3976 */             if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/* 3978 */             out.write(10);
/* 3979 */             response.setContentType("text/html;charset=UTF-8");
/* 3980 */             out.write(10);
/* 3981 */             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3982 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 3986 */         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3987 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */         }
/*      */         
/* 3990 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3991 */         out.write(10);
/* 3992 */         out.write(10);
/* 3993 */       } catch (Exception ex) { ex.printStackTrace(); }
/* 3994 */       out.write(10);
/*      */     } catch (Throwable t) {
/* 3996 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3997 */         out = _jspx_out;
/* 3998 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3999 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4000 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4003 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4009 */     PageContext pageContext = _jspx_page_context;
/* 4010 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4012 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4013 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4014 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 4016 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 4018 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 4019 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4020 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4021 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4022 */       return true;
/*      */     }
/* 4024 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4025 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4030 */     PageContext pageContext = _jspx_page_context;
/* 4031 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4033 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4034 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4035 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 4037 */     _jspx_th_c_005fout_005f1.setValue("${growthTrend}");
/* 4038 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4039 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4040 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4041 */       return true;
/*      */     }
/* 4043 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4044 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4049 */     PageContext pageContext = _jspx_page_context;
/* 4050 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4052 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4053 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4054 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 4056 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 4058 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 4060 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 4061 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 4063 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4064 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 4066 */           out.write("\n  \t                 ");
/* 4067 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4068 */             return true;
/* 4069 */           out.write("\n  \t                 if(formCustomerId == customerId)\n  \t                 {\n  \t                         document.AMActionForm.haid.options[rowCount++] = new Option(siteName,siteId);\n  \t                 }\n  \t         ");
/* 4070 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4071 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4075 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 4076 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4079 */         int tmp195_194 = 0; int[] tmp195_192 = _jspx_push_body_count_c_005fforEach_005f0; int tmp197_196 = tmp195_192[tmp195_194];tmp195_192[tmp195_194] = (tmp197_196 - 1); if (tmp197_196 <= 0) break;
/* 4080 */         out = _jspx_page_context.popBody(); }
/* 4081 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4083 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4084 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 4086 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4091 */     PageContext pageContext = _jspx_page_context;
/* 4092 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4094 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4095 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4096 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4098 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 4100 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 4102 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 4103 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 4105 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4106 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 4108 */           out.write("\n  \t                         ");
/* 4109 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4110 */             return true;
/* 4111 */           out.write("\n  \t                         ");
/* 4112 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4113 */             return true;
/* 4114 */           out.write("\n  \t                         ");
/* 4115 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4116 */             return true;
/* 4117 */           out.write("\n  \t                 ");
/* 4118 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4119 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4123 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 4124 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4127 */         int tmp282_281 = 0; int[] tmp282_279 = _jspx_push_body_count_c_005fforEach_005f1; int tmp284_283 = tmp282_279[tmp282_281];tmp282_279[tmp282_281] = (tmp284_283 - 1); if (tmp284_283 <= 0) break;
/* 4128 */         out = _jspx_page_context.popBody(); }
/* 4129 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 4131 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4132 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 4134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4139 */     PageContext pageContext = _jspx_page_context;
/* 4140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4142 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4143 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4144 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4146 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 4147 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4148 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4150 */         out.write("\n  \t                                 siteName = '");
/* 4151 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4152 */           return true;
/* 4153 */         out.write("';\n  \t                         ");
/* 4154 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4155 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4159 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4160 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4161 */       return true;
/*      */     }
/* 4163 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4169 */     PageContext pageContext = _jspx_page_context;
/* 4170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4172 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4173 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4174 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4176 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 4177 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4178 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4179 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4180 */       return true;
/*      */     }
/* 4182 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4188 */     PageContext pageContext = _jspx_page_context;
/* 4189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4191 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4192 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4193 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4195 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 4196 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4197 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4199 */         out.write("\n  \t                                 siteId = '");
/* 4200 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4201 */           return true;
/* 4202 */         out.write("';\n  \t                         ");
/* 4203 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4204 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4208 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4209 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4210 */       return true;
/*      */     }
/* 4212 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4213 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4218 */     PageContext pageContext = _jspx_page_context;
/* 4219 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4221 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4222 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4223 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4225 */     _jspx_th_c_005fout_005f3.setValue("${b}");
/* 4226 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4227 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4228 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4229 */       return true;
/*      */     }
/* 4231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4237 */     PageContext pageContext = _jspx_page_context;
/* 4238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4240 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4241 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 4242 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4244 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 4245 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 4246 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 4248 */         out.write("\n  \t                                 customerId = '");
/* 4249 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4250 */           return true;
/* 4251 */         out.write("';\n  \t                         ");
/* 4252 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 4253 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4257 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 4258 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4259 */       return true;
/*      */     }
/* 4261 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4262 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4267 */     PageContext pageContext = _jspx_page_context;
/* 4268 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4270 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4271 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4272 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4274 */     _jspx_th_c_005fout_005f4.setValue("${b}");
/* 4275 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4276 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4277 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4278 */       return true;
/*      */     }
/* 4280 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4281 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4286 */     PageContext pageContext = _jspx_page_context;
/* 4287 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4289 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4290 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4291 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 4293 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 4294 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4295 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4297 */         out.write("\n\talertUser();\n\treturn false;\n");
/* 4298 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4299 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4303 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4304 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4305 */       return true;
/*      */     }
/* 4307 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4308 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4313 */     PageContext pageContext = _jspx_page_context;
/* 4314 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4316 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4317 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 4318 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 4320 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 4321 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 4322 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 4324 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t\t");
/* 4325 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 4326 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4330 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 4331 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4332 */       return true;
/*      */     }
/* 4334 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4335 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4340 */     PageContext pageContext = _jspx_page_context;
/* 4341 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4343 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 4344 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 4345 */     _jspx_th_bean_005fwrite_005f0.setParent(null);
/*      */     
/* 4347 */     _jspx_th_bean_005fwrite_005f0.setName("AMActionForm");
/*      */     
/* 4349 */     _jspx_th_bean_005fwrite_005f0.setProperty("taskMethod");
/* 4350 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 4351 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 4352 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 4353 */       return true;
/*      */     }
/* 4355 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 4356 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4361 */     PageContext pageContext = _jspx_page_context;
/* 4362 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4364 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 4365 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 4366 */     _jspx_th_bean_005fwrite_005f1.setParent(null);
/*      */     
/* 4368 */     _jspx_th_bean_005fwrite_005f1.setName("AMActionForm");
/*      */     
/* 4370 */     _jspx_th_bean_005fwrite_005f1.setProperty("taskMethod");
/* 4371 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 4372 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 4373 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 4374 */       return true;
/*      */     }
/* 4376 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 4377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4382 */     PageContext pageContext = _jspx_page_context;
/* 4383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4385 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4386 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4387 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4389 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4391 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 4392 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4393 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4394 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4395 */       return true;
/*      */     }
/* 4397 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4403 */     PageContext pageContext = _jspx_page_context;
/* 4404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4406 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4407 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4408 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4410 */     _jspx_th_c_005fout_005f5.setValue("${selectedskin}");
/*      */     
/* 4412 */     _jspx_th_c_005fout_005f5.setDefault("${initParam.defaultSkin}");
/* 4413 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4414 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4415 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4416 */       return true;
/*      */     }
/* 4418 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4419 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4424 */     PageContext pageContext = _jspx_page_context;
/* 4425 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4427 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4428 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4429 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4431 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.maintenance.scheduledetails");
/* 4432 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4433 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4434 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4435 */       return true;
/*      */     }
/* 4437 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4443 */     PageContext pageContext = _jspx_page_context;
/* 4444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4446 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonmouseout_005fonblur_005fmaxlength_005fnobody.get(TextTag.class);
/* 4447 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4448 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4450 */     _jspx_th_html_005ftext_005f0.setProperty("taskName");
/*      */     
/* 4452 */     _jspx_th_html_005ftext_005f0.setSize("46");
/*      */     
/* 4454 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext large");
/*      */     
/* 4456 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/*      */     
/* 4458 */     _jspx_th_html_005ftext_005f0.setOnblur("javascript:checkName(document.AMActionForm.taskName.value)");
/*      */     
/* 4460 */     _jspx_th_html_005ftext_005f0.setOnmouseout("hideddrivetip()");
/* 4461 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4462 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4463 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonmouseout_005fonblur_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4464 */       return true;
/*      */     }
/* 4466 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonmouseout_005fonblur_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4467 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4472 */     PageContext pageContext = _jspx_page_context;
/* 4473 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4475 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 4476 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 4477 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4479 */     _jspx_th_html_005ftextarea_005f0.setProperty("taskDescription");
/*      */     
/* 4481 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea xlarge");
/*      */     
/* 4483 */     _jspx_th_html_005ftextarea_005f0.setRows("3");
/*      */     
/* 4485 */     _jspx_th_html_005ftextarea_005f0.setCols("39");
/*      */     
/* 4487 */     _jspx_th_html_005ftextarea_005f0.setStyle("width:300px;");
/* 4488 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 4489 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 4490 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4491 */       return true;
/*      */     }
/* 4493 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4494 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4499 */     PageContext pageContext = _jspx_page_context;
/* 4500 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4502 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4503 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 4504 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4506 */     _jspx_th_html_005fradio_005f0.setProperty("taskStatus");
/*      */     
/* 4508 */     _jspx_th_html_005fradio_005f0.setValue("enable");
/* 4509 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 4510 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 4511 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4512 */       return true;
/*      */     }
/* 4514 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4515 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4520 */     PageContext pageContext = _jspx_page_context;
/* 4521 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4523 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4524 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 4525 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4527 */     _jspx_th_html_005fradio_005f1.setProperty("taskStatus");
/*      */     
/* 4529 */     _jspx_th_html_005fradio_005f1.setValue("disable");
/* 4530 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 4531 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 4532 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4533 */       return true;
/*      */     }
/* 4535 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4536 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4541 */     PageContext pageContext = _jspx_page_context;
/* 4542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4544 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4545 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4546 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4548 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.maintenance.scheduledetails");
/* 4549 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4550 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4551 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4552 */       return true;
/*      */     }
/* 4554 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4555 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4560 */     PageContext pageContext = _jspx_page_context;
/* 4561 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4563 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4564 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4565 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4567 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.schedulereport.newschedule.details.help");
/* 4568 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4569 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4570 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4571 */       return true;
/*      */     }
/* 4573 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4574 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4579 */     PageContext pageContext = _jspx_page_context;
/* 4580 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4582 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4583 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4584 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4586 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.schedulereport.showschedule.scheduleheading.text");
/* 4587 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4588 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4589 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4590 */       return true;
/*      */     }
/* 4592 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4593 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4598 */     PageContext pageContext = _jspx_page_context;
/* 4599 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4601 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4602 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 4603 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4605 */     _jspx_th_html_005fselect_005f0.setProperty("typeofreport");
/*      */     
/* 4607 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext default");
/*      */     
/* 4609 */     _jspx_th_html_005fselect_005f0.setOnchange("javascript:checkAttribute()");
/* 4610 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 4611 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 4612 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4613 */         out = _jspx_page_context.pushBody();
/* 4614 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 4615 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4618 */         out.write("\n\t               \t\t\t\t");
/* 4619 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 4620 */           return true;
/* 4621 */         out.write("\n\t\t \t\t\t\t     ");
/* 4622 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4623 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4626 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4627 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4630 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4631 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 4632 */       return true;
/*      */     }
/* 4634 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 4635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4640 */     PageContext pageContext = _jspx_page_context;
/* 4641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4643 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4644 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 4645 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 4647 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("reportTypes");
/* 4648 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 4649 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 4650 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4651 */       return true;
/*      */     }
/* 4653 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4659 */     PageContext pageContext = _jspx_page_context;
/* 4660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4662 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4663 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 4664 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4666 */     _jspx_th_html_005fselect_005f1.setProperty("typeofattribute");
/*      */     
/* 4668 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext default");
/*      */     
/* 4670 */     _jspx_th_html_005fselect_005f1.setOnchange("javascript:checkAttribute()");
/* 4671 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 4672 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 4673 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4674 */         out = _jspx_page_context.pushBody();
/* 4675 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 4676 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4679 */         out.write("\n\t\t\t\t\t               ");
/* 4680 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 4681 */           return true;
/* 4682 */         out.write("\n\t\t\t\t\t\t ");
/* 4683 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 4684 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4687 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4688 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4691 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 4692 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 4693 */       return true;
/*      */     }
/* 4695 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 4696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4701 */     PageContext pageContext = _jspx_page_context;
/* 4702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4704 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4705 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 4706 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 4708 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("reportAttribute");
/* 4709 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 4710 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 4711 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 4712 */       return true;
/*      */     }
/* 4714 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 4715 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4720 */     PageContext pageContext = _jspx_page_context;
/* 4721 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4723 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4724 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 4725 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4727 */     _jspx_th_html_005fselect_005f2.setProperty("typeofperformance");
/*      */     
/* 4729 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext default");
/*      */     
/* 4731 */     _jspx_th_html_005fselect_005f2.setOnchange("javascript:checkAttribute()");
/* 4732 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 4733 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 4734 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4735 */         out = _jspx_page_context.pushBody();
/* 4736 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 4737 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4740 */         out.write("\n\t\t\t\t\t               ");
/* 4741 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 4742 */           return true;
/* 4743 */         out.write("\n\t\t\t\t\t\t ");
/* 4744 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 4745 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4748 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4749 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4752 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 4753 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 4754 */       return true;
/*      */     }
/* 4756 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 4757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4762 */     PageContext pageContext = _jspx_page_context;
/* 4763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4765 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4766 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 4767 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 4769 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("reportPerformance");
/* 4770 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 4771 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 4772 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 4773 */       return true;
/*      */     }
/* 4775 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 4776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4781 */     PageContext pageContext = _jspx_page_context;
/* 4782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4784 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4785 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 4786 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4788 */     _jspx_th_html_005fselect_005f3.setProperty("typeofforecastattribute");
/*      */     
/* 4790 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext default");
/*      */     
/* 4792 */     _jspx_th_html_005fselect_005f3.setOnchange("javascript:checkAttribute()");
/* 4793 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 4794 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 4795 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 4796 */         out = _jspx_page_context.pushBody();
/* 4797 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 4798 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4801 */         out.write("\n\t\t\t\t\t               ");
/* 4802 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 4803 */           return true;
/* 4804 */         out.write("\n\t\t\t\t\t\t ");
/* 4805 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 4806 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4809 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 4810 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4813 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 4814 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 4815 */       return true;
/*      */     }
/* 4817 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 4818 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4823 */     PageContext pageContext = _jspx_page_context;
/* 4824 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4826 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4827 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 4828 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 4830 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("forecastReportAttribute");
/* 4831 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 4832 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 4833 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 4834 */       return true;
/*      */     }
/* 4836 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 4837 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4842 */     PageContext pageContext = _jspx_page_context;
/* 4843 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4845 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.get(SelectTag.class);
/* 4846 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 4847 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4849 */     _jspx_th_html_005fselect_005f4.setProperty("typeofperiod");
/*      */     
/* 4851 */     _jspx_th_html_005fselect_005f4.setStyleClass("formtext medium");
/*      */     
/* 4853 */     _jspx_th_html_005fselect_005f4.setStyleId("per");
/* 4854 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 4855 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 4856 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 4857 */         out = _jspx_page_context.pushBody();
/* 4858 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 4859 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4862 */         out.write("\n\t\t\t\t             \t  \t\t");
/* 4863 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 4864 */           return true;
/* 4865 */         out.write("\n\t\t\t\t             \t  \t ");
/* 4866 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 4867 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4870 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 4871 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4874 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 4875 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 4876 */       return true;
/*      */     }
/* 4878 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 4879 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4884 */     PageContext pageContext = _jspx_page_context;
/* 4885 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4887 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4888 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 4889 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 4891 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("reportPeriod");
/* 4892 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 4893 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 4894 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 4895 */       return true;
/*      */     }
/* 4897 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 4898 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4903 */     PageContext pageContext = _jspx_page_context;
/* 4904 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4906 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 4907 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 4908 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4910 */     _jspx_th_html_005fselect_005f5.setProperty("typeofperformanceperiod");
/*      */     
/* 4912 */     _jspx_th_html_005fselect_005f5.setStyleClass("formtext medium");
/* 4913 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 4914 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 4915 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 4916 */         out = _jspx_page_context.pushBody();
/* 4917 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 4918 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4921 */         out.write("\n               \t\t\t\t\t\t");
/* 4922 */         if (_jspx_meth_html_005foptionsCollection_005f5(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 4923 */           return true;
/* 4924 */         out.write("\n\t \t\t\t\t\t\t\t");
/* 4925 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 4926 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4929 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 4930 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4933 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 4934 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 4935 */       return true;
/*      */     }
/* 4937 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 4938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f5(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4943 */     PageContext pageContext = _jspx_page_context;
/* 4944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4946 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f5 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4947 */     _jspx_th_html_005foptionsCollection_005f5.setPageContext(_jspx_page_context);
/* 4948 */     _jspx_th_html_005foptionsCollection_005f5.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 4950 */     _jspx_th_html_005foptionsCollection_005f5.setProperty("reportPeriodForPerformance");
/* 4951 */     int _jspx_eval_html_005foptionsCollection_005f5 = _jspx_th_html_005foptionsCollection_005f5.doStartTag();
/* 4952 */     if (_jspx_th_html_005foptionsCollection_005f5.doEndTag() == 5) {
/* 4953 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 4954 */       return true;
/*      */     }
/* 4956 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 4957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4962 */     PageContext pageContext = _jspx_page_context;
/* 4963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4965 */     SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.get(SelectTag.class);
/* 4966 */     _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 4967 */     _jspx_th_html_005fselect_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4969 */     _jspx_th_html_005fselect_005f6.setProperty("forecastTypeofperiod");
/*      */     
/* 4971 */     _jspx_th_html_005fselect_005f6.setStyleClass("formtext medium");
/*      */     
/* 4973 */     _jspx_th_html_005fselect_005f6.setStyleId("per");
/* 4974 */     int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 4975 */     if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 4976 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 4977 */         out = _jspx_page_context.pushBody();
/* 4978 */         _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 4979 */         _jspx_th_html_005fselect_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4982 */         out.write("\n\t\t\t\t             \t  \t\t");
/* 4983 */         if (_jspx_meth_html_005foptionsCollection_005f6(_jspx_th_html_005fselect_005f6, _jspx_page_context))
/* 4984 */           return true;
/* 4985 */         out.write("\n\t\t\t\t             \t");
/* 4986 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 4987 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4990 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 4991 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4994 */     if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 4995 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 4996 */       return true;
/*      */     }
/* 4998 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 4999 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f6(JspTag _jspx_th_html_005fselect_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5004 */     PageContext pageContext = _jspx_page_context;
/* 5005 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5007 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f6 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 5008 */     _jspx_th_html_005foptionsCollection_005f6.setPageContext(_jspx_page_context);
/* 5009 */     _jspx_th_html_005foptionsCollection_005f6.setParent((Tag)_jspx_th_html_005fselect_005f6);
/*      */     
/* 5011 */     _jspx_th_html_005foptionsCollection_005f6.setProperty("forecastReportPeriod");
/* 5012 */     int _jspx_eval_html_005foptionsCollection_005f6 = _jspx_th_html_005foptionsCollection_005f6.doStartTag();
/* 5013 */     if (_jspx_th_html_005foptionsCollection_005f6.doEndTag() == 5) {
/* 5014 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 5015 */       return true;
/*      */     }
/* 5017 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 5018 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5023 */     PageContext pageContext = _jspx_page_context;
/* 5024 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5026 */     SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5027 */     _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 5028 */     _jspx_th_html_005fselect_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5030 */     _jspx_th_html_005fselect_005f7.setProperty("typeofoutageperiod");
/*      */     
/* 5032 */     _jspx_th_html_005fselect_005f7.setStyleClass("formtext medium");
/* 5033 */     int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 5034 */     if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 5035 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 5036 */         out = _jspx_page_context.pushBody();
/* 5037 */         _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 5038 */         _jspx_th_html_005fselect_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5041 */         out.write("\n              \t\t\t\t\t \t\t");
/* 5042 */         if (_jspx_meth_html_005foptionsCollection_005f7(_jspx_th_html_005fselect_005f7, _jspx_page_context))
/* 5043 */           return true;
/* 5044 */         out.write("\n\t \t\t\t\t\t\t");
/* 5045 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 5046 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5049 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 5050 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5053 */     if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 5054 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f7);
/* 5055 */       return true;
/*      */     }
/* 5057 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f7);
/* 5058 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f7(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5063 */     PageContext pageContext = _jspx_page_context;
/* 5064 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5066 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f7 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 5067 */     _jspx_th_html_005foptionsCollection_005f7.setPageContext(_jspx_page_context);
/* 5068 */     _jspx_th_html_005foptionsCollection_005f7.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*      */     
/* 5070 */     _jspx_th_html_005foptionsCollection_005f7.setProperty("reportPeriodForOutage");
/* 5071 */     int _jspx_eval_html_005foptionsCollection_005f7 = _jspx_th_html_005foptionsCollection_005f7.doStartTag();
/* 5072 */     if (_jspx_th_html_005foptionsCollection_005f7.doEndTag() == 5) {
/* 5073 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 5074 */       return true;
/*      */     }
/* 5076 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 5077 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5082 */     PageContext pageContext = _jspx_page_context;
/* 5083 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5085 */     SelectTag _jspx_th_html_005fselect_005f8 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5086 */     _jspx_th_html_005fselect_005f8.setPageContext(_jspx_page_context);
/* 5087 */     _jspx_th_html_005fselect_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5089 */     _jspx_th_html_005fselect_005f8.setProperty("typeoftrendperiod");
/*      */     
/* 5091 */     _jspx_th_html_005fselect_005f8.setStyleClass("formtext medium");
/* 5092 */     int _jspx_eval_html_005fselect_005f8 = _jspx_th_html_005fselect_005f8.doStartTag();
/* 5093 */     if (_jspx_eval_html_005fselect_005f8 != 0) {
/* 5094 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 5095 */         out = _jspx_page_context.pushBody();
/* 5096 */         _jspx_th_html_005fselect_005f8.setBodyContent((BodyContent)out);
/* 5097 */         _jspx_th_html_005fselect_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5100 */         out.write("\n               \t\t\t\t\t\t\t");
/* 5101 */         if (_jspx_meth_html_005foptionsCollection_005f8(_jspx_th_html_005fselect_005f8, _jspx_page_context))
/* 5102 */           return true;
/* 5103 */         out.write("\n\t \t\t\t\t\t\t");
/* 5104 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f8.doAfterBody();
/* 5105 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5108 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 5109 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5112 */     if (_jspx_th_html_005fselect_005f8.doEndTag() == 5) {
/* 5113 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f8);
/* 5114 */       return true;
/*      */     }
/* 5116 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f8);
/* 5117 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f8(JspTag _jspx_th_html_005fselect_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5122 */     PageContext pageContext = _jspx_page_context;
/* 5123 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5125 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f8 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 5126 */     _jspx_th_html_005foptionsCollection_005f8.setPageContext(_jspx_page_context);
/* 5127 */     _jspx_th_html_005foptionsCollection_005f8.setParent((Tag)_jspx_th_html_005fselect_005f8);
/*      */     
/* 5129 */     _jspx_th_html_005foptionsCollection_005f8.setProperty("reportPeriodForAvailabilityTrend");
/* 5130 */     int _jspx_eval_html_005foptionsCollection_005f8 = _jspx_th_html_005foptionsCollection_005f8.doStartTag();
/* 5131 */     if (_jspx_th_html_005foptionsCollection_005f8.doEndTag() == 5) {
/* 5132 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f8);
/* 5133 */       return true;
/*      */     }
/* 5135 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f8);
/* 5136 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5141 */     PageContext pageContext = _jspx_page_context;
/* 5142 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5144 */     SelectTag _jspx_th_html_005fselect_005f9 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5145 */     _jspx_th_html_005fselect_005f9.setPageContext(_jspx_page_context);
/* 5146 */     _jspx_th_html_005fselect_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5148 */     _jspx_th_html_005fselect_005f9.setProperty("businessPeriod");
/*      */     
/* 5150 */     _jspx_th_html_005fselect_005f9.setStyleClass("formtext default");
/*      */     
/* 5152 */     _jspx_th_html_005fselect_005f9.setStyleId("per");
/* 5153 */     int _jspx_eval_html_005fselect_005f9 = _jspx_th_html_005fselect_005f9.doStartTag();
/* 5154 */     if (_jspx_eval_html_005fselect_005f9 != 0) {
/* 5155 */       if (_jspx_eval_html_005fselect_005f9 != 1) {
/* 5156 */         out = _jspx_page_context.pushBody();
/* 5157 */         _jspx_th_html_005fselect_005f9.setBodyContent((BodyContent)out);
/* 5158 */         _jspx_th_html_005fselect_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5161 */         out.write("\n\t\t\t\t\t\t\t               ");
/* 5162 */         if (_jspx_meth_html_005foptionsCollection_005f9(_jspx_th_html_005fselect_005f9, _jspx_page_context))
/* 5163 */           return true;
/* 5164 */         out.write("\n\t \t\t\t\t\t\t\t\t\t\t");
/* 5165 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f9.doAfterBody();
/* 5166 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5169 */       if (_jspx_eval_html_005fselect_005f9 != 1) {
/* 5170 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5173 */     if (_jspx_th_html_005fselect_005f9.doEndTag() == 5) {
/* 5174 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f9);
/* 5175 */       return true;
/*      */     }
/* 5177 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f9);
/* 5178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f9(JspTag _jspx_th_html_005fselect_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5183 */     PageContext pageContext = _jspx_page_context;
/* 5184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5186 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f9 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 5187 */     _jspx_th_html_005foptionsCollection_005f9.setPageContext(_jspx_page_context);
/* 5188 */     _jspx_th_html_005foptionsCollection_005f9.setParent((Tag)_jspx_th_html_005fselect_005f9);
/*      */     
/* 5190 */     _jspx_th_html_005foptionsCollection_005f9.setProperty("businessRuleNames");
/* 5191 */     int _jspx_eval_html_005foptionsCollection_005f9 = _jspx_th_html_005foptionsCollection_005f9.doStartTag();
/* 5192 */     if (_jspx_th_html_005foptionsCollection_005f9.doEndTag() == 5) {
/* 5193 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f9);
/* 5194 */       return true;
/*      */     }
/* 5196 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f9);
/* 5197 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5202 */     PageContext pageContext = _jspx_page_context;
/* 5203 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5205 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5206 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 5207 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5209 */     _jspx_th_html_005fradio_005f2.setProperty("slatype");
/*      */     
/* 5211 */     _jspx_th_html_005fradio_005f2.setValue("allba");
/*      */     
/* 5213 */     _jspx_th_html_005fradio_005f2.setOnclick("javascript:constructSLAUrl()");
/* 5214 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 5215 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 5216 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 5217 */       return true;
/*      */     }
/* 5219 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 5220 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5225 */     PageContext pageContext = _jspx_page_context;
/* 5226 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5228 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5229 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 5230 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5232 */     _jspx_th_html_005fradio_005f3.setProperty("slatype");
/*      */     
/* 5234 */     _jspx_th_html_005fradio_005f3.setValue("slaba");
/*      */     
/* 5236 */     _jspx_th_html_005fradio_005f3.setOnclick("javascript:constructSLAUrl()");
/* 5237 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 5238 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 5239 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 5240 */       return true;
/*      */     }
/* 5242 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 5243 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5248 */     PageContext pageContext = _jspx_page_context;
/* 5249 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5251 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5252 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 5253 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5255 */     _jspx_th_html_005fradio_005f4.setProperty("slatype");
/*      */     
/* 5257 */     _jspx_th_html_005fradio_005f4.setValue("allserver");
/*      */     
/* 5259 */     _jspx_th_html_005fradio_005f4.setOnclick("javascript:constructSLAUrl()");
/* 5260 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 5261 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 5262 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 5263 */       return true;
/*      */     }
/* 5265 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 5266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5271 */     PageContext pageContext = _jspx_page_context;
/* 5272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5274 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5275 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 5276 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5278 */     _jspx_th_html_005fradio_005f5.setProperty("slatype");
/*      */     
/* 5280 */     _jspx_th_html_005fradio_005f5.setValue("slaserver");
/*      */     
/* 5282 */     _jspx_th_html_005fradio_005f5.setOnclick("javascript:constructSLAUrl()");
/* 5283 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 5284 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 5285 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 5286 */       return true;
/*      */     }
/* 5288 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 5289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5294 */     PageContext pageContext = _jspx_page_context;
/* 5295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5297 */     RadioTag _jspx_th_html_005fradio_005f6 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5298 */     _jspx_th_html_005fradio_005f6.setPageContext(_jspx_page_context);
/* 5299 */     _jspx_th_html_005fradio_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5301 */     _jspx_th_html_005fradio_005f6.setProperty("reportmonitor");
/*      */     
/* 5303 */     _jspx_th_html_005fradio_005f6.setValue("monitorgroup");
/*      */     
/* 5305 */     _jspx_th_html_005fradio_005f6.setOnclick("javascript:constructUrl()");
/* 5306 */     int _jspx_eval_html_005fradio_005f6 = _jspx_th_html_005fradio_005f6.doStartTag();
/* 5307 */     if (_jspx_th_html_005fradio_005f6.doEndTag() == 5) {
/* 5308 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 5309 */       return true;
/*      */     }
/* 5311 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 5312 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5317 */     PageContext pageContext = _jspx_page_context;
/* 5318 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5320 */     RadioTag _jspx_th_html_005fradio_005f7 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5321 */     _jspx_th_html_005fradio_005f7.setPageContext(_jspx_page_context);
/* 5322 */     _jspx_th_html_005fradio_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5324 */     _jspx_th_html_005fradio_005f7.setProperty("reportmonitor");
/*      */     
/* 5326 */     _jspx_th_html_005fradio_005f7.setValue("monitor");
/*      */     
/* 5328 */     _jspx_th_html_005fradio_005f7.setOnclick("javascript:constructUrl()");
/* 5329 */     int _jspx_eval_html_005fradio_005f7 = _jspx_th_html_005fradio_005f7.doStartTag();
/* 5330 */     if (_jspx_th_html_005fradio_005f7.doEndTag() == 5) {
/* 5331 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 5332 */       return true;
/*      */     }
/* 5334 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 5335 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5340 */     PageContext pageContext = _jspx_page_context;
/* 5341 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5343 */     RadioTag _jspx_th_html_005fradio_005f8 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5344 */     _jspx_th_html_005fradio_005f8.setPageContext(_jspx_page_context);
/* 5345 */     _jspx_th_html_005fradio_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5347 */     _jspx_th_html_005fradio_005f8.setProperty("reportmonitor");
/*      */     
/* 5349 */     _jspx_th_html_005fradio_005f8.setValue("indimonitor");
/*      */     
/* 5351 */     _jspx_th_html_005fradio_005f8.setOnclick("javascript:constructUrl()");
/* 5352 */     int _jspx_eval_html_005fradio_005f8 = _jspx_th_html_005fradio_005f8.doStartTag();
/* 5353 */     if (_jspx_th_html_005fradio_005f8.doEndTag() == 5) {
/* 5354 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 5355 */       return true;
/*      */     }
/* 5357 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 5358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5363 */     PageContext pageContext = _jspx_page_context;
/* 5364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5366 */     SelectTag _jspx_th_html_005fselect_005f12 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5367 */     _jspx_th_html_005fselect_005f12.setPageContext(_jspx_page_context);
/* 5368 */     _jspx_th_html_005fselect_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5370 */     _jspx_th_html_005fselect_005f12.setProperty("selectedEumInSchedule");
/*      */     
/* 5372 */     _jspx_th_html_005fselect_005f12.setStyleClass("formtext normal");
/*      */     
/* 5374 */     _jspx_th_html_005fselect_005f12.setOnchange("javascript:checkAttribute()");
/* 5375 */     int _jspx_eval_html_005fselect_005f12 = _jspx_th_html_005fselect_005f12.doStartTag();
/* 5376 */     if (_jspx_eval_html_005fselect_005f12 != 0) {
/* 5377 */       if (_jspx_eval_html_005fselect_005f12 != 1) {
/* 5378 */         out = _jspx_page_context.pushBody();
/* 5379 */         _jspx_th_html_005fselect_005f12.setBodyContent((BodyContent)out);
/* 5380 */         _jspx_th_html_005fselect_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5383 */         out.write("\n\t\t\t\t\t\t\t \t \t\t\t ");
/* 5384 */         if (_jspx_meth_html_005foptionsCollection_005f10(_jspx_th_html_005fselect_005f12, _jspx_page_context))
/* 5385 */           return true;
/* 5386 */         out.write("\n\t\t\t\t\t\t\t \t \t\t");
/* 5387 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f12.doAfterBody();
/* 5388 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5391 */       if (_jspx_eval_html_005fselect_005f12 != 1) {
/* 5392 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5395 */     if (_jspx_th_html_005fselect_005f12.doEndTag() == 5) {
/* 5396 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f12);
/* 5397 */       return true;
/*      */     }
/* 5399 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f12);
/* 5400 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f10(JspTag _jspx_th_html_005fselect_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5405 */     PageContext pageContext = _jspx_page_context;
/* 5406 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5408 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f10 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 5409 */     _jspx_th_html_005foptionsCollection_005f10.setPageContext(_jspx_page_context);
/* 5410 */     _jspx_th_html_005foptionsCollection_005f10.setParent((Tag)_jspx_th_html_005fselect_005f12);
/*      */     
/* 5412 */     _jspx_th_html_005foptionsCollection_005f10.setProperty("eumResTypes");
/* 5413 */     int _jspx_eval_html_005foptionsCollection_005f10 = _jspx_th_html_005foptionsCollection_005f10.doStartTag();
/* 5414 */     if (_jspx_th_html_005foptionsCollection_005f10.doEndTag() == 5) {
/* 5415 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f10);
/* 5416 */       return true;
/*      */     }
/* 5418 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f10);
/* 5419 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5424 */     PageContext pageContext = _jspx_page_context;
/* 5425 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5427 */     SelectTag _jspx_th_html_005fselect_005f13 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5428 */     _jspx_th_html_005fselect_005f13.setPageContext(_jspx_page_context);
/* 5429 */     _jspx_th_html_005fselect_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5431 */     _jspx_th_html_005fselect_005f13.setProperty("trapName");
/*      */     
/* 5433 */     _jspx_th_html_005fselect_005f13.setStyleClass("formtext chosenselect");
/*      */     
/* 5435 */     _jspx_th_html_005fselect_005f13.setOnchange("javascript:checkAttribute()");
/* 5436 */     int _jspx_eval_html_005fselect_005f13 = _jspx_th_html_005fselect_005f13.doStartTag();
/* 5437 */     if (_jspx_eval_html_005fselect_005f13 != 0) {
/* 5438 */       if (_jspx_eval_html_005fselect_005f13 != 1) {
/* 5439 */         out = _jspx_page_context.pushBody();
/* 5440 */         _jspx_th_html_005fselect_005f13.setBodyContent((BodyContent)out);
/* 5441 */         _jspx_th_html_005fselect_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5444 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t               ");
/* 5445 */         if (_jspx_meth_html_005foptionsCollection_005f11(_jspx_th_html_005fselect_005f13, _jspx_page_context))
/* 5446 */           return true;
/* 5447 */         out.write("\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t \t\t  \t");
/* 5448 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f13.doAfterBody();
/* 5449 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5452 */       if (_jspx_eval_html_005fselect_005f13 != 1) {
/* 5453 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5456 */     if (_jspx_th_html_005fselect_005f13.doEndTag() == 5) {
/* 5457 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f13);
/* 5458 */       return true;
/*      */     }
/* 5460 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f13);
/* 5461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f11(JspTag _jspx_th_html_005fselect_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5466 */     PageContext pageContext = _jspx_page_context;
/* 5467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5469 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f11 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 5470 */     _jspx_th_html_005foptionsCollection_005f11.setPageContext(_jspx_page_context);
/* 5471 */     _jspx_th_html_005foptionsCollection_005f11.setParent((Tag)_jspx_th_html_005fselect_005f13);
/*      */     
/* 5473 */     _jspx_th_html_005foptionsCollection_005f11.setProperty("resourcesForReports");
/* 5474 */     int _jspx_eval_html_005foptionsCollection_005f11 = _jspx_th_html_005foptionsCollection_005f11.doStartTag();
/* 5475 */     if (_jspx_th_html_005foptionsCollection_005f11.doEndTag() == 5) {
/* 5476 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f11);
/* 5477 */       return true;
/*      */     }
/* 5479 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f11);
/* 5480 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5485 */     PageContext pageContext = _jspx_page_context;
/* 5486 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5488 */     SelectTag _jspx_th_html_005fselect_005f14 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5489 */     _jspx_th_html_005fselect_005f14.setPageContext(_jspx_page_context);
/* 5490 */     _jspx_th_html_005fselect_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5492 */     _jspx_th_html_005fselect_005f14.setProperty("selectedMSSQLResource");
/*      */     
/* 5494 */     _jspx_th_html_005fselect_005f14.setStyleId("selectedMSSQLResource");
/*      */     
/* 5496 */     _jspx_th_html_005fselect_005f14.setStyleClass("formtext default");
/*      */     
/* 5498 */     _jspx_th_html_005fselect_005f14.setOnchange("javascript:loadDBs(this.value);");
/* 5499 */     int _jspx_eval_html_005fselect_005f14 = _jspx_th_html_005fselect_005f14.doStartTag();
/* 5500 */     if (_jspx_eval_html_005fselect_005f14 != 0) {
/* 5501 */       if (_jspx_eval_html_005fselect_005f14 != 1) {
/* 5502 */         out = _jspx_page_context.pushBody();
/* 5503 */         _jspx_th_html_005fselect_005f14.setBodyContent((BodyContent)out);
/* 5504 */         _jspx_th_html_005fselect_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5507 */         out.write("\n\t\t\t\t\t\t");
/* 5508 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f14.doAfterBody();
/* 5509 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5512 */       if (_jspx_eval_html_005fselect_005f14 != 1) {
/* 5513 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5516 */     if (_jspx_th_html_005fselect_005f14.doEndTag() == 5) {
/* 5517 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f14);
/* 5518 */       return true;
/*      */     }
/* 5520 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f14);
/* 5521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5526 */     PageContext pageContext = _jspx_page_context;
/* 5527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5529 */     SelectTag _jspx_th_html_005fselect_005f15 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5530 */     _jspx_th_html_005fselect_005f15.setPageContext(_jspx_page_context);
/* 5531 */     _jspx_th_html_005fselect_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5533 */     _jspx_th_html_005fselect_005f15.setProperty("sqlDBforPerformance");
/*      */     
/* 5535 */     _jspx_th_html_005fselect_005f15.setStyleId("sqlDBforPerformance");
/*      */     
/* 5537 */     _jspx_th_html_005fselect_005f15.setStyleClass("formtext default");
/* 5538 */     int _jspx_eval_html_005fselect_005f15 = _jspx_th_html_005fselect_005f15.doStartTag();
/* 5539 */     if (_jspx_eval_html_005fselect_005f15 != 0) {
/* 5540 */       if (_jspx_eval_html_005fselect_005f15 != 1) {
/* 5541 */         out = _jspx_page_context.pushBody();
/* 5542 */         _jspx_th_html_005fselect_005f15.setBodyContent((BodyContent)out);
/* 5543 */         _jspx_th_html_005fselect_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5546 */         out.write("\n\t\t\t\t\t\t");
/* 5547 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f15.doAfterBody();
/* 5548 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5551 */       if (_jspx_eval_html_005fselect_005f15 != 1) {
/* 5552 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5555 */     if (_jspx_th_html_005fselect_005f15.doEndTag() == 5) {
/* 5556 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f15);
/* 5557 */       return true;
/*      */     }
/* 5559 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f15);
/* 5560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5565 */     PageContext pageContext = _jspx_page_context;
/* 5566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5568 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5569 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 5570 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5572 */     _jspx_th_html_005fhidden_005f0.setProperty("sqlDBPerf");
/* 5573 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 5574 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 5575 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5576 */       return true;
/*      */     }
/* 5578 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5579 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5584 */     PageContext pageContext = _jspx_page_context;
/* 5585 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5587 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5588 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 5589 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5591 */     _jspx_th_html_005fhidden_005f1.setProperty("sqlServerPerf");
/* 5592 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 5593 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 5594 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5595 */       return true;
/*      */     }
/* 5597 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 5598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5603 */     PageContext pageContext = _jspx_page_context;
/* 5604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5606 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5607 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5608 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5610 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.schedulereport.showschedule.scheduleheading.text");
/* 5611 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5612 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 5613 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5614 */       return true;
/*      */     }
/* 5616 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 5617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5622 */     PageContext pageContext = _jspx_page_context;
/* 5623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5625 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5626 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 5627 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5629 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.schedulereport.newschedule.reportdetails.help");
/* 5630 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 5631 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 5632 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5633 */       return true;
/*      */     }
/* 5635 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 5636 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5641 */     PageContext pageContext = _jspx_page_context;
/* 5642 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5644 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5645 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 5646 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5648 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.maintenance.recurrencedetails");
/* 5649 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 5650 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 5651 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5652 */       return true;
/*      */     }
/* 5654 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 5655 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5660 */     PageContext pageContext = _jspx_page_context;
/* 5661 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5663 */     RadioTag _jspx_th_html_005fradio_005f9 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5664 */     _jspx_th_html_005fradio_005f9.setPageContext(_jspx_page_context);
/* 5665 */     _jspx_th_html_005fradio_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5667 */     _jspx_th_html_005fradio_005f9.setProperty("taskMethod");
/*      */     
/* 5669 */     _jspx_th_html_005fradio_005f9.setValue("Daily");
/*      */     
/* 5671 */     _jspx_th_html_005fradio_005f9.setOnclick("hideDiv('view_weekly$view_custom');");
/* 5672 */     int _jspx_eval_html_005fradio_005f9 = _jspx_th_html_005fradio_005f9.doStartTag();
/* 5673 */     if (_jspx_th_html_005fradio_005f9.doEndTag() == 5) {
/* 5674 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 5675 */       return true;
/*      */     }
/* 5677 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 5678 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5683 */     PageContext pageContext = _jspx_page_context;
/* 5684 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5686 */     RadioTag _jspx_th_html_005fradio_005f10 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5687 */     _jspx_th_html_005fradio_005f10.setPageContext(_jspx_page_context);
/* 5688 */     _jspx_th_html_005fradio_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5690 */     _jspx_th_html_005fradio_005f10.setProperty("taskMethod");
/*      */     
/* 5692 */     _jspx_th_html_005fradio_005f10.setValue("Weekly");
/*      */     
/* 5694 */     _jspx_th_html_005fradio_005f10.setOnclick("javascript:showDiv('view_weekly'),hideDiv('view_custom');");
/* 5695 */     int _jspx_eval_html_005fradio_005f10 = _jspx_th_html_005fradio_005f10.doStartTag();
/* 5696 */     if (_jspx_th_html_005fradio_005f10.doEndTag() == 5) {
/* 5697 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 5698 */       return true;
/*      */     }
/* 5700 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f10);
/* 5701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5706 */     PageContext pageContext = _jspx_page_context;
/* 5707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5709 */     RadioTag _jspx_th_html_005fradio_005f11 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5710 */     _jspx_th_html_005fradio_005f11.setPageContext(_jspx_page_context);
/* 5711 */     _jspx_th_html_005fradio_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5713 */     _jspx_th_html_005fradio_005f11.setProperty("taskMethod");
/*      */     
/* 5715 */     _jspx_th_html_005fradio_005f11.setValue("Monthly");
/*      */     
/* 5717 */     _jspx_th_html_005fradio_005f11.setOnclick("javascript:showDiv('view_custom'),hideDiv('view_weekly');");
/* 5718 */     int _jspx_eval_html_005fradio_005f11 = _jspx_th_html_005fradio_005f11.doStartTag();
/* 5719 */     if (_jspx_th_html_005fradio_005f11.doEndTag() == 5) {
/* 5720 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 5721 */       return true;
/*      */     }
/* 5723 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f11);
/* 5724 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5729 */     PageContext pageContext = _jspx_page_context;
/* 5730 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5732 */     SelectTag _jspx_th_html_005fselect_005f16 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5733 */     _jspx_th_html_005fselect_005f16.setPageContext(_jspx_page_context);
/* 5734 */     _jspx_th_html_005fselect_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5736 */     _jspx_th_html_005fselect_005f16.setProperty("dailyhour");
/*      */     
/* 5738 */     _jspx_th_html_005fselect_005f16.setStyleClass("formtext msmall");
/* 5739 */     int _jspx_eval_html_005fselect_005f16 = _jspx_th_html_005fselect_005f16.doStartTag();
/* 5740 */     if (_jspx_eval_html_005fselect_005f16 != 0) {
/* 5741 */       if (_jspx_eval_html_005fselect_005f16 != 1) {
/* 5742 */         out = _jspx_page_context.pushBody();
/* 5743 */         _jspx_th_html_005fselect_005f16.setBodyContent((BodyContent)out);
/* 5744 */         _jspx_th_html_005fselect_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5747 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5748 */         if (_jspx_meth_html_005foptionsCollection_005f12(_jspx_th_html_005fselect_005f16, _jspx_page_context))
/* 5749 */           return true;
/* 5750 */         out.write("\n\t\t\t\t \t\t\t\t\t\t ");
/* 5751 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f16.doAfterBody();
/* 5752 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5755 */       if (_jspx_eval_html_005fselect_005f16 != 1) {
/* 5756 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5759 */     if (_jspx_th_html_005fselect_005f16.doEndTag() == 5) {
/* 5760 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f16);
/* 5761 */       return true;
/*      */     }
/* 5763 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f16);
/* 5764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f12(JspTag _jspx_th_html_005fselect_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5769 */     PageContext pageContext = _jspx_page_context;
/* 5770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5772 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f12 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 5773 */     _jspx_th_html_005foptionsCollection_005f12.setPageContext(_jspx_page_context);
/* 5774 */     _jspx_th_html_005foptionsCollection_005f12.setParent((Tag)_jspx_th_html_005fselect_005f16);
/*      */     
/* 5776 */     _jspx_th_html_005foptionsCollection_005f12.setProperty("hour");
/* 5777 */     int _jspx_eval_html_005foptionsCollection_005f12 = _jspx_th_html_005foptionsCollection_005f12.doStartTag();
/* 5778 */     if (_jspx_th_html_005foptionsCollection_005f12.doEndTag() == 5) {
/* 5779 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f12);
/* 5780 */       return true;
/*      */     }
/* 5782 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f12);
/* 5783 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5788 */     PageContext pageContext = _jspx_page_context;
/* 5789 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5791 */     SelectTag _jspx_th_html_005fselect_005f17 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5792 */     _jspx_th_html_005fselect_005f17.setPageContext(_jspx_page_context);
/* 5793 */     _jspx_th_html_005fselect_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5795 */     _jspx_th_html_005fselect_005f17.setProperty("dailyminute");
/*      */     
/* 5797 */     _jspx_th_html_005fselect_005f17.setStyleClass("formtext msmall");
/* 5798 */     int _jspx_eval_html_005fselect_005f17 = _jspx_th_html_005fselect_005f17.doStartTag();
/* 5799 */     if (_jspx_eval_html_005fselect_005f17 != 0) {
/* 5800 */       if (_jspx_eval_html_005fselect_005f17 != 1) {
/* 5801 */         out = _jspx_page_context.pushBody();
/* 5802 */         _jspx_th_html_005fselect_005f17.setBodyContent((BodyContent)out);
/* 5803 */         _jspx_th_html_005fselect_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5806 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 5807 */         if (_jspx_meth_html_005foptionsCollection_005f13(_jspx_th_html_005fselect_005f17, _jspx_page_context))
/* 5808 */           return true;
/* 5809 */         out.write("\n\t\t\t\t  \t\t\t\t\t\t");
/* 5810 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f17.doAfterBody();
/* 5811 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5814 */       if (_jspx_eval_html_005fselect_005f17 != 1) {
/* 5815 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5818 */     if (_jspx_th_html_005fselect_005f17.doEndTag() == 5) {
/* 5819 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f17);
/* 5820 */       return true;
/*      */     }
/* 5822 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f17);
/* 5823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f13(JspTag _jspx_th_html_005fselect_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5828 */     PageContext pageContext = _jspx_page_context;
/* 5829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5831 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f13 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 5832 */     _jspx_th_html_005foptionsCollection_005f13.setPageContext(_jspx_page_context);
/* 5833 */     _jspx_th_html_005foptionsCollection_005f13.setParent((Tag)_jspx_th_html_005fselect_005f17);
/*      */     
/* 5835 */     _jspx_th_html_005foptionsCollection_005f13.setProperty("minute");
/* 5836 */     int _jspx_eval_html_005foptionsCollection_005f13 = _jspx_th_html_005foptionsCollection_005f13.doStartTag();
/* 5837 */     if (_jspx_th_html_005foptionsCollection_005f13.doEndTag() == 5) {
/* 5838 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f13);
/* 5839 */       return true;
/*      */     }
/* 5841 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f13);
/* 5842 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5847 */     PageContext pageContext = _jspx_page_context;
/* 5848 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5850 */     MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5851 */     _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 5852 */     _jspx_th_html_005fmultibox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5854 */     _jspx_th_html_005fmultibox_005f0.setProperty("days");
/*      */     
/* 5856 */     _jspx_th_html_005fmultibox_005f0.setValue("Sunday");
/* 5857 */     int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 5858 */     if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 5859 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 5860 */       return true;
/*      */     }
/* 5862 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 5863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5868 */     PageContext pageContext = _jspx_page_context;
/* 5869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5871 */     MultiboxTag _jspx_th_html_005fmultibox_005f1 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5872 */     _jspx_th_html_005fmultibox_005f1.setPageContext(_jspx_page_context);
/* 5873 */     _jspx_th_html_005fmultibox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5875 */     _jspx_th_html_005fmultibox_005f1.setProperty("days");
/*      */     
/* 5877 */     _jspx_th_html_005fmultibox_005f1.setValue("Monday");
/* 5878 */     int _jspx_eval_html_005fmultibox_005f1 = _jspx_th_html_005fmultibox_005f1.doStartTag();
/* 5879 */     if (_jspx_th_html_005fmultibox_005f1.doEndTag() == 5) {
/* 5880 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1);
/* 5881 */       return true;
/*      */     }
/* 5883 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1);
/* 5884 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5889 */     PageContext pageContext = _jspx_page_context;
/* 5890 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5892 */     MultiboxTag _jspx_th_html_005fmultibox_005f2 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5893 */     _jspx_th_html_005fmultibox_005f2.setPageContext(_jspx_page_context);
/* 5894 */     _jspx_th_html_005fmultibox_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5896 */     _jspx_th_html_005fmultibox_005f2.setProperty("days");
/*      */     
/* 5898 */     _jspx_th_html_005fmultibox_005f2.setValue("Tuesday");
/* 5899 */     int _jspx_eval_html_005fmultibox_005f2 = _jspx_th_html_005fmultibox_005f2.doStartTag();
/* 5900 */     if (_jspx_th_html_005fmultibox_005f2.doEndTag() == 5) {
/* 5901 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2);
/* 5902 */       return true;
/*      */     }
/* 5904 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2);
/* 5905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5910 */     PageContext pageContext = _jspx_page_context;
/* 5911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5913 */     MultiboxTag _jspx_th_html_005fmultibox_005f3 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5914 */     _jspx_th_html_005fmultibox_005f3.setPageContext(_jspx_page_context);
/* 5915 */     _jspx_th_html_005fmultibox_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5917 */     _jspx_th_html_005fmultibox_005f3.setProperty("days");
/*      */     
/* 5919 */     _jspx_th_html_005fmultibox_005f3.setValue("Wednesday");
/* 5920 */     int _jspx_eval_html_005fmultibox_005f3 = _jspx_th_html_005fmultibox_005f3.doStartTag();
/* 5921 */     if (_jspx_th_html_005fmultibox_005f3.doEndTag() == 5) {
/* 5922 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f3);
/* 5923 */       return true;
/*      */     }
/* 5925 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f3);
/* 5926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5931 */     PageContext pageContext = _jspx_page_context;
/* 5932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5934 */     MultiboxTag _jspx_th_html_005fmultibox_005f4 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5935 */     _jspx_th_html_005fmultibox_005f4.setPageContext(_jspx_page_context);
/* 5936 */     _jspx_th_html_005fmultibox_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5938 */     _jspx_th_html_005fmultibox_005f4.setProperty("days");
/*      */     
/* 5940 */     _jspx_th_html_005fmultibox_005f4.setValue("Thursday");
/* 5941 */     int _jspx_eval_html_005fmultibox_005f4 = _jspx_th_html_005fmultibox_005f4.doStartTag();
/* 5942 */     if (_jspx_th_html_005fmultibox_005f4.doEndTag() == 5) {
/* 5943 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f4);
/* 5944 */       return true;
/*      */     }
/* 5946 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f4);
/* 5947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5952 */     PageContext pageContext = _jspx_page_context;
/* 5953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5955 */     MultiboxTag _jspx_th_html_005fmultibox_005f5 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5956 */     _jspx_th_html_005fmultibox_005f5.setPageContext(_jspx_page_context);
/* 5957 */     _jspx_th_html_005fmultibox_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5959 */     _jspx_th_html_005fmultibox_005f5.setProperty("days");
/*      */     
/* 5961 */     _jspx_th_html_005fmultibox_005f5.setValue("Friday");
/* 5962 */     int _jspx_eval_html_005fmultibox_005f5 = _jspx_th_html_005fmultibox_005f5.doStartTag();
/* 5963 */     if (_jspx_th_html_005fmultibox_005f5.doEndTag() == 5) {
/* 5964 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f5);
/* 5965 */       return true;
/*      */     }
/* 5967 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f5);
/* 5968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5973 */     PageContext pageContext = _jspx_page_context;
/* 5974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5976 */     MultiboxTag _jspx_th_html_005fmultibox_005f6 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 5977 */     _jspx_th_html_005fmultibox_005f6.setPageContext(_jspx_page_context);
/* 5978 */     _jspx_th_html_005fmultibox_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5980 */     _jspx_th_html_005fmultibox_005f6.setProperty("days");
/*      */     
/* 5982 */     _jspx_th_html_005fmultibox_005f6.setValue("Saturday");
/* 5983 */     int _jspx_eval_html_005fmultibox_005f6 = _jspx_th_html_005fmultibox_005f6.doStartTag();
/* 5984 */     if (_jspx_th_html_005fmultibox_005f6.doEndTag() == 5) {
/* 5985 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f6);
/* 5986 */       return true;
/*      */     }
/* 5988 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f6);
/* 5989 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f18(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5994 */     PageContext pageContext = _jspx_page_context;
/* 5995 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5997 */     SelectTag _jspx_th_html_005fselect_005f18 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5998 */     _jspx_th_html_005fselect_005f18.setPageContext(_jspx_page_context);
/* 5999 */     _jspx_th_html_005fselect_005f18.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6001 */     _jspx_th_html_005fselect_005f18.setProperty("selectday");
/*      */     
/* 6003 */     _jspx_th_html_005fselect_005f18.setStyleClass("formtext width-50px");
/* 6004 */     int _jspx_eval_html_005fselect_005f18 = _jspx_th_html_005fselect_005f18.doStartTag();
/* 6005 */     if (_jspx_eval_html_005fselect_005f18 != 0) {
/* 6006 */       if (_jspx_eval_html_005fselect_005f18 != 1) {
/* 6007 */         out = _jspx_page_context.pushBody();
/* 6008 */         _jspx_th_html_005fselect_005f18.setBodyContent((BodyContent)out);
/* 6009 */         _jspx_th_html_005fselect_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6012 */         out.write("\n\t\t\t\t\t\t\t\t\t               ");
/* 6013 */         if (_jspx_meth_html_005foptionsCollection_005f14(_jspx_th_html_005fselect_005f18, _jspx_page_context))
/* 6014 */           return true;
/* 6015 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t ");
/* 6016 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f18.doAfterBody();
/* 6017 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6020 */       if (_jspx_eval_html_005fselect_005f18 != 1) {
/* 6021 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6024 */     if (_jspx_th_html_005fselect_005f18.doEndTag() == 5) {
/* 6025 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f18);
/* 6026 */       return true;
/*      */     }
/* 6028 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f18);
/* 6029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f14(JspTag _jspx_th_html_005fselect_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6034 */     PageContext pageContext = _jspx_page_context;
/* 6035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6037 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f14 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 6038 */     _jspx_th_html_005foptionsCollection_005f14.setPageContext(_jspx_page_context);
/* 6039 */     _jspx_th_html_005foptionsCollection_005f14.setParent((Tag)_jspx_th_html_005fselect_005f18);
/*      */     
/* 6041 */     _jspx_th_html_005foptionsCollection_005f14.setProperty("daysForMonthly");
/* 6042 */     int _jspx_eval_html_005foptionsCollection_005f14 = _jspx_th_html_005foptionsCollection_005f14.doStartTag();
/* 6043 */     if (_jspx_th_html_005foptionsCollection_005f14.doEndTag() == 5) {
/* 6044 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f14);
/* 6045 */       return true;
/*      */     }
/* 6047 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f14);
/* 6048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6053 */     PageContext pageContext = _jspx_page_context;
/* 6054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6056 */     org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (org.apache.struts.taglib.html.CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
/* 6057 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 6058 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6060 */     _jspx_th_html_005fcheckbox_005f0.setProperty("mode");
/*      */     
/* 6062 */     _jspx_th_html_005fcheckbox_005f0.setValue("All");
/*      */     
/* 6064 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("javascript:checkMonth()");
/* 6065 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 6066 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 6067 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 6068 */       return true;
/*      */     }
/* 6070 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 6071 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6076 */     PageContext pageContext = _jspx_page_context;
/* 6077 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6079 */     MultiboxTag _jspx_th_html_005fmultibox_005f7 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6080 */     _jspx_th_html_005fmultibox_005f7.setPageContext(_jspx_page_context);
/* 6081 */     _jspx_th_html_005fmultibox_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6083 */     _jspx_th_html_005fmultibox_005f7.setProperty("months");
/*      */     
/* 6085 */     _jspx_th_html_005fmultibox_005f7.setValue("January");
/*      */     
/* 6087 */     _jspx_th_html_005fmultibox_005f7.setStyleId("jan");
/* 6088 */     int _jspx_eval_html_005fmultibox_005f7 = _jspx_th_html_005fmultibox_005f7.doStartTag();
/* 6089 */     if (_jspx_th_html_005fmultibox_005f7.doEndTag() == 5) {
/* 6090 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f7);
/* 6091 */       return true;
/*      */     }
/* 6093 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f7);
/* 6094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6099 */     PageContext pageContext = _jspx_page_context;
/* 6100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6102 */     MultiboxTag _jspx_th_html_005fmultibox_005f8 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6103 */     _jspx_th_html_005fmultibox_005f8.setPageContext(_jspx_page_context);
/* 6104 */     _jspx_th_html_005fmultibox_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6106 */     _jspx_th_html_005fmultibox_005f8.setProperty("months");
/*      */     
/* 6108 */     _jspx_th_html_005fmultibox_005f8.setValue("February");
/*      */     
/* 6110 */     _jspx_th_html_005fmultibox_005f8.setStyleId("feb");
/* 6111 */     int _jspx_eval_html_005fmultibox_005f8 = _jspx_th_html_005fmultibox_005f8.doStartTag();
/* 6112 */     if (_jspx_th_html_005fmultibox_005f8.doEndTag() == 5) {
/* 6113 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f8);
/* 6114 */       return true;
/*      */     }
/* 6116 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f8);
/* 6117 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6122 */     PageContext pageContext = _jspx_page_context;
/* 6123 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6125 */     MultiboxTag _jspx_th_html_005fmultibox_005f9 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6126 */     _jspx_th_html_005fmultibox_005f9.setPageContext(_jspx_page_context);
/* 6127 */     _jspx_th_html_005fmultibox_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6129 */     _jspx_th_html_005fmultibox_005f9.setProperty("months");
/*      */     
/* 6131 */     _jspx_th_html_005fmultibox_005f9.setValue("March");
/*      */     
/* 6133 */     _jspx_th_html_005fmultibox_005f9.setStyleId("mar");
/* 6134 */     int _jspx_eval_html_005fmultibox_005f9 = _jspx_th_html_005fmultibox_005f9.doStartTag();
/* 6135 */     if (_jspx_th_html_005fmultibox_005f9.doEndTag() == 5) {
/* 6136 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f9);
/* 6137 */       return true;
/*      */     }
/* 6139 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f9);
/* 6140 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6145 */     PageContext pageContext = _jspx_page_context;
/* 6146 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6148 */     MultiboxTag _jspx_th_html_005fmultibox_005f10 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6149 */     _jspx_th_html_005fmultibox_005f10.setPageContext(_jspx_page_context);
/* 6150 */     _jspx_th_html_005fmultibox_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6152 */     _jspx_th_html_005fmultibox_005f10.setProperty("months");
/*      */     
/* 6154 */     _jspx_th_html_005fmultibox_005f10.setValue("April");
/*      */     
/* 6156 */     _jspx_th_html_005fmultibox_005f10.setStyleId("apr");
/* 6157 */     int _jspx_eval_html_005fmultibox_005f10 = _jspx_th_html_005fmultibox_005f10.doStartTag();
/* 6158 */     if (_jspx_th_html_005fmultibox_005f10.doEndTag() == 5) {
/* 6159 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f10);
/* 6160 */       return true;
/*      */     }
/* 6162 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f10);
/* 6163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6168 */     PageContext pageContext = _jspx_page_context;
/* 6169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6171 */     MultiboxTag _jspx_th_html_005fmultibox_005f11 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6172 */     _jspx_th_html_005fmultibox_005f11.setPageContext(_jspx_page_context);
/* 6173 */     _jspx_th_html_005fmultibox_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6175 */     _jspx_th_html_005fmultibox_005f11.setProperty("months");
/*      */     
/* 6177 */     _jspx_th_html_005fmultibox_005f11.setValue("May");
/*      */     
/* 6179 */     _jspx_th_html_005fmultibox_005f11.setStyleId("may");
/* 6180 */     int _jspx_eval_html_005fmultibox_005f11 = _jspx_th_html_005fmultibox_005f11.doStartTag();
/* 6181 */     if (_jspx_th_html_005fmultibox_005f11.doEndTag() == 5) {
/* 6182 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f11);
/* 6183 */       return true;
/*      */     }
/* 6185 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f11);
/* 6186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6191 */     PageContext pageContext = _jspx_page_context;
/* 6192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6194 */     MultiboxTag _jspx_th_html_005fmultibox_005f12 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6195 */     _jspx_th_html_005fmultibox_005f12.setPageContext(_jspx_page_context);
/* 6196 */     _jspx_th_html_005fmultibox_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6198 */     _jspx_th_html_005fmultibox_005f12.setProperty("months");
/*      */     
/* 6200 */     _jspx_th_html_005fmultibox_005f12.setValue("June");
/*      */     
/* 6202 */     _jspx_th_html_005fmultibox_005f12.setStyleId("jun");
/* 6203 */     int _jspx_eval_html_005fmultibox_005f12 = _jspx_th_html_005fmultibox_005f12.doStartTag();
/* 6204 */     if (_jspx_th_html_005fmultibox_005f12.doEndTag() == 5) {
/* 6205 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f12);
/* 6206 */       return true;
/*      */     }
/* 6208 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f12);
/* 6209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6214 */     PageContext pageContext = _jspx_page_context;
/* 6215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6217 */     MultiboxTag _jspx_th_html_005fmultibox_005f13 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6218 */     _jspx_th_html_005fmultibox_005f13.setPageContext(_jspx_page_context);
/* 6219 */     _jspx_th_html_005fmultibox_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6221 */     _jspx_th_html_005fmultibox_005f13.setProperty("months");
/*      */     
/* 6223 */     _jspx_th_html_005fmultibox_005f13.setValue("July");
/*      */     
/* 6225 */     _jspx_th_html_005fmultibox_005f13.setStyleId("jul");
/* 6226 */     int _jspx_eval_html_005fmultibox_005f13 = _jspx_th_html_005fmultibox_005f13.doStartTag();
/* 6227 */     if (_jspx_th_html_005fmultibox_005f13.doEndTag() == 5) {
/* 6228 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f13);
/* 6229 */       return true;
/*      */     }
/* 6231 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f13);
/* 6232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6237 */     PageContext pageContext = _jspx_page_context;
/* 6238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6240 */     MultiboxTag _jspx_th_html_005fmultibox_005f14 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6241 */     _jspx_th_html_005fmultibox_005f14.setPageContext(_jspx_page_context);
/* 6242 */     _jspx_th_html_005fmultibox_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6244 */     _jspx_th_html_005fmultibox_005f14.setProperty("months");
/*      */     
/* 6246 */     _jspx_th_html_005fmultibox_005f14.setValue("August");
/*      */     
/* 6248 */     _jspx_th_html_005fmultibox_005f14.setStyleId("aug");
/* 6249 */     int _jspx_eval_html_005fmultibox_005f14 = _jspx_th_html_005fmultibox_005f14.doStartTag();
/* 6250 */     if (_jspx_th_html_005fmultibox_005f14.doEndTag() == 5) {
/* 6251 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f14);
/* 6252 */       return true;
/*      */     }
/* 6254 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f14);
/* 6255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6260 */     PageContext pageContext = _jspx_page_context;
/* 6261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6263 */     MultiboxTag _jspx_th_html_005fmultibox_005f15 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6264 */     _jspx_th_html_005fmultibox_005f15.setPageContext(_jspx_page_context);
/* 6265 */     _jspx_th_html_005fmultibox_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6267 */     _jspx_th_html_005fmultibox_005f15.setProperty("months");
/*      */     
/* 6269 */     _jspx_th_html_005fmultibox_005f15.setValue("September");
/*      */     
/* 6271 */     _jspx_th_html_005fmultibox_005f15.setStyleId("sep");
/* 6272 */     int _jspx_eval_html_005fmultibox_005f15 = _jspx_th_html_005fmultibox_005f15.doStartTag();
/* 6273 */     if (_jspx_th_html_005fmultibox_005f15.doEndTag() == 5) {
/* 6274 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f15);
/* 6275 */       return true;
/*      */     }
/* 6277 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f15);
/* 6278 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6283 */     PageContext pageContext = _jspx_page_context;
/* 6284 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6286 */     MultiboxTag _jspx_th_html_005fmultibox_005f16 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6287 */     _jspx_th_html_005fmultibox_005f16.setPageContext(_jspx_page_context);
/* 6288 */     _jspx_th_html_005fmultibox_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6290 */     _jspx_th_html_005fmultibox_005f16.setProperty("months");
/*      */     
/* 6292 */     _jspx_th_html_005fmultibox_005f16.setValue("October");
/*      */     
/* 6294 */     _jspx_th_html_005fmultibox_005f16.setStyleId("oct");
/* 6295 */     int _jspx_eval_html_005fmultibox_005f16 = _jspx_th_html_005fmultibox_005f16.doStartTag();
/* 6296 */     if (_jspx_th_html_005fmultibox_005f16.doEndTag() == 5) {
/* 6297 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f16);
/* 6298 */       return true;
/*      */     }
/* 6300 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f16);
/* 6301 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6306 */     PageContext pageContext = _jspx_page_context;
/* 6307 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6309 */     MultiboxTag _jspx_th_html_005fmultibox_005f17 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6310 */     _jspx_th_html_005fmultibox_005f17.setPageContext(_jspx_page_context);
/* 6311 */     _jspx_th_html_005fmultibox_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6313 */     _jspx_th_html_005fmultibox_005f17.setProperty("months");
/*      */     
/* 6315 */     _jspx_th_html_005fmultibox_005f17.setValue("November");
/*      */     
/* 6317 */     _jspx_th_html_005fmultibox_005f17.setStyleId("nov");
/* 6318 */     int _jspx_eval_html_005fmultibox_005f17 = _jspx_th_html_005fmultibox_005f17.doStartTag();
/* 6319 */     if (_jspx_th_html_005fmultibox_005f17.doEndTag() == 5) {
/* 6320 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f17);
/* 6321 */       return true;
/*      */     }
/* 6323 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f17);
/* 6324 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f18(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6329 */     PageContext pageContext = _jspx_page_context;
/* 6330 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6332 */     MultiboxTag _jspx_th_html_005fmultibox_005f18 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 6333 */     _jspx_th_html_005fmultibox_005f18.setPageContext(_jspx_page_context);
/* 6334 */     _jspx_th_html_005fmultibox_005f18.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6336 */     _jspx_th_html_005fmultibox_005f18.setProperty("months");
/*      */     
/* 6338 */     _jspx_th_html_005fmultibox_005f18.setValue("December");
/*      */     
/* 6340 */     _jspx_th_html_005fmultibox_005f18.setStyleId("dec");
/* 6341 */     int _jspx_eval_html_005fmultibox_005f18 = _jspx_th_html_005fmultibox_005f18.doStartTag();
/* 6342 */     if (_jspx_th_html_005fmultibox_005f18.doEndTag() == 5) {
/* 6343 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f18);
/* 6344 */       return true;
/*      */     }
/* 6346 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f18);
/* 6347 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6352 */     PageContext pageContext = _jspx_page_context;
/* 6353 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6355 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6356 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 6357 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6359 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.maintenance.recurrencedetails");
/* 6360 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 6361 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 6362 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6363 */       return true;
/*      */     }
/* 6365 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6366 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6371 */     PageContext pageContext = _jspx_page_context;
/* 6372 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6374 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6375 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 6376 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6378 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.schedulereport.newschedule.recurencedetails.help");
/* 6379 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 6380 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 6381 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6382 */       return true;
/*      */     }
/* 6384 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6390 */     PageContext pageContext = _jspx_page_context;
/* 6391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6393 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6394 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 6395 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6397 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.schedulereport.newschedule.reportdeliveryheading.text");
/* 6398 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 6399 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 6400 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6401 */       return true;
/*      */     }
/* 6403 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6404 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6409 */     PageContext pageContext = _jspx_page_context;
/* 6410 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6412 */     RadioTag _jspx_th_html_005fradio_005f12 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 6413 */     _jspx_th_html_005fradio_005f12.setPageContext(_jspx_page_context);
/* 6414 */     _jspx_th_html_005fradio_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6416 */     _jspx_th_html_005fradio_005f12.setProperty("actionsEnabled");
/*      */     
/* 6418 */     _jspx_th_html_005fradio_005f12.setValue("true");
/* 6419 */     int _jspx_eval_html_005fradio_005f12 = _jspx_th_html_005fradio_005f12.doStartTag();
/* 6420 */     if (_jspx_th_html_005fradio_005f12.doEndTag() == 5) {
/* 6421 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f12);
/* 6422 */       return true;
/*      */     }
/* 6424 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f12);
/* 6425 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6430 */     PageContext pageContext = _jspx_page_context;
/* 6431 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6433 */     RadioTag _jspx_th_html_005fradio_005f13 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(RadioTag.class);
/* 6434 */     _jspx_th_html_005fradio_005f13.setPageContext(_jspx_page_context);
/* 6435 */     _jspx_th_html_005fradio_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6437 */     _jspx_th_html_005fradio_005f13.setProperty("actionsEnabled");
/*      */     
/* 6439 */     _jspx_th_html_005fradio_005f13.setStyleId("publishlink");
/*      */     
/* 6441 */     _jspx_th_html_005fradio_005f13.setValue("publish");
/* 6442 */     int _jspx_eval_html_005fradio_005f13 = _jspx_th_html_005fradio_005f13.doStartTag();
/* 6443 */     if (_jspx_th_html_005fradio_005f13.doEndTag() == 5) {
/* 6444 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f13);
/* 6445 */       return true;
/*      */     }
/* 6447 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f13);
/* 6448 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6453 */     PageContext pageContext = _jspx_page_context;
/* 6454 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6456 */     RadioTag _jspx_th_html_005fradio_005f14 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(RadioTag.class);
/* 6457 */     _jspx_th_html_005fradio_005f14.setPageContext(_jspx_page_context);
/* 6458 */     _jspx_th_html_005fradio_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6460 */     _jspx_th_html_005fradio_005f14.setProperty("reportDelivery");
/*      */     
/* 6462 */     _jspx_th_html_005fradio_005f14.setStyleId("reportDeliveryPDF");
/*      */     
/* 6464 */     _jspx_th_html_005fradio_005f14.setValue("pdf");
/* 6465 */     int _jspx_eval_html_005fradio_005f14 = _jspx_th_html_005fradio_005f14.doStartTag();
/* 6466 */     if (_jspx_th_html_005fradio_005f14.doEndTag() == 5) {
/* 6467 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f14);
/* 6468 */       return true;
/*      */     }
/* 6470 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f14);
/* 6471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6476 */     PageContext pageContext = _jspx_page_context;
/* 6477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6479 */     RadioTag _jspx_th_html_005fradio_005f15 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(RadioTag.class);
/* 6480 */     _jspx_th_html_005fradio_005f15.setPageContext(_jspx_page_context);
/* 6481 */     _jspx_th_html_005fradio_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6483 */     _jspx_th_html_005fradio_005f15.setProperty("reportDelivery");
/*      */     
/* 6485 */     _jspx_th_html_005fradio_005f15.setStyleId("reportDelivery");
/*      */     
/* 6487 */     _jspx_th_html_005fradio_005f15.setValue("excel");
/* 6488 */     int _jspx_eval_html_005fradio_005f15 = _jspx_th_html_005fradio_005f15.doStartTag();
/* 6489 */     if (_jspx_th_html_005fradio_005f15.doEndTag() == 5) {
/* 6490 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f15);
/* 6491 */       return true;
/*      */     }
/* 6493 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f15);
/* 6494 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6499 */     PageContext pageContext = _jspx_page_context;
/* 6500 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6502 */     RadioTag _jspx_th_html_005fradio_005f16 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(RadioTag.class);
/* 6503 */     _jspx_th_html_005fradio_005f16.setPageContext(_jspx_page_context);
/* 6504 */     _jspx_th_html_005fradio_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6506 */     _jspx_th_html_005fradio_005f16.setProperty("reportDelivery");
/*      */     
/* 6508 */     _jspx_th_html_005fradio_005f16.setStyleId("reportDelivery");
/*      */     
/* 6510 */     _jspx_th_html_005fradio_005f16.setValue("csv");
/* 6511 */     int _jspx_eval_html_005fradio_005f16 = _jspx_th_html_005fradio_005f16.doStartTag();
/* 6512 */     if (_jspx_th_html_005fradio_005f16.doEndTag() == 5) {
/* 6513 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f16);
/* 6514 */       return true;
/*      */     }
/* 6516 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f16);
/* 6517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f19(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6522 */     PageContext pageContext = _jspx_page_context;
/* 6523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6525 */     SelectTag _jspx_th_html_005fselect_005f19 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 6526 */     _jspx_th_html_005fselect_005f19.setPageContext(_jspx_page_context);
/* 6527 */     _jspx_th_html_005fselect_005f19.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6529 */     _jspx_th_html_005fselect_005f19.setProperty("sendmail");
/*      */     
/* 6531 */     _jspx_th_html_005fselect_005f19.setStyleClass("formtext default actionSelectCSS");
/* 6532 */     int _jspx_eval_html_005fselect_005f19 = _jspx_th_html_005fselect_005f19.doStartTag();
/* 6533 */     if (_jspx_eval_html_005fselect_005f19 != 0) {
/* 6534 */       if (_jspx_eval_html_005fselect_005f19 != 1) {
/* 6535 */         out = _jspx_page_context.pushBody();
/* 6536 */         _jspx_th_html_005fselect_005f19.setBodyContent((BodyContent)out);
/* 6537 */         _jspx_th_html_005fselect_005f19.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6540 */         out.write("\n\t\t\t\t\t\t            \t  \t\t");
/* 6541 */         if (_jspx_meth_html_005foptionsCollection_005f15(_jspx_th_html_005fselect_005f19, _jspx_page_context))
/* 6542 */           return true;
/* 6543 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/* 6544 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f19.doAfterBody();
/* 6545 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6548 */       if (_jspx_eval_html_005fselect_005f19 != 1) {
/* 6549 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6552 */     if (_jspx_th_html_005fselect_005f19.doEndTag() == 5) {
/* 6553 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f19);
/* 6554 */       return true;
/*      */     }
/* 6556 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f19);
/* 6557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f15(JspTag _jspx_th_html_005fselect_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6562 */     PageContext pageContext = _jspx_page_context;
/* 6563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6565 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f15 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 6566 */     _jspx_th_html_005foptionsCollection_005f15.setPageContext(_jspx_page_context);
/* 6567 */     _jspx_th_html_005foptionsCollection_005f15.setParent((Tag)_jspx_th_html_005fselect_005f19);
/*      */     
/* 6569 */     _jspx_th_html_005foptionsCollection_005f15.setProperty("applications");
/* 6570 */     int _jspx_eval_html_005foptionsCollection_005f15 = _jspx_th_html_005foptionsCollection_005f15.doStartTag();
/* 6571 */     if (_jspx_th_html_005foptionsCollection_005f15.doEndTag() == 5) {
/* 6572 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f15);
/* 6573 */       return true;
/*      */     }
/* 6575 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f15);
/* 6576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6581 */     PageContext pageContext = _jspx_page_context;
/* 6582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6584 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 6585 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 6586 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6588 */     _jspx_th_html_005ftext_005f1.setProperty("priority");
/*      */     
/* 6590 */     _jspx_th_html_005ftext_005f1.setSize("30");
/*      */     
/* 6592 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/*      */     
/* 6594 */     _jspx_th_html_005ftext_005f1.setMaxlength("50");
/* 6595 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 6596 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 6597 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6598 */       return true;
/*      */     }
/* 6600 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6601 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6606 */     PageContext pageContext = _jspx_page_context;
/* 6607 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6609 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6610 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 6611 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6613 */     _jspx_th_fmt_005fmessage_005f10.setKey("am.webclient.schedulereport.newschedule.reportdeliveryheading.text");
/* 6614 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 6615 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 6616 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6617 */       return true;
/*      */     }
/* 6619 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6625 */     PageContext pageContext = _jspx_page_context;
/* 6626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6628 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6629 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 6630 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6632 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.webclient.schedulereport.newschedule.reportdelivery.help");
/* 6633 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 6634 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 6635 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6636 */       return true;
/*      */     }
/* 6638 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6644 */     PageContext pageContext = _jspx_page_context;
/* 6645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6647 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6648 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 6649 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6651 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 6653 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 6654 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 6655 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 6656 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6657 */       return true;
/*      */     }
/* 6659 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 6660 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ScheduleReportDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */