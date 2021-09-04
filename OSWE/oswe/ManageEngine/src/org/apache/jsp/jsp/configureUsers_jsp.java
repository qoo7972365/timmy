/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.utils.client.UserConfigurationUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.SkipPageException;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FileTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.util.TokenProcessor;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParamTag;
/*      */ 
/*      */ public final class configureUsers_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*      */ {
/*   63 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   66 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   67 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   73 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   78 */     ArrayList list = null;
/*   79 */     StringBuffer sbf = new StringBuffer();
/*   80 */     ManagedApplication mo = new ManagedApplication();
/*   81 */     if (distinct)
/*      */     {
/*   83 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   87 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   90 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   92 */       ArrayList row = (ArrayList)list.get(i);
/*   93 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   94 */       if (distinct) {
/*   95 */         sbf.append(row.get(0));
/*      */       } else
/*   97 */         sbf.append(row.get(1));
/*   98 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  101 */     return sbf.toString();
/*      */   }
/*      */   
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  106 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  111 */     return com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*      */   }
/*      */   
/*      */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List)
/*      */   {
/*  116 */     int i = 0;
/*  117 */     String uri = request.getRequestURI();
/*  118 */     String fromAlarmEscalation = "/jsp/AlertRes_Mtrgrp.jsp";
/*  119 */     String uri1 = "/jsp/MaintenanceTask.jsp";
/*  120 */     if (uri.equals(uri1)) {
/*  121 */       i = 1;
/*      */     }
/*      */     else {
/*  124 */       i = 0;
/*      */     }
/*  126 */     String addColspan = "";
/*  127 */     if (fromAlarmEscalation.equals(uri)) {
/*  128 */       addColspan = "colspan=\"2\"";
/*      */     }
/*  130 */     String type = request.getParameter("selectionType");
/*      */     
/*  132 */     StringBuffer toreturn = new StringBuffer();
/*      */     
/*  134 */     String disableSelAllChilds = request.getParameter("disableSelAllChilds");
/*  135 */     String selectedDependentMGroupsStr = (String)request.getAttribute("selectedDepMonGroups");
/*  136 */     ArrayList<String> mgroupsToDisable = (ArrayList)request.getAttribute("mgroupsToDisable");
/*      */     
/*  138 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/*  140 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/*  141 */       String childresid = (String)singlerow.get(0);
/*  142 */       String childresname = (String)singlerow.get(1);
/*  143 */       String childtype = ((String)singlerow.get(2) + "").trim();
/*      */       
/*  145 */       String shortname = ((String)singlerow.get(4) + "").trim();
/*  146 */       String checkedvalue = "";
/*  147 */       ArrayList grouplist = new ArrayList();
/*  148 */       if (i == 1) {
/*  149 */         grouplist = ((AMActionForm)request.getAttribute("AMActionForm")).getPresentg();
/*      */       }
/*  151 */       else if ("usergroup".equalsIgnoreCase(type)) {
/*  152 */         grouplist = (ArrayList)request.getAttribute("selectedUserGroupMg");
/*      */       } else {
/*  154 */         grouplist = (ArrayList)request.getAttribute("selectedMonitor");
/*      */       }
/*      */       
/*  157 */       if ((grouplist != null) && (grouplist.size() != 0))
/*      */       {
/*  159 */         for (int z = 0; z < grouplist.size(); z++)
/*      */         {
/*  161 */           if (childresid.equals(((Properties)grouplist.get(z)).getProperty("value")))
/*      */           {
/*  163 */             checkedvalue = "checked";
/*      */           }
/*      */         }
/*      */       }
/*  167 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/*  168 */       int spacing = 0;
/*  169 */       if (level >= 1)
/*      */       {
/*  171 */         spacing = 20 * level;
/*      */       }
/*  173 */       if (EnterpriseUtil.isAdminServer)
/*      */       {
/*      */ 
/*  176 */         int childresidInt = Integer.parseInt(childresid);
/*  177 */         if (childresidInt > com.adventnet.appmanager.server.framework.comm.Constants.RANGE)
/*      */         {
/*  179 */           String parent = CommDBUtil.getManagedServerNameWithPort(childresid);
/*  180 */           childresname = childresname + "_" + parent;
/*      */         }
/*      */       }
/*  183 */       if ((childtype.equals("HAI")) && (!com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.isSiteId(childresid + "")))
/*      */       {
/*  185 */         String checkedStr = "";
/*  186 */         String disableStr = "";
/*  187 */         if ((selectedDependentMGroupsStr != null) && (selectedDependentMGroupsStr.indexOf(childresid) != -1)) {
/*  188 */           checkedStr = "checked";
/*      */         }
/*  190 */         if ((mgroupsToDisable != null) && (mgroupsToDisable.contains(childresid))) {
/*  191 */           disableStr = "disabled";
/*      */         }
/*  193 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/*  194 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/*  195 */         String checkbox = "<input type=\"checkbox\" " + disableStr + " " + checkedStr + " name=\"select\" " + checkedvalue + " id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"";
/*  196 */         if ((disableSelAllChilds == null) || (disableSelAllChilds.equalsIgnoreCase("false"))) {
/*  197 */           checkbox = checkbox + " onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"";
/*      */         }
/*  199 */         checkbox = checkbox + ">";
/*  200 */         String mgNameHidInput = "<input type=\"hidden\" name=\"mgName\" id=\"mgName\" value=\"" + childresname + "\">";
/*  201 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/*      */         
/*  203 */         System.out.println("request.isUserInRole\"ADMIN\")" + request.isUserInRole("ADMIN"));
/*  204 */         String resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:inline;\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:none;\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + " " + getTrimmedText(childresname, 45) + " ";
/*      */         
/*  206 */         toreturn.append("<tr " + tempbgcolor + " width=\"80%\" id=\"#monitor" + currentresourceidtree + "\" style=\"background: rgb(255, 255, 255) none repeat scroll 0%; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; display: none;\">");
/*  207 */         toreturn.append("<td " + tempbgcolor + " width=\"2%\" >&nbsp;&nbsp;</td> ");
/*  208 */         toreturn.append(mgNameHidInput);
/*  209 */         toreturn.append("<td " + tempbgcolor + " " + addColspan + " width=\"68%\"  style=\"padding-left: " + spacing + "px !important;\">" + resourcelink + "</td>");
/*  210 */         toreturn.append("");
/*  211 */         toreturn.append("</tr>");
/*  212 */         if (childmos.get(childresid + "") != null)
/*      */         {
/*  214 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/*  215 */           toreturn.append(toappend);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  221 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*  225 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  231 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  232 */   static { _jspx_dependants.put("/jsp/mgtreeview.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fnobody;
/*      */   private ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*  269 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  273 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  274 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  275 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  276 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  277 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  278 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  279 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  280 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  281 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  282 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  283 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  284 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  285 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  286 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  287 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  288 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  289 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  290 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  291 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  292 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  293 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  294 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  295 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  296 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  297 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  298 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  299 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  300 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  301 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  302 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  303 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  307 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  308 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  309 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  310 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  311 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  312 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  313 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/*  314 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/*  315 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*  316 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.release();
/*  317 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  318 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  319 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.release();
/*  320 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  321 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  322 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  323 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.release();
/*  324 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/*  325 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  326 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.release();
/*  327 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  328 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  329 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  330 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.release();
/*  331 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*  332 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.release();
/*  333 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.release();
/*  334 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
/*  335 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  342 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  345 */     JspWriter out = null;
/*  346 */     Object page = this;
/*  347 */     JspWriter _jspx_out = null;
/*  348 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  352 */       response.setContentType("text/html;charset=UTF-8");
/*  353 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/*  355 */       _jspx_page_context = pageContext;
/*  356 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  357 */       ServletConfig config = pageContext.getServletConfig();
/*  358 */       session = pageContext.getSession();
/*  359 */       out = pageContext.getOut();
/*  360 */       _jspx_out = out;
/*      */       
/*  362 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  363 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  364 */       out.write("\n<style>\n\n\n\n\t.custom-userassociation-show{\n\tmax-height: 200px;\n\toverflow: auto;\n}\n* html .custom-userassociation-show{\n\theight: 200px;\n}\n\n.custom-user-heading-sub{\n\tfont: 11px Arial, Helvetica, sans-serif;\n\tfont-family: Arial, Helvetica, sans-serif;\n\tbackground-color: #fafafa;\n\tline-height: 20px;\n}\n\n.customfieldlinks{\n    color: #595959;\n    font-family: Arial,Helvetica,sans-serif;\n    font-size: 11px;\n    text-decoration: underline;\n}\n\n.assignsuccess  {\n    color: #33DB55;\n    font-family: Arial,Helvetica,sans-serif;\n    font-size: 11px;\n}\n\n.custom-fields-value {\n font-family:Arial, Helvetica, sans-serif;\n color: #5c5c5c;\n font-size:11px;\n line-height: 28px;\n border-bottom: 1px solid #F1F1F1;\n}\n\n.custom-fields-heading {\n\tcolor: #595959;\n\tfont: bold 12px Arial, Helvetica, sans-serif;\n\tline-height: 25px;\n}\n\n.customfieldsanchor {\n\tcolor: #595959;\n    font-family: Arial,Helvetica,sans-serif;\n    font-size: 11px;\n}\n\n.customfieldforalarm{\n\tcolor: #595959;\n    font-family: Arial,Helvetica,sans-serif;\n    font-size: 11px;\n");
/*  365 */       out.write("}\n\na.customfieldsanchor:hover {\n\ttext-decoration: underline;\n}\n\n\n.custom-entity-display {\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-weight: normal;\n\tfont-size: 11px;\n\tpadding-left: 20px;\n\tcolor: #595959;\n\tline-height: 20px;\n}\n\n.custom-bold-text {\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-weight: bold;\n\tfont-size: 11px;\n\tpadding-left: 10px;\n\tcolor: #595959;\n\tline-height: 28px;\n\tborder-bottom: 1px solid #F1F1F1;\n}\n\n\n.ulstyleforcf {\nlist-style-type: none;\nmargin: 0px;\npadding:0px;\n}\n\n.listyleforcf {\n\tfloat: left;\n\tposition: relative;\n\tpadding-left: 0px;\n\tpadding-right: 0px;\n\n}\n\n.ulanchor {\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\npadding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: normal;\n}\n\n.ulanchoractive {\nbackground-image: url(/images/icon_customfield_sep.gif);\nbackground-repeat: no-repeat;\nbackground-position: right;\npadding-right: 20px;\n");
/*  366 */       out.write("padding-left: 20px;\nfloat:left;\nline-height: 50px;\ntext-decoration: none;\ncolor: #595959;\nfont-size: 11px;\nfont-weight: bold;\n}\n\n #customfieldTabs .liselectedforcf a span.tabarrows {\n\n\tdisplay: block;\n}\n\n\ndiv.userslistarea {\n\n    background-color: #FFFFFF;\n    border: 1px dashed #CCCCCC;\n    margin: auto 1em 3em 3em 0em;\n    padding: 0em;\n    align:left;\n}\nul.options {\n    list-style-type: none;\n    margin: 0.3em 0;\n    padding: 0;\n    width: 15em;\n}\nul.options li {\n    background: url(\"/images/cross.gif\") no-repeat scroll 98% center #DFE9F2;\n    border: 1px solid #CCDDEE;\n    color: #000000;\n    cursor: pointer;\n    font-family: Arial,Helvetica,sans-serif;\n    font-size: 12px;\n    margin: 1px;\n    padding: 0.1em 0.3em;\n}\nselect.options {\n    color: #4466AA;\n    font-weight: bold;\n}\n\n.disabletextfield{\nbackground:#DCDCDC;\ncursor:default;\n}\n.search-field input:text {height:50px;}\n\n</style>\n\n");
/*      */       
/*  368 */       String formAction = "/admin/userconfiguration";
/*  369 */       if ("true".equals((String)request.getAttribute("updateProfile"))) {
/*  370 */         formAction = "/userconfiguration";
/*      */       }
/*  372 */       HashMap map = new HashMap();
/*  373 */       ArrayList<String> selectUserlist = new ArrayList();
/*  374 */       ArrayList<String> selectedUserGroup = new ArrayList();
/*  375 */       ArrayList<String> selectedDomains = new ArrayList();
/*  376 */       HashMap<String, ArrayList<HashMap<String, String>>> usergroupList = new HashMap();
/*      */       try
/*      */       {
/*  379 */         HashMap<String, String> querycondition = new HashMap();
/*  380 */         if (ClientDBUtil.isPrivilegedUser(request)) {
/*  381 */           if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/*  382 */             String loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/*  383 */             querycondition.put("privelagedCondition", " and AM_HOLISTICAPPLICATION.HAID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")");
/*      */           } else {
/*  385 */             querycondition.put("privelagedCondition", " and " + DependantMOUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", ClientDBUtil.getAssociatedMonitorGroupsForOwner(request.getRemoteUser())) + " ");
/*      */           }
/*      */         }
/*      */         
/*  389 */         map = ReportUtil.getMonitorGroups(false, querycondition);
/*  390 */         usergroupList = UserConfigurationUtil.getAllUserGroup();
/*  391 */         ArrayList allusersList = UserConfigurationUtil.getUserListDetails(0);
/*  392 */         ArrayList alldomainList = com.manageengine.appmanager.util.ADAuthenticationUtil.getDomainList(true);
/*  393 */         selectUserlist = (ArrayList)request.getAttribute("selectedUserList");
/*  394 */         selectedUserGroup = (ArrayList)request.getAttribute("selectedUsergroupList");
/*  395 */         selectedDomains = (ArrayList)request.getAttribute("selectedDomainList");
/*      */         
/*  397 */         request.setAttribute("usergroupList", usergroupList);
/*  398 */         request.setAttribute("userList", allusersList);
/*  399 */         request.setAttribute("domainList", alldomainList);
/*  400 */         ArrayList tempList = (ArrayList)map.get("grouplist");
/*  401 */         request.setAttribute("applications", tempList);
/*      */       } catch (Exception ex) {
/*  403 */         ex.printStackTrace();
/*      */       }
/*  405 */       TokenProcessor token = TokenProcessor.getInstance();
/*  406 */       token.saveToken(request);
/*  407 */       String userid = request.getParameter("userid");
/*  408 */       String username = request.getParameter("username");
/*  409 */       String remoteUsername = request.getRemoteUser();
/*  410 */       String remoteUserRole = DBUtil.getUserRole(request.getRemoteUser());
/*  411 */       ArrayList attribIDs = new ArrayList(2);
/*  412 */       attribIDs.add("17");
/*  413 */       attribIDs.add("18");
/*  414 */       ArrayList resIDs = new ArrayList();
/*  415 */       Hashtable chilmos = (Hashtable)map.get("childlist");
/*  416 */       System.out.println("the childlist:" + map.get("childlist"));
/*      */       
/*  418 */       int totalmonitorcount = 0;
/*  419 */       ArrayList tempresourelist = new ArrayList();
/*      */       
/*  421 */       HashMap extDeviceMap = null;
/*  422 */       HashMap site24x7List = null;
/*  423 */       if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*      */       {
/*  425 */         extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink();
/*  426 */         site24x7List = DBUtil.getAllsite24x7MonitorsLink();
/*      */       }
/*      */       
/*      */ 
/*  430 */       out.write(10);
/*  431 */       out.write(10);
/*      */       
/*  433 */       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  434 */       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  435 */       _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */       
/*  437 */       _jspx_th_logic_005fiterate_005f0.setName("applications");
/*      */       
/*  439 */       _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */       
/*  441 */       _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*      */       
/*  443 */       _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*  444 */       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  445 */       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  446 */         ArrayList row = null;
/*  447 */         Integer i = null;
/*  448 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  449 */           out = _jspx_page_context.pushBody();
/*  450 */           _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/*  451 */           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */         }
/*  453 */         row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  454 */         i = (Integer)_jspx_page_context.findAttribute("i");
/*      */         for (;;) {
/*  456 */           out.write(10);
/*      */           
/*  458 */           if (!((String)row.get(6)).equals("orphaned"))
/*      */           {
/*  460 */             resIDs.add((String)row.get(6));
/*      */           }
/*      */           try
/*      */           {
/*  464 */             ArrayList srow = (ArrayList)chilmos.get((String)row.get(6) + "");
/*  465 */             if (srow != null)
/*      */             {
/*  467 */               for (int k = 0; k < srow.size(); k++)
/*      */               {
/*  469 */                 ArrayList mo = (ArrayList)srow.get(k);
/*  470 */                 if ((mo != null) && (mo.get(0) != null) && (!((String)mo.get(0)).equals("null")))
/*      */                 {
/*  472 */                   String cresid = ((String)mo.get(0) + "").trim();
/*  473 */                   resIDs.add(cresid);
/*  474 */                   String resourceType = ((String)mo.get(2) + "").trim();
/*      */                   
/*  476 */                   if ((!resourceType.equals("HAI")) && (!tempresourelist.contains(cresid)))
/*      */                   {
/*  478 */                     tempresourelist.add(cresid);
/*  479 */                     totalmonitorcount++;
/*      */                   }
/*      */                   
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  488 */             ex.printStackTrace();
/*      */           }
/*      */           
/*  491 */           out.write(10);
/*  492 */           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  493 */           row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  494 */           i = (Integer)_jspx_page_context.findAttribute("i");
/*  495 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*  498 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  499 */           out = _jspx_page_context.popBody();
/*      */         }
/*      */       }
/*  502 */       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  503 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*      */       }
/*      */       else {
/*  506 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  507 */         out.write("\n<SCRIPT src=\"/template/listview.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n<script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\">\nvar GlobalMGs=new Array();\n");
/*      */         
/*  509 */         IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  510 */         _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/*  511 */         _jspx_th_logic_005fiterate_005f1.setParent(null);
/*      */         
/*  513 */         _jspx_th_logic_005fiterate_005f1.setName("applications");
/*      */         
/*  515 */         _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */         
/*  517 */         _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/*      */         
/*  519 */         _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/*  520 */         int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/*  521 */         if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/*  522 */           ArrayList row = null;
/*  523 */           Integer i = null;
/*  524 */           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/*  525 */             out = _jspx_page_context.pushBody();
/*  526 */             _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/*  527 */             _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */           }
/*  529 */           row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  530 */           i = (Integer)_jspx_page_context.findAttribute("i");
/*      */           for (;;) {
/*  532 */             out.write("\nGlobalMGs[");
/*  533 */             out.print(i);
/*  534 */             out.write(93);
/*  535 */             out.write(61);
/*  536 */             out.write(39);
/*  537 */             out.print((String)row.get(6));
/*  538 */             out.write(39);
/*  539 */             out.write(59);
/*  540 */             out.write(10);
/*  541 */             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/*  542 */             row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  543 */             i = (Integer)_jspx_page_context.findAttribute("i");
/*  544 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*  547 */           if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/*  548 */             out = _jspx_page_context.popBody();
/*      */           }
/*      */         }
/*  551 */         if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/*  552 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*      */         }
/*      */         else {
/*  555 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*  556 */           out.write("</SCRIPT>\n\n\n<script>\n\nfunction fnSelectAll(e,name)\n{\nToggleAll(e,document.UserConfiguration,name);\n}\n\nvar retaintree;\nfunction selectAllChildCKbs(obname,ckb,myfrm)\n{\n\tif(typeof(myfrm.length)==\"undefined\") {\n\n\treturn;\n\t}\n\nfor(i=0;i<myfrm.length;i++) {\nif(myfrm.elements[i].type == \"checkbox\" && myfrm.elements[i].id.indexOf(obname) == 0) {\nmyfrm.elements[i].checked = ckb.checked;\n}\n}\n}\n\nfunction deselectParentCKbs(obname1,ckb1,myfrm)\n{\n if(ckb1.checked)\n return;\n\n var temp1=obname1.split(\"|\");\n for(i=0;i<temp1.length;i++)\n  {\n   if(i==0)\n    parentresid=temp1[i];\n   else\n    parentresid=parentresid+\"|\"+temp1[i];\n\n  for(j=0;j<myfrm.length;j++) {\n\n   if(myfrm.elements[j].id == parentresid) {\n    myfrm.elements[j].checked = false;\n   }\n  }\n }\n}\n\nfunction toggleallDivs(action)\n{\nvar newDisplay ;\nif (document.all) newDisplay = \"block\"; //IE4+ specific code\nelse newDisplay = \"table-row\"; //Netscape and Mozilla\nvar collapseid ;\ncollapseid= document.getElementById(\"showall\");\nif(collapseid.style.display==\"inline\")\n{\n\n\tdocument.getElementById('showall').style.display=\"none\";\n");
/*  557 */           out.write("\tdocument.getElementById('hideall').style.display=\"inline\";\n}\nelse\n{\n\tnewDisplay=\"none\";\n\tdocument.getElementById('showall').style.display=\"inline\";\n\tdocument.getElementById('hideall').style.display=\"none\";\n}\nvar table = document.getElementById(\"allMonitorGroups\");\nrows = table.rows;\nrowcount = rows.length;\nfor( i=1;i<rowcount;i++)\n{\n\tif(rows[i].id.indexOf(\"#monitor\")>=0)\n\trows[i].style.display=newDisplay;\n}\n\nvar plus,minus;\nif(newDisplay=='none')\n{\n\tplus=\"inline\";\n\tminus=\"none\"\n}\nelse\n{\n\tplus=\"none\";\n\tminus=\"inline\"\n}\nvar alldivs =document.getElementsByTagName(\"div\");\nfor(var j=0; j < alldivs.length; j++)\n{\n\tvar singlediv = alldivs[j];\n\tvar id = singlediv.id;\n\tif(id.indexOf(\"monitorHide\")>=0)\n\t{\n\t\tsinglediv.style.display =minus ;\n\t}\n\tif(id.indexOf(\"monitorShow\")>=0)\n\t{\n\t\tsinglediv.style.display =plus;\n\t}\n}\n}\n\nvar rows;\nvar rowcount,start;\nvar idtotoggle;\nvar toggletype;\nfunction toggleChildMos(tempidtotoggle)\n{\n\t\nidtotoggle=tempidtotoggle;\nvar table = document.getElementById(\"allMonitorGroups\");\nrows = table.rows;\n");
/*  558 */           out.write("rowcount = rows.length;\nfor( i=1;i<rowcount;i++)\n{\n\tvar myrow = rows[i];\n\tif(myrow.id==idtotoggle)\n\t{\n\t       if(rows[i].style.display=='none')\n\t\t{\n\t\t    toggletype='none';\n\t\t}\n\t\telse\n\t\t{\n\t\t    toggletype='block';\n\t\t}\n\t\tbreak;\n\t}\n}\nif(toggletype=='none')\n{\n  slideDown();\n}\nelse\n{\nhideOtherRows();\n}\nreturn;\n}\n\nfunction hideOtherRows()\n{\nvar newDisplay = \"block\";\nif (document.all) newDisplay = \"block\"; //IE4+ specific code\nelse newDisplay = \"table-row\"; //Netscape and Mozilla\nvar i;\nfor(i=1;i<rowcount;i++)\n{\n\tif(rows[i].id.indexOf( idtotoggle)!=-1)\n\t{\n\n\t\trows[i].style.display = \"none\";\n\t}\n\n}\nreturn;\n}\n\nfunction slideDown()\n{\nvar newDisplay = \"block\";\nif (document.all) newDisplay = \"block\"; //IE4+ specific code\nelse newDisplay = \"table-row\"; //Netscape and Mozilla\nvar i;\nfor(i=1;i<rowcount;i++)\n{\n\tif(rows[i].id == idtotoggle)\n\t{\n\t\trows[i].style.display = newDisplay;\n\t\trows[i].removeAttribute(\"style\");\n\t\trows[i].className = \"leftcells\";\n\t}\n\telse\n\t{\n\t\trows[i].style.background = \"#FFFFFF\";\n\t}\n}\nreturn;\n}\n\nfunction toggleTreeImage(divname)\n");
/*  559 */           out.write("{\n var hide1=\"monitorHide\"+divname;\n var show1=\"monitorShow\"+divname;\n if(document.getElementById(show1))\n {\n if(document.getElementById(show1).style.display == 'inline')\n {\n\t//if it is to show the child elements just return changing the image of current monitor group level and return\n\tdocument.getElementById(show1).style.display='none';\n\tdocument.getElementById(hide1).style.display='inline';\n\treturn;\n }\n }\n else\n {\n\treturn;\n }\n //else if it is to hide an monitor group then parse through all the child elements and find subgroups and change the images to minus\nvar alldivs =document.getElementsByTagName(\"div\");\nvar i;\nfor(i=0; i <alldivs.length ; i++)\n{\n\tif((alldivs[i].id.indexOf(hide1)) >= 0)\n\t{\n\t\thidediv=document.getElementById(alldivs[i].id) ;\n\t\tif(hidediv)\n\t\t{\n\t\t\tif(hidediv.style.display == 'inline')\n\t\t\thidediv.style.display='none';\n\t\t}\n\t}\n\tif((alldivs[i].id.indexOf(show1)) >= 0)\n\t{\n\t\tvar showdiv;\n\t\tshowdiv=document.getElementById(alldivs[i].id) ;\n\t\tif(showdiv)\n\t\t{\n\t\t\tif(showdiv.style.display == 'none')\n\t\t\tshowdiv.style.display='inline';\n");
/*  560 */           out.write("\t\t}\n\t}\n}\n}\nextArray = new Array(\".jpg\", \".png\", \".jpeg\",\".gif\");\nfunction fnUserConfSubmit(myfrm)\n{\n\t if(trimAll(document.UserConfiguration.theFile.value)!='')\n \t{\n\n \t\tvar fileobj=document.UserConfiguration.theFile;\n \t\tvar file1=fileobj.value;\n \t\tallowSubmit = false;\n \t    if (!file1) return;\n \t    while (file1.indexOf(\"\\\\\") != -1){\n \t   \t\t file1 = file1.slice(file1.indexOf(\"\\\\\") + 1);\n \t    }\n \t   \text = file1.slice(file1.indexOf(\".\")).toLowerCase();\n \t   \tfor (var i = 0; i < extArray.length; i++) {\n \t  \t \tif (extArray[i] == ext)\n \t\t\t{\n \t\t\t    allowSubmit = true;\n \t\t\t    break;\n \t\t\t}\n \t   \t }\n \t    if (!allowSubmit)\n \t\t{\n\n \t        alert(\"");
/*  561 */           out.print(FormatUtil.getString("am.webclient.userconfig.uploadfile.alert1.text"));
/*  562 */           out.write("  \" + (extArray.join(\"  \")) + \"\\n");
/*  563 */           out.print(FormatUtil.getString("am.webclient.userconfig.uploadfile.alert2.text"));
/*  564 */           out.write("\"); //NO I18N\n \t        fileobj.parentNode.innerHTML = fileobj.parentNode.innerHTML;\n \t        return;\n \t\t}\n \t}\n\t \n\tif(trimAll(document.UserConfiguration.userName.value)=='')\n\t{\n\t\talert('");
/*  565 */           out.print(FormatUtil.getString("am.webclient.jsalertforusername.text"));
/*  566 */           out.write("');\n\t\tdocument.UserConfiguration.userName.select();\n\n\t\treturn;\n\t}\n\n\t");
/*      */           
/*  568 */           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  569 */           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  570 */           _jspx_th_c_005fif_005f0.setParent(null);
/*      */           
/*  572 */           _jspx_th_c_005fif_005f0.setTest("${(param.method=='editUser') && (UserConfiguration.authType =='ADAuthentication') && !isOpenLdapUser}");
/*  573 */           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  574 */           if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */             for (;;) {
/*  576 */               out.write("\n\t\tif(jQuery(\"#userDomainListBox\").val() != null){\t\t\t\t\t\t\t\t\t\t\t\t// NO I18N\n\t\t\tdocument.UserConfiguration.ldapdomainValue.value=jQuery(\"#userDomainListBox\").val()\t\t// NO I18N\t\n\t\t\tdocument.UserConfiguration.ldapdomainName.value= \"true\"\n\t\t}else{\n\t\t\talert('");
/*  577 */               out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.select.domain.text"));
/*  578 */               out.write("');\n\t\t\treturn false;\n\t\t}\n\t");
/*  579 */               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  580 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  584 */           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  585 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */           }
/*      */           else {
/*  588 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  589 */             out.write(10);
/*  590 */             out.write(9);
/*      */             
/*  592 */             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  593 */             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  594 */             _jspx_th_c_005fif_005f1.setParent(null);
/*      */             
/*  596 */             _jspx_th_c_005fif_005f1.setTest("${!empty updateProfile && updateProfile=='true'}");
/*  597 */             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  598 */             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */               for (;;) {
/*  600 */                 out.write("\n\t\tif(!isEmailId(document.UserConfiguration.email.value))\n\t\t{\n\t\t\talert('");
/*  601 */                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.email"));
/*  602 */                 out.write("');\n\t\t\tdocument.UserConfiguration.email.select();\n\t\t\treturn;\n\t\t}\n\t");
/*  603 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  604 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  608 */             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  609 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */             }
/*      */             else {
/*  612 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  613 */               out.write("\n\tvar methodChk=myfrm.method.value;\n\tvar updateVar = false;\n\tif(document.UserConfiguration.updateChk != null)\n\t{\n\t\t      updateVar = document.UserConfiguration.updateChk.checked;\n\t}\n\tdocument.UserConfiguration.associatedUserGroups.value=jQuery(\"#usergroupSelect\").val()\t\t// NO I18N\n\t\n\tif(document.UserConfiguration.delegatedAdmin != null && document.UserConfiguration.delegatedAdmin.checked){\n\t\t\n\t\tvar delegatedUserGroup = false;\n\t\tvar delegatedMG = false;\n\t\t\n\t\tvar usergroups = jQuery(\"#usergroupSelect\").val();\t\t// NO I18N \n\t\t\n\t\tif(usergroups != null){\n\t\t\tdelegatedUserGroup = true;\n\t\t}\n\t\tvar selectCount = document.UserConfiguration.select.length\n\t\tif(selectCount){\n\t\t\tfor(i=0;i<selectCount;i++)\n\t\t\t{\n\t\t\t\tdelegatedMG = document.UserConfiguration.select[i].checked\n\t\t\t\tif(delegatedMG){\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t}\n\t\t}else{\n\t\t\tif(document.UserConfiguration.select.checked){\n\t\t\t\tdelegatedMG = true;\n\t\t\t}\n\t\t}\n\t\t\n\t\tif(!delegatedMG && !delegatedUserGroup){\n\t\t\talert('");
/*  614 */               out.print(FormatUtil.getString("am.webclient.delegatedadmin.adduser.check.text"));
/*  615 */               out.write("');\n\t\t\treturn;\n\t\t}\n\t\t\n\t}\n\t\n\tif((methodChk==\"createUser\")||((methodChk==\"updateUser\")&&(updateVar==true))) // compares the password & conf password only if update password is enabled in edit user or it is create new user\n \t{\n\t\tif((trimAll(document.UserConfiguration.password.value)=='')||(trimAll(document.UserConfiguration.confirmpassword.value)==''))\n\t\t{\n\t\t\talert('");
/*  616 */               out.print(FormatUtil.getString("am.webclient.jsalertforpassword.text"));
/*  617 */               out.write("');\n\t\t\tdocument.UserConfiguration.password.select();\n\t\t\treturn;\n\n\t\t}\n\t\tif(document.UserConfiguration.password.value!=document.UserConfiguration.confirmpassword.value)\n\t\t{\n\t\t\talert('");
/*  618 */               out.print(FormatUtil.getString("am.webclient.jsalertforconfirmpassword.text"));
/*  619 */               out.write("');\n\t\t\tdocument.UserConfiguration.password.select();\n\t\t\treturn;\n\t\t}\n\t\t\n\t\t\n        var selectMessage=\"notchecked\";\n\n        if(document.UserConfiguration.checked)\n        {\n            for(i=0;i<myfrm.length;i++)\n            {\n                if(myfrm.elements[i].type == \"checkbox\" && myfrm.elements[i].checked==true)\n                {\n                    selectMessage=\"yeschecked\";\n                }\n            }\n        }\n\n    /********ME-SOLUTIONS CODE CHANGES STARTS HERE************/\n    //to get user password policy enabled status\n        var pwdPolicyState = false;\n    \tvar httpreq = getHTTPObject();\n\n        httpreq.open(\"GET\",\"/showresource.do?method=getUserPwdPolicy\"); //NO I18N\n        httpreq.onreadystatechange = function(){\n\t\tif(httpreq.readyState == 4 && httpreq.status == 200){\n            var pwdPolicyState1=httpreq.responseText.toString();\n            if (pwdPolicyState1==\"false\")\n                {\n                   pwdPolicyState=false;\n                }\n            else if (pwdPolicyState1==\"true\")\n");
/*  620 */               out.write("                {\n                   pwdPolicyState=true;\n                }\n\n            if (pwdPolicyState==true)\n            {\n                var validateState=pwdPolicyValidate();\n                if (validateState==false)\n                {\n                    return;\n                }\n                else\n                    {\n\t\t\t\t\t\tjQuery('select[name=\"groups\"]').removeAttr('disabled');\t//NO I18N    \n\t\t\t\t\t\tjQuery('input[name=\"delegatedAdmin\"]').removeAttr('disabled');\t//NO I18N    \n                        document.UserConfiguration.submit();\n                    }\n            }\n            else\n                {\n\t\t\t\t\tjQuery('select[name=\"groups\"]').removeAttr('disabled');\t//NO I18N   \n\t\t\t\t\tjQuery('input[name=\"delegatedAdmin\"]').removeAttr('disabled');\t//NO I18N      \n\t                document.UserConfiguration.submit();\n                }\n\n\t}\n        };\n        httpreq.send(null);\n\n/*******ME-SOLUTIONS CODE CHANGES ENDS HERE ********/\n\t}\n\telse if((methodChk==\"updateUser\"))//No i18n\n\t{\n\t\tjQuery('select[name=\"groups\"]').removeAttr('disabled');\t//NO I18N    \n");
/*  621 */               out.write("\t\tjQuery('input[name=\"delegatedAdmin\"]').removeAttr('disabled');\t//NO I18N     \n\t\tdocument.UserConfiguration.submit();\n\t}\n}\n\n\tfunction addNewDomain(frm){\n\n\t\tvar domainname = jQuery.trim(document.UserConfiguration.newDomainName.value)\n\t\tvar domaincontroller = jQuery.trim(document.UserConfiguration.newDomainController.value)\n\t\tvar domainport = jQuery.trim(document.UserConfiguration.newDomainPort.value)\n\t\t\n\t\tif(domainname == ''){\n\t\t\talert('");
/*  622 */               out.print(FormatUtil.getString("am.webclient.useradministration.domain.name.empty.text"));
/*  623 */               out.write("');//No I18N\n\t\t\tjQuery(\"[name='newDomainName']\").focus();\t//No I18N\n\t\t\treturn false;\n\t\t}\n\t\t\n\t\tif(domaincontroller == ''){\n\t\t\talert('");
/*  624 */               out.print(FormatUtil.getString("am.webclient.useradministration.domain.controller.empty.text"));
/*  625 */               out.write("');//No I18N\n\t\t\tjQuery(\"[name='newDomainController']\").focus();\t//No I18N\n\t\t\treturn false;\n\t\t}\n\t\t\n\t\tif(domainport == ''){\n\t\t\talert('");
/*  626 */               out.print(FormatUtil.getString("am.webclient.useradministration.domain.port.empty.text"));
/*  627 */               out.write("');//No I18N\n\t\t\tjQuery(\"[name='newDomainPort']\").focus();\t//No I18N\n\t\t\treturn false;\n\t\t}\n\t\t");
/*  628 */               if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */                 return;
/*  630 */               out.write("\n\t\t\n\t\tdocument.UserConfiguration.submit();\n\t}\n\n\tfunction addnewUserGroup(frm){\n\t\t\n\t\tvar groupname = jQuery.trim(document.UserConfiguration.usergroupName.value)\n\t\t\n\t\tif(groupname == \"\"){\n\t\t\talert('");
/*  631 */               out.print(FormatUtil.getString("am.webclient.useradministration.usergroup.empty.alert.text"));
/*  632 */               out.write("');//No I18N\n\t\t\tjQuery(\"[name='usergroupName']\").focus();\t//No I18N\n\t\t\treturn false;\n\t\t}else{\n\t\t\tdocument.UserConfiguration.userGroupUsers.value=jQuery(\"#chosenSelectBox\").val()\t\t// NO I18N\n\t\t\t");
/*      */               
/*  634 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  635 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  636 */               _jspx_th_c_005fif_005f2.setParent(null);
/*      */               
/*  638 */               _jspx_th_c_005fif_005f2.setTest("${param.method=='editUserGroup' && isDomainGroup && !isOpenLdapGroup}");
/*  639 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  640 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/*  642 */                   out.write("\n\t\t\t\tif(jQuery(\"#domainListBox\").val() != null){\t\t\t\t\t\t\t\t\t\t\t\t// NO I18N\n\t\t\t\t\tdocument.UserConfiguration.ldapdomainValue.value=jQuery(\"#domainListBox\").val()\t\t// NO I18N\n\t\t\t\t\tdocument.UserConfiguration.ldapdomainName.value= \"true\"\n\t\t\t\t}else{\n\t\t\t\t\talert('");
/*  643 */                   out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.select.domain.text"));
/*  644 */                   out.write("');\t\t\t//No I18N\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t");
/*  645 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  646 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  650 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  651 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */               }
/*      */               else {
/*  654 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  655 */                 out.write("\n\t\t\t\n\t\t\t\n\t\t\tvar methodValue = frm.method.value\n\t\t\tif(methodValue != 'updateUserGroup'){\n\t\t\t\tdocument.UserConfiguration.method.value='createUserGroup'\t\t// NO I18N\n\t\t\t}else{\n\t\t\t\tdocument.UserConfiguration.groupid.value='");
/*  656 */                 if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                   return;
/*  658 */                 out.write("'\n\t\t\t}\n\t\t\tdocument.UserConfiguration.submit();\n\t\t\t}\n\t}\n\n\n\n\n\n\n\n\n\n/*ME-SOLUTIONS CODE CHANGES STARTS HERE */\n// to validate the password\nfunction pwdPolicyValidate()\n{\n        var strPass=document.UserConfiguration.password.value;\n        var passLength=strPass.length\n\n\t\tif((passLength<8 )||(passLength>255))\n\t\t{\n            //Password Length > 8 and < 255 characters\n\t\t\talert(\"");
/*  659 */                 out.print(FormatUtil.getString("am.webclient.userconfig.password.Length.text"));
/*  660 */                 out.write("\"); //NO I18N\n\t\t\tdocument.UserConfiguration.password.select();\n\t\t\treturn false;\n\t\t}\n\n        var checkForSame=checkUserNameAsPwd();\n        if (checkForSame==false)\n        {\n            //'UserName should not be part of Password'\n\t\t\talert(\"");
/*  661 */                 out.print(FormatUtil.getString("am.webclient.userconfig.password.usernotpart.text"));
/*  662 */                 out.write("\"); //NO I18N\n\t\t\tdocument.UserConfiguration.password.select();\n\t\t\treturn false;\n        }\n        //\\\\({|*,`~.)}'\"!@#$%^&+=_-\n    var re=/^.*(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[({|,`~.})\\'\\\"!@#$%^&+=_\\-\\?\\[\\]\\*\\\\><]).*$/;\n    if (!re.test(strPass))\n            {\n                alert(\"");
/*  663 */                 out.print(FormatUtil.getString("am.webclient.userconfig.password.vaildation.text"));
/*  664 */                 out.write("\"); //NO I18N\n                return false;\n            }\n\n}\n//check to username as password\nfunction checkUserNameAsPwd()\n{\n    var strUserName=document.UserConfiguration.userName.value;\n    var strPass=document.UserConfiguration.password.value;\n    var userNameLen=strUserName.length;\n    var userPassLen=strPass.length;\n    var i=0;\n\n    for (i=0;i<userPassLen;i++)\n    {\n        var str=strPass.substr(i,userNameLen);\n        var lstrUserName=strUserName.toLowerCase();\n        var lstr=str.toLowerCase();\n        if (lstrUserName==lstr)\n            {\n               return false;\n            }\n    }\n}\n/*ME-SOLUTIONS CODE CHANGES ENDS HERE */\n\n\n\nfunction showMonitorGroups()\n{\n\tvar userRole = document.UserConfiguration.groups.value;\n\tif(userRole=='USERS')\n\t{\n\t\tjQuery(\"#restrictedadminuser\").hide()\t\t// NO I18N\n\t\tjQuery(\"#mgselection\").hide()\t\t// NO I18N\n\t\t\n\t}else if(userRole == 'ADMIN'){\n\t\t");
/*  665 */                 if (_jspx_meth_c_005fchoose_005f1(_jspx_page_context))
/*      */                   return;
/*  667 */                 out.write("\n\t}\n\telse\n\t{\n\t\tjQuery(\"#restrictedadminuser\").hide()\t\t// NO I18N\n\t\tjQuery(\"#mgselection\").show()\t// NO I18N\n\t}\n}\n\nfunction isDelegatedAdmin(){\n\t\n\tif(document.UserConfiguration.delegatedAdmin.checked){\n\t\tjQuery(\"#mgselection\").show()\t// NO I18N\n\t}else{\n\t\tjQuery(\"#mgselection\").hide()\t// NO I18N\n\t}\n}\n\nfunction showUserGroupMGs()\n{\n\tif(document.UserConfiguration.groupsRole.value=='USERS' || document.UserConfiguration.groupsRole.value=='ADMIN')\n\t{\n\t\tdocument.UserConfiguration.userGroupMG.disabled = true;\n\t\tjQuery(\"#ugallgroups\").hide();\t\t// NO I18N\n\t}\n\telse\n\t{\n\t\tdocument.UserConfiguration.userGroupMG.disabled = false;\n\t}\n}\n\n\nfunction selectUserGroupMG(checkboxObj){\n\t\n\tif(!checkboxObj.checked)\n\t{\n\t\tjQuery(\"#ugallgroups\").slideUp('slow')\t\t// NO I18N\t\n\t}\n\telse\n\t{\n\t\tjQuery(\"#ugallgroups\").slideDown('slow')\t\t// NO I18N\n\t}\n}\n\nfunction doInitStuffOnBodyLoad()\n{\n\t\n  var methodname = document.UserConfiguration.method.value;\n  if(methodname==\"updateUser\" && (document.UserConfiguration.groups.value=='OPERATOR' || document.UserConfiguration.groups.value=='MANAGER'  || document.UserConfiguration.groups.value=='ADMIN')) {\n");
/*  668 */                 out.write("\t  jQuery(\"#allgroups\").show()\t\t// NO I18N\n  }\n\n  if(methodname==\"editUser\" || methodname==\"updateUser\"){\n  \t");
/*  669 */                 if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */                   return;
/*  671 */                 out.write("\n  }\n\n  if(methodname == \"updateUserGroup\"){\n\t  document.UserConfiguration.userGroupMG.checked = true;\n\t  jQuery(\"#ugallgroups\").show();\t\t// NO I18N\n  }\n\n if(methodname==\"updateUser\" && document.UserConfiguration.groups.value=='USERS') {\n\t jQuery(\"#mgselection\").hide()\t\t// NO I18N\n  }\n \n if(methodname==\"createUser\")\n  {\n \t\tjQuery(\"#mgselection\").show()\t\t// NO I18N\n \t\tjQuery(\"#allgroups\").show()\t\t// NO I18N\n  }\n}\n\nfunction updatePassword()\n{\n\t//alert(document.UserConfiguration.updateChk.checked);\n if(document.UserConfiguration.updateChk.checked==true)\n {\n\n\tdocument.getElementById(\"updatePWD\").style.display='block';\n   if (document.getElementById(\"loginuser\").value==document.UserConfiguration.userName.value)\n\t{\n        document.getElementById(\"oldPWD\").style.display='block';\n    }\n    else\n    {\n        document.getElementById(\"oldPWD\").style.display='none';\n    }\n\n\n }\n else\n {\n \tdocument.getElementById(\"updatePWD\").style.display='none';\n \tdocument.getElementById(\"oldPWD\").style.display='none';\n }\n}\nfunction fnChangePhoto(){\n");
/*  672 */                 out.write("\tif(document.getElementById(\"changePhoto\").style.display == 'none')\n\t{\n\t\tdocument.getElementById(\"changePhoto\").style.display='block';\n\t\tdocument.UserConfiguration.change.value=true;\n\t}\n\thideDiv('changeordelete');\n}\nfunction hidefnChangePhoto(){\n\tif(document.getElementById(\"changePhoto\").style.display == 'block')\n\t{\n\t\tdocument.getElementById(\"changePhoto\").style.display='none';\n\t\tdocument.UserConfiguration.change.value=false;\n\t}\n\tshowDiv('changeordelete');\n}\nfunction fnDeletePhoto(){\n\tif(confirm(\"");
/*  673 */                 out.print(FormatUtil.getString("am.webclient.jsAlertforConfirmDeleteImage.text"));
/*  674 */                 out.write("\"))\n\t{\n\t\tdocument.getElementById(\"showphoto\").style.display='none';\n\t\tdocument.getElementById(\"myCol\").style.width='0%';\n\t\tdocument.getElementById(\"changePhoto\").style.display='block';\n\t\tdocument.UserConfiguration.deletePic.value=true;\n\t}\n}\n\n\n\n\n\n\n\n$(\"#fields_table\").hover(// NO I18N\n\t\t\n\tfunction()\n\t{\n\n\t\t$('#fields_table').find('.customfieldsanchor').each(function(){\t// NO I18N\n\t\t\t$(this).addClass('staticlinks');\t// NO I18N\n\t\t});\n\n\t},\n\tfunction()\n\t{\n\t\t$('#fields_table').find('.staticlinks').each(function(){\t// NO I18N\n\t\t\t$(this).removeClass('staticlinks');\t// NO I18N\n\t\t});\n\t}\n\t\n);\n\n$(document).ready(function(){\n\t\n\tjQuery(\".chosenselect\").chosen( // NO I18N\n\t{\n\t  no_results_text: '");
/*  675 */                 out.print(FormatUtil.getString("am.common.search.no.result.match.text"));
/*  676 */                 out.write("'\n\t});// NO I18N\n\t\tshowMonitorGroups()\n\t");
/*  677 */                 if (_jspx_meth_c_005fchoose_005f2(_jspx_page_context))
/*      */                   return;
/*  679 */                 out.write("\n\t\n\t\n\t$('input[name=usergroupName]').blur(function(){\n\t\t\n\t\t");
/*  680 */                 if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*      */                   return;
/*  682 */                 out.write("\n\t\t});\n\t\n\t\n\t$('input[name=newDomainName]').blur(function(){\n\t\t\t\n\t\t\t");
/*  683 */                 if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */                   return;
/*  685 */                 out.write("\n\t\t\t});\n\t");
/*  686 */                 if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
/*      */                   return;
/*  688 */                 out.write("\n\t\t\n\t$('.search-field input:text').css('height', 25);\t\t// NO I18N\n\t$('[name=newDomainService]').change(function () {\n        \tvar opt = $('[name=newDomainService]').val();\n\tif(opt == 'LDAP'){\n\t$('#sslenablerow').closest('tr').show(); // NO I18N\n\t}\n\tif(opt == 'ACTIVE DIRECTORY'){\n\t$('#sslenablerow').closest('tr').hide(); // NO I18N\n\t}\n    });\n});\n\n\nfunction displayAddUserGroup(){\n\t\n\tjQuery(\"#customfieldsfullList\").hide();\t\t// NO I18N\n\tjQuery(\"#usergroupconfiguration\").show();\t\t// NO I18N\n\tvar groupauth = document.location.hash\n\tif(groupauth.indexOf(\"uc/Ldap\") != -1){\n\t\t\n\t\ttoggleUserGroupDiv('Ldap')\t\t// NO I18N\n\t\t\n\t}else if(groupauth.indexOf(\"uc/ADAuth\") != -1){\n\t\t\n\t\ttoggleUserGroupDiv('adgroup')\t\t// NO I18N\n\t}\n\telse{\n\t\t\n\t\ttoggleUserGroupDiv('localgroup')\t\t// NO I18N\n\t}\n\t\n}\n\n\n\tfunction toggleCustomFieldDiv1(divname){\n\t\t\t \n\t        document.location.hash=\"uc/\"+divname\t// No I18N\n\t\t$('.liselectedforcf').each(function(){$(this).removeClass('liselectedforcf');});\t\t// No I18N\n\t\t$('li#'+divname+'_tabs').addClass('liselectedforcf');\t\t// No I18N\n");
/*  689 */                 out.write("\t\t$(\"#customfieldsfullList div[id^='eachTableDiv_']\").hide(); \t// No I18N\n\t\t$(\"#customfieldsfullList div[id^='eachTableDiv_\"+divname+\"']\").show(); \t// No I18N\n\t\t$(\"#customfieldsfullList div[id^='helpdiv_']\").hide(); \t// No I18N\n\t\t$(\"#customfieldsfullList div[id^='helpdiv_\"+divname+\"']\").show(); \t// No I18N\n\n\t}\n\t\n\tfunction toggleUserGroupDiv(value){\n\t\t\n\t\tdocument.location.hash=\"uc/\"+value\t\t// NO I18N\n\t\t$('.liselectedforcf').each(function(){$(this).removeClass('liselectedforcf');});\t\t// No I18N\n\t\t$('li#'+value+'_tabs').addClass('liselectedforcf');\t\t// No I18N\n\t\t$(\"#usergroupconfiguration div[id^='addusergroup_']\").hide(); \t// No I18N\n\t\t$(\"#usergroupconfiguration div[id^='addusergroup_\"+value+\"']\").show(); \t// No I18N\n\t\t$(\"#usergroupconfiguration div[id^='helpdiv_']\").hide(); \t// No I18N\n\t\t$(\"#usergroupconfiguration div[id^='helpdiv_\"+value+\"']\").show(); \t// No I18N\n\t\t\n\t}\n\t\n\tfunction fnFetchLdapUsergroups(formname){\n\t\t\n\t\tvar d = new Date();\n\t\tvar username = formname.ldapdomainUsername.value\n\t\tvar password = formname.ldapdomainPassword.value\n");
/*  690 */                 out.write("\t\tvar domainvalue = formname.ldapdomainValue.value\n\t\tvar domainname = formname.ldapdomainName.value\n\t\tvar domaincontrol = formname.ldapdomainController.value\n\t\tvar searchFilter = formname.ldapsearchFilter.value\n\t\tvar ldapport = formname.ldapPort.value\n\t//\tvar searchbase = formname.ldapsearchBase.value\n\t\tvar fetchValue = formname.fetchLDAP.value\n\t\t\n\t\tif(domainvalue == 'select'){\n\t\t\t alert(\"");
/*  691 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.select.domain.text"));
/*  692 */                 out.write("\");\n\t\t      return;\n\t\t}else if(domainvalue == 'new'){\n\n\t\t    if(domainname == \"\")//No i18n\n\t\t    {\n\t\t      alert(\"");
/*  693 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.domain.text"));
/*  694 */                 out.write("\");\n\t\t      return;\n\t\t    }\n\t\t    if(domaincontrol == \"\")//No i18n\n\t\t    {\n\t\t      alert(\"");
/*  695 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.domaincontroller.text"));
/*  696 */                 out.write("\");\n\t\t      return;\n\t\t    }\n\t\t    if(username == \"\")//No i18n\n\t\t    {\n\t\t      alert(\"");
/*  697 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.username.text"));
/*  698 */                 out.write("\");\n\t\t      return;\n\t\t    }\n\t\t}\n\t\t\n\t\t\n\t\t  if(password == \"\")//No i18n\n\t\t  {\n\t\t      alert(\"");
/*  699 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.password.text"));
/*  700 */                 out.write("\");\n\t\t      return;\n\t\t  }\n\t\t  if(domainvalue != \"new\")//No i18n\n\t\t  {\n\t\t\tvar arr = domainvalue.split(\"##\");//NO I18N\n\t\t\tdomainvalue = arr[1];\n\t\t\tdomainUsername = arr[2];\n\t\t  }\n\t\n\t\t  var parameters = { 'method' : 'fetchLDAPUserGroups','domainName' : domainname,'ldapPort' : ldapport,'domainController' : domaincontrol,'searchFilter' : searchFilter,'domainUsername' : username,'domainPassword' : password,'fetchldap':fetchValue,'domainValue' : domainvalue,'date' : d};//NO I18N\n\t\t  openWin(\"/admin/userconfiguration.do\",parameters,\"width=900,height=600,left=100,top=100,resizable=yes,scrollbars=yes\",\"popup\");//NO I18N\n\t\t\n\t}\n\n\n   function fnFetchADUsers(myFrm)\n   {\n\t  var d = new Date();\n\t  var url;\n\t  var usr = myFrm.domainUsername.value;\n\t  var cred = myFrm.domainPassword.value;\n\t  var dc = myFrm.domainController.value;\n\t  var domain = myFrm.domainName.value;\n\t  var domainValue =myFrm.domainValue.value;\n\t  var searchFilter = myFrm.searchFilter.value;\n\t  var fetchValue = myFrm.fetchActiveDirectory.value\n\t  var port = myFrm.domainPort.value\n");
/*  701 */                 out.write("\t  var authType = myFrm.domainAuthentication.value\n\t  var port = myFrm.domainPort.value\n\t  if(domainValue == \"select\")//No i18n\n\t  {\n\t      alert(\"");
/*  702 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.select.domain.text"));
/*  703 */                 out.write("\");\n\t      return;\n\t  }\n\t  else if(domainValue == \"new\")\n\t  {\n\t    if(domain == \"\")//No i18n\n\t    {\n\t      alert(\"");
/*  704 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.domain.text"));
/*  705 */                 out.write("\");\n\t      return;\n\t    }\n\t    if(dc == \"\")//No i18n\n\t    {\n\t      alert(\"");
/*  706 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.domaincontroller.text"));
/*  707 */                 out.write("\");\n\t      return;\n\t    }\n\t    if(usr == \"\")//No i18n\n\t    {\n\t      alert(\"");
/*  708 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.username.text"));
/*  709 */                 out.write("\");\n\t      return;\n\t    }\n\t  }\n\t  \n\t  if(port == \"\"){\n\t    \talert(\"");
/*  710 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.port.text"));
/*  711 */                 out.write("\");\n\t\t    return;\n\t  }\n\t  if(usr == \"\")//No i18n\n\t  {\n\t\t  alert(\"");
/*  712 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.username.text"));
/*  713 */                 out.write("\");\n\t      return;\n\t  }\n\t  if(cred == \"\")//No i18n\n\t  {\n\t      alert(\"");
/*  714 */                 out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.alert.password.text"));
/*  715 */                 out.write("\");\n\t      return;\n\t  }\n\t  \n\t  if(domainValue != \"new\")//No i18n\n\t  {\n\t\tvar arr = domainValue.split(\"##\");//NO I18N\n\t\tdomainValue = arr[1];\n\t\tdomainUsername = arr[2];\n\t  }\n\t  //url =  \"/admin/userconfiguration.do?method=fetchADUser&domainName=\"+domain+\"&domainController=\"+dc+\"&searchFilter=\"+searchFilter+\"&domainUsername=\"+usr+\"&domainPassword=\"+cred+\"&domainValue=\"+domainValue+\"&date=\"+d;//No i18n\n\t  var parameters = { 'method' : 'fetchADUser','domainName' : domain,'domainController' : dc,'searchFilter' : searchFilter,'domainUsername' : usr,'domainPassword' : cred,'port':port,'domainValue' : domainValue,'fetchValue':fetchValue,'authType':authType,'date' : d};//NO I18N\n\t  openWin(\"/admin/userconfiguration.do\",parameters,\"width=1024,height=700,left=100,top=100,resizable=yes,scrollbars=yes\",\"popup\");//NO I18N\n}\n\n    function openWin(url, params,popUpSize, name)\n    {\n\t\t\tvar form = document.createElement(\"form\");//NO I18N\n\t\t\tform.setAttribute(\"method\", \"post\");//NO I18N\n\t\t\tform.setAttribute(\"action\", url);//NO I18N\n");
/*  716 */                 out.write("\t\t\tform.setAttribute(\"target\", name);//NO I18N\n\t\t\tfor (var i in params) {\n\t\t\t\tif (params.hasOwnProperty(i)) {\n\t\t\t\t\tvar input = document.createElement('input');//NO I18N\n\t\t\t\t\tinput.type = 'hidden';//NO I18N\n\t\t\t\t\tinput.name = i;\n\t\t\t\t\tinput.value = params[i];\n\t\t\t\t\tform.appendChild(input);\n\t\t\t\t}\n\t\t\t}\n\t\t\tdocument.body.appendChild(form);\n\t\t\twindow.open(\"/jsp/ADUsersList.jsp\", name, popUpSize);//NO I18N\n\t\t\tform.submit();\n\t\t\tdocument.body.removeChild(form);\n\t}\n\n   \n\nfunction showDomainConfig(selDomain,domain){\n\nvar dval = selDomain.options[selDomain.selectedIndex].value;\n  if(dval == \"new\")\n  {\n      jQuery(\"#usersList\").show();\t\t// NO I18N\n      jQuery(\"#usersList2\").show();\t\t// NO I18N\n      document.forms[1].domainUsername.value = ''\n      document.forms[1].domainController.value = ''\n      document.forms[1].domainPassword.value = ''\n      return;\n  }\n  else if(dval != \"select\"){\n      \t\tvar arr = dval.split(\"##\");\n      \t\tif(arr[4] == 'LDAP'){\n      \t\t\tdocument.forms[1].domainAuthentication.value=arr[4]\n\t\t\t$('#sslenablerow').closest('tr').show(); // NO I18N\n");
/*  717 */                 out.write("\t\t\tif(arr[5] == 'true'){\n\t\t\t\t$(\"input[name='sslenable']\").attr('checked',true);// NO I18N\n\t\t\t}else{\n\t\t\t\t$(\"input[name='sslenable']\").attr('checked',false);// NO I18N\n\t\t\t}\n      \t\t}else{\n      \t\t\tdocument.forms[1].domainAuthentication.value='ACTIVE DIRECTORY'\n\t\t\t$('#sslenablerow').closest('tr').hide(); // NO I18N\n      \t\t}\n      \t\t\n    \t\tdocument.forms[1].domainPort.value=arr[3]\n    \t\tif(arr[2] != null){\n    \t\t\tdocument.forms[1].domainUsername.value=arr[2];\n  \t\t\t}\n    \t    document.forms[1].domainController.value=arr[0];\n      \t\tjQuery(\"#usersList\").hide();\t\t// NO I18N\n      \t\tjQuery(\"#usersList2\").show()\t\t// NO I18N\n      \t\tdocument.forms[1].domainPassword.value = ''\n      \n    }\n   else{\n      document.getElementById(userArea+\"2\").style.display='none';\n      document.getElementById(userArea).style.display='none';\n      document.forms[1].domainPassword.value = ''\n    }\n  return;\n}\n\n</script>\n\n<input type=\"hidden\" name=\"loginuser\" id=\"loginuser\" value=\"");
/*  718 */                 out.print(request.getRemoteUser());
/*  719 */                 out.write("\">\n<!-- ME-SOLUTIONS USER MGMT CODE CHANGES STARTS HERE -->\n");
/*      */                 
/*  721 */                 String errType = request.getParameter("errtype");
/*  722 */                 if ((errType != null) && (!errType.equals("")) && (!errType.equals("noerror")))
/*      */                 {
/*      */ 
/*  725 */                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n<tr><td> \n  ");
/*      */                   
/*      */ 
/*  728 */                   if ((errType != null) && (errType.equals("restrictUpdateUsername")))
/*      */                   {
/*      */ 
/*  731 */                     out.write("\n<img src=\"../images/icon_message_failure.gif\" align=\"absmiddle\"> &nbsp;\n<span class=\"bodytext\">\n  \n          ");
/*  732 */                     out.print(FormatUtil.getString("am.webclient.global.nonAdminUpdateUsernamePermission.text"));
/*  733 */                     out.write("\n    \n    </span>\n    ");
/*      */                   }
/*      */                   else
/*      */                   {
/*  737 */                     out.write("\n<img src=\"../images/icon_message_success.gif\" align=\"absmiddle\"> &nbsp;\n<span class=\"bodytext\">\n    ");
/*      */                     
/*      */ 
/*  740 */                     if ((errType != null) && (errType.equals("pwdinhistory")))
/*      */                     {
/*      */ 
/*  743 */                       out.write("\n          ");
/*  744 */                       out.print(FormatUtil.getString("am.webclient.userconfig.password.historylast.text"));
/*  745 */                       out.write("\n    ");
/*      */ 
/*      */                     }
/*  748 */                     else if ((errType != null) && (errType.equalsIgnoreCase("pwdempty")))
/*      */                     {
/*      */ 
/*  751 */                       out.write("\n            ");
/*  752 */                       out.print(FormatUtil.getString("am.webclient.jsalertforpassword.text"));
/*  753 */                       out.write("\n      ");
/*      */ 
/*      */                     }
/*  756 */                     else if ((errType != null) && (errType.equalsIgnoreCase("confirmpwdempty")))
/*      */                     {
/*      */ 
/*  759 */                       out.write("\n            ");
/*  760 */                       out.print(FormatUtil.getString("am.webclient.jsalertforpassword.text"));
/*  761 */                       out.write("\n     ");
/*      */ 
/*      */ 
/*      */                     }
/*  765 */                     else if ((errType != null) && (errType.equalsIgnoreCase("pwdnotequal")))
/*      */                     {
/*      */ 
/*      */ 
/*  769 */                       out.write("\n            ");
/*  770 */                       out.print(FormatUtil.getString("am.webclient.jsalertforconfirmpassword.text"));
/*  771 */                       out.write("\n     ");
/*      */ 
/*      */                     }
/*  774 */                     else if ((errType != null) && (errType.equalsIgnoreCase("pwdlen")))
/*      */                     {
/*      */ 
/*  777 */                       out.write("\n           ");
/*  778 */                       out.print(FormatUtil.getString("am.webclient.userconfig.password.Length.text"));
/*  779 */                       out.write("\n     ");
/*      */                     }
/*  781 */                     else if ((errType != null) && (errType.equalsIgnoreCase("pwdlen")))
/*      */                     {
/*      */ 
/*  784 */                       out.write("\n           ");
/*  785 */                       out.print(FormatUtil.getString("am.webclient.userconfig.password.Length.text"));
/*  786 */                       out.write("\n      ");
/*      */ 
/*      */                     }
/*  789 */                     else if ((errType != null) && (errType.equalsIgnoreCase("notRegEx")))
/*      */                     {
/*      */ 
/*  792 */                       out.write("\n      ");
/*  793 */                       out.print(FormatUtil.getString("am.webclient.userconfig.password.vaildation.text"));
/*  794 */                       out.write("\n      ");
/*      */ 
/*      */                     }
/*  797 */                     else if ((errType != null) && (errType.equalsIgnoreCase("sameasuser")))
/*      */                     {
/*      */ 
/*  800 */                       out.write("\n      ");
/*  801 */                       out.print(FormatUtil.getString("am.webclient.userconfig.passwordrules.noduplication.text"));
/*  802 */                       out.write("\n      ");
/*      */ 
/*      */                     }
/*  805 */                     else if (errType.equals("oldpwdnotmatch"))
/*      */                     {
/*      */ 
/*  808 */                       out.write("\n            ");
/*  809 */                       out.print(FormatUtil.getString("am.webclient.changepassword.oldpasswordfailure.text"));
/*  810 */                       out.write("\n    ");
/*      */ 
/*      */                     }
/*  813 */                     else if (errType.equals("pwdinconchar"))
/*      */                     {
/*      */ 
/*  816 */                       out.write("\n            ");
/*  817 */                       out.print(FormatUtil.getString("am.webclient.userconfig.passwordrules.threeconsecutive.text"));
/*  818 */                       out.write("\n    ");
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*  823 */                     out.write("\n</span>\n");
/*      */                   }
/*  825 */                   out.write("\n</td></tr></table>\n");
/*      */                 }
/*  827 */                 out.write("\n<!-- ME-SOLUTIONS USER MGMT  CODE CHANGES ENDS HERE -->\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\" style=\"padding-top:10px\">\n");
/*  828 */                 String userAdminLink = BreadcrumbUtil.getUserAdministrationPage(request);
/*  829 */                 out.write("\n\t<tr>\n\t");
/*      */                 
/*  831 */                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  832 */                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/*  833 */                 _jspx_th_c_005fif_005f7.setParent(null);
/*      */                 
/*  835 */                 _jspx_th_c_005fif_005f7.setTest("${param.method=='editUser'}");
/*  836 */                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/*  837 */                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                   for (;;) {
/*  839 */                     out.write(10);
/*  840 */                     out.write(9);
/*      */                     
/*  842 */                     if (EnterpriseUtil.isAdminServer())
/*      */                     {
/*  844 */                       out.write("\n\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/*  845 */                       out.print(BreadcrumbUtil.getEnterpriseAdminPage(request));
/*  846 */                       out.write(" &gt; ");
/*  847 */                       if (userAdminLink != "") {
/*  848 */                         out.write(32);
/*  849 */                         out.print(userAdminLink);
/*  850 */                         out.write(" &gt;");
/*      */                       }
/*  852 */                       out.write(" <span class=\"bcactive\"> ");
/*  853 */                       out.print(FormatUtil.getString("am.webclient.userprofile.text"));
/*  854 */                       out.write("</span></td>\n\t");
/*      */                     }
/*      */                     else
/*      */                     {
/*  858 */                       out.write("\n\t <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/*  859 */                       out.print(BreadcrumbUtil.getAdminPage(request));
/*  860 */                       out.write(" &gt; ");
/*  861 */                       if (userAdminLink != "") {
/*  862 */                         out.write(32);
/*  863 */                         out.print(userAdminLink);
/*  864 */                         out.write(" &gt;");
/*      */                       }
/*  866 */                       out.write(" <span class=\"bcactive\"> ");
/*  867 */                       out.print(FormatUtil.getString("am.webclient.userprofile.text"));
/*  868 */                       out.write("</span></td>\n\t ");
/*      */                     }
/*  870 */                     out.write(10);
/*  871 */                     out.write(9);
/*  872 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/*  873 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  877 */                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/*  878 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*      */                 }
/*      */                 else {
/*  881 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*  882 */                   out.write(10);
/*  883 */                   out.write(9);
/*      */                   
/*  885 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  886 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/*  887 */                   _jspx_th_c_005fif_005f8.setParent(null);
/*      */                   
/*  889 */                   _jspx_th_c_005fif_005f8.setTest("${param.method!='editUser'}");
/*  890 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/*  891 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/*  893 */                       out.write(10);
/*  894 */                       out.write(9);
/*      */                       
/*  896 */                       if (EnterpriseUtil.isAdminServer())
/*      */                       {
/*  898 */                         out.write("\n\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/*  899 */                         out.print(BreadcrumbUtil.getEnterpriseAdminPage(request));
/*  900 */                         out.write(" &gt; ");
/*  901 */                         if (userAdminLink != "") {
/*  902 */                           out.write(32);
/*  903 */                           out.print(userAdminLink);
/*  904 */                           out.write(" &gt;");
/*      */                         }
/*  906 */                         out.write(" <span class=\"bcactive\"> ");
/*  907 */                         out.print(FormatUtil.getString("am.webclient.newuser.text"));
/*  908 */                         out.write("</span></td>\n\t");
/*      */                       }
/*      */                       else
/*      */                       {
/*  912 */                         out.write("\n\t <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/*  913 */                         out.print(BreadcrumbUtil.getAdminPage(request));
/*  914 */                         out.write(" &gt; ");
/*  915 */                         if (userAdminLink != "") {
/*  916 */                           out.write(32);
/*  917 */                           out.print(userAdminLink);
/*  918 */                           out.write(" &gt;");
/*      */                         }
/*  920 */                         out.write(" \n\t <span class=\"bcactive\"> \n\t\t ");
/*      */                         
/*  922 */                         ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  923 */                         _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  924 */                         _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f8);
/*  925 */                         int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  926 */                         if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                           for (;;) {
/*  928 */                             out.write("\n\t\t\t ");
/*      */                             
/*  930 */                             WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  931 */                             _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  932 */                             _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                             
/*  934 */                             _jspx_th_c_005fwhen_005f4.setTest("${param.usergroup == \"true\"}");
/*  935 */                             int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  936 */                             if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                               for (;;) {
/*  938 */                                 out.write("\n\t\t\t\t ");
/*      */                                 
/*  940 */                                 ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  941 */                                 _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  942 */                                 _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fwhen_005f4);
/*  943 */                                 int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  944 */                                 if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                   for (;;) {
/*  946 */                                     out.write("\n\t\t\t\t\t ");
/*      */                                     
/*  948 */                                     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  949 */                                     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  950 */                                     _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                     
/*  952 */                                     _jspx_th_c_005fwhen_005f5.setTest("${param.method=='editUserGroup'}");
/*  953 */                                     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  954 */                                     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                       for (;;) {
/*  956 */                                         out.print(FormatUtil.getString("am.webclient.useradministration.usergroup.edit.text"));
/*  957 */                                         out.write("\n\t\t\t\t\t ");
/*  958 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  959 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/*  963 */                                     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  964 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                     }
/*      */                                     
/*  967 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  968 */                                     out.write("\n\t\t\t\t\t ");
/*      */                                     
/*  970 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  971 */                                     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  972 */                                     _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f4);
/*  973 */                                     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  974 */                                     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                       for (;;) {
/*  976 */                                         out.print(FormatUtil.getString("am.webclient.useradministration.newusergroup.text"));
/*  977 */                                         out.write("\n\t\t\t\t\t ");
/*  978 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  979 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/*  983 */                                     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  984 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                     }
/*      */                                     
/*  987 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  988 */                                     out.write("\n\t\t\t\t ");
/*  989 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  990 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  994 */                                 if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  995 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                                 }
/*      */                                 
/*  998 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  999 */                                 out.write("\n\t\t\t ");
/* 1000 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1001 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1005 */                             if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1006 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                             }
/*      */                             
/* 1009 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1010 */                             out.write("\n\t\t\t ");
/* 1011 */                             if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/*      */                               return;
/* 1013 */                             out.write(" \n\t\t\t ");
/* 1014 */                             if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/*      */                               return;
/* 1016 */                             out.write("\n\t\t ");
/* 1017 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1018 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1022 */                         if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1023 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                         }
/*      */                         
/* 1026 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1027 */                         out.write(" \n\t </span>\n\t </td>\n\t ");
/*      */                       }
/* 1029 */                       out.write(10);
/* 1030 */                       out.write(9);
/* 1031 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 1032 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1036 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 1037 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*      */                   }
/*      */                   else {
/* 1040 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1041 */                     out.write("\n\t</tr>\n</table>\n\t");
/*      */                     
/* 1043 */                     FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.get(FormTag.class);
/* 1044 */                     _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 1045 */                     _jspx_th_html_005fform_005f0.setParent(null);
/*      */                     
/* 1047 */                     _jspx_th_html_005fform_005f0.setAction(formAction);
/*      */                     
/* 1049 */                     _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/*      */                     
/* 1051 */                     _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/* 1052 */                     int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 1053 */                     if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                       for (;;) {
/* 1055 */                         out.write("\n\t<input type=\"hidden\" name=\"authType\" value=\"local\">\n\t<input type=\"hidden\" name=\"userGroupUsers\" >\n\t<input type=\"hidden\" name=\"ldapdomainValue\" >\n\t<input type=\"hidden\" name=\"ldapdomainName\" >\n\t<input type=\"hidden\" name=\"associatedUserGroups\" >\n\t");
/* 1056 */                         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1058 */                         out.write(10);
/* 1059 */                         out.write(9);
/* 1060 */                         if (_jspx_meth_c_005fif_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1062 */                         out.write("\n      ");
/* 1063 */                         if (_jspx_meth_c_005fif_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1065 */                         out.write("\n    ");
/* 1066 */                         if (_jspx_meth_c_005fchoose_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1068 */                         out.write("\n\n<div id=\"customfieldsfullList\"  style=' display:block; width: 100%; padding: 10px 0px 0px 0px;'>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td width=\"53%\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"padding-right:0px;\">\n\n  \t<tr>\n\n\t<td class=\"tableheading-monitor-config\">\n\t\t");
/* 1069 */                         if (_jspx_meth_c_005fchoose_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1071 */                         out.write("\n\t</td>\n\t</tr>\n<tr>\n<td  width=\"100%\">\n<br/>\n<!-- Local Auth Config Div Starts-->\n<div id=\"eachTableDiv_LocalAuth\" style=\"display:none;\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n<td width=\"70%\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\n");
/*      */                         
/* 1073 */                         String isEnable = "";
/* 1074 */                         boolean isDelegAdmin = false;
/* 1075 */                         if (DBUtil.isDelegatedAdmin(request.getRemoteUser())) {
/* 1076 */                           isEnable = "disabled";
/* 1077 */                           isDelegAdmin = true;
/*      */                         }
/*      */                         
/* 1080 */                         out.write(10);
/* 1081 */                         out.write(10);
/*      */                         
/* 1083 */                         IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1084 */                         _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1085 */                         _jspx_th_c_005fif_005f13.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1087 */                         _jspx_th_c_005fif_005f13.setTest("${(param.method=='editUser') && (UserConfiguration.authType =='ADAuthentication')}");
/* 1088 */                         int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1089 */                         if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                           for (;;) {
/* 1091 */                             out.write("\n\n<tr>\n\t<td  height=\"20\" class=\"bodytext label-align\">&nbsp; ");
/* 1092 */                             out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.domain.text"));
/* 1093 */                             out.write("</td>\n\t");
/* 1094 */                             if (_jspx_meth_c_005fif_005f14(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                               return;
/* 1096 */                             out.write(10);
/* 1097 */                             out.write(9);
/*      */                             
/* 1099 */                             IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1100 */                             _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 1101 */                             _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f13);
/*      */                             
/* 1103 */                             _jspx_th_c_005fif_005f15.setTest("${!isOpenLdapUser }");
/* 1104 */                             int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 1105 */                             if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                               for (;;) {
/* 1107 */                                 out.write("\n\t<td height=\"20\" >\n\t<select ");
/* 1108 */                                 out.print(isEnable);
/* 1109 */                                 out.write(" id=\"userDomainListBox\" data-placeholder=\"");
/* 1110 */                                 if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                   return;
/* 1112 */                                 out.write("\" class=\"chosenselect\" multiple style=\"width:250px;\" tabindex=\"2\">\n\t          ");
/*      */                                 
/* 1114 */                                 ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1115 */                                 _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1116 */                                 _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f15);
/*      */                                 
/* 1118 */                                 _jspx_th_c_005fforEach_005f0.setItems("${domainList}");
/*      */                                 
/* 1120 */                                 _jspx_th_c_005fforEach_005f0.setVar("row");
/*      */                                 
/* 1122 */                                 _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 1123 */                                 int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                 try {
/* 1125 */                                   int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1126 */                                   if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                     for (;;) {
/* 1128 */                                       out.write("\n\t          \t\t");
/* 1129 */                                       if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                       {
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
/* 1162 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 1163 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/* 1131 */                                       out.write("\n\t           \t \t\t\t\t");
/*      */                                       
/* 1133 */                                       String optionSelect = "";
/* 1134 */                                       if ((selectedDomains != null) && (selectedDomains.contains((String)pageContext.getAttribute("selecteddomainid")))) {
/* 1135 */                                         optionSelect = "selected=\"selected\"";
/*      */                                       }
/*      */                                       
/* 1138 */                                       out.write("\n\t           \t \t\t\t\t<option value=\"");
/* 1139 */                                       if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                       {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1162 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 1163 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/* 1141 */                                       out.write(34);
/* 1142 */                                       out.write(32);
/* 1143 */                                       out.print(optionSelect);
/* 1144 */                                       out.write(62);
/* 1145 */                                       out.write(32);
/* 1146 */                                       if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                       {
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
/* 1162 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 1163 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/* 1148 */                                       out.write("</option>\n\t          ");
/* 1149 */                                       int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1150 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1154 */                                   if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1162 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 1163 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/*      */                                 }
/*      */                                 catch (Throwable _jspx_exception)
/*      */                                 {
/*      */                                   for (;;)
/*      */                                   {
/* 1158 */                                     int tmp5297_5296 = 0; int[] tmp5297_5294 = _jspx_push_body_count_c_005fforEach_005f0; int tmp5299_5298 = tmp5297_5294[tmp5297_5296];tmp5297_5294[tmp5297_5296] = (tmp5299_5298 - 1); if (tmp5299_5298 <= 0) break;
/* 1159 */                                     out = _jspx_page_context.popBody(); }
/* 1160 */                                   _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                 } finally {
/* 1162 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 1163 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                 }
/* 1165 */                                 out.write("\n\t </select></td>\n\t ");
/* 1166 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 1167 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1171 */                             if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 1172 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                             }
/*      */                             
/* 1175 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 1176 */                             out.write("\n</tr>\n\n   ");
/* 1177 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1178 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1182 */                         if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1183 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                         }
/*      */                         
/* 1186 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1187 */                         out.write("\n\n<tr>\n<td width=\"25%\" height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 1188 */                         out.print(FormatUtil.getString("am.webclient.common.username.text"));
/* 1189 */                         out.write("<span class=\"mandatory\">*</span></td>\n<td width=\"75%\" height=\"20\">\n");
/* 1190 */                         if (isDelegAdmin) {
/* 1191 */                           out.write(10);
/* 1192 */                           out.write(9);
/* 1193 */                           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 1195 */                           out.write(10);
/*      */                         } else {
/* 1197 */                           out.write(10);
/* 1198 */                           if (_jspx_meth_c_005fif_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 1200 */                           out.write(10);
/* 1201 */                           if (_jspx_meth_c_005fif_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 1203 */                           out.write(10);
/*      */                         }
/* 1205 */                         out.write("\n</td>\n</tr>\n<tr>\n\t<td  height=\"20\" class=\"bodytext label-align\">&nbsp; ");
/* 1206 */                         out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketbody"));
/* 1207 */                         out.write("</td>\n\t<td height=\"20\" >");
/* 1208 */                         if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1210 */                         out.write("</td>\n</tr>\n");
/*      */                         
/* 1212 */                         IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1213 */                         _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 1214 */                         _jspx_th_c_005fif_005f18.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1216 */                         _jspx_th_c_005fif_005f18.setTest("${(param.method=='editUser')&& (UserConfiguration.authType !='ADAuthentication')}");
/* 1217 */                         int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 1218 */                         if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                           for (;;) {
/* 1220 */                             out.write("\n<tr>\n<td>&nbsp;</td>\n<td height=\"20\" colspan=\"2\" class=\"bodytext\"> ");
/* 1221 */                             if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_c_005fif_005f18, _jspx_page_context))
/*      */                               return;
/* 1223 */                             out.write("&nbsp; ");
/* 1224 */                             out.print(FormatUtil.getString("Update Password"));
/* 1225 */                             out.write("</td>\n</tr>\n<tr>\n<td colspan=\"3\">\n<!--ME-SOLUTIONS USER MGMT CODE CHANGES STARTS HERE-->\n\t<div id=\"oldPWD\" style=\"Display:none\">\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t<tr>\n\t\t\t<td width=\"25%\" height=\"20\" class=\"bodytext\" align=\"right\">");
/* 1226 */                             out.print(FormatUtil.getString("am.webclient.user.oldpassword.text"));
/* 1227 */                             out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"20\" colspan=\"2\" Style=\"padding-left:2px;padding-top:8px\"> &nbsp; <input type=\"password\" name=\"oldpassword\" class=\"formtext default\" size=\"30\" > </td>\n\t\t\t</tr>\n\t\t</table>\n\t</div>\n<!--ME-SOLUTIONS USER MGMT  CODE CHANGES ENDS HERE-->\n\t<div id=\"updatePWD\" style=\"Display:none\">\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t<tr >\n\t\t\t<td width=\"25%\" height=\"20\" class=\"bodytext label-align\">&nbsp; ");
/* 1228 */                             out.print(FormatUtil.getString("am.webclient.common.password.text"));
/* 1229 */                             out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"20\" colspan=\"2\" Style=\"padding-top:8px\"> &nbsp;  ");
/* 1230 */                             if (_jspx_meth_html_005fpassword_005f0(_jspx_th_c_005fif_005f18, _jspx_page_context))
/*      */                               return;
/* 1232 */                             out.write(" </td>\n\t\t\t</tr>\n\t\t\t<tr >\n\t\t\t<td  height=\"20\" class=\"bodytext label-align\">&nbsp; ");
/* 1233 */                             out.print(FormatUtil.getString("am.webclient.user.confirmpassword.text"));
/* 1234 */                             out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"20\" colspan=\"2\" Style=\"padding-top:8px\"> &nbsp; <input type=\"password\" name=\"confirmpassword\" class=\"formtext default\" size=\"30\" > </td>\n\t\t\t</tr>\n\t\t</table>\n\t</div>\n</td>\n</tr>\n");
/* 1235 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 1236 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1240 */                         if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 1241 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                         }
/*      */                         
/* 1244 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1245 */                         out.write(10);
/* 1246 */                         out.write(10);
/*      */                         
/* 1248 */                         IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1249 */                         _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 1250 */                         _jspx_th_c_005fif_005f19.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1252 */                         _jspx_th_c_005fif_005f19.setTest("${param.method!='editUser'}");
/* 1253 */                         int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 1254 */                         if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                           for (;;) {
/* 1256 */                             out.write("\n<tr>\n\t<td  width=\"25%\" height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 1257 */                             out.print(FormatUtil.getString("am.webclient.common.password.text"));
/* 1258 */                             out.write("<span class=\"mandatory\">*</span></td>\n\t<td height=\"20\" colspan=\"2\"> ");
/* 1259 */                             if (_jspx_meth_html_005fpassword_005f1(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                               return;
/* 1261 */                             out.write("</td>\n</tr>\n<tr>\n\t<td  height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 1262 */                             out.print(FormatUtil.getString("am.webclient.user.confirmpassword.text"));
/* 1263 */                             out.write("<span class=\"mandatory\">*</span></td>\n\t<td height=\"20\" colspan=\"2\"> <input type=\"password\" name=\"confirmpassword\" class=\"formtext default\" size=\"30\" > </td>\n</tr>\n");
/* 1264 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 1265 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1269 */                         if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 1270 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                         }
/*      */                         
/* 1273 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 1274 */                         out.write("\n<tr>\n\t<td width=\"25%\" height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 1275 */                         out.print(FormatUtil.getString("am.webclient.useremail.text"));
/* 1276 */                         if (_jspx_meth_c_005fif_005f20(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1278 */                         out.write("</td>\n\t<td height=\"20\" > ");
/* 1279 */                         if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1281 */                         out.write("</td>\n</tr>\n<tr>\n\t<td  height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 1282 */                         out.print(FormatUtil.getString("am.webclient.userrole.text"));
/* 1283 */                         out.write("<span class=\"mandatory\">*</span></td>\n\t<td height=\"20\" >\n\t");
/* 1284 */                         out.write(10);
/* 1285 */                         out.write(9);
/* 1286 */                         if (("admin".equals(username)) && (("ADMIN".equals(remoteUserRole)) || ("ENTERPRISEADMIN".equals(remoteUserRole))))
/*      */                         {
/* 1288 */                           out.write(10);
/* 1289 */                           out.write(9);
/*      */                           
/* 1291 */                           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.get(SelectTag.class);
/* 1292 */                           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1293 */                           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 1295 */                           _jspx_th_html_005fselect_005f0.setProperty("groups");
/*      */                           
/* 1297 */                           _jspx_th_html_005fselect_005f0.setOnchange("javascript:showMonitorGroups();");
/* 1298 */                           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1299 */                           if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1300 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1301 */                               out = _jspx_page_context.pushBody();
/* 1302 */                               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 1303 */                               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1306 */                               out.write(10);
/* 1307 */                               out.write(9);
/* 1308 */                               out.write(9);
/*      */                               
/* 1310 */                               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1311 */                               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 1312 */                               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/* 1314 */                               _jspx_th_html_005foption_005f0.setValue("ADMIN");
/* 1315 */                               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 1316 */                               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 1317 */                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1318 */                                   out = _jspx_page_context.pushBody();
/* 1319 */                                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 1320 */                                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1323 */                                   out.write(32);
/* 1324 */                                   out.print(FormatUtil.getString("am.webclient.user.Administrator.text"));
/* 1325 */                                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 1326 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1329 */                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1330 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1333 */                               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 1334 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                               }
/*      */                               
/* 1337 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 1338 */                               out.write(10);
/* 1339 */                               out.write(9);
/* 1340 */                               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 1341 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1344 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1345 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1348 */                           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 1349 */                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                           }
/*      */                           
/* 1352 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 1353 */                           out.write(10);
/* 1354 */                           out.write(9);
/*      */                         }
/*      */                         else
/*      */                         {
/* 1358 */                           out.write(10);
/* 1359 */                           out.write(9);
/* 1360 */                           if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 1362 */                           out.write(10);
/* 1363 */                           out.write(10);
/* 1364 */                           out.write(9);
/*      */                         }
/* 1366 */                         out.write("\n\t</td>\n</tr>\n<tr>\n\t<td colspan=\"2\" >\n\t\t<div id=\"restrictedadminuser\" style=\"display: none;\">\n\t\t\t<table border=\"0\" width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"25%\" height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 1367 */                         out.print(FormatUtil.getString("am.webclient.delegatedadmin.text"));
/* 1368 */                         out.write("</td>\n\t\t\t\t\t<td height=\"20\" >\n\t\t\t\t\t");
/* 1369 */                         if (_jspx_meth_c_005fchoose_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1371 */                         out.write("\n\t\t\t\t\t </td>\n\t\t\t\t</tr>\n\t\t\t\t\n\t\t\t</table>\n\t\t</div>\n\t</td>\n</tr>\n\n");
/*      */                         
/* 1373 */                         IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1374 */                         _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 1375 */                         _jspx_th_c_005fif_005f21.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1377 */                         _jspx_th_c_005fif_005f21.setTest("${param.method!='editUser'}");
/* 1378 */                         int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 1379 */                         if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                           for (;;) {
/* 1381 */                             out.write("\n<tr>\n\t<td  height=\"35\" class=\"bodytext label-align\" >&nbsp; ");
/* 1382 */                             out.print(FormatUtil.getString("am.webclient.fileupload.UploadImageFile.text"));
/* 1383 */                             out.write("</td>\n\t<td  height=\"35\" class=\"bodytext\">");
/* 1384 */                             if (_jspx_meth_html_005ffile_005f0(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                               return;
/* 1386 */                             out.write("</td>\n</tr>\n<tr>\n\t<td> </td>\n\t<td align=\"left\">\n\t\t<table width=\"100%\" class=\"font_italic_11px\">\n\t\t\t<tr>\n\t\t\t<td width=\"3%\" valign=\"top\"><b><span class=\"bodytext\" style=\"color:#FA5858\">");
/* 1387 */                             out.print(FormatUtil.getString("am.webclient.fileupload.note.text"));
/* 1388 */                             out.write(":-</span></b></td>\n\t\t\t<td class=\"bodytext\">&nbsp; ");
/* 1389 */                             out.print(FormatUtil.getString("am.webclient.fileupload.note1.text"));
/* 1390 */                             out.write("</br>&nbsp; ");
/* 1391 */                             out.print(FormatUtil.getString("am.webclient.fileupload.note2.text"));
/* 1392 */                             out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n</tr>\n");
/* 1393 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 1394 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1398 */                         if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 1399 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                         }
/*      */                         
/* 1402 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 1403 */                         out.write(10);
/* 1404 */                         out.write(10);
/*      */                         
/* 1406 */                         IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1407 */                         _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 1408 */                         _jspx_th_c_005fif_005f22.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1410 */                         _jspx_th_c_005fif_005f22.setTest("${param.method=='editUser'}");
/* 1411 */                         int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 1412 */                         if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                           for (;;) {
/* 1414 */                             out.write(10);
/*      */                             
/* 1416 */                             String path = DBUtil.getImageStatus(username, userid);
/*      */                             
/* 1418 */                             out.write("\n<tr>\n\t<td width=\"25%\"  height=\"35\" class=\"bodytext label-align\" >&nbsp; ");
/* 1419 */                             out.print(FormatUtil.getString("am.webclient.fileupload.changeImageFile.text"));
/* 1420 */                             out.write(" </td>\n\t<td>\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td style=\"width:10%;\" id=\"myCol\">\n\t\t\t\t<div id=\"showphoto\" style=\"Display:block\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td  height=\"35\" class=\"bodytext\" ><img height=50 width=50 src=");
/* 1421 */                             out.print(path);
/* 1422 */                             out.write(" /></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr> <td colspan=\"2\" height=\"15\"></td></tr>\n\t\t\t\t</table>\n\t\t\t\t</div>\n\t\t\t</td>\n\t\t\t<td>\n\t\t\t<div id=\"changeordelete\"  style=\"padding-left:7px;\">\n\t\t\t\t");
/* 1423 */                             if (_jspx_meth_html_005fhidden_005f3(_jspx_th_c_005fif_005f22, _jspx_page_context))
/*      */                               return;
/* 1425 */                             out.write("\n\t\t\t\t<A HREF=\"javascript:fnChangePhoto();\" class=\"staticlinks\" >");
/* 1426 */                             out.print(FormatUtil.getString("am.webclient.fileupload.changePhoto.text"));
/* 1427 */                             out.write("</a>&nbsp;&nbsp;<span class=\"bodytext\" >|</span>&nbsp;");
/* 1428 */                             if (_jspx_meth_html_005fhidden_005f4(_jspx_th_c_005fif_005f22, _jspx_page_context))
/*      */                               return;
/* 1430 */                             out.write("<A HREF=\"javascript:fnDeletePhoto();\" class=\"staticlinks\" ><span id=\"del\">");
/* 1431 */                             out.print(FormatUtil.getString("am.webclient.fileupload.deletePhoto.text"));
/* 1432 */                             out.write("</span></a>\n\t\t\t</div>\n\t\t\t<div id=\"changePhoto\" style=\"Display:none\">\n\t\t\t\t<table width=\"70%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" class=\"dottedline\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"2\" width=\"90%\" valign=\"middle\" style=\"padding:3px;\" ><span><input type=\"file\" name=\"theFile\" size=\"40\" value=\"\"></span></td><td valign=\"top\" align=\"right\" style=\"padding:3px;\"><img src=\"/images/delete.png\"   title=\"Close\" onclick=\"hidefnChangePhoto();\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"3%\" valign=\"top\" align=\"right\"><b><span class=\"bodytext\" style=\"color:#FA5858\">");
/* 1433 */                             out.print(FormatUtil.getString("am.webclient.fileupload.note.text"));
/* 1434 */                             out.write(":-</span></b></td>\n\t\t\t\t\t\t<td class=\"bodytext\">&nbsp; ");
/* 1435 */                             out.print(FormatUtil.getString("am.webclient.fileupload.note1.text"));
/* 1436 */                             out.write("</br>&nbsp; ");
/* 1437 */                             out.print(FormatUtil.getString("am.webclient.fileupload.note2.text"));
/* 1438 */                             out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</div>\n\t\t\t</td>\n\t\t</tr>\n\t\t</table>\n\n\t</td>\n</tr>\n");
/* 1439 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 1440 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1444 */                         if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 1445 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                         }
/*      */                         
/* 1448 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 1449 */                         out.write("\n\n<tr>\n\n<td valign=\"top\"  colspan=\"2\">\n<div id=\"mgselection\">\n\t<table border=\"0\" cellpadding=\"8\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n\t<tr>\n\t<td width=\"25%\" valign=\"top\" class=\"bodytext label-align\">&nbsp; ");
/* 1450 */                         out.print(FormatUtil.getString("am.webclient.useradministration.usergroup.select.text"));
/* 1451 */                         out.write("</td>\n\t<td>\n\t<select id=\"usergroupSelect\" data-placeholder=\"");
/* 1452 */                         if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1454 */                         out.write("\" class=\"chosenselect\" multiple style=\"width:250px;\" tabindex=\"2\">\n\t\t           \t <optgroup label=\"");
/* 1455 */                         if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1457 */                         out.write("\">\n\t\t           \t \t");
/*      */                         
/* 1459 */                         ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1460 */                         _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1461 */                         _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1463 */                         _jspx_th_c_005fforEach_005f1.setItems("${usergroupList['local']}");
/*      */                         
/* 1465 */                         _jspx_th_c_005fforEach_005f1.setVar("row");
/*      */                         
/* 1467 */                         _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 1468 */                         int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                         try {
/* 1470 */                           int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1471 */                           if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                             for (;;) {
/* 1473 */                               out.write("\n\t\t           \t \t");
/* 1474 */                               if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                               {
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
/* 1508 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 1509 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                               }
/* 1476 */                               out.write("\n\t           \t \t\t\t\t");
/*      */                               
/* 1478 */                               String optionSelect = "";
/* 1479 */                               if ((selectedUserGroup != null) && (selectedUserGroup.contains((String)pageContext.getAttribute("selectedgroupid")))) {
/* 1480 */                                 optionSelect = "selected=\"selected\"";
/*      */                               }
/*      */                               
/* 1483 */                               out.write("\n\t\t           \t \t\t<option value=\"");
/* 1484 */                               if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                               {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1508 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 1509 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                               }
/* 1486 */                               out.write(34);
/* 1487 */                               out.write(32);
/* 1488 */                               out.print(optionSelect);
/* 1489 */                               out.write(32);
/* 1490 */                               out.write(62);
/* 1491 */                               out.write(32);
/* 1492 */                               if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                               {
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
/* 1508 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 1509 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                               }
/* 1494 */                               out.write("</option>\n\t\t           \t \t");
/* 1495 */                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1496 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1500 */                           if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1508 */                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 1509 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                           }
/*      */                         }
/*      */                         catch (Throwable _jspx_exception)
/*      */                         {
/*      */                           for (;;)
/*      */                           {
/* 1504 */                             int tmp7730_7729 = 0; int[] tmp7730_7727 = _jspx_push_body_count_c_005fforEach_005f1; int tmp7732_7731 = tmp7730_7727[tmp7730_7729];tmp7730_7727[tmp7730_7729] = (tmp7732_7731 - 1); if (tmp7732_7731 <= 0) break;
/* 1505 */                             out = _jspx_page_context.popBody(); }
/* 1506 */                           _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                         } finally {
/* 1508 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 1509 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                         }
/* 1511 */                         out.write("\n\t\t           \t </optgroup>\n\t\t           \t <optgroup label=\"");
/* 1512 */                         if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1514 */                         out.write("\">\n\t\t           \t \t");
/*      */                         
/* 1516 */                         ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1517 */                         _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 1518 */                         _jspx_th_c_005fforEach_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1520 */                         _jspx_th_c_005fforEach_005f2.setItems("${usergroupList['domain']}");
/*      */                         
/* 1522 */                         _jspx_th_c_005fforEach_005f2.setVar("row");
/*      */                         
/* 1524 */                         _jspx_th_c_005fforEach_005f2.setVarStatus("status");
/* 1525 */                         int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */                         try {
/* 1527 */                           int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 1528 */                           if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */                             for (;;) {
/* 1530 */                               out.write("\n\t\t           \t \t\t \t");
/* 1531 */                               if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                               {
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
/* 1565 */                                 _jspx_th_c_005fforEach_005f2.doFinally();
/* 1566 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                               }
/* 1533 */                               out.write("\n\t           \t \t\t\t\t");
/*      */                               
/* 1535 */                               String optionSelect = "";
/* 1536 */                               if ((selectedUserGroup != null) && (selectedUserGroup.contains((String)pageContext.getAttribute("selectedgroupid")))) {
/* 1537 */                                 optionSelect = "selected=\"selected\"";
/*      */                               }
/*      */                               
/* 1540 */                               out.write("\n\t\t           \t \t\t<option value=\"");
/* 1541 */                               if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                               {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1565 */                                 _jspx_th_c_005fforEach_005f2.doFinally();
/* 1566 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                               }
/* 1543 */                               out.write(34);
/* 1544 */                               out.write(32);
/* 1545 */                               out.print(optionSelect);
/* 1546 */                               out.write(32);
/* 1547 */                               out.write(62);
/* 1548 */                               out.write(32);
/* 1549 */                               if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                               {
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
/* 1565 */                                 _jspx_th_c_005fforEach_005f2.doFinally();
/* 1566 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                               }
/* 1551 */                               out.write("</option>\n\t\t           \t \t");
/* 1552 */                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 1553 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1557 */                           if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1565 */                             _jspx_th_c_005fforEach_005f2.doFinally();
/* 1566 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                           }
/*      */                         }
/*      */                         catch (Throwable _jspx_exception)
/*      */                         {
/*      */                           for (;;)
/*      */                           {
/* 1561 */                             int tmp8188_8187 = 0; int[] tmp8188_8185 = _jspx_push_body_count_c_005fforEach_005f2; int tmp8190_8189 = tmp8188_8185[tmp8188_8187];tmp8188_8185[tmp8188_8187] = (tmp8190_8189 - 1); if (tmp8190_8189 <= 0) break;
/* 1562 */                             out = _jspx_page_context.popBody(); }
/* 1563 */                           _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */                         } finally {
/* 1565 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 1566 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */                         }
/* 1568 */                         out.write("\n\t\t           \t </optgroup>\n\t\t\t</select>\n\t</td>\n\t</tr>\n\t<tr>\n\t<td valign=\"top\" class=\"bodytext label-align\">&nbsp; ");
/* 1569 */                         out.print(FormatUtil.getString("am.webclient.reports.select.mg.text"));
/* 1570 */                         out.write("</td>\n\t<td>\n\t");
/* 1571 */                         if (_jspx_meth_c_005fif_005f23(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1573 */                         out.write("\n\t</td></tr>\n\t</table>\n</div>\n\t\n</td>\n</tr>\n<tr class=\"tablebottom\">\n\t<td></td>\n\t<td  align=\"left\">\n\t\t");
/*      */                         
/* 1575 */                         IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1576 */                         _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 1577 */                         _jspx_th_c_005fif_005f24.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1579 */                         _jspx_th_c_005fif_005f24.setTest("${param.method=='editUser'}");
/* 1580 */                         int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 1581 */                         if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */                           for (;;) {
/* 1583 */                             out.write("\n\t\t\t<input type=\"button\" name=\"sub\" value=\"");
/* 1584 */                             out.print(FormatUtil.getString("am.webclient.user.updateuser.text"));
/* 1585 */                             out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:fnUserConfSubmit(this.form)\">\n\t\t");
/* 1586 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 1587 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1591 */                         if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 1592 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24); return;
/*      */                         }
/*      */                         
/* 1595 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 1596 */                         out.write(10);
/* 1597 */                         out.write(9);
/* 1598 */                         out.write(9);
/*      */                         
/* 1600 */                         IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1601 */                         _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 1602 */                         _jspx_th_c_005fif_005f25.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1604 */                         _jspx_th_c_005fif_005f25.setTest("${param.method !='editUser'}");
/* 1605 */                         int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 1606 */                         if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                           for (;;) {
/* 1608 */                             out.write("\n\t\t\t<input type=\"button\" name=\"sub\" value=\"");
/* 1609 */                             out.print(FormatUtil.getString("am.webclient.user.createuser.text"));
/* 1610 */                             out.write("\" class=\"buttons btn_highlt\" onClick=\"javascript:fnUserConfSubmit(this.form)\">\n\t\t");
/* 1611 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 1612 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1616 */                         if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 1617 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                         }
/*      */                         
/* 1620 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 1621 */                         out.write(10);
/* 1622 */                         out.write(9);
/* 1623 */                         out.write(9);
/*      */                         
/* 1625 */                         IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1626 */                         _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 1627 */                         _jspx_th_c_005fif_005f26.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1629 */                         _jspx_th_c_005fif_005f26.setTest("${param.CustomField =='NewUser'}");
/* 1630 */                         int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 1631 */                         if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                           for (;;) {
/* 1633 */                             out.write("\n\t\t\t<input type=\"button\" name=\"sub1\" value=\"");
/* 1634 */                             out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 1635 */                             out.write("\" class=\"buttons btn_link\" onClick=\"javascript:window.close();\">\n\t\t");
/* 1636 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 1637 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1641 */                         if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 1642 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                         }
/*      */                         
/* 1645 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 1646 */                         out.write(10);
/* 1647 */                         out.write(9);
/* 1648 */                         out.write(9);
/*      */                         
/* 1650 */                         IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1651 */                         _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 1652 */                         _jspx_th_c_005fif_005f27.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1654 */                         _jspx_th_c_005fif_005f27.setTest("${param.CustomField !='NewUser'}");
/* 1655 */                         int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 1656 */                         if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */                           for (;;) {
/* 1658 */                             out.write("\n\t\t\t");
/*      */                             
/* 1660 */                             ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1661 */                             _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 1662 */                             _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_c_005fif_005f27);
/* 1663 */                             int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 1664 */                             if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                               for (;;) {
/* 1666 */                                 out.write("\n\t\t\t\t");
/*      */                                 
/* 1668 */                                 WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1669 */                                 _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 1670 */                                 _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                                 
/* 1672 */                                 _jspx_th_c_005fwhen_005f11.setTest("${!empty updateProfile && updateProfile=='true'}");
/* 1673 */                                 int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 1674 */                                 if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                                   for (;;) {
/* 1676 */                                     out.write("\n\t\t\t\t\t<input type=\"button\" name=\"sub1\" value=\"");
/* 1677 */                                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 1678 */                                     out.write("\" class=\"buttons btn_link\" onClick=\"javascript:location.href='/MyPage.do?method=viewDashBoard';\">\n\t\t\t\t");
/* 1679 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 1680 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1684 */                                 if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 1685 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                                 }
/*      */                                 
/* 1688 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 1689 */                                 out.write("\n\t\t\t\t");
/*      */                                 
/* 1691 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1692 */                                 _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1693 */                                 _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1694 */                                 int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1695 */                                 if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                                   for (;;) {
/* 1697 */                                     out.write("\n\t\t\t\t\t<input type=\"button\" name=\"sub1\" value=\"");
/* 1698 */                                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 1699 */                                     out.write("\" class=\"buttons btn_link\" onClick=\"javascript:location.href='/admin/userconfiguration.do?method=showUsers';\">\n\t\t\t\t");
/* 1700 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1701 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1705 */                                 if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1706 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                                 }
/*      */                                 
/* 1709 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1710 */                                 out.write("\n\t\t\t");
/* 1711 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1712 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1716 */                             if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1717 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                             }
/*      */                             
/* 1720 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1721 */                             out.write("\t\t\n\t\t");
/* 1722 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 1723 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1727 */                         if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 1728 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27); return;
/*      */                         }
/*      */                         
/* 1731 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 1732 */                         out.write("\n\t</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</div>\n<!-- Local Auth Config Div Ends-->\n<!-- AD Auth Config Div Starts-->\n\t");
/* 1733 */                         if (_jspx_meth_c_005fif_005f28(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1735 */                         out.write("\n<!-- AD Auth Config Div Endss-->\n</td>\n</tr>\n</table>\n</td>\n<td valign=\"top\" width=\"30%\" style=\"padding-bottom:10px;\">\n");
/* 1736 */                         if (!OEMUtil.isRemove("am.userrole.remove")) {
/* 1737 */                           out.write("\n<div id=\"helpdiv_LocalAuth\" style=\"display:none;\">\n");
/* 1738 */                           JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.user.helpcard.text", new String[] { OEMUtil.getOEMString("product.name") })), request.getCharacterEncoding()), out, false);
/* 1739 */                           out.write("\n </div>\n <div id=\"helpdiv_ADAuth\" style=\"display:none;\">\n");
/* 1740 */                           JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admintab.adduser.importad.helpcard.text", new String[] { OEMUtil.getOEMString("product.name") })), request.getCharacterEncoding()), out, false);
/* 1741 */                           out.write("\n</div>\n");
/*      */                         }
/* 1743 */                         out.write("\n</td>\n</tr>\n</table>\n</div>\n\n\n\n\n<div id=\"usergroupconfiguration\"  style=' display:none; width: 100%; padding: 10px 0px 0px 0px;'>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td width=\"53%\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"padding-right:0px;\">\n\n  \t<tr>\n\n\t<td class=\"tableheading-monitor-config\">\n\n    \t<!--<td width=\"6%\" height=\"31\" align=\"center\" ><img height=\"31\" align=\"right\" style=\"padding-right:3px;\" src=\"/images/icon_admin_useradministrati.gif\"/></td><td >");
/* 1744 */                         out.print(FormatUtil.getString("am.webclient.configureusers.text"));
/* 1745 */                         out.write("</td><td width=\"200px\">&nbsp;</td>\n\t-->\n\n\n\n\t<div id=\"customfieldTabs\">\n\t <ul class=\"ulstyleforcf\">\n\t\t<li class=\"listyleforcf\" id=\"localgroup_tabs\">\n\t\t\t<a title=\"");
/* 1746 */                         if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1748 */                         out.write("\" href=\"javascript:void(0)\" class=\"ulanchor\" onclick=\"toggleUserGroupDiv('localgroup')\">\n\t\t\t\t<img src=\"/images/icon_customfields_add.png\" style=\"position: relative;top: 12px;\" border=\"0\" align=\"left\"></img>\n\t\t\t\t&nbsp;");
/* 1749 */                         if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1751 */                         out.write(9);
/* 1752 */                         out.write("\n\t\t\t\t<span class=\"tabarrows\"></span>\n\t\t\t</a>\n\t\t</li>\n\t\t<li class=\"listyleforcf\" id=\"adgroup_tabs\">\n\t\t\t<a title=\"");
/* 1753 */                         if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1755 */                         out.write("\" href=\"javascript:void(0)\" class=\"ulanchor\" onclick=\"toggleUserGroupDiv('adgroup')\">\n\t\t\t\t<img src=\"/images/icon_customfields_add.png\" style=\"position: relative;top: 12px;\" border=\"0\" align=\"left\"></img>\n\t\t\t\t&nbsp;");
/* 1756 */                         if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1758 */                         out.write(9);
/* 1759 */                         out.write("\n\t\t\t\t<span class=\"tabarrows\"></span>\n\t\t\t</a>\n\t\t</li>\n\t  </ul>\n\t</div>\n\n\n\t</td>\n\t</tr>\n<tr>\n<td  width=\"100%\">\n<br/>\n<!-- Local Auth Config Div Starts-->\n<div id=\"addusergroup_localgroup\" style=\"display:block;\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n<td width=\"70%\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n\n\n");
/*      */                         
/* 1761 */                         IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1762 */                         _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 1763 */                         _jspx_th_c_005fif_005f29.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1765 */                         _jspx_th_c_005fif_005f29.setTest("${param.method=='editUserGroup' && isDomainGroup}");
/* 1766 */                         int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 1767 */                         if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */                           for (;;) {
/* 1769 */                             out.write("\n\n<tr>\n\t<td height=\"20\" width=\"25%\" class=\"bodytext label-align\">&nbsp; ");
/* 1770 */                             out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.domain.text"));
/* 1771 */                             out.write("</td>\n\t<td height=\"20\" >\n\t");
/*      */                             
/* 1773 */                             IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1774 */                             _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 1775 */                             _jspx_th_c_005fif_005f30.setParent(_jspx_th_c_005fif_005f29);
/*      */                             
/* 1777 */                             _jspx_th_c_005fif_005f30.setTest("${!isOpenLdapGroup }");
/* 1778 */                             int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 1779 */                             if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */                               for (;;) {
/* 1781 */                                 out.write("\n\t\t<select id=\"domainListBox\" data-placeholder=\"");
/* 1782 */                                 if (_jspx_meth_fmt_005fmessage_005f18(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                                   return;
/* 1784 */                                 out.write("\" class=\"chosenselect\" multiple style=\"width:250px;\" tabindex=\"2\">\n\t          ");
/*      */                                 
/* 1786 */                                 ForEachTag _jspx_th_c_005fforEach_005f3 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1787 */                                 _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
/* 1788 */                                 _jspx_th_c_005fforEach_005f3.setParent(_jspx_th_c_005fif_005f30);
/*      */                                 
/* 1790 */                                 _jspx_th_c_005fforEach_005f3.setItems("${domainList}");
/*      */                                 
/* 1792 */                                 _jspx_th_c_005fforEach_005f3.setVar("row");
/*      */                                 
/* 1794 */                                 _jspx_th_c_005fforEach_005f3.setVarStatus("status");
/* 1795 */                                 int[] _jspx_push_body_count_c_005fforEach_005f3 = { 0 };
/*      */                                 try {
/* 1797 */                                   int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
/* 1798 */                                   if (_jspx_eval_c_005fforEach_005f3 != 0) {
/*      */                                     for (;;) {
/* 1800 */                                       out.write("\n\t          \t\t");
/* 1801 */                                       if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/*      */                                       {
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
/* 1834 */                                         _jspx_th_c_005fforEach_005f3.doFinally();
/* 1835 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3); return;
/*      */                                       }
/* 1803 */                                       out.write("\n\t           \t \t\t\t\t");
/*      */                                       
/* 1805 */                                       String optionSelect = "";
/* 1806 */                                       if ((selectedDomains != null) && (selectedDomains.contains((String)pageContext.getAttribute("selecteddomainid")))) {
/* 1807 */                                         optionSelect = "selected=\"selected\"";
/*      */                                       }
/*      */                                       
/* 1810 */                                       out.write("\n\t           \t \t\t\t\t<option value=\"");
/* 1811 */                                       if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/*      */                                       {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1834 */                                         _jspx_th_c_005fforEach_005f3.doFinally();
/* 1835 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3); return;
/*      */                                       }
/* 1813 */                                       out.write(34);
/* 1814 */                                       out.write(32);
/* 1815 */                                       out.print(optionSelect);
/* 1816 */                                       out.write(62);
/* 1817 */                                       out.write(32);
/* 1818 */                                       if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/*      */                                       {
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
/* 1834 */                                         _jspx_th_c_005fforEach_005f3.doFinally();
/* 1835 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3); return;
/*      */                                       }
/* 1820 */                                       out.write("</option>\n\t          ");
/* 1821 */                                       int evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
/* 1822 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1826 */                                   if (_jspx_th_c_005fforEach_005f3.doEndTag() == 5)
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1834 */                                     _jspx_th_c_005fforEach_005f3.doFinally();
/* 1835 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3); return;
/*      */                                   }
/*      */                                 }
/*      */                                 catch (Throwable _jspx_exception)
/*      */                                 {
/*      */                                   for (;;)
/*      */                                   {
/* 1830 */                                     int tmp10191_10190 = 0; int[] tmp10191_10188 = _jspx_push_body_count_c_005fforEach_005f3; int tmp10193_10192 = tmp10191_10188[tmp10191_10190];tmp10191_10188[tmp10191_10190] = (tmp10193_10192 - 1); if (tmp10193_10192 <= 0) break;
/* 1831 */                                     out = _jspx_page_context.popBody(); }
/* 1832 */                                   _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
/*      */                                 } finally {
/* 1834 */                                   _jspx_th_c_005fforEach_005f3.doFinally();
/* 1835 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
/*      */                                 }
/* 1837 */                                 out.write("\n\t    </select>\n\t ");
/* 1838 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 1839 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1843 */                             if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 1844 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30); return;
/*      */                             }
/*      */                             
/* 1847 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 1848 */                             out.write(10);
/* 1849 */                             out.write(9);
/* 1850 */                             out.write(32);
/* 1851 */                             if (_jspx_meth_c_005fif_005f31(_jspx_th_c_005fif_005f29, _jspx_page_context))
/*      */                               return;
/* 1853 */                             out.write("\n\t</td>\n</tr>\n\n   ");
/* 1854 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 1855 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1859 */                         if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 1860 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29); return;
/*      */                         }
/*      */                         
/* 1863 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 1864 */                         out.write("\n\n<tr>\n<td height=\"20\" width=\"25%\" class=\"bodytext label-align\"> &nbsp; ");
/* 1865 */                         out.print(FormatUtil.getString("am.webclient.useradministration.usergroup.text"));
/* 1866 */                         out.write("<span class=\"mandatory\">*</span></td>\n\n\n<td height=\"20\">\n\n");
/* 1867 */                         if (_jspx_meth_c_005fchoose_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1869 */                         out.write("\n\n\n\n</td>\n</tr>\n\t<tr><td class=\"bodytext label-align\">&nbsp;  ");
/* 1870 */                         out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.selectuser.text"));
/* 1871 */                         out.write("</td>\t\n\t<td>\t\n\t<select id=\"chosenSelectBox\" data-placeholder=\"");
/* 1872 */                         if (_jspx_meth_fmt_005fmessage_005f19(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1874 */                         out.write("\" class=\"chosenselect\" multiple style=\"width:250px;\" tabindex=\"2\">\n\t          \n\t           <optgroup label=\"");
/* 1875 */                         if (_jspx_meth_fmt_005fmessage_005f20(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1877 */                         out.write("\">\n\t           \t ");
/*      */                         
/* 1879 */                         ForEachTag _jspx_th_c_005fforEach_005f4 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1880 */                         _jspx_th_c_005fforEach_005f4.setPageContext(_jspx_page_context);
/* 1881 */                         _jspx_th_c_005fforEach_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1883 */                         _jspx_th_c_005fforEach_005f4.setItems("${userList}");
/*      */                         
/* 1885 */                         _jspx_th_c_005fforEach_005f4.setVar("row");
/*      */                         
/* 1887 */                         _jspx_th_c_005fforEach_005f4.setVarStatus("status");
/* 1888 */                         int[] _jspx_push_body_count_c_005fforEach_005f4 = { 0 };
/*      */                         try {
/* 1890 */                           int _jspx_eval_c_005fforEach_005f4 = _jspx_th_c_005fforEach_005f4.doStartTag();
/* 1891 */                           if (_jspx_eval_c_005fforEach_005f4 != 0) {
/*      */                             for (;;) {
/* 1893 */                               out.write("\n\t           \t \t\t\t\t");
/* 1894 */                               if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/*      */                               {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1948 */                                 _jspx_th_c_005fforEach_005f4.doFinally();
/* 1949 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4); return;
/*      */                               }
/* 1896 */                               out.write("\n\t           \t \t\t\t\t");
/*      */                               
/* 1898 */                               String optionSelect = "";
/* 1899 */                               if ((selectUserlist != null) && (selectUserlist.contains((String)pageContext.getAttribute("selecteduserid")))) {
/* 1900 */                                 optionSelect = "selected=\"selected\"";
/*      */                               }
/*      */                               
/* 1903 */                               out.write("\n\t           \t \t\t\t\t");
/*      */                               
/* 1905 */                               IfTag _jspx_th_c_005fif_005f32 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1906 */                               _jspx_th_c_005fif_005f32.setPageContext(_jspx_page_context);
/* 1907 */                               _jspx_th_c_005fif_005f32.setParent(_jspx_th_c_005fforEach_005f4);
/*      */                               
/* 1909 */                               _jspx_th_c_005fif_005f32.setTest("${row[\"role\"] == \"OPERATOR\"}");
/* 1910 */                               int _jspx_eval_c_005fif_005f32 = _jspx_th_c_005fif_005f32.doStartTag();
/* 1911 */                               if (_jspx_eval_c_005fif_005f32 != 0) {
/*      */                                 for (;;) {
/* 1913 */                                   out.write("\n\t           \t \t\t\t\t<option value=\"");
/* 1914 */                                   if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fif_005f32, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/*      */                                   {
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
/* 1948 */                                     _jspx_th_c_005fforEach_005f4.doFinally();
/* 1949 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4); return;
/*      */                                   }
/* 1916 */                                   out.write(34);
/* 1917 */                                   out.write(32);
/* 1918 */                                   out.print(optionSelect);
/* 1919 */                                   out.write(62);
/* 1920 */                                   out.write(32);
/* 1921 */                                   if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fif_005f32, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/*      */                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1948 */                                     _jspx_th_c_005fforEach_005f4.doFinally();
/* 1949 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4); return;
/*      */                                   }
/* 1923 */                                   out.write("</option>\n\t           \t \t\t\t\t");
/* 1924 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f32.doAfterBody();
/* 1925 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1929 */                               if (_jspx_th_c_005fif_005f32.doEndTag() == 5) {
/* 1930 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
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
/*      */ 
/* 1948 */                                 _jspx_th_c_005fforEach_005f4.doFinally();
/* 1949 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4); return;
/*      */                               }
/* 1933 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 1934 */                               out.write("\n\t           \t ");
/* 1935 */                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f4.doAfterBody();
/* 1936 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1940 */                           if (_jspx_th_c_005fforEach_005f4.doEndTag() == 5)
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1948 */                             _jspx_th_c_005fforEach_005f4.doFinally();
/* 1949 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4); return;
/*      */                           }
/*      */                         }
/*      */                         catch (Throwable _jspx_exception)
/*      */                         {
/*      */                           for (;;)
/*      */                           {
/* 1944 */                             int tmp11037_11036 = 0; int[] tmp11037_11034 = _jspx_push_body_count_c_005fforEach_005f4; int tmp11039_11038 = tmp11037_11034[tmp11037_11036];tmp11037_11034[tmp11037_11036] = (tmp11039_11038 - 1); if (tmp11039_11038 <= 0) break;
/* 1945 */                             out = _jspx_page_context.popBody(); }
/* 1946 */                           _jspx_th_c_005fforEach_005f4.doCatch(_jspx_exception);
/*      */                         } finally {
/* 1948 */                           _jspx_th_c_005fforEach_005f4.doFinally();
/* 1949 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4);
/*      */                         }
/* 1951 */                         out.write("\n\t           </optgroup>\n\t           \n\t           <optgroup label=\"");
/* 1952 */                         if (_jspx_meth_fmt_005fmessage_005f21(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1954 */                         out.write("\">\n\t           \t ");
/*      */                         
/* 1956 */                         ForEachTag _jspx_th_c_005fforEach_005f5 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1957 */                         _jspx_th_c_005fforEach_005f5.setPageContext(_jspx_page_context);
/* 1958 */                         _jspx_th_c_005fforEach_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1960 */                         _jspx_th_c_005fforEach_005f5.setItems("${userList}");
/*      */                         
/* 1962 */                         _jspx_th_c_005fforEach_005f5.setVar("row");
/*      */                         
/* 1964 */                         _jspx_th_c_005fforEach_005f5.setVarStatus("status");
/* 1965 */                         int[] _jspx_push_body_count_c_005fforEach_005f5 = { 0 };
/*      */                         try {
/* 1967 */                           int _jspx_eval_c_005fforEach_005f5 = _jspx_th_c_005fforEach_005f5.doStartTag();
/* 1968 */                           if (_jspx_eval_c_005fforEach_005f5 != 0) {
/*      */                             for (;;) {
/* 1970 */                               out.write("\n\t           \t \t\t\t\t");
/* 1971 */                               if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fforEach_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
/*      */                               {
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
/*      */ 
/*      */ 
/*      */ 
/* 2024 */                                 _jspx_th_c_005fforEach_005f5.doFinally();
/* 2025 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5); return;
/*      */                               }
/* 1973 */                               out.write("\n\t           \t \t\t\t\t");
/*      */                               
/* 1975 */                               String optionSelect = "";
/* 1976 */                               if ((selectUserlist != null) && (selectUserlist.contains((String)pageContext.getAttribute("selecteduserid")))) {
/* 1977 */                                 optionSelect = "selected=\"selected\"";
/*      */                               }
/*      */                               
/* 1980 */                               out.write("\n\t           \t \t");
/*      */                               
/* 1982 */                               IfTag _jspx_th_c_005fif_005f33 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1983 */                               _jspx_th_c_005fif_005f33.setPageContext(_jspx_page_context);
/* 1984 */                               _jspx_th_c_005fif_005f33.setParent(_jspx_th_c_005fforEach_005f5);
/*      */                               
/* 1986 */                               _jspx_th_c_005fif_005f33.setTest("${row[\"role\"] == \"ADMIN\" || row[\"role\"] == \"ENTERPRISEADMIN\"}");
/* 1987 */                               int _jspx_eval_c_005fif_005f33 = _jspx_th_c_005fif_005f33.doStartTag();
/* 1988 */                               if (_jspx_eval_c_005fif_005f33 != 0) {
/*      */                                 for (;;) {
/* 1990 */                                   out.write("\n\t           \t \t\t<option value=\"");
/* 1991 */                                   if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fif_005f33, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
/*      */                                   {
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
/* 2024 */                                     _jspx_th_c_005fforEach_005f5.doFinally();
/* 2025 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5); return;
/*      */                                   }
/* 1993 */                                   out.write(34);
/* 1994 */                                   out.write(32);
/* 1995 */                                   out.print(optionSelect);
/* 1996 */                                   out.write(62);
/* 1997 */                                   if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fif_005f33, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
/*      */                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2024 */                                     _jspx_th_c_005fforEach_005f5.doFinally();
/* 2025 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5); return;
/*      */                                   }
/* 1999 */                                   out.write("</option>\n\t           \t \t");
/* 2000 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f33.doAfterBody();
/* 2001 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2005 */                               if (_jspx_th_c_005fif_005f33.doEndTag() == 5) {
/* 2006 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
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
/*      */ 
/* 2024 */                                 _jspx_th_c_005fforEach_005f5.doFinally();
/* 2025 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5); return;
/*      */                               }
/* 2009 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
/* 2010 */                               out.write("\n\t           \t ");
/* 2011 */                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f5.doAfterBody();
/* 2012 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2016 */                           if (_jspx_th_c_005fforEach_005f5.doEndTag() == 5)
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2024 */                             _jspx_th_c_005fforEach_005f5.doFinally();
/* 2025 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5); return;
/*      */                           }
/*      */                         }
/*      */                         catch (Throwable _jspx_exception)
/*      */                         {
/*      */                           for (;;)
/*      */                           {
/* 2020 */                             int tmp11615_11614 = 0; int[] tmp11615_11612 = _jspx_push_body_count_c_005fforEach_005f5; int tmp11617_11616 = tmp11615_11612[tmp11615_11614];tmp11615_11612[tmp11615_11614] = (tmp11617_11616 - 1); if (tmp11617_11616 <= 0) break;
/* 2021 */                             out = _jspx_page_context.popBody(); }
/* 2022 */                           _jspx_th_c_005fforEach_005f5.doCatch(_jspx_exception);
/*      */                         } finally {
/* 2024 */                           _jspx_th_c_005fforEach_005f5.doFinally();
/* 2025 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5);
/*      */                         }
/* 2027 */                         out.write("\n\t           </optgroup>\n\t           \n\t           <optgroup label=\"");
/* 2028 */                         if (_jspx_meth_fmt_005fmessage_005f22(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2030 */                         out.write("\">\n\t           \t ");
/*      */                         
/* 2032 */                         ForEachTag _jspx_th_c_005fforEach_005f6 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2033 */                         _jspx_th_c_005fforEach_005f6.setPageContext(_jspx_page_context);
/* 2034 */                         _jspx_th_c_005fforEach_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2036 */                         _jspx_th_c_005fforEach_005f6.setItems("${userList}");
/*      */                         
/* 2038 */                         _jspx_th_c_005fforEach_005f6.setVar("row");
/*      */                         
/* 2040 */                         _jspx_th_c_005fforEach_005f6.setVarStatus("status");
/* 2041 */                         int[] _jspx_push_body_count_c_005fforEach_005f6 = { 0 };
/*      */                         try {
/* 2043 */                           int _jspx_eval_c_005fforEach_005f6 = _jspx_th_c_005fforEach_005f6.doStartTag();
/* 2044 */                           if (_jspx_eval_c_005fforEach_005f6 != 0) {
/*      */                             for (;;) {
/* 2046 */                               out.write("\n\t           \t \t\t\t\t");
/* 2047 */                               if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fforEach_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/*      */                               {
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
/*      */ 
/*      */ 
/*      */ 
/* 2100 */                                 _jspx_th_c_005fforEach_005f6.doFinally();
/* 2101 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6); return;
/*      */                               }
/* 2049 */                               out.write("\n\t           \t \t\t\t\t");
/*      */                               
/* 2051 */                               String optionSelect = "";
/* 2052 */                               if ((selectUserlist != null) && (selectUserlist.contains((String)pageContext.getAttribute("selecteduserid")))) {
/* 2053 */                                 optionSelect = "selected=\"selected\"";
/*      */                               }
/*      */                               
/* 2056 */                               out.write("\n\t           \t \t");
/*      */                               
/* 2058 */                               IfTag _jspx_th_c_005fif_005f34 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2059 */                               _jspx_th_c_005fif_005f34.setPageContext(_jspx_page_context);
/* 2060 */                               _jspx_th_c_005fif_005f34.setParent(_jspx_th_c_005fforEach_005f6);
/*      */                               
/* 2062 */                               _jspx_th_c_005fif_005f34.setTest("${row[\"role\"] == \"MANAGER\"}");
/* 2063 */                               int _jspx_eval_c_005fif_005f34 = _jspx_th_c_005fif_005f34.doStartTag();
/* 2064 */                               if (_jspx_eval_c_005fif_005f34 != 0) {
/*      */                                 for (;;) {
/* 2066 */                                   out.write("\n\t           \t \t\t<option value=\"");
/* 2067 */                                   if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f34, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/*      */                                   {
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
/* 2100 */                                     _jspx_th_c_005fforEach_005f6.doFinally();
/* 2101 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6); return;
/*      */                                   }
/* 2069 */                                   out.write(34);
/* 2070 */                                   out.write(32);
/* 2071 */                                   out.print(optionSelect);
/* 2072 */                                   out.write(62);
/* 2073 */                                   if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f34, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/*      */                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2100 */                                     _jspx_th_c_005fforEach_005f6.doFinally();
/* 2101 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6); return;
/*      */                                   }
/* 2075 */                                   out.write("</option>\n\t           \t \t");
/* 2076 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f34.doAfterBody();
/* 2077 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2081 */                               if (_jspx_th_c_005fif_005f34.doEndTag() == 5) {
/* 2082 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
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
/*      */ 
/* 2100 */                                 _jspx_th_c_005fforEach_005f6.doFinally();
/* 2101 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6); return;
/*      */                               }
/* 2085 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 2086 */                               out.write("\n\t           \t ");
/* 2087 */                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f6.doAfterBody();
/* 2088 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2092 */                           if (_jspx_th_c_005fforEach_005f6.doEndTag() == 5)
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2100 */                             _jspx_th_c_005fforEach_005f6.doFinally();
/* 2101 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6); return;
/*      */                           }
/*      */                         }
/*      */                         catch (Throwable _jspx_exception)
/*      */                         {
/*      */                           for (;;)
/*      */                           {
/* 2096 */                             int tmp12193_12192 = 0; int[] tmp12193_12190 = _jspx_push_body_count_c_005fforEach_005f6; int tmp12195_12194 = tmp12193_12190[tmp12193_12192];tmp12193_12190[tmp12193_12192] = (tmp12195_12194 - 1); if (tmp12195_12194 <= 0) break;
/* 2097 */                             out = _jspx_page_context.popBody(); }
/* 2098 */                           _jspx_th_c_005fforEach_005f6.doCatch(_jspx_exception);
/*      */                         } finally {
/* 2100 */                           _jspx_th_c_005fforEach_005f6.doFinally();
/* 2101 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6);
/*      */                         }
/* 2103 */                         out.write("\n\t           </optgroup>\n\t           \n\t           <optgroup label=\"");
/* 2104 */                         if (_jspx_meth_fmt_005fmessage_005f23(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2106 */                         out.write("\">\n\t           \t ");
/*      */                         
/* 2108 */                         ForEachTag _jspx_th_c_005fforEach_005f7 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2109 */                         _jspx_th_c_005fforEach_005f7.setPageContext(_jspx_page_context);
/* 2110 */                         _jspx_th_c_005fforEach_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2112 */                         _jspx_th_c_005fforEach_005f7.setItems("${userList}");
/*      */                         
/* 2114 */                         _jspx_th_c_005fforEach_005f7.setVar("row");
/*      */                         
/* 2116 */                         _jspx_th_c_005fforEach_005f7.setVarStatus("status");
/* 2117 */                         int[] _jspx_push_body_count_c_005fforEach_005f7 = { 0 };
/*      */                         try {
/* 2119 */                           int _jspx_eval_c_005fforEach_005f7 = _jspx_th_c_005fforEach_005f7.doStartTag();
/* 2120 */                           if (_jspx_eval_c_005fforEach_005f7 != 0) {
/*      */                             for (;;) {
/* 2122 */                               out.write("\n\t           \t \t\t\t\t");
/* 2123 */                               if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fforEach_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/*      */                               {
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
/*      */ 
/*      */ 
/*      */ 
/* 2176 */                                 _jspx_th_c_005fforEach_005f7.doFinally();
/* 2177 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f7); return;
/*      */                               }
/* 2125 */                               out.write("\n\t           \t \t\t\t\t");
/*      */                               
/* 2127 */                               String optionSelect = "";
/* 2128 */                               if ((selectUserlist != null) && (selectUserlist.contains((String)pageContext.getAttribute("selecteduserid")))) {
/* 2129 */                                 optionSelect = "selected=\"selected\"";
/*      */                               }
/*      */                               
/* 2132 */                               out.write("\n\t           \t \t");
/*      */                               
/* 2134 */                               IfTag _jspx_th_c_005fif_005f35 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2135 */                               _jspx_th_c_005fif_005f35.setPageContext(_jspx_page_context);
/* 2136 */                               _jspx_th_c_005fif_005f35.setParent(_jspx_th_c_005fforEach_005f7);
/*      */                               
/* 2138 */                               _jspx_th_c_005fif_005f35.setTest("${row[\"role\"] == \"USERS\"}");
/* 2139 */                               int _jspx_eval_c_005fif_005f35 = _jspx_th_c_005fif_005f35.doStartTag();
/* 2140 */                               if (_jspx_eval_c_005fif_005f35 != 0) {
/*      */                                 for (;;) {
/* 2142 */                                   out.write("\n\t           \t \t\t<option value=\"");
/* 2143 */                                   if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f35, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/*      */                                   {
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
/* 2176 */                                     _jspx_th_c_005fforEach_005f7.doFinally();
/* 2177 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f7); return;
/*      */                                   }
/* 2145 */                                   out.write(34);
/* 2146 */                                   out.write(32);
/* 2147 */                                   out.print(optionSelect);
/* 2148 */                                   out.write(62);
/* 2149 */                                   if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fif_005f35, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/*      */                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2176 */                                     _jspx_th_c_005fforEach_005f7.doFinally();
/* 2177 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f7); return;
/*      */                                   }
/* 2151 */                                   out.write("</option>\n\t           \t \t");
/* 2152 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f35.doAfterBody();
/* 2153 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2157 */                               if (_jspx_th_c_005fif_005f35.doEndTag() == 5) {
/* 2158 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35);
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
/*      */ 
/* 2176 */                                 _jspx_th_c_005fforEach_005f7.doFinally();
/* 2177 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f7); return;
/*      */                               }
/* 2161 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35);
/* 2162 */                               out.write("\n\t           \t ");
/* 2163 */                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f7.doAfterBody();
/* 2164 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2168 */                           if (_jspx_th_c_005fforEach_005f7.doEndTag() == 5)
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2176 */                             _jspx_th_c_005fforEach_005f7.doFinally();
/* 2177 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f7); return;
/*      */                           }
/*      */                         }
/*      */                         catch (Throwable _jspx_exception)
/*      */                         {
/*      */                           for (;;)
/*      */                           {
/* 2172 */                             int tmp12771_12770 = 0; int[] tmp12771_12768 = _jspx_push_body_count_c_005fforEach_005f7; int tmp12773_12772 = tmp12771_12768[tmp12771_12770];tmp12771_12768[tmp12771_12770] = (tmp12773_12772 - 1); if (tmp12773_12772 <= 0) break;
/* 2173 */                             out = _jspx_page_context.popBody(); }
/* 2174 */                           _jspx_th_c_005fforEach_005f7.doCatch(_jspx_exception);
/*      */                         } finally {
/* 2176 */                           _jspx_th_c_005fforEach_005f7.doFinally();
/* 2177 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f7);
/*      */                         }
/* 2179 */                         out.write("\n\t           </optgroup>\n\t           \n\t\t\t\t\t          \n    </select>\n                \n</td></tr>\n");
/*      */                         
/* 2181 */                         IfTag _jspx_th_c_005fif_005f36 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2182 */                         _jspx_th_c_005fif_005f36.setPageContext(_jspx_page_context);
/* 2183 */                         _jspx_th_c_005fif_005f36.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2185 */                         _jspx_th_c_005fif_005f36.setTest("${isDomainGroup }");
/* 2186 */                         int _jspx_eval_c_005fif_005f36 = _jspx_th_c_005fif_005f36.doStartTag();
/* 2187 */                         if (_jspx_eval_c_005fif_005f36 != 0) {
/*      */                           for (;;) {
/* 2189 */                             out.write("\n<tr>\n\t<td>&nbsp;</td>\n\t<td>");
/* 2190 */                             out.print(FormatUtil.getString("am.webclient.useradministration.newuser.role.text"));
/* 2191 */                             out.write("</td>\n</tr>\n<tr>\n\t<td>&nbsp;</td>\n\t<td>\n\t\t");
/* 2192 */                             if (_jspx_meth_html_005fradio_005f0(_jspx_th_c_005fif_005f36, _jspx_page_context))
/*      */                               return;
/* 2194 */                             out.print(FormatUtil.getString("am.webclient.role.administrator.text"));
/* 2195 */                             out.write("&nbsp;&nbsp;\n\t    ");
/* 2196 */                             if (_jspx_meth_html_005fradio_005f1(_jspx_th_c_005fif_005f36, _jspx_page_context))
/*      */                               return;
/* 2198 */                             out.print(FormatUtil.getString("am.webclient.role.administrator.text"));
/* 2199 */                             out.write(40);
/* 2200 */                             out.print(FormatUtil.getString("am.webclient.delegate.text"));
/* 2201 */                             out.write(")&nbsp;&nbsp;\n\t    ");
/* 2202 */                             if (_jspx_meth_html_005fradio_005f2(_jspx_th_c_005fif_005f36, _jspx_page_context))
/*      */                               return;
/* 2204 */                             out.print(FormatUtil.getString("am.webclient.role.operator.text"));
/* 2205 */                             out.write("<br>\n\t</td>\n</tr>\n");
/* 2206 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f36.doAfterBody();
/* 2207 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2211 */                         if (_jspx_th_c_005fif_005f36.doEndTag() == 5) {
/* 2212 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36); return;
/*      */                         }
/*      */                         
/* 2215 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36);
/* 2216 */                         out.write("\n<tr>\n<td></td>\n<td valign=\"top\" colspan=\"4\" >\n\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n\t<tr>\n\t<td width=\"10px\">\n\t<input style=\"padding-left:0px;\" id=\"monitorid\" type=\"checkbox\" name=\"userGroupMG\" value=\"applyselected\" onclick=\"javascript:selectUserGroupMG(this);\">\n\t</td>\n\t<td ><a style=\"cursor:pointer\">");
/* 2217 */                         out.print(FormatUtil.getString("am.reporttab.selectmg.text"));
/* 2218 */                         out.write("</a></td>\n\t</tr>\n\t</table>\n\t<br>\n\t");
/* 2219 */                         if (_jspx_meth_c_005fif_005f37(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2221 */                         out.write("\n\n</td>\n\n</tr>\n<tr class=\"tablebottom\">\n\t<td >&nbsp;</td>\n\t<td  align=\"left\">\n\t\t");
/*      */                         
/* 2223 */                         IfTag _jspx_th_c_005fif_005f38 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2224 */                         _jspx_th_c_005fif_005f38.setPageContext(_jspx_page_context);
/* 2225 */                         _jspx_th_c_005fif_005f38.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2227 */                         _jspx_th_c_005fif_005f38.setTest("${param.method=='editUserGroup'}");
/* 2228 */                         int _jspx_eval_c_005fif_005f38 = _jspx_th_c_005fif_005f38.doStartTag();
/* 2229 */                         if (_jspx_eval_c_005fif_005f38 != 0) {
/*      */                           for (;;) {
/* 2231 */                             out.write("\n\t\t\t<input type=\"button\" name=\"editUserGroup\" value=\"");
/* 2232 */                             out.print(FormatUtil.getString("am.webclient.useradministration.usergroup.update.text"));
/* 2233 */                             out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:addnewUserGroup(this.form)\">\n\t\t");
/* 2234 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f38.doAfterBody();
/* 2235 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2239 */                         if (_jspx_th_c_005fif_005f38.doEndTag() == 5) {
/* 2240 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38); return;
/*      */                         }
/*      */                         
/* 2243 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38);
/* 2244 */                         out.write(10);
/* 2245 */                         out.write(9);
/* 2246 */                         out.write(9);
/*      */                         
/* 2248 */                         IfTag _jspx_th_c_005fif_005f39 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2249 */                         _jspx_th_c_005fif_005f39.setPageContext(_jspx_page_context);
/* 2250 */                         _jspx_th_c_005fif_005f39.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2252 */                         _jspx_th_c_005fif_005f39.setTest("${param.method !='editUserGroup'}");
/* 2253 */                         int _jspx_eval_c_005fif_005f39 = _jspx_th_c_005fif_005f39.doStartTag();
/* 2254 */                         if (_jspx_eval_c_005fif_005f39 != 0) {
/*      */                           for (;;) {
/* 2256 */                             out.write("\n\t\t\t<input type=\"button\" name=\"newUserGroup\" value=\"");
/* 2257 */                             out.print(FormatUtil.getString("am.webclient.useradministration.create.usergroup.text"));
/* 2258 */                             out.write("\" class=\"buttons btn_highlt\" onClick=\"javascript:addnewUserGroup(this.form)\">\n\t\t");
/* 2259 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f39.doAfterBody();
/* 2260 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2264 */                         if (_jspx_th_c_005fif_005f39.doEndTag() == 5) {
/* 2265 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f39); return;
/*      */                         }
/*      */                         
/* 2268 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f39);
/* 2269 */                         out.write("\n\t\t<input type=\"button\" name=\"sub1\" value=\"");
/* 2270 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 2271 */                         out.write("\" class=\"buttons btn_link\" onClick=\"javascript:location.href='/admin/userconfiguration.do?method=showUsers&showtab=usergroup';\">\n\t</td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</div>\n<!-- Local Auth Config Div Ends-->\n<!-- AD Auth Config Div Starts-->\n\t");
/* 2272 */                         if (_jspx_meth_c_005fif_005f40(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2274 */                         out.write("\n<!-- AD Auth Config Div Endss-->\n</td>\n</tr>\n</table>\n</td>\n<td valign=\"top\" width=\"30%\" style=\"padding-bottom:10px;\">\n");
/* 2275 */                         if (!OEMUtil.isRemove("am.userrole.remove")) {
/* 2276 */                           out.write("\n<div id=\"helpdiv_localgroup\" style=\"display:none;\">\n");
/* 2277 */                           JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.usergroup.helpcard.text", new String[] { OEMUtil.getOEMString("product.name") })), request.getCharacterEncoding()), out, false);
/* 2278 */                           out.write("\n </div>\n <div id=\"helpdiv_adgroup\" style=\"display:none;\">\n");
/* 2279 */                           JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admintab.addusergroup.import.helpcard.text", new String[] { OEMUtil.getOEMString("product.name") })), request.getCharacterEncoding()), out, false);
/* 2280 */                           out.write("\n </div>\n\n");
/*      */                         }
/* 2282 */                         out.write("\n</td>\n</tr>\n</table>\n</div>\n\n\n\n<div id=\"domainconfiguration\"  style=' display:none; width: 100%; padding: 10px 0px 0px 0px;'>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n<td width=\"53%\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"padding-right:0px;\">\n\n  \t<tr>\n\n\t<td class=\"tableheading-monitor-config\">\n\n\t<div id=\"customfieldTabs\">\n\t <ul class=\"ulstyleforcf\">\n\t\t<li class=\"listyleforcf\" id=\"localgroup_tabs\"><span class=\"ulanchor\"><img src=\"/images/icon_customfields_add.png\" style=\"position: relative;top: 12px;\" border=\"0\" align=\"left\"></img>\n\t\t\t\t&nbsp;");
/* 2283 */                         if (_jspx_meth_fmt_005fmessage_005f24(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2285 */                         out.write("</span>\t\t");
/* 2286 */                         out.write("\n\t\t</li>\n\t  </ul>\n\t</div>\n\n\t</td>\n\t</tr>\n<tr>\n<td  width=\"100%\">\n<br/>\n\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n<td width=\"70%\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n\n\n\t<tr>\n\t\t\t<td width=\"25%\" height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 2287 */                         out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.domain.text"));
/* 2288 */                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"20\" > ");
/* 2289 */                         if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2291 */                         out.write("<span id=\"newdomainname_alert\" class=\"mandatory\"></span></td>\n\t</tr>\n\n\t<tr>\n\t\t<td width=\"25%\" height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 2292 */                         out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.dns.text"));
/* 2293 */                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t<td height=\"20\" > ");
/* 2294 */                         if (_jspx_meth_html_005ftext_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2296 */                         out.write("</td>\n\t</tr>\n\t\n\t<tr>\n\t\t<td width=\"25%\" height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 2297 */                         out.print(FormatUtil.getString("am.webclient.admintab.adduser.importad.domainport.text"));
/* 2298 */                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t<td height=\"20\" > ");
/* 2299 */                         if (_jspx_meth_html_005ftext_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2301 */                         out.write("</td>\n\t</tr>\n\t\n\t<tr>\n\t\t<td width=\"25%\" height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 2302 */                         out.print(FormatUtil.getString("am.webclient.useradministration.domaindetails.service.text"));
/* 2303 */                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t<td height=\"20\" > \n\t\t");
/*      */                         
/* 2305 */                         SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 2306 */                         _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 2307 */                         _jspx_th_html_005fselect_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2309 */                         _jspx_th_html_005fselect_005f2.setProperty("newDomainService");
/*      */                         
/* 2311 */                         _jspx_th_html_005fselect_005f2.setStyleClass("formtext normal");
/*      */                         
/* 2313 */                         _jspx_th_html_005fselect_005f2.setStyle("width: 215px;");
/* 2314 */                         int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 2315 */                         if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 2316 */                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 2317 */                             out = _jspx_page_context.pushBody();
/* 2318 */                             _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 2319 */                             _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2322 */                             out.write("\n\t\t\t   ");
/*      */                             
/* 2324 */                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2325 */                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2326 */                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f2);
/*      */                             
/* 2328 */                             _jspx_th_html_005foption_005f1.setValue("ACTIVE DIRECTORY");
/* 2329 */                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2330 */                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2331 */                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2332 */                                 out = _jspx_page_context.pushBody();
/* 2333 */                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2334 */                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2337 */                                 out.print(FormatUtil.getString("am.webclient.useradministration.activedirectory.text"));
/* 2338 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2339 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2342 */                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2343 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2346 */                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2347 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                             }
/*      */                             
/* 2350 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2351 */                             out.write("\n               ");
/*      */                             
/* 2353 */                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2354 */                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2355 */                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f2);
/*      */                             
/* 2357 */                             _jspx_th_html_005foption_005f2.setValue("LDAP");
/* 2358 */                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2359 */                             if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2360 */                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2361 */                                 out = _jspx_page_context.pushBody();
/* 2362 */                                 _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 2363 */                                 _jspx_th_html_005foption_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2366 */                                 out.print(FormatUtil.getString("am.webclient.useradministration.ldap.text"));
/* 2367 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2368 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2371 */                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2372 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2375 */                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2376 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                             }
/*      */                             
/* 2379 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2380 */                             out.write("\n        ");
/* 2381 */                             int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 2382 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2385 */                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 2386 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2389 */                         if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 2390 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                         }
/*      */                         
/* 2393 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 2394 */                         out.write("\n\t\t</td>\n\t</tr>\n\t\n\t  <tr id=\"sslenablerow\">\n    <td width=\"26%\" class=\"bodytext label-align\">");
/* 2395 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssl"));
/* 2396 */                         out.write("</td>\n    <td > ");
/* 2397 */                         if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2399 */                         out.write("</td>\n  </tr>\n\t<tr>\n\t\t<td width=\"25%\" height=\"20\" class=\"bodytext label-align\"> &nbsp; ");
/* 2400 */                         out.print(FormatUtil.getString("am.webclient.useradministration.domain.permission.text"));
/* 2401 */                         out.write("<span class=\"mandatory\">*</span></td>\n\t\t<td height=\"20\" >\n\t\t<table width=\"40%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr><td>\n\t\t");
/* 2402 */                         if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2404 */                         out.print(FormatUtil.getString("am.webclient.useradministration.domain.readonly.text"));
/* 2405 */                         out.write("\n\t\t</td>\n\t\t<td>\n\t\t");
/* 2406 */                         if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2408 */                         out.print(FormatUtil.getString("am.webclient.useradministration.domain.fullcontrol.text"));
/* 2409 */                         out.write("\n\t\t</td>\n\t\t</tr>\n\t\t</table>\n\t\t\t\t \n\t             \n\t\t</td>\n\t</tr>\n\t\n<tr class=\"tablebottom\">\n\t<td >&nbsp;</td>\n\t<td  align=\"left\">\n\t\t\t");
/*      */                         
/* 2411 */                         IfTag _jspx_th_c_005fif_005f41 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2412 */                         _jspx_th_c_005fif_005f41.setPageContext(_jspx_page_context);
/* 2413 */                         _jspx_th_c_005fif_005f41.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2415 */                         _jspx_th_c_005fif_005f41.setTest("${param.method=='editDomainConfig'}");
/* 2416 */                         int _jspx_eval_c_005fif_005f41 = _jspx_th_c_005fif_005f41.doStartTag();
/* 2417 */                         if (_jspx_eval_c_005fif_005f41 != 0) {
/*      */                           for (;;) {
/* 2419 */                             out.write("\n\t\t\t\t<input type=\"button\" value=\"");
/* 2420 */                             out.print(FormatUtil.getString("am.webclient.useradministration.domain.update.text"));
/* 2421 */                             out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:addNewDomain(this.form)\">\n\t\t\t");
/* 2422 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f41.doAfterBody();
/* 2423 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2427 */                         if (_jspx_th_c_005fif_005f41.doEndTag() == 5) {
/* 2428 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f41); return;
/*      */                         }
/*      */                         
/* 2431 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f41);
/* 2432 */                         out.write("\n\t\t\t");
/*      */                         
/* 2434 */                         IfTag _jspx_th_c_005fif_005f42 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2435 */                         _jspx_th_c_005fif_005f42.setPageContext(_jspx_page_context);
/* 2436 */                         _jspx_th_c_005fif_005f42.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2438 */                         _jspx_th_c_005fif_005f42.setTest("${param.method !='editDomainConfig'}");
/* 2439 */                         int _jspx_eval_c_005fif_005f42 = _jspx_th_c_005fif_005f42.doStartTag();
/* 2440 */                         if (_jspx_eval_c_005fif_005f42 != 0) {
/*      */                           for (;;) {
/* 2442 */                             out.write("\n\t\t\t\t<input type=\"button\" name=\"newDomainConfig\" value=\"");
/* 2443 */                             out.print(FormatUtil.getString("am.webclient.useradministration.domain.add.text"));
/* 2444 */                             out.write("\" class=\"buttons btn_highlt\" onClick=\"javascript:addNewDomain(this.form)\">\n\t\t\t");
/* 2445 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f42.doAfterBody();
/* 2446 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2450 */                         if (_jspx_th_c_005fif_005f42.doEndTag() == 5) {
/* 2451 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f42); return;
/*      */                         }
/*      */                         
/* 2454 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f42);
/* 2455 */                         out.write("\n\t\t\t<input type=\"button\" name=\"sub1\" value=\"");
/* 2456 */                         out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 2457 */                         out.write("\" class=\"buttons btn_link\" onClick=\"javascript:location.href='/admin/userconfiguration.do?method=showUsers&showtab=domaindetails';\">\n\t</td>\n</tr>\n\n</table>\n</td>\n</tr>\n</table>\n\n</td>\n</tr>\n</table>\n</td>\n<td valign=\"top\" width=\"30%\" style=\"padding-bottom:10px;\">\n");
/* 2458 */                         if (!OEMUtil.isRemove("am.userrole.remove")) {
/* 2459 */                           out.write("\n<div id=\"helpdiv_DomainConfig\" style=\"display:none;\">\n");
/* 2460 */                           JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.useradministration.domain.helpcard.text", new String[] { OEMUtil.getOEMString("product.name") })), request.getCharacterEncoding()), out, false);
/* 2461 */                           out.write("\n</div>\n");
/*      */                         }
/* 2463 */                         out.write("\n</td>\n</tr>\n</table>\n</div>\n\t");
/* 2464 */                         int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2465 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2469 */                     if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2470 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */                     }
/*      */                     else {
/* 2473 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2474 */                       out.write("\n<br>\n\n\n\n\n\n");
/*      */                     }
/* 2476 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 2477 */         out = _jspx_out;
/* 2478 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2479 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2480 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2483 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2489 */     PageContext pageContext = _jspx_page_context;
/* 2490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2492 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2493 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2494 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2495 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2496 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 2498 */         out.write("\n\t\t\t");
/* 2499 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2500 */           return true;
/* 2501 */         out.write("\n\t\t\t");
/* 2502 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2503 */           return true;
/* 2504 */         out.write(10);
/* 2505 */         out.write(9);
/* 2506 */         out.write(9);
/* 2507 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2508 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2512 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2513 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2514 */       return true;
/*      */     }
/* 2516 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2522 */     PageContext pageContext = _jspx_page_context;
/* 2523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2525 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2526 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2527 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 2529 */     _jspx_th_c_005fwhen_005f0.setTest("${param.method == \"editDomainConfig\"}");
/* 2530 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2531 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 2533 */         out.write("\n\t\t\t\tdocument.UserConfiguration.domainID.value='");
/* 2534 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 2535 */           return true;
/* 2536 */         out.write("'\n\t\t\t\tdocument.UserConfiguration.method.value='updateDomainConfig'\t\t// NO I18N\n\t\t\t");
/* 2537 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2538 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2542 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2543 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2544 */       return true;
/*      */     }
/* 2546 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2547 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2552 */     PageContext pageContext = _jspx_page_context;
/* 2553 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2555 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2556 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2557 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2559 */     _jspx_th_c_005fout_005f0.setValue("${param.domainid}");
/* 2560 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2561 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2562 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2563 */       return true;
/*      */     }
/* 2565 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2566 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2571 */     PageContext pageContext = _jspx_page_context;
/* 2572 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2574 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2575 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2576 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 2577 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2578 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 2580 */         out.write("\n\t\t\t\tdocument.UserConfiguration.method.value='addDomainConfig'\t\t// NO I18N\n\t\t\t");
/* 2581 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2582 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2586 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2587 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2588 */       return true;
/*      */     }
/* 2590 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2591 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2596 */     PageContext pageContext = _jspx_page_context;
/* 2597 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2599 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2600 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2601 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 2603 */     _jspx_th_c_005fout_005f1.setValue("${param.groupid }");
/* 2604 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2605 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2606 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2607 */       return true;
/*      */     }
/* 2609 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2610 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2615 */     PageContext pageContext = _jspx_page_context;
/* 2616 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2618 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2619 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2620 */     _jspx_th_c_005fchoose_005f1.setParent(null);
/* 2621 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2622 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 2624 */         out.write("\n\t\t\t");
/* 2625 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 2626 */           return true;
/* 2627 */         out.write("\n\t\t\t");
/* 2628 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 2629 */           return true;
/* 2630 */         out.write(10);
/* 2631 */         out.write(9);
/* 2632 */         out.write(9);
/* 2633 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2634 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2638 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2639 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2640 */       return true;
/*      */     }
/* 2642 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2643 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2648 */     PageContext pageContext = _jspx_page_context;
/* 2649 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2651 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2652 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2653 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 2655 */     _jspx_th_c_005fwhen_005f1.setTest("${!disableRestrictedAdmin}");
/* 2656 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2657 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 2659 */         out.write("\n\t\t\t\tjQuery(\"#restrictedadminuser\").show()\t\t// NO I18N\n\t\t\t\tif(document.UserConfiguration.delegatedAdmin.checked){\n\t\t\t\t\tjQuery(\"#mgselection\").show()\t// NO I18N\n\t\t\t\t}else{\n\t\t\t\t\tjQuery(\"#mgselection\").hide()\t// NO I18N\n\t\t\t\t}\n\t\t\t");
/* 2660 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2661 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2665 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2666 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2667 */       return true;
/*      */     }
/* 2669 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2670 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2675 */     PageContext pageContext = _jspx_page_context;
/* 2676 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2678 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2679 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2680 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 2681 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2682 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 2684 */         out.write("\n\t\t\t\tjQuery(\"#mgselection\").hide()\t// NO I18N\n\t\t\t");
/* 2685 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2686 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2690 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2691 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2692 */       return true;
/*      */     }
/* 2694 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2695 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2700 */     PageContext pageContext = _jspx_page_context;
/* 2701 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2703 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2704 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2705 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 2707 */     _jspx_th_c_005fif_005f3.setTest("${!empty updateProfile && updateProfile=='true'}");
/* 2708 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2709 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 2711 */         out.write("\n\t\tjQuery(\"#mgselection\").hide()\t\t// NO I18N\n\t\tjQuery('select[name=\"groups\"]').attr('disabled','disabled');\t//NO I18N\n\t\tjQuery('input[name=\"delegatedAdmin\"]').attr(\"disabled\",\"disabled\");\t//NO I18N\n\t");
/* 2712 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2713 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2717 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2718 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2719 */       return true;
/*      */     }
/* 2721 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2727 */     PageContext pageContext = _jspx_page_context;
/* 2728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2730 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2731 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2732 */     _jspx_th_c_005fchoose_005f2.setParent(null);
/* 2733 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2734 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 2736 */         out.write(10);
/* 2737 */         out.write(9);
/* 2738 */         out.write(9);
/* 2739 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 2740 */           return true;
/* 2741 */         out.write(10);
/* 2742 */         out.write(9);
/* 2743 */         out.write(9);
/* 2744 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 2745 */           return true;
/* 2746 */         out.write(10);
/* 2747 */         out.write(9);
/* 2748 */         out.write(9);
/* 2749 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 2750 */           return true;
/* 2751 */         out.write(10);
/* 2752 */         out.write(9);
/* 2753 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2754 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2758 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2759 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2760 */       return true;
/*      */     }
/* 2762 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2763 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2768 */     PageContext pageContext = _jspx_page_context;
/* 2769 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2771 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2772 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2773 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 2775 */     _jspx_th_c_005fwhen_005f2.setTest("${param.domainconfig == true}");
/* 2776 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2777 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 2779 */         out.write("\n\t\t\tjQuery(\"#customfieldsfullList\").hide();\t\t// NO I18N\n\t\t\tjQuery(\"#domainconfiguration\").show()\t\t// NO I18N\n\t\t\tjQuery(\"#helpdiv_DomainConfig\").show();\t\t// NO I18N\n\t\t");
/* 2780 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2781 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2785 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2786 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2787 */       return true;
/*      */     }
/* 2789 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2795 */     PageContext pageContext = _jspx_page_context;
/* 2796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2798 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2799 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2800 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 2802 */     _jspx_th_c_005fwhen_005f3.setTest("${param.usergroup != \"true\"}");
/* 2803 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2804 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 2806 */         out.write("\n\t\t\tvar locn = document.location.hash;\n\t\t\t\n\t\t\tif(locn.indexOf(\"uc/ADAuth\") != -1)\n\t\t\t{\n\t\t\t  toggleCustomFieldDiv1('ADAuth')\t// NO I18N\n\t\t\t  \n\t\t\t}else if(locn.indexOf(\"uc/Ldapauth\") != -1){\n\t\t\t\t\n\t\t\t\t\n\t\t\t\ttoggleCustomFieldDiv1('Ldapauth')\t// NO I18N\n\t\t\t\t \n\t\t\t}\n\t\t\telse{\n\t\t\t\t\n\t\t\t  toggleCustomFieldDiv1('LocalAuth')\t// NO I18N\n\t\t\t}\n\t\t");
/* 2807 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2808 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2812 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2813 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2814 */       return true;
/*      */     }
/* 2816 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2822 */     PageContext pageContext = _jspx_page_context;
/* 2823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2825 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2826 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2827 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 2828 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2829 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 2831 */         out.write("\n\t\t\tdisplayAddUserGroup();\n\t\t");
/* 2832 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2833 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2837 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2838 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2839 */       return true;
/*      */     }
/* 2841 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2842 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2847 */     PageContext pageContext = _jspx_page_context;
/* 2848 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2850 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2851 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2852 */     _jspx_th_c_005fif_005f4.setParent(null);
/*      */     
/* 2854 */     _jspx_th_c_005fif_005f4.setTest("${param.method !='editUserGroup'}");
/* 2855 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2856 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2858 */         out.write("\n\t\t\tvar groupname = jQuery('input[name=usergroupName]').val()\t\t// NO I18N\n\t\t\tjQuery.ajax({\n\t\t\t\t\n\t \t\t\turl: \"/admin/userconfiguration.do?method=checkUserGroupName&groupname=\"+groupname+\"&randomnumber=\"+Math.random(),\t// NO I18N\n\t \t\t\tsuccess:function(data){\n\t \t\t\t\t\n\t \t \t\t\tif(data.trim() == 'true'){\n\t\t\t\t\t\t\tjQuery(\"#usergroupname_alert\").html('&nbsp;");
/* 2859 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 2860 */           return true;
/* 2861 */         out.write("').fadeIn().delay(2000).fadeOut();\t//No I18N\n\t\t\t\t\t\t\t$(\"input[name=newUserGroup]\").attr(\"disabled\", true); //No I18N\n\t \t \t \t\t\t\treturn false;\n\t \t \t\t\t}else{\n\t \t \t\t\t\t\tif(groupname.trim().length  > 0 ){\n\t \t \t\t\t\t\tjQuery(\"#usergroupname_alert\").html('&nbsp;<img src=\"/images/icon_tickmark.gif\">');\t\t// NO I18N \n\t \t \t\t\t\t\t$(\"input[name=newUserGroup]\").attr(\"disabled\", false); //No I18N\n\t \t \t\t\t\t\tjQuery(\"#usergroupname_alert\").show()\t\t// NO I18N \n\t \t \t\t\t\t\t}\n\t \t \t\t\t}\n\t\t\t\t}\n\t\t\t});\n\t\t");
/* 2862 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2863 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2867 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2868 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2869 */       return true;
/*      */     }
/* 2871 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2872 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2877 */     PageContext pageContext = _jspx_page_context;
/* 2878 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2880 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 2881 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2882 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 2884 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.useradministration.usergroup.duplicate.message.text");
/* 2885 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2886 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 2887 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2888 */         out = _jspx_page_context.pushBody();
/* 2889 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 2890 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2893 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f0, _jspx_page_context))
/* 2894 */           return true;
/* 2895 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 2896 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2899 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2900 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2903 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2904 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2905 */       return true;
/*      */     }
/* 2907 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2908 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2913 */     PageContext pageContext = _jspx_page_context;
/* 2914 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2916 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 2917 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 2918 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f0);
/* 2919 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 2920 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 2921 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 2922 */         out = _jspx_page_context.pushBody();
/* 2923 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/* 2924 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2927 */         out.write("'+groupname+'");
/* 2928 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 2929 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2932 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 2933 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2936 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 2937 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 2938 */       return true;
/*      */     }
/* 2940 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 2941 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2946 */     PageContext pageContext = _jspx_page_context;
/* 2947 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2949 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2950 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2951 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 2953 */     _jspx_th_c_005fif_005f5.setTest("${param.method !='editDomainConfig'}");
/* 2954 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2955 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 2957 */         out.write("\n\t\t\t\tvar domainName = jQuery('input[name=newDomainName]').val()\t\t// NO I18N\n\t\t\t\tjQuery.ajax({\n\t\t \t\t\turl: \"/admin/userconfiguration.do?method=checkDomainName&domainname=\"+domainName+\"&randomnumber=\"+Math.random(),\t// NO I18N\n\t\t \t\t\tsuccess:function(data){\n\t\t \t\t\t\t\n\t\t \t \t\t\tif(data.trim() == 'true'){\n\t\t\t\t\t\t\t\tjQuery(\"#newdomainname_alert\").html('&nbsp;");
/* 2958 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 2959 */           return true;
/* 2960 */         out.write("').fadeIn().delay(2000).fadeOut();\t//No I18N\n\t\t\t\t\t\t\t\tjQuery(\"[name='newDomainName']\").focus();\t//No I18N\n\t\t\t\t\t\t\t\t$(\"input[name=newDomainConfig]\").attr(\"disabled\", true); //No I18N\n\t\t \t \t \t\t\t\treturn false;\n\t\t \t \t\t\t}else{\n\t\t \t \t\t\t\t\tif(domainName.trim().length  > 0 ){\n\t\t \t \t\t\t//\t\tjQuery(\"#newdomainname_alert\").html('&nbsp;<img src=\"/images/icon_tickmark.gif\">');\t\t// NO I18N \n\t\t \t \t\t\t\t\t$(\"input[name=newDomainConfig]\").attr(\"disabled\", false); //No I18N\n\t\t \t \t\t\t\t\tjQuery(\"#newdomainname_alert\").show()\t\t// NO I18N \n\t\t \t \t\t\t\t\t}\n\t\t \t \t\t\t}\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t");
/* 2961 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2962 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2966 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2967 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2968 */       return true;
/*      */     }
/* 2970 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2971 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2976 */     PageContext pageContext = _jspx_page_context;
/* 2977 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2979 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 2980 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2981 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 2983 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.useradministration.domain.duplicate.message.text");
/* 2984 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2985 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 2986 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2987 */         out = _jspx_page_context.pushBody();
/* 2988 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 2989 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2992 */         if (_jspx_meth_fmt_005fparam_005f1(_jspx_th_fmt_005fmessage_005f1, _jspx_page_context))
/* 2993 */           return true;
/* 2994 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 2995 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2998 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2999 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3002 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3003 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3004 */       return true;
/*      */     }
/* 3006 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3007 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f1(JspTag _jspx_th_fmt_005fmessage_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3012 */     PageContext pageContext = _jspx_page_context;
/* 3013 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3015 */     ParamTag _jspx_th_fmt_005fparam_005f1 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3016 */     _jspx_th_fmt_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3017 */     _jspx_th_fmt_005fparam_005f1.setParent((Tag)_jspx_th_fmt_005fmessage_005f1);
/* 3018 */     int _jspx_eval_fmt_005fparam_005f1 = _jspx_th_fmt_005fparam_005f1.doStartTag();
/* 3019 */     if (_jspx_eval_fmt_005fparam_005f1 != 0) {
/* 3020 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 3021 */         out = _jspx_page_context.pushBody();
/* 3022 */         _jspx_th_fmt_005fparam_005f1.setBodyContent((BodyContent)out);
/* 3023 */         _jspx_th_fmt_005fparam_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3026 */         out.write("'+domainName+'");
/* 3027 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f1.doAfterBody();
/* 3028 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3031 */       if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 3032 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3035 */     if (_jspx_th_fmt_005fparam_005f1.doEndTag() == 5) {
/* 3036 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 3037 */       return true;
/*      */     }
/* 3039 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 3040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3045 */     PageContext pageContext = _jspx_page_context;
/* 3046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3048 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3049 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3050 */     _jspx_th_c_005fif_005f6.setParent(null);
/*      */     
/* 3052 */     _jspx_th_c_005fif_005f6.setTest("${param.method !='editDomainConfig'}");
/* 3053 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3054 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 3056 */         out.write("\n\t$('#sslenablerow').closest('tr').hide(); // NO I18N\n\t");
/* 3057 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3058 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3062 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3063 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3064 */       return true;
/*      */     }
/* 3066 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3072 */     PageContext pageContext = _jspx_page_context;
/* 3073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3075 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3076 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3077 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 3079 */     _jspx_th_c_005fwhen_005f6.setTest("${param.domainconfig == \"true\" }");
/* 3080 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3081 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 3083 */         out.write("\n\t\t\t \t");
/* 3084 */         if (_jspx_meth_c_005fchoose_005f5(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/* 3085 */           return true;
/* 3086 */         out.write("\n\t\t\t ");
/* 3087 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3088 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3092 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3093 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3094 */       return true;
/*      */     }
/* 3096 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3102 */     PageContext pageContext = _jspx_page_context;
/* 3103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3105 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3106 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 3107 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/* 3108 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 3109 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 3111 */         out.write("\n\t\t\t\t\t ");
/* 3112 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 3113 */           return true;
/* 3114 */         out.write("\n\t\t\t\t\t ");
/* 3115 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/* 3116 */           return true;
/* 3117 */         out.write("\n\t\t\t\t ");
/* 3118 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3119 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3123 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3124 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3125 */       return true;
/*      */     }
/* 3127 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3133 */     PageContext pageContext = _jspx_page_context;
/* 3134 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3136 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3137 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3138 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 3140 */     _jspx_th_c_005fwhen_005f7.setTest("${param.method=='editDomainConfig'}");
/* 3141 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3142 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 3144 */         out.write("\n\t\t\t\t\t \t");
/* 3145 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fwhen_005f7, _jspx_page_context))
/* 3146 */           return true;
/* 3147 */         out.write(9);
/* 3148 */         out.write(9);
/* 3149 */         out.write("\n\t\t\t\t\t ");
/* 3150 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3151 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3155 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3156 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3157 */       return true;
/*      */     }
/* 3159 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3165 */     PageContext pageContext = _jspx_page_context;
/* 3166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3168 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3169 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3170 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/* 3171 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3172 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 3173 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3174 */         out = _jspx_page_context.pushBody();
/* 3175 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 3176 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3179 */         out.write("am.webclient.useradministration.domain.update.text");
/* 3180 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3181 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3184 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3185 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3188 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3189 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3190 */       return true;
/*      */     }
/* 3192 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3193 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3198 */     PageContext pageContext = _jspx_page_context;
/* 3199 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3201 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3202 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 3203 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 3204 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 3205 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 3207 */         out.write("\n\t\t\t\t\t \t");
/* 3208 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 3209 */           return true;
/* 3210 */         out.write(9);
/* 3211 */         out.write(9);
/* 3212 */         out.write("\n\t\t\t\t\t ");
/* 3213 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 3214 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3218 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 3219 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3220 */       return true;
/*      */     }
/* 3222 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3228 */     PageContext pageContext = _jspx_page_context;
/* 3229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3231 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3232 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3233 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/* 3234 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3235 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 3236 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3237 */         out = _jspx_page_context.pushBody();
/* 3238 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 3239 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3242 */         out.write("am.webclient.useradministration.domain.newdomain.text");
/* 3243 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3244 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3247 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3248 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3251 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3252 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3253 */       return true;
/*      */     }
/* 3255 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3256 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3261 */     PageContext pageContext = _jspx_page_context;
/* 3262 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3264 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3265 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3266 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 3267 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3268 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 3270 */         out.write("\n\t\t\t \t");
/* 3271 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/* 3272 */           return true;
/* 3273 */         out.write(9);
/* 3274 */         out.write(9);
/* 3275 */         out.write("\n\t\t\t ");
/* 3276 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3277 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3281 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3282 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3283 */       return true;
/*      */     }
/* 3285 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3286 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3291 */     PageContext pageContext = _jspx_page_context;
/* 3292 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3294 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3295 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3296 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/* 3297 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3298 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3299 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3300 */         out = _jspx_page_context.pushBody();
/* 3301 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3302 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3305 */         out.write("am.webclient.newuser.text");
/* 3306 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3307 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3310 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3311 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3314 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3315 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3316 */       return true;
/*      */     }
/* 3318 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3319 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3324 */     PageContext pageContext = _jspx_page_context;
/* 3325 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3327 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3328 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 3329 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3331 */     _jspx_th_html_005fhidden_005f0.setProperty("domainID");
/* 3332 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 3333 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 3334 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3335 */       return true;
/*      */     }
/* 3337 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3343 */     PageContext pageContext = _jspx_page_context;
/* 3344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3346 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3347 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3348 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3350 */     _jspx_th_c_005fif_005f9.setTest("${param.method=='editUser'}");
/* 3351 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3352 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 3354 */         out.write(10);
/* 3355 */         out.write(9);
/* 3356 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 3357 */           return true;
/* 3358 */         out.write(10);
/* 3359 */         out.write(9);
/* 3360 */         if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 3361 */           return true;
/* 3362 */         out.write("\n\t<input type=\"hidden\" name=\"method\" value=\"updateUser\">\n      ");
/* 3363 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3364 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3368 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3369 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3370 */       return true;
/*      */     }
/* 3372 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3373 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3378 */     PageContext pageContext = _jspx_page_context;
/* 3379 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3381 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3382 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 3383 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 3385 */     _jspx_th_html_005fhidden_005f1.setProperty("userid");
/* 3386 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 3387 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 3388 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3389 */       return true;
/*      */     }
/* 3391 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3392 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3397 */     PageContext pageContext = _jspx_page_context;
/* 3398 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3400 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3401 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3402 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 3404 */     _jspx_th_c_005fif_005f10.setTest("${param.CustomField =='NewUser'}");
/* 3405 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3406 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 3408 */         out.write("\n\t\t<input type=\"hidden\" name=\"CustomField\" value=\"NewUser\">\n\t\t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3409 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 3410 */           return true;
/* 3411 */         out.write("\">\n\t");
/* 3412 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3413 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3417 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3418 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3419 */       return true;
/*      */     }
/* 3421 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3422 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3427 */     PageContext pageContext = _jspx_page_context;
/* 3428 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3430 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3431 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3432 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 3434 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 3435 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3436 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3437 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3438 */       return true;
/*      */     }
/* 3440 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3441 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3446 */     PageContext pageContext = _jspx_page_context;
/* 3447 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3449 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3450 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3451 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3453 */     _jspx_th_c_005fif_005f11.setTest("${param.method == 'editUserGroup'}");
/* 3454 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3455 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 3457 */         out.write("\n      ");
/* 3458 */         if (_jspx_meth_html_005fhidden_005f2(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 3459 */           return true;
/* 3460 */         out.write("\n\t  <input type=\"hidden\" name=\"method\" value=\"updateUserGroup\">\n    ");
/* 3461 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3462 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3466 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3467 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3468 */       return true;
/*      */     }
/* 3470 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3476 */     PageContext pageContext = _jspx_page_context;
/* 3477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3479 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3480 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 3481 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 3483 */     _jspx_th_html_005fhidden_005f2.setProperty("groupid");
/* 3484 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 3485 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 3486 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 3487 */       return true;
/*      */     }
/* 3489 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 3490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3495 */     PageContext pageContext = _jspx_page_context;
/* 3496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3498 */     ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3499 */     _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 3500 */     _jspx_th_c_005fchoose_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 3501 */     int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 3502 */     if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */       for (;;) {
/* 3504 */         out.write("\n    ");
/* 3505 */         if (_jspx_meth_c_005fwhen_005f8(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/* 3506 */           return true;
/* 3507 */         out.write("\n    ");
/* 3508 */         if (_jspx_meth_c_005fotherwise_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/* 3509 */           return true;
/* 3510 */         out.write(10);
/* 3511 */         out.write(9);
/* 3512 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3513 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3517 */     if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3518 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3519 */       return true;
/*      */     }
/* 3521 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3522 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f8(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3527 */     PageContext pageContext = _jspx_page_context;
/* 3528 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3530 */     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3531 */     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3532 */     _jspx_th_c_005fwhen_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*      */     
/* 3534 */     _jspx_th_c_005fwhen_005f8.setTest("${param.method!='editUser' && param.method != 'editUserGroup'}");
/* 3535 */     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3536 */     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */       for (;;) {
/* 3538 */         out.write("\n\t  <input type=\"hidden\" name=\"method\" value=\"createUser\">\n    ");
/* 3539 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3540 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3544 */     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3545 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3546 */       return true;
/*      */     }
/* 3548 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3554 */     PageContext pageContext = _jspx_page_context;
/* 3555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3557 */     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3558 */     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3559 */     _jspx_th_c_005fotherwise_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/* 3560 */     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3561 */     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */       for (;;) {
/* 3563 */         out.write("\n    \t");
/* 3564 */         if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/* 3565 */           return true;
/* 3566 */         out.write(10);
/* 3567 */         out.write(9);
/* 3568 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3569 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3573 */     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3574 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3575 */       return true;
/*      */     }
/* 3577 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3578 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3583 */     PageContext pageContext = _jspx_page_context;
/* 3584 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3586 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3587 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3588 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 3590 */     _jspx_th_c_005fif_005f12.setTest("${!empty updateProfile && updateProfile=='true'}");
/* 3591 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3592 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 3594 */         out.write("\n\t\t\t<input type=\"hidden\" name=\"updateProfile\" value=\"true\">\n\t\t");
/* 3595 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3596 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3600 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3601 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3602 */       return true;
/*      */     }
/* 3604 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3610 */     PageContext pageContext = _jspx_page_context;
/* 3611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3613 */     ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3614 */     _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 3615 */     _jspx_th_c_005fchoose_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 3616 */     int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 3617 */     if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */       for (;;) {
/* 3619 */         out.write("\n\t\t\t");
/* 3620 */         if (_jspx_meth_c_005fwhen_005f9(_jspx_th_c_005fchoose_005f7, _jspx_page_context))
/* 3621 */           return true;
/* 3622 */         out.write("\n\t\t\t");
/* 3623 */         if (_jspx_meth_c_005fotherwise_005f7(_jspx_th_c_005fchoose_005f7, _jspx_page_context))
/* 3624 */           return true;
/* 3625 */         out.write(10);
/* 3626 */         out.write(9);
/* 3627 */         out.write(9);
/* 3628 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 3629 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3633 */     if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 3634 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3635 */       return true;
/*      */     }
/* 3637 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3638 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f9(JspTag _jspx_th_c_005fchoose_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3643 */     PageContext pageContext = _jspx_page_context;
/* 3644 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3646 */     WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3647 */     _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 3648 */     _jspx_th_c_005fwhen_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f7);
/*      */     
/* 3650 */     _jspx_th_c_005fwhen_005f9.setTest("${!empty updateProfile && updateProfile=='true'}");
/* 3651 */     int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 3652 */     if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */       for (;;) {
/* 3654 */         out.write("\n\t\t\t\t<div style=\"line-height:25px\">\n\t\t\t\t\t<img style=\"width:32px;height:32px;vertical-align:middle\" src=\"/images/icon_customfields_add.png\" border=\"0\" align=\"left\"/>\n   \t\t\t\t\t<span>&nbsp;&nbsp;&nbsp;");
/* 3655 */         if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fwhen_005f9, _jspx_page_context))
/* 3656 */           return true;
/* 3657 */         out.write("</span>\n   \t\t\t\t</div>\n\t\t\t");
/* 3658 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 3659 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3663 */     if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 3664 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3665 */       return true;
/*      */     }
/* 3667 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3668 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fwhen_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3673 */     PageContext pageContext = _jspx_page_context;
/* 3674 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3676 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3677 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3678 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f9);
/*      */     
/* 3680 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.user.updateuser.text");
/* 3681 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3682 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3683 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3684 */       return true;
/*      */     }
/* 3686 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3687 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f7(JspTag _jspx_th_c_005fchoose_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3692 */     PageContext pageContext = _jspx_page_context;
/* 3693 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3695 */     OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3696 */     _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 3697 */     _jspx_th_c_005fotherwise_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f7);
/* 3698 */     int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 3699 */     if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */       for (;;) {
/* 3701 */         out.write("\n\t\t\t\t<div id=\"customfieldTabs\">\n\t\t\t\t\t<ul class=\"ulstyleforcf\">\n\t\t\t\t\t\t<li class=\"listyleforcf\" id=\"LocalAuth_tabs\">\n\t\t\t\t\t\t\t<a title=\"");
/* 3702 */         if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/* 3703 */           return true;
/* 3704 */         out.write("\" href=\"javascript:void(0)\" class=\"ulanchor\" onclick=\"toggleCustomFieldDiv1('LocalAuth')\">\n\t\t\t\t\t\t\t\t<img src=\"/images/icon_customfields_add.png\" style=\"position: relative;top: 12px;\" border=\"0\" align=\"left\"/>\n\t\t\t\t\t\t\t\t&nbsp;");
/* 3705 */         if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/* 3706 */           return true;
/* 3707 */         out.write("\t\n\t\t\t\t\t\t\t\t<span class=\"tabarrows\"></span>\n\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t</li>\n\t\t\t\t\t\t<li class=\"listyleforcf\" id=\"ADAuth_tabs\">\n\t\t\t\t\t\t\t<a title=\"");
/* 3708 */         if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/* 3709 */           return true;
/* 3710 */         out.write("\" href=\"javascript:void(0)\" class=\"ulanchor\" onclick=\"toggleCustomFieldDiv1('ADAuth')\">\n\t\t\t\t\t\t\t\t<img src=\"/images/icon_customfields_user.png\" style=\"position: relative;top: 12px;\"\tborder=\"0\" align=\"left\"/>\n\t\t\t\t\t\t\t\t&nbsp;");
/* 3711 */         if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/* 3712 */           return true;
/* 3713 */         out.write("\n\t\t\t\t\t\t\t\t<span class=\"tabarrows\"></span>\n\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t</li>\n\t\t\t\t\t</ul>\n\t\t\t\t</div>\n\t\t\t");
/* 3714 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3715 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3719 */     if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3720 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3721 */       return true;
/*      */     }
/* 3723 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3724 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3729 */     PageContext pageContext = _jspx_page_context;
/* 3730 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3732 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3733 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3734 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/* 3735 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3736 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3737 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3738 */         out = _jspx_page_context.pushBody();
/* 3739 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 3740 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3743 */         out.write("am.webclient.admintab.adduser.localauth.title.text");
/* 3744 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 3745 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3748 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3749 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3752 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3753 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3754 */       return true;
/*      */     }
/* 3756 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3762 */     PageContext pageContext = _jspx_page_context;
/* 3763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3765 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3766 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3767 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/*      */     
/* 3769 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.admintab.adduser.localauth.tab.text");
/* 3770 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3771 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3772 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3773 */       return true;
/*      */     }
/* 3775 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3781 */     PageContext pageContext = _jspx_page_context;
/* 3782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3784 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3785 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3786 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/* 3787 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3788 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 3789 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3790 */         out = _jspx_page_context.pushBody();
/* 3791 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 3792 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3795 */         out.write("am.webclient.admintab.adduser.importad.tab.text");
/* 3796 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 3797 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3800 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3801 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3804 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3805 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3806 */       return true;
/*      */     }
/* 3808 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3809 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3814 */     PageContext pageContext = _jspx_page_context;
/* 3815 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3817 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3818 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3819 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/*      */     
/* 3821 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.admintab.adduser.importad.tab.text");
/* 3822 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3823 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3824 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3825 */       return true;
/*      */     }
/* 3827 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3828 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3833 */     PageContext pageContext = _jspx_page_context;
/* 3834 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3836 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3837 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3838 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 3840 */     _jspx_th_c_005fif_005f14.setTest("${isOpenLdapUser }");
/* 3841 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3842 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 3844 */         out.write("\n\t<td height=\"20\" >");
/* 3845 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fif_005f14, _jspx_page_context))
/* 3846 */           return true;
/* 3847 */         out.write("</td>\n\t");
/* 3848 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3849 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3853 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3854 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3855 */       return true;
/*      */     }
/* 3857 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3858 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3863 */     PageContext pageContext = _jspx_page_context;
/* 3864 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3866 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.get(TextTag.class);
/* 3867 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3868 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 3870 */     _jspx_th_html_005ftext_005f0.setProperty("domainValue");
/*      */     
/* 3872 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default bg-lite");
/*      */     
/* 3874 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/* 3876 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/* 3877 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3878 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3879 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3880 */       return true;
/*      */     }
/* 3882 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3883 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3888 */     PageContext pageContext = _jspx_page_context;
/* 3889 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3891 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3892 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3893 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fif_005f15);
/* 3894 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 3895 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 3896 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3897 */         out = _jspx_page_context.pushBody();
/* 3898 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 3899 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3902 */         out.write("am.webclient.useradministration.usergroup.chooseuser.text");
/* 3903 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 3904 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3907 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3908 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3911 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 3912 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3913 */       return true;
/*      */     }
/* 3915 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3916 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3921 */     PageContext pageContext = _jspx_page_context;
/* 3922 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3924 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3925 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3926 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3928 */     _jspx_th_c_005fset_005f0.setVar("selecteddomainid");
/* 3929 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3930 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3931 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3932 */         out = _jspx_page_context.pushBody();
/* 3933 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3934 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3935 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3938 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3939 */           return true;
/* 3940 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3941 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3944 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3945 */         out = _jspx_page_context.popBody();
/* 3946 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3949 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3950 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3951 */       return true;
/*      */     }
/* 3953 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3954 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3959 */     PageContext pageContext = _jspx_page_context;
/* 3960 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3962 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3963 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3964 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3966 */     _jspx_th_c_005fout_005f3.setValue("${row[\"value\"] }");
/* 3967 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3968 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3969 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3970 */       return true;
/*      */     }
/* 3972 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3973 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3978 */     PageContext pageContext = _jspx_page_context;
/* 3979 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3981 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3982 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3983 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3985 */     _jspx_th_c_005fout_005f4.setValue("${row[\"value\"] }");
/* 3986 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3987 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3988 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3989 */       return true;
/*      */     }
/* 3991 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3997 */     PageContext pageContext = _jspx_page_context;
/* 3998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4000 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4001 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4002 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4004 */     _jspx_th_c_005fout_005f5.setValue("${row[\"label\"] }");
/* 4005 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4006 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4007 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4008 */       return true;
/*      */     }
/* 4010 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4011 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4016 */     PageContext pageContext = _jspx_page_context;
/* 4017 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4019 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.get(TextTag.class);
/* 4020 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4021 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4023 */     _jspx_th_html_005ftext_005f1.setProperty("userName");
/*      */     
/* 4025 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default bg-lite");
/*      */     
/* 4027 */     _jspx_th_html_005ftext_005f1.setSize("30");
/*      */     
/* 4029 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/* 4030 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4031 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4032 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4033 */       return true;
/*      */     }
/* 4035 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4036 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4041 */     PageContext pageContext = _jspx_page_context;
/* 4042 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4044 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4045 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 4046 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4048 */     _jspx_th_c_005fif_005f16.setTest("${(UserConfiguration.userName==\"admin\") || ((param.method == \"editUser\")&& (UserConfiguration.authType == \"ADAuthentication\"))}");
/* 4049 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 4050 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 4052 */         out.write(10);
/* 4053 */         out.write(10);
/* 4054 */         if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 4055 */           return true;
/* 4056 */         out.write(10);
/* 4057 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 4058 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4062 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 4063 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4064 */       return true;
/*      */     }
/* 4066 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4072 */     PageContext pageContext = _jspx_page_context;
/* 4073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4075 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.get(TextTag.class);
/* 4076 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 4077 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 4079 */     _jspx_th_html_005ftext_005f2.setProperty("userName");
/*      */     
/* 4081 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext default bg-lite");
/*      */     
/* 4083 */     _jspx_th_html_005ftext_005f2.setSize("30");
/*      */     
/* 4085 */     _jspx_th_html_005ftext_005f2.setReadonly(true);
/* 4086 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 4087 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 4088 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4089 */       return true;
/*      */     }
/* 4091 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4092 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4097 */     PageContext pageContext = _jspx_page_context;
/* 4098 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4100 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4101 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 4102 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4104 */     _jspx_th_c_005fif_005f17.setTest("${(UserConfiguration.userName!=\"admin\") && (UserConfiguration.authType != \"ADAuthentication\")}");
/* 4105 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 4106 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 4108 */         out.write(10);
/* 4109 */         if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fif_005f17, _jspx_page_context))
/* 4110 */           return true;
/* 4111 */         out.write(10);
/* 4112 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 4113 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4117 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 4118 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4119 */       return true;
/*      */     }
/* 4121 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4127 */     PageContext pageContext = _jspx_page_context;
/* 4128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4130 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4131 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 4132 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 4134 */     _jspx_th_html_005ftext_005f3.setProperty("userName");
/*      */     
/* 4136 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext default");
/*      */     
/* 4138 */     _jspx_th_html_005ftext_005f3.setSize("30");
/* 4139 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 4140 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 4141 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4142 */       return true;
/*      */     }
/* 4144 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4150 */     PageContext pageContext = _jspx_page_context;
/* 4151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4153 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 4154 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 4155 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4157 */     _jspx_th_html_005ftextarea_005f0.setProperty("description");
/*      */     
/* 4159 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea");
/*      */     
/* 4161 */     _jspx_th_html_005ftextarea_005f0.setRows("3");
/*      */     
/* 4163 */     _jspx_th_html_005ftextarea_005f0.setCols("29");
/*      */     
/* 4165 */     _jspx_th_html_005ftextarea_005f0.setStyle("width:250px");
/* 4166 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 4167 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 4168 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4169 */       return true;
/*      */     }
/* 4171 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4172 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4177 */     PageContext pageContext = _jspx_page_context;
/* 4178 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4180 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 4181 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 4182 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 4184 */     _jspx_th_html_005fcheckbox_005f0.setProperty("updateChk");
/*      */     
/* 4186 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("javascript:updatePassword()");
/* 4187 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 4188 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 4189 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4190 */       return true;
/*      */     }
/* 4192 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4193 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4198 */     PageContext pageContext = _jspx_page_context;
/* 4199 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4201 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4202 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 4203 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 4205 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 4207 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext default");
/*      */     
/* 4209 */     _jspx_th_html_005fpassword_005f0.setSize("30");
/* 4210 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 4211 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 4212 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4213 */       return true;
/*      */     }
/* 4215 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4216 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f1(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4221 */     PageContext pageContext = _jspx_page_context;
/* 4222 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4224 */     PasswordTag _jspx_th_html_005fpassword_005f1 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4225 */     _jspx_th_html_005fpassword_005f1.setPageContext(_jspx_page_context);
/* 4226 */     _jspx_th_html_005fpassword_005f1.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 4228 */     _jspx_th_html_005fpassword_005f1.setProperty("password");
/*      */     
/* 4230 */     _jspx_th_html_005fpassword_005f1.setStyleClass("formtext default");
/*      */     
/* 4232 */     _jspx_th_html_005fpassword_005f1.setSize("30");
/* 4233 */     int _jspx_eval_html_005fpassword_005f1 = _jspx_th_html_005fpassword_005f1.doStartTag();
/* 4234 */     if (_jspx_th_html_005fpassword_005f1.doEndTag() == 5) {
/* 4235 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 4236 */       return true;
/*      */     }
/* 4238 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/* 4239 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4244 */     PageContext pageContext = _jspx_page_context;
/* 4245 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4247 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4248 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 4249 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4251 */     _jspx_th_c_005fif_005f20.setTest("${!empty updateProfile && updateProfile=='true'}");
/* 4252 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 4253 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 4255 */         out.write("<span class=\"mandatory\">*</span>");
/* 4256 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 4257 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4261 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 4262 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4263 */       return true;
/*      */     }
/* 4265 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4271 */     PageContext pageContext = _jspx_page_context;
/* 4272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4274 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4275 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 4276 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4278 */     _jspx_th_html_005ftext_005f4.setProperty("email");
/*      */     
/* 4280 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext default");
/*      */     
/* 4282 */     _jspx_th_html_005ftext_005f4.setSize("30");
/* 4283 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 4284 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 4285 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4286 */       return true;
/*      */     }
/* 4288 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4294 */     PageContext pageContext = _jspx_page_context;
/* 4295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4297 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4298 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 4299 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4301 */     _jspx_th_html_005fselect_005f1.setProperty("groups");
/*      */     
/* 4303 */     _jspx_th_html_005fselect_005f1.setOnchange("javascript:showMonitorGroups();");
/*      */     
/* 4305 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext medium");
/* 4306 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 4307 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 4308 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4309 */         out = _jspx_page_context.pushBody();
/* 4310 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 4311 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4314 */         out.write(10);
/* 4315 */         out.write(9);
/* 4316 */         out.write(9);
/* 4317 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 4318 */           return true;
/* 4319 */         out.write(10);
/* 4320 */         out.write(9);
/* 4321 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 4322 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4325 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4326 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4329 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 4330 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 4331 */       return true;
/*      */     }
/* 4333 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 4334 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4339 */     PageContext pageContext = _jspx_page_context;
/* 4340 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4342 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4343 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 4344 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 4346 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("availableGroups");
/* 4347 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 4348 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 4349 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4350 */       return true;
/*      */     }
/* 4352 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4353 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4358 */     PageContext pageContext = _jspx_page_context;
/* 4359 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4361 */     ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4362 */     _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 4363 */     _jspx_th_c_005fchoose_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 4364 */     int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 4365 */     if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */       for (;;) {
/* 4367 */         out.write("\n\t\t\t\t\t\t ");
/* 4368 */         if (_jspx_meth_c_005fwhen_005f10(_jspx_th_c_005fchoose_005f8, _jspx_page_context))
/* 4369 */           return true;
/* 4370 */         out.write("\n\t\t\t\t\t\t ");
/* 4371 */         if (_jspx_meth_c_005fotherwise_005f8(_jspx_th_c_005fchoose_005f8, _jspx_page_context))
/* 4372 */           return true;
/* 4373 */         out.write("\n\t\t\t\t\t ");
/* 4374 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 4375 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4379 */     if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 4380 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 4381 */       return true;
/*      */     }
/* 4383 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 4384 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f10(JspTag _jspx_th_c_005fchoose_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4389 */     PageContext pageContext = _jspx_page_context;
/* 4390 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4392 */     WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4393 */     _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 4394 */     _jspx_th_c_005fwhen_005f10.setParent((Tag)_jspx_th_c_005fchoose_005f8);
/*      */     
/* 4396 */     _jspx_th_c_005fwhen_005f10.setTest("${(UserConfiguration.userName==\"admin\") && (param.method == \"editUser\")}");
/* 4397 */     int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 4398 */     if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */       for (;;) {
/* 4400 */         out.write("\n\t\t\t\t\t\t ");
/* 4401 */         if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_c_005fwhen_005f10, _jspx_page_context))
/* 4402 */           return true;
/* 4403 */         out.write("\n\t\t\t\t\t\t ");
/* 4404 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 4405 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4409 */     if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 4410 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 4411 */       return true;
/*      */     }
/* 4413 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 4414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4419 */     PageContext pageContext = _jspx_page_context;
/* 4420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4422 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 4423 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 4424 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/*      */     
/* 4426 */     _jspx_th_html_005fcheckbox_005f1.setOnclick("return false");
/*      */     
/* 4428 */     _jspx_th_html_005fcheckbox_005f1.setProperty("delegatedAdmin");
/* 4429 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 4430 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 4431 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4432 */       return true;
/*      */     }
/* 4434 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f8(JspTag _jspx_th_c_005fchoose_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4440 */     PageContext pageContext = _jspx_page_context;
/* 4441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4443 */     OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4444 */     _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 4445 */     _jspx_th_c_005fotherwise_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f8);
/* 4446 */     int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 4447 */     if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */       for (;;) {
/* 4449 */         out.write("\n\t\t\t\t\t\t ");
/* 4450 */         if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_c_005fotherwise_005f8, _jspx_page_context))
/* 4451 */           return true;
/* 4452 */         out.write("\n\t\t\t\t\t\t ");
/* 4453 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 4454 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4458 */     if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 4459 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 4460 */       return true;
/*      */     }
/* 4462 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 4463 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_c_005fotherwise_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4468 */     PageContext pageContext = _jspx_page_context;
/* 4469 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4471 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 4472 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 4473 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f8);
/*      */     
/* 4475 */     _jspx_th_html_005fcheckbox_005f2.setProperty("delegatedAdmin");
/*      */     
/* 4477 */     _jspx_th_html_005fcheckbox_005f2.setOnclick("javascript:isDelegatedAdmin()");
/* 4478 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 4479 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 4480 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 4481 */       return true;
/*      */     }
/* 4483 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 4484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ffile_005f0(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4489 */     PageContext pageContext = _jspx_page_context;
/* 4490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4492 */     FileTag _jspx_th_html_005ffile_005f0 = (FileTag)this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.get(FileTag.class);
/* 4493 */     _jspx_th_html_005ffile_005f0.setPageContext(_jspx_page_context);
/* 4494 */     _jspx_th_html_005ffile_005f0.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 4496 */     _jspx_th_html_005ffile_005f0.setSize("42");
/*      */     
/* 4498 */     _jspx_th_html_005ffile_005f0.setProperty("theFile");
/* 4499 */     int _jspx_eval_html_005ffile_005f0 = _jspx_th_html_005ffile_005f0.doStartTag();
/* 4500 */     if (_jspx_th_html_005ffile_005f0.doEndTag() == 5) {
/* 4501 */       this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 4502 */       return true;
/*      */     }
/* 4504 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 4505 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_c_005fif_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4510 */     PageContext pageContext = _jspx_page_context;
/* 4511 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4513 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 4514 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 4515 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_c_005fif_005f22);
/*      */     
/* 4517 */     _jspx_th_html_005fhidden_005f3.setProperty("change");
/*      */     
/* 4519 */     _jspx_th_html_005fhidden_005f3.setValue("false");
/* 4520 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 4521 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 4522 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 4523 */       return true;
/*      */     }
/* 4525 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 4526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_c_005fif_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4531 */     PageContext pageContext = _jspx_page_context;
/* 4532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4534 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 4535 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/* 4536 */     _jspx_th_html_005fhidden_005f4.setParent((Tag)_jspx_th_c_005fif_005f22);
/*      */     
/* 4538 */     _jspx_th_html_005fhidden_005f4.setProperty("deletePic");
/*      */     
/* 4540 */     _jspx_th_html_005fhidden_005f4.setValue("false");
/* 4541 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/* 4542 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/* 4543 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 4544 */       return true;
/*      */     }
/* 4546 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 4547 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4552 */     PageContext pageContext = _jspx_page_context;
/* 4553 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4555 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4556 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 4557 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 4558 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 4559 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 4560 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 4561 */         out = _jspx_page_context.pushBody();
/* 4562 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 4563 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4566 */         out.write("am.webclient.useradministration.usergroup.chooseusergroup.text");
/* 4567 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 4568 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4571 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 4572 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4575 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 4576 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4577 */       return true;
/*      */     }
/* 4579 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4585 */     PageContext pageContext = _jspx_page_context;
/* 4586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4588 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4589 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 4590 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 4591 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 4592 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 4593 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 4594 */         out = _jspx_page_context.pushBody();
/* 4595 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 4596 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4599 */         out.write("am.webclient.useradministration.usergroup.local.text");
/* 4600 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 4601 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4604 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 4605 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4608 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 4609 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 4610 */       return true;
/*      */     }
/* 4612 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 4613 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4618 */     PageContext pageContext = _jspx_page_context;
/* 4619 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4621 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4622 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4623 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4625 */     _jspx_th_c_005fset_005f1.setVar("selectedgroupid");
/* 4626 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4627 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4628 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4629 */         out = _jspx_page_context.pushBody();
/* 4630 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4631 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 4632 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4635 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4636 */           return true;
/* 4637 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4638 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4641 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4642 */         out = _jspx_page_context.popBody();
/* 4643 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 4646 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4647 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4648 */       return true;
/*      */     }
/* 4650 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4651 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4656 */     PageContext pageContext = _jspx_page_context;
/* 4657 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4659 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4660 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4661 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 4663 */     _jspx_th_c_005fout_005f6.setValue("${row[\"groupid\"] }");
/* 4664 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4665 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4666 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4667 */       return true;
/*      */     }
/* 4669 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4670 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4675 */     PageContext pageContext = _jspx_page_context;
/* 4676 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4678 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4679 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4680 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4682 */     _jspx_th_c_005fout_005f7.setValue("${row[\"groupid\"] }");
/* 4683 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4684 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4685 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4686 */       return true;
/*      */     }
/* 4688 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4694 */     PageContext pageContext = _jspx_page_context;
/* 4695 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4697 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4698 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4699 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4701 */     _jspx_th_c_005fout_005f8.setValue("${row[\"groupname\"] }");
/* 4702 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4703 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4704 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4705 */       return true;
/*      */     }
/* 4707 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4713 */     PageContext pageContext = _jspx_page_context;
/* 4714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4716 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4717 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 4718 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 4719 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 4720 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 4721 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 4722 */         out = _jspx_page_context.pushBody();
/* 4723 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 4724 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4727 */         out.write("am.webclient.useradministration.usergroup.domain.text");
/* 4728 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 4729 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4732 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 4733 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4736 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 4737 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 4738 */       return true;
/*      */     }
/* 4740 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 4741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 4746 */     PageContext pageContext = _jspx_page_context;
/* 4747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4749 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4750 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 4751 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 4753 */     _jspx_th_c_005fset_005f2.setVar("selectedgroupid");
/* 4754 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 4755 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 4756 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4757 */         out = _jspx_page_context.pushBody();
/* 4758 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 4759 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 4760 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4763 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fset_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 4764 */           return true;
/* 4765 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 4766 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4769 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4770 */         out = _jspx_page_context.popBody();
/* 4771 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 4774 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 4775 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 4776 */       return true;
/*      */     }
/* 4778 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 4779 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 4784 */     PageContext pageContext = _jspx_page_context;
/* 4785 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4787 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4788 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4789 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 4791 */     _jspx_th_c_005fout_005f9.setValue("${row[\"groupid\"] }");
/* 4792 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4793 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4794 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4795 */       return true;
/*      */     }
/* 4797 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4798 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 4803 */     PageContext pageContext = _jspx_page_context;
/* 4804 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4806 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4807 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4808 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 4810 */     _jspx_th_c_005fout_005f10.setValue("${row[\"groupid\"] }");
/* 4811 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4812 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4813 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4814 */       return true;
/*      */     }
/* 4816 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4817 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 4822 */     PageContext pageContext = _jspx_page_context;
/* 4823 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4825 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4826 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4827 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 4829 */     _jspx_th_c_005fout_005f11.setValue("${row[\"groupname\"] }");
/* 4830 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4831 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4832 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4833 */       return true;
/*      */     }
/* 4835 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f23(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4841 */     PageContext pageContext = _jspx_page_context;
/* 4842 */     JspWriter out = _jspx_page_context.getOut();
/* 4843 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 4844 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 4846 */     IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4847 */     _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 4848 */     _jspx_th_c_005fif_005f23.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4850 */     _jspx_th_c_005fif_005f23.setTest("${!param.usergroup}");
/* 4851 */     int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 4852 */     if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */       for (;;) {
/* 4854 */         out.write("\n\t\t<div id=\"allgroups\">\n\t\t\t");
/* 4855 */         JspRuntimeLibrary.include(request, response, "/jsp/AssociateMonitorGroups.jsp" + ("/jsp/AssociateMonitorGroups.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("selectionType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("user", request.getCharacterEncoding()), out, false);
/* 4856 */         out.write(9);
/* 4857 */         out.write(9);
/* 4858 */         out.write("\n\t\t\t\t\n\t\t</div>\n\t\t<div id=\"allusergroups\" style=\"display:none;\">\n\t\t\t\n\t\t</div>\n\t");
/* 4859 */         int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 4860 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4864 */     if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 4865 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 4866 */       return true;
/*      */     }
/* 4868 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 4869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f28(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4874 */     PageContext pageContext = _jspx_page_context;
/* 4875 */     JspWriter out = _jspx_page_context.getOut();
/* 4876 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 4877 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 4879 */     IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4880 */     _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 4881 */     _jspx_th_c_005fif_005f28.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4883 */     _jspx_th_c_005fif_005f28.setTest("${!param.usergroup }");
/* 4884 */     int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 4885 */     if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */       for (;;) {
/* 4887 */         out.write("\n\t\t<div id=\"eachTableDiv_ADAuth\" style=\"display:none;\">\t\n\t\t\t");
/* 4888 */         JspRuntimeLibrary.include(request, response, "/jsp/LdapAuthentication.jsp" + ("/jsp/LdapAuthentication.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("submitValue", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("users", request.getCharacterEncoding()), out, false);
/* 4889 */         out.write(9);
/* 4890 */         out.write(9);
/* 4891 */         out.write("\n\t\t</div>\n\t");
/* 4892 */         int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 4893 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4897 */     if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 4898 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 4899 */       return true;
/*      */     }
/* 4901 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 4902 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4907 */     PageContext pageContext = _jspx_page_context;
/* 4908 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4910 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4911 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 4912 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 4913 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 4914 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 4915 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 4916 */         out = _jspx_page_context.pushBody();
/* 4917 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 4918 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4921 */         out.write("am.webclient.useradministration.usergroup.add.text");
/* 4922 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 4923 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4926 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 4927 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4930 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 4931 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 4932 */       return true;
/*      */     }
/* 4934 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 4935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4940 */     PageContext pageContext = _jspx_page_context;
/* 4941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4943 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4944 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 4945 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 4946 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 4947 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 4948 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 4949 */         out = _jspx_page_context.pushBody();
/* 4950 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 4951 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4954 */         out.write("am.webclient.useradministration.usergroup.add.text");
/* 4955 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 4956 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4959 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 4960 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4963 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 4964 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 4965 */       return true;
/*      */     }
/* 4967 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 4968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4973 */     PageContext pageContext = _jspx_page_context;
/* 4974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4976 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4977 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 4978 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 4979 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 4980 */     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 4981 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 4982 */         out = _jspx_page_context.pushBody();
/* 4983 */         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 4984 */         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4987 */         out.write("am.webclient.useradministration.usergroup.ad.import.text");
/* 4988 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 4989 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4992 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 4993 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4996 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 4997 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 4998 */       return true;
/*      */     }
/* 5000 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 5001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5006 */     PageContext pageContext = _jspx_page_context;
/* 5007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5009 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5010 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 5011 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 5012 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 5013 */     if (_jspx_eval_fmt_005fmessage_005f17 != 0) {
/* 5014 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 5015 */         out = _jspx_page_context.pushBody();
/* 5016 */         _jspx_th_fmt_005fmessage_005f17.setBodyContent((BodyContent)out);
/* 5017 */         _jspx_th_fmt_005fmessage_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5020 */         out.write("am.webclient.useradministration.usergroup.ad.import.text");
/* 5021 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f17.doAfterBody();
/* 5022 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5025 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 5026 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5029 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 5030 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 5031 */       return true;
/*      */     }
/* 5033 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 5034 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5039 */     PageContext pageContext = _jspx_page_context;
/* 5040 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5042 */     MessageTag _jspx_th_fmt_005fmessage_005f18 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5043 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 5044 */     _jspx_th_fmt_005fmessage_005f18.setParent((Tag)_jspx_th_c_005fif_005f30);
/* 5045 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 5046 */     if (_jspx_eval_fmt_005fmessage_005f18 != 0) {
/* 5047 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 5048 */         out = _jspx_page_context.pushBody();
/* 5049 */         _jspx_th_fmt_005fmessage_005f18.setBodyContent((BodyContent)out);
/* 5050 */         _jspx_th_fmt_005fmessage_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5053 */         out.write("am.webclient.useradministration.usergroup.chooseuser.text");
/* 5054 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f18.doAfterBody();
/* 5055 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5058 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 5059 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5062 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 5063 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 5064 */       return true;
/*      */     }
/* 5066 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 5067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 5072 */     PageContext pageContext = _jspx_page_context;
/* 5073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5075 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5076 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5077 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 5079 */     _jspx_th_c_005fset_005f3.setVar("selecteddomainid");
/* 5080 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5081 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 5082 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5083 */         out = _jspx_page_context.pushBody();
/* 5084 */         _jspx_push_body_count_c_005fforEach_005f3[0] += 1;
/* 5085 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 5086 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5089 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fset_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 5090 */           return true;
/* 5091 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 5092 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5095 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 5096 */         out = _jspx_page_context.popBody();
/* 5097 */         _jspx_push_body_count_c_005fforEach_005f3[0] -= 1;
/*      */       }
/*      */     }
/* 5100 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5101 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 5102 */       return true;
/*      */     }
/* 5104 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 5105 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 5110 */     PageContext pageContext = _jspx_page_context;
/* 5111 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5113 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5114 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5115 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 5117 */     _jspx_th_c_005fout_005f12.setValue("${row[\"value\"] }");
/* 5118 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5119 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5120 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5121 */       return true;
/*      */     }
/* 5123 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5124 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 5129 */     PageContext pageContext = _jspx_page_context;
/* 5130 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5132 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5133 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5134 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 5136 */     _jspx_th_c_005fout_005f13.setValue("${row[\"value\"] }");
/* 5137 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5138 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5139 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5140 */       return true;
/*      */     }
/* 5142 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 5148 */     PageContext pageContext = _jspx_page_context;
/* 5149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5151 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5152 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5153 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 5155 */     _jspx_th_c_005fout_005f14.setValue("${row[\"label\"] }");
/* 5156 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5157 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5158 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5159 */       return true;
/*      */     }
/* 5161 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f31(JspTag _jspx_th_c_005fif_005f29, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5167 */     PageContext pageContext = _jspx_page_context;
/* 5168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5170 */     IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5171 */     _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 5172 */     _jspx_th_c_005fif_005f31.setParent((Tag)_jspx_th_c_005fif_005f29);
/*      */     
/* 5174 */     _jspx_th_c_005fif_005f31.setTest("${isOpenLdapGroup}");
/* 5175 */     int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 5176 */     if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */       for (;;) {
/* 5178 */         out.write("\n\t \t");
/* 5179 */         if (_jspx_meth_html_005ftext_005f5(_jspx_th_c_005fif_005f31, _jspx_page_context))
/* 5180 */           return true;
/* 5181 */         out.write(10);
/* 5182 */         out.write(9);
/* 5183 */         out.write(32);
/* 5184 */         int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 5185 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5189 */     if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 5190 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 5191 */       return true;
/*      */     }
/* 5193 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 5194 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5199 */     PageContext pageContext = _jspx_page_context;
/* 5200 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5202 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.get(TextTag.class);
/* 5203 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 5204 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 5206 */     _jspx_th_html_005ftext_005f5.setProperty("ldapdomainValue");
/*      */     
/* 5208 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext default bg-lite");
/*      */     
/* 5210 */     _jspx_th_html_005ftext_005f5.setSize("40");
/*      */     
/* 5212 */     _jspx_th_html_005ftext_005f5.setReadonly(true);
/* 5213 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 5214 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 5215 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 5216 */       return true;
/*      */     }
/* 5218 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 5219 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5224 */     PageContext pageContext = _jspx_page_context;
/* 5225 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5227 */     ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5228 */     _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 5229 */     _jspx_th_c_005fchoose_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 5230 */     int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 5231 */     if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */       for (;;) {
/* 5233 */         out.write(10);
/* 5234 */         out.write(9);
/* 5235 */         if (_jspx_meth_c_005fwhen_005f12(_jspx_th_c_005fchoose_005f10, _jspx_page_context))
/* 5236 */           return true;
/* 5237 */         out.write(10);
/* 5238 */         out.write(9);
/* 5239 */         if (_jspx_meth_c_005fotherwise_005f10(_jspx_th_c_005fchoose_005f10, _jspx_page_context))
/* 5240 */           return true;
/* 5241 */         out.write(10);
/* 5242 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 5243 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5247 */     if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 5248 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 5249 */       return true;
/*      */     }
/* 5251 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 5252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f12(JspTag _jspx_th_c_005fchoose_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5257 */     PageContext pageContext = _jspx_page_context;
/* 5258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5260 */     WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5261 */     _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 5262 */     _jspx_th_c_005fwhen_005f12.setParent((Tag)_jspx_th_c_005fchoose_005f10);
/*      */     
/* 5264 */     _jspx_th_c_005fwhen_005f12.setTest("${param.method=='editUserGroup' && isDomainGroup}");
/* 5265 */     int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 5266 */     if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */       for (;;) {
/* 5268 */         out.write(10);
/* 5269 */         out.write(9);
/* 5270 */         out.write(9);
/* 5271 */         if (_jspx_meth_html_005ftext_005f6(_jspx_th_c_005fwhen_005f12, _jspx_page_context))
/* 5272 */           return true;
/* 5273 */         out.write(10);
/* 5274 */         out.write(9);
/* 5275 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 5276 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5280 */     if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 5281 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 5282 */       return true;
/*      */     }
/* 5284 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 5285 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_c_005fwhen_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5290 */     PageContext pageContext = _jspx_page_context;
/* 5291 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5293 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.get(TextTag.class);
/* 5294 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 5295 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f12);
/*      */     
/* 5297 */     _jspx_th_html_005ftext_005f6.setProperty("usergroupName");
/*      */     
/* 5299 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext default bg-lite");
/*      */     
/* 5301 */     _jspx_th_html_005ftext_005f6.setSize("40");
/*      */     
/* 5303 */     _jspx_th_html_005ftext_005f6.setReadonly(true);
/* 5304 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 5305 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 5306 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 5307 */       return true;
/*      */     }
/* 5309 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 5310 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f10(JspTag _jspx_th_c_005fchoose_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5315 */     PageContext pageContext = _jspx_page_context;
/* 5316 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5318 */     OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5319 */     _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 5320 */     _jspx_th_c_005fotherwise_005f10.setParent((Tag)_jspx_th_c_005fchoose_005f10);
/* 5321 */     int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 5322 */     if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */       for (;;) {
/* 5324 */         out.write(10);
/* 5325 */         out.write(9);
/* 5326 */         out.write(9);
/* 5327 */         if (_jspx_meth_html_005ftext_005f7(_jspx_th_c_005fotherwise_005f10, _jspx_page_context))
/* 5328 */           return true;
/* 5329 */         out.write("<span id=\"usergroupname_alert\" class=\"mandatory\"></span>\n\t");
/* 5330 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 5331 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5335 */     if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 5336 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 5337 */       return true;
/*      */     }
/* 5339 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 5340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_c_005fotherwise_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5345 */     PageContext pageContext = _jspx_page_context;
/* 5346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5348 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5349 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 5350 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f10);
/*      */     
/* 5352 */     _jspx_th_html_005ftext_005f7.setProperty("usergroupName");
/*      */     
/* 5354 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext");
/*      */     
/* 5356 */     _jspx_th_html_005ftext_005f7.setSize("40");
/* 5357 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 5358 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 5359 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 5360 */       return true;
/*      */     }
/* 5362 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 5363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5368 */     PageContext pageContext = _jspx_page_context;
/* 5369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5371 */     MessageTag _jspx_th_fmt_005fmessage_005f19 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5372 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 5373 */     _jspx_th_fmt_005fmessage_005f19.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 5374 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 5375 */     if (_jspx_eval_fmt_005fmessage_005f19 != 0) {
/* 5376 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 5377 */         out = _jspx_page_context.pushBody();
/* 5378 */         _jspx_th_fmt_005fmessage_005f19.setBodyContent((BodyContent)out);
/* 5379 */         _jspx_th_fmt_005fmessage_005f19.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5382 */         out.write("am.webclient.useradministration.usergroup.chooseuser.text");
/* 5383 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f19.doAfterBody();
/* 5384 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5387 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 5388 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5391 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 5392 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 5393 */       return true;
/*      */     }
/* 5395 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 5396 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f20(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5401 */     PageContext pageContext = _jspx_page_context;
/* 5402 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5404 */     MessageTag _jspx_th_fmt_005fmessage_005f20 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5405 */     _jspx_th_fmt_005fmessage_005f20.setPageContext(_jspx_page_context);
/* 5406 */     _jspx_th_fmt_005fmessage_005f20.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 5407 */     int _jspx_eval_fmt_005fmessage_005f20 = _jspx_th_fmt_005fmessage_005f20.doStartTag();
/* 5408 */     if (_jspx_eval_fmt_005fmessage_005f20 != 0) {
/* 5409 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 5410 */         out = _jspx_page_context.pushBody();
/* 5411 */         _jspx_th_fmt_005fmessage_005f20.setBodyContent((BodyContent)out);
/* 5412 */         _jspx_th_fmt_005fmessage_005f20.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5415 */         out.write("am.webclient.role.operator.text");
/* 5416 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f20.doAfterBody();
/* 5417 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5420 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 5421 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5424 */     if (_jspx_th_fmt_005fmessage_005f20.doEndTag() == 5) {
/* 5425 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 5426 */       return true;
/*      */     }
/* 5428 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 5429 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 5434 */     PageContext pageContext = _jspx_page_context;
/* 5435 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5437 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5438 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 5439 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 5441 */     _jspx_th_c_005fset_005f4.setVar("selecteduserid");
/* 5442 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 5443 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 5444 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5445 */         out = _jspx_page_context.pushBody();
/* 5446 */         _jspx_push_body_count_c_005fforEach_005f4[0] += 1;
/* 5447 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 5448 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5451 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fset_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 5452 */           return true;
/* 5453 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 5454 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5457 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 5458 */         out = _jspx_page_context.popBody();
/* 5459 */         _jspx_push_body_count_c_005fforEach_005f4[0] -= 1;
/*      */       }
/*      */     }
/* 5462 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 5463 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 5464 */       return true;
/*      */     }
/* 5466 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 5467 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 5472 */     PageContext pageContext = _jspx_page_context;
/* 5473 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5475 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5476 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5477 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 5479 */     _jspx_th_c_005fout_005f15.setValue("${row[\"userId\"] }");
/* 5480 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5481 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5482 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5483 */       return true;
/*      */     }
/* 5485 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5486 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fif_005f32, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 5491 */     PageContext pageContext = _jspx_page_context;
/* 5492 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5494 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5495 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5496 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fif_005f32);
/*      */     
/* 5498 */     _jspx_th_c_005fout_005f16.setValue("${row[\"userId\"] }");
/* 5499 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5500 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5501 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5502 */       return true;
/*      */     }
/* 5504 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5505 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fif_005f32, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 5510 */     PageContext pageContext = _jspx_page_context;
/* 5511 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5513 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5514 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5515 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fif_005f32);
/*      */     
/* 5517 */     _jspx_th_c_005fout_005f17.setValue("${row[\"userName\"] }");
/* 5518 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5519 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5520 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5521 */       return true;
/*      */     }
/* 5523 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5524 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f21(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5529 */     PageContext pageContext = _jspx_page_context;
/* 5530 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5532 */     MessageTag _jspx_th_fmt_005fmessage_005f21 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5533 */     _jspx_th_fmt_005fmessage_005f21.setPageContext(_jspx_page_context);
/* 5534 */     _jspx_th_fmt_005fmessage_005f21.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 5535 */     int _jspx_eval_fmt_005fmessage_005f21 = _jspx_th_fmt_005fmessage_005f21.doStartTag();
/* 5536 */     if (_jspx_eval_fmt_005fmessage_005f21 != 0) {
/* 5537 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 5538 */         out = _jspx_page_context.pushBody();
/* 5539 */         _jspx_th_fmt_005fmessage_005f21.setBodyContent((BodyContent)out);
/* 5540 */         _jspx_th_fmt_005fmessage_005f21.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5543 */         out.write("am.webclient.role.admin.text");
/* 5544 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f21.doAfterBody();
/* 5545 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5548 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 5549 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5552 */     if (_jspx_th_fmt_005fmessage_005f21.doEndTag() == 5) {
/* 5553 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 5554 */       return true;
/*      */     }
/* 5556 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 5557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fforEach_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5) throws Throwable
/*      */   {
/* 5562 */     PageContext pageContext = _jspx_page_context;
/* 5563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5565 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5566 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 5567 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f5);
/*      */     
/* 5569 */     _jspx_th_c_005fset_005f5.setVar("selecteduserid");
/* 5570 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 5571 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 5572 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 5573 */         out = _jspx_page_context.pushBody();
/* 5574 */         _jspx_push_body_count_c_005fforEach_005f5[0] += 1;
/* 5575 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 5576 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5579 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fset_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
/* 5580 */           return true;
/* 5581 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 5582 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5585 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 5586 */         out = _jspx_page_context.popBody();
/* 5587 */         _jspx_push_body_count_c_005fforEach_005f5[0] -= 1;
/*      */       }
/*      */     }
/* 5590 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 5591 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 5592 */       return true;
/*      */     }
/* 5594 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 5595 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5) throws Throwable
/*      */   {
/* 5600 */     PageContext pageContext = _jspx_page_context;
/* 5601 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5603 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5604 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5605 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 5607 */     _jspx_th_c_005fout_005f18.setValue("${row[\"userId\"] }");
/* 5608 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5609 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5610 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5611 */       return true;
/*      */     }
/* 5613 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5614 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fif_005f33, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5) throws Throwable
/*      */   {
/* 5619 */     PageContext pageContext = _jspx_page_context;
/* 5620 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5622 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5623 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5624 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fif_005f33);
/*      */     
/* 5626 */     _jspx_th_c_005fout_005f19.setValue("${row[\"userId\"] }");
/* 5627 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5628 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5629 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5630 */       return true;
/*      */     }
/* 5632 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5633 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fif_005f33, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5) throws Throwable
/*      */   {
/* 5638 */     PageContext pageContext = _jspx_page_context;
/* 5639 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5641 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5642 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5643 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fif_005f33);
/*      */     
/* 5645 */     _jspx_th_c_005fout_005f20.setValue("${row[\"userName\"] }");
/* 5646 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5647 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5648 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5649 */       return true;
/*      */     }
/* 5651 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5652 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f22(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5657 */     PageContext pageContext = _jspx_page_context;
/* 5658 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5660 */     MessageTag _jspx_th_fmt_005fmessage_005f22 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5661 */     _jspx_th_fmt_005fmessage_005f22.setPageContext(_jspx_page_context);
/* 5662 */     _jspx_th_fmt_005fmessage_005f22.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 5663 */     int _jspx_eval_fmt_005fmessage_005f22 = _jspx_th_fmt_005fmessage_005f22.doStartTag();
/* 5664 */     if (_jspx_eval_fmt_005fmessage_005f22 != 0) {
/* 5665 */       if (_jspx_eval_fmt_005fmessage_005f22 != 1) {
/* 5666 */         out = _jspx_page_context.pushBody();
/* 5667 */         _jspx_th_fmt_005fmessage_005f22.setBodyContent((BodyContent)out);
/* 5668 */         _jspx_th_fmt_005fmessage_005f22.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5671 */         out.write("am.webclient.role.manager.text");
/* 5672 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f22.doAfterBody();
/* 5673 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5676 */       if (_jspx_eval_fmt_005fmessage_005f22 != 1) {
/* 5677 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5680 */     if (_jspx_th_fmt_005fmessage_005f22.doEndTag() == 5) {
/* 5681 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 5682 */       return true;
/*      */     }
/* 5684 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 5685 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fforEach_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 5690 */     PageContext pageContext = _jspx_page_context;
/* 5691 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5693 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5694 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 5695 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f6);
/*      */     
/* 5697 */     _jspx_th_c_005fset_005f6.setVar("selecteduserid");
/* 5698 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 5699 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 5700 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5701 */         out = _jspx_page_context.pushBody();
/* 5702 */         _jspx_push_body_count_c_005fforEach_005f6[0] += 1;
/* 5703 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 5704 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5707 */         if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fset_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 5708 */           return true;
/* 5709 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 5710 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5713 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 5714 */         out = _jspx_page_context.popBody();
/* 5715 */         _jspx_push_body_count_c_005fforEach_005f6[0] -= 1;
/*      */       }
/*      */     }
/* 5718 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 5719 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 5720 */       return true;
/*      */     }
/* 5722 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 5723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 5728 */     PageContext pageContext = _jspx_page_context;
/* 5729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5731 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5732 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5733 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 5735 */     _jspx_th_c_005fout_005f21.setValue("${row[\"userId\"] }");
/* 5736 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5737 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5738 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5739 */       return true;
/*      */     }
/* 5741 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5742 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f34, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 5747 */     PageContext pageContext = _jspx_page_context;
/* 5748 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5750 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5751 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5752 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f34);
/*      */     
/* 5754 */     _jspx_th_c_005fout_005f22.setValue("${row[\"userId\"] }");
/* 5755 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5756 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5757 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5758 */       return true;
/*      */     }
/* 5760 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5761 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f34, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 5766 */     PageContext pageContext = _jspx_page_context;
/* 5767 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5769 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5770 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5771 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f34);
/*      */     
/* 5773 */     _jspx_th_c_005fout_005f23.setValue("${row[\"userName\"] }");
/* 5774 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5775 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5776 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5777 */       return true;
/*      */     }
/* 5779 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f23(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5785 */     PageContext pageContext = _jspx_page_context;
/* 5786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5788 */     MessageTag _jspx_th_fmt_005fmessage_005f23 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5789 */     _jspx_th_fmt_005fmessage_005f23.setPageContext(_jspx_page_context);
/* 5790 */     _jspx_th_fmt_005fmessage_005f23.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 5791 */     int _jspx_eval_fmt_005fmessage_005f23 = _jspx_th_fmt_005fmessage_005f23.doStartTag();
/* 5792 */     if (_jspx_eval_fmt_005fmessage_005f23 != 0) {
/* 5793 */       if (_jspx_eval_fmt_005fmessage_005f23 != 1) {
/* 5794 */         out = _jspx_page_context.pushBody();
/* 5795 */         _jspx_th_fmt_005fmessage_005f23.setBodyContent((BodyContent)out);
/* 5796 */         _jspx_th_fmt_005fmessage_005f23.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5799 */         out.write("am.webclient.role.user.text");
/* 5800 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f23.doAfterBody();
/* 5801 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5804 */       if (_jspx_eval_fmt_005fmessage_005f23 != 1) {
/* 5805 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5808 */     if (_jspx_th_fmt_005fmessage_005f23.doEndTag() == 5) {
/* 5809 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 5810 */       return true;
/*      */     }
/* 5812 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 5813 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fforEach_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 5818 */     PageContext pageContext = _jspx_page_context;
/* 5819 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5821 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5822 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 5823 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f7);
/*      */     
/* 5825 */     _jspx_th_c_005fset_005f7.setVar("selecteduserid");
/* 5826 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 5827 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 5828 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 5829 */         out = _jspx_page_context.pushBody();
/* 5830 */         _jspx_push_body_count_c_005fforEach_005f7[0] += 1;
/* 5831 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 5832 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5835 */         if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fset_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/* 5836 */           return true;
/* 5837 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 5838 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5841 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 5842 */         out = _jspx_page_context.popBody();
/* 5843 */         _jspx_push_body_count_c_005fforEach_005f7[0] -= 1;
/*      */       }
/*      */     }
/* 5846 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 5847 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 5848 */       return true;
/*      */     }
/* 5850 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 5851 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 5856 */     PageContext pageContext = _jspx_page_context;
/* 5857 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5859 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5860 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5861 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 5863 */     _jspx_th_c_005fout_005f24.setValue("${row[\"userId\"] }");
/* 5864 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5865 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5866 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5867 */       return true;
/*      */     }
/* 5869 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5870 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f35, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 5875 */     PageContext pageContext = _jspx_page_context;
/* 5876 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5878 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5879 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5880 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f35);
/*      */     
/* 5882 */     _jspx_th_c_005fout_005f25.setValue("${row[\"userId\"] }");
/* 5883 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5884 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5885 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5886 */       return true;
/*      */     }
/* 5888 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5889 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fif_005f35, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 5894 */     PageContext pageContext = _jspx_page_context;
/* 5895 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5897 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5898 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5899 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fif_005f35);
/*      */     
/* 5901 */     _jspx_th_c_005fout_005f26.setValue("${row[\"userName\"] }");
/* 5902 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5903 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5904 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5905 */       return true;
/*      */     }
/* 5907 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5908 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_c_005fif_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5913 */     PageContext pageContext = _jspx_page_context;
/* 5914 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5916 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5917 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 5918 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_c_005fif_005f36);
/*      */     
/* 5920 */     _jspx_th_html_005fradio_005f0.setStyle("position:relative;");
/*      */     
/* 5922 */     _jspx_th_html_005fradio_005f0.setOnclick("scaleOptions(this.value)");
/*      */     
/* 5924 */     _jspx_th_html_005fradio_005f0.setProperty("userGroupRole");
/*      */     
/* 5926 */     _jspx_th_html_005fradio_005f0.setValue("ADMIN");
/*      */     
/* 5928 */     _jspx_th_html_005fradio_005f0.setStyleClass("radiobutton");
/* 5929 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 5930 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 5931 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 5932 */       return true;
/*      */     }
/* 5934 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 5935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_c_005fif_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5940 */     PageContext pageContext = _jspx_page_context;
/* 5941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5943 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5944 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 5945 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_c_005fif_005f36);
/*      */     
/* 5947 */     _jspx_th_html_005fradio_005f1.setStyle("position:relative;");
/*      */     
/* 5949 */     _jspx_th_html_005fradio_005f1.setOnclick("scaleOptions(this.value)");
/*      */     
/* 5951 */     _jspx_th_html_005fradio_005f1.setProperty("userGroupRole");
/*      */     
/* 5953 */     _jspx_th_html_005fradio_005f1.setValue("DELADMIN");
/*      */     
/* 5955 */     _jspx_th_html_005fradio_005f1.setStyleClass("radiobutton");
/* 5956 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 5957 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 5958 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 5959 */       return true;
/*      */     }
/* 5961 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 5962 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_c_005fif_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5967 */     PageContext pageContext = _jspx_page_context;
/* 5968 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5970 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 5971 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 5972 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_c_005fif_005f36);
/*      */     
/* 5974 */     _jspx_th_html_005fradio_005f2.setStyle("position:relative;");
/*      */     
/* 5976 */     _jspx_th_html_005fradio_005f2.setOnclick("scaleOptions(this.value)");
/*      */     
/* 5978 */     _jspx_th_html_005fradio_005f2.setProperty("userGroupRole");
/*      */     
/* 5980 */     _jspx_th_html_005fradio_005f2.setValue("OPERATOR");
/*      */     
/* 5982 */     _jspx_th_html_005fradio_005f2.setStyleClass("radiobutton");
/* 5983 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 5984 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 5985 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 5986 */       return true;
/*      */     }
/* 5988 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 5989 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f37(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5994 */     PageContext pageContext = _jspx_page_context;
/* 5995 */     JspWriter out = _jspx_page_context.getOut();
/* 5996 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 5997 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 5999 */     IfTag _jspx_th_c_005fif_005f37 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6000 */     _jspx_th_c_005fif_005f37.setPageContext(_jspx_page_context);
/* 6001 */     _jspx_th_c_005fif_005f37.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6003 */     _jspx_th_c_005fif_005f37.setTest("${param.usergroup}");
/* 6004 */     int _jspx_eval_c_005fif_005f37 = _jspx_th_c_005fif_005f37.doStartTag();
/* 6005 */     if (_jspx_eval_c_005fif_005f37 != 0) {
/*      */       for (;;) {
/* 6007 */         out.write("\n\t\t<div id=\"ugallgroups\" style=\"display: none;\">\n\t\t\t");
/* 6008 */         JspRuntimeLibrary.include(request, response, "/jsp/AssociateMonitorGroups.jsp" + ("/jsp/AssociateMonitorGroups.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("selectionType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("usergroup", request.getCharacterEncoding()), out, false);
/* 6009 */         out.write(9);
/* 6010 */         out.write(9);
/* 6011 */         out.write("\n\t\t</div>\n\t");
/* 6012 */         int evalDoAfterBody = _jspx_th_c_005fif_005f37.doAfterBody();
/* 6013 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6017 */     if (_jspx_th_c_005fif_005f37.doEndTag() == 5) {
/* 6018 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37);
/* 6019 */       return true;
/*      */     }
/* 6021 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37);
/* 6022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f40(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6027 */     PageContext pageContext = _jspx_page_context;
/* 6028 */     JspWriter out = _jspx_page_context.getOut();
/* 6029 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 6030 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 6032 */     IfTag _jspx_th_c_005fif_005f40 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6033 */     _jspx_th_c_005fif_005f40.setPageContext(_jspx_page_context);
/* 6034 */     _jspx_th_c_005fif_005f40.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6036 */     _jspx_th_c_005fif_005f40.setTest("${param.usergroup}");
/* 6037 */     int _jspx_eval_c_005fif_005f40 = _jspx_th_c_005fif_005f40.doStartTag();
/* 6038 */     if (_jspx_eval_c_005fif_005f40 != 0) {
/*      */       for (;;) {
/* 6040 */         out.write("\n\t\t<div id=\"addusergroup_adgroup\" style=\"display:none;\">\t\n\t\t\t");
/* 6041 */         JspRuntimeLibrary.include(request, response, "/jsp/LdapAuthentication.jsp" + ("/jsp/LdapAuthentication.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("submitValue", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("usergroup", request.getCharacterEncoding()), out, false);
/* 6042 */         out.write(9);
/* 6043 */         out.write(9);
/* 6044 */         out.write("\n\t\t</div>\n\t");
/* 6045 */         int evalDoAfterBody = _jspx_th_c_005fif_005f40.doAfterBody();
/* 6046 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6050 */     if (_jspx_th_c_005fif_005f40.doEndTag() == 5) {
/* 6051 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f40);
/* 6052 */       return true;
/*      */     }
/* 6054 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f40);
/* 6055 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f24(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6060 */     PageContext pageContext = _jspx_page_context;
/* 6061 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6063 */     MessageTag _jspx_th_fmt_005fmessage_005f24 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6064 */     _jspx_th_fmt_005fmessage_005f24.setPageContext(_jspx_page_context);
/* 6065 */     _jspx_th_fmt_005fmessage_005f24.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 6066 */     int _jspx_eval_fmt_005fmessage_005f24 = _jspx_th_fmt_005fmessage_005f24.doStartTag();
/* 6067 */     if (_jspx_eval_fmt_005fmessage_005f24 != 0) {
/* 6068 */       if (_jspx_eval_fmt_005fmessage_005f24 != 1) {
/* 6069 */         out = _jspx_page_context.pushBody();
/* 6070 */         _jspx_th_fmt_005fmessage_005f24.setBodyContent((BodyContent)out);
/* 6071 */         _jspx_th_fmt_005fmessage_005f24.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6074 */         out.write("am.webclient.useradministration.domain.add.text");
/* 6075 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f24.doAfterBody();
/* 6076 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6079 */       if (_jspx_eval_fmt_005fmessage_005f24 != 1) {
/* 6080 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6083 */     if (_jspx_th_fmt_005fmessage_005f24.doEndTag() == 5) {
/* 6084 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 6085 */       return true;
/*      */     }
/* 6087 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 6088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6093 */     PageContext pageContext = _jspx_page_context;
/* 6094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6096 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6097 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 6098 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6100 */     _jspx_th_html_005ftext_005f8.setProperty("newDomainName");
/*      */     
/* 6102 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext");
/*      */     
/* 6104 */     _jspx_th_html_005ftext_005f8.setSize("30");
/* 6105 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 6106 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 6107 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 6108 */       return true;
/*      */     }
/* 6110 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 6111 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6116 */     PageContext pageContext = _jspx_page_context;
/* 6117 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6119 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6120 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 6121 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6123 */     _jspx_th_html_005ftext_005f9.setProperty("newDomainController");
/*      */     
/* 6125 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext");
/*      */     
/* 6127 */     _jspx_th_html_005ftext_005f9.setSize("30");
/* 6128 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 6129 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 6130 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 6131 */       return true;
/*      */     }
/* 6133 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 6134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6139 */     PageContext pageContext = _jspx_page_context;
/* 6140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6142 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6143 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/* 6144 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6146 */     _jspx_th_html_005ftext_005f10.setProperty("newDomainPort");
/*      */     
/* 6148 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext");
/*      */     
/* 6150 */     _jspx_th_html_005ftext_005f10.setSize("30");
/* 6151 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/* 6152 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/* 6153 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 6154 */       return true;
/*      */     }
/* 6156 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 6157 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6162 */     PageContext pageContext = _jspx_page_context;
/* 6163 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6165 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 6166 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 6167 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6169 */     _jspx_th_html_005fcheckbox_005f3.setProperty("sslenable");
/* 6170 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 6171 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 6172 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 6173 */       return true;
/*      */     }
/* 6175 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 6176 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6181 */     PageContext pageContext = _jspx_page_context;
/* 6182 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6184 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fnobody.get(RadioTag.class);
/* 6185 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 6186 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6188 */     _jspx_th_html_005fradio_005f3.setStyle("position:relative;");
/*      */     
/* 6190 */     _jspx_th_html_005fradio_005f3.setProperty("newDomainPermission");
/*      */     
/* 6192 */     _jspx_th_html_005fradio_005f3.setValue("0");
/*      */     
/* 6194 */     _jspx_th_html_005fradio_005f3.setStyleClass("radiobutton");
/* 6195 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 6196 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 6197 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 6198 */       return true;
/*      */     }
/* 6200 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 6201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6206 */     PageContext pageContext = _jspx_page_context;
/* 6207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6209 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fnobody.get(RadioTag.class);
/* 6210 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 6211 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6213 */     _jspx_th_html_005fradio_005f4.setStyle("position:relative;");
/*      */     
/* 6215 */     _jspx_th_html_005fradio_005f4.setProperty("newDomainPermission");
/*      */     
/* 6217 */     _jspx_th_html_005fradio_005f4.setValue("1");
/*      */     
/* 6219 */     _jspx_th_html_005fradio_005f4.setStyleClass("radiobutton");
/* 6220 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 6221 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 6222 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 6223 */       return true;
/*      */     }
/* 6225 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 6226 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\configureUsers_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */