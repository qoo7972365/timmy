/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
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
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class networkdetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   62 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   65 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   66 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   67 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   74 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   79 */     ArrayList list = null;
/*   80 */     StringBuffer sbf = new StringBuffer();
/*   81 */     ManagedApplication mo = new ManagedApplication();
/*   82 */     if (distinct)
/*      */     {
/*   84 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   88 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   91 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   93 */       ArrayList row = (ArrayList)list.get(i);
/*   94 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   95 */       if (distinct) {
/*   96 */         sbf.append(row.get(0));
/*      */       } else
/*   98 */         sbf.append(row.get(1));
/*   99 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  102 */     return sbf.toString(); }
/*      */   
/*  104 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  107 */     if (severity == null)
/*      */     {
/*  109 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  111 */     if (severity.equals("5"))
/*      */     {
/*  113 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  115 */     if (severity.equals("1"))
/*      */     {
/*  117 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  122 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  129 */     if (severity == null)
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  133 */     if (severity.equals("1"))
/*      */     {
/*  135 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  137 */     if (severity.equals("4"))
/*      */     {
/*  139 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  141 */     if (severity.equals("5"))
/*      */     {
/*  143 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  148 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  154 */     if (severity == null)
/*      */     {
/*  156 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  158 */     if (severity.equals("5"))
/*      */     {
/*  160 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  162 */     if (severity.equals("1"))
/*      */     {
/*  164 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  168 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  174 */     if (severity == null)
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  178 */     if (severity.equals("1"))
/*      */     {
/*  180 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  182 */     if (severity.equals("4"))
/*      */     {
/*  184 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  186 */     if (severity.equals("5"))
/*      */     {
/*  188 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  192 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  198 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  204 */     if (severity == 5)
/*      */     {
/*  206 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  208 */     if (severity == 1)
/*      */     {
/*  210 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  215 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  221 */     if (severity == null)
/*      */     {
/*  223 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  225 */     if (severity.equals("5"))
/*      */     {
/*  227 */       if (isAvailability) {
/*  228 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  231 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  234 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  236 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  238 */     if (severity.equals("1"))
/*      */     {
/*  240 */       if (isAvailability) {
/*  241 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  244 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  251 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  258 */     if (severity == null)
/*      */     {
/*  260 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  262 */     if (severity.equals("5"))
/*      */     {
/*  264 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  266 */     if (severity.equals("4"))
/*      */     {
/*  268 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  270 */     if (severity.equals("1"))
/*      */     {
/*  272 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  277 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  283 */     if (severity == null)
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  287 */     if (severity.equals("5"))
/*      */     {
/*  289 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  291 */     if (severity.equals("4"))
/*      */     {
/*  293 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  295 */     if (severity.equals("1"))
/*      */     {
/*  297 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  302 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  309 */     if (severity == null)
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  313 */     if (severity.equals("5"))
/*      */     {
/*  315 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  317 */     if (severity.equals("4"))
/*      */     {
/*  319 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  321 */     if (severity.equals("1"))
/*      */     {
/*  323 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  328 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  336 */     StringBuffer out = new StringBuffer();
/*  337 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  338 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  339 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  340 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  341 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  342 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  343 */     out.append("</tr>");
/*  344 */     out.append("</form></table>");
/*  345 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  352 */     if (val == null)
/*      */     {
/*  354 */       return "-";
/*      */     }
/*      */     
/*  357 */     String ret = FormatUtil.formatNumber(val);
/*  358 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  359 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  362 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  366 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  374 */     StringBuffer out = new StringBuffer();
/*  375 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  376 */     out.append("<tr>");
/*  377 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  379 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  381 */     out.append("</tr>");
/*  382 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  386 */       if (j % 2 == 0)
/*      */       {
/*  388 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  392 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  395 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  397 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  400 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  404 */       out.append("</tr>");
/*      */     }
/*  406 */     out.append("</table>");
/*  407 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  408 */     out.append("<tr>");
/*  409 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  410 */     out.append("</tr>");
/*  411 */     out.append("</table>");
/*  412 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  418 */     StringBuffer out = new StringBuffer();
/*  419 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  420 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  421 */     out.append("<tr>");
/*  422 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  423 */     out.append("<tr>");
/*  424 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  425 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  426 */     out.append("</tr>");
/*  427 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  430 */       out.append("<tr>");
/*  431 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  432 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  433 */       out.append("</tr>");
/*      */     }
/*      */     
/*  436 */     out.append("</table>");
/*  437 */     out.append("</table>");
/*  438 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  443 */     if (severity.equals("0"))
/*      */     {
/*  445 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  449 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  456 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  469 */     StringBuffer out = new StringBuffer();
/*  470 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  471 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  473 */       out.append("<tr>");
/*  474 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  475 */       out.append("</tr>");
/*      */       
/*      */ 
/*  478 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  480 */         String borderclass = "";
/*      */         
/*      */ 
/*  483 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  485 */         out.append("<tr>");
/*      */         
/*  487 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  488 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  489 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  495 */     out.append("</table><br>");
/*  496 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  497 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  499 */       List sLinks = secondLevelOfLinks[0];
/*  500 */       List sText = secondLevelOfLinks[1];
/*  501 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  504 */         out.append("<tr>");
/*  505 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  506 */         out.append("</tr>");
/*  507 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  509 */           String borderclass = "";
/*      */           
/*      */ 
/*  512 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  514 */           out.append("<tr>");
/*      */           
/*  516 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  517 */           if (sLinks.get(i).toString().length() == 0) {
/*  518 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  521 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  523 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  527 */     out.append("</table>");
/*  528 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  535 */     StringBuffer out = new StringBuffer();
/*  536 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  537 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  539 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  541 */         out.append("<tr>");
/*  542 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  543 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  547 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  549 */           String borderclass = "";
/*      */           
/*      */ 
/*  552 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  554 */           out.append("<tr>");
/*      */           
/*  556 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  557 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  558 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  561 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  564 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  569 */     out.append("</table><br>");
/*  570 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  571 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  573 */       List sLinks = secondLevelOfLinks[0];
/*  574 */       List sText = secondLevelOfLinks[1];
/*  575 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  578 */         out.append("<tr>");
/*  579 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  580 */         out.append("</tr>");
/*  581 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  583 */           String borderclass = "";
/*      */           
/*      */ 
/*  586 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  588 */           out.append("<tr>");
/*      */           
/*  590 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  591 */           if (sLinks.get(i).toString().length() == 0) {
/*  592 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  595 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  597 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  601 */     out.append("</table>");
/*  602 */     return out.toString();
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
/*  615 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  618 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  621 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  624 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  627 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  630 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  633 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  636 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  644 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  649 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  654 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  659 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  664 */     if (val != null)
/*      */     {
/*  666 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  670 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  675 */     if (val == null) {
/*  676 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  680 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  685 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  691 */     if (val != null)
/*      */     {
/*  693 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  697 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  703 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  708 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  712 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  717 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  722 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  727 */     String hostaddress = "";
/*  728 */     String ip = request.getHeader("x-forwarded-for");
/*  729 */     if (ip == null)
/*  730 */       ip = request.getRemoteAddr();
/*  731 */     InetAddress add = null;
/*  732 */     if (ip.equals("127.0.0.1")) {
/*  733 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  737 */       add = InetAddress.getByName(ip);
/*      */     }
/*  739 */     hostaddress = add.getHostName();
/*  740 */     if (hostaddress.indexOf('.') != -1) {
/*  741 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  742 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  746 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  751 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  757 */     if (severity == null)
/*      */     {
/*  759 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  761 */     if (severity.equals("5"))
/*      */     {
/*  763 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  765 */     if (severity.equals("1"))
/*      */     {
/*  767 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  772 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  777 */     ResultSet set = null;
/*  778 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  779 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  781 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  782 */       if (set.next()) { String str1;
/*  783 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  784 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  787 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  792 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  795 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  797 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  801 */     StringBuffer rca = new StringBuffer();
/*  802 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  803 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  806 */     int rcalength = key.length();
/*  807 */     String split = "6. ";
/*  808 */     int splitPresent = key.indexOf(split);
/*  809 */     String div1 = "";String div2 = "";
/*  810 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  812 */       if (rcalength > 180) {
/*  813 */         rca.append("<span class=\"rca-critical-text\">");
/*  814 */         getRCATrimmedText(key, rca);
/*  815 */         rca.append("</span>");
/*      */       } else {
/*  817 */         rca.append("<span class=\"rca-critical-text\">");
/*  818 */         rca.append(key);
/*  819 */         rca.append("</span>");
/*      */       }
/*  821 */       return rca.toString();
/*      */     }
/*  823 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  824 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  825 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  826 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  827 */     getRCATrimmedText(div1, rca);
/*  828 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  831 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  832 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  833 */     getRCATrimmedText(div2, rca);
/*  834 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  836 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  841 */     String[] st = msg.split("<br>");
/*  842 */     for (int i = 0; i < st.length; i++) {
/*  843 */       String s = st[i];
/*  844 */       if (s.length() > 180) {
/*  845 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  847 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  851 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  852 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  854 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  858 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  859 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  860 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  863 */       if (key == null) {
/*  864 */         return ret;
/*      */       }
/*      */       
/*  867 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  868 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  871 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  872 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  873 */       set = AMConnectionPool.executeQueryStmt(query);
/*  874 */       if (set.next())
/*      */       {
/*  876 */         String helpLink = set.getString("LINK");
/*  877 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  880 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  886 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  905 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  896 */         if (set != null) {
/*  897 */           AMConnectionPool.closeStatement(set);
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
/*  911 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  912 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  914 */       String entityStr = (String)keys.nextElement();
/*  915 */       String mmessage = temp.getProperty(entityStr);
/*  916 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  917 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  919 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  925 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  926 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  928 */       String entityStr = (String)keys.nextElement();
/*  929 */       String mmessage = temp.getProperty(entityStr);
/*  930 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  931 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  933 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  938 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  948 */     String des = new String();
/*  949 */     while (str.indexOf(find) != -1) {
/*  950 */       des = des + str.substring(0, str.indexOf(find));
/*  951 */       des = des + replace;
/*  952 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  954 */     des = des + str;
/*  955 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  962 */       if (alert == null)
/*      */       {
/*  964 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  966 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  968 */         return "&nbsp;";
/*      */       }
/*      */       
/*  971 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  973 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  976 */       int rcalength = test.length();
/*  977 */       if (rcalength < 300)
/*      */       {
/*  979 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  983 */       StringBuffer out = new StringBuffer();
/*  984 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  985 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  986 */       out.append("</div>");
/*  987 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  988 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  989 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  994 */       ex.printStackTrace();
/*      */     }
/*  996 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1002 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1007 */     ArrayList attribIDs = new ArrayList();
/* 1008 */     ArrayList resIDs = new ArrayList();
/* 1009 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1011 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1013 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1015 */       String resourceid = "";
/* 1016 */       String resourceType = "";
/* 1017 */       if (type == 2) {
/* 1018 */         resourceid = (String)row.get(0);
/* 1019 */         resourceType = (String)row.get(3);
/*      */       }
/* 1021 */       else if (type == 3) {
/* 1022 */         resourceid = (String)row.get(0);
/* 1023 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1026 */         resourceid = (String)row.get(6);
/* 1027 */         resourceType = (String)row.get(7);
/*      */       }
/* 1029 */       resIDs.add(resourceid);
/* 1030 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1031 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1033 */       String healthentity = null;
/* 1034 */       String availentity = null;
/* 1035 */       if (healthid != null) {
/* 1036 */         healthentity = resourceid + "_" + healthid;
/* 1037 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1040 */       if (availid != null) {
/* 1041 */         availentity = resourceid + "_" + availid;
/* 1042 */         entitylist.add(availentity);
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
/* 1056 */     Properties alert = getStatus(entitylist);
/* 1057 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1062 */     int size = monitorList.size();
/*      */     
/* 1064 */     String[] severity = new String[size];
/*      */     
/* 1066 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1068 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1069 */       String resourceName1 = (String)row1.get(7);
/* 1070 */       String resourceid1 = (String)row1.get(6);
/* 1071 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1072 */       if (severity[j] == null)
/*      */       {
/* 1074 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1078 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1080 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1082 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1085 */         if (sev > 0) {
/* 1086 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1087 */           monitorList.set(k, monitorList.get(j));
/* 1088 */           monitorList.set(j, t);
/* 1089 */           String temp = severity[k];
/* 1090 */           severity[k] = severity[j];
/* 1091 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1097 */     int z = 0;
/* 1098 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1101 */       int i = 0;
/* 1102 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1105 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1109 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1113 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1115 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1118 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1122 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1125 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1126 */       String resourceName1 = (String)row1.get(7);
/* 1127 */       String resourceid1 = (String)row1.get(6);
/* 1128 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1129 */       if (hseverity[j] == null)
/*      */       {
/* 1131 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1136 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1138 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1141 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1144 */         if (hsev > 0) {
/* 1145 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1146 */           monitorList.set(k, monitorList.get(j));
/* 1147 */           monitorList.set(j, t);
/* 1148 */           String temp1 = hseverity[k];
/* 1149 */           hseverity[k] = hseverity[j];
/* 1150 */           hseverity[j] = temp1;
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
/* 1162 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1163 */     boolean forInventory = false;
/* 1164 */     String trdisplay = "none";
/* 1165 */     String plusstyle = "inline";
/* 1166 */     String minusstyle = "none";
/* 1167 */     String haidTopLevel = "";
/* 1168 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1170 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1172 */         haidTopLevel = request.getParameter("haid");
/* 1173 */         forInventory = true;
/* 1174 */         trdisplay = "table-row;";
/* 1175 */         plusstyle = "none";
/* 1176 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1183 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1186 */     ArrayList listtoreturn = new ArrayList();
/* 1187 */     StringBuffer toreturn = new StringBuffer();
/* 1188 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1189 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1190 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1192 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1194 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1195 */       String childresid = (String)singlerow.get(0);
/* 1196 */       String childresname = (String)singlerow.get(1);
/* 1197 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1198 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1199 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1200 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1201 */       String unmanagestatus = (String)singlerow.get(5);
/* 1202 */       String actionstatus = (String)singlerow.get(6);
/* 1203 */       String linkclass = "monitorgp-links";
/* 1204 */       String titleforres = childresname;
/* 1205 */       String titilechildresname = childresname;
/* 1206 */       String childimg = "/images/trcont.png";
/* 1207 */       String flag = "enable";
/* 1208 */       String dcstarted = (String)singlerow.get(8);
/* 1209 */       String configMonitor = "";
/* 1210 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1211 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1213 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1215 */       if (singlerow.get(7) != null)
/*      */       {
/* 1217 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1219 */       String haiGroupType = "0";
/* 1220 */       if ("HAI".equals(childtype))
/*      */       {
/* 1222 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1224 */       childimg = "/images/trend.png";
/* 1225 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1226 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1227 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1229 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1231 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1233 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1234 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1237 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1239 */         linkclass = "disabledtext";
/* 1240 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1242 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1243 */       String availmouseover = "";
/* 1244 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1246 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1248 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1249 */       String healthmouseover = "";
/* 1250 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1252 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1255 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1256 */       int spacing = 0;
/* 1257 */       if (level >= 1)
/*      */       {
/* 1259 */         spacing = 40 * level;
/*      */       }
/* 1261 */       if (childtype.equals("HAI"))
/*      */       {
/* 1263 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1264 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1265 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1267 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1268 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1269 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1270 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1271 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1272 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1273 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1274 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1275 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1276 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1277 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1279 */         if (!forInventory)
/*      */         {
/* 1281 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1284 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1286 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1288 */           actions = editlink + actions;
/*      */         }
/* 1290 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1292 */           actions = actions + associatelink;
/*      */         }
/* 1294 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1295 */         String arrowimg = "";
/* 1296 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1298 */           actions = "";
/* 1299 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1300 */           checkbox = "";
/* 1301 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1303 */         if (isIt360)
/*      */         {
/* 1305 */           actionimg = "";
/* 1306 */           actions = "";
/* 1307 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1308 */           checkbox = "";
/*      */         }
/*      */         
/* 1311 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1313 */           actions = "";
/*      */         }
/* 1315 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1317 */           checkbox = "";
/*      */         }
/*      */         
/* 1320 */         String resourcelink = "";
/*      */         
/* 1322 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1324 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1328 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1331 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1332 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1333 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1334 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1335 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1336 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1337 */         if (!isIt360)
/*      */         {
/* 1339 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1343 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1346 */         toreturn.append("</tr>");
/* 1347 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1349 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1350 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1354 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1355 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1358 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1362 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1364 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1365 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1366 */             toreturn.append(assocMessage);
/* 1367 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1368 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1369 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1370 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1376 */         String resourcelink = null;
/* 1377 */         boolean hideEditLink = false;
/* 1378 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1380 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1381 */           hideEditLink = true;
/* 1382 */           if (isIt360)
/*      */           {
/* 1384 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1388 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1390 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1392 */           hideEditLink = true;
/* 1393 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1394 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1399 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1402 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1403 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1404 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1405 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1406 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1407 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1408 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1409 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1410 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1411 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1412 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1413 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1414 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1416 */         if (hideEditLink)
/*      */         {
/* 1418 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1420 */         if (!forInventory)
/*      */         {
/* 1422 */           removefromgroup = "";
/*      */         }
/* 1424 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1425 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1426 */           actions = actions + configcustomfields;
/*      */         }
/* 1428 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1430 */           actions = editlink + actions;
/*      */         }
/* 1432 */         String managedLink = "";
/* 1433 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1435 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1436 */           actions = "";
/* 1437 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1438 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1441 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1443 */           checkbox = "";
/*      */         }
/*      */         
/* 1446 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1448 */           actions = "";
/*      */         }
/* 1450 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1451 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1452 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1453 */         if (isIt360)
/*      */         {
/* 1455 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1459 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1461 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1462 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1463 */         if (!isIt360)
/*      */         {
/* 1465 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1469 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1471 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1474 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1481 */       StringBuilder toreturn = new StringBuilder();
/* 1482 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1483 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1484 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1485 */       String title = "";
/* 1486 */       message = EnterpriseUtil.decodeString(message);
/* 1487 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1488 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1489 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1491 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1493 */       else if ("5".equals(severity))
/*      */       {
/* 1495 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1499 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1501 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1502 */       toreturn.append(v);
/*      */       
/* 1504 */       toreturn.append(link);
/* 1505 */       if (severity == null)
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1509 */       else if (severity.equals("5"))
/*      */       {
/* 1511 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1513 */       else if (severity.equals("4"))
/*      */       {
/* 1515 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1517 */       else if (severity.equals("1"))
/*      */       {
/* 1519 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1524 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1526 */       toreturn.append("</a>");
/* 1527 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1531 */       ex.printStackTrace();
/*      */     }
/* 1533 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1540 */       StringBuilder toreturn = new StringBuilder();
/* 1541 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1542 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1543 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1544 */       if (message == null)
/*      */       {
/* 1546 */         message = "";
/*      */       }
/*      */       
/* 1549 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1550 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1552 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1553 */       toreturn.append(v);
/*      */       
/* 1555 */       toreturn.append(link);
/*      */       
/* 1557 */       if (severity == null)
/*      */       {
/* 1559 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1561 */       else if (severity.equals("5"))
/*      */       {
/* 1563 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1565 */       else if (severity.equals("1"))
/*      */       {
/* 1567 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1572 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1574 */       toreturn.append("</a>");
/* 1575 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1581 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1584 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1585 */     if (invokeActions != null) {
/* 1586 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1587 */       while (iterator.hasNext()) {
/* 1588 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1589 */         if (actionmap.containsKey(actionid)) {
/* 1590 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1595 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1599 */     String actionLink = "";
/* 1600 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1601 */     String query = "";
/* 1602 */     ResultSet rs = null;
/* 1603 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1604 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1605 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1606 */       actionLink = "method=" + methodName;
/*      */     }
/* 1608 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1609 */       actionLink = methodName;
/*      */     }
/* 1611 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1612 */     Iterator itr = methodarglist.iterator();
/* 1613 */     boolean isfirstparam = true;
/* 1614 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1615 */     while (itr.hasNext()) {
/* 1616 */       HashMap argmap = (HashMap)itr.next();
/* 1617 */       String argtype = (String)argmap.get("TYPE");
/* 1618 */       String argname = (String)argmap.get("IDENTITY");
/* 1619 */       String paramname = (String)argmap.get("PARAMETER");
/* 1620 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1621 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1622 */         isfirstparam = false;
/* 1623 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1625 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1629 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1633 */         actionLink = actionLink + "&";
/*      */       }
/* 1635 */       String paramValue = null;
/* 1636 */       String tempargname = argname;
/* 1637 */       if (commonValues.getProperty(tempargname) != null) {
/* 1638 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1641 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1642 */           String dbType = DBQueryUtil.getDBType();
/* 1643 */           if (dbType.equals("mysql")) {
/* 1644 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1647 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1649 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1651 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1652 */             if (rs.next()) {
/* 1653 */               paramValue = rs.getString("VALUE");
/* 1654 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1658 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1662 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1665 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1670 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1671 */           paramValue = rowId;
/*      */         }
/* 1673 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1674 */           paramValue = managedObjectName;
/*      */         }
/* 1676 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1677 */           paramValue = resID;
/*      */         }
/* 1679 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1680 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1683 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1685 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1686 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1687 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1689 */     return actionLink;
/*      */   }
/*      */   
/* 1692 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1693 */     String dependentAttribute = null;
/* 1694 */     String align = "left";
/*      */     
/* 1696 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1697 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1698 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1699 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1700 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1701 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1702 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1703 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1704 */       align = "center";
/*      */     }
/*      */     
/* 1707 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1708 */     String actualdata = "";
/*      */     
/* 1710 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1711 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1712 */         actualdata = availValue;
/*      */       }
/* 1714 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1715 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1719 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1720 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1723 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1729 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1730 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1731 */       toreturn.append("<table>");
/* 1732 */       toreturn.append("<tr>");
/* 1733 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1734 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1735 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1736 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1737 */         String toolTip = "";
/* 1738 */         String hideClass = "";
/* 1739 */         String textStyle = "";
/* 1740 */         boolean isreferenced = true;
/* 1741 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1742 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1743 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1744 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1746 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1747 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1748 */           while (valueList.hasMoreTokens()) {
/* 1749 */             String dependentVal = valueList.nextToken();
/* 1750 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1751 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1752 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1754 */               toolTip = "";
/* 1755 */               hideClass = "";
/* 1756 */               isreferenced = false;
/* 1757 */               textStyle = "disabledtext";
/* 1758 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1762 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1763 */           toolTip = "";
/* 1764 */           hideClass = "";
/* 1765 */           isreferenced = false;
/* 1766 */           textStyle = "disabledtext";
/* 1767 */           if (dependentImageMap != null) {
/* 1768 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1769 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1772 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1776 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1777 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1778 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1779 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1780 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1781 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1783 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1784 */           if (isreferenced) {
/* 1785 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1789 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1790 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1791 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1792 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1793 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1794 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1796 */           toreturn.append("</span>");
/* 1797 */           toreturn.append("</a>");
/* 1798 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1801 */       toreturn.append("</tr>");
/* 1802 */       toreturn.append("</table>");
/* 1803 */       toreturn.append("</td>");
/*      */     } else {
/* 1805 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1808 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1812 */     String colTime = null;
/* 1813 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1814 */     if ((rows != null) && (rows.size() > 0)) {
/* 1815 */       Iterator<String> itr = rows.iterator();
/* 1816 */       String maxColQuery = "";
/* 1817 */       for (;;) { if (itr.hasNext()) {
/* 1818 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1819 */           ResultSet maxCol = null;
/*      */           try {
/* 1821 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1822 */             while (maxCol.next()) {
/* 1823 */               if (colTime == null) {
/* 1824 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1827 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1836 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1838 */               if (maxCol != null)
/* 1839 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1841 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1836 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1838 */               if (maxCol != null)
/* 1839 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1841 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1846 */     return colTime;
/*      */   }
/*      */   
/* 1849 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1850 */     tablename = null;
/* 1851 */     ResultSet rsTable = null;
/* 1852 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1854 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1855 */       while (rsTable.next()) {
/* 1856 */         tablename = rsTable.getString("DATATABLE");
/* 1857 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1858 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1871 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1862 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1865 */         if (rsTable != null)
/* 1866 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1868 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1874 */     String argsList = "";
/* 1875 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1877 */       if (showArgsMap.get(row) != null) {
/* 1878 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1879 */         if (showArgslist != null) {
/* 1880 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1881 */             if (argsList.trim().equals("")) {
/* 1882 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1885 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1892 */       e.printStackTrace();
/* 1893 */       return "";
/*      */     }
/* 1895 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1900 */     String argsList = "";
/* 1901 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1904 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1906 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1907 */         if (hideArgsList != null)
/*      */         {
/* 1909 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1911 */             if (argsList.trim().equals(""))
/*      */             {
/* 1913 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1917 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1925 */       ex.printStackTrace();
/*      */     }
/* 1927 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1931 */     StringBuilder toreturn = new StringBuilder();
/* 1932 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1939 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1940 */       Iterator itr = tActionList.iterator();
/* 1941 */       while (itr.hasNext()) {
/* 1942 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1943 */         String confirmmsg = "";
/* 1944 */         String link = "";
/* 1945 */         String isJSP = "NO";
/* 1946 */         HashMap tactionMap = (HashMap)itr.next();
/* 1947 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1948 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1949 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1950 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1951 */           (actionmap.containsKey(actionId))) {
/* 1952 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1953 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1954 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1955 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1956 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1958 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1964 */           if (isTableAction) {
/* 1965 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1968 */             tableName = "Link";
/* 1969 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1970 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1971 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1972 */             toreturn.append("</a></td>");
/*      */           }
/* 1974 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1975 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1976 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1977 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1983 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1989 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1991 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1992 */       Properties prop = (Properties)node.getUserObject();
/* 1993 */       String mgID = prop.getProperty("label");
/* 1994 */       String mgName = prop.getProperty("value");
/* 1995 */       String isParent = prop.getProperty("isParent");
/* 1996 */       int mgIDint = Integer.parseInt(mgID);
/* 1997 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1999 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2001 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2002 */       if (node.getChildCount() > 0)
/*      */       {
/* 2004 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2006 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2008 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2010 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2014 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2019 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2021 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2023 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2025 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2029 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2032 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2033 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2035 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2039 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2041 */       if (node.getChildCount() > 0)
/*      */       {
/* 2043 */         builder.append("<UL>");
/* 2044 */         printMGTree(node, builder);
/* 2045 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2050 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2051 */     StringBuffer toReturn = new StringBuffer();
/* 2052 */     String table = "-";
/*      */     try {
/* 2054 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2055 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2056 */       float total = 0.0F;
/* 2057 */       while (it.hasNext()) {
/* 2058 */         String attName = (String)it.next();
/* 2059 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2060 */         boolean roundOffData = false;
/* 2061 */         if ((data != null) && (!data.equals(""))) {
/* 2062 */           if (data.indexOf(",") != -1) {
/* 2063 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2066 */             float value = Float.parseFloat(data);
/* 2067 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2070 */             total += value;
/* 2071 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2074 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2079 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2080 */       while (attVsWidthList.hasNext()) {
/* 2081 */         String attName = (String)attVsWidthList.next();
/* 2082 */         String data = (String)attVsWidthProps.get(attName);
/* 2083 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2084 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2085 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2086 */         String className = (String)graphDetails.get("ClassName");
/* 2087 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2088 */         if (percentage < 1.0F)
/*      */         {
/* 2090 */           data = percentage + "";
/*      */         }
/* 2092 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2094 */       if (toReturn.length() > 0) {
/* 2095 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2099 */       e.printStackTrace();
/*      */     }
/* 2101 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2107 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2108 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2109 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2110 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2111 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2112 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2113 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2114 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2115 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2118 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2119 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2120 */       splitvalues[0] = multiplecondition.toString();
/* 2121 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2124 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2129 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2130 */     if (thresholdType != 3) {
/* 2131 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2132 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2133 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2134 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2135 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2136 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2138 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2139 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2140 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2141 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2142 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2143 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2145 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2146 */     if (updateSelected != null) {
/* 2147 */       updateSelected[0] = "selected";
/*      */     }
/* 2149 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2154 */       StringBuffer toreturn = new StringBuffer("");
/* 2155 */       if (commaSeparatedMsgId != null) {
/* 2156 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2157 */         int count = 0;
/* 2158 */         while (msgids.hasMoreTokens()) {
/* 2159 */           String id = msgids.nextToken();
/* 2160 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2161 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2162 */           count++;
/* 2163 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2164 */             if (toreturn.length() == 0) {
/* 2165 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2167 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2168 */             if (!image.trim().equals("")) {
/* 2169 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2171 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2172 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2175 */         if (toreturn.length() > 0) {
/* 2176 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2180 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2183 */       e.printStackTrace(); }
/* 2184 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ArrayList getServersInCluster()
/*      */   {
/* 2195 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2196 */     ResultSet set = null;
/* 2197 */     toReturn = new ArrayList();
/*      */     try
/*      */     {
/* 2200 */       set = AMConnectionPool.executeQueryStmt("select CHILDID from AM_PARENTCHILDMAPPER,AM_WLS_CLUSTER where AM_PARENTCHILDMAPPER.PARENTID=AM_WLS_CLUSTER.ID");
/* 2201 */       while (set.next())
/*      */       {
/* 2203 */         toReturn.add(String.valueOf(set.getInt("CHILDID")));
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
/* 2218 */       return toReturn;
/*      */     }
/*      */     catch (Exception exp) {}finally
/*      */     {
/*      */       try
/*      */       {
/* 2212 */         if (set != null) {
/* 2213 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */       }
/*      */       catch (Exception exp) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getType(String resourcetype)
/*      */   {
/* 2225 */     if (resourcetype.equals("JBOSS-server"))
/*      */     {
/* 2227 */       return "JBoss:8080";
/*      */     }
/* 2229 */     if (resourcetype.equals("WEBLOGIC-server"))
/*      */     {
/* 2231 */       return "WEBLOGIC:7001";
/*      */     }
/* 2233 */     if (resourcetype.equals("Tomcat-server"))
/*      */     {
/* 2235 */       return "Tomcat:8080";
/*      */     }
/* 2237 */     if (resourcetype.equals("MYSQL-DB-server"))
/*      */     {
/* 2239 */       return "MYSQLDB:3306";
/*      */     }
/* 2241 */     if (resourcetype.equals("ORACLE-DB-server"))
/*      */     {
/* 2243 */       return "ORACLEDB:1521";
/*      */     }
/* 2245 */     if (resourcetype.equals("DB2-server"))
/*      */     {
/* 2247 */       return "DB2:50000";
/*      */     }
/* 2249 */     if (resourcetype.equals("SYBASE-DB-server"))
/*      */     {
/* 2251 */       return "SYBASE:5000";
/*      */     }
/* 2253 */     if (resourcetype.equals("RMI"))
/*      */     {
/* 2255 */       return "RMI:1099";
/*      */     }
/* 2257 */     if (resourcetype.equals("MAIL-server"))
/*      */     {
/* 2259 */       return "MAIL:110:25";
/*      */     }
/* 2261 */     if (resourcetype.equals("WEB-server"))
/*      */     {
/* 2263 */       return "WEB:80";
/*      */     }
/* 2265 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   private String getPort(String resourcetype)
/*      */   {
/* 2271 */     if (resourcetype.equals("JBOSS-server"))
/*      */     {
/* 2273 */       return "8080";
/*      */     }
/* 2275 */     if (resourcetype.equals("WEBLOGIC-server"))
/*      */     {
/* 2277 */       return "7001";
/*      */     }
/* 2279 */     if (resourcetype.equals("Tomcat-server"))
/*      */     {
/* 2281 */       return "8080";
/*      */     }
/* 2283 */     if (resourcetype.equals("MYSQL-DB-server"))
/*      */     {
/* 2285 */       return "3306";
/*      */     }
/* 2287 */     if (resourcetype.equals("ORACLE-DB-server"))
/*      */     {
/* 2289 */       return "1521";
/*      */     }
/* 2291 */     if (resourcetype.equals("DB2-server"))
/*      */     {
/* 2293 */       return "50000";
/*      */     }
/* 2295 */     if (resourcetype.equals("SYBASE-DB-server"))
/*      */     {
/* 2297 */       return "5000";
/*      */     }
/* 2299 */     if (resourcetype.equals("RMI"))
/*      */     {
/* 2301 */       return "1099";
/*      */     }
/* 2303 */     if (resourcetype.equals("MAIL-server"))
/*      */     {
/* 2305 */       return "110:25";
/*      */     }
/* 2307 */     if (resourcetype.equals("WEB-server"))
/*      */     {
/* 2309 */       return "80";
/*      */     }
/* 2311 */     return null;
/*      */   }
/*      */   
/* 2314 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2320 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2321 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/* 2322 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2323 */     _jspx_dependants.put("/jsp/includes/monitors_setasdefault.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2346 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2350 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2351 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2352 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2353 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2354 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2355 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2356 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2357 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2358 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2359 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2360 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2361 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2362 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2363 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2364 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2365 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2366 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2370 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/* 2371 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2372 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2373 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2374 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2375 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2376 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2377 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2378 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2379 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2380 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2381 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2382 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2383 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2384 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2391 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2394 */     JspWriter out = null;
/* 2395 */     Object page = this;
/* 2396 */     JspWriter _jspx_out = null;
/* 2397 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2401 */       response.setContentType("text/html;charset=UTF-8");
/* 2402 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2404 */       _jspx_page_context = pageContext;
/* 2405 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2406 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2407 */       session = pageContext.getSession();
/* 2408 */       out = pageContext.getOut();
/* 2409 */       _jspx_out = out;
/*      */       
/* 2411 */       out.write("<!DOCTYPE html>\n");
/* 2412 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2413 */       out.write(10);
/*      */       
/* 2415 */       request.setAttribute("HelpKey", "Monitors Page");
/*      */       
/* 2417 */       out.write(10);
/* 2418 */       out.write(10);
/* 2419 */       out.write(10);
/* 2420 */       ManagedApplication mo = null;
/* 2421 */       synchronized (application) {
/* 2422 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2423 */         if (mo == null) {
/* 2424 */           mo = new ManagedApplication();
/* 2425 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2428 */       out.write(10);
/* 2429 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2431 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2432 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2434 */       out.write(10);
/* 2435 */       out.write(10);
/* 2436 */       out.write(10);
/* 2437 */       out.write("\n\n\n\n\n\n");
/* 2438 */       Hashtable motypedisplaynames = null;
/* 2439 */       synchronized (application) {
/* 2440 */         motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2441 */         if (motypedisplaynames == null) {
/* 2442 */           motypedisplaynames = new Hashtable();
/* 2443 */           _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */         }
/*      */       }
/* 2446 */       out.write("\n\n\n\n\n\n\n\n\n<script src=\"/template/TabDrag.js\" type=\"text/javascript\" language=\"JavaScript\"></script>\n\n");
/*      */       
/* 2448 */       String selectedTab = EnterpriseUtil.getSelectedTab(request);
/* 2449 */       Object isSearchRequest = request.getAttribute("searchRequest");
/* 2450 */       if ((!"Admin".equals(selectedTab)) && (EnterpriseUtil.isIt360MSPEdition()) && (request.getSession().getAttribute("custProp") == null) && (isSearchRequest == null))
/*      */       {
/* 2452 */         response.sendRedirect("/showIT360Tile.do?TileName=IT360.CustomersSummary");
/*      */       }
/*      */       try
/*      */       {
/*      */         try {
/* 2457 */           String it360SPCondition = "";
/* 2458 */           request.setAttribute("fullpercent", "true");
/* 2459 */           if ((EnterpriseUtil.isIt360MSPEdition()) && (request.getSession().getAttribute("custProp") != null))
/*      */           {
/* 2461 */             it360SPCondition = " AND " + com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getCondition("AM_ManagedObject.RESOURCEID", com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.filterResourceIds(request)) + " ";
/*      */           }
/* 2463 */           String eumChildString = null;
/*      */           
/* 2465 */           if (request.getAttribute("eumChildString") != null) {
/* 2466 */             eumChildString = (String)request.getAttribute("eumChildString");
/*      */           } else {
/* 2468 */             eumChildString = com.adventnet.appmanager.util.Constants.getEUMChildString();
/*      */           }
/* 2470 */           String eumCondition = " AND AM_ManagedObject.RESOURCEID NOT IN (" + eumChildString + ")";
/* 2471 */           it360SPCondition = it360SPCondition + eumCondition;
/*      */           
/* 2473 */           String types = com.adventnet.appmanager.util.Constants.resourceTypes;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2478 */           types = types.replaceAll("OpManager-Interface-", "");
/*      */           
/* 2480 */           Hashtable table = mo.getDistinctManagedObjects();
/* 2481 */           String infraView = "false";
/* 2482 */           if (request.getAttribute("InfrastructureView") != null)
/*      */           {
/* 2484 */             infraView = (String)request.getAttribute("InfrastructureView");
/*      */           }
/* 2486 */           String resourceName = request.getParameter("network");
/* 2487 */           String haid = request.getParameter("haid");
/* 2488 */           String group = request.getParameter("group");
/* 2489 */           String selectedNetwork = request.getParameter("selectedNetwork");
/* 2490 */           String method = request.getParameter("method");
/* 2491 */           String customValue = request.getParameter("customValue");
/* 2492 */           String queries = request.getParameter("query");
/* 2493 */           String dataTable = "";
/* 2494 */           String qryCon = "";
/* 2495 */           String customCol = "";
/* 2496 */           String colValue = "";
/* 2497 */           String forbulkAssign = "";
/* 2498 */           ArrayList<String> countqueries = new ArrayList();
/* 2499 */           if (group != null)
/*      */           {
/* 2501 */             resourceName = group;
/*      */           }
/* 2503 */           motypedisplaynames.put("All", "All Monitors");
/* 2504 */           String displayName = FormatUtil.getString((String)motypedisplaynames.get(resourceName));
/* 2505 */           if ((resourceName != null & resourceName.startsWith("OpManager-"))) {
/* 2506 */             int index = resourceName.indexOf("-");
/* 2507 */             displayName = resourceName.substring(index + 1);
/*      */           }
/*      */           
/* 2510 */           out.write(10);
/* 2511 */           out.write(10);
/*      */           
/* 2513 */           String headerandTab = "/jsp/header.jsp?tabtoselect=1";
/* 2514 */           if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */           {
/* 2516 */             if (request.getAttribute("isReqFromAdmin") != null)
/*      */             {
/* 2518 */               String isReqFromAdmin = request.getAttribute("isReqFromAdmin").toString();
/* 2519 */               if (isReqFromAdmin.equals("true"))
/*      */               {
/* 2521 */                 headerandTab = "/jsp/header.jsp?tabtoselect=6";
/*      */               }
/*      */               else
/*      */               {
/* 2525 */                 headerandTab = "/jsp/header.jsp";
/*      */               }
/*      */             }
/*      */           }
/* 2529 */           String title = FormatUtil.getString("am.webclient.common.monitortab.bulkconfig.title");
/* 2530 */           String network = request.getParameter("selectedNetwork");
/* 2531 */           String leftPage = "/jsp/MapsLeftPage.jsp?method=showResourceTypesAll&group=All&selectedNetwork=" + network;
/* 2532 */           String toAppendLink = "";
/* 2533 */           String monTypeAtt = "All";
/* 2534 */           String showManage = request.getParameter("showmanage");
/* 2535 */           if (request.getParameter("viewmontype") != null)
/*      */           {
/* 2537 */             monTypeAtt = request.getParameter("viewmontype");
/*      */           }
/* 2539 */           if (network != null)
/*      */           {
/* 2541 */             title = network + "&nbsp;-&nbsp;" + title;
/* 2542 */             leftPage = leftPage + "&selectedNetwork=" + network;
/* 2543 */             toAppendLink = "&selectedNetwork=" + network;
/*      */           }
/*      */           else
/*      */           {
/* 2547 */             title = title;
/*      */           }
/* 2549 */           request.setAttribute("defaultview", "showResourceTypesAll");
/* 2550 */           ArrayList globalslist = mo.getRows("select VALUE from AM_GLOBALCONFIG where NAME='allowOperatorEdit' or NAME='allowOperatorManage' or NAME='allowOperatorUnmanageAndReset' or NAME='allowOperatorUpdateIP'");
/* 2551 */           String allowEdit = (String)((ArrayList)globalslist.get(0)).get(0);
/* 2552 */           String allowManage = (String)((ArrayList)globalslist.get(1)).get(0);
/* 2553 */           String allowReset = (String)((ArrayList)globalslist.get(2)).get(0);
/* 2554 */           String allowUpdateIP = (String)((ArrayList)globalslist.get(3)).get(0);
/* 2555 */           pageContext.setAttribute("allowEdit", allowEdit);
/* 2556 */           pageContext.setAttribute("allowUpdateIP", allowUpdateIP);
/* 2557 */           pageContext.setAttribute("allowManage", allowManage);
/* 2558 */           pageContext.setAttribute("allowReset", allowReset);
/*      */           
/* 2560 */           out.write(10);
/* 2561 */           if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */             return;
/* 2563 */           out.write(10);
/*      */           
/* 2565 */           InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2566 */           _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2567 */           _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */           
/* 2569 */           _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2570 */           int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2571 */           if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */             for (;;) {
/* 2573 */               out.write(10);
/*      */               
/* 2575 */               PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2576 */               _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2577 */               _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */               
/* 2579 */               _jspx_th_tiles_005fput_005f0.setName("title");
/*      */               
/* 2581 */               _jspx_th_tiles_005fput_005f0.setValue(title);
/* 2582 */               int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2583 */               if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2584 */                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */               }
/*      */               
/* 2587 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2588 */               out.write(10);
/*      */               
/* 2590 */               PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2591 */               _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2592 */               _jspx_th_tiles_005fput_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */               
/* 2594 */               _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */               
/* 2596 */               _jspx_th_tiles_005fput_005f1.setValue(headerandTab);
/* 2597 */               int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2598 */               if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2599 */                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1); return;
/*      */               }
/*      */               
/* 2602 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2603 */               out.write(10);
/*      */               
/* 2605 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2606 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2607 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/* 2608 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2609 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 2611 */                   out.write(10);
/*      */                   
/* 2613 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2614 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2615 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/* 2617 */                   _jspx_th_c_005fwhen_005f0.setTest("${isReqFromAdmin=='true'}");
/* 2618 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2619 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/* 2621 */                       out.write(10);
/*      */                       
/* 2623 */                       PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2624 */                       _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2625 */                       _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 2627 */                       _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                       
/* 2629 */                       _jspx_th_tiles_005fput_005f2.setType("string");
/* 2630 */                       int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2631 */                       if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2632 */                         if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2633 */                           out = _jspx_page_context.pushBody();
/* 2634 */                           _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2635 */                           _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2638 */                           out.write(10);
/* 2639 */                           out.write(32);
/* 2640 */                           out.write(32);
/* 2641 */                           out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                           
/*      */ 
/* 2644 */                           String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                           
/* 2646 */                           out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 2647 */                           out.print(FormatUtil.getString("wizard.disabled"));
/* 2648 */                           out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 2649 */                           out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 2650 */                           out.write("</td>\n  </tr>\n  \n ");
/*      */                           
/* 2652 */                           if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                           {
/*      */ 
/* 2655 */                             out.write("  \n  <tr>\n\n  ");
/*      */                             
/* 2657 */                             if (request.getParameter("wiz") != null)
/*      */                             {
/* 2659 */                               out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 2660 */                               out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2661 */                               out.write("\" class='disabledlink'>");
/* 2662 */                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2663 */                               out.write("</a></td>\n  ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 2667 */                               out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                               
/* 2669 */                               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2670 */                               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2671 */                               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/* 2672 */                               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2673 */                               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                 for (;;) {
/* 2675 */                                   out.write(10);
/*      */                                   
/* 2677 */                                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2678 */                                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2679 */                                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 2681 */                                   _jspx_th_c_005fwhen_005f1.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 2682 */                                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2683 */                                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                     for (;;) {
/* 2685 */                                       out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 2686 */                                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2687 */                                       out.write("\n    </a>\n ");
/* 2688 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2689 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2693 */                                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2694 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                   }
/*      */                                   
/* 2697 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2698 */                                   out.write(10);
/* 2699 */                                   out.write(32);
/*      */                                   
/* 2701 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2702 */                                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2703 */                                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f1);
/* 2704 */                                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2705 */                                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                     for (;;) {
/* 2707 */                                       out.write(10);
/* 2708 */                                       out.write(9);
/* 2709 */                                       out.write(32);
/* 2710 */                                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2711 */                                       out.write(10);
/* 2712 */                                       out.write(32);
/* 2713 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2714 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2718 */                                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2719 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                   }
/*      */                                   
/* 2722 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2723 */                                   out.write(10);
/* 2724 */                                   out.write(32);
/* 2725 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2726 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2730 */                               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2731 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                               }
/*      */                               
/* 2734 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2735 */                               out.write("\n    </td>\n\t");
/*      */                             }
/* 2737 */                             out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                             
/* 2739 */                             if (request.getParameter("wiz") != null)
/*      */                             {
/* 2741 */                               out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 2742 */                               out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2743 */                               out.write("\" class='disabledlink'>");
/* 2744 */                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2745 */                               out.write("</a></td>\n   ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 2749 */                               out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                               
/* 2751 */                               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2752 */                               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2753 */                               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/* 2754 */                               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2755 */                               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                 for (;;) {
/* 2757 */                                   out.write(10);
/*      */                                   
/* 2759 */                                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2760 */                                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2761 */                                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                   
/* 2763 */                                   _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 2764 */                                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2765 */                                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                     for (;;) {
/* 2767 */                                       out.write("\n   ");
/* 2768 */                                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2769 */                                       out.write(10);
/* 2770 */                                       out.write(32);
/* 2771 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2772 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2776 */                                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2777 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                   }
/*      */                                   
/* 2780 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2781 */                                   out.write(10);
/* 2782 */                                   out.write(32);
/*      */                                   
/* 2784 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2785 */                                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2786 */                                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f2);
/* 2787 */                                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2788 */                                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                     for (;;) {
/* 2790 */                                       out.write(10);
/* 2791 */                                       String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 2792 */                                       out.write("\n\t \n <a href=\"");
/* 2793 */                                       out.print(link);
/* 2794 */                                       out.write("\" class=\"new-left-links\">\n               ");
/* 2795 */                                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2796 */                                       out.write("\n    </a>    \n ");
/* 2797 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2798 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2802 */                                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2803 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                   }
/*      */                                   
/* 2806 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2807 */                                   out.write(10);
/* 2808 */                                   out.write(32);
/* 2809 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2810 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2814 */                               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2815 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                               }
/*      */                               
/* 2818 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2819 */                               out.write("\n</td>\n");
/*      */                             }
/* 2821 */                             out.write("\n</tr>\n\n ");
/*      */                           }
/*      */                           
/*      */ 
/* 2825 */                           out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 2827 */                           ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2828 */                           _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2829 */                           _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/* 2830 */                           int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2831 */                           if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                             for (;;) {
/* 2833 */                               out.write(10);
/*      */                               
/* 2835 */                               WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2836 */                               _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2837 */                               _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                               
/* 2839 */                               _jspx_th_c_005fwhen_005f3.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 2840 */                               int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2841 */                               if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                 for (;;) {
/* 2843 */                                   out.write("\n    \n       ");
/* 2844 */                                   out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2845 */                                   out.write(10);
/* 2846 */                                   out.write(32);
/* 2847 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2848 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2852 */                               if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2853 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                               }
/*      */                               
/* 2856 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2857 */                               out.write(10);
/* 2858 */                               out.write(32);
/*      */                               
/* 2860 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2861 */                               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2862 */                               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f3);
/* 2863 */                               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2864 */                               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                 for (;;) {
/* 2866 */                                   out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 2867 */                                   out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2868 */                                   out.write("\n    </a>\n ");
/* 2869 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2870 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2874 */                               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2875 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                               }
/*      */                               
/* 2878 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2879 */                               out.write(10);
/* 2880 */                               out.write(32);
/* 2881 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2882 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2886 */                           if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2887 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                           }
/*      */                           
/* 2890 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2891 */                           out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 2893 */                           ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2894 */                           _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2895 */                           _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/* 2896 */                           int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2897 */                           if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                             for (;;) {
/* 2899 */                               out.write(10);
/*      */                               
/* 2901 */                               WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2902 */                               _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2903 */                               _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                               
/* 2905 */                               _jspx_th_c_005fwhen_005f4.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 2906 */                               int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2907 */                               if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                 for (;;) {
/* 2909 */                                   out.write("\n    \n       ");
/* 2910 */                                   out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2911 */                                   out.write(10);
/* 2912 */                                   out.write(32);
/* 2913 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2914 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2918 */                               if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2919 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                               }
/*      */                               
/* 2922 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2923 */                               out.write(10);
/* 2924 */                               out.write(32);
/*      */                               
/* 2926 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2927 */                               _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2928 */                               _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f4);
/* 2929 */                               int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2930 */                               if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                 for (;;) {
/* 2932 */                                   out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 2933 */                                   out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2934 */                                   out.write("\n\t </a>\n ");
/* 2935 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2936 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2940 */                               if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2941 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                               }
/*      */                               
/* 2944 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2945 */                               out.write(10);
/* 2946 */                               out.write(32);
/* 2947 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2948 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2952 */                           if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2953 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                           }
/*      */                           
/* 2956 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2957 */                           out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                           
/* 2959 */                           if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                           {
/*      */ 
/* 2962 */                             out.write(32);
/* 2963 */                             out.write(32);
/* 2964 */                             out.write(10);
/*      */                             
/* 2966 */                             ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2967 */                             _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2968 */                             _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/* 2969 */                             int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2970 */                             if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                               for (;;) {
/* 2972 */                                 out.write(10);
/*      */                                 
/* 2974 */                                 WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2975 */                                 _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2976 */                                 _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                 
/* 2978 */                                 _jspx_th_c_005fwhen_005f5.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 2979 */                                 int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2980 */                                 if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                   for (;;) {
/* 2982 */                                     out.write("\n<tr>\n    ");
/* 2983 */                                     if (!request.isUserInRole("OPERATOR")) {
/* 2984 */                                       out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 2985 */                                       out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2986 */                                       out.write("\n    </a>\n        </td>\n     ");
/*      */                                     } else {
/* 2988 */                                       out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 2989 */                                       out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2990 */                                       out.write("\n\t</a>\n\t </td>\n\t");
/*      */                                     }
/* 2992 */                                     out.write("\n    </tr>\n ");
/* 2993 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2994 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2998 */                                 if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2999 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                 }
/*      */                                 
/* 3002 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 3003 */                                 out.write(10);
/* 3004 */                                 out.write(32);
/*      */                                 
/* 3006 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3007 */                                 _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 3008 */                                 _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f5);
/* 3009 */                                 int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 3010 */                                 if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                   for (;;) {
/* 3012 */                                     out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 3013 */                                     out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 3014 */                                     out.write("\n\t </td>\n ");
/* 3015 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 3016 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3020 */                                 if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 3021 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                 }
/*      */                                 
/* 3024 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3025 */                                 out.write(10);
/* 3026 */                                 out.write(32);
/* 3027 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3028 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3032 */                             if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3033 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                             }
/*      */                             
/* 3036 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3037 */                             out.write("\n \n  ");
/*      */                           }
/*      */                           
/*      */ 
/* 3041 */                           out.write("  \n \n ");
/*      */                           
/* 3043 */                           if (!usertype.equals("F"))
/*      */                           {
/* 3045 */                             out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                             
/* 3047 */                             ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3048 */                             _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 3049 */                             _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/* 3050 */                             int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 3051 */                             if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                               for (;;) {
/* 3053 */                                 out.write(10);
/* 3054 */                                 out.write(9);
/*      */                                 
/* 3056 */                                 WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3057 */                                 _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3058 */                                 _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                                 
/* 3060 */                                 _jspx_th_c_005fwhen_005f6.setTest("${param.method !='maintenanceTaskListView'}");
/* 3061 */                                 int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3062 */                                 if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                                   for (;;) {
/* 3064 */                                     out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3065 */                                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3066 */                                     out.write("</a>\n  \t");
/* 3067 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3068 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3072 */                                 if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3073 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                                 }
/*      */                                 
/* 3076 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3077 */                                 out.write("\n  \t");
/*      */                                 
/* 3079 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3080 */                                 _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3081 */                                 _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f6);
/* 3082 */                                 int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3083 */                                 if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                   for (;;) {
/* 3085 */                                     out.write("\n \t\t");
/* 3086 */                                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3087 */                                     out.write("\n  \t");
/* 3088 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3089 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3093 */                                 if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3094 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                                 }
/*      */                                 
/* 3097 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3098 */                                 out.write("\n  \t");
/* 3099 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3100 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3104 */                             if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3105 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                             }
/*      */                             
/* 3108 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3109 */                             out.write("\n     </td>\n </tr>   \n \n ");
/*      */                             
/* 3111 */                             if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                             {
/*      */ 
/* 3114 */                               out.write(32);
/* 3115 */                               out.write(32);
/* 3116 */                               out.write(10);
/*      */                               
/* 3118 */                               IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3119 */                               _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3120 */                               _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3122 */                               _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/* 3123 */                               int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3124 */                               if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                 for (;;) {
/* 3126 */                                   out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                                   
/* 3128 */                                   ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3129 */                                   _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 3130 */                                   _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_c_005fif_005f0);
/* 3131 */                                   int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 3132 */                                   if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                                     for (;;) {
/* 3134 */                                       out.write(10);
/* 3135 */                                       out.write(32);
/* 3136 */                                       out.write(9);
/*      */                                       
/* 3138 */                                       WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3139 */                                       _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3140 */                                       _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                                       
/* 3142 */                                       _jspx_th_c_005fwhen_005f7.setTest("${param.method !='listTrapListener'}");
/* 3143 */                                       int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3144 */                                       if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                         for (;;) {
/* 3146 */                                           out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 3147 */                                           out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3148 */                                           out.write("</a>\n   \t");
/* 3149 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3150 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3154 */                                       if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3155 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                                       }
/*      */                                       
/* 3158 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3159 */                                       out.write("\n   \t");
/*      */                                       
/* 3161 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3162 */                                       _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3163 */                                       _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f7);
/* 3164 */                                       int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3165 */                                       if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                         for (;;) {
/* 3167 */                                           out.write("\n  \t\t");
/* 3168 */                                           out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3169 */                                           out.write(" \n   \t");
/* 3170 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3171 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3175 */                                       if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3176 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                                       }
/*      */                                       
/* 3179 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3180 */                                       out.write("\n   \t");
/* 3181 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 3182 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3186 */                                   if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 3187 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                                   }
/*      */                                   
/* 3190 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3191 */                                   out.write("\n      </td>\n  </tr>   \n");
/* 3192 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3193 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3197 */                               if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3198 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                               }
/*      */                               
/* 3201 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3202 */                               out.write(10);
/* 3203 */                               out.write(32);
/*      */                             }
/*      */                             
/*      */ 
/* 3207 */                             out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 3209 */                             ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3210 */                             _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 3211 */                             _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/* 3212 */                             int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 3213 */                             if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                               for (;;) {
/* 3215 */                                 out.write(10);
/*      */                                 
/* 3217 */                                 WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3218 */                                 _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3219 */                                 _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                                 
/* 3221 */                                 _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showScheduleReports'}");
/* 3222 */                                 int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3223 */                                 if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                   for (;;) {
/* 3225 */                                     out.write("\n       ");
/* 3226 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3227 */                                     out.write(10);
/* 3228 */                                     out.write(32);
/* 3229 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3230 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3234 */                                 if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3235 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                 }
/*      */                                 
/* 3238 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3239 */                                 out.write(10);
/* 3240 */                                 out.write(32);
/*      */                                 
/* 3242 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3243 */                                 _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 3244 */                                 _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f8);
/* 3245 */                                 int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 3246 */                                 if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                                   for (;;) {
/* 3248 */                                     out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 3249 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3250 */                                     out.write("\n\t </a>\n ");
/* 3251 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3252 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3256 */                                 if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3257 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                                 }
/*      */                                 
/* 3260 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3261 */                                 out.write(10);
/* 3262 */                                 out.write(32);
/* 3263 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 3264 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3268 */                             if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 3269 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                             }
/*      */                             
/* 3272 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 3273 */                             out.write("\n    </td>\n</tr> \n");
/*      */                           } else {
/* 3275 */                             out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3276 */                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3277 */                             out.write("</a>\n     </td>\n </tr>   \n");
/*      */                             
/* 3279 */                             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3280 */                             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3281 */                             _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 3283 */                             _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 3284 */                             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3285 */                             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                               for (;;) {
/* 3287 */                                 out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 3288 */                                 out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3289 */                                 out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 3290 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3291 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3295 */                             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3296 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                             }
/*      */                             
/* 3299 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3300 */                             out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 3301 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3302 */                             out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                           }
/* 3304 */                           out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 3306 */                           ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3307 */                           _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 3308 */                           _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 3309 */                           int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 3310 */                           if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                             for (;;) {
/* 3312 */                               out.write(10);
/*      */                               
/* 3314 */                               WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3315 */                               _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 3316 */                               _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                               
/* 3318 */                               _jspx_th_c_005fwhen_005f9.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 3319 */                               int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 3320 */                               if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                 for (;;) {
/* 3322 */                                   out.write("\n        ");
/* 3323 */                                   out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 3324 */                                   out.write(10);
/* 3325 */                                   out.write(32);
/* 3326 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 3327 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3331 */                               if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 3332 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                               }
/*      */                               
/* 3335 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3336 */                               out.write(10);
/* 3337 */                               out.write(32);
/*      */                               
/* 3339 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3340 */                               _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 3341 */                               _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f9);
/* 3342 */                               int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 3343 */                               if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                                 for (;;) {
/* 3345 */                                   out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 3346 */                                   out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 3347 */                                   out.write("\n\t </a>\n ");
/* 3348 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 3349 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3353 */                               if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 3354 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                               }
/*      */                               
/* 3357 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 3358 */                               out.write(10);
/* 3359 */                               out.write(32);
/* 3360 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 3361 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3365 */                           if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 3366 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                           }
/*      */                           
/* 3369 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 3370 */                           out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 3372 */                           ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3373 */                           _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 3374 */                           _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 3375 */                           int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 3376 */                           if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                             for (;;) {
/* 3378 */                               out.write(10);
/*      */                               
/* 3380 */                               WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3381 */                               _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 3382 */                               _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                               
/* 3384 */                               _jspx_th_c_005fwhen_005f10.setTest("${param.method!='showMailServerConfiguration'}");
/* 3385 */                               int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 3386 */                               if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                 for (;;) {
/* 3388 */                                   out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 3389 */                                   out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 3390 */                                   out.write("\n    </a>    \n ");
/* 3391 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 3392 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3396 */                               if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 3397 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                               }
/*      */                               
/* 3400 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3401 */                               out.write(10);
/* 3402 */                               out.write(32);
/*      */                               
/* 3404 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3405 */                               _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 3406 */                               _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f10);
/* 3407 */                               int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 3408 */                               if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                                 for (;;) {
/* 3410 */                                   out.write(10);
/* 3411 */                                   out.write(9);
/* 3412 */                                   out.write(32);
/* 3413 */                                   out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 3414 */                                   out.write(10);
/* 3415 */                                   out.write(32);
/* 3416 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 3417 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3421 */                               if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 3422 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                               }
/*      */                               
/* 3425 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 3426 */                               out.write(10);
/* 3427 */                               out.write(32);
/* 3428 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 3429 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3433 */                           if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 3434 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                           }
/*      */                           
/* 3437 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 3438 */                           out.write("\n    </td>\n</tr>\n\n\n");
/* 3439 */                           if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 3440 */                             out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 3442 */                             ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3443 */                             _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 3444 */                             _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 3445 */                             int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 3446 */                             if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                               for (;;) {
/* 3448 */                                 out.write(10);
/*      */                                 
/* 3450 */                                 WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3451 */                                 _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 3452 */                                 _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                                 
/* 3454 */                                 _jspx_th_c_005fwhen_005f11.setTest("${param.method!='SMSServerConfiguration'}");
/* 3455 */                                 int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 3456 */                                 if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                                   for (;;) {
/* 3458 */                                     out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 3459 */                                     out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 3460 */                                     out.write("\n    </a>\n ");
/* 3461 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 3462 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3466 */                                 if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 3467 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                                 }
/*      */                                 
/* 3470 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 3471 */                                 out.write(10);
/* 3472 */                                 out.write(32);
/*      */                                 
/* 3474 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3475 */                                 _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 3476 */                                 _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f11);
/* 3477 */                                 int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 3478 */                                 if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                                   for (;;) {
/* 3480 */                                     out.write("\n         ");
/* 3481 */                                     out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 3482 */                                     out.write(10);
/* 3483 */                                     out.write(32);
/* 3484 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 3485 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3489 */                                 if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 3490 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                                 }
/*      */                                 
/* 3493 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 3494 */                                 out.write(10);
/* 3495 */                                 out.write(32);
/* 3496 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 3497 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3501 */                             if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 3502 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                             }
/*      */                             
/* 3505 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 3506 */                             out.write("\n    </td>\n</tr>\n");
/*      */                           }
/* 3508 */                           out.write("\n\n\n ");
/*      */                           
/* 3510 */                           if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                           {
/*      */ 
/* 3513 */                             out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                             
/* 3515 */                             ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3516 */                             _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 3517 */                             _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 3518 */                             int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 3519 */                             if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                               for (;;) {
/* 3521 */                                 out.write(10);
/*      */                                 
/* 3523 */                                 WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3524 */                                 _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 3525 */                                 _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                                 
/* 3527 */                                 _jspx_th_c_005fwhen_005f12.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 3528 */                                 int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 3529 */                                 if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                                   for (;;) {
/* 3531 */                                     out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 3532 */                                     out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 3533 */                                     out.write("\n    </a>\n ");
/* 3534 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 3535 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3539 */                                 if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 3540 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                                 }
/*      */                                 
/* 3543 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3544 */                                 out.write(10);
/* 3545 */                                 out.write(32);
/*      */                                 
/* 3547 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3548 */                                 _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 3549 */                                 _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f12);
/* 3550 */                                 int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 3551 */                                 if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                                   for (;;) {
/* 3553 */                                     out.write(10);
/* 3554 */                                     out.write(9);
/* 3555 */                                     out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 3556 */                                     out.write(10);
/* 3557 */                                     out.write(32);
/* 3558 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 3559 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3563 */                                 if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 3564 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                                 }
/*      */                                 
/* 3567 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 3568 */                                 out.write(10);
/* 3569 */                                 out.write(32);
/* 3570 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 3571 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3575 */                             if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 3576 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                             }
/*      */                             
/* 3579 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 3580 */                             out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 3582 */                             ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3583 */                             _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 3584 */                             _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 3585 */                             int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 3586 */                             if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                               for (;;) {
/* 3588 */                                 out.write(10);
/*      */                                 
/* 3590 */                                 WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3591 */                                 _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 3592 */                                 _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                                 
/* 3594 */                                 _jspx_th_c_005fwhen_005f13.setTest("${uri !='/Upload.do'}");
/* 3595 */                                 int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 3596 */                                 if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                                   for (;;) {
/* 3598 */                                     out.write("   \n        ");
/*      */                                     
/* 3600 */                                     AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3601 */                                     _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 3602 */                                     _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f13);
/*      */                                     
/* 3604 */                                     _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                                     
/* 3606 */                                     _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 3607 */                                     int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 3608 */                                     if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 3609 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3610 */                                         out = _jspx_page_context.pushBody();
/* 3611 */                                         _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 3612 */                                         _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3615 */                                         out.write("\n           ");
/* 3616 */                                         out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 3617 */                                         out.write("\n            ");
/* 3618 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 3619 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3622 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3623 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3626 */                                     if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 3627 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                     }
/*      */                                     
/* 3630 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 3631 */                                     out.write(10);
/* 3632 */                                     out.write(10);
/* 3633 */                                     out.write(32);
/* 3634 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 3635 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3639 */                                 if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 3640 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                                 }
/*      */                                 
/* 3643 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 3644 */                                 out.write(10);
/* 3645 */                                 out.write(32);
/*      */                                 
/* 3647 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3648 */                                 _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 3649 */                                 _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f13);
/* 3650 */                                 int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 3651 */                                 if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                                   for (;;) {
/* 3653 */                                     out.write(10);
/* 3654 */                                     out.write(9);
/* 3655 */                                     out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 3656 */                                     out.write(10);
/* 3657 */                                     out.write(32);
/* 3658 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 3659 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3663 */                                 if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 3664 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                                 }
/*      */                                 
/* 3667 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 3668 */                                 out.write(10);
/* 3669 */                                 out.write(32);
/* 3670 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 3671 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3675 */                             if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 3676 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                             }
/*      */                             
/* 3679 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 3680 */                             out.write("\n    </td>\n</tr>\n \n ");
/*      */                           }
/*      */                           
/*      */ 
/* 3684 */                           out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                           
/* 3686 */                           ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3687 */                           _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 3688 */                           _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 3689 */                           int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 3690 */                           if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                             for (;;) {
/* 3692 */                               out.write(10);
/*      */                               
/* 3694 */                               WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3695 */                               _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 3696 */                               _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                               
/* 3698 */                               _jspx_th_c_005fwhen_005f14.setTest("${uri !='/admin/userconfiguration.do'}");
/* 3699 */                               int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 3700 */                               if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                                 for (;;) {
/* 3702 */                                   out.write("\n    \n        ");
/*      */                                   
/* 3704 */                                   AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3705 */                                   _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 3706 */                                   _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f14);
/*      */                                   
/* 3708 */                                   _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                   
/* 3710 */                                   _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 3711 */                                   int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 3712 */                                   if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 3713 */                                     if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3714 */                                       out = _jspx_page_context.pushBody();
/* 3715 */                                       _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 3716 */                                       _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3719 */                                       out.write("\n       ");
/* 3720 */                                       out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 3721 */                                       out.write("\n        ");
/* 3722 */                                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 3723 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3726 */                                     if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3727 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3730 */                                   if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 3731 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                   }
/*      */                                   
/* 3734 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 3735 */                                   out.write(10);
/* 3736 */                                   out.write(10);
/* 3737 */                                   out.write(32);
/* 3738 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 3739 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3743 */                               if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 3744 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                               }
/*      */                               
/* 3747 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 3748 */                               out.write(10);
/* 3749 */                               out.write(32);
/*      */                               
/* 3751 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3752 */                               _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 3753 */                               _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f14);
/* 3754 */                               int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 3755 */                               if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                                 for (;;) {
/* 3757 */                                   out.write(10);
/* 3758 */                                   out.write(9);
/* 3759 */                                   out.write(32);
/* 3760 */                                   out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 3761 */                                   out.write(10);
/* 3762 */                                   out.write(32);
/* 3763 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 3764 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3768 */                               if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 3769 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                               }
/*      */                               
/* 3772 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 3773 */                               out.write(10);
/* 3774 */                               out.write(32);
/* 3775 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 3776 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3780 */                           if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 3781 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                           }
/*      */                           
/* 3784 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 3785 */                           out.write("\n    </td>\n</tr>\n   \n\n ");
/* 3786 */                           if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 3787 */                             out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                             
/* 3789 */                             ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3790 */                             _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 3791 */                             _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_tiles_005fput_005f2);
/* 3792 */                             int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 3793 */                             if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                               for (;;) {
/* 3795 */                                 out.write("\n   ");
/*      */                                 
/* 3797 */                                 WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3798 */                                 _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 3799 */                                 _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                                 
/* 3801 */                                 _jspx_th_c_005fwhen_005f15.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 3802 */                                 int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 3803 */                                 if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                   for (;;) {
/* 3805 */                                     out.write("\n    ");
/*      */                                     
/* 3807 */                                     AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3808 */                                     _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 3809 */                                     _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f15);
/*      */                                     
/* 3811 */                                     _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                                     
/* 3813 */                                     _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 3814 */                                     int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 3815 */                                     if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 3816 */                                       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3817 */                                         out = _jspx_page_context.pushBody();
/* 3818 */                                         _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 3819 */                                         _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3822 */                                         out.write(10);
/* 3823 */                                         out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 3824 */                                         out.write("\n    ");
/* 3825 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 3826 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3829 */                                       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3830 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3833 */                                     if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 3834 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                                     }
/*      */                                     
/* 3837 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 3838 */                                     out.write("\n   ");
/* 3839 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 3840 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3844 */                                 if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 3845 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                                 }
/*      */                                 
/* 3848 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 3849 */                                 out.write("\n   ");
/*      */                                 
/* 3851 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3852 */                                 _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 3853 */                                 _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f15);
/* 3854 */                                 int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 3855 */                                 if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                                   for (;;) {
/* 3857 */                                     out.write(10);
/* 3858 */                                     out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 3859 */                                     out.write("\n   ");
/* 3860 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 3861 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3865 */                                 if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 3866 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                                 }
/*      */                                 
/* 3869 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 3870 */                                 out.write(10);
/* 3871 */                                 out.write(32);
/* 3872 */                                 out.write(32);
/* 3873 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 3874 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3878 */                             if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 3879 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                             }
/*      */                             
/* 3882 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 3883 */                             out.write("\n </td>\n</tr>\n  ");
/*      */                           }
/* 3885 */                           out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                           
/* 3887 */                           ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3888 */                           _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 3889 */                           _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_tiles_005fput_005f2);
/* 3890 */                           int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 3891 */                           if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                             for (;;) {
/* 3893 */                               out.write("\n   ");
/*      */                               
/* 3895 */                               WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3896 */                               _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 3897 */                               _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */                               
/* 3899 */                               _jspx_th_c_005fwhen_005f16.setTest("${param.method!='showDataCleanUp'}");
/* 3900 */                               int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 3901 */                               if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                                 for (;;) {
/* 3903 */                                   out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 3904 */                                   out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3905 */                                   out.write("\n    </a>\n   ");
/* 3906 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 3907 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3911 */                               if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 3912 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */                               }
/*      */                               
/* 3915 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 3916 */                               out.write("\n   ");
/*      */                               
/* 3918 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3919 */                               _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 3920 */                               _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f16);
/* 3921 */                               int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 3922 */                               if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                 for (;;) {
/* 3924 */                                   out.write(10);
/* 3925 */                                   out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3926 */                                   out.write("\n   ");
/* 3927 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 3928 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3932 */                               if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 3933 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                               }
/*      */                               
/* 3936 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 3937 */                               out.write(10);
/* 3938 */                               out.write(32);
/* 3939 */                               out.write(32);
/* 3940 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 3941 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3945 */                           if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 3946 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */                           }
/*      */                           
/* 3949 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 3950 */                           out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 3951 */                           out.write(10);
/* 3952 */                           int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3953 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3956 */                         if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3957 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3960 */                       if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3961 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                       }
/*      */                       
/* 3964 */                       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3965 */                       out.write(10);
/* 3966 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3967 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3971 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3972 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/* 3975 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3976 */                   out.write(10);
/*      */                   
/* 3978 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3979 */                   _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 3980 */                   _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f0);
/* 3981 */                   int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 3982 */                   if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                     for (;;) {
/* 3984 */                       out.write(10);
/*      */                       
/* 3986 */                       PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3987 */                       _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3988 */                       _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_c_005fotherwise_005f16);
/*      */                       
/* 3990 */                       _jspx_th_tiles_005fput_005f3.setName("LeftArea");
/*      */                       
/* 3992 */                       _jspx_th_tiles_005fput_005f3.setValue(leftPage);
/* 3993 */                       int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3994 */                       if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3995 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                       }
/*      */                       
/* 3998 */                       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3999 */                       out.write(10);
/* 4000 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 4001 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4005 */                   if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 4006 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */                   }
/*      */                   
/* 4009 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 4010 */                   out.write(10);
/* 4011 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4012 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 4016 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4017 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 4020 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4021 */               out.write("\n<input type=\"hidden\" id=\"oldtab\" name=\"oldtab\" value='");
/* 4022 */               out.print(request.getParameter("oldtab"));
/* 4023 */               out.write("'/>\n");
/*      */               
/* 4025 */               PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4026 */               _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4027 */               _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */               
/* 4029 */               _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */               
/* 4031 */               _jspx_th_tiles_005fput_005f4.setType("string");
/* 4032 */               int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4033 */               if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 4034 */                 if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 4035 */                   out = _jspx_page_context.pushBody();
/* 4036 */                   _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 4037 */                   _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 4040 */                   out.write("\n\t<!--<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 4041 */                   out.print(BreadcrumbUtil.getMonitorsPage());
/* 4042 */                   out.write(" \n      &gt;<span class=\"bcactive\"> ");
/* 4043 */                   out.print(displayName);
/* 4044 */                   out.write(" </span> </td>\n    </tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"/images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"/images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n\t</table>-->\n\n\n\t");
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 4049 */                   int selectedPage = 1;
/* 4050 */                   int startIndex = 0;
/* 4051 */                   boolean isCritical = false;
/* 4052 */                   if (request.getParameter("selectedPage") != null)
/*      */                   {
/* 4054 */                     selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
/*      */                   }
/* 4056 */                   else if (request.getSession().getAttribute("selectedPage") != null)
/*      */                   {
/* 4058 */                     selectedPage = ((Integer)request.getSession().getAttribute("selectedPage")).intValue();
/*      */                   }
/*      */                   
/* 4061 */                   int noOfRows = 25;
/* 4062 */                   int tempCount = 0;
/*      */                   
/* 4064 */                   if (request.getParameter("noOfRows") != null)
/*      */                   {
/* 4066 */                     noOfRows = Integer.parseInt(request.getParameter("noOfRows"));
/*      */                   }
/* 4068 */                   else if (request.getSession().getAttribute("noOfRows") != null)
/*      */                   {
/* 4070 */                     noOfRows = ((Integer)request.getSession().getAttribute("noOfRows")).intValue();
/*      */                   }
/*      */                   
/* 4073 */                   startIndex = (selectedPage - 1) * noOfRows;
/*      */                   
/*      */ 
/*      */ 
/* 4077 */                   if (request.getParameter("mas_host") != null)
/*      */                   {
/* 4079 */                     out.write("\n\t\t<table class=\"messagebox\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" width=\"98%\">\n\t\t<tbody><tr>\n\t\t<td align=\"center\" width=\"4%\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" height=\"23\" width=\"23\">\n\t\t</td>\n\n\t\t<td class=\"message\" height=\"34\" width=\"94%\">\n\t\t");
/*      */                     
/* 4081 */                     if (!request.getParameter("serverId").equals("-1"))
/*      */                     {
/* 4083 */                       if (request.getParameter("serverId").equals("0"))
/*      */                       {
/*      */ 
/* 4086 */                         out.write("\n\t      ");
/* 4087 */                         out.print(FormatUtil.getString("am.webclient.managedserver.accessproblem.details"));
/* 4088 */                         out.write("\n\t   ");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 4093 */                         out.write("\n\t    ");
/* 4094 */                         out.print(FormatUtil.getString("am.webclient.managedserver.passwordWrong.message", new String[] { request.getParameter("mas_host"), request.getParameter("mas_port"), request.getParameter("serverId") }));
/* 4095 */                         out.write(10);
/* 4096 */                         out.write(9);
/* 4097 */                         out.write(9);
/*      */                       }
/*      */                     } else {
/* 4100 */                       out.write(10);
/* 4101 */                       out.write(9);
/* 4102 */                       out.write(9);
/* 4103 */                       out.print(FormatUtil.getString("am.webclient.managedserver.down.message", new String[] { request.getParameter("mas_host"), request.getParameter("mas_port") }));
/* 4104 */                       out.write(10);
/* 4105 */                       out.write(9);
/* 4106 */                       out.write(9);
/*      */                     }
/* 4108 */                     out.write("\n\t\t</tr>\n\t\t</tbody></table>\n\t\t<table>\n\t\t <tr>\n\t\t  <td class=\"monitorsheading\"><img src=\"../images/spacer.gif\" height=\"5\" width=\"99%\"></td>\n\t\t </tr>\n\t\t</table>\n\n   ");
/*      */                   }
/* 4110 */                   out.write(10);
/*      */                   
/* 4112 */                   String toptitle = FormatUtil.getString("am.webclient.networkdetails.title");
/* 4113 */                   if (selectedNetwork != null)
/*      */                   {
/* 4115 */                     toptitle = selectedNetwork;
/*      */                   }
/*      */                   
/* 4118 */                   out.write(10);
/* 4119 */                   out.write(10);
/*      */                   
/* 4121 */                   boolean resourceAvailable = false;
/* 4122 */                   String owner = request.getRemoteUser();
/* 4123 */                   Vector resourceids = null;
/* 4124 */                   String selectedResourceids = "";
/* 4125 */                   if (EnterpriseUtil.isIt360MSPEdition())
/*      */                   {
/* 4127 */                     resourceids = com.adventnet.appmanager.struts.beans.ClientDBUtil.getResourceIdentity(owner, request, null);
/*      */                   }
/* 4129 */                   else if (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request))
/*      */                   {
/* 4131 */                     if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 4132 */                       String loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 4133 */                       selectedResourceids = " and AM_ManagedObject.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ") ";
/*      */                     } else {
/* 4135 */                       resourceids = com.adventnet.appmanager.struts.beans.ClientDBUtil.getResourceIdentity(owner);
/* 4136 */                       selectedResourceids = " and " + DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + " ";
/*      */                     }
/*      */                   }
/*      */                   
/* 4140 */                   request.setAttribute("resourceids_owner", resourceids);
/* 4141 */                   List list1 = new ArrayList();
/* 4142 */                   if (resourceName != null)
/*      */                   {
/*      */ 
/* 4145 */                     String query = null;
/* 4146 */                     String query1 = null;
/* 4147 */                     String devicesquery = null;String devicesquery1 = null;
/* 4148 */                     String memQuery = "";
/* 4149 */                     String networkname = request.getParameter("network");
/* 4150 */                     for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                     {
/* 4152 */                       if (networkname.equals(com.adventnet.appmanager.util.Constants.categoryLink[i]))
/*      */                       {
/* 4154 */                         group = networkname;
/* 4155 */                         break;
/*      */                       }
/*      */                     }
/* 4158 */                     if (group != null) {
/* 4159 */                       String withGroup = " and AM_ManagedResourceType.RESOURCEGROUP='" + group + "'";
/* 4160 */                       if (group.equals("All"))
/*      */                       {
/*      */ 
/* 4163 */                         withGroup = "";
/*      */                         
/* 4165 */                         if (selectedTab != null)
/*      */                         {
/* 4167 */                           if (selectedTab.equals("Server"))
/*      */                           {
/* 4169 */                             withGroup = " and AM_ManagedResourceType.RESOURCEGROUP IN " + com.adventnet.appmanager.util.Constants.serverResGpTypes;
/*      */                           }
/* 4171 */                           else if (selectedTab.equals("Application"))
/*      */                           {
/* 4173 */                             withGroup = " and AM_ManagedResourceType.RESOURCEGROUP NOT IN " + com.adventnet.appmanager.util.Constants.nonApplicationResGpTypes;
/*      */                           }
/* 4175 */                           else if (selectedTab.equals("Networks"))
/*      */                           {
/* 4177 */                             withGroup = " and AM_ManagedResourceType.RESOURCEGROUP IN ('NWD') and AM_ManagedResourceType.SUBGROUP not in ('OpManager-Interface')";
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 4184 */                       if ((request.getParameter("assignCustomValues") != null) && (!request.getParameter("assignCustomValues").equals("null"))) {
/* 4185 */                         forbulkAssign = request.getParameter("assignCustomValues");
/*      */                       }
/*      */                       
/*      */ 
/* 4189 */                       if ((customValue != null) && (!customValue.equals("null")) && (forbulkAssign.trim().equals(""))) {
/*      */                         try
/*      */                         {
/* 4192 */                           if (customValue.contains("$")) {
/* 4193 */                             customCol = customValue.substring(0, customValue.indexOf("$"));
/* 4194 */                             colValue = customValue.substring(customValue.indexOf("$") + 1);
/*      */                           } else {
/* 4196 */                             customCol = customValue;
/* 4197 */                             colValue = "none";
/*      */                           }
/*      */                           
/* 4200 */                           if (customCol.indexOf("SYSTEMDATA") != -1) {
/* 4201 */                             dataTable = "AM_MYFIELDS_SYSTEMDATA,";
/* 4202 */                             if ((!colValue.equals("none")) && (!colValue.equals(""))) {
/* 4203 */                               qryCon = " and AM_MYFIELDS_SYSTEMDATA." + customCol + "='" + colValue + "' and AM_MYFIELDS_SYSTEMDATA.RESOURCEID=AM_ManagedObject.RESOURCEID";
/*      */                             } else {
/* 4205 */                               qryCon = " and AM_MYFIELDS_SYSTEMDATA." + customCol + " like '%" + queries + "%' and AM_MYFIELDS_SYSTEMDATA.RESOURCEID=AM_ManagedObject.RESOURCEID";
/*      */                             }
/*      */                           }
/* 4208 */                           else if (customCol.indexOf("USERDATA") != -1) {
/* 4209 */                             dataTable = "AM_MYFIELDS_USERDATA,";
/* 4210 */                             if ((!colValue.equals("none")) && (!colValue.equals(""))) {
/* 4211 */                               qryCon = " and AM_MYFIELDS_USERDATA." + customCol + "='" + colValue + "' and AM_MYFIELDS_USERDATA.RESOURCEID=AM_ManagedObject.RESOURCEID";
/*      */                             } else {
/* 4213 */                               qryCon = " and AM_MYFIELDS_USERDATA." + customCol + " like '%" + queries + "%' and AM_MYFIELDS_USERDATA.RESOURCEID=AM_ManagedObject.RESOURCEID";
/*      */                             }
/* 4215 */                           } else if (customCol.equals("LOCATION_NAME"))
/*      */                           {
/* 4217 */                             dataTable = "AM_MYFIELDS_LOCATION,AM_MYFIELDS_ENTITYDATA,";
/* 4218 */                             qryCon = " and AM_ManagedObject.RESOURCEID=AM_MYFIELDS_ENTITYDATA.RESOURCEID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION' and VALUEID=LOCATIONID and LOCATIONID=" + colValue;
/*      */                           }
/* 4220 */                           else if (customCol.equals("USERNAME"))
/*      */                           {
/* 4222 */                             dataTable = "AM_UserPasswordTable,AM_MYFIELDS_ENTITYDATA,";
/* 4223 */                             qryCon = " and AM_ManagedObject.RESOURCEID=AM_MYFIELDS_ENTITYDATA.RESOURCEID and DATATABLE='AM_UserPasswordTable' and AM_MYFIELDS_ENTITYDATA.VALUEID=AM_UserPasswordTable.USERID and USERID=" + colValue;
/* 4224 */                           } else if (customCol.equals("VALUEID"))
/*      */                           {
/* 4226 */                             dataTable = "AM_MYFIELDS_LABELDATA,";
/* 4227 */                             qryCon = " and AM_ManagedObject.RESOURCEID=AM_MYFIELDS_LABELDATA.RESOURCEID and VALUEID=" + colValue;
/*      */                           }
/* 4229 */                           qryCon = qryCon + selectedResourceids;
/*      */                         }
/*      */                         catch (Exception ex)
/*      */                         {
/* 4233 */                           ex.printStackTrace();
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 4239 */                       out.write(10);
/* 4240 */                       out.write(9);
/* 4241 */                       out.write(9);
/*      */                       
/* 4243 */                       ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4244 */                       _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 4245 */                       _jspx_th_c_005fchoose_005f17.setParent(_jspx_th_tiles_005fput_005f4);
/* 4246 */                       int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 4247 */                       if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */                         for (;;) {
/* 4249 */                           out.write("\n\t\t\t");
/*      */                           
/* 4251 */                           WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4252 */                           _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 4253 */                           _jspx_th_c_005fwhen_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/*      */                           
/* 4255 */                           _jspx_th_c_005fwhen_005f17.setTest("${ empty param.selectedNetwork }");
/* 4256 */                           int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 4257 */                           if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */                             for (;;) {
/* 4259 */                               out.write("\n\t\t\t");
/*      */                               
/* 4261 */                               PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4262 */                               _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4263 */                               _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f17);
/*      */                               
/* 4265 */                               _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 4266 */                               int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4267 */                               if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                 for (;;) {
/* 4269 */                                   out.write("\n\t\t\t");
/* 4270 */                                   query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,AM_ManagedObject.resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from " + dataTable + " AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE " + eumCondition + selectedResourceids + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + qryCon;
/* 4271 */                                   if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                   {
/* 4273 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE " + eumCondition + " and AM_UnManagedNodes.resid is null " + selectedResourceids + " and AM_ManagedResourceType.RESOURCETYPE in " + types;
/*      */                                   }
/* 4275 */                                   else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                   {
/*      */ 
/* 4278 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE " + eumCondition + selectedResourceids + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in  " + types;
/*      */                                   }
/* 4280 */                                   else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("critical")))
/*      */                                   {
/* 4282 */                                     isCritical = true;
/* 4283 */                                     request.setAttribute("isCritical", "true");
/* 4284 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,AM_ManagedObject.resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH,MODTIME from " + dataTable + " AM_ManagedObject , AM_ManagedResourceType,Alert where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE " + eumCondition + selectedResourceids + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + qryCon + " and Alert.source=AM_ManagedObject.resourceid and Alert.groupname='AppManager' and Alert.severity!=5 order by MODTIME desc";
/*      */                                   }
/*      */                                   
/* 4287 */                                   out.write("\n\t\t\t");
/* 4288 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4289 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4293 */                               if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4294 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                               }
/*      */                               
/* 4297 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4298 */                               out.write("\n\t\t\t");
/*      */                               
/* 4300 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4301 */                               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 4302 */                               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fwhen_005f17);
/*      */                               
/* 4304 */                               _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 4305 */                               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 4306 */                               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                 for (;;) {
/* 4308 */                                   out.write("\n\t\t\t");
/*      */                                   
/* 4310 */                                   if ((dataTable == null) || (dataTable.trim().equals(""))) {
/* 4311 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,AM_ManagedObject.resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType inner join AM_ManagedObject on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type " + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + qryCon + it360SPCondition;
/*      */                                   }
/*      */                                   else {
/* 4314 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,AM_ManagedObject.resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from " + dataTable + " AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE" + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + qryCon + it360SPCondition;
/*      */                                   }
/* 4316 */                                   if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                   {
/* 4318 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null" + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + "" + selectedResourceids + it360SPCondition;
/*      */                                   }
/* 4320 */                                   else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                   {
/* 4322 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE" + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + "" + selectedResourceids + it360SPCondition;
/*      */                                   }
/* 4324 */                                   else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("critical")))
/*      */                                   {
/* 4326 */                                     isCritical = true;
/* 4327 */                                     request.setAttribute("isCritical", "true");
/* 4328 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,AM_ManagedObject.resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH,MODTIME from AM_ManagedResourceType,AM_ManagedObject,Alert where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type " + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + qryCon + it360SPCondition + "  and Alert.source=AM_ManagedObject.resourceid and Alert.groupname='AppManager' and Alert.severity!=5 order by MODTIME desc";
/*      */                                   }
/*      */                                   
/*      */ 
/* 4332 */                                   out.write("\n\t\t\t");
/* 4333 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 4334 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4338 */                               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 4339 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                               }
/*      */                               
/* 4342 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4343 */                               out.write("\n\t\t\t");
/* 4344 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 4345 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4349 */                           if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 4350 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17); return;
/*      */                           }
/*      */                           
/* 4353 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 4354 */                           out.write("\n\t\t\t");
/*      */                           
/* 4356 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4357 */                           _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 4358 */                           _jspx_th_c_005fotherwise_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/* 4359 */                           int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 4360 */                           if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */                             for (;;) {
/* 4362 */                               out.write("\n                        ");
/* 4363 */                               if (group.equals("SYS")) {
/* 4364 */                                 out.write("\n                            ");
/*      */                                 
/* 4366 */                                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4367 */                                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 4368 */                                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fotherwise_005f17);
/*      */                                 
/* 4370 */                                 _jspx_th_logic_005fpresent_005f1.setRole("OPERATOR");
/* 4371 */                                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 4372 */                                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 4374 */                                     out.write("\n                                ");
/* 4375 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, IpAddress  where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + withGroup + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "' ";
/* 4376 */                                     devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, InetService, IpAddress where AM_ManagedResourceType.RESOURCETYPE in " + types + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + selectedResourceids + withGroup + "  " + eumCondition + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/* 4377 */                                     if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                     {
/* 4379 */                                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null" + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + "  " + eumCondition + selectedResourceids + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4380 */                                       devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, InetService, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + "  " + eumCondition + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_UnManagedNodes.resid is null" + selectedResourceids + withGroup + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/* 4382 */                                     else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                     {
/*      */ 
/* 4385 */                                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE" + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4386 */                                       devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, InetService, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + selectedResourceids + withGroup + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/*      */                                     
/* 4389 */                                     out.write("\n                                ");
/* 4390 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 4391 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4395 */                                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 4396 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 4399 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4400 */                                 out.write("\n                                ");
/*      */                                 
/* 4402 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4403 */                                 _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 4404 */                                 _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fotherwise_005f17);
/*      */                                 
/* 4406 */                                 _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/* 4407 */                                 int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 4408 */                                 if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 4410 */                                     out.write("\n                                ");
/* 4411 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, IpAddress  where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + withGroup + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'" + it360SPCondition;
/* 4412 */                                     devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, InetService, IpAddress where AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + withGroup + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' " + it360SPCondition + " order by AM_ManagedObject.displayname";
/* 4413 */                                     if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                     {
/* 4415 */                                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null" + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'" + it360SPCondition;
/* 4416 */                                       devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, InetService, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_UnManagedNodes.resid is null" + withGroup + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' " + it360SPCondition + " order by AM_ManagedObject.displayname";
/*      */                                     }
/* 4418 */                                     else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                     {
/* 4420 */                                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE" + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'" + it360SPCondition;
/* 4421 */                                       devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, InetService, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + withGroup + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' " + it360SPCondition + " order by AM_ManagedObject.displayname";
/*      */                                     }
/*      */                                     
/* 4424 */                                     out.write("\n                                ");
/* 4425 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 4426 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4430 */                                 if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 4431 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 4434 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 4435 */                                 out.write("\n                               ");
/*      */                               } else {
/* 4437 */                                 out.write("\n                               ");
/*      */                                 
/* 4439 */                                 PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4440 */                                 _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4441 */                                 _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fotherwise_005f17);
/*      */                                 
/* 4443 */                                 _jspx_th_logic_005fpresent_005f2.setRole("OPERATOR");
/* 4444 */                                 int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4445 */                                 if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                   for (;;) {
/* 4447 */                                     out.write("\n                                ");
/* 4448 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, InetService, IpAddress  where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + withGroup + " and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4449 */                                     devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, IpAddress where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + withGroup + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/* 4450 */                                     if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                     {
/* 4452 */                                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, InetService, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null" + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + " and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4453 */                                       devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + withGroup + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/* 4455 */                                     else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                     {
/* 4457 */                                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, InetService, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE" + withGroup + " and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + " and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4458 */                                       devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + withGroup + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "'  order by AM_ManagedObject.displayname";
/*      */                                     }
/*      */                                     
/* 4461 */                                     out.write("\n\n                                ");
/* 4462 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4463 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4467 */                                 if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4468 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                 }
/*      */                                 
/* 4471 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4472 */                                 out.write("\n                                ");
/*      */                                 
/* 4474 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4475 */                                 _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 4476 */                                 _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fotherwise_005f17);
/*      */                                 
/* 4478 */                                 _jspx_th_logic_005fnotPresent_005f2.setRole("OPERATOR");
/* 4479 */                                 int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 4480 */                                 if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                   for (;;) {
/* 4482 */                                     out.write("\n                               ");
/* 4483 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, InetService, IpAddress  where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + withGroup + " and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'" + it360SPCondition;
/* 4484 */                                     devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, IpAddress where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + withGroup + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' " + it360SPCondition + " order by AM_ManagedObject.displayname";
/* 4485 */                                     if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                     {
/* 4487 */                                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, InetService, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null" + withGroup + selectedResourceids + " and AM_ManagedResourceType.RESOURCETYPE in " + types + " and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'" + it360SPCondition;
/* 4488 */                                       devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + withGroup + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' " + it360SPCondition + " order by AM_ManagedObject.displayname";
/*      */                                     }
/* 4490 */                                     else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                     {
/* 4492 */                                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, InetService, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE" + withGroup + selectedResourceids + " and AM_ManagedResourceType.RESOURCETYPE in " + types + " and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'" + it360SPCondition;
/* 4493 */                                       devicesquery = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + withGroup + selectedResourceids + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' " + it360SPCondition + " order by AM_ManagedObject.displayname";
/*      */                                     }
/*      */                                     
/* 4496 */                                     out.write("\n\n                                 ");
/* 4497 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 4498 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4502 */                                 if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 4503 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                 }
/*      */                                 
/* 4506 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 4507 */                                 out.write("\n                                 ");
/*      */                               }
/*      */                               
/* 4510 */                               out.write("\n\t\t\t");
/* 4511 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 4512 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4516 */                           if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 4517 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17); return;
/*      */                           }
/*      */                           
/* 4520 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 4521 */                           out.write(10);
/* 4522 */                           out.write(9);
/* 4523 */                           out.write(9);
/* 4524 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 4525 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4529 */                       if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 4530 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17); return;
/*      */                       }
/*      */                       
/* 4533 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 4534 */                       out.write("\n        ");
/*      */                     } else {
/* 4536 */                       out.write(10);
/* 4537 */                       out.write(9);
/* 4538 */                       out.write(9);
/*      */                       
/* 4540 */                       ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4541 */                       _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 4542 */                       _jspx_th_c_005fchoose_005f18.setParent(_jspx_th_tiles_005fput_005f4);
/* 4543 */                       int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 4544 */                       if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */                         for (;;) {
/* 4546 */                           out.write("\n\t\t\t");
/*      */                           
/* 4548 */                           WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4549 */                           _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 4550 */                           _jspx_th_c_005fwhen_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/*      */                           
/* 4552 */                           _jspx_th_c_005fwhen_005f18.setTest("${ empty param.selectedNetwork }");
/* 4553 */                           int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 4554 */                           if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */                             for (;;) {
/* 4556 */                               out.write("\n\t\t\t");
/*      */                               
/* 4558 */                               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4559 */                               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 4560 */                               _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fwhen_005f18);
/*      */                               
/* 4562 */                               _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/* 4563 */                               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 4564 */                               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                 for (;;) {
/* 4566 */                                   out.write("\n\t\t\t");
/* 4567 */                                   query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "'";
/* 4568 */                                   if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                   {
/* 4570 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE " + eumCondition + " and AM_UnManagedNodes.resid is null " + selectedResourceids + " and AM_ManagedResourceType.RESOURCETYPE in " + types + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "'";
/*      */                                   }
/* 4572 */                                   else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                   {
/* 4574 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE " + eumCondition + selectedResourceids + " and AM_ManagedResourceType.RESOURCETYPE in " + types + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "'";
/*      */                                   }
/* 4576 */                                   else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("critical")))
/*      */                                   {
/* 4578 */                                     isCritical = true;
/* 4579 */                                     request.setAttribute("isCritical", "true");
/* 4580 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE,MODTIME from AM_ManagedObject , AM_ManagedResourceType,Alert where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and Alert.source=AM_ManagedObject.resourceid and Alert.groupname='AppManager' and Alert.severity!=5 order by MODTIME desc";
/*      */                                   }
/*      */                                   
/* 4583 */                                   out.write("\n\t\t\t");
/* 4584 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 4585 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4589 */                               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 4590 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                               }
/*      */                               
/* 4593 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 4594 */                               out.write("\n\t\t\t");
/*      */                               
/* 4596 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4597 */                               _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 4598 */                               _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fwhen_005f18);
/*      */                               
/* 4600 */                               _jspx_th_logic_005fnotPresent_005f3.setRole("OPERATOR");
/* 4601 */                               int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 4602 */                               if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                 for (;;) {
/* 4604 */                                   out.write("\n\t\t\t");
/* 4605 */                                   query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "'" + it360SPCondition;
/* 4606 */                                   if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                   {
/* 4608 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition;
/*      */                                   }
/* 4610 */                                   else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                   {
/* 4612 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition;
/*      */                                   }
/* 4614 */                                   else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("critical")))
/*      */                                   {
/* 4616 */                                     isCritical = true;
/* 4617 */                                     request.setAttribute("isCritical", "true");
/* 4618 */                                     query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE,MODTIME from AM_ManagedObject , AM_ManagedResourceType,Alert where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "'" + it360SPCondition + " and Alert.source=AM_ManagedObject.resourceid and Alert.groupname='AppManager' and Alert.severity!=5 order by MODTIME desc";
/*      */                                   }
/*      */                                   
/* 4621 */                                   out.write("\n\t\t\t");
/* 4622 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 4623 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4627 */                               if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 4628 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                               }
/*      */                               
/* 4631 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 4632 */                               out.write("\n\t\t\t");
/* 4633 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 4634 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4638 */                           if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 4639 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18); return;
/*      */                           }
/*      */                           
/* 4642 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 4643 */                           out.write("\n\t\t\t");
/*      */                           
/* 4645 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4646 */                           _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 4647 */                           _jspx_th_c_005fotherwise_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/* 4648 */                           int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 4649 */                           if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */                             for (;;) {
/* 4651 */                               out.write("\n                        ");
/* 4652 */                               if ((resourceName.equals("Windows")) || (resourceName.equals("Linux")) || (resourceName.equals("Unknown")) || (resourceName.equals("Sun Solaris"))) {
/* 4653 */                                 out.write("\n                             ");
/*      */                                 
/* 4655 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4656 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 4657 */                                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fotherwise_005f18);
/*      */                                 
/* 4659 */                                 _jspx_th_logic_005fpresent_005f4.setRole("OPERATOR");
/* 4660 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 4661 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 4663 */                                     out.write("\n\t\t\t    ");
/* 4664 */                                     query = "select AM_ManagedObject.resourcename,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType, IpAddress where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + "  and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4665 */                                     devicesquery = "select AM_ManagedObject.resourcename,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType, InetService, IpAddress where AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/* 4666 */                                     if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                     {
/* 4668 */                                       query = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + "  and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4669 */                                       devicesquery = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, InetService, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + " and AM_UnManagedNodes.resid is null and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/* 4671 */                                     else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                     {
/* 4673 */                                       query = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4674 */                                       devicesquery = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, InetService, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/*      */                                     
/* 4677 */                                     out.write("\n                            ");
/* 4678 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 4679 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4683 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 4684 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 4687 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4688 */                                 out.write("\n                            ");
/*      */                                 
/* 4690 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4691 */                                 _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 4692 */                                 _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_c_005fotherwise_005f18);
/*      */                                 
/* 4694 */                                 _jspx_th_logic_005fnotPresent_005f4.setRole("OPERATOR");
/* 4695 */                                 int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 4696 */                                 if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 4698 */                                     out.write("\n                             ");
/* 4699 */                                     query = "select AM_ManagedObject.resourcename,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType, IpAddress where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4700 */                                     devicesquery = "select AM_ManagedObject.resourcename,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType, InetService, IpAddress where AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/* 4701 */                                     if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                     {
/* 4703 */                                       query = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "'" + it360SPCondition + "  and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4704 */                                       devicesquery = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, InetService, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/* 4706 */                                     else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                     {
/* 4708 */                                       query = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4709 */                                       devicesquery = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, InetService, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/*      */                                     
/* 4712 */                                     out.write("\n                                ");
/* 4713 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 4714 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4718 */                                 if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 4719 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 4722 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 4723 */                                 out.write("\n                               ");
/*      */                               } else {
/* 4725 */                                 out.write("\n                                ");
/*      */                                 
/* 4727 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4728 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 4729 */                                 _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fotherwise_005f18);
/*      */                                 
/* 4731 */                                 _jspx_th_logic_005fpresent_005f5.setRole("OPERATOR");
/* 4732 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 4733 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 4735 */                                     out.write("\n                                ");
/* 4736 */                                     query = "select AM_ManagedObject.resourcename,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType,InetService,IpAddress where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + "  and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4737 */                                     devicesquery = "select AM_ManagedObject.resourcename,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType,IpAddress where AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/* 4738 */                                     if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                     {
/* 4740 */                                       query = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, InetService, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + selectedResourceids + "  and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4741 */                                       devicesquery = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + " and AM_UnManagedNodes.resid is null and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/* 4743 */                                     else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                     {
/* 4745 */                                       query = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, InetService, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + " and " + DependantMOUtil.getCondition("RESOURCEID", resourceids) + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4746 */                                       devicesquery = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + " " + eumCondition + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE" + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/*      */                                     
/* 4749 */                                     out.write("\n                                  ");
/* 4750 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 4751 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4755 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 4756 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 4759 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4760 */                                 out.write("\n                                   ");
/*      */                                 
/* 4762 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4763 */                                 _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 4764 */                                 _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_c_005fotherwise_005f18);
/*      */                                 
/* 4766 */                                 _jspx_th_logic_005fnotPresent_005f5.setRole("OPERATOR");
/* 4767 */                                 int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 4768 */                                 if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 4770 */                                     out.write("\n                                ");
/* 4771 */                                     query = "select AM_ManagedObject.resourcename,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType,InetService,IpAddress where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4772 */                                     devicesquery = "select AM_ManagedObject.resourcename,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedObject , AM_ManagedResourceType,IpAddress where AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/* 4773 */                                     if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                     {
/* 4775 */                                       query = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, InetService, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4776 */                                       devicesquery = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/* 4778 */                                     else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                     {
/* 4780 */                                       query = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, InetService, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.resourcename=InetService.name and InetService.interfacename=IpAddress.name and IpAddress.parentnet='" + selectedNetwork + "'";
/* 4781 */                                       devicesquery = "select AM_ManagedObject.resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE from AM_ManagedResourceType, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedResourceType.RESOURCETYPE in " + types + selectedResourceids + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP='" + resourceName + "' " + it360SPCondition + " and AM_ManagedObject.resourcename=IpAddress.parentnode and IpAddress.PARENTNET='" + selectedNetwork + "' order by AM_ManagedObject.displayname";
/*      */                                     }
/* 4783 */                                     out.write("\n                                        ");
/* 4784 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 4785 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4789 */                                 if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 4790 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 4793 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 4794 */                                 out.write("\n                           ");
/*      */                               }
/* 4796 */                               out.write("\n\t\t\t");
/* 4797 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 4798 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4802 */                           if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 4803 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18); return;
/*      */                           }
/*      */                           
/* 4806 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 4807 */                           out.write(10);
/* 4808 */                           out.write(9);
/* 4809 */                           out.write(9);
/* 4810 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 4811 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4815 */                       if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 4816 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18); return;
/*      */                       }
/*      */                       
/* 4819 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 4820 */                       out.write(10);
/* 4821 */                       out.write(9);
/* 4822 */                       out.write(9);
/*      */                     }
/*      */                     
/* 4825 */                     if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*      */                     {
/* 4827 */                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.RESOURCEID in (" + request.getAttribute("searchresourceids") + ") " + it360SPCondition;
/*      */                     }
/*      */                     
/* 4830 */                     if ((request.isUserInRole("DEMO")) && ("NWD".equalsIgnoreCase(group))) {
/* 4831 */                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,AM_ManagedObject.resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType inner join AM_ManagedObject on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type and AM_ManagedResourceType.RESOURCEGROUP='NWD' and (UPPER(AM_ManagedResourceType.RESOURCETYPE) like 'OPMANAGER%') AND AM_ManagedObject.RESOURCEID NOT IN (-1,-1)";
/*      */                     }
/* 4833 */                     else if ((request.isUserInRole("DEMO")) && ("SAN".equalsIgnoreCase(group))) {
/* 4834 */                       query = "select resourcename ,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,AM_ManagedObject.resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType inner join AM_ManagedObject on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.type and AM_ManagedResourceType.RESOURCEGROUP='SAN' and (UPPER(AM_ManagedResourceType.RESOURCETYPE) like 'OPSTOR%') AND AM_ManagedObject.RESOURCEID NOT IN (-1,-1)";
/*      */                     }
/* 4836 */                     String totalDevicesQuery = DBUtil.getCountQuery(devicesquery);
/* 4837 */                     String totalmonitorcountsqry = "";
/* 4838 */                     totalmonitorcountsqry = DBUtil.getCountQuery(query);
/*      */                     
/* 4840 */                     countqueries.add(totalmonitorcountsqry);
/* 4841 */                     request.setAttribute("totalmonitors", query);
/*      */                     
/*      */ 
/* 4844 */                     String sortBy = request.getParameter("sortBy");
/* 4845 */                     String orderBy = request.getParameter("orderBy");
/*      */                     
/* 4847 */                     if (!isCritical)
/*      */                     {
/* 4849 */                       if (("displayName".equals(sortBy)) && ("desc".equals(orderBy))) {
/* 4850 */                         query = query + " order by AM_ManagedObject.displayname desc ";
/* 4851 */                       } else if (("type".equals(sortBy)) && ("desc".equals(orderBy))) {
/* 4852 */                         if (DBQueryUtil.isPgsql()) {
/* 4853 */                           query = query + " order by lower(AM_ManagedObject.type) desc ";
/*      */                         } else {
/* 4855 */                           query = query + " order by AM_ManagedObject.type desc ";
/*      */                         }
/* 4857 */                       } else if (("type".equals(sortBy)) && ("asc".equals(orderBy))) {
/* 4858 */                         if (DBQueryUtil.isPgsql()) {
/* 4859 */                           query = query + " order by lower(AM_ManagedObject.type) asc ";
/*      */                         } else {
/* 4861 */                           query = query + " order by AM_ManagedObject.type asc ";
/*      */                         }
/*      */                       } else {
/* 4864 */                         query = query + " order by AM_ManagedObject.displayname";
/*      */                       }
/*      */                     }
/*      */                     
/* 4868 */                     if ((noOfRows > 0) && (startIndex != -1) && (noOfRows != -1))
/*      */                     {
/* 4870 */                       if (isCritical) {
/* 4871 */                         query = DBQueryUtil.addLimit(query, startIndex, noOfRows, "Alert.modtime");
/*      */                       } else {
/* 4873 */                         query = DBQueryUtil.addLimit(query, startIndex, noOfRows, "AM_ManagedObject.DISPLAYNAME");
/*      */                       }
/*      */                     }
/*      */                     
/* 4877 */                     List list = mo.getRows(query);
/* 4878 */                     int baseCount = 0;
/* 4879 */                     int baseCount1 = 0;
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4885 */                     list1 = (ArrayList)((ArrayList)list).clone();
/* 4886 */                     ResultSet rsMg = null;
/* 4887 */                     Vector resids = new Vector();
/* 4888 */                     String cons = "";
/*      */                     
/* 4890 */                     if ((customValue != null) && (!customValue.equals("null")))
/*      */                     {
/*      */ 
/* 4893 */                       if (customCol.indexOf("SYSTEMDATA") != -1)
/*      */                       {
/* 4895 */                         dataTable = "AM_MYFIELDS_SYSTEMDATA";
/* 4896 */                         cons = "and " + dataTable + ".RESOURCEID IS NULL";
/* 4897 */                       } else if (customCol.indexOf("USERDATA") != -1)
/*      */                       {
/* 4899 */                         dataTable = "AM_MYFIELDS_USERDATA";
/* 4900 */                         cons = "and " + dataTable + ".RESOURCEID IS NULL";
/* 4901 */                       } else if (customCol.equals("LOCATION_NAME"))
/*      */                       {
/* 4903 */                         dataTable = "AM_MYFIELDS_ENTITYDATA";
/* 4904 */                         cons = "and AM_ManagedObject.RESOURCEID NOT IN (select RESOURCEID from AM_MYFIELDS_ENTITYDATA where DATATABLE='AM_MYFIELDS_LOCATION')";
/* 4905 */                         qryCon = " and AM_ManagedObject.RESOURCEID=AM_MYFIELDS_ENTITYDATA.RESOURCEID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION' and AM_MYFIELDS_ENTITYDATA.VALUEID=" + colValue;
/*      */                       }
/* 4907 */                       else if (customCol.equals("USERNAME"))
/*      */                       {
/* 4909 */                         dataTable = "AM_MYFIELDS_ENTITYDATA";
/* 4910 */                         cons = "and AM_ManagedObject.RESOURCEID NOT IN (select RESOURCEID from AM_MYFIELDS_ENTITYDATA where DATATABLE='AM_UserPasswordTable ')";
/* 4911 */                         qryCon = " and AM_ManagedObject.RESOURCEID=AM_MYFIELDS_ENTITYDATA.RESOURCEID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_UserPasswordTable' and AM_MYFIELDS_ENTITYDATA.VALUEID=" + colValue;
/* 4912 */                       } else if (customCol.equals("VALUEID"))
/*      */                       {
/* 4914 */                         dataTable = "AM_MYFIELDS_LABELDATA";
/* 4915 */                         cons = "and AM_ManagedObject.RESOURCEID NOT IN (select RESOURCEID from AM_MYFIELDS_LABELDATA where VALUEID=" + colValue + ")";
/* 4916 */                         qryCon = " and AM_ManagedObject.RESOURCEID=AM_MYFIELDS_LABELDATA.RESOURCEID and AM_MYFIELDS_LABELDATA.VALUEID=" + colValue;
/*      */                       }
/*      */                       
/* 4919 */                       qryCon = qryCon + selectedResourceids;
/*      */                       try {
/* 4921 */                         String mgQuery = "select AM_ManagedObject.RESOURCEID from " + dataTable + ", AM_ManagedObject where AM_ManagedObject.type='HAI' " + qryCon;
/*      */                         
/* 4923 */                         rsMg = AMConnectionPool.executeQueryStmt(mgQuery);
/* 4924 */                         while (rsMg.next())
/*      */                         {
/*      */ 
/* 4927 */                           mo.getAllChildsinTree(resids, rsMg.getString("RESOURCEID"));
/*      */                         }
/*      */                         
/* 4930 */                         memQuery = "select RESOURCENAME,-1 as abc, 0 as def , AM_ManagedResourceType.SHORTNAME ,AM_ManagedObject.resourceid as resid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType,AM_ManagedObject left join " + dataTable + "  on AM_ManagedObject.RESOURCEID=" + dataTable + ".RESOURCEID where " + DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resids) + " " + cons + "  and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE not in ('Node','Network','HAI')";
/*      */                       }
/*      */                       catch (Exception ex)
/*      */                       {
/* 4934 */                         ex.printStackTrace();
/*      */                       }
/*      */                       finally {
/* 4937 */                         if (rsMg != null) {
/* 4938 */                           AMConnectionPool.closeStatement(rsMg);
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 4945 */                       tempCount = list.size();
/*      */                       
/* 4947 */                       String totalmemQuery1 = DBUtil.getCountQuery(memQuery);
/*      */                       
/* 4949 */                       int totalMemRows = DBUtil.getCount(totalmemQuery1);
/* 4950 */                       if ((totalmemQuery1 != null) && (totalmemQuery1.trim().length() > 1)) {
/* 4951 */                         countqueries.add(totalmemQuery1);
/*      */                       }
/* 4953 */                       if ((memQuery != null) && (tempCount < noOfRows))
/*      */                       {
/* 4955 */                         startIndex -= baseCount;
/* 4956 */                         if ((startIndex != -1) && (noOfRows != -1) && (totalMemRows > noOfRows))
/*      */                         {
/* 4958 */                           int modifiedRowCount = noOfRows - tempCount;
/* 4959 */                           memQuery = DBQueryUtil.addLimit(memQuery, startIndex, modifiedRowCount, "AM_ManagedObject.DISPLAYNAME");
/*      */                         }
/*      */                         
/* 4962 */                         Object monitorsUnderMg = mo.getRows(memQuery);
/* 4963 */                         if (monitorsUnderMg != null)
/*      */                         {
/* 4965 */                           list.addAll((java.util.Collection)monitorsUnderMg);
/*      */                         }
/* 4967 */                         baseCount += totalMemRows;
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 4973 */                     tempCount = list.size();
/*      */                     
/* 4975 */                     if ((devicesquery != null) && (tempCount < noOfRows))
/*      */                     {
/* 4977 */                       int totalDeviceRows = DBUtil.getCount(totalDevicesQuery);
/* 4978 */                       countqueries.add(totalDevicesQuery);
/* 4979 */                       startIndex -= baseCount;
/* 4980 */                       if ((startIndex != -1) && (noOfRows != -1) && (totalDeviceRows > noOfRows))
/*      */                       {
/* 4982 */                         int modifiedRowCount = noOfRows - tempCount;
/* 4983 */                         devicesquery = DBQueryUtil.addLimit(devicesquery, startIndex, modifiedRowCount, "AM_ManagedObject.DISPLAYNAME");
/*      */                       }
/* 4985 */                       List devicesList = mo.getRows(devicesquery);
/* 4986 */                       if (devicesList != null)
/*      */                       {
/* 4988 */                         list.addAll(devicesList);
/*      */                       }
/* 4990 */                       baseCount += totalDeviceRows;
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5051 */                     request.getSession().setAttribute("totalcountqueries", countqueries);
/* 5052 */                     request.setAttribute("resourcetable", list);
/* 5053 */                     resourceAvailable = list.size() > 0;
/*      */                     
/* 5055 */                     if ((resourceAvailable) && 
/* 5056 */                       (!com.adventnet.appmanager.util.Constants.sqlManager)) {
/* 5057 */                       title = "<span class=bodytext height=22 valign=top>" + BreadcrumbUtil.getMonitorsPage() + "</span><span class=bodytext> &gt;</span><span class=bodytext> " + displayName + "</span> - " + title;
/*      */                     }
/*      */                     
/*      */ 
/* 5061 */                     out.write(10);
/*      */                     
/* 5063 */                     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5064 */                     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5065 */                     _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                     
/* 5067 */                     _jspx_th_c_005fif_005f2.setTest("${param.search!='true'}");
/* 5068 */                     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5069 */                     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                       for (;;) {
/* 5071 */                         out.write(10);
/* 5072 */                         out.write("<!--$Id$-->\n\n<script>\nvar urlredirect = new Array();\nurlredirect[0] = '/showresource.do?method=showResourceTypesAll&group=All");
/* 5073 */                         out.print(toAppendLink);
/* 5074 */                         out.write("';\n</script>\n");
/*      */                         
/* 5076 */                         if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 5077 */                           out.write("\n  <script>\n     urlredirect[0]='showresource.do?method=showResourceTypes&detailspage=true&network=MSSQL-DB-server&viewmontype=MSSQL-DB-server");
/* 5078 */                           out.print(toAppendLink);
/* 5079 */                           out.write("';\n </script>\n ");
/*      */                         }
/*      */                         
/* 5082 */                         out.write("\n <script>\nurlredirect[1] = '/showresource.do?method=showResourceTypes");
/* 5083 */                         out.print(toAppendLink);
/* 5084 */                         out.write("&monitor_viewtype=categoryview';\nurlredirect[2] = '/showresource.do?method=showPlasmaView';\nurlredirect[3] = '/showresource.do?method=showMonitorGroupView';\nurlredirect[4] = '/showresource.do?method=showGMapView&group=All");
/* 5085 */                         out.print(toAppendLink);
/* 5086 */                         out.write("';\nurlredirect[5] = '/showresource.do?method=showIconsView");
/* 5087 */                         out.print(toAppendLink);
/* 5088 */                         out.write("';\nurlredirect[6] = '/showresource.do?method=showDetailsView");
/* 5089 */                         out.print(toAppendLink);
/* 5090 */                         out.write("';\n\n</script>\n\n\n\n\n");
/*      */                         
/* 5092 */                         FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 5093 */                         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 5094 */                         _jspx_th_html_005fform_005f0.setParent(_jspx_th_c_005fif_005f2);
/*      */                         
/* 5096 */                         _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */                         
/* 5098 */                         _jspx_th_html_005fform_005f0.setStyle("display :inline");
/* 5099 */                         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 5100 */                         if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                           for (;;) {
/* 5102 */                             out.write(10);
/* 5103 */                             if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 5105 */                             out.write(10);
/* 5106 */                             if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                               return;
/* 5108 */                             out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"62%\" height=\"35\" class=\"monitorsheading\">\n      <table width=\"100%\">\n      <tr>\n        <td  height=\"35\"   width=\"70%\" class=\"monitorsheading\"> ");
/* 5109 */                             out.print(title);
/* 5110 */                             out.write(" </td>\n        <td  height=\"35\" class=\"monitorsheading\"  style=\"padding-left:2px\">\n    ");
/* 5111 */                             String CategoryViewtype = (String)request.getAttribute("categorytype");
/* 5112 */                             if (CategoryViewtype == null) {
/* 5113 */                               CategoryViewtype = "";
/*      */                             }
/* 5115 */                             String monitorviewtype = (String)request.getAttribute("monitor_viewtype");
/* 5116 */                             if (monitorviewtype == null) {
/* 5117 */                               monitorviewtype = "";
/*      */                             }
/* 5119 */                             if (monitorviewtype.startsWith("CategoryView")) {
/* 5120 */                               if (CategoryViewtype.equals("added monitors"))
/*      */                               {
/* 5122 */                                 out.write("          <a  href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\"  class=\"staticlinks\" onclick=\"javascript:setCookieval('showAddedMonitors');\">");
/* 5123 */                                 out.print(FormatUtil.getString("am.monitortab.category.AddedMonitors.text"));
/* 5124 */                                 out.write("</a>\n  ");
/*      */ 
/*      */                               }
/* 5127 */                               else if (CategoryViewtype.equals("all monitors"))
/*      */                               {
/* 5129 */                                 out.write("            <a href=\"/showresource.do?method=showResourceTypes\"   class=\"staticlinks\" onclick=\"javascript:setCookieval('showAllMonitors');\">");
/* 5130 */                                 out.print(FormatUtil.getString("am.monitortab.category.AllMonitors.text"));
/* 5131 */                                 out.write("</a>\n\n  ");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 5136 */                             out.write("\n        </td>\n      </tr>\n      </table>\n    </td>\n    ");
/*      */                             
/* 5138 */                             String tempStl = "center";
/* 5139 */                             if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                             {
/* 5141 */                               tempStl = "right";
/*      */                               
/* 5143 */                               out.write("\n      <td align=\"right\" width=\"30%\" class=\"bodytext\" style=\"white-space:nowrap;\">\n      ");
/* 5144 */                               if (PluginUtil.isPlugin()) {
/* 5145 */                                 out.write("\n      ");
/*      */                                 
/* 5147 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5148 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 5149 */                                 _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 5151 */                                 _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 5152 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 5153 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;) {
/* 5155 */                                     out.write("\n        <span id=\"createNewMG\">");
/* 5156 */                                     out.print(FormatUtil.getString("am.monitortab.creategroup.text"));
/* 5157 */                                     out.write(" :  &nbsp;</span>\n        <select id=\"createMG\" onchange=\"javascript:changeMGURL(this)\" styleClass=\"formtext\" style=\"margin-right: 30px;display:none;\">\n          <option value=\"createNewMG\" selected>");
/* 5158 */                                     out.print(FormatUtil.getString("am.monitortab.selectgrouptype.text"));
/* 5159 */                                     out.write("</option>\n          <option value=\"1\">");
/* 5160 */                                     out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 5161 */                                     out.write("</option>\n          <option value=\"2\">");
/* 5162 */                                     out.print(FormatUtil.getString("am.webclient.mg.type.webappgroup"));
/* 5163 */                                     out.write("</option>\n          <option value=\"3\">");
/* 5164 */                                     out.print(FormatUtil.getString("am.webclient.mg.type.vcenter"));
/* 5165 */                                     out.write("</option>\n        </select>\n      ");
/* 5166 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 5167 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5171 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 5172 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                 }
/*      */                                 
/* 5175 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 5176 */                                 out.write("\n     ");
/*      */                               }
/* 5178 */                               out.write("\n\n      ");
/* 5179 */                               out.print(FormatUtil.getString("am.monitortab.selectview.text"));
/* 5180 */                               out.write(" :  &nbsp;\n\n      ");
/*      */                               
/* 5182 */                               SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5183 */                               _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 5184 */                               _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                               
/* 5186 */                               _jspx_th_html_005fselect_005f0.setProperty("defaultmonitorsview");
/*      */                               
/* 5188 */                               _jspx_th_html_005fselect_005f0.setOnchange("javascript:changeUrl(this)");
/*      */                               
/* 5190 */                               _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/* 5191 */                               int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 5192 */                               if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 5193 */                                 if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5194 */                                   out = _jspx_page_context.pushBody();
/* 5195 */                                   _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 5196 */                                   _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 5199 */                                   out.write("\n        ");
/*      */                                   
/* 5201 */                                   OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5202 */                                   _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 5203 */                                   _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                   
/* 5205 */                                   _jspx_th_html_005foption_005f0.setValue("showResourceTypesAll");
/* 5206 */                                   int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 5207 */                                   if (_jspx_eval_html_005foption_005f0 != 0) {
/* 5208 */                                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 5209 */                                       out = _jspx_page_context.pushBody();
/* 5210 */                                       _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 5211 */                                       _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5214 */                                       out.print(FormatUtil.getString("am.monitortab.bulkconfiguration.text"));
/* 5215 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 5216 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5219 */                                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 5220 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5223 */                                   if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 5224 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                   }
/*      */                                   
/* 5227 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 5228 */                                   out.write("\n        ");
/*      */                                   
/* 5230 */                                   OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5231 */                                   _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 5232 */                                   _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                   
/* 5234 */                                   _jspx_th_html_005foption_005f1.setValue("showResourceTypes");
/* 5235 */                                   int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 5236 */                                   if (_jspx_eval_html_005foption_005f1 != 0) {
/* 5237 */                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 5238 */                                       out = _jspx_page_context.pushBody();
/* 5239 */                                       _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 5240 */                                       _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5243 */                                       out.print(FormatUtil.getString("am.monitortab.category.text"));
/* 5244 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 5245 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5248 */                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 5249 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5252 */                                   if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 5253 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                   }
/*      */                                   
/* 5256 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 5257 */                                   out.write("\n        ");
/*      */                                   
/* 5259 */                                   OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5260 */                                   _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 5261 */                                   _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                   
/* 5263 */                                   _jspx_th_html_005foption_005f2.setValue("plasmaView");
/* 5264 */                                   int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 5265 */                                   if (_jspx_eval_html_005foption_005f2 != 0) {
/* 5266 */                                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 5267 */                                       out = _jspx_page_context.pushBody();
/* 5268 */                                       _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 5269 */                                       _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5272 */                                       out.print(FormatUtil.getString("am.monitortab.plasmaview.text"));
/* 5273 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 5274 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5277 */                                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 5278 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5281 */                                   if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 5282 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                   }
/*      */                                   
/* 5285 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 5286 */                                   out.write("\n        ");
/*      */                                   
/* 5288 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 5289 */                                   _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 5290 */                                   _jspx_th_logic_005fnotPresent_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                   
/* 5292 */                                   _jspx_th_logic_005fnotPresent_005f6.setRole("OPERATOR");
/* 5293 */                                   int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 5294 */                                   if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 5296 */                                       out.write("\n        ");
/*      */                                       
/* 5298 */                                       OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5299 */                                       _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 5300 */                                       _jspx_th_html_005foption_005f3.setParent(_jspx_th_logic_005fnotPresent_005f6);
/*      */                                       
/* 5302 */                                       _jspx_th_html_005foption_005f3.setValue("showMonitorGroupView");
/* 5303 */                                       int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 5304 */                                       if (_jspx_eval_html_005foption_005f3 != 0) {
/* 5305 */                                         if (_jspx_eval_html_005foption_005f3 != 1) {
/* 5306 */                                           out = _jspx_page_context.pushBody();
/* 5307 */                                           _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 5308 */                                           _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5311 */                                           out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 5312 */                                           int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 5313 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5316 */                                         if (_jspx_eval_html_005foption_005f3 != 1) {
/* 5317 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5320 */                                       if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 5321 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                       }
/*      */                                       
/* 5324 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 5325 */                                       out.write("\n        ");
/*      */                                       
/* 5327 */                                       OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5328 */                                       _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 5329 */                                       _jspx_th_html_005foption_005f4.setParent(_jspx_th_logic_005fnotPresent_005f6);
/*      */                                       
/* 5331 */                                       _jspx_th_html_005foption_005f4.setValue("showGMapView");
/* 5332 */                                       int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 5333 */                                       if (_jspx_eval_html_005foption_005f4 != 0) {
/* 5334 */                                         if (_jspx_eval_html_005foption_005f4 != 1) {
/* 5335 */                                           out = _jspx_page_context.pushBody();
/* 5336 */                                           _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 5337 */                                           _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5340 */                                           out.print(FormatUtil.getString("am.monitortab.gmap.text"));
/* 5341 */                                           int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 5342 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5345 */                                         if (_jspx_eval_html_005foption_005f4 != 1) {
/* 5346 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5349 */                                       if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 5350 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                       }
/*      */                                       
/* 5353 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 5354 */                                       out.write("\n        ");
/* 5355 */                                       if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 5356 */                                         out.write("\n        ");
/*      */                                         
/* 5358 */                                         OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5359 */                                         _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 5360 */                                         _jspx_th_html_005foption_005f5.setParent(_jspx_th_logic_005fnotPresent_005f6);
/*      */                                         
/* 5362 */                                         _jspx_th_html_005foption_005f5.setValue("showIconsView");
/* 5363 */                                         int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 5364 */                                         if (_jspx_eval_html_005foption_005f5 != 0) {
/* 5365 */                                           if (_jspx_eval_html_005foption_005f5 != 1) {
/* 5366 */                                             out = _jspx_page_context.pushBody();
/* 5367 */                                             _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 5368 */                                             _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5371 */                                             out.print(FormatUtil.getString("am.monitortab.icons.text"));
/* 5372 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 5373 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5376 */                                           if (_jspx_eval_html_005foption_005f5 != 1) {
/* 5377 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5380 */                                         if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 5381 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                         }
/*      */                                         
/* 5384 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 5385 */                                         out.write("\n        ");
/*      */                                         
/* 5387 */                                         OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5388 */                                         _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 5389 */                                         _jspx_th_html_005foption_005f6.setParent(_jspx_th_logic_005fnotPresent_005f6);
/*      */                                         
/* 5391 */                                         _jspx_th_html_005foption_005f6.setValue("showDetailsView");
/* 5392 */                                         int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 5393 */                                         if (_jspx_eval_html_005foption_005f6 != 0) {
/* 5394 */                                           if (_jspx_eval_html_005foption_005f6 != 1) {
/* 5395 */                                             out = _jspx_page_context.pushBody();
/* 5396 */                                             _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 5397 */                                             _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5400 */                                             out.print(FormatUtil.getString("am.monitortab.table.text"));
/* 5401 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 5402 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5405 */                                           if (_jspx_eval_html_005foption_005f6 != 1) {
/* 5406 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5409 */                                         if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 5410 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                         }
/*      */                                         
/* 5413 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 5414 */                                         out.write("\n        ");
/*      */                                       }
/* 5416 */                                       out.write("\n        ");
/*      */                                       
/* 5418 */                                       OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5419 */                                       _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 5420 */                                       _jspx_th_html_005foption_005f7.setParent(_jspx_th_logic_005fnotPresent_005f6);
/*      */                                       
/* 5422 */                                       _jspx_th_html_005foption_005f7.setValue("showFlashView");
/* 5423 */                                       int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 5424 */                                       if (_jspx_eval_html_005foption_005f7 != 0) {
/* 5425 */                                         if (_jspx_eval_html_005foption_005f7 != 1) {
/* 5426 */                                           out = _jspx_page_context.pushBody();
/* 5427 */                                           _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 5428 */                                           _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5431 */                                           out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/* 5432 */                                           int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 5433 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5436 */                                         if (_jspx_eval_html_005foption_005f7 != 1) {
/* 5437 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5440 */                                       if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 5441 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                       }
/*      */                                       
/* 5444 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 5445 */                                       out.write("\n        ");
/* 5446 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 5447 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5451 */                                   if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 5452 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                                   }
/*      */                                   
/* 5455 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 5456 */                                   out.write("\n      ");
/* 5457 */                                   int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 5458 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 5461 */                                 if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5462 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 5465 */                               if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 5466 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                               }
/*      */                               
/* 5469 */                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 5470 */                               out.write("\n\n      ");
/* 5471 */                               if (!PluginUtil.isPlugin()) {
/* 5472 */                                 out.write("\n      <span class=\"bodytext\">\n        ");
/*      */                                 
/* 5474 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 5475 */                                 _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 5476 */                                 _jspx_th_logic_005fnotPresent_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 5478 */                                 _jspx_th_logic_005fnotPresent_005f7.setRole("OPERATOR");
/* 5479 */                                 int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 5480 */                                 if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                                   for (;;) {
/* 5482 */                                     out.write("\n          ");
/*      */                                     
/* 5484 */                                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5485 */                                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 5486 */                                     _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fnotPresent_005f7);
/*      */                                     
/* 5488 */                                     _jspx_th_c_005fif_005f3.setTest("${globalconfig['defaultmonitorsview'] != requestScope.defaultview}");
/* 5489 */                                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 5490 */                                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                       for (;;) {
/* 5492 */                                         out.write("\n            <input type=hidden name=\"method\" value=\"setDefaultMonitorsView\">\n        <a href=\"javascript:setMonitorsViewDefault()\" class=\"new-monitordiv-link\">");
/* 5493 */                                         out.print(FormatUtil.getString("am.monitortab.setasdefaultview.text"));
/* 5494 */                                         out.write(" </a>\n          ");
/* 5495 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5496 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5500 */                                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5501 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                     }
/*      */                                     
/* 5504 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5505 */                                     out.write("\n        ");
/* 5506 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 5507 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5511 */                                 if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 5512 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                                 }
/*      */                                 
/* 5515 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 5516 */                                 out.write("\n      </span>\n      ");
/*      */                               }
/* 5518 */                               out.write("\n      </td>\n      ");
/*      */                             }
/*      */                             
/* 5521 */                             out.write("\n      ");
/*      */                             
/* 5523 */                             String location = (String)pageContext.getAttribute("setdefaultlocation");
/* 5524 */                             if ((location != null) && (location.equals("Googleview")) && (request.getAttribute("key_set") != null) && (request.getAttribute("key_set").equals("true")))
/*      */                             {
/* 5526 */                               out.write("\n      <td colspan=\"2\" align=\"");
/* 5527 */                               out.print(tempStl);
/* 5528 */                               out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 5529 */                               out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" class=\"staticlinks\">");
/* 5530 */                               out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 5531 */                               out.write("</a>\n       </span>\n\t  </td> \n      ");
/*      */                             }
/*      */                             
/*      */ 
/* 5535 */                             out.write("\n      ");
/*      */                             
/* 5537 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5538 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 5539 */                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/* 5541 */                             _jspx_th_c_005fif_005f4.setTest("${AMActionForm.showMapView == true}");
/* 5542 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 5543 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/* 5545 */                                 out.write("\n      <td colspan=\"2\" align=\"");
/* 5546 */                                 out.print(tempStl);
/* 5547 */                                 out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 5548 */                                 out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" id=\"savezoomlevel\" class=\"staticlinks\">");
/* 5549 */                                 out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 5550 */                                 out.write("</a>\n       </span>\n\t  </td> \n      ");
/* 5551 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 5552 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5556 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5557 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                             }
/*      */                             
/* 5560 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5561 */                             out.write("\n  </tr>\n</table>\n");
/* 5562 */                             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5563 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 5567 */                         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5568 */                           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                         }
/*      */                         
/* 5571 */                         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5572 */                         out.write("\n\n\n<SCRIPT LANGUAGE=\"Javascript1.2\">\nvar defaultview = \"");
/* 5573 */                         out.print(request.getAttribute("defaultview"));
/* 5574 */                         out.write("\";\nif(defaultview == \"showMonitorGroupView\")//No I18N\n{\n  $('#createMG').show();//No I18N\n  $('#createNewMG').show();//No I18N\n}\nelse\n{\n  $('#createMG').hide();//No I18N\n  $('#createNewMG').hide();//No I18N\n}\n//Set cookie function\nfunction setCookie(name,value,expdays)\n{\n       var expdate=new Date();   //No I18N\n       expdate.setDate(expdate.getDate() + expdays);\n       var val=escape(value) + ((expdays==null) ? \"\" : \"; expires=\"+expdate.toUTCString());  //No I18N\n       document.cookie=name + \"=\" + val;   //No I18N\n}\n\n// Get cookie function\nfunction getCookie(name)\n{\n       var i,x,y,arr=document.cookie.split(\";\");   //No I18N\n       y = null;\n       for (i=0;i<arr.length;i++)\n       {\n         x=arr[i].substr(0,arr[i].indexOf(\"=\"));   //No I18N\n         y=arr[i].substr(arr[i].indexOf(\"=\")+1);   //No I18N\n         x=x.replace(/^\\s+|\\s+$/g,\"\");   //No I18N\n         if (x==name)\n         {\n           return unescape(y);\n         }\n       }\n}\nfunction setCookieval(Category_type){\n  //alert(Category_type);\n  if(Category_type==\"showAddedMonitors\"){\n");
/* 5575 */                         out.write("    setCookie('Category_type','showAddedMonitors');  //No I18N\n  }\n  else{\n    setCookie('Category_type','showAllMonitors');  //No I18N\n  }\n}\n\nfunction setMonitorsViewDefault() {\n");
/* 5576 */                         if (_jspx_meth_logic_005fpresent_005f7(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                           return;
/* 5578 */                         out.write(10);
/* 5579 */                         out.write(32);
/* 5580 */                         out.write(32);
/* 5581 */                         if (_jspx_meth_logic_005fnotPresent_005f8(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                           return;
/* 5583 */                         out.write("\n}\n\nfunction changeMGURL(a)\n{\n  var er = /^[0-9]+$/;\n  if(!er.test(a.value))\n  {\n    return;\n  }\n  location.href = '/admin/createapplication.do?method=createapp&grouptype='+a.value;\n}\n\nfunction changeUrl(a)\n{\n\t if(a.selectedIndex == 2 || a.selectedIndex == 7)\n\t {\n \t\tvar url = urlredirect[2];\n \t\tvar windowOpenOptions='scrollbars=1,resizable=1,width=900,height=650,left=50,screenX=50,screenY=25,top=25';\n \t\tvar name = \"PlasmaView\"; // NO I18N\n \t\t\n \t\tif(a.selectedIndex == 7)\n \t\t{\n \t\t\turl = '/GraphicalView.do?method=popUp&haid=0&isPopUp=true'; // NO I18N\n \t\t\twindowOpenOptions = 'scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25'; // NO I18N\n \t\t\tname = \"FlashView\"; // NO I18N\n \t\t}\n\t\twindow.open(url, name, windowOpenOptions);\n\n\t\tvar defaultview = \"");
/* 5584 */                         out.print(request.getAttribute("defaultview"));
/* 5585 */                         out.write("\";\n        \n        if(defaultview == \"showResourceTypesAll\")\n        {\n\t\t\ta.selectedIndex =0;\n        }\n        else if(defaultview == \"showResourceTypes\")\n        {\n            a.selectedIndex = 1;\n        }\n        else if(defaultview == \"showMonitorGroupView\")\n        {\n            a.selectedIndex = 3;\n        }\n        else if(defaultview == \"showGMapView\")\n        {\n\t\t\ta.selectedIndex = 4;\n        }\n        else if(defaultview == \"showIconsView\")\n        {\n            a.selectedIndex = 5;\n        }\n        else if(defaultview == \"showDetailsView\")\n        {\n            a.selectedIndex = 6;\n        }\n        return;\n\t}\n\tlocation.href=urlredirect[a.selectedIndex]; //NO I18N\n}\n</script>\n");
/* 5586 */                         out.write(10);
/* 5587 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5588 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5592 */                     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5593 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                     }
/*      */                     
/* 5596 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5597 */                     out.write("\n<!--<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 5598 */                     out.print(BreadcrumbUtil.getMonitorsPage());
/* 5599 */                     out.write("\n      &gt;<span class=\"bcactive\"> ");
/* 5600 */                     out.print(displayName);
/* 5601 */                     out.write(" </span> </td>\n    </tr>\n</table>-->\n\n");
/* 5602 */                     out.write(10);
/* 5603 */                     if ("WTA".equals(resourceName)) {
/* 5604 */                       out.write("\n <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n   <tr>\n        <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n        <td width=\"95%\" class=\"message\"> \n            ");
/* 5605 */                       out.print(FormatUtil.getString("am.webclient.wta.upgrade.message"));
/* 5606 */                       out.write("\n        </td>\n\t</tr>\n </table>\n <br/>\n");
/*      */                     }
/* 5608 */                     out.write(10);
/* 5609 */                     out.write(10);
/* 5610 */                     if (resourceName.equals("RBM"))
/*      */                     {
/* 5612 */                       String con = "";
/* 5613 */                       if (com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request))
/*      */                       {
/* 5615 */                         con = " and " + com.adventnet.appmanager.reporting.ReportUtilities.getQueryCondition("a.RESOURCEID", owner);
/*      */                       }
/* 5617 */                       String qury = "select c.PARENTID,a.SCRIPT,b.AGENTNAME,a.AGENTID,b.DISPLAYNAME,a.RESOURCEID from AM_RBMDATA a, AM_RBMAGENTDATA b,AM_EUM_PARENTCHILD_MAPPING c  where a.AGENTID=b.AGENTID and c.CHILDID=a.RESOURCEID " + con;
/* 5618 */                       List rbmAgentData = mo.getRows(qury);
/* 5619 */                       request.setAttribute("rbmagenttable", rbmAgentData);
/* 5620 */                       qury = "select DISTINCT(SCRIPT) from AM_RBMDATA ";
/* 5621 */                       List webscrs = mo.getRows(qury);
/* 5622 */                       request.setAttribute("rbmscripts", webscrs);
/* 5623 */                       qury = "select DISTINCT(b.AGENTNAME),b.DISPLAYNAME from AM_RBMDATA a, AM_RBMAGENTDATA b where a.AGENTID=b.AGENTID " + con;
/* 5624 */                       List agts = mo.getRows(qury);
/* 5625 */                       request.setAttribute("availableagents", agts);
/* 5626 */                       qury = "select DISPLAYNAME from AM_RBMAGENTDATA where STATUS <> 0";
/* 5627 */                       List downagts = mo.getRows(qury);
/* 5628 */                       request.setAttribute("downagents", downagts);
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 5635 */                   out.write(10);
/* 5636 */                   out.write(10);
/*      */                   
/* 5638 */                   String selectedFunction = "am.webclient.dasboard.availabilitytab.title";
/* 5639 */                   if (resourceName.equals("RBM"))
/*      */                   {
/* 5641 */                     selectedFunction = "am.webclient.dasboard.rbmlistviewtab.title";
/*      */ 
/*      */                   }
/* 5644 */                   else if ((infraView.equals("true")) && (!EnterpriseUtil.isAdminServer()))
/*      */                   {
/* 5646 */                     selectedFunction = "am.webclient.dashboard.infrastructuretab.title";
/*      */                   }
/* 5648 */                   Cookie[] cookies = request.getCookies();
/*      */                   try {
/* 5650 */                     for (int i = 0; i < cookies.length; i++) {
/* 5651 */                       if (cookies[i].getName().equals("am_monitorsview"))
/*      */                       {
/* 5653 */                         if (cookies[i].getValue().equals("list"))
/*      */                         {
/* 5655 */                           selectedFunction = "am.webclient.dasboard.listviewtab.title";
/* 5656 */                           break;
/*      */                         }
/* 5658 */                         if (cookies[i].getValue().equals("performance"))
/*      */                         {
/* 5660 */                           selectedFunction = "am.webclient.dasboard.performancetab.title";
/* 5661 */                           break;
/*      */                         }
/* 5663 */                         if (cookies[i].getValue().equals("availability"))
/*      */                         {
/* 5665 */                           selectedFunction = "am.webclient.dasboard.availabilitytab.title";
/* 5666 */                           break;
/*      */                         }
/* 5668 */                         if ((cookies[i].getValue().equals("rbmsummary")) && (resourceName.equals("RBM")))
/*      */                         {
/* 5670 */                           selectedFunction = "am.webclient.dasboard.rbmlistviewtab.title";
/* 5671 */                           break;
/*      */                         }
/* 5673 */                         if ((cookies[i].getValue().equals("infrastructure")) && ((resourceName.equals("VMWare ESX/ESXi")) || ((infraView.equals("true")) && (!EnterpriseUtil.isAdminServer()))))
/*      */                         {
/*      */ 
/* 5676 */                           selectedFunction = "am.webclient.dashboard.infrastructuretab.title";
/* 5677 */                           break;
/*      */                         }
/*      */                         
/*      */ 
/* 5681 */                         selectedFunction = PluginUtil.isPlugin() ? "am.webclient.dasboard.listviewtab.title" : "am.webclient.dasboard.availabilitytab.title";
/*      */                         
/* 5683 */                         out.write("\n\t\t\t<script>\n\t\t\t\tSet_Cookie('am_monitorsview',PluginUtil.isPlugin()?'list':'availability');  //No I18N\n\t\t\t</script>\n\t\t\t");
/*      */                         
/* 5685 */                         break;
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   catch (Exception e)
/*      */                   {
/* 5691 */                     e.printStackTrace();
/*      */                   }
/* 5693 */                   if ((method.equals("showResourceTypesAll")) && ((request.getParameter("detailspage") == null) || (!request.getParameter("detailspage").equals("true"))))
/*      */                   {
/* 5695 */                     selectedFunction = "am.webclient.dasboard.listviewtab.title";
/*      */                     
/* 5697 */                     out.write("\n\t <script>\n\t \tSet_Cookie('am_monitorsview','list');\n\t </script>\n");
/*      */                   }
/* 5699 */                   if (selectedFunction.equals("am.webclient.dasboard.rbmlistviewtab.title"))
/*      */                   {
/*      */ 
/* 5702 */                     out.write("\n\t\t\t<script>\n\t\t\t\tSet_Cookie('am_monitorsview','rbmsummary');\n\t\t\t</script>\n\t\t\t");
/*      */                   }
/*      */                   
/*      */ 
/* 5706 */                   if ((resourceAvailable) && ("VirtualMachine".equals(resourceName)) && (!EnterpriseUtil.isAdminServer()))
/*      */                   {
/*      */ 
/*      */ 
/* 5710 */                     out.write(10);
/* 5711 */                     out.write(10);
/* 5712 */                     JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.topvm.name", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.virtualinfrastructure.name", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeAvailabilityData,getCategoryPerformanceData,getHomeRealTimeData,getVirtualDashData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()), out, true);
/* 5713 */                     out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:15px;\" width=\"100%\" id=\"tableID\">\n<tr>\n\n\n<!-- Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\">\n\n\n<div id=\"realtimedata\" style=\"display:none;\">\n\n\n");
/*      */ 
/*      */ 
/*      */                   }
/* 5717 */                   else if ((resourceAvailable) && ("XenServerHost".equals(resourceName)) && (!EnterpriseUtil.isAdminServer()))
/*      */                   {
/*      */ 
/* 5720 */                     out.write(10);
/* 5721 */                     out.write(10);
/* 5722 */                     JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.topxenserverhost.name,am.webclient.dashboard.infrastructuretab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.virtualinfrastructure.name,am.webclient.dashboard.infrastructuretab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeAvailabilityData,getCategoryPerformanceData,getHomeRealTimeData,getVirtualDashData,getInfrastructureView", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()), out, true);
/* 5723 */                     out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:15px;\" width=\"100%\" id=\"tableID\">\n<tr>\n\n\n<!-- Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\">\n<div id=\"realtimedata\" style=\"display:none;\">\n\n\n");
/*      */ 
/*      */                   }
/* 5726 */                   else if ((resourceAvailable) && ("VMWare ESX/ESXi".equals(resourceName)) && (!EnterpriseUtil.isAdminServer()))
/*      */                   {
/*      */ 
/* 5729 */                     out.write(10);
/* 5730 */                     out.write(10);
/* 5731 */                     JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.topesx.name,am.webclient.dashboard.infrastructuretab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.virtualinfrastructure.name,am.webclient.dashboard.infrastructuretab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeAvailabilityData,getCategoryPerformanceData,getHomeRealTimeData,getVirtualDashData,getInfrastructureView", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()), out, true);
/* 5732 */                     out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:15px;\" width=\"100%\" id=\"tableID\">\n<tr>\n\n\n<!-- Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\">\n<div id=\"realtimedata\" style=\"display:none;\">\n\n\n");
/*      */ 
/*      */ 
/*      */                   }
/* 5736 */                   else if ((resourceAvailable) && ("Container".equals(resourceName)) && (!EnterpriseUtil.isAdminServer()))
/*      */                   {
/*      */ 
/* 5739 */                     out.write(10);
/* 5740 */                     out.write(10);
/* 5741 */                     JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.topDockerContainer.name", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.virtualinfrastructure.name", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeAvailabilityData,getCategoryPerformanceData,getHomeRealTimeData,getContainerDashData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()), out, true);
/* 5742 */                     out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:15px;\" width=\"100%\" id=\"tableID\">\n<tr>\n\n\n<!-- Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\">\n<div id=\"realtimedata\" style=\"display:none;\">\n\n\n");
/*      */ 
/*      */ 
/*      */                   }
/* 5746 */                   else if ((resourceAvailable) && ("Hyper-V-Server".equals(resourceName)) && (!EnterpriseUtil.isAdminServer()))
/*      */                   {
/*      */ 
/* 5749 */                     out.write(10);
/* 5750 */                     JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.tophyperv.name", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dashboard.tophyperv.name", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeAvailabilityData,getCategoryPerformanceData,getHomeRealTimeData,getHyperVDashData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()), out, true);
/* 5751 */                     out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:15px;\" width=\"100%\" id=\"tableID\">\n<tr>\n\n\n<!-- Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\">\n\n<div id=\"realtimedata\" style=\"display:none;\">\n\n\n");
/*      */ 
/*      */                   }
/* 5754 */                   else if ((resourceAvailable) && (infraView.equals("true")) && (!EnterpriseUtil.isAdminServer()))
/*      */                   {
/*      */ 
/* 5757 */                     out.write(10);
/* 5758 */                     JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dashboard.infrastructuretab.title,am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dashboard.infrastructuretab.title,am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getInfrastructureView,getHomeAvailabilityData,getCategoryPerformanceData,getHomeRealTimeData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()), out, true);
/* 5759 */                     out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:15px;\" width=\"100%\" id=\"tableID\">\n<tr>\n\n\n<!-- Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\">\n\n\n<div id=\"realtimedata\" style=\"display:none;\">\n\n\n");
/*      */ 
/*      */ 
/*      */                   }
/* 5763 */                   else if (resourceAvailable)
/*      */                   {
/* 5765 */                     if (resourceName.equals("RBM"))
/*      */                     {
/*      */ 
/* 5768 */                       out.write("\n\t\t\t");
/* 5769 */                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dasboard.rbmlistviewtab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title,am.webclient.dasboard.rbmlistviewtab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeAvailabilityData,getCategoryPerformanceData,getHomeRealTimeData,getRBMSummaryData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()), out, true);
/* 5770 */                       out.write("\n\n\t\t\t");
/*      */ 
/*      */                     }
/* 5773 */                     else if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*      */                     {
/* 5775 */                       out.write("\n\t\t\t");
/* 5776 */                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.searchresultstab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.searchresultstab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeRealTimeData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.searchresultstab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()), out, true);
/* 5777 */                       out.write(10);
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 5782 */                       out.write(10);
/* 5783 */                       out.write(10);
/* 5784 */                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.dasboard.availabilitytab.title,am.webclient.dasboard.performancetab.title,am.webclient.dasboard.listviewtab.title", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHomeAvailabilityData,getCategoryPerformanceData,getHomeRealTimeData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedFunction), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()), out, true);
/* 5785 */                       out.write(10);
/*      */                     }
/*      */                     
/*      */ 
/* 5789 */                     out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"margin-top:15px;\" width=\"100%\" id=\"tableID\">\n<tr>\n\n\n<!-- Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\">\n\n\n<div id=\"realtimedata\" style=\"display:none;\">\n\n");
/*      */                   }
/*      */                   
/*      */ 
/* 5793 */                   out.write("\n   <!--   <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n           <tr>\n       <td height=\"26\" class=\"tableheading\"> ");
/* 5794 */                   out.print(toptitle + " : " + displayName);
/* 5795 */                   out.write("\n             </td>\n           </tr>\n         </table>-->\n    <!--  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n           <tr>\n             <td width=\"99%\" height=\"24\"> -->\n\n              ");
/* 5796 */                   JspRuntimeLibrary.include(request, response, "displayresources.jsp", out, false);
/* 5797 */                   out.write("\n   <!--\n   \t</td>\n   \t</tr>\n   \t</table> -->\n\n");
/*      */                   
/* 5799 */                   if (resourceAvailable)
/*      */                   {
/*      */ 
/* 5802 */                     out.write("\n</div>\n\n<div id=\"health\" style=\"display:none;\">\n");
/*      */                     
/* 5804 */                     if (group == null)
/*      */                     {
/* 5806 */                       if ((!resourceName.equals("Generic WMI")) && (!resourceName.equals("WTA")) && (!resourceName.equals("Custom-Application")) && (!resourceName.equals("File System Monitor")) && (!resourceName.equals("SAP-CCMS")))
/*      */                       {
/* 5808 */                         String[] attribs = com.adventnet.appmanager.util.Constants.getAttributelistfortype(resourceName);
/*      */                         
/* 5810 */                         out.write(10);
/*      */                         
/* 5812 */                         if (!EnterpriseUtil.isAdminServer())
/*      */                         {
/*      */ 
/* 5815 */                           out.write(10);
/* 5816 */                           JspRuntimeLibrary.include(request, response, "/jsp/PerformanceDashboard.jsp" + ("/jsp/PerformanceDashboard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("attribute", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(attribs[0]), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("charttype", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(attribs[1]), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("units", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(attribs[2]), request.getCharacterEncoding()), out, true);
/* 5817 */                           out.write(10);
/*      */                         }
/*      */                         
/*      */ 
/* 5821 */                         out.write(10);
/* 5822 */                         out.write(10);
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 5827 */                     out.write("\n<div id=\"healthdata\">\n</div>\n</div>\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tr>\n<td>\n<div id=\"VIdashdata\">\n</div>\n</td>\n</tr>\n</table>\n\n<div id=\"availability\" style=\"display:block;\">\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n<tr>\n<td class=\"bodytextbold tableheadingbborder\" style=\"height:50px;\" align=\"left\"><img src=\"/images/icon_mon_avail.png\" style=\"position:relative; top:3px; left:6px;\">&nbsp; &nbsp;<span style=\"position:relative; bottom:4px; left:3px;\">");
/* 5828 */                     out.print(FormatUtil.getString("am.webclient.dasboard.availabilityfor.title"));
/* 5829 */                     out.write(32);
/* 5830 */                     out.write(32);
/* 5831 */                     out.print(displayName);
/* 5832 */                     out.write("</span>\n&nbsp; &nbsp;\n<span style=\"position:relative; bottom:4px; left:3px;\"> \n<SELECT id=\"historyperiod\" onchange=\"getHomeAvailabilityData('");
/* 5833 */                     out.print(resourceName);
/* 5834 */                     out.write("')\" class=\"formtext\"> \n\t<OPTION ");
/* 5835 */                     if (_jspx_meth_c_005fif_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                       return;
/* 5837 */                     out.write(" value=\"1\">");
/* 5838 */                     out.print(FormatUtil.getString("am.webclient.period.last24hours"));
/* 5839 */                     out.write(" </OPTION> \n\t<OPTION ");
/* 5840 */                     if (_jspx_meth_c_005fif_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                       return;
/* 5842 */                     out.write(" value=\"2\">");
/* 5843 */                     out.print(FormatUtil.getString("am.webclient.period.last30days"));
/* 5844 */                     out.write("</OPTION>\n</SELECT> &nbsp; &nbsp;\n</span>\n</td>\n<td class=\"bodytextbold tableheadingbborder\" style=\"height:50px;\" align=\"right\"> <div id=\"availabilityPageRange\" >\n\t\t</div>\n\t</td>\n</tr>\n</table> \n<div id=\"availabilitydata\">\n</div>\n\n\n</div>\n\n<!-- For RBM A New Tab is included -->\n\n<div id=\"rbmsummary\" style=\"display:block;\">\n\n <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n        <tr>\n    <td height=\"26\" class=\"tableheadingbborder\">");
/* 5845 */                     out.print(displayName);
/* 5846 */                     out.write("\n          </td>\n        </tr>\n      </table>\n\n<div id=\"rbmdata\">\n\n\n\n</div>\n</div>\n\n<!-- Infrastructure View for VMWare -->\n<div id=\"infrastructureview\" style=\"display:none;\">\n");
/*      */                     
/* 5848 */                     if ("VMWare ESX/ESXi".equals(resourceName))
/*      */                     {
/* 5850 */                       out.write(10);
/* 5851 */                       JspRuntimeLibrary.include(request, response, "vmlistview.jsp", out, false);
/* 5852 */                       out.write(32);
/* 5853 */                       out.write(10);
/*      */ 
/*      */                     }
/* 5856 */                     else if ("XenServerHost".equals(resourceName))
/*      */                     {
/*      */ 
/* 5859 */                       out.write(10);
/* 5860 */                       JspRuntimeLibrary.include(request, response, "vmlistview.jsp", out, false);
/* 5861 */                       out.write(32);
/* 5862 */                       out.write(10);
/*      */ 
/*      */                     }
/* 5865 */                     else if ((infraView.equals("true")) && (!EnterpriseUtil.isAdminServer()))
/*      */                     {
/* 5867 */                       out.write(10);
/* 5868 */                       JspRuntimeLibrary.include(request, response, "NoSqlListView.jsp", out, false);
/* 5869 */                       out.write(32);
/* 5870 */                       out.write(10);
/*      */                     }
/*      */                     
/*      */ 
/* 5874 */                     out.write("\n</div>\n\n</td>\n\n<!-- Inner tabel ends!-->\n\n<script>\n    //getHomeAvailabilityData('");
/* 5875 */                     out.print(resourceName);
/* 5876 */                     out.write("')\n    var temp=Get_Cookie('am_monitorsview');\n    var resName=\"");
/* 5877 */                     out.print(resourceName);
/* 5878 */                     out.write("\";\n    var iview=\"");
/* 5879 */                     out.print(infraView);
/* 5880 */                     out.write("\";\n    var isAdmin=\"");
/* 5881 */                     out.print(EnterpriseUtil.isAdminServer());
/* 5882 */                     out.write("\";\n    if(temp=='performance')\n        {\n            getCategoryPerformanceData('");
/* 5883 */                     out.print(resourceName);
/* 5884 */                     out.write("');\n        }\n        else if(temp=='availability')\n            {\n                getHomeAvailabilityData('");
/* 5885 */                     out.print(resourceName);
/* 5886 */                     out.write("'); //No I18N\n            }\n            else if(temp=='list')\n                {\n                    getHomeRealTimeData('");
/* 5887 */                     out.print(resourceName);
/* 5888 */                     out.write("');\n                }\n\t\t\t\telse if(temp=='infrastructure'){\n\t\t\t\t\tif((iview == \"true\" && isAdmin == \"false\") || resName.trim()==\"VMWare ESX/ESXi\"){ \n                        getInfrastructureView('");
/* 5889 */                     out.print(resourceName);
/* 5890 */                     out.write("');\n\t\t\t\t\t}\n\t\t\t\t\telse{\n\t\t\t\t\t\tgetHomeAvailabilityData('");
/* 5891 */                     out.print(resourceName);
/* 5892 */                     out.write("'); //No I18N\n\t\t\t\t\t}\n                }\n\t\t\t\telse if(temp=='rbmsummary')\n                {\n\t\t\t\t\tgetRBMSummaryData('");
/* 5893 */                     out.print(resourceName);
/* 5894 */                     out.write("');//No I18N\n\t\t\t\t}\n\t\t\t\telse if(iview == \"true\" && isAdmin == \"false\")\n\t\t\t\t{\n                        getInfrastructureView('");
/* 5895 */                     out.print(resourceName);
/* 5896 */                     out.write("');\n\t\t\t\t}\n\t\t\t\telse\n                {\n                    getHomeAvailabilityData('");
/* 5897 */                     out.print(resourceName);
/* 5898 */                     out.write("');//No I18N\n                }\n\t\t\t\tfunction getMonitorsCountForListView(url){\n\t\t\t\ttry{\n\t\t\t\t$('#mycount').load(url);\n\t\t\t\t$(\"#showtotal\").css(\"color\",\"#a9a9a9\");  //No I18N \n\t\t\t\t\t$(\"#showtotal\").text(\"");
/* 5899 */                     out.print(FormatUtil.getString("am.webclient.listview.count"));
/* 5900 */                     out.write("\");\t\t\t\t\t\n\t\t\t\t\t}\n\t\t\tcatch(err)\n\t\t\t{\n\t\tconsole.log('err      '+err)\n\t\t\t\t}\t\t\t\t\t\n                }\n    </script>\n</tr>\n<tr>\n<td width=\"8\"></td>\n<td></td>\n<td width=\"7\"></td>\n</tr><!-- Temp Fix - vcenter-shadow effect removed, 3 <td> to be removed eventually after fixing firefox issue - Ramakrishnan.J -->\n</table> \n\n");
/*      */                   }
/*      */                   
/*      */ 
/* 5904 */                   out.write("\n\n\n\n");
/* 5905 */                   int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 5906 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 5909 */                 if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 5910 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 5913 */               if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5914 */                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */               }
/*      */               
/* 5917 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 5918 */               out.write(10);
/* 5919 */               out.write(32);
/* 5920 */               if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                 return;
/* 5922 */               out.write(10);
/* 5923 */               int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5924 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 5928 */           if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5929 */             this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */           }
/*      */           
/* 5932 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5933 */           out.write(10);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 5937 */           e.printStackTrace();
/*      */         }
/*      */         
/* 5940 */         out.write(10);
/* 5941 */         out.write(10);
/*      */ 
/*      */       }
/*      */       catch (Exception m)
/*      */       {
/* 5946 */         m.printStackTrace();
/*      */       }
/*      */       
/* 5949 */       out.write("\n\n<script>\n$(window).load(function() {\n$('.chzn-select').chosen();\n});\n</script>");
/*      */     } catch (Throwable t) {
/* 5951 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5952 */         out = _jspx_out;
/* 5953 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5954 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5955 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5958 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5964 */     PageContext pageContext = _jspx_page_context;
/* 5965 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5967 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 5968 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5969 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 5971 */     _jspx_th_c_005fset_005f0.setTarget("${AMActionForm}");
/*      */     
/* 5973 */     _jspx_th_c_005fset_005f0.setProperty("defaultmonitorsview");
/*      */     
/* 5975 */     _jspx_th_c_005fset_005f0.setValue("showResourceTypesAll");
/* 5976 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5977 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5978 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 5979 */       return true;
/*      */     }
/* 5981 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 5982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5987 */     PageContext pageContext = _jspx_page_context;
/* 5988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5990 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 5991 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 5992 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5994 */     _jspx_th_html_005fhidden_005f0.setProperty("zoomlevel");
/* 5995 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 5996 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 5997 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 5998 */       return true;
/*      */     }
/* 6000 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 6001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6006 */     PageContext pageContext = _jspx_page_context;
/* 6007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6009 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6010 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 6011 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6013 */     _jspx_th_html_005fhidden_005f1.setProperty("zoomlocation");
/* 6014 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 6015 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 6016 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6017 */       return true;
/*      */     }
/* 6019 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6020 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f7(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6025 */     PageContext pageContext = _jspx_page_context;
/* 6026 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6028 */     PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6029 */     _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 6030 */     _jspx_th_logic_005fpresent_005f7.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6032 */     _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 6033 */     int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 6034 */     if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */       for (;;) {
/* 6036 */         out.write("\n    alertUser();\n    return;\n  ");
/* 6037 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 6038 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6042 */     if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 6043 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 6044 */       return true;
/*      */     }
/* 6046 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 6047 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f8(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6052 */     PageContext pageContext = _jspx_page_context;
/* 6053 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6055 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 6056 */     _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 6057 */     _jspx_th_logic_005fnotPresent_005f8.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6059 */     _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 6060 */     int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 6061 */     if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */       for (;;) {
/* 6063 */         out.write("\n  document.AMActionForm.submit();\n  ");
/* 6064 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 6065 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6069 */     if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 6070 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 6071 */       return true;
/*      */     }
/* 6073 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 6074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6079 */     PageContext pageContext = _jspx_page_context;
/* 6080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6082 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6083 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 6084 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6086 */     _jspx_th_c_005fif_005f5.setTest("${param.period == '1'}");
/* 6087 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 6088 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 6090 */         out.write("SELECTED");
/* 6091 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 6092 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6096 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 6097 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6098 */       return true;
/*      */     }
/* 6100 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6106 */     PageContext pageContext = _jspx_page_context;
/* 6107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6109 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6110 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 6111 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6113 */     _jspx_th_c_005fif_005f6.setTest("${param.period == '2'}");
/* 6114 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 6115 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 6117 */         out.write("SELECTED");
/* 6118 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 6119 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6123 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 6124 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6125 */       return true;
/*      */     }
/* 6127 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6133 */     PageContext pageContext = _jspx_page_context;
/* 6134 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6136 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6137 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 6138 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6140 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 6142 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 6143 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 6144 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 6145 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6146 */       return true;
/*      */     }
/* 6148 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6149 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\networkdetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */