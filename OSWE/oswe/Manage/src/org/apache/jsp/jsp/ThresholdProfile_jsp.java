/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.AMRegexUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ThresholdProfile_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  818 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  819 */     getRCATrimmedText(div1, rca);
/*  820 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  823 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  824 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  977 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 2046 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
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
/* 2100 */     List<String> criticalThresholdValues = AMRegexUtil.getThresholdGroups(criticalcondition, true);
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
/*      */ 
/*      */   private String getCondition(String str)
/*      */   {
/* 2185 */     return " CASE " + str + " when 'LT' then '<' WHEN 'GT' THEN '>' when 'EQ' then '=' WHEN 'NE' THEN '!=' when 'LE' then '<=' WHEN 'GE' THEN '>=' WHEN 'CT' THEN 'contains' WHEN 'DC' THEN 'does not contain' WHEN 'QL' THEN 'equals' WHEN 'NQ' THEN 'not equal to' WHEN 'DC' THEN 'does not contain' WHEN 'SW' THEN 'starts with' WHEN 'EW' THEN 'ends with' ELSE " + str + " END  ";
/*      */   }
/*      */   
/* 2188 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2194 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2195 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2209 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2213 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2220 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2224 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2236 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2239 */     JspWriter out = null;
/* 2240 */     Object page = this;
/* 2241 */     JspWriter _jspx_out = null;
/* 2242 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2246 */       response.setContentType("text/html;charset=UTF-8");
/* 2247 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2249 */       _jspx_page_context = pageContext;
/* 2250 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2251 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2252 */       session = pageContext.getSession();
/* 2253 */       out = pageContext.getOut();
/* 2254 */       _jspx_out = out;
/*      */       
/* 2256 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n<style>\n.indenttest\n{\npadding-left: 5px;\n}\n</style>\n");
/* 2257 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2259 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2260 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2261 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2263 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2265 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2267 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2269 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2270 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2271 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2272 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2275 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2276 */         String available = null;
/* 2277 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2278 */         out.write(10);
/*      */         
/* 2280 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2281 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2282 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2284 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2286 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2288 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2290 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2291 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2292 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2293 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2296 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2297 */           String unavailable = null;
/* 2298 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2299 */           out.write(10);
/*      */           
/* 2301 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2302 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2303 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2305 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2307 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2309 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2311 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2312 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2313 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2314 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2317 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2318 */             String unmanaged = null;
/* 2319 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2320 */             out.write(10);
/*      */             
/* 2322 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2323 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2324 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2326 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2328 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2330 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2332 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2333 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2334 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2335 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2338 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2339 */               String scheduled = null;
/* 2340 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2341 */               out.write(10);
/*      */               
/* 2343 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2344 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2345 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2347 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2349 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2351 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2353 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2354 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2355 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2356 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2359 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2360 */                 String critical = null;
/* 2361 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2362 */                 out.write(10);
/*      */                 
/* 2364 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2365 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2366 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2368 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2370 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2372 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2374 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2375 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2376 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2377 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2380 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2381 */                   String clear = null;
/* 2382 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2383 */                   out.write(10);
/*      */                   
/* 2385 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2386 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2387 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2389 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2391 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2393 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2395 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2396 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2397 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2398 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2401 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2402 */                     String warning = null;
/* 2403 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2404 */                     out.write(10);
/* 2405 */                     out.write(10);
/*      */                     
/* 2407 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2408 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2410 */                     out.write(10);
/* 2411 */                     out.write(10);
/* 2412 */                     out.write(10);
/* 2413 */                     out.write("\n\n<input type=\"hidden\" id=\"secondarycriticalexist\" name=\"secondarycriticalexist\" value=\"false\">\n<input type=\"hidden\" id=\"secondarywarningexist\" name=\"secondarywarningexist\" value=\"false\">\n<input type=\"hidden\" id=\"secondaryinfoexist\" name=\"secondaryinfoexist\" value=\"false\">\n\n");
/*      */                     
/* 2415 */                     request.setAttribute("HelpKey", "New Threshold Profile");
/*      */                     
/* 2417 */                     out.write(10);
/* 2418 */                     out.write(10);
/*      */                     
/* 2420 */                     ManagedApplication mo = new ManagedApplication();
/*      */                     
/* 2422 */                     if (request.getParameter("thresholdid") != null) {
/* 2423 */                       String thresholdValue = null;
/*      */                       try
/*      */                       {
/* 2426 */                         thresholdValue = request.getParameter("thresholdid");
/*      */                       } catch (Exception ex) {
/* 2428 */                         ex.printStackTrace();
/*      */                       }
/*      */                       try {
/* 2431 */                         if ((EnterpriseUtil.isManagedServer()) && (Integer.parseInt(thresholdValue) >= 10000) && (Integer.parseInt(thresholdValue) < 10000000)) {
/* 2432 */                           request.setAttribute("adminThresholdConfig", Boolean.valueOf(true));
/*      */                         }
/*      */                       } catch (Exception ex) {
/* 2435 */                         ex.printStackTrace();
/*      */                       }
/*      */                     }
/* 2438 */                     if (request.getParameter("criticalCondition") != null)
/*      */                     {
/* 2440 */                       String thresName = request.getParameter("thresName");
/* 2441 */                       String criticalCon = request.getParameter("criticalCondition");
/* 2442 */                       String warningCon = request.getParameter("warningCondition");
/* 2443 */                       String clearCon = request.getParameter("clearCondition");
/* 2444 */                       String criticalValue = request.getParameter("criticalValue");
/* 2445 */                       String warningValue = request.getParameter("warningValue");
/* 2446 */                       String clearValue = request.getParameter("clearValue");
/* 2447 */                       String thresholdId = request.getParameter("thresholdid");
/* 2448 */                       String updateactionquery = null;
/* 2449 */                       int id = 0;
/*      */                       try
/*      */                       {
/* 2452 */                         id = Integer.parseInt(thresholdId);
/*      */                       }
/*      */                       catch (Exception e) {}
/*      */                       
/*      */ 
/*      */ 
/* 2458 */                       updateactionquery = "update AM_THRESHOLDCONFIG set Name='" + thresName + "',CRITICALTHRESHOLDCONDITION='" + request.getParameter("criticalCondition") + "',WARNINGTHRESHOLDCONDITION='" + request.getParameter("warningCondition") + "',INFOTHRESHOLDCONDITION='" + request.getParameter("clearCondition") + "',CRITICALTHRESHOLDVALUE='" + criticalValue + "', WARNINGTHRESHOLDVALUE='" + warningValue + "', INFOTHRESHOLDVALUE='" + clearValue + "' where ID = " + id;
/*      */                       try
/*      */                       {
/* 2461 */                         String typequery = "select  TYPE from AM_THRESHOLDCONFIG where AM_THRESHOLDCONFIG.ID =" + id;
/* 2462 */                         ArrayList typerows = mo.getRows(typequery);
/* 2463 */                         if ((typerows != null) && (typerows.size() > 0))
/*      */                         {
/* 2465 */                           ArrayList temprow = (ArrayList)typerows.get(0);
/* 2466 */                           String thresholdtype = (String)temprow.get(0);
/* 2467 */                           if (thresholdtype.equals("3"))
/*      */                           {
/* 2469 */                             updateactionquery = "update AM_THRESHOLDCONFIG set Name='" + thresName + "',CRITICALTHRESHOLDCONDITION='" + request.getParameter("criticalCondition") + "',WARNINGTHRESHOLDCONDITION='" + request.getParameter("warningCondition") + "',INFOTHRESHOLDCONDITION='" + request.getParameter("clearCondition") + "',CRITICALTHRESHOLDVALUE='-1', WARNINGTHRESHOLDVALUE='-1', INFOTHRESHOLDVALUE='-1' where ID = " + id;
/* 2470 */                             mo.executeUpdateStmt(updateactionquery);
/*      */                             
/*      */ 
/* 2473 */                             PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("update AM_PATTERNMATCHERCONFIG set CRITICALTHRESHOLDVALUE=?, WARNINGTHRESHOLDVALUE=?,INFOTHRESHOLDVALUE=? where ID=?");
/* 2474 */                             ps.setString(1, criticalValue);
/* 2475 */                             ps.setString(2, warningValue);
/* 2476 */                             ps.setString(3, clearValue);
/* 2477 */                             ps.setInt(4, id);
/* 2478 */                             ps.executeUpdate();
/*      */ 
/*      */ 
/*      */                           }
/* 2482 */                           else if ("4".equals(thresholdtype)) {
/* 2483 */                             updateactionquery = "update AM_THRESHOLDCONFIG set Name='" + request.getParameter("thresName") + "',CRITICALTHRESHOLDCONDITION='" + request.getParameter("criticalCondition") + "',WARNINGTHRESHOLDCONDITION='" + request.getParameter("warningCondition") + "',INFOTHRESHOLDCONDITION='" + request.getParameter("clearCondition") + "',CRITICALTHRESHOLDVALUE='-1', WARNINGTHRESHOLDVALUE='-1', INFOTHRESHOLDVALUE='-1' where ID = " + id;
/* 2484 */                             mo.executeUpdateStmt(updateactionquery);
/* 2485 */                             PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("update AM_FLOAT_THRESHOLDCONFIG set CRITICALTHRESHOLDVALUE=?, WARNINGTHRESHOLDVALUE=?,INFOTHRESHOLDVALUE=? where ID=?");
/* 2486 */                             ps.setString(1, criticalValue);
/* 2487 */                             ps.setString(2, warningValue);
/* 2488 */                             ps.setString(3, clearValue);
/* 2489 */                             ps.setInt(4, id);
/* 2490 */                             ps.executeUpdate();
/*      */                           }
/*      */                           else {
/* 2493 */                             mo.executeUpdateStmt(updateactionquery);
/*      */                           }
/* 2495 */                           com.adventnet.appmanager.dbcache.AMCacheHandler.setThresholdProfileinCache(id);
/*      */                         }
/*      */                       }
/*      */                       catch (Exception ex)
/*      */                       {
/* 2500 */                         ex.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/* 2504 */                     String thresid = request.getParameter("thresholdid");
/* 2505 */                     String remoteUser = request.getRemoteUser();
/* 2506 */                     int userID = Integer.parseInt(DBUtil.getUserID(remoteUser));
/* 2507 */                     boolean isAllowedToEdit = false;
/*      */                     
/* 2509 */                     ArrayList rows = new ArrayList();
/* 2510 */                     if ((thresid != null) && (!thresid.equals("Reset")) && (!thresid.equals("Newfalse")) && (!thresid.equals("Newtrue")))
/*      */                     {
/* 2512 */                       isAllowedToEdit = com.manageengine.appmanager.util.DelegatedUserRoleUtil.isOwnedByDelegatedUser(Integer.parseInt(thresid), userID, 1);
/*      */                       
/* 2514 */                       String query = com.adventnet.appmanager.db.DBQueryUtil.getDBQuery("am.showstringthresholdconfiguration.query", new String[] { getCondition("CRITICALTHRESHOLDCONDITION"), getCondition("WARNINGTHRESHOLDCONDITION"), getCondition("INFOTHRESHOLDCONDITION") });
/* 2515 */                       query = query + " and AM_THRESHOLDCONFIG.ID=" + thresid;
/* 2516 */                       rows = mo.getRows(query);
/*      */                     }
/*      */                     else
/*      */                     {
/* 2520 */                       String bgclass = "monitorinfoeven-thresholds";
/* 2521 */                       String txtWidth = "5";
/*      */                       
/* 2523 */                       out.write("\n<input type=\"hidden\" name=\"description\" value=\"\">\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\" class=\"dottedline\">\n\n    <tr style=\"margin-top:5px;\">\n\n    <td height=\"22\" width=\"20%\" class=\"");
/* 2524 */                       out.print(bgclass);
/* 2525 */                       out.write(" label-align\"><b>&nbsp;");
/* 2526 */                       out.print(FormatUtil.getString("am.webclient.threshold.thresholdname"));
/* 2527 */                       out.write("</b></td>\n    <td height=\"22\" colspan=\"3\" class=\"");
/* 2528 */                       out.print(bgclass);
/* 2529 */                       out.write("\" title='");
/* 2530 */                       out.print(FormatUtil.getString("am.webclient.threshold.thresholdname"));
/* 2531 */                       out.write("'><input type=\"text\" name=\"displayname\" styleClass=\"formtext normal\" maxlength=\"100\" size=\"30\" style=\"margin-top:5px;\"></td>");
/* 2532 */                       out.write("\n    </tr>\n\n     <tr><td colspan=\"4\" height=\"5\" class=\"");
/* 2533 */                       out.print(bgclass);
/* 2534 */                       out.write("\"></td></tr>\n\n    <tr class=\"");
/* 2535 */                       out.print(bgclass);
/* 2536 */                       out.write("\">\n\n    <td height=\"22\"  class=\"monitorinfoeven-thresholds label-align\">&nbsp;<span><b>");
/* 2537 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalalert"));
/* 2538 */                       out.write("</b></span> </td>\n    <td width=\"7%\" class=\"");
/* 2539 */                       out.print(bgclass);
/* 2540 */                       out.write("\">\n    \t");
/*      */                       
/* 2542 */                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2543 */                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2544 */                       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2545 */                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2546 */                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                         for (;;) {
/* 2548 */                           out.write(10);
/* 2549 */                           out.write(9);
/* 2550 */                           if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                             return;
/* 2552 */                           out.write(10);
/* 2553 */                           out.write(9);
/*      */                           
/* 2555 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2556 */                           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2557 */                           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2558 */                           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2559 */                           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                             for (;;) {
/* 2561 */                               out.write(10);
/* 2562 */                               out.write(9);
/*      */                               
/* 2564 */                               txtWidth = "40";
/*      */                               
/* 2566 */                               out.write("\n\t<select name=\"criticalthresholdcondition\" class=\"formtext medium\"><option value=\"CT\">");
/* 2567 */                               out.print(FormatUtil.getString("contains"));
/* 2568 */                               out.write("</option><option value=\"DC\">");
/* 2569 */                               out.print(FormatUtil.getString("does not contain"));
/* 2570 */                               out.write("</option><option value=\"QL\">");
/* 2571 */                               out.print(FormatUtil.getString("equal to"));
/* 2572 */                               out.write("</option><option value=\"NQ\">");
/* 2573 */                               out.print(FormatUtil.getString("not equal to"));
/* 2574 */                               out.write("</option><option value=\"SW\">");
/* 2575 */                               out.print(FormatUtil.getString("starts with"));
/* 2576 */                               out.write("</option><option value=\"EW\">");
/* 2577 */                               out.print(FormatUtil.getString("ends with"));
/* 2578 */                               out.write("</option></select>\n\t");
/* 2579 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2580 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2584 */                           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2585 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                           }
/*      */                           
/* 2588 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2589 */                           out.write(10);
/* 2590 */                           out.write(9);
/* 2591 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2592 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2596 */                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2597 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                       }
/*      */                       
/* 2600 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2601 */                       out.write("\n\t</td>\n    <td width=\"15%\" title='");
/* 2602 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalthresholdvalue"));
/* 2603 */                       out.write("'><input type=\"text\" name=\"criticalthresholdvalue\" Class=\"formtext medium\" size=");
/* 2604 */                       out.print(txtWidth);
/* 2605 */                       out.write("></td>\n    ");
/*      */                       
/* 2607 */                       ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2608 */                       _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2609 */                       _jspx_th_c_005fchoose_005f1.setParent(null);
/* 2610 */                       int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2611 */                       if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                         for (;;) {
/* 2613 */                           out.write("\n    ");
/*      */                           
/* 2615 */                           WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2616 */                           _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2617 */                           _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                           
/* 2619 */                           _jspx_th_c_005fwhen_005f1.setTest("${param.thresholdid=='Newfalse'}");
/* 2620 */                           int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2621 */                           if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                             for (;;) {
/* 2623 */                               out.write("\n    <td align=\"left\"><a id=\"addmorecritical\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/* 2624 */                               out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/* 2625 */                               out.write("</a></td>\n    ");
/* 2626 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2627 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2631 */                           if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2632 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                           }
/*      */                           
/* 2635 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2636 */                           out.write("\n    ");
/* 2637 */                           if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/*      */                             return;
/* 2639 */                           out.write("\n    ");
/* 2640 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2641 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2645 */                       if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2646 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                       }
/*      */                       
/* 2649 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2650 */                       out.write("\n    </tr>\n\n    <tr id=\"criticalrow_secondary\" style=\"display: none;\" class=\"");
/* 2651 */                       out.print(bgclass);
/* 2652 */                       out.write("\">\n\n    <td height=\"22\" class=\"label-align\"><select id=\"criticalconditionjoiner\" name=\"criticalconditionjoiner\" class=\"formtext msmall\"> \n\t\t<option value=\"OR\" selected=\"selected\">");
/* 2653 */                       out.print(FormatUtil.getString("OR"));
/* 2654 */                       out.write(" </option><option value=\"AND\">");
/* 2655 */                       out.print(FormatUtil.getString("AND"));
/* 2656 */                       out.write("</option></select>\n    </td>\n    <td width=\"7%\" class=\"");
/* 2657 */                       out.print(bgclass);
/* 2658 */                       out.write("\">\n        <select id=\"secondarycriticalthresholdcondition\" name=\"secondarycriticalthresholdcondition\" class=\"formtext msmall\"><option value=\"LT\">&lt;</option><option value=\"GT\" selected=\"selected\">&gt;</option><option value=\"EQ\">=</option><option value=\"NE\">!=</option><option value=\"LE\">&lt;=</option><option value=\"GE\">&gt;=</option></select>\n        </td>\n    <td width=\"15%\" title='");
/* 2659 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalthresholdvalue"));
/* 2660 */                       out.write("'><input type=\"text\" id=\"secondarycriticalthresholdvalue\" name=\"secondarycriticalthresholdvalue\" Class=\"formtext medium\" size=");
/* 2661 */                       out.print(txtWidth);
/* 2662 */                       out.write("</td>\n    <td id=\"criticalclose\" align=\"left\" ><img src=\"/images/prereq_notconfigured.gif\" /></td>\n    </tr>\n\n\n     <tr><td colspan=\"4\" height=\"5\" class=\"");
/* 2663 */                       out.print(bgclass);
/* 2664 */                       out.write("\"></td></tr>\n   <tr style=\"margin-top:5px;\"  class=\"");
/* 2665 */                       out.print(bgclass);
/* 2666 */                       out.write("\">\n\t<td  width=\"10%\" class=\"label-align\">");
/* 2667 */                       out.print(FormatUtil.getString("am.webclient.configurealert.advancedoptions"));
/* 2668 */                       out.write("</td>\n\t<td colspan=\"3\" class=\"monitorinfoeven-thresholds\"><input name=\"checkbox2\" type=\"checkbox\" ");
/* 2669 */                       out.print(FaultUtil.ADVANCED_USER ? "checked" : "");
/* 2670 */                       out.write(" onclick=\"javascript:toggleDiv('thresholdInlineAdvanced');\"></td>\n\t\n   </tr>\t\n     <tr><td colspan=\"4\" height=\"5\" class=\"");
/* 2671 */                       out.print(bgclass);
/* 2672 */                       out.write("\"></td></tr>\n\n    <tr>\n\n    <td colspan=\"4\">\n    <div id=\"thresholdInlineAdvanced\" style=\"display:");
/* 2673 */                       out.print(FaultUtil.ADVANCED_USER ? "block" : "none");
/* 2674 */                       out.write("\">\n\n    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"");
/* 2675 */                       out.print(bgclass);
/* 2676 */                       out.write("\">\n    <tr><td colspan=\"4\" height=\"5\"></td></tr>\n    <tr>\n    <td height=\"22\" width=\"20%\" class=\"");
/* 2677 */                       out.print(bgclass);
/* 2678 */                       out.write(" label-align\"><span class=\"\"><b>");
/* 2679 */                       out.print(FormatUtil.getString("am.webclient.threshold.warningalert"));
/* 2680 */                       out.write("</b>&nbsp;</span> </td>\n    <td width=\"7%\" class=\"monitorinfoeven-thresholds\">\n\t");
/*      */                       
/* 2682 */                       ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2683 */                       _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2684 */                       _jspx_th_c_005fchoose_005f2.setParent(null);
/* 2685 */                       int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2686 */                       if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                         for (;;) {
/* 2688 */                           out.write(10);
/* 2689 */                           out.write(9);
/* 2690 */                           if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/*      */                             return;
/* 2692 */                           out.write(10);
/* 2693 */                           out.write(9);
/*      */                           
/* 2695 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2696 */                           _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2697 */                           _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2698 */                           int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2699 */                           if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                             for (;;) {
/* 2701 */                               out.write("\n\t<select name=\"warningthresholdcondition\" class=\"formtext medium\"><option value=\"CT\">");
/* 2702 */                               out.print(FormatUtil.getString("contains"));
/* 2703 */                               out.write("</option><option value=\"DC\">");
/* 2704 */                               out.print(FormatUtil.getString("does not contain"));
/* 2705 */                               out.write("</option><option value=\"QL\">");
/* 2706 */                               out.print(FormatUtil.getString("equal to"));
/* 2707 */                               out.write("</option><option value=\"NQ\">");
/* 2708 */                               out.print(FormatUtil.getString("not equal to"));
/* 2709 */                               out.write("</option><option value=\"SW\">");
/* 2710 */                               out.print(FormatUtil.getString("starts with"));
/* 2711 */                               out.write("</option><option value=\"EW\">");
/* 2712 */                               out.print(FormatUtil.getString("ends with"));
/* 2713 */                               out.write("</option></select>\n\t");
/* 2714 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2715 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2719 */                           if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2720 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                           }
/*      */                           
/* 2723 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2724 */                           out.write(10);
/* 2725 */                           out.write(9);
/* 2726 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2727 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2731 */                       if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2732 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                       }
/*      */                       
/* 2735 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2736 */                       out.write("\n\t</td>\n\t<td width=\"15%\" title='");
/* 2737 */                       out.print(FormatUtil.getString("am.webclient.threshold.warningthresholdvalue"));
/* 2738 */                       out.write("' ><input type=\"text\" name=\"warningthresholdvalue\" class=\"formtext medium\" size=");
/* 2739 */                       out.print(txtWidth);
/* 2740 */                       out.write("></td>\n    ");
/*      */                       
/* 2742 */                       ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2743 */                       _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2744 */                       _jspx_th_c_005fchoose_005f3.setParent(null);
/* 2745 */                       int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2746 */                       if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                         for (;;) {
/* 2748 */                           out.write("\n    ");
/*      */                           
/* 2750 */                           WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2751 */                           _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2752 */                           _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                           
/* 2754 */                           _jspx_th_c_005fwhen_005f3.setTest("${param.thresholdid=='Newfalse'}");
/* 2755 */                           int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2756 */                           if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                             for (;;) {
/* 2758 */                               out.write("\n    <td align=\"left\" ><a id=\"addmorewarning\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/* 2759 */                               out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/* 2760 */                               out.write("</a></td>\n    ");
/* 2761 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2762 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2766 */                           if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2767 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                           }
/*      */                           
/* 2770 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2771 */                           out.write("\n    ");
/* 2772 */                           if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/*      */                             return;
/* 2774 */                           out.write("\n    ");
/* 2775 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2776 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2780 */                       if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2781 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                       }
/*      */                       
/* 2784 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2785 */                       out.write("\n\t</tr>\n    <tr id=\"warningrow_secondary\" style=\"display: none;\" class=\"");
/* 2786 */                       out.print(bgclass);
/* 2787 */                       out.write("\">\n    <td height=\"22\"  width=\"20%\" align=\"right\"><select name=\"warningconditionjoiner\" class=\"formtext msmall\"> \n\t\t<option value=\"OR\" selected=\"selected\">");
/* 2788 */                       out.print(FormatUtil.getString("OR"));
/* 2789 */                       out.write(" </option><option value=\"AND\">");
/* 2790 */                       out.print(FormatUtil.getString("AND"));
/* 2791 */                       out.write("</option></select>\n    </td>\n    <td width=\"7%\" class=\"");
/* 2792 */                       out.print(bgclass);
/* 2793 */                       out.write("\">\n        <select name=\"secondarywarningthresholdcondition\" class=\"formtext msmall\"><option value=\"LT\">&lt;</option><option value=\"GT\" selected=\"selected\">&gt;</option><option value=\"EQ\">=</option><option value=\"NE\">!=</option><option value=\"LE\">&lt;=</option><option value=\"GE\">&gt;=</option></select>\n        </td>\n    <td width=\"15%\" title='");
/* 2794 */                       out.print(FormatUtil.getString("am.webclient.threshold.warningthresholdvalue"));
/* 2795 */                       out.write("'><input type=\"text\" name=\"secondarywarningthresholdvalue\" Class=\"formtext medium\" size=");
/* 2796 */                       out.print(txtWidth);
/* 2797 */                       out.write("</td>\n    <td id=\"warningclose\" align=\"left\" ><img src=\"/images/prereq_notconfigured.gif\" /></td>\n</tr>\n\t <tr><td colspan=\"4\" height=\"5\"></td></tr>\n\t<tr class=\"");
/* 2798 */                       out.print(bgclass);
/* 2799 */                       out.write("\">\n\t<td height=\"22\" class=\"label-align\"><span class=\"");
/* 2800 */                       out.print(bgclass);
/* 2801 */                       out.write("\"><b>");
/* 2802 */                       out.print(FormatUtil.getString("am.webclient.threshold.clearalert"));
/* 2803 */                       out.write("</b>&nbsp;</span> </td>\n\t<td width=\"7%\" class=\"monitorinfoeven-thresholds\">\n\t");
/*      */                       
/* 2805 */                       ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2806 */                       _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2807 */                       _jspx_th_c_005fchoose_005f4.setParent(null);
/* 2808 */                       int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2809 */                       if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                         for (;;) {
/* 2811 */                           out.write(10);
/* 2812 */                           out.write(9);
/* 2813 */                           if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/*      */                             return;
/* 2815 */                           out.write(10);
/* 2816 */                           out.write(9);
/*      */                           
/* 2818 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2819 */                           _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2820 */                           _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 2821 */                           int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2822 */                           if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                             for (;;) {
/* 2824 */                               out.write("\n\t<select name=\"infothresholdcondition\" class=\"formtext medium\"><option value=\"CT\">");
/* 2825 */                               out.print(FormatUtil.getString("contains"));
/* 2826 */                               out.write("</option><option value=\"DC\">");
/* 2827 */                               out.print(FormatUtil.getString("does not contain"));
/* 2828 */                               out.write("</option><option value=\"QL\">");
/* 2829 */                               out.print(FormatUtil.getString("equal to"));
/* 2830 */                               out.write("</option><option value=\"NQ\">");
/* 2831 */                               out.print(FormatUtil.getString("not equal to"));
/* 2832 */                               out.write("</option><option value=\"SW\">");
/* 2833 */                               out.print(FormatUtil.getString("starts with"));
/* 2834 */                               out.write("</option><option value=\"EW\">");
/* 2835 */                               out.print(FormatUtil.getString("ends with"));
/* 2836 */                               out.write("</option></select>\n\t");
/* 2837 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2838 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2842 */                           if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2843 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                           }
/*      */                           
/* 2846 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2847 */                           out.write(10);
/* 2848 */                           out.write(9);
/* 2849 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2850 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2854 */                       if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2855 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                       }
/*      */                       
/* 2858 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2859 */                       out.write("\n\t</td>\n\t<td title='");
/* 2860 */                       out.print(FormatUtil.getString("am.webclient.threshold.clearthresholdvalue"));
/* 2861 */                       out.write("' ><input type=\"text\" name=\"infothresholdvalue\" class=\"formtext medium\" size=");
/* 2862 */                       out.print(txtWidth);
/* 2863 */                       out.write("></td>\n    ");
/*      */                       
/* 2865 */                       ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2866 */                       _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2867 */                       _jspx_th_c_005fchoose_005f5.setParent(null);
/* 2868 */                       int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2869 */                       if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                         for (;;) {
/* 2871 */                           out.write("\n    ");
/*      */                           
/* 2873 */                           WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2874 */                           _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2875 */                           _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                           
/* 2877 */                           _jspx_th_c_005fwhen_005f5.setTest("${param.thresholdid=='Newfalse'}");
/* 2878 */                           int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2879 */                           if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                             for (;;) {
/* 2881 */                               out.write("\n    <td align=\"left\" ><a id=\"addmoreinfo\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/* 2882 */                               out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/* 2883 */                               out.write("</a></td>\n    ");
/* 2884 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2885 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2889 */                           if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2890 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                           }
/*      */                           
/* 2893 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2894 */                           out.write("\n    ");
/* 2895 */                           if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/*      */                             return;
/* 2897 */                           out.write("\n    ");
/* 2898 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2899 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2903 */                       if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2904 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                       }
/*      */                       
/* 2907 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2908 */                       out.write("\n    </tr>\n\t <tr><td colspan=\"4\" height=\"5\"></td></tr>\n    <tr id=\"inforow_secondary\" style=\"display: none;\" class=\"");
/* 2909 */                       out.print(bgclass);
/* 2910 */                       out.write("\">\n    <td height=\"22\"  width=\"20%\" align=\"right\"><select name=\"infoconditionjoiner\" class=\"formtext msmall\"> \n\t\t<option value=\"OR\" selected=\"selected\">");
/* 2911 */                       out.print(FormatUtil.getString("am.webclient.ruleMgr.condition.or"));
/* 2912 */                       out.write(" </option><option value=\"AND\">");
/* 2913 */                       out.print(FormatUtil.getString("am.webclient.ruleMgr.condition.and"));
/* 2914 */                       out.write("</option></select>\n    </td>\n    <td width=\"7%\" class=\"");
/* 2915 */                       out.print(bgclass);
/* 2916 */                       out.write("\">\n        <select name=\"secondaryinfothresholdcondition\" class=\"formtext msmall\"><option value=\"LT\">&lt;</option><option value=\"GT\" selected=\"selected\">&gt;</option><option value=\"EQ\">=</option><option value=\"NE\">!=</option><option value=\"LE\">&lt;=</option><option value=\"GE\">&gt;=</option></select>\n        </td>\n    <td width=\"15%\" title='");
/* 2917 */                       out.print(FormatUtil.getString("am.webclient.threshold.infothresholdvalue"));
/* 2918 */                       out.write("'><input type=\"text\" name=\"secondaryinfothresholdvalue\" Class=\"formtext medium\" size=");
/* 2919 */                       out.print(txtWidth);
/* 2920 */                       out.write("</td>\n    <td id=\"infoclose\" align=\"left\" ><img src=\"/images/prereq_notconfigured.gif\" /></td>\n</tr>\n\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\t</table>\n");
/*      */                     }
/*      */                     
/* 2923 */                     request.setAttribute("isAllowedToEdit", Boolean.valueOf(isAllowedToEdit));
/*      */                     
/* 2925 */                     out.write("\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></script>\n");
/*      */                     
/*      */ 
/* 2928 */                     if (rows.size() > 0)
/*      */                     {
/*      */ 
/* 2931 */                       out.write("\n<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven-thresholds dottedline\">\n\t");
/*      */                       
/* 2933 */                       String bgclass = "monitorinfoeven-thresholds";
/* 2934 */                       ArrayList row = (ArrayList)rows.get(0);
/*      */                       
/* 2936 */                       out.write("\n    <tr>\n\t<td>\n\t<div id=\"showThreshold\"   style=\"DISPLAY: block\">\n        <table width=\"100%\" border=\"0\" colspan=\"5\">\n        <tr>\n\t\t     <td width=\"25%\" height=\"30\" class=\"bodytext\" title=\"");
/* 2937 */                       out.print(FormatUtil.getString((String)row.get(1)));
/* 2938 */                       out.write(34);
/* 2939 */                       out.write(62);
/* 2940 */                       out.print(FormatUtil.getString((String)row.get(1)));
/* 2941 */                       out.write("</td>\n            <td class=\"bodytext\" width=\"25%\"><b>");
/* 2942 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalalert"));
/* 2943 */                       out.write("</b> \n        \n\t\t");
/*      */                       
/* 2945 */                       String criticalcondition = String.valueOf(row.get(3));
/* 2946 */                       String criticalThValue = row.get(4) != null ? String.valueOf(row.get(4)) : "-";
/* 2947 */                       String[] splitCvalues = splitMultiConditionThreshold(criticalcondition, criticalThValue);
/*      */                       
/*      */ 
/* 2950 */                       out.write("\n        \t  ");
/* 2951 */                       out.print(splitCvalues[0]);
/* 2952 */                       out.write(" \n                <a style=\"cursor:pointer\" class=\"conf-mon-txt\" onMouseOver=\"ddrivetip(this,event,'");
/* 2953 */                       out.print(splitCvalues[1]);
/* 2954 */                       out.write("',false,true,'#000000',250,'lightyellow','break-all')\" onmouseout=\"hideddrivetip()\">\n   \t\t\t\t\t");
/* 2955 */                       out.print(splitCvalues[1].length() < 30 ? splitCvalues[1] : FormatUtil.getTrimmedText(splitCvalues[1], 30));
/* 2956 */                       out.write("</a>\n   \t\t\t\t \n\t</td>\n            <td class=\"bodytext\" width=\"25%\"><b>");
/* 2957 */                       out.print(FormatUtil.getString("am.webclient.threshold.warningalert"));
/* 2958 */                       out.write("</b> \n\t\t\t ");
/*      */                       
/* 2960 */                       String warningcondition = String.valueOf(row.get(6));
/* 2961 */                       String warningThValue = String.valueOf(row.get(7));
/* 2962 */                       String[] splitWvalues = splitMultiConditionThreshold(warningcondition, warningThValue);
/*      */                       
/* 2964 */                       out.write("\n                \t ");
/* 2965 */                       out.print(splitWvalues[0]);
/* 2966 */                       out.write(" \n   \t\t\t\t\t<a style=\"cursor:pointer\" class=\"conf-mon-txt\" onMouseOver=\"ddrivetip(this,event,'");
/* 2967 */                       out.print(splitWvalues[1]);
/* 2968 */                       out.write("',false,true,'#000000',250,'lightyellow','break-all')\" onmouseout=\"hideddrivetip()\">\n   \t\t\t\t\t");
/* 2969 */                       out.print(splitWvalues[1].length() < 30 ? splitWvalues[1] : FormatUtil.getTrimmedText(splitWvalues[1], 30));
/* 2970 */                       out.write("</a>\n   \t\t\t\t\t\n            </td>\n            <td class=\"bodytext\" width=\"25%\"><b>");
/* 2971 */                       out.print(FormatUtil.getString("am.webclient.threshold.clearalert"));
/* 2972 */                       out.write("</b>\n\t\t ");
/*      */                       
/* 2974 */                       String infocondition = String.valueOf(row.get(9));
/* 2975 */                       String infoThValue = String.valueOf(row.get(10));
/* 2976 */                       String[] splitIvalues = splitMultiConditionThreshold(infocondition, infoThValue);
/*      */                       
/* 2978 */                       out.write("\n                 ");
/* 2979 */                       out.print(splitIvalues[0]);
/* 2980 */                       out.write(" \n                <a style=\"cursor:pointer\" class=\"conf-mon-txt\" onMouseOver=\"ddrivetip(this,event,'");
/* 2981 */                       out.print(splitIvalues[1]);
/* 2982 */                       out.write("',false,true,'#000000',250,'lightyellow','break-all')\" onmouseout=\"hideddrivetip()\">\n   \t\t\t\t\t");
/* 2983 */                       out.print(splitIvalues[1].length() < 30 ? splitIvalues[1] : FormatUtil.getTrimmedText(splitIvalues[1], 30));
/* 2984 */                       out.write("</a>\n              \n            </td>\n        \n\t\t\t");
/*      */                       
/* 2986 */                       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2987 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2988 */                       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                       
/* 2990 */                       _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2991 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2992 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2994 */                           out.write("\n\t\t\t\t<td class=\"bodytext\" width=\"8%\">\n\t\t\t\t");
/*      */                           
/* 2996 */                           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2997 */                           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2998 */                           _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                           
/* 3000 */                           _jspx_th_c_005fif_005f0.setTest("${!adminThresholdConfig}");
/* 3001 */                           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3002 */                           if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                             for (;;) {
/* 3004 */                               out.write("\n\t\t\t\t\t<input name=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 3005 */                               out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 3006 */                               out.write("\" type=\"button\" onClick=\"showEdit()\">\n\t\t\t\t");
/* 3007 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3008 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3012 */                           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3013 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                           }
/*      */                           
/* 3016 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3017 */                           out.write("\n\t\t\t\t</td>\n\t\t\t");
/* 3018 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3019 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3023 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3024 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                       }
/*      */                       
/* 3027 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3028 */                       out.write("\n\t\t\n        </tr>\n\t\t</table>\n\t\t</div>\n\t\t<div id=\"editThreshold\"   style=\"DISPLAY: none\">\n\t\t <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\" class=\"bodytext\">\n\t\t\t");
/*      */                       
/* 3030 */                       bgclass = "bodytext";
/* 3031 */                       row = (ArrayList)rows.get(0);
/* 3032 */                       ResultSet extractQuery = AMConnectionPool.executeQueryStmt("select NAME,CRITICALTHRESHOLDCONDITION,WARNINGTHRESHOLDCONDITION,INFOTHRESHOLDCONDITION,TYPE from AM_THRESHOLDCONFIG where ID=" + thresid);
/* 3033 */                       String thresholdName = "";
/* 3034 */                       String criCon = "";
/* 3035 */                       String criCon2 = "";
/* 3036 */                       Object crithvalue1 = "";
/* 3037 */                       String crithvalue2 = "";
/* 3038 */                       String criconditionjoiner = "";
/* 3039 */                       String warCon = "";
/* 3040 */                       String warCon2 = "";
/* 3041 */                       Object warthvalue1 = "";
/* 3042 */                       String warthvalue2 = "";
/* 3043 */                       String warconditionjoiner = "";
/* 3044 */                       String clearCon = "";
/* 3045 */                       String clearCon2 = "";
/* 3046 */                       Object clearthvalue1 = "";
/* 3047 */                       String clearthvalue2 = "";
/* 3048 */                       String clearconditionjoiner = "";
/* 3049 */                       int thresholdType = 2;
/* 3050 */                       if (extractQuery.next())
/*      */                       {
/* 3052 */                         thresholdName = extractQuery.getString("NAME");
/* 3053 */                         criCon = extractQuery.getString("CRITICALTHRESHOLDCONDITION");
/* 3054 */                         List<String> multiCcondition = AMRegexUtil.getThresholdGroups(criCon, false);
/* 3055 */                         thresholdType = extractQuery.getInt("TYPE");
/*      */                         
/* 3057 */                         if ((thresholdType != 3) && (multiCcondition != null)) {
/* 3058 */                           criCon = (String)multiCcondition.get(0);
/* 3059 */                           crithvalue1 = multiCcondition.get(1);
/* 3060 */                           criconditionjoiner = (String)multiCcondition.get(4);
/* 3061 */                           criCon2 = (String)multiCcondition.get(5);
/* 3062 */                           crithvalue2 = (String)multiCcondition.get(6);
/*      */                         }
/*      */                         else {
/* 3065 */                           crithvalue1 = row.get(4);
/*      */                         }
/* 3067 */                         warCon = extractQuery.getString("WARNINGTHRESHOLDCONDITION");
/* 3068 */                         multiCcondition = AMRegexUtil.getThresholdGroups(warCon, false);
/* 3069 */                         if ((thresholdType != 3) && (multiCcondition != null)) {
/* 3070 */                           warCon = (String)multiCcondition.get(0);
/* 3071 */                           warthvalue1 = multiCcondition.get(1);
/* 3072 */                           warconditionjoiner = (String)multiCcondition.get(4);
/* 3073 */                           warCon2 = (String)multiCcondition.get(5);
/* 3074 */                           warthvalue2 = (String)multiCcondition.get(6);
/*      */                         }
/*      */                         else {
/* 3077 */                           warthvalue1 = row.get(7);
/*      */                         }
/*      */                         
/* 3080 */                         clearCon = extractQuery.getString("INFOTHRESHOLDCONDITION");
/* 3081 */                         multiCcondition = AMRegexUtil.getThresholdGroups(clearCon, false);
/* 3082 */                         if ((thresholdType != 3) && (multiCcondition != null)) {
/* 3083 */                           clearCon = (String)multiCcondition.get(0);
/* 3084 */                           clearthvalue1 = multiCcondition.get(1);
/* 3085 */                           clearconditionjoiner = (String)multiCcondition.get(4);
/* 3086 */                           clearCon2 = (String)multiCcondition.get(5);
/* 3087 */                           clearthvalue2 = (String)multiCcondition.get(6);
/*      */                         }
/*      */                         else {
/* 3090 */                           clearthvalue1 = row.get(10);
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 3096 */                       out.write(10);
/* 3097 */                       out.write(9);
/* 3098 */                       out.write(9);
/* 3099 */                       if ((criconditionjoiner != null) && (!criconditionjoiner.isEmpty()))
/*      */                       {
/*      */ 
/* 3102 */                         out.write("\n\t   \t\t<script> $(\"#secondarycriticalexist\").val('true');\t</script>\n\t\t");
/*      */                       } else {
/* 3104 */                         out.write("\n\t   \t\t<script> $(\"#secondarycriticalexist\").val('false');\t</script>\n\t\t");
/*      */                       }
/* 3106 */                       out.write("\t\n\t\t\n\t\t");
/* 3107 */                       if ((warconditionjoiner != null) && (!warconditionjoiner.isEmpty())) {
/* 3108 */                         out.write("\n                        <input type=\"hidden\" id=\"secondarywarningexist\" value=\"true\">\n                ");
/*      */                       } else {
/* 3110 */                         out.write("\n                        <input type=\"hidden\" id=\"secondarywarningexist\" value=\"false\">\n                ");
/*      */                       }
/* 3112 */                       out.write("\n\n\t\t");
/* 3113 */                       if ((clearconditionjoiner != null) && (!clearconditionjoiner.isEmpty())) {
/* 3114 */                         out.write("\n                        <input type=\"hidden\" id=\"secondaryinfoexist\" value=\"true\">\n                ");
/*      */                       } else {
/* 3116 */                         out.write("\n                        <input type=\"hidden\" id=\"secondaryinfoexist\" value=\"false\">\n                ");
/*      */                       }
/* 3118 */                       out.write("\t\n\t\t<tr>\n\t\t<td height=\"22\" class=\"");
/* 3119 */                       out.print(bgclass);
/* 3120 */                       out.write("\">\n\t\t<table width=\"100%\" cellpadding=\"5\">\n\t\t<tr>\n\t\t<td height=\"22\" width=\"20%\" class=\"");
/* 3121 */                       out.print(bgclass);
/* 3122 */                       out.write(" label-align\"><b>");
/* 3123 */                       out.print(FormatUtil.getString("am.webclient.threshold.thresholdname"));
/* 3124 */                       out.write("</b></td>\n\t\t<td height=\"22\" colspan=\"3\" class=\"");
/* 3125 */                       out.print(bgclass);
/* 3126 */                       out.write("\" title=\"Threshold Name\"><input type=\"text\" name=\"thresName\" value=\"");
/* 3127 */                       out.print(FormatUtil.getString(thresholdName));
/* 3128 */                       out.write("\" styleClass=\"formtext normal\" maxlength=\"100\" size=\"30\"></td>\n\t\t</tr>\n        <tr>\n\t\t<td height=\"22\" class=\"label-align\" nowrap><span class=\"");
/* 3129 */                       out.print(bgclass);
/* 3130 */                       out.write("\"><b>");
/* 3131 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalalert"));
/* 3132 */                       out.write("</b></span> </td>\n\t\t<td width=\"7%\" >\n\t\t  ");
/*      */                       
/* 3134 */                       String txtWidth = "5";
/* 3135 */                       String selectclass = "formtext msmall";
/* 3136 */                       if (thresholdType == 3)
/*      */                       {
/* 3138 */                         txtWidth = "40";
/* 3139 */                         selectclass = "formtext medium";
/*      */                       }
/*      */                       
/* 3142 */                       out.write("\n\t\t   \t<select name=\"criticalthresholdcondition1\" class=\"");
/* 3143 */                       out.print(selectclass);
/* 3144 */                       out.write("\">\n\t\t   ");
/*      */                       
/* 3146 */                       Map<String, String[]> conditionMap = setSelectedCondition(criCon, thresholdType);
/* 3147 */                       for (Map.Entry<String, String[]> selectoption : conditionMap.entrySet()) {
/* 3148 */                         String conditionindb = (String)selectoption.getKey();
/* 3149 */                         String conditionforview = FormatUtil.getString(((String[])selectoption.getValue())[1]);
/* 3150 */                         String isselected = ((String[])selectoption.getValue())[0];
/*      */                         
/* 3152 */                         out.write("\t\n\t\t\t<option value=\"");
/* 3153 */                         out.print(conditionindb);
/* 3154 */                         out.write(34);
/* 3155 */                         out.write(32);
/* 3156 */                         out.print(isselected);
/* 3157 */                         out.write(62);
/* 3158 */                         out.print(conditionforview);
/* 3159 */                         out.write("</option>\n\t\t  ");
/*      */                       }
/* 3161 */                       out.write("\t\t\n\t\t\t</select>\n\t\t<td width=\"12%\" title='");
/* 3162 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalthresholdvalue"));
/* 3163 */                       out.write("'><input type=\"text\" name=\"criticalthresholdvalue1\" value=\"");
/* 3164 */                       out.print(crithvalue1);
/* 3165 */                       out.write("\" Class=\"formtext small\" size=");
/* 3166 */                       out.print(txtWidth);
/* 3167 */                       out.write("></td>\n\t\t </td>\n\t\t ");
/* 3168 */                       if (thresholdType != 3) {
/* 3169 */                         out.write("\n\t\t<td align=\"left\"><a id=\"addmorecritical\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/* 3170 */                         out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/* 3171 */                         out.write("</a></td>\n\t\t");
/*      */                       } else {
/* 3173 */                         out.write("\n    <td align=\"left\">&nbsp;</td>\n    ");
/*      */                       }
/* 3175 */                       out.write("\n    \n</tr>\n\t\t<tr>\n\t\t\n\t\t<tr id=\"criticalrow_secondary\" style=\"display: none;\" class=\"");
/* 3176 */                       out.print(bgclass);
/* 3177 */                       out.write("\">\n\n    <td height=\"22\"  align=\"right\"><select id=\"criticalconditionjoiner\" name=\"criticalconditionjoiner\" class=\"");
/* 3178 */                       out.print(selectclass);
/* 3179 */                       out.write("\">\n                <option value=\"OR\" selected=\"selected\">");
/* 3180 */                       out.print(FormatUtil.getString("am.webclient.ruleMgr.condition.or"));
/* 3181 */                       out.write(" </option><option value=\"AND\">");
/* 3182 */                       out.print(FormatUtil.getString("am.webclient.ruleMgr.condition.and"));
/* 3183 */                       out.write("</option></select>\n    </td>\n    <td width=\"7%\" class=\"");
/* 3184 */                       out.print(bgclass);
/* 3185 */                       out.write("\">\n        <select id=\"secondarycriticalthresholdcondition\" name=\"secondarycriticalthresholdcondition\" class=\"formtext msmall\">\n\t");
/*      */                       
/* 3187 */                       conditionMap = setSelectedCondition(criCon2, thresholdType);
/* 3188 */                       for (Map.Entry<String, String[]> selectoption : conditionMap.entrySet()) {
/* 3189 */                         String conditionindb = (String)selectoption.getKey();
/* 3190 */                         String conditionforview = FormatUtil.getString(((String[])selectoption.getValue())[1]);
/* 3191 */                         String isselected = ((String[])selectoption.getValue())[0];
/*      */                         
/* 3193 */                         out.write("\n                        <option value=\"");
/* 3194 */                         out.print(conditionindb);
/* 3195 */                         out.write(34);
/* 3196 */                         out.write(32);
/* 3197 */                         out.print(isselected);
/* 3198 */                         out.write(62);
/* 3199 */                         out.print(conditionforview);
/* 3200 */                         out.write("</option>\n                  ");
/*      */                       }
/* 3202 */                       out.write("\n\t</select>\n        </td>\n    <td width=\"12%\" title='");
/* 3203 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalthresholdvalue"));
/* 3204 */                       out.write("'><input type=\"text\" id=\"secondarycriticalthresholdvalue\" name=\"secondarycriticalthresholdvalue\" Class=\"formtext small\" value=\"");
/* 3205 */                       out.print(crithvalue2);
/* 3206 */                       out.write("\" size=");
/* 3207 */                       out.print(txtWidth);
/* 3208 */                       out.write("</td>\n    <td id=\"criticalclose\" align=\"left\" ><img src=\"/images/prereq_notconfigured.gif\" /></td>\n    </tr>\n\n\t\t<td styleClass=\"formtext normal\" width=\"20%\" align=\"right\"><input name=\"showOption\" type=\"checkbox\" onclick=\"javascript:toggleDiv('thresholdInlineAdvancedEdit');\"><span class=\"bodytext\">");
/* 3209 */                       out.print(FormatUtil.getString("am.webclient.configurealert.advancedoptions"));
/* 3210 */                       out.write("</span></td>\n\t\t<td colspan=\"3\">&nbsp; </td>\n\t\t</tr>\n\t\t<tr>\n\t\t<td colspan=\"4\">\n\t\t\t<div id=\"thresholdInlineAdvancedEdit\" style=\"display:none\">\n\n\t\t\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\">\n\t\t<tr>\n\t\t<td height=\"22\" width=\"20%\" class=\"label-align\" nowrap><span class=\"");
/* 3211 */                       out.print(bgclass);
/* 3212 */                       out.write("\"><b>");
/* 3213 */                       out.print(FormatUtil.getString("am.webclient.threshold.warningalert"));
/* 3214 */                       out.write("</b>&nbsp;</span> </td>\n\t\t<td width=\"7%\" >\n        \t<select id=\"criticalthresholdcondition1\" name=\"criticalthresholdcondition1\" class=\"");
/* 3215 */                       out.print(selectclass);
/* 3216 */                       out.write("\">\n\t\t");
/*      */                       
/* 3218 */                       conditionMap = setSelectedCondition(warCon, thresholdType);
/* 3219 */                       for (Map.Entry<String, String[]> selectoption : conditionMap.entrySet()) {
/* 3220 */                         String conditionindb = (String)selectoption.getKey();
/* 3221 */                         String conditionforview = FormatUtil.getString(((String[])selectoption.getValue())[1]);
/* 3222 */                         String isselected = ((String[])selectoption.getValue())[0];
/*      */                         
/* 3224 */                         out.write("\n                        <option value=\"");
/* 3225 */                         out.print(conditionindb);
/* 3226 */                         out.write(34);
/* 3227 */                         out.write(32);
/* 3228 */                         out.print(isselected);
/* 3229 */                         out.write(62);
/* 3230 */                         out.print(conditionforview);
/* 3231 */                         out.write("</option>\n                  ");
/*      */                       }
/* 3233 */                       out.write("\n\t\t</select>\n\t\t</td>\n\t\t<td styleClass=\"formtext normal\" title='");
/* 3234 */                       out.print(FormatUtil.getString("am.webclient.threshold.warningthresholdvalue"));
/* 3235 */                       out.write("' ><input type=\"text\" name=\"criticalthresholdvalue1\" value=\"");
/* 3236 */                       out.print(warthvalue1);
/* 3237 */                       out.write("\" styleClass=\"formtext normal\"  size=");
/* 3238 */                       out.print(txtWidth);
/* 3239 */                       out.write("></td>\n\t\t");
/* 3240 */                       if (thresholdType != 3) {
/* 3241 */                         out.write("\n\t\t<td align=\"left\"><a id=\"addmorewarning\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/* 3242 */                         out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/* 3243 */                         out.write("</a></td>\n\t\t");
/*      */                       } else {
/* 3245 */                         out.write("\n    <td align=\"left\">&nbsp;</td>\n    ");
/*      */                       }
/* 3247 */                       out.write("\n</tr>\n                <tr>\n                <tr id=\"warningrow_secondary\" style=\"display: none;\" class=\"");
/* 3248 */                       out.print(bgclass);
/* 3249 */                       out.write("\">\n\n    <td height=\"22\"  align=\"right\"><select id=\"warningconditionjoiner\" name=\"warningconditionjoiner\" class=\"formtext msmall\">\n                <option value=\"OR\" selected=\"selected\">");
/* 3250 */                       out.print(FormatUtil.getString("am.webclient.ruleMgr.condition.or"));
/* 3251 */                       out.write(" </option><option value=\"AND\">");
/* 3252 */                       out.print(FormatUtil.getString("am.webclient.ruleMgr.condition.and"));
/* 3253 */                       out.write("</option></select>\n    </td>\n    <td width=\"7%\" class=\"");
/* 3254 */                       out.print(bgclass);
/* 3255 */                       out.write("\">\n        <select name=\"secondarycriticalthresholdcondition\" id=\"secondarywarningthresholdcondition\" class=\"");
/* 3256 */                       out.print(selectclass);
/* 3257 */                       out.write("\">\n\t\t");
/*      */                       
/* 3259 */                       conditionMap = setSelectedCondition(warCon2, thresholdType);
/* 3260 */                       for (Map.Entry<String, String[]> selectoption : conditionMap.entrySet()) {
/* 3261 */                         String conditionindb = (String)selectoption.getKey();
/* 3262 */                         String conditionforview = FormatUtil.getString(((String[])selectoption.getValue())[1]);
/* 3263 */                         String isselected = ((String[])selectoption.getValue())[0];
/*      */                         
/* 3265 */                         out.write("\n                        <option value=\"");
/* 3266 */                         out.print(conditionindb);
/* 3267 */                         out.write(34);
/* 3268 */                         out.write(32);
/* 3269 */                         out.print(isselected);
/* 3270 */                         out.write(62);
/* 3271 */                         out.print(conditionforview);
/* 3272 */                         out.write("</option>\n                  ");
/*      */                       }
/* 3274 */                       out.write("\n\n\t</select>\n        </td>\n    <td width=\"12%\" title='");
/* 3275 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalthresholdvalue"));
/* 3276 */                       out.write("'><input type=\"text\" id=\"secondarywarningthresholdvalue\" name=\"secondarywarningthresholdvalue\" value=\"");
/* 3277 */                       out.print(warthvalue2);
/* 3278 */                       out.write("\" styleClass=\"formtext normal\" size=");
/* 3279 */                       out.print(txtWidth);
/* 3280 */                       out.write("</td>\n    <td id=\"warningclose\" align=\"left\" ><img src=\"/images/prereq_notconfigured.gif\" /></td>\n\n\t\t</tr>\n\t\t<tr>\n\t\t<td height=\"22\" width=\"20%\" class=\"label-align\" nowrap><span class=\"");
/* 3281 */                       out.print(bgclass);
/* 3282 */                       out.write("\"><b>");
/* 3283 */                       out.print(FormatUtil.getString("am.webclient.threshold.clearalert"));
/* 3284 */                       out.write("</b>&nbsp;</span> </td>\n\t\t<td width=\"7%\" >\n\t\t   <select name=\"criticalthresholdcondition1\" class=\"");
/* 3285 */                       out.print(selectclass);
/* 3286 */                       out.write("\">\n\t\t\t");
/*      */                       
/* 3288 */                       conditionMap = setSelectedCondition(clearCon, thresholdType);
/* 3289 */                       for (Map.Entry<String, String[]> selectoption : conditionMap.entrySet()) {
/* 3290 */                         String conditionindb = (String)selectoption.getKey();
/* 3291 */                         String conditionforview = FormatUtil.getString(((String[])selectoption.getValue())[1]);
/* 3292 */                         String isselected = ((String[])selectoption.getValue())[0];
/*      */                         
/* 3294 */                         out.write("\n                        <option value=\"");
/* 3295 */                         out.print(conditionindb);
/* 3296 */                         out.write(34);
/* 3297 */                         out.write(32);
/* 3298 */                         out.print(isselected);
/* 3299 */                         out.write(62);
/* 3300 */                         out.print(conditionforview);
/* 3301 */                         out.write("</option>\n                  ");
/*      */                       }
/* 3303 */                       out.write("\n\n\t\t   </select>\n\t\t</td>\n\t\t<td width=\"12%\" styleClass=\"formtext normal\" title='");
/* 3304 */                       out.print(FormatUtil.getString("am.webclient.threshold.clearthresholdvalue"));
/* 3305 */                       out.write("' ><input type=\"text\" name=\"criticalthresholdvalue1\" value=\"");
/* 3306 */                       out.print(clearthvalue1);
/* 3307 */                       out.write("\" styleClass=\"formtext normal\"  size=");
/* 3308 */                       out.print(txtWidth);
/* 3309 */                       out.write("></td>\n\t\t");
/* 3310 */                       if (thresholdType != 3) {
/* 3311 */                         out.write("\n\t\t<td align=\"left\"><a id=\"addmoreinfo\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/* 3312 */                         out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/* 3313 */                         out.write("</a></td>\n\t\t");
/*      */                       } else {
/* 3315 */                         out.write("\n    <td align=\"left\">&nbsp;</td>\n    ");
/*      */                       }
/* 3317 */                       out.write("\n</tr>\n                <tr>\n                <tr id=\"inforow_secondary\" style=\"display: none;\" class=\"");
/* 3318 */                       out.print(bgclass);
/* 3319 */                       out.write("\">\n\n    <td height=\"22\"  align=\"right\"><select id=\"infoconditionjoiner\" name=\"infoconditionjoiner\" class=\"formtext msmall\">\n                <option value=\"OR\" selected=\"selected\">");
/* 3320 */                       out.print(FormatUtil.getString("am.webclient.ruleMgr.condition.or"));
/* 3321 */                       out.write(" </option><option value=\"AND\">");
/* 3322 */                       out.print(FormatUtil.getString("am.webclient.ruleMgr.condition.and"));
/* 3323 */                       out.write("</option></select>\n    </td>\n    <td width=\"7%\" class=\"");
/* 3324 */                       out.print(bgclass);
/* 3325 */                       out.write("\">\n        <select name=\"secondarycriticalthresholdcondition\" id=\"secondaryinfothresholdcondition\" class=\"");
/* 3326 */                       out.print(selectclass);
/* 3327 */                       out.write("\">\n\t");
/*      */                       
/* 3329 */                       conditionMap = setSelectedCondition(clearCon2, thresholdType);
/* 3330 */                       for (Map.Entry<String, String[]> selectoption : conditionMap.entrySet()) {
/* 3331 */                         String conditionindb = (String)selectoption.getKey();
/* 3332 */                         String conditionforview = FormatUtil.getString(((String[])selectoption.getValue())[1]);
/* 3333 */                         String isselected = ((String[])selectoption.getValue())[0];
/*      */                         
/* 3335 */                         out.write("\n                        <option value=\"");
/* 3336 */                         out.print(conditionindb);
/* 3337 */                         out.write(34);
/* 3338 */                         out.write(32);
/* 3339 */                         out.print(isselected);
/* 3340 */                         out.write(62);
/* 3341 */                         out.print(conditionforview);
/* 3342 */                         out.write("</option>\n                  ");
/*      */                       }
/* 3344 */                       out.write("\n\n\t</select>\n        </td>\n    <td width=\"12%\" title='");
/* 3345 */                       out.print(FormatUtil.getString("am.webclient.threshold.criticalthresholdvalue"));
/* 3346 */                       out.write("'><input type=\"text\" id=\"secondaryinfothresholdvalue\" name=\"secondaryinfothresholdvalue\" value=\"");
/* 3347 */                       out.print(clearthvalue2);
/* 3348 */                       out.write("\" styleClass=\"formtext normal\" size=");
/* 3349 */                       out.print(txtWidth);
/* 3350 */                       out.write("</td>\n    <td id=\"infoclose\" align=\"left\" ><img src=\"/images/prereq_notconfigured.gif\" /></td>\n    </tr>\n\t\t</table>\n\t\t</div>\n\t\t</td>\n\t\t</tr>\n\n\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n\t\t\t\t    <tbody><tr>\n\n\t\t\t\t    <tr><td colspan=\"2\" style=\"height:10px;\"></td></tr>\n\t\t<td width=\"20%\" class=\"label-align\"></td>\n\t\t\t\t\t <td >\n\t\t\t");
/*      */                       
/* 3352 */                       ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3353 */                       _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 3354 */                       _jspx_th_c_005fchoose_005f6.setParent(null);
/* 3355 */                       int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 3356 */                       if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                         for (;;) {
/* 3358 */                           out.write("\n\t\t\t  ");
/*      */                           
/* 3360 */                           WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3361 */                           _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3362 */                           _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                           
/* 3364 */                           _jspx_th_c_005fwhen_005f6.setTest("${isAllowedToEdit}");
/* 3365 */                           int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3366 */                           if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                             for (;;) {
/* 3368 */                               out.write("\n\t\t\t\t&nbsp;<input width=\"30\" align=\"middle\" name=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 3369 */                               out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 3370 */                               out.write("\" onclick=\"javascript:editThresholdValueAjax(");
/* 3371 */                               out.print(thresid);
/* 3372 */                               out.write(",thresName.value,criticalthresholdcondition1,criticalthresholdvalue1,");
/* 3373 */                               out.print(thresholdType);
/* 3374 */                               out.write(")\" type=\"button\">&nbsp;&nbsp;\n\t\t\t\t<input type=\"reset\" name=\"button\" class=\"buttons btn_reset\" value=\"");
/* 3375 */                               out.print(FormatUtil.getString("am.webclient.common.reset.text"));
/* 3376 */                               out.write("\">&nbsp;&nbsp;\n\t\t\t  ");
/* 3377 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3378 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3382 */                           if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3383 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                           }
/*      */                           
/* 3386 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3387 */                           out.write("\n\t\t\t  ");
/*      */                           
/* 3389 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3390 */                           _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3391 */                           _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 3392 */                           int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3393 */                           if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                             for (;;) {
/* 3395 */                               out.write("\n\t\t\t\t&nbsp;<p class=\"bg-info\">");
/* 3396 */                               out.print(FormatUtil.getString("am.webclient.delegatedadmin.notauthorised.update.message"));
/* 3397 */                               out.write("</p>&nbsp;\n\t\t\t  ");
/* 3398 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3399 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3403 */                           if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3404 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                           }
/*      */                           
/* 3407 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3408 */                           out.write("\n\t\t\t");
/* 3409 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3410 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3414 */                       if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3415 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                       }
/*      */                       
/* 3418 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3419 */                       out.write("\n\t\t\t\t<input name=\"button\" class=\"buttons btn_link\" value=\"");
/* 3420 */                       out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3421 */                       out.write("\" onclick=\"takeBack()\" type=\"button\"></td>\n      \n\t\t\t\t    </tr>\n\t\t\t  </tbody></table>\n\t</table>\n\t</div>\n   </td>\n    </tr>\n</table>\n");
/*      */                     }
/*      */                     
/*      */ 
/* 3425 */                     out.write(10);
/* 3426 */                     if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */                       return;
/* 3428 */                     out.write(10);
/* 3429 */                     out.write(10);
/*      */                   }
/* 3431 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3432 */         out = _jspx_out;
/* 3433 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3434 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3435 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3438 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3444 */     PageContext pageContext = _jspx_page_context;
/* 3445 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3447 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3448 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3449 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3451 */     _jspx_th_c_005fwhen_005f0.setTest("${param.thresholdid=='Newfalse'}");
/* 3452 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3453 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 3455 */         out.write("<select name=\"criticalthresholdcondition\" class=\"formtext msmall\"><option value=\"LT\">&lt;</option><option value=\"GT\" selected=\"selected\">&gt;</option><option value=\"EQ\">=</option><option value=\"NE\">!=</option><option value=\"LE\">&lt;=</option><option value=\"GE\">&gt;=</option></select>\n\t");
/* 3456 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3457 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3461 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3462 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3463 */       return true;
/*      */     }
/* 3465 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3466 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3471 */     PageContext pageContext = _jspx_page_context;
/* 3472 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3474 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3475 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3476 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 3477 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3478 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 3480 */         out.write("\n    <td align=\"left\">&nbsp;</td>\n    ");
/* 3481 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3482 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3486 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3487 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3488 */       return true;
/*      */     }
/* 3490 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3496 */     PageContext pageContext = _jspx_page_context;
/* 3497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3499 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3500 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3501 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 3503 */     _jspx_th_c_005fwhen_005f2.setTest("${param.thresholdid=='Newfalse'}");
/* 3504 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3505 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 3507 */         out.write("\n\t<select name=\"warningthresholdcondition\" class=\"formtext msmall\"><option value=\"LT\">&lt;</option><option value=\"GT\">&gt;</option><option value=\"EQ\" selected=\"selected\">=</option><option value=\"NE\">!=</option><option value=\"LE\">&lt;=</option><option value=\"GE\">&gt;=</option></select>\n\t");
/* 3508 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3509 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3513 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3514 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3515 */       return true;
/*      */     }
/* 3517 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3518 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3523 */     PageContext pageContext = _jspx_page_context;
/* 3524 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3526 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3527 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3528 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 3529 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3530 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 3532 */         out.write("\n    <td align=\"left\">&nbsp;</td>\n    ");
/* 3533 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3534 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3538 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3539 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3540 */       return true;
/*      */     }
/* 3542 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3543 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3548 */     PageContext pageContext = _jspx_page_context;
/* 3549 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3551 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3552 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3553 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 3555 */     _jspx_th_c_005fwhen_005f4.setTest("${param.thresholdid=='Newfalse'}");
/* 3556 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3557 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 3559 */         out.write("\n\t<select name=\"infothresholdcondition\" class=\"formtext msmall\"><option value=\"LT\" selected=\"selected\">&lt;</option><option value=\"GT\">&gt;</option><option value=\"EQ\">=</option><option value=\"NE\">!=</option><option value=\"LE\">&lt;=</option><option value=\"GE\">&gt;=</option></select>\n\t");
/* 3560 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3561 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3565 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3566 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3567 */       return true;
/*      */     }
/* 3569 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3575 */     PageContext pageContext = _jspx_page_context;
/* 3576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3578 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3579 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3580 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 3581 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3582 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 3584 */         out.write("\n    <td align=\"left\">&nbsp;</td>\n    ");
/* 3585 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3586 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3590 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3591 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3592 */       return true;
/*      */     }
/* 3594 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3595 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3600 */     PageContext pageContext = _jspx_page_context;
/* 3601 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3603 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3604 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3605 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 3607 */     _jspx_th_c_005fif_005f1.setTest("${true}");
/* 3608 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3609 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3611 */         out.write(10);
/* 3612 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3613 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3617 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3618 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3619 */       return true;
/*      */     }
/* 3621 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3622 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ThresholdProfile_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */