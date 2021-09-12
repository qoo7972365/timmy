/*      */ package org.apache.jsp.webclient.fault.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.rcatree.AMRCATreeCreator;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
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
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class alarmProperties_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  672 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
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
/*  844 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/*  859 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  860 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  863 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  864 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  865 */       set = AMConnectionPool.executeQueryStmt(query);
/*  866 */       if (set.next())
/*      */       {
/*  868 */         String helpLink = set.getString("LINK");
/*  869 */         DBUtil.searchLinks.put(key, helpLink);
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
/* 1022 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1023 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2211 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2230 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2239 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/* 2244 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/* 2247 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2254 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2257 */     JspWriter out = null;
/* 2258 */     Object page = this;
/* 2259 */     JspWriter _jspx_out = null;
/* 2260 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2264 */       response.setContentType("text/html;charset=UTF-8");
/* 2265 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2267 */       _jspx_page_context = pageContext;
/* 2268 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2269 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2270 */       session = pageContext.getSession();
/* 2271 */       out = pageContext.getOut();
/* 2272 */       _jspx_out = out;
/*      */       
/* 2274 */       out.write("<!-- $Id$ -->\n\n\n\n");
/* 2275 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2277 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2278 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2280 */       out.write(10);
/* 2281 */       out.write(10);
/* 2282 */       out.write(10);
/* 2283 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2284 */       AMRCATreeCreator RCATree = null;
/* 2285 */       RCATree = (AMRCATreeCreator)_jspx_page_context.getAttribute("RCATree", 2);
/* 2286 */       if (RCATree == null) {
/* 2287 */         RCATree = new AMRCATreeCreator();
/* 2288 */         _jspx_page_context.setAttribute("RCATree", RCATree, 2);
/*      */       }
/* 2290 */       out.write(10);
/*      */       
/* 2292 */       if (session.getAttribute("org.apache.struts.action.TOKEN") == null)
/*      */       {
/* 2294 */         org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2295 */         token.saveToken(request);
/*      */       }
/*      */       
/* 2298 */       Properties p = (Properties)request.getAttribute("alertProp");
/*      */       
/*      */ 
/*      */ 
/* 2302 */       String redirectURL = "/fault/AlarmDetails.do?";
/* 2303 */       Set urlSet = request.getParameterMap().keySet();
/* 2304 */       for (Iterator<String> keyIter = urlSet.iterator(); keyIter.hasNext();)
/*      */       {
/* 2306 */         String paramKey = (String)keyIter.next();
/* 2307 */         String paramValue = request.getParameter(paramKey);
/* 2308 */         if ((!paramKey.equals("method")) && ((!urlSet.contains("isFromBSG")) || (!paramKey.equals("redirectto")) || (!paramValue.contains("showapplication.do"))))
/*      */         {
/* 2310 */           redirectURL = redirectURL + paramKey + "=" + paramValue + "&";
/*      */         }
/*      */       }
/* 2313 */       redirectURL = redirectURL + "method=traversePage";
/* 2314 */       redirectURL = URLEncoder.encode(redirectURL);
/*      */       
/* 2316 */       if (p != null) {
/* 2317 */         String attributeName = p.getProperty("category");
/*      */         
/* 2319 */         p.setProperty("category", attributeName);
/* 2320 */         p.setProperty("stringpreviousseverity", FormatUtil.getString(p.getProperty("stringpreviousseverity")));
/* 2321 */         p.setProperty("stringseverity", FormatUtil.getString(p.getProperty("stringseverity")));
/* 2322 */         String temp = p.getProperty("entity");
/* 2323 */         ArrayList list = (ArrayList)request.getAttribute("associatedMG");
/* 2324 */         String monType = request.getParameter("monitortype");
/* 2325 */         HashMap extDeviceMap = null;
/* 2326 */         String resid = ((Properties)request.getAttribute("alertProp")).getProperty("resourceid");
/* 2327 */         HashMap site24x7List = new HashMap();
/* 2328 */         if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*      */         {
/* 2330 */           extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink(resid);
/*      */         }
/*      */         
/* 2333 */         site24x7List = DBUtil.getAllsite24x7MonitorsLink();
/* 2334 */         String glanceUrl = "/showReports.do?actionMethod=generateIndividualGlanceReport&resourceid=" + ((Properties)request.getAttribute("alertProp")).getProperty("resourceid") + "&period=0&Report=true&resourceType=Monitors&resid=" + ((Properties)request.getAttribute("alertProp")).getProperty("resourceid");
/* 2335 */         if ((monType != null) && ("HAI".equals(monType)))
/*      */         {
/* 2337 */           glanceUrl = "/showReports.do?actionMethod=generateGlanceReport&resourceid=" + ((Properties)request.getAttribute("alertProp")).getProperty("resourceid") + "&period=0&Report=true&resourceType=&resid=" + ((Properties)request.getAttribute("alertProp")).getProperty("resourceid") + "&haid=" + ((Properties)request.getAttribute("alertProp")).getProperty("resourceid");
/*      */         }
/* 2339 */         String moURL = "/showresource.do?resourceid=" + ((Properties)request.getAttribute("alertProp")).getProperty("resourceid") + "&method=showResourceForResourceID";
/*      */         
/*      */ 
/* 2342 */         if ((monType != null) && ("HAI".equals(monType)))
/*      */         {
/* 2344 */           moURL = "/showapplication.do?&method=showApplication&haid=" + resid;
/*      */         }
/*      */         
/*      */ 
/* 2348 */         boolean ent = false;
/*      */         try
/*      */         {
/* 2351 */           Integer.parseInt(temp.substring(0, temp.lastIndexOf("_")));
/* 2352 */           ent = true;
/*      */         }
/*      */         catch (Exception e) {}
/*      */         
/*      */ 
/* 2357 */         boolean history = false;
/*      */         try
/*      */         {
/* 2360 */           AMConnectionPool pool = AMConnectionPool.getInstance();
/* 2361 */           String query = "select * from Event where ENTITY='" + temp + "'";
/* 2362 */           query = com.adventnet.appmanager.db.DBQueryUtil.getTopNValues(query, "1");
/* 2363 */           ResultSet res = AMConnectionPool.executeQueryStmt(query);
/* 2364 */           if (res.next())
/*      */           {
/* 2366 */             history = true;
/*      */           }
/* 2368 */           res.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2372 */           e.printStackTrace();
/*      */         }
/* 2374 */         StringBuffer buf = new StringBuffer();
/* 2375 */         RCATree.updateRuleDetails(buf, p.getProperty("resourceid"), p.getProperty("attributeid"));
/* 2376 */         String rule = buf.toString();
/*      */         
/*      */ 
/* 2379 */         out.write(10);
/* 2380 */         out.write(10);
/*      */         
/*      */ 
/* 2383 */         String statusMsg = request.getParameter("statusMsg");
/* 2384 */         Object showAlarmActionStatus = request.getSession().getAttribute("showAlarmActionStatus");
/*      */         
/*      */ 
/* 2387 */         if ((showAlarmActionStatus != null) && (EnterpriseUtil.isAdminServer()) && (com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 2389 */           request.getSession().removeAttribute("showAlarmActionStatus");
/*      */           
/* 2391 */           out.write("\n\t\t<table class=\"messagebox\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" width=\"98%\">\n\t\t<tbody><tr>\n\t\t<td align=\"center\" width=\"4%\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" height=\"23\" width=\"23\">\n\t\t</td>\n\n\t\t<td class=\"message\" height=\"34\" width=\"94%\">\n\t\t\t");
/* 2392 */           out.print(FormatUtil.getString("am.webclient.admin.action.success.text"));
/* 2393 */           out.write("\n\t\t</td>\n\t\t</tr>\n\t\t</tbody></table>\n\t\t");
/*      */         }
/*      */         
/*      */ 
/* 2397 */         out.write("\n\n\n\n\n\t\t");
/* 2398 */         if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */           return;
/* 2400 */         out.write("\n       ");
/* 2401 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */           return;
/* 2403 */         out.write("\n\n       ");
/* 2404 */         if (_jspx_meth_c_005fchoose_005f1(_jspx_page_context))
/*      */           return;
/* 2406 */         out.write("\n\n     ");
/* 2407 */         if (_jspx_meth_c_005fchoose_005f2(_jspx_page_context))
/*      */           return;
/* 2409 */         out.write("\n   ");
/* 2410 */         Properties p1 = (Properties)request.getAttribute("alertProp");
/*      */         
/* 2412 */         String EID = p1.getProperty("entity");
/* 2413 */         String source = p1.getProperty("source");
/* 2414 */         String currentserverity = p1.getProperty("stringseverity");
/*      */         
/*      */ 
/* 2417 */         out.write(10);
/* 2418 */         out.write(10);
/* 2419 */         out.write(10);
/* 2420 */         if (_jspx_meth_c_005fset_005f12(_jspx_page_context))
/*      */           return;
/* 2422 */         out.write(32);
/* 2423 */         if (_jspx_meth_c_005fset_005f13(_jspx_page_context))
/*      */           return;
/* 2425 */         out.write(32);
/* 2426 */         out.write(10);
/* 2427 */         if (_jspx_meth_c_005fset_005f14(_jspx_page_context))
/*      */           return;
/* 2429 */         out.write(" \n<table width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\"  onmouseout=\"this.className='alarmtabheader'\" onmouseover=\"this.className='alarmtabHeaderHover'\">\n<tr>\n<td colspan=\"2\" width=\"100%\" class=\"lrbborder\" >\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n  <!--  <tr>\n        <td  align=\"left\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2430 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */           return;
/* 2432 */         out.write("</span></td>\n        <td  class=\"bodytext\">");
/* 2433 */         if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */           return;
/* 2435 */         out.write("</td></tr> -->\n  ");
/* 2436 */         if (_jspx_meth_c_005fset_005f15(_jspx_page_context))
/*      */           return;
/* 2438 */         out.write(" \n  <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"> \n    <td align=\"left\" class=\"whitegrayborder\" width=\"17%\"><span class=\"bodytextbold\">");
/* 2439 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */           return;
/* 2441 */         out.write("</span></td>\n    <td width=\"79%\"  class=\"whitegrayborder\"><span class=\"bodytext\">\n    ");
/* 2442 */         if ((extDeviceMap != null) && (extDeviceMap.get(resid) != null)) {
/* 2443 */           out.write("\n\t\t<a class=\"staticlinks\" href=\"javascript:MM_openBrWindow('");
/* 2444 */           out.print(extDeviceMap.get(resid));
/* 2445 */           out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\">\n\t");
/* 2446 */         } else if ((site24x7List != null) && (site24x7List.containsKey(resid))) {
/* 2447 */           out.write("\n\t    <a class=\"staticlinks\" href=\"javascript:MM_openBrWindow('");
/* 2448 */           out.print(URLEncoder.encode((String)site24x7List.get(resid)));
/* 2449 */           out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\">\n \n\t");
/*      */         } else {
/* 2451 */           out.write("<a class=\"staticlinks\" href=\"");
/* 2452 */           out.print(moURL);
/* 2453 */           out.write(34);
/* 2454 */           out.write(62);
/*      */         }
/*      */         
/* 2457 */         if (source.equals("-1"))
/*      */         {
/* 2459 */           out.println(EID.substring(0, EID.indexOf("_")));
/*      */         } else {
/* 2461 */           out.write(10);
/* 2462 */           if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */             return;
/* 2464 */           out.write("\n\n\t\t");
/*      */         }
/* 2466 */         out.write("</a></span> &nbsp; \n\t<a class=\"staticlinks\" href=\"javascript:fnOpenWindow('");
/* 2467 */         out.print(glanceUrl);
/* 2468 */         out.write("')\"><img  align=\"absmiddle\"  src=\"../images/AtaGlance-icon.gif\" border=\"0\" alt=\"");
/* 2469 */         out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 2470 */         out.write("\" hspace=\"4\" title=\"");
/* 2471 */         out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 2472 */         out.write("\"></a>\n\t");
/*      */         
/* 2474 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2475 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2476 */         _jspx_th_c_005fif_005f0.setParent(null);
/*      */         
/* 2478 */         _jspx_th_c_005fif_005f0.setTest("${ SHOW_CI_LINKS==true && not empty CI_INFO_URL}");
/* 2479 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2480 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */           for (;;) {
/* 2482 */             out.write("\n\t<a class=\"staticlinks\" href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2483 */             if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */               return;
/* 2485 */             out.write("','950','500','100','100')\"><img  align=\"absmiddle\"  src=\"/images/CI_Details.gif\" border=\"0\" alt=\"");
/* 2486 */             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 2487 */             out.write("\" hspace=\"4\" title=\"");
/* 2488 */             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 2489 */             out.write("\"></a>\n\t");
/* 2490 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2491 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2495 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2496 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */         }
/*      */         
/* 2499 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2500 */         out.write(10);
/* 2501 */         out.write(9);
/*      */         
/* 2503 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2504 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2505 */         _jspx_th_c_005fif_005f1.setParent(null);
/*      */         
/* 2507 */         _jspx_th_c_005fif_005f1.setTest("${SHOW_CI_LINKS==true && not empty CI_RL_URL}");
/* 2508 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2509 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */           for (;;) {
/* 2511 */             out.write("\n\t<a class=\"staticlinks\" href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2512 */             if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */               return;
/* 2514 */             out.write("','950','500','100','100')\"><img  align=\"absmiddle\"  src=\"/images/cmdb-rship-icon.gif\" border=\"0\" alt=\"");
/* 2515 */             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 2516 */             out.write("\" hspace=\"4\" title=\"");
/* 2517 */             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 2518 */             out.write("\"></a>\n\t");
/* 2519 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2520 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2524 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2525 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */         }
/*      */         
/* 2528 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2529 */         out.write("\n\t\n    </td>\n  </tr>\n  ");
/* 2530 */         if (_jspx_meth_c_005fset_005f16(_jspx_page_context))
/*      */           return;
/* 2532 */         out.write(" \n  <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"> \n    <td align=\"left\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 2533 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */           return;
/* 2535 */         out.write("</span></td>\n    <td  class=\"yellowgrayborder\"><span class=\"bodytext\">");
/* 2536 */         if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*      */           return;
/* 2538 */         out.write("</span></td>\n  </tr>\n  \n  ");
/*      */         
/* 2540 */         IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2541 */         _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2542 */         _jspx_th_c_005fif_005f2.setParent(null);
/*      */         
/* 2544 */         _jspx_th_c_005fif_005f2.setTest("${SHOW_TICKET_LINK==true || ENABLE_DYNAMIC_TICKETING==true}");
/* 2545 */         int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2546 */         if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */           for (;;) {
/* 2548 */             out.write("\n  <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"> \n    <td align=\"left\" class=\"yellowgrayborder\"><span class=\"bodytextbold\">");
/* 2549 */             if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */               return;
/* 2551 */             out.write("</span></td>\n    <td width=\"79%\"  class=\"whitegrayborder\"><span class=\"bodytext\">\n\t");
/*      */             
/* 2553 */             ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2554 */             _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2555 */             _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f2);
/* 2556 */             int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2557 */             if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */               for (;;) {
/* 2559 */                 out.write(10);
/* 2560 */                 out.write(9);
/* 2561 */                 out.write(9);
/*      */                 
/* 2563 */                 WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2564 */                 _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2565 */                 _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                 
/* 2567 */                 _jspx_th_c_005fwhen_005f6.setTest("${not empty workOrderUrl}");
/* 2568 */                 int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2569 */                 if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                   for (;;) {
/* 2571 */                     out.write("\n\t\t\t");
/*      */                     
/* 2573 */                     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2574 */                     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2575 */                     _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fwhen_005f6);
/* 2576 */                     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2577 */                     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                       for (;;) {
/* 2579 */                         out.write("\n\t\t\t\t");
/*      */                         
/* 2581 */                         WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2582 */                         _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 2583 */                         _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                         
/* 2585 */                         _jspx_th_c_005fwhen_005f7.setTest("${SHOW_TICKET_LINK==true}");
/* 2586 */                         int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 2587 */                         if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                           for (;;) {
/* 2589 */                             out.write("\n\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2590 */                             if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f7, _jspx_page_context))
/*      */                               return;
/* 2592 */                             out.write("','950','500','100','100')\"><img  align=\"absmiddle\"  src=\"/images/ticket.png\" border=\"0\" alt=\"");
/* 2593 */                             out.print(FormatUtil.getString("webclient.fault.alarm.Workorderurl"));
/* 2594 */                             out.write("\" hspace=\"4\" title=\"");
/* 2595 */                             out.print(FormatUtil.getString("webclient.fault.alarm.Workorderurl"));
/* 2596 */                             out.write("\"></a>\n\t\t\t\t");
/* 2597 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 2598 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2602 */                         if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 2603 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                         }
/*      */                         
/* 2606 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2607 */                         out.write("\n\t\t\t\t");
/* 2608 */                         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/*      */                           return;
/* 2610 */                         out.write("\n\t\t\t");
/* 2611 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2612 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2616 */                     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2617 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                     }
/*      */                     
/* 2620 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2621 */                     out.write(10);
/* 2622 */                     out.write(9);
/* 2623 */                     out.write(9);
/* 2624 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2625 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2629 */                 if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2630 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                 }
/*      */                 
/* 2633 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2634 */                 out.write("\n\t\t\n\t\t");
/*      */                 
/* 2636 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2637 */                 _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2638 */                 _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f3);
/* 2639 */                 int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2640 */                 if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                   for (;;) {
/* 2642 */                     out.write("\n\t\t\t");
/*      */                     
/* 2644 */                     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2645 */                     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2646 */                     _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fotherwise_005f4);
/* 2647 */                     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2648 */                     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                       for (;;) {
/* 2650 */                         out.write("\n\t\t\t\t");
/*      */                         
/* 2652 */                         WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2653 */                         _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 2654 */                         _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                         
/* 2656 */                         _jspx_th_c_005fwhen_005f8.setTest("${ENABLE_DYNAMIC_TICKETING==true && severity!='Clear'}");
/* 2657 */                         int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 2658 */                         if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                           for (;;) {
/* 2660 */                             out.write("\n\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('/fault/AlarmDetails.do?method=doExecuteActions&amp;userName=");
/* 2661 */                             out.print(request.getRemoteUser());
/* 2662 */                             out.write("&amp;entity=");
/* 2663 */                             if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fwhen_005f8, _jspx_page_context))
/*      */                               return;
/* 2665 */                             out.write("','650','400','100','100')\"><img  align=\"absmiddle\"  src=\"/images/icon_alarm_executeaction_.gif\" border=\"0\" alt=\"");
/* 2666 */                             out.print(FormatUtil.getString("webclient.fault.alarm.action.text"));
/* 2667 */                             out.write("\" hspace=\"4\" title=\"");
/* 2668 */                             out.print(FormatUtil.getString("webclient.fault.alarm.action.text"));
/* 2669 */                             out.write("\"></a>\n\t\t\t\t");
/* 2670 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 2671 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2675 */                         if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 2676 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                         }
/*      */                         
/* 2679 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 2680 */                         out.write("\n\t\t\t\t");
/* 2681 */                         if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/*      */                           return;
/* 2683 */                         out.write("\n\t\t\t");
/* 2684 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2685 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2689 */                     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2690 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                     }
/*      */                     
/* 2693 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2694 */                     out.write(10);
/* 2695 */                     out.write(9);
/* 2696 */                     out.write(9);
/* 2697 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2698 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2702 */                 if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2703 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                 }
/*      */                 
/* 2706 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2707 */                 out.write(10);
/* 2708 */                 out.write(9);
/* 2709 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2710 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2714 */             if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2715 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */             }
/*      */             
/* 2718 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2719 */             out.write("\n\t</td>\n  </tr>\t\n  ");
/* 2720 */             int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2721 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2725 */         if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2726 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */         }
/*      */         
/* 2729 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2730 */         out.write("\n  \n  \n  ");
/* 2731 */         if (_jspx_meth_c_005fset_005f17(_jspx_page_context))
/*      */           return;
/* 2733 */         out.write("  \n  <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"> \n    <td align=\"left\"  class=\"whitegrayborder\"><span class=\"bodytext\">");
/* 2734 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */           return;
/* 2736 */         out.write("&nbsp; ");
/* 2737 */         if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */           return;
/* 2739 */         out.write(" </span></td>\n    <td  class=\"whitegrayborder\">");
/* 2740 */         out.print(formatAlertDT(((Properties)request.getAttribute("alertProp")).getProperty("createTime")));
/* 2741 */         out.write("</td>\n  </tr>\n  ");
/* 2742 */         if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */           return;
/* 2744 */         out.write(10);
/* 2745 */         out.write(32);
/* 2746 */         out.write(32);
/* 2747 */         if (_jspx_meth_c_005fset_005f18(_jspx_page_context))
/*      */           return;
/* 2749 */         out.write(" \n  <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"> \n    <td align=\"left\"  class=\"yellowgrayborder\"><span class=\"bodytext\">");
/* 2750 */         if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */           return;
/* 2752 */         out.write("&nbsp; ");
/* 2753 */         if (_jspx_meth_fmt_005fmessage_005f8(_jspx_page_context))
/*      */           return;
/* 2755 */         out.write("</span></td>\n    <td  class=\"yellowgrayborder\">");
/* 2756 */         out.print(formatAlertDT(((Properties)request.getAttribute("alertProp")).getProperty("modTime")));
/* 2757 */         out.write("</td>\n  </tr>\n  ");
/* 2758 */         if (_jspx_meth_c_005fset_005f19(_jspx_page_context))
/*      */           return;
/* 2760 */         out.write("\n    ");
/* 2761 */         if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */           return;
/* 2763 */         out.write(10);
/* 2764 */         out.write(32);
/* 2765 */         out.write(32);
/* 2766 */         if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
/*      */           return;
/* 2768 */         out.write(10);
/* 2769 */         out.write(32);
/* 2770 */         out.write(32);
/* 2771 */         if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
/*      */           return;
/* 2773 */         out.write("\n  \n  <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"> \n  <td  align=\"left\" class=\"whitegrayborder\"><span class=\"highlite\">");
/* 2774 */         if (_jspx_meth_fmt_005fmessage_005f9(_jspx_page_context))
/*      */           return;
/* 2776 */         out.write("</span></td>\n    <td  class=\"whitegrayborder\"> \n    \t<table cellpadding=\"0\" cellspacing=\"0\"><tr><td >\n    \t");
/* 2777 */         String roleValue = "ADMIN,ENTERPRISEADMIN";
/* 2778 */         if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */         {
/* 2780 */           roleValue = roleValue + ",OPERATOR";
/*      */         }
/* 2782 */         out.write(10);
/* 2783 */         out.write(32);
/* 2784 */         out.write(32);
/*      */         
/* 2786 */         ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2787 */         _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 2788 */         _jspx_th_c_005fchoose_005f7.setParent(null);
/* 2789 */         int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 2790 */         if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */           for (;;) {
/* 2792 */             out.write(" \n  ");
/*      */             
/* 2794 */             WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2795 */             _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 2796 */             _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f7);
/*      */             
/* 2798 */             _jspx_th_c_005fwhen_005f10.setTest("${clearStatus == true}");
/* 2799 */             int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 2800 */             if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */               for (;;) {
/* 2802 */                 out.write(" \n  \n    \n    \t");
/*      */                 
/* 2804 */                 if (ent) {
/* 2805 */                   out.write("\n    \t <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?monitorType=");
/* 2806 */                   out.print(monType);
/* 2807 */                   out.write("&resourceid=");
/* 2808 */                   out.print(((Properties)request.getAttribute("alertProp")).getProperty("resourceid"));
/* 2809 */                   out.write("&attributeid=");
/* 2810 */                   out.print(((Properties)request.getAttribute("alertProp")).getProperty("attributeid"));
/* 2811 */                   out.write("')\" class=\"resourcename\"> ");
/*      */                 } else {
/* 2813 */                   out.write(" <a href=\"javascript:void(0)\"> ");
/*      */                 }
/* 2815 */                 out.write(" <img style=\"position:relative; top:5px;\" src=\"");
/* 2816 */                 out.print(((Properties)request.getAttribute("alertProp")).getProperty("currentstatusImg"));
/* 2817 */                 out.write("\" \n      alt=\"");
/* 2818 */                 if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fwhen_005f10, _jspx_page_context))
/*      */                   return;
/* 2820 */                 out.write("\" title=\"");
/* 2821 */                 if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fwhen_005f10, _jspx_page_context))
/*      */                   return;
/* 2823 */                 out.write("\" align=\"middle\" border=0 ></a>\n       ");
/* 2824 */                 if (ent) {
/* 2825 */                   out.write("\n       \t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?monitorType=");
/* 2826 */                   out.print(monType);
/* 2827 */                   out.write("&resourceid=");
/* 2828 */                   out.print(((Properties)request.getAttribute("alertProp")).getProperty("resourceid"));
/* 2829 */                   out.write("&attributeid=");
/* 2830 */                   out.print(((Properties)request.getAttribute("alertProp")).getProperty("attributeid"));
/* 2831 */                   out.write("')\" class=\"resourcename\"> ");
/*      */                 } else {
/* 2833 */                   out.write(" <a href=\"javascript:void(0)\" class=\"resourcename\"> ");
/*      */                 }
/* 2835 */                 out.write(32);
/* 2836 */                 if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fwhen_005f10, _jspx_page_context))
/*      */                   return;
/* 2838 */                 out.write("</a>\n       \n\t  ");
/*      */                 
/* 2840 */                 if (request.getParameter("extDevice") == null)
/*      */                 {
/* 2842 */                   out.write("\n     ");
/*      */                   
/* 2844 */                   PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2845 */                   _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2846 */                   _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f10);
/*      */                   
/* 2848 */                   _jspx_th_logic_005fpresent_005f0.setRole(roleValue);
/* 2849 */                   int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2850 */                   if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                     for (;;) {
/* 2852 */                       out.write("<a class=\"resourcename\" href=\"/fault/AlarmOperations.do?methodCall=clearAlarm&haid=");
/* 2853 */                       if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                         return;
/* 2855 */                       out.write("&monitor=");
/* 2856 */                       if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                         return;
/* 2858 */                       out.write("&viewId=Alerts.5&selectedEntity=");
/* 2859 */                       if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                         return;
/* 2861 */                       out.write("&source=");
/* 2862 */                       if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                         return;
/* 2864 */                       out.write("&category=");
/* 2865 */                       if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                         return;
/* 2867 */                       out.write("&redirectto=");
/* 2868 */                       if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                         return;
/* 2870 */                       out.write("&org.apache.struts.taglib.html.TOKEN=");
/* 2871 */                       out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 2872 */                       out.write("&alertdetails=true\">");
/* 2873 */                       if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                         return;
/* 2875 */                       out.write("</a>");
/* 2876 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2877 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2881 */                   if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2882 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                   }
/*      */                   
/* 2885 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2886 */                   out.write("\n     \n\t ");
/*      */                 }
/* 2888 */                 out.write("\n\t \n\t ");
/*      */                 
/* 2890 */                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2891 */                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2892 */                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fwhen_005f10);
/*      */                 
/* 2894 */                 _jspx_th_logic_005fpresent_005f1.setRole("OPERATOR");
/* 2895 */                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2896 */                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                   for (;;) {
/* 2898 */                     out.write("\n  \t\t");
/* 2899 */                     if (DBUtil.getGlobalConfigValueasBoolean("allowClearAlarms")) {
/* 2900 */                       out.write("\n  \t\t <a class=\"resourcename\" href=\"/fault/AlarmOperations.do?methodCall=clearAlarm&haid=");
/* 2901 */                       if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                         return;
/* 2903 */                       out.write("&monitor=");
/* 2904 */                       if (_jspx_meth_c_005fout_005f19(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                         return;
/* 2906 */                       out.write("&viewId=Alerts.5&selectedEntity=");
/* 2907 */                       if (_jspx_meth_c_005fout_005f20(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                         return;
/* 2909 */                       out.write("&source=");
/* 2910 */                       if (_jspx_meth_c_005fout_005f21(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                         return;
/* 2912 */                       out.write("&category=");
/* 2913 */                       if (_jspx_meth_c_005fout_005f22(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                         return;
/* 2915 */                       out.write("&redirectto=");
/* 2916 */                       if (_jspx_meth_c_005fout_005f23(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                         return;
/* 2918 */                       out.write("&org.apache.struts.taglib.html.TOKEN=");
/* 2919 */                       out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 2920 */                       out.write("&alertdetails=true\">");
/* 2921 */                       if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*      */                         return;
/* 2923 */                       out.write("</a>\n  \t\t");
/*      */                     }
/* 2925 */                     out.write("\n  \t");
/* 2926 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2927 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2931 */                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2932 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                 }
/*      */                 
/* 2935 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2936 */                 out.write(10);
/* 2937 */                 out.write(32);
/* 2938 */                 out.write(32);
/* 2939 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 2940 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2944 */             if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 2945 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */             }
/*      */             
/* 2948 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 2949 */             out.write(" \n  ");
/*      */             
/* 2951 */             OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2952 */             _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 2953 */             _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 2954 */             int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 2955 */             if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */               for (;;) {
/* 2957 */                 out.write(" \n    \n    ");
/*      */                 
/* 2959 */                 if (ent) {
/* 2960 */                   out.write(" <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?monitorType=");
/* 2961 */                   out.print(monType);
/* 2962 */                   out.write("&resourceid=");
/* 2963 */                   out.print(((Properties)request.getAttribute("alertProp")).getProperty("resourceid"));
/* 2964 */                   out.write("&attributeid=");
/* 2965 */                   out.print(((Properties)request.getAttribute("alertProp")).getProperty("attributeid"));
/* 2966 */                   out.write("')\" class=\"resourcename\"> ");
/*      */                 } else {
/* 2968 */                   out.write(" <a href=\"javascript:void(0)\"> ");
/*      */                 }
/* 2970 */                 out.write(" <img style=\"position:relative; top:5px;\" src=\"");
/* 2971 */                 out.print(((Properties)request.getAttribute("alertProp")).getProperty("currentstatusImg"));
/* 2972 */                 out.write("\" alt=\"");
/* 2973 */                 if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/*      */                   return;
/* 2975 */                 out.write("\" title=\"");
/* 2976 */                 if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/*      */                   return;
/* 2978 */                 out.write("\"  align=\"top\" border=0 ></a>&nbsp; \n    ");
/* 2979 */                 if (ent) {
/* 2980 */                   out.write("  <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?monitorType=");
/* 2981 */                   out.print(monType);
/* 2982 */                   out.write("&resourceid=");
/* 2983 */                   out.print(((Properties)request.getAttribute("alertProp")).getProperty("resourceid"));
/* 2984 */                   out.write("&attributeid=");
/* 2985 */                   out.print(((Properties)request.getAttribute("alertProp")).getProperty("attributeid"));
/* 2986 */                   out.write("')\" class=\"resourcename\"> ");
/*      */                 } else {
/* 2988 */                   out.write(" <a href=\"javascript:void(0)\" class=\"resourcename\"> ");
/*      */                 }
/* 2990 */                 out.write(32);
/* 2991 */                 if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fotherwise_005f7, _jspx_page_context))
/*      */                   return;
/* 2993 */                 out.write("</a>&nbsp;\n    \n    \n    ");
/*      */                 
/* 2995 */                 PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2996 */                 _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2997 */                 _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                 
/* 2999 */                 _jspx_th_logic_005fpresent_005f2.setRole(roleValue);
/* 3000 */                 int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3001 */                 if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                   for (;;) {
/* 3003 */                     out.write("\n    ");
/*      */                     
/* 3005 */                     if (request.getParameter("extDevice") == null)
/*      */                     {
/* 3007 */                       String redirectto = request.getParameter("redirectto");
/* 3008 */                       if (redirectto != null)
/*      */                       {
/* 3010 */                         if (EnterpriseUtil.isAdminServer())
/*      */                         {
/* 3012 */                           redirectto = URLEncoder.encode(redirectto);
/*      */                         }
/*      */                       }
/*      */                       
/* 3016 */                       out.write("\n    \n    <a class=\"resourcename\" href=\"/fault/AlarmOperations.do?methodCall=clearAlarm&haid=");
/* 3017 */                       if (_jspx_meth_c_005fout_005f27(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                         return;
/* 3019 */                       out.write("&monitor=");
/* 3020 */                       if (_jspx_meth_c_005fout_005f28(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                         return;
/* 3022 */                       out.write("&viewId=Alerts.5&monitortype=");
/* 3023 */                       if (_jspx_meth_c_005fout_005f29(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                         return;
/* 3025 */                       out.write("&selectedEntity=");
/* 3026 */                       if (_jspx_meth_c_005fout_005f30(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                         return;
/* 3028 */                       out.write("&source=");
/* 3029 */                       if (_jspx_meth_c_005fout_005f31(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                         return;
/* 3031 */                       out.write("&category=");
/* 3032 */                       if (_jspx_meth_c_005fout_005f32(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                         return;
/* 3034 */                       out.write("&org.apache.struts.taglib.html.TOKEN=");
/* 3035 */                       out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 3036 */                       out.write("&alertdetails=true&redirectto=");
/* 3037 */                       out.print(redirectto);
/* 3038 */                       out.write(34);
/* 3039 */                       out.write(62);
/* 3040 */                       if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                         return;
/* 3042 */                       out.write("</a>\n    \n    ");
/*      */                     }
/*      */                     
/*      */ 
/* 3046 */                     out.write("\n    \n    ");
/* 3047 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3048 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3052 */                 if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3053 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                 }
/*      */                 
/* 3056 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3057 */                 out.write("\n   \n    ");
/*      */                 
/* 3059 */                 PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3060 */                 _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3061 */                 _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                 
/* 3063 */                 _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/* 3064 */                 int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3065 */                 if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                   for (;;) {
/* 3067 */                     out.write("\n  \t\t");
/* 3068 */                     if (DBUtil.getGlobalConfigValueasBoolean("allowClearAlarms")) {
/* 3069 */                       out.write("\n  \t\t <a class=\"resourcename\" href=\"/fault/AlarmOperations.do?methodCall=clearAlarm&haid=");
/* 3070 */                       if (_jspx_meth_c_005fout_005f33(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                         return;
/* 3072 */                       out.write("&monitor=");
/* 3073 */                       if (_jspx_meth_c_005fout_005f34(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                         return;
/* 3075 */                       out.write("&viewId=Alerts.5&selectedEntity=");
/* 3076 */                       if (_jspx_meth_c_005fout_005f35(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                         return;
/* 3078 */                       out.write("&source=");
/* 3079 */                       if (_jspx_meth_c_005fout_005f36(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                         return;
/* 3081 */                       out.write("&category=");
/* 3082 */                       if (_jspx_meth_c_005fout_005f37(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                         return;
/* 3084 */                       out.write("&redirectto=");
/* 3085 */                       if (_jspx_meth_c_005fout_005f38(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                         return;
/* 3087 */                       out.write("&org.apache.struts.taglib.html.TOKEN=");
/* 3088 */                       out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 3089 */                       out.write("&alertdetails=true\">");
/* 3090 */                       if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                         return;
/* 3092 */                       out.write("</a>\n  \t\t");
/*      */                     }
/* 3094 */                     out.write("\n  \t");
/* 3095 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3096 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3100 */                 if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3101 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                 }
/*      */                 
/* 3104 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3105 */                 out.write("\n   \n   \n  \t\n  ");
/* 3106 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3107 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 3111 */             if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3112 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */             }
/*      */             
/* 3115 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3116 */             out.write(10);
/* 3117 */             out.write(32);
/* 3118 */             out.write(32);
/* 3119 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 3120 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 3124 */         if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 3125 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */         }
/*      */         
/* 3128 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3129 */         out.write(" </td>\n  <td style=\"width:30px;\" class=\"bodytext\">&nbsp; &nbsp; | &nbsp;</td>\n   <td align=\"left\" style=\"font-style:italic;\" class=\"bodytext\"><span class=\"bodytext\" >");
/* 3130 */         if (_jspx_meth_fmt_005fmessage_005f14(_jspx_page_context))
/*      */           return;
/* 3132 */         out.write("&nbsp;:&nbsp;</span>\n   \n\t\n        ");
/* 3133 */         if (_jspx_meth_c_005fchoose_005f8(_jspx_page_context))
/*      */           return;
/* 3135 */         out.write(" \n\t\t</td></tr></table></td>\n </tr>\n ");
/*      */         
/* 3137 */         String tempResid = p.getProperty("resourceid");
/* 3138 */         String tempAttrid = p.getProperty("attributeid");
/* 3139 */         String dependentDevices = null;
/* 3140 */         ArrayList<HashMap<String, String>> arrlist = com.adventnet.appmanager.fault.DependentDeviceUtil.getInstance().getDependentMonitorInfo(tempResid);
/* 3141 */         if (arrlist.size() == 0) {
/* 3142 */           dependentDevices = "None";
/*      */         }
/*      */         else {
/* 3145 */           for (int i = 0; i < arrlist.size(); i++) {
/* 3146 */             HashMap map = (HashMap)arrlist.get(i);
/* 3147 */             String disname = (String)map.get("displayname");
/*      */             
/* 3149 */             if (i == 0) {
/* 3150 */               dependentDevices = disname;
/*      */             }
/*      */             else {
/* 3153 */               dependentDevices = dependentDevices + " , " + disname;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 3158 */         String restype = DBUtil.getResourceType(tempResid);
/* 3159 */         String availKeyforResType = "";
/* 3160 */         if (restype != null)
/*      */         {
/* 3162 */           availKeyforResType = AMAttributesCache.getAvailabilityId(restype);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 3167 */         out.write("\n \n \n ");
/*      */         
/* 3169 */         if ((!EnterpriseUtil.isAdminServer()) && (!"3200".equalsIgnoreCase(tempAttrid)))
/*      */         {
/*      */ 
/* 3172 */           out.write("\n <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"> \n    <td align=\"left\"  class=\"whitegrayborder\" valign=\"top\"><span class=\"bodytext\">");
/* 3173 */           if (_jspx_meth_fmt_005fmessage_005f15(_jspx_page_context))
/*      */             return;
/* 3175 */           out.write("</span></td>\n    <td  class=\"whitegrayborder\">\n    \t<table>\n\t<tr>\n\t\t<td class=\"bodytext\">\n\t\t");
/*      */           
/* 3177 */           PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3178 */           _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3179 */           _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */           
/* 3181 */           _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 3182 */           int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3183 */           if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */             for (;;) {
/* 3185 */               out.write("\n    \t  \t");
/*      */               
/* 3187 */               if (((attributeName.equals("Health")) && (p.getProperty("attributeid").equals("18"))) || ((attributeName.equals("Availability")) && (p.getProperty("attributeid").equals("17"))))
/*      */               {
/*      */ 
/* 3190 */                 out.write("\n               \t\t<a href=\"javascript:void(0)\" onClick=\"javascript:fnOpenNewWindow('/jsp/ThresholdActionConfiguration.jsp?attributeToSelect=");
/* 3191 */                 out.print(tempAttrid);
/* 3192 */                 out.write("&attributeIDs=");
/* 3193 */                 out.print(tempAttrid);
/* 3194 */                 out.write("&resourceid=");
/* 3195 */                 out.print(tempResid);
/* 3196 */                 out.write("&global=true&redirectto=showActionProfiles.do?method=getHAProfiles&admin=true&all=true&haid=");
/* 3197 */                 out.print(tempResid);
/* 3198 */                 out.write("&hideArea=true&PRINTER_FRIENDLY=true');\" class=\"resourcename\">");
/* 3199 */                 out.print(rule);
/* 3200 */                 out.write("</a>\n               \t\t\n                ");
/*      */ 
/*      */               }
/* 3203 */               else if (attributeName.equals("Health"))
/*      */               {
/*      */ 
/* 3206 */                 out.write("\n               \t\t<a href=\"javascript:void(0)\" onClick=\"javascript:fnOpenNewWindow('/jsp/ThresholdActionConfiguration.jsp?attributeToSelect=");
/* 3207 */                 out.print(tempAttrid);
/* 3208 */                 out.write("&attributeIDs=");
/* 3209 */                 out.print(tempAttrid);
/* 3210 */                 out.write("&resourceid=");
/* 3211 */                 out.print(tempResid);
/* 3212 */                 out.write("&global=true&redirectto=showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=");
/* 3213 */                 out.print(tempResid);
/* 3214 */                 out.write("&PRINTER_FRIENDLY=true');\" class=\"resourcename\">");
/* 3215 */                 out.print(rule);
/* 3216 */                 out.write("</a>\n                ");
/*      */               }
/*      */               
/*      */ 
/* 3220 */               out.write("\n    \t\t");
/* 3221 */               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3222 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3226 */           if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3227 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */           }
/*      */           
/* 3230 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3231 */           out.write(10);
/* 3232 */           out.write(9);
/* 3233 */           out.write(9);
/*      */           
/* 3235 */           NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3236 */           _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3237 */           _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */           
/* 3239 */           _jspx_th_logic_005fnotPresent_005f0.setRole("ADMIN");
/* 3240 */           int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3241 */           if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */             for (;;) {
/* 3243 */               out.write("\n\t\t\t");
/* 3244 */               out.print(rule);
/* 3245 */               out.write(10);
/* 3246 */               out.write(9);
/* 3247 */               out.write(9);
/* 3248 */               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3249 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3253 */           if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3254 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */           }
/*      */           
/* 3257 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3258 */           out.write("\n   \t\t</td>\n   \t\t<td class=\"bodytext\">&nbsp; | &nbsp;</td>\n    \t\t<td class=\"bodytext\" >");
/* 3259 */           if (_jspx_meth_fmt_005fmessage_005f16(_jspx_page_context))
/*      */             return;
/* 3261 */           out.write("&nbsp;&nbsp;:&nbsp;&nbsp;\n    \t\t");
/* 3262 */           out.print(dependentDevices);
/* 3263 */           out.write(" &nbsp;&nbsp;\n\t\t");
/*      */           
/* 3265 */           PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3266 */           _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3267 */           _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */           
/* 3269 */           _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3270 */           int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3271 */           if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */             for (;;) {
/* 3273 */               out.write("\n   \t\t");
/*      */               
/* 3275 */               if (com.adventnet.appmanager.util.ReportDataUtilities.isHAI(tempResid))
/*      */               {
/*      */ 
/* 3278 */                 out.write("\n\t\t\t\t<a class=\"resourcename\" href=\"#\" onclick=\"javascript:MM_openBrWindow('/jsp/NewThresholdConfiguration.jsp?resourceid=");
/* 3279 */                 out.print(tempResid);
/* 3280 */                 out.write("&attributeToSelect=");
/* 3281 */                 out.print(availKeyforResType);
/* 3282 */                 out.write("&attributeIDs=");
/* 3283 */                 out.print(availKeyforResType);
/* 3284 */                 out.write("&global=true&PRINTER_FRIENDLY=true&hideArea=true&redirectto=','','resizable=yes,scrollbars=yes,top=100,left=100,width=950,height=500');\">\n         \t");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 3290 */                 out.write("\n\t\t\t\t<a class=\"resourcename\" href=\"#\" onclick=\"javascript:MM_openBrWindow('/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3291 */                 out.print(tempResid);
/* 3292 */                 out.write("&attributeToSelect=");
/* 3293 */                 out.print(availKeyforResType);
/* 3294 */                 out.write("&attributeIDs=");
/* 3295 */                 out.print(availKeyforResType);
/* 3296 */                 out.write("&global=true&PRINTER_FRIENDLY=true&hideArea=true&redirectto=','','resizable=yes,scrollbars=yes,top=100,left=100,width=950,height=500');\">\n         \t");
/*      */               }
/*      */               
/*      */ 
/* 3300 */               out.write("\n    \t\t");
/* 3301 */               if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_logic_005fpresent_005f5, _jspx_page_context))
/*      */                 return;
/* 3303 */               out.write("</a>\n\t\t");
/* 3304 */               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3305 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3309 */           if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3310 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */           }
/*      */           
/* 3313 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3314 */           out.write("\n    </td>\n   </tr></table> </td>\n  </tr>\n  ");
/*      */         }
/*      */         
/*      */ 
/* 3318 */         out.write(10);
/* 3319 */         out.write(32);
/* 3320 */         out.write(32);
/* 3321 */         if (_jspx_meth_c_005fset_005f23(_jspx_page_context))
/*      */           return;
/* 3323 */         out.write("\n  <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"> \n    <td align=\"left\"  class=\"whitegrayborder\" valign=\"top\"><span class=\"bodytext\">");
/* 3324 */         if (_jspx_meth_fmt_005fmessage_005f18(_jspx_page_context))
/*      */           return;
/* 3326 */         out.write("</span></td>\n    <td  class=\"whitegrayborder\">\n    ");
/*      */         
/* 3328 */         ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3329 */         _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 3330 */         _jspx_th_c_005fchoose_005f9.setParent(null);
/* 3331 */         int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 3332 */         if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */           for (;;) {
/* 3334 */             out.write("\n    \t");
/*      */             
/* 3336 */             WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3337 */             _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 3338 */             _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f9);
/*      */             
/* 3340 */             _jspx_th_c_005fwhen_005f12.setTest("${alertProp.owner == '-'}");
/* 3341 */             int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 3342 */             if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */               for (;;) {
/* 3344 */                 out.write("\n\t\t<a href=\"/fault/AlarmOperations.do?methodCall=pickUpAlarm&selectedEntity=");
/* 3345 */                 out.print(temp);
/* 3346 */                 out.write("&redirectto=");
/* 3347 */                 out.print(redirectURL);
/* 3348 */                 out.write("\" class=\"alarm-links\"><img src=\"/images/icon_alert_unacknowleged.gif\" style=\"position:relative; top:4px; right:2px;\" border=\"0\"><span class=\"alarm-links\">");
/* 3349 */                 out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3350 */                 out.write("</span></a>\n\t");
/* 3351 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 3352 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 3356 */             if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 3357 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */             }
/*      */             
/* 3360 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3361 */             out.write(10);
/* 3362 */             out.write(9);
/*      */             
/* 3364 */             OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3365 */             _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 3366 */             _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 3367 */             int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 3368 */             if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */               for (;;) {
/* 3370 */                 out.write("\n\t\t<a href=\"/fault/AlarmOperations.do?methodCall=unPickAlarm&selectedEntity=");
/* 3371 */                 out.print(temp);
/* 3372 */                 out.write("&redirectto=");
/* 3373 */                 out.print(redirectURL);
/* 3374 */                 out.write("\" class=\"alarm-links\"><img src=\"/images/icon_alert_acknowleged.gif\" border=\"0\"> <span class=\"alarm-links\">");
/* 3375 */                 if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fotherwise_005f9, _jspx_page_context))
/*      */                   return;
/* 3377 */                 out.write("</span></a>\n\t");
/* 3378 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 3379 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 3383 */             if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 3384 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */             }
/*      */             
/* 3387 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 3388 */             out.write("\n     ");
/* 3389 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 3390 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 3394 */         if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 3395 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */         }
/*      */         
/* 3398 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 3399 */         out.write("\n    </td>\n  </tr>\n  <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"> \n    <td align=\"left\"  class=\"whitegrayborder\" valign=\"top\"><span class=\"bodytext\">");
/* 3400 */         if (_jspx_meth_fmt_005fmessage_005f19(_jspx_page_context))
/*      */           return;
/* 3402 */         out.write("</span></td>\n    <td  class=\"whitegrayborder\">");
/* 3403 */         if (_jspx_meth_c_005fout_005f42(_jspx_page_context))
/*      */           return;
/* 3405 */         out.write("</td>\n  </tr>\n  ");
/* 3406 */         if (_jspx_meth_c_005fset_005f24(_jspx_page_context))
/*      */           return;
/* 3408 */         out.write(" \n  ");
/* 3409 */         if (!"3200".equalsIgnoreCase(tempAttrid)) {
/* 3410 */           out.write("\n      <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\"><td class=\"yellowgrayborder\" valign=\"top\">");
/* 3411 */           if (_jspx_meth_fmt_005fmessage_005f20(_jspx_page_context))
/*      */             return;
/* 3413 */           out.write("</td>\t");
/* 3414 */           out.write("\t\n      <td class=\"yellowgrayborder\">\n      ");
/* 3415 */           if (_jspx_meth_c_005fif_005f8(_jspx_page_context))
/*      */             return;
/* 3417 */           out.write("\n     ");
/* 3418 */           if (_jspx_meth_c_005fif_005f9(_jspx_page_context))
/*      */             return;
/* 3420 */           out.write("\n     ");
/* 3421 */           if (_jspx_meth_c_005fif_005f10(_jspx_page_context))
/*      */             return;
/* 3423 */           out.write("\n \t<a  href=\"javascript:void(0)\" onclick=\"displaycustomFields()\" class=\"resourcename\" >");
/* 3424 */           if (_jspx_meth_fmt_005fmessage_005f24(_jspx_page_context))
/*      */             return;
/* 3426 */           out.write("</a></td></tr>\t");
/* 3427 */           out.write(10);
/* 3428 */           out.write(32);
/*      */         }
/* 3430 */         out.write(10);
/* 3431 */         out.write(32);
/*      */         
/*      */ 
/* 3434 */         if (("Critical".equals(currentserverity)) && (!EnterpriseUtil.isAdminServer()) && (!isTablet))
/*      */         {
/* 3436 */           String monitorType = request.getParameter("monitortype");
/* 3437 */           if (!"APM-Insight-Application".equals(monitorType)) {
/* 3438 */             String time = p1.getProperty("timeinMillis");
/* 3439 */             String constructURL = "/jsp/AttributeDetails.jsp?fromAlerts=true&haid=" + EID + "&time=" + time;
/* 3440 */             String link = "<a href=" + constructURL + "  class=\"StaticLinks-Anomaly\" target='_blank'>" + FormatUtil.getString("am.webclient.anomaly.anomalydashboard.message") + "</a>";
/*      */             
/* 3442 */             if (EID.indexOf("_3200") == -1) {
/* 3443 */               out.write("\n      <tr>\n          <td colspan=\"2\">   \n                 <span class=\"bodytext\">");
/* 3444 */               out.print(FormatUtil.getString("am.webclient.anomaly.anomalydashboardlink.message", new String[] { link }));
/* 3445 */               out.write("</span>\n                \n      </tr>\n        ");
/*      */             }
/*      */           }
/*      */         }
/* 3449 */         out.write("\n      \n  ");
/* 3450 */         if (_jspx_meth_c_005fset_005f25(_jspx_page_context))
/*      */           return;
/* 3452 */         out.write(" \n  ");
/* 3453 */         if (_jspx_meth_c_005fset_005f26(_jspx_page_context))
/*      */           return;
/* 3455 */         out.write(" \n  \n  ");
/* 3456 */         if (_jspx_meth_c_005fset_005f27(_jspx_page_context))
/*      */           return;
/* 3458 */         out.write(32);
/* 3459 */         out.write(10);
/* 3460 */         out.write(32);
/* 3461 */         if (_jspx_meth_c_005fchoose_005f10(_jspx_page_context))
/*      */           return;
/* 3463 */         out.write(32);
/* 3464 */         if (_jspx_meth_c_005fset_005f28(_jspx_page_context))
/*      */           return;
/* 3466 */         out.write(" \n  <!--tr> \n    <td align=\"left\"  class=\"bodytext\"><span class=\"bodytext\">");
/* 3467 */         if (_jspx_meth_fmt_005fmessage_005f28(_jspx_page_context))
/*      */           return;
/* 3469 */         out.write("</span></td>\n    <td  class=\"bodytext\">");
/* 3470 */         if (_jspx_meth_c_005fout_005f49(_jspx_page_context))
/*      */           return;
/* 3472 */         out.write("</td>\n  </tr-->\n  ");
/* 3473 */         if (_jspx_meth_c_005fset_005f29(_jspx_page_context))
/*      */           return;
/* 3475 */         out.write(" \n  <!--tr> \n    <td align=\"left\"  class=\"bodytext\">");
/* 3476 */         if (_jspx_meth_fmt_005fmessage_005f29(_jspx_page_context))
/*      */           return;
/* 3478 */         out.write("</td>\n    <td  class=\"bodytext\">");
/* 3479 */         if (_jspx_meth_c_005fout_005f50(_jspx_page_context))
/*      */           return;
/* 3481 */         out.write("</td>\n  </tr-->\n  ");
/* 3482 */         if (_jspx_meth_c_005fset_005f30(_jspx_page_context))
/*      */           return;
/* 3484 */         out.write(32);
/* 3485 */         if (_jspx_meth_c_005fforEach_005f2(_jspx_page_context))
/*      */           return;
/* 3487 */         out.write(" \n</table>\n  </td>\n</tr>\n</table>\n");
/* 3488 */         out.write("\n<br> \n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n\t<tr>\n\t\t<td height=\"25\" class=\"tableheading\">");
/* 3489 */         if (_jspx_meth_fmt_005fmessage_005f30(_jspx_page_context))
/*      */           return;
/* 3491 */         out.write("</td>\n\t</tr>\n\t<tr>\n\t\t<td>");
/* 3492 */         JspRuntimeLibrary.include(request, response, "/fault/AlarmDetails.do?method=viewAnnotationAndHistory&displayname=Annotations", out, false);
/* 3493 */         out.write("</td>\n\t</tr>\n\t</table>\n\t<br>\n\t\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t  <tr class=\"tabBtmLine\">\n   \t\t<td nowrap=\"nowrap\">\n          \t<table id=\"InnerTab\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n              <tbody>\n                <tr>\n                  <td width=\"17\"></td>\n\n                  <td>\n                  <table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                      <tbody>\n                        <tr>\n                          <td class=\"tbSelected_Left\" id=\"HealthHistory-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                          <td class=\"tbSelected_Middle\" id=\"HealthHistory-middle\">\n                        <a href=\"javascript:showHideHistory('HealthHistory','AvailabilityHistory')\" >&nbsp;<span  class=\"tabLink\">");
/* 3494 */         out.print(FormatUtil.getString("am.webclient.hometab.healthdashboard.title"));
/* 3495 */         out.write("</span></a>\n                          </td>\n                          <td class=\"tbSelected_Right\" id=\"HealthHistory-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                        </tr>\n                      </tbody>\n                    </table>\n                  </td>\n                  \n                  <td>\n                  \t<table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                      <tbody>\n                        <tr>\n                          <td class=\"tbUnselected_Left\" id=\"AvailabilityHistory-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                          <td class=\"tbUnselected_Middle\" id=\"AvailabilityHistory-middle\">\n                        \t<a href=\"javascript:showHideHistory('AvailabilityHistory','HealthHistory')\" >&nbsp;<span  class=\"tabLink\">");
/* 3496 */         out.print(FormatUtil.getString("am.webclient.dasboard.availability.title"));
/* 3497 */         out.write("</span></a>\n                          </td>\n                          <td class=\"tbUnselected_Right\" id=\"AvailabilityHistory-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                        </tr>\n                      </tbody>\n                    </table>\n                  </td>\n \t\t\t\t</tr>\n \t\t\t</table>\n \t\t</td>\n \t  </tr>\n \t</table>\n<br>\n\n\t<div id=\"HealthHistory\">\n\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n\t\t<tr><td >\n\t\t");
/* 3498 */         if (history) {
/* 3499 */           out.write("\n\t\t\t\t");
/* 3500 */           JspRuntimeLibrary.include(request, response, "/webclient/fault/jsp/eventList.jsp" + ("/webclient/fault/jsp/eventList.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("historyDetails", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("health", request.getCharacterEncoding()), out, false);
/* 3501 */           out.write("\n\t\t\t");
/*      */         }
/*      */         else {
/* 3504 */           out.write("  \n\t\t\t<span class=\"bodytext\">");
/* 3505 */           if (_jspx_meth_fmt_005fmessage_005f31(_jspx_page_context))
/*      */             return;
/* 3507 */           out.write("</span>\n\t\t");
/*      */         }
/* 3509 */         out.write("    \n\t\t</td></tr>\n\t\t</table>\n  \t</div>\n  \n\t  <div id=\"AvailabilityHistory\" style=\"display:none\">\n\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n\t\t\t<tr><td >\n\t\t\t");
/* 3510 */         if (history) {
/* 3511 */           out.write("\n\t\t\t\t\t\n\t\t\t\t\t");
/* 3512 */           JspRuntimeLibrary.include(request, response, "/webclient/fault/jsp/eventList.jsp" + ("/webclient/fault/jsp/eventList.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("historyDetails", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("availability", request.getCharacterEncoding()), out, false);
/* 3513 */           out.write("\n\t\t\t\t\t\n\t\t\t\t");
/*      */         }
/*      */         else {
/* 3516 */           out.write("  \n\t\t\t\t<span class=\"bodytext\">");
/* 3517 */           if (_jspx_meth_fmt_005fmessage_005f32(_jspx_page_context))
/*      */             return;
/* 3519 */           out.write("</span>\n\t\t\t");
/*      */         }
/* 3521 */         out.write("    \n\t\t\t</td></tr>\n\t\t</table>\n\t  </div>\n\n <script>\n \n function showHideHistory(showid,hideid){\n\t \n\t jQuery(\"#\"+showid).show();\n\t jQuery(\"#\"+showid+\"-left\").attr(\"class\", \"tbSelected_Left\");\t\t // NO I18N \n\t jQuery(\"#\"+showid+\"-middle\").attr(\"class\", \"tbSelected_Middle\");\t\t// NO I18N \n\t jQuery(\"#\"+showid+\"-right\").attr(\"class\", \"tbselected_Right\");\t\t// NO I18N \n\t \n\t jQuery(\"#\"+hideid).hide();\n\t jQuery(\"#\"+hideid+\"-left\").attr(\"class\", \"tbUnselected_Left\");\t\t// NO I18N \n\t jQuery(\"#\"+hideid+\"-middle\").attr(\"class\", \"tbUnselected_Middle\");\t\t// NO I18N \n\t jQuery(\"#\"+hideid+\"-right\").attr(\"class\", \"tbUnselected_Right\");\t\t// NO I18N \n }\n\nfunction openURLInParent(url)\n{\n  window.opener.location.href=url;\n}\n\nfunction displaycustomFields(){\n\tvar resid = '");
/* 3522 */         out.print(((Properties)request.getAttribute("alertProp")).getProperty("resourceid"));
/* 3523 */         out.write("'\n\tfnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid='+resid) // NO I18N\n}\n\njQuery(document).ready(function(){\n\t\n\t\n\tvar scrollEvent = false;\n\tjQuery(window).scroll(function(){\n\t\tif(jQuery(window).scrollTop() + jQuery(window).height() > jQuery(document).height() - 100){\n\t\t\t  if(scrollEvent){\n\t\t\t\t  return false;\n\t\t\t  }else{\n\t\t\t\t  scrollEvent = true;\n\t\t\t\t  loadEventList();\n\t\t\t\t  scrollEvent = false\n\t\t\t\t  \n\t\t\t  }\n\t\t\t}\n\t});\n});\n\nvar history_pageIndex = 25\nvar history_oldIndex = 0\nvar availability_pageIndex = 25\nvar availability_oldIndex = 0\nfunction loadEventList(){\n\t\n\tvar historyDetails = \"healthHistory\";\t\t// NO I18N \n\tvar healthHistory = jQuery(\"#HealthHistory\").css('display')\t\t// NO I18N \n\tvar isHealth = true;\n\tvar pageIndex = history_pageIndex\n\tvar oldIndex = history_oldIndex\n\tif(healthHistory == 'none'){\n\t\tisHealth = false;\n\t\thistoryDetails = \"availabilityHistory\";\t\t// NO I18N \n\t\tpageIndex = availability_pageIndex\n\t\toldIndex = availability_oldIndex\n\t}\n\t\n\t\n\tjQuery(\"#\"+historyDetails+\"_loadingIcon\").show();\t\t// NO I18N\n");
/* 3524 */         out.write("\tvar resourceid = '");
/* 3525 */         out.print(((Properties)request.getAttribute("alertProp")).getProperty("resourceid"));
/* 3526 */         out.write("'\n\tvar url = \"/fault/AlarmDetails.do?method=loadEventHistoryData&pageIndex=\"+pageIndex+\"&historyDetails=\"+historyDetails+\"&entity=");
/* 3527 */         if (_jspx_meth_c_005fout_005f53(_jspx_page_context))
/*      */           return;
/* 3529 */         out.write("&resid=\"+resourceid\t\t// NO I18N\n\tif(pageIndex > oldIndex){\n\t\tif(isHealth){\n\t\t\thistory_oldIndex += 25\n\t\t}else{\n\t\t\tavailability_oldIndex += 25\n\t\t}\n\t\t\n\t   jQuery.ajax({\n\t\t\turl:url,\n\t\t\tsuccess:function(data,status,jxhr){\n\t\t\t\t    if(jxhr.getResponseHeader(\"noDataResponse\") == 'true'){\n\t\t\t\t    \tjQuery(\"#\"+historyDetails+\"_loadingIcon\").hide();\t\t// NO I18N\n\t\t\t\t    \tif(isHealth){\n\t\t\t\t    \t\thistory_pageIndex = 0;\n\t\t\t\t\t\t}else{\n\t\t\t\t\t\t\tavailability_pageIndex = 0;\n\t\t\t\t\t\t}\n\t\t\t\t    \t\n\t\t\t\t    }else{\n\t\t\t\t\t\tjQuery(\"#\"+historyDetails+\"_details\").append(\"<tr><td colspan='5'>\"+data+\"<td></tr>\")\t\t// NO I18N\n\t\t\t\t\t\tjQuery(\"#\"+historyDetails+\"_loadingIcon\").hide();\t\t// NO I18N\n\t\t\t\t\t\tif(isHealth){\n\t\t\t\t\t\t\thistory_pageIndex += 25;\n\t\t\t\t\t\t}else{\n\t\t\t\t\t\t\tavailability_pageIndex += 25;\n\t\t\t\t\t\t}\n\t\t\t\t\t    \n\t\t\t\t\t}\n\t\t\t\t\n\t\t\t}\n\t\t});\n\t}else{\n\t\tjQuery(\"#\"+historyDetails+\"_loadingIcon\").hide();\t\t// NO I18N\n\t}\n}\n</script>\t  \n\t  \n\t");
/*      */       } else {
/* 3531 */         out.write("\n\n\n\t<table class=\"lrtbborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n\t\t <tr>\n\t          <td class=\"lrbborder\" align=\"center\" height=\"20\"><span class=\"bodytext\">");
/* 3532 */         if (_jspx_meth_fmt_005fmessage_005f33(_jspx_page_context))
/*      */           return;
/* 3534 */         out.write("</span></td>\n\t     </tr>\n\t</table>\n\n");
/*      */       }
/* 3536 */       out.write(10);
/* 3537 */       out.write(10);
/*      */     } catch (Throwable t) {
/* 3539 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3540 */         out = _jspx_out;
/* 3541 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3542 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3543 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3546 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3552 */     PageContext pageContext = _jspx_page_context;
/* 3553 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3555 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3556 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3557 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3559 */     _jspx_th_c_005fout_005f0.setValue("${ent}");
/* 3560 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3561 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3562 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3563 */       return true;
/*      */     }
/* 3565 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3566 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3571 */     PageContext pageContext = _jspx_page_context;
/* 3572 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3574 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3575 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3576 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 3577 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3578 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 3580 */         out.write("\n       ");
/* 3581 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3582 */           return true;
/* 3583 */         out.write("\n       ");
/* 3584 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3585 */           return true;
/* 3586 */         out.write("\n       ");
/* 3587 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3588 */           return true;
/* 3589 */         out.write("\n       ");
/* 3590 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3591 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3595 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3596 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3597 */       return true;
/*      */     }
/* 3599 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3600 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3605 */     PageContext pageContext = _jspx_page_context;
/* 3606 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3608 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3609 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3610 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3612 */     _jspx_th_c_005fwhen_005f0.setTest("${pickUpStatus == true}");
/* 3613 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3614 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 3616 */         out.write("\n         ");
/* 3617 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 3618 */           return true;
/* 3619 */         out.write("\n         ");
/* 3620 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 3621 */           return true;
/* 3622 */         out.write("\n       ");
/* 3623 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3624 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3628 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3629 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3630 */       return true;
/*      */     }
/* 3632 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3633 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3638 */     PageContext pageContext = _jspx_page_context;
/* 3639 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3641 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3642 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3643 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3645 */     _jspx_th_c_005fset_005f0.setVar("image");
/*      */     
/* 3647 */     _jspx_th_c_005fset_005f0.setValue("/webclient/common/images/tick.gif");
/* 3648 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3649 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3650 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3651 */       return true;
/*      */     }
/* 3653 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3659 */     PageContext pageContext = _jspx_page_context;
/* 3660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3662 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3663 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3664 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3666 */     _jspx_th_c_005fset_005f1.setVar("result");
/*      */     
/* 3668 */     _jspx_th_c_005fset_005f1.setValue("webclient.fault.alarmdetails.pickup.success");
/* 3669 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3670 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3671 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 3672 */       return true;
/*      */     }
/* 3674 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 3675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3680 */     PageContext pageContext = _jspx_page_context;
/* 3681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3683 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3684 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3685 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3687 */     _jspx_th_c_005fwhen_005f1.setTest("${pickUpStatus == false}");
/* 3688 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3689 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 3691 */         out.write("\n         ");
/* 3692 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 3693 */           return true;
/* 3694 */         out.write("\n         ");
/* 3695 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 3696 */           return true;
/* 3697 */         out.write("\n       ");
/* 3698 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3699 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3703 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3704 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3705 */       return true;
/*      */     }
/* 3707 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3713 */     PageContext pageContext = _jspx_page_context;
/* 3714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3716 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3717 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3718 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3720 */     _jspx_th_c_005fset_005f2.setVar("image");
/*      */     
/* 3722 */     _jspx_th_c_005fset_005f2.setValue("/webclient/common/images/cross.gif");
/* 3723 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3724 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3725 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 3726 */       return true;
/*      */     }
/* 3728 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 3729 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3734 */     PageContext pageContext = _jspx_page_context;
/* 3735 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3737 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3738 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3739 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3741 */     _jspx_th_c_005fset_005f3.setVar("result");
/*      */     
/* 3743 */     _jspx_th_c_005fset_005f3.setValue("webclient.fault.alarmdetails.pickup.failure");
/* 3744 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3745 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3746 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 3747 */       return true;
/*      */     }
/* 3749 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 3750 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3755 */     PageContext pageContext = _jspx_page_context;
/* 3756 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3758 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3759 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3760 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 3761 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3762 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3764 */         out.write("\n       ");
/* 3765 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3766 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3770 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3771 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3772 */       return true;
/*      */     }
/* 3774 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3775 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3780 */     PageContext pageContext = _jspx_page_context;
/* 3781 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3783 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3784 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3785 */     _jspx_th_c_005fchoose_005f1.setParent(null);
/* 3786 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3787 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 3789 */         out.write("\n       ");
/* 3790 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 3791 */           return true;
/* 3792 */         out.write("\n       ");
/* 3793 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 3794 */           return true;
/* 3795 */         out.write("\n      ");
/* 3796 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 3797 */           return true;
/* 3798 */         out.write("\n     ");
/* 3799 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3800 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3804 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3805 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3806 */       return true;
/*      */     }
/* 3808 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3809 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3814 */     PageContext pageContext = _jspx_page_context;
/* 3815 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3817 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3818 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3819 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 3821 */     _jspx_th_c_005fwhen_005f2.setTest("${UnPickStatus == true}");
/* 3822 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3823 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 3825 */         out.write("\n         ");
/* 3826 */         if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 3827 */           return true;
/* 3828 */         out.write("\n         ");
/* 3829 */         if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/* 3830 */           return true;
/* 3831 */         out.write("\n       ");
/* 3832 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3833 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3837 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3838 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3839 */       return true;
/*      */     }
/* 3841 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3842 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3847 */     PageContext pageContext = _jspx_page_context;
/* 3848 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3850 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3851 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 3852 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 3854 */     _jspx_th_c_005fset_005f4.setVar("image");
/*      */     
/* 3856 */     _jspx_th_c_005fset_005f4.setValue("/webclient/common/images/tick.gif");
/* 3857 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 3858 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 3859 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 3860 */       return true;
/*      */     }
/* 3862 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/* 3863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3868 */     PageContext pageContext = _jspx_page_context;
/* 3869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3871 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3872 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 3873 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 3875 */     _jspx_th_c_005fset_005f5.setVar("result");
/*      */     
/* 3877 */     _jspx_th_c_005fset_005f5.setValue("webclient.fault.alarmdetails.unpick.succes");
/* 3878 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 3879 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 3880 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 3881 */       return true;
/*      */     }
/* 3883 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 3884 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3889 */     PageContext pageContext = _jspx_page_context;
/* 3890 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3892 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3893 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3894 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 3896 */     _jspx_th_c_005fwhen_005f3.setTest("${UnPickStatus == false}");
/* 3897 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3898 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 3900 */         out.write("\n         ");
/* 3901 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 3902 */           return true;
/* 3903 */         out.write("\n         ");
/* 3904 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 3905 */           return true;
/* 3906 */         out.write("\n       ");
/* 3907 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3908 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3912 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3913 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3914 */       return true;
/*      */     }
/* 3916 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3917 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3922 */     PageContext pageContext = _jspx_page_context;
/* 3923 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3925 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3926 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 3927 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 3929 */     _jspx_th_c_005fset_005f6.setVar("image");
/*      */     
/* 3931 */     _jspx_th_c_005fset_005f6.setValue("/webclient/common/images/cross.gif");
/* 3932 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 3933 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 3934 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 3935 */       return true;
/*      */     }
/* 3937 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 3938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3943 */     PageContext pageContext = _jspx_page_context;
/* 3944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3946 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3947 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 3948 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 3950 */     _jspx_th_c_005fset_005f7.setVar("result");
/*      */     
/* 3952 */     _jspx_th_c_005fset_005f7.setValue("webclient.fault.alarmdetails.unpick.failure");
/* 3953 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 3954 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 3955 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f7);
/* 3956 */       return true;
/*      */     }
/* 3958 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f7);
/* 3959 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3964 */     PageContext pageContext = _jspx_page_context;
/* 3965 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3967 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3968 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3969 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 3970 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3971 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 3973 */         out.write("\n     ");
/* 3974 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3975 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3979 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3980 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3981 */       return true;
/*      */     }
/* 3983 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3989 */     PageContext pageContext = _jspx_page_context;
/* 3990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3992 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3993 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3994 */     _jspx_th_c_005fchoose_005f2.setParent(null);
/* 3995 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3996 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 3998 */         out.write("\n     ");
/* 3999 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 4000 */           return true;
/* 4001 */         out.write("\n    ");
/* 4002 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 4003 */           return true;
/* 4004 */         out.write("\n    ");
/* 4005 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 4006 */           return true;
/* 4007 */         out.write("\n   ");
/* 4008 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 4009 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4013 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 4014 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 4015 */       return true;
/*      */     }
/* 4017 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 4018 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4023 */     PageContext pageContext = _jspx_page_context;
/* 4024 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4026 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4027 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 4028 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 4030 */     _jspx_th_c_005fwhen_005f4.setTest("${clearStatus == true}");
/* 4031 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 4032 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 4034 */         out.write("\n         ");
/* 4035 */         if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/* 4036 */           return true;
/* 4037 */         out.write("\n         ");
/* 4038 */         if (_jspx_meth_c_005fset_005f9(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/* 4039 */           return true;
/* 4040 */         out.write("\n     ");
/* 4041 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 4042 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4046 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 4047 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 4048 */       return true;
/*      */     }
/* 4050 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 4051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4056 */     PageContext pageContext = _jspx_page_context;
/* 4057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4059 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4060 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 4061 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 4063 */     _jspx_th_c_005fset_005f8.setVar("image");
/*      */     
/* 4065 */     _jspx_th_c_005fset_005f8.setValue("/webclient/common/images/tick.gif");
/* 4066 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 4067 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 4068 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f8);
/* 4069 */       return true;
/*      */     }
/* 4071 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f8);
/* 4072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4077 */     PageContext pageContext = _jspx_page_context;
/* 4078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4080 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4081 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 4082 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 4084 */     _jspx_th_c_005fset_005f9.setVar("result");
/*      */     
/* 4086 */     _jspx_th_c_005fset_005f9.setValue("webclient.fault.alarmdetails.clear.success");
/* 4087 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 4088 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 4089 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f9);
/* 4090 */       return true;
/*      */     }
/* 4092 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f9);
/* 4093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4098 */     PageContext pageContext = _jspx_page_context;
/* 4099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4101 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4102 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 4103 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 4105 */     _jspx_th_c_005fwhen_005f5.setTest("${clearStatus == false}");
/* 4106 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 4107 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 4109 */         out.write("\n         ");
/* 4110 */         if (_jspx_meth_c_005fset_005f10(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/* 4111 */           return true;
/* 4112 */         out.write("\n         ");
/* 4113 */         if (_jspx_meth_c_005fset_005f11(_jspx_th_c_005fwhen_005f5, _jspx_page_context))
/* 4114 */           return true;
/* 4115 */         out.write("\n    ");
/* 4116 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 4117 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4121 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 4122 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 4123 */       return true;
/*      */     }
/* 4125 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 4126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f10(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4131 */     PageContext pageContext = _jspx_page_context;
/* 4132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4134 */     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4135 */     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 4136 */     _jspx_th_c_005fset_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 4138 */     _jspx_th_c_005fset_005f10.setVar("image");
/*      */     
/* 4140 */     _jspx_th_c_005fset_005f10.setValue("/webclient/common/images/cross.gif");
/* 4141 */     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 4142 */     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 4143 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f10);
/* 4144 */       return true;
/*      */     }
/* 4146 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f10);
/* 4147 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f11(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4152 */     PageContext pageContext = _jspx_page_context;
/* 4153 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4155 */     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4156 */     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 4157 */     _jspx_th_c_005fset_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 4159 */     _jspx_th_c_005fset_005f11.setVar("result");
/*      */     
/* 4161 */     _jspx_th_c_005fset_005f11.setValue("webclient.fault.alarmdetails.clear.failure");
/* 4162 */     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 4163 */     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 4164 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f11);
/* 4165 */       return true;
/*      */     }
/* 4167 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f11);
/* 4168 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4173 */     PageContext pageContext = _jspx_page_context;
/* 4174 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4176 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4177 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 4178 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 4179 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 4180 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 4182 */         out.write("\n   ");
/* 4183 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 4184 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4188 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 4189 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 4190 */       return true;
/*      */     }
/* 4192 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 4193 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4198 */     PageContext pageContext = _jspx_page_context;
/* 4199 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4201 */     SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4202 */     _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 4203 */     _jspx_th_c_005fset_005f12.setParent(null);
/*      */     
/* 4205 */     _jspx_th_c_005fset_005f12.setVar("severity");
/*      */     
/* 4207 */     _jspx_th_c_005fset_005f12.setValue("${alertProp.stringseverity}");
/* 4208 */     int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 4209 */     if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 4210 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f12);
/* 4211 */       return true;
/*      */     }
/* 4213 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f12);
/* 4214 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4219 */     PageContext pageContext = _jspx_page_context;
/* 4220 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4222 */     SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4223 */     _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 4224 */     _jspx_th_c_005fset_005f13.setParent(null);
/*      */     
/* 4226 */     _jspx_th_c_005fset_005f13.setVar("groupName");
/*      */     
/* 4228 */     _jspx_th_c_005fset_005f13.setValue("${alertProp.groupName}");
/* 4229 */     int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 4230 */     if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 4231 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f13);
/* 4232 */       return true;
/*      */     }
/* 4234 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f13);
/* 4235 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4240 */     PageContext pageContext = _jspx_page_context;
/* 4241 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4243 */     SetTag _jspx_th_c_005fset_005f14 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4244 */     _jspx_th_c_005fset_005f14.setPageContext(_jspx_page_context);
/* 4245 */     _jspx_th_c_005fset_005f14.setParent(null);
/*      */     
/* 4247 */     _jspx_th_c_005fset_005f14.setVar("groupName");
/*      */     
/* 4249 */     _jspx_th_c_005fset_005f14.setValue("${alertProp.who}");
/* 4250 */     int _jspx_eval_c_005fset_005f14 = _jspx_th_c_005fset_005f14.doStartTag();
/* 4251 */     if (_jspx_th_c_005fset_005f14.doEndTag() == 5) {
/* 4252 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f14);
/* 4253 */       return true;
/*      */     }
/* 4255 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f14);
/* 4256 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4261 */     PageContext pageContext = _jspx_page_context;
/* 4262 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4264 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4265 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4266 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 4268 */     _jspx_th_fmt_005fmessage_005f0.setKey("webclient.fault.details.properties.entity");
/* 4269 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4270 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4271 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4272 */       return true;
/*      */     }
/* 4274 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4275 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4280 */     PageContext pageContext = _jspx_page_context;
/* 4281 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4283 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4284 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4285 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 4287 */     _jspx_th_c_005fout_005f1.setValue("${alertProp.entity}");
/* 4288 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4289 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4290 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4291 */       return true;
/*      */     }
/* 4293 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4294 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f15(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4299 */     PageContext pageContext = _jspx_page_context;
/* 4300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4302 */     SetTag _jspx_th_c_005fset_005f15 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 4303 */     _jspx_th_c_005fset_005f15.setPageContext(_jspx_page_context);
/* 4304 */     _jspx_th_c_005fset_005f15.setParent(null);
/*      */     
/* 4306 */     _jspx_th_c_005fset_005f15.setTarget("${alertProp}");
/*      */     
/* 4308 */     _jspx_th_c_005fset_005f15.setProperty("entity");
/*      */     
/* 4310 */     _jspx_th_c_005fset_005f15.setValue("${null}");
/* 4311 */     int _jspx_eval_c_005fset_005f15 = _jspx_th_c_005fset_005f15.doStartTag();
/* 4312 */     if (_jspx_th_c_005fset_005f15.doEndTag() == 5) {
/* 4313 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f15);
/* 4314 */       return true;
/*      */     }
/* 4316 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f15);
/* 4317 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4322 */     PageContext pageContext = _jspx_page_context;
/* 4323 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4325 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4326 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4327 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*      */     
/* 4329 */     _jspx_th_fmt_005fmessage_005f1.setKey("webclient.fault.details.properties.source");
/* 4330 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4331 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4332 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4333 */       return true;
/*      */     }
/* 4335 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4336 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4341 */     PageContext pageContext = _jspx_page_context;
/* 4342 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4344 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4345 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4346 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 4348 */     _jspx_th_c_005fout_005f2.setValue("${alertProp.source}");
/* 4349 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4350 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4351 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4352 */       return true;
/*      */     }
/* 4354 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4355 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4360 */     PageContext pageContext = _jspx_page_context;
/* 4361 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4363 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4364 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4365 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4367 */     _jspx_th_c_005fout_005f3.setValue("${CI_INFO_URL}");
/* 4368 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4369 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4370 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4371 */       return true;
/*      */     }
/* 4373 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4379 */     PageContext pageContext = _jspx_page_context;
/* 4380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4382 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4383 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4384 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4386 */     _jspx_th_c_005fout_005f4.setValue("${CI_RL_URL}");
/* 4387 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4388 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4389 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4390 */       return true;
/*      */     }
/* 4392 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4393 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4398 */     PageContext pageContext = _jspx_page_context;
/* 4399 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4401 */     SetTag _jspx_th_c_005fset_005f16 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 4402 */     _jspx_th_c_005fset_005f16.setPageContext(_jspx_page_context);
/* 4403 */     _jspx_th_c_005fset_005f16.setParent(null);
/*      */     
/* 4405 */     _jspx_th_c_005fset_005f16.setTarget("${alertProp}");
/*      */     
/* 4407 */     _jspx_th_c_005fset_005f16.setProperty("source");
/*      */     
/* 4409 */     _jspx_th_c_005fset_005f16.setValue("${null}");
/* 4410 */     int _jspx_eval_c_005fset_005f16 = _jspx_th_c_005fset_005f16.doStartTag();
/* 4411 */     if (_jspx_th_c_005fset_005f16.doEndTag() == 5) {
/* 4412 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f16);
/* 4413 */       return true;
/*      */     }
/* 4415 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f16);
/* 4416 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4421 */     PageContext pageContext = _jspx_page_context;
/* 4422 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4424 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4425 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4426 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/*      */     
/* 4428 */     _jspx_th_fmt_005fmessage_005f2.setKey("table.heading.attribute");
/* 4429 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4430 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4431 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4432 */       return true;
/*      */     }
/* 4434 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4440 */     PageContext pageContext = _jspx_page_context;
/* 4441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4443 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4444 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4445 */     _jspx_th_c_005fout_005f5.setParent(null);
/*      */     
/* 4447 */     _jspx_th_c_005fout_005f5.setValue("${alertProp.category}");
/* 4448 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4449 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4450 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4451 */       return true;
/*      */     }
/* 4453 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4459 */     PageContext pageContext = _jspx_page_context;
/* 4460 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4462 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4463 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4464 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4466 */     _jspx_th_fmt_005fmessage_005f3.setKey("webclient.fault.alarm.Workorderurl");
/* 4467 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4468 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4469 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4470 */       return true;
/*      */     }
/* 4472 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4473 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4478 */     PageContext pageContext = _jspx_page_context;
/* 4479 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4481 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4482 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4483 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f7);
/*      */     
/* 4485 */     _jspx_th_c_005fout_005f6.setValue("${workOrderUrl}");
/* 4486 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4487 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4488 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4489 */       return true;
/*      */     }
/* 4491 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4497 */     PageContext pageContext = _jspx_page_context;
/* 4498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4500 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4501 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 4502 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 4503 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 4504 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 4506 */         out.write("\n\t\t\t\t\t\t-\n\t\t\t\t");
/* 4507 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 4508 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4512 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 4513 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 4514 */       return true;
/*      */     }
/* 4516 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 4517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fwhen_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4522 */     PageContext pageContext = _jspx_page_context;
/* 4523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4525 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4526 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4527 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f8);
/*      */     
/* 4529 */     _jspx_th_c_005fout_005f7.setValue("${param.entity}");
/* 4530 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4531 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4532 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4533 */       return true;
/*      */     }
/* 4535 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4536 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4541 */     PageContext pageContext = _jspx_page_context;
/* 4542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4544 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4545 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 4546 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 4547 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 4548 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 4550 */         out.write("\n\t\t\t\t\t-\n\t\t\t\t");
/* 4551 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 4552 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4556 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 4557 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 4558 */       return true;
/*      */     }
/* 4560 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 4561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f17(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4566 */     PageContext pageContext = _jspx_page_context;
/* 4567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4569 */     SetTag _jspx_th_c_005fset_005f17 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 4570 */     _jspx_th_c_005fset_005f17.setPageContext(_jspx_page_context);
/* 4571 */     _jspx_th_c_005fset_005f17.setParent(null);
/*      */     
/* 4573 */     _jspx_th_c_005fset_005f17.setTarget("${alertProp}");
/*      */     
/* 4575 */     _jspx_th_c_005fset_005f17.setProperty("category");
/*      */     
/* 4577 */     _jspx_th_c_005fset_005f17.setValue("${null}");
/* 4578 */     int _jspx_eval_c_005fset_005f17 = _jspx_th_c_005fset_005f17.doStartTag();
/* 4579 */     if (_jspx_th_c_005fset_005f17.doEndTag() == 5) {
/* 4580 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f17);
/* 4581 */       return true;
/*      */     }
/* 4583 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f17);
/* 4584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4589 */     PageContext pageContext = _jspx_page_context;
/* 4590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4592 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4593 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 4594 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/*      */     
/* 4596 */     _jspx_th_fmt_005fmessage_005f4.setKey("webclient.fault.details.properties.createdtime");
/* 4597 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 4598 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 4599 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4600 */       return true;
/*      */     }
/* 4602 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4608 */     PageContext pageContext = _jspx_page_context;
/* 4609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4611 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4612 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 4613 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/*      */     
/* 4615 */     _jspx_th_fmt_005fmessage_005f5.setKey("Time");
/* 4616 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 4617 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 4618 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 4619 */       return true;
/*      */     }
/* 4621 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 4622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4627 */     PageContext pageContext = _jspx_page_context;
/* 4628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4630 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4631 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4632 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 4634 */     _jspx_th_c_005fif_005f3.setTest("${!empty associatedMG }");
/* 4635 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4636 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 4638 */         out.write(10);
/* 4639 */         out.write(32);
/* 4640 */         if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 4641 */           return true;
/* 4642 */         out.write(10);
/* 4643 */         out.write(32);
/* 4644 */         out.write(32);
/* 4645 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4646 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4650 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4651 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4652 */       return true;
/*      */     }
/* 4654 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4655 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4660 */     PageContext pageContext = _jspx_page_context;
/* 4661 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4663 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4664 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 4665 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4667 */     _jspx_th_c_005fif_005f4.setTest("${param.monitortype != 'HAI'}");
/* 4668 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 4669 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 4671 */         out.write("\n  <tr class=\"alarmheader\" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\">\n  <td align=\"left\"  class=\"whitegrayborder\"><span class=\"bodytext\">");
/* 4672 */         if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 4673 */           return true;
/* 4674 */         out.write("</span></td>\n      <td  class=\"yellowgrayborder\"><span class=\"bodytext\">");
/* 4675 */         if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 4676 */           return true;
/* 4677 */         out.write("</span></td>\n  </tr>\n  ");
/* 4678 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4679 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4683 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 4684 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4685 */       return true;
/*      */     }
/* 4687 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 4688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4693 */     PageContext pageContext = _jspx_page_context;
/* 4694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4696 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4697 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 4698 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 4700 */     _jspx_th_fmt_005fmessage_005f6.setKey("webclient.fault.details.properties.AssociatedMonitorGroups");
/* 4701 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 4702 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 4703 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4704 */       return true;
/*      */     }
/* 4706 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4707 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4712 */     PageContext pageContext = _jspx_page_context;
/* 4713 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4715 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4716 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4717 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 4719 */     _jspx_th_c_005fforEach_005f0.setVar("result");
/*      */     
/* 4721 */     _jspx_th_c_005fforEach_005f0.setItems("${associatedMG}");
/*      */     
/* 4723 */     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 4724 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 4726 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4727 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 4729 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4730 */             return true;
/* 4731 */           out.write(10);
/* 4732 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4733 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4737 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 4738 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4741 */         int tmp192_191 = 0; int[] tmp192_189 = _jspx_push_body_count_c_005fforEach_005f0; int tmp194_193 = tmp192_189[tmp192_191];tmp192_189[tmp192_191] = (tmp194_193 - 1); if (tmp194_193 <= 0) break;
/* 4742 */         out = _jspx_page_context.popBody(); }
/* 4743 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4745 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4746 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 4748 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4753 */     PageContext pageContext = _jspx_page_context;
/* 4754 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4756 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 4757 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4758 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4760 */     _jspx_th_c_005fforEach_005f1.setVar("result1");
/*      */     
/* 4762 */     _jspx_th_c_005fforEach_005f1.setItems("${result}");
/* 4763 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 4765 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4766 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 4768 */           out.write(10);
/* 4769 */           boolean bool; if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4770 */             return true;
/* 4771 */           out.write("\n    ");
/* 4772 */           if (_jspx_meth_c_005fchoose_005f6(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4773 */             return true;
/* 4774 */           out.write(10);
/* 4775 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4776 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4780 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 4781 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4784 */         int tmp232_231 = 0; int[] tmp232_229 = _jspx_push_body_count_c_005fforEach_005f1; int tmp234_233 = tmp232_229[tmp232_231];tmp232_229[tmp232_231] = (tmp234_233 - 1); if (tmp234_233 <= 0) break;
/* 4785 */         out = _jspx_page_context.popBody(); }
/* 4786 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 4788 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4789 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 4791 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4796 */     PageContext pageContext = _jspx_page_context;
/* 4797 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4799 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4800 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4801 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4803 */     _jspx_th_c_005fout_005f8.setValue("${result1}");
/* 4804 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4805 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4806 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4807 */       return true;
/*      */     }
/* 4809 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4810 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f6(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4815 */     PageContext pageContext = _jspx_page_context;
/* 4816 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4818 */     ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4819 */     _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 4820 */     _jspx_th_c_005fchoose_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 4821 */     int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 4822 */     if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */       for (;;) {
/* 4824 */         if (_jspx_meth_c_005fwhen_005f9(_jspx_th_c_005fchoose_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4825 */           return true;
/* 4826 */         out.write("\n    ");
/* 4827 */         if (_jspx_meth_c_005fotherwise_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4828 */           return true;
/* 4829 */         out.write("\n    ");
/* 4830 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 4831 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4835 */     if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 4836 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 4837 */       return true;
/*      */     }
/* 4839 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 4840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f9(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4845 */     PageContext pageContext = _jspx_page_context;
/* 4846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4848 */     WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4849 */     _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 4850 */     _jspx_th_c_005fwhen_005f9.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*      */     
/* 4852 */     _jspx_th_c_005fwhen_005f9.setTest("${status.last}");
/* 4853 */     int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 4854 */     if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */       for (;;) {
/* 4856 */         out.write(" \n      \n    ");
/* 4857 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 4858 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4862 */     if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 4863 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 4864 */       return true;
/*      */     }
/* 4866 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 4867 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4872 */     PageContext pageContext = _jspx_page_context;
/* 4873 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4875 */     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4876 */     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 4877 */     _jspx_th_c_005fotherwise_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/* 4878 */     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 4879 */     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */       for (;;) {
/* 4881 */         out.write(44);
/* 4882 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 4883 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4887 */     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 4888 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 4889 */       return true;
/*      */     }
/* 4891 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 4892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4897 */     PageContext pageContext = _jspx_page_context;
/* 4898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4900 */     SetTag _jspx_th_c_005fset_005f18 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 4901 */     _jspx_th_c_005fset_005f18.setPageContext(_jspx_page_context);
/* 4902 */     _jspx_th_c_005fset_005f18.setParent(null);
/*      */     
/* 4904 */     _jspx_th_c_005fset_005f18.setTarget("${alertProp}");
/*      */     
/* 4906 */     _jspx_th_c_005fset_005f18.setProperty("createTime");
/*      */     
/* 4908 */     _jspx_th_c_005fset_005f18.setValue("${null}");
/* 4909 */     int _jspx_eval_c_005fset_005f18 = _jspx_th_c_005fset_005f18.doStartTag();
/* 4910 */     if (_jspx_th_c_005fset_005f18.doEndTag() == 5) {
/* 4911 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f18);
/* 4912 */       return true;
/*      */     }
/* 4914 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f18);
/* 4915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4920 */     PageContext pageContext = _jspx_page_context;
/* 4921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4923 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4924 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 4925 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/*      */     
/* 4927 */     _jspx_th_fmt_005fmessage_005f7.setKey("webclient.fault.details.properties.modttime");
/* 4928 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 4929 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 4930 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4931 */       return true;
/*      */     }
/* 4933 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4934 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4939 */     PageContext pageContext = _jspx_page_context;
/* 4940 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4942 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4943 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 4944 */     _jspx_th_fmt_005fmessage_005f8.setParent(null);
/*      */     
/* 4946 */     _jspx_th_fmt_005fmessage_005f8.setKey("Time");
/* 4947 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 4948 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 4949 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4950 */       return true;
/*      */     }
/* 4952 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4953 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f19(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4958 */     PageContext pageContext = _jspx_page_context;
/* 4959 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4961 */     SetTag _jspx_th_c_005fset_005f19 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 4962 */     _jspx_th_c_005fset_005f19.setPageContext(_jspx_page_context);
/* 4963 */     _jspx_th_c_005fset_005f19.setParent(null);
/*      */     
/* 4965 */     _jspx_th_c_005fset_005f19.setTarget("${alertProp}");
/*      */     
/* 4967 */     _jspx_th_c_005fset_005f19.setProperty("modTime");
/*      */     
/* 4969 */     _jspx_th_c_005fset_005f19.setValue("${null}");
/* 4970 */     int _jspx_eval_c_005fset_005f19 = _jspx_th_c_005fset_005f19.doStartTag();
/* 4971 */     if (_jspx_th_c_005fset_005f19.doEndTag() == 5) {
/* 4972 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f19);
/* 4973 */       return true;
/*      */     }
/* 4975 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f19);
/* 4976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4981 */     PageContext pageContext = _jspx_page_context;
/* 4982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4984 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4985 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 4986 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 4988 */     _jspx_th_c_005fif_005f5.setTest("${alertProp.stringpreviousseverity == '5'}");
/* 4989 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 4990 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 4992 */         out.write("\n      ");
/* 4993 */         if (_jspx_meth_c_005fset_005f20(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 4994 */           return true;
/* 4995 */         out.write(10);
/* 4996 */         out.write(32);
/* 4997 */         out.write(32);
/* 4998 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 4999 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5003 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5004 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5005 */       return true;
/*      */     }
/* 5007 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5008 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f20(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5013 */     PageContext pageContext = _jspx_page_context;
/* 5014 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5016 */     SetTag _jspx_th_c_005fset_005f20 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 5017 */     _jspx_th_c_005fset_005f20.setPageContext(_jspx_page_context);
/* 5018 */     _jspx_th_c_005fset_005f20.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5020 */     _jspx_th_c_005fset_005f20.setTarget("${alertProp}");
/*      */     
/* 5022 */     _jspx_th_c_005fset_005f20.setProperty("stringpreviousseverity");
/*      */     
/* 5024 */     _jspx_th_c_005fset_005f20.setValue("Clear");
/* 5025 */     int _jspx_eval_c_005fset_005f20 = _jspx_th_c_005fset_005f20.doStartTag();
/* 5026 */     if (_jspx_th_c_005fset_005f20.doEndTag() == 5) {
/* 5027 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f20);
/* 5028 */       return true;
/*      */     }
/* 5030 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f20);
/* 5031 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5036 */     PageContext pageContext = _jspx_page_context;
/* 5037 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5039 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5040 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 5041 */     _jspx_th_c_005fif_005f6.setParent(null);
/*      */     
/* 5043 */     _jspx_th_c_005fif_005f6.setTest("${alertProp.stringpreviousseverity == '4'}");
/* 5044 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 5045 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 5047 */         out.write("\n      ");
/* 5048 */         if (_jspx_meth_c_005fset_005f21(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 5049 */           return true;
/* 5050 */         out.write(10);
/* 5051 */         out.write(32);
/* 5052 */         out.write(32);
/* 5053 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5054 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5058 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5059 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5060 */       return true;
/*      */     }
/* 5062 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5063 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f21(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5068 */     PageContext pageContext = _jspx_page_context;
/* 5069 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5071 */     SetTag _jspx_th_c_005fset_005f21 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 5072 */     _jspx_th_c_005fset_005f21.setPageContext(_jspx_page_context);
/* 5073 */     _jspx_th_c_005fset_005f21.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5075 */     _jspx_th_c_005fset_005f21.setTarget("${alertProp}");
/*      */     
/* 5077 */     _jspx_th_c_005fset_005f21.setProperty("stringpreviousseverity");
/*      */     
/* 5079 */     _jspx_th_c_005fset_005f21.setValue("Warning");
/* 5080 */     int _jspx_eval_c_005fset_005f21 = _jspx_th_c_005fset_005f21.doStartTag();
/* 5081 */     if (_jspx_th_c_005fset_005f21.doEndTag() == 5) {
/* 5082 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f21);
/* 5083 */       return true;
/*      */     }
/* 5085 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f21);
/* 5086 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5091 */     PageContext pageContext = _jspx_page_context;
/* 5092 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5094 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5095 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5096 */     _jspx_th_c_005fif_005f7.setParent(null);
/*      */     
/* 5098 */     _jspx_th_c_005fif_005f7.setTest("${alertProp.stringpreviousseverity == '1'}");
/* 5099 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5100 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 5102 */         out.write("\n      ");
/* 5103 */         if (_jspx_meth_c_005fset_005f22(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 5104 */           return true;
/* 5105 */         out.write(10);
/* 5106 */         out.write(32);
/* 5107 */         out.write(32);
/* 5108 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5109 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5113 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5114 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5115 */       return true;
/*      */     }
/* 5117 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5118 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f22(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5123 */     PageContext pageContext = _jspx_page_context;
/* 5124 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5126 */     SetTag _jspx_th_c_005fset_005f22 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 5127 */     _jspx_th_c_005fset_005f22.setPageContext(_jspx_page_context);
/* 5128 */     _jspx_th_c_005fset_005f22.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5130 */     _jspx_th_c_005fset_005f22.setTarget("${alertProp}");
/*      */     
/* 5132 */     _jspx_th_c_005fset_005f22.setProperty("stringpreviousseverity");
/*      */     
/* 5134 */     _jspx_th_c_005fset_005f22.setValue("Critical");
/* 5135 */     int _jspx_eval_c_005fset_005f22 = _jspx_th_c_005fset_005f22.doStartTag();
/* 5136 */     if (_jspx_th_c_005fset_005f22.doEndTag() == 5) {
/* 5137 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f22);
/* 5138 */       return true;
/*      */     }
/* 5140 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f22);
/* 5141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5146 */     PageContext pageContext = _jspx_page_context;
/* 5147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5149 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5150 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 5151 */     _jspx_th_fmt_005fmessage_005f9.setParent(null);
/*      */     
/* 5153 */     _jspx_th_fmt_005fmessage_005f9.setKey("webclient.fault.details.properties.severity");
/* 5154 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 5155 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 5156 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 5157 */       return true;
/*      */     }
/* 5159 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 5160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5165 */     PageContext pageContext = _jspx_page_context;
/* 5166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5168 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5169 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5170 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/*      */     
/* 5172 */     _jspx_th_c_005fout_005f9.setValue("${alertProp.stringseverity}");
/* 5173 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5174 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5175 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5176 */       return true;
/*      */     }
/* 5178 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5179 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5184 */     PageContext pageContext = _jspx_page_context;
/* 5185 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5187 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5188 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 5189 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/*      */     
/* 5191 */     _jspx_th_c_005fout_005f10.setValue("${alertProp.stringseverity}");
/* 5192 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 5193 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 5194 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5195 */       return true;
/*      */     }
/* 5197 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 5198 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5203 */     PageContext pageContext = _jspx_page_context;
/* 5204 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5206 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5207 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 5208 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/*      */     
/* 5210 */     _jspx_th_c_005fout_005f11.setValue("${alertProp.stringseverity}");
/* 5211 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 5212 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 5213 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5214 */       return true;
/*      */     }
/* 5216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 5217 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5222 */     PageContext pageContext = _jspx_page_context;
/* 5223 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5225 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5226 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 5227 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5229 */     _jspx_th_c_005fout_005f12.setValue("${haid}");
/* 5230 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 5231 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 5232 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5233 */       return true;
/*      */     }
/* 5235 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 5236 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5241 */     PageContext pageContext = _jspx_page_context;
/* 5242 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5244 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5245 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 5246 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5248 */     _jspx_th_c_005fout_005f13.setValue("${monitor}");
/* 5249 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 5250 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 5251 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5252 */       return true;
/*      */     }
/* 5254 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 5255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5260 */     PageContext pageContext = _jspx_page_context;
/* 5261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5263 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5264 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 5265 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5267 */     _jspx_th_c_005fout_005f14.setValue("${param.entity}");
/* 5268 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 5269 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 5270 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5271 */       return true;
/*      */     }
/* 5273 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 5274 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5279 */     PageContext pageContext = _jspx_page_context;
/* 5280 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5282 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5283 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 5284 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5286 */     _jspx_th_c_005fout_005f15.setValue("${source}");
/* 5287 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 5288 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 5289 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5290 */       return true;
/*      */     }
/* 5292 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 5293 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5298 */     PageContext pageContext = _jspx_page_context;
/* 5299 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5301 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5302 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 5303 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5305 */     _jspx_th_c_005fout_005f16.setValue("${category}");
/* 5306 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 5307 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 5308 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5309 */       return true;
/*      */     }
/* 5311 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 5312 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5317 */     PageContext pageContext = _jspx_page_context;
/* 5318 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5320 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5321 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 5322 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5324 */     _jspx_th_c_005fout_005f17.setValue("${param.redirectto}");
/* 5325 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 5326 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 5327 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5328 */       return true;
/*      */     }
/* 5330 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 5331 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5336 */     PageContext pageContext = _jspx_page_context;
/* 5337 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5339 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5340 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 5341 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 5343 */     _jspx_th_fmt_005fmessage_005f10.setKey("am.webclient.alerttab.setasclear.text");
/* 5344 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 5345 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 5346 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 5347 */       return true;
/*      */     }
/* 5349 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 5350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5355 */     PageContext pageContext = _jspx_page_context;
/* 5356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5358 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5359 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 5360 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 5362 */     _jspx_th_c_005fout_005f18.setValue("${haid}");
/* 5363 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 5364 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 5365 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5366 */       return true;
/*      */     }
/* 5368 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 5369 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5374 */     PageContext pageContext = _jspx_page_context;
/* 5375 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5377 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5378 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 5379 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 5381 */     _jspx_th_c_005fout_005f19.setValue("${monitor}");
/* 5382 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 5383 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 5384 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5385 */       return true;
/*      */     }
/* 5387 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 5388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5393 */     PageContext pageContext = _jspx_page_context;
/* 5394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5396 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5397 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 5398 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 5400 */     _jspx_th_c_005fout_005f20.setValue("${param.entity}");
/* 5401 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 5402 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 5403 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5404 */       return true;
/*      */     }
/* 5406 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 5407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5412 */     PageContext pageContext = _jspx_page_context;
/* 5413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5415 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5416 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 5417 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 5419 */     _jspx_th_c_005fout_005f21.setValue("${source}");
/* 5420 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 5421 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 5422 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5423 */       return true;
/*      */     }
/* 5425 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 5426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5431 */     PageContext pageContext = _jspx_page_context;
/* 5432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5434 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5435 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 5436 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 5438 */     _jspx_th_c_005fout_005f22.setValue("${category}");
/* 5439 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 5440 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 5441 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5442 */       return true;
/*      */     }
/* 5444 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 5445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5450 */     PageContext pageContext = _jspx_page_context;
/* 5451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5453 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5454 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5455 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 5457 */     _jspx_th_c_005fout_005f23.setValue("${param.redirectto}");
/* 5458 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5459 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5460 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5461 */       return true;
/*      */     }
/* 5463 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5464 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5469 */     PageContext pageContext = _jspx_page_context;
/* 5470 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5472 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5473 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 5474 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 5476 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.webclient.alerttab.setasclear.text");
/* 5477 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 5478 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 5479 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 5480 */       return true;
/*      */     }
/* 5482 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 5483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5488 */     PageContext pageContext = _jspx_page_context;
/* 5489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5491 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5492 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5493 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/*      */     
/* 5495 */     _jspx_th_c_005fout_005f24.setValue("${alertProp.stringseverity}");
/* 5496 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5497 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5498 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5499 */       return true;
/*      */     }
/* 5501 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5502 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5507 */     PageContext pageContext = _jspx_page_context;
/* 5508 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5510 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5511 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5512 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/*      */     
/* 5514 */     _jspx_th_c_005fout_005f25.setValue("${alertProp.stringseverity}");
/* 5515 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5516 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5517 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5518 */       return true;
/*      */     }
/* 5520 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fotherwise_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5526 */     PageContext pageContext = _jspx_page_context;
/* 5527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5529 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5530 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5531 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fotherwise_005f7);
/*      */     
/* 5533 */     _jspx_th_c_005fout_005f26.setValue("${alertProp.stringseverity}");
/* 5534 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5535 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5536 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5537 */       return true;
/*      */     }
/* 5539 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5540 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5545 */     PageContext pageContext = _jspx_page_context;
/* 5546 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5548 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5549 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5550 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 5552 */     _jspx_th_c_005fout_005f27.setValue("${haid}");
/* 5553 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5554 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5555 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5556 */       return true;
/*      */     }
/* 5558 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5564 */     PageContext pageContext = _jspx_page_context;
/* 5565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5567 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5568 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5569 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 5571 */     _jspx_th_c_005fout_005f28.setValue("${monitor}");
/* 5572 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5573 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5574 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5575 */       return true;
/*      */     }
/* 5577 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5578 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5583 */     PageContext pageContext = _jspx_page_context;
/* 5584 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5586 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5587 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5588 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 5590 */     _jspx_th_c_005fout_005f29.setValue("${param.monitortype }");
/* 5591 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5592 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5593 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5594 */       return true;
/*      */     }
/* 5596 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5602 */     PageContext pageContext = _jspx_page_context;
/* 5603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5605 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5606 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 5607 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 5609 */     _jspx_th_c_005fout_005f30.setValue("${param.entity}");
/* 5610 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 5611 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 5612 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5613 */       return true;
/*      */     }
/* 5615 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5621 */     PageContext pageContext = _jspx_page_context;
/* 5622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5624 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5625 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 5626 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 5628 */     _jspx_th_c_005fout_005f31.setValue("${source}");
/* 5629 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 5630 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 5631 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5632 */       return true;
/*      */     }
/* 5634 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5640 */     PageContext pageContext = _jspx_page_context;
/* 5641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5643 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5644 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 5645 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 5647 */     _jspx_th_c_005fout_005f32.setValue("${category}");
/* 5648 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 5649 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 5650 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5651 */       return true;
/*      */     }
/* 5653 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5654 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5659 */     PageContext pageContext = _jspx_page_context;
/* 5660 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5662 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5663 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 5664 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 5666 */     _jspx_th_fmt_005fmessage_005f12.setKey("am.webclient.alerttab.setasclear.text");
/* 5667 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 5668 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 5669 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 5670 */       return true;
/*      */     }
/* 5672 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 5673 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5678 */     PageContext pageContext = _jspx_page_context;
/* 5679 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5681 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5682 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 5683 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 5685 */     _jspx_th_c_005fout_005f33.setValue("${haid}");
/* 5686 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 5687 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 5688 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5689 */       return true;
/*      */     }
/* 5691 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5692 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5697 */     PageContext pageContext = _jspx_page_context;
/* 5698 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5700 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5701 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 5702 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 5704 */     _jspx_th_c_005fout_005f34.setValue("${monitor}");
/* 5705 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 5706 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 5707 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5708 */       return true;
/*      */     }
/* 5710 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5711 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5716 */     PageContext pageContext = _jspx_page_context;
/* 5717 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5719 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5720 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 5721 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 5723 */     _jspx_th_c_005fout_005f35.setValue("${param.entity}");
/* 5724 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 5725 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 5726 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5727 */       return true;
/*      */     }
/* 5729 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5730 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5735 */     PageContext pageContext = _jspx_page_context;
/* 5736 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5738 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5739 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 5740 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 5742 */     _jspx_th_c_005fout_005f36.setValue("${source}");
/* 5743 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 5744 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 5745 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5746 */       return true;
/*      */     }
/* 5748 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5749 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5754 */     PageContext pageContext = _jspx_page_context;
/* 5755 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5757 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5758 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 5759 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 5761 */     _jspx_th_c_005fout_005f37.setValue("${category}");
/* 5762 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 5763 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 5764 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5765 */       return true;
/*      */     }
/* 5767 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5768 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5773 */     PageContext pageContext = _jspx_page_context;
/* 5774 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5776 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5777 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 5778 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 5780 */     _jspx_th_c_005fout_005f38.setValue("${param.redirectto}");
/* 5781 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 5782 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 5783 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 5784 */       return true;
/*      */     }
/* 5786 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 5787 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5792 */     PageContext pageContext = _jspx_page_context;
/* 5793 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5795 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5796 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 5797 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 5799 */     _jspx_th_fmt_005fmessage_005f13.setKey("am.webclient.alerttab.setasclear.text");
/* 5800 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 5801 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 5802 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 5803 */       return true;
/*      */     }
/* 5805 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 5806 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5811 */     PageContext pageContext = _jspx_page_context;
/* 5812 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5814 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5815 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 5816 */     _jspx_th_fmt_005fmessage_005f14.setParent(null);
/*      */     
/* 5818 */     _jspx_th_fmt_005fmessage_005f14.setKey("webclient.fault.details.properties.previousseverity");
/* 5819 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 5820 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 5821 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 5822 */       return true;
/*      */     }
/* 5824 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 5825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5830 */     PageContext pageContext = _jspx_page_context;
/* 5831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5833 */     ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5834 */     _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 5835 */     _jspx_th_c_005fchoose_005f8.setParent(null);
/* 5836 */     int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 5837 */     if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */       for (;;) {
/* 5839 */         out.write("\n\t\t\t");
/* 5840 */         if (_jspx_meth_c_005fwhen_005f11(_jspx_th_c_005fchoose_005f8, _jspx_page_context))
/* 5841 */           return true;
/* 5842 */         out.write(" \n\t\t\t");
/* 5843 */         if (_jspx_meth_c_005fotherwise_005f8(_jspx_th_c_005fchoose_005f8, _jspx_page_context))
/* 5844 */           return true;
/* 5845 */         out.write(" \n\t\t");
/* 5846 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 5847 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5851 */     if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 5852 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 5853 */       return true;
/*      */     }
/* 5855 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 5856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f11(JspTag _jspx_th_c_005fchoose_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5861 */     PageContext pageContext = _jspx_page_context;
/* 5862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5864 */     WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5865 */     _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 5866 */     _jspx_th_c_005fwhen_005f11.setParent((Tag)_jspx_th_c_005fchoose_005f8);
/*      */     
/* 5868 */     _jspx_th_c_005fwhen_005f11.setTest("${alertProp.stringpreviousseverity == '-1'}");
/* 5869 */     int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 5870 */     if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */       for (;;) {
/* 5872 */         out.write("\n\t\t\t\t");
/* 5873 */         if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fwhen_005f11, _jspx_page_context))
/* 5874 */           return true;
/* 5875 */         out.write("\n\t\t\t");
/* 5876 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 5877 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5881 */     if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 5882 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 5883 */       return true;
/*      */     }
/* 5885 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 5886 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fwhen_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5891 */     PageContext pageContext = _jspx_page_context;
/* 5892 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5894 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5895 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 5896 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fwhen_005f11);
/*      */     
/* 5898 */     _jspx_th_c_005fout_005f39.setValue("-");
/* 5899 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 5900 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 5901 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 5902 */       return true;
/*      */     }
/* 5904 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 5905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f8(JspTag _jspx_th_c_005fchoose_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5910 */     PageContext pageContext = _jspx_page_context;
/* 5911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5913 */     OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5914 */     _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 5915 */     _jspx_th_c_005fotherwise_005f8.setParent((Tag)_jspx_th_c_005fchoose_005f8);
/* 5916 */     int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 5917 */     if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */       for (;;) {
/* 5919 */         out.write(" \n\t\t\t\t");
/* 5920 */         if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fotherwise_005f8, _jspx_page_context))
/* 5921 */           return true;
/* 5922 */         out.write("\n\t\t\t");
/* 5923 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 5924 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5928 */     if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 5929 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 5930 */       return true;
/*      */     }
/* 5932 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 5933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fotherwise_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5938 */     PageContext pageContext = _jspx_page_context;
/* 5939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5941 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5942 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 5943 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fotherwise_005f8);
/*      */     
/* 5945 */     _jspx_th_c_005fout_005f40.setValue("${alertProp.stringpreviousseverity}");
/* 5946 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 5947 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 5948 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 5949 */       return true;
/*      */     }
/* 5951 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 5952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5957 */     PageContext pageContext = _jspx_page_context;
/* 5958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5960 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5961 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 5962 */     _jspx_th_fmt_005fmessage_005f15.setParent(null);
/*      */     
/* 5964 */     _jspx_th_fmt_005fmessage_005f15.setKey("am.webclient.rca.dependencyrule");
/* 5965 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 5966 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 5967 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 5968 */       return true;
/*      */     }
/* 5970 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 5971 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5976 */     PageContext pageContext = _jspx_page_context;
/* 5977 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5979 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5980 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 5981 */     _jspx_th_fmt_005fmessage_005f16.setParent(null);
/*      */     
/* 5983 */     _jspx_th_fmt_005fmessage_005f16.setKey("am.webclient.alarm.dependentDevices.text");
/* 5984 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 5985 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 5986 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 5987 */       return true;
/*      */     }
/* 5989 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 5990 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5995 */     PageContext pageContext = _jspx_page_context;
/* 5996 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5998 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5999 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 6000 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 6002 */     _jspx_th_fmt_005fmessage_005f17.setKey("Configure");
/* 6003 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 6004 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 6005 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 6006 */       return true;
/*      */     }
/* 6008 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 6009 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f23(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6014 */     PageContext pageContext = _jspx_page_context;
/* 6015 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6017 */     SetTag _jspx_th_c_005fset_005f23 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 6018 */     _jspx_th_c_005fset_005f23.setPageContext(_jspx_page_context);
/* 6019 */     _jspx_th_c_005fset_005f23.setParent(null);
/*      */     
/* 6021 */     _jspx_th_c_005fset_005f23.setTarget("${alertProp}");
/*      */     
/* 6023 */     _jspx_th_c_005fset_005f23.setProperty("currentstatusImg");
/*      */     
/* 6025 */     _jspx_th_c_005fset_005f23.setValue("${null}");
/* 6026 */     int _jspx_eval_c_005fset_005f23 = _jspx_th_c_005fset_005f23.doStartTag();
/* 6027 */     if (_jspx_th_c_005fset_005f23.doEndTag() == 5) {
/* 6028 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f23);
/* 6029 */       return true;
/*      */     }
/* 6031 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f23);
/* 6032 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6037 */     PageContext pageContext = _jspx_page_context;
/* 6038 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6040 */     MessageTag _jspx_th_fmt_005fmessage_005f18 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6041 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 6042 */     _jspx_th_fmt_005fmessage_005f18.setParent(null);
/*      */     
/* 6044 */     _jspx_th_fmt_005fmessage_005f18.setKey("webclient.fault.alarm.who");
/* 6045 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 6046 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 6047 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 6048 */       return true;
/*      */     }
/* 6050 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 6051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fotherwise_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6056 */     PageContext pageContext = _jspx_page_context;
/* 6057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6059 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6060 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 6061 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fotherwise_005f9);
/*      */     
/* 6063 */     _jspx_th_c_005fout_005f41.setValue("${alertProp.owner}");
/* 6064 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 6065 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 6066 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6067 */       return true;
/*      */     }
/* 6069 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 6070 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6075 */     PageContext pageContext = _jspx_page_context;
/* 6076 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6078 */     MessageTag _jspx_th_fmt_005fmessage_005f19 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6079 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 6080 */     _jspx_th_fmt_005fmessage_005f19.setParent(null);
/*      */     
/* 6082 */     _jspx_th_fmt_005fmessage_005f19.setKey("webclient.fault.details.properties.message");
/* 6083 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 6084 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 6085 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 6086 */       return true;
/*      */     }
/* 6088 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 6089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6094 */     PageContext pageContext = _jspx_page_context;
/* 6095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6097 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 6098 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 6099 */     _jspx_th_c_005fout_005f42.setParent(null);
/*      */     
/* 6101 */     _jspx_th_c_005fout_005f42.setValue("${alertProp.message}");
/*      */     
/* 6103 */     _jspx_th_c_005fout_005f42.setEscapeXml("false");
/* 6104 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 6105 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 6106 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6107 */       return true;
/*      */     }
/* 6109 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 6110 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f24(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6115 */     PageContext pageContext = _jspx_page_context;
/* 6116 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6118 */     SetTag _jspx_th_c_005fset_005f24 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 6119 */     _jspx_th_c_005fset_005f24.setPageContext(_jspx_page_context);
/* 6120 */     _jspx_th_c_005fset_005f24.setParent(null);
/*      */     
/* 6122 */     _jspx_th_c_005fset_005f24.setTarget("${alertProp}");
/*      */     
/* 6124 */     _jspx_th_c_005fset_005f24.setProperty("stringseverity");
/*      */     
/* 6126 */     _jspx_th_c_005fset_005f24.setValue("${null}");
/* 6127 */     int _jspx_eval_c_005fset_005f24 = _jspx_th_c_005fset_005f24.doStartTag();
/* 6128 */     if (_jspx_th_c_005fset_005f24.doEndTag() == 5) {
/* 6129 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f24);
/* 6130 */       return true;
/*      */     }
/* 6132 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f24);
/* 6133 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6138 */     PageContext pageContext = _jspx_page_context;
/* 6139 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6141 */     MessageTag _jspx_th_fmt_005fmessage_005f20 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6142 */     _jspx_th_fmt_005fmessage_005f20.setPageContext(_jspx_page_context);
/* 6143 */     _jspx_th_fmt_005fmessage_005f20.setParent(null);
/* 6144 */     int _jspx_eval_fmt_005fmessage_005f20 = _jspx_th_fmt_005fmessage_005f20.doStartTag();
/* 6145 */     if (_jspx_eval_fmt_005fmessage_005f20 != 0) {
/* 6146 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 6147 */         out = _jspx_page_context.pushBody();
/* 6148 */         _jspx_th_fmt_005fmessage_005f20.setBodyContent((BodyContent)out);
/* 6149 */         _jspx_th_fmt_005fmessage_005f20.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6152 */         out.write("am.myfield.customfield.text");
/* 6153 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f20.doAfterBody();
/* 6154 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6157 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 6158 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6161 */     if (_jspx_th_fmt_005fmessage_005f20.doEndTag() == 5) {
/* 6162 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 6163 */       return true;
/*      */     }
/* 6165 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 6166 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6171 */     PageContext pageContext = _jspx_page_context;
/* 6172 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6174 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6175 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 6176 */     _jspx_th_c_005fif_005f8.setParent(null);
/*      */     
/* 6178 */     _jspx_th_c_005fif_005f8.setTest("${alertProp.haslabel == \"true\"}");
/* 6179 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 6180 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 6182 */         out.write("\n      ");
/* 6183 */         if (_jspx_meth_fmt_005fmessage_005f21(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 6184 */           return true;
/* 6185 */         out.write(32);
/* 6186 */         out.write(58);
/* 6187 */         out.write(32);
/* 6188 */         if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fif_005f8, _jspx_page_context))
/* 6189 */           return true;
/* 6190 */         out.write(9);
/* 6191 */         out.write("\n     <br>\n     ");
/* 6192 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 6193 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6197 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 6198 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6199 */       return true;
/*      */     }
/* 6201 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f21(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6207 */     PageContext pageContext = _jspx_page_context;
/* 6208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6210 */     MessageTag _jspx_th_fmt_005fmessage_005f21 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6211 */     _jspx_th_fmt_005fmessage_005f21.setPageContext(_jspx_page_context);
/* 6212 */     _jspx_th_fmt_005fmessage_005f21.setParent((Tag)_jspx_th_c_005fif_005f8);
/* 6213 */     int _jspx_eval_fmt_005fmessage_005f21 = _jspx_th_fmt_005fmessage_005f21.doStartTag();
/* 6214 */     if (_jspx_eval_fmt_005fmessage_005f21 != 0) {
/* 6215 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 6216 */         out = _jspx_page_context.pushBody();
/* 6217 */         _jspx_th_fmt_005fmessage_005f21.setBodyContent((BodyContent)out);
/* 6218 */         _jspx_th_fmt_005fmessage_005f21.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6221 */         out.write("am.myfield.label");
/* 6222 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f21.doAfterBody();
/* 6223 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6226 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 6227 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6230 */     if (_jspx_th_fmt_005fmessage_005f21.doEndTag() == 5) {
/* 6231 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 6232 */       return true;
/*      */     }
/* 6234 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 6235 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6240 */     PageContext pageContext = _jspx_page_context;
/* 6241 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6243 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6244 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 6245 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6247 */     _jspx_th_c_005fout_005f43.setValue("${alertProp.label}");
/* 6248 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 6249 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 6250 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6251 */       return true;
/*      */     }
/* 6253 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 6254 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6259 */     PageContext pageContext = _jspx_page_context;
/* 6260 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6262 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6263 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 6264 */     _jspx_th_c_005fif_005f9.setParent(null);
/*      */     
/* 6266 */     _jspx_th_c_005fif_005f9.setTest("${alertProp.hasimpact == \"true\"}");
/* 6267 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 6268 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 6270 */         out.write("\n     ");
/* 6271 */         if (_jspx_meth_fmt_005fmessage_005f22(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 6272 */           return true;
/* 6273 */         out.write(32);
/* 6274 */         out.write(58);
/* 6275 */         out.write(32);
/* 6276 */         if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 6277 */           return true;
/* 6278 */         out.write(9);
/* 6279 */         out.write("\n     <br>\n     ");
/* 6280 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 6281 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6285 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 6286 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6287 */       return true;
/*      */     }
/* 6289 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6290 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f22(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6295 */     PageContext pageContext = _jspx_page_context;
/* 6296 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6298 */     MessageTag _jspx_th_fmt_005fmessage_005f22 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6299 */     _jspx_th_fmt_005fmessage_005f22.setPageContext(_jspx_page_context);
/* 6300 */     _jspx_th_fmt_005fmessage_005f22.setParent((Tag)_jspx_th_c_005fif_005f9);
/* 6301 */     int _jspx_eval_fmt_005fmessage_005f22 = _jspx_th_fmt_005fmessage_005f22.doStartTag();
/* 6302 */     if (_jspx_eval_fmt_005fmessage_005f22 != 0) {
/* 6303 */       if (_jspx_eval_fmt_005fmessage_005f22 != 1) {
/* 6304 */         out = _jspx_page_context.pushBody();
/* 6305 */         _jspx_th_fmt_005fmessage_005f22.setBodyContent((BodyContent)out);
/* 6306 */         _jspx_th_fmt_005fmessage_005f22.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6309 */         out.write("am.myfield.impact");
/* 6310 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f22.doAfterBody();
/* 6311 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6314 */       if (_jspx_eval_fmt_005fmessage_005f22 != 1) {
/* 6315 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6318 */     if (_jspx_th_fmt_005fmessage_005f22.doEndTag() == 5) {
/* 6319 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 6320 */       return true;
/*      */     }
/* 6322 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 6323 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6328 */     PageContext pageContext = _jspx_page_context;
/* 6329 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6331 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6332 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 6333 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6335 */     _jspx_th_c_005fout_005f44.setValue("${alertProp.impact}");
/* 6336 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 6337 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 6338 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 6339 */       return true;
/*      */     }
/* 6341 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 6342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6347 */     PageContext pageContext = _jspx_page_context;
/* 6348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6350 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6351 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 6352 */     _jspx_th_c_005fif_005f10.setParent(null);
/*      */     
/* 6354 */     _jspx_th_c_005fif_005f10.setTest("${alertProp.hasurgency == \"true\"}");
/* 6355 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 6356 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 6358 */         out.write("\n     ");
/* 6359 */         if (_jspx_meth_fmt_005fmessage_005f23(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 6360 */           return true;
/* 6361 */         out.write(32);
/* 6362 */         out.write(58);
/* 6363 */         out.write(32);
/* 6364 */         if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 6365 */           return true;
/* 6366 */         out.write(9);
/* 6367 */         out.write("\n     <br>\n     ");
/* 6368 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 6369 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6373 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 6374 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6375 */       return true;
/*      */     }
/* 6377 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f23(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6383 */     PageContext pageContext = _jspx_page_context;
/* 6384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6386 */     MessageTag _jspx_th_fmt_005fmessage_005f23 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6387 */     _jspx_th_fmt_005fmessage_005f23.setPageContext(_jspx_page_context);
/* 6388 */     _jspx_th_fmt_005fmessage_005f23.setParent((Tag)_jspx_th_c_005fif_005f10);
/* 6389 */     int _jspx_eval_fmt_005fmessage_005f23 = _jspx_th_fmt_005fmessage_005f23.doStartTag();
/* 6390 */     if (_jspx_eval_fmt_005fmessage_005f23 != 0) {
/* 6391 */       if (_jspx_eval_fmt_005fmessage_005f23 != 1) {
/* 6392 */         out = _jspx_page_context.pushBody();
/* 6393 */         _jspx_th_fmt_005fmessage_005f23.setBodyContent((BodyContent)out);
/* 6394 */         _jspx_th_fmt_005fmessage_005f23.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6397 */         out.write("am.myfield.urgency");
/* 6398 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f23.doAfterBody();
/* 6399 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6402 */       if (_jspx_eval_fmt_005fmessage_005f23 != 1) {
/* 6403 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6406 */     if (_jspx_th_fmt_005fmessage_005f23.doEndTag() == 5) {
/* 6407 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 6408 */       return true;
/*      */     }
/* 6410 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 6411 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6416 */     PageContext pageContext = _jspx_page_context;
/* 6417 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6419 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6420 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 6421 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 6423 */     _jspx_th_c_005fout_005f45.setValue("${alertProp.urgency}");
/* 6424 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 6425 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 6426 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 6427 */       return true;
/*      */     }
/* 6429 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 6430 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f24(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6435 */     PageContext pageContext = _jspx_page_context;
/* 6436 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6438 */     MessageTag _jspx_th_fmt_005fmessage_005f24 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6439 */     _jspx_th_fmt_005fmessage_005f24.setPageContext(_jspx_page_context);
/* 6440 */     _jspx_th_fmt_005fmessage_005f24.setParent(null);
/* 6441 */     int _jspx_eval_fmt_005fmessage_005f24 = _jspx_th_fmt_005fmessage_005f24.doStartTag();
/* 6442 */     if (_jspx_eval_fmt_005fmessage_005f24 != 0) {
/* 6443 */       if (_jspx_eval_fmt_005fmessage_005f24 != 1) {
/* 6444 */         out = _jspx_page_context.pushBody();
/* 6445 */         _jspx_th_fmt_005fmessage_005f24.setBodyContent((BodyContent)out);
/* 6446 */         _jspx_th_fmt_005fmessage_005f24.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6449 */         out.write("am.myfield.alarmspage.allvalues.text");
/* 6450 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f24.doAfterBody();
/* 6451 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6454 */       if (_jspx_eval_fmt_005fmessage_005f24 != 1) {
/* 6455 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6458 */     if (_jspx_th_fmt_005fmessage_005f24.doEndTag() == 5) {
/* 6459 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 6460 */       return true;
/*      */     }
/* 6462 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 6463 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f25(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6468 */     PageContext pageContext = _jspx_page_context;
/* 6469 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6471 */     SetTag _jspx_th_c_005fset_005f25 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 6472 */     _jspx_th_c_005fset_005f25.setPageContext(_jspx_page_context);
/* 6473 */     _jspx_th_c_005fset_005f25.setParent(null);
/*      */     
/* 6475 */     _jspx_th_c_005fset_005f25.setTarget("${alertProp}");
/*      */     
/* 6477 */     _jspx_th_c_005fset_005f25.setProperty("stringpreviousseverity");
/*      */     
/* 6479 */     _jspx_th_c_005fset_005f25.setValue("${null}");
/* 6480 */     int _jspx_eval_c_005fset_005f25 = _jspx_th_c_005fset_005f25.doStartTag();
/* 6481 */     if (_jspx_th_c_005fset_005f25.doEndTag() == 5) {
/* 6482 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f25);
/* 6483 */       return true;
/*      */     }
/* 6485 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f25);
/* 6486 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f26(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6491 */     PageContext pageContext = _jspx_page_context;
/* 6492 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6494 */     SetTag _jspx_th_c_005fset_005f26 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 6495 */     _jspx_th_c_005fset_005f26.setPageContext(_jspx_page_context);
/* 6496 */     _jspx_th_c_005fset_005f26.setParent(null);
/*      */     
/* 6498 */     _jspx_th_c_005fset_005f26.setTarget("${alertProp}");
/*      */     
/* 6500 */     _jspx_th_c_005fset_005f26.setProperty("prestatusImg");
/*      */     
/* 6502 */     _jspx_th_c_005fset_005f26.setValue("${null}");
/* 6503 */     int _jspx_eval_c_005fset_005f26 = _jspx_th_c_005fset_005f26.doStartTag();
/* 6504 */     if (_jspx_th_c_005fset_005f26.doEndTag() == 5) {
/* 6505 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f26);
/* 6506 */       return true;
/*      */     }
/* 6508 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f26);
/* 6509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f27(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6514 */     PageContext pageContext = _jspx_page_context;
/* 6515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6517 */     SetTag _jspx_th_c_005fset_005f27 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 6518 */     _jspx_th_c_005fset_005f27.setPageContext(_jspx_page_context);
/* 6519 */     _jspx_th_c_005fset_005f27.setParent(null);
/*      */     
/* 6521 */     _jspx_th_c_005fset_005f27.setTarget("${alertProp}");
/*      */     
/* 6523 */     _jspx_th_c_005fset_005f27.setProperty("message");
/*      */     
/* 6525 */     _jspx_th_c_005fset_005f27.setValue("${null}");
/* 6526 */     int _jspx_eval_c_005fset_005f27 = _jspx_th_c_005fset_005f27.doStartTag();
/* 6527 */     if (_jspx_th_c_005fset_005f27.doEndTag() == 5) {
/* 6528 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f27);
/* 6529 */       return true;
/*      */     }
/* 6531 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f27);
/* 6532 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6537 */     PageContext pageContext = _jspx_page_context;
/* 6538 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6540 */     ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 6541 */     _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 6542 */     _jspx_th_c_005fchoose_005f10.setParent(null);
/* 6543 */     int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 6544 */     if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */       for (;;) {
/* 6546 */         out.write(" \n  ");
/* 6547 */         if (_jspx_meth_c_005fwhen_005f13(_jspx_th_c_005fchoose_005f10, _jspx_page_context))
/* 6548 */           return true;
/* 6549 */         out.write(32);
/* 6550 */         if (_jspx_meth_c_005fwhen_005f14(_jspx_th_c_005fchoose_005f10, _jspx_page_context))
/* 6551 */           return true;
/* 6552 */         out.write(32);
/* 6553 */         if (_jspx_meth_c_005fotherwise_005f10(_jspx_th_c_005fchoose_005f10, _jspx_page_context))
/* 6554 */           return true;
/* 6555 */         out.write(32);
/* 6556 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 6557 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6561 */     if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 6562 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 6563 */       return true;
/*      */     }
/* 6565 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 6566 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f13(JspTag _jspx_th_c_005fchoose_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6571 */     PageContext pageContext = _jspx_page_context;
/* 6572 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6574 */     WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6575 */     _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 6576 */     _jspx_th_c_005fwhen_005f13.setParent((Tag)_jspx_th_c_005fchoose_005f10);
/*      */     
/* 6578 */     _jspx_th_c_005fwhen_005f13.setTest("${pickUpStatus == true || param.pickUpStatus == true}");
/* 6579 */     int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 6580 */     if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */       for (;;) {
/* 6582 */         out.write(" \n  <!--tr> \n    <td align=\"left\"  class=\"bodytext\"><span class=\"highlite\">");
/* 6583 */         if (_jspx_meth_fmt_005fmessage_005f25(_jspx_th_c_005fwhen_005f13, _jspx_page_context))
/* 6584 */           return true;
/* 6585 */         out.write("</span></td>\n    <td  class=\"highlite\">");
/* 6586 */         if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fwhen_005f13, _jspx_page_context))
/* 6587 */           return true;
/* 6588 */         out.write("</td>\n  </tr-->\n  ");
/* 6589 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 6590 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6594 */     if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 6595 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 6596 */       return true;
/*      */     }
/* 6598 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 6599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f25(JspTag _jspx_th_c_005fwhen_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6604 */     PageContext pageContext = _jspx_page_context;
/* 6605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6607 */     MessageTag _jspx_th_fmt_005fmessage_005f25 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6608 */     _jspx_th_fmt_005fmessage_005f25.setPageContext(_jspx_page_context);
/* 6609 */     _jspx_th_fmt_005fmessage_005f25.setParent((Tag)_jspx_th_c_005fwhen_005f13);
/*      */     
/* 6611 */     _jspx_th_fmt_005fmessage_005f25.setKey("webclient.fault.details.properties.owner");
/* 6612 */     int _jspx_eval_fmt_005fmessage_005f25 = _jspx_th_fmt_005fmessage_005f25.doStartTag();
/* 6613 */     if (_jspx_th_fmt_005fmessage_005f25.doEndTag() == 5) {
/* 6614 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 6615 */       return true;
/*      */     }
/* 6617 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 6618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fwhen_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6623 */     PageContext pageContext = _jspx_page_context;
/* 6624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6626 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6627 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 6628 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fwhen_005f13);
/*      */     
/* 6630 */     _jspx_th_c_005fout_005f46.setValue("${alertProp.owner}");
/* 6631 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 6632 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 6633 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 6634 */       return true;
/*      */     }
/* 6636 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 6637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f14(JspTag _jspx_th_c_005fchoose_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6642 */     PageContext pageContext = _jspx_page_context;
/* 6643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6645 */     WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6646 */     _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 6647 */     _jspx_th_c_005fwhen_005f14.setParent((Tag)_jspx_th_c_005fchoose_005f10);
/*      */     
/* 6649 */     _jspx_th_c_005fwhen_005f14.setTest("${UnPickStatus == true}");
/* 6650 */     int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 6651 */     if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */       for (;;) {
/* 6653 */         out.write(" \n  <!--str> \n    <td align=\"left\" class=\"bodytext\"><span class=\"highlite\">");
/* 6654 */         if (_jspx_meth_fmt_005fmessage_005f26(_jspx_th_c_005fwhen_005f14, _jspx_page_context))
/* 6655 */           return true;
/* 6656 */         out.write("</span></td>\n    <td  class=\"highlite\">");
/* 6657 */         if (_jspx_meth_c_005fout_005f47(_jspx_th_c_005fwhen_005f14, _jspx_page_context))
/* 6658 */           return true;
/* 6659 */         out.write("</td>\n  </tr-->\n  ");
/* 6660 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 6661 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6665 */     if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 6666 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 6667 */       return true;
/*      */     }
/* 6669 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 6670 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f26(JspTag _jspx_th_c_005fwhen_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6675 */     PageContext pageContext = _jspx_page_context;
/* 6676 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6678 */     MessageTag _jspx_th_fmt_005fmessage_005f26 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6679 */     _jspx_th_fmt_005fmessage_005f26.setPageContext(_jspx_page_context);
/* 6680 */     _jspx_th_fmt_005fmessage_005f26.setParent((Tag)_jspx_th_c_005fwhen_005f14);
/*      */     
/* 6682 */     _jspx_th_fmt_005fmessage_005f26.setKey("webclient.fault.details.properties.owner");
/* 6683 */     int _jspx_eval_fmt_005fmessage_005f26 = _jspx_th_fmt_005fmessage_005f26.doStartTag();
/* 6684 */     if (_jspx_th_fmt_005fmessage_005f26.doEndTag() == 5) {
/* 6685 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 6686 */       return true;
/*      */     }
/* 6688 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 6689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_c_005fwhen_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6694 */     PageContext pageContext = _jspx_page_context;
/* 6695 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6697 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6698 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 6699 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_c_005fwhen_005f14);
/*      */     
/* 6701 */     _jspx_th_c_005fout_005f47.setValue("${alertProp.owner}");
/* 6702 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 6703 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 6704 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 6705 */       return true;
/*      */     }
/* 6707 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 6708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f10(JspTag _jspx_th_c_005fchoose_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6713 */     PageContext pageContext = _jspx_page_context;
/* 6714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6716 */     OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6717 */     _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 6718 */     _jspx_th_c_005fotherwise_005f10.setParent((Tag)_jspx_th_c_005fchoose_005f10);
/* 6719 */     int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 6720 */     if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */       for (;;) {
/* 6722 */         out.write(" \n  <!--tr> \n    <td align=\"left\"  class=\"bodytext\"><span class=\"bodytext\">");
/* 6723 */         if (_jspx_meth_fmt_005fmessage_005f27(_jspx_th_c_005fotherwise_005f10, _jspx_page_context))
/* 6724 */           return true;
/* 6725 */         out.write("</span></td>\n    <td  class=\"bodytext\">");
/* 6726 */         if (_jspx_meth_c_005fout_005f48(_jspx_th_c_005fotherwise_005f10, _jspx_page_context))
/* 6727 */           return true;
/* 6728 */         out.write("</td>\n  </tr-->\n  ");
/* 6729 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 6730 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6734 */     if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 6735 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 6736 */       return true;
/*      */     }
/* 6738 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 6739 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f27(JspTag _jspx_th_c_005fotherwise_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6744 */     PageContext pageContext = _jspx_page_context;
/* 6745 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6747 */     MessageTag _jspx_th_fmt_005fmessage_005f27 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6748 */     _jspx_th_fmt_005fmessage_005f27.setPageContext(_jspx_page_context);
/* 6749 */     _jspx_th_fmt_005fmessage_005f27.setParent((Tag)_jspx_th_c_005fotherwise_005f10);
/*      */     
/* 6751 */     _jspx_th_fmt_005fmessage_005f27.setKey("webclient.fault.details.properties.owner");
/* 6752 */     int _jspx_eval_fmt_005fmessage_005f27 = _jspx_th_fmt_005fmessage_005f27.doStartTag();
/* 6753 */     if (_jspx_th_fmt_005fmessage_005f27.doEndTag() == 5) {
/* 6754 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 6755 */       return true;
/*      */     }
/* 6757 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 6758 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_c_005fotherwise_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6763 */     PageContext pageContext = _jspx_page_context;
/* 6764 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6766 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6767 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 6768 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_c_005fotherwise_005f10);
/*      */     
/* 6770 */     _jspx_th_c_005fout_005f48.setValue("${alertProp.owner}");
/* 6771 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 6772 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 6773 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 6774 */       return true;
/*      */     }
/* 6776 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 6777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f28(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6782 */     PageContext pageContext = _jspx_page_context;
/* 6783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6785 */     SetTag _jspx_th_c_005fset_005f28 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 6786 */     _jspx_th_c_005fset_005f28.setPageContext(_jspx_page_context);
/* 6787 */     _jspx_th_c_005fset_005f28.setParent(null);
/*      */     
/* 6789 */     _jspx_th_c_005fset_005f28.setTarget("${alertProp}");
/*      */     
/* 6791 */     _jspx_th_c_005fset_005f28.setProperty("owner");
/*      */     
/* 6793 */     _jspx_th_c_005fset_005f28.setValue("${null}");
/* 6794 */     int _jspx_eval_c_005fset_005f28 = _jspx_th_c_005fset_005f28.doStartTag();
/* 6795 */     if (_jspx_th_c_005fset_005f28.doEndTag() == 5) {
/* 6796 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f28);
/* 6797 */       return true;
/*      */     }
/* 6799 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f28);
/* 6800 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f28(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6805 */     PageContext pageContext = _jspx_page_context;
/* 6806 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6808 */     MessageTag _jspx_th_fmt_005fmessage_005f28 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6809 */     _jspx_th_fmt_005fmessage_005f28.setPageContext(_jspx_page_context);
/* 6810 */     _jspx_th_fmt_005fmessage_005f28.setParent(null);
/*      */     
/* 6812 */     _jspx_th_fmt_005fmessage_005f28.setKey("webclient.fault.details.properties.groupname");
/* 6813 */     int _jspx_eval_fmt_005fmessage_005f28 = _jspx_th_fmt_005fmessage_005f28.doStartTag();
/* 6814 */     if (_jspx_th_fmt_005fmessage_005f28.doEndTag() == 5) {
/* 6815 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 6816 */       return true;
/*      */     }
/* 6818 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 6819 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6824 */     PageContext pageContext = _jspx_page_context;
/* 6825 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6827 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6828 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 6829 */     _jspx_th_c_005fout_005f49.setParent(null);
/*      */     
/* 6831 */     _jspx_th_c_005fout_005f49.setValue("${alertProp.groupName}");
/* 6832 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 6833 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 6834 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 6835 */       return true;
/*      */     }
/* 6837 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 6838 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f29(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6843 */     PageContext pageContext = _jspx_page_context;
/* 6844 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6846 */     SetTag _jspx_th_c_005fset_005f29 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 6847 */     _jspx_th_c_005fset_005f29.setPageContext(_jspx_page_context);
/* 6848 */     _jspx_th_c_005fset_005f29.setParent(null);
/*      */     
/* 6850 */     _jspx_th_c_005fset_005f29.setTarget("${alertProp}");
/*      */     
/* 6852 */     _jspx_th_c_005fset_005f29.setProperty("groupName");
/*      */     
/* 6854 */     _jspx_th_c_005fset_005f29.setValue("${null}");
/* 6855 */     int _jspx_eval_c_005fset_005f29 = _jspx_th_c_005fset_005f29.doStartTag();
/* 6856 */     if (_jspx_th_c_005fset_005f29.doEndTag() == 5) {
/* 6857 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f29);
/* 6858 */       return true;
/*      */     }
/* 6860 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f29);
/* 6861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f29(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6866 */     PageContext pageContext = _jspx_page_context;
/* 6867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6869 */     MessageTag _jspx_th_fmt_005fmessage_005f29 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6870 */     _jspx_th_fmt_005fmessage_005f29.setPageContext(_jspx_page_context);
/* 6871 */     _jspx_th_fmt_005fmessage_005f29.setParent(null);
/*      */     
/* 6873 */     _jspx_th_fmt_005fmessage_005f29.setKey("webclient.fault.details.properties.priority");
/* 6874 */     int _jspx_eval_fmt_005fmessage_005f29 = _jspx_th_fmt_005fmessage_005f29.doStartTag();
/* 6875 */     if (_jspx_th_fmt_005fmessage_005f29.doEndTag() == 5) {
/* 6876 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 6877 */       return true;
/*      */     }
/* 6879 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 6880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6885 */     PageContext pageContext = _jspx_page_context;
/* 6886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6888 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6889 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 6890 */     _jspx_th_c_005fout_005f50.setParent(null);
/*      */     
/* 6892 */     _jspx_th_c_005fout_005f50.setValue("${alertProp.priority}");
/* 6893 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 6894 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 6895 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 6896 */       return true;
/*      */     }
/* 6898 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 6899 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f30(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6904 */     PageContext pageContext = _jspx_page_context;
/* 6905 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6907 */     SetTag _jspx_th_c_005fset_005f30 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 6908 */     _jspx_th_c_005fset_005f30.setPageContext(_jspx_page_context);
/* 6909 */     _jspx_th_c_005fset_005f30.setParent(null);
/*      */     
/* 6911 */     _jspx_th_c_005fset_005f30.setTarget("${alertProp}");
/*      */     
/* 6913 */     _jspx_th_c_005fset_005f30.setProperty("priority");
/*      */     
/* 6915 */     _jspx_th_c_005fset_005f30.setValue("${null}");
/* 6916 */     int _jspx_eval_c_005fset_005f30 = _jspx_th_c_005fset_005f30.doStartTag();
/* 6917 */     if (_jspx_th_c_005fset_005f30.doEndTag() == 5) {
/* 6918 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f30);
/* 6919 */       return true;
/*      */     }
/* 6921 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f30);
/* 6922 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6927 */     PageContext pageContext = _jspx_page_context;
/* 6928 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6930 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 6931 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 6932 */     _jspx_th_c_005fforEach_005f2.setParent(null);
/*      */     
/* 6934 */     _jspx_th_c_005fforEach_005f2.setVar("prop");
/*      */     
/* 6936 */     _jspx_th_c_005fforEach_005f2.setItems("${alertProp}");
/* 6937 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 6939 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 6940 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 6942 */           out.write(" \n  <!--tr> \n    <td align=\"left\" class=\"bodytext\"><span class=\"bodytext\">");
/* 6943 */           boolean bool; if (_jspx_meth_c_005fout_005f51(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 6944 */             return true;
/* 6945 */           out.write("</span></td>\n    <td ><span class=\"bodytext\">");
/* 6946 */           if (_jspx_meth_c_005fout_005f52(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 6947 */             return true;
/* 6948 */           out.write("</span></td>\n  </tr-->\n  ");
/* 6949 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 6950 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6954 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 6955 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6958 */         int tmp226_225 = 0; int[] tmp226_223 = _jspx_push_body_count_c_005fforEach_005f2; int tmp228_227 = tmp226_223[tmp226_225];tmp226_223[tmp226_225] = (tmp228_227 - 1); if (tmp228_227 <= 0) break;
/* 6959 */         out = _jspx_page_context.popBody(); }
/* 6960 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 6962 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 6963 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 6965 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6970 */     PageContext pageContext = _jspx_page_context;
/* 6971 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6973 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6974 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 6975 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 6977 */     _jspx_th_c_005fout_005f51.setValue("${prop.key}");
/* 6978 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 6979 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 6980 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 6981 */       return true;
/*      */     }
/* 6983 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 6984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 6989 */     PageContext pageContext = _jspx_page_context;
/* 6990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6992 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6993 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 6994 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 6996 */     _jspx_th_c_005fout_005f52.setValue("${prop.value}");
/* 6997 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 6998 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 6999 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 7000 */       return true;
/*      */     }
/* 7002 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 7003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f30(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7008 */     PageContext pageContext = _jspx_page_context;
/* 7009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7011 */     MessageTag _jspx_th_fmt_005fmessage_005f30 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7012 */     _jspx_th_fmt_005fmessage_005f30.setPageContext(_jspx_page_context);
/* 7013 */     _jspx_th_fmt_005fmessage_005f30.setParent(null);
/*      */     
/* 7015 */     _jspx_th_fmt_005fmessage_005f30.setKey("webclient.fault.alarmdetails.viewannotation.header");
/* 7016 */     int _jspx_eval_fmt_005fmessage_005f30 = _jspx_th_fmt_005fmessage_005f30.doStartTag();
/* 7017 */     if (_jspx_th_fmt_005fmessage_005f30.doEndTag() == 5) {
/* 7018 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 7019 */       return true;
/*      */     }
/* 7021 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 7022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f31(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7027 */     PageContext pageContext = _jspx_page_context;
/* 7028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7030 */     MessageTag _jspx_th_fmt_005fmessage_005f31 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7031 */     _jspx_th_fmt_005fmessage_005f31.setPageContext(_jspx_page_context);
/* 7032 */     _jspx_th_fmt_005fmessage_005f31.setParent(null);
/*      */     
/* 7034 */     _jspx_th_fmt_005fmessage_005f31.setKey("webclient.fault.alarmlist.nodata");
/* 7035 */     int _jspx_eval_fmt_005fmessage_005f31 = _jspx_th_fmt_005fmessage_005f31.doStartTag();
/* 7036 */     if (_jspx_th_fmt_005fmessage_005f31.doEndTag() == 5) {
/* 7037 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 7038 */       return true;
/*      */     }
/* 7040 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 7041 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f32(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7046 */     PageContext pageContext = _jspx_page_context;
/* 7047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7049 */     MessageTag _jspx_th_fmt_005fmessage_005f32 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7050 */     _jspx_th_fmt_005fmessage_005f32.setPageContext(_jspx_page_context);
/* 7051 */     _jspx_th_fmt_005fmessage_005f32.setParent(null);
/*      */     
/* 7053 */     _jspx_th_fmt_005fmessage_005f32.setKey("webclient.fault.alarmlist.nodata");
/* 7054 */     int _jspx_eval_fmt_005fmessage_005f32 = _jspx_th_fmt_005fmessage_005f32.doStartTag();
/* 7055 */     if (_jspx_th_fmt_005fmessage_005f32.doEndTag() == 5) {
/* 7056 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 7057 */       return true;
/*      */     }
/* 7059 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 7060 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7065 */     PageContext pageContext = _jspx_page_context;
/* 7066 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7068 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7069 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 7070 */     _jspx_th_c_005fout_005f53.setParent(null);
/*      */     
/* 7072 */     _jspx_th_c_005fout_005f53.setValue("${param.entity}");
/* 7073 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 7074 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 7075 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 7076 */       return true;
/*      */     }
/* 7078 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 7079 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f33(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7084 */     PageContext pageContext = _jspx_page_context;
/* 7085 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7087 */     MessageTag _jspx_th_fmt_005fmessage_005f33 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7088 */     _jspx_th_fmt_005fmessage_005f33.setPageContext(_jspx_page_context);
/* 7089 */     _jspx_th_fmt_005fmessage_005f33.setParent(null);
/*      */     
/* 7091 */     _jspx_th_fmt_005fmessage_005f33.setKey("webclient.fault.alarmlist.nodata");
/* 7092 */     int _jspx_eval_fmt_005fmessage_005f33 = _jspx_th_fmt_005fmessage_005f33.doStartTag();
/* 7093 */     if (_jspx_th_fmt_005fmessage_005f33.doEndTag() == 5) {
/* 7094 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f33);
/* 7095 */       return true;
/*      */     }
/* 7097 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f33);
/* 7098 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\fault\jsp\alarmProperties_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */