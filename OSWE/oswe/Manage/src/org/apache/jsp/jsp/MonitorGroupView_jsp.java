/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
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
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class MonitorGroupView_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  195 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
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
/*  405 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
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
/*  418 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*  487 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/*  488 */   static { _jspx_dependants.put("/jsp/MonitorGroupViewInclude.jsp", Long.valueOf(1473429417000L));
/*  489 */     _jspx_dependants.put("/jsp/includes/bulkActions.jspf", Long.valueOf(1473429417000L));
/*  490 */     _jspx_dependants.put("/jsp/groupviewutil.jspf", Long.valueOf(1473429417000L));
/*  491 */     _jspx_dependants.put("/jsp/includes/monitors_setasdefault.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
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
/*  519 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  523 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  524 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  525 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  526 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  527 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  528 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  529 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  530 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  531 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  532 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  533 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  534 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  535 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  536 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  537 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  538 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  539 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  540 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  541 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  542 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  543 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  544 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  548 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  549 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*  550 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  551 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  552 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  553 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  554 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  555 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  556 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/*  557 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  558 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  559 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  560 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  561 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  562 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  563 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  564 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*  565 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  566 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*  567 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  574 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  577 */     JspWriter out = null;
/*  578 */     Object page = this;
/*  579 */     JspWriter _jspx_out = null;
/*  580 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  584 */       response.setContentType("text/html;charset=UTF-8");
/*  585 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/*  587 */       _jspx_page_context = pageContext;
/*  588 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  589 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  590 */       session = pageContext.getSession();
/*  591 */       out = pageContext.getOut();
/*  592 */       _jspx_out = out;
/*      */       
/*  594 */       out.write("<!DOCTYPE html>\n");
/*  595 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$ -->\n");
/*      */       
/*  597 */       request.setAttribute("HelpKey", "Monitors Page");
/*      */       
/*  599 */       out.write(10);
/*  600 */       out.write(10);
/*  601 */       out.write(10);
/*      */       
/*  603 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/*  604 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  605 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/*  607 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/*  608 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  609 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/*  611 */           out.write(10);
/*      */           
/*  613 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/*  614 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/*  615 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/*  617 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */           
/*  619 */           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.monitorgroupview.title"));
/*  620 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/*  621 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/*  622 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/*  625 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/*  626 */           out.write(10);
/*  627 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/*  629 */           out.write(10);
/*  630 */           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/*  632 */           out.write(10);
/*      */           
/*  634 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  635 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/*  636 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/*  638 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/*  640 */           _jspx_th_tiles_005fput_005f3.setType("string");
/*  641 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/*  642 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/*  643 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/*  644 */               out = _jspx_page_context.pushBody();
/*  645 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/*  646 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  649 */               out.write(10);
/*  650 */               request.setAttribute("treewithLayout", "true");
/*  651 */               out.write(10);
/*      */               try
/*      */               {
/*  654 */                 out.write(10);
/*  655 */                 out.write("<!--$Id$-->\n");
/*  656 */                 out.write(10);
/*      */                 
/*  658 */                 request.setAttribute("HelpKey", "Monitors Page");
/*      */                 
/*  660 */                 out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  661 */                 out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  662 */                 out.write(10);
/*  663 */                 out.write(10);
/*  664 */                 Hashtable availabilitykeys = null;
/*  665 */                 synchronized (application) {
/*  666 */                   availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/*  667 */                   if (availabilitykeys == null) {
/*  668 */                     availabilitykeys = new Hashtable();
/*  669 */                     _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                   }
/*      */                 }
/*  672 */                 out.write(10);
/*  673 */                 Hashtable healthkeys = null;
/*  674 */                 synchronized (application) {
/*  675 */                   healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/*  676 */                   if (healthkeys == null) {
/*  677 */                     healthkeys = new Hashtable();
/*  678 */                     _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                   }
/*      */                 }
/*  681 */                 out.write(10);
/*  682 */                 ManagedApplication MA = null;
/*  683 */                 synchronized (application) {
/*  684 */                   MA = (ManagedApplication)_jspx_page_context.getAttribute("MA", 4);
/*  685 */                   if (MA == null) {
/*  686 */                     MA = new ManagedApplication();
/*  687 */                     _jspx_page_context.setAttribute("MA", MA, 4);
/*      */                   }
/*      */                 }
/*  690 */                 out.write("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
/*      */                 
/*  692 */                 if ((EnterpriseUtil.isIt360MSPEdition()) && (request.getSession().getAttribute("custProp") == null))
/*      */                 {
/*  694 */                   response.sendRedirect("/showIT360Tile.do?TileName=IT360.CustomersSummary");
/*      */                 }
/*      */                 
/*  697 */                 out.write(10);
/*      */                 
/*  699 */                 String resourceName = "";
/*  700 */                 String customValue = request.getParameter("customValue");
/*  701 */                 boolean showmore = Boolean.parseBoolean((String)request.getAttribute("showmore"));
/*  702 */                 int pageno = 2;
/*  703 */                 if (request.getParameter("pageno") != null) {
/*  704 */                   pageno = Integer.parseInt(request.getParameter("pageno")) + 1;
/*      */                 }
/*  706 */                 String viewtype = (String)request.getAttribute("viewtype");
/*  707 */                 Hashtable chilmos = (Hashtable)request.getAttribute("childlist");
/*  708 */                 if (chilmos == null)
/*      */                 {
/*  710 */                   chilmos = new Hashtable();
/*      */                 }
/*  712 */                 MyFields fields = new MyFields();
/*  713 */                 ArrayList tags = null;
/*  714 */                 tags = MyFields.getDollarTags(false, "HAI", request, false);
/*  715 */                 request.setAttribute("tags", tags);
/*  716 */                 int displayNameindex = 0;
/*  717 */                 int actionIndex = 9;
/*  718 */                 int unmanagestatusindex = 8;
/*  719 */                 int enableindex = 10;
/*  720 */                 int resourceTypeindex = 7;
/*  721 */                 int residIndex = 6;
/*  722 */                 int haiGroupTypeIndex = 11;
/*  723 */                 String healthid = "18";
/*  724 */                 String availid = "17";
/*  725 */                 String isopen = "false";
/*  726 */                 String forInventory = (String)request.getAttribute("forInventory");
/*  727 */                 boolean isInventory = false;
/*  728 */                 String colpseStyle = "none";
/*  729 */                 String expandStyle = "inline";
/*  730 */                 String plusStyle = "inline";
/*  731 */                 String minusStyle = "none";
/*  732 */                 String rowStyle = "none";
/*  733 */                 String img = "";
/*      */                 
/*  735 */                 ArrayList resIDs = new ArrayList();
/*  736 */                 ArrayList entitylist = new ArrayList();
/*  737 */                 int totalmonitorcount = 0;
/*  738 */                 ArrayList tempresourelist = new ArrayList();
/*  739 */                 ArrayList eumChildList = com.adventnet.appmanager.util.Constants.getEUMChildList();
/*  740 */                 HashMap extDeviceMap = null;
/*  741 */                 HashMap site24x7List = null;
/*  742 */                 String ag1 = request.getHeader("User-Agent");
/*  743 */                 String filterstyle = "filterby-txt";
/*  744 */                 String nouncategorize = (String)request.getAttribute("nouncategorize");
/*  745 */                 ag1 = ag1.toLowerCase();
/*  746 */                 if (ag1.contains("chrome"))
/*      */                 {
/*  748 */                   filterstyle = "bodytextbold";
/*      */                 }
/*      */                 
/*  751 */                 if ((com.adventnet.appmanager.util.Constants.isIt360) || (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured()))
/*      */                 {
/*  753 */                   extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink(false);
/*  754 */                   if (com.adventnet.appmanager.util.OEMUtil.isRemove("enable.proxyForExtProd"))
/*      */                   {
/*      */ 
/*      */ 
/*  758 */                     HashMap opmExtDeviceMap = com.adventnet.appmanager.util.ExtProdUtil.getDeviceLinksOfExtProduct("OpManager", false, false);
/*  759 */                     extDeviceMap.putAll(opmExtDeviceMap);
/*      */                   }
/*  761 */                   site24x7List = com.adventnet.appmanager.util.DBUtil.getAllsite24x7MonitorsLink();
/*      */                 }
/*  763 */                 String already = "false";
/*  764 */                 if ("true".equals(request.getParameter("uncategorize"))) {
/*  765 */                   isopen = "true";
/*      */                 }
/*      */                 
/*      */ 
/*  769 */                 String uncatstyle = "new-monitordiv-link";
/*  770 */                 String groupviewstyle = "new-monitordiv-link";
/*      */                 
/*  772 */                 if ("true".equalsIgnoreCase(request.getParameter("uncategorize")))
/*      */                 {
/*  774 */                   uncatstyle = "bulkmon-tag";
/*      */                 }
/*      */                 else {
/*  777 */                   groupviewstyle = "bulkmon-tag";
/*      */                 }
/*      */                 
/*      */ 
/*  781 */                 out.write(10);
/*  782 */                 out.write(10);
/*  783 */                 out.write(10);
/*      */                 
/*  785 */                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  786 */                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  787 */                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/*  789 */                 _jspx_th_logic_005fiterate_005f0.setName("applications");
/*      */                 
/*  791 */                 _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                 
/*  793 */                 _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*      */                 
/*  795 */                 _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*  796 */                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  797 */                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  798 */                   ArrayList row = null;
/*  799 */                   Integer i = null;
/*  800 */                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  801 */                     out = _jspx_page_context.pushBody();
/*  802 */                     _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/*  803 */                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                   }
/*  805 */                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  806 */                   i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                   for (;;) {
/*  808 */                     out.write(10);
/*      */                     try
/*      */                     {
/*  811 */                       String parentResourceType = (String)row.get(resourceTypeindex);
/*  812 */                       availid = (String)healthkeys.get(parentResourceType);
/*  813 */                       healthid = (String)availabilitykeys.get(parentResourceType);
/*  814 */                       entitylist.add((String)row.get(residIndex) + "_" + availid);
/*  815 */                       entitylist.add((String)row.get(residIndex) + "_" + healthid);
/*      */                       try
/*      */                       {
/*  818 */                         ArrayList srow = (ArrayList)chilmos.get((String)row.get(residIndex) + "");
/*  819 */                         if (srow != null)
/*      */                         {
/*  821 */                           for (int k = 0; k < srow.size(); k++)
/*      */                           {
/*  823 */                             ArrayList mo = (ArrayList)srow.get(k);
/*  824 */                             if ((mo != null) && (mo.get(0) != null) && (!((String)mo.get(0)).equals("null")))
/*      */                             {
/*  826 */                               String cresid = ((String)mo.get(0) + "").trim();
/*  827 */                               if (!eumChildList.contains(cresid))
/*      */                               {
/*      */ 
/*      */ 
/*  831 */                                 String resourceType = ((String)mo.get(2) + "").trim();
/*  832 */                                 String healthkey = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/*  833 */                                 if ((healthkey != null) && (!entitylist.contains(cresid + "_" + healthkey)))
/*      */                                 {
/*  835 */                                   entitylist.add(cresid + "_" + healthkey);
/*      */                                 }
/*      */                                 
/*  838 */                                 String availabilitykey = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*  839 */                                 if ((!resourceType.equals("HAI")) && (!tempresourelist.contains(cresid)))
/*      */                                 {
/*  841 */                                   tempresourelist.add(cresid);
/*  842 */                                   totalmonitorcount++;
/*      */                                 }
/*  844 */                                 if ((availabilitykey != null) && (!entitylist.contains(cresid + "_" + availabilitykey)))
/*      */                                 {
/*  846 */                                   entitylist.add(cresid + "_" + availabilitykey);
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       catch (Exception ex) {
/*  854 */                         ex.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     catch (Exception ex)
/*      */                     {
/*  859 */                       ex.printStackTrace();
/*      */                     }
/*      */                     
/*  862 */                     out.write(10);
/*  863 */                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  864 */                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  865 */                     i = (Integer)_jspx_page_context.findAttribute("i");
/*  866 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  869 */                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  870 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  873 */                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  874 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                 }
/*      */                 
/*  877 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  878 */                 out.write(10);
/*      */                 
/*  880 */                 if (("true".equals(forInventory)) || ("true".equalsIgnoreCase(request.getParameter("alllevels"))))
/*      */                 {
/*  882 */                   already = "true";
/*  883 */                   isInventory = true;
/*  884 */                   String haidInventory = request.getParameter("haid");
/*  885 */                   ArrayList singlechilmos1 = (ArrayList)chilmos.get(haidInventory);
/*  886 */                   if (singlechilmos1 != null)
/*      */                   {
/*  888 */                     request.setAttribute("applications", singlechilmos1);
/*      */                   }
/*      */                   else
/*      */                   {
/*  892 */                     request.setAttribute("applications", new ArrayList());
/*      */                   }
/*  894 */                   actionIndex = 6;
/*  895 */                   residIndex = 0;
/*  896 */                   enableindex = 7;
/*  897 */                   unmanagestatusindex = 5;
/*  898 */                   displayNameindex = 1;
/*  899 */                   resourceTypeindex = 2;
/*  900 */                   colpseStyle = "inline";
/*  901 */                   expandStyle = "none";
/*  902 */                   plusStyle = "none";
/*  903 */                   minusStyle = "inline";
/*  904 */                   rowStyle = "table-row";
/*  905 */                   isopen = "true";
/*      */                 }
/*      */                 
/*  908 */                 out.write(10);
/*  909 */                 out.write(10);
/*      */                 
/*  911 */                 Properties alert = getStatus(entitylist);
/*  912 */                 request.setAttribute("alert", alert);
/*      */                 
/*  914 */                 out.write("\n\n<Script>\n$.ajaxSetup({\n  cache: false\n});\nfunction fnSelectAll(e,name)\n{\n    ToggleAll(e,document.deletemonitor,name);\n}\n\nfunction showCustomFieldValues(http1)\n{\n        if(http1.readyState==4)\n        {\n                document.getElementById(\"customFieldValues\").style.display = \"inline\";\n                document.getElementById(\"customFieldValues\").innerHTML=http1.responseText;//No I18N\n\t\t\t\t\n        }\n\t\t$('.chzn-select').chosen();\n}\n\n\n\nfunction loadUrl(option){\n\n        if(option !=null && option != '-'){\n        location.href=\"/showresource.do?method=showMonitorGroupView&customValue=\"+option;\n        }\n\n}\nfunction loadURLType(option,frm,a,forBulkAssign)\n{\n\n\tif(option.indexOf(\"SYSTEMDATA\") != -1 || option.indexOf(\"USERDATA\") != -1 || option.indexOf(\"LOCATION_NAME\") != -1 || option.indexOf(\"USERNAME\") != -1 || option.indexOf(\"VALUEID\") != -1){\n\n                                var http1=getHTTPObject();\n                                http1.onreadystatechange= function (){showCustomFieldValues(http1);};//No I18N\n");
/*  915 */                 out.write("\t\t\t\tif(option.indexOf(\"$\") != -1){\n\t\t\t\t\tdocument.getElementById(\"groupviewfilterby\").value=option.substring(0,option.indexOf(\"$\"))\n\t\t\t\t\tURL = \"/myFields.do?method=getFieldValues&aliasName=\"+option.substring(0,option.indexOf(\"$\"))+\"&optionSel=\"+option.substring(option.indexOf(\"$\")+1)+\"&forBulkAssign=false&monitortype=HAI\"; // NO I18N\n\n\t\t\t\t}else{\n                                URL=\"/myFields.do?method=getFieldValues&aliasName=\"+option+\"&forBulkAssign=false&monitortype=HAI\";//No I18N\n\t\t\t\t}\n                                http1.open(\"GET\",URL,true);//No I18N\n                                http1.send(null);//No I18N\n\n\n                        }\n}\n\nvar retaintree;\nfunction deleteMO(unopengroups,MGselected)\n{\n\t");
/*  916 */                 if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  918 */                 out.write(10);
/*  919 */                 out.write(9);
/*      */                 
/*  921 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  922 */                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  923 */                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/*  925 */                 _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  926 */                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  927 */                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                   for (;;) {
/*  929 */                     out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert('");
/*  930 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.delete.alert"));
/*  931 */                     out.write("');//No I18N\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\t\t   var c_value = \"\";\n           for (var i=0; i < document.deletemonitor.select.length; i++)\n\t\t   {\n\t\t      if (document.deletemonitor.select[i].checked)\n\t\t      {\n\t\t         c_value = c_value + document.deletemonitor.select[i].value + \"|\";//No I18N\t\n\t\t         }\n\n\t\t\t}\n\t        if(MGselected==\"true\")\n\t        {\n\t\t\t\t url=\"/deleteMO.do?method=deleteMonitorGroup&choosedeleteoption=true&select=\"+c_value+\"&retaintree=\"+retaintree+\"&haid=");
/*  932 */                     if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/*      */                       return;
/*  934 */                     out.write("\"+\"&unopengroups=\"+unopengroups;//No I18N \t\t\t\t \t\t\t\t \n\t\t \t\t var featurelist = \"toolbar=no,status=no,menubar=no,width=450,height=300,scrollbars=yes,left=300,top=200\";\n\t\t                 windowReference = window.open(url,'windowName',featurelist);\n\t\t                 document.deletemonitor.operation.selectedIndex=0;\n\t        }\n\t\telse\n\t\t{\n\t\t  if (confirm('");
/*  935 */                     out.print(FormatUtil.getString("Do you want to delete the selected Monitors"));
/*  936 */                     out.write("'))\n\t\t  {\t\t  \t\t   \n\t\t   location.href= \"/deleteMO.do?method=deleteMonitorGroup&select=\"+c_value+\"&retaintree=\"+retaintree+\"&haid=");
/*  937 */                     if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/*      */                       return;
/*  939 */                     out.write("\"+\"&unopengroups=\"+unopengroups;//No I18N\n\t\t   \t\t   \n\t\t  }\n\t\t  else\n\t\t  {\n\t\t     document.deletemonitor.operation.selectedIndex=0;\n\t\t     return;\n\t\t  }\n\t\t}\n\t");
/*  940 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  941 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  945 */                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  946 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                 }
/*      */                 
/*  949 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  950 */                 out.write("\n}\n\nfunction unManageMO(unopengroups,res_list)\n{\n\t");
/*  951 */                 if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  953 */                 out.write(10);
/*  954 */                 out.write(9);
/*      */                 
/*  956 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  957 */                 _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  958 */                 _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/*  960 */                 _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/*  961 */                 int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  962 */                 if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                   for (;;) {
/*  964 */                     out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert('");
/*  965 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.unmanage.alert"));
/*  966 */                     out.write("');//No I18N\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{\n\t\tif(confirm(\"");
/*  967 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.unmanage.confirm.alert"));
/*  968 */                     out.write("\"))\n\t\t{\n\t\t\tvar show_msg=\"false\";\n\t\t\tvar res=unopengroups+'|'+res_list; //No i18n\n\t\t\tvar resids = res.split('|');\n\t\t\tvar i=0\n\t\t\tfor(i=0;i<resids.length;i++)\n\t\t\t{\n\t\t\t\tvar url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+resids[i]; //No i18n\n\t\t\t$.ajax({\n\t\t\t\ttype:'POST', //No i18n\n\t\t\t\turl:url,\n\t\t\t\tasync:false,\n\t\t\t\tsuccess: function(data)\n\t\t\t\t{\n\t\t\t\t\tshow_msg=data\n\t\t\t\t}\n\t\t\t});\n\t\t\tif(show_msg.indexOf(\"true\")>-1)\n\t\t\t{\n\t\t\t\talert(\"");
/*  969 */                     out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/*  970 */                     out.write("\")\n\t\t\t\tbreak;\n\t\t\t}\n\t\t    \n\t\t\t}\n\t\t    ");
/*  971 */                     if (_jspx_meth_c_005fif_005f0(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                       return;
/*  973 */                     out.write("\n\t\t    ");
/*  974 */                     if (_jspx_meth_c_005fif_005f1(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                       return;
/*  976 */                     out.write("\n                    document.deletemonitor.submit();\n\t\t}\n\t\telse\n\t\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t}\n\t}\n\t");
/*  977 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  978 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  982 */                 if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  983 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                 }
/*      */                 
/*  986 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  987 */                 out.write("\n}\n\nfunction assignCustomField(unopengroups){\n\n");
/*  988 */                 if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/*  990 */                 out.write("\n        ");
/*      */                 
/*  992 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  993 */                 _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  994 */                 _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/*  996 */                 _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/*  997 */                 int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  998 */                 if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                   for (;;) {
/* 1000 */                     out.write("\n\n if(!checkforOneSelected(document.deletemonitor,\"select\"))\n        {\n                alert(\"");
/* 1001 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.customfield.alert"));
/* 1002 */                     out.write("\");//No i18n\n                document.deletemonitor.operation.selectedIndex=0;\n                return;\n        }\n        else\n        {\n\n\t\t\t                var len = document.deletemonitor.select.length;\n                var temp=\"\";\n                if(len > 0)\n                {\n                        for( i=0; i<len; i++)\n                        {\n                                if(document.deletemonitor.select[i].checked)\n                                        temp = temp + document.deletemonitor.select[i].value + \",\";\n                        }\n                }\n                else\n                {\n                        temp=document.deletemonitor.select.value;\n                }\n\n\n                document.deletemonitor.action=\"/myFields.do?method=assignCustomFields&resids=\"+temp+\"&unopengroups=\"+unopengroups;\n                        window.open(document.deletemonitor.action,'','resizable=yes,scrollbars=yes,width=700,height=400,top=200,left=200');\n\n\n        }\n        ");
/* 1003 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 1004 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1008 */                 if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 1009 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                 }
/*      */                 
/* 1012 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 1013 */                 out.write("\n\n\n}\n\nfunction unManageAndReset(unopengroups)\n{\n\t");
/* 1014 */                 if (_jspx_meth_logic_005fpresent_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 1016 */                 out.write(10);
/* 1017 */                 out.write(9);
/*      */                 
/* 1019 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1020 */                 _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 1021 */                 _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 1023 */                 _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 1024 */                 int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 1025 */                 if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                   for (;;) {
/* 1027 */                     out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert('");
/* 1028 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.unmanage.alert"));
/* 1029 */                     out.write("');//No I18N\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{\n\t\tif(confirm(\"");
/* 1030 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.reset.confirm.alert"));
/* 1031 */                     out.write("\"))\n\t\t{\n\t\t\tvar show_msg=\"false\"\n\t\t\tvar url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+unopengroups; //No i18n\n\t\t    $.ajax({\n\t\t\t\ttype:'POST', //No i18n\n\t\t\t\turl:url,\n\t\t\t\tasync:false,\n\t\t\t\tsuccess: function(data)\n\t\t\t\t{\n\t\t\t\t\t\tshow_msg=data\n\t\t\t\t}\n\t\t\t});\n\t\t\tif(show_msg.indexOf(\"true\")>-1)\n\t\t\t{\n\t\t\t\talert(\"");
/* 1032 */                     out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 1033 */                     out.write("\")\n\t\t\t}\n\t\t    ");
/* 1034 */                     if (_jspx_meth_c_005fif_005f2(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                       return;
/* 1036 */                     out.write("\n\t\t    ");
/* 1037 */                     if (_jspx_meth_c_005fif_005f3(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                       return;
/* 1039 */                     out.write("\n                    document.deletemonitor.submit();\n\t\t}\n\t\telse\n\t\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t}\n\t}\n\t");
/* 1040 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 1041 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1045 */                 if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 1046 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                 }
/*      */                 
/* 1049 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 1050 */                 out.write("\n}\n\nfunction manageMO(unopengroups)\n{\n\n\t");
/* 1051 */                 if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 1053 */                 out.write(10);
/* 1054 */                 out.write(9);
/*      */                 
/* 1056 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1057 */                 _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 1058 */                 _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 1060 */                 _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 1061 */                 int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 1062 */                 if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                   for (;;) {
/* 1064 */                     out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert('");
/* 1065 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.manage.alert"));
/* 1066 */                     out.write("');//No I18N\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{ \n\n\t\tif(confirm(\"");
/* 1067 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.manage.confirm.alert"));
/* 1068 */                     out.write("\"))\n\t\t{\n\t\t  ");
/* 1069 */                     if (_jspx_meth_c_005fif_005f4(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                       return;
/* 1071 */                     out.write("\n\t\t ");
/* 1072 */                     if (_jspx_meth_c_005fif_005f5(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                       return;
/* 1074 */                     out.write("\n                  document.deletemonitor.submit();\n\t\t}\n\t\telse\n\t\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t}\n\t}\n\t");
/* 1075 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 1076 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1080 */                 if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 1081 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                 }
/*      */                 
/* 1084 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 1085 */                 out.write("\n}\n\n\nfunction disableactions(unopengroups)\n{\n     ");
/* 1086 */                 if (_jspx_meth_logic_005fpresent_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 1088 */                 out.write(10);
/* 1089 */                 out.write(9);
/*      */                 
/* 1091 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1092 */                 _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 1093 */                 _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 1095 */                 _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 1096 */                 int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 1097 */                 if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                   for (;;) {
/* 1099 */                     out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t\t{\n\t\t\talert('");
/* 1100 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.disable.alert"));
/* 1101 */                     out.write("');//No I18N\n\t\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t\treturn;\n\t}\n\tif(confirm('");
/* 1102 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.disable.confirm.alert"));
/* 1103 */                     out.write("'))\n\t{\n\t ");
/* 1104 */                     if (_jspx_meth_c_005fif_005f6(_jspx_th_logic_005fnotPresent_005f5, _jspx_page_context))
/*      */                       return;
/* 1106 */                     out.write(10);
/* 1107 */                     out.write(9);
/* 1108 */                     out.write(32);
/* 1109 */                     if (_jspx_meth_c_005fif_005f7(_jspx_th_logic_005fnotPresent_005f5, _jspx_page_context))
/*      */                       return;
/* 1111 */                     out.write("\n\t  document.deletemonitor.submit();\n\t}\n\telse\n\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t}\n\t");
/* 1112 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 1113 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1117 */                 if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 1118 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                 }
/*      */                 
/* 1121 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 1122 */                 out.write("\n}\nfunction enableactions(unopengroups)\n{\n\n     ");
/* 1123 */                 if (_jspx_meth_logic_005fpresent_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 1125 */                 out.write(10);
/* 1126 */                 out.write(9);
/*      */                 
/* 1128 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1129 */                 _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 1130 */                 _jspx_th_logic_005fnotPresent_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 1132 */                 _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 1133 */                 int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 1134 */                 if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                   for (;;) {
/* 1136 */                     out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t\t{\n\t\t\talert('");
/* 1137 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.enable.alert"));
/* 1138 */                     out.write("');//No I18N\n\t\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t\t\treturn;\n\t}\n\tif(confirm('");
/* 1139 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.enable.confirm.alert"));
/* 1140 */                     out.write("'))\n\t{\n\t  ");
/* 1141 */                     if (_jspx_meth_c_005fif_005f8(_jspx_th_logic_005fnotPresent_005f6, _jspx_page_context))
/*      */                       return;
/* 1143 */                     out.write(10);
/* 1144 */                     out.write(9);
/* 1145 */                     if (_jspx_meth_c_005fif_005f9(_jspx_th_logic_005fnotPresent_005f6, _jspx_page_context))
/*      */                       return;
/* 1147 */                     out.write("\n\t  document.deletemonitor.submit();\n\t}\n\telse\n\t{\n\t\tdocument.deletemonitor.operation.selectedIndex=0;\n\t}\n\t");
/* 1148 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 1149 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1153 */                 if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 1154 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                 }
/*      */                 
/* 1157 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 1158 */                 out.write("\n}\n\nfunction selectAllChildCKbs(obname,ckb,myfrm) {\n\n\t\n    if(typeof(myfrm.length)==\"undefined\") {\n\t\treturn;\n    }\n\n    for(i=0;i<myfrm.length;i++) {\n\n        if(myfrm.elements[i].type == \"checkbox\" && myfrm.elements[i].id.indexOf(obname) == 0) {\n           myfrm.elements[i].checked = ckb.checked;\n        }\n    }\n\t\n\n}\n\nfunction deselectParentCKbs(obname1,ckb1,myfrm)\n{\n\tif(ckb1.checked)\n\treturn;\n\n\tvar temp1=obname1.split(\"|\");\n\tfor(i=0;i<temp1.length;i++)\n\t{\n\tif(i==0)\n\tparentresid=temp1[i];//No I18N\n\telse\n\tparentresid=parentresid+\"|\"+temp1[i];//No I18N\n\n\tfor(j=0;j<myfrm.length;j++) {\n\n\t\tif(myfrm.elements[j].id == parentresid) {\n\t\t\tmyfrm.elements[j].checked = false;\n\t\t}\n\t    }\n\t}\n\n}\n\nfunction invokeOperations(act)\n{\ntry{\n var alldivs =document.getElementsByTagName(\"div\");\nvar i;\nretaintree=\"\";//No I18N\nvar unopenresid ='';\nvar MGselected=\"false\";//No I18N\nvar res_list ='';  //No I18N\n$('.expandlink').each(function(){\nif($(this).attr('isopen')=='true'){ //No I18N\n\t$('.bulkmonHeader').each(function(){\nvar row=$(this).closest('tr');//No I18N\n");
/* 1159 */                 out.write("if($(row).find('input:checkbox:first').is(':checked'))\n{\n\tres_list=res_list+$(this).attr('groupid')+'|';  //No I18N\n}\n});\n}\nif($(this).attr('isopen')=='false'){ //No I18N\nvar row=$(this).closest('tr');//No I18N\nif($(row).find('input:checkbox:first').is(':checked')){//No I18N\nunopenresid=unopenresid+$(this).attr('resid')+'|'; //No I18N\nMGselected=\"true\";//No I18N\n}\n}\nelse if(MGselected==\"false\"){//No I18N\nvar row=$(this).closest('tr');//No I18N \nif($(row).find('input:checkbox:first').is(':checked')){\nMGselected=\"true\";//No I18N\n}\n}\n});\nif(unopenresid.length>1){\nunopenresid=unopenresid.substring(0,unopenresid.length-1);\n}\n");
/* 1160 */                 if (("true".equals(request.getParameter("uncategorize"))) || ("orphaned".equals(request.getParameter("retaintree")))) {
/* 1161 */                   out.write(" \nretaintree='orphaned'; //No I18N\n");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/* 1166 */                   out.write("\nfor(i=0; i <alldivs.length ; i++)\n{\n\t if((alldivs[i].id.indexOf(\"monitorHide\")) >= 0)//No I18N\n\t {\n\t   showdiv=document.getElementById(alldivs[i].id) ;\n\t   if(showdiv.style.display == 'inline')\n\t   {\n\t      ids=(alldivs[i].id).substring(11);//No I18N\n\t      if(retaintree==\"\")\n\t      retaintree=retaintree+ids;\n\t      else\n\t      retaintree=retaintree+\"$\"+ids;//No I18N\n\t   }\n\t }\n}\n\n");
/*      */                 }
/* 1168 */                 out.write("  \n  if(act.value=='Manage')\n  {\n    manageMO(unopenresid);\n  }\n  else if(act.value=='Unmanage')\n  {\n   unManageMO(unopenresid,res_list);\n  }\n  else if(act.value=='Delete')\n  {\n   deleteMO(unopenresid,MGselected);\n  }\n  else if(act.value=='Enable Actions')\n  {\n   enableactions(unopenresid);\n  }\n  else if(act.value=='Disable Actions')\n  {\n   disableactions(unopenresid);\n  }\n  else if(act.value=='updatepoll')\n  {\n   updatepolling('poll',unopenresid);//No I18N   \n  }\n  else if(act.value=='updateauth')\n  {\n   updatepolling('auth',unopenresid);//No I18N   \n  }\n  else if(act.value=='unManageAndReset')\n  {\n   unManageAndReset(unopenresid);//No I18N   \n  }\n  else if(act.value=='customField')\n  {\n\tassignCustomField(unopenresid);\n  }\n  else if(act.value=='editDisplayNames')\n  {\n\t  editDisplaynames();\n  }\n  \n  }\n  catch(err)\n  {\n  console.log('invokeOperations'+err); \n  }\n }\n \nfunction editDisplaynames(selectname)\n{\n\t\n\t ");
/* 1169 */                 if (_jspx_meth_logic_005fpresent_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 1171 */                 out.write(10);
/* 1172 */                 out.write(9);
/*      */                 
/* 1174 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1175 */                 _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 1176 */                 _jspx_th_logic_005fnotPresent_005f7.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 1178 */                 _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 1179 */                 int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 1180 */                 if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                   for (;;) {
/* 1182 */                     out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert(\"");
/* 1183 */                     out.print(FormatUtil.getString("am.webclient.alert.displayname.select.text"));
/* 1184 */                     out.write("\");//No i18n\n\t\tselectname.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n    {\n\t\tvar temp = \"\";\n\t\ttemp = $.map($(\"input:checkbox[name='select']:checked\"), function(vals, i) // NO I18N\n\t\t{\n\t\t\treturn vals.value;\n\t\t}).join(',');\n\t\tvar vmResids='");
/* 1185 */                     out.print(request.getAttribute("VirtualMachines"));
/* 1186 */                     out.write("';\n\t\tdocument.deletemonitor.action=\"/jsp/EditDisplaynames.jsp?resids=\"+temp+\"&vmResids=\"+vmResids;\n\t\twindow.open(document.deletemonitor.action,'','resizable=yes,scrollbars=yes,width=780,height=300,top=200,left=200');\n\t}\n\t");
/* 1187 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 1188 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1192 */                 if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 1193 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                 }
/*      */                 
/* 1196 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 1197 */                 out.write("\n}\n \n  function updatepolling(action,unopengroups)\n  {\n  ");
/* 1198 */                 if (_jspx_meth_logic_005fpresent_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 1200 */                 out.write("\n  \t");
/*      */                 
/* 1202 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1203 */                 _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 1204 */                 _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 1206 */                 _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 1207 */                 int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 1208 */                 if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                   for (;;) {
/* 1210 */                     out.write("\n  \tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n  \t\t{\n  \t\t\tif(action=='poll')\n  \t\t\t{\n  \t\t\talert('");
/* 1211 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.polling.alert"));
/* 1212 */                     out.write("');//No I18N\n  \t\t\t}\n  \t\t\telse if(action=='auth')\n  \t\t\t{\n  \t\t\talert('");
/* 1213 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.userpass.alert"));
/* 1214 */                     out.write("');//No I18N\n  \t\t\t}\n  \t\t\tdocument.deletemonitor.operation.selectedIndex=0;\n  \t\t\treturn;\n  \t        }\n\t   var MGselected=\"false\";//No I18N\n\t   var ml=document.deletemonitor;\n\t   var d=new Date();\n\t   var temp='';\n\t\tfor (var i = 0; i < document.deletemonitor.elements.length; i++) {\n\t\t    var e = document.deletemonitor.elements[i];\n\t\t    if (ml.elements[i].name == 'select') {\n\t\t\t   if(ml.elements[i].checked==true){\n\t\t\t\tvar trAttributes = ml.elements[i].parentElement.parentElement.attributes;\n\t\t\t\tif(trAttributes != null)\n\t\t\t\t{\n\t\t\t\t\tvar len = trAttributes.length;\n\t\t\t\t\tfor(j=0;j<len;j++)\n\t\t\t\t\t{\n\t\t\t\t\t\tif(trAttributes[j].name == 'ischild' && trAttributes[j].value == 'true')\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\talert('");
/* 1215 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupview.childtype.operation.notallowed"));
/* 1216 */                     out.write("');//No I18N\n\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t if(temp=='')\n\t\t\t{\n\t\t\t\ttemp=temp+ml.elements[i].value;//No I18N\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\ttemp=temp+','+ml.elements[i].value;//No I18N\n\t\t\t}\n\t\t\t}\n\t\t    }\n\t\t}\n\n  \t  if(action=='poll')\n  \t  {\n  \t  action1='/jsp/ChangeBulkAuthentication.jsp?values='+temp+'&methodName=showMonitorGroupView&haid=");
/* 1217 */                     if (_jspx_meth_c_005fout_005f7(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context))
/*      */                       return;
/* 1219 */                     out.write("&networkName=null&groupName=All&polling=updatePolling&retaintree='+retaintree+\"&unopengroups=\"+unopengroups;//No I18N\n\t  MM_openBrWindow(action1+'&'+d,'secondWindow','resizable=yes,scrollbars=yes,width=450,height=150,top=300,left=300');//No I18N\n\t  }\n\t  else if(action=='auth')\n\t  {\n\t  action1='/jsp/ChangeBulkAuthentication.jsp?values='+temp+'&methodName=showMonitorGroupView&haid=");
/* 1220 */                     if (_jspx_meth_c_005fout_005f8(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context))
/*      */                       return;
/* 1222 */                     out.write("&networkName=null&groupName=All&haid=");
/* 1223 */                     if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context))
/*      */                       return;
/* 1225 */                     out.write("&retaintree='+retaintree+\"&unopengroups=\"+unopengroups;//No I18N\n\t  MM_openBrWindow(action1+'&'+d,'secondWindow','resizable=yes,scrollbars=yes,width=450,height=300,top=200,left=300');//No I18N\n\t  }\n\t  \t  document.deletemonitor.operation.selectedIndex=0;\n\t");
/* 1226 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 1227 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1231 */                 if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 1232 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8); return;
/*      */                 }
/*      */                 
/* 1235 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 1236 */                 out.write("\n}\n  /************* To display the monitor groups based on viewtype ***** STARTS ***********/\n  function disableTreeView()\n  {\n\t\t//if viewtype=\"treeview\" display only in expanded mode\n\t\t//if viewtype=\"show\" display in any mode(expand or collapse mode)\n\t\t");
/* 1237 */                 request.setAttribute("viewtype", "show");
/*      */                 
/* 1239 */                 out.write("\n\t}\n/************* To display the monitor groups based on viewtype ******* ENDS *********/\n//set cookie function\n\tfunction setCookie(name,value,expdays)\n\t{\n      var expdate=new Date();\n      expdate.setDate(expdate.getDate() + expdays);\n      var val=escape(value) + ((expdays==null) ? \"\" : \"; expires=\"+expdate.toUTCString()); //No I18N\n      document.cookie=name + \"=\" + val;\n\t}\n//Get cookie function\n\tfunction getCookie(name)\n\t{\n      var i,x,y,arr=document.cookie.split(\";\");//No I18N\n      y = null;\n      for (i=0;i<arr.length;i++)\n      {\n        x=arr[i].substr(0,arr[i].indexOf(\"=\"));//No I18N\n        y=arr[i].substr(arr[i].indexOf(\"=\")+1);//No I18N\n        x=x.replace(/^\\s+|\\s+$/g,\"\");//No I18N\n        if (x==name)\n        {\n          return unescape(y);\n        }\n      }\n\t}\t\n \tfunction toggleallDivs(action,expandimage)\n    {\n \t\ttry{\n\tvar secondtime=$('#treelink').attr('already');//No I18N\t\n\tif($('#treelink').attr('already')=='false'){\n\topenAllGroups();\n\tsethovertomgaction();\t\n\t}\t\n \t\tvar newDisplay ;\n\t\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n");
/* 1240 */                 out.write("\t\telse newDisplay = \"table-row\"; //Netscape and Mozilla\n\t\tvar collapseid,actionid;\n\t\tvar plus,minus;\n\t\tvar alldivs =document.getElementsByTagName(\"div\");\n\t\tvar myaction='';\n\t\tif(action==\"treeview\"){//No I18N\n\t\t\tdocument.getElementById('showall').style.display=\"none\";//No I18N\n\t\t\tdocument.getElementById('hideall').style.display=\"inline\";//No I18N\n\t\t\tmyaction='hideall';//No I18N\n\t\t}\n\t\telse if(action==\"showall\" || action==\"hideall\"){\n\t\t\tif(action==\"showall\"){//No I18N\n\t\t\t\tnewDisplay=\"none\";//No I18N\n\t\t\t\tdocument.getElementById('showall').style.display=\"inline\";//No I18N\n\t\t\t\tdocument.getElementById('hideall').style.display=\"none\";//No I18N\n\t\t\t\tsetCookie('actionid','showall'); //No I18N\n\t\t\t\tmyaction='showall';//No I18N\n\t\t\t}\n\t\t\telse{\n\t\t\t\tdocument.getElementById('showall').style.display=\"none\";\n\t\t\t\tdocument.getElementById('hideall').style.display=\"inline\";\n\t\t\t\tsetCookie('actionid','hideall'); //No I18N\n\t\t\t\tmyaction='hideall';//No I18N\n\t\t\t}\n\t\t}\n\t\telse\n\t\t{\n\t\t\tcollapseid= document.getElementById(\"showall\");//No I18N\n\t\t\tif(collapseid.style.display==\"inline\" )\n");
/* 1241 */                 out.write("\t\t\t{\n\t\t\t\tdocument.getElementById('showall').style.display=\"none\";//No I18N\n\t\t\t\tdocument.getElementById('hideall').style.display=\"inline\";//No I18N\n\t\t\t\tsetCookie('actionid','hideall'); //No I18N\n\t\t\t\tmyaction='hideall';//No I18N\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t\tnewDisplay=\"none\";//No I18N\n\t\t\t\t\tdocument.getElementById('showall').style.display=\"inline\";//No I18N\n\t\t\t\t\tdocument.getElementById('hideall').style.display=\"none\";//No I18N\n\t\t\t\t\tsetCookie('actionid','showall'); //No I18N\n\t\t\t\t\tmyaction='showall'; //No I18N\n\t\t\t}\n\t\t}\n\t\tif(action==\"treeview\"){\n\t\t\tplus=\"none\";//No I18N\n\t\t\tminus=\"inline\"//No I18N\n\t\t}\n\t\telse{\n\t\t\tif(newDisplay=='none')\n\t\t\t{\n\t\t\t\tplus=\"inline\";//No I18N\n\t\t\t\tminus=\"none\"//No I18N\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tplus=\"none\";//No I18N\n\t\t\t\tminus=\"inline\"//No I18N\n\t\t\t}\n\t\t}\t\t\n\t\tif(newDisplay==\"block\" || newDisplay==\"table-row\")\n\t\t{\n\t\tif(expandimage=='true')\n\t\t{\n\t\t$(\"#allMonitorGroups tr[id^=#monitor][toplevel!=true]\").css(\"display\",newDisplay);//No I18N\n\t\t}\n\t\telse{\n        if(secondtime=='true')\t{\t\n\t\t$('.expandlink').each(function(){\n");
/* 1242 */                 out.write("\t\tvar row=$(this).closest('tr');//No I18N\t\n\t\tvar parenttr = $(row).attr(\"toplevel\");//No I18N\t\t\n\t\tif(parenttr==\"true\"){\t//No I18N\t\n\t\tif(myaction=='hideall'){\t\t\n\t\tvar groupid=$(row).attr(\"groupid\");//No I18N\t\n\t\tvar monhyde=$('#monitorHide'+groupid).css(\"display\");//No I18N\t \t\n\t\tif(monhyde=='none'){\n\t   toggleChildMos('#monitor'+groupid,this);\t//No I18N   \n\t   toggleTreeImage(groupid); //No I18N \n\t   }\n\t\t}\n\t\telse{\n\t\ttoggleChildMos('#monitor'+groupid,this);//No I18N\t\t   \n\t   toggleTreeImage(groupid);//No I18N\t\n\t\t}\n\t       }\t\n\t });\t\t\n\t\t}\n\t\treturn false;\n\t\t}\n\t\t}\n\t\telse{\n\t\t$(\"#allMonitorGroups tr[id^=#monitor][toplevel!=true]\").css(\"display\",newDisplay);//No I18N\n\t\t}\n\t\tif(expandimage=='true'){\t\t\n\t\tfor(var j=0; j < alldivs.length; j++)\n\t\t{\n\t\t\tvar singlediv = alldivs[j];\n\t\t\tvar id = singlediv.id;\n\t\t\tif(id.indexOf(\"monitorHide\")>=0)//No I18N\n\t\t\t{\n\t\t\t\tsinglediv.style.display =minus ;\n\t\t\t}\n\t\t\tif(id.indexOf(\"monitorShow\")>=0)//No I18N\n\t\t\t{\n\t\t\t\tsinglediv.style.display =plus;\n\t\t\t}\n\t\t}\n\t\t}\nelse{\n$('.expandlink').each(function(){\n");
/* 1243 */                 out.write("if($(this).attr('isopen')=='true'){ //No I18N\nvar row=$(this).closest('tr');//No I18N\n$(row).find(\"[id^=monitorHide]\").css(\"display\",minus);//No I18N \n$(row).find(\"[id^=monitorShow]\").css(\"display\",plus);//No I18N \n}\n});\n\n}\t\t\n \t\t}\n \t\tcatch(err)\n \t\t{\n \t\t\tconsole.log('err in toggleallDivs '+err); \t\t\t\n \t\t}\n \t}\n\tfunction sethovertomgaction(){\n\ttry{\t  \n\t\t $(\"#allMonitorGroups tr div[id^=mgaction]\").css('display','none');//No I18N\n                                 $(\"#allMonitorGroups tr[id^=#monitor]\").hover(function() {//No I18N\n\n                                        $(this).find(\"div[id^=mgaction]\").css('display','inline'); //No I18N\n\n                                },\n                                function () {\n                                          $(this).find(\"div[id^=mgaction]\").css('display','none'); //No I18N\n\n                                });\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\tcatch(err){\n\t\t\t\t\t\t\t\tconsole.log(err);\n\t\t\t\t\t\t\t\t}\n\t\n\t}\nfunction openAllGroups()\n{\ntry{\n$('.expandlink').each(function(){\nif(this.getAttribute('isopen')=='false'){\n");
/* 1244 */                 out.write("var parenttr = $(this).parent().parent().attr(\"toplevel\");//No I18N\nif(parenttr==\"true\"){\nvar row=$(this).closest('tr');//No I18N \nvar groupid=$(row).attr(\"groupid\");//No I18N\nchrcked=\"false\";\nif ( $(row).find('input:checkbox:first').is(':checked') ) {\nchrcked=\"true\"; //No I18N\n}\nvar url='/showresource.do?method=showMonitorGroupViewIndividual&parents='+groupid+'&lindx=1&pageno=1&alllevels=false&checked='+chrcked;//No I18N\ngetDynamicCall(url,this); //No I18N\n$(this).attr('isopen','true');//No I18N\ntoggleTreeImage(groupid);\n}\n}\n}\n);\n$('#treelink').attr(\"already\",'true');//No I18N\n}\ncatch(err)\n{\nconsole.log('openAllGroups    '+err);//No I18N\n}\n}\nvar rows;\nvar rowcount,start;\nvar idtotoggle;\nvar toggletype;\nfunction toggleChildMos(tempidtotoggle,obj)\n{\ntry{\n\tvar isopen=obj.getAttribute(\"isopen\");\t//No I18N\n\tif(isopen==\"false\"){//No I18N\n\tobj.setAttribute(\"isopen\",\"true\");//No I18N\n\treturn ;\n\t}\n\tidtotoggle=tempidtotoggle;\t\n\tvar table = document.getElementById(\"allMonitorGroups\");//No I18N\t\n\trows = table.rows;\n\trowcount = rows.length;\t\n");
/* 1245 */                 out.write("\tfor( i=2;i<rowcount;i++)\n\t{\n\t\tvar myrow = rows[i];\n\t\tif(myrow.id==idtotoggle)\n\t\t{\n\t\t\tif(rows[i].style.display=='none')\n\t\t\t{\n\t\t\t    toggletype='none';\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t    toggletype='block';\n\t\t\t}\n\t\t\tbreak;//No I18N\n\t\t}\n\t}\n\tif(toggletype=='none')\n\t{\n\t  slideDown();\n\t}\n\telse\n\t{\n\thideOtherRows();\n\t}\n        return false;\n\t\t}\n\t\tcatch(err) \n\t\t{\n\t\tconsole.log('toggleChildMos err'+err); \n\t\t}\n}\n\nfunction hideOtherRows()\n{\n\n         var newDisplay = \"block\";//No I18N\n\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n         else newDisplay = \"table-row\"; //Netscape and Mozilla\n\tvar i;\n\tfor(i=2;i<rowcount;i++)\n\t{\n\tif(rows[i].id.indexOf( idtotoggle)!=-1)\n\t{\n\n\t\trows[i].style.display = \"none\";//No I18N\n\t}\n\n\t}\n\treturn;\n\n}\n\n\n\n $(function() {\n         $('#allMonitorGroups tr').mouseover(function() {   //No I18N\n            $(this).addClass('monitorgpHeaderHover'); //No I18N\n         }).mouseout(function() {\n            $(this).removeClass('monitorgpHeaderHover');//No I18N\n         });\n      });\n\n\n\n\nfunction slideDown()\n");
/* 1246 */                 out.write("{\n\t  var newDisplay = \"block\";//No I18N\n\t\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n\t        else newDisplay = \"table-row\"; //Netscape and Mozilla\n\t\tvar i;\n\t\tfor(i=2;i<rowcount;i++)\n\t\t{\n\t\tif(rows[i].id == idtotoggle)\n\t\t{\n\n\t\t\trows[i].style.display = newDisplay;//No I18N\n\t\t\trows[i].removeAttribute(\"style\");//No I18N\n\t\t\trows[i].className = \"leftcells\";//No I18N\n\t\t}\n\t\telse\n\t\t{\n\t\trows[i].style.background = \"#FFFFFF\";//No I18N\n\t\t}\n\t        }\n\treturn;\n\n}\nfunction toggleTreeImage(divname)\n{\ntry{\n\t var hide1=\"monitorHide\"+divname;\n\t var show1=\"monitorShow\"+divname;\n\t if(document.getElementById(show1))\n\t {\n\t if(document.getElementById(show1).style.display == 'inline')\n\t {\n\t //if it is to show the child elements just return changing the image of current monitor group level and return\n\t document.getElementById(show1).style.display='none';\n\t document.getElementById(hide1).style.display='inline';\n\t return;\n\t }\n\t }\n\t else\n\t {\n\t return;\n\t }\n\t //else if it is to hide an monitor group then parse through all the child elements and find subgroups and change the images to minus\n");
/* 1247 */                 out.write("\t  var alldivs =document.getElementsByTagName(\"div\");//No I18N\n\t     var i;\n\t     for(i=0; i <alldivs.length ; i++)\n             {\n\t                if((alldivs[i].id.indexOf(hide1)) >= 0)\n\t                {\n\t                hidediv=document.getElementById(alldivs[i].id) ;\n\t                if(hidediv)\n\t                {\n\t                if(hidediv.style.display == 'inline')\n\t\t\thidediv.style.display='none';//No I18N\n\t\t\t}\n                        }\n                        if((alldivs[i].id.indexOf(show1)) >= 0)\n\t\t\t{\n\t\t\tvar showdiv;\n\t\t\tshowdiv=document.getElementById(alldivs[i].id) ;\n\t\t\tif(showdiv)\n\t\t\t{\n\t\t\tif(showdiv.style.display == 'none')\n\t\t\tshowdiv.style.display='inline';\n\t\t\t}\n                        }\n            }\n\t\t\t}\n\t\t\tcatch(err)\n\t\t\t{\n\t\t\tconsole.log('err in toggleTreeImage '+err);\n\t\t\t}\n}\n \n \nfunction getDynamicCall(url,obj,sethover) \n{\ntry{\nvar isopen=obj.getAttribute(\"isopen\");//No I18N\nvar inprogress=\"false\";//No I18N\nif(isopen==\"true\" ){//No I18N\nreturn ;\n }\nvar row = $(obj).parent().parent();\nvar indx =row.index();\n");
/* 1248 */                 out.write("var chrcked=\"false\";//No I18N\nif ( $(row).find('input:checkbox:first').is(':checked') ) {//No I18N\nchrcked=\"true\"; //No I18N\n}//No I18N\nurl=url+'&checked='+chrcked;//No I18N\n $.ajax({\n  type:'POST',//No I18N \n  url: url,\n  async:false,\n  success: function(data){\n  $(document).ready(function() {\n  $('#allMonitorGroups > tbody > tr').eq(indx).after(data);   //No I18N\n  }); \n              }\n\t\t\t   });\n   if(sethover==\"true\"){//No I18N \n\t sethovertomgaction();\n\t }\n}\ncatch(err){\nconsole.log('getDynamicCall'+err);//No I18N \n}\n}\n\nfunction showmoremonitors(url,obj,sethover) \n{\ntry{\nvar isopen=obj.getAttribute(\"isopen\");//No I18N\nvar inprogress=\"false\";//No I18N\nif(isopen==\"true\" ){\nreturn ;\n } \nvar row = $(obj).closest('tr');//No I18N \nvar td1=$(obj).closest('td');//No I18N \n$(obj).remove();\n$(td1).html(\"<span class='fetching-tag'>");
/* 1249 */                 out.print(FormatUtil.getString("am.webclient.as400.fetch.wait"));
/* 1250 */                 out.write("</span>\");\nvar checked=findparentselected(row);\nurl=url+'&checked='+checked;//No I18N \nvar indx =row.index();\n $.ajax({\n  type:'POST',//No I18N \n  url: url,\n  async:false,\n  success: function(data){\n  $(document).ready(function() {\n  $('#allMonitorGroups > tbody > tr').eq(indx).after(data);   \n  }); \n              }\n\t\t\t   });\n   if(sethover==\"true\"){//No I18N\n\t sethovertomgaction();\n\t }\t \n\t $(row).remove();\n}\ncatch(err){\nconsole.log('showmoremonitors'+err);//No I18N \n}\n}\nfunction findparentselected(row){\nvar checked1=\"false\";//No I18N \ntry{\nvar rowid=$(row).attr('id');//No I18N \nvar groupid11='';//No I18N \nif (rowid.indexOf(\"|\") >= 0)\n{\ngroupid11=rowid.substring(rowid.lastIndexOf('|')+1,rowid.length);\n}\nelse{\ngroupid11=groupid11=rowid.substring(rowid.lastIndexOf('r')+1,rowid.length);\n}\n$('#allMonitorGroups > tbody  > tr').each(function() {                     \n                     if($(this).attr(\"isgroup\")==\"true\"){\t\t\t\t\t \n                     if ( $(this).find('input:checkbox:first').is(':checked') && $(this).attr(\"groupid\")==groupid11) {//No I18N\t\t\t\t\t \n");
/* 1251 */                 out.write("                         checked1=\"true\"; \n\t\t\t\t\t\t return false;\n                      }\n\t\t\t\t\t\t }\t\t\t\t\t\t \n\t\t\t\t\t\t }\n\t\t\t\t\t\t );\t\n\t\treturn checked1;\t\t\t\t \n}\ncatch(err){\nconsole.log('error in findparentselected'+err);//No I18N\n}\nreturn \"false\";\n}\nfunction getDynamicCall11(url,row) \n{\ntry{\nvar inprogress=\"false\";//No I18N \nvar indx=row.index();\n\n $.ajax({\n  type:'POST',//No I18N \n  url: url,\n  async:false,\n  success: function(data){\n  $('#allMonitorGroups > tbody > tr').eq(indx).after(data);  \n              }\n\t\t\t   });\n}\ncatch(err){\nconsole.log('getDynamicCall11'+err); \n}\n}\n</script>\n\n");
/* 1252 */                 if ((request.getAttribute("treewithLayout") != null) && (((String)request.getAttribute("treewithLayout")).equals("true"))) {
/* 1253 */                   out.write(10);
/* 1254 */                   if (_jspx_meth_c_005fset_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 1256 */                   out.write(10);
/*      */                   
/* 1258 */                   String title = FormatUtil.getString("am.webclient.monitorgroupview.title");
/* 1259 */                   String toAppendLink = "";
/* 1260 */                   if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 1261 */                     out.write(10);
/* 1262 */                     out.write("<!--$Id$-->\n\n<script>\nvar urlredirect = new Array();\nurlredirect[0] = '/showresource.do?method=showResourceTypesAll&group=All");
/* 1263 */                     out.print(toAppendLink);
/* 1264 */                     out.write("';\n</script>\n");
/*      */                     
/* 1266 */                     if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1267 */                       out.write("\n  <script>\n     urlredirect[0]='showresource.do?method=showResourceTypes&detailspage=true&network=MSSQL-DB-server&viewmontype=MSSQL-DB-server");
/* 1268 */                       out.print(toAppendLink);
/* 1269 */                       out.write("';\n </script>\n ");
/*      */                     }
/*      */                     
/* 1272 */                     out.write("\n <script>\nurlredirect[1] = '/showresource.do?method=showResourceTypes");
/* 1273 */                     out.print(toAppendLink);
/* 1274 */                     out.write("&monitor_viewtype=categoryview';\nurlredirect[2] = '/showresource.do?method=showPlasmaView';\nurlredirect[3] = '/showresource.do?method=showMonitorGroupView';\nurlredirect[4] = '/showresource.do?method=showGMapView&group=All");
/* 1275 */                     out.print(toAppendLink);
/* 1276 */                     out.write("';\nurlredirect[5] = '/showresource.do?method=showIconsView");
/* 1277 */                     out.print(toAppendLink);
/* 1278 */                     out.write("';\nurlredirect[6] = '/showresource.do?method=showDetailsView");
/* 1279 */                     out.print(toAppendLink);
/* 1280 */                     out.write("';\n\n</script>\n\n\n\n\n");
/*      */                     
/* 1282 */                     FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 1283 */                     _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 1284 */                     _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                     
/* 1286 */                     _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */                     
/* 1288 */                     _jspx_th_html_005fform_005f0.setStyle("display :inline");
/* 1289 */                     int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 1290 */                     if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                       for (;;) {
/* 1292 */                         out.write(10);
/* 1293 */                         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1295 */                         out.write(10);
/* 1296 */                         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 1298 */                         out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"62%\" height=\"35\" class=\"monitorsheading\">\n      <table width=\"100%\">\n      <tr>\n        <td  height=\"35\"   width=\"70%\" class=\"monitorsheading\"> ");
/* 1299 */                         out.print(title);
/* 1300 */                         out.write(" </td>\n        <td  height=\"35\" class=\"monitorsheading\"  style=\"padding-left:2px\">\n    ");
/* 1301 */                         String CategoryViewtype = (String)request.getAttribute("categorytype");
/* 1302 */                         if (CategoryViewtype == null) {
/* 1303 */                           CategoryViewtype = "";
/*      */                         }
/* 1305 */                         String monitorviewtype = (String)request.getAttribute("monitor_viewtype");
/* 1306 */                         if (monitorviewtype == null) {
/* 1307 */                           monitorviewtype = "";
/*      */                         }
/* 1309 */                         if (monitorviewtype.startsWith("CategoryView")) {
/* 1310 */                           if (CategoryViewtype.equals("added monitors"))
/*      */                           {
/* 1312 */                             out.write("          <a  href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\"  class=\"staticlinks\" onclick=\"javascript:setCookieval('showAddedMonitors');\">");
/* 1313 */                             out.print(FormatUtil.getString("am.monitortab.category.AddedMonitors.text"));
/* 1314 */                             out.write("</a>\n  ");
/*      */ 
/*      */                           }
/* 1317 */                           else if (CategoryViewtype.equals("all monitors"))
/*      */                           {
/* 1319 */                             out.write("            <a href=\"/showresource.do?method=showResourceTypes\"   class=\"staticlinks\" onclick=\"javascript:setCookieval('showAllMonitors');\">");
/* 1320 */                             out.print(FormatUtil.getString("am.monitortab.category.AllMonitors.text"));
/* 1321 */                             out.write("</a>\n\n  ");
/*      */                           }
/*      */                         }
/*      */                         
/*      */ 
/* 1326 */                         out.write("\n        </td>\n      </tr>\n      </table>\n    </td>\n    ");
/*      */                         
/* 1328 */                         String tempStl = "center";
/* 1329 */                         if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                         {
/* 1331 */                           tempStl = "right";
/*      */                           
/* 1333 */                           out.write("\n      <td align=\"right\" width=\"30%\" class=\"bodytext\" style=\"white-space:nowrap;\">\n      ");
/* 1334 */                           if (PluginUtil.isPlugin()) {
/* 1335 */                             out.write("\n      ");
/*      */                             
/* 1337 */                             PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1338 */                             _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 1339 */                             _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 1341 */                             _jspx_th_logic_005fpresent_005f9.setRole("ADMIN");
/* 1342 */                             int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 1343 */                             if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                               for (;;) {
/* 1345 */                                 out.write("\n        <span id=\"createNewMG\">");
/* 1346 */                                 out.print(FormatUtil.getString("am.monitortab.creategroup.text"));
/* 1347 */                                 out.write(" :  &nbsp;</span>\n        <select id=\"createMG\" onchange=\"javascript:changeMGURL(this)\" styleClass=\"formtext\" style=\"margin-right: 30px;display:none;\">\n          <option value=\"createNewMG\" selected>");
/* 1348 */                                 out.print(FormatUtil.getString("am.monitortab.selectgrouptype.text"));
/* 1349 */                                 out.write("</option>\n          <option value=\"1\">");
/* 1350 */                                 out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 1351 */                                 out.write("</option>\n          <option value=\"2\">");
/* 1352 */                                 out.print(FormatUtil.getString("am.webclient.mg.type.webappgroup"));
/* 1353 */                                 out.write("</option>\n          <option value=\"3\">");
/* 1354 */                                 out.print(FormatUtil.getString("am.webclient.mg.type.vcenter"));
/* 1355 */                                 out.write("</option>\n        </select>\n      ");
/* 1356 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 1357 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1361 */                             if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 1362 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                             }
/*      */                             
/* 1365 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 1366 */                             out.write("\n     ");
/*      */                           }
/* 1368 */                           out.write("\n\n      ");
/* 1369 */                           out.print(FormatUtil.getString("am.monitortab.selectview.text"));
/* 1370 */                           out.write(" :  &nbsp;\n\n      ");
/*      */                           
/* 1372 */                           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 1373 */                           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1374 */                           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 1376 */                           _jspx_th_html_005fselect_005f0.setProperty("defaultmonitorsview");
/*      */                           
/* 1378 */                           _jspx_th_html_005fselect_005f0.setOnchange("javascript:changeUrl(this)");
/*      */                           
/* 1380 */                           _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/* 1381 */                           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1382 */                           if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1383 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1384 */                               out = _jspx_page_context.pushBody();
/* 1385 */                               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 1386 */                               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1389 */                               out.write("\n        ");
/*      */                               
/* 1391 */                               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1392 */                               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 1393 */                               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/* 1395 */                               _jspx_th_html_005foption_005f0.setValue("showResourceTypesAll");
/* 1396 */                               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 1397 */                               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 1398 */                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1399 */                                   out = _jspx_page_context.pushBody();
/* 1400 */                                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 1401 */                                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1404 */                                   out.print(FormatUtil.getString("am.monitortab.bulkconfiguration.text"));
/* 1405 */                                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 1406 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1409 */                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1410 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1413 */                               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 1414 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                               }
/*      */                               
/* 1417 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 1418 */                               out.write("\n        ");
/*      */                               
/* 1420 */                               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1421 */                               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 1422 */                               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/* 1424 */                               _jspx_th_html_005foption_005f1.setValue("showResourceTypes");
/* 1425 */                               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 1426 */                               if (_jspx_eval_html_005foption_005f1 != 0) {
/* 1427 */                                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1428 */                                   out = _jspx_page_context.pushBody();
/* 1429 */                                   _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 1430 */                                   _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1433 */                                   out.print(FormatUtil.getString("am.monitortab.category.text"));
/* 1434 */                                   int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 1435 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1438 */                                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1439 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1442 */                               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 1443 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                               }
/*      */                               
/* 1446 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 1447 */                               out.write("\n        ");
/*      */                               
/* 1449 */                               OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1450 */                               _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 1451 */                               _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/* 1453 */                               _jspx_th_html_005foption_005f2.setValue("plasmaView");
/* 1454 */                               int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 1455 */                               if (_jspx_eval_html_005foption_005f2 != 0) {
/* 1456 */                                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1457 */                                   out = _jspx_page_context.pushBody();
/* 1458 */                                   _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 1459 */                                   _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1462 */                                   out.print(FormatUtil.getString("am.monitortab.plasmaview.text"));
/* 1463 */                                   int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 1464 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1467 */                                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1468 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1471 */                               if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 1472 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                               }
/*      */                               
/* 1475 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 1476 */                               out.write("\n        ");
/*      */                               
/* 1478 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f9 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1479 */                               _jspx_th_logic_005fnotPresent_005f9.setPageContext(_jspx_page_context);
/* 1480 */                               _jspx_th_logic_005fnotPresent_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/* 1482 */                               _jspx_th_logic_005fnotPresent_005f9.setRole("OPERATOR");
/* 1483 */                               int _jspx_eval_logic_005fnotPresent_005f9 = _jspx_th_logic_005fnotPresent_005f9.doStartTag();
/* 1484 */                               if (_jspx_eval_logic_005fnotPresent_005f9 != 0) {
/*      */                                 for (;;) {
/* 1486 */                                   out.write("\n        ");
/*      */                                   
/* 1488 */                                   OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1489 */                                   _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 1490 */                                   _jspx_th_html_005foption_005f3.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                   
/* 1492 */                                   _jspx_th_html_005foption_005f3.setValue("showMonitorGroupView");
/* 1493 */                                   int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 1494 */                                   if (_jspx_eval_html_005foption_005f3 != 0) {
/* 1495 */                                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 1496 */                                       out = _jspx_page_context.pushBody();
/* 1497 */                                       _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 1498 */                                       _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 1501 */                                       out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 1502 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 1503 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 1506 */                                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 1507 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 1510 */                                   if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 1511 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                   }
/*      */                                   
/* 1514 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 1515 */                                   out.write("\n        ");
/*      */                                   
/* 1517 */                                   OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1518 */                                   _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 1519 */                                   _jspx_th_html_005foption_005f4.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                   
/* 1521 */                                   _jspx_th_html_005foption_005f4.setValue("showGMapView");
/* 1522 */                                   int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 1523 */                                   if (_jspx_eval_html_005foption_005f4 != 0) {
/* 1524 */                                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 1525 */                                       out = _jspx_page_context.pushBody();
/* 1526 */                                       _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 1527 */                                       _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 1530 */                                       out.print(FormatUtil.getString("am.monitortab.gmap.text"));
/* 1531 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 1532 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 1535 */                                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 1536 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 1539 */                                   if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 1540 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                   }
/*      */                                   
/* 1543 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 1544 */                                   out.write("\n        ");
/* 1545 */                                   if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1546 */                                     out.write("\n        ");
/*      */                                     
/* 1548 */                                     OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1549 */                                     _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 1550 */                                     _jspx_th_html_005foption_005f5.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                     
/* 1552 */                                     _jspx_th_html_005foption_005f5.setValue("showIconsView");
/* 1553 */                                     int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 1554 */                                     if (_jspx_eval_html_005foption_005f5 != 0) {
/* 1555 */                                       if (_jspx_eval_html_005foption_005f5 != 1) {
/* 1556 */                                         out = _jspx_page_context.pushBody();
/* 1557 */                                         _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 1558 */                                         _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1561 */                                         out.print(FormatUtil.getString("am.monitortab.icons.text"));
/* 1562 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 1563 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1566 */                                       if (_jspx_eval_html_005foption_005f5 != 1) {
/* 1567 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1570 */                                     if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 1571 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                     }
/*      */                                     
/* 1574 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 1575 */                                     out.write("\n        ");
/*      */                                     
/* 1577 */                                     OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1578 */                                     _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 1579 */                                     _jspx_th_html_005foption_005f6.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                     
/* 1581 */                                     _jspx_th_html_005foption_005f6.setValue("showDetailsView");
/* 1582 */                                     int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 1583 */                                     if (_jspx_eval_html_005foption_005f6 != 0) {
/* 1584 */                                       if (_jspx_eval_html_005foption_005f6 != 1) {
/* 1585 */                                         out = _jspx_page_context.pushBody();
/* 1586 */                                         _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 1587 */                                         _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1590 */                                         out.print(FormatUtil.getString("am.monitortab.table.text"));
/* 1591 */                                         int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 1592 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1595 */                                       if (_jspx_eval_html_005foption_005f6 != 1) {
/* 1596 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1599 */                                     if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 1600 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                     }
/*      */                                     
/* 1603 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 1604 */                                     out.write("\n        ");
/*      */                                   }
/* 1606 */                                   out.write("\n        ");
/*      */                                   
/* 1608 */                                   OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1609 */                                   _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 1610 */                                   _jspx_th_html_005foption_005f7.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                   
/* 1612 */                                   _jspx_th_html_005foption_005f7.setValue("showFlashView");
/* 1613 */                                   int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 1614 */                                   if (_jspx_eval_html_005foption_005f7 != 0) {
/* 1615 */                                     if (_jspx_eval_html_005foption_005f7 != 1) {
/* 1616 */                                       out = _jspx_page_context.pushBody();
/* 1617 */                                       _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 1618 */                                       _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 1621 */                                       out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/* 1622 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 1623 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 1626 */                                     if (_jspx_eval_html_005foption_005f7 != 1) {
/* 1627 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 1630 */                                   if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 1631 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                   }
/*      */                                   
/* 1634 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 1635 */                                   out.write("\n        ");
/* 1636 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f9.doAfterBody();
/* 1637 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1641 */                               if (_jspx_th_logic_005fnotPresent_005f9.doEndTag() == 5) {
/* 1642 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9); return;
/*      */                               }
/*      */                               
/* 1645 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9);
/* 1646 */                               out.write("\n      ");
/* 1647 */                               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 1648 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1651 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1652 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1655 */                           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 1656 */                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                           }
/*      */                           
/* 1659 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 1660 */                           out.write("\n\n      ");
/* 1661 */                           if (!PluginUtil.isPlugin()) {
/* 1662 */                             out.write("\n      <span class=\"bodytext\">\n        ");
/*      */                             
/* 1664 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f10 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1665 */                             _jspx_th_logic_005fnotPresent_005f10.setPageContext(_jspx_page_context);
/* 1666 */                             _jspx_th_logic_005fnotPresent_005f10.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 1668 */                             _jspx_th_logic_005fnotPresent_005f10.setRole("OPERATOR");
/* 1669 */                             int _jspx_eval_logic_005fnotPresent_005f10 = _jspx_th_logic_005fnotPresent_005f10.doStartTag();
/* 1670 */                             if (_jspx_eval_logic_005fnotPresent_005f10 != 0) {
/*      */                               for (;;) {
/* 1672 */                                 out.write("\n          ");
/*      */                                 
/* 1674 */                                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1675 */                                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 1676 */                                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fnotPresent_005f10);
/*      */                                 
/* 1678 */                                 _jspx_th_c_005fif_005f10.setTest("${globalconfig['defaultmonitorsview'] != requestScope.defaultview}");
/* 1679 */                                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 1680 */                                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                   for (;;) {
/* 1682 */                                     out.write("\n            <input type=hidden name=\"method\" value=\"setDefaultMonitorsView\">\n        <a href=\"javascript:setMonitorsViewDefault()\" class=\"new-monitordiv-link\">");
/* 1683 */                                     out.print(FormatUtil.getString("am.monitortab.setasdefaultview.text"));
/* 1684 */                                     out.write(" </a>\n          ");
/* 1685 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 1686 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1690 */                                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 1691 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                 }
/*      */                                 
/* 1694 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1695 */                                 out.write("\n        ");
/* 1696 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f10.doAfterBody();
/* 1697 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1701 */                             if (_jspx_th_logic_005fnotPresent_005f10.doEndTag() == 5) {
/* 1702 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f10); return;
/*      */                             }
/*      */                             
/* 1705 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f10);
/* 1706 */                             out.write("\n      </span>\n      ");
/*      */                           }
/* 1708 */                           out.write("\n      </td>\n      ");
/*      */                         }
/*      */                         
/* 1711 */                         out.write("\n      ");
/*      */                         
/* 1713 */                         String location = (String)pageContext.getAttribute("setdefaultlocation");
/* 1714 */                         if ((location != null) && (location.equals("Googleview")) && (request.getAttribute("key_set") != null) && (request.getAttribute("key_set").equals("true")))
/*      */                         {
/* 1716 */                           out.write("\n      <td colspan=\"2\" align=\"");
/* 1717 */                           out.print(tempStl);
/* 1718 */                           out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 1719 */                           out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" class=\"staticlinks\">");
/* 1720 */                           out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 1721 */                           out.write("</a>\n       </span>\n\t  </td> \n      ");
/*      */                         }
/*      */                         
/*      */ 
/* 1725 */                         out.write("\n      ");
/*      */                         
/* 1727 */                         IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1728 */                         _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 1729 */                         _jspx_th_c_005fif_005f11.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1731 */                         _jspx_th_c_005fif_005f11.setTest("${AMActionForm.showMapView == true}");
/* 1732 */                         int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 1733 */                         if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                           for (;;) {
/* 1735 */                             out.write("\n      <td colspan=\"2\" align=\"");
/* 1736 */                             out.print(tempStl);
/* 1737 */                             out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 1738 */                             out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" id=\"savezoomlevel\" class=\"staticlinks\">");
/* 1739 */                             out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 1740 */                             out.write("</a>\n       </span>\n\t  </td> \n      ");
/* 1741 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 1742 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1746 */                         if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 1747 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                         }
/*      */                         
/* 1750 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1751 */                         out.write("\n  </tr>\n</table>\n");
/* 1752 */                         int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 1753 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1757 */                     if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 1758 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                     }
/*      */                     
/* 1761 */                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 1762 */                     out.write("\n\n\n<SCRIPT LANGUAGE=\"Javascript1.2\">\nvar defaultview = \"");
/* 1763 */                     out.print(request.getAttribute("defaultview"));
/* 1764 */                     out.write("\";\nif(defaultview == \"showMonitorGroupView\")//No I18N\n{\n  $('#createMG').show();//No I18N\n  $('#createNewMG').show();//No I18N\n}\nelse\n{\n  $('#createMG').hide();//No I18N\n  $('#createNewMG').hide();//No I18N\n}\n//Set cookie function\nfunction setCookie(name,value,expdays)\n{\n       var expdate=new Date();   //No I18N\n       expdate.setDate(expdate.getDate() + expdays);\n       var val=escape(value) + ((expdays==null) ? \"\" : \"; expires=\"+expdate.toUTCString());  //No I18N\n       document.cookie=name + \"=\" + val;   //No I18N\n}\n\n// Get cookie function\nfunction getCookie(name)\n{\n       var i,x,y,arr=document.cookie.split(\";\");   //No I18N\n       y = null;\n       for (i=0;i<arr.length;i++)\n       {\n         x=arr[i].substr(0,arr[i].indexOf(\"=\"));   //No I18N\n         y=arr[i].substr(arr[i].indexOf(\"=\")+1);   //No I18N\n         x=x.replace(/^\\s+|\\s+$/g,\"\");   //No I18N\n         if (x==name)\n         {\n           return unescape(y);\n         }\n       }\n}\nfunction setCookieval(Category_type){\n  //alert(Category_type);\n  if(Category_type==\"showAddedMonitors\"){\n");
/* 1765 */                     out.write("    setCookie('Category_type','showAddedMonitors');  //No I18N\n  }\n  else{\n    setCookie('Category_type','showAllMonitors');  //No I18N\n  }\n}\n\nfunction setMonitorsViewDefault() {\n");
/* 1766 */                     if (_jspx_meth_logic_005fpresent_005f10(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                       return;
/* 1768 */                     out.write(10);
/* 1769 */                     out.write(32);
/* 1770 */                     out.write(32);
/* 1771 */                     if (_jspx_meth_logic_005fnotPresent_005f11(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                       return;
/* 1773 */                     out.write("\n}\n\nfunction changeMGURL(a)\n{\n  var er = /^[0-9]+$/;\n  if(!er.test(a.value))\n  {\n    return;\n  }\n  location.href = '/admin/createapplication.do?method=createapp&grouptype='+a.value;\n}\n\nfunction changeUrl(a)\n{\n\t if(a.selectedIndex == 2 || a.selectedIndex == 7)\n\t {\n \t\tvar url = urlredirect[2];\n \t\tvar windowOpenOptions='scrollbars=1,resizable=1,width=900,height=650,left=50,screenX=50,screenY=25,top=25';\n \t\tvar name = \"PlasmaView\"; // NO I18N\n \t\t\n \t\tif(a.selectedIndex == 7)\n \t\t{\n \t\t\turl = '/GraphicalView.do?method=popUp&haid=0&isPopUp=true'; // NO I18N\n \t\t\twindowOpenOptions = 'scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25'; // NO I18N\n \t\t\tname = \"FlashView\"; // NO I18N\n \t\t}\n\t\twindow.open(url, name, windowOpenOptions);\n\n\t\tvar defaultview = \"");
/* 1774 */                     out.print(request.getAttribute("defaultview"));
/* 1775 */                     out.write("\";\n        \n        if(defaultview == \"showResourceTypesAll\")\n        {\n\t\t\ta.selectedIndex =0;\n        }\n        else if(defaultview == \"showResourceTypes\")\n        {\n            a.selectedIndex = 1;\n        }\n        else if(defaultview == \"showMonitorGroupView\")\n        {\n            a.selectedIndex = 3;\n        }\n        else if(defaultview == \"showGMapView\")\n        {\n\t\t\ta.selectedIndex = 4;\n        }\n        else if(defaultview == \"showIconsView\")\n        {\n            a.selectedIndex = 5;\n        }\n        else if(defaultview == \"showDetailsView\")\n        {\n            a.selectedIndex = 6;\n        }\n        return;\n\t}\n\tlocation.href=urlredirect[a.selectedIndex]; //NO I18N\n}\n</script>\n");
/* 1776 */                     out.write(10);
/* 1777 */                     out.write(32);
/*      */                   }
/* 1779 */                   out.write(10);
/* 1780 */                   out.write(10);
/*      */                 }
/* 1782 */                 out.write("\n<table id=\"tableID\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" width=\"100%\" class=\"lrbtborder\" style=\"margin-top:5px;\">\n<tbody>\n<tr>\n<td width=\"100%\" valign=\"top\">\n<form action=\"/deleteMO.do?method=deleteMO\" name=\"deletemonitor\" method=\"post\" style=\"display:inline\" >\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" valign=\"center\">\n<tr height=\"28\">\n\n <td class=\"bodytextbold tableheadingbborder\" colspan=\"13\" align=\"left\" valign=\"center\" width=\"65%\" style=\"height:50px;padding-left:20px\"  >\n ");
/* 1783 */                 if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 1784 */                   out.write("\n       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n ");
/*      */                   
/* 1786 */                   PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1787 */                   _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 1788 */                   _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 1790 */                   _jspx_th_logic_005fpresent_005f11.setRole("ADMIN,ENTERPRISEADMIN");
/* 1791 */                   int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 1792 */                   if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                     for (;;) {
/* 1794 */                       out.write(32);
/* 1795 */                       out.write(10);
/* 1796 */                       out.write(32);
/* 1797 */                       out.write("<!--$Id$-->\n<script type=\"text/javascript\" src=\"/template/chosen.jquery.min.js\"></script>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n ");
/*      */                       
/* 1799 */                       PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1800 */                       _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 1801 */                       _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_logic_005fpresent_005f11);
/*      */                       
/* 1803 */                       _jspx_th_logic_005fpresent_005f12.setRole("DEMO,ADMIN");
/* 1804 */                       int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 1805 */                       if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                         for (;;) {
/* 1807 */                           out.write("&nbsp;<span class=\"filterby-txt input-uptime\">");
/* 1808 */                           out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 1809 */                           out.write(" :</span>\n                 <select style=\"width:220px\"  data-placeholder=\"");
/* 1810 */                           out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 1811 */                           out.write("\" tabindex=\"5\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\" name=\"operation\">\n                     <option value=\"\">   </option>\n\t\t\t\t\t <option value=\"customField\">");
/* 1812 */                           out.print(FormatUtil.getString("am.myfield.assign.text"));
/* 1813 */                           out.write("</option>\n                     <option value=\"Delete\">");
/* 1814 */                           out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 1815 */                           out.write("</option>\n\t\t\t");
/* 1816 */                           if ((request.getParameter("method").equals("showMonitorGroupView")) || (request.getParameter("method").equals("showSubGroupTree"))) {
/* 1817 */                             out.write("\n\t\t     <option VALUE=\"Disable Actions\">");
/* 1818 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupview.disableaction.text"));
/* 1819 */                             out.write("</option>\n\t\t     <option VALUE=\"Enable Actions\">");
/* 1820 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupview.enableaction.text"));
/* 1821 */                             out.write("  </option>\n\n");
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/* 1826 */                           if (!request.getParameter("method").equals("showMonitorGroupView"))
/*      */                           {
/* 1828 */                             if (!"VirtualMachine".equals(resourceName))
/*      */                             {
/*      */ 
/*      */ 
/* 1832 */                               out.write("\n                     <option value=\"editDisplayNames\">");
/* 1833 */                               out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 1834 */                               out.write("</option>\n                    ");
/*      */                               
/* 1836 */                               if (!request.getParameter("method").equals("showSubGroupTree"))
/*      */                               {
/* 1838 */                                 out.write("\n\t\t\t\t\t\t<option value=\"updateIP\">");
/* 1839 */                                 out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 1840 */                                 out.write("</option>\n\t\t\t\t\t");
/*      */                               }
/* 1842 */                               out.write("\n\t\t\t");
/*      */                             }
/*      */                           }
/*      */                           
/*      */ 
/* 1847 */                           out.write("\n\t\t     <option value=\"Manage\">");
/* 1848 */                           out.print(FormatUtil.getString("Manage"));
/* 1849 */                           out.write("</option>\n             <option value=\"Unmanage\">");
/* 1850 */                           out.print(FormatUtil.getString("UnManage"));
/* 1851 */                           out.write("</option>\n             <option value=\"unManageAndReset\">");
/* 1852 */                           out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 1853 */                           out.write("</option>\n\t\t\t");
/*      */                           
/* 1855 */                           if (!"VirtualMachine".equals(resourceName))
/*      */                           {
/*      */ 
/* 1858 */                             out.write("\n                     <option value=\"updateauth\">");
/* 1859 */                             out.print(FormatUtil.getString("am.webclient.updateauthentication.text"));
/* 1860 */                             out.write("</option>\n                     <option value=\"updatepoll\">");
/* 1861 */                             out.print(FormatUtil.getString("am.webclient.updatepolling.text"));
/* 1862 */                             out.write("</option>\n\t\t\t");
/*      */                           }
/*      */                           
/* 1865 */                           if ((!request.getParameter("method").equals("showMonitorGroupView")) && (!request.getParameter("method").equals("showSubGroupTree")))
/*      */                           {
/* 1867 */                             out.write("\n                     <option value=\"copyPaste\">");
/* 1868 */                             out.print(FormatUtil.getString("am.webclient.copypaste.headtitle"));
/* 1869 */                             out.write("</option>\n\n\t\t\t");
/*      */                           }
/* 1871 */                           out.write("\n                 </select>\n            ");
/* 1872 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 1873 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1877 */                       if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 1878 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                       }
/*      */                       
/* 1881 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 1882 */                       out.write("\n\t\t\t");
/*      */                       
/* 1884 */                       PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1885 */                       _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 1886 */                       _jspx_th_logic_005fpresent_005f13.setParent(_jspx_th_logic_005fpresent_005f11);
/*      */                       
/* 1888 */                       _jspx_th_logic_005fpresent_005f13.setRole("ENTERPRISEADMIN");
/* 1889 */                       int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 1890 */                       if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */                         for (;;) {
/* 1892 */                           out.write("\n\t\t\t");
/*      */                           
/* 1894 */                           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1895 */                           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 1896 */                           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fpresent_005f13);
/* 1897 */                           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 1898 */                           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                             for (;;) {
/* 1900 */                               out.write("\n\t\t\t");
/*      */                               
/* 1902 */                               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1903 */                               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 1904 */                               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                               
/* 1906 */                               _jspx_th_c_005fwhen_005f0.setTest("${param.method eq 'showMonitorGroupView'}");
/* 1907 */                               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 1908 */                               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                 for (;;) {
/* 1910 */                                   out.write("\n\t\t\t <select style=\"width:220px\"  data-placeholder=\"");
/* 1911 */                                   out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 1912 */                                   out.write("\" tabindex=\"5\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\" name=\"operation\">");
/* 1913 */                                   out.write("\n\t\t\t <option value=\"\">   </option>\n\t\t\t <option value=\"Manage\">");
/* 1914 */                                   out.print(FormatUtil.getString("Manage"));
/* 1915 */                                   out.write("</option>");
/* 1916 */                                   out.write("\n\t\t\t <option value=\"Unmanage\">");
/* 1917 */                                   out.print(FormatUtil.getString("UnManage"));
/* 1918 */                                   out.write("</option>");
/* 1919 */                                   out.write("\n\t\t\t <option value=\"unManageAndReset\">");
/* 1920 */                                   out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 1921 */                                   out.write("</option>");
/* 1922 */                                   out.write("\n\t\t\t </select>\n\t\t\t");
/* 1923 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 1924 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1928 */                               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 1929 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                               }
/*      */                               
/* 1932 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 1933 */                               out.write("\n\t\t\t");
/* 1934 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1935 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1939 */                           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1940 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                           }
/*      */                           
/* 1943 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1944 */                           out.write("\n\t\t\t");
/* 1945 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 1946 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1950 */                       if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 1951 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13); return;
/*      */                       }
/*      */                       
/* 1954 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 1955 */                       out.write("\n\n    ");
/*      */                       
/* 1957 */                       PresentTag _jspx_th_logic_005fpresent_005f14 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1958 */                       _jspx_th_logic_005fpresent_005f14.setPageContext(_jspx_page_context);
/* 1959 */                       _jspx_th_logic_005fpresent_005f14.setParent(_jspx_th_logic_005fpresent_005f11);
/*      */                       
/* 1961 */                       _jspx_th_logic_005fpresent_005f14.setRole("OPERATOR");
/* 1962 */                       int _jspx_eval_logic_005fpresent_005f14 = _jspx_th_logic_005fpresent_005f14.doStartTag();
/* 1963 */                       if (_jspx_eval_logic_005fpresent_005f14 != 0) {
/*      */                         for (;;) {
/* 1965 */                           out.write(" \n                ");
/*      */                           
/* 1967 */                           ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1968 */                           _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1969 */                           _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_logic_005fpresent_005f14);
/* 1970 */                           int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1971 */                           if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                             for (;;) {
/* 1973 */                               out.write("\n                    ");
/*      */                               
/* 1975 */                               WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1976 */                               _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1977 */                               _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                               
/* 1979 */                               _jspx_th_c_005fwhen_005f1.setTest("${allowEdit=='true' || allowUpdateIP=='true' || allowManage=='true' || allowReset=='true'}");
/* 1980 */                               int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1981 */                               if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                 for (;;) {
/* 1983 */                                   out.write("&nbsp<span class=\"filterby-txt input-uptime\">");
/* 1984 */                                   out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 1985 */                                   out.write(" :</span>\n                        <select style=\"width:220px\" data-placeholder=\"");
/* 1986 */                                   out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 1987 */                                   out.write("\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\"  name=\"operation\">\n                            <option VALUE=\"selectactions\" selected=true></option>\n                            ");
/*      */                                   
/* 1989 */                                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1990 */                                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1991 */                                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/* 1992 */                                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1993 */                                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                     for (;;) {
/* 1995 */                                       out.write("\n\t                   \t\t\t ");
/*      */                                       
/* 1997 */                                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1998 */                                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1999 */                                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                       
/* 2001 */                                       _jspx_th_c_005fwhen_005f2.setTest("${allowEdit=='true'}");
/* 2002 */                                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2003 */                                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                         for (;;) {
/* 2005 */                                           out.write("\n\t                            \t<option value=\"editDisplayNames\">");
/* 2006 */                                           out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 2007 */                                           out.write("</option>\n\t                             ");
/* 2008 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2009 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2013 */                                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2014 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                       }
/*      */                                       
/* 2017 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2018 */                                       out.write("\n                \t\t\t");
/* 2019 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2020 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2024 */                                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2025 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                   }
/*      */                                   
/* 2028 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2029 */                                   out.write("\n                \t\t\t");
/*      */                                   
/* 2031 */                                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2032 */                                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2033 */                                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fwhen_005f1);
/* 2034 */                                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2035 */                                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                     for (;;) {
/* 2037 */                                       out.write("\n\t                   \t\t\t ");
/*      */                                       
/* 2039 */                                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2040 */                                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2041 */                                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                       
/* 2043 */                                       _jspx_th_c_005fwhen_005f3.setTest("${allowUpdateIP=='true'}");
/* 2044 */                                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2045 */                                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                         for (;;) {
/* 2047 */                                           out.write("\n\t\t\t\t\t\t\t\t\t <option value=\"updateIP\">");
/* 2048 */                                           out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 2049 */                                           out.write("</option>\n\t                             ");
/* 2050 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2051 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2055 */                                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2056 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                       }
/*      */                                       
/* 2059 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2060 */                                       out.write("\n                \t\t\t");
/* 2061 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2062 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2066 */                                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2067 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                   }
/*      */                                   
/* 2070 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2071 */                                   out.write("\n                \t\t\t");
/*      */                                   
/* 2073 */                                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2074 */                                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2075 */                                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fwhen_005f1);
/* 2076 */                                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2077 */                                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                     for (;;) {
/* 2079 */                                       out.write("\n\t                   \t\t\t ");
/*      */                                       
/* 2081 */                                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2082 */                                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2083 */                                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                       
/* 2085 */                                       _jspx_th_c_005fwhen_005f4.setTest("${allowManage=='true' || allowReset=='true'}");
/* 2086 */                                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2087 */                                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                         for (;;) {
/* 2089 */                                           out.write("\n\t                            \t<option value=\"Manage\">");
/* 2090 */                                           out.print(FormatUtil.getString("Manage"));
/* 2091 */                                           out.write("</option>\n                            \t\t<option value=\"Unmanage\">");
/* 2092 */                                           out.print(FormatUtil.getString("UnManage"));
/* 2093 */                                           out.write("</option>\n\t                             ");
/* 2094 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2095 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2099 */                                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2100 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                       }
/*      */                                       
/* 2103 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2104 */                                       out.write("\n\t\t\t\t\t");
/* 2105 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2106 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2110 */                                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2111 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                                   }
/*      */                                   
/* 2114 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2115 */                                   out.write("\n                \t\t\t");
/*      */                                   
/* 2117 */                                   ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2118 */                                   _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2119 */                                   _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fwhen_005f1);
/* 2120 */                                   int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2121 */                                   if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                                     for (;;) {
/* 2123 */                                       out.write("\n\t\t\t\t\t");
/*      */                                       
/* 2125 */                                       WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2126 */                                       _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2127 */                                       _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                       
/* 2129 */                                       _jspx_th_c_005fwhen_005f5.setTest("${allowReset=='true'}");
/* 2130 */                                       int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2131 */                                       if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                         for (;;) {
/* 2133 */                                           out.write("\n\t\t\t\t\t <option value=\"unManageAndReset\">");
/* 2134 */                                           out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 2135 */                                           out.write("</option>");
/* 2136 */                                           out.write("\n\t\t\t\t\t");
/* 2137 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2138 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2142 */                                       if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2143 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                       }
/*      */                                       
/* 2146 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2147 */                                       out.write("\n                \t\t\t");
/* 2148 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2149 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2153 */                                   if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2154 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                                   }
/*      */                                   
/* 2157 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2158 */                                   out.write("\n                        </select>\n                    ");
/* 2159 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2160 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2164 */                               if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2165 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                               }
/*      */                               
/* 2168 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2169 */                               out.write("\n                ");
/* 2170 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2171 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2175 */                           if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2176 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                           }
/*      */                           
/* 2179 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2180 */                           out.write("\n            ");
/* 2181 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f14.doAfterBody();
/* 2182 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2186 */                       if (_jspx_th_logic_005fpresent_005f14.doEndTag() == 5) {
/* 2187 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14); return;
/*      */                       }
/*      */                       
/* 2190 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/* 2191 */                       out.write("\n          \n");
/* 2192 */                       out.write("\n    ");
/* 2193 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 2194 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2198 */                   if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 2199 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11); return;
/*      */                   }
/*      */                   
/* 2202 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/*      */                 }
/* 2204 */                 out.write(" \n \n  ");
/*      */                 
/* 2206 */                 IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2207 */                 _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2208 */                 _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2210 */                 _jspx_th_c_005fif_005f12.setTest("${forInventory != \"true\"}");
/* 2211 */                 int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2212 */                 if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                   for (;;) {
/* 2214 */                     out.write("\n    ");
/* 2215 */                     if ((!com.adventnet.appmanager.util.Constants.isIt360) && (tags.size() > 0)) {
/* 2216 */                       out.write("\n\t   &nbsp;\n       <b class=\"");
/* 2217 */                       out.print(filterstyle);
/* 2218 */                       out.write(" input-uptime\">");
/* 2219 */                       out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/* 2220 */                       out.write(" : </b>\n    ");
/*      */                     }
/* 2222 */                     out.write(10);
/* 2223 */                     out.write(32);
/* 2224 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2225 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2229 */                 if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2230 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                 }
/*      */                 
/* 2233 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2234 */                 out.write(10);
/* 2235 */                 out.write(32);
/* 2236 */                 out.write(32);
/* 2237 */                 if (_jspx_meth_c_005fif_005f13(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 2239 */                 out.write(10);
/*      */                 
/* 2241 */                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2242 */                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 2243 */                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2245 */                 _jspx_th_c_005fif_005f14.setTest("${forInventory != \"true\"}");
/* 2246 */                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 2247 */                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                   for (;;) {
/* 2249 */                     out.write("\n       ");
/* 2250 */                     if ((!com.adventnet.appmanager.util.Constants.isIt360) && (tags.size() > 0)) {
/* 2251 */                       out.write("\n          <select name=\"groupviewfilterby\"  style=\"width:150px;\" id=\"groupviewfilterby\" data-placeholder=\"");
/* 2252 */                       out.print(FormatUtil.getString("am.myfield.choosevalue.text"));
/* 2253 */                       out.write("\" class=\"chzn-select\"  tabindex=\"5\" onchange='loadURLType(this.options[this.selectedIndex].value,this.form,this,\"true\")'>\n\t\t\t\t\t\t\t<option selected=value=\"-\"> </option> ");
/* 2254 */                       out.write("\n\t\t\t\t\t\t\t");
/* 2255 */                       if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                         return;
/* 2257 */                       out.write("\n                            </select>\n\t\t\t\t\t\t\t&nbsp;\n        ");
/*      */                     }
/* 2259 */                     out.write("\n     ");
/* 2260 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 2261 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2265 */                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 2266 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                 }
/*      */                 
/* 2269 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 2270 */                 out.write("\n   ");
/* 2271 */                 if (_jspx_meth_c_005fif_005f15(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 2273 */                 out.write("\n\n\n<div id=\"customFieldValues\" style=\"display:none;\">\n </div>\n</td>\n <td  class=\"tableheadingbborder\" height=\"50\" align=\"right\" style=\"padding-right:20px;\" > \n <table border=\"0\">\n <tr>\n <td>\n\n \n <a href='/showresource.do?group=All&method=showMonitorGroupView&uncategorize=false'  class=\"");
/* 2274 */                 out.print(groupviewstyle);
/* 2275 */                 out.write(34);
/* 2276 */                 out.write(62);
/* 2277 */                 out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 2278 */                 out.write("</a>  \n  ");
/* 2279 */                 if (((request.getParameter("uncategorize") == null) || ("false".equals(request.getParameter("uncategorize")))) && (!"orphaned".equals(request.getParameter("retaintree")))) {
/* 2280 */                   out.write("  \n  <a id=\"treelink\" class=\"new-monitordiv-link\" style=\"font-size:10px\"already=");
/* 2281 */                   out.print(already);
/* 2282 */                   out.write(" href=\"javascript:void(0);\"  \n  onClick=\"javascript:disableTreeView();toggleallDivs('show','");
/* 2283 */                   out.print(isInventory);
/* 2284 */                   out.write("');\"><div id=\"showall\" style=\"display:");
/* 2285 */                   out.print(expandStyle);
/* 2286 */                   out.write(34);
/* 2287 */                   out.write(62);
/* 2288 */                   out.print(FormatUtil.getString("am.webclient.configurealert.expandall"));
/* 2289 */                   out.write("</div><div id=\"hideall\" style=\"display:");
/* 2290 */                   out.print(colpseStyle);
/* 2291 */                   out.write(34);
/* 2292 */                   out.write(62);
/* 2293 */                   out.print(FormatUtil.getString("am.webclient.configurealert.collapseall"));
/* 2294 */                   out.write("</div></a>\n");
/*      */                 }
/* 2296 */                 out.write(" \n  ");
/*      */                 
/* 2298 */                 if ((!isInventory) && (!com.adventnet.appmanager.util.Constants.isPrivilegedUser(request)))
/*      */                 {
/* 2300 */                   out.write("\n <span class=\"bodytext\" style=\"color:#595959;\"> &nbsp;|</span> \n  <a href='/showresource.do?group=All&method=showMonitorGroupView&uncategorize=true&parents=orphaned' title=\"");
/* 2301 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupview.unassociate.title"));
/* 2302 */                   out.write("\" class=\"");
/* 2303 */                   out.print(uncatstyle);
/* 2304 */                   out.write(34);
/* 2305 */                   out.write(62);
/* 2306 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupview.orphaned.monitors.text"));
/* 2307 */                   out.write("</a> \n ");
/*      */                 }
/* 2309 */                 out.write("\n\n</td>\n</tr>\n</table>\n  </td> \n\n</tr>\n\n</table>\n\n");
/*      */                 
/* 2311 */                 String tableClass = "";
/* 2312 */                 String emptyTableClass = "columnheading";
/* 2313 */                 if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                 {
/* 2315 */                   tableClass = "lrtbdarkborder";
/* 2316 */                   emptyTableClass = "emptyTableMsg";
/*      */                 }
/*      */                 
/* 2319 */                 out.write("\n<table width=\"100%\" id=\"allMonitorGroups\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\"  class=\"");
/* 2320 */                 out.print(tableClass);
/* 2321 */                 out.write("\" >\n");
/*      */                 
/* 2323 */                 IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2324 */                 _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2325 */                 _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2327 */                 _jspx_th_c_005fif_005f16.setTest("${ empty applications}");
/* 2328 */                 int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2329 */                 if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                   for (;;) {
/* 2331 */                     out.write(10);
/*      */                     
/* 2333 */                     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2334 */                     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2335 */                     _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fif_005f16);
/*      */                     
/* 2337 */                     _jspx_th_logic_005fempty_005f0.setName("applications");
/* 2338 */                     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2339 */                     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                       for (;;) {
/* 2341 */                         out.write("\n<tr class=\"whitegrayrightalign\" >\n\n         <td   height=\"50\" class=\"bodytext\"  colspan=\"5\" style='padding-left:40px' height=\"100\">\n\t\t<span class=\"bodytext\">");
/* 2342 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupview.nogroups.message.text"));
/* 2343 */                         out.write("</span>\n\t\t");
/*      */                         
/* 2345 */                         PresentTag _jspx_th_logic_005fpresent_005f15 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2346 */                         _jspx_th_logic_005fpresent_005f15.setPageContext(_jspx_page_context);
/* 2347 */                         _jspx_th_logic_005fpresent_005f15.setParent(_jspx_th_logic_005fempty_005f0);
/*      */                         
/* 2349 */                         _jspx_th_logic_005fpresent_005f15.setRole("ADMIN");
/* 2350 */                         int _jspx_eval_logic_005fpresent_005f15 = _jspx_th_logic_005fpresent_005f15.doStartTag();
/* 2351 */                         if (_jspx_eval_logic_005fpresent_005f15 != 0) {
/*      */                           for (;;) {
/* 2353 */                             out.write(10);
/* 2354 */                             out.write(9);
/* 2355 */                             out.write(9);
/* 2356 */                             if (EnterpriseUtil.isIt360MSPEdition()) {
/* 2357 */                               out.write("\n\t       \t\t<span class=\"bodytext\"> ");
/* 2358 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 2359 */                               out.write(" <a href=\"/it360/createBusinessServiceWiz.do\")%>");
/* 2360 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 2361 */                               out.write("</a></span>\n               ");
/*      */                             }
/* 2363 */                             out.write("\n\t\t    \n               ");
/* 2364 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f15.doAfterBody();
/* 2365 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2369 */                         if (_jspx_th_logic_005fpresent_005f15.doEndTag() == 5) {
/* 2370 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15); return;
/*      */                         }
/*      */                         
/* 2373 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/* 2374 */                         out.write("\n\t</td>\n        </tr>\n");
/* 2375 */                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2376 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2380 */                     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2381 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                     }
/*      */                     
/* 2384 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2385 */                     out.write(10);
/* 2386 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2387 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2391 */                 if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2392 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                 }
/*      */                 
/* 2395 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2396 */                 out.write("\n\n\n\n");
/*      */                 
/* 2398 */                 IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2399 */                 _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 2400 */                 _jspx_th_c_005fif_005f17.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2402 */                 _jspx_th_c_005fif_005f17.setTest("${not empty applications}");
/* 2403 */                 int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 2404 */                 if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                   for (;;) {
/* 2406 */                     out.write(10);
/*      */                     
/* 2408 */                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2409 */                     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2410 */                     _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fif_005f17);
/*      */                     
/* 2412 */                     _jspx_th_logic_005fnotEmpty_005f0.setName("applications");
/* 2413 */                     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2414 */                     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                       for (;;) {
/* 2416 */                         out.write("\n<tr class=\"bodytextbold\">\n\n<td class=\"columnheading\" valign=\"center\" width=\"3%\">&nbsp;</td>\n\n<td class=\"columnheading\" align=\"left\" height=\"24\" width=\"47%\">");
/* 2417 */                         if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 2418 */                           out.write(10);
/* 2419 */                           if (!EnterpriseUtil.isAdminServer()) {
/* 2420 */                             out.write("\n<input type='checkbox' name='headercheckbox1' onclick=\"javascript:fnSelectAll(this,'select');\" >");
/*      */                           }
/* 2422 */                           out.write(10);
/*      */                         }
/* 2424 */                         out.write(10);
/* 2425 */                         out.print(FormatUtil.getString("webclient.topo.operations.response.name"));
/* 2426 */                         out.write("\n</td>\n\n<td class=\"columnheading\" align=\"left\" height=\"24\" width=\"20%\">");
/* 2427 */                         out.print(FormatUtil.getString("Actions"));
/* 2428 */                         out.write("</td>\n\n<td class=\"columnheading\" align=\"center\"  width=\"8%\">");
/* 2429 */                         out.print(FormatUtil.getString("Availability"));
/* 2430 */                         out.write("</td>\n\n<td class=\"columnheading\" align=\"center\" width=\"7%\">");
/* 2431 */                         out.print(FormatUtil.getString("Health"));
/* 2432 */                         out.write("</td>\n\n<td class=\"columnheading\" align=\"center\"  width=\"15%\">&nbsp;</td>\n\n</tr>\n");
/* 2433 */                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2434 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2438 */                     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2439 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                     }
/*      */                     
/* 2442 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2443 */                     out.write(10);
/* 2444 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 2445 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2449 */                 if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 2450 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                 }
/*      */                 
/* 2453 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2454 */                 out.write(10);
/* 2455 */                 out.write(10);
/* 2456 */                 if ("true".equals(nouncategorize)) {
/* 2457 */                   out.write("\n<tr class=\"whitegrayrightalign\" >\n\n         <td   height=\"50\" class=\"bodytext\"  colspan=\"5\" style='padding-left:40px' height=\"100\">\n\t\t<span class=\"bodytext\">");
/* 2458 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupview.nouncategorize"));
/* 2459 */                   out.write("</span>\n\t\t\n\t</td>\n        </tr>\n");
/*      */                 }
/* 2461 */                 out.write(10);
/* 2462 */                 out.write(10);
/* 2463 */                 boolean checkRow = false;
/* 2464 */                 String mgresourceid = null;
/* 2465 */                 int mgviewlist = 0;
/* 2466 */                 int monitorlist = 0;
/* 2467 */                 boolean showeditLink = true;
/* 2468 */                 String haidininventory = "";
/* 2469 */                 if (request.getParameter("haid") != null)
/*      */                 {
/* 2471 */                   haidininventory = request.getParameter("haid");
/*      */                 }
/*      */                 
/* 2474 */                 out.write(" \n     ");
/*      */                 
/* 2476 */                 IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2477 */                 _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 2478 */                 _jspx_th_c_005fif_005f18.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2480 */                 _jspx_th_c_005fif_005f18.setTest("${not empty applications}");
/* 2481 */                 int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 2482 */                 if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                   for (;;) {
/* 2484 */                     out.write("\n     \n    ");
/*      */                     
/* 2486 */                     IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2487 */                     _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2488 */                     _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_c_005fif_005f18);
/*      */                     
/* 2490 */                     _jspx_th_logic_005fiterate_005f1.setName("applications");
/*      */                     
/* 2492 */                     _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                     
/* 2494 */                     _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/*      */                     
/* 2496 */                     _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/* 2497 */                     int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2498 */                     if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2499 */                       ArrayList row = null;
/* 2500 */                       Integer i = null;
/* 2501 */                       if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2502 */                         out = _jspx_page_context.pushBody();
/* 2503 */                         _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2504 */                         _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                       }
/* 2506 */                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2507 */                       i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                       for (;;) {
/* 2509 */                         out.write("\n    ");
/*      */                         
/* 2511 */                         mgviewlist = i.intValue();
/* 2512 */                         showeditLink = true;
/* 2513 */                         healthid = "18";
/* 2514 */                         availid = "17";
/* 2515 */                         String rowID = "";
/* 2516 */                         String MGType = row.get(2) != null ? (String)row.get(2) : "";
/* 2517 */                         String haiGroupType = "0";
/* 2518 */                         if (row.size() - 1 == haiGroupTypeIndex)
/*      */                         {
/* 2520 */                           haiGroupType = (String)row.get(haiGroupTypeIndex);
/*      */                         }
/* 2522 */                         String resourceType = row.get(resourceTypeindex) != null ? (String)row.get(resourceTypeindex) : "";
/* 2523 */                         img = "";
/* 2524 */                         if (!resourceType.equals("HAI"))
/*      */                         {
/* 2526 */                           pageContext.setAttribute("notMG", "true");
/* 2527 */                           minusStyle = "none";
/* 2528 */                           img = "<img src='" + row.get(3) + "' title='" + row.get(4) + "' align='absmiddle' border='0'/>&nbsp;";
/* 2529 */                           healthid = (String)healthkeys.get(resourceType);
/* 2530 */                           availid = (String)availabilitykeys.get(resourceType);
/*      */                         }
/* 2532 */                         if (isInventory)
/*      */                         {
/* 2534 */                           MGType = "0";
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/* 2539 */                         String MGresourceids = (String)row.get(6);
/* 2540 */                         if ((MGType.equals("0")) || ((EnterpriseUtil.isIt360MSPEdition()) && (!"orphaned".equals(MGresourceids)) && (com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.isBusinessServiceGrpId(MGresourceids))))
/*      */                         {
/* 2542 */                           String actionstatus = (String)row.get(actionIndex);
/* 2543 */                           String actionimage = "/images/alarm-icon.png";
/* 2544 */                           String actionmessage = FormatUtil.getString("Actions Enabled");
/* 2545 */                           if ((actionstatus != null) && (actionstatus.equals("0")))
/*      */                           {
/* 2547 */                             actionimage = "/images/icon_actions_disabled.gif";
/* 2548 */                             actionmessage = FormatUtil.getString("Actions Disabled");
/*      */                           }
/* 2550 */                           String restitle = row.get(unmanagestatusindex) != null ? (String)row.get(displayNameindex) + "-UnManaged" : (String)row.get(displayNameindex);
/* 2551 */                           String status = row.get(unmanagestatusindex) != null ? "disabledtext" : "monitorgp-links";
/* 2552 */                           String resIdTOCheck = "-1";
/* 2553 */                           int residint = 1;
/*      */                           try
/*      */                           {
/* 2556 */                             resIdTOCheck = (String)row.get(residIndex);
/* 2557 */                             residint = Integer.parseInt((String)row.get(residIndex));
/*      */                           }
/*      */                           catch (Exception e) {}
/*      */                           
/* 2561 */                           if (!resIdTOCheck.equals("orphaned"))
/*      */                           {
/*      */ 
/* 2564 */                             out.write("\n\n\t <tr  id=\"#monitor\" toplevel='true' isgroup=\"true\" groupid=\"");
/* 2565 */                             out.print(resIdTOCheck);
/* 2566 */                             out.write("\"  class=\" monitorgpHeader\" onmouseover=\"this.className='monitorgpHeaderHover'\" onmouseout=\"this.className='monitorgpHeader'\" > \n\t\t  ");
/*      */                             
/* 2568 */                             if (EnterpriseUtil.isAdminServer())
/*      */                             {
/* 2570 */                               String dspName = (String)row.get(1);
/* 2571 */                               String groupName = row.get(displayNameindex) + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort((String)row.get(residIndex));
/*      */                               
/* 2573 */                               if (!checkRow) {
/* 2574 */                                 mgresourceid = resIdTOCheck;
/* 2575 */                                 rowID = "expandRow";
/* 2576 */                                 checkRow = true;
/*      */                               }
/*      */                               
/* 2579 */                               out.write("\n\t <td  class=\"whitegrayrightalign\"  align=\"center\"  width=\"3%\" style=\"padding-right:10px\">\n\t\t");
/*      */                               
/* 2581 */                               IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2582 */                               _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 2583 */                               _jspx_th_c_005fif_005f19.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                               
/* 2585 */                               _jspx_th_c_005fif_005f19.setTest("${notMG!=\"true\"}");
/* 2586 */                               int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 2587 */                               if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                                 for (;;) {
/* 2589 */                                   out.write("\n\t\t&nbsp;&nbsp;&nbsp;\n\t\t\t<a  class =\"expandlink\" isopen=\"");
/* 2590 */                                   out.print(isopen);
/* 2591 */                                   out.write("\" resid=\"");
/* 2592 */                                   out.print(resIdTOCheck);
/* 2593 */                                   out.write("\" id=\"");
/* 2594 */                                   out.print(rowID);
/* 2595 */                                   out.write("\" href=\"javascript:void(0);\" onclick=\"getDynamicCall('/showresource.do?method=showMonitorGroupViewIndividual&parents=");
/* 2596 */                                   out.print(resIdTOCheck);
/* 2597 */                                   out.write("&lindx=1&pageno=1',this,'true'), toggleChildMos('#monitor");
/* 2598 */                                   out.print(resIdTOCheck);
/* 2599 */                                   out.write("',this),toggleTreeImage('");
/* 2600 */                                   out.print(resIdTOCheck);
/* 2601 */                                   out.write("');\" > \t\t\t\n\t\t\t<div id=\"monitorShow");
/* 2602 */                                   out.print(resIdTOCheck);
/* 2603 */                                   out.write("\" style=\"display:");
/* 2604 */                                   out.print(plusStyle);
/* 2605 */                                   out.write(";\"><img src=\"/images/icon_plus.gif\" border=\"0\" ></div><div id=\"monitorHide");
/* 2606 */                                   out.print(resIdTOCheck);
/* 2607 */                                   out.write("\" style=\"display:");
/* 2608 */                                   out.print(minusStyle);
/* 2609 */                                   out.write(";\"><img src=\"/images/icon_minus.gif\" border=\"0\" ></div> </a>\n\t\t");
/* 2610 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 2611 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2615 */                               if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 2616 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                               }
/*      */                               
/* 2619 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 2620 */                               out.write(10);
/* 2621 */                               out.write(9);
/* 2622 */                               out.write(9);
/* 2623 */                               if (_jspx_meth_c_005fif_005f20(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                 return;
/* 2625 */                               out.write("\n\t</td>\n\n\t <td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"47%\" title=\"");
/* 2626 */                               out.print(groupName);
/* 2627 */                               out.write("\"  style=\"padding-left: 0px;\">\n\n\t \n\t ");
/*      */                               
/* 2629 */                               IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2630 */                               _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 2631 */                               _jspx_th_c_005fif_005f21.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                               
/* 2633 */                               _jspx_th_c_005fif_005f21.setTest("${notMG!=\"true\"}");
/* 2634 */                               int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 2635 */                               if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                 for (;;) {
/* 2637 */                                   out.write("\t \n\t   <input type=\"checkbox\" name=\"select\" value=\"");
/* 2638 */                                   out.print(resIdTOCheck);
/* 2639 */                                   out.write("\" id=\"");
/* 2640 */                                   out.print(resIdTOCheck);
/* 2641 */                                   out.write("\" grpresid=\"");
/* 2642 */                                   out.print(resIdTOCheck);
/* 2643 */                                   out.write("\" onclick=\"selectAllChildCKbs('");
/* 2644 */                                   out.print(resIdTOCheck);
/* 2645 */                                   out.write("',this,this.form);\"><a href=\"/showapplication.do?haid=");
/* 2646 */                                   out.print(row.get(residIndex));
/* 2647 */                                   out.write("&method=showApplication\" class=\"");
/* 2648 */                                   out.print(status);
/* 2649 */                                   out.write(34);
/* 2650 */                                   out.write(62);
/* 2651 */                                   out.print(getTrimmedText(groupName, 35));
/* 2652 */                                   out.write("</a></td>");
/* 2653 */                                   out.write("\n\t   ");
/* 2654 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 2655 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2659 */                               if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 2660 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                               }
/*      */                               
/* 2663 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 2664 */                               out.write("\n\t   ");
/*      */                               
/* 2666 */                               IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2667 */                               _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 2668 */                               _jspx_th_c_005fif_005f22.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                               
/* 2670 */                               _jspx_th_c_005fif_005f22.setTest("${notMG==\"true\"}");
/* 2671 */                               int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 2672 */                               if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                 for (;;) {
/* 2674 */                                   out.write("\n\t   ");
/* 2675 */                                   out.print(img);
/* 2676 */                                   out.write(10);
/* 2677 */                                   out.write(9);
/* 2678 */                                   out.write(9);
/* 2679 */                                   if ((site24x7List != null) && (site24x7List.containsKey(row.get(residIndex)))) {
/* 2680 */                                     out.write("\n <a href=\"javascript:MM_openBrWindow('");
/* 2681 */                                     out.print(URLEncoder.encode((String)site24x7List.get(row.get(residIndex))));
/* 2682 */                                     out.write("','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2683 */                                     out.print(status);
/* 2684 */                                     out.write(34);
/* 2685 */                                     out.write(62);
/* 2686 */                                     out.print(row.get(1));
/* 2687 */                                     out.write("</a>\n");
/*      */                                   }
/* 2689 */                                   else if ((extDeviceMap != null) && (extDeviceMap.get(row.get(residIndex)) != null))
/*      */                                   {
/* 2691 */                                     String link1 = (String)extDeviceMap.get(row.get(residIndex));
/* 2692 */                                     showeditLink = false;
/* 2693 */                                     if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                     {
/* 2695 */                                       out.write("\n\t\t\t   <a href=");
/* 2696 */                                       out.print(link1);
/* 2697 */                                       out.write("  class=\"");
/* 2698 */                                       out.print(status);
/* 2699 */                                       out.write("\">  ");
/* 2700 */                                       out.print(getTrimmedText((String)row.get(1), 45));
/* 2701 */                                       out.write("</a>;\t\t \n\t\t\t  ");
/*      */                                     }
/*      */                                     else {
/* 2704 */                                       out.write("\n\t\t\t  <a href=\"javascript:MM_openBrWindow('");
/* 2705 */                                       out.print(link1);
/* 2706 */                                       out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2707 */                                       out.print(status);
/* 2708 */                                       out.write(34);
/* 2709 */                                       out.write(62);
/* 2710 */                                       out.print(getTrimmedText((String)row.get(1), 45));
/* 2711 */                                       out.write("</a>\n\t\t\t");
/*      */                                     }
/*      */                                     
/* 2714 */                                     out.write(10);
/*      */                                   }
/* 2716 */                                   else if (ChildMOHandler.isChildMonitorTypeSupportedForMG((String)row.get(2)))
/*      */                                   {
/*      */ 
/* 2719 */                                     out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('/showapplication.do?method=showChildApplicationDetail&resId=");
/* 2720 */                                     out.print(row.get(0));
/* 2721 */                                     out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2722 */                                     out.print(status);
/* 2723 */                                     out.write(34);
/* 2724 */                                     out.write(62);
/* 2725 */                                     out.print(row.get(1));
/* 2726 */                                     out.write("</a>\n");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2731 */                                     out.write("\n\t\t<a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2732 */                                     out.print(row.get(residIndex));
/* 2733 */                                     out.write("&haid=");
/* 2734 */                                     out.print(haidininventory);
/* 2735 */                                     out.write("\"\n\t  class=\"");
/* 2736 */                                     out.print(status);
/* 2737 */                                     out.write(34);
/* 2738 */                                     out.write(62);
/* 2739 */                                     out.print(row.get(1));
/* 2740 */                                     out.write("</a>\t\t\n\t\t");
/*      */                                   }
/*      */                                   
/* 2743 */                                   out.write(10);
/* 2744 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 2745 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2749 */                               if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 2750 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                               }
/*      */                               
/* 2753 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 2754 */                               out.write("\n\t\n\t  </td>\n\t  </td>\n      ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/* 2759 */                               if (!checkRow) {
/* 2760 */                                 mgresourceid = resIdTOCheck;
/* 2761 */                                 rowID = "expandRow";
/* 2762 */                                 checkRow = true;
/*      */                               }
/*      */                               
/* 2765 */                               out.write("\n\t <td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"3%\" style=\"padding-right:10px\">\n\t\t");
/*      */                               
/* 2767 */                               IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2768 */                               _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 2769 */                               _jspx_th_c_005fif_005f23.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                               
/* 2771 */                               _jspx_th_c_005fif_005f23.setTest("${notMG!=\"true\"}");
/* 2772 */                               int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 2773 */                               if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                 for (;;) {
/* 2775 */                                   out.write("\n\t\t&nbsp;&nbsp;\n\t\t<a href=\"javascript:void(0);\" id=\"");
/* 2776 */                                   out.print(rowID);
/* 2777 */                                   out.write("\" class=\"expandlink\" isopen=\"");
/* 2778 */                                   out.print(isopen);
/* 2779 */                                   out.write("\" resid=\"");
/* 2780 */                                   out.print(resIdTOCheck);
/* 2781 */                                   out.write("\" onclick=\"getDynamicCall('/showresource.do?method=showMonitorGroupViewIndividual&parents=");
/* 2782 */                                   out.print(resIdTOCheck);
/* 2783 */                                   out.write("&lindx=1&pageno=1',this,'true'), toggleChildMos('#monitor");
/* 2784 */                                   out.print(resIdTOCheck);
/* 2785 */                                   out.write("',this),toggleTreeImage('");
/* 2786 */                                   out.print(resIdTOCheck);
/* 2787 */                                   out.write("');\">\n\t\t\n\t\t<div id=\"monitorShow");
/* 2788 */                                   out.print(resIdTOCheck);
/* 2789 */                                   out.write("\" style=\"display:");
/* 2790 */                                   out.print(plusStyle);
/* 2791 */                                   out.write(";\"><img src=\"/images/icon_plus.gif\"   border=\"0\" ></div><div id=\"monitorHide");
/* 2792 */                                   out.print(resIdTOCheck);
/* 2793 */                                   out.write("\" style=\"display:");
/* 2794 */                                   out.print(minusStyle);
/* 2795 */                                   out.write(";\"><img src=\"/images/icon_minus.gif\" border=\"0\" ></div> </a> \n        ");
/* 2796 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 2797 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2801 */                               if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 2802 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                               }
/*      */                               
/* 2805 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 2806 */                               out.write("\n\n\t ");
/* 2807 */                               if (_jspx_meth_c_005fif_005f24(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                                 return;
/* 2809 */                               out.write("\n\t</td>\n\n\t <td  class=\"whitegrayrightalign\"  align=\"left\"  width=\"47%\" title=\"");
/* 2810 */                               out.print(restitle);
/* 2811 */                               out.write("\">\n\t \t  ");
/*      */                               
/* 2813 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f12 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2814 */                               _jspx_th_logic_005fnotPresent_005f12.setPageContext(_jspx_page_context);
/* 2815 */                               _jspx_th_logic_005fnotPresent_005f12.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                               
/* 2817 */                               _jspx_th_logic_005fnotPresent_005f12.setRole("OPERATOR");
/* 2818 */                               int _jspx_eval_logic_005fnotPresent_005f12 = _jspx_th_logic_005fnotPresent_005f12.doStartTag();
/* 2819 */                               if (_jspx_eval_logic_005fnotPresent_005f12 != 0) {
/*      */                                 for (;;) {
/* 2821 */                                   out.write("\n\t\t   <input type=\"checkbox\" name=\"select\" value=\"");
/* 2822 */                                   out.print(resIdTOCheck);
/* 2823 */                                   out.write("\" id=\"");
/* 2824 */                                   out.print(resIdTOCheck);
/* 2825 */                                   out.write("\" grpresid=\"");
/* 2826 */                                   out.print(resIdTOCheck);
/* 2827 */                                   out.write("\" onclick=\"selectAllChildCKbs('");
/* 2828 */                                   out.print(resIdTOCheck);
/* 2829 */                                   out.write("',this,this.form);\">\n\t\t   ");
/* 2830 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f12.doAfterBody();
/* 2831 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2835 */                               if (_jspx_th_logic_005fnotPresent_005f12.doEndTag() == 5) {
/* 2836 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f12); return;
/*      */                               }
/*      */                               
/* 2839 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f12);
/* 2840 */                               out.write(10);
/* 2841 */                               out.write(9);
/* 2842 */                               out.write(9);
/* 2843 */                               out.print(img);
/* 2844 */                               out.write(10);
/* 2845 */                               out.write(9);
/* 2846 */                               out.write(9);
/* 2847 */                               if ((site24x7List != null) && (site24x7List.containsKey(row.get(residIndex)))) {
/* 2848 */                                 out.write("\n\t\t    <a href=\"javascript:MM_openBrWindow('");
/* 2849 */                                 out.print(URLEncoder.encode((String)site24x7List.get(row.get(residIndex))));
/* 2850 */                                 out.write("','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2851 */                                 out.print(status);
/* 2852 */                                 out.write(34);
/* 2853 */                                 out.write(62);
/* 2854 */                                 out.print(row.get(1));
/* 2855 */                                 out.write("</a>\n\t\t");
/*      */                               }
/* 2857 */                               else if ((extDeviceMap != null) && (extDeviceMap.get(row.get(residIndex)) != null))
/*      */                               {
/* 2859 */                                 String link1 = (String)extDeviceMap.get(row.get(residIndex));
/* 2860 */                                 showeditLink = false;
/* 2861 */                                 if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                 {
/* 2863 */                                   out.write("\n\t\t\t  <a href=");
/* 2864 */                                   out.print(link1);
/* 2865 */                                   out.write("  class=\"");
/* 2866 */                                   out.print(status);
/* 2867 */                                   out.write("\">  ");
/* 2868 */                                   out.print(getTrimmedText((String)row.get(1), 45));
/* 2869 */                                   out.write("</a>\";\n\t\t ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2873 */                                   out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('");
/* 2874 */                                   out.print(link1);
/* 2875 */                                   out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2876 */                                   out.print(status);
/* 2877 */                                   out.write(34);
/* 2878 */                                   out.write(62);
/* 2879 */                                   out.print(getTrimmedText((String)row.get(1), 45));
/* 2880 */                                   out.write("</a>\n\t\t ");
/*      */                                 }
/*      */                               }
/* 2883 */                               else if (ChildMOHandler.isChildMonitorTypeSupportedForMG((String)row.get(2)))
/*      */                               {
/*      */ 
/* 2886 */                                 out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('/showapplication.do?method=showChildApplicationDetail&resId=");
/* 2887 */                                 out.print(row.get(0));
/* 2888 */                                 out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 2889 */                                 out.print(status);
/* 2890 */                                 out.write(34);
/* 2891 */                                 out.write(62);
/* 2892 */                                 out.print(row.get(1));
/* 2893 */                                 out.write("</a>\n\t\t");
/*      */                               }
/*      */                               else {
/* 2896 */                                 out.write(" \n\t\t\t<a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2897 */                                 out.print(row.get(residIndex));
/* 2898 */                                 out.write("&haid=");
/* 2899 */                                 out.print(haidininventory);
/* 2900 */                                 out.write("\" class=\"");
/* 2901 */                                 out.print(status);
/* 2902 */                                 out.write(34);
/* 2903 */                                 out.write(62);
/* 2904 */                                 out.print(row.get(1));
/* 2905 */                                 out.write("</a>\n\t\t");
/*      */                               }
/* 2907 */                               out.write("\n\t\t\t\n\t\t\t\n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 2911 */                             out.write(10);
/* 2912 */                             out.write(32);
/*      */                             
/* 2914 */                             String editlink = "/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + resIdTOCheck;
/*      */                             
/* 2916 */                             out.write(10);
/* 2917 */                             out.write(9);
/* 2918 */                             out.write(9);
/*      */                             
/* 2920 */                             IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2921 */                             _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 2922 */                             _jspx_th_c_005fif_005f25.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 2924 */                             _jspx_th_c_005fif_005f25.setTest("${forInventory==\"true\"}");
/* 2925 */                             int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 2926 */                             if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                               for (;;) {
/* 2928 */                                 out.write("\n\t\t\t");
/*      */                                 
/* 2930 */                                 editlink = "/showapplication.do?method=editApplication&haid=" + resIdTOCheck;
/*      */                                 
/* 2932 */                                 out.write(10);
/* 2933 */                                 out.write(9);
/* 2934 */                                 out.write(9);
/* 2935 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 2936 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2940 */                             if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 2941 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                             }
/*      */                             
/* 2944 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 2945 */                             out.write(10);
/* 2946 */                             out.write(9);
/* 2947 */                             out.write(9);
/*      */                             
/* 2949 */                             IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2950 */                             _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 2951 */                             _jspx_th_c_005fif_005f26.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 2953 */                             _jspx_th_c_005fif_005f26.setTest("${notMG==\"true\"}");
/* 2954 */                             int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 2955 */                             if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                               for (;;) {
/* 2957 */                                 out.write(10);
/* 2958 */                                 out.write(9);
/* 2959 */                                 out.write(9);
/* 2960 */                                 editlink = "/showresource.do?method=showResourceForResourceID&editPage=true&resourceid=" + resIdTOCheck;
/*      */                                 
/* 2962 */                                 out.write("\n                ");
/* 2963 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 2964 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2968 */                             if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 2969 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                             }
/*      */                             
/* 2972 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 2973 */                             out.write("\n\t    ");
/*      */                             
/* 2975 */                             if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                             {
/* 2977 */                               editlink = "/it360/createBS.do?method=editApplication&haid=" + resIdTOCheck;
/*      */                             }
/*      */                             
/* 2980 */                             out.write("\n\t\t<td  class=\"whitegrayrightalign\" width=\"15%\"  align=\"left\" ><table><tr class='whitegrayrightalign'><td>\n               <div id=\"mgaction\" class=\"actionborder\">\n\t\t");
/*      */                             
/* 2982 */                             PresentTag _jspx_th_logic_005fpresent_005f16 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2983 */                             _jspx_th_logic_005fpresent_005f16.setPageContext(_jspx_page_context);
/* 2984 */                             _jspx_th_logic_005fpresent_005f16.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 2986 */                             _jspx_th_logic_005fpresent_005f16.setRole("ADMIN");
/* 2987 */                             int _jspx_eval_logic_005fpresent_005f16 = _jspx_th_logic_005fpresent_005f16.doStartTag();
/* 2988 */                             if (_jspx_eval_logic_005fpresent_005f16 != 0) {
/*      */                               for (;;) {
/* 2990 */                                 out.write(10);
/* 2991 */                                 out.write(9);
/* 2992 */                                 out.write(9);
/*      */                                 
/* 2994 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f13 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2995 */                                 _jspx_th_logic_005fnotPresent_005f13.setPageContext(_jspx_page_context);
/* 2996 */                                 _jspx_th_logic_005fnotPresent_005f13.setParent(_jspx_th_logic_005fpresent_005f16);
/*      */                                 
/* 2998 */                                 _jspx_th_logic_005fnotPresent_005f13.setRole("DEMO,ENTERPRISEADMIN");
/* 2999 */                                 int _jspx_eval_logic_005fnotPresent_005f13 = _jspx_th_logic_005fnotPresent_005f13.doStartTag();
/* 3000 */                                 if (_jspx_eval_logic_005fnotPresent_005f13 != 0) {
/*      */                                   for (;;) {
/* 3002 */                                     out.write("\n\t\t\n\t\t");
/* 3003 */                                     if ((showeditLink) && (site24x7List != null) && (!site24x7List.containsKey(row.get(residIndex)))) {
/* 3004 */                                       out.write("\n\t\t    ");
/*      */                                       
/* 3006 */                                       AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3007 */                                       _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 3008 */                                       _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fnotPresent_005f13);
/*      */                                       
/* 3010 */                                       _jspx_th_am_005fadminlink_005f0.setHref(editlink);
/*      */                                       
/* 3012 */                                       _jspx_th_am_005fadminlink_005f0.setEnableClass("monitorgp-links");
/* 3013 */                                       int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 3014 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 3015 */                                         if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3016 */                                           out = _jspx_page_context.pushBody();
/* 3017 */                                           _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 3018 */                                           _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3021 */                                           out.write("<img src=\"/images/icon_edit.gif\" align=\"center\" border=\"0\"  title=\"");
/* 3022 */                                           out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 3023 */                                           out.write(34);
/* 3024 */                                           out.write(47);
/* 3025 */                                           out.write(62);
/* 3026 */                                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 3027 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3030 */                                         if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3031 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3034 */                                       if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 3035 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                       }
/*      */                                       
/* 3038 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 3039 */                                       out.write(32);
/* 3040 */                                       out.write(10);
/* 3041 */                                       out.write(9);
/* 3042 */                                       out.write(9);
/*      */                                     }
/* 3044 */                                     out.write("\t\t   \n\t\t   ");
/* 3045 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f13.doAfterBody();
/* 3046 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3050 */                                 if (_jspx_th_logic_005fnotPresent_005f13.doEndTag() == 5) {
/* 3051 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f13); return;
/*      */                                 }
/*      */                                 
/* 3054 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f13);
/* 3055 */                                 out.write(10);
/* 3056 */                                 out.write(9);
/* 3057 */                                 out.write(9);
/* 3058 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f16.doAfterBody();
/* 3059 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3063 */                             if (_jspx_th_logic_005fpresent_005f16.doEndTag() == 5) {
/* 3064 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16); return;
/*      */                             }
/*      */                             
/* 3067 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/* 3068 */                             out.write("\n\t\t&nbsp;&nbsp;\n\t\t");
/*      */                             
/* 3070 */                             PresentTag _jspx_th_logic_005fpresent_005f17 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3071 */                             _jspx_th_logic_005fpresent_005f17.setPageContext(_jspx_page_context);
/* 3072 */                             _jspx_th_logic_005fpresent_005f17.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 3074 */                             _jspx_th_logic_005fpresent_005f17.setRole("ADMIN");
/* 3075 */                             int _jspx_eval_logic_005fpresent_005f17 = _jspx_th_logic_005fpresent_005f17.doStartTag();
/* 3076 */                             if (_jspx_eval_logic_005fpresent_005f17 != 0) {
/*      */                               for (;;) {
/* 3078 */                                 out.write(10);
/* 3079 */                                 out.write(9);
/* 3080 */                                 out.write(9);
/*      */                                 
/* 3082 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f14 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3083 */                                 _jspx_th_logic_005fnotPresent_005f14.setPageContext(_jspx_page_context);
/* 3084 */                                 _jspx_th_logic_005fnotPresent_005f14.setParent(_jspx_th_logic_005fpresent_005f17);
/*      */                                 
/* 3086 */                                 _jspx_th_logic_005fnotPresent_005f14.setRole("DEMO,ENTERPRISEADMIN");
/* 3087 */                                 int _jspx_eval_logic_005fnotPresent_005f14 = _jspx_th_logic_005fnotPresent_005f14.doStartTag();
/* 3088 */                                 if (_jspx_eval_logic_005fnotPresent_005f14 != 0) {
/*      */                                   for (;;) {
/* 3090 */                                     out.write("\n\t\t    ");
/*      */                                     
/* 3092 */                                     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3093 */                                     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 3094 */                                     _jspx_th_c_005fif_005f27.setParent(_jspx_th_logic_005fnotPresent_005f14);
/*      */                                     
/* 3096 */                                     _jspx_th_c_005fif_005f27.setTest("${notMG!=\"true\"}");
/* 3097 */                                     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 3098 */                                     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */                                       for (;;) {
/* 3100 */                                         out.write("\n                     <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 3101 */                                         out.print(resIdTOCheck);
/* 3102 */                                         out.write("\"> <img src=\" /images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"");
/* 3103 */                                         out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 3104 */                                         out.write("\"/></a>\n\t\t     ");
/* 3105 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 3106 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3110 */                                     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 3111 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27); return;
/*      */                                     }
/*      */                                     
/* 3114 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 3115 */                                     out.write("\n\t\t    ");
/*      */                                     
/* 3117 */                                     IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3118 */                                     _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 3119 */                                     _jspx_th_c_005fif_005f28.setParent(_jspx_th_logic_005fnotPresent_005f14);
/*      */                                     
/* 3121 */                                     _jspx_th_c_005fif_005f28.setTest("${notMG==\"true\"}");
/* 3122 */                                     int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 3123 */                                     if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */                                       for (;;) {
/* 3125 */                                         out.write("\n\t\t      <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=");
/* 3126 */                                         out.print(resIdTOCheck);
/* 3127 */                                         out.write("\"> <img src=\" /images/icon_associateaction.gif\" border=\"0\" align=\"center\" title=\"");
/* 3128 */                                         out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 3129 */                                         out.write("\"/></a>\n                     ");
/* 3130 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 3131 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3135 */                                     if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 3136 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28); return;
/*      */                                     }
/*      */                                     
/* 3139 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 3140 */                                     out.write("\n                     ");
/* 3141 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f14.doAfterBody();
/* 3142 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3146 */                                 if (_jspx_th_logic_005fnotPresent_005f14.doEndTag() == 5) {
/* 3147 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f14); return;
/*      */                                 }
/*      */                                 
/* 3150 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f14);
/* 3151 */                                 out.write(10);
/* 3152 */                                 out.write(9);
/* 3153 */                                 out.write(9);
/* 3154 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f17.doAfterBody();
/* 3155 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3159 */                             if (_jspx_th_logic_005fpresent_005f17.doEndTag() == 5) {
/* 3160 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17); return;
/*      */                             }
/*      */                             
/* 3163 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17);
/* 3164 */                             out.write("\n\t\t&nbsp;&nbsp;\n\t\t");
/*      */                             
/* 3166 */                             PresentTag _jspx_th_logic_005fpresent_005f18 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3167 */                             _jspx_th_logic_005fpresent_005f18.setPageContext(_jspx_page_context);
/* 3168 */                             _jspx_th_logic_005fpresent_005f18.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 3170 */                             _jspx_th_logic_005fpresent_005f18.setRole("ADMIN");
/* 3171 */                             int _jspx_eval_logic_005fpresent_005f18 = _jspx_th_logic_005fpresent_005f18.doStartTag();
/* 3172 */                             if (_jspx_eval_logic_005fpresent_005f18 != 0) {
/*      */                               for (;;) {
/* 3174 */                                 out.write("\n\t\t ");
/*      */                                 
/* 3176 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f15 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3177 */                                 _jspx_th_logic_005fnotPresent_005f15.setPageContext(_jspx_page_context);
/* 3178 */                                 _jspx_th_logic_005fnotPresent_005f15.setParent(_jspx_th_logic_005fpresent_005f18);
/*      */                                 
/* 3180 */                                 _jspx_th_logic_005fnotPresent_005f15.setRole("DEMO,ENTERPRISEADMIN");
/* 3181 */                                 int _jspx_eval_logic_005fnotPresent_005f15 = _jspx_th_logic_005fnotPresent_005f15.doStartTag();
/* 3182 */                                 if (_jspx_eval_logic_005fnotPresent_005f15 != 0) {
/*      */                                   for (;;) {
/* 3184 */                                     out.write("\n\t\t    ");
/*      */                                     
/* 3186 */                                     IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3187 */                                     _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 3188 */                                     _jspx_th_c_005fif_005f29.setParent(_jspx_th_logic_005fnotPresent_005f15);
/*      */                                     
/* 3190 */                                     _jspx_th_c_005fif_005f29.setTest("${forInventory==\"true\"}");
/* 3191 */                                     int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 3192 */                                     if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */                                       for (;;) {
/* 3194 */                                         out.write("\n                     <a href=\"javascript: removeMonitorFromGroup ('");
/* 3195 */                                         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f29, _jspx_page_context))
/*      */                                           return;
/* 3197 */                                         out.write(39);
/* 3198 */                                         out.write(44);
/* 3199 */                                         out.write(39);
/* 3200 */                                         out.print(resIdTOCheck);
/* 3201 */                                         out.write(39);
/* 3202 */                                         out.write(44);
/* 3203 */                                         out.write(39);
/* 3204 */                                         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f29, _jspx_page_context))
/*      */                                           return;
/* 3206 */                                         out.write("');\"> <img src=\"/images/icon_removefromgroup.gif\" align=\"center\" border=\"0\" title=\"");
/* 3207 */                                         out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 3208 */                                         out.write("\"/></a>\n\t\t\t&nbsp;&nbsp;\n                    ");
/* 3209 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 3210 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3214 */                                     if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 3215 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29); return;
/*      */                                     }
/*      */                                     
/* 3218 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 3219 */                                     out.write("\n\t\t ");
/* 3220 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f15.doAfterBody();
/* 3221 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3225 */                                 if (_jspx_th_logic_005fnotPresent_005f15.doEndTag() == 5) {
/* 3226 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f15); return;
/*      */                                 }
/*      */                                 
/* 3229 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f15);
/* 3230 */                                 out.write("\n                ");
/* 3231 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f18.doAfterBody();
/* 3232 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3236 */                             if (_jspx_th_logic_005fpresent_005f18.doEndTag() == 5) {
/* 3237 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18); return;
/*      */                             }
/*      */                             
/* 3240 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18);
/* 3241 */                             out.write("\n\t\n\t\t");
/*      */                             
/* 3243 */                             PresentTag _jspx_th_logic_005fpresent_005f19 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3244 */                             _jspx_th_logic_005fpresent_005f19.setPageContext(_jspx_page_context);
/* 3245 */                             _jspx_th_logic_005fpresent_005f19.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 3247 */                             _jspx_th_logic_005fpresent_005f19.setRole("ADMIN");
/* 3248 */                             int _jspx_eval_logic_005fpresent_005f19 = _jspx_th_logic_005fpresent_005f19.doStartTag();
/* 3249 */                             if (_jspx_eval_logic_005fpresent_005f19 != 0) {
/*      */                               for (;;) {
/* 3251 */                                 out.write(10);
/* 3252 */                                 out.write(9);
/* 3253 */                                 out.write(9);
/*      */                                 
/* 3255 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f16 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3256 */                                 _jspx_th_logic_005fnotPresent_005f16.setPageContext(_jspx_page_context);
/* 3257 */                                 _jspx_th_logic_005fnotPresent_005f16.setParent(_jspx_th_logic_005fpresent_005f19);
/*      */                                 
/* 3259 */                                 _jspx_th_logic_005fnotPresent_005f16.setRole("DEMO,ENTERPRISEADMIN");
/* 3260 */                                 int _jspx_eval_logic_005fnotPresent_005f16 = _jspx_th_logic_005fnotPresent_005f16.doStartTag();
/* 3261 */                                 if (_jspx_eval_logic_005fnotPresent_005f16 != 0) {
/*      */                                   for (;;) {
/* 3263 */                                     out.write("\n\t       ");
/*      */                                     
/*      */ 
/* 3266 */                                     if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                     {
/*      */ 
/* 3269 */                                       out.write("\n\t\t    <a href=\"/it360/createBS.do?method=getNetworkDevices&productName=OpManager&haid=");
/* 3270 */                                       out.print(resIdTOCheck);
/* 3271 */                                       out.write("\" class=\"monitorgp-links\" ><img src=\" /images/icon_assoicatemonitors.gif\" border=\"0\" align=\"center\"  title=\"");
/* 3272 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text"));
/* 3273 */                                       out.write("\"/></a>\n\t       ");
/*      */ 
/*      */                                     }
/* 3276 */                                     else if (("HAI".equals(resourceType)) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType)) && (!"1013".equals(haiGroupType)))
/*      */                                     {
/*      */ 
/* 3279 */                                       out.write("\n\t\t    <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=");
/* 3280 */                                       out.print(resIdTOCheck);
/* 3281 */                                       out.write("&fromwhere=monitorgroupview\" class=\"monitorgp-links\" ><img src=\" /images/icon_assoicatemonitors.gif\" align=\"center\" border=\"0\"  title=\"");
/* 3282 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text"));
/* 3283 */                                       out.write("\"/></a>\n\t       ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3287 */                                     out.write("\n\t\t    ");
/* 3288 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f16.doAfterBody();
/* 3289 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3293 */                                 if (_jspx_th_logic_005fnotPresent_005f16.doEndTag() == 5) {
/* 3294 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f16); return;
/*      */                                 }
/*      */                                 
/* 3297 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f16);
/* 3298 */                                 out.write("\n                ");
/* 3299 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f19.doAfterBody();
/* 3300 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3304 */                             if (_jspx_th_logic_005fpresent_005f19.doEndTag() == 5) {
/* 3305 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f19); return;
/*      */                             }
/*      */                             
/* 3308 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f19);
/* 3309 */                             out.write("\n                \t\t&nbsp;&nbsp;\n\t\t\t");
/*      */                             
/* 3311 */                             PresentTag _jspx_th_logic_005fpresent_005f20 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3312 */                             _jspx_th_logic_005fpresent_005f20.setPageContext(_jspx_page_context);
/* 3313 */                             _jspx_th_logic_005fpresent_005f20.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 3315 */                             _jspx_th_logic_005fpresent_005f20.setRole("ADMIN,ENTERPRISEADMIN");
/* 3316 */                             int _jspx_eval_logic_005fpresent_005f20 = _jspx_th_logic_005fpresent_005f20.doStartTag();
/* 3317 */                             if (_jspx_eval_logic_005fpresent_005f20 != 0) {
/*      */                               for (;;) {
/* 3319 */                                 out.write("\n\t\t <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=");
/* 3320 */                                 out.print(resIdTOCheck);
/* 3321 */                                 out.write("&mgview=true')\"  enableClass=\"monitorgp-links\" ><img src=\"/images/icon_assigncustomfields.gif\" align=\"center\" border=\"0\"  title=\"");
/* 3322 */                                 out.print(FormatUtil.getString("am.myfield.assign.text"));
/* 3323 */                                 out.write("\"/></a> ");
/* 3324 */                                 out.write("\n        ");
/* 3325 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f20.doAfterBody();
/* 3326 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3330 */                             if (_jspx_th_logic_005fpresent_005f20.doEndTag() == 5) {
/* 3331 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f20); return;
/*      */                             }
/*      */                             
/* 3334 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f20);
/* 3335 */                             out.write("\n\t\t</td></tr></table></div>\n         </td>\n\n                <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n                ");
/*      */                             
/* 3337 */                             SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3338 */                             _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3339 */                             _jspx_th_c_005fset_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 3341 */                             _jspx_th_c_005fset_005f1.setVar("key");
/* 3342 */                             int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3343 */                             if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3344 */                               if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3345 */                                 out = _jspx_page_context.pushBody();
/* 3346 */                                 _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 3347 */                                 _jspx_th_c_005fset_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3350 */                                 out.write(32);
/* 3351 */                                 out.print(row.get(residIndex) + "#" + availid + "#" + "MESSAGE");
/* 3352 */                                 int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3353 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3356 */                               if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3357 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3360 */                             if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3361 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                             }
/*      */                             
/* 3364 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3365 */                             out.write("\n\n         <td  class=\"whitegrayrightalign\" align=\"center\" width=\"8%\" ><a href=\"javascript:void(0)\" ");
/*      */                             
/* 3367 */                             IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3368 */                             _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 3369 */                             _jspx_th_c_005fif_005f30.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 3371 */                             _jspx_th_c_005fif_005f30.setTest("${alert[key]!=null}");
/* 3372 */                             int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 3373 */                             if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */                               for (;;) {
/* 3375 */                                 out.write(" onmouseover=\"ddrivetip(this,event,'");
/* 3376 */                                 if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                                   return;
/* 3378 */                                 out.write("<br>'+v+'");
/* 3379 */                                 out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 3380 */                                 out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3381 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 3382 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3386 */                             if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 3387 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30); return;
/*      */                             }
/*      */                             
/* 3390 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 3391 */                             out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3392 */                             out.print(row.get(residIndex));
/* 3393 */                             out.write("&attributeid=");
/* 3394 */                             out.print(availid);
/* 3395 */                             out.write("&alertconfigurl=");
/* 3396 */                             out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + row.get(residIndex) + "&attributeIDs=" + healthid + "," + availid + "&attributeToSelect=" + availid + "&redirectto=" + URLEncoder.encode(new StringBuilder().append("/showapplication.do?haid=").append(row.get(residIndex)).append("&method=showApplication").toString())));
/* 3397 */                             out.write("')\">\n                ");
/* 3398 */                             out.print(getSeverityImageForAvailability(alert.getProperty(row.get(residIndex) + "#" + availid)));
/* 3399 */                             out.write(" </a></td>\n  \t        ");
/*      */                             
/* 3401 */                             SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3402 */                             _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3403 */                             _jspx_th_c_005fset_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 3405 */                             _jspx_th_c_005fset_005f2.setVar("key");
/* 3406 */                             int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3407 */                             if (_jspx_eval_c_005fset_005f2 != 0) {
/* 3408 */                               if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3409 */                                 out = _jspx_page_context.pushBody();
/* 3410 */                                 _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 3411 */                                 _jspx_th_c_005fset_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3414 */                                 out.write(32);
/* 3415 */                                 out.print(row.get(residIndex) + "#" + availid + "#" + "MESSAGE");
/* 3416 */                                 int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3417 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3420 */                               if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3421 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3424 */                             if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3425 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                             }
/*      */                             
/* 3428 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3429 */                             out.write("\n                <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\t ");
/*      */                             
/* 3431 */                             SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3432 */                             _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3433 */                             _jspx_th_c_005fset_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 3435 */                             _jspx_th_c_005fset_005f3.setVar("key");
/* 3436 */                             int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3437 */                             if (_jspx_eval_c_005fset_005f3 != 0) {
/* 3438 */                               if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3439 */                                 out = _jspx_page_context.pushBody();
/* 3440 */                                 _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 3441 */                                 _jspx_th_c_005fset_005f3.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3444 */                                 out.write(32);
/* 3445 */                                 out.print(row.get(residIndex) + "#" + healthid + "#" + "MESSAGE");
/* 3446 */                                 int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 3447 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3450 */                               if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3451 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3454 */                             if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3455 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                             }
/*      */                             
/* 3458 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 3459 */                             out.write("\n         <td  class=\"whitegrayrightalign\" align=\"center\" width=\"7%\">");
/* 3460 */                             if (!"Service".equals((String)row.get(2))) {
/* 3461 */                               out.write("<a href=\"javascript:void(-1)\" ");
/*      */                               
/* 3463 */                               IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3464 */                               _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 3465 */                               _jspx_th_c_005fif_005f31.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                               
/* 3467 */                               _jspx_th_c_005fif_005f31.setTest("${alert[key]!=null}");
/* 3468 */                               int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 3469 */                               if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */                                 for (;;) {
/* 3471 */                                   out.write(" onmouseover=\"ddrivetip(this,event,'");
/* 3472 */                                   if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f31, _jspx_page_context))
/*      */                                     return;
/* 3474 */                                   out.write("<br>'+v+'");
/* 3475 */                                   out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 3476 */                                   out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3477 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 3478 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3482 */                               if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 3483 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31); return;
/*      */                               }
/*      */                               
/* 3486 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 3487 */                               out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3488 */                               out.print(row.get(residIndex));
/* 3489 */                               out.write("&attributeid=");
/* 3490 */                               out.print(healthid);
/* 3491 */                               out.write("&alertconfigurl=");
/* 3492 */                               out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + row.get(residIndex) + "&attributeIDs=" + healthid + "," + availid + "&attributeToSelect=" + healthid + "&redirectto=" + URLEncoder.encode(new StringBuilder().append("/showapplication.do?haid=").append(row.get(residIndex)).append("&method=showApplication").toString())));
/* 3493 */                               out.write("')\">\n                ");
/* 3494 */                               out.print(getSeverityImageForHealth(alert.getProperty(row.get(residIndex) + "#" + healthid)));
/* 3495 */                               out.write("</a>");
/*      */                             }
/* 3497 */                             out.write("</td>\n     <td  class=\"whitegrayrightalign\" align=\"left\" width=\"8%\" > <img src=\" ");
/* 3498 */                             out.print(actionimage);
/* 3499 */                             out.write("\" border=\"0\"   title=\"");
/* 3500 */                             out.print(actionmessage);
/* 3501 */                             out.write("\"/>&nbsp;&nbsp;</td>\n    </tr>\n    ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 3506 */                             if (!checkRow) {
/* 3507 */                               mgresourceid = resIdTOCheck;
/* 3508 */                               rowID = "expandRow";
/* 3509 */                               checkRow = true;
/*      */                             }
/*      */                             
/* 3512 */                             out.write("\t  \n     ");
/*      */                           }
/* 3514 */                           out.write(10);
/* 3515 */                           out.write(9);
/* 3516 */                           out.write(32);
/*      */                           
/* 3518 */                           if (chilmos.get(resIdTOCheck + "") != null)
/*      */                           {
/* 3520 */                             ArrayList singlechilmos = (ArrayList)chilmos.get(resIdTOCheck + "");
/* 3521 */                             if ((mgresourceid != null) && (mgresourceid.equals(resIdTOCheck))) {
/* 3522 */                               monitorlist = singlechilmos.size();
/*      */                             }
/* 3524 */                             Hashtable availhealth = new Hashtable();
/* 3525 */                             availhealth.put("avail", availabilitykeys);
/* 3526 */                             availhealth.put("health", healthkeys);
/* 3527 */                             availhealth.put("alert", alert);
/* 3528 */                             String toappend = getAllChildNodestoDisplay(singlechilmos, resIdTOCheck + "", resIdTOCheck + "", chilmos, availhealth, 1, request, extDeviceMap, site24x7List);
/* 3529 */                             out.println(toappend);
/*      */                           }
/* 3531 */                           else if (("HAI".equals(resourceType)) && (isInventory))
/*      */                           {
/*      */ 
/* 3534 */                             out.write("\n\t  <tr class=\"whitegrayrightalign\" id=\"#monitor");
/* 3535 */                             out.print(resIdTOCheck);
/* 3536 */                             out.write("\"  style=\"display:");
/* 3537 */                             out.print(rowStyle);
/* 3538 */                             out.write(";\">\n\t <td class=\"whitegrayrightalign\" width=3% >&nbsp;</td>\n\t <td class=\"whitegrayrightalign\"  colspan=\"5\" style='padding-left:40px' >\n\t       <span class=\"bodytext\">");
/* 3539 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text"));
/* 3540 */                             out.write("</span>\n\t       ");
/*      */                             
/* 3542 */                             PresentTag _jspx_th_logic_005fpresent_005f21 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3543 */                             _jspx_th_logic_005fpresent_005f21.setPageContext(_jspx_page_context);
/* 3544 */                             _jspx_th_logic_005fpresent_005f21.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                             
/* 3546 */                             _jspx_th_logic_005fpresent_005f21.setRole("ADMIN");
/* 3547 */                             int _jspx_eval_logic_005fpresent_005f21 = _jspx_th_logic_005fpresent_005f21.doStartTag();
/* 3548 */                             if (_jspx_eval_logic_005fpresent_005f21 != 0) {
/*      */                               for (;;) {
/* 3550 */                                 out.write("\n\t       ");
/*      */                                 
/*      */ 
/* 3553 */                                 if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                 {
/*      */ 
/* 3556 */                                   out.write("\n\t       <span class=\"bodytext\"> ");
/* 3557 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 3558 */                                   out.write(" <a href=\"/it360/createBS.do?method=getNetworkDevices&productName=OpManager&haid=");
/* 3559 */                                   out.print(resIdTOCheck);
/* 3560 */                                   out.write("\" class=\"monitorgp-links\">");
/* 3561 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 3562 */                                   out.write("</span>\n\t       ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3568 */                                   out.write("\n\t       <span class=\"bodytext\">");
/* 3569 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 3570 */                                   out.write(" <a href=\"/showresource.do?type=All&method=getMonitorForm&haid=");
/* 3571 */                                   out.print(resIdTOCheck);
/* 3572 */                                   out.write("&fromwhere=monitorgroupview\" class=\"monitorgp-links\">");
/* 3573 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 3574 */                                   out.write("</span>\n\t       ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3578 */                                 out.write("\n\t       ");
/* 3579 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f21.doAfterBody();
/* 3580 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3584 */                             if (_jspx_th_logic_005fpresent_005f21.doEndTag() == 5) {
/* 3585 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f21); return;
/*      */                             }
/*      */                             
/* 3588 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f21);
/* 3589 */                             out.write(" </td>\n\t</tr>\n\t     ");
/*      */                           }
/* 3591 */                           out.write(10);
/* 3592 */                           out.write(9);
/* 3593 */                           out.write(32);
/*      */                         }
/* 3595 */                         out.write(10);
/* 3596 */                         out.write(9);
/* 3597 */                         out.write(32);
/* 3598 */                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3599 */                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3600 */                         i = (Integer)_jspx_page_context.findAttribute("i");
/* 3601 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 3604 */                       if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3605 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 3608 */                     if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3609 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                     }
/*      */                     
/* 3612 */                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3613 */                     out.write(10);
/* 3614 */                     out.write(9);
/* 3615 */                     out.write(32);
/* 3616 */                     if (showmore)
/*      */                     {
/* 3618 */                       out.write("\n\t <tr  id=\"#monitororphaned\" class=\"leftcells\" height=\"35\"> ");
/* 3619 */                       out.write("\n\t <td class=\"whitegrayrightalign\" width=3% >&nbsp;</td>\n\t <td class=\"whitegrayrightalign\" align=\"right\" colspan=\"5\" style=\"padding-right:40px\" >\t       \n\t       <a href=\"javascript:void(0);\" class=\"show-tag\"  onclick=\"showmoremonitors('/showresource.do?method=showMonitorGroupViewIndividual&parents=orphaned&lindx=1&pageno=");
/* 3620 */                       out.print(pageno);
/* 3621 */                       out.write("',this,'true')\">\n\t\t   ");
/* 3622 */                       out.print(FormatUtil.getString("am.webclient.configurealert.showmore"));
/* 3623 */                       out.write("</a> \t\t   \n\t\t   </td>\n\t</tr>  \n\t ");
/*      */                     }
/* 3625 */                     out.write(10);
/* 3626 */                     out.write(9);
/* 3627 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3628 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3632 */                 if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3633 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                 }
/*      */                 
/* 3636 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3637 */                 out.write("\t\n</table>\n\n\n</form>\n </td>\n </tr>\n</table>\n\n<script> \t\t\n\t\t\t\t\n                           $(window).load(function() {//No I18N\n\t\t\t\t\t\t   try{\n\t\t\t\t\t\t   \n\t\t\t\t\t\t   ");
/* 3638 */                 if ("treeview".equals(viewtype)) {
/* 3639 */                   out.write("\t\t\t\t\t\t   \n\t\t\t\t\t\t   toggleallDivs('show','false');\t//No I18N \t\t\t\t\t   \n\t\t\t\t\t\t   ");
/*      */                 }
/*      */                 else
/*      */                 {
/* 3643 */                   out.write("\n                        \t   retainstructure(\"");
/* 3644 */                   out.print(request.getParameter("retaintree"));
/* 3645 */                   out.write("\");\n\t\t\t\t\t\t\t   ");
/*      */                 }
/* 3647 */                 out.write("\n\t\t\t\t $(\"#allMonitorGroups tr div[id^=mgaction]\").css('display','none');//No I18N\n                                 $(\"#allMonitorGroups tr[id^=#monitor]\").hover(function() {//No I18N\n\n                                        $(this).find(\"div[id^=mgaction]\").css('display','inline'); //No I18N\n\n                                },\n                                function () {\n                                          $(this).find(\"div[id^=mgaction]\").css('display','none'); //No I18N\n\n                                });\n\t\t\t\t\t\t\t\t\n                                 var actionid=getCookie('actionid');//No I18N                             \t\t\n\t\t\t\t\t\t\t\tif(actionid==undefined){\n                             \t\t\t\tactionid=\"showall\"; //No I18N\n                             \t\t\t}\n                             \tif(document.deletemonitor.operation)\n                             \t\tdocument.deletemonitor.operation.selectedIndex=0;\n                             ");
/*      */                 
/* 3649 */                 String retaintree = (String)request.getAttribute("retaintree");
/*      */                 
/* 3651 */                 out.write("\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/* 3652 */                 if ((retaintree != null) && (!retaintree.equals("null")) && (!retaintree.trim().equals("")))
/*      */                 {
/*      */ 
/* 3655 */                   out.write("\t\t\tvar temp1,temp2;\n                             \t\t\ttemp1='");
/* 3656 */                   out.print(retaintree);
/* 3657 */                   out.write("';//No I18N\n                             \t\t\ttemp2 =temp1.split(\"$\");//No I18N\n                             \t\t\tfor(h=0;h<temp2.length;h++)\n                             \t\t\t{\n                             \t\t\t\tparentresid=temp2[h];//No I18N                             \t\t\t\n                             \t\t\t}\n                             ");
/*      */                 }
/*      */                 
/* 3660 */                 out.write("\n                             loadURLType(\"");
/* 3661 */                 out.print(customValue);
/* 3662 */                 out.write("\",null,null,false);\n}\n\t\t\t\tcatch(err){\n\t\t\t\t\t\tconsole.log('err document ready    '+err)\n\t\t\t\t\t\t}\t\t\t\t\t\t\n\t\t\t\t\t\t$('.chzn-select').chosen();\t\n                        });\t\n\t\t\t\t\t\t\n\t\t\t\t\t\tfunction retainstructure(structure)\n\t\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\ttry{\n\t\t\t\t\t\tvar array = structure.split('$');\t\t\t\t\t\t\n\t\t\t\t\t\tfor (var i=0;i<array.length;i++)\n\t\t\t\t\t\t{\t\t\t\t\t\t\n\t\t\t\t\t\tvar tree=array[i];\n\t\t\t\t\t\tvar grpids=tree.split('|');\n\t\t\t\t\t\tvar idx=grpids.length;\t\t\t\t\t\t\n\t\t\t\t\t\tvar resourceid=grpids[idx-1];\t\t\t\t\t\t\t\t\t\t\t\t\t\n                        expandgroup(resourceid,idx,tree); \t \n\t\t\t\t\t\t}\n\t\t\t\t\t\tsethovertomgaction();\n\t\t\t\t\t\t}\n\t\t\t\t\t\tcatch(err)\n\t\t\t\t\t\t{\n\t\t\t\t\t\tconsole.log('err  in retainstructure   '+err);\t\t\t\t\t\t\n\t\t\t\t\t\t}\n\t\t\t\t\t\t}\n\t\t\t\t\t\tfunction expandgroup(resourceid,idx,tree){ \n\t\t\t\t\t\ttry{\n\t\t\t\t\t\t\tvar aa=\"false\";\n\t\t\t\t\t\t\tvar url='/showresource.do?method=showMonitorGroupViewIndividual&parents='+tree+'&lindx='+idx;//No I18N\n\t\t\t\t\t\t\t $('#allMonitorGroups > tbody  > tr').each(function() {\t\t\t\t\t\t\t \n\t\t\t\t\t\t\t try{\n\t\t\t\t\t\t\tif($(this).attr(\"isgroup\")==\"true\"){\t\t\t\t\t\t\t \n\t\t\t\t\t\t\t$(this).find('input[type=checkbox]').each(function(){\t\t\t\t\t\t\t\t\n");
/* 3663 */                 out.write("\t\t\t\t\t\t\tif($(this).val()==resourceid){\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t aa=\"true\";\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\t});\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\tif(aa==\"true\"){ \n\t\t\t\t\t\t\t\tgetDynamicCall11(url,$(this));\n\t\t\t\t\t\t\t\tvar explk=$(this).find('.expandlink')[0]; //No I18N \n\t\t\t\t\t\t        toggleChildMos('#monitor'+tree,explk);//No I18N \n\t\t\t\t\t\t\t\ttoggleTreeImage(tree); \t\n\t\t\t\t\t\t\t\treturn false;\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\t}\t\t\t \n\t\t\t\t\t\t }\n\t\t\t\t\t\t catch(err){\n\t\t\t\t\t\t console.log('err     '+err); \n\t\t\t\t\t\t }\n\t\t\t\t\t\t });\n\t\t\t\t\t\t\t\n\t\t\t\t}catch(err)\n\t\t\t\t{\n\t\t\t\tconsole.log(err);\n\t\t\t\t}\t\t\t\t\t\t\t\n\t\t\t\t}\t\n\t\t\t\t\t\t\t\t\n                </script>\n\t\t\t\t\n\t\t\t\t\n");
/* 3664 */                 out.write(10);
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/* 3668 */                 ex.printStackTrace();
/*      */               }
/*      */               
/* 3671 */               out.write(10);
/* 3672 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3673 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3676 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3677 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3680 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3681 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 3684 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3685 */           out.write(10);
/* 3686 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3688 */           out.write(32);
/* 3689 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3690 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3694 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3695 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3698 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3699 */         out.write("                                                                                                                \n\n");
/*      */       }
/* 3701 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3702 */         out = _jspx_out;
/* 3703 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3704 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3705 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3708 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3714 */     PageContext pageContext = _jspx_page_context;
/* 3715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3717 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3718 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3719 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3721 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3723 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3724 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3725 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3726 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3727 */       return true;
/*      */     }
/* 3729 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3730 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3735 */     PageContext pageContext = _jspx_page_context;
/* 3736 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3738 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3739 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3740 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3742 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */     
/* 3744 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/MapsLeftPage.jsp?method=showMonitorGroupView&group=All&selectedNetwork=null");
/* 3745 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3746 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3747 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3748 */       return true;
/*      */     }
/* 3750 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3756 */     PageContext pageContext = _jspx_page_context;
/* 3757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3759 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3760 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3761 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3763 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3764 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3765 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3767 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3768 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3769 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3773 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3774 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3775 */       return true;
/*      */     }
/* 3777 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3783 */     PageContext pageContext = _jspx_page_context;
/* 3784 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3786 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3787 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3788 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 3790 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 3791 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3792 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3793 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3794 */       return true;
/*      */     }
/* 3796 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3802 */     PageContext pageContext = _jspx_page_context;
/* 3803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3805 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3806 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3807 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 3809 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 3810 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3811 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3812 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3813 */       return true;
/*      */     }
/* 3815 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3821 */     PageContext pageContext = _jspx_page_context;
/* 3822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3824 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3825 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3826 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3828 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3829 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3830 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3832 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3833 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3834 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3838 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3839 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3840 */       return true;
/*      */     }
/* 3842 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3843 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3848 */     PageContext pageContext = _jspx_page_context;
/* 3849 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3851 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3852 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3853 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 3855 */     _jspx_th_c_005fif_005f0.setTest("${not empty param.haid}");
/* 3856 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3857 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3859 */         out.write("\n                    document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitorGroups&retaintree=\"+retaintree+\"&haid=");
/* 3860 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3861 */           return true;
/* 3862 */         out.write("\"+\"&unopengroups=\"+unopengroups;\t\t    \n\t\t\t");
/* 3863 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3864 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3868 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3869 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3870 */       return true;
/*      */     }
/* 3872 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3878 */     PageContext pageContext = _jspx_page_context;
/* 3879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3881 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3882 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3883 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3885 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 3886 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3887 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3888 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3889 */       return true;
/*      */     }
/* 3891 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3897 */     PageContext pageContext = _jspx_page_context;
/* 3898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3900 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3901 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3902 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 3904 */     _jspx_th_c_005fif_005f1.setTest("${empty param.haid}");
/* 3905 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3906 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3908 */         out.write("\n                     document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitorGroups&retaintree=\"+retaintree+\"&unopengroups=\"+unopengroups;\n\t\t    ");
/* 3909 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3910 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3914 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3915 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3916 */       return true;
/*      */     }
/* 3918 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3919 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3924 */     PageContext pageContext = _jspx_page_context;
/* 3925 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3927 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3928 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3929 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3931 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3932 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3933 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3935 */         out.write("\n                alertUser();\n                return;\n        ");
/* 3936 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3937 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3941 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3942 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3943 */       return true;
/*      */     }
/* 3945 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3951 */     PageContext pageContext = _jspx_page_context;
/* 3952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3954 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3955 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3956 */     _jspx_th_logic_005fpresent_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3958 */     _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 3959 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3960 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 3962 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3963 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3964 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3968 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3969 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3970 */       return true;
/*      */     }
/* 3972 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3973 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3978 */     PageContext pageContext = _jspx_page_context;
/* 3979 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3981 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3982 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3983 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 3985 */     _jspx_th_c_005fif_005f2.setTest("${not empty param.haid}");
/* 3986 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3987 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3989 */         out.write("\n                    document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitorGroups&retaintree=\"+retaintree+\"&haid=");
/* 3990 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3991 */           return true;
/* 3992 */         out.write("&isReset=true\"+\"&unopengroups=\"+unopengroups;\n\t\t    ");
/* 3993 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3994 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3998 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3999 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4000 */       return true;
/*      */     }
/* 4002 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4008 */     PageContext pageContext = _jspx_page_context;
/* 4009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4011 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4012 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4013 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4015 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 4016 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4017 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4018 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4019 */       return true;
/*      */     }
/* 4021 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4027 */     PageContext pageContext = _jspx_page_context;
/* 4028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4030 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4031 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4032 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 4034 */     _jspx_th_c_005fif_005f3.setTest("${empty param.haid}");
/* 4035 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4036 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 4038 */         out.write("\n                     document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitorGroups&retaintree=\"+retaintree+\"&isReset=true\"+\"&unopengroups=\"+unopengroups;\n\t\t    ");
/* 4039 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4040 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4044 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4045 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4046 */       return true;
/*      */     }
/* 4048 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4054 */     PageContext pageContext = _jspx_page_context;
/* 4055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4057 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4058 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 4059 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4061 */     _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 4062 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 4063 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 4065 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 4066 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 4067 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4071 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 4072 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4073 */       return true;
/*      */     }
/* 4075 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4076 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4081 */     PageContext pageContext = _jspx_page_context;
/* 4082 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4084 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4085 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 4086 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 4088 */     _jspx_th_c_005fif_005f4.setTest("${not empty param.haid}");
/* 4089 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 4090 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 4092 */         out.write("\n                  document.deletemonitor.action=\"/deleteMO.do?method=ManageMonitorGroups&retaintree=\"+retaintree+\"&haid=");
/* 4093 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 4094 */           return true;
/* 4095 */         out.write("&unopengroups=\"+unopengroups;\t\t\t  \t\t\t\t\t\t  \n\t\t   ");
/* 4096 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4097 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4101 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 4102 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4103 */       return true;
/*      */     }
/* 4105 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4106 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4111 */     PageContext pageContext = _jspx_page_context;
/* 4112 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4114 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4115 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4116 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 4118 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 4119 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4120 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4121 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4122 */       return true;
/*      */     }
/* 4124 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4125 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4130 */     PageContext pageContext = _jspx_page_context;
/* 4131 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4133 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4134 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 4135 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 4137 */     _jspx_th_c_005fif_005f5.setTest("${empty param.haid}");
/* 4138 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 4139 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 4141 */         out.write("\n\t\t\t document.deletemonitor.action=\"/deleteMO.do?method=ManageMonitorGroups&retaintree=\"+retaintree+\"&unopengroups=\"+unopengroups;\t\t\t\t\t\t\n\t\t ");
/* 4142 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 4143 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4147 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 4148 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4149 */       return true;
/*      */     }
/* 4151 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4152 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4157 */     PageContext pageContext = _jspx_page_context;
/* 4158 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4160 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4161 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 4162 */     _jspx_th_logic_005fpresent_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4164 */     _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 4165 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 4166 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 4168 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 4169 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 4170 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4174 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 4175 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4176 */       return true;
/*      */     }
/* 4178 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4179 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_logic_005fnotPresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4184 */     PageContext pageContext = _jspx_page_context;
/* 4185 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4187 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4188 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 4189 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_logic_005fnotPresent_005f5);
/*      */     
/* 4191 */     _jspx_th_c_005fif_005f6.setTest("${empty param.haid}");
/* 4192 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 4193 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 4195 */         out.write("\n\t\t   document.deletemonitor.action=\"/deleteMO.do?method=alterActionsforMG&alteraction=disable&retaintree=\"+retaintree+\"&unopengroups=\"+unopengroups;\n\t ");
/* 4196 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4197 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4201 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4202 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4203 */       return true;
/*      */     }
/* 4205 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4206 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_logic_005fnotPresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4211 */     PageContext pageContext = _jspx_page_context;
/* 4212 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4214 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4215 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4216 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_logic_005fnotPresent_005f5);
/*      */     
/* 4218 */     _jspx_th_c_005fif_005f7.setTest("${not empty param.haid}");
/* 4219 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4220 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 4222 */         out.write("\n\t  document.deletemonitor.action=\"/deleteMO.do?method=alterActionsforMG&alteraction=disable&retaintree=\"+retaintree+\"&haid=");
/* 4223 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 4224 */           return true;
/* 4225 */         out.write("\"+\"&unopengroups=\"+unopengroups;\n\t ");
/* 4226 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4227 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4231 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4232 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4233 */       return true;
/*      */     }
/* 4235 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4236 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4241 */     PageContext pageContext = _jspx_page_context;
/* 4242 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4244 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4245 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4246 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4248 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 4249 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4250 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4251 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4252 */       return true;
/*      */     }
/* 4254 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4260 */     PageContext pageContext = _jspx_page_context;
/* 4261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4263 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4264 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 4265 */     _jspx_th_logic_005fpresent_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4267 */     _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 4268 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 4269 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 4271 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 4272 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 4273 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4277 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 4278 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4279 */       return true;
/*      */     }
/* 4281 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 4282 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_logic_005fnotPresent_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4287 */     PageContext pageContext = _jspx_page_context;
/* 4288 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4290 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4291 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4292 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_logic_005fnotPresent_005f6);
/*      */     
/* 4294 */     _jspx_th_c_005fif_005f8.setTest("${not empty param.haid}");
/* 4295 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4296 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 4298 */         out.write("\n\t  document.deletemonitor.action=\"/deleteMO.do?method=alterActionsforMG&alteraction=enable&retaintree=\"+retaintree+\"&haid=");
/* 4299 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 4300 */           return true;
/* 4301 */         out.write("\"+\"&unopengroups=\"+unopengroups;\n\t");
/* 4302 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4303 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4307 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4308 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4309 */       return true;
/*      */     }
/* 4311 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4312 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4317 */     PageContext pageContext = _jspx_page_context;
/* 4318 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4320 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4321 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4322 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 4324 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 4325 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4326 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4327 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4328 */       return true;
/*      */     }
/* 4330 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4331 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_logic_005fnotPresent_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4336 */     PageContext pageContext = _jspx_page_context;
/* 4337 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4339 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4340 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4341 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_logic_005fnotPresent_005f6);
/*      */     
/* 4343 */     _jspx_th_c_005fif_005f9.setTest("${empty param.haid}");
/* 4344 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4345 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 4347 */         out.write("\n\t\tdocument.deletemonitor.action=\"/deleteMO.do?method=alterActionsforMG&alteraction=enable&retaintree=\"+retaintree+\"&unopengroups=\"+unopengroups;\n\t");
/* 4348 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4349 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4353 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4354 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4355 */       return true;
/*      */     }
/* 4357 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4363 */     PageContext pageContext = _jspx_page_context;
/* 4364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4366 */     PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4367 */     _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 4368 */     _jspx_th_logic_005fpresent_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4370 */     _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 4371 */     int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 4372 */     if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */       for (;;) {
/* 4374 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 4375 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 4376 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4380 */     if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 4381 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4382 */       return true;
/*      */     }
/* 4384 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 4385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4390 */     PageContext pageContext = _jspx_page_context;
/* 4391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4393 */     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4394 */     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 4395 */     _jspx_th_logic_005fpresent_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4397 */     _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 4398 */     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 4399 */     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */       for (;;) {
/* 4401 */         out.write("\n  \t\talertUser();\n  \t\treturn;\n  \t");
/* 4402 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 4403 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4407 */     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 4408 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4409 */       return true;
/*      */     }
/* 4411 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4412 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4417 */     PageContext pageContext = _jspx_page_context;
/* 4418 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4420 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4421 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4422 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 4424 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 4425 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4426 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4427 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4428 */       return true;
/*      */     }
/* 4430 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4431 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4436 */     PageContext pageContext = _jspx_page_context;
/* 4437 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4439 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4440 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4441 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 4443 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 4444 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4445 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4446 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4447 */       return true;
/*      */     }
/* 4449 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4455 */     PageContext pageContext = _jspx_page_context;
/* 4456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4458 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4459 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4460 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 4462 */     _jspx_th_c_005fout_005f9.setValue("${param.haid}");
/* 4463 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4464 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4465 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4466 */       return true;
/*      */     }
/* 4468 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4469 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4474 */     PageContext pageContext = _jspx_page_context;
/* 4475 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4477 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 4478 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4479 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4481 */     _jspx_th_c_005fset_005f0.setTarget("${AMActionForm}");
/*      */     
/* 4483 */     _jspx_th_c_005fset_005f0.setProperty("defaultmonitorsview");
/*      */     
/* 4485 */     _jspx_th_c_005fset_005f0.setValue("showMonitorGroupView");
/* 4486 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4487 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4488 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4489 */       return true;
/*      */     }
/* 4491 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4497 */     PageContext pageContext = _jspx_page_context;
/* 4498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4500 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4501 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 4502 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4504 */     _jspx_th_html_005fhidden_005f0.setProperty("zoomlevel");
/* 4505 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 4506 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 4507 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4508 */       return true;
/*      */     }
/* 4510 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4516 */     PageContext pageContext = _jspx_page_context;
/* 4517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4519 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4520 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 4521 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4523 */     _jspx_th_html_005fhidden_005f1.setProperty("zoomlocation");
/* 4524 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 4525 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 4526 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 4527 */       return true;
/*      */     }
/* 4529 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 4530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f10(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4535 */     PageContext pageContext = _jspx_page_context;
/* 4536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4538 */     PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4539 */     _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 4540 */     _jspx_th_logic_005fpresent_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4542 */     _jspx_th_logic_005fpresent_005f10.setRole("DEMO");
/* 4543 */     int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 4544 */     if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */       for (;;) {
/* 4546 */         out.write("\n    alertUser();\n    return;\n  ");
/* 4547 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 4548 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4552 */     if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 4553 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4554 */       return true;
/*      */     }
/* 4556 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f11(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4562 */     PageContext pageContext = _jspx_page_context;
/* 4563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4565 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f11 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4566 */     _jspx_th_logic_005fnotPresent_005f11.setPageContext(_jspx_page_context);
/* 4567 */     _jspx_th_logic_005fnotPresent_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4569 */     _jspx_th_logic_005fnotPresent_005f11.setRole("DEMO");
/* 4570 */     int _jspx_eval_logic_005fnotPresent_005f11 = _jspx_th_logic_005fnotPresent_005f11.doStartTag();
/* 4571 */     if (_jspx_eval_logic_005fnotPresent_005f11 != 0) {
/*      */       for (;;) {
/* 4573 */         out.write("\n  document.AMActionForm.submit();\n  ");
/* 4574 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f11.doAfterBody();
/* 4575 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4579 */     if (_jspx_th_logic_005fnotPresent_005f11.doEndTag() == 5) {
/* 4580 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f11);
/* 4581 */       return true;
/*      */     }
/* 4583 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f11);
/* 4584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4589 */     PageContext pageContext = _jspx_page_context;
/* 4590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4592 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4593 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4594 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4596 */     _jspx_th_c_005fif_005f13.setTest("${forInventory == \"true\"}");
/* 4597 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4598 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 4600 */         out.write("\n&nbsp;\n");
/* 4601 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4602 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4606 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4607 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4608 */       return true;
/*      */     }
/* 4610 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4611 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4616 */     PageContext pageContext = _jspx_page_context;
/* 4617 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4619 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 4620 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4621 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 4623 */     _jspx_th_c_005fforEach_005f0.setVar("eachtag");
/*      */     
/* 4625 */     _jspx_th_c_005fforEach_005f0.setItems("${tags}");
/* 4626 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 4628 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4629 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 4631 */           out.write("\n\t\t\t\t\t\t\t<option value=\"");
/* 4632 */           boolean bool; if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4633 */             return true;
/* 4634 */           out.write(34);
/* 4635 */           out.write(62);
/* 4636 */           if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4637 */             return true;
/* 4638 */           out.write("</option>\n                            ");
/* 4639 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4640 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4644 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 4645 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4648 */         int tmp239_238 = 0; int[] tmp239_236 = _jspx_push_body_count_c_005fforEach_005f0; int tmp241_240 = tmp239_236[tmp239_238];tmp239_236[tmp239_238] = (tmp241_240 - 1); if (tmp241_240 <= 0) break;
/* 4649 */         out = _jspx_page_context.popBody(); }
/* 4650 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4652 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4653 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 4655 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4660 */     PageContext pageContext = _jspx_page_context;
/* 4661 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4663 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4664 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4665 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4667 */     _jspx_th_c_005fout_005f10.setValue("${eachtag.labelalias}");
/* 4668 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4669 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4670 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4671 */       return true;
/*      */     }
/* 4673 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4679 */     PageContext pageContext = _jspx_page_context;
/* 4680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4682 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4683 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4684 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4686 */     _jspx_th_c_005fout_005f11.setValue("${eachtag.label}");
/* 4687 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4688 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4689 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4690 */       return true;
/*      */     }
/* 4692 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4693 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4698 */     PageContext pageContext = _jspx_page_context;
/* 4699 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4701 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4702 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4703 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4705 */     _jspx_th_c_005fif_005f15.setTest("${forInventory == \"true\"}");
/* 4706 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4707 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 4709 */         out.write("\n&nbsp;\n");
/* 4710 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4711 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4715 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4716 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4717 */       return true;
/*      */     }
/* 4719 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4720 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4725 */     PageContext pageContext = _jspx_page_context;
/* 4726 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4728 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4729 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 4730 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 4732 */     _jspx_th_c_005fif_005f20.setTest("${notMG==\"true\"}");
/* 4733 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 4734 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 4736 */         out.write("\n\t\t&nbsp;\n\t\t");
/* 4737 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 4738 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4742 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 4743 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4744 */       return true;
/*      */     }
/* 4746 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4752 */     PageContext pageContext = _jspx_page_context;
/* 4753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4755 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4756 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 4757 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 4759 */     _jspx_th_c_005fif_005f24.setTest("${notMG==\"true\"}");
/* 4760 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 4761 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 4763 */         out.write("\n                &nbsp;\n                ");
/* 4764 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 4765 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4769 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 4770 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 4771 */       return true;
/*      */     }
/* 4773 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 4774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f29, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4779 */     PageContext pageContext = _jspx_page_context;
/* 4780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4782 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4783 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4784 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f29);
/*      */     
/* 4786 */     _jspx_th_c_005fout_005f12.setValue("${param.haid}");
/* 4787 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4788 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4789 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4790 */       return true;
/*      */     }
/* 4792 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f29, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4798 */     PageContext pageContext = _jspx_page_context;
/* 4799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4801 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4802 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4803 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f29);
/*      */     
/* 4805 */     _jspx_th_c_005fout_005f13.setValue("${param.haid}");
/* 4806 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4807 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4808 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4809 */       return true;
/*      */     }
/* 4811 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4817 */     PageContext pageContext = _jspx_page_context;
/* 4818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4820 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4821 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4822 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 4824 */     _jspx_th_c_005fout_005f14.setValue("${alert[key]}");
/* 4825 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4826 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4827 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4828 */       return true;
/*      */     }
/* 4830 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4836 */     PageContext pageContext = _jspx_page_context;
/* 4837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4839 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4840 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4841 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 4843 */     _jspx_th_c_005fout_005f15.setValue("${alert[key]}");
/* 4844 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4845 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4846 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4847 */       return true;
/*      */     }
/* 4849 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4855 */     PageContext pageContext = _jspx_page_context;
/* 4856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4858 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4859 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4860 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4862 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 4864 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 4865 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4866 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4867 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4868 */       return true;
/*      */     }
/* 4870 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4871 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MonitorGroupView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */