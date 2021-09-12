/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.BarChart;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.it360.util.SLAUtil;
/*      */ import java.io.IOException;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ 
/*      */ public final class BAEventReport_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 1293 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
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
/* 1991 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
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
/* 2188 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2189 */   static { _jspx_dependants.put("/jsp/includes/SlaLinks.jspf", Long.valueOf(1473429417000L));
/* 2190 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2208 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2212 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2223 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2227 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2229 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2230 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2231 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.release();
/* 2232 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2233 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2236 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2243 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2246 */     JspWriter out = null;
/* 2247 */     Object page = this;
/* 2248 */     JspWriter _jspx_out = null;
/* 2249 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2253 */       response.setContentType("text/html;charset=UTF-8");
/* 2254 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2256 */       _jspx_page_context = pageContext;
/* 2257 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2258 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2259 */       session = pageContext.getSession();
/* 2260 */       out = pageContext.getOut();
/* 2261 */       _jspx_out = out;
/*      */       
/* 2263 */       out.write("<!DOCTYPE html>\n");
/* 2264 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2265 */       out.write(10);
/* 2266 */       out.write(10);
/* 2267 */       Hashtable availabilitykeys = null;
/* 2268 */       synchronized (application) {
/* 2269 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2270 */         if (availabilitykeys == null) {
/* 2271 */           availabilitykeys = new Hashtable();
/* 2272 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/* 2275 */       out.write(10);
/* 2276 */       Hashtable healthkeys = null;
/* 2277 */       synchronized (application) {
/* 2278 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2279 */         if (healthkeys == null) {
/* 2280 */           healthkeys = new Hashtable();
/* 2281 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/* 2284 */       out.write(10);
/*      */       
/* 2286 */       request.setAttribute("HelpKey", "Manager Console");
/* 2287 */       boolean isIT360SLAEnabled = SLAUtil.isIT360SLAEnabled(request);
/* 2288 */       String isdefaultview = request.getAttribute("defaultView") != null ? (String)request.getAttribute("defaultView") : "";
/*      */       
/* 2290 */       out.write("\n\n\n\n\n\n\n\n\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n\n\n\n\n\n\n\n");
/* 2291 */       ManagedApplication mo = null;
/* 2292 */       synchronized (application) {
/* 2293 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2294 */         if (mo == null) {
/* 2295 */           mo = new ManagedApplication();
/* 2296 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2299 */       out.write("\n<script>\nvar searchForm;\nvar reportForm1 = document.forms[0];\nvar reportForm2 = document.forms[1];\nvar showReportsForm = document.forms[2];\nvar evTblForm = document.forms[3];\nfunction myOnLoad()\n{\n\tSORTTABLENAME = 'eventTable';\n\tnumberOfColumnsToBeSorted=1;\n\tignoreCheckBox=false;\n\t");
/* 2300 */       if (isIT360SLAEnabled) {
/* 2301 */         out.write("\n    //numberOfColumnsToBeSorted=2;\n    //ignoreCheckBox=true;\n    searchForm = document.forms[0];\n    reportForm1 = document.forms[1];\n    reportForm2 = document.forms[2];\n    showReportsForm = document.forms[3];\n    evTblForm = document.forms[4];\n    jQuery(\"#loadingg\").hide();//No I18N\n\t");
/* 2302 */       } else if (isdefaultview.equals("true")) {
/* 2303 */         out.write("\n\t\tsearchForm = document.forms[0];\n\t\t    reportForm1 = document.forms[1];\n\t\t    reportForm2 = document.forms[2];\n\t\t    showReportsForm = document.forms[3];\n\t\t    evTblForm = document.forms[4];\n\t");
/*      */       }
/* 2305 */       out.write("\n\tsortables_init(numberOfColumnsToBeSorted,ignoreCheckBox);\n}\nfunction fnCheckCustomTime(f)\n {\n if(f.startDate.value=='')\n {\n alert(\"");
/* 2306 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/* 2307 */       out.write("\");\n return false\n }\n else if(f.endDate.value=='')\n {\n alert(\"");
/* 2308 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/* 2309 */       out.write("\")\n return false\n }\n var s=f.startDate.value;\n var e=f.endDate.value;\n    if(s>e)\n    {\n     alert(\"");
/* 2310 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/* 2311 */       out.write("\" );\n     return false;\n    }\n return true\n }\n function fnSetEndTime(a)\n{\n\tshowReportsForm.endDate.value=a;\n}\nfunction fnSetStartTime(a)\n{\n\tshowReportsForm.startDate.value=a;\n}\n function fnPeriod(periodcombo)\n{\n     \treportForm2.submit();\n}\nfunction getNewWindow(url, title, width, height, params)\n{\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title,true, winParms);\n            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww',true, winParms);\n            return newwindow;\n        }\n}\nfunction fnshowData(b,c)\n{\n        reportForm1.action='/showReports.do';\n\treportForm1.haid.value=b;\n\treportForm1.actionMethod.value='generateEventSummary';//No I18N\n\treportForm1.period.value=c;\n\treportForm1.PRINTER_FRIENDLY.value=true;\n");
/* 2312 */       out.write("\t //var newwindow = getNewWindow('#','Reports','800','900','true');\n\t var newwindow =window.open('#','Reports','resizable=yes,scrollbars=yes,width=800,height=900')\n\treportForm1.target=newwindow.name;\n\treportForm1.submit();\n}\n//TODO: Need to betterment the implementation by using obj ref 'this' rather than using 4 js method.\nfunction showAvailabilitySLATab()\n{\n\tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n\tjQuery(\"#showAvailabilitySLATab\").show(); //No I18N\n\tjQuery(\"#showServerSLATab\").hide(); //No I18N\n\tjQuery(\"#showEventVolumeSLATab\").hide(); //No I18N\n\tjQuery(\"#showSLASettingsTab\").hide(); //No I18N\n\tjQuery(\"#loadingg\").show(); //No I18N\n\tdocument.location.href=\"/showBussiness.do?method=generateApplicationAvailablity\";\n}\nfunction showServerSLATab()\n{\n\tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n\tjQuery(\"#showEventVolumeSLATab\").hide(); //No I18N\n\tjQuery(\"#showServerSLATab\").show(); //No I18N\n\tjQuery(\"#showAvailabilitySLATab\").hide(); //No I18N\n\tjQuery(\"#showSLASettingsTab\").hide(); //No I18N\n\tjQuery(\"#loadingg\").show(); //No I18N\n");
/* 2313 */       out.write("\tdocument.location.href=\"/showBussiness.do?method=generateSystemAvailablity\";\n}\nfunction showEventVolumeSLATab()\n{\n\tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n\tjQuery(\"#showServerSLATab\").hide(); //No I18N\n\tjQuery(\"#showEventVolumeSLATab\").show(); //No I18N\n\tjQuery(\"#showAvailabilitySLATab\").hide(); //No I18N\n\tjQuery(\"#showSLASettingsTab\").hide(); //No I18N\n\tjQuery(\"#loadingg\").show(); //No I18N\n\tdocument.location.href=\"/showBussiness.do?method=generateTroubleTicket\";\n}\nfunction showSLASettingsTab()\n{\n\tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n\tjQuery(\"#showSLASettingsTab\").show(); //No I18N\n\tjQuery(\"#showServerSLATab\").hide(); //No I18N\n\tjQuery(\"#showEventVolumeSLATab\").hide(); //No I18N\n\tjQuery(\"#showAvailabilitySLATab\").hide(); //No I18N\n\tjQuery(\"#loadingg\").show(); //No I18N\n\tdocument.location.href=\"/showBussiness.do?method=generateSLA&sla=true\";\n}\n</script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/* 2314 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2316 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2317 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2318 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2320 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2322 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2324 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2326 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2327 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2328 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2329 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2332 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2333 */         String available = null;
/* 2334 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2335 */         out.write(10);
/*      */         
/* 2337 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2338 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2339 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2341 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2343 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2345 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2347 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2348 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2349 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2350 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2353 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2354 */           String unavailable = null;
/* 2355 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2356 */           out.write(10);
/*      */           
/* 2358 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2359 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2360 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2362 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2364 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2366 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2368 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2369 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2370 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2371 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2374 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2375 */             String unmanaged = null;
/* 2376 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2377 */             out.write(10);
/*      */             
/* 2379 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2380 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2381 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2383 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2385 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2387 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2389 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2390 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2391 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2392 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2395 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2396 */               String scheduled = null;
/* 2397 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2398 */               out.write(10);
/*      */               
/* 2400 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2401 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2402 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2404 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2406 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2408 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2410 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2411 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2412 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2413 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2416 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2417 */                 String critical = null;
/* 2418 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2419 */                 out.write(10);
/*      */                 
/* 2421 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2422 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2423 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2425 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2427 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2429 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2431 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2432 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2433 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2434 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2437 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2438 */                   String clear = null;
/* 2439 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2440 */                   out.write(10);
/*      */                   
/* 2442 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2443 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2444 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2446 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2448 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2450 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2452 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2453 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2454 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2455 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2458 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2459 */                     String warning = null;
/* 2460 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2461 */                     out.write(10);
/* 2462 */                     out.write(10);
/*      */                     
/* 2464 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2465 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2467 */                     out.write(10);
/* 2468 */                     out.write(10);
/* 2469 */                     out.write(10);
/* 2470 */                     out.write(10);
/* 2471 */                     out.write(10);
/*      */                     
/* 2473 */                     String layOutPage = null;
/* 2474 */                     String titleName = null;
/* 2475 */                     String headerPage = null;
/*      */                     
/* 2477 */                     if (isIT360SLAEnabled)
/*      */                     {
/* 2479 */                       layOutPage = "/jsp/BasicLayoutNoLeft.jsp";
/* 2480 */                       titleName = FormatUtil.getString("am.webclient.sla.slaview.txt");
/* 2481 */                       headerPage = "/jsp/header.jsp";
/* 2482 */                       request.setAttribute("oldtab", "7");
/*      */                     }
/* 2484 */                     else if ((isdefaultview != null) && (isdefaultview.trim().equalsIgnoreCase("true")))
/*      */                     {
/* 2486 */                       layOutPage = "/jsp/ManagerLayout.jsp";
/* 2487 */                       titleName = FormatUtil.getString("am.webclient.manager.ttslatab.title.text");
/* 2488 */                       headerPage = "/jsp/header.jsp";
/*      */                     }
/*      */                     else
/*      */                     {
/* 2492 */                       layOutPage = "/jsp/ManagerLayout.jsp";
/* 2493 */                       titleName = FormatUtil.getString("am.webclient.manager.ttslatab.title.text");
/* 2494 */                       headerPage = "/jsp/ManagerHeader.jsp";
/*      */                     }
/*      */                     
/* 2497 */                     out.write(10);
/* 2498 */                     out.write(10);
/*      */                     
/* 2500 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2501 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2502 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2504 */                     _jspx_th_tiles_005finsert_005f0.setPage(layOutPage);
/* 2505 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2506 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2508 */                         out.write(10);
/*      */                         
/* 2510 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2511 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2512 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2514 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2516 */                         _jspx_th_tiles_005fput_005f0.setValue(titleName);
/* 2517 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2518 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2519 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2522 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2523 */                         out.write(32);
/*      */                         
/* 2525 */                         PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2526 */                         _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2527 */                         _jspx_th_tiles_005fput_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2529 */                         _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */                         
/* 2531 */                         _jspx_th_tiles_005fput_005f1.setValue(headerPage);
/* 2532 */                         int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2533 */                         if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2534 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1); return;
/*      */                         }
/*      */                         
/* 2537 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2538 */                         out.write(10);
/* 2539 */                         out.write(10);
/* 2540 */                         out.write(32);
/*      */                         
/* 2542 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2543 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2544 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2546 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2548 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2549 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2550 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2551 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2552 */                             out = _jspx_page_context.pushBody();
/* 2553 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2554 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2557 */                             out.write(10);
/* 2558 */                             out.write(10);
/* 2559 */                             if (isIT360SLAEnabled) {
/* 2560 */                               out.write(10);
/* 2561 */                               out.write(10);
/* 2562 */                               out.write(9);
/*      */                               
/* 2564 */                               String titles = SLAUtil.getSLASubTabsTitle();
/* 2565 */                               String functions = SLAUtil.getSLASubTabsJS();
/* 2566 */                               String selectedTab = "am.webclient.manager.ttslatab.tableheading.text";
/*      */                               
/* 2568 */                               out.write(10);
/* 2569 */                               out.write(9);
/* 2570 */                               JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(titles), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(titles), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(functions), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedTab), request.getCharacterEncoding()), out, true);
/* 2571 */                               out.write(10);
/* 2572 */                               out.write(9);
/*      */                             }
/*      */                             
/* 2575 */                             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\" align=\"center\" id=\"showEventVolumeSLATab\">\n");
/* 2576 */                             String Periodvalue = (String)request.getAttribute("pvalue");
/* 2577 */                             out.write("\n <form action='/showReports.do'>\n             <input type=\"hidden\" name=\"actionMethod\" value=\"\">\n                  <input type=\"hidden\" name=\"period\" value=\"\"><input type=\"hidden\" name=\"haid\" value=\"\">\n                  <input type='hidden' name='PRINTER_FRIENDLY' value='true'>\n                  </form>\n<tr>\n<td width='80%' valign='top'>\n");
/*      */                             try {
/* 2579 */                               ArrayList list = (ArrayList)request.getAttribute("array");
/*      */                               int wide;
/* 2581 */                               int wide; if ((list.size() == 0) || (list.size() == 1))
/*      */                               {
/* 2583 */                                 wide = 300;
/*      */                               } else { int wide;
/* 2585 */                                 if ((list.size() > 1) && (list.size() <= 4))
/*      */                                 {
/* 2587 */                                   wide = list.size() * 75;
/*      */                                 } else { int wide;
/* 2589 */                                   if ((list.size() > 4) && (list.size() < 10))
/*      */                                   {
/* 2591 */                                     wide = list.size() * 65;
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2595 */                                     wide = list.size() * 36; }
/*      */                                 }
/*      */                               }
/* 2598 */                               String width = String.valueOf(wide);
/*      */                               
/* 2600 */                               out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" class='grayfullborder'>\n    <tr>\n\t      <td  width=\"99%\" class=\"tableheadingbborder\" >");
/* 2601 */                               out.print(FormatUtil.getString("am.webclient.manager.ttslatab.heading.text", new String[] { Periodvalue }));
/* 2602 */                               out.write("\n\t      </td>\n\t </tr>\n   <tr>\n          <td height=\"170\" align=\"center\" class=\"bodytext\">\n\n          ");
/*      */                               
/* 2604 */                               BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 2605 */                               _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 2606 */                               _jspx_th_awolf_005fbarchart_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2608 */                               _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("bargraph");
/*      */                               
/* 2610 */                               _jspx_th_awolf_005fbarchart_005f0.setWidth(width);
/*      */                               
/* 2612 */                               _jspx_th_awolf_005fbarchart_005f0.setHeight("195");
/*      */                               
/* 2614 */                               _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */                               
/* 2616 */                               _jspx_th_awolf_005fbarchart_005f0.setUrl(false);
/*      */                               
/* 2618 */                               _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.manager.ttslatab.xaxis.text"));
/*      */                               
/* 2620 */                               _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.manager.ttslatab.yaxis.text"));
/*      */                               
/* 2622 */                               _jspx_th_awolf_005fbarchart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2623 */                               int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 2624 */                               if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 2625 */                                 if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2626 */                                   out = _jspx_page_context.pushBody();
/* 2627 */                                   _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 2628 */                                   _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2631 */                                   out.write("\n            ");
/* 2632 */                                   int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 2633 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2636 */                                 if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2637 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2640 */                               if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 2641 */                                 this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*      */                               }
/*      */                               
/* 2644 */                               this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 2645 */                               out.write("\n          </td>\n\n        </tr>\n\n      </table></td>\n\n<td width=\"20%\" valign='top'>\n");
/* 2646 */                               out.write("<!--$Id$-->\n\n");
/*      */                               
/* 2648 */                               String uri = request.getRequestURI();
/* 2649 */                               if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin())
/*      */                               {
/* 2651 */                                 out.write("\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  style=\"Display:inline: padding:5px;margin-bottom:10px;\" class=\"lrtbdarkborder\">\n    <tr>\n    <td class=\"leftlinksheading\">");
/* 2652 */                                 out.print(FormatUtil.getString("am.webclient.jboss.quicklinks.text"));
/* 2653 */                                 out.write("</td>\n    </tr>\n    <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateSLA&showdiv=true\" class=\"new-left-links\">");
/* 2654 */                                 out.print(FormatUtil.getString("am.webclient.manager.newsla.text"));
/* 2655 */                                 out.write("</a></td>\n   </tr>\n   ");
/* 2656 */                                 if (!uri.contains("Bussiness")) {
/* 2657 */                                   out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"new-left-links\">");
/* 2658 */                                   out.print(FormatUtil.getString("Application"));
/* 2659 */                                   out.write(32);
/* 2660 */                                   out.write(32);
/* 2661 */                                   out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/* 2662 */                                   out.write("</a></td>\n   </tr>\n   "); }
/* 2663 */                                 if (!uri.contains("SystemAvailablity")) {
/* 2664 */                                   out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"new-left-links\">");
/* 2665 */                                   out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/* 2666 */                                   out.write("</a></td>\n   </tr>\n   "); }
/* 2667 */                                 if (!uri.contains("BAEventReport")) {
/* 2668 */                                   out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"new-left-links\">");
/* 2669 */                                   out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/* 2670 */                                   out.write("</a></td>\n   </tr>\n   ");
/*      */                                 }
/* 2672 */                                 out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"new-left-links\">");
/* 2673 */                                 out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/* 2674 */                                 out.write("</a></td>\n   </tr>\n</table>\n");
/*      */                               }
/* 2676 */                               out.write(" \n <table width=\"95%\" class=\"lrtbdarkborder\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align='right'>\n <tr>\n <td  align='left' >");
/*      */                               
/* 2678 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2679 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2680 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2682 */                               _jspx_th_html_005fform_005f0.setAction("/showBussiness.do?method=generateTroubleTicket");
/*      */                               
/* 2684 */                               _jspx_th_html_005fform_005f0.setStyle("Display:inline");
/* 2685 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2686 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2688 */                                   out.write("\n\n               <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n                <tr>\n                <td class=\"tableheadingbborder\">");
/* 2689 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.text"));
/* 2690 */                                   out.write("</td>\n              </tr>\n              <tr>\n               <td class=\"leftlinkstd\"> ");
/*      */                                   
/* 2692 */                                   SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2693 */                                   _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2694 */                                   _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2696 */                                   _jspx_th_html_005fselect_005f0.setProperty("period");
/*      */                                   
/* 2698 */                                   _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnPeriod(this)");
/*      */                                   
/* 2700 */                                   _jspx_th_html_005fselect_005f0.setStyleClass("formtextselected marg-tbtm-7px");
/* 2701 */                                   int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2702 */                                   if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2703 */                                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2704 */                                       out = _jspx_page_context.pushBody();
/* 2705 */                                       _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2706 */                                       _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2709 */                                       out.write("\n                  ");
/*      */                                       
/* 2711 */                                       OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2712 */                                       _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2713 */                                       _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2715 */                                       _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */                                       
/* 2717 */                                       _jspx_th_html_005foption_005f0.setValue("0");
/* 2718 */                                       int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2719 */                                       if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2720 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                       }
/*      */                                       
/* 2723 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/* 2724 */                                       out.write(32);
/*      */                                       
/* 2726 */                                       OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2727 */                                       _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2728 */                                       _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2730 */                                       _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */                                       
/* 2732 */                                       _jspx_th_html_005foption_005f1.setValue("3");
/* 2733 */                                       int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2734 */                                       if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2735 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                       }
/*      */                                       
/* 2738 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/* 2739 */                                       out.write("\n                  ");
/*      */                                       
/* 2741 */                                       OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2742 */                                       _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2743 */                                       _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2745 */                                       _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */                                       
/* 2747 */                                       _jspx_th_html_005foption_005f2.setValue("6");
/* 2748 */                                       int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2749 */                                       if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2750 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                       }
/*      */                                       
/* 2753 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/*      */                                       
/* 2755 */                                       OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2756 */                                       _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2757 */                                       _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2759 */                                       _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                                       
/* 2761 */                                       _jspx_th_html_005foption_005f3.setValue("1");
/* 2762 */                                       int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2763 */                                       if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2764 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                       }
/*      */                                       
/* 2767 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/* 2768 */                                       out.write(32);
/*      */                                       
/* 2770 */                                       OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2771 */                                       _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2772 */                                       _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2774 */                                       _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */                                       
/* 2776 */                                       _jspx_th_html_005foption_005f4.setValue("12");
/* 2777 */                                       int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2778 */                                       if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2779 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                       }
/*      */                                       
/* 2782 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/* 2783 */                                       out.write("\n                   ");
/*      */                                       
/* 2785 */                                       OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2786 */                                       _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2787 */                                       _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2789 */                                       _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */                                       
/* 2791 */                                       _jspx_th_html_005foption_005f5.setValue("7");
/* 2792 */                                       int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2793 */                                       if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2794 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                       }
/*      */                                       
/* 2797 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/* 2798 */                                       out.write(32);
/*      */                                       
/* 2800 */                                       OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2801 */                                       _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2802 */                                       _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2804 */                                       _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */                                       
/* 2806 */                                       _jspx_th_html_005foption_005f6.setValue("11");
/* 2807 */                                       int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2808 */                                       if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2809 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                       }
/*      */                                       
/* 2812 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*      */                                       
/* 2814 */                                       OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2815 */                                       _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 2816 */                                       _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2818 */                                       _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                                       
/* 2820 */                                       _jspx_th_html_005foption_005f7.setValue("2");
/* 2821 */                                       int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 2822 */                                       if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 2823 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                       }
/*      */                                       
/* 2826 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/* 2827 */                                       out.write(32);
/*      */                                       
/* 2829 */                                       OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2830 */                                       _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 2831 */                                       _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2833 */                                       _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */                                       
/* 2835 */                                       _jspx_th_html_005foption_005f8.setValue("9");
/* 2836 */                                       int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 2837 */                                       if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 2838 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                       }
/*      */                                       
/* 2841 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/* 2842 */                                       out.write("\n                    ");
/*      */                                       
/* 2844 */                                       OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2845 */                                       _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 2846 */                                       _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2848 */                                       _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */                                       
/* 2850 */                                       _jspx_th_html_005foption_005f9.setValue("8");
/* 2851 */                                       int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 2852 */                                       if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 2853 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                       }
/*      */                                       
/* 2856 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/*      */                                       
/* 2858 */                                       OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2859 */                                       _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 2860 */                                       _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2862 */                                       _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                                       
/* 2864 */                                       _jspx_th_html_005foption_005f10.setValue("5");
/* 2865 */                                       int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 2866 */                                       if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 2867 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                       }
/*      */                                       
/* 2870 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/* 2871 */                                       out.write("\n                  ");
/* 2872 */                                       int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 2873 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2876 */                                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2877 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2880 */                                   if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 2881 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                   }
/*      */                                   
/* 2884 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 2885 */                                   out.write(" <input type=hidden name=\"resourcename\" value=\"");
/* 2886 */                                   out.print(request.getParameter("resourcename"));
/* 2887 */                                   out.write("\" >\n                  <input type=hidden name=\"resourceid\" value=\"");
/* 2888 */                                   out.print(request.getParameter("resourceid"));
/* 2889 */                                   out.write("\" >\n                 <input type=hidden name=\"defaultView\" value=\"");
/* 2890 */                                   out.print(isdefaultview);
/* 2891 */                                   out.write("\" >\n            </td></tr></table>\n\t\t");
/* 2892 */                                   if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2894 */                                   out.write("\n            ");
/* 2895 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2896 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2900 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2901 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 2904 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2905 */                               out.write(" <br></td>\n            </tr>\n             <tr>\n          <td  align='left' > ");
/*      */                               
/* 2907 */                               FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2908 */                               _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 2909 */                               _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2911 */                               _jspx_th_html_005fform_005f1.setAction("/showBussiness.do?method=generateTroubleTicket&value=4");
/*      */                               
/* 2913 */                               _jspx_th_html_005fform_005f1.setStyle("Display:inline");
/* 2914 */                               int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 2915 */                               if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                                 for (;;) {
/* 2917 */                                   out.write("\n            <table width=\"100%\" height=\"30%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tr>\n                <td height=\"22\" class=\"leftlinksheading\">");
/* 2918 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.customtime.text"));
/* 2919 */                                   out.write("</td>\n              </tr>\n              <tr >\n                <td width=\"50%\" height=\"24\"  class=\"columnheading\" > ");
/* 2920 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.starttime.text"));
/* 2921 */                                   out.write("\n                </td>\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td height=\"38\">&nbsp;");
/* 2922 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2924 */                                   out.write("\n                  <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/* 2925 */                                   out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 2926 */                                   out.write("\"></a>\n                  <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT></td>\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td height=\"24\" class=\"columnheading\" width=\"50%\">");
/* 2927 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.endtime.text"));
/* 2928 */                                   out.write("</td>\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td height=\"24\" >&nbsp;");
/* 2929 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2931 */                                   out.write("\n                 <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=endTrigger title=\"");
/* 2932 */                                   out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 2933 */                                   out.write("\"></a>\n                  <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"end\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT> </td>\n              </tr>\n               <input type=hidden name=\"resourcename\" value=\"");
/* 2934 */                                   out.print(request.getParameter("resourcename"));
/* 2935 */                                   out.write("\" >\n                  <input type=hidden name=\"resourceid\" value=\"");
/* 2936 */                                   out.print(request.getParameter("resourceid"));
/* 2937 */                                   out.write("\" >\n              <tr class=\"whitegrayborder\">\n                <td height=\"24\" align=\"center\" class=\"tablebottom\"> <input type=\"submit\" name=\"show\" value=\"");
/* 2938 */                                   out.print(FormatUtil.getString("am.webclient.historydata.showreport.text"));
/* 2939 */                                   out.write("\" class=\"buttons btn_highlt\" onclick=\"return fnCheckCustomTime(this.form)\">\n                <input type=hidden name=\"defaultView\" value=\"");
/* 2940 */                                   out.print(isdefaultview);
/* 2941 */                                   out.write("\" >\n                </td>\n              </tr>\n            </table>\n             ");
/* 2942 */                                   if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2944 */                                   out.write("\n            ");
/* 2945 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 2946 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2950 */                               if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 2951 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                               }
/*      */                               
/* 2954 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 2955 */                               out.write(" </td>\n        </tr>\n      </table></td>\n      </tr>\n<tr>\n<td width=\"100%\" colspan='2'>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align='center'>\n<tr><td colspan='2'>&nbsp;</td></tr>\n");
/*      */                               
/* 2957 */                               ArrayList value = (ArrayList)request.getAttribute("event");
/* 2958 */                               Hashtable hsm = (Hashtable)request.getAttribute("map");
/* 2959 */                               String mgcritical = null;
/* 2960 */                               for (int k = 0; k < list.size(); k++)
/*      */                               {
/* 2962 */                                 ArrayList list1 = (ArrayList)list.get(k);
/* 2963 */                                 String name1 = (String)list1.get(1);
/* 2964 */                                 mgcritical = hsm.get(name1).toString();
/*      */                               }
/*      */                               
/* 2967 */                               out.write("\n        <tr>\n<td width=\"100%\" colspan='2' align='left'>\n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\" align=\"left\">\n    <tr>\n\t      <td  width=\"100%\" class=\"tableheadingbborder\" colspan=\"10\" >");
/* 2968 */                               out.print(FormatUtil.getString("am.webclient.manager.ttslatab.tableheading.text"));
/* 2969 */                               out.write("\n\t      </td>\n    </tr>\n   </table>\n   </td>\n   </tr>\n   <tr>\n   <td><form action='/showReports.do'>\n   \t\t\t<input type=\"hidden\" name=\"actionMethod\" value=\"\">\n            <input type=\"hidden\" name=\"method\" value=\"\">\n            <input type=\"hidden\" name=\"haid\" value=\"\">\n    <table id=\"eventTable\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" align=\"left\">\n    <tr >\n      <td width=\"15%\" height=\"28\"  class=\"columnheading\">");
/* 2970 */                               out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.name"));
/* 2971 */                               out.write("</td>\n      <td width=\"9%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2972 */                               out.print(FormatUtil.getString("am.webclient.manager.slatab.slaname.text"));
/* 2973 */                               out.write("</td>\n      <td width=\"9%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2974 */                               out.print(FormatUtil.getString("am.webclient.period.today"));
/* 2975 */                               out.write("</td>\n      <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2976 */                               out.print(FormatUtil.getString("am.webclient.period.yesterday"));
/* 2977 */                               out.write("</td>\n      <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2978 */                               out.print(FormatUtil.getString("am.webclient.period.thisweek"));
/* 2979 */                               out.write("</td>\n      <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2980 */                               out.print(FormatUtil.getString("am.webclient.period.lastweek"));
/* 2981 */                               out.write("</td>\n      <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2982 */                               out.print(FormatUtil.getString("am.webclient.period.thismonth"));
/* 2983 */                               out.write("</td>\n      <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2984 */                               out.print(FormatUtil.getString("am.webclient.period.lastmonth"));
/* 2985 */                               out.write("</td>\n      <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2986 */                               out.print(FormatUtil.getString("am.webclient.period.thisquarter"));
/* 2987 */                               out.write("</td>\n      <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2988 */                               out.print(FormatUtil.getString("am.webclient.period.thisyear"));
/* 2989 */                               out.write("</td>\n    </tr>\n ");
/*      */                               
/* 2991 */                               for (int h = 0; h < value.size(); h++)
/*      */                               {
/* 2993 */                                 String bgcolor = null;
/* 2994 */                                 if (h % 2 == 0)
/*      */                                 {
/* 2996 */                                   bgcolor = "class=\"whitegrayborder\"";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3000 */                                   bgcolor = "class=\"yellowgrayborder\"";
/*      */                                 }
/* 3002 */                                 ArrayList event = (ArrayList)value.get(h);
/* 3003 */                                 String resid = (String)event.get(0);
/* 3004 */                                 String resname = (String)event.get(1);
/* 3005 */                                 String slid = (String)event.get(2);
/* 3006 */                                 String slaname = (String)event.get(3);
/* 3007 */                                 String slaop = (String)event.get(4);
/* 3008 */                                 String slaval = (String)event.get(5);
/*      */                                 
/* 3010 */                                 out.write("\n         <tr ");
/* 3011 */                                 out.print(bgcolor);
/* 3012 */                                 out.write(" class=\"alarm-links \" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\" >\n\t\t ");
/*      */                                 
/* 3014 */                                 String title = "";
/* 3015 */                                 if (EnterpriseUtil.isAdminServer())
/*      */                                 {
/* 3017 */                                   if (CommDBUtil.getManagedServerNameWithPort(resid).equals("NA"))
/*      */                                   {
/* 3019 */                                     title = FormatUtil.getString("am.webclient.common.notapplicable.text");
/* 3020 */                                     resname = resname + "_" + title;
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3024 */                                     title = FormatUtil.getString("am.webclient.managedserver.tooltip.monitorgroupname", new String[] { CommDBUtil.getManagedServerNameWithPort(resid) });
/* 3025 */                                     resname = resname + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 3029 */                                 out.write("\n\n      <td title=\"");
/* 3030 */                                 out.print(title);
/* 3031 */                                 out.write("\"  class=\"yellowgrayborder\">");
/* 3032 */                                 out.print(resname);
/* 3033 */                                 out.write("</td>\n      ");
/* 3034 */                                 if (!slaname.equals("not set"))
/*      */                                 {
/* 3036 */                                   out.write("\n      <td title='");
/* 3037 */                                   out.print(slaop);
/* 3038 */                                   out.write("&nbsp;");
/* 3039 */                                   out.print(slaval);
/* 3040 */                                   out.write("' class=\"yellowgrayborder\"><a href=\"javascript:void(0)\" onClick=\"javascript:window.open('../jsp/Popup_SLA.jsp?sid=");
/* 3041 */                                   out.print(slid);
/* 3042 */                                   out.write("','','resizable=yes,width=390,height=170')\" class=\"alarm-links\">");
/* 3043 */                                   out.print(FormatUtil.getString(slaname));
/* 3044 */                                   out.write("</a></td>\n      ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3048 */                                   out.write("\n          <td title='");
/* 3049 */                                   out.print(slaop);
/* 3050 */                                   out.write("&nbsp;");
/* 3051 */                                   out.print(slaval);
/* 3052 */                                   out.write("'  class=\"yellowgrayborder\">");
/* 3053 */                                   out.print(FormatUtil.getString(slaname));
/* 3054 */                                   out.write("</td>\n         ");
/*      */                                 }
/* 3056 */                                 out.write("\n      ");
/*      */                                 
/* 3058 */                                 ArrayList a1 = new ArrayList();
/*      */                                 
/* 3060 */                                 int z = 7; for (int u = 0; z <= 14; u++)
/*      */                                 {
/* 3062 */                                   ArrayList temp = (ArrayList)event.get(z);
/* 3063 */                                   int[] q = { 0, 3, 6, 12, 7, 11, 9, 8 };
/* 3064 */                                   String critevent = (String)temp.get(1);
/* 3065 */                                   String sla = (String)temp.get(3);
/*      */                                   
/* 3067 */                                   int f = q[u];
/*      */                                   
/* 3069 */                                   if (sla.equalsIgnoreCase("PASS"))
/*      */                                   {
/*      */ 
/* 3072 */                                     out.write("\n                <td align=\"left\" class=\"yellowgrayborder\"><a href='javascript:void(0)' onclick=\"javascript:fnshowData('");
/* 3073 */                                     out.print(resid);
/* 3074 */                                     out.write(39);
/* 3075 */                                     out.write(44);
/* 3076 */                                     out.write(39);
/* 3077 */                                     out.print(f);
/* 3078 */                                     out.write("')\" class=\"alarm-links\">");
/* 3079 */                                     out.print(critevent);
/* 3080 */                                     out.write("</a></td>\n                ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3085 */                                     out.write("\n                           <td  align=\"left\"  class=\"yellowgrayborder \"><table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"StaticLinks-Anomaly\">\n\n\n                           <tr ><td align=\"left\"   class=\"StaticLinks-Anomaly \"><a href='javascript:void(0)' onclick=\"javascript:fnshowData('");
/* 3086 */                                     out.print(resid);
/* 3087 */                                     out.write(39);
/* 3088 */                                     out.write(44);
/* 3089 */                                     out.write(39);
/* 3090 */                                     out.print(f);
/* 3091 */                                     out.write("')\" class=\"StaticLinks-Anomaly\" style=\"color:#595959; background-color:#FFAAAA\">");
/* 3092 */                                     out.print(critevent);
/* 3093 */                                     out.write("</a></td></tr></table></td>\n                        ");
/*      */                                   }
/* 3060 */                                   z++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                             }
/*      */                             catch (Exception e)
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3097 */                               e.printStackTrace();
/*      */                             }
/* 3099 */                             out.write("\n </tr>\n </table></form>\n  </td>\n    </tr>\n  </table>\n  </td>\n  </tr>\n  </table>\n<script>\n  reportForm1 = document.forms[0];\n  reportForm2 = document.forms[1];\n  showReportsForm = document.forms[2];\n  evTblForm = document.forms[3];\n</script>\n\n");
/* 3100 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3101 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3104 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3105 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3108 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3109 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 3112 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3113 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 3115 */                         out.write(10);
/* 3116 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3117 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3121 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3122 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 3125 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3126 */                       out.write("\n</body>\n");
/*      */                     }
/* 3128 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3129 */         out = _jspx_out;
/* 3130 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3131 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3132 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3135 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3141 */     PageContext pageContext = _jspx_page_context;
/* 3142 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3144 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3145 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3146 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3148 */     _jspx_th_c_005fif_005f0.setTest("${PRINTER_FRIENDLY != null && PRINTER_FRIENDLY == 'true'}");
/* 3149 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3150 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3152 */         out.write("\n                 <input type='hidden' name='PRINTER_FRIENDLY' value='true'>\n\t\t");
/* 3153 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3154 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3158 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3159 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3160 */       return true;
/*      */     }
/* 3162 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3168 */     PageContext pageContext = _jspx_page_context;
/* 3169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3171 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3172 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3173 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3175 */     _jspx_th_html_005ftext_005f0.setSize("15");
/*      */     
/* 3177 */     _jspx_th_html_005ftext_005f0.setProperty("startDate");
/*      */     
/* 3179 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/* 3181 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/* 3183 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetStartTime(this.value)");
/* 3184 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3185 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3186 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3187 */       return true;
/*      */     }
/* 3189 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3195 */     PageContext pageContext = _jspx_page_context;
/* 3196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3198 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3199 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3200 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3202 */     _jspx_th_html_005ftext_005f1.setProperty("endDate");
/*      */     
/* 3204 */     _jspx_th_html_005ftext_005f1.setSize("15");
/*      */     
/* 3206 */     _jspx_th_html_005ftext_005f1.setStyleId("end");
/*      */     
/* 3208 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 3210 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetEndTime(this.value)");
/* 3211 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3212 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3213 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3214 */       return true;
/*      */     }
/* 3216 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3217 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3222 */     PageContext pageContext = _jspx_page_context;
/* 3223 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3225 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3226 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3227 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3229 */     _jspx_th_c_005fif_005f1.setTest("${PRINTER_FRIENDLY != null && PRINTER_FRIENDLY == 'true'}");
/* 3230 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3231 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3233 */         out.write("\n                 <input type='hidden' name='PRINTER_FRIENDLY' value='true'>\n\t\t");
/* 3234 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3235 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3239 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3240 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3241 */       return true;
/*      */     }
/* 3243 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3249 */     PageContext pageContext = _jspx_page_context;
/* 3250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3252 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3253 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3254 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3256 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 3258 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 3259 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3260 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3261 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3262 */       return true;
/*      */     }
/* 3264 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3265 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\BAEventReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */