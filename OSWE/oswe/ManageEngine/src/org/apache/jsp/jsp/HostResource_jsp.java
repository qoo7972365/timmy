/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.CpuGraph;
/*      */ import com.adventnet.appmanager.bean.HostResourceBean;
/*      */ import com.adventnet.appmanager.bean.MemGraph;
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.bean.SysloadGraph;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
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
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class HostResource_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  358 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
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
/*  826 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  827 */     getRCATrimmedText(div1, rca);
/*  828 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  831 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  832 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  985 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1642 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
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
/* 2054 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
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
/* 2190 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2196 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2197 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2198 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2216 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2231 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2236 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2240 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2241 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2244 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2251 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2254 */     JspWriter out = null;
/* 2255 */     Object page = this;
/* 2256 */     JspWriter _jspx_out = null;
/* 2257 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2261 */       response.setContentType("text/html;charset=UTF-8");
/* 2262 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2264 */       _jspx_page_context = pageContext;
/* 2265 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2266 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2267 */       session = pageContext.getSession();
/* 2268 */       out = pageContext.getOut();
/* 2269 */       _jspx_out = out;
/*      */       
/* 2271 */       out.write("<!DOCTYPE html>\n");
/* 2272 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n <!DOCTYPE html>\n");
/* 2273 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2274 */       out.write(10);
/*      */       
/* 2276 */       request.setAttribute("HelpKey", "Monitors System Details");
/*      */       
/* 2278 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2279 */       CpuGraph cpugraph = null;
/* 2280 */       cpugraph = (CpuGraph)_jspx_page_context.getAttribute("cpugraph", 1);
/* 2281 */       if (cpugraph == null) {
/* 2282 */         cpugraph = new CpuGraph();
/* 2283 */         _jspx_page_context.setAttribute("cpugraph", cpugraph, 1);
/*      */       }
/* 2285 */       out.write(10);
/* 2286 */       MemGraph memgraph = null;
/* 2287 */       memgraph = (MemGraph)_jspx_page_context.getAttribute("memgraph", 1);
/* 2288 */       if (memgraph == null) {
/* 2289 */         memgraph = new MemGraph();
/* 2290 */         _jspx_page_context.setAttribute("memgraph", memgraph, 1);
/*      */       }
/* 2292 */       out.write(10);
/* 2293 */       SysloadGraph sysgraph = null;
/* 2294 */       sysgraph = (SysloadGraph)_jspx_page_context.getAttribute("sysgraph", 1);
/* 2295 */       if (sysgraph == null) {
/* 2296 */         sysgraph = new SysloadGraph();
/* 2297 */         _jspx_page_context.setAttribute("sysgraph", sysgraph, 1);
/*      */       }
/* 2299 */       out.write(10);
/* 2300 */       HostResourceBean hrbean = null;
/* 2301 */       hrbean = (HostResourceBean)_jspx_page_context.getAttribute("hrbean", 1);
/* 2302 */       if (hrbean == null) {
/* 2303 */         hrbean = new HostResourceBean();
/* 2304 */         _jspx_page_context.setAttribute("hrbean", hrbean, 1);
/*      */       }
/* 2306 */       out.write(10);
/* 2307 */       GetWLSGraph wlsGraph = null;
/* 2308 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2309 */       if (wlsGraph == null) {
/* 2310 */         wlsGraph = new GetWLSGraph();
/* 2311 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2313 */       out.write(10);
/* 2314 */       PerformanceBean perfgraph = null;
/* 2315 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2316 */       if (perfgraph == null) {
/* 2317 */         perfgraph = new PerformanceBean();
/* 2318 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2320 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2321 */       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */         return;
/* 2323 */       out.write("\n\n<script language=\"javascript\" src=\"../webclient/common/js/windowFunctions.js\"></script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/dnd.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n<script>\nfunction servicesInHostByAJAX()\n{\t\n\tURL ='/jsp/HostServicesDetails.jsp?resourceid=");
/* 2324 */       out.print(request.getAttribute("resourceid"));
/* 2325 */       out.write("&resourcename=");
/* 2326 */       out.print(request.getParameter("name"));
/* 2327 */       out.write("&resourceType=");
/* 2328 */       out.print(request.getParameter("name"));
/* 2329 */       out.write("&mode=");
/* 2330 */       out.print(request.getAttribute("mode"));
/* 2331 */       out.write("&hostname=");
/* 2332 */       out.print(request.getParameter("hostname"));
/* 2333 */       out.write("'; //NO I18N\n\thttp3=getHTTPObject();\n\thttp3.onreadystatechange = getServicesInHost;\n\thttp3.open(\"GET\", URL, true);\n\thttp3.send(null);\n}\n\nfunction getServicesInHost()\n{\n\tif(http3.readyState == 4 && http3.status == 200)\n\t{\n\t\tdocument.getElementById(\"actionstatus3\").innerHTML =\"&nbsp;\";\n\t\tdocument.getElementById(\"servicesinhost\").innerHTML = http3.responseText;\n\n\t}else{\n\n\t\tdocument.getElementById(\"actionstatus3\").innerHTML =\"&nbsp;\";\n\n\n\t}\n\n}\n\nfunction enableReports()\n{\n\tif(!checkforOneSelected(document.removemonitor,\"monitors\"))\n\t{\n\t\talert(\"");
/* 2334 */       out.print(FormatUtil.getString("am.webclient.hostresourceprocess.alert.enable"));
/* 2335 */       out.write("\");\n\t\treturn;\n\t}\n\tdocument.removemonitor.enabled.value='true';\n\tdocument.removemonitor.submit();\n}\n\nfunction disableReports()\n{\n\tif(!checkforOneSelected(document.removemonitor,\"monitors\"))\n\t{\n\t\talert(\"");
/* 2336 */       out.print(FormatUtil.getString("am.webclient.hostresourceprocess.alert.disable"));
/* 2337 */       out.write("\");\n\t\treturn;\n\t}\n\tdocument.removemonitor.enabled.value ='false';\n\tdocument.removemonitor.submit();\n}\n\nfunction fnSelectAll(e)\n{\n \tToggleAll(e,document.removemonitor,\"monitors\"); //NO I18N\n}\n\nfunction fnAddProcess()\n{\n\ttoggleDiv('addprocess');\n\tlocation.href=\"#top1\";\n}\n\nfunction goDisk()\n{\n\n\tlocation.href=\"#disk\";\n}\n\nfunction manageMonitors(state, motype, isReset)\n{\n\tvar formElement = document.removemonitor;\n\tvar type = \"monitors\";//NO I18N\n\tif(motype == 'service')\n\t{\n\t\tformElement = document.removeservice;\n\t\ttype = \"services\";//NO I18N\n\t}\n\tif(!checkforOneSelected(formElement,type))\n\t{\n\t\tif(state)\n\t\t{\n\t\t\talert(\"");
/* 2338 */       out.print(FormatUtil.getString("am.webclient.common.alert.manage.text"));
/* 2339 */       out.write("\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\talert(\"");
/* 2340 */       out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.text"));
/* 2341 */       out.write("\");\n\t\t}\n\t\treturn;\n\t}\n\tvar message = \"\";\n\tif(state)\n\t{\n\t\tmessage = \"");
/* 2342 */       out.print(FormatUtil.getString("am.webclient.common.confirm.manage.text"));
/* 2343 */       out.write("\";\n\t}\n\telse\n\t{\n\t\tmessage = \"");
/* 2344 */       out.print(FormatUtil.getString("am.webclient.common.confirm.unmanage.text"));
/* 2345 */       out.write("\";\n\t}\n\tif(message != '')\n\t{\n\t\tif(!confirm(message))\n\t\t{\n\t\t\treturn;\n\t\t}\n\t\tif(motype == 'process')\n\t\t{\n\t\t\tdocument.removemonitor.managedprocess.value = state;\n\t\t\tif(isReset)\n\t\t\t{\n\t\t\t\tdocument.removemonitor.unmanageAndResetProcess.value = true;\n\t\t\t}\n\t\t\tdocument.removemonitor.submit();\n\t\t}\n\t\telse if(motype == 'service')\n\t\t{\n\t\t\tdocument.removeservice.managedservice.value = state;\n\t\t\tif(isReset)\n\t\t\t{\n\t\t\t\tdocument.removeservice.unmanageAndResetService.value = true;\n\t\t\t}\n\t\t\tdocument.removeservice.submit();\n\t\t}\n\t}\n}\nfunction removeMonitors()\n{\n\tif(!checkforOneSelected(document.removemonitor,\"monitors\"))\n\t{\n\t\talert(\"");
/* 2346 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.processdel"));
/* 2347 */       out.write("\");\n\t\treturn;\n\t}\n\n\tif(confirm(\"");
/* 2348 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.processdelconfirm"));
/* 2349 */       out.write("\"))\n\t{\n\tdocument.removemonitor.submit();\n\t}\n}\n\nfunction fnSelectAllDisks(e)\n{\n \tToggleAll(e,document.removedrive,\"drives\");//NO I18N\n}\n\nfunction actiononServices(action)\n{\n\tif(!checkforOneSelected(document.removeservice,\"services\"))\n\t{\n\t  if(action==\"Delete\")\n\t  alertmessage='");
/* 2350 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.selectoneservice"));
/* 2351 */       out.write("'+' '+'");
/* 2352 */       out.print(FormatUtil.getString("webclient.fault.alarm.operations.delete"));
/* 2353 */       out.write("';\n\t  else if(action==\"Start\")\n\t  alertmessage='");
/* 2354 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.selectoneservice"));
/* 2355 */       out.write("'+' '+'");
/* 2356 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.start"));
/* 2357 */       out.write("';\n\t  else if(action==\"Stop\")\n\t  alertmessage='");
/* 2358 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.selectoneservice"));
/* 2359 */       out.write("'+' '+'");
/* 2360 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.stop"));
/* 2361 */       out.write("';\n\t  else if(action==\"Restart\")\n\t  alertmessage='");
/* 2362 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.selectoneservice"));
/* 2363 */       out.write("'+' '+'");
/* 2364 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.restart"));
/* 2365 */       out.write("';\n\t  alert(alertmessage);\n\t  return;\n\t }\n\tif(action==\"Delete\")\n\tconfirmmessage='");
/* 2366 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.delete"));
/* 2367 */       out.write("';\n\telse if(action==\"Start\")\n\tconfirmmessage='");
/* 2368 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.start"));
/* 2369 */       out.write("';\n\telse if(action==\"Stop\")\n\tconfirmmessage='");
/* 2370 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.stop"));
/* 2371 */       out.write("';\n\telse if(action==\"Restart\")\n\tconfirmmessage='");
/* 2372 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.alert.restart"));
/* 2373 */       out.write("';\n\tif(confirm(confirmmessage))\n\t{\n\tdocument.removeservice.actiononServices.value=action;\n\t//document.removeservice.submit();\n\teditservice(action);\n\t}\n}\n\nfunction selectallservices(e)\n{\n\tToggleAll(e,document.removeservice,'services');//NO I18N\n}\n\nvar http = getHTTPObject(); // We create the HTTP Object\nfunction getHTTPObject() {\n  var xmlhttp;\n  if (window.ActiveXObject){\n    try {\n      xmlhttp = new ActiveXObject(\"Msxml2.XMLHTTP\");\n    } catch (e) {\n      try {\n        xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n      } catch (E) {\n        xmlhttp = false;\n      }\n    }\n}\n  else if (typeof XMLHttpRequest != 'undefined') {\n    try {\n      xmlhttp = new XMLHttpRequest();\n    } catch (e) {\n      xmlhttp = false;\n    }\n  }\n  return xmlhttp;\n}\n\nfunction editservice(action)\n{\n\tif(action=='Start')\n\t\tmsg='");
/* 2374 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.startprogress"));
/* 2375 */       out.write("';\n\telse if(action=='Stop')\n\t\tmsg='");
/* 2376 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.stopprogress"));
/* 2377 */       out.write("';\n\telse if(action=='Restart')\n\t\tmsg='");
/* 2378 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.restartprogress"));
/* 2379 */       out.write("';\n\telse if(action=='Delete')\n\t\tmsg='");
/* 2380 */       out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.deleteprogress"));
/* 2381 */       out.write("';\n\tdocument.getElementById('actionstatus').innerHTML ='<img src=\"../images/icon_cogwheel.gif\" width=\"20\" height=\"20\">'+msg;\n\tURL=\"/HostResource.do?\";//NO I18N\n\tfor (i=0; i<document.removeservice.length; i++)\n\t{\n\t\tif(document.removeservice.elements[i].type == \"checkbox\" && document.removeservice.elements[i].checked )\n\t\t{\n\t\t\tURL=URL+'&'+document.removeservice.elements[i].name+'='+document.removeservice.elements[i].value;\n\t\t}\n\t}\n\tURL=URL+'&actiononServices='+action; //NO I18N\n\tURL=URL+'&haid='+document.removeservice.haid.value; //NO I18N\n\tURL=URL+'&name='+document.removeservice.name.value; //NO I18N\n\tURL=URL+'&appName='+document.removeservice.appName.value; //NO I18N\n\tURL=URL+'&resourceid='+document.removeservice.resourceid.value; //NO I18N\n\thttp = getHTTPObject();\n\thttp.onreadystatechange = changeServiceStatus;\n\thttp.open(\"GET\", URL, true);\n\thttp.send(null);\n}\n\nfunction changeServiceStatus()\n{\n\tif(http.readyState == 4)\n\t{\t\n\t\tif (http.status == 200)\n\t\t{\t\t  \n\t\t\tdocument.getElementById('servicedetail').innerHTML = http.responseText;\n");
/* 2382 */       out.write("\t\t}\n\n\t}\n\n}\n\nfunction hidemessage()\n{\n   javascript:hideDiv('serviceactionmessage');\n   javascript:showDiv('servicedetail');\n}\n\nfunction showSummaryGraph(resID,resType,moId,mode)\n{\n\t\n\tMM_openBrWindow('/jsp/PopUp_Graph.jsp?restype='+resType+'&monID='+resID+'&resids='+moId+'&modeOfMon='+mode+'&summarygraphfor=cpucore','ExecutionTimeStatistic','width=900,height=500,top=140,left=200,scrollbars=yes,resizable=yes'); // No I18N\n} \n\nfunction showCompareGraph(resID,resType)\n{\n\tvar count = 0;\n\tvar attrId = 9641;\n\tvar element = document.getElementsByName(\"cpucore_chkbox\");\n\tif (element != null) {\n\t\tvar resIds = \"\";\n\t\tfor(var i=0;i<element.length;i++)\n\t\t{\n\t\t\tif (element[i].checked) {\n\t\t\t\tresIds = resIds + element[i].value + \",\";\t\n\t\t\t\tcount ++;\t\t\t\n\t\t\t}\n\t\t}\n\t\tif (count == 0) {\n\t\t\talert('");
/* 2383 */       out.print(FormatUtil.getString("am.webclient.server.cpucore.usage.compare.jsalert"));
/* 2384 */       out.write("');\n\t\t\treturn;\n\t\t}\n\t\tvar sel = document.getElementById(\"cpuattrbcomp\");\n\t\tif (sel != null) {\n\t\t\tvar selAttrb = sel.value;\n\t\t\tif (selAttrb == \"-1\") {\n\t\t\t\talert('");
/* 2385 */       out.print(FormatUtil.getString("am.webclient.server.cpucore.usage.compare.attribute.notselect.jsalert"));
/* 2386 */       out.write("');\n\t\t\t\treturn;\n\t\t\t} else {\n\t\t\t\tattrId = selAttrb;\n\t\t\t}\n\t\t}\n\t\tresIds = resIds.substring(0,resIds.length-1);\n\t\tfnOpenWindow('/jsp/PopUp_Graph.jsp?restype='+resType+'&monID='+resID+'&resids='+resIds+'&attids='+attrId+'&listsize='+count,'ExecutionTimeStatistic','width=800,height=500,top=200,left=200,scrollbars=yes,resizable=yes'); // No I18N\n\t}\n}\n\n\nfunction selAllCpuCores() \n{\n\tvar head_element = document.getElementsByName(\"cpucore_chkbox_head\");\n\tif (head_element != null){\n\t\tvar element = document.getElementsByName(\"cpucore_chkbox\");\t\t\n\t\t//alert(head_element[0].checked);\n\t\tif(head_element[0].checked) {\n\t\t\tif (element != null) {\t\t\n\t\t\t\tfor(var i=0;i<element.length;i++)\n\t\t\t\t{\n\t\t\t\t\telement[i].checked = true;\n\t\t\t\t}\n\t\t\t}\t\n\t\t} else {\n\t\t\tif (element != null) {\t\t\n\t\t\t\tfor(var i=0;i<element.length;i++)\n\t\t\t\t{\n\t\t\t\t\telement[i].checked = false;\n\t\t\t\t}\n\t\t\t}\n\t\t}\t\t\n\t}\n}\n\nfunction chkToSelAll()\n{\tvar flag = true;\n\tvar element = document.getElementsByName(\"cpucore_chkbox\");\t\n\tif (element != null) \n\t{\t\t\n\t\tfor(var i=0;i<element.length;i++)\n");
/* 2387 */       out.write("\t\t{\n\t\t\tif (!element[i].checked) {\n\t\t\t\tflag = false;\n\t\t\t}\t\t\t\t\n\t\t}\n\t}\n\n\tvar head_element = document.getElementsByName(\"cpucore_chkbox_head\");\n\tif (head_element != null) {\n\t\tif (flag) {\t\t\n\t\t\thead_element[0].checked = true;\n\t\t\t\n\t\t} else {\t\t\n\t\t\thead_element[0].checked = false;\t\n\t\t}\n\t}\n}\n</script>\n\n");
/*      */       
/* 2389 */       String mode = (String)request.getAttribute("mode");
/* 2390 */       String resourcename = request.getParameter("name");
/* 2391 */       String resType = (String)request.getAttribute("hostResType");
/* 2392 */       String hostname = (String)request.getAttribute("hostResName");
/* 2393 */       String haid = (String)request.getAttribute("haid");
/* 2394 */       String displayname = (String)request.getAttribute("displayname");
/* 2395 */       request.setAttribute("name", resourcename);
/* 2396 */       request.setAttribute("haid", request.getParameter("haid"));
/* 2397 */       request.setAttribute("appName", request.getParameter("appName"));
/* 2398 */       String resourceid = (String)request.getAttribute("resourceid");
/*      */       
/*      */ 
/* 2401 */       String tab = "1";
/* 2402 */       String search = null;
/* 2403 */       request.setAttribute("breadcumps", search);
/* 2404 */       request.setAttribute("tabtoselect", tab);
/*      */       
/* 2406 */       out.write(10);
/* 2407 */       out.write(10);
/*      */       
/* 2409 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2410 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2411 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2413 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2414 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2415 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2417 */           out.write(10);
/*      */           
/* 2419 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2420 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2421 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2423 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */           
/* 2425 */           _jspx_th_tiles_005fput_005f0.setValue(com.adventnet.nms.util.NmsUtil.GetString("Windows, Linux ,  AIX , FreeBSD , HP-UX/Tru64 , Mac OS & Solaris Monitoring"));
/* 2426 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2427 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2428 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2431 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2432 */           out.write(10);
/* 2433 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2435 */           out.write(10);
/* 2436 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2438 */           out.write(10);
/* 2439 */           out.write(10);
/* 2440 */           out.write(10);
/* 2441 */           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2443 */           out.write(10);
/*      */           
/* 2445 */           PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2446 */           _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2447 */           _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2449 */           _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */           
/* 2451 */           _jspx_th_tiles_005fput_005f4.setType("string");
/* 2452 */           int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2453 */           if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2454 */             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2455 */               out = _jspx_page_context.pushBody();
/* 2456 */               _jspx_th_tiles_005fput_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2457 */               _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2460 */               out.write("\n<a name=\"top1\"></a>\n");
/* 2461 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2463 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2464 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2465 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f4);
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
/* 2486 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f4);
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
/* 2507 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f4);
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
/* 2528 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f4);
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
/* 2549 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f4);
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
/* 2570 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f4);
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
/* 2591 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f4);
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
/* 2618 */               out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */               
/* 2620 */               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2621 */               _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2622 */               _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 2624 */               _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2625 */               int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2626 */               if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                 for (;;) {
/* 2628 */                   out.write(10);
/*      */                   
/*      */ 
/* 2631 */                   if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                   {
/*      */ 
/* 2634 */                     out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2635 */                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2636 */                     out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2637 */                     out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2638 */                     out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2639 */                     out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2640 */                     out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2641 */                     out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2642 */                     out.write("\n </span></td>\n  <tr>\n  ");
/*      */                     
/* 2644 */                     int failedNumber = 1;
/*      */                     
/* 2646 */                     out.write(10);
/*      */                     
/* 2648 */                     IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2649 */                     _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2650 */                     _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                     
/* 2652 */                     _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                     
/* 2654 */                     _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                     
/* 2656 */                     _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                     
/* 2658 */                     _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2659 */                     int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2660 */                     if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2661 */                       ArrayList row = null;
/* 2662 */                       Integer i = null;
/* 2663 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2664 */                         out = _jspx_page_context.pushBody();
/* 2665 */                         _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2666 */                         _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                       }
/* 2668 */                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2669 */                       i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                       for (;;) {
/* 2671 */                         out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2672 */                         out.print(row.get(0));
/* 2673 */                         out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2674 */                         out.print(row.get(1));
/* 2675 */                         out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                         
/* 2677 */                         if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                         {
/* 2679 */                           request.setAttribute("isDiscoverySuccess", "true");
/*      */                           
/* 2681 */                           out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2682 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2683 */                           out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/* 2688 */                           request.setAttribute("isDiscoverySuccess", "false");
/*      */                           
/* 2690 */                           out.write("\n      <img alt=\"");
/* 2691 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2692 */                           out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                         }
/*      */                         
/*      */ 
/* 2696 */                         out.write("\n      <span class=\"bodytextbold\">");
/* 2697 */                         out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2698 */                         out.write("</span> </td>\n\n      ");
/*      */                         
/* 2700 */                         pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                         
/* 2702 */                         out.write("\n                     ");
/*      */                         
/* 2704 */                         IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2705 */                         _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2706 */                         _jspx_th_c_005fif_005f2.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                         
/* 2708 */                         _jspx_th_c_005fif_005f2.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2709 */                         int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2710 */                         if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                           for (;;) {
/* 2712 */                             out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2713 */                             out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2714 */                             out.write("\n                                 ");
/* 2715 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2716 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2720 */                         if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2721 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                         }
/*      */                         
/* 2724 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2725 */                         out.write("\n                                       ");
/*      */                         
/* 2727 */                         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2728 */                         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2729 */                         _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                         
/* 2731 */                         _jspx_th_c_005fif_005f3.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2732 */                         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2733 */                         if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                           for (;;) {
/* 2735 */                             out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2736 */                             out.print(row.get(3));
/* 2737 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                             
/* 2739 */                             if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                             {
/* 2741 */                               if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                               {
/* 2743 */                                 String fWhr = request.getParameter("hideFieldsForIT360");
/* 2744 */                                 if (fWhr == null)
/*      */                                 {
/* 2746 */                                   fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                 }
/*      */                                 
/* 2749 */                                 if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2750 */                                   (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                 {
/* 2752 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2753 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2754 */                                   out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                 }
/*      */                               } }
/* 2757 */                             if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                             {
/* 2759 */                               failedNumber++;
/*      */                               
/*      */ 
/* 2762 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2763 */                               out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/* 2764 */                               out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                             }
/* 2766 */                             out.write("\n                                                   ");
/* 2767 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2768 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2772 */                         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2773 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                         }
/*      */                         
/* 2776 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2777 */                         out.write(10);
/* 2778 */                         out.write(10);
/* 2779 */                         out.write(10);
/*      */                         
/*      */ 
/* 2782 */                         if (row.size() > 4)
/*      */                         {
/*      */ 
/* 2785 */                           out.write("<br>\n");
/* 2786 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2787 */                           out.write(10);
/*      */                         }
/*      */                         
/*      */ 
/* 2791 */                         out.write("\n</td>\n\n</tr>\n");
/* 2792 */                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2793 */                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2794 */                         i = (Integer)_jspx_page_context.findAttribute("i");
/* 2795 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2798 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2799 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2802 */                     if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2803 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                     }
/*      */                     
/* 2806 */                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2807 */                     out.write("\n</table>\n");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/* 2812 */                     ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                     
/* 2814 */                     out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2815 */                     String mtype = (String)request.getAttribute("type");
/* 2816 */                     out.write(10);
/* 2817 */                     if (mtype.equals("File System Monitor")) {
/* 2818 */                       out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2819 */                       out.print(FormatUtil.getString("File/Directory Name"));
/* 2820 */                       out.write("</span> </td>\n");
/* 2821 */                     } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2822 */                       out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2823 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2824 */                       out.write("</span> </td>\n");
/*      */                     } else {
/* 2826 */                       out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2827 */                       out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2828 */                       out.write("</span> </td>\n");
/*      */                     }
/* 2830 */                     out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2831 */                     out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2832 */                     out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2833 */                     out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2834 */                     out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2835 */                     out.print(al1.get(0));
/* 2836 */                     out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                     
/* 2838 */                     if (al1.get(1).equals("Success"))
/*      */                     {
/* 2840 */                       request.setAttribute("isDiscoverySuccess", "true");
/*      */                       
/* 2842 */                       out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2843 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2844 */                       out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 2849 */                       request.setAttribute("isDiscoverySuccess", "false");
/*      */                       
/*      */ 
/* 2852 */                       out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                     }
/*      */                     
/*      */ 
/* 2856 */                     out.write("\n<span class=\"bodytextbold\">");
/* 2857 */                     out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2858 */                     out.write("</span> </td>\n");
/*      */                     
/* 2860 */                     if (al1.get(1).equals("Success"))
/*      */                     {
/* 2862 */                       boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2863 */                       if (isAdminServer) {
/* 2864 */                         String masDisplayName = (String)al1.get(3);
/* 2865 */                         String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                         
/* 2867 */                         out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2868 */                         out.print(format);
/* 2869 */                         out.write("</td>\n");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2873 */                         out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2874 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2875 */                         out.write("<br> ");
/* 2876 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2877 */                         out.write("</td>\n");
/*      */                       }
/*      */                       
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 2884 */                       out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2885 */                       out.print(al1.get(2));
/* 2886 */                       out.write("</span></td>\n");
/*      */                     }
/*      */                     
/*      */ 
/* 2890 */                     out.write("\n  </tr>\n</table>\n\n");
/*      */                   }
/*      */                   
/*      */ 
/* 2894 */                   out.write(10);
/* 2895 */                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2896 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2900 */               if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2901 */                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */               }
/*      */               
/* 2904 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2905 */               out.write(10);
/* 2906 */               out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"84%\" valign=\"top\">\n\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n");
/*      */               
/* 2908 */               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2909 */               String appId = request.getParameter("haid");
/* 2910 */               String haName = null;
/* 2911 */               if (appId != null)
/*      */               {
/*      */                 try
/*      */                 {
/* 2915 */                   if (Integer.parseInt(appId) < 0)
/*      */                   {
/* 2917 */                     throw new NumberFormatException();
/*      */                   }
/* 2919 */                   haName = (String)ht.get(appId);
/*      */                 }
/*      */                 catch (NumberFormatException ex) {
/* 2922 */                   System.out.println("Not a Number: " + ex.getMessage());
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 2927 */               String systemType = resType;
/* 2928 */               if (systemType != null)
/*      */               {
/* 2930 */                 if (systemType.startsWith("Windows"))
/*      */                 {
/* 2932 */                   systemType = "Windows";
/*      */                 }
/* 2934 */                 else if (systemType.equals("SUN"))
/*      */                 {
/* 2936 */                   systemType = "Sun Solaris";
/*      */                 }
/* 2938 */                 else if ((systemType.equals("HP-UX")) || (systemType.equals("HP-TRU64")))
/*      */                 {
/* 2940 */                   systemType = "HP-UX / Tru64";
/*      */                 }
/* 2942 */                 else if (systemType.equals("FreeBSD")) {
/* 2943 */                   systemType = "FreeBSD / OpenBSD";
/*      */                 }
/*      */               }
/*      */               
/* 2947 */               out.write("\n\t\t\t\t");
/*      */               
/* 2949 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2950 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2951 */               _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 2953 */               _jspx_th_c_005fif_005f4.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2954 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2955 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                 for (;;) {
/* 2957 */                   out.write("\n\t\t\t          <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2958 */                   out.print(BreadcrumbUtil.getHomePage(request));
/* 2959 */                   out.write("\n\t\t\t            &gt; ");
/* 2960 */                   out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2961 */                   out.write("\n\t\t\t            &gt; <span class=\"bcactive\"> ");
/* 2962 */                   out.print(displayname);
/* 2963 */                   out.write("</span>\n\t\t\t           </td>\n\t\t\t\t");
/* 2964 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2965 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2969 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2970 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */               }
/*      */               
/* 2973 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2974 */               out.write("\n\t\t\t\t\n\t\t\t\t");
/*      */               
/* 2976 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2977 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2978 */               _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 2980 */               _jspx_th_c_005fif_005f5.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2981 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2982 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/* 2984 */                   out.write("\n\t\t\t        <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2985 */                   out.print(BreadcrumbUtil.getMonitorsPage());
/* 2986 */                   out.write("\n\t\t\t          &gt; ");
/* 2987 */                   out.print(BreadcrumbUtil.getMonitorResourceTypes(systemType));
/* 2988 */                   out.write(" &gt;\n\t\t\t          <span class=\"bcactive\"> ");
/* 2989 */                   out.print(displayname);
/* 2990 */                   out.write(" </span>\n\t\t\t        </td>\n\t\t\t\t");
/* 2991 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2992 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2996 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2997 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/* 3000 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3001 */               out.write("\n\t\t\t</tr>\n\t\t</table>\t\t\n\t\n\t");
/* 3002 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                 return;
/* 3004 */               out.write("\n\n\n\t\t<table border=\"0\" width=\"100%\">\n\t\t\t<tr>\n\t\t\t\t<td>\n");
/*      */               
/*      */ 
/*      */ 
/* 3008 */               if ((resType != null) && (resType.toLowerCase().startsWith("windows")))
/*      */               {
/* 3010 */                 out.write(10);
/* 3011 */                 out.write(9);
/* 3012 */                 JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview,am.webclient.hostinnertabs.CpuCore,am.webclient.hostinnertabs.disk,am.webclient.hostinnertabs.network,am.webclient.hostinnertabs.eventlog,am.webclient.hostinnertabs.hardware,am.webclient.hostinnertabs.config,am.webclient.hostinnertabs.scheduledtasks", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview,am.webclient.hostinnertabs.CpuCore,am.webclient.hostinnertabs.disk,am.webclient.hostinnertabs.network,am.webclient.hostinnertabs.eventlog,am.webclient.hostinnertabs.hardware,am.webclient.hostinnertabs.config,am.webclient.hostinnertabs.scheduledtasks", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHostOverviewDetails,getCPUCoreDetails,getDiskDetails,getNetworkDetails,getEventLogDetails,getHardwareHealthDetails,getConfigDetails,getScheduledTasksDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/* 3013 */                 out.write(32);
/* 3014 */                 out.write(10);
/*      */               }
/* 3016 */               else if ((mode != null) && (mode.equalsIgnoreCase("SNMP")))
/*      */               {
/* 3018 */                 out.write(10);
/* 3019 */                 JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview,am.webclient.hostinnertabs.CpuCore,am.webclient.hostinnertabs.disk,am.webclient.hostinnertabs.network,am.webclient.hostinnertabs.hardware,am.webclient.hostinnertabs.config", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview,am.webclient.hostinnertabs.CpuCore,am.webclient.hostinnertabs.disk,am.webclient.hostinnertabs.network,am.webclient.hostinnertabs.hardware,am.webclient.hostinnertabs.config", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHostOverviewDetails,getCPUCoreDetails,getDiskDetails,getNetworkDetails,getHardwareHealthDetails,getConfigDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/* 3020 */                 out.write(10);
/*      */ 
/*      */               }
/* 3023 */               else if ((resType.equalsIgnoreCase("aix")) || (resType.equalsIgnoreCase("hp-ux")))
/*      */               {
/* 3025 */                 out.write(10);
/* 3026 */                 JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview,am.webclient.hostinnertabs.CpuCore,am.webclient.hostinnertabs.disk", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview,am.webclient.hostinnertabs.CpuCore,am.webclient.hostinnertabs.disk", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHostOverviewDetails,getCPUCoreDetails,getDiskDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/* 3027 */                 out.write(10);
/*      */               }
/*      */               else
/*      */               {
/* 3031 */                 out.write(10);
/* 3032 */                 JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview,am.webclient.hostinnertabs.CpuCore,am.webclient.hostinnertabs.disk,am.webclient.hostinnertabs.config", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview,am.webclient.hostinnertabs.CpuCore,am.webclient.hostinnertabs.disk,am.webclient.hostinnertabs.config", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getHostOverviewDetails,getCPUCoreDetails,getDiskDetails,getConfigDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.hostinnertabs.overview", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/* 3033 */                 out.write(10);
/*      */               }
/*      */               
/*      */ 
/* 3037 */               out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\n\t\t<input type=\"hidden\" name=\"resourceType\" id=\"resourceType\" value='");
/* 3038 */               out.print(resType);
/* 3039 */               out.write("'>\n\t\t<input type=\"hidden\" name=\"modeOfMon\" id=\"modeOfMon\" value='");
/* 3040 */               out.print(mode);
/* 3041 */               out.write("'>\n\t\t<br>\n\t\t<table border=\"0\" width=\"100%\">\n\t\t\t<tr>\n\t\t\t\t<td>\t\t\t\t\n\t\t\t\t<div id=\"data\" style=\"display:none\"></div>\t\t\t\t\n\t\t\t\t<br><div id=\"loadingg1\" style=\"display:none\" class=\"bodytext\">");
/* 3042 */               out.print(FormatUtil.getString("am.webclient.host.servicesinhost.fetchingmessage"));
/* 3043 */               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n\t\t\t\t<div id=\"monitors_in_the_system\" style=\"display:none\"></div>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t\t</td>\n\t</tr>\n</table>\n<script language=\"JavaScript\">\nvar sel_tab = getCookie('");
/* 3044 */               out.print(resourceid + "_host_seltab");
/* 3045 */               out.write("');\nif (sel_tab == null || sel_tab == \"\") {\n\tsel_tab = \"0\";\n}\nvar id=\"am.webclient.hostinnertabs.overview\";\nif (sel_tab==\"0\") {\t\t\n\tgetHostOverviewDetails(\"");
/* 3046 */               out.print(resourceid);
/* 3047 */               out.write(34);
/* 3048 */               out.write(44);
/* 3049 */               out.write(34);
/* 3050 */               out.print(resourcename);
/* 3051 */               out.write("\");\n} else if (sel_tab==\"1\") {\n\tid=\"am.webclient.hostinnertabs.CpuCore\";\n\tgetCPUCoreDetails(\"");
/* 3052 */               out.print(resourceid);
/* 3053 */               out.write(34);
/* 3054 */               out.write(44);
/* 3055 */               out.write(34);
/* 3056 */               out.print(resourcename);
/* 3057 */               out.write("\");\n} else if (sel_tab==\"2\") {\n\tid=\"am.webclient.hostinnertabs.disk\";\n\tgetDiskDetails(\"");
/* 3058 */               out.print(resourceid);
/* 3059 */               out.write(34);
/* 3060 */               out.write(44);
/* 3061 */               out.write(34);
/* 3062 */               out.print(resourcename);
/* 3063 */               out.write("\");\n} else if (sel_tab==\"3\") {\n\tid=\"am.webclient.hostinnertabs.network\";\n\tgetNetworkDetails(\"");
/* 3064 */               out.print(resourceid);
/* 3065 */               out.write(34);
/* 3066 */               out.write(44);
/* 3067 */               out.write(34);
/* 3068 */               out.print(resourcename);
/* 3069 */               out.write("\");\n} else if (sel_tab==\"4\") {\n\tid=\"am.webclient.hostinnertabs.memory\";\n\tgetMemoryDetails(\"");
/* 3070 */               out.print(resourceid);
/* 3071 */               out.write(34);
/* 3072 */               out.write(44);
/* 3073 */               out.write(34);
/* 3074 */               out.print(resourcename);
/* 3075 */               out.write("\");\n} else if (sel_tab==\"5\") {\n\tid=\"am.webclient.hostinnertabs.eventlog\";\n\tgetEventLogDetails(\"");
/* 3076 */               out.print(resourceid);
/* 3077 */               out.write(34);
/* 3078 */               out.write(44);
/* 3079 */               out.write(34);
/* 3080 */               out.print(resourcename);
/* 3081 */               out.write("\");\n} else if (sel_tab==\"6\") {\n\tid=\"am.webclient.hostinnertabs.config\";\n\tgetConfigDetails(\"");
/* 3082 */               out.print(resourceid);
/* 3083 */               out.write(34);
/* 3084 */               out.write(44);
/* 3085 */               out.write(34);
/* 3086 */               out.print(resourcename);
/* 3087 */               out.write("\");\n} else if (sel_tab==\"7\") {\n\tid=\"am.webclient.hostinnertabs.hardware\"; // NO I18N\n\tgetHardwareHealthDetails(\"");
/* 3088 */               out.print(resourceid);
/* 3089 */               out.write(34);
/* 3090 */               out.write(44);
/* 3091 */               out.write(34);
/* 3092 */               out.print(resourcename);
/* 3093 */               out.write("\");\n}\nelse if (sel_tab==\"8\") {\n\tid=\"Scheduled Tasks\"; // NO I18N\n\tgetScheduledTasksDetails(\"");
/* 3094 */               out.print(resourceid);
/* 3095 */               out.write(34);
/* 3096 */               out.write(44);
/* 3097 */               out.write(34);
/* 3098 */               out.print(resourcename);
/* 3099 */               out.write("\");\n}\nvar number = $('#tab_random_number').val();\nSetTabStyle(id,'InnerTab'+number);\n</script>\n");
/* 3100 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 3101 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3104 */             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3105 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3108 */           if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3109 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */           }
/*      */           
/* 3112 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 3113 */           out.write(10);
/* 3114 */           if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3116 */           out.write(10);
/* 3117 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3118 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3122 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3123 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3126 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3127 */         out.write(10);
/*      */       }
/* 3129 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3130 */         out = _jspx_out;
/* 3131 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3132 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3133 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3136 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3142 */     PageContext pageContext = _jspx_page_context;
/* 3143 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3145 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3146 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3147 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 3149 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3150 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3152 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3153 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3155 */           out.write(10);
/* 3156 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3157 */             return true;
/* 3158 */           out.write(10);
/* 3159 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3160 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3164 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3165 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3168 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 3169 */         out = _jspx_page_context.popBody(); }
/* 3170 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3172 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3173 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3175 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3180 */     PageContext pageContext = _jspx_page_context;
/* 3181 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3183 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3184 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3185 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3187 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3189 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3190 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3191 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3192 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3193 */       return true;
/*      */     }
/* 3195 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3196 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3201 */     PageContext pageContext = _jspx_page_context;
/* 3202 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3204 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3205 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3206 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3208 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3209 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3210 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3212 */         out.write(10);
/* 3213 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3214 */           return true;
/* 3215 */         out.write(10);
/* 3216 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3217 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3221 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3222 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3223 */       return true;
/*      */     }
/* 3225 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3231 */     PageContext pageContext = _jspx_page_context;
/* 3232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3234 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3235 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3236 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3238 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3240 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 3241 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3242 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3243 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3244 */       return true;
/*      */     }
/* 3246 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3247 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3252 */     PageContext pageContext = _jspx_page_context;
/* 3253 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3255 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3256 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3257 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3259 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3260 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3261 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3263 */         out.write(10);
/* 3264 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3265 */           return true;
/* 3266 */         out.write(10);
/* 3267 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3268 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3272 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3273 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3274 */       return true;
/*      */     }
/* 3276 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3277 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3282 */     PageContext pageContext = _jspx_page_context;
/* 3283 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3285 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3286 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3287 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3289 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 3291 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3292 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3293 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3294 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3295 */       return true;
/*      */     }
/* 3297 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3303 */     PageContext pageContext = _jspx_page_context;
/* 3304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3306 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3307 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3308 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3310 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 3312 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/HostLeftPage.jsp");
/* 3313 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3314 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3315 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3316 */       return true;
/*      */     }
/* 3318 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3319 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3324 */     PageContext pageContext = _jspx_page_context;
/* 3325 */     JspWriter out = _jspx_page_context.getOut();
/* 3326 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 3327 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 3329 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3330 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3331 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 3333 */     _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,USERS,OPERATOR,DEMO,ENTERPRISEADMIN");
/* 3334 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3335 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3337 */         out.write(10);
/* 3338 */         out.write(9);
/* 3339 */         out.write(9);
/* 3340 */         if (_jspx_meth_c_005fif_005f6(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/* 3341 */           return true;
/* 3342 */         out.write("\n\n\t\t");
/* 3343 */         if (_jspx_meth_c_005fif_005f7(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/* 3344 */           return true;
/* 3345 */         out.write("\n\n\t\t");
/* 3346 */         if (_jspx_meth_c_005fif_005f8(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/* 3347 */           return true;
/* 3348 */         out.write("\n\n\t\t");
/* 3349 */         if (_jspx_meth_c_005fif_005f9(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/* 3350 */           return true;
/* 3351 */         out.write(10);
/* 3352 */         out.write(9);
/* 3353 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3354 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3358 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3359 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3360 */       return true;
/*      */     }
/* 3362 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3368 */     PageContext pageContext = _jspx_page_context;
/* 3369 */     JspWriter out = _jspx_page_context.getOut();
/* 3370 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 3371 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 3373 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3374 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3375 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 3377 */     _jspx_th_c_005fif_005f6.setTest("${!empty param.showconfigdiv && param.showconfigdiv=='true'}");
/* 3378 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3379 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 3381 */         out.write("\n\t\t<div id=\"edit\" style=\"DISPLAY: block\">\n\t\t");
/* 3382 */         JspRuntimeLibrary.include(request, response, "/jsp/HostResourceConfig.jsp?reconfigure=true", out, false);
/* 3383 */         out.write("\n\t\t<br>\n\t\t</div>\n\t\t");
/* 3384 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3385 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3389 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3390 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3391 */       return true;
/*      */     }
/* 3393 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3394 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3399 */     PageContext pageContext = _jspx_page_context;
/* 3400 */     JspWriter out = _jspx_page_context.getOut();
/* 3401 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 3402 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 3404 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3405 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3406 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 3408 */     _jspx_th_c_005fif_005f7.setTest("${empty param.showconfigdiv && param.showconfigdiv!='true'}");
/* 3409 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3410 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 3412 */         out.write("\n\t\t<div id=\"edit\" style=\"DISPLAY: none\">\n\t\t");
/* 3413 */         JspRuntimeLibrary.include(request, response, "/HostResource.do?reconfigure=true&include=true&configure=false", out, false);
/* 3414 */         out.write(10);
/* 3415 */         out.write(9);
/* 3416 */         out.write(9);
/* 3417 */         JspRuntimeLibrary.include(request, response, "/jsp/HostResourceConfig.jsp?reconfigure=true", out, false);
/* 3418 */         out.write("\n\t\t<br>\n\t\t</div>\n\t\t");
/* 3419 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3420 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3424 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3425 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3426 */       return true;
/*      */     }
/* 3428 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3429 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3434 */     PageContext pageContext = _jspx_page_context;
/* 3435 */     JspWriter out = _jspx_page_context.getOut();
/* 3436 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 3437 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 3439 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3440 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3441 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 3443 */     _jspx_th_c_005fif_005f8.setTest("${!empty param.showadddiv && param.showadddiv=='true'}");
/* 3444 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3445 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 3447 */         out.write("\n\t\t<div id=\"addprocess\" style=\"DISPLAY: block\">\n\t\t");
/* 3448 */         JspRuntimeLibrary.include(request, response, "/jsp/HostResourceConfig.jsp?addProcesScreen=true&addProcess=true", out, false);
/* 3449 */         out.write("\n\t\t<br>\n\t\t</div>\n\t\t");
/* 3450 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3451 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3455 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3456 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3457 */       return true;
/*      */     }
/* 3459 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3460 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3465 */     PageContext pageContext = _jspx_page_context;
/* 3466 */     JspWriter out = _jspx_page_context.getOut();
/* 3467 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 3468 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 3470 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3471 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3472 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 3474 */     _jspx_th_c_005fif_005f9.setTest("${empty param.showadddiv && param.showadddiv!='true'}");
/* 3475 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3476 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 3478 */         out.write("\n\t\t<div id=\"addprocess\" style=\"DISPLAY: none\">\n\t\t");
/* 3479 */         JspRuntimeLibrary.include(request, response, "/jsp/HostResourceConfig.jsp?addProcesScreen=true&addProcess=true", out, false);
/* 3480 */         out.write("\t\t\n\t\t</div>\n\t\t");
/* 3481 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3482 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3486 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3487 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3488 */       return true;
/*      */     }
/* 3490 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3491 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3496 */     PageContext pageContext = _jspx_page_context;
/* 3497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3499 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3500 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 3501 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3503 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 3505 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 3506 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 3507 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 3508 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 3509 */       return true;
/*      */     }
/* 3511 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 3512 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostResource_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */