/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
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
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ExchangeLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   57 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   60 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   61 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   62 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   69 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   74 */     ArrayList list = null;
/*   75 */     StringBuffer sbf = new StringBuffer();
/*   76 */     ManagedApplication mo = new ManagedApplication();
/*   77 */     if (distinct)
/*      */     {
/*   79 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   83 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   86 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   88 */       ArrayList row = (ArrayList)list.get(i);
/*   89 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   90 */       if (distinct) {
/*   91 */         sbf.append(row.get(0));
/*      */       } else
/*   93 */         sbf.append(row.get(1));
/*   94 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   97 */     return sbf.toString(); }
/*      */   
/*   99 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  102 */     if (severity == null)
/*      */     {
/*  104 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  106 */     if (severity.equals("5"))
/*      */     {
/*  108 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  110 */     if (severity.equals("1"))
/*      */     {
/*  112 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  117 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  124 */     if (severity == null)
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  128 */     if (severity.equals("1"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  132 */     if (severity.equals("4"))
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  136 */     if (severity.equals("5"))
/*      */     {
/*  138 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  143 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  149 */     if (severity == null)
/*      */     {
/*  151 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  153 */     if (severity.equals("5"))
/*      */     {
/*  155 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  157 */     if (severity.equals("1"))
/*      */     {
/*  159 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  163 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  169 */     if (severity == null)
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  173 */     if (severity.equals("1"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  177 */     if (severity.equals("4"))
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  181 */     if (severity.equals("5"))
/*      */     {
/*  183 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  187 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  193 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  199 */     if (severity == 5)
/*      */     {
/*  201 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  203 */     if (severity == 1)
/*      */     {
/*  205 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  210 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  216 */     if (severity == null)
/*      */     {
/*  218 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  220 */     if (severity.equals("5"))
/*      */     {
/*  222 */       if (isAvailability) {
/*  223 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  226 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  229 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  231 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  233 */     if (severity.equals("1"))
/*      */     {
/*  235 */       if (isAvailability) {
/*  236 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  239 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  246 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  253 */     if (severity == null)
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  257 */     if (severity.equals("5"))
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  261 */     if (severity.equals("4"))
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  265 */     if (severity.equals("1"))
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  272 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  278 */     if (severity == null)
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("5"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  286 */     if (severity.equals("4"))
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  290 */     if (severity.equals("1"))
/*      */     {
/*  292 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  297 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  304 */     if (severity == null)
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  308 */     if (severity.equals("5"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  312 */     if (severity.equals("4"))
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  316 */     if (severity.equals("1"))
/*      */     {
/*  318 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  323 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  331 */     StringBuffer out = new StringBuffer();
/*  332 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  333 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  334 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  335 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  336 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  337 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  338 */     out.append("</tr>");
/*  339 */     out.append("</form></table>");
/*  340 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  347 */     if (val == null)
/*      */     {
/*  349 */       return "-";
/*      */     }
/*      */     
/*  352 */     String ret = FormatUtil.formatNumber(val);
/*  353 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  354 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  357 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  361 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  369 */     StringBuffer out = new StringBuffer();
/*  370 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  371 */     out.append("<tr>");
/*  372 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  374 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  376 */     out.append("</tr>");
/*  377 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  381 */       if (j % 2 == 0)
/*      */       {
/*  383 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  387 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  390 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  392 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  395 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  399 */       out.append("</tr>");
/*      */     }
/*  401 */     out.append("</table>");
/*  402 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  403 */     out.append("<tr>");
/*  404 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  405 */     out.append("</tr>");
/*  406 */     out.append("</table>");
/*  407 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  413 */     StringBuffer out = new StringBuffer();
/*  414 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  415 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  416 */     out.append("<tr>");
/*  417 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  418 */     out.append("<tr>");
/*  419 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  420 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  421 */     out.append("</tr>");
/*  422 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  425 */       out.append("<tr>");
/*  426 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  427 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  428 */       out.append("</tr>");
/*      */     }
/*      */     
/*  431 */     out.append("</table>");
/*  432 */     out.append("</table>");
/*  433 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  438 */     if (severity.equals("0"))
/*      */     {
/*  440 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  444 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  451 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  464 */     StringBuffer out = new StringBuffer();
/*  465 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  466 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  468 */       out.append("<tr>");
/*  469 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  470 */       out.append("</tr>");
/*      */       
/*      */ 
/*  473 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  475 */         String borderclass = "";
/*      */         
/*      */ 
/*  478 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  480 */         out.append("<tr>");
/*      */         
/*  482 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  483 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  484 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  490 */     out.append("</table><br>");
/*  491 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  492 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  494 */       List sLinks = secondLevelOfLinks[0];
/*  495 */       List sText = secondLevelOfLinks[1];
/*  496 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  499 */         out.append("<tr>");
/*  500 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  501 */         out.append("</tr>");
/*  502 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  504 */           String borderclass = "";
/*      */           
/*      */ 
/*  507 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  509 */           out.append("<tr>");
/*      */           
/*  511 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  512 */           if (sLinks.get(i).toString().length() == 0) {
/*  513 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  516 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  518 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  522 */     out.append("</table>");
/*  523 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  530 */     StringBuffer out = new StringBuffer();
/*  531 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  532 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  534 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  536 */         out.append("<tr>");
/*  537 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  538 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  542 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  544 */           String borderclass = "";
/*      */           
/*      */ 
/*  547 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  549 */           out.append("<tr>");
/*      */           
/*  551 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  552 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  553 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  556 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  559 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  564 */     out.append("</table><br>");
/*  565 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  566 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  568 */       List sLinks = secondLevelOfLinks[0];
/*  569 */       List sText = secondLevelOfLinks[1];
/*  570 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  573 */         out.append("<tr>");
/*  574 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  575 */         out.append("</tr>");
/*  576 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  578 */           String borderclass = "";
/*      */           
/*      */ 
/*  581 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  583 */           out.append("<tr>");
/*      */           
/*  585 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  586 */           if (sLinks.get(i).toString().length() == 0) {
/*  587 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  590 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  592 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  596 */     out.append("</table>");
/*  597 */     return out.toString();
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
/*  610 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  622 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  625 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  628 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  631 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  639 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  644 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  649 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  654 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  659 */     if (val != null)
/*      */     {
/*  661 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  665 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  670 */     if (val == null) {
/*  671 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  675 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  680 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  686 */     if (val != null)
/*      */     {
/*  688 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  692 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  698 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  703 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  707 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  712 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  717 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  722 */     String hostaddress = "";
/*  723 */     String ip = request.getHeader("x-forwarded-for");
/*  724 */     if (ip == null)
/*  725 */       ip = request.getRemoteAddr();
/*  726 */     InetAddress add = null;
/*  727 */     if (ip.equals("127.0.0.1")) {
/*  728 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  732 */       add = InetAddress.getByName(ip);
/*      */     }
/*  734 */     hostaddress = add.getHostName();
/*  735 */     if (hostaddress.indexOf('.') != -1) {
/*  736 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  737 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  741 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  746 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  752 */     if (severity == null)
/*      */     {
/*  754 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  756 */     if (severity.equals("5"))
/*      */     {
/*  758 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  760 */     if (severity.equals("1"))
/*      */     {
/*  762 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  767 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  772 */     ResultSet set = null;
/*  773 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  774 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  776 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  777 */       if (set.next()) { String str1;
/*  778 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  779 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  782 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  787 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  790 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  792 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  796 */     StringBuffer rca = new StringBuffer();
/*  797 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  798 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  801 */     int rcalength = key.length();
/*  802 */     String split = "6. ";
/*  803 */     int splitPresent = key.indexOf(split);
/*  804 */     String div1 = "";String div2 = "";
/*  805 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  807 */       if (rcalength > 180) {
/*  808 */         rca.append("<span class=\"rca-critical-text\">");
/*  809 */         getRCATrimmedText(key, rca);
/*  810 */         rca.append("</span>");
/*      */       } else {
/*  812 */         rca.append("<span class=\"rca-critical-text\">");
/*  813 */         rca.append(key);
/*  814 */         rca.append("</span>");
/*      */       }
/*  816 */       return rca.toString();
/*      */     }
/*  818 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  819 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  820 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  821 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  822 */     getRCATrimmedText(div1, rca);
/*  823 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  826 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  827 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  828 */     getRCATrimmedText(div2, rca);
/*  829 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  831 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  836 */     String[] st = msg.split("<br>");
/*  837 */     for (int i = 0; i < st.length; i++) {
/*  838 */       String s = st[i];
/*  839 */       if (s.length() > 180) {
/*  840 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  842 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  846 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  847 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  849 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  853 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  854 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  855 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  858 */       if (key == null) {
/*  859 */         return ret;
/*      */       }
/*      */       
/*  862 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  863 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  866 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  867 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  868 */       set = AMConnectionPool.executeQueryStmt(query);
/*  869 */       if (set.next())
/*      */       {
/*  871 */         String helpLink = set.getString("LINK");
/*  872 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  875 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  881 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  900 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  891 */         if (set != null) {
/*  892 */           AMConnectionPool.closeStatement(set);
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
/*  906 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  907 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  909 */       String entityStr = (String)keys.nextElement();
/*  910 */       String mmessage = temp.getProperty(entityStr);
/*  911 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  912 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  914 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  920 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  921 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  923 */       String entityStr = (String)keys.nextElement();
/*  924 */       String mmessage = temp.getProperty(entityStr);
/*  925 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  926 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  928 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  933 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  943 */     String des = new String();
/*  944 */     while (str.indexOf(find) != -1) {
/*  945 */       des = des + str.substring(0, str.indexOf(find));
/*  946 */       des = des + replace;
/*  947 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  949 */     des = des + str;
/*  950 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  957 */       if (alert == null)
/*      */       {
/*  959 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  961 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  963 */         return "&nbsp;";
/*      */       }
/*      */       
/*  966 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  968 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  971 */       int rcalength = test.length();
/*  972 */       if (rcalength < 300)
/*      */       {
/*  974 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  978 */       StringBuffer out = new StringBuffer();
/*  979 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  980 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  981 */       out.append("</div>");
/*  982 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  983 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  984 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  989 */       ex.printStackTrace();
/*      */     }
/*  991 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  997 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1002 */     ArrayList attribIDs = new ArrayList();
/* 1003 */     ArrayList resIDs = new ArrayList();
/* 1004 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1006 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1008 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1010 */       String resourceid = "";
/* 1011 */       String resourceType = "";
/* 1012 */       if (type == 2) {
/* 1013 */         resourceid = (String)row.get(0);
/* 1014 */         resourceType = (String)row.get(3);
/*      */       }
/* 1016 */       else if (type == 3) {
/* 1017 */         resourceid = (String)row.get(0);
/* 1018 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1021 */         resourceid = (String)row.get(6);
/* 1022 */         resourceType = (String)row.get(7);
/*      */       }
/* 1024 */       resIDs.add(resourceid);
/* 1025 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1026 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1028 */       String healthentity = null;
/* 1029 */       String availentity = null;
/* 1030 */       if (healthid != null) {
/* 1031 */         healthentity = resourceid + "_" + healthid;
/* 1032 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1035 */       if (availid != null) {
/* 1036 */         availentity = resourceid + "_" + availid;
/* 1037 */         entitylist.add(availentity);
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
/* 1051 */     Properties alert = getStatus(entitylist);
/* 1052 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1057 */     int size = monitorList.size();
/*      */     
/* 1059 */     String[] severity = new String[size];
/*      */     
/* 1061 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1063 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1064 */       String resourceName1 = (String)row1.get(7);
/* 1065 */       String resourceid1 = (String)row1.get(6);
/* 1066 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1067 */       if (severity[j] == null)
/*      */       {
/* 1069 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1073 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1075 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1077 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1080 */         if (sev > 0) {
/* 1081 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1082 */           monitorList.set(k, monitorList.get(j));
/* 1083 */           monitorList.set(j, t);
/* 1084 */           String temp = severity[k];
/* 1085 */           severity[k] = severity[j];
/* 1086 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1092 */     int z = 0;
/* 1093 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1096 */       int i = 0;
/* 1097 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1100 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1104 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1108 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1110 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1113 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1117 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1120 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1121 */       String resourceName1 = (String)row1.get(7);
/* 1122 */       String resourceid1 = (String)row1.get(6);
/* 1123 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1124 */       if (hseverity[j] == null)
/*      */       {
/* 1126 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1131 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1133 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1136 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1139 */         if (hsev > 0) {
/* 1140 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1141 */           monitorList.set(k, monitorList.get(j));
/* 1142 */           monitorList.set(j, t);
/* 1143 */           String temp1 = hseverity[k];
/* 1144 */           hseverity[k] = hseverity[j];
/* 1145 */           hseverity[j] = temp1;
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
/* 1157 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1158 */     boolean forInventory = false;
/* 1159 */     String trdisplay = "none";
/* 1160 */     String plusstyle = "inline";
/* 1161 */     String minusstyle = "none";
/* 1162 */     String haidTopLevel = "";
/* 1163 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1165 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1167 */         haidTopLevel = request.getParameter("haid");
/* 1168 */         forInventory = true;
/* 1169 */         trdisplay = "table-row;";
/* 1170 */         plusstyle = "none";
/* 1171 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1178 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1181 */     ArrayList listtoreturn = new ArrayList();
/* 1182 */     StringBuffer toreturn = new StringBuffer();
/* 1183 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1184 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1185 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1187 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1189 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1190 */       String childresid = (String)singlerow.get(0);
/* 1191 */       String childresname = (String)singlerow.get(1);
/* 1192 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1193 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1194 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1195 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1196 */       String unmanagestatus = (String)singlerow.get(5);
/* 1197 */       String actionstatus = (String)singlerow.get(6);
/* 1198 */       String linkclass = "monitorgp-links";
/* 1199 */       String titleforres = childresname;
/* 1200 */       String titilechildresname = childresname;
/* 1201 */       String childimg = "/images/trcont.png";
/* 1202 */       String flag = "enable";
/* 1203 */       String dcstarted = (String)singlerow.get(8);
/* 1204 */       String configMonitor = "";
/* 1205 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1206 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1208 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1210 */       if (singlerow.get(7) != null)
/*      */       {
/* 1212 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1214 */       String haiGroupType = "0";
/* 1215 */       if ("HAI".equals(childtype))
/*      */       {
/* 1217 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1219 */       childimg = "/images/trend.png";
/* 1220 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1221 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1222 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1224 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1226 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1228 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1229 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1232 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1234 */         linkclass = "disabledtext";
/* 1235 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1237 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1238 */       String availmouseover = "";
/* 1239 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1241 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1243 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1244 */       String healthmouseover = "";
/* 1245 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1247 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1250 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1251 */       int spacing = 0;
/* 1252 */       if (level >= 1)
/*      */       {
/* 1254 */         spacing = 40 * level;
/*      */       }
/* 1256 */       if (childtype.equals("HAI"))
/*      */       {
/* 1258 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1259 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1260 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1262 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1263 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1264 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1265 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1266 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1267 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1268 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1269 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1270 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1271 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1272 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1274 */         if (!forInventory)
/*      */         {
/* 1276 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1279 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1281 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1283 */           actions = editlink + actions;
/*      */         }
/* 1285 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1287 */           actions = actions + associatelink;
/*      */         }
/* 1289 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1290 */         String arrowimg = "";
/* 1291 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1293 */           actions = "";
/* 1294 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1295 */           checkbox = "";
/* 1296 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1298 */         if (isIt360)
/*      */         {
/* 1300 */           actionimg = "";
/* 1301 */           actions = "";
/* 1302 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1303 */           checkbox = "";
/*      */         }
/*      */         
/* 1306 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1308 */           actions = "";
/*      */         }
/* 1310 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1312 */           checkbox = "";
/*      */         }
/*      */         
/* 1315 */         String resourcelink = "";
/*      */         
/* 1317 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1319 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1323 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1326 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1332 */         if (!isIt360)
/*      */         {
/* 1334 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1338 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1341 */         toreturn.append("</tr>");
/* 1342 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1344 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1345 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1349 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1350 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1353 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1357 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1359 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1361 */             toreturn.append(assocMessage);
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1364 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1365 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1371 */         String resourcelink = null;
/* 1372 */         boolean hideEditLink = false;
/* 1373 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1375 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1376 */           hideEditLink = true;
/* 1377 */           if (isIt360)
/*      */           {
/* 1379 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1383 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1385 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1387 */           hideEditLink = true;
/* 1388 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1389 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1394 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1397 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1398 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1399 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1400 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1401 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1402 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1403 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1404 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1405 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1406 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1407 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1408 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1409 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1411 */         if (hideEditLink)
/*      */         {
/* 1413 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1415 */         if (!forInventory)
/*      */         {
/* 1417 */           removefromgroup = "";
/*      */         }
/* 1419 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1420 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1421 */           actions = actions + configcustomfields;
/*      */         }
/* 1423 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1425 */           actions = editlink + actions;
/*      */         }
/* 1427 */         String managedLink = "";
/* 1428 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1430 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1431 */           actions = "";
/* 1432 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1433 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1436 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1438 */           checkbox = "";
/*      */         }
/*      */         
/* 1441 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1443 */           actions = "";
/*      */         }
/* 1445 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1448 */         if (isIt360)
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1456 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1457 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1458 */         if (!isIt360)
/*      */         {
/* 1460 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1464 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1466 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1469 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1476 */       StringBuilder toreturn = new StringBuilder();
/* 1477 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1478 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1479 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1480 */       String title = "";
/* 1481 */       message = EnterpriseUtil.decodeString(message);
/* 1482 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1483 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1484 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1486 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1488 */       else if ("5".equals(severity))
/*      */       {
/* 1490 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1494 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1496 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1497 */       toreturn.append(v);
/*      */       
/* 1499 */       toreturn.append(link);
/* 1500 */       if (severity == null)
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1504 */       else if (severity.equals("5"))
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       else if (severity.equals("4"))
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1512 */       else if (severity.equals("1"))
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1519 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1521 */       toreturn.append("</a>");
/* 1522 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1526 */       ex.printStackTrace();
/*      */     }
/* 1528 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1535 */       StringBuilder toreturn = new StringBuilder();
/* 1536 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1537 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1538 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1539 */       if (message == null)
/*      */       {
/* 1541 */         message = "";
/*      */       }
/*      */       
/* 1544 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1545 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1547 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1548 */       toreturn.append(v);
/*      */       
/* 1550 */       toreturn.append(link);
/*      */       
/* 1552 */       if (severity == null)
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1556 */       else if (severity.equals("5"))
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1560 */       else if (severity.equals("1"))
/*      */       {
/* 1562 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1567 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1569 */       toreturn.append("</a>");
/* 1570 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1576 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1579 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1580 */     if (invokeActions != null) {
/* 1581 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1582 */       while (iterator.hasNext()) {
/* 1583 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1584 */         if (actionmap.containsKey(actionid)) {
/* 1585 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1590 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1594 */     String actionLink = "";
/* 1595 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1596 */     String query = "";
/* 1597 */     ResultSet rs = null;
/* 1598 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1599 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1600 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1601 */       actionLink = "method=" + methodName;
/*      */     }
/* 1603 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1604 */       actionLink = methodName;
/*      */     }
/* 1606 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1607 */     Iterator itr = methodarglist.iterator();
/* 1608 */     boolean isfirstparam = true;
/* 1609 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1610 */     while (itr.hasNext()) {
/* 1611 */       HashMap argmap = (HashMap)itr.next();
/* 1612 */       String argtype = (String)argmap.get("TYPE");
/* 1613 */       String argname = (String)argmap.get("IDENTITY");
/* 1614 */       String paramname = (String)argmap.get("PARAMETER");
/* 1615 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1616 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1617 */         isfirstparam = false;
/* 1618 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1620 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1624 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1628 */         actionLink = actionLink + "&";
/*      */       }
/* 1630 */       String paramValue = null;
/* 1631 */       String tempargname = argname;
/* 1632 */       if (commonValues.getProperty(tempargname) != null) {
/* 1633 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1636 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1637 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1638 */           if (dbType.equals("mysql")) {
/* 1639 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1642 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1644 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1646 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1647 */             if (rs.next()) {
/* 1648 */               paramValue = rs.getString("VALUE");
/* 1649 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1653 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1657 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1660 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1665 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1666 */           paramValue = rowId;
/*      */         }
/* 1668 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1669 */           paramValue = managedObjectName;
/*      */         }
/* 1671 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1672 */           paramValue = resID;
/*      */         }
/* 1674 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1675 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1678 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1680 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1681 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1682 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1684 */     return actionLink;
/*      */   }
/*      */   
/* 1687 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1688 */     String dependentAttribute = null;
/* 1689 */     String align = "left";
/*      */     
/* 1691 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1692 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1693 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1694 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1695 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1696 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1697 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1698 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1699 */       align = "center";
/*      */     }
/*      */     
/* 1702 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1703 */     String actualdata = "";
/*      */     
/* 1705 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1706 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1707 */         actualdata = availValue;
/*      */       }
/* 1709 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1710 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1714 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1715 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1718 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1724 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1725 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1726 */       toreturn.append("<table>");
/* 1727 */       toreturn.append("<tr>");
/* 1728 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1729 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1730 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1731 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1732 */         String toolTip = "";
/* 1733 */         String hideClass = "";
/* 1734 */         String textStyle = "";
/* 1735 */         boolean isreferenced = true;
/* 1736 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1737 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1738 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1739 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1741 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1742 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1743 */           while (valueList.hasMoreTokens()) {
/* 1744 */             String dependentVal = valueList.nextToken();
/* 1745 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1746 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1747 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1749 */               toolTip = "";
/* 1750 */               hideClass = "";
/* 1751 */               isreferenced = false;
/* 1752 */               textStyle = "disabledtext";
/* 1753 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1757 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1758 */           toolTip = "";
/* 1759 */           hideClass = "";
/* 1760 */           isreferenced = false;
/* 1761 */           textStyle = "disabledtext";
/* 1762 */           if (dependentImageMap != null) {
/* 1763 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1764 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1767 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1771 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1772 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1773 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1774 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1775 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1776 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1778 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1779 */           if (isreferenced) {
/* 1780 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1784 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1785 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1786 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1787 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1788 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1789 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1791 */           toreturn.append("</span>");
/* 1792 */           toreturn.append("</a>");
/* 1793 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1796 */       toreturn.append("</tr>");
/* 1797 */       toreturn.append("</table>");
/* 1798 */       toreturn.append("</td>");
/*      */     } else {
/* 1800 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1803 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1807 */     String colTime = null;
/* 1808 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1809 */     if ((rows != null) && (rows.size() > 0)) {
/* 1810 */       Iterator<String> itr = rows.iterator();
/* 1811 */       String maxColQuery = "";
/* 1812 */       for (;;) { if (itr.hasNext()) {
/* 1813 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1814 */           ResultSet maxCol = null;
/*      */           try {
/* 1816 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1817 */             while (maxCol.next()) {
/* 1818 */               if (colTime == null) {
/* 1819 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1822 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1831 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1833 */               if (maxCol != null)
/* 1834 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1836 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1831 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1833 */               if (maxCol != null)
/* 1834 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1836 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1841 */     return colTime;
/*      */   }
/*      */   
/* 1844 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1845 */     tablename = null;
/* 1846 */     ResultSet rsTable = null;
/* 1847 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1849 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1850 */       while (rsTable.next()) {
/* 1851 */         tablename = rsTable.getString("DATATABLE");
/* 1852 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1853 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1866 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1857 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1860 */         if (rsTable != null)
/* 1861 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1863 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1869 */     String argsList = "";
/* 1870 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1872 */       if (showArgsMap.get(row) != null) {
/* 1873 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1874 */         if (showArgslist != null) {
/* 1875 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1876 */             if (argsList.trim().equals("")) {
/* 1877 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1880 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1887 */       e.printStackTrace();
/* 1888 */       return "";
/*      */     }
/* 1890 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1895 */     String argsList = "";
/* 1896 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1899 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1901 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1902 */         if (hideArgsList != null)
/*      */         {
/* 1904 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1906 */             if (argsList.trim().equals(""))
/*      */             {
/* 1908 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1912 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1920 */       ex.printStackTrace();
/*      */     }
/* 1922 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1926 */     StringBuilder toreturn = new StringBuilder();
/* 1927 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1934 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1935 */       Iterator itr = tActionList.iterator();
/* 1936 */       while (itr.hasNext()) {
/* 1937 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1938 */         String confirmmsg = "";
/* 1939 */         String link = "";
/* 1940 */         String isJSP = "NO";
/* 1941 */         HashMap tactionMap = (HashMap)itr.next();
/* 1942 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1943 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1944 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1945 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1946 */           (actionmap.containsKey(actionId))) {
/* 1947 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1948 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1949 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1950 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1951 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1953 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1959 */           if (isTableAction) {
/* 1960 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1963 */             tableName = "Link";
/* 1964 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1965 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1966 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1967 */             toreturn.append("</a></td>");
/*      */           }
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1978 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1984 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1986 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1987 */       Properties prop = (Properties)node.getUserObject();
/* 1988 */       String mgID = prop.getProperty("label");
/* 1989 */       String mgName = prop.getProperty("value");
/* 1990 */       String isParent = prop.getProperty("isParent");
/* 1991 */       int mgIDint = Integer.parseInt(mgID);
/* 1992 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1994 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1996 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1997 */       if (node.getChildCount() > 0)
/*      */       {
/* 1999 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2001 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2003 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2005 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2009 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2014 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2016 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2018 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2020 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2024 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2027 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2028 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2030 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2034 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2036 */       if (node.getChildCount() > 0)
/*      */       {
/* 2038 */         builder.append("<UL>");
/* 2039 */         printMGTree(node, builder);
/* 2040 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2045 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2046 */     StringBuffer toReturn = new StringBuffer();
/* 2047 */     String table = "-";
/*      */     try {
/* 2049 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2050 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2051 */       float total = 0.0F;
/* 2052 */       while (it.hasNext()) {
/* 2053 */         String attName = (String)it.next();
/* 2054 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2055 */         boolean roundOffData = false;
/* 2056 */         if ((data != null) && (!data.equals(""))) {
/* 2057 */           if (data.indexOf(",") != -1) {
/* 2058 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2061 */             float value = Float.parseFloat(data);
/* 2062 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2065 */             total += value;
/* 2066 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2069 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2074 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2075 */       while (attVsWidthList.hasNext()) {
/* 2076 */         String attName = (String)attVsWidthList.next();
/* 2077 */         String data = (String)attVsWidthProps.get(attName);
/* 2078 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2079 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2080 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2081 */         String className = (String)graphDetails.get("ClassName");
/* 2082 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2083 */         if (percentage < 1.0F)
/*      */         {
/* 2085 */           data = percentage + "";
/*      */         }
/* 2087 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2089 */       if (toReturn.length() > 0) {
/* 2090 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2094 */       e.printStackTrace();
/*      */     }
/* 2096 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2102 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2103 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2104 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2105 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2106 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2107 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2108 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2109 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2110 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2113 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2114 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2115 */       splitvalues[0] = multiplecondition.toString();
/* 2116 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2119 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2124 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2125 */     if (thresholdType != 3) {
/* 2126 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2127 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2128 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2129 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2130 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2131 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2133 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2134 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2135 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2136 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2137 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2138 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2140 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2141 */     if (updateSelected != null) {
/* 2142 */       updateSelected[0] = "selected";
/*      */     }
/* 2144 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2149 */       StringBuffer toreturn = new StringBuffer("");
/* 2150 */       if (commaSeparatedMsgId != null) {
/* 2151 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2152 */         int count = 0;
/* 2153 */         while (msgids.hasMoreTokens()) {
/* 2154 */           String id = msgids.nextToken();
/* 2155 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2156 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2157 */           count++;
/* 2158 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2159 */             if (toreturn.length() == 0) {
/* 2160 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2162 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2163 */             if (!image.trim().equals("")) {
/* 2164 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2166 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2167 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2170 */         if (toreturn.length() > 0) {
/* 2171 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2175 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2178 */       e.printStackTrace(); }
/* 2179 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2185 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2191 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2192 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2194 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2213 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2217 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2229 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2233 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2250 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2253 */     JspWriter out = null;
/* 2254 */     Object page = this;
/* 2255 */     JspWriter _jspx_out = null;
/* 2256 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2260 */       response.setContentType("text/html;charset=UTF-8");
/* 2261 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2263 */       _jspx_page_context = pageContext;
/* 2264 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2265 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2266 */       session = pageContext.getSession();
/* 2267 */       out = pageContext.getOut();
/* 2268 */       _jspx_out = out;
/*      */       
/* 2270 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2271 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2273 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2274 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2277 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2279 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2284 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2285 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2286 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2289 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2290 */         String available = null;
/* 2291 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2292 */         out.write(10);
/*      */         
/* 2294 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2295 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2298 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2300 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2305 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2306 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2307 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2310 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2311 */           String unavailable = null;
/* 2312 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2313 */           out.write(10);
/*      */           
/* 2315 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2316 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2319 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2321 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2326 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2327 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2328 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2331 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2332 */             String unmanaged = null;
/* 2333 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2334 */             out.write(10);
/*      */             
/* 2336 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2337 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2340 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2342 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2347 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2348 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2349 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2352 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2353 */               String scheduled = null;
/* 2354 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2355 */               out.write(10);
/*      */               
/* 2357 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2358 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2361 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2363 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2368 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2369 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2370 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2373 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2374 */                 String critical = null;
/* 2375 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2376 */                 out.write(10);
/*      */                 
/* 2378 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2379 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2382 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2384 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2389 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2390 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2391 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2394 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2395 */                   String clear = null;
/* 2396 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2397 */                   out.write(10);
/*      */                   
/* 2399 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2400 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2403 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2405 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2410 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2411 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2412 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2415 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2416 */                     String warning = null;
/* 2417 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2418 */                     out.write(10);
/* 2419 */                     out.write(10);
/*      */                     
/* 2421 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2422 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2424 */                     out.write(10);
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/* 2427 */                     out.write("\n<script>\n");
/*      */                     
/* 2429 */                     if (request.getParameter("editPage") != null)
/*      */                     {
/* 2431 */                       out.write("\n\ttoggleDiv('edit');\n");
/*      */                     }
/*      */                     
/* 2434 */                     out.write("\n</script>\n");
/*      */                     
/* 2436 */                     String haid = null;
/* 2437 */                     String appname = null;
/* 2438 */                     String network = null;
/* 2439 */                     haid = (String)request.getAttribute("haid");
/* 2440 */                     appname = (String)request.getAttribute("appName");
/* 2441 */                     String resourcename = (String)request.getAttribute("name");
/* 2442 */                     String resourceid = request.getParameter("resourceid");
/* 2443 */                     String configured = (String)request.getAttribute("configured");
/* 2444 */                     String displayname = null;
/* 2445 */                     if (request.getParameter("configure") != null)
/*      */                     {
/* 2447 */                       displayname = (String)request.getAttribute("displayname");
/* 2448 */                       if (displayname == null)
/*      */                       {
/* 2450 */                         displayname = request.getParameter("resourcename");
/*      */                       }
/*      */                     }
/*      */                     else
/*      */                     {
/* 2455 */                       displayname = request.getParameter("resourcename");
/*      */                     }
/* 2457 */                     ArrayList attribIDs = new ArrayList();
/* 2458 */                     ArrayList resIDs = new ArrayList();
/* 2459 */                     attribIDs.add("3201");
/* 2460 */                     attribIDs.add("3202");
/* 2461 */                     resIDs.add(resourceid);
/* 2462 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2463 */                     if (configured.equals("false"))
/*      */                     {
/* 2465 */                       out.write("\n<br>\n    ");
/* 2466 */                       out.write("\n<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2467 */                       out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2468 */                       out.write("</td>\n    <td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/* 2469 */                       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                         return;
/* 2471 */                       out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n  <tr>\n    <td colspan=\"2\" class=\"quicknote\">\n      ");
/* 2472 */                       out.print(FormatUtil.getString("am.webclient.exchange.quicknote"));
/* 2473 */                       out.write("</td>\n  </tr>\n</table>\n\n");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 2478 */                       out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" class=\"leftlinksheading\">");
/* 2479 */                       out.print(FormatUtil.getString("am.webclient.exchange.servertype"));
/* 2480 */                       out.write(" </td>\n  </tr>\n  <tr>\n    <td width=\"87%\" class=\"leftlinkstd\" >\n  ");
/*      */                       
/* 2482 */                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2483 */                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2484 */                       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2485 */                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2486 */                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                         for (;;) {
/* 2488 */                           out.write(10);
/* 2489 */                           out.write(32);
/* 2490 */                           out.write(32);
/*      */                           
/* 2492 */                           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2493 */                           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2494 */                           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2496 */                           _jspx_th_c_005fwhen_005f0.setTest("${(!empty param.reconfigure && param.reconfigure=='true') || param.all=='true' || param.details=='DB'}");
/* 2497 */                           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2498 */                           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                             for (;;) {
/* 2500 */                               out.write("\n    <a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2501 */                               if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2503 */                               out.write("&haid=");
/* 2504 */                               out.print(haid);
/* 2505 */                               out.write("\" class=\"new-left-links\">");
/* 2506 */                               out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2507 */                               out.write("\n      </a></td>\n  ");
/* 2508 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2509 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2513 */                           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2514 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                           }
/*      */                           
/* 2517 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2518 */                           out.write(10);
/* 2519 */                           out.write(32);
/* 2520 */                           out.write(32);
/*      */                           
/* 2522 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2523 */                           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2524 */                           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2525 */                           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2526 */                           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                             for (;;) {
/* 2528 */                               out.write("\n    ");
/* 2529 */                               out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2530 */                               out.write(10);
/* 2531 */                               out.write(32);
/* 2532 */                               out.write(32);
/* 2533 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2534 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2538 */                           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2539 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                           }
/*      */                           
/* 2542 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2543 */                           out.write(10);
/* 2544 */                           out.write(32);
/* 2545 */                           out.write(32);
/* 2546 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2547 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2551 */                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2552 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                       }
/*      */                       
/* 2555 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2556 */                       out.write("\n  </tr>\n  ");
/*      */                       
/* 2558 */                       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2559 */                       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2560 */                       _jspx_th_c_005fif_005f0.setParent(null);
/*      */                       
/* 2562 */                       _jspx_th_c_005fif_005f0.setTest("${!empty ADMIN || !empty DEMO}");
/* 2563 */                       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2564 */                       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                         for (;;) {
/* 2566 */                           out.write(10);
/* 2567 */                           out.write(32);
/* 2568 */                           out.write(32);
/*      */                           
/* 2570 */                           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2571 */                           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2572 */                           _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */                           
/* 2574 */                           _jspx_th_c_005fif_005f1.setTest("${showdata!='1'}");
/* 2575 */                           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2576 */                           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                             for (;;) {
/* 2578 */                               out.write("\n  <tr>\n  <td width=\"87%\" class=\"leftlinkstd\" >\n  ");
/*      */                               
/* 2580 */                               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2581 */                               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2582 */                               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f1);
/* 2583 */                               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2584 */                               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                 for (;;) {
/* 2586 */                                   out.write(10);
/* 2587 */                                   out.write(32);
/* 2588 */                                   out.write(32);
/*      */                                   
/* 2590 */                                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2591 */                                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2592 */                                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 2594 */                                   _jspx_th_c_005fwhen_005f1.setTest("${(!empty param.reconfigure && param.reconfigure=='true') || param.details=='DB'}");
/* 2595 */                                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2596 */                                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                     for (;;) {
/* 2598 */                                       out.write("\n    <a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2599 */                                       if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                                         return;
/* 2601 */                                       out.write("&haid=");
/* 2602 */                                       out.print(haid);
/* 2603 */                                       out.write("&alert=true\" class=\"new-left-links\">");
/* 2604 */                                       out.print(ALERTCONFIG_TEXT);
/* 2605 */                                       out.write("</a>\n  ");
/* 2606 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2607 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2611 */                                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2612 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                   }
/*      */                                   
/* 2615 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2616 */                                   out.write(10);
/* 2617 */                                   out.write(32);
/* 2618 */                                   out.write(32);
/*      */                                   
/* 2620 */                                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2621 */                                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2622 */                                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 2624 */                                   _jspx_th_c_005fwhen_005f2.setTest("${(param.all=='true')}");
/* 2625 */                                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2626 */                                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                     for (;;) {
/* 2628 */                                       out.write(10);
/* 2629 */                                       out.write(9);
/* 2630 */                                       out.write(32);
/* 2631 */                                       out.print(ALERTCONFIG_TEXT);
/* 2632 */                                       out.write(10);
/* 2633 */                                       out.write(32);
/* 2634 */                                       out.write(32);
/* 2635 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2636 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2640 */                                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2641 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                   }
/*      */                                   
/* 2644 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2645 */                                   out.write(10);
/* 2646 */                                   out.write(32);
/* 2647 */                                   out.write(32);
/*      */                                   
/* 2649 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2650 */                                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2651 */                                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2652 */                                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2653 */                                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                     for (;;) {
/* 2655 */                                       out.write("\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2656 */                                       if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                                         return;
/* 2658 */                                       out.write("\" class=\"new-left-links\">");
/* 2659 */                                       out.print(ALERTCONFIG_TEXT);
/* 2660 */                                       out.write("</a>\n  ");
/* 2661 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2662 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2666 */                                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2667 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                   }
/*      */                                   
/* 2670 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2671 */                                   out.write(10);
/* 2672 */                                   out.write(32);
/* 2673 */                                   out.write(32);
/* 2674 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2675 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2679 */                               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2680 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                               }
/*      */                               
/* 2683 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2684 */                               out.write("\n  </td>\n  </tr>\n  ");
/* 2685 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2686 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2690 */                           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2691 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                           }
/*      */                           
/* 2694 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2695 */                           out.write(10);
/* 2696 */                           out.write(32);
/* 2697 */                           out.write(32);
/* 2698 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2699 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2703 */                       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2704 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                       }
/*      */                       
/* 2707 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2708 */                       out.write(10);
/* 2709 */                       out.write(32);
/* 2710 */                       out.write(32);
/*      */                       
/* 2712 */                       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2713 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2714 */                       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                       
/* 2716 */                       _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2717 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2718 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2720 */                           out.write("\n  <tr>\n   <td class=\"leftlinkstd\" > <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 2721 */                           out.print(request.getParameter("resourceid"));
/* 2722 */                           out.write("&type=");
/* 2723 */                           out.print(request.getParameter("type"));
/* 2724 */                           out.write("&moname=");
/* 2725 */                           out.print(request.getParameter("moname"));
/* 2726 */                           out.write("&method=showdetails&resourcename=");
/* 2727 */                           out.print(request.getParameter("resourcename"));
/* 2728 */                           out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n  ");
/* 2729 */                           out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2730 */                           out.write("</a> </td>\n  </tr>\n  ");
/* 2731 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2732 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2736 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2737 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                       }
/*      */                       
/* 2740 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2741 */                       out.write("\n\n  ");
/*      */                       
/* 2743 */                       IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2744 */                       _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2745 */                       _jspx_th_c_005fif_005f2.setParent(null);
/*      */                       
/* 2747 */                       _jspx_th_c_005fif_005f2.setTest("${!empty ADMIN || !empty DEMO}");
/* 2748 */                       int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2749 */                       if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                         for (;;) {
/* 2751 */                           out.write("\n  <tr>\n    <td width=\"87%\" class=\"leftlinkstd\" >\n    ");
/*      */                           
/* 2753 */                           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2754 */                           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2755 */                           _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fif_005f2);
/* 2756 */                           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2757 */                           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                             for (;;) {
/* 2759 */                               out.write(10);
/* 2760 */                               out.write(9);
/*      */                               
/* 2762 */                               WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2763 */                               _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2764 */                               _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                               
/* 2766 */                               _jspx_th_c_005fwhen_005f3.setTest("${param.details=='DB'}");
/* 2767 */                               int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2768 */                               if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                 for (;;) {
/* 2770 */                                   out.write("\n\t  <a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2771 */                                   if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/*      */                                     return;
/* 2773 */                                   out.write("&haid=");
/* 2774 */                                   out.print(haid);
/* 2775 */                                   out.write("\" class=\"new-left-links\">");
/* 2776 */                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2777 */                                   out.write("</a></td>\n\t");
/* 2778 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2779 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2783 */                               if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2784 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                               }
/*      */                               
/* 2787 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2788 */                               out.write(10);
/* 2789 */                               out.write(9);
/*      */                               
/* 2791 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2792 */                               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2793 */                               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2794 */                               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2795 */                               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                 for (;;) {
/* 2797 */                                   out.write("\n    <a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">");
/* 2798 */                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2799 */                                   out.write("</a></td>\n\t");
/* 2800 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2801 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2805 */                               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2806 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                               }
/*      */                               
/* 2809 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2810 */                               out.write(10);
/* 2811 */                               out.write(9);
/* 2812 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2813 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2817 */                           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2818 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                           }
/*      */                           
/* 2821 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2822 */                           out.write("\n  </tr>\n  ");
/* 2823 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2824 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2828 */                       if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2829 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                       }
/*      */                       
/* 2832 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2833 */                       out.write("\n   <script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n  var s = confirm(\"");
/* 2834 */                       out.print(FormatUtil.getString("am.webclient.exchange.deleteexchange"));
/* 2835 */                       out.write("\")\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=deleteMO&type=Exchange-server&select=");
/* 2836 */                       out.print(resourceid);
/* 2837 */                       out.write("\";\n\t }\n\t   function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2838 */                       out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2839 */                       out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2840 */                       if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*      */                         return;
/* 2842 */                       out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t ");
/* 2843 */                       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                         return;
/* 2845 */                       out.write("\n\t\t  var show_msg=\"false\";\n\t      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2846 */                       out.print(request.getParameter("resourceid"));
/* 2847 */                       out.write("; //No i18n\n\t      $.ajax({\n\t        type:'POST', //No i18n\n\t        url:url,\n\t        async:false,\n\t        success: function(data)\n\t        {\n\t          show_msg=data\n\t        }\n\t      });\n\t      if(show_msg.indexOf(\"true\")>-1)\n\t      {\n\t          alert(\"");
/* 2848 */                       out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2849 */                       out.write("\");\n\t\t   \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2850 */                       if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*      */                         return;
/* 2852 */                       out.write("\";\n          }\n       else { \n\t\t    var s = confirm(\"");
/* 2853 */                       out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2854 */                       out.write("\");\n    \t\tif (s){\n   \t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2855 */                       if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*      */                         return;
/* 2857 */                       out.write("\"; //No I18N\n\t\t\t\t}\n\t\t   }\n\t }\n  </script>\n  ");
/*      */                       
/* 2859 */                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2860 */                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2861 */                       _jspx_th_c_005fif_005f3.setParent(null);
/*      */                       
/* 2863 */                       _jspx_th_c_005fif_005f3.setTest("${!empty ADMIN || !empty DEMO}");
/* 2864 */                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2865 */                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                         for (;;) {
/* 2867 */                           out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n  <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 2868 */                           out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2869 */                           out.write("</A></td>\n  \t</td>\n  </tr>\n  ");
/* 2870 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2871 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2875 */                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2876 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                       }
/*      */                       
/* 2879 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2880 */                       out.write(10);
/* 2881 */                       out.write(32);
/* 2882 */                       out.write(32);
/*      */                       
/* 2884 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2885 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2886 */                       _jspx_th_c_005fif_005f4.setParent(null);
/*      */                       
/* 2888 */                       _jspx_th_c_005fif_005f4.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 2889 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2890 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/* 2892 */                           out.write("\n  <tr>\n  ");
/*      */                           
/* 2894 */                           if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                           {
/*      */ 
/* 2897 */                             out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 2898 */                             out.print(FormatUtil.getString("Manage"));
/* 2899 */                             out.write("</A></td>\n    ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 2905 */                             out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 2906 */                             out.print(FormatUtil.getString("UnManage"));
/* 2907 */                             out.write("</A></td>\n    ");
/*      */                           }
/*      */                           
/*      */ 
/* 2911 */                           out.write("\n  </tr>\n  ");
/* 2912 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2913 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2917 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2918 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/* 2921 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2922 */                       out.write(10);
/* 2923 */                       out.write(32);
/* 2924 */                       out.write(32);
/*      */                       
/* 2926 */                       IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2927 */                       _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2928 */                       _jspx_th_c_005fif_005f5.setParent(null);
/*      */                       
/* 2930 */                       _jspx_th_c_005fif_005f5.setTest("${!empty ADMIN || !empty DEMO}");
/* 2931 */                       int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2932 */                       if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                         for (;;) {
/* 2934 */                           out.write("\n  \t    \t <tr>\n  \t    \t <td colspan=\"2\" class=\"leftlinkstd\">\n  \t    \t ");
/* 2935 */                           out.print(FaultUtil.getAlertTemplateURL(resourceid, displayname, "Exchange-server", "Exchange Server"));
/* 2936 */                           out.write("\n  \t    \t </td>\n  \t     \t</tr>\n  ");
/* 2937 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2938 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2942 */                       if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2943 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                       }
/*      */                       
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2947 */                       out.write("\n   ");
/*      */                       
/* 2949 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2950 */                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2951 */                       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                       
/* 2953 */                       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2954 */                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2955 */                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2957 */                           out.write("\n    ");
/*      */                           
/* 2959 */                           String resourceid_poll = request.getParameter("resourceid");
/* 2960 */                           String resourcetype = request.getParameter("type");
/*      */                           
/* 2962 */                           out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 2963 */                           out.print(resourceid_poll);
/* 2964 */                           out.write("&resourcetype=");
/* 2965 */                           out.print(resourcetype);
/* 2966 */                           out.write("\" class=\"new-left-links\"> ");
/* 2967 */                           out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2968 */                           out.write("</a></td>\n    </tr>\n    ");
/* 2969 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2970 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2974 */                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2975 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                       }
/*      */                       
/* 2978 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2979 */                       out.write("\n    ");
/*      */                       
/* 2981 */                       PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2982 */                       _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2983 */                       _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */                       
/* 2985 */                       _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 2986 */                       int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2987 */                       if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                         for (;;) {
/* 2989 */                           out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 2990 */                           out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2991 */                           out.write("</a></td>\n          </td>\n    ");
/* 2992 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2993 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2997 */                       if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2998 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                       }
/*      */                       
/* 3001 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3002 */                       out.write("\n     ");
/* 3003 */                       out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                       
/* 3005 */                       if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                       {
/* 3007 */                         Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3008 */                         String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                         
/* 3010 */                         String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3011 */                         String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3012 */                         if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                         {
/* 3014 */                           if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                           {
/*      */ 
/* 3017 */                             out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3018 */                             out.print(ciInfoUrl);
/* 3019 */                             out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3020 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3021 */                             out.write("</a></td>");
/* 3022 */                             out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3023 */                             out.print(ciRLUrl);
/* 3024 */                             out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3025 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3026 */                             out.write("</a></td>");
/* 3027 */                             out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                           }
/* 3031 */                           else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                           {
/*      */ 
/* 3034 */                             out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3035 */                             out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3036 */                             out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3037 */                             out.print(ciInfoUrl);
/* 3038 */                             out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3039 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3040 */                             out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3041 */                             out.print(ciRLUrl);
/* 3042 */                             out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3043 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3044 */                             out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       
/* 3049 */                       out.write("\n \n \n\n");
/* 3050 */                       out.write("\n  </table>\n  <br>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr >\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3051 */                       out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3052 */                       out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\">\n\t <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3053 */                       out.print(request.getParameter("resourceid"));
/* 3054 */                       out.write("&attributeid=3202')\" class=\"new-left-links\"> ");
/* 3055 */                       out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3056 */                       out.write(" </a>\n\n\t     </td>\n\t\t     <td width=\"51%\"> <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3057 */                       out.print(request.getParameter("resourceid"));
/* 3058 */                       out.write("&attributeid=3202')\" >");
/* 3059 */                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "3202")));
/* 3060 */                       out.write("</a></td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n  <td width=\"49%\">\n  <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3061 */                       out.print((String)request.getAttribute("resourceid"));
/* 3062 */                       out.write("&attributeid=3201')\" class=\"new-left-links\">");
/* 3063 */                       out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 3064 */                       out.write("</a></td>\n  <td width=\"51%\"> <a  href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3065 */                       out.print((String)request.getAttribute("resourceid"));
/* 3066 */                       out.write("&attributeid=3201')\">");
/* 3067 */                       out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "3201")));
/* 3068 */                       out.write("</a></td>\n </tr>\n</table>\n");
/*      */                       
/* 3070 */                       ArrayList menupos = new ArrayList(5);
/* 3071 */                       if (request.isUserInRole("OPERATOR"))
/*      */                       {
/* 3073 */                         menupos.add("169");
/* 3074 */                         menupos.add("190");
/* 3075 */                         menupos.add("212");
/* 3076 */                         menupos.add("232");
/* 3077 */                         menupos.add("148");
/* 3078 */                         menupos.add("252");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/*      */ 
/* 3084 */                         menupos.add("250");
/* 3085 */                         menupos.add("270");
/* 3086 */                         menupos.add("290");
/* 3087 */                         menupos.add("312");
/* 3088 */                         menupos.add("229");
/* 3089 */                         menupos.add("322");
/*      */                       }
/* 3091 */                       pageContext.setAttribute("menupos", menupos);
/*      */                       
/* 3093 */                       out.write(10);
/* 3094 */                       out.write(10);
/*      */                     }
/*      */                     
/*      */ 
/* 3098 */                     out.write("\n<br>\n");
/* 3099 */                     out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                     
/*      */ 
/*      */ 
/* 3103 */                     boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3104 */                     if (EnterpriseUtil.isIt360MSPEdition)
/*      */                     {
/* 3106 */                       showAssociatedBSG = false;
/*      */                       
/*      */ 
/*      */ 
/* 3110 */                       CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3111 */                       CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3112 */                       String loginName = request.getUserPrincipal().getName();
/* 3113 */                       CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                       
/* 3115 */                       if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                       {
/* 3117 */                         showAssociatedBSG = true;
/*      */                       }
/*      */                     }
/* 3120 */                     String monitorType = request.getParameter("type");
/* 3121 */                     ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3122 */                     boolean mon = conf1.isConfMonitor(monitorType);
/* 3123 */                     if (showAssociatedBSG)
/*      */                     {
/* 3125 */                       Hashtable associatedmgs = new Hashtable();
/* 3126 */                       String resId = request.getParameter("resourceid");
/* 3127 */                       request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3128 */                       if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                       {
/* 3130 */                         mon = false;
/*      */                       }
/*      */                       
/* 3133 */                       if (!mon)
/*      */                       {
/* 3135 */                         out.write(10);
/* 3136 */                         out.write(10);
/*      */                         
/* 3138 */                         IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3139 */                         _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3140 */                         _jspx_th_c_005fif_005f6.setParent(null);
/*      */                         
/* 3142 */                         _jspx_th_c_005fif_005f6.setTest("${!empty associatedmgs}");
/* 3143 */                         int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3144 */                         if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                           for (;;) {
/* 3146 */                             out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3147 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3148 */                             out.write("</td>\n        </tr>\n        ");
/*      */                             
/* 3150 */                             ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3151 */                             _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3152 */                             _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                             
/* 3154 */                             _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                             
/* 3156 */                             _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                             
/* 3158 */                             _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3159 */                             int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                             try {
/* 3161 */                               int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3162 */                               if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                 for (;;) {
/* 3164 */                                   out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3165 */                                   if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3223 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3224 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 3167 */                                   out.write("&method=showApplication\" title=\"");
/* 3168 */                                   if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3223 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3224 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 3170 */                                   out.write("\"  class=\"new-left-links\">\n         ");
/* 3171 */                                   if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3223 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3224 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 3173 */                                   out.write("\n    \t");
/* 3174 */                                   out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3175 */                                   out.write("\n         </a></td>\n        <td>");
/*      */                                   
/* 3177 */                                   PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3178 */                                   _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3179 */                                   _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                   
/* 3181 */                                   _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3182 */                                   int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3183 */                                   if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                     for (;;) {
/* 3185 */                                       out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3186 */                                       if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fpresent_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3223 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3224 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/* 3188 */                                       out.write(39);
/* 3189 */                                       out.write(44);
/* 3190 */                                       out.write(39);
/* 3191 */                                       out.print(resId);
/* 3192 */                                       out.write(39);
/* 3193 */                                       out.write(44);
/* 3194 */                                       out.write(39);
/* 3195 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3196 */                                       out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3197 */                                       out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3198 */                                       out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3199 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3200 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3204 */                                   if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3205 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3223 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3224 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 3208 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3209 */                                   out.write("</td>\n        </tr>\n\t");
/* 3210 */                                   int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3211 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3215 */                               if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3223 */                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3224 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                               }
/*      */                             }
/*      */                             catch (Throwable _jspx_exception)
/*      */                             {
/*      */                               for (;;)
/*      */                               {
/* 3219 */                                 int tmp6282_6281 = 0; int[] tmp6282_6279 = _jspx_push_body_count_c_005fforEach_005f0; int tmp6284_6283 = tmp6282_6279[tmp6282_6281];tmp6282_6279[tmp6282_6281] = (tmp6284_6283 - 1); if (tmp6284_6283 <= 0) break;
/* 3220 */                                 out = _jspx_page_context.popBody(); }
/* 3221 */                               _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                             } finally {
/* 3223 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3224 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                             }
/* 3226 */                             out.write("\n      </table>\n ");
/* 3227 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3228 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3232 */                         if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3233 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                         }
/*      */                         
/* 3236 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3237 */                         out.write(10);
/* 3238 */                         out.write(32);
/*      */                         
/* 3240 */                         IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3241 */                         _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3242 */                         _jspx_th_c_005fif_005f7.setParent(null);
/*      */                         
/* 3244 */                         _jspx_th_c_005fif_005f7.setTest("${empty associatedmgs}");
/* 3245 */                         int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3246 */                         if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                           for (;;) {
/* 3248 */                             out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3249 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3250 */                             out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3251 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3252 */                             out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3253 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3254 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3258 */                         if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3259 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                         }
/*      */                         
/* 3262 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3263 */                         out.write(10);
/* 3264 */                         out.write(32);
/* 3265 */                         out.write(10);
/*      */ 
/*      */                       }
/* 3268 */                       else if (mon)
/*      */                       {
/*      */ 
/*      */ 
/* 3272 */                         out.write(10);
/*      */                         
/* 3274 */                         IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3275 */                         _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3276 */                         _jspx_th_c_005fif_005f8.setParent(null);
/*      */                         
/* 3278 */                         _jspx_th_c_005fif_005f8.setTest("${!empty associatedmgs}");
/* 3279 */                         int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3280 */                         if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                           for (;;) {
/* 3282 */                             out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                             
/* 3284 */                             ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3285 */                             _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3286 */                             _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f8);
/*      */                             
/* 3288 */                             _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                             
/* 3290 */                             _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                             
/* 3292 */                             _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3293 */                             int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                             try {
/* 3295 */                               int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3296 */                               if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                 for (;;) {
/* 3298 */                                   out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3299 */                                   if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3357 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3358 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 3301 */                                   out.write("&method=showApplication\" title=\"");
/* 3302 */                                   if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3357 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3358 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 3304 */                                   out.write("\"  class=\"staticlinks\">\n         ");
/* 3305 */                                   if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3357 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3358 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 3307 */                                   out.write("\n    \t");
/* 3308 */                                   out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3309 */                                   out.write("</a></span>\t\n\t\t ");
/*      */                                   
/* 3311 */                                   PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3312 */                                   _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3313 */                                   _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                   
/* 3315 */                                   _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 3316 */                                   int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3317 */                                   if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                     for (;;) {
/* 3319 */                                       out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3320 */                                       if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fpresent_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3357 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 3358 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/* 3322 */                                       out.write(39);
/* 3323 */                                       out.write(44);
/* 3324 */                                       out.write(39);
/* 3325 */                                       out.print(resId);
/* 3326 */                                       out.write(39);
/* 3327 */                                       out.write(44);
/* 3328 */                                       out.write(39);
/* 3329 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3330 */                                       out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3331 */                                       out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3332 */                                       out.write("\"  title=\"<fmt:message key=\"am.webclient.quickremoval.monitorgroup.txt\" />\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3333 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3334 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3338 */                                   if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3339 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3357 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3358 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/* 3342 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3343 */                                   out.write("\n\n\t\t \t");
/* 3344 */                                   int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3345 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3349 */                               if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3357 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3358 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                               }
/*      */                             }
/*      */                             catch (Throwable _jspx_exception)
/*      */                             {
/*      */                               for (;;)
/*      */                               {
/* 3353 */                                 int tmp7234_7233 = 0; int[] tmp7234_7231 = _jspx_push_body_count_c_005fforEach_005f1; int tmp7236_7235 = tmp7234_7231[tmp7234_7233];tmp7234_7231[tmp7234_7233] = (tmp7236_7235 - 1); if (tmp7236_7235 <= 0) break;
/* 3354 */                                 out = _jspx_page_context.popBody(); }
/* 3355 */                               _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                             } finally {
/* 3357 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3358 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                             }
/* 3360 */                             out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3361 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3362 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3366 */                         if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3367 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                         }
/*      */                         
/* 3370 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3371 */                         out.write(10);
/* 3372 */                         out.write(32);
/* 3373 */                         if (_jspx_meth_c_005fif_005f9(_jspx_page_context))
/*      */                           return;
/* 3375 */                         out.write(32);
/* 3376 */                         out.write(10);
/*      */                       }
/*      */                       
/*      */                     }
/* 3380 */                     else if (mon)
/*      */                     {
/*      */ 
/* 3383 */                       out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                     }
/*      */                     
/*      */ 
/* 3387 */                     out.write(9);
/* 3388 */                     out.write(9);
/* 3389 */                     out.write(10);
/*      */                   }
/* 3391 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3392 */         out = _jspx_out;
/* 3393 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3394 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3395 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3398 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3404 */     PageContext pageContext = _jspx_page_context;
/* 3405 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3407 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3408 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3409 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3411 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3413 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3414 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3415 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3416 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3417 */       return true;
/*      */     }
/* 3419 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3420 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3425 */     PageContext pageContext = _jspx_page_context;
/* 3426 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3428 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3429 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3430 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3432 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 3433 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3434 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3435 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3436 */       return true;
/*      */     }
/* 3438 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3439 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3444 */     PageContext pageContext = _jspx_page_context;
/* 3445 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3447 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3448 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3449 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3451 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 3452 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3453 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3454 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3455 */       return true;
/*      */     }
/* 3457 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3458 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3463 */     PageContext pageContext = _jspx_page_context;
/* 3464 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3466 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3467 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3468 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3470 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 3471 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3472 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3473 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3474 */       return true;
/*      */     }
/* 3476 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3477 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3482 */     PageContext pageContext = _jspx_page_context;
/* 3483 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3485 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3486 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3487 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 3489 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 3490 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3491 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3492 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3493 */       return true;
/*      */     }
/* 3495 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3496 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3501 */     PageContext pageContext = _jspx_page_context;
/* 3502 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3504 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3505 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3506 */     _jspx_th_c_005fout_005f5.setParent(null);
/*      */     
/* 3508 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 3509 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3510 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3511 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3512 */       return true;
/*      */     }
/* 3514 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3515 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3520 */     PageContext pageContext = _jspx_page_context;
/* 3521 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3523 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3524 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3525 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3527 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3528 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3529 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3531 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 3532 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3533 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3537 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3538 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3539 */       return true;
/*      */     }
/* 3541 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3547 */     PageContext pageContext = _jspx_page_context;
/* 3548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3550 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3551 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3552 */     _jspx_th_c_005fout_005f6.setParent(null);
/*      */     
/* 3554 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 3555 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3556 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3557 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3558 */       return true;
/*      */     }
/* 3560 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3566 */     PageContext pageContext = _jspx_page_context;
/* 3567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3569 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3570 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3571 */     _jspx_th_c_005fout_005f7.setParent(null);
/*      */     
/* 3573 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 3574 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3575 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3576 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3577 */       return true;
/*      */     }
/* 3579 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3585 */     PageContext pageContext = _jspx_page_context;
/* 3586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3588 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3589 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3590 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3592 */     _jspx_th_c_005fout_005f8.setValue("${ha.key}");
/* 3593 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3594 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3595 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3596 */       return true;
/*      */     }
/* 3598 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3604 */     PageContext pageContext = _jspx_page_context;
/* 3605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3607 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3608 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3609 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3611 */     _jspx_th_c_005fout_005f9.setValue("${ha.value}");
/* 3612 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3613 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3614 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3615 */       return true;
/*      */     }
/* 3617 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3623 */     PageContext pageContext = _jspx_page_context;
/* 3624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3626 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3627 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3628 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3630 */     _jspx_th_c_005fset_005f0.setVar("monitorName");
/* 3631 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3632 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3633 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3634 */         out = _jspx_page_context.pushBody();
/* 3635 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3636 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3637 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3640 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3641 */           return true;
/* 3642 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3643 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3646 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3647 */         out = _jspx_page_context.popBody();
/* 3648 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3651 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3652 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3653 */       return true;
/*      */     }
/* 3655 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3661 */     PageContext pageContext = _jspx_page_context;
/* 3662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3664 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3665 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3666 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3668 */     _jspx_th_c_005fout_005f10.setValue("${ha.value}");
/* 3669 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3670 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3671 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3672 */       return true;
/*      */     }
/* 3674 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3680 */     PageContext pageContext = _jspx_page_context;
/* 3681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3683 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3684 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3685 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3687 */     _jspx_th_c_005fout_005f11.setValue("${ha.key}");
/* 3688 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3689 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3690 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3691 */       return true;
/*      */     }
/* 3693 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3699 */     PageContext pageContext = _jspx_page_context;
/* 3700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3702 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3703 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3704 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3706 */     _jspx_th_c_005fout_005f12.setValue("${ha.key}");
/* 3707 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3708 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3709 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3710 */       return true;
/*      */     }
/* 3712 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3718 */     PageContext pageContext = _jspx_page_context;
/* 3719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3721 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3722 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 3723 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3725 */     _jspx_th_c_005fout_005f13.setValue("${ha.value}");
/* 3726 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 3727 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 3728 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3729 */       return true;
/*      */     }
/* 3731 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3737 */     PageContext pageContext = _jspx_page_context;
/* 3738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3740 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3741 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3742 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 3744 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 3745 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3746 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3747 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3748 */         out = _jspx_page_context.pushBody();
/* 3749 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 3750 */         _jspx_th_c_005fset_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3751 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3754 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3755 */           return true;
/* 3756 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3757 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3760 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3761 */         out = _jspx_page_context.popBody();
/* 3762 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 3765 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3766 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3767 */       return true;
/*      */     }
/* 3769 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3775 */     PageContext pageContext = _jspx_page_context;
/* 3776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3778 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3779 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 3780 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 3782 */     _jspx_th_c_005fout_005f14.setValue("${ha.value}");
/* 3783 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 3784 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 3785 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3786 */       return true;
/*      */     }
/* 3788 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3794 */     PageContext pageContext = _jspx_page_context;
/* 3795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3797 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3798 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 3799 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3801 */     _jspx_th_c_005fout_005f15.setValue("${ha.key}");
/* 3802 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 3803 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 3804 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3805 */       return true;
/*      */     }
/* 3807 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3808 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3813 */     PageContext pageContext = _jspx_page_context;
/* 3814 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3816 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3817 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3818 */     _jspx_th_c_005fif_005f9.setParent(null);
/*      */     
/* 3820 */     _jspx_th_c_005fif_005f9.setTest("${empty associatedmgs}");
/* 3821 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3822 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 3824 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"><fmt:message key=\"am.webclient.urlmonitor.none.text\"/></td>\n\t ");
/* 3825 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3826 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3830 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3831 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3832 */       return true;
/*      */     }
/* 3834 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3835 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ExchangeLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */