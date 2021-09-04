/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.HAAlertGraph;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
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
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class DisplayMGStatus_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   56 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   59 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   60 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   61 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   68 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   73 */     ArrayList list = null;
/*   74 */     StringBuffer sbf = new StringBuffer();
/*   75 */     ManagedApplication mo = new ManagedApplication();
/*   76 */     if (distinct)
/*      */     {
/*   78 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   82 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   85 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   87 */       ArrayList row = (ArrayList)list.get(i);
/*   88 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   89 */       if (distinct) {
/*   90 */         sbf.append(row.get(0));
/*      */       } else
/*   92 */         sbf.append(row.get(1));
/*   93 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   96 */     return sbf.toString(); }
/*      */   
/*   98 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  101 */     if (severity == null)
/*      */     {
/*  103 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  105 */     if (severity.equals("5"))
/*      */     {
/*  107 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  109 */     if (severity.equals("1"))
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  116 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  123 */     if (severity == null)
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  127 */     if (severity.equals("1"))
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  131 */     if (severity.equals("4"))
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  135 */     if (severity.equals("5"))
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  142 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  148 */     if (severity == null)
/*      */     {
/*  150 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  152 */     if (severity.equals("5"))
/*      */     {
/*  154 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  156 */     if (severity.equals("1"))
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  162 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  168 */     if (severity == null)
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  172 */     if (severity.equals("1"))
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  176 */     if (severity.equals("4"))
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  180 */     if (severity.equals("5"))
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  186 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  192 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  198 */     if (severity == 5)
/*      */     {
/*  200 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  202 */     if (severity == 1)
/*      */     {
/*  204 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  209 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  215 */     if (severity == null)
/*      */     {
/*  217 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  219 */     if (severity.equals("5"))
/*      */     {
/*  221 */       if (isAvailability) {
/*  222 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  225 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  228 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  230 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  232 */     if (severity.equals("1"))
/*      */     {
/*  234 */       if (isAvailability) {
/*  235 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  238 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  245 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  252 */     if (severity == null)
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  256 */     if (severity.equals("5"))
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  260 */     if (severity.equals("4"))
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  264 */     if (severity.equals("1"))
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  271 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  277 */     if (severity == null)
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  281 */     if (severity.equals("5"))
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  285 */     if (severity.equals("4"))
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  289 */     if (severity.equals("1"))
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  296 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  303 */     if (severity == null)
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  307 */     if (severity.equals("5"))
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  311 */     if (severity.equals("4"))
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  315 */     if (severity.equals("1"))
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  322 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  330 */     StringBuffer out = new StringBuffer();
/*  331 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  332 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  333 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  334 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  335 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  336 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  337 */     out.append("</tr>");
/*  338 */     out.append("</form></table>");
/*  339 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  346 */     if (val == null)
/*      */     {
/*  348 */       return "-";
/*      */     }
/*      */     
/*  351 */     String ret = FormatUtil.formatNumber(val);
/*  352 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  353 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  356 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  360 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  368 */     StringBuffer out = new StringBuffer();
/*  369 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  370 */     out.append("<tr>");
/*  371 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  373 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  375 */     out.append("</tr>");
/*  376 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  380 */       if (j % 2 == 0)
/*      */       {
/*  382 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  386 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  389 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  391 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  394 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  398 */       out.append("</tr>");
/*      */     }
/*  400 */     out.append("</table>");
/*  401 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  402 */     out.append("<tr>");
/*  403 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  404 */     out.append("</tr>");
/*  405 */     out.append("</table>");
/*  406 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  412 */     StringBuffer out = new StringBuffer();
/*  413 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  414 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  415 */     out.append("<tr>");
/*  416 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  417 */     out.append("<tr>");
/*  418 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  419 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  420 */     out.append("</tr>");
/*  421 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  424 */       out.append("<tr>");
/*  425 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  426 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  427 */       out.append("</tr>");
/*      */     }
/*      */     
/*  430 */     out.append("</table>");
/*  431 */     out.append("</table>");
/*  432 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  437 */     if (severity.equals("0"))
/*      */     {
/*  439 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  443 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  450 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  463 */     StringBuffer out = new StringBuffer();
/*  464 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  465 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  467 */       out.append("<tr>");
/*  468 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  469 */       out.append("</tr>");
/*      */       
/*      */ 
/*  472 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  474 */         String borderclass = "";
/*      */         
/*      */ 
/*  477 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  479 */         out.append("<tr>");
/*      */         
/*  481 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  482 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  483 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  489 */     out.append("</table><br>");
/*  490 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  491 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  493 */       List sLinks = secondLevelOfLinks[0];
/*  494 */       List sText = secondLevelOfLinks[1];
/*  495 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  498 */         out.append("<tr>");
/*  499 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  500 */         out.append("</tr>");
/*  501 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  503 */           String borderclass = "";
/*      */           
/*      */ 
/*  506 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  508 */           out.append("<tr>");
/*      */           
/*  510 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  511 */           if (sLinks.get(i).toString().length() == 0) {
/*  512 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  515 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  517 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  521 */     out.append("</table>");
/*  522 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  529 */     StringBuffer out = new StringBuffer();
/*  530 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  531 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  533 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  535 */         out.append("<tr>");
/*  536 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  537 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  541 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  543 */           String borderclass = "";
/*      */           
/*      */ 
/*  546 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  548 */           out.append("<tr>");
/*      */           
/*  550 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  551 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  552 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  555 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  558 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  563 */     out.append("</table><br>");
/*  564 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  565 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  567 */       List sLinks = secondLevelOfLinks[0];
/*  568 */       List sText = secondLevelOfLinks[1];
/*  569 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  572 */         out.append("<tr>");
/*  573 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  574 */         out.append("</tr>");
/*  575 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  577 */           String borderclass = "";
/*      */           
/*      */ 
/*  580 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  582 */           out.append("<tr>");
/*      */           
/*  584 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  585 */           if (sLinks.get(i).toString().length() == 0) {
/*  586 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  589 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  591 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  595 */     out.append("</table>");
/*  596 */     return out.toString();
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
/*  609 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  618 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  621 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  624 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  627 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  630 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  638 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  643 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  648 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  653 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  658 */     if (val != null)
/*      */     {
/*  660 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  664 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  669 */     if (val == null) {
/*  670 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  674 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  679 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  685 */     if (val != null)
/*      */     {
/*  687 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  691 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  697 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  702 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  706 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  711 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  716 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  721 */     String hostaddress = "";
/*  722 */     String ip = request.getHeader("x-forwarded-for");
/*  723 */     if (ip == null)
/*  724 */       ip = request.getRemoteAddr();
/*  725 */     InetAddress add = null;
/*  726 */     if (ip.equals("127.0.0.1")) {
/*  727 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  731 */       add = InetAddress.getByName(ip);
/*      */     }
/*  733 */     hostaddress = add.getHostName();
/*  734 */     if (hostaddress.indexOf('.') != -1) {
/*  735 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  736 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  740 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  745 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  751 */     if (severity == null)
/*      */     {
/*  753 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  755 */     if (severity.equals("5"))
/*      */     {
/*  757 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  759 */     if (severity.equals("1"))
/*      */     {
/*  761 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  766 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  771 */     ResultSet set = null;
/*  772 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  773 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  775 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  776 */       if (set.next()) { String str1;
/*  777 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  778 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  781 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  786 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  789 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  791 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  795 */     StringBuffer rca = new StringBuffer();
/*  796 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  797 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  800 */     int rcalength = key.length();
/*  801 */     String split = "6. ";
/*  802 */     int splitPresent = key.indexOf(split);
/*  803 */     String div1 = "";String div2 = "";
/*  804 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  806 */       if (rcalength > 180) {
/*  807 */         rca.append("<span class=\"rca-critical-text\">");
/*  808 */         getRCATrimmedText(key, rca);
/*  809 */         rca.append("</span>");
/*      */       } else {
/*  811 */         rca.append("<span class=\"rca-critical-text\">");
/*  812 */         rca.append(key);
/*  813 */         rca.append("</span>");
/*      */       }
/*  815 */       return rca.toString();
/*      */     }
/*  817 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  818 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  819 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  820 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  821 */     getRCATrimmedText(div1, rca);
/*  822 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  825 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  826 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  827 */     getRCATrimmedText(div2, rca);
/*  828 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  830 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  835 */     String[] st = msg.split("<br>");
/*  836 */     for (int i = 0; i < st.length; i++) {
/*  837 */       String s = st[i];
/*  838 */       if (s.length() > 180) {
/*  839 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  841 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  845 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  846 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  848 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  852 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  853 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  854 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  857 */       if (key == null) {
/*  858 */         return ret;
/*      */       }
/*      */       
/*  861 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  862 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  865 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  866 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  867 */       set = AMConnectionPool.executeQueryStmt(query);
/*  868 */       if (set.next())
/*      */       {
/*  870 */         String helpLink = set.getString("LINK");
/*  871 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  874 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  880 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  899 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  890 */         if (set != null) {
/*  891 */           AMConnectionPool.closeStatement(set);
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
/*  905 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  906 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  908 */       String entityStr = (String)keys.nextElement();
/*  909 */       String mmessage = temp.getProperty(entityStr);
/*  910 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  911 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  913 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  919 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  920 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  922 */       String entityStr = (String)keys.nextElement();
/*  923 */       String mmessage = temp.getProperty(entityStr);
/*  924 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  925 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  927 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  932 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  942 */     String des = new String();
/*  943 */     while (str.indexOf(find) != -1) {
/*  944 */       des = des + str.substring(0, str.indexOf(find));
/*  945 */       des = des + replace;
/*  946 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  948 */     des = des + str;
/*  949 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  956 */       if (alert == null)
/*      */       {
/*  958 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  960 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  962 */         return "&nbsp;";
/*      */       }
/*      */       
/*  965 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  967 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  970 */       int rcalength = test.length();
/*  971 */       if (rcalength < 300)
/*      */       {
/*  973 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  977 */       StringBuffer out = new StringBuffer();
/*  978 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  979 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  980 */       out.append("</div>");
/*  981 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  982 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  983 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  988 */       ex.printStackTrace();
/*      */     }
/*  990 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  996 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1001 */     ArrayList attribIDs = new ArrayList();
/* 1002 */     ArrayList resIDs = new ArrayList();
/* 1003 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1005 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1007 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1009 */       String resourceid = "";
/* 1010 */       String resourceType = "";
/* 1011 */       if (type == 2) {
/* 1012 */         resourceid = (String)row.get(0);
/* 1013 */         resourceType = (String)row.get(3);
/*      */       }
/* 1015 */       else if (type == 3) {
/* 1016 */         resourceid = (String)row.get(0);
/* 1017 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1020 */         resourceid = (String)row.get(6);
/* 1021 */         resourceType = (String)row.get(7);
/*      */       }
/* 1023 */       resIDs.add(resourceid);
/* 1024 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1025 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1027 */       String healthentity = null;
/* 1028 */       String availentity = null;
/* 1029 */       if (healthid != null) {
/* 1030 */         healthentity = resourceid + "_" + healthid;
/* 1031 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1034 */       if (availid != null) {
/* 1035 */         availentity = resourceid + "_" + availid;
/* 1036 */         entitylist.add(availentity);
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
/* 1050 */     Properties alert = getStatus(entitylist);
/* 1051 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1056 */     int size = monitorList.size();
/*      */     
/* 1058 */     String[] severity = new String[size];
/*      */     
/* 1060 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1062 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1063 */       String resourceName1 = (String)row1.get(7);
/* 1064 */       String resourceid1 = (String)row1.get(6);
/* 1065 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1066 */       if (severity[j] == null)
/*      */       {
/* 1068 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1072 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1074 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1076 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1079 */         if (sev > 0) {
/* 1080 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1081 */           monitorList.set(k, monitorList.get(j));
/* 1082 */           monitorList.set(j, t);
/* 1083 */           String temp = severity[k];
/* 1084 */           severity[k] = severity[j];
/* 1085 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1091 */     int z = 0;
/* 1092 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1095 */       int i = 0;
/* 1096 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1099 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1103 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1107 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1109 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1112 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1116 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1119 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1120 */       String resourceName1 = (String)row1.get(7);
/* 1121 */       String resourceid1 = (String)row1.get(6);
/* 1122 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1123 */       if (hseverity[j] == null)
/*      */       {
/* 1125 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1130 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1132 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1135 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1138 */         if (hsev > 0) {
/* 1139 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1140 */           monitorList.set(k, monitorList.get(j));
/* 1141 */           monitorList.set(j, t);
/* 1142 */           String temp1 = hseverity[k];
/* 1143 */           hseverity[k] = hseverity[j];
/* 1144 */           hseverity[j] = temp1;
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
/* 1156 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1157 */     boolean forInventory = false;
/* 1158 */     String trdisplay = "none";
/* 1159 */     String plusstyle = "inline";
/* 1160 */     String minusstyle = "none";
/* 1161 */     String haidTopLevel = "";
/* 1162 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1164 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1166 */         haidTopLevel = request.getParameter("haid");
/* 1167 */         forInventory = true;
/* 1168 */         trdisplay = "table-row;";
/* 1169 */         plusstyle = "none";
/* 1170 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1177 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1180 */     ArrayList listtoreturn = new ArrayList();
/* 1181 */     StringBuffer toreturn = new StringBuffer();
/* 1182 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1183 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1184 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1186 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1188 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1189 */       String childresid = (String)singlerow.get(0);
/* 1190 */       String childresname = (String)singlerow.get(1);
/* 1191 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1192 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1193 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1194 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1195 */       String unmanagestatus = (String)singlerow.get(5);
/* 1196 */       String actionstatus = (String)singlerow.get(6);
/* 1197 */       String linkclass = "monitorgp-links";
/* 1198 */       String titleforres = childresname;
/* 1199 */       String titilechildresname = childresname;
/* 1200 */       String childimg = "/images/trcont.png";
/* 1201 */       String flag = "enable";
/* 1202 */       String dcstarted = (String)singlerow.get(8);
/* 1203 */       String configMonitor = "";
/* 1204 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1205 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1207 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1209 */       if (singlerow.get(7) != null)
/*      */       {
/* 1211 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1213 */       String haiGroupType = "0";
/* 1214 */       if ("HAI".equals(childtype))
/*      */       {
/* 1216 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1218 */       childimg = "/images/trend.png";
/* 1219 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1220 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1221 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1223 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1225 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1227 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1228 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1231 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1233 */         linkclass = "disabledtext";
/* 1234 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1236 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1237 */       String availmouseover = "";
/* 1238 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1240 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1242 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1243 */       String healthmouseover = "";
/* 1244 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1246 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1249 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1250 */       int spacing = 0;
/* 1251 */       if (level >= 1)
/*      */       {
/* 1253 */         spacing = 40 * level;
/*      */       }
/* 1255 */       if (childtype.equals("HAI"))
/*      */       {
/* 1257 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1258 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1259 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1261 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1262 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1263 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1264 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1265 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1266 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1267 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1268 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1269 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1270 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1271 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1273 */         if (!forInventory)
/*      */         {
/* 1275 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1278 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1280 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1282 */           actions = editlink + actions;
/*      */         }
/* 1284 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1286 */           actions = actions + associatelink;
/*      */         }
/* 1288 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1289 */         String arrowimg = "";
/* 1290 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1292 */           actions = "";
/* 1293 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1294 */           checkbox = "";
/* 1295 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1297 */         if (isIt360)
/*      */         {
/* 1299 */           actionimg = "";
/* 1300 */           actions = "";
/* 1301 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1302 */           checkbox = "";
/*      */         }
/*      */         
/* 1305 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1307 */           actions = "";
/*      */         }
/* 1309 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1311 */           checkbox = "";
/*      */         }
/*      */         
/* 1314 */         String resourcelink = "";
/*      */         
/* 1316 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1318 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1322 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1325 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1331 */         if (!isIt360)
/*      */         {
/* 1333 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1337 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1340 */         toreturn.append("</tr>");
/* 1341 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1343 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1344 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1348 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1349 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1352 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1356 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1358 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1360 */             toreturn.append(assocMessage);
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1364 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1370 */         String resourcelink = null;
/* 1371 */         boolean hideEditLink = false;
/* 1372 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1374 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1375 */           hideEditLink = true;
/* 1376 */           if (isIt360)
/*      */           {
/* 1378 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1382 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1384 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1386 */           hideEditLink = true;
/* 1387 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1388 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1393 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1396 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1397 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1398 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1399 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1400 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1401 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1402 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1403 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1404 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1405 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1406 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1407 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1408 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1410 */         if (hideEditLink)
/*      */         {
/* 1412 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1414 */         if (!forInventory)
/*      */         {
/* 1416 */           removefromgroup = "";
/*      */         }
/* 1418 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1419 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1420 */           actions = actions + configcustomfields;
/*      */         }
/* 1422 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1424 */           actions = editlink + actions;
/*      */         }
/* 1426 */         String managedLink = "";
/* 1427 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1429 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1430 */           actions = "";
/* 1431 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1432 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1435 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1437 */           checkbox = "";
/*      */         }
/*      */         
/* 1440 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1442 */           actions = "";
/*      */         }
/* 1444 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1447 */         if (isIt360)
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1455 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1456 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1457 */         if (!isIt360)
/*      */         {
/* 1459 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1463 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1465 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1468 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1475 */       StringBuilder toreturn = new StringBuilder();
/* 1476 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1477 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1478 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1479 */       String title = "";
/* 1480 */       message = EnterpriseUtil.decodeString(message);
/* 1481 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1482 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1483 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1485 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1487 */       else if ("5".equals(severity))
/*      */       {
/* 1489 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1493 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1495 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1496 */       toreturn.append(v);
/*      */       
/* 1498 */       toreturn.append(link);
/* 1499 */       if (severity == null)
/*      */       {
/* 1501 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1503 */       else if (severity.equals("5"))
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1507 */       else if (severity.equals("4"))
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       else if (severity.equals("1"))
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1518 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1520 */       toreturn.append("</a>");
/* 1521 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1525 */       ex.printStackTrace();
/*      */     }
/* 1527 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1534 */       StringBuilder toreturn = new StringBuilder();
/* 1535 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1536 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1537 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1538 */       if (message == null)
/*      */       {
/* 1540 */         message = "";
/*      */       }
/*      */       
/* 1543 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1544 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1546 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1547 */       toreturn.append(v);
/*      */       
/* 1549 */       toreturn.append(link);
/*      */       
/* 1551 */       if (severity == null)
/*      */       {
/* 1553 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1555 */       else if (severity.equals("5"))
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1559 */       else if (severity.equals("1"))
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1566 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1568 */       toreturn.append("</a>");
/* 1569 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1575 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1578 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1579 */     if (invokeActions != null) {
/* 1580 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1581 */       while (iterator.hasNext()) {
/* 1582 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1583 */         if (actionmap.containsKey(actionid)) {
/* 1584 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1589 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1593 */     String actionLink = "";
/* 1594 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1595 */     String query = "";
/* 1596 */     ResultSet rs = null;
/* 1597 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1598 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1599 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1600 */       actionLink = "method=" + methodName;
/*      */     }
/* 1602 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1603 */       actionLink = methodName;
/*      */     }
/* 1605 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1606 */     Iterator itr = methodarglist.iterator();
/* 1607 */     boolean isfirstparam = true;
/* 1608 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1609 */     while (itr.hasNext()) {
/* 1610 */       HashMap argmap = (HashMap)itr.next();
/* 1611 */       String argtype = (String)argmap.get("TYPE");
/* 1612 */       String argname = (String)argmap.get("IDENTITY");
/* 1613 */       String paramname = (String)argmap.get("PARAMETER");
/* 1614 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1615 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1616 */         isfirstparam = false;
/* 1617 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1619 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1623 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1627 */         actionLink = actionLink + "&";
/*      */       }
/* 1629 */       String paramValue = null;
/* 1630 */       String tempargname = argname;
/* 1631 */       if (commonValues.getProperty(tempargname) != null) {
/* 1632 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1635 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1636 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1637 */           if (dbType.equals("mysql")) {
/* 1638 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1641 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1643 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1645 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1646 */             if (rs.next()) {
/* 1647 */               paramValue = rs.getString("VALUE");
/* 1648 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1652 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1656 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1659 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1664 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1665 */           paramValue = rowId;
/*      */         }
/* 1667 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1668 */           paramValue = managedObjectName;
/*      */         }
/* 1670 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1671 */           paramValue = resID;
/*      */         }
/* 1673 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1674 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1677 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1679 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1680 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1681 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1683 */     return actionLink;
/*      */   }
/*      */   
/* 1686 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1687 */     String dependentAttribute = null;
/* 1688 */     String align = "left";
/*      */     
/* 1690 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1691 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1692 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1693 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1694 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1695 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1696 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1697 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1698 */       align = "center";
/*      */     }
/*      */     
/* 1701 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1702 */     String actualdata = "";
/*      */     
/* 1704 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1705 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1706 */         actualdata = availValue;
/*      */       }
/* 1708 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1709 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1713 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1714 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1717 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1723 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1724 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1725 */       toreturn.append("<table>");
/* 1726 */       toreturn.append("<tr>");
/* 1727 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1728 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1729 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1730 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1731 */         String toolTip = "";
/* 1732 */         String hideClass = "";
/* 1733 */         String textStyle = "";
/* 1734 */         boolean isreferenced = true;
/* 1735 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1736 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1737 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1738 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1740 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1741 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1742 */           while (valueList.hasMoreTokens()) {
/* 1743 */             String dependentVal = valueList.nextToken();
/* 1744 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1745 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1746 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1748 */               toolTip = "";
/* 1749 */               hideClass = "";
/* 1750 */               isreferenced = false;
/* 1751 */               textStyle = "disabledtext";
/* 1752 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1756 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1757 */           toolTip = "";
/* 1758 */           hideClass = "";
/* 1759 */           isreferenced = false;
/* 1760 */           textStyle = "disabledtext";
/* 1761 */           if (dependentImageMap != null) {
/* 1762 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1763 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1766 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1770 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1771 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1772 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1773 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1774 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1775 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1777 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1778 */           if (isreferenced) {
/* 1779 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1783 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1784 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1785 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1786 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1787 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1788 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1790 */           toreturn.append("</span>");
/* 1791 */           toreturn.append("</a>");
/* 1792 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1795 */       toreturn.append("</tr>");
/* 1796 */       toreturn.append("</table>");
/* 1797 */       toreturn.append("</td>");
/*      */     } else {
/* 1799 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1802 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1806 */     String colTime = null;
/* 1807 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1808 */     if ((rows != null) && (rows.size() > 0)) {
/* 1809 */       Iterator<String> itr = rows.iterator();
/* 1810 */       String maxColQuery = "";
/* 1811 */       for (;;) { if (itr.hasNext()) {
/* 1812 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1813 */           ResultSet maxCol = null;
/*      */           try {
/* 1815 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1816 */             while (maxCol.next()) {
/* 1817 */               if (colTime == null) {
/* 1818 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1821 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1830 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1832 */               if (maxCol != null)
/* 1833 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1835 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1830 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1832 */               if (maxCol != null)
/* 1833 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1835 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1840 */     return colTime;
/*      */   }
/*      */   
/* 1843 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1844 */     tablename = null;
/* 1845 */     ResultSet rsTable = null;
/* 1846 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1848 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1849 */       while (rsTable.next()) {
/* 1850 */         tablename = rsTable.getString("DATATABLE");
/* 1851 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1852 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1865 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1856 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1859 */         if (rsTable != null)
/* 1860 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1862 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1868 */     String argsList = "";
/* 1869 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1871 */       if (showArgsMap.get(row) != null) {
/* 1872 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1873 */         if (showArgslist != null) {
/* 1874 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1875 */             if (argsList.trim().equals("")) {
/* 1876 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1879 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1886 */       e.printStackTrace();
/* 1887 */       return "";
/*      */     }
/* 1889 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1894 */     String argsList = "";
/* 1895 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1898 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1900 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1901 */         if (hideArgsList != null)
/*      */         {
/* 1903 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1905 */             if (argsList.trim().equals(""))
/*      */             {
/* 1907 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1911 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1919 */       ex.printStackTrace();
/*      */     }
/* 1921 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1925 */     StringBuilder toreturn = new StringBuilder();
/* 1926 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1933 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1934 */       Iterator itr = tActionList.iterator();
/* 1935 */       while (itr.hasNext()) {
/* 1936 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1937 */         String confirmmsg = "";
/* 1938 */         String link = "";
/* 1939 */         String isJSP = "NO";
/* 1940 */         HashMap tactionMap = (HashMap)itr.next();
/* 1941 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1942 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1943 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1944 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1945 */           (actionmap.containsKey(actionId))) {
/* 1946 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1947 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1948 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1949 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1950 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1952 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1958 */           if (isTableAction) {
/* 1959 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1962 */             tableName = "Link";
/* 1963 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1964 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1965 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1966 */             toreturn.append("</a></td>");
/*      */           }
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1977 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1983 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1985 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1986 */       Properties prop = (Properties)node.getUserObject();
/* 1987 */       String mgID = prop.getProperty("label");
/* 1988 */       String mgName = prop.getProperty("value");
/* 1989 */       String isParent = prop.getProperty("isParent");
/* 1990 */       int mgIDint = Integer.parseInt(mgID);
/* 1991 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1993 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1995 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1996 */       if (node.getChildCount() > 0)
/*      */       {
/* 1998 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2000 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2002 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2004 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2008 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2013 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2015 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2017 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2019 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2023 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2026 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2027 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2029 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2033 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2035 */       if (node.getChildCount() > 0)
/*      */       {
/* 2037 */         builder.append("<UL>");
/* 2038 */         printMGTree(node, builder);
/* 2039 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2044 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2045 */     StringBuffer toReturn = new StringBuffer();
/* 2046 */     String table = "-";
/*      */     try {
/* 2048 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2049 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2050 */       float total = 0.0F;
/* 2051 */       while (it.hasNext()) {
/* 2052 */         String attName = (String)it.next();
/* 2053 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2054 */         boolean roundOffData = false;
/* 2055 */         if ((data != null) && (!data.equals(""))) {
/* 2056 */           if (data.indexOf(",") != -1) {
/* 2057 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2060 */             float value = Float.parseFloat(data);
/* 2061 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2064 */             total += value;
/* 2065 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2068 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2073 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2074 */       while (attVsWidthList.hasNext()) {
/* 2075 */         String attName = (String)attVsWidthList.next();
/* 2076 */         String data = (String)attVsWidthProps.get(attName);
/* 2077 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2078 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2079 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2080 */         String className = (String)graphDetails.get("ClassName");
/* 2081 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2082 */         if (percentage < 1.0F)
/*      */         {
/* 2084 */           data = percentage + "";
/*      */         }
/* 2086 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2088 */       if (toReturn.length() > 0) {
/* 2089 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2093 */       e.printStackTrace();
/*      */     }
/* 2095 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2101 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2102 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2103 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2104 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2105 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2106 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2107 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2108 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2109 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2112 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2113 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2114 */       splitvalues[0] = multiplecondition.toString();
/* 2115 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2118 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2123 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2124 */     if (thresholdType != 3) {
/* 2125 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2126 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2127 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2128 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2129 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2130 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2132 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2133 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2134 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2135 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2136 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2137 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2139 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2140 */     if (updateSelected != null) {
/* 2141 */       updateSelected[0] = "selected";
/*      */     }
/* 2143 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2148 */       StringBuffer toreturn = new StringBuffer("");
/* 2149 */       if (commaSeparatedMsgId != null) {
/* 2150 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2151 */         int count = 0;
/* 2152 */         while (msgids.hasMoreTokens()) {
/* 2153 */           String id = msgids.nextToken();
/* 2154 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2155 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2156 */           count++;
/* 2157 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2158 */             if (toreturn.length() == 0) {
/* 2159 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2161 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2162 */             if (!image.trim().equals("")) {
/* 2163 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2165 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2166 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2169 */         if (toreturn.length() > 0) {
/* 2170 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2174 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2177 */       e.printStackTrace(); }
/* 2178 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2184 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2190 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2191 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2205 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2209 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2216 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2220 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2223 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*      */   }
/*      */   
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response) throws IOException, javax.servlet.ServletException
/*      */   {
/*      */     ;
/*      */     ;
/* 2232 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2235 */     JspWriter out = null;
/* 2236 */     Object page = this;
/* 2237 */     JspWriter _jspx_out = null;
/* 2238 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2242 */       response.setContentType("text/html;charset=UTF-8");
/* 2243 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2245 */       _jspx_page_context = pageContext;
/* 2246 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2247 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2248 */       session = pageContext.getSession();
/* 2249 */       out = pageContext.getOut();
/* 2250 */       _jspx_out = out;
/*      */       
/* 2252 */       out.write(10);
/*      */       
/* 2254 */       Date date = new Date();
/* 2255 */       Calendar cal = Calendar.getInstance();
/* 2256 */       System.out.println("server starttime" + cal.getTime());
/* 2257 */       long start = System.currentTimeMillis();
/*      */       
/*      */ 
/* 2260 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2261 */       ManagedApplication mo = null;
/* 2262 */       synchronized (application) {
/* 2263 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2264 */         if (mo == null) {
/* 2265 */           mo = new ManagedApplication();
/* 2266 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2269 */       out.write(10);
/* 2270 */       HAAlertGraph graph = null;
/* 2271 */       graph = (HAAlertGraph)_jspx_page_context.getAttribute("graph", 1);
/* 2272 */       if (graph == null) {
/* 2273 */         graph = new HAAlertGraph();
/* 2274 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*      */       }
/* 2276 */       out.write(10);
/* 2277 */       out.write(10);
/* 2278 */       out.write(10);
/* 2279 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2281 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2285 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2287 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2289 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2291 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2292 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2293 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2294 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2297 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2298 */         String available = null;
/* 2299 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2300 */         out.write(10);
/*      */         
/* 2302 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2306 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2308 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2310 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2312 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2313 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2314 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2315 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2318 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2319 */           String unavailable = null;
/* 2320 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2321 */           out.write(10);
/*      */           
/* 2323 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2327 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2329 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2331 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2333 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2334 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2335 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2336 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2339 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2340 */             String unmanaged = null;
/* 2341 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2342 */             out.write(10);
/*      */             
/* 2344 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2348 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2350 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2352 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2354 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2355 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2356 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2357 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2360 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2361 */               String scheduled = null;
/* 2362 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2363 */               out.write(10);
/*      */               
/* 2365 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2369 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2371 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2373 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2375 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2376 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2377 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2378 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2381 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2382 */                 String critical = null;
/* 2383 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2384 */                 out.write(10);
/*      */                 
/* 2386 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2390 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2392 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2394 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2396 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2397 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2398 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2399 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2402 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2403 */                   String clear = null;
/* 2404 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2405 */                   out.write(10);
/*      */                   
/* 2407 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2411 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2413 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2415 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2417 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2418 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2419 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2420 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2423 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2424 */                     String warning = null;
/* 2425 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2426 */                     out.write(10);
/* 2427 */                     out.write(10);
/*      */                     
/* 2429 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2430 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2432 */                     out.write(10);
/* 2433 */                     out.write(10);
/* 2434 */                     out.write(10);
/* 2435 */                     out.write("\n\n\n\n\n\n\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2436 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2438 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<script type=\"text/javascript\" src=\"/template/appmanager.js\"></script>\n\n");
/*      */                     
/* 2440 */                     AMConnectionPool ME_cp = AMConnectionPool.getInstance();
/* 2441 */                     String viewname = request.getParameter("viewname");
/* 2442 */                     System.out.println("ViewName:" + viewname);
/* 2443 */                     if ((viewname == null) || (viewname.trim().equals("")))
/*      */                     {
/* 2445 */                       viewname = "DefaultView";
/*      */                     }
/*      */                     
/* 2448 */                     out.write("\n\t<title>Applications Manager -  ");
/* 2449 */                     out.print(viewname);
/* 2450 */                     out.write("</title>");
/* 2451 */                     out.write("\n\t<input type=hidden name=\"viewname\" value=");
/* 2452 */                     out.print(viewname);
/* 2453 */                     out.write("></input>\n\n    \t\t");
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2459 */                     String stateFile = "WebClientState.properties";
/* 2460 */                     String path = com.adventnet.nms.util.PureUtils.rootDir + "users/admin/" + stateFile;
/* 2461 */                     System.out.println("read state file" + path);
/* 2462 */                     File f = new File(path);
/* 2463 */                     if (f.exists())
/*      */                     {
/*      */ 
/* 2466 */                       System.out.println("File exists");
/* 2467 */                       Properties prop = new Properties();
/*      */                       try {
/* 2469 */                         FileInputStream inStream = new FileInputStream(f);
/* 2470 */                         prop.load(inStream);
/* 2471 */                         inStream.close();
/*      */                       } catch (IOException ex) {
/* 2473 */                         System.out.println("Exception" + ex);
/*      */                       } catch (Exception exe) {
/* 2475 */                         System.out.println("Exception" + exe);
/*      */                       }
/*      */                       
/* 2478 */                       if (prop.getProperty("customreloadperiod") != null)
/*      */                       {
/* 2480 */                         String reloadPeriodMins = prop.getProperty("customreloadperiod");
/* 2481 */                         response.setHeader("Refresh", reloadPeriodMins);
/*      */                       }
/*      */                       else
/*      */                       {
/* 2485 */                         response.setHeader("Refresh", "300");
/*      */                       }
/*      */                     }
/*      */                     else
/*      */                     {
/* 2490 */                       response.setHeader("Refresh", "300");
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 2495 */                     ResultSet viewID_Set = null;
/*      */                     
/* 2497 */                     String viewID_Set_query = "";
/* 2498 */                     String resid = "";
/* 2499 */                     String resTypeName = "";
/*      */                     
/* 2501 */                     int viewID = DBUtil.getViewID(viewname);
/*      */                     
/* 2503 */                     ManagedApplication mo1 = new ManagedApplication();
/*      */                     
/* 2505 */                     ArrayList list1 = mo1.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_STANDALONE_VIEW_MONITORGROUP_ASSOCIATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.TYPE=0 and  AM_STANDALONE_VIEW_MONITORGROUP_ASSOCIATION.MONITORGROUPID=AM_ManagedObject.RESOURCEID and AM_STANDALONE_VIEW_MONITORGROUP_ASSOCIATION.VIEWID=" + viewID + " order by RESOURCENAME");
/* 2506 */                     int monitorgrpCount = list1.size();
/* 2507 */                     pageContext.setAttribute("applications", list1);
/*      */                     
/* 2509 */                     ResultSet columnNameSet = null;
/* 2510 */                     ResultSet columnCountSet = null;
/*      */                     
/* 2512 */                     String columnDisplayName = "";
/* 2513 */                     String columnNameSet_query = "";
/*      */                     
/* 2515 */                     String columnCount_Query = "";
/* 2516 */                     int columnCount = 0;int columnWidth = 0;
/*      */                     
/*      */ 
/*      */ 
/*      */                     try
/*      */                     {
/* 2522 */                       columnCount_Query = "Select count(*) from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where VIEWID=" + viewID + "";
/* 2523 */                       columnCountSet = AMConnectionPool.executeQueryStmt(columnCount_Query);
/*      */                       
/* 2525 */                       while (columnCountSet.next())
/*      */                       {
/* 2527 */                         columnCount = Integer.parseInt(columnCountSet.getString(1));
/*      */                       }
/*      */                       
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2533 */                       e.printStackTrace();
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 2538 */                       AMConnectionPool.closeStatement(columnCountSet); return; } finally { AMConnectionPool.closeStatement(columnCountSet);
/*      */                     }
/*      */                     
/* 2541 */                     if (columnCount > 0)
/*      */                     {
/* 2543 */                       columnWidth = 80 / columnCount;
/*      */                     }
/*      */                     
/*      */ 
/* 2547 */                     System.out.println("column block AttributeID Forming starts");
/*      */                     
/* 2549 */                     ArrayList columnAttrIDList = new ArrayList();
/* 2550 */                     ArrayList columnAttrResTypeList = new ArrayList();
/* 2551 */                     ArrayList columnDisplayNameList = new ArrayList();
/* 2552 */                     ArrayList columnAttrResTypeQstrList = new ArrayList();
/* 2553 */                     String totalResType = "";
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     try
/*      */                     {
/* 2561 */                       columnNameSet_query = "Select COLUMNNAME from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where VIEWID=" + viewID + " order by COLUMNID";
/* 2562 */                       columnNameSet = AMConnectionPool.executeQueryStmt(columnNameSet_query);
/* 2563 */                       String attrIDQuery = "";
/* 2564 */                       ResultSet attrIDSet = null;
/* 2565 */                       String attrID = "";
/* 2566 */                       String attrID1 = "";
/* 2567 */                       String columnAttrResTypeName = "";
/* 2568 */                       String columnAttrResTypeName1 = "";
/* 2569 */                       String columnAttrResTypeNameQstr = "";
/* 2570 */                       ArrayList tempcolumnAttrResTypeNameList = new ArrayList();
/* 2571 */                       int scriptCheck = 0;
/* 2572 */                       int size1 = 0;
/*      */                       
/* 2574 */                       while (columnNameSet.next())
/*      */                       {
/* 2576 */                         columnDisplayName = columnNameSet.getString("COLUMNNAME");
/* 2577 */                         columnDisplayNameList.add(columnDisplayName);
/* 2578 */                         attrIDQuery = "";
/* 2579 */                         attrIDSet = null;
/* 2580 */                         attrID = "";
/* 2581 */                         attrID1 = "";
/* 2582 */                         columnAttrResTypeName = "";
/* 2583 */                         columnAttrResTypeName1 = "";
/* 2584 */                         columnAttrResTypeNameQstr = "";
/* 2585 */                         scriptCheck = 0;
/*      */                         
/*      */ 
/*      */                         try
/*      */                         {
/* 2590 */                           attrIDQuery = "select distinct vd.ATTRIBUTEID, mo.TYPE from AM_STANDALONE_VIEWCOLUMN_DETAILS vd, AM_STANDALONE_VIEWCOLUMN_ASSOCIATION va, AM_ManagedObject mo, AM_ATTRIBUTES att  where att.ATTRIBUTEID=vd.ATTRIBUTEID and mo.TYPE =att.RESOURCETYPE and vd.COLUMNID=va.COLUMNID and va.VIEWID=" + viewID + " and va.COLUMNNAME='" + columnDisplayName + "'";
/* 2591 */                           attrIDSet = AMConnectionPool.executeQueryStmt(attrIDQuery);
/* 2592 */                           tempcolumnAttrResTypeNameList.clear();
/* 2593 */                           while (attrIDSet.next())
/*      */                           {
/* 2595 */                             attrID1 = attrIDSet.getString("ATTRIBUTEID").trim();
/* 2596 */                             attrID = attrID + attrID1 + ",";
/*      */                             
/*      */ 
/*      */ 
/* 2600 */                             columnAttrResTypeName1 = attrIDSet.getString("TYPE").trim();
/*      */                             
/*      */ 
/* 2603 */                             if (!tempcolumnAttrResTypeNameList.contains(columnAttrResTypeName1))
/*      */                             {
/* 2605 */                               tempcolumnAttrResTypeNameList.add(columnAttrResTypeName1);
/*      */                             }
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/* 2612 */                           if (tempcolumnAttrResTypeNameList.size() == 0)
/*      */                           {
/* 2614 */                             tempcolumnAttrResTypeNameList.add("0");
/*      */                           }
/*      */                           
/* 2617 */                           size1 = tempcolumnAttrResTypeNameList.size();
/* 2618 */                           for (int p = 0; p < size1; p++)
/*      */                           {
/* 2620 */                             columnAttrResTypeName1 = (String)tempcolumnAttrResTypeNameList.get(p);
/* 2621 */                             columnAttrResTypeName = columnAttrResTypeName + columnAttrResTypeName1 + ",";
/* 2622 */                             columnAttrResTypeNameQstr = columnAttrResTypeNameQstr + "'" + columnAttrResTypeName1 + "'" + ",";
/*      */                           }
/*      */                           
/* 2625 */                           if ((attrID != null) && (!attrID.equals("")))
/*      */                           {
/* 2627 */                             attrID = attrID.substring(0, attrID.length() - 1);
/* 2628 */                             columnAttrIDList.add(attrID);
/*      */                           }
/*      */                           else
/*      */                           {
/* 2632 */                             columnAttrIDList.add("0");
/*      */                           }
/*      */                           
/*      */ 
/* 2636 */                           if ((columnAttrResTypeName != null) && (!columnAttrResTypeName.equals("")))
/*      */                           {
/* 2638 */                             columnAttrResTypeName = columnAttrResTypeName.substring(0, columnAttrResTypeName.length() - 1);
/* 2639 */                             columnAttrResTypeList.add(columnAttrResTypeName);
/*      */                           }
/*      */                           
/* 2642 */                           if ((columnAttrResTypeNameQstr != null) && (!columnAttrResTypeNameQstr.equals("")))
/*      */                           {
/* 2644 */                             columnAttrResTypeNameQstr = columnAttrResTypeNameQstr.substring(0, columnAttrResTypeNameQstr.length() - 1);
/* 2645 */                             columnAttrResTypeQstrList.add(columnAttrResTypeNameQstr);
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         catch (Exception e)
/*      */                         {
/* 2651 */                           e.printStackTrace();
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/* 2656 */                           AMConnectionPool.closeStatement(attrIDSet);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2690 */                           AMConnectionPool.closeStatement(columnNameSet); return;
/*      */                         }
/*      */                         finally {}
/*      */                       }
/* 2663 */                       int viewResTypeCount = columnAttrResTypeQstrList.size();
/* 2664 */                       String totalResType1 = "";
/* 2665 */                       if (viewResTypeCount > 0)
/*      */                       {
/* 2667 */                         for (int h = 0; h < viewResTypeCount; h++)
/*      */                         {
/* 2669 */                           totalResType1 = (String)columnAttrResTypeQstrList.get(h);
/* 2670 */                           totalResType = totalResType + totalResType1 + ",";
/*      */                         }
/* 2672 */                         if ((totalResType != null) && (!totalResType.equals("")))
/*      */                         {
/* 2674 */                           totalResType = totalResType.substring(0, totalResType.length() - 1);
/*      */                         }
/*      */                       }
/*      */                       else
/*      */                       {
/* 2679 */                         totalResType = "0";
/*      */                       }
/*      */                       
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2685 */                       e.printStackTrace();
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 2690 */                       AMConnectionPool.closeStatement(columnNameSet); return; } finally { AMConnectionPool.closeStatement(columnNameSet);
/*      */                     }
/*      */                     
/* 2693 */                     System.out.println("column AttributeID Forming block ends");
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2699 */                     ArrayList monitorGrpResIDList = new ArrayList();
/* 2700 */                     ArrayList monitorGrpResTypeNameList = new ArrayList();
/* 2701 */                     ArrayList MonitorGrpChildResTypeList = new ArrayList();
/* 2702 */                     ArrayList tempMonitorGrpChildResTypeList = new ArrayList();
/* 2703 */                     ArrayList tempMonitorGrpResTypeNameList = new ArrayList();
/*      */                     
/* 2705 */                     String tempChildResTypeName = "";
/* 2706 */                     String ME_query = "";
/* 2707 */                     ArrayList attribIDs = new ArrayList(2);
/* 2708 */                     attribIDs.add("17");
/* 2709 */                     attribIDs.add("18");
/* 2710 */                     ResultSet ME_set = null;
/* 2711 */                     String childResourceQuery = "";
/* 2712 */                     String childResourceTypeQuery = "";
/* 2713 */                     ResultSet childResourceTypeSet = null;
/* 2714 */                     String resid1 = "";
/* 2715 */                     String resTypeName1 = "";
/* 2716 */                     String restype = "";
/* 2717 */                     String temp_resid = "";
/* 2718 */                     int server_monitor_available = 0;
/* 2719 */                     ResultSet childResourceSet = null;
/* 2720 */                     String childresid = "";
/* 2721 */                     String childResTypeName1 = "";
/* 2722 */                     String childResTypeName = "";
/* 2723 */                     String tempresid = "";
/* 2724 */                     String childresid1 = "";
/* 2725 */                     int size2 = 0;
/* 2726 */                     int size3 = 0;
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2732 */                     out.write(10);
/* 2733 */                     out.write(10);
/* 2734 */                     out.write(9);
/*      */                     
/* 2736 */                     IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2737 */                     _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2738 */                     _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */                     
/* 2740 */                     _jspx_th_logic_005fiterate_005f0.setName("applications");
/*      */                     
/* 2742 */                     _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                     
/* 2744 */                     _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*      */                     
/* 2746 */                     _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/* 2747 */                     int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2748 */                     if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2749 */                       ArrayList row = null;
/* 2750 */                       Integer i = null;
/* 2751 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2752 */                         out = _jspx_page_context.pushBody();
/* 2753 */                         _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2754 */                         _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                       }
/* 2756 */                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2757 */                       i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                       for (;;) {
/* 2759 */                         out.write(10);
/*      */                         
/* 2761 */                         tempChildResTypeName = "";
/*      */                         
/* 2763 */                         tempMonitorGrpChildResTypeList.clear();
/* 2764 */                         System.out.println("MonitorGroup resourceID  Forming block starts");
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/* 2769 */                         server_monitor_available = 0;
/*      */                         
/* 2771 */                         temp_resid = (String)row.get(6);
/* 2772 */                         childResourceQuery = "";
/* 2773 */                         resid1 = "";
/* 2774 */                         resTypeName1 = "";
/*      */                         
/* 2776 */                         tempMonitorGrpResTypeNameList.clear();
/*      */                         
/*      */                         try
/*      */                         {
/*      */                           do
/*      */                           {
/* 2782 */                             ME_query = "SELECT AM_ManagedObject.RESOURCEID as RESID,AM_ManagedObject.TYPE as RES_TYPE FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID in (" + temp_resid + ")";
/* 2783 */                             ME_set = AMConnectionPool.executeQueryStmt(ME_query);
/*      */                             
/* 2785 */                             temp_resid = "";
/*      */                             
/* 2787 */                             while (ME_set.next())
/*      */                             {
/* 2789 */                               server_monitor_available = 1;
/* 2790 */                               restype = ME_set.getString("RES_TYPE").trim();
/* 2791 */                               resid1 = ME_set.getString("RESID").trim();
/*      */                               
/* 2793 */                               if (restype.equals("HAI"))
/*      */                               {
/* 2795 */                                 temp_resid = temp_resid + resid1 + ",";
/*      */                               }
/*      */                               else
/*      */                               {
/* 2799 */                                 resid = resid + resid1 + ",";
/* 2800 */                                 resTypeName1 = ME_set.getString("RES_TYPE").trim();
/*      */                                 
/* 2802 */                                 if (!tempMonitorGrpResTypeNameList.contains(resTypeName1))
/*      */                                 {
/* 2804 */                                   tempMonitorGrpResTypeNameList.add(resTypeName1);
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/* 2809 */                             if ((temp_resid != null) && (!temp_resid.trim().equals("")) && (temp_resid.substring(temp_resid.length() - 1).equals(",")))
/*      */                             {
/* 2811 */                               temp_resid = temp_resid.substring(0, temp_resid.length() - 1);
/*      */ 
/*      */                             }
/*      */                             
/*      */                           }
/* 2816 */                           while (!temp_resid.equals(""));
/* 2817 */                           if ((resid != null) && (!resid.trim().equals("")))
/*      */                           {
/* 2819 */                             tempresid = resid;
/* 2820 */                             if ((tempresid != null) && (!tempresid.equals("")))
/*      */                             {
/* 2822 */                               tempresid = tempresid.substring(0, tempresid.length() - 1);
/*      */                             }
/* 2824 */                             childResourceSet = null;
/* 2825 */                             childresid = "";
/* 2826 */                             childResTypeName1 = "";
/* 2827 */                             childResTypeName = "";
/*      */                             
/*      */ 
/*      */ 
/*      */                             try
/*      */                             {
/* 2833 */                               childResourceQuery = "SELECT AM_ManagedObject.RESOURCEID as RESID FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID in (" + tempresid + ") and AM_ManagedObject.TYPE in (" + totalResType + ")";
/* 2834 */                               childResourceSet = AMConnectionPool.executeQueryStmt(childResourceQuery);
/* 2835 */                               while (childResourceSet.next())
/*      */                               {
/*      */ 
/* 2838 */                                 childresid1 = childResourceSet.getString("RESID").trim();
/* 2839 */                                 childresid = childresid + childresid1 + ",";
/*      */                               }
/* 2841 */                               childResourceTypeQuery = "SELECT distinct AM_ManagedObject.TYPE  as RES_TYPE FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID in (" + tempresid + ") and AM_ManagedObject.TYPE in (" + totalResType + ")";
/* 2842 */                               childResourceTypeSet = AMConnectionPool.executeQueryStmt(childResourceTypeQuery);
/* 2843 */                               while (childResourceTypeSet.next())
/*      */                               {
/*      */ 
/* 2846 */                                 childResTypeName1 = childResourceTypeSet.getString("RES_TYPE").trim();
/* 2847 */                                 tempMonitorGrpResTypeNameList.add(childResTypeName1);
/* 2848 */                                 tempMonitorGrpChildResTypeList.add(childResTypeName1);
/*      */                               }
/*      */                               
/* 2851 */                               resid = resid + childresid;
/*      */                             }
/*      */                             catch (Exception e)
/*      */                             {
/* 2855 */                               e.printStackTrace();
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 2860 */                               AMConnectionPool.closeStatement(childResourceSet);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2928 */                               AMConnectionPool.closeStatement(ME_set); return;
/*      */                             }
/*      */                             finally {}
/*      */                           }
/* 2868 */                           size2 = tempMonitorGrpResTypeNameList.size();
/* 2869 */                           for (int q = 0; q < size2; q++)
/*      */                           {
/* 2871 */                             resTypeName1 = (String)tempMonitorGrpResTypeNameList.get(q);
/* 2872 */                             resTypeName = resTypeName + resTypeName1 + ",";
/*      */                           }
/*      */                           
/*      */ 
/* 2876 */                           if (tempMonitorGrpChildResTypeList.size() == 0)
/*      */                           {
/* 2878 */                             tempMonitorGrpChildResTypeList.add("0");
/*      */                           }
/*      */                           
/* 2881 */                           size3 = tempMonitorGrpChildResTypeList.size();
/*      */                           
/* 2883 */                           for (int r = 0; r < size3; r++)
/*      */                           {
/*      */ 
/* 2886 */                             String tempChildResTypeName1 = (String)tempMonitorGrpChildResTypeList.get(r);
/* 2887 */                             tempChildResTypeName = tempChildResTypeName + tempChildResTypeName1 + ",";
/*      */                           }
/*      */                           
/* 2890 */                           if ((tempChildResTypeName != null) && (!tempChildResTypeName.equals("")))
/*      */                           {
/* 2892 */                             tempChildResTypeName = tempChildResTypeName.substring(0, tempChildResTypeName.length() - 1);
/* 2893 */                             MonitorGrpChildResTypeList.add(tempChildResTypeName);
/*      */                           }
/* 2895 */                           if (server_monitor_available > 0)
/*      */                           {
/*      */ 
/* 2898 */                             if ((resid != null) && (!resid.equals("")))
/*      */                             {
/* 2900 */                               resid = resid.substring(0, resid.length() - 1);
/* 2901 */                               monitorGrpResIDList.add(resid);
/*      */                             }
/*      */                             
/* 2904 */                             if ((resTypeName != null) && (!resTypeName.equals("")))
/*      */                             {
/* 2906 */                               resTypeName = resTypeName.substring(0, resTypeName.length() - 1);
/* 2907 */                               monitorGrpResTypeNameList.add(resTypeName);
/*      */                             }
/*      */                             
/* 2910 */                             resid = "";
/* 2911 */                             resTypeName = "";
/*      */                           }
/* 2913 */                           else if (server_monitor_available == 0)
/*      */                           {
/* 2915 */                             monitorGrpResIDList.add("0");
/* 2916 */                             monitorGrpResTypeNameList.add("0");
/*      */                           }
/*      */                           
/*      */ 
/*      */                         }
/*      */                         catch (Exception e)
/*      */                         {
/* 2923 */                           e.printStackTrace();
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/* 2928 */                           AMConnectionPool.closeStatement(ME_set); return; } finally { AMConnectionPool.closeStatement(ME_set);
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/* 2934 */                         out.write(10);
/* 2935 */                         out.write(9);
/* 2936 */                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2937 */                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2938 */                         i = (Integer)_jspx_page_context.findAttribute("i");
/* 2939 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2942 */                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2943 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2946 */                     if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2947 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*      */                     }
/*      */                     else {
/* 2950 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2951 */                       out.write(10);
/* 2952 */                       out.write(10);
/*      */                       
/* 2954 */                       System.out.println("MonitorGroup resourceID Forming block ends");
/*      */                       
/*      */ 
/*      */ 
/* 2958 */                       String totalResIDs = "";
/* 2959 */                       int viewMonitorgrpCount = monitorGrpResIDList.size();
/* 2960 */                       if (viewMonitorgrpCount > 0)
/*      */                       {
/* 2962 */                         for (int i = 0; i < viewMonitorgrpCount; i++)
/*      */                         {
/* 2964 */                           String totalResIDs1 = (String)monitorGrpResIDList.get(i);
/* 2965 */                           totalResIDs = totalResIDs + totalResIDs1 + ",";
/*      */                         }
/* 2967 */                         if ((totalResIDs != null) && (!totalResIDs.equals("")))
/*      */                         {
/* 2969 */                           totalResIDs = totalResIDs.substring(0, totalResIDs.length() - 1);
/*      */                         }
/*      */                       }
/*      */                       
/* 2973 */                       String totalAttrIDs = "";
/* 2974 */                       int viewAttrCount = columnAttrIDList.size();
/* 2975 */                       if (viewAttrCount > 0)
/*      */                       {
/* 2977 */                         for (int i = 0; i < viewAttrCount; i++)
/*      */                         {
/* 2979 */                           String totalAttrIDs1 = (String)columnAttrIDList.get(i);
/* 2980 */                           totalAttrIDs = totalAttrIDs + totalAttrIDs1 + ",";
/*      */                         }
/* 2982 */                         if ((totalAttrIDs != null) && (!totalAttrIDs.equals("")))
/*      */                         {
/* 2984 */                           totalAttrIDs = totalAttrIDs.substring(0, totalAttrIDs.length() - 1);
/*      */                         }
/*      */                       }
/*      */                       ArrayList list;
/* 2988 */                       if (!EnterpriseUtil.isAdminServer())
/*      */                       {
/*      */ 
/* 2991 */                         list = mo.getRows("select AM_ManagedObject.RESOURCENAME from AM_ManagedObject,Network,IpAddress where Network.NAME=IpAddress.PARENTNET and AM_ManagedObject.RESOURCENAME=IpAddress.PARENTNET group by RESOURCENAME");
/*      */                       }
/*      */                       
/* 2994 */                       out.write(10);
/* 2995 */                       out.write(10);
/* 2996 */                       Properties applications = null;
/* 2997 */                       synchronized (application) {
/* 2998 */                         applications = (Properties)_jspx_page_context.getAttribute("applications", 4);
/* 2999 */                         if (applications == null) {
/* 3000 */                           applications = new Properties();
/* 3001 */                           _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                         }
/*      */                       }
/* 3004 */                       out.write("\n\n\n<div id=\"dhtmltooltip\"></div>\n<div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\n<div id=\"GHOST\" style=\"background-color:transparent;position:absolute;cursor:default;cursor:default;z-index:999;visibility:hidden;filter:alpha(opacity=50);-moz-opacity:0.5;opacity: 0.5;\" class=\"bodyText\"></div>\n<table width=\"99%\">\n    <tr>\n\t\t<td height=\"28\" align=\"right\">\n\t\t\t<!--a href=\"javascript:void(0)\" onclick=\"window.open('/jsp/SelectMonitorGroupList.jsp?viewname=");
/* 3005 */                       out.print(viewname);
/* 3006 */                       out.write("','','menubar=no,scrollbars=yes,status=no,toolbar=no,resizable=yes,width=400,height=400,screenX=300,screenY=150')\">Select Application Groups</a-->\n            <!--Create New View -->\n            <a title=\"");
/* 3007 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                         return;
/* 3009 */                       out.write("\" href=\"/jsp/ConfigureViews.jsp?selectedTab=divMonitorGroup&isCreateView=true&viewname=NewViewName\" ><span><b><u>");
/* 3010 */                       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */                         return;
/* 3012 */                       out.write("</u></b></span></a>");
/* 3013 */                       out.write("\n\t\t&nbsp;\n\t\t\t<!--a href=\"javascript:void(0)\" onclick=\"window.open('/jsp/AvailableViews.jsp','','menubar=no,scrollbars=yes,status=no,toolbar=no,resizable=yes,width=400,height=400,screenX=300,screenY=150')\">Available Views</a-->\n            <a title=\"");
/* 3014 */                       if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */                         return;
/* 3016 */                       out.write("\" href=\"javascript:void(0)\" onclick=\"window.open('/jsp/ShowAvailableViews.jsp','','menubar=no,scrollbars=yes,status=no,toolbar=no,resizable=yes,width=400,height=400,screenX=300,screenY=150')\"><span><b><u>");
/* 3017 */                       if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */                         return;
/* 3019 */                       out.write("</u></b></span></a> ");
/* 3020 */                       out.write("\n\n\t\t</td>\n    </tr>\n</table>\n\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=center>\n\t<tr>\n\t    <td class=\"tableheadingbborder\">\n            <!--View Name -->\n\t\t\t");
/* 3021 */                       if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */                         return;
/* 3023 */                       out.print(viewname);
/* 3024 */                       out.write("\n\t\t</td>\n\t\t<!--td height=\"28\" class=\"tableheading\" align=right> ");
/* 3025 */                       out.write("\n\t\t\t<!--a href=\"javascript:void(0)\" onclick=\"window.open('/jsp/SelectMonitorGroupList.jsp?viewname=");
/* 3026 */                       out.print(viewname);
/* 3027 */                       out.write("','','menubar=no,scrollbars=yes,status=no,toolbar=no,resizable=yes,width=400,height=400,screenX=300,screenY=150')\">Select Application Groups</a-->\n            <!--Create New View -->\n            <!--a title=\"");
/* 3028 */                       if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */                         return;
/* 3030 */                       out.write("\" href=\"/jsp/ConfigureViews.jsp?selectedTab=divMonitorGroup&isCreateView=true&viewname=NewViewName\" ><span style=\"color:#425B79\">");
/* 3031 */                       if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */                         return;
/* 3033 */                       out.write("</span></a> ");
/* 3034 */                       out.write("\n\t\t&nbsp;\n\t\t\t<!--a href=\"javascript:void(0)\" onclick=\"window.open('/jsp/AvailableViews.jsp','','menubar=no,scrollbars=yes,status=no,toolbar=no,resizable=yes,width=400,height=400,screenX=300,screenY=150')\">Available Views</a--> <!--No I18N -->\n            <!--a title=\"");
/* 3035 */                       if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */                         return;
/* 3037 */                       out.write("\" href=\"javascript:void(0)\" onclick=\"window.open('/jsp/showAvailableViews.jsp','','menubar=no,scrollbars=yes,status=no,toolbar=no,resizable=yes,width=400,height=400,screenX=300,screenY=150')\"><span style=\"color:#425B79\">");
/* 3038 */                       if (_jspx_meth_fmt_005fmessage_005f8(_jspx_page_context))
/*      */                         return;
/* 3040 */                       out.write("</span></a> ");
/* 3041 */                       out.write("\n\n\t\t</td-->\n    </tr>\n\n\t<tr>\n\t\t<td colspan=2>\n");
/* 3042 */                       int monitorGrpCount = 0;
/*      */                       
/* 3044 */                       if (columnCount > 0)
/*      */                       {
/*      */ 
/* 3047 */                         ArrayList alertList = mo.getRows("select SOURCE, CATEGORY, AM_ATTRIBUTES.DISPLAYNAME, SEVERITY, MMESSAGE  from AM_ATTRIBUTES, Alert, AM_ManagedObject  where  AM_ATTRIBUTES.ATTRIBUTEID in (" + totalAttrIDs + ") and Alert.CATEGORY=AM_ATTRIBUTES.ATTRIBUTEID and Alert.SOURCE=AM_ManagedObject.RESOURCEID and Alert.SEVERITY in (1,4) and AM_ManagedObject.RESOURCEID in (" + totalResIDs + ")order by AM_ATTRIBUTES.ATTRIBUTE");
/*      */                         
/*      */ 
/* 3050 */                         out.write(10);
/*      */                         
/* 3052 */                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3053 */                         _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3054 */                         _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                         
/* 3056 */                         _jspx_th_logic_005fnotEmpty_005f0.setName("applications");
/* 3057 */                         int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3058 */                         if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                           for (;;) {
/* 3060 */                             out.write("\n\t\t\t\t<table width=\"100%\" id=\"monitorGroupTable\" cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" >\n\t\t\t\t<tr>\n                <!--Monitor Groups -->\n\t\t\t\t\t<td width=\"20%\" height=\"28\" style=\"font-size: 10pt;\" class=\"columnheading\">");
/* 3061 */                             if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                               return;
/* 3063 */                             out.write("</td> ");
/* 3064 */                             out.write("\n\t\t\t\t\t<!--td width=\"10%\" align=\"center\" height=\"28\"  class=\"columnheading\">Health(Based on CPU/Memory)</td-->\n                    ");
/*      */                             
/* 3066 */                             ResultSet columnDetailSet = null;
/* 3067 */                             int columnListCount = columnDisplayNameList.size();
/*      */                             
/*      */                             try
/*      */                             {
/* 3071 */                               String resourceName = "";
/* 3072 */                               String attributeName = "";
/* 3073 */                               String columnDetails = "";
/* 3074 */                               String columnDetailQuery = "";
/* 3075 */                               for (int i = 0; i < columnListCount; i++)
/*      */                               {
/* 3077 */                                 columnDisplayName = (String)columnDisplayNameList.get(i);
/* 3078 */                                 columnDetails = "";
/*      */                                 
/* 3080 */                                 columnDetailQuery = "select distinct RESOURCETYPE,ATTRIBUTE from AM_STANDALONE_VIEWCOLUMN_DETAILS vcd, AM_STANDALONE_VIEWCOLUMN_ASSOCIATION vca where vca.COLUMNID=vcd.COLUMNID and vca.VIEWID=" + viewID + " and vca.columnname='" + columnDisplayName + "'";
/* 3081 */                                 columnDetailSet = AMConnectionPool.executeQueryStmt(columnDetailQuery);
/*      */                                 
/* 3083 */                                 while (columnDetailSet.next())
/*      */                                 {
/* 3085 */                                   resourceName = columnDetailSet.getString("RESOURCETYPE");
/* 3086 */                                   attributeName = columnDetailSet.getString("ATTRIBUTE");
/* 3087 */                                   columnDetails = columnDetails + "<BR>" + resourceName + " -- " + attributeName;
/* 3088 */                                   columnDetails = columnDetails.trim();
/*      */                                 }
/*      */                                 
/* 3091 */                                 out.write("\n                                <!-- display column header  -->\n                               <td width=\"");
/* 3092 */                                 out.print(columnWidth);
/* 3093 */                                 out.write("%\" align=\"center\" class=\"columnheading\" >\n                                    <a class=\"columnheading\" style=\"font-size: 10pt;\"  onmouseover=\"ddrivetip(this,event,'");
/* 3094 */                                 out.print(columnDetails);
/* 3095 */                                 out.write("',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\">");
/* 3096 */                                 out.print(columnDisplayName);
/* 3097 */                                 out.write("</a>\n                               </td>\n                      ");
/*      */                               }
/*      */                             }
/*      */                             catch (Exception e)
/*      */                             {
/* 3102 */                               e.printStackTrace();
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 3107 */                               AMConnectionPool.closeStatement(columnDetailSet); return; } finally { AMConnectionPool.closeStatement(columnDetailSet);
/*      */                             }
/* 3109 */                             System.out.println("alerts showing block starts");
/*      */                             
/*      */ 
/*      */ 
/* 3113 */                             int count_cric_messages = 0;
/* 3114 */                             int count_warn_messages = 0;
/* 3115 */                             String mouse_over_disp_message = "";
/* 3116 */                             int priority = 0;
/* 3117 */                             String severity = null;
/* 3118 */                             String Display_name = "";
/* 3119 */                             String deatil_message = "";
/* 3120 */                             String resourceID = "";
/* 3121 */                             String attributeID = "";
/* 3122 */                             String deatil_Critical_message1 = "<b>Critical</b><BR>";
/* 3123 */                             String deatil_Warning_message1 = "<b>Warning</b><BR>";
/* 3124 */                             String attrIDString = "";
/* 3125 */                             String columnResTypeNameString = "";
/* 3126 */                             boolean criticalAlert = false;
/* 3127 */                             boolean warningAlert = false;
/* 3128 */                             boolean clearAlert = false;
/* 3129 */                             boolean attributeMatch = false;
/* 3130 */                             int singleColumnResTypeCount = 0;
/* 3131 */                             String columnRestypeName = "";
/* 3132 */                             int alertCount = 0;
/* 3133 */                             String tempSource = "";
/* 3134 */                             String tempCategory = "";
/* 3135 */                             String ME_health_msg = "";
/* 3136 */                             String bgcolor = null;
/*      */                             
/*      */ 
/* 3139 */                             out.write("\n\t\t\t\t\t<!--td width=\"45%\"  class=\"columnheading\">Root Cause</td-->\n                    <!--here we have insert solumn name -->\n\t\t\t\t</tr>\n");
/*      */                             
/* 3141 */                             IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3142 */                             _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 3143 */                             _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                             
/* 3145 */                             _jspx_th_logic_005fiterate_005f1.setName("applications");
/*      */                             
/* 3147 */                             _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                             
/* 3149 */                             _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/*      */                             
/* 3151 */                             _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/* 3152 */                             int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 3153 */                             if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 3154 */                               ArrayList row = null;
/* 3155 */                               Integer i = null;
/* 3156 */                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3157 */                                 out = _jspx_page_context.pushBody();
/* 3158 */                                 _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 3159 */                                 _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                               }
/* 3161 */                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3162 */                               i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                               for (;;) {
/* 3164 */                                 out.write("\n\t\t</td> \n\t</tr>\n");
/*      */                                 
/* 3166 */                                 bgcolor = null;
/* 3167 */                                 if (i.intValue() % 2 == 0)
/*      */                                 {
/* 3169 */                                   bgcolor = "class=\"whitegrayborder\"";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3173 */                                   bgcolor = "class=\"yellowgrayborder\"";
/*      */                                 }
/*      */                                 
/* 3176 */                                 out.write("\n\t<tr>\n");
/*      */                                 
/* 3178 */                                 int resIdTOCheck = -1;
/*      */                                 try
/*      */                                 {
/* 3181 */                                   resIdTOCheck = Integer.parseInt((String)row.get(6));
/*      */                                 }
/*      */                                 catch (Exception e) {}
/*      */                                 
/* 3185 */                                 if ((EnterpriseUtil.isAdminServer()) && (resIdTOCheck > com.adventnet.appmanager.server.framework.comm.Constants.RANGE))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/* 3190 */                                   out.write("\n\t\t<td width=\"20%\" ");
/* 3191 */                                   out.print(bgcolor);
/* 3192 */                                   out.write(" title=\"");
/* 3193 */                                   out.print(row.get(0));
/* 3194 */                                   out.write("\" >\n\t\t\t");
/* 3195 */                                   out.print(row.get(0));
/* 3196 */                                   out.write("\n   \n\t\t</td>\n");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3202 */                                   out.write("\n\t\t<td width=\"20%\" align=\"left\" ");
/* 3203 */                                   out.print(bgcolor);
/* 3204 */                                   out.write(" class=\"ResourceName\">\n\t\t\t<!--a href=\"javascript:void(0)\" onclick=\"window.open('/jsp/reports/ServerCPUMemoryReport.jsp?HAID=");
/* 3205 */                                   out.print(row.get(6));
/* 3206 */                                   out.write("&ATTNAME=CPU Utilization','','menubar=no,scrollbars=yes,status=no,toolbar=no,resizable=yes')\" class=\"ResourceName\">");
/* 3207 */                                   out.print(row.get(0));
/* 3208 */                                   out.write("</a-->\n            <!--a href=\"javascript:void(0)\" class=\"ResourceName\"><b>");
/* 3209 */                                   out.print(row.get(0));
/* 3210 */                                   out.write("<b></a-->\n            ");
/* 3211 */                                   out.print(row.get(0));
/* 3212 */                                   out.write("\n\t\t</td> \n\n");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/* 3218 */                                 resid = (String)monitorGrpResIDList.get(monitorGrpCount);
/* 3219 */                                 String[] a = resid.split(",");
/* 3220 */                                 List<String> singleMonitorlist = new ArrayList(Arrays.asList(a));
/*      */                                 
/* 3222 */                                 resTypeName = (String)monitorGrpResTypeNameList.get(monitorGrpCount);
/* 3223 */                                 String[] res = resTypeName.split(",");
/* 3224 */                                 List<String> singleMonitorGrpResTypelist = new ArrayList(Arrays.asList(res));
/*      */                                 
/* 3226 */                                 tempChildResTypeName = (String)MonitorGrpChildResTypeList.get(monitorGrpCount);
/* 3227 */                                 String[] childRes = tempChildResTypeName.split(",");
/* 3228 */                                 List<String> singleChildMonitorGrpResTypelist = new ArrayList(Arrays.asList(childRes));
/*      */                                 
/*      */ 
/* 3231 */                                 monitorGrpCount++;
/* 3232 */                                 count_cric_messages = 0;
/* 3233 */                                 count_warn_messages = 0;
/* 3234 */                                 mouse_over_disp_message = "";
/* 3235 */                                 priority = 0;
/* 3236 */                                 severity = null;
/* 3237 */                                 Display_name = "";
/* 3238 */                                 deatil_message = "";
/* 3239 */                                 resourceID = "";
/* 3240 */                                 attributeID = "";
/* 3241 */                                 deatil_Critical_message1 = "<b>Critical</b><BR>";
/* 3242 */                                 deatil_Warning_message1 = "<b>Warning</b><BR>";
/*      */                                 
/*      */ 
/* 3245 */                                 for (int j = 0; j < columnCount; ???++)
/*      */                                 {
/*      */ 
/* 3248 */                                   attrIDString = (String)columnAttrIDList.get(j);
/* 3249 */                                   String[] tempB = attrIDString.split(",");
/* 3250 */                                   List<String> singleColumnlist = new ArrayList(Arrays.asList(tempB));
/*      */                                   
/* 3252 */                                   columnResTypeNameString = (String)columnAttrResTypeList.get(j);
/* 3253 */                                   String[] resB = columnResTypeNameString.split(",");
/* 3254 */                                   List<String> singleColumnResTypelist = new ArrayList(Arrays.asList(resB));
/*      */                                   
/* 3256 */                                   count_cric_messages = 0;
/* 3257 */                                   count_warn_messages = 0;
/* 3258 */                                   mouse_over_disp_message = "";
/* 3259 */                                   priority = 0;
/* 3260 */                                   severity = null;
/* 3261 */                                   Display_name = "";
/* 3262 */                                   deatil_message = "";
/*      */                                   
/* 3264 */                                   criticalAlert = false;
/* 3265 */                                   warningAlert = false;
/* 3266 */                                   clearAlert = false;
/* 3267 */                                   attributeMatch = false;
/*      */                                   
/* 3269 */                                   deatil_Critical_message1 = "<b>Critical</b><BR>";
/* 3270 */                                   deatil_Warning_message1 = "<b>Warning</b><BR>";
/*      */                                   try
/*      */                                   {
/* 3273 */                                     singleColumnResTypeCount = singleColumnResTypelist.size();
/*      */                                     
/* 3275 */                                     for (int n = 0; n < singleColumnResTypeCount; ???++)
/*      */                                     {
/* 3277 */                                       columnRestypeName = (String)singleColumnResTypelist.get(n);
/*      */                                       
/* 3279 */                                       if (singleMonitorGrpResTypelist.contains(columnRestypeName))
/*      */                                       {
/* 3281 */                                         attributeMatch = true;
/* 3282 */                                         break;
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/* 3289 */                                     if (attributeMatch)
/*      */                                     {
/* 3291 */                                       alertCount = alertList.size();
/*      */                                       
/* 3293 */                                       for (int k = 0; k < alertCount; ???++)
/*      */                                       {
/*      */ 
/* 3296 */                                         ArrayList tempColumn = (ArrayList)alertList.get(k);
/*      */                                         
/* 3298 */                                         tempSource = (String)tempColumn.get(0);
/* 3299 */                                         tempCategory = (String)tempColumn.get(1);
/* 3300 */                                         if ((singleColumnlist.contains(tempCategory)) && (singleMonitorlist.contains(tempSource)))
/*      */                                         {
/* 3302 */                                           Display_name = (String)tempColumn.get(2);
/* 3303 */                                           severity = (String)tempColumn.get(3);
/* 3304 */                                           deatil_message = (String)tempColumn.get(4);
/*      */                                           
/*      */ 
/* 3307 */                                           if (severity.equals("1"))
/*      */                                           {
/*      */                                             try
/*      */                                             {
/* 3311 */                                               criticalAlert = true;
/* 3312 */                                               count_cric_messages++;
/*      */                                               
/* 3314 */                                               String[] tempName = deatil_message.split("<br>");
/* 3315 */                                               String dispMessage = "";
/*      */                                               
/* 3317 */                                               if (tempName[1].equals("Root Cause : "))
/*      */                                               {
/* 3319 */                                                 dispMessage = tempName[0] + tempName[2];
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 3323 */                                                 dispMessage = tempName[1];
/*      */                                               }
/* 3325 */                                               deatil_Critical_message1 = deatil_Critical_message1 + count_cric_messages + ". " + dispMessage + "<br>";
/*      */                                             }
/*      */                                             catch (Exception e)
/*      */                                             {
/* 3329 */                                               System.out.println("ERROROCCUREDHERE   :  " + e);
/*      */                                             }
/*      */                                             
/*      */                                           }
/* 3333 */                                           else if (severity.equals("4"))
/*      */                                           {
/*      */                                             try
/*      */                                             {
/* 3337 */                                               warningAlert = true;
/* 3338 */                                               count_warn_messages++;
/*      */                                               
/* 3340 */                                               String[] tempWarningName = deatil_message.split("<br>");
/* 3341 */                                               String dispWarningMessage = "";
/*      */                                               
/* 3343 */                                               if (tempWarningName[1].equals("Root Cause : "))
/*      */                                               {
/* 3345 */                                                 dispWarningMessage = tempWarningName[0] + tempWarningName[2];
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 3349 */                                                 dispWarningMessage = tempWarningName[1];
/*      */                                               }
/* 3351 */                                               deatil_Warning_message1 = deatil_Warning_message1 + count_warn_messages + ". " + dispWarningMessage + "<br>";
/*      */ 
/*      */                                             }
/*      */                                             catch (Exception e)
/*      */                                             {
/* 3356 */                                               System.out.println("ERROROCCUREDHERE   :  " + e);
/*      */                                             }
/*      */                                             
/* 3359 */                                           } else if (severity.equals("5"))
/*      */                                           {
/*      */ 
/* 3362 */                                             clearAlert = true;
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                       
/*      */ 
/*      */ 
/*      */ 
/* 3370 */                                       if ((!criticalAlert) && (!warningAlert))
/*      */                                       {
/* 3372 */                                         severity = "5";
/*      */                                       }
/*      */                                       
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3378 */                                       mouse_over_disp_message = "No attributes Resource Type match with this monitor group !";
/* 3379 */                                       severity = null;
/*      */                                     }
/*      */                                   }
/*      */                                   catch (Exception e)
/*      */                                   {
/* 3384 */                                     e.printStackTrace(); return;
/*      */                                   }
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3396 */                                   out.write("\n\n    ");
/*      */                                   
/* 3398 */                                   SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3399 */                                   _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3400 */                                   _jspx_th_c_005fset_005f0.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                   
/* 3402 */                                   _jspx_th_c_005fset_005f0.setVar("key");
/* 3403 */                                   int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3404 */                                   if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3405 */                                     if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3406 */                                       out = _jspx_page_context.pushBody();
/* 3407 */                                       _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3408 */                                       _jspx_th_c_005fset_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 3411 */                                       out.write(32);
/* 3412 */                                       out.print(row.get(6) + "#" + "18" + "#" + "MESSAGE");
/* 3413 */                                       int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3414 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3417 */                                     if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3418 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3421 */                                   if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3422 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                                   }
/*      */                                   
/* 3425 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3426 */                                   out.write("\n    ");
/*      */                                   
/* 3428 */                                   String ME_key = row.get(6) + "#" + "18" + "#" + "MESSAGE";
/*      */                                   
/*      */ 
/* 3431 */                                   out.write("\n            <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\n    ");
/*      */                                   
/* 3433 */                                   if (resid.equals("0"))
/*      */                                   {
/*      */ 
/*      */ 
/* 3437 */                                     mouse_over_disp_message = "No Monitors Associated with this monitor group !";
/* 3438 */                                     severity = null;
/*      */                                   }
/*      */                                   
/* 3441 */                                   String img_src = "";
/*      */                                   
/* 3443 */                                   if (criticalAlert)
/*      */                                   {
/* 3445 */                                     img_src = "<img border=\"0\" src=\"/images/standalone_icon_health_critical.gif\"></img>";
/* 3446 */                                     mouse_over_disp_message = deatil_Critical_message1;
/*      */                                   }
/* 3448 */                                   else if ((criticalAlert & warningAlert))
/*      */                                   {
/* 3450 */                                     img_src = "<img border=\"0\" src=\"/images/standalone_icon_health_critical.gif\"></img>";
/* 3451 */                                     mouse_over_disp_message = deatil_Critical_message1 + deatil_Warning_message1;
/*      */                                   }
/* 3453 */                                   else if (warningAlert)
/*      */                                   {
/* 3455 */                                     img_src = "<img border=\"0\" src=\"/images/standalone_icon_health_warning.gif\"></img>";
/* 3456 */                                     mouse_over_disp_message = deatil_Warning_message1;
/*      */                                   }
/* 3458 */                                   else if (severity == null)
/*      */                                   {
/* 3460 */                                     img_src = "<img border=\"0\" src=\"/images/standalone_icon_health_unknown.gif\"></img>";
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3465 */                                     img_src = "<img border=\"0\" src=\"/images/standalone_icon_health_clear.gif\"></img>";
/* 3466 */                                     mouse_over_disp_message = "Clear";
/*      */                                   }
/*      */                                   
/* 3469 */                                   out.write("\n        <td  align=\"center\" ");
/* 3470 */                                   out.print(bgcolor);
/* 3471 */                                   out.write(" >\n                <a onmouseover=\"ddrivetip(this,event,'");
/* 3472 */                                   out.print(mouse_over_disp_message);
/* 3473 */                                   out.write("',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\"> ");
/* 3474 */                                   out.print(img_src);
/* 3475 */                                   out.write("</a>\n        </td>\n        <!--column loop ends here  -->\n    ");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 3480 */                                 out.write("\n\n\t</tr>\n\n\t");
/* 3481 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3482 */                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3483 */                                 i = (Integer)_jspx_page_context.findAttribute("i");
/* 3484 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3487 */                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3488 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3491 */                             if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3492 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                             }
/*      */                             
/* 3495 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3496 */                             out.write("\n\t</table>\n");
/* 3497 */                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3498 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3502 */                         if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3503 */                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                         }
/*      */                         
/* 3506 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3507 */                         out.write(32);
/* 3508 */                         out.write(10);
/*      */                       }
/* 3510 */                       System.out.println("alerts showing block ends");
/* 3511 */                       System.out.println("server endtime" + cal.getTime());
/* 3512 */                       System.out.println(new Date() + "\n");
/* 3513 */                       long end = System.currentTimeMillis();
/* 3514 */                       long diff = end - start;
/* 3515 */                       System.out.println("Difference is : " + diff);
/* 3516 */                       String responseTimeMessage = FormatUtil.getString("webclient.server.responsetime");
/*      */                       
/* 3518 */                       out.write("\n\t\t</td>\n\t</tr>\n\t</table>\n\n\n\n</table>\n");
/*      */                     }
/* 3520 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3521 */         out = _jspx_out;
/* 3522 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3523 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3524 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3527 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3533 */     PageContext pageContext = _jspx_page_context;
/* 3534 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3536 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3537 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3538 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3540 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3542 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3543 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3544 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3546 */       return true;
/*      */     }
/* 3548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3554 */     PageContext pageContext = _jspx_page_context;
/* 3555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3557 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3558 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3559 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 3560 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3561 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3562 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3563 */         out = _jspx_page_context.pushBody();
/* 3564 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 3565 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3568 */         out.write("am.webclient.alertviews.createnewview.link.name");
/* 3569 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3570 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3573 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3574 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3577 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3578 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3579 */       return true;
/*      */     }
/* 3581 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3582 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3587 */     PageContext pageContext = _jspx_page_context;
/* 3588 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3590 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3591 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3592 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/* 3593 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3594 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 3595 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3596 */         out = _jspx_page_context.pushBody();
/* 3597 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 3598 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3601 */         out.write("am.webclient.alertviews.createnewview.link.name");
/* 3602 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 3603 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3606 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3607 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3610 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3611 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3612 */       return true;
/*      */     }
/* 3614 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3615 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3620 */     PageContext pageContext = _jspx_page_context;
/* 3621 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3623 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3624 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3625 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/* 3626 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3627 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 3628 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3629 */         out = _jspx_page_context.pushBody();
/* 3630 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 3631 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3634 */         out.write("am.webclient.alertviews.availableviews.title");
/* 3635 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3636 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3639 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3640 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3643 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3644 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3645 */       return true;
/*      */     }
/* 3647 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3648 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3653 */     PageContext pageContext = _jspx_page_context;
/* 3654 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3656 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3657 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3658 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/* 3659 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3660 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 3661 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3662 */         out = _jspx_page_context.pushBody();
/* 3663 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 3664 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3667 */         out.write("am.webclient.alertviews.availableviews.title");
/* 3668 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3669 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3672 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3673 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3676 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3677 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3678 */       return true;
/*      */     }
/* 3680 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3686 */     PageContext pageContext = _jspx_page_context;
/* 3687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3689 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3690 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3691 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/* 3692 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3693 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3694 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3695 */         out = _jspx_page_context.pushBody();
/* 3696 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3697 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3700 */         out.write("am.webclient.alertviews.viewname.title");
/* 3701 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3702 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3705 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3706 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3709 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3710 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3711 */       return true;
/*      */     }
/* 3713 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3719 */     PageContext pageContext = _jspx_page_context;
/* 3720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3722 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3723 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3724 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/* 3725 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3726 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 3727 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3728 */         out = _jspx_page_context.pushBody();
/* 3729 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 3730 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3733 */         out.write("am.webclient.alertviews.createnewview.link.name");
/* 3734 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 3735 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3738 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3739 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3742 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3743 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3744 */       return true;
/*      */     }
/* 3746 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3752 */     PageContext pageContext = _jspx_page_context;
/* 3753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3755 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3756 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3757 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/* 3758 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3759 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3760 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3761 */         out = _jspx_page_context.pushBody();
/* 3762 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 3763 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3766 */         out.write("am.webclient.alertviews.createnewview.link.name");
/* 3767 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 3768 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3771 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3772 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3775 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3776 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3777 */       return true;
/*      */     }
/* 3779 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3785 */     PageContext pageContext = _jspx_page_context;
/* 3786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3788 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3789 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3790 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/* 3791 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3792 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 3793 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3794 */         out = _jspx_page_context.pushBody();
/* 3795 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 3796 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3799 */         out.write("am.webclient.alertviews.availableviews.title");
/* 3800 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 3801 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3804 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3805 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3808 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3809 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3810 */       return true;
/*      */     }
/* 3812 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3813 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3818 */     PageContext pageContext = _jspx_page_context;
/* 3819 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3821 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3822 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3823 */     _jspx_th_fmt_005fmessage_005f8.setParent(null);
/* 3824 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3825 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 3826 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3827 */         out = _jspx_page_context.pushBody();
/* 3828 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 3829 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3832 */         out.write("am.webclient.alertviews.availableviews.title");
/* 3833 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 3834 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3837 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3838 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3841 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3842 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3843 */       return true;
/*      */     }
/* 3845 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3851 */     PageContext pageContext = _jspx_page_context;
/* 3852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3854 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3855 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3856 */     _jspx_th_fmt_005fmessage_005f9.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f0);
/* 3857 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3858 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 3859 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3860 */         out = _jspx_page_context.pushBody();
/* 3861 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 3862 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3865 */         out.write("am.webclient.alertviews.monitorgroups.title");
/* 3866 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 3867 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3870 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3871 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3874 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3875 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3876 */       return true;
/*      */     }
/* 3878 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3879 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DisplayMGStatus_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */