/*      */ package org.apache.jsp.jsp.reports;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ 
/*      */ public final class ReportList_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  903 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
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
/*  917 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*  930 */     AMLog.debug("JSP : " + debugMessage);
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
/* 1385 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1430 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/*      */           catch (SQLException e) {
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
/* 1828 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1830 */               if (maxCol != null)
/* 1831 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1833 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1828 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 2188 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2189 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange_005falt;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange_005falt;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2218 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange_005falt = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange_005falt = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2244 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2248 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.release();
/* 2250 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/* 2254 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/* 2256 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.release();
/* 2257 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.release();
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.release();
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2260 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange_005falt.release();
/* 2261 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2262 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange_005falt.release();
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.release();
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2266 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.release();
/* 2267 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2268 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2275 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2278 */     JspWriter out = null;
/* 2279 */     Object page = this;
/* 2280 */     JspWriter _jspx_out = null;
/* 2281 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2285 */       response.setContentType("text/html;charset=UTF-8");
/* 2286 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2288 */       _jspx_page_context = pageContext;
/* 2289 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2290 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2291 */       session = pageContext.getSession();
/* 2292 */       out = pageContext.getOut();
/* 2293 */       _jspx_out = out;
/*      */       
/* 2295 */       out.write("<!--$Id$-->\n\n\n\n\n\n");
/* 2296 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2298 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2299 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2301 */       out.write(10);
/* 2302 */       out.write(10);
/* 2303 */       out.write(10);
/* 2304 */       out.write("\n\n\n\n\n\n\n\n\n\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/appreports.js\"></script>\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n<title>Report Tab Proto</title>\n<script language=\"JavaScript\">\n\n     \nvar fetchFl=0;\n\n\nvar prevObj=0;\nvar jk=0;\nvar dispFl=false;\n\n\nvar selMonResid ;\nfunction SelectService(dname,val,res,imgname)\n{\n\n\tif(document.forms[1].saturday){\n\t\tdocument.forms[1].saturday.value=val;\n\t}\n\tif(document.forms[1].resid){\n\t\tdocument.forms[1].resid.value=res;\n\t}\n\n\tdocument.getElementById(dname).style.display=\"none\";\n\tif (document.getElementById(dname)==\"service_list_center\")\n\t{\n\tdocument.getElementById('monitordisplayframe').style.display = 'none';\n\n\t}\n\tif(dname=='service_list_center'){\n\n\t\tif(val.length>20 && val.length<30) \t{\n\t\t   \t\tval = val.substring(0, 24);\n\n\n\t\t   \t}\n\t\telse if(val.length>=30){\n\t\t\tval = val.substring(0, 20);\n\n\t\t}\n\n\t\t$('#saturday').val(val);// NO I18N\n");
/* 2305 */       out.write("\t\tvar isEUMParent=false;\n\t\tvar url = \"/SeleniumActions.do?method=isEUMParent&resid=\"+res; // NO I18N\n\t\t$.ajax({\n\t\t\ttype:'GET', // NO I18N\n\t\t\turl:url,\n\t\t\tasync:false,\n\t\t\tsuccess : function(data){\n\t\t\t\tisEUMParent=data;\n\t\t\t}\n\t\t});\n\n\t}\n\tif (dname=='service_list_popup')\n\t{\n\tsendReport(document.forms[1].resid.value,document.forms[1].resid);\n\t}\n\tif (dname=='service_list_summary')\n\t{\n\tgetForm('generateSummaryReport');\n\t}\n\tselMonResid = res;\n\tdocument[imgname].src=\"../../images/icon_downarrow.gif\";\n\tdispFl = false;\n\tif(isEUMParent.indexOf(\"false\")>-1)\n\t{\n\t\tvar  url=\"/jsp/reports/TrendReportList.jsp?resid=\"+res+\"&todaytime=\"+");
/* 2306 */       out.print(System.currentTimeMillis());
/* 2307 */       out.write(";\n    \thttp.open(\"GET\",url,true);\n    \thttp.onreadystatechange = setAttributeList;\n    \thttp.send(null);\n    }\n\telse\n\t{\n\t\tdocument.getElementById(\"trendDiv\").style.display='none';\n  \t}\n\t\n}\n  \ndocument.body.onclick=CloseServiceList;\nfunction CloseServiceList()\n\n{\n\n\tif(dispFl == false)\n\n\t{\n\n\t\t if( document.getElementById(\"service_list_left\") ) {\n    \tdocument.getElementById(\"service_list_left\").style.display=\"none\";\n\t\tdocument.getElementById('leftmonitordisplayframe').style.display = 'none';\n\t\tdocument[\"leftimage\"].src=\"../../images/icon_downarrow.gif\";\n\t\t}\n\n\t\t if( document.getElementById(\"service_list_center\") ) {\n    \tdocument.getElementById(\"service_list_center\").style.display=\"none\";\n\t\tdocument.getElementById('monitordisplayframe').style.display = 'none';\n\t\tdocument[\"centerarrow\"].src=\"../../images/icon_downarrow.gif\";\n\t\t}\n\n\t    if( document.getElementById(\"service_list_popup\") ) {\n    \tdocument.getElementById(\"service_list_popup\").style.display=\"none\";\n\t\tdocument[\"centerimage\"].src=\"../../images/icon_downarrow.gif\";\n");
/* 2308 */       out.write("\t\t}\n\t if( document.getElementById(\"service_list_summary\") ) {\n    \tdocument.getElementById(\"service_list_summary\").style.display=\"none\";\n\t\tdocument[\"centerimage\"].src=\"../../images/icon_downarrow.gif\";\n\t\t}\n\t if( document.getElementById(\"service_list_left1\") ) {\n    \tdocument.getElementById(\"service_list_left1\").style.display=\"none\";\n\t\tdocument.getElementById('leftmonitordisplayframe1').style.display = 'none';\n\t\tdocument[\"leftimage1\"].src=\"../../images/icon_downarrow.gif\";\n\t\t}\n if( document.getElementById(\"service_list_left2\") ) {\n    \tdocument.getElementById(\"service_list_left2\").style.display=\"none\";\n\t\tdocument.getElementById('leftmonitordisplayframe2').style.display = 'none';\n\t\t}\n\n\t}\n\telse\n\t{\n\t\tdispFl = false;\n\t}\n}\n\nfunction mgAttributeForm(form) {\n\tvar a=form.sunday.options[form.sunday.selectedIndex].value;\n\tif(a=='resource'){\n\t\talert(\"");
/* 2309 */       out.print(FormatUtil.getString("am.webclient.customattribute.jsalert.text"));
/* 2310 */       out.write("\"); ");
/* 2311 */       out.write("\n\t\treturn;\n    } else {\n\t\tattributeForm(form)\n\t}\n}\n\n\n</script>\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n\n\n<script language=\"JavaScript1.2\">\n\n/*\nThe below code uses document.forms[1] to access the left form\nIf new form gets added to the basic layout then this index should be changed\n*/\n\n\n\n\nfunction clickForm2(a,b,c)\n{\n\n\t//b.value=selMonResid;\n      \n        document.forms[1].actionMethod.value=a;\n\t//document.forms[1].resid.selectedIndex=b.selectedIndex;\n        \n\tvar resid = selMonResid;\n\tif(c!=undefined && c==\"EUM\")\n\t{\n\t\tresid=b;\n\t}\n\tif(resid==null || resid==\"All\")\n\t{\n\t\talert(\"");
/* 2312 */       out.print(FormatUtil.getString("am.reporttab.heading.jsalert.downtimesummary.text"));
/* 2313 */       out.write("\");\n\t\treturn;\n\t}\n\tvar url=\"/showReports.do?actionMethod=\"+a+\"&resourceid=\"+resid+\"&period=0&Report=true&resourceType=Monitors&resid=\"+resid;\n\n\t window.open(url,'','resizable=yes,scrollbars=yes,width=950,height=700,top=15,left=15');\n\t//document.forms[1].submit();\n\n}\n\n\n\nfunction attributeFormAction(r,f)\n{\n\tvar cf='&customfield=false';\t// NO I18N\n\tvar resourcetype = r.value;\n    if(resourcetype.indexOf(\"$\") != -1){\n    \tvar selectName = 'selectFieldVal_'+r.name;\t// NO I18N\n    \tcf = \"&customfield=true&customFieldValue=\"+jQuery(\"#\"+selectName).val();\t// NO I18N\n    }\nvar a=f.options[f.selectedIndex].value;\n\n if(a=='resource'){\n     alert(\"");
/* 2314 */       out.print(FormatUtil.getString("am.webclient.customattribute.jsalert.text"));
/* 2315 */       out.write("\"); ");
/* 2316 */       out.write("\n     return;\n     }else{\nvar temp=a.split(\"#\");\n\n        document.forms[1].attribute.value=temp[1];\n        document.forms[1].workingdays.value='false';\n        document.forms[1].resourceType.value=temp[0];\n        document.forms[1].actionMethod.value='generateAttributeReport';\n      //  document.forms[1].submit();\n\tvar url=\"/showReports.do?actionMethod=generateAttributeReport&period=0&Report=true&resourceType=\"+temp[0]+\"&workingdays=false&showcfFilter=true&attribute=\"+temp[1]+cf;\n\t window.open(url,'','resizable=yes,scrollbars=yes,width=950,height=700,top=15,left=15');\n}\n\n} \n\n\nfunction sendReport(a,z)\n{\n\n        document.forms[1].actionMethod.value='generateMttrAvailablityReport';\n       a = selMonResid;\n       document.forms[1].Report.value='true';\n       document.forms[1].reporttype.value='Monitors';\n      //  document.forms[1].resid.selectedIndex=z.selectedIndex;\n        document.forms[1].resourceid.value=document.forms[1].resid.value;\n\n        if(a == null){\n\talert(\"");
/* 2317 */       out.print(FormatUtil.getString("am.reporttab.heading.jsalert.downtimesummary.text"));
/* 2318 */       out.write("\");\n\t//return false;\n\t}\n\telse{\n\n\tvar url=\"/showReports.do?actionMethod=generateMttrAvailablityReport&resourceid=\"+a+\"&period=0&Report=true&resourceType=Monitors\";\n\n\t window.open(url,'','resizable=yes,scrollbars=yes,width=950,height=700,top=15,left=15');\n       // document.forms[1].submit();\n        }\n\t//location.href=\"/showReports.do?actionMethod=generateMttrAvailablityReport&resourceid=\"+a+\"&period=\"+c+\"&Report=true&resourceType=Monitors\";\n}\nfunction outageReload()\n{\n\n        if(document.forms[1].thisstart.value=='')\n        {\n            alert(\"");
/* 2319 */       out.print(FormatUtil.getString("am.webclient.historydata.thisperiod.outage.jsalertforstarttime"));
/* 2320 */       out.write("\");\n            return false\n          }\n        else if(document.forms[1].thisend.value=='')\n        {\n            alert(\"");
/* 2321 */       out.print(FormatUtil.getString("am.webclient.historydata.thisperiod.outage.jsalertforendtime"));
/* 2322 */       out.write("\")\n            return false\n        }\n        else if(document.forms[1].laststart.value=='')\n        {\n            alert(\"");
/* 2323 */       out.print(FormatUtil.getString("am.webclient.historydata.lastperiod.outage.jsalertforstarttime"));
/* 2324 */       out.write("\");\n            return false\n          }\n        else if(document.forms[1].lastend.value=='')\n        {\n            alert(\"");
/* 2325 */       out.print(FormatUtil.getString("am.webclient.historydata.lastperiod.outage.jsalertforendtime"));
/* 2326 */       out.write("\")\n            return false\n        }\n        else if(document.forms[1].thisstart.value > document.forms[1].thisend.value)\n\n        {\n\n\n                alert(\"");
/* 2327 */       out.print(FormatUtil.getString("am.webclient.historydata.thisperiod.stGEend.outage.jsalertforendtime"));
/* 2328 */       out.write("\" );\n                document.forms[1].thisstart.value='';\n                document.forms[1].thisend.value='';\n                return false;\n\n         }\n          else if(document.forms[1].laststart.value > document.forms[1].lastend.value)\n\n        {\n\n\n                alert(\"");
/* 2329 */       out.print(FormatUtil.getString("am.webclient.historydata.lastperiod.stGEend.outage.jsalertforendtime"));
/* 2330 */       out.write("\" );\n                document.forms[1].laststart.value='';\n                document.forms[1].lastend.value='';\n                return false;\n\n         }\n\n         else\n         {\n\n\n            document.forms[1].submit();\n         }\n}\n\nfunction reload()\n{\n\n        if(document.forms[1].startDate.value=='')\n        {\n            alert(\"");
/* 2331 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/* 2332 */       out.write("\");\n            return false\n          }\n        else if(document.forms[1].endDate.value=='')\n        {\n            alert(\"");
/* 2333 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/* 2334 */       out.write("\")\n            return false\n        }\n        else if(document.forms[1].startDate.value > document.forms[1].endDate.value)\n\n        {\n\n\n                alert(\"");
/* 2335 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/* 2336 */       out.write("\" );\n                document.forms[1].startDate.value='';\n                document.forms[1].endDate.value='';\n                return false;\n\n         }\n         else\n         {\n\n            document.forms[1].period.value='4';\n                //alert('**********'+document.forms[1].submit());\n            document.forms[1].submit();\n         }\n}\n\n\n\nfunction generateReport(type)\n{\n\n var s=document.forms[1].interval.value;\n\n\n  if(type=='excel' && s=='customtime'){\n\n  if(document.forms[1].thisstart.value=='' || document.forms[1].thisend.value==''){\n\n  alert(\"");
/* 2337 */       out.print(FormatUtil.getString("am.webclient.historydata.customperiod.excel.outage.jsalert"));
/* 2338 */       out.write("\");\n\n  return;\n  }else{\n\n\n   document.forms[1].reporttype.value=type;\n\n    document.forms[1].submit();\n\n    }\n\n\n  }else{\n\n   document.forms[1].reporttype.value=type;\n\n    document.forms[1].submit();\n   }\n   document.forms[1].reporttype.value=\"html\";\n\n}\n\n\n\n\n</script>\n\n\n\n\n\n\n<script type=\"text/javascript\">\n\nonload = function() {\n\n/* Level 0 */\n\tvar getEls = document.getElementById('new-report-sub').getElementsByTagName(\"LI\");\n\tif(getEls.length >= 0)\n\t{\n\t\tvar elem = getEls[0];\n\t\tvar elemId = elem.id;\n\t\telem.className = \"current\";\n\t\tswitchOn(elem);\n\t}\n\tfor (var i=0; i<getEls.length; i++) {\n\t\tgetEls[i].onclick=function() {\n\t\tthis.className = this.className == 'clicked' ? '' : 'clicked';\n\n\t\tcheckClick ('new-report-sub',this)\n\n\t\tallOff ('sub2')\n\n\t\tswitchOn (this)\n\n\t\tswitchOff ('new-report')\n\t\t}\n\t}\n/* Level 1 */\n\tvar getEls = document.getElementById('new-report').getElementsByTagName(\"LI\");\n\tfor (var i=0; i<getEls.length; i++) {\n\t\tgetEls[i].onclick=function() {\n\t\tthis.className = this.className == 'clicked' ? '' : 'clicked';\n");
/* 2339 */       out.write("\n\t\tcheckClick ('new-report',this)\n\n\t\tswitchOn (this)\n\n\t\tswitchOff ('sub2')\t\t// NO I18N\n\t\t}\n\t}\n/* Level 2 */\n\tvar getEls = document.getElementById('sub2').getElementsByTagName(\"LI\");\n\tfor (var i=0; i<getEls.length; i++) {\n\t\tgetEls[i].onclick=function() {\n\n\t\tthis.className = '';\n\n\t\tliId = this.id.replace(\"no\", \"sub\");\n\t\tdocument.getElementById(liId).className = 'none';\n\n\t\tliId = this.id.replace(\"no\", \"top\");\n\t\tdocument.getElementById(liId).className = '';\n\t\t}\n\t}\n/*For allMG condition*/\n\tif(document.getElementById('allMGRem1_all'))\n\t{\n\t\tdocument.getElementById('allMGRem1_all').style.display = \"none\";\n\t}\n/*For Middleware condition*/\n\tif(document.getElementById('wliRem_2') != null)\n\t{\n\tdocument.getElementById('wliRem_2').style.display = \"none\";\n\t}\n}\n\n\n</script>\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n</head>\n\n<body>\n\n\t\t<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" class=\"padd-bottom\">\n\t\t\t<tr>\n\t\t\t");
/* 2340 */       String keyToDisplay = FormatUtil.getString("am.webclient.customattribute.heading.text");
/* 2341 */       out.write("\n\t\t\t");
/*      */       
/* 2343 */       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2344 */       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2345 */       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */       
/* 2347 */       _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2348 */       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2349 */       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */         for (;;) {
/* 2351 */           out.write("\n\t\t\t");
/* 2352 */           keyToDisplay = FormatUtil.getString("am.reporting.admin.summarymail.subject");
/* 2353 */           out.write("\n\t\t\t  ");
/* 2354 */           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2355 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2359 */       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2360 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */       }
/*      */       else {
/* 2363 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2364 */         out.write("\t\n\t\t\t");
/*      */         
/* 2366 */         if ((com.adventnet.appmanager.util.Constants.getUserType() != null) && (!com.adventnet.appmanager.util.Constants.getUserType().equals("F")))
/*      */         {
/*      */ 
/* 2369 */           out.write("\n\t\t\t\t<td align=\"right\" valign=\"top\">\n\t\t\t\t\t");
/*      */           
/* 2371 */           PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2372 */           _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2373 */           _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */           
/* 2375 */           _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/* 2376 */           int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2377 */           if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */             for (;;) {
/* 2379 */               out.write("\n\t\t\t\t\t\t");
/*      */               
/* 2381 */               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2382 */               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2383 */               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */               
/* 2385 */               _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2386 */               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2387 */               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                 for (;;) {
/* 2389 */                   out.write("\n\t\t\t\t\t\t\t<a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"bodytext\" align=\"right\">");
/* 2390 */                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 2391 */                   out.write("</a>\n\t\t\t\t\t\t");
/* 2392 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2393 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2397 */               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2398 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */               }
/*      */               
/* 2401 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2402 */               out.write("\n\t\t\t\t\t\t");
/* 2403 */               if (!com.adventnet.appmanager.util.Constants.isPrivilegedUser(request)) {
/* 2404 */                 out.write("\n\t\t\t\t\t\t&nbsp;|&nbsp;\n\t\t\t\t\t\t<a href=\"/customReports.do?method=showCustomReports\" class=\"bodytext\" align=\"right\">");
/* 2405 */                 out.print(keyToDisplay);
/* 2406 */                 out.write("</a>\n\t\t\t\t\t\t");
/*      */               }
/* 2408 */               out.write("\n\t\t\t\t\t");
/* 2409 */               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2410 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2414 */           if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2415 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */           }
/*      */           
/* 2418 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2419 */           out.write("\n\t\t\t");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/* 2425 */           out.write("\n\t\t\t\t<td align=\"right\" valign=\"top\" title=\"");
/* 2426 */           out.print(FormatUtil.getString("am.webclient.freeedition.disabled.function.message"));
/* 2427 */           out.write("\">\n\t\t\t\t\t");
/*      */           
/* 2429 */           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2430 */           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2431 */           _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */           
/* 2433 */           _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,ENTERPRISEADMIN");
/* 2434 */           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2435 */           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */             for (;;) {
/* 2437 */               out.write("\n\t\t\t\t\t\t");
/*      */               
/* 2439 */               PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2440 */               _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2441 */               _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */               
/* 2443 */               _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 2444 */               int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2445 */               if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                 for (;;) {
/* 2447 */                   out.write("\n\t\t\t\t\t\t\t<a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"bodytext\" align=\"right\">");
/* 2448 */                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 2449 */                   out.write("</a>&nbsp;|&nbsp;\n\t\t\t\t\t\t");
/* 2450 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2451 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2455 */               if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2456 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */               }
/*      */               
/* 2459 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2460 */               out.write("\n                        <a href=\"/customReports.do?method=showCustomReports\" class=\"bodytext\" align=\"right\">");
/* 2461 */               out.print(keyToDisplay);
/* 2462 */               out.write("</a>\n\t\t\t\t\t");
/* 2463 */               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2464 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2468 */           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2469 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */           }
/*      */           
/* 2472 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2473 */           out.write("\n\t\t\t");
/*      */         }
/*      */         
/*      */ 
/* 2477 */         out.write("\n\t\t\t</td></tr>\n\t\t</table>\n\n");
/*      */         
/* 2479 */         boolean isPrivilegedUser = com.adventnet.appmanager.util.Constants.isPrivilegedUser(request);
/* 2480 */         MyFields fields = new MyFields();
/* 2481 */         ArrayList custInfo = MyFields.getDollarTagsforReports();
/* 2482 */         String category = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 2483 */         pageContext.setAttribute("category", category);
/* 2484 */         ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/* 2485 */         ArrayList list = frm.getMonitors();
/* 2486 */         ArrayList list1 = frm.getApplications();
/* 2487 */         boolean isListEmpty = false;
/* 2488 */         if ((list1 == null) || (list1.isEmpty())) {
/* 2489 */           isListEmpty = true;
/*      */         }
/* 2491 */         ArrayList list2 = frm.getVmapplications();
/* 2492 */         Hashtable rGroupCountHash = (Hashtable)request.getAttribute("ResourceGroupCount");
/* 2493 */         Hashtable rGroupCustomHash = (Hashtable)request.getAttribute("CustomResGroupCount");
/* 2494 */         int menucount = rGroupCountHash.size();
/*      */         
/* 2496 */         int height1 = 500;
/* 2497 */         int height2 = 479;
/* 2498 */         int height3 = 270;
/* 2499 */         if (menucount > 14)
/*      */         {
/* 2501 */           height1 = 700;
/* 2502 */           height2 = 679;
/* 2503 */           height3 = 350;
/*      */         }
/* 2505 */         else if (menucount >= 12)
/*      */         {
/* 2507 */           height1 = 600;
/* 2508 */           height2 = 579;
/* 2509 */           height3 = 300;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2518 */         out.write("\n<script>\nfunction changeHeight(type)\n{\n\tvar row=document.getElementById('reportTable').rows;\n\tvar cell=row[0].cells;\n\tif(type=='SAP_ALL')\n\t{\n\t\tcell[0].height='740';\n\t\tdocument.getElementById(\"top_28\").style.height ='700';\n\t\ttableHeightChanged='true';\n\t}\n\telse if(type=='APPSERVER')\n\t{\n\t\tcell[0].height='600';//No i18n\n\t\tdocument.getElementById(\"top_20\").style.height ='579';//No i18n\n\t\ttableHeightChanged='true';//No i18n\n\t}\n\telse if(type=='TREND')\n\t{\n\t\tcell[0].height='600';//No i18n\n\t\tdocument.getElementById(\"top_18\").style.height ='579';//No i18n\n\t\ttableHeightChanged='true';//No i18n\n\t}\n\n         else if(type=='capacity')\n\t{\n\t\tcell[0].height='600';//No i18n\n               document.getElementById(\"top_39\").style.height ='579';//No i18n\n\n\t\ttableHeightChanged='true';//No i18n\n\t}\n        else\n\t{\n\t\tcell[0].height=");
/* 2519 */         out.print(height1);
/* 2520 */         out.write(";\n\t\tdocument.getElementById(\"top_28\").style.height =");
/* 2521 */         out.print(height2);
/* 2522 */         out.write(";\n\t\tdocument.getElementById(\"top_20\").style.height =");
/* 2523 */         out.print(height2);
/* 2524 */         out.write(";\n\t\tdocument.getElementById(\"top_18\").style.height =");
/* 2525 */         out.print(height2);
/* 2526 */         out.write(";\n\t\ttableHeightChanged='false';\n\t}\n}\n</script>\n\n\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" width=\"99%\">\n<tr>\n<td class=\"vcenter-shadow-tp-lcorn\" width=\"8\"><img src=\"/images/spacer.gif\" width=\"7\" height=\"9\" /></td>\n<td class=\"vcenter-shadow-tp-mtile\"></td>\n<td class=\"vcenter-shadow-tp-rcorn\" width=\"7\"><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n</tr>\n<tr>\n<td class=\"vcenter-shadow-lfttile\" ><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n\n\n<!-- Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\">\n\n<div class=\"report-content\">\n<div id=\"content\">\n\n\n<div id=\"menu\">\n\n<div id=\"new-report-sub\">\n\t<ul id=\"sub_0\">\n\n\t\t<table id=\"reportTable\" width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"new-report-table\">\n\t\t<tr><td height=\"");
/* 2527 */         out.print(height1);
/* 2528 */         out.write("px\"  valign=\"top\" class=\"new-report-tdbg\">\n\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"new-reporet-lft\" >\n       <tr height=\"40px\"><td  class=\"report-heading new-report-text\" style=\"height:68px;\"><img src=\"/images/chart_bar.png\" style=\"position:relative; top:16px;left:13px;\"> <span style=\"position:relative; top:8px;  left:17px;\">");
/* 2529 */         out.print(FormatUtil.getString("webclient.heading.reports.text"));
/* 2530 */         out.write("</span><br /><br /></td></tr>\t\t\n           ");
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2535 */         CustomerManagementAPI.getInstance();Properties siteProp = CustomerManagementAPI.getSiteProp(request);
/* 2536 */         CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 2537 */         String loginName = request.getUserPrincipal().getName();
/* 2538 */         CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/* 2539 */         boolean showBSGReportOption = false;
/* 2540 */         if (((siteProp == null) || (siteProp.isEmpty())) && (showAllBSGs))
/*      */         {
/* 2542 */           showBSGReportOption = true;
/*      */         }
/* 2544 */         if (showBSGReportOption)
/*      */         {
/*      */ 
/* 2547 */           out.write("\n\t<tr ><td  class=\"new-tableheading-report \" align=\"right\" ><span onclick=\"toggle('top_1')\"  ><li id=\"top_1\"  class=\"current\"   ><img src=\"/images/icon_monitors_mg.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2548 */           out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/* 2549 */           out.write("<br /></li></span></td></tr>\n\n\t\t");
/*      */         }
/*      */         
/*      */ 
/* 2553 */         out.write(10);
/*      */         
/* 2555 */         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 2556 */         _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2557 */         _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */         
/* 2559 */         _jspx_th_logic_005fnotEmpty_005f0.setName("ReportForm");
/*      */         
/* 2561 */         _jspx_th_logic_005fnotEmpty_005f0.setProperty("customTypes");
/* 2562 */         int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2563 */         if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */           for (;;) {
/* 2565 */             out.write(10);
/*      */             
/* 2567 */             if (rGroupCountHash.get("CustomMOType") != null)
/*      */             {
/*      */ 
/* 2570 */               out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_2')\" ><li id=\"top_2\"  ><img src=\"/images/custom-report.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2571 */               out.print(FormatUtil.getString("Custom Types"));
/* 2572 */               out.write("<br /></li></span></td></tr>\n");
/*      */             }
/*      */             
/*      */ 
/* 2576 */             out.write(10);
/* 2577 */             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2578 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2582 */         if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2583 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */         }
/*      */         else {
/* 2586 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2587 */           out.write(10);
/*      */           
/* 2589 */           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 2590 */           _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2591 */           _jspx_th_logic_005fnotEmpty_005f1.setParent(null);
/*      */           
/* 2593 */           _jspx_th_logic_005fnotEmpty_005f1.setName("ReportForm");
/*      */           
/* 2595 */           _jspx_th_logic_005fnotEmpty_005f1.setProperty("monitors");
/* 2596 */           int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2597 */           if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */             for (;;) {
/* 2599 */               out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_3')\" ><li id=\"top_3\" ><img src=\"/images/icon-anamoly-responsetime.gif\" class=\"report-icons-padd\">&nbsp;");
/* 2600 */               out.print(FormatUtil.getString("am.reporttab.glancereport.text"));
/* 2601 */               out.write("<br /></li></span></td></tr>\n");
/* 2602 */               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2603 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2607 */           if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2608 */             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/*      */           }
/*      */           else {
/* 2611 */             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2612 */             out.write(10);
/*      */             
/* 2614 */             if (rGroupCountHash.get("SYS") != null)
/*      */             {
/* 2616 */               int noOfMos = ((Integer)rGroupCountHash.get("SYS")).intValue();
/* 2617 */               if (rGroupCustomHash.get("SYS") != null) {
/* 2618 */                 noOfMos -= ((Integer)rGroupCustomHash.get("SYS")).intValue();
/*      */               }
/* 2620 */               if (noOfMos > 0)
/*      */               {
/* 2622 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_4')\" ><li id=\"top_4\" ><img src=\"/images/icon_hyper_vhost.gif\" class=\"report-icons-padd\">&nbsp; ");
/* 2623 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 2624 */                 out.write("<br /></li></span></td></tr>\n");
/*      */               }
/*      */             }
/* 2627 */             if (rGroupCountHash.get("APP") != null) {
/* 2628 */               int noOfMos = ((Integer)rGroupCountHash.get("APP")).intValue();
/* 2629 */               if (rGroupCustomHash.get("APP") != null) {
/* 2630 */                 noOfMos -= ((Integer)rGroupCustomHash.get("APP")).intValue();
/*      */               }
/* 2632 */               if (noOfMos > 0)
/*      */               {
/* 2634 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_5')\" ><li id=\"top_5\" ><img src=\"/images/icon_monitors_memcached.gif\" class=\"report-icons-padd\">&nbsp; ");
/* 2635 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 2636 */                 out.write("<br /></li></span></td></tr>\n\n");
/*      */               }
/*      */             }
/* 2639 */             if (rGroupCountHash.get("DBS") != null)
/*      */             {
/* 2641 */               int noOfMos = ((Integer)rGroupCountHash.get("DBS")).intValue();
/* 2642 */               if (rGroupCustomHash.get("DBS") != null) {
/* 2643 */                 noOfMos -= ((Integer)rGroupCustomHash.get("DBS")).intValue();
/*      */               }
/* 2645 */               if (noOfMos > 0)
/*      */               {
/* 2647 */                 out.write("\n    <tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_6')\" ><li id=\"top_6\" ><img src=\"/images/icon_monitor_rds_ins.gif\" class=\"report-icons-padd\">&nbsp; ");
/* 2648 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 2649 */                 out.write(" <br /></li></span></td></tr>\n");
/*      */               }
/*      */             }
/* 2652 */             if (rGroupCountHash.get("Web Service") != null)
/*      */             {
/*      */ 
/* 2655 */               out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_7')\" ><li id=\"top_7\" ><img src=\"/images/icon_monitors_webapp.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2656 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 2657 */               out.write("<br /></li></span></td></tr>\n");
/*      */             }
/*      */             
/* 2660 */             if (rGroupCountHash.get("WEB-server") != null)
/*      */             {
/*      */ 
/* 2663 */               out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_8')\" ><li id=\"top_8\" ><img src=\"/images/icon_monitor_DNS.gif\" class=\"report-icons-padd\">&nbsp; ");
/* 2664 */               out.print(FormatUtil.getString("am.webclient.reports.reportlist.webserver.text"));
/* 2665 */               out.write("<br /></li></span></td></tr>\n");
/*      */             }
/*      */             
/* 2668 */             if (rGroupCountHash.get("UrlMonitor") != null)
/*      */             {
/*      */ 
/* 2671 */               out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_9')\" ><li id=\"top_9\" ><img src=\"/images/icon_monitors_urlmonitor.gif\" class=\"report-icons-padd\">&nbsp; ");
/* 2672 */               out.print(FormatUtil.getString("am.webclient.reports.reportlist.urls.text"));
/* 2673 */               out.write("<br /></li></span></td></tr>\n");
/*      */             }
/*      */             
/* 2676 */             if (rGroupCountHash.get("SER") != null)
/*      */             {
/* 2678 */               int noOfMos = ((Integer)rGroupCountHash.get("SER")).intValue();
/* 2679 */               if (rGroupCustomHash.get("SER") != null) {
/* 2680 */                 noOfMos -= ((Integer)rGroupCustomHash.get("SER")).intValue();
/*      */               }
/* 2682 */               if (noOfMos > 0)
/*      */               {
/* 2684 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_10')\" ><li id=\"top_10\" ><img src=\"/images/report-service.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2685 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 2686 */                 out.write("<br /></li></span></td></tr>\n");
/*      */               }
/*      */             }
/* 2689 */             if (rGroupCountHash.get("MS") != null)
/*      */             {
/* 2691 */               int noOfMos = ((Integer)rGroupCountHash.get("MS")).intValue();
/* 2692 */               if (rGroupCustomHash.get("MS") != null) {
/* 2693 */                 noOfMos -= ((Integer)rGroupCustomHash.get("MS")).intValue();
/*      */               }
/* 2695 */               if (noOfMos > 0)
/*      */               {
/* 2697 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_11')\" ><li id=\"top_11\" ><img src=\"/images/report-email.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2698 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 2699 */                 out.write("<br /></li></span></td></tr>\n");
/*      */               }
/*      */             }
/* 2702 */             if (rGroupCountHash.get("TM") != null)
/*      */             {
/* 2704 */               int noOfMos = ((Integer)rGroupCountHash.get("TM")).intValue();
/* 2705 */               if (rGroupCustomHash.get("TM") != null) {
/* 2706 */                 noOfMos -= ((Integer)rGroupCustomHash.get("TM")).intValue();
/*      */               }
/* 2708 */               if (noOfMos > 0)
/*      */               {
/* 2710 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_12')\" ><li id=\"top_12\" ><img src=\"/images/java_actions.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2711 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.transaction"));
/* 2712 */                 out.write("<br /></li></span></td></tr>\n");
/*      */               }
/*      */             }
/* 2715 */             if (rGroupCountHash.get("ERP") != null)
/*      */             {
/* 2717 */               int noOfMos = ((Integer)rGroupCountHash.get("ERP")).intValue();
/* 2718 */               if (rGroupCustomHash.get("ERP") != null) {
/* 2719 */                 noOfMos -= ((Integer)rGroupCustomHash.get("ERP")).intValue();
/*      */               }
/* 2721 */               if (noOfMos > 0)
/*      */               {
/* 2723 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_13')\" ><li id=\"top_13\" ><img src=\"/images/icon_monitors_app.gif\" class=\"report-icons-padd\">&nbsp; ");
/* 2724 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 2725 */                 out.write("<br /></li></span></td></tr>\n");
/*      */               }
/*      */             }
/* 2728 */             if (rGroupCountHash.get("MOM") != null)
/*      */             {
/* 2730 */               int noOfMos = ((Integer)rGroupCountHash.get("MOM")).intValue();
/* 2731 */               if (rGroupCustomHash.get("MOM") != null) {
/* 2732 */                 noOfMos -= ((Integer)rGroupCustomHash.get("MOM")).intValue();
/*      */               }
/* 2734 */               if (noOfMos > 0)
/*      */               {
/* 2736 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_14')\" ><li id=\"top_14\" ><img src=\"/images/icon_monitors_cam.gif\" class=\"report-icons-padd\">&nbsp; ");
/* 2737 */                 out.print(FormatUtil.getString("Middleware/Portal"));
/* 2738 */                 out.write("<br /></li></span></td></tr>\n");
/*      */               }
/*      */             }
/*      */             
/* 2742 */             if (rGroupCountHash.get("VIR") != null)
/*      */             {
/* 2744 */               int noOfMos = ((Integer)rGroupCountHash.get("VIR")).intValue();
/* 2745 */               if (rGroupCustomHash.get("VIR") != null) {
/* 2746 */                 noOfMos -= ((Integer)rGroupCustomHash.get("VIR")).intValue();
/*      */               }
/* 2748 */               if (noOfMos > 0)
/*      */               {
/* 2750 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_35')\" ><li id=\"top_35\" ><img src=\"/images/icon_monitor_vmware.gif\" class=\"report-icons-padd\">&nbsp; ");
/* 2751 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.virtualserver"));
/* 2752 */                 out.write("<br /></li></span></td></tr>\n");
/*      */               }
/*      */             }
/*      */             
/* 2756 */             if (rGroupCountHash.get("CLD") != null)
/*      */             {
/* 2758 */               int noOfMos = ((Integer)rGroupCountHash.get("CLD")).intValue();
/* 2759 */               if (rGroupCustomHash.get("CLD") != null) {
/* 2760 */                 noOfMos -= ((Integer)rGroupCustomHash.get("CLD")).intValue();
/*      */               }
/* 2762 */               if (noOfMos > 0)
/*      */               {
/* 2764 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_36')\" ><li id=\"top_36\" ><img src=\"/images/icon_monitor_ec2_ins.gif\" class=\"report-icons-padd\">&nbsp; ");
/* 2765 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.cloudapps"));
/* 2766 */                 out.write("<br /></li></span></td></tr>\n");
/*      */               }
/*      */             }
/*      */             
/* 2770 */             out.write(10);
/* 2771 */             out.write(10);
/*      */             
/* 2773 */             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2774 */             _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 2775 */             _jspx_th_logic_005fnotEmpty_005f2.setParent(null);
/*      */             
/* 2777 */             _jspx_th_logic_005fnotEmpty_005f2.setName("CAMData");
/* 2778 */             int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 2779 */             if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */               for (;;) {
/* 2781 */                 out.write("\n  \t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_15')\" ><li id=\"top_15\" ><img src=\"/images/custom-report.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2782 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 2783 */                 out.write("<br /></li></span></td></tr>\n");
/* 2784 */                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 2785 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2789 */             if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 2790 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/*      */             }
/*      */             else {
/* 2793 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 2794 */               out.write(10);
/*      */               
/* 2796 */               int noOfMosVM = 0;
/* 2797 */               if (rGroupCountHash.get("SYS") != null)
/*      */               {
/* 2799 */                 noOfMosVM = ((Integer)rGroupCountHash.get("SYS")).intValue();
/* 2800 */                 if (rGroupCustomHash.get("SYS") != null)
/*      */                 {
/*      */ 
/*      */ 
/* 2804 */                   noOfMosVM -= ((Integer)rGroupCustomHash.get("SYS")).intValue();
/*      */                 }
/*      */               }
/* 2807 */               if (rGroupCountHash.get("VIR") != null)
/*      */               {
/* 2809 */                 int noOfMos1 = ((Integer)rGroupCountHash.get("VIR")).intValue();
/* 2810 */                 noOfMosVM += noOfMos1;
/*      */               }
/* 2812 */               if (noOfMosVM > 0)
/*      */               {
/* 2814 */                 out.write("\n\t<tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_38')\" ><li id=\"top_38\" ><img src=\"/images/icon-capacity-small.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2815 */                 out.print(FormatUtil.getString("am.vmreports.capacityplanning.tabletitle"));
/* 2816 */                 out.write("<br /></li></span></td></tr>\n");
/*      */               }
/*      */               
/*      */ 
/* 2820 */               if ((com.adventnet.appmanager.util.Constants.getUserType() != null) && (!com.adventnet.appmanager.util.Constants.getUserType().equals("F")))
/*      */               {
/* 2822 */                 if (rGroupCountHash.get("eumMOType") != null)
/*      */                 {
/* 2824 */                   int noOfMos = ((Integer)rGroupCountHash.get("eumMOType")).intValue();
/* 2825 */                   if (noOfMos > 0)
/*      */                   {
/*      */ 
/* 2828 */                     out.write("\n                                <tr><td class=\"new-tableheading-report \"><span onclick=\"toggle('top_37')\" ><li id=\"top_37\"><img src=\"/images/eum-report.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2829 */                     out.print(FormatUtil.getString("am.webclient.eum.withacronym"));
/* 2830 */                     out.write("<br /></li></span></td></tr> ");
/* 2831 */                     out.write("\n                              \t\t\t");
/*      */                   }
/*      */                 }
/* 2834 */                 out.write("\n\t\t \t \t\t\t\t\t\t<tr>\n\t\t \t \t\t\t\t\t\t\t<td class=\"new-tableheading-report \">\n\t\t \t \t\t\t\t\t\t\t\t<span onclick=\"toggle('top_40')\" >\n\t\t \t \t\t\t\t\t\t\t\t\t<li id=\"top_40\" ><img src=\"/images/forecast-report.png\" height=\"16\" width=\"16\" class=\"report-icons-padd\">&nbsp; ");
/* 2835 */                 out.print(FormatUtil.getString("am.webclient.forecast.report.text"));
/* 2836 */                 out.write("<br /></li>\n\t\t \t \t\t\t\t\t\t\t\t</span>\n\t\t \t \t\t\t\t\t\t\t</td>\n\t\t \t \t\t\t\t\t\t</tr>\n        ");
/*      */                 
/* 2838 */                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2839 */                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 2840 */                 _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */                 
/* 2842 */                 _jspx_th_logic_005fpresent_005f5.setRole("ADMIN,ENTERPRISEADMIN");
/* 2843 */                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 2844 */                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                   for (;;) {
/* 2846 */                     out.write(10);
/*      */                     
/*      */ 
/*      */ 
/* 2850 */                     if ((!com.adventnet.appmanager.util.OEMUtil.isRemove("it360.hide.slamanagement")) || (EnterpriseUtil.isIt360MSPAdminServer()))
/*      */                     {
/* 2852 */                       out.write("\n<tr><td class=\"new-tableheading-report \">\n\t\t\n<span onclick=\"location.href='/showBussiness.do?method=generateApplicationAvailablity&selectTabName=SLA'\" ><li ><img src=\"/images/report-sla.png\" class=\"report-icons-padd\">&nbsp; ");
/* 2853 */                       out.print(FormatUtil.getString("am.webclient.selectmonitorview.SLAview.text"));
/* 2854 */                       out.write("<br /></li></span>\n\t\t\n</td></tr>\n");
/*      */                     }
/* 2856 */                     out.write("\n\n        ");
/* 2857 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 2858 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2862 */                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 2863 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                 }
/*      */                 
/* 2866 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 2867 */                 out.write("\n                        ");
/*      */               }
/*      */               
/*      */ 
/* 2871 */               out.write("\n</table>\n    </td></tr></table>\n\t</ul>\n</div>\n");
/*      */               
/* 2873 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 2874 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2875 */               _jspx_th_html_005fform_005f0.setParent(null);
/*      */               
/* 2877 */               _jspx_th_html_005fform_005f0.setAction("/showReports.do");
/* 2878 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2879 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 2881 */                   out.write(10);
/* 2882 */                   out.write(9);
/* 2883 */                   if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2885 */                   out.write(10);
/* 2886 */                   out.write(9);
/* 2887 */                   if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2889 */                   out.write("\n\t<input type=\"hidden\" name=\"period\" value=\"0\">\n\t");
/* 2890 */                   if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2892 */                   out.write(10);
/* 2893 */                   out.write(9);
/* 2894 */                   if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2896 */                   out.write(10);
/* 2897 */                   out.write(9);
/* 2898 */                   if (_jspx_meth_html_005fhidden_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2900 */                   out.write(10);
/* 2901 */                   out.write(9);
/* 2902 */                   if (_jspx_meth_html_005fhidden_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2904 */                   out.write(10);
/* 2905 */                   out.write(9);
/* 2906 */                   if (_jspx_meth_html_005fhidden_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2908 */                   out.write(10);
/* 2909 */                   out.write(9);
/* 2910 */                   if (_jspx_meth_html_005fhidden_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2912 */                   out.write(10);
/* 2913 */                   out.write(9);
/* 2914 */                   if (_jspx_meth_html_005fhidden_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2916 */                   out.write(10);
/* 2917 */                   out.write(9);
/* 2918 */                   if (_jspx_meth_html_005fhidden_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2920 */                   out.write(10);
/* 2921 */                   out.write(9);
/* 2922 */                   if (_jspx_meth_html_005fhidden_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2924 */                   out.write(10);
/* 2925 */                   out.write(9);
/* 2926 */                   if (_jspx_meth_html_005fhidden_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2928 */                   out.write(10);
/* 2929 */                   out.write(9);
/* 2930 */                   if (_jspx_meth_html_005fhidden_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2932 */                   out.write(10);
/* 2933 */                   out.write(9);
/* 2934 */                   if (_jspx_meth_html_005fhidden_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2936 */                   out.write(10);
/* 2937 */                   out.write(9);
/* 2938 */                   if (_jspx_meth_html_005fhidden_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2940 */                   out.write(10);
/* 2941 */                   out.write(9);
/* 2942 */                   if (_jspx_meth_html_005fhidden_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2944 */                   out.write("\n\n\n<div id=\"new-report\">\n\t");
/*      */                   
/* 2946 */                   if (showBSGReportOption)
/*      */                   {
/*      */ 
/* 2949 */                     out.write("\n\t<ul id=\"sub_1\" class=\"yes\">\n\t\t<li id=\"top_16\" style=\"height:");
/* 2950 */                     out.print(height2);
/* 2951 */                     out.write("px;\">\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\n\n\t\t");
/*      */                     
/* 2953 */                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 2954 */                     _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 2955 */                     _jspx_th_logic_005fnotEmpty_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 2957 */                     _jspx_th_logic_005fnotEmpty_005f3.setName("ReportForm");
/*      */                     
/* 2959 */                     _jspx_th_logic_005fnotEmpty_005f3.setProperty("applications");
/* 2960 */                     int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 2961 */                     if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */                       for (;;) {
/* 2963 */                         out.write("\n\n\t\t   \t\t<tr>\n  \t            ");
/*      */                         
/* 2965 */                         int listsize1 = list1.size();
/*      */                         
/* 2967 */                         int divlength1 = 80;
/*      */                         
/* 2969 */                         if ((listsize1 > 10) && (listsize1 < 20))
/*      */                         {
/* 2971 */                           divlength1 = 130;
/*      */                         }
/* 2973 */                         else if (listsize1 > 25)
/*      */                         {
/* 2975 */                           divlength1 = 200;
/*      */                         }
/*      */                         
/* 2978 */                         out.write("\n  \t            <td valign=\"top\"  colspan=\"3\" class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" width:70%>\n\t\t    &nbsp; &nbsp; &nbsp;");
/* 2979 */                         out.print(FormatUtil.getString("am.webclient.reports.filter.mg.text"));
/* 2980 */                         out.write(" &nbsp;\n  \t            ");
/*      */                         
/* 2982 */                         HiddenTag _jspx_th_html_005fhidden_005f16 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 2983 */                         _jspx_th_html_005fhidden_005f16.setPageContext(_jspx_page_context);
/* 2984 */                         _jspx_th_html_005fhidden_005f16.setParent(_jspx_th_logic_005fnotEmpty_005f3);
/*      */                         
/* 2986 */                         _jspx_th_html_005fhidden_005f16.setProperty("haid");
/*      */                         
/* 2988 */                         _jspx_th_html_005fhidden_005f16.setValue(((Properties)list1.get(0)).getProperty("value"));
/* 2989 */                         int _jspx_eval_html_005fhidden_005f16 = _jspx_th_html_005fhidden_005f16.doStartTag();
/* 2990 */                         if (_jspx_th_html_005fhidden_005f16.doEndTag() == 5) {
/* 2991 */                           this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f16); return;
/*      */                         }
/*      */                         
/* 2994 */                         this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f16);
/* 2995 */                         out.write("\n\n\n\n\n  \t            <!-- input  type=\"text\" style=\"height:25px;\" class=\"formtext input-down-arrow normal\" size=\"24\" name=\"monday\" id=\"monday\" onMousedown=\"DisplayServiceList1('service_list_left1','leftimage1')\"  value='");
/* 2996 */                         out.print(((Properties)list1.get(0)).getProperty("label"));
/* 2997 */                         out.write("' readonly-->\n  \t             <input  type=\"text\" style=\"height:25px;\" class=\"formtext input-down-arrow default\" size=\"30\" name=\"monday\" id=\"monday\" onMousedown=\"DisplayServiceList1('service_list_left1','leftimage1')\"  value='");
/* 2998 */                         out.print(((Properties)list1.get(0)).getProperty("label"));
/* 2999 */                         out.write("' readonly> <span id=\"customFieldValuesFilterby\"></span> ");
/* 3000 */                         out.write("\n  \t             \n  \t            \t<img src=\"../../images/icon_downarrow.gif\" class=\"drop-downimg\" name=\"leftimage1\"  align=\"absmiddle\" onClick=\"DisplayServiceList1('service_list_left1','leftimage1')\" style=\"display:none\">\n  \t            \t");
/* 3001 */                         out.write("\n\n\n\n\n\n  \t           <div id=\"dummyid\" style=\"width:0px; height:0px; overflow:none;\">\n  \t           <div id=\"leftmonitordisplayframe1\" style=\"display:none\"><iframe src=\"/images/icon_message_success.gif\" style=\"position:absolute; width:400; height:");
/* 3002 */                         out.print(divlength1);
/* 3003 */                         out.write("; z-index:0;\" id=\"leftmonitordisplay1\" frameborder=\"0\"></iframe></div></div>\n  \t          <div id=\"dummyid\" style=\"width:0px; height:0px; overflow:none;\">\n  \t          <div id=\"service_list_left1\" class=\"formtext default\" style=\"overflow:auto; left:142px; left:145px\\9; height:auto; min-width:280px;max-height:450px; display:none; position:absolute; background:#FFFFFF; margin-top: -2px; \">\n  \t          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  \t           ");
/* 3004 */                         for (int i = 0; i < list1.size(); i++) {
/* 3005 */                           out.write("\n  \t             <tr>\n\t  \t            <td id=\"");
/* 3006 */                           out.print(((Properties)list1.get(i)).getProperty("value"));
/* 3007 */                           out.write("_list\" class=\"bodytext dropDownText\" onmouseover='SetSelected(this)' onmouseout=\"changeStyle(this);\" onclick=\"SelectMonitorGroup('service_list_left1','");
/* 3008 */                           out.print(((Properties)list1.get(i)).getProperty("label"));
/* 3009 */                           out.write(39);
/* 3010 */                           out.write(44);
/* 3011 */                           out.write(39);
/* 3012 */                           out.print(((Properties)list1.get(i)).getProperty("value"));
/* 3013 */                           out.write("','leftimage1')\">");
/* 3014 */                           out.print(((Properties)list1.get(i)).getProperty("label"));
/* 3015 */                           out.write("</td>\n  \t            </tr>\n  \t            ");
/*      */                         }
/* 3017 */                         out.write("\n  \t            \n  \t            ");
/*      */                         
/* 3019 */                         ArrayList mgFields = MyFields.parseCustomFieldInfo(custInfo, "HAI", isPrivilegedUser);
/* 3020 */                         if (mgFields.size() > 0)
/*      */                         {
/* 3022 */                           out.write("\n\t\t\t\t<tr>\n\t  \t            <td id=\"customfield\" class=\"bodytext dropDownText\" style=\"background-color: #FFF8C6;font-weight: bold;cursor: text;\" >");
/* 3023 */                           out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 3024 */                           out.write("</td>\n  \t            </tr>\n  \t           ");
/*      */                         }
/* 3026 */                         out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t");
/* 3027 */                         for (int count = 0; count < mgFields.size(); count++)
/*      */                         {
/* 3029 */                           out.write("\n                           <tr><td id=\"");
/* 3030 */                           out.print(((Properties)mgFields.get(count)).getProperty("labelalias") + "$" + ((Properties)mgFields.get(count)).getProperty("fieldid") + "_list");
/* 3031 */                           out.write("\" class=\"bodytext dropDownText\" style=\"background-color: #FFF8C6\"  onmouseover='SetSelected(this,true)' onmouseout=\"changeStyle(this,true);\" onclick=\"SelectMonitorGroup('service_list_left1','");
/* 3032 */                           out.print(((Properties)mgFields.get(count)).getProperty("label"));
/* 3033 */                           out.write(39);
/* 3034 */                           out.write(44);
/* 3035 */                           out.write(39);
/* 3036 */                           out.print(((Properties)mgFields.get(count)).getProperty("labelalias") + "$" + ((Properties)mgFields.get(count)).getProperty("fieldid"));
/* 3037 */                           out.write("','leftimage1')\">\n                           ");
/* 3038 */                           out.print(((Properties)mgFields.get(count)).getProperty("label"));
/* 3039 */                           out.write("\n                           </td>\n                           </tr>\n                           ");
/*      */                         }
/* 3041 */                         out.write("                \n                           \n  \t            </table>\n  \t              </div>\n  \t               </div>\n  \t            </td>\n  \t            </tr>\n\n  \t   ");
/* 3042 */                         if (EnterpriseUtil.isIt360MSPEdition()) {
/* 3043 */                           out.write("\n  \t   <script>SelectMonitorGroup('service_list_left1','");
/* 3044 */                           out.print(((Properties)list1.get(0)).getProperty("label"));
/* 3045 */                           out.write(39);
/* 3046 */                           out.write(44);
/* 3047 */                           out.write(39);
/* 3048 */                           out.print(((Properties)list1.get(0)).getProperty("value"));
/* 3049 */                           out.write("','leftimage1');</script>\n  \t   ");
/*      */                         }
/* 3051 */                         out.write("\n\n\t\t  <tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"5\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n        \t<tr>\n\t\t\t");
/* 3052 */                         JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("mg", request.getCharacterEncoding()), out, false);
/* 3053 */                         out.write(9);
/* 3054 */                         out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\">&nbsp;</td>\n\t\t\t<td width=\"42%\" valign=\"top\">\n\t\t\t<div id=\"allMGRem3\">\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"30\"></td></tr>\n\n\t\t\t\t<tr id='mghealth'>\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 3055 */                         out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 3056 */                         out.write("\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id='mghealth'><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<span style=\"color:#595959; font-size:11px; font-weight:normal;\">&bull;</span> <a href=\"javascript:clickForm1('generateHAHealthReport',document.forms[1].haid)\" class=\"new-report-link\">");
/* 3057 */                         out.print(FormatUtil.getString("am.webclient.reports.availability.percentagereports.text"));
/* 3058 */                         out.write("</a>  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr id='mgevents'>\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\" >\n               \t\t\t");
/* 3059 */                         out.print(FormatUtil.getString("am.webclient.reports.reportlist.event.report.text"));
/* 3060 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id='mgevents'><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<span style=\"color:#595959; font-size:11px; font-weight:normal;\">&bull;</span> <a href=\"javascript:clickForm1('generateEventSummary',document.forms[1].haid)\" class=\"new-report-link\">\n\t\t\t\t");
/* 3061 */                         out.print(FormatUtil.getString("am.webclient.reports.reportlist.mgevent.report.text"));
/* 3062 */                         out.write("</a>  <br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr id='mgresponse'><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 3063 */                         out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 3064 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id='mgresponse'><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<span style=\"color:#595959; font-size:11px; font-weight:normal;\">&bull;</span> <a href=\"javascript:clickForm1('generateHAResponseTimeReport',document.forms[1].haid)\" class=\"new-report-link\">\n\t\t\t\t");
/* 3065 */                         out.print(FormatUtil.getString("am.webclient.reports.reportlist.responsetime.report.text"));
/* 3066 */                         out.write("</a>  <br /> <br />\n                \t\t</td></tr>\n\n\n                \t\t<tr id='Rmgattribute'><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 3067 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 3068 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id='Rmgattribute'><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\" >\t\t\t\n\t\t\t\t\n                \t\t<div id='MGAttribute'>\n\t\t\t\t\t\t");
/* 3069 */                         if (_jspx_meth_html_005fselect_005f0(_jspx_th_logic_005fnotEmpty_005f3, _jspx_page_context))
/*      */                           return;
/* 3071 */                         out.write("\n\t\t\t\t</div>\n\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</div>\n\t\t\t</td>\n\t\t</tr>\n\t\n\t\t ");
/* 3072 */                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 3073 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3077 */                     if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 3078 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3); return;
/*      */                     }
/*      */                     
/* 3081 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 3082 */                     out.write(10);
/* 3083 */                     out.write(9);
/* 3084 */                     out.write(9);
/*      */                     
/* 3086 */                     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 3087 */                     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 3088 */                     _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/* 3090 */                     _jspx_th_logic_005fempty_005f0.setName("ReportForm");
/*      */                     
/* 3092 */                     _jspx_th_logic_005fempty_005f0.setProperty("applications");
/* 3093 */                     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 3094 */                     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                       for (;;) {
/* 3096 */                         out.write("\n  \t\t<tr>\n    \t\t\t<td class=\"columnheading\" colspan=\"3\" >&nbsp;");
/* 3097 */                         out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.nodatamessage"));
/* 3098 */                         out.write("</td>\n  \t\t</tr>\n  \t\t");
/* 3099 */                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3100 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3104 */                     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3105 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                     }
/*      */                     
/* 3108 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3109 */                     out.write("\n\t\t</table>\n\n\t\t</li>\n\t</ul>\n\n\t");
/*      */                   }
/*      */                   
/*      */ 
/* 3113 */                   out.write("\n\t<ul id=\"sub_2\" class=\"none\">\n\t\t<li id=\"top_17\" style=\"height:");
/* 3114 */                   out.print(height2);
/* 3115 */                   out.write("px\">\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\n\n\t\t");
/*      */                   
/* 3117 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3118 */                   _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 3119 */                   _jspx_th_logic_005fnotEmpty_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 3121 */                   _jspx_th_logic_005fnotEmpty_005f4.setName("ReportForm");
/*      */                   
/* 3123 */                   _jspx_th_logic_005fnotEmpty_005f4.setProperty("customTypes");
/* 3124 */                   int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 3125 */                   if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */                     for (;;) {
/* 3127 */                       out.write(10);
/* 3128 */                       out.write(9);
/* 3129 */                       out.write(9);
/*      */                       
/* 3131 */                       ArrayList allCustomTypes = frm.getCustomTypes();
/* 3132 */                       StringBuilder customMonitors = new StringBuilder();
/* 3133 */                       for (int customListsize = 0; customListsize < allCustomTypes.size(); customListsize++) {
/* 3134 */                         Properties customtype = (Properties)allCustomTypes.get(customListsize);
/* 3135 */                         customMonitors.append("'" + customtype.get("value") + "',");
/*      */                       }
/* 3137 */                       customMonitors.deleteCharAt(0);
/* 3138 */                       String allCustomMonitor = customMonitors.substring(0, customMonitors.length() - 2);
/*      */                       
/* 3140 */                       out.write("\n\n\n\t\t\n\t\t<tr><td colspan=\"3\">\n\t\t<table width=\"100%\">\n\t\t<tr>\n\t\t<td class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\">\n\t\t&nbsp;&nbsp;&nbsp; ");
/* 3141 */                       out.print(FormatUtil.getString("Custom Types"));
/* 3142 */                       out.write("\n\t\t ");
/*      */                       
/* 3144 */                       SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 3145 */                       _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3146 */                       _jspx_th_html_005fselect_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                       
/* 3148 */                       _jspx_th_html_005fselect_005f1.setProperty("customservice");
/*      */                       
/* 3150 */                       _jspx_th_html_005fselect_005f1.setAlt(allCustomMonitor);
/*      */                       
/* 3152 */                       _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*      */                       
/* 3154 */                       _jspx_th_html_005fselect_005f1.setOnchange("loadURLType(this.options[this.selectedIndex].value,this,'customMonitorsFilterBy')");
/* 3155 */                       int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3156 */                       if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3157 */                         if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3158 */                           out = _jspx_page_context.pushBody();
/* 3159 */                           _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 3160 */                           _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3163 */                           out.write("\n      \t\t");
/* 3164 */                           if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/*      */                             return;
/* 3166 */                           out.write("\n      \t\t\n      \t\t\t\t   \t");
/*      */                           
/* 3168 */                           ArrayList customMonitorFields = MyFields.parseCustomFieldInfo(custInfo, allCustomMonitor, isPrivilegedUser);
/* 3169 */                           if (customMonitorFields.size() > 0)
/*      */                           {
/* 3171 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 3172 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 3173 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 3175 */                           for (int customcount = 0; customcount < customMonitorFields.size(); customcount++)
/*      */                           {
/* 3177 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 3179 */                             OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 3180 */                             _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3181 */                             _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f1);
/*      */                             
/* 3183 */                             _jspx_th_html_005foption_005f0.setStyle("background-color: #FFF8C6");
/*      */                             
/* 3185 */                             _jspx_th_html_005foption_005f0.setValue(((Properties)customMonitorFields.get(customcount)).getProperty("labelalias") + "$" + ((Properties)customMonitorFields.get(customcount)).getProperty("fieldid"));
/* 3186 */                             int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3187 */                             if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3188 */                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3189 */                                 out = _jspx_page_context.pushBody();
/* 3190 */                                 _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3191 */                                 _jspx_th_html_005foption_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3194 */                                 out.print(((Properties)customMonitorFields.get(customcount)).getProperty("label"));
/* 3195 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3196 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3199 */                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3200 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3203 */                             if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3204 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                             }
/*      */                             
/* 3207 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f0);
/* 3208 */                             out.write("\n                          ");
/*      */                           }
/* 3210 */                           out.write("\n      \t\t");
/* 3211 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3212 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3215 */                         if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3216 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3219 */                       if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3220 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                       }
/*      */                       
/* 3223 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f1);
/* 3224 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"customMonitorsFilterBy\"></span>\n\t\t</td>\n\t\t</tr>\n\t\t</table></td>\n\t\t</tr>\n\n\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 3225 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("customTypes", request.getCharacterEncoding()), out, false);
/* 3226 */                       out.write(9);
/* 3227 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 3228 */                       out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 3229 */                       out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('responseTime','generateAttributeReport',document.forms[1].customservice)\">");
/* 3230 */                       out.print(FormatUtil.getString("am.reporttab.customtype.responsetime"));
/* 3231 */                       out.write("<br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t");
/*      */                       
/* 3233 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f5 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3234 */                       _jspx_th_logic_005fnotEmpty_005f5.setPageContext(_jspx_page_context);
/* 3235 */                       _jspx_th_logic_005fnotEmpty_005f5.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                       
/* 3237 */                       _jspx_th_logic_005fnotEmpty_005f5.setName("ReportForm");
/*      */                       
/* 3239 */                       _jspx_th_logic_005fnotEmpty_005f5.setProperty("customserviceAttrib");
/* 3240 */                       int _jspx_eval_logic_005fnotEmpty_005f5 = _jspx_th_logic_005fnotEmpty_005f5.doStartTag();
/* 3241 */                       if (_jspx_eval_logic_005fnotEmpty_005f5 != 0) {
/*      */                         for (;;) {
/* 3243 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 3244 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 3245 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\t<br />\n\n\t\t\t\t\t");
/* 3246 */                           if (_jspx_meth_html_005fselect_005f2(_jspx_th_logic_005fnotEmpty_005f5, _jspx_page_context))
/*      */                             return;
/* 3248 */                           out.write("\n\n\t\t\t\t<br /> <br />\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t ");
/* 3249 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f5.doAfterBody();
/* 3250 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3254 */                       if (_jspx_th_logic_005fnotEmpty_005f5.doEndTag() == 5) {
/* 3255 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5); return;
/*      */                       }
/*      */                       
/* 3258 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5);
/* 3259 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\t\t");
/* 3260 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 3261 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3265 */                   if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 3266 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4); return;
/*      */                   }
/*      */                   
/* 3269 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 3270 */                   out.write(10);
/* 3271 */                   out.write(9);
/* 3272 */                   out.write(9);
/*      */                   
/* 3274 */                   EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 3275 */                   _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3276 */                   _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 3278 */                   _jspx_th_logic_005fempty_005f1.setName("ReportForm");
/*      */                   
/* 3280 */                   _jspx_th_logic_005fempty_005f1.setProperty("customTypes");
/* 3281 */                   int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3282 */                   if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                     for (;;) {
/* 3284 */                       out.write("\n  \t\t<tr>\n    \t\t<td class=\"columnheading\" colspan=\"3\">\n\t\t");
/*      */                       
/* 3286 */                       String message = new String();
/* 3287 */                       if (EnterpriseUtil.isAdminServer())
/*      */                       {
/* 3289 */                         message = FormatUtil.getString("am.reporttab.customtype.nodatamessage.admin");
/*      */                       }
/*      */                       else
/*      */                       {
/* 3293 */                         message = FormatUtil.getString("am.reporttab.customtype.nodatamessage");
/*      */                       }
/*      */                       
/* 3296 */                       out.write("\n\t\t&nbsp;\n\t\t ");
/* 3297 */                       out.print(message);
/* 3298 */                       out.write("\n\t\t</td>\n  \t\t</tr>\n  \t\t");
/* 3299 */                       int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3300 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3304 */                   if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3305 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                   }
/*      */                   
/* 3308 */                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3309 */                   out.write("\n\t\t</table>\n\n\t</li>\n\t</ul>\n\n\t<ul id=\"sub_3\" class=\"none\">\n\t\t<li id=\"top_18\" style=\"height:");
/* 3310 */                   out.print(height2);
/* 3311 */                   out.write("px\">\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\n\t\t");
/* 3312 */                   if (_jspx_meth_logic_005fnotEmpty_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3314 */                   out.write(32);
/* 3315 */                   out.write(10);
/* 3316 */                   out.write(9);
/* 3317 */                   out.write(9);
/* 3318 */                   if (_jspx_meth_logic_005fempty_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3320 */                   out.write(32);
/* 3321 */                   out.write(10);
/* 3322 */                   out.write(9);
/* 3323 */                   out.write(9);
/* 3324 */                   out.write(10);
/* 3325 */                   out.write(9);
/* 3326 */                   out.write(9);
/* 3327 */                   if (_jspx_meth_logic_005fnotEmpty_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3329 */                   out.write(32);
/* 3330 */                   out.write(" \n\t\t");
/* 3331 */                   if (_jspx_meth_logic_005fempty_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 3333 */                   out.write(32);
/* 3334 */                   out.write(10);
/* 3335 */                   out.write(9);
/* 3336 */                   out.write(9);
/* 3337 */                   out.write(10);
/* 3338 */                   out.write(9);
/* 3339 */                   out.write(9);
/*      */                   
/* 3341 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f8 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3342 */                   _jspx_th_logic_005fnotEmpty_005f8.setPageContext(_jspx_page_context);
/* 3343 */                   _jspx_th_logic_005fnotEmpty_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 3345 */                   _jspx_th_logic_005fnotEmpty_005f8.setName("ReportForm");
/*      */                   
/* 3347 */                   _jspx_th_logic_005fnotEmpty_005f8.setProperty("monitors");
/* 3348 */                   int _jspx_eval_logic_005fnotEmpty_005f8 = _jspx_th_logic_005fnotEmpty_005f8.doStartTag();
/* 3349 */                   if (_jspx_eval_logic_005fnotEmpty_005f8 != 0) {
/*      */                     for (;;) {
/* 3351 */                       out.write("\n\n\t\t<tr>\n\n\t\t<td class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" colspan=\"3\" >&nbsp;&nbsp;&nbsp; ");
/* 3352 */                       out.print(FormatUtil.getString("am.reporttab.selectmonitor.text"));
/* 3353 */                       out.write("&nbsp;\n\n\t\t<input type=\"text\" class=\"formtext input-down-arrow normal\" size=\"30\" style=\"height:25px;\" onMouseDown=\"DisplayServiceList('service_list_center','centerarrow')\" name=\"saturday\"  id=\"saturday\" value='");
/* 3354 */                       if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fnotEmpty_005f8, _jspx_page_context))
/*      */                         return;
/* 3356 */                       out.write("' readonly>\n\n\t\t<img src=\"../../images/icon_downarrow.gif\" name=\"centerarrow\" class=\"drop-downimg\" width=\"25\" height=\"20\" style=\"display:none\" align=\"absmiddle\" onclick=\"DisplayServiceList('service_list_center','centerarrow')\">\n\t  ");
/*      */                       
/* 3358 */                       int listsize = list.size();
/* 3359 */                       int divlength = 80;
/*      */                       
/* 3361 */                       if ((listsize > 5) && (listsize < 20))
/*      */                       {
/* 3363 */                         divlength = 130;
/*      */                       }
/* 3365 */                       else if (listsize > 25)
/*      */                       {
/* 3367 */                         divlength = 200;
/*      */                       }
/*      */                       
/* 3370 */                       out.write("\n\t    <div id=\"dummyid1\" style=\"width:0px; height:0px; overflow:none;\"><div id=\"monitordisplayframe\" style=\"display:none\"><iframe src=\"/images/icon_message_success.gif\" style=\"position:absolute; width:auto; height:");
/* 3371 */                       out.print(divlength);
/* 3372 */                       out.write("px; z-index:0;\" id=\"monitordisplay\" frameborder=\"0\"></iframe></div></div>\n\n      <div id=\"dummyid\" style=\"width:0px; height:0px; overflow:none;\">\n\n\t    <div id=\"service_list_center\" class=\"formtext\" style=\"overflow:auto; left:100px; width:225px; height:");
/* 3373 */                       out.print(divlength);
/* 3374 */                       out.write("px; display:none; position:absolute; background:#FFFFFF;\">\n          <table width=\"100%\" border=\"0\" cellspacing=\"0\" csellpadding=\"0\">\n            ");
/* 3375 */                       for (int i = 0; i < list.size(); i++) {
/* 3376 */                         out.write("\n\n\n\n            <tr>\n             ");
/* 3377 */                         String value = ((Properties)list.get(i)).getProperty("label");
/* 3378 */                         value = com.adventnet.appmanager.util.VMReportUtilities.escape(value, false).replaceAll("\"", "");
/*      */                         
/*      */ 
/* 3381 */                         out.write("\n              <td id=\"");
/* 3382 */                         out.print(((Properties)list.get(i)).getProperty("value"));
/* 3383 */                         out.write("_Servicelist\" class=\"bodytext dropDownText\" onmouseover='SetSelected(this)' onmouseout=\"changeStyle(this);\" onclick=\"SelectService('service_list_center','");
/* 3384 */                         out.print(value);
/* 3385 */                         out.write(39);
/* 3386 */                         out.write(44);
/* 3387 */                         out.write(39);
/* 3388 */                         out.print(((Properties)list.get(i)).getProperty("value"));
/* 3389 */                         out.write("','centerarrow')\">");
/* 3390 */                         out.print(((Properties)list.get(i)).getProperty("label"));
/* 3391 */                         out.write("\n                ");
/* 3392 */                         if (_jspx_meth_html_005fhidden_005f21(_jspx_th_logic_005fnotEmpty_005f8, _jspx_page_context))
/*      */                           return;
/* 3394 */                         out.write(" </td>\n\n            </tr>\n\n            ");
/*      */                       }
/* 3396 */                       out.write("\n          </table>\n        </div>\n      </div>\n\t\t</td>\n\t\t</tr>\n\n<tr><td colspan=\"3\" height=\"3\"></td></tr>\n<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\n\n\n        <tr>\n\t\t\t<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 3397 */                       out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 3398 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickForm2('generateIndividualGlanceReport',document.forms[1].resid)\" class=\"new-report-link\">");
/* 3399 */                       out.print(FormatUtil.getString("am.webclient.reports.ataglance.monitor"));
/* 3400 */                       out.write("</a>");
/* 3401 */                       out.write("  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 3402 */                       out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 3403 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\"><a href=\"javascript:sendReport(document.forms[1].resid.value,document.forms[1].resid)\" class=\"new-report-link\">");
/* 3404 */                       out.print(FormatUtil.getString("am.reporttab.monitordowntime.text"));
/* 3405 */                       out.write("</a>\n                \t\t  <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\" >\n               \t\t\t");
/* 3406 */                       out.print(FormatUtil.getString("am.reporttab.monsummary.text"));
/* 3407 */                       out.write(" </td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickForm2('generateSummaryReport',document.forms[1].resid)\" class=\"new-report-link\">");
/* 3408 */                       out.print(FormatUtil.getString("Summary Report of Monitor"));
/* 3409 */                       out.write("\n      \t\t\t\t</a>  <br /> <br />\n                \t\t</td></tr>\n\n\n\t\t\t\t</table>\n\t\t\t</td>\n\n\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td colspan='3'>\n\t\t\t\t<div id='trendDiv' style=\"display:none; overflow:auto; width:100%; height:");
/* 3410 */                       out.print(height3);
/* 3411 */                       out.write("px\">\n\n\t\t\t\t</div>\n\t\t\t</td>\n\t\t</tr>\n\t\t");
/* 3412 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f8.doAfterBody();
/* 3413 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3417 */                   if (_jspx_th_logic_005fnotEmpty_005f8.doEndTag() == 5) {
/* 3418 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f8); return;
/*      */                   }
/*      */                   
/* 3421 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f8);
/* 3422 */                   out.write(10);
/* 3423 */                   out.write(9);
/* 3424 */                   out.write(9);
/*      */                   
/* 3426 */                   EmptyTag _jspx_th_logic_005fempty_005f4 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 3427 */                   _jspx_th_logic_005fempty_005f4.setPageContext(_jspx_page_context);
/* 3428 */                   _jspx_th_logic_005fempty_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 3430 */                   _jspx_th_logic_005fempty_005f4.setName("ReportForm");
/*      */                   
/* 3432 */                   _jspx_th_logic_005fempty_005f4.setProperty("monitors");
/* 3433 */                   int _jspx_eval_logic_005fempty_005f4 = _jspx_th_logic_005fempty_005f4.doStartTag();
/* 3434 */                   if (_jspx_eval_logic_005fempty_005f4 != 0) {
/*      */                     for (;;) {
/* 3436 */                       out.write("\n  \t\t<tr>\n    \t\t<td class=\"columnheading\" colspan=2> ");
/* 3437 */                       out.print(FormatUtil.getString("am.monitortab.nomonitors.text"));
/* 3438 */                       out.write("</td>\n  \t\t</tr>\n  \t\t");
/* 3439 */                       int evalDoAfterBody = _jspx_th_logic_005fempty_005f4.doAfterBody();
/* 3440 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3444 */                   if (_jspx_th_logic_005fempty_005f4.doEndTag() == 5) {
/* 3445 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f4); return;
/*      */                   }
/*      */                   
/* 3448 */                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f4);
/* 3449 */                   out.write("\n\t\t</table>\n\n\n\t\t</li>\n\t</ul>\n\n\t<ul id=\"sub_4\" class=\"none\" >\n\t\t<li id=\"top_19\" style=\"height:");
/* 3450 */                   out.print(height2);
/* 3451 */                   out.write("px\">\n\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" >\n\n\t\t");
/*      */                   
/* 3453 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f9 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3454 */                   _jspx_th_logic_005fnotEmpty_005f9.setPageContext(_jspx_page_context);
/* 3455 */                   _jspx_th_logic_005fnotEmpty_005f9.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 3457 */                   _jspx_th_logic_005fnotEmpty_005f9.setName("ReportForm");
/*      */                   
/* 3459 */                   _jspx_th_logic_005fnotEmpty_005f9.setProperty("systems");
/* 3460 */                   int _jspx_eval_logic_005fnotEmpty_005f9 = _jspx_th_logic_005fnotEmpty_005f9.doStartTag();
/* 3461 */                   if (_jspx_eval_logic_005fnotEmpty_005f9 != 0) {
/*      */                     for (;;) {
/* 3463 */                       out.write(10);
/* 3464 */                       out.write(9);
/* 3465 */                       out.write(9);
/*      */                       
/* 3467 */                       Properties allServerTypes = (Properties)frm.getSystems().get(0);
/* 3468 */                       String allServer = (String)allServerTypes.get("value");
/*      */                       
/* 3470 */                       out.write("\n\t\t<tr><td colspan=\"3\">\n\t\t<table width=\"100%\" border=\"0\"><tr>\n\t\t<td class=\"bodytextbold reportHeaderSpace\" height=\"22\" >\n\t\t&nbsp;&nbsp;&nbsp; ");
/* 3471 */                       out.print(FormatUtil.getString("am.webclient.reports.select.server.text"));
/* 3472 */                       out.write(" &nbsp;\n\t\t");
/*      */                       
/* 3474 */                       SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 3475 */                       _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 3476 */                       _jspx_th_html_005fselect_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f9);
/*      */                       
/* 3478 */                       _jspx_th_html_005fselect_005f3.setProperty("system");
/*      */                       
/* 3480 */                       _jspx_th_html_005fselect_005f3.setAlt(allServer);
/*      */                       
/* 3482 */                       _jspx_th_html_005fselect_005f3.setOnchange("loadURLType(this.options[this.selectedIndex].value,this,'serversFilterBy')");
/*      */                       
/* 3484 */                       _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/* 3485 */                       int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 3486 */                       if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 3487 */                         if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3488 */                           out = _jspx_page_context.pushBody();
/* 3489 */                           _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 3490 */                           _jspx_th_html_005fselect_005f3.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3493 */                           out.write(10);
/* 3494 */                           out.write(9);
/* 3495 */                           out.write(9);
/* 3496 */                           if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/*      */                             return;
/* 3498 */                           out.write("\n\t\t\t   \t");
/*      */                           
/* 3500 */                           ArrayList serverfields = MyFields.parseCustomFieldInfo(custInfo, allServer, isPrivilegedUser);
/* 3501 */                           if (serverfields.size() > 0)
/*      */                           {
/* 3503 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 3504 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 3505 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 3507 */                           for (int servercount = 0; servercount < serverfields.size(); servercount++)
/*      */                           {
/* 3509 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 3511 */                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 3512 */                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3513 */                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f3);
/*      */                             
/* 3515 */                             _jspx_th_html_005foption_005f1.setStyle("background-color: #FFF8C6");
/*      */                             
/* 3517 */                             _jspx_th_html_005foption_005f1.setValue(((Properties)serverfields.get(servercount)).getProperty("labelalias") + "$" + ((Properties)serverfields.get(servercount)).getProperty("fieldid"));
/* 3518 */                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3519 */                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3520 */                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3521 */                                 out = _jspx_page_context.pushBody();
/* 3522 */                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3523 */                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3526 */                                 out.print(((Properties)serverfields.get(servercount)).getProperty("label"));
/* 3527 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3528 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3531 */                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3532 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3535 */                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3536 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                             }
/*      */                             
/* 3539 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f1);
/* 3540 */                             out.write("\n                          ");
/*      */                           }
/* 3542 */                           out.write("\n                           \n                           \n      \t\t");
/* 3543 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 3544 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3547 */                         if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3548 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3551 */                       if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 3552 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f3); return;
/*      */                       }
/*      */                       
/* 3555 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f3);
/* 3556 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"serversFilterBy\">\n                             </span>\n\t\t</td>\n\t\t</tr></table>\n        </td></tr>\n\t\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n        \t<tr>\n\t\t\t");
/* 3557 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("server", request.getCharacterEncoding()), out, false);
/* 3558 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 3559 */                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 3560 */                       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('cpuid','generateAttributeReport',document.forms[1].system)\">");
/* 3561 */                       out.print(FormatUtil.getString("am.reporttab.cpuofservers.text"));
/* 3562 */                       out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\n\t\t\t\t");
/*      */                       
/* 3564 */                       List al = com.adventnet.appmanager.util.ReportUtil.getRepoEnabledAttribs();
/* 3565 */                       if ((al != null) && (al.contains("9641")))
/*      */                       {
/* 3567 */                         out.write("\n\n\t\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t\t<td  class=\"new-report-heading\"  >\n\t               \t\t\t ");
/* 3568 */                         out.print(FormatUtil.getString("am.reporttab.shortname.cpucore.usage.text"));
/* 3569 */                         out.write("\n\t               \t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t\t<td height=\"20\">\n\t                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('9641','generateAttributeReport',document.forms[1].system)\">");
/* 3570 */                         out.print(FormatUtil.getString("am.reporttab.servers.cpucore.usage.text"));
/* 3571 */                         out.write("</a> <br /> <br />\n\t                \t\t</td>\n\t                </tr>\n\t\t\t\t");
/*      */                       }
/*      */                       
/*      */ 
/* 3575 */                       out.write("\n\n\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\" >\n               \t\t\t");
/* 3576 */                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 3577 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('mem','generateAttributeReport',document.forms[1].system)\">");
/* 3578 */                       out.print(FormatUtil.getString("am.reporttab.memoryofservers.text"));
/* 3579 */                       out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 3580 */                       out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 3581 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('disk','generateAttributeReport',document.forms[1].system)\">");
/* 3582 */                       out.print(FormatUtil.getString("am.reporttab.diskofservers.text"));
/* 3583 */                       out.write("</a> <br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t ");
/*      */                       
/* 3585 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f10 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3586 */                       _jspx_th_logic_005fnotEmpty_005f10.setPageContext(_jspx_page_context);
/* 3587 */                       _jspx_th_logic_005fnotEmpty_005f10.setParent(_jspx_th_logic_005fnotEmpty_005f9);
/*      */                       
/* 3589 */                       _jspx_th_logic_005fnotEmpty_005f10.setName("ReportForm");
/*      */                       
/* 3591 */                       _jspx_th_logic_005fnotEmpty_005f10.setProperty("serverArrayAttribute");
/* 3592 */                       int _jspx_eval_logic_005fnotEmpty_005f10 = _jspx_th_logic_005fnotEmpty_005f10.doStartTag();
/* 3593 */                       if (_jspx_eval_logic_005fnotEmpty_005f10 != 0) {
/*      */                         for (;;) {
/* 3595 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 3596 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 3597 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\t\t\t\t\t");
/* 3598 */                           if (_jspx_meth_html_005fselect_005f4(_jspx_th_logic_005fnotEmpty_005f10, _jspx_page_context))
/*      */                             return;
/* 3600 */                           out.write("\n\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 3601 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f10.doAfterBody();
/* 3602 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3606 */                       if (_jspx_th_logic_005fnotEmpty_005f10.doEndTag() == 5) {
/* 3607 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f10); return;
/*      */                       }
/*      */                       
/* 3610 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f10);
/* 3611 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\t\t");
/* 3612 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f9.doAfterBody();
/* 3613 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3617 */                   if (_jspx_th_logic_005fnotEmpty_005f9.doEndTag() == 5) {
/* 3618 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f9); return;
/*      */                   }
/*      */                   
/* 3621 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f9);
/* 3622 */                   out.write("\n\t\t</table>\n\n\n\t\t</li>\n\t</ul>\n\n\t<ul id=\"sub_5\" class=\"none\">\n\t\t<li id=\"top_20\" style=\"height:");
/* 3623 */                   out.print(height2);
/* 3624 */                   out.write("px\">\n\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\n\t\t");
/*      */                   
/* 3626 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f11 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3627 */                   _jspx_th_logic_005fnotEmpty_005f11.setPageContext(_jspx_page_context);
/* 3628 */                   _jspx_th_logic_005fnotEmpty_005f11.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 3630 */                   _jspx_th_logic_005fnotEmpty_005f11.setName("ReportForm");
/*      */                   
/* 3632 */                   _jspx_th_logic_005fnotEmpty_005f11.setProperty("appServers");
/* 3633 */                   int _jspx_eval_logic_005fnotEmpty_005f11 = _jspx_th_logic_005fnotEmpty_005f11.doStartTag();
/* 3634 */                   if (_jspx_eval_logic_005fnotEmpty_005f11 != 0) {
/*      */                     for (;;) {
/* 3636 */                       out.write(10);
/* 3637 */                       out.write(9);
/* 3638 */                       out.write(9);
/*      */                       
/* 3640 */                       Properties allAppServerTypes = (Properties)frm.getAppServers().get(0);
/* 3641 */                       String allAppServer = (String)allAppServerTypes.get("value");
/*      */                       
/* 3643 */                       out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\" border=\"0\"><tr>\n\t\t<td  class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\">&nbsp;&nbsp;&nbsp; ");
/* 3644 */                       out.print(FormatUtil.getString("am.webclient.reports.select.appserver.text"));
/* 3645 */                       out.write(" &nbsp;\n\t\t ");
/*      */                       
/* 3647 */                       SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 3648 */                       _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 3649 */                       _jspx_th_html_005fselect_005f5.setParent(_jspx_th_logic_005fnotEmpty_005f11);
/*      */                       
/* 3651 */                       _jspx_th_html_005fselect_005f5.setProperty("appserver");
/*      */                       
/* 3653 */                       _jspx_th_html_005fselect_005f5.setStyleClass("formtext");
/*      */                       
/* 3655 */                       _jspx_th_html_005fselect_005f5.setAlt(allAppServer);
/*      */                       
/* 3657 */                       _jspx_th_html_005fselect_005f5.setOnchange("eval(showRelaventReportForApp(this))");
/*      */                       
/* 3659 */                       _jspx_th_html_005fselect_005f5.setStyle("width:150px");
/* 3660 */                       int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 3661 */                       if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 3662 */                         if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3663 */                           out = _jspx_page_context.pushBody();
/* 3664 */                           _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 3665 */                           _jspx_th_html_005fselect_005f5.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3668 */                           out.write("\n    \t\t");
/* 3669 */                           if (_jspx_meth_html_005foptionsCollection_005f5(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/*      */                             return;
/* 3671 */                           out.write("\n                          \n                           \t");
/*      */                           
/* 3673 */                           ArrayList appserverfields = MyFields.parseCustomFieldInfo(custInfo, allAppServer, isPrivilegedUser);
/* 3674 */                           if (appserverfields.size() > 0)
/*      */                           {
/* 3676 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 3677 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 3678 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 3680 */                           for (int appservercount = 0; appservercount < appserverfields.size(); appservercount++)
/*      */                           {
/* 3682 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 3684 */                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 3685 */                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3686 */                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f5);
/*      */                             
/* 3688 */                             _jspx_th_html_005foption_005f2.setStyle("background-color: #FFF8C6");
/*      */                             
/* 3690 */                             _jspx_th_html_005foption_005f2.setValue(((Properties)appserverfields.get(appservercount)).getProperty("labelalias") + "$" + ((Properties)appserverfields.get(appservercount)).getProperty("fieldid"));
/* 3691 */                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3692 */                             if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3693 */                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3694 */                                 out = _jspx_page_context.pushBody();
/* 3695 */                                 _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3696 */                                 _jspx_th_html_005foption_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3699 */                                 out.print(((Properties)appserverfields.get(appservercount)).getProperty("label"));
/* 3700 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3701 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3704 */                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3705 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3708 */                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3709 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                             }
/*      */                             
/* 3712 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f2);
/* 3713 */                             out.write("\n                          ");
/*      */                           }
/* 3715 */                           out.write("\n    \t\t");
/* 3716 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 3717 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3720 */                         if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3721 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3724 */                       if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 3725 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f5); return;
/*      */                       }
/*      */                       
/* 3728 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f5);
/* 3729 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"appserversFilterBy\" >\n                             </span>\n    \t\t\n\t\t</td>\n\t\t</tr></table></td>\n\t\t</tr>\n\t\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"3\" height=\"20\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n        \t<tr>\n\t\t\t");
/* 3730 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("applicationserver", request.getCharacterEncoding()), out, false);
/* 3731 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\" >\n\t\t\t\t<div id=\"thread_1\">\n               \t\t\t");
/* 3732 */                       out.print(FormatUtil.getString("am.reporttab.shortname.thread.text"));
/* 3733 */                       out.write("</div></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"thread_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('thread','generateAttributeReport',document.forms[1].appserver)\" class=\"new-report-link\">");
/* 3734 */                       out.print(FormatUtil.getString("am.reporttab.threadofapp.text"));
/* 3735 */                       out.write("</a> <br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"session_1\">\n               \t\t\t ");
/* 3736 */                       out.print(FormatUtil.getString("am.reporttab.shortname.session.text"));
/* 3737 */                       out.write("</div></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"session_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('session','generateAttributeReport',document.forms[1].appserver)\" class=\"new-report-link\">");
/* 3738 */                       out.print(FormatUtil.getString("am.reporttab.httpofapp.text"));
/* 3739 */                       out.write(" </a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"throughput_1\">\n               \t\t\t ");
/* 3740 */                       out.print(FormatUtil.getString("am.webclient.oracleas.throughput"));
/* 3741 */                       out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"throughput_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('throughput','generateAttributeReport',document.forms[1].appserver)\" class=\"new-report-link\">");
/* 3742 */                       out.print(FormatUtil.getString("am.reporttab.throughputofapp.text"));
/* 3743 */                       out.write("</a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"webappthroughput_1\">\n               \t\t\t ");
/* 3744 */                       out.print(FormatUtil.getString("am.reporttab.shortname.webapprequestthroughput.text"));
/* 3745 */                       out.write("</div></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"webappthroughput_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('webappthroughput','generateAttributeReport',document.forms[1].appserver)\" class=\"new-report-link\">");
/* 3746 */                       out.print(FormatUtil.getString("am.reporttab.webappthroughputofapp.text"));
/* 3747 */                       out.write("</a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\n\t\t\t\t ");
/*      */                       
/* 3749 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f12 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3750 */                       _jspx_th_logic_005fnotEmpty_005f12.setPageContext(_jspx_page_context);
/* 3751 */                       _jspx_th_logic_005fnotEmpty_005f12.setParent(_jspx_th_logic_005fnotEmpty_005f11);
/*      */                       
/* 3753 */                       _jspx_th_logic_005fnotEmpty_005f12.setName("ReportForm");
/*      */                       
/* 3755 */                       _jspx_th_logic_005fnotEmpty_005f12.setProperty("appArrayAttribute");
/* 3756 */                       int _jspx_eval_logic_005fnotEmpty_005f12 = _jspx_th_logic_005fnotEmpty_005f12.doStartTag();
/* 3757 */                       if (_jspx_eval_logic_005fnotEmpty_005f12 != 0) {
/*      */                         for (;;) {
/* 3759 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 3760 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 3761 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\n\t\t\t\t\t");
/* 3762 */                           if (_jspx_meth_html_005fselect_005f6(_jspx_th_logic_005fnotEmpty_005f12, _jspx_page_context))
/*      */                             return;
/* 3764 */                           out.write("\n\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t  ");
/* 3765 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f12.doAfterBody();
/* 3766 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3770 */                       if (_jspx_th_logic_005fnotEmpty_005f12.doEndTag() == 5) {
/* 3771 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f12); return;
/*      */                       }
/*      */                       
/* 3774 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f12);
/* 3775 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t\t");
/* 3776 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f11.doAfterBody();
/* 3777 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3781 */                   if (_jspx_th_logic_005fnotEmpty_005f11.doEndTag() == 5) {
/* 3782 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f11); return;
/*      */                   }
/*      */                   
/* 3785 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f11);
/* 3786 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n<ul id=\"sub_6\" class=\"none\">\n\t\t<li id=\"top_21\" style=\"height:");
/* 3787 */                   out.print(height2);
/* 3788 */                   out.write("px\">\n\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t");
/*      */                   
/* 3790 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f13 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3791 */                   _jspx_th_logic_005fnotEmpty_005f13.setPageContext(_jspx_page_context);
/* 3792 */                   _jspx_th_logic_005fnotEmpty_005f13.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 3794 */                   _jspx_th_logic_005fnotEmpty_005f13.setName("ReportForm");
/*      */                   
/* 3796 */                   _jspx_th_logic_005fnotEmpty_005f13.setProperty("dbServers");
/* 3797 */                   int _jspx_eval_logic_005fnotEmpty_005f13 = _jspx_th_logic_005fnotEmpty_005f13.doStartTag();
/* 3798 */                   if (_jspx_eval_logic_005fnotEmpty_005f13 != 0) {
/*      */                     for (;;) {
/* 3800 */                       out.write(10);
/* 3801 */                       out.write(9);
/* 3802 */                       out.write(9);
/*      */                       
/* 3804 */                       Properties allDbServerTypes = (Properties)frm.getDbServers().get(0);
/* 3805 */                       String allDbServer = (String)allDbServerTypes.get("value");
/*      */                       
/* 3807 */                       out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\" border=\"0\"><tr>\n\t\t<td  class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" >&nbsp;&nbsp;&nbsp; ");
/* 3808 */                       out.print(FormatUtil.getString("am.webclient.reports.select.dbserver.text"));
/* 3809 */                       out.write(" &nbsp;\n\t\t");
/*      */                       
/* 3811 */                       SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 3812 */                       _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 3813 */                       _jspx_th_html_005fselect_005f7.setParent(_jspx_th_logic_005fnotEmpty_005f13);
/*      */                       
/* 3815 */                       _jspx_th_html_005fselect_005f7.setProperty("dbserver");
/*      */                       
/* 3817 */                       _jspx_th_html_005fselect_005f7.setAlt(allDbServer);
/*      */                       
/* 3819 */                       _jspx_th_html_005fselect_005f7.setStyleClass("formtext");
/*      */                       
/* 3821 */                       _jspx_th_html_005fselect_005f7.setOnchange("eval(showRelaventReportfordb(this))");
/* 3822 */                       int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 3823 */                       if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 3824 */                         if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 3825 */                           out = _jspx_page_context.pushBody();
/* 3826 */                           _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 3827 */                           _jspx_th_html_005fselect_005f7.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3830 */                           out.write(10);
/* 3831 */                           out.write(9);
/* 3832 */                           out.write(9);
/* 3833 */                           if (_jspx_meth_html_005foptionsCollection_005f7(_jspx_th_html_005fselect_005f7, _jspx_page_context))
/*      */                             return;
/* 3835 */                           out.write("\n\t\t\n\t\t");
/*      */                           
/* 3837 */                           ArrayList dbserverfields = MyFields.parseCustomFieldInfo(custInfo, allDbServer, isPrivilegedUser);
/* 3838 */                           if (dbserverfields.size() > 0)
/*      */                           {
/* 3840 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 3841 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 3842 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 3844 */                           for (int dbservercount = 0; dbservercount < dbserverfields.size(); dbservercount++)
/*      */                           {
/* 3846 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 3848 */                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 3849 */                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3850 */                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f7);
/*      */                             
/* 3852 */                             _jspx_th_html_005foption_005f3.setStyle("background-color: #FFF8C6");
/*      */                             
/* 3854 */                             _jspx_th_html_005foption_005f3.setValue(((Properties)dbserverfields.get(dbservercount)).getProperty("labelalias") + "$" + ((Properties)dbserverfields.get(dbservercount)).getProperty("fieldid"));
/* 3855 */                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3856 */                             if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3857 */                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3858 */                                 out = _jspx_page_context.pushBody();
/* 3859 */                                 _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3860 */                                 _jspx_th_html_005foption_005f3.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3863 */                                 out.print(((Properties)dbserverfields.get(dbservercount)).getProperty("label"));
/* 3864 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3865 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3868 */                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3869 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3872 */                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3873 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                             }
/*      */                             
/* 3876 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f3);
/* 3877 */                             out.write("\n                          ");
/*      */                           }
/* 3879 */                           out.write("\n                          \n\t\t\n      \t\t");
/* 3880 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 3881 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3884 */                         if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 3885 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3888 */                       if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 3889 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f7); return;
/*      */                       }
/*      */                       
/* 3892 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f7);
/* 3893 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"dbserversFilterBy\" >\n                             </span>\n\t\t</td>\n\t\t</tr></table></td>\n\t\t</tr>\n<tr><td colspan=\"3\" height=\"3\"></td></tr>\n<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 3894 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("databaseserver", request.getCharacterEncoding()), out, false);
/* 3895 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\" >\n\t\t\t\t<div id=\"bufferhit_1\">\n               \t\t\t");
/* 3896 */                       out.print(FormatUtil.getString("am.reporttab.buffer.text"));
/* 3897 */                       out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"bufferhit_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('buffer','generateAttributeReport',document.forms[1].dbserver)\" class=\"new-report-link\">");
/* 3898 */                       out.print(FormatUtil.getString("am.reporttab.bufferofdb.text"));
/* 3899 */                       out.write("</a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"cachehit_1\">\n               \t\t\t ");
/* 3900 */                       out.print(FormatUtil.getString("am.reporttab.cache.text"));
/* 3901 */                       out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"cachehit_2\">\n                \t\t<a href=\"javascript:clickAttributeForm('cache','generateAttributeReport',document.forms[1].dbserver)\" class=\"new-report-link\">");
/* 3902 */                       out.print(FormatUtil.getString("am.reporttab.cacheofdb.text"));
/* 3903 */                       out.write("</a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\n\t\t\t\t");
/*      */                       
/* 3905 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f14 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3906 */                       _jspx_th_logic_005fnotEmpty_005f14.setPageContext(_jspx_page_context);
/* 3907 */                       _jspx_th_logic_005fnotEmpty_005f14.setParent(_jspx_th_logic_005fnotEmpty_005f13);
/*      */                       
/* 3909 */                       _jspx_th_logic_005fnotEmpty_005f14.setName("ReportForm");
/*      */                       
/* 3911 */                       _jspx_th_logic_005fnotEmpty_005f14.setProperty("dbArrayAttribute");
/* 3912 */                       int _jspx_eval_logic_005fnotEmpty_005f14 = _jspx_th_logic_005fnotEmpty_005f14.doStartTag();
/* 3913 */                       if (_jspx_eval_logic_005fnotEmpty_005f14 != 0) {
/*      */                         for (;;) {
/* 3915 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 3916 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 3917 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\n\t\t\t\t\t");
/* 3918 */                           if (_jspx_meth_html_005fselect_005f8(_jspx_th_logic_005fnotEmpty_005f14, _jspx_page_context))
/*      */                             return;
/* 3920 */                           out.write("\n\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 3921 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f14.doAfterBody();
/* 3922 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3926 */                       if (_jspx_th_logic_005fnotEmpty_005f14.doEndTag() == 5) {
/* 3927 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f14); return;
/*      */                       }
/*      */                       
/* 3930 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f14);
/* 3931 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\t\t");
/* 3932 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f13.doAfterBody();
/* 3933 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3937 */                   if (_jspx_th_logic_005fnotEmpty_005f13.doEndTag() == 5) {
/* 3938 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f13); return;
/*      */                   }
/*      */                   
/* 3941 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f13);
/* 3942 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n\n\n\n<ul id=\"sub_7\" class=\"none\">\n\t\t<li id=\"top_22\" style=\"height:");
/* 3943 */                   out.print(height2);
/* 3944 */                   out.write("px\">\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\t\t");
/*      */                   
/* 3946 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f15 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3947 */                   _jspx_th_logic_005fnotEmpty_005f15.setPageContext(_jspx_page_context);
/* 3948 */                   _jspx_th_logic_005fnotEmpty_005f15.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 3950 */                   _jspx_th_logic_005fnotEmpty_005f15.setName("ReportForm");
/*      */                   
/* 3952 */                   _jspx_th_logic_005fnotEmpty_005f15.setProperty("webservices");
/* 3953 */                   int _jspx_eval_logic_005fnotEmpty_005f15 = _jspx_th_logic_005fnotEmpty_005f15.doStartTag();
/* 3954 */                   if (_jspx_eval_logic_005fnotEmpty_005f15 != 0) {
/*      */                     for (;;) {
/* 3956 */                       out.write("\n\t\t<tr>\n\t\t<td height=\"35\" colspan=\"3\"><table width=\"100%\">\n\t\t<tr>\n\t\t<td width=\"15%\" class=\"report-heading new-report-heading\" align=\"left\">&nbsp;&nbsp;&nbsp; ");
/* 3957 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3958 */                       out.write(" &nbsp;</td>\n\t\t<td  width=\"85%\">\n\t\t");
/*      */                       
/* 3960 */                       ArrayList webservicefields = MyFields.parseCustomFieldInfo(custInfo, "Web Service", isPrivilegedUser);
/* 3961 */                       if (webservicefields.size() > 0)
/*      */                       {
/* 3963 */                         out.write("\n\t\t\t   \t\t<select  alt=\"Web Service\" name=\"WebServiceFilter\" styleClass=\"formtext\" onchange=\"loadURLType(this.value,this,'webserviceFilterBy')\" >\n\t\t\t   \t\t<option value=\"All\">All</option>\n\t\t\t   \t\t<optgroup  label=\"");
/* 3964 */                         out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 3965 */                         out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t  \n\t\t\t   ");
/*      */                         
/* 3967 */                         for (int webservicecount = 0; webservicecount < webservicefields.size(); webservicecount++)
/*      */                         {
/*      */ 
/* 3970 */                           out.write("\n\t\t\t\t\t\t<option style=\"background-color: #FFF8C6\" value=\"");
/* 3971 */                           out.print(((Properties)webservicefields.get(webservicecount)).getProperty("labelalias") + "$" + ((Properties)webservicefields.get(webservicecount)).getProperty("fieldid"));
/* 3972 */                           out.write(34);
/* 3973 */                           out.write(62);
/* 3974 */                           out.print(((Properties)webservicefields.get(webservicecount)).getProperty("label"));
/* 3975 */                           out.write("</option>\n                          ");
/*      */                         }
/* 3977 */                         out.write("\n                    </select>\n\t\t\t  ");
/*      */                       }
/* 3979 */                       out.write("\n\t\t\t  &nbsp;&nbsp;&nbsp;<span id=\"webserviceFilterBy\"></span>\n\t\t</td>\n\t\t\n\t\t</tr>\n\t\t</table>\n\t\t</td>\n\t\t");
/* 3980 */                       if (_jspx_meth_html_005fhidden_005f22(_jspx_th_logic_005fnotEmpty_005f15, _jspx_page_context))
/*      */                         return;
/* 3982 */                       out.write("\n\t\t</tr>\n\t\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 3983 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("webservice", request.getCharacterEncoding()), out, false);
/* 3984 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 3985 */                       out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 3986 */                       out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm2('responseTime','generateAttributeReport',document.forms[1].webservice)\">");
/* 3987 */                       out.print(FormatUtil.getString("am.reporttab.resofwebservices.text"));
/* 3988 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\" >\n\t\t\t\t<div id=\"webServOpn_1\">\n               \t\t\t");
/* 3989 */                       out.print(FormatUtil.getString("am.reporttab.shortname.operationexecutiontime.text"));
/* 3990 */                       out.write("</td>\n\t\t\t\t</div>\n\t\t\t\t</tr>\n\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"webServOpn_2\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm2('operationExecutionTime','generateAttributeReport',document.forms[1].webservice)\">");
/* 3991 */                       out.print(FormatUtil.getString("am.reporttab.operationexecutiontime.text"));
/* 3992 */                       out.write("</a>\n\t\t\t\t<br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\n\t\t\t\t");
/*      */                       
/* 3994 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f16 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 3995 */                       _jspx_th_logic_005fnotEmpty_005f16.setPageContext(_jspx_page_context);
/* 3996 */                       _jspx_th_logic_005fnotEmpty_005f16.setParent(_jspx_th_logic_005fnotEmpty_005f15);
/*      */                       
/* 3998 */                       _jspx_th_logic_005fnotEmpty_005f16.setName("ReportForm");
/*      */                       
/* 4000 */                       _jspx_th_logic_005fnotEmpty_005f16.setProperty("webserviceArrayAttribute");
/* 4001 */                       int _jspx_eval_logic_005fnotEmpty_005f16 = _jspx_th_logic_005fnotEmpty_005f16.doStartTag();
/* 4002 */                       if (_jspx_eval_logic_005fnotEmpty_005f16 != 0) {
/*      */                         for (;;) {
/* 4004 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4005 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 4006 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\n\t\t\t\t\t");
/* 4007 */                           if (_jspx_meth_html_005fselect_005f9(_jspx_th_logic_005fnotEmpty_005f16, _jspx_page_context))
/*      */                             return;
/* 4009 */                           out.write("\n\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t  ");
/* 4010 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f16.doAfterBody();
/* 4011 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4015 */                       if (_jspx_th_logic_005fnotEmpty_005f16.doEndTag() == 5) {
/* 4016 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f16); return;
/*      */                       }
/*      */                       
/* 4019 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f16);
/* 4020 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\n\t\t  ");
/* 4021 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f15.doAfterBody();
/* 4022 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4026 */                   if (_jspx_th_logic_005fnotEmpty_005f15.doEndTag() == 5) {
/* 4027 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f15); return;
/*      */                   }
/*      */                   
/* 4030 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f15);
/* 4031 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n\n\n\n<ul id=\"sub_8\" class=\"none\">\n\t\t<li id=\"top_23\" style=\"height:");
/* 4032 */                   out.print(height2);
/* 4033 */                   out.write("px\">\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t");
/*      */                   
/* 4035 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f17 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4036 */                   _jspx_th_logic_005fnotEmpty_005f17.setPageContext(_jspx_page_context);
/* 4037 */                   _jspx_th_logic_005fnotEmpty_005f17.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 4039 */                   _jspx_th_logic_005fnotEmpty_005f17.setName("ReportForm");
/*      */                   
/* 4041 */                   _jspx_th_logic_005fnotEmpty_005f17.setProperty("webservers");
/* 4042 */                   int _jspx_eval_logic_005fnotEmpty_005f17 = _jspx_th_logic_005fnotEmpty_005f17.doStartTag();
/* 4043 */                   if (_jspx_eval_logic_005fnotEmpty_005f17 != 0) {
/*      */                     for (;;) {
/* 4045 */                       out.write(10);
/* 4046 */                       out.write(9);
/* 4047 */                       out.write(9);
/*      */                       
/* 4049 */                       Properties allWebserversTypes = (Properties)frm.getWebservers().get(0);
/* 4050 */                       String allWebserver = (String)allWebserversTypes.get("value");
/*      */                       
/* 4052 */                       out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\" border=\"0\"><tr>\n\t\t<td  class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\"  > &nbsp;&nbsp;&nbsp; ");
/* 4053 */                       out.print(FormatUtil.getString("am.webclient.reports.select.webserver.text"));
/* 4054 */                       out.write(" &nbsp;\n\t\t\t");
/*      */                       
/* 4056 */                       SelectTag _jspx_th_html_005fselect_005f10 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 4057 */                       _jspx_th_html_005fselect_005f10.setPageContext(_jspx_page_context);
/* 4058 */                       _jspx_th_html_005fselect_005f10.setParent(_jspx_th_logic_005fnotEmpty_005f17);
/*      */                       
/* 4060 */                       _jspx_th_html_005fselect_005f10.setProperty("webserver");
/*      */                       
/* 4062 */                       _jspx_th_html_005fselect_005f10.setStyleClass("formtext");
/*      */                       
/* 4064 */                       _jspx_th_html_005fselect_005f10.setAlt(allWebserver);
/*      */                       
/* 4066 */                       _jspx_th_html_005fselect_005f10.setOnchange("loadURLType(this.options[this.selectedIndex].value,this,'webserversFilterBy')");
/* 4067 */                       int _jspx_eval_html_005fselect_005f10 = _jspx_th_html_005fselect_005f10.doStartTag();
/* 4068 */                       if (_jspx_eval_html_005fselect_005f10 != 0) {
/* 4069 */                         if (_jspx_eval_html_005fselect_005f10 != 1) {
/* 4070 */                           out = _jspx_page_context.pushBody();
/* 4071 */                           _jspx_th_html_005fselect_005f10.setBodyContent((BodyContent)out);
/* 4072 */                           _jspx_th_html_005fselect_005f10.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 4075 */                           out.write("\n      \t\t");
/* 4076 */                           if (_jspx_meth_html_005foptionsCollection_005f10(_jspx_th_html_005fselect_005f10, _jspx_page_context))
/*      */                             return;
/* 4078 */                           out.write("\n      \t\t\n      \t\t");
/*      */                           
/* 4080 */                           ArrayList webserverfields = MyFields.parseCustomFieldInfo(custInfo, allWebserver, isPrivilegedUser);
/* 4081 */                           if (webserverfields.size() > 0)
/*      */                           {
/* 4083 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 4084 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 4085 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 4087 */                           for (int webservercount = 0; webservercount < webserverfields.size(); webservercount++)
/*      */                           {
/* 4089 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 4091 */                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 4092 */                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 4093 */                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f10);
/*      */                             
/* 4095 */                             _jspx_th_html_005foption_005f4.setStyle("background-color: #FFF8C6");
/*      */                             
/* 4097 */                             _jspx_th_html_005foption_005f4.setValue(((Properties)webserverfields.get(webservercount)).getProperty("labelalias") + "$" + ((Properties)webserverfields.get(webservercount)).getProperty("fieldid"));
/* 4098 */                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 4099 */                             if (_jspx_eval_html_005foption_005f4 != 0) {
/* 4100 */                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 4101 */                                 out = _jspx_page_context.pushBody();
/* 4102 */                                 _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 4103 */                                 _jspx_th_html_005foption_005f4.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4106 */                                 out.print(((Properties)webserverfields.get(webservercount)).getProperty("label"));
/* 4107 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 4108 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4111 */                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 4112 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4115 */                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 4116 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                             }
/*      */                             
/* 4119 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f4);
/* 4120 */                             out.write("\n                          ");
/*      */                           }
/* 4122 */                           out.write("\n      \t\t");
/* 4123 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f10.doAfterBody();
/* 4124 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 4127 */                         if (_jspx_eval_html_005fselect_005f10 != 1) {
/* 4128 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 4131 */                       if (_jspx_th_html_005fselect_005f10.doEndTag() == 5) {
/* 4132 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f10); return;
/*      */                       }
/*      */                       
/* 4135 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f10);
/* 4136 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"webserversFilterBy\"></span>\n\t\t</td>\n\t\t</tr></table></td>\n\t\t</tr>\n\t\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 4137 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("webserver", request.getCharacterEncoding()), out, false);
/* 4138 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 4139 */                       out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 4140 */                       out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('responseTime','generateAttributeReport',document.forms[1].webserver)\">");
/* 4141 */                       out.print(FormatUtil.getString("am.reporttab.responsetimeofwebservers.text"));
/* 4142 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t");
/*      */                       
/* 4144 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f18 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4145 */                       _jspx_th_logic_005fnotEmpty_005f18.setPageContext(_jspx_page_context);
/* 4146 */                       _jspx_th_logic_005fnotEmpty_005f18.setParent(_jspx_th_logic_005fnotEmpty_005f17);
/*      */                       
/* 4148 */                       _jspx_th_logic_005fnotEmpty_005f18.setName("ReportForm");
/*      */                       
/* 4150 */                       _jspx_th_logic_005fnotEmpty_005f18.setProperty("webserverArrayAttribute");
/* 4151 */                       int _jspx_eval_logic_005fnotEmpty_005f18 = _jspx_th_logic_005fnotEmpty_005f18.doStartTag();
/* 4152 */                       if (_jspx_eval_logic_005fnotEmpty_005f18 != 0) {
/*      */                         for (;;) {
/* 4154 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4155 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 4156 */                           out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\n\t\t\t\t\t");
/* 4157 */                           if (_jspx_meth_html_005fselect_005f11(_jspx_th_logic_005fnotEmpty_005f18, _jspx_page_context))
/*      */                             return;
/* 4159 */                           out.write("\n\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t ");
/* 4160 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f18.doAfterBody();
/* 4161 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4165 */                       if (_jspx_th_logic_005fnotEmpty_005f18.doEndTag() == 5) {
/* 4166 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f18); return;
/*      */                       }
/*      */                       
/* 4169 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f18);
/* 4170 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\n\t\t  ");
/* 4171 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f17.doAfterBody();
/* 4172 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4176 */                   if (_jspx_th_logic_005fnotEmpty_005f17.doEndTag() == 5) {
/* 4177 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f17); return;
/*      */                   }
/*      */                   
/* 4180 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f17);
/* 4181 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n\n\n<ul id=\"sub_9\" class=\"none\">\n\t\t<li id=\"top_24\" style=\"height:");
/* 4182 */                   out.print(height2);
/* 4183 */                   out.write("px\">\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\t\t");
/*      */                   
/* 4185 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f19 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4186 */                   _jspx_th_logic_005fnotEmpty_005f19.setPageContext(_jspx_page_context);
/* 4187 */                   _jspx_th_logic_005fnotEmpty_005f19.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 4189 */                   _jspx_th_logic_005fnotEmpty_005f19.setName("ReportForm");
/*      */                   
/* 4191 */                   _jspx_th_logic_005fnotEmpty_005f19.setProperty("urls");
/* 4192 */                   int _jspx_eval_logic_005fnotEmpty_005f19 = _jspx_th_logic_005fnotEmpty_005f19.doStartTag();
/* 4193 */                   if (_jspx_eval_logic_005fnotEmpty_005f19 != 0) {
/*      */                     for (;;) {
/* 4195 */                       out.write(10);
/* 4196 */                       out.write(9);
/* 4197 */                       out.write(9);
/*      */                       
/* 4199 */                       Properties allUrlsTypes = (Properties)frm.getUrls().get(0);
/* 4200 */                       String allUrl = (String)allUrlsTypes.get("value");
/* 4201 */                       if (!isListEmpty)
/*      */                       {
/* 4203 */                         out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\" border=\"0\"><tr>\n\t\t<td  class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" >&nbsp;&nbsp;&nbsp; ");
/* 4204 */                         out.print(FormatUtil.getString("am.webclient.reports.select.urls.text"));
/* 4205 */                         out.write(" &nbsp;\n\t\t\t");
/*      */                         
/* 4207 */                         SelectTag _jspx_th_html_005fselect_005f12 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 4208 */                         _jspx_th_html_005fselect_005f12.setPageContext(_jspx_page_context);
/* 4209 */                         _jspx_th_html_005fselect_005f12.setParent(_jspx_th_logic_005fnotEmpty_005f19);
/*      */                         
/* 4211 */                         _jspx_th_html_005fselect_005f12.setProperty("url");
/*      */                         
/* 4213 */                         _jspx_th_html_005fselect_005f12.setStyleClass("formtext");
/*      */                         
/* 4215 */                         _jspx_th_html_005fselect_005f12.setAlt(allUrl);
/*      */                         
/* 4217 */                         _jspx_th_html_005fselect_005f12.setOnchange("loadURLType(this.options[this.selectedIndex].value,this,'urlsFilterBy')");
/* 4218 */                         int _jspx_eval_html_005fselect_005f12 = _jspx_th_html_005fselect_005f12.doStartTag();
/* 4219 */                         if (_jspx_eval_html_005fselect_005f12 != 0) {
/* 4220 */                           if (_jspx_eval_html_005fselect_005f12 != 1) {
/* 4221 */                             out = _jspx_page_context.pushBody();
/* 4222 */                             _jspx_th_html_005fselect_005f12.setBodyContent((BodyContent)out);
/* 4223 */                             _jspx_th_html_005fselect_005f12.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 4226 */                             out.write("\n      \t\t\t\t");
/* 4227 */                             if (_jspx_meth_html_005foptionsCollection_005f12(_jspx_th_html_005fselect_005f12, _jspx_page_context))
/*      */                               return;
/* 4229 */                             out.write("\n      \t\t\t\t");
/*      */                             
/* 4231 */                             ArrayList urlfields = MyFields.parseCustomFieldInfo(custInfo, allUrl, isPrivilegedUser);
/* 4232 */                             if (urlfields.size() > 0)
/*      */                             {
/* 4234 */                               out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 4235 */                               out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 4236 */                               out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                             }
/* 4238 */                             for (int urlcount = 0; urlcount < urlfields.size(); urlcount++)
/*      */                             {
/* 4240 */                               out.write("\n\t\t\t\t\t\t");
/*      */                               
/* 4242 */                               OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 4243 */                               _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 4244 */                               _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f12);
/*      */                               
/* 4246 */                               _jspx_th_html_005foption_005f5.setStyle("background-color: #FFF8C6");
/*      */                               
/* 4248 */                               _jspx_th_html_005foption_005f5.setValue(((Properties)urlfields.get(urlcount)).getProperty("labelalias") + "$" + ((Properties)urlfields.get(urlcount)).getProperty("fieldid"));
/* 4249 */                               int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 4250 */                               if (_jspx_eval_html_005foption_005f5 != 0) {
/* 4251 */                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 4252 */                                   out = _jspx_page_context.pushBody();
/* 4253 */                                   _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 4254 */                                   _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 4257 */                                   out.print(((Properties)urlfields.get(urlcount)).getProperty("label"));
/* 4258 */                                   int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 4259 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4262 */                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 4263 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4266 */                               if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 4267 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                               }
/*      */                               
/* 4270 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f5);
/* 4271 */                               out.write("\n                          ");
/*      */                             }
/* 4273 */                             out.write("\n      \t\t\t");
/* 4274 */                             int evalDoAfterBody = _jspx_th_html_005fselect_005f12.doAfterBody();
/* 4275 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 4278 */                           if (_jspx_eval_html_005fselect_005f12 != 1) {
/* 4279 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 4282 */                         if (_jspx_th_html_005fselect_005f12.doEndTag() == 5) {
/* 4283 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f12); return;
/*      */                         }
/*      */                         
/* 4286 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f12);
/* 4287 */                         out.write("&nbsp;&nbsp;&nbsp;<span id=\"urlsFilterBy\"></span>\n\t\t</td>\n\t\t</tr></table></td>\n\t\t</tr>\n\t\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 4288 */                         JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("url", request.getCharacterEncoding()), out, false);
/* 4289 */                         out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 4290 */                         out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 4291 */                         out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('responseTime','generateAttributeReport',document.forms[1].url)\">");
/* 4292 */                         out.print(FormatUtil.getString("am.reporttab.responsetimeofurls.text"));
/* 4293 */                         out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t");
/*      */                         
/* 4295 */                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f20 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4296 */                         _jspx_th_logic_005fnotEmpty_005f20.setPageContext(_jspx_page_context);
/* 4297 */                         _jspx_th_logic_005fnotEmpty_005f20.setParent(_jspx_th_logic_005fnotEmpty_005f19);
/*      */                         
/* 4299 */                         _jspx_th_logic_005fnotEmpty_005f20.setName("ReportForm");
/*      */                         
/* 4301 */                         _jspx_th_logic_005fnotEmpty_005f20.setProperty("urlsArrayAttribute");
/* 4302 */                         int _jspx_eval_logic_005fnotEmpty_005f20 = _jspx_th_logic_005fnotEmpty_005f20.doStartTag();
/* 4303 */                         if (_jspx_eval_logic_005fnotEmpty_005f20 != 0) {
/*      */                           for (;;) {
/* 4305 */                             out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4306 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 4307 */                             out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\t\t\t\n\t\t\t\t\t");
/* 4308 */                             if (_jspx_meth_html_005fselect_005f13(_jspx_th_logic_005fnotEmpty_005f20, _jspx_page_context))
/*      */                               return;
/* 4310 */                             out.write("\n\n\t\t\t\t\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t ");
/* 4311 */                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f20.doAfterBody();
/* 4312 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4316 */                         if (_jspx_th_logic_005fnotEmpty_005f20.doEndTag() == 5) {
/* 4317 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f20); return;
/*      */                         }
/*      */                         
/* 4320 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f20);
/* 4321 */                         out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\t\t\t");
/*      */                       }
/* 4323 */                       out.write("\n\t\t  ");
/* 4324 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f19.doAfterBody();
/* 4325 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4329 */                   if (_jspx_th_logic_005fnotEmpty_005f19.doEndTag() == 5) {
/* 4330 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f19); return;
/*      */                   }
/*      */                   
/* 4333 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f19);
/* 4334 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n\n\n\n\n\n<ul id=\"sub_10\" class=\"none\">\n\t\t<li id=\"top_25\" style=\"height:");
/* 4335 */                   out.print(height2);
/* 4336 */                   out.write("px\">\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t");
/*      */                   
/* 4338 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f21 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4339 */                   _jspx_th_logic_005fnotEmpty_005f21.setPageContext(_jspx_page_context);
/* 4340 */                   _jspx_th_logic_005fnotEmpty_005f21.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 4342 */                   _jspx_th_logic_005fnotEmpty_005f21.setName("ReportForm");
/*      */                   
/* 4344 */                   _jspx_th_logic_005fnotEmpty_005f21.setProperty("services");
/* 4345 */                   int _jspx_eval_logic_005fnotEmpty_005f21 = _jspx_th_logic_005fnotEmpty_005f21.doStartTag();
/* 4346 */                   if (_jspx_eval_logic_005fnotEmpty_005f21 != 0) {
/*      */                     for (;;) {
/* 4348 */                       out.write(10);
/* 4349 */                       out.write(9);
/* 4350 */                       out.write(9);
/*      */                       
/* 4352 */                       Properties allServicesTypes = (Properties)frm.getServices().get(0);
/* 4353 */                       String allService = (String)allServicesTypes.get("value");
/*      */                       
/* 4355 */                       out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\" border=\"0\"><tr>\n\t\t<td  class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" >&nbsp;&nbsp;&nbsp; ");
/* 4356 */                       out.print(FormatUtil.getString("am.webclient.reports.select.services.text"));
/* 4357 */                       out.write("&nbsp;\n\t\t");
/*      */                       
/* 4359 */                       SelectTag _jspx_th_html_005fselect_005f14 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 4360 */                       _jspx_th_html_005fselect_005f14.setPageContext(_jspx_page_context);
/* 4361 */                       _jspx_th_html_005fselect_005f14.setParent(_jspx_th_logic_005fnotEmpty_005f21);
/*      */                       
/* 4363 */                       _jspx_th_html_005fselect_005f14.setProperty("service");
/*      */                       
/* 4365 */                       _jspx_th_html_005fselect_005f14.setStyleClass("formtext");
/*      */                       
/* 4367 */                       _jspx_th_html_005fselect_005f14.setAlt(allService);
/*      */                       
/* 4369 */                       _jspx_th_html_005fselect_005f14.setOnchange("loadURLType(this.options[this.selectedIndex].value,this,'servicesFilterBy')");
/* 4370 */                       int _jspx_eval_html_005fselect_005f14 = _jspx_th_html_005fselect_005f14.doStartTag();
/* 4371 */                       if (_jspx_eval_html_005fselect_005f14 != 0) {
/* 4372 */                         if (_jspx_eval_html_005fselect_005f14 != 1) {
/* 4373 */                           out = _jspx_page_context.pushBody();
/* 4374 */                           _jspx_th_html_005fselect_005f14.setBodyContent((BodyContent)out);
/* 4375 */                           _jspx_th_html_005fselect_005f14.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 4378 */                           out.write("\n      \t\t");
/* 4379 */                           if (_jspx_meth_html_005foptionsCollection_005f14(_jspx_th_html_005fselect_005f14, _jspx_page_context))
/*      */                             return;
/* 4381 */                           out.write("\n      \t\t");
/*      */                           
/* 4383 */                           ArrayList servicefields = MyFields.parseCustomFieldInfo(custInfo, allService, isPrivilegedUser);
/* 4384 */                           if (servicefields.size() > 0)
/*      */                           {
/* 4386 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 4387 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 4388 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 4390 */                           for (int servicecount = 0; servicecount < servicefields.size(); servicecount++)
/*      */                           {
/* 4392 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 4394 */                             OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 4395 */                             _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 4396 */                             _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f14);
/*      */                             
/* 4398 */                             _jspx_th_html_005foption_005f6.setStyle("background-color: #FFF8C6");
/*      */                             
/* 4400 */                             _jspx_th_html_005foption_005f6.setValue(((Properties)servicefields.get(servicecount)).getProperty("labelalias") + "$" + ((Properties)servicefields.get(servicecount)).getProperty("fieldid"));
/* 4401 */                             int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 4402 */                             if (_jspx_eval_html_005foption_005f6 != 0) {
/* 4403 */                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 4404 */                                 out = _jspx_page_context.pushBody();
/* 4405 */                                 _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 4406 */                                 _jspx_th_html_005foption_005f6.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4409 */                                 out.print(((Properties)servicefields.get(servicecount)).getProperty("label"));
/* 4410 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 4411 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4414 */                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 4415 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4418 */                             if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 4419 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                             }
/*      */                             
/* 4422 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f6);
/* 4423 */                             out.write("\n                          ");
/*      */                           }
/* 4425 */                           out.write("\n     \t\t");
/* 4426 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f14.doAfterBody();
/* 4427 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 4430 */                         if (_jspx_eval_html_005fselect_005f14 != 1) {
/* 4431 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 4434 */                       if (_jspx_th_html_005fselect_005f14.doEndTag() == 5) {
/* 4435 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f14); return;
/*      */                       }
/*      */                       
/* 4438 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f14);
/* 4439 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"servicesFilterBy\"></span>\n\t\t</td>\n\t\t</tr></table></td>\n\t\t</tr>\n<tr><td colspan=\"3\" height=\"3\"></td></tr>\n<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 4440 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("services", request.getCharacterEncoding()), out, false);
/* 4441 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 4442 */                       out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 4443 */                       out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('responseTime','generateAttributeReport',document.forms[1].service)\">");
/* 4444 */                       out.print(FormatUtil.getString("am.reporttab.resofservices.text"));
/* 4445 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t");
/*      */                       
/* 4447 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f22 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4448 */                       _jspx_th_logic_005fnotEmpty_005f22.setPageContext(_jspx_page_context);
/* 4449 */                       _jspx_th_logic_005fnotEmpty_005f22.setParent(_jspx_th_logic_005fnotEmpty_005f21);
/*      */                       
/* 4451 */                       _jspx_th_logic_005fnotEmpty_005f22.setName("ReportForm");
/*      */                       
/* 4453 */                       _jspx_th_logic_005fnotEmpty_005f22.setProperty("servicesArrayAttribute");
/* 4454 */                       int _jspx_eval_logic_005fnotEmpty_005f22 = _jspx_th_logic_005fnotEmpty_005f22.doStartTag();
/* 4455 */                       if (_jspx_eval_logic_005fnotEmpty_005f22 != 0) {
/*      */                         for (;;) {
/* 4457 */                           out.write("\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4458 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 4459 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\t\n\t\t\t\t\t");
/* 4460 */                           if (_jspx_meth_html_005fselect_005f15(_jspx_th_logic_005fnotEmpty_005f22, _jspx_page_context))
/*      */                             return;
/* 4462 */                           out.write("\n\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t       ");
/* 4463 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f22.doAfterBody();
/* 4464 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4468 */                       if (_jspx_th_logic_005fnotEmpty_005f22.doEndTag() == 5) {
/* 4469 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f22); return;
/*      */                       }
/*      */                       
/* 4472 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f22);
/* 4473 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\t\t");
/* 4474 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f21.doAfterBody();
/* 4475 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4479 */                   if (_jspx_th_logic_005fnotEmpty_005f21.doEndTag() == 5) {
/* 4480 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f21); return;
/*      */                   }
/*      */                   
/* 4483 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f21);
/* 4484 */                   out.write("\n\t\t</table>\n\n\t</li>\n\t</ul>\n<ul id=\"sub_11\" class=\"none\">\n\t\t<li id=\"top_26\" style=\"height:");
/* 4485 */                   out.print(height2);
/* 4486 */                   out.write("px\">\n\n\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t");
/*      */                   
/* 4488 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f23 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4489 */                   _jspx_th_logic_005fnotEmpty_005f23.setPageContext(_jspx_page_context);
/* 4490 */                   _jspx_th_logic_005fnotEmpty_005f23.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 4492 */                   _jspx_th_logic_005fnotEmpty_005f23.setName("ReportForm");
/*      */                   
/* 4494 */                   _jspx_th_logic_005fnotEmpty_005f23.setProperty("mailservers");
/* 4495 */                   int _jspx_eval_logic_005fnotEmpty_005f23 = _jspx_th_logic_005fnotEmpty_005f23.doStartTag();
/* 4496 */                   if (_jspx_eval_logic_005fnotEmpty_005f23 != 0) {
/*      */                     for (;;) {
/* 4498 */                       out.write(10);
/* 4499 */                       out.write(9);
/* 4500 */                       out.write(9);
/*      */                       
/* 4502 */                       Properties allMailserversTypes = (Properties)frm.getMailservers().get(0);
/* 4503 */                       String allMailserver = (String)allMailserversTypes.get("value");
/*      */                       
/* 4505 */                       out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\"><tr>\n\t\t<td  class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\"  >&nbsp;&nbsp;&nbsp; ");
/* 4506 */                       out.print(FormatUtil.getString("am.webclient.reports.select.mailserver.text"));
/* 4507 */                       out.write("&nbsp;\n\t\t");
/*      */                       
/* 4509 */                       SelectTag _jspx_th_html_005fselect_005f16 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 4510 */                       _jspx_th_html_005fselect_005f16.setPageContext(_jspx_page_context);
/* 4511 */                       _jspx_th_html_005fselect_005f16.setParent(_jspx_th_logic_005fnotEmpty_005f23);
/*      */                       
/* 4513 */                       _jspx_th_html_005fselect_005f16.setProperty("mailservice");
/*      */                       
/* 4515 */                       _jspx_th_html_005fselect_005f16.setAlt(allMailserver);
/*      */                       
/* 4517 */                       _jspx_th_html_005fselect_005f16.setOnchange("eval(showRelaventReportforMail(this))");
/*      */                       
/* 4519 */                       _jspx_th_html_005fselect_005f16.setStyleClass("formtext");
/* 4520 */                       int _jspx_eval_html_005fselect_005f16 = _jspx_th_html_005fselect_005f16.doStartTag();
/* 4521 */                       if (_jspx_eval_html_005fselect_005f16 != 0) {
/* 4522 */                         if (_jspx_eval_html_005fselect_005f16 != 1) {
/* 4523 */                           out = _jspx_page_context.pushBody();
/* 4524 */                           _jspx_th_html_005fselect_005f16.setBodyContent((BodyContent)out);
/* 4525 */                           _jspx_th_html_005fselect_005f16.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 4528 */                           out.write("\n      \t\t");
/* 4529 */                           if (_jspx_meth_html_005foptionsCollection_005f16(_jspx_th_html_005fselect_005f16, _jspx_page_context))
/*      */                             return;
/* 4531 */                           out.write("\n      \t\t");
/*      */                           
/* 4533 */                           ArrayList mailserverfields = MyFields.parseCustomFieldInfo(custInfo, allMailserver, isPrivilegedUser);
/* 4534 */                           if (mailserverfields.size() > 0)
/*      */                           {
/* 4536 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 4537 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 4538 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 4540 */                           for (int mailservercount = 0; mailservercount < mailserverfields.size(); mailservercount++)
/*      */                           {
/* 4542 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 4544 */                             OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 4545 */                             _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 4546 */                             _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f16);
/*      */                             
/* 4548 */                             _jspx_th_html_005foption_005f7.setStyle("background-color: #FFF8C6");
/*      */                             
/* 4550 */                             _jspx_th_html_005foption_005f7.setValue(((Properties)mailserverfields.get(mailservercount)).getProperty("labelalias") + "$" + ((Properties)mailserverfields.get(mailservercount)).getProperty("fieldid"));
/* 4551 */                             int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 4552 */                             if (_jspx_eval_html_005foption_005f7 != 0) {
/* 4553 */                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 4554 */                                 out = _jspx_page_context.pushBody();
/* 4555 */                                 _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 4556 */                                 _jspx_th_html_005foption_005f7.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4559 */                                 out.print(((Properties)mailserverfields.get(mailservercount)).getProperty("label"));
/* 4560 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 4561 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4564 */                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 4565 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4568 */                             if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 4569 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                             }
/*      */                             
/* 4572 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f7);
/* 4573 */                             out.write("\n                          ");
/*      */                           }
/* 4575 */                           out.write("\n      \t\t");
/* 4576 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f16.doAfterBody();
/* 4577 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 4580 */                         if (_jspx_eval_html_005fselect_005f16 != 1) {
/* 4581 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 4584 */                       if (_jspx_th_html_005fselect_005f16.doEndTag() == 5) {
/* 4585 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f16); return;
/*      */                       }
/*      */                       
/* 4588 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f16);
/* 4589 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"mailserverFilterBy\"></span>\n\t\t</td>\n\t\t</tr></table></td>\n\t\t</tr>\n<tr><td colspan=\"3\" height=\"3\"></td></tr>\n<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 4590 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("mailserver", request.getCharacterEncoding()), out, false);
/* 4591 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr id=\"Mhealth\" >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4592 */                       out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 4593 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"Mhealth\"><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].mailservice)\">");
/* 4594 */                       out.print(FormatUtil.getString("am.reporttab.healthofmail.text"));
/* 4595 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr id=\"Mresponsetime\">\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n\t\t\t\t<div id=\"Mresponsetime_1\">\n               \t\t\t");
/* 4596 */                       out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 4597 */                       out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n\t\t\t\t<div id=\"Mresponsetime_2\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('responseTime','generateAttributeReport',document.forms[1].mailservice)\">");
/* 4598 */                       out.print(FormatUtil.getString("am.reporttab.resofmail.text"));
/* 4599 */                       out.write("</a><br /> <br />\n\t\t\t\t</div>\n                \t\t</td></tr>\n\n\t\t\t      ");
/*      */                       
/* 4601 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f24 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4602 */                       _jspx_th_logic_005fnotEmpty_005f24.setPageContext(_jspx_page_context);
/* 4603 */                       _jspx_th_logic_005fnotEmpty_005f24.setParent(_jspx_th_logic_005fnotEmpty_005f23);
/*      */                       
/* 4605 */                       _jspx_th_logic_005fnotEmpty_005f24.setName("ReportForm");
/*      */                       
/* 4607 */                       _jspx_th_logic_005fnotEmpty_005f24.setProperty("mailserverArrayAttribute");
/* 4608 */                       int _jspx_eval_logic_005fnotEmpty_005f24 = _jspx_th_logic_005fnotEmpty_005f24.doStartTag();
/* 4609 */                       if (_jspx_eval_logic_005fnotEmpty_005f24 != 0) {
/*      */                         for (;;) {
/* 4611 */                           out.write("\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4612 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 4613 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\t\t\t\n\t\t\t\t\t");
/* 4614 */                           if (_jspx_meth_html_005fselect_005f17(_jspx_th_logic_005fnotEmpty_005f24, _jspx_page_context))
/*      */                             return;
/* 4616 */                           out.write("\n\n\t\t\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t       ");
/* 4617 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f24.doAfterBody();
/* 4618 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4622 */                       if (_jspx_th_logic_005fnotEmpty_005f24.doEndTag() == 5) {
/* 4623 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f24); return;
/*      */                       }
/*      */                       
/* 4626 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f24);
/* 4627 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t\t");
/* 4628 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f23.doAfterBody();
/* 4629 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4633 */                   if (_jspx_th_logic_005fnotEmpty_005f23.doEndTag() == 5) {
/* 4634 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f23); return;
/*      */                   }
/*      */                   
/* 4637 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f23);
/* 4638 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n<ul id=\"sub_12\" class=\"none\">\n\t\t<li id=\"top_27\" style=\"height:");
/* 4639 */                   out.print(height2);
/* 4640 */                   out.write("px\">\n\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t");
/*      */                   
/* 4642 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f25 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4643 */                   _jspx_th_logic_005fnotEmpty_005f25.setPageContext(_jspx_page_context);
/* 4644 */                   _jspx_th_logic_005fnotEmpty_005f25.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 4646 */                   _jspx_th_logic_005fnotEmpty_005f25.setName("ReportForm");
/*      */                   
/* 4648 */                   _jspx_th_logic_005fnotEmpty_005f25.setProperty("javaservers");
/* 4649 */                   int _jspx_eval_logic_005fnotEmpty_005f25 = _jspx_th_logic_005fnotEmpty_005f25.doStartTag();
/* 4650 */                   if (_jspx_eval_logic_005fnotEmpty_005f25 != 0) {
/*      */                     for (;;) {
/* 4652 */                       out.write(10);
/* 4653 */                       out.write(9);
/* 4654 */                       out.write(9);
/*      */                       
/* 4656 */                       Properties allJavaserversTypes = (Properties)frm.getJavaservers().get(0);
/* 4657 */                       String allJavaserver = (String)allJavaserversTypes.get("value");
/*      */                       
/* 4659 */                       out.write("\n\t\t<tr>\n\t\t<td colspan=\"3\" class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" >&nbsp;&nbsp;&nbsp; ");
/* 4660 */                       out.print(FormatUtil.getString("am.webclient.reports.select.javaserver.text"));
/* 4661 */                       out.write("&nbsp;\n\t\t");
/*      */                       
/* 4663 */                       SelectTag _jspx_th_html_005fselect_005f18 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 4664 */                       _jspx_th_html_005fselect_005f18.setPageContext(_jspx_page_context);
/* 4665 */                       _jspx_th_html_005fselect_005f18.setParent(_jspx_th_logic_005fnotEmpty_005f25);
/*      */                       
/* 4667 */                       _jspx_th_html_005fselect_005f18.setProperty("javaservice");
/*      */                       
/* 4669 */                       _jspx_th_html_005fselect_005f18.setAlt(allJavaserver);
/*      */                       
/* 4671 */                       _jspx_th_html_005fselect_005f18.setOnchange("loadURLType(this.options[this.selectedIndex].value,this,'javaServerFilterBy')");
/*      */                       
/* 4673 */                       _jspx_th_html_005fselect_005f18.setStyleClass("formtext");
/* 4674 */                       int _jspx_eval_html_005fselect_005f18 = _jspx_th_html_005fselect_005f18.doStartTag();
/* 4675 */                       if (_jspx_eval_html_005fselect_005f18 != 0) {
/* 4676 */                         if (_jspx_eval_html_005fselect_005f18 != 1) {
/* 4677 */                           out = _jspx_page_context.pushBody();
/* 4678 */                           _jspx_th_html_005fselect_005f18.setBodyContent((BodyContent)out);
/* 4679 */                           _jspx_th_html_005fselect_005f18.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 4682 */                           out.write("\n      \t\t");
/* 4683 */                           if (_jspx_meth_html_005foptionsCollection_005f18(_jspx_th_html_005fselect_005f18, _jspx_page_context))
/*      */                             return;
/* 4685 */                           out.write("\n      \t\t");
/*      */                           
/* 4687 */                           ArrayList javaserverfields = MyFields.parseCustomFieldInfo(custInfo, allJavaserver, isPrivilegedUser);
/* 4688 */                           if (javaserverfields.size() > 0)
/*      */                           {
/* 4690 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 4691 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 4692 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 4694 */                           for (int javaservercount = 0; javaservercount < javaserverfields.size(); javaservercount++)
/*      */                           {
/* 4696 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 4698 */                             OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 4699 */                             _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 4700 */                             _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f18);
/*      */                             
/* 4702 */                             _jspx_th_html_005foption_005f8.setStyle("background-color: #FFF8C6");
/*      */                             
/* 4704 */                             _jspx_th_html_005foption_005f8.setValue(((Properties)javaserverfields.get(javaservercount)).getProperty("labelalias") + "$" + ((Properties)javaserverfields.get(javaservercount)).getProperty("fieldid"));
/* 4705 */                             int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 4706 */                             if (_jspx_eval_html_005foption_005f8 != 0) {
/* 4707 */                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 4708 */                                 out = _jspx_page_context.pushBody();
/* 4709 */                                 _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 4710 */                                 _jspx_th_html_005foption_005f8.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4713 */                                 out.print(((Properties)javaserverfields.get(javaservercount)).getProperty("label"));
/* 4714 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 4715 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4718 */                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 4719 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4722 */                             if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 4723 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                             }
/*      */                             
/* 4726 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f8);
/* 4727 */                             out.write("\n                          ");
/*      */                           }
/* 4729 */                           out.write("\n      \t\t");
/* 4730 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f18.doAfterBody();
/* 4731 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 4734 */                         if (_jspx_eval_html_005fselect_005f18 != 1) {
/* 4735 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 4738 */                       if (_jspx_th_html_005fselect_005f18.doEndTag() == 5) {
/* 4739 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f18); return;
/*      */                       }
/*      */                       
/* 4742 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f18);
/* 4743 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"javaServerFilterBy\"></span>\n\t\t</td>\n\n\t\t</tr>\n<tr><td colspan=\"3\" height=\"3\"></td></tr>\n<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 4744 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("javaserver", request.getCharacterEncoding()), out, false);
/* 4745 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n                \t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 4746 */                       out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 4747 */                       out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('connectiontime','generateAttributeReport',document.forms[1].javaservice)\">");
/* 4748 */                       out.print(FormatUtil.getString("am.reporttab.resofjava.text"));
/* 4749 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\" >\n               \t\t\t");
/* 4750 */                       out.print(FormatUtil.getString("am.reporttab.memoryusage.text"));
/* 4751 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('memmb','generateAttributeReport',document.forms[1].javaservice)\">");
/* 4752 */                       out.print(FormatUtil.getString("am.reporttab.memofjava.text"));
/* 4753 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4754 */                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4755 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('jdkcpuid','generateAttributeReport',document.forms[1].javaservice)\">");
/* 4756 */                       out.print(FormatUtil.getString("am.reporttab.cpuofjava.text"));
/* 4757 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t");
/*      */                       
/* 4759 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f26 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4760 */                       _jspx_th_logic_005fnotEmpty_005f26.setPageContext(_jspx_page_context);
/* 4761 */                       _jspx_th_logic_005fnotEmpty_005f26.setParent(_jspx_th_logic_005fnotEmpty_005f25);
/*      */                       
/* 4763 */                       _jspx_th_logic_005fnotEmpty_005f26.setName("ReportForm");
/*      */                       
/* 4765 */                       _jspx_th_logic_005fnotEmpty_005f26.setProperty("transactionArrayAttribute");
/* 4766 */                       int _jspx_eval_logic_005fnotEmpty_005f26 = _jspx_th_logic_005fnotEmpty_005f26.doStartTag();
/* 4767 */                       if (_jspx_eval_logic_005fnotEmpty_005f26 != 0) {
/*      */                         for (;;) {
/* 4769 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4770 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 4771 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom'>\n\t\t\t\t<br />\n\t\t\t\t\t");
/* 4772 */                           if (_jspx_meth_html_005fselect_005f19(_jspx_th_logic_005fnotEmpty_005f26, _jspx_page_context))
/*      */                             return;
/* 4774 */                           out.write("\n\t\t\t\t<br /> <br />\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 4775 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f26.doAfterBody();
/* 4776 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4780 */                       if (_jspx_th_logic_005fnotEmpty_005f26.doEndTag() == 5) {
/* 4781 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f26); return;
/*      */                       }
/*      */                       
/* 4784 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f26);
/* 4785 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\t\t");
/* 4786 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f25.doAfterBody();
/* 4787 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4791 */                   if (_jspx_th_logic_005fnotEmpty_005f25.doEndTag() == 5) {
/* 4792 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f25); return;
/*      */                   }
/*      */                   
/* 4795 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f25);
/* 4796 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n\n<ul id=\"sub_13\" class=\"none\">\n\t\t<li id=\"top_28\" style=\"height:");
/* 4797 */                   out.print(height2);
/* 4798 */                   out.write("px\">\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t");
/*      */                   
/* 4800 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f27 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4801 */                   _jspx_th_logic_005fnotEmpty_005f27.setPageContext(_jspx_page_context);
/* 4802 */                   _jspx_th_logic_005fnotEmpty_005f27.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 4804 */                   _jspx_th_logic_005fnotEmpty_005f27.setName("ReportForm");
/*      */                   
/* 4806 */                   _jspx_th_logic_005fnotEmpty_005f27.setProperty("sapservers");
/* 4807 */                   int _jspx_eval_logic_005fnotEmpty_005f27 = _jspx_th_logic_005fnotEmpty_005f27.doStartTag();
/* 4808 */                   if (_jspx_eval_logic_005fnotEmpty_005f27 != 0) {
/*      */                     for (;;) {
/* 4810 */                       out.write(10);
/* 4811 */                       out.write(9);
/* 4812 */                       out.write(9);
/*      */                       
/* 4814 */                       Properties allSapserversTypes = (Properties)frm.getSapservers().get(0);
/* 4815 */                       String allSapserver = (String)allSapserversTypes.get("value");
/*      */                       
/* 4817 */                       out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\"><tr>\n\t\t<td  class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" >&nbsp;&nbsp;&nbsp; ");
/* 4818 */                       out.print(FormatUtil.getString("am.webclient.reports.select.erp.text"));
/* 4819 */                       out.write("&nbsp;\n\t\t ");
/*      */                       
/* 4821 */                       SelectTag _jspx_th_html_005fselect_005f20 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 4822 */                       _jspx_th_html_005fselect_005f20.setPageContext(_jspx_page_context);
/* 4823 */                       _jspx_th_html_005fselect_005f20.setParent(_jspx_th_logic_005fnotEmpty_005f27);
/*      */                       
/* 4825 */                       _jspx_th_html_005fselect_005f20.setStyleId("erpmonitors");
/*      */                       
/* 4827 */                       _jspx_th_html_005fselect_005f20.setAlt(allSapserver);
/*      */                       
/* 4829 */                       _jspx_th_html_005fselect_005f20.setProperty("sapservice");
/*      */                       
/* 4831 */                       _jspx_th_html_005fselect_005f20.setOnchange("eval(showRelaventERPReport(this))");
/*      */                       
/* 4833 */                       _jspx_th_html_005fselect_005f20.setStyleClass("formtext");
/* 4834 */                       int _jspx_eval_html_005fselect_005f20 = _jspx_th_html_005fselect_005f20.doStartTag();
/* 4835 */                       if (_jspx_eval_html_005fselect_005f20 != 0) {
/* 4836 */                         if (_jspx_eval_html_005fselect_005f20 != 1) {
/* 4837 */                           out = _jspx_page_context.pushBody();
/* 4838 */                           _jspx_th_html_005fselect_005f20.setBodyContent((BodyContent)out);
/* 4839 */                           _jspx_th_html_005fselect_005f20.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 4842 */                           out.write("\n      \t\t\t");
/* 4843 */                           if (_jspx_meth_html_005foptionsCollection_005f20(_jspx_th_html_005fselect_005f20, _jspx_page_context))
/*      */                             return;
/* 4845 */                           out.write("\n      \t\t\t");
/*      */                           
/* 4847 */                           ArrayList sapserverfields = MyFields.parseCustomFieldInfo(custInfo, allSapserver, isPrivilegedUser);
/* 4848 */                           if (sapserverfields.size() > 0)
/*      */                           {
/* 4850 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 4851 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 4852 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 4854 */                           for (int sapservercount = 0; sapservercount < sapserverfields.size(); sapservercount++)
/*      */                           {
/* 4856 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 4858 */                             OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 4859 */                             _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 4860 */                             _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f20);
/*      */                             
/* 4862 */                             _jspx_th_html_005foption_005f9.setStyle("background-color: #FFF8C6");
/*      */                             
/* 4864 */                             _jspx_th_html_005foption_005f9.setValue(((Properties)sapserverfields.get(sapservercount)).getProperty("labelalias") + "$" + ((Properties)sapserverfields.get(sapservercount)).getProperty("fieldid"));
/* 4865 */                             int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 4866 */                             if (_jspx_eval_html_005foption_005f9 != 0) {
/* 4867 */                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 4868 */                                 out = _jspx_page_context.pushBody();
/* 4869 */                                 _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 4870 */                                 _jspx_th_html_005foption_005f9.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 4873 */                                 out.print(((Properties)sapserverfields.get(sapservercount)).getProperty("label"));
/* 4874 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 4875 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 4878 */                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 4879 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 4882 */                             if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 4883 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                             }
/*      */                             
/* 4886 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f9);
/* 4887 */                             out.write("\n                          ");
/*      */                           }
/* 4889 */                           out.write("\n      \t\t ");
/* 4890 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f20.doAfterBody();
/* 4891 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 4894 */                         if (_jspx_eval_html_005fselect_005f20 != 1) {
/* 4895 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 4898 */                       if (_jspx_th_html_005fselect_005f20.doEndTag() == 5) {
/* 4899 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f20); return;
/*      */                       }
/*      */                       
/* 4902 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f20);
/* 4903 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"sapserverFilterby\"></span>\n\t\t</td>\n\t\t</tr></table></td>\n\t\t</tr>\n\n\t\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n        \t<tr>\n\t\t\t");
/* 4904 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("sapserver", request.getCharacterEncoding()), out, false);
/* 4905 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<div id=\"SAPright\">\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 4906 */                       out.print(FormatUtil.getString("am.reporttab.shortname.pageinofsap.text"));
/* 4907 */                       out.write("\n               \t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('sappagein','generateAttributeReport',document.forms[1].sapservice)\">");
/* 4908 */                       out.print(FormatUtil.getString("am.reporttab.pageinofsap.text"));
/* 4909 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 4910 */                       out.print(FormatUtil.getString("am.reporttab.shortname.pageoutofsap.text"));
/* 4911 */                       out.write("\n               \t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('sappageout','generateAttributeReport',document.forms[1].sapservice)\">");
/* 4912 */                       out.print(FormatUtil.getString("am.reporttab.pageoutofsap.text"));
/* 4913 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4914 */                       out.print(FormatUtil.getString("am.reporttab.shortname.sutilofsap.text"));
/* 4915 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('sutilization','generateAttributeReport',document.forms[1].sapservice)\">");
/* 4916 */                       out.print(FormatUtil.getString("am.reporttab.sutilofsap.text"));
/* 4917 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4918 */                       out.print(FormatUtil.getString("am.reporttab.shortname.butilofsap.text"));
/* 4919 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr  ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('butilization','generateAttributeReport',document.forms[1].sapservice)\">");
/* 4920 */                       out.print(FormatUtil.getString("am.reporttab.butilofsap.text"));
/* 4921 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 4922 */                       out.print(FormatUtil.getString("am.reporttab.shortname.enqreqofsap.text"));
/* 4923 */                       out.write("\n               \t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('sapenqreq','generateAttributeReport',document.forms[1].sapservice)\">");
/* 4924 */                       out.print(FormatUtil.getString("am.reporttab.enqreqofsap.text"));
/* 4925 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n                </table>\n                </div>\n                <div id=\"SAPcon\">\n                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 4926 */                       out.print(FormatUtil.getString("Connection Time"));
/* 4927 */                       out.write("\n               \t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('sapccmscontime','generateAttributeReport',document.forms[1].sapservice)\">");
/* 4928 */                       out.print(FormatUtil.getString("Connection Time"));
/* 4929 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n                </table>\n                </div>\n                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t ");
/*      */                       
/* 4931 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f28 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 4932 */                       _jspx_th_logic_005fnotEmpty_005f28.setPageContext(_jspx_page_context);
/* 4933 */                       _jspx_th_logic_005fnotEmpty_005f28.setParent(_jspx_th_logic_005fnotEmpty_005f27);
/*      */                       
/* 4935 */                       _jspx_th_logic_005fnotEmpty_005f28.setName("ReportForm");
/*      */                       
/* 4937 */                       _jspx_th_logic_005fnotEmpty_005f28.setProperty("erpArrayAttribute");
/* 4938 */                       int _jspx_eval_logic_005fnotEmpty_005f28 = _jspx_th_logic_005fnotEmpty_005f28.doStartTag();
/* 4939 */                       if (_jspx_eval_logic_005fnotEmpty_005f28 != 0) {
/*      */                         for (;;) {
/* 4941 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 4942 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 4943 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\t\n\n\t\t\t\t\t");
/* 4944 */                           if (_jspx_meth_html_005fselect_005f21(_jspx_th_logic_005fnotEmpty_005f28, _jspx_page_context))
/*      */                             return;
/* 4946 */                           out.write("\n\n\t\t\t\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t  ");
/* 4947 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f28.doAfterBody();
/* 4948 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4952 */                       if (_jspx_th_logic_005fnotEmpty_005f28.doEndTag() == 5) {
/* 4953 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f28); return;
/*      */                       }
/*      */                       
/* 4956 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f28);
/* 4957 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr><td colspan=\"3\">&nbsp;</td></tr>\n\t\t<tr>\n\t\t\t<td colspan=\"3\" align=\"center\">\n\t\t\t<div id=\"SAPcustom\">\n\t\t\t");
/*      */                       
/* 4959 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f29 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4960 */                       _jspx_th_logic_005fnotEmpty_005f29.setPageContext(_jspx_page_context);
/* 4961 */                       _jspx_th_logic_005fnotEmpty_005f29.setParent(_jspx_th_logic_005fnotEmpty_005f27);
/*      */                       
/* 4963 */                       _jspx_th_logic_005fnotEmpty_005f29.setName("CCMSData");
/* 4964 */                       int _jspx_eval_logic_005fnotEmpty_005f29 = _jspx_th_logic_005fnotEmpty_005f29.doStartTag();
/* 4965 */                       if (_jspx_eval_logic_005fnotEmpty_005f29 != 0) {
/*      */                         for (;;) {
/* 4967 */                           out.write("\n\t\t\t<table width=\"96%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"new-report-table\">\n\t\t\t  <tr>\n\t\t\t\t<td class=\"tableheadingbborder\" height=\"22\" align=\"left\" colspan=\"3\">");
/* 4968 */                           out.print(FormatUtil.getString("Custom Attributes"));
/* 4969 */                           out.write("</td>\n\t\t\t  </tr>\n\t\t\t  <tr>\n\t\t\t\t<td width=\"25%\" height=\"29\" class=\"columnheading\"> ");
/* 4970 */                           out.print(FormatUtil.getString("am.reporttab.attributes.text"));
/* 4971 */                           out.write("</td>\n\t\t\t\t<td width=\"35%\" height=\"29\" class=\"columnheading\"> ");
/* 4972 */                           out.print(FormatUtil.getString("am.webclient.cam.snmp.monitorname"));
/* 4973 */                           out.write("</td>\n\t\t\t\t<td width=\"40%\" height=\"29\" class=\"columnheading\"> ");
/* 4974 */                           out.print(FormatUtil.getString("am.webclient.camscreen.groupname.text"));
/* 4975 */                           out.write("</td>\n\t\t\t  </tr>\n\t\t\t  ");
/*      */                           
/* 4977 */                           ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.get(ForEachTag.class);
/* 4978 */                           _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4979 */                           _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f29);
/*      */                           
/* 4981 */                           _jspx_th_c_005fforEach_005f0.setVar("row");
/*      */                           
/* 4983 */                           _jspx_th_c_005fforEach_005f0.setItems("${CCMSData}");
/*      */                           
/* 4985 */                           _jspx_th_c_005fforEach_005f0.setBegin("0");
/*      */                           
/* 4987 */                           _jspx_th_c_005fforEach_005f0.setEnd("3");
/*      */                           
/* 4989 */                           _jspx_th_c_005fforEach_005f0.setVarStatus("i");
/* 4990 */                           int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                           try {
/* 4992 */                             int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4993 */                             if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                               for (;;) {
/* 4995 */                                 out.write("\n\t\t\t\t");
/*      */                                 
/* 4997 */                                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4998 */                                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4999 */                                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                 
/* 5001 */                                 _jspx_th_c_005fif_005f0.setTest("${i.index !='3'}");
/* 5002 */                                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5003 */                                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                   for (;;) {
/* 5005 */                                     out.write("\n\t\t\t\t ");
/* 5006 */                                     if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 5008 */                                     out.write("\n\t\t\t\t ");
/* 5009 */                                     if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 5011 */                                     out.write("\n\t\t\t\t ");
/* 5012 */                                     String ccmsagentname = (String)pageContext.getAttribute("ccmsaname");
/* 5013 */                                     String ccmsmbeanname = (String)pageContext.getAttribute("ccmsmbname");
/*      */                                     
/* 5015 */                                     out.write("\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td height=\"18\" class=\"bodytext\" title=\"");
/* 5016 */                                     if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 5018 */                                     out.write("\">&nbsp;<a class=\"new-report-link\" href=\"javascript:ccmsAttributeAction(");
/* 5019 */                                     if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 5021 */                                     out.write(41);
/* 5022 */                                     out.write(34);
/* 5023 */                                     out.write(62);
/* 5024 */                                     if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 5026 */                                     out.write("</a></td>\n\t\t\t\t\t\t<td height=\"18\" class=\"bodytext\" title=\"");
/* 5027 */                                     if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 5029 */                                     out.write("\">&nbsp;");
/* 5030 */                                     out.print(getTrimmedText(ccmsagentname, 35));
/* 5031 */                                     out.write("</td>\n\t\t\t\t\t\t<td height=\"18\" class=\"bodytext\" title=\"");
/* 5032 */                                     if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 5034 */                                     out.write("\">&nbsp;");
/* 5035 */                                     out.print(getTrimmedText(ccmsmbeanname, 50));
/* 5036 */                                     out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t");
/* 5037 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5038 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5042 */                                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5043 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 5046 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5047 */                                 out.write("\n\t\t\t\t\t");
/*      */                                 
/* 5049 */                                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5050 */                                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5051 */                                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                 
/* 5053 */                                 _jspx_th_c_005fif_005f1.setTest("${i.index=='3'}");
/* 5054 */                                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5055 */                                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                   for (;;) {
/* 5057 */                                     out.write("\n\t\t\t\t\t\t <tr>\n\t\t\t\t\t\t\t<td height=\"18\" class=\"bodytext\" colspan=\"3\" align=\"right\"><a class=\"new-report-link\" href=\"javascript:getCCMSMore()\">");
/* 5058 */                                     out.print(FormatUtil.getString("am.reporttab.more.text"));
/* 5059 */                                     out.write("</a>&nbsp;&nbsp;</td>\n\t\t\t\t\t\t </tr>\n\t\t\t\t");
/* 5060 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5061 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5065 */                                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5066 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 5069 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5070 */                                 out.write("\n\t\t\t   ");
/* 5071 */                                 int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 5072 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5076 */                             if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5084 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/*      */                           }
/*      */                           catch (Throwable _jspx_exception)
/*      */                           {
/*      */                             for (;;)
/*      */                             {
/* 5080 */                               int tmp19564_19563 = 0; int[] tmp19564_19561 = _jspx_push_body_count_c_005fforEach_005f0; int tmp19566_19565 = tmp19564_19561[tmp19564_19563];tmp19564_19561[tmp19564_19563] = (tmp19566_19565 - 1); if (tmp19566_19565 <= 0) break;
/* 5081 */                               out = _jspx_page_context.popBody(); }
/* 5082 */                             _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                           } finally {
/* 5084 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 5085 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                           }
/* 5087 */                           out.write("\n\t\t\t</table>\n\t\t\t");
/* 5088 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f29.doAfterBody();
/* 5089 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5093 */                       if (_jspx_th_logic_005fnotEmpty_005f29.doEndTag() == 5) {
/* 5094 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f29); return;
/*      */                       }
/*      */                       
/* 5097 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f29);
/* 5098 */                       out.write("\n\t\t\t</div>\n\t\t\t</td>\n\t\t</tr>\n\t\t");
/* 5099 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f27.doAfterBody();
/* 5100 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5104 */                   if (_jspx_th_logic_005fnotEmpty_005f27.doEndTag() == 5) {
/* 5105 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f27); return;
/*      */                   }
/*      */                   
/* 5108 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f27);
/* 5109 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n\n\t<ul id=\"sub_14\" class=\"none\">\n\t\t<li id=\"top_29\" style=\"height:");
/* 5110 */                   out.print(height2);
/* 5111 */                   out.write("px\">\n\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t");
/*      */                   
/* 5113 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f30 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5114 */                   _jspx_th_logic_005fnotEmpty_005f30.setPageContext(_jspx_page_context);
/* 5115 */                   _jspx_th_logic_005fnotEmpty_005f30.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 5117 */                   _jspx_th_logic_005fnotEmpty_005f30.setName("ReportForm");
/*      */                   
/* 5119 */                   _jspx_th_logic_005fnotEmpty_005f30.setProperty("middleware");
/* 5120 */                   int _jspx_eval_logic_005fnotEmpty_005f30 = _jspx_th_logic_005fnotEmpty_005f30.doStartTag();
/* 5121 */                   if (_jspx_eval_logic_005fnotEmpty_005f30 != 0) {
/*      */                     for (;;) {
/* 5123 */                       out.write(10);
/* 5124 */                       out.write(9);
/* 5125 */                       out.write(9);
/*      */                       
/* 5127 */                       Properties allMiddlewareTypes = (Properties)frm.getMiddleware().get(0);
/* 5128 */                       String allMiddleware = (String)allMiddlewareTypes.get("value");
/*      */                       
/* 5130 */                       out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\"><tr>\n\t\t<td  class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\"  >&nbsp;&nbsp;&nbsp; ");
/* 5131 */                       out.print(FormatUtil.getString("am.webclient.reports.select.middleware.text"));
/* 5132 */                       out.write("&nbsp;\n\t\t ");
/*      */                       
/* 5134 */                       SelectTag _jspx_th_html_005fselect_005f22 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 5135 */                       _jspx_th_html_005fselect_005f22.setPageContext(_jspx_page_context);
/* 5136 */                       _jspx_th_html_005fselect_005f22.setParent(_jspx_th_logic_005fnotEmpty_005f30);
/*      */                       
/* 5138 */                       _jspx_th_html_005fselect_005f22.setProperty("middlewareserver");
/*      */                       
/* 5140 */                       _jspx_th_html_005fselect_005f22.setAlt(allMiddleware);
/*      */                       
/* 5142 */                       _jspx_th_html_005fselect_005f22.setStyleClass("formtext");
/*      */                       
/* 5144 */                       _jspx_th_html_005fselect_005f22.setOnchange("eval(showRelaventReportforwli(this))");
/*      */                       
/* 5146 */                       _jspx_th_html_005fselect_005f22.setStyle("width:150px");
/* 5147 */                       int _jspx_eval_html_005fselect_005f22 = _jspx_th_html_005fselect_005f22.doStartTag();
/* 5148 */                       if (_jspx_eval_html_005fselect_005f22 != 0) {
/* 5149 */                         if (_jspx_eval_html_005fselect_005f22 != 1) {
/* 5150 */                           out = _jspx_page_context.pushBody();
/* 5151 */                           _jspx_th_html_005fselect_005f22.setBodyContent((BodyContent)out);
/* 5152 */                           _jspx_th_html_005fselect_005f22.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 5155 */                           out.write("\n       \t\t");
/* 5156 */                           if (_jspx_meth_html_005foptionsCollection_005f22(_jspx_th_html_005fselect_005f22, _jspx_page_context))
/*      */                             return;
/* 5158 */                           out.write("\n       \t\t");
/*      */                           
/* 5160 */                           ArrayList middlewarefields = MyFields.parseCustomFieldInfo(custInfo, allMiddleware, isPrivilegedUser);
/* 5161 */                           if (middlewarefields.size() > 0)
/*      */                           {
/* 5163 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 5164 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 5165 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 5167 */                           for (int middlewarecount = 0; middlewarecount < middlewarefields.size(); middlewarecount++)
/*      */                           {
/* 5169 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 5171 */                             OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 5172 */                             _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 5173 */                             _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f22);
/*      */                             
/* 5175 */                             _jspx_th_html_005foption_005f10.setStyle("background-color: #FFF8C6");
/*      */                             
/* 5177 */                             _jspx_th_html_005foption_005f10.setValue(((Properties)middlewarefields.get(middlewarecount)).getProperty("labelalias") + "$" + ((Properties)middlewarefields.get(middlewarecount)).getProperty("fieldid"));
/* 5178 */                             int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 5179 */                             if (_jspx_eval_html_005foption_005f10 != 0) {
/* 5180 */                               if (_jspx_eval_html_005foption_005f10 != 1) {
/* 5181 */                                 out = _jspx_page_context.pushBody();
/* 5182 */                                 _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 5183 */                                 _jspx_th_html_005foption_005f10.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 5186 */                                 out.print(((Properties)middlewarefields.get(middlewarecount)).getProperty("label"));
/* 5187 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 5188 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 5191 */                               if (_jspx_eval_html_005foption_005f10 != 1) {
/* 5192 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 5195 */                             if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 5196 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                             }
/*      */                             
/* 5199 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f10);
/* 5200 */                             out.write("\n                          ");
/*      */                           }
/* 5202 */                           out.write("\n       \t\t");
/* 5203 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f22.doAfterBody();
/* 5204 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 5207 */                         if (_jspx_eval_html_005fselect_005f22 != 1) {
/* 5208 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 5211 */                       if (_jspx_th_html_005fselect_005f22.doEndTag() == 5) {
/* 5212 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f22); return;
/*      */                       }
/*      */                       
/* 5215 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f22);
/* 5216 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"middlewareFilterby\"></span>\n\t\t</td>\n\t\t</tr></table></td>\n\n\t\t</tr>\n\t\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n        \t<tr>\n\t\t\t");
/* 5217 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("middleware", request.getCharacterEncoding()), out, false);
/* 5218 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\n\t\t\t\t<div id=\"wliRem_1\">\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"3%\">&nbsp;</td>\n\t\t\t\t\t<td align=\"left\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                       
/* 5220 */                       if ((ReportForm.moTypeCount != null) && (ReportForm.moTypeCount.get("WEBLOGIC-Integration") != null))
/*      */                       {
/*      */ 
/* 5223 */                         out.write("\n\t\t\t\t<tr id=\"wlijdbc\" >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\" >\n               \t\t\t");
/* 5224 */                         out.print(FormatUtil.getString("am.reporttab.shortname.connectionpool.text"));
/* 5225 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"wlijdbc\"><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('wlijdbc','generateAttributeReport',document.forms[1].middlewareserver)\" class=\"new-report-link\">");
/* 5226 */                         out.print(FormatUtil.getString("am.reporttab.jdbcofapp.wli.text"));
/* 5227 */                         out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr id=\"wlithread\"><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 5228 */                         out.print(FormatUtil.getString("am.reporttab.shortname.thread.text"));
/* 5229 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"wlithread\"><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('wlithread','generateAttributeReport',document.forms[1].middlewareserver)\" class=\"new-report-link\">");
/* 5230 */                         out.print(FormatUtil.getString("am.reporttab.threadofapp.wli.text"));
/* 5231 */                         out.write("</a><br /> <br />\n                \t\t</td></tr>\n\t\t\t\t<tr id=\"wlisession\"><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 5232 */                         out.print(FormatUtil.getString("am.reporttab.shortname.session.text"));
/* 5233 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"wlisession\"><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a href=\"javascript:clickAttributeForm('wlisession','generateAttributeReport',document.forms[1].middlewareserver)\" class=\"new-report-link\">");
/* 5234 */                         out.print(FormatUtil.getString("am.reporttab.httpofapp.wli.text"));
/* 5235 */                         out.write("</a><br /> <br />\n\t\t\t\t</td></tr>\n\t\t\t");
/*      */                       }
/*      */                       
/*      */ 
/* 5239 */                       out.write("\n\t\t\t");
/*      */                       
/* 5241 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f31 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5242 */                       _jspx_th_logic_005fnotEmpty_005f31.setPageContext(_jspx_page_context);
/* 5243 */                       _jspx_th_logic_005fnotEmpty_005f31.setParent(_jspx_th_logic_005fnotEmpty_005f30);
/*      */                       
/* 5245 */                       _jspx_th_logic_005fnotEmpty_005f31.setName("ReportForm");
/*      */                       
/* 5247 */                       _jspx_th_logic_005fnotEmpty_005f31.setProperty("momArrayAttribute");
/* 5248 */                       int _jspx_eval_logic_005fnotEmpty_005f31 = _jspx_th_logic_005fnotEmpty_005f31.doStartTag();
/* 5249 */                       if (_jspx_eval_logic_005fnotEmpty_005f31 != 0) {
/*      */                         for (;;) {
/* 5251 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 5252 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 5253 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\n\n\t\t\t\t\t");
/* 5254 */                           if (_jspx_meth_html_005fselect_005f23(_jspx_th_logic_005fnotEmpty_005f31, _jspx_page_context))
/*      */                             return;
/* 5256 */                           out.write("\n\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t ");
/* 5257 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f31.doAfterBody();
/* 5258 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5262 */                       if (_jspx_th_logic_005fnotEmpty_005f31.doEndTag() == 5) {
/* 5263 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f31); return;
/*      */                       }
/*      */                       
/* 5266 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f31);
/* 5267 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</div>\n\n\t\t\t<div id=\"wliRem_2\">\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t");
/*      */                       
/* 5269 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f32 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5270 */                       _jspx_th_logic_005fnotEmpty_005f32.setPageContext(_jspx_page_context);
/* 5271 */                       _jspx_th_logic_005fnotEmpty_005f32.setParent(_jspx_th_logic_005fnotEmpty_005f30);
/*      */                       
/* 5273 */                       _jspx_th_logic_005fnotEmpty_005f32.setName("ReportForm");
/*      */                       
/* 5275 */                       _jspx_th_logic_005fnotEmpty_005f32.setProperty("momArrayAttribute");
/* 5276 */                       int _jspx_eval_logic_005fnotEmpty_005f32 = _jspx_th_logic_005fnotEmpty_005f32.doStartTag();
/* 5277 */                       if (_jspx_eval_logic_005fnotEmpty_005f32 != 0) {
/*      */                         for (;;) {
/* 5279 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 5280 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 5281 */                           out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom'>\n\t\t\t\t<br />\n\n\t\t\t\t\t");
/* 5282 */                           if (_jspx_meth_html_005fselect_005f24(_jspx_th_logic_005fnotEmpty_005f32, _jspx_page_context))
/*      */                             return;
/* 5284 */                           out.write("\n\n\t\t\t\t<br /> <br />\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t ");
/* 5285 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f32.doAfterBody();
/* 5286 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5290 */                       if (_jspx_th_logic_005fnotEmpty_005f32.doEndTag() == 5) {
/* 5291 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f32); return;
/*      */                       }
/*      */                       
/* 5294 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f32);
/* 5295 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</div>\n\t\t\t</td>\n\n\t\t</tr>\n\t\t ");
/* 5296 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f30.doAfterBody();
/* 5297 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5301 */                   if (_jspx_th_logic_005fnotEmpty_005f30.doEndTag() == 5) {
/* 5302 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f30); return;
/*      */                   }
/*      */                   
/* 5305 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f30);
/* 5306 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n\n\n<ul id=\"sub_15\" class=\"none\">\n\t\t<li id=\"top_30\" style=\"height:");
/* 5307 */                   out.print(height2);
/* 5308 */                   out.write("px\">\n\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n        <tr>\n\t\t\t<td >\n\n");
/*      */                   
/* 5310 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f33 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5311 */                   _jspx_th_logic_005fnotEmpty_005f33.setPageContext(_jspx_page_context);
/* 5312 */                   _jspx_th_logic_005fnotEmpty_005f33.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 5314 */                   _jspx_th_logic_005fnotEmpty_005f33.setName("CAMData");
/* 5315 */                   int _jspx_eval_logic_005fnotEmpty_005f33 = _jspx_th_logic_005fnotEmpty_005f33.doStartTag();
/* 5316 */                   if (_jspx_eval_logic_005fnotEmpty_005f33 != 0) {
/*      */                     for (;;) {
/* 5318 */                       out.write("\n  <tr>\n    <td width=\"25%\" height=\"22\" class=\"new-report-heading\"> &nbsp; ");
/* 5319 */                       out.print(FormatUtil.getString("am.reporttab.attributes.text"));
/* 5320 */                       out.write("</td>\n    <td width=\"35%\" height=\"22\" class=\"new-report-heading\"> ");
/* 5321 */                       out.print(FormatUtil.getString("am.reporttab.agentname.text"));
/* 5322 */                       out.write("</td>\n    <td width=\"35%\" height=\"22\" class=\"new-report-heading\"> ");
/* 5323 */                       out.print(FormatUtil.getString("am.reporttab.mbeanname.text"));
/* 5324 */                       out.write("</td>\n  </tr>\n  ");
/*      */                       
/* 5326 */                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.get(ForEachTag.class);
/* 5327 */                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 5328 */                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f33);
/*      */                       
/* 5330 */                       _jspx_th_c_005fforEach_005f1.setVar("row");
/*      */                       
/* 5332 */                       _jspx_th_c_005fforEach_005f1.setItems("${CAMData}");
/*      */                       
/* 5334 */                       _jspx_th_c_005fforEach_005f1.setBegin("0");
/*      */                       
/* 5336 */                       _jspx_th_c_005fforEach_005f1.setEnd("2");
/*      */                       
/* 5338 */                       _jspx_th_c_005fforEach_005f1.setVarStatus("i");
/* 5339 */                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                       try {
/* 5341 */                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 5342 */                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                           for (;;) {
/* 5344 */                             out.write(10);
/* 5345 */                             out.write(9);
/*      */                             
/* 5347 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5348 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5349 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 5351 */                             _jspx_th_c_005fif_005f2.setTest("${i.index !='2'}");
/* 5352 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5353 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 5355 */                                 out.write(10);
/* 5356 */                                 out.write(9);
/* 5357 */                                 out.write(32);
/* 5358 */                                 if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 5360 */                                 out.write(10);
/* 5361 */                                 out.write(9);
/* 5362 */                                 out.write(32);
/* 5363 */                                 if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 5365 */                                 out.write(10);
/* 5366 */                                 out.write(9);
/* 5367 */                                 out.write(32);
/* 5368 */                                 String agentname = (String)pageContext.getAttribute("aname");
/* 5369 */                                 String mbeanname = (String)pageContext.getAttribute("mbname");
/*      */                                 
/* 5371 */                                 out.write("\n\n\t\t<tr>\n\t\t    <td height=\"18\" class=\"bodytext\" title=\"");
/* 5372 */                                 if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 5374 */                                 out.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\">&nbsp; <a class=\"new-report-link\" href=\"javascript:submitCustomAttribute('");
/* 5375 */                                 if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 5377 */                                 out.write(39);
/* 5378 */                                 out.write(44);
/* 5379 */                                 out.write(39);
/* 5380 */                                 if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 5382 */                                 out.write("','generateCustomAttributeReport','");
/* 5383 */                                 if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 5385 */                                 out.write("')\">");
/* 5386 */                                 if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 5388 */                                 out.write("</a></td>\n\t\t    <td height=\"18\" class=\"bodytext\">");
/* 5389 */                                 out.print(getTrimmedText(agentname, 35));
/* 5390 */                                 out.write("</td>\n\t\t    <td height=\"18\" class=\"bodytext\">");
/* 5391 */                                 out.print(getTrimmedText(mbeanname, 35));
/* 5392 */                                 out.write("</td>\n\t\t</tr>\n\t");
/* 5393 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5394 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5398 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5399 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 5402 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5403 */                             out.write("\n        ");
/*      */                             
/* 5405 */                             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5406 */                             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5407 */                             _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 5409 */                             _jspx_th_c_005fif_005f3.setTest("${i.index=='2'}");
/* 5410 */                             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5411 */                             if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                               for (;;) {
/* 5413 */                                 out.write("\n\t         <tr>\n        \t    <td height=\"18\" class=\"bodytext\" colspan=\"3\">&nbsp; <a class=\"new-report-link\" href=\"javascript:getCAMMore()\"> ");
/* 5414 */                                 out.print(FormatUtil.getString("am.reporttab.more.text"));
/* 5415 */                                 out.write("</a></td>\n\t         </tr>\n\t");
/* 5416 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5417 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5421 */                             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5422 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 5425 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5426 */                             out.write("\n   ");
/* 5427 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 5428 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 5432 */                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5440 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 5436 */                           int tmp22120_22119 = 0; int[] tmp22120_22117 = _jspx_push_body_count_c_005fforEach_005f1; int tmp22122_22121 = tmp22120_22117[tmp22120_22119];tmp22120_22117[tmp22120_22119] = (tmp22122_22121 - 1); if (tmp22122_22121 <= 0) break;
/* 5437 */                           out = _jspx_page_context.popBody(); }
/* 5438 */                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                       } finally {
/* 5440 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 5441 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                       }
/* 5443 */                       out.write("\n   <tr>\n\t");
/*      */                       
/* 5445 */                       PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5446 */                       _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 5447 */                       _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_logic_005fnotEmpty_005f33);
/*      */                       
/* 5449 */                       _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 5450 */                       int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 5451 */                       if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                         for (;;) {
/* 5453 */                           out.write("\n       <td colspan=\"3\" height=\"20\" class=\"tablebottom\" align=\"right\"><a href=\"/jsp/cam_globalreportsconfig.jsp\" class=\"links\">");
/* 5454 */                           out.print(FormatUtil.getString("am.reporttab.enabledisplay.text"));
/* 5455 */                           out.write("</a>&nbsp;</td>\n\t");
/* 5456 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 5457 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5461 */                       if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 5462 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                       }
/*      */                       
/* 5465 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 5466 */                       out.write("\n  </tr>\n");
/* 5467 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f33.doAfterBody();
/* 5468 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5472 */                   if (_jspx_th_logic_005fnotEmpty_005f33.doEndTag() == 5) {
/* 5473 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f33); return;
/*      */                   }
/*      */                   
/* 5476 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f33);
/* 5477 */                   out.write(10);
/* 5478 */                   out.write(10);
/* 5479 */                   out.write(10);
/*      */                   
/* 5481 */                   EmptyTag _jspx_th_logic_005fempty_005f5 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 5482 */                   _jspx_th_logic_005fempty_005f5.setPageContext(_jspx_page_context);
/* 5483 */                   _jspx_th_logic_005fempty_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 5485 */                   _jspx_th_logic_005fempty_005f5.setName("CAMData");
/* 5486 */                   int _jspx_eval_logic_005fempty_005f5 = _jspx_th_logic_005fempty_005f5.doStartTag();
/* 5487 */                   if (_jspx_eval_logic_005fempty_005f5 != 0) {
/*      */                     for (;;) {
/* 5489 */                       out.write("\n  <table width=\"100%\"  >\n  <tr>\n    ");
/*      */                       
/* 5491 */                       String messageToAppend = "";
/* 5492 */                       if (EnterpriseUtil.isAdminServer())
/*      */                       {
/* 5494 */                         messageToAppend = FormatUtil.getString("am.reporttab.cammessage.text.admin");
/*      */                       }
/*      */                       else
/*      */                       {
/* 5498 */                         messageToAppend = FormatUtil.getString("am.reporttab.cammessage.text");
/*      */                       }
/*      */                       
/* 5501 */                       List attributeConfigList = com.adventnet.appmanager.cam.CAMDBUtil.getAttributeDetailsForReportAdminActivity();
/* 5502 */                       if (attributeConfigList.size() > 0)
/*      */                       {
/* 5504 */                         if (EnterpriseUtil.isAdminServer())
/*      */                         {
/* 5506 */                           messageToAppend = FormatUtil.getString("am.reporttab.cammessage1.text.admin");
/*      */                         }
/*      */                         else
/*      */                         {
/* 5510 */                           messageToAppend = FormatUtil.getString("am.reporttab.cammessage1.text");
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/* 5515 */                       out.write("\n    <td height=\"18\" class=\"columnheading\" colspan=\"3\"><br>&nbsp;");
/* 5516 */                       out.print(messageToAppend);
/* 5517 */                       out.write("<br><br></td>\n\n  </tr>\n</table>\n");
/* 5518 */                       int evalDoAfterBody = _jspx_th_logic_005fempty_005f5.doAfterBody();
/* 5519 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5523 */                   if (_jspx_th_logic_005fempty_005f5.doEndTag() == 5) {
/* 5524 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5); return;
/*      */                   }
/*      */                   
/* 5527 */                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5);
/* 5528 */                   out.write("\n\n\t\t\t\t</td>\n\t\t</tr>\n\t</table>\n\n\n\n\t</li>\n\t</ul>\n\n\n\n\n\t<ul id=\"sub_35\" class=\"none\">\n\t\t<li id=\"top_23\" style=\"height:");
/* 5529 */                   out.print(height2);
/* 5530 */                   out.write("px\">\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t\t");
/*      */                   
/* 5532 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f34 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5533 */                   _jspx_th_logic_005fnotEmpty_005f34.setPageContext(_jspx_page_context);
/* 5534 */                   _jspx_th_logic_005fnotEmpty_005f34.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 5536 */                   _jspx_th_logic_005fnotEmpty_005f34.setName("ReportForm");
/*      */                   
/* 5538 */                   _jspx_th_logic_005fnotEmpty_005f34.setProperty("virtualservers");
/* 5539 */                   int _jspx_eval_logic_005fnotEmpty_005f34 = _jspx_th_logic_005fnotEmpty_005f34.doStartTag();
/* 5540 */                   if (_jspx_eval_logic_005fnotEmpty_005f34 != 0) {
/*      */                     for (;;) {
/* 5542 */                       out.write(10);
/* 5543 */                       out.write(9);
/* 5544 */                       out.write(9);
/*      */                       
/* 5546 */                       Properties allVirtualserversTypes = (Properties)frm.getVirtualservers().get(0);
/* 5547 */                       String allVirtualserver = (String)allVirtualserversTypes.get("value");
/*      */                       
/* 5549 */                       out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\"><tr>\n\t\t<td  class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\"  > &nbsp;&nbsp;&nbsp; ");
/* 5550 */                       out.print(FormatUtil.getString("am.webclient.reports.select.virtual.text"));
/* 5551 */                       out.write(" &nbsp;\n\t\t\t");
/*      */                       
/* 5553 */                       SelectTag _jspx_th_html_005fselect_005f25 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 5554 */                       _jspx_th_html_005fselect_005f25.setPageContext(_jspx_page_context);
/* 5555 */                       _jspx_th_html_005fselect_005f25.setParent(_jspx_th_logic_005fnotEmpty_005f34);
/*      */                       
/* 5557 */                       _jspx_th_html_005fselect_005f25.setProperty("virserver");
/*      */                       
/* 5559 */                       _jspx_th_html_005fselect_005f25.setAlt(allVirtualserver);
/*      */                       
/* 5561 */                       _jspx_th_html_005fselect_005f25.setOnchange("loadURLType(this.options[this.selectedIndex].value,this,'virtualserverFilterby')");
/*      */                       
/* 5563 */                       _jspx_th_html_005fselect_005f25.setStyleClass("formtext");
/* 5564 */                       int _jspx_eval_html_005fselect_005f25 = _jspx_th_html_005fselect_005f25.doStartTag();
/* 5565 */                       if (_jspx_eval_html_005fselect_005f25 != 0) {
/* 5566 */                         if (_jspx_eval_html_005fselect_005f25 != 1) {
/* 5567 */                           out = _jspx_page_context.pushBody();
/* 5568 */                           _jspx_th_html_005fselect_005f25.setBodyContent((BodyContent)out);
/* 5569 */                           _jspx_th_html_005fselect_005f25.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 5572 */                           out.write("\n      \t\t");
/* 5573 */                           if (_jspx_meth_html_005foptionsCollection_005f25(_jspx_th_html_005fselect_005f25, _jspx_page_context))
/*      */                             return;
/* 5575 */                           out.write("\n      \t\t");
/*      */                           
/* 5577 */                           ArrayList virtualserverfields = MyFields.parseCustomFieldInfo(custInfo, allVirtualserver, isPrivilegedUser);
/* 5578 */                           if (virtualserverfields.size() > 0)
/*      */                           {
/* 5580 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 5581 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 5582 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 5584 */                           for (int virtualservercount = 0; virtualservercount < virtualserverfields.size(); virtualservercount++)
/*      */                           {
/* 5586 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 5588 */                             OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 5589 */                             _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 5590 */                             _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f25);
/*      */                             
/* 5592 */                             _jspx_th_html_005foption_005f11.setStyle("background-color: #FFF8C6");
/*      */                             
/* 5594 */                             _jspx_th_html_005foption_005f11.setValue(((Properties)virtualserverfields.get(virtualservercount)).getProperty("labelalias") + "$" + ((Properties)virtualserverfields.get(virtualservercount)).getProperty("fieldid"));
/* 5595 */                             int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 5596 */                             if (_jspx_eval_html_005foption_005f11 != 0) {
/* 5597 */                               if (_jspx_eval_html_005foption_005f11 != 1) {
/* 5598 */                                 out = _jspx_page_context.pushBody();
/* 5599 */                                 _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 5600 */                                 _jspx_th_html_005foption_005f11.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 5603 */                                 out.print(((Properties)virtualserverfields.get(virtualservercount)).getProperty("label"));
/* 5604 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 5605 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 5608 */                               if (_jspx_eval_html_005foption_005f11 != 1) {
/* 5609 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 5612 */                             if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 5613 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                             }
/*      */                             
/* 5616 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f11);
/* 5617 */                             out.write("\n                          ");
/*      */                           }
/* 5619 */                           out.write("\n      \t\t");
/* 5620 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f25.doAfterBody();
/* 5621 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 5624 */                         if (_jspx_eval_html_005fselect_005f25 != 1) {
/* 5625 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 5628 */                       if (_jspx_th_html_005fselect_005f25.doEndTag() == 5) {
/* 5629 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f25); return;
/*      */                       }
/*      */                       
/* 5632 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f25);
/* 5633 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"virtualserverFilterby\"></span>\n\t\t</td>\n\t\t</tr></table></td>\n\t\t</tr>\n\t\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 5634 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("virtualserver", request.getCharacterEncoding()), out, false);
/* 5635 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 5636 */                       out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 5637 */                       out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('responseTime','generateAttributeReport',document.forms[1].virserver)\">");
/* 5638 */                       out.print(FormatUtil.getString("am.reporttab.resofvirtual.text"));
/* 5639 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t");
/*      */                       
/* 5641 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f35 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5642 */                       _jspx_th_logic_005fnotEmpty_005f35.setPageContext(_jspx_page_context);
/* 5643 */                       _jspx_th_logic_005fnotEmpty_005f35.setParent(_jspx_th_logic_005fnotEmpty_005f34);
/*      */                       
/* 5645 */                       _jspx_th_logic_005fnotEmpty_005f35.setName("ReportForm");
/*      */                       
/* 5647 */                       _jspx_th_logic_005fnotEmpty_005f35.setProperty("virserverArrayAttribute");
/* 5648 */                       int _jspx_eval_logic_005fnotEmpty_005f35 = _jspx_th_logic_005fnotEmpty_005f35.doStartTag();
/* 5649 */                       if (_jspx_eval_logic_005fnotEmpty_005f35 != 0) {
/*      */                         for (;;) {
/* 5651 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 5652 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 5653 */                           out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\n\t\t\t\t\t");
/* 5654 */                           if (_jspx_meth_html_005fselect_005f26(_jspx_th_logic_005fnotEmpty_005f35, _jspx_page_context))
/*      */                             return;
/* 5656 */                           out.write("\n\n\t\t\t\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t ");
/* 5657 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f35.doAfterBody();
/* 5658 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5662 */                       if (_jspx_th_logic_005fnotEmpty_005f35.doEndTag() == 5) {
/* 5663 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f35); return;
/*      */                       }
/*      */                       
/* 5666 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f35);
/* 5667 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\n\t\t  ");
/* 5668 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f34.doAfterBody();
/* 5669 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5673 */                   if (_jspx_th_logic_005fnotEmpty_005f34.doEndTag() == 5) {
/* 5674 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f34); return;
/*      */                   }
/*      */                   
/* 5677 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f34);
/* 5678 */                   out.write("\n\t\t</table>\n\n\n\t</li>\n\t</ul>\n\n<ul id=\"sub_38\" class=\"none\">\n\t\t<li id=\"top_39\" style=\"height:");
/* 5679 */                   out.print(height2);
/* 5680 */                   out.write("px\">\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n\t\t\t");
/*      */                   
/* 5682 */                   int listsize1 = list1.size();
/*      */                   
/* 5684 */                   int divlength1 = 80;
/*      */                   
/* 5686 */                   if ((listsize1 > 10) && (listsize1 < 20))
/*      */                   {
/* 5688 */                     divlength1 = 130;
/*      */                   }
/* 5690 */                   else if (listsize1 > 25)
/*      */                   {
/* 5692 */                     divlength1 = 200;
/*      */                   }
/*      */                   
/*      */ 
/* 5696 */                   out.write(10);
/* 5697 */                   out.write(9);
/* 5698 */                   out.write(9);
/*      */                   
/* 5700 */                   String capacityPlanningServerTypes = "";
/* 5701 */                   ArrayList capacityServersOptions = frm.getCapacityServers();
/* 5702 */                   for (int i = 1; i < capacityServersOptions.size(); i++) {
/* 5703 */                     Properties options = (Properties)capacityServersOptions.get(i);
/* 5704 */                     capacityPlanningServerTypes = capacityPlanningServerTypes + options.get("value") + "','";
/*      */                   }
/* 5706 */                   if (capacityPlanningServerTypes.length() > 0) {
/* 5707 */                     capacityPlanningServerTypes = capacityPlanningServerTypes.substring(0, capacityPlanningServerTypes.length() - 3);
/*      */                   }
/*      */                   
/* 5710 */                   out.write("\n\t\t\t\t\t\t\t\t\t\t");
/*      */                   
/* 5712 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f36 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5713 */                   _jspx_th_logic_005fnotEmpty_005f36.setPageContext(_jspx_page_context);
/* 5714 */                   _jspx_th_logic_005fnotEmpty_005f36.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 5716 */                   _jspx_th_logic_005fnotEmpty_005f36.setName("ReportForm");
/*      */                   
/* 5718 */                   _jspx_th_logic_005fnotEmpty_005f36.setProperty("capacityServers");
/* 5719 */                   int _jspx_eval_logic_005fnotEmpty_005f36 = _jspx_th_logic_005fnotEmpty_005f36.doStartTag();
/* 5720 */                   if (_jspx_eval_logic_005fnotEmpty_005f36 != 0) {
/*      */                     for (;;) {
/* 5722 */                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 5723 */                       if (!isListEmpty) {
/* 5724 */                         out.write("\n                                                                                    \n\t\t\t\t\t\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" > &nbsp; ");
/* 5725 */                         out.print(FormatUtil.getString("am.capacityplanning.select.servers"));
/* 5726 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; ");
/* 5727 */                         if (_jspx_meth_html_005fselect_005f27(_jspx_th_logic_005fnotEmpty_005f36, _jspx_page_context))
/*      */                           return;
/* 5729 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp;\n                                                                                                        \n                                                                                                        \n                                                                                                        \n                                                                                                         \n                                                                                                      \n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5730 */                         out.print(FormatUtil.getString("am.webclient.reports.filter.mg.text"));
/* 5731 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; ");
/* 5732 */                         if (_jspx_meth_html_005fhidden_005f23(_jspx_th_logic_005fnotEmpty_005f36, _jspx_page_context))
/*      */                           return;
/* 5734 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" id=\"capacityPlanningServerTypes\" value=\"");
/* 5735 */                         out.print(capacityPlanningServerTypes);
/* 5736 */                         out.write("\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<!-- input  type=\"text\" style=\"height:25px;\" class=\"formtext input-down-arrow\" size=\"24\" name=\"monday\" id=\"monday\" onMousedown=\"DisplayServiceList1('service_list_left1','leftimage1')\"  value='");
/* 5737 */                         out.print(((Properties)list1.get(list1.size() - 1)).getProperty("label"));
/* 5738 */                         out.write("' readonly-->\n\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" class=\"formtext input-down-arrow normal\" size=\"30\" name=\"mondaycapacity\" id=\"mondaycapacity\"\n\t\t\t\t\t\t\t\t\t\t\t\t\tonMousedown=\"DisplayServiceList1('service_list_left2','leftimage2')\"\n\t\t\t\t\t\t\t\t\t\t\t\t\tvalue='");
/* 5739 */                         out.print(FormatUtil.getString("am.vmreports.capacityplanning.nogroup"));
/* 5740 */                         out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t <img src=\"../../images/icon_downarrow.gif\" class=\"drop-downimg\" name=\"leftimage2\" align=\"absmiddle\"\tonclick=\"DisplayServiceList1('service_list_left2','leftimage2')\" style=\"display: none\">\n                                                                                                                     \n                                                                                                                     <span id=\"customFieldValuesFilterbyCapacity\" style=\"display:none; \">    </span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"dummyid1\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"width: 0px; height: 0px; overflow: none;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"leftmonitordisplayframe2\" style=\"display: none\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n                                                                                                                                                \n                                                                                                                                                \n");
/* 5741 */                         out.write("                         \n                                                                                                                                                \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"dummyid\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"width: 0px; height: 0px; overflow: none;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"service_list_left2\" class=\"formtext normal\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"overflow:auto; width: 200px; left: 428px; left: 433\\9; height:");
/* 5742 */                         out.print(divlength1);
/* 5743 */                         out.write("px; display:none; position:absolute; background:#FFFFFF; margin-top: -1px;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tcellpadding=\"0\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td id=\"nomgs_list2\" class=\"bodytext dropDownText\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tonmouseover='SetSelected1(this)'\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tonmouseout=\"changeStyle(this);\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tonclick=\"SelectMonitorGroup('service_list_left2','");
/* 5744 */                         out.print(FormatUtil.getString("am.vmreports.capacityplanning.nogroup"));
/* 5745 */                         out.write("','nogroup','leftimage2')\">");
/* 5746 */                         out.print(FormatUtil.getString("am.vmreports.capacityplanning.nogroup"));
/* 5747 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5748 */                         for (int i = 0; i < list2.size(); i++) {
/* 5749 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tid=\"");
/* 5750 */                           out.print(((Properties)list2.get(i)).getProperty("value"));
/* 5751 */                           out.write("_list2\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tclass=\"bodytext dropDownText\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tonmouseover='SetSelected1(this)'\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tonmouseout=\"changeStyle(this);\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tonclick=\"SelectMonitorGroup('service_list_left2','");
/* 5752 */                           out.print(((Properties)list2.get(i)).getProperty("label"));
/* 5753 */                           out.write(39);
/* 5754 */                           out.write(44);
/* 5755 */                           out.write(39);
/* 5756 */                           out.print(((Properties)list2.get(i)).getProperty("value"));
/* 5757 */                           out.write("','leftimage2')\">");
/* 5758 */                           out.print(((Properties)list2.get(i)).getProperty("label"));
/* 5759 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                         }
/* 5761 */                         out.write("\n                                                                                                                                                  ");
/*      */                         
/* 5763 */                         ArrayList mgFields = MyFields.parseCustomFieldInfo(custInfo, capacityPlanningServerTypes, isPrivilegedUser);
/* 5764 */                         if (mgFields.size() > 0)
/*      */                         {
/* 5766 */                           out.write("\n                                                                                                                                        <tr>\n                                                                                                                                      <td id=\"customfield\" class=\"bodytext dropDownText\" style=\"background-color: #FFF8C6;font-weight: bold;cursor: text;\" >");
/* 5767 */                           out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 5768 */                           out.write("</td>\n                                                                                                                                        </tr>\n                                                                                                                                        ");
/*      */                         }
/*      */                         
/* 5771 */                         for (int count = 0; count < mgFields.size(); count++)
/*      */                         {
/* 5773 */                           out.write("\n                                                                                                                                        <tr><td id=\"");
/* 5774 */                           out.print(((Properties)mgFields.get(count)).getProperty("labelalias") + "$" + ((Properties)mgFields.get(count)).getProperty("fieldid") + "_list");
/* 5775 */                           out.write("\" class=\"bodytext dropDownText\" style=\"background-color: #FFF8C6\"  onmouseover='SetSelected(this,true)' onMouseOut=\"changeStyle(this,true);\" onClick=\"SelectMonitorGroup('service_list_left2','");
/* 5776 */                           out.print(((Properties)mgFields.get(count)).getProperty("label"));
/* 5777 */                           out.write(39);
/* 5778 */                           out.write(44);
/* 5779 */                           out.write(39);
/* 5780 */                           out.print(((Properties)mgFields.get(count)).getProperty("labelalias") + "$" + ((Properties)mgFields.get(count)).getProperty("fieldid"));
/* 5781 */                           out.write("','leftimage1')\">\n                                                                                                                                        ");
/* 5782 */                           out.print(((Properties)mgFields.get(count)).getProperty("label"));
/* 5783 */                           out.write("\n                                                                                                                                        </td>\n                                                                                                                                        </tr>\n                                                                                                                                        ");
/*      */                         }
/* 5785 */                         out.write("\n                                                                                                                                            </table>\n                                                                                                                                                </div>\n                                                                                                                                                   </div>\n\t\t\t\t\t\t\t\t\t\t\t\t \n                                                                                     \n                                                                                                                 </td>\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t");
/* 5786 */                         if (EnterpriseUtil.isIt360MSPEdition()) {
/* 5787 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t<script>SelectMonitorGroup('service_list_left2','");
/* 5788 */                           out.print(((Properties)list1.get(0)).getProperty("label"));
/* 5789 */                           out.write(39);
/* 5790 */                           out.write(44);
/* 5791 */                           out.write(39);
/* 5792 */                           out.print(((Properties)list1.get(0)).getProperty("value"));
/* 5793 */                           out.write("','leftimage2');</script>\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                         }
/* 5795 */                         out.write("\n\n\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"10\"  colspan=\"5\" align=\"center\" class=\"report-heading-tile\">\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\n\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 15px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"100%\" colspan=\"5\">\n                                                                                                    ");
/*      */                         
/*      */ 
/* 5798 */                         if (rGroupCountHash.get("SYS") != null)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/* 5803 */                           out.write("\n                                                                                                     <table id=\"allserver\" cellspacing=\"0\" cellpadding=\"0\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\twidth=\"100%\" style=\"border-bottom: 1px solid #F1F1F1;\">\n                                                                                       \t\t\t\t\t<tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 5804 */                           out.print(FormatUtil.getString("am.capacityplanning.servers"));
/* 5805 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n                                                                                                                <tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>&nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span><a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('1,2','generateUnderSizedMonitors',document.forms[1].capacityserver,'underSizedMonitorsServers','generateUnderSizedMonitors',document.forms[1].mgCapacity.value,'true',document.forms[1].mondaycapacity.value)\">");
/* 5806 */                           out.print(FormatUtil.getString("am.reporttab.undersized.servers"));
/* 5807 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('1,2','generateUnderSizedMonitors',document.forms[1].capacityserver,'oversizedMonitorsServers','generateOverSizedMonitors',document.forms[1].mgCapacity.value,'true',document.forms[1].mondaycapacity.value)\">");
/* 5808 */                           out.print(FormatUtil.getString("am.reporttab.oversized.servers"));
/* 5809 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('1,2','generateUnderSizedMonitors',document.forms[1].capacityserver,'idleMonitorsServers','generateIdleMonitors',document.forms[1].mgCapacity.value,'true',document.forms[1].mondaycapacity.value)\">");
/* 5810 */                           out.print(FormatUtil.getString("am.reporttab.idlesized.servers"));
/* 5811 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp;</td>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n                                                                                                                </table>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                         }
/*      */                         
/* 5814 */                         if ((ReportForm.moTypeCount != null) && (ReportForm.moTypeCount.get("VMWare ESX/ESXi") != null))
/*      */                         {
/*      */ 
/* 5817 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table id=\"esxserver\" cellspacing=\"0\" cellpadding=\"0\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\twidth=\"100%\" style=\"border-bottom: 1px solid #F1F1F1;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 20px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 5818 */                           out.print(FormatUtil.getString("am.reporttab.vmware.server"));
/* 5819 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>&nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span><a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('7520,7522,7523,7524','generateUnderSizedMonitors',document.forms[1].virserver,'underSizedMonitorsVMware','generateUnderSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5820 */                           out.print(FormatUtil.getString("am.reporttab.undersized.servers"));
/* 5821 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('7520,7522,7523,7524','generateUnderSizedMonitors',document.forms[1].virserver,'oversizedMonitorsVMware','generateOverSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5822 */                           out.print(FormatUtil.getString("am.reporttab.oversized.servers"));
/* 5823 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('7520,7522,7523,7524','generateUnderSizedMonitors',document.forms[1].virserver,'idleMonitorsVMware','generateIdleMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5824 */                           out.print(FormatUtil.getString("am.reporttab.idlesized.servers"));
/* 5825 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp;</td>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 10px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table> ");
/*      */                         }
/*      */                         
/* 5828 */                         if ((ReportForm.moTypeCount != null) && (ReportForm.moTypeCount.get("Hyper-V-Server") != null))
/*      */                         {
/*      */ 
/*      */ 
/* 5832 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table id=\"hypervserver\" cellspacing=\"0\" cellpadding=\"0\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\twidth=\"100%\" style=\"border-bottom: 1px solid #F1F1F1;\">\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 20px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 5833 */                           out.print(FormatUtil.getString("am.reporttab.hyperv.server"));
/* 5834 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>&nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span><a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('4112,4118','generateUnderSizedMonitors',document.forms[1].virserver,'underSizedMonitorsHyperVServer','generateUnderSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5835 */                           out.print(FormatUtil.getString("am.reporttab.undersized.servers"));
/* 5836 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('4112,4118','generateUnderSizedMonitors',document.forms[1].virserver,'oversizedMonitorsHyperVServer','generateOverSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5837 */                           out.print(FormatUtil.getString("am.reporttab.oversized.servers"));
/* 5838 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('4112,4118','generateUnderSizedMonitors',document.forms[1].virserver,'idleMonitorsHyperVServer','generateIdleMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5839 */                           out.print(FormatUtil.getString("am.reporttab.idlesized.servers"));
/* 5840 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 10px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table> ");
/*      */                         }
/* 5842 */                         if ((ReportForm.moTypeCount != null) && (ReportForm.moTypeCount.get("XenServerHost") != null))
/*      */                         {
/*      */ 
/*      */ 
/* 5846 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table id=\"xenserver\" cellspacing=\"0\" cellpadding=\"0\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\twidth=\"100%\" style=\"border-bottom: 1px solid #F1F1F1;\">\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 20px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 5847 */                           out.print(FormatUtil.getString("am.reporttab.xenserver.server"));
/* 5848 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>&nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span><a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('15005,15010','generateUnderSizedMonitors',document.forms[1].virserver,'underSizedMonitorsXenServerHost','generateUnderSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5849 */                           out.print(FormatUtil.getString("am.reporttab.undersized.servers"));
/* 5850 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('15005,15010','generateUnderSizedMonitors',document.forms[1].virserver,'oversizedMonitorsXenServerHost','generateOverSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5851 */                           out.print(FormatUtil.getString("am.reporttab.oversized.servers"));
/* 5852 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('15005,15010','generateUnderSizedMonitors',document.forms[1].virserver,'idleMonitorsXenServerHost','generateIdleMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5853 */                           out.print(FormatUtil.getString("am.reporttab.idlesized.servers"));
/* 5854 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 10px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t");
/*      */                         }
/*      */                         
/* 5857 */                         if (((ReportForm.moTypeCount != null) && (ReportForm.moTypeCount.get("VMWare ESX/ESXi") != null) && (ReportForm.moTypeCount.get("VirtualMachine") != null)) || ((request.isUserInRole("OPERATOR")) && (ReportForm.moTypeCount.get("VirtualMachine") != null)))
/*      */                         {
/*      */ 
/* 5860 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table id=\"vmsesx\" cellspacing=\"0\" cellpadding=\"0\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\twidth=\"100%\" style=\"border-bottom: 1px solid #F1F1F1;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 20px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"new-report-heading\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5861 */                           out.print(FormatUtil.getString("am.reporttab.vmware.virtualmachines"));
/* 5862 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>&nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span><a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('7624,7626,7627,7628','generateUnderSizedMonitors',document.forms[1].virserver,'underSizedMonitorsVMSESX','generateUnderSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5863 */                           out.print(FormatUtil.getString("am.reporttab.undersized.virtualmachines"));
/* 5864 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('7624,7626,7627,7628','generateUnderSizedMonitors',document.forms[1].virserver,'oversizedMonitorsVMSESX','generateOverSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5865 */                           out.print(FormatUtil.getString("am.reporttab.oversized.virtualmachines"));
/* 5866 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('7624,7626,7627,7628','generateUnderSizedMonitors',document.forms[1].virserver,'idleMonitorsVMSESX','generateIdleMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5867 */                           out.print(FormatUtil.getString("am.reporttab.idlesized.virtualmachines"));
/* 5868 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 10px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table> ");
/*      */                         }
/* 5870 */                         if (((ReportForm.moTypeCount != null) && (ReportForm.moTypeCount.get("Hyper-V-Server") != null) && (ReportForm.moTypeCount.get("HyperVVirtualMachine") != null)) || ((request.isUserInRole("OPERATOR")) && (ReportForm.moTypeCount.get("HyperVVirtualMachine") != null)))
/*      */                         {
/* 5872 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table id=\"vmshyperv\" cellspacing=\"0\" cellpadding=\"0\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\twidth=\"100%\" style=\"border-bottom: 1px solid #F1F1F1;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 20px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"new-report-heading\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5873 */                           out.print(FormatUtil.getString("am.reporttab.hyperv.virtualmachines"));
/* 5874 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>&nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span><a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('4218,4220','generateUnderSizedMonitors',document.forms[1].virserver,'underSizedMonitorsVMSHyperVServer10','generateUnderSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5875 */                           out.print(FormatUtil.getString("am.reporttab.undersized.virtualmachines"));
/* 5876 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('4218,4220','generateUnderSizedMonitors',document.forms[1].virserver,'oversizedMonitorsVMSHyperVServers13','generateOverSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5877 */                           out.print(FormatUtil.getString("am.reporttab.oversized.virtualmachines"));
/* 5878 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('4218,4220','generateUnderSizedMonitors',document.forms[1].virserver,'idleMonitorsVMSHyperVServers12','generateIdleMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5879 */                           out.print(FormatUtil.getString("am.reporttab.idlesized.virtualmachines"));
/* 5880 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 10px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 20px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                         }
/*      */                         
/* 5883 */                         if (((ReportForm.moTypeCount != null) && (ReportForm.moTypeCount.get("XenServerHost") != null) && (ReportForm.moTypeCount.get("XenServerVM") != null)) || ((request.isUserInRole("OPERATOR")) && (ReportForm.moTypeCount.get("XenServerVM") != null)))
/*      */                         {
/*      */ 
/*      */ 
/* 5887 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table id=\"xenservervm\" cellspacing=\"0\" cellpadding=\"0\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\twidth=\"100%\" style=\"border-bottom: 1px solid #F1F1F1;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 20px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"new-report-heading\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5888 */                           out.print(FormatUtil.getString("am.reporttab.xenserver.virtualmachines"));
/* 5889 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>&nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span><a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('15509,15513','generateUnderSizedMonitors',document.forms[1].virserver,'underSizedMonitorsXenServerVM10','generateUnderSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5890 */                           out.print(FormatUtil.getString("am.reporttab.undersized.virtualmachines"));
/* 5891 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('15509,15513','generateUnderSizedMonitors',document.forms[1].virserver,'oversizedMonitorsXenServerVM13','generateOverSizedMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5892 */                           out.print(FormatUtil.getString("am.reporttab.oversized.virtualmachines"));
/* 5893 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp; &nbsp; <span\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"color: #595959; font-size: 11px; font-weight: normal;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&bull; </span> <a class=\"new-report-link\"\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm4('15509,15513','generateUnderSizedMonitors',document.forms[1].virserver,'idleMonitorsXenServerVM12','generateIdleMonitors',document.forms[1].mgCapacity.value,'false',document.forms[1].mondaycapacity.value)\">");
/* 5894 */                           out.print(FormatUtil.getString("am.reporttab.idlesized.virtualmachines"));
/* 5895 */                           out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp; &nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 10px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"height: 20px;\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                         }
/*      */                         
/*      */ 
/* 5899 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                       }
/* 5901 */                       out.write("\n\t\t\t\t\t\t\t\t\t</table> ");
/* 5902 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f36.doAfterBody();
/* 5903 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 5907 */                   if (_jspx_th_logic_005fnotEmpty_005f36.doEndTag() == 5) {
/* 5908 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f36); return;
/*      */                   }
/*      */                   
/* 5911 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f36);
/* 5912 */                   out.write("\n\n                                          </li>\n        </ul>\n\n\t\t<ul id=\"sub_36\" class=\"none\">\n\t\t<li id=\"top_23\" style=\"height:");
/* 5913 */                   out.print(height2);
/* 5914 */                   out.write("px\">\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t");
/*      */                   
/* 5916 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f37 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5917 */                   _jspx_th_logic_005fnotEmpty_005f37.setPageContext(_jspx_page_context);
/* 5918 */                   _jspx_th_logic_005fnotEmpty_005f37.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 5920 */                   _jspx_th_logic_005fnotEmpty_005f37.setName("ReportForm");
/*      */                   
/* 5922 */                   _jspx_th_logic_005fnotEmpty_005f37.setProperty("cloudApps");
/* 5923 */                   int _jspx_eval_logic_005fnotEmpty_005f37 = _jspx_th_logic_005fnotEmpty_005f37.doStartTag();
/* 5924 */                   if (_jspx_eval_logic_005fnotEmpty_005f37 != 0) {
/*      */                     for (;;) {
/* 5926 */                       out.write(10);
/* 5927 */                       out.write(9);
/* 5928 */                       out.write(9);
/*      */                       
/* 5930 */                       Properties allCloudAppsTypes = (Properties)frm.getCloudApps().get(0);
/* 5931 */                       String allCloudApp = (String)allCloudAppsTypes.get("value");
/*      */                       
/* 5933 */                       out.write("\n\t\t<tr><td colspan=\"3\"><table width=\"100%\"><tr>\n\t\t<td class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" > &nbsp;&nbsp;&nbsp; ");
/* 5934 */                       out.print(FormatUtil.getString("am.webclient.reports.select.cloudapps.text"));
/* 5935 */                       out.write(" &nbsp;\n\t\t\t");
/*      */                       
/* 5937 */                       SelectTag _jspx_th_html_005fselect_005f28 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.get(SelectTag.class);
/* 5938 */                       _jspx_th_html_005fselect_005f28.setPageContext(_jspx_page_context);
/* 5939 */                       _jspx_th_html_005fselect_005f28.setParent(_jspx_th_logic_005fnotEmpty_005f37);
/*      */                       
/* 5941 */                       _jspx_th_html_005fselect_005f28.setProperty("cldApps");
/*      */                       
/* 5943 */                       _jspx_th_html_005fselect_005f28.setAlt(allCloudApp);
/*      */                       
/* 5945 */                       _jspx_th_html_005fselect_005f28.setOnchange("loadURLType(this.options[this.selectedIndex].value,this,'cloudappsFilterby')");
/*      */                       
/* 5947 */                       _jspx_th_html_005fselect_005f28.setStyleClass("formtext");
/* 5948 */                       int _jspx_eval_html_005fselect_005f28 = _jspx_th_html_005fselect_005f28.doStartTag();
/* 5949 */                       if (_jspx_eval_html_005fselect_005f28 != 0) {
/* 5950 */                         if (_jspx_eval_html_005fselect_005f28 != 1) {
/* 5951 */                           out = _jspx_page_context.pushBody();
/* 5952 */                           _jspx_th_html_005fselect_005f28.setBodyContent((BodyContent)out);
/* 5953 */                           _jspx_th_html_005fselect_005f28.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 5956 */                           out.write("\n      \t\t");
/* 5957 */                           if (_jspx_meth_html_005foptionsCollection_005f28(_jspx_th_html_005fselect_005f28, _jspx_page_context))
/*      */                             return;
/* 5959 */                           out.write("\n      \t\t");
/*      */                           
/* 5961 */                           ArrayList cloudappsfields = MyFields.parseCustomFieldInfo(custInfo, allCloudApp, isPrivilegedUser);
/* 5962 */                           if (cloudappsfields.size() > 0)
/*      */                           {
/* 5964 */                             out.write("\n\t\t\t   \t\t<optgroup  label=\"");
/* 5965 */                             out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 5966 */                             out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t   ");
/*      */                           }
/* 5968 */                           for (int cloudappscount = 0; cloudappscount < cloudappsfields.size(); cloudappscount++)
/*      */                           {
/* 5970 */                             out.write("\n\t\t\t\t\t\t");
/*      */                             
/* 5972 */                             OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 5973 */                             _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 5974 */                             _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f28);
/*      */                             
/* 5976 */                             _jspx_th_html_005foption_005f12.setStyle("background-color: #FFF8C6");
/*      */                             
/* 5978 */                             _jspx_th_html_005foption_005f12.setValue(((Properties)cloudappsfields.get(cloudappscount)).getProperty("labelalias") + "$" + ((Properties)cloudappsfields.get(cloudappscount)).getProperty("fieldid"));
/* 5979 */                             int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 5980 */                             if (_jspx_eval_html_005foption_005f12 != 0) {
/* 5981 */                               if (_jspx_eval_html_005foption_005f12 != 1) {
/* 5982 */                                 out = _jspx_page_context.pushBody();
/* 5983 */                                 _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 5984 */                                 _jspx_th_html_005foption_005f12.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 5987 */                                 out.print(((Properties)cloudappsfields.get(cloudappscount)).getProperty("label"));
/* 5988 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 5989 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 5992 */                               if (_jspx_eval_html_005foption_005f12 != 1) {
/* 5993 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 5996 */                             if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 5997 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                             }
/*      */                             
/* 6000 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f12);
/* 6001 */                             out.write("\n                          ");
/*      */                           }
/* 6003 */                           out.write("\n      \t\t");
/* 6004 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f28.doAfterBody();
/* 6005 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 6008 */                         if (_jspx_eval_html_005fselect_005f28 != 1) {
/* 6009 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 6012 */                       if (_jspx_th_html_005fselect_005f28.doEndTag() == 5) {
/* 6013 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f28); return;
/*      */                       }
/*      */                       
/* 6016 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange_005falt.reuse(_jspx_th_html_005fselect_005f28);
/* 6017 */                       out.write("&nbsp;&nbsp;&nbsp;<span id=\"cloudappsFilterby\"></span>\n\t\t</td>\n\t\t</tr></table></td>\n\t\t</tr>\n\t\t<tr><td colspan=\"3\" height=\"3\"></td></tr>\n\t\t<tr><td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td></tr>\n\t\t<tr>\n\t\t\t");
/* 6018 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("cloudapps", request.getCharacterEncoding()), out, false);
/* 6019 */                       out.write("\n\t\t\t<td width=\"2%\" class=\"report-seperator\" >&nbsp;</td>\n\t\t\t<td width=\"49%\" >\n\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr><td height=\"20\"></td></tr>\n\n\t\t\t\t<tr >\n\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t");
/* 6020 */                       out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 6021 */                       out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\">\n                \t\t<a class=\"new-report-link\" href=\"javascript:clickAttributeForm('responseTime','generateAttributeReport',document.forms[1].cldApps)\">");
/* 6022 */                       out.print(FormatUtil.getString("am.reporttab.resofcloud.text"));
/* 6023 */                       out.write("</a><br /> <br />\n                \t\t</td></tr>\n\n\t\t\t\t");
/*      */                       
/* 6025 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f38 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 6026 */                       _jspx_th_logic_005fnotEmpty_005f38.setPageContext(_jspx_page_context);
/* 6027 */                       _jspx_th_logic_005fnotEmpty_005f38.setParent(_jspx_th_logic_005fnotEmpty_005f37);
/*      */                       
/* 6029 */                       _jspx_th_logic_005fnotEmpty_005f38.setName("ReportForm");
/*      */                       
/* 6031 */                       _jspx_th_logic_005fnotEmpty_005f38.setProperty("cloudAppsArrayAttribute");
/* 6032 */                       int _jspx_eval_logic_005fnotEmpty_005f38 = _jspx_th_logic_005fnotEmpty_005f38.doStartTag();
/* 6033 */                       if (_jspx_eval_logic_005fnotEmpty_005f38 != 0) {
/*      */                         for (;;) {
/* 6035 */                           out.write("\n                \t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td  class=\"new-report-heading\"  >\n               \t\t\t ");
/* 6036 */                           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.selectattribute.text"));
/* 6037 */                           out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr ><td width=\"3%\"></td>\n\t\t\t\t<td height=\"20\" valign='bottom' class=\"report-content1\">\n\t\t\t\t\n\t\t\t\t\t");
/* 6038 */                           if (_jspx_meth_html_005fselect_005f29(_jspx_th_logic_005fnotEmpty_005f38, _jspx_page_context))
/*      */                             return;
/* 6040 */                           out.write("\n\t\t\t\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t ");
/* 6041 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f38.doAfterBody();
/* 6042 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 6046 */                       if (_jspx_th_logic_005fnotEmpty_005f38.doEndTag() == 5) {
/* 6047 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f38); return;
/*      */                       }
/*      */                       
/* 6050 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f38);
/* 6051 */                       out.write("\n\t\t\t\t</table>\n\t\t\t</td>\n\n\t\t</tr>\n\n\t\t  ");
/* 6052 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f37.doAfterBody();
/* 6053 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6057 */                   if (_jspx_th_logic_005fnotEmpty_005f37.doEndTag() == 5) {
/* 6058 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f37); return;
/*      */                   }
/*      */                   
/* 6061 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f37);
/* 6062 */                   out.write("\n\t\t</table>\n\t</li>\n\t</ul>\n\n\n\t<ul id=\"sub_37\" class=\"none\">\n\t\t<li id=\"top_23\" style=\"height:");
/* 6063 */                   out.print(height2);
/* 6064 */                   out.write("px\">\n\n\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t");
/*      */                   
/* 6066 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f39 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 6067 */                   _jspx_th_logic_005fnotEmpty_005f39.setPageContext(_jspx_page_context);
/* 6068 */                   _jspx_th_logic_005fnotEmpty_005f39.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 6070 */                   _jspx_th_logic_005fnotEmpty_005f39.setName("ReportForm");
/*      */                   
/* 6072 */                   _jspx_th_logic_005fnotEmpty_005f39.setProperty("eummonitors");
/* 6073 */                   int _jspx_eval_logic_005fnotEmpty_005f39 = _jspx_th_logic_005fnotEmpty_005f39.doStartTag();
/* 6074 */                   if (_jspx_eval_logic_005fnotEmpty_005f39 != 0) {
/*      */                     for (;;) {
/* 6076 */                       out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\" colspan=\"2\">&nbsp;&nbsp;&nbsp; ");
/* 6077 */                       out.print(FormatUtil.getString("am.webclient.processtemplate.selectmontype"));
/* 6078 */                       out.write("&nbsp;\n\t\t\t\t\t\t");
/* 6079 */                       if (_jspx_meth_html_005fselect_005f30(_jspx_th_logic_005fnotEmpty_005f39, _jspx_page_context))
/*      */                         return;
/* 6081 */                       out.write("\n\t\t\t\t\t\t<td class=\"bodytextbold reportHeaderSpace\" height=\"22\" align=\"left\">\n\t\t\t\t\t\t\t<div id=\"selectEUMMonDiv\" style=\"display:none\">\n      \t\t\t\t\t\t\t&nbsp;&nbsp;&nbsp; ");
/* 6082 */                       out.print(FormatUtil.getString("am.reporttab.selectmonitor.text"));
/* 6083 */                       out.write("&nbsp;\n\t\t\t\t\t\t\t\t<select id=\"EUMMonitorContent\" name=\"EUMMonitor\" class=\"formtext\">\n\t\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\" height=\"3\"></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\" height=\"30\" align=\"center\">\n\t\t\t\t\t<div class=\"report-heading-tile\">&nbsp;</div>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"49%\">\n\n\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"eumRepTable\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td height=\"20\"></td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 6084 */                       out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 6085 */                       out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td height=\"20\">\n\t\t\t\t\t\t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].eummonitors,'All')\" class=\"new-report-link\">");
/* 6086 */                       out.print(FormatUtil.getString("am.webclient.eum.report.glancedesc"));
/* 6087 */                       out.write("</a>\n\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 6088 */                       out.print(FormatUtil.getString("am.webclient.eum.report.location"));
/* 6089 */                       out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td height=\"20\">\n\t\t\t\t\t\t\t\t<a href=\"javascript: callEUMSummary();\" class=\"new-report-link\">");
/* 6090 */                       out.print(FormatUtil.getString("am.webclient.eum.report.locationdesc"));
/* 6091 */                       out.write("</a><br/>\n\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 6092 */                       out.print(FormatUtil.getString("am.webclient.reports.reportlist.availability.report.text"));
/* 6093 */                       out.write("</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td height=\"20\"><a\n\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm1('availability','generateAvailabilityReport',document.forms[1].eummonitors)\"\n\t\t\t\t\t\t\t\tclass=\"new-report-link\">");
/* 6094 */                       out.print(FormatUtil.getString("am.webclient.eum.report.availabilitydesc"));
/* 6095 */                       out.write("</a><br> <br>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 6096 */                       out.print(FormatUtil.getString("am.webclient.reports.reportlist.health.report.text"));
/* 6097 */                       out.write("</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t<td height=\"20\"><a class=\"new-report-link\"\n\t\t\t\t\t\t\t\thref=\"javascript:clickServiceForm1('health','generateHealthReport',document.forms[1].eummonitors)\">");
/* 6098 */                       out.print(FormatUtil.getString("am.webclient.eum.report.healthdesc"));
/* 6099 */                       out.write("</a><br> <br>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"2%\" class=\"report-seperator\">&nbsp;</td>\n\t\t\t\t\t<td width=\"49%\" valign=\"top\">\n\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td height=\"20\"></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t\t<td class=\"new-report-heading\">");
/* 6100 */                       out.print(FormatUtil.getString("am.webclient.eum.glancereport"));
/* 6101 */                       out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t\t<td height=\"20\" class=\"restrictClick\">\n\t\t\t\t\t\t\t\t<a href=\"javascript:clickAttributeForm('glancereport','generateGlanceReport',document.forms[1].eummonitors,document.forms[1].EUMMonitor.value)\" class=\"new-report-link\">");
/* 6102 */                       out.print(FormatUtil.getString("am.webclient.eum.report.eumglancedesc"));
/* 6103 */                       out.write("</a>\n\t\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td height=\"10\"></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t\t<td class=\"new-report-heading\">EUM Summary Report</td>\n\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"3%\"></td>\n\t\t\t\t\t\t\t\t<td height=\"20\" class=\"restrictClick\">\n\t\t\t\t\t\t\t\t<a href=\"javascript:clickForm2('generateSummaryReport',document.forms[1].EUMMonitor.value,'EUM')\" class=\"new-report-link\">Summary Report of an EUM monitor across locations</a>\n\t\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t\t<br />\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/* 6104 */                       out.write("\n\n\t\t\t\t</tr>\n\n\t\t\t");
/* 6105 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f39.doAfterBody();
/* 6106 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6110 */                   if (_jspx_th_logic_005fnotEmpty_005f39.doEndTag() == 5) {
/* 6111 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f39); return;
/*      */                   }
/*      */                   
/* 6114 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f39);
/* 6115 */                   out.write("\n\t\t</table>\n\t\t</li>\n\t</ul>\n\t\n\t\t\n\t<ul id=\"sub_40\" class=\"none\">\n\t\t<li id=\"top_25\" style=\"height:");
/* 6116 */                   out.print(height2);
/* 6117 */                   out.write("px\">\n\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t");
/*      */                   
/* 6119 */                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f40 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 6120 */                   _jspx_th_logic_005fnotEmpty_005f40.setPageContext(_jspx_page_context);
/* 6121 */                   _jspx_th_logic_005fnotEmpty_005f40.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 6123 */                   _jspx_th_logic_005fnotEmpty_005f40.setName("ReportForm");
/*      */                   
/* 6125 */                   _jspx_th_logic_005fnotEmpty_005f40.setProperty("services");
/* 6126 */                   int _jspx_eval_logic_005fnotEmpty_005f40 = _jspx_th_logic_005fnotEmpty_005f40.doStartTag();
/* 6127 */                   if (_jspx_eval_logic_005fnotEmpty_005f40 != 0) {
/*      */                     for (;;) {
/* 6129 */                       out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"3\">\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td  class=\"bodytextbold report-content1\" width=\"15%\" height=\"52\" align=\"left\" >&nbsp;&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t\t");
/* 6130 */                       out.print(FormatUtil.getString("am.webclient.selectmonitor.alert.text"));
/* 6131 */                       out.write("&nbsp;\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"30%\"  class=\"report-content1\">\n\t\t\t\t\t\t\t\t\t\t");
/* 6132 */                       if (_jspx_meth_html_005fselect_005f31(_jspx_th_logic_005fnotEmpty_005f40, _jspx_page_context))
/*      */                         return;
/* 6134 */                       out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td class=\"bodytextbold report-content1\" height=\"32\"  align=\"left\">\n\t\t\t\t\t\t\t\t\t<div id=\"forecastReportAttribute\" style=\"display:none\">\n\t\t\t\t\t\t\t\t\t\t\t<select  id=\"attributeValues\" styleClass=\"formtext chosenselect\" onchange=\"showforecastreport(this,true)\" style=\"width:300px; height:22px;\">\n\t\t\t\t\t\t\t\t\t\t\t\t<option>\n\t\t\t\t\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"3\" height=\"3\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"3\" height=\"30\" align=\"center\"><div class=\"report-heading-tile\" >&nbsp;</div></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t");
/* 6135 */                       JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("forecast", request.getCharacterEncoding()), out, false);
/* 6136 */                       out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t");
/* 6137 */                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f40.doAfterBody();
/* 6138 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 6142 */                   if (_jspx_th_logic_005fnotEmpty_005f40.doEndTag() == 5) {
/* 6143 */                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f40); return;
/*      */                   }
/*      */                   
/* 6146 */                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f40);
/* 6147 */                   out.write("\n\t\t\t</table>\n\t\t</li>\n\t</ul>\n\t\n\n\n\n</div>\n");
/* 6148 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 6149 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 6153 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 6154 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */               }
/*      */               else {
/* 6157 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 6158 */                 out.write(10);
/* 6159 */                 out.write(10);
/* 6160 */                 JspRuntimeLibrary.include(request, response, "MonitorLevelAttributes.jsp" + ("MonitorLevelAttributes.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitortype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("bottomdiv", request.getCharacterEncoding()), out, false);
/* 6161 */                 out.write(9);
/* 6162 */                 out.write("\n\n\n</div>\n</div></div>\n\n\n\n</td>\n\n<!-- Inner tabel ends!-->\n\n<td class=\"vcenter-shadow-rigtile\"><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n</tr>\n<tr>\n<td class=\"vcenter-shadow-btm-lcorn\" width=\"8\"><img src=\"/images/spacer.gif\" width=\"8\" /></td>\n<td class=\"vcenter-shadow-btm-mtile\"></td>\n<td class=\"vcenter-shadow-btm-rcorn\" width=\"7\"><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n</tr>\n</table>\n\n\n<script type=\"text/javascript\">\n\tjQuery(document).ready(function() {\t\t\n\t  if ( ");
/* 6163 */                 out.print(showBSGReportOption);
/* 6164 */                 out.write(" ) {\n\t    var val = $(\"#monday\").val(); //No I18N\n\t    if (val && $(\"#monday\").val().length > 20) {\n\t      val = val.substring(0, 20);\n\t    }\n\t    $('#monday').val(val); //No I18N\n\t  }\n\n\t  var val = $(\"#mondaycapacity\").val() //No I18N\n\t  if (val && $(\"#mondaycapacity\").val().length > 20) {\n\t    val = val.substring(0, 20);\n\t  }\n\t  $('#mondaycapacity').val(val); //No I18N\n\n\t  val = $(\"#saturday\").val(); //No I18N\n\t  if (val && val.length > 20 && val.length < 30) {\n\t    val = val.substring(0, 24);\n\t  }\n\t  else if (val && val.length >= 30) {\n\t    val = val.substring(0, 20);\n\t  }\n\t  $('#saturday').val(val); //No I18N\n\t  // Issue id : AM805\n\t  \n\t  jQuery('#EUMMonitorContent').load(\"/jsp/reports/eumReportsDropDown.jsp?monType=None\"); //NO I18N\n\t  \n\t  jQuery('.EUMServiecsContent').change(function(){ //No I18N\n\t      if (jQuery(\"select.EUMServiecsContent option:selected\").html() == \"All\") //NO I18N\n\t      {\n\t        document.getElementById(\"selectEUMMonDiv\").style.display=\"none\";\n\t      }\n\t      else {\n\t        document.getElementById(\"selectEUMMonDiv\").style.display=\"inline\";\n");
/* 6165 */                 out.write("\t        jQuery('#EUMMonitorContent').load(\"/jsp/reports/eumReportsDropDown.jsp?monType=\" + escape(jQuery(\".EUMServiecsContent\").val())); //NO I18N\n\t      }\n\t    });\n\t  \n\t  jQuery(\".chosenselect\").chosen();\t\t// NO I18N\n\t  jQuery(\"#attributeValues\").chosen();\t\t// NO I18N\n\t  sortSelectItems(\"system\");// NO I18N\n\t  sortSelectItems(\"appserver\");// NO I18N\n\t  removeScroll('top_16');// NO I18N\n\t});\n</script>\n</html>\n\n");
/*      */               }
/* 6167 */             } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 6168 */         out = _jspx_out;
/* 6169 */         if ((out != null) && (out.getBufferSize() != 0))
/* 6170 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 6171 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 6174 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6180 */     PageContext pageContext = _jspx_page_context;
/* 6181 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6183 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6184 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 6185 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6187 */     _jspx_th_html_005fhidden_005f0.setProperty("actionMethod");
/* 6188 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 6189 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 6190 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 6191 */       return true;
/*      */     }
/* 6193 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 6194 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6199 */     PageContext pageContext = _jspx_page_context;
/* 6200 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6202 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6203 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 6204 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6206 */     _jspx_th_html_005fhidden_005f1.setProperty("attribute");
/* 6207 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 6208 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 6209 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6210 */       return true;
/*      */     }
/* 6212 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6213 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6218 */     PageContext pageContext = _jspx_page_context;
/* 6219 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6221 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6222 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 6223 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6225 */     _jspx_th_html_005fhidden_005f2.setProperty("startDate");
/* 6226 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 6227 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 6228 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 6229 */       return true;
/*      */     }
/* 6231 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 6232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6237 */     PageContext pageContext = _jspx_page_context;
/* 6238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6240 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6241 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 6242 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6244 */     _jspx_th_html_005fhidden_005f3.setProperty("endDate");
/* 6245 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 6246 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 6247 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 6248 */       return true;
/*      */     }
/* 6250 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 6251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6256 */     PageContext pageContext = _jspx_page_context;
/* 6257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6259 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6260 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/* 6261 */     _jspx_th_html_005fhidden_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6263 */     _jspx_th_html_005fhidden_005f4.setProperty("resourceType");
/* 6264 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/* 6265 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/* 6266 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 6267 */       return true;
/*      */     }
/* 6269 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 6270 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6275 */     PageContext pageContext = _jspx_page_context;
/* 6276 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6278 */     HiddenTag _jspx_th_html_005fhidden_005f5 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6279 */     _jspx_th_html_005fhidden_005f5.setPageContext(_jspx_page_context);
/* 6280 */     _jspx_th_html_005fhidden_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6282 */     _jspx_th_html_005fhidden_005f5.setProperty("workingdays");
/* 6283 */     int _jspx_eval_html_005fhidden_005f5 = _jspx_th_html_005fhidden_005f5.doStartTag();
/* 6284 */     if (_jspx_th_html_005fhidden_005f5.doEndTag() == 5) {
/* 6285 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 6286 */       return true;
/*      */     }
/* 6288 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/* 6289 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6294 */     PageContext pageContext = _jspx_page_context;
/* 6295 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6297 */     HiddenTag _jspx_th_html_005fhidden_005f6 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6298 */     _jspx_th_html_005fhidden_005f6.setPageContext(_jspx_page_context);
/* 6299 */     _jspx_th_html_005fhidden_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6301 */     _jspx_th_html_005fhidden_005f6.setProperty("laststart");
/* 6302 */     int _jspx_eval_html_005fhidden_005f6 = _jspx_th_html_005fhidden_005f6.doStartTag();
/* 6303 */     if (_jspx_th_html_005fhidden_005f6.doEndTag() == 5) {
/* 6304 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/* 6305 */       return true;
/*      */     }
/* 6307 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/* 6308 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6313 */     PageContext pageContext = _jspx_page_context;
/* 6314 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6316 */     HiddenTag _jspx_th_html_005fhidden_005f7 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6317 */     _jspx_th_html_005fhidden_005f7.setPageContext(_jspx_page_context);
/* 6318 */     _jspx_th_html_005fhidden_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6320 */     _jspx_th_html_005fhidden_005f7.setProperty("lastend");
/* 6321 */     int _jspx_eval_html_005fhidden_005f7 = _jspx_th_html_005fhidden_005f7.doStartTag();
/* 6322 */     if (_jspx_th_html_005fhidden_005f7.doEndTag() == 5) {
/* 6323 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/* 6324 */       return true;
/*      */     }
/* 6326 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/* 6327 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6332 */     PageContext pageContext = _jspx_page_context;
/* 6333 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6335 */     HiddenTag _jspx_th_html_005fhidden_005f8 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6336 */     _jspx_th_html_005fhidden_005f8.setPageContext(_jspx_page_context);
/* 6337 */     _jspx_th_html_005fhidden_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6339 */     _jspx_th_html_005fhidden_005f8.setProperty("thisstart");
/* 6340 */     int _jspx_eval_html_005fhidden_005f8 = _jspx_th_html_005fhidden_005f8.doStartTag();
/* 6341 */     if (_jspx_th_html_005fhidden_005f8.doEndTag() == 5) {
/* 6342 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f8);
/* 6343 */       return true;
/*      */     }
/* 6345 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f8);
/* 6346 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6351 */     PageContext pageContext = _jspx_page_context;
/* 6352 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6354 */     HiddenTag _jspx_th_html_005fhidden_005f9 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6355 */     _jspx_th_html_005fhidden_005f9.setPageContext(_jspx_page_context);
/* 6356 */     _jspx_th_html_005fhidden_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6358 */     _jspx_th_html_005fhidden_005f9.setProperty("thisend");
/* 6359 */     int _jspx_eval_html_005fhidden_005f9 = _jspx_th_html_005fhidden_005f9.doStartTag();
/* 6360 */     if (_jspx_th_html_005fhidden_005f9.doEndTag() == 5) {
/* 6361 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f9);
/* 6362 */       return true;
/*      */     }
/* 6364 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f9);
/* 6365 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6370 */     PageContext pageContext = _jspx_page_context;
/* 6371 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6373 */     HiddenTag _jspx_th_html_005fhidden_005f10 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 6374 */     _jspx_th_html_005fhidden_005f10.setPageContext(_jspx_page_context);
/* 6375 */     _jspx_th_html_005fhidden_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6377 */     _jspx_th_html_005fhidden_005f10.setProperty("Report");
/*      */     
/* 6379 */     _jspx_th_html_005fhidden_005f10.setValue("true");
/* 6380 */     int _jspx_eval_html_005fhidden_005f10 = _jspx_th_html_005fhidden_005f10.doStartTag();
/* 6381 */     if (_jspx_th_html_005fhidden_005f10.doEndTag() == 5) {
/* 6382 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f10);
/* 6383 */       return true;
/*      */     }
/* 6385 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f10);
/* 6386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6391 */     PageContext pageContext = _jspx_page_context;
/* 6392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6394 */     HiddenTag _jspx_th_html_005fhidden_005f11 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6395 */     _jspx_th_html_005fhidden_005f11.setPageContext(_jspx_page_context);
/* 6396 */     _jspx_th_html_005fhidden_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6398 */     _jspx_th_html_005fhidden_005f11.setProperty("interval");
/* 6399 */     int _jspx_eval_html_005fhidden_005f11 = _jspx_th_html_005fhidden_005f11.doStartTag();
/* 6400 */     if (_jspx_th_html_005fhidden_005f11.doEndTag() == 5) {
/* 6401 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f11);
/* 6402 */       return true;
/*      */     }
/* 6404 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f11);
/* 6405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6410 */     PageContext pageContext = _jspx_page_context;
/* 6411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6413 */     HiddenTag _jspx_th_html_005fhidden_005f12 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 6414 */     _jspx_th_html_005fhidden_005f12.setPageContext(_jspx_page_context);
/* 6415 */     _jspx_th_html_005fhidden_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6417 */     _jspx_th_html_005fhidden_005f12.setProperty("reporttype");
/*      */     
/* 6419 */     _jspx_th_html_005fhidden_005f12.setValue("html");
/* 6420 */     int _jspx_eval_html_005fhidden_005f12 = _jspx_th_html_005fhidden_005f12.doStartTag();
/* 6421 */     if (_jspx_th_html_005fhidden_005f12.doEndTag() == 5) {
/* 6422 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f12);
/* 6423 */       return true;
/*      */     }
/* 6425 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f12);
/* 6426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6431 */     PageContext pageContext = _jspx_page_context;
/* 6432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6434 */     HiddenTag _jspx_th_html_005fhidden_005f13 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6435 */     _jspx_th_html_005fhidden_005f13.setPageContext(_jspx_page_context);
/* 6436 */     _jspx_th_html_005fhidden_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6438 */     _jspx_th_html_005fhidden_005f13.setProperty("pdfAttributeName");
/* 6439 */     int _jspx_eval_html_005fhidden_005f13 = _jspx_th_html_005fhidden_005f13.doStartTag();
/* 6440 */     if (_jspx_th_html_005fhidden_005f13.doEndTag() == 5) {
/* 6441 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f13);
/* 6442 */       return true;
/*      */     }
/* 6444 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f13);
/* 6445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6450 */     PageContext pageContext = _jspx_page_context;
/* 6451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6453 */     HiddenTag _jspx_th_html_005fhidden_005f14 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6454 */     _jspx_th_html_005fhidden_005f14.setPageContext(_jspx_page_context);
/* 6455 */     _jspx_th_html_005fhidden_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6457 */     _jspx_th_html_005fhidden_005f14.setProperty("resourceid");
/* 6458 */     int _jspx_eval_html_005fhidden_005f14 = _jspx_th_html_005fhidden_005f14.doStartTag();
/* 6459 */     if (_jspx_th_html_005fhidden_005f14.doEndTag() == 5) {
/* 6460 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f14);
/* 6461 */       return true;
/*      */     }
/* 6463 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f14);
/* 6464 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6469 */     PageContext pageContext = _jspx_page_context;
/* 6470 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6472 */     HiddenTag _jspx_th_html_005fhidden_005f15 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6473 */     _jspx_th_html_005fhidden_005f15.setPageContext(_jspx_page_context);
/* 6474 */     _jspx_th_html_005fhidden_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6476 */     _jspx_th_html_005fhidden_005f15.setProperty("attributeName");
/* 6477 */     int _jspx_eval_html_005fhidden_005f15 = _jspx_th_html_005fhidden_005f15.doStartTag();
/* 6478 */     if (_jspx_th_html_005fhidden_005f15.doEndTag() == 5) {
/* 6479 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f15);
/* 6480 */       return true;
/*      */     }
/* 6482 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f15);
/* 6483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_logic_005fnotEmpty_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6488 */     PageContext pageContext = _jspx_page_context;
/* 6489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6491 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 6492 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 6493 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f3);
/*      */     
/* 6495 */     _jspx_th_html_005fselect_005f0.setProperty("sunday");
/*      */     
/* 6497 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext chosenselect");
/*      */     
/* 6499 */     _jspx_th_html_005fselect_005f0.setStyle("width:250px;line-height:20px");
/*      */     
/* 6501 */     _jspx_th_html_005fselect_005f0.setOnchange("javascript:mgAttributeForm(this.form)");
/* 6502 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 6503 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 6504 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 6505 */         out = _jspx_page_context.pushBody();
/* 6506 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 6507 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6510 */         out.write(32);
/* 6511 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 6512 */           return true;
/* 6513 */         out.write("\n        \t\t\t\t");
/* 6514 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 6515 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6518 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 6519 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6522 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 6523 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 6524 */       return true;
/*      */     }
/* 6526 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 6527 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6532 */     PageContext pageContext = _jspx_page_context;
/* 6533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6535 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 6536 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 6537 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 6539 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("durations");
/* 6540 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 6541 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 6542 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6543 */       return true;
/*      */     }
/* 6545 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 6546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6551 */     PageContext pageContext = _jspx_page_context;
/* 6552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6554 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 6555 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 6556 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 6558 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("customTypes");
/* 6559 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 6560 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 6561 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 6562 */       return true;
/*      */     }
/* 6564 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 6565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6570 */     PageContext pageContext = _jspx_page_context;
/* 6571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6573 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 6574 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 6575 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f5);
/*      */     
/* 6577 */     _jspx_th_html_005fselect_005f2.setProperty("customTypeAttrib");
/*      */     
/* 6579 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext chosenselect");
/*      */     
/* 6581 */     _jspx_th_html_005fselect_005f2.setStyle("width:250px;line-height:20px");
/*      */     
/* 6583 */     _jspx_th_html_005fselect_005f2.setOnchange("javascript:attributeFormAction(document.forms[1].customservice,this)");
/* 6584 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 6585 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 6586 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 6587 */         out = _jspx_page_context.pushBody();
/* 6588 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 6589 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6592 */         out.write(32);
/* 6593 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 6594 */           return true;
/* 6595 */         out.write("\n        \t\t\t\t");
/* 6596 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 6597 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6600 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 6601 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6604 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 6605 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 6606 */       return true;
/*      */     }
/* 6608 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 6609 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6614 */     PageContext pageContext = _jspx_page_context;
/* 6615 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6617 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 6618 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 6619 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 6621 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("customserviceAttrib");
/* 6622 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 6623 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 6624 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 6625 */       return true;
/*      */     }
/* 6627 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 6628 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6633 */     PageContext pageContext = _jspx_page_context;
/* 6634 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6636 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f6 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 6637 */     _jspx_th_logic_005fnotEmpty_005f6.setPageContext(_jspx_page_context);
/* 6638 */     _jspx_th_logic_005fnotEmpty_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6640 */     _jspx_th_logic_005fnotEmpty_005f6.setName("ReportForm");
/*      */     
/* 6642 */     _jspx_th_logic_005fnotEmpty_005f6.setProperty("nwd");
/* 6643 */     int _jspx_eval_logic_005fnotEmpty_005f6 = _jspx_th_logic_005fnotEmpty_005f6.doStartTag();
/* 6644 */     if (_jspx_eval_logic_005fnotEmpty_005f6 != 0) {
/*      */       for (;;) {
/* 6646 */         out.write("\n\t\t\t");
/* 6647 */         if (_jspx_meth_html_005fhidden_005f17(_jspx_th_logic_005fnotEmpty_005f6, _jspx_page_context))
/* 6648 */           return true;
/* 6649 */         out.write(10);
/* 6650 */         out.write(9);
/* 6651 */         out.write(9);
/* 6652 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f6.doAfterBody();
/* 6653 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6657 */     if (_jspx_th_logic_005fnotEmpty_005f6.doEndTag() == 5) {
/* 6658 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6);
/* 6659 */       return true;
/*      */     }
/* 6661 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6);
/* 6662 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f17(JspTag _jspx_th_logic_005fnotEmpty_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6667 */     PageContext pageContext = _jspx_page_context;
/* 6668 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6670 */     HiddenTag _jspx_th_html_005fhidden_005f17 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6671 */     _jspx_th_html_005fhidden_005f17.setPageContext(_jspx_page_context);
/* 6672 */     _jspx_th_html_005fhidden_005f17.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f6);
/*      */     
/* 6674 */     _jspx_th_html_005fhidden_005f17.setProperty("nwd");
/* 6675 */     int _jspx_eval_html_005fhidden_005f17 = _jspx_th_html_005fhidden_005f17.doStartTag();
/* 6676 */     if (_jspx_th_html_005fhidden_005f17.doEndTag() == 5) {
/* 6677 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f17);
/* 6678 */       return true;
/*      */     }
/* 6680 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f17);
/* 6681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6686 */     PageContext pageContext = _jspx_page_context;
/* 6687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6689 */     EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 6690 */     _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 6691 */     _jspx_th_logic_005fempty_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6693 */     _jspx_th_logic_005fempty_005f2.setName("ReportForm");
/*      */     
/* 6695 */     _jspx_th_logic_005fempty_005f2.setProperty("nwd");
/* 6696 */     int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 6697 */     if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */       for (;;) {
/* 6699 */         out.write("\n\t\t\t");
/* 6700 */         if (_jspx_meth_html_005fhidden_005f18(_jspx_th_logic_005fempty_005f2, _jspx_page_context))
/* 6701 */           return true;
/* 6702 */         out.write(10);
/* 6703 */         out.write(9);
/* 6704 */         out.write(9);
/* 6705 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 6706 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6710 */     if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 6711 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 6712 */       return true;
/*      */     }
/* 6714 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 6715 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f18(JspTag _jspx_th_logic_005fempty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6720 */     PageContext pageContext = _jspx_page_context;
/* 6721 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6723 */     HiddenTag _jspx_th_html_005fhidden_005f18 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 6724 */     _jspx_th_html_005fhidden_005f18.setPageContext(_jspx_page_context);
/* 6725 */     _jspx_th_html_005fhidden_005f18.setParent((Tag)_jspx_th_logic_005fempty_005f2);
/*      */     
/* 6727 */     _jspx_th_html_005fhidden_005f18.setProperty("nwd");
/*      */     
/* 6729 */     _jspx_th_html_005fhidden_005f18.setValue("OpManager-Router");
/* 6730 */     int _jspx_eval_html_005fhidden_005f18 = _jspx_th_html_005fhidden_005f18.doStartTag();
/* 6731 */     if (_jspx_th_html_005fhidden_005f18.doEndTag() == 5) {
/* 6732 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f18);
/* 6733 */       return true;
/*      */     }
/* 6735 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f18);
/* 6736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6741 */     PageContext pageContext = _jspx_page_context;
/* 6742 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6744 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f7 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 6745 */     _jspx_th_logic_005fnotEmpty_005f7.setPageContext(_jspx_page_context);
/* 6746 */     _jspx_th_logic_005fnotEmpty_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6748 */     _jspx_th_logic_005fnotEmpty_005f7.setName("ReportForm");
/*      */     
/* 6750 */     _jspx_th_logic_005fnotEmpty_005f7.setProperty("san");
/* 6751 */     int _jspx_eval_logic_005fnotEmpty_005f7 = _jspx_th_logic_005fnotEmpty_005f7.doStartTag();
/* 6752 */     if (_jspx_eval_logic_005fnotEmpty_005f7 != 0) {
/*      */       for (;;) {
/* 6754 */         out.write("\n\t\t\t");
/* 6755 */         if (_jspx_meth_html_005fhidden_005f19(_jspx_th_logic_005fnotEmpty_005f7, _jspx_page_context))
/* 6756 */           return true;
/* 6757 */         out.write(10);
/* 6758 */         out.write(9);
/* 6759 */         out.write(9);
/* 6760 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f7.doAfterBody();
/* 6761 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6765 */     if (_jspx_th_logic_005fnotEmpty_005f7.doEndTag() == 5) {
/* 6766 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f7);
/* 6767 */       return true;
/*      */     }
/* 6769 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f7);
/* 6770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f19(JspTag _jspx_th_logic_005fnotEmpty_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6775 */     PageContext pageContext = _jspx_page_context;
/* 6776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6778 */     HiddenTag _jspx_th_html_005fhidden_005f19 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6779 */     _jspx_th_html_005fhidden_005f19.setPageContext(_jspx_page_context);
/* 6780 */     _jspx_th_html_005fhidden_005f19.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f7);
/*      */     
/* 6782 */     _jspx_th_html_005fhidden_005f19.setProperty("san");
/* 6783 */     int _jspx_eval_html_005fhidden_005f19 = _jspx_th_html_005fhidden_005f19.doStartTag();
/* 6784 */     if (_jspx_th_html_005fhidden_005f19.doEndTag() == 5) {
/* 6785 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f19);
/* 6786 */       return true;
/*      */     }
/* 6788 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f19);
/* 6789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6794 */     PageContext pageContext = _jspx_page_context;
/* 6795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6797 */     EmptyTag _jspx_th_logic_005fempty_005f3 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 6798 */     _jspx_th_logic_005fempty_005f3.setPageContext(_jspx_page_context);
/* 6799 */     _jspx_th_logic_005fempty_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6801 */     _jspx_th_logic_005fempty_005f3.setName("ReportForm");
/*      */     
/* 6803 */     _jspx_th_logic_005fempty_005f3.setProperty("san");
/* 6804 */     int _jspx_eval_logic_005fempty_005f3 = _jspx_th_logic_005fempty_005f3.doStartTag();
/* 6805 */     if (_jspx_eval_logic_005fempty_005f3 != 0) {
/*      */       for (;;) {
/* 6807 */         out.write("\n\t\t\t");
/* 6808 */         if (_jspx_meth_html_005fhidden_005f20(_jspx_th_logic_005fempty_005f3, _jspx_page_context))
/* 6809 */           return true;
/* 6810 */         out.write(10);
/* 6811 */         out.write(9);
/* 6812 */         out.write(9);
/* 6813 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f3.doAfterBody();
/* 6814 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6818 */     if (_jspx_th_logic_005fempty_005f3.doEndTag() == 5) {
/* 6819 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f3);
/* 6820 */       return true;
/*      */     }
/* 6822 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f3);
/* 6823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f20(JspTag _jspx_th_logic_005fempty_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6828 */     PageContext pageContext = _jspx_page_context;
/* 6829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6831 */     HiddenTag _jspx_th_html_005fhidden_005f20 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 6832 */     _jspx_th_html_005fhidden_005f20.setPageContext(_jspx_page_context);
/* 6833 */     _jspx_th_html_005fhidden_005f20.setParent((Tag)_jspx_th_logic_005fempty_005f3);
/*      */     
/* 6835 */     _jspx_th_html_005fhidden_005f20.setProperty("san");
/*      */     
/* 6837 */     _jspx_th_html_005fhidden_005f20.setValue("OpStor-Host");
/* 6838 */     int _jspx_eval_html_005fhidden_005f20 = _jspx_th_html_005fhidden_005f20.doStartTag();
/* 6839 */     if (_jspx_th_html_005fhidden_005f20.doEndTag() == 5) {
/* 6840 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f20);
/* 6841 */       return true;
/*      */     }
/* 6843 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f20);
/* 6844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fnotEmpty_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6849 */     PageContext pageContext = _jspx_page_context;
/* 6850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6852 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6853 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 6854 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f8);
/*      */     
/* 6856 */     _jspx_th_c_005fout_005f0.setValue("${ReportForm.monitorDisplayNames[ReportForm.resid]}");
/* 6857 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 6858 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 6859 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6860 */       return true;
/*      */     }
/* 6862 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f21(JspTag _jspx_th_logic_005fnotEmpty_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6868 */     PageContext pageContext = _jspx_page_context;
/* 6869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6871 */     HiddenTag _jspx_th_html_005fhidden_005f21 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6872 */     _jspx_th_html_005fhidden_005f21.setPageContext(_jspx_page_context);
/* 6873 */     _jspx_th_html_005fhidden_005f21.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f8);
/*      */     
/* 6875 */     _jspx_th_html_005fhidden_005f21.setProperty("resid");
/* 6876 */     int _jspx_eval_html_005fhidden_005f21 = _jspx_th_html_005fhidden_005f21.doStartTag();
/* 6877 */     if (_jspx_th_html_005fhidden_005f21.doEndTag() == 5) {
/* 6878 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f21);
/* 6879 */       return true;
/*      */     }
/* 6881 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f21);
/* 6882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6887 */     PageContext pageContext = _jspx_page_context;
/* 6888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6890 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 6891 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 6892 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 6894 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("systems");
/* 6895 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 6896 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 6897 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 6898 */       return true;
/*      */     }
/* 6900 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 6901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6906 */     PageContext pageContext = _jspx_page_context;
/* 6907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6909 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 6910 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 6911 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f10);
/*      */     
/* 6913 */     _jspx_th_html_005fselect_005f4.setProperty("serverAttribute");
/*      */     
/* 6915 */     _jspx_th_html_005fselect_005f4.setStyle("width:250px");
/*      */     
/* 6917 */     _jspx_th_html_005fselect_005f4.setStyleClass("formtext chosenselect");
/*      */     
/* 6919 */     _jspx_th_html_005fselect_005f4.setOnchange("javascript:attributeFormAction(document.forms[1].system,this)");
/* 6920 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 6921 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 6922 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 6923 */         out = _jspx_page_context.pushBody();
/* 6924 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 6925 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6928 */         out.write(32);
/* 6929 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 6930 */           return true;
/* 6931 */         out.write("\n        \t\t\t\t");
/* 6932 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 6933 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6936 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 6937 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6940 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 6941 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 6942 */       return true;
/*      */     }
/* 6944 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 6945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6950 */     PageContext pageContext = _jspx_page_context;
/* 6951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6953 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 6954 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 6955 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 6957 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("serverArrayAttribute");
/* 6958 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 6959 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 6960 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 6961 */       return true;
/*      */     }
/* 6963 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 6964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f5(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6969 */     PageContext pageContext = _jspx_page_context;
/* 6970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6972 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f5 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 6973 */     _jspx_th_html_005foptionsCollection_005f5.setPageContext(_jspx_page_context);
/* 6974 */     _jspx_th_html_005foptionsCollection_005f5.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 6976 */     _jspx_th_html_005foptionsCollection_005f5.setProperty("appServers");
/* 6977 */     int _jspx_eval_html_005foptionsCollection_005f5 = _jspx_th_html_005foptionsCollection_005f5.doStartTag();
/* 6978 */     if (_jspx_th_html_005foptionsCollection_005f5.doEndTag() == 5) {
/* 6979 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 6980 */       return true;
/*      */     }
/* 6982 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 6983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f6(JspTag _jspx_th_logic_005fnotEmpty_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6988 */     PageContext pageContext = _jspx_page_context;
/* 6989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6991 */     SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 6992 */     _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 6993 */     _jspx_th_html_005fselect_005f6.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f12);
/*      */     
/* 6995 */     _jspx_th_html_005fselect_005f6.setProperty("applicationAttribute");
/*      */     
/* 6997 */     _jspx_th_html_005fselect_005f6.setStyleClass("formtext chosenselect");
/*      */     
/* 6999 */     _jspx_th_html_005fselect_005f6.setStyle("width:250px;line-height:20px");
/*      */     
/* 7001 */     _jspx_th_html_005fselect_005f6.setOnchange("javascript:attributeFormAction(document.forms[1].appserver,this)");
/* 7002 */     int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 7003 */     if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 7004 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 7005 */         out = _jspx_page_context.pushBody();
/* 7006 */         _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 7007 */         _jspx_th_html_005fselect_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7010 */         out.write(32);
/* 7011 */         if (_jspx_meth_html_005foptionsCollection_005f6(_jspx_th_html_005fselect_005f6, _jspx_page_context))
/* 7012 */           return true;
/* 7013 */         out.write("\n        \t\t\t\t");
/* 7014 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 7015 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7018 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 7019 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7022 */     if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 7023 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f6);
/* 7024 */       return true;
/*      */     }
/* 7026 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f6);
/* 7027 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f6(JspTag _jspx_th_html_005fselect_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7032 */     PageContext pageContext = _jspx_page_context;
/* 7033 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7035 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f6 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7036 */     _jspx_th_html_005foptionsCollection_005f6.setPageContext(_jspx_page_context);
/* 7037 */     _jspx_th_html_005foptionsCollection_005f6.setParent((Tag)_jspx_th_html_005fselect_005f6);
/*      */     
/* 7039 */     _jspx_th_html_005foptionsCollection_005f6.setProperty("appArrayAttribute");
/* 7040 */     int _jspx_eval_html_005foptionsCollection_005f6 = _jspx_th_html_005foptionsCollection_005f6.doStartTag();
/* 7041 */     if (_jspx_th_html_005foptionsCollection_005f6.doEndTag() == 5) {
/* 7042 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 7043 */       return true;
/*      */     }
/* 7045 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 7046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f7(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7051 */     PageContext pageContext = _jspx_page_context;
/* 7052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7054 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f7 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7055 */     _jspx_th_html_005foptionsCollection_005f7.setPageContext(_jspx_page_context);
/* 7056 */     _jspx_th_html_005foptionsCollection_005f7.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*      */     
/* 7058 */     _jspx_th_html_005foptionsCollection_005f7.setProperty("dbServers");
/* 7059 */     int _jspx_eval_html_005foptionsCollection_005f7 = _jspx_th_html_005foptionsCollection_005f7.doStartTag();
/* 7060 */     if (_jspx_th_html_005foptionsCollection_005f7.doEndTag() == 5) {
/* 7061 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 7062 */       return true;
/*      */     }
/* 7064 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 7065 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f8(JspTag _jspx_th_logic_005fnotEmpty_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7070 */     PageContext pageContext = _jspx_page_context;
/* 7071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7073 */     SelectTag _jspx_th_html_005fselect_005f8 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 7074 */     _jspx_th_html_005fselect_005f8.setPageContext(_jspx_page_context);
/* 7075 */     _jspx_th_html_005fselect_005f8.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f14);
/*      */     
/* 7077 */     _jspx_th_html_005fselect_005f8.setProperty("dbAttribute");
/*      */     
/* 7079 */     _jspx_th_html_005fselect_005f8.setStyleClass("formtext chosenselect");
/*      */     
/* 7081 */     _jspx_th_html_005fselect_005f8.setStyle("width:250px;line-height:20px");
/*      */     
/* 7083 */     _jspx_th_html_005fselect_005f8.setOnchange("javascript:attributeFormAction(document.forms[1].dbserver,this)");
/* 7084 */     int _jspx_eval_html_005fselect_005f8 = _jspx_th_html_005fselect_005f8.doStartTag();
/* 7085 */     if (_jspx_eval_html_005fselect_005f8 != 0) {
/* 7086 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 7087 */         out = _jspx_page_context.pushBody();
/* 7088 */         _jspx_th_html_005fselect_005f8.setBodyContent((BodyContent)out);
/* 7089 */         _jspx_th_html_005fselect_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7092 */         out.write(32);
/* 7093 */         if (_jspx_meth_html_005foptionsCollection_005f8(_jspx_th_html_005fselect_005f8, _jspx_page_context))
/* 7094 */           return true;
/* 7095 */         out.write("\n        \t\t\t\t");
/* 7096 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f8.doAfterBody();
/* 7097 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7100 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 7101 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7104 */     if (_jspx_th_html_005fselect_005f8.doEndTag() == 5) {
/* 7105 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f8);
/* 7106 */       return true;
/*      */     }
/* 7108 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f8);
/* 7109 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f8(JspTag _jspx_th_html_005fselect_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7114 */     PageContext pageContext = _jspx_page_context;
/* 7115 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7117 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f8 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7118 */     _jspx_th_html_005foptionsCollection_005f8.setPageContext(_jspx_page_context);
/* 7119 */     _jspx_th_html_005foptionsCollection_005f8.setParent((Tag)_jspx_th_html_005fselect_005f8);
/*      */     
/* 7121 */     _jspx_th_html_005foptionsCollection_005f8.setProperty("dbArrayAttribute");
/* 7122 */     int _jspx_eval_html_005foptionsCollection_005f8 = _jspx_th_html_005foptionsCollection_005f8.doStartTag();
/* 7123 */     if (_jspx_th_html_005foptionsCollection_005f8.doEndTag() == 5) {
/* 7124 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f8);
/* 7125 */       return true;
/*      */     }
/* 7127 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f8);
/* 7128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f22(JspTag _jspx_th_logic_005fnotEmpty_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7133 */     PageContext pageContext = _jspx_page_context;
/* 7134 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7136 */     HiddenTag _jspx_th_html_005fhidden_005f22 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 7137 */     _jspx_th_html_005fhidden_005f22.setPageContext(_jspx_page_context);
/* 7138 */     _jspx_th_html_005fhidden_005f22.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f15);
/*      */     
/* 7140 */     _jspx_th_html_005fhidden_005f22.setProperty("webservice");
/* 7141 */     int _jspx_eval_html_005fhidden_005f22 = _jspx_th_html_005fhidden_005f22.doStartTag();
/* 7142 */     if (_jspx_th_html_005fhidden_005f22.doEndTag() == 5) {
/* 7143 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f22);
/* 7144 */       return true;
/*      */     }
/* 7146 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f22);
/* 7147 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f9(JspTag _jspx_th_logic_005fnotEmpty_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7152 */     PageContext pageContext = _jspx_page_context;
/* 7153 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7155 */     SelectTag _jspx_th_html_005fselect_005f9 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 7156 */     _jspx_th_html_005fselect_005f9.setPageContext(_jspx_page_context);
/* 7157 */     _jspx_th_html_005fselect_005f9.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f16);
/*      */     
/* 7159 */     _jspx_th_html_005fselect_005f9.setProperty("webserviceAttribute");
/*      */     
/* 7161 */     _jspx_th_html_005fselect_005f9.setStyleClass("formtext chosenselect");
/*      */     
/* 7163 */     _jspx_th_html_005fselect_005f9.setStyle("width:250px;line-height:20px");
/*      */     
/* 7165 */     _jspx_th_html_005fselect_005f9.setOnchange("javascript:attributeFormAction(document.forms[1].webservice,this)");
/* 7166 */     int _jspx_eval_html_005fselect_005f9 = _jspx_th_html_005fselect_005f9.doStartTag();
/* 7167 */     if (_jspx_eval_html_005fselect_005f9 != 0) {
/* 7168 */       if (_jspx_eval_html_005fselect_005f9 != 1) {
/* 7169 */         out = _jspx_page_context.pushBody();
/* 7170 */         _jspx_th_html_005fselect_005f9.setBodyContent((BodyContent)out);
/* 7171 */         _jspx_th_html_005fselect_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7174 */         out.write(32);
/* 7175 */         if (_jspx_meth_html_005foptionsCollection_005f9(_jspx_th_html_005fselect_005f9, _jspx_page_context))
/* 7176 */           return true;
/* 7177 */         out.write("\n        \t\t\t\t");
/* 7178 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f9.doAfterBody();
/* 7179 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7182 */       if (_jspx_eval_html_005fselect_005f9 != 1) {
/* 7183 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7186 */     if (_jspx_th_html_005fselect_005f9.doEndTag() == 5) {
/* 7187 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f9);
/* 7188 */       return true;
/*      */     }
/* 7190 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f9);
/* 7191 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f9(JspTag _jspx_th_html_005fselect_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7196 */     PageContext pageContext = _jspx_page_context;
/* 7197 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7199 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f9 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7200 */     _jspx_th_html_005foptionsCollection_005f9.setPageContext(_jspx_page_context);
/* 7201 */     _jspx_th_html_005foptionsCollection_005f9.setParent((Tag)_jspx_th_html_005fselect_005f9);
/*      */     
/* 7203 */     _jspx_th_html_005foptionsCollection_005f9.setProperty("webserviceArrayAttribute");
/* 7204 */     int _jspx_eval_html_005foptionsCollection_005f9 = _jspx_th_html_005foptionsCollection_005f9.doStartTag();
/* 7205 */     if (_jspx_th_html_005foptionsCollection_005f9.doEndTag() == 5) {
/* 7206 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f9);
/* 7207 */       return true;
/*      */     }
/* 7209 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f9);
/* 7210 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f10(JspTag _jspx_th_html_005fselect_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7215 */     PageContext pageContext = _jspx_page_context;
/* 7216 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7218 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f10 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7219 */     _jspx_th_html_005foptionsCollection_005f10.setPageContext(_jspx_page_context);
/* 7220 */     _jspx_th_html_005foptionsCollection_005f10.setParent((Tag)_jspx_th_html_005fselect_005f10);
/*      */     
/* 7222 */     _jspx_th_html_005foptionsCollection_005f10.setProperty("webservers");
/* 7223 */     int _jspx_eval_html_005foptionsCollection_005f10 = _jspx_th_html_005foptionsCollection_005f10.doStartTag();
/* 7224 */     if (_jspx_th_html_005foptionsCollection_005f10.doEndTag() == 5) {
/* 7225 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f10);
/* 7226 */       return true;
/*      */     }
/* 7228 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f10);
/* 7229 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f11(JspTag _jspx_th_logic_005fnotEmpty_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7234 */     PageContext pageContext = _jspx_page_context;
/* 7235 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7237 */     SelectTag _jspx_th_html_005fselect_005f11 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 7238 */     _jspx_th_html_005fselect_005f11.setPageContext(_jspx_page_context);
/* 7239 */     _jspx_th_html_005fselect_005f11.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f18);
/*      */     
/* 7241 */     _jspx_th_html_005fselect_005f11.setProperty("webserverAttribute");
/*      */     
/* 7243 */     _jspx_th_html_005fselect_005f11.setStyleClass("formtext chosenselect");
/*      */     
/* 7245 */     _jspx_th_html_005fselect_005f11.setStyle("width:250px;line-height:20px");
/*      */     
/* 7247 */     _jspx_th_html_005fselect_005f11.setOnchange("javascript:attributeFormAction(document.forms[1].webservice,this)");
/* 7248 */     int _jspx_eval_html_005fselect_005f11 = _jspx_th_html_005fselect_005f11.doStartTag();
/* 7249 */     if (_jspx_eval_html_005fselect_005f11 != 0) {
/* 7250 */       if (_jspx_eval_html_005fselect_005f11 != 1) {
/* 7251 */         out = _jspx_page_context.pushBody();
/* 7252 */         _jspx_th_html_005fselect_005f11.setBodyContent((BodyContent)out);
/* 7253 */         _jspx_th_html_005fselect_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7256 */         out.write(32);
/* 7257 */         if (_jspx_meth_html_005foptionsCollection_005f11(_jspx_th_html_005fselect_005f11, _jspx_page_context))
/* 7258 */           return true;
/* 7259 */         out.write("\n        \t\t\t\t");
/* 7260 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f11.doAfterBody();
/* 7261 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7264 */       if (_jspx_eval_html_005fselect_005f11 != 1) {
/* 7265 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7268 */     if (_jspx_th_html_005fselect_005f11.doEndTag() == 5) {
/* 7269 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f11);
/* 7270 */       return true;
/*      */     }
/* 7272 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f11);
/* 7273 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f11(JspTag _jspx_th_html_005fselect_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7278 */     PageContext pageContext = _jspx_page_context;
/* 7279 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7281 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f11 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7282 */     _jspx_th_html_005foptionsCollection_005f11.setPageContext(_jspx_page_context);
/* 7283 */     _jspx_th_html_005foptionsCollection_005f11.setParent((Tag)_jspx_th_html_005fselect_005f11);
/*      */     
/* 7285 */     _jspx_th_html_005foptionsCollection_005f11.setProperty("webserverArrayAttribute");
/* 7286 */     int _jspx_eval_html_005foptionsCollection_005f11 = _jspx_th_html_005foptionsCollection_005f11.doStartTag();
/* 7287 */     if (_jspx_th_html_005foptionsCollection_005f11.doEndTag() == 5) {
/* 7288 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f11);
/* 7289 */       return true;
/*      */     }
/* 7291 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f11);
/* 7292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f12(JspTag _jspx_th_html_005fselect_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7297 */     PageContext pageContext = _jspx_page_context;
/* 7298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7300 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f12 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7301 */     _jspx_th_html_005foptionsCollection_005f12.setPageContext(_jspx_page_context);
/* 7302 */     _jspx_th_html_005foptionsCollection_005f12.setParent((Tag)_jspx_th_html_005fselect_005f12);
/*      */     
/* 7304 */     _jspx_th_html_005foptionsCollection_005f12.setProperty("urls");
/* 7305 */     int _jspx_eval_html_005foptionsCollection_005f12 = _jspx_th_html_005foptionsCollection_005f12.doStartTag();
/* 7306 */     if (_jspx_th_html_005foptionsCollection_005f12.doEndTag() == 5) {
/* 7307 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f12);
/* 7308 */       return true;
/*      */     }
/* 7310 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f12);
/* 7311 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f13(JspTag _jspx_th_logic_005fnotEmpty_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7316 */     PageContext pageContext = _jspx_page_context;
/* 7317 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7319 */     SelectTag _jspx_th_html_005fselect_005f13 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 7320 */     _jspx_th_html_005fselect_005f13.setPageContext(_jspx_page_context);
/* 7321 */     _jspx_th_html_005fselect_005f13.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f20);
/*      */     
/* 7323 */     _jspx_th_html_005fselect_005f13.setProperty("urlsAttribute");
/*      */     
/* 7325 */     _jspx_th_html_005fselect_005f13.setStyleClass("formtext chosenselect");
/*      */     
/* 7327 */     _jspx_th_html_005fselect_005f13.setStyle("width:250px;line-height:20px");
/*      */     
/* 7329 */     _jspx_th_html_005fselect_005f13.setOnchange("javascript:attributeFormAction(document.forms[1].webservice,this)");
/* 7330 */     int _jspx_eval_html_005fselect_005f13 = _jspx_th_html_005fselect_005f13.doStartTag();
/* 7331 */     if (_jspx_eval_html_005fselect_005f13 != 0) {
/* 7332 */       if (_jspx_eval_html_005fselect_005f13 != 1) {
/* 7333 */         out = _jspx_page_context.pushBody();
/* 7334 */         _jspx_th_html_005fselect_005f13.setBodyContent((BodyContent)out);
/* 7335 */         _jspx_th_html_005fselect_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7338 */         out.write(32);
/* 7339 */         if (_jspx_meth_html_005foptionsCollection_005f13(_jspx_th_html_005fselect_005f13, _jspx_page_context))
/* 7340 */           return true;
/* 7341 */         out.write("\n        \t\t\t\t");
/* 7342 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f13.doAfterBody();
/* 7343 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7346 */       if (_jspx_eval_html_005fselect_005f13 != 1) {
/* 7347 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7350 */     if (_jspx_th_html_005fselect_005f13.doEndTag() == 5) {
/* 7351 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f13);
/* 7352 */       return true;
/*      */     }
/* 7354 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f13);
/* 7355 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f13(JspTag _jspx_th_html_005fselect_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7360 */     PageContext pageContext = _jspx_page_context;
/* 7361 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7363 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f13 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7364 */     _jspx_th_html_005foptionsCollection_005f13.setPageContext(_jspx_page_context);
/* 7365 */     _jspx_th_html_005foptionsCollection_005f13.setParent((Tag)_jspx_th_html_005fselect_005f13);
/*      */     
/* 7367 */     _jspx_th_html_005foptionsCollection_005f13.setProperty("urlsArrayAttribute");
/* 7368 */     int _jspx_eval_html_005foptionsCollection_005f13 = _jspx_th_html_005foptionsCollection_005f13.doStartTag();
/* 7369 */     if (_jspx_th_html_005foptionsCollection_005f13.doEndTag() == 5) {
/* 7370 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f13);
/* 7371 */       return true;
/*      */     }
/* 7373 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f13);
/* 7374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f14(JspTag _jspx_th_html_005fselect_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7379 */     PageContext pageContext = _jspx_page_context;
/* 7380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7382 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f14 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7383 */     _jspx_th_html_005foptionsCollection_005f14.setPageContext(_jspx_page_context);
/* 7384 */     _jspx_th_html_005foptionsCollection_005f14.setParent((Tag)_jspx_th_html_005fselect_005f14);
/*      */     
/* 7386 */     _jspx_th_html_005foptionsCollection_005f14.setProperty("services");
/* 7387 */     int _jspx_eval_html_005foptionsCollection_005f14 = _jspx_th_html_005foptionsCollection_005f14.doStartTag();
/* 7388 */     if (_jspx_th_html_005foptionsCollection_005f14.doEndTag() == 5) {
/* 7389 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f14);
/* 7390 */       return true;
/*      */     }
/* 7392 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f14);
/* 7393 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f15(JspTag _jspx_th_logic_005fnotEmpty_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7398 */     PageContext pageContext = _jspx_page_context;
/* 7399 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7401 */     SelectTag _jspx_th_html_005fselect_005f15 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 7402 */     _jspx_th_html_005fselect_005f15.setPageContext(_jspx_page_context);
/* 7403 */     _jspx_th_html_005fselect_005f15.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f22);
/*      */     
/* 7405 */     _jspx_th_html_005fselect_005f15.setProperty("servicesAttribute");
/*      */     
/* 7407 */     _jspx_th_html_005fselect_005f15.setStyleClass("formtext chosenselect");
/*      */     
/* 7409 */     _jspx_th_html_005fselect_005f15.setStyle("width:250px;line-height:20px");
/*      */     
/* 7411 */     _jspx_th_html_005fselect_005f15.setOnchange("javascript:attributeFormAction(document.forms[1].service,this)");
/* 7412 */     int _jspx_eval_html_005fselect_005f15 = _jspx_th_html_005fselect_005f15.doStartTag();
/* 7413 */     if (_jspx_eval_html_005fselect_005f15 != 0) {
/* 7414 */       if (_jspx_eval_html_005fselect_005f15 != 1) {
/* 7415 */         out = _jspx_page_context.pushBody();
/* 7416 */         _jspx_th_html_005fselect_005f15.setBodyContent((BodyContent)out);
/* 7417 */         _jspx_th_html_005fselect_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7420 */         out.write(32);
/* 7421 */         if (_jspx_meth_html_005foptionsCollection_005f15(_jspx_th_html_005fselect_005f15, _jspx_page_context))
/* 7422 */           return true;
/* 7423 */         out.write("\n        \t\t\t\t");
/* 7424 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f15.doAfterBody();
/* 7425 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7428 */       if (_jspx_eval_html_005fselect_005f15 != 1) {
/* 7429 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7432 */     if (_jspx_th_html_005fselect_005f15.doEndTag() == 5) {
/* 7433 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f15);
/* 7434 */       return true;
/*      */     }
/* 7436 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f15);
/* 7437 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f15(JspTag _jspx_th_html_005fselect_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7442 */     PageContext pageContext = _jspx_page_context;
/* 7443 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7445 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f15 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7446 */     _jspx_th_html_005foptionsCollection_005f15.setPageContext(_jspx_page_context);
/* 7447 */     _jspx_th_html_005foptionsCollection_005f15.setParent((Tag)_jspx_th_html_005fselect_005f15);
/*      */     
/* 7449 */     _jspx_th_html_005foptionsCollection_005f15.setProperty("servicesArrayAttribute");
/* 7450 */     int _jspx_eval_html_005foptionsCollection_005f15 = _jspx_th_html_005foptionsCollection_005f15.doStartTag();
/* 7451 */     if (_jspx_th_html_005foptionsCollection_005f15.doEndTag() == 5) {
/* 7452 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f15);
/* 7453 */       return true;
/*      */     }
/* 7455 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f15);
/* 7456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f16(JspTag _jspx_th_html_005fselect_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7461 */     PageContext pageContext = _jspx_page_context;
/* 7462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7464 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f16 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7465 */     _jspx_th_html_005foptionsCollection_005f16.setPageContext(_jspx_page_context);
/* 7466 */     _jspx_th_html_005foptionsCollection_005f16.setParent((Tag)_jspx_th_html_005fselect_005f16);
/*      */     
/* 7468 */     _jspx_th_html_005foptionsCollection_005f16.setProperty("mailservers");
/* 7469 */     int _jspx_eval_html_005foptionsCollection_005f16 = _jspx_th_html_005foptionsCollection_005f16.doStartTag();
/* 7470 */     if (_jspx_th_html_005foptionsCollection_005f16.doEndTag() == 5) {
/* 7471 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f16);
/* 7472 */       return true;
/*      */     }
/* 7474 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f16);
/* 7475 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f17(JspTag _jspx_th_logic_005fnotEmpty_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7480 */     PageContext pageContext = _jspx_page_context;
/* 7481 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7483 */     SelectTag _jspx_th_html_005fselect_005f17 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 7484 */     _jspx_th_html_005fselect_005f17.setPageContext(_jspx_page_context);
/* 7485 */     _jspx_th_html_005fselect_005f17.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f24);
/*      */     
/* 7487 */     _jspx_th_html_005fselect_005f17.setProperty("mailserverAttribute");
/*      */     
/* 7489 */     _jspx_th_html_005fselect_005f17.setStyleClass("formtext chosenselect");
/*      */     
/* 7491 */     _jspx_th_html_005fselect_005f17.setStyle("width:250px;line-height:20px");
/*      */     
/* 7493 */     _jspx_th_html_005fselect_005f17.setOnchange("javascript:attributeFormAction(document.forms[1].mailservice,this)");
/* 7494 */     int _jspx_eval_html_005fselect_005f17 = _jspx_th_html_005fselect_005f17.doStartTag();
/* 7495 */     if (_jspx_eval_html_005fselect_005f17 != 0) {
/* 7496 */       if (_jspx_eval_html_005fselect_005f17 != 1) {
/* 7497 */         out = _jspx_page_context.pushBody();
/* 7498 */         _jspx_th_html_005fselect_005f17.setBodyContent((BodyContent)out);
/* 7499 */         _jspx_th_html_005fselect_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7502 */         out.write("\n\t\t\t\t\t");
/* 7503 */         if (_jspx_meth_html_005foptionsCollection_005f17(_jspx_th_html_005fselect_005f17, _jspx_page_context))
/* 7504 */           return true;
/* 7505 */         out.write("\n        \t\t\t\t");
/* 7506 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f17.doAfterBody();
/* 7507 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7510 */       if (_jspx_eval_html_005fselect_005f17 != 1) {
/* 7511 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7514 */     if (_jspx_th_html_005fselect_005f17.doEndTag() == 5) {
/* 7515 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f17);
/* 7516 */       return true;
/*      */     }
/* 7518 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f17);
/* 7519 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f17(JspTag _jspx_th_html_005fselect_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7524 */     PageContext pageContext = _jspx_page_context;
/* 7525 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7527 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f17 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7528 */     _jspx_th_html_005foptionsCollection_005f17.setPageContext(_jspx_page_context);
/* 7529 */     _jspx_th_html_005foptionsCollection_005f17.setParent((Tag)_jspx_th_html_005fselect_005f17);
/*      */     
/* 7531 */     _jspx_th_html_005foptionsCollection_005f17.setProperty("mailserverArrayAttribute");
/* 7532 */     int _jspx_eval_html_005foptionsCollection_005f17 = _jspx_th_html_005foptionsCollection_005f17.doStartTag();
/* 7533 */     if (_jspx_th_html_005foptionsCollection_005f17.doEndTag() == 5) {
/* 7534 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f17);
/* 7535 */       return true;
/*      */     }
/* 7537 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f17);
/* 7538 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f18(JspTag _jspx_th_html_005fselect_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7543 */     PageContext pageContext = _jspx_page_context;
/* 7544 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7546 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f18 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7547 */     _jspx_th_html_005foptionsCollection_005f18.setPageContext(_jspx_page_context);
/* 7548 */     _jspx_th_html_005foptionsCollection_005f18.setParent((Tag)_jspx_th_html_005fselect_005f18);
/*      */     
/* 7550 */     _jspx_th_html_005foptionsCollection_005f18.setProperty("javaservers");
/* 7551 */     int _jspx_eval_html_005foptionsCollection_005f18 = _jspx_th_html_005foptionsCollection_005f18.doStartTag();
/* 7552 */     if (_jspx_th_html_005foptionsCollection_005f18.doEndTag() == 5) {
/* 7553 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f18);
/* 7554 */       return true;
/*      */     }
/* 7556 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f18);
/* 7557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f19(JspTag _jspx_th_logic_005fnotEmpty_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7562 */     PageContext pageContext = _jspx_page_context;
/* 7563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7565 */     SelectTag _jspx_th_html_005fselect_005f19 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 7566 */     _jspx_th_html_005fselect_005f19.setPageContext(_jspx_page_context);
/* 7567 */     _jspx_th_html_005fselect_005f19.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f26);
/*      */     
/* 7569 */     _jspx_th_html_005fselect_005f19.setProperty("transactionAttribute");
/*      */     
/* 7571 */     _jspx_th_html_005fselect_005f19.setStyleClass("formtext");
/*      */     
/* 7573 */     _jspx_th_html_005fselect_005f19.setOnchange("javascript:attributeFormAction(document.forms[1].javaservice,this)");
/* 7574 */     int _jspx_eval_html_005fselect_005f19 = _jspx_th_html_005fselect_005f19.doStartTag();
/* 7575 */     if (_jspx_eval_html_005fselect_005f19 != 0) {
/* 7576 */       if (_jspx_eval_html_005fselect_005f19 != 1) {
/* 7577 */         out = _jspx_page_context.pushBody();
/* 7578 */         _jspx_th_html_005fselect_005f19.setBodyContent((BodyContent)out);
/* 7579 */         _jspx_th_html_005fselect_005f19.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7582 */         out.write(32);
/* 7583 */         if (_jspx_meth_html_005foptionsCollection_005f19(_jspx_th_html_005fselect_005f19, _jspx_page_context))
/* 7584 */           return true;
/* 7585 */         out.write("\n\t\t\t\t\t");
/* 7586 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f19.doAfterBody();
/* 7587 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7590 */       if (_jspx_eval_html_005fselect_005f19 != 1) {
/* 7591 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7594 */     if (_jspx_th_html_005fselect_005f19.doEndTag() == 5) {
/* 7595 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f19);
/* 7596 */       return true;
/*      */     }
/* 7598 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f19);
/* 7599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f19(JspTag _jspx_th_html_005fselect_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7604 */     PageContext pageContext = _jspx_page_context;
/* 7605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7607 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f19 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7608 */     _jspx_th_html_005foptionsCollection_005f19.setPageContext(_jspx_page_context);
/* 7609 */     _jspx_th_html_005foptionsCollection_005f19.setParent((Tag)_jspx_th_html_005fselect_005f19);
/*      */     
/* 7611 */     _jspx_th_html_005foptionsCollection_005f19.setProperty("transactionArrayAttribute");
/* 7612 */     int _jspx_eval_html_005foptionsCollection_005f19 = _jspx_th_html_005foptionsCollection_005f19.doStartTag();
/* 7613 */     if (_jspx_th_html_005foptionsCollection_005f19.doEndTag() == 5) {
/* 7614 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f19);
/* 7615 */       return true;
/*      */     }
/* 7617 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f19);
/* 7618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f20(JspTag _jspx_th_html_005fselect_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7623 */     PageContext pageContext = _jspx_page_context;
/* 7624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7626 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f20 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7627 */     _jspx_th_html_005foptionsCollection_005f20.setPageContext(_jspx_page_context);
/* 7628 */     _jspx_th_html_005foptionsCollection_005f20.setParent((Tag)_jspx_th_html_005fselect_005f20);
/*      */     
/* 7630 */     _jspx_th_html_005foptionsCollection_005f20.setProperty("sapservers");
/* 7631 */     int _jspx_eval_html_005foptionsCollection_005f20 = _jspx_th_html_005foptionsCollection_005f20.doStartTag();
/* 7632 */     if (_jspx_th_html_005foptionsCollection_005f20.doEndTag() == 5) {
/* 7633 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f20);
/* 7634 */       return true;
/*      */     }
/* 7636 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f20);
/* 7637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f21(JspTag _jspx_th_logic_005fnotEmpty_005f28, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7642 */     PageContext pageContext = _jspx_page_context;
/* 7643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7645 */     SelectTag _jspx_th_html_005fselect_005f21 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 7646 */     _jspx_th_html_005fselect_005f21.setPageContext(_jspx_page_context);
/* 7647 */     _jspx_th_html_005fselect_005f21.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f28);
/*      */     
/* 7649 */     _jspx_th_html_005fselect_005f21.setProperty("erpAttribute");
/*      */     
/* 7651 */     _jspx_th_html_005fselect_005f21.setStyleClass("formtext chosenselect");
/*      */     
/* 7653 */     _jspx_th_html_005fselect_005f21.setStyle("width:250px;line-height:20px");
/*      */     
/* 7655 */     _jspx_th_html_005fselect_005f21.setOnchange("javascript:attributeFormAction(document.forms[1].sapservice,this)");
/* 7656 */     int _jspx_eval_html_005fselect_005f21 = _jspx_th_html_005fselect_005f21.doStartTag();
/* 7657 */     if (_jspx_eval_html_005fselect_005f21 != 0) {
/* 7658 */       if (_jspx_eval_html_005fselect_005f21 != 1) {
/* 7659 */         out = _jspx_page_context.pushBody();
/* 7660 */         _jspx_th_html_005fselect_005f21.setBodyContent((BodyContent)out);
/* 7661 */         _jspx_th_html_005fselect_005f21.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7664 */         out.write(32);
/* 7665 */         if (_jspx_meth_html_005foptionsCollection_005f21(_jspx_th_html_005fselect_005f21, _jspx_page_context))
/* 7666 */           return true;
/* 7667 */         out.write("\n        \t\t\t\t");
/* 7668 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f21.doAfterBody();
/* 7669 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7672 */       if (_jspx_eval_html_005fselect_005f21 != 1) {
/* 7673 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7676 */     if (_jspx_th_html_005fselect_005f21.doEndTag() == 5) {
/* 7677 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f21);
/* 7678 */       return true;
/*      */     }
/* 7680 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f21);
/* 7681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f21(JspTag _jspx_th_html_005fselect_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7686 */     PageContext pageContext = _jspx_page_context;
/* 7687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7689 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f21 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7690 */     _jspx_th_html_005foptionsCollection_005f21.setPageContext(_jspx_page_context);
/* 7691 */     _jspx_th_html_005foptionsCollection_005f21.setParent((Tag)_jspx_th_html_005fselect_005f21);
/*      */     
/* 7693 */     _jspx_th_html_005foptionsCollection_005f21.setProperty("erpArrayAttribute");
/* 7694 */     int _jspx_eval_html_005foptionsCollection_005f21 = _jspx_th_html_005foptionsCollection_005f21.doStartTag();
/* 7695 */     if (_jspx_th_html_005foptionsCollection_005f21.doEndTag() == 5) {
/* 7696 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f21);
/* 7697 */       return true;
/*      */     }
/* 7699 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f21);
/* 7700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7705 */     PageContext pageContext = _jspx_page_context;
/* 7706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7708 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 7709 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 7710 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 7712 */     _jspx_th_c_005fset_005f0.setVar("ccmsaname");
/* 7713 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 7714 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 7715 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 7716 */         out = _jspx_page_context.pushBody();
/* 7717 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 7718 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 7719 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7722 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7723 */           return true;
/* 7724 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 7725 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7728 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 7729 */         out = _jspx_page_context.popBody();
/* 7730 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 7733 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 7734 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 7735 */       return true;
/*      */     }
/* 7737 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 7738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7743 */     PageContext pageContext = _jspx_page_context;
/* 7744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7746 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7747 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 7748 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 7750 */     _jspx_th_c_005fout_005f1.setValue("${row.NAME}");
/* 7751 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 7752 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 7753 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 7754 */       return true;
/*      */     }
/* 7756 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 7757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7762 */     PageContext pageContext = _jspx_page_context;
/* 7763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7765 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 7766 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 7767 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 7769 */     _jspx_th_c_005fset_005f1.setVar("ccmsmbname");
/* 7770 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 7771 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 7772 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 7773 */         out = _jspx_page_context.pushBody();
/* 7774 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 7775 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 7776 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7779 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 7780 */           return true;
/* 7781 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 7782 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7785 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 7786 */         out = _jspx_page_context.popBody();
/* 7787 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 7790 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 7791 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 7792 */       return true;
/*      */     }
/* 7794 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 7795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7800 */     PageContext pageContext = _jspx_page_context;
/* 7801 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7803 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7804 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 7805 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 7807 */     _jspx_th_c_005fout_005f2.setValue("${row.GROUPNAME}");
/* 7808 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 7809 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 7810 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 7811 */       return true;
/*      */     }
/* 7813 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 7814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7819 */     PageContext pageContext = _jspx_page_context;
/* 7820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7822 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7823 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 7824 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 7826 */     _jspx_th_c_005fout_005f3.setValue("${row.DISPNAME}");
/* 7827 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 7828 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 7829 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 7830 */       return true;
/*      */     }
/* 7832 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 7833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7838 */     PageContext pageContext = _jspx_page_context;
/* 7839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7841 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7842 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 7843 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 7845 */     _jspx_th_c_005fout_005f4.setValue("${row.ATTRIBUTEID}");
/* 7846 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 7847 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 7848 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7849 */       return true;
/*      */     }
/* 7851 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7857 */     PageContext pageContext = _jspx_page_context;
/* 7858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7860 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7861 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 7862 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 7864 */     _jspx_th_c_005fout_005f5.setValue("${row.DISPNAME}");
/* 7865 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 7866 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 7867 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 7868 */       return true;
/*      */     }
/* 7870 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 7871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7876 */     PageContext pageContext = _jspx_page_context;
/* 7877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7879 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7880 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 7881 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 7883 */     _jspx_th_c_005fout_005f6.setValue("${row.NAME}");
/* 7884 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 7885 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 7886 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7887 */       return true;
/*      */     }
/* 7889 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7895 */     PageContext pageContext = _jspx_page_context;
/* 7896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7898 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7899 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 7900 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 7902 */     _jspx_th_c_005fout_005f7.setValue("${row.GROUPNAME}");
/* 7903 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 7904 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 7905 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7906 */       return true;
/*      */     }
/* 7908 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f22(JspTag _jspx_th_html_005fselect_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7914 */     PageContext pageContext = _jspx_page_context;
/* 7915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7917 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f22 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7918 */     _jspx_th_html_005foptionsCollection_005f22.setPageContext(_jspx_page_context);
/* 7919 */     _jspx_th_html_005foptionsCollection_005f22.setParent((Tag)_jspx_th_html_005fselect_005f22);
/*      */     
/* 7921 */     _jspx_th_html_005foptionsCollection_005f22.setProperty("middleware");
/* 7922 */     int _jspx_eval_html_005foptionsCollection_005f22 = _jspx_th_html_005foptionsCollection_005f22.doStartTag();
/* 7923 */     if (_jspx_th_html_005foptionsCollection_005f22.doEndTag() == 5) {
/* 7924 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f22);
/* 7925 */       return true;
/*      */     }
/* 7927 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f22);
/* 7928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f23(JspTag _jspx_th_logic_005fnotEmpty_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7933 */     PageContext pageContext = _jspx_page_context;
/* 7934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7936 */     SelectTag _jspx_th_html_005fselect_005f23 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.get(SelectTag.class);
/* 7937 */     _jspx_th_html_005fselect_005f23.setPageContext(_jspx_page_context);
/* 7938 */     _jspx_th_html_005fselect_005f23.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f31);
/*      */     
/* 7940 */     _jspx_th_html_005fselect_005f23.setStyle("width:320px;line-height:20px");
/*      */     
/* 7942 */     _jspx_th_html_005fselect_005f23.setProperty("momAttribute");
/*      */     
/* 7944 */     _jspx_th_html_005fselect_005f23.setStyleClass("formtext chosenselect");
/*      */     
/* 7946 */     _jspx_th_html_005fselect_005f23.setOnmousedown("if(navigator.appName=='Microsoft Internet Explorer'){this.style.width='auto'}");
/*      */     
/* 7948 */     _jspx_th_html_005fselect_005f23.setOnblur("this.style.width='320px'");
/*      */     
/* 7950 */     _jspx_th_html_005fselect_005f23.setOnchange("this.style.width='320px';javascript:attributeFormAction(document.forms[1].middlewareserver,this)");
/* 7951 */     int _jspx_eval_html_005fselect_005f23 = _jspx_th_html_005fselect_005f23.doStartTag();
/* 7952 */     if (_jspx_eval_html_005fselect_005f23 != 0) {
/* 7953 */       if (_jspx_eval_html_005fselect_005f23 != 1) {
/* 7954 */         out = _jspx_page_context.pushBody();
/* 7955 */         _jspx_th_html_005fselect_005f23.setBodyContent((BodyContent)out);
/* 7956 */         _jspx_th_html_005fselect_005f23.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7959 */         out.write(32);
/* 7960 */         if (_jspx_meth_html_005foptionsCollection_005f23(_jspx_th_html_005fselect_005f23, _jspx_page_context))
/* 7961 */           return true;
/* 7962 */         out.write("\n        \t\t\t\t");
/* 7963 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f23.doAfterBody();
/* 7964 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7967 */       if (_jspx_eval_html_005fselect_005f23 != 1) {
/* 7968 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7971 */     if (_jspx_th_html_005fselect_005f23.doEndTag() == 5) {
/* 7972 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f23);
/* 7973 */       return true;
/*      */     }
/* 7975 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f23);
/* 7976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f23(JspTag _jspx_th_html_005fselect_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7981 */     PageContext pageContext = _jspx_page_context;
/* 7982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7984 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f23 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 7985 */     _jspx_th_html_005foptionsCollection_005f23.setPageContext(_jspx_page_context);
/* 7986 */     _jspx_th_html_005foptionsCollection_005f23.setParent((Tag)_jspx_th_html_005fselect_005f23);
/*      */     
/* 7988 */     _jspx_th_html_005foptionsCollection_005f23.setProperty("momArrayAttribute");
/* 7989 */     int _jspx_eval_html_005foptionsCollection_005f23 = _jspx_th_html_005foptionsCollection_005f23.doStartTag();
/* 7990 */     if (_jspx_th_html_005foptionsCollection_005f23.doEndTag() == 5) {
/* 7991 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f23);
/* 7992 */       return true;
/*      */     }
/* 7994 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f23);
/* 7995 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f24(JspTag _jspx_th_logic_005fnotEmpty_005f32, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8000 */     PageContext pageContext = _jspx_page_context;
/* 8001 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8003 */     SelectTag _jspx_th_html_005fselect_005f24 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.get(SelectTag.class);
/* 8004 */     _jspx_th_html_005fselect_005f24.setPageContext(_jspx_page_context);
/* 8005 */     _jspx_th_html_005fselect_005f24.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f32);
/*      */     
/* 8007 */     _jspx_th_html_005fselect_005f24.setStyle("width:320px");
/*      */     
/* 8009 */     _jspx_th_html_005fselect_005f24.setProperty("momAttribute");
/*      */     
/* 8011 */     _jspx_th_html_005fselect_005f24.setStyleClass("formtext");
/*      */     
/* 8013 */     _jspx_th_html_005fselect_005f24.setOnmousedown("if(navigator.appName=='Microsoft Internet Explorer'){this.style.width='auto'}");
/*      */     
/* 8015 */     _jspx_th_html_005fselect_005f24.setOnblur("this.style.width='320px'");
/*      */     
/* 8017 */     _jspx_th_html_005fselect_005f24.setOnchange("this.style.width='320px';javascript:attributeFormAction(document.forms[1].middlewareserver,this)");
/* 8018 */     int _jspx_eval_html_005fselect_005f24 = _jspx_th_html_005fselect_005f24.doStartTag();
/* 8019 */     if (_jspx_eval_html_005fselect_005f24 != 0) {
/* 8020 */       if (_jspx_eval_html_005fselect_005f24 != 1) {
/* 8021 */         out = _jspx_page_context.pushBody();
/* 8022 */         _jspx_th_html_005fselect_005f24.setBodyContent((BodyContent)out);
/* 8023 */         _jspx_th_html_005fselect_005f24.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8026 */         out.write(32);
/* 8027 */         if (_jspx_meth_html_005foptionsCollection_005f24(_jspx_th_html_005fselect_005f24, _jspx_page_context))
/* 8028 */           return true;
/* 8029 */         out.write("\n        \t\t\t\t");
/* 8030 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f24.doAfterBody();
/* 8031 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8034 */       if (_jspx_eval_html_005fselect_005f24 != 1) {
/* 8035 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8038 */     if (_jspx_th_html_005fselect_005f24.doEndTag() == 5) {
/* 8039 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f24);
/* 8040 */       return true;
/*      */     }
/* 8042 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonmousedown_005fonchange_005fonblur.reuse(_jspx_th_html_005fselect_005f24);
/* 8043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f24(JspTag _jspx_th_html_005fselect_005f24, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8048 */     PageContext pageContext = _jspx_page_context;
/* 8049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8051 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f24 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8052 */     _jspx_th_html_005foptionsCollection_005f24.setPageContext(_jspx_page_context);
/* 8053 */     _jspx_th_html_005foptionsCollection_005f24.setParent((Tag)_jspx_th_html_005fselect_005f24);
/*      */     
/* 8055 */     _jspx_th_html_005foptionsCollection_005f24.setProperty("momArrayAttribute");
/* 8056 */     int _jspx_eval_html_005foptionsCollection_005f24 = _jspx_th_html_005foptionsCollection_005f24.doStartTag();
/* 8057 */     if (_jspx_th_html_005foptionsCollection_005f24.doEndTag() == 5) {
/* 8058 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f24);
/* 8059 */       return true;
/*      */     }
/* 8061 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f24);
/* 8062 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8067 */     PageContext pageContext = _jspx_page_context;
/* 8068 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8070 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 8071 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 8072 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 8074 */     _jspx_th_c_005fset_005f2.setVar("aname");
/* 8075 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 8076 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 8077 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 8078 */         out = _jspx_page_context.pushBody();
/* 8079 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 8080 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 8081 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8084 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 8085 */           return true;
/* 8086 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 8087 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8090 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 8091 */         out = _jspx_page_context.popBody();
/* 8092 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 8095 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 8096 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 8097 */       return true;
/*      */     }
/* 8099 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 8100 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8105 */     PageContext pageContext = _jspx_page_context;
/* 8106 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8108 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8109 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 8110 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 8112 */     _jspx_th_c_005fout_005f8.setValue("${row.NAME}");
/* 8113 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 8114 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 8115 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 8116 */       return true;
/*      */     }
/* 8118 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 8119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8124 */     PageContext pageContext = _jspx_page_context;
/* 8125 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8127 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 8128 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 8129 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 8131 */     _jspx_th_c_005fset_005f3.setVar("mbname");
/* 8132 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 8133 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 8134 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 8135 */         out = _jspx_page_context.pushBody();
/* 8136 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 8137 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 8138 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8141 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fset_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 8142 */           return true;
/* 8143 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 8144 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8147 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 8148 */         out = _jspx_page_context.popBody();
/* 8149 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 8152 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 8153 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 8154 */       return true;
/*      */     }
/* 8156 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 8157 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8162 */     PageContext pageContext = _jspx_page_context;
/* 8163 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8165 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8166 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 8167 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 8169 */     _jspx_th_c_005fout_005f9.setValue("${row.GROUPNAME}");
/* 8170 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 8171 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 8172 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 8173 */       return true;
/*      */     }
/* 8175 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 8176 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8181 */     PageContext pageContext = _jspx_page_context;
/* 8182 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8184 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8185 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 8186 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 8188 */     _jspx_th_c_005fout_005f10.setValue("${row.ATTRIBUTENAME}");
/* 8189 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 8190 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 8191 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 8192 */       return true;
/*      */     }
/* 8194 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 8195 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8200 */     PageContext pageContext = _jspx_page_context;
/* 8201 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8203 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8204 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 8205 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 8207 */     _jspx_th_c_005fout_005f11.setValue("${row.ATTRIBUTEID}");
/* 8208 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 8209 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 8210 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 8211 */       return true;
/*      */     }
/* 8213 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 8214 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8219 */     PageContext pageContext = _jspx_page_context;
/* 8220 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8222 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8223 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 8224 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 8226 */     _jspx_th_c_005fout_005f12.setValue("${row.RESOURCEID}");
/* 8227 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 8228 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 8229 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 8230 */       return true;
/*      */     }
/* 8232 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 8233 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8238 */     PageContext pageContext = _jspx_page_context;
/* 8239 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8241 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8242 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 8243 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 8245 */     _jspx_th_c_005fout_005f13.setValue("${row.ATTRIBUTENAME}");
/* 8246 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 8247 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 8248 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 8249 */       return true;
/*      */     }
/* 8251 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 8252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 8257 */     PageContext pageContext = _jspx_page_context;
/* 8258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8260 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 8261 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 8262 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 8264 */     _jspx_th_c_005fout_005f14.setValue("${row.DISPNAME}");
/* 8265 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 8266 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 8267 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 8268 */       return true;
/*      */     }
/* 8270 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 8271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f25(JspTag _jspx_th_html_005fselect_005f25, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8276 */     PageContext pageContext = _jspx_page_context;
/* 8277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8279 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f25 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8280 */     _jspx_th_html_005foptionsCollection_005f25.setPageContext(_jspx_page_context);
/* 8281 */     _jspx_th_html_005foptionsCollection_005f25.setParent((Tag)_jspx_th_html_005fselect_005f25);
/*      */     
/* 8283 */     _jspx_th_html_005foptionsCollection_005f25.setProperty("virtualservers");
/* 8284 */     int _jspx_eval_html_005foptionsCollection_005f25 = _jspx_th_html_005foptionsCollection_005f25.doStartTag();
/* 8285 */     if (_jspx_th_html_005foptionsCollection_005f25.doEndTag() == 5) {
/* 8286 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f25);
/* 8287 */       return true;
/*      */     }
/* 8289 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f25);
/* 8290 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f26(JspTag _jspx_th_logic_005fnotEmpty_005f35, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8295 */     PageContext pageContext = _jspx_page_context;
/* 8296 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8298 */     SelectTag _jspx_th_html_005fselect_005f26 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 8299 */     _jspx_th_html_005fselect_005f26.setPageContext(_jspx_page_context);
/* 8300 */     _jspx_th_html_005fselect_005f26.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f35);
/*      */     
/* 8302 */     _jspx_th_html_005fselect_005f26.setProperty("virserverAttribute");
/*      */     
/* 8304 */     _jspx_th_html_005fselect_005f26.setStyleClass("formtext chosenselect");
/*      */     
/* 8306 */     _jspx_th_html_005fselect_005f26.setStyle("width:250px;line-height:20px");
/*      */     
/* 8308 */     _jspx_th_html_005fselect_005f26.setOnchange("javascript:attributeFormAction(document.forms[1].virserver,this)");
/* 8309 */     int _jspx_eval_html_005fselect_005f26 = _jspx_th_html_005fselect_005f26.doStartTag();
/* 8310 */     if (_jspx_eval_html_005fselect_005f26 != 0) {
/* 8311 */       if (_jspx_eval_html_005fselect_005f26 != 1) {
/* 8312 */         out = _jspx_page_context.pushBody();
/* 8313 */         _jspx_th_html_005fselect_005f26.setBodyContent((BodyContent)out);
/* 8314 */         _jspx_th_html_005fselect_005f26.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8317 */         out.write(32);
/* 8318 */         if (_jspx_meth_html_005foptionsCollection_005f26(_jspx_th_html_005fselect_005f26, _jspx_page_context))
/* 8319 */           return true;
/* 8320 */         out.write("\n        \t\t\t\t");
/* 8321 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f26.doAfterBody();
/* 8322 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8325 */       if (_jspx_eval_html_005fselect_005f26 != 1) {
/* 8326 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8329 */     if (_jspx_th_html_005fselect_005f26.doEndTag() == 5) {
/* 8330 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f26);
/* 8331 */       return true;
/*      */     }
/* 8333 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f26);
/* 8334 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f26(JspTag _jspx_th_html_005fselect_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8339 */     PageContext pageContext = _jspx_page_context;
/* 8340 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8342 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f26 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8343 */     _jspx_th_html_005foptionsCollection_005f26.setPageContext(_jspx_page_context);
/* 8344 */     _jspx_th_html_005foptionsCollection_005f26.setParent((Tag)_jspx_th_html_005fselect_005f26);
/*      */     
/* 8346 */     _jspx_th_html_005foptionsCollection_005f26.setProperty("virserverArrayAttribute");
/* 8347 */     int _jspx_eval_html_005foptionsCollection_005f26 = _jspx_th_html_005foptionsCollection_005f26.doStartTag();
/* 8348 */     if (_jspx_th_html_005foptionsCollection_005f26.doEndTag() == 5) {
/* 8349 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f26);
/* 8350 */       return true;
/*      */     }
/* 8352 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f26);
/* 8353 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f27(JspTag _jspx_th_logic_005fnotEmpty_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8358 */     PageContext pageContext = _jspx_page_context;
/* 8359 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8361 */     SelectTag _jspx_th_html_005fselect_005f27 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 8362 */     _jspx_th_html_005fselect_005f27.setPageContext(_jspx_page_context);
/* 8363 */     _jspx_th_html_005fselect_005f27.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f36);
/*      */     
/* 8365 */     _jspx_th_html_005fselect_005f27.setProperty("capacityserver");
/*      */     
/* 8367 */     _jspx_th_html_005fselect_005f27.setOnchange("javascript:capacityPlanningAction(document.forms[1].capacityserver.value)");
/*      */     
/* 8369 */     _jspx_th_html_005fselect_005f27.setStyleClass("formtext normal");
/* 8370 */     int _jspx_eval_html_005fselect_005f27 = _jspx_th_html_005fselect_005f27.doStartTag();
/* 8371 */     if (_jspx_eval_html_005fselect_005f27 != 0) {
/* 8372 */       if (_jspx_eval_html_005fselect_005f27 != 1) {
/* 8373 */         out = _jspx_page_context.pushBody();
/* 8374 */         _jspx_th_html_005fselect_005f27.setBodyContent((BodyContent)out);
/* 8375 */         _jspx_th_html_005fselect_005f27.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8378 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 8379 */         if (_jspx_meth_html_005foptionsCollection_005f27(_jspx_th_html_005fselect_005f27, _jspx_page_context))
/* 8380 */           return true;
/* 8381 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 8382 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f27.doAfterBody();
/* 8383 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8386 */       if (_jspx_eval_html_005fselect_005f27 != 1) {
/* 8387 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8390 */     if (_jspx_th_html_005fselect_005f27.doEndTag() == 5) {
/* 8391 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f27);
/* 8392 */       return true;
/*      */     }
/* 8394 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f27);
/* 8395 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f27(JspTag _jspx_th_html_005fselect_005f27, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8400 */     PageContext pageContext = _jspx_page_context;
/* 8401 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8403 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f27 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8404 */     _jspx_th_html_005foptionsCollection_005f27.setPageContext(_jspx_page_context);
/* 8405 */     _jspx_th_html_005foptionsCollection_005f27.setParent((Tag)_jspx_th_html_005fselect_005f27);
/*      */     
/* 8407 */     _jspx_th_html_005foptionsCollection_005f27.setProperty("capacityServers");
/* 8408 */     int _jspx_eval_html_005foptionsCollection_005f27 = _jspx_th_html_005foptionsCollection_005f27.doStartTag();
/* 8409 */     if (_jspx_th_html_005foptionsCollection_005f27.doEndTag() == 5) {
/* 8410 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f27);
/* 8411 */       return true;
/*      */     }
/* 8413 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f27);
/* 8414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f23(JspTag _jspx_th_logic_005fnotEmpty_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8419 */     PageContext pageContext = _jspx_page_context;
/* 8420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8422 */     HiddenTag _jspx_th_html_005fhidden_005f23 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 8423 */     _jspx_th_html_005fhidden_005f23.setPageContext(_jspx_page_context);
/* 8424 */     _jspx_th_html_005fhidden_005f23.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f36);
/*      */     
/* 8426 */     _jspx_th_html_005fhidden_005f23.setProperty("mgCapacity");
/*      */     
/* 8428 */     _jspx_th_html_005fhidden_005f23.setValue("nomgs");
/* 8429 */     int _jspx_eval_html_005fhidden_005f23 = _jspx_th_html_005fhidden_005f23.doStartTag();
/* 8430 */     if (_jspx_th_html_005fhidden_005f23.doEndTag() == 5) {
/* 8431 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f23);
/* 8432 */       return true;
/*      */     }
/* 8434 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f23);
/* 8435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f28(JspTag _jspx_th_html_005fselect_005f28, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8440 */     PageContext pageContext = _jspx_page_context;
/* 8441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8443 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f28 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8444 */     _jspx_th_html_005foptionsCollection_005f28.setPageContext(_jspx_page_context);
/* 8445 */     _jspx_th_html_005foptionsCollection_005f28.setParent((Tag)_jspx_th_html_005fselect_005f28);
/*      */     
/* 8447 */     _jspx_th_html_005foptionsCollection_005f28.setProperty("cloudApps");
/* 8448 */     int _jspx_eval_html_005foptionsCollection_005f28 = _jspx_th_html_005foptionsCollection_005f28.doStartTag();
/* 8449 */     if (_jspx_th_html_005foptionsCollection_005f28.doEndTag() == 5) {
/* 8450 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f28);
/* 8451 */       return true;
/*      */     }
/* 8453 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f28);
/* 8454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f29(JspTag _jspx_th_logic_005fnotEmpty_005f38, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8459 */     PageContext pageContext = _jspx_page_context;
/* 8460 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8462 */     SelectTag _jspx_th_html_005fselect_005f29 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 8463 */     _jspx_th_html_005fselect_005f29.setPageContext(_jspx_page_context);
/* 8464 */     _jspx_th_html_005fselect_005f29.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f38);
/*      */     
/* 8466 */     _jspx_th_html_005fselect_005f29.setProperty("cloudAppsAttribute");
/*      */     
/* 8468 */     _jspx_th_html_005fselect_005f29.setStyleClass("formtext chosenselect");
/*      */     
/* 8470 */     _jspx_th_html_005fselect_005f29.setStyle("width:250px;line-height:20px");
/*      */     
/* 8472 */     _jspx_th_html_005fselect_005f29.setOnchange("javascript:attributeFormAction(document.forms[1].cldApps,this)");
/* 8473 */     int _jspx_eval_html_005fselect_005f29 = _jspx_th_html_005fselect_005f29.doStartTag();
/* 8474 */     if (_jspx_eval_html_005fselect_005f29 != 0) {
/* 8475 */       if (_jspx_eval_html_005fselect_005f29 != 1) {
/* 8476 */         out = _jspx_page_context.pushBody();
/* 8477 */         _jspx_th_html_005fselect_005f29.setBodyContent((BodyContent)out);
/* 8478 */         _jspx_th_html_005fselect_005f29.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8481 */         out.write(32);
/* 8482 */         if (_jspx_meth_html_005foptionsCollection_005f29(_jspx_th_html_005fselect_005f29, _jspx_page_context))
/* 8483 */           return true;
/* 8484 */         out.write("\n        \t\t\t\t");
/* 8485 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f29.doAfterBody();
/* 8486 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8489 */       if (_jspx_eval_html_005fselect_005f29 != 1) {
/* 8490 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8493 */     if (_jspx_th_html_005fselect_005f29.doEndTag() == 5) {
/* 8494 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f29);
/* 8495 */       return true;
/*      */     }
/* 8497 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f29);
/* 8498 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f29(JspTag _jspx_th_html_005fselect_005f29, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8503 */     PageContext pageContext = _jspx_page_context;
/* 8504 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8506 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f29 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8507 */     _jspx_th_html_005foptionsCollection_005f29.setPageContext(_jspx_page_context);
/* 8508 */     _jspx_th_html_005foptionsCollection_005f29.setParent((Tag)_jspx_th_html_005fselect_005f29);
/*      */     
/* 8510 */     _jspx_th_html_005foptionsCollection_005f29.setProperty("cloudAppsArrayAttribute");
/* 8511 */     int _jspx_eval_html_005foptionsCollection_005f29 = _jspx_th_html_005foptionsCollection_005f29.doStartTag();
/* 8512 */     if (_jspx_th_html_005foptionsCollection_005f29.doEndTag() == 5) {
/* 8513 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f29);
/* 8514 */       return true;
/*      */     }
/* 8516 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f29);
/* 8517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f30(JspTag _jspx_th_logic_005fnotEmpty_005f39, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8522 */     PageContext pageContext = _jspx_page_context;
/* 8523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8525 */     SelectTag _jspx_th_html_005fselect_005f30 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 8526 */     _jspx_th_html_005fselect_005f30.setPageContext(_jspx_page_context);
/* 8527 */     _jspx_th_html_005fselect_005f30.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f39);
/*      */     
/* 8529 */     _jspx_th_html_005fselect_005f30.setProperty("eummonitors");
/*      */     
/* 8531 */     _jspx_th_html_005fselect_005f30.setStyleClass("EUMServiecsContent");
/* 8532 */     int _jspx_eval_html_005fselect_005f30 = _jspx_th_html_005fselect_005f30.doStartTag();
/* 8533 */     if (_jspx_eval_html_005fselect_005f30 != 0) {
/* 8534 */       if (_jspx_eval_html_005fselect_005f30 != 1) {
/* 8535 */         out = _jspx_page_context.pushBody();
/* 8536 */         _jspx_th_html_005fselect_005f30.setBodyContent((BodyContent)out);
/* 8537 */         _jspx_th_html_005fselect_005f30.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8540 */         out.write("\n\t\t\t\t\t\t\t");
/* 8541 */         if (_jspx_meth_html_005foptionsCollection_005f30(_jspx_th_html_005fselect_005f30, _jspx_page_context))
/* 8542 */           return true;
/* 8543 */         out.write("\n      \t\t\t\t\t");
/* 8544 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f30.doAfterBody();
/* 8545 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8548 */       if (_jspx_eval_html_005fselect_005f30 != 1) {
/* 8549 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8552 */     if (_jspx_th_html_005fselect_005f30.doEndTag() == 5) {
/* 8553 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f30);
/* 8554 */       return true;
/*      */     }
/* 8556 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f30);
/* 8557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f30(JspTag _jspx_th_html_005fselect_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8562 */     PageContext pageContext = _jspx_page_context;
/* 8563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8565 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f30 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8566 */     _jspx_th_html_005foptionsCollection_005f30.setPageContext(_jspx_page_context);
/* 8567 */     _jspx_th_html_005foptionsCollection_005f30.setParent((Tag)_jspx_th_html_005fselect_005f30);
/*      */     
/* 8569 */     _jspx_th_html_005foptionsCollection_005f30.setProperty("eummonitors");
/* 8570 */     int _jspx_eval_html_005foptionsCollection_005f30 = _jspx_th_html_005foptionsCollection_005f30.doStartTag();
/* 8571 */     if (_jspx_th_html_005foptionsCollection_005f30.doEndTag() == 5) {
/* 8572 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f30);
/* 8573 */       return true;
/*      */     }
/* 8575 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f30);
/* 8576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f31(JspTag _jspx_th_logic_005fnotEmpty_005f40, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8581 */     PageContext pageContext = _jspx_page_context;
/* 8582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8584 */     SelectTag _jspx_th_html_005fselect_005f31 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 8585 */     _jspx_th_html_005fselect_005f31.setPageContext(_jspx_page_context);
/* 8586 */     _jspx_th_html_005fselect_005f31.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f40);
/*      */     
/* 8588 */     _jspx_th_html_005fselect_005f31.setProperty("selectedMonitorType");
/*      */     
/* 8590 */     _jspx_th_html_005fselect_005f31.setStyleClass("formtext chosenselect");
/*      */     
/* 8592 */     _jspx_th_html_005fselect_005f31.setStyle("width:200px; height:22px;");
/*      */     
/* 8594 */     _jspx_th_html_005fselect_005f31.setOnchange("getForecastAttribute(this)");
/* 8595 */     int _jspx_eval_html_005fselect_005f31 = _jspx_th_html_005fselect_005f31.doStartTag();
/* 8596 */     if (_jspx_eval_html_005fselect_005f31 != 0) {
/* 8597 */       if (_jspx_eval_html_005fselect_005f31 != 1) {
/* 8598 */         out = _jspx_page_context.pushBody();
/* 8599 */         _jspx_th_html_005fselect_005f31.setBodyContent((BodyContent)out);
/* 8600 */         _jspx_th_html_005fselect_005f31.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 8603 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 8604 */         if (_jspx_meth_html_005foptionsCollection_005f31(_jspx_th_html_005fselect_005f31, _jspx_page_context))
/* 8605 */           return true;
/* 8606 */         out.write("\n\t\t\t\t\t\t    \t\t\t");
/* 8607 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f31.doAfterBody();
/* 8608 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 8611 */       if (_jspx_eval_html_005fselect_005f31 != 1) {
/* 8612 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 8615 */     if (_jspx_th_html_005fselect_005f31.doEndTag() == 5) {
/* 8616 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f31);
/* 8617 */       return true;
/*      */     }
/* 8619 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f31);
/* 8620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f31(JspTag _jspx_th_html_005fselect_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 8625 */     PageContext pageContext = _jspx_page_context;
/* 8626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 8628 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f31 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 8629 */     _jspx_th_html_005foptionsCollection_005f31.setPageContext(_jspx_page_context);
/* 8630 */     _jspx_th_html_005foptionsCollection_005f31.setParent((Tag)_jspx_th_html_005fselect_005f31);
/*      */     
/* 8632 */     _jspx_th_html_005foptionsCollection_005f31.setProperty("availableMonTypes");
/* 8633 */     int _jspx_eval_html_005foptionsCollection_005f31 = _jspx_th_html_005foptionsCollection_005f31.doStartTag();
/* 8634 */     if (_jspx_th_html_005foptionsCollection_005f31.doEndTag() == 5) {
/* 8635 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f31);
/* 8636 */       return true;
/*      */     }
/* 8638 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f31);
/* 8639 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\ReportList_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */