/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.actions.ShowResourceDetails;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
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
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletConfig;
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
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class header_005fconsole_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  680 */       val = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
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
/*  911 */     Properties temp = FaultUtil.getStatus(entitylist, false);
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
/*  925 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/* 1030 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1031 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
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
/* 1301 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
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
/* 1999 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
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
/* 2190 */   String tab_select = "topheadermenucurrent";
/* 2191 */   String tab_normal = "topheadermenu";
/*      */   
/*      */   private String getIndex(int index, String tabToSelect) {
/* 2194 */     int temp = Integer.parseInt(tabToSelect);
/* 2195 */     if (temp == index) {
/* 2196 */       return this.tab_select;
/*      */     }
/* 2198 */     return this.tab_normal; }
/* 2199 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2205 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2206 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2207 */     _jspx_dependants.put("/jsp/includes/monitortypes.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2224 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2238 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/* 2250 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2257 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2260 */     JspWriter out = null;
/* 2261 */     Object page = this;
/* 2262 */     JspWriter _jspx_out = null;
/* 2263 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2267 */       response.setContentType("text/html;charset=UTF-8");
/* 2268 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2270 */       _jspx_page_context = pageContext;
/* 2271 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2272 */       ServletConfig config = pageContext.getServletConfig();
/* 2273 */       session = pageContext.getSession();
/* 2274 */       out = pageContext.getOut();
/* 2275 */       _jspx_out = out;
/*      */       
/* 2277 */       out.write("\n\n\n\n\n\n\n\n\n");
/* 2278 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2280 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2281 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2283 */       out.write(10);
/* 2284 */       out.write(10);
/* 2285 */       out.write(10);
/* 2286 */       out.write("\n\n<div id=\"dubform\" style='display:none'>\n\t<form></form>\n</div>\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/dropdown.js\"></SCRIPT>\n<script language=\"JavaScript\" type=\"text/JavaScript\">\n<!--\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\n  window.open(theURL,winName,features);\n}\n//-->\nfunction getNewWindow(url, title, width, height, params) {\n\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width;\n\t\t\n        if (params) { winParms += \",\" + params; }\n\t\t\n        try {\n             return (newwindow = window.open(url, title, winParms));\n            \n            \n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n            \n        }\n}\n\nfunction openPrintWindow(title, width, height, params) {\n\t\n    var    url = window.location.href;\n    ");
/*      */       
/* 2288 */       Enumeration en = request.getParameterNames();
/*      */       
/* 2290 */       out.write("\n    if (url.indexOf(\"showReports.do\") != -1 && url.indexOf(\"actionMethod=getReportIndex\") == -1 ) {\n    \t\n\tdocument.forms[1].target=title;\n        document.forms[1].PRINTER_FRIENDLY.value='true';\n    \n        document.forms[1].target='_top';\n        document.forms[1].PRINTER_FRIENDLY.value='false';\n               ");
/*      */       
/*      */ 
/* 2293 */       out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/* 2294 */       while (en.hasMoreElements()) {
/* 2295 */         String reqKey = (String)en.nextElement();
/* 2296 */         String reqValue = request.getParameter(reqKey);
/* 2297 */         if ((!reqKey.equals("message")) && (!reqKey.equals("mailMsg")))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2303 */           out.print("&" + reqKey + "=" + reqValue);
/*      */         }
/*      */       }
/* 2306 */       out.println("\";");
/*      */       
/* 2308 */       out.write("\n                    if (url.indexOf(\"?\") != -1) {\n\t                url=url + \"&\" + urlToAdd;\n\t            } else {\n\t                url=url + \"?\" + urlToAdd;\n            }\n        var newwindow = getNewWindow(url,title,width,height,params);\n       \n        newwindow.focus();\n        return newwindow;\n    } else if (url.indexOf(\"AMAlarmView.do\") != -1 || url.indexOf(\"AlarmView.do\") != -1 ) {\n       ");
/*      */       
/*      */ 
/* 2311 */       out.print("var urlToAdd =\"PRINTER_FRIENDLY=true");
/* 2312 */       while (en.hasMoreElements()) {
/* 2313 */         String reqKey = (String)en.nextElement();
/* 2314 */         String reqValue = request.getParameter(reqKey);
/* 2315 */         if (!reqKey.equals("message"))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2321 */           out.print("&" + reqKey + "=" + reqValue);
/*      */         }
/*      */       }
/* 2324 */       out.println("\";");
/*      */       
/* 2326 */       out.write("\n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&\" + urlToAdd;\n            } else {\n                url=url + \"?\" + urlToAdd;\n            }\n\t\t\t\n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n    } else {\n   \n            if (url.indexOf(\"?\") != -1) {\n                url=url + \"&PRINTER_FRIENDLY=true\";\n            } else {\n                url=url + \"?PRINTER_FRIENDLY=true\";\n            }\n            \n            var newwindow = getNewWindow(url,title,width,height,params);\n            newwindow.focus();\n            return newwindow;\n\n    }\n   \n}\n\nfunction Onimg(id,color)\n{\n  if(color == 1)\n  {\n  document.getElementById(id).src=\"/images/red-bar-ro.gif\";\n  }\n  if(color == 4)\n  {\n  document.getElementById(id).src=\"/images/orange-bar-ro.gif\";\n  }\n  if(color == 5)\n  {\n  document.getElementById(id).src=\"/images/green-bar-ro.gif\";\n  }\n}\nfunction Offimg(id,color)\n{\n  if(color == 1)\n  {\n  document.getElementById(id).src=\"/images/red-bar.gif\";\n");
/* 2327 */       out.write("  }\n  if(color == 4)\n  {\n  document.getElementById(id).src=\"/images/orange-bar.gif\";\n  }\n  if(color == 5)\n  {\n  document.getElementById(id).src=\"/images/green-bar.gif\";\n  }\n}\n\n\n</script>\n<Script>\n  \nfunction showmenu(){\n\n\nif(document.getElementById(\"newmonitormenu\").style.display == 'none'){\n\n\t\t\t\n\t\t\tdocument.getElementById(\"newmonitorframediv\").style.display='block';\n\t\t\tdocument.getElementById(\"newmonitormenu\").style.display='block';\n\t\t\t\n\t\t\t}\n\t\telse\n\t\t\t{\n\t\t\tdocument.getElementById(\"newmonitorframediv\").style.display='none';\n\t\t\tdocument.getElementById(\"newmonitormenu\").style.display='none';\n\t\t\t}\n    \n }\n  \n\n  \n    \n  </script>\n\n<html>\n<head>\n\t\t\n</head>\n\n");
/* 2328 */       out.write(10);
/* 2329 */       out.write(10);
/* 2330 */       out.write(10);
/* 2331 */       out.write(10);
/*      */       
/*      */ 
/* 2334 */       Enumeration enumer = request.getParameterNames();
/* 2335 */       String rparams = "";
/* 2336 */       while (enumer.hasMoreElements()) {
/* 2337 */         String name = (String)enumer.nextElement();
/* 2338 */         rparams = rparams + "&" + name + "=" + request.getParameter(name);
/*      */       }
/*      */       
/*      */ 
/* 2342 */       String[] imageSuffix = { "nor", "nor", "nor", "nor", "nor", "nor", "nor" };
/* 2343 */       String tabToSelect = request.getParameter("tabtoselect");
/*      */       
/* 2345 */       if (tabToSelect == null)
/*      */       {
/* 2347 */         tabToSelect = "0";
/*      */       }
/* 2349 */       if (request.getParameter("wiz") != null)
/*      */       {
/* 2351 */         tabToSelect = "0";
/*      */       }
/*      */       
/*      */ 
/* 2355 */       int index = 0;
/* 2356 */       if (tabToSelect != null)
/*      */       {
/* 2358 */         index = Integer.parseInt(tabToSelect);
/* 2359 */         imageSuffix[index] = "sel";
/*      */       }
/* 2361 */       String uri = (String)request.getAttribute("uri");
/* 2362 */       String qs = (String)request.getAttribute("qs");
/* 2363 */       String context = uri + "?" + qs;
/* 2364 */       String searchQuery = request.getParameter("query");
/* 2365 */       if ((searchQuery == null) || (uri.indexOf("Search.do") == -1)) {
/* 2366 */         searchQuery = "";
/*      */       }
/* 2368 */       pageContext.setAttribute("showintrotab", "true", 3);
/*      */       
/*      */ 
/* 2371 */       out.write("\n<body>\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" background=\"images/spacer.gif\" id=\"topheader\">\n\t\t<tbody>\n\t\t\n\t\t\t\t\n    <tr> \n      <td valign=\"top\" align=\"left\" >\n\t  \n\t  \n");
/* 2372 */       if (!OEMUtil.isRemove("hide.header")) {
/* 2373 */         out.write("\n\t  \n\t  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n          <tbody><tr>\n\t\t      <td class=\"topheadermenufirst\"><span class=\"topmenuspan\"/></td>\n\t\t\n\t\t<!-- <td class= ");
/* 2374 */         out.print(getIndex(5, tabToSelect));
/* 2375 */         out.write(" ><span class=\"topmenuspan\"><a class=\"topmenufont\" href=\"/index.do\" >");
/* 2376 */         out.print(FormatUtil.getString("am.webclient.introtab.text"));
/* 2377 */         out.write("</a></span></td>-->\n\t\t<td class= ");
/* 2378 */         out.print(getIndex(0, tabToSelect));
/* 2379 */         out.write(" ><span class=\"topmenuspan\">  <a class=\"topmenufont\" href=\"/applications.do\">");
/* 2380 */         out.print(FormatUtil.getString("Dashboard"));
/* 2381 */         out.write("</a></span></td>\n\t\t");
/*      */         
/* 2383 */         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2384 */         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2385 */         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */         
/* 2387 */         _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 2388 */         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2389 */         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */           for (;;) {
/* 2391 */             out.write("\n\t\t<td class= ");
/* 2392 */             out.print(getIndex(1, tabToSelect));
/* 2393 */             out.write("><span class=\"topmenuspan\">\n\t\t");
/* 2394 */             if (_jspx_meth_c_005fchoose_005f0(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */               return;
/* 2396 */             out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 2397 */             out.write(" </a></span>\t\t           \n\t\t");
/* 2398 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2399 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2403 */         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2404 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */         }
/*      */         
/* 2407 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2408 */         out.write(10);
/* 2409 */         out.write(9);
/* 2410 */         out.write(9);
/*      */         
/* 2412 */         NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2413 */         _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2414 */         _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */         
/* 2416 */         _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 2417 */         int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2418 */         if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */           for (;;) {
/* 2420 */             out.write("\n\t\t<td class= ");
/* 2421 */             out.print(getIndex(1, tabToSelect));
/* 2422 */             out.write(" ><span class=\"topmenuspan\"><a class=\"topmenufont\" href=\"/showresource.do?group=All&method=");
/* 2423 */             if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/*      */               return;
/* 2425 */             out.write(34);
/* 2426 */             out.write(62);
/* 2427 */             out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 2428 */             out.write("</a></span></td> \n\t\t");
/* 2429 */             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2430 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2434 */         if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2435 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */         }
/*      */         
/* 2438 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2439 */         out.write("\n\t\t<!-- <td class= ");
/* 2440 */         out.print(getIndex(1, tabToSelect));
/* 2441 */         out.write(" ><span class=\"topmenuspan\"><a class=\"topmenufont\" href=\"/showresource.do?group=All&method=showResourceTypes\">");
/* 2442 */         out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 2443 */         out.write("</a></span></td> -->\n\t\t<td class= ");
/* 2444 */         out.print(getIndex(2, tabToSelect));
/* 2445 */         out.write(" ><span class=\"topmenuspan\"><a class=\"topmenufont\" href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.5&header=All Alerts-Critical and Warning\">");
/* 2446 */         out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/* 2447 */         out.write("</a></span></td>\n\t\t<td class= ");
/* 2448 */         out.print(getIndex(4, tabToSelect));
/* 2449 */         out.write(" ><span class=\"topmenuspan\"><a class=\"topmenufont\" href=\"/showReports.do?actionMethod=getReportIndex\"> ");
/* 2450 */         out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/* 2451 */         out.write("</a></span></td>\n\t\t<!--<td class= ");
/* 2452 */         out.print(getIndex(6, tabToSelect));
/* 2453 */         out.write(" ><span class=\"topmenuspan\"><a class=\"topmenufont\" href=\"/common/serverinfo.do\">");
/* 2454 */         out.print(FormatUtil.getString("am.webclient.supporttab.text"));
/* 2455 */         out.write("</a></span></td>\n\t\t<td class= ");
/* 2456 */         out.print(getIndex(3, tabToSelect));
/* 2457 */         out.write(" ><span class=\"topmenuspan\"><a class=\"topmenufont\" href=\"/showTile.do?TileName=Tile.AdminConf\">");
/* 2458 */         out.print(FormatUtil.getString("am.webclient.admintab.text"));
/* 2459 */         out.write("</a></span></td>-->\n\t\t<td align=\"right\" class=\"topheadermenulast\">\n\t\t<div class=\"topsearchdiv\" id=\"topsearch\" >\n\t\t\n\t\t</div> \n\t\t</td>\n\t\t</tr>\n\t\t</tbody></table> ");
/*      */       }
/* 2461 */       out.write("\n  <div id=\"newmonitorframediv\" style=\"display:none\"><iframe style=\"position:absolute;left:0px; top:155px; width:100%; height:280px; z-index:2;\" id=\"newmonitorframe\" frameborder=\"0\"></iframe></div>\n  <div id=\"newmonitormenu\" style=\"position:absolute; left:0px; top:61px; width:100%; height:280px; z-index:3; display:none\" class=\"monitorsmenu\">");
/* 2462 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/* 2463 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */         return;
/* 2465 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */       
/* 2467 */       ShowResourceDetails sh = new ShowResourceDetails();
/* 2468 */       List list = new ArrayList();
/* 2469 */       boolean listMonitorsByorder = (request.getParameter("listByOrder") != null) && (request.getParameter("listByOrder").equals("true"));
/* 2470 */       list = listMonitorsByorder == true ? sh.getMonitorTypesforNewMonitor(true) : sh.getMonitorTypesforNewMonitor();
/* 2471 */       ConfMonitorConfiguration conf = ConfMonitorConfiguration.getInstance();
/* 2472 */       String isAgent = "NO";
/* 2473 */       TreeMap eumMonitors = new TreeMap();
/* 2474 */       pageContext.setAttribute("eumMonitors", eumMonitors);
/* 2475 */       int totCount = 21;
/* 2476 */       int dispCount = 0;
/*      */       
/* 2478 */       out.write(10);
/* 2479 */       out.write(10);
/* 2480 */       out.write(9);
/* 2481 */       String category = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 2482 */       int colcount = 0; for (int i = 0; i < list.size() - 2; i++)
/*      */       {
/* 2484 */         ArrayList sublist = (ArrayList)list.get(i);
/* 2485 */         String s = (String)sublist.get(0);
/* 2486 */         String value = (String)sublist.get(1);
/* 2487 */         String name = (String)sublist.get(0);
/*      */         
/* 2489 */         if ((value != null) && (value.equals("APP")))
/*      */         {
/* 2491 */           out.write("\n\t\t\t<td valign=\"top\">\n\t\t\t\t<div id=\"outer3\">\n\t\t\t\t<div class=\"content\">\n\t\t\t");
/* 2492 */           out.println("<h3>" + s + "</h3>");
/*      */ 
/*      */ 
/*      */         }
/* 2496 */         else if ((value != null) && ((value.equals("VIR")) || (value.equals("DBS")) || (value.equals("TM")) || (value.equals("SYS")) || (value.equals("SER")) || (value.equals("URL")) || (value.equals("MS")) || (value.equals("CAM")) || (value.equals("ALL")) || (value.equals("ERP")) || (value.equals("MOM")) || (value.equals("CLD"))))
/*      */         {
/*      */ 
/* 2499 */           if (((EnterpriseUtil.isCloudEdition()) && ((value.equals("SER")) || (value.equals("CAM")))) || ((!EnterpriseUtil.isCloudEdition()) && ((value.equals("SYS")) || (value.equals("SER")) || (value.equals("MOM")))))
/*      */           {
/*      */ 
/*      */ 
/* 2503 */             out.write("\n\t\t       \t     </div></div></td>\n\t\t       \t     <td valign=\"top\">\n\t\t       \t     <div id=\"outer1\">\n\t\t\t\t<div class=\"content\">\n\n\t\t       ");
/*      */           }
/*      */           else {
/* 2506 */             out.println("<br>");
/*      */           }
/* 2508 */           out.println("<h3>" + s + "</h3>");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2513 */           if ((listMonitorsByorder) && ((dispCount == 20) || (dispCount == 0))) {
/* 2514 */             String divStyle = "outer3";
/* 2515 */             if (dispCount == 20) {
/* 2516 */               dispCount = 0;
/* 2517 */               divStyle = "outer1";
/*      */               
/* 2519 */               out.write("\n\t\t\t\t\t   </div></div></td>\n\t\t\t\t");
/*      */             }
/* 2521 */             out.write("\n\t\t\t\n\t\t\t\t<td valign=\"top\">\n\t\t\t\t<div id=\"");
/* 2522 */             out.print(divStyle);
/* 2523 */             out.write("\">\n\t\t\t\t<div class=\"content\">\n\t\t");
/*      */           }
/* 2525 */           dispCount++;
/* 2526 */           String resourceType = (String)sublist.get(3);
/* 2527 */           if ((EnterpriseUtil.isAdminServer()) && ((resourceType.equals("APM-Insight-Instance")) || (resourceType.equals("RBM")) || (resourceType.equals("UrlSeq")) || (resourceType.equals("Custom-Application")) || (resourceType.equals("SAP-CCMS")))) {
/* 2528 */             String alertMessage = FormatUtil.getString("am.webclient.admin.add.monitor.not.supported.alert.text", new String[] { s });
/* 2529 */             out.println("<p><a href='javascript:alert(\"" + alertMessage + "\")' title=\"" + alertMessage + "\">");
/* 2530 */             out.println(s + "&nbsp;");
/* 2531 */             if ((com.adventnet.appmanager.util.Constants.addonList != null) && (com.adventnet.appmanager.util.Constants.addonList.contains(resourceType)) && (!EnterpriseUtil.isCloudEdition()))
/*      */             {
/* 2533 */               out.println("<img src=\"/images/icon_addon.gif\" align=\"middle\" style=\"position:relative;bottom:3px\" border=\"0\" title='" + FormatUtil.getString("am.webclient.addon.tooltip") + "' >&nbsp;");
/*      */             }
/* 2535 */             out.println("</a></p>");
/*      */           } else {
/* 2537 */             out.println("<p><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=" + value + "&restype=" + resourceType + "&haid=" + request.getParameter("haid") + "\">");
/* 2538 */             out.println(s + "&nbsp;");
/* 2539 */             if ((com.adventnet.appmanager.util.Constants.addonList != null) && (com.adventnet.appmanager.util.Constants.addonList.contains(resourceType)) && (!EnterpriseUtil.isCloudEdition()) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */             {
/* 2541 */               out.println("<img src=\"/images/icon_addon.gif\" align=\"middle\" style=\"position:relative;bottom:3px\" border=\"0\" title='" + FormatUtil.getString("am.webclient.addon.tooltip") + "' >&nbsp;");
/*      */             }
/* 2543 */             out.println("</a></p>");
/*      */           }
/* 2545 */           isAgent = conf.getTypeDescription(resourceType) != null ? conf.getTypeDescription(resourceType).getProperty("IS-AGENT-ENABLED") : "No";
/* 2546 */           if (((value != null) && (value.equals("RBM"))) || ((isAgent != null) && (!isAgent.equals("null")) && (isAgent.equalsIgnoreCase("YES")))) {
/* 2547 */             if ((EnterpriseUtil.isAdminServer()) && (resourceType.equals("RBM"))) {
/* 2548 */               String alertMessage = FormatUtil.getString("am.webclient.admin.add.monitor.not.supported.alert.text", new String[] { s });
/* 2549 */               eumMonitors.put(s, "javascript:alert('" + alertMessage + "')");
/*      */             } else {
/* 2551 */               eumMonitors.put(s, "/adminAction.do?method=reloadHostDiscoveryForm&type=" + value + "&restype=" + resourceType + "&haid=" + request.getParameter("haid"));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2558 */       out.write("\n<script>\n\tSet_Cookie('listMonitorsByorder','");
/* 2559 */       out.print(listMonitorsByorder);
/* 2560 */       out.write("'); //NO I18N\n</script>\n\t<br>\n\n</div>\n\t<div class=\"header-heading-newmonitor\"><h3 style=\"padding-left:8px\">");
/* 2561 */       out.print(FormatUtil.getString("am.new.createmonitor.text"));
/* 2562 */       out.write("&nbsp;&nbsp;</h3>");
/* 2563 */       out.write("\n</div>\n\t<div class=\"content\">\n\t<p><a href=\"/monitorType.do?method=showTypes\">\n\t\t");
/* 2564 */       out.print(FormatUtil.getString("am.webclient.createnewmonitor"));
/* 2565 */       out.write("&nbsp;\n\t</a></p>\n\t</div>\n\n\n\t<br>\n\n</div>\n</td>\n\n<td valign=\"top\">\n\t");
/* 2566 */       if (!EnterpriseUtil.isCloudEdition()) {
/* 2567 */         out.write("\n\t<div id=\"outer2\">\n\t\t<div class=\"header-heading\">\n\t\t\t<h3>");
/* 2568 */         out.print(FormatUtil.getString("am.webclient.eum.add"));
/* 2569 */         if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 2570 */           out.write("<img src=\"images/icon_addon.gif\" style=\"position: relative; left: 3px;\">");
/*      */         }
/* 2572 */         out.write("</h3>\n\t\t</div>\n\t\t<div class=\"content\">\n\t\t\t");
/*      */         
/* 2574 */         IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2575 */         _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2576 */         _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */         
/* 2578 */         _jspx_th_logic_005fiterate_005f0.setId("monitors");
/*      */         
/* 2580 */         _jspx_th_logic_005fiterate_005f0.setName("eumMonitors");
/*      */         
/* 2582 */         _jspx_th_logic_005fiterate_005f0.setIndexId("m");
/* 2583 */         int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2584 */         if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2585 */           Object monitors = null;
/* 2586 */           Integer m = null;
/* 2587 */           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2588 */             out = _jspx_page_context.pushBody();
/* 2589 */             _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2590 */             _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */           }
/* 2592 */           monitors = _jspx_page_context.findAttribute("monitors");
/* 2593 */           m = (Integer)_jspx_page_context.findAttribute("m");
/*      */           for (;;) {
/* 2595 */             out.write("\n\t\t\t\t<p><a href=\"<bean:write name='monitors' property=\"value\"/>\"><bean:write name='monitors' property=\"key\" />&nbsp;</a></p>\n\t\t\t");
/* 2596 */             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2597 */             monitors = _jspx_page_context.findAttribute("monitors");
/* 2598 */             m = (Integer)_jspx_page_context.findAttribute("m");
/* 2599 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/* 2602 */           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2603 */             out = _jspx_page_context.popBody();
/*      */           }
/*      */         }
/* 2606 */         if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2607 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */         }
/*      */         
/* 2610 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2611 */         out.write("\n\t\t</div>\n\t\t<br>\n\t</div>\n\t");
/*      */       }
/* 2613 */       out.write("\n</td>\n");
/* 2614 */       out.write("</div>\n\n");
/*      */       
/* 2616 */       out.println("<div style=\"width:55px\" id=\"loading\"><span class=\"bodytextboldwhite\">&nbsp;Loading...&nbsp;</span></div>");
/* 2617 */       out.println("<div id=\"dhtmltooltip\"></div>");
/* 2618 */       out.println("<div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>");
/*      */       
/* 2620 */       out.write(10);
/* 2621 */       out.write(10);
/* 2622 */       out.write(10);
/*      */       
/*      */ 
/*      */ 
/* 2626 */       String crumbs = "";
/* 2627 */       if (request.getParameter("breadcrumbs") != null)
/*      */       {
/* 2629 */         crumbs = request.getParameter("breadcrumbs");
/*      */       }
/*      */       
/* 2632 */       String addtoha = "false";
/* 2633 */       if (request.getParameter("haid") != null)
/*      */       {
/* 2635 */         if ((!request.getParameter("haid").equals("null")) && (!request.getParameter("haid").equals("-")))
/*      */         {
/* 2637 */           addtoha = "true";
/*      */         }
/*      */       }
/*      */       
/* 2641 */       out.write(10);
/* 2642 */       out.write(10);
/*      */       
/* 2644 */       if (request.getParameter("wiz") != null)
/*      */       {
/*      */ 
/* 2647 */         out.write("\n<table width='100%' border='0' cellpadding='0' cellspacing='0' class=\"topmenuborder\">\n  <tr>\n    <td width='1%'><img src='/images/spacer.gif' width='10' height='5'></td>\n    <td width='81%' height='27' class=\"bodytext\"> <a href='#' onClick=\"javascript:alert('");
/* 2648 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */           return;
/* 2650 */         out.write("')\" title=\"");
/* 2651 */         out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2652 */         out.write("\" class='disabledlink'>");
/* 2653 */         out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorgrouplink.text", new String[] { MGSTR }));
/* 2654 */         out.write("</a>\n      &nbsp;|&nbsp;&nbsp;<a href='#' onClick=\"javascript:alert('");
/* 2655 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */           return;
/* 2657 */         out.write("')\" title=\"");
/* 2658 */         out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2659 */         out.write("\" class='disabledlink'>");
/* 2660 */         out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/* 2661 */         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='#' onClick=\"javascript:alert('");
/* 2662 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */           return;
/* 2664 */         out.write("')\" title=\"");
/* 2665 */         out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2666 */         out.write("\" class='disabledlink'>");
/* 2667 */         out.print(FormatUtil.getString("am.webclient.toolbar.newthresholdlink.text"));
/* 2668 */         out.write("</a>&nbsp;&nbsp;\n      |&nbsp;&nbsp;<a href='#' onClick=\"javascript:alert('");
/* 2669 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */           return;
/* 2671 */         out.write("')\" title=\"");
/* 2672 */         out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2673 */         out.write("\" class='disabledlink'>");
/* 2674 */         out.print(FormatUtil.getString("am.webclient.toolbar.viewthresholdlink.text"));
/* 2675 */         out.write("</a>&nbsp; | &nbsp;<a href='#' onClick=\"javascript:alert('");
/* 2676 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */           return;
/* 2678 */         out.write("')\" title=\"");
/* 2679 */         out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2680 */         out.write("\" class='disabledlink'>");
/* 2681 */         out.print(FormatUtil.getString("am.webclient.toolbar.newactionlink.text"));
/* 2682 */         out.write("</a> &nbsp;| &nbsp;<a href='#' onClick=\"javascript:alert('");
/* 2683 */         if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */           return;
/* 2685 */         out.write("')\" title=\"");
/* 2686 */         out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2687 */         out.write("\" class='disabledlink'>");
/* 2688 */         out.print(FormatUtil.getString("am.webclient.toolbar.viewactionlink.text"));
/* 2689 */         out.write("</a>&nbsp;&nbsp;&nbsp;   </td>\n  </tr>\n</table>\n\n\n");
/*      */ 
/*      */       }
/* 2692 */       else if ((request.isUserInRole("ADMIN")) || (request.isUserInRole("DEMO")))
/*      */       {
/*      */ 
/* 2695 */         out.write("\n<table width='100%' border='0' cellpadding='0' cellspacing='0' class=\"topmenuborder\">\n  <tr>\n    <td width='1%'><img src='/images/spacer.gif' width='10' height='5'></td>\n    <td width='73%' height='27' class=\"footer\">\n      ");
/*      */         
/* 2697 */         String type = request.getParameter("type");
/*      */         
/* 2699 */         if (request.isUserInRole("DEMO"))
/*      */         {
/* 2701 */           if (type == null)
/*      */           {
/* 2703 */             type = "UrlMonitor";
/*      */           }
/* 2705 */           else if ((!type.equals("UrlMonitor")) && (!type.equals("UrlSeq")))
/*      */           {
/* 2707 */             type = "UrlMonitor";
/*      */           }
/*      */         }
/*      */         
/* 2711 */         if ((type == null) || (type.equals("UrlEle")) || (type.equals("SUN")))
/*      */         {
/* 2713 */           type = "SYSTEM:9999";
/*      */         }
/* 2715 */         if ((type != null) && (type.equals("MAIL:25")))
/*      */         {
/* 2717 */           type = "MAIL:25&pop=true";
/*      */         }
/*      */         
/* 2720 */         out.write("\n      <a href='#' class='footer' title=\"");
/* 2721 */         out.print(FormatUtil.getString("am.webclient.header.title.newmonitor.text"));
/* 2722 */         out.write("\" onClick=\"showmenu();\">");
/* 2723 */         out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/* 2724 */         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"/showTile.do?TileName=.ThresholdConf&haid=");
/* 2725 */         out.print(request.getParameter("haid"));
/* 2726 */         out.write("\" class=\"footer\" title=\"");
/* 2727 */         out.print(FormatUtil.getString("am.webclient.header.title.newthreshold.text"));
/* 2728 */         out.write(34);
/* 2729 */         out.write(62);
/* 2730 */         out.print(FormatUtil.getString("am.webclient.toolbar.newthresholdlink.text"));
/* 2731 */         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"/common/viewThreshold.do?haid=");
/* 2732 */         out.print(request.getParameter("haid"));
/* 2733 */         out.write("\" class=\"footer\" title=\"");
/* 2734 */         out.print(FormatUtil.getString("am.webclient.header.title.viewthreshold.text"));
/* 2735 */         out.write(34);
/* 2736 */         out.write(62);
/* 2737 */         out.print(FormatUtil.getString("am.webclient.toolbar.viewthresholdlink.text"));
/* 2738 */         out.write("</a>\n      &nbsp; | &nbsp;<a href=\"/showTile.do?TileName=.EmailActions&haid=");
/* 2739 */         out.print(request.getParameter("haid"));
/* 2740 */         out.write("\" class=\"footer\" title=\"");
/* 2741 */         out.print(FormatUtil.getString("am.webclient.header.title.newaction.text"));
/* 2742 */         out.write(34);
/* 2743 */         out.write(62);
/* 2744 */         out.print(FormatUtil.getString("am.webclient.toolbar.newactionlink.text"));
/* 2745 */         out.write("</a> &nbsp;| &nbsp;<a href=\"/adminAction.do?method=showActionProfiles&haid=");
/* 2746 */         out.print(request.getParameter("haid"));
/* 2747 */         out.write("\" class=\"footer\" title=\"");
/* 2748 */         out.print(FormatUtil.getString("am.webclient.header.title.viewaction.text"));
/* 2749 */         out.write(34);
/* 2750 */         out.write(62);
/* 2751 */         out.print(FormatUtil.getString("am.webclient.toolbar.viewactionlink.text"));
/* 2752 */         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true\" class=\"footer\" title=\"");
/* 2753 */         out.print(FormatUtil.getString("am.webclient.header.title.configurealert.text"));
/* 2754 */         out.write(34);
/* 2755 */         out.write(62);
/* 2756 */         out.print(FormatUtil.getString("am.webclient.toolbar.configurealert.text"));
/* 2757 */         out.write("</a>&nbsp; </td>\n    <td width='2%' align=\"center\">");
/* 2758 */         if (!uri.equals("/showReports.do")) {
/* 2759 */           out.write("<a href='javascript:void(0)' onClick=\"javascript:openPrintWindow('Printer Friendly',800, 600,'menubar=yes,resizable=1,scrollbars=1')\" class='bodytextbold11'><img src=\"/images/icon_printerfriendly.gif\" border=\"0\" align=\"absmiddle\" title=\"");
/* 2760 */           out.print(FormatUtil.getString("am.webclient.header.title.printerfriendly.text"));
/* 2761 */           out.write("\"> </a>");
/*      */         }
/* 2763 */         out.write("</td>\n    <td width='2%' align=\"center\"><a href='javascript:void(0)' onClick=\"MM_openBrWindow('/jsp/Alarm.jsp','AlertSummary','width=600,height=450, scrollbars=yes,resizable=yes')\" class='bodytextbold11' ><img src=\"/images/icon_alarmsummary.gif\" border=\"0\" align=\"absmiddle\" title=\"");
/* 2764 */         out.print(FormatUtil.getString("am.webclient.header.title.alertsummary.text"));
/* 2765 */         out.write("\"></a></td>\n  </tr>\n</table>\n");
/*      */ 
/*      */       }
/* 2768 */       else if ((EnterpriseUtil.isAdminServer()) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*      */ 
/* 2771 */         out.write("\n <table width='100%' border='0' cellpadding='0' cellspacing='0' background='/images/");
/* 2772 */         if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */           return;
/* 2774 */         out.write("/mnustrip.gif' class=\"topmenuborder\">\n <tr>\n <td width='1%'><img src='/images/spacer.gif' width='10' height='5'></td>\n <td width='71%' height='27' class=\"footer\">\n <a href=\"/adminAction.do?method=showManagedServers\" class=\"footer\">");
/* 2775 */         out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 2776 */         out.write("</a>\n ");
/* 2777 */         if (request.isUserInRole("ENTERPRISEADMIN")) {
/* 2778 */           out.write("\n &nbsp; | &nbsp;<a href='/admin/createapplication.do' class='footer'>");
/* 2779 */           out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorgrouplink.text", new String[] { MGSTR }));
/* 2780 */           out.write("</a>\n ");
/*      */         }
/* 2782 */         out.write("\t \n </td>\n <!--<a href=\"/admin/userconfiguration.do?method=showUsers\" class=\"footer\">");
/* 2783 */         out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2784 */         out.write("</a>\n &nbsp; | &nbsp;<a href=\"/jsp/ProxyConfiguration.jsp\" class=\"footer\">");
/* 2785 */         out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2786 */         out.write("</a>\n &nbsp; | &nbsp;<a href=\"/adminAction.do?method=showMailServerConfiguration&admin=true\" class=\"footer\">");
/* 2787 */         out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 2788 */         out.write("</a>\n &nbsp; | &nbsp;<a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"footer\">");
/* 2789 */         out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 2790 */         out.write("</a>\n &nbsp; | &nbsp;<a href=\"/adminAction.do?method=showManagedServers\" class=\"footer\">");
/* 2791 */         out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 2792 */         out.write("</a>\n&nbsp;&nbsp;|&nbsp;&nbsp;<a style=\"cursor: pointer\"  onClick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/* 2793 */         out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/* 2794 */         out.write("</a> &nbsp; | &nbsp;<a href=\"javascript:MM_openBrWindow('/jsp/Popup_password.jsp','Password','width=400,height=225,top=250,left=275 scrollbars=yes,resizable=yes')\" class=\"footer\" >");
/* 2795 */         out.print(FormatUtil.getString("am.webclient.changepassword.heading.text"));
/* 2796 */         out.write("</a></td>-->\n <td width='2%' align=\"center\">");
/* 2797 */         if (!uri.equals("/showReports.do")) {
/* 2798 */           out.write("<a href='javascript:void(0)' onClick=\"javascript:openPrintWindow('Printer Friendly',800, 600,'menubar=yes,resizable=1,scrollbars=1')\" class='bodytextbold11'><img src=\"/images/icon_printerfriendly.gif\" border=\"0\" align=\"absmiddle\" title=\"");
/* 2799 */           out.print(FormatUtil.getString("am.webclient.header.title.printerfriendly.text"));
/* 2800 */           out.write("\"> </a>");
/*      */         }
/* 2802 */         out.write("</td>\n   <td width='2%' align=\"center\"><a href='javascript:void(0)' onClick=\"MM_openBrWindow('/jsp/Alarm.jsp','AlertSummary','width=600,height=450, scrollbars=yes,resizable=yes')\" class='bodytextbold11' ><img src=\"/images/icon_alarmsummary.gif\" border=\"0\" align=\"absmiddle\" title=\"");
/* 2803 */         out.print(FormatUtil.getString("am.webclient.header.title.alertsummary.text"));
/* 2804 */         out.write("\"></a></td>\n   </tr>\n </table>\n ");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 2810 */         out.write("\n<table width='100%' border='0' cellpadding='0' cellspacing='0' background='/images/");
/* 2811 */         if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */           return;
/* 2813 */         out.write("/mnustrip.gif' class=\"topmenuborder\">\n  <tr>\n    <td width='1%'><img src='/images/spacer.gif' width='10' height='5'></td>\n    <td width='71%' height='27' class=\"footer\">\n    <a href=\"/common/viewThreshold.do?haid=");
/* 2814 */         out.print(request.getParameter("haid"));
/* 2815 */         out.write("\" class=\"footer\">");
/* 2816 */         out.print(FormatUtil.getString("am.webclient.toolbar.viewthresholdlink.text"));
/* 2817 */         out.write("</a>\n      &nbsp; | &nbsp;<a href=\"/common/viewActions.do?haid=");
/* 2818 */         out.print(request.getParameter("haid"));
/* 2819 */         out.write("\" class=\"footer\">");
/* 2820 */         out.print(FormatUtil.getString("am.webclient.toolbar.viewactionlink.text"));
/* 2821 */         out.write("</a>\n      &nbsp; | &nbsp;<a href=\"/common/serverinfo.do\" class=\"footer\">");
/* 2822 */         out.print(FormatUtil.getString("am.webclient.header.productinformation.text"));
/* 2823 */         out.write("</a>&nbsp;\n       ");
/* 2824 */         if (!OEMUtil.isRemove("am.personalize.remove")) {
/* 2825 */           out.write("| &nbsp;&nbsp;<a style=\"cursor: pointer\" onClick=\"MM_openBrWindow('/skinSelection.do','Personalize','width=390,height=450, scrollbars=yes')\" class=\"footer\">");
/* 2826 */           out.print(FormatUtil.getString("webclient.common.skinselection.personalise"));
/* 2827 */           out.write("</a>");
/*      */         }
/* 2829 */         out.write("\n       &nbsp; | &nbsp;<a href=\"javascript:MM_openBrWindow('/jsp/Popup_password.jsp','Password','width=400,height=225,top=250,left=275 scrollbars=yes,resizable=yes')\" class=\"footer\" >");
/* 2830 */         out.print(FormatUtil.getString("am.webclient.changepassword.heading.text"));
/* 2831 */         out.write("</a></td>\n    <td width='2%' align=\"center\">");
/* 2832 */         if (!uri.equals("/showReports.do")) {
/* 2833 */           out.write("<a href='javascript:void(0)' onClick=\"javascript:openPrintWindow('Printer Friendly',800, 600,'menubar=yes,resizable=1,scrollbars=1')\" class='bodytextbold11'><img src=\"/images/icon_printerfriendly.gif\" border=\"0\" align=\"absmiddle\" title=\"");
/* 2834 */           out.print(FormatUtil.getString("am.webclient.header.title.printerfriendly.text"));
/* 2835 */           out.write("\"> </a>");
/*      */         }
/* 2837 */         out.write("</td>\n    <td width='2%' align=\"center\"><a href='javascript:void(0)' onClick=\"MM_openBrWindow('/jsp/Alarm.jsp','AlertSummary','width=600,height=450, scrollbars=yes,resizable=yes')\" class='bodytextbold11' ><img src=\"/images/icon_alarmsummary.gif\" border=\"0\" align=\"absmiddle\" title=\"");
/* 2838 */         out.print(FormatUtil.getString("am.webclient.header.title.alertsummary.text"));
/* 2839 */         out.write("\"></a></td>\n  </tr>\n</table>\n");
/*      */       }
/*      */       
/*      */ 
/* 2843 */       out.write(9);
/* 2844 */       out.write(10);
/* 2845 */       out.write("\n\t\n\n\n</body>\n</html> \n\n\n\n\n");
/*      */     } catch (Throwable t) {
/* 2847 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2848 */         out = _jspx_out;
/* 2849 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2850 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2851 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2854 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2860 */     PageContext pageContext = _jspx_page_context;
/* 2861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2863 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2864 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2865 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/* 2866 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2867 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 2869 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2870 */           return true;
/* 2871 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2872 */           return true;
/* 2873 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2874 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2878 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2879 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2880 */       return true;
/*      */     }
/* 2882 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2883 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2888 */     PageContext pageContext = _jspx_page_context;
/* 2889 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2891 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2892 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2893 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 2895 */     _jspx_th_c_005fwhen_005f0.setTest("${globalconfig['defaultmonitorsview']=='showResourceTypesAll'}");
/* 2896 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2897 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 2899 */         out.write("<a  class=\"topmenufont\" href=\"/showresource.do?method=showResourceTypesAll\">");
/* 2900 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2901 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2905 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2906 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2907 */       return true;
/*      */     }
/* 2909 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2910 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2915 */     PageContext pageContext = _jspx_page_context;
/* 2916 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2918 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2919 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2920 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 2921 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2922 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 2924 */         out.write("<a  class=\"topmenufont\" href=\"/showresource.do?method=showResourceTypes\">");
/* 2925 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2926 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2930 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2931 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2932 */       return true;
/*      */     }
/* 2934 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2940 */     PageContext pageContext = _jspx_page_context;
/* 2941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2943 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2944 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2945 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 2947 */     _jspx_th_c_005fout_005f0.setValue("${globalconfig['defaultmonitorsview']}");
/* 2948 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2949 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2950 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2951 */       return true;
/*      */     }
/* 2953 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2954 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2959 */     PageContext pageContext = _jspx_page_context;
/* 2960 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2962 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2963 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2964 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 2966 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*      */     
/* 2968 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 2969 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2970 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2971 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2972 */       return true;
/*      */     }
/* 2974 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2975 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2980 */     PageContext pageContext = _jspx_page_context;
/* 2981 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2983 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2984 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2985 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 2986 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2987 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 2988 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2989 */         out = _jspx_page_context.pushBody();
/* 2990 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 2991 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2994 */         out.write("wizard.disabled");
/* 2995 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 2996 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2999 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3000 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3003 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3004 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3005 */       return true;
/*      */     }
/* 3007 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3008 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3013 */     PageContext pageContext = _jspx_page_context;
/* 3014 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3016 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3017 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3018 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/* 3019 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3020 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 3021 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3022 */         out = _jspx_page_context.pushBody();
/* 3023 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 3024 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3027 */         out.write("wizard.disabled");
/* 3028 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 3029 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3032 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3033 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3036 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3037 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3038 */       return true;
/*      */     }
/* 3040 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3041 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3046 */     PageContext pageContext = _jspx_page_context;
/* 3047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3049 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3050 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3051 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/* 3052 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3053 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 3054 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3055 */         out = _jspx_page_context.pushBody();
/* 3056 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 3057 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3060 */         out.write("wizard.disabled");
/* 3061 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3062 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3065 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3066 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3069 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3070 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3071 */       return true;
/*      */     }
/* 3073 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3079 */     PageContext pageContext = _jspx_page_context;
/* 3080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3082 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3083 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3084 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/* 3085 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3086 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 3087 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3088 */         out = _jspx_page_context.pushBody();
/* 3089 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 3090 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3093 */         out.write("wizard.disabled");
/* 3094 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3095 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3098 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3099 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3102 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3103 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3104 */       return true;
/*      */     }
/* 3106 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3112 */     PageContext pageContext = _jspx_page_context;
/* 3113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3115 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3116 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3117 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/* 3118 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3119 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3120 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3121 */         out = _jspx_page_context.pushBody();
/* 3122 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3123 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3126 */         out.write("wizard.disabled");
/* 3127 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3128 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3131 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3132 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3135 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3136 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3137 */       return true;
/*      */     }
/* 3139 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3140 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3145 */     PageContext pageContext = _jspx_page_context;
/* 3146 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3148 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3149 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3150 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/* 3151 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3152 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 3153 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3154 */         out = _jspx_page_context.pushBody();
/* 3155 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 3156 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3159 */         out.write("wizard.disabled");
/* 3160 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 3161 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3164 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3165 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3168 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3169 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3170 */       return true;
/*      */     }
/* 3172 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3178 */     PageContext pageContext = _jspx_page_context;
/* 3179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3181 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3182 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3183 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 3185 */     _jspx_th_c_005fout_005f2.setValue("${selectedskin}");
/*      */     
/* 3187 */     _jspx_th_c_005fout_005f2.setDefault("${initParam.defaultSkin}");
/* 3188 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3189 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3190 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3191 */       return true;
/*      */     }
/* 3193 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3194 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3199 */     PageContext pageContext = _jspx_page_context;
/* 3200 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3202 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3203 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3204 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 3206 */     _jspx_th_c_005fout_005f3.setValue("${selectedskin}");
/*      */     
/* 3208 */     _jspx_th_c_005fout_005f3.setDefault("${initParam.defaultSkin}");
/* 3209 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3210 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3211 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3212 */       return true;
/*      */     }
/* 3214 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3215 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\header_005fconsole_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */