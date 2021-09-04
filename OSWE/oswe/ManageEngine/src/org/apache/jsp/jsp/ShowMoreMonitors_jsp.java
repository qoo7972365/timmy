/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.confparser.PreConfMonitorXMLParser;
/*      */ import com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.ChildMOHandler;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.io.IOException;
/*      */ import java.net.URLEncoder;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.jsp.JspApplicationContext;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.SkipPageException;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.HttpJspBase;
/*      */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.tomcat.InstanceManager;
/*      */ 
/*      */ public final class ShowMoreMonitors_jsp extends HttpJspBase implements JspSourceDependent
/*      */ {
/*   52 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*      */   
/*      */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List) {
/*   57 */     String ischecked = "true".equals(request.getParameter("checked")) ? "checked=\"checked\"" : "";
/*   58 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/*   59 */     boolean forInventory = false;
/*   60 */     String trdisplay = "none";
/*   61 */     String plusstyle = "inline";
/*   62 */     String minusstyle = "none";
/*   63 */     String haidTopLevel = "";
/*   64 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/*   66 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/*   68 */         haidTopLevel = request.getParameter("haid");
/*   69 */         forInventory = true;
/*   70 */         trdisplay = "table-row;";
/*   71 */         plusstyle = "none";
/*   72 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*   79 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*   81 */     if (("true".equals(request.getParameter("uncategorize"))) || ("orphaned".equals(request.getParameter("retaintree"))))
/*      */     {
/*   83 */       trdisplay = "table-row;";
/*      */     }
/*   85 */     HashMap<String, String[]> procAndServiceInfo = (HashMap)request.getAttribute("ProcessAndServiceInfo");
/*   86 */     ArrayList listtoreturn = new ArrayList();
/*   87 */     StringBuffer toreturn = new StringBuffer();
/*   88 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/*   89 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/*   90 */     Properties alert = (Properties)availhealth.get("alert");
/*   91 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/*   93 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/*   94 */       String childresid = (String)singlerow.get(0);
/*   95 */       String childresname = (String)singlerow.get(1);
/*   96 */       String childtype = ((String)singlerow.get(2) + "").trim();
/*   97 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/*   98 */       String shortname = ((String)singlerow.get(4) + "").trim();
/*   99 */       String unmanagestatus = (String)singlerow.get(5);
/*  100 */       String actionstatus = (String)singlerow.get(6);
/*  101 */       String linkclass = "monitorgp-links";
/*  102 */       String titleforres = childresname;
/*  103 */       String titilechildresname = childresname;
/*  104 */       String childimg = "/images/trcont.png";
/*  105 */       String flag = "enable";
/*  106 */       String dcstarted = (String)singlerow.get(8);
/*  107 */       String configMonitor = "";
/*  108 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/*  109 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/*  111 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/*  113 */       if (singlerow.get(7) != null)
/*      */       {
/*  115 */         flag = (String)singlerow.get(7);
/*      */       }
/*  117 */       String haiGroupType = "0";
/*  118 */       if ("HAI".equals(childtype))
/*      */       {
/*  120 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/*  122 */       childimg = "/images/trend.png";
/*  123 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/*  124 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/*  125 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/*  127 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/*  129 */       else if (actionstatus.equals("0"))
/*      */       {
/*  131 */         actionmsg = FormatUtil.getString("Actions Disabled");
/*  132 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/*  135 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/*  137 */         linkclass = "disabledtext";
/*  138 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/*  140 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/*  141 */       String availmouseover = "";
/*  142 */       if (alert.getProperty(availkey) != null)
/*      */       {
/*  144 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*  146 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/*  147 */       String healthmouseover = "";
/*  148 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/*  150 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey) + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/*  153 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/*  154 */       int spacing = 0;
/*  155 */       if (level >= 1)
/*      */       {
/*  157 */         spacing = 40 * level;
/*      */       }
/*  159 */       if (childtype.equals("HAI"))
/*      */       {
/*  161 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/*  162 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/*  163 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/*  165 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/*  166 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/*  167 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/*  168 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*  169 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/*  170 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  " + ischecked + ">";
/*  171 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/*  172 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/*  173 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/*  174 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/*  175 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/*  177 */         if (!forInventory)
/*      */         {
/*  179 */           removefromgroup = "";
/*      */         }
/*      */         
/*  182 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/*  184 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType))))
/*      */         {
/*  186 */           actions = editlink + actions;
/*      */         }
/*  188 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType))))
/*      */         {
/*  190 */           actions = actions + associatelink;
/*      */         }
/*  192 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/*  193 */         String arrowimg = "";
/*  194 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/*  196 */           actions = "";
/*  197 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/*  198 */           checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  " + ischecked + ">";
/*  199 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/*  201 */         if (isIt360)
/*      */         {
/*  203 */           actionimg = "";
/*  204 */           actions = "";
/*  205 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/*  206 */           checkbox = "";
/*      */         }
/*      */         
/*  209 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/*  211 */           actions = "";
/*      */         }
/*  213 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/*  215 */           checkbox = "";
/*      */         }
/*      */         
/*  218 */         String resourcelink = "";
/*      */         
/*  220 */         if ((!isIt360) || ((isIt360) && (childmos.get(childresid + "") != null)))
/*      */         {
/*  222 */           if ((flag != null) && (flag.equals("enable")))
/*      */           {
/*  224 */             resourcelink = "<a isopen=\"true\" href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "',this),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */           }
/*      */           else
/*      */           {
/*  228 */             resourcelink = "<a isopen=\"true\" href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "',this),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */           }
/*      */           
/*  231 */           toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/*  232 */           toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/*  233 */           toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/*  234 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*  235 */           toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/*  236 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/*  237 */           if (!isIt360)
/*      */           {
/*  239 */             toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */           }
/*      */           else
/*      */           {
/*  243 */             toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */           }
/*      */           
/*  246 */           toreturn.append("</tr>");
/*  247 */           if (childmos.get(childresid + "") != null)
/*      */           {
/*  249 */             String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/*  250 */             toreturn.append(toappend);
/*      */           }
/*      */           else
/*      */           {
/*  254 */             String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/*  255 */             if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*  256 */               assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*  257 */             if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType))))
/*      */             {
/*  259 */               toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/*  260 */               toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/*  261 */               toreturn.append(assocMessage);
/*  262 */               toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/*  263 */               toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/*  264 */               toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/*  265 */               toreturn.append("</tr>");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  272 */         String resourcelink = null;
/*  273 */         boolean hideEditLink = false;
/*  274 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/*  276 */           String link1 = (String)extDeviceMap.get(childresid);
/*  277 */           hideEditLink = true;
/*  278 */           if (isIt360)
/*      */           {
/*  280 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/*  284 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*  286 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/*  288 */           hideEditLink = true;
/*  289 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/*  290 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*  293 */         else if (ChildMOHandler.isChildMonitorTypeSupportedForMG(childtype))
/*      */         {
/*  295 */           String link1 = "/showapplication.do?method=showChildApplicationDetail&resId=" + childresid;
/*  296 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         else
/*      */         {
/*  300 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/*  303 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/*  304 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" " + ischecked + ">";
/*  305 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/*  306 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*  307 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/*  308 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/*  309 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/*  310 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*  311 */         if (ChildMOHandler.isChildMonitorTypeSupportedForMG(childtype))
/*      */         {
/*  313 */           String parentResId = "";
/*  314 */           String childdisplayname = "";
/*  315 */           if (procAndServiceInfo != null)
/*      */           {
/*  317 */             String[] arr = (String[])procAndServiceInfo.get(childresid);
/*  318 */             parentResId = arr[0];
/*  319 */             childdisplayname = arr[1];
/*      */           }
/*  321 */           if (childtype.equals("Process"))
/*      */           {
/*  323 */             editlink = "<a href=\"/HostResource.do?editProcess=true&processid=" + childresid + "&resourceid=" + parentResId + "&disableEdit=false&haid=&appName=\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*      */           }
/*  325 */           else if (childtype.equals("Service"))
/*      */           {
/*  327 */             editlink = "<a href=\"/HostResource.do?actiononServices=Edit&serviceid=" + childresid + "&resourceid=" + parentResId + "&displayname=" + childdisplayname + "&servicename=" + childdisplayname + "&name=&haid=&appName=\"  class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*      */           }
/*      */         }
/*  330 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/*  331 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/*  332 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/*  333 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/*  334 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/*  336 */         if (hideEditLink)
/*      */         {
/*  338 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/*  340 */         if (!forInventory)
/*      */         {
/*  342 */           removefromgroup = "";
/*      */         }
/*  344 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*  345 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/*  346 */           actions = actions + configcustomfields;
/*      */         }
/*  348 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType))))
/*      */         {
/*  350 */           actions = editlink + actions;
/*      */         }
/*  352 */         String managedLink = "";
/*  353 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/*      */ 
/*      */ 
/*  357 */           checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" " + ischecked + ">" + "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/*      */           
/*  359 */           actions = "";
/*  360 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/*  361 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/*  364 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/*  366 */           checkbox = "";
/*      */         }
/*      */         
/*  369 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/*  371 */           actions = "";
/*      */         }
/*  373 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'" + (ChildMOHandler.isChildMonitorTypeSupportedForMG(childtype) ? " ischild=true>" : ">"));
/*  374 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/*  375 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/*  376 */         if (isIt360)
/*      */         {
/*  378 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/*  382 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/*  384 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/*  385 */         if (!"Service".equals(childtype))
/*      */         {
/*  387 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/*      */         }
/*      */         else
/*      */         {
/*  391 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">&nbsp;</td>");
/*      */         }
/*  393 */         if (!isIt360)
/*      */         {
/*  395 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/*  399 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*  401 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/*  404 */     return toreturn.toString(); }
/*      */   
/*  406 */   long j = 0L;
/*      */   
/*      */   public Properties getStatus(List entitylist) {
/*  409 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  410 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  412 */       String entityStr = (String)keys.nextElement();
/*  413 */       String mmessage = temp.getProperty(entityStr);
/*  414 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  415 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  417 */     return temp;
/*      */   }
/*      */   
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  422 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  423 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  425 */       String entityStr = (String)keys.nextElement();
/*  426 */       String mmessage = temp.getProperty(entityStr);
/*  427 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  428 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  430 */     return temp;
/*      */   }
/*      */   
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString) {
/*  434 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity)
/*      */   {
/*  439 */     if (severity == null)
/*      */     {
/*  441 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  443 */     if (severity.equals("5"))
/*      */     {
/*  445 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  447 */     if (severity.equals("1"))
/*      */     {
/*  449 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  454 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  461 */     if (severity == null)
/*      */     {
/*  463 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  465 */     if (severity.equals("5"))
/*      */     {
/*  467 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  469 */     if (severity.equals("4"))
/*      */     {
/*  471 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  473 */     if (severity.equals("1"))
/*      */     {
/*  475 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  480 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  485 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  491 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  492 */   static { _jspx_dependants.put("/jsp/groupviewutil.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private ExpressionFactory _el_expressionfactory;
/*      */   private InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*  507 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  511 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  512 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  513 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  514 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  515 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  516 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  517 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  518 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  519 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  523 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  524 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  525 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  526 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  527 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*  528 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  529 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  536 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  539 */     JspWriter out = null;
/*  540 */     Object page = this;
/*  541 */     JspWriter _jspx_out = null;
/*  542 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  546 */       response.setContentType("text/html;charset=UTF-8");
/*  547 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/*  549 */       _jspx_page_context = pageContext;
/*  550 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  551 */       ServletConfig config = pageContext.getServletConfig();
/*  552 */       session = pageContext.getSession();
/*  553 */       out = pageContext.getOut();
/*  554 */       _jspx_out = out;
/*      */       
/*  556 */       out.write("<!--$Id$-->\n");
/*      */       
/*  558 */       request.setAttribute("HelpKey", "Monitors Page");
/*      */       
/*  560 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  561 */       Hashtable availabilitykeys = null;
/*  562 */       synchronized (application) {
/*  563 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/*  564 */         if (availabilitykeys == null) {
/*  565 */           availabilitykeys = new Hashtable();
/*  566 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/*  569 */       out.write(10);
/*  570 */       Hashtable healthkeys = null;
/*  571 */       synchronized (application) {
/*  572 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/*  573 */         if (healthkeys == null) {
/*  574 */           healthkeys = new Hashtable();
/*  575 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/*  578 */       out.write(10);
/*  579 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  580 */       out.write(10);
/*  581 */       out.write("\n\n\n\n\n");
/*      */       try
/*      */       {
/*  584 */         String haidInventory = request.getParameter("parents");
/*  585 */         Hashtable ht = (Hashtable)request.getAttribute("childlist");
/*  586 */         String parentmongroups = request.getParameter("parents");
/*  587 */         int pageno = 2;
/*  588 */         if (request.getParameter("pageno") != null) {
/*  589 */           pageno = Integer.parseInt(request.getParameter("pageno")) + 1;
/*      */         }
/*  591 */         int leftpadding = 40;
/*  592 */         int lindx = 1;
/*  593 */         if (request.getParameter("lindx") != null)
/*      */         {
/*  595 */           lindx = Integer.parseInt(request.getParameter("lindx"));
/*      */         }
/*  597 */         leftpadding = Integer.parseInt(request.getParameter("lindx")) * 40;
/*  598 */         boolean showmore = Boolean.parseBoolean((String)request.getAttribute("showmore"));
/*  599 */         String checked = "";
/*  600 */         if ("true".equals(request.getParameter("checked"))) {
/*  601 */           checked = "checked=\"checked\"";
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  606 */         out.write(10);
/*  607 */         out.write(10);
/*      */         
/*  609 */         String resourceName = "";
/*  610 */         String customValue = request.getParameter("customValue");
/*      */         
/*  612 */         String viewtype = (String)request.getAttribute("viewtype");
/*  613 */         Hashtable chilmos = (Hashtable)request.getAttribute("childlist");
/*  614 */         MyFields fields = new MyFields();
/*  615 */         int displayNameindex = 1;
/*  616 */         int actionIndex = 6;
/*  617 */         int unmanagestatusindex = 5;
/*  618 */         int enableindex = 7;
/*  619 */         int resourceTypeindex = 2;
/*  620 */         int residIndex = 0;
/*  621 */         int haiGroupTypeIndex = 11;
/*  622 */         String healthid = "18";
/*  623 */         String availid = "17";
/*      */         
/*  625 */         String forInventory = (String)request.getAttribute("forInventory");
/*  626 */         boolean isInventory = false;
/*  627 */         String img = "";
/*  628 */         String isopen = "false";
/*  629 */         ArrayList entitylist = new ArrayList();
/*  630 */         ArrayList tempresourelist = new ArrayList();
/*  631 */         ArrayList eumChildList = com.adventnet.appmanager.util.Constants.getEUMChildList();
/*  632 */         HashMap extDeviceMap = null;
/*  633 */         HashMap site24x7List = null;
/*  634 */         if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured()) {
/*      */           try
/*      */           {
/*  637 */             extDeviceMap = IntegProdDBUtil.getExtAllDevicesLink(false);
/*  638 */             if (OEMUtil.isRemove("enable.proxyForExtProd"))
/*      */             {
/*      */ 
/*      */ 
/*  642 */               extDeviceMap = ExtProdUtil.getDeviceLinksOfExtProduct("OpManager", false, false);
/*      */             }
/*  644 */             site24x7List = com.adventnet.appmanager.util.DBUtil.getAllsite24x7MonitorsLink();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  648 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*  652 */         if ((haidInventory != null) && (haidInventory.contains("|"))) {
/*  653 */           haidInventory = parentmongroups.substring(parentmongroups.lastIndexOf("|") + 1, parentmongroups.length());
/*      */         }
/*  655 */         isInventory = true;
/*  656 */         ArrayList singlechilmos1 = (ArrayList)chilmos.get(haidInventory);
/*  657 */         if (singlechilmos1 != null)
/*      */         {
/*  659 */           request.setAttribute("applications", singlechilmos1);
/*  660 */           haiGroupTypeIndex = 9;
/*      */         }
/*      */         else
/*      */         {
/*  664 */           request.setAttribute("applications", new ArrayList());
/*      */         }
/*  666 */         if ("true".equalsIgnoreCase(request.getParameter("alllevels")))
/*      */         {
/*  668 */           isopen = "true";
/*      */         }
/*      */         
/*  671 */         out.write(10);
/*  672 */         out.write(10);
/*      */         
/*  674 */         IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  675 */         _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  676 */         _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */         
/*  678 */         _jspx_th_logic_005fiterate_005f0.setName("applications");
/*      */         
/*  680 */         _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */         
/*  682 */         _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*      */         
/*  684 */         _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*  685 */         int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  686 */         if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  687 */           ArrayList row = null;
/*  688 */           Integer i = null;
/*  689 */           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  690 */             out = _jspx_page_context.pushBody();
/*  691 */             _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/*  692 */             _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */           }
/*  694 */           row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  695 */           i = (Integer)_jspx_page_context.findAttribute("i");
/*      */           for (;;) {
/*  697 */             out.write(10);
/*      */             
/*  699 */             if (!((String)row.get(residIndex)).equals("orphaned"))
/*      */             {
/*  701 */               String parentResourceType = (String)row.get(resourceTypeindex);
/*  702 */               availid = (String)healthkeys.get(parentResourceType);
/*  703 */               healthid = (String)availabilitykeys.get(parentResourceType);
/*  704 */               entitylist.add((String)row.get(residIndex) + "_" + availid);
/*  705 */               entitylist.add((String)row.get(residIndex) + "_" + healthid);
/*  706 */               if ((!"HAI".equals(parentResourceType)) && (!tempresourelist.contains(row.get(residIndex))))
/*      */               {
/*  708 */                 tempresourelist.add(row.get(residIndex));
/*      */               }
/*      */             }
/*      */             try
/*      */             {
/*  713 */               ArrayList srow = (ArrayList)chilmos.get((String)row.get(residIndex) + "");
/*  714 */               if (srow != null)
/*      */               {
/*  716 */                 for (int k = 0; k < srow.size(); k++)
/*      */                 {
/*  718 */                   ArrayList mo = (ArrayList)srow.get(k);
/*  719 */                   if ((mo != null) && (mo.get(0) != null) && (!((String)mo.get(0)).equals("null")))
/*      */                   {
/*  721 */                     String cresid = ((String)mo.get(0) + "").trim();
/*  722 */                     if (!eumChildList.contains(cresid))
/*      */                     {
/*      */ 
/*      */ 
/*  726 */                       String resourceType = ((String)mo.get(2) + "").trim();
/*  727 */                       String healthkey = AMAttributesCache.getHealthId(resourceType);
/*  728 */                       if ((healthkey != null) && (!entitylist.contains(cresid + "_" + healthkey)))
/*      */                       {
/*  730 */                         entitylist.add(cresid + "_" + healthkey);
/*      */                       }
/*  732 */                       String availabilitykey = AMAttributesCache.getAvailabilityId(resourceType);
/*  733 */                       if ((!resourceType.equals("HAI")) && (!tempresourelist.contains(cresid)))
/*      */                       {
/*  735 */                         tempresourelist.add(cresid);
/*      */                       }
/*  737 */                       if ((availabilitykey != null) && (!entitylist.contains(cresid + "_" + availabilitykey)))
/*      */                       {
/*  739 */                         entitylist.add(cresid + "_" + availabilitykey);
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception ex) {
/*  747 */               ex.printStackTrace();
/*      */             }
/*      */             
/*  750 */             out.write(10);
/*  751 */             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  752 */             row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  753 */             i = (Integer)_jspx_page_context.findAttribute("i");
/*  754 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*  757 */           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  758 */             out = _jspx_page_context.popBody();
/*      */           }
/*      */         }
/*  761 */         if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  762 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */         }
/*      */         
/*  765 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  766 */         out.write(10);
/*      */         
/*  768 */         Properties alert = getStatus(entitylist);
/*  769 */         request.setAttribute("alert", alert);
/*      */         
/*  771 */         out.write("\n\n\n\n");
/*  772 */         boolean checkRow = false;
/*      */         
/*  774 */         int mgviewlist = 0;
/*  775 */         int monitorlist = 0;
/*  776 */         boolean showeditLink = true;
/*      */         
/*      */ 
/*  779 */         out.write(10);
/*      */         
/*  781 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  782 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  783 */         _jspx_th_c_005fif_005f0.setParent(null);
/*      */         
/*  785 */         _jspx_th_c_005fif_005f0.setTest("${not empty applications}");
/*  786 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  787 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */           for (;;) {
/*  789 */             out.write("\n     \n    ");
/*      */             
/*  791 */             IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  792 */             _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/*  793 */             _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */             
/*  795 */             _jspx_th_logic_005fiterate_005f1.setName("applications");
/*      */             
/*  797 */             _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */             
/*  799 */             _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/*      */             
/*  801 */             _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/*  802 */             int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/*  803 */             if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/*  804 */               ArrayList row = null;
/*  805 */               Integer i = null;
/*  806 */               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/*  807 */                 out = _jspx_page_context.pushBody();
/*  808 */                 _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/*  809 */                 _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */               }
/*  811 */               row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  812 */               i = (Integer)_jspx_page_context.findAttribute("i");
/*      */               for (;;) {
/*  814 */                 out.write("\n    ");
/*      */                 
/*  816 */                 mgviewlist = i.intValue();
/*  817 */                 String resIdTOCheck = null;
/*  818 */                 healthid = "18";
/*  819 */                 availid = "17";
/*  820 */                 String rowID = "";
/*  821 */                 String MGType = row.get(2) != null ? (String)row.get(2) : "";
/*  822 */                 String haiGroupType = "0";
/*  823 */                 if ((row.size() == haiGroupTypeIndex) || (haiGroupTypeIndex == 9))
/*      */                 {
/*  825 */                   haiGroupType = (String)row.get(haiGroupTypeIndex);
/*      */                 }
/*  827 */                 String resourceType = row.get(resourceTypeindex) != null ? (String)row.get(resourceTypeindex) : "";
/*  828 */                 img = "";
/*  829 */                 String isgroup = "true";
/*  830 */                 String dspName = (String)row.get(1);
/*  831 */                 String parentResID = "";
/*  832 */                 String childResName = "";
/*  833 */                 String childDispName = "";
/*  834 */                 String toolTipTitle = "";
/*  835 */                 if (!resourceType.equals("HAI"))
/*      */                 {
/*  837 */                   pageContext.setAttribute("notMG", "true");
/*  838 */                   img = "<img src='" + row.get(3) + "' title='" + row.get(4) + "' align='absmiddle' border='0'/>&nbsp;";
/*  839 */                   healthid = (String)healthkeys.get(resourceType);
/*  840 */                   availid = (String)availabilitykeys.get(resourceType);
/*  841 */                   isgroup = "false";
/*  842 */                   if (ChildMOHandler.isChildMonitorTypeSupportedForMG(resourceType))
/*      */                   {
/*  844 */                     Vector<String> childVec = new Vector();
/*  845 */                     String tempChildId = (String)row.get(residIndex);
/*  846 */                     childVec.add(tempChildId);
/*  847 */                     HashMap<String, HashMap<String, String>> childMonitorInfo = ChildMOHandler.getChildMonitorWithParentInfo(childVec);
/*  848 */                     if (childMonitorInfo != null)
/*      */                     {
/*  850 */                       HashMap<String, String> childInfo = (HashMap)childMonitorInfo.get(tempChildId);
/*  851 */                       if (childInfo != null)
/*      */                       {
/*  853 */                         img = "<img src='" + (String)childInfo.get("imagepath") + "' title='" + row.get(2) + "' align='absmiddle' border='0'/>&nbsp;";
/*  854 */                         dspName = (String)childInfo.get("trimmeddisplayname");
/*  855 */                         toolTipTitle = (String)childInfo.get("displayname");
/*  856 */                         parentResID = (String)childInfo.get("parentResourceid");
/*  857 */                         childDispName = (String)childInfo.get("childDisplayname");
/*  858 */                         childResName = (String)childInfo.get("childDisplayname");
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 else
/*      */                 {
/*  865 */                   pageContext.setAttribute("notMG", "false");
/*      */                 }
/*  867 */                 showeditLink = true;
/*  868 */                 if ((resourceType.equals("HAI")) && (("1009".equals(haiGroupType)) || ("1010".equals(haiGroupType)) || ("3".equals(haiGroupType)))) {
/*  869 */                   showeditLink = false;
/*      */                 }
/*      */                 
/*      */ 
/*  873 */                 if (isInventory)
/*      */                 {
/*  875 */                   MGType = "0";
/*      */                 }
/*      */                 
/*  878 */                 if ((MGType.equals("0")) || (EnterpriseUtil.isIt360MSPEdition()))
/*      */                 {
/*  880 */                   String actionstatus = (String)row.get(actionIndex);
/*  881 */                   String actionimage = "/images/alarm-icon.png";
/*  882 */                   String actionmessage = FormatUtil.getString("Actions Enabled");
/*  883 */                   if ((actionstatus != null) && (actionstatus.equals("0")))
/*      */                   {
/*  885 */                     actionimage = "/images/icon_actions_disabled.gif";
/*  886 */                     actionmessage = FormatUtil.getString("Actions Disabled");
/*      */                   }
/*  888 */                   String restitle = row.get(unmanagestatusindex) != null ? (String)row.get(displayNameindex) + "-" + FormatUtil.getString("UnManaged") : (String)row.get(displayNameindex);
/*  889 */                   if (!toolTipTitle.equals(""))
/*      */                   {
/*  891 */                     restitle = row.get(unmanagestatusindex) != null ? toolTipTitle + "-" + FormatUtil.getString("UnManaged") : toolTipTitle;
/*      */                   }
/*  893 */                   String status = row.get(unmanagestatusindex) != null ? "disabledtext" : "monitorgp-links";
/*  894 */                   resIdTOCheck = "-1";
/*  895 */                   int residint = 1;
/*      */                   try
/*      */                   {
/*  898 */                     resIdTOCheck = (String)row.get(residIndex);
/*  899 */                     residint = Integer.parseInt((String)row.get(residIndex));
/*      */                   }
/*      */                   catch (Exception e) {}
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*  906 */                   out.write(10);
/*      */                   
/*  908 */                   IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  909 */                   _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  910 */                   _jspx_th_c_005fif_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                   
/*  912 */                   _jspx_th_c_005fif_005f1.setTest("${notMG!=\"true\"}");
/*  913 */                   int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  914 */                   if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                     for (;;) {
/*  916 */                       out.write("\n         <tr width=\"100%\" isgroup=\"");
/*  917 */                       out.print(isgroup);
/*  918 */                       out.write("\" id=\"#monitor");
/*  919 */                       out.print(parentmongroups);
/*  920 */                       out.write("\" groupid=\"");
/*  921 */                       out.print(resIdTOCheck);
/*  922 */                       out.write("\" class=\"leftcells monitorgpHeader\" onmouseover=\"this.className='monitorgpHeaderHover'\" onmouseout=\"this.className='monitorgpHeader'\" >\n");
/*  923 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  924 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  928 */                   if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  929 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                   }
/*      */                   
/*  932 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  933 */                   out.write(10);
/*      */                   
/*  935 */                   IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  936 */                   _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  937 */                   _jspx_th_c_005fif_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                   
/*  939 */                   _jspx_th_c_005fif_005f2.setTest("${notMG==\"true\"}");
/*  940 */                   int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  941 */                   if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                     for (;;) {
/*  943 */                       out.write("\n\t\t<tr width=\"100%\" isgroup=\"");
/*  944 */                       out.print(isgroup);
/*  945 */                       out.write("\" id=\"#monitor");
/*  946 */                       out.print(parentmongroups);
/*  947 */                       out.write("\" groupid=\"");
/*  948 */                       out.print(resIdTOCheck);
/*  949 */                       out.write("\" class=\"leftcells bulkmonHeader\" onmouseout=\"this.className='bulkmonHeader'\" onmouseover=\"this.className='bulkmonHeaderHover'\" \n\t\t");
/*      */                       
/*  951 */                       if (ChildMOHandler.isChildMonitorTypeSupportedForMG(resourceType))
/*      */                       {
/*      */ 
/*  954 */                         out.write("\n\t\tischild='true'\n\t\t");
/*      */                       }
/*      */                       
/*      */ 
/*  958 */                       out.write("\n\t\t>\n\t\t");
/*  959 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  960 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  964 */                   if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  965 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                   }
/*      */                   
/*  968 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  969 */                   out.write("\n\t\n\t \n\t\t  ");
/*      */                   
/*  971 */                   if (EnterpriseUtil.isAdminServer())
/*      */                   {
/*  973 */                     String groupName = dspName + "_" + CommDBUtil.getManagedServerNameWithPort((String)row.get(residIndex));
/*      */                     
/*  975 */                     if (!checkRow)
/*      */                     {
/*  977 */                       rowID = "expandRow";
/*  978 */                       checkRow = true;
/*      */                     }
/*      */                     
/*  981 */                     out.write("\n\t <td  class=\"whitegrayrightalign\"  align=\"left\" style=\"padding-left:40px;\"> \n\t\t&nbsp;\n\t</td>\n\t <td class=\"whitegrayrightalign\" align=\"left\" width=\"47%\" nowrap=\"false\" style=\"padding-left:");
/*  982 */                     out.print(leftpadding);
/*  983 */                     out.write("px;\">\n\t <input type=\"checkbox\" name=\"select\" value=\"");
/*  984 */                     out.print(resIdTOCheck);
/*  985 */                     out.write(34);
/*  986 */                     out.write(32);
/*  987 */                     out.print(checked);
/*  988 */                     out.write(" id=\"");
/*  989 */                     out.print(parentmongroups + "|" + resIdTOCheck);
/*  990 */                     out.write("\" grpresid=\"");
/*  991 */                     out.print(resIdTOCheck);
/*  992 */                     out.write("\" onclick=\"\n\t\t   ");
/*      */                     
/*  994 */                     if ("HAI".equals(resourceType)) {
/*  995 */                       out.write("\n\t\t   selectAllChildCKbs('");
/*  996 */                       out.print(parentmongroups + "|" + resIdTOCheck);
/*  997 */                       out.write("',this,this.form),\t\t   \n\t\t  ");
/*      */                     }
/*  999 */                     out.write("\n\t\t   deselectParentCKbs('");
/* 1000 */                     out.print(parentmongroups + "|" + resIdTOCheck);
/* 1001 */                     out.write("',this,this.form);\">\n\t ");
/*      */                     
/* 1003 */                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1004 */                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1005 */                     _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 1007 */                     _jspx_th_c_005fif_005f3.setTest("${notMG!=\"true\"}");
/* 1008 */                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1009 */                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                       for (;;) {
/* 1011 */                         out.write("\t\t    \n\t\t\t<a href=\"javascript:void(0);\" isopen=\"");
/* 1012 */                         out.print(isopen);
/* 1013 */                         out.write("\" id=\"");
/* 1014 */                         out.print(rowID);
/* 1015 */                         out.write("\" class =\"expandlink\" resid=\"");
/* 1016 */                         out.print(resIdTOCheck);
/* 1017 */                         out.write("\" onclick=\"getDynamicCall('/showresource.do?method=showMonitorGroupViewIndividual&parents=");
/* 1018 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1019 */                         out.write("&lindx=");
/* 1020 */                         out.print(lindx + 1);
/* 1021 */                         out.write("',this,'true'),toggleChildMos('#monitor");
/* 1022 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1023 */                         out.write("',this,'true'),\t\t\t\n\t\t\ttoggleTreeImage('");
/* 1024 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1025 */                         out.write("');\">\n\t\t\t<div id=\"monitorShow");
/* 1026 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1027 */                         out.write("\" style=\"display:inline;\"><img hspace=\"5\" border=\"0\" src=\"/images/icon_plus.gif\"></div>\t\t\n\t\t    <div id=\"monitorHide");
/* 1028 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1029 */                         out.write("\" style=\"display:none;\"><img  hspace=\"5\" border=\"0\" src=\"/images/icon_minus.gif\"></div> \t\t\t\n\t\t\t </a>\n\t\t");
/* 1030 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1031 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1035 */                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1036 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                     }
/*      */                     
/* 1039 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1040 */                     out.write(10);
/* 1041 */                     out.write(9);
/* 1042 */                     out.write(9);
/* 1043 */                     if (_jspx_meth_c_005fif_005f4(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                       return;
/* 1045 */                     out.write("\n      <img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"> \t \n\t   ");
/* 1046 */                     out.print(img);
/* 1047 */                     out.write("\n\t   \n\t   \n\t   ");
/*      */                     
/* 1049 */                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1050 */                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1051 */                     _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 1053 */                     _jspx_th_c_005fif_005f5.setTest("${notMG!=\"true\"}");
/* 1054 */                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1055 */                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                       for (;;) {
/* 1057 */                         out.write("\n\t   <a href=\"/showapplication.do?haid=");
/* 1058 */                         out.print(row.get(residIndex));
/* 1059 */                         out.write("&method=showApplication\" class=\"");
/* 1060 */                         out.print(status);
/* 1061 */                         out.write(34);
/* 1062 */                         out.write(62);
/* 1063 */                         out.print(getTrimmedText(dspName, 35));
/* 1064 */                         out.write("</a>\n\t   ");
/* 1065 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1066 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1070 */                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1071 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                     }
/*      */                     
/* 1074 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1075 */                     out.write("\n\t   ");
/*      */                     
/* 1077 */                     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1078 */                     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1079 */                     _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 1081 */                     _jspx_th_c_005fif_005f6.setTest("${notMG==\"true\"}");
/* 1082 */                     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1083 */                     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                       for (;;) {
/* 1085 */                         out.write("\n\t   ");
/* 1086 */                         if ((site24x7List != null) && (site24x7List.containsKey(row.get(residIndex)))) {
/* 1087 */                           out.write("\n\t\t    <a href=\"javascript:MM_openBrWindow('");
/* 1088 */                           out.print(URLEncoder.encode((String)site24x7List.get(row.get(residIndex))));
/* 1089 */                           out.write("','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 1090 */                           out.print(status);
/* 1091 */                           out.write(34);
/* 1092 */                           out.write(62);
/* 1093 */                           out.print(row.get(1));
/* 1094 */                           out.write("</a>\n\t\t");
/*      */                         }
/* 1096 */                         else if ((extDeviceMap != null) && (extDeviceMap.get(row.get(residIndex)) != null))
/*      */                         {
/* 1098 */                           String link1 = (String)extDeviceMap.get(row.get(residIndex));
/* 1099 */                           showeditLink = false;
/* 1100 */                           if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                           {
/* 1102 */                             out.write("\n\t\t\t  <a href=");
/* 1103 */                             out.print(link1);
/* 1104 */                             out.write("  class=\"");
/* 1105 */                             out.print(status);
/* 1106 */                             out.write("\">  ");
/* 1107 */                             out.print(getTrimmedText((String)row.get(1), 45));
/* 1108 */                             out.write("</a>\n\t\t ");
/*      */                           }
/*      */                           else
/*      */                           {
/* 1112 */                             out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('");
/* 1113 */                             out.print(link1);
/* 1114 */                             out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 1115 */                             out.print(status);
/* 1116 */                             out.write(34);
/* 1117 */                             out.write(62);
/* 1118 */                             out.print(getTrimmedText((String)row.get(1), 45));
/* 1119 */                             out.write("</a>\n\t\t ");
/*      */                           }
/*      */                         }
/* 1122 */                         else if (ChildMOHandler.isChildMonitorTypeSupportedForMG(resourceType))
/*      */                         {
/* 1124 */                           String link1 = "/showapplication.do?method=showChildApplicationDetail&resId=" + (String)row.get(residIndex);
/*      */                           
/* 1126 */                           out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('");
/* 1127 */                           out.print(link1);
/* 1128 */                           out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 1129 */                           out.print(status);
/* 1130 */                           out.write(34);
/* 1131 */                           out.write(62);
/* 1132 */                           out.print(dspName);
/* 1133 */                           out.write("</a>\n\t\t");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/* 1138 */                           out.write("  \n\t  <a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 1139 */                           out.print(row.get(residIndex));
/* 1140 */                           out.write("&haid=");
/* 1141 */                           out.print(haidInventory);
/* 1142 */                           out.write("\" \n\t  class=\"");
/* 1143 */                           out.print(status);
/* 1144 */                           out.write(34);
/* 1145 */                           out.write(62);
/* 1146 */                           out.print(row.get(1));
/* 1147 */                           out.write("</a>\n\t  ");
/*      */                         }
/* 1149 */                         out.write(10);
/* 1150 */                         out.write(9);
/* 1151 */                         out.write(9);
/* 1152 */                         if (Integer.parseInt((String)row.get(residIndex)) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE)
/*      */                         {
/*      */ 
/* 1155 */                           out.write("\n\t    ");
/* 1156 */                           if ((site24x7List != null) && (site24x7List.containsKey(row.get(residIndex)))) {
/* 1157 */                             out.write("\n\t\t    <a href=\"javascript:MM_openBrWindow('");
/* 1158 */                             out.print(URLEncoder.encode((String)site24x7List.get(row.get(residIndex))));
/* 1159 */                             out.write("','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 1160 */                             out.print(status);
/* 1161 */                             out.write(34);
/* 1162 */                             out.write(62);
/* 1163 */                             out.print(row.get(1));
/* 1164 */                             out.write("</a>\n\t\t");
/*      */                           }
/* 1166 */                           else if ((extDeviceMap != null) && (extDeviceMap.get(row.get(residIndex)) != null))
/*      */                           {
/* 1168 */                             String link1 = (String)extDeviceMap.get(row.get(residIndex));
/* 1169 */                             showeditLink = false;
/* 1170 */                             if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                             {
/* 1172 */                               out.write("\n\t\t\t  <a href=");
/* 1173 */                               out.print(link1);
/* 1174 */                               out.write("  class=\"");
/* 1175 */                               out.print(status);
/* 1176 */                               out.write("\">  <img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a> \n\t\t ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 1180 */                               out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('");
/* 1181 */                               out.print(link1);
/* 1182 */                               out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 1183 */                               out.print(status);
/* 1184 */                               out.write("\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a> \n\t\t ");
/*      */                             }
/*      */                           }
/*      */                           else {
/* 1188 */                             out.write(" \n\t   <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 1189 */                             out.print(row.get(residIndex));
/* 1190 */                             out.write("&type=");
/* 1191 */                             out.print(resourceType);
/* 1192 */                             out.write("&moname=");
/* 1193 */                             out.print(row.get(1));
/* 1194 */                             out.write("&resourcename=");
/* 1195 */                             out.print(row.get(1));
/* 1196 */                             out.write("&method=showdetails&aam_jump=true&useHTTP=");
/* 1197 */                             out.print(!com.adventnet.appmanager.util.Constants.isIt360);
/* 1198 */                             out.write("\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>  \n\t  ");
/*      */                           }
/*      */                         }
/*      */                         
/*      */ 
/* 1203 */                         out.write("\n\t  \n\t   ");
/* 1204 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1205 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1209 */                     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1210 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                     }
/*      */                     
/* 1213 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1214 */                     out.write(" \n\t   \n\t    \n\t  </td>\n\t  \n      ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/* 1219 */                     if (!checkRow) {
/* 1220 */                       rowID = "expandRow";
/* 1221 */                       checkRow = true;
/*      */                     }
/*      */                     
/* 1224 */                     out.write("\n\t <td  class=\"whitegrayrightalign\"  align=\"left\"  >&nbsp;\n\t\t\n\t</td>\n \n\t <td  class=\"whitegrayrightalign\"  align=\"left\"   title=\"");
/* 1225 */                     out.print(restitle);
/* 1226 */                     out.write("\" style=\"padding-left:");
/* 1227 */                     out.print(leftpadding);
/* 1228 */                     out.write("px;\">\n\t ");
/*      */                     
/* 1230 */                     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1231 */                     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 1232 */                     _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 1234 */                     _jspx_th_c_005fif_005f7.setTest("${notMG!=\"true\"}");
/* 1235 */                     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1236 */                     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                       for (;;) {
/* 1238 */                         out.write("\n\t\n\t\t<a href=\"javascript:void(0);\" isopen=\"");
/* 1239 */                         out.print(isopen);
/* 1240 */                         out.write("\" id=\"");
/* 1241 */                         out.print(rowID);
/* 1242 */                         out.write("\" class =\"expandlink\" resid=\"");
/* 1243 */                         out.print(resIdTOCheck);
/* 1244 */                         out.write("\" onclick=\"getDynamicCall('/showresource.do?method=showMonitorGroupViewIndividual&parents=");
/* 1245 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1246 */                         out.write("&lindx=");
/* 1247 */                         out.print(lindx + 1);
/* 1248 */                         out.write("',this,'true'),\n\t\ttoggleChildMos('#monitor");
/* 1249 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1250 */                         out.write("',this),\n\t\ttoggleTreeImage('");
/* 1251 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1252 */                         out.write("');\">\t\n\t\t<div id=\"monitorShow");
/* 1253 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1254 */                         out.write("\" style=\"display:inline;\"><img hspace=\"5\" border=\"0\" src=\"/images/icon_plus.gif\"></div>\t\t\n\t\t<div id=\"monitorHide");
/* 1255 */                         out.print(parentmongroups + "|" + resIdTOCheck);
/* 1256 */                         out.write("\" style=\"display:none;\"><img  hspace=\"5\" border=\"0\" src=\"/images/icon_minus.gif\"></div>\n\t\t </a> \n        ");
/* 1257 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1258 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1262 */                     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1263 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                     }
/*      */                     
/* 1266 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1267 */                     out.write(10);
/* 1268 */                     out.write(9);
/* 1269 */                     out.write(32);
/* 1270 */                     if (_jspx_meth_c_005fif_005f8(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*      */                       return;
/* 1272 */                     out.write("\t \n\t \t");
/* 1273 */                     if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 1274 */                       out.write("\n\t\t  ");
/*      */                       
/* 1276 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1277 */                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 1278 */                       _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                       
/* 1280 */                       _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 1281 */                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 1282 */                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                         for (;;) {
/* 1284 */                           out.write("\n\t\t   <input type=\"checkbox\" name=\"select\" value=\"");
/* 1285 */                           out.print(resIdTOCheck);
/* 1286 */                           out.write(34);
/* 1287 */                           out.write(32);
/* 1288 */                           out.print(checked);
/* 1289 */                           out.write(" id=\"");
/* 1290 */                           out.print(parentmongroups + "|" + resIdTOCheck);
/* 1291 */                           out.write("\" grpresid=\"");
/* 1292 */                           out.print(resIdTOCheck);
/* 1293 */                           out.write("\" onclick=\"\n\t\t   ");
/*      */                           
/* 1295 */                           if ("HAI".equals(resourceType)) {
/* 1296 */                             out.write("\n\t\t   selectAllChildCKbs('");
/* 1297 */                             out.print(parentmongroups + "|" + resIdTOCheck);
/* 1298 */                             out.write("',this,this.form),\t\t   \n\t\t  ");
/*      */                           }
/* 1300 */                           out.write("\n\t\t   deselectParentCKbs('");
/* 1301 */                           out.print(parentmongroups + "|" + resIdTOCheck);
/* 1302 */                           out.write("',this,this.form);\">\n\t\t   ");
/* 1303 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 1304 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1308 */                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 1309 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                       }
/*      */                       
/* 1312 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1313 */                       out.write("\n\t\t   ");
/*      */                     }
/* 1315 */                     out.write(10);
/* 1316 */                     out.write(9);
/* 1317 */                     out.write(9);
/* 1318 */                     out.print(img);
/* 1319 */                     out.write("\n\t\t\n\t\t\n\t\t");
/* 1320 */                     if ((site24x7List != null) && (site24x7List.containsKey(row.get(residIndex)))) {
/* 1321 */                       out.write("\n\t\t    <a href=\"javascript:MM_openBrWindow('");
/* 1322 */                       out.print(URLEncoder.encode((String)site24x7List.get(row.get(residIndex))));
/* 1323 */                       out.write("','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 1324 */                       out.print(status);
/* 1325 */                       out.write(34);
/* 1326 */                       out.write(62);
/* 1327 */                       out.print(row.get(1));
/* 1328 */                       out.write("</a>\n\t\t");
/*      */                     }
/* 1330 */                     else if ((extDeviceMap != null) && (extDeviceMap.get(row.get(residIndex)) != null))
/*      */                     {
/* 1332 */                       String link1 = (String)extDeviceMap.get(row.get(residIndex));
/* 1333 */                       showeditLink = false;
/* 1334 */                       if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                       {
/* 1336 */                         out.write("\n\t\t\t  <a href=");
/* 1337 */                         out.print(link1);
/* 1338 */                         out.write("  class=\"");
/* 1339 */                         out.print(status);
/* 1340 */                         out.write("\">  ");
/* 1341 */                         out.print(getTrimmedText((String)row.get(1), 45));
/* 1342 */                         out.write("</a>\";\n\t\t ");
/*      */                       }
/*      */                       else
/*      */                       {
/* 1346 */                         out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('");
/* 1347 */                         out.print(link1);
/* 1348 */                         out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 1349 */                         out.print(status);
/* 1350 */                         out.write(34);
/* 1351 */                         out.write(62);
/* 1352 */                         out.print(dspName);
/* 1353 */                         out.write("</a>\n\t\t ");
/*      */                       }
/*      */                     }
/* 1356 */                     else if (ChildMOHandler.isChildMonitorTypeSupportedForMG(resourceType))
/*      */                     {
/* 1358 */                       String link1 = "/showapplication.do?method=showChildApplicationDetail&resId=" + (String)row.get(residIndex);
/*      */                       
/* 1360 */                       out.write("\n\t\t <a href=\"javascript:MM_openBrWindow('");
/* 1361 */                       out.print(link1);
/* 1362 */                       out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"");
/* 1363 */                       out.print(status);
/* 1364 */                       out.write(34);
/* 1365 */                       out.write(62);
/* 1366 */                       out.print(getTrimmedText(dspName, 45));
/* 1367 */                       out.write("</a>\n\t\t ");
/*      */                     }
/*      */                     else {
/* 1370 */                       out.write(" \n\t\t\t<a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 1371 */                       out.print(row.get(residIndex));
/* 1372 */                       out.write("&haid=");
/* 1373 */                       out.print(haidInventory);
/* 1374 */                       out.write("\" class=\"");
/* 1375 */                       out.print(status);
/* 1376 */                       out.write(34);
/* 1377 */                       out.write(62);
/* 1378 */                       out.print(row.get(1));
/* 1379 */                       out.write("</a>\n\t\t\t\t\t");
/*      */                     }
/* 1381 */                     out.write(" \n\t\t\t\n\t\t\t\n      ");
/*      */                   }
/*      */                   
/*      */ 
/* 1385 */                   out.write(10);
/* 1386 */                   out.write(32);
/*      */                   
/* 1388 */                   String editlink = "/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + resIdTOCheck;
/*      */                   
/* 1390 */                   out.write(10);
/* 1391 */                   out.write(9);
/* 1392 */                   out.write(9);
/*      */                   
/* 1394 */                   IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1395 */                   _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 1396 */                   _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                   
/* 1398 */                   _jspx_th_c_005fif_005f9.setTest("${forInventory==\"true\"}");
/* 1399 */                   int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 1400 */                   if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                     for (;;) {
/* 1402 */                       out.write("\n\t\t\t");
/*      */                       
/* 1404 */                       editlink = "/showapplication.do?method=editApplication&haid=" + resIdTOCheck;
/*      */                       
/* 1406 */                       out.write(10);
/* 1407 */                       out.write(9);
/* 1408 */                       out.write(9);
/* 1409 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 1410 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1414 */                   if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 1415 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                   }
/*      */                   
/* 1418 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 1419 */                   out.write(10);
/* 1420 */                   out.write(9);
/* 1421 */                   out.write(9);
/*      */                   
/* 1423 */                   IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1424 */                   _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 1425 */                   _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                   
/* 1427 */                   _jspx_th_c_005fif_005f10.setTest("${notMG==\"true\"}");
/* 1428 */                   int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 1429 */                   if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                     for (;;) {
/* 1431 */                       out.write(10);
/* 1432 */                       out.write(9);
/* 1433 */                       out.write(9);
/* 1434 */                       editlink = "/showresource.do?method=showResourceForResourceID&editPage=true&resourceid=" + resIdTOCheck;
/*      */                       
/* 1436 */                       out.write("\n                ");
/* 1437 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 1438 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1442 */                   if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 1443 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                   }
/*      */                   
/* 1446 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1447 */                   out.write("\n\n\t\t");
/* 1448 */                   if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 1449 */                     out.write("\n         <td  class=\"whitegrayrightalign\"   align=\"left\" ><table><tr class='whitegrayrightalign'><td>\n               <div id=\"mgaction\" style=\"display:none\" class=\"actionborder\"> \n\t\t");
/*      */                     
/* 1451 */                     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1452 */                     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1453 */                     _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 1455 */                     _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 1456 */                     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1457 */                     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                       for (;;) {
/* 1459 */                         out.write(10);
/* 1460 */                         out.write(9);
/* 1461 */                         out.write(9);
/*      */                         
/* 1463 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1464 */                         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 1465 */                         _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                         
/* 1467 */                         _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO,ENTERPRISEADMIN");
/* 1468 */                         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 1469 */                         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                           for (;;) {
/* 1471 */                             out.write(10);
/* 1472 */                             out.write(9);
/* 1473 */                             out.write(9);
/* 1474 */                             if ((showeditLink) && ((site24x7List == null) || (!site24x7List.containsKey(row.get(residIndex))))) {
/* 1475 */                               out.write(10);
/* 1476 */                               out.write(9);
/* 1477 */                               out.write(9);
/*      */                               
/* 1479 */                               IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1480 */                               _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 1481 */                               _jspx_th_c_005fif_005f11.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                               
/* 1483 */                               _jspx_th_c_005fif_005f11.setTest("${notMG!=\"true\"}");
/* 1484 */                               int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 1485 */                               if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                 for (;;) {
/* 1487 */                                   out.write("\n\t\t\t");
/* 1488 */                                   editlink = "/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + row.get(residIndex);
/* 1489 */                                   out.write(10);
/* 1490 */                                   out.write(9);
/* 1491 */                                   out.write(9);
/* 1492 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 1493 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1497 */                               if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 1498 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                               }
/*      */                               
/* 1501 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1502 */                               out.write(10);
/* 1503 */                               out.write(9);
/* 1504 */                               out.write(9);
/*      */                               
/* 1506 */                               IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1507 */                               _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 1508 */                               _jspx_th_c_005fif_005f12.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                               
/* 1510 */                               _jspx_th_c_005fif_005f12.setTest("${notMG==\"true\"}");
/* 1511 */                               int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 1512 */                               if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                 for (;;) {
/* 1514 */                                   out.write("\t\n\t\t\t");
/*      */                                   
/* 1516 */                                   String resourceTypeToSet = (String)row.get(2);
/* 1517 */                                   if (ConfMonitorConfiguration.getInstance().isConfMonitor(resourceTypeToSet))
/*      */                                   {
/* 1519 */                                     editlink = "/manageConfMons.do?haid=" + haidInventory + "&method=editMonitor&resourceid=" + row.get(residIndex) + "&type=" + resourceTypeToSet + "&resourcename=" + row.get(1);
/*      */                                   }
/* 1521 */                                   else if ((new PreConfMonitorXMLParser().getPreConfMonitorListSupported().containsKey(resourceTypeToSet)) || (new PreConfMonitorXMLParser().getPreConfMonitorListSupported().containsValue(resourceTypeToSet)))
/*      */                                   {
/* 1523 */                                     if (resourceTypeToSet.indexOf("Windows") != -1)
/* 1524 */                                       resourceTypeToSet = "Windows";
/* 1525 */                                     editlink = "/manageConfMons.do?haid=" + haidInventory + "&method=editPreConfMonitor&resourceid=" + row.get(residIndex) + "&type=" + resourceTypeToSet + "&resourcename=" + row.get(1);
/*      */                                   }
/* 1527 */                                   else if ((ChildMOHandler.isChildMonitorTypeSupportedForMG(resourceTypeToSet)) && (resourceTypeToSet.equals("Service")))
/*      */                                   {
/* 1529 */                                     editlink = "/HostResource.do?actiononServices=Edit&serviceid=" + row.get(residIndex) + "&resourceid=" + parentResID + "&displayname=" + childDispName + "&servicename=" + childResName + "&name=&haid=&appName=";
/*      */                                   }
/* 1531 */                                   else if ((ChildMOHandler.isChildMonitorTypeSupportedForMG(resourceTypeToSet)) && (resourceTypeToSet.equals("Process")))
/*      */                                   {
/* 1533 */                                     editlink = "/HostResource.do?editProcess=true&processid=" + row.get(residIndex) + "&resourceid=" + parentResID + "&disableEdit=false&haid=&appName=";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 1537 */                                     editlink = "/showresource.do?haid=" + haidInventory + "&resourceid=" + row.get(residIndex) + "&resourcename=" + row.get(1) + "&type=" + resourceTypeToSet + "&method=showdetails&editPage=true&moname=" + row.get(1);
/*      */                                   }
/*      */                                   
/* 1540 */                                   out.write("\n\t\t ");
/* 1541 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 1542 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1546 */                               if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 1547 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                               }
/*      */                               
/* 1550 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 1551 */                               out.write("\t\t\n\t\t    ");
/*      */                               
/* 1553 */                               AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1554 */                               _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 1555 */                               _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                               
/* 1557 */                               _jspx_th_am_005fadminlink_005f0.setHref(editlink);
/*      */                               
/* 1559 */                               _jspx_th_am_005fadminlink_005f0.setEnableClass("monitorgp-links");
/* 1560 */                               int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 1561 */                               if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 1562 */                                 if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 1563 */                                   out = _jspx_page_context.pushBody();
/* 1564 */                                   _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 1565 */                                   _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 1568 */                                   out.write("<img src=\"/images/icon_edit.gif\" align=\"center\" border=\"0\"  title=\"");
/* 1569 */                                   out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 1570 */                                   out.write(34);
/* 1571 */                                   out.write(47);
/* 1572 */                                   out.write(62);
/* 1573 */                                   int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 1574 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 1577 */                                 if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 1578 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 1581 */                               if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 1582 */                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                               }
/*      */                               
/* 1585 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 1586 */                               out.write(32);
/* 1587 */                               out.write("\t\t\n\t\t");
/*      */                             }
/* 1589 */                             out.write("\n\t\t&nbsp;\t\t   \n\t\t   ");
/* 1590 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 1591 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1595 */                         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 1596 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                         }
/*      */                         
/* 1599 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 1600 */                         out.write(10);
/* 1601 */                         out.write(9);
/* 1602 */                         out.write(9);
/* 1603 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1604 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1608 */                     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1609 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                     }
/*      */                     
/* 1612 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1613 */                     out.write("\n\t\t   \n\t\t");
/*      */                     
/* 1615 */                     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1616 */                     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1617 */                     _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 1619 */                     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 1620 */                     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1621 */                     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                       for (;;) {
/* 1623 */                         out.write(10);
/* 1624 */                         out.write(9);
/* 1625 */                         out.write(9);
/*      */                         
/* 1627 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1628 */                         _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 1629 */                         _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                         
/* 1631 */                         _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO,ENTERPRISEADMIN");
/* 1632 */                         int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 1633 */                         if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                           for (;;) {
/* 1635 */                             out.write("\n\t\t    ");
/*      */                             
/* 1637 */                             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1638 */                             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1639 */                             _jspx_th_c_005fif_005f13.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                             
/* 1641 */                             _jspx_th_c_005fif_005f13.setTest("${notMG!=\"true\"}");
/* 1642 */                             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1643 */                             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                               for (;;) {
/* 1645 */                                 out.write("\n                     <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 1646 */                                 out.print(resIdTOCheck);
/* 1647 */                                 out.write("\"> <img src=\" /images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"");
/* 1648 */                                 out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 1649 */                                 out.write("\"/></a>\n\t\t     ");
/* 1650 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1651 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1655 */                             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1656 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                             }
/*      */                             
/* 1659 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1660 */                             out.write("\n\t\t    ");
/*      */                             
/* 1662 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1663 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1664 */                             _jspx_th_c_005fif_005f14.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                             
/* 1666 */                             _jspx_th_c_005fif_005f14.setTest("${notMG==\"true\"}");
/* 1667 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1668 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/* 1670 */                                 out.write("\n\t\t      <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=");
/* 1671 */                                 out.print(resIdTOCheck);
/* 1672 */                                 out.write("\"> <img src=\" /images/icon_associateaction.gif\" border=\"0\" align=\"center\" title=\"");
/* 1673 */                                 out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 1674 */                                 out.write("\"/></a>\n                     ");
/* 1675 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1676 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1680 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1681 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                             }
/*      */                             
/* 1684 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1685 */                             out.write("\n           &nbsp;          \n\t\t ");
/* 1686 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 1687 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1691 */                         if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 1692 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                         }
/*      */                         
/* 1695 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 1696 */                         out.write(10);
/* 1697 */                         out.write(9);
/* 1698 */                         out.write(9);
/* 1699 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1700 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1704 */                     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1705 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                     }
/*      */                     
/* 1708 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1709 */                     out.write("\n\t\t\n\t\t");
/*      */                     
/* 1711 */                     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1712 */                     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 1713 */                     _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 1715 */                     _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 1716 */                     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 1717 */                     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                       for (;;) {
/* 1719 */                         out.write("\n\t\t ");
/*      */                         
/* 1721 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1722 */                         _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 1723 */                         _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                         
/* 1725 */                         _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO,ENTERPRISEADMIN");
/* 1726 */                         int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 1727 */                         if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                           for (;;) {
/* 1729 */                             out.write("\n\t\t    ");
/*      */                             
/* 1731 */                             IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1732 */                             _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 1733 */                             _jspx_th_c_005fif_005f15.setParent(_jspx_th_logic_005fnotPresent_005f3);
/*      */                             
/* 1735 */                             _jspx_th_c_005fif_005f15.setTest("${forInventory==\"true\"}");
/* 1736 */                             int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 1737 */                             if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                               for (;;) {
/* 1739 */                                 out.write("\n                     <a href=\"javascript: removeMonitorFromGroup ('");
/* 1740 */                                 if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                   return;
/* 1742 */                                 out.write(39);
/* 1743 */                                 out.write(44);
/* 1744 */                                 out.write(39);
/* 1745 */                                 out.print(resIdTOCheck);
/* 1746 */                                 out.write(39);
/* 1747 */                                 out.write(44);
/* 1748 */                                 out.write(39);
/* 1749 */                                 if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                   return;
/* 1751 */                                 out.write("');\"> <img src=\"/images/icon_removefromgroup.gif\" align=\"center\" border=\"0\" title=\"");
/* 1752 */                                 out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 1753 */                                 out.write("\"/></a>");
/* 1754 */                                 out.write("\n\t\t\t&nbsp;\n                    ");
/* 1755 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 1756 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1760 */                             if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 1761 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                             }
/*      */                             
/* 1764 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 1765 */                             out.write("\n\t\t ");
/* 1766 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 1767 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1771 */                         if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 1772 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                         }
/*      */                         
/* 1775 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 1776 */                         out.write("\n                ");
/* 1777 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 1778 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1782 */                     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 1783 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                     }
/*      */                     
/* 1786 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1787 */                     out.write("\n\t\n\t\t");
/*      */                     
/* 1789 */                     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1790 */                     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 1791 */                     _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 1793 */                     _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 1794 */                     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 1795 */                     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                       for (;;) {
/* 1797 */                         out.write(10);
/* 1798 */                         out.write(9);
/* 1799 */                         out.write(9);
/*      */                         
/* 1801 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1802 */                         _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 1803 */                         _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                         
/* 1805 */                         _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO,ENTERPRISEADMIN");
/* 1806 */                         int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 1807 */                         if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                           for (;;) {
/* 1809 */                             out.write("\n\t       ");
/*      */                             
/* 1811 */                             if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                             {
/*      */ 
/* 1814 */                               out.write("\n\t\t    <a href=\"/it360/createBS.do?method=getNetworkDevices&productName=OpManager&haid=");
/* 1815 */                               out.print(resIdTOCheck);
/* 1816 */                               out.write("\" class=\"monitorgp-links\" ><img src=\" /images/icon_assoicatemonitors.gif\" border=\"0\" align=\"center\"  title=\"");
/* 1817 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text"));
/* 1818 */                               out.write("\"/></a>\n\t       ");
/*      */ 
/*      */                             }
/* 1821 */                             else if (("HAI".equals(resourceType)) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)))
/*      */                             {
/*      */ 
/* 1824 */                               out.write("\n\t\t    <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=");
/* 1825 */                               out.print(resIdTOCheck);
/* 1826 */                               out.write("&fromwhere=monitorgroupview\" class=\"monitorgp-links\" ><img src=\" /images/icon_assoicatemonitors.gif\" align=\"center\" border=\"0\"  title=\"");
/* 1827 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text"));
/* 1828 */                               out.write("\"/></a>\n\t       ");
/*      */                             }
/*      */                             
/*      */ 
/* 1832 */                             out.write("\n\t       &nbsp;\n\t\t    ");
/* 1833 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 1834 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1838 */                         if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 1839 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                         }
/*      */                         
/* 1842 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 1843 */                         out.write("\n                ");
/* 1844 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 1845 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1849 */                     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 1850 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                     }
/*      */                     
/* 1853 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 1854 */                     out.write("\n                \t\t\n\t\t\t");
/*      */                     
/* 1856 */                     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1857 */                     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 1858 */                     _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 1860 */                     _jspx_th_logic_005fpresent_005f4.setRole("ADMIN,ENTERPRISEADMIN");
/* 1861 */                     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 1862 */                     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                       for (;;) {
/* 1864 */                         out.write("\n\t\t <a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=");
/* 1865 */                         out.print(resIdTOCheck);
/* 1866 */                         out.write("&mgview=true')\"  enableClass=\"monitorgp-links\" ><img src=\"/images/icon_assigncustomfields.gif\" align=\"center\" border=\"0\"  title=\"");
/* 1867 */                         out.print(FormatUtil.getString("am.myfield.assign.text"));
/* 1868 */                         out.write("\"/></a> ");
/* 1869 */                         out.write("\n        ");
/* 1870 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 1871 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1875 */                     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 1876 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                     }
/*      */                     
/* 1879 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 1880 */                     out.write("\n\t\t</td></tr></table></div>\n         </td>");
/*      */                   } else {
/* 1882 */                     out.write("<td  class=\"whitegrayrightalign\">&nbsp;</td>");
/*      */                   }
/* 1884 */                   out.write("\n\n\n                <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n                ");
/*      */                   
/* 1886 */                   SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1887 */                   _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 1888 */                   _jspx_th_c_005fset_005f0.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                   
/* 1890 */                   _jspx_th_c_005fset_005f0.setVar("key");
/* 1891 */                   int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 1892 */                   if (_jspx_eval_c_005fset_005f0 != 0) {
/* 1893 */                     if (_jspx_eval_c_005fset_005f0 != 1) {
/* 1894 */                       out = _jspx_page_context.pushBody();
/* 1895 */                       _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 1896 */                       _jspx_th_c_005fset_005f0.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 1899 */                       out.write(32);
/* 1900 */                       out.print(row.get(residIndex) + "#" + availid + "#" + "MESSAGE");
/* 1901 */                       int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 1902 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1905 */                     if (_jspx_eval_c_005fset_005f0 != 1) {
/* 1906 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1909 */                   if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 1910 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                   }
/*      */                   
/* 1913 */                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 1914 */                   out.write(" \n\n         <td  class=\"whitegrayrightalign\" align=\"center\" ><a href=\"javascript:void(0)\" ");
/*      */                   
/* 1916 */                   IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1917 */                   _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 1918 */                   _jspx_th_c_005fif_005f16.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                   
/* 1920 */                   _jspx_th_c_005fif_005f16.setTest("${alert[key]!=null}");
/* 1921 */                   int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 1922 */                   if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                     for (;;) {
/* 1924 */                       out.write(" onmouseover=\"ddrivetip(this,event,'");
/* 1925 */                       if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                         return;
/* 1927 */                       out.write("<br>'+v+'");
/* 1928 */                       out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 1929 */                       out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 1930 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 1931 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1935 */                   if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 1936 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                   }
/*      */                   
/* 1939 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 1940 */                   out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 1941 */                   out.print(row.get(residIndex));
/* 1942 */                   out.write("&attributeid=");
/* 1943 */                   out.print(availid);
/* 1944 */                   out.write("&alertconfigurl=");
/* 1945 */                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + row.get(residIndex) + "&attributeIDs=" + healthid + "," + availid + "&attributeToSelect=" + availid + "&redirectto=" + URLEncoder.encode(new StringBuilder().append("/showapplication.do?haid=").append(row.get(residIndex)).append("&method=showApplication").toString())));
/* 1946 */                   out.write("')\"> ");
/* 1947 */                   out.write("\n                ");
/* 1948 */                   out.print(getSeverityImageForAvailability(alert.getProperty(row.get(residIndex) + "#" + availid)));
/* 1949 */                   out.write(" </a></td>");
/* 1950 */                   out.write("\n  \t        ");
/*      */                   
/* 1952 */                   SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1953 */                   _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 1954 */                   _jspx_th_c_005fset_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                   
/* 1956 */                   _jspx_th_c_005fset_005f1.setVar("key");
/* 1957 */                   int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 1958 */                   if (_jspx_eval_c_005fset_005f1 != 0) {
/* 1959 */                     if (_jspx_eval_c_005fset_005f1 != 1) {
/* 1960 */                       out = _jspx_page_context.pushBody();
/* 1961 */                       _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 1962 */                       _jspx_th_c_005fset_005f1.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 1965 */                       out.write(32);
/* 1966 */                       out.print(row.get(residIndex) + "#" + availid + "#" + "MESSAGE");
/* 1967 */                       int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 1968 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1971 */                     if (_jspx_eval_c_005fset_005f1 != 1) {
/* 1972 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1975 */                   if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 1976 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                   }
/*      */                   
/* 1979 */                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 1980 */                   out.write("\n                <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\t ");
/*      */                   
/* 1982 */                   SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1983 */                   _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 1984 */                   _jspx_th_c_005fset_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                   
/* 1986 */                   _jspx_th_c_005fset_005f2.setVar("key");
/* 1987 */                   int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 1988 */                   if (_jspx_eval_c_005fset_005f2 != 0) {
/* 1989 */                     if (_jspx_eval_c_005fset_005f2 != 1) {
/* 1990 */                       out = _jspx_page_context.pushBody();
/* 1991 */                       _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 1992 */                       _jspx_th_c_005fset_005f2.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 1995 */                       out.write(32);
/* 1996 */                       out.print(row.get(residIndex) + "#" + healthid + "#" + "MESSAGE");
/* 1997 */                       int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 1998 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2001 */                     if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2002 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2005 */                   if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 2006 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                   }
/*      */                   
/* 2009 */                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 2010 */                   out.write("\n\t <td  class=\"whitegrayrightalign\" align=\"center\" >");
/* 2011 */                   if (!resourceType.equals("Service")) {
/* 2012 */                     out.write("<a href=\"javascript:void(0)\" ");
/*      */                     
/* 2014 */                     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2015 */                     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 2016 */                     _jspx_th_c_005fif_005f17.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 2018 */                     _jspx_th_c_005fif_005f17.setTest("${alert[key]!=null}");
/* 2019 */                     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 2020 */                     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                       for (;;) {
/* 2022 */                         out.write(" onmouseover=\"ddrivetip(this,event,'");
/* 2023 */                         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                           return;
/* 2025 */                         out.write("<br>'+v+'");
/* 2026 */                         out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 2027 */                         out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 2028 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 2029 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2033 */                     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 2034 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                     }
/*      */                     
/* 2037 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2038 */                     out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2039 */                     out.print(row.get(residIndex));
/* 2040 */                     out.write("&attributeid=");
/* 2041 */                     out.print(healthid);
/* 2042 */                     out.write("&alertconfigurl=");
/* 2043 */                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + row.get(residIndex) + "&attributeIDs=" + healthid + "," + availid + "&attributeToSelect=" + healthid + "&redirectto=" + URLEncoder.encode(new StringBuilder().append("/showapplication.do?haid=").append(row.get(residIndex)).append("&method=showApplication").toString())));
/* 2044 */                     out.write("')\"> ");
/* 2045 */                     out.write("\n\t\t ");
/* 2046 */                     out.print(getSeverityImageForHealth(alert.getProperty(row.get(residIndex) + "#" + healthid)));
/* 2047 */                     out.write("</a>");
/*      */                   }
/* 2049 */                   out.write("</td>\n");
/* 2050 */                   if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                   {
/* 2052 */                     out.write(" \n               <td  class=\"whitegrayrightalign\" align=\"left\" > <img src=\" ");
/* 2053 */                     out.print(actionimage);
/* 2054 */                     out.write("\" border=\"0\"   title=\"");
/* 2055 */                     out.print(actionmessage);
/* 2056 */                     out.write("\"/>&nbsp;&nbsp;</td>");
/* 2057 */                     out.write(10);
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 2063 */                     out.write("\n<td class=\"whitegrayrightalign\" align=\"left\" height=\"24\" >&nbsp;</td>\n");
/*      */                   }
/*      */                   
/*      */ 
/* 2067 */                   out.write("\n    </tr>\n      ");
/*      */                   
/* 2069 */                   if (chilmos.get(resIdTOCheck + "") != null)
/*      */                   {
/* 2071 */                     ArrayList singlechilmos = (ArrayList)chilmos.get(resIdTOCheck + "");
/* 2072 */                     Hashtable availhealth = new Hashtable();
/* 2073 */                     availhealth.put("avail", availabilitykeys);
/* 2074 */                     availhealth.put("health", healthkeys);
/* 2075 */                     availhealth.put("alert", alert);
/* 2076 */                     String toappend = getAllChildNodestoDisplay(singlechilmos, resIdTOCheck + "", parentmongroups + "|" + resIdTOCheck + "", chilmos, availhealth, 2, request, extDeviceMap, site24x7List);
/* 2077 */                     out.println(toappend);
/*      */                   }
/* 2079 */                   else if (("HAI".equalsIgnoreCase(resourceType)) && ("true".equalsIgnoreCase(request.getParameter("alllevels"))) && (chilmos.get(resIdTOCheck + "") == null) && (!"orphaned".equals(haidInventory)))
/*      */                   {
/* 2081 */                     out.write("\n\t <tr  id=\"#monitor");
/* 2082 */                     out.print(parentmongroups + "|" + resIdTOCheck + "");
/* 2083 */                     out.write("\" class=\"leftcells\">\n\t <td class=\"whitegrayrightalign\" width=3% >&nbsp;</td>\n\t <td class=\"whitegrayrightalign\"  colspan=\"5\" style=\"padding-left:");
/* 2084 */                     out.print(leftpadding);
/* 2085 */                     out.write("px\" >\n\t       <span class=\"bodytext\">");
/* 2086 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text"));
/* 2087 */                     out.write("</span>\n\t       ");
/*      */                     
/* 2089 */                     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2090 */                     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 2091 */                     _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                     
/* 2093 */                     _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 2094 */                     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 2095 */                     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                       for (;;) {
/* 2097 */                         out.write("\n\t       ");
/*      */                         
/* 2099 */                         if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                         {
/*      */ 
/* 2102 */                           out.write("\n\t       <span class=\"bodytext\"> ");
/* 2103 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 2104 */                           out.write("\n\t\t   <a href=\"/it360/createBS.do?method=getNetworkDevices&productName=OpManager&haid=");
/* 2105 */                           out.print(haidInventory);
/* 2106 */                           out.write("\" class=\"monitorgp-links\">");
/* 2107 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 2108 */                           out.write("</span>\n\t       ");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/* 2114 */                           out.write("\n\t       <span class=\"bodytext\">");
/* 2115 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 2116 */                           out.write(" <a href=\"/showresource.do?type=All&method=getMonitorForm&haid=");
/* 2117 */                           out.print(haidInventory);
/* 2118 */                           out.write("&fromwhere=monitorgroupview\" class=\"monitorgp-links\">");
/* 2119 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 2120 */                           out.write("</span>\n\t       ");
/*      */                         }
/*      */                         
/*      */ 
/* 2124 */                         out.write("\n\t       ");
/* 2125 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 2126 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2130 */                     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 2131 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                     }
/*      */                     
/* 2134 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 2135 */                     out.write(" </td>\n\t</tr>\t \n\t ");
/*      */                   }
/*      */                   
/* 2138 */                   out.write(10);
/* 2139 */                   out.write(9);
/* 2140 */                   out.write(32);
/*      */                 }
/* 2142 */                 out.write("\t\t \t \n    ");
/* 2143 */                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 2144 */                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2145 */                 i = (Integer)_jspx_page_context.findAttribute("i");
/* 2146 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 2149 */               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2150 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 2153 */             if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 2154 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */             }
/*      */             
/* 2157 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 2158 */             out.write(10);
/* 2159 */             out.write(9);
/* 2160 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2161 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2165 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2166 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */         }
/*      */         
/* 2169 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2170 */         out.write("\n\t\n\t");
/*      */         
/* 2172 */         IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2173 */         _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 2174 */         _jspx_th_c_005fif_005f18.setParent(null);
/*      */         
/* 2176 */         _jspx_th_c_005fif_005f18.setTest("${empty applications}");
/* 2177 */         int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 2178 */         if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */           for (;;) {
/* 2180 */             out.write(10);
/* 2181 */             out.write(9);
/* 2182 */             if (!"orphaned".equals(haidInventory)) {
/* 2183 */               out.write("\n\t<tr  id=\"#monitor");
/* 2184 */               out.print(parentmongroups);
/* 2185 */               out.write("\" class=\"leftcells\">\n\t <td class=\"whitegrayrightalign\" width=3% >&nbsp;</td>\n\t <td class=\"whitegrayrightalign\"  colspan=\"5\" style=\"padding-left:");
/* 2186 */               out.print(leftpadding);
/* 2187 */               out.write("px; padding-top:10px; padding-bottom:10px;\" >\n\t       <span class=\"bodytext\">");
/* 2188 */               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text"));
/* 2189 */               out.write("</span>\n\t       ");
/*      */               
/* 2191 */               PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2192 */               _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 2193 */               _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f18);
/*      */               
/* 2195 */               _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 2196 */               int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 2197 */               if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                 for (;;) {
/* 2199 */                   out.write("\n\t       ");
/*      */                   
/* 2201 */                   if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                   {
/*      */ 
/* 2204 */                     out.write("\n\t       <span class=\"bodytext\"> ");
/* 2205 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 2206 */                     out.write(" <a href=\"/it360/createBS.do?method=getNetworkDevices&productName=OpManager&haid=");
/* 2207 */                     out.print(haidInventory);
/* 2208 */                     out.write("\" class=\"monitorgp-links\">");
/* 2209 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 2210 */                     out.write("</span>\n\t       ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 2216 */                     out.write("\n\t       <span class=\"bodytext\" >");
/* 2217 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.click.text"));
/* 2218 */                     out.write(" <a href=\"/showresource.do?type=All&method=getMonitorForm&haid=");
/* 2219 */                     out.print(haidInventory);
/* 2220 */                     out.write("&fromwhere=monitorgroupview\" class=\"staticlinks\">");
/* 2221 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text"));
/* 2222 */                     out.write("</span>\n\t       ");
/*      */                   }
/*      */                   
/*      */ 
/* 2226 */                   out.write("\n\t       ");
/* 2227 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 2228 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2232 */               if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 2233 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */               }
/*      */               
/* 2236 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 2237 */               out.write(" </td>\n\t</tr> \n\t");
/*      */             }
/*      */             
/* 2240 */             out.write("\n\t\n\t");
/* 2241 */             int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 2242 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2246 */         if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 2247 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */         }
/*      */         
/* 2250 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 2251 */         out.write(10);
/* 2252 */         out.write(9);
/* 2253 */         if (showmore)
/*      */         {
/* 2255 */           out.write("\n\t <tr  id=\"#monitor");
/* 2256 */           out.print(parentmongroups);
/* 2257 */           out.write("\" height=\"35\" class=\"leftcells\"> ");
/* 2258 */           out.write("\n\t <td class=\"whitegrayrightalign\" width=3% >&nbsp;</td>\n\t <td class=\"whitegrayrightalign\"  colspan=\"5\" style=\"padding-right:20px\" align=\"right\">\t       \n\t       <a href=\"javascript:void(0);\" class=\"show-tag\" onclick=\"showmoremonitors('/showresource.do?method=showMonitorGroupViewIndividual&parents=");
/* 2259 */           out.print(parentmongroups);
/* 2260 */           out.write("&lindx=");
/* 2261 */           out.print(lindx);
/* 2262 */           out.write("&pageno=");
/* 2263 */           out.print(pageno);
/* 2264 */           out.write("',this,'true')\">\n\t\t   ");
/* 2265 */           out.print(FormatUtil.getString("am.webclient.configurealert.showmore"));
/* 2266 */           out.write("</a>\t\t   \n\t\t   </td>\n\t</tr>  \n\t ");
/*      */         }
/* 2268 */         out.write("\n\t\t\t\t");
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 2273 */         e.printStackTrace();
/*      */       }
/*      */       
/* 2276 */       out.write("\n\t\t\t\t\n\n");
/*      */     } catch (Throwable t) {
/* 2278 */       if (!(t instanceof SkipPageException)) {
/* 2279 */         out = _jspx_out;
/* 2280 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2281 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2282 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2285 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2291 */     PageContext pageContext = _jspx_page_context;
/* 2292 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2294 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2295 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2296 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 2298 */     _jspx_th_c_005fif_005f4.setTest("${notMG==\"true\"}");
/* 2299 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2300 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2302 */         out.write("\n\t\t&nbsp;\n\t\t");
/* 2303 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2304 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2308 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2309 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2310 */       return true;
/*      */     }
/* 2312 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2318 */     PageContext pageContext = _jspx_page_context;
/* 2319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2321 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2322 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2323 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*      */     
/* 2325 */     _jspx_th_c_005fif_005f8.setTest("${notMG==\"true\"}");
/* 2326 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2327 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 2329 */         out.write("\n                \n                ");
/* 2330 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2331 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2335 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2336 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2337 */       return true;
/*      */     }
/* 2339 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2345 */     PageContext pageContext = _jspx_page_context;
/* 2346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2348 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2349 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2350 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 2352 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 2353 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2354 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2355 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2356 */       return true;
/*      */     }
/* 2358 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2364 */     PageContext pageContext = _jspx_page_context;
/* 2365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2367 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2368 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2369 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 2371 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 2372 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2373 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2374 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2375 */       return true;
/*      */     }
/* 2377 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2383 */     PageContext pageContext = _jspx_page_context;
/* 2384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2386 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2387 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2388 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 2390 */     _jspx_th_c_005fout_005f2.setValue("${alert[key]}");
/* 2391 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2392 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2393 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2394 */       return true;
/*      */     }
/* 2396 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2397 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2402 */     PageContext pageContext = _jspx_page_context;
/* 2403 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2405 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2406 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2407 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 2409 */     _jspx_th_c_005fout_005f3.setValue("${alert[key]}");
/* 2410 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2411 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2412 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2413 */       return true;
/*      */     }
/* 2415 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2416 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ShowMoreMonitors_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */