/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.ChildMOHandler;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import java.io.IOException;
/*      */ import java.net.URLEncoder;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class MonitorGroupViewInclude_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   48 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*      */   
/*      */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List) {
/*   53 */     String ischecked = "true".equals(request.getParameter("checked")) ? "checked=\"checked\"" : "";
/*   54 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/*   55 */     boolean forInventory = false;
/*   56 */     String trdisplay = "none";
/*   57 */     String plusstyle = "inline";
/*   58 */     String minusstyle = "none";
/*   59 */     String haidTopLevel = "";
/*   60 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/*   62 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/*   64 */         haidTopLevel = request.getParameter("haid");
/*   65 */         forInventory = true;
/*   66 */         trdisplay = "table-row;";
/*   67 */         plusstyle = "none";
/*   68 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*   75 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*   77 */     if (("true".equals(request.getParameter("uncategorize"))) || ("orphaned".equals(request.getParameter("retaintree"))))
/*      */     {
/*   79 */       trdisplay = "table-row;";
/*      */     }
/*   81 */     HashMap<String, String[]> procAndServiceInfo = (HashMap)request.getAttribute("ProcessAndServiceInfo");
/*   82 */     ArrayList listtoreturn = new ArrayList();
/*   83 */     StringBuffer toreturn = new StringBuffer();
/*   84 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/*   85 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/*   86 */     Properties alert = (Properties)availhealth.get("alert");
/*   87 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/*   89 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/*   90 */       String childresid = (String)singlerow.get(0);
/*   91 */       String childresname = (String)singlerow.get(1);
/*   92 */       String childtype = ((String)singlerow.get(2) + "").trim();
/*   93 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/*   94 */       String shortname = ((String)singlerow.get(4) + "").trim();
/*   95 */       String unmanagestatus = (String)singlerow.get(5);
/*   96 */       String actionstatus = (String)singlerow.get(6);
/*   97 */       String linkclass = "monitorgp-links";
/*   98 */       String titleforres = childresname;
/*   99 */       String titilechildresname = childresname;
/*  100 */       String childimg = "/images/trcont.png";
/*  101 */       String flag = "enable";
/*  102 */       String dcstarted = (String)singlerow.get(8);
/*  103 */       String configMonitor = "";
/*  104 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/*  105 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/*  107 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/*  109 */       if (singlerow.get(7) != null)
/*      */       {
/*  111 */         flag = (String)singlerow.get(7);
/*      */       }
/*  113 */       String haiGroupType = "0";
/*  114 */       if ("HAI".equals(childtype))
/*      */       {
/*  116 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/*  118 */       childimg = "/images/trend.png";
/*  119 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/*  120 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/*  121 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/*  123 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/*  125 */       else if (actionstatus.equals("0"))
/*      */       {
/*  127 */         actionmsg = FormatUtil.getString("Actions Disabled");
/*  128 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/*  131 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/*  133 */         linkclass = "disabledtext";
/*  134 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/*  136 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/*  137 */       String availmouseover = "";
/*  138 */       if (alert.getProperty(availkey) != null)
/*      */       {
/*  140 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*  142 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/*  143 */       String healthmouseover = "";
/*  144 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/*  146 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey) + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/*  149 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/*  150 */       int spacing = 0;
/*  151 */       if (level >= 1)
/*      */       {
/*  153 */         spacing = 40 * level;
/*      */       }
/*  155 */       if (childtype.equals("HAI"))
/*      */       {
/*  157 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/*  158 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/*  159 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/*  161 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/*  162 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/*  163 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/*  164 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*  165 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/*  166 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  " + ischecked + ">";
/*  167 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/*  168 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/*  169 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/*  170 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/*  171 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/*  173 */         if (!forInventory)
/*      */         {
/*  175 */           removefromgroup = "";
/*      */         }
/*      */         
/*  178 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/*  180 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType))))
/*      */         {
/*  182 */           actions = editlink + actions;
/*      */         }
/*  184 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType))))
/*      */         {
/*  186 */           actions = actions + associatelink;
/*      */         }
/*  188 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/*  189 */         String arrowimg = "";
/*  190 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/*  192 */           actions = "";
/*  193 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/*  194 */           checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  " + ischecked + ">";
/*  195 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/*  197 */         if (isIt360)
/*      */         {
/*  199 */           actionimg = "";
/*  200 */           actions = "";
/*  201 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/*  202 */           checkbox = "";
/*      */         }
/*      */         
/*  205 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/*  207 */           actions = "";
/*      */         }
/*  209 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/*  211 */           checkbox = "";
/*      */         }
/*      */         
/*  214 */         String resourcelink = "";
/*      */         
/*  216 */         if ((!isIt360) || ((isIt360) && (childmos.get(childresid + "") != null)))
/*      */         {
/*  218 */           if ((flag != null) && (flag.equals("enable")))
/*      */           {
/*  220 */             resourcelink = "<a isopen=\"true\" href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "',this),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */           }
/*      */           else
/*      */           {
/*  224 */             resourcelink = "<a isopen=\"true\" href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "',this),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */           }
/*      */           
/*  227 */           toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/*  228 */           toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/*  229 */           toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/*  230 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*  231 */           toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/*  232 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/*  233 */           if (!isIt360)
/*      */           {
/*  235 */             toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */           }
/*      */           else
/*      */           {
/*  239 */             toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */           }
/*      */           
/*  242 */           toreturn.append("</tr>");
/*  243 */           if (childmos.get(childresid + "") != null)
/*      */           {
/*  245 */             String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/*  246 */             toreturn.append(toappend);
/*      */           }
/*      */           else
/*      */           {
/*  250 */             String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/*  251 */             if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*  252 */               assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*  253 */             if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType))))
/*      */             {
/*  255 */               toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/*  256 */               toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/*  257 */               toreturn.append(assocMessage);
/*  258 */               toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/*  259 */               toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/*  260 */               toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/*  261 */               toreturn.append("</tr>");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  268 */         String resourcelink = null;
/*  269 */         boolean hideEditLink = false;
/*  270 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/*  272 */           String link1 = (String)extDeviceMap.get(childresid);
/*  273 */           hideEditLink = true;
/*  274 */           if (isIt360)
/*      */           {
/*  276 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/*  280 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*  282 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/*  284 */           hideEditLink = true;
/*  285 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/*  286 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*  289 */         else if (ChildMOHandler.isChildMonitorTypeSupportedForMG(childtype))
/*      */         {
/*  291 */           String link1 = "/showapplication.do?method=showChildApplicationDetail&resId=" + childresid;
/*  292 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         else
/*      */         {
/*  296 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/*  299 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/*  300 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" " + ischecked + ">";
/*  301 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/*  302 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*  303 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/*  304 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/*  305 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/*  306 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*  307 */         if (ChildMOHandler.isChildMonitorTypeSupportedForMG(childtype))
/*      */         {
/*  309 */           String parentResId = "";
/*  310 */           String childdisplayname = "";
/*  311 */           if (procAndServiceInfo != null)
/*      */           {
/*  313 */             String[] arr = (String[])procAndServiceInfo.get(childresid);
/*  314 */             parentResId = arr[0];
/*  315 */             childdisplayname = arr[1];
/*      */           }
/*  317 */           if (childtype.equals("Process"))
/*      */           {
/*  319 */             editlink = "<a href=\"/HostResource.do?editProcess=true&processid=" + childresid + "&resourceid=" + parentResId + "&disableEdit=false&haid=&appName=\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*      */           }
/*  321 */           else if (childtype.equals("Service"))
/*      */           {
/*  323 */             editlink = "<a href=\"/HostResource.do?actiononServices=Edit&serviceid=" + childresid + "&resourceid=" + parentResId + "&displayname=" + childdisplayname + "&servicename=" + childdisplayname + "&name=&haid=&appName=\"  class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*      */           }
/*      */         }
/*  326 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/*  327 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/*  328 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/*  329 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/*  330 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/*  332 */         if (hideEditLink)
/*      */         {
/*  334 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/*  336 */         if (!forInventory)
/*      */         {
/*  338 */           removefromgroup = "";
/*      */         }
/*  340 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*  341 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/*  342 */           actions = actions + configcustomfields;
/*      */         }
/*  344 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType))))
/*      */         {
/*  346 */           actions = editlink + actions;
/*      */         }
/*  348 */         String managedLink = "";
/*  349 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/*      */ 
/*      */ 
/*  353 */           checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" " + ischecked + ">" + "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/*      */           
/*  355 */           actions = "";
/*  356 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/*  357 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/*  360 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/*  362 */           checkbox = "";
/*      */         }
/*      */         
/*  365 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/*  367 */           actions = "";
/*      */         }
/*  369 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'" + (ChildMOHandler.isChildMonitorTypeSupportedForMG(childtype) ? " ischild=true>" : ">"));
/*  370 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/*  371 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/*  372 */         if (isIt360)
/*      */         {
/*  374 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/*  378 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/*  380 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/*  381 */         if (!"Service".equals(childtype))
/*      */         {
/*  383 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/*      */         }
/*      */         else
/*      */         {
/*  387 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">&nbsp;</td>");
/*      */         }
/*  389 */         if (!isIt360)
/*      */         {
/*  391 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/*  395 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*  397 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/*  400 */     return toreturn.toString(); }
/*      */   
/*  402 */   long j = 0L;
/*      */   
/*      */   public Properties getStatus(List entitylist) {
/*  405 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  406 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  408 */       String entityStr = (String)keys.nextElement();
/*  409 */       String mmessage = temp.getProperty(entityStr);
/*  410 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  411 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  413 */     return temp;
/*      */   }
/*      */   
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  418 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  419 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  421 */       String entityStr = (String)keys.nextElement();
/*  422 */       String mmessage = temp.getProperty(entityStr);
/*  423 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  424 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  426 */     return temp;
/*      */   }
/*      */   
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString) {
/*  430 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity)
/*      */   {
/*  435 */     if (severity == null)
/*      */     {
/*  437 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  439 */     if (severity.equals("5"))
/*      */     {
/*  441 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  443 */     if (severity.equals("1"))
/*      */     {
/*  445 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  450 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  457 */     if (severity == null)
/*      */     {
/*  459 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  461 */     if (severity.equals("5"))
/*      */     {
/*  463 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  465 */     if (severity.equals("4"))
/*      */     {
/*  467 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  469 */     if (severity.equals("1"))
/*      */     {
/*  471 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  476 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  481 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  487 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/*  488 */   static { _jspx_dependants.put("/jsp/includes/bulkActions.jspf", Long.valueOf(1473429417000L));
/*  489 */     _jspx_dependants.put("/jsp/groupviewutil.jspf", Long.valueOf(1473429417000L));
/*  490 */     _jspx_dependants.put("/jsp/includes/monitors_setasdefault.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*  515 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  519 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  520 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  521 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  522 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  523 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  524 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  525 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  526 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  527 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  528 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  529 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  530 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  531 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  532 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  533 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  534 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  535 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  536 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  537 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  541 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  542 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  543 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  544 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  545 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  546 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/*  547 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  548 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  549 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  550 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  551 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  552 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  553 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  554 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*  555 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  556 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*  557 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  564 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  567 */     JspWriter out = null;
/*  568 */     Object page = this;
/*  569 */     JspWriter _jspx_out = null;
/*  570 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  574 */       response.setContentType("text/html;charset=UTF-8");
/*  575 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/*  577 */       _jspx_page_context = pageContext;
/*  578 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  579 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  580 */       session = pageContext.getSession();
/*  581 */       out = pageContext.getOut();
/*  582 */       _jspx_out = out;
/*      */       
/*  584 */       out.write("<!--$Id$-->\n");
/*  585 */       out.write(10);
/*      */       
/*  587 */       request.setAttribute("HelpKey", "Monitors Page");
/*      */       
/*  589 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  590 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  591 */       out.write(10);
/*  592 */       out.write(10);
/*  593 */       Hashtable availabilitykeys = null;
/*  594 */       synchronized (application) {
/*  595 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/*  596 */         if (availabilitykeys == null) {
/*  597 */           availabilitykeys = new Hashtable();
/*  598 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/*  601 */       out.write(10);
/*  602 */       Hashtable healthkeys = null;
/*  603 */       synchronized (application) {
/*  604 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/*  605 */         if (healthkeys == null) {
/*  606 */           healthkeys = new Hashtable();
/*  607 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/*  610 */       out.write(10);
/*  611 */       ManagedApplication MA = null;
/*  612 */       synchronized (application) {
/*  613 */         MA = (ManagedApplication)_jspx_page_context.getAttribute("MA", 4);
/*  614 */         if (MA == null) {
/*  615 */           MA = new ManagedApplication();
/*  616 */           _jspx_page_context.setAttribute("MA", MA, 4);
/*      */         }
/*      */       }
/*  619 */       out.write("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
/*      */       
/*  621 */       if ((EnterpriseUtil.isIt360MSPEdition()) && (request.getSession().getAttribute("custProp") == null))
/*      */       {
/*  623 */         response.sendRedirect("/showIT360Tile.do?TileName=IT360.CustomersSummary");
/*      */       }
/*      */       
/*  626 */       out.write(10);
/*      */       
/*  628 */       String resourceName = "";
/*  629 */       String customValue = request.getParameter("customValue");
/*  630 */       boolean showmore = Boolean.parseBoolean((String)request.getAttribute("showmore"));
/*  631 */       int pageno = 2;
/*  632 */       if (request.getParameter("pageno") != null) {
/*  633 */         pageno = Integer.parseInt(request.getParameter("pageno")) + 1;
/*      */       }
/*  635 */       String viewtype = (String)request.getAttribute("viewtype");
/*  636 */       Hashtable chilmos = (Hashtable)request.getAttribute("childlist");
/*  637 */       if (chilmos == null)
/*      */       {
/*  639 */         chilmos = new Hashtable();
/*      */       }
/*  641 */       MyFields fields = new MyFields();
/*  642 */       ArrayList tags = null;
/*  643 */       tags = MyFields.getDollarTags(false, "HAI", request, false);
/*  644 */       request.setAttribute("tags", tags);
/*  645 */       int displayNameindex = 0;
/*  646 */       int actionIndex = 9;
/*  647 */       int unmanagestatusindex = 8;
/*  648 */       int enableindex = 10;
/*  649 */       int resourceTypeindex = 7;
/*  650 */       int residIndex = 6;
/*  651 */       int haiGroupTypeIndex = 11;
/*  652 */       String healthid = "18";
/*  653 */       String availid = "17";
/*  654 */       String isopen = "false";
/*  655 */       String forInventory = (String)request.getAttribute("forInventory");
/*  656 */       boolean isInventory = false;
/*  657 */       String colpseStyle = "none";
/*  658 */       String expandStyle = "inline";
/*  659 */       String plusStyle = "inline";
/*  660 */       String minusStyle = "none";
/*  661 */       String rowStyle = "none";
/*  662 */       String img = "";
/*      */       
/*  664 */       ArrayList resIDs = new ArrayList();
/*  665 */       ArrayList entitylist = new ArrayList();
/*  666 */       int totalmonitorcount = 0;
/*  667 */       ArrayList tempresourelist = new ArrayList();
/*  668 */       ArrayList eumChildList = com.adventnet.appmanager.util.Constants.getEUMChildList();
/*  669 */       HashMap extDeviceMap = null;
/*  670 */       HashMap site24x7List = null;
/*  671 */       String ag1 = request.getHeader("User-Agent");
/*  672 */       String filterstyle = "filterby-txt";
/*  673 */       String nouncategorize = (String)request.getAttribute("nouncategorize");
/*  674 */       ag1 = ag1.toLowerCase();
/*  675 */       if (ag1.contains("chrome"))
/*      */       {
/*  677 */         filterstyle = "bodytextbold";
/*      */       }
/*      */       
/*  680 */       if ((com.adventnet.appmanager.util.Constants.isIt360) || (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured()))
/*      */       {
/*  682 */         extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink(false);
/*  683 */         if (com.adventnet.appmanager.util.OEMUtil.isRemove("enable.proxyForExtProd"))
/*      */         {
/*      */ 
/*      */ 
/*  687 */           HashMap opmExtDeviceMap = com.adventnet.appmanager.util.ExtProdUtil.getDeviceLinksOfExtProduct("OpManager", false, false);
/*  688 */           extDeviceMap.putAll(opmExtDeviceMap);
/*      */         }
/*  690 */         site24x7List = com.adventnet.appmanager.util.DBUtil.getAllsite24x7MonitorsLink();
/*      */       }
/*  692 */       String already = "false";
/*  693 */       if ("true".equals(request.getParameter("uncategorize"))) {
/*  694 */         isopen = "true";
/*      */       }
/*      */       
/*      */ 
/*  698 */       String uncatstyle = "new-monitordiv-link";
/*  699 */       String groupviewstyle = "new-monitordiv-link";
/*      */       
/*  701 */       if ("true".equalsIgnoreCase(request.getParameter("uncategorize")))
/*      */       {
/*  703 */         uncatstyle = "bulkmon-tag";
/*      */       }
/*      */       else {
/*  706 */         groupviewstyle = "bulkmon-tag";
/*      */       }
/*      */       
/*      */ 
/*  710 */       out.write(10);
/*  711 */       out.write(10);
/*  712 */       out.write(10);
/*      */       
/*  714 */       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  715 */       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  716 */       _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */       
/*  718 */       _jspx_th_logic_005fiterate_005f0.setName("applications");
/*      */       
/*  720 */       _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */       
/*  722 */       _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*      */       
/*  724 */       _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*  725 */       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  726 */       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  727 */         ArrayList row = null;
/*  728 */         Integer i = null;
/*  729 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  730 */           out = _jspx_page_context.pushBody();
/*  731 */           _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/*  732 */           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */         }
/*  734 */         row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  735 */         i = (Integer)_jspx_page_context.findAttribute("i");
/*      */         for (;;) {
/*  737 */           out.write(10);
/*      */           try
/*      */           {
/*  740 */             String parentResourceType = (String)row.get(resourceTypeindex);
/*  741 */             availid = (String)healthkeys.get(parentResourceType);
/*  742 */             healthid = (String)availabilitykeys.get(parentResourceType);
/*  743 */             entitylist.add((String)row.get(residIndex) + "_" + availid);
/*  744 */             entitylist.add((String)row.get(residIndex) + "_" + healthid);
/*      */             try
/*      */             {
/*  747 */               ArrayList srow = (ArrayList)chilmos.get((String)row.get(residIndex) + "");
/*  748 */               if (srow != null)
/*      */               {
/*  750 */                 for (int k = 0; k < srow.size(); k++)
/*      */                 {
/*  752 */                   ArrayList mo = (ArrayList)srow.get(k);
/*  753 */                   if ((mo != null) && (mo.get(0) != null) && (!((String)mo.get(0)).equals("null")))
/*      */                   {
/*  755 */                     String cresid = ((String)mo.get(0) + "").trim();
/*  756 */                     if (!eumChildList.contains(cresid))
/*      */                     {
/*      */ 
/*      */ 
/*  760 */                       String resourceType = ((String)mo.get(2) + "").trim();
/*  761 */                       String healthkey = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/*  762 */                       if ((healthkey != null) && (!entitylist.contains(cresid + "_" + healthkey)))
/*      */                       {
/*  764 */                         entitylist.add(cresid + "_" + healthkey);
/*      */                       }
/*      */                       
/*  767 */                       String availabilitykey = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*  768 */                       if ((!resourceType.equals("HAI")) && (!tempresourelist.contains(cresid)))
/*      */                       {
/*  770 */                         tempresourelist.add(cresid);
/*  771 */                         totalmonitorcount++;
/*      */                       }
/*  773 */                       if ((availabilitykey != null) && (!entitylist.contains(cresid + "_" + availabilitykey)))
/*      */                       {
/*  775 */                         entitylist.add(cresid + "_" + availabilitykey);
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception ex) {
/*  783 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  788 */             ex.printStackTrace();
/*      */           }
/*      */           
/*  791 */           out.write(10);
/*  792 */           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  793 */           row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  794 */           i = (Integer)_jspx_page_context.findAttribute("i");
/*  795 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*  798 */         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  799 */           out = _jspx_page_context.popBody();
/*      */         }
/*      */       }
/*  802 */       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  803 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*      */       }
/*      */       else {
/*  806 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  807 */         out.write(10);
/*      */         
/*  809 */         if (("true".equals(forInventory)) || ("true".equalsIgnoreCase(request.getParameter("alllevels"))))
/*      */         {
/*  811 */           already = "true";
/*  812 */           isInventory = true;
/*  813 */           String haidInventory = request.getParameter("haid");
/*  814 */           ArrayList singlechilmos1 = (ArrayList)chilmos.get(haidInventory);
/*  815 */           if (singlechilmos1 != null)
/*      */           {
/*  817 */             request.setAttribute("applications", singlechilmos1);
/*      */           }
/*      */           else
/*      */           {
/*  821 */             request.setAttribute("applications", new ArrayList());
/*      */           }
/*  823 */           actionIndex = 6;
/*  824 */           residIndex = 0;
/*  825 */           enableindex = 7;
/*  826 */           unmanagestatusindex = 5;
/*  827 */           displayNameindex = 1;
/*  828 */           resourceTypeindex = 2;
/*  829 */           colpseStyle = "inline";
/*  830 */           expandStyle = "none";
/*  831 */           plusStyle = "none";
/*  832 */           minusStyle = "inline";
/*  833 */           rowStyle = "table-row";
/*  834 */           isopen = "true";
/*      */         }
/*      */         
/*  837 */         out.write(10);
/*  838 */         out.write(10);
/*      */         
/*  840 */         Properties alert = getStatus(entitylist);
/*  841 */         request.setAttribute("alert", alert);
/*      */         
/*  843 */         out.write("\n\n<Script>\n$.ajaxSetup({\n  cache: false\n});\nfunction fnSelectAll(e,name)\n{\n    ToggleAll(e,document.deletemonitor,name);\n}\n\nfunction showCustomFieldValues(http1)\n{\n        if(http1.readyState==4)\n        {\n                document.getElementById(\"customFieldValues\").style.display = \"inline\";\n                document.getElementById(\"customFieldValues\").innerHTML=http1.responseText;//No I18N\n\t\t\t\t\n        }\n\t\t$('.chzn-select').chosen();\n}\n\n\n\nfunction loadUrl(option){\n\n        if(option !=null && option != '-'){\n        location.href=\"/showresource.do?method=showMonitorGroupView&customValue=\"+option;\n        }\n\n}\nfunction loadURLType(option,frm,a,forBulkAssign)\n{\n\n\tif(option.indexOf(\"SYSTEMDATA\") != -1 || option.indexOf(\"USERDATA\") != -1 || option.indexOf(\"LOCATION_NAME\") != -1 || option.indexOf(\"USERNAME\") != -1 || option.indexOf(\"VALUEID\") != -1){\n\n                                var http1=getHTTPObject();\n                                http1.onreadystatechange= function (){showCustomFieldValues(http1);};//No I18N\n");
/*  844 */         out.write("\t\t\t\tif(option.indexOf(\"$\") != -1){\n\t\t\t\t\tdocument.getElementById(\"groupviewfilterby\").value=option.substring(0,option.indexOf(\"$\"))\n\t\t\t\t\tURL = \"/myFields.do?method=getFieldValues&aliasName=\"+option.substring(0,option.indexOf(\"$\"))+\"&optionSel=\"+option.substring(option.indexOf(\"$\")+1)+\"&forBulkAssign=false&monitortype=HAI\"; // NO I18N\n\n\t\t\t\t}else{\n                                URL=\"/myFields.do?method=getFieldValues&aliasName=\"+option+\"&forBulkAssign=false&monitortype=HAI\";//No I18N\n\t\t\t\t}\n                                http1.open(\"GET\",URL,true);//No I18N\n                                http1.send(null);//No I18N\n\n\n                        }\n}\n\nvar retaintree;\nfunction deleteMO(unopengroups,MGselected)\n{\n\t");
/*  845 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */           return;
/*  847 */         out.write(10);
/*  848 */         out.write(9);
/*      */         
/*  850 */         NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  851 */         _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  852 */         _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */         
/*  854 */         _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  855 */         int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  856 */         if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */           for (;;) {
/*  858 */             out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert('");
/*  859 */             out.print(FormatUtil.getString("am.webclient.monitorgroupview.delete.alert"));
/*  860 */             out.write("');//No I18N\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\t\t   var c_value = \"\";\n           for (var i=0; i < document.deletemonitor.select.length; i++)\n\t\t   {\n\t\t      if (document.deletemonitor.select[i].checked)\n\t\t      {\n\t\t         c_value = c_value + document.deletemonitor.select[i].value + \"|\";//No I18N\t\n\t\t         }\n\n\t\t\t}\n\t        if(MGselected==\"true\")\n\t        {\n\t\t\t\t url=\"/deleteMO.do?method=deleteMonitorGroup&choosedeleteoption=true&select=\"+c_value+\"&retaintree=\"+retaintree+\"&haid=");
/*  861 */             if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/*      */               return;
/*  863 */             out.write("\"+\"&unopengroups=\"+unopengroups;//No I18N \t\t\t\t \t\t\t\t \n\t\t \t\t var featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes,left=300,top=200\";\n\t\t                 windowReference = window.open(url,'windowName',featurelist);\n\t\t                 document.deletemonitor.operation.selectedIndex=0;\n\t        }\n\t\telse\n\t\t{\n\t\t  if (confirm('");
/*  864 */             out.print(FormatUtil.getString("Do you want to delete the selected Monitors"));
/*  865 */             out.write("'))\n\t\t  {\t\t  \t\t   \n\t\t   location.href= \"/deleteMO.do?method=deleteMonitorGroup&select=\"+c_value+\"&retaintree=\"+retaintree+\"&haid=");
/*  866 */             if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/*      */               return;
/*  868 */             out.write("\"+\"&unopengroups=\"+unopengroups;//No I18N\n\t\t   \t\t   \n\t\t  }\n\t\t  else\n\t\t  {\n\t\t     document.deletemonitor.operation.selectedIndex=0;\n\t\t     return;\n\t\t  }\n\t\t}\n\t");
/*  869 */             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  870 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  874 */         if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  875 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */         }
/*      */         else {
/*  878 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  879 */           out.write("\n}\n\nfunction unManageMO(unopengroups,res_list)\n{\n\t");
/*  880 */           if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */             return;
/*  882 */           out.write(10);
/*  883 */           out.write(9);
/*      */           
/*  885 */           NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  886 */           _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  887 */           _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */           
/*  889 */           _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/*  890 */           int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  891 */           if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */             for (;;) {
/*  893 */               out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert('");
/*  894 */               out.print(FormatUtil.getString("am.webclient.monitorgroupview.unmanage.alert"));
/*  895 */               out.write("');//No I18N\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{\n\t\tif(confirm(\"");
/*  896 */               out.print(FormatUtil.getString("am.webclient.monitorgroupview.unmanage.confirm.alert"));
/*  897 */               out.write("\"))\n\t\t{\n\t\t\tvar show_msg=\"false\";\n\t\t\tvar res=unopengroups+'|'+res_list; //No i18n\n\t\t\tvar resids = res.split('|');\n\t\t\tvar i=0\n\t\t\tfor(i=0;i<resids.length;i++)\n\t\t\t{\n\t\t\t\tvar url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+resids[i]; //No i18n\n\t\t\t$.ajax({\n\t\t\t\ttype:'POST', //No i18n\n\t\t\t\turl:url,\n\t\t\t\tasync:false,\n\t\t\t\tsuccess: function(data)\n\t\t\t\t{\n\t\t\t\t\tshow_msg=data\n\t\t\t\t}\n\t\t\t});\n\t\t\tif(show_msg.indexOf(\"true\")>-1)\n\t\t\t{\n\t\t\t\talert(\"");
/*  898 */               out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/*  899 */               out.write("\")\n\t\t\t\tbreak;\n\t\t\t}\n\t\t    \n\t\t\t}\n\t\t    ");
/*  900 */               if (_jspx_meth_c_005fif_005f0(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                 return;
/*  902 */               out.write("\n\t\t    ");
/*  903 */               if (_jspx_meth_c_005fif_005f1(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                 return;
/*  905 */               out.write("\n                    document.deletemonitor.submit();\n\t\t}\n\t\telse\n\t\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t}\n\t}\n\t");
/*  906 */               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  907 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  911 */           if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  912 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */           }
/*      */           else {
/*  915 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  916 */             out.write("\n}\n\nfunction assignCustomField(unopengroups){\n\n");
/*  917 */             if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */               return;
/*  919 */             out.write("\n        ");
/*      */             
/*  921 */             NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  922 */             _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  923 */             _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */             
/*  925 */             _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/*  926 */             int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  927 */             if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */               for (;;) {
/*  929 */                 out.write("\n\n if(!checkforOneSelected(document.deletemonitor,\"select\"))\n        {\n                alert(\"");
/*  930 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupview.customfield.alert"));
/*  931 */                 out.write("\");//No i18n\n                document.deletemonitor.operation.selectedIndex=0;\n                return;\n        }\n        else\n        {\n\n\t\t\t                var len = document.deletemonitor.select.length;\n                var temp=\"\";\n                if(len > 0)\n                {\n                        for( i=0; i<len; i++)\n                        {\n                                if(document.deletemonitor.select[i].checked)\n                                        temp = temp + document.deletemonitor.select[i].value + \",\";\n                        }\n                }\n                else\n                {\n                        temp=document.deletemonitor.select.value;\n                }\n\n\n                document.deletemonitor.action=\"/myFields.do?method=assignCustomFields&resids=\"+temp+\"&unopengroups=\"+unopengroups;\n                        window.open(document.deletemonitor.action,'','resizable=yes,scrollbars=yes,width=700,height=400,top=200,left=200');\n\n\n        }\n        ");
/*  932 */                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/*  933 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  937 */             if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/*  938 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*      */             }
/*      */             else {
/*  941 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*  942 */               out.write("\n\n\n}\n\nfunction unManageAndReset(unopengroups)\n{\n\t");
/*  943 */               if (_jspx_meth_logic_005fpresent_005f3(_jspx_page_context))
/*      */                 return;
/*  945 */               out.write(10);
/*  946 */               out.write(9);
/*      */               
/*  948 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  949 */               _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/*  950 */               _jspx_th_logic_005fnotPresent_005f3.setParent(null);
/*      */               
/*  952 */               _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/*  953 */               int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/*  954 */               if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                 for (;;) {
/*  956 */                   out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert('");
/*  957 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupview.unmanage.alert"));
/*  958 */                   out.write("');//No I18N\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{\n\t\tif(confirm(\"");
/*  959 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupview.reset.confirm.alert"));
/*  960 */                   out.write("\"))\n\t\t{\n\t\t\tvar show_msg=\"false\"\n\t\t\tvar url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+unopengroups; //No i18n\n\t\t    $.ajax({\n\t\t\t\ttype:'POST', //No i18n\n\t\t\t\turl:url,\n\t\t\t\tasync:false,\n\t\t\t\tsuccess: function(data)\n\t\t\t\t{\n\t\t\t\t\t\tshow_msg=data\n\t\t\t\t}\n\t\t\t});\n\t\t\tif(show_msg.indexOf(\"true\")>-1)\n\t\t\t{\n\t\t\t\talert(\"");
/*  961 */                   out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/*  962 */                   out.write("\")\n\t\t\t}\n\t\t    ");
/*  963 */                   if (_jspx_meth_c_005fif_005f2(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                     return;
/*  965 */                   out.write("\n\t\t    ");
/*  966 */                   if (_jspx_meth_c_005fif_005f3(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                     return;
/*  968 */                   out.write("\n                    document.deletemonitor.submit();\n\t\t}\n\t\telse\n\t\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t}\n\t}\n\t");
/*  969 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/*  970 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  974 */               if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/*  975 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/*      */               }
/*      */               else {
/*  978 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/*  979 */                 out.write("\n}\n\nfunction manageMO(unopengroups)\n{\n\n\t");
/*  980 */                 if (_jspx_meth_logic_005fpresent_005f4(_jspx_page_context))
/*      */                   return;
/*  982 */                 out.write(10);
/*  983 */                 out.write(9);
/*      */                 
/*  985 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  986 */                 _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/*  987 */                 _jspx_th_logic_005fnotPresent_005f4.setParent(null);
/*      */                 
/*  989 */                 _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/*  990 */                 int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/*  991 */                 if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                   for (;;) {
/*  993 */                     out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert('");
/*  994 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.manage.alert"));
/*  995 */                     out.write("');//No I18N\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{ \n\n\t\tif(confirm(\"");
/*  996 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.manage.confirm.alert"));
/*  997 */                     out.write("\"))\n\t\t{\n\t\t  ");
/*  998 */                     if (_jspx_meth_c_005fif_005f4(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                       return;
/* 1000 */                     out.write("\n\t\t ");
/* 1001 */                     if (_jspx_meth_c_005fif_005f5(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                       return;
/* 1003 */                     out.write("\n                  document.deletemonitor.submit();\n\t\t}\n\t\telse\n\t\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t}\n\t}\n\t");
/* 1004 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 1005 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1009 */                 if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 1010 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/*      */                 }
/*      */                 else {
/* 1013 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 1014 */                   out.write("\n}\n\n\nfunction disableactions(unopengroups)\n{\n     ");
/* 1015 */                   if (_jspx_meth_logic_005fpresent_005f5(_jspx_page_context))
/*      */                     return;
/* 1017 */                   out.write(10);
/* 1018 */                   out.write(9);
/*      */                   
/* 1020 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1021 */                   _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 1022 */                   _jspx_th_logic_005fnotPresent_005f5.setParent(null);
/*      */                   
/* 1024 */                   _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 1025 */                   int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 1026 */                   if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                     for (;;) {
/* 1028 */                       out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t\t{\n\t\t\talert('");
/* 1029 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupview.disable.alert"));
/* 1030 */                       out.write("');//No I18N\n\t\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t\treturn;\n\t}\n\tif(confirm('");
/* 1031 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupview.disable.confirm.alert"));
/* 1032 */                       out.write("'))\n\t{\n\t ");
/* 1033 */                       if (_jspx_meth_c_005fif_005f6(_jspx_th_logic_005fnotPresent_005f5, _jspx_page_context))
/*      */                         return;
/* 1035 */                       out.write(10);
/* 1036 */                       out.write(9);
/* 1037 */                       out.write(32);
/* 1038 */                       if (_jspx_meth_c_005fif_005f7(_jspx_th_logic_005fnotPresent_005f5, _jspx_page_context))
/*      */                         return;
/* 1040 */                       out.write("\n\t  document.deletemonitor.submit();\n\t}\n\telse\n\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t}\n\t");
/* 1041 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 1042 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1046 */                   if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 1047 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/*      */                   }
/*      */                   else {
/* 1050 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 1051 */                     out.write("\n}\nfunction enableactions(unopengroups)\n{\n\n     ");
/* 1052 */                     if (_jspx_meth_logic_005fpresent_005f6(_jspx_page_context))
/*      */                       return;
/* 1054 */                     out.write(10);
/* 1055 */                     out.write(9);
/*      */                     
/* 1057 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1058 */                     _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 1059 */                     _jspx_th_logic_005fnotPresent_005f6.setParent(null);
/*      */                     
/* 1061 */                     _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 1062 */                     int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 1063 */                     if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                       for (;;) {
/* 1065 */                         out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t\t{\n\t\t\talert('");
/* 1066 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupview.enable.alert"));
/* 1067 */                         out.write("');//No I18N\n\t\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t\treturn;\n\t}\n\tif(confirm('");
/* 1068 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupview.enable.confirm.alert"));
/* 1069 */                         out.write("'))\n\t{\n\t  ");
/* 1070 */                         if (_jspx_meth_c_005fif_005f8(_jspx_th_logic_005fnotPresent_005f6, _jspx_page_context))
/*      */                           return;
/* 1072 */                         out.write(10);
/* 1073 */                         out.write(9);
/* 1074 */                         if (_jspx_meth_c_005fif_005f9(_jspx_th_logic_005fnotPresent_005f6, _jspx_page_context))
/*      */                           return;
/* 1076 */                         out.write("\n\t  document.deletemonitor.submit();\n\t}\n\telse\n\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t}\n\t");
/* 1077 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 1078 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1082 */                     if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 1083 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/*      */                     }
/*      */                     else {
/* 1086 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 1087 */                       out.write("\n}\n\nfunction selectAllChildCKbs(obname,ckb,myfrm) {\n\n\t\n    if(typeof(myfrm.length)==\"undefined\") {\n\t\treturn;\n    }\n\n    for(i=0;i<myfrm.length;i++) {\n\n        if(myfrm.elements[i].type == \"checkbox\" && myfrm.elements[i].id.indexOf(obname) == 0) {\n           myfrm.elements[i].checked = ckb.checked;\n        }\n    }\n\t\n\n}\n\nfunction deselectParentCKbs(obname1,ckb1,myfrm)\n{\n\tif(ckb1.checked)\n\treturn;\n\n\tvar temp1=obname1.split(\"|\");\n\tfor(i=0;i<temp1.length;i++)\n\t{\n\tif(i==0)\n\tparentresid=temp1[i];//No I18N\n\telse\n\tparentresid=parentresid+\"|\"+temp1[i];//No I18N\n\n\tfor(j=0;j<myfrm.length;j++) {\n\n\t\tif(myfrm.elements[j].id == parentresid) {\n\t\t\tmyfrm.elements[j].checked = false;\n\t\t}\n\t    }\n\t}\n\n}\n\nfunction invokeOperations(act)\n{\ntry{\n var alldivs =document.getElementsByTagName(\"div\");\nvar i;\nretaintree=\"\";//No I18N\nvar unopenresid ='';\nvar MGselected=\"false\";//No I18N\nvar res_list ='';  //No I18N\n$('.expandlink').each(function(){\nif($(this).attr('isopen')=='true'){ //No I18N\n\t$('.bulkmonHeader').each(function(){\nvar row=$(this).closest('tr');//No I18N\n");
/* 1088 */                       out.write("if($(row).find('input:checkbox:first').is(':checked'))\n{\n\tres_list=res_list+$(this).attr('groupid')+'|';  //No I18N\n}\n});\n}\nif($(this).attr('isopen')=='false'){ //No I18N\nvar row=$(this).closest('tr');//No I18N\nif($(row).find('input:checkbox:first').is(':checked')){//No I18N\nunopenresid=unopenresid+$(this).attr('resid')+'|'; //No I18N\nMGselected=\"true\";//No I18N\n}\n}\nelse if(MGselected==\"false\"){//No I18N\nvar row=$(this).closest('tr');//No I18N \nif($(row).find('input:checkbox:first').is(':checked')){\nMGselected=\"true\";//No I18N\n}\n}\n});\nif(unopenresid.length>1){\nunopenresid=unopenresid.substring(0,unopenresid.length-1);\n}\n");
/* 1089 */                       if (("true".equals(request.getParameter("uncategorize"))) || ("orphaned".equals(request.getParameter("retaintree")))) {
/* 1090 */                         out.write(" \nretaintree='orphaned'; //No I18N\n");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 1095 */                         out.write("\nfor(i=0; i <alldivs.length ; i++)\n{\n\t if((alldivs[i].id.indexOf(\"monitorHide\")) >= 0)//No I18N\n\t {\n\t   showdiv=document.getElementById(alldivs[i].id) ;\n\t   if(showdiv.style.display == 'inline')\n\t   {\n\t      ids=(alldivs[i].id).substring(11);//No I18N\n\t      if(retaintree==\"\")\n\t      retaintree=retaintree+ids;\n\t      else\n\t      retaintree=retaintree+\"$\"+ids;//No I18N\n\t   }\n\t }\n}\n\n");
/*      */                       }
/* 1097 */                       out.write("  \n  if(act.value=='Manage')\n  {\n    manageMO(unopenresid);\n  }\n  else if(act.value=='Unmanage')\n  {\n   unManageMO(unopenresid,res_list);\n  }\n  else if(act.value=='Delete')\n  {\n   deleteMO(unopenresid,MGselected);\n  }\n  else if(act.value=='Enable Actions')\n  {\n   enableactions(unopenresid);\n  }\n  else if(act.value=='Disable Actions')\n  {\n   disableactions(unopenresid);\n  }\n  else if(act.value=='updatepoll')\n  {\n   updatepolling('poll',unopenresid);//No I18N   \n  }\n  else if(act.value=='updateauth')\n  {\n   updatepolling('auth',unopenresid);//No I18N   \n  }\n  else if(act.value=='unManageAndReset')\n  {\n   unManageAndReset(unopenresid);//No I18N   \n  }\n  else if(act.value=='customField')\n  {\n\tassignCustomField(unopenresid);\n  }\n  else if(act.value=='editDisplayNames')\n  {\n\t  editDisplaynames();\n  }\n  \n  }\n  catch(err)\n  {\n  console.log('invokeOperations'+err); \n  }\n }\n \nfunction editDisplaynames(selectname)\n{\n\t\n\t ");
/* 1098 */                       if (_jspx_meth_logic_005fpresent_005f7(_jspx_page_context))
/*      */                         return;
/* 1100 */                       out.write(10);
/* 1101 */                       out.write(9);
/*      */                       
/* 1103 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1104 */                       _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 1105 */                       _jspx_th_logic_005fnotPresent_005f7.setParent(null);
/*      */                       
/* 1107 */                       _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 1108 */                       int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 1109 */                       if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                         for (;;) {
/* 1111 */                           out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert(\"");
/* 1112 */                           out.print(FormatUtil.getString("am.webclient.alert.displayname.select.text"));
/* 1113 */                           out.write("\");//No i18n\n\t\tselectname.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n    {\n\t\tvar temp = \"\";\n\t\ttemp = $.map($(\"input:checkbox[name='select']:checked\"), function(vals, i) // NO I18N\n\t\t{\n\t\t\treturn vals.value;\n\t\t}).join(',');\n\t\tvar vmResids='");
/* 1114 */                           out.print(request.getAttribute("VirtualMachines"));
/* 1115 */                           out.write("';\n\t\tdocument.deletemonitor.action=\"/jsp/EditDisplaynames.jsp?resids=\"+temp+\"&vmResids=\"+vmResids;\n\t\twindow.open(document.deletemonitor.action,'','resizable=yes,scrollbars=yes,width=780,height=300,top=200,left=200');\n\t}\n\t");
/* 1116 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 1117 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1121 */                       if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 1122 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/*      */                       }
/*      */                       else {
/* 1125 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 1126 */                         out.write("\n}\n \n  function updatepolling(action,unopengroups)\n  {\n  ");
/* 1127 */                         if (_jspx_meth_logic_005fpresent_005f8(_jspx_page_context))
/*      */                           return;
/* 1129 */                         out.write("\n  \t");
/*      */                         
/* 1131 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1132 */                         _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 1133 */                         _jspx_th_logic_005fnotPresent_005f8.setParent(null);
/*      */                         
/* 1135 */                         _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 1136 */                         int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 1137 */                         if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                           for (;;) {
/* 1139 */                             out.write("\n  \tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n  \t\t{\n  \t\t\tif(action=='poll')\n  \t\t\t{\n  \t\t\talert('");
/* 1140 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupview.polling.alert"));
/* 1141 */                             out.write("');//No I18N\n  \t\t\t}\n  \t\t\telse if(action=='auth')\n  \t\t\t{\n  \t\t\talert('");
/* 1142 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupview.userpass.alert"));
/* 1143 */                             out.write("');//No I18N\n  \t\t\t}\n  \t\t\tdocument.deletemonitor.operation.selectedIndex=0;\n  \t\t\treturn;\n  \t        }\n\t   var MGselected=\"false\";//No I18N\n\t   var ml=document.deletemonitor;\n\t   var d=new Date();\n\t   var temp='';\n\t\tfor (var i = 0; i < document.deletemonitor.elements.length; i++) {\n\t\t    var e = document.deletemonitor.elements[i];\n\t\t    if (ml.elements[i].name == 'select') {\n\t\t\t   if(ml.elements[i].checked==true){\n\t\t\t\tvar trAttributes = ml.elements[i].parentElement.parentElement.attributes;\n\t\t\t\tif(trAttributes != null)\n\t\t\t\t{\n\t\t\t\t\tvar len = trAttributes.length;\n\t\t\t\t\tfor(j=0;j<len;j++)\n\t\t\t\t\t{\n\t\t\t\t\t\tif(trAttributes[j].name == 'ischild' && trAttributes[j].value == 'true')\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\talert('");
/* 1144 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupview.childtype.operation.notallowed"));
/* 1145 */                             out.write("');//No I18N\n\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t if(temp=='')\n\t\t\t{\n\t\t\t\ttemp=temp+ml.elements[i].value;//No I18N\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\ttemp=temp+','+ml.elements[i].value;//No I18N\n\t\t\t}\n\t\t\t}\n\t\t    }\n\t\t}\n\n  \t  if(action=='poll')\n  \t  {\n  \t  action1='/jsp/ChangeBulkAuthentication.jsp?values='+temp+'&methodName=showMonitorGroupView&haid=");
/* 1146 */                             if (_jspx_meth_c_005fout_005f7(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context))
/*      */                               return;
/* 1148 */                             out.write("&networkName=null&groupName=All&polling=updatePolling&retaintree='+retaintree+\"&unopengroups=\"+unopengroups;//No I18N\n\t  MM_openBrWindow(action1+'&'+d,'secondWindow','resizable=yes,scrollbars=yes,width=450,height=150,top=300,left=300');//No I18N\n\t  }\n\t  else if(action=='auth')\n\t  {\n\t  action1='/jsp/ChangeBulkAuthentication.jsp?values='+temp+'&methodName=showMonitorGroupView&haid=");
/* 1149 */                             if (_jspx_meth_c_005fout_005f8(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context))
/*      */                               return;
/* 1151 */                             out.write("&networkName=null&groupName=All&haid=");
/* 1152 */                             if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context))
/*      */                               return;
/* 1154 */                             out.write("&retaintree='+retaintree+\"&unopengroups=\"+unopengroups;//No I18N\n\t  MM_openBrWindow(action1+'&'+d,'secondWindow','resizable=yes,scrollbars=yes,width=450,height=300,top=200,left=300');//No I18N\n\t  }\n\t  \t  document.deletemonitor.operation.selectedIndex=0;\n\t");
/* 1155 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 1156 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1160 */                         if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 1161 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/*      */                         }
/*      */                         else {
/* 1164 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 1165 */                           out.write("\n}\n  /************* To display the monitor groups based on viewtype ***** STARTS ***********/\n  function disableTreeView()\n  {\n\t\t//if viewtype=\"treeview\" display only in expanded mode\n\t\t//if viewtype=\"show\" display in any mode(expand or collapse mode)\n\t\t");
/* 1166 */                           request.setAttribute("viewtype", "show");
/*      */                           
/* 1168 */                           out.write("\n\t}\n/************* To display the monitor groups based on viewtype ******* ENDS *********/\n//set cookie function\n\tfunction setCookie(name,value,expdays)\n\t{\n      var expdate=new Date();\n      expdate.setDate(expdate.getDate() + expdays);\n      var val=escape(value) + ((expdays==null) ? \"\" : \"; expires=\"+expdate.toUTCString()); //No I18N\n      document.cookie=name + \"=\" + val;\n\t}\n//Get cookie function\n\tfunction getCookie(name)\n\t{\n      var i,x,y,arr=document.cookie.split(\";\");//No I18N\n      y = null;\n      for (i=0;i<arr.length;i++)\n      {\n        x=arr[i].substr(0,arr[i].indexOf(\"=\"));//No I18N\n        y=arr[i].substr(arr[i].indexOf(\"=\")+1);//No I18N\n        x=x.replace(/^\\s+|\\s+$/g,\"\");//No I18N\n        if (x==name)\n        {\n          return unescape(y);\n        }\n      }\n\t}\t\n \tfunction toggleallDivs(action,expandimage)\n    {\n \t\ttry{\n\tvar secondtime=$('#treelink').attr('already');//No I18N\t\n\tif($('#treelink').attr('already')=='false'){\n\topenAllGroups();\n\tsethovertomgaction();\t\n\t}\t\n \t\tvar newDisplay ;\n\t\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n");
/* 1169 */                           out.write("\t\telse newDisplay = \"table-row\"; //Netscape and Mozilla\n\t\tvar collapseid,actionid;\n\t\tvar plus,minus;\n\t\tvar alldivs =document.getElementsByTagName(\"div\");\n\t\tvar myaction='';\n\t\tif(action==\"treeview\"){//No I18N\n\t\t\tdocument.getElementById('showall').style.display=\"none\";//No I18N\n\t\t\tdocument.getElementById('hideall').style.display=\"inline\";//No I18N\n\t\t\tmyaction='hideall';//No I18N\n\t\t}\n\t\telse if(action==\"showall\" || action==\"hideall\"){\n\t\t\tif(action==\"showall\"){//No I18N\n\t\t\t\tnewDisplay=\"none\";//No I18N\n\t\t\t\tdocument.getElementById('showall').style.display=\"inline\";//No I18N\n\t\t\t\tdocument.getElementById('hideall').style.display=\"none\";//No I18N\n\t\t\t\tsetCookie('actionid','showall'); //No I18N\n\t\t\t\tmyaction='showall';//No I18N\n\t\t\t}\n\t\t\telse{\n\t\t\t\tdocument.getElementById('showall').style.display=\"none\";\n\t\t\t\tdocument.getElementById('hideall').style.display=\"inline\";\n\t\t\t\tsetCookie('actionid','hideall'); //No I18N\n\t\t\t\tmyaction='hideall';//No I18N\n\t\t\t}\n\t\t}\n\t\telse\n\t\t{\n\t\t\tcollapseid= document.getElementById(\"showall\");//No I18N\n\t\t\tif(collapseid.style.display==\"inline\" )\n");
/* 1170 */                           out.write("\t\t\t{\n\t\t\t\tdocument.getElementById('showall').style.display=\"none\";//No I18N\n\t\t\t\tdocument.getElementById('hideall').style.display=\"inline\";//No I18N\n\t\t\t\tsetCookie('actionid','hideall'); //No I18N\n\t\t\t\tmyaction='hideall';//No I18N\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t\tnewDisplay=\"none\";//No I18N\n\t\t\t\t\tdocument.getElementById('showall').style.display=\"inline\";//No I18N\n\t\t\t\t\tdocument.getElementById('hideall').style.display=\"none\";//No I18N\n\t\t\t\t\tsetCookie('actionid','showall'); //No I18N\n\t\t\t\t\tmyaction='showall'; //No I18N\n\t\t\t}\n\t\t}\n\t\tif(action==\"treeview\"){\n\t\t\tplus=\"none\";//No I18N\n\t\t\tminus=\"inline\"//No I18N\n\t\t}\n\t\telse{\n\t\t\tif(newDisplay=='none')\n\t\t\t{\n\t\t\t\tplus=\"inline\";//No I18N\n\t\t\t\tminus=\"none\"//No I18N\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tplus=\"none\";//No I18N\n\t\t\t\tminus=\"inline\"//No I18N\n\t\t\t}\n\t\t}\t\t\n\t\tif(newDisplay==\"block\" || newDisplay==\"table-row\")\n\t\t{\n\t\tif(expandimage=='true')\n\t\t{\n\t\t$(\"#allMonitorGroups tr[id^=#monitor][toplevel!=true]\").css(\"display\",newDisplay);//No I18N\n\t\t}\n\t\telse{\n        if(secondtime=='true')\t{\t\n\t\t$('.expandlink').each(function(){\n");
/* 1171 */                           out.write("\t\tvar row=$(this).closest('tr');//No I18N\t\n\t\tvar parenttr = $(row).attr(\"toplevel\");//No I18N\t\t\n\t\tif(parenttr==\"true\"){\t//No I18N\t\n\t\tif(myaction=='hideall'){\t\t\n\t\tvar groupid=$(row).attr(\"groupid\");//No I18N\t\n\t\tvar monhyde=$('#monitorHide'+groupid).css(\"display\");//No I18N\t \t\n\t\tif(monhyde=='none'){\n\t   toggleChildMos('#monitor'+groupid,this);\t//No I18N   \n\t   toggleTreeImage(groupid); //No I18N \n\t   }\n\t\t}\n\t\telse{\n\t\ttoggleChildMos('#monitor'+groupid,this);//No I18N\t\t   \n\t   toggleTreeImage(groupid);//No I18N\t\n\t\t}\n\t       }\t\n\t });\t\t\n\t\t}\n\t\treturn false;\n\t\t}\n\t\t}\n\t\telse{\n\t\t$(\"#allMonitorGroups tr[id^=#monitor][toplevel!=true]\").css(\"display\",newDisplay);//No I18N\n\t\t}\n\t\tif(expandimage=='true'){\t\t\n\t\tfor(var j=0; j < alldivs.length; j++)\n\t\t{\n\t\t\tvar singlediv = alldivs[j];\n\t\t\tvar id = singlediv.id;\n\t\t\tif(id.indexOf(\"monitorHide\")>=0)//No I18N\n\t\t\t{\n\t\t\t\tsinglediv.style.display =minus ;\n\t\t\t}\n\t\t\tif(id.indexOf(\"monitorShow\")>=0)//No I18N\n\t\t\t{\n\t\t\t\tsinglediv.style.display =plus;\n\t\t\t}\n\t\t}\n\t\t}\nelse{\n$('.expandlink').each(function(){\n");
/* 1172 */                           out.write("if($(this).attr('isopen')=='true'){ //No I18N\nvar row=$(this).closest('tr');//No I18N\n$(row).find(\"[id^=monitorHide]\").css(\"display\",minus);//No I18N \n$(row).find(\"[id^=monitorShow]\").css(\"display\",plus);//No I18N \n}\n});\n\n}\t\t\n \t\t}\n \t\tcatch(err)\n \t\t{\n \t\t\tconsole.log('err in toggleallDivs '+err); \t\t\t\n \t\t}\n \t}\n\tfunction sethovertomgaction(){\n\ttry{\t  \n\t\t $(\"#allMonitorGroups tr div[id^=mgaction]\").css('display','none');//No I18N\n                                 $(\"#allMonitorGroups tr[id^=#monitor]\").hover(function() {//No I18N\n\n                                        $(this).find(\"div[id^=mgaction]\").css('display','inline'); //No I18N\n\n                                },\n                                function () {\n                                          $(this).find(\"div[id^=mgaction]\").css('display','none'); //No I18N\n\n                                });\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\tcatch(err){\n\t\t\t\t\t\t\t\tconsole.log(err);\n\t\t\t\t\t\t\t\t}\n\t\n\t}\nfunction openAllGroups()\n{\ntry{\n$('.expandlink').each(function(){\nif(this.getAttribute('isopen')=='false'){\n");
/* 1173 */                           out.write("var parenttr = $(this).parent().parent().attr(\"toplevel\");//No I18N\nif(parenttr==\"true\"){\nvar row=$(this).closest('tr');//No I18N \nvar groupid=$(row).attr(\"groupid\");//No I18N\nchrcked=\"false\";\nif ( $(row).find('input:checkbox:first').is(':checked') ) {\nchrcked=\"true\"; //No I18N\n}\nvar url='/showresource.do?method=showMonitorGroupViewIndividual&parents='+groupid+'&lindx=1&pageno=1&alllevels=false&checked='+chrcked;//No I18N\ngetDynamicCall(url,this); //No I18N\n$(this).attr('isopen','true');//No I18N\ntoggleTreeImage(groupid);\n}\n}\n}\n);\n$('#treelink').attr(\"already\",'true');//No I18N\n}\ncatch(err)\n{\nconsole.log('openAllGroups    '+err);//No I18N\n}\n}\nvar rows;\nvar rowcount,start;\nvar idtotoggle;\nvar toggletype;\nfunction toggleChildMos(tempidtotoggle,obj)\n{\ntry{\n\tvar isopen=obj.getAttribute(\"isopen\");\t//No I18N\n\tif(isopen==\"false\"){//No I18N\n\tobj.setAttribute(\"isopen\",\"true\");//No I18N\n\treturn ;\n\t}\n\tidtotoggle=tempidtotoggle;\t\n\tvar table = document.getElementById(\"allMonitorGroups\");//No I18N\t\n\trows = table.rows;\n\trowcount = rows.length;\t\n");
/* 1174 */                           out.write("\tfor( i=2;i<rowcount;i++)\n\t{\n\t\tvar myrow = rows[i];\n\t\tif(myrow.id==idtotoggle)\n\t\t{\n\t\t\tif(rows[i].style.display=='none')\n\t\t\t{\n\t\t\t    toggletype='none';\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t    toggletype='block';\n\t\t\t}\n\t\t\tbreak;//No I18N\n\t\t}\n\t}\n\tif(toggletype=='none')\n\t{\n\t  slideDown();\n\t}\n\telse\n\t{\n\thideOtherRows();\n\t}\n        return false;\n\t\t}\n\t\tcatch(err) \n\t\t{\n\t\tconsole.log('toggleChildMos err'+err); \n\t\t}\n}\n\nfunction hideOtherRows()\n{\n\n         var newDisplay = \"block\";//No I18N\n\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n         else newDisplay = \"table-row\"; //Netscape and Mozilla\n\tvar i;\n\tfor(i=2;i<rowcount;i++)\n\t{\n\tif(rows[i].id.indexOf( idtotoggle)!=-1)\n\t{\n\n\t\trows[i].style.display = \"none\";//No I18N\n\t}\n\n\t}\n\treturn;\n\n}\n\n\n\n $(function() {\n         $('#allMonitorGroups tr').mouseover(function() {   //No I18N\n            $(this).addClass('monitorgpHeaderHover'); //No I18N\n         }).mouseout(function() {\n            $(this).removeClass('monitorgpHeaderHover');//No I18N\n         });\n      });\n\n\n\n\nfunction slideDown()\n");
/* 1175 */                           out.write("{\n\t  var newDisplay = \"block\";//No I18N\n\t\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n\t        else newDisplay = \"table-row\"; //Netscape and Mozilla\n\t\tvar i;\n\t\tfor(i=2;i<rowcount;i++)\n\t\t{\n\t\tif(rows[i].id == idtotoggle)\n\t\t{\n\n\t\t\trows[i].style.display = newDisplay;//No I18N\n\t\t\trows[i].removeAttribute(\"style\");//No I18N\n\t\t\trows[i].className = \"leftcells\";//No I18N\n\t\t}\n\t\telse\n\t\t{\n\t\trows[i].style.background = \"#FFFFFF\";//No I18N\n\t\t}\n\t        }\n\treturn;\n\n}\nfunction toggleTreeImage(divname)\n{\ntry{\n\t var hide1=\"monitorHide\"+divname;\n\t var show1=\"monitorShow\"+divname;\n\t if(document.getElementById(show1))\n\t {\n\t if(document.getElementById(show1).style.display == 'inline')\n\t {\n\t //if it is to show the child elements just return changing the image of current monitor group level and return\n\t document.getElementById(show1).style.display='none';\n\t document.getElementById(hide1).style.display='inline';\n\t return;\n\t }\n\t }\n\t else\n\t {\n\t return;\n\t }\n\t //else if it is to hide an monitor group then parse through all the child elements and find subgroups and change the images to minus\n");
/* 1176 */                           out.write("\t  var alldivs =document.getElementsByTagName(\"div\");//No I18N\n\t     var i;\n\t     for(i=0; i <alldivs.length ; i++)\n             {\n\t                if((alldivs[i].id.indexOf(hide1)) >= 0)\n\t                {\n\t                hidediv=document.getElementById(alldivs[i].id) ;\n\t                if(hidediv)\n\t                {\n\t                if(hidediv.style.display == 'inline')\n\t\t\thidediv.style.display='none';//No I18N\n\t\t\t}\n                        }\n                        if((alldivs[i].id.indexOf(show1)) >= 0)\n\t\t\t{\n\t\t\tvar showdiv;\n\t\t\tshowdiv=document.getElementById(alldivs[i].id) ;\n\t\t\tif(showdiv)\n\t\t\t{\n\t\t\tif(showdiv.style.display == 'none')\n\t\t\tshowdiv.style.display='inline';\n\t\t\t}\n                        }\n            }\n\t\t\t}\n\t\t\tcatch(err)\n\t\t\t{\n\t\t\tconsole.log('err in toggleTreeImage '+err);\n\t\t\t}\n}\n \n \nfunction getDynamicCall(url,obj,sethover) \n{\ntry{\nvar isopen=obj.getAttribute(\"isopen\");//No I18N\nvar inprogress=\"false\";//No I18N\nif(isopen==\"true\" ){//No I18N\nreturn ;\n }\nvar row = $(obj).parent().parent();\nvar indx =row.index();\n");
/* 1177 */                           out.write("var chrcked=\"false\";//No I18N\nif ( $(row).find('input:checkbox:first').is(':checked') ) {//No I18N\nchrcked=\"true\"; //No I18N\n}//No I18N\nurl=url+'&checked='+chrcked;//No I18N\n $.ajax({\n  type:'POST',//No I18N \n  url: url,\n  async:false,\n  success: function(data){\n  $(document).ready(function() {\n  $('#allMonitorGroups > tbody > tr').eq(indx).after(data);   //No I18N\n  }); \n              }\n\t\t\t   });\n   if(sethover==\"true\"){//No I18N \n\t sethovertomgaction();\n\t }\n}\ncatch(err){\nconsole.log('getDynamicCall'+err);//No I18N \n}\n}\n\nfunction showmoremonitors(url,obj,sethover) \n{\ntry{\nvar isopen=obj.getAttribute(\"isopen\");//No I18N\nvar inprogress=\"false\";//No I18N\nif(isopen==\"true\" ){\nreturn ;\n } \nvar row = $(obj).closest('tr');//No I18N \nvar td1=$(obj).closest('td');//No I18N \n$(obj).remove();\n$(td1).html(\"<span class='fetching-tag'>");
/* 1178 */                           out.print(FormatUtil.getString("am.webclient.as400.fetch.wait"));
/* 1179 */                           out.write("</span>\");\nvar checked=findparentselected(row);\nurl=url+'&checked='+checked;//No I18N \nvar indx =row.index();\n $.ajax({\n  type:'POST',//No I18N \n  url: url,\n  async:false,\n  success: function(data){\n  $(document).ready(function() {\n  $('#allMonitorGroups > tbody > tr').eq(indx).after(data);   \n  }); \n              }\n\t\t\t   });\n   if(sethover==\"true\"){//No I18N\n\t sethovertomgaction();\n\t }\t \n\t $(row).remove();\n}\ncatch(err){\nconsole.log('showmoremonitors'+err);//No I18N \n}\n}\nfunction findparentselected(row){\nvar checked1=\"false\";//No I18N \ntry{\nvar rowid=$(row).attr('id');//No I18N \nvar groupid11='';//No I18N \nif (rowid.indexOf(\"|\") >= 0)\n{\ngroupid11=rowid.substring(rowid.lastIndexOf('|')+1,rowid.length);\n}\nelse{\ngroupid11=groupid11=rowid.substring(rowid.lastIndexOf('r')+1,rowid.length);\n}\n$('#allMonitorGroups > tbody  > tr').each(function() {                     \n                     if($(this).attr(\"isgroup\")==\"true\"){\t\t\t\t\t \n                     if ( $(this).find('input:checkbox:first').is(':checked') && $(this).attr(\"groupid\")==groupid11) {//No I18N\t\t\t\t\t \n");
/* 1180 */                           out.write("                         checked1=\"true\"; \n\t\t\t\t\t\t return false;\n                      }\n\t\t\t\t\t\t }\t\t\t\t\t\t \n\t\t\t\t\t\t }\n\t\t\t\t\t\t );\t\n\t\treturn checked1;\t\t\t\t \n}\ncatch(err){\nconsole.log('error in findparentselected'+err);//No I18N\n}\nreturn \"false\";\n}\nfunction getDynamicCall11(url,row) \n{\ntry{\nvar inprogress=\"false\";//No I18N \nvar indx=row.index();\n\n $.ajax({\n  type:'POST',//No I18N \n  url: url,\n  async:false,\n  success: function(data){\n  $('#allMonitorGroups > tbody > tr').eq(indx).after(data);  \n              }\n\t\t\t   });\n}\ncatch(err){\nconsole.log('getDynamicCall11'+err); \n}\n}\n</script>\n\n");
/* 1181 */                           if ((request.getAttribute("treewithLayout") != null) && (((String)request.getAttribute("treewithLayout")).equals("true"))) {
/* 1182 */                             out.write(10);
/* 1183 */                             if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */                               return;
/* 1185 */                             out.write(10);
/*      */                             
/* 1187 */                             String title = FormatUtil.getString("am.webclient.monitorgroupview.title");
/* 1188 */                             String toAppendLink = "";
/* 1189 */                             if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 1190 */                               out.write(10);
/* 1191 */                               out.write("<!--$Id$-->\n\n<script>\nvar urlredirect = new Array();\nurlredirect[0] = '/showresource.do?method=showResourceTypesAll&group=All");
/* 1192 */                               out.print(toAppendLink);
/* 1193 */                               out.write("';\n</script>\n");
/*      */                               
/* 1195 */                               if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1196 */                                 out.write("\n  <script>\n     urlredirect[0]='showresource.do?method=showResourceTypes&detailspage=true&network=MSSQL-DB-server&viewmontype=MSSQL-DB-server");
/* 1197 */                                 out.print(toAppendLink);
/* 1198 */                                 out.write("';\n </script>\n ");
/*      */                               }
/*      */                               
/* 1201 */                               out.write("\n <script>\nurlredirect[1] = '/showresource.do?method=showResourceTypes");
/* 1202 */                               out.print(toAppendLink);
/* 1203 */                               out.write("&monitor_viewtype=categoryview';\nurlredirect[2] = '/showresource.do?method=showPlasmaView';\nurlredirect[3] = '/showresource.do?method=showMonitorGroupView';\nurlredirect[4] = '/showresource.do?method=showGMapView&group=All");
/* 1204 */                               out.print(toAppendLink);
/* 1205 */                               out.write("';\nurlredirect[5] = '/showresource.do?method=showIconsView");
/* 1206 */                               out.print(toAppendLink);
/* 1207 */                               out.write("';\nurlredirect[6] = '/showresource.do?method=showDetailsView");
/* 1208 */                               out.print(toAppendLink);
/* 1209 */                               out.write("';\n\n</script>\n\n\n\n\n");
/*      */                               
/* 1211 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 1212 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 1213 */                               _jspx_th_html_005fform_005f0.setParent(null);
/*      */                               
/* 1215 */                               _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */                               
/* 1217 */                               _jspx_th_html_005fform_005f0.setStyle("display :inline");
/* 1218 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 1219 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 1221 */                                   out.write(10);
/* 1222 */                                   if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 1224 */                                   out.write(10);
/* 1225 */                                   if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 1227 */                                   out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"62%\" height=\"35\" class=\"monitorsheading\">\n      <table width=\"100%\">\n      <tr>\n        <td  height=\"35\"   width=\"70%\" class=\"monitorsheading\"> ");
/* 1228 */                                   out.print(title);
/* 1229 */                                   out.write(" </td>\n        <td  height=\"35\" class=\"monitorsheading\"  style=\"padding-left:2px\">\n    ");
/* 1230 */                                   String CategoryViewtype = (String)request.getAttribute("categorytype");
/* 1231 */                                   if (CategoryViewtype == null) {
/* 1232 */                                     CategoryViewtype = "";
/*      */                                   }
/* 1234 */                                   String monitorviewtype = (String)request.getAttribute("monitor_viewtype");
/* 1235 */                                   if (monitorviewtype == null) {
/* 1236 */                                     monitorviewtype = "";
/*      */                                   }
/* 1238 */                                   if (monitorviewtype.startsWith("CategoryView")) {
/* 1239 */                                     if (CategoryViewtype.equals("added monitors"))
/*      */                                     {
/* 1241 */                                       out.write("          <a  href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\"  class=\"staticlinks\" onclick=\"javascript:setCookieval('showAddedMonitors');\">");
/* 1242 */                                       out.print(FormatUtil.getString("am.monitortab.category.AddedMonitors.text"));
/* 1243 */                                       out.write("</a>\n  ");
/*      */ 
/*      */                                     }
/* 1246 */                                     else if (CategoryViewtype.equals("all monitors"))
/*      */                                     {
/* 1248 */                                       out.write("            <a href=\"/showresource.do?method=showResourceTypes\"   class=\"staticlinks\" onclick=\"javascript:setCookieval('showAllMonitors');\">");
/* 1249 */                                       out.print(FormatUtil.getString("am.monitortab.category.AllMonitors.text"));
/* 1250 */                                       out.write("</a>\n\n  ");
/*      */                                     }
/*      */                                   }
/*      */                                   
/*      */ 
/* 1255 */                                   out.write("\n        </td>\n      </tr>\n      </table>\n    </td>\n    ");
/*      */                                   
/* 1257 */                                   String tempStl = "center";
/* 1258 */                                   if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                   {
/* 1260 */                                     tempStl = "right";
/*      */                                     
/* 1262 */                                     out.write("\n      <td align=\"right\" width=\"30%\" class=\"bodytext\" style=\"white-space:nowrap;\">\n      ");
/* 1263 */                                     if (PluginUtil.isPlugin()) {
/* 1264 */                                       out.write("\n      ");
/*      */                                       
/* 1266 */                                       PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1267 */                                       _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 1268 */                                       _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 1270 */                                       _jspx_th_logic_005fpresent_005f9.setRole("ADMIN");
/* 1271 */                                       int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 1272 */                                       if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                         for (;;) {
/* 1274 */                                           out.write("\n        <span id=\"createNewMG\">");
/* 1275 */                                           out.print(FormatUtil.getString("am.monitortab.creategroup.text"));
/* 1276 */                                           out.write(" :  &nbsp;</span>\n        <select id=\"createMG\" onchange=\"javascript:changeMGURL(this)\" styleClass=\"formtext\" style=\"margin-right: 30px;display:none;\">\n          <option value=\"createNewMG\" selected>");
/* 1277 */                                           out.print(FormatUtil.getString("am.monitortab.selectgrouptype.text"));
/* 1278 */                                           out.write("</option>\n          <option value=\"1\">");
/* 1279 */                                           out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 1280 */                                           out.write("</option>\n          <option value=\"2\">");
/* 1281 */                                           out.print(FormatUtil.getString("am.webclient.mg.type.webappgroup"));
/* 1282 */                                           out.write("</option>\n          <option value=\"3\">");
/* 1283 */                                           out.print(FormatUtil.getString("am.webclient.mg.type.vcenter"));
/* 1284 */                                           out.write("</option>\n        </select>\n      ");
/* 1285 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 1286 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 1290 */                                       if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 1291 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                                       }
/*      */                                       
/* 1294 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 1295 */                                       out.write("\n     ");
/*      */                                     }
/* 1297 */                                     out.write("\n\n      ");
/* 1298 */                                     out.print(FormatUtil.getString("am.monitortab.selectview.text"));
/* 1299 */                                     out.write(" :  &nbsp;\n\n      ");
/*      */                                     
/* 1301 */                                     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 1302 */                                     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1303 */                                     _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 1305 */                                     _jspx_th_html_005fselect_005f0.setProperty("defaultmonitorsview");
/*      */                                     
/* 1307 */                                     _jspx_th_html_005fselect_005f0.setOnchange("javascript:changeUrl(this)");
/*      */                                     
/* 1309 */                                     _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/* 1310 */                                     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1311 */                                     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1312 */                                       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1313 */                                         out = _jspx_page_context.pushBody();
/* 1314 */                                         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 1315 */                                         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1318 */                                         out.write("\n        ");
/*      */                                         
/* 1320 */                                         OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1321 */                                         _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 1322 */                                         _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                         
/* 1324 */                                         _jspx_th_html_005foption_005f0.setValue("showResourceTypesAll");
/* 1325 */                                         int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 1326 */                                         if (_jspx_eval_html_005foption_005f0 != 0) {
/* 1327 */                                           if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1328 */                                             out = _jspx_page_context.pushBody();
/* 1329 */                                             _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 1330 */                                             _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 1333 */                                             out.print(FormatUtil.getString("am.monitortab.bulkconfiguration.text"));
/* 1334 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 1335 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 1338 */                                           if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1339 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 1342 */                                         if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 1343 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                         }
/*      */                                         
/* 1346 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 1347 */                                         out.write("\n        ");
/*      */                                         
/* 1349 */                                         OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1350 */                                         _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 1351 */                                         _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                         
/* 1353 */                                         _jspx_th_html_005foption_005f1.setValue("showResourceTypes");
/* 1354 */                                         int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 1355 */                                         if (_jspx_eval_html_005foption_005f1 != 0) {
/* 1356 */                                           if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1357 */                                             out = _jspx_page_context.pushBody();
/* 1358 */                                             _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 1359 */                                             _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 1362 */                                             out.print(FormatUtil.getString("am.monitortab.category.text"));
/* 1363 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 1364 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 1367 */                                           if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1368 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 1371 */                                         if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 1372 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                         }
/*      */                                         
/* 1375 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 1376 */                                         out.write("\n        ");
/*      */                                         
/* 1378 */                                         OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1379 */                                         _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 1380 */                                         _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                         
/* 1382 */                                         _jspx_th_html_005foption_005f2.setValue("plasmaView");
/* 1383 */                                         int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 1384 */                                         if (_jspx_eval_html_005foption_005f2 != 0) {
/* 1385 */                                           if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1386 */                                             out = _jspx_page_context.pushBody();
/* 1387 */                                             _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 1388 */                                             _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 1391 */                                             out.print(FormatUtil.getString("am.monitortab.plasmaview.text"));
/* 1392 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 1393 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 1396 */                                           if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1397 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 1400 */                                         if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 1401 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                         }
/*      */                                         
/* 1404 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 1405 */                                         out.write("\n        ");
/*      */                                         
/* 1407 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f9 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1408 */                                         _jspx_th_logic_005fnotPresent_005f9.setPageContext(_jspx_page_context);
/* 1409 */                                         _jspx_th_logic_005fnotPresent_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                         
/* 1411 */                                         _jspx_th_logic_005fnotPresent_005f9.setRole("OPERATOR");
/* 1412 */                                         int _jspx_eval_logic_005fnotPresent_005f9 = _jspx_th_logic_005fnotPresent_005f9.doStartTag();
/* 1413 */                                         if (_jspx_eval_logic_005fnotPresent_005f9 != 0) {
/*      */                                           for (;;) {
/* 1415 */                                             out.write("\n        ");
/*      */                                             
/* 1417 */                                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1418 */                                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 1419 */                                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                             
/* 1421 */                                             _jspx_th_html_005foption_005f3.setValue("showMonitorGroupView");
/* 1422 */                                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 1423 */                                             if (_jspx_eval_html_005foption_005f3 != 0) {
/* 1424 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 1425 */                                                 out = _jspx_page_context.pushBody();
/* 1426 */                                                 _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 1427 */                                                 _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 1430 */                                                 out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 1431 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 1432 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 1435 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 1436 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 1439 */                                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 1440 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                             }
/*      */                                             
/* 1443 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 1444 */                                             out.write("\n        ");
/*      */                                             
/* 1446 */                                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1447 */                                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 1448 */                                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                             
/* 1450 */                                             _jspx_th_html_005foption_005f4.setValue("showGMapView");
/* 1451 */                                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 1452 */                                             if (_jspx_eval_html_005foption_005f4 != 0) {
/* 1453 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 1454 */                                                 out = _jspx_page_context.pushBody();
/* 1455 */                                                 _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 1456 */                                                 _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 1459 */                                                 out.print(FormatUtil.getString("am.monitortab.gmap.text"));
/* 1460 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 1461 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 1464 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 1465 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 1468 */                                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 1469 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                             }
/*      */                                             
/* 1472 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 1473 */                                             out.write("\n        ");
/* 1474 */                                             if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1475 */                                               out.write("\n        ");
/*      */                                               
/* 1477 */                                               OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1478 */                                               _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 1479 */                                               _jspx_th_html_005foption_005f5.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                               
/* 1481 */                                               _jspx_th_html_005foption_005f5.setValue("showIconsView");
/* 1482 */                                               int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 1483 */                                               if (_jspx_eval_html_005foption_005f5 != 0) {
/* 1484 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 1485 */                                                   out = _jspx_page_context.pushBody();
/* 1486 */                                                   _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 1487 */                                                   _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 1490 */                                                   out.print(FormatUtil.getString("am.monitortab.icons.text"));
/* 1491 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 1492 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 1495 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 1496 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 1499 */                                               if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 1500 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                               }
/*      */                                               
/* 1503 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 1504 */                                               out.write("\n        ");
/*      */                                               
/* 1506 */                                               OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1507 */                                               _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 1508 */                                               _jspx_th_html_005foption_005f6.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                               
/* 1510 */                                               _jspx_th_html_005foption_005f6.setValue("showDetailsView");
/* 1511 */                                               int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 1512 */                                               if (_jspx_eval_html_005foption_005f6 != 0) {
/* 1513 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 1514 */                                                   out = _jspx_page_context.pushBody();
/* 1515 */                                                   _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 1516 */                                                   _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 1519 */                                                   out.print(FormatUtil.getString("am.monitortab.table.text"));
/* 1520 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 1521 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 1524 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 1525 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 1528 */                                               if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 1529 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                               }
/*      */                                               
/* 1532 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 1533 */                                               out.write("\n        ");
/*      */                                             }
/* 1535 */                                             out.write("\n        ");
/*      */                                             
/* 1537 */                                             OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1538 */                                             _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 1539 */                                             _jspx_th_html_005foption_005f7.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                             
/* 1541 */                                             _jspx_th_html_005foption_005f7.setValue("showFlashView");
/* 1542 */                                             int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 1543 */                                             if (_jspx_eval_html_005foption_005f7 != 0) {
/* 1544 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 1545 */                                                 out = _jspx_page_context.pushBody();
/* 1546 */                                                 _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 1547 */                                                 _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 1550 */                                                 out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/* 1551 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 1552 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 1555 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 1556 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 1559 */                                             if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 1560 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                             }
/*      */                                             
/* 1563 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 1564 */                                             out.write("\n        ");
/* 1565 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f9.doAfterBody();
/* 1566 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 1570 */                                         if (_jspx_th_logic_005fnotPresent_005f9.doEndTag() == 5) {
/* 1571 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9); return;
/*      */                                         }
/*      */                                         
/* 1574 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9);
/* 1575 */                                         out.write("\n      ");
/* 1576 */                                         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 1577 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1580 */                                       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1581 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1584 */                                     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 1585 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                     }
/*      */                                     
/* 1588 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 1589 */                                     out.write("\n\n      ");
/* 1590 */                                     if (!PluginUtil.isPlugin()) {
/* 1591 */                                       out.write("\n      <span class=\"bodytext\">\n        ");
/*      */                                       
/* 1593 */                                       NotPresentTag _jspx_th_logic_005fnotPresent_005f10 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1594 */                                       _jspx_th_logic_005fnotPresent_005f10.setPageContext(_jspx_page_context);
/* 1595 */                                       _jspx_th_logic_005fnotPresent_005f10.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 1597 */                                       _jspx_th_logic_005fnotPresent_005f10.setRole("OPERATOR");
/* 1598 */                                       int _jspx_eval_logic_005fnotPresent_005f10 = _jspx_th_logic_005fnotPresent_005f10.doStartTag();
/* 1599 */                                       if (_jspx_eval_logic_005fnotPresent_005f10 != 0) {
/*      */                                         for (;;) {
/* 1601 */                                           out.write("\n          ");
/*      */                                           
/* 1603 */                                           IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1604 */                                           _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 1605 */                                           _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fnotPresent_005f10);
/*      */                                           
/* 1607 */                                           _jspx_th_c_005fif_005f10.setTest("${globalconfig['defaultmonitorsview'] != requestScope.defaultview}");
/* 1608 */                                           int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 1609 */                                           if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                             for (;;) {
/* 1611 */                                               out.write("\n            <input type=hidden name=\"method\" value=\"setDefaultMonitorsView\">\n        <a href=\"javascript:setMonitorsViewDefault()\" class=\"new-monitordiv-link\">");
/* 1612 */                                               out.print(FormatUtil.getString("am.monitortab.setasdefaultview.text"));
/* 1613 */                                               out.write(" </a>\n          ");
/* 1614 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 1615 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 1619 */                                           if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 1620 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                           }
/*      */                                           
/* 1623 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1624 */                                           out.write("\n        ");
/* 1625 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f10.doAfterBody();
/* 1626 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 1630 */                                       if (_jspx_th_logic_005fnotPresent_005f10.doEndTag() == 5) {
/* 1631 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f10); return;
/*      */                                       }
/*      */                                       
/* 1634 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f10);
/* 1635 */                                       out.write("\n      </span>\n      ");
/*      */                                     }
/* 1637 */                                     out.write("\n      </td>\n      ");
/*      */                                   }
/*      */                                   
/* 1640 */                                   out.write("\n      ");
/*      */                                   
/* 1642 */                                   String location = (String)pageContext.getAttribute("setdefaultlocation");
/* 1643 */                                   if ((location != null) && (location.equals("Googleview")) && (request.getAttribute("key_set") != null) && (request.getAttribute("key_set").equals("true")))
/*      */                                   {
/* 1645 */                                     out.write("\n      <td colspan=\"2\" align=\"");
/* 1646 */                                     out.print(tempStl);
/* 1647 */                                     out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 1648 */                                     out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" class=\"staticlinks\">");
/* 1649 */                                     out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 1650 */                                     out.write("</a>\n       </span>\n\t  </td> \n      ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 1654 */                                   out.write("\n      ");
/*      */                                   
/* 1656 */                                   IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1657 */                                   _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 1658 */                                   _jspx_th_c_005fif_005f11.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 1660 */                                   _jspx_th_c_005fif_005f11.setTest("${AMActionForm.showMapView == true}");
/* 1661 */                                   int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 1662 */                                   if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                     for (;;) {
/* 1664 */                                       out.write("\n      <td colspan=\"2\" align=\"");
/* 1665 */                                       out.print(tempStl);
/* 1666 */                                       out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 1667 */                                       out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" id=\"savezoomlevel\" class=\"staticlinks\">");
/* 1668 */                                       out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 1669 */                                       out.write("</a>\n       </span>\n\t  </td> \n      ");
/* 1670 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 1671 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1675 */                                   if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 1676 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                   }
/*      */                                   
/* 1679 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1680 */                                   out.write("\n  </tr>\n</table>\n");
/* 1681 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 1682 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1686 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 1687 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 1690 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 1691 */                               out.write("\n\n\n<SCRIPT LANGUAGE=\"Javascript1.2\">\nvar defaultview = \"");
/* 1692 */                               out.print(request.getAttribute("defaultview"));
/* 1693 */                               out.write("\";\nif(defaultview == \"showMonitorGroupView\")//No I18N\n{\n  $('#createMG').show();//No I18N\n  $('#createNewMG').show();//No I18N\n}\nelse\n{\n  $('#createMG').hide();//No I18N\n  $('#createNewMG').hide();//No I18N\n}\n//Set cookie function\nfunction setCookie(name,value,expdays)\n{\n       var expdate=new Date();   //No I18N\n       expdate.setDate(expdate.getDate() + expdays);\n       var val=escape(value) + ((expdays==null) ? \"\" : \"; expires=\"+expdate.toUTCString());  //No I18N\n       document.cookie=name + \"=\" + val;   //No I18N\n}\n\n// Get cookie function\nfunction getCookie(name)\n{\n       var i,x,y,arr=document.cookie.split(\";\");   //No I18N\n       y = null;\n       for (i=0;i<arr.length;i++)\n       {\n         x=arr[i].substr(0,arr[i].indexOf(\"=\"));   //No I18N\n         y=arr[i].substr(arr[i].indexOf(\"=\")+1);   //No I18N\n         x=x.replace(/^\\s+|\\s+$/g,\"\");   //No I18N\n         if (x==name)\n         {\n           return unescape(y);\n         }\n       }\n}\nfunction setCookieval(Category_type){\n  //alert(Category_type);\n  if(Category_type==\"showAddedMonitors\"){\n");
/* 1694 */                               out.write("    setCookie('Category_type','showAddedMonitors');  //No I18N\n  }\n  else{\n    setCookie('Category_type','showAllMonitors');  //No I18N\n  }\n}\n\nfunction setMonitorsViewDefault() {\n");
/* 1695 */                               if (_jspx_meth_logic_005fpresent_005f10(_jspx_page_context))
/*      */                                 return;
/* 1697 */                               out.write(10);
/* 1698 */                               out.write(32);
/* 1699 */                               out.write(32);
/* 1700 */                               if (_jspx_meth_logic_005fnotPresent_005f11(_jspx_page_context))
/*      */                                 return;
/* 1702 */                               out.write("\n}\n\nfunction changeMGURL(a)\n{\n  var er = /^[0-9]+$/;\n  if(!er.test(a.value))\n  {\n    return;\n  }\n  location.href = '/admin/createapplication.do?method=createapp&grouptype='+a.value;\n}\n\nfunction changeUrl(a)\n{\n\t if(a.selectedIndex == 2 || a.selectedIndex == 7)\n\t {\n \t\tvar url = urlredirect[2];\n \t\tvar windowOpenOptions='scrollbars=1,resizable=1,width=900,height=650,left=50,screenX=50,screenY=25,top=25';\n \t\tvar name = \"PlasmaView\"; // NO I18N\n \t\t\n \t\tif(a.selectedIndex == 7)\n \t\t{\n \t\t\turl = '/GraphicalView.do?method=popUp&haid=0&isPopUp=true'; // NO I18N\n \t\t\twindowOpenOptions = 'scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25'; // NO I18N\n \t\t\tname = \"FlashView\"; // NO I18N\n \t\t}\n\t\twindow.open(url, name, windowOpenOptions);\n\n\t\tvar defaultview = \"");
/* 1703 */                               out.print(request.getAttribute("defaultview"));
/* 1704 */                               out.write("\";\n        \n        if(defaultview == \"showResourceTypesAll\")\n        {\n\t\t\ta.selectedIndex =0;\n        }\n        else if(defaultview == \"showResourceTypes\")\n        {\n            a.selectedIndex = 1;\n        }\n        else if(defaultview == \"showMonitorGroupView\")\n        {\n            a.selectedIndex = 3;\n        }\n        else if(defaultview == \"showGMapView\")\n        {\n\t\t\ta.selectedIndex = 4;\n        }\n        else if(defaultview == \"showIconsView\")\n        {\n            a.selectedIndex = 5;\n        }\n        else if(defaultview == \"showDetailsView\")\n        {\n            a.selectedIndex = 6;\n        }\n        return;\n\t}\n\tlocation.href=urlredirect[a.selectedIndex]; //NO I18N\n}\n</script>\n");
/* 1705 */                               out.write(10);
/* 1706 */                               out.write(32);
/*      */                             }
/* 1708 */                             out.write(10);
/* 1709 */                             out.write(10);
/*      */                           }
/* 1711 */                           out.write("\n<table id=\"tableID\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" width=\"100%\" class=\"lrbtborder\" style=\"margin-top:5px;\">\n<tbody>\n<tr>\n<td width=\"100%\" valign=\"top\">\n<form action=\"/deleteMO.do?method=deleteMO\" name=\"deletemonitor\" method=\"post\" style=\"display:inline\" >\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" valign=\"center\">\n<tr height=\"28\">\n\n <td class=\"bodytextbold tableheadingbborder\" colspan=\"13\" align=\"left\" valign=\"center\" width=\"65%\" style=\"height:50px;padding-left:20px\"  >\n ");
/* 1712 */                           if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 1713 */                             out.write("\n       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n ");
/*      */                             
/* 1715 */                             PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1716 */                             _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 1717 */                             _jspx_th_logic_005fpresent_005f11.setParent(null);
/*      */                             
/* 1719 */                             _jspx_th_logic_005fpresent_005f11.setRole("ADMIN,ENTERPRISEADMIN");
/* 1720 */                             int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 1721 */                             if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                               for (;;) {
/* 1723 */                                 out.write(32);
/* 1724 */                                 out.write(10);
/* 1725 */                                 out.write(32);
/* 1726 */                                 out.write("<!--$Id$-->\n<script type=\"text/javascript\" src=\"/template/chosen.jquery.min.js\"></script>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n ");
/*      */                                 
/* 1728 */                                 PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1729 */                                 _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 1730 */                                 _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_logic_005fpresent_005f11);
/*      */                                 
/* 1732 */                                 _jspx_th_logic_005fpresent_005f12.setRole("DEMO,ADMIN");
/* 1733 */                                 int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 1734 */                                 if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                                   for (;;) {
/* 1736 */                                     out.write("&nbsp;<span class=\"filterby-txt input-uptime\">");
/* 1737 */                                     out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 1738 */                                     out.write(" :</span>\n                 <select style=\"width:220px\"  data-placeholder=\"");
/* 1739 */                                     out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 1740 */                                     out.write("\" tabindex=\"5\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\" name=\"operation\">\n                     <option value=\"\">   </option>\n\t\t\t\t\t <option value=\"customField\">");
/* 1741 */                                     out.print(FormatUtil.getString("am.myfield.assign.text"));
/* 1742 */                                     out.write("</option>\n                     <option value=\"Delete\">");
/* 1743 */                                     out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 1744 */                                     out.write("</option>\n\t\t\t");
/* 1745 */                                     if ((request.getParameter("method").equals("showMonitorGroupView")) || (request.getParameter("method").equals("showSubGroupTree"))) {
/* 1746 */                                       out.write("\n\t\t     <option VALUE=\"Disable Actions\">");
/* 1747 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupview.disableaction.text"));
/* 1748 */                                       out.write("</option>\n\t\t     <option VALUE=\"Enable Actions\">");
/* 1749 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupview.enableaction.text"));
/* 1750 */                                       out.write("  </option>\n\n");
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/* 1755 */                                     if (!request.getParameter("method").equals("showMonitorGroupView"))
/*      */                                     {
/* 1757 */                                       if (!"VirtualMachine".equals(resourceName))
/*      */                                       {
/*      */ 
/*      */ 
/* 1761 */                                         out.write("\n                     <option value=\"editDisplayNames\">");
/* 1762 */                                         out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 1763 */                                         out.write("</option>\n                    ");
/*      */                                         
/* 1765 */                                         if (!request.getParameter("method").equals("showSubGroupTree"))
/*      */                                         {
/* 1767 */                                           out.write("\n\t\t\t\t\t\t<option value=\"updateIP\">");
/* 1768 */                                           out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 1769 */                                           out.write("</option>\n\t\t\t\t\t");
/*      */                                         }
/* 1771 */                                         out.write("\n\t\t\t");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 1776 */                                     out.write("\n\t\t     <option value=\"Manage\">");
/* 1777 */                                     out.print(FormatUtil.getString("Manage"));
/* 1778 */                                     out.write("</option>\n             <option value=\"Unmanage\">");
/* 1779 */                                     out.print(FormatUtil.getString("UnManage"));
/* 1780 */                                     out.write("</option>\n             <option value=\"unManageAndReset\">");
/* 1781 */                                     out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 1782 */                                     out.write("</option>\n\t\t\t");
/*      */                                     
/* 1784 */                                     if (!"VirtualMachine".equals(resourceName))
/*      */                                     {
/*      */ 
/* 1787 */                                       out.write("\n                     <option value=\"updateauth\">");
/* 1788 */                                       out.print(FormatUtil.getString("am.webclient.updateauthentication.text"));
/* 1789 */                                       out.write("</option>\n                     <option value=\"updatepoll\">");
/* 1790 */                                       out.print(FormatUtil.getString("am.webclient.updatepolling.text"));
/* 1791 */                                       out.write("</option>\n\t\t\t");
/*      */                                     }
/*      */                                     
/* 1794 */                                     if ((!request.getParameter("method").equals("showMonitorGroupView")) && (!request.getParameter("method").equals("showSubGroupTree")))
/*      */                                     {
/* 1796 */                                       out.write("\n                     <option value=\"copyPaste\">");
/* 1797 */                                       out.print(FormatUtil.getString("am.webclient.copypaste.headtitle"));
/* 1798 */                                       out.write("</option>\n\n\t\t\t");
/*      */                                     }
/* 1800 */                                     out.write("\n                 </select>\n            ");
/* 1801 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 1802 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1806 */                                 if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 1807 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                                 }
/*      */                                 
/* 1810 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 1811 */                                 out.write("\n\t\t\t");
/*      */                                 
/* 1813 */                                 PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1814 */                                 _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 1815 */                                 _jspx_th_logic_005fpresent_005f13.setParent(_jspx_th_logic_005fpresent_005f11);
/*      */                                 
/* 1817 */                                 _jspx_th_logic_005fpresent_005f13.setRole("ENTERPRISEADMIN");
/* 1818 */                                 int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 1819 */                                 if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */                                   for (;;) {
/* 1821 */                                     out.write("\n\t\t\t");
/*      */                                     
/* 1823 */                                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1824 */                                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 1825 */                                     _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fpresent_005f13);
/* 1826 */                                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 1827 */                                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                       for (;;) {
/* 1829 */                                         out.write("\n\t\t\t");
/*      */                                         
/* 1831 */                                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1832 */                                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1833 */                                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                         
/* 1835 */                                         _jspx_th_c_005fwhen_005f0.setTest("${param.method eq 'showMonitorGroupView'}");
/* 1836 */                                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1837 */                                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                           for (;;) {
/* 1839 */                                             out.write("\n\t\t\t <select style=\"width:220px\"  data-placeholder=\"");
/* 1840 */                                             out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 1841 */                                             out.write("\" tabindex=\"5\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\" name=\"operation\">");
/* 1842 */                                             out.write("\n\t\t\t <option value=\"\">   </option>\n\t\t\t <option value=\"Manage\">");
/* 1843 */                                             out.print(FormatUtil.getString("Manage"));
/* 1844 */                                             out.write("</option>");
/* 1845 */                                             out.write("\n\t\t\t <option value=\"Unmanage\">");
/* 1846 */                                             out.print(FormatUtil.getString("UnManage"));
/* 1847 */                                             out.write("</option>");
/* 1848 */                                             out.write("\n\t\t\t <option value=\"unManageAndReset\">");
/* 1849 */                                             out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 1850 */                                             out.write("</option>");
/* 1851 */                                             out.write("\n\t\t\t </select>\n\t\t\t");
/* 1852 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1853 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 1857 */                                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1858 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                         }
/*      */                                         
/* 1861 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1862 */                                         out.write("\n\t\t\t");
/* 1863 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1864 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 1868 */                                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1869 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                     }
/*      */                                     
/* 1872 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1873 */                                     out.write("\n\t\t\t");
/* 1874 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 1875 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1879 */                                 if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 1880 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13); return;
/*      */                                 }
/*      */                                 
/* 1883 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 1884 */                                 out.write("\n\n    ");
/*      */                                 
/* 1886 */                                 PresentTag _jspx_th_logic_005fpresent_005f14 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1887 */                                 _jspx_th_logic_005fpresent_005f14.setPageContext(_jspx_page_context);
/* 1888 */                                 _jspx_th_logic_005fpresent_005f14.setParent(_jspx_th_logic_005fpresent_005f11);
/*      */                                 
/* 1890 */                                 _jspx_th_logic_005fpresent_005f14.setRole("OPERATOR");
/* 1891 */                                 int _jspx_eval_logic_005fpresent_005f14 = _jspx_th_logic_005fpresent_005f14.doStartTag();
/* 1892 */                                 if (_jspx_eval_logic_005fpresent_005f14 != 0) {
/*      */                                   for (;;) {
/* 1894 */                                     out.write(" \n                ");
/*      */                                     
/* 1896 */                                     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1897 */                                     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1898 */                                     _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_logic_005fpresent_005f14);
/* 1899 */                                     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1900 */                                     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                       for (;;) {
/* 1902 */                                         out.write("\n                    ");
/*      */                                         
/* 1904 */                                         WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1905 */                                         _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1906 */                                         _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                         
/* 1908 */                                         _jspx_th_c_005fwhen_005f1.setTest("${allowEdit=='true' || allowUpdateIP=='true' || allowManage=='true' || allowReset=='true'}");
/* 1909 */                                         int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1910 */                                         if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                           for (;;) {
/* 1912 */                                             out.write("&nbsp<span class=\"filterby-txt input-uptime\">");
/* 1913 */                                             out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 1914 */                                             out.write(" :</span>\n                        <select style=\"width:220px\" data-placeholder=\"");
/* 1915 */                                             out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 1916 */                                             out.write("\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\"  name=\"operation\">\n                            <option VALUE=\"selectactions\" selected=true></option>\n                            ");
/*      */                                             
/* 1918 */                                             ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1919 */                                             _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1920 */                                             _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/* 1921 */                                             int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1922 */                                             if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                               for (;;) {
/* 1924 */                                                 out.write("\n\t                   \t\t\t ");
/*      */                                                 
/* 1926 */                                                 WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1927 */                                                 _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1928 */                                                 _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                                 
/* 1930 */                                                 _jspx_th_c_005fwhen_005f2.setTest("${allowEdit=='true'}");
/* 1931 */                                                 int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1932 */                                                 if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                                   for (;;) {
/* 1934 */                                                     out.write("\n\t                            \t<option value=\"editDisplayNames\">");
/* 1935 */                                                     out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 1936 */                                                     out.write("</option>\n\t                             ");
/* 1937 */                                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1938 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 1942 */                                                 if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1943 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                                 }
/*      */                                                 
/* 1946 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1947 */                                                 out.write("\n                \t\t\t");
/* 1948 */                                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1949 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 1953 */                                             if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1954 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                             }
/*      */                                             
/* 1957 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1958 */                                             out.write("\n                \t\t\t");
/*      */                                             
/* 1960 */                                             ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1961 */                                             _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1962 */                                             _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fwhen_005f1);
/* 1963 */                                             int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1964 */                                             if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                               for (;;) {
/* 1966 */                                                 out.write("\n\t                   \t\t\t ");
/*      */                                                 
/* 1968 */                                                 WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1969 */                                                 _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1970 */                                                 _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                                 
/* 1972 */                                                 _jspx_th_c_005fwhen_005f3.setTest("${allowUpdateIP=='true'}");
/* 1973 */                                                 int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1974 */                                                 if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                                   for (;;) {
/* 1976 */                                                     out.write("\n\t\t\t\t\t\t\t\t\t <option value=\"updateIP\">");
/* 1977 */                                                     out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 1978 */                                                     out.write("</option>\n\t                             ");
/* 1979 */                                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1980 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 1984 */                                                 if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1985 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                                 }
/*      */                                                 
/* 1988 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1989 */                                                 out.write("\n                \t\t\t");
/* 1990 */                                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1991 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 1995 */                                             if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1996 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                             }
/*      */                                             
/* 1999 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2000 */                                             out.write("\n                \t\t\t");
/*      */                                             
/* 2002 */                                             ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2003 */                                             _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2004 */                                             _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fwhen_005f1);
/* 2005 */                                             int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2006 */                                             if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                               for (;;) {
/* 2008 */                                                 out.write("\n\t                   \t\t\t ");
/*      */                                                 
/* 2010 */                                                 WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2011 */                                                 _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2012 */                                                 _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                                 
/* 2014 */                                                 _jspx_th_c_005fwhen_005f4.setTest("${allowManage=='true' || allowReset=='true'}");
/* 2015 */                                                 int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2016 */                                                 if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                                   for (;;) {
/* 2018 */                                                     out.write("\n\t                            \t<option value=\"Manage\">");
/* 2019 */                                                     out.print(FormatUtil.getString("Manage"));
/* 2020 */                                                     out.write("</option>\n                            \t\t<option value=\"Unmanage\">");
/* 2021 */                                                     out.print(FormatUtil.getString("UnManage"));
/* 2022 */                                                     out.write("</option>\n\t                             ");
/* 2023 */                                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2024 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 2028 */                                                 if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2029 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                                 }
/*      */                                                 
/* 2032 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2033 */                                                 out.write("\n\t\t\t\t\t");
/* 2034 */                                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2035 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2039 */                                             if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2040 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                                             }
/*      */                                             
/* 2043 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2044 */                                             out.write("\n                \t\t\t");
/*      */                                             
/* 2046 */                                             ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2047 */                                             _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2048 */                                             _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fwhen_005f1);
/* 2049 */                                             int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2050 */                                             if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                                               for (;;) {
/* 2052 */                                                 out.write("\n\t\t\t\t\t");
/*      */                                                 
/* 2054 */                                                 WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2055 */                                                 _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2056 */                                                 _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                                 
/* 2058 */                                                 _jspx_th_c_005fwhen_005f5.setTest("${allowReset=='true'}");
/* 2059 */                                                 int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2060 */                                                 if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                                   for (;;) {
/* 2062 */                                                     out.write("\n\t\t\t\t\t <option value=\"unManageAndReset\">");
/* 2063 */                                                     out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 2064 */                                                     out.write("</option>");
/* 2065 */                                                     out.write("\n\t\t\t\t\t");
/* 2066 */                                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2067 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 2071 */                                                 if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2072 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                                 }
/*      */                                                 
/* 2075 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2076 */                                                 out.write("\n                \t\t\t");
/* 2077 */                                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2078 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2082 */                                             if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2083 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                                             }
/*      */                                             
/* 2086 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2087 */                                             out.write("\n                        </select>\n                    ");
/* 2088 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2089 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2093 */                                         if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2094 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                         }
/*      */                                         
/* 2097 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2098 */                                         out.write("\n                ");
/* 2099 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2100 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2104 */                                     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2105 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                     }
/*      */                                     
/* 2108 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2109 */                                     out.write("\n            ");
/* 2110 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f14.doAfterBody();
/* 2111 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2115 */                                 if (_jspx_th_logic_005fpresent_005f14.doEndTag() == 5) {
/* 2116 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14); return;
/*      */                                 }
/*      */                                 
/* 2119 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/* 2120 */                                 out.write("\n          \n");
/* 2121 */                                 out.write("\n    ");
/* 2122 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 2123 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2127 */                             if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 2128 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11); return;
/*      */                             }
/*      */                             
/* 2131 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/*      */                           }
/* 2133 */                           out.write(" \n \n  ");
/*      */                           
/* 2135 */                           IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2136 */                           _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2137 */                           _jspx_th_c_005fif_005f12.setParent(null);
/*      */                           
/* 2139 */                           _jspx_th_c_005fif_005f12.setTest("${forInventory != \"true\"}");
/* 2140 */                           int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2141 */                           if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                             for (;;) {
/* 2143 */                               out.write("\n    ");
/* 2144 */                               if ((!com.adventnet.appmanager.util.Constants.isIt360) && (tags.size() > 0)) {
/* 2145 */                                 out.write("\n\t   &nbsp;\n       <b class=\"");
/* 2146 */                                 out.print(filterstyle);
/* 2147 */                                 out.write(" input-uptime\">");
/* 2148 */                                 out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/* 2149 */                                 out.write(" : </b>\n    ");
/*      */                               }
/* 2151 */                               out.write(10);
/* 2152 */                               out.write(32);
/* 2153 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2154 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2158 */                           if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2159 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*      */                           }
/*      */                           else {
/* 2162 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2163 */                             out.write(10);
/* 2164 */                             out.write(32);
/* 2165 */                             out.write(32);
/* 2166 */                             if (_jspx_meth_c_005fif_005f13(_jspx_page_context))
/*      */                               return;
/* 2168 */                             out.write(10);
/*      */                             
/* 2170 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2171 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 2172 */                             _jspx_th_c_005fif_005f14.setParent(null);
/*      */                             
/* 2174 */                             _jspx_th_c_005fif_005f14.setTest("${forInventory != \"true\"}");
/* 2175 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 2176 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/* 2178 */                                 out.write("\n       ");
/* 2179 */                                 if ((!com.adventnet.appmanager.util.Constants.isIt360) && (tags.size() > 0)) {
/* 2180 */                                   out.write("\n          <select name=\"groupviewfilterby\"  style=\"width:150px;\" id=\"groupviewfilterby\" data-placeholder=\"");
/* 2181 */                                   out.print(FormatUtil.getString("am.myfield.choosevalue.text"));
/* 2182 */                                   out.write("\" class=\"chzn-select\"  tabindex=\"5\" onchange='loadURLType(this.options[this.selectedIndex].value,this.form,this,\"true\")'>\n\t\t\t\t\t\t\t<option selected=value=\"-\"> </option> ");
/* 2183 */                                   out.write("\n\t\t\t\t\t\t\t");
/* 2184 */                                   if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                     return;
/* 2186 */                                   out.write("\n                            </select>\n\t\t\t\t\t\t\t&nbsp;\n        ");
/*      */                                 }
/* 2188 */                                 out.write("\n     ");
/* 2189 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 2190 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2194 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 2195 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*      */                             }
/*      */                             else {
/* 2198 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 2199 */                               out.write("\n   ");
/* 2200 */                               if (_jspx_meth_c_005fif_005f15(_jspx_page_context))
/*      */                                 return;
/* 2202 */                               out.write("\n\n\n<div id=\"customFieldValues\" style=\"display:none;\">\n </div>\n</td>\n <td  class=\"tableheadingbborder\" height=\"50\" align=\"right\" style=\"padding-right:20px;\" > \n <table border=\"0\">\n <tr>\n <td>\n\n \n <a href='/showresource.do?group=All&method=showMonitorGroupView&uncategorize=false'  class=\"");
/* 2203 */                               out.print(groupviewstyle);
/* 2204 */                               out.write(34);
/* 2205 */                               out.write(62);
/* 2206 */                               out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 2207 */                               out.write("</a>  \n  ");
/* 2208 */                               if (((request.getParameter("uncategorize") == null) || ("false".equals(request.getParameter("uncategorize")))) && (!"orphaned".equals(request.getParameter("retaintree")))) {
/* 2209 */                                 out.write("  \n  <a id=\"treelink\" class=\"new-monitordiv-link\" style=\"font-size:10px\"already=");
/* 2210 */                                 out.print(already);
/* 2211 */                                 out.write(" href=\"javascript:void(0);\"  \n  onClick=\"javascript:disableTreeView();toggleallDivs('show','");
/* 2212 */                                 out.print(isInventory);
/* 2213 */                                 out.write("');\"><div id=\"showall\" style=\"display:");
/* 2214 */                                 out.print(expandStyle);
/* 2215 */                                 out.write(34);
/* 2216 */                                 out.write(62);
/* 2217 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.expandall"));
/* 2218 */                                 out.write("</div><div id=\"hideall\" style=\"display:");
/* 2219 */                                 out.print(colpseStyle);
/* 2220 */                                 out.write(34);
/* 2221 */                                 out.write(62);
/* 2222 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.collapseall"));
/* 2223 */                                 out.write("</div></a>\n");
/*      */                               }
/* 2225 */                               out.write(" \n  ");
/*      */                               
/* 2227 */                               if ((!isInventory) && (!com.adventnet.appmanager.util.Constants.isPrivilegedUser(request)))
/*      */                               {
/* 2229 */                                 out.write("\n <span class=\"bodytext\" style=\"color:#595959;\"> &nbsp;|</span> \n  <a href='/showresource.do?group=All&method=showMonitorGroupView&uncategorize=true&parents=orphaned' title=\"");
/* 2230 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupview.unassociate.title"));
/* 2231 */                                 out.write("\" class=\"");
/* 2232 */                                 out.print(uncatstyle);
/* 2233 */                                 out.write(34);
/* 2234 */                                 out.write(62);
/* 2235 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupview.orphaned.monitors.text"));
/* 2236 */                                 out.write("</a> \n ");
/*      */                               }
/* 2238 */                               out.write("\n\n</td>\n</tr>\n</table>\n  </td> \n\n</tr>\n\n</table>\n\n");
/*      */                               
/* 2240 */                               String tableClass = "";
/* 2241 */                               String emptyTableClass = "columnheading";
/* 2242 */                               if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                               {
/* 2244 */                                 tableClass = "lrtbdarkborder";
/* 2245 */                                 emptyTableClass = "emptyTableMsg";
/*      */                               }
/*      */                               
/* 2248 */                               out.write("\n<table width=\"100%\" id=\"allMonitorGroups\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\"  class=\"");
/* 2249 */                               out.print(tableClass);
/* 2250 */                               out.write("\" >\n");
/*      */                               
/* 2252 */                               IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2253 */                               _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2254 */                               _jspx_th_c_005fif_005f16.setParent(null);
/*      */                               
/* 2256 */                               _jspx_th_c_005fif_005f16.setTest("${ empty applications}");
/* 2257 */                               int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2258 */                               if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                                 for (;;) {
/* 2260 */                                   out.write(10);
/*      */                                   
/* 2262 */                                   EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2263 */                                   _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2264 */                                   _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fif_005f16);
/*      */                                   
/* 2266 */                                   _jspx_th_logic_005fempty_005f0.setName("applications");
/* 2267 */                                   int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2268 */                                   if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                                     for (;;) {
/* 2270 */                                       out.write("\n<tr class=\"whitegrayrightalign\" >\n\n         <td   height=\"50\" class=\"bodytext\"  colspan=\"5\" style='padding-left:40px' height=\"100\">\n\t\t<span class=\"bodytext\">");
/* 2271 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupview.nogroups.message.text"));
/* 2272 */                                       out.write("</span>\n\t\t");
/*      */                                       
/* 2274 */                                       PresentTag _jspx_th_logic_005fpresent_005f15 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2275 */                                       _jspx_th_logic_005fpresent_005f15.setPageContext(_jspx_page_context);
/* 2276 */                                       _jspx_th_logic_005fpresent_005f15.setParent(_jspx_th_logic_005fempty_005f0);
/*      */                                       
/* 2278 */                                       _jspx_th_logic_005fpresent_005f15.setRole("ADMIN");
/* 2279 */                                       int _jspx_eval_logic_005fpresent_005f15 = _jspx_th_logic_005fpresent_005f15.doStartTag();
/* 2280 */                                       if (_jspx_eval_logic_005fpresent_005f15 != 0) {
/*      */                                         for (;;) {
/* 2282 */                                           out.write(10);
/* 2283 */                                           out.write(9);
/* 2284 */                                           out.write(9);
/* 2285 */                                           if (EnterpriseUtil.isIt360MSPEdition()) {
/* 2286 */                                             out.write("\n\t       \t\t<span class=\"bodytext\"> ");
/* 2287 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 2288 */                                             out.write(" <a href=\"/it360/createBusinessServiceWiz.do\")%>");
/* 2289 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 2290 */                                             out.write("</a></span>\n               ");
/*      */                                           }
/* 2292 */                                           out.write("\n\t\t    \n               ");
/* 2293 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f15.doAfterBody();
/* 2294 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2298 */                                       if (_jspx_th_logic_005fpresent_005f15.doEndTag() == 5) {
/* 2299 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15); return;
/*      */                                       }
/*      */                                       
/* 2302 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/* 2303 */                                       out.write("\n\t</td>\n        </tr>\n");
/* 2304 */                                       int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2305 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2309 */                                   if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2310 */                                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                                   }
/*      */                                   
/* 2313 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2314 */                                   out.write(10);
/* 2315 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2316 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2320 */                               if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2321 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/*      */                               }
/*      */                               else {
/* 2324 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2325 */                                 out.write("\n\n\n\n");
/*      */                                 
/* 2327 */                                 IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2328 */                                 _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 2329 */                                 _jspx_th_c_005fif_005f17.setParent(null);
/*      */                                 
/* 2331 */                                 _jspx_th_c_005fif_005f17.setTest("${not empty applications}");
/* 2332 */                                 int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 2333 */                                 if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                   for (;;) {
/* 2335 */                                     out.write(10);
/*      */                                     
/* 2337 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2338 */                                     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2339 */                                     _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fif_005f17);
/*      */                                     
/* 2341 */                                     _jspx_th_logic_005fnotEmpty_005f0.setName("applications");
/* 2342 */                                     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2343 */                                     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                       for (;;) {
/* 2345 */                                         out.write("\n<tr class=\"bodytextbold\">\n\n<td class=\"columnheading\" valign=\"center\" width=\"3%\">&nbsp;</td>\n\n<td class=\"columnheading\" align=\"left\" height=\"24\" width=\"47%\">");
/* 2346 */                                         if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 2347 */                                           out.write(10);
/* 2348 */                                           if (!EnterpriseUtil.isAdminServer()) {
/* 2349 */                                             out.write("\n<input type='checkbox' name='headercheckbox1' onclick=\"javascript:fnSelectAll(this,'select');\" >");
/*      */                                           }
/* 2351 */                                           out.write(10);
/*      */                                         }
/* 2353 */                                         out.write(10);
/* 2354 */                                         out.print(FormatUtil.getString("webclient.topo.operations.response.name"));
/* 2355 */                                         out.write("\n</td>\n\n<td class=\"columnheading\" align=\"left\" height=\"24\" width=\"20%\">");
/* 2356 */                                         out.print(FormatUtil.getString("Actions"));
/* 2357 */                                         out.write("</td>\n\n<td class=\"columnheading\" align=\"center\"  width=\"8%\">");
/* 2358 */                                         out.print(FormatUtil.getString("Availability"));
/* 2359 */                                         out.write("</td>\n\n<td class=\"columnheading\" align=\"center\" width=\"7%\">");
/* 2360 */                                         out.print(FormatUtil.getString("Health"));
/* 2361 */                                         out.write("</td>\n\n<td class=\"columnheading\" align=\"center\"  width=\"15%\">&nbsp;</td>\n\n</tr>\n");
/* 2362 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2363 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2367 */                                     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2368 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                     }
/*      */                                     
/* 2371 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2372 */                                     out.write(10);
/* 2373 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 2374 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2378 */                                 if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 2379 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/*      */                                 }
/*      */                                 else {
/* 2382 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2383 */                                   out.write(10);
/* 2384 */                                   out.write(10);
/* 2385 */                                   if ("true".equals(nouncategorize)) {
/* 2386 */                                     out.write("\n<tr class=\"whitegrayrightalign\" >\n\n         <td   height=\"50\" class=\"bodytext\"  colspan=\"5\" style='padding-left:40px' height=\"100\">\n\t\t<span class=\"bodytext\">");
/* 2387 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.nouncategorize"));
/* 2388 */                                     out.write("</span>\n\t\t\n\t</td>\n        </tr>\n");
/*      */                                   }
/* 2390 */                                   out.write(10);
/* 2391 */                                   out.write(10);
/* 2392 */                                   boolean checkRow = false;
/* 2393 */                                   String mgresourceid = null;
/* 2394 */                                   int mgviewlist = 0;
/* 2395 */                                   int monitorlist = 0;
/* 2396 */                                   boolean showeditLink = true;
/* 2397 */                                   String haidininventory = "";
/* 2398 */                                   if (request.getParameter("haid") != null)
/*      */                                   {
/* 2400 */                                     haidininventory = request.getParameter("haid");
/*      */                                   }
/*      */                                   
/* 2403 */                                   out.write(" \n     ");
/*      */                                   
/* 2405 */                                   IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2406 */                                   _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 2407 */                                   _jspx_th_c_005fif_005f18.setParent(null);
/*      */                                   
/* 2409 */                                   _jspx_th_c_005fif_005f18.setTest("${not empty applications}");
/* 2410 */                                   int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 2411 */                                   if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                                     for (;;) {
/* 2413 */                                       out.write("\n     \n    ");
/*      */                                       
/* 2415 */                                       IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2416 */                                       _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2417 */                                       _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_c_005fif_005f18);
/*      */                                       
/* 2419 */                                       _jspx_th_logic_005fiterate_005f1.setName("applications");
/*      */                                       
/* 2421 */                                       _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                       
/* 2423 */                                       _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/*      */                                       
/* 2425 */                                       _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/* 2426 */                                       int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2427 */                                       if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2428 */                                         ArrayList row = null;
/* 2429 */                                         Integer i = null;
/* 2430 */                                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2431 */                                           out = _jspx_page_context.pushBody();
/* 2432 */                                           _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2433 */                                           _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                         }
/* 2435 */                                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2436 */                                         i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                         for (;;) {
/* 2438 */                                           out.write("\n    ");
/*      */                                           
/* 2440 */                                           mgviewlist = i.intValue();
/* 2441 */                                           showeditLink = true;
/* 2442 */                                           healthid = "18";
/* 2443 */                                           availid = "17";
/* 2444 */                                           String rowID = "";
/* 2445 */                                           String MGType = row.get(2) != null ? (String)row.get(2) : "";
/* 2446 */                                           String haiGroupType = "0";
/* 2447 */                                           if (row.size() - 1 == haiGroupTypeIndex)
/*      */                                           {
/* 2449 */                                             haiGroupType = (String)row.get(haiGroupTypeIndex);
/*      */                                           }
/* 2451 */                                           String resourceType = row.get(resourceTypeindex) != null ? (String)row.get(resourceTypeindex) : "";
/* 2452 */                                           img = "";
/* 2453 */                                           if (!resourceType.equals("HAI"))
/*      */                                           {
/* 2455 */                                             pageContext.setAttribute("notMG", "true");
/* 2456 */                                             minusStyle = "none";
/* 2457 */                                             img = "<img src='" + row.get(3) + "' title='" + row.get(4) + "' align='absmiddle' border='0'/>&nbsp;";
/* 2458 */                                             healthid = (String)healthkeys.get(resourceType);
/* 2459 */                                             availid = (String)availabilitykeys.get(resourceType);
/*      */                                           }
/* 2461 */                                           if (isInventory)
/*      */                                           {
/* 2463 */                                             MGType = "0";
/*      */                                           }
/*      */                                           
/*      */ 
/*      */ 
/* 2468 */                                           String MGresourceids = (String)row.get(6);
/* 2469 */                                           if ((MGType.equals("0")) || ((EnterpriseUtil.isIt360MSPEdition()) && (!"orphaned".equals(MGresourceids)) && (com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.isBusinessServiceGrpId(MGresourceids))))
/*      */                                           {
/* 2471 */                                             String actionstatus = (String)row.get(actionIndex);
/* 2472 */                                             String actionimage = "/images/alarm-icon.png";
/* 2473 */                                             String actionmessage = FormatUtil.getString("Actions Enabled");
/* 2474 */                                             if ((actionstatus != null) && (actionstatus.equals("0")))
/*      */                                             {
/* 2476 */                                               actionimage = "/images/icon_actions_disabled.gif";
/* 2477 */                                               actionmessage = FormatUtil.getString("Actions Disabled");
/*      */                                             }
/* 2479 */                                             String restitle = row.get(unmanagestatusindex) != null ? (String)row.get(displayNameindex) + "-UnManaged" : (String)row.get(displayNameindex);
/* 2480 */                                             String status = row.get(unmanagestatusindex) != null ? "disabledtext" : "monitorgp-links";
/* 2481 */                                             String resIdTOCheck = "-1";
/* 2482 */                                             int residint = 1;
/*      */                                             try
/*      */                                             {
/* 2485 */                                               resIdTOCheck = (String)row.get(residIndex);
/* 2486 */                                               residint = Integer.parseInt((String)row.get(residIndex));
/*      */                                             }
/*      */                                             catch (Exception e) {}
/*      */                                             
/* 2490 */                                             if (!resIdTOCheck.equals("orphaned"))
/*      */                                             {
/*      */ 
/* 2493 */                                               out.write("\n\n\t <tr  id=\"#monitor\" toplevel='true' isgroup=\"true\" groupid=\"");
/* 2494 */                                               out.print(resIdTOCheck);
/* 2495 */                                               out.write("\"  class=\" monitorgpHeader\" onmouseover=\"this.className='monitorgpHeaderHover'\" onmouseout=\"this.className='monitorgpHeader'\" > \n\t\t  ");
/*      */                                               
/* 2497 */                                               if (EnterpriseUtil.isAdminServer())
/*      */                                               {
/* 2499 */                                                 String dspName = (String)row.get(1);
/* 2500 */                                                 String groupName = row.get(displayNameindex) + "_" + CommDBUtil.getManagedServerNameWithPort((String)row.get(residIndex));
/*      */                                                 
/* 2502 */                                                 if (!checkRow) {
/* 2503 */                                                   mgresourceid = resIdTOCheck;
/* 2504 */                                                   rowID = "expandRow";
/* 2505 */                                                   checkRow = true;
/*      */                                                 }
/*      */                                                 
/* 2508 */                                                 out.write("\n\t <td  class=\"whitegrayrightalign\"  align=\"center\"  width=\"3%\" style=\"padding-right:10px\">\n\t\t");
/*      */                                                 
/* 2510 */                                                 IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2511 */                                                 _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 2512 */                                                 _jspx_th_c_005fif_005f19.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                 
/* 2514 */                                                 _jspx_th_c_005fif_005f19.setTest("${notMG!=\"true\"}");
/* 2515 */                                                 int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 2516 */                                                 if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                                                   for (;;) {
/* 2518 */                                                     out.write("\n\t\t&nbsp;&nbsp;&nbsp;\n\t\t\t<a  class =\"expandlink\" isopen=\"");
/* 2519 */                                                     out.print(isopen);
/* 2520 */                                                     out.write("\" resid=\"");
/* 2521 */                                                     out.print(resIdTOCheck);
/* 2522 */                                                     out.write("\" id=\"");
/* 2523 */                                                     out.print(rowID);
/* 2524 */                                                     out.write("\" href=\"javascript:void(0);\" onclick=\"getDynamicCall('/showresource.do?method=showMonitorGroupViewIndividual&parents=");
/* 2525 */                                                     out.print(resIdTOCheck);
/* 2526 */                                                     out.write("&lindx=1&pageno=1',this,'true'), toggleChildMos('#monitor");
/* 2527 */                                                     out.print(resIdTOCheck);
/* 2528 */                                                     out.write("',this),toggleTreeImage('");
/* 2529 */                                                     out.print(resIdTOCheck);
/* 2530 */                                                     out.write("');\" > \t\t\t\n\t\t\t<div id=\"monitorShow");
/* 2531 */                                                     out.print(resIdTOCheck);
/* 2532 */                                                     out.write("\" style=\"display:");
/* 2533 */                                                     out.print(plusStyle);
/* 2534 */                                                     out.write(";\"><img src=\"/images/icon_plus.gif\" border=\"0\" ></div><div id=\"monitorHide");
/* 2535 */                                                     out.print(resIdTOCheck);
/* 2536 */                                                     out.write("\" style=\"display:");
/* 2537 */                                                     out.print(minusStyle);
/* 2538 */                                                     out.write(";\"><img src=\"/images/icon_minus.gif\" border=\"0\" ></div> </a>\n\t\t");
/* 2539 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 2540 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 2544 */                                                 if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 2545 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                                                 }
/*      */                                                 
/* 2548 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 2549 */                                                 out.write(10);
/* 2550 */                                                 out.write(9);
/* 2551 */                                                 out.write(9);
/* 2552 */                                                 if (_jspx_meth_c_005fif_005f20(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                                   return;
/* 2554 */                                                 out.write("\n\t</td>\n\n\t <td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"47%\" title=\"");
/* 2555 */                                                 out.print(groupName);
/* 2556 */                                                 out.write("\"  style=\"padding-left: 0px;\">\n\n\t \n\t ");
/*      */                                                 
/* 2558 */                                                 IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2559 */                                                 _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 2560 */                                                 _jspx_th_c_005fif_005f21.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                 
/* 2562 */                                                 _jspx_th_c_005fif_005f21.setTest("${notMG!=\"true\"}");
/* 2563 */                                                 int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 2564 */                                                 if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                                   for (;;) {
/* 2566 */                                                     out.write("\t \n\t   <input type=\"checkbox\" name=\"select\" value=\"");
/* 2567 */                                                     out.print(resIdTOCheck);
/* 2568 */                                                     out.write("\" id=\"");
/* 2569 */                                                     out.print(resIdTOCheck);
/* 2570 */                                                     out.write("\" grpresid=\"");
/* 2571 */                                                     out.print(resIdTOCheck);
/* 2572 */                                                     out.write("\" onclick=\"selectAllChildCKbs('");
/* 2573 */                                                     out.print(resIdTOCheck);
/* 2574 */                                                     out.write("',this,this.form);\"><a href=\"/showapplication.do?haid=");
/* 2575 */                                                     out.print(row.get(residIndex));
/* 2576 */                                                     out.write("&method=showApplication\" class=\"");
/* 2577 */                                                     out.print(status);
/* 2578 */                                                     out.write(34);
/* 2579 */                                                     out.write(62);
/* 2580 */                                                     out.print(getTrimmedText(groupName, 35));
/* 2581 */                                                     out.write("</a></td>");
/* 2582 */                                                     out.write("\n\t   ");
/* 2583 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 2584 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 2588 */                                                 if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 2589 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                                                 }
/*      */                                                 
/* 2592 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 2593 */                                                 out.write("\n\t   ");
/*      */                                                 
/* 2595 */                                                 IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2596 */                                                 _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 2597 */                                                 _jspx_th_c_005fif_005f22.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                 
/* 2599 */                                                 _jspx_th_c_005fif_005f22.setTest("${notMG==\"true\"}");
/* 2600 */                                                 int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 2601 */                                                 if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                                   for (;;) {
/* 2603 */                                                     out.write("\n\t   ");
/* 2604 */                                                     out.print(img);
/* 2605 */                                                     out.write(10);
/* 2606 */                                                     out.write(9);
/* 2607 */                                                     out.write(9);
/* 2608 */                                                     if ((site24x7List != null) && (site24x7List.containsKey(row.get(residIndex)))) {
/* 2609 */                                                       out.write("\n <a href=\"javascript:MM_openBrWindow('");
/* 2610 */                                                       out.print(URLEncoder.encode((String)site24x7List.get(row.get(residIndex))));
/* 2611 */                                                       out.write("','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2612 */                                                       out.print(status);
/* 2613 */                                                       out.write(34);
/* 2614 */                                                       out.write(62);
/* 2615 */                                                       out.print(row.get(1));
/* 2616 */                                                       out.write("</a>\n");
/*      */                                                     }
/* 2618 */                                                     else if ((extDeviceMap != null) && (extDeviceMap.get(row.get(residIndex)) != null))
/*      */                                                     {
/* 2620 */                                                       String link1 = (String)extDeviceMap.get(row.get(residIndex));
/* 2621 */                                                       showeditLink = false;
/* 2622 */                                                       if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                                       {
/* 2624 */                                                         out.write("\n\t\t\t   <a href=");
/* 2625 */                                                         out.print(link1);
/* 2626 */                                                         out.write("  class=\"");
/* 2627 */                                                         out.print(status);
/* 2628 */                                                         out.write("\">  ");
/* 2629 */                                                         out.print(getTrimmedText((String)row.get(1), 45));
/* 2630 */                                                         out.write("</a>;\t\t \n\t\t\t  ");
/*      */                                                       }
/*      */                                                       else {
/* 2633 */                                                         out.write("\n\t\t\t  <a href=\"javascript:MM_openBrWindow('");
/* 2634 */                                                         out.print(link1);
/* 2635 */                                                         out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2636 */                                                         out.print(status);
/* 2637 */                                                         out.write(34);
/* 2638 */                                                         out.write(62);
/* 2639 */                                                         out.print(getTrimmedText((String)row.get(1), 45));
/* 2640 */                                                         out.write("</a>\n\t\t\t");
/*      */                                                       }
/*      */                                                       
/* 2643 */                                                       out.write(10);
/*      */                                                     }
/* 2645 */                                                     else if (ChildMOHandler.isChildMonitorTypeSupportedForMG((String)row.get(2)))
/*      */                                                     {
/*      */ 
/* 2648 */                                                       out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('/showapplication.do?method=showChildApplicationDetail&resId=");
/* 2649 */                                                       out.print(row.get(0));
/* 2650 */                                                       out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2651 */                                                       out.print(status);
/* 2652 */                                                       out.write(34);
/* 2653 */                                                       out.write(62);
/* 2654 */                                                       out.print(row.get(1));
/* 2655 */                                                       out.write("</a>\n");
/*      */ 
/*      */                                                     }
/*      */                                                     else
/*      */                                                     {
/* 2660 */                                                       out.write("\n\t\t<a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2661 */                                                       out.print(row.get(residIndex));
/* 2662 */                                                       out.write("&haid=");
/* 2663 */                                                       out.print(haidininventory);
/* 2664 */                                                       out.write("\"\n\t  class=\"");
/* 2665 */                                                       out.print(status);
/* 2666 */                                                       out.write(34);
/* 2667 */                                                       out.write(62);
/* 2668 */                                                       out.print(row.get(1));
/* 2669 */                                                       out.write("</a>\t\t\n\t\t");
/*      */                                                     }
/*      */                                                     
/* 2672 */                                                     out.write(10);
/* 2673 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 2674 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 2678 */                                                 if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 2679 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                                                 }
/*      */                                                 
/* 2682 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 2683 */                                                 out.write("\n\t\n\t  </td>\n\t  </td>\n      ");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 2688 */                                                 if (!checkRow) {
/* 2689 */                                                   mgresourceid = resIdTOCheck;
/* 2690 */                                                   rowID = "expandRow";
/* 2691 */                                                   checkRow = true;
/*      */                                                 }
/*      */                                                 
/* 2694 */                                                 out.write("\n\t <td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"3%\" style=\"padding-right:10px\">\n\t\t");
/*      */                                                 
/* 2696 */                                                 IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2697 */                                                 _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 2698 */                                                 _jspx_th_c_005fif_005f23.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                 
/* 2700 */                                                 _jspx_th_c_005fif_005f23.setTest("${notMG!=\"true\"}");
/* 2701 */                                                 int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 2702 */                                                 if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                                   for (;;) {
/* 2704 */                                                     out.write("\n\t\t&nbsp;&nbsp;\n\t\t<a href=\"javascript:void(0);\" id=\"");
/* 2705 */                                                     out.print(rowID);
/* 2706 */                                                     out.write("\" class=\"expandlink\" isopen=\"");
/* 2707 */                                                     out.print(isopen);
/* 2708 */                                                     out.write("\" resid=\"");
/* 2709 */                                                     out.print(resIdTOCheck);
/* 2710 */                                                     out.write("\" onclick=\"getDynamicCall('/showresource.do?method=showMonitorGroupViewIndividual&parents=");
/* 2711 */                                                     out.print(resIdTOCheck);
/* 2712 */                                                     out.write("&lindx=1&pageno=1',this,'true'), toggleChildMos('#monitor");
/* 2713 */                                                     out.print(resIdTOCheck);
/* 2714 */                                                     out.write("',this),toggleTreeImage('");
/* 2715 */                                                     out.print(resIdTOCheck);
/* 2716 */                                                     out.write("');\">\n\t\t\n\t\t<div id=\"monitorShow");
/* 2717 */                                                     out.print(resIdTOCheck);
/* 2718 */                                                     out.write("\" style=\"display:");
/* 2719 */                                                     out.print(plusStyle);
/* 2720 */                                                     out.write(";\"><img src=\"/images/icon_plus.gif\"   border=\"0\" ></div><div id=\"monitorHide");
/* 2721 */                                                     out.print(resIdTOCheck);
/* 2722 */                                                     out.write("\" style=\"display:");
/* 2723 */                                                     out.print(minusStyle);
/* 2724 */                                                     out.write(";\"><img src=\"/images/icon_minus.gif\" border=\"0\" ></div> </a> \n        ");
/* 2725 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 2726 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 2730 */                                                 if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 2731 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                                                 }
/*      */                                                 
/* 2734 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 2735 */                                                 out.write("\n\n\t ");
/* 2736 */                                                 if (_jspx_meth_c_005fif_005f24(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                                   return;
/* 2738 */                                                 out.write("\n\t</td>\n\n\t <td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"47%\" title=\"");
/* 2739 */                                                 out.print(restitle);
/* 2740 */                                                 out.write("\">\n\t \t  ");
/*      */                                                 
/* 2742 */                                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f12 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2743 */                                                 _jspx_th_logic_005fnotPresent_005f12.setPageContext(_jspx_page_context);
/* 2744 */                                                 _jspx_th_logic_005fnotPresent_005f12.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                 
/* 2746 */                                                 _jspx_th_logic_005fnotPresent_005f12.setRole("OPERATOR");
/* 2747 */                                                 int _jspx_eval_logic_005fnotPresent_005f12 = _jspx_th_logic_005fnotPresent_005f12.doStartTag();
/* 2748 */                                                 if (_jspx_eval_logic_005fnotPresent_005f12 != 0) {
/*      */                                                   for (;;) {
/* 2750 */                                                     out.write("\n\t\t   <input type=\"checkbox\" name=\"select\" value=\"");
/* 2751 */                                                     out.print(resIdTOCheck);
/* 2752 */                                                     out.write("\" id=\"");
/* 2753 */                                                     out.print(resIdTOCheck);
/* 2754 */                                                     out.write("\" grpresid=\"");
/* 2755 */                                                     out.print(resIdTOCheck);
/* 2756 */                                                     out.write("\" onclick=\"selectAllChildCKbs('");
/* 2757 */                                                     out.print(resIdTOCheck);
/* 2758 */                                                     out.write("',this,this.form);\">\n\t\t   ");
/* 2759 */                                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f12.doAfterBody();
/* 2760 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 2764 */                                                 if (_jspx_th_logic_005fnotPresent_005f12.doEndTag() == 5) {
/* 2765 */                                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f12); return;
/*      */                                                 }
/*      */                                                 
/* 2768 */                                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f12);
/* 2769 */                                                 out.write(10);
/* 2770 */                                                 out.write(9);
/* 2771 */                                                 out.write(9);
/* 2772 */                                                 out.print(img);
/* 2773 */                                                 out.write(10);
/* 2774 */                                                 out.write(9);
/* 2775 */                                                 out.write(9);
/* 2776 */                                                 if ((site24x7List != null) && (site24x7List.containsKey(row.get(residIndex)))) {
/* 2777 */                                                   out.write("\n\t\t    <a href=\"javascript:MM_openBrWindow('");
/* 2778 */                                                   out.print(URLEncoder.encode((String)site24x7List.get(row.get(residIndex))));
/* 2779 */                                                   out.write("','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2780 */                                                   out.print(status);
/* 2781 */                                                   out.write(34);
/* 2782 */                                                   out.write(62);
/* 2783 */                                                   out.print(row.get(1));
/* 2784 */                                                   out.write("</a>\n\t\t");
/*      */                                                 }
/* 2786 */                                                 else if ((extDeviceMap != null) && (extDeviceMap.get(row.get(residIndex)) != null))
/*      */                                                 {
/* 2788 */                                                   String link1 = (String)extDeviceMap.get(row.get(residIndex));
/* 2789 */                                                   showeditLink = false;
/* 2790 */                                                   if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                                   {
/* 2792 */                                                     out.write("\n\t\t\t  <a href=");
/* 2793 */                                                     out.print(link1);
/* 2794 */                                                     out.write("  class=\"");
/* 2795 */                                                     out.print(status);
/* 2796 */                                                     out.write("\">  ");
/* 2797 */                                                     out.print(getTrimmedText((String)row.get(1), 45));
/* 2798 */                                                     out.write("</a>\";\n\t\t ");
/*      */                                                   }
/*      */                                                   else
/*      */                                                   {
/* 2802 */                                                     out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('");
/* 2803 */                                                     out.print(link1);
/* 2804 */                                                     out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2805 */                                                     out.print(status);
/* 2806 */                                                     out.write(34);
/* 2807 */                                                     out.write(62);
/* 2808 */                                                     out.print(getTrimmedText((String)row.get(1), 45));
/* 2809 */                                                     out.write("</a>\n\t\t ");
/*      */                                                   }
/*      */                                                 }
/* 2812 */                                                 else if (ChildMOHandler.isChildMonitorTypeSupportedForMG((String)row.get(2)))
/*      */                                                 {
/*      */ 
/* 2815 */                                                   out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('/showapplication.do?method=showChildApplicationDetail&resId=");
/* 2816 */                                                   out.print(row.get(0));
/* 2817 */                                                   out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2818 */                                                   out.print(status);
/* 2819 */                                                   out.write(34);
/* 2820 */                                                   out.write(62);
/* 2821 */                                                   out.print(row.get(1));
/* 2822 */                                                   out.write("</a>\n\t\t");
/*      */                                                 }
/*      */                                                 else {
/* 2825 */                                                   out.write(" \n\t\t\t<a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2826 */                                                   out.print(row.get(residIndex));
/* 2827 */                                                   out.write("&haid=");
/* 2828 */                                                   out.print(haidininventory);
/* 2829 */                                                   out.write("\" class=\"");
/* 2830 */                                                   out.print(status);
/* 2831 */                                                   out.write(34);
/* 2832 */                                                   out.write(62);
/* 2833 */                                                   out.print(row.get(1));
/* 2834 */                                                   out.write("</a>\n\t\t");
/*      */                                                 }
/* 2836 */                                                 out.write("\n\t\t\t\n\t\t\t\n      ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 2840 */                                               out.write(10);
/* 2841 */                                               out.write(32);
/*      */                                               
/* 2843 */                                               String editlink = "/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + resIdTOCheck;
/*      */                                               
/* 2845 */                                               out.write(10);
/* 2846 */                                               out.write(9);
/* 2847 */                                               out.write(9);
/*      */                                               
/* 2849 */                                               IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2850 */                                               _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 2851 */                                               _jspx_th_c_005fif_005f25.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 2853 */                                               _jspx_th_c_005fif_005f25.setTest("${forInventory==\"true\"}");
/* 2854 */                                               int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 2855 */                                               if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                                                 for (;;) {
/* 2857 */                                                   out.write("\n\t\t\t");
/*      */                                                   
/* 2859 */                                                   editlink = "/showapplication.do?method=editApplication&haid=" + resIdTOCheck;
/*      */                                                   
/* 2861 */                                                   out.write(10);
/* 2862 */                                                   out.write(9);
/* 2863 */                                                   out.write(9);
/* 2864 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 2865 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2869 */                                               if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 2870 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                                               }
/*      */                                               
/* 2873 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 2874 */                                               out.write(10);
/* 2875 */                                               out.write(9);
/* 2876 */                                               out.write(9);
/*      */                                               
/* 2878 */                                               IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2879 */                                               _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 2880 */                                               _jspx_th_c_005fif_005f26.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 2882 */                                               _jspx_th_c_005fif_005f26.setTest("${notMG==\"true\"}");
/* 2883 */                                               int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 2884 */                                               if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                                                 for (;;) {
/* 2886 */                                                   out.write(10);
/* 2887 */                                                   out.write(9);
/* 2888 */                                                   out.write(9);
/* 2889 */                                                   editlink = "/showresource.do?method=showResourceForResourceID&editPage=true&resourceid=" + resIdTOCheck;
/*      */                                                   
/* 2891 */                                                   out.write("\n                ");
/* 2892 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 2893 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2897 */                                               if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 2898 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                                               }
/*      */                                               
/* 2901 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 2902 */                                               out.write("\n\t    ");
/*      */                                               
/* 2904 */                                               if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                               {
/* 2906 */                                                 editlink = "/it360/createBS.do?method=editApplication&haid=" + resIdTOCheck;
/*      */                                               }
/*      */                                               
/* 2909 */                                               out.write("\n\t\t<td  class=\"whitegrayrightalign\" width=\"15%\"  align=\"left\" ><table><tr class='whitegrayrightalign'><td>\n               <div id=\"mgaction\" class=\"actionborder\">\n\t\t");
/*      */                                               
/* 2911 */                                               PresentTag _jspx_th_logic_005fpresent_005f16 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2912 */                                               _jspx_th_logic_005fpresent_005f16.setPageContext(_jspx_page_context);
/* 2913 */                                               _jspx_th_logic_005fpresent_005f16.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 2915 */                                               _jspx_th_logic_005fpresent_005f16.setRole("ADMIN");
/* 2916 */                                               int _jspx_eval_logic_005fpresent_005f16 = _jspx_th_logic_005fpresent_005f16.doStartTag();
/* 2917 */                                               if (_jspx_eval_logic_005fpresent_005f16 != 0) {
/*      */                                                 for (;;) {
/* 2919 */                                                   out.write(10);
/* 2920 */                                                   out.write(9);
/* 2921 */                                                   out.write(9);
/*      */                                                   
/* 2923 */                                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f13 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2924 */                                                   _jspx_th_logic_005fnotPresent_005f13.setPageContext(_jspx_page_context);
/* 2925 */                                                   _jspx_th_logic_005fnotPresent_005f13.setParent(_jspx_th_logic_005fpresent_005f16);
/*      */                                                   
/* 2927 */                                                   _jspx_th_logic_005fnotPresent_005f13.setRole("DEMO,ENTERPRISEADMIN");
/* 2928 */                                                   int _jspx_eval_logic_005fnotPresent_005f13 = _jspx_th_logic_005fnotPresent_005f13.doStartTag();
/* 2929 */                                                   if (_jspx_eval_logic_005fnotPresent_005f13 != 0) {
/*      */                                                     for (;;) {
/* 2931 */                                                       out.write("\n\t\t\n\t\t");
/* 2932 */                                                       if ((showeditLink) && (site24x7List != null) && (!site24x7List.containsKey(row.get(residIndex)))) {
/* 2933 */                                                         out.write("\n\t\t    ");
/*      */                                                         
/* 2935 */                                                         AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2936 */                                                         _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 2937 */                                                         _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fnotPresent_005f13);
/*      */                                                         
/* 2939 */                                                         _jspx_th_am_005fadminlink_005f0.setHref(editlink);
/*      */                                                         
/* 2941 */                                                         _jspx_th_am_005fadminlink_005f0.setEnableClass("monitorgp-links");
/* 2942 */                                                         int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 2943 */                                                         if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 2944 */                                                           if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2945 */                                                             out = _jspx_page_context.pushBody();
/* 2946 */                                                             _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 2947 */                                                             _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                                           }
/*      */                                                           for (;;) {
/* 2950 */                                                             out.write("<img src=\"/images/icon_edit.gif\" align=\"center\" border=\"0\"  title=\"");
/* 2951 */                                                             out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 2952 */                                                             out.write(34);
/* 2953 */                                                             out.write(47);
/* 2954 */                                                             out.write(62);
/* 2955 */                                                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 2956 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/* 2959 */                                                           if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2960 */                                                             out = _jspx_page_context.popBody();
/*      */                                                           }
/*      */                                                         }
/* 2963 */                                                         if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 2964 */                                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                                         }
/*      */                                                         
/* 2967 */                                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 2968 */                                                         out.write(32);
/* 2969 */                                                         out.write(10);
/* 2970 */                                                         out.write(9);
/* 2971 */                                                         out.write(9);
/*      */                                                       }
/* 2973 */                                                       out.write("\t\t   \n\t\t   ");
/* 2974 */                                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f13.doAfterBody();
/* 2975 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 2979 */                                                   if (_jspx_th_logic_005fnotPresent_005f13.doEndTag() == 5) {
/* 2980 */                                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f13); return;
/*      */                                                   }
/*      */                                                   
/* 2983 */                                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f13);
/* 2984 */                                                   out.write(10);
/* 2985 */                                                   out.write(9);
/* 2986 */                                                   out.write(9);
/* 2987 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f16.doAfterBody();
/* 2988 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2992 */                                               if (_jspx_th_logic_005fpresent_005f16.doEndTag() == 5) {
/* 2993 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16); return;
/*      */                                               }
/*      */                                               
/* 2996 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/* 2997 */                                               out.write("\n\t\t&nbsp;&nbsp;\n\t\t");
/*      */                                               
/* 2999 */                                               PresentTag _jspx_th_logic_005fpresent_005f17 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3000 */                                               _jspx_th_logic_005fpresent_005f17.setPageContext(_jspx_page_context);
/* 3001 */                                               _jspx_th_logic_005fpresent_005f17.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 3003 */                                               _jspx_th_logic_005fpresent_005f17.setRole("ADMIN");
/* 3004 */                                               int _jspx_eval_logic_005fpresent_005f17 = _jspx_th_logic_005fpresent_005f17.doStartTag();
/* 3005 */                                               if (_jspx_eval_logic_005fpresent_005f17 != 0) {
/*      */                                                 for (;;) {
/* 3007 */                                                   out.write(10);
/* 3008 */                                                   out.write(9);
/* 3009 */                                                   out.write(9);
/*      */                                                   
/* 3011 */                                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f14 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3012 */                                                   _jspx_th_logic_005fnotPresent_005f14.setPageContext(_jspx_page_context);
/* 3013 */                                                   _jspx_th_logic_005fnotPresent_005f14.setParent(_jspx_th_logic_005fpresent_005f17);
/*      */                                                   
/* 3015 */                                                   _jspx_th_logic_005fnotPresent_005f14.setRole("DEMO,ENTERPRISEADMIN");
/* 3016 */                                                   int _jspx_eval_logic_005fnotPresent_005f14 = _jspx_th_logic_005fnotPresent_005f14.doStartTag();
/* 3017 */                                                   if (_jspx_eval_logic_005fnotPresent_005f14 != 0) {
/*      */                                                     for (;;) {
/* 3019 */                                                       out.write("\n\t\t    ");
/*      */                                                       
/* 3021 */                                                       IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3022 */                                                       _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 3023 */                                                       _jspx_th_c_005fif_005f27.setParent(_jspx_th_logic_005fnotPresent_005f14);
/*      */                                                       
/* 3025 */                                                       _jspx_th_c_005fif_005f27.setTest("${notMG!=\"true\"}");
/* 3026 */                                                       int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 3027 */                                                       if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */                                                         for (;;) {
/* 3029 */                                                           out.write("\n                     <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 3030 */                                                           out.print(resIdTOCheck);
/* 3031 */                                                           out.write("\"> <img src=\" /images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"");
/* 3032 */                                                           out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 3033 */                                                           out.write("\"/></a>\n\t\t     ");
/* 3034 */                                                           int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 3035 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/*      */                                                       }
/* 3039 */                                                       if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 3040 */                                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27); return;
/*      */                                                       }
/*      */                                                       
/* 3043 */                                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 3044 */                                                       out.write("\n\t\t    ");
/*      */                                                       
/* 3046 */                                                       IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3047 */                                                       _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 3048 */                                                       _jspx_th_c_005fif_005f28.setParent(_jspx_th_logic_005fnotPresent_005f14);
/*      */                                                       
/* 3050 */                                                       _jspx_th_c_005fif_005f28.setTest("${notMG==\"true\"}");
/* 3051 */                                                       int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 3052 */                                                       if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */                                                         for (;;) {
/* 3054 */                                                           out.write("\n\t\t      <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=");
/* 3055 */                                                           out.print(resIdTOCheck);
/* 3056 */                                                           out.write("\"> <img src=\" /images/icon_associateaction.gif\" border=\"0\" align=\"center\" title=\"");
/* 3057 */                                                           out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 3058 */                                                           out.write("\"/></a>\n                     ");
/* 3059 */                                                           int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 3060 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/*      */                                                       }
/* 3064 */                                                       if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 3065 */                                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28); return;
/*      */                                                       }
/*      */                                                       
/* 3068 */                                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 3069 */                                                       out.write("\n                     ");
/* 3070 */                                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f14.doAfterBody();
/* 3071 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 3075 */                                                   if (_jspx_th_logic_005fnotPresent_005f14.doEndTag() == 5) {
/* 3076 */                                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f14); return;
/*      */                                                   }
/*      */                                                   
/* 3079 */                                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f14);
/* 3080 */                                                   out.write(10);
/* 3081 */                                                   out.write(9);
/* 3082 */                                                   out.write(9);
/* 3083 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f17.doAfterBody();
/* 3084 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3088 */                                               if (_jspx_th_logic_005fpresent_005f17.doEndTag() == 5) {
/* 3089 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17); return;
/*      */                                               }
/*      */                                               
/* 3092 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17);
/* 3093 */                                               out.write("\n\t\t&nbsp;&nbsp;\n\t\t");
/*      */                                               
/* 3095 */                                               PresentTag _jspx_th_logic_005fpresent_005f18 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3096 */                                               _jspx_th_logic_005fpresent_005f18.setPageContext(_jspx_page_context);
/* 3097 */                                               _jspx_th_logic_005fpresent_005f18.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 3099 */                                               _jspx_th_logic_005fpresent_005f18.setRole("ADMIN");
/* 3100 */                                               int _jspx_eval_logic_005fpresent_005f18 = _jspx_th_logic_005fpresent_005f18.doStartTag();
/* 3101 */                                               if (_jspx_eval_logic_005fpresent_005f18 != 0) {
/*      */                                                 for (;;) {
/* 3103 */                                                   out.write("\n\t\t ");
/*      */                                                   
/* 3105 */                                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f15 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3106 */                                                   _jspx_th_logic_005fnotPresent_005f15.setPageContext(_jspx_page_context);
/* 3107 */                                                   _jspx_th_logic_005fnotPresent_005f15.setParent(_jspx_th_logic_005fpresent_005f18);
/*      */                                                   
/* 3109 */                                                   _jspx_th_logic_005fnotPresent_005f15.setRole("DEMO,ENTERPRISEADMIN");
/* 3110 */                                                   int _jspx_eval_logic_005fnotPresent_005f15 = _jspx_th_logic_005fnotPresent_005f15.doStartTag();
/* 3111 */                                                   if (_jspx_eval_logic_005fnotPresent_005f15 != 0) {
/*      */                                                     for (;;) {
/* 3113 */                                                       out.write("\n\t\t    ");
/*      */                                                       
/* 3115 */                                                       IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3116 */                                                       _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 3117 */                                                       _jspx_th_c_005fif_005f29.setParent(_jspx_th_logic_005fnotPresent_005f15);
/*      */                                                       
/* 3119 */                                                       _jspx_th_c_005fif_005f29.setTest("${forInventory==\"true\"}");
/* 3120 */                                                       int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 3121 */                                                       if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */                                                         for (;;) {
/* 3123 */                                                           out.write("\n                     <a href=\"javascript: removeMonitorFromGroup ('");
/* 3124 */                                                           if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f29, _jspx_page_context))
/*      */                                                             return;
/* 3126 */                                                           out.write(39);
/* 3127 */                                                           out.write(44);
/* 3128 */                                                           out.write(39);
/* 3129 */                                                           out.print(resIdTOCheck);
/* 3130 */                                                           out.write(39);
/* 3131 */                                                           out.write(44);
/* 3132 */                                                           out.write(39);
/* 3133 */                                                           if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f29, _jspx_page_context))
/*      */                                                             return;
/* 3135 */                                                           out.write("');\"> <img src=\"/images/icon_removefromgroup.gif\" align=\"center\" border=\"0\" title=\"");
/* 3136 */                                                           out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 3137 */                                                           out.write("\"/></a>\n\t\t\t&nbsp;&nbsp;\n                    ");
/* 3138 */                                                           int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 3139 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/*      */                                                       }
/* 3143 */                                                       if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 3144 */                                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29); return;
/*      */                                                       }
/*      */                                                       
/* 3147 */                                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 3148 */                                                       out.write("\n\t\t ");
/* 3149 */                                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f15.doAfterBody();
/* 3150 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 3154 */                                                   if (_jspx_th_logic_005fnotPresent_005f15.doEndTag() == 5) {
/* 3155 */                                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f15); return;
/*      */                                                   }
/*      */                                                   
/* 3158 */                                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f15);
/* 3159 */                                                   out.write("\n                ");
/* 3160 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f18.doAfterBody();
/* 3161 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3165 */                                               if (_jspx_th_logic_005fpresent_005f18.doEndTag() == 5) {
/* 3166 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18); return;
/*      */                                               }
/*      */                                               
/* 3169 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18);
/* 3170 */                                               out.write("\n\t\n\t\t");
/*      */                                               
/* 3172 */                                               PresentTag _jspx_th_logic_005fpresent_005f19 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3173 */                                               _jspx_th_logic_005fpresent_005f19.setPageContext(_jspx_page_context);
/* 3174 */                                               _jspx_th_logic_005fpresent_005f19.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 3176 */                                               _jspx_th_logic_005fpresent_005f19.setRole("ADMIN");
/* 3177 */                                               int _jspx_eval_logic_005fpresent_005f19 = _jspx_th_logic_005fpresent_005f19.doStartTag();
/* 3178 */                                               if (_jspx_eval_logic_005fpresent_005f19 != 0) {
/*      */                                                 for (;;) {
/* 3180 */                                                   out.write(10);
/* 3181 */                                                   out.write(9);
/* 3182 */                                                   out.write(9);
/*      */                                                   
/* 3184 */                                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f16 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3185 */                                                   _jspx_th_logic_005fnotPresent_005f16.setPageContext(_jspx_page_context);
/* 3186 */                                                   _jspx_th_logic_005fnotPresent_005f16.setParent(_jspx_th_logic_005fpresent_005f19);
/*      */                                                   
/* 3188 */                                                   _jspx_th_logic_005fnotPresent_005f16.setRole("DEMO,ENTERPRISEADMIN");
/* 3189 */                                                   int _jspx_eval_logic_005fnotPresent_005f16 = _jspx_th_logic_005fnotPresent_005f16.doStartTag();
/* 3190 */                                                   if (_jspx_eval_logic_005fnotPresent_005f16 != 0) {
/*      */                                                     for (;;) {
/* 3192 */                                                       out.write("\n\t       ");
/*      */                                                       
/*      */ 
/* 3195 */                                                       if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                                       {
/*      */ 
/* 3198 */                                                         out.write("\n\t\t    <a href=\"/it360/createBS.do?method=getNetworkDevices&productName=OpManager&haid=");
/* 3199 */                                                         out.print(resIdTOCheck);
/* 3200 */                                                         out.write("\" class=\"monitorgp-links\" ><img src=\" /images/icon_assoicatemonitors.gif\" border=\"0\" align=\"center\"  title=\"");
/* 3201 */                                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text"));
/* 3202 */                                                         out.write("\"/></a>\n\t       ");
/*      */ 
/*      */                                                       }
/* 3205 */                                                       else if (("HAI".equals(resourceType)) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType)) && (!"1013".equals(haiGroupType)))
/*      */                                                       {
/*      */ 
/* 3208 */                                                         out.write("\n\t\t    <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=");
/* 3209 */                                                         out.print(resIdTOCheck);
/* 3210 */                                                         out.write("&fromwhere=monitorgroupview\" class=\"monitorgp-links\" ><img src=\" /images/icon_assoicatemonitors.gif\" align=\"center\" border=\"0\"  title=\"");
/* 3211 */                                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text"));
/* 3212 */                                                         out.write("\"/></a>\n\t       ");
/*      */                                                       }
/*      */                                                       
/*      */ 
/* 3216 */                                                       out.write("\n\t\t    ");
/* 3217 */                                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f16.doAfterBody();
/* 3218 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 3222 */                                                   if (_jspx_th_logic_005fnotPresent_005f16.doEndTag() == 5) {
/* 3223 */                                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f16); return;
/*      */                                                   }
/*      */                                                   
/* 3226 */                                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f16);
/* 3227 */                                                   out.write("\n                ");
/* 3228 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f19.doAfterBody();
/* 3229 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3233 */                                               if (_jspx_th_logic_005fpresent_005f19.doEndTag() == 5) {
/* 3234 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f19); return;
/*      */                                               }
/*      */                                               
/* 3237 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f19);
/* 3238 */                                               out.write("\n                \t\t&nbsp;&nbsp;\n\t\t\t");
/*      */                                               
/* 3240 */                                               PresentTag _jspx_th_logic_005fpresent_005f20 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3241 */                                               _jspx_th_logic_005fpresent_005f20.setPageContext(_jspx_page_context);
/* 3242 */                                               _jspx_th_logic_005fpresent_005f20.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 3244 */                                               _jspx_th_logic_005fpresent_005f20.setRole("ADMIN,ENTERPRISEADMIN");
/* 3245 */                                               int _jspx_eval_logic_005fpresent_005f20 = _jspx_th_logic_005fpresent_005f20.doStartTag();
/* 3246 */                                               if (_jspx_eval_logic_005fpresent_005f20 != 0) {
/*      */                                                 for (;;) {
/* 3248 */                                                   out.write("\n\t\t <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=");
/* 3249 */                                                   out.print(resIdTOCheck);
/* 3250 */                                                   out.write("&mgview=true')\"  enableClass=\"monitorgp-links\" ><img src=\"/images/icon_assigncustomfields.gif\" align=\"center\" border=\"0\"  title=\"");
/* 3251 */                                                   out.print(FormatUtil.getString("am.myfield.assign.text"));
/* 3252 */                                                   out.write("\"/></a> ");
/* 3253 */                                                   out.write("\n        ");
/* 3254 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f20.doAfterBody();
/* 3255 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3259 */                                               if (_jspx_th_logic_005fpresent_005f20.doEndTag() == 5) {
/* 3260 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f20); return;
/*      */                                               }
/*      */                                               
/* 3263 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f20);
/* 3264 */                                               out.write("\n\t\t</td></tr></table></div>\n         </td>\n\n                <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n                ");
/*      */                                               
/* 3266 */                                               SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3267 */                                               _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3268 */                                               _jspx_th_c_005fset_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 3270 */                                               _jspx_th_c_005fset_005f1.setVar("key");
/* 3271 */                                               int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3272 */                                               if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3273 */                                                 if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3274 */                                                   out = _jspx_page_context.pushBody();
/* 3275 */                                                   _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 3276 */                                                   _jspx_th_c_005fset_005f1.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3279 */                                                   out.write(32);
/* 3280 */                                                   out.print(row.get(residIndex) + "#" + availid + "#" + "MESSAGE");
/* 3281 */                                                   int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3282 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3285 */                                                 if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3286 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3289 */                                               if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3290 */                                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                                               }
/*      */                                               
/* 3293 */                                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3294 */                                               out.write("\n\n         <td  class=\"whitegrayrightalign\" align=\"center\" width=\"8%\" ><a href=\"javascript:void(0)\" ");
/*      */                                               
/* 3296 */                                               IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3297 */                                               _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 3298 */                                               _jspx_th_c_005fif_005f30.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 3300 */                                               _jspx_th_c_005fif_005f30.setTest("${alert[key]!=null}");
/* 3301 */                                               int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 3302 */                                               if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */                                                 for (;;) {
/* 3304 */                                                   out.write(" onmouseover=\"ddrivetip(this,event,'");
/* 3305 */                                                   if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                                                     return;
/* 3307 */                                                   out.write("<br>'+v+'");
/* 3308 */                                                   out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 3309 */                                                   out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3310 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 3311 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3315 */                                               if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 3316 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30); return;
/*      */                                               }
/*      */                                               
/* 3319 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 3320 */                                               out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3321 */                                               out.print(row.get(residIndex));
/* 3322 */                                               out.write("&attributeid=");
/* 3323 */                                               out.print(availid);
/* 3324 */                                               out.write("&alertconfigurl=");
/* 3325 */                                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + row.get(residIndex) + "&attributeIDs=" + healthid + "," + availid + "&attributeToSelect=" + availid + "&redirectto=" + URLEncoder.encode(new StringBuilder().append("/showapplication.do?haid=").append(row.get(residIndex)).append("&method=showApplication").toString())));
/* 3326 */                                               out.write("')\">\n                ");
/* 3327 */                                               out.print(getSeverityImageForAvailability(alert.getProperty(row.get(residIndex) + "#" + availid)));
/* 3328 */                                               out.write(" </a></td>\n  \t        ");
/*      */                                               
/* 3330 */                                               SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3331 */                                               _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3332 */                                               _jspx_th_c_005fset_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 3334 */                                               _jspx_th_c_005fset_005f2.setVar("key");
/* 3335 */                                               int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3336 */                                               if (_jspx_eval_c_005fset_005f2 != 0) {
/* 3337 */                                                 if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3338 */                                                   out = _jspx_page_context.pushBody();
/* 3339 */                                                   _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 3340 */                                                   _jspx_th_c_005fset_005f2.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3343 */                                                   out.write(32);
/* 3344 */                                                   out.print(row.get(residIndex) + "#" + availid + "#" + "MESSAGE");
/* 3345 */                                                   int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3346 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3349 */                                                 if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3350 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3353 */                                               if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3354 */                                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                                               }
/*      */                                               
/* 3357 */                                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3358 */                                               out.write("\n                <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\t ");
/*      */                                               
/* 3360 */                                               SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3361 */                                               _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3362 */                                               _jspx_th_c_005fset_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 3364 */                                               _jspx_th_c_005fset_005f3.setVar("key");
/* 3365 */                                               int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3366 */                                               if (_jspx_eval_c_005fset_005f3 != 0) {
/* 3367 */                                                 if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3368 */                                                   out = _jspx_page_context.pushBody();
/* 3369 */                                                   _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 3370 */                                                   _jspx_th_c_005fset_005f3.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3373 */                                                   out.write(32);
/* 3374 */                                                   out.print(row.get(residIndex) + "#" + healthid + "#" + "MESSAGE");
/* 3375 */                                                   int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 3376 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3379 */                                                 if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3380 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3383 */                                               if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3384 */                                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                                               }
/*      */                                               
/* 3387 */                                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 3388 */                                               out.write("\n         <td  class=\"whitegrayrightalign\" align=\"center\" width=\"7%\">");
/* 3389 */                                               if (!"Service".equals((String)row.get(2))) {
/* 3390 */                                                 out.write("<a href=\"javascript:void(-1)\" ");
/*      */                                                 
/* 3392 */                                                 IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3393 */                                                 _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 3394 */                                                 _jspx_th_c_005fif_005f31.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                 
/* 3396 */                                                 _jspx_th_c_005fif_005f31.setTest("${alert[key]!=null}");
/* 3397 */                                                 int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 3398 */                                                 if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */                                                   for (;;) {
/* 3400 */                                                     out.write(" onmouseover=\"ddrivetip(this,event,'");
/* 3401 */                                                     if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                                       return;
/* 3403 */                                                     out.write("<br>'+v+'");
/* 3404 */                                                     out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 3405 */                                                     out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3406 */                                                     int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 3407 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 3411 */                                                 if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 3412 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31); return;
/*      */                                                 }
/*      */                                                 
/* 3415 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 3416 */                                                 out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3417 */                                                 out.print(row.get(residIndex));
/* 3418 */                                                 out.write("&attributeid=");
/* 3419 */                                                 out.print(healthid);
/* 3420 */                                                 out.write("&alertconfigurl=");
/* 3421 */                                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + row.get(residIndex) + "&attributeIDs=" + healthid + "," + availid + "&attributeToSelect=" + healthid + "&redirectto=" + URLEncoder.encode(new StringBuilder().append("/showapplication.do?haid=").append(row.get(residIndex)).append("&method=showApplication").toString())));
/* 3422 */                                                 out.write("')\">\n                ");
/* 3423 */                                                 out.print(getSeverityImageForHealth(alert.getProperty(row.get(residIndex) + "#" + healthid)));
/* 3424 */                                                 out.write("</a>");
/*      */                                               }
/* 3426 */                                               out.write("</td>\n     <td  class=\"whitegrayrightalign\" align=\"left\" width=\"8%\" > <img src=\" ");
/* 3427 */                                               out.print(actionimage);
/* 3428 */                                               out.write("\" border=\"0\"   title=\"");
/* 3429 */                                               out.print(actionmessage);
/* 3430 */                                               out.write("\"/>&nbsp;&nbsp;</td>\n    </tr>\n    ");
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 3435 */                                               if (!checkRow) {
/* 3436 */                                                 mgresourceid = resIdTOCheck;
/* 3437 */                                                 rowID = "expandRow";
/* 3438 */                                                 checkRow = true;
/*      */                                               }
/*      */                                               
/* 3441 */                                               out.write("\t  \n     ");
/*      */                                             }
/* 3443 */                                             out.write(10);
/* 3444 */                                             out.write(9);
/* 3445 */                                             out.write(32);
/*      */                                             
/* 3447 */                                             if (chilmos.get(resIdTOCheck + "") != null)
/*      */                                             {
/* 3449 */                                               ArrayList singlechilmos = (ArrayList)chilmos.get(resIdTOCheck + "");
/* 3450 */                                               if ((mgresourceid != null) && (mgresourceid.equals(resIdTOCheck))) {
/* 3451 */                                                 monitorlist = singlechilmos.size();
/*      */                                               }
/* 3453 */                                               Hashtable availhealth = new Hashtable();
/* 3454 */                                               availhealth.put("avail", availabilitykeys);
/* 3455 */                                               availhealth.put("health", healthkeys);
/* 3456 */                                               availhealth.put("alert", alert);
/* 3457 */                                               String toappend = getAllChildNodestoDisplay(singlechilmos, resIdTOCheck + "", resIdTOCheck + "", chilmos, availhealth, 1, request, extDeviceMap, site24x7List);
/* 3458 */                                               out.println(toappend);
/*      */                                             }
/* 3460 */                                             else if (("HAI".equals(resourceType)) && (isInventory))
/*      */                                             {
/*      */ 
/* 3463 */                                               out.write("\n\t  <tr class=\"whitegrayrightalign\" id=\"#monitor");
/* 3464 */                                               out.print(resIdTOCheck);
/* 3465 */                                               out.write("\"  style=\"display:");
/* 3466 */                                               out.print(rowStyle);
/* 3467 */                                               out.write(";\">\n\t <td class=\"whitegrayrightalign\" width=3% >&nbsp;</td>\n\t <td class=\"whitegrayrightalign\"  colspan=\"5\" style='padding-left:40px' >\n\t       <span class=\"bodytext\">");
/* 3468 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text"));
/* 3469 */                                               out.write("</span>\n\t       ");
/*      */                                               
/* 3471 */                                               PresentTag _jspx_th_logic_005fpresent_005f21 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3472 */                                               _jspx_th_logic_005fpresent_005f21.setPageContext(_jspx_page_context);
/* 3473 */                                               _jspx_th_logic_005fpresent_005f21.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 3475 */                                               _jspx_th_logic_005fpresent_005f21.setRole("ADMIN");
/* 3476 */                                               int _jspx_eval_logic_005fpresent_005f21 = _jspx_th_logic_005fpresent_005f21.doStartTag();
/* 3477 */                                               if (_jspx_eval_logic_005fpresent_005f21 != 0) {
/*      */                                                 for (;;) {
/* 3479 */                                                   out.write("\n\t       ");
/*      */                                                   
/*      */ 
/* 3482 */                                                   if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                                   {
/*      */ 
/* 3485 */                                                     out.write("\n\t       <span class=\"bodytext\"> ");
/* 3486 */                                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 3487 */                                                     out.write(" <a href=\"/it360/createBS.do?method=getNetworkDevices&productName=OpManager&haid=");
/* 3488 */                                                     out.print(resIdTOCheck);
/* 3489 */                                                     out.write("\" class=\"monitorgp-links\">");
/* 3490 */                                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 3491 */                                                     out.write("</span>\n\t       ");
/*      */ 
/*      */                                                   }
/*      */                                                   else
/*      */                                                   {
/*      */ 
/* 3497 */                                                     out.write("\n\t       <span class=\"bodytext\">");
/* 3498 */                                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 3499 */                                                     out.write(" <a href=\"/showresource.do?type=All&method=getMonitorForm&haid=");
/* 3500 */                                                     out.print(resIdTOCheck);
/* 3501 */                                                     out.write("&fromwhere=monitorgroupview\" class=\"monitorgp-links\">");
/* 3502 */                                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 3503 */                                                     out.write("</span>\n\t       ");
/*      */                                                   }
/*      */                                                   
/*      */ 
/* 3507 */                                                   out.write("\n\t       ");
/* 3508 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f21.doAfterBody();
/* 3509 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3513 */                                               if (_jspx_th_logic_005fpresent_005f21.doEndTag() == 5) {
/* 3514 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f21); return;
/*      */                                               }
/*      */                                               
/* 3517 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f21);
/* 3518 */                                               out.write(" </td>\n\t</tr>\n\t     ");
/*      */                                             }
/* 3520 */                                             out.write(10);
/* 3521 */                                             out.write(9);
/* 3522 */                                             out.write(32);
/*      */                                           }
/* 3524 */                                           out.write(10);
/* 3525 */                                           out.write(9);
/* 3526 */                                           out.write(32);
/* 3527 */                                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3528 */                                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3529 */                                           i = (Integer)_jspx_page_context.findAttribute("i");
/* 3530 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3533 */                                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3534 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3537 */                                       if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3538 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                       }
/*      */                                       
/* 3541 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3542 */                                       out.write(10);
/* 3543 */                                       out.write(9);
/* 3544 */                                       out.write(32);
/* 3545 */                                       if (showmore)
/*      */                                       {
/* 3547 */                                         out.write("\n\t <tr  id=\"#monitororphaned\" class=\"leftcells\" height=\"35\"> ");
/* 3548 */                                         out.write("\n\t <td class=\"whitegrayrightalign\" width=3% >&nbsp;</td>\n\t <td class=\"whitegrayrightalign\" align=\"right\" colspan=\"5\" style=\"padding-right:40px\" >\t       \n\t       <a href=\"javascript:void(0);\" class=\"show-tag\"  onclick=\"showmoremonitors('/showresource.do?method=showMonitorGroupViewIndividual&parents=orphaned&lindx=1&pageno=");
/* 3549 */                                         out.print(pageno);
/* 3550 */                                         out.write("',this,'true')\">\n\t\t   ");
/* 3551 */                                         out.print(FormatUtil.getString("am.webclient.configurealert.showmore"));
/* 3552 */                                         out.write("</a> \t\t   \n\t\t   </td>\n\t</tr>  \n\t ");
/*      */                                       }
/* 3554 */                                       out.write(10);
/* 3555 */                                       out.write(9);
/* 3556 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3557 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3561 */                                   if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3562 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/*      */                                   }
/*      */                                   else {
/* 3565 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3566 */                                     out.write("\t\n</table>\n\n\n</form>\n </td>\n </tr>\n</table>\n\n<script> \t\t\n\t\t\t\t\n                           $(window).load(function() {//No I18N\n\t\t\t\t\t\t   try{\n\t\t\t\t\t\t   \n\t\t\t\t\t\t   ");
/* 3567 */                                     if ("treeview".equals(viewtype)) {
/* 3568 */                                       out.write("\t\t\t\t\t\t   \n\t\t\t\t\t\t   toggleallDivs('show','false');\t//No I18N \t\t\t\t\t   \n\t\t\t\t\t\t   ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3572 */                                       out.write("\n                        \t   retainstructure(\"");
/* 3573 */                                       out.print(request.getParameter("retaintree"));
/* 3574 */                                       out.write("\");\n\t\t\t\t\t\t\t   ");
/*      */                                     }
/* 3576 */                                     out.write("\n\t\t\t\t $(\"#allMonitorGroups tr div[id^=mgaction]\").css('display','none');//No I18N\n                                 $(\"#allMonitorGroups tr[id^=#monitor]\").hover(function() {//No I18N\n\n                                        $(this).find(\"div[id^=mgaction]\").css('display','inline'); //No I18N\n\n                                },\n                                function () {\n                                          $(this).find(\"div[id^=mgaction]\").css('display','none'); //No I18N\n\n                                });\n\t\t\t\t\t\t\t\t\n                                 var actionid=getCookie('actionid');//No I18N                             \t\t\n\t\t\t\t\t\t\t\tif(actionid==undefined){\n                             \t\t\t\tactionid=\"showall\"; //No I18N\n                             \t\t\t}\n                             \tif(document.deletemonitor.operation)\n                             \t\tdocument.deletemonitor.operation.selectedIndex=0;\n                             ");
/*      */                                     
/* 3578 */                                     String retaintree = (String)request.getAttribute("retaintree");
/*      */                                     
/* 3580 */                                     out.write("\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/* 3581 */                                     if ((retaintree != null) && (!retaintree.equals("null")) && (!retaintree.trim().equals("")))
/*      */                                     {
/*      */ 
/* 3584 */                                       out.write("\t\t\tvar temp1,temp2;\n                             \t\t\ttemp1='");
/* 3585 */                                       out.print(retaintree);
/* 3586 */                                       out.write("';//No I18N\n                             \t\t\ttemp2 =temp1.split(\"$\");//No I18N\n                             \t\t\tfor(h=0;h<temp2.length;h++)\n                             \t\t\t{\n                             \t\t\t\tparentresid=temp2[h];//No I18N                             \t\t\t\n                             \t\t\t}\n                             ");
/*      */                                     }
/*      */                                     
/* 3589 */                                     out.write("\n                             loadURLType(\"");
/* 3590 */                                     out.print(customValue);
/* 3591 */                                     out.write("\",null,null,false);\n}\n\t\t\t\tcatch(err){\n\t\t\t\t\t\tconsole.log('err document ready    '+err)\n\t\t\t\t\t\t}\t\t\t\t\t\t\n\t\t\t\t\t\t$('.chzn-select').chosen();\t\n                        });\t\n\t\t\t\t\t\t\n\t\t\t\t\t\tfunction retainstructure(structure)\n\t\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\ttry{\n\t\t\t\t\t\tvar array = structure.split('$');\t\t\t\t\t\t\n\t\t\t\t\t\tfor (var i=0;i<array.length;i++)\n\t\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\tvar tree=array[i];\n\t\t\t\t\t\tvar grpids=tree.split('|');\n\t\t\t\t\t\tvar idx=grpids.length;\t\t\t\t\t\t\n\t\t\t\t\t\tvar resourceid=grpids[idx-1];\t\t\t\t\t\t\t\t\t\t\t\t\t\n                        expandgroup(resourceid,idx,tree); \t \n\t\t\t\t\t\t}\n\t\t\t\t\t\tsethovertomgaction();\n\t\t\t\t\t\t}\n\t\t\t\t\t\tcatch(err)\n\t\t\t\t\t\t{\n\t\t\t\t\t\tconsole.log('err  in retainstructure   '+err);\t\t\t\t\t\t\n\t\t\t\t\t\t}\n\t\t\t\t\t\t}\n\t\t\t\t\t\tfunction expandgroup(resourceid,idx,tree){ \n\t\t\t\t\t\ttry{\n\t\t\t\t\t\t\tvar aa=\"false\";\n\t\t\t\t\t\t\tvar url='/showresource.do?method=showMonitorGroupViewIndividual&parents='+tree+'&lindx='+idx;//No I18N\n\t\t\t\t\t\t\t $('#allMonitorGroups > tbody  > tr').each(function() {\t\t\t\t\t\t\t \n\t\t\t\t\t\t\t try{\n\t\t\t\t\t\t\tif($(this).attr(\"isgroup\")==\"true\"){\t\t\t\t\t\t\t \n\t\t\t\t\t\t\t$(this).find('input[type=checkbox]').each(function(){\t\t\t\t\t\t\t\t\n");
/* 3592 */                                     out.write("\t\t\t\t\t\t\tif($(this).val()==resourceid){\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t aa=\"true\";\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\t});\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\tif(aa==\"true\"){ \n\t\t\t\t\t\t\t\tgetDynamicCall11(url,$(this));\n\t\t\t\t\t\t\t\tvar explk=$(this).find('.expandlink')[0]; //No I18N \n\t\t\t\t\t\t        toggleChildMos('#monitor'+tree,explk);//No I18N \n\t\t\t\t\t\t\t\ttoggleTreeImage(tree); \t\n\t\t\t\t\t\t\t\treturn false;\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\t}\t\t\t \n\t\t\t\t\t\t }\n\t\t\t\t\t\t catch(err){\n\t\t\t\t\t\t console.log('err     '+err); \n\t\t\t\t\t\t }\n\t\t\t\t\t\t });\n\t\t\t\t\t\t\t\n\t\t\t\t}catch(err)\n\t\t\t\t{\n\t\t\t\tconsole.log(err);\n\t\t\t\t}\t\t\t\t\t\t\t\n\t\t\t\t}\t\n\t\t\t\t\t\t\t\t\n                </script>\n\t\t\t\t\n\t\t\t\t\n");
/*      */                                   }
/* 3594 */                                 } } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3595 */         out = _jspx_out;
/* 3596 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3597 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3598 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3601 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3607 */     PageContext pageContext = _jspx_page_context;
/* 3608 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3610 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3611 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3612 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3614 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3615 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3616 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3618 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3619 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3620 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3624 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3625 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3626 */       return true;
/*      */     }
/* 3628 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3629 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3634 */     PageContext pageContext = _jspx_page_context;
/* 3635 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3637 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3638 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3639 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 3641 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 3642 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3643 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3644 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3645 */       return true;
/*      */     }
/* 3647 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3648 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3653 */     PageContext pageContext = _jspx_page_context;
/* 3654 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3656 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3657 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3658 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 3660 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 3661 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3662 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3663 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3664 */       return true;
/*      */     }
/* 3666 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3667 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3672 */     PageContext pageContext = _jspx_page_context;
/* 3673 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3675 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3676 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3677 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3679 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3680 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3681 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3683 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3684 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3685 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3689 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3690 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3691 */       return true;
/*      */     }
/* 3693 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3699 */     PageContext pageContext = _jspx_page_context;
/* 3700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3702 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3703 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3704 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 3706 */     _jspx_th_c_005fif_005f0.setTest("${not empty param.haid}");
/* 3707 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3708 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3710 */         out.write("\n                    document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitorGroups&retaintree=\"+retaintree+\"&haid=");
/* 3711 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3712 */           return true;
/* 3713 */         out.write("\"+\"&unopengroups=\"+unopengroups;\t\t    \n\t\t\t");
/* 3714 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3715 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3719 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3720 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3721 */       return true;
/*      */     }
/* 3723 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3724 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3729 */     PageContext pageContext = _jspx_page_context;
/* 3730 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3732 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3733 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3734 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3736 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 3737 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3738 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3739 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3740 */       return true;
/*      */     }
/* 3742 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3743 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3748 */     PageContext pageContext = _jspx_page_context;
/* 3749 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3751 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3752 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3753 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 3755 */     _jspx_th_c_005fif_005f1.setTest("${empty param.haid}");
/* 3756 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3757 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3759 */         out.write("\n                     document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitorGroups&retaintree=\"+retaintree+\"&unopengroups=\"+unopengroups;\n\t\t    ");
/* 3760 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3761 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3765 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3766 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3767 */       return true;
/*      */     }
/* 3769 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3775 */     PageContext pageContext = _jspx_page_context;
/* 3776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3778 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3779 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3780 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 3782 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3783 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3784 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3786 */         out.write("\n                alertUser();\n                return;\n        ");
/* 3787 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3788 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3792 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3793 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3794 */       return true;
/*      */     }
/* 3796 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3802 */     PageContext pageContext = _jspx_page_context;
/* 3803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3805 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3806 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3807 */     _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */     
/* 3809 */     _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 3810 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3811 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 3813 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3814 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3815 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3819 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3820 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3821 */       return true;
/*      */     }
/* 3823 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3824 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3829 */     PageContext pageContext = _jspx_page_context;
/* 3830 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3832 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3833 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3834 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 3836 */     _jspx_th_c_005fif_005f2.setTest("${not empty param.haid}");
/* 3837 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3838 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3840 */         out.write("\n                    document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitorGroups&retaintree=\"+retaintree+\"&haid=");
/* 3841 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3842 */           return true;
/* 3843 */         out.write("&isReset=true\"+\"&unopengroups=\"+unopengroups;\n\t\t    ");
/* 3844 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3845 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3849 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3850 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3851 */       return true;
/*      */     }
/* 3853 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3854 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3859 */     PageContext pageContext = _jspx_page_context;
/* 3860 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3862 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3863 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3864 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3866 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 3867 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3868 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3869 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3870 */       return true;
/*      */     }
/* 3872 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3878 */     PageContext pageContext = _jspx_page_context;
/* 3879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3881 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3882 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3883 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 3885 */     _jspx_th_c_005fif_005f3.setTest("${empty param.haid}");
/* 3886 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3887 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3889 */         out.write("\n                     document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitorGroups&retaintree=\"+retaintree+\"&isReset=true\"+\"&unopengroups=\"+unopengroups;\n\t\t    ");
/* 3890 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3891 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3895 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3896 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3897 */       return true;
/*      */     }
/* 3899 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3900 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3905 */     PageContext pageContext = _jspx_page_context;
/* 3906 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3908 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3909 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3910 */     _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */     
/* 3912 */     _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3913 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3914 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 3916 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3917 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3918 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3922 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3923 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3924 */       return true;
/*      */     }
/* 3926 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3927 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3932 */     PageContext pageContext = _jspx_page_context;
/* 3933 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3935 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3936 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3937 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 3939 */     _jspx_th_c_005fif_005f4.setTest("${not empty param.haid}");
/* 3940 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3941 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 3943 */         out.write("\n                  document.deletemonitor.action=\"/deleteMO.do?method=ManageMonitorGroups&retaintree=\"+retaintree+\"&haid=");
/* 3944 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 3945 */           return true;
/* 3946 */         out.write("&unopengroups=\"+unopengroups;\t\t\t  \t\t\t\t\t\t  \n\t\t   ");
/* 3947 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3948 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3952 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3953 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3954 */       return true;
/*      */     }
/* 3956 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3962 */     PageContext pageContext = _jspx_page_context;
/* 3963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3965 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3966 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3967 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3969 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 3970 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3971 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3972 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3973 */       return true;
/*      */     }
/* 3975 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3981 */     PageContext pageContext = _jspx_page_context;
/* 3982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3984 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3985 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3986 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 3988 */     _jspx_th_c_005fif_005f5.setTest("${empty param.haid}");
/* 3989 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3990 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 3992 */         out.write("\n\t\t\t document.deletemonitor.action=\"/deleteMO.do?method=ManageMonitorGroups&retaintree=\"+retaintree+\"&unopengroups=\"+unopengroups;\t\t\t\t\t\t\n\t\t ");
/* 3993 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3994 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3998 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3999 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4000 */       return true;
/*      */     }
/* 4002 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4008 */     PageContext pageContext = _jspx_page_context;
/* 4009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4011 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4012 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 4013 */     _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */     
/* 4015 */     _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 4016 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 4017 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 4019 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 4020 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 4021 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4025 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 4026 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4027 */       return true;
/*      */     }
/* 4029 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4030 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_logic_005fnotPresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4035 */     PageContext pageContext = _jspx_page_context;
/* 4036 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4038 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4039 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 4040 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_logic_005fnotPresent_005f5);
/*      */     
/* 4042 */     _jspx_th_c_005fif_005f6.setTest("${empty param.haid}");
/* 4043 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 4044 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 4046 */         out.write("\n\t\t   document.deletemonitor.action=\"/deleteMO.do?method=alterActionsforMG&alteraction=disable&retaintree=\"+retaintree+\"&unopengroups=\"+unopengroups;\n\t ");
/* 4047 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4048 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4052 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4053 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4054 */       return true;
/*      */     }
/* 4056 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4057 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_logic_005fnotPresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4062 */     PageContext pageContext = _jspx_page_context;
/* 4063 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4065 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4066 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4067 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_logic_005fnotPresent_005f5);
/*      */     
/* 4069 */     _jspx_th_c_005fif_005f7.setTest("${not empty param.haid}");
/* 4070 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4071 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 4073 */         out.write("\n\t  document.deletemonitor.action=\"/deleteMO.do?method=alterActionsforMG&alteraction=disable&retaintree=\"+retaintree+\"&haid=");
/* 4074 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 4075 */           return true;
/* 4076 */         out.write("\"+\"&unopengroups=\"+unopengroups;\n\t ");
/* 4077 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4078 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4082 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4083 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4084 */       return true;
/*      */     }
/* 4086 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4087 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4092 */     PageContext pageContext = _jspx_page_context;
/* 4093 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4095 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4096 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4097 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4099 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 4100 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4101 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4102 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4103 */       return true;
/*      */     }
/* 4105 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4106 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4111 */     PageContext pageContext = _jspx_page_context;
/* 4112 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4114 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4115 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 4116 */     _jspx_th_logic_005fpresent_005f6.setParent(null);
/*      */     
/* 4118 */     _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 4119 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 4120 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 4122 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 4123 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 4124 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4128 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 4129 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4130 */       return true;
/*      */     }
/* 4132 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4133 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_logic_005fnotPresent_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4138 */     PageContext pageContext = _jspx_page_context;
/* 4139 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4141 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4142 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4143 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_logic_005fnotPresent_005f6);
/*      */     
/* 4145 */     _jspx_th_c_005fif_005f8.setTest("${not empty param.haid}");
/* 4146 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4147 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 4149 */         out.write("\n\t  document.deletemonitor.action=\"/deleteMO.do?method=alterActionsforMG&alteraction=enable&retaintree=\"+retaintree+\"&haid=");
/* 4150 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 4151 */           return true;
/* 4152 */         out.write("\"+\"&unopengroups=\"+unopengroups;\n\t");
/* 4153 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4154 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4158 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4159 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4160 */       return true;
/*      */     }
/* 4162 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4168 */     PageContext pageContext = _jspx_page_context;
/* 4169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4171 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4172 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4173 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 4175 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 4176 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4177 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4178 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4179 */       return true;
/*      */     }
/* 4181 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4182 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_logic_005fnotPresent_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4187 */     PageContext pageContext = _jspx_page_context;
/* 4188 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4190 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4191 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4192 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_logic_005fnotPresent_005f6);
/*      */     
/* 4194 */     _jspx_th_c_005fif_005f9.setTest("${empty param.haid}");
/* 4195 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4196 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 4198 */         out.write("\n\t\tdocument.deletemonitor.action=\"/deleteMO.do?method=alterActionsforMG&alteraction=enable&retaintree=\"+retaintree+\"&unopengroups=\"+unopengroups;\n\t");
/* 4199 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4200 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4204 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4205 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4206 */       return true;
/*      */     }
/* 4208 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4214 */     PageContext pageContext = _jspx_page_context;
/* 4215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4217 */     PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4218 */     _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 4219 */     _jspx_th_logic_005fpresent_005f7.setParent(null);
/*      */     
/* 4221 */     _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 4222 */     int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 4223 */     if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */       for (;;) {
/* 4225 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 4226 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 4227 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4231 */     if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 4232 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4233 */       return true;
/*      */     }
/* 4235 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4236 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4241 */     PageContext pageContext = _jspx_page_context;
/* 4242 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4244 */     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4245 */     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 4246 */     _jspx_th_logic_005fpresent_005f8.setParent(null);
/*      */     
/* 4248 */     _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 4249 */     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 4250 */     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */       for (;;) {
/* 4252 */         out.write("\n  \t\talertUser();\n  \t\treturn;\n  \t");
/* 4253 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 4254 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4258 */     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 4259 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4260 */       return true;
/*      */     }
/* 4262 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4268 */     PageContext pageContext = _jspx_page_context;
/* 4269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4271 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4272 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4273 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 4275 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 4276 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4277 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4278 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4279 */       return true;
/*      */     }
/* 4281 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4282 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4287 */     PageContext pageContext = _jspx_page_context;
/* 4288 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4290 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4291 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4292 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 4294 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 4295 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4296 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4297 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4298 */       return true;
/*      */     }
/* 4300 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4301 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4306 */     PageContext pageContext = _jspx_page_context;
/* 4307 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4309 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4310 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4311 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 4313 */     _jspx_th_c_005fout_005f9.setValue("${param.haid}");
/* 4314 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4315 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4316 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4317 */       return true;
/*      */     }
/* 4319 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4320 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4325 */     PageContext pageContext = _jspx_page_context;
/* 4326 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4328 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 4329 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4330 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 4332 */     _jspx_th_c_005fset_005f0.setTarget("${AMActionForm}");
/*      */     
/* 4334 */     _jspx_th_c_005fset_005f0.setProperty("defaultmonitorsview");
/*      */     
/* 4336 */     _jspx_th_c_005fset_005f0.setValue("showMonitorGroupView");
/* 4337 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4338 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4339 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4340 */       return true;
/*      */     }
/* 4342 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4343 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4348 */     PageContext pageContext = _jspx_page_context;
/* 4349 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4351 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4352 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 4353 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4355 */     _jspx_th_html_005fhidden_005f0.setProperty("zoomlevel");
/* 4356 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 4357 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 4358 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4359 */       return true;
/*      */     }
/* 4361 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4362 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4367 */     PageContext pageContext = _jspx_page_context;
/* 4368 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4370 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4371 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 4372 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4374 */     _jspx_th_html_005fhidden_005f1.setProperty("zoomlocation");
/* 4375 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 4376 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 4377 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 4378 */       return true;
/*      */     }
/* 4380 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 4381 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4386 */     PageContext pageContext = _jspx_page_context;
/* 4387 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4389 */     PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4390 */     _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 4391 */     _jspx_th_logic_005fpresent_005f10.setParent(null);
/*      */     
/* 4393 */     _jspx_th_logic_005fpresent_005f10.setRole("DEMO");
/* 4394 */     int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 4395 */     if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */       for (;;) {
/* 4397 */         out.write("\n    alertUser();\n    return;\n  ");
/* 4398 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 4399 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4403 */     if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 4404 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4405 */       return true;
/*      */     }
/* 4407 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4408 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4413 */     PageContext pageContext = _jspx_page_context;
/* 4414 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4416 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f11 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4417 */     _jspx_th_logic_005fnotPresent_005f11.setPageContext(_jspx_page_context);
/* 4418 */     _jspx_th_logic_005fnotPresent_005f11.setParent(null);
/*      */     
/* 4420 */     _jspx_th_logic_005fnotPresent_005f11.setRole("DEMO");
/* 4421 */     int _jspx_eval_logic_005fnotPresent_005f11 = _jspx_th_logic_005fnotPresent_005f11.doStartTag();
/* 4422 */     if (_jspx_eval_logic_005fnotPresent_005f11 != 0) {
/*      */       for (;;) {
/* 4424 */         out.write("\n  document.AMActionForm.submit();\n  ");
/* 4425 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f11.doAfterBody();
/* 4426 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4430 */     if (_jspx_th_logic_005fnotPresent_005f11.doEndTag() == 5) {
/* 4431 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f11);
/* 4432 */       return true;
/*      */     }
/* 4434 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f11);
/* 4435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4440 */     PageContext pageContext = _jspx_page_context;
/* 4441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4443 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4444 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4445 */     _jspx_th_c_005fif_005f13.setParent(null);
/*      */     
/* 4447 */     _jspx_th_c_005fif_005f13.setTest("${forInventory == \"true\"}");
/* 4448 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4449 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 4451 */         out.write("\n&nbsp;\n");
/* 4452 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4453 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4457 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4458 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4459 */       return true;
/*      */     }
/* 4461 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4462 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4467 */     PageContext pageContext = _jspx_page_context;
/* 4468 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4470 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 4471 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4472 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 4474 */     _jspx_th_c_005fforEach_005f0.setVar("eachtag");
/*      */     
/* 4476 */     _jspx_th_c_005fforEach_005f0.setItems("${tags}");
/* 4477 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 4479 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4480 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 4482 */           out.write("\n\t\t\t\t\t\t\t<option value=\"");
/* 4483 */           boolean bool; if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4484 */             return true;
/* 4485 */           out.write(34);
/* 4486 */           out.write(62);
/* 4487 */           if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4488 */             return true;
/* 4489 */           out.write("</option>\n                            ");
/* 4490 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4491 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4495 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 4496 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4499 */         int tmp239_238 = 0; int[] tmp239_236 = _jspx_push_body_count_c_005fforEach_005f0; int tmp241_240 = tmp239_236[tmp239_238];tmp239_236[tmp239_238] = (tmp241_240 - 1); if (tmp241_240 <= 0) break;
/* 4500 */         out = _jspx_page_context.popBody(); }
/* 4501 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4503 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4504 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 4506 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4511 */     PageContext pageContext = _jspx_page_context;
/* 4512 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4514 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4515 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4516 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4518 */     _jspx_th_c_005fout_005f10.setValue("${eachtag.labelalias}");
/* 4519 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4520 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4521 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4522 */       return true;
/*      */     }
/* 4524 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4530 */     PageContext pageContext = _jspx_page_context;
/* 4531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4533 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4534 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4535 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4537 */     _jspx_th_c_005fout_005f11.setValue("${eachtag.label}");
/* 4538 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4539 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4540 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4541 */       return true;
/*      */     }
/* 4543 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4544 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4549 */     PageContext pageContext = _jspx_page_context;
/* 4550 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4552 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4553 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4554 */     _jspx_th_c_005fif_005f15.setParent(null);
/*      */     
/* 4556 */     _jspx_th_c_005fif_005f15.setTest("${forInventory == \"true\"}");
/* 4557 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4558 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 4560 */         out.write("\n&nbsp;\n");
/* 4561 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4562 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4566 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4567 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4568 */       return true;
/*      */     }
/* 4570 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4571 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4576 */     PageContext pageContext = _jspx_page_context;
/* 4577 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4579 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4580 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 4581 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 4583 */     _jspx_th_c_005fif_005f20.setTest("${notMG==\"true\"}");
/* 4584 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 4585 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 4587 */         out.write("\n\t\t&nbsp;\n\t\t");
/* 4588 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 4589 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4593 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 4594 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4595 */       return true;
/*      */     }
/* 4597 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4603 */     PageContext pageContext = _jspx_page_context;
/* 4604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4606 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4607 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 4608 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 4610 */     _jspx_th_c_005fif_005f24.setTest("${notMG==\"true\"}");
/* 4611 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 4612 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 4614 */         out.write("\n                &nbsp;\n                ");
/* 4615 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 4616 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4620 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 4621 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 4622 */       return true;
/*      */     }
/* 4624 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 4625 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f29, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4630 */     PageContext pageContext = _jspx_page_context;
/* 4631 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4633 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4634 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4635 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f29);
/*      */     
/* 4637 */     _jspx_th_c_005fout_005f12.setValue("${param.haid}");
/* 4638 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4639 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4640 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4641 */       return true;
/*      */     }
/* 4643 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4644 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f29, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4649 */     PageContext pageContext = _jspx_page_context;
/* 4650 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4652 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4653 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4654 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f29);
/*      */     
/* 4656 */     _jspx_th_c_005fout_005f13.setValue("${param.haid}");
/* 4657 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4658 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4659 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4660 */       return true;
/*      */     }
/* 4662 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4663 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4668 */     PageContext pageContext = _jspx_page_context;
/* 4669 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4671 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4672 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4673 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 4675 */     _jspx_th_c_005fout_005f14.setValue("${alert[key]}");
/* 4676 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4677 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4678 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4679 */       return true;
/*      */     }
/* 4681 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4682 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4687 */     PageContext pageContext = _jspx_page_context;
/* 4688 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4690 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4691 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4692 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 4694 */     _jspx_th_c_005fout_005f15.setValue("${alert[key]}");
/* 4695 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4696 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4697 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4698 */       return true;
/*      */     }
/* 4700 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4701 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MonitorGroupViewInclude_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */