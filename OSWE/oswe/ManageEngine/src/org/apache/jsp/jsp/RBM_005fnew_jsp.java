/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.AMRegexUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
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
/*      */ import java.util.Vector;
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.SkipPageException;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ 
/*      */ public final class RBM_005fnew_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
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
/* 1197 */       childresname = ExtProdUtil.decodeString(childresname);
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
/* 2108 */     List<String> criticalThresholdValues = AMRegexUtil.getThresholdGroups(criticalcondition, true);
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
/* 2198 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2210 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2214 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2219 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2223 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2224 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2225 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2226 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2233 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2236 */     JspWriter out = null;
/* 2237 */     Object page = this;
/* 2238 */     JspWriter _jspx_out = null;
/* 2239 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2243 */       response.setContentType("text/html;charset=UTF-8");
/* 2244 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2246 */       _jspx_page_context = pageContext;
/* 2247 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2248 */       ServletConfig config = pageContext.getServletConfig();
/* 2249 */       session = pageContext.getSession();
/* 2250 */       out = pageContext.getOut();
/* 2251 */       _jspx_out = out;
/*      */       
/* 2253 */       out.write("<!DOCTYPE html>\n");
/* 2254 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2255 */       out.write(10);
/*      */       
/* 2257 */       request.setAttribute("HelpKey", "RBM Monitor Details");
/*      */       
/* 2259 */       out.write("\n\n\n\n\n\n\n\n");
/* 2260 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2262 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2263 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2264 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2266 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2268 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2270 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2272 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2273 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2274 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2275 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2278 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2279 */         String available = null;
/* 2280 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2281 */         out.write(10);
/*      */         
/* 2283 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2284 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2285 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2287 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2289 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2291 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2293 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2294 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2295 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2296 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2299 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2300 */           String unavailable = null;
/* 2301 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2302 */           out.write(10);
/*      */           
/* 2304 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2305 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2306 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2308 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2310 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2312 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2314 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2315 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2316 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2317 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2320 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2321 */             String unmanaged = null;
/* 2322 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2323 */             out.write(10);
/*      */             
/* 2325 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2326 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2327 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2329 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2331 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2333 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2335 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2336 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2337 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2338 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2341 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2342 */               String scheduled = null;
/* 2343 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2344 */               out.write(10);
/*      */               
/* 2346 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2347 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2348 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2350 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2352 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2354 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2356 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2357 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2358 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2359 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2362 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2363 */                 String critical = null;
/* 2364 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2365 */                 out.write(10);
/*      */                 
/* 2367 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2368 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2369 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2371 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2373 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2375 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2377 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2378 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2379 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2380 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2383 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2384 */                   String clear = null;
/* 2385 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2386 */                   out.write(10);
/*      */                   
/* 2388 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2389 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2390 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2392 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2394 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2396 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2398 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2399 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2400 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2401 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2404 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2405 */                     String warning = null;
/* 2406 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2407 */                     out.write(10);
/* 2408 */                     out.write(10);
/*      */                     
/* 2410 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2411 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2413 */                     out.write(10);
/* 2414 */                     out.write(10);
/* 2415 */                     out.write(10);
/* 2416 */                     out.write(10);
/* 2417 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2418 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2419 */                     ManagedApplication mo = null;
/* 2420 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2421 */                     if (mo == null) {
/* 2422 */                       mo = new ManagedApplication();
/* 2423 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2425 */                     out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/hostdiscoveryform.js\"></SCRIPT>\n");
/* 2426 */                     org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/monitorGroups.jsp", out, false);
/* 2427 */                     out.write(10);
/*      */                     
/* 2429 */                     request.setAttribute("isRBM", "true");
/* 2430 */                     String fromWhere = "RBM";
/*      */                     
/* 2432 */                     out.write("\n<html>\n<head>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/commonstyle.css\">\n<style>\n</style>\n</head>\n<body>\n");
/*      */                     
/* 2434 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2435 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2436 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2438 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2439 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2440 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2442 */                         out.write(10);
/* 2443 */                         out.write(32);
/* 2444 */                         out.write(32);
/*      */                         
/* 2446 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2447 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2448 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2450 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2452 */                         _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.rbmurlmonitor.type.text"));
/* 2453 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2454 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2455 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2458 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2459 */                         out.write(10);
/* 2460 */                         out.write(32);
/* 2461 */                         out.write(32);
/* 2462 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2464 */                         out.write(10);
/* 2465 */                         out.write(32);
/* 2466 */                         out.write(32);
/*      */                         
/* 2468 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2469 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2470 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2472 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2474 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2475 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2476 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2477 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2478 */                             out = _jspx_page_context.pushBody();
/* 2479 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2480 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2483 */                             out.write("\n    <title>");
/* 2484 */                             out.print(FormatUtil.getString("am.webclient.rbm.title.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 2485 */                             out.write("</title>\n    ");
/* 2486 */                             out.write("\n\t<div align='center' style=\"width:400px; float:right\">\n\t\t<img src=\"/images/rbm_firefox_flowchart.png\">\n\t</div>\n\t\n    <div class=\"box-left\" >\n      <div id=\"intro\" class=\"noteBox\">\n        <center>\n          <h1>");
/* 2487 */                             out.print(FormatUtil.getString("am.webclient.rbm"));
/* 2488 */                             out.write("<span style=\"font-size:19px; color:#eb7834\"> (");
/* 2489 */                             out.print(FormatUtil.getString("am.rbm.firefox"));
/* 2490 */                             out.write(")</span></h1>\n        </center>\n        <p>");
/* 2491 */                             out.print(FormatUtil.getString("am.rbm.helpcard.rbmFirefox"));
/* 2492 */                             out.write("</p>\n        <h3>");
/* 2493 */                             out.print(FormatUtil.getString("am.webclient.gettingstarted.prerequisites"));
/* 2494 */                             out.write("</h3>\n        <ol>\n          <li>");
/* 2495 */                             out.print(FormatUtil.getString("am.rbm.helpcard.header.rbmrecorder"));
/* 2496 */                             out.write("</li>\n          <li>");
/* 2497 */                             out.print(FormatUtil.getString("am.rbm.helpcard.Prerequisites.eumAgents"));
/* 2498 */                             out.write(" <span id=\"eumCheck\"><img src=\"/images/Alarm-green-tick.gif\"></span></li>\n        </ol>\n        <p></p>\n      </div>\n      <div class=\"noteBox\" id=\"DownloadAgent\"> ");
/* 2499 */                             out.print(FormatUtil.getString("am.rbm.helpcard.eumagent"));
/* 2500 */                             out.write(" </div>\n      <div class=\"noteBox\" id=\"DownloadRecorder\">\n        <h3>");
/* 2501 */                             out.print(FormatUtil.getString("am.rbm.selenium.add.content1"));
/* 2502 */                             out.write("</h3>\n        <ol type=\"i\">\n          ");
/* 2503 */                             out.print(FormatUtil.getString("am.webclient.rbm.helpcard.recorder"));
/* 2504 */                             out.write("\n        </ol>\n      </div>\n\t  \n\t  <div class=\"note\">\n\t\t<b>");
/* 2505 */                             out.print(FormatUtil.getString("am.rbm.helpcard.note.firefox"));
/* 2506 */                             out.write("</b>\n\t  </div>\n    </div>\n\t\n   \n  ");
/* 2507 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2508 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2511 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2512 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2515 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2516 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 2519 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2520 */                         out.write(10);
/* 2521 */                         out.write(32);
/* 2522 */                         out.write(32);
/* 2523 */                         out.write(10);
/* 2524 */                         out.write(32);
/* 2525 */                         out.write(32);
/* 2526 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2528 */                         out.write(10);
/* 2529 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2530 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2534 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2535 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 2538 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2539 */                       out.write("\n<script>\n\n\t$(document).ready(function()\n  {\n  \tvar url = \"/SeleniumActions.do?method=getAgentDetails\"; //No I18N\n  \t$.ajax({\n          type:'POST', //No i18n\n          url:url,\n          async:false,\n          success: function(data)\n          {\n            if(data!=null)\n            {\n            \tvar jsonResp=JSON.parse(data);\n            \tvar agentList=jsonResp.locations1;\n            \tif(!jQuery.isEmptyObject(jsonResp.locations1[0]))\n            \t{\n            \t\tdocument.getElementById(\"DownloadAgent\").style.display=\"none\";\n\t\t\t\t\t$(\"eumCheck\").css(\"display:inline-block\"); //No i18n\n            \t} else {\n\t\t\t\t\t$(\"eumCheck\").css(\"display:none\"); //No i18n\n\t\t\t\t}\n            }\n          }\n    });\n\t\n\tjQuery('.table-of-contents a').click(function() {  //No i18n\n            jQuery('.table-of-contents a').removeClass('active'); //No i18n\n\t\t\tjQuery(this).addClass('active'); //No i18n\n        });\n    \n  });\n\t\n</script>\n</body>\n</html>");
/*      */                     }
/* 2541 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 2542 */         out = _jspx_out;
/* 2543 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2544 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2545 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2548 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2554 */     PageContext pageContext = _jspx_page_context;
/* 2555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2557 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2558 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2559 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2561 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2563 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 2564 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2565 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2566 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2567 */       return true;
/*      */     }
/* 2569 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2575 */     PageContext pageContext = _jspx_page_context;
/* 2576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2578 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2579 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2580 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2582 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 2584 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 2585 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2586 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2587 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 2588 */       return true;
/*      */     }
/* 2590 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 2591 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\RBM_005fnew_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */