/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.SummaryBean;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class rbmresourcedetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   61 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   64 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   65 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   66 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   73 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   78 */     ArrayList list = null;
/*   79 */     StringBuffer sbf = new StringBuffer();
/*   80 */     ManagedApplication mo = new ManagedApplication();
/*   81 */     if (distinct)
/*      */     {
/*   83 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   87 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   90 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   92 */       ArrayList row = (ArrayList)list.get(i);
/*   93 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   94 */       if (distinct) {
/*   95 */         sbf.append(row.get(0));
/*      */       } else
/*   97 */         sbf.append(row.get(1));
/*   98 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  101 */     return sbf.toString(); }
/*      */   
/*  103 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  106 */     if (severity == null)
/*      */     {
/*  108 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  110 */     if (severity.equals("5"))
/*      */     {
/*  112 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  114 */     if (severity.equals("1"))
/*      */     {
/*  116 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  121 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  128 */     if (severity == null)
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  132 */     if (severity.equals("1"))
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  136 */     if (severity.equals("4"))
/*      */     {
/*  138 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  140 */     if (severity.equals("5"))
/*      */     {
/*  142 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  147 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  153 */     if (severity == null)
/*      */     {
/*  155 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  157 */     if (severity.equals("5"))
/*      */     {
/*  159 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  161 */     if (severity.equals("1"))
/*      */     {
/*  163 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  167 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  173 */     if (severity == null)
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  177 */     if (severity.equals("1"))
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  181 */     if (severity.equals("4"))
/*      */     {
/*  183 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  185 */     if (severity.equals("5"))
/*      */     {
/*  187 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  191 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  197 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  203 */     if (severity == 5)
/*      */     {
/*  205 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  207 */     if (severity == 1)
/*      */     {
/*  209 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  214 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  220 */     if (severity == null)
/*      */     {
/*  222 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  224 */     if (severity.equals("5"))
/*      */     {
/*  226 */       if (isAvailability) {
/*  227 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  230 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  233 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  235 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  237 */     if (severity.equals("1"))
/*      */     {
/*  239 */       if (isAvailability) {
/*  240 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  243 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  250 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  257 */     if (severity == null)
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  261 */     if (severity.equals("5"))
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  265 */     if (severity.equals("4"))
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  269 */     if (severity.equals("1"))
/*      */     {
/*  271 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  276 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  282 */     if (severity == null)
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  286 */     if (severity.equals("5"))
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  290 */     if (severity.equals("4"))
/*      */     {
/*  292 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  294 */     if (severity.equals("1"))
/*      */     {
/*  296 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  301 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  308 */     if (severity == null)
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  312 */     if (severity.equals("5"))
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  316 */     if (severity.equals("4"))
/*      */     {
/*  318 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  320 */     if (severity.equals("1"))
/*      */     {
/*  322 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  327 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  335 */     StringBuffer out = new StringBuffer();
/*  336 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  337 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  338 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  339 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  340 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  341 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  342 */     out.append("</tr>");
/*  343 */     out.append("</form></table>");
/*  344 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  351 */     if (val == null)
/*      */     {
/*  353 */       return "-";
/*      */     }
/*      */     
/*  356 */     String ret = FormatUtil.formatNumber(val);
/*  357 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  358 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  361 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  365 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  373 */     StringBuffer out = new StringBuffer();
/*  374 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  375 */     out.append("<tr>");
/*  376 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  378 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  380 */     out.append("</tr>");
/*  381 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  385 */       if (j % 2 == 0)
/*      */       {
/*  387 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  391 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  394 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  396 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  399 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  403 */       out.append("</tr>");
/*      */     }
/*  405 */     out.append("</table>");
/*  406 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  407 */     out.append("<tr>");
/*  408 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  409 */     out.append("</tr>");
/*  410 */     out.append("</table>");
/*  411 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  417 */     StringBuffer out = new StringBuffer();
/*  418 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  419 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  420 */     out.append("<tr>");
/*  421 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  422 */     out.append("<tr>");
/*  423 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  424 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  425 */     out.append("</tr>");
/*  426 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  429 */       out.append("<tr>");
/*  430 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  431 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  432 */       out.append("</tr>");
/*      */     }
/*      */     
/*  435 */     out.append("</table>");
/*  436 */     out.append("</table>");
/*  437 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  442 */     if (severity.equals("0"))
/*      */     {
/*  444 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  448 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  455 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  468 */     StringBuffer out = new StringBuffer();
/*  469 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  470 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  472 */       out.append("<tr>");
/*  473 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  474 */       out.append("</tr>");
/*      */       
/*      */ 
/*  477 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  479 */         String borderclass = "";
/*      */         
/*      */ 
/*  482 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  484 */         out.append("<tr>");
/*      */         
/*  486 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  487 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  488 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  494 */     out.append("</table><br>");
/*  495 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  496 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  498 */       List sLinks = secondLevelOfLinks[0];
/*  499 */       List sText = secondLevelOfLinks[1];
/*  500 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  503 */         out.append("<tr>");
/*  504 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  505 */         out.append("</tr>");
/*  506 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  508 */           String borderclass = "";
/*      */           
/*      */ 
/*  511 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  513 */           out.append("<tr>");
/*      */           
/*  515 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  516 */           if (sLinks.get(i).toString().length() == 0) {
/*  517 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  520 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  522 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  526 */     out.append("</table>");
/*  527 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  534 */     StringBuffer out = new StringBuffer();
/*  535 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  536 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  538 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  540 */         out.append("<tr>");
/*  541 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  542 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  546 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  548 */           String borderclass = "";
/*      */           
/*      */ 
/*  551 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  553 */           out.append("<tr>");
/*      */           
/*  555 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  556 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  557 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  560 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  563 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  568 */     out.append("</table><br>");
/*  569 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  570 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  572 */       List sLinks = secondLevelOfLinks[0];
/*  573 */       List sText = secondLevelOfLinks[1];
/*  574 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  577 */         out.append("<tr>");
/*  578 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  579 */         out.append("</tr>");
/*  580 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  582 */           String borderclass = "";
/*      */           
/*      */ 
/*  585 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  587 */           out.append("<tr>");
/*      */           
/*  589 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  590 */           if (sLinks.get(i).toString().length() == 0) {
/*  591 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  594 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  596 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  600 */     out.append("</table>");
/*  601 */     return out.toString();
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
/*  614 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  617 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  620 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  623 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  626 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  629 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  632 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  635 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  643 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  648 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  653 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  658 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  663 */     if (val != null)
/*      */     {
/*  665 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  669 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  674 */     if (val == null) {
/*  675 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  679 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  684 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  690 */     if (val != null)
/*      */     {
/*  692 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  696 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  702 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  707 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  711 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  716 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  721 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  726 */     String hostaddress = "";
/*  727 */     String ip = request.getHeader("x-forwarded-for");
/*  728 */     if (ip == null)
/*  729 */       ip = request.getRemoteAddr();
/*  730 */     InetAddress add = null;
/*  731 */     if (ip.equals("127.0.0.1")) {
/*  732 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  736 */       add = InetAddress.getByName(ip);
/*      */     }
/*  738 */     hostaddress = add.getHostName();
/*  739 */     if (hostaddress.indexOf('.') != -1) {
/*  740 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  741 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  745 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  750 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  756 */     if (severity == null)
/*      */     {
/*  758 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  760 */     if (severity.equals("5"))
/*      */     {
/*  762 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  764 */     if (severity.equals("1"))
/*      */     {
/*  766 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  771 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  776 */     ResultSet set = null;
/*  777 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  778 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  780 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  781 */       if (set.next()) { String str1;
/*  782 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  783 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  786 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  791 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  794 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  796 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  800 */     StringBuffer rca = new StringBuffer();
/*  801 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  802 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  805 */     int rcalength = key.length();
/*  806 */     String split = "6. ";
/*  807 */     int splitPresent = key.indexOf(split);
/*  808 */     String div1 = "";String div2 = "";
/*  809 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  811 */       if (rcalength > 180) {
/*  812 */         rca.append("<span class=\"rca-critical-text\">");
/*  813 */         getRCATrimmedText(key, rca);
/*  814 */         rca.append("</span>");
/*      */       } else {
/*  816 */         rca.append("<span class=\"rca-critical-text\">");
/*  817 */         rca.append(key);
/*  818 */         rca.append("</span>");
/*      */       }
/*  820 */       return rca.toString();
/*      */     }
/*  822 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  823 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  824 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  825 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  826 */     getRCATrimmedText(div1, rca);
/*  827 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  830 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  831 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  832 */     getRCATrimmedText(div2, rca);
/*  833 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  835 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  840 */     String[] st = msg.split("<br>");
/*  841 */     for (int i = 0; i < st.length; i++) {
/*  842 */       String s = st[i];
/*  843 */       if (s.length() > 180) {
/*  844 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  846 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  850 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  851 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  853 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  857 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  858 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  859 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  862 */       if (key == null) {
/*  863 */         return ret;
/*      */       }
/*      */       
/*  866 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  867 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  870 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  871 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  872 */       set = AMConnectionPool.executeQueryStmt(query);
/*  873 */       if (set.next())
/*      */       {
/*  875 */         String helpLink = set.getString("LINK");
/*  876 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  879 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  885 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  904 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  895 */         if (set != null) {
/*  896 */           AMConnectionPool.closeStatement(set);
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
/*  910 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  911 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  913 */       String entityStr = (String)keys.nextElement();
/*  914 */       String mmessage = temp.getProperty(entityStr);
/*  915 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  916 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  918 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  924 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  925 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  927 */       String entityStr = (String)keys.nextElement();
/*  928 */       String mmessage = temp.getProperty(entityStr);
/*  929 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  930 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  932 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  937 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  947 */     String des = new String();
/*  948 */     while (str.indexOf(find) != -1) {
/*  949 */       des = des + str.substring(0, str.indexOf(find));
/*  950 */       des = des + replace;
/*  951 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  953 */     des = des + str;
/*  954 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  961 */       if (alert == null)
/*      */       {
/*  963 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  965 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  967 */         return "&nbsp;";
/*      */       }
/*      */       
/*  970 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  972 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  975 */       int rcalength = test.length();
/*  976 */       if (rcalength < 300)
/*      */       {
/*  978 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  982 */       StringBuffer out = new StringBuffer();
/*  983 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  984 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  985 */       out.append("</div>");
/*  986 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  987 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  988 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  993 */       ex.printStackTrace();
/*      */     }
/*  995 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1001 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1006 */     ArrayList attribIDs = new ArrayList();
/* 1007 */     ArrayList resIDs = new ArrayList();
/* 1008 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1010 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1012 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1014 */       String resourceid = "";
/* 1015 */       String resourceType = "";
/* 1016 */       if (type == 2) {
/* 1017 */         resourceid = (String)row.get(0);
/* 1018 */         resourceType = (String)row.get(3);
/*      */       }
/* 1020 */       else if (type == 3) {
/* 1021 */         resourceid = (String)row.get(0);
/* 1022 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1025 */         resourceid = (String)row.get(6);
/* 1026 */         resourceType = (String)row.get(7);
/*      */       }
/* 1028 */       resIDs.add(resourceid);
/* 1029 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1030 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1032 */       String healthentity = null;
/* 1033 */       String availentity = null;
/* 1034 */       if (healthid != null) {
/* 1035 */         healthentity = resourceid + "_" + healthid;
/* 1036 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1039 */       if (availid != null) {
/* 1040 */         availentity = resourceid + "_" + availid;
/* 1041 */         entitylist.add(availentity);
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
/* 1055 */     Properties alert = getStatus(entitylist);
/* 1056 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1061 */     int size = monitorList.size();
/*      */     
/* 1063 */     String[] severity = new String[size];
/*      */     
/* 1065 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1067 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1068 */       String resourceName1 = (String)row1.get(7);
/* 1069 */       String resourceid1 = (String)row1.get(6);
/* 1070 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1071 */       if (severity[j] == null)
/*      */       {
/* 1073 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1077 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1079 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1081 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1084 */         if (sev > 0) {
/* 1085 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1086 */           monitorList.set(k, monitorList.get(j));
/* 1087 */           monitorList.set(j, t);
/* 1088 */           String temp = severity[k];
/* 1089 */           severity[k] = severity[j];
/* 1090 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1096 */     int z = 0;
/* 1097 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1100 */       int i = 0;
/* 1101 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1104 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1108 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1112 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1114 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1117 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1121 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1124 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1125 */       String resourceName1 = (String)row1.get(7);
/* 1126 */       String resourceid1 = (String)row1.get(6);
/* 1127 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1128 */       if (hseverity[j] == null)
/*      */       {
/* 1130 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1135 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1137 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1140 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1143 */         if (hsev > 0) {
/* 1144 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1145 */           monitorList.set(k, monitorList.get(j));
/* 1146 */           monitorList.set(j, t);
/* 1147 */           String temp1 = hseverity[k];
/* 1148 */           hseverity[k] = hseverity[j];
/* 1149 */           hseverity[j] = temp1;
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
/* 1161 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1162 */     boolean forInventory = false;
/* 1163 */     String trdisplay = "none";
/* 1164 */     String plusstyle = "inline";
/* 1165 */     String minusstyle = "none";
/* 1166 */     String haidTopLevel = "";
/* 1167 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1169 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1171 */         haidTopLevel = request.getParameter("haid");
/* 1172 */         forInventory = true;
/* 1173 */         trdisplay = "table-row;";
/* 1174 */         plusstyle = "none";
/* 1175 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1182 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1185 */     ArrayList listtoreturn = new ArrayList();
/* 1186 */     StringBuffer toreturn = new StringBuffer();
/* 1187 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1188 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1189 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1191 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1193 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1194 */       String childresid = (String)singlerow.get(0);
/* 1195 */       String childresname = (String)singlerow.get(1);
/* 1196 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1197 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1198 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1199 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1200 */       String unmanagestatus = (String)singlerow.get(5);
/* 1201 */       String actionstatus = (String)singlerow.get(6);
/* 1202 */       String linkclass = "monitorgp-links";
/* 1203 */       String titleforres = childresname;
/* 1204 */       String titilechildresname = childresname;
/* 1205 */       String childimg = "/images/trcont.png";
/* 1206 */       String flag = "enable";
/* 1207 */       String dcstarted = (String)singlerow.get(8);
/* 1208 */       String configMonitor = "";
/* 1209 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1210 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1212 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1214 */       if (singlerow.get(7) != null)
/*      */       {
/* 1216 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1218 */       String haiGroupType = "0";
/* 1219 */       if ("HAI".equals(childtype))
/*      */       {
/* 1221 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1223 */       childimg = "/images/trend.png";
/* 1224 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1225 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1226 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1228 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1230 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1232 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1233 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1236 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1238 */         linkclass = "disabledtext";
/* 1239 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1241 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1242 */       String availmouseover = "";
/* 1243 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1245 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1247 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1248 */       String healthmouseover = "";
/* 1249 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1251 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1254 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1255 */       int spacing = 0;
/* 1256 */       if (level >= 1)
/*      */       {
/* 1258 */         spacing = 40 * level;
/*      */       }
/* 1260 */       if (childtype.equals("HAI"))
/*      */       {
/* 1262 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1263 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1264 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1266 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1267 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1268 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1269 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1270 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1271 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1272 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1273 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1274 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1275 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1276 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1278 */         if (!forInventory)
/*      */         {
/* 1280 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1283 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1285 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1287 */           actions = editlink + actions;
/*      */         }
/* 1289 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1291 */           actions = actions + associatelink;
/*      */         }
/* 1293 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1294 */         String arrowimg = "";
/* 1295 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1297 */           actions = "";
/* 1298 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1299 */           checkbox = "";
/* 1300 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1302 */         if (isIt360)
/*      */         {
/* 1304 */           actionimg = "";
/* 1305 */           actions = "";
/* 1306 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1307 */           checkbox = "";
/*      */         }
/*      */         
/* 1310 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1312 */           actions = "";
/*      */         }
/* 1314 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1316 */           checkbox = "";
/*      */         }
/*      */         
/* 1319 */         String resourcelink = "";
/*      */         
/* 1321 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1323 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1327 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1330 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1332 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1333 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1334 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1335 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1336 */         if (!isIt360)
/*      */         {
/* 1338 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1342 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1345 */         toreturn.append("</tr>");
/* 1346 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1348 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1349 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1353 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1354 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1357 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1361 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1363 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1364 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1365 */             toreturn.append(assocMessage);
/* 1366 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1367 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1368 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1369 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1375 */         String resourcelink = null;
/* 1376 */         boolean hideEditLink = false;
/* 1377 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1379 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1380 */           hideEditLink = true;
/* 1381 */           if (isIt360)
/*      */           {
/* 1383 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1387 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1389 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1391 */           hideEditLink = true;
/* 1392 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1393 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1398 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1401 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1402 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1403 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1404 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1405 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1406 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1407 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1408 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1409 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1410 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1411 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1412 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1413 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1415 */         if (hideEditLink)
/*      */         {
/* 1417 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1419 */         if (!forInventory)
/*      */         {
/* 1421 */           removefromgroup = "";
/*      */         }
/* 1423 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1424 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1425 */           actions = actions + configcustomfields;
/*      */         }
/* 1427 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1429 */           actions = editlink + actions;
/*      */         }
/* 1431 */         String managedLink = "";
/* 1432 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1434 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1435 */           actions = "";
/* 1436 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1437 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1440 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1442 */           checkbox = "";
/*      */         }
/*      */         
/* 1445 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1447 */           actions = "";
/*      */         }
/* 1449 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1450 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1451 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1452 */         if (isIt360)
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1458 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1460 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1461 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1462 */         if (!isIt360)
/*      */         {
/* 1464 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1468 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1470 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1473 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1480 */       StringBuilder toreturn = new StringBuilder();
/* 1481 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1482 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1483 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1484 */       String title = "";
/* 1485 */       message = EnterpriseUtil.decodeString(message);
/* 1486 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1487 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1488 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1490 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1492 */       else if ("5".equals(severity))
/*      */       {
/* 1494 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1498 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1500 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1501 */       toreturn.append(v);
/*      */       
/* 1503 */       toreturn.append(link);
/* 1504 */       if (severity == null)
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       else if (severity.equals("5"))
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1512 */       else if (severity.equals("4"))
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1516 */       else if (severity.equals("1"))
/*      */       {
/* 1518 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1523 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1525 */       toreturn.append("</a>");
/* 1526 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1530 */       ex.printStackTrace();
/*      */     }
/* 1532 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1539 */       StringBuilder toreturn = new StringBuilder();
/* 1540 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1541 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1542 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1543 */       if (message == null)
/*      */       {
/* 1545 */         message = "";
/*      */       }
/*      */       
/* 1548 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1549 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1551 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1552 */       toreturn.append(v);
/*      */       
/* 1554 */       toreturn.append(link);
/*      */       
/* 1556 */       if (severity == null)
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1560 */       else if (severity.equals("5"))
/*      */       {
/* 1562 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1564 */       else if (severity.equals("1"))
/*      */       {
/* 1566 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1571 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1573 */       toreturn.append("</a>");
/* 1574 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1580 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1583 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1584 */     if (invokeActions != null) {
/* 1585 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1586 */       while (iterator.hasNext()) {
/* 1587 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1588 */         if (actionmap.containsKey(actionid)) {
/* 1589 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1594 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1598 */     String actionLink = "";
/* 1599 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1600 */     String query = "";
/* 1601 */     ResultSet rs = null;
/* 1602 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1603 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1604 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1605 */       actionLink = "method=" + methodName;
/*      */     }
/* 1607 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1608 */       actionLink = methodName;
/*      */     }
/* 1610 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1611 */     Iterator itr = methodarglist.iterator();
/* 1612 */     boolean isfirstparam = true;
/* 1613 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1614 */     while (itr.hasNext()) {
/* 1615 */       HashMap argmap = (HashMap)itr.next();
/* 1616 */       String argtype = (String)argmap.get("TYPE");
/* 1617 */       String argname = (String)argmap.get("IDENTITY");
/* 1618 */       String paramname = (String)argmap.get("PARAMETER");
/* 1619 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1620 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1621 */         isfirstparam = false;
/* 1622 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1624 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1628 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1632 */         actionLink = actionLink + "&";
/*      */       }
/* 1634 */       String paramValue = null;
/* 1635 */       String tempargname = argname;
/* 1636 */       if (commonValues.getProperty(tempargname) != null) {
/* 1637 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1640 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1641 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1642 */           if (dbType.equals("mysql")) {
/* 1643 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1646 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1648 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1650 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1651 */             if (rs.next()) {
/* 1652 */               paramValue = rs.getString("VALUE");
/* 1653 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1657 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1661 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1664 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1669 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1670 */           paramValue = rowId;
/*      */         }
/* 1672 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1673 */           paramValue = managedObjectName;
/*      */         }
/* 1675 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1676 */           paramValue = resID;
/*      */         }
/* 1678 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1679 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1682 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1684 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1685 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1686 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1688 */     return actionLink;
/*      */   }
/*      */   
/* 1691 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1692 */     String dependentAttribute = null;
/* 1693 */     String align = "left";
/*      */     
/* 1695 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1696 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1697 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1698 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1699 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1700 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1701 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1702 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1703 */       align = "center";
/*      */     }
/*      */     
/* 1706 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1707 */     String actualdata = "";
/*      */     
/* 1709 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1710 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1711 */         actualdata = availValue;
/*      */       }
/* 1713 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1714 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1718 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1719 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1722 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1728 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1729 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1730 */       toreturn.append("<table>");
/* 1731 */       toreturn.append("<tr>");
/* 1732 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1733 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1734 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1735 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1736 */         String toolTip = "";
/* 1737 */         String hideClass = "";
/* 1738 */         String textStyle = "";
/* 1739 */         boolean isreferenced = true;
/* 1740 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1741 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1742 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1743 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1745 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1746 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1747 */           while (valueList.hasMoreTokens()) {
/* 1748 */             String dependentVal = valueList.nextToken();
/* 1749 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1750 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1751 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1753 */               toolTip = "";
/* 1754 */               hideClass = "";
/* 1755 */               isreferenced = false;
/* 1756 */               textStyle = "disabledtext";
/* 1757 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1761 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1762 */           toolTip = "";
/* 1763 */           hideClass = "";
/* 1764 */           isreferenced = false;
/* 1765 */           textStyle = "disabledtext";
/* 1766 */           if (dependentImageMap != null) {
/* 1767 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1768 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1771 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1775 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1776 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1777 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1778 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1779 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1780 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1782 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1783 */           if (isreferenced) {
/* 1784 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1788 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1789 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1790 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1791 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1792 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1793 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1795 */           toreturn.append("</span>");
/* 1796 */           toreturn.append("</a>");
/* 1797 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1800 */       toreturn.append("</tr>");
/* 1801 */       toreturn.append("</table>");
/* 1802 */       toreturn.append("</td>");
/*      */     } else {
/* 1804 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1807 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1811 */     String colTime = null;
/* 1812 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1813 */     if ((rows != null) && (rows.size() > 0)) {
/* 1814 */       Iterator<String> itr = rows.iterator();
/* 1815 */       String maxColQuery = "";
/* 1816 */       for (;;) { if (itr.hasNext()) {
/* 1817 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1818 */           ResultSet maxCol = null;
/*      */           try {
/* 1820 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1821 */             while (maxCol.next()) {
/* 1822 */               if (colTime == null) {
/* 1823 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1826 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1835 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1837 */               if (maxCol != null)
/* 1838 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1840 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1835 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1837 */               if (maxCol != null)
/* 1838 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1840 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1845 */     return colTime;
/*      */   }
/*      */   
/* 1848 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1849 */     tablename = null;
/* 1850 */     ResultSet rsTable = null;
/* 1851 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1853 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1854 */       while (rsTable.next()) {
/* 1855 */         tablename = rsTable.getString("DATATABLE");
/* 1856 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1857 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1870 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1861 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1864 */         if (rsTable != null)
/* 1865 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1867 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1873 */     String argsList = "";
/* 1874 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1876 */       if (showArgsMap.get(row) != null) {
/* 1877 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1878 */         if (showArgslist != null) {
/* 1879 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1880 */             if (argsList.trim().equals("")) {
/* 1881 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1884 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1891 */       e.printStackTrace();
/* 1892 */       return "";
/*      */     }
/* 1894 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1899 */     String argsList = "";
/* 1900 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1903 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1905 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1906 */         if (hideArgsList != null)
/*      */         {
/* 1908 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1910 */             if (argsList.trim().equals(""))
/*      */             {
/* 1912 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1916 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1924 */       ex.printStackTrace();
/*      */     }
/* 1926 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1930 */     StringBuilder toreturn = new StringBuilder();
/* 1931 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1938 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1939 */       Iterator itr = tActionList.iterator();
/* 1940 */       while (itr.hasNext()) {
/* 1941 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1942 */         String confirmmsg = "";
/* 1943 */         String link = "";
/* 1944 */         String isJSP = "NO";
/* 1945 */         HashMap tactionMap = (HashMap)itr.next();
/* 1946 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1947 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1948 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1949 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1950 */           (actionmap.containsKey(actionId))) {
/* 1951 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1952 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1953 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1954 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1955 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1957 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1963 */           if (isTableAction) {
/* 1964 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1967 */             tableName = "Link";
/* 1968 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1969 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1970 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1971 */             toreturn.append("</a></td>");
/*      */           }
/* 1973 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1974 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1975 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1976 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1982 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1988 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1990 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1991 */       Properties prop = (Properties)node.getUserObject();
/* 1992 */       String mgID = prop.getProperty("label");
/* 1993 */       String mgName = prop.getProperty("value");
/* 1994 */       String isParent = prop.getProperty("isParent");
/* 1995 */       int mgIDint = Integer.parseInt(mgID);
/* 1996 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1998 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2000 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2001 */       if (node.getChildCount() > 0)
/*      */       {
/* 2003 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2005 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2007 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2009 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2013 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2018 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2020 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2022 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2024 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2028 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2031 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2032 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2034 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2038 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2040 */       if (node.getChildCount() > 0)
/*      */       {
/* 2042 */         builder.append("<UL>");
/* 2043 */         printMGTree(node, builder);
/* 2044 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2049 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2050 */     StringBuffer toReturn = new StringBuffer();
/* 2051 */     String table = "-";
/*      */     try {
/* 2053 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2054 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2055 */       float total = 0.0F;
/* 2056 */       while (it.hasNext()) {
/* 2057 */         String attName = (String)it.next();
/* 2058 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2059 */         boolean roundOffData = false;
/* 2060 */         if ((data != null) && (!data.equals(""))) {
/* 2061 */           if (data.indexOf(",") != -1) {
/* 2062 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2065 */             float value = Float.parseFloat(data);
/* 2066 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2069 */             total += value;
/* 2070 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2073 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2078 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2079 */       while (attVsWidthList.hasNext()) {
/* 2080 */         String attName = (String)attVsWidthList.next();
/* 2081 */         String data = (String)attVsWidthProps.get(attName);
/* 2082 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2083 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2084 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2085 */         String className = (String)graphDetails.get("ClassName");
/* 2086 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2087 */         if (percentage < 1.0F)
/*      */         {
/* 2089 */           data = percentage + "";
/*      */         }
/* 2091 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2093 */       if (toReturn.length() > 0) {
/* 2094 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2098 */       e.printStackTrace();
/*      */     }
/* 2100 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2106 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2107 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2108 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2109 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2110 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2111 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2112 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2113 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2114 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2117 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2118 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2119 */       splitvalues[0] = multiplecondition.toString();
/* 2120 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2123 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2128 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2129 */     if (thresholdType != 3) {
/* 2130 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2131 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2132 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2133 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2134 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2135 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2137 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2138 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2139 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2140 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2141 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2142 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2144 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2145 */     if (updateSelected != null) {
/* 2146 */       updateSelected[0] = "selected";
/*      */     }
/* 2148 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2153 */       StringBuffer toreturn = new StringBuffer("");
/* 2154 */       if (commaSeparatedMsgId != null) {
/* 2155 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2156 */         int count = 0;
/* 2157 */         while (msgids.hasMoreTokens()) {
/* 2158 */           String id = msgids.nextToken();
/* 2159 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2160 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2161 */           count++;
/* 2162 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2163 */             if (toreturn.length() == 0) {
/* 2164 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2166 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2167 */             if (!image.trim().equals("")) {
/* 2168 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2170 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2171 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2174 */         if (toreturn.length() > 0) {
/* 2175 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2179 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2182 */       e.printStackTrace(); }
/* 2183 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2189 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2195 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2196 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2215 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2219 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2231 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2235 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2237 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2240 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2244 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2245 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2252 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2255 */     JspWriter out = null;
/* 2256 */     Object page = this;
/* 2257 */     JspWriter _jspx_out = null;
/* 2258 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2262 */       response.setContentType("text/html;charset=UTF-8");
/* 2263 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2265 */       _jspx_page_context = pageContext;
/* 2266 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2267 */       ServletConfig config = pageContext.getServletConfig();
/* 2268 */       session = pageContext.getSession();
/* 2269 */       out = pageContext.getOut();
/* 2270 */       _jspx_out = out;
/*      */       
/* 2272 */       out.write("<!DOCTYPE html>\n");
/* 2273 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 2274 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2276 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2277 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2280 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2286 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2287 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2288 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2289 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2292 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2293 */         String available = null;
/* 2294 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2295 */         out.write(10);
/*      */         
/* 2297 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2298 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2301 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2307 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2308 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2309 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2310 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2313 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2314 */           String unavailable = null;
/* 2315 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2316 */           out.write(10);
/*      */           
/* 2318 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2319 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2322 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2328 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2329 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2330 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2331 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2334 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2335 */             String unmanaged = null;
/* 2336 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2337 */             out.write(10);
/*      */             
/* 2339 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2340 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2343 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2349 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2350 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2351 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2352 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2355 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2356 */               String scheduled = null;
/* 2357 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2358 */               out.write(10);
/*      */               
/* 2360 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2361 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2364 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2370 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2371 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2372 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2373 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2376 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2377 */                 String critical = null;
/* 2378 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2379 */                 out.write(10);
/*      */                 
/* 2381 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2382 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2385 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2391 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2392 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2393 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2394 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2397 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2398 */                   String clear = null;
/* 2399 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2400 */                   out.write(10);
/*      */                   
/* 2402 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2403 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2406 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2412 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2413 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2414 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2415 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2418 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2419 */                     String warning = null;
/* 2420 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2421 */                     out.write(10);
/* 2422 */                     out.write(10);
/*      */                     
/* 2424 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2425 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2427 */                     out.write(10);
/* 2428 */                     out.write(10);
/* 2429 */                     out.write(10);
/* 2430 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n ");
/* 2431 */                     SummaryBean sumgraph = null;
/* 2432 */                     sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/* 2433 */                     if (sumgraph == null) {
/* 2434 */                       sumgraph = new SummaryBean();
/* 2435 */                       _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*      */                     }
/* 2437 */                     out.write(10);
/* 2438 */                     ManagedApplication mo = null;
/* 2439 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2440 */                     if (mo == null) {
/* 2441 */                       mo = new ManagedApplication();
/* 2442 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2444 */                     out.write(10);
/* 2445 */                     Hashtable availabilitykeys = null;
/* 2446 */                     synchronized (application) {
/* 2447 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2448 */                       if (availabilitykeys == null) {
/* 2449 */                         availabilitykeys = new Hashtable();
/* 2450 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2453 */                     out.write(10);
/* 2454 */                     Hashtable healthkeys = null;
/* 2455 */                     synchronized (application) {
/* 2456 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2457 */                       if (healthkeys == null) {
/* 2458 */                         healthkeys = new Hashtable();
/* 2459 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2462 */                     out.write(10);
/*      */                     
/* 2464 */                     request.setAttribute("HelpKey", "RBM Monitor Details");
/*      */                     
/* 2466 */                     out.write(10);
/* 2467 */                     Hashtable applications = null;
/* 2468 */                     synchronized (application) {
/* 2469 */                       applications = (Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 2470 */                       if (applications == null) {
/* 2471 */                         applications = new Hashtable();
/* 2472 */                         _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                       }
/*      */                     }
/* 2475 */                     out.write("\n<SCRIPT LANGAUGE =\"javascript\" SRC=\"../template/validation.js\" ></SCRIPT>\n<SCRIPT LANGAUGE =\"javascript\" SRC=\"../template/appmanager.js\" ></SCRIPT>\n\n<script>\nvar scriptmanager;\nfunction openScriptManager(name,tab)\n{\n\t");
/*      */                     
/* 2477 */                     if ((!EnterpriseUtil.isAdminServer()) && (!request.isUserInRole("OPERATOR")))
/*      */                     {
/*      */ 
/* 2480 */                       out.write("\n\tscriptmanager = window.open('/jsp/RBMScriptManager.jsp?from=urlseqdetails&bcname='+name+'&tab='+tab,'Application_ManagerRBM_Script_Manager','toolbar=no,status=no,menubar=no,width=1000,height=650,scrollbars=yes,location=no');// NO I18N\n\t");
/*      */                     }
/*      */                     
/*      */ 
/* 2484 */                     out.write("\n\n\n}\n</script>\n\n");
/*      */                     
/*      */ 
/* 2487 */                     String webscript = request.getParameter("webscript");
/* 2488 */                     String type = request.getParameter("type");
/* 2489 */                     int urlIndex = 0;
/* 2490 */                     if (type == null)
/*      */                     {
/* 2492 */                       type = "webscript";
/*      */                     }
/* 2494 */                     else if (type.equals("urldetails"))
/*      */                     {
/* 2496 */                       urlIndex = Integer.parseInt(request.getParameter("urlindex"));
/*      */                     }
/* 2498 */                     String healthAttID = "405";
/* 2499 */                     String availAttID = "404";
/* 2500 */                     String responseTimeAttID = "417";
/* 2501 */                     String avgresponseTimeAttID = "410";
/* 2502 */                     if ((type.equals("webscript")) || ("RBM".equals(type)))
/*      */                     {
/* 2504 */                       healthAttID = "8111";
/* 2505 */                       availAttID = "8110";
/* 2506 */                       responseTimeAttID = "8112";
/* 2507 */                       avgresponseTimeAttID = "8122";
/*      */                     }
/*      */                     else
/*      */                     {
/* 2511 */                       healthAttID = "8121";
/* 2512 */                       availAttID = "8120";
/* 2513 */                       responseTimeAttID = "8122";
/* 2514 */                       avgresponseTimeAttID = "8122";
/*      */                     }
/*      */                     
/* 2517 */                     ArrayList attribIDs = new ArrayList();
/* 2518 */                     attribIDs.add("8111");
/* 2519 */                     attribIDs.add("8110");
/* 2520 */                     attribIDs.add("8112");
/* 2521 */                     attribIDs.add("8120");
/* 2522 */                     attribIDs.add("8121");
/* 2523 */                     attribIDs.add("8122");
/* 2524 */                     attribIDs.add("8123");
/* 2525 */                     attribIDs.add("8124");
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2530 */                     Properties prop = (Properties)request.getAttribute("responsetimeforscript");
/* 2531 */                     HashMap resIdVsDisplayName = (HashMap)prop.get("resIdVsDisplayName");
/*      */                     
/*      */ 
/*      */ 
/* 2535 */                     ArrayList rbmAgents = new ArrayList();
/* 2536 */                     ArrayList resIDs = new ArrayList();
/* 2537 */                     Hashtable respTimes = new Hashtable();
/* 2538 */                     String maxRespTimes = "";
/* 2539 */                     if (prop != null)
/*      */                     {
/* 2541 */                       if (prop.containsKey("agents"))
/*      */                       {
/* 2543 */                         rbmAgents = (ArrayList)prop.get("agents");
/*      */                       }
/*      */                       
/* 2546 */                       if (prop.containsKey("resourceid"))
/*      */                       {
/* 2548 */                         resIDs = (ArrayList)prop.get("resourceid");
/*      */                       }
/*      */                       
/* 2551 */                       if (prop.containsKey("responsetime"))
/*      */                       {
/* 2553 */                         respTimes = (Hashtable)prop.get("responsetime");
/*      */                       }
/* 2555 */                       if (prop.containsKey("maxresponsetime"))
/*      */                       {
/* 2557 */                         maxRespTimes = "" + prop.get("maxresponsetime");
/*      */                       }
/*      */                     }
/*      */                     
/* 2561 */                     System.out.println("MAX RESPONSE : " + maxRespTimes);
/*      */                     
/* 2563 */                     Hashtable resiDVsResp = (Hashtable)prop.get("childresponsetime");
/* 2564 */                     Hashtable resiDVsChild = (Hashtable)prop.get("childid");
/*      */                     
/* 2566 */                     String s = "";
/* 2567 */                     String sep = "";
/* 2568 */                     ArrayList childList = new ArrayList();
/* 2569 */                     if (type.equals("urldetails"))
/*      */                     {
/* 2571 */                       for (int j = 0; (resIDs != null) && (j < resIDs.size()); j++)
/*      */                       {
/* 2573 */                         if (resiDVsChild.containsKey((String)resIDs.get(j)))
/*      */                         {
/* 2575 */                           String resourceid = (String)((ArrayList)resiDVsChild.get((String)resIDs.get(j))).get(urlIndex);
/* 2576 */                           childList.add(resourceid);
/* 2577 */                           s = s + sep + resourceid;
/* 2578 */                           sep = ",";
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     else
/*      */                     {
/* 2584 */                       for (int j = 0; (resIDs != null) && (j < resIDs.size()); j++)
/*      */                       {
/* 2586 */                         String resourceid = (String)resIDs.get(j);
/* 2587 */                         s = s + sep + resourceid;
/* 2588 */                         sep = ",";
/*      */                       }
/* 2590 */                       if (resIDs != null)
/*      */                       {
/* 2592 */                         childList = resIDs;
/*      */                       }
/*      */                     }
/*      */                     
/* 2596 */                     sumgraph.setResid(s);
/* 2597 */                     sumgraph.setAttributeid(responseTimeAttID);
/* 2598 */                     sumgraph.setArchivedforUrl(false);
/* 2599 */                     sumgraph.setCompareUrls(true);
/* 2600 */                     sumgraph.setRBMScriptWise(true);
/* 2601 */                     String height = "250";
/*      */                     
/* 2603 */                     Properties alert = FaultUtil.getStatus(childList, attribIDs);
/* 2604 */                     ArrayList agtNotRun = (ArrayList)request.getAttribute("downagents");
/*      */                     
/*      */ 
/* 2607 */                     out.write(10);
/* 2608 */                     out.write(32);
/*      */                     
/* 2610 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2611 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2612 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2614 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2615 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2616 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2618 */                         out.write("\n    ");
/*      */                         
/* 2620 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2621 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2622 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2624 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2626 */                         _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.rbmurlmonitor.type.text"));
/* 2627 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2628 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2629 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2632 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2633 */                         out.write("\n    ");
/* 2634 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2636 */                         out.write(10);
/* 2637 */                         out.write(10);
/*      */                         
/* 2639 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2640 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2641 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2643 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2645 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2646 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2647 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2648 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2649 */                             out = _jspx_page_context.pushBody();
/* 2650 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2651 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2654 */                             out.write("\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */                             
/* 2656 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2657 */                             String aid = request.getParameter("haid");
/* 2658 */                             String haName = null;
/* 2659 */                             if (aid != null)
/*      */                             {
/* 2661 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 2664 */                             out.write(10);
/* 2665 */                             out.write(9);
/* 2666 */                             if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 2668 */                             out.write(10);
/* 2669 */                             out.write(9);
/* 2670 */                             if (_jspx_meth_c_005fset_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 2672 */                             out.write(10);
/* 2673 */                             out.write(9);
/*      */                             
/* 2675 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2676 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2677 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 2679 */                             _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2680 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2681 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2683 */                                 out.write("\n\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2684 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 2685 */                                 out.write(" &gt; ");
/* 2686 */                                 out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2687 */                                 out.write(" &gt; \n\t  ");
/*      */                                 
/* 2689 */                                 if (type.equals("urldetails"))
/*      */                                 {
/* 2691 */                                   String url = request.getParameter("url");
/* 2692 */                                   String disp = request.getParameter("displayname");
/*      */                                   
/* 2694 */                                   out.write("\n\t\t\t  <a href=\"/rbmresources.do?method=showRBMDetails&webscript=");
/* 2695 */                                   out.print(webscript);
/* 2696 */                                   out.write("\" class=\"bcinactive\"> ");
/* 2697 */                                   out.print(webscript);
/* 2698 */                                   out.write("</a>&gt; <span class=\"bcactive\">");
/* 2699 */                                   out.print(disp);
/* 2700 */                                   out.write("</span>\n\t\t\t  ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 2706 */                                   out.write(10);
/* 2707 */                                   out.print(webscript);
/* 2708 */                                   out.write("  </span>\n\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 2713 */                                 out.write("\n\t  </td>\n\n\t");
/* 2714 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2715 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2719 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2720 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                             }
/*      */                             
/* 2723 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2724 */                             out.write(10);
/* 2725 */                             out.write(9);
/*      */                             
/* 2727 */                             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2728 */                             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2729 */                             _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 2731 */                             _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2732 */                             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2733 */                             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                               for (;;) {
/* 2735 */                                 out.write("\n      <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2736 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 2737 */                                 out.write(" &gt; ");
/* 2738 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes("RBM"));
/* 2739 */                                 out.write(" &gt; \n\t  \n\t  ");
/*      */                                 
/* 2741 */                                 if (type.equals("urldetails"))
/*      */                                 {
/* 2743 */                                   String url = request.getParameter("url");
/* 2744 */                                   String disp = request.getParameter("displayname");
/*      */                                   
/* 2746 */                                   out.write("\n\t\t\t  <a href=\"/rbmresources.do?method=showRBMDetails&webscript=");
/* 2747 */                                   out.print(webscript);
/* 2748 */                                   out.write("\" class=\"bcinactive\"> ");
/* 2749 */                                   out.print(webscript);
/* 2750 */                                   out.write("</a>&gt; <span class=\"bcactive\">");
/* 2751 */                                   out.print(disp);
/* 2752 */                                   out.write("</span>\n\t\t\t  ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 2758 */                                   out.write(10);
/* 2759 */                                   out.print(webscript);
/* 2760 */                                   out.write("  </span>\n\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 2765 */                                 out.write("\n\t  \n\t  </td>\n\t");
/* 2766 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2767 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2771 */                             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2772 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                             }
/*      */                             
/* 2775 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2776 */                             out.write(10);
/*      */                             
/* 2778 */                             if ((!EnterpriseUtil.isAdminServer()) && (!request.isUserInRole("OPERATOR")))
/*      */                             {
/*      */ 
/* 2781 */                               out.write("\n\t <td  class=\"bcsign breadcrumb_btm_space\" align=\"right\" ><a href=\"#\" class=\"staticlinks\" onclick=\"javascript:openScriptManager('");
/* 2782 */                               out.print(webscript);
/* 2783 */                               out.write("','webscripttab');\"  title='");
/* 2784 */                               out.print(webscript);
/* 2785 */                               out.write(39);
/* 2786 */                               out.write(32);
/* 2787 */                               out.write(62);
/* 2788 */                               out.print(FormatUtil.getString("am.webclient.rbm.recordbutton.text"));
/* 2789 */                               out.write("</a></td>\n\t\t ");
/*      */                             }
/*      */                             
/*      */ 
/* 2793 */                             out.write("\n    </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n <tr>\n    <td height=\"26\" class=\"tableheadingbborder\">\n\t");
/*      */                             
/* 2795 */                             if (type.equals("urldetails"))
/*      */                             {
/* 2797 */                               String disp = request.getParameter("displayname");
/*      */                               
/* 2799 */                               out.write("\n\t\t\t");
/* 2800 */                               out.print(FormatUtil.getString("am.webclient.rbm.scripturlview.graphheading", new String[] { disp }));
/* 2801 */                               out.write("\n\t\t\t");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 2807 */                               out.write("\n\t\t\t\t");
/* 2808 */                               out.print(FormatUtil.getString("am.webclient.rbm.scriptview.graphheading", new String[] { webscript }));
/* 2809 */                               out.write("\n\t\t\t\t");
/*      */                             }
/*      */                             
/*      */ 
/* 2813 */                             out.write("\n          </td>\n        </tr>\t\n\t<tr>\n\t\t<td height=\"190\" align=\"center\" class=\"bodytext\" >\n\t\t");
/*      */                             
/* 2815 */                             TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 2816 */                             _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 2817 */                             _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 2819 */                             _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("sumgraph");
/*      */                             
/* 2821 */                             _jspx_th_awolf_005ftimechart_005f0.setWidth("850");
/*      */                             
/* 2823 */                             _jspx_th_awolf_005ftimechart_005f0.setHeight(height);
/*      */                             
/* 2825 */                             _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */                             
/* 2827 */                             _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */                             
/* 2829 */                             _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.urlmonitor.responsewithunit.text"));
/*      */                             
/* 2831 */                             _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 2832 */                             int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 2833 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 2834 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2835 */                                 out = _jspx_page_context.pushBody();
/* 2836 */                                 _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 2837 */                                 _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2840 */                                 out.write(10);
/* 2841 */                                 out.write(9);
/* 2842 */                                 out.write(9);
/* 2843 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 2844 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2847 */                               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2848 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2851 */                             if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 2852 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                             }
/*      */                             
/* 2855 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 2856 */                             out.write("\n\t\t</td>\n\t</tr>\n</table>\n<br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n <tr>\n    <td height=\"26\" class=\"tableheadingbborder\">\n\t\n\t\n\t");
/*      */                             
/* 2858 */                             if (type.equals("urldetails"))
/*      */                             {
/* 2860 */                               String disp = request.getParameter("displayname");
/*      */                               
/* 2862 */                               out.write("\n\t\t\t");
/* 2863 */                               out.print(FormatUtil.getString("am.webclient.rbm.scriptview.tableheading", new String[] { disp }));
/* 2864 */                               out.write("\n\t\t\t");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 2870 */                               out.write("\n\t\t\t\t");
/* 2871 */                               out.print(FormatUtil.getString("am.webclient.rbm.scriptview.tableheading", new String[] { webscript }));
/* 2872 */                               out.write("\n\t\t\t\t");
/*      */                             }
/*      */                             
/*      */ 
/* 2876 */                             out.write("\n          </td>\n        </tr>\n\t\t<tr><td width=\"99%\">\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\t\t<tr class=\"bodytextbold\" >\n\t\t<td class=\"columnheading\" width=\"20%\">");
/* 2877 */                             out.print(FormatUtil.getString("am.webclient.rbm.scriptview.tablerowheading"));
/* 2878 */                             out.write("\n\t\t</td>\n\t\t");
/*      */                             
/* 2880 */                             String agt = "";
/* 2881 */                             for (int j = 0; (rbmAgents != null) && (j < rbmAgents.size()); j++)
/*      */                             {
/* 2883 */                               agt = (String)rbmAgents.get(j);
/* 2884 */                               if (agtNotRun.contains(agt))
/*      */                               {
/*      */ 
/* 2887 */                                 out.write("\n\t\t\t\t<td  class=\"columnheading\"  align=center title='");
/* 2888 */                                 out.print(FormatUtil.getString("am.webclient.rbm.agentdown.title", new String[] { agt }));
/* 2889 */                                 out.write("'>\n\t\t\t\t<span  class=\"staticlinksred\"  >");
/* 2890 */                                 out.print(agt);
/* 2891 */                                 out.write("</a></span></td>\n\t\t");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 2897 */                                 out.write("\n\t\t\t\t<td class=\"columnheading\" align=\"center\">");
/* 2898 */                                 out.print(agt);
/* 2899 */                                 out.write("</a></td>\n\t\t");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 2904 */                             out.write("\n\t\t</tr>\n<tr class=\"whitegrayborder\"  ><td class=\"bcactive whitegrayborder\" >&nbsp;");
/* 2905 */                             out.print(FormatUtil.getString("webclient.fault.alarm.severity"));
/* 2906 */                             out.write("</td>\n\t\t");
/*      */                             
/* 2908 */                             String resourceid = "";
/* 2909 */                             String thresholdurl = "";
/* 2910 */                             if (type.equals("urldetails"))
/*      */                             {
/* 2912 */                               for (int jj = 0; jj < resIDs.size(); jj++)
/*      */                               {
/*      */ 
/* 2915 */                                 ArrayList ids = (ArrayList)resiDVsChild.get("" + resIDs.get(jj));
/* 2916 */                                 String urlid = "0";
/* 2917 */                                 if (ids != null)
/*      */                                 {
/* 2919 */                                   if (ids.size() > urlIndex)
/*      */                                   {
/* 2921 */                                     resourceid = "" + ids.get(urlIndex);
/*      */                                   }
/*      */                                   
/*      */                                 }
/*      */                                 else {
/* 2926 */                                   resourceid = "0";
/*      */                                 }
/* 2928 */                                 thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + resourceid;
/* 2929 */                                 String resourceName = "RBMURL";
/*      */                                 
/* 2931 */                                 out.write("\n\t\t\t");
/*      */                                 
/* 2933 */                                 SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2934 */                                 _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2935 */                                 _jspx_th_c_005fset_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                 
/* 2937 */                                 _jspx_th_c_005fset_005f1.setVar("key");
/* 2938 */                                 int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2939 */                                 if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2940 */                                   if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2941 */                                     out = _jspx_page_context.pushBody();
/* 2942 */                                     _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2943 */                                     _jspx_th_c_005fset_005f1.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 2946 */                                     out.write(32);
/* 2947 */                                     out.print(resourceid + "#" + 8121 + "#" + "MESSAGE");
/* 2948 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2949 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2952 */                                   if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2953 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2956 */                                 if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2957 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                                 }
/*      */                                 
/* 2960 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2961 */                                 out.write("\n          <td  align=\"center\" class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" ");
/* 2962 */                                 if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                   return;
/* 2964 */                                 out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2965 */                                 out.print(resourceid);
/* 2966 */                                 out.write("&attributeid=8121&alertconfigurl=");
/* 2967 */                                 out.print(URLEncoder.encode(thresholdurl));
/* 2968 */                                 out.write("')\">");
/* 2969 */                                 out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "8121")));
/* 2970 */                                 out.write("</a></td> \n\n\t\t");
/*      */ 
/*      */                               }
/*      */                               
/*      */                             }
/*      */                             else
/*      */                             {
/* 2977 */                               for (int j = 0; (resIDs != null) && (j < resIDs.size()); j++)
/*      */                               {
/* 2979 */                                 resourceid = (String)resIDs.get(j);
/* 2980 */                                 thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + resourceid;
/* 2981 */                                 String resourceName = "RBM";
/*      */                                 
/*      */ 
/* 2984 */                                 out.write("\n\t\t\t");
/*      */                                 
/* 2986 */                                 SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2987 */                                 _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2988 */                                 _jspx_th_c_005fset_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                 
/* 2990 */                                 _jspx_th_c_005fset_005f2.setVar("key");
/* 2991 */                                 int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2992 */                                 if (_jspx_eval_c_005fset_005f2 != 0) {
/* 2993 */                                   if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2994 */                                     out = _jspx_page_context.pushBody();
/* 2995 */                                     _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 2996 */                                     _jspx_th_c_005fset_005f2.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 2999 */                                     out.write(32);
/* 3000 */                                     out.print(resourceid + "#" + 8111 + "#" + "MESSAGE");
/* 3001 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3002 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3005 */                                   if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3006 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3009 */                                 if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3010 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                                 }
/*      */                                 
/* 3013 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3014 */                                 out.write("\n          <td  align=\"center\" class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" ");
/* 3015 */                                 if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                   return;
/* 3017 */                                 out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3018 */                                 out.print(resourceid);
/* 3019 */                                 out.write("&attributeid=8111&alertconfigurl=");
/* 3020 */                                 out.print(URLEncoder.encode(thresholdurl));
/* 3021 */                                 out.write("')\">");
/* 3022 */                                 out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "8111")));
/* 3023 */                                 out.write("</a></td> \n\n\t\t");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 3028 */                             out.write("\n\t\t</tr>\n\t\t<tr   ><td class=\"bcactive whitegrayborder\" width=\"30%\" >&nbsp;");
/* 3029 */                             out.print(FormatUtil.getString("Response Time (ms)"));
/* 3030 */                             out.write("</td>\n\t\t");
/*      */                             
/* 3032 */                             String rst = "";
/* 3033 */                             String link1 = "";
/* 3034 */                             if (type.equals("urldetails"))
/*      */                             {
/* 3036 */                               for (int jj = 0; jj < resIDs.size(); jj++)
/*      */                               {
/*      */ 
/* 3039 */                                 ArrayList ids = (ArrayList)resiDVsChild.get("" + resIDs.get(jj));
/* 3040 */                                 String respTime = "0";
/* 3041 */                                 String urlid = "0";
/* 3042 */                                 long max = 0L;
/*      */                                 
/* 3044 */                                 for (int m = 0; m < resIDs.size(); m++)
/*      */                                 {
/* 3046 */                                   if (resiDVsChild.containsKey("" + resIDs.get(m)))
/*      */                                   {
/* 3048 */                                     ArrayList r = (ArrayList)resiDVsChild.get("" + resIDs.get(m));
/* 3049 */                                     if (r.size() > urlIndex)
/*      */                                     {
/* 3051 */                                       if (resiDVsResp.containsKey("" + r.get(urlIndex)))
/*      */                                       {
/* 3053 */                                         long temp = Long.parseLong("" + resiDVsResp.get(new StringBuilder().append("").append(r.get(urlIndex)).toString()));
/* 3054 */                                         if (temp > max)
/*      */                                         {
/* 3056 */                                           max = temp;
/*      */                                         }
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 3065 */                                 respTime = "-";
/* 3066 */                                 if (resiDVsChild.containsKey("" + resIDs.get(jj)))
/*      */                                 {
/* 3068 */                                   if (ids.size() > urlIndex)
/*      */                                   {
/* 3070 */                                     urlid = "" + ids.get(urlIndex);
/* 3071 */                                     if (resiDVsResp.containsKey("" + ids.get(urlIndex)))
/*      */                                     {
/* 3073 */                                       respTime = "" + resiDVsResp.get(new StringBuilder().append("").append(ids.get(urlIndex)).toString());
/*      */                                     }
/*      */                                   }
/*      */                                 }
/*      */                                 
/*      */ 
/* 3079 */                                 String maxRes = "" + max;
/*      */                                 
/*      */ 
/* 3082 */                                 link1 = "/showresource.do?resourceid=" + urlid + "&parentid=" + resIDs.get(jj) + "&type=RBMURL&method=showdetails";
/* 3083 */                                 if (respTime.equals("-"))
/*      */                                 {
/*      */ 
/* 3086 */                                   out.write("\n\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" ><a href='");
/* 3087 */                                   out.print(link1);
/* 3088 */                                   out.write("' class='ResourceName' Style='text-decoration:none'> ");
/* 3089 */                                   out.print(respTime);
/* 3090 */                                   out.write("</a></td>\n\t\t\t");
/*      */ 
/*      */ 
/*      */ 
/*      */                                 }
/* 3095 */                                 else if (respTime.equals(maxRes))
/*      */                                 {
/*      */ 
/* 3098 */                                   out.write("\n\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" ><a href='");
/* 3099 */                                   out.print(link1);
/* 3100 */                                   out.write("' class='ResourceName' Style='text-decoration:none'><span class=\"staticlinksred\">");
/*      */                                   
/* 3102 */                                   FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.get(FormatNumberTag.class);
/* 3103 */                                   _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 3104 */                                   _jspx_th_fmt_005fformatNumber_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 3106 */                                   _jspx_th_fmt_005fformatNumber_005f0.setMaxFractionDigits("0");
/* 3107 */                                   int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 3108 */                                   if (_jspx_eval_fmt_005fformatNumber_005f0 != 0) {
/* 3109 */                                     if (_jspx_eval_fmt_005fformatNumber_005f0 != 1) {
/* 3110 */                                       out = _jspx_page_context.pushBody();
/* 3111 */                                       _jspx_th_fmt_005fformatNumber_005f0.setBodyContent((BodyContent)out);
/* 3112 */                                       _jspx_th_fmt_005fformatNumber_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3115 */                                       out.write(32);
/* 3116 */                                       out.print(respTime);
/* 3117 */                                       int evalDoAfterBody = _jspx_th_fmt_005fformatNumber_005f0.doAfterBody();
/* 3118 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3121 */                                     if (_jspx_eval_fmt_005fformatNumber_005f0 != 1) {
/* 3122 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3125 */                                   if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 3126 */                                     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f0); return;
/*      */                                   }
/*      */                                   
/* 3129 */                                   this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 3130 */                                   out.write("</span></a></td>\n\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3136 */                                   out.write("\n\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" ><a href='");
/* 3137 */                                   out.print(link1);
/* 3138 */                                   out.write("' class='ResourceName' Style='text-decoration:none'>");
/*      */                                   
/* 3140 */                                   FormatNumberTag _jspx_th_fmt_005fformatNumber_005f1 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.get(FormatNumberTag.class);
/* 3141 */                                   _jspx_th_fmt_005fformatNumber_005f1.setPageContext(_jspx_page_context);
/* 3142 */                                   _jspx_th_fmt_005fformatNumber_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 3144 */                                   _jspx_th_fmt_005fformatNumber_005f1.setMaxFractionDigits("0");
/* 3145 */                                   int _jspx_eval_fmt_005fformatNumber_005f1 = _jspx_th_fmt_005fformatNumber_005f1.doStartTag();
/* 3146 */                                   if (_jspx_eval_fmt_005fformatNumber_005f1 != 0) {
/* 3147 */                                     if (_jspx_eval_fmt_005fformatNumber_005f1 != 1) {
/* 3148 */                                       out = _jspx_page_context.pushBody();
/* 3149 */                                       _jspx_th_fmt_005fformatNumber_005f1.setBodyContent((BodyContent)out);
/* 3150 */                                       _jspx_th_fmt_005fformatNumber_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3153 */                                       out.write(32);
/* 3154 */                                       out.print(respTime);
/* 3155 */                                       int evalDoAfterBody = _jspx_th_fmt_005fformatNumber_005f1.doAfterBody();
/* 3156 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3159 */                                     if (_jspx_eval_fmt_005fformatNumber_005f1 != 1) {
/* 3160 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3163 */                                   if (_jspx_th_fmt_005fformatNumber_005f1.doEndTag() == 5) {
/* 3164 */                                     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f1); return;
/*      */                                   }
/*      */                                   
/* 3167 */                                   this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 3168 */                                   out.write("</a></td>\n\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 
/*      */ 
/*      */                               }
/*      */                               
/*      */                             }
/*      */                             else
/*      */                             {
/* 3178 */                               for (int j = 0; (resIDs != null) && (j < resIDs.size()); j++)
/*      */                               {
/*      */ 
/* 3181 */                                 resourceid = (String)resIDs.get(j);
/* 3182 */                                 if (respTimes.containsKey(resourceid))
/*      */                                 {
/* 3184 */                                   rst = "" + respTimes.get(new StringBuilder().append("").append(resourceid).toString());
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3188 */                                   rst = "-";
/*      */                                 }
/* 3190 */                                 System.out.println("rst : " + rst + " and maxRes : " + maxRespTimes);
/* 3191 */                                 if (rst == null)
/*      */                                 {
/* 3193 */                                   rst = "-";
/*      */                                 }
/* 3195 */                                 System.out.println("rst : " + rst + " and maxRes : " + maxRespTimes);
/* 3196 */                                 link1 = "/showresource.do?resourceid=" + resourceid + "&type=RBM&method=showdetails&viewType=showResourceTypes";
/* 3197 */                                 if (rst.equals("-"))
/*      */                                 {
/*      */ 
/* 3200 */                                   out.write("\n\t\t\t\t\t<td class=\"whitegrayborder\" align=center ><a href='");
/* 3201 */                                   out.print(link1);
/* 3202 */                                   out.write("' class='ResourceName' Style='text-decoration:none'> ");
/* 3203 */                                   out.print(rst);
/* 3204 */                                   out.write("</a></td>\n\t\t");
/*      */ 
/*      */ 
/*      */ 
/*      */                                 }
/* 3209 */                                 else if (rst.equals(maxRespTimes))
/*      */                                 {
/*      */ 
/* 3212 */                                   out.write("\n\t\t\t\t\t<td class=\"whitegrayborder\" align=center ><a href='");
/* 3213 */                                   out.print(link1);
/* 3214 */                                   out.write("' class='ResourceName' Style='text-decoration:none'><span class=\"staticlinksred\">");
/*      */                                   
/* 3216 */                                   FormatNumberTag _jspx_th_fmt_005fformatNumber_005f2 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.get(FormatNumberTag.class);
/* 3217 */                                   _jspx_th_fmt_005fformatNumber_005f2.setPageContext(_jspx_page_context);
/* 3218 */                                   _jspx_th_fmt_005fformatNumber_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 3220 */                                   _jspx_th_fmt_005fformatNumber_005f2.setMaxFractionDigits("0");
/* 3221 */                                   int _jspx_eval_fmt_005fformatNumber_005f2 = _jspx_th_fmt_005fformatNumber_005f2.doStartTag();
/* 3222 */                                   if (_jspx_eval_fmt_005fformatNumber_005f2 != 0) {
/* 3223 */                                     if (_jspx_eval_fmt_005fformatNumber_005f2 != 1) {
/* 3224 */                                       out = _jspx_page_context.pushBody();
/* 3225 */                                       _jspx_th_fmt_005fformatNumber_005f2.setBodyContent((BodyContent)out);
/* 3226 */                                       _jspx_th_fmt_005fformatNumber_005f2.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3229 */                                       out.write(32);
/* 3230 */                                       out.print(rst);
/* 3231 */                                       int evalDoAfterBody = _jspx_th_fmt_005fformatNumber_005f2.doAfterBody();
/* 3232 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3235 */                                     if (_jspx_eval_fmt_005fformatNumber_005f2 != 1) {
/* 3236 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3239 */                                   if (_jspx_th_fmt_005fformatNumber_005f2.doEndTag() == 5) {
/* 3240 */                                     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f2); return;
/*      */                                   }
/*      */                                   
/* 3243 */                                   this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 3244 */                                   out.write("</span></a></td>\n\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3250 */                                   out.write("\n\t\t\t\t\t<td class=\"whitegrayborder\" align=center ><a href='");
/* 3251 */                                   out.print(link1);
/* 3252 */                                   out.write("' class='ResourceName' Style='text-decoration:none'>");
/*      */                                   
/* 3254 */                                   FormatNumberTag _jspx_th_fmt_005fformatNumber_005f3 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.get(FormatNumberTag.class);
/* 3255 */                                   _jspx_th_fmt_005fformatNumber_005f3.setPageContext(_jspx_page_context);
/* 3256 */                                   _jspx_th_fmt_005fformatNumber_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 3258 */                                   _jspx_th_fmt_005fformatNumber_005f3.setMaxFractionDigits("0");
/* 3259 */                                   int _jspx_eval_fmt_005fformatNumber_005f3 = _jspx_th_fmt_005fformatNumber_005f3.doStartTag();
/* 3260 */                                   if (_jspx_eval_fmt_005fformatNumber_005f3 != 0) {
/* 3261 */                                     if (_jspx_eval_fmt_005fformatNumber_005f3 != 1) {
/* 3262 */                                       out = _jspx_page_context.pushBody();
/* 3263 */                                       _jspx_th_fmt_005fformatNumber_005f3.setBodyContent((BodyContent)out);
/* 3264 */                                       _jspx_th_fmt_005fformatNumber_005f3.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3267 */                                       out.write(32);
/* 3268 */                                       out.print(rst);
/* 3269 */                                       int evalDoAfterBody = _jspx_th_fmt_005fformatNumber_005f3.doAfterBody();
/* 3270 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3273 */                                     if (_jspx_eval_fmt_005fformatNumber_005f3 != 1) {
/* 3274 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3277 */                                   if (_jspx_th_fmt_005fformatNumber_005f3.doEndTag() == 5) {
/* 3278 */                                     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f3); return;
/*      */                                   }
/*      */                                   
/* 3281 */                                   this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 3282 */                                   out.write("</a></td>\n\n\t\t");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/* 3289 */                             out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"bcactive whitegrayborder\" width=\"30%\" >&nbsp;");
/* 3290 */                             out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 3291 */                             out.write("\n\t\t\t</td>\n\t\t\t");
/* 3292 */                             for (int m = 0; m < resIDs.size(); m++) {
/* 3293 */                               if (resIdVsDisplayName.containsKey("" + resIDs.get(m))) {
/* 3294 */                                 String url = "/showresource.do?resourceid=" + resIDs.get(m) + "&type=RBM&method=showdetails&viewType=showResourceTypes";
/* 3295 */                                 String disName = (String)resIdVsDisplayName.get(resIDs.get(m));
/* 3296 */                                 if (disName.length() > 15) {
/* 3297 */                                   disName = FormatUtil.getTrimmedText(disName, 15);
/*      */                                 }
/*      */                                 
/*      */ 
/* 3301 */                                 out.write("\n\t\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" style=\"padding-left:30px\">\n\t\t\t\t\t\t<a href=\"");
/* 3302 */                                 out.print(url);
/* 3303 */                                 out.write("\"  class=\"staticlinks\"  onMouseOver=\"ddrivetip(this,event,'");
/* 3304 */                                 out.print(((String)resIdVsDisplayName.get(resIDs.get(m))).length() > 15 ? resIdVsDisplayName.get(resIDs.get(m)) : "");
/* 3305 */                                 out.write("',false,true,'#000000',100,'lightyellow')\" onmouseout='");
/* 3306 */                                 out.print(((String)resIdVsDisplayName.get(resIDs.get(m))).length() > 15 ? "hideddrivetip()" : "");
/* 3307 */                                 out.write(39);
/* 3308 */                                 out.write(62);
/* 3309 */                                 out.write(" \n\t\t\t\t\t\t\t<span class=\"bcsign\">");
/* 3310 */                                 out.print(disName);
/* 3311 */                                 out.write("</span>\n\t\t\t\t\t\t</a>\n\t\t\t\t\t</td>\t\n\t\t\t");
/*      */                               }
/*      */                             }
/* 3314 */                             out.write("\t\n\t\t</tr>\t\n\n\n\t\t</table>\n\t\t</td></tr>\n\t\t</table>\n\n<br>\n");
/*      */                             
/* 3316 */                             if (!type.equals("urldetails"))
/*      */                             {
/*      */ 
/* 3319 */                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n <tr>\n    <td height=\"26\" class=\"tableheadingbborder\">\n\t");
/* 3320 */                               out.print(FormatUtil.getString("am.webclient.rbm.scriptview.urltableheading", new String[] { webscript }));
/* 3321 */                               out.write("\n          </td>\n        </tr>\n\t\t<tr><td width=\"99%\">\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\t\t<tr class=\"bodytextbold\" >\n\t\t<td class=\"columnheading\" width=\"20%\">");
/* 3322 */                               out.print(FormatUtil.getString("am.webclient.rbm.scriptview.tablerowheading"));
/* 3323 */                               out.write("\n\t\t\t</td>\n\t\t");
/*      */                               
/*      */ 
/* 3326 */                               for (int j = 0; (rbmAgents != null) && (j < rbmAgents.size()); j++)
/*      */                               {
/* 3328 */                                 agt = (String)rbmAgents.get(j);
/* 3329 */                                 if (agtNotRun.contains(agt))
/*      */                                 {
/*      */ 
/* 3332 */                                   out.write("\n\t\t\t<td class=\"columnheading\"  align=center title='");
/* 3333 */                                   out.print(FormatUtil.getString("am.webclient.rbm.agentdown.title", new String[] { agt }));
/* 3334 */                                   out.write("'><span  class=\"staticlinksred\"  >");
/* 3335 */                                   out.print(agt);
/* 3336 */                                   out.write("</a></span></td>\n\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3342 */                                   out.write("\n\t\t\t\t<td class=\"columnheading\" align=\"center\">");
/* 3343 */                                   out.print(agt);
/* 3344 */                                   out.write("</a></td>\n\t\t");
/*      */                                 }
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 3351 */                               out.write("\n\t\t</tr>\n");
/*      */                               
/* 3353 */                               List urlDets = null;
/* 3354 */                               for (int j = 0; (resIDs != null) && (j < resIDs.size()); j++)
/*      */                               {
/* 3356 */                                 String rsid = (String)resIDs.get(j);
/* 3357 */                                 String qry = "select b.RESOURCEID,b.RESOURCENAME,b.DISPLAYNAME from AM_ManagedObject b, AM_PARENTCHILDMAPPER a where a.CHILDID=b.RESOURCEID and a.PARENTID=" + rsid + " order by b.RESOURCEID";
/* 3358 */                                 System.out.println("qry : " + qry);
/* 3359 */                                 urlDets = mo.getRows(qry);
/* 3360 */                                 if (urlDets.size() > 0) {
/*      */                                   break;
/*      */                                 }
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 3367 */                               for (int j = 0; (urlDets != null) && (j < urlDets.size()); j++)
/*      */                               {
/* 3369 */                                 ArrayList row = (ArrayList)urlDets.get(j);
/* 3370 */                                 String urlId = (String)row.get(0);
/* 3371 */                                 String url = (String)row.get(1);
/* 3372 */                                 String disp = (String)row.get(2);
/* 3373 */                                 String displayname = "<a href='/rbmresources.do?method=showRBMDetails&webscript=" + webscript + "&type=urldetails&urlindex=" + j + "&displayname=" + disp + "&url=" + URLEncoder.encode(url) + "'  class='ResourceName'>" + getTrimmedText(disp, 60) + "</a>";
/*      */                                 
/*      */ 
/* 3376 */                                 out.write("\n\t\t<tr><td class=\"whitegrayborder\"  title='");
/* 3377 */                                 out.print(url);
/* 3378 */                                 out.write("' width=\"30%\" >");
/* 3379 */                                 out.print(displayname);
/* 3380 */                                 out.write("</td>\n\t\t");
/*      */                                 
/* 3382 */                                 long max = 0L;
/* 3383 */                                 for (int m = 0; m < resIDs.size(); m++)
/*      */                                 {
/* 3385 */                                   if (resiDVsChild.containsKey("" + resIDs.get(m)))
/*      */                                   {
/* 3387 */                                     ArrayList r = (ArrayList)resiDVsChild.get("" + resIDs.get(m));
/* 3388 */                                     if (r.size() > j)
/*      */                                     {
/* 3390 */                                       if (resiDVsResp.containsKey("" + r.get(j)))
/*      */                                       {
/* 3392 */                                         long temp = Long.parseLong("" + resiDVsResp.get(new StringBuilder().append("").append(r.get(j)).toString()));
/* 3393 */                                         if (temp > max)
/*      */                                         {
/* 3395 */                                           max = temp;
/*      */                                         }
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 3402 */                                 String respTime = "0";
/* 3403 */                                 String urlid = "0";
/* 3404 */                                 String maxRes = "" + max;
/* 3405 */                                 System.out.println("Maximun : " + maxRes);
/* 3406 */                                 for (int jj = 0; jj < resIDs.size(); jj++)
/*      */                                 {
/*      */ 
/* 3409 */                                   ArrayList ids = (ArrayList)resiDVsChild.get("" + resIDs.get(jj));
/* 3410 */                                   respTime = "-";
/* 3411 */                                   if (resiDVsChild.containsKey("" + resIDs.get(jj)))
/*      */                                   {
/* 3413 */                                     if (ids.size() > j)
/*      */                                     {
/* 3415 */                                       urlid = "" + ids.get(j);
/* 3416 */                                       if (resiDVsResp.containsKey("" + ids.get(j)))
/*      */                                       {
/* 3418 */                                         respTime = "" + resiDVsResp.get(new StringBuilder().append("").append(ids.get(j)).toString());
/*      */                                       }
/*      */                                     }
/*      */                                   }
/* 3422 */                                   link1 = "/showresource.do?resourceid=" + urlid + "&parentid=" + resIDs.get(jj) + "&type=RBMURL&method=showdetails";
/* 3423 */                                   if (respTime.equals("-"))
/*      */                                   {
/*      */ 
/* 3426 */                                     out.write("\n\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" ><a href='");
/* 3427 */                                     out.print(link1);
/* 3428 */                                     out.write("' class='ResourceName' Style='text-decoration:none'> ");
/* 3429 */                                     out.print(respTime);
/* 3430 */                                     out.write("</a></td>\n\t\t\t");
/*      */ 
/*      */ 
/*      */ 
/*      */                                   }
/* 3435 */                                   else if (respTime.equals(maxRes))
/*      */                                   {
/*      */ 
/* 3438 */                                     out.write("\n\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" ><a href='");
/* 3439 */                                     out.print(link1);
/* 3440 */                                     out.write("' class='ResourceName' Style='text-decoration:none'><span class=\"staticlinksred\">");
/*      */                                     
/* 3442 */                                     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f4 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.get(FormatNumberTag.class);
/* 3443 */                                     _jspx_th_fmt_005fformatNumber_005f4.setPageContext(_jspx_page_context);
/* 3444 */                                     _jspx_th_fmt_005fformatNumber_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                     
/* 3446 */                                     _jspx_th_fmt_005fformatNumber_005f4.setMaxFractionDigits("0");
/* 3447 */                                     int _jspx_eval_fmt_005fformatNumber_005f4 = _jspx_th_fmt_005fformatNumber_005f4.doStartTag();
/* 3448 */                                     if (_jspx_eval_fmt_005fformatNumber_005f4 != 0) {
/* 3449 */                                       if (_jspx_eval_fmt_005fformatNumber_005f4 != 1) {
/* 3450 */                                         out = _jspx_page_context.pushBody();
/* 3451 */                                         _jspx_th_fmt_005fformatNumber_005f4.setBodyContent((BodyContent)out);
/* 3452 */                                         _jspx_th_fmt_005fformatNumber_005f4.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3455 */                                         out.write(32);
/* 3456 */                                         out.print(respTime);
/* 3457 */                                         int evalDoAfterBody = _jspx_th_fmt_005fformatNumber_005f4.doAfterBody();
/* 3458 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3461 */                                       if (_jspx_eval_fmt_005fformatNumber_005f4 != 1) {
/* 3462 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3465 */                                     if (_jspx_th_fmt_005fformatNumber_005f4.doEndTag() == 5) {
/* 3466 */                                       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f4); return;
/*      */                                     }
/*      */                                     
/* 3469 */                                     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 3470 */                                     out.write("</span></a></td>\n\t\t\t");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 3476 */                                     out.write("\n\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" ><a href='");
/* 3477 */                                     out.print(link1);
/* 3478 */                                     out.write("' class='ResourceName' Style='text-decoration:none'>");
/*      */                                     
/* 3480 */                                     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f5 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.get(FormatNumberTag.class);
/* 3481 */                                     _jspx_th_fmt_005fformatNumber_005f5.setPageContext(_jspx_page_context);
/* 3482 */                                     _jspx_th_fmt_005fformatNumber_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                     
/* 3484 */                                     _jspx_th_fmt_005fformatNumber_005f5.setMaxFractionDigits("0");
/* 3485 */                                     int _jspx_eval_fmt_005fformatNumber_005f5 = _jspx_th_fmt_005fformatNumber_005f5.doStartTag();
/* 3486 */                                     if (_jspx_eval_fmt_005fformatNumber_005f5 != 0) {
/* 3487 */                                       if (_jspx_eval_fmt_005fformatNumber_005f5 != 1) {
/* 3488 */                                         out = _jspx_page_context.pushBody();
/* 3489 */                                         _jspx_th_fmt_005fformatNumber_005f5.setBodyContent((BodyContent)out);
/* 3490 */                                         _jspx_th_fmt_005fformatNumber_005f5.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3493 */                                         out.write(32);
/* 3494 */                                         out.print(respTime);
/* 3495 */                                         int evalDoAfterBody = _jspx_th_fmt_005fformatNumber_005f5.doAfterBody();
/* 3496 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3499 */                                       if (_jspx_eval_fmt_005fformatNumber_005f5 != 1) {
/* 3500 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3503 */                                     if (_jspx_th_fmt_005fformatNumber_005f5.doEndTag() == 5) {
/* 3504 */                                       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f5); return;
/*      */                                     }
/*      */                                     
/* 3507 */                                     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fmaxFractionDigits.reuse(_jspx_th_fmt_005fformatNumber_005f5);
/* 3508 */                                     out.write("</a></td>\n\t\t\t");
/*      */                                   }
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/* 3515 */                                 out.write("\n\t\t\t</tr>\n\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 3520 */                               out.write("\n\t\n\t\t</table>\n</td></tr>\n</table>\n");
/*      */                             }
/*      */                             
/*      */ 
/* 3524 */                             out.write(10);
/* 3525 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3526 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3529 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3530 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3533 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3534 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 3537 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3538 */                         out.write(10);
/* 3539 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 3541 */                         out.write(32);
/* 3542 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3543 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3547 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3548 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 3551 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3552 */                       out.write(10);
/*      */                     }
/* 3554 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3555 */         out = _jspx_out;
/* 3556 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3557 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3558 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3561 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3567 */     PageContext pageContext = _jspx_page_context;
/* 3568 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3570 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3571 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3572 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3574 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3576 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 3577 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3578 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3579 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3580 */       return true;
/*      */     }
/* 3582 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3583 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3588 */     PageContext pageContext = _jspx_page_context;
/* 3589 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3591 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3592 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3593 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3595 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3596 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3598 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3599 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3601 */           out.write("\n\t    ");
/* 3602 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3603 */             return true;
/* 3604 */           out.write(10);
/* 3605 */           out.write(9);
/* 3606 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3607 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3611 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3612 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3615 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 3616 */         out = _jspx_page_context.popBody(); }
/* 3617 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3619 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3620 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3627 */     PageContext pageContext = _jspx_page_context;
/* 3628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3630 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3631 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3632 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3634 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3636 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3637 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3638 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3639 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3640 */       return true;
/*      */     }
/* 3642 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3643 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3648 */     PageContext pageContext = _jspx_page_context;
/* 3649 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3651 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3652 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3653 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3655 */     _jspx_th_c_005fset_005f0.setVar("breadcrumbname");
/* 3656 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3657 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3658 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3659 */         out = _jspx_page_context.pushBody();
/* 3660 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3661 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3664 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 3665 */           return true;
/* 3666 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3667 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3670 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3671 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3674 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3675 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3676 */       return true;
/*      */     }
/* 3678 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3684 */     PageContext pageContext = _jspx_page_context;
/* 3685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3687 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3688 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3689 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3691 */     _jspx_th_c_005fout_005f0.setValue("${props.name}");
/* 3692 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3693 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3694 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3695 */       return true;
/*      */     }
/* 3697 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3698 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3703 */     PageContext pageContext = _jspx_page_context;
/* 3704 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3706 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3707 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3708 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3710 */     _jspx_th_c_005fif_005f2.setTest("${alert[key]!=null}");
/* 3711 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3712 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3714 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3715 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3716 */           return true;
/* 3717 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3718 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3719 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3723 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3724 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3725 */       return true;
/*      */     }
/* 3727 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3728 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3733 */     PageContext pageContext = _jspx_page_context;
/* 3734 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3736 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3737 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3738 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3740 */     _jspx_th_c_005fout_005f1.setValue("${alert[key]}");
/* 3741 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3742 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3743 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3744 */       return true;
/*      */     }
/* 3746 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3752 */     PageContext pageContext = _jspx_page_context;
/* 3753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3755 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3756 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3757 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3759 */     _jspx_th_c_005fif_005f3.setTest("${alert[key]!=null}");
/* 3760 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3761 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3763 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3764 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3765 */           return true;
/* 3766 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3767 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3768 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3772 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3773 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3774 */       return true;
/*      */     }
/* 3776 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3782 */     PageContext pageContext = _jspx_page_context;
/* 3783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3785 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3786 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3787 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3789 */     _jspx_th_c_005fout_005f2.setValue("${alert[key]}");
/* 3790 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3791 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3792 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3793 */       return true;
/*      */     }
/* 3795 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3796 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3801 */     PageContext pageContext = _jspx_page_context;
/* 3802 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3804 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3805 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3806 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3808 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 3810 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 3811 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3812 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3813 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3814 */       return true;
/*      */     }
/* 3816 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3817 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\rbmresourcedetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */