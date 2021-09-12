/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class JDK15Details_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   54 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   57 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   58 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   59 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   66 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   71 */     ArrayList list = null;
/*   72 */     StringBuffer sbf = new StringBuffer();
/*   73 */     ManagedApplication mo = new ManagedApplication();
/*   74 */     if (distinct)
/*      */     {
/*   76 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   80 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   83 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   85 */       ArrayList row = (ArrayList)list.get(i);
/*   86 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   87 */       if (distinct) {
/*   88 */         sbf.append(row.get(0));
/*      */       } else
/*   90 */         sbf.append(row.get(1));
/*   91 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   94 */     return sbf.toString(); }
/*      */   
/*   96 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   99 */     if (severity == null)
/*      */     {
/*  101 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  103 */     if (severity.equals("5"))
/*      */     {
/*  105 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  107 */     if (severity.equals("1"))
/*      */     {
/*  109 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  114 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  121 */     if (severity == null)
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  125 */     if (severity.equals("1"))
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  129 */     if (severity.equals("4"))
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  133 */     if (severity.equals("5"))
/*      */     {
/*  135 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  140 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  146 */     if (severity == null)
/*      */     {
/*  148 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  150 */     if (severity.equals("5"))
/*      */     {
/*  152 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  154 */     if (severity.equals("1"))
/*      */     {
/*  156 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  160 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  166 */     if (severity == null)
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  170 */     if (severity.equals("1"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  174 */     if (severity.equals("4"))
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  178 */     if (severity.equals("5"))
/*      */     {
/*  180 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  184 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  190 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  196 */     if (severity == 5)
/*      */     {
/*  198 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  200 */     if (severity == 1)
/*      */     {
/*  202 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  207 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  213 */     if (severity == null)
/*      */     {
/*  215 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  217 */     if (severity.equals("5"))
/*      */     {
/*  219 */       if (isAvailability) {
/*  220 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  223 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  226 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  228 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  230 */     if (severity.equals("1"))
/*      */     {
/*  232 */       if (isAvailability) {
/*  233 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  236 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  243 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  250 */     if (severity == null)
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  254 */     if (severity.equals("5"))
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  258 */     if (severity.equals("4"))
/*      */     {
/*  260 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  262 */     if (severity.equals("1"))
/*      */     {
/*  264 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  269 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  275 */     if (severity == null)
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("5"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("4"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  287 */     if (severity.equals("1"))
/*      */     {
/*  289 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  294 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  301 */     if (severity == null)
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  305 */     if (severity.equals("5"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  309 */     if (severity.equals("4"))
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  313 */     if (severity.equals("1"))
/*      */     {
/*  315 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  320 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  328 */     StringBuffer out = new StringBuffer();
/*  329 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  330 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  331 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  332 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  333 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  334 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  335 */     out.append("</tr>");
/*  336 */     out.append("</form></table>");
/*  337 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  344 */     if (val == null)
/*      */     {
/*  346 */       return "-";
/*      */     }
/*      */     
/*  349 */     String ret = FormatUtil.formatNumber(val);
/*  350 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  351 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  354 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  358 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  366 */     StringBuffer out = new StringBuffer();
/*  367 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  368 */     out.append("<tr>");
/*  369 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  371 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  373 */     out.append("</tr>");
/*  374 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  378 */       if (j % 2 == 0)
/*      */       {
/*  380 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  384 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  387 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  389 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  392 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  396 */       out.append("</tr>");
/*      */     }
/*  398 */     out.append("</table>");
/*  399 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  400 */     out.append("<tr>");
/*  401 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  402 */     out.append("</tr>");
/*  403 */     out.append("</table>");
/*  404 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  410 */     StringBuffer out = new StringBuffer();
/*  411 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  412 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  413 */     out.append("<tr>");
/*  414 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  415 */     out.append("<tr>");
/*  416 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  417 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  418 */     out.append("</tr>");
/*  419 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  422 */       out.append("<tr>");
/*  423 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  424 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  425 */       out.append("</tr>");
/*      */     }
/*      */     
/*  428 */     out.append("</table>");
/*  429 */     out.append("</table>");
/*  430 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  435 */     if (severity.equals("0"))
/*      */     {
/*  437 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  441 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  448 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session)
/*      */   {
/*  461 */     StringBuffer out = new StringBuffer();
/*  462 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  463 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  465 */       out.append("<tr>");
/*  466 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  467 */       out.append("</tr>");
/*      */       
/*      */ 
/*  470 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  472 */         String borderclass = "";
/*      */         
/*      */ 
/*  475 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  477 */         out.append("<tr>");
/*      */         
/*  479 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  480 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  481 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  487 */     out.append("</table><br>");
/*  488 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  489 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  491 */       List sLinks = secondLevelOfLinks[0];
/*  492 */       List sText = secondLevelOfLinks[1];
/*  493 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  496 */         out.append("<tr>");
/*  497 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  498 */         out.append("</tr>");
/*  499 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  501 */           String borderclass = "";
/*      */           
/*      */ 
/*  504 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  506 */           out.append("<tr>");
/*      */           
/*  508 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  509 */           if (sLinks.get(i).toString().length() == 0) {
/*  510 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  513 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  515 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  519 */     out.append("</table>");
/*  520 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  527 */     StringBuffer out = new StringBuffer();
/*  528 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  529 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  531 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  533 */         out.append("<tr>");
/*  534 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  535 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  539 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  541 */           String borderclass = "";
/*      */           
/*      */ 
/*  544 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  546 */           out.append("<tr>");
/*      */           
/*  548 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  549 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  550 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  553 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  556 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  561 */     out.append("</table><br>");
/*  562 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  563 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  565 */       List sLinks = secondLevelOfLinks[0];
/*  566 */       List sText = secondLevelOfLinks[1];
/*  567 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  570 */         out.append("<tr>");
/*  571 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  572 */         out.append("</tr>");
/*  573 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  575 */           String borderclass = "";
/*      */           
/*      */ 
/*  578 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  580 */           out.append("<tr>");
/*      */           
/*  582 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  583 */           if (sLinks.get(i).toString().length() == 0) {
/*  584 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  587 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  589 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  593 */     out.append("</table>");
/*  594 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSeverityClass(int status)
/*      */   {
/*  607 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  622 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  625 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  628 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  636 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  641 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  646 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  651 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  656 */     if (val != null)
/*      */     {
/*  658 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  662 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  667 */     if (val == null) {
/*  668 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  672 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  677 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  683 */     if (val != null)
/*      */     {
/*  685 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  689 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  695 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  700 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  704 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  709 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  714 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  719 */     String hostaddress = "";
/*  720 */     String ip = request.getHeader("x-forwarded-for");
/*  721 */     if (ip == null)
/*  722 */       ip = request.getRemoteAddr();
/*  723 */     InetAddress add = null;
/*  724 */     if (ip.equals("127.0.0.1")) {
/*  725 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  729 */       add = InetAddress.getByName(ip);
/*      */     }
/*  731 */     hostaddress = add.getHostName();
/*  732 */     if (hostaddress.indexOf('.') != -1) {
/*  733 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  734 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  738 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  743 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  749 */     if (severity == null)
/*      */     {
/*  751 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  753 */     if (severity.equals("5"))
/*      */     {
/*  755 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  757 */     if (severity.equals("1"))
/*      */     {
/*  759 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  764 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  769 */     ResultSet set = null;
/*  770 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  771 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  773 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  774 */       if (set.next()) { String str1;
/*  775 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  776 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  779 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  784 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  787 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  789 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  793 */     StringBuffer rca = new StringBuffer();
/*  794 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  795 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  798 */     int rcalength = key.length();
/*  799 */     String split = "6. ";
/*  800 */     int splitPresent = key.indexOf(split);
/*  801 */     String div1 = "";String div2 = "";
/*  802 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  804 */       if (rcalength > 180) {
/*  805 */         rca.append("<span class=\"rca-critical-text\">");
/*  806 */         getRCATrimmedText(key, rca);
/*  807 */         rca.append("</span>");
/*      */       } else {
/*  809 */         rca.append("<span class=\"rca-critical-text\">");
/*  810 */         rca.append(key);
/*  811 */         rca.append("</span>");
/*      */       }
/*  813 */       return rca.toString();
/*      */     }
/*  815 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  816 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  817 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  818 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  819 */     getRCATrimmedText(div1, rca);
/*  820 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  823 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  824 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  825 */     getRCATrimmedText(div2, rca);
/*  826 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  828 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  833 */     String[] st = msg.split("<br>");
/*  834 */     for (int i = 0; i < st.length; i++) {
/*  835 */       String s = st[i];
/*  836 */       if (s.length() > 180) {
/*  837 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  839 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  843 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  844 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  846 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  850 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  851 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  852 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  855 */       if (key == null) {
/*  856 */         return ret;
/*      */       }
/*      */       
/*  859 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  860 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  863 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  864 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  865 */       set = AMConnectionPool.executeQueryStmt(query);
/*  866 */       if (set.next())
/*      */       {
/*  868 */         String helpLink = set.getString("LINK");
/*  869 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  872 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  878 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  897 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  888 */         if (set != null) {
/*  889 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties getStatus(List entitylist)
/*      */   {
/*  903 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  904 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  906 */       String entityStr = (String)keys.nextElement();
/*  907 */       String mmessage = temp.getProperty(entityStr);
/*  908 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  909 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  911 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  917 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  918 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  920 */       String entityStr = (String)keys.nextElement();
/*  921 */       String mmessage = temp.getProperty(entityStr);
/*  922 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  923 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  925 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  930 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  940 */     String des = new String();
/*  941 */     while (str.indexOf(find) != -1) {
/*  942 */       des = des + str.substring(0, str.indexOf(find));
/*  943 */       des = des + replace;
/*  944 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  946 */     des = des + str;
/*  947 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  954 */       if (alert == null)
/*      */       {
/*  956 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  958 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  960 */         return "&nbsp;";
/*      */       }
/*      */       
/*  963 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  965 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  968 */       int rcalength = test.length();
/*  969 */       if (rcalength < 300)
/*      */       {
/*  971 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  975 */       StringBuffer out = new StringBuffer();
/*  976 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  977 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  978 */       out.append("</div>");
/*  979 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  980 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  981 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  986 */       ex.printStackTrace();
/*      */     }
/*  988 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  994 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  999 */     ArrayList attribIDs = new ArrayList();
/* 1000 */     ArrayList resIDs = new ArrayList();
/* 1001 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1003 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1005 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1007 */       String resourceid = "";
/* 1008 */       String resourceType = "";
/* 1009 */       if (type == 2) {
/* 1010 */         resourceid = (String)row.get(0);
/* 1011 */         resourceType = (String)row.get(3);
/*      */       }
/* 1013 */       else if (type == 3) {
/* 1014 */         resourceid = (String)row.get(0);
/* 1015 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1018 */         resourceid = (String)row.get(6);
/* 1019 */         resourceType = (String)row.get(7);
/*      */       }
/* 1021 */       resIDs.add(resourceid);
/* 1022 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1023 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1025 */       String healthentity = null;
/* 1026 */       String availentity = null;
/* 1027 */       if (healthid != null) {
/* 1028 */         healthentity = resourceid + "_" + healthid;
/* 1029 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1032 */       if (availid != null) {
/* 1033 */         availentity = resourceid + "_" + availid;
/* 1034 */         entitylist.add(availentity);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1048 */     Properties alert = getStatus(entitylist);
/* 1049 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1054 */     int size = monitorList.size();
/*      */     
/* 1056 */     String[] severity = new String[size];
/*      */     
/* 1058 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1060 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1061 */       String resourceName1 = (String)row1.get(7);
/* 1062 */       String resourceid1 = (String)row1.get(6);
/* 1063 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1064 */       if (severity[j] == null)
/*      */       {
/* 1066 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1070 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1072 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1074 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1077 */         if (sev > 0) {
/* 1078 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1079 */           monitorList.set(k, monitorList.get(j));
/* 1080 */           monitorList.set(j, t);
/* 1081 */           String temp = severity[k];
/* 1082 */           severity[k] = severity[j];
/* 1083 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1089 */     int z = 0;
/* 1090 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1093 */       int i = 0;
/* 1094 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1097 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1101 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1105 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1107 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1110 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1114 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1117 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1118 */       String resourceName1 = (String)row1.get(7);
/* 1119 */       String resourceid1 = (String)row1.get(6);
/* 1120 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1121 */       if (hseverity[j] == null)
/*      */       {
/* 1123 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1128 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1130 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1133 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1136 */         if (hsev > 0) {
/* 1137 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1138 */           monitorList.set(k, monitorList.get(j));
/* 1139 */           monitorList.set(j, t);
/* 1140 */           String temp1 = hseverity[k];
/* 1141 */           hseverity[k] = hseverity[j];
/* 1142 */           hseverity[j] = temp1;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List)
/*      */   {
/* 1154 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1155 */     boolean forInventory = false;
/* 1156 */     String trdisplay = "none";
/* 1157 */     String plusstyle = "inline";
/* 1158 */     String minusstyle = "none";
/* 1159 */     String haidTopLevel = "";
/* 1160 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1162 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1164 */         haidTopLevel = request.getParameter("haid");
/* 1165 */         forInventory = true;
/* 1166 */         trdisplay = "table-row;";
/* 1167 */         plusstyle = "none";
/* 1168 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1175 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1178 */     ArrayList listtoreturn = new ArrayList();
/* 1179 */     StringBuffer toreturn = new StringBuffer();
/* 1180 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1181 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1182 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1184 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1186 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1187 */       String childresid = (String)singlerow.get(0);
/* 1188 */       String childresname = (String)singlerow.get(1);
/* 1189 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1190 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1191 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1192 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1193 */       String unmanagestatus = (String)singlerow.get(5);
/* 1194 */       String actionstatus = (String)singlerow.get(6);
/* 1195 */       String linkclass = "monitorgp-links";
/* 1196 */       String titleforres = childresname;
/* 1197 */       String titilechildresname = childresname;
/* 1198 */       String childimg = "/images/trcont.png";
/* 1199 */       String flag = "enable";
/* 1200 */       String dcstarted = (String)singlerow.get(8);
/* 1201 */       String configMonitor = "";
/* 1202 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1203 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1205 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1207 */       if (singlerow.get(7) != null)
/*      */       {
/* 1209 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1211 */       String haiGroupType = "0";
/* 1212 */       if ("HAI".equals(childtype))
/*      */       {
/* 1214 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1216 */       childimg = "/images/trend.png";
/* 1217 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1218 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1219 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1221 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1223 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1225 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1226 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1229 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1231 */         linkclass = "disabledtext";
/* 1232 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1234 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1235 */       String availmouseover = "";
/* 1236 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1238 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1240 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1241 */       String healthmouseover = "";
/* 1242 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1244 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1247 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1248 */       int spacing = 0;
/* 1249 */       if (level >= 1)
/*      */       {
/* 1251 */         spacing = 40 * level;
/*      */       }
/* 1253 */       if (childtype.equals("HAI"))
/*      */       {
/* 1255 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1256 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1257 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1259 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1260 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1261 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1262 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1263 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1264 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1265 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1266 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1267 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1268 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1269 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1271 */         if (!forInventory)
/*      */         {
/* 1273 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1276 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1278 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1280 */           actions = editlink + actions;
/*      */         }
/* 1282 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1284 */           actions = actions + associatelink;
/*      */         }
/* 1286 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1287 */         String arrowimg = "";
/* 1288 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1290 */           actions = "";
/* 1291 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1292 */           checkbox = "";
/* 1293 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1295 */         if (isIt360)
/*      */         {
/* 1297 */           actionimg = "";
/* 1298 */           actions = "";
/* 1299 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1300 */           checkbox = "";
/*      */         }
/*      */         
/* 1303 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1305 */           actions = "";
/*      */         }
/* 1307 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1309 */           checkbox = "";
/*      */         }
/*      */         
/* 1312 */         String resourcelink = "";
/*      */         
/* 1314 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1316 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1320 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1323 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1329 */         if (!isIt360)
/*      */         {
/* 1331 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1335 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1338 */         toreturn.append("</tr>");
/* 1339 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1341 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1342 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1346 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1347 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1350 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1354 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1356 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1358 */             toreturn.append(assocMessage);
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1362 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1368 */         String resourcelink = null;
/* 1369 */         boolean hideEditLink = false;
/* 1370 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1372 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1373 */           hideEditLink = true;
/* 1374 */           if (isIt360)
/*      */           {
/* 1376 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1380 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1382 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1384 */           hideEditLink = true;
/* 1385 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1386 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1391 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1394 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1395 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1396 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1397 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1398 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1399 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1400 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1401 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1402 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1403 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1404 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1405 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1406 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1408 */         if (hideEditLink)
/*      */         {
/* 1410 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1412 */         if (!forInventory)
/*      */         {
/* 1414 */           removefromgroup = "";
/*      */         }
/* 1416 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1417 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1418 */           actions = actions + configcustomfields;
/*      */         }
/* 1420 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1422 */           actions = editlink + actions;
/*      */         }
/* 1424 */         String managedLink = "";
/* 1425 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1427 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1428 */           actions = "";
/* 1429 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1430 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1433 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1435 */           checkbox = "";
/*      */         }
/*      */         
/* 1438 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1440 */           actions = "";
/*      */         }
/* 1442 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1445 */         if (isIt360)
/*      */         {
/* 1447 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1455 */         if (!isIt360)
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1463 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1466 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1473 */       StringBuilder toreturn = new StringBuilder();
/* 1474 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1475 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1476 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1477 */       String title = "";
/* 1478 */       message = EnterpriseUtil.decodeString(message);
/* 1479 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1480 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1481 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1483 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1485 */       else if ("5".equals(severity))
/*      */       {
/* 1487 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1491 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1493 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1494 */       toreturn.append(v);
/*      */       
/* 1496 */       toreturn.append(link);
/* 1497 */       if (severity == null)
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1501 */       else if (severity.equals("5"))
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1505 */       else if (severity.equals("4"))
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1509 */       else if (severity.equals("1"))
/*      */       {
/* 1511 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1516 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1518 */       toreturn.append("</a>");
/* 1519 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1523 */       ex.printStackTrace();
/*      */     }
/* 1525 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1532 */       StringBuilder toreturn = new StringBuilder();
/* 1533 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1534 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1535 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1536 */       if (message == null)
/*      */       {
/* 1538 */         message = "";
/*      */       }
/*      */       
/* 1541 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1542 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1544 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1545 */       toreturn.append(v);
/*      */       
/* 1547 */       toreturn.append(link);
/*      */       
/* 1549 */       if (severity == null)
/*      */       {
/* 1551 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1553 */       else if (severity.equals("5"))
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1557 */       else if (severity.equals("1"))
/*      */       {
/* 1559 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1564 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1566 */       toreturn.append("</a>");
/* 1567 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1573 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1576 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1577 */     if (invokeActions != null) {
/* 1578 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1579 */       while (iterator.hasNext()) {
/* 1580 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1581 */         if (actionmap.containsKey(actionid)) {
/* 1582 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1587 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1591 */     String actionLink = "";
/* 1592 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1593 */     String query = "";
/* 1594 */     ResultSet rs = null;
/* 1595 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1596 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1597 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1598 */       actionLink = "method=" + methodName;
/*      */     }
/* 1600 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1601 */       actionLink = methodName;
/*      */     }
/* 1603 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1604 */     Iterator itr = methodarglist.iterator();
/* 1605 */     boolean isfirstparam = true;
/* 1606 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1607 */     while (itr.hasNext()) {
/* 1608 */       HashMap argmap = (HashMap)itr.next();
/* 1609 */       String argtype = (String)argmap.get("TYPE");
/* 1610 */       String argname = (String)argmap.get("IDENTITY");
/* 1611 */       String paramname = (String)argmap.get("PARAMETER");
/* 1612 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1613 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1614 */         isfirstparam = false;
/* 1615 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1617 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1621 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1625 */         actionLink = actionLink + "&";
/*      */       }
/* 1627 */       String paramValue = null;
/* 1628 */       String tempargname = argname;
/* 1629 */       if (commonValues.getProperty(tempargname) != null) {
/* 1630 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1633 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1634 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1635 */           if (dbType.equals("mysql")) {
/* 1636 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1639 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1641 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1643 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1644 */             if (rs.next()) {
/* 1645 */               paramValue = rs.getString("VALUE");
/* 1646 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1650 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1654 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1657 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1662 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1663 */           paramValue = rowId;
/*      */         }
/* 1665 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1666 */           paramValue = managedObjectName;
/*      */         }
/* 1668 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1669 */           paramValue = resID;
/*      */         }
/* 1671 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1672 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1675 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1677 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1678 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1679 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1681 */     return actionLink;
/*      */   }
/*      */   
/* 1684 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1685 */     String dependentAttribute = null;
/* 1686 */     String align = "left";
/*      */     
/* 1688 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1689 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1690 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1691 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1692 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1693 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1694 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1695 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1696 */       align = "center";
/*      */     }
/*      */     
/* 1699 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1700 */     String actualdata = "";
/*      */     
/* 1702 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1703 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1704 */         actualdata = availValue;
/*      */       }
/* 1706 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1707 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1711 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1712 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1715 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1721 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1722 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1723 */       toreturn.append("<table>");
/* 1724 */       toreturn.append("<tr>");
/* 1725 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1726 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1727 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1728 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1729 */         String toolTip = "";
/* 1730 */         String hideClass = "";
/* 1731 */         String textStyle = "";
/* 1732 */         boolean isreferenced = true;
/* 1733 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1734 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1735 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1736 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1738 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1739 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1740 */           while (valueList.hasMoreTokens()) {
/* 1741 */             String dependentVal = valueList.nextToken();
/* 1742 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1743 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1744 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1746 */               toolTip = "";
/* 1747 */               hideClass = "";
/* 1748 */               isreferenced = false;
/* 1749 */               textStyle = "disabledtext";
/* 1750 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1754 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1755 */           toolTip = "";
/* 1756 */           hideClass = "";
/* 1757 */           isreferenced = false;
/* 1758 */           textStyle = "disabledtext";
/* 1759 */           if (dependentImageMap != null) {
/* 1760 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1761 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1764 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1768 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1769 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1770 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1771 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1772 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1773 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1775 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1776 */           if (isreferenced) {
/* 1777 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1781 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1782 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1783 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1784 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1785 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1786 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1788 */           toreturn.append("</span>");
/* 1789 */           toreturn.append("</a>");
/* 1790 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1793 */       toreturn.append("</tr>");
/* 1794 */       toreturn.append("</table>");
/* 1795 */       toreturn.append("</td>");
/*      */     } else {
/* 1797 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1800 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1804 */     String colTime = null;
/* 1805 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1806 */     if ((rows != null) && (rows.size() > 0)) {
/* 1807 */       Iterator<String> itr = rows.iterator();
/* 1808 */       String maxColQuery = "";
/* 1809 */       for (;;) { if (itr.hasNext()) {
/* 1810 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1811 */           ResultSet maxCol = null;
/*      */           try {
/* 1813 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1814 */             while (maxCol.next()) {
/* 1815 */               if (colTime == null) {
/* 1816 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1819 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1828 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1830 */               if (maxCol != null)
/* 1831 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1833 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1828 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1830 */               if (maxCol != null)
/* 1831 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1833 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1838 */     return colTime;
/*      */   }
/*      */   
/* 1841 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1842 */     tablename = null;
/* 1843 */     ResultSet rsTable = null;
/* 1844 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1846 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1847 */       while (rsTable.next()) {
/* 1848 */         tablename = rsTable.getString("DATATABLE");
/* 1849 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1850 */           tablename = "AM_Script_Numeric_Data_" + baseid;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1863 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1854 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1857 */         if (rsTable != null)
/* 1858 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1860 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1866 */     String argsList = "";
/* 1867 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1869 */       if (showArgsMap.get(row) != null) {
/* 1870 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1871 */         if (showArgslist != null) {
/* 1872 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1873 */             if (argsList.trim().equals("")) {
/* 1874 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1877 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1884 */       e.printStackTrace();
/* 1885 */       return "";
/*      */     }
/* 1887 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1892 */     String argsList = "";
/* 1893 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1896 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1898 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1899 */         if (hideArgsList != null)
/*      */         {
/* 1901 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1903 */             if (argsList.trim().equals(""))
/*      */             {
/* 1905 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1909 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1917 */       ex.printStackTrace();
/*      */     }
/* 1919 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1923 */     StringBuilder toreturn = new StringBuilder();
/* 1924 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1931 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1932 */       Iterator itr = tActionList.iterator();
/* 1933 */       while (itr.hasNext()) {
/* 1934 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1935 */         String confirmmsg = "";
/* 1936 */         String link = "";
/* 1937 */         String isJSP = "NO";
/* 1938 */         HashMap tactionMap = (HashMap)itr.next();
/* 1939 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1940 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1941 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1942 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1943 */           (actionmap.containsKey(actionId))) {
/* 1944 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1945 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1946 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1947 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1948 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1950 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1956 */           if (isTableAction) {
/* 1957 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1960 */             tableName = "Link";
/* 1961 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1962 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1963 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1964 */             toreturn.append("</a></td>");
/*      */           }
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1975 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1981 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1983 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1984 */       Properties prop = (Properties)node.getUserObject();
/* 1985 */       String mgID = prop.getProperty("label");
/* 1986 */       String mgName = prop.getProperty("value");
/* 1987 */       String isParent = prop.getProperty("isParent");
/* 1988 */       int mgIDint = Integer.parseInt(mgID);
/* 1989 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1991 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1993 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1994 */       if (node.getChildCount() > 0)
/*      */       {
/* 1996 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1998 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2000 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2002 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2006 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2011 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2013 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2015 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2017 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2021 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2024 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2025 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2027 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2031 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2033 */       if (node.getChildCount() > 0)
/*      */       {
/* 2035 */         builder.append("<UL>");
/* 2036 */         printMGTree(node, builder);
/* 2037 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2042 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2043 */     StringBuffer toReturn = new StringBuffer();
/* 2044 */     String table = "-";
/*      */     try {
/* 2046 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2047 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2048 */       float total = 0.0F;
/* 2049 */       while (it.hasNext()) {
/* 2050 */         String attName = (String)it.next();
/* 2051 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2052 */         boolean roundOffData = false;
/* 2053 */         if ((data != null) && (!data.equals(""))) {
/* 2054 */           if (data.indexOf(",") != -1) {
/* 2055 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2058 */             float value = Float.parseFloat(data);
/* 2059 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2062 */             total += value;
/* 2063 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2066 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2071 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2072 */       while (attVsWidthList.hasNext()) {
/* 2073 */         String attName = (String)attVsWidthList.next();
/* 2074 */         String data = (String)attVsWidthProps.get(attName);
/* 2075 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2076 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2077 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2078 */         String className = (String)graphDetails.get("ClassName");
/* 2079 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2080 */         if (percentage < 1.0F)
/*      */         {
/* 2082 */           data = percentage + "";
/*      */         }
/* 2084 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2086 */       if (toReturn.length() > 0) {
/* 2087 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2091 */       e.printStackTrace();
/*      */     }
/* 2093 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2099 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2100 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2101 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2102 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2103 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2104 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2105 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2106 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2107 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2110 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2111 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2112 */       splitvalues[0] = multiplecondition.toString();
/* 2113 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2116 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2121 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2122 */     if (thresholdType != 3) {
/* 2123 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2124 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2125 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2126 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2127 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2128 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2130 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2131 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2132 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2133 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2134 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2135 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2137 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2138 */     if (updateSelected != null) {
/* 2139 */       updateSelected[0] = "selected";
/*      */     }
/* 2141 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2146 */       StringBuffer toreturn = new StringBuffer("");
/* 2147 */       if (commaSeparatedMsgId != null) {
/* 2148 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2149 */         int count = 0;
/* 2150 */         while (msgids.hasMoreTokens()) {
/* 2151 */           String id = msgids.nextToken();
/* 2152 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2153 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2154 */           count++;
/* 2155 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2156 */             if (toreturn.length() == 0) {
/* 2157 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2159 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2160 */             if (!image.trim().equals("")) {
/* 2161 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2163 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2164 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2167 */         if (toreturn.length() > 0) {
/* 2168 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2172 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2175 */       e.printStackTrace(); }
/* 2176 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2182 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2188 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2189 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2190 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2191 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/includes/JDK15RightArea.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2216 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2220 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2237 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2241 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2242 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2244 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2247 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2256 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2263 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2266 */     JspWriter out = null;
/* 2267 */     Object page = this;
/* 2268 */     JspWriter _jspx_out = null;
/* 2269 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2273 */       response.setContentType("text/html;charset=UTF-8");
/* 2274 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2276 */       _jspx_page_context = pageContext;
/* 2277 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2278 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2279 */       session = pageContext.getSession();
/* 2280 */       out = pageContext.getOut();
/* 2281 */       _jspx_out = out;
/*      */       
/* 2283 */       out.write("<!DOCTYPE html>\n");
/* 2284 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2286 */       request.setAttribute("HelpKey", "Monitors JDK Details");
/*      */       
/* 2288 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2290 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2291 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2292 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2294 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2295 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2296 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2298 */           out.write(10);
/* 2299 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2301 */           out.write(10);
/* 2302 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2304 */           out.write(10);
/* 2305 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2307 */           out.write(10);
/* 2308 */           out.write(10);
/*      */           
/* 2310 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2311 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2312 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2314 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2316 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2317 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2318 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2319 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2320 */               out = _jspx_page_context.pushBody();
/* 2321 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2322 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2325 */               out.write(10);
/* 2326 */               out.write(10);
/* 2327 */               com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2328 */               wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 2);
/* 2329 */               if (wlsGraph == null) {
/* 2330 */                 wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2331 */                 _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 2);
/*      */               }
/* 2333 */               out.write(10);
/* 2334 */               com.adventnet.appmanager.client.jdk15.JDK15Graph jdk15Graph = null;
/* 2335 */               jdk15Graph = (com.adventnet.appmanager.client.jdk15.JDK15Graph)_jspx_page_context.getAttribute("jdk15Graph", 2);
/* 2336 */               if (jdk15Graph == null) {
/* 2337 */                 jdk15Graph = new com.adventnet.appmanager.client.jdk15.JDK15Graph();
/* 2338 */                 _jspx_page_context.setAttribute("jdk15Graph", jdk15Graph, 2);
/*      */               }
/* 2340 */               out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/JDK.js\"></SCRIPT> ");
/* 2341 */               out.write("\n<script>\n\nfunction deleteThreadDump(url,id,tabval)\n{\n      if(!confirm(\"");
/* 2342 */               out.print(FormatUtil.getString("am.javaruntime.confirm.delete.text"));
/* 2343 */               out.write("\"))\n        {\n        return;//No I18N\n        }\n\tdeleteThreadDumpurl(url,id,tabval) //NO I18N\n}\n\nfunction deleteHeapDump(url,id)\n{\n\tif(!confirm(\"");
/* 2344 */               out.print(FormatUtil.getString("am.javaruntime.heap.confirm.delete.text"));
/* 2345 */               out.write("\"))\n\t   {\n\t   return;//No I18N\n\t   }\n\tdeleteHeapDumpurl(url,id) //NO I18N\n}\n\nfunction deleteHeapDumpurl(url,rid)\n{\n\tvar date = new Date();\n\tvar url ='/JavaRuntime.do?resourceid='+rid+'&url='+url+'&method=deleteheapURL&noredirect=false&date='+date;\n\thttp.open(\"GET\",url,true); //NO I18N\n\thttp.onreadystatechange = handleResponse; //NO I18N\n\thttp.send(null); //NO I18N\n}\nfunction submitForm(editform)\n{\n\t");
/* 2346 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2348 */               out.write(10);
/* 2349 */               out.write(9);
/*      */               
/* 2351 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2352 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2353 */               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2355 */               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2356 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2357 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 2359 */                   out.write("\n\n\tvar poll=trimAll(editform.pollInterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n\t\talert('");
/* 2360 */                   out.print(FormatUtil.getString("am.webclient.common.validpollinginterval.text"));
/* 2361 */                   out.write("');\n\t\treturn;\n\t}\n\tvar dispname = trimAll(editform.displayname.value);\n\tif(dispname=='')\n\t{\n\t\talert('");
/* 2362 */                   out.print(FormatUtil.getString("am.webclient.common.validatename.text"));
/* 2363 */                   out.write("');\n\t\treturn;\n\t}\n\tvar jndiname = trimAll(editform.jndiurl.value);\n\tif(jndiname=='')\n\t{\n\t\talert('");
/* 2364 */                   out.print(FormatUtil.getString("am.webclient.jdk15.jndiempty.text"));
/* 2365 */                   out.write("');\n\t\treturn;\n\t}\n\t\n\tvar port = trimAll(editform.port.value);\n\tif(port=='')\n\t{\n\t\talert('");
/* 2366 */                   out.print(FormatUtil.getString("am.webclient.addonproduct.servicedesk.alert.port"));
/* 2367 */                   out.write("'); //NO I18N\n\t\treturn;\n\t}\t\n\t\n\n\teditform.submit();\n\t");
/* 2368 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2369 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2373 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2374 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */               }
/*      */               
/* 2377 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2378 */               out.write("\n}\n\nfunction showDiv(divnames)\n{\n\n\t\tif(document.getElementById(divnames).style.display == 'none')\n\t\t\tdocument.getElementById(divnames).style.display='block';\n\t\t\t\n\n}\nfunction hideDiv(divnameh)\n{\n \n\t\tif(document.getElementById(divnameh).style.display == 'block')\n\t\t\tdocument.getElementById(divnameh).style.display='none';\n}\n\nfunction createconfdetailsa(gc)\n{\nvar a=gc;\nlocation.href=\"#\"; //NO I18N\ntoggleDiva(a);\n}\n\n\nfunction toggleDiva(divname)\n{\n\tvar na = divname.split(\"$\");\n\tfor(i=0;i<na.length;i++)\n\t{\n\n\t\tif(document.getElementById(na[i]).style.display == 'block')\n\t\t\tdocument.getElementById(na[i]).style.display='none';\n\t\telse\n\t\t\tdocument.getElementById(na[i]).style.display='block';\n\t\t\tif(document.getElementById(\"loadingg\"))\n\t\t\t{\n\t\t\t    \tdocument.getElementById(\"loadingg\").style.display = \"none\";\n    \t\t}\n    \t\t\t\n\t}\n\t\n}\n\n//Call Heap Dump method\nfunction getHeapDumpDatar(rid)\n{\n    resourceid=rid;   //NO I18N \n\tvar date = new Date();\n\tvar url ='/JavaRuntime.do?resourceid='+rid+'&method=getHeapDump&noredirect=false&date='+date; //NO I18N \n");
/* 2379 */               out.write("\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse1 //NO I18N\n\thttp.open(\"POST\", url, true); //NO I18N\n\thttp.send(null); //NO I18N\n}\nfunction handleResponse1() \n{\n if(http.readyState == 4 && http.status == 200)\n {\n\tvar result = http.responseText;\n\tdocument.getElementById('heapDumpr').innerHTML = result;\n\tdocument.getElementById('heapDumpr').style.display = \"block\";\n }\n else\n {\n\tdocument.getElementById('heapDumpr').style.display = \"block\";\n }\n} \n\nfunction getPerformGCData(rid)\n{\n    resourceid=rid;   //NO I18N \n\tvar date = new Date();\n\tvar url ='/JavaRuntime.do?resourceid='+rid+'&method=triggerGC&noredirect=false&date='+date; //NO I18N \n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponsegc //NO I18N\n\thttp.open(\"POST\", url, true); //NO I18N\n\thttp.send(null); //NO I18N\n}\nfunction handleResponsegc() \n{\n if(http.readyState == 4 && http.status == 200)\n {\n\tvar result = http.responseText;\n\tdocument.getElementById('performgcr').innerHTML = result; //NO I18N\n\tdocument.getElementById('performgcr').style.display = \"block\"; //NO I18N\n");
/* 2380 */               out.write(" }\n else\n {\n\tdocument.getElementById('performgcr').style.display = \"block\"; //NO I18N\n }\n} \n\n\n\n//Call Thread Dump Details\n\nfunction getThreadDumpData(rid,tabval)\n{\n\tif(document.getElementById('exturl').style.display=='block')\n\t{\n\t    var showall = document.getElementById('more'); //NO I18N\n\t    showall.innerHTML=\"More...\"; //NO I18N\n\t\ttoggleDiv('exturl'); //NO I18N\n\t\treturn;\n\t}\n    resourceid=rid; //NO I18N \n\tvar date = new Date(); //NO I18N\n\tvar url ='/JavaRuntime.do?resourceid='+rid+'&tabval='+tabval+'&method=getThreadDump&noredirect=false&date='+date; //NO I18N\n\thttp=getHTTPObject(); //NO I18N\n\thttp.onreadystatechange = handleResponse2 //NO I18N\n\thttp.open(\"POST\", url, true); //NO I18N\n\thttp.send(null); //NO I18N\n}\nfunction handleResponse2() \n{\n if(http.readyState == 4 && http.status == 200)\n {\n\tvar result = http.responseText;\n\tdocument.getElementById('exturl').innerHTML = result; //NO I18N\n\tvar showall = document.getElementById('more'); //NO I18N\n\tshowall.innerHTML=\"Hide...\"; //NO I18N\n\ttoggleDiv('exturl'); //NO I18N\n");
/* 2381 */               out.write(" }\n} \n\n//Call Heap Dump Details\n\nfunction getHeapDumpData(rid)\n{\n\tif(document.getElementById('exturlh').style.display=='block')\n\t{\n\t    var showall = document.getElementById('moreh'); //NO I18N\n\t    showall.innerHTML=\"More...\"; //NO I18N\n\t\ttoggleDiv('exturlh'); //NO I18N\n\t\treturn;\n\t}\n    resourceid=rid; //NO I18N \n\tvar date = new Date();\n\tvar url ='/JavaRuntime.do?resourceid='+rid+'&method=getHeapdumpdata&noredirect=false&date='+date; //NO I18N\n\thttp=getHTTPObject(); //NO I18N \n\thttp.onreadystatechange = handleResponse3 //NO I18N\n\thttp.open(\"POST\", url, true); //NO I18N\n\thttp.send(null); //NO I18N\n}\nfunction handleResponse3() \n{\n if(http.readyState == 4 && http.status == 200)\n {\n\tvar result = http.responseText; //NO I18N\n\tdocument.getElementById('exturlh').innerHTML = result; //NO I18N\n\tvar showall = document.getElementById('moreh'); //NO I18N\n\tshowall.innerHTML=\"Hide...\";  //NO I18N\n\ttoggleDiv('exturlh'); //NO I18N\n }\n} \n\n</script>\n  <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */               
/* 2383 */               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/*      */               
/* 2385 */               String haid = request.getParameter("haid");
/* 2386 */               String haName = null;
/* 2387 */               if (haid != null)
/*      */               {
/* 2389 */                 haName = (String)ht.get(haid);
/*      */               }
/*      */               
/* 2392 */               out.write(10);
/* 2393 */               out.write(9);
/*      */               
/* 2395 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2396 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2397 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2399 */               _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid) && param.haid!='null'}");
/* 2400 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2401 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 2403 */                   out.write("\n      \t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2404 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2405 */                   out.write(" &gt; ");
/* 2406 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2407 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 2408 */                   out.print(request.getAttribute("monitorname"));
/* 2409 */                   out.write(" </span></td>\n\t");
/* 2410 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2411 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2415 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2416 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 2419 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2420 */               out.write(10);
/* 2421 */               out.write(9);
/*      */               
/* 2423 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2424 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2425 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2427 */               _jspx_th_c_005fif_005f3.setTest("${(empty param.haid || (!empty invalidhaid) || param.haid=='null')}");
/* 2428 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2429 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 2431 */                   out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2432 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2433 */                   out.write(" &gt; ");
/* 2434 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("JDK1.5"));
/* 2435 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 2436 */                   out.print(request.getAttribute("monitorname"));
/* 2437 */                   out.write(" </span></td>\n\t");
/* 2438 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2439 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2443 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2444 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 2447 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2448 */               out.write("\n    </tr>\n</table> \n \n");
/*      */               
/* 2450 */               String resourceid = request.getParameter("resourceid");
/* 2451 */               String name = (String)request.getAttribute("name");
/* 2452 */               String appname = (String)request.getAttribute("appName");
/* 2453 */               String datatypestr = request.getParameter("datatype");
/*      */               
/* 2455 */               int datatype = 1;
/* 2456 */               if (datatypestr != null) {
/* 2457 */                 datatype = Integer.parseInt(datatypestr);
/*      */               }
/*      */               
/* 2460 */               out.write(10);
/* 2461 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2463 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2464 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2465 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2467 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2469 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2471 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2473 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2474 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2475 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2476 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2479 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2480 */               String available = null;
/* 2481 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2482 */               out.write(10);
/*      */               
/* 2484 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2485 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2486 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2488 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2490 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2492 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2494 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2495 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2496 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2497 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2500 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2501 */               String unavailable = null;
/* 2502 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2503 */               out.write(10);
/*      */               
/* 2505 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2506 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2507 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2509 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2511 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2513 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2515 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2516 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2517 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2518 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2521 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2522 */               String unmanaged = null;
/* 2523 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2524 */               out.write(10);
/*      */               
/* 2526 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2527 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2528 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2530 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2532 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2534 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2536 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2537 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2538 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2539 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2542 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2543 */               String scheduled = null;
/* 2544 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2545 */               out.write(10);
/*      */               
/* 2547 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2548 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2549 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2551 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2553 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2555 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2557 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2558 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2559 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2560 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2563 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2564 */               String critical = null;
/* 2565 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2566 */               out.write(10);
/*      */               
/* 2568 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2569 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2570 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2572 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2574 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2576 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2578 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2579 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2580 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2581 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2584 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2585 */               String clear = null;
/* 2586 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2587 */               out.write(10);
/*      */               
/* 2589 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2590 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2591 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2593 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2595 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2597 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2599 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2600 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2601 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2602 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2605 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2606 */               String warning = null;
/* 2607 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2608 */               out.write(10);
/* 2609 */               out.write(10);
/*      */               
/* 2611 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2612 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2614 */               out.write(10);
/* 2615 */               out.write(10);
/* 2616 */               out.write(10);
/* 2617 */               out.write(10);
/*      */               
/* 2619 */               String selected = "";
/* 2620 */               if (datatype == 1) {
/* 2621 */                 selected = "am.javaruntime.tab.overview";
/*      */               }
/* 2623 */               else if (datatype == 2) {
/* 2624 */                 selected = "am.javaruntime.tab.garbagecollection";
/*      */               }
/* 2626 */               else if (datatype == 3) {
/* 2627 */                 selected = "am.javaruntime.tab.threads";
/*      */               }
/* 2629 */               else if (datatype == 4) {
/* 2630 */                 selected = "am.javaruntime.tab.configuration";
/*      */               }
/*      */               
/* 2633 */               String editpage = request.getParameter("editPage");
/* 2634 */               String display = "none";
/* 2635 */               if ((editpage != null) && (editpage.equals("true"))) {
/* 2636 */                 display = "block";
/*      */               }
/*      */               
/* 2639 */               out.write(10);
/* 2640 */               out.write(10);
/* 2641 */               out.write(10);
/* 2642 */               JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.javaruntime.tab.overview,am.javaruntime.tab.garbagecollection,am.javaruntime.tab.threads,am.javaruntime.tab.configuration,am.javaruntime.tab.action", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.javaruntime.tab.overview,am.javaruntime.tab.garbagecollection,am.javaruntime.tab.threads,am.javaruntime.tab.configuration,am.javaruntime.tab.action", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getOverviewData,getGarbageData,getThreadsData,getConfigurationData,getActionData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/* 2643 */               out.write("\n\n\n<div id=\"Reconfigure\" style=\"display:");
/* 2644 */               out.print(display);
/* 2645 */               out.write("\">\n<br>\n");
/*      */               
/* 2647 */               String type = request.getParameter("type");
/* 2648 */               String pollInterval = request.getParameter("pollInterval");
/* 2649 */               if (pollInterval == null) {
/* 2650 */                 pollInterval = "5";
/*      */               }
/* 2652 */               out.write(10);
/* 2653 */               out.write(10);
/*      */               
/* 2655 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2656 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2657 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2659 */               _jspx_th_html_005fform_005f0.setAction("/JavaRuntime");
/*      */               
/* 2661 */               _jspx_th_html_005fform_005f0.setMethod("get");
/*      */               
/* 2663 */               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2664 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2665 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 2667 */                   out.write("\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2668 */                   out.print(request.getParameter("name"));
/* 2669 */                   out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 2670 */                   out.print(type);
/* 2671 */                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"editMonitor\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2672 */                   out.print(request.getParameter("resourceid"));
/* 2673 */                   out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2674 */                   out.print(request.getParameter("resourcename"));
/* 2675 */                   out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 2676 */                   out.print(request.getParameter("moname"));
/* 2677 */                   out.write("\">\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n    <td height=\"28\"   class=\"tableheading\">");
/* 2678 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2679 */                   out.write("\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:reconfigure()\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n<span><a href=\"javascript:reconfigure()\" class=\"staticlinks\" onClick=\"javascript:reconfigure()\">");
/* 2680 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2681 */                   out.write("</a></span>\n</td>\n</tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n\t<TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 2682 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.displayname"));
/* 2683 */                   out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\">\n\t");
/* 2684 */                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2686 */                   out.write("\n\t</TD>\n\t</TR>\n\t<TR>\n\t<TD width=\"23%\" height=\"28\" class=\"bodytext\">");
/* 2687 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.jndi.name"));
/* 2688 */                   out.write("<span class=\"mandatory\">*</span>\n\t</TD>\n\t<TD height=\"28\" width=\"77%\">\n\t");
/* 2689 */                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2691 */                   out.write("\n\t</TD>\n        </TR>\n\t<TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 2692 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval"));
/* 2693 */                   out.write(" <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\">\n        ");
/* 2694 */                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2696 */                   out.write("<span class=\"bodytext\">&nbsp;");
/* 2697 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval.units"));
/* 2698 */                   out.write("</span>\n\t</TD>\n\t</TR>\n\t\n\t<TR id=\"port\">\n\t<TD height=\"28\" width=\"25%\" class=\"bodytext\" style=\"padding-left:5px\">");
/* 2699 */                   out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 2700 */                   out.write(" <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"75%\" style=\"padding-left:5px\">");
/* 2701 */                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2703 */                   out.write("</TD>\n\t</TR>\n\t\n\t<TR id=\"username\">\n\t<TD height=\"28\" width=\"25%\" class=\"bodytext\" style=\"padding-left:5px\">");
/* 2704 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2705 */                   out.write("</TD>\n\t<TD height=\"28\" width=\"75%\" style=\"padding-left:5px\">");
/* 2706 */                   if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2708 */                   out.write("</TD>\n\t</TR>\n\t<TR id=\"password\">\n\t<TD width=\"23%\" height=\"28\" class=\"bodytext\" style=\"padding-left:5px\">");
/* 2709 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 2710 */                   out.write("</TD>\n\t<TD width=\"77%\" height=\"28\" style=\"padding-left:5px\"> <input type=\"password\" name=\"password\" class=\"formtext\" size=\"15\"/></TD>\n\t</TR>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"40%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"60%\" class=\"tablebottom\">\n\n    <input name=\"but1\" type=\"button\"  class=\"buttons\" value=\"");
/*      */                   
/* 2712 */                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2713 */                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2714 */                   _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 2716 */                   _jspx_th_c_005fif_005f4.setTest("${empty enabled}");
/* 2717 */                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2718 */                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                     for (;;) {
/* 2720 */                       out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 2721 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2722 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2726 */                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2727 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                   }
/*      */                   
/* 2730 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2731 */                   if (_jspx_meth_c_005fif_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2733 */                   out.write("\" onClick=\"javascript:submitForm(this.form)\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons\" value=\"");
/* 2734 */                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2735 */                   out.write("\" onClick=\"javascript:reconfigure()\" />\n     </td>\n  </tr>\n</table>\n");
/* 2736 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2737 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2741 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2742 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 2745 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2746 */               out.write("\n<br>\n<br>\n</div>\n\n\n<table border=\"0\" width=\"100%\">\n<tr>\n<td>\n<div id=\"data\">\n");
/*      */               
/* 2748 */               if (datatype == 1)
/*      */               {
/* 2750 */                 out.write(10);
/* 2751 */                 JspRuntimeLibrary.include(request, response, "/jsp/JDK15Overview.jsp" + ("/jsp/JDK15Overview.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2752 */                 out.write(10);
/*      */ 
/*      */               }
/* 2755 */               else if (datatype == 2)
/*      */               {
/* 2757 */                 out.write(10);
/* 2758 */                 JspRuntimeLibrary.include(request, response, "/jsp/JDK15Garbage.jsp" + ("/jsp/JDK15Garbage.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2759 */                 out.write(10);
/*      */ 
/*      */               }
/* 2762 */               else if (datatype == 3)
/*      */               {
/* 2764 */                 out.write(10);
/* 2765 */                 JspRuntimeLibrary.include(request, response, "/jsp/JDK15Threads.jsp" + ("/jsp/JDK15Threads.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2766 */                 out.write(10);
/*      */ 
/*      */               }
/* 2769 */               else if (datatype == 4)
/*      */               {
/* 2771 */                 out.write(10);
/* 2772 */                 JspRuntimeLibrary.include(request, response, "/jsp/JDK15Configuration.jsp" + ("/jsp/JDK15Configuration.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2773 */                 out.write(10);
/*      */ 
/*      */               }
/* 2776 */               else if (datatype == 5)
/*      */               {
/* 2778 */                 out.write(10);
/* 2779 */                 JspRuntimeLibrary.include(request, response, "/jsp/JDK15Action.jsp" + ("/jsp/JDK15Action.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2780 */                 out.write(10);
/*      */               }
/*      */               
/*      */ 
/* 2784 */               out.write("\n</div>\n\n</td>\n</tr>\n</table>\n");
/* 2785 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2786 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2789 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2790 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2793 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2794 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 2797 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2798 */           out.write(32);
/* 2799 */           out.write("\n\n\n \n\n\n \n");
/* 2800 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2802 */           out.write(10);
/*      */           
/* 2804 */           PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2805 */           _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 2806 */           _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2808 */           _jspx_th_tiles_005fput_005f5.setName("ServerLeftArea");
/*      */           
/* 2810 */           _jspx_th_tiles_005fput_005f5.setType("string");
/* 2811 */           int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 2812 */           if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 2813 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 2814 */               out = _jspx_page_context.pushBody();
/* 2815 */               _jspx_th_tiles_005fput_005f5.setBodyContent((BodyContent)out);
/* 2816 */               _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2819 */               out.write(10);
/* 2820 */               out.write("<!--$Id$-->\n\n\n\n<script>\nfunction reconfigure()\n{\n\ttoggleDiv('Reconfigure');\n\t//hideDiv('snapshot');\n}\n</script>\n");
/*      */               
/* 2822 */               String resourceid = request.getParameter("resourceid");
/* 2823 */               String moname = request.getParameter("moname");
/* 2824 */               String configure_link = (String)request.getAttribute("configurelink");
/*      */               
/* 2826 */               out.write("\n\n<script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n \t ");
/* 2827 */               if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 2829 */               out.write(10);
/* 2830 */               out.write(9);
/*      */               
/* 2832 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2833 */               _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2834 */               _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2836 */               _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2837 */               int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2838 */               if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                 for (;;) {
/* 2840 */                   out.write("\n        var s = confirm(\"");
/* 2841 */                   out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2842 */                   out.write("\");\n        if (s)\n        document.location.href=\"/deleteMO.do?method=deleteMO&type=JDK1.5&select=");
/* 2843 */                   out.print(resourceid);
/* 2844 */                   out.write("\";\n\t ");
/* 2845 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2846 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2850 */               if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2851 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */               }
/*      */               
/* 2854 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2855 */               out.write("\n\t }\n\t        function confirmManage()\n \t {\n \t ");
/* 2856 */               if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 2858 */               out.write(10);
/* 2859 */               out.write(9);
/*      */               
/* 2861 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2862 */               _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 2863 */               _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2865 */               _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 2866 */               int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 2867 */               if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                 for (;;) {
/* 2869 */                   out.write("\n  var s = confirm(\"");
/* 2870 */                   out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2871 */                   out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2872 */                   if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fnotPresent_005f2, _jspx_page_context))
/*      */                     return;
/* 2874 */                   out.write("\";\n\t ");
/* 2875 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 2876 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2880 */               if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 2881 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */               }
/*      */               
/* 2884 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 2885 */               out.write("\n\t }\n\n         function confirmUnManage()\n \t {\n \t ");
/* 2886 */               if (_jspx_meth_logic_005fpresent_005f3(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 2888 */               out.write(10);
/* 2889 */               out.write(9);
/*      */               
/* 2891 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2892 */               _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 2893 */               _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2895 */               _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 2896 */               int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 2897 */               if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                 for (;;) {
/* 2899 */                   out.write("\n\t  var show_msg=\"false\";\n      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2900 */                   out.print(request.getParameter("resourceid"));
/* 2901 */                   out.write("; //No i18n\n      $.ajax({\n        type:'POST', //No i18n\n        url:url,\n        async:false,\n        success: function(data)\n        {\n          show_msg=data\n        }\n      });\n      if(show_msg.indexOf(\"true\")>-1)\n      {\n          alert(\"");
/* 2902 */                   out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2903 */                   out.write("\");\n\t   \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2904 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                     return;
/* 2906 */                   out.write("\";\n     }\n   else { \n\t\tvar s = confirm(\"");
/* 2907 */                   out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2908 */                   out.write("\");\n\t\tif (s){\n\t  \t \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2909 */                   if (_jspx_meth_c_005fout_005f2(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                     return;
/* 2911 */                   out.write("\"; //No I18N\n\t\t}\n\t } \n\t ");
/* 2912 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 2913 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2917 */               if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 2918 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */               }
/*      */               
/* 2921 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 2922 */               out.write("\n\t}\n</script>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"   class=\"leftmnutables\">\n\n  <tr>\n    <td class=\"leftlinksheading\">");
/* 2923 */               out.print(FormatUtil.getString("am.webclient.jboss.quicklinks.text"));
/* 2924 */               out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n  ");
/*      */               
/* 2926 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2927 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2928 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/* 2929 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2930 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 2932 */                   out.write(10);
/* 2933 */                   out.write(32);
/* 2934 */                   out.write(32);
/*      */                   
/* 2936 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2937 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2938 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/* 2940 */                   _jspx_th_c_005fwhen_005f0.setTest("${param.method == 'showdetails' && param.all!='true'}");
/* 2941 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2942 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/* 2944 */                       out.write("\n         ");
/* 2945 */                       out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2946 */                       out.write(10);
/* 2947 */                       out.write(32);
/* 2948 */                       out.write(32);
/* 2949 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2950 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2954 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2955 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/* 2958 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2959 */                   out.write(10);
/* 2960 */                   out.write(32);
/* 2961 */                   out.write(32);
/*      */                   
/* 2963 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2964 */                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2965 */                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2966 */                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2967 */                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                     for (;;) {
/* 2969 */                       out.write("\n\t<a href=\"/showresource.do?resourceid=");
/* 2970 */                       if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                         return;
/* 2972 */                       out.write("&method=showResourceForResourceID");
/* 2973 */                       if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                         return;
/* 2975 */                       out.write("\"\n      class=\"new-left-links\">");
/* 2976 */                       out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2977 */                       out.write("</a>\n  ");
/* 2978 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2979 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2983 */                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2984 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                   }
/*      */                   
/* 2987 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2988 */                   out.write(10);
/* 2989 */                   out.write(32);
/* 2990 */                   out.write(32);
/* 2991 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2992 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2996 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2997 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 3000 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3001 */               out.write("\n  </td>\n  </tr>\n  <!--Alert configuration should be enabled for Admin and Demo users alone-->\n  ");
/*      */               
/* 3003 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3004 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3005 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3007 */               _jspx_th_c_005fif_005f6.setTest("${!empty ADMIN || !empty DEMO}");
/* 3008 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3009 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 3011 */                   out.write("\n   <tr>\n          <td width=\"88%\" class=\"leftlinkstd\">\n       \t");
/*      */                   
/* 3013 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3014 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3015 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f6);
/* 3016 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3017 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/* 3019 */                       out.write("\n         ");
/*      */                       
/* 3021 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3022 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3023 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/* 3025 */                       _jspx_th_c_005fwhen_005f1.setTest("${param.all=='true'}");
/* 3026 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3027 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/* 3029 */                           out.write("\n                ");
/* 3030 */                           out.print(ALERTCONFIG_TEXT);
/* 3031 */                           out.write("\n         ");
/* 3032 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3033 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3037 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3038 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/* 3041 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3042 */                       out.write("\n  \t");
/*      */                       
/* 3044 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3045 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3046 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 3047 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3048 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/* 3050 */                           out.write("\n          <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 3051 */                           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                             return;
/* 3053 */                           out.write("\"\n            class=\"new-left-links\">");
/* 3054 */                           out.print(ALERTCONFIG_TEXT);
/* 3055 */                           out.write("</a>\n              ");
/* 3056 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3057 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3061 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3062 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/* 3065 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3066 */                       out.write("\n\t      ");
/* 3067 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3068 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3072 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3073 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/* 3076 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3077 */                   out.write("\n            </td>\n        </tr>\n  ");
/* 3078 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3079 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3083 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3084 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 3087 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3088 */               out.write("\n  <!-- Edit link should be enabled for ADMIN server when SSO is enabled -->\n   ");
/* 3089 */               if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 3090 */                 out.write("  \n    ");
/*      */                 
/* 3092 */                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3093 */                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3094 */                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3096 */                 _jspx_th_logic_005fpresent_005f4.setRole("ENTERPRISEADMIN");
/* 3097 */                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3098 */                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                   for (;;) {
/* 3100 */                     out.write("\n  <tr>\n   <td class=\"leftlinkstd\" > <a target=\"mas_window\" href=\"/manageConfMons.do?haid=null&resourceid=");
/* 3101 */                     if (_jspx_meth_c_005fout_005f6(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                       return;
/* 3103 */                     out.write("&method=editPreConfMonitor&type=JDK1.5&resourcename=");
/* 3104 */                     out.print(moname);
/* 3105 */                     out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n  ");
/* 3106 */                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3107 */                     out.write("</a> </td>\n  </tr>\n  ");
/* 3108 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3109 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3113 */                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3114 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                 }
/*      */                 
/* 3117 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3118 */                 out.write(10);
/* 3119 */                 out.write(32);
/* 3120 */                 out.write(32);
/*      */               }
/* 3122 */               out.write("\n<!-- Edit link should be enabled for ADMIN and DEMO Users alone -->\n");
/*      */               
/* 3124 */               IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3125 */               _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3126 */               _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3128 */               _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN || !empty DEMO}");
/* 3129 */               int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3130 */               if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                 for (;;) {
/* 3132 */                   out.write("\n  <tr>\n    <td class=\"leftlinkstd\">\n ");
/*      */                   
/* 3134 */                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3135 */                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3136 */                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fif_005f7);
/* 3137 */                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3138 */                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                     for (;;) {
/* 3140 */                       out.write("\n    ");
/*      */                       
/* 3142 */                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3143 */                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3144 */                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                       
/* 3146 */                       _jspx_th_c_005fwhen_005f2.setTest("${uri=='/reconfigure.do'}");
/* 3147 */                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3148 */                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                         for (;;) {
/* 3150 */                           out.write("\n        ");
/* 3151 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3152 */                           out.write(10);
/* 3153 */                           out.write(32);
/* 3154 */                           out.write(32);
/* 3155 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3156 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3160 */                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3161 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                       }
/*      */                       
/* 3164 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3165 */                       out.write(10);
/* 3166 */                       out.write(32);
/* 3167 */                       out.write(32);
/*      */                       
/* 3169 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3170 */                       _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3171 */                       _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 3172 */                       int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3173 */                       if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                         for (;;) {
/* 3175 */                           out.write("\n    <a href=\"/manageConfMons.do?haid=null&resourceid=");
/* 3176 */                           if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/*      */                             return;
/* 3178 */                           out.write("&method=editPreConfMonitor&type=JDK1.5&resourcename=");
/* 3179 */                           out.print(moname);
/* 3180 */                           out.write("\"\n      class=\"new-left-links\">");
/* 3181 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3182 */                           out.write("</a>\n  ");
/* 3183 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3184 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3188 */                       if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3189 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                       }
/*      */                       
/* 3192 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3193 */                       out.write(10);
/* 3194 */                       out.write(32);
/* 3195 */                       out.write(32);
/* 3196 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3197 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3201 */                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3202 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                   }
/*      */                   
/* 3205 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3206 */                   out.write("\n  </td>\n  </tr>\n");
/* 3207 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3208 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3212 */               if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3213 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */               }
/*      */               
/* 3216 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3217 */               out.write(10);
/* 3218 */               out.write(10);
/*      */               
/* 3220 */               IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3221 */               _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3222 */               _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3224 */               _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN || !empty DEMO}");
/* 3225 */               int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3226 */               if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                 for (;;) {
/* 3228 */                   out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n     <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 3229 */                   out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3230 */                   out.write("</A></td>\n  \t</td>\n  </tr>\n  </tr>\n  ");
/* 3231 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3232 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3236 */               if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3237 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */               }
/*      */               
/* 3240 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3241 */               out.write(10);
/*      */               
/* 3243 */               IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3244 */               _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3245 */               _jspx_th_c_005fif_005f9.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3247 */               _jspx_th_c_005fif_005f9.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 3248 */               int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3249 */               if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                 for (;;) {
/* 3251 */                   out.write("\n  <tr>\n  ");
/*      */                   
/* 3253 */                   if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                   {
/*      */ 
/* 3256 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 3257 */                     out.print(FormatUtil.getString("Manage"));
/* 3258 */                     out.write("</A></td>\n    ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 3264 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 3265 */                     out.print(FormatUtil.getString("UnManage"));
/* 3266 */                     out.write("</A></td>\n    ");
/*      */                   }
/*      */                   
/*      */ 
/* 3270 */                   out.write("\n  </tr>\n  ");
/* 3271 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3272 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3276 */               if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3277 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */               }
/*      */               
/* 3280 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3281 */               out.write("\n\n\n\n");
/*      */               
/* 3283 */               if ((configure_link != null) && (!configure_link.equals("null")))
/*      */               {
/*      */ 
/* 3286 */                 out.write("\n\n   ");
/*      */                 
/* 3288 */                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3289 */                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3290 */                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3292 */                 _jspx_th_c_005fif_005f10.setTest("${!empty ADMIN || !empty DEMO}");
/* 3293 */                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3294 */                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                   for (;;) {
/* 3296 */                     out.write("\n    <tr>\n    <td class=\"leftlinkstd\" >\n\n    \t<a href=\"");
/* 3297 */                     out.print(request.getAttribute("configurelink"));
/* 3298 */                     out.write("\" onclick=\"javascript:return subAddCustom(this);\" class=\"new-left-links\">\n    \t  ");
/* 3299 */                     out.print(FormatUtil.getString("am.webclient.common.addcustomattributes.text"));
/* 3300 */                     out.write("\n    \t</a>\n    </td>\n  </tr>\n   ");
/* 3301 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3302 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3306 */                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3307 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                 }
/*      */                 
/* 3310 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3311 */                 out.write("\n   ");
/*      */               }
/*      */               
/*      */ 
/* 3315 */               out.write(10);
/* 3316 */               out.write(32);
/* 3317 */               out.write(32);
/*      */               
/* 3319 */               IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3320 */               _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3321 */               _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3323 */               _jspx_th_c_005fif_005f11.setTest("${!empty ADMIN}");
/* 3324 */               int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3325 */               if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                 for (;;) {
/* 3327 */                   out.write("\n  \t   \t<tr>\n  \t       \t <td colspan=\"2\" class=\"leftlinkstd\">\n  \t       \t ");
/* 3328 */                   out.print(FaultUtil.getAlertTemplateURL(request.getParameter("resourceid"), request.getParameter("name"), "JDK1.5", "JDK1.5 Server"));
/* 3329 */                   out.write("\n  \t       \t </td>\n  \t      \t</tr>\n  ");
/* 3330 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3331 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3335 */               if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3336 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */               }
/*      */               
/* 3339 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3340 */               out.write("\n   ");
/*      */               
/* 3342 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3343 */               _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3344 */               _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3346 */               _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 3347 */               int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 3348 */               if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                 for (;;) {
/* 3350 */                   out.write("\n    ");
/*      */                   
/* 3352 */                   String resourceid_poll = request.getParameter("resourceid");
/* 3353 */                   String resourcetype = request.getParameter("type");
/*      */                   
/* 3355 */                   out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3356 */                   out.print(resourceid_poll);
/* 3357 */                   out.write("&resourcetype=");
/* 3358 */                   out.print(resourcetype);
/* 3359 */                   out.write("\" class=\"new-left-links\"> ");
/* 3360 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3361 */                   out.write("</a></td>\n    </tr>\n    ");
/* 3362 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 3363 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3367 */               if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 3368 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */               }
/*      */               
/* 3371 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 3372 */               out.write("\n    ");
/*      */               
/* 3374 */               PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3375 */               _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3376 */               _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3378 */               _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3379 */               int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3380 */               if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                 for (;;) {
/* 3382 */                   out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3383 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3384 */                   out.write("</a></td>\n          </td>\n    ");
/* 3385 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3386 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3390 */               if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3391 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */               }
/*      */               
/* 3394 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3395 */               out.write(10);
/* 3396 */               out.write(10);
/* 3397 */               out.write("<!-- $Id$-->\n\n\n  \n");
/*      */               
/* 3399 */               if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */               {
/* 3401 */                 Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3402 */                 String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                 
/* 3404 */                 String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3405 */                 String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3406 */                 if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                 {
/* 3408 */                   if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                   {
/*      */ 
/* 3411 */                     out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3412 */                     out.print(ciInfoUrl);
/* 3413 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3414 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3415 */                     out.write("</a></td>");
/* 3416 */                     out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3417 */                     out.print(ciRLUrl);
/* 3418 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3419 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3420 */                     out.write("</a></td>");
/* 3421 */                     out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                   }
/* 3425 */                   else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                   {
/*      */ 
/* 3428 */                     out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3429 */                     out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3430 */                     out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3431 */                     out.print(ciInfoUrl);
/* 3432 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3433 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3434 */                     out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3435 */                     out.print(ciRLUrl);
/* 3436 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3437 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3438 */                     out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/* 3443 */               out.write("\n \n \n\n");
/* 3444 */               out.write("\n  </table>\n");
/*      */               
/*      */ 
/* 3447 */               ArrayList attribIDs = new ArrayList();
/* 3448 */               ArrayList resIDs = new ArrayList();
/* 3449 */               resIDs.add(resourceid);
/* 3450 */               attribIDs.add("3600");
/* 3451 */               attribIDs.add("3601");
/* 3452 */               Properties alert = getStatus(resIDs, attribIDs);
/*      */               
/* 3454 */               out.write("\n</td>\n  </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n  <tr>\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3455 */               out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3456 */               out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3457 */               if (_jspx_meth_c_005fout_005f8(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 3459 */               out.write("&attributeid=3601')\" class=\"new-left-links\">");
/* 3460 */               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3461 */               out.write("</a></td>\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3462 */               if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 3464 */               out.write("&attributeid=3601')\">");
/* 3465 */               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "3601")));
/* 3466 */               out.write("</a></td>\n   </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3467 */               if (_jspx_meth_c_005fout_005f10(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 3469 */               out.write("&attributeid=3600')\" class=\"new-left-links\">");
/* 3470 */               out.print(FormatUtil.getString("Availability"));
/* 3471 */               out.write("</a></td>\n    <td width=\"51%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3472 */               if (_jspx_meth_c_005fout_005f11(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 3474 */               out.write("&attributeid=3600')\">");
/* 3475 */               out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "3600")));
/* 3476 */               out.write("</a>\n </td>\n  </tr>\n</table></td>\n  </tr>\n    </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n\n\n");
/* 3477 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */               
/*      */ 
/*      */ 
/* 3481 */               boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3482 */               if (EnterpriseUtil.isIt360MSPEdition)
/*      */               {
/* 3484 */                 showAssociatedBSG = false;
/*      */                 
/*      */ 
/*      */ 
/* 3488 */                 CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3489 */                 CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3490 */                 String loginName = request.getUserPrincipal().getName();
/* 3491 */                 CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                 
/* 3493 */                 if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                 {
/* 3495 */                   showAssociatedBSG = true;
/*      */                 }
/*      */               }
/* 3498 */               String monitorType = request.getParameter("type");
/* 3499 */               ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3500 */               boolean mon = conf1.isConfMonitor(monitorType);
/* 3501 */               if (showAssociatedBSG)
/*      */               {
/* 3503 */                 Hashtable associatedmgs = new Hashtable();
/* 3504 */                 String resId = request.getParameter("resourceid");
/* 3505 */                 request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3506 */                 if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                 {
/* 3508 */                   mon = false;
/*      */                 }
/*      */                 
/* 3511 */                 if (!mon)
/*      */                 {
/* 3513 */                   out.write(10);
/* 3514 */                   out.write(10);
/*      */                   
/* 3516 */                   IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3517 */                   _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3518 */                   _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 3520 */                   _jspx_th_c_005fif_005f12.setTest("${!empty associatedmgs}");
/* 3521 */                   int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3522 */                   if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                     for (;;) {
/* 3524 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3525 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3526 */                       out.write("</td>\n        </tr>\n        ");
/*      */                       
/* 3528 */                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3529 */                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3530 */                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f12);
/*      */                       
/* 3532 */                       _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                       
/* 3534 */                       _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                       
/* 3536 */                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3537 */                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                       try {
/* 3539 */                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3540 */                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                           for (;;) {
/* 3542 */                             out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3543 */                             if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3601 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3602 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3545 */                             out.write("&method=showApplication\" title=\"");
/* 3546 */                             if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3601 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3602 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3548 */                             out.write("\"  class=\"new-left-links\">\n         ");
/* 3549 */                             if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3601 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3602 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3551 */                             out.write("\n    \t");
/* 3552 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3553 */                             out.write("\n         </a></td>\n        <td>");
/*      */                             
/* 3555 */                             PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3556 */                             _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3557 */                             _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                             
/* 3559 */                             _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 3560 */                             int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3561 */                             if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                               for (;;) {
/* 3563 */                                 out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3564 */                                 if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fpresent_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3601 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3602 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 3566 */                                 out.write(39);
/* 3567 */                                 out.write(44);
/* 3568 */                                 out.write(39);
/* 3569 */                                 out.print(resId);
/* 3570 */                                 out.write(39);
/* 3571 */                                 out.write(44);
/* 3572 */                                 out.write(39);
/* 3573 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3574 */                                 out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3575 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3576 */                                 out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3577 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3578 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3582 */                             if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3583 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3601 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3602 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3586 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3587 */                             out.write("</td>\n        </tr>\n\t");
/* 3588 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3589 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3593 */                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3601 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3602 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 3597 */                           int tmp9463_9462 = 0; int[] tmp9463_9460 = _jspx_push_body_count_c_005fforEach_005f0; int tmp9465_9464 = tmp9463_9460[tmp9463_9462];tmp9463_9460[tmp9463_9462] = (tmp9465_9464 - 1); if (tmp9465_9464 <= 0) break;
/* 3598 */                           out = _jspx_page_context.popBody(); }
/* 3599 */                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                       } finally {
/* 3601 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3602 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                       }
/* 3604 */                       out.write("\n      </table>\n ");
/* 3605 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3606 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3610 */                   if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3611 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                   }
/*      */                   
/* 3614 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3615 */                   out.write(10);
/* 3616 */                   out.write(32);
/*      */                   
/* 3618 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3619 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3620 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 3622 */                   _jspx_th_c_005fif_005f13.setTest("${empty associatedmgs}");
/* 3623 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3624 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                     for (;;) {
/* 3626 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3627 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3628 */                       out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3629 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3630 */                       out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3631 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3632 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3636 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3637 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                   }
/*      */                   
/* 3640 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3641 */                   out.write(10);
/* 3642 */                   out.write(32);
/* 3643 */                   out.write(10);
/*      */ 
/*      */                 }
/* 3646 */                 else if (mon)
/*      */                 {
/*      */ 
/*      */ 
/* 3650 */                   out.write(10);
/*      */                   
/* 3652 */                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3653 */                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3654 */                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 3656 */                   _jspx_th_c_005fif_005f14.setTest("${!empty associatedmgs}");
/* 3657 */                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3658 */                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                     for (;;) {
/* 3660 */                       out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 3661 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                         return;
/* 3663 */                       out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                       
/* 3665 */                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3666 */                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3667 */                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f14);
/*      */                       
/* 3669 */                       _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                       
/* 3671 */                       _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                       
/* 3673 */                       _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3674 */                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                       try {
/* 3676 */                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3677 */                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                           for (;;) {
/* 3679 */                             out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3680 */                             if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3741 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3742 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3682 */                             out.write("&method=showApplication\" title=\"");
/* 3683 */                             if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3741 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3742 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3685 */                             out.write("\"  class=\"staticlinks\">\n         ");
/* 3686 */                             if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3741 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3742 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3688 */                             out.write("\n    \t");
/* 3689 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3690 */                             out.write("</a></span>\t\n\t\t ");
/*      */                             
/* 3692 */                             PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3693 */                             _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3694 */                             _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 3696 */                             _jspx_th_logic_005fpresent_005f7.setRole("ADMIN");
/* 3697 */                             int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3698 */                             if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                               for (;;) {
/* 3700 */                                 out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3701 */                                 if (_jspx_meth_c_005fout_005f19(_jspx_th_logic_005fpresent_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3741 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3742 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3703 */                                 out.write(39);
/* 3704 */                                 out.write(44);
/* 3705 */                                 out.write(39);
/* 3706 */                                 out.print(resId);
/* 3707 */                                 out.write(39);
/* 3708 */                                 out.write(44);
/* 3709 */                                 out.write(39);
/* 3710 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3711 */                                 out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3712 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3713 */                                 out.write("\"  title=\"");
/* 3714 */                                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3741 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3742 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3716 */                                 out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3717 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3718 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3722 */                             if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3723 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3741 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3742 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3726 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3727 */                             out.write("\n\n\t\t \t");
/* 3728 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3729 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3733 */                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3741 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3742 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 3737 */                           int tmp10489_10488 = 0; int[] tmp10489_10486 = _jspx_push_body_count_c_005fforEach_005f1; int tmp10491_10490 = tmp10489_10486[tmp10489_10488];tmp10489_10486[tmp10489_10488] = (tmp10491_10490 - 1); if (tmp10491_10490 <= 0) break;
/* 3738 */                           out = _jspx_page_context.popBody(); }
/* 3739 */                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                       } finally {
/* 3741 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 3742 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                       }
/* 3744 */                       out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3745 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3746 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3750 */                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3751 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                   }
/*      */                   
/* 3754 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3755 */                   out.write(10);
/* 3756 */                   out.write(32);
/* 3757 */                   if (_jspx_meth_c_005fif_005f15(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                     return;
/* 3759 */                   out.write(32);
/* 3760 */                   out.write(10);
/*      */                 }
/*      */                 
/*      */               }
/* 3764 */               else if (mon)
/*      */               {
/*      */ 
/* 3767 */                 out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 3768 */                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 3770 */                 out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */               }
/*      */               
/*      */ 
/* 3774 */               out.write(9);
/* 3775 */               out.write(9);
/* 3776 */               out.write("\n\n</table>\n\n\n");
/* 3777 */               out.write(10);
/* 3778 */               response.setContentType("text/html;charset=UTF-8");
/* 3779 */               out.write(10);
/* 3780 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 3781 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3784 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 3785 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3788 */           if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 3789 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */           }
/*      */           
/* 3792 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 3793 */           out.write(10);
/* 3794 */           out.write(32);
/* 3795 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3796 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3800 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3801 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3804 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3805 */         out.write(10);
/* 3806 */         out.write(10);
/*      */       }
/* 3808 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3809 */         out = _jspx_out;
/* 3810 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3811 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3812 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3815 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3821 */     PageContext pageContext = _jspx_page_context;
/* 3822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3824 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3825 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3826 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3828 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3830 */     _jspx_th_tiles_005fput_005f0.setValue("JDK1.5 - Snapshot");
/* 3831 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3832 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3833 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3834 */       return true;
/*      */     }
/* 3836 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3837 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3842 */     PageContext pageContext = _jspx_page_context;
/* 3843 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3845 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3846 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3847 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3849 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3850 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3851 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3853 */         out.write(10);
/* 3854 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3855 */           return true;
/* 3856 */         out.write(10);
/* 3857 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3858 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3862 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3863 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3864 */       return true;
/*      */     }
/* 3866 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3867 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3872 */     PageContext pageContext = _jspx_page_context;
/* 3873 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3875 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3876 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3877 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3879 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3881 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 3882 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3883 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3884 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3885 */       return true;
/*      */     }
/* 3887 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3893 */     PageContext pageContext = _jspx_page_context;
/* 3894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3896 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3897 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3898 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3900 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3901 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3902 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3904 */         out.write(10);
/* 3905 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3906 */           return true;
/* 3907 */         out.write(10);
/* 3908 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3909 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3913 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3914 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3915 */       return true;
/*      */     }
/* 3917 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3918 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3923 */     PageContext pageContext = _jspx_page_context;
/* 3924 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3926 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3927 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3928 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3930 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 3932 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3933 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3934 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3935 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3936 */       return true;
/*      */     }
/* 3938 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3944 */     PageContext pageContext = _jspx_page_context;
/* 3945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3947 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3948 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3949 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3951 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3952 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3953 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3955 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 3956 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3957 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3961 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3962 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3963 */       return true;
/*      */     }
/* 3965 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3971 */     PageContext pageContext = _jspx_page_context;
/* 3972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3974 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3975 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3976 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3978 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 3980 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*      */     
/* 3982 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 3983 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3984 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3985 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3986 */       return true;
/*      */     }
/* 3988 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3989 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3994 */     PageContext pageContext = _jspx_page_context;
/* 3995 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3997 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3998 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3999 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4001 */     _jspx_th_html_005ftext_005f1.setProperty("jndiurl");
/*      */     
/* 4003 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 4005 */     _jspx_th_html_005ftext_005f1.setSize("25");
/* 4006 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4007 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4008 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4009 */       return true;
/*      */     }
/* 4011 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4012 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4017 */     PageContext pageContext = _jspx_page_context;
/* 4018 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4020 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4021 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 4022 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4024 */     _jspx_th_html_005ftext_005f2.setProperty("pollInterval");
/*      */     
/* 4026 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 4028 */     _jspx_th_html_005ftext_005f2.setSize("15");
/* 4029 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 4030 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 4031 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4032 */       return true;
/*      */     }
/* 4034 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4035 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4040 */     PageContext pageContext = _jspx_page_context;
/* 4041 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4043 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4044 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 4045 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4047 */     _jspx_th_html_005ftext_005f3.setProperty("port");
/*      */     
/* 4049 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 4051 */     _jspx_th_html_005ftext_005f3.setSize("15");
/* 4052 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 4053 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 4054 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4055 */       return true;
/*      */     }
/* 4057 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4058 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4063 */     PageContext pageContext = _jspx_page_context;
/* 4064 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4066 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4067 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 4068 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4070 */     _jspx_th_html_005ftext_005f4.setProperty("username");
/*      */     
/* 4072 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */     
/* 4074 */     _jspx_th_html_005ftext_005f4.setSize("15");
/* 4075 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 4076 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 4077 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4078 */       return true;
/*      */     }
/* 4080 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4081 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4086 */     PageContext pageContext = _jspx_page_context;
/* 4087 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4089 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4090 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 4091 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4093 */     _jspx_th_c_005fif_005f5.setTest("${!empty enabled}");
/* 4094 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 4095 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 4097 */         out.write("Edit Monitor");
/* 4098 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 4099 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4103 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 4104 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4105 */       return true;
/*      */     }
/* 4107 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4108 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4113 */     PageContext pageContext = _jspx_page_context;
/* 4114 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4116 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4117 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4118 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4120 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 4122 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 4123 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4124 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4125 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4126 */       return true;
/*      */     }
/* 4128 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4129 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4134 */     PageContext pageContext = _jspx_page_context;
/* 4135 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4137 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4138 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 4139 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4141 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 4142 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 4143 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 4145 */         out.write("\n\t \t\talertUser();\n\t \t\treturn;\n\t \t");
/* 4146 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 4147 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4151 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 4152 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4153 */       return true;
/*      */     }
/* 4155 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4156 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4161 */     PageContext pageContext = _jspx_page_context;
/* 4162 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4164 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4165 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4166 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4168 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 4169 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4170 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 4172 */         out.write("\n\t \t \t\talertUser();\n\t \t \t\treturn;\n\t \t \t");
/* 4173 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4174 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4178 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4179 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4180 */       return true;
/*      */     }
/* 4182 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fnotPresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4188 */     PageContext pageContext = _jspx_page_context;
/* 4189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4191 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4192 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4193 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f2);
/*      */     
/* 4195 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 4196 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4197 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4198 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4199 */       return true;
/*      */     }
/* 4201 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4207 */     PageContext pageContext = _jspx_page_context;
/* 4208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4210 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4211 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 4212 */     _jspx_th_logic_005fpresent_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4214 */     _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 4215 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 4216 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 4218 */         out.write("\n\t \talertUser();\n\t \treturn;\n\t ");
/* 4219 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 4220 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4224 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 4225 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 4226 */       return true;
/*      */     }
/* 4228 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 4229 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4234 */     PageContext pageContext = _jspx_page_context;
/* 4235 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4237 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4238 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4239 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 4241 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 4242 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4243 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4244 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4245 */       return true;
/*      */     }
/* 4247 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4248 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4253 */     PageContext pageContext = _jspx_page_context;
/* 4254 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4256 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4257 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4258 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 4260 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 4261 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4262 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4263 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4264 */       return true;
/*      */     }
/* 4266 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4267 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4272 */     PageContext pageContext = _jspx_page_context;
/* 4273 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4275 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4276 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4277 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 4279 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 4280 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4281 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4282 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4283 */       return true;
/*      */     }
/* 4285 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4286 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4291 */     PageContext pageContext = _jspx_page_context;
/* 4292 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4294 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4295 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4296 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 4298 */     _jspx_th_c_005fout_005f4.setValue("${haid}");
/* 4299 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4300 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4301 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4302 */       return true;
/*      */     }
/* 4304 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4305 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4310 */     PageContext pageContext = _jspx_page_context;
/* 4311 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4313 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4314 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4315 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 4317 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 4318 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4319 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4320 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4321 */       return true;
/*      */     }
/* 4323 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4324 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4329 */     PageContext pageContext = _jspx_page_context;
/* 4330 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4332 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4333 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4334 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 4336 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 4337 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4338 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4339 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4340 */       return true;
/*      */     }
/* 4342 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4343 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4348 */     PageContext pageContext = _jspx_page_context;
/* 4349 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4351 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4352 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4353 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 4355 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 4356 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4357 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4358 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4359 */       return true;
/*      */     }
/* 4361 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4362 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4367 */     PageContext pageContext = _jspx_page_context;
/* 4368 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4370 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4371 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4372 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4374 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 4375 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4376 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4377 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4378 */       return true;
/*      */     }
/* 4380 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4381 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4386 */     PageContext pageContext = _jspx_page_context;
/* 4387 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4389 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4390 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4391 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4393 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 4394 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4395 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4396 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4397 */       return true;
/*      */     }
/* 4399 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4400 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4405 */     PageContext pageContext = _jspx_page_context;
/* 4406 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4408 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4409 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4410 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4412 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 4413 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4414 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4415 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4416 */       return true;
/*      */     }
/* 4418 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4419 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4424 */     PageContext pageContext = _jspx_page_context;
/* 4425 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4427 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4428 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4429 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4431 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 4432 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4433 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4434 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4435 */       return true;
/*      */     }
/* 4437 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4443 */     PageContext pageContext = _jspx_page_context;
/* 4444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4446 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4447 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4448 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4450 */     _jspx_th_c_005fout_005f12.setValue("${ha.key}");
/* 4451 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4452 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4453 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4454 */       return true;
/*      */     }
/* 4456 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4457 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4462 */     PageContext pageContext = _jspx_page_context;
/* 4463 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4465 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4466 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4467 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4469 */     _jspx_th_c_005fout_005f13.setValue("${ha.value}");
/* 4470 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4471 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4472 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4473 */       return true;
/*      */     }
/* 4475 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4476 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4481 */     PageContext pageContext = _jspx_page_context;
/* 4482 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4484 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4485 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4486 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4488 */     _jspx_th_c_005fset_005f0.setVar("monitorName");
/* 4489 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4490 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4491 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4492 */         out = _jspx_page_context.pushBody();
/* 4493 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 4494 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4495 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4498 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4499 */           return true;
/* 4500 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4501 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4504 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4505 */         out = _jspx_page_context.popBody();
/* 4506 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 4509 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4510 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4511 */       return true;
/*      */     }
/* 4513 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4514 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4519 */     PageContext pageContext = _jspx_page_context;
/* 4520 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4522 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4523 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4524 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4526 */     _jspx_th_c_005fout_005f14.setValue("${ha.value}");
/* 4527 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4528 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4529 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4530 */       return true;
/*      */     }
/* 4532 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4533 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4538 */     PageContext pageContext = _jspx_page_context;
/* 4539 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4541 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4542 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4543 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fpresent_005f6);
/*      */     
/* 4545 */     _jspx_th_c_005fout_005f15.setValue("${ha.key}");
/* 4546 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4547 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4548 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4549 */       return true;
/*      */     }
/* 4551 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4552 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4557 */     PageContext pageContext = _jspx_page_context;
/* 4558 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4560 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4561 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4562 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 4564 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4565 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4566 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4567 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4568 */       return true;
/*      */     }
/* 4570 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4571 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4576 */     PageContext pageContext = _jspx_page_context;
/* 4577 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4579 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4580 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4581 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4583 */     _jspx_th_c_005fout_005f16.setValue("${ha.key}");
/* 4584 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4585 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4586 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4587 */       return true;
/*      */     }
/* 4589 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4590 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4595 */     PageContext pageContext = _jspx_page_context;
/* 4596 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4598 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4599 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4600 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4602 */     _jspx_th_c_005fout_005f17.setValue("${ha.value}");
/* 4603 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4604 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4605 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4606 */       return true;
/*      */     }
/* 4608 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4609 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4614 */     PageContext pageContext = _jspx_page_context;
/* 4615 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4617 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4618 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4619 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4621 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 4622 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4623 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4624 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4625 */         out = _jspx_page_context.pushBody();
/* 4626 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4627 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 4628 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4631 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4632 */           return true;
/* 4633 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4634 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4637 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4638 */         out = _jspx_page_context.popBody();
/* 4639 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 4642 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4643 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4644 */       return true;
/*      */     }
/* 4646 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4647 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4652 */     PageContext pageContext = _jspx_page_context;
/* 4653 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4655 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4656 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4657 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 4659 */     _jspx_th_c_005fout_005f18.setValue("${ha.value}");
/* 4660 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4661 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4662 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4663 */       return true;
/*      */     }
/* 4665 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4666 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4671 */     PageContext pageContext = _jspx_page_context;
/* 4672 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4674 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4675 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4676 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 4678 */     _jspx_th_c_005fout_005f19.setValue("${ha.key}");
/* 4679 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4680 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4681 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4682 */       return true;
/*      */     }
/* 4684 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4685 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4690 */     PageContext pageContext = _jspx_page_context;
/* 4691 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4693 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4694 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4695 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f7);
/*      */     
/* 4697 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 4698 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4699 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4700 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4701 */       return true;
/*      */     }
/* 4703 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4704 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4709 */     PageContext pageContext = _jspx_page_context;
/* 4710 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4712 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4713 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4714 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4716 */     _jspx_th_c_005fif_005f15.setTest("${empty associatedmgs}");
/* 4717 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4718 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 4720 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4721 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 4722 */           return true;
/* 4723 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 4724 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 4725 */           return true;
/* 4726 */         out.write("</td>\n\t ");
/* 4727 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4728 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4732 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4733 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4734 */       return true;
/*      */     }
/* 4736 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4737 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4742 */     PageContext pageContext = _jspx_page_context;
/* 4743 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4745 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4746 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4747 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 4749 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4750 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4751 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4752 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4753 */       return true;
/*      */     }
/* 4755 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4756 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4761 */     PageContext pageContext = _jspx_page_context;
/* 4762 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4764 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4765 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4766 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 4768 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 4769 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4770 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4771 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4772 */       return true;
/*      */     }
/* 4774 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4775 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4780 */     PageContext pageContext = _jspx_page_context;
/* 4781 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4783 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4784 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 4785 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4787 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4788 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 4789 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 4790 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4791 */       return true;
/*      */     }
/* 4793 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4794 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\JDK15Details_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */