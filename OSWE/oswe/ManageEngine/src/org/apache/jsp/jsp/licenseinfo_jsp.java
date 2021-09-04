/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.SWMgmtUtil;
/*      */ import com.adventnet.tools.prevalent.Wield;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.apminsight.server.license.ApmInsightLicenseType;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.security.Principal;
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
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class licenseinfo_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   60 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   63 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   64 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   65 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   72 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   77 */     ArrayList list = null;
/*   78 */     StringBuffer sbf = new StringBuffer();
/*   79 */     ManagedApplication mo = new ManagedApplication();
/*   80 */     if (distinct)
/*      */     {
/*   82 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   86 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   89 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   91 */       ArrayList row = (ArrayList)list.get(i);
/*   92 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   93 */       if (distinct) {
/*   94 */         sbf.append(row.get(0));
/*      */       } else
/*   96 */         sbf.append(row.get(1));
/*   97 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  100 */     return sbf.toString(); }
/*      */   
/*  102 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  105 */     if (severity == null)
/*      */     {
/*  107 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  109 */     if (severity.equals("5"))
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  113 */     if (severity.equals("1"))
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  120 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  127 */     if (severity == null)
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  131 */     if (severity.equals("1"))
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  135 */     if (severity.equals("4"))
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  139 */     if (severity.equals("5"))
/*      */     {
/*  141 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  146 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  152 */     if (severity == null)
/*      */     {
/*  154 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  156 */     if (severity.equals("5"))
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  160 */     if (severity.equals("1"))
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  166 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  172 */     if (severity == null)
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  176 */     if (severity.equals("1"))
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  180 */     if (severity.equals("4"))
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  184 */     if (severity.equals("5"))
/*      */     {
/*  186 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  190 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  196 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  202 */     if (severity == 5)
/*      */     {
/*  204 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  206 */     if (severity == 1)
/*      */     {
/*  208 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  213 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  219 */     if (severity == null)
/*      */     {
/*  221 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  223 */     if (severity.equals("5"))
/*      */     {
/*  225 */       if (isAvailability) {
/*  226 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  229 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  232 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  234 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  236 */     if (severity.equals("1"))
/*      */     {
/*  238 */       if (isAvailability) {
/*  239 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  242 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  249 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  256 */     if (severity == null)
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  260 */     if (severity.equals("5"))
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  264 */     if (severity.equals("4"))
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  268 */     if (severity.equals("1"))
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  275 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  281 */     if (severity == null)
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  285 */     if (severity.equals("5"))
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  289 */     if (severity.equals("4"))
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  293 */     if (severity.equals("1"))
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  300 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  307 */     if (severity == null)
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  311 */     if (severity.equals("5"))
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  315 */     if (severity.equals("4"))
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  319 */     if (severity.equals("1"))
/*      */     {
/*  321 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  326 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  334 */     StringBuffer out = new StringBuffer();
/*  335 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  336 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  337 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  338 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  339 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  340 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  341 */     out.append("</tr>");
/*  342 */     out.append("</form></table>");
/*  343 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  350 */     if (val == null)
/*      */     {
/*  352 */       return "-";
/*      */     }
/*      */     
/*  355 */     String ret = FormatUtil.formatNumber(val);
/*  356 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  357 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  360 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  364 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  372 */     StringBuffer out = new StringBuffer();
/*  373 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  374 */     out.append("<tr>");
/*  375 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  377 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  379 */     out.append("</tr>");
/*  380 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  384 */       if (j % 2 == 0)
/*      */       {
/*  386 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  390 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  393 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  395 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  398 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  402 */       out.append("</tr>");
/*      */     }
/*  404 */     out.append("</table>");
/*  405 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  406 */     out.append("<tr>");
/*  407 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  408 */     out.append("</tr>");
/*  409 */     out.append("</table>");
/*  410 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  416 */     StringBuffer out = new StringBuffer();
/*  417 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  418 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  419 */     out.append("<tr>");
/*  420 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  421 */     out.append("<tr>");
/*  422 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  423 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  424 */     out.append("</tr>");
/*  425 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  428 */       out.append("<tr>");
/*  429 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  430 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  431 */       out.append("</tr>");
/*      */     }
/*      */     
/*  434 */     out.append("</table>");
/*  435 */     out.append("</table>");
/*  436 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  441 */     if (severity.equals("0"))
/*      */     {
/*  443 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  447 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  454 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  467 */     StringBuffer out = new StringBuffer();
/*  468 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  469 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  471 */       out.append("<tr>");
/*  472 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  473 */       out.append("</tr>");
/*      */       
/*      */ 
/*  476 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  478 */         String borderclass = "";
/*      */         
/*      */ 
/*  481 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  483 */         out.append("<tr>");
/*      */         
/*  485 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  486 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  487 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  493 */     out.append("</table><br>");
/*  494 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  495 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  497 */       List sLinks = secondLevelOfLinks[0];
/*  498 */       List sText = secondLevelOfLinks[1];
/*  499 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  502 */         out.append("<tr>");
/*  503 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  504 */         out.append("</tr>");
/*  505 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  507 */           String borderclass = "";
/*      */           
/*      */ 
/*  510 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  512 */           out.append("<tr>");
/*      */           
/*  514 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  515 */           if (sLinks.get(i).toString().length() == 0) {
/*  516 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  519 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  521 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  525 */     out.append("</table>");
/*  526 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  533 */     StringBuffer out = new StringBuffer();
/*  534 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  535 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  537 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  539 */         out.append("<tr>");
/*  540 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  541 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  545 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  547 */           String borderclass = "";
/*      */           
/*      */ 
/*  550 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  552 */           out.append("<tr>");
/*      */           
/*  554 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  555 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  556 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  559 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  562 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  567 */     out.append("</table><br>");
/*  568 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  569 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  571 */       List sLinks = secondLevelOfLinks[0];
/*  572 */       List sText = secondLevelOfLinks[1];
/*  573 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  576 */         out.append("<tr>");
/*  577 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  578 */         out.append("</tr>");
/*  579 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  581 */           String borderclass = "";
/*      */           
/*      */ 
/*  584 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  586 */           out.append("<tr>");
/*      */           
/*  588 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  589 */           if (sLinks.get(i).toString().length() == 0) {
/*  590 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  593 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  595 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  599 */     out.append("</table>");
/*  600 */     return out.toString();
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
/*  613 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  622 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  625 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  628 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  631 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  634 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  642 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  647 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  652 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  657 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  662 */     if (val != null)
/*      */     {
/*  664 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  668 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  673 */     if (val == null) {
/*  674 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  678 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  683 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  689 */     if (val != null)
/*      */     {
/*  691 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  695 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  701 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  706 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  710 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  715 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  720 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  725 */     String hostaddress = "";
/*  726 */     String ip = request.getHeader("x-forwarded-for");
/*  727 */     if (ip == null)
/*  728 */       ip = request.getRemoteAddr();
/*  729 */     InetAddress add = null;
/*  730 */     if (ip.equals("127.0.0.1")) {
/*  731 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  735 */       add = InetAddress.getByName(ip);
/*      */     }
/*  737 */     hostaddress = add.getHostName();
/*  738 */     if (hostaddress.indexOf('.') != -1) {
/*  739 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  740 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  744 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  749 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  755 */     if (severity == null)
/*      */     {
/*  757 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  759 */     if (severity.equals("5"))
/*      */     {
/*  761 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  763 */     if (severity.equals("1"))
/*      */     {
/*  765 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  770 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  775 */     ResultSet set = null;
/*  776 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  777 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  779 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  780 */       if (set.next()) { String str1;
/*  781 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  782 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  785 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  790 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  793 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  795 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  799 */     StringBuffer rca = new StringBuffer();
/*  800 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  801 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  804 */     int rcalength = key.length();
/*  805 */     String split = "6. ";
/*  806 */     int splitPresent = key.indexOf(split);
/*  807 */     String div1 = "";String div2 = "";
/*  808 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  810 */       if (rcalength > 180) {
/*  811 */         rca.append("<span class=\"rca-critical-text\">");
/*  812 */         getRCATrimmedText(key, rca);
/*  813 */         rca.append("</span>");
/*      */       } else {
/*  815 */         rca.append("<span class=\"rca-critical-text\">");
/*  816 */         rca.append(key);
/*  817 */         rca.append("</span>");
/*      */       }
/*  819 */       return rca.toString();
/*      */     }
/*  821 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  822 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  823 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  824 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  825 */     getRCATrimmedText(div1, rca);
/*  826 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  829 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  830 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  831 */     getRCATrimmedText(div2, rca);
/*  832 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  834 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  839 */     String[] st = msg.split("<br>");
/*  840 */     for (int i = 0; i < st.length; i++) {
/*  841 */       String s = st[i];
/*  842 */       if (s.length() > 180) {
/*  843 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  845 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  849 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  850 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  852 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  856 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  857 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  858 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  861 */       if (key == null) {
/*  862 */         return ret;
/*      */       }
/*      */       
/*  865 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  866 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  869 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  870 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  871 */       set = AMConnectionPool.executeQueryStmt(query);
/*  872 */       if (set.next())
/*      */       {
/*  874 */         String helpLink = set.getString("LINK");
/*  875 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  878 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  884 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  903 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  894 */         if (set != null) {
/*  895 */           AMConnectionPool.closeStatement(set);
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
/*  909 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  910 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  912 */       String entityStr = (String)keys.nextElement();
/*  913 */       String mmessage = temp.getProperty(entityStr);
/*  914 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  915 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  917 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  923 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  924 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  926 */       String entityStr = (String)keys.nextElement();
/*  927 */       String mmessage = temp.getProperty(entityStr);
/*  928 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  929 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  931 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  936 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  946 */     String des = new String();
/*  947 */     while (str.indexOf(find) != -1) {
/*  948 */       des = des + str.substring(0, str.indexOf(find));
/*  949 */       des = des + replace;
/*  950 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  952 */     des = des + str;
/*  953 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  960 */       if (alert == null)
/*      */       {
/*  962 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  964 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  966 */         return "&nbsp;";
/*      */       }
/*      */       
/*  969 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  971 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  974 */       int rcalength = test.length();
/*  975 */       if (rcalength < 300)
/*      */       {
/*  977 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  981 */       StringBuffer out = new StringBuffer();
/*  982 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  983 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  984 */       out.append("</div>");
/*  985 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  986 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  987 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  992 */       ex.printStackTrace();
/*      */     }
/*  994 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1000 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1005 */     ArrayList attribIDs = new ArrayList();
/* 1006 */     ArrayList resIDs = new ArrayList();
/* 1007 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1009 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1011 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1013 */       String resourceid = "";
/* 1014 */       String resourceType = "";
/* 1015 */       if (type == 2) {
/* 1016 */         resourceid = (String)row.get(0);
/* 1017 */         resourceType = (String)row.get(3);
/*      */       }
/* 1019 */       else if (type == 3) {
/* 1020 */         resourceid = (String)row.get(0);
/* 1021 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1024 */         resourceid = (String)row.get(6);
/* 1025 */         resourceType = (String)row.get(7);
/*      */       }
/* 1027 */       resIDs.add(resourceid);
/* 1028 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1029 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1031 */       String healthentity = null;
/* 1032 */       String availentity = null;
/* 1033 */       if (healthid != null) {
/* 1034 */         healthentity = resourceid + "_" + healthid;
/* 1035 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1038 */       if (availid != null) {
/* 1039 */         availentity = resourceid + "_" + availid;
/* 1040 */         entitylist.add(availentity);
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
/* 1054 */     Properties alert = getStatus(entitylist);
/* 1055 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1060 */     int size = monitorList.size();
/*      */     
/* 1062 */     String[] severity = new String[size];
/*      */     
/* 1064 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1066 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1067 */       String resourceName1 = (String)row1.get(7);
/* 1068 */       String resourceid1 = (String)row1.get(6);
/* 1069 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1070 */       if (severity[j] == null)
/*      */       {
/* 1072 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1076 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1078 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1080 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1083 */         if (sev > 0) {
/* 1084 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1085 */           monitorList.set(k, monitorList.get(j));
/* 1086 */           monitorList.set(j, t);
/* 1087 */           String temp = severity[k];
/* 1088 */           severity[k] = severity[j];
/* 1089 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1095 */     int z = 0;
/* 1096 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1099 */       int i = 0;
/* 1100 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1103 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1107 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1111 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1113 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1116 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1120 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1123 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1124 */       String resourceName1 = (String)row1.get(7);
/* 1125 */       String resourceid1 = (String)row1.get(6);
/* 1126 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1127 */       if (hseverity[j] == null)
/*      */       {
/* 1129 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1134 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1136 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1139 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1142 */         if (hsev > 0) {
/* 1143 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1144 */           monitorList.set(k, monitorList.get(j));
/* 1145 */           monitorList.set(j, t);
/* 1146 */           String temp1 = hseverity[k];
/* 1147 */           hseverity[k] = hseverity[j];
/* 1148 */           hseverity[j] = temp1;
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
/* 1160 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1161 */     boolean forInventory = false;
/* 1162 */     String trdisplay = "none";
/* 1163 */     String plusstyle = "inline";
/* 1164 */     String minusstyle = "none";
/* 1165 */     String haidTopLevel = "";
/* 1166 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1168 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1170 */         haidTopLevel = request.getParameter("haid");
/* 1171 */         forInventory = true;
/* 1172 */         trdisplay = "table-row;";
/* 1173 */         plusstyle = "none";
/* 1174 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1181 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1184 */     ArrayList listtoreturn = new ArrayList();
/* 1185 */     StringBuffer toreturn = new StringBuffer();
/* 1186 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1187 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1188 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1190 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1192 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1193 */       String childresid = (String)singlerow.get(0);
/* 1194 */       String childresname = (String)singlerow.get(1);
/* 1195 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1196 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1197 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1198 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1199 */       String unmanagestatus = (String)singlerow.get(5);
/* 1200 */       String actionstatus = (String)singlerow.get(6);
/* 1201 */       String linkclass = "monitorgp-links";
/* 1202 */       String titleforres = childresname;
/* 1203 */       String titilechildresname = childresname;
/* 1204 */       String childimg = "/images/trcont.png";
/* 1205 */       String flag = "enable";
/* 1206 */       String dcstarted = (String)singlerow.get(8);
/* 1207 */       String configMonitor = "";
/* 1208 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1209 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1211 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1213 */       if (singlerow.get(7) != null)
/*      */       {
/* 1215 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1217 */       String haiGroupType = "0";
/* 1218 */       if ("HAI".equals(childtype))
/*      */       {
/* 1220 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1222 */       childimg = "/images/trend.png";
/* 1223 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1224 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1225 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1227 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1229 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1231 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1232 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1235 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1237 */         linkclass = "disabledtext";
/* 1238 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1240 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1241 */       String availmouseover = "";
/* 1242 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1244 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1246 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1247 */       String healthmouseover = "";
/* 1248 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1250 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1253 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1254 */       int spacing = 0;
/* 1255 */       if (level >= 1)
/*      */       {
/* 1257 */         spacing = 40 * level;
/*      */       }
/* 1259 */       if (childtype.equals("HAI"))
/*      */       {
/* 1261 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1262 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1263 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1265 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1266 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1267 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1268 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1269 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1270 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1271 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1272 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1273 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1274 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1275 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1277 */         if (!forInventory)
/*      */         {
/* 1279 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1282 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1284 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1286 */           actions = editlink + actions;
/*      */         }
/* 1288 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1290 */           actions = actions + associatelink;
/*      */         }
/* 1292 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1293 */         String arrowimg = "";
/* 1294 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1296 */           actions = "";
/* 1297 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1298 */           checkbox = "";
/* 1299 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1301 */         if (isIt360)
/*      */         {
/* 1303 */           actionimg = "";
/* 1304 */           actions = "";
/* 1305 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1306 */           checkbox = "";
/*      */         }
/*      */         
/* 1309 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1311 */           actions = "";
/*      */         }
/* 1313 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1315 */           checkbox = "";
/*      */         }
/*      */         
/* 1318 */         String resourcelink = "";
/*      */         
/* 1320 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1322 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1326 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1329 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1332 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1333 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1334 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1335 */         if (!isIt360)
/*      */         {
/* 1337 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1341 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1344 */         toreturn.append("</tr>");
/* 1345 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1347 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1348 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1352 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1353 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1356 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1360 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1362 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1364 */             toreturn.append(assocMessage);
/* 1365 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1366 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1367 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1368 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1374 */         String resourcelink = null;
/* 1375 */         boolean hideEditLink = false;
/* 1376 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1378 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1379 */           hideEditLink = true;
/* 1380 */           if (isIt360)
/*      */           {
/* 1382 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1386 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1388 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1390 */           hideEditLink = true;
/* 1391 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1392 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1397 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1400 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1401 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1402 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1403 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1404 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1405 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1406 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1407 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1408 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1409 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1410 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1411 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1412 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1414 */         if (hideEditLink)
/*      */         {
/* 1416 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1418 */         if (!forInventory)
/*      */         {
/* 1420 */           removefromgroup = "";
/*      */         }
/* 1422 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1423 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1424 */           actions = actions + configcustomfields;
/*      */         }
/* 1426 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1428 */           actions = editlink + actions;
/*      */         }
/* 1430 */         String managedLink = "";
/* 1431 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1433 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1434 */           actions = "";
/* 1435 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1436 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1439 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1441 */           checkbox = "";
/*      */         }
/*      */         
/* 1444 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1446 */           actions = "";
/*      */         }
/* 1448 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1449 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1450 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1451 */         if (isIt360)
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1459 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1460 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1461 */         if (!isIt360)
/*      */         {
/* 1463 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1467 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1469 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1472 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1479 */       StringBuilder toreturn = new StringBuilder();
/* 1480 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1481 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1482 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1483 */       String title = "";
/* 1484 */       message = EnterpriseUtil.decodeString(message);
/* 1485 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1486 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1487 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1489 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1491 */       else if ("5".equals(severity))
/*      */       {
/* 1493 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1497 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1499 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1500 */       toreturn.append(v);
/*      */       
/* 1502 */       toreturn.append(link);
/* 1503 */       if (severity == null)
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1507 */       else if (severity.equals("5"))
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       else if (severity.equals("4"))
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1515 */       else if (severity.equals("1"))
/*      */       {
/* 1517 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1522 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1524 */       toreturn.append("</a>");
/* 1525 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1529 */       ex.printStackTrace();
/*      */     }
/* 1531 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1538 */       StringBuilder toreturn = new StringBuilder();
/* 1539 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1540 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1541 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1542 */       if (message == null)
/*      */       {
/* 1544 */         message = "";
/*      */       }
/*      */       
/* 1547 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1548 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1550 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1551 */       toreturn.append(v);
/*      */       
/* 1553 */       toreturn.append(link);
/*      */       
/* 1555 */       if (severity == null)
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1559 */       else if (severity.equals("5"))
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1563 */       else if (severity.equals("1"))
/*      */       {
/* 1565 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1570 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1572 */       toreturn.append("</a>");
/* 1573 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1579 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1582 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1583 */     if (invokeActions != null) {
/* 1584 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1585 */       while (iterator.hasNext()) {
/* 1586 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1587 */         if (actionmap.containsKey(actionid)) {
/* 1588 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1593 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1597 */     String actionLink = "";
/* 1598 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1599 */     String query = "";
/* 1600 */     ResultSet rs = null;
/* 1601 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1602 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1603 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1604 */       actionLink = "method=" + methodName;
/*      */     }
/* 1606 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1607 */       actionLink = methodName;
/*      */     }
/* 1609 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1610 */     Iterator itr = methodarglist.iterator();
/* 1611 */     boolean isfirstparam = true;
/* 1612 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1613 */     while (itr.hasNext()) {
/* 1614 */       HashMap argmap = (HashMap)itr.next();
/* 1615 */       String argtype = (String)argmap.get("TYPE");
/* 1616 */       String argname = (String)argmap.get("IDENTITY");
/* 1617 */       String paramname = (String)argmap.get("PARAMETER");
/* 1618 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1619 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1620 */         isfirstparam = false;
/* 1621 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1623 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1627 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1631 */         actionLink = actionLink + "&";
/*      */       }
/* 1633 */       String paramValue = null;
/* 1634 */       String tempargname = argname;
/* 1635 */       if (commonValues.getProperty(tempargname) != null) {
/* 1636 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1639 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1640 */           String dbType = DBQueryUtil.getDBType();
/* 1641 */           if (dbType.equals("mysql")) {
/* 1642 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1645 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1647 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1649 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1650 */             if (rs.next()) {
/* 1651 */               paramValue = rs.getString("VALUE");
/* 1652 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1656 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1660 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1663 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1668 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1669 */           paramValue = rowId;
/*      */         }
/* 1671 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1672 */           paramValue = managedObjectName;
/*      */         }
/* 1674 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1675 */           paramValue = resID;
/*      */         }
/* 1677 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1678 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1681 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1683 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1684 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1685 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1687 */     return actionLink;
/*      */   }
/*      */   
/* 1690 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1691 */     String dependentAttribute = null;
/* 1692 */     String align = "left";
/*      */     
/* 1694 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1695 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1696 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1697 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1698 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1699 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1700 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1701 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1702 */       align = "center";
/*      */     }
/*      */     
/* 1705 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1706 */     String actualdata = "";
/*      */     
/* 1708 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1709 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1710 */         actualdata = availValue;
/*      */       }
/* 1712 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1713 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1717 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1718 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1721 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1727 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1728 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1729 */       toreturn.append("<table>");
/* 1730 */       toreturn.append("<tr>");
/* 1731 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1732 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1733 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1734 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1735 */         String toolTip = "";
/* 1736 */         String hideClass = "";
/* 1737 */         String textStyle = "";
/* 1738 */         boolean isreferenced = true;
/* 1739 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1740 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1741 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1742 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1744 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1745 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1746 */           while (valueList.hasMoreTokens()) {
/* 1747 */             String dependentVal = valueList.nextToken();
/* 1748 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1749 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1750 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1752 */               toolTip = "";
/* 1753 */               hideClass = "";
/* 1754 */               isreferenced = false;
/* 1755 */               textStyle = "disabledtext";
/* 1756 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1760 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1761 */           toolTip = "";
/* 1762 */           hideClass = "";
/* 1763 */           isreferenced = false;
/* 1764 */           textStyle = "disabledtext";
/* 1765 */           if (dependentImageMap != null) {
/* 1766 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1767 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1770 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1774 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1775 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1776 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1777 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1778 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1779 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1781 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1782 */           if (isreferenced) {
/* 1783 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1787 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1788 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1789 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1790 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1791 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1792 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1794 */           toreturn.append("</span>");
/* 1795 */           toreturn.append("</a>");
/* 1796 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1799 */       toreturn.append("</tr>");
/* 1800 */       toreturn.append("</table>");
/* 1801 */       toreturn.append("</td>");
/*      */     } else {
/* 1803 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1806 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1810 */     String colTime = null;
/* 1811 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1812 */     if ((rows != null) && (rows.size() > 0)) {
/* 1813 */       Iterator<String> itr = rows.iterator();
/* 1814 */       String maxColQuery = "";
/* 1815 */       for (;;) { if (itr.hasNext()) {
/* 1816 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1817 */           ResultSet maxCol = null;
/*      */           try {
/* 1819 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1820 */             while (maxCol.next()) {
/* 1821 */               if (colTime == null) {
/* 1822 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1825 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1834 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1836 */               if (maxCol != null)
/* 1837 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1839 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1834 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1836 */               if (maxCol != null)
/* 1837 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1839 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1844 */     return colTime;
/*      */   }
/*      */   
/* 1847 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1848 */     tablename = null;
/* 1849 */     ResultSet rsTable = null;
/* 1850 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1852 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1853 */       while (rsTable.next()) {
/* 1854 */         tablename = rsTable.getString("DATATABLE");
/* 1855 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1856 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1869 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1860 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1863 */         if (rsTable != null)
/* 1864 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1866 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1872 */     String argsList = "";
/* 1873 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1875 */       if (showArgsMap.get(row) != null) {
/* 1876 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1877 */         if (showArgslist != null) {
/* 1878 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1879 */             if (argsList.trim().equals("")) {
/* 1880 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1883 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1890 */       e.printStackTrace();
/* 1891 */       return "";
/*      */     }
/* 1893 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1898 */     String argsList = "";
/* 1899 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1902 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1904 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1905 */         if (hideArgsList != null)
/*      */         {
/* 1907 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1909 */             if (argsList.trim().equals(""))
/*      */             {
/* 1911 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1915 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1923 */       ex.printStackTrace();
/*      */     }
/* 1925 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1929 */     StringBuilder toreturn = new StringBuilder();
/* 1930 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1937 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1938 */       Iterator itr = tActionList.iterator();
/* 1939 */       while (itr.hasNext()) {
/* 1940 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1941 */         String confirmmsg = "";
/* 1942 */         String link = "";
/* 1943 */         String isJSP = "NO";
/* 1944 */         HashMap tactionMap = (HashMap)itr.next();
/* 1945 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1946 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1947 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1948 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1949 */           (actionmap.containsKey(actionId))) {
/* 1950 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1951 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1952 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1953 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1954 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1956 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1962 */           if (isTableAction) {
/* 1963 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1966 */             tableName = "Link";
/* 1967 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1968 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1969 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1970 */             toreturn.append("</a></td>");
/*      */           }
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1973 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1974 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1975 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1981 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1987 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1989 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1990 */       Properties prop = (Properties)node.getUserObject();
/* 1991 */       String mgID = prop.getProperty("label");
/* 1992 */       String mgName = prop.getProperty("value");
/* 1993 */       String isParent = prop.getProperty("isParent");
/* 1994 */       int mgIDint = Integer.parseInt(mgID);
/* 1995 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1997 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1999 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2000 */       if (node.getChildCount() > 0)
/*      */       {
/* 2002 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2004 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2006 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2008 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2012 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2017 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2019 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2021 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2023 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2027 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2030 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2031 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2033 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2037 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2039 */       if (node.getChildCount() > 0)
/*      */       {
/* 2041 */         builder.append("<UL>");
/* 2042 */         printMGTree(node, builder);
/* 2043 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2048 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2049 */     StringBuffer toReturn = new StringBuffer();
/* 2050 */     String table = "-";
/*      */     try {
/* 2052 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2053 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2054 */       float total = 0.0F;
/* 2055 */       while (it.hasNext()) {
/* 2056 */         String attName = (String)it.next();
/* 2057 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2058 */         boolean roundOffData = false;
/* 2059 */         if ((data != null) && (!data.equals(""))) {
/* 2060 */           if (data.indexOf(",") != -1) {
/* 2061 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2064 */             float value = Float.parseFloat(data);
/* 2065 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2068 */             total += value;
/* 2069 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2072 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2077 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2078 */       while (attVsWidthList.hasNext()) {
/* 2079 */         String attName = (String)attVsWidthList.next();
/* 2080 */         String data = (String)attVsWidthProps.get(attName);
/* 2081 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2082 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2083 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2084 */         String className = (String)graphDetails.get("ClassName");
/* 2085 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2086 */         if (percentage < 1.0F)
/*      */         {
/* 2088 */           data = percentage + "";
/*      */         }
/* 2090 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2092 */       if (toReturn.length() > 0) {
/* 2093 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2097 */       e.printStackTrace();
/*      */     }
/* 2099 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2105 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2106 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2107 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2108 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2109 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2110 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2111 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2112 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2113 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2116 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2117 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2118 */       splitvalues[0] = multiplecondition.toString();
/* 2119 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2122 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2127 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2128 */     if (thresholdType != 3) {
/* 2129 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2130 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2131 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2132 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2133 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2134 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2136 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2137 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2138 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2139 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2140 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2141 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2143 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2144 */     if (updateSelected != null) {
/* 2145 */       updateSelected[0] = "selected";
/*      */     }
/* 2147 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2152 */       StringBuffer toreturn = new StringBuffer("");
/* 2153 */       if (commaSeparatedMsgId != null) {
/* 2154 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2155 */         int count = 0;
/* 2156 */         while (msgids.hasMoreTokens()) {
/* 2157 */           String id = msgids.nextToken();
/* 2158 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2159 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2160 */           count++;
/* 2161 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2162 */             if (toreturn.length() == 0) {
/* 2163 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2165 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2166 */             if (!image.trim().equals("")) {
/* 2167 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2169 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2170 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2173 */         if (toreturn.length() > 0) {
/* 2174 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2178 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2181 */       e.printStackTrace(); }
/* 2182 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2209 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2220 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
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
/* 2256 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2257 */       ManagedApplication mo = null;
/* 2258 */       synchronized (application) {
/* 2259 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2260 */         if (mo == null) {
/* 2261 */           mo = new ManagedApplication();
/* 2262 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2265 */       out.write(10);
/* 2266 */       out.write(10);
/* 2267 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2269 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2270 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2272 */       out.write(10);
/* 2273 */       out.write(10);
/* 2274 */       out.write(10);
/* 2275 */       out.write(10);
/*      */       
/* 2277 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2278 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2279 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/* 2281 */       _jspx_th_c_005fif_005f0.setTest("${not empty param.aboutPage}");
/* 2282 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2283 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/* 2285 */           out.write(10);
/*      */           
/* 2287 */           if (!OEMUtil.isOEM())
/*      */           {
/* 2289 */             out.write("\n<script language=\"JavaScript1.2\">\n                                var marqueewidth=\"250px\"\n\t\t\t\tvar marqueeheight=\"20px\"\n\t\t\t\tvar marqueespeed=1\n\t\t\t\tvar pauseit=5\n\t\t\t\tvar marqueecontent='<p align=\"center\"><font color=\"#0066CC\" size=\"1\"><br>Anandhi&nbsp;A<br>Anne&nbsp;Rejula&nbsp;S<br>Anugraha&nbsp;Benjamin&nbsp;S<br>Appadurai&nbsp;A&nbsp;N<br>Aravindhan&nbsp;J<br>Arun&nbsp;B<br>Arun&nbsp;C<br>Arun&nbsp;K<br>Arun&nbsp;Kumar&nbsp;M<br>ArunKumar&nbsp;N<br>Balaji&nbsp;P&nbsp;R&nbsp;M<br>Baskar&nbsp;Sugumar<br>Bhagidaran&nbsp;Jayakumar<br>Bhavishya&nbsp;P<br>Bijoy&nbsp;Joseph<br>Deepak&nbsp;M<br>Dilip&nbsp;Kumaravel&nbsp;D<br>Divya&nbsp;Radhakrishnan<br>Divya&nbsp;Ravishankar<br>Emmanuel&nbsp;Jeba&nbsp;Shantha&nbsp;Kumar<br>Gibu&nbsp;K.&nbsp;Mathew<br>Gobikrishnan&nbsp;J<br>Hajeera&nbsp;Begum&nbsp;A<br>Hemachand&nbsp;M<br>Jagan&nbsp;Pandurangan<br>Jasper&nbsp;Paul<br>Kabilan&nbsp;Thirumavalavan<br>Karthikeyan&nbsp;R<br>Krishnakumar&nbsp;K<br>Krishna&nbsp;Kumar&nbsp;M<br>Lini&nbsp;Susan&nbsp;John<br>Magesh&nbsp;Rajan<br>Maruthupandi&nbsp;K<br>Muraleedharan&nbsp;Sadhasivam<br>Nagarjun&nbsp;K<br>Navaneeth&nbsp;Suresh&nbsp;K<br>Pavan&nbsp;Kumar&nbsp;Reddy&nbsp;K<br>Pradeep&nbsp;Kumar&nbsp;K<br>Prasadh&nbsp;S<br>Pritika&nbsp;R<br>Priya&nbsp;Gopalakrishnan<br>Rajesh&nbsp;Seshadri<br>Rakesh&nbsp;Jayaprakash<br>Ramachandran&nbsp;K&nbsp;N<br>Ramkumar&nbsp;MJ<br>Rashmi&nbsp;Sasi<br>Rohan&nbsp;Kapoor<br>Sabarinathan&nbsp;P<br>Sathyanarayanan&nbsp;Venkatasubramanian<br>Satyavani&nbsp;Ghanta<br>Selva&nbsp;Athimoolam&nbsp;Balasubramanian<br>Selvaraj&nbsp;M<br>Selvi&nbsp;Eswari&nbsp;A<br>Shanmugam&nbsp;G<br>Sharavanan&nbsp;Sridharan<br>Smrithil&nbsp;Soman<br>Sridhar&nbsp;Iyengar<br>Sudhanshu&nbsp;Kumar<br>Vijaya&nbsp;Kumar&nbsp;M<br>Vijayalakshmi&nbsp;Balasubramanian</font><br><br></p>'//No I18N\t\t\t\t\n");
/* 2290 */             out.write("\t\t\t\tmarqueespeed=(document.all)? marqueespeed : Math.max(1, marqueespeed-1) //slow speed down by 1 for NS\n\t\t\t\tvar copyspeed=marqueespeed\n\t\t\t\tvar pausespeed=(pauseit==0)? copyspeed: 0\n\t\t\t\tvar iedom=document.all||document.getElementById\n\t\t\t\tvar actualheight=''\n\t\t\t\tvar cross_marquee, ns_marquee\n\n\t\t\t\tfunction populate(){\n\t\t\t\t\tcross_marquee=document.getElementById? document.getElementById(\"iemarquee\") : document.all.iemarquee\n\t\t\t\t\tcross_marquee.style.top=parseInt(marqueeheight)+8+\"px\"\n\t\t\t\t\tcross_marquee.innerHTML=marqueecontent\n\t\t\t\t\tactualheight=cross_marquee.offsetHeight\n\t\t\t\t\tlefttime=setInterval(\"scrollmarquee()\",30)\n\t\t\t\t}\n\t\t\t\t//window.onload=populate\n\t\t\t\twindow.onload=new Function(\"timer = setTimeout('populate()', 1000);\")\n\n\t\t\t\tfunction scrollmarquee() {\n\t\t\t\t\tif (parseInt(cross_marquee.style.top)>(actualheight*(-1)+8))\n\t\t\t\t\t{\n\t\t\t\t\t\tcross_marquee.style.top=parseInt(cross_marquee.style.top)-copyspeed+\"px\"\n\t\t\t\t\t\t}\n\t\t\t\t\telse{\n\t\t\t\t\t\tcross_marquee.style.top=parseInt(marqueeheight)+8+\"px\"\n\t\t\t\t\t\t}\n\t\t\t\t}\n\n\n\nfunction swAction(action)\n");
/* 2291 */             out.write("{\n    if(action == 'download')\n    {\n    \t  if(confirm('");
/* 2292 */             out.print(FormatUtil.getString("am.product.sp.download.initiate.confirm.text"));
/* 2293 */             out.write("'))//No I18N\n          {\n    \t\t   alert('");
/* 2294 */             out.print(FormatUtil.getString("am.product.sp.download.initiated"));
/* 2295 */             out.write("');\n\t       \t   document.form1.action=\"/swMgmt.do?method=initiateCentralDownload\";\t       \t   \n\t\t       document.form1.method=\"Post\"; //No I18N\n\t\t       document.form1.submit();\n\t       }\n    }\n    else if( action == 'upgrade')\n    {\n    \tvar isAmsExpired = '");
/* 2296 */             out.print(com.adventnet.appmanager.util.Constants.isAMSExpired());
/* 2297 */             out.write("';\n    \tif (isAmsExpired == 'true') {\n    \t\talertMessage = '");
/* 2298 */             out.print(FormatUtil.getString("am.ams.expired.message.js.text"));
/* 2299 */             out.write("';\n    \t\talert(alertMessage)\n    \t\treturn;\n    \t}\n    \tvar isMSSQL = false;\n    \t");
/*      */             
/* 2301 */             boolean isMSSQL = DBQueryUtil.isMssql();
/* 2302 */             if (isMSSQL)
/*      */             {
/*      */ 
/* 2305 */               out.write("\n\t\t\t\tvar alertMessage = '");
/* 2306 */               out.print(FormatUtil.getString("am.product.sp.backup.mssql.confirm.alert.text"));
/* 2307 */               out.write("';\n\t\t\t\tisMSSQL = true;\n\t\t\t");
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/* 2313 */               out.write("\n\t\t\t\tvar alertMessage = '");
/* 2314 */               out.print(FormatUtil.getString("am.product.sp.backup.general.confirm.alert.text"));
/* 2315 */               out.write("';\n\t\t\t");
/*      */             }
/*      */             
/*      */ 
/* 2319 */             out.write("\n\t\t if(confirm('");
/* 2320 */             out.print(FormatUtil.getString("am.product.sp.upgrade.initiate.confirm.text"));
/* 2321 */             out.write("'))//No I18N\n         {\n\t\t\t alert('");
/* 2322 */             out.print(FormatUtil.getString("am.product.sp.upgrade.initiated"));
/* 2323 */             out.write("')\n        \t document.form1.action=\"/swMgmt.do?method=initiateCentralUpgrade\"; \n        \t document.form1.method=\"Post\"; //No I18N\n\t\t\t document.form1.submit();\n        \t /*\tCommented as it will be handled and tested later (Vijay)\n         \tif(!isMSSQL)\n         \t{\n         \t\tif(confirm(alertMessage))\n\t         \t{\n\t         \t\tdocument.form1.action=\"/swMgmt.do?method=initiateCentralUpgrade&backupRequired=true\"; \n\t\t\t\t    document.form1.method=\"Post\"; //No I18N\n\t\t\t\t    document.form1.submit();\n\t         \t}\n\t         \telse\n\t         \t{\n\t         \t\tdocument.form1.action=\"/swMgmt.do?method=initiateCentralUpgrade&backupRequired=false\"; \n\t\t\t\t    document.form1.method=\"Post\"; //No I18N\n\t\t\t\t    document.form1.submit();\n\t         \t}\n         \t}\n         \telse\n         \t{\n         \t\tif(confirm(alertMessage))\n\t         \t{\n\t         \t\tdocument.form1.action=\"/swMgmt.do?method=initiateCentralUpgrade&backupRequired=false\"; \n\t\t\t\t    document.form1.method=\"Post\"; //No I18N\n\t\t\t\t    document.form1.submit();\n\t         \t}\n         \t}\n");
/* 2324 */             out.write("        \t */\n\t\t    \n\t     } \n    }\n}\n\n\n\n</script>\n");
/*      */           }
/* 2326 */           out.write(10);
/* 2327 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2328 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2332 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2333 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/* 2336 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2337 */         out.write(10);
/* 2338 */         out.write(10);
/* 2339 */         out.write(10);
/*      */         
/* 2341 */         FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*      */         
/* 2343 */         Wield w = Wield.getInstance();
/* 2344 */         Hashtable licensetype = new Hashtable(4);
/*      */         
/*      */ 
/* 2347 */         licensetype.put("R", FormatUtil.getString("am.webclient.licensetype.registered"));
/* 2348 */         licensetype.put("T", FormatUtil.getString("am.webclient.licensetype.evaluation"));
/* 2349 */         licensetype.put("RT", FormatUtil.getString("am.webclient.licensetype.runtime"));
/* 2350 */         licensetype.put("F", FormatUtil.getString("am.webclient.licensetype.freeedition"));
/* 2351 */         request.setAttribute("licensetypes", licensetype);
/* 2352 */         request.setAttribute("isCloud", Boolean.valueOf(EnterpriseUtil.isCloudEdition()));
/* 2353 */         Properties licenseprops = new Properties();
/* 2354 */         licenseprops.put("userType", w.getUserType());
/* 2355 */         licenseprops.put("evaluationDays", String.valueOf(w.getEvaluationDays()));
/*      */         
/* 2357 */         String evaluationExpiryDate = w.getEvaluationExpiryDate();
/* 2358 */         StringTokenizer st = new StringTokenizer(evaluationExpiryDate);
/* 2359 */         if (st.countTokens() == 3)
/*      */         {
/* 2361 */           String year = st.nextToken();
/* 2362 */           String month = st.nextToken();
/* 2363 */           String day = st.nextToken();
/* 2364 */           String[] Months = { " ", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
/*      */           try
/*      */           {
/* 2367 */             month = Months[Integer.parseInt(month)];
/*      */           }
/*      */           catch (Exception exp)
/*      */           {
/* 2371 */             exp.printStackTrace();
/*      */           }
/* 2373 */           evaluationExpiryDate = month + " " + day + ", " + year;
/*      */         }
/* 2375 */         licenseprops.put("evaluationExpiryDate", evaluationExpiryDate);
/* 2376 */         licenseprops.put("userName", w.getUserName());
/* 2377 */         licenseprops.put("companyName", w.getCompanyName());
/*      */         
/* 2379 */         Properties info = com.adventnet.appmanager.struts.beans.ClientDBUtil.getLicenseInfo();
/* 2380 */         licenseprops.put("numberofclients", info.getProperty("numberofclients"));
/* 2381 */         licenseprops.put("nodes", info.getProperty("nodes"));
/* 2382 */         if (info.getProperty("amsExpiryDate") != null) {
/* 2383 */           licenseprops.put("amsExpiryDate", info.getProperty("amsExpiryDate"));
/*      */         }
/* 2385 */         request.setAttribute("licenseinfo", licenseprops);
/*      */         
/* 2387 */         String categorytype = "";
/* 2388 */         if (com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */         {
/* 2390 */           categorytype = com.adventnet.appmanager.util.Constants.getCategorytype();
/*      */         }
/*      */         
/* 2393 */         int totalmonitors = DBUtil.getNumberOfMonitors();
/* 2394 */         String eumChildListCond = " mo.resourceid NOT IN (" + com.adventnet.appmanager.util.Constants.getEUMChildString() + ")";
/* 2395 */         String types = com.adventnet.appmanager.util.Constants.resourceTypes;
/* 2396 */         String query = "select count(resourcename) from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type in " + types + " and (AM_ManagedResourceType.RESOURCEGROUP='APP' or AM_ManagedResourceType.RESOURCEGROUP='CAM'  or AM_ManagedResourceType.RESOURCEGROUP='SCR' or AM_ManagedResourceType.RESOURCEGROUP='DBS' or AM_ManagedResourceType.RESOURCEGROUP='SER' or AM_ManagedResourceType.RESOURCEGROUP='SYS' or AM_ManagedResourceType.RESOURCEGROUP='URL' or AM_ManagedResourceType.RESOURCEGROUP='MS' or AM_ManagedResourceType.RESOURCEGROUP='TM') group by resourcename,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID";
/* 2397 */         List list = mo.getRows(query);
/*      */         
/* 2399 */         query = "select * from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type in " + types + " and mo.resourceid=umo.resid and " + eumChildListCond;
/* 2400 */         List list1 = mo.getRows(query);
/* 2401 */         int unmanagedcount = list1.size();
/*      */         
/*      */ 
/* 2404 */         int noofmonitorsforlicense = com.adventnet.appmanager.util.Constants.getNoofMonitors_withoutnatives();
/* 2405 */         int sapcount = 0;
/* 2406 */         int sapunmanaged = 0;
/* 2407 */         int sapccmscount = 0;
/* 2408 */         int sapccmsunmanaged = 0;
/* 2409 */         int as400count = 0;
/* 2410 */         int as400unmanaged = 0;
/* 2411 */         int rbmcount = 0;
/* 2412 */         int rbmunmanaged = 0;
/* 2413 */         int oracleEBScount = 0;
/* 2414 */         int oracleEBSunmanaged = 0;
/* 2415 */         int hypervcount = 0;
/* 2416 */         int hypervunmanagedcount = 0;
/* 2417 */         int mqcount = 0;
/* 2418 */         int mqunmanaged = 0;
/* 2419 */         int sharepointcount = 0;
/* 2420 */         int sharepointunmanaged = 0;
/* 2421 */         int webtransaccount = 0;
/* 2422 */         int webtransacunmananaged = 0;
/* 2423 */         int apminsightJavaCount = 0;
/* 2424 */         int apminsightJavaUnManagedCount = 0;
/* 2425 */         int apminsightDotnetCount = 0;
/* 2426 */         int apminsightDotnetUnManagedCount = 0;
/* 2427 */         int vmesxcount = 0;
/* 2428 */         int vmesxunmanagedcount = 0;
/* 2429 */         int amazoncount = 0;
/* 2430 */         int amazonunmanagedcount = 0;
/* 2431 */         int eumCount = 0;
/* 2432 */         if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */         {
/* 2434 */           query = "select count(resourcename) from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type ='SAP' and (AM_ManagedResourceType.RESOURCEGROUP='ERP') group by resourcename,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID";
/* 2435 */           sapcount = mo.getRows(query).size();
/* 2436 */           query = "select * from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type ='SAP' and mo.resourceid=umo.resid";
/* 2437 */           sapunmanaged = mo.getRows(query).size();
/* 2438 */           query = "select * from AM_ManagedObject where AM_ManagedObject.Type ='SAP-CCMS'";
/* 2439 */           sapccmscount = mo.getRows(query).size();
/* 2440 */           query = "select * from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type ='SAP-CCMS' and mo.resourceid=umo.resid";
/* 2441 */           sapccmsunmanaged = mo.getRows(query).size();
/*      */           
/*      */ 
/* 2444 */           query = "select * from AM_ManagedObject where AM_ManagedObject.Type ='AS400/iSeries'";
/* 2445 */           as400count = mo.getRows(query).size();
/* 2446 */           query = "select * from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type ='AS400/iSeries' and mo.resourceid=umo.resid";
/* 2447 */           as400unmanaged = mo.getRows(query).size();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2452 */           query = "select * from AM_ManagedObject mo where mo.Type ='RBM' and " + eumChildListCond;
/* 2453 */           rbmcount = mo.getRows(query).size();
/* 2454 */           query = "select * from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type ='RBM' and mo.resourceid=umo.resid and " + eumChildListCond;
/* 2455 */           rbmunmanaged = mo.getRows(query).size();
/* 2456 */           query = "select distinct(AGENTNAME) from AM_RBMAGENTDATA where AGENTID>" + EnterpriseUtil.getDistributedStartResourceId();
/* 2457 */           eumCount = mo.getRows(query).size();
/*      */           
/*      */ 
/*      */ 
/* 2461 */           query = "select * from AM_ManagedObject where AM_ManagedObject.Type ='OracleEBS'";
/* 2462 */           oracleEBScount = mo.getRows(query).size();
/* 2463 */           query = "select * from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type ='OracleEBS' and mo.resourceid=umo.resid";
/* 2464 */           oracleEBSunmanaged = mo.getRows(query).size();
/*      */           
/* 2466 */           query = "select * from AM_ManagedObject where AM_ManagedObject.Type ='WebsphereMQ'";
/* 2467 */           mqcount = mo.getRows(query).size();
/* 2468 */           query = "select * from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type ='WebsphereMQ' and mo.resourceid=umo.resid";
/* 2469 */           mqunmanaged = mo.getRows(query).size();
/*      */           
/* 2471 */           query = "select * from AM_ManagedObject where AM_ManagedObject.Type ='OfficeSharePointServer'";
/* 2472 */           sharepointcount = mo.getRows(query).size();
/* 2473 */           query = "select * from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type ='OfficeSharePointServer' and mo.resourceid=umo.resid";
/* 2474 */           sharepointunmanaged = mo.getRows(query).size();
/*      */           
/* 2476 */           query = "select * from AM_ManagedObject where AM_ManagedObject.Type ='WTA'";
/* 2477 */           webtransaccount = mo.getRows(query).size();
/* 2478 */           query = "select * from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type ='WTA' and mo.resourceid=umo.resid";
/* 2479 */           webtransacunmananaged = mo.getRows(query).size();
/*      */           
/* 2481 */           query = "select * from apm_instances where type='JAVA'";
/* 2482 */           apminsightJavaCount = mo.getRows(query).size();
/* 2483 */           query = "select umo.* from AM_UnManagedNodes umo inner join apm_instances imo on umo.resid=imo.RESOURCEID where imo.TYPE='JAVA'";
/* 2484 */           apminsightJavaUnManagedCount = mo.getRows(query).size();
/*      */           
/* 2486 */           query = "select * from apm_instances where type='DOTNET'";
/* 2487 */           apminsightDotnetCount = mo.getRows(query).size();
/* 2488 */           query = "select umo.* from AM_UnManagedNodes umo inner join apm_instances imo on umo.resid=imo.RESOURCEID where imo.TYPE='DOTNET'";
/* 2489 */           apminsightDotnetUnManagedCount = mo.getRows(query).size();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2495 */         out.write(10);
/* 2496 */         out.write(10);
/*      */         
/* 2498 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2499 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2500 */         _jspx_th_c_005fif_005f1.setParent(null);
/*      */         
/* 2502 */         _jspx_th_c_005fif_005f1.setTest("${ not empty param.aboutPage}");
/* 2503 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2504 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */           for (;;) {
/* 2506 */             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"darkheaderbg\" >\n      <tr>\n\t  <td>\n<img hspace=\"10\" vspace=\"5\" src=\"/images/");
/* 2507 */             out.print(OEMUtil.getOEMString("am.header.logo"));
/* 2508 */             out.write("\">\n</td>\n</tr>\n</table>\n");
/* 2509 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2510 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2514 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2515 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */         }
/*      */         else {
/* 2518 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2519 */           out.write("\n\n\n    <form name=\"form1\">\n <table  width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"margin-top:5px;\">\n              <tr>\n                <td colspan=\"2\" class=\"tableheadingbborder\">\n                ");
/*      */           
/* 2521 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2522 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2523 */           _jspx_th_c_005fif_005f2.setParent(null);
/*      */           
/* 2525 */           _jspx_th_c_005fif_005f2.setTest("${not empty param.aboutPage}");
/* 2526 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2527 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */             for (;;) {
/* 2529 */               out.write(32);
/* 2530 */               out.print(OEMUtil.getOEMString("product.name"));
/* 2531 */               out.write("&nbsp;");
/* 2532 */               out.print(OEMUtil.getOEMString("product.version"));
/* 2533 */               out.write(47);
/* 2534 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2535 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2539 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2540 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */           }
/*      */           else {
/* 2543 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2544 */             out.print(FormatUtil.getString("am.webclient.support.licenseinfo"));
/* 2545 */             out.write("</td>\n              </tr>\n              <tr>\n                <td width=\"40%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2546 */             out.print(FormatUtil.getString("am.webclient.support.product"));
/* 2547 */             out.write(" </td>\n                <td width=\"60%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2548 */             out.print(OEMUtil.getOEMString("product.name"));
/* 2549 */             out.write("&nbsp;");
/* 2550 */             out.print(OEMUtil.getOEMString("product.version"));
/* 2551 */             out.write(32);
/* 2552 */             out.print(categorytype);
/* 2553 */             out.write(" </td>\n                \n              </tr>\n              ");
/*      */             
/* 2555 */             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2556 */             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2557 */             _jspx_th_c_005fif_005f3.setParent(null);
/*      */             
/* 2559 */             _jspx_th_c_005fif_005f3.setTest("${ not empty param.aboutPage}");
/* 2560 */             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2561 */             if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */               for (;;) {
/* 2563 */                 out.write("\n               <tr>\n\t              <td width=\"11%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2564 */                 out.print(FormatUtil.getString("am.webclient.about.buildnumber.text"));
/* 2565 */                 out.write("</td>\n\t              <td width=\"28%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"> ");
/* 2566 */                 out.print(OEMUtil.getOEMString("product.build.number"));
/* 2567 */                 out.write("\n\t          \t  </td>\n\t      </tr>\n\t     \n\n\t      ");
/*      */                 
/* 2569 */                 String dbType = System.getProperty("am.dbserver.type");
/* 2570 */                 String displayType = FormatUtil.getString("PostgreSQL");
/* 2571 */                 SWMgmtUtil.checkForLatestVersion();
/* 2572 */                 String latestVersion = SWMgmtUtil.getLatestVersionFromWebSite();
/* 2573 */                 String compatibleLatestVersionInWebSite = SWMgmtUtil.getCompatibleLatestVersionFromWebSite();
/* 2574 */                 boolean downloaded = SWMgmtUtil.getDownloadStatus("2").equals("COMPLETED");
/* 2575 */                 String downloadedVersion = SWMgmtUtil.getDownloadedVersion("2");
/* 2576 */                 if ((dbType != null) && (dbType.equalsIgnoreCase("mssql")))
/*      */                 {
/* 2578 */                   displayType = FormatUtil.getString("am.webclient.support.mssqldb");
/* 2579 */                 } else if ((dbType != null) && (dbType.equalsIgnoreCase("mysql"))) {
/* 2580 */                   displayType = FormatUtil.getString("MySQL");
/*      */                 }
/* 2582 */                 if (SWMgmtUtil.showLatestVersion())
/*      */                 {
/*      */ 
/* 2585 */                   out.write("\n\t      \n <tr>\n\t              <td width=\"11%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2586 */                   out.print(FormatUtil.getString("am.product.sp.latest.version"));
/* 2587 */                   out.write("</td>\n\t\t          <td width=\"28%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"> ");
/* 2588 */                   out.print(latestVersion);
/* 2589 */                   out.write("</td>\n\t      </tr>\n\t         ");
/*      */                   
/* 2591 */                   if (SWMgmtUtil.showCompatibleLatestVersion())
/*      */                   {
/*      */ 
/* 2594 */                     out.write("\n<tr>\n\t              <td width=\"11%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2595 */                     out.print(FormatUtil.getString("am.product.sp.compatible.version"));
/* 2596 */                     out.write("</td>\n\t\t          <td width=\"28%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"> \n\t\t       ");
/*      */                     
/* 2598 */                     if ((!latestVersion.equals(OEMUtil.getOEMString("product.build.number"))) && (!downloaded))
/*      */                     {
/*      */ 
/* 2601 */                       out.write("\n\t     ");
/* 2602 */                       out.print(compatibleLatestVersionInWebSite);
/*      */                       
/* 2604 */                       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2605 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2606 */                       _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f3);
/*      */                       
/* 2608 */                       _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/* 2609 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2610 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2612 */                           out.write("\n\t     ");
/*      */                           
/* 2614 */                           if ((request.getUserPrincipal().getName().equalsIgnoreCase("admin")) && (DBUtil.getGlobalConfigValueasBoolean("easyUpgrade")))
/*      */                           {
/*      */ 
/* 2617 */                             out.write("\n\t\t  \t<a href=\"javascript:swAction('download')\" class=\"staticlinks\">");
/* 2618 */                             out.print(FormatUtil.getString("am.product.sp.download.now"));
/* 2619 */                             out.write("</a>\n\t\t  ");
/*      */                           }
/*      */                           
/*      */ 
/* 2623 */                           out.write("\n\t     \n\t      ");
/* 2624 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2625 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2629 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2630 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                       }
/*      */                       
/* 2633 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2634 */                       out.write("</td>\n\t      ");
/*      */                     }
/*      */                     
/* 2637 */                     if ((!compatibleLatestVersionInWebSite.equals(OEMUtil.getOEMString("product.build.number"))) && (downloaded) && (compatibleLatestVersionInWebSite.equals(downloadedVersion)))
/*      */                     {
/*      */ 
/* 2640 */                       out.write("\n\t     \n\t\t  ");
/* 2641 */                       out.print(compatibleLatestVersionInWebSite);
/* 2642 */                       out.write(" \n\t\t  ");
/*      */                       
/* 2644 */                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2645 */                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2646 */                       _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f3);
/*      */                       
/* 2648 */                       _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/* 2649 */                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2650 */                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                         for (;;) {
/* 2652 */                           out.write("\n\t\t  ");
/*      */                           
/* 2654 */                           if ((request.getUserPrincipal().getName().equalsIgnoreCase("admin")) && (DBUtil.getGlobalConfigValueasBoolean("easyUpgrade")))
/*      */                           {
/*      */ 
/* 2657 */                             out.write("\n\t\t  \t\n\t\t  \t<a href=\"javascript:swAction('upgrade')\" class=\"staticlinks\">");
/* 2658 */                             out.print(FormatUtil.getString("am.product.sp.upgrade.now"));
/* 2659 */                             out.write("</a>\n\t\t  ");
/*      */                           }
/*      */                           
/*      */ 
/* 2663 */                           out.write("\n\t\t  ");
/* 2664 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2665 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2669 */                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2670 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                       }
/*      */                       
/* 2673 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2674 */                       out.write("</td>\n\t      \n\t      </tr>\n\t      ");
/*      */                     }
/*      */                     
/*      */ 
/* 2678 */                     out.write("\n\t     \n\t      ");
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/* 2683 */                 out.write("\n\n           <tr>\n\t              <td width=\"11%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2684 */                 out.print(FormatUtil.getString("am.webclient.mssqlleftpage.database"));
/* 2685 */                 out.write("</td>\n\t              <td width=\"28%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"> ");
/* 2686 */                 out.print(displayType);
/* 2687 */                 out.write("</td>\n\t       </tr>\n\t       <!--<tr>\n\t       ");
/* 2688 */                 String spversion = com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getServicePackVersion();
/* 2689 */                 out.write("\n\t               <td width=\"11%\" class=\"whitegrayrightalign-reports\">");
/* 2690 */                 out.print(FormatUtil.getString("am.webclient.about.servicepack.text"));
/* 2691 */                 out.write("</td>\n\t               <td width=\"28%\" class=\"whitegrayrightalign-reports\"> ");
/* 2692 */                 out.print(spversion);
/* 2693 */                 out.write("\n\n\t               </td>\n               </tr>-->\n               <tr>\n\t               <td width=\"11%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2694 */                 out.print(FormatUtil.getString("am.webclient.about.tollfree.text"));
/* 2695 */                 out.write("</td>\n\t               <td width=\"28%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"> ");
/* 2696 */                 out.print(OEMUtil.getOEMString("product.tollfree.number"));
/* 2697 */                 out.write("\n\n\n\t               </td>\n\t             </tr>\n\n\t             <tr>\n\t               <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2698 */                 out.print(FormatUtil.getString("am.webclient.about.support.text"));
/* 2699 */                 out.write(" </td>\n\t               <td width=\"28%\" class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"> ");
/* 2700 */                 out.print(com.adventnet.appmanager.util.UserUtil.getSupportMailID());
/* 2701 */                 out.write("</td>\n            </tr>\n            ");
/* 2702 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2703 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2707 */             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2708 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */             }
/*      */             else {
/* 2711 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2712 */               out.write("\n            ");
/*      */               
/* 2714 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2715 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2716 */               _jspx_th_c_005fif_005f4.setParent(null);
/*      */               
/* 2718 */               _jspx_th_c_005fif_005f4.setTest("${licenseinfo.userType=='R'}");
/* 2719 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2720 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                 for (;;) {
/* 2722 */                   out.write("\n\t          <tr>\n\t    \t\t                <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2723 */                   out.print(FormatUtil.getString("am.webclient.about.expires.text", new String[] { FormatUtil.getString("am.webclient.about.expires.on.text") }));
/* 2724 */                   out.write("</td>\n\t    \t\t                <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2725 */                   if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                     return;
/* 2727 */                   out.write("</td>\n                 </tr>\n                 ");
/*      */                   
/* 2729 */                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2730 */                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2731 */                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                   
/* 2733 */                   _jspx_th_c_005fif_005f5.setTest("${not empty licenseinfo.amsExpiryDate}");
/* 2734 */                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2735 */                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                     for (;;) {
/* 2737 */                       out.write("\n\t          <tr>\n\t    \t\t                <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2738 */                       out.print(FormatUtil.getString("am.webclient.about.amsexpires.text"));
/* 2739 */                       out.write("</td>\n\t    \t\t                <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2740 */                       if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                         return;
/* 2742 */                       out.write("</td>\n                 </tr>\n                 ");
/* 2743 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2744 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2748 */                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2749 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                   }
/*      */                   
/* 2752 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2753 */                   out.write("\n            ");
/* 2754 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2755 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2759 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2760 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*      */               }
/*      */               else {
/* 2763 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2764 */                 out.write("\n\n\t\t   <tr>\n\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2765 */                 out.print(FormatUtil.getString("Currently Used :"));
/* 2766 */                 out.write(32);
/* 2767 */                 out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitorcount"));
/* 2768 */                 out.write(" </td>\n\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2769 */                 out.print(totalmonitors);
/* 2770 */                 out.write("&nbsp;(");
/* 2771 */                 out.print(FormatUtil.getString("Managed"));
/* 2772 */                 out.write(32);
/* 2773 */                 out.write(45);
/* 2774 */                 out.write(32);
/* 2775 */                 out.print(totalmonitors - unmanagedcount);
/* 2776 */                 out.write("&nbsp;&nbsp;");
/* 2777 */                 out.print(FormatUtil.getString("UnManaged"));
/* 2778 */                 out.write(32);
/* 2779 */                 out.write(45);
/* 2780 */                 out.write(32);
/* 2781 */                 out.print(unmanagedcount);
/* 2782 */                 out.write(")</td>\n\t\t   </tr>\n\t\t   ");
/* 2783 */                 if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2784 */                   out.write("\n\t\t   <tr>\n\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2785 */                   out.print(FormatUtil.getString("Currently Used :"));
/* 2786 */                   out.write(32);
/* 2787 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.users"));
/* 2788 */                   out.write("</td>\n\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2789 */                   out.print(DBUtil.getNumberOfUsers());
/* 2790 */                   out.write("</td>\n\t\t   </tr>\n\t\t   ");
/*      */                 }
/* 2792 */                 out.write("\n\t\t   <tr>\n\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2793 */                 out.print(FormatUtil.getString("am.webclient.support.systeminformation.licensecount"));
/* 2794 */                 out.write("</td>\n\t\t   ");
/* 2795 */                 if (noofmonitorsforlicense != -1) {
/* 2796 */                   out.write("\n\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2797 */                   out.print(noofmonitorsforlicense);
/* 2798 */                   out.write("</td>\n\t\t   ");
/*      */                 } else {
/* 2800 */                   out.write("\n\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">-</td>\n\t\t   ");
/*      */                 }
/* 2802 */                 out.write("\n\t\t   </tr>\n\t\t\t\t");
/* 2803 */                 if ((fd.getUserType().equals("T")) && (sapccmscount <= 0) && (sapcount > 0)) {
/* 2804 */                   out.write("\n                <tr>\n\t\t   <td  class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2805 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.sapaddonrequired"));
/* 2806 */                   out.write("&nbsp;&nbsp;</td>\n\t\t   <td  class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2807 */                   out.print(sapcount - sapunmanaged);
/* 2808 */                   out.write("&nbsp;");
/* 2809 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitors"));
/* 2810 */                   out.write("</td>\n\t\t</tr>\n                ");
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/* 2815 */                 if ((fd.getUserType().equals("T")) && (as400count > 0)) {
/* 2816 */                   out.write("\n\n                 <tr>\n\t\t   \t\t\t<td  class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2817 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.as400addonrequired"));
/* 2818 */                   out.write("&nbsp;&nbsp;</td>\n\t\t   \t\t\t<td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2819 */                   out.print(as400count - as400unmanaged);
/* 2820 */                   out.write("&nbsp;");
/* 2821 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitors"));
/* 2822 */                   out.write("</td>\n\t\t\t\t</tr>\n\n                ");
/*      */                 }
/*      */                 
/* 2825 */                 if ((fd.getUserType().equals("T")) && (rbmcount > 0)) {
/* 2826 */                   out.write("\n\n                 <tr>\n\t\t   \t\t\t<td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2827 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.rbmaddonrequired"));
/* 2828 */                   out.write("&nbsp;&nbsp;</td>\n\t\t   \t\t\t<td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\" >");
/* 2829 */                   out.print(rbmcount - rbmunmanaged);
/* 2830 */                   out.write("&nbsp;");
/* 2831 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitors"));
/* 2832 */                   out.write("</td>\n\t\t\t\t</tr>\n\n                ");
/*      */                 }
/*      */                 
/* 2835 */                 if ((fd.getUserType().equals("T")) && (oracleEBScount > 0)) {
/* 2836 */                   out.write("\n\n\t\t                 <tr>\n\t\t\t\t   \t\t\t<td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\" >");
/* 2837 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.OracleEBSaddonrequired"));
/* 2838 */                   out.write("&nbsp;&nbsp;</td>\n\t\t\t\t   \t\t\t<td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2839 */                   out.print(oracleEBScount - oracleEBSunmanaged);
/* 2840 */                   out.write("&nbsp;");
/* 2841 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitors"));
/* 2842 */                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                 }
/*      */                 
/*      */ 
/* 2846 */                 if ((fd.getUserType().equals("T")) && (mqcount > 0)) {
/* 2847 */                   out.write("\n\n                                 <tr>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2848 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.mqaddonrequired"));
/* 2849 */                   out.write("&nbsp;&nbsp;</td>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2850 */                   out.print(mqcount - mqunmanaged);
/* 2851 */                   out.write("&nbsp;");
/* 2852 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitors"));
/* 2853 */                   out.write("</td>\n                                </tr>\n                                ");
/*      */                 }
/*      */                 
/* 2856 */                 if ((fd.getUserType().equals("T")) && (sharepointcount > 0)) {
/* 2857 */                   out.write("\n\n                                 <tr>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2858 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.sharepointaddonrequired"));
/* 2859 */                   out.write("&nbsp;&nbsp;</td>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2860 */                   out.print(sharepointcount - sharepointunmanaged);
/* 2861 */                   out.write("&nbsp;");
/* 2862 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitors"));
/* 2863 */                   out.write("</td>\n                                </tr>\n                                ");
/*      */                 }
/*      */                 
/* 2866 */                 if ((fd.getUserType().equals("T")) && (apminsightJavaCount > 0)) {
/* 2867 */                   out.write("\n\t\t\t\t\t\t\t\t<tr>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2868 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.apminsight.java.addonrequired"));
/* 2869 */                   out.write("&nbsp;&nbsp;</td>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2870 */                   out.print(apminsightJavaCount - apminsightJavaUnManagedCount);
/* 2871 */                   out.write("&nbsp;");
/* 2872 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitors"));
/* 2873 */                   out.write("</td>\n                                </tr>\n                                "); }
/* 2874 */                 if ((fd.getUserType().equals("T")) && (apminsightDotnetCount > 0)) {
/* 2875 */                   out.write("\n\t\t\t\t\t\t\t\t<tr>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2876 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.apminsight.dotnet.addonrequired"));
/* 2877 */                   out.write("&nbsp;&nbsp;</td>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2878 */                   out.print(apminsightDotnetCount - apminsightDotnetUnManagedCount);
/* 2879 */                   out.write("&nbsp;");
/* 2880 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitors"));
/* 2881 */                   out.write("</td>\n                                </tr>\n\t\t\t\t\t\t\t\t"); }
/* 2882 */                 if ((fd.getUserType().equals("T")) && (webtransaccount > 0)) {
/* 2883 */                   out.write("\n\n                                 <tr>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2884 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.webtransacaddonrequired"));
/* 2885 */                   out.write("&nbsp;&nbsp;</td>\n                                                        <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2886 */                   out.print(webtransaccount - webtransacunmananaged);
/* 2887 */                   out.write("&nbsp;");
/* 2888 */                   out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitors"));
/* 2889 */                   out.write("</td>\n                                </tr>\n\t  \n                                ");
/*      */                 }
/*      */                 
/* 2892 */                 out.write(10);
/* 2893 */                 out.write(10);
/*      */                 
/*      */ 
/* 2896 */                 if ((OEMUtil.isOEM()) && (!com.adventnet.appmanager.util.Constants.sqlManager))
/*      */                 {
/* 2898 */                   out.write("\n\n              <tr>\n                <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2899 */                   out.print(FormatUtil.getString("am.supporttab.maxmonitors.text"));
/* 2900 */                   out.write("</td>\n                ");
/* 2901 */                   if (fd.getUserType().equals("R")) {
/* 2902 */                     out.write("\n                <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2903 */                     out.print(fd.getNumberOfMonitorsPermitted());
/* 2904 */                     out.write("</td>\n                ");
/*      */                   }
/*      */                   else {
/* 2907 */                     String s1 = fd.getBoxTypeForOEM();
/* 2908 */                     String[] t1 = s1.split("#");
/* 2909 */                     String boxtype = t1[0];
/* 2910 */                     out.write("\n                 <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2911 */                     out.print(boxtype);
/* 2912 */                     out.write("</td>\n                 ");
/*      */                   }
/* 2914 */                   out.write("\n              </tr>\n\t\t\t\t");
/*      */                 }
/* 2916 */                 out.write("\n              ");
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 2921 */                 if ((!OEMUtil.isOEM()) || (OEMUtil.isRemove("am.licenseinfo.show"))) {
/* 2922 */                   out.write("\n               ");
/* 2923 */                   if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */                     return;
/* 2925 */                   out.write("\n        ");
/* 2926 */                   String expdays = (String)pageContext.getAttribute("expdays");
/* 2927 */                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                   {
/*      */ 
/* 2930 */                     out.write("\n           ");
/*      */                     
/* 2932 */                     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2933 */                     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2934 */                     _jspx_th_c_005fif_005f6.setParent(null);
/*      */                     
/* 2936 */                     _jspx_th_c_005fif_005f6.setTest("${licenseinfo.userType=='R'}");
/* 2937 */                     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2938 */                     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                       for (;;) {
/* 2940 */                         out.write("\n\t   \t                   ");
/* 2941 */                         if (fd.isOpmanagerConnectorPresent())
/*      */                         {
/* 2943 */                           out.write("\n\t   \t                    <tr>\n\t   \t   \t\t    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2944 */                           out.print(FormatUtil.getString("am.webclient.licenseinfo.noofnetworkdevices"));
/* 2945 */                           out.write("</td>\n\t   \t   \t\t    ");
/*      */                           
/*      */                           try
/*      */                           {
/* 2949 */                             String extDeviceQuery = "select count(*) as COUNT from AM_AssociatedExtDevices inner join AM_ManagedObject on  AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID where AM_ManagedObject.TYPE like 'OpManager%' and AM_ManagedObject.TYPE not like 'OpManager-Interface%'";
/* 2950 */                             ArrayList countlist = mo.getRows(extDeviceQuery);
/* 2951 */                             ArrayList singlerow = (ArrayList)countlist.get(0);
/*      */                             
/* 2953 */                             out.write("\n\t   \t   \t\t     <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2954 */                             out.print(singlerow.get(0));
/* 2955 */                             out.write("</td>\n\t   \t   \t\t     ");
/*      */                           } catch (Exception ex) {
/* 2957 */                             ex.printStackTrace();
/*      */                             
/* 2959 */                             out.write("\n\t   \t   \t\t     <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">-</td>\n\t   \t   \t\t     ");
/*      */                           }
/* 2961 */                           out.write("\n\n\t   \t                   </tr>\n\t                   ");
/*      */                         }
/* 2963 */                         out.write("\n                ");
/* 2964 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2965 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2969 */                     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2970 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                     }
/*      */                     
/* 2973 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2974 */                     out.write("\n\t\t\t\t");
/*      */                   }
/* 2976 */                   out.write("\n\t\t\t   <tr>\n\t      \t\t\t      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2977 */                   out.print(FormatUtil.getString("am.webclient.support.licensetype.heading"));
/* 2978 */                   out.write("</td>\n\t      \t\t\t      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 2979 */                   if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */                     return;
/* 2981 */                   out.write("\n\t      \t\t\t\t");
/*      */                   
/* 2983 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2984 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2985 */                   _jspx_th_c_005fif_005f7.setParent(null);
/*      */                   
/* 2987 */                   _jspx_th_c_005fif_005f7.setTest("${licenseinfo.userType=='T'}");
/* 2988 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2989 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 2991 */                       if ((expdays != null) && (expdays.equals("0"))) {
/* 2992 */                         out.print(FormatUtil.getString("am.support.expiresmessage.today.text"));
/* 2993 */                         out.write(32);
/* 2994 */                       } else if ((expdays != null) && (expdays.indexOf("-") != -1)) {
/* 2995 */                         out.write(32);
/* 2996 */                         out.write(40);
/* 2997 */                         out.print(FormatUtil.getString("am.header.trialperiod.expired.text"));
/* 2998 */                         out.write(41);
/* 2999 */                         out.write(32);
/*      */                       }
/*      */                       else {
/* 3002 */                         out.print(FormatUtil.getString("am.support.expiresmessage.text", new String[] { expdays }));
/*      */                       }
/* 3004 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3005 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3009 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3010 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 3013 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3014 */                   out.write(" </td>\n              </tr>\n              ");
/*      */                   
/* 3016 */                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3017 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3018 */                   _jspx_th_c_005fchoose_005f0.setParent(null);
/* 3019 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3020 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/* 3022 */                       out.write("\n                ");
/*      */                       
/* 3024 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3025 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3026 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/* 3028 */                       _jspx_th_c_005fwhen_005f0.setTest("${licenseinfo.userType=='R'}");
/* 3029 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3030 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/* 3032 */                           out.write("\n                <tr>\n\t\t\t                      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3033 */                           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverusername"));
/* 3034 */                           out.write(" </td>\n\t\t\t                      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3035 */                           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3037 */                           out.write("\n\t\t\t                      </td>\n\t\t\t                    </tr>\n\t\t\t                    <tr>\n\t\t\t      \t                      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3038 */                           out.print(FormatUtil.getString("am.webclient.licenseinfo.licensed"));
/* 3039 */                           out.write("&nbsp;: ");
/* 3040 */                           out.print(FormatUtil.getString("am.webclient.support.systeminformation.monitorcount"));
/* 3041 */                           out.write("</td>\n\t\t\t      \t                      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3042 */                           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3044 */                           out.write("</td>\n\t\t\t                    </tr>\n\t\t\t                    ");
/* 3045 */                           if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 3046 */                             out.write("\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3047 */                             out.print(FormatUtil.getString("am.webclient.licenseinfo.licensed"));
/* 3048 */                             out.write("&nbsp;: ");
/* 3049 */                             out.print(FormatUtil.getString("am.webclient.oracle.numberofusers"));
/* 3050 */                             out.write("</td>\n\t\t\t\t\t\t      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3051 */                             if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                               return;
/* 3053 */                             out.write("</td>\n\t\t\t\t\t  </tr>\n\t\t\t\t\t  ");
/*      */                           }
/* 3055 */                           out.write("\n\t\t\t                    <tr>\n\t\t\t                      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3056 */                           out.print(FormatUtil.getString("am.webclient.supporttab.registercomapany.text"));
/* 3057 */                           out.write(" </td>\n\t\t\t                      <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3058 */                           if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                             return;
/* 3060 */                           out.write("</td>\n\t                    </tr>\n              ");
/* 3061 */                           if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                           {
/* 3063 */                             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3064 */                             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3065 */                             _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 3067 */                             _jspx_th_c_005fif_005f8.setTest("${!isCloud}");
/* 3068 */                             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3069 */                             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                               for (;;) {
/* 3071 */                                 out.write("\n\t\t\t  <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3072 */                                 out.print(FormatUtil.getString("am.webclient.support.systeminformation.sapaddon"));
/* 3073 */                                 out.write("</td>\n\t                    ");
/*      */                                 
/* 3075 */                                 if ((fd.isSAPAddOnPresent()) || (FreeEditionDetails.sapMessage != null) || (fd.isSAPCCMSAddOnPresent()) || (FreeEditionDetails.sapccmsMessage != null))
/*      */                                 {
/*      */ 
/* 3078 */                                   out.write("\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">\n");
/* 3079 */                                   if (FreeEditionDetails.sapMessage != null) out.println("(" + FreeEditionDetails.sapMessage + ")");
/* 3080 */                                   out.write("\n\n</td>\n\t                    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3085 */                                   out.write("\n\t                    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3087 */                                 out.write("\n              </tr>\n              <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3088 */                                 out.print(FormatUtil.getString("am.webclient.support.systeminformation.sapccmsaddon"));
/* 3089 */                                 out.write("</td>\n\t                    ");
/*      */                                 
/* 3091 */                                 if ((fd.isSAPCCMSAddOnPresent()) || (FreeEditionDetails.sapccmsMessage != null))
/*      */                                 {
/*      */ 
/* 3094 */                                   out.write("\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">&nbsp;");
/* 3095 */                                   if (FreeEditionDetails.sapccmsMessage != null) out.println("(" + FreeEditionDetails.sapccmsMessage + ")");
/* 3096 */                                   out.write("</td>\n\t                    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3101 */                                   out.write("\n\t                    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3103 */                                 out.write("\n              </tr>\n\n\n\n              <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3104 */                                 out.print(FormatUtil.getString("am.webclient.support.systeminformation.as400addon"));
/* 3105 */                                 out.write("</td>\n\t                    ");
/*      */                                 
/* 3107 */                                 if ((fd.isAS400AddOnPresent()) || (FreeEditionDetails.as400Message != null))
/*      */                                 {
/*      */ 
/* 3110 */                                   out.write("\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">&nbsp;");
/* 3111 */                                   if (FreeEditionDetails.as400Message != null) out.println("(" + FreeEditionDetails.as400nodrMessage + ")");
/* 3112 */                                   out.write("</td>\n\t                    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3117 */                                   out.write("\n\t                    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3119 */                                 out.write("\n              </tr>\n              <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3120 */                                 out.print(FormatUtil.getString("am.webclient.support.systeminformation.as400adminaddon"));
/* 3121 */                                 out.write("</td>\n\t                    ");
/*      */                                 
/* 3123 */                                 if ((fd.isAS400adminAddOnPresent()) || (FreeEditionDetails.as400Message != null))
/*      */                                 {
/*      */ 
/* 3126 */                                   out.write("\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">&nbsp;");
/* 3127 */                                   if (FreeEditionDetails.as400Message != null) out.println("(" + FreeEditionDetails.as400nodrMessage + ")");
/* 3128 */                                   out.write("</td>\n\t                    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3133 */                                   out.write("\n\t                    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3135 */                                 out.write("\n              </tr>\n <!--  <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3136 */                                 out.print(FormatUtil.getString("am.webclient.anomalyprofile.addon.text"));
/* 3137 */                                 out.write("</td>\n\t                    ");
/*      */                                 
/* 3139 */                                 if ((fd.isAnomalyAddOnPresent()) || (FreeEditionDetails.anomalyMessage != null))
/*      */                                 {
/*      */ 
/* 3142 */                                   out.write("\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">&nbsp;");
/* 3143 */                                   if (FreeEditionDetails.anomalyMessage != null) out.println("(" + FreeEditionDetails.anomalynodrMessage + ")");
/* 3144 */                                   out.write("</td>\n\t                    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3149 */                                   out.write("\n\t                    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3151 */                                 out.write("\n              </tr> -->\n              <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3152 */                                 out.print(FormatUtil.getString("am.webclient.rbmurlmonitor.addon.text"));
/* 3153 */                                 out.write("</td>\n\t                    ");
/*      */                                 
/* 3155 */                                 if ((fd.isEUMAddOnPresent()) || (FreeEditionDetails.eumMessage != null))
/*      */                                 {
/*      */ 
/* 3158 */                                   out.write("\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">&nbsp;");
/* 3159 */                                   if (FreeEditionDetails.eumMessage != null) out.println("(" + FreeEditionDetails.eumnodrMessage + ")");
/* 3160 */                                   out.write(" <!--  &nbsp; ");
/* 3161 */                                   out.print(FormatUtil.getString("am.webclient.eumagent.license.text", new String[] { "" + fd.getNumberOfEUMPermitted(), "" + eumCount }));
/* 3162 */                                   out.write(" --> </td>\n\t                    ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3166 */                                   out.write("\n\t                    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3168 */                                 out.write("\n              </tr>\n              \n              <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3169 */                                 out.print(FormatUtil.getString("apminsight.java.addon"));
/* 3170 */                                 out.write("&nbsp;");
/* 3171 */                                 out.print(FormatUtil.getString("am.webclient.supporttab.registeraddon.text"));
/* 3172 */                                 out.write("</td>\n\t                    ");
/* 3173 */                                 if (fd.isApmInsightJavaEnabled()) {
/* 3174 */                                   out.write("\n      \t           \t\t\t<td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">\n     \t           \t\t\t \t");
/* 3175 */                                   if (FreeEditionDetails.addOnEvalDaysProps.getProperty(ApmInsightLicenseType.JAVA.getLicenseTypeInLincenseFile()) != null) {
/* 3176 */                                     out.write("\n\t      \t             \t\t\t(");
/* 3177 */                                     out.print(FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty(ApmInsightLicenseType.JAVA.getLicenseTypeInLincenseFile()) }));
/* 3178 */                                     out.write(")\n      \t             \t\t\t");
/*      */                                   }
/* 3180 */                                   out.write("\n      \t             \t\t</td>\n\t                    ");
/*      */                                 } else {
/* 3182 */                                   out.write("\n\t                    \t\t<td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3184 */                                 out.write("\n              </tr>\n              \n              <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3185 */                                 out.print(FormatUtil.getString("apminsight.dotnet.addon"));
/* 3186 */                                 out.write("&nbsp;");
/* 3187 */                                 out.print(FormatUtil.getString("am.webclient.supporttab.registeraddon.text"));
/* 3188 */                                 out.write("</td>\n\t                    ");
/* 3189 */                                 if (fd.isApmInsightDotnetEnabled()) {
/* 3190 */                                   out.write("\n      \t           \t\t\t<td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">\n     \t           \t\t\t \t");
/* 3191 */                                   if (FreeEditionDetails.addOnEvalDaysProps.getProperty(ApmInsightLicenseType.DOTNET.getLicenseTypeInLincenseFile()) != null) {
/* 3192 */                                     out.write("\n\t      \t             \t\t\t(");
/* 3193 */                                     out.print(FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty(ApmInsightLicenseType.DOTNET.getLicenseTypeInLincenseFile()) }));
/* 3194 */                                     out.write(")\n      \t             \t\t\t");
/*      */                                   }
/* 3196 */                                   out.write("\n      \t             \t\t</td>\n\t                    ");
/*      */                                 } else {
/* 3198 */                                   out.write("\n\t                    \t\t<td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3200 */                                 out.write("\n              </tr>\n              \n              <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3201 */                                 out.print(FormatUtil.getString("J2EE Web Transactions"));
/* 3202 */                                 out.write("&nbsp;");
/* 3203 */                                 out.print(FormatUtil.getString("am.webclient.supporttab.registeraddon.text"));
/* 3204 */                                 out.write("</td>\n\t                    ");
/*      */                                 
/* 3206 */                                 if (fd.isWebTransaction())
/*      */                                 {
/*      */ 
/* 3209 */                                   out.write("\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">\n\t      \t             ");
/* 3210 */                                   if (FreeEditionDetails.addOnEvalDaysProps.getProperty("J2EEWebTransaction") != null) {
/* 3211 */                                     out.write("\n\t      \t             (");
/* 3212 */                                     out.print(FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty("J2EEWebTransaction") }));
/* 3213 */                                     out.write(41);
/*      */                                   }
/* 3215 */                                   out.write("</td>\n\n\t                    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3220 */                                   out.write("\n\t                    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3222 */                                 out.write("\n              </tr>\n              <tr>\n\t      \t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3223 */                                 out.print(FormatUtil.getString("OracleEBS"));
/* 3224 */                                 out.write("&nbsp;");
/* 3225 */                                 out.print(FormatUtil.getString("am.webclient.supporttab.registeraddon.text"));
/* 3226 */                                 out.write("</td>\n\t      \t                    ");
/*      */                                 
/*      */ 
/* 3229 */                                 if (fd.isOracleEBSAllowed())
/*      */                                 {
/*      */ 
/* 3232 */                                   out.write("\n\t      \t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">\n\n\t      \t      \t             ");
/*      */                                   
/* 3234 */                                   if (FreeEditionDetails.oracleEBSMessage1 != null)
/*      */                                   {
/* 3236 */                                     out.println("(" + FreeEditionDetails.oracleEBSMessage1 + ")");
/*      */                                   }
/*      */                                   
/* 3239 */                                   out.write("\n\t      \t      \t             </td>\n\n\t      \t                    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3245 */                                   out.write("\n\t      \t                    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t      \t                    ");
/*      */                                 }
/* 3247 */                                 out.write("\n              </tr>\n              <tr>\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3248 */                                 out.print(FormatUtil.getString("IBM Websphere MQ"));
/* 3249 */                                 out.write("&nbsp;");
/* 3250 */                                 out.print(FormatUtil.getString("am.webclient.supporttab.registeraddon.text"));
/* 3251 */                                 out.write("</td>\n\t                    ");
/*      */                                 
/* 3253 */                                 if (fd.isMqSeries())
/*      */                                 {
/*      */ 
/* 3256 */                                   out.write("\n\t      \t           <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">\n\t      \t             ");
/* 3257 */                                   if (FreeEditionDetails.addOnEvalDaysProps.getProperty("WebSphereMQ") != null) {
/* 3258 */                                     out.write("\n\t      \t             (");
/* 3259 */                                     out.print(FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty("WebSphereMQ") }));
/* 3260 */                                     out.write(41);
/*      */                                   }
/* 3262 */                                   out.write("</td>\n\n\t                    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3267 */                                   out.write("\n\t                    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t                    ");
/*      */                                 }
/* 3269 */                                 out.write("\n              </tr>\n              <tr>\n\t\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3270 */                                 out.print(FormatUtil.getString("MS Office SharePoint"));
/* 3271 */                                 out.write("&nbsp;");
/* 3272 */                                 out.print(FormatUtil.getString("am.webclient.supporttab.registeraddon.text"));
/* 3273 */                                 out.write("</td>\n\t\t\t    ");
/*      */                                 
/* 3275 */                                 if (fd.isSharePoint())
/*      */                                 {
/*      */ 
/* 3278 */                                   out.write("\n\t\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">\n\t      \t             ");
/* 3279 */                                   if (FreeEditionDetails.addOnEvalDaysProps.getProperty("OfficeSharePoint") != null) {
/* 3280 */                                     out.write("\n\t      \t             (");
/* 3281 */                                     out.print(FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty("OfficeSharePoint") }));
/* 3282 */                                     out.write(41);
/*      */                                   }
/* 3284 */                                   out.write("</td>\n\n\t\t\t    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3289 */                                   out.write("\n\t\t\t    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t\t\t    ");
/*      */                                 }
/* 3291 */                                 out.write("\n              </tr>\n              <tr>\n\t\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3292 */                                 out.print(FormatUtil.getString("Siebel"));
/* 3293 */                                 out.write("&nbsp;");
/* 3294 */                                 out.print(FormatUtil.getString("am.webclient.supporttab.registeraddon.text"));
/* 3295 */                                 out.write("</td>\n\t\t\t    ");
/*      */                                 
/* 3297 */                                 if (fd.isSiebelAllowed())
/*      */                                 {
/*      */ 
/* 3300 */                                   out.write("\n\t\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">\n\t      \t             ");
/* 3301 */                                   if (FreeEditionDetails.siebelMessage != null) {
/* 3302 */                                     out.write("\n\t      \t             (");
/* 3303 */                                     out.print(FreeEditionDetails.siebelMessage);
/* 3304 */                                     out.write(41);
/*      */                                   }
/* 3306 */                                   out.write("</td>\n\n\t\t\t    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3311 */                                   out.write("\n\t\t\t    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t\t\t    ");
/*      */                                 }
/* 3313 */                                 out.write("\n              </tr>\n\t      <tr>\n\t\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\">");
/* 3314 */                                 out.print(FormatUtil.getString("Application Discovery and Dependency Mapping"));
/* 3315 */                                 out.write("&nbsp;");
/* 3316 */                                 out.print(FormatUtil.getString("am.webclient.supporttab.registeraddon.text"));
/* 3317 */                                 out.write("</td>\n\t\t\t    ");
/*      */                                 
/* 3319 */                                 if (fd.isADDMAllowed())
/*      */                                 {
/*      */ 
/* 3322 */                                   out.write("\n\t\t\t   <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/icon_tickmark.gif\" border=\"0\">\n\t      \t             ");
/* 3323 */                                   if (FreeEditionDetails.addmMessage != null) {
/* 3324 */                                     out.write("\n\t      \t             (");
/* 3325 */                                     out.print(FreeEditionDetails.addmMessage);
/* 3326 */                                     out.write(41);
/*      */                                   }
/* 3328 */                                   out.write("</td>\n\n\t\t\t    ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3333 */                                   out.write("\n\t\t\t    <td class=\"whitegrayrightalign-reports\" style=\"padding-left:5px\"><img src=\"/images/cross.gif\" border=\"0\"></td>\n\t\t\t    ");
/*      */                                 }
/* 3335 */                                 out.write("\n              </tr>\t\n              \n              ");
/* 3336 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3337 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3341 */                             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3342 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                             }
/*      */                             
/* 3345 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3346 */                             out.write("\n\t\t\t  ");
/*      */                           }
/* 3348 */                           out.write("\n               ");
/* 3349 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3350 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3354 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3355 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/* 3358 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3359 */                       out.write("\n                ");
/*      */                       
/* 3361 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3362 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3363 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/* 3365 */                       _jspx_th_c_005fwhen_005f1.setTest("${licenseinfo.userType=='F'}");
/* 3366 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3367 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/* 3369 */                           out.write("\n\t                        <tr class=\"tablebottom\">\n\t                       <td style=\"padding-left:5px\" align=\"right\" colspan=\"2\">\n\t                       ");
/*      */                           
/* 3371 */                           if (new File("./classes/Evaluation.xml").exists())
/*      */                           {
/*      */ 
/* 3374 */                             out.write("\n\t                       <b><a href=\"/adminAction.do?method=updateLicense\" class=\"staticlinks\" title=\"Change to Evaluation Edition of Applications Manager\">");
/* 3375 */                             out.print(FormatUtil.getString("am.webclient.support.evaluate.link"));
/* 3376 */                             out.write("</a></b>\n\t                       ");
/*      */                           }
/*      */                           
/*      */ 
/* 3380 */                           out.write("\n\t                        ");
/* 3381 */                           if (!OEMUtil.isOEM()) {
/* 3382 */                             out.write(" &nbsp; <b><a href=\"");
/* 3383 */                             out.print(FormatUtil.getString("am.webclient.buy.link.tooltip.text"));
/* 3384 */                             out.write("\" target=\"none\" class=\"staticlinks\" title=\"");
/* 3385 */                             out.print(FormatUtil.getString("am.webclient.support.buy.tooltip"));
/* 3386 */                             out.write(34);
/* 3387 */                             out.write(62);
/* 3388 */                             out.print(FormatUtil.getString("am.webclient.support.buy.link"));
/* 3389 */                             out.write("</a></b>");
/*      */                           }
/* 3391 */                           out.write("&nbsp;\n\t                       </td>\n\t                       </tr>\n\t                       ");
/* 3392 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3393 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3397 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3398 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/* 3401 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3402 */                       out.write("\n\t                       ");
/*      */                       
/* 3404 */                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3405 */                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3406 */                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/* 3408 */                       _jspx_th_c_005fwhen_005f2.setTest("${licenseinfo.userType=='T'}");
/* 3409 */                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3410 */                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                         for (;;) {
/* 3412 */                           out.write("\n\t                       <tr >\n\t                       <td class=\"tablebottom\" style=\"padding-left:5px\" align=\"right\" colspan=\"2\"> ");
/* 3413 */                           if ((!OEMUtil.isOEM()) || (com.adventnet.appmanager.util.Constants.sqlManager)) {
/* 3414 */                             out.write("<a href=\"");
/* 3415 */                             out.print(FormatUtil.getString("am.webclient.buy.link.tooltip.text"));
/* 3416 */                             out.write("\" target=\"none\" class=\"staticlinks\" title=\"");
/* 3417 */                             out.print(FormatUtil.getString("am.webclient.support.buy.tooltip"));
/* 3418 */                             out.write(34);
/* 3419 */                             out.write(62);
/* 3420 */                             out.print(FormatUtil.getString("am.webclient.support.buy.link"));
/* 3421 */                             out.write("</a>");
/*      */                           }
/* 3423 */                           out.write("&nbsp;\n\t                       </td>\n\t                       </tr>\n                ");
/* 3424 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3425 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3429 */                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3430 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                       }
/*      */                       
/* 3433 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3434 */                       out.write("\n               ");
/* 3435 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3436 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3440 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3441 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/* 3444 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3445 */                   out.write("\n             ");
/*      */                 }
/* 3447 */                 out.write("\n\n           ");
/*      */                 
/* 3449 */                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3450 */                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3451 */                 _jspx_th_c_005fif_005f9.setParent(null);
/*      */                 
/* 3453 */                 _jspx_th_c_005fif_005f9.setTest("${ not empty param.aboutPage}");
/* 3454 */                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3455 */                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                   for (;;) {
/* 3457 */                     out.write("\n           ");
/* 3458 */                     if (!OEMUtil.isOEM()) {
/* 3459 */                       out.write("\n\t         <tr>\n\n\t       <td class=\"bodytext\" align=\"center\" colspan='3'>");
/* 3460 */                       out.print(FormatUtil.getString("am.webclient.about.credits.text"));
/* 3461 */                       out.write("</td>\n\t     </tr>\n\t     <tr>\n\t         <td  colspan=\"3\" class=\"bodyText\" align=\"center\">\n\t           <table width=\"40%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class='lrtbdarkborder'>\n\t             <tr>\n\t               <td class=\"lrbtborder\"> <div style=\"height:60;overflow:hidden\">\n\n\t                 <div id=\"iemarquee\" style=\"position:relative;left:0px;top:0px;width:100%;\" onMouseover=\"copyspeed=pausespeed\" onMouseout=\"copyspeed=marqueespeed\"></div>\n\n\t                 </div></td>\n\t             </tr>\n\t       </table>\n    </td></tr>\n    <tr>\n    \t<td  colspan=\"3\"><img src=\"/images/spacer.gif\" height=\"5\" width=\"5\"></td>\n    </tr>\n\n\n    ");
/*      */                     }
/* 3463 */                     out.write("\n           ");
/* 3464 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3465 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3469 */                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3470 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*      */                 }
/*      */                 else {
/* 3473 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3474 */                   out.write("\n   </table>\n   </form>\n");
/*      */                 }
/* 3476 */               } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3477 */         out = _jspx_out;
/* 3478 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3479 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3480 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3483 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3489 */     PageContext pageContext = _jspx_page_context;
/* 3490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3492 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3493 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3494 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3496 */     _jspx_th_c_005fout_005f0.setValue("${licenseinfo.evaluationExpiryDate}");
/* 3497 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3498 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3499 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3500 */       return true;
/*      */     }
/* 3502 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3503 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3508 */     PageContext pageContext = _jspx_page_context;
/* 3509 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3511 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3512 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3513 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3515 */     _jspx_th_c_005fout_005f1.setValue("${licenseinfo.amsExpiryDate}");
/* 3516 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3517 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3518 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3519 */       return true;
/*      */     }
/* 3521 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3522 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3527 */     PageContext pageContext = _jspx_page_context;
/* 3528 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3530 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3531 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3532 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 3534 */     _jspx_th_c_005fset_005f0.setVar("expdays");
/* 3535 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3536 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3537 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3538 */         out = _jspx_page_context.pushBody();
/* 3539 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3540 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3543 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 3544 */           return true;
/* 3545 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3546 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3549 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3550 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3553 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3554 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3555 */       return true;
/*      */     }
/* 3557 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3558 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3563 */     PageContext pageContext = _jspx_page_context;
/* 3564 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3566 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3567 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3568 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3570 */     _jspx_th_c_005fout_005f2.setValue("${licenseinfo.evaluationDays}");
/* 3571 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3572 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3573 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3574 */       return true;
/*      */     }
/* 3576 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3582 */     PageContext pageContext = _jspx_page_context;
/* 3583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3585 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3586 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3587 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 3589 */     _jspx_th_c_005fout_005f3.setValue("${licensetypes[licenseinfo.userType]}");
/* 3590 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3591 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3592 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3593 */       return true;
/*      */     }
/* 3595 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3596 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3601 */     PageContext pageContext = _jspx_page_context;
/* 3602 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3604 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3605 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3606 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3608 */     _jspx_th_c_005fout_005f4.setValue("${licenseinfo.userName}");
/* 3609 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3610 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3611 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3612 */       return true;
/*      */     }
/* 3614 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3615 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3620 */     PageContext pageContext = _jspx_page_context;
/* 3621 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3623 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3624 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3625 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3627 */     _jspx_th_c_005fout_005f5.setValue("${licenseinfo.nodes}");
/* 3628 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3629 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3630 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3631 */       return true;
/*      */     }
/* 3633 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3634 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3639 */     PageContext pageContext = _jspx_page_context;
/* 3640 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3642 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3643 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3644 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3646 */     _jspx_th_c_005fout_005f6.setValue("${licenseinfo.numberofclients}");
/* 3647 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3648 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3649 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3650 */       return true;
/*      */     }
/* 3652 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3653 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3658 */     PageContext pageContext = _jspx_page_context;
/* 3659 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3661 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3662 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3663 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3665 */     _jspx_th_c_005fout_005f7.setValue("${licenseinfo.companyName}");
/* 3666 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3667 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3668 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3669 */       return true;
/*      */     }
/* 3671 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3672 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\licenseinfo_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */